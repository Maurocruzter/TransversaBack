package br.transversa.backend.repository;

import java.util.Date;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import br.transversa.backend.model.Produto;
import br.transversa.backend.model.Promocoes;

@Repository
public interface PromocoesRepository extends JpaRepository<Promocoes, Long> {

	
	@Query(value = "SELECT new Promocoes(max(p.id), max(p.dataInicio), max(p.dataFim), max(p.compraMinima), max(p.desconto), max(p.produto.id), "
			+ "max(p.produto.preco), max(p.produto.nome) , max(p.produto.uuid) )  FROM Promocoes p "
			+ "WHERE p.dataInicio <= :dataAtual AND p.dataFim >= :dataAtual "
			+ "GROUP BY p.produto.id")
	Page<Promocoes>findPromocoesByPage(Pageable pageable, Date dataAtual);
	
	@Query(value = "SELECT new Promocoes(p.id, p.dataInicio, p.dataFim, p.compraMinima, p.desconto, p.produto.id, "
			+ "p.produto.preco, p.produto.nome , p.produto.uuid )  FROM Promocoes p "
			+ "WHERE p.produto.id = :produtoId ORDER BY p.id DESC")
	Page<Promocoes> findPromocoesByProdutoId(Pageable pageable, Long produtoId);
	
	@Query(value = "SELECT new Promocoes(p.id, p.dataInicio, p.dataFim, p.compraMinima, p.desconto, p.produto.id)  FROM Promocoes p WHERE p.produto.id = :idProduto "
			+ "AND p.dataInicio<= :dataInicio AND p.dataFim >= :dataFim")
	Optional<Promocoes> findIfPromocaoNoOverlap(Long idProduto, Date dataInicio, Date dataFim);
	
//	@Query("SELECT new Promocoes(p.id, p.produto.nome, p.produto.preco, p.produto.uuid) from Promocoes p GROUP BY p.produto.id")
//    Page<Promocoes> findProdutosByPage(Pageable pageable);
}

 