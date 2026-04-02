package controller;

import model.*;
import java.util.List;

public class ParticipanteController {
    private SistemaApostas sistema = SistemaApostas.getInstancia();

    public String cadastrarParticipante(String nome, String login, String senha) {
        if (nome == null || nome.isBlank()) return "Nome inválido.";
        if (login == null || login.isBlank()) return "Login inválido.";
        if (senha == null || senha.isBlank()) return "Senha inválida.";

        for (Participante p : sistema.getParticipantes()) {
            if (p.getLogin().equals(login)) return "Login já cadastrado.";
        }
        Participante p = new Participante(nome, login, senha);
        sistema.adicionarParticipante(p);
        return "Participante cadastrado com sucesso!";
    }

    public String criarGrupo(String nome) {
        if (nome == null || nome.isBlank()) return "Nome do grupo inválido.";
        if (sistema.getGrupos().size() >= sistema.getMaxGrupos())
            return "Limite de " + sistema.getMaxGrupos() + " grupos atingido.";
        Grupo g = new Grupo(nome);
        sistema.adicionarGrupo(g);
        return "Grupo '" + nome + "' criado com sucesso!";
    }

    public String entrarNoGrupo(Participante participante, Grupo grupo) {
        if (participante == null) return "Participante não encontrado.";
        if (grupo == null) return "Selecione um grupo.";
        if (!grupo.adicionarParticipante(participante))
            return "Grupo cheio (máximo " + grupo.getMaxParticipantes() + " participantes).";
        return participante.getNome() + " entrou no grupo " + grupo.getNome() + "!";
    }

    public Participante login(String login, String senha) {
        return sistema.buscarParticipante(login, senha);
    }

    public List<Grupo> listarGrupos() { return sistema.getGrupos(); }
    public List<Participante> listarParticipantes() { return sistema.getParticipantes(); }
}