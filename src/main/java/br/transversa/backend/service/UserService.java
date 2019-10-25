package br.transversa.backend.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.transversa.backend.model.Produto;
import br.transversa.backend.model.User;
import br.transversa.backend.model.UserHasRole;
import br.transversa.backend.repository.UserHasRoleRepository;
import br.transversa.backend.repository.UserRepository;
//import br.transversa.backend.repository.UsersRepository;
import br.transversa.backend.security.JwtTokenProvider;

@Service
@Transactional
public class UserService {
	
//	@Autowired
//    UsersRepository usersRepository;
	
	@Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    UserRepository userRepository;
    
    @Autowired
    UserHasRoleRepository userHasRoleRepository;
    

//    @Autowired
//    RoleRepository roleRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    JwtTokenProvider tokenProvider;
    
    public List<User> findAll() {
    	return userRepository.findAll();
    }
    

    
    public Page<User> findSomething(int pageNumber) {
    	
    	Pageable pageable = PageRequest.of(pageNumber, 20);
		
		return userRepository.findthis(pageable);
    	
    }
    
    public Boolean existsByEmail(String email) {
    	return userRepository.existsByEmail(email);
    }
    
    public User save(User user) {
    	return userRepository.save(user);
    }
    
    public Page<User> findAllUserByPage(int pageNumber) {
		Pageable pageable = PageRequest.of(pageNumber, 20);
		return userRepository.findUserAll(pageable);
	}
    
    public List<UserHasRole> listUsersByRole(Long idRole) {
//		Pageable pageable = PageRequest.of(pageNumber, 20);
    	
		return userHasRoleRepository.listUsersByRoleId(idRole);
	}
	
	
	public Page<User> findUserSearchNome(String nome, int pageNumber) {
		Pageable pageable = PageRequest.of(pageNumber, 20);
//		System.out.println(nome);
		return userRepository.findUserByNome(nome, pageable);
	}



	public List<User> findAllUsers() {
		return userRepository.findAllUsers();
	}
	
	public List<User> listAllClientesDoUser(Long id) {
		return userRepository.listAllClientesDoUser(id);
	}
	
	public Optional<User> findIfVendedorHasCliente(Long idVendedor, Long idCliente) {
		
//		System.out.println("Vendedor " + idVendedor);
//		System.out.println("Cliente " + idCliente);
		return userRepository.findIfVendedorHasCliente(idVendedor, idCliente);
	}

	public User findCPFCnpJByEmail(String email) {
		return userRepository.findUserCpfCnpjByEmail(email);
	}



	public void ChangePassword(String senha, Long id) {
		userRepository.ChangePassword(senha, id);
		
	}
    
}
