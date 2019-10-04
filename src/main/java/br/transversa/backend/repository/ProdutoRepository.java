package br.transversa.backend.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import br.transversa.backend.model.Produto;


@Repository
public interface ProdutoRepository extends JpaRepository<Produto, Long> {
	
	
	//@Query(value = "SELECT * FROM carrinhos_has_produtos INNER JOIN carrinhos ON carrinhos_has_produtos.carrinhos_id = carrinhos.id_carrinho INNER JOIN produtos ON carrinhos_has_produtos.produtos_id = produtos.id_produto WHERE carrinhos.users_id_cliente = ?1",  nativeQuery = true)
	Optional<Produto> findById(Long id);

	@Query("select new Produto(p.id, p.nome, p.preco, p.uuid) from Produto p")
    Page<Produto> findProdutoAll(Pageable pageable);
	
	@Query(value = "select new Produto(p.nome, p.data, p.fileType) from Produto p WHERE p.uuid = :uuid")
	Produto findProdutoByUuid(String uuid);
	
	@Query("select new Produto(p.id, p.descricao, p.nome, p.preco, p.uuid) from Produto p WHERE p.id = :id")
	Produto findProdutoById(Long id);
	
	@Query("select new Produto(p.preco) from Produto p WHERE p.id = :id")
	Optional<Produto> findProdutoPrecoById(Long id);
	

	
	
	@Query(value = "SELECT new Produto(p.id, p.nome, p.preco, p.uuid, chp.quantidade) FROM Produto p  "
			+ "INNER JOIN CarrinhosHasProduto chp ON p.id = chp.produto "
			+ "INNER JOIN Carrinho c ON chp.carrinho = c.id WHERE c.user2.id = :idCliente")
	List<Produto>findProdutosNoCarrinhoCliente( Long idCliente); 
	
	
	@Query(value = "SELECT new Produto(p.id, p.nome, p.preco, p.uuid, chp.quantidade, c.id) FROM Produto p  "
			+ "INNER JOIN CarrinhosHasProduto chp ON p.id = chp.produto "
			+ "INNER JOIN Carrinho c ON chp.carrinho = c.id WHERE c.user2.id = :idCliente")
	List<Produto>findProdutosNoCarrinhoClienteWithIdCarrinho( Long idCliente); 

	@Query(value="SELECT new Produto(p.id, p.descricao, p.nome, p.preco, p.uuid) FROM Produto p  "
			+ "WHERE p.nome LIKE CONCAT('%',:nome,'%')")
	Page<Produto> findProdutoByNome(@Param("nome") String nome, Pageable pageable);
	
	
	@Query(value="SELECT new Produto(p.id, p.descricao, p.nome, p.preco, p.uuid) FROM Produto p")
	List<Produto>findAllProdutos();
	
}
