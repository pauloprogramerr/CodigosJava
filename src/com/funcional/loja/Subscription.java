package com.funcional.loja;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Optional;

public class Subscription {
    /** Sistema de assinatura */
    private BigDecimal monthlyFee;
    private LocalDateTime begin;
    private Optional<LocalDateTime> end;
    private Customer customer;

    public Subscription(BigDecimal monthlyFee, LocalDateTime begin, Customer customer) {
        this.monthlyFee = monthlyFee;
        this.begin = begin;
        this.end = Optional.empty();
        this.customer = customer;
    }



}
