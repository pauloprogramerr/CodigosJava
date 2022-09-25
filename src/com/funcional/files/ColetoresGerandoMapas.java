package com.funcional.files;

import com.funcional.Usuario;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.LongStream;
import java.util.stream.Stream;
public class ColetoresGerandoMapas {

    static Stream<String> lines(Path p){
        try{
            return Files.lines(p);
        }catch (IOException e){
            throw new UncheckedIOException(e);
        }
    }

    public static void main(String[] args) throws IOException {

        Usuario user1 = new Usuario("Paulo Silveira", 150);
        Usuario user2 = new Usuario("Rodrigo Turini", 120);
        Usuario user3 = new Usuario("Guilherme Silveira", 190);
        List<Usuario> usuarios = Arrays.asList(user1, user2, user3);


        /** Gerando um Stream com todas as linash dos arquivos de
         * determindo diretório:
         */
        Stream<String> strings =
                Files.list(Paths.get("CodigosJava/com/funcional/files"))
                        .filter(p -> p.toString().endsWith(".java"))
                        .flatMap(p -> lines(p));
        /** Poderiamos ter um Strema com a quantdade de linhas de cada arquivo
         * Para issp, em vez de fazer um flatMpa para as linhas, fazemos um
         * map para a quantidade de linhas, usando o count do Stream:
         */

        LongStream lines =
                Files.list(Paths.get("./com/funconal"))
                        .filter(p -> p.toString().endsWith(".java"))
                        .mapToLong(p -> lines(p).count());

        /** Se quisermos uma List<Long> com os valores desse LongStream,
         * fazemos um collect como já conhecemos.
         */
        List<Long> liness =
                Files.list(Paths.get("./com/funcional"))
                        .filter(p -> p.toString().endsWith(".java"))
                        .map(p -> lines(p).count())
                        .collect(Collectors.toList());

        /** O que precisamos com mais frequencia é saber quantas linhas
         * tem cada arquivo, po exemplo. Podemos fazer um forEach e popular
         * um Map<Path, Long> no qual a chave é o arquivo e o valor é a quantidade
         * de linhas daquele arquivo. página 73
          */
        Map<Path, Long> linesPerFile = new HashMap<>();

        Files.list(Paths.get("./com/funcional"))
                .filter(p -> p.toString().endsWith(".java"))
                .forEach(p -> linesPerFile.put(p, lines(p).count()));

        System.out.println(linesPerFile);

        /** Esta solução acima não é muito funcional, o lambda passado
         * para o forEaxh utiliza uma variável declarada fora do seu escopo,
         * mudando seu estado, o que chamamos de efeito colateral. Issp diminui
         * a possibilidade de otimização, em especial para execuçãi em paralelo.
         *
         * Podemos criar esse mesmo mapa com um outro coletor mais específico
         * que esse tipo de tarefa, o toMap
         */

        Map<Path, Long> linesss = Files.list(Paths.get("com/funcional/files"))
                .filter(p -> p.toString().endsWith(".java"))
                .collect(Collectors.toMap(
                        p -> p,
                        p -> lines(p).count()));
        System.out.println(linesss);

        /** toMap recebe duas Functions, a primeiro produzirá a chave
         * (no nosso caso o próprio Path) e a segunda produzirá o valor
         * (a quantidade de linhas). Como é comum precisarmos de um lambda que retorne
         * o próprio arqgumento (o nosso p -> p), podemos utilizar
         * Function.identy() para deixar mais claro
         * Se quisermso gerar um mapa de cada arquivo para toda a lista de linhas
         * contidas nos arquivos, podemos utilizar um outro coletor e gerar um
         * Map<Path, List<String>>
         */

        Map<Path, List<String>> content =
            Files.list(Paths.get("com/funcional"))
                    .filter(p -> p.toString().endsWith(".java"))
                    .collect(Collectors.toMap(
                            Function.identity(),
                            p -> lines(p).collect(Collectors.toList())
                    ));
        /** Certamente o toMap vai aparecer bastante no seu código.
         * São muitos os casos em que queremos gerar mapas temporários
         * para processar dados e gerar estatísticas e relatórios
         * Mapear todos os usuarios utilizando seu nome como chave é fácil
         */

        Map<String, Usuario> nameToUser = usuarios
                .stream()
                .collect(Collectors.toMap(
                        Usuario::getNome,
                        Function.identity()));

        /** Se o usuário fosse uma entidade JPA, poderíamos utilizar
         * toMap(Ususario::getId, Funcion.identify()) para gerar um
         * Map<Long, Usuario> no qual a chave é um id da entidade.
          */

    }
}
