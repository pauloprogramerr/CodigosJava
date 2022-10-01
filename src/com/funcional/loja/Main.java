package com.funcional.loja;

import java.math.BigDecimal;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.YearMonth;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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
        System.out.println(total);

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
        System.out.println(total1);

        /** Em vex de realizarmos operações de soma em momentos distintos
         * podemos criar um único Stream<BigDecimal> com valores de todos os
         * produtos de todos os pagamentos.
         */

        Stream<BigDecimal> princeOfEachProduct =
            payments.stream()
                    .flatMap(p -> p.getProducts()
                            .stream()
                            .map(Product::getPrice));

        // Se está difíciller este código, leia-o passo a passo. O importante é enxergar essa função
        Function<Payment, Stream<BigDecimal>> mapper =
                p -> p.getProducts().stream().map(Product::getPrice);

        /** Essa função mapeia um Payment para o Stream que passeia por todos
         * os seus produtos. E é por esse exaro motivo que precisamos invocar depois o
         * flatMap e não o map, caso contrário obteriamos um
         * Stream<Stream<BigDecimal>>. Para somar todos os Stream<BigDecimao>
         * basta realizarmos a operação de reduce que conhecemos.
          */

        BigDecimal totalFlat =
                payments.stream()
                        .flatMap(p -> p.getProducts()
                                .stream()
                                .map(Product::getPrice))
                        .reduce(BigDecimal.ZERO, BigDecimal::add);

        /** Produtos mais vendidos
         *
         * Mapearemos nossos produtos para a quantidade que eles aparecem
         * Para tal, criamos um Stream comtodos os Product vendidos.
         * Mais um vez entra o flatMap
         */
        Stream<Product> products = payments.stream()
                .map(Payment::getProducts)
                .flatMap(p -> p.stream());

        /** Em vez de p -> p.stream(), há possibilidade de passar o lambda
         * como method reference: List:: stream
         */
        Stream<Product> productss = payments.stream()
                .map(Payment::getProducts)
                .flatMap(List::stream);


        /** Sempre podemos juntar dois maps (independente de um deles ser flat)
         *  em um único map
         */
        Stream<Product> products1 = payments.stream()
                .flatMap(p -> p.getProducts().stream());

        /** Precisamos gerar um Map de Product par Long. Esse Long indica quantas
         * vezes o produto foi vendido. Usaremos o groupinhBy agrupando todos esses
         * produtos pelo próprio produto, mapeando-o pela sua contagem.
         */

        Map<Product, Long> topProducts = payments.stream()
                .flatMap(p -> p.getProducts().stream())
                .collect(Collectors.groupingBy(Function.identity(),
                        Collectors.counting()));
        System.out.println(topProducts);

        /** Podemos pegar o entreySet desse mapa e imprimir linha a linha */

        topProducts.entrySet().stream()
                .forEach(System.out::println);

        /** Pedindo a maior entrada do mapa considerado um Comparadtor
         * que compare o value de cada entrada. Vale lembrar que ela é representada
         * pela interface interna Map.Entry
          */
        topProducts.entrySet().stream()
                .max(Comparator.comparing(Map.Entry::getValue))
                .ifPresent(System.out::println);

        /**  Valores gerados pro produtos
         *
         * Calculamos a quantidade de vendas pro produtos, e a soma do valor por produto?
         * O processo é parecido, em vez de agruparmos com o vaçor de Collectors.counting
         * queremos fazer algo como Collectors.summing. Há diversos métodos como esse em Collectors
         * porém todos trabalham com tipos primitivos. Para realizar a soma em BigDecimal
         * teremos de deixar o reduce explícito
         */
        Map<Product, BigDecimal> totalValuePerProduct =
                payments.stream()
                        .flatMap(p -> p.getProducts().stream())
                        .collect(Collectors.groupingBy(Function.identity(),
                                Collectors.reducing(BigDecimal.ZERO, Product::getPrice,
                                        BigDecimal::add)));

        /** Podemos usar a mesma estratégia do stream().forEach(System.out::println)
         * para mostrar o resultado, mas vamos aproveitar e ordenar a saída
         * por valor
         */
        totalValuePerProduct.entrySet().stream()
                .sorted(Comparator.comparing(Map.Entry::getValue))
                .forEach(System.out::println);

        /** Quais são os produtos de cada cliente?
         *
         * Em um primeiro momento, podemos ter para cada Customer, sua
         * List<Payment>, bastando agrupar os payments
         * com groupingBy(Payment::getCustomer)
         */

        Map<Customer, List<Payment>> customerToPayments =
                payments.stream()
                        .collect(Collectors.groupingBy(Payment::getCustomer));

        /** Nã estamos interessados nos payments de um Customer, e sim nas linhas
         * de Product dentro de cada um desse Payments
         * Uma implementação inocente vai gerar uma List<List<<Product>>
         * dentro do valor do Map
         */
        Map<Customer, List<List<Product>>> customerToProductsList =
                payments.stream()
                        .collect(Collectors.groupingBy(Payment::getCustomer,
                                Collectors.mapping(Payment::getProducts,
                                        Collectors.toList())));

        customerToProductsList.entrySet().stream()
                .sorted(Comparator.comparing(e -> e.getKey().getName()))
                .forEach(System.out::println);
        /** Queremos o mesmo resultado, porém com as listas achatadas em uma só
         *  Há duas formas, Sim, uma envolve o flatMap do mapa resultante
         *  Dado customerToProductsList, queremos que o value de cada entry
         *  seja achatado
         */
        Map<Customer, List<Product>> customerToProducts2steps =
                customerToProductsList.entrySet().stream()
                        .collect(Collectors.toMap(Map.Entry::getKey,
                                e -> e.getValue().stream()
                                        .flatMap(List::stream)
                                        .collect(Collectors.toList())));

        customerToProducts2steps.entrySet().stream()
                .sorted(Comparator.comparing(e -> e.getKey().getName()))
                .forEach(System.out::println);

        /** Usamos o Collectors.toMap para criar um novo mapa no qual
         * a chave continua a mesma (Map.EntrygetKey) mas o valor é o resultado
         * do flatMap dos ListStream de todas as linhas
         * obtemos os mesmos resultados sem as linhas aninhadas
         *
         * Poderíamos ter feito com uma única chamada, Creio que nesse caso
         * estouramos o limite da legibilidade do uso da API. Apenas para efeito
         * didático, veja como ficaria
         */

        Map<Customer, List<Product>> customerToProducts1step =
                payments.stream()
                        .collect(Collectors.groupingBy(Payment::getCustomer,
                                Collectors.mapping(Payment::getProducts,
                                        Collectors.toList())))
                        .entrySet().stream()
                        .collect(Collectors.toMap(Map.Entry::getKey,
                                e -> e.getValue().stream()
                                        .flatMap(List::stream)
                                        .collect(Collectors.toList())));

        /** Difícil de seguir a sequencia, quebrar em varios passos é o mas indicado
         *
         * Como sempre, há outras formas de resolver o mesmo problemas. Podemos
         * usar o reducing mais uma vez, pois queremos acumular as listas de
         * cada cliente agrupado
         */

        Map<Customer, List<Product>> customerToProducts = payments.stream()
                .collect(Collectors.groupingBy(Payment::getCustomer,
                        Collectors.reducing(Collections.emptyList(),
                                Payment::getProducts,
                                (l1, l2) -> {List<Product> l = new ArrayList<>();
                            l.addAll(l1);
                            l.addAll(l2);
                            return l;})));

        /** Qual é o nosso Cliente mais especial 108
         *
         *  Qual seria a estratégia para obter o desejadp Map<Customer, BigDecimal>?
         *  Será a mesma que a da redução anterior, apenas mudando a operação
         *  Começaermos com BigDecimal.ZERO e para cada Payment, faremos BigDecimal::add
         *  da soma dos preços de seus produtos. Por esse motivo uma redução apaarece
         *  dentro de um reducing
         */

        Map<Customer, BigDecimal> totalValuePerCustomer = payments.stream()
                .collect(Collectors.groupingBy(Payment::getCustomer,
                        Collectors.reducing(BigDecimal.ZERO,
                                p -> p.getProducts().stream()
                                        .map(Product::getPrice)
                                        .reduce(BigDecimal.ZERO, BigDecimal::add),
                                        BigDecimal::add)));

        /** O código está no mínimo muito acumulado. Cremos ja termos passado do
         * limite da legibilidade. vamos quebrar essa reduçãi, criando uma variável temporária
         * responsavel por mapear um Payment para soma de todos os preços de
         * seus produtos
          */

         Function<Payment, BigDecimal> paymentToTotal =
         p -> p.getProducts().stream()
             .map(Product::getPrice)
             .reduce(BigDecimal.ZERO, BigDecimal::add);
        /** Com isto podemos utilizar essa Function no reducing
         */
        Map<Customer, BigDecimal> totalValuePerCustomer2 = payments.stream()
                .collect(Collectors.groupingBy(Payment::getCustomer,
                        Collectors.reducing(BigDecimal.ZERO,
                                paymentToTotal, BigDecimal::add)));

        /** Novamente usrge um forte indício de que deveria haver um método
         * getTotalAmount em Payment, que calculasse todo esse valor.
         * Nesse caso, poderíamos fazer um simples reducing(BigDecimal.ZERO,
         * PaymentgetTotalAmount, BigDecimaladd) Ao mesmo tempo, este está sendo
         * um excelente exercício de manipulação de coleções e relacionamentos.
         *
         */
        totalValuePerCustomer.entrySet().stream()
                .sorted(Comparator.comparing(Map.Entry::getValue))
                .forEach(System.out::println);

        /** Relatórios com datas
         *  É muito simples separarmos os pagamentos por data, usando um
         *  grouping(Payment::getDate).
         *  Há um perigo: o LocalDateTime vai agrupar os pagamentos até pelos
         *  milissegundos. Não é o que queremos. Podemos agrupar LocalDate, usando
         *  um groupingBy(p -> p.getDate().toLocalDate()), ou em um intervalo
         *  ainda maior, como por ano e mês. Para isto usamos o YearMonth
         */

        Map<YearMonth, List<Payment>> paymentPerMonth = payments
                .stream()
                .collect(Collectors.groupingBy(
                        p -> YearMonth.from(p.getDate())));
        paymentPerMonth.entrySet()
                .stream()
                .forEach(System.out::println);

        /** E se quisermos saber, também por mês, quanto doi faturado na loja?
         * Basta agrupar com o mesmo critério e usar a redução qye conhecemos
         * saomanod todos os preços de todos os produtos de todos os pagamentos
         */
        Map<YearMonth, BigDecimal> paymetsvaluePerMonth = payments
                .stream()
                .collect(Collectors.groupingBy(p -> YearMonth.from(p.getDate()),
                        Collectors.reducing(BigDecimal.ZERO,
                                p -> p.getProducts()
                                        .stream()
                                        .map(Product::getPrice)
                                        .reduce(BigDecimal.ZERO,
                                                BigDecimal::add),
                                BigDecimal::add)));



        BigDecimal monthfyLee = new BigDecimal("90.90");
        Subscription s1 =
                new Subscription(monthfyLee,
                        yesterday.minusMonths(5), paulo);
        Subscription s2 = new Subscription(monthfyLee,
                yesterday.minusMonths(8), today.minusMonths(1),rodrigo);
        Subscription s3 = new Subscription(monthfyLee,
                yesterday.minusMonths(5), today.minusMonths(2),
                adriano);
        List<Subscription> subscriptions = Arrays.asList(s1, s2,s3);

        /** Como calcular quantos meses foram pagos através daquela assinatura
         * Basta usar o que conhecemos da API de java.time. Mas depende do caso
         * Se a assinatura ainda estiver ativa, calculamos o intervalo de tempo entre
         * begin e a data de hoje
         */

        long meses = ChronoUnit.MONTHS
                .between(s1.getBegin(), LocalDateTime.now());
        /** E se a assinatura terminou? Em vez de enchermos nosso código de
         * ifs, tiramos proveito do Optional
         */
        long meses1 = ChronoUnit.MONTHS
                .between(s1.getBegin(),
                        s1.getEnd().orElse(LocalDateTime.now()));

        /** Para calcular o valor daquela assimatura, basta multiplicar
         * esse número de meses pelo custo mensal
          */
        BigDecimal total2 = s1.getMonthlyFee()
                .multiply(new BigDecimal(ChronoUnit.MONTHS
                        .between(s1.getBegin(),
                                s1.getEnd().orElse(LocalDateTime.now()))));


        /** Depois de adicionado o método getTotalPaid(), dada uma lista
         * de subscription, fica fácil somar todo o total pago.
          */
        BigDecimal totalPaid = subscriptions
                .stream()
                .map(Subscription::getTotalPaid)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }
}
