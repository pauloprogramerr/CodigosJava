package com.funcional.loja;

import java.math.BigDecimal;
import java.nio.file.Paths;

public class Main { // Página 100
    public static void main(String[] args) {
        Customer paulo = new Customer("Paulo Silveira");
        Customer rodrigo = new Customer("Rodrigo Turini");
        Customer guilherme = new Customer("Guilherme Silveira");
        Customer adriano = new Customer("Adriano Almeida");

        Product bach = new Product("Bach Completo",
                Paths.get("/music/bach.mp3"), new BigDecimal(100));

    }
}
