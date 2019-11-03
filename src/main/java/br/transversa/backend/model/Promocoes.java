package br.transversa.backend.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
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
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="id_promocoes")
    private Long id;

	@Column(name="data_inicio")
	private Date dataInicio;
	
	@Column(name="data_fim")
	private Date dataFim;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="data_adicionado")
	private Date dataAdicionado;

	@Column(name="compra_minima")
	private int compraMinima;
	
	private BigDecimal desconto;
	
	//bi-directional many-to-one association to User
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="produtos_id")
	private Produto produto;
	
	//bi-directional many-to-one association to User
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="users_id")
	private User user;
	
	@Transient
	private Long produtoId;
	
	@Transient
	private BigDecimal preco;
	
	@Transient
	private String nome;
	
	@Transient
	private String uuid;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}


	public Date getDataInicio() {
		return dataInicio;
	}

	public void setDataInicio(Date dataInicio) {
		this.dataInicio = dataInicio;
	}

	public Date getDataFim() {
		return dataFim;
	}

	public void setDataFim(Date dataFim) {
		this.dataFim = dataFim;
	}

	public Date getDataAdicionado() {
		return dataAdicionado;
	}

	public void setDataAdicionado(Date dataAdicionado) {
		this.dataAdicionado = dataAdicionado;
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

	public Produto getProduto() {
		return produto;
	}

	public void setProduto(Produto produto) {
		this.produto = produto;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
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

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	
	
	
	

}