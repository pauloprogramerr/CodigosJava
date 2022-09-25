package com.funcional.files;

import com.funcional.Usuario;

import java.util.*;
import java.util.stream.Collectors;

public class GroupingByPartitioningBy {

    public static void main(String[] args) {
        Usuario user1 = new Usuario("Paulo Silveira", 150, true);
        Usuario user2 = new Usuario("Rodrigo Turini", 120, true);
        Usuario user3 = new Usuario("Guilherme Silveira", 90);
        Usuario user4 = new Usuario("Sergio Lopes", 120);
        Usuario user5 = new Usuario("Adrinan Almeida", 100);

        List<Usuario> usuario =
                Arrays.asList(user1, user2, user3, user4, user5);


        /** Queremos que a chave seja a pontuação do usuário e o valor seja
         * uma lista de usuários que possuem aquela pontuação.
         */

        // Forma tradicional
        Map<Integer, List<Usuario>> pontuacao = new HashMap<>();

        for (Usuario u : usuario){

            if (!pontuacao.containsKey(u.getPontos())){
                pontuacao.put(u.getPontos(), new ArrayList<>());
            }
            pontuacao.get(u.getPontos()).add(u);
        }
        System.out.println(pontuacao);

    // diminuindo o código com a ajuda de novos métodos default do Map

        for (Usuario u : usuario){
            pontuacao
                    .computeIfAbsent(u.getPontos(), user -> new ArrayList<>())
                    .add(u);
        }
        System.out.println(pontuacao);

        /** O método computerIfAbsent vai chamar a Function do lambda no caso
         * de não encontrar um valor para a chave u.getPontos() e associar o resultado
         * (a nova ArrayList) a essa mesma chave. isto é, essa invocação do
         * computerIfAbsent faz o papel do if que fizemos no código anterior.
         *
         * Mas oq ue relamente queremos é trabalhar com Stream. Poderíamos escrever um
         * Collector que faz exatamente esse trabalho:
          */
        Map<Integer, List<Usuario>> ponts = usuario
                .stream()
                .collect(Collectors.groupingBy(Usuario::getPontos));
        System.out.println(ponts); // a saída é a mesma

        /** O segredo é o Collector,groupingBy, que é uma factory de
         * Collectors que fazem agrupamentos. Podemos fazer mais.
         * Podemos particionar todos os usuarios entre moderadores e não
         * moderadores, usando o partitioningBy:
         */

        Map<Boolean, List<Usuario>> moderadores = usuario
                .stream()
                .collect(Collectors.partitioningBy(Usuario::isModerador));
        System.out.println(moderadores);// Lista de quem é moderador e quem não é.


        /** O partitioningBy nada mais é do que uma versão mais eficinente
         * para ser usada ao agrupar booleans.
         * O partitioningBy(Usuario::isModerador) nos devolve um
         * Map<Boolean, List<Usuario>>
         *     Se fizermos stream().map(Usuario::getNome) não poderemos particionar
         *     por Usuario::isModerador, pois o map nos retornaria um Stream<String>
         */

        /** Tanto o partitioningBy tanto groupingBy possuem uma sobrecarga
         * que permite passar um Collector como argumento. Há um Collector que
         * sabe coletar os objetos ao mesmo tempo que realiza uma transformação de map
         *
         * Em vez de guardar os objetos dos usuários, poderíamos guardar uma lista
         * com apenas o nome de cada usuário, usando o mapping para coletar esse nomes
         * em um lista.
         */
        Map<Boolean, List<String>> nomePorTipo = usuario
                .stream()
                .collect(
                        Collectors.partitioningBy(
                                Usuario::isModerador,
                                Collectors.mapping(Usuario::getNome,
                                        Collectors.toList())));
        System.out.println(nomePorTipo);

        /** Queremos particionar por moderaçõa, mas ter como valor não
         * os usuarios, mas sim a soma de seus pontos. Também existe um
         * coletor para realizar essas somatórias, que pode ser usado em conjunto
         * com o partitioningBy e groupingBy
          */
        Map<Boolean, Integer> pontuacaoPorTipo = usuario
                .stream()
                .collect(Collectors.partitioningBy(
                   Usuario::isModerador,
                   Collectors.summingInt(Usuario::getPontos)
                ));
        System.out.println(pontuacaoPorTipo);

        /** Perceba que não usamos mais loops para processar os elementos
         * Ate mesmo para concatenar todos os nomes dos usuarios há um coletor
         */

        String nomes = usuario
                .stream()
                .map(Usuario::getNome)
                .collect(Collectors.joining(", "));
        System.out.println(nomes);

        /** Com streams e coletores, conseguimos os mesmos resultados de
         * antigamente, porém em um estilo funcional e consequentemente
         * mais exuto e expressivo. Além disso, mesmo que você não tenha percebido
         * acabamos trabalhando com menos efeitos colaterais e favorecendo a
         * imutabiulidade das coleções originais.
          */
    }
}
