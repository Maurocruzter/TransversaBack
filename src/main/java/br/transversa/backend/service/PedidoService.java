package br.transversa.backend.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.transversa.backend.model.EstadoPedido;
import br.transversa.backend.model.Pedido;
import br.transversa.backend.model.PedidosHasProduto;
import br.transversa.backend.repository.EstadoPedidoRepository;
import br.transversa.backend.repository.PedidoHasProdutoRepository;
import br.transversa.backend.repository.PedidoRepository;

@Service
@Transactional
public class PedidoService {
	
	@Autowired
    PedidoRepository pedidoRepository;
	
	@Autowired
	PedidoHasProdutoRepository pedidoHasProdutoRepository;
	
	@Autowired
	EstadoPedidoRepository estadoPedidoRepository;
	
	public void createBulk(List<PedidosHasProduto> listPedidoHasProduto) {
		pedidoHasProdutoRepository.saveAll(listPedidoHasProduto);
	}
	
	public Pedido createOrUpdatePedido(Pedido pedido) {
		return pedidoRepository.save(pedido);
	}
	
	public EstadoPedido createEstadoPedido(EstadoPedido estadoPedido) {
		return estadoPedidoRepository.save(estadoPedido);
	}
	
	public Page<EstadoPedido> listarPedidosCliente(Long idCliente, int pageNumber) {
//		Pageable sortedByPriceDesc = 
//				  PageRequest.of(0, 1, Sort.by("id").descending());
//		Pageable pageable = PageRequest.of(pageNumber, 1);
		
		Pageable pageable = PageRequest.of(pageNumber, 20);
		return estadoPedidoRepository.findPedidosCliente(idCliente, pageable);
	}
	
	public Page<EstadoPedido> listarPedidos(int pageNumber) {
//		Pageable sortedByPriceDesc = 
//				  PageRequest.of(0, 1, Sort.by("id").descending());
//		Pageable pageable = PageRequest.of(pageNumber, 1);
		
		Pageable pageable = PageRequest.of(pageNumber, 20);
		return estadoPedidoRepository.findPedidos(pageable);
	}
	
	public Page<EstadoPedido> listarPedidosVendedor(Long idVendedor,int pageNumber) {
	
		Pageable pageable = PageRequest.of(pageNumber, 20);
		return estadoPedidoRepository.findPedidosVendedor(idVendedor, pageable);
	}
	
	public Page<EstadoPedido> listarPedidosPendentesVCliente(Long idCliente, int pageNumber) {
		Pageable pageable = PageRequest.of(pageNumber, 20);
		return estadoPedidoRepository.listarPedidosPendentesVCliente(idCliente, pageable);
	}
		
	public Page<EstadoPedido> listarPedidosPendentes(int pageNumber) {
		Pageable sortedByPriceDesc = 
				  PageRequest.of(0, 1, Sort.by("id").descending());
//		Pageable pageable = PageRequest.of(pageNumber, 1);
		return estadoPedidoRepository.findPedidosPendentes(sortedByPriceDesc);
	}
	
	public Page<EstadoPedido> listarPedidosNaoAprovadosVFuncionario(int pageNumber) {
//		Pageable sortedByPriceDesc = 
//				  PageRequest.of(0, 20, Sort.by("id").descending());
////		Pageable pageable = PageRequest.of(pageNumber, 1);
		
		Pageable pageable = PageRequest.of(pageNumber, 20);
		return estadoPedidoRepository.findPedidosNaoAprovadosVFuncionario(pageable);
	}
	

	public List<EstadoPedido> findObservacoesPedidoByIdPedidoAndIdCliente(Long idCliente, Long idPedido) {
		
		return estadoPedidoRepository.findObservacoesPedidoByIdPedidoAndIdCliente(idCliente,idPedido);
	}
	
	public List<EstadoPedido> findObservacoesPedidoByIdPedido(Long idPedido) {
		
		return estadoPedidoRepository.findObservacoesPedidoByIdPedido(idPedido);
	}
	
	public List<PedidosHasProduto> findPedidoDetalhesByIdPedidoAndIdCliente(Long idCliente, Long idPedido) {
		
		return pedidoHasProdutoRepository.findPedidoDetalhesByIdPedidoAndIdCliente(idPedido);
	}
	
	public List<PedidosHasProduto> findPedidoDetalhesByIdPedidoAndIdClienteReturnUUID(Long idCliente, Long idPedido) {
		
		return pedidoHasProdutoRepository.findPedidoDetalhesByIdPedidoAndIdClienteReturnUUID(idPedido);
	}
	
	public List<PedidosHasProduto> findPedidoDetalhesByIdPedidoAndIdFuncionarioReturnUUID(Long idFuncionario, Long idPedido) {
		
		return pedidoHasProdutoRepository.findPedidoDetalhesByIdPedidoAndIdFuncionarioReturnUUID(idFuncionario, idPedido);
	}
	
	public List<PedidosHasProduto> findPedidoDetalhesByIdPedidoReturnUUID(Long idPedido) {
		return pedidoHasProdutoRepository.findPedidoDetalhesByIdPedidoReturnUUID(idPedido);
	}
	
	
//	public Page<EstadoPedido> findPedidoByIdPedidoAndIdCliente(Long idCliente, Long idPedido) {
//		Pageable sortedByPriceDesc = 
//				  PageRequest.of(0, 1, Sort.by("id").descending());
//		return estadoPedidoRepository.findPedidoByIdPedidoAndIdCliente(idCliente,idPedido, sortedByPriceDesc);
//	}
	
//	public void deleteCarrinhoHasProdutoByIdCarrinho(Carrinho carrinho) {
//
//		return 
//	}
	

}
