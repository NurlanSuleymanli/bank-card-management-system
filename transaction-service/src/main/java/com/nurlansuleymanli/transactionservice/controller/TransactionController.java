package com.nurlansuleymanli.transactionservice.controller;

import com.nurlansuleymanli.transactionservice.model.dto.request.TransactionRequest;
import com.nurlansuleymanli.transactionservice.model.dto.response.TransactionResponse;
import com.nurlansuleymanli.transactionservice.model.enums.TransactionStatus;
import com.nurlansuleymanli.transactionservice.service.TransactionService;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.domain.Page;
import org.springframework.data.web.config.EnableSpringDataWebSupport;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.time.LocalDateTime;

import static org.springframework.data.web.config.EnableSpringDataWebSupport.PageSerializationMode.VIA_DTO;

@RestController
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@EnableSpringDataWebSupport(pageSerializationMode = VIA_DTO)
@RequestMapping("/api/v1/transactions")
public class TransactionController {

    TransactionService transactionService;

    @PostMapping
    public ResponseEntity<TransactionResponse> createTransaction(
            @RequestBody @Valid TransactionRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(transactionService.createTransaction(request));
    }

    @GetMapping("/{id}")
    public ResponseEntity<TransactionResponse> getTransaction(@PathVariable Long id){
        return ResponseEntity.ok(transactionService.getTransaction(id));
    }

    @PostMapping("/{id}")
    public ResponseEntity<Void> setStatus(@PathVariable Long id,
                                          @RequestBody @Valid TransactionStatus transactionStatus){
        transactionService.setStatus(id,transactionStatus);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/card/{cardId}")
    public ResponseEntity<Page<TransactionResponse>> getCardTransactions(@PathVariable Long cardId,
                                                                         @RequestParam(defaultValue = "0") int page,
                                                                         @RequestParam(defaultValue = "10") int size){

        return ResponseEntity.ok(transactionService.getCardTransactions(cardId,page,size));
    }

    @GetMapping("/card/{cardId}/filter")
    public ResponseEntity<Page<TransactionResponse>> getCardTransactionsWithFiltering(@PathVariable Long cardId,
                                                                                      @RequestParam(defaultValue = "0") int page,
                                                                                      @RequestParam(defaultValue = "10") int size,
                                                                                      @RequestParam LocalDateTime from,
                                                                                      @RequestParam LocalDateTime to){
        return ResponseEntity.ok(transactionService.getCardTransactionsWithFiltering(cardId,page,size,from,to));
    }

}

