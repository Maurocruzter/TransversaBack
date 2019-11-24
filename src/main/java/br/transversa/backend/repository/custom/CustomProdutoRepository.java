package br.transversa.backend.repository.custom;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import br.transversa.backend.model.Produto;
import br.transversa.backend.model.StockPromocao;
import br.transversa.backend.repository.FilterProdutoRepository;

@Repository
public class CustomProdutoRepository implements FilterProdutoRepository {
	 
    @PersistenceContext
    private EntityManager em;
	
//	public Page<User> findUserByFields(String nome, String sobrenome, String cpf, String cnpj, Pageable pageable) {
//
//		String where = "";
//
//		int conta = 0;
//
//		if(!nome.equalsIgnoreCase("-1")) {
//			conta = conta +1;
//			where = " AND u.nome LIKE CONCAT('%',:nome,'%') ";
//		}
//		if(!sobrenome.equalsIgnoreCase("-1")) {
//			conta = conta +1;
//			where = where + " AND u.sobrenome LIKE CONCAT('%',:sobrenome,'%') ";
//			
//		}
//		
//		if(!cpf.equalsIgnoreCase("-1")) {
//			conta = conta +1;
//			where = where + " AND u.cpf LIKE CONCAT('%',:cpf,'%') ";
//		} 
//    	if(!cnpj.equalsIgnoreCase("-1")) {
//    		conta = conta +1;
//    		where = where + " AND u.cnpj LIKE CONCAT('%',:cnpj,'%') ";
//    	}
//    	
//    	String query = "SELECT  new User(u.id, u.nome, u.sobrenome, "
//    			+ "u.cpf, u.email, u.celular, u.uuid, u.latitude, "
//    			+ "u.longitude, u.logradouro) from User u "
//    			+ "WHERE ";
//    	
//    	if(conta <1 ) {
//    		where = "";
//    		query = query.replaceFirst("WHERE", "");
//    	} else if(conta == 1 ) {
//    		where = where.replace("AND", "");
//    	} else {
//    		where = where.replaceFirst("AND", "");
//    	}    	
//    	
//    	
//    	
//    	query = query + where;
//    	
//    	
//    	Query query2 = em.createQuery(query)
//    		      .setFirstResult(pageable.getPageNumber()*20)
//    		      .setMaxResults(20);
//    	
//    	
//    	if(!nome.equalsIgnoreCase("-1")) {
//    		query2.setParameter("nome", nome);
//		}
//		if(!sobrenome.equalsIgnoreCase("-1")) {
//			query2.setParameter("sobrenome", sobrenome);
//		}
//		
//		if(!cpf.equalsIgnoreCase("-1")) {
//			query2.setParameter("cpf", cpf);
//		} 
//    	if(!cnpj.equalsIgnoreCase("-1")) {
//    		query2.setParameter("cnpj", cnpj);
//    	}
//
//    	List<User> users = query2
//	      .getResultList();
//    	    	
//    	
//    	Page<User> pages = new PageImpl<User>(users, pageable, users.size());
//	    return pages;
//	}

	@Override
	public Page<StockPromocao> findProdutoByFields(String nome, String precoMin, String precoMaximo, Pageable pageable) {


		Date dataAtual = new Date();
//		return null;
		String where = "";

		int conta = 0;

		if(!nome.equalsIgnoreCase("-1")) {
			conta = conta +1;
			where = " AND sp.promocoe.stock.produto.nome LIKE CONCAT('%',:nome,'%') ";
		}
		if(!precoMin.equalsIgnoreCase("-1")) {
			conta = conta +1;
//			p.promocoe.dataInicio <= :dataAtual AND p.promocoe.dataFim >= :dataAtual 
//					WHERE CURRENT_TIMESTAMP between dbfrotas.promocoes.data_inicio AND dbfrotas.promocoes.data_fim;
			where = where + " AND (sp.promocoe.stock.produto.preco >= :precoMin AND sp.promocoe.precoPromocao >= :precoMin) "
					+ " OR (sp.promocoe.precoPromocao >= :precoMin AND "
					+ " :dataAtual1 BETWEEN sp.promocoe.dataInicio AND sp.promocoe.dataFim) ";
			
		}
		
		if(!precoMaximo.equalsIgnoreCase("-1")) {
			conta = conta +1;
			where = where + " AND sp.promocoe.stock.produto.preco <= :precoMaximo "
					+ " OR (sp.promocoe.precoPromocao <= :precoMaximo AND "
					+ " :dataAtual2 BETWEEN sp.promocoe.dataInicio AND sp.promocoe.dataFim) ";
		} 
		
		
		String query = "SELECT new StockPromocao(sp.id, sp.promocoe.dataInicio, sp.promocoe.dataFim, "
				+ "sp.promocoe.compraMinima, sp.promocoe.desconto, sp.promocoe.stock.produto.id, "
				+ "sp.promocoe.stock.produto.preco, sp.promocoe.stock.produto.nome , "
				+ "sp.promocoe.stock.produto.uuid, sp.quantidadeEmPromocao, sp.promocoe.stock.quantidade ) "
				+ "FROM StockPromocao sp "
				+ "WHERE ";

		
//    	String query = "SELECT new Produto(p.id, p.descricao, p.nome, p.preco, p.uuid) "
//    			+ "FROM Produto p "
//    			+ "WHERE ";
    	
    	if(conta <1 ) {
    		where = "";
    		query = query.replaceFirst("WHERE", "");
    	} else if(conta == 1 ) {
    		where = where.replaceFirst("AND", "");
    	} else {
    		where = where.replaceFirst("AND", "");
    	}    	
    	
    	
//    	where = where + " GROUP BY sp.promocoe.stock.produto.id";
    	query = query + where;
    	
    	
    	Query query2 = em.createQuery(query)
    		      .setFirstResult(pageable.getPageNumber()*20)
    		      .setMaxResults(20);
    	
    	
    	if(!nome.equalsIgnoreCase("-1")) {
    		query2.setParameter("nome", nome);
		}
		if(!precoMin.equalsIgnoreCase("-1")) {
			
			query2.setParameter("precoMin", new BigDecimal(precoMin));
			query2.setParameter("dataAtual1", dataAtual);
		}
		
		if(!precoMaximo.equalsIgnoreCase("-1")) {
			query2.setParameter("precoMaximo", new BigDecimal(precoMaximo));
			query2.setParameter("dataAtual2", dataAtual);
		}

    	List<StockPromocao> produtos = query2
	      .getResultList();
    	    	
    	
    	Page<StockPromocao> pages = new PageImpl<StockPromocao>(produtos, pageable, produtos.size());
	    return pages;
		
//		
//		
//		
//		// TODO Auto-generated method stub
//		return null;
	}
	
}