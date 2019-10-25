package br.transversa.backend.repository;



import br.transversa.backend.model.UserHasRole;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;




@Repository
public interface UserHasRoleRepository extends JpaRepository<UserHasRole, Long> {


	@Query(value = "SELECT new UserHasRole(uhr.user.id, uhr.user.nome)  "
			+ "FROM UserHasRole uhr WHERE uhr.role.id = :idRole")
	List<UserHasRole> listUsersByRoleId(Long idRole);
}
