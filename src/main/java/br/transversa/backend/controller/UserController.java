package br.transversa.backend.controller;

import java.io.IOException;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Lob;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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
	ResponseEntity<?> alterarSenha( @RequestBody String senha) throws IOException{
		
		senha = senha.substring(10, senha.length()-2);
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		
		User loggedUser = new User();
        loggedUser.setId(Long.parseLong(auth.getName()));
		
//		@Modifying
//		@Query("UPDATE Pedido p set p.isEntregue = :isEntregue where p.id = :id")
//		int setPedidoEntregue(byte isEntregue, Long id);
        loggedUser.setSenha(passwordEncoder.encode(senha));
        
        System.out.println(loggedUser.getSenha());
		userService.ChangePassword(loggedUser.getSenha(), loggedUser.getId());

        return new ResponseEntity(new ApiResponse(true, "Produto adicionado com sucesso"),
                    HttpStatus.CREATED);

	}
	
	@RequestMapping(value = "/user/add", method = RequestMethod.POST)
	ResponseEntity<?> registrarProduto(
			@RequestParam(name="fotoLocal", required=false) MultipartFile fotoLocal, 
			@RequestParam(name="fotoDocumento", required=false) MultipartFile fotoDocumento,
			
			@RequestParam(name="cpf", required=true) String cpf, 
			@RequestParam(name="celular", required=false) String celular, 
			@RequestParam("email") String email,
			@RequestParam(name="fixo", required=false) String fixo, 
			@RequestParam("nome") String nome, 
			@RequestParam("sobrenome") String sobrenome,
			@RequestParam("senha") String senha,
			@RequestParam("whatsapp") String whatsapp,
			
			@RequestParam(name="bairro", required=false) String bairro, 
			
			@RequestParam(name="cep", required=false) String cep, 
			@RequestParam(name="cidade", required=false) String cidade,
			@RequestParam(name="cnpj", required=false) String cnpj, 
			@RequestParam(name="casaNumero", required=false) String casaNumero, 
			
			
			
			@RequestParam(name="latitude", required=false) Double latitude,
			@RequestParam(name="longitude", required=false) Double longitude,
			@RequestParam(name="logradouro", required=false) String logradouro,
			
			@RequestParam(name="observacao", required=false) String observacao,
			@RequestParam(name="pontoReferencia1", required=false) String pontoReferencia1,
			@RequestParam(name="pontoReferencia2", required=false) String pontoReferencia2,
			@RequestParam(name="inscricaoEstadual", required=false) String inscricaoEstadual,
			@RequestParam("roles") String roles,
			@RequestParam(name="tipoEstabelecimento", required=false) String tipoEstabelecimento,
			
			@RequestParam(name="comissao", required=false) String comissao,
			
			@RequestParam(name = "assignedTo", required=false) Long assignedTo
			) throws IOException{

		if(userService.existsByEmail(email)) {
            return new ResponseEntity(new ApiResponse(false, "Email Address already in use!"),
                    HttpStatus.BAD_REQUEST);
        }

		
		
		List<UserHasRole> userHasRoles = new ArrayList<>();

		Long id = new Long(1+1);
		
		boolean isCadastrarCliente = false;
		boolean isCadastrarVendedor = false;
		boolean isVendedor = false;
		
		String[] str;
		str = roles.split(",");
		int index;
		
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		

		if(auth.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_ADMIN")) || auth.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_BASE"))) {
			
			
			for(int i = 0; i< str.length ; i++) {
				
				index = AppConstants.ROLES.indexOf(str[i].trim());
				if(index <0) {
					return new ResponseEntity(new ApiResponse(false, "O role "+ str[i].trim() + " não existe"),
		                    HttpStatus.BAD_REQUEST);
				}
				if (str[i].equalsIgnoreCase("ROLE_CLIENTE")) {
					isCadastrarCliente = true;
				}
				
				if (str[i].equalsIgnoreCase("ROLE_VENDEDOR")) {
					isCadastrarVendedor = true;
				}
					
				Role role = new Role();
				id = new Long(index+1);
				role.setId(id);
				UserHasRole userHasRole = new UserHasRole();
				userHasRole.setRole(role);
				userHasRoles.add(userHasRole);
					
			}
			
		} else if(auth.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_VENDEDOR"))) {
			isCadastrarCliente = true;
			isVendedor = true;
			index = AppConstants.ROLES.indexOf("ROLE_CLIENTE");
			Role role = new Role();
			role = new Role();
			id = new Long(index+1);
			role.setId(id);
			UserHasRole userHasRole = new UserHasRole();
			userHasRole.setRole(role);
			userHasRoles.add(userHasRole);
			
		}		
		
		

		User user = new  User();
		user.setNome(nome);
		user.setSobrenome(sobrenome);
		user.setEmail(email);
		user.setCpf(cpf);
		if(celular == null || celular.isEmpty()) {
			user.setCelular(null);
		} else {
			user.setCelular(celular);
		}
		
		if(fixo == null || fixo.isEmpty()) {
			user.setFixo(null);
		} else {
			user.setFixo(fixo);
		}
		
		user.setWhatsapp(whatsapp);
		
		user.setAtivo((byte) 1);
		
		
        User loggedUser = new User();
        loggedUser.setId(Long.parseLong(auth.getName()));
		if(isCadastrarCliente) {
			
			System.out.println(casaNumero);
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
			if(pontoReferencia2 == null || pontoReferencia2.isEmpty()) {
				
				
				user.setPontoReferencia2(null);
			} else {
				user.setPontoReferencia2(pontoReferencia2);
			}
			
			user.setTipoEstabelecimento(AppConstants.TIPO_ESTABELECIMENTOS.indexOf(tipoEstabelecimento));
			
			
			//Placing assignedUser
			if(isVendedor) {
				user.setUser2(loggedUser);
			} else {
				User userAux = new User();
				userAux.setId(assignedTo);
				user.setUser2(userAux);
			}
			
		}
		
		if(isCadastrarVendedor) {
			user.setComissao(new BigDecimal(comissao) );
		}
		
		
        
        //Placing createdBy
        user.setUser1(loggedUser);
