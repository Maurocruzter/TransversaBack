package br.transversa.backend.service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import br.transversa.backend.model.ObservacaoEstadoPedido;
import br.transversa.backend.model.Produto;
import br.transversa.backend.model.Promocoes;
import br.transversa.backend.repository.ObservacaoEstadoPedidoRepository;
import br.transversa.backend.repository.ProdutoRepository;
import br.transversa.backend.repository.PromocoesRepository;

@Service
public class ProdutoService {
	
	@Autowired
	ProdutoRepository produtoRepository;
	
	@Autowired
	ObservacaoEstadoPedidoRepository observacaoEstadoPedidoRepository;
	
	
	public Produto save(Produto produto) {
		return produtoRepository.save(produto);
	}
	
	public ObservacaoEstadoPedido carregarReclamacaoImagem(Long id) {
		return observacaoEstadoPedidoRepository.findObservacaoEstadoPedidoById(id);
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
	
	public Page<Promocoes> findAllProdutoByPageRetrieveOnlyId(int pageNumber) {
		Pageable pageable = PageRequest.of(pageNumber, 20);
		return produtoRepository.findProdutoAllRetrieveOnlyId(pageable);
	}
	
	
	
	
	public List<Produto> findAllProdutos() {
		return produtoRepository.findAllProdutos();
	}
	
	
	public Page<Produto> findPesquisaPrecoSearchNome(String nome, int pageNumber) {
		Pageable pageable = PageRequest.of(pageNumber, 20);
//		System.out.println(nome);
		return produtoRepository.findProdutoByNome(nome, pageable);
	}
	
	public Produto findProdutoById(Long id) {


		return produtoRepository.findProdutoById(id);
	}

    
}
