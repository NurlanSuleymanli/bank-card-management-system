package com.nurlansuleymanli.cardservice.mapper;

import com.nurlansuleymanli.cardservice.entity.CardEntity;
import com.nurlansuleymanli.cardservice.modul.dto.response.CreateCardResponse;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CardMapper {

    CreateCardResponse toCreateCardResponse(CardEntity cardEntity);

}
