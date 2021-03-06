package br.datacoper.model;

import java.io.BufferedReader;
/**
 *
 * @author dread
 */
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.swing.JOptionPane;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

public class Configuracoes {

	private static Configuracoes config;
	private static Boolean valido = false;

	public static final String SEPARADOR = System.getProperty("file.separator");

	public static final String PARAM_TEMPO_MONITORAR = "TEMPO_MONITORAR";
	public static final String PARAM_TAMANHO_LINHA = "TAMANHO_LINHA";
	public static final String PARAM_URL_POST = "URL_POST";
	public static final String PARAM_FILTRO_PREFIXO = "FILTRO_PREFIXO";
	public static final String PARAM_FILTRO_EXTENSAO = "FILTRO_EXTENSAO";
	public static final String PARAM_DIRETORIO_IMPORTACAO = "DIRETORIO_IMPORTACAO";
	public static final String PARAM_DIRETORIO_IMPORTADOS = "DIRETORIO_IMPORTADOS";

	public static final String DIRETORIO_RAIZ = System.getProperty("user.dir");
	public static final String DIRETORIO_CONFIGURACOES = DIRETORIO_RAIZ.concat(SEPARADOR).concat("config").concat(SEPARADOR);
	public static final String DIRETORIO_DATABASE = DIRETORIO_RAIZ.concat(SEPARADOR).concat("database").concat(SEPARADOR);
	public static final String DIRETORIO_IMAGENS = DIRETORIO_RAIZ.concat("/src/main/resources/img/");
	public static final String ARQUIVO_DE_CONFIGURACOES = "config.properties";
	public static final String NOME_ABSOLUTO_ARQUIVO_CONFIGURACOES = DIRETORIO_CONFIGURACOES + ARQUIVO_DE_CONFIGURACOES;

	private Map<String, Object> mapaParametros;
	private List<String> listaParametros;

	Logger logger = Logger.getLogger("br.datacoper.model.Configuracoes");

	public Configuracoes() {
		listaParametros = new ArrayList<String>();
		mapaParametros = new HashMap<>();
		listaParametros.add(PARAM_TEMPO_MONITORAR);
		listaParametros.add(PARAM_TAMANHO_LINHA);
		listaParametros.add(PARAM_URL_POST);
		listaParametros.add(PARAM_FILTRO_PREFIXO);
		listaParametros.add(PARAM_FILTRO_EXTENSAO);
		listaParametros.add(PARAM_DIRETORIO_IMPORTACAO);
		listaParametros.add(PARAM_DIRETORIO_IMPORTADOS);

		File diretorioConfig = new File(DIRETORIO_CONFIGURACOES);
		if (!diretorioConfig.exists()){
			diretorioConfig.mkdir();
		}

		File diretorioDatabase = new File(DIRETORIO_DATABASE);
		if (!diretorioDatabase.exists()){
			diretorioDatabase.mkdir();
		}

		File diretorioLog = new File(DIRETORIO_RAIZ.concat(SEPARADOR).concat("log"));
		if (!diretorioLog.exists()){
			diretorioLog.mkdir();
		}
		
		carregarParametros();
		carregarArquivoLog4j();

		logger.info("Iniciando leitura do arquivo de configuracoes");
	}

