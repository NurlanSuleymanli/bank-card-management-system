package com.nurlansuleymanli.cardservice.service;


import com.nurlansuleymanli.cardservice.client.CustomerServiceClient;
import com.nurlansuleymanli.cardservice.entity.CardEntity;
import com.nurlansuleymanli.cardservice.exception.CardLimitExceededException;
import com.nurlansuleymanli.cardservice.exception.CardNotFoundException;
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

      if((cardRepository.countCardEntitiesByCustomerId(customer.getId()))<3) {

          if (request.getCardType().equals(CardType.DEBIT)) {

              CardEntity card = CardEntity.builder()
                      .cardNumber(CardNumberGenerator.generate())
                      .cvv(passwordEncoder.encode(CardNumberGenerator.generateCvv()))
                      .customerId(customer.getId())
                      .cardType(CardType.DEBIT)
                      .status(Status.ACTIVE)
                      .balance(BigDecimal.valueOf(0))
                      .expiryDate(LocalDate.now().plusYears(3))
                      .build();

              return cardMapper.toCreateCardResponse(card);


          }

          if(request.getCardType().equals(CardType.CREDIT)){

              CardEntity card = CardEntity.builder()
                      .cardNumber(CardNumberGenerator.generate())
                      .cvv(passwordEncoder.encode(CardNumberGenerator.generateCvv()))
                      .customerId(customer.getId())
                      .cardType(CardType.CREDIT)
                      .creditLimit(BigDecimal.valueOf(30000))
                      .status(Status.ACTIVE)
                      .balance(BigDecimal.valueOf(0))
                      .expiryDate(LocalDate.now().plusYears(3))
                      .build();

              return cardMapper.toCreateCardResponse(card);

          }

      }
          throw new CardLimitExceededException(" Card limit exceeded!");
}


    public CardResponse getCard(Long cardId){

        CardEntity card = cardRepository.getCardEntityById(cardId).orElseThrow(()-> new CardNotFoundException("Card not found!"));

        return cardMapper.toCardResponse(card);

    }


}
