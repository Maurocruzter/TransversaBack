package br.transversa.backend.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import br.transversa.backend.model.Automovel;
import br.transversa.backend.model.EstadoPedido;
import br.transversa.backend.model.ObservacaoEstadoPedido;
import br.transversa.backend.model.Produto;
import br.transversa.backend.model.Quilometragem;
import br.transversa.backend.repository.AutomovelRepository;
import br.transversa.backend.repository.ObservacaoEstadoPedidoRepository;
import br.transversa.backend.repository.ProdutoRepository;
import br.transversa.backend.repository.QuilometragemRepository;

@Service
public class AutomovelService {
	
	@Autowired
	AutomovelRepository automovelRepository;
	
	@Autowired
	QuilometragemRepository quilometragemRepository;

	public void save(Automovel automovel) {
		// TODO Auto-generated method stub
		automovelRepository.save(automovel);
	}
	
	public Optional<Automovel> findAutomovelByMatricula(String matricula) {
		// TODO Auto-generated method stub
		return automovelRepository.findAutomovelByMatricula(matricula);
	}
	
	public List<Automovel> findAllAutomovel() {
		// TODO Auto-generated method stub
		return automovelRepository.findAllAutomoveis();
	}
	
	public void saveQuilometragem(Quilometragem quilometragem) {
		// TODO Auto-generated method stub
		quilometragemRepository.save(quilometragem);
	}

	public Page<Quilometragem> findAutomovelByPage(int pageNumber) {

		Pageable pageable = PageRequest.of(pageNumber, 20);
		return quilometragemRepository.findAutomovelByPage(pageable);
	}

	
//	@Autowired
//	ProdutoRepository produtoRepository;
//	
//	@Autowired
//	ObservacaoEstadoPedidoRepository observacaoEstadoPedidoRepository;
//	
//	
//	public Produto save(Produto produto) {
//		return produtoRepository.save(produto);
//	}
//	
//	public ObservacaoEstadoPedido carregarReclamacaoImagem(Long id) {
//		return observacaoEstadoPedidoRepository.findObservacaoEstadoPedidoById(id);
//	}
//
//	public Produto findPesquisaPrecoByUuid(String uuid) {
//		return produtoRepository.findProdutoByUuid(uuid);
//	}
//	
//	public Produto findPesquisaPrecoById(Long id) {
//		return produtoRepository.findProdutoById(id);
//	}
//	
//	public Optional<Produto> findProdutoPrecoById(Long id) {
//		return produtoRepository.findProdutoPrecoById(id);
//	}
//	
//
//	public Page<Produto> findAllProdutoByPage(int pageNumber) {
//		Pageable pageable = PageRequest.of(pageNumber, 20);
//		return produtoRepository.findProdutoAll(pageable);
//	}
//	
//	public List<Produto> findAllProdutos() {
//		return produtoRepository.findAllProdutos();
//	}
//	
//	
//	public Page<Produto> findPesquisaPrecoSearchNome(String nome, int pageNumber) {
//		Pageable pageable = PageRequest.of(pageNumber, 20);
////		System.out.println(nome);
//		return produtoRepository.findProdutoByNome(nome, pageable);
//	}
//	
//	public Produto findProdutoById(Long id) {
//
//
//		return produtoRepository.findProdutoById(id);
//	}

    
}
