package br.transversa.backend.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import br.transversa.backend.model.EntregadorPedido;
import br.transversa.backend.model.EstadoPedido;
import br.transversa.backend.model.SaidaProdutoStock;
import br.transversa.backend.model.SaidaStockPromocao;

@Repository
public interface SaidaProdutoStockRepository extends JpaRepository<SaidaProdutoStock, Long> {


}
