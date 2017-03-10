/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
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
	private JTextField tf = new JTextField();
	public MyTextField(int largura, String titulo) {
		this.setSize(100, 40);
		this.setLayout(new GridLayout());		
		addComponentesPadrao();
		this.setBorder(BorderFactory.createTitledBorder(titulo));		
		//tf.setVisible(true);
	}
	
	public void addComponentesPadrao(){		
		this.add(tf);		
	}

	public JTextField getTf() {
		return tf;
	}

	public void setTf(JTextField tf) {
		this.tf = tf;
	}
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 400, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 300, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}
