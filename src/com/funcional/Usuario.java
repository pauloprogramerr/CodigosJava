package com.funcional;

public class Usuario {
    private String nome;
    private int pontos;
    private boolean moderador;

    public Usuario() {
        this.nome = nome;
    }
    public Usuario(String nome){
        this.nome = nome;

    }
    public Usuario(String nome, int pontos){
        this.nome = nome;
        this.pontos = pontos;
        this.moderador = false;
    }

    public String getNome() {
        return nome;
    }

    public int getPontos() {
        return pontos;
    }

    public void tornarModerador(){
        this.moderador = true;
    }

    @Override
    public String toString() {
        return "Usuário: " + nome;
    }

    public boolean isModerador() {
        return moderador;
    }
}
