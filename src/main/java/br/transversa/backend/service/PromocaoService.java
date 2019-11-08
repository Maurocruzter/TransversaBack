package br.transversa.backend.service;

import java.util.Date;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.transversa.backend.model.Promocoes;
import br.transversa.backend.repository.PromocoesRepository;

@Service
@Transactional
public class PromocaoService {
	
	@Autowired
    PromocoesRepository promocoesRepository;
	

	public void save(Promocoes promocao) {
		promocoesRepository.save(promocao);
	}
	
	public Page<Promocoes> findPromocoesByPage(int pageNumber, Date date) {
		Pageable pageable = PageRequest.of(pageNumber, 20);
		return promocoesRepository.findPromocoesByPage(pageable, date);
	}
	
	public Promocoes findPromocoesByProdutoId(int pageNumber ,Long produtoId) {
		
		System.out.println("Esteve aqyu debtri");
		Pageable pageable = PageRequest.of(pageNumber, 1);
		Page<Promocoes> aux = promocoesRepository.findPromocoesByProdutoId(pageable, produtoId);
		
		if(aux.isEmpty()) {
			return null;
		}
		
		return aux.getContent().get(0);
	}
	
	public Optional<Promocoes> findIfPromocaoExiste(Long idProduto,Date dataInicio, Date  dataFim) {

		return promocoesRepository.findIfPromocaoNoOverlap(idProduto, dataInicio,  dataFim);//, dataInicio, dataFim);
	}
	
}
