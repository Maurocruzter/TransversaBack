package br.transversa.backend.model;

import java.io.Serializable;
import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import java.sql.Timestamp;
import java.util.List;


/**
 * The persistent class for the vendas database table.
 * 
 */
@Entity
@Table(name="vendas")
@NamedQuery(name="Venda.findAll", query="SELECT v FROM Venda v")
public class Venda implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="id_venda")
    private Long id;

	@Column(name="data_adicionado")
	private Timestamp dataAdicionado;

	//bi-directional many-to-one association to User
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="users_id_vendedor")
	private User user1;

	//bi-directional many-to-one association to User
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="users_id_cliente")
	private User user2;

	//bi-directional many-to-one association to VendasHasProduto
	@OneToMany(mappedBy="venda")
	private List<VendasHasProduto> vendasHasProdutos;

	public Venda() {
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

	public List<VendasHasProduto> getVendasHasProdutos() {
		return this.vendasHasProdutos;
	}

	public void setVendasHasProdutos(List<VendasHasProduto> vendasHasProdutos) {
		this.vendasHasProdutos = vendasHasProdutos;
	}

	public VendasHasProduto addVendasHasProduto(VendasHasProduto vendasHasProduto) {
		getVendasHasProdutos().add(vendasHasProduto);
		vendasHasProduto.setVenda(this);

		return vendasHasProduto;
	}

	public VendasHasProduto removeVendasHasProduto(VendasHasProduto vendasHasProduto) {
		getVendasHasProdutos().remove(vendasHasProduto);
		vendasHasProduto.setVenda(null);

		return vendasHasProduto;
	}
	
	

}