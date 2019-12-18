package br.transversa.backend.model;

import java.io.Serializable;
import javax.persistence.*;
import java.sql.Timestamp;


/**
 * The persistent class for the entrada_stock_promocao database table.
 * 
 */
@Entity
@Table(name="entrada_stock_promocao")
@NamedQuery(name="EntradaStockPromocao.findAll", query="SELECT e FROM EntradaStockPromocao e")
public class EntradaStockPromocao implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id_entrada_stock_promocao", unique=true, nullable=false)
	private Long id;

	@Column(name="data_entrada", nullable=false)
	private Timestamp dataEntrada;

	@Column(nullable=false)
	private int quantidade;

	//bi-directional many-to-one association to Produto
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="stock_promocao_id", nullable=false)
	private StockPromocao stockPromocao;
	
	// bi-directional many-to-one association to User
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "users_id", nullable = false)
	private User user;

	public EntradaStockPromocao() {
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

	public StockPromocao getStockPromocao() {
		return stockPromocao;
	}

	public void setStockPromocao(StockPromocao stockPromocao) {
		this.stockPromocao = stockPromocao;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}


	
	
}