package com.funcional.loja;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.List;

public class Payment {
    /** Quando o usuário realiza uma compra no sistema, um Payment é utilizado
     * para representá-la, armazenando uma lista de produtos comprados, o
     * momento da compra e quem realizou
      */

    private List<Product> products;
    private LocalDateTime date;
    private Customer customer;

    public Payment(List<Product> products, LocalDateTime date, Customer customer) {
        this.products = Collections.unmodifiableList(products);
        this.date = date;
        this.customer = customer;
    }

    public List<Product> getProducts() {
        return products;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public Customer getCustomer() {
        return customer;
    }

    @Override
    public String toString() {
        return "[Payment: " +
                date.format(DateTimeFormatter.ofPattern("dd/MM/yyyy")) +
                " " + customer + " " + products + "]";
    }
}
