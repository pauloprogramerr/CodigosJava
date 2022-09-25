package com.funcional.files;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Stream;

public class PraticandoComJavaNioFileFiles {

    static Stream<String> lines(Path p) {
        try {
            return Files.lines(p);
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }
    public static void main(String[] args) throws Exception{

        /** Se quisermos listar todos os arquivos do diretório, basta pegar o Stream<Path>
         * e depois o forEach:
         */
        Files.list(Paths.get("./com/funcional/run"))
                .forEach(System.out::println);

        //Quer apenas os arquivos java? podemos usar um filter:


        Files.list(Paths.get("./com/funcional/run"))
                .filter(p -> p.toString().endsWith(".java"))
                .forEach(System.out::println);

        // Se quisermos todo o conteúdos dos arquivos, vamos tentar usar o Files.lines
        Files.list(Paths.get("./com/funcional/run"))
                .filter(p -> p.toString().endsWith(".java"))
                .map(p -> lines(p))
                .forEach(System.out::println);


        /** O problema é que com esse map, teremos um Stream<Stream<String>>,
         * pois a invocação de lines(p) devolve um Stream<String> para cada
         * Path do nosso Stream<Patha> original, Isso fica mais claro de observar
         * se não usarmos o forEach e atribuimos o resultado a uma variável
         */

        Stream<Stream<String>> strings = Files.list(
                Paths.get("./com/funcional/run/Main"))
                .filter(p -> p.toString().endsWith(".java"))
                .map(p -> lines(p));

    }
}
