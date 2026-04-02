package view;

import controller.CampeonatoController;
import model.*;

import javax.swing.*;
import java.awt.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;

public class TelaAdmin extends JFrame {
    private CampeonatoController controller = new CampeonatoController();
    private JTextArea areaLog;

    public TelaAdmin() {
        setTitle("Administrador - Campeonato de Apostas");
        setSize(700, 560);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        initComponents();
    }

    private void initComponents() {
        JPanel painelPrincipal = new JPanel(new BorderLayout(8, 8));
        painelPrincipal.setBackground(new Color(30, 30, 50));
        painelPrincipal.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JLabel titulo = new JLabel("⚽ Painel do Administrador", SwingConstants.CENTER);
        titulo.setFont(new Font("Arial", Font.BOLD, 18));
        titulo.setForeground(new Color(255, 200, 0));
        painelPrincipal.add(titulo, BorderLayout.NORTH);

        // Botões
        JPanel painelBotoes = new JPanel(new GridLayout(6, 1, 6, 6));
        painelBotoes.setBackground(new Color(30, 30, 50));

        String[] nomesBotoes = {
                "Cadastrar Campeonato", "Cadastrar Clube",
                "Adicionar Clube ao Campeonato", "Cadastrar Partida",
                "Registrar Resultado", "Logout"
        };

        for (String nome : nomesBotoes) {
            JButton btn = criarBotao(nome);
            painelBotoes.add(btn);
        }

        painelPrincipal.add(painelBotoes, BorderLayout.WEST);

        areaLog = new JTextArea();
        areaLog.setEditable(false);
        areaLog.setBackground(new Color(20, 20, 35));
        areaLog.setForeground(new Color(180, 255, 180));
        areaLog.setFont(new Font("Monospaced", Font.PLAIN, 12));
        JScrollPane scroll = new JScrollPane(areaLog);
        scroll.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(new Color(80, 80, 120)), "Log",
                0, 0, null, Color.WHITE));
        painelPrincipal.add(scroll, BorderLayout.CENTER);

        add(painelPrincipal);
        log("Sistema iniciado. Bem-vindo, Administrador!");
        log("Login padrão: admin / admin123");
    }

    private JButton criarBotao(String nome) {
        JButton btn = new JButton(nome);
        btn.setBackground(new Color(0, 80, 160));
        btn.setForeground(Color.WHITE);
        btn.setFont(new Font("Arial", Font.BOLD, 12));
        btn.setPreferredSize(new Dimension(200, 36));
        btn.addActionListener(e -> acaoBotao(nome));
        return btn;
    }

    private void acaoBotao(String acao) {
        switch (acao) {
            case "Cadastrar Campeonato" -> cadastrarCampeonato();
            case "Cadastrar Clube" -> cadastrarClube();
            case "Adicionar Clube ao Campeonato" -> adicionarClubeAoCampeonato();
            case "Cadastrar Partida" -> cadastrarPartida();
            case "Registrar Resultado" -> registrarResultado();
            case "Logout" -> { dispose(); new TelaLogin().setVisible(true); }
        }
    }

    private void cadastrarCampeonato() {
        String nome = JOptionPane.showInputDialog(this, "Nome do campeonato:");
        if (nome == null || nome.isBlank()) return;
        String edicao = JOptionPane.showInputDialog(this, "Edição (ex: 2026):");
        if (edicao == null) return;
        log(controller.cadastrarCampeonato(nome.trim(), edicao.trim()));
    }

    private void cadastrarClube() {
        String nome = JOptionPane.showInputDialog(this, "Nome do clube:");
        if (nome == null || nome.isBlank()) return;
        String sigla = JOptionPane.showInputDialog(this, "Sigla (ex: FLA):");
        if (sigla == null || sigla.isBlank()) return;
        log(controller.cadastrarClube(nome.trim(), sigla.trim()));
    }

    private void adicionarClubeAoCampeonato() {
        List<Campeonato> campeonatos = controller.listarCampeonatos();
        List<Clube> clubes = controller.listarClubes();
        if (campeonatos.isEmpty()) { log("Nenhum campeonato cadastrado."); return; }
        if (clubes.isEmpty()) { log("Nenhum clube cadastrado."); return; }

        Campeonato camp = (Campeonato) JOptionPane.showInputDialog(
                this, "Campeonato:", "Selecionar", JOptionPane.PLAIN_MESSAGE,
                null, campeonatos.toArray(), campeonatos.get(0));
        if (camp == null) return;

        Clube clube = (Clube) JOptionPane.showInputDialog(
                this, "Clube:", "Selecionar", JOptionPane.PLAIN_MESSAGE,
                null, clubes.toArray(), clubes.get(0));
        if (clube == null) return;

        log(controller.adicionarClubeAoCampeonato(camp, clube));
    }

    private void cadastrarPartida() {
        List<Campeonato> campeonatos = controller.listarCampeonatos();
        if (campeonatos.isEmpty()) { log("Nenhum campeonato cadastrado."); return; }

        Campeonato camp = (Campeonato) JOptionPane.showInputDialog(
                this, "Campeonato:", "Selecionar", JOptionPane.PLAIN_MESSAGE,
                null, campeonatos.toArray(), campeonatos.get(0));
        if (camp == null || camp.getClubes().size() < 2) {
            log("Campeonato precisa ter ao menos 2 clubes."); return;
        }

        Object[] clubesCamp = camp.getClubes().toArray();
        Clube mandante = (Clube) JOptionPane.showInputDialog(
                this, "Mandante:", "Selecionar", JOptionPane.PLAIN_MESSAGE,
                null, clubesCamp, clubesCamp[0]);
        if (mandante == null) return;

        Clube visitante = (Clube) JOptionPane.showInputDialog(
                this, "Visitante:", "Selecionar", JOptionPane.PLAIN_MESSAGE,
                null, clubesCamp, clubesCamp[0]);
        if (visitante == null) return;

        String dtStr = JOptionPane.showInputDialog(this, "Data e hora (dd/MM/yyyy HH:mm):");
        if (dtStr == null || dtStr.isBlank()) return;
        try {
            DateTimeFormatter fmt = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
            LocalDateTime dt = LocalDateTime.parse(dtStr.trim(), fmt);
            log(controller.cadastrarPartida(camp, mandante, visitante, dt));
        } catch (DateTimeParseException ex) {
            log("Formato de data inválido. Use: dd/MM/yyyy HH:mm");
        }
    }

    private void registrarResultado() {
        List<Campeonato> campeonatos = controller.listarCampeonatos();
        if (campeonatos.isEmpty()) { log("Nenhum campeonato cadastrado."); return; }

        Campeonato camp = (Campeonato) JOptionPane.showInputDialog(
                this, "Campeonato:", "Selecionar", JOptionPane.PLAIN_MESSAGE,
                null, campeonatos.toArray(), campeonatos.get(0));
        if (camp == null || camp.getPartidas().isEmpty()) {
            log("Nenhuma partida neste campeonato."); return;
        }

        List<Partida> partidas = camp.getPartidas();
        Partida partida = (Partida) JOptionPane.showInputDialog(
                this, "Partida:", "Selecionar", JOptionPane.PLAIN_MESSAGE,
                null, partidas.toArray(), partidas.get(0));
        if (partida == null) return;
        if (partida.isResultadoRegistrado()) { log("Resultado já registrado."); return; }

        try {
            int golsM = Integer.parseInt(JOptionPane.showInputDialog(this,
                    "Gols de " + partida.getClubeMandante().getNome() + ":"));
            int golsV = Integer.parseInt(JOptionPane.showInputDialog(this,
                    "Gols de " + partida.getClubeVisitante().getNome() + ":"));
            log(controller.registrarResultado(partida, golsM, golsV));
        } catch (NumberFormatException ex) {
            log("Número de gols inválido.");
        }
    }

    private void log(String msg) {
        areaLog.append("[" + LocalDateTime.now().toLocalTime().withNano(0) + "] " + msg + "\n");
        areaLog.setCaretPosition(areaLog.getDocument().getLength());
    }
}