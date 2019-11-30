package br.transversa.backend.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import br.transversa.backend.model.Pesquisapreco;
import br.transversa.backend.model.User;

public interface FilterPesquisaPrecoRepository {
	
	
	
	Page<Pesquisapreco> findPesquisaPrecoByFields(String razaoSocial, String precoMin, String precoMax, String produto,
			String marca, String codigoBarras, String dataInicio, String dataFim, String vendedor, Pageable pageable);
	
	
//	Page<User> findClienteByFields(String nome,
//			String sobrenome,
//			String cpf,
//			String cnpj,
//			Pageable pageable);

}
