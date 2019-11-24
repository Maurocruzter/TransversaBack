package br.transversa.backend.service;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.transversa.backend.model.Promocoes;
import br.transversa.backend.model.StockPromocao;
import br.transversa.backend.repository.PromocoesRepository;
import br.transversa.backend.repository.StockPromocaoRepository;

@Service
@Transactional
public class PromocaoService {

	@Autowired
	PromocoesRepository promocoesRepository;

	@Autowired
	StockPromocaoRepository stockPromocaoRepository;

	public void save(Promocoes promocao) {
		promocoesRepository.save(promocao);
	}

	public void update(Promocoes promocao, Long idPromocao) {

		promocoesRepository.updatePromocao(promocao.getCompraMinima(), new Timestamp(new Date().getTime()),
				promocao.getDataFim(), promocao.getDataInicio(), promocao.getDesconto(), idPromocao, promocao.getPrecoPromocao());
	}

	public Page<Promocoes> findPromocoesByPage(int pageNumber, Date date) {
		Pageable pageable = PageRequest.of(pageNumber, 20);
		return promocoesRepository.findPromocoesByPage(pageable, date);
	}

	public Page<StockPromocao> findPromocoesByPageModified(int pageNumber, Date date) {
		Pageable pageable = PageRequest.of(pageNumber, 20);
		return stockPromocaoRepository.findStockPromocoesAtivasByPage(pageable, date);
	}

	public List<StockPromocao> findAllStockPromocoesAtivasVOffline(Date date) {
		return stockPromocaoRepository.findAllStockPromocoesAtivasVOffline(date);
	}

	public Promocoes findPromocoesByProdutoId(int pageNumber, Long produtoId) {

		Pageable pageable = PageRequest.of(pageNumber, 1);
		Page<Promocoes> aux = promocoesRepository.findPromocoesByProdutoId(pageable, produtoId);

		if (aux.isEmpty()) {
			return null;
		}

		return aux.getContent().get(0);
	}
	
	public Promocoes findPromocaoIdByProdutoId(Long produtoId) {
		return promocoesRepository.findPromocaoAndPrecoIdByProdutoId(produtoId);
	}

	public Optional<Promocoes> findIfPromocaoExiste(Long idProduto, Date dataInicio, Date dataFim) {

		return promocoesRepository.findIfPromocaoNoOverlap(idProduto, dataInicio, dataFim);// , dataInicio, dataFim);
	}

}
