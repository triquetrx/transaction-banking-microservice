package com.cognizant.transaction.clients;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

import com.cognizant.transaction.dto.Account;
import com.cognizant.transaction.dto.TransactionDTO;
import com.cognizant.transaction.dto.TransactionStatus;

@FeignClient(name="account-client",url="http://localhost:8003")
public interface AccountClient {
	
	@PostMapping("/deposit")
	public TransactionStatus deposit(@RequestHeader(name="Authorization")String token,@RequestBody TransactionDTO transactionDTO);
	
	@PostMapping("/withdraw")
	public TransactionStatus withdraw(@RequestHeader(name="Authorization")String token,@RequestBody TransactionDTO transactionDTO);
	
	@GetMapping("/get-accounts/{accountId}")
	public Account getAccounts(@RequestHeader(name="Authorization")String token,@PathVariable String accountId);

}
