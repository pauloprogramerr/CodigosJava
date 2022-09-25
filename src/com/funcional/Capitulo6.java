package com.funcional;

import com.funcional.interfaces.Suppliers;

import java.util.Arrays;
import java.util.List;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.IntBinaryOperator;
import java.util.function.ToIntBiFunction;
import java.util.stream.Stream;

public class Capitulo6 {
    public static void main(String[] args) {
        Usuario user1 = new Usuario("Paulo Silveira", 150);
        Usuario user2 = new Usuario("Rodrigo Turini", 120);
        Usuario user3 = new Usuario("Guilherme Silveira", 190);
        List<Usuario> usuarios = Arrays.asList(user1, user2, user3);

        usuarios.forEach(System.out::println);

        // Construtor sem parâmetros
        Suppliers<Usuario> criadorDeUsuario = Usuario::new;

        // Construtor com 1 parâmetro
        Function<String, Usuario> criadorDeUsuarios = Usuario::new;
        // Agora conseguiremos criar novos usuários invocando seu único método abstrato, o apply
        Usuario rodrigues = criadorDeUsuarios.apply("Rodrigues Turini");
        Usuario paulo = criadorDeUsuarios.apply("Paulo Cesar Gomes");


        // Criando um usuário usando dois parametros
        BiFunction<String, Integer, Usuario> criadorComDoisParametros = Usuario::new;

        Usuario rodrig = criadorComDoisParametros.apply("Rodrigo Turini", 50);
        Usuario paul = criadorComDoisParametros.apply("Paulo Cesar Gomes", 300);

        /* Atenção ao auto boxing desnecessário e constante, a Function e a BiFunction possuem
        *  suas interfaces análogas para tipos primitivos
        *  Em vez de usar a BiFunction poderíamos usar a ToIntBiFunction<Integer, Interger>
        *  evitando o unboxing no retorno
        *  è possivel evitar todo boxing usando uma IntBinaryOpertion
        *  que recebe dois ints e devolve um int, ex:
        */

        BiFunction<Integer, Integer, Integer> max = Math::max;
        ToIntBiFunction<Integer, Integer> max2 = Math::max;
        IntBinaryOperator max3 = Math::max;


        streamsCollectors(usuarios);
    }
     static void streamsCollectors(List<Usuario> usuarios){

        /*
         usuarios.sort(Comparator.comparing(Usuario::getPontos).reversed());
         usuarios.subList(0, 10)
                .forEach(Usuario::tornarModerador);
         */

         Stream<Usuario> streams = usuarios.stream();
         streams.filter(u ->  u.getPontos() > 100);
    }
}
