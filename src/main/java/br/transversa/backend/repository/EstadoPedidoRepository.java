package br.transversa.backend.repository;



import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import br.transversa.backend.model.EstadoPedido;




@Repository
public interface EstadoPedidoRepository extends JpaRepository<EstadoPedido, Long> {
	
	@Query(value = "SELECT new EstadoPedido(max(ep.id), max(ep.dataAdicionado), max(ep.currestado), "
			+ "ep.pedido.user1.nome, ep.pedido.user1.id, "
			+ "ep.pedido.user2.nome, ep.pedido.user2.id,  ep.pedido.totalPedido, "
			+ " ep.pedido.id, ep.pedido.clienteReclamouEstado)  FROM EstadoPedido ep  "
			+ "WHERE ep.pedido.user2.id = :idCliente GROUP BY ep.pedido.id")
	Page<EstadoPedido>findPedidosCliente(Long idCliente, Pageable pageable);
	
	@Query(value = "SELECT new EstadoPedido(max(ep.id), max(ep.dataAdicionado), max(ep.currestado), "
			+ "ep.pedido.user1.nome, ep.pedido.user1.id, "
			+ "ep.pedido.user2.nome, ep.pedido.user2.id,  ep.pedido.totalPedido, "
			+ " ep.pedido.id, ep.pedido.clienteReclamouEstado)  FROM EstadoPedido ep  "
			+ "GROUP BY ep.pedido.id")
	Page<EstadoPedido>findPedidos(Pageable pageable);
	
	@Query(value = "SELECT new EstadoPedido(max(ep.id), max(ep.dataAdicionado), max(ep.currestado), "
			+ "ep.pedido.user1.nome, ep.pedido.user1.id, "
			+ "ep.pedido.user2.nome, ep.pedido.user2.id,  ep.pedido.totalPedido, "
			+ " ep.pedido.id, ep.pedido.clienteReclamouEstado)  FROM EstadoPedido ep  "
			+ " WHERE pedido.user2.user2.id =:idVendedor GROUP BY ep.pedido.id")
	Page<EstadoPedido>findPedidosVendedor(Long idVendedor, Pageable pageable);
	
	
	@Query(value = "SELECT new EstadoPedido(ep.id, ep.pedido.user1.nome, ep.pedido.user1.id, "
			+ "ep.pedido.user2.nome, ep.pedido.user2.id, ep.dataAdicionado, ep.pedido.totalPedido, "
			+ "ep.observacao, ep.pedido.id) FROM EstadoPedido ep  WHERE ep.pedido.isFinalizado = 0")
	Page<EstadoPedido>findPedidosPendentes(Pageable pageable);
	
	
	@Query(value = "SELECT new EstadoPedido(max(ep.id), max(ep.dataAdicionado), max(ep.currestado), "
			+ "ep.pedido.user1.nome, ep.pedido.user1.id, "
			+ "ep.pedido.user2.nome, ep.pedido.user2.id,  ep.pedido.totalPedido, "
			+ " ep.pedido.id, ep.pedido.clienteReclamouEstado) FROM EstadoPedido ep "
			+ " WHERE ep.pedido.isAprovado = 0 GROUP BY ep.pedido.id")
	Page<EstadoPedido>findPedidosNaoAprovadosVFuncionario(Pageable pageable);
	
	@Query(value = "SELECT new EstadoPedido(max(ep.id), max(ep.dataAdicionado), max(ep.currestado), "
			+ "ep.pedido.user1.nome, ep.pedido.user1.id, "
			+ "ep.pedido.user2.nome, ep.pedido.user2.id,  ep.pedido.totalPedido, "
			+ " ep.pedido.id, ep.pedido.clienteReclamouEstado)  FROM EstadoPedido ep  "
			+ "WHERE ep.pedido.user2.id = :idCliente AND ep.pedido.isAprovado =0 GROUP BY ep.pedido.id")
	Page<EstadoPedido>listarPedidosPendentesVCliente(Long idCliente, Pageable pageable);
	
	@Query(value = "SELECT new EstadoPedido(ep.id, ep.pedido.user1.nome, ep.pedido.user1.id, "
			+ "ep.pedido.user2.nome, ep.pedido.user2.id, ep.dataAdicionado, "
			+ "ep.observacao, ep.pedido.clienteReclamouEstado, ep.includesPhoto) FROM EstadoPedido ep  "
			+ "WHERE ep.pedido.id = :idPedido AND ep.pedido.user2.id = :idCliente")
	List<EstadoPedido>findObservacoesPedidoByIdPedidoAndIdCliente(Long idCliente, Long idPedido);
	
	
	@Query(value = "SELECT new EstadoPedido(ep.id, ep.pedido.user1.nome, ep.pedido.user1.id, "
			+ "ep.pedido.user2.nome, ep.pedido.user2.id, ep.dataAdicionado, "
			+ "ep.observacao, ep.pedido.clienteReclamouEstado, ep.includesPhoto) FROM EstadoPedido ep  "
			+ "WHERE ep.pedido.id = :idPedido")
	List<EstadoPedido>findObservacoesPedidoByIdPedido(Long idPedido);
	
	
//	@Query(value = "SELECT new EstadoPedido(ep.id, ep.pedido.user1.nome, ep.pedido.user1.id, "
//			+ "ep.pedido.user2.nome, ep.pedido.user2.id, ep.dataAdicionado, "
//			+ "ep.observacao) FROM EstadoPedido ep  "
//			+ "WHERE ep.pedido.id = :idPedido AND ep.pedido.user2.id = :idCliente")
//	Page<EstadoPedido> findPedidoByIdPedidoAndIdCliente(Long idCliente, Long idPedido, Pageable pageable);
//	List<EstadoPedido>findByPedido(Pedido pedido);

	
	
	
//	public void nothing() {
//		EstadoPedido estadoPedido = new EstadoPedido();
//		estadoPedido.getPedido().getUser2().getId()
//	}
}
