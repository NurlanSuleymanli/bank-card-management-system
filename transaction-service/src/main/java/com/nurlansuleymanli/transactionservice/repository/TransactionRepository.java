package com.nurlansuleymanli.transactionservice.repository;

import com.nurlansuleymanli.transactionservice.entity.TransactionEntity;
import com.nurlansuleymanli.transactionservice.model.dto.response.TransactionResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;

@Repository
public interface TransactionRepository extends JpaRepository<TransactionEntity, Long> {


    Page<TransactionEntity> findByCardId(Long cardId, Pageable pageable);

    Page<TransactionEntity> findByCardIdAndTransactionDateIsBetween
            (Long cardId, LocalDateTime from, LocalDateTime to, Pageable pageable);
}
