package model;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class Grupo {
    private static final int MAX_PARTICIPANTES = 5;
    private String nome;
    private List<Participante> participantes;

    public Grupo() {
        this.nome = "";
        this.participantes = new ArrayList<>();
    }

    public Grupo(String nome) {
        this.nome = nome;
        this.participantes = new ArrayList<>();
    }

    public boolean adicionarParticipante(Participante participante) {
        if (participantes.size() >= MAX_PARTICIPANTES) return false;
        participantes.add(participante);
        return true;
    }

    public List<Participante> getClassificacao() {
        List<Participante> classificados = new ArrayList<>(participantes);
        classificados.sort(Comparator.comparingInt(Participante::getTotalPontos).reversed());
        return classificados;
    }

    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }

    public List<Participante> getParticipantes() { return participantes; }
    public int getMaxParticipantes() { return MAX_PARTICIPANTES; }

    @Override
    public String toString() { return nome; }
}