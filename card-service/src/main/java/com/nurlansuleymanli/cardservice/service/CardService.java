package com.nurlansuleymanli.cardservice.service;


import com.nurlansuleymanli.cardservice.client.CustomerServiceClient;
import com.nurlansuleymanli.cardservice.client.TransactionServiceClient;
import com.nurlansuleymanli.cardservice.entity.CardEntity;
import com.nurlansuleymanli.cardservice.exception.CardLimitExceededException;
import com.nurlansuleymanli.cardservice.exception.CardNotFoundException;
import com.nurlansuleymanli.cardservice.exception.CardNumberGeneratorException;
import com.nurlansuleymanli.cardservice.exception.UnsupportedCardOperationException;
import com.nurlansuleymanli.cardservice.mapper.CardMapper;
import com.nurlansuleymanli.cardservice.modul.dto.request.CardDepositRequest;
import com.nurlansuleymanli.cardservice.modul.dto.request.CardPaymentRequest;
import com.nurlansuleymanli.cardservice.modul.dto.request.CreateCardRequest;
import com.nurlansuleymanli.cardservice.modul.dto.request.TransactionRequest;
import com.nurlansuleymanli.cardservice.modul.dto.response.*;
import com.nurlansuleymanli.cardservice.modul.enums.*;
import com.nurlansuleymanli.cardservice.repository.CardRepository;
import com.nurlansuleymanli.cardservice.util.CardNumberGenerator;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Transactional
public class CardService {

    PasswordEncoder passwordEncoder;
    CustomerServiceClient serviceClient;
    TransactionServiceClient transactionServiceClient;
    CardRepository cardRepository;
    CardMapper cardMapper;

    public CreateCardResponse createCard(CreateCardRequest request){

      CustomerInfoResponse customer = serviceClient.getCustomer(request.getCustomerId());

      if((cardRepository.countCardEntitiesByCustomerIdAndStatusIn(customer.getId(),List.of(Status.ACTIVE,Status.BLOCKED)))>=3) {
          throw new CardLimitExceededException(" Card limit exceeded!");
      }

              CardEntity card = CardEntity.builder()
                      .cardNumber(CardNumberGenerator.generate())
                      .cvv(passwordEncoder.encode(CardNumberGenerator.generateCvv()))
                      .customerId(customer.getId())
                      .cardType(request.getCardType())
                      .status(Status.ACTIVE)
                      .balance(BigDecimal.valueOf(0))
                      .creditLimit(request.getCardType()==CardType.CREDIT ? BigDecimal.valueOf(30000): null)
                      .expiryDate(LocalDate.now().plusYears(3))
                      .build();
        for (int i = 0; i < 5; i++) {
            if(!cardRepository.existsCardEntitiesByCardNumber(card.getCardNumber())){
                break;
            }
            card.setCardNumber(CardNumberGenerator.generate());
        }

        if(cardRepository.existsCardEntitiesByCardNumber(card.getCardNumber())){
            throw new CardNumberGeneratorException("Card number generate is failed, please try again!");
        }

              cardRepository.save(card);

              return cardMapper.toCreateCardResponse(card);


}


    public CardResponse getCard(Long cardId){

        CardEntity card = cardRepository.getCardEntityByIdAndStatusIn(cardId,List.of(Status.ACTIVE,Status.BLOCKED)).orElseThrow(()-> new CardNotFoundException("Card not found!"));

        return cardMapper.toCardResponse(card);

    }


    public List<CardResponse> getAllCardsByCustomer(Long customerId){

        return cardRepository.getCardEntitiesByCustomerIdAndStatusIn(customerId, List.of(Status.ACTIVE,Status.BLOCKED, Status.EXPIRED)).stream()
                .map(cardMapper::toCardResponse)
                .toList();
    }

    public void blockCard(Long cardId){
        CardEntity card = cardRepository.findById(cardId)
                .orElseThrow(()-> new CardNotFoundException("Card not found!"));

        if (card.getStatus() == Status.BLOCKED) {
            throw new UnsupportedCardOperationException("Card is already blocked!");
        }
        if (card.getStatus() == Status.CLOSED || card.getStatus() == Status.EXPIRED) {
            throw new UnsupportedCardOperationException("Cannot block a closed or expired card!");
        }

        card.setStatus(Status.BLOCKED);
        cardRepository.save(card);
    }

    public void activateCard(Long cardId){
        CardEntity card = cardRepository.findById(cardId)
                .orElseThrow(()-> new CardNotFoundException("Card not found!"));

        if (card.getStatus() == Status.ACTIVE) {
            throw new UnsupportedCardOperationException("Card is already active!");
        }
        if (card.getStatus() == Status.CLOSED || card.getStatus() == Status.EXPIRED) {
            throw new UnsupportedCardOperationException("Cannot activate a closed or expired card!");
        }
        if (card.getExpiryDate().isBefore(LocalDate.now())) {
            card.setStatus(Status.EXPIRED);
            cardRepository.save(card);
            throw new UnsupportedCardOperationException("Card has expired. Please request a new card.");
        }

        card.setStatus(Status.ACTIVE);
        cardRepository.save(card);
    }

    public void refreshLimit(Long cardId){
        CardEntity card = cardRepository.findById(cardId)
                .orElseThrow(()->new CardNotFoundException("Card not found!"));

        if (card.getStatus() == Status.CLOSED || card.getStatus() == Status.EXPIRED) {
            throw new UnsupportedCardOperationException("Cannot update limit of closed/expired card!");
        }
        if (!(card.getCardType() == CardType.CREDIT)) {
            throw new UnsupportedCardOperationException("Credit limit can only be set for CREDIT cards!");
        }

            card.setCreditLimit(BigDecimal.valueOf(30000));
            cardRepository.save(card);

    }

    public CardPaymentResponse payment(Long cardId, CardPaymentRequest request){

        CardEntity card = cardRepository.findById(cardId).orElseThrow(()-> new CardNotFoundException("Card not found!"));

        TransactionRequest transactionRequest = TransactionRequest.builder()
                .cardId(cardId)
                .amount(request.getAmount())
                .type(TransactionType.PAYMENT)
                .description("Payment")
                .transactionDate(LocalDateTime.now())
                .build();

        TransactionResponse transactionResponse = transactionServiceClient.createTransaction(transactionRequest);

        if (card.getExpiryDate().isBefore(LocalDate.now())) {
            card.setStatus(Status.EXPIRED);
            cardRepository.save(card);
            transactionServiceClient.setStatus(transactionResponse.getId(),TransactionStatus.FAILED);
            throw new UnsupportedCardOperationException("Card has expired!");
        }

        if(card.getStatus()==Status.ACTIVE) {

            if (card.getCardType() == CardType.DEBIT) {
                if (card.getBalance().compareTo(request.getAmount()) < 0) {
                    transactionServiceClient.setStatus(transactionResponse.getId(),TransactionStatus.FAILED);
                    throw new UnsupportedCardOperationException("The payment amount is more than the balance!");
                }

                card.setBalance(card.getBalance().subtract(request.getAmount()));
                cardRepository.save(card);
                transactionServiceClient.setStatus(transactionResponse.getId(),TransactionStatus.SUCCESS);
                return CardPaymentResponse.builder()
                        .message("The payment was successfully carried out!")
                        .status(PaymentStatus.SUCCESS)
                        .dateTime(LocalDateTime.now())
                        .build();

            }
            if (card.getCardType() == CardType.CREDIT) {
                if (card.getCreditLimit().compareTo(request.getAmount()) < 0) {
                    transactionServiceClient.setStatus(transactionResponse.getId(),TransactionStatus.FAILED);
                    throw new UnsupportedCardOperationException("Insufficient credit limit!");
                }
                card.setBalance(card.getBalance().subtract(request.getAmount()));
                card.setCreditLimit(card.getCreditLimit().subtract(request.getAmount()));
                cardRepository.save(card);
                transactionServiceClient.setStatus(transactionResponse.getId(),TransactionStatus.SUCCESS);
                return CardPaymentResponse.builder()
                        .message("The payment was successfully carried out!")
                        .status(PaymentStatus.SUCCESS)
                        .dateTime(LocalDateTime.now())
                        .build();
            }
        }
        else{
            transactionServiceClient.setStatus(transactionResponse.getId(),TransactionStatus.FAILED);
            throw new UnsupportedCardOperationException("Payment is only made through active cards!");
        }
            return CardPaymentResponse.builder()
                    .status(PaymentStatus.FAILED)
                    .message("The payment was unsuccessful!")
                    .dateTime(LocalDateTime.now())
                    .build();
    }

    public CardDepositResponse deposit(Long cardId, CardDepositRequest request){

        CardEntity card = cardRepository.findById(cardId).orElseThrow(()-> new CardNotFoundException("Card not found!"));
        TransactionRequest transactionRequest = TransactionRequest.builder()
                .cardId(cardId)
                .amount(request.getAmount())
                .type(TransactionType.TOP_UP)
                .description("Deposit")
                .transactionDate(LocalDateTime.now())
                .build();

        TransactionResponse transactionResponse = transactionServiceClient.createTransaction(transactionRequest);

        if (card.getExpiryDate().isBefore(LocalDate.now())) {
            card.setStatus(Status.EXPIRED);
            cardRepository.save(card);
            transactionServiceClient.setStatus(transactionResponse.getId(),TransactionStatus.FAILED);
            throw new UnsupportedCardOperationException("Card has expired!");
        }

        if(card.getStatus()!=Status.ACTIVE){
            transactionServiceClient.setStatus(transactionResponse.getId(),TransactionStatus.FAILED);
            throw new UnsupportedCardOperationException("Deposit is only made through active cards!");
        }

        card.setBalance(card.getBalance().add(request.getAmount()));
        cardRepository.save(card);
        transactionServiceClient.setStatus(transactionResponse.getId(),TransactionStatus.SUCCESS);

        return CardDepositResponse.builder()
                .message("Balance successfully topped up!")
                .newBalance(card.getBalance())
                .dateTime(LocalDateTime.now())
                .build();
    }

    public void closeCard(Long cardId){
        CardEntity card = cardRepository.findById(cardId)
                .orElseThrow(()-> new CardNotFoundException("Card not found!"));

        if (card.getStatus() == Status.CLOSED) {
            throw new UnsupportedCardOperationException("Card is already closed!");
        }

        card.setStatus(Status.CLOSED);
        cardRepository.save(card);
    }

}
