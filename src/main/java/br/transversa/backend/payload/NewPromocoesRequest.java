package br.transversa.backend.payload;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import br.transversa.backend.model.Produto;
import br.transversa.backend.model.User;

public class NewPromocoesRequest {

	private Long idProduto;


	private Date dataInicio;
	
	private Date dataFim;


	private int compraMinima;
	
	private BigDecimal desconto;
	
	private int quantidade;

	public Long getIdProduto() {
		return idProduto;
	}

	public void setIdProduto(Long idProduto) {
		this.idProduto = idProduto;
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

	public int getQuantidade() {
		return quantidade;
	}

	public void setQuantidade(int quantidade) {
		this.quantidade = quantidade;
	}
	
	
	
}
