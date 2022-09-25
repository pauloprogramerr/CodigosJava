package com.funcional.files;

import java.util.stream.LongStream;

public class OperacaoesNaoDeterministicasOrderedStreams {
    private static long total = 0;

    public static void main(String[] args) {
        /** Algumas operaçõe no pipeline são chamadas de não determinísticas
         * Elas podem devolver diferentes resultados quando utilizados em streams paralelos
         * os principais exemplos são o forEach e o findAny
         * Ao invocar estes dois métodos, você não tem a garantia da ordem de execução
         * Isso melhora sua performace em paralelo. Caso necessite
         * garantir a ordem de execução, você deve utilizar o forEachOrdered e o findFirst
         * na maioria das vezes eles não são necessários.
         *
         * O Collectors.groupingBy garante a ordem de aparição dos elementos ao agrupá-los
         * o que pode ser custoso na fase de fazer join.
         * Utilizar Collectors.groupingByConcurrent não garante essa ordem,
         * utilizando um único map concorrent como ConcurrentHasMap, mas a performace
         * final será melhor.
         *
         * O streams em parelelos são incrivelmente poderosos, mas não há mágica:
         * continuamos a ter problemas se houver operações com efeitos colaterais em estado comparilhado
         * Imagine outra forma de somar de 1 a 1 bilhao. Em vez de usar o sum
         * do LongStream, vamos criar atributo "total" para armazenar o resultado
         * Pelo forEach realizaremos a soma nesse atributo em comum.
          */

        LongStream.range(0, 1_000_000_000)
                .parallel()
                .filter(x -> x % 2 == 0)
                .forEach(n -> total += n);
        System.out.println(total);

        /** A base do trabalho de todo stream paralelo é o Spliterator
         * Ele é como um Iterator, so que muitas vezes pode ser facilmente quebrado em spliteratos
         * menores, para que cada Thread disponível consuma um pedaço do seu stream
         *
         * A Interface Iterable agora também define um método default spliterator()
         * Tudo que vimos de paralelização são abstrações que utilizam spliterators por baixp dos
         * panos, junto com a API de Fork/Join.
         * Caso você vá criar uma operação complexa paralela, é esse o caminho que deve seguir.
         */

    }
}