	private void carregarArquivoLog4j() {
		File arquivoConfiguracoesApp = new File(DIRETORIO_RAIZ.concat("/src/main/resources/log4j.properties"));
		File arquivoConfiguracoesLocal = new File(DIRETORIO_RAIZ.concat(SEPARADOR).concat("log4j.properties"));

		try {
			FileReader fis = new FileReader(arquivoConfiguracoesApp);
			BufferedReader bufferedReader = new BufferedReader(fis);
			StringBuilder buffer = new StringBuilder();
			String line = "";

			while ((line = bufferedReader.readLine()) != null) {
				buffer.append(line).append("\n");
			}

			fis.close();
			bufferedReader.close();
			FileWriter writer = new FileWriter(arquivoConfiguracoesLocal);

			writer.write(buffer.toString());
			writer.flush();
			writer.close();
			
			PropertyConfigurator.configure(arquivoConfiguracoesLocal.getAbsolutePath());

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public synchronized static Configuracoes getInstancia() {
		if (config == null) {
			config = new Configuracoes();
		}
		return config;
	}

	public void carregarParametros() {
		Properties propriedades = new Properties();
		InputStream input;
		try {
			input = new FileInputStream(NOME_ABSOLUTO_ARQUIVO_CONFIGURACOES);
			propriedades.load(input);
			this.mapaParametros.clear();
			for (String param : listaParametros) {
				if (propriedades.getProperty(param) != null)
					mapaParametros.put(param, propriedades.getProperty(param));
				else
					mapaParametros.put(param, "");
			}
		} catch (IOException ex) {
			logger.error("Erro ao realizar a leitura do arquivo de configuracoes");
		}

		valido = validarParametros();
	}

	private boolean validarParametros() {
		List<String> retorno = new ArrayList<>();

		String filtroPrefixo = getParam(PARAM_FILTRO_PREFIXO);
		String filtroExtensao = getParam(PARAM_FILTRO_EXTENSAO);
		String tempoMonitorar = getParam(PARAM_TEMPO_MONITORAR);
		String tamanhoLinha = getParam(PARAM_TAMANHO_LINHA);
		String urlPost = getParam(PARAM_URL_POST);
		String diretorioImportacao = getParam(PARAM_DIRETORIO_IMPORTACAO);
		String diretorioImportados = getParam(PARAM_DIRETORIO_IMPORTADOS);

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
				Integer tempo = Integer.parseInt(tempoMonitorar);
				if (tempo <= 0){
					retorno.add("Tempo para monitoramento do diretorio invalido!");
				}
			} catch (Exception e) {
				retorno.add("Tempo para monitoramento do diretorio invalido!");
			}
		}

		if (tamanhoLinha.equals("")) {
			retorno.add("Tamanho da linha do arquivo para importação nao informado!");
		} else {
			try {
				Integer.parseInt(tamanhoLinha);
			} catch (Exception e) {
				retorno.add("Tamanho da linha do arquivo para importação invalido!");
			}
		}

		if (diretorioImportacao.equals("")) {
			retorno.add("Diretorio para importacao nao informado!");
		} else {
			if (!getParam(PARAM_DIRETORIO_IMPORTACAO).substring(getParam(PARAM_DIRETORIO_IMPORTACAO).length() - 1)
					.equals(SEPARADOR)) {
				setParam(PARAM_DIRETORIO_IMPORTACAO, getParam(PARAM_DIRETORIO_IMPORTACAO) + SEPARADOR);
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
			if (!getParam(PARAM_DIRETORIO_IMPORTADOS).substring(getParam(PARAM_DIRETORIO_IMPORTADOS).length() - 1)
					.equals(SEPARADOR)) {
				setParam(PARAM_DIRETORIO_IMPORTADOS, getParam(PARAM_DIRETORIO_IMPORTADOS) + SEPARADOR);
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

			logger.error(String.format("Erros na validacao de parametros: %s", mensagemErro));
			return false;
		}

		return true;
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

	public void gravarParametros() {
		Properties prop = new Properties();
		OutputStream output;
		try {
			File arquivoPropriedades = new File(NOME_ABSOLUTO_ARQUIVO_CONFIGURACOES);
			if (!arquivoPropriedades.exists())
				arquivoPropriedades.createNewFile();
			output = new FileOutputStream(NOME_ABSOLUTO_ARQUIVO_CONFIGURACOES);
			prop.putAll(this.mapaParametros);
			prop.store(output, "#propriedades");
			output.flush();
			output.close();
		} catch (IOException ex) {
			ex.printStackTrace();
		}

	}

	public boolean getValido() {
		return valido;
	}
}
