package com.cognizant.transaction.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.cognizant.transaction.dto.OneWayTransactionDTO;
import com.cognizant.transaction.dto.TransactionStatus;
import com.cognizant.transaction.dto.TransferDTO;
import com.cognizant.transaction.dto.TransferSuccessMessage;
import com.cognizant.transaction.exception.InsufficientBalanceException;
import com.cognizant.transaction.exception.InvalidAccessException;
import com.cognizant.transaction.exception.InvalidAccountIdException;
import com.cognizant.transaction.model.TransactionHistory;

@Service
public interface TransactionService {

	TransactionStatus deposit(String token, OneWayTransactionDTO transactionDTO) throws InvalidAccessException;

	TransactionStatus withdraw(String token, OneWayTransactionDTO transactionDTO)
			throws InvalidAccessException, InsufficientBalanceException;

	TransferSuccessMessage transfer(String token, TransferDTO transferDTO)
			throws InvalidAccessException, InsufficientBalanceException;

	List<TransactionHistory> transactionHistory(String token, String accountId)
			throws InvalidAccessException, InvalidAccountIdException;

	
}
