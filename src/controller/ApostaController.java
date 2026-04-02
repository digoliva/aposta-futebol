package controller;

import model.*;
import java.util.List;

public class ApostaController {
    private SistemaApostas sistema = SistemaApostas.getInstancia();

    public String registrarAposta(Participante participante, Partida partida,
                                  int golsMandante, int golsVisitante) {
        if (participante == null) return "Participante inválido.";
        if (partida == null) return "Partida inválida.";
        if (!partida.podeApostar())
            return "Apostas encerradas! Só é permitido apostar até 20 minutos antes da partida.";
        if (golsMandante < 0 || golsVisitante < 0) return "Gols não podem ser negativos.";

        // Verificar se já existe aposta para essa partida
        Aposta existente = participante.getApostaPorPartida(partida);
        if (existente != null) return "Você já apostou nessa partida.";

        Aposta aposta = new Aposta(participante, partida, golsMandante, golsVisitante);
        participante.adicionarAposta(aposta);
        return "Aposta registrada: " + golsMandante + " x " + golsVisitante;
    }

    public List<Aposta> listarApostasParticipante(Participante participante) {
        return participante.getApostas();
    }
}