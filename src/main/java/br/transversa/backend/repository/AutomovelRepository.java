package br.transversa.backend.repository;



import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import br.transversa.backend.model.Automovel;
import br.transversa.backend.model.Carrinho;




@Repository
public interface AutomovelRepository extends JpaRepository<Automovel, Long> {

	
	@Query(value = "SELECT new Automovel(a.id) FROM Automovel a WHERE a.matricula= :matricula")
	Optional<Automovel> findAutomovelByMatricula(String matricula);
	

	@Query(value = "SELECT new Automovel(a.id, a.matricula) FROM Automovel a")
	List<Automovel> findAllAutomoveis();
}
