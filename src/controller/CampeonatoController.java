package controller;

import model.*;

import java.time.LocalDateTime;
import java.util.List;

public class CampeonatoController {
    private SistemaApostas sistema = SistemaApostas.getInstancia();

    public String cadastrarCampeonato(String nome, String edicao) {
        if (nome == null || nome.isBlank()) return "Nome do campeonato inválido.";
        Campeonato c = new Campeonato(nome, edicao);
        sistema.getCampeonatos().add(c);
        sistema.setCampeonatoAtivo(c);
        return "Campeonato cadastrado com sucesso!";
    }

    public String cadastrarClube(String nome, String sigla) {
        if (nome == null || nome.isBlank() || sigla == null || sigla.isBlank())
            return "Nome ou sigla inválidos.";
        Clube clube = new Clube(nome, sigla.toUpperCase());
        sistema.getClubes().add(clube);
        return "Clube cadastrado com sucesso!";
    }

    public String adicionarClubeAoCampeonato(Campeonato campeonato, Clube clube) {
        if (campeonato == null) return "Selecione um campeonato.";
        if (clube == null) return "Selecione um clube.";
        if (!campeonato.adicionarClube(clube))
            return "Limite de " + campeonato.getMaxClubes() + " clubes atingido.";
        return clube.getNome() + " adicionado ao campeonato!";
    }

    public String cadastrarPartida(Campeonato campeonato, Clube mandante, Clube visitante, LocalDateTime dataHora) {
        if (campeonato == null) return "Selecione um campeonato.";
        if (mandante == null || visitante == null) return "Selecione os clubes.";
        if (mandante.equals(visitante)) return "Os clubes devem ser diferentes.";
        if (dataHora == null || dataHora.isBefore(LocalDateTime.now())) return "Data/hora inválida.";
        Partida p = new Partida(mandante, visitante, dataHora);
        campeonato.adicionarPartida(p);
        return "Partida cadastrada com sucesso!";
    }

    public String registrarResultado(Partida partida, int golsMandante, int golsVisitante) {
        if (partida == null) return "Selecione uma partida.";
        if (partida.isResultadoRegistrado()) return "Resultado já foi registrado.";
        if (golsMandante < 0 || golsVisitante < 0) return "Gols não podem ser negativos.";
        partida.setGolsMandante(golsMandante);
        partida.setGolsVisitante(golsVisitante);
        partida.setResultadoRegistrado(true);

        // Avaliar apostas de todos os participantes para esta partida
        for (Participante p : sistema.getParticipantes()) {
            Aposta a = p.getApostaPorPartida(partida);
            if (a != null && !a.isAvaliada()) {
                a.avaliar();
            }
        }
        return "Resultado registrado! Apostas avaliadas automaticamente.";
    }

    public List<Campeonato> listarCampeonatos() { return sistema.getCampeonatos(); }
    public List<Clube> listarClubes() { return sistema.getClubes(); }
}