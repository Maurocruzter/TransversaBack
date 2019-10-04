package br.transversa.backend.model;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.Table;


/**
 * The persistent class for the produtos_has_tipo_produtos_complementar database table.
 * 
 */
@Entity
@Table(name="produtos_has_tipo_produtos_complementar")
@NamedQuery(name="ProdutosHasTipoProdutosComplementar.findAll", query="SELECT p FROM ProdutosHasTipoProdutosComplementar p")
public class ProdutosHasTipoProdutosComplementar implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="id_produto_tipo_produto_complementar")
    private Long id;

	@Column(name="data_adicionado")
	private Timestamp dataAdicionado;

	//bi-directional many-to-one association to Produto
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="produtos_id")
	private Produto produto;

	//bi-directional many-to-one association to TipoProduto
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="tipo_produtos_id")
	private TipoProduto tipoProduto;

	//bi-directional many-to-one association to User
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="users_id")
	private User user;

	public ProdutosHasTipoProdutosComplementar() {
	}



	public Long getId() {
		return id;
	}



	public void setId(Long id) {
		this.id = id;
	}



	public Timestamp getDataAdicionado() {
		return this.dataAdicionado;
	}

	public void setDataAdicionado(Timestamp dataAdicionado) {
		this.dataAdicionado = dataAdicionado;
	}

	public Produto getProduto() {
		return this.produto;
	}

	public void setProduto(Produto produto) {
		this.produto = produto;
	}

	public TipoProduto getTipoProduto() {
		return this.tipoProduto;
	}

	public void setTipoProduto(TipoProduto tipoProduto) {
		this.tipoProduto = tipoProduto;
	}

	public User getUser() {
		return this.user;
	}

	public void setUser(User user) {
		this.user = user;
	}

}