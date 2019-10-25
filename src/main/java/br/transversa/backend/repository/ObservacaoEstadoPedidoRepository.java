package br.transversa.backend.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import br.transversa.backend.model.ObservacaoEstadoPedido;
import br.transversa.backend.model.Produto;

@Repository
public interface ObservacaoEstadoPedidoRepository extends JpaRepository<ObservacaoEstadoPedido, Long> {

	@Query(value = "SELECT new ObservacaoEstadoPedido(obsep.id, obsep.estadoPedido.observacao)  "
			+ "FROM ObservacaoEstadoPedido obsep WHERE obsep.estadoPedido.pedido.id = :idPedido")
	List<ObservacaoEstadoPedido> findObservacaoEstadoPedidoByIdPedido(Long idPedido);
	
	@Query(value = "select new ObservacaoEstadoPedido(obsep.data) from ObservacaoEstadoPedido obsep "
			+ "WHERE obsep.estadoPedido.id = :id")
	ObservacaoEstadoPedido findObservacaoEstadoPedidoById(Long id);

	
}
