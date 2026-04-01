package model;
import java.time.Duration;
import java.time.LocalDateTime;

public class Partida {
    private LocalDateTime dataHora;
    private Clube timeA;
    private Clube timeB;
    private int golsA;
    private int golsB;
    private boolean encerrada;

    public Partida(LocalDateTime dataHora, Clube timeA, Clube timeB) {
        this.dataHora = dataHora;
        this.timeA = timeA;
        this.timeB = timeB;
        this.encerrada = false;
        this.golsA = 0;
        this.golsB = 0;
    }

    public void registrarResultado(int golsA, int golsB) {
        this.golsA = golsA;
        this.golsB = golsB;
        this.encerrada = true;
    }

    // Métodos de acesso (Getters) para a Aposta calcular os pontos
    public LocalDateTime getDataHora() {
        return dataHora;
    }

    public int getGolsA() {
        return golsA;
    }

    public int getGolsB() {
        return golsB;
    }

    public boolean isEncerrada() {
        return encerrada;
    }

    public Clube getTimeA() {
        return timeA;
    }

    public Clube getTimeB() {
        return timeB;
    }

    public boolean isApostaValida() {
        LocalDateTime agora = LocalDateTime.now();

        // Calcula a duração entre agora e o início da partida
        Duration diferenca = Duration.between(agora, this.dataHora);

        // Retorna true se faltarem 20 minutos ou mais
        return diferenca.toMinutes() >= 20;
    }
}
