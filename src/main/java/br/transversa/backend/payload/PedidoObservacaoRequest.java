package br.transversa.backend.payload;

import javax.validation.constraints.NotBlank;

public class PedidoObservacaoRequest {
    @NotBlank
    private String observacao;
    
    @NotBlank
    private int estado;
    
    private Long entregadorId;

	public String getObservacao() {
		return observacao;
	}

	public void setObservacao(String observacao) {
		this.observacao = observacao;
	}

	public int getEstado() {
		return estado;
	}

	public void setEstado(int estado) {
		this.estado = estado;
	}

	public Long getEntregadorId() {
		return entregadorId;
	}

	public void setEntregadorId(Long entregadorId) {
		this.entregadorId = entregadorId;
	}


	
	

    
    
}
