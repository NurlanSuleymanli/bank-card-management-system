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

        return cardRepository.getCardEntitiesByCustomerIdAndStatusIn(customerId, List.of(Status.ACTIVE,Status.BLOCKED, Status.EXPIRED)).stream()
                .map(cardMapper::toCardResponse)
                .toList();

    }

    public void blockCard(Long cardId){
        CardEntity card = cardRepository.getCardEntityById(cardId)
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
        CardEntity card = cardRepository.getCardEntityById(cardId)
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
        CardEntity card = cardRepository.getCardEntityById(cardId)
                .orElseThrow(()->new CardNotFoundException("Card not found!"));

        if (card.getStatus() == Status.CLOSED || card.getStatus() == Status.EXPIRED) {
            throw new UnsupportedCardOperationException("Cannot update limit of closed/expired card!");
        }
        if (!card.getCardType().equals(CardType.CREDIT)) {
            throw new UnsupportedCardOperationException("Credit limit can only be set for CREDIT cards!");
        }

            card.setCreditLimit(BigDecimal.valueOf(30000));
            cardRepository.save(card);

    }

}
