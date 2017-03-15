package br.datacoper.model;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileFilter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;

import org.apache.log4j.Logger;

import br.datacoper.control.Rest;

/**
 * 
 * @author dread
 *
 *         Monitora o diretorio no aguardo de arquivos de abastecimento
 * 
 *         Para cada arquivo encontrado, um objeto é criado e suas informações
 *         são salvas no banco e enviadas para uma aplicação rest
 */
public class Monitor implements Runnable {

	private Thread threadMonitor;
	private String diretorio;

	private static final BigDecimal MIL = new BigDecimal(1000);
	private static final Integer TAMANHO = Integer
			.parseInt(Configuracoes.getInstancia().getParam(Configuracoes.PARAM_TAMANHO_LINHA));

	Logger logger = Logger.getLogger("br.datacoper.model.Monitor");

	/**
	 * Construtor
	 */
	public Monitor() {
		this.init();
	}

	/**
	 * Inicia a execução da thread
	 */
	private void init() {
		setDiretorio(Configuracoes.getInstancia().getParam(Configuracoes.PARAM_DIRETORIO_IMPORTACAO));

		this.threadMonitor = new Thread(this);
		this.threadMonitor.start();
	}

	/**
	 * Lê o diretorio em busca de novos arquivos
	 * 
	 * @param dir
	 * 
	 */
	private void verificarDiretorio(final String dir) {
		File diretorio = new File(dir);
		File arquivos[] = diretorio.listFiles(new FileFilter() {
			public boolean accept(File pathname) {
				return (pathname.getName().toLowerCase().startsWith(
						Configuracoes.getInstancia().getParam(Configuracoes.PARAM_FILTRO_PREFIXO).toLowerCase())
						&& pathname.getName().toLowerCase().endsWith(Configuracoes.getInstancia()
								.getParam(Configuracoes.PARAM_FILTRO_EXTENSAO).toLowerCase()));
			}
		});

		try {
			for (int i = 0; i < arquivos.length; i++) {
				File file = arquivos[i];
				if (file.length() != TAMANHO) {
					logger.error(
							String.format("Tamanho %d invalido para o arquivo: %s", file.length(), file.getName()));
				} else {
					logger.info(String.format("Arquivo encontrado: %s", file.getName()));
					converterArquivoAbastecida(file.getName());
				}
			}
		} catch (NullPointerException e) {
			logger.error(String.format("Diretorio %s vazio!", dir));
		}
	}

	/**
	 * Converte o arquivo para objetos do tipo Supply
	 * 
	 * @param file
	 */
	private void converterArquivoAbastecida(final String file) {
		List<String> listFile = lerArquivo(file);
		listFile.forEach(line -> {
			Abastecida abastecida = new Abastecida();
			abastecida.setNumeroBico(Integer.parseInt(line.substring(0, 2)))
					.setHoraAbastecimento(line.substring(20, 26)).setDataAbastecimento(line.substring(12, 20))
					.setQuantidade(
							new BigDecimal(line.substring(32, 40)).divide(MIL).setScale(3, BigDecimal.ROUND_HALF_UP))
					.setEncerrante(
							new BigDecimal(line.substring(49, 61)).divide(MIL).setScale(3, BigDecimal.ROUND_HALF_UP))
					.setFrentista(Integer.parseInt(line.substring(61, 65))).setArquivo(file);

			logger.info(String.format("Salvando abastecida %s", abastecida.getArquivo()));

			Rest.enviarAbastecida(abastecida);
		});
	}

	public List<String> lerArquivo(final String file) {
		ArrayList<String> list = new ArrayList<>();

		try (FileReader fileReader = new FileReader(new File(getDiretorio() + file));
				BufferedReader br = new BufferedReader(fileReader)) {

			String line = null;
			while ((line = br.readLine()) != null) {
				list.add(line);
			}
		} catch (FileNotFoundException e) {
			logger.error(String.format("Arquivo %s nao foi encontrado.", file));
		} catch (IOException e) {
			logger.error(String.format("Erro na leitura do arquivo %s.", file));
		}
		return list;
	}

	@Override
	public void run() {
		Thread currentThread = Thread.currentThread();

		// thread que fica verificando a pasta
		while (this.threadMonitor == currentThread) {

			verificarDiretorio(getDiretorio());

			try {
				Thread.sleep(1000
						* Integer.parseInt(Configuracoes.getInstancia().getParam(Configuracoes.PARAM_TEMPO_MONITORAR)));
			} catch (InterruptedException e) {
				logger.error(String.format("Erro na execuçao da thread: %s", e.getMessage()));
			} catch (NumberFormatException ex) {
				logger.error(String.format("Tempo invalido para a execucao da thread"));
			}

		}
	}

	public String getDiretorio() {
		return diretorio;
	}

	public void setDiretorio(final String diretorio) {
		this.diretorio = diretorio;
	}
}