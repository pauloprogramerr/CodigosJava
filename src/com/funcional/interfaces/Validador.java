package com.funcional.interfaces;

@FunctionalInterface
public interface Validador<T> {
    boolean validar(T t);
}
