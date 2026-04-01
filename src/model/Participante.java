package model;

public class Participante extends Usuario {
        private Integer pontuacaoAcumulada;

        public Participante(String login, String senha){
         super(login, senha);
         this.pontuacaoAcumulada =   0;
        }
        public void ingressarEmGrupo(Grupo g){

        }
        public void realizarAposta(Partida p, int gM, int gV){
        }

        public int getPontuacaoAcumulada(){
            return pontuacaoAcumulada;
        }
    public void realizarAposta(Partida p, int gA, int gB) {
        // 1. Pergunta para a partida se ela ainda aceita apostas
        if (p.isApostaValida()) {
            // 2. Se sim, cria o objeto Aposta
            Aposta novaAposta = new Aposta(gA, gB, p);
            System.out.println("Aposta registrada com sucesso!");
            // Aqui você adicionaria a aposta a uma lista do participante, se houver
        } else {
            // 3. Se não, avisa o usuário
            System.out.println("Erro: Apostas só são permitidas até 20 min antes do jogo.");
        }
    }
}
