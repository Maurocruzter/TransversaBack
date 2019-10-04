package br.transversa.backend.payload;

import javax.validation.constraints.NotBlank;

public class PedidoObservacaoRequest {
    @NotBlank
    private String observacao;

	public String getObservacao() {
		return observacao;
	}

	public void setObservacao(String observacao) {
		this.observacao = observacao;
	}



    
    
}
