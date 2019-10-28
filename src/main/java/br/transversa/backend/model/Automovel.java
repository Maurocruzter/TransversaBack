package br.transversa.backend.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

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
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import org.hibernate.annotations.GenericGenerator;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;


/**
 * The persistent class for the produtos database table.
 * 
 */
@Entity
@Table(name="automovel")
@NamedQuery(name="Automovel.findAll", query="SELECT a FROM Automovel a")
@JsonInclude(value = Include.NON_NULL)
public class Automovel implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="id_automovel")
    private Long id;

	@Column(name="data_adicionado")
	private Timestamp dataAdicionado;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="data_alterado")
	private Date dataAlterado;
	
	private String descricao;
	
	private String matricula;

	//bi-directional many-to-one association to User
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="users_id")
	private User user;

	public Automovel() {
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Timestamp getDataAdicionado() {
		return dataAdicionado;
	}

	public void setDataAdicionado(Timestamp dataAdicionado) {
		this.dataAdicionado = dataAdicionado;
	}

	public Date getDataAlterado() {
		return dataAlterado;
	}

	public void setDataAlterado(Date dataAlterado) {
		this.dataAlterado = dataAlterado;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public String getMatricula() {
		return matricula;
	}

	public void setMatricula(String matricula) {
		this.matricula = matricula;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Automovel(Long id) {
		super();
		this.id = id;
	}

	public Automovel(Long id, String matricula) {
		super();
		this.id = id;
		this.matricula = matricula;
	}
	
	

	

}