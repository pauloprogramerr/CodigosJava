package com.funcional;

import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class OperacaoDeCurtoCircuito {
    public static void main(String[] args) {
        Usuario user1 = new Usuario("Paulo Silveira", 150);
        Usuario user2 = new Usuario("Rodrigo Turini", 120);
        Usuario user3 = new Usuario("Guilherme Silveira", 90);
        List<Usuario> usuarios = Arrays.asList(user1, user2, user3);


        Random random = new Random(0);
        IntStream stream = IntStream.generate(() -> random.nextInt());
        List<Integer> list = stream
                .limit(100)
                .boxed()
                .collect(Collectors.toList());
        /** Repare a invocação de boxed, Ele retorna um Stream<Integer> em vez de um
         * InputStream, possibilitando a invocação a collect da forma que vimos.
         * sem isso teriamso apenas a opção de fazer IntStream.toArray, ou então de chamar
         * o collect que recebe três argumentos, mas não teriamos onde guardar os números
         */


        List<Integer>list1 = IntStream
                .generate(() -> random.nextInt())
                .limit(100)
                .boxed()
                .collect(Collectors.toList());
    }
}
