package br.transversa.backend.model;

import java.io.Serializable;
import javax.persistence.*;
import java.sql.Timestamp;


/**
 * The persistent class for the entrada_produto_stock database table.
 * 
 */
@Entity
@Table(name="entrada_produto_stock")
@NamedQuery(name="EntradaProdutoStock.findAll", query="SELECT e FROM EntradaProdutoStock e")
public class EntradaProdutoStock implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id_entrada_produto", unique=true, nullable=false)
	private Long id;

	@Column(name="data_entrada", nullable=false)
	private Timestamp dataEntrada;

	@Column(nullable=false)
	private int quantidade;

	//bi-directional many-to-one association to Produto
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="produtos_id", nullable=false)
	private Produto produto;

	//bi-directional many-to-one association to User
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="users_id_user", nullable=false)
	private User user;

	public EntradaProdutoStock() {
	}

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Timestamp getDataEntrada() {
		return this.dataEntrada;
	}

	public void setDataEntrada(Timestamp dataEntrada) {
		this.dataEntrada = dataEntrada;
	}

	public int getQuantidade() {
		return this.quantidade;
	}

	public void setQuantidade(int quantidade) {
		this.quantidade = quantidade;
	}

	public Produto getProduto() {
		return this.produto;
	}

	public void setProduto(Produto produto) {
		this.produto = produto;
	}

	public User getUser() {
		return this.user;
	}

	public void setUser(User user) {
		this.user = user;
	}

}