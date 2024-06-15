package com.csc3402.lab.formlogin.service;

import com.csc3402.lab.formlogin.model.Transaction;
import com.csc3402.lab.formlogin.repository.TransactionRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TransactionServiceImpl implements TransactionService {

    private final TransactionRepository transactionRepository;

    public TransactionServiceImpl(TransactionRepository transactionRepository){
        this.transactionRepository = transactionRepository;
    }

    @Override
    public List<Transaction> listAllTransaction() {
        return transactionRepository.findAll();
    }

    @Override
    public Transaction addNewTransaction(Transaction transaction) {
        return transactionRepository.save(transaction);
    }
}
