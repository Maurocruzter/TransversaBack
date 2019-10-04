package br.transversa.backend.payload;

import java.util.List;

import javax.validation.constraints.NotBlank;

public class ListNovoPedidoRequest {
	
    List <NovoPedidoRequest> produtosList;
    
    Long cliente;

	public List<NovoPedidoRequest> getProdutosList() {
		return produtosList;
	}

	public void setProdutosList(List<NovoPedidoRequest> produtosList) {
		this.produtosList = produtosList;
	}

	public Long getCliente() {
		return cliente;
	}

	public void setCliente(Long cliente) {
		this.cliente = cliente;
	}

    
    





    
    
}
