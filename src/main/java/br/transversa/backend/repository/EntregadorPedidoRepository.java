package br.transversa.backend.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import br.transversa.backend.model.EntregadorPedido;
import br.transversa.backend.model.EstadoPedido;

@Repository
public interface EntregadorPedidoRepository extends JpaRepository<EntregadorPedido, Long> {

//	@Query("SELECT new EstadoPedido(max(ep.id), max(ep.dataAdicionado), max(ep.currestado), " + 
//			" ep.pedido.user1.nome, ep.pedido.user1.id, " + 
//			" ep.pedido.user2.nome, ep.pedido.user2.id,  ep.pedido.totalPedido, " + 
//			" ep.pedido.id, ep.pedido.clienteReclamouEstado) from EstadoPedido ep "
//			+ "WHERE ep.pedido.id = (SELECT ex.estadoPedido.pedido.id FROM EntregadorPedido ex "
//			+ "WHERE ex.user.id = :idEntregador) GROUP BY ep.pedido.id")
	@Query("SELECT new EstadoPedido(max(ep.estadoPedido.id), max(ep.estadoPedido.dataAdicionado), max(ep.estadoPedido.currestado), " + 
			" ep.estadoPedido.pedido.user1.nome, ep.estadoPedido.pedido.user1.id, " + 
			" ep.estadoPedido.pedido.user2.nome, ep.estadoPedido.pedido.user2.id,  ep.estadoPedido.pedido.totalPedido, " + 
			" ep.estadoPedido.pedido.id, ep.estadoPedido.pedido.clienteReclamouEstado) from EntregadorPedido ep "
			+ "WHERE ep.user.id = :idEntregador  GROUP BY ep.estadoPedido.pedido.id")
	Page<EstadoPedido> findPedidosEntregador(Long idEntregador, Pageable pageable);

	
//	SELECT a
//	FROM A a
//	WHERE a.val = (SELECT b.someval 
//	               FROM B b 
//	               WHERE b.someotherval=3)
	
//	+ "WHERE ep.pedido.user2.id IN ()")
//	SELECT ProductName
//	  FROM Product 
//	 WHERE Id IN (SELECT ProductId 
//	                FROM OrderItem
//	               WHERE Quantity > 100)
}
