package model;

public class Administrador extends Usuario {

    public Administrador(String login, String senha){
        super(login, senha);
    }

    @Override
    public String getLogin() {
        return super.getLogin();
    }

    @Override
    public void setLogin(String login) {
        super.setLogin(login);
    }

    @Override
    public void setSenha(String senha) {
        super.setSenha(senha);
    }

}
