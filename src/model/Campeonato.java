package model;

import java.util.ArrayList;
import java.util.List;

public class Campeonato {
    private static final int MAX_CLUBES = 8;
    private String nome;
    private String edicao;
    private List<Clube> clubes;
    private List<Partida> partidas;

    public Campeonato() {
        this.nome = "";
        this.edicao = "";
        this.clubes = new ArrayList<>();
        this.partidas = new ArrayList<>();
    }

    public Campeonato(String nome, String edicao) {
        this.nome = nome;
        this.edicao = edicao;
        this.clubes = new ArrayList<>();
        this.partidas = new ArrayList<>();
    }

    public boolean adicionarClube(Clube clube) {
        if (clubes.size() >= MAX_CLUBES) return false;
        clubes.add(clube);
        return true;
    }

    public void adicionarPartida(Partida partida) {
        partidas.add(partida);
    }

    public boolean isClubeCadastrado(Clube clube) {
        return clubes.contains(clube);
    }

    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }

    public String getEdicao() { return edicao; }
    public void setEdicao(String edicao) { this.edicao = edicao; }

    public List<Clube> getClubes() { return clubes; }
    public List<Partida> getPartidas() { return partidas; }

    public int getMaxClubes() { return MAX_CLUBES; }

    @Override
    public String toString() {
        return nome + " - " + edicao;
    }
}