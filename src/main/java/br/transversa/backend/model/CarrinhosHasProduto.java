package br.transversa.backend.model;

import java.io.Serializable;
import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonBackReference;


import java.sql.Timestamp;


/**
 * The persistent class for the carrinhos_has_produtos database table.
 * 
 */
@Entity
@Table(name="carrinhos_has_produtos")
@NamedQuery(name="CarrinhosHasProduto.findAll", query="SELECT c FROM CarrinhosHasProduto c")
public class CarrinhosHasProduto implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="id_carrinho_produto")
    private Long id;

	@Column(name="data_adicionado_carrinho_produto")
	private Timestamp dataAdicionado;

	private int quantidade;

	//bi-directional many-to-one association to Carrinho
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="carrinhos_id")
	private Carrinho carrinho;

	//bi-directional many-to-one association to Produto
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="produtos_id")
	private Produto produto;

	public CarrinhosHasProduto() {
	}


	public CarrinhosHasProduto(Long id) {
		super();
		this.id = id;
	}


	public CarrinhosHasProduto(Long id, int quantidade) {
		super();
		this.id = id;
		this.quantidade = quantidade;
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

	public int getQuantidade() {
		return this.quantidade;
	}

	public void setQuantidade(int quantidade) {
		this.quantidade = quantidade;
	}

	public Carrinho getCarrinho() {
		return this.carrinho;
	}

	public void setCarrinho(Carrinho carrinho) {
		this.carrinho = carrinho;
	}

	public Produto getProduto() {
		return this.produto;
	}

	public void setProduto(Produto produto) {
		this.produto = produto;
	}

}