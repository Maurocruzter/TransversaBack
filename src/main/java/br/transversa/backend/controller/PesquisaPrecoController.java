package br.transversa.backend.controller;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
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
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import br.transversa.backend.model.Pesquisapreco;
import br.transversa.backend.model.Produto;
import br.transversa.backend.model.Role;
import br.transversa.backend.model.User;
import br.transversa.backend.model.UserHasRole;
import br.transversa.backend.payload.ApiResponse;
import br.transversa.backend.security.JwtTokenProvider;
import br.transversa.backend.service.PesquisaPrecoService;
import br.transversa.backend.service.ProdutoService;
import br.transversa.backend.service.RoleService;
import br.transversa.backend.service.UserHasRoleService;
import br.transversa.backend.service.UserService;
import br.transversa.backend.util.AppConstants;

@RestController
@RequestMapping("/api/v1")
public class PesquisaPrecoController {


	@Autowired
	PesquisaPrecoService pesquisaPrecoService;
	
	

	@PostMapping(path = "/pesquisaPreco/add")
	ResponseEntity<?> registrarProduto(@RequestParam("file") MultipartFile file, 
			@RequestParam("nome") String nome, 
			@RequestParam("marca") String marca,
			@RequestParam("preco") BigDecimal preco) throws IOException{
		
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		boolean isVendedor = auth.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_VENDEDOR"));
		
		if(!isVendedor) {
			return new ResponseEntity(new ApiResponse(true, "Produto Inválido!"), HttpStatus.FORBIDDEN);
		}

		System.out.println("entra aqui?");
		
		Pesquisapreco pesquisaPreco = new Pesquisapreco();
		
		pesquisaPreco.setNome(nome);
		pesquisaPreco.setMarca(marca);
		pesquisaPreco.setPreco(preco);
		pesquisaPreco.setData(file.getBytes());
		pesquisaPreco.setFileType(file.getContentType());
		
		UUID uuid = UUID.randomUUID();
		
		pesquisaPreco.setUuid(uuid.toString());
		
		
        User loggedUser = new User();
        loggedUser.setId(Long.parseLong(auth.getName()));
        
        //Placing createdBy		
        pesquisaPreco.setUser(loggedUser);
		
		pesquisaPrecoService.save(pesquisaPreco);
		//produtoService.save(produto);
        return new ResponseEntity(new ApiResponse(true, "Produto adicionado com sucesso"),
                    HttpStatus.CREATED);

		
	}
	
	
	@GetMapping(path = "/listPesquisaPreco/page/{pageNumber}")
	Page<Pesquisapreco> listProdutosByPage(
		@PathVariable(name = "pageNumber", required = false) int pageNumber) {

		System.out.println("Estou aqui dentro");
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		
//		boolean isVendedor = auth.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_ADMIN"));
//		boolean isBase = auth.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_BASE"));
//		
//		if(!isVendedor || !isBase) {
//			return null;
//		}
		
		return pesquisaPrecoService.findAllPesquisaPrecoByPage(pageNumber);
	}
	
	
	@GetMapping(path = "/pesquisaPreco/loadImage/{id}")
	ResponseEntity<Resource> carregarProdutoImagem(@PathVariable(name = "id", required = false) String uuid) {
		
		Pesquisapreco produto = pesquisaPrecoService.findPesquisaPrecoByUuid(uuid);
		
		if(produto == null)
			return null;
		
		return ResponseEntity.ok()
        .contentType(MediaType.parseMediaType(produto.getFileType()))
        .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + produto.getNome() + "\"")
        .body(new ByteArrayResource(produto.getData()));
        		
	}
	
	
	@GetMapping(path = "/pesquisaPreco/{id}")
	Pesquisapreco carregarProduto(@PathVariable(name = "id", required = true) Long id) {
		
		return pesquisaPrecoService.findPesquisaPrecoById(id);

	}
	
	@GetMapping(path = "/pesquisaPreco/searchNome/{nome}/page/{pageNumber}")
	Page<Pesquisapreco> searchProduto(@PathVariable(name = "nome", required = true) String nome,
								@PathVariable(name = "pageNumber", required = true) int pageNumber) {
		
//		System.out.println("sfasfasfsa "+nome);
//		return null;
		//System.out.println(nome);
		
		return pesquisaPrecoService.findPesquisaPrecoSearchNome(nome,pageNumber);
		//return produtoService.findProdutoById(nome);

	}
	
	
}