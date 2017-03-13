package br.datacoper.view;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JTextField;
import javax.swing.filechooser.FileNameExtensionFilter;

/**
 * Inclui um TextField + Button para buscar Diretorio
 * 
 * @author dread
 *
 */
public class MyFolderField extends javax.swing.JPanel {

	private static final long serialVersionUID = 1L;

	private JButton buttonFind = new JButton("Buscar");
	private JFileChooser arquivo = new JFileChooser();
	private JTextField textField = new JTextField();

	public MyFolderField(final int largura, final String titulo) {
		this.setSize(largura, 40);
		this.setLayout(new GridLayout());
		addComponentesPadrao(largura);
		this.setBorder(BorderFactory.createTitledBorder(titulo));
	}

	public void addComponentesPadrao(final int largura) {
		ActionListener procurarPasta = new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFileChooser arquivo = new JFileChooser();
				arquivo.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);

				FileNameExtensionFilter filtroPDF = new FileNameExtensionFilter("Pastas", "folder");
				arquivo.addChoosableFileFilter(filtroPDF);
				arquivo.setAcceptAllFileFilterUsed(false);

			}
		};
		buttonFind.addActionListener(procurarPasta);
		this.add(textField).setEnabled(false);
		this.add(buttonFind);
	}

	@SuppressWarnings("unchecked")
	private void initComponents() {
		javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
		this.setLayout(layout);
		layout.setHorizontalGroup(
				layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addGap(0, 400, Short.MAX_VALUE));
		layout.setVerticalGroup(
				layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addGap(0, 300, Short.MAX_VALUE));
	}

	/**
	 * @return the buttonFind
	 */
	public JButton getButtonFind() {
		return buttonFind;
	}

	/**
	 * @param buttonFind
	 *            the buttonFind to set
	 */
	public void setButtonFind(JButton buttonFind) {
		this.buttonFind = buttonFind;
	}

	/**
	 * @return the textField
	 */
	public JTextField getTextField() {
		return textField;
	}

	/**
	 * @param textField
	 *            the textField to set
	 */
	public void setTextField(JTextField textField) {
		this.textField = textField;
	}
}
