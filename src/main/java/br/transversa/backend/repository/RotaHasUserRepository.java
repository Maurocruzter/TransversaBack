package br.transversa.backend.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import br.transversa.backend.model.Produto;
import br.transversa.backend.model.Role;
import br.transversa.backend.model.Rota;
import br.transversa.backend.model.RotaHasUser;

@Repository
public interface RotaHasUserRepository extends JpaRepository<RotaHasUser, Long> {

	@Query("SELECT new RotaHasUser(r.id) FROM RotaHasUser r WHERE r.user.id = :idVendedor AND r.rota.id = :idRota")
	Optional<RotaHasUser> findIfExists(Long idVendedor, Long idRota);

//	@Query("SELECT new Rota(r.id, r.designacao, r.cidade.nome, r.cidade.idEstado) FROM Rota r WHERE r.id = :id")
//	Rota findRotaVendaById(Long id);
//	 
//	@Query("SELECT new Rota(r.id, r.designacao, r.cidade.nome, r.cidade.idEstado) FROM Rota r")
//	List<Rota> findAllRotaVenda();

}
