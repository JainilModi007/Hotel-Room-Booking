package com.upgrad.Payment.service;

import com.upgrad.Payment.dao.TransactionDao;
import com.upgrad.Payment.entities.TransactionDetailsEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PaymentServiceImpl implements PaymentService{

    TransactionDao transactionDetailsEntityDAO;

    @Autowired
    public PaymentServiceImpl(TransactionDao transactionDetailsEntityDAO){
        this.transactionDetailsEntityDAO = transactionDetailsEntityDAO;
    }

    // service for end point 1
    @Override
    public TransactionDetailsEntity createTransaction(TransactionDetailsEntity transactionDetailsEntity) {
        return transactionDetailsEntityDAO.save(transactionDetailsEntity);
    }

    // service for end point 2
    @Override
    public TransactionDetailsEntity getTransaction(int id) {
        return transactionDetailsEntityDAO.getById(id);
    }
}

