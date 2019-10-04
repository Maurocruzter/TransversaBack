package br.transversa.backend.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.transversa.backend.model.UserHasRole;
import br.transversa.backend.repository.UserHasRoleRepository;

@Service
public class UserHasRoleService {
	
	@Autowired
    UserHasRoleRepository usersHasRoleRepository;
	
	public UserHasRole save(UserHasRole userHasRole) {
		return usersHasRoleRepository.save(userHasRole);
	}

    
}
