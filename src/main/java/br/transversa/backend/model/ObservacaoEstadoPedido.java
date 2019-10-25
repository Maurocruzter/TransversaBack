package br.transversa.backend.model;

import java.io.Serializable;

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
import javax.persistence.Transient;


/**
 * The persistent class for the pesquisaprecos database table.
 * 
 */
@Entity
@Table(name="observacao_estado_pedidos")
@NamedQuery(name="ObservacaoEstadoPedido.findAll", query="SELECT o FROM ObservacaoEstadoPedido o")
public class ObservacaoEstadoPedido implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id_observacao_estado_pedidos")
	private Long id;

	@Lob
	@Column(nullable=false)
	private byte[] data;
	
	//bi-directional many-to-one association to EstadoPedido
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="estado_pedidos_id")
	private EstadoPedido estadoPedido;

	@Transient
	private String observacao;

	public ObservacaoEstadoPedido() {
	}
	
	public ObservacaoEstadoPedido(byte[] data) {
		super();
		this.data = data;
	}

	public ObservacaoEstadoPedido(Long id, String observacao) {
		super();
		this.id = id;
		this.observacao = observacao;
	}




	public String getObservacao() {
		return observacao;
	}




	public void setObservacao(String observacao) {
		this.observacao = observacao;
	}




	public Long getId() {
		return id;
	}



	public void setId(Long id) {
		this.id = id;
	}



	public byte[] getData() {
		return data;
	}



	public void setData(byte[] data) {
		this.data = data;
	}



	public EstadoPedido getEstadoPedido() {
		return estadoPedido;
	}



	public void setEstadoPedido(EstadoPedido estadoPedido) {
		this.estadoPedido = estadoPedido;
	}
	
}


	