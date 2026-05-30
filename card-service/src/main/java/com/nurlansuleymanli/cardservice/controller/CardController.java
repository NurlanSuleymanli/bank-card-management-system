package com.nurlansuleymanli.cardservice.controller;

import com.nurlansuleymanli.cardservice.modul.dto.request.CardPaymentRequest;
import com.nurlansuleymanli.cardservice.modul.dto.request.CreateCardRequest;
import com.nurlansuleymanli.cardservice.modul.dto.response.CardPaymentResponse;
import com.nurlansuleymanli.cardservice.modul.dto.response.CardResponse;
import com.nurlansuleymanli.cardservice.modul.dto.response.CreateCardResponse;
import com.nurlansuleymanli.cardservice.service.CardService;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/cards")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class CardController {

    CardService cardService;

    @PostMapping
    public ResponseEntity<CreateCardResponse> createCard(@Valid @RequestBody CreateCardRequest request){
        return ResponseEntity.status(HttpStatus.CREATED).body(cardService.createCard(request));
    }

    @GetMapping("/{cardId}")
    public ResponseEntity<CardResponse> getCard(@PathVariable Long cardId){
        return ResponseEntity.ok(cardService.getCard(cardId));
    }

    @GetMapping("/customer/{customerId}")
    public ResponseEntity<List<CardResponse>> getAllCardsByCustomer(@PathVariable Long customerId){
        return ResponseEntity.ok(cardService.getAllCardsByCustomer(customerId));
    }

    @PatchMapping("/{cardId}/block")
    public ResponseEntity<Void> blockCard(@PathVariable Long cardId){
        cardService.blockCard(cardId);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{cardId}/activate")
    public ResponseEntity<Void> activateCard(@PathVariable Long cardId){
        cardService.activateCard(cardId);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{cardId}/limit")
    public ResponseEntity<Void> refreshLimit(@PathVariable Long cardId){
        cardService.refreshLimit(cardId);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{cardId}/payment")
    public ResponseEntity<CardPaymentResponse> payment(@PathVariable Long cardId,
                                                       @Valid @RequestBody CardPaymentRequest request){
        return ResponseEntity.ok(cardService.payment(cardId,request));
    }

}
