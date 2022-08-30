package com.cognizant.transaction.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import com.cognizant.transaction.dto.OneWayTransactionDTO;
import com.cognizant.transaction.dto.TransactionStatus;
import com.cognizant.transaction.dto.TransferDTO;
import com.cognizant.transaction.dto.TransferSuccessMessage;
import com.cognizant.transaction.exception.InsufficientBalanceException;
import com.cognizant.transaction.exception.InvalidAccessException;
import com.cognizant.transaction.exception.InvalidAccountIdException;
import com.cognizant.transaction.model.TransactionHistory;
import com.cognizant.transaction.service.TransactionService;

import feign.FeignException.FeignClientException;

@RestController
public class TransactionController {
	
	@Autowired
	TransactionService service;
	
	@CrossOrigin(origins ="http://localhost:5000")
	@PostMapping("/deposit")
	public ResponseEntity<?> deposit(@RequestHeader(name="Authorization")String token,@RequestBody OneWayTransactionDTO transactionDTO){
		try {
			TransactionStatus deposit = service.deposit(token, transactionDTO);
			return new ResponseEntity<>(deposit,HttpStatus.OK);
		} catch (InvalidAccessException e) {
			return new ResponseEntity<>("UNAUTHORIZED_ACCESS",HttpStatus.UNAUTHORIZED);
		} catch(FeignClientException e) {
			String[] message = e.getMessage().split(" ");
			int errCode = Integer.parseInt(message[0].split("")[1]+message[0].split("")[2]+message[0].split("")[3]);
			return new ResponseEntity<>(message[5],HttpStatus.valueOf(errCode));
		}
	}
	
	@CrossOrigin(origins ="http://localhost:5000")
	@PostMapping("/withdraw")
	public ResponseEntity<?> withdraw(@RequestHeader(name="Authorization")String token,@RequestBody OneWayTransactionDTO transactionDTO){
		try {
			TransactionStatus withdraw = service.withdraw(token, transactionDTO);
			return new ResponseEntity<>(withdraw,HttpStatus.OK);
		} catch (InsufficientBalanceException e) {
			return new ResponseEntity<>("INSUFFICIENT_BALANCE",HttpStatus.BAD_REQUEST);
		} catch (InvalidAccessException e) {
			return new ResponseEntity<>("UNAUTHORIZED_ACCESS",HttpStatus.UNAUTHORIZED);
		} catch(FeignClientException e) {
			String[] message = e.getMessage().split(" ");
			int errCode = Integer.parseInt(message[0].split("")[1]+message[0].split("")[2]+message[0].split("")[3]);
			return new ResponseEntity<>(message[5],HttpStatus.valueOf(errCode));
		}
	}
	
	@PostMapping("/transfer")
	@CrossOrigin(origins ="http://localhost:5000")
	public ResponseEntity<?> transfer(@RequestHeader(name="Authorization")String token,@RequestBody TransferDTO transferDTO){
		try {
			TransferSuccessMessage transfer = service.transfer(token, transferDTO);
			return new ResponseEntity<>(transfer,HttpStatus.OK);
		} catch (InsufficientBalanceException e) {
			return new ResponseEntity<>("INSUFFICIENT_BALANCE",HttpStatus.BAD_REQUEST);
		} catch (InvalidAccessException e) {
			return new ResponseEntity<>("UNAUTHORIZED_ACCESS",HttpStatus.UNAUTHORIZED);
		} catch(FeignClientException e) {
			String[] message = e.getMessage().split(" ");
			int errCode = Integer.parseInt(message[0].split("")[1]+message[0].split("")[2]+message[0].split("")[3]);
			return new ResponseEntity<>(message[5],HttpStatus.valueOf(errCode));
		} 
	}
	
	@GetMapping("/get-trasactions/{accountId}")
	@CrossOrigin(origins = "http://localhost:5000")
	public ResponseEntity<?> getTransactions(@RequestHeader(name="Authorization")String token,@PathVariable String accountId){
		try {
			List<TransactionHistory> transactionHistory = service.transactionHistory(token, accountId);
			return new ResponseEntity<>(transactionHistory,HttpStatus.OK);
		} catch (InvalidAccessException e) {
			// TODO Auto-generated catch block
			return new ResponseEntity<>("UNAUTHORIZED_ACCESS",HttpStatus.UNAUTHORIZED);
		} catch (InvalidAccountIdException e) {
			// TODO Auto-generated catch block
			return new ResponseEntity<>("ACCOUNT_NOT_FOUND",HttpStatus.BAD_REQUEST);
		} catch(FeignClientException e) {
			String[] message = e.getMessage().split(" ");
			int errCode = Integer.parseInt(message[0].split("")[1]+message[0].split("")[2]+message[0].split("")[3]);
			return new ResponseEntity<>(message[5],HttpStatus.valueOf(errCode));
		} 
	}
	

}
