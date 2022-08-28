package com.cognizant.transaction.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

public @Data @NoArgsConstructor @AllArgsConstructor class Account {
	
	private String accountId;
	private String accountType;
	private String customerId;
	private double balance;

}
