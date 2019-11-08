package br.transversa.backend.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.transversa.backend.model.Automovel;
import br.transversa.backend.model.Stock;
import br.transversa.backend.repository.AutomovelRepository;
import br.transversa.backend.repository.StockRepository;

@Service
public class StockService {
	
	@Autowired
	StockRepository stockRepository;


	public void save(Stock stock) {
		// TODO Auto-generated method stub
		stockRepository.save(stock);
	}
	
	public Stock findStockByProdutoId(Long produtoId) {
		// TODO Auto-generated method stub
		return stockRepository.findStockByProdutoId(produtoId);
	}

    
}
