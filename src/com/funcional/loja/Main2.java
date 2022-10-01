package com.funcional.loja;

import com.funcional.Usuario;
import com.funcional.interfaces.RelatorioController;
import com.funcional.interfaces.Role;
import com.sun.source.util.ParameterNameProvider;

import java.lang.reflect.Constructor;
import java.lang.reflect.Parameter;
import java.math.BigDecimal;
import java.security.PrivilegedAction;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Supplier;

public class Main2 {

    /** Apêndice: mais java 8 com reflecion, JVM, APIs e limitações */

    public static void main(String args) throws NoSuchMethodException {

        /** Novos detalhes na linguagem
         *
         * Operador diamante melhorado
         *
         * O operador diamante(diamond operator) é aquele que evita digitarmos
         * desncessáriamente em algumas situações óbivias
         */

        List<Usuario> list = new ArrayList<>();
        // Em java 7 seria
        List<Usuario> lista = new ArrayList<Usuario>();

        Supplier<String> supplier = () -> "retorna uma string";
        PrivilegedAction<String> p = () -> "retorna uma string";

        PrivilegedAction<String> action = () -> "executando uma ação";
        execute(action::run);

        Runnable r = () -> {
            System.out.println("eu sou um runnable!");
        };
        new Thread(r).start();

        RelatorioController controller = new RelatorioController();
        Role[] annotationByType = controller
                .getClass()
                .getAnnotationsByType(Role.class);
        Arrays.asList(annotationByType)
                .forEach(a -> System.out.println(a.value()));

        Constructor<Usuarios> constructor =
                Usuarios
                        .class
                        .getConstructor(String.class, int.class);
        Parameter[] parameters = constructor.getParameters();
        Arrays.asList(parameters)
                .forEach(param -> System.out.println(
                        param.isNamePresent() + ": " +param.getName()));
    }
    private static void execute(Supplier<String> s){
        System.out.println(s.get());
    }
}


class Usuarios {
    private String nome;
    private int pontos;
    public Usuarios(String nome, int pontos){
        this.pontos = pontos;
        this.nome = nome;
    }
}
