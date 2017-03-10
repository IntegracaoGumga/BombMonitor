/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.datacoper.view;

import java.awt.GridLayout;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import javax.swing.BorderFactory;
import javax.swing.JCheckBox;

/**
 *
 * @author daniel.tokarski
 */
public class MyCheckBox extends javax.swing.JPanel {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JCheckBox cb;
	private Object objetosDesativar[];
	
	public MyCheckBox(int largura, String titulo, String opcao) {
		setConfigPadrao();
		cb = new JCheckBox(opcao);
		addComponentesPadrao();
		this.setBorder(BorderFactory.createTitledBorder(titulo));		
	}
	
	public MyCheckBox(int largura, String titulo, String opcao, Object[] objs) {
		this.objetosDesativar = objs;		
		setConfigPadrao();
		cb = new JCheckBox(opcao);
		setObjetosDesativar(objs);
		addComponentesPadrao();
		this.setBorder(BorderFactory.createTitledBorder(titulo));
				
	}
	
	public void setConfigPadrao(){
		this.setSize(100, 40);
		this.setLayout(new GridLayout());
				
	}
	public void addComponentesPadrao(){		
		this.add(cb);		
	}

	public JCheckBox getCb() {
		return cb;
	}

	public void setCb(JCheckBox cb) {
		this.cb = cb;
	}

	public Object[] getObjetosDesativar() {
		return objetosDesativar;
	}

	public void setObjetosDesativar(Object[] objetosDesativar) {
		this.objetosDesativar = objetosDesativar;
		for (int i = 0; i < this.objetosDesativar.length; i++) {
			((MyTextField) this.objetosDesativar[i]).getTf().setEditable(false);
			//((MyTextField) this.objetosDesativar[i]).getTf().setText("");
		}		
		cb.addItemListener(testeListener());					
	}	
	private ItemListener testeListener(){
		return new ItemListener() {			
			@Override
			public void itemStateChanged(ItemEvent arg0) {				
				for (int i = 0; i < objetosDesativar.length; i++) {					
					if(arg0.getStateChange() == ItemEvent.SELECTED){
						((MyTextField) objetosDesativar[i]).getTf().setEditable(true);					
					}else{
						((MyTextField) objetosDesativar[i]).getTf().setEditable(false);
						((MyTextField) objetosDesativar[i]).getTf().setText("");
					}
				}				
			}
		};
		
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
