package com.cognizant.transaction;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.cognizant.transaction.clients.AccountClient;
import com.cognizant.transaction.clients.AuthClient;
import com.cognizant.transaction.clients.RulesClient;

@SpringBootTest
@RunWith(SpringRunner.class)
public class ClientTest {

	AuthClient authClient;
	AccountClient accountClient;
	RulesClient rulesClient;

	@Test
	void testAuthClientLoad() {
		assertThat(authClient).isNull();
	}

	@Test
	void testTransactionClientLoad() {
		assertThat(accountClient).isNull();
	}

	@Test
	void testCustomerClientLoad() {
		assertThat(rulesClient).isNull();
	}

}
