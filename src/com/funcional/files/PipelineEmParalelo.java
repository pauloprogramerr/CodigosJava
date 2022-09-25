package com.funcional.files;

import com.funcional.Usuario;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.LongStream;

public class PipelineEmParalelo {
    public static void main(String[] args) {
        Usuario user1 = new Usuario("Paulo Silveira", 150, true);
        Usuario user2 = new Usuario("Rodrigo Turini", 120, true);
        Usuario user3 = new Usuario("Guilherme Silveira", 90);
        Usuario user4 = new Usuario("Sergio Lopes", 120);
        Usuario user5 = new Usuario("Adrinan Almeida", 100);

        List<Usuario> usuarios =
                Arrays.asList(user1, user2, user3, user4, user5);

        List<Usuario> filtradosOrdenados =
                usuarios.stream()
                        .filter(u -> u.getPontos() > 100)
                        .sorted(Comparator.comparing(Usuario::getNome))
                        .collect(Collectors.toList());

        System.out.println(filtradosOrdenados);

        /** Ao utilizar o stream paralelo, ele vai decidir quantas threds deve utilizar
         * como deve quebrar o processamento dos dados e qual será a forma de unir o
         * resultado final em um só. Tudo isso sem ter que configurar nada.
         * Basta apenas invocar parallelStrean em vez de Stream
         */

        List<Usuario> filtrarOrdernados = usuarios.parallelStream()
                .filter(u -> u.getPontos() > 100)
                .sorted(Comparator.comparing(Usuario::getNome))
                .collect(Collectors.toList());
        System.out.println(filtrarOrdernados);

        /** Caso ão tenha acesso ao produtor de dados original, o Stream
         * tem um método parallel que devolve sua versão de execução em paralelo
         * Há também o sequential que retorna sua versão classica.
         */

        /** Vamos gerar uma quantidade grande de números, filtrá-los
         * e ordená-los, para poder ter uma base de comparação
         * Podemos gerar os números de 1 a um bilhão, utilizando o
         * LongStream.range. Usaremos o parallel e o filter par filtrar:
         */

        long soma =
                LongStream.range(0, 1_000_000_000)
                        .parallel()
                        .filter(x -> x % 2 == 0)
                        .sum();
        System.out.println(soma);
    }
}
