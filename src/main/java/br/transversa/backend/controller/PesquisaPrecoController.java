package br.transversa.backend.controller;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
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
	
	
	
	@RequestMapping(value = "/pesquisaPrecoBulk/add", method = RequestMethod.POST)
	ResponseEntity<?> registrarVariosProdutos(
			@RequestParam List<String> razaoSocial,
			@RequestParam List<String> endereco,
			@RequestParam List<BigInteger> codigoBarras,
			@RequestParam List<String> descricao,
			@RequestParam List<String> marca,
			@RequestParam List<String> nome,
			@RequestParam List<BigDecimal> preco,
			@RequestParam List<MultipartFile>  file) throws IOException {
		
		
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		boolean isVendedor = auth.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_VENDEDOR"));
		
		if(!isVendedor) {
			return new ResponseEntity(new ApiResponse(true, "Produto Inválido!"), HttpStatus.FORBIDDEN);
		}

		
		User loggedUser = new User();
        loggedUser.setId(Long.parseLong(auth.getName()));
		
        List<Pesquisapreco> pesquisaPrecoList = new ArrayList<>();
        
        
		for(int i = 0; i<descricao.size(); i++) {
			
			Pesquisapreco pesquisaPreco = new Pesquisapreco();
			
			pesquisaPreco.setCodigoBarras(codigoBarras.get(i));
			pesquisaPreco.setData(file.get(i).getBytes());
			pesquisaPreco.setFileType(file.get(i).getContentType());
			pesquisaPreco.setDescricao(descricao.get(i));
			pesquisaPreco.setEndereco(endereco.get(i));
			pesquisaPreco.setMarca(marca.get(i));
			pesquisaPreco.setNome(nome.get(i));
			pesquisaPreco.setPreco(preco.get(i));
			pesquisaPreco.setRazaoSocial(razaoSocial.get(i));
			pesquisaPreco.setUser(loggedUser);
			UUID uuid = UUID.randomUUID();
			pesquisaPreco.setUuid(uuid.toString());
			
			pesquisaPrecoList.add(pesquisaPreco);
		}
		
		
		pesquisaPrecoService.createBulk(pesquisaPrecoList);

		


		return new ResponseEntity(new ApiResponse(true, "Produto adicionado com sucesso"), HttpStatus.CREATED);

	}

	@PostMapping(path = "/pesquisaPreco/add")
	ResponseEntity<?> registrarProduto(
			@RequestParam String razaoSocial,
			@RequestParam String endereco,
			@RequestParam List<BigInteger> codigoBarras,
			@RequestParam List<String> descricao,
			@RequestParam List<String> marca,
			@RequestParam List<String> nome,
			@RequestParam List<BigDecimal> preco,
			@RequestParam List<MultipartFile>  file
			
//			@RequestParam("preco") BigDecimal preco
			) throws IOException{
		
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		boolean isVendedor = auth.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_VENDEDOR"));
		
		if(!isVendedor) {
			return new ResponseEntity(new ApiResponse(true, "Produto Inválido!"), HttpStatus.FORBIDDEN);
		}

		
		User loggedUser = new User();
        loggedUser.setId(Long.parseLong(auth.getName()));
		
        List<Pesquisapreco> pesquisaPrecoList = new ArrayList<>();
        
        
		for(int i = 0; i<descricao.size(); i++) {
			
			Pesquisapreco pesquisaPreco = new Pesquisapreco();
			
			pesquisaPreco.setCodigoBarras(codigoBarras.get(i));
			pesquisaPreco.setData(file.get(i).getBytes());
			pesquisaPreco.setFileType(file.get(i).getContentType());
			pesquisaPreco.setDescricao(descricao.get(i));
			pesquisaPreco.setEndereco(endereco);
			pesquisaPreco.setMarca(marca.get(i));
			pesquisaPreco.setNome(nome.get(i));
			pesquisaPreco.setPreco(preco.get(i));
			pesquisaPreco.setRazaoSocial(razaoSocial);
			pesquisaPreco.setUser(loggedUser);
			UUID uuid = UUID.randomUUID();
			pesquisaPreco.setUuid(uuid.toString());
			
			pesquisaPrecoList.add(pesquisaPreco);
		}
		
		
		pesquisaPrecoService.createBulk(pesquisaPrecoList);

		return new ResponseEntity(new ApiResponse(true, "Produto adicionado com sucesso"),
                    HttpStatus.CREATED);

		
	}
	
	
	@GetMapping(path = "/listPesquisaPreco/page/{pageNumber}")
	Page<Pesquisapreco> listProdutosByPage(
		@PathVariable(name = "pageNumber", required = false) int pageNumber) {

		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if( auth.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_BASE")) ||
				auth.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_ADMIN"))	) {
			pesquisaPrecoService.findAllPesquisaPrecoByPage(pageNumber);
		}
		
		return null;
	}
	
	@GetMapping(path = "/pesquisaPreco/search"
			+ "/produto/{produto}"
			+ "/precoMin/{precoMin}"
			+ "/precoMax/{precoMax}"
			+ "/marca/{marca}"
			+ "/codigoBarras/{codigoBarras}"
			+ "/vendedor/{vendedor}"
			+ "/dataInicio/{dataInicio}"
			+ "/dataFim/{dataFim}"
			+ "/razaoSocial/{razaoSocial}"
			+ "/page/{pageNumber}")
	Page<Pesquisapreco> listPesquisaPrecosByFields(
				@PathVariable(name = "razaoSocial", required = true) String razaoSocial,
				@PathVariable(name = "precoMin", required = true) String precoMin,
				@PathVariable(name = "precoMax", required = true) String precoMax,
				@PathVariable(name = "produto", required = true) String produto,
				@PathVariable(name = "marca", required = true) String marca,
				@PathVariable(name = "codigoBarras", required = true) String codigoBarras,
				@PathVariable(name = "dataInicio", required = true) String dataInicio,
				@PathVariable(name = "dataFim", required = true) String dataFim,
				@PathVariable(name = "vendedor", required = true) String vendedor,
				@PathVariable(name = "pageNumber", required = true) int pageNumber) {

		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if( auth.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_BASE")) ||
				auth.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_ADMIN"))	) {
			return pesquisaPrecoService.findPesquisaPrecoByFields(razaoSocial, precoMin, precoMax, produto,
					marca, codigoBarras, dataInicio, dataFim, vendedor, pageNumber);
		}
		
		return null;
		
//		return pesquisaPrecoService.findAllPesquisaPrecoByPage(pageNumber);
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

		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if( auth.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_BASE")) ||
				auth.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_ADMIN"))	) {
			return pesquisaPrecoService.findPesquisaPrecoById(id);
		}
		return null;
		

	}
	
	@GetMapping(path = "/pesquisaPreco/searchNome/{nome}/page/{pageNumber}")
	Page<Pesquisapreco> searchProduto(@PathVariable(name = "nome", required = true) String nome,
								@PathVariable(name = "pageNumber", required = true) int pageNumber) {
		
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		
		if( auth.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_BASE")) ||
				auth.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_ADMIN"))	) {
			return pesquisaPrecoService.findPesquisaPrecoSearchNome(nome,pageNumber);
		}

		return null;

	}
	
	
}
