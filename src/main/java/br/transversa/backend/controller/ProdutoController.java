package br.transversa.backend.controller;

import java.io.IOException;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Optional;
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
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import br.transversa.backend.model.Produto;
import br.transversa.backend.model.Promocoes;
import br.transversa.backend.model.Stock;
import br.transversa.backend.model.StockPromocao;
import br.transversa.backend.model.User;
import br.transversa.backend.payload.ApiResponse;
import br.transversa.backend.payload.NewPromocoesRequest;
import br.transversa.backend.service.ProdutoService;
import br.transversa.backend.service.PromocaoService;
import br.transversa.backend.service.StockPromocaoService;
import br.transversa.backend.service.StockService;

@RestController
@RequestMapping("/api/v1")
public class ProdutoController {

	@Autowired
	ProdutoService produtoService;
	
	@Autowired
	PromocaoService promocoesService;
	
	@Autowired
	StockService stockService;
	
	@Autowired
	StockPromocaoService stockPromocaoService;
	
	
	@PostMapping(path = "/produto/add")
	ResponseEntity<?> registrarProduto(@RequestParam("file") MultipartFile file, 
			@RequestParam("nome") String nome, 
			@RequestParam("descricao") String descricao,
			@RequestParam("preco") BigDecimal preco,
			@RequestParam("comprimento") Float comprimento,
			@RequestParam("largura") Float largura,
			@RequestParam("altura") Float altura,
			@RequestParam("peso") Float peso,
			@RequestParam("stockInicial") String stockInicial) throws IOException{
		
		
		Produto produto = new Produto();
		
		produto.setNome(nome);
		produto.setDescricao(descricao);
		
		produto.setPreco(preco);
		produto.setData(file.getBytes());
		produto.setFileType(file.getContentType());
		
		produto.setComprimento(comprimento);
		produto.setAltura(altura);
		produto.setLargura(largura);
		produto.setPeso(peso);
		
		UUID uuid = UUID.randomUUID();
		
		produto.setUuid(uuid.toString());
		
		
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User loggedUser = new User();
        loggedUser.setId(Long.parseLong(auth.getName()));
        
        //Placing createdBy		
		produto.setUser(loggedUser);
		
		Date myDate = parseDate("1992-10-14");
		
		Promocoes promocaoReal = new Promocoes();
		
		promocaoReal.setCompraMinima(0);
		promocaoReal.setDataAdicionado(new Timestamp(new Date().getTime()));
		promocaoReal.setDataFim(new Timestamp(myDate.getTime()));
		promocaoReal.setDataInicio(new Timestamp(myDate.getTime()));
		promocaoReal.setDesconto(new BigDecimal(0));
		
		
		produtoService.save(produto);
		
		Stock stock = new Stock();
		stock.setProduto(produto);
		stock.setQuantidade(new Integer(stockInicial));
		
		stockService.save(stock);
		
		//promocaoReal.setProduto(produto);
        loggedUser.setId(Long.parseLong(auth.getName()));
		promocaoReal.setUser(loggedUser);
		
		promocaoReal.setStock(stock);
		promocoesService.save(promocaoReal);
		
		
		StockPromocao stockPromocao = new StockPromocao();
		stockPromocao.setPromocoe(promocaoReal);
		stockPromocao.setQuantidadeEmPromocao(0);
		
		stockPromocaoService.save(stockPromocao);
		
        return new ResponseEntity(new ApiResponse(true, "Produto adicionado com sucesso"),
                    HttpStatus.CREATED);

		
	}
	
	private Date dateFromUTC(Date date){
	    return new Date(date.getTime() + Calendar.getInstance().getTimeZone().getOffset(date.getTime()));
	}
	
	private Date dateToUTC(Date date){
	    return new Date(date.getTime() - Calendar.getInstance().getTimeZone().getOffset(date.getTime()));
	}

	 public Date parseDate(String date) {
	     try {
	         return new SimpleDateFormat("yyyy-MM-dd").parse(date);
	     } catch (ParseException e) {
	         return null;
	     }
	  }
	
	@PostMapping(path = "/promocao/add")
	ResponseEntity<?> registrarProduto(@RequestBody NewPromocoesRequest promocao) {
		
		
		
		
		
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		

		if(auth.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_ADMIN")) == false &&
				auth.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_BASE")) == false) {
			
			return new ResponseEntity(new ApiResponse(true, "Não possui previlégios"),
                    HttpStatus.FORBIDDEN);
		}
		

		if(promocao.getDataInicio().compareTo(promocao.getDataFim()) > 0) {
			
			return new ResponseEntity(new ApiResponse(true, "Data de início é maior que a data fim"),
                    HttpStatus.FORBIDDEN);
            
		}
		
		Promocoes promocaoReal = new Promocoes();
		
		promocaoReal.setCompraMinima(promocao.getCompraMinima());
		promocaoReal.setDataAdicionado(new Timestamp(new Date().getTime()));
		promocaoReal.setDataFim(new Timestamp(promocao.getDataFim().getTime()) );
		promocaoReal.setDataInicio(new Timestamp(promocao.getDataInicio().getTime()));
		promocaoReal.setDesconto(promocao.getDesconto());
		Produto produto = new Produto();
		produto.setId(promocao.getIdProduto());
		//promocaoReal.setProduto(produto);
		User loggedUser = new User();
        loggedUser.setId(Long.parseLong(auth.getName()));
		promocaoReal.setUser(loggedUser);
		
		Optional<Promocoes> str = promocoesService.findIfPromocaoExiste(promocao.getIdProduto(), promocao.getDataInicio(), promocao.getDataFim());

		if(str.isPresent()) {
			return new ResponseEntity(new ApiResponse(true, "Espere a promoção expirar"),
                    HttpStatus.BAD_REQUEST);
		}
		
		
		
		promocaoReal.setStock(
				stockService.findStockByProdutoId(promocao.getIdProduto()));
		promocoesService.save(promocaoReal);

		StockPromocao stockPromocao = new StockPromocao();
		stockPromocao.setPromocoe(promocaoReal);
		stockPromocao.setQuantidadeEmPromocao(promocao.getQuantidade());
		
		stockPromocaoService.save(stockPromocao);

		

		
        return new ResponseEntity(new ApiResponse(true, "Promoção adicionada com sucesso"),
                    HttpStatus.CREATED);

		
	}
	
	@GetMapping(path = "/listPromocoes/page/{pageNumber}")
	Page<StockPromocao> listPromocoesByPage(
		@PathVariable(name = "pageNumber", required = false) int pageNumber) {
		return promocoesService.findPromocoesByPageModified(pageNumber, new Date());

	}
	
	
	@GetMapping(path = "/listAllPromocoesVOffline")
	List<StockPromocao> listAllPromocoesVOffline() {
		
		return promocoesService.findAllStockPromocoesAtivasVOffline(new Date());


//		return promocoesService.findPromocoesByPage(pageNumber, new Date());

	}
	
	@GetMapping(path = "/listProdutos/page/{pageNumber}")
//	Page<Produto> listProdutosByPage(
	List<StockPromocao> listProdutosByPage(
		@PathVariable(name = "pageNumber", required = false) int pageNumber) {
		

//		Page<Promocoes> aux = produtoService.findAllProdutoByPageRetrieveOnlyId(pageNumber);
		Page<StockPromocao> aux = stockPromocaoService.findAllProdutoByPageRetrieveOnlyId(pageNumber);
		
		List<StockPromocao> promocoesList = new ArrayList<>();
		
		
		
		int tamanho = aux.getContent().size();
		for(int i = 0; i < tamanho; i++) {
			
			promocoesList.add(
					stockPromocaoService.findPromocoesByProdutoId(
							pageNumber, aux.getContent().get(i).getId())) ;
		}
		return promocoesList;
//		return promocoesService.findProdutosByPage(pageNumber);
//		return produtoService.findAllProdutoByPage(pageNumber);
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
		
//		return null;
		
		return produtoService.findPesquisaPrecoSearchNome(nome,pageNumber);
		//return produtoService.findProdutoById(nome);

	}
	
//	@GetMapping(path = "/produto/searchById/{id}")
//	Produto searchProdutoById(@PathVariable(name = "id", required = true) Long id) {
//		
//		return produtoService.findProdutoById(id);
//
//	}
	
	@GetMapping(path = "/produto/searchById/{id}")
	StockPromocao searchProdutoById(@PathVariable(name = "id", required = true) Long id) {
		
//		System.out.println("fasfasfsaf");
		
//		StockPromocaoService.find
		return stockPromocaoService.findPromocoesByProdutoId(0, id);
//		return promocoesService.findPromocoesByProdutoId(0, id);

	}
	
	
	@GetMapping(path = "/listAllProdutos")
	List<Produto> listAllProdutos() {
		return produtoService.findAllProdutos();
	}
	
	@GetMapping(path = "/listAllProdutosVOffline")
	List<StockPromocao> listAllProdutosVOffline() {
		
		System.out.println("Veio aqui");
		
		List<Produto> aux = stockPromocaoService.findProdutoAllRetrieveOnlyId();
		
		List<StockPromocao> promocoesList = new ArrayList<>();
		
		
		int pageNumber = 0;
		
		
		int tamanho = aux.size();
		for(int i = 0; i < tamanho; i++) {
			
			promocoesList.add(
					stockPromocaoService.findPromocoesByProdutoId(
							pageNumber, aux.get(i).getId())) ;
			
			
//			promocoesList.add(promocoesService.findPromocoesByProdutoId(pageNumber, aux.getContent().get(i).getId())) ;
			
		}
		
		
		return promocoesList;
	}
	
	
}
