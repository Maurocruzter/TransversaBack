package br.transversa.backend.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.transversa.backend.model.Produto;
import br.transversa.backend.model.Promocoes;
import br.transversa.backend.model.StockPromocao;
import br.transversa.backend.repository.StockPromocaoRepository;

@Service
@Transactional
public class StockPromocaoService {
	
	@Autowired
	StockPromocaoRepository stockPromocaoRepository;
	
	
	public void save(StockPromocao stockPromocao) {
		stockPromocaoRepository.save(stockPromocao);
	}
	
	public void update(int quantidade, Long idStock) {
		
		System.out.println("qtd");
		System.out.println(quantidade);
		System.out.println("idStock");
		System.out.println(idStock);
		stockPromocaoRepository.updateStockPromocao(quantidade, idStock);
	}
	
	public Page<StockPromocao> findAllProdutoByPageRetrieveOnlyId(int pageNumber) {
		Pageable pageable = PageRequest.of(pageNumber, 20);
		return stockPromocaoRepository.findProdutoAllByPageRetrieveOnlyId(pageable);
	}
	
	public List<Produto> findProdutoAllRetrieveOnlyId() {
		return stockPromocaoRepository.findProdutoAllRetrieveOnlyId();
	}

	public StockPromocao findPromocoesByProdutoId(int pageNumber ,Long produtoId) {
		Pageable pageable = PageRequest.of(pageNumber, 1);
		Page<StockPromocao> aux = stockPromocaoRepository.findStockPromocoesByProdutoId(pageable, produtoId);
		if(aux.isEmpty()) {
			return null;
		}

		return aux.getContent().get(0);
	}
	
	
	public Long findStockPromocaoByPromocaoId(Long promocaoId) {
		

		return stockPromocaoRepository.findStockPromocaoRetrieveOnlyId(promocaoId).getId();
	}


    
}
