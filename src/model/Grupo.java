package model;
import java.util.ArrayList;
import java.util.List;



public class Grupo {
    private String nome;
    private List<Participante> membros;


    public Grupo(String nome) {
        this.nome = nome;
        this.membros = new ArrayList<>();
    }


    public void adicionarMembro(Participante p) {
        if (this.membros.size() < 5) {
            this.membros.add(p);
        } else {
            System.out.println("Erro: O grupo já atingiu o limite de 5 membros.");
        }
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public List<Participante> getMembros() {
        return membros;
    }
}