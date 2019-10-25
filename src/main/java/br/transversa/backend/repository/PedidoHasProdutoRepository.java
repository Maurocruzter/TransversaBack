package br.transversa.backend.repository;



import java.util.List;

import javax.persistence.Transient;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import br.transversa.backend.model.PedidosHasProduto;




@Repository
public interface PedidoHasProdutoRepository extends JpaRepository<PedidosHasProduto, Long> {
	
	@Query(value = "SELECT new PedidosHasProduto(php.id, php.preco, php.produto.nome, php.quantidade) from PedidosHasProduto php WHERE php.pedido.id = :idPedido")
	List<PedidosHasProduto> findPedidoDetalhesByIdPedidoAndIdCliente(Long idPedido);
	
	@Query(value = "SELECT new PedidosHasProduto(php.id, php.preco, php.produto.nome, php.quantidade, php.produto.uuid,"
			+ "php.pedido.isAprovado, php.pedido.isFinalizado, php.pedido.isTransporte, php.pedido.isCancelado, php.pedido.isEntregue) "
			+ "from PedidosHasProduto php WHERE php.pedido.id = :idPedido")
	List<PedidosHasProduto> findPedidoDetalhesByIdPedidoAndIdClienteReturnUUID(Long idPedido);
	
	@Query(value = "SELECT new PedidosHasProduto(php.id, php.preco, php.produto.nome, php.quantidade, php.produto.uuid) "
			+ "from PedidosHasProduto php WHERE php.pedido.id = :idPedido AND php.pedido.user2.user2.id = :idFuncionario")
	List<PedidosHasProduto> findPedidoDetalhesByIdPedidoAndIdFuncionarioReturnUUID(Long idFuncionario, Long idPedido);
	
	@Query(value = "SELECT new PedidosHasProduto(php.id, php.preco, php.produto.nome, php.quantidade, php.produto.uuid,"
			+ "php.pedido.isAprovado, php.pedido.isFinalizado, php.pedido.isTransporte, php.pedido.isCancelado, php.pedido.isEntregue) "
			+ "from PedidosHasProduto php WHERE php.pedido.id = :idPedido")
	List<PedidosHasProduto> findPedidoDetalhesByIdPedidoReturnUUID(Long idPedido);
	 
}
