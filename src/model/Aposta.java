package model;

public class Aposta {
    private static final int PONTOS_RESULTADO = 5;
    private static final int PONTOS_PLACAR_EXATO = 10;

    private Participante participante;
    private Partida partida;
    private int golsMandantePrevisto;
    private int golsVisitantePrevisto;
    private int pontosObtidos;
    private boolean avaliada;

    public Aposta() {
        this.pontosObtidos = 0;
        this.avaliada = false;
    }

    public Aposta(Participante participante, Partida partida, int golsMandantePrevisto, int golsVisitantePrevisto) {
        this.participante = participante;
        this.partida = partida;
        this.golsMandantePrevisto = golsMandantePrevisto;
        this.golsVisitantePrevisto = golsVisitantePrevisto;
        this.pontosObtidos = 0;
        this.avaliada = false;
    }

    // Polimorfismo: sobrecarga (overload) do método avaliar
    public void avaliar() {
        if (!partida.isResultadoRegistrado() || avaliada) return;

        boolean acertouResultado = acertouResultado();
        boolean acertouPlacar = acertouPlacarExato();

        if (acertouPlacar) {
            pontosObtidos = PONTOS_PLACAR_EXATO;
        } else if (acertouResultado) {
            pontosObtidos = PONTOS_RESULTADO;
        } else {
            pontosObtidos = 0;
        }

        participante.atualizarPontuacao(pontosObtidos);
        avaliada = true;
    }

    // Sobrecarga: avaliar com pontuação customizada
    public void avaliar(int pontosPersonalizados) {
        if (!partida.isResultadoRegistrado() || avaliada) return;
        this.pontosObtidos = pontosPersonalizados;
        participante.atualizarPontuacao(pontosObtidos);
        avaliada = true;
    }

    private boolean acertouResultado() {
        int golsM = partida.getGolsMandante();
        int golsV = partida.getGolsVisitante();
        boolean realEmpate = golsM == golsV;
        boolean prevEmpate = golsMandantePrevisto == golsVisitantePrevisto;
        if (realEmpate && prevEmpate) return true;
        boolean realMandanteVenceu = golsM > golsV;
        boolean prevMandanteVenceu = golsMandantePrevisto > golsVisitantePrevisto;
        return realMandanteVenceu == prevMandanteVenceu && !realEmpate && !prevEmpate;
    }

    private boolean acertouPlacarExato() {
        return partida.getGolsMandante() == golsMandantePrevisto
                && partida.getGolsVisitante() == golsVisitantePrevisto;
    }

    public String getPlacarPrevisto() {
        return golsMandantePrevisto + " x " + golsVisitantePrevisto;
    }

    public Participante getParticipante() { return participante; }
    public void setParticipante(Participante participante) { this.participante = participante; }

    public Partida getPartida() { return partida; }
    public void setPartida(Partida partida) { this.partida = partida; }

    public int getGolsMandantePrevisto() { return golsMandantePrevisto; }
    public void setGolsMandantePrevisto(int golsMandantePrevisto) { this.golsMandantePrevisto = golsMandantePrevisto; }

    public int getGolsVisitantePrevisto() { return golsVisitantePrevisto; }
    public void setGolsVisitantePrevisto(int golsVisitantePrevisto) { this.golsVisitantePrevisto = golsVisitantePrevisto; }

    public int getPontosObtidos() { return pontosObtidos; }
    public boolean isAvaliada() { return avaliada; }

    @Override
    public String toString() {
        return partida + " | Palpite: " + getPlacarPrevisto() + " | Pontos: " + pontosObtidos;
    }
}