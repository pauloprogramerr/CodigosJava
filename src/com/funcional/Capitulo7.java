package com.funcional;

import java.util.*;
import java.util.function.BiConsumer;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static java.util.stream.Collectors.*;

public class Capitulo7 {
    public static void main(String[] args) {
        Usuario user1 = new Usuario("Paulo Silveira", 150);
        Usuario user2 = new Usuario("Rodrigo Turini", 120);
        Usuario user3 = new Usuario("Guilherme Silveira", 90);
        List<Usuario> usuarios = Arrays.asList(user1, user2, user3);

        usuarios.stream()
                .filter(u -> u.getPontos() > 100);
        usuarios.forEach(System.out::println);

        // Vamos utilizar o retorno do filter para encaixar diretamente o forEach
        Stream<Usuario> streams = usuarios
                .stream()
                .filter(u -> u.getPontos() > 100);
        streams.forEach(System.out::println);

        //filtrar os usuários e tornar moderadores
        usuarios.stream()
                .filter(u -> u.getPontos() > 100)
                .forEach(Usuario::tornarModerador);
        // filtrar os usuários que são moderadores
        usuarios.stream()
                .filter(u -> u.isModerador());
        usuarios.stream()
                .filter(Usuario::isModerador);

        obterDeVoltaLista(usuarios);
    }
    static void obterDeVoltaLista(List<Usuario> usuarios){
        List<Usuario> maisQue100 = new ArrayList<>();
        usuarios.stream()
                .filter(u -> u.getPontos() > 100)
                .forEach(u -> maisQue100.add(u));
        // Tirando proveito da sintaxe do method references
        usuarios.stream()
                .filter(u -> u.getPontos() > 100)
                .forEach(maisQue100::add);

        /** Maneira mais simples de coletar os elementos de um Stream página 47
         * Podemos usar o Métoo collect para resgatar esses elementos
         * do nosso Stream<Usuario> para uma List, para fazer esta transformação simples
         * teriamos de escrever um código como:
         */

        Supplier<ArrayList<Usuario>> supplier = ArrayList::new;
        BiConsumer<ArrayList<Usuario>,Usuario> accumulator = ArrayList::add;
        BiConsumer<ArrayList<Usuario>, ArrayList<Usuario>> combiner = ArrayList::addAll;

        List<Usuario> maisQue100s = usuarios
                .stream()
                .filter(u -> u.getPontos() > 100)
                .collect(supplier, accumulator, combiner);

        usuarios.stream()
                .filter(u -> u.getPontos() > 100)
                .collect(ArrayList::new, ArrayList::add, ArrayList::addAll);

        /** Para simplificar nosso código podemos podemos passar como parâmetro em nosso método
            collect o Collectors.toList(), uma das implementações dessa nova interface
        */
        usuarios.stream()
                .filter(u -> u.getPontos() > 100)
                .collect(toList());

        /** Fazendo um import statico podemos deixar o código um pouco mais enxuto
         * em um único statement
         */
        usuarios.stream()
                .filter(u -> u.getPontos() > 100)
                .collect(toList());

        /** Podemos utilizar o método toSet para coletar as informações desse stream em um Set<Usuario>
         */

        Set<Usuario> maisQue100_2 = usuarios
                .stream()
                .filter(u -> u.getPontos() > 100)
                .collect(toSet());
        // Há ainda o método toCollection, que permite que você escolha a implementação que será devolvida no final da coleta
        Set<Usuario> set = usuarios
                .stream()
                .collect(Collectors
                        .toCollection(HashSet::new));

        // Extraindo uma lista com a pontuação de todos os usuários
        List<Integer> pontos = new ArrayList<>();
        usuarios.forEach(u -> pontos.add(u.getPontos()));


        // De forma mais simples
        List<Integer> ponto = usuarios.stream()
                .map(u -> u.getPontos())
                .collect(toList());
        //  tirando proveito do method references
        List<Integer> pont = usuarios.stream()
                .map(Usuario::getPontos)
                .collect(toList());

        /** O pacote java.util.stream possui implementações equivalentes
         *  ao Stream para os principais tipos primitivos InstStream, LongStream e DoubleStream
         *  Para evitar o autoboxing

            O maptoInt recebe uma função mais específica, enquanto o map recebe uma Function
        *   o mapToInt rece ToIntFunction,
        *   interface que o método aplly sempre retorna int e se chama apllyAsInt
        * */
        IntStream stream = usuarios.stream()
                .mapToInt(Usuario::getPontos);

        /** No Intstream, existe métodos que simplificam bastante nosso trabalho
        *   quando trabalhamos com inteiros, como max, sorted, e average.
        *   Observe como ficou mais simples obter a média de pontos dos usuários */

        double pontucacaoMedia = usuarios.stream()
                .mapToInt(Usuario::getPontos)
                .average()
                .getAsDouble();
        /** Livro Java 8 Prático - Lambdas, Streams e os novos recursos da linguagem
         *  O average() que so existe me streams primitivos, devolve um OptionalDouble
         *  se a lista for vazia, o valor de pontuação media será 0.0
         *  sem o uso do orElse, ao invocar oge você receberia um NuSurchElemenrException
         *  indicando que o Optional não possue valor definido
        */
        OptionalDouble media = usuarios.stream()
                .mapToInt(Usuario::getPontos)
                .average();
        double pontuacaoMedia = media.orElse(0.0);


        /** Podemos escrever em uma única linha */
        double pontucacaoMedias = usuarios.stream()
                .mapToInt(Usuario::getPontos)
                .average()
                .orElse(0.0);
        /** Ou ir além, como lançar uma exceptionutilizando o método orElseThrow
         * ele recebe um Supplier de exception, aquela interface funcional que parece
         * bastante uma factory. podemos fazer então:
         */

        double pontoMedia = usuarios.stream()
                .mapToInt(Usuario::getPontos)
                .average()
                .orElseThrow(IllegalStateException::new);

        /** Queremos o usuario com maior quantidade de pontos, podemos usar
         * o método max para tal, que recebe um comparator
         */

        Optional<Usuario> max = usuarios
                .stream()
                .max(Comparator.comparingInt(Usuario::getPontos));

        /** Página 54 */
    }
}
