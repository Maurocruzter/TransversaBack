package br.transversa.backend.model;

import java.io.Serializable;
import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;


import java.sql.Timestamp;
import java.util.List;


/**
 * The persistent class for the tipo_produtos database table.
 * 
 */
@Entity
@Table(name="tipo_produtos")
@NamedQuery(name="TipoProduto.findAll", query="SELECT t FROM TipoProduto t")
public class TipoProduto implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="id_tipo_produto")
    private Long id;

	@Column(name="data_adicionado")
	private Timestamp dataAdicionado;

	private String nome;

	//bi-directional many-to-one association to ProdutoHasTipoProdutosSubstituto
	@OneToMany(mappedBy="tipoProduto")
	private List<ProdutoHasTipoProdutosSubstituto> produtoHasTipoProdutosSubstitutos;

	//bi-directional many-to-one association to ProdutosHasTipoProduto
	@OneToMany(mappedBy="tipoProduto")
	private List<ProdutosHasTipoProduto> produtosHasTipoProdutos;

	//bi-directional many-to-one association to ProdutosHasTipoProdutosComplementar
	@OneToMany(mappedBy="tipoProduto")
	private List<ProdutosHasTipoProdutosComplementar> produtosHasTipoProdutosComplementars;

	//bi-directional many-to-one association to User
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="users_id")
	private User user;

	public TipoProduto() {
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

	public String getNome() {
		return this.nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public List<ProdutoHasTipoProdutosSubstituto> getProdutoHasTipoProdutosSubstitutos() {
		return this.produtoHasTipoProdutosSubstitutos;
	}

	public void setProdutoHasTipoProdutosSubstitutos(List<ProdutoHasTipoProdutosSubstituto> produtoHasTipoProdutosSubstitutos) {
		this.produtoHasTipoProdutosSubstitutos = produtoHasTipoProdutosSubstitutos;
	}

	public ProdutoHasTipoProdutosSubstituto addProdutoHasTipoProdutosSubstituto(ProdutoHasTipoProdutosSubstituto produtoHasTipoProdutosSubstituto) {
		getProdutoHasTipoProdutosSubstitutos().add(produtoHasTipoProdutosSubstituto);
		produtoHasTipoProdutosSubstituto.setTipoProduto(this);

		return produtoHasTipoProdutosSubstituto;
	}

	public ProdutoHasTipoProdutosSubstituto removeProdutoHasTipoProdutosSubstituto(ProdutoHasTipoProdutosSubstituto produtoHasTipoProdutosSubstituto) {
		getProdutoHasTipoProdutosSubstitutos().remove(produtoHasTipoProdutosSubstituto);
		produtoHasTipoProdutosSubstituto.setTipoProduto(null);

		return produtoHasTipoProdutosSubstituto;
	}

	public List<ProdutosHasTipoProduto> getProdutosHasTipoProdutos() {
		return this.produtosHasTipoProdutos;
	}

	public void setProdutosHasTipoProdutos(List<ProdutosHasTipoProduto> produtosHasTipoProdutos) {
		this.produtosHasTipoProdutos = produtosHasTipoProdutos;
	}

	public ProdutosHasTipoProduto addProdutosHasTipoProduto(ProdutosHasTipoProduto produtosHasTipoProduto) {
		getProdutosHasTipoProdutos().add(produtosHasTipoProduto);
		produtosHasTipoProduto.setTipoProduto(this);

		return produtosHasTipoProduto;
	}

	public ProdutosHasTipoProduto removeProdutosHasTipoProduto(ProdutosHasTipoProduto produtosHasTipoProduto) {
		getProdutosHasTipoProdutos().remove(produtosHasTipoProduto);
		produtosHasTipoProduto.setTipoProduto(null);

		return produtosHasTipoProduto;
	}

	public List<ProdutosHasTipoProdutosComplementar> getProdutosHasTipoProdutosComplementars() {
		return this.produtosHasTipoProdutosComplementars;
	}

	public void setProdutosHasTipoProdutosComplementars(List<ProdutosHasTipoProdutosComplementar> produtosHasTipoProdutosComplementars) {
		this.produtosHasTipoProdutosComplementars = produtosHasTipoProdutosComplementars;
	}

	public ProdutosHasTipoProdutosComplementar addProdutosHasTipoProdutosComplementar(ProdutosHasTipoProdutosComplementar produtosHasTipoProdutosComplementar) {
		getProdutosHasTipoProdutosComplementars().add(produtosHasTipoProdutosComplementar);
		produtosHasTipoProdutosComplementar.setTipoProduto(this);

		return produtosHasTipoProdutosComplementar;
	}

	public ProdutosHasTipoProdutosComplementar removeProdutosHasTipoProdutosComplementar(ProdutosHasTipoProdutosComplementar produtosHasTipoProdutosComplementar) {
		getProdutosHasTipoProdutosComplementars().remove(produtosHasTipoProdutosComplementar);
		produtosHasTipoProdutosComplementar.setTipoProduto(null);

		return produtosHasTipoProdutosComplementar;
	}

	public User getUser() {
		return this.user;
	}

	public void setUser(User user) {
		this.user = user;
	}

}