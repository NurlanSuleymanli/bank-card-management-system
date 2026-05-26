package com.nurlansuleymanli.cardservice.service;


import com.nurlansuleymanli.cardservice.client.CustomerServiceClient;
import com.nurlansuleymanli.cardservice.entity.CardEntity;
import com.nurlansuleymanli.cardservice.exception.CardLimitExceededException;
import com.nurlansuleymanli.cardservice.exception.CardNotFoundException;
import com.nurlansuleymanli.cardservice.exception.UnsupportedCardOperationException;
import com.nurlansuleymanli.cardservice.mapper.CardMapper;
import com.nurlansuleymanli.cardservice.modul.dto.request.CreateCardRequest;
import com.nurlansuleymanli.cardservice.modul.dto.response.CardResponse;
import com.nurlansuleymanli.cardservice.modul.dto.response.CreateCardResponse;
import com.nurlansuleymanli.cardservice.modul.dto.response.CustomerInfoResponse;
import com.nurlansuleymanli.cardservice.modul.enums.CardType;
import com.nurlansuleymanli.cardservice.modul.enums.Status;
import com.nurlansuleymanli.cardservice.repository.CardRepository;
import com.nurlansuleymanli.cardservice.util.CardNumberGenerator;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class CardService {

    PasswordEncoder passwordEncoder;
    CustomerServiceClient serviceClient;
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

              cardRepository.save(card);

              return cardMapper.toCreateCardResponse(card);


}


    public CardResponse getCard(Long cardId){

        CardEntity card = cardRepository.getCardEntityByIdAndStatusIn(cardId,List.of(Status.ACTIVE,Status.BLOCKED)).orElseThrow(()-> new CardNotFoundException("Card not found!"));

        return cardMapper.toCardResponse(card);

    }


    public List<CardResponse> getAllCardsByCustomer(Long customerId){
        CustomerInfoResponse customer = serviceClient.getCustomer(customerId);

        return cardRepository.getCardEntitiesByCustomerIdAndStatusIn(customer.getId(), List.of(Status.ACTIVE,Status.BLOCKED)).stream()
                .map(cardMapper::toCardResponse)
                .toList();

    }

    public void blockCard(Long cardId){
        CardEntity card = cardRepository.getCardEntityByIdAndStatus(cardId,Status.ACTIVE)
                .orElseThrow(()-> new CardNotFoundException("Card not found!"));

        card.setStatus(Status.BLOCKED);
        cardRepository.save(card);
    }

    public void activateCard(Long cardId){
        CardEntity card = cardRepository.getCardEntityByIdAndStatus(cardId,Status.BLOCKED)
                .orElseThrow(()-> new CardNotFoundException("Card not found!"));

        card.setStatus(Status.ACTIVE);
        cardRepository.save(card);
    }

    public void refreshLimit(Long cardId){
        CardEntity card = cardRepository.getCardEntityByIdAndStatus(cardId, Status.ACTIVE)
                .orElseThrow(()->new CardNotFoundException("Card not found!"));

        if (card.getCardType().equals(CardType.CREDIT)){
            card.setCreditLimit(BigDecimal.valueOf(30000));
            cardRepository.save(card);
            return;
        }

        throw new UnsupportedCardOperationException("Must have a credit card only!");

    }

}
