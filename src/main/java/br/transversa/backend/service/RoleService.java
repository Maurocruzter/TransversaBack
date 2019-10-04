package br.transversa.backend.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import br.transversa.backend.model.Role;
import br.transversa.backend.model.User;
import br.transversa.backend.repository.RoleRepository;
import br.transversa.backend.repository.UserRepository;
//import br.transversa.backend.repository.UsersRepository;
import br.transversa.backend.security.JwtTokenProvider;

@Service
public class RoleService {
	
//	@Autowired
//    UsersRepository usersRepository;
	
	@Autowired
    RoleRepository roleRepository;

    
    public List<Role> findAll() {
    	return roleRepository.findRoleAll();
    }


    
}
