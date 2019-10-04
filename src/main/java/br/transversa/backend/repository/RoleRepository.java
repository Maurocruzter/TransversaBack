package br.transversa.backend.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import br.transversa.backend.model.Produto;
import br.transversa.backend.model.Role;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
	
//    Optional<User> findByEmail(String email);
//
//    @Query("select new User(u.id, u.nome) from User u")
//    Page<User> findthis(Pageable pageable);
//    
//    Boolean existsByEmail(String email);
//    
////	@Query("SELECT r.id FROM users r") 
//	Page<User> findAll(Pageable pageable);
//
//	//Page<Users> findCustomQuery(Pageable pageable);
//	
//	//@Query(value = "SELECT * FROM users WHERE email = ?1", nativeQuery = true)
//	@Query(value = "SELECT * FROM users INNER JOIN user_has_roles on users.id = user_has_roles.users_id WHERE users.email = ?1",  nativeQuery = true)
//	User findByEmailAddress(String emailAddress);
	
//	@Query(value="SELECT new Role(r.id, r.nome) FROM Role r ")
//	List<Role> findRoles();
	
	@Query("select new Role(r.id, r.name) from Role r")
    List<Role> findRoleAll();
}
