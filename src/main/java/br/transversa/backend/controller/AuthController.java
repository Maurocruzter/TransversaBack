package br.transversa.backend.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.transversa.backend.model.User;
import br.transversa.backend.payload.JwtAuthenticationResponse;
import br.transversa.backend.payload.LoginRequest;
import br.transversa.backend.security.JwtTokenProvider;
import br.transversa.backend.service.UserHasRoleService;
import br.transversa.backend.service.UserService;

@RestController
@RequestMapping("/api/v1")
public class AuthController {

	@Value("${app.jwtExpirationInMs}")
    private int expiresIn;
	@Autowired
	UserService userService;
	
	@Autowired
	UserHasRoleService userHasRoleService;
	
	@Autowired
    JwtTokenProvider tokenProvider;
	
	@Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    PasswordEncoder passwordEncoder;
	
	


	
//	@GetMapping(path = "/page/{pageNumber}")
//	Page<User2> loadUsersPage(
//		@PathVariable(name = "pageNumber", required = false) int pageNumber) {
//		
//		return userService.findSomething(pageNumber);
//	}
	
	@GetMapping(path = "/page/{pageNumber}")
	Page<User> loadUsersPage(
		@PathVariable(name = "pageNumber", required = false) int pageNumber) {
		
		return userService.findSomething(pageNumber);
	}
	
	
	@GetMapping(path = "/pages/{pageNumber}")
	List<User> loadUserssPage(
		@PathVariable(name = "pageNumber", required = false) int pageNumber) {
		
		return userService.findAll();
	}

    @PostMapping("/signin")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getEmail(),
                        loginRequest.getSenha()
                )
        );
        
//        System.out.println(expiresIn);

        SecurityContextHolder.getContext().setAuthentication(authentication);

        String jwt = tokenProvider.generateToken(authentication);
        User user = userService.findCPFCnpJByEmail(authentication.getName());
//        
//        System.out.println("This is the new Version ");
        return ResponseEntity.ok(new JwtAuthenticationResponse(jwt, authentication.getAuthorities(), expiresIn, authentication.getName(), user.getCpfCnpj()));
    }

//    @RequestMapping(value = "/signup", method = RequestMethod.POST, consumes = "application/json")
//    public ResponseEntity<?> registerUser(@Valid @RequestBody SignupRequest signupRequest) {
//
//
//        if(userService.existsByEmail(signupRequest.getEmail())) {
//            return new ResponseEntity(new ApiResponse(false, "Email Address already in use!"),
//                    HttpStatus.BAD_REQUEST);
//        }
//
//        User user = new User();
//        user.setEmail(signupRequest.getEmail());
//        user.setSenha(signupRequest.getSenha());
//        user.setNome(signupRequest.getNome());
//        user.setSobrenome(signupRequest.getSobrenome());
//        user.setCelular(signupRequest.getCelular());
//        
//        
//        
//        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
//        User loggedUser = new User();
//        loggedUser.setId(Long.parseLong(auth.getName()));
//        
//        //Placing createdBy
//        user.setUser(loggedUser);
//        user.setSenha(passwordEncoder.encode(user.getSenha()));
//
//        User result = userService.save(user);
//
//        
//        
//        int length = signupRequest.getRole().size();
//        
//
//        for(int i = 0; i < length; i++) {
//        	
//        	UserHasRole userHasRole = new UserHasRole();
//            
//            userHasRole.setUser(result);
//        	Role role = new Role();
//            role.setId(signupRequest.getRole().get(i));
//            userHasRole.setRole(role);
//            userHasRoleService.save(userHasRole);
//            
//        }
//
//        URI location = ServletUriComponentsBuilder
//                .fromCurrentContextPath().path("/users/{username}")
//                .buildAndExpand(result.getEmail()).toUri();
//
//        return ResponseEntity.created(location).body(new ApiResponse(true, "User registered successfully"));
//    }
    
    
    //@PostMapping(path = "/user/add")
    
}
