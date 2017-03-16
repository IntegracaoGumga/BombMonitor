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
import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import org.apache.log4j.Logger;

import br.datacoper.model.Configuracoes;

/**
 * Singleton de tela de configuracoes
 * 
 * @author Dread
 */
public class TelaConfiguracoes extends javax.swing.JFrame {

	private static final long serialVersionUID = 1L;

	private static TelaConfiguracoes telaConfiguracoes = new TelaConfiguracoes();
	
	public static final int LARGURA = 600;
	public static final int ALTURA = 400;

	private static Configuracoes config = Configuracoes.getInstancia();

	Logger logger = Logger.getLogger("br.datacoper.view.TelaConfiguracoes");

	JPanel jpTelaConfiguracoes = new JPanel();

	Dimension dimensaoTela = Toolkit.getDefaultToolkit().getScreenSize();

	private MyTextField mtfFiltroPrefixo = new MyTextField(LARGURA, "FILTRAR PREFIXO");
	private MyTextField mtfFiltroExtensao = new MyTextField(LARGURA, "FILTRAR EXTENSAO");
	private MyNumberField mtfTempoMonitorar = new MyNumberField(LARGURA, "TEMPO MONITORAMENTO (SEGUNDOS)");
	private MyNumberField mtfTamanhoLinha = new MyNumberField(LARGURA, "TAMANHO DA LINHA - ARQUIVO IMPORTACAO");
	private MyTextField mtfUrlPost = new MyTextField(LARGURA, "URL POST");
	private MyFolderField mffDiretorioImportacao = new MyFolderField(LARGURA, "DIRETORIO IMPORTACAO");
	private MyFolderField mffDiretorioImportados = new MyFolderField(LARGURA, "DIRETORIO DESTINO");

	private JButton bntSalvar = new JButton("Salvar");

	/**
	 * Construtor para a tela de configuraçoes
	 */
	private TelaConfiguracoes() {
		logger.info("Iniciando tela de configuracoes");

		config = Configuracoes.getInstancia();

		setSize(LARGURA, ALTURA);
		setLocation((dimensaoTela.width - this.getSize().width) / 2, (dimensaoTela.height - this.getSize().height) / 2);

		bntSalvar.addActionListener(actionSalvar());

		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		this.setSize(LARGURA, ALTURA);
		this.setResizable(false);

		jpTelaConfiguracoes.setSize(LARGURA, ALTURA);
		jpTelaConfiguracoes.setLayout(new GridLayout(0, 1));

		this.setLayout(new GridLayout(0, 1));
		this.carregarParametros();

		adicionarComponentes();
	}

	/**
	 * Adiciona os componentes em tela
	 */
	public void adicionarComponentes() {
		logger.info("Adicionando componentes TelaConfiguracoes");
		jpTelaConfiguracoes.add(mtfFiltroPrefixo);
		jpTelaConfiguracoes.add(mtfFiltroExtensao);
		jpTelaConfiguracoes.add(mtfTempoMonitorar);
		jpTelaConfiguracoes.add(mtfTamanhoLinha);
		jpTelaConfiguracoes.add(mtfUrlPost);
		jpTelaConfiguracoes.add(mffDiretorioImportacao);
		jpTelaConfiguracoes.add(mffDiretorioImportados);
		jpTelaConfiguracoes.add(bntSalvar);

		this.getContentPane().add(jpTelaConfiguracoes, BorderLayout.CENTER);
	}

	/**
	 * Carrega os parametros do arquivo para a tela
	 */
	public void carregarParametros() {
		logger.info("Carregando Parametros TelaConfiguracoes");

		mffDiretorioImportados.getTextField().setText(config.getParam(Configuracoes.PARAM_DIRETORIO_IMPORTADOS));
		mffDiretorioImportacao.getTextField().setText(config.getParam(Configuracoes.PARAM_DIRETORIO_IMPORTACAO));
		mtfTempoMonitorar.getNumberField().setText(config.getParam(Configuracoes.PARAM_TEMPO_MONITORAR));
		mtfTamanhoLinha.getNumberField().setText(config.getParam(Configuracoes.PARAM_TAMANHO_LINHA));
		mtfUrlPost.getTextField().setText(config.getParam(Configuracoes.PARAM_URL_POST));
		mtfFiltroExtensao.getTextField().setText(config.getParam(Configuracoes.PARAM_FILTRO_EXTENSAO));
		mtfFiltroPrefixo.getTextField().setText(config.getParam(Configuracoes.PARAM_FILTRO_PREFIXO));

	}

