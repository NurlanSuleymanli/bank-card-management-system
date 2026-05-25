package com.nurlansuleymanli.cardservice.repository;

import com.fasterxml.jackson.annotation.OptBoolean;
import com.nurlansuleymanli.cardservice.entity.CardEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CardRepository extends JpaRepository<CardEntity, Long> {


    int countCardEntitiesByCustomerId(Long customerId);


    Optional<CardEntity> getCardEntityById(Long id);
}
