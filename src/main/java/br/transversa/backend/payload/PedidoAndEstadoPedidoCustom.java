package br.transversa.backend.payload;

import java.util.List;

import br.transversa.backend.model.EstadoPedido;
import br.transversa.backend.model.ObservacaoEstadoPedido;
import br.transversa.backend.model.PedidosHasProduto;

public class PedidoAndEstadoPedidoCustom {

	private List<PedidosHasProduto> pedidosProdutosList;
	
	private List<EstadoPedido> estadoPedidoList;
	
	private List<ObservacaoEstadoPedido> observacaoEstadoPedidoList;
	
	
	public PedidoAndEstadoPedidoCustom(List<PedidosHasProduto> pedidosProdutosList,
			List<EstadoPedido> estadoPedidoList, List<ObservacaoEstadoPedido> observacaoEstadoPedidoList) {
		super();
		this.pedidosProdutosList = pedidosProdutosList;
		this.estadoPedidoList = estadoPedidoList;
		this.observacaoEstadoPedidoList = observacaoEstadoPedidoList;
	}

	public List<PedidosHasProduto> getPedidosProdutosList() {
		return pedidosProdutosList;
	}

	public void setPedidosProdutosList(List<PedidosHasProduto> pedidosProdutosList) {
		this.pedidosProdutosList = pedidosProdutosList;
	}

	public List<EstadoPedido> getEstadoPedidoList() {
		return estadoPedidoList;
	}

	public void setEstadoPedidoList(List<EstadoPedido> estadoPedidoList) {
		this.estadoPedidoList = estadoPedidoList;
	}

	public List<ObservacaoEstadoPedido> getObservacaoEstadoPedidoList() {
		return observacaoEstadoPedidoList;
	}

	public void setObservacaoEstadoPedidoList(List<ObservacaoEstadoPedido> observacaoEstadoPedidoList) {
		this.observacaoEstadoPedidoList = observacaoEstadoPedidoList;
	}


	
	
	
	
}
