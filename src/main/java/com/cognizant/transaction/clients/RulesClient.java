package com.cognizant.transaction.clients;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;

import com.cognizant.transaction.dto.RuleStatus;

@FeignClient(name="rules-microservice",url = "http://localhost:8005")
public interface RulesClient {
	
	@GetMapping("/evaluate-min-balance/{balance}/{accountType}")
	public RuleStatus evaluateMinBalance(@RequestHeader(name="Authorization") String token,@PathVariable double balance, @PathVariable String accountType);

	@GetMapping("/get-service-charges/{accountType}")
	public double getServiceCharges(@PathVariable String accountType);
	
}
