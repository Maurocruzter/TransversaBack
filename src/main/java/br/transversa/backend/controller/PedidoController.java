package br.transversa.backend.controller;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.transversa.backend.model.Carrinho;
import br.transversa.backend.model.EstadoPedido;
import br.transversa.backend.model.Pedido;
import br.transversa.backend.model.PedidosHasProduto;
import br.transversa.backend.model.Produto;
import br.transversa.backend.model.User;
import br.transversa.backend.payload.ApiResponse;
import br.transversa.backend.payload.ListNovoPedidoRequest;
import br.transversa.backend.payload.LoginRequest;
import br.transversa.backend.payload.PedidoAndEstadoPedidoCustom;
import br.transversa.backend.payload.PedidoObservacaoRequest;
import br.transversa.backend.service.CarrinhoService;
import br.transversa.backend.service.PedidoService;
import br.transversa.backend.service.ProdutoService;
import br.transversa.backend.service.UserService;

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

	@PostMapping(path = "/pedidoFuncionario/novo")
	ResponseEntity fazerPedidoComoFuncionario(@RequestBody ListNovoPedidoRequest listNovoPedidoRequest) {

		System.out.println("cliente " + listNovoPedidoRequest.getCliente());
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

		return new ResponseEntity(new ApiResponse(true, "Pedido efetuado com sucesso!"), HttpStatus.CREATED);

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

		return new ResponseEntity(new ApiResponse(true, "Pedido efetuado com sucesso!"), HttpStatus.CREATED);

	}

	int searchForRepeatedElements(List<PedidosHasProduto> pedidoHasProdutoList, Long compareItem) {
		for (int i = 0; i < pedidoHasProdutoList.size(); i++) {

			if (pedidoHasProdutoList.get(i).getProduto().getId() == compareItem) {
				System.out.println("PRODUTO REPETIDO");
				return i;
			}

		}
		return -1;
	}

	@PostMapping(path = "/adicionarNotaPedido/pedido/{pedido}", consumes = "application/json")
	ResponseEntity adicionarNotaPedido(@PathVariable(name = "pedido", required = true) Long pedidoNumero,
			@RequestBody PedidoObservacaoRequest observacao) {

		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		User loggedUser = new User();
		loggedUser.setId(Long.parseLong(auth.getName()));

		EstadoPedido estadoPedido = new EstadoPedido();
		Pedido pedidoNovo = new Pedido();
		pedidoNovo.setId(pedidoNumero);
		estadoPedido.setPedido(pedidoNovo);
		estadoPedido.setUser(loggedUser);
		estadoPedido.setObservacao(observacao.getObservacao());

		pedidoService.createEstadoPedido(estadoPedido);

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
		
		if(isAdmin || isBase) {
			return pedidoService.listarPedidos(pageNumber);
		}
		
		if(isVendedor) {
			return pedidoService.listarPedidosVendedor(loggedUser.getId(), pageNumber);
		}
		if(isCliente) {
			return pedidoService.listarPedidosCliente(loggedUser.getId(), pageNumber);
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

		PedidoAndEstadoPedidoCustom pedidoCustom = new PedidoAndEstadoPedidoCustom(pedidosHasProdutoList,
				estadoPedidoList);
//        System.out.println("Esteve aqui?");

		return pedidoCustom;

	}

	@GetMapping(path = "/pedidoFuncionario/pedido/{pedido}")
	public PedidoAndEstadoPedidoCustom findPedidoFuncionario(
			@PathVariable(name = "pedido", required = true) Long pedido) {

		Authentication auth = SecurityContextHolder.getContext().getAuthentication();

		boolean isAdmin = auth.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_ADMIN"));

		boolean isBase = auth.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_BASE"));

		boolean isVendedor = auth.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_VENDEDOR"));

		if (isVendedor) {

			User loggedUser = new User();
			loggedUser.setId(Long.parseLong(auth.getName()));

			List<PedidosHasProduto> pedidosHasProdutoList = pedidoService
					.findPedidoDetalhesByIdPedidoAndIdFuncionarioReturnUUID(loggedUser.getId(), pedido);

			if (pedidosHasProdutoList.isEmpty()) {
				return null;
			}

			List<EstadoPedido> estadoPedidoList = pedidoService.findObservacoesPedidoByIdPedido(pedido);

			PedidoAndEstadoPedidoCustom pedidoCustom = new PedidoAndEstadoPedidoCustom(pedidosHasProdutoList,
					estadoPedidoList);

			return pedidoCustom;

		} else if (isBase || isAdmin) {
			
			List<PedidosHasProduto> pedidosHasProdutoList = pedidoService
					.findPedidoDetalhesByIdPedidoReturnUUID(pedido);

			List<EstadoPedido> estadoPedidoList = pedidoService.findObservacoesPedidoByIdPedido(pedido);

			PedidoAndEstadoPedidoCustom pedidoCustom = new PedidoAndEstadoPedidoCustom(pedidosHasProdutoList,
					estadoPedidoList);

			return pedidoCustom;
			
		} else {
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
