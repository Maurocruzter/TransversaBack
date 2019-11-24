package br.transversa.backend.repository;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Date;
import java.util.Optional;

import javax.persistence.Column;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import br.transversa.backend.model.Produto;
import br.transversa.backend.model.Promocoes;

@Repository
public interface PromocoesRepository extends JpaRepository<Promocoes, Long> {

	
	@Query(value = "SELECT new Promocoes(max(p.id), max(p.dataInicio), max(p.dataFim), max(p.compraMinima), max(p.desconto), max(p.stock.produto.id), "
			+ "max(p.stock.produto.preco), max(p.stock.produto.nome) , max(p.stock.produto.uuid) )  FROM Promocoes p "
			+ "WHERE p.dataInicio <= :dataAtual AND p.dataFim >= :dataAtual "
			+ "GROUP BY p.stock.produto.id")
	Page<Promocoes>findPromocoesByPage(Pageable pageable, Date dataAtual);
	
	@Query(value = "SELECT new Promocoes(p.id, p.dataInicio, p.dataFim, p.compraMinima, p.desconto, p.stock.produto.id, "
			+ "p.stock.produto.preco, p.stock.produto.nome , p.stock.produto.uuid )  FROM Promocoes p "
			+ "WHERE p.stock.produto.id = :produtoId ORDER BY p.id DESC")
	Page<Promocoes> findPromocoesByProdutoId(Pageable pageable, Long produtoId);
	
	@Query(value = "SELECT new Promocoes(p.id, p.dataInicio, p.dataFim, p.compraMinima, p.desconto, p.stock.produto.id)  FROM Promocoes p WHERE p.stock.produto.id = :idProduto "
			+ "AND p.dataInicio<= :dataInicio AND p.dataFim >= :dataFim")
	Optional<Promocoes> findIfPromocaoNoOverlap(Long idProduto, Date dataInicio, Date dataFim);
	
//	@Query("SELECT new Promocoes(p.id, p.produto.nome, p.produto.preco, p.produto.uuid) from Promocoes p GROUP BY p.produto.id")
//    Page<Promocoes> findProdutosByPage(Pageable pageable);
	
	
	@Modifying
	@Query("UPDATE Promocoes p set p.compraMinima = :compraMinima, "
			+ "p.dataAdicionado = :dataAdicionado, "
			+ "p.dataFim = :dataFim, "
			+ "p.dataInicio = :dataInicio, "
			+ "p.precoPromocao = :precoPromocao, "
			+ "p.desconto = :desconto WHERE p.id = :idPromocao")
	void updatePromocao(int compraMinima, Timestamp dataAdicionado, 
			Date dataFim, Date dataInicio, BigDecimal desconto, Long idPromocao, BigDecimal precoPromocao);
	
	
	@Query(value = "SELECT new Promocoes(p.id, p.stock.produto.preco)  FROM Promocoes p "
			+ "WHERE p.stock.produto.id = :produtoId")
	Promocoes findPromocaoAndPrecoIdByProdutoId(Long produtoId);

}

 