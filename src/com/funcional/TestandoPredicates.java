package com.funcional;

import java.util.Arrays;
import java.util.List;

public class TestandoPredicates {
    public static void main(String[] args) {
        Usuario user1 = new Usuario("Paulo Silveira", 150);
        Usuario user2 = new Usuario("Rodrigo Turini", 120);
        Usuario user3 = new Usuario("Guilherme Silveira", 90);
        List<Usuario> usuarios = Arrays.asList(user1, user2, user3);

        /** Vimos o uso do filter, ele recebe um lambda como argumento,
         *  que é da interface Predicate. Há outras situações em que queremos testar
         *  predicados mas não precisamos da lista filtrada
         *  Por exemplo, se quisermos saber se há algum elemento daquela
         *  lista de usuários que é moderador usamos  o anyMatch, podemos
         *  descobrir se todos os usuários são moderadores com allMatch
         *  ou se nenhum deles é com o noneMatch. página 63
         */

        boolean hasModerdor = usuarios.stream()
                .anyMatch(Usuario::isModerador);

    }
}