//        user.setSenha(user.getCpfCnpj());
        user.setSenha(passwordEncoder.encode(user.getCpf()));

        User result = userService.save(user);
        
        User userAux;
        for(int i = 0; i <userHasRoles.size(); i++) {
			userAux = new User();
			userAux.setId(result.getId());
			userHasRoles.get(i).setUser(userAux);
			
			userHasRoleService.save(userHasRoles.get(i));
        }

        return new ResponseEntity(new ApiResponse(true, "Produto adicionado com sucesso"),
                    HttpStatus.CREATED);

	}
	
	@GetMapping(path = "/user/searchNome/{nome}/page/{pageNumber}")
	Page<User> searchProduto(@PathVariable(name = "nome", required = true) String nome,
								@PathVariable(name = "pageNumber", required = true) int pageNumber) {
		
//		System.out.println("sfasfasfsa "+nome);
//		return null;
		//System.out.println(nome);
		
		return userService.findUserSearchNome(nome,pageNumber);
		//return produtoService.findProdutoById(nome);

	}
	
	@GetMapping(path = "/listUsers/page/{pageNumber}")
	Page<User> listUsersByPage(
		@PathVariable(name = "pageNumber", required = false) int pageNumber) {

		return userService.findAllUserByPage(pageNumber);
	}
	
	@GetMapping(path = "/listClientesVendedor/page/{pageNumber}")
	Page<User> listClientesVendedorByPage(
		@PathVariable(name = "pageNumber", required = false) int pageNumber) {

		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		return userService.findClientesVendedorByPage(pageNumber, Long.parseLong(auth.getName()));
	}
	
	@GetMapping(path = "/listUsersByRole/{role}")
	List<UserHasRole> listUsersByRole(
		@PathVariable(name = "role", required = false) Long role) {
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

		return userService.listAllClientesDoUser(Long.parseLong(auth.getName()));
	}
	
	
}
