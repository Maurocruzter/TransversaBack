package br.transversa.backend.controller;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
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
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import br.transversa.backend.model.Role;
import br.transversa.backend.model.User;
import br.transversa.backend.model.UserHasRole;
import br.transversa.backend.payload.ApiResponse;
import br.transversa.backend.security.JwtTokenProvider;
import br.transversa.backend.service.RoleService;
import br.transversa.backend.service.UserHasRoleService;
import br.transversa.backend.service.UserService;
import br.transversa.backend.util.AppConstants;

@RestController
@RequestMapping("/api/v1")
public class UserController {

	@Autowired
	UserHasRoleService userHasRoleService;

	@Autowired
	JwtTokenProvider tokenProvider;

	@Autowired
	AuthenticationManager authenticationManager;

	@Autowired
	PasswordEncoder passwordEncoder;

	@Autowired
	UserService userService;

	@Autowired
	RoleService roleService;

	@RequestMapping(value = "/user/alterarSenha", method = RequestMethod.POST)
	ResponseEntity<?> alterarSenha(@RequestBody String senha) throws IOException {

		senha = senha.substring(10, senha.length() - 2);
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();

		User loggedUser = new User();
		loggedUser.setId(Long.parseLong(auth.getName()));

		loggedUser.setSenha(passwordEncoder.encode(senha));

		userService.ChangePassword(loggedUser.getSenha(), loggedUser.getId());

		return new ResponseEntity(new ApiResponse(true, "Produto adicionado com sucesso"), HttpStatus.CREATED);

	}
	
	@PostMapping(path = "/user/ajustarComissao/vendedor/{id}/comissao/{comissao}")
	ResponseEntity<?> carregarFotoEstabelecimento(
			@PathVariable(name = "id") Long id,
			@PathVariable(name = "comissao") BigDecimal comissao) {
		
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();

		if (auth.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_ADMIN"))
				|| auth.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_BASE"))) {
		
			userService.ChangeComissao(id, comissao);
			return new ResponseEntity(new ApiResponse(true, "Produto adicionado com sucesso"), HttpStatus.CREATED); 
		}
		
		return new ResponseEntity(new ApiResponse(true, "Produto adicionado com sucesso"), HttpStatus.FORBIDDEN); 
		
        		
	}

