package br.transversa.backend.controller;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import br.transversa.backend.model.Produto;
import br.transversa.backend.model.User;
import br.transversa.backend.payload.ApiResponse;
import br.transversa.backend.service.ProdutoService;

@RestController
@RequestMapping("/api/v1")
public class ProdutoController {

	@Autowired
	ProdutoService produtoService;
	
	
	@PostMapping(path = "/produto/add")
	ResponseEntity<?> registrarProduto(@RequestParam("file") MultipartFile file, 
			@RequestParam("nome") String nome, 
			@RequestParam("descricao") String descricao,
			@RequestParam("preco") BigDecimal preco) throws IOException{
		
		
//		System.out.println("entra aqui?");
		Produto produto = new Produto();
		
		produto.setNome(nome);
		produto.setDescricao(descricao);
		produto.setPreco(preco);
		produto.setData(file.getBytes());
		produto.setFileType(file.getContentType());
		
		UUID uuid = UUID.randomUUID();
		
		produto.setUuid(uuid.toString());
		
		
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User loggedUser = new User();
        loggedUser.setId(Long.parseLong(auth.getName()));
        
        //Placing createdBy		
		produto.setUser(loggedUser);
		
		produtoService.save(produto);
        return new ResponseEntity(new ApiResponse(true, "Produto adicionado com sucesso"),
                    HttpStatus.CREATED);

		
	}
	
	@GetMapping(path = "/listProdutos/page/{pageNumber}")
	Page<Produto> listProdutosByPage(
		@PathVariable(name = "pageNumber", required = false) int pageNumber) {

		return produtoService.findAllProdutoByPage(pageNumber);
	}
	
	
	@GetMapping(path = "/produto/loadImage/{id}")
	ResponseEntity<Resource> carregarProdutoImagem(@PathVariable(name = "id", required = false) String uuid) {
		
		Produto produto = produtoService.findPesquisaPrecoByUuid(uuid);
		
		if(produto == null)
			return null;
		
		return ResponseEntity.ok()
        .contentType(MediaType.parseMediaType(produto.getFileType()))
        .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + produto.getNome() + "\"")
        .body(new ByteArrayResource(produto.getData()));
        		
	}
	
	
	@GetMapping(path = "/produto/{id}")
	Produto carregarProduto(@PathVariable(name = "id", required = true) Long id) {
		
		return produtoService.findPesquisaPrecoById(id);

	}
	
	@GetMapping(path = "/produto/searchNome/{nome}/page/{pageNumber}")
	Page<Produto> searchProduto(@PathVariable(name = "nome", required = true) String nome,
								@PathVariable(name = "pageNumber", required = true) int pageNumber) {
		
//		System.out.println("sfasfasfsa "+nome);
//		return null;
		//System.out.println(nome);
		
		return produtoService.findPesquisaPrecoSearchNome(nome,pageNumber);
		//return produtoService.findProdutoById(nome);

	}
	
	@GetMapping(path = "/produto/searchById/{id}")
	Produto searchProdutoById(@PathVariable(name = "id", required = true) Long id) {
		
		return produtoService.findProdutoById(id);

	}
	
	
	@GetMapping(path = "/listAllProdutos")
	List<Produto> listAllProdutos() {
		return produtoService.findAllProdutos();
	}
	
	
}
