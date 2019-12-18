package br.transversa.backend.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.transversa.backend.model.EntregadorPedido;
import br.transversa.backend.model.EstadoPedido;
import br.transversa.backend.model.ObservacaoEstadoPedido;
import br.transversa.backend.model.Pedido;
import br.transversa.backend.model.PedidosHasProduto;
import br.transversa.backend.model.StockPromocao;
import br.transversa.backend.repository.EntregadorPedidoRepository;
import br.transversa.backend.repository.EstadoPedidoRepository;
import br.transversa.backend.repository.ObservacaoEstadoPedidoRepository;
import br.transversa.backend.repository.PedidoHasProdutoRepository;
import br.transversa.backend.repository.PedidoRepository;
import br.transversa.backend.repository.custom.CustomPedidoRepository;

@Service
@Transactional
public class PedidoService {
	
	@Autowired
    PedidoRepository pedidoRepository;
	
	@Autowired
	PedidoHasProdutoRepository pedidoHasProdutoRepository;
	
	@Autowired
	EstadoPedidoRepository estadoPedidoRepository;
	
	@Autowired
	ObservacaoEstadoPedidoRepository observacaoEstadoPedidoRepository;
	
	@Autowired
	EntregadorPedidoRepository entregadorPedidoRepository;
	
	@Autowired
	CustomPedidoRepository customPedidoRepository;
	
	
	
	public void createBulk(List<PedidosHasProduto> listPedidoHasProduto) {
		pedidoHasProdutoRepository.saveAll(listPedidoHasProduto);
	}
	
	public Pedido createOrUpdatePedido(Pedido pedido) {
		return pedidoRepository.save(pedido);
	}
	
	public EstadoPedido createEstadoPedido(EstadoPedido estadoPedido) {
		return estadoPedidoRepository.save(estadoPedido);
	}
	
	public void saveEntregadorPedido(EntregadorPedido entregadorPedido) {
		entregadorPedidoRepository.save(entregadorPedido);
	}
	
	public Page<EstadoPedido> listarPedidosCliente(Long idCliente, int pageNumber) {
//		Pageable sortedByPriceDesc = 
//				  PageRequest.of(0, 1, Sort.by("id").descending());
//		Pageable pageable = PageRequest.of(pageNumber, 1);
		
		Pageable pageable = PageRequest.of(pageNumber, 20);
		return estadoPedidoRepository.findPedidosCliente(idCliente, pageable);
	}
	
	public Page<EstadoPedido> listarPedidosEntregador(Long idEntregador, int pageNumber) {
		
		Pageable pageable = PageRequest.of(pageNumber, 20);
		return entregadorPedidoRepository.findPedidosEntregador(idEntregador, pageable);
	}
	
	public Page<EstadoPedido> listarPedidos(int pageNumber) {
//		Pageable sortedByPriceDesc = 
//				  PageRequest.of(0, 1, Sort.by("id").descending());
//		Pageable pageable = PageRequest.of(pageNumber, 1);
		
		Pageable pageable = PageRequest.of(pageNumber, 20);
		return estadoPedidoRepository.findPedidos(pageable);
	}
	
	
	public Page<EstadoPedido> findPedidoFilter(String razaoSocial, String precoMin, String precoMax,
			String cpf, String cnpj, Long idPedido, int pageNumber, int perfil, Long idUser, String dataInicio, String dataFim,
			int estadoPedido) {
		Pageable pageable = PageRequest.of(pageNumber, 20);
		return customPedidoRepository.findPedidoByFields(razaoSocial, precoMin, precoMax, cpf, cnpj, idPedido, pageable, 
				perfil, idUser, dataInicio, dataFim, estadoPedido);
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
	

	public List<ObservacaoEstadoPedido> findObservacaoEstadoPedidoByIdPedido(Long idPedido){
		return observacaoEstadoPedidoRepository.findObservacaoEstadoPedidoByIdPedido(idPedido);
		
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
	
	public List<PedidosHasProduto> findPedidoDetalhesByIdPedido(Long idPedido) {
		
		return pedidoHasProdutoRepository.findPedidoDetalhesByIdPedido(idPedido);
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

	public int setPedidoAprovado(byte isAprovado, Long id) {
		return pedidoRepository.setPedidoAprovado(isAprovado, id);
		
	}
	
	public int setPedidoTransporte(byte isTransporte, Long id) {
		return pedidoRepository.setPedidoTransporte(isTransporte, id);
		
	}

	public int setPedidoFinalizado(byte isFinalizado, Long id) {
		return pedidoRepository.setPedidoFinalizado(isFinalizado, id);
		
	}

	public int setPedidoEntregue(byte isEntregue, Long id) {
		return pedidoRepository.setPedidoEntregue(isEntregue, id);
		
	}

	public int setPedidoCancelado(byte isCancelado, Long id) {
		return pedidoRepository.setPedidoCancelado(isCancelado, id);
		
	}

	public int setClienteReclamouEstado(int clienteReclamouEstado, Long id) {
		return pedidoRepository.setClienteReclamouEstado(clienteReclamouEstado, id);
	}
	
	public void saveObservacaoEstadoPedido(ObservacaoEstadoPedido observacaoEstadoPedido) {
		observacaoEstadoPedidoRepository.save(observacaoEstadoPedido);
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
