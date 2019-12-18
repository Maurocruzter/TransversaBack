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
import br.transversa.backend.repository.FilterCidadeRepository;
import br.transversa.backend.util.AppConstants;

@Repository
public class CustomCidadeRepository implements FilterCidadeRepository {

	@PersistenceContext
	private EntityManager em;

	@Override
	public Page<Cidade> findCidadeByFields(String nome, String abreviacao, String estado, Pageable pageable) {
		
		
		
		int conta = 0;
		int idEstado = 0;
		String where = "";
		
		if (!nome.equalsIgnoreCase("-1")) {
			conta = conta + 1;
			where = where + " AND c.nome LIKE CONCAT('%',:nome,'%') ";
		}
		
		if (!abreviacao.equalsIgnoreCase("-1")) {
			conta = conta + 1;
			where = where + " AND c.abreviacao LIKE CONCAT('%',:abreviacao,'%') ";
		}
		
		if (!estado.equalsIgnoreCase("-1")) {
			
			idEstado = AppConstants.ESTADOS_BRASILEIROS.indexOf(estado);
			
			if(idEstado == -1) {
				return null; 
			}
			
			conta = conta + 1;
			where = where + " AND c.idEstado = :idEstado ";
		}
		
		
		String query = "SELECT new Cidade(c.id, c.abreviacao, c.nome, c.idEstado) "
				+ "FROM Cidade c "
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
    	
    	
    	if(!nome.equalsIgnoreCase("-1")) {
    		query2.setParameter("nome", nome);
		}
		if(!abreviacao.equalsIgnoreCase("-1")) {
			
			query2.setParameter("abreviacao", abreviacao);
		}
		
		if(!estado.equalsIgnoreCase("-1")) {
			query2.setParameter("idEstado", idEstado);
		}

    	List<Cidade> cidades = query2
	      .getResultList();
    	    	
    	
    	Page<Cidade> pages = new PageImpl<Cidade>(cidades, pageable, cidades.size());
	    return pages;
		

	}

	@Override
	public List<Cidade> findAllCidadeByFields(String nome, String abreviacao, String estado, Pageable pageable) {
		
		
		System.out.println(estado);
		
		int conta = 0;
		int idEstado = 0;
		String where = "";
		
		if (!nome.equalsIgnoreCase("-1")) {
			conta = conta + 1;
			where = where + " AND c.nome LIKE CONCAT('%',:nome,'%') ";
		}
		
		if (!abreviacao.equalsIgnoreCase("-1")) {
			conta = conta + 1;
			where = where + " AND c.abreviacao LIKE CONCAT('%',:abreviacao,'%') ";
		}
		
		if (!estado.equalsIgnoreCase("-1")) {
			
			idEstado = AppConstants.ESTADOS_BRASILEIROS.indexOf(estado);
			
			if(idEstado == -1) {
				return null; 
			}
			
			conta = conta + 1;
			where = where + " AND c.idEstado = :idEstado ";
		}
		
		
		String query = "SELECT new Cidade(c.id, c.abreviacao, c.nome, c.idEstado) "
				+ "FROM Cidade c "
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
    	
    	
    	Query query2 = em.createQuery(query);
    	
    	
    	if(!nome.equalsIgnoreCase("-1")) {
    		query2.setParameter("nome", nome);
		}
		if(!abreviacao.equalsIgnoreCase("-1")) {
			
			query2.setParameter("abreviacao", abreviacao);
		}
		
		if(!estado.equalsIgnoreCase("-1")) {
			query2.setParameter("idEstado", idEstado);
		}

    	List<Cidade> cidades = query2
	      .getResultList();
    	
	    return cidades;
	}

}