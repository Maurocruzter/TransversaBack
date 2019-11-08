package br.transversa.backend.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import br.transversa.backend.model.Promocoes;
import br.transversa.backend.model.StockPromocao;
import br.transversa.backend.repository.StockPromocaoRepository;

@Service
public class StockPromocaoService {
	
	@Autowired
	StockPromocaoRepository stockPromocaoRepository;
	
	
	public void save(StockPromocao stockPromocao) {
		stockPromocaoRepository.save(stockPromocao);
	}
	
	public Page<StockPromocao> findAllProdutoByPageRetrieveOnlyId(int pageNumber) {
		Pageable pageable = PageRequest.of(pageNumber, 20);
		return stockPromocaoRepository.findProdutoAllRetrieveOnlyId(pageable);
	}

	public StockPromocao findPromocoesByProdutoId(int pageNumber ,Long produtoId) {
		Pageable pageable = PageRequest.of(pageNumber, 1);
		Page<StockPromocao> aux = stockPromocaoRepository.findStockPromocoesByProdutoId(pageable, produtoId);
		if(aux.isEmpty()) {
			return null;
		}
		System.out.println("Show me this please");
		System.out.println(aux.getContent().get(0).getQuantidadeEmPromocao());
		return aux.getContent().get(0);
	}


    
}
