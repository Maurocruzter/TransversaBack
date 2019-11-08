package br.transversa.backend.repository;



import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import br.transversa.backend.model.Automovel;
import br.transversa.backend.model.Stock;




@Repository
public interface StockRepository extends JpaRepository<Stock, Long> {

	
	@Query(value = "SELECT new Stock(s.id) FROM Stock s WHERE s.produto.id = :produtoId")
	List<Automovel> findAllAutomoveis();
	Stock findStockByProdutoId(Long produtoId);


}
