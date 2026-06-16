package com.nurlansuleymanli.transactionservice.service;

import com.nurlansuleymanli.transactionservice.entity.TransactionEntity;
import com.nurlansuleymanli.transactionservice.exception.TransactionNotFoundException;
import com.nurlansuleymanli.transactionservice.exception.WrongDateTimeException;
import com.nurlansuleymanli.transactionservice.mapper.TransactionMapper;
import com.nurlansuleymanli.transactionservice.model.dto.request.TransactionRequest;
import com.nurlansuleymanli.transactionservice.model.dto.response.TransactionResponse;
import com.nurlansuleymanli.transactionservice.model.enums.TransactionStatus;
import com.nurlansuleymanli.transactionservice.repository.TransactionRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;


@Service
@Transactional
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class TransactionService {

    TransactionRepository transactionRepository;
    TransactionMapper transactionMapper;

    public TransactionResponse createTransaction(TransactionRequest request) {
        TransactionEntity entity = TransactionEntity.builder()
                .cardId(request.getCardId())
                .amount(request.getAmount())
                .type(request.getType())
                .status(TransactionStatus.PENDING)
                .description(request.getDescription())
                .transactionDate(request.getTransactionDate())
                .build();

        transactionRepository.save(entity);

        return transactionMapper.toTransactionResponse(entity);
    }

    public TransactionResponse getTransaction(Long id){

        return transactionMapper.toTransactionResponse(transactionRepository.findById(id)
                .orElseThrow(()->new TransactionNotFoundException("Transaction not found!")));

    }

    public Page<TransactionResponse> getCardTransactions(Long cardId, int page, int size){

        Pageable pageable = PageRequest.of(page,size);

        return transactionRepository.findByCardId(cardId,pageable)
                .map(transactionMapper::toTransactionResponse);
    }

    public Page<TransactionResponse> getCardTransactionsWithFiltering(Long cardId, int page, int size, LocalDateTime from, LocalDateTime to){

        if(!from.isBefore(to)){
            throw new WrongDateTimeException("From time must be before to time!");
        }

        Pageable pageable = PageRequest.of(page,size);

        return transactionRepository.findByCardIdAndTransactionDateIsBetween(cardId, from, to, pageable).map(transactionMapper::toTransactionResponse);
    }

}

