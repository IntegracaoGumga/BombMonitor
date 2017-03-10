package br.datacoper.model;

import java.awt.PageAttributes.MediaType;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileFilter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.*;

import br.datacoper.control.ConexaoBanco;
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
	private String directory;

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

		/* Parametro */
		setDirectory("/home/dread/workspace/BombMonitor/src/main/resources/monitorar/");

		this.threadMonitor = new Thread(this);
		this.threadMonitor.start();
	}

	/**
	 * Lê o diretorio em busca de novos arquivos
	 * 
	 * @param dir
	 * 
	 */
	private void verifyDictory(final String dir) {
		File diretorio = new File(dir);
		File arquivos[] = diretorio.listFiles(new FileFilter() {
			public boolean accept(File pathname) {
				return (pathname.getName().toLowerCase().startsWith("ab_")
						&& pathname.getName().toLowerCase().endsWith(".txt"));
			}
		});

		for (int i = 0; i < arquivos.length; i++) {
			File file = arquivos[i];
			parseFileToSupply(file.getName());
		}

	}

	/**
	 * Converte o arquivo para objetos do tipo Supply
	 * 
	 * @param file
	 */
	private void parseFileToSupply(final String file) {
		ConexaoBanco conexao = ConexaoBanco.getConexaoBanco();

		List<String> listFile = readFile(file);
		listFile.forEach(line -> {
			Supply supplyAux = new Supply();
			supplyAux.setNumeroBico(Integer.parseInt(line.substring(0, 2))).setHoraAbastecimento(line.substring(20, 26))
					.setDataAbastecimento(line.substring(12, 20))
					.setQuantidade(Float.parseFloat(line.substring(32, 40)) / 1000)
					.setEncerrante(Float.parseFloat(line.substring(49, 61)) / 1000)
					.setFrentista(Integer.parseInt(line.substring(61, 65)))
					.setArquivo(file);

			conexao.getDatabase().set(supplyAux);

			Rest.postSupply(supplyAux);
		});
	}

	@Override
	public void run() {
		Thread currentThread = Thread.currentThread();

		// thread que fica verificando a pasta
		while (this.threadMonitor == currentThread) {

			verifyDictory(getDirectory());

			try {
				/* Parametro */
				Thread.sleep(1000);
			} catch (InterruptedException e) {

			}
		}
	}

	public List<String> readFile(final String file) {
		ArrayList<String> list = new ArrayList<>();

		try (FileReader fileReader = new FileReader(new File(getDirectory() + file));
				BufferedReader br = new BufferedReader(fileReader)) {

			String line = null;
			while ((line = br.readLine()) != null) {
				list.add(line);
			}
		} catch (FileNotFoundException e) {
			System.err.println(String.format("Arquivo %s nao foi encontrado.", file));
		} catch (IOException e) {
			System.err.println(String.format("Erro na leitura do arquivo %s.", file));
		}
		return list;
	}

	public String getDirectory() {
		return directory;
	}

	public void setDirectory(final String diretorio) {
		this.directory = diretorio;
	}
}