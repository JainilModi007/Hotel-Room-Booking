package com.upgrad.Payment.dao;

import com.upgrad.Payment.entities.TransactionDetailsEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TransactionDao extends JpaRepository<TransactionDetailsEntity,Integer> {

}
