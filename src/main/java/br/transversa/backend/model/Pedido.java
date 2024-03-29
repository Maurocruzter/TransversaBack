package br.transversa.backend.model;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.sql.Timestamp;
import java.util.List;


/**
 * The persistent class for the pedidos database table.
 * 
 */
@Entity
@Table(name="pedidos")
@NamedQuery(name="Pedido.findAll", query="SELECT p FROM Pedido p")
public class Pedido implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id_pedidos")
	private Long id;

//	@Column(name="data_adicionado_pedido")
//	private Timestamp dataAdicionado;

	@Column(name="is_aprovado")
	private byte isAprovado;

	@Column(name="is_finalizado")
	private byte isFinalizado;

	@Column(name="is_transporte")
	private byte isTransporte;
	
	@Column(name="is_entregue")
	private byte isEntregue;
	
	@Column(name="is_cancelado")
	private byte isCancelado;
	
	@Column(name="forma_pagamento")
	private int formaPagamento;
	
	@Column(name="cliente_reclamou_estado")
	private int clienteReclamouEstado;
	
	@Column(name="data_adicionado_pedido")
	private Timestamp dataAdicionadoPedido;

	//bi-directional many-to-one association to EstadoPedido
	@OneToMany(mappedBy="pedido")
	private List<EstadoPedido> estadoPedidos;

	//bi-directional many-to-one association to User
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="users_id_vendedor")
	private User user1;

	//bi-directional many-to-one association to User
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="users_id_cliente")
	private User user2;
	
	//bi-directional many-to-one association to User
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="users_id_receptor_comissao", nullable=false)
	private User user3;
	
	@Column(name="total_pedido")
	private BigDecimal totalPedido;
	
	@Column(name="comissao_vendedor")
	private BigDecimal comissaoVendedor;

	//bi-directional many-to-one association to PedidosHasProduto
	@OneToMany(mappedBy="pedido")
	private List<PedidosHasProduto> pedidosHasProdutos;

	public Pedido() {
	}

	

	public Long getId() {
		return id;
	}



	public void setId(Long id) {
		this.id = id;
	}

	


//	public Timestamp getDataAdicionado() {
//		return dataAdicionado;
//	}
//
//
//
//	public void setDataAdicionado(Timestamp dataAdicionado) {
//		this.dataAdicionado = dataAdicionado;
//	}




	

	public Timestamp getDataAdicionadoPedido() {
		return dataAdicionadoPedido;
	}



	public void setDataAdicionadoPedido(Timestamp dataAdicionadoPedido) {
		this.dataAdicionadoPedido = dataAdicionadoPedido;
	}



	public byte getIsAprovado() {
		return isAprovado;
	}



	public int getFormaPagamento() {
		return formaPagamento;
	}



	public void setFormaPagamento(int formaPagamento) {
		this.formaPagamento = formaPagamento;
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

	
	


	public byte getIsEntregue() {
		return isEntregue;
	}



	public void setIsEntregue(byte isEntregue) {
		this.isEntregue = isEntregue;
	}



	public byte getIsCancelado() {
		return isCancelado;
	}



	public void setIsCancelado(byte isCancelado) {
		this.isCancelado = isCancelado;
	}

	


	public int getClienteReclamouEstado() {
		return clienteReclamouEstado;
	}



	public void setClienteReclamouEstado(int clienteReclamouEstado) {
		this.clienteReclamouEstado = clienteReclamouEstado;
	}

	


	public User getUser3() {
		return user3;
	}



	public void setUser3(User user3) {
		this.user3 = user3;
	}



	public BigDecimal getComissaoVendedor() {
		return comissaoVendedor;
	}



	public void setComissaoVendedor(BigDecimal comissaoVendedor) {
		this.comissaoVendedor = comissaoVendedor;
	}



	public List<EstadoPedido> getEstadoPedidos() {
		return this.estadoPedidos;
	}

	public void setEstadoPedidos(List<EstadoPedido> estadoPedidos) {
		this.estadoPedidos = estadoPedidos;
	}

	public EstadoPedido addEstadoPedido(EstadoPedido estadoPedido) {
		getEstadoPedidos().add(estadoPedido);
		estadoPedido.setPedido(this);

		return estadoPedido;
	}

	public EstadoPedido removeEstadoPedido(EstadoPedido estadoPedido) {
		getEstadoPedidos().remove(estadoPedido);
		estadoPedido.setPedido(null);

		return estadoPedido;
	}

	public User getUser1() {
		return this.user1;
	}

	public void setUser1(User user1) {
		this.user1 = user1;
	}

	public User getUser2() {
		return this.user2;
	}

	public void setUser2(User user2) {
		this.user2 = user2;
	}

	public List<PedidosHasProduto> getPedidosHasProdutos() {
		return this.pedidosHasProdutos;
	}

	public void setPedidosHasProdutos(List<PedidosHasProduto> pedidosHasProdutos) {
		this.pedidosHasProdutos = pedidosHasProdutos;
	}

	public PedidosHasProduto addPedidosHasProduto(PedidosHasProduto pedidosHasProduto) {
		getPedidosHasProdutos().add(pedidosHasProduto);
		pedidosHasProduto.setPedido(this);

		return pedidosHasProduto;
	}

	public PedidosHasProduto removePedidosHasProduto(PedidosHasProduto pedidosHasProduto) {
		getPedidosHasProdutos().remove(pedidosHasProduto);
		pedidosHasProduto.setPedido(null);

		return pedidosHasProduto;
	}



	public BigDecimal getTotalPedido() {
		return totalPedido;
	}



	public void setTotalPedido(BigDecimal totalPedido) {
		this.totalPedido = totalPedido;
	}

	
}