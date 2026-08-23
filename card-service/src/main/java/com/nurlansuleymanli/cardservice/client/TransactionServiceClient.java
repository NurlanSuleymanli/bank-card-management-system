package com.nurlansuleymanli.cardservice.client;

import com.nurlansuleymanli.cardservice.modul.dto.request.TransactionRequest;
import com.nurlansuleymanli.cardservice.modul.dto.response.TransactionResponse;
import com.nurlansuleymanli.cardservice.modul.enums.TransactionStatus;
import jakarta.validation.Valid;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "transaction-service" , url = "${feign.client.config.transaction-url}")
public interface TransactionServiceClient {

@PostMapping
TransactionResponse createTransaction(@RequestBody @Valid TransactionRequest request);

@PatchMapping("/{id}")
    void setStatus(@PathVariable Long id, @RequestParam TransactionStatus transactionStatus);
}
