package com.funcional.run;

import com.funcional.Usuario;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;

public class Main {
    public static void main(String[] args) {
        Usuario user1 = new Usuario("Paulo Silveira", 150);
        Usuario user2 = new Usuario("Rodrigo Turini", 120);
        Usuario user3 = new Usuario("Guilherme Silveira", 190);

        List<Usuario> usuarios = Arrays.asList(user1, user2, user3);


        usuarios.forEach(u -> u.tornarModerador());
        // Usando a mesma lógica de forma mais simpes
        usuarios.forEach(Usuario::tornarModerador);

        Consumer<Usuario> tornarModerador = Usuario::tornarModerador;
        usuarios.forEach(tornarModerador);

        // O código acima gera o mesmo consumidor que
        Consumer<Usuario> tornarModerado = u -> u.tornarModerador();
        // e não há reflection sendo utilizada, tudo é resolvido em tempo de compilação
        // sem custo de overhead para a performance


        // Comparando de uma forma ainda mais enxuta sem method references
        usuarios.sort(Comparator.comparing(u -> u.getNome()));

        // Com method references
        usuarios.sort(Comparator.comparing(Usuario::getNome));

        Function<Usuario, String> byName = Usuario::getNome;
        usuarios.sort(Comparator.comparing(byName));

        // Compondo Comparators, ordenando usuaruis
        usuarios.sort(Comparator.comparing(u -> u.getPontos()));

        //Usando a nova sintaxe podemos fazer:
        //utilizamos o comparingInt para evitar o boxing desnecessário
        usuarios.sort(Comparator.comparingInt(Usuario::getPontos));


        // ordenar pelos pontos e caso de empate, ordenar pelo nome
        // Evitando o boxing de primitivos como thenComparingInt
        Comparator<Usuario> c = Comparator.comparingInt(Usuario::getPontos)
                .thenComparing(Usuario::getNome);

        // Passando o comparator direto para o sort
        usuarios.sort(Comparator.comparingInt(Usuario::getPontos)
                .thenComparing(Usuario::getNome));

        // outro método que podemos usar para compor o comparator, porém passando como argumento o nullsLast
        usuarios.sort(Comparator.nullsLast(Comparator.comparing(Usuario::getNome)));

        // Os dois consumidores abaixo são equivalentes, um usando o method references e o outro usando lambda
        Consumer<Usuario> consumer1 = Usuario::tornarModerador;
        Consumer<Usuario> consumer2 = u -> u.tornarModerador();



    }
}