package view;

import controller.ParticipanteController;

import javax.swing.*;
import java.awt.*;

public class TelaCadastroParticipante extends JDialog {
    private JTextField txtNome, txtLogin;
    private JPasswordField txtSenha;
    private ParticipanteController controller = new ParticipanteController();

    public TelaCadastroParticipante(JFrame parent) {
        super(parent, "Cadastro de Participante", true);
        setSize(380, 280);
        setLocationRelativeTo(parent);
        setResizable(false);
        initComponents();
    }

    private void initComponents() {
        JPanel painel = new JPanel(new GridBagLayout());
        painel.setBackground(new Color(40, 40, 60));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 12, 8, 12);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel titulo = new JLabel("Novo Participante", SwingConstants.CENTER);
        titulo.setFont(new Font("Arial", Font.BOLD, 16));
        titulo.setForeground(new Color(255, 200, 0));
        gbc.gridx = 0; gbc.gridy = 0; gbc.gridwidth = 2;
        painel.add(titulo, gbc);

        gbc.gridwidth = 1;
        String[] labels = {"Nome:", "Login:", "Senha:"};
        for (int i = 0; i < labels.length; i++) {
            JLabel lbl = new JLabel(labels[i]);
            lbl.setForeground(Color.WHITE);
            gbc.gridx = 0; gbc.gridy = i + 1;
            painel.add(lbl, gbc);
        }

        txtNome = new JTextField(15);
        gbc.gridx = 1; gbc.gridy = 1;
        painel.add(txtNome, gbc);

        txtLogin = new JTextField(15);
        gbc.gridx = 1; gbc.gridy = 2;
        painel.add(txtLogin, gbc);

        txtSenha = new JPasswordField(15);
        gbc.gridx = 1; gbc.gridy = 3;
        painel.add(txtSenha, gbc);

        JButton btnSalvar = new JButton("Cadastrar");
        btnSalvar.setBackground(new Color(0, 150, 0));
        btnSalvar.setForeground(Color.BLACK);
        btnSalvar.setFont(new Font("Arial", Font.BOLD, 13));
        gbc.gridx = 0; gbc.gridy = 4; gbc.gridwidth = 2;
        painel.add(btnSalvar, gbc);

        btnSalvar.addActionListener(e -> cadastrar());
        add(painel);
    }

    private void cadastrar() {
        String msg = controller.cadastrarParticipante(
                txtNome.getText().trim(),
                txtLogin.getText().trim(),
                new String(txtSenha.getPassword())
        );
        JOptionPane.showMessageDialog(this, msg);
        if (msg.contains("sucesso")) dispose();
    }
}