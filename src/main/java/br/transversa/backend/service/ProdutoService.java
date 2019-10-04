package br.transversa.backend.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import br.transversa.backend.model.Produto;
import br.transversa.backend.repository.ProdutoRepository;

@Service
public class ProdutoService {
	
	@Autowired
	ProdutoRepository produtoRepository;
	
	
	public Produto save(Produto produto) {
		return produtoRepository.save(produto);
	}
	

	public Produto findPesquisaPrecoByUuid(String uuid) {
		return produtoRepository.findProdutoByUuid(uuid);
	}
	
	public Produto findPesquisaPrecoById(Long id) {
		return produtoRepository.findProdutoById(id);
	}
	
	public Optional<Produto> findProdutoPrecoById(Long id) {
		return produtoRepository.findProdutoPrecoById(id);
	}
	

	public Page<Produto> findAllProdutoByPage(int pageNumber) {
		Pageable pageable = PageRequest.of(pageNumber, 20);
		return produtoRepository.findProdutoAll(pageable);
	}
	
	public List<Produto> findAllProdutos() {
		return produtoRepository.findAllProdutos();
	}
	
	
	public Page<Produto> findPesquisaPrecoSearchNome(String nome, int pageNumber) {
		Pageable pageable = PageRequest.of(pageNumber, 20);
//		System.out.println(nome);
		return produtoRepository.findProdutoByNome(nome, pageable);
	}

    
}
