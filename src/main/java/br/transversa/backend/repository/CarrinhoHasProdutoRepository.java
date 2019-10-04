package br.transversa.backend.repository;



import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import br.transversa.backend.model.Carrinho;
import br.transversa.backend.model.CarrinhosHasProduto;
import br.transversa.backend.model.Produto;




@Repository
public interface CarrinhoHasProdutoRepository extends JpaRepository<CarrinhosHasProduto, Long> {
    //Optional<Role> findByName(RoleName roleName);
//	@Query(value = "SELECT * FROM carrinhos_has_produtos INNER JOIN carrinhos ON carrinhos_has_produtos.carrinhos_id = carrinhos.id_carrinho WHERE carrinhos.users_id_cliente = ?1",  nativeQuery = true)
//	List<CarrinhosHasProduto> findProdutosDoCliente(Long idCliente);
	
	
	Long deleteByCarrinhoAndProduto(Carrinho carrinho, Produto produto);
	
	@Query("select new CarrinhosHasProduto(chp.id) from CarrinhosHasProduto chp WHERE chp.carrinho.id = :carrinhoId AND chp.produto.id = :produtoId")
	Optional<CarrinhosHasProduto> findCarrinhoByCarrinhoIdAndProdutoId(Long carrinhoId,Long produtoId);
	
//	@Query( value = "DELETE chp FROM carrinhos_has_produtos AS chp \n" + 
//			"INNER JOIN carrinhos AS c ON chp.carrinhos_id = c.id_carrinho \n" + 
//			"WHERE c.users_id_cliente = 1", nativeQuery = true)
	Long deleteByCarrinho(Carrinho carrinho);

//	@Query(value = "SELECT new CarrinhoHasProduto(chp.id, chp.quantidade) FROM CarrinhoHasProduto chp ")
//	List<CarrinhosHasProduto>findCarrinhos();
}
