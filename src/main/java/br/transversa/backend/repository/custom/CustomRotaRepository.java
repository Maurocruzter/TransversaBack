package br.transversa.backend.repository.custom;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import br.transversa.backend.model.Cidade;
import br.transversa.backend.model.Rota;
import br.transversa.backend.repository.FilterCidadeRepository;
import br.transversa.backend.repository.FilterRotaRepository;
import br.transversa.backend.util.AppConstants;

@Repository
public class CustomRotaRepository implements FilterRotaRepository {

	@PersistenceContext
	private EntityManager em;

	@Override
	public Page<Rota> findRotasByFields(String rota, String cidade, String estado, Pageable pageable) {
		int conta = 0;
		int idEstado = 0;
		String where = "";
		
		if (!rota.equalsIgnoreCase("-1")) {
			conta = conta + 1;
			where = where + " AND r.designacao LIKE CONCAT('%',:rota,'%') ";
		}
		
		if (!cidade.equalsIgnoreCase("-1")) {
			conta = conta + 1;
			where = where + " AND r.cidade.nome LIKE CONCAT('%',:cidade,'%') ";
		}
		
		if (!estado.equalsIgnoreCase("-1")) {
			
			idEstado = AppConstants.ESTADOS_BRASILEIROS.indexOf(estado);
			
			if(idEstado == -1) {
				return null; 
			}
			
			conta = conta + 1;
			where = where + " AND r.cidade.idEstado = :idEstado ";
		}
		
		
		String query = "SELECT new Rota(r.id, r.designacao, r.cidade.nome, r.cidade.idEstado) "
				+ "FROM Rota r "
				+ "WHERE ";
    	
    	if(conta <1 ) {
    		where = "";
    		query = query.replaceFirst("WHERE", "");
    	} else if(conta == 1 ) {
    		where = where.replaceFirst("AND", "");
    	} else {
    		where = where.replaceFirst("AND", "");
    	}    	
    	
    	
    	query = query + where;
    	
    	
    	Query query2 = em.createQuery(query)
    		      .setFirstResult(pageable.getPageNumber()*20)
    		      .setMaxResults(20);
    	
    	
    	if(!rota.equalsIgnoreCase("-1")) {
    		query2.setParameter("rota", rota);
		}
		if(!cidade.equalsIgnoreCase("-1")) {
			
			query2.setParameter("cidade", cidade);
		}
		
		if(!estado.equalsIgnoreCase("-1")) {
			query2.setParameter("idEstado", idEstado);
		}

    	List<Rota> cidades = query2
	      .getResultList();
    	
    	Page<Rota> pages = new PageImpl<Rota>(cidades, pageable, cidades.size());
	    return pages;
	}

}