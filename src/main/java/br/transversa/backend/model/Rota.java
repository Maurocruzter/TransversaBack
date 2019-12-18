package br.transversa.backend.model;

import java.io.Serializable;
import javax.persistence.*;

import br.transversa.backend.util.AppConstants;


/**
 * The persistent class for the rota database table.
 * 
 */
@Entity
@Table(name="rota")
@NamedQuery(name="Rota.findAll", query="SELECT r FROM Rota r")
public class Rota implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id_rota", unique=true, nullable=false)
	private Long id;

	@Column(nullable=false, length=45)
	private String designacao;

	//bi-directional many-to-one association to Cidade
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="cidade_id", nullable=false)
	private Cidade cidade;
	
	@Transient
	String cidadeNome;
	
	@Transient
	Long cidadeId;
	
	@Transient
	String estadoNome;


	

	public Long getId() {
		return id;
	}



	public void setId(Long id) {
		this.id = id;
	}



	public String getDesignacao() {
		return this.designacao;
	}

	public void setDesignacao(String designacao) {
		this.designacao = designacao;
	}

	public Cidade getCidade() {
		return this.cidade;
	}

	public void setCidade(Cidade cidade) {
		this.cidade = cidade;
	}



	public String getCidadeNome() {
		return cidadeNome;
	}



	public void setCidadeNome(String cidadeNome) {
		this.cidadeNome = cidadeNome;
	}



	public Long getCidadeId() {
		return cidadeId;
	}



	public void setCidadeId(Long cidadeId) {
		this.cidadeId = cidadeId;
	}

	
	


	public String getEstadoNome() {
		return estadoNome;
	}



	public void setEstadoNome(String estadoNome) {
		this.estadoNome = estadoNome;
	}



	public Rota() {
	}



	public Rota(Long id, String designacao, String cidadeNome, int idEstado) {
		super();
		this.id = id;
		this.designacao = designacao;
		this.cidadeNome = cidadeNome;
		this.estadoNome = AppConstants.ESTADOS_BRASILEIROS.get(idEstado);
	}

	
	


	
	
}