package br.transversa.backend.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.transversa.backend.model.Carrinho;
import br.transversa.backend.model.CarrinhosHasProduto;
import br.transversa.backend.model.Produto;
import br.transversa.backend.repository.CarrinhoHasProdutoRepository;
import br.transversa.backend.repository.CarrinhoRepository;
import br.transversa.backend.repository.ProdutoRepository;

@Service
@Transactional
public class CarrinhoService {
	
	@Autowired
    CarrinhoRepository carrinhoRepository;
	
	@Autowired
    CarrinhoHasProdutoRepository carrinhoHasProdutoRepository;
	
	
	@Autowired
	ProdutoRepository produtoRepository;
	
	public Carrinho save(Carrinho carrinho) {
		return carrinhoRepository.save(carrinho);
	}
	
	
	public CarrinhosHasProduto saveCarrinhoHasProduto(CarrinhosHasProduto carrinhoHasProduto) {
		return carrinhoHasProdutoRepository.save(carrinhoHasProduto);
	}
	
	public Long deleteProdutoDoCarrinho(Carrinho carrinho, Produto produto) {
		return carrinhoHasProdutoRepository.deleteByCarrinhoAndProduto(carrinho, produto);
	}
	
	public List<Produto> findProdutosNoCarrinhoCliente(Long idCliente){
		return produtoRepository.findProdutosNoCarrinhoCliente(idCliente);
	}

	public Optional<Carrinho> findCarrinhoByIdClienteAndIdVendedor(Long idVendedor, Long idCliente) {
		return carrinhoRepository.findCarrinhoByUser1AndUser2(idVendedor, idCliente);
	}
	
	public Optional<CarrinhosHasProduto> findCarrinhoProdutoDetails(Long carrinhoId, Long produtoId){
		return carrinhoHasProdutoRepository.findCarrinhoByCarrinhoIdAndProdutoId(carrinhoId, produtoId);
	}
	
//	public Long deleteThings(Carrinho carrinho) {
//		return carrinhoHasProdutoRepository.deleteByCarrinho(carrinho);
//	}
	
	public Long deleteByCarrinho(Carrinho carrinho) {
		return carrinhoHasProdutoRepository.deleteByCarrinho(carrinho);
	}
	
	public List<Produto> findProdutosNoCarrinhoClienteWithIdCarrinho(Long idCliente){
		return produtoRepository.findProdutosNoCarrinhoClienteWithIdCarrinho(idCliente);
	}

}
