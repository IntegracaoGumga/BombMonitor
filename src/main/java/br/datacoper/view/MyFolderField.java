package br.datacoper.view;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JTextField;

/**
 * Inclui um TextField + Button para buscar Diretorio
 * 
 * @author dread
 *
 */
public class MyFolderField extends javax.swing.JPanel {

	private static final long serialVersionUID = 1L;

	private JButton buttonFind = new JButton("Buscar");
	private JTextField textField = new JTextField();

	/**
	 * Metodo construtor
	 * 
	 * @param largura
	 * @param titulo
	 */
	public MyFolderField(final int largura, final String titulo) {
		this.setSize(largura, 40);
		this.setLayout(new BorderLayout());
		addComponentesPadrao(largura);
		this.setBorder(BorderFactory.createTitledBorder(titulo));
	}

	/**
	 * Adicona os componentes no panel
	 * 
	 * @param largura
	 */
	public void addComponentesPadrao(final int largura) {
		buttonFind.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				JFileChooser arquivo = new JFileChooser();
				arquivo.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
				arquivo.setCurrentDirectory(new File(System.getProperty("user.dir")));
				arquivo.setAcceptAllFileFilterUsed(false);
				arquivo.showSaveDialog(null);
				if (arquivo.getSelectedFile() != null) {
					textField.setText(arquivo.getSelectedFile().toString());
				}
			}
		});
		this.add(textField).setEnabled(false);
		this.add(buttonFind, BorderLayout.LINE_END);
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
