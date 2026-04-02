package view;

import controller.ApostaController;
import controller.ParticipanteController;
import model.*;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.time.LocalDateTime;
import java.util.List;

public class TelaParticipante extends JFrame {
    private Participante participante;
    private ApostaController apostaController = new ApostaController();
    private ParticipanteController participanteController = new ParticipanteController();
    private JLabel lblPontos;
    private JTextArea areaLog;

    public TelaParticipante(Participante participante) {
        this.participante = participante;
        setTitle("Participante: " + participante.getNome());
        setSize(720, 580);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        initComponents();
    }

    private void initComponents() {
        JPanel painelPrincipal = new JPanel(new BorderLayout(8, 8));
        painelPrincipal.setBackground(new Color(30, 30, 50));
        painelPrincipal.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Header
        JPanel painelHeader = new JPanel(new BorderLayout());
        painelHeader.setBackground(new Color(30, 30, 50));
        JLabel titulo = new JLabel("👤 " + participante.getNome(), SwingConstants.LEFT);
        titulo.setFont(new Font("Arial", Font.BOLD, 17));
        titulo.setForeground(new Color(255, 200, 0));
        painelHeader.add(titulo, BorderLayout.WEST);

        lblPontos = new JLabel("Pontos: " + participante.getTotalPontos(), SwingConstants.RIGHT);
        lblPontos.setFont(new Font("Arial", Font.BOLD, 15));
        lblPontos.setForeground(new Color(100, 220, 100));
        painelHeader.add(lblPontos, BorderLayout.EAST);
        painelPrincipal.add(painelHeader, BorderLayout.NORTH);

        // Botões laterais
        JPanel painelBotoes = new JPanel(new GridLayout(5, 1, 6, 6));
        painelBotoes.setBackground(new Color(30, 30, 50));

        String[] bts = {"Registrar Aposta", "Minhas Apostas", "Criar Grupo", "Entrar no Grupo", "Ver Classificação"};
        for (String nome : bts) {
            JButton btn = criarBotao(nome);
            painelBotoes.add(btn);
        }

        JButton btnLogout = criarBotao("Logout");
        btnLogout.setBackground(new Color(120, 40, 40));
        painelBotoes.add(btnLogout);
        painelPrincipal.add(painelBotoes, BorderLayout.WEST);

        // Log
        areaLog = new JTextArea();
        areaLog.setEditable(false);
        areaLog.setBackground(new Color(20, 20, 35));
        areaLog.setForeground(new Color(200, 220, 255));
        areaLog.setFont(new Font("Monospaced", Font.PLAIN, 12));
        JScrollPane scroll = new JScrollPane(areaLog);
        scroll.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(new Color(80, 80, 120)), "Atividade",
                0, 0, null, Color.WHITE));
        painelPrincipal.add(scroll, BorderLayout.CENTER);

        add(painelPrincipal);
        log("Bem-vindo, " + participante.getNome() + "! Você tem " + participante.getTotalPontos() + " pontos.");
    }

    private JButton criarBotao(String nome) {
        JButton btn = new JButton(nome);
        btn.setBackground(new Color(0, 80, 160));
        btn.setForeground(Color.WHITE);
        btn.setFont(new Font("Arial", Font.BOLD, 12));
        btn.addActionListener(e -> acaoBotao(nome));
        return btn;
    }

    private void acaoBotao(String acao) {
        switch (acao) {
            case "Registrar Aposta" -> registrarAposta();
            case "Minhas Apostas" -> verApostas();
            case "Criar Grupo" -> criarGrupo();
            case "Entrar no Grupo" -> entrarNoGrupo();
            case "Ver Classificação" -> verClassificacao();
            case "Logout" -> { dispose(); new TelaLogin().setVisible(true); }
        }
    }

    private void registrarAposta() {
        SistemaApostas sistema = SistemaApostas.getInstancia();
        if (sistema.getCampeonatos().isEmpty()) { log("Nenhum campeonato disponível."); return; }

        Campeonato camp = (Campeonato) JOptionPane.showInputDialog(
                this, "Campeonato:", "Selecionar", JOptionPane.PLAIN_MESSAGE,
                null, sistema.getCampeonatos().toArray(), sistema.getCampeonatos().get(0));
        if (camp == null || camp.getPartidas().isEmpty()) { log("Nenhuma partida disponível."); return; }

        List<Partida> disponiveis = camp.getPartidas().stream()
                .filter(p -> !p.isResultadoRegistrado())
                .toList();
        if (disponiveis.isEmpty()) { log("Nenhuma partida aberta para apostas."); return; }

        Partida partida = (Partida) JOptionPane.showInputDialog(
                this, "Partida:", "Selecionar", JOptionPane.PLAIN_MESSAGE,
                null, disponiveis.toArray(), disponiveis.get(0));
        if (partida == null) return;

        try {
            int golsM = Integer.parseInt(JOptionPane.showInputDialog(this,
                    "Palpite - Gols " + partida.getClubeMandante().getNome() + ":"));
            int golsV = Integer.parseInt(JOptionPane.showInputDialog(this,
                    "Palpite - Gols " + partida.getClubeVisitante().getNome() + ":"));
            String msg = apostaController.registrarAposta(participante, partida, golsM, golsV);
            log(msg);
            atualizarPontos();
        } catch (NumberFormatException ex) {
            log("Número inválido.");
        }
    }

    private void verApostas() {
        List<Aposta> apostas = apostaController.listarApostasParticipante(participante);
        if (apostas.isEmpty()) { log("Você não tem apostas registradas."); return; }

        String[] colunas = {"Partida", "Palpite", "Resultado Real", "Pontos"};
        DefaultTableModel modelo = new DefaultTableModel(colunas, 0);
        for (Aposta a : apostas) {
            Partida p = a.getPartida();
            String resultado = p.isResultadoRegistrado() ? p.getPlacar() : "Aguardando";
            modelo.addRow(new Object[]{
                    p.getClubeMandante().getSigla() + " x " + p.getClubeVisitante().getSigla(),
                    a.getPlacarPrevisto(),
                    resultado,
                    a.isAvaliada() ? a.getPontosObtidos() : "-"
            });
        }

        JTable tabela = new JTable(modelo);
        tabela.setBackground(new Color(40, 40, 60));
        tabela.setForeground(Color.WHITE);
        tabela.setGridColor(new Color(80, 80, 100));
        tabela.getTableHeader().setBackground(new Color(0, 80, 160));
        tabela.getTableHeader().setForeground(Color.WHITE);

        JScrollPane scroll = new JScrollPane(tabela);
        scroll.setPreferredSize(new Dimension(500, 200));
        JOptionPane.showMessageDialog(this, scroll, "Minhas Apostas", JOptionPane.PLAIN_MESSAGE);
    }

    private void criarGrupo() {
        String nome = JOptionPane.showInputDialog(this, "Nome do grupo:");
        if (nome == null || nome.isBlank()) return;
        log(participanteController.criarGrupo(nome.trim()));
    }

    private void entrarNoGrupo() {
        List<Grupo> grupos = participanteController.listarGrupos();
        if (grupos.isEmpty()) { log("Nenhum grupo disponível."); return; }

        Grupo grupo = (Grupo) JOptionPane.showInputDialog(
                this, "Grupo:", "Selecionar", JOptionPane.PLAIN_MESSAGE,
                null, grupos.toArray(), grupos.get(0));
        if (grupo == null) return;
        log(participanteController.entrarNoGrupo(participante, grupo));
    }

    private void verClassificacao() {
        List<Grupo> grupos = participanteController.listarGrupos();
        if (grupos.isEmpty()) { log("Nenhum grupo disponível."); return; }

        Grupo grupo = (Grupo) JOptionPane.showInputDialog(
                this, "Grupo:", "Selecionar", JOptionPane.PLAIN_MESSAGE,
                null, grupos.toArray(), grupos.get(0));
        if (grupo == null) return;

        List<Participante> classificacao = grupo.getClassificacao();
        if (classificacao.isEmpty()) { log("Grupo sem participantes."); return; }

        String[] colunas = {"Posição", "Nome", "Pontos"};
        DefaultTableModel modelo = new DefaultTableModel(colunas, 0);
        int pos = 1;
        for (Participante p : classificacao) {
            modelo.addRow(new Object[]{ pos++ + "º", p.getNome(), p.getTotalPontos() });
        }

        JTable tabela = new JTable(modelo);
        tabela.setBackground(new Color(40, 40, 60));
        tabela.setForeground(Color.WHITE);
        tabela.setGridColor(new Color(80, 80, 100));
        tabela.getTableHeader().setBackground(new Color(0, 80, 160));
        tabela.getTableHeader().setForeground(Color.WHITE);

        JScrollPane scroll = new JScrollPane(tabela);
        scroll.setPreferredSize(new Dimension(350, 200));
        JOptionPane.showMessageDialog(this, scroll, "Classificação - " + grupo.getNome(), JOptionPane.PLAIN_MESSAGE);
    }

    private void atualizarPontos() {
        lblPontos.setText("Pontos: " + participante.getTotalPontos());
    }

    private void log(String msg) {
        areaLog.append("[" + LocalDateTime.now().toLocalTime().withNano(0) + "] " + msg + "\n");
        areaLog.setCaretPosition(areaLog.getDocument().getLength());
        atualizarPontos();
    }
}