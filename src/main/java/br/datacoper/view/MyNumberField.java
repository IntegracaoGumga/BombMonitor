package br.datacoper.view;

import java.awt.GridLayout;

import javax.swing.BorderFactory;

/**
 *	Adiciona um Painel com um campo do tipo NumberField na tela 
 * @author dread
 *
 */
public class MyNumberField extends javax.swing.JPanel{

	private static final long serialVersionUID = 1L;

	private JNumberField numberField = new JNumberField();

	public MyNumberField(int largura, String titulo) {
		this.setSize(100, 40);
		this.setLayout(new GridLayout());
		addComponentesPadrao();
		this.setBorder(BorderFactory.createTitledBorder(titulo));
	}

	public void addComponentesPadrao() {
		this.add(numberField);
	}

	public JNumberField getNumberField() {
		return numberField;
	}

	public void setNumberField(JNumberField nField) {
		this.numberField = nField;
	}
}
