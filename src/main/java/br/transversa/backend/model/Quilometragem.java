package br.transversa.backend.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.GenericGenerator;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;


/**
 * The persistent class for the produtos database table.
 * 
 */
@Entity
@Table(name="quilometragem")
@NamedQuery(name="Quilometragem.findAll", query="SELECT q FROM Quilometragem q")
@JsonInclude(value = Include.NON_NULL)
public class Quilometragem implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="id_quilometragem")
    private Long id;
	
	@Column(name="quilometragem_atual")
	private BigDecimal quilometragemAtual;
	
	//bi-directional many-to-one association to User
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="automovel_id")
	private Automovel automovel;
	
	//bi-directional many-to-one association to User
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="users_id")
	private User user;
	
	@Column(name="data_adicionado")
	private Timestamp dataAdicionado;
	
	@Transient
	private String matricula;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public BigDecimal getQuilometragemAtual() {
		return quilometragemAtual;
	}

	public void setQuilometragemAtual(BigDecimal quilometragemAtual) {
		this.quilometragemAtual = quilometragemAtual;
	}

	public Automovel getAutomovel() {
		return automovel;
	}

	public void setAutomovel(Automovel automovel) {
		this.automovel = automovel;
	}

	
	@Column(name="file_type")
	private String fileType;
	
	@Lob
	private byte[] data;
	
	@GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
	private String uuid;

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Timestamp getDataAdicionado() {
		return dataAdicionado;
	}

	public void setDataAdicionado(Timestamp dataAdicionado) {
		this.dataAdicionado = dataAdicionado;
	}

	public String getFileType() {
		return fileType;
	}

	public void setFileType(String fileType) {
		this.fileType = fileType;
	}

	public byte[] getData() {
		return data;
	}

	public void setData(byte[] data) {
		this.data = data;
	}

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}
	
	

	public String getMatricula() {
		return matricula;
	}

	public void setMatricula(String matricula) {
		this.matricula = matricula;
	}

	public Quilometragem(Long id) {
		super();
		this.id = id;
	}

	public Quilometragem() {
		super();
	}

	public Quilometragem(Long id, String matricula, BigDecimal quilometragemAtual) {
		super();
		this.id = id;
		this.quilometragemAtual = quilometragemAtual;
		this.matricula = matricula;
	}
	
	public Quilometragem(Long id, String matricula) {
		super();
		this.id = id;
		this.matricula = matricula;
	}

	

	

}