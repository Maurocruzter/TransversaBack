package br.transversa.backend.repository.custom;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
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

import br.transversa.backend.model.Pesquisapreco;
import br.transversa.backend.model.User;
import br.transversa.backend.repository.FilterPesquisaPrecoRepository;
import br.transversa.backend.repository.FilterUserRepository;
import br.transversa.backend.repository.UserRepository;
import br.transversa.backend.util.AppConstants;

@Repository
public class CustomPesquisaPrecoRepository implements FilterPesquisaPrecoRepository {
	 
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

	@Override
	public Page<Pesquisapreco> findPesquisaPrecoByFields(String razaoSocial, String precoMin, String precoMax, String produto,
			String marca, String codigoBarras, String dataInicio, String dataFim, String vendedor, Pageable pageable) {
		
		
		
		String where = "";

		int conta = 0;

		if(!razaoSocial.equalsIgnoreCase("-1")) {
			conta = conta +1;
			where = " AND p.razaoSocial LIKE CONCAT('%',:razaoSocial,'%') ";
		}
		if(!precoMin.equalsIgnoreCase("-1")) {
			conta = conta +1;
			where = where + " AND p.preco >= :precoMin ";
			
		}
		
		if(!precoMax.equalsIgnoreCase("-1")) {
			conta = conta +1;
			where = where + " AND p.preco <= :precoMax  ";
		} 
    	if(!produto.equalsIgnoreCase("-1")) {
    		conta = conta +1;
    		where = where + " AND p.nome LIKE CONCAT('%',:nome,'%') ";
    	}
    	
    	if(!marca.equalsIgnoreCase("-1")) {
    		conta = conta +1;
    		where = where + " AND p.marca LIKE CONCAT('%',:marca,'%') ";
    	}
    	
    	if(!codigoBarras.equalsIgnoreCase("-1")) {
    		conta = conta +1;
    		where = where + " AND p.codigoBarras LIKE CONCAT('%',:codigoBarras,'%') ";
    	}
    	
    	if(!dataInicio.equalsIgnoreCase("-1")) {
    		conta = conta +1;
    		where = where + " AND p.dataCriado > :dataInicio ";
    	}
    	
    	if(!dataFim.equalsIgnoreCase("-1")) {
    		conta = conta +1;
    		where = where + " AND p.dataCriado < :dataFim ";
    	}
    	
    	if(!vendedor.equalsIgnoreCase("-1")) {
    		conta = conta +1;
    		where = where + " AND p.user.nome LIKE CONCAT('%',:vendedor,'%') ";
    	}

//    	user
    	
    	String query = "SELECT  new Pesquisapreco(p.id, p.nome, p.preco, p.uuid, p.marca) from Pesquisapreco p "
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
    	
    	
    	if(!razaoSocial.equalsIgnoreCase("-1")) {
    		query2.setParameter("razaoSocial", razaoSocial);
		}
		if(!precoMin.equalsIgnoreCase("-1")) {
			query2.setParameter("precoMin", new BigDecimal(precoMin));
		}
		
		if(!precoMax.equalsIgnoreCase("-1")) {
			query2.setParameter("precoMax", new BigDecimal(precoMax));
		} 
    	if(!produto.equalsIgnoreCase("-1")) {
    		query2.setParameter("nome", produto);
    	}
    	
    	if(!marca.equalsIgnoreCase("-1")) {
    		query2.setParameter("marca", marca);
    	}
    	
    	if(!codigoBarras.equalsIgnoreCase("-1")) {
    		query2.setParameter("codigoBarras", codigoBarras);
    	}
    	
    	if(!dataInicio.equalsIgnoreCase("-1")) {
    		try {
				Date date1=new SimpleDateFormat("yyyy-MM-dd").parse(dataInicio.substring(0, 10));
				query2.setParameter("dataInicio", date1);
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
    	}
    	
    	if(!dataFim.equalsIgnoreCase("-1")) {
    		try {
				Date date1=new SimpleDateFormat("yyyy-MM-dd").parse(dataFim.substring(0, 10));
				query2.setParameter("dataFim", date1);
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
    	}
    	
    	if(!vendedor.equalsIgnoreCase("-1")) {
    		query2.setParameter("vendedor", vendedor);
    	}
    	


    	List<Pesquisapreco> pesquisaPrecos = query2
	      .getResultList();
    	Page<Pesquisapreco> pages = new PageImpl<Pesquisapreco>(pesquisaPrecos, pageable, pesquisaPrecos.size());
	    return pages;
	}
	
}