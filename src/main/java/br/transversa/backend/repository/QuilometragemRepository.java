package br.transversa.backend.repository;



import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import br.transversa.backend.model.Quilometragem;




@Repository
public interface QuilometragemRepository extends JpaRepository<Quilometragem, Long> {

	@Query("SELECT new Quilometragem(max(q.automovel.id), max(q.automovel.matricula), max(q.quilometragemAtual)) from Quilometragem q "
			+ "GROUP BY q.automovel.id")
	Page<Quilometragem> findAutomovelByPage(Pageable pageable);

//	@Query(value = "SELECT new EstadoPedido(max(ep.id), max(ep.dataAdicionado), max(ep.currestado), "
//			+ "ep.pedido.user1.nome, ep.pedido.user1.id, "
//			+ "ep.pedido.user2.nome, ep.pedido.user2.id,  ep.pedido.totalPedido, "
//			+ " ep.pedido.id, ep.pedido.clienteReclamouEstado)  FROM EstadoPedido ep  "
//			+ "WHERE ep.pedido.user2.id = :idCliente GROUP BY ep.pedido.id")
	
}
