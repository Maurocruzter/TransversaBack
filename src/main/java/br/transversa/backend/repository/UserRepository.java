package br.transversa.backend.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import br.transversa.backend.model.Produto;
import br.transversa.backend.model.User;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
	
    Optional<User> findByEmail(String email);

    @Query("select new User(u.id, u.nome) from User u")
    Page<User> findthis(Pageable pageable);
    
    Boolean existsByEmail(String email);
    
//	@Query("SELECT r.id FROM users r") 
	Page<User> findAll(Pageable pageable);

	//Page<Users> findCustomQuery(Pageable pageable);
	
	//@Query(value = "SELECT * FROM users WHERE email = ?1", nativeQuery = true)
	@Query(value = "SELECT * FROM users INNER JOIN user_has_roles on users.id = user_has_roles.users_id WHERE users.email = ?1",  nativeQuery = true)
	User findByEmailAddress(String emailAddress);
	
	
	@Query("select new User(u.id, u.nome, u.sobrenome, u.cpfCnpj, u.email, u.celular, u.uuid, u.latitude, u.longitude, u.endereco) from User u")
    Page<User> findUserAll(Pageable pageable);
	
	@Query(value="SELECT new User(u.id, u.nome, u.sobrenome, u.cpfCnpj, u.email, u.celular, u.uuid, u.latitude, u.longitude, u.endereco) from User u  "
			+ "WHERE u.nome LIKE CONCAT('%',:nome,'%')")
	Page<User> findUserByNome(@Param("nome") String nome, Pageable pageable);

	@Query("select new User(u.id, u.nome, u.sobrenome, u.cpfCnpj, u.email, u.celular, u.uuid, ROUND(u.latitude,7), ROUND(u.longitude,7), u.endereco) from User u ")
	List<User> findAllUsers();
	
	
	@Query("select new User(u.id, u.nome, u.sobrenome, u.cpfCnpj, u.email, u.celular, u.uuid, u.latitude, u.longitude, u.endereco) from User u WHERE u.user2.id = :id")
	List<User> listAllClientesDoUser(Long id);
	
	@Query("select new User(u.id) from User u WHERE u.id = :idCliente AND u.user2.id = :idVendedor")
	Optional<User> findIfVendedorHasCliente(Long idVendedor, Long idCliente);
}