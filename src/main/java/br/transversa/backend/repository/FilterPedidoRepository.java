package br.transversa.backend.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import br.transversa.backend.model.EstadoPedido;

public interface FilterPedidoRepository {
	
	Page<EstadoPedido> findPedidoByFields(String razaoSocial,
			String precoMin,
			String precoMax,
			String cpf,
			String cnpj,
			Long idPedido,
			Pageable pageable,
			int perfil, 
			Long idUser, 
			String dataInicio, 
			String dataFim,
			int estadoPedido);
	
	
//	Page<User> findClienteByFields(String nome,
//			String sobrenome,
//			String cpf,
//			String cnpj,
//			Pageable pageable);

}
