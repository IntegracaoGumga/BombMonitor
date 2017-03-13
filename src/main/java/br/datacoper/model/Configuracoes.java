/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.datacoper.model;

/**
 *
 * @author dread
 */
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.swing.JOptionPane;

import org.apache.log4j.PropertyConfigurator;

public class Configuracoes {

	private static Configuracoes config;
	public static final String DIRETORIORAIZ = System.getProperty("user.dir");
	public static final String DIRETORIOCONFIGURACOES = DIRETORIORAIZ + "/config/";
	public static final String DIRETORIOIMAGENS = DIRETORIORAIZ + "/img/";
	public static final String ARQUIVODECONFIGURACOES = "config.properties";
	public static final String NOMEABSOLUTOARQUIVOCONFIGURACOES = DIRETORIOCONFIGURACOES + ARQUIVODECONFIGURACOES;
	
	public static final String TRUE = "SIM";
	public static final String FALSE = "NAO";

	public static final String PARAM_GERA_LOG = "GERA_LOG";
	public static final String PARAM_DEBUG = "DEBUG";
	public static final String PARAM_MOVER_ARQUIVO_IMPORTADO = "MOVER_ARQUIVO_TRANSFERIDO";
	public static final String PARAM_FILTRO_PREFIXO = "FILTRO_PREFIXO";
	public static final String PARAM_FILTRO_EXTENSAO = "FILTRO_EXTENSAO";
	public static final String PARAM_DIRETORIO_IMAGEM = "DIRETORIO_IMAGEM";
	public static final String PARAM_DIRETORIO_IMPORTACAO = "DIRETORIO_IMPORTACAO";
	public static final String PARAM_DIRETORIO_IMPORTADOS = "DIRETORIO_IMPORTADOS";
	public static final String PARAM_DIRETORIO_LOG = "DIRETORIO_LOG";

	private Map<String, Object> mapaParametros;
	private List<String> listaParametros;

	public Configuracoes() {
		listaParametros = new ArrayList<String>();
		mapaParametros = new HashMap<>();
		listaParametros.add(PARAM_GERA_LOG);
		listaParametros.add(PARAM_DEBUG);
		listaParametros.add(PARAM_MOVER_ARQUIVO_IMPORTADO);
		listaParametros.add(PARAM_FILTRO_PREFIXO);
		listaParametros.add(PARAM_FILTRO_EXTENSAO);
		listaParametros.add(PARAM_DIRETORIO_IMAGEM);
		listaParametros.add(PARAM_DIRETORIO_IMPORTACAO);
		listaParametros.add(PARAM_DIRETORIO_IMPORTADOS);
		listaParametros.add(PARAM_DIRETORIO_LOG);

		carregaParametros();
		String log4jConfPath = DIRETORIOCONFIGURACOES + "log4j.properties";
		PropertyConfigurator.configure(log4jConfPath);
	}

	public static Configuracoes getInstance() {
		if (config == null) {
			config = new Configuracoes();
		}
		return config;
	}

	public void carregaParametros() {
		Properties prop = new Properties();
		InputStream input;
		try {
			input = new FileInputStream(NOMEABSOLUTOARQUIVOCONFIGURACOES);
			prop.load(input);
			this.mapaParametros.clear();
			for (String param : listaParametros) {
				if (prop.getProperty(param) != null)
					mapaParametros.put(param, prop.getProperty(param).toUpperCase());
				else
					mapaParametros.put(param, "");
			}
		} catch (IOException ex) {
			JOptionPane.showMessageDialog(null, "Erro ao realizar a leitura de parametros!");
		}
	}

	public String getParam(String constante) {
		String valor = "";
		if (this.mapaParametros.get(constante) != null)
			valor = this.mapaParametros.get(constante).toString();
		return valor;
	}

	public void setParam(String constante, String newValor) {
		this.mapaParametros.put(constante, newValor);
	}

	public void gravaParametros() {
		Properties prop = new Properties();
		OutputStream output;
		try {
			File arquivoPropriedades = new File(NOMEABSOLUTOARQUIVOCONFIGURACOES);
			if (!arquivoPropriedades.exists())
				arquivoPropriedades.createNewFile();
			output = new FileOutputStream(NOMEABSOLUTOARQUIVOCONFIGURACOES);
			prop.putAll(this.mapaParametros);
			prop.store(output, "#propriedades");
			output.flush();
			output.close();
		} catch (IOException ex) {
			ex.printStackTrace();
		}

	}
}
