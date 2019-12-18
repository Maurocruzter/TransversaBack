package br.transversa.backend.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import br.transversa.backend.model.Cidade;
import br.transversa.backend.model.EstadoPedido;
import br.transversa.backend.model.Rota;

public interface FilterRotaRepository {
	
	Page<Rota> findRotasByFields(String rota,
			String cidade,
			String estado,
			Pageable pageable);

}
