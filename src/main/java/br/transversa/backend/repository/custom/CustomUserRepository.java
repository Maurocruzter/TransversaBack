package br.transversa.backend.repository.custom;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import br.transversa.backend.model.User;
import br.transversa.backend.repository.FilterUserRepository;
import br.transversa.backend.repository.UserRepository;

@Repository
public class CustomUserRepository implements FilterUserRepository {
	 
    @PersistenceContext
    private EntityManager em;
	
	public Page<User> findUserByFields(String nome, String sobrenome, String cpf, String cnpj, Pageable pageable) {

		String where = "";

		int conta = 0;

		if(!nome.equalsIgnoreCase("-1")) {
			conta = conta +1;
			where = " AND u.nome LIKE CONCAT('%',:nome,'%') ";
		}
		if(!sobrenome.equalsIgnoreCase("-1")) {
			conta = conta +1;
			where = where + " AND u.sobrenome LIKE CONCAT('%',:sobrenome,'%') ";
			
		}
		
		if(!cpf.equalsIgnoreCase("-1")) {
			conta = conta +1;
			where = where + " AND u.cpf LIKE CONCAT('%',:cpf,'%') ";
		} 
    	if(!cnpj.equalsIgnoreCase("-1")) {
    		conta = conta +1;
    		where = where + " AND u.cnpj LIKE CONCAT('%',:cnpj,'%') ";
    	}
    	
    	String query = "SELECT  new User(u.id, u.nome, u.sobrenome, "
    			+ "u.cpf, u.email, u.celular, u.uuid, u.latitude, "
    			+ "u.longitude, u.logradouro) from User u "
    			+ "WHERE ";
    	
    	if(conta <1 ) {
    		where = "";
    		query = query.replaceFirst("WHERE", "");
    	} else if(conta == 1 ) {
    		where = where.replace("AND", "");
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
		if(!sobrenome.equalsIgnoreCase("-1")) {
			query2.setParameter("sobrenome", sobrenome);
		}
		
		if(!cpf.equalsIgnoreCase("-1")) {
			query2.setParameter("cpf", cpf);
		} 
    	if(!cnpj.equalsIgnoreCase("-1")) {
    		query2.setParameter("cnpj", cnpj);
    	}

    	List<User> users = query2
	      .getResultList();
    	    	
    	
    	Page<User> pages = new PageImpl<User>(users, pageable, users.size());
	    return pages;
	}
	
}