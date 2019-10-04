package br.transversa.backend.repository;



import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import br.transversa.backend.model.Carrinho;




@Repository
public interface CarrinhoRepository extends JpaRepository<Carrinho, Long> {
	@Query(value = "SELECT new Carrinho(c.id, c.user1.id, c.user2.id) FROM Carrinho c WHERE c.user1.id= :idVendedor AND c.user2.id= :idCliente")
	Optional<Carrinho> findCarrinhoByUser1AndUser2(Long idVendedor, Long idCliente);
	
}
