package view;

import controller.CampeonatoController;
import model.Campeonato;
import model.Clube;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class TelaCadastroCampeonato extends JDialog {
    private JTextField txtNome, txtEdicao;
    private JList<Clube> listaClubes;
    private CampeonatoController controller = new CampeonatoController();

    public TelaCadastroCampeonato(JFrame parent) {
        super(parent, "Cadastro de Campeonato", true);
        setSize(460, 420);
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

        JLabel titulo = new JLabel("Cadastro de Campeonato", SwingConstants.CENTER);
        titulo.setFont(new Font("Arial", Font.BOLD, 16));
        titulo.setForeground(new Color(255, 200, 0));
        gbc.gridx = 0; gbc.gridy = 0; gbc.gridwidth = 2;
        painel.add(titulo, gbc);

        gbc.gridwidth = 1;

        JLabel lblNome = new JLabel("Nome:");
        lblNome.setForeground(Color.WHITE);
        gbc.gridx = 0; gbc.gridy = 1;
        painel.add(lblNome, gbc);

        txtNome = new JTextField(15);
        gbc.gridx = 1; gbc.gridy = 1;
        painel.add(txtNome, gbc);

        JLabel lblEdicao = new JLabel("Edição (ex: 2026):");
        lblEdicao.setForeground(Color.WHITE);
        gbc.gridx = 0; gbc.gridy = 2;
        painel.add(lblEdicao, gbc);

        txtEdicao = new JTextField(10);
        gbc.gridx = 1; gbc.gridy = 2;
        painel.add(txtEdicao, gbc);

        // Lista de clubes para adicionar ao campeonato
        JLabel lblClubes = new JLabel("Selecione os Clubes (máx. 8):");
        lblClubes.setForeground(Color.WHITE);
        gbc.gridx = 0; gbc.gridy = 3; gbc.gridwidth = 2;
        painel.add(lblClubes, gbc);

        List<Clube> clubesCadastrados = controller.listarClubes();
        DefaultListModel<Clube> modelo = new DefaultListModel<>();
        clubesCadastrados.forEach(modelo::addElement);

        listaClubes = new JList<>(modelo);
        listaClubes.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
        listaClubes.setBackground(new Color(55, 55, 75));
        listaClubes.setForeground(Color.WHITE);
        listaClubes.setFont(new Font("Arial", Font.PLAIN, 13));

        JScrollPane scroll = new JScrollPane(listaClubes);
        scroll.setPreferredSize(new Dimension(300, 120));
        gbc.gridx = 0; gbc.gridy = 4; gbc.gridwidth = 2;
        painel.add(scroll, gbc);

        JLabel lblDica = new JLabel("* Use Ctrl+Clique para selecionar múltiplos", SwingConstants.CENTER);
        lblDica.setForeground(new Color(180, 180, 180));
        lblDica.setFont(new Font("Arial", Font.ITALIC, 11));
        gbc.gridx = 0; gbc.gridy = 5; gbc.gridwidth = 2;
        painel.add(lblDica, gbc);

        JButton btnSalvar = new JButton("Cadastrar Campeonato");
        btnSalvar.setBackground(new Color(0, 150, 0));
        btnSalvar.setForeground(Color.BLACK);
        btnSalvar.setFont(new Font("Arial", Font.BOLD, 13));
        gbc.gridx = 0; gbc.gridy = 6; gbc.gridwidth = 2;
        painel.add(btnSalvar, gbc);

        btnSalvar.addActionListener(e -> cadastrar());

        add(painel);
    }

    private void cadastrar() {
        String nome = txtNome.getText().trim();
        String edicao = txtEdicao.getText().trim();

        if (nome.isBlank() || edicao.isBlank()) {
            JOptionPane.showMessageDialog(this, "Preencha todos os campos.", "Atenção", JOptionPane.WARNING_MESSAGE);
            return;
        }

        String msg = controller.cadastrarCampeonato(nome, edicao);
        JOptionPane.showMessageDialog(this, msg);

        if (msg.contains("sucesso")) {
            // Adicionar clubes selecionados ao campeonato recém-criado
            List<Clube> selecionados = listaClubes.getSelectedValuesList();
            Campeonato campeonato = controller.listarCampeonatos().get(controller.listarCampeonatos().size() - 1);

            int adicionados = 0;
            for (Clube c : selecionados) {
                String r = controller.adicionarClubeAoCampeonato(campeonato, c);
                if (r.contains("adicionado")) adicionados++;
            }

            if (adicionados > 0)
                JOptionPane.showMessageDialog(this, adicionados + " clube(s) adicionado(s) ao campeonato.");

            dispose();
        }
    }
}