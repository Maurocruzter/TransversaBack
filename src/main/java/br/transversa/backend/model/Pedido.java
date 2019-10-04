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
	
	@Column(name="total_pedido")
	private BigDecimal totalPedido;

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