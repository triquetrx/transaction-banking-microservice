package com.cognizant.transaction.model;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Id;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
public @Data @NoArgsConstructor @AllArgsConstructor class TransactionHistory {
	
	
	@Id
	private String transactionId;
	private String accountId;
	private double amount;
	private String narration;
	private String paymentMethod;
	private String toAccount;
	private Date transactionDate;
	private String transactionType;

}
