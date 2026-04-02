package model;

import interfaces.Pontuavel;
import java.util.ArrayList;
import java.util.List;

public class Participante extends Usuario implements Pontuavel {
    private int totalPontos;
    private List<Aposta> apostas;

    // Construtor padrão
    public Participante() {
        super();
        this.totalPontos = 0;
        this.apostas = new ArrayList<>();
    }

    // Construtor sobrecarregado
    public Participante(String nome, String login, String senha) {
        super(nome, login, senha);
        this.totalPontos = 0;
        this.apostas = new ArrayList<>();
    }

    // Polimorfismo: sobreposição (override) do método abstrato de Usuario
    @Override
    public String getTipoUsuario() {
        return "Participante";
    }

    // Implementação da interface Pontuavel
    @Override
    public int calcularPontuacao() {
        return totalPontos;
    }

    @Override
    public void atualizarPontuacao(int pontos) {
        this.totalPontos += pontos;
    }

    public void adicionarAposta(Aposta aposta) {
        apostas.add(aposta);
    }

    public Aposta getApostaPorPartida(Partida partida) {
        for (Aposta a : apostas) {
            if (a.getPartida().equals(partida)) return a;
        }
        return null;
    }

    public List<Aposta> getApostas() { return apostas; }
    public int getTotalPontos() { return totalPontos; }
    public void setTotalPontos(int totalPontos) { this.totalPontos = totalPontos; }

    @Override
    public String toString() {
        return getNome() + " - " + totalPontos + " pts";
    }
}