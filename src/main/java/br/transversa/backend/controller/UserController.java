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

import br.transversa.backend.model.Produto;
import br.transversa.backend.model.Role;
import br.transversa.backend.model.User;
import br.transversa.backend.model.UserHasRole;
import br.transversa.backend.payload.ApiResponse;
import br.transversa.backend.security.JwtTokenProvider;
import br.transversa.backend.service.ProdutoService;
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
	
	
	@RequestMapping(value = "/user/add", method = RequestMethod.POST)
	ResponseEntity<?> registrarProduto(@RequestParam(name="file", required=false) MultipartFile file, 
			@RequestParam("nome") String nome, 
			@RequestParam("sobrenome") String sobrenome,
			@RequestParam("email") String email,
			@RequestParam("cpf_cnpj") String cpfCnpj,
			@RequestParam(name="latitude", required=false) Double latitude,
			@RequestParam(name="longitude", required=false) Double longitude,
			@RequestParam("roles") String roles,
			@RequestParam("senha") String senha,
			@RequestParam("celular") String celular,
			@RequestParam("endereco") String endereco,
			@RequestParam(name = "assignedTo", required=false) Long assignedTo) throws IOException{

		if(userService.existsByEmail(email)) {
            return new ResponseEntity(new ApiResponse(false, "Email Address already in use!"),
                    HttpStatus.BAD_REQUEST);
        }
		
		
		
		List<UserHasRole> userHasRoles = new ArrayList<>();

		Long id = new Long(1+1);
		
		boolean isCadastrarCliente = false;
		boolean isVendedor = false;
		
		String[] str;
		str = roles.split(",");
		int index;
		
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		

		if(auth.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_ADMIN"))) {
			
			
			for(int i = 0; i< str.length ; i++) {
				
				index = AppConstants.ROLES.indexOf(str[i].trim());
				if(index <0) {
					return new ResponseEntity(new ApiResponse(false, "O role "+ str[i].trim() + " não existe"),
		                    HttpStatus.BAD_REQUEST);
				}
				if (str[i].equalsIgnoreCase("ROLE_CLIENTE")) {
					isCadastrarCliente = true;
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
		user.setCelular(celular);
		user.setCpfCnpj(cpfCnpj);
		user.setCelular(celular);
		
		
        User loggedUser = new User();
        loggedUser.setId(Long.parseLong(auth.getName()));
		if(isCadastrarCliente) {
			user.setLatitude(latitude);
			user.setLongitude(longitude);
			user.setEndereco(endereco);
			UUID uuid = UUID.randomUUID();
			user.setUuid(uuid.toString());
			user.setData(file.getBytes());
			user.setFileType(file.getContentType());
			
			//Placing assignedUser
			if(isVendedor) {
				user.setUser2(loggedUser);
			} else {
				User userAux = new User();
				userAux.setId(assignedTo);
				user.setUser2(loggedUser);
			}
			
		}
		
		
        
        //Placing createdBy
        user.setUser(loggedUser);
        user.setSenha(senha);
        user.setSenha(passwordEncoder.encode(user.getSenha()));

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
	
	@GetMapping(path = "/listAllUsers")
	List<User> listAllUsers() {

		return userService.findAllUsers();
	}
	
	
	@GetMapping(path = "/listAllClientesDoUser")
	List<User> listAllClientesDoUser() {
		
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();

		return userService.listAllClientesDoUser(Long.parseLong(auth.getName()));
	}
	
	
}
