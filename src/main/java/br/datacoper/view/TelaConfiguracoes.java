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
 *
 * @author Dread
 */
public class TelaConfiguracoes extends javax.swing.JFrame {

	private static final long serialVersionUID = 1L;

	public static final int LARGURA = 600;
	public static final int ALTURA = 450;

	private static Configuracoes config = Configuracoes.getInstancia();

	Logger logger = Logger.getLogger("br.datacoper.view.TelaConfiguracoes");

	JPanel jpTelaConfiguracoes = new JPanel();

	Dimension dimensaoTela = Toolkit.getDefaultToolkit().getScreenSize();

	private MyTextField mtfFiltroPrefixo = new MyTextField(LARGURA, "FILTRAR PREFIXO");
	private MyTextField mtfFiltroExtensao = new MyTextField(LARGURA, "FILTRAR EXTENSAO");
	private MyTextField mtfIcone = new MyTextField(LARGURA, "IMAGEM");
	private MyTextField mtfTempoMonitorar = new MyTextField(LARGURA, "TEMPO MONITORAMENTO (SEGUNDOS)");
	private MyTextField mtfUrlPost = new MyTextField(LARGURA, "URL POST");
	private MyTextField mtfDiretorioImportacao = new MyTextField(LARGURA, "DIRETORIO IMPORTACAO");
	private MyTextField mtfDiretorioImportados = new MyTextField(LARGURA, "DIRETORIO DESTINO");
	private MyFolderField mtfDiretorioLog = new MyFolderField(LARGURA, "DIRETORIO LOG");

	private JButton bntSalvar = new JButton("Salvar");

	/**
	 * Construtor para a tela de configuraçoes
	 */
	public TelaConfiguracoes() {
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
		jpTelaConfiguracoes.add(mtfUrlPost);
		jpTelaConfiguracoes.add(mtfIcone);
		jpTelaConfiguracoes.add(mtfDiretorioImportacao);
		jpTelaConfiguracoes.add(mtfDiretorioImportados);
		jpTelaConfiguracoes.add(mtfDiretorioLog);
		jpTelaConfiguracoes.add(bntSalvar);

		this.getContentPane().add(jpTelaConfiguracoes, BorderLayout.CENTER);
	}

	/**
	 * Carrega os parametros do arquivo para a tela
	 */
	public void carregarParametros() {
		logger.info("Carregando Parametros TelaConfiguracoes");

		mtfDiretorioLog.getTextField().setText(config.getParam(Configuracoes.PARAM_DIRETORIO_LOG));
		mtfDiretorioImportados.getTextField().setText(config.getParam(Configuracoes.PARAM_DIRETORIO_IMPORTADOS));
		mtfDiretorioImportacao.getTextField().setText(config.getParam(Configuracoes.PARAM_DIRETORIO_IMPORTACAO));
		mtfTempoMonitorar.getTextField().setText(config.getParam(Configuracoes.PARAM_TEMPO_MONITORAR));
		mtfUrlPost.getTextField().setText(config.getParam(Configuracoes.PARAM_URL_POST));
		mtfIcone.getTextField().setText(config.getParam(Configuracoes.PARAM_ICONE));
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

		config.setParam(Configuracoes.PARAM_DIRETORIO_LOG, mtfDiretorioLog.getTextField().getText());
		config.setParam(Configuracoes.PARAM_DIRETORIO_IMPORTADOS, mtfDiretorioImportados.getTextField().getText());
		config.setParam(Configuracoes.PARAM_DIRETORIO_IMPORTACAO, mtfDiretorioImportacao.getTextField().getText());
		config.setParam(Configuracoes.PARAM_ICONE, mtfIcone.getTextField().getText());
		config.setParam(Configuracoes.PARAM_URL_POST, mtfUrlPost.getTextField().getText());
		config.setParam(Configuracoes.PARAM_TEMPO_MONITORAR, mtfTempoMonitorar.getTextField().getText());
		config.setParam(Configuracoes.PARAM_FILTRO_EXTENSAO, mtfFiltroExtensao.getTextField().getText());
		config.setParam(Configuracoes.PARAM_FILTRO_PREFIXO, mtfFiltroPrefixo.getTextField().getText());

		logger.info("Salvando parametros");
		config.gravarParametros();

		JOptionPane.showMessageDialog(null, "SALVO COM SECESSO!" + "\n"
				+ "A APLICACAO DEVE SER REINICIADA PARA QUE OS PARAMETROS SEJAM CARREGADOS NOVAMENTE!");
		System.exit(0);
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
		String tempoMonitorar = mtfTempoMonitorar.getTextField().getText();
		String urlPost = mtfUrlPost.getTextField().getText();
		String icone = mtfIcone.getTextField().getText();
		String diretorioImportacao = mtfDiretorioImportacao.getTextField().getText();
		String diretorioImportados = mtfDiretorioImportados.getTextField().getText();
		String diretorioLog = mtfDiretorioLog.getTextField().getText();

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

		if (icone.equals("")) {
			retorno.add("Icone da aplicacao nao informado!");
		} else {
			if (!icone.endsWith(".png")) {
				retorno.add("Extensao invalida para o Icone (PNG)!");
			} else {
				try {
					File iconeApp = new File(icone);
					if (!iconeApp.exists()) {
						retorno.add("Icone invalido ou nao encontrado!");
					}
				} catch (Exception e) {
					retorno.add("Icone invalido ou nao encontrado!");
				}
			}
		}

		if (diretorioImportacao.equals("")) {
			retorno.add("Diretorio para importacao nao informado!");
		} else {
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
			try {
				if (!new File(diretorioImportados).exists()) {
					retorno.add("Diretorio para arquivos importados invalido ou nao encontrado!");
				}
			} catch (Exception e) {
				retorno.add("Diretorio para arquivos importados invalido ou nao encontrado!");
			}
		}

		if (diretorioLog.equals("")) {
			retorno.add("Diretorio para importacao nao informado!");
		} else {
			try {
				if (!new File(diretorioLog).exists()) {
					retorno.add("Diretorio para arquivo de logs invalido ou nao encontrado!");
				}
			} catch (Exception e) {
				retorno.add("Diretorio para arquivo de logs invalido ou nao encontrado!");
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
