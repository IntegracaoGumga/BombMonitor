package br.datacoper.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * @author dread
 * 
 *         Classe persistente para o abastecimento
 * 
 */
public class Abastecida {

	private String horaAbastecimento;
	private String dataAbastecimento;
	private Double quantidade;
	private Double encerrante;
	private Integer numeroBico;
	private Integer frentista;
	private Long id;

	@JsonIgnore
	private String arquivo;
	@JsonIgnore
	private Integer statusHTTP;

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
		this.horaAbastecimento = horaAbastecimento.substring(0, 2).concat(":").concat(horaAbastecimento.substring(2, 4))
				.concat(":").concat(horaAbastecimento.substring(4));
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
		this.dataAbastecimento = dataAbastecimento.substring(0, 2).concat("/").concat(dataAbastecimento.substring(2, 4))
				.concat("/").concat(dataAbastecimento.substring(4));
		return this;
	}

	/**
	 * @return the quantidade
	 */
	public Double getQuantidade() {
		return quantidade;
	}

	/**
	 * @param quantidade
	 *            the quantidade to set
	 */
	public Abastecida setQuantidade(final Double quantidade) {
		this.quantidade = quantidade;
		return this;
	}

	/**
	 * @return the encerrante
	 */
	public Double getEncerrante() {
		return encerrante;
	}

	/**
	 * @param encerrante
	 *            the encerrante to set
	 */
	public Abastecida setEncerrante(final Double encerrante) {
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
	public Abastecida setArquivo(final String arquivo) {
		this.arquivo = arquivo;
		return this;
	}

	/**
	 * @return the statusHTTP
	 */
	public Integer getStatusHTTP() {
		return statusHTTP;
	}

	/**
	 * @param statusHTTP
	 *            the statusHTTP to set
	 */
	public Abastecida setStatusHTTP(final Integer statusHTTP) {
		this.statusHTTP = statusHTTP;
		return this;
	}

	/**
	 * @return the chaveAbastecimento
	 */
	public Long getId() {
		return id;
	}

	/**
	 * @param chaveAbastecimento
	 *            the chaveAbastecimento to set
	 */
	public Abastecida setId(final Long id) {
		this.id = id;
		return this;
	}
}
