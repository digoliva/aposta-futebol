package model;

public class Participante extends Usuario {
        private Integer pontuacaoAcumulada;

        public Participante(String login, String senha){
         super(login, senha);
         this.pontuacaoAcumulada = 0;
        }
        public void ingressarEmGrupo(grupo g){
        }
        public void realizarAposta(Partida p, int gM, int gV){
        }

        public int getPontuacaoAcumulada(){
            return pontuacaoAcumulada;
        }
}