	@RequestMapping(value = "/userBulk/add", method = RequestMethod.POST)
	ResponseEntity<?> registrarVariosProdutos(
			@RequestParam(name = "fotoLocal", required = false) List<MultipartFile> fotoLocal,
			@RequestParam(name = "fotoDocumento", required = false) List<MultipartFile> fotoDocumento,

			@RequestParam(name = "cpf", required = true) List<String> cpf,
			@RequestParam(name = "celular", required = false) List<String> celular,
			@RequestParam("email") List<String> email, @RequestParam(name = "fixo", required = false) List<String> fixo,
			@RequestParam("nome") List<String> nome, @RequestParam("sobrenome") List<String> sobrenome,
			@RequestParam("senha") List<String> senha, @RequestParam("whatsapp") List<String> whatsapp,

			@RequestParam(name = "bairro", required = false) List<String> bairro,

			@RequestParam(name = "cep", required = false) List<String> cep,
			@RequestParam(name = "cidade", required = false) List<String> cidade,
			@RequestParam(name = "cnpj", required = false) List<String> cnpj,
			@RequestParam(name = "casaNumero", required = false) List<String> casaNumero,

			@RequestParam(name = "latitude", required = false) List<Double> latitude,
			@RequestParam(name = "longitude", required = false) List<Double> longitude,
			@RequestParam(name = "logradouro", required = false) List<String> logradouro,

			@RequestParam(name = "observacao", required = false) List<String> observacao,
			@RequestParam(name = "pontoReferencia1", required = false) List<String> pontoReferencia1,
			@RequestParam(name = "pontoReferencia2", required = false) List<String> pontoReferencia2,
			@RequestParam(name = "inscricaoEstadual", required = false) List<String> inscricaoEstadual,
			@RequestParam("roles") String roles,
			@RequestParam(name = "tipoEstabelecimento", required = false) List<String> tipoEstabelecimento,

			@RequestParam(name = "comissao", required = false) List<String> comissao,

			@RequestParam(name = "assignedTo", required = false) List<Long> assignedTo) throws IOException {

			for (int l = 0; l < cpf.size(); l++) {

			if (userService.existsByEmail(email.get(l))) {
				return new ResponseEntity(new ApiResponse(false, "Email Address already in use!"),
						HttpStatus.BAD_REQUEST);
			}

			List<UserHasRole> userHasRoles = new ArrayList<>();

			Long id = new Long(1 + 1);

			boolean isCadastrarCliente = false;
			boolean isCadastrarVendedor = false;
			boolean isVendedor = false;

			String[] str;
			str = roles.split(",");
			int index;

			Authentication auth = SecurityContextHolder.getContext().getAuthentication();

			if (auth.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_ADMIN"))
					|| auth.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_BASE"))) {

				for (int i = 0; i < str.length; i++) {

					index = AppConstants.ROLES.indexOf(str[i].trim());
					if (index < 0) {
						return new ResponseEntity(new ApiResponse(false, "O role " + str[i].trim() + " não existe"),
								HttpStatus.BAD_REQUEST);
					}
					if (str[i].equalsIgnoreCase("ROLE_CLIENTE")) {
						isCadastrarCliente = true;
					}

					if (str[i].equalsIgnoreCase("ROLE_VENDEDOR")) {
						isCadastrarVendedor = true;
					}

					Role role = new Role();
					id = new Long(index + 1);
					role.setId(id);
					UserHasRole userHasRole = new UserHasRole();
					userHasRole.setRole(role);
					userHasRoles.add(userHasRole);

				}

			} else if (auth.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_VENDEDOR"))) {
				isCadastrarCliente = true;
				isVendedor = true;
				index = AppConstants.ROLES.indexOf("ROLE_CLIENTE");
				Role role = new Role();
				role = new Role();
				id = new Long(index + 1);
				role.setId(id);
				UserHasRole userHasRole = new UserHasRole();
				userHasRole.setRole(role);
				userHasRoles.add(userHasRole);

			}

			User user = new User();
			user.setNome(nome.get(l));
			user.setSobrenome(sobrenome.get(l));
			user.setEmail(email.get(l));
			user.setCpf(cpf.get(l));
			if (celular == null || celular.isEmpty()) {
				user.setCelular(null);
			} else {
				user.setCelular(celular.get(l));
			}

			if (fixo == null || fixo.isEmpty()) {
				user.setFixo(null);
			} else {
				user.setFixo(fixo.get(l));
			}

			user.setWhatsapp(whatsapp.get(l));

			user.setAtivo((byte) 1);

			User loggedUser = new User();
			loggedUser.setId(Long.parseLong(auth.getName()));
			if (isCadastrarCliente) {

				user.setLatitude(latitude.get(l));
				user.setLongitude(longitude.get(l));
				user.setLogradouro(logradouro.get(l));
				UUID uuid = UUID.randomUUID();
				user.setUuid(uuid.toString());
				user.setFotoDocumento(fotoDocumento.get(l).getBytes());
				user.setFotoEstabelecimento(fotoLocal.get(l).getBytes());
				user.setFileType(fotoDocumento.get(l).getContentType());
				user.setBairro(bairro.get(l));
				user.setCasaNumero(casaNumero.get(l));

				user.setCep(cep.get(l));

				user.setCidade(cidade.get(l));
				user.setCnpj(cnpj.get(l));
				user.setInscricaoEstadual(inscricaoEstadual.get(l));
				user.setObservacao(observacao.get(l));

				user.setPontoReferencia1(pontoReferencia1.get(l));
				if (pontoReferencia2 == null || pontoReferencia2.isEmpty()) {

					user.setPontoReferencia2(null);
				} else {
					user.setPontoReferencia2(pontoReferencia2.get(l));
				}

				user.setTipoEstabelecimento(AppConstants.TIPO_ESTABELECIMENTOS.indexOf(tipoEstabelecimento.get(l)));

				// Placing assignedUser
				if (isVendedor) {
					user.setUser2(loggedUser);
				} else {
					User userAux = new User();
					userAux.setId(assignedTo.get(l));
					user.setUser2(userAux);
				}

			}

			if (isCadastrarVendedor) {
				user.setComissao(new BigDecimal(comissao.get(l)));
			}

			// Placing createdBy
			user.setUser1(loggedUser);
//	        user.setSenha(user.getCpfCnpj());
			user.setSenha(passwordEncoder.encode(user.getCpf()));

			User result = userService.save(user);

			User userAux;
			for (int i = 0; i < userHasRoles.size(); i++) {
				userAux = new User();
				userAux.setId(result.getId());
				userHasRoles.get(i).setUser(userAux);

				userHasRoleService.save(userHasRoles.get(i));
			}

		}

		return new ResponseEntity(new ApiResponse(true, "Produto adicionado com sucesso"), HttpStatus.CREATED);

	}
	
