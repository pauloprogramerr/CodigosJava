package com.funcional;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

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
         *  e ai ordená-los
         */
        usuarios.stream()
                .filter(u -> u.getPontos() > 100)
                .sorted(Comparator.comparing(Usuario::getNome));


    }
}
