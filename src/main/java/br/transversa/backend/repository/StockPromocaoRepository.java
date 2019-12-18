package br.transversa.backend.repository;



import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import br.transversa.backend.model.Produto;
import br.transversa.backend.model.StockPromocao;




@Repository
public interface StockPromocaoRepository extends JpaRepository<StockPromocao, Long> {

	@Query("select new StockPromocao(p.id) from Produto p")
	Page<StockPromocao> findProdutoAllByPageRetrieveOnlyId(Pageable pageable);
	
	@Query("select new Produto(p.id) from Produto p")
	List<Produto> findProdutoAllRetrieveOnlyId();

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

	
	@Query(value = "SELECT new StockPromocao(p.id, p.promocoe.dataInicio, p.promocoe.dataFim, "
			+ "p.promocoe.compraMinima, p.promocoe.desconto, p.promocoe.stock.produto.id, "
			+ "p.promocoe.stock.produto.preco, p.promocoe.stock.produto.nome , "
			+ "p.promocoe.stock.produto.uuid, p.quantidadeEmPromocao, p.promocoe.stock.quantidade )  FROM StockPromocao p "
			+ "WHERE p.promocoe.dataInicio <= :dataAtual AND p.promocoe.dataFim >= :dataAtual ")
	List<StockPromocao> findAllStockPromocoesAtivasVOffline(Date dataAtual);
	
	
	
	@Modifying
	@Query("UPDATE StockPromocao sp set sp.quantidadeEmPromocao = :quantidade "
			+ "WHERE sp.id = :idStock")
	void updateStockPromocao(int quantidade, Long idStock);
	
	
	@Query("select new StockPromocao(sp.id) from StockPromocao sp WHERE sp.promocoe.id = :idPromocao")
	StockPromocao findStockPromocaoRetrieveOnlyId(Long idPromocao);
	
	@Query("select new StockPromocao(sp.id) from StockPromocao sp WHERE sp.promocoe.stock.produto.id = :idProduto")
	StockPromocao findStockPromocaoByProdutoIdRetrieveOnlyId(Long idProduto);
	
	

}
