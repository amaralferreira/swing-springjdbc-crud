package br.com.yaw.ssjc.ui;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.KeyEvent;
import java.text.ParseException;

import javax.swing.JButton;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import br.com.yaw.ssjc.model.Mercadoria;


/**
 * Tela para incluir o registro da <code>Mercadoria</code>.
 * 
 * @author YaW Tecnologia
 */
public class IncluirMercadoriaFrame extends JFrame {

	private JTextField tfNome;
	private JFormattedTextField tfQuantidade;
	private JTextField tfDescricao;
	private JTextField tfPreco;
	private JFormattedTextField tfId;
	
	private JButton bSalvar;
	private JButton bCancelar;
	
	public IncluirMercadoriaFrame() {
		setTitle("Incluir");
		setSize(300,250);
		setLocationRelativeTo(null);
		setResizable(false);
		inicializaComponentes();
		resetForm();
	}
	
	private void inicializaComponentes() {
		JPanel panel = new JPanel();
		panel.setLayout(new BorderLayout());
		panel.add(montaPanelEditarMercadoria(), BorderLayout.CENTER);
		panel.add(montaPanelBotoesEditar(), BorderLayout.SOUTH);
		add(panel);
	}
	
	private JPanel montaPanelBotoesEditar() {
		JPanel panel = new JPanel();

		bSalvar = new JButton("Salvar");
		bSalvar.setActionCommand("salvarIncluirMercadoriaAction");
		bSalvar.setMnemonic(KeyEvent.VK_M);
		panel.add(bSalvar);

		bCancelar = new JButton("Cancelar");
		bCancelar.setActionCommand("cancelarIncluirMercadoriaAction");
		bCancelar.setMnemonic(KeyEvent.VK_C);
		panel.add(bCancelar);

		return panel;
	}

	private JPanel montaPanelEditarMercadoria() {
		JPanel painel = new JPanel();
		GridLayout layout = new GridLayout(8, 1);
		painel.setLayout(layout);
		
		tfNome = new JTextField();
		tfDescricao = new JTextField();
		tfPreco = new JTextField();
		tfQuantidade = new JFormattedTextField();
		tfQuantidade.setValue(new Integer(1));
		tfId = new JFormattedTextField();
		tfId.setValue(new Integer(0));
		tfId.setEnabled(false);
		
		painel.add(new JLabel("Nome:"));
		painel.add(tfNome);
		painel.add(new JLabel("Descricao:"));
		painel.add(tfDescricao);
		painel.add(new JLabel("Preco:"));
		painel.add(tfPreco);
		painel.add(new JLabel("Quantidade:"));
		painel.add(tfQuantidade);
		painel.add(new JLabel("Id: "));
		painel.add(tfId);

		return painel;
	}
	
	
	private String validador() {
		StringBuilder sb = new StringBuilder();
		sb.append(tfNome.getText() == null || "".equals(tfNome.getText().trim()) ? "Nome, " : "");
		sb.append(tfPreco.getText() == null || "".equals(tfPreco.getText().trim()) ? "Preco, " : "");
		sb.append(tfQuantidade.getText() == null || "".equals(tfQuantidade.getText().trim()) ? "Quantidade, " : "");
		
		if (!sb.toString().isEmpty()) {
			sb.delete(sb.toString().length()-2, sb.toString().length());
		}
		return sb.toString();
	}
	
	private Mercadoria loadMercadoriaFromPanel() {
		String msg = validador();
		if (!msg.isEmpty()) {
			throw new RuntimeException("Informe o(s) campo(s): "+msg);
		}
		
		String nome = tfNome.getText();
		String descricao = tfDescricao.getText();
		
		Integer quantidade = null;
		try {
			quantidade = Integer.valueOf(tfQuantidade.getText());
		} catch (NumberFormatException nex) {
			throw new RuntimeException("Campo quantidade com conteudo invalido!");
		}
		
		Double preco = null;
		try {
			preco = Mercadoria.formatStringToPreco(tfPreco.getText());
		} catch (ParseException nex) {
			throw new RuntimeException("Campo preco com conteudo invalido!");
		}
		
		Integer id = null;
		try {
			id = Integer.parseInt(tfId.getText());
		} catch (Exception nex) {}
		
		return new Mercadoria(id, nome, descricao, quantidade, preco);
	}
	
	public void resetForm() {
		tfId.setValue(null);
		tfNome.setText("");
		tfDescricao.setText("");
		tfPreco.setText("");
		tfQuantidade.setValue(new Integer(1));
	}
	
	private void populaTextFields(Mercadoria m){
		tfId.setValue(m.getId());
		tfNome.setText(m.getNome());
		tfDescricao.setText(m.getDescricao());
		tfQuantidade.setValue(m.getQuantidade());
		tfPreco.setText(m.getPrecoFormatado());
	}
	
	public void setMercadoria(Mercadoria m){
		resetForm();
		if (m != null) {
			populaTextFields(m);
		}
	}
	
	public Mercadoria getMercadoria() {
		return loadMercadoriaFromPanel();
	}
	
	public JButton getSalvarButton() {
		return bSalvar;
	}
	
	public JButton getCancelarButton() {
		return bCancelar;
	}
}