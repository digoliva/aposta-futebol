package model;

import java.time.LocalDateTime;

public class Partida {
    private Clube clubeMandante;
    private Clube clubeVisitante;
    private LocalDateTime dataHora;
    private int golsMandante;
    private int golsVisitante;
    private boolean resultadoRegistrado;

    public Partida() {
        this.resultadoRegistrado = false;
    }

    public Partida(Clube clubeMandante, Clube clubeVisitante, LocalDateTime dataHora) {
        this.clubeMandante = clubeMandante;
        this.clubeVisitante = clubeVisitante;
        this.dataHora = dataHora;
        this.resultadoRegistrado = false;
    }

    // Verifica se ainda é possível apostar (mínimo 20 min antes)
    public boolean podeApostar() {
        if (resultadoRegistrado) return false;
        LocalDateTime limiteAposta = dataHora.minusMinutes(20);
        return LocalDateTime.now().isBefore(limiteAposta);
    }

    public String getResultado() {
        if (!resultadoRegistrado) return "Não realizada";
        if (golsMandante > golsVisitante)
            return clubeMandante.getNome() + " venceu";
        if (golsVisitante > golsMandante)
            return clubeVisitante.getNome() + " venceu";
        return "Empate";
    }

    public String getPlacar() {
        if (!resultadoRegistrado) return "-";
        return golsMandante + " x " + golsVisitante;
    }

    // Getters e Setters
    public Clube getClubeMandante() { return clubeMandante; }
    public void setClubeMandante(Clube clubeMandante) { this.clubeMandante = clubeMandante; }

    public Clube getClubeVisitante() { return clubeVisitante; }
    public void setClubeVisitante(Clube clubeVisitante) { this.clubeVisitante = clubeVisitante; }

    public LocalDateTime getDataHora() { return dataHora; }
    public void setDataHora(LocalDateTime dataHora) { this.dataHora = dataHora; }

    public int getGolsMandante() { return golsMandante; }
    public void setGolsMandante(int golsMandante) { this.golsMandante = golsMandante; }

    public int getGolsVisitante() { return golsVisitante; }
    public void setGolsVisitante(int golsVisitante) { this.golsVisitante = golsVisitante; }

    public boolean isResultadoRegistrado() { return resultadoRegistrado; }
    public void setResultadoRegistrado(boolean resultadoRegistrado) { this.resultadoRegistrado = resultadoRegistrado; }

    @Override
    public String toString() {
        return clubeMandante + " x " + clubeVisitante + " | " + dataHora.toLocalDate() + " " + dataHora.toLocalTime();
    }
}