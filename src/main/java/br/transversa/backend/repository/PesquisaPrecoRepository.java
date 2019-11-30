package br.transversa.backend.repository;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import br.transversa.backend.model.Pesquisapreco;
import br.transversa.backend.model.Produto;


@Repository
public interface PesquisaPrecoRepository extends JpaRepository<Pesquisapreco, Long> {
	
	@Query(value = "select new Pesquisapreco(p.nome, p.data, p.fileType) from Pesquisapreco p WHERE p.uuid = :uuid")
	Pesquisapreco findPesquisaprecoByUuid(String uuid);

	
	@Query("select new Pesquisapreco(p.id, p.nome, p.preco, p.uuid, "
			+ "p.marca, p.descricao, p.codigoBarras, p.razaoSocial, p.endereco, p.dataCriado, p.user.nome) from Pesquisapreco p WHERE p.id = :id")
	Pesquisapreco findPesquisaPrecoById(Long id);
	
	
	@Query("select new Pesquisapreco(p.id, p.nome, p.preco, p.uuid, p.marca) from Pesquisapreco p")
    Page<Pesquisapreco> findPesquisaPrecoAll(Pageable pageable);
	
	
	@Query(value="SELECT new Pesquisapreco(p.id, p.nome, p.preco, p.uuid, p.marca) FROM Pesquisapreco p  "
			+ "WHERE p.nome LIKE CONCAT('%',:nome,'%')")
	Page<Pesquisapreco> findPesquisaPrecoByNome(@Param("nome") String nome, Pageable pageable);
	
}
