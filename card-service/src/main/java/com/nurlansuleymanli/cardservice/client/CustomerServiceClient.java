package com.nurlansuleymanli.cardservice.client;


import com.nurlansuleymanli.cardservice.modul.dto.response.CustomerInfoResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "customer-service" , url = "${feign.client.config.customer-url}")
public interface CustomerServiceClient {

    @GetMapping("/customers/{id}")
    CustomerInfoResponse getCustomer(@PathVariable Long id);

}
