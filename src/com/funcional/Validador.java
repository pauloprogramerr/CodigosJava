package com.funcional;

@FunctionalInterface
public interface Validador<T> {
    boolean validar(T t);
}
