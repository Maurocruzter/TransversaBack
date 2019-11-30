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
import br.transversa.backend.util.AppConstants;

@Repository
public class CustomUserRepository implements FilterUserRepository {
	 
    @PersistenceContext
    private EntityManager em;
	
	public Page<User> findUserByFields(String nome, String sobrenome, String cpf, String cnpj, int perfil, Pageable pageable) {

		String where = "";

		int conta = 0;

		if(!nome.equalsIgnoreCase("-1")) {
			conta = conta +1;
			where = " AND u.user.nome LIKE CONCAT('%',:nome,'%') ";
		}
		if(!sobrenome.equalsIgnoreCase("-1")) {
			conta = conta +1;
			where = where + " AND u.user.sobrenome LIKE CONCAT('%',:sobrenome,'%') ";
			
		}
		
		if(!cpf.equalsIgnoreCase("-1")) {
			conta = conta +1;
			where = where + " AND u.user.cpf LIKE CONCAT('%',:cpf,'%') ";
		} 
    	if(!cnpj.equalsIgnoreCase("-1")) {
    		conta = conta +1;
    		where = where + " AND u.user.cnpj LIKE CONCAT('%',:cnpj,'%') ";
    	}

    	if(perfil == 0) {

		} else if(perfil == 88) {
			conta = conta + 1;
			where = where + " AND u.role.id != 3 ";
		} else {
			conta = conta + 1;
			where = where + " AND u.role.id = " + perfil +" ";
		} 
//    	user
    	
    	String query = "SELECT  new User(u.user.id, u.user.nome, u.user.sobrenome, "
    			+ "u.user.cpf, u.user.email, u.user.celular, u.user.uuid, u.user.comissao, u.user.latitude, "
    			+ "u.user.longitude, u.user.logradouro) from UserHasRole u "
    			+ "WHERE ";
//    	String query = "SELECT  new User(u.id, u.nome, u.sobrenome, "
//    			+ "u.cpf, u.email, u.celular, u.uuid, u.latitude, "
//    			+ "u.longitude, u.logradouro) from User u "
//    			+ "WHERE ";
    	
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