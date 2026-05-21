package com.nurlansuleymanli.cardservice.service;


import com.nurlansuleymanli.cardservice.client.CustomerServiceClient;
import com.nurlansuleymanli.cardservice.modul.request.CreateCardRequest;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class CardService {

    CustomerServiceClient serviceClient;

    public CreateCardResponse createCard(CreateCardRequest request){

        HttpEntity<?> http = (HttpEntity<?>) serviceClient.getCustomer(request.getCustomerId());

        

}

}
