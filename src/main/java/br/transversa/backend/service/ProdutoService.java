package br.transversa.backend.service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.transversa.backend.model.ObservacaoEstadoPedido;
import br.transversa.backend.model.Produto;
import br.transversa.backend.model.Promocoes;
import br.transversa.backend.model.StockPromocao;
import br.transversa.backend.repository.ObservacaoEstadoPedidoRepository;
import br.transversa.backend.repository.ProdutoRepository;
import br.transversa.backend.repository.PromocoesRepository;
import br.transversa.backend.repository.custom.CustomProdutoRepository;

@Service
@Transactional
public class ProdutoService {
	
	@Autowired
	ProdutoRepository produtoRepository;
	
	@Autowired
	CustomProdutoRepository customProdutoRepository;
	
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
		return produtoRepository.findProdutoByNome(nome, pageable);
	}
	
	public Page<StockPromocao> findProdutoFilter(String nome, String precoMin, String precoMax, int pageNumber) {
		Pageable pageable = PageRequest.of(pageNumber, 20);
		return customProdutoRepository.findProdutoByFields(nome, precoMin, precoMax, pageable);
	}
	
	public Produto findProdutoById(Long id) {


		return produtoRepository.findProdutoById(id);
	}

	public Produto findProdutoDetailsById(Long id) {
		return produtoRepository.findProdutoDetailsById(id);
	}

	public void editIncludesFile(Produto produto) {
		produtoRepository.editIncludesFile(produto.getNome(), 
				produto.getDescricao(), produto.getComprimento(), produto.getLargura(), 
				produto.getAltura(), produto.getPeso(), produto.getPreco(), produto.getData(), produto.getFileType(),
				produto.getId());
		
	}

	public void editNoFile(Produto produto) {
		produtoRepository.editNoFile(produto.getNome(), 
				produto.getDescricao(), produto.getComprimento(), produto.getLargura(), 
				produto.getAltura(), produto.getPeso(), produto.getPreco(),
				produto.getId());
		
	}

    
}
