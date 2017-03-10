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
import org.apache.log4j.PropertyConfigurator;

public class Configuracoes {

	private static Configuracoes conf;
	public static final String TRUE = "SIM";
	public static final String FALSE = "SIM";
	public static final String DIRETORIORAIZ = System.getProperty("user.dir");
	public static final String DIRETORIOCONFIGURACOES = DIRETORIORAIZ + "/config/";
	public static final String DIRETORIOIMAGENS = DIRETORIORAIZ + "/imagens/";
	public static final String ARQUIVODECONFIGURACOES = "config.properties";
	public static final String NOMEABSOLUTOARQUIVOCONFIGURACOES = DIRETORIOCONFIGURACOES + ARQUIVODECONFIGURACOES;

	public static final String PARAM_PORTASERVIDOR = "PORTA_SERVIDOR";
	public static final String PARAM_DIRETORIOARQUIVOS = "DIR_ARQUIVOS";
	public static final String PARAM_MOVERARQUIVOTRANSFERIDO = "MOVER_ARQUIVO_TRANSFERIDO";
	public static final String PARAM_DIRETORIODESTINOARQUIVO = "DIRETORIO_DESTINO_ARQUIVO";
	public static final String PARAM_NOME_ARQUIVO = "NOME_ARQUIVO";
	public static final String PARAM_FILTROEXTENSAO = "FILTRO_EXTENSAO";
	public static final String PARAM_FILTROPREFIXO = "FILTRO_PREFIXO";
	public static final String PARAM_DEBUG = "DEBUG";
	public static final String PARAM_GERALOG = "GERA_LOG";
	public static final String PARAM_PORTA_SERIAL = "PORTA_SERIAL";
	public static final String PARAM_BAUND_RATE = "BAUND_RATE";
	public static final String PARAM_UNIDADE = "UNIDADE";

	private Map<String, Object> mapaParametros;
	private List<String> listaParametros;

	public Configuracoes() {
		listaParametros = new ArrayList<String>();
		mapaParametros = new HashMap<>();
		listaParametros.add(PARAM_PORTASERVIDOR);
		listaParametros.add(PARAM_DIRETORIOARQUIVOS);
		listaParametros.add(PARAM_MOVERARQUIVOTRANSFERIDO);
		listaParametros.add(PARAM_DIRETORIODESTINOARQUIVO);
		listaParametros.add(PARAM_DEBUG);
		listaParametros.add(PARAM_GERALOG);
		listaParametros.add(PARAM_FILTROEXTENSAO);
		listaParametros.add(PARAM_FILTROPREFIXO);
		listaParametros.add(PARAM_PORTA_SERIAL);
		listaParametros.add(PARAM_BAUND_RATE);
		listaParametros.add(PARAM_NOME_ARQUIVO);
		listaParametros.add(PARAM_UNIDADE);

		carregaParametros();
		String log4jConfPath = DIRETORIOCONFIGURACOES + "log4j.properties";
		PropertyConfigurator.configure(log4jConfPath);
	}

	public static Configuracoes getInstance() {
		if (conf == null) {
			conf = new Configuracoes();
		}
		return conf;
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
			ex.printStackTrace();
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
