package com.upgrad.Payment.controller;


import com.upgrad.Payment.dto.TransactionDto;
import com.upgrad.Payment.entities.TransactionDetailsEntity;
import org.modelmapper.ModelMapper;
import com.upgrad.Payment.service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/payment")
public class PaymentController {
    ModelMapper modelMapper;
    PaymentService paymentService;

    //autowire payment service and model mapper
    @Autowired
    public PaymentController(ModelMapper modelMapper,PaymentService paymentService){
        this.modelMapper=modelMapper;
        this.paymentService=paymentService;
    }


    // end point 1
    @PostMapping(value = "/transaction")
    public ResponseEntity<TransactionDto> makeTransaction(@RequestBody TransactionDto transactionDetailsEntityDTO){

        TransactionDetailsEntity transactionDetailsEntity =modelMapper.map(transactionDetailsEntityDTO,TransactionDetailsEntity.class);
        TransactionDetailsEntity savedTransactionDetailsEntity=paymentService.createTransaction(transactionDetailsEntity);
        TransactionDto savedTransactionDetailsEntityDTO =modelMapper.map(savedTransactionDetailsEntity, TransactionDto.class);
        return new ResponseEntity(savedTransactionDetailsEntityDTO.getTransactionId(), HttpStatus.CREATED) ;
    }


    //end point 2
    @GetMapping("/transaction/{id}")
    public ResponseEntity<TransactionDto> getTransactionDetailsById(@PathVariable("id") int transactionId){

        TransactionDetailsEntity transactionDetailsEntity=paymentService.getTransaction(transactionId);
        TransactionDto transactionDetailsEntityDTO=modelMapper.map(transactionDetailsEntity, TransactionDto.class);
        return new ResponseEntity(transactionDetailsEntityDTO,HttpStatus.OK);
    }
}
