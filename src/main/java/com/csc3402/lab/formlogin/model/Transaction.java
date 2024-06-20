package com.csc3402.lab.formlogin.model;

import jakarta.persistence.*;

import java.util.Date;

@Entity
@Table(name = "transactions")
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "transaction_id")
    private Integer transactionId;

    @Column(name = "payment_method")
    private String paymentMethod;
    @Column(name = "note")
    private String note;
    @Column(name = "date")
    @Temporal(TemporalType.DATE)
    private Date date;
    @Column(name = "amount")
    private Integer amount;


//    @Column(name = "category")
//    private String category;

    @ManyToOne
    @JoinColumn(name = "budget_id")
    private Budget budget;

    public Transaction() {
    }

    public Transaction(Integer transactionId, String paymentMethod, String note, Date date, Integer amount) {
        this.transactionId = transactionId;
        this.paymentMethod = paymentMethod;
        this.note = note;
        this.date = date;
        this.amount = amount;
    }

    public Integer getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(Integer transactionId) {
        this.transactionId = transactionId;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

//    public String getCategory() {
//        return category;
//    }
//
//    public void setCategory(String category) {
//        this.category = category;
//    }

    @Override
    public String toString() {
        return "Transaction{" +
                "transactionId=" + transactionId +
                ", paymentMethod='" + paymentMethod + '\'' +
                ", note='" + note + '\'' +
                ", date=" + date +
                ", amount=" + amount +
                '}';
    }
}
