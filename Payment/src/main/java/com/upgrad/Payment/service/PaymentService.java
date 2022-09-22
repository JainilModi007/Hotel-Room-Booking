package com.upgrad.Payment.service;

import com.upgrad.Payment.entities.TransactionDetailsEntity;

public interface PaymentService {

    public TransactionDetailsEntity createTransaction(TransactionDetailsEntity transactionDetailsEntity);
    public TransactionDetailsEntity getTransaction(int id);
}
