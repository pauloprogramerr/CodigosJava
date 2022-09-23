package com.funcional;

import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.function.IntSupplier;
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


        /** Vamos gerar a sequência infinita de números fibonacci de maneira lazy
         *  e imprimir seus 10 primeiros elementos:
         */
        IntStream.generate(new Fibonacci())
                .limit(10)
                .forEach(System.out::println);

        /** Veremos que manter o estado em uma intefae funcional pode limitar
         * os recursos de paralelização que um Stream fornece,
         * Além do limit, há outras operações que são de curto-circuito.
         * O findFirst é uma delas, mas não queremos pegar o primeiro elemento Fibonacci
         * Quero pegar o primeiro elemento maior aue 100
         * Podemos filtrar antes de invocar o findFIrst
         */

        int maiorQue100 = IntStream
                .generate(new Fibonacci())
                .filter(f -> f > 100)
                .findFirst()
                .getAsInt();
        System.out.println(maiorQue100);

        /** O filter não é de curto-circuito, Podemos tentar descobrir se todos os elementos
         * de Fibonicci são pares com allMatch(f -> f % 2 == 0) se houver ele retorna false,
         * caso contrário ele rodará indefinidamente.
         * Quando for necessário manter o estado de apenas uma variável,
         * podemos usar o iterate em vez de generate, que recebe um UnaryOperator
         * para gerar numeros naturais.
         */

        IntStream.iterate(0, x -> x + 1)
                .limit(10)
                .forEach(System.out::println);
    }

}


class Fibonacci implements IntSupplier{
    private int anterior = 0;
    private int proximo = 1;
    public int getAsInt(){
        proximo = proximo + anterior;
        anterior = proximo - anterior;
        return anterior;
    }
}