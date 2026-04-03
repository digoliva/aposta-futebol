package view;

import controller.ParticipanteController;
import model.Participante;
import model.SistemaApostas;

import javax.swing.*;
import java.awt.*;

public class TelaLogin extends JFrame {
    private JTextField txtLogin;
    private JPasswordField txtSenha;
    private ParticipanteController controller = new ParticipanteController();

    public TelaLogin() {
        setTitle("Campeonato de Apostas - Login");
        setSize(420, 430);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);
        initComponents();
    }

    private void initComponents() {
        JPanel painel = new JPanel(new GridBagLayout());
        painel.setBackground(new Color(30, 30, 50));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 14, 8, 14);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel titulo = new JLabel("⚽ Sistema de Apostas", SwingConstants.CENTER);
        titulo.setFont(new Font("Arial", Font.BOLD, 20));
        titulo.setForeground(new Color(255, 200, 0));
        gbc.gridx = 0; gbc.gridy = 0; gbc.gridwidth = 2;
        painel.add(titulo, gbc);

        JLabel subtitulo = new JLabel("Campeonato de Futebol", SwingConstants.CENTER);
        subtitulo.setFont(new Font("Arial", Font.PLAIN, 13));
        subtitulo.setForeground(new Color(180, 180, 200));
        gbc.gridx = 0; gbc.gridy = 1; gbc.gridwidth = 2;
        painel.add(subtitulo, gbc);

        JSeparator sep = new JSeparator();
        sep.setForeground(new Color(80, 80, 120));
        gbc.gridx = 0; gbc.gridy = 2; gbc.gridwidth = 2;
        painel.add(sep, gbc);

        gbc.gridwidth = 1;
        JLabel lblLogin = new JLabel("Login:");
        lblLogin.setForeground(Color.WHITE);
        gbc.gridx = 0; gbc.gridy = 3;
        painel.add(lblLogin, gbc);

        txtLogin = new JTextField(15);
        gbc.gridx = 1; gbc.gridy = 3;
        painel.add(txtLogin, gbc);

        JLabel lblSenha = new JLabel("Senha:");
        lblSenha.setForeground(Color.WHITE);
        gbc.gridx = 0; gbc.gridy = 4;
        painel.add(lblSenha, gbc);

        txtSenha = new JPasswordField(15);
        gbc.gridx = 1; gbc.gridy = 4;
        painel.add(txtSenha, gbc);

        JButton btnEntrar = new JButton("Entrar");
        btnEntrar.setBackground(new Color(0, 150, 0));
        btnEntrar.setForeground(Color.BLACK);
        btnEntrar.setFont(new Font("Arial", Font.BOLD, 13));
        gbc.gridx = 0; gbc.gridy = 5; gbc.gridwidth = 2;
        painel.add(btnEntrar, gbc);

        JLabel lblCadastros = new JLabel("─── Cadastros ───", SwingConstants.CENTER);
        lblCadastros.setForeground(new Color(150, 150, 180));
        lblCadastros.setFont(new Font("Arial", Font.PLAIN, 12));
        gbc.gridx = 0; gbc.gridy = 6; gbc.gridwidth = 2;
        painel.add(lblCadastros, gbc);

        JButton btnCadastrarParticipante = criarBotaoCadastro("👤 Cadastrar Participante");
        gbc.gridx = 0; gbc.gridy = 7; gbc.gridwidth = 2;
        painel.add(btnCadastrarParticipante, gbc);

        JButton btnCadastrarClube = criarBotaoCadastro("🏟️ Cadastrar Clube");
        gbc.gridx = 0; gbc.gridy = 8; gbc.gridwidth = 2;
        painel.add(btnCadastrarClube, gbc);

        JButton btnCadastrarCampeonato = criarBotaoCadastro("🏆 Cadastrar Campeonato");
        gbc.gridx = 0; gbc.gridy = 9; gbc.gridwidth = 2;
        painel.add(btnCadastrarCampeonato, gbc);

        btnEntrar.addActionListener(e -> fazerLogin());
        txtSenha.addActionListener(e -> fazerLogin());
        btnCadastrarParticipante.addActionListener(e -> new TelaCadastroParticipante(this).setVisible(true));
        btnCadastrarClube.addActionListener(e -> new TelaCadastroClube(this).setVisible(true));
        btnCadastrarCampeonato.addActionListener(e -> new TelaCadastroCampeonato(this).setVisible(true));

        add(painel);
    }

    private JButton criarBotaoCadastro(String texto) {
        JButton btn = new JButton(texto);
        btn.setBackground(new Color(0, 80, 160));
        btn.setForeground(Color.BLACK);
        btn.setFont(new Font("Arial", Font.BOLD, 12));
        return btn;
    }

    private void fazerLogin() {
        String login = txtLogin.getText().trim();
        String senha = new String(txtSenha.getPassword());

        if (SistemaApostas.getInstancia().isAdministrador(login, senha)) {
            dispose();
            new TelaAdmin().setVisible(true);
            return;
        }

        Participante p = controller.login(login, senha);
        if (p != null) {
            dispose();
            new TelaParticipante(p).setVisible(true);
        } else {
            JOptionPane.showMessageDialog(this, "Login ou senha inválidos.", "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new TelaLogin().setVisible(true));
    }
}