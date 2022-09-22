package com.funcional;

import java.util.*;
import java.util.stream.Collectors;

public class Capitulo8 {
    public static void main(String[] args) {
        Usuario user1 = new Usuario("Paulo Silveira", 150);
        Usuario user2 = new Usuario("Rodrigo Turini", 120);
        Usuario user3 = new Usuario("Guilherme Silveira", 90);
        List<Usuario> usuarios = Arrays.asList(user1, user2, user3);

        /** Ordenando uma Stream
         *  Dada uma List<Usuario> usuarios, sabemos como ordená-la por nome
         *  usuarios.sort(Comparator.comparing(Usuario::getNome));
         *  Imagine que queremos filtrar os usuários com mais de 100 pontos
         *  e ai ordená-los. pagina 56
         */
        usuarios.stream()
                .filter(u -> u.getPontos() > 100)
                .sorted(Comparator.comparing(Usuario::getNome));

        /**
         * No stream, o processo de ordenação é o sorted. A diferença entre
         * uma lista cmo sort e um stream com sorted você já deve imaginar:
         * um método invocado em Stream não altera quem o gerou. No caso ele altera
         * a List<Usuário> usuarios.
         * Se quisermos o resultado em uma List, precisamos usar um coletor
         */

        List<Usuario> filtradosOrdenados = usuarios.
                stream()
                .filter(u -> u.getPontos() > 100)
                .sorted(Comparator.comparing(Usuario::getNome))
                .collect(Collectors.toList());


        // TODO O mesmo código acima mas perdendo a clareza e simplicidade.
        List<Usuario> usuarios1Filtrados = new ArrayList<>();
        for (Usuario usuario : usuarios){
            if (usuario.getPontos() > 100){
                usuarios1Filtrados.add(usuario);
            }
        }
        Collections.sort(usuarios1Filtrados, new Comparator<Usuario>(){
            public int compare(Usuario u1, Usuario u2){
                return u1.getNome().compareTo(u2.getNome());
            }
        });
        /**
         *  É necessário uma lista temporaria para filtragem,
         *  um laço para esse mesmo filtro, uma classe anônima para o Comparator
         *  e finalmente a invaocação para a ordenação.
         */

        /**
         * Enxergando a execução do pipeline com peek
         * Podemos pedir para que o stream execute uma tarefa toda vez que
         * processar um elemento. fazemos isso através do peek:
         */

        usuarios.stream()
                .filter(u -> u.getPontos() > 100)
                .peek(System.out::println)
                .findAny();
        /** O peek devolve um novo stream, onde esta marcado para imprimir
         *  todos os elementos processados. ele so vai processar quando encontrar
         *  uma operação terminal, como findAny, collect ou forEach
         *  vamos experimentar fazer o mesmo com sorted */

        usuarios.stream()
                .sorted(Comparator.comparing(Usuario::getNome))
                .peek(System.out::println)
                .findAny();

        /** No código acima, o peek imprime todos os usuários, mesmo se só queremos
         *  fazer um findAny. Dizemos que o sorted é um método intermediário stateful.
         *  Operações stateful podem precisar processar todo o stream que sia operação
         *  terminal não demande isso.
        * */
    }
}