	@GetMapping(path = "/user/loadFotoEstabelecimento/{uuid}")
	ResponseEntity<Resource> carregarFotoEstabelecimento(@PathVariable(name = "uuid", required = false) String uuid) {
		
//		return null;
		User user = userService.findUserFotoEstabelecimentoByUuid(uuid);
		
		if(user == null || user.getFotoEstabelecimento() == null)
			return null;
		
		return ResponseEntity.ok()
        .contentType(MediaType.parseMediaType(user.getFileType()))
        .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + "file" + "\"")
        .body(new ByteArrayResource(user.getFotoEstabelecimento()));
        		
	}
	
	@GetMapping(path = "/user/loadFotoDocumento/{uuid}")
	ResponseEntity<Resource> carregarFotoDocumento(@PathVariable(name = "uuid", required = false) String uuid) {
		
//		return null;
		User user = userService.findUserFotoDocumentoByUUID(uuid);
		
		if(user == null || user.getFotoDocumento() == null)
			return null;
		
		return ResponseEntity.ok()
        .contentType(MediaType.parseMediaType(user.getFileType()))
        .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + "File" + "\"")
        .body(new ByteArrayResource(user.getFotoDocumento()));
        		
	}
	

	@RequestMapping(value = "/user/add", method = RequestMethod.POST)
	ResponseEntity<?> registrarProduto(@RequestParam(name = "fotoLocal", required = false) MultipartFile fotoLocal,
			@RequestParam(name = "fotoDocumento", required = false) MultipartFile fotoDocumento,

			@RequestParam(name = "cpf", required = true) String cpf,
			@RequestParam(name = "celular", required = false) String celular, @RequestParam("email") String email,
			@RequestParam(name = "fixo", required = false) String fixo, @RequestParam("nome") String nome,
			@RequestParam("sobrenome") String sobrenome, @RequestParam("senha") String senha,
			@RequestParam("whatsapp") String whatsapp,

			@RequestParam(name = "bairro", required = false) String bairro,

			@RequestParam(name = "cep", required = false) String cep,
			@RequestParam(name = "cidade", required = false) String cidade,
			@RequestParam(name = "cnpj", required = false) String cnpj,
			@RequestParam(name = "casaNumero", required = false) String casaNumero,

			@RequestParam(name = "latitude", required = false) Double latitude,
			@RequestParam(name = "longitude", required = false) Double longitude,
			@RequestParam(name = "logradouro", required = false) String logradouro,

			@RequestParam(name = "observacao", required = false) String observacao,
			@RequestParam(name = "pontoReferencia1", required = false) String pontoReferencia1,
			@RequestParam(name = "pontoReferencia2", required = false) String pontoReferencia2,
			@RequestParam(name = "inscricaoEstadual", required = false) String inscricaoEstadual,
			@RequestParam("roles") String roles,
			@RequestParam(name = "tipoEstabelecimento", required = false) String tipoEstabelecimento,

			@RequestParam(name = "comissao", required = false) String comissao,

			@RequestParam(name = "assignedTo", required = false) Long assignedTo) throws IOException {

		if (userService.existsByEmail(email)) {
			return new ResponseEntity(new ApiResponse(false, "Email Address already in use!"), HttpStatus.BAD_REQUEST);
		}

		List<UserHasRole> userHasRoles = new ArrayList<>();

		Long id = new Long(1 + 1);

		boolean isCadastrarCliente = false;
		boolean isCadastrarVendedor = false;
		boolean isVendedor = false;

		String[] str;
		str = roles.split(",");
		int index;

		Authentication auth = SecurityContextHolder.getContext().getAuthentication();

		if (auth.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_ADMIN"))
				|| auth.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_BASE"))) {

			for (int i = 0; i < str.length; i++) {

				index = AppConstants.ROLES.indexOf(str[i].trim());
				if (index < 0) {
					return new ResponseEntity(new ApiResponse(false, "O role " + str[i].trim() + " não existe"),
							HttpStatus.BAD_REQUEST);
				}
				if (str[i].equalsIgnoreCase("ROLE_CLIENTE")) {
					isCadastrarCliente = true;
				}

				if (str[i].equalsIgnoreCase("ROLE_VENDEDOR")) {
					isCadastrarVendedor = true;
				}

				Role role = new Role();
				id = new Long(index + 1);
				role.setId(id);
				UserHasRole userHasRole = new UserHasRole();
				userHasRole.setRole(role);
				userHasRoles.add(userHasRole);

			}

		} else if (auth.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_VENDEDOR"))) {
			isCadastrarCliente = true;
			isVendedor = true;
			index = AppConstants.ROLES.indexOf("ROLE_CLIENTE");
			Role role = new Role();
			role = new Role();
			id = new Long(index + 1);
			role.setId(id);
			UserHasRole userHasRole = new UserHasRole();
			userHasRole.setRole(role);
			userHasRoles.add(userHasRole);

		}

		User user = new User();
		user.setNome(nome);
		user.setSobrenome(sobrenome);
		user.setEmail(email);
		user.setCpf(cpf);
		if (celular == null || celular.isEmpty()) {
			user.setCelular(null);
		} else {
			user.setCelular(celular);
		}

		if (fixo == null || fixo.isEmpty()) {
			user.setFixo(null);
		} else {
			user.setFixo(fixo);
		}

		user.setWhatsapp(whatsapp);

		user.setAtivo((byte) 1);

		User loggedUser = new User();
		loggedUser.setId(Long.parseLong(auth.getName()));
		if (isCadastrarCliente) {

			user.setLatitude(latitude);
			user.setLongitude(longitude);
			user.setLogradouro(logradouro);
			UUID uuid = UUID.randomUUID();
			user.setUuid(uuid.toString());
			user.setFotoDocumento(fotoDocumento.getBytes());
			user.setFotoEstabelecimento(fotoLocal.getBytes());
			user.setFileType(fotoDocumento.getContentType());
			user.setBairro(bairro);
			user.setCasaNumero(casaNumero);

			user.setCep(cep);

			user.setCidade(cidade);
			user.setCnpj(cnpj);
			user.setInscricaoEstadual(inscricaoEstadual);
			user.setObservacao(observacao);

			user.setPontoReferencia1(pontoReferencia1);
			if (pontoReferencia2 == null || pontoReferencia2.isEmpty()) {

				user.setPontoReferencia2(null);
			} else {
				user.setPontoReferencia2(pontoReferencia2);
			}

			user.setTipoEstabelecimento(AppConstants.TIPO_ESTABELECIMENTOS.indexOf(tipoEstabelecimento));

			// Placing assignedUser
			if (isVendedor) {
				user.setUser2(loggedUser);
			} else {
				User userAux = new User();
				userAux.setId(assignedTo);
				user.setUser2(userAux);
			}

		}

		if (isCadastrarVendedor) {
			user.setComissao(new BigDecimal(comissao));
		}

		// Placing createdBy
		user.setUser1(loggedUser);
//        user.setSenha(user.getCpfCnpj());
		user.setSenha(passwordEncoder.encode(user.getCpf()));

		User result = userService.save(user);

		User userAux;
		for (int i = 0; i < userHasRoles.size(); i++) {
			userAux = new User();
			userAux.setId(result.getId());
			userHasRoles.get(i).setUser(userAux);

			userHasRoleService.save(userHasRoles.get(i));
		}

		return new ResponseEntity(new ApiResponse(true, "Produto adicionado com sucesso"), HttpStatus.CREATED);

	}
	
	@GetMapping(path = "/user/id/{id}")
	User searchUserById(@PathVariable(name = "id", required = true) Long id) {

//		return null;
		
		Optional<User> aux = userService.findUserById(id);
		
		if(aux.isPresent()) {
			return aux.get();
		}

		return null;
		// return produtoService.findProdutoById(nome);

	}

	@GetMapping(path = "/user/searchNome/{nome}/page/{pageNumber}")
	Page<User> searchProduto(@PathVariable(name = "nome", required = true) String nome,
			@PathVariable(name = "pageNumber", required = true) int pageNumber) {

//		return null;

		return userService.findUserSearchNome(nome, pageNumber);
		// return produtoService.findProdutoById(nome);

	}
	
	
	@GetMapping(path = "/user/search/nome/{nome}/sobrenome/{sobrenome}/cpf/{cpf}/cnpj/{cnpj}/perfil/{perfil}/page/{pageNumber}")
	Page<User> searchUserByFields(
			@PathVariable(name = "nome", required = true) String nome,
			@PathVariable(name = "sobrenome", required = true) String sobrenome,
			@PathVariable(name = "cpf", required = true) String cpf,
			@PathVariable(name = "cnpj", required = true) String cnpj,
			@PathVariable(name = "perfil", required = true) int perfil,
			@PathVariable(name = "pageNumber", required = true) int pageNumber) {

		Authentication auth = SecurityContextHolder.getContext().getAuthentication();

		if (auth.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_ADMIN")) ||
				auth.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_BASE"))) {

			return userService.findUserFilterFields(nome, sobrenome, cpf, cnpj, perfil, pageNumber);
		}
		else {
			return null;
		}
		
		

	}
	

	@GetMapping(path = "/listUsers/page/{pageNumber}")
	Page<User> listUsersByPage(@PathVariable(name = "pageNumber", required = false) int pageNumber) {

		return userService.findAllUserByPage(pageNumber);
	}

	@GetMapping(path = "/listClientesVendedor/page/{pageNumber}")
	Page<User> listClientesVendedorByPage(@PathVariable(name = "pageNumber", required = false) int pageNumber) {

		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		return userService.findClientesVendedorByPage(pageNumber, Long.parseLong(auth.getName()));
	}

	@GetMapping(path = "/listUsersByRole/{role}")
	List<UserHasRole> listUsersByRole(@PathVariable(name = "role", required = false) Long role) {
		return userService.listUsersByRole(role);
	}

	@GetMapping(path = "/listAllUsers")
	List<User> listAllUsers() {

		return userService.findAllUsers();
	}

	@GetMapping(path = "/listAllVendedores")
	List<UserHasRole> listAllVendedores() {

		return userService.findAllVendedores();
	}

	@GetMapping(path = "/listAllClientesDoUser")
	List<User> listAllClientesDoUser() {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();

		if (auth.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_ADMIN"))) {
			return userHasRoleService.listAllUsersWithRole(new Long(AppConstants.ROLES.indexOf("ROLE_CLIENTE") + 1));
		}

		return userService.listAllClientesDoUser(Long.parseLong(auth.getName()));
	}

	@GetMapping(path = "/listAllClientesDoUserVOffline")
	List<User> listAllClientesDoUserVOffline() {

		System.out.println("Esteve aqui");
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();

		if (auth.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_VENDEDOR"))) {

			return userService.listAllClientesDoUserVoffline(Long.parseLong(auth.getName()));
		}

		return null;

	}

}
