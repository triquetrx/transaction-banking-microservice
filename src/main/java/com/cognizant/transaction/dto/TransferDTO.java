package com.cognizant.transaction.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

public @Data @AllArgsConstructor @NoArgsConstructor class TransferDTO {
	
	private String fromAccountId;
	private String toAccountId;
	private String narration;
	private String transactionType;
	private double amount;

}
