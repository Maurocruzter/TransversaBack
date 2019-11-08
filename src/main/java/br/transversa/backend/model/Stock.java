package br.transversa.backend.model;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the stock database table.
 * 
 */
@Entity
@Table(name="stock")
@NamedQuery(name="Stock.findAll", query="SELECT s FROM Stock s")
public class Stock implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id_stock", unique=true, nullable=false)
	private Long id;

	@Column(nullable=false)
	private int quantidade;

	//bi-directional many-to-one association to Promocoes
	@OneToMany(mappedBy="stock")
	private List<Promocoes> promocoes;

	//bi-directional many-to-one association to Produto
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="produtos_id", nullable=false)
	private Produto produto;

	
	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public int getQuantidade() {
		return this.quantidade;
	}

	public void setQuantidade(int quantidade) {
		this.quantidade = quantidade;
	}

	public List<Promocoes> getPromocoes() {
		return this.promocoes;
	}

	public void setPromocoes(List<Promocoes> promocoes) {
		this.promocoes = promocoes;
	}

	public Promocoes addPromocoe(Promocoes promocoe) {
		getPromocoes().add(promocoe);
		promocoe.setStock(this);

		return promocoe;
	}

	public Promocoes removePromocoe(Promocoes promocoe) {
		getPromocoes().remove(promocoe);
		promocoe.setStock(null);

		return promocoe;
	}

	public Produto getProduto() {
		return this.produto;
	}

	public void setProduto(Produto produto) {
		this.produto = produto;
	}
	
	public Stock() {
	}

	public Stock(Long id) {
		super();
		this.id = id;
	}

	

}