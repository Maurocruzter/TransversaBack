package br.transversa.backend.controller;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.transversa.backend.model.Carrinho;
import br.transversa.backend.model.CarrinhosHasProduto;
import br.transversa.backend.model.Produto;
import br.transversa.backend.model.User;
import br.transversa.backend.payload.ApiResponse;
import br.transversa.backend.repository.CarrinhoHasProdutoRepository;
import br.transversa.backend.service.CarrinhoService;

@RestController
@RequestMapping("/api")
public class CarrinhoController {
	
	@Autowired
	CarrinhoService carrinhoService;
	
	
	@PostMapping(path = "/carrinho/add/{produtoId}/{quantidade}")
	ResponseEntity adicionarProdutoAoCarrinho(
			@PathVariable(name = "produtoId", required = true) Long produtoId,
			@PathVariable(name = "quantidade", required = true) int quantidade) {
		
		
		
		
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User loggedUser = new User();
        loggedUser.setId(Long.parseLong(auth.getName()));
        Carrinho carrinho = new Carrinho();
        
//        System.out.println("1");
        Optional<Carrinho> simpleCarrinho = 
        		carrinhoService.findCarrinhoByIdClienteAndIdVendedor(
        				loggedUser.getId(), 
        				loggedUser.getId());
        

        if(!simpleCarrinho.isPresent()) {
        	
        	carrinho.setUser1(loggedUser);
            carrinho.setUser2(loggedUser);
        	carrinho = carrinhoService.save(carrinho);
        	
        } else {
        	
        	carrinho.setId(simpleCarrinho.get().getId());
        	
        }
        
        Optional<CarrinhosHasProduto> simpleCarrinhosHasProduto = carrinhoService.findCarrinhoProdutoDetails(carrinho.getId(), produtoId);
        
        
        CarrinhosHasProduto carrinhosHasProduto = new CarrinhosHasProduto();
        carrinhosHasProduto.setCarrinho(carrinho);
    	Produto produto = new Produto();
    	produto.setId(produtoId);
    	carrinhosHasProduto.setProduto(produto);
    	carrinhosHasProduto.setQuantidade(quantidade);
        
        if(simpleCarrinhosHasProduto.isPresent()) {
        	
        	carrinhosHasProduto.setId(simpleCarrinhosHasProduto.get().getId());
        	
        }

        carrinhoService.saveCarrinhoHasProduto(carrinhosHasProduto);
        
        return new ResponseEntity(new ApiResponse(true, "Produto adicionado ao carrinho com sucesso!"),
                HttpStatus.CREATED);
        
		
	}
	
	@DeleteMapping(path = "/carrinho/delete/{produtoId}")
	public Long deleteProdutoCarrinho(@PathVariable(name = "produtoId", required = true) Long produtoId) {

		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User loggedUser = new User();

        loggedUser.setId(Long.parseLong(auth.getName()));
        
        Optional<Carrinho> simpleCarrinho = carrinhoService.findCarrinhoByIdClienteAndIdVendedor(loggedUser.getId(), loggedUser.getId());
        
        if(!simpleCarrinho.isPresent())
        	return null;
        
        Carrinho carrinho = new Carrinho();
        carrinho.setId(simpleCarrinho.get().getId());
        
        Produto produto = new Produto();
        produto.setId(produtoId);

        
        return carrinhoService.deleteProdutoDoCarrinho(carrinho, produto);

	}
	
	
	@GetMapping(path = "/carrinho")
	public List<Produto> visualizarCarrinho() {

		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User loggedUser = new User();
        loggedUser.setId(Long.parseLong(auth.getName()));
        List<Produto> str = carrinhoService.findProdutosNoCarrinhoCliente( loggedUser.getId() );
        return str;

	}
	


}
