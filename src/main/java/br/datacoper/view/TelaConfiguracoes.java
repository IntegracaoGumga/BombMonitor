/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.datacoper.view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;

import br.datacoper.model.Configuracoes;


/**
 *
 * @author daniel.tokarski
 */
public class TelaConfiguracoes extends javax.swing.JFrame {

    private static final long serialVersionUID = 1L;

    /**
     *
     */
    public static final int LARGURA = 600;

    /**
     *
     */
    public static final int ALTURA = 450;
    
    JPanel jpSerial = new JPanel();
    JPanel jpSocket = new JPanel();
    JPanel jpProgramador = new JPanel();
    
    JTabbedPane jtpTabs = new JTabbedPane();
    Dimension tela = Toolkit.getDefaultToolkit().getScreenSize();

    private static Configuracoes conf = Configuracoes.getInstance();
    private MyTextField mtf_portaservidor = new MyTextField(LARGURA, "PORTA SERVIDOR");
    private MyTextField mtf_diretorioArquivos = new MyTextField(LARGURA, "DIRETORIO DOS ARQUIVOS");
    private MyTextField mtf_filtroExtensao = new MyTextField(LARGURA, "EXTENSAO DOS ARQUIVOS");
    private MyTextField mtf_filtroPrefixo = new MyTextField(LARGURA, "PREFIXO DOS ARQUIVOS");
    private MyTextField mtf_diretorioDestino = new MyTextField(LARGURA, "DIRETORIO DESTINO");
    private MyTextField mtf_baund_rate       = new MyTextField(LARGURA,"BAUND RATE");
    private MyTextField mtf_porta_serial     = new MyTextField(LARGURA,"PORTA SERIAL");
    private MyTextField mtf_nome_arquivo     = new MyTextField(LARGURA,"NOME ARQUIVO");
    private MyTextField mtf_unidade          = new MyTextField(LARGURA,"UNIDADE(FILIAL)");
    
    
    private MyCheckBox mcb_moveArquivos = new MyCheckBox(LARGURA, "MOVE ARQUIVOS", "SIM");
    private MyCheckBox mcb_debug = new MyCheckBox(LARGURA, "DEBUG", "SIM");
    private MyCheckBox mcb_geraLog = new MyCheckBox(LARGURA, "GERA LOG", "SIM");
    private JButton btSalvarSocket = new JButton("Salvar");
    private JButton btSalvarSerial = new JButton("Salvar");
    
    /**
     *
     */
    public TelaConfiguracoes() {
        super("CONFIGURAÇÕES - MOTOMCO VERSAO 3.0");
        setSize(LARGURA, ALTURA); //ponha aqui suas medidas
        setLocation((tela.width - this.getSize().width) / 2,
                (tela.height - this.getSize().height) / 2);
        mcb_moveArquivos.getCb().setSelected(false);
        
        btSalvarSocket.addActionListener(actionSalvar());
        btSalvarSerial.addActionListener(actionSalvar());
        
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.setSize(LARGURA, ALTURA);
        this.setResizable(false);
        
        jpSocket.setSize(LARGURA,ALTURA);
        jpSocket.setLayout(new GridLayout(0, 1));
        jpSerial.setSize(LARGURA,ALTURA);
        jpSerial.setLayout(new GridLayout(9, 1));
        
        this.setLayout(new GridLayout(0, 1));
        carregaParametros();
        addComponentes();
    }

    /**
     *
     */
    public void addComponentes() {
        /*Socket*/
        jpSocket.add(mtf_portaservidor);
        jpSocket.add(mtf_diretorioArquivos);
        jpSocket.add(mtf_filtroExtensao);
        jpSocket.add(mtf_filtroPrefixo);
        jpSocket.add(mcb_moveArquivos);
        jpSocket.add(mtf_diretorioDestino);
        jpSocket.add(mcb_debug);
        jpSocket.add(mcb_geraLog);
        jpSocket.add(btSalvarSocket);
        
        /*Serial*/
        jpSerial.add(mtf_unidade);
        jpSerial.add(mtf_nome_arquivo);
        jpSerial.add(mtf_porta_serial);
        jpSerial.add(mtf_baund_rate);
        jpSerial.add(btSalvarSerial);
        jtpTabs.addTab("SERVIDOR SOCKET", jpSocket);
        jtpTabs.addTab("PORTA SERIAL"   , jpSerial);
        
        this.getContentPane().add(jtpTabs, BorderLayout.CENTER);
       
    }

    /**
     *
     */
    public void carregaParametros() {
        
        mcb_debug.getCb().setSelected(false);
        mcb_geraLog.getCb().setSelected(false);
        mcb_moveArquivos.getCb().setSelected(false);
        mtf_portaservidor.getTf().setText(conf.getParam(Configuracoes.PARAM_PORTASERVIDOR));
        mtf_diretorioArquivos.getTf().setText(conf.getParam(Configuracoes.PARAM_DIRETORIOARQUIVOS));
        mtf_filtroExtensao.getTf().setText(conf.getParam(Configuracoes.PARAM_FILTROEXTENSAO));
        mtf_filtroPrefixo.getTf().setText(conf.getParam(Configuracoes.PARAM_FILTROPREFIXO));
        mtf_diretorioDestino.getTf().setText(conf.getParam(Configuracoes.PARAM_DIRETORIODESTINOARQUIVO));
        mcb_moveArquivos.setObjetosDesativar(new Object[]{mtf_diretorioDestino});
        
        mtf_baund_rate.getTf().setText(conf.getParam(Configuracoes.PARAM_BAUND_RATE));
        mtf_porta_serial.getTf().setText(conf.getParam(Configuracoes.PARAM_PORTA_SERIAL));
        mtf_nome_arquivo.getTf().setText(conf.getParam(Configuracoes.PARAM_NOME_ARQUIVO));
        mtf_unidade.getTf().setText(conf.getParam(Configuracoes.PARAM_UNIDADE));
        
        if (conf.getParam(Configuracoes.PARAM_MOVERARQUIVOTRANSFERIDO).equals(Configuracoes.TRUE)) {
            mcb_moveArquivos.getCb().setSelected(true);
        }
        if (conf.getParam(Configuracoes.PARAM_GERALOG).equals(Configuracoes.TRUE)) {
            mcb_geraLog.getCb().setSelected(true);
        }
        if (conf.getParam(Configuracoes.PARAM_DEBUG).equals(Configuracoes.TRUE)) {
            mcb_debug.getCb().setSelected(true);
        }
    }

    /**
     *
     */
    public void salvar() {
        /*Socket*/
        conf.setParam(Configuracoes.PARAM_PORTASERVIDOR, mtf_portaservidor.getTf().getText());
        conf.setParam(Configuracoes.PARAM_DIRETORIOARQUIVOS, mtf_diretorioArquivos.getTf().getText());
        conf.setParam(Configuracoes.PARAM_FILTROEXTENSAO, mtf_filtroExtensao.getTf().getText());
        conf.setParam(Configuracoes.PARAM_FILTROPREFIXO, mtf_filtroPrefixo.getTf().getText());
        conf.setParam(Configuracoes.PARAM_DIRETORIODESTINOARQUIVO, mtf_diretorioDestino.getTf().getText());
        
        /*Serial*/
        conf.setParam(Configuracoes.PARAM_BAUND_RATE,mtf_baund_rate.getTf().getText());
        conf.setParam(Configuracoes.PARAM_PORTA_SERIAL,mtf_porta_serial.getTf().getText());
        conf.setParam(Configuracoes.PARAM_NOME_ARQUIVO,mtf_nome_arquivo.getTf().getText());
        conf.setParam(Configuracoes.PARAM_UNIDADE,mtf_unidade.getTf().getText());
        if (mtf_unidade.getTf().getText().equals("")){
            JOptionPane.showMessageDialog(null,"Favor cadastrar o parametro UNIDADE(FILIAL)!","",0);
            System.exit(0);
        }
        
        if (mcb_moveArquivos.getCb().isSelected()) {
            conf.setParam(Configuracoes.PARAM_MOVERARQUIVOTRANSFERIDO, Configuracoes.TRUE);

        } else {
            conf.setParam(Configuracoes.PARAM_MOVERARQUIVOTRANSFERIDO, "");
        }
        if (mcb_geraLog.getCb().isSelected()) {
            conf.setParam(Configuracoes.PARAM_GERALOG, Configuracoes.TRUE);
        } else {
            conf.setParam(Configuracoes.PARAM_GERALOG, "");
        }
        if (mcb_debug.getCb().isSelected()) {
            conf.setParam(Configuracoes.PARAM_DEBUG, Configuracoes.TRUE);
        } else {
            conf.setParam(Configuracoes.PARAM_DEBUG, "");
        }
        conf.gravaParametros();
        JOptionPane.showMessageDialog(null, "SALVO COM SECESSO!" + "\n" + "FAVOR REINICIAR A APLICACAO PARA QUE OS PARAMETROS SEJAM CARREGADOS NOVAMENTE!");
    }

    /**
     *
     * @return
     */
    public ActionListener actionSalvar() {
        return new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent arg0) {
                salvar();

            }
        };
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 400, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 300, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
 
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Windows".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(TelaConfiguracoes.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(TelaConfiguracoes.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(TelaConfiguracoes.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(TelaConfiguracoes.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
         
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new TelaConfiguracoes().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}
