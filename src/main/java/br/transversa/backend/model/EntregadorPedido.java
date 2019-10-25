package br.transversa.backend.model;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;


import java.sql.Timestamp;
import java.util.Date;
import java.util.List;


/**
 * The persistent class for the estado_pedidos database table.
 * 
 */
@Entity
@Table(name="entregador_pedidos")
@NamedQuery(name="EntregadorPedido.findAll", query="SELECT e FROM EntregadorPedido e")
@JsonInclude(value = Include.NON_NULL)
public class EntregadorPedido implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id_entregador_pedido")
	private Long id;

	@Column(name="data_atribuido_entregador")
	private Timestamp dataAdicionado;
	
	@Column(name="data_entregue")
	private Timestamp dataEntregue;


	//bi-directional many-to-one association to Pedido
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="estado_pedidos_id")
	private EstadoPedido estadoPedido;

	//bi-directional many-to-one association to User
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="users_id")
	private User user;
	
	private int currestado;
	
//	
//	@Transient
//	private String nomeVendedor;
//	
//	@Transient
//	private String nomeCliente;
//	
//	@Transient
//	private Long idVendedor;
//	
//	@Transient
//	private Long idCliente;
//	
//	@Transient
//	private BigDecimal precoTotal;
//	
//	@Transient
//	private Long idPedido;
//	
//	@Transient
//	private byte isAprovado;
//
//	@Transient
//	private byte isFinalizado;
//
//	@Transient
//	private byte isTransporte;
//	
//	@Transient
//	private int clienteReclamouEstado;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Timestamp getDataAdicionado() {
		return dataAdicionado;
	}

	public void setDataAdicionado(Timestamp dataAdicionado) {
		this.dataAdicionado = dataAdicionado;
	}

	public Timestamp getDataEntregue() {
		return dataEntregue;
	}

	public void setDataEntregue(Timestamp dataEntregue) {
		this.dataEntregue = dataEntregue;
	}
	
	
	public EstadoPedido getEstadoPedido() {
		return estadoPedido;
	}

	public void setEstadoPedido(EstadoPedido estadoPedido) {
		this.estadoPedido = estadoPedido;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public int getCurrestado() {
		return currestado;
	}

	public void setCurrestado(int currestado) {
		this.currestado = currestado;
	}

	public EntregadorPedido(Long id) {
		super();
		this.id = id;
	}

	public EntregadorPedido() {
	}

//	public String getNomeVendedor() {
//		return nomeVendedor;
//	}
//
//	public void setNomeVendedor(String nomeVendedor) {
//		this.nomeVendedor = nomeVendedor;
//	}
//
//	public String getNomeCliente() {
//		return nomeCliente;
//	}
//
//	public void setNomeCliente(String nomeCliente) {
//		this.nomeCliente = nomeCliente;
//	}
//
//	public Long getIdVendedor() {
//		return idVendedor;
//	}
//
//	public void setIdVendedor(Long idVendedor) {
//		this.idVendedor = idVendedor;
//	}
//
//	public Long getIdCliente() {
//		return idCliente;
//	}
//
//	public void setIdCliente(Long idCliente) {
//		this.idCliente = idCliente;
//	}
//
//	public BigDecimal getPrecoTotal() {
//		return precoTotal;
//	}
//
//	public void setPrecoTotal(BigDecimal precoTotal) {
//		this.precoTotal = precoTotal;
//	}
//
//	public Long getIdPedido() {
//		return idPedido;
//	}
//
//	public void setIdPedido(Long idPedido) {
//		this.idPedido = idPedido;
//	}
//
//	public byte getIsAprovado() {
//		return isAprovado;
//	}
//
//	public void setIsAprovado(byte isAprovado) {
//		this.isAprovado = isAprovado;
//	}
//
//	public byte getIsFinalizado() {
//		return isFinalizado;
//	}
//
//	public void setIsFinalizado(byte isFinalizado) {
//		this.isFinalizado = isFinalizado;
//	}
//
//	public byte getIsTransporte() {
//		return isTransporte;
//	}
//
//	public void setIsTransporte(byte isTransporte) {
//		this.isTransporte = isTransporte;
//	}
//
//	public int getClienteReclamouEstado() {
//		return clienteReclamouEstado;
//	}
//
//	public void setClienteReclamouEstado(int clienteReclamouEstado) {
//		this.clienteReclamouEstado = clienteReclamouEstado;
//	}

	
	
	
}