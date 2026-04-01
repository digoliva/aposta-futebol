package model;
import java.time.LocalDateTime;
import java.time.Duration;

public class Aposta {
    private int palpiteA;
    private int palpiteB;
    private LocalDateTime dataRegistro;

    private Partida partida;

    public Aposta(int palpiteA, int palpiteB, Partida partida) {
        this.palpiteA = palpiteA;
        this.palpiteB = palpiteB;
        this.partida = partida;
        this.dataRegistro = LocalDateTime.now();
    }

    public int calcularPontos(int realA, int realB) {
        if (this.palpiteA == realA && this.palpiteB == realB) {
            return 10;
        }

        if (realA == realB && this.palpiteA == this.palpiteB) {
            return 5;
        }
        if (realA > realB && this.palpiteA > this.palpiteB) {
            return 5;
        }
        if (realB > realA && this.palpiteB > this.palpiteA) {
            return 5;
        }
        return 0;
    }
    public boolean isApostaValida() {
        if (this.partida == null) return false;

        LocalDateTime horarioPartida = this.partida.getDataHora();
        Duration diferenca = Duration.between(this.dataRegistro, horarioPartida);

        return !diferenca.isNegative() && diferenca.toMinutes() >= 20;
    }

    public int getPalpiteA() {
        return palpiteA; }
    public int getPalpiteB() {
        return palpiteB; }
    public LocalDateTime getDataRegistro() { return dataRegistro; }
}