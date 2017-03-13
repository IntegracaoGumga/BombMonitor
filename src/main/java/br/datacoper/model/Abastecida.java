/**
 * 
 */
package br.datacoper.model;

import com.db4o.config.annotations.GeneratedUUIDs;
import com.db4o.config.annotations.GeneratedVersionNumbers;
import com.fasterxml.jackson.annotation.JsonAutoDetect;

/**
 * @author dread
 * 
 *         Classe persistente para o abastecimento
 * 
 */
public class Abastecida {

	private String horaAbastecimento;
	private String dataAbastecimento;
	private Float quantidade;
	private Float encerrante;
	private Integer numeroBico;
	private Integer frentista;
	private String arquivo;

	/**
	 * @return the horaAbastecimento
	 */
	public String getHoraAbastecimento() {
		return horaAbastecimento;
	}

	/**
	 * @param horaAbastecimento
	 *            the horaAbastecimento to set
	 */
	public Abastecida setHoraAbastecimento(final String horaAbastecimento) {
		this.horaAbastecimento = horaAbastecimento;
		return this;
	}

	/**
	 * @return the dataAbastecimento
	 */
	public String getDataAbastecimento() {
		return dataAbastecimento;
	}

	/**
	 * @param dataAbastecimento
	 *            the dataAbastecimento to set
	 */
	public Abastecida setDataAbastecimento(final String dataAbastecimento) {
		this.dataAbastecimento = dataAbastecimento;
		return this;
	}

	/**
	 * @return the quantidade
	 */
	public Float getQuantidade() {
		return quantidade;
	}

	/**
	 * @param quantidade
	 *            the quantidade to set
	 */
	public Abastecida setQuantidade(final Float quantidade) {
		this.quantidade = quantidade;
		return this;
	}

	/**
	 * @return the encerrante
	 */
	public Float getEncerrante() {
		return encerrante;
	}

	/**
	 * @param encerrante
	 *            the encerrante to set
	 */
	public Abastecida setEncerrante(final Float encerrante) {
		this.encerrante = encerrante;
		return this;
	}

	/**
	 * @return the numeroBico
	 */
	public Integer getNumeroBico() {
		return numeroBico;
	}

	/**
	 * @param numeroBico
	 *            the numeroBico to set
	 */
	public Abastecida setNumeroBico(final Integer numeroBico) {
		this.numeroBico = numeroBico;
		return this;
	}

	/**
	 * @return the frentista
	 */
	public Integer getFrentista() {
		return frentista;
	}

	/**
	 * @param frentista
	 *            the frentista to set
	 */
	public Abastecida setFrentista(final Integer frentista) {
		this.frentista = frentista;
		return this;
	}

	/**
	 * @return the arquivo
	 */
	public String getArquivo() {
		return arquivo;
	}

	/**
	 * @param arquivo
	 *            the arquivo to set
	 */
	public void setArquivo(String arquivo) {
		this.arquivo = arquivo;
	}
}
