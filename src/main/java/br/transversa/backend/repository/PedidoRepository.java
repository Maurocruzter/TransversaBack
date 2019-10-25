package br.transversa.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import br.transversa.backend.model.Pedido;

@Repository
public interface PedidoRepository extends JpaRepository<Pedido, Long> {
	
	@Modifying
	@Query("UPDATE Pedido p set p.isAprovado = :isAprovado where p.id = :id")
	int setPedidoAprovado(Byte isAprovado, Long id);

	@Modifying
	@Query("UPDATE Pedido p set p.isTransporte = :isTransporte where p.id = :id")
	int setPedidoTransporte(byte isTransporte, Long id);

	@Modifying
	@Query("UPDATE Pedido p set p.isFinalizado = :isFinalizado where p.id = :id")
	int setPedidoFinalizado(byte isFinalizado, Long id);

	@Modifying
	@Query("UPDATE Pedido p set p.isEntregue = :isEntregue where p.id = :id")
	int setPedidoEntregue(byte isEntregue, Long id);

	@Modifying
	@Query("UPDATE Pedido p set p.isCancelado = :isCancelado where p.id = :id")
	int setPedidoCancelado(byte isCancelado, Long id);

	@Modifying
	@Query("UPDATE Pedido p set p.clienteReclamouEstado = :clienteReclamouEstado where p.id = :id")
	int setClienteReclamouEstado(int clienteReclamouEstado, Long id);
	
}

