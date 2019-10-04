package br.transversa.backend.payload;

import javax.validation.constraints.NotBlank;

public class NovoPedidoRequest {
    @NotBlank
    private Long id;
    
    @NotBlank
    private int quantidade;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public int getQuantidade() {
		return quantidade;
	}

	public void setQuantidade(int quantidade) {
		this.quantidade = quantidade;
	}





    
    
}
