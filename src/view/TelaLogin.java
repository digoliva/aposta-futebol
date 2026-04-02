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
        setSize(400, 320);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);
        initComponents();
    }

    private void initComponents() {
        JPanel painel = new JPanel(new GridBagLayout());
        painel.setBackground(new Color(30, 30, 50));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 12, 8, 12);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel titulo = new JLabel("⚽ Sistema de Apostas", SwingConstants.CENTER);
        titulo.setFont(new Font("Arial", Font.BOLD, 18));
        titulo.setForeground(new Color(255, 200, 0));
        gbc.gridx = 0; gbc.gridy = 0; gbc.gridwidth = 2;
        painel.add(titulo, gbc);

        gbc.gridwidth = 1;
        JLabel lblLogin = new JLabel("Login:");
        lblLogin.setForeground(Color.WHITE);
        gbc.gridx = 0; gbc.gridy = 1;
        painel.add(lblLogin, gbc);

        txtLogin = new JTextField(15);
        gbc.gridx = 1; gbc.gridy = 1;
        painel.add(txtLogin, gbc);

        JLabel lblSenha = new JLabel("Senha:");
        lblSenha.setForeground(Color.WHITE);
        gbc.gridx = 0; gbc.gridy = 2;
        painel.add(lblSenha, gbc);

        txtSenha = new JPasswordField(15);
        gbc.gridx = 1; gbc.gridy = 2;
        painel.add(txtSenha, gbc);

        JButton btnEntrar = new JButton("Entrar");
        btnEntrar.setBackground(new Color(0, 150, 0));
        btnEntrar.setForeground(Color.WHITE);
        btnEntrar.setFont(new Font("Arial", Font.BOLD, 13));
        gbc.gridx = 0; gbc.gridy = 3; gbc.gridwidth = 2;
        painel.add(btnEntrar, gbc);

        JButton btnCadastrar = new JButton("Cadastrar-se");
        btnCadastrar.setBackground(new Color(0, 80, 160));
        btnCadastrar.setForeground(Color.WHITE);
        gbc.gridx = 0; gbc.gridy = 4; gbc.gridwidth = 2;
        painel.add(btnCadastrar, gbc);

        btnEntrar.addActionListener(e -> fazerLogin());
        btnCadastrar.addActionListener(e -> abrirCadastro());

        txtSenha.addActionListener(e -> fazerLogin());

        add(painel);
    }

    private void fazerLogin() {
        String login = txtLogin.getText().trim();
        String senha = new String(txtSenha.getPassword());

        // Admin
        if (SistemaApostas.getInstancia().isAdministrador(login, senha)) {
            dispose();
            new TelaAdmin().setVisible(true);
            return;
        }

        // Participante
        Participante p = controller.login(login, senha);
        if (p != null) {
            dispose();
            new TelaParticipante(p).setVisible(true);
        } else {
            JOptionPane.showMessageDialog(this, "Login ou senha inválidos.", "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void abrirCadastro() {
        new TelaCadastroParticipante().setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new TelaLogin().setVisible(true));
    }
}