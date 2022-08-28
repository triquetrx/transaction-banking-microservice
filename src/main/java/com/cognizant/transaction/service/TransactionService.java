package com.cognizant.transaction.service;

import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cognizant.transaction.clients.AccountClient;
import com.cognizant.transaction.clients.AuthClient;
import com.cognizant.transaction.clients.RulesClient;
import com.cognizant.transaction.dto.Account;
import com.cognizant.transaction.dto.OneWayTransactionDTO;
import com.cognizant.transaction.dto.RuleStatus;
import com.cognizant.transaction.dto.TransactionDTO;
import com.cognizant.transaction.dto.TransactionStatus;
import com.cognizant.transaction.dto.TransferDTO;
import com.cognizant.transaction.dto.TransferSuccessMessage;
import com.cognizant.transaction.exception.InsufficientBalanceException;
import com.cognizant.transaction.exception.InvalidAccessException;
import com.cognizant.transaction.exception.InvalidAccountIdException;
import com.cognizant.transaction.model.TransactionHistory;
import com.cognizant.transaction.repository.TransactionHistoryRepository;

@Service
public class TransactionService {

	@Autowired
	AuthClient authClient;

	@Autowired
	AccountClient accountClient;

	@Autowired
	TransactionHistoryRepository repository;

	@Autowired
	RulesClient rulesClient;

	private boolean isTransactionValid(String token, String accountType, double balance) {
		RuleStatus minBalance = rulesClient.evaluateMinBalance(token, balance, accountType);
		if (minBalance.getStatus().equalsIgnoreCase("Allowed")) {
			return true;
		}
		return false;
	}

	public TransactionStatus deposit(String token, OneWayTransactionDTO transactionDTO) throws InvalidAccessException {
		if (authClient.validatingToken(token).isValidStatus()) {
			String id = "TRANSACT" + repository.count();
			String narration = transactionDTO.getNarration();
			if (narration.isBlank() || narration.isEmpty()) {
				narration = "SELF_DEPOSIT";
			}
			TransactionStatus deposit = accountClient.deposit(token,
					new TransactionDTO(transactionDTO.getAccountId(), narration, transactionDTO.getAmount(), id));
			repository.save(
					new TransactionHistory(id, transactionDTO.getAccountId(), transactionDTO.getAmount(), narration,
							transactionDTO.getTransactionType(), transactionDTO.getAccountId(), new Date(), "DEPOSIT"));
			return deposit;
		}
		throw new InvalidAccessException();
	}

	public TransactionStatus withdraw(String token, OneWayTransactionDTO transactionDTO)
			throws InvalidAccessException, InsufficientBalanceException {
		if (authClient.validatingToken(token).isValidStatus()) {
			Account account = accountClient.getAccounts(token, transactionDTO.getAccountId());
			double amount = transactionDTO.getAmount()
					+ (transactionDTO.getAmount() * rulesClient.getServiceCharges(account.getAccountType()));
			if (isTransactionValid(token, account.getAccountType(), account.getBalance() - amount)) {
				String id = "TRANSACT" + repository.count();
				String narration = transactionDTO.getNarration();
				if (narration.isBlank() || narration.isEmpty()) {
					narration = "SELF_WITHDRAW";
				}
				TransactionStatus withdraw = accountClient.withdraw(token,
						new TransactionDTO(transactionDTO.getAccountId(), narration, amount, id));
				repository.save(new TransactionHistory(id, transactionDTO.getAccountId(),
						Math.round(amount * 100 / 100), narration, transactionDTO.getTransactionType(),
						transactionDTO.getAccountId(), new Date(), "WITHDRAW"));
				return withdraw;
			}
			throw new InsufficientBalanceException();
		}
		throw new InvalidAccessException();
	}

	@Transactional
	public TransferSuccessMessage transfer(String token, TransferDTO transferDTO)
			throws InvalidAccessException, InsufficientBalanceException {
		if (authClient.validatingToken(token).isValidStatus()) {
			Account account = accountClient.getAccounts(token, transferDTO.getFromAccountId());
			double amount = transferDTO.getAmount()
					+ (transferDTO.getAmount() * rulesClient.getServiceCharges(account.getAccountType()));
			if (isTransactionValid(token, account.getAccountType(), (account.getBalance() - amount))) {
				String id = "TRANSACT" + repository.count();
				String narration = transferDTO.getNarration();
				if (narration.isBlank() || narration.isEmpty()) {
					narration = "SELF_WITHDRAW";
				}
				TransactionStatus withdraw = accountClient.withdraw(token, new TransactionDTO(
						transferDTO.getFromAccountId(), narration, Math.round(amount * 100 / 100), id));
				TransactionStatus deposit = accountClient.deposit(token,
						new TransactionDTO(transferDTO.getToAccountId(), narration, transferDTO.getAmount(), id));

				repository.save(new TransactionHistory(id, transferDTO.getFromAccountId(), transferDTO.getAmount(),
						narration, transferDTO.getTransactionType(), transferDTO.getToAccountId(), new Date(),
						"TRANSFER"));
				return new TransferSuccessMessage(withdraw.getMessage(), deposit.getMessage(), withdraw.getBalance());
			}
			throw new InsufficientBalanceException();
		}
		throw new InvalidAccessException();
	}

	public List<TransactionHistory> transactionHistory(String token, String accountId)
			throws InvalidAccessException, InvalidAccountIdException {
		if (authClient.validatingToken(token).isValidStatus()
				&& authClient.validatingToken(token).getUserRole().equalsIgnoreCase("ROLE_EMPLOYEE")) {
			List<TransactionHistory> list = repository.findByAccountId(accountId);
			if (list.isEmpty()) {
				throw new InvalidAccountIdException();
			}
			return list;
		}
		throw new InvalidAccessException();
	}

}
