/**
 * 
 */
package br.datacoper.control;

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
		this.database = Db4o.openFile("./src/main/resources/database/abastecimentoDB.yap");
	}

	/**
	 * @return the conexaoBanco
	 */
	public static ConexaoBanco getConexaoBanco() {
		if (conexaoBanco == null) {
			conexaoBanco = new ConexaoBanco();
		}
		return conexaoBanco;
	}

	/**
	 * @return the database
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
