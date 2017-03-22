package br.datacoper.control;

import java.math.BigDecimal;

import javax.swing.JOptionPane;

import org.apache.log4j.Logger;

import com.db4o.Db4o;
import com.db4o.ObjectContainer;

/**
 * @author dread
 *
 *         Singleton de conexao com o banco de Dados db4o
 * 
 */
public class ConexaoBanco {
	private static ConexaoBanco conexaoBanco;
	private ObjectContainer database;

	public ConexaoBanco() {
		try {
			Logger logger = Logger.getLogger("br.datacoper.control.ConexaoBanco");
			logger.info("Iniciando conexao com o banco");

			this.database = Db4o.openFile("./database/abastecimentoDB.yap");
		    Db4o.configure().objectClass(BigDecimal.class);

		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "ERRO NA CONEXAO COM O BANCO DE DADOS! \nA APLICACAO SERA FECHADA.");
			Logger logger = Logger.getLogger("br.datacoper.control.ConexaoBanco");
			logger.error("Erro na conexao com o banco " + e.getMessage());
			System.exit(0);
		}
	}

	/**
	 * @return Conexao com o banco
	 */
	public static ConexaoBanco getConexaoBanco() {
		if (conexaoBanco == null) {
			conexaoBanco = new ConexaoBanco();
		}
		return conexaoBanco;
	}

	/**
	 * @return database
	 */
	public ObjectContainer getDatabase() {
		return database;
	}

	/**
	 * @param database
	 *            the database to set
	 */
	public void setDatabase(ObjectContainer database) {
		this.database = database;
	}

}
