package br.transversa.backend.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import br.transversa.backend.model.Cidade;
import br.transversa.backend.model.EstadoPedido;

public interface FilterCidadeRepository {
	
	Page<Cidade> findCidadeByFields(String nome,
			String abreviacao,
			String estado,
			Pageable pageable);
	
	List<Cidade> findAllCidadeByFields(String nome,
			String abreviacao,
			String estado,
			Pageable pageable);

}
