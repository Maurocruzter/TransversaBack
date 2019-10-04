package br.transversa.backend.model;

import java.io.Serializable;
import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonBackReference;

import java.math.BigDecimal;
import java.sql.Timestamp;


/**
 * The persistent class for the vendas_has_produtos database table.
 * 
 */
@Entity
@Table(name="vendas_has_produtos")
@NamedQuery(name="VendasHasProduto.findAll", query="SELECT v FROM VendasHasProduto v")
public class VendasHasProduto implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="id_venda_produto")
    private Long id;

	@Column(name="data_adicionado")
	private Timestamp dataAdicionado;

	private BigDecimal preco;

	private int quantidade;

	//bi-directional many-to-one association to Produto
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="produtos_id")
	private Produto produto;

	//bi-directional many-to-one association to Venda
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="vendas_id")
	private Venda venda;

	public VendasHasProduto() {
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

	public Produto getProduto() {
		return this.produto;
	}

	public void setProduto(Produto produto) {
		this.produto = produto;
	}

	public Venda getVenda() {
		return this.venda;
	}

	public void setVenda(Venda venda) {
		this.venda = venda;
	}

}