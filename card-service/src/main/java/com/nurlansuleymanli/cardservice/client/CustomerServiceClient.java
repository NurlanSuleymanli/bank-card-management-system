package com.nurlansuleymanli.cardservice.client;


import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "customer-service" , url = "${feign.client.config.url")
public interface CustomerServiceClient {

    @GetMapping("/customers/{id}")
    ResponseEntity<?> getCustomer(@PathVariable Long id);

}
