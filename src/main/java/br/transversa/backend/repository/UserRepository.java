package br.transversa.backend.repository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import br.transversa.backend.model.User;

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
	@Query(value = "SELECT * FROM users INNER JOIN user_has_roles on users.id = user_has_roles.users_id "
			+ "WHERE users.email = ?1",  nativeQuery = true)
	User findByEmailAddress(String emailAddress);
	
	
	@Query("select new User(u.id, u.nome, u.sobrenome, u.cpf, u.email, u.celular, u.uuid, u.latitude, u.longitude, "
			+ "u.logradouro) from User u")
    Page<User> findUserAll(Pageable pageable);
	
	@Query("select new User(u.fotoEstabelecimento, 0, 0, 0, u.fileType) from User u WHERE u.uuid = :uuid")
    User findFotoEstabelecimentoByUuid(String uuid);
	
	@Query("select new User(u.fotoDocumento, 0, 0, u.fileType) from User u WHERE u.uuid = :uuid")
    User findFotoDocumentoByUuid(String uuid);
	
	@Query(value="SELECT new User(u.id, u.nome, u.sobrenome, u.cpf, u.email, u.celular, u.uuid, u.latitude, u.longitude, "
			+ "u.logradouro) from User u  "
			+ "WHERE u.nome LIKE CONCAT('%',:nome,'%')")
	Page<User> findUserByNome(@Param("nome") String nome, Pageable pageable);
	
	@Query(value="SELECT new User(u.id, u.nome, u.sobrenome, u.cpf, u.email, u.celular, "
			+ "u.uuid, u.latitude, u.longitude, u.logradouro, u.cnpj, u.whatsapp, u.fixo, u.pontoReferencia1, "
			+ "u.pontoReferencia2, u.observacao, u.cep, u.casaNumero, u.cidade, u.inscricaoEstadual, "
			+ "u.tipoEstabelecimento, u.bairro, u.comissao) "
			+ " from User u "
			+ " WHERE u.id = :id")
	Optional<User> findUserById(@Param("id") Long id);
	
	
	
//	@Query(value="SELECT new User(u.id, u.nome, u.sobrenome, u.cpf, u.email, u.celular, u.uuid, u.latitude, u.longitude, "
//			+ "u.logradouro) from User u  "
//			+ "WHERE "
//			+ 	"CASE "
//			+ 		"WHEN nome < 1 THEN "
//			+ 							" u.nome = :nome "
//			+ 		"ELSE "
//			+ 			" u.nome = :nome "
//			+ 	"END")
//			+ 			"WHERE u.nome = -1")
	
////	SELECT e.name, CASE WHEN (u.nome = 1) THEN 1 ELSE 0 END from Employee e
//	@Query(value="SELECT new User(u.id, CASE WHEN (u.nome = 1) THEN '1' ELSE u.nome END, u.sobrenome, u.cpf, u.email, u.celular, u.uuid, u.latitude, u.longitude, "
//			+ "u.logradouro) from User u  "
//			+ "")
////			+ "WHERE u.nome LIKE CONCAT('%',:nome,'%')")
//	Page<User> findUserByFields(@Param("nome") String nome,
//			Pageable pageable);
	

//	CASE WHEN (e.salary >= 100000) THEN 1 WHEN (e.salary < 100000) THEN 2 ELSE 0 END FROM Employee e

	
//	WHERE 
//
//	CASE WHEN 1 = 2 THEN 
//		dbfrotas.users.id_user = 1
//	ELSE
//		dbfrotas.users.id_user = 2
//	END
//	
//	
//	SELECT e.name, 
//		CASE WHEN (e.salary >= 100000) THEN 1 
//		WHEN (e.salary < 100000) THEN 2 
//		ELSE 0 END FROM Employee e


	@Query("select new User(u.id, u.nome, u.sobrenome, u.cpf, u.email, u.celular, u.uuid, "
			+ "ROUND(u.latitude,7), ROUND(u.longitude,7), u.logradouro) from User u ")
	List<User> findAllUsers();
	
	
	@Query("select new User(u.id, u.nome, u.sobrenome, u.cpf, u.email, u.celular, "
			+ "u.uuid, u.latitude, u.longitude, u.logradouro, u.cnpj) from User u WHERE u.user2.id = :id")
	List<User> listAllClientesDoUser(Long id);
	
	
	@Query("select new User(u.id, u.nome, u.sobrenome, u.cpf, u.email, u.celular, "
			+ "u.uuid, u.latitude, u.longitude, u.logradouro, u.cnpj, u.whatsapp, u.fixo, u.pontoReferencia1, "
			+ "u.pontoReferencia2, u.observacao, u.cep, u.casaNumero, u.cidade, u.inscricaoEstadual, "
			+ "u.tipoEstabelecimento, u.bairro) from User u WHERE u.user2.id = :id")
	List<User> listAllClientesDoUserVOffline(Long id);

	@Query("select new User(u.id) from User u WHERE u.id = :idCliente AND u.user2.id = :idVendedor")
	Optional<User> findIfVendedorHasCliente(Long idVendedor, Long idCliente);
	
	@Query("select new User(u.cpf) from User u WHERE u.email = :email")
	User findUserCpfCnpjByEmail(String email);
	
	@Modifying
	@Query("UPDATE User u set u.senha = :senha where u.id = :id")
	void ChangePassword(String senha, Long id);
	
	@Modifying
	@Query("UPDATE User u set u.comissao = :comissao where u.id = :id")
	void ChangeComissao(BigDecimal comissao, Long id);

	@Query("select new User(u.id, u.nome, u.sobrenome, u.cpf, u.email, u.celular, "
			+ "u.uuid, u.latitude, u.longitude, u.logradouro) from User u WHERE u.user2.id = :idVendedor")
	Page<User> findClientesVendedorByPage(Pageable pageable, long idVendedor);
}