	/**
	 * Salva os parametros da aplicacao
	 * 
	 * @return True se os parametros forem salvos corretamente, false se houver
	 *         algum erro.
	 */
	public void salvar() {

		if (!validarParametros()) {
			logger.info("Parametros incorretos.");
			return;
		}

		config.setParam(Configuracoes.PARAM_DIRETORIO_IMPORTADOS, mffDiretorioImportados.getTextField().getText());
		config.setParam(Configuracoes.PARAM_DIRETORIO_IMPORTACAO, mffDiretorioImportacao.getTextField().getText());
		config.setParam(Configuracoes.PARAM_URL_POST, mtfUrlPost.getTextField().getText());
		config.setParam(Configuracoes.PARAM_TEMPO_MONITORAR, mtfTempoMonitorar.getNumberField().getText());
		config.setParam(Configuracoes.PARAM_TAMANHO_LINHA, mtfTamanhoLinha.getNumberField().getText());
		config.setParam(Configuracoes.PARAM_FILTRO_EXTENSAO, mtfFiltroExtensao.getTextField().getText());
		config.setParam(Configuracoes.PARAM_FILTRO_PREFIXO, mtfFiltroPrefixo.getTextField().getText());

		logger.info("Salvando parametros");
		config.gravarParametros();

		JOptionPane.showMessageDialog(null, "SALVO COM SUCESSO!");
		Configuracoes.getInstancia().carregarParametros();
		this.dispose();
	}

	/**
	 * Validar se os parametros estao corretos e joga uma mensagem na tela
	 * 
	 * @return True para parametros OK, false para algum erro
	 */
	private boolean validarParametros() {
		List<String> retorno = new ArrayList<>();

		String filtroPrefixo = mtfFiltroPrefixo.getTextField().getText();
		String filtroExtensao = mtfFiltroExtensao.getTextField().getText();
		String tempoMonitorar = mtfTempoMonitorar.getNumberField().getText();
		String urlPost = mtfUrlPost.getTextField().getText();
		String diretorioImportacao = mffDiretorioImportacao.getTextField().getText();
		String diretorioImportados = mffDiretorioImportados.getTextField().getText();

		if (filtroPrefixo.equals("")) {
			retorno.add("Prefixo para varredura de arquivos nao informado!");
		}

		if (filtroExtensao.equals("")) {
			retorno.add("Extensao para varredura de arquivos nao informada!");
		}

		if (urlPost.equals("")) {
			retorno.add("Url para envio de abastecidas nao informada!");
		}

		if (tempoMonitorar.equals("")) {
			retorno.add("Tempo para monitoramento do diretorio nao informado!");
		} else {
			try {
				Integer.parseInt(tempoMonitorar);
			} catch (Exception e) {
				retorno.add("Tempo para monitoramento do diretorio invalido!");
			}
		}

		if (diretorioImportacao.equals("")) {
			retorno.add("Diretorio para importacao nao informado!");
		} else {
			if (!diretorioImportacao.substring(diretorioImportacao.length() - 1).equals(Configuracoes.SEPARADOR)) {
				diretorioImportacao += Configuracoes.SEPARADOR;
				mffDiretorioImportacao.getTextField().setText(diretorioImportacao);
			}

			try {
				if (!new File(diretorioImportacao).exists()) {
					retorno.add("Diretorio para importacao invalido ou nao encontrado!");
				}
			} catch (Exception e) {
				retorno.add("Diretorio para importacao invalido ou nao encontrado!");
			}
		}

		if (diretorioImportados.equals("")) {
			retorno.add("Diretorio de destino para arquivos importados nao informado!");
		} else {
			if (!diretorioImportados.substring(diretorioImportados.length() - 1).equals(Configuracoes.SEPARADOR)) {
				diretorioImportados += Configuracoes.SEPARADOR;
				mffDiretorioImportados.getTextField().setText(diretorioImportados);
			}

			try {
				if (!new File(diretorioImportados).exists()) {
					retorno.add("Diretorio para arquivos importados invalido ou nao encontrado!");
				}
			} catch (Exception e) {
				retorno.add("Diretorio para arquivos importados invalido ou nao encontrado!");
			}
		}

		String mensagemErro = "";
		if (!retorno.isEmpty()) {
			for (String r : retorno) {
				mensagemErro += r + "\n";
			}

			JOptionPane.showMessageDialog(null, mensagemErro, "Erros na validacao de parametros", 1);
			logger.error(String.format("Erros na validacao dos parametros: %s", mensagemErro));

			return false;
		}
		return true;
	}

	public static TelaConfiguracoes getTela(){
		if (telaConfiguracoes == null){
			telaConfiguracoes = new TelaConfiguracoes();
		}
		return telaConfiguracoes;
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
