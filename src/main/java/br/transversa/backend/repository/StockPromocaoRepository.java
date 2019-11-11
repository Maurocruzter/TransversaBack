package br.transversa.backend.repository;



import java.util.Date;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import br.transversa.backend.model.Promocoes;
import br.transversa.backend.model.StockPromocao;




@Repository
public interface StockPromocaoRepository extends JpaRepository<StockPromocao, Long> {

	@Query("select new StockPromocao(p.id) from Produto p")
	Page<StockPromocao> findProdutoAllRetrieveOnlyId(Pageable pageable);

	@Query(value = "SELECT new StockPromocao(p.id, p.promocoe.dataInicio, p.promocoe.dataFim, "
			+ "p.promocoe.compraMinima, p.promocoe.desconto, p.promocoe.stock.produto.id, "
			+ "p.promocoe.stock.produto.preco, p.promocoe.stock.produto.nome , "
			+ "p.promocoe.stock.produto.uuid, p.quantidadeEmPromocao, p.promocoe.stock.quantidade )  FROM StockPromocao p "
			+ "WHERE p.promocoe.stock.produto.id = :produtoId ORDER BY p.id DESC")
	Page<StockPromocao> findStockPromocoesByProdutoId(Pageable pageable, Long produtoId);
	
	
	@Query(value = "SELECT new StockPromocao(p.id, p.promocoe.dataInicio, p.promocoe.dataFim, "
			+ "p.promocoe.compraMinima, p.promocoe.desconto, p.promocoe.stock.produto.id, "
			+ "p.promocoe.stock.produto.preco, p.promocoe.stock.produto.nome , "
			+ "p.promocoe.stock.produto.uuid, p.quantidadeEmPromocao, p.promocoe.stock.quantidade )  FROM StockPromocao p "
			+ "WHERE p.promocoe.dataInicio <= :dataAtual AND p.promocoe.dataFim >= :dataAtual ")
	Page<StockPromocao> findStockPromocoesAtivasByPage(Pageable pageable, Date dataAtual);


}
