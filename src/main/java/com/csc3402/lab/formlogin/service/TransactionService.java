package com.csc3402.lab.formlogin.service;

import com.csc3402.lab.formlogin.model.Transaction;

import java.util.List;

public interface TransactionService {
    List<Transaction> listAllTransaction();
    Transaction addNewTransaction(Transaction transaction);
}
