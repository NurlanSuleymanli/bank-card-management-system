package com.nurlansuleymanli.transactionservice.mapper;


import com.nurlansuleymanli.transactionservice.entity.TransactionEntity;
import com.nurlansuleymanli.transactionservice.model.dto.response.TransactionResponse;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface TransactionMapper {

    TransactionResponse toTransactionResponse(TransactionEntity transactionEntity);

}
