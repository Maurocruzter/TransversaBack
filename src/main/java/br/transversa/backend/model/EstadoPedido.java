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
@Table(name="estado_pedidos")
@NamedQuery(name="EstadoPedido.findAll", query="SELECT e FROM EstadoPedido e")
@JsonInclude(value = Include.NON_NULL)
public class EstadoPedido implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id_estado_pedido")
	private Long id;

	@Column(name="data_adicionado_estado_pedido")
	private Timestamp dataAdicionado;

	private String observacao;

	//bi-directional many-to-one association to Pedido
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="pedidos_id")
	private Pedido pedido;

	//bi-directional many-to-one association to User
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="users_id")
	private User user;
	
	private int currestado;
	
	@Column(name="includes_photo")
	private byte includesPhoto;
	
	//bi-directional many-to-one association to ObservacaoEstadoPedido
	@OneToMany(mappedBy="estadoPedido")
	private List<ObservacaoEstadoPedido> observacaoEstadoPedidos;
	
	
	@Transient
	private String nomeVendedor;
	
	@Transient
	private String nomeCliente;
	
	@Transient
	private Long idVendedor;
	
	@Transient
	private Long idCliente;
	
	@Transient
	private BigDecimal precoTotal;
	
	@Transient
	private Long idPedido;
	
	@Transient
	private byte isAprovado;

	@Transient
	private byte isFinalizado;

	@Transient
	private byte isTransporte;
	
	@Transient
	private int clienteReclamouEstado;
	
	@Transient
	private Long idProduto;

	public EstadoPedido() {
	}

	
	


	public EstadoPedido(Long id) {
		super();
		this.id = id;
	}





	public EstadoPedido(Long id, String nomeVendedor, 
			Long idVendedor, String nomeCliente, 
			Long idCliente, Date dataAdicionado,
			BigDecimal precoTotal, String observacao,
			Long idPedido) {
		super();
		this.id = id;
		this.nomeVendedor = nomeVendedor;
		this.idVendedor = idVendedor;
		this.nomeCliente = nomeCliente;
		this.idCliente = idCliente;
		this.dataAdicionado = (Timestamp) dataAdicionado;
		this.precoTotal = precoTotal;
		this.observacao = observacao;
		this.idPedido = idPedido;
	}
	
	
	public EstadoPedido(Long id, String nomeVendedor, 
			Long idVendedor, String nomeCliente, 
			Long idCliente, Date dataAdicionado,
			String observacao, int clienteReclamouEstado, byte includesPhoto) {
		super();
		this.id = id;
		this.nomeVendedor = nomeVendedor;
		this.idVendedor = idVendedor;
		this.nomeCliente = nomeCliente;
		this.idCliente = idCliente;
		this.dataAdicionado = (Timestamp) dataAdicionado;
		this.observacao = observacao;
		this.clienteReclamouEstado = clienteReclamouEstado;
		this.includesPhoto = includesPhoto;
	}
	
	public EstadoPedido(Long id, String nomeVendedor, 
			Long idVendedor, String nomeCliente, 
			Long idCliente, Date dataAdicionado,
			String observacao, int clienteReclamouEstado, byte includesPhoto,
			Long idProduto) {
		super();
		this.id = id;
		this.nomeVendedor = nomeVendedor;
		this.idVendedor = idVendedor;
		this.nomeCliente = nomeCliente;
		this.idCliente = idCliente;
		this.dataAdicionado = (Timestamp) dataAdicionado;
		this.observacao = observacao;
		this.clienteReclamouEstado = clienteReclamouEstado;
		this.includesPhoto = includesPhoto;
		this.idProduto = idProduto;
	}

	public EstadoPedido(Long id,Date dataAdicionado, int currestado,
			String nomeVendedor, 
			Long idVendedor, String nomeCliente,
			Long idCliente, BigDecimal precoTotal, Long idPedido,
			int clienteReclamouEstado
			) {
		super();
		this.id = id;
		this.nomeVendedor = nomeVendedor;
		this.idVendedor = idVendedor;
		this.nomeCliente = nomeCliente;
		this.idCliente = idCliente;
		this.dataAdicionado = (Timestamp) dataAdicionado;
		this.currestado = currestado;
		
		if(currestado == 0) {
			this.observacao = "Em análise";
		} else if(currestado == 1) {
			this.observacao = "Em separação";
		} else if(currestado == 2) {
			this.observacao = "Em transporte";
		} else if(currestado == 3) {
			this.observacao = "Entregue";
		} else if(currestado == 4) {
			this.observacao = "Finalizado";
		} else if(currestado == 99) {
			this.observacao = "Cancelado";
		}
		
//		this.observacao 
		this.precoTotal = precoTotal;
		this.idPedido = idPedido;
//		this.clienteReclamouEstado = clienteReclamouEstado;
		if(clienteReclamouEstado == 1) {
			this.observacao = "Reclamação em análise";
		} else if(clienteReclamouEstado == 2) {
			this.observacao = "Reclamacao aceite";
		} else if(clienteReclamouEstado == 3) {
			this.observacao = "Substituto em separação";
		} else if(clienteReclamouEstado == 4) {
			this.observacao = "Substituto em transporte";
		} else if(clienteReclamouEstado == 5) {
			this.observacao = "Substituto entregue";
		} else if(clienteReclamouEstado == 6) {
			this.observacao = "Substituto finalizado";
		} else if(clienteReclamouEstado == 99) {
			this.observacao = "Cancelado pelo administrador";
		}
	}
	
	
	
//	public EstadoPedido(Long id, String nomeVendedor, 
//			Long idVendedor, String nomeCliente, 
//			Long idCliente, Date dataAdicionado,
//			String observacao,
//			Pedido pedido) {
//		super();
//		this.id = id;
//		this.nomeVendedor = nomeVendedor;
//		this.idVendedor = idVendedor;
//		this.nomeCliente = nomeCliente;
//		this.idCliente = idCliente;
//		this.dataAdicionado = (Timestamp) dataAdicionado;
//		this.observacao = observacao;
//	}




	public byte getIncludesPhoto() {
		return includesPhoto;
	}





	public void setIncludesPhoto(byte includesPhoto) {
		this.includesPhoto = includesPhoto;
	}





	public List<ObservacaoEstadoPedido> getObservacaoEstadoPedidos() {
		return observacaoEstadoPedidos;
	}





	public void setObservacaoEstadoPedidos(List<ObservacaoEstadoPedido> observacaoEstadoPedidos) {
		this.observacaoEstadoPedidos = observacaoEstadoPedidos;
	}





	public int getCurrestado() {
		return currestado;
	}





	public void setCurrestado(int currestado) {
		this.currestado = currestado;
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





	public int getClienteReclamouEstado() {
		return clienteReclamouEstado;
	}





	public void setClienteReclamouEstado(int clienteReclamouEstado) {
		this.clienteReclamouEstado = clienteReclamouEstado;
	}





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



	public String getObservacao() {
		return this.observacao;
	}

	public void setObservacao(String observacao) {
		this.observacao = observacao;
	}

	public Pedido getPedido() {
		return this.pedido;
	}

	public void setPedido(Pedido pedido) {
		this.pedido = pedido;
	}

	public User getUser() {
		return this.user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public String getNomeVendedor() {
		return nomeVendedor;
	}

	public void setNomeVendedor(String nomeVendedor) {
		this.nomeVendedor = nomeVendedor;
	}

	public String getNomeCliente() {
		return nomeCliente;
	}

	public void setNomeCliente(String nomeCliente) {
		this.nomeCliente = nomeCliente;
	}

	public Long getIdVendedor() {
		return idVendedor;
	}

	public void setIdVendedor(Long idVendedor) {
		this.idVendedor = idVendedor;
	}

	public Long getIdCliente() {
		return idCliente;
	}

	public void setIdCliente(Long idCliente) {
		this.idCliente = idCliente;
	}




	public BigDecimal getPrecoTotal() {
		return precoTotal;
	}




	public void setPrecoTotal(BigDecimal precoTotal) {
		this.precoTotal = precoTotal;
	}





	public Long getIdPedido() {
		return idPedido;
	}





	public void setIdPedido(Long idPedido) {
		this.idPedido = idPedido;
	}





	public Long getIdProduto() {
		return idProduto;
	}





	public void setIdProduto(Long idProduto) {
		this.idProduto = idProduto;
	}

	
	
	
}