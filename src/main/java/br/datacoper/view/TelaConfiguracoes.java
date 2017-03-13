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
 * @author Dread
 */
public class TelaConfiguracoes extends javax.swing.JFrame {

	private static final long serialVersionUID = 1L;

	public static final int LARGURA = 600;
	public static final int ALTURA = 450;

	private static Configuracoes config = Configuracoes.getInstance();

	JPanel jpTelaConfiguracoes = new JPanel();

	Dimension dimensaoTela = Toolkit.getDefaultToolkit().getScreenSize();

	private MyTextField mtf_filtro_prefixo = new MyTextField(LARGURA, "FILTRAR PREFIXO");
	private MyTextField mtf_filtro_extensao = new MyTextField(LARGURA, "FILTRAR EXTENSAO");
	private MyTextField mtf_diretorio_imagem = new MyTextField(LARGURA, "IMAGEM");
	private MyTextField mtf_diretorio_importacao = new MyTextField(LARGURA, "DIRETORIO IMPORTACAO");
	private MyTextField mtf_diretorio_importados = new MyTextField(LARGURA, "DIRETORIO DESTINO");
	private MyTextField mtf_diretorio_log = new MyTextField(LARGURA, "DIRETORIO LOG");

	private MyCheckBox mcb_gera_log = new MyCheckBox(LARGURA, "GERAR LOG", "");
	private MyCheckBox mcb_debug = new MyCheckBox(LARGURA, "DEBUG", "");
	private MyCheckBox mcb_mover_arquivo_importado = new MyCheckBox(LARGURA, "MOVER ARQUIVOS TRANSFERIDOS", "");

	private JButton bntSalvar = new JButton("Salvar");

	/**
	 *
	 */
	public TelaConfiguracoes() {
		super("CONFIGURAÇÕES - MOTOMCO VERSAO 3.0");
		setSize(LARGURA, ALTURA);
		setLocation((dimensaoTela.width - this.getSize().width) / 2, (dimensaoTela.height - this.getSize().height) / 2);
		mcb_mover_arquivo_importado.getCb().setSelected(false);

		bntSalvar.addActionListener(actionSalvar());

		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		this.setSize(LARGURA, ALTURA);
		this.setResizable(false);

		jpTelaConfiguracoes.setSize(LARGURA, ALTURA);
		jpTelaConfiguracoes.setLayout(new GridLayout(0, 1));

		this.setLayout(new GridLayout(0, 1));

		carregarParametros();

		adicionarComponentes();
	}

	/**
	 *
	 */
	public void adicionarComponentes() {
		/* Socket */
		jpTelaConfiguracoes.add(mtf_filtro_prefixo);
		jpTelaConfiguracoes.add(mtf_filtro_extensao);
		jpTelaConfiguracoes.add(mtf_diretorio_imagem);
		jpTelaConfiguracoes.add(mtf_diretorio_importacao);
		jpTelaConfiguracoes.add(mtf_diretorio_importados);
		jpTelaConfiguracoes.add(mtf_diretorio_log);
		jpTelaConfiguracoes.add(mcb_gera_log);
		jpTelaConfiguracoes.add(mcb_debug);
		jpTelaConfiguracoes.add(mcb_mover_arquivo_importado);
		jpTelaConfiguracoes.add(bntSalvar);

		this.getContentPane().add(jpTelaConfiguracoes, BorderLayout.CENTER);

	}

	/**
	 *
	 */
	public void carregarParametros() {

		mcb_debug.getCb().setSelected(false);
		mcb_gera_log.getCb().setSelected(false);
		mcb_mover_arquivo_importado.getCb().setSelected(false);

		mtf_diretorio_log.getTf().setText(config.getParam(Configuracoes.PARAM_DIRETORIO_LOG));
		mtf_diretorio_importados.getTf().setText(config.getParam(Configuracoes.PARAM_DIRETORIO_IMPORTADOS));
		mtf_diretorio_importacao.getTf().setText(config.getParam(Configuracoes.PARAM_DIRETORIO_IMPORTACAO));
		mtf_diretorio_imagem.getTf().setText(config.getParam(Configuracoes.PARAM_DIRETORIO_IMAGEM));
		mtf_filtro_extensao.getTf().setText(config.getParam(Configuracoes.PARAM_FILTRO_EXTENSAO));
		mtf_filtro_prefixo.getTf().setText(config.getParam(Configuracoes.PARAM_FILTRO_PREFIXO));

		if (config.getParam(Configuracoes.PARAM_MOVER_ARQUIVO_IMPORTADO).equals(Configuracoes.TRUE)) {
			mcb_mover_arquivo_importado.getCb().setSelected(true);
		}
		if (config.getParam(Configuracoes.PARAM_GERA_LOG).equals(Configuracoes.TRUE)) {
			mcb_gera_log.getCb().setSelected(true);
		}
		if (config.getParam(Configuracoes.PARAM_DEBUG).equals(Configuracoes.TRUE)) {
			mcb_debug.getCb().setSelected(true);
		}
	}

	/**
	 * Salva os parametros da aplicacao
	 * 
	 * @return True se os parametros forem salvos corretamente, false se houver
	 *         algum erro.
	 */
	public void salvar() {

		config.setParam(Configuracoes.PARAM_DIRETORIO_LOG, mtf_diretorio_log.getTf().getText());
		config.setParam(Configuracoes.PARAM_DIRETORIO_IMPORTADOS, mtf_diretorio_importados.getTf().getText());
		config.setParam(Configuracoes.PARAM_DIRETORIO_IMPORTACAO, mtf_diretorio_importacao.getTf().getText());
		config.setParam(Configuracoes.PARAM_DIRETORIO_IMAGEM, mtf_diretorio_imagem.getTf().getText());
		config.setParam(Configuracoes.PARAM_FILTRO_EXTENSAO, mtf_filtro_extensao.getTf().getText());
		config.setParam(Configuracoes.PARAM_FILTRO_PREFIXO, mtf_filtro_prefixo.getTf().getText());

		if (mcb_mover_arquivo_importado.getCb().isSelected()) {
			config.setParam(Configuracoes.PARAM_MOVER_ARQUIVO_IMPORTADO, Configuracoes.TRUE);
		} else {
			config.setParam(Configuracoes.PARAM_MOVER_ARQUIVO_IMPORTADO, Configuracoes.FALSE);
		}

		if (mcb_gera_log.getCb().isSelected()) {
			config.setParam(Configuracoes.PARAM_GERA_LOG, Configuracoes.TRUE);
		} else {
			config.setParam(Configuracoes.PARAM_GERA_LOG, Configuracoes.FALSE);
		}

		if (mcb_debug.getCb().isSelected()) {
			config.setParam(Configuracoes.PARAM_DEBUG, Configuracoes.TRUE);
		} else {
			config.setParam(Configuracoes.PARAM_DEBUG, Configuracoes.FALSE);
		}

		config.gravaParametros();

		JOptionPane.showMessageDialog(null, "SALVO COM SECESSO!" + "\n"
				+ "A APLICACAO DEVE SER REINICIADA PARA QUE OS PARAMETROS SEJAM CARREGADOS NOVAMENTE!");
	}

	/**
	 * Adiciona a açao salvar ao botão
	 * 
	 * @return ActionListener para o botao salvar
	 */
	public ActionListener actionSalvar() {
		return new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				salvar();

			}
		};
	}
}
