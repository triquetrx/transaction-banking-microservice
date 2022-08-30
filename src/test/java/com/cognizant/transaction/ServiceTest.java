package com.cognizant.transaction;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.cognizant.transaction.service.TransactionService;

@SpringBootTest
@RunWith(SpringRunner.class)
public class ServiceTest {

	TransactionService transactionService;
	
	@Test
	void testServiceLoad() {
		assertThat(transactionService).isNull();
	}
	
}
