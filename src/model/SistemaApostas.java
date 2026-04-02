package model;

import java.util.ArrayList;
import java.util.List;

public class SistemaApostas {
    private static final int MAX_GRUPOS = 5;
    private static SistemaApostas instancia;

    private Administrador administrador;
    private List<Campeonato> campeonatos;
    private List<Clube> clubes;
    private List<Grupo> grupos;
    private List<Participante> participantes;
    private Campeonato campeonatoAtivo;

    private SistemaApostas() {
        campeonatos = new ArrayList<>();
        clubes = new ArrayList<>();
        grupos = new ArrayList<>();
        participantes = new ArrayList<>();
        administrador = new Administrador("Admin", "admin", "admin123");
    }

    public static SistemaApostas getInstancia() {
        if (instancia == null) instancia = new SistemaApostas();
        return instancia;
    }

    public boolean adicionarGrupo(Grupo grupo) {
        if (grupos.size() >= MAX_GRUPOS) return false;
        grupos.add(grupo);
        return true;
    }

    public boolean adicionarParticipante(Participante p) {
        participantes.add(p);
        return true;
    }

    public Participante buscarParticipante(String login, String senha) {
        for (Participante p : participantes) {
            if (p.getLogin().equals(login) && p.getSenha().equals(senha)) return p;
        }
        return null;
    }

    public boolean isAdministrador(String login, String senha) {
        return administrador.getLogin().equals(login) && administrador.getSenha().equals(senha);
    }

    public List<Campeonato> getCampeonatos() { return campeonatos; }
    public List<Clube> getClubes() { return clubes; }
    public List<Grupo> getGrupos() { return grupos; }
    public List<Participante> getParticipantes() { return participantes; }
    public Administrador getAdministrador() { return administrador; }

    public Campeonato getCampeonatoAtivo() { return campeonatoAtivo; }
    public void setCampeonatoAtivo(Campeonato campeonatoAtivo) { this.campeonatoAtivo = campeonatoAtivo; }

    public int getMaxGrupos() { return MAX_GRUPOS; }
}