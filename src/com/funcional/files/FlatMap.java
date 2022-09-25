package com.funcional.files;

import com.funcional.Usuario;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.IntStream;
import java.util.stream.Stream;


public class FlatMap {

    static Stream<String> lines(Path p) {
        try {
            return Files.lines(p);
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }


    public static void main(String[] args) throws Exception{

        Usuario user1 = new Usuario("Paulo Silveira", 150);
        Usuario user2 = new Usuario("Rodrigo Turini", 120);
        Usuario user3 = new Usuario("Guilherme Silveira", 190);


        /** Podemos achatar um Stream de Streams com o flatMap
         * Basta trocar a invocação que teremos o final um Stream<String>
          */

            Stream<Object> strings = Files.list(
                            Paths.get("./com/funcinal/run/Main"))
                    .filter(p -> p.toString().endsWith(".java"))
                    .map(p -> lines(p));
          /**
         Isso pode ser encadeado em vários níveis, Para cada String podemos
         invocar String.chars() e obter um IntStream(Definiram assim para evitar
         o boxing para Stream<Character>). Se fizermos map(s -> s.chars())
         obteremos um indesejado Stream<IntStream>. Precisamos passat esse
         lambda para o flatMapInt
          */

        IntStream chars =
                Files.list(Paths.get("./com/funcional"))
                        .filter(p -> p.toString().endsWith(".java"))
                        .flatMap(p -> lines(p))
                        .flatMapToInt((String s) -> s.chars());
        /**
        O IntStream resultante possui todos os caracteres de todos os
         arquivos java do nosso diretório
        */

        Grupo englishSpeakers = new Grupo();
        englishSpeakers.add(user1);
        englishSpeakers.add(user2);

        Grupo spanishSpeakers = new Grupo();
        spanishSpeakers.add(user2);
        spanishSpeakers.add(user3);

        // Se temos esses grupos dentro de uma coleção
        List<Grupo> groups = Arrays.asList(englishSpeakers, spanishSpeakers);
        /** Pode ser que queriamos todos os usuários desse grupos. se fizermos
         * um imples groups.stream().map(g -> g.getUsuarios().stream())
         * teremos um Stream<Stream<Usuario>> que não desejamos.
         * O flatMap cai desembrulhar esses Streams, achatando-os.
         */

        groups.stream()
                .flatMap(g -> g.getUsuarios().stream())
                .distinct()
                .forEach(System.out::println);
        /** Temos como resultado todos os usuarios de ambos os grupos
         * sem repetição, Se tivéssemos coletado o resultado do pipeline em um
         * Set, não precisaríamos do distinct. Um outro exemplo de uso de flatMap?
         * Se nosso Usuarios possuíssem List<Pedidos> pedidos, chamar o
         * usuarios.map(u -> u.getPedidos()) geraria um Stream<List<Pedidos>>
         * Se você tentar faer usuarios.map(u -> u.getPedidos().stream()),
         * vai cair no Stream<Stream<<Pedidos>>. A reposta para obter um Strema<Pedido>
         * com os pedidos de todos os usuários da nossa lista é fazer
         * usuarios.flatMap(u -> u.getPedidos().stream()).
         */


    }
}

/** Main um exemplo de flapMap */

class Grupo{
    private Set<Usuario> usuarios = new HashSet<>();

    public void add(Usuario u){
        usuarios.add(u);
    }
    public Set<Usuario> getUsuarios(){
        return Collections.unmodifiableSet(this.usuarios);
    }
}