package br.datacoper.view;

import java.awt.GridLayout;
import javax.swing.BorderFactory;
import javax.swing.JTextField;

/**
 *
 * @author daniel.tokarski
 */
public class MyTextField extends javax.swing.JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private JTextField textField = new JTextField();

	public MyTextField(int largura, String titulo) {
		this.setSize(100, 40);
		this.setLayout(new GridLayout());
		addComponentesPadrao();
		this.setBorder(BorderFactory.createTitledBorder(titulo));
	}

	public void addComponentesPadrao() {
		this.add(textField);
	}

	public JTextField getTextField() {
		return textField;
	}

	public void setTextField(JTextField tf) {
		this.textField = tf;
	}
}
