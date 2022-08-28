package com.cognizant.transaction.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

public @Data @AllArgsConstructor @NoArgsConstructor class TransferSuccessMessage {
	
	private String fromAccount;
	private String toAccount;
	private double sourceBalance;

}
