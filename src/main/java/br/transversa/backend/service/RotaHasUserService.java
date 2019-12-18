package br.transversa.backend.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.transversa.backend.model.RotaHasUser;
import br.transversa.backend.repository.RotaHasUserRepository;

@Service
@Transactional
public class RotaHasUserService {
	
	@Autowired
    RotaHasUserRepository rotaHasUserRepository;
	
	public RotaHasUser save (RotaHasUser rotaHasUser) {
		return rotaHasUserRepository.save(rotaHasUser);
	}
	
	public Optional<RotaHasUser> findIfExists(Long idVendedor, Long idRota) {
		
		return rotaHasUserRepository.findIfExists(idVendedor, idRota);
	}
	
//	@Autowired
//	CustomRotaRepository customRotaRepository;
//
//	public Rota save(Rota cidade) {
//    	return rotaRepository.save(cidade);
//    }
//	
//	public Page<Rota> findrotaVendaFilter(String rota, String cidade, String estado, int pageNumber) {
//		Pageable pageable = PageRequest.of(pageNumber, 20);
//		
//		return customRotaRepository.findRotasByFields(rota, cidade, estado, pageable);
//	}
//	
//	public Rota findrotaVendaById(Long idRota) {
//		
//		return rotaRepository.findRotaVendaById(idRota);
//	}
//
//	public List<Rota> findAllRotaVenda() {
//		// TODO Auto-generated method stub
//		return rotaRepository.findAllRotaVenda();
//	}
}