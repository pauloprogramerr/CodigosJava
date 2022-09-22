package com.funcional;

import java.util.*;
import java.util.function.IntBinaryOperator;

public class OperacoesDeReducao {

    public static void main(String... args) {
        Usuario user1 = new Usuario("Paulo Silveira", 150);
        Usuario user2 = new Usuario("Rodrigo Turini", 120);
        Usuario user3 = new Usuario("Guilherme Silveira", 90);
        List<Usuario> usuarios = Arrays.asList(user1, user2, user3);

        /** Operações que utilizam os elementos da stream para retornar um
         *  valor final são frequentemente chamados de operações  de redução (reduction)
         *  um exemplo é o average, que ja havíamos visto:
         */
        double pontuacaoMedia = usuarios.stream()
                .mapToInt(Usuario::getPontos)
                .average()
                .getAsDouble();
        /** Há outros métodos úteis como o average: o count, o mim, o max
         *  e o sum. Esse ultimo, comom a average encontra-se apenas nos streams
         *  primitivos. o min e max pedem um Comparator como argumento.
         *  Todos, com exeção do sum e count, trabalham com Optional
         */
        Optional<Usuario> max = usuarios.stream()
                .max(Comparator.comparingInt(Usuario::getPontos));
        Usuario maximaPontuacao =  max.get();

        /** Se desejamso somar todos os pontos de usuários fazemos:
         *
         */
        int total = usuarios.stream()
                .mapToInt(Usuario::getPontos)
                .sum();

        /** IntBinaryOperator é um ainterface funcional que define o método
         * applyAsInt, que recebe dois inteiros e devolve um inteiro, com estas definições
         * podemos pedir que s stream processe a redução passo a passo
         */

        int valorInicial = 0;
        IntBinaryOperator operacao = (a, b) -> a + b;
        int totals = usuarios.stream()
                .mapToInt(Usuario::getPontos)
                .reduce(valorInicial, operacao);
        /** Temos um código equivalente ao sum, poderíamos ter escrito tudo sucintamnet,
         * sem declarações de variáveis locais
         */
        int totalls = usuarios.stream()
                .mapToInt(Usuario::getPontos)
                .reduce(0, (a, b) -> a + b);

        /** Na classe Integer, há agora o método estático Integer.sum,
         * que soma dois inteiros. Em vex do lambda, podemos usar um method references
         */

        int tootal = usuarios.stream()
                .mapToInt(Usuario::getPontos)
                .reduce(0, Integer::sum);

        // Multiplicar todos so pontos
        int multiplicacao = usuarios.stream()
                .mapToInt(Usuario::getPontos)
                .reduce(1, (a, b) -> a * b);

        // soma sem o map
        int ttotal = usuarios.stream()
                .reduce(0, (atual, u) -> atual + u.getPontos(), Integer::sum);
        /** Esse overload do reduce recebe mais um lambda que, no caso, é o
         * Integer::sum. Esse lambda a mais serve para combinar os valores de redução
         * parciais, no caso de streams paralelos.
          */


        /** Podemos percorrer os elementos de um Stream através de um Iterator
         */

        Iterator<Usuario> i = usuarios.
                stream()
                .iterator();

        /** A interface Iterator ja existe no java há bastante tempo e define os
         * métodos hasNexr, next e remove. Com o Java8, também podemosa pecorrer
         * um iterator utilizando o método forEachRemaining que recebe um
         * Consumer como parâmetro.
          */

        usuarios.stream()
                .iterator()
                .forEachRemaining(System.out::println);

        /** Motivo para usar o Iterator é quando queremos modificar os objetos de uma stream
         *  Quando utilizarmos streams paralelos, veremos que não devemos
         *  mudar o estado dos objetos que estão nele, correndo o risco de ter resultados
         *  não determinísticos. Outro motivo é a compatibilidade de APIs.
         *  Pode ser que você precise invocar um método que recebe Iterator
         */


    }
}