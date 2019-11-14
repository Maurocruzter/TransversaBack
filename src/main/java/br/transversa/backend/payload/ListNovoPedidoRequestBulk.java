package br.transversa.backend.payload;

import java.util.List;

import javax.validation.constraints.NotBlank;

public class ListNovoPedidoRequestBulk {
	
    List <ListNovoPedidoRequest> pedidosOfflineList;

	public List<ListNovoPedidoRequest> getPedidosOfflineList() {
		return pedidosOfflineList;
	}

	public void setPedidosOfflineList(List<ListNovoPedidoRequest> pedidosOfflineList) {
		this.pedidosOfflineList = pedidosOfflineList;
	}


    
    
    
    
}
