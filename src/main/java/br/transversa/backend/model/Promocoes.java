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
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;


/**
 * The persistent class for the produtos database table.
 * 
 */
@Entity
@Table(name="promocoes")
@NamedQuery(name="Promocoes.findAll", query="SELECT p FROM Promocoes p")
@JsonInclude(value = Include.NON_NULL)
public class Promocoes implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id_promocoes", unique=true, nullable=false)
	private Long id;

	@Column(name="compra_minima")
	private int compraMinima;

	@Column(name="data_adicionado", nullable=false)
	private Timestamp dataAdicionado;

	@Temporal(TemporalType.DATE)
	@Column(name="data_fim")
	private Date dataFim;

	@Temporal(TemporalType.DATE)
	@Column(name="data_inicio")
	private Date dataInicio;

	@Column(precision=10, scale=2)
	private BigDecimal desconto;
	
	@Transient
	private Long produtoId;
	
	@Transient
	private BigDecimal preco; 
	
	@Transient
	private String nome;
	
	@Transient
	private String uuid;

	//bi-directional many-to-one association to Stock
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="stock_id", nullable=false)
	private Stock stock;

	//bi-directional many-to-one association to User
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="users_id", nullable=false)
	private User user;

	//bi-directional many-to-one association to StockPromocao
	@OneToMany(mappedBy="promocoe")
	private List<StockPromocao> stockPromocaos;

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public int getCompraMinima() {
		return this.compraMinima;
	}

	public void setCompraMinima(int compraMinima) {
		this.compraMinima = compraMinima;
	}

	public Timestamp getDataAdicionado() {
		return this.dataAdicionado;
	}

	public void setDataAdicionado(Timestamp dataAdicionado) {
		this.dataAdicionado = dataAdicionado;
	}

	public Date getDataFim() {
		return this.dataFim;
	}

	public void setDataFim(Date dataFim) {
		this.dataFim = dataFim;
	}

	public Date getDataInicio() {
		return this.dataInicio;
	}

	public void setDataInicio(Date dataInicio) {
		this.dataInicio = dataInicio;
	}

	public BigDecimal getDesconto() {
		return this.desconto;
	}

	public void setDesconto(BigDecimal desconto) {
		this.desconto = desconto;
	}

	public Stock getStock() {
		return this.stock;
	}

	public void setStock(Stock stock) {
		this.stock = stock;
	}

	public User getUser() {
		return this.user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public List<StockPromocao> getStockPromocaos() {
		return this.stockPromocaos;
	}

	public void setStockPromocaos(List<StockPromocao> stockPromocaos) {
		this.stockPromocaos = stockPromocaos;
	}

	public StockPromocao addStockPromocao(StockPromocao stockPromocao) {
		getStockPromocaos().add(stockPromocao);
		stockPromocao.setPromocoe(this);

		return stockPromocao;
	}

	public StockPromocao removeStockPromocao(StockPromocao stockPromocao) {
		getStockPromocaos().remove(stockPromocao);
		stockPromocao.setPromocoe(null);

		return stockPromocao;
	}

	public Promocoes(Long id, Date dataInicio, Date dataFim, 
			int compraMinima, BigDecimal desconto, Long produtoId) {
		super();
		this.id = id;
		this.dataInicio = dataInicio;
		this.dataFim = dataFim;
		this.compraMinima = compraMinima;
		this.desconto = desconto;
		this.produtoId = produtoId;
	}
	
	public Promocoes(Long id, Date dataInicio, Date dataFim, 
			int compraMinima, BigDecimal desconto, Long produtoId,
			BigDecimal preco, String nome, String uuid) {
		super();
		this.id = id;
		this.dataInicio = dataInicio;
		this.dataFim = dataFim;
		this.compraMinima = compraMinima;
		this.desconto = desconto;
		this.produtoId = produtoId;
		this.preco = preco;
		this.nome = nome;
		this.uuid = uuid;
	}
	
	public Promocoes(Long id, String nome, BigDecimal preco, String uuid) {
		this.id = id;
		this.nome = nome;
		this.preco = preco;
		this.uuid = uuid;
	}

	public Promocoes() {
		// TODO Auto-generated constructor stub
	}

	public Promocoes(Long id) {
		super();
		this.id = id;
	}

	public Long getProdutoId() {
		return produtoId;
	}

	public void setProdutoId(Long produtoId) {
		this.produtoId = produtoId;
	}

	public BigDecimal getPreco() {
		return preco;
	}

	public void setPreco(BigDecimal preco) {
		this.preco = preco;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	

	
	
	
	

}