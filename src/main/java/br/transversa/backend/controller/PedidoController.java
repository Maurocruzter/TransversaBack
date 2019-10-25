package br.transversa.backend.controller;

import java.io.IOException;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

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

import br.transversa.backend.model.EntregadorPedido;
import br.transversa.backend.model.EstadoPedido;
import br.transversa.backend.model.ObservacaoEstadoPedido;
import br.transversa.backend.model.Pedido;
import br.transversa.backend.model.PedidosHasProduto;
import br.transversa.backend.model.Produto;
import br.transversa.backend.model.User;
import br.transversa.backend.payload.ApiResponse;
import br.transversa.backend.payload.ListNovoPedidoRequest;
import br.transversa.backend.payload.PedidoAndEstadoPedidoCustom;
import br.transversa.backend.payload.PedidoObservacaoRequest;
import br.transversa.backend.service.CarrinhoService;
import br.transversa.backend.service.PedidoService;
import br.transversa.backend.service.ProdutoService;
import br.transversa.backend.service.UserService;
import br.transversa.backend.util.AppConstants;

@RestController
@RequestMapping("/api/v1")
public class PedidoController {

	@Autowired
	CarrinhoService carrinhoService;

	@Autowired
	UserService userService;

	@Autowired
	PedidoService pedidoService;

	@Autowired
	ProdutoService produtoService;

//	@PostMapping(path = "/pedidoCliente/novo")
//	ResponseEntity fazerPedidoCliente() {
//
//		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
//		User loggedUser = new User();
//		loggedUser.setId(Long.parseLong(auth.getName()));
//
//		List<Produto> listProdutos = carrinhoService.findProdutosNoCarrinhoClienteWithIdCarrinho(loggedUser.getId());
//
//		if (listProdutos.isEmpty())
//			return null;
//
//		Pedido pedido = new Pedido();
//		pedido.setUser1(loggedUser);
//		pedido.setUser2(loggedUser);
//
//		pedido = pedidoService.createOrUpdatePedido(pedido);
//
//		List<PedidosHasProduto> pedidoHasProdutoList = new ArrayList<>();
//
//		BigDecimal total = new BigDecimal(0);
//
//		for (Produto produto2 : listProdutos) {
//
//			PedidosHasProduto pedidoHasProduto = new PedidosHasProduto();
//			pedidoHasProduto.setPedido(pedido);
//			pedidoHasProduto.setPreco(produto2.getPreco());
//			pedidoHasProduto.setProduto(produto2);
//			pedidoHasProduto.setQuantidade(produto2.getQuantidade());
//			pedidoHasProdutoList.add(pedidoHasProduto);
//			BigDecimal itemPrice = produto2.getPreco().multiply(new BigDecimal(produto2.getQuantidade()));
//			total = total.add(itemPrice);
//
//		}
//
//		pedido.setTotalPedido(total);
//		pedido = pedidoService.createOrUpdatePedido(pedido);
//
//		pedidoService.createBulk(pedidoHasProdutoList);
//
//		EstadoPedido estadoPedido = new EstadoPedido();
//		estadoPedido.setPedido(pedido);
//		estadoPedido.setUser(loggedUser);
//		estadoPedido.setObservacao("Em análise");
//
//		pedidoService.createEstadoPedido(estadoPedido);
//
//		Carrinho carrinho = new Carrinho();
//		carrinho.setId(listProdutos.get(0).getIdCarrinho());
//
//		carrinhoService.deleteByCarrinho(carrinho);
//
//		return new ResponseEntity(new ApiResponse(true, "Pedido efetuado com sucesso!"), HttpStatus.CREATED);
//
//	}
	
	@GetMapping(path = "/pedido/loadImage/reclamacaoImagem/{id}")
	ResponseEntity<Resource> carregarReclamacaoImagem(@PathVariable(name = "id", required = false) Long id) {
		
		ObservacaoEstadoPedido observacaoEstadoPedido = produtoService.carregarReclamacaoImagem(id);
		
		if(observacaoEstadoPedido == null)
			return null;
		
		return ResponseEntity.ok()
        .contentType(MediaType.parseMediaType("image/jpeg"))
        .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + "Reclamacao" + "\"")
        .body(new ByteArrayResource(observacaoEstadoPedido.getData()));
        		
	}

	@PostMapping(path = "/pedidoFuncionario/novo")
	ResponseEntity fazerPedidoComoFuncionario(@RequestBody ListNovoPedidoRequest listNovoPedidoRequest) {

		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		boolean isAdmin = auth.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_ADMIN"));
		boolean isVendedor = auth.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_VENDEDOR"));
		User loggedUser = new User();
		loggedUser.setId(Long.parseLong(auth.getName()));

		User clienteUser = new User();
		clienteUser.setId(listNovoPedidoRequest.getCliente());

		Pedido pedido = new Pedido();

		if (isAdmin) {
			// Setting quem disparou a compra
			pedido.setUser1(loggedUser);
			// Setting quem disparou vai pagar a compra
			pedido.setUser2(clienteUser);

		} else if (isVendedor) {

			Optional<User> optionalUser = userService.findIfVendedorHasCliente(loggedUser.getId(), clienteUser.getId());

			if (optionalUser.isPresent()) {
				// Setting quem disparou a compra
				pedido.setUser1(loggedUser);
				// Setting quem disparou vai pagar a compra
				pedido.setUser2(clienteUser);
				pedido.setTotalPedido(new BigDecimal(0));
			} else {
				return new ResponseEntity(new ApiResponse(true, "Acesso negado!"), HttpStatus.FORBIDDEN);
			}

		} else {
			return new ResponseEntity(new ApiResponse(true, "Acesso negado!"), HttpStatus.FORBIDDEN);
		}

		int indexPagamento = AppConstants.FORMA_PAGAMENTOS.indexOf(listNovoPedidoRequest.getFormaPagamento());

		if (indexPagamento < 0) {
			return new ResponseEntity(new ApiResponse(false, "O Método de pagamento especificado "),
					HttpStatus.BAD_REQUEST);
		}

		pedido.setFormaPagamento(indexPagamento);
		// pedidoService.createOrUpdatePedido(pedido);
		List<PedidosHasProduto> pedidoHasProdutoList = new ArrayList<>();

		BigDecimal total = new BigDecimal(0);

		for (int i = 0; i < listNovoPedidoRequest.getProdutosList().size(); i++) {

			int quantidade = listNovoPedidoRequest.getProdutosList().get(i).getQuantidade();

			if (quantidade < 1) {
				return new ResponseEntity(new ApiResponse(true, "Acesso negado!"), HttpStatus.FORBIDDEN);
			}

			Optional<Produto> optionalProduto = produtoService
					.findProdutoPrecoById(listNovoPedidoRequest.getProdutosList().get(i).getId());

			if (!optionalProduto.isPresent()) {
				return new ResponseEntity(new ApiResponse(true, "Acesso negado!"), HttpStatus.FORBIDDEN);
			}

			PedidosHasProduto pedidoHasProduto = new PedidosHasProduto();

			pedidoHasProduto.setPedido(pedido);
			pedidoHasProduto.setPreco(optionalProduto.get().getPreco());
			pedidoHasProduto.setQuantidade(quantidade);
			optionalProduto.get().setId(listNovoPedidoRequest.getProdutosList().get(i).getId());
			pedidoHasProduto.setProduto(optionalProduto.get());

			int index = searchForRepeatedElements(pedidoHasProdutoList, optionalProduto.get().getId());

			if (index == -1) {
				pedidoHasProdutoList.add(pedidoHasProduto);
			} else {
				pedidoHasProdutoList.get(index)
						.setQuantidade(pedidoHasProdutoList.get(index).getQuantidade() + quantidade);
			}

			BigDecimal itemPrice = optionalProduto.get().getPreco().multiply(new BigDecimal(quantidade));
			total = total.add(itemPrice);

		}

		pedido.setTotalPedido(total);
		pedido = pedidoService.createOrUpdatePedido(pedido);

		pedidoService.createBulk(pedidoHasProdutoList);

		EstadoPedido estadoPedido = new EstadoPedido();
		estadoPedido.setPedido(pedido);
		estadoPedido.setUser(loggedUser);
		estadoPedido.setObservacao("Em análise");

		pedidoService.createEstadoPedido(estadoPedido);

		return new ResponseEntity(new ApiResponse(true, "" + pedido.getId()), HttpStatus.CREATED);

	}

	@PostMapping(path = "/pedidoCliente/novo")
	ResponseEntity fazerPedidoComoCliente(@RequestBody ListNovoPedidoRequest listNovoPedidoRequest) {

		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		boolean isCliente = auth.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_CLIENTE"));

		if (!isCliente) {
			return new ResponseEntity(new ApiResponse(true, "Pedido efetuado com sucesso!"), HttpStatus.FORBIDDEN);
		}

		User loggedUser = new User();
		loggedUser.setId(Long.parseLong(auth.getName()));

		Pedido pedido = new Pedido();

		// Setting quem disparou a compra
		pedido.setUser1(loggedUser);
		// Setting quem disparou vai pagar a compra
		pedido.setUser2(loggedUser);

		List<PedidosHasProduto> pedidoHasProdutoList = new ArrayList<>();

		BigDecimal total = new BigDecimal(0);

		int indexPagamento = AppConstants.FORMA_PAGAMENTOS.indexOf(listNovoPedidoRequest.getFormaPagamento());

		if (indexPagamento < 0) {
			return new ResponseEntity(new ApiResponse(false, "O Método de pagamento especificado "),
					HttpStatus.BAD_REQUEST);
		}

		pedido.setFormaPagamento(indexPagamento);

//		System.out.println("A forma de PAgamento é!!!!!!!!!!!! " + pedido.getFormaPagamento());
//		int a = 0;
//		if (a == 0) {
//			return null;
//		}

		for (int i = 0; i < listNovoPedidoRequest.getProdutosList().size(); i++) {

			int quantidade = listNovoPedidoRequest.getProdutosList().get(i).getQuantidade();

			if (quantidade < 1) {
				return new ResponseEntity(new ApiResponse(true, "Quantidade deve ser superior a 0!"),
						HttpStatus.NOT_ACCEPTABLE);
			}

			Optional<Produto> optionalProduto = produtoService
					.findProdutoPrecoById(listNovoPedidoRequest.getProdutosList().get(i).getId());

			if (!optionalProduto.isPresent()) {
				return new ResponseEntity(new ApiResponse(true, "Produto Inválido!"), HttpStatus.NOT_ACCEPTABLE);
			}

			PedidosHasProduto pedidoHasProduto = new PedidosHasProduto();

			pedidoHasProduto.setPedido(pedido);
			pedidoHasProduto.setPreco(optionalProduto.get().getPreco());
			pedidoHasProduto.setQuantidade(quantidade);
			optionalProduto.get().setId(listNovoPedidoRequest.getProdutosList().get(i).getId());
			pedidoHasProduto.setProduto(optionalProduto.get());

			int index = searchForRepeatedElements(pedidoHasProdutoList, optionalProduto.get().getId());

			if (index == -1) {
				pedidoHasProdutoList.add(pedidoHasProduto);
			} else {
				pedidoHasProdutoList.get(index)
						.setQuantidade(pedidoHasProdutoList.get(index).getQuantidade() + quantidade);
			}

			BigDecimal itemPrice = optionalProduto.get().getPreco().multiply(new BigDecimal(quantidade));
			total = total.add(itemPrice);

		}

		pedido.setTotalPedido(total);
		pedido = pedidoService.createOrUpdatePedido(pedido);

		pedidoService.createBulk(pedidoHasProdutoList);

		EstadoPedido estadoPedido = new EstadoPedido();
		estadoPedido.setPedido(pedido);
		estadoPedido.setUser(loggedUser);
		estadoPedido.setObservacao("Em análise");

		pedidoService.createEstadoPedido(estadoPedido);

		return new ResponseEntity(new ApiResponse(true, "" + pedido.getId()), HttpStatus.CREATED);

	}

	int searchForRepeatedElements(List<PedidosHasProduto> pedidoHasProdutoList, Long compareItem) {
		for (int i = 0; i < pedidoHasProdutoList.size(); i++) {

			if (pedidoHasProdutoList.get(i).getProduto().getId() == compareItem) {
//				System.out.println("PRODUTO REPETIDO");
				return i;
			}

		}
		return -1;
	}

	@PostMapping(path = "/adicionarObservacaoPedido/pedido/{pedido}", consumes = "application/json")
	ResponseEntity adicionarNotaObservacaoPedido(@PathVariable(name = "pedido", required = true) Long pedidoNumero,
			@RequestBody PedidoObservacaoRequest observacao) {

//		System.out.println(observacao.getEntregadorId());
//		
//		if(0 == 0)
//			return null;
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		User loggedUser = new User();
		loggedUser.setId(Long.parseLong(auth.getName()));

		EstadoPedido estadoPedido = new EstadoPedido();
		Pedido pedidoNovo = new Pedido();
		pedidoNovo.setId(pedidoNumero);
		estadoPedido.setPedido(pedidoNovo);
		estadoPedido.setUser(loggedUser);
		estadoPedido.setObservacao(observacao.getObservacao());
		
		if (observacao.getEstado() < 20) {
			estadoPedido.setCurrestado(observacao.getEstado());
		} 

		pedidoService.createEstadoPedido(estadoPedido);

		if (estadoPedido.getCurrestado() == 1) {

			pedidoNovo.setIsAprovado((byte) 1);
			pedidoService.setPedidoAprovado(pedidoNovo.getIsAprovado(), pedidoNovo.getId());

		} else if (estadoPedido.getCurrestado() == 2) {

			
			EntregadorPedido entregadorPedido = new EntregadorPedido();
			entregadorPedido.setEstadoPedido(estadoPedido);
			User entregadorUser = new User();
			entregadorUser.setId(observacao.getEntregadorId());
			entregadorPedido.setUser(entregadorUser);
			entregadorPedido.setCurrestado(0);
			entregadorPedido.setDataAdicionado(new Timestamp(new Date().getTime()));
			pedidoService.saveEntregadorPedido(entregadorPedido);
			pedidoNovo.setIsTransporte((byte) 1);
			pedidoService.setPedidoTransporte(pedidoNovo.getIsTransporte(), pedidoNovo.getId());

		} else if (estadoPedido.getCurrestado() == 3) {

			pedidoNovo.setIsEntregue((byte) 1);
			pedidoService.setPedidoEntregue(pedidoNovo.getIsEntregue(), pedidoNovo.getId());

		} else if (estadoPedido.getCurrestado() == 4) {

			pedidoNovo.setIsFinalizado((byte) 1);
			pedidoService.setPedidoFinalizado(pedidoNovo.getIsFinalizado(), pedidoNovo.getId());

		} else if (estadoPedido.getCurrestado() == 99) {

			pedidoNovo.setIsFinalizado((byte) 1);
			pedidoService.setPedidoCancelado(pedidoNovo.getIsCancelado(), pedidoNovo.getId());

		}
		
		if (observacao.getEstado() > 20) {
			pedidoNovo.setClienteReclamouEstado(observacao.getEstado());
			if(observacao.getEstado() == 4) {
				
				EntregadorPedido entregadorPedido = new EntregadorPedido();
				entregadorPedido.setEstadoPedido(estadoPedido);
				User entregadorUser = new User();
				entregadorUser.setId(observacao.getEntregadorId());
				entregadorPedido.setUser(entregadorUser);
				entregadorPedido.setCurrestado(1);
				entregadorPedido.setDataAdicionado((Timestamp) new Date());
				pedidoService.saveEntregadorPedido(entregadorPedido);
				
			}
			pedidoService.setClienteReclamouEstado(pedidoNovo.getClienteReclamouEstado() - 20, pedidoNovo.getId());
		}

		return new ResponseEntity(new ApiResponse(true, "Pedido efetuado com sucesso!"), HttpStatus.CREATED);

	}
	
//	@PostMapping(path = "/produto/add")
//	ResponseEntity<?> registrarProduto(@RequestParam("file") MultipartFile file, 
//			@RequestParam("nome") String nome, 
//			@RequestParam("descricao") String descricao,
//			@RequestParam("preco") BigDecimal preco) throws IOException{
	
	@PostMapping(path = "/adicionarObservacaoPedidoCliente/pedido/{pedido}")
	ResponseEntity adicionarObservacaoPedido(@RequestParam("file") MultipartFile file, 
			@RequestParam("estado") int estado, 
			@RequestParam("observacao") String observacao,
//			@RequestParam("entregadorId") Long atribuidoAEntregador,
			@PathVariable(name = "pedido", required = true) Long pedidoNumero) throws IOException {

		
		
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		User loggedUser = new User();
		loggedUser.setId(Long.parseLong(auth.getName()));

		EstadoPedido estadoPedido = new EstadoPedido();
		Pedido pedidoNovo = new Pedido();
		pedidoNovo.setId(pedidoNumero);
		estadoPedido.setPedido(pedidoNovo);
		estadoPedido.setUser(loggedUser);
		estadoPedido.setObservacao(observacao);
		estadoPedido.setCurrestado(estado);

		estadoPedido.setIncludesPhoto((byte) 0);
		pedidoService.createEstadoPedido(estadoPedido);

		//Pedido aprovado
		if (estadoPedido.getCurrestado() == 1) {

			pedidoNovo.setIsAprovado((byte) 1);
			pedidoService.setPedidoAprovado(pedidoNovo.getIsAprovado(), pedidoNovo.getId());

			//Pedido em transporte
		} else if (estadoPedido.getCurrestado() == 2) {

			
			pedidoNovo.setIsTransporte((byte) 1);
			pedidoService.setPedidoTransporte(pedidoNovo.getIsTransporte(), pedidoNovo.getId());

		} else if (estadoPedido.getCurrestado() == 3) {

			pedidoNovo.setIsEntregue((byte) 1);
			pedidoService.setPedidoEntregue(pedidoNovo.getIsEntregue(), pedidoNovo.getId());

		} else if (estadoPedido.getCurrestado() == 4) {

			pedidoNovo.setIsFinalizado((byte) 1);
			pedidoService.setPedidoFinalizado(pedidoNovo.getIsFinalizado(), pedidoNovo.getId());

		} else if (estadoPedido.getCurrestado() == 5) {

			pedidoNovo.setClienteReclamouEstado(1);

			estadoPedido.setIncludesPhoto((byte) 1);
			pedidoService.createEstadoPedido(estadoPedido);
			
			pedidoService.setClienteReclamouEstado(pedidoNovo.getClienteReclamouEstado(), pedidoNovo.getId());
			
			ObservacaoEstadoPedido observacaoEstadoPedido = new ObservacaoEstadoPedido();
			observacaoEstadoPedido.setEstadoPedido(estadoPedido);
			observacaoEstadoPedido.setData(file.getBytes());
			
			pedidoService.saveObservacaoEstadoPedido(observacaoEstadoPedido);

		} else if (estadoPedido.getCurrestado() == 99) {

			pedidoNovo.setIsFinalizado((byte) 1);
			pedidoService.setPedidoCancelado(pedidoNovo.getIsCancelado(), pedidoNovo.getId());

		}

		return new ResponseEntity(new ApiResponse(true, "Pedido efetuado com sucesso!"), HttpStatus.CREATED);

	}

	@GetMapping(path = "/pedidos/page/{pageNumber}")
	public Page<EstadoPedido> listarPedidos(@PathVariable(name = "pageNumber", required = false) int pageNumber) {

		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		User loggedUser = new User();
		loggedUser.setId(Long.parseLong(auth.getName()));

		boolean isAdmin = auth.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_ADMIN"));

		boolean isBase = auth.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_BASE"));

		boolean isVendedor = auth.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_VENDEDOR"));

		boolean isCliente = auth.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_CLIENTE"));
		
		boolean isEntregador = auth.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_ENTREGADOR"));

		if (isAdmin || isBase) {
			return pedidoService.listarPedidos(pageNumber);
		}

		if (isVendedor) {
//			System.out.println("ESTÀ A VIR AQUI");
			return pedidoService.listarPedidosVendedor(loggedUser.getId(), pageNumber);
		}
		if (isCliente) {
			return pedidoService.listarPedidosCliente(loggedUser.getId(), pageNumber);
		}
		
		if(isEntregador) {
			return pedidoService.listarPedidosEntregador(loggedUser.getId(), pageNumber);
		}

		return null;

	}

	@GetMapping(path = "/pedidosPendentes/page/{pageNumber}")
	public Page<EstadoPedido> listarPedidosPendentes(
			@PathVariable(name = "pageNumber", required = false) int pageNumber) {

		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		User loggedUser = new User();
		loggedUser.setId(Long.parseLong(auth.getName()));

		return pedidoService.listarPedidosPendentes(pageNumber);

	}

	@GetMapping(path = "/pedidosNaoAprovadosVFuncionario/page/{pageNumber}")
	public Page<EstadoPedido> listarPedidosPendentesVFuncionario(
			@PathVariable(name = "pageNumber", required = false) int pageNumber) {

		Authentication auth = SecurityContextHolder.getContext().getAuthentication();

		boolean isAdmin = auth.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_ADMIN"));

		boolean isBase = auth.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_BASE"));

		if (!isAdmin || isBase) {

			return pedidoService.listarPedidosNaoAprovadosVFuncionario(pageNumber);
		} else {
			return null;
		}

	}

	@GetMapping(path = "/pedidosNaoAprovadosVCliente/page/{pageNumber}")
	public Page<EstadoPedido> listarPedidosPendentesVCliente(
			@PathVariable(name = "pageNumber", required = false) int pageNumber) {

		Authentication auth = SecurityContextHolder.getContext().getAuthentication();

		User loggedUser = new User();
		loggedUser.setId(Long.parseLong(auth.getName()));

		return pedidoService.listarPedidosPendentesVCliente(loggedUser.getId(), pageNumber);

	}

	@GetMapping(path = "/observacoesPedidoCliente/pedido/{pedido}")
	public List<EstadoPedido> findPedidoObservacoes(@PathVariable(name = "pedido", required = true) Long pedido) {

		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		User loggedUser = new User();
		loggedUser.setId(Long.parseLong(auth.getName()));

		return pedidoService.findObservacoesPedidoByIdPedidoAndIdCliente(loggedUser.getId(), pedido);

	}

	@GetMapping(path = "/pedidoCliente/pedido/{pedido}")
	public PedidoAndEstadoPedidoCustom findPedido(@PathVariable(name = "pedido", required = true) Long pedido) {

		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		User loggedUser = new User();
		loggedUser.setId(Long.parseLong(auth.getName()));

		List<PedidosHasProduto> pedidosHasProdutoList = pedidoService
				.findPedidoDetalhesByIdPedidoAndIdClienteReturnUUID(loggedUser.getId(), pedido);

		List<EstadoPedido> estadoPedidoList = pedidoService
				.findObservacoesPedidoByIdPedidoAndIdCliente(loggedUser.getId(), pedido);
		
		List<ObservacaoEstadoPedido> observacaoEstadoPedidoList = new ArrayList<>();
		if(estadoPedidoList.get(estadoPedidoList.size()-1).getClienteReclamouEstado() > 0) {
			observacaoEstadoPedidoList = pedidoService
					.findObservacaoEstadoPedidoByIdPedido(pedido);
		}

		PedidoAndEstadoPedidoCustom pedidoCustom = new PedidoAndEstadoPedidoCustom(pedidosHasProdutoList,
				estadoPedidoList, observacaoEstadoPedidoList);

		return pedidoCustom;

	}

	@GetMapping(path = "/pedidoFuncionario/pedido/{pedido}")
	public PedidoAndEstadoPedidoCustom findPedidoFuncionario(
			@PathVariable(name = "pedido", required = true) Long pedido) {

		Authentication auth = SecurityContextHolder.getContext().getAuthentication();

		boolean isAdmin = auth.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_ADMIN"));

		boolean isBase = auth.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_BASE"));

		boolean isVendedor = auth.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_VENDEDOR"));
		
		boolean isEntregador = auth.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_ENTREGADOR"));

		List<ObservacaoEstadoPedido> observacaoEstadoPedidoList = new ArrayList<>();
		if (isVendedor) {

			User loggedUser = new User();
			loggedUser.setId(Long.parseLong(auth.getName()));

			List<PedidosHasProduto> pedidosHasProdutoList = pedidoService
					.findPedidoDetalhesByIdPedidoAndIdFuncionarioReturnUUID(loggedUser.getId(), pedido);

			if (pedidosHasProdutoList.isEmpty()) {
				return null;
			}

			List<EstadoPedido> estadoPedidoList = pedidoService.findObservacoesPedidoByIdPedido(pedido);

			if(estadoPedidoList.get(estadoPedidoList.size()-1).getClienteReclamouEstado() > 0) {
				observacaoEstadoPedidoList = pedidoService
						.findObservacaoEstadoPedidoByIdPedido(pedido);
			}
			PedidoAndEstadoPedidoCustom pedidoCustom = new PedidoAndEstadoPedidoCustom(pedidosHasProdutoList,
					estadoPedidoList, observacaoEstadoPedidoList);

			return pedidoCustom;

		} else if (isBase || isAdmin) {

			List<PedidosHasProduto> pedidosHasProdutoList = pedidoService
					.findPedidoDetalhesByIdPedidoReturnUUID(pedido);

			List<EstadoPedido> estadoPedidoList = pedidoService.findObservacoesPedidoByIdPedido(pedido);
			
			if(estadoPedidoList.get(estadoPedidoList.size()-1).getClienteReclamouEstado() > 0) {
				observacaoEstadoPedidoList = pedidoService
						.findObservacaoEstadoPedidoByIdPedido(pedido);
			}

			PedidoAndEstadoPedidoCustom pedidoCustom = new PedidoAndEstadoPedidoCustom(pedidosHasProdutoList,
					estadoPedidoList, observacaoEstadoPedidoList);

			return pedidoCustom;

		} if (isEntregador) {

			List<EstadoPedido> estadoPedidoList = pedidoService.findObservacoesPedidoByIdPedido(pedido);
			
			List<PedidosHasProduto> pedidosHasProdutoList = pedidoService
					.findPedidoDetalhesByIdPedidoReturnUUID(pedido);
			
			if(estadoPedidoList.get(estadoPedidoList.size()-1).getClienteReclamouEstado() > 0) {
				observacaoEstadoPedidoList = pedidoService
						.findObservacaoEstadoPedidoByIdPedido(pedido);
			}

			PedidoAndEstadoPedidoCustom pedidoCustom = new PedidoAndEstadoPedidoCustom(pedidosHasProdutoList,
					estadoPedidoList, observacaoEstadoPedidoList);

			return pedidoCustom;
			
		}
			else {
			return null;
		}

//		User loggedUser = new User();
//		loggedUser.setId(Long.parseLong(auth.getName()));
//
//		List<PedidosHasProduto> pedidosHasProdutoList = pedidoService
//				.findPedidoDetalhesByIdPedidoAndIdClienteReturnUUID(loggedUser.getId(), pedido);
//
//		List<EstadoPedido> estadoPedidoList = pedidoService
//				.findObservacoesPedidoByIdPedidoAndIdCliente(loggedUser.getId(), pedido);
//
//		PedidoAndEstadoPedidoCustom pedidoCustom = new PedidoAndEstadoPedidoCustom(pedidosHasProdutoList,
//				estadoPedidoList);
////        System.out.println("Esteve aqui?");
//
//		return pedidoCustom;

	}

}
