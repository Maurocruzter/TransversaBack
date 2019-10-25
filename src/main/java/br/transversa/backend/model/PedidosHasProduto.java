package br.transversa.backend.model;

import java.io.Serializable;
import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import java.math.BigDecimal;


/**
 * The persistent class for the pedidos_has_produtos database table.
 * 
 */
@Entity
@Table(name="pedidos_has_produtos")
@NamedQuery(name="PedidosHasProduto.findAll", query="SELECT p FROM PedidosHasProduto p")
@JsonInclude(value = Include.NON_NULL)
public class PedidosHasProduto implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id_pedido_produto")
	private Long id;

	private BigDecimal preco;

	private int quantidade;

	//bi-directional many-to-one association to Pedido
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="pedidos_id_pedidos")
	private Pedido pedido;

	
	
	//bi-directional many-to-one association to Produto
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="produtos_id_produto")
	private Produto produto;
	
	@Transient
	private String produtoNome;
	
	@Transient
	private String uuid;
	
	@Transient
	private byte isAprovado;

	@Transient
	private byte isFinalizado;

	@Transient
	private byte isTransporte;
	
	@Transient
	private byte isCancelado;

	@Transient
	private byte isEntregue;

	
	
	public PedidosHasProduto(Long id, BigDecimal preco, String produtoNome, int quantidade) {
		super();
		this.id = id;
		this.preco = preco;
		this.produtoNome = produtoNome;
		this.quantidade = quantidade;
	}
	
	
	public PedidosHasProduto(Long id, BigDecimal preco, String produtoNome, int quantidade, String uuid) {
		super();
		this.id = id;
		this.preco = preco;
		this.produtoNome = produtoNome;
		this.quantidade = quantidade;
		this.uuid = uuid;
	}
	
	public PedidosHasProduto(Long id, BigDecimal preco, String produtoNome, int quantidade, String uuid,
			byte isAprovado, byte isFinalizado, byte isTransporte, byte isCancelado, byte isEntregue) {
		super();
		this.id = id;
		this.preco = preco;
		this.produtoNome = produtoNome;
		this.quantidade = quantidade;
		this.uuid = uuid;
		this.isAprovado = isAprovado;
		this.isFinalizado = isFinalizado;
		this.isTransporte = isTransporte;
		this.isCancelado = isCancelado;
		this.isEntregue = isEntregue;
	}

	public Long getId() {
		return id;
	}



	public void setId(Long id) {
		this.id = id;
	}



	public BigDecimal getPreco() {
		return this.preco;
	}

	public void setPreco(BigDecimal preco) {
		this.preco = preco;
	}

	public int getQuantidade() {
		return this.quantidade;
	}

	public void setQuantidade(int quantidade) {
		this.quantidade = quantidade;
	}

	public Pedido getPedido() {
		return this.pedido;
	}

	public void setPedido(Pedido pedido) {
		this.pedido = pedido;
	}

	public Produto getProduto() {
		return this.produto;
	}

	public void setProduto(Produto produto) {
		this.produto = produto;
	}

	public String getProdutoNome() {
		return produtoNome;
	}

	public void setProdutoNome(String produtoNome) {
		this.produtoNome = produtoNome;
	}

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	
	
	
	public byte getIsAprovado() {
		return isAprovado;
	}


	public void setIsAprovado(byte isAprovado) {
		this.isAprovado = isAprovado;
	}


	public byte getIsFinalizado() {
		return isFinalizado;
	}


	public void setIsFinalizado(byte isFinalizado) {
		this.isFinalizado = isFinalizado;
	}


	public byte getIsTransporte() {
		return isTransporte;
	}


	public void setIsTransporte(byte isTransporte) {
		this.isTransporte = isTransporte;
	}
	
	


	public byte getIsCancelado() {
		return isCancelado;
	}


	public void setIsCancelado(byte isCancelado) {
		this.isCancelado = isCancelado;
	}


	public byte getIsEntregue() {
		return isEntregue;
	}


	public void setIsEntregue(byte isEntregue) {
		this.isEntregue = isEntregue;
	}


	public PedidosHasProduto() {
	}
	

}