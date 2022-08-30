package com.cognizant.transaction;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.cognizant.transaction.dto.OneWayTransactionDTO;
import com.cognizant.transaction.dto.TransactionStatus;
import com.cognizant.transaction.dto.TransferDTO;
import com.cognizant.transaction.dto.TransferSuccessMessage;

@SpringBootTest
@RunWith(SpringRunner.class)
public class DTOTest {
	
	OneWayTransactionDTO oneWayTransactionDTO;
	TransferDTO transferDTO;
	TransferSuccessMessage transferSuccessMessage;
	TransactionStatus transactionStatus;
	
	@Test
	void testOneWayTransaction() {
		oneWayTransactionDTO = new OneWayTransactionDTO("ACC0", "TEST", "DEPOSIT", 1000);
		assertEquals("ACC0", oneWayTransactionDTO.getAccountId());
		assertEquals("TEST", oneWayTransactionDTO.getNarration());
		assertEquals("DEPOSIT", oneWayTransactionDTO.getTransactionType());
		assertEquals(1000, oneWayTransactionDTO.getAmount());
	}
	
	@Test
	void testTransfer() {
		transferDTO = new TransferDTO("ACC0", "ACC1", "TEST", "TRANSFER", 2000);
		assertEquals("ACC0", transferDTO.getFromAccountId());
		assertEquals("ACC1", transferDTO.getToAccountId());
		assertEquals("TEST", transferDTO.getNarration());
		assertEquals("TRANSFER", transferDTO.getTransactionType());
		assertEquals(2000, transferDTO.getAmount());
	}
	
	@Test
	void testTransferSuccess() {
		transferSuccessMessage = new TransferSuccessMessage("ACC0", "ACC1", 1000);
		assertEquals("ACC0", transferSuccessMessage.getFromAccount());
		assertEquals("ACC1", transferSuccessMessage.getToAccount());
		assertEquals(1000, transferSuccessMessage.getSourceBalance());
	}
	
	@Test
	void testTransactionStatus() {
		transactionStatus = new TransactionStatus("TEST_MESSAGE", 1000);
		assertEquals("TEST_MESSAGE", transactionStatus.getMessage());
		assertEquals(1000, transactionStatus.getBalance());
	}

}
