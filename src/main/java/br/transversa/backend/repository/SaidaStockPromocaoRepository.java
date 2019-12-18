package br.transversa.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.transversa.backend.model.SaidaProdutoStock;
import br.transversa.backend.model.SaidaStockPromocao;

@Repository
public interface SaidaStockPromocaoRepository extends JpaRepository<SaidaStockPromocao, Long> {


}
