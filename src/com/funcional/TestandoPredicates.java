package com.funcional;

import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.function.Supplier;
import java.util.stream.IntStream;
import java.util.stream.Stream;


public class TestandoPredicates {
    public static void main(String[] args) {
        Usuario user1 = new Usuario("Paulo Silveira", 150);
        Usuario user2 = new Usuario("Rodrigo Turini", 120);
        Usuario user3 = new Usuario("Guilherme Silveira", 90);
        List<Usuario> usuarios = Arrays.asList(user1, user2, user3);

        /** Vimos o uso do filter, ele recebe um lambda como argumento,
         *  que é da interface Predicate. Há outras situações em que queremos testar
         *  predicados mas não precisamos da lista filtrada
         *  Por exemplo, se quisermos saber se há algum elemento daquela
         *  lista de usuários que é moderador usamos  o anyMatch, podemos
         *  descobrir se todos os usuários são moderadores com allMatch
         *  ou se nenhum deles é com o noneMatch. página 63
         */

        boolean hasModerdor = usuarios.stream()
                .anyMatch(Usuario::isModerador);


        /** Podemos pecorrer um iterator utilizando o métod forEachRemaining que recebe um Consumer
         * como parâmetro
         */
        usuarios.stream()
                .iterator()
                .forEachRemaining(System.out::println);

        /** Streams infinitos
         *  Um recurso poderoso no java 8, através da interface factory Supplier,
         *  podemos definir um Stream infinito, bastando dizer qual é a regra para a
         *  criação de objetos pertencentes a esse stream. se quisermos gerar
         *  uma lista de números aleatórios, podemos fazer:
          */
        Random random = new Random(0);
        Supplier<Integer> suppliers = () -> random.nextInt();
        Stream<Integer> stream = Stream.generate(suppliers);

        IntStream stream1 = IntStream.generate(() -> random.nextInt());
        /** Agora precisamos de cuidado, qualquer operação que necessite passar
         * por todos os elementos do Stream nunca terminará de executar.
         */
        int valor = stream1.sum();

    }
}
