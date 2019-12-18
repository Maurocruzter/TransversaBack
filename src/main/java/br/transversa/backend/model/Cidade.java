package br.transversa.backend.model;


import java.io.Serializable;
import java.util.List;

import javax.persistence.*;

import br.transversa.backend.util.AppConstants;


/**
 * The persistent class for the cidade database table.
 * 
 */
@Entity
@Table(name="cidade")
@NamedQuery(name="Cidade.findAll", query="SELECT c FROM Cidade c")
public class Cidade implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id_cidade", unique=true, nullable=false)
	private Long id;

	@Column(nullable=false, length=45)
	private String abreviacao;

	@Column(nullable=false, length=45)
	private String nome;
	
	@Transient
	private String estadoNome;


	@Column(name="id_estado", nullable=false)
	private int idEstado;
	
	//bi-directional many-to-one association to Rota
	@OneToMany(mappedBy="cidade")
	private List<Rota> rotas;



	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getAbreviacao() {
		return this.abreviacao;
	}

	public void setAbreviacao(String abreviacao) {
		this.abreviacao = abreviacao;
	}

	public String getNome() {
		return this.nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}
	

	public int getIdEstado() {
		return idEstado;
	}

	public void setIdEstado(int idEstado) {
		this.idEstado = idEstado;
	}

	public String getEstadoNome() {
		return estadoNome;
	}

	public void setEstadoNome(String estadoNome) {
		this.estadoNome = estadoNome;
	}
	
	public List<Rota> getRotas() {
		return this.rotas;
	}

	public void setRotas(List<Rota> rotas) {
		this.rotas = rotas;
	}

	public Rota addRota(Rota rota) {
		getRotas().add(rota);
		rota.setCidade(this);

		return rota;
	}

	public Rota removeRota(Rota rota) {
		getRotas().remove(rota);
		rota.setCidade(null);

		return rota;
	}

	public Cidade() {
	}

	public Cidade(Long id, String abreviacao, String nome, int idEstado) {
		super();
		this.id = id;
		this.abreviacao = abreviacao;
		this.nome = nome;
		this.estadoNome = AppConstants.ESTADOS_BRASILEIROS.get(idEstado);
	}
	
	
}