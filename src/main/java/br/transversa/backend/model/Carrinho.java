package br.transversa.backend.model;

import java.io.Serializable;
import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonInclude.Include;



import java.sql.Timestamp;
import java.util.List;


/**
 * The persistent class for the carrinhos database table.
 * 
 */
@Entity
@Table(name="carrinhos")
@NamedQuery(name="Carrinho.findAll", query="SELECT c FROM Carrinho c")
@JsonInclude(value = Include.NON_NULL)
public class Carrinho implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id_carrinho")
	private Long id;

	@Column(name="data_adicionado_carrinho")
	private Timestamp dataAdicionado;

	//bi-directional many-to-one association to User
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="users_id_vendedor")
	private User user1;

	//bi-directional many-to-one association to User
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="users_id_cliente")
	private User user2;

	//bi-directional many-to-one association to CarrinhosHasProduto
	@OneToMany(mappedBy="carrinho")
	private List<CarrinhosHasProduto> carrinhosHasProdutos;
	
	
	@Transient
	private Long vendedorId;
	@Transient
	private Long clienteId;

	public Carrinho() {
	}


	
	

	public Carrinho(Long id, Long vendedorId, Long clienteId) {
		super();
		this.id = id;
		this.vendedorId = vendedorId;
		this.clienteId = clienteId;
	}





	public Long getVendedorId() {
		return vendedorId;
	}





	public void setVendedorId(Long vendedorId) {
		this.vendedorId = vendedorId;
	}





	public Long getClienteId() {
		return clienteId;
	}





	public void setClienteId(Long clienteId) {
		this.clienteId = clienteId;
	}





	public Long getId() {
		return id;
	}



	public void setId(Long id) {
		this.id = id;
	}



	public Timestamp getDataAdicionado() {
		return this.dataAdicionado;
	}

	public void setDataAdicionado(Timestamp dataAdicionado) {
		this.dataAdicionado = dataAdicionado;
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

	public List<CarrinhosHasProduto> getCarrinhosHasProdutos() {
		return this.carrinhosHasProdutos;
	}

	public void setCarrinhosHasProdutos(List<CarrinhosHasProduto> carrinhosHasProdutos) {
		this.carrinhosHasProdutos = carrinhosHasProdutos;
	}

	public CarrinhosHasProduto addCarrinhosHasProduto(CarrinhosHasProduto carrinhosHasProduto) {
		getCarrinhosHasProdutos().add(carrinhosHasProduto);
		carrinhosHasProduto.setCarrinho(this);

		return carrinhosHasProduto;
	}

	public CarrinhosHasProduto removeCarrinhosHasProduto(CarrinhosHasProduto carrinhosHasProduto) {
		getCarrinhosHasProdutos().remove(carrinhosHasProduto);
		carrinhosHasProduto.setCarrinho(null);

		return carrinhosHasProduto;
	}

}