package view;

import controller.CampeonatoController;

import javax.swing.*;
import java.awt.*;

public class TelaCadastroClube extends JDialog {
    private JTextField txtNome, txtSigla;
    private CampeonatoController controller = new CampeonatoController();

    public TelaCadastroClube(JFrame parent) {
        super(parent, "Cadastro de Clube", true);
        setSize(380, 260);
        setLocationRelativeTo(parent);
        setResizable(false);
        initComponents();
    }

    private void initComponents() {
        JPanel painel = new JPanel(new GridBagLayout());
        painel.setBackground(new Color(40, 40, 60));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 12, 10, 12);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel titulo = new JLabel("Cadastro de Clube", SwingConstants.CENTER);
        titulo.setFont(new Font("Arial", Font.BOLD, 16));
        titulo.setForeground(new Color(255, 200, 0));
        gbc.gridx = 0; gbc.gridy = 0; gbc.gridwidth = 2;
        painel.add(titulo, gbc);

        gbc.gridwidth = 1;

        JLabel lblNome = new JLabel("Nome do Clube:");
        lblNome.setForeground(Color.WHITE);
        gbc.gridx = 0; gbc.gridy = 1;
        painel.add(lblNome, gbc);

        txtNome = new JTextField(15);
        gbc.gridx = 1; gbc.gridy = 1;
        painel.add(txtNome, gbc);

        JLabel lblSigla = new JLabel("Sigla (ex: FLA):");
        lblSigla.setForeground(Color.WHITE);
        gbc.gridx = 0; gbc.gridy = 2;
        painel.add(lblSigla, gbc);

        txtSigla = new JTextField(6);
        gbc.gridx = 1; gbc.gridy = 2;
        painel.add(txtSigla, gbc);

        JButton btnSalvar = new JButton("Cadastrar");
        btnSalvar.setBackground(new Color(0, 150, 0));
        btnSalvar.setForeground(Color.BLACK);
        btnSalvar.setFont(new Font("Arial", Font.BOLD, 13));
        gbc.gridx = 0; gbc.gridy = 3; gbc.gridwidth = 2;
        painel.add(btnSalvar, gbc);

        btnSalvar.addActionListener(e -> cadastrar());

        add(painel);
    }

    private void cadastrar() {
        String msg = controller.cadastrarClube(
                txtNome.getText().trim(),
                txtSigla.getText().trim()
        );
        JOptionPane.showMessageDialog(this, msg);
        if (msg.contains("sucesso")) {
            txtNome.setText("");
            txtSigla.setText("");
        }
    }
}