package br.transversa.backend.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import br.transversa.backend.model.Produto;
import br.transversa.backend.model.StockPromocao;

public interface FilterProdutoRepository {
	
	
	
	Page<StockPromocao> findProdutoByFields(String nome,
			String precoMin,
			String precoMaximo,
			Pageable pageable);
	
	
//	Page<User> findClienteByFields(String nome,
//			String sobrenome,
//			String cpf,
//			String cnpj,
//			Pageable pageable);

}
