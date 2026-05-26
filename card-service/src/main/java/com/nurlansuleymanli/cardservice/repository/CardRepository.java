package com.nurlansuleymanli.cardservice.repository;

import com.fasterxml.jackson.annotation.OptBoolean;
import com.nurlansuleymanli.cardservice.entity.CardEntity;
import com.nurlansuleymanli.cardservice.modul.enums.Status;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Repository
public interface CardRepository extends JpaRepository<CardEntity, Long> {


    int countCardEntitiesByCustomerIdAndStatusIn(Long customerId, Collection<Status> statuses);

    Optional<CardEntity> getCardEntityByIdAndStatusIn(Long id, Collection<Status> statuses);

    List<CardEntity> getCardEntitiesByCustomerIdAndStatusIn(Long customerId, Collection<Status> statuses);

    Optional<CardEntity> getCardEntityById(Long id);
    
}
