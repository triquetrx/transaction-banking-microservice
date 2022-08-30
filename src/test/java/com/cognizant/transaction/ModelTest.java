package com.cognizant.transaction;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.cognizant.transaction.model.TransactionHistory;

@SpringBootTest
@RunWith(SpringRunner.class)
public class ModelTest {
	
	TransactionHistory transactionHistory;
	
	@Test
	void testTransactionHistory() {
		transactionHistory = new TransactionHistory();
		transactionHistory.setAccountId("ACC0");
		assertEquals("ACC0", transactionHistory.getAccountId());
		transactionHistory.setAmount(1000);
		assertEquals(1000, transactionHistory.getAmount());
		transactionHistory.setNarration("TEST");
		assertEquals("TEST", transactionHistory.getNarration());
		transactionHistory.setPaymentMethod("CASH");
		assertEquals("CASH", transactionHistory.getPaymentMethod());
		transactionHistory.setToAccount("ACC1");
		assertEquals("ACC1", transactionHistory.getToAccount());
		transactionHistory.setTransactionId("TR1");
		assertEquals("TR1", transactionHistory.getTransactionId());
		transactionHistory.setTransactionType("TRANSFER");
		assertEquals("TRANSFER", transactionHistory.getTransactionType());
	}

}
