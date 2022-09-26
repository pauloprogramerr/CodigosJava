package com.funcional.loja;

import java.math.BigDecimal;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;

import static java.util.Arrays.asList;

public class Main { // Página 100
    public static void main(String[] args) {
        Customer paulo = new Customer("Paulo Silveira");
        Customer rodrigo = new Customer("Rodrigo Turini");
        Customer guilherme = new Customer("Guilherme Silveira");
        Customer adriano = new Customer("Adriano Almeida");

        Product bach = new Product("Bach Completo",
                Paths.get("./music/bach.mp3"), new BigDecimal(100));
        Product poderosas = new Product("Poderosas Anita",
                Paths.get("/music/poderosas.mp3"), new BigDecimal(90));
        Product bandeira = new Product("Bandeira Brasil",
                Paths.get("/images/brasil.jpg"), new BigDecimal(50));
        Product beauty = new Product("Beleza Americana",
                Paths.get("beauty.mov"), new BigDecimal(150));
        Product vingadores = new Product("Os Vingadores",
                Paths.get("/movies/vingadores.mov"), new BigDecimal(200));
        Product amelie = new Product("Amelie Poulain",
                Paths.get("/movies/amelie.mov"), new BigDecimal(100));


        LocalDateTime today = LocalDateTime.now();
        LocalDateTime yesterday = today.minusDays(1);
        LocalDateTime lastMonth = today.minusMonths(1);

        Payment payment1 =
                new Payment(asList(bach, poderosas), today, paulo);
        Payment payment2 =
                new Payment(asList(bach, bandeira, amelie), yesterday, rodrigo);
        Payment payment3 =
                new Payment(asList(beauty, vingadores, bach), today, adriano);
        Payment payment4 =
                new Payment(asList(bach, poderosas, amelie), lastMonth, guilherme);
        Payment payment5 =
                new Payment(asList(beauty, amelie), yesterday, paulo);

        List<Payment> payments = asList(payment1, payment2, payment3, payment4, payment5);

        /** Ordenando nosso pagamentos
         * Ordenar os pagamentos por data e imprimi-los para que fique clara a nossa base
         * de dados, Para isso podemos encadear o sorted e o forEach do stream dessa coleção.
         */

        payments.stream()
                .sorted(Comparator.comparing(Payment::getDate))
                .forEach(System.out::println);

        /** Reduzndo o BigDecimal em samas
         * Vamos clcular o valor total do pagamento do payment1 utilizando a
         * API de Stream e lambdas. Há um problema. Se preço fosse int, poderíamos usar o
         * mapToDouble e invocar o sum do DoulbeStream resultante.
         * Não é o caso, teremos um Stream<BigDecimal> e elem não possui um sum
         *
         * Nesse caso precisamos fazer a reduçãon mão, realizando a soma de BigDecimal
         * Podemos usar o (total, price) -> total.add(price), mas fica ainda mais fácil
         * usando um method reference
         */

        payment1.getProducts().stream()
                .map(Product::getPrice)
                .reduce(BigDecimal::add)
                .ifPresent(System.out::println);

        /** O ifPresent é do Optional<BigDecimal> retornado pelo reduce.
         * Se invocarmos o reduce que revebe o argumento de inicialização, eriamso como
         * retorno um BigDecimal diretamente. 103
         */
        BigDecimal total =
                payment1.getProducts()
                        .stream()
                        .map(Product::getPrice)
                        .reduce(BigDecimal.ZERO, BigDecimal::add);

        /**
         * Repare que o código dentro do primeiro map é o mesmo que o do código que
         * usamos para calcular a soma dos valores do payment1. Comesse map, temos como
         * resultado um Stream<BigDecimal>. Precisamos repetir a operação de reduce
         * para somar esses valores intermediários. Isto é, realizamos a soma de preços dos
         * produtos de cada pagamento, agora vamos somar cada um desses subtotais:
         */
        BigDecimal total1 =
                payments.stream()
                        .map(p -> p.getProducts().stream()
                                .map(Product::getPrice)
                                .reduce(BigDecimal.ZERO, BigDecimal::add))
                        .reduce(BigDecimal.ZERO, BigDecimal::add);
    }
}
