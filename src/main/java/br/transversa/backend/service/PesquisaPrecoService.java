package br.transversa.backend.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import br.transversa.backend.model.Pesquisapreco;
import br.transversa.backend.model.Produto;
import br.transversa.backend.repository.PesquisaPrecoRepository;

@Service
public class PesquisaPrecoService {
	
	@Autowired
	PesquisaPrecoRepository pesquisaPrecoRepository;
	
	
	public Pesquisapreco save(Pesquisapreco pesquisaPreco) {
		return pesquisaPrecoRepository.save(pesquisaPreco);
	}
	
	
	

	public Pesquisapreco findPesquisaPrecoByUuid(String uuid) {
		return pesquisaPrecoRepository.findPesquisaprecoByUuid(uuid);
	}
	
	public Pesquisapreco findPesquisaPrecoById(Long id) {
		return pesquisaPrecoRepository.findPesquisaPrecoById(id);
	}
	
//	public Optional<Produto> findProdutoPrecoById(Long id) {
//		return produtoRepository.findProdutoPrecoById(id);
//	}
	

	public Page<Pesquisapreco> findAllPesquisaPrecoByPage(int pageNumber) {
		Pageable pageable = PageRequest.of(pageNumber, 20);
		return pesquisaPrecoRepository.findPesquisaPrecoAll(pageable);
	}
	
//	public List<Produto> findAllProdutos() {
//		return produtoRepository.findAllProdutos();
//	}
	
	
	public Page<Pesquisapreco> findPesquisaPrecoSearchNome(String nome, int pageNumber) {
		Pageable pageable = PageRequest.of(pageNumber, 20);
//		System.out.println(nome);
		return pesquisaPrecoRepository.findPesquisaPrecoByNome(nome, pageable);
	}

    
}
