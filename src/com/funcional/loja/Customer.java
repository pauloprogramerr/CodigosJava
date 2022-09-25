package com.funcional.loja;

public class Customer {

    /** Nosso e-commerce conta com diversos clientes, dos quais para nós
     *  importa apenas o nome
      */
    private String name;

    public Customer(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return this.name;
    }
}
