package br.transversa.backend.repository.custom;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
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

import br.transversa.backend.model.EstadoPedido;
import br.transversa.backend.model.StockPromocao;
import br.transversa.backend.model.User;
import br.transversa.backend.repository.FilterPedidoRepository;
import br.transversa.backend.repository.FilterUserRepository;
import br.transversa.backend.repository.UserRepository;

@Repository
public class CustomPedidoRepository implements FilterPedidoRepository {

	@PersistenceContext
	private EntityManager em;

	@Override
	public Page<EstadoPedido> findPedidoByFields(String razaoSocial, String precoMin, String precoMax, String cpf,
			String cnpj, Long idPedido, Pageable pageable, int perfil, Long idUser, String dataInicio, String dataFim,
			int estadoPedido) {

		Date dataAtual = new Date();
		String where = "";

		int conta = 0;

		if (perfil != 3) {
			if (!(idPedido == -1)) {
				conta = conta + 1;
				where = where + " AND ep.pedido.id = :idPedido ";
			}

			if (!razaoSocial.equalsIgnoreCase("-1")) {
				conta = conta + 1;
				where = where + " AND ep.pedido.user2.nome LIKE CONCAT('%',:razaoSocial,'%') ";
			}
			if (!precoMin.equalsIgnoreCase("-1")) {
				conta = conta + 1;
				where = where + " AND ep.pedido.totalPedido >= :precoMin ";

			}

			if (!precoMax.equalsIgnoreCase("-1")) {
				conta = conta + 1;
				where = where + " AND ep.pedido.totalPedido <= :precoMax ";
			}

			if (!cpf.equalsIgnoreCase("-1")) {
				conta = conta + 1;
				where = where + " AND ep.pedido.user2.cpf LIKE CONCAT('%',:cpf,'%') ";
			}
//		
			if (!cnpj.equalsIgnoreCase("-1")) {
				conta = conta + 1;
				where = where + " AND ep.pedido.user2.cnpj LIKE CONCAT('%',:cnpj,'%') ";
			}
			
			if(!dataInicio.equalsIgnoreCase("-1")) {
				conta = conta + 1;
				where = where + " AND ep.pedido.dataAdicionadoPedido >= :dataInicio ";
			}
			
			if(!dataFim.equalsIgnoreCase("-1")) {
				conta = conta + 1;
				where = where + " AND ep.pedido.dataAdicionadoPedido <= :dataFim ";
			}
			
			if(estadoPedido == 2) {
				//aprovado
				conta = conta + 1;
				where = where + " AND ep.pedido.isAprovado = 1 AND ep.pedido.isFinalizado = 0 "
						+ " AND ep.pedido.isTransporte = 0 AND ep.pedido.isEntregue = 0 "
						+ " AND ep.pedido.isCancelado = 0 AND ep.pedido.clienteReclamouEstado = 0 ";
			} else if(estadoPedido == 3) {
				//Transporte
				conta = conta + 1;
				where = where + " AND ep.pedido.isFinalizado = 0 "
						+ " AND ep.pedido.isTransporte = 1 AND ep.pedido.isEntregue = 0 "
						+ " AND ep.pedido.isCancelado = 0 AND ep.pedido.clienteReclamouEstado = 0 ";
			} else if(estadoPedido == 4) {
				//Transporte
				conta = conta + 1;
				where = where + " AND ep.pedido.isFinalizado = 0 "
						+ " AND ep.pedido.isEntregue = 1 "
						+ " AND ep.pedido.isCancelado = 0 AND ep.pedido.clienteReclamouEstado = 0 ";
			} else if(estadoPedido == 5) {
				//Transporte
				conta = conta + 1;
				where = where + " AND ep.pedido.isFinalizado = 1 "
						+ " AND ep.pedido.isCancelado = 0 AND ep.pedido.clienteReclamouEstado = 0 ";
			} else if(estadoPedido == 6) {
				//Transporte
				conta = conta + 1;
				where = where + " AND ep.pedido.clienteReclamouEstado > 0 ";
			} else if(estadoPedido == 7) {
				//Transporte
				conta = conta + 1;
				where = where + " AND ep.pedido.isAprovado = 0 "
						+ " AND ep.pedido.isCancelado = 0 AND ep.pedido.clienteReclamouEstado = 0 ";
			} else if(estadoPedido == 8) {
				//Transporte
				conta = conta + 1;
				where = where + " AND ep.pedido.isCancelado = 1 ";
			}

			System.out.println(estadoPedido);

		} else {
			
			
			if (!(idPedido == -1)) {
				conta = conta + 1;
				where = where + " AND ep.estadoPedido.pedido.id = :idPedido ";
			}

			if (!razaoSocial.equalsIgnoreCase("-1")) {
				conta = conta + 1;
				where = where + " AND ep.estadoPedido.pedido.user2.nome LIKE CONCAT('%',:razaoSocial,'%') ";
			}
			if (!precoMin.equalsIgnoreCase("-1")) {
				conta = conta + 1;
				where = where + " AND ep.estadoPedido.pedido.totalPedido >= :precoMin ";

			}

			if (!precoMax.equalsIgnoreCase("-1")) {
				conta = conta + 1;
				where = where + " AND ep.estadoPedido.pedido.totalPedido <= :precoMax ";
			}

			if (!cpf.equalsIgnoreCase("-1")) {
				conta = conta + 1;
				where = where + " AND ep.estadoPedido.pedido.user2.cpf LIKE CONCAT('%',:cpf,'%') ";
			}
//		
			if (!cnpj.equalsIgnoreCase("-1")) {
				conta = conta + 1;
				where = where + " AND ep.estadoPedido.pedido.user2.cnpj LIKE CONCAT('%',:cnpj,'%') ";
			}
			
			if(!dataInicio.equalsIgnoreCase("-1")) {
				
				conta = conta + 1;
				where = where + " AND ep.estadoPedido.pedido.dataAdicionadoPedido <= :dataInicio ";
				
			}
			
			if(!dataFim.equalsIgnoreCase("-1")) {
				
				conta = conta + 1;
				where = where + " AND ep.estadoPedido.pedido.dataAdicionadoPedido <= :dataFim ";
				
			}
			
			if(estadoPedido == 2) {
				conta = conta + 1;
				where = where + " AND ep.estadoPedido.pedido.isAprovado = 1 AND ep.estadoPedido.pedido.isFinalizado = 0 "
						+ " AND ep.estadoPedido.pedido.isTransporte = 0 AND ep.estadoPedido.pedido.isEntregue = 0 "
						+ " AND ep.estadoPedido.pedido.isCancelado = 0 AND ep.estadoPedido.pedido.clienteReclamouEstado = 0 ";
			} else if(estadoPedido == 3) {
				conta = conta + 1;
				where = where + " AND ep.estadoPedido.pedido.isFinalizado = 0 "
						+ " AND ep.estadoPedido.pedido.isTransporte = 1 AND ep.estadoPedido.pedido.isEntregue = 0 "
						+ " AND ep.estadoPedido.pedido.isCancelado = 0 AND ep.estadoPedido.pedido.clienteReclamouEstado = 0 ";
			} else if(estadoPedido == 4) {
				//Transporte
				conta = conta + 1;
				where = where + " AND ep.estadoPedido.pedido.isFinalizado = 0 "
						+ " AND ep.estadoPedido.pedido.isEntregue = 1 "
						+ " AND ep.estadoPedido.pedido.isCancelado = 0 AND ep.estadoPedido.pedido.clienteReclamouEstado = 0 ";

			} else if(estadoPedido == 5) {
				//Transporte
				conta = conta + 1;
				where = where + " AND ep.estadoPedido.pedido.isFinalizado = 1 "
						+ " AND ep.estadoPedido.pedido.isCancelado = 0 AND ep.estadoPedido.pedido.clienteReclamouEstado = 0 ";
			} else if(estadoPedido == 6) {
				//Transporte
				conta = conta + 1;
				where = where + " AND ep.estadoPedido.pedido.clienteReclamouEstado > 0 ";
			} else if(estadoPedido == 7) {
				//Transporte
				conta = conta + 1;
				where = where + " AND ep.estadoPedido.pedido.isAprovado = 0 "
						+ " AND ep.estadoPedido.pedido.isCancelado = 0 AND ep.estadoPedido.pedido.clienteReclamouEstado = 0 ";
			} else if(estadoPedido == 8) {
				//Transporte
				conta = conta + 1;
				where = where + " AND ep.estadoPedido.pedido.isCancelado = 1 ";
			}

			
		}
		String query = "";
		String groupBy = "";

		if (perfil == 0) {
			query = "SELECT new EstadoPedido(max(ep.id), max(ep.dataAdicionado), max(ep.currestado), "
					+ "ep.pedido.user1.nome, ep.pedido.user1.id, "
					+ "ep.pedido.user2.nome, ep.pedido.user2.id,  ep.pedido.totalPedido, "
					+ " ep.pedido.id, ep.pedido.clienteReclamouEstado)  FROM EstadoPedido ep  " 
					+ "WHERE ";

			groupBy = " GROUP BY ep.pedido.id";
		} else if (perfil == 1) {

//			@Query(value = "SELECT new EstadoPedido(max(ep.id), max(ep.dataAdicionado), max(ep.currestado), "
//					+ "ep.pedido.user1.nome, ep.pedido.user1.id, "
//					+ "ep.pedido.user2.nome, ep.pedido.user2.id,  ep.pedido.totalPedido, "
//					+ " ep.pedido.id, ep.pedido.clienteReclamouEstado)  FROM EstadoPedido ep  "
//					+ " WHERE pedido.user2.user2.id =:idVendedor GROUP BY ep.pedido.id")
			
			query = "SELECT new EstadoPedido(max(ep.id), max(ep.dataAdicionado), max(ep.currestado), "
					+ "ep.pedido.user1.nome, ep.pedido.user1.id, "
					+ "ep.pedido.user2.nome, ep.pedido.user2.id,  ep.pedido.totalPedido, "
					+ "ep.pedido.id, ep.pedido.clienteReclamouEstado)  FROM EstadoPedido ep  "
//					+ "WHERE ";
					+ "WHERE ep.pedido.user2.user2.id =:idUser";

			groupBy = " GROUP BY ep.pedido.id";
		} else if (perfil == 2) {

			query = "SELECT new EstadoPedido(max(ep.id), max(ep.dataAdicionado), max(ep.currestado), "
					+ "ep.pedido.user1.nome, ep.pedido.user1.id, "
					+ "ep.pedido.user2.nome, ep.pedido.user2.id,  ep.pedido.totalPedido, "
					+ " ep.pedido.id, ep.pedido.clienteReclamouEstado)  FROM EstadoPedido ep  "
					+ "WHERE ep.pedido.user2.id = :idUser";

			groupBy = " GROUP BY ep.pedido.id";
		} else if (perfil == 3) {

			
//			@Query("SELECT new EstadoPedido(max(ep.estadoPedido.id), max(ep.estadoPedido.dataAdicionado), max(ep.estadoPedido.currestado), " + 
//					" ep.estadoPedido.pedido.user1.nome, ep.estadoPedido.pedido.user1.id, " + 
//					" ep.estadoPedido.pedido.user2.nome, ep.estadoPedido.pedido.user2.id,  ep.estadoPedido.pedido.totalPedido, " + 
//					" ep.estadoPedido.pedido.id, ep.estadoPedido.pedido.clienteReclamouEstado) from EntregadorPedido ep "
//					+ "WHERE ep.user.id = :idEntregador  GROUP BY ep.estadoPedido.pedido.id")
			
			query = "SELECT new EstadoPedido(max(ep.estadoPedido.id), max(ep.estadoPedido.dataAdicionado), max(ep.estadoPedido.currestado), "
					+ " ep.estadoPedido.pedido.user1.nome, ep.estadoPedido.pedido.user1.id, "
					+ " ep.estadoPedido.pedido.user2.nome, ep.estadoPedido.pedido.user2.id,  ep.estadoPedido.pedido.totalPedido, "
					+ " ep.estadoPedido.pedido.id, ep.estadoPedido.pedido.clienteReclamouEstado) from EntregadorPedido ep "
					+ "WHERE ep.user.id = :idUser ";
			
//			ep.user.id = :idEntregador  GROUP BY ep.estadoPedido.pedido.id

			groupBy = " GROUP BY ep.estadoPedido.pedido.id";
		}
		
		
		
		if(perfil == 0) {
			
			if (conta < 1) {
				where = "";
				query = query.replaceFirst("WHERE", "");
			} else if (conta == 1) {
				where = where.replaceFirst("AND", "");
			} else {
				where = where.replaceFirst("AND", "");
			}
		} 

		query = query + where + groupBy;

		Query query2 = em.createQuery(query).setFirstResult(pageable.getPageNumber() * 20).setMaxResults(20);
		
		
		if ( perfil > 0) {
			query2.setParameter("idUser", idUser);
		}

		if (!(idPedido == -1)) {
			query2.setParameter("idPedido", idPedido);
		}

		if (!razaoSocial.equalsIgnoreCase("-1")) {
			query2.setParameter("razaoSocial", razaoSocial);
		}

		if (!precoMin.equalsIgnoreCase("-1")) {
			query2.setParameter("precoMin", new BigDecimal(precoMin));
		}

		if (!precoMax.equalsIgnoreCase("-1")) {
			query2.setParameter("precoMax", new BigDecimal(precoMax));
		}
		if (!cpf.equalsIgnoreCase("-1")) {

			query2.setParameter("cpf", cpf);
		}

		if (!cnpj.equalsIgnoreCase("-1")) {
			query2.setParameter("cnpj", cnpj);
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
				Calendar calendar=Calendar.getInstance();
				calendar.setTime(date1);
				calendar.set(Calendar.HOUR_OF_DAY, 23);
				calendar.set(Calendar.MINUTE, 59);
				calendar.set(Calendar.SECOND, 59);
				query2.setParameter("dataFim", calendar.getTime());
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}  
			
		}


		List<EstadoPedido> pedidos = query2.getResultList();

		Page<EstadoPedido> pages = new PageImpl<EstadoPedido>(pedidos, pageable, pedidos.size());
		return pages;

	}

}