package br.transversa.backend.model;

import java.io.Serializable;
import javax.persistence.*;
import java.math.BigDecimal;
import java.sql.Timestamp;


/**
 * The persistent class for the pesquisaprecos database table.
 * 
 */
@Entity
@Table(name="pesquisaprecos")
@NamedQuery(name="Pesquisapreco.findAll", query="SELECT p FROM Pesquisapreco p")
public class Pesquisapreco implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="id_pesquisaprecos", unique=true, nullable=false)
	private String id;

	@Lob
	@Column(nullable=false)
	private byte[] data;

	@Column(name="data_criado", nullable=false)
	private Timestamp dataCriado;

	@Column(name="file_type", nullable=false, length=15)
	private String fileType;

	@Column(nullable=false, length=45)
	private String marca;

	@Column(nullable=false, length=45)
	private String nome;

	@Column(nullable=false, precision=10)
	private BigDecimal preco;

	@Column(nullable=false, length=45)
	private String uuid;

	//bi-directional many-to-one association to User
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="users_id_user", nullable=false)
	private User user;

	public Pesquisapreco() {
	}

	public String getIdPesquisaprecos() {
		return this.id;
	}

	public void setIdPesquisaprecos(String idPesquisaprecos) {
		this.id = idPesquisaprecos;
	}

	public byte[] getData() {
		return this.data;
	}

	public void setData(byte[] data) {
		this.data = data;
	}

	public Timestamp getDataCriado() {
		return this.dataCriado;
	}

	public void setDataCriado(Timestamp dataCriado) {
		this.dataCriado = dataCriado;
	}

	public String getFileType() {
		return this.fileType;
	}

	public void setFileType(String fileType) {
		this.fileType = fileType;
	}

	public String getMarca() {
		return this.marca;
	}

	public void setMarca(String marca) {
		this.marca = marca;
	}

	public String getNome() {
		return this.nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public BigDecimal getPreco() {
		return this.preco;
	}

	public void setPreco(BigDecimal preco) {
		this.preco = preco;
	}

	public String getUuid() {
		return this.uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public User getUser() {
		return this.user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Pesquisapreco(String nome, byte[] data, String fileType) {
		super();
		this.data = data;
		this.fileType = fileType;
		this.nome = nome;
	}

	public Pesquisapreco(String id, String marca, String nome, BigDecimal preco, String uuid) {
		super();
		this.id = id;
		this.marca = marca;
		this.nome = nome;
		this.preco = preco;
		this.uuid = uuid;
	}

	public Pesquisapreco(String id, String nome, BigDecimal preco, String uuid) {
		super();
		this.id = id;
		this.nome = nome;
		this.preco = preco;
		this.uuid = uuid;
	}
	
	
	
	
	
	

}