package model;

public class Administrador extends Usuario {

    public Administrador() {
        super();
    }

    public Administrador(String nome, String login, String senha) {
        super(nome, login, senha);
    }
    @Override
    public String getTipoUsuario() {
        return "Administrador";
    }
}