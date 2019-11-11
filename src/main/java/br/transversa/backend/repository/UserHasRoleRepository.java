package br.transversa.backend.repository;



import br.transversa.backend.model.User;
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
	
	
	@Query("select new User(u.user.id, u.user.nome, u.user.sobrenome, u.user.cpf, u.user.email, u.user.celular, "
			+ "u.user.uuid, u.user.latitude, u.user.longitude, u.user.logradouro, u.user.cnpj) from UserHasRole u WHERE u.role.id = :idRole")
	List<User> listAllUsersWithRole(Long idRole);
}
