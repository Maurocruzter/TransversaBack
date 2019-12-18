package br.transversa.backend.service;

import java.math.BigDecimal;
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

import br.transversa.backend.model.Cidade;
import br.transversa.backend.model.Produto;
import br.transversa.backend.model.User;
import br.transversa.backend.model.UserHasRole;
import br.transversa.backend.repository.CidadeRepository;
import br.transversa.backend.repository.UserHasRoleRepository;
import br.transversa.backend.repository.UserRepository;
import br.transversa.backend.repository.custom.CustomCidadeRepository;
import br.transversa.backend.repository.custom.CustomUserRepository;
//import br.transversa.backend.repository.UsersRepository;
import br.transversa.backend.security.JwtTokenProvider;

@Service
@Transactional
public class CidadeService {
	
	@Autowired
    CidadeRepository cidadeRepository;
	
	@Autowired
	CustomCidadeRepository customCidadeRepository;
	
	public Cidade save(Cidade cidade) {
    	return cidadeRepository.save(cidade);
    }


	public Page<Cidade> findCidadeFilter(String nome, String abreviacao, String estado, int pageNumber) {
		Pageable pageable = PageRequest.of(pageNumber, 20);
		
		return customCidadeRepository.findCidadeByFields(nome, abreviacao, estado, pageable);
	}
	
	public List<Cidade> findAllCidadeFilter(String nome, String abreviacao, String estado, int pageNumber) {
		Pageable pageable = PageRequest.of(pageNumber, 20);
		
		return customCidadeRepository.findAllCidadeByFields(nome, abreviacao, estado, pageable);
	}
}
