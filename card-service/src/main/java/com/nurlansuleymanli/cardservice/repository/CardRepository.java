package com.nurlansuleymanli.cardservice.repository;

import com.nurlansuleymanli.cardservice.entity.CardEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CardRepository extends JpaRepository<CardEntity, Long> {


    int countCardEntitiesByCustomerId(Long customerId);
    


}
