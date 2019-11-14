package br.transversa.backend.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Date;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonFormat;


/**
 * The persistent class for the stock_promocao database table.
 * 
 */
@Entity
@Table(name="stock_promocao")
@NamedQuery(name="StockPromocao.findAll", query="SELECT s FROM StockPromocao s")
public class StockPromocao implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id_stock_promocao", unique=true, nullable=false)
	private Long id;

	@Column(name="quantidade", nullable=false)
	private int quantidadeEmPromocao;

	//bi-directional many-to-one association to Promocoes
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="promocoes_id", nullable=false)
	private Promocoes promocoe;

	@Transient
	private Timestamp dataInicio;
	
	@Transient
	private Timestamp dataFim;

	@Transient
	private int compraMinima;
	
	@Transient
	private int quantidadeEmStock;

	

	@Transient
	private BigDecimal desconto;

	@Transient
	private Long produtoId;

	@Transient
	private BigDecimal preco;

	@Transient
	private String nome;

	@Transient
	private String uuid;

	

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	

	public int getQuantidadeEmPromocao() {
		return quantidadeEmPromocao;
	}

	public void setQuantidadeEmPromocao(int quantidadeEmPromocao) {
		this.quantidadeEmPromocao = quantidadeEmPromocao;
	}

	public Promocoes getPromocoe() {
		return this.promocoe;
	}

	public void setPromocoe(Promocoes promocoe) {
		this.promocoe = promocoe;
	}
	
	public StockPromocao() {
	}

	public StockPromocao(Long id) {
		super();
		this.id = id;
	}
	
	


	public Timestamp getDataInicio() {
		return dataInicio;
	}

	public void setDataInicio(Timestamp dataInicio) {
		this.dataInicio = dataInicio;
	}

	public Timestamp getDataFim() {
		return dataFim;
	}

	public void setDataFim(Timestamp dataFim) {
		this.dataFim = dataFim;
	}

	public int getCompraMinima() {
		return compraMinima;
	}

	public void setCompraMinima(int compraMinima) {
		this.compraMinima = compraMinima;
	}


	public BigDecimal getDesconto() {
		return desconto;
	}

	public void setDesconto(BigDecimal desconto) {
		this.desconto = desconto;
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
	
	
	
	
	public int getQuantidadeEmStock() {
		return quantidadeEmStock;
	}

	public void setQuantidadeEmStock(int quantidadeEmStock) {
		this.quantidadeEmStock = quantidadeEmStock;
	}

	public StockPromocao(Long id, Date dataInicio, Date dataFim, int compraMinima, BigDecimal desconto, 
			Long produtoId, BigDecimal preco, String nome , String uuid ) {
		super();
		this.id = id;
		this.dataInicio = new Timestamp(dataInicio.getTime());
		this.dataFim = new Timestamp(dataFim.getTime());
		this.compraMinima = compraMinima;
		this.desconto = desconto;
		this.produtoId = produtoId;
		this.preco = preco;
		this.nome = nome;
		this.uuid = uuid;

	}
	
	public StockPromocao(Long id, Date dataInicio, Date dataFim, int compraMinima, BigDecimal desconto, 
			Long produtoId, BigDecimal preco, String nome , String uuid, int quantidade ) {
		super();
		this.id = id;
		this.dataInicio = new Timestamp(dataInicio.getTime());
		this.dataFim = new Timestamp(dataFim.getTime());
		this.compraMinima = compraMinima;
		this.desconto = desconto;
		this.produtoId = produtoId;
		this.preco = preco;
		this.nome = nome;
		this.uuid = uuid;
		this.quantidadeEmPromocao = quantidade;

	}
	
	
	public StockPromocao(Long id, Date dataInicio, Date dataFim, int compraMinima, BigDecimal desconto, 
			Long produtoId, BigDecimal preco, String nome , String uuid, int quantidade, int quantidadeEmStock ) {
		super();
		this.id = id;
		this.dataInicio = new Timestamp(dataInicio.getTime());
		this.dataFim = new Timestamp(dataFim.getTime());
		this.compraMinima = compraMinima;
		this.desconto = desconto;
		this.produtoId = produtoId;
		this.preco = preco;
		this.nome = nome;
		this.uuid = uuid;
		this.quantidadeEmPromocao = quantidade;
		this.quantidadeEmStock = quantidadeEmStock;

	}

	
	

}