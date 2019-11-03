package br.transversa.backend.model;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import org.hibernate.annotations.GenericGenerator;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;


@Entity
@Table(name = "users")
@NamedQuery(name="User.findAll", query="SELECT u FROM User u")
@JsonInclude(value = Include.NON_EMPTY)
public class User {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="id_user")
	private Long id;
	
	@Lob
	private byte[] data;
	
	@GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
	private String uuid;
	
	@Column(name="file_type")
	private String fileType;
	
	@Column(name="cpf_cnpj")
	private String cpfCnpj;

	private String celular;

	@Column(name="data_adicionado_user")
	private Timestamp dataAdicionado;

	private String email;

	@Column(name="razao_social")
	private String nome;

	@JsonIgnore
	private String senha;

	@Column(name="nome_fantasia")
	private String sobrenome;
	
	private String endereco;
	
	private Double latitude;
	
	private Double longitude;
	
	private String observacao;
	
	private String cep;
	
	@Column(name="inscricao_estadual")
	private String inscricaoEstadual;

	//bi-directional many-to-one association to Carrinho
	@OneToMany(mappedBy="user1")
	private List<Carrinho> carrinhos1;

	//bi-directional many-to-one association to Carrinho
	@OneToMany(mappedBy="user2")
	private List<Carrinho> carrinhos2;

	//bi-directional many-to-one association to EstadoPedido
	@OneToMany(mappedBy="user")
	private List<EstadoPedido> estadoPedidos;

	//bi-directional many-to-one association to Pedido
	@OneToMany(mappedBy="user1")
	private List<Pedido> pedidos1;

	//bi-directional many-to-one association to Pedido
	@OneToMany(mappedBy="user2")
	private List<Pedido> pedidos2;

	//bi-directional many-to-one association to ProdutoHasTipoProdutosSubstituto
	@OneToMany(mappedBy="user")
	private List<ProdutoHasTipoProdutosSubstituto> produtoHasTipoProdutosSubstitutos;

	//bi-directional many-to-one association to Produto
	@OneToMany(mappedBy="user")
	private List<Produto> produtos;

	//bi-directional many-to-one association to ProdutosHasTipoProduto
	@OneToMany(mappedBy="user")
	private List<ProdutosHasTipoProduto> produtosHasTipoProdutos;

	//bi-directional many-to-one association to ProdutosHasTipoProdutosComplementar
	@OneToMany(mappedBy="user")
	private List<ProdutosHasTipoProdutosComplementar> produtosHasTipoProdutosComplementars;

	//bi-directional many-to-one association to TipoProduto
	@OneToMany(mappedBy="user")
	private List<TipoProduto> tipoProdutos;

	//bi-directional many-to-one association to UserHasRole
	@OneToMany(mappedBy="user")
	private List<UserHasRole> userHasRoles;

	//bi-directional many-to-one association to User
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="created_by_id")
	private User user;
	
	//bi-directional many-to-one association to User
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="assigned_to_id")
	private User user2;

	//bi-directional many-to-one association to User
	@OneToMany(mappedBy="user")
	private List<User> users;

	//bi-directional many-to-one association to Venda
	@OneToMany(mappedBy="user1")
	private List<Venda> vendas1;

	//bi-directional many-to-one association to Venda
	@OneToMany(mappedBy="user2")
	private List<Venda> vendas2;
	
	//bi-directional many-to-one association to Pesquisapreco
	@OneToMany(mappedBy="user")
	private List<Pesquisapreco> pesquisaprecos;
	
	//bi-directional many-to-one association to Pesquisapreco
	@OneToMany(mappedBy="user")
	private List<Automovel> automovels;
	
	private BigDecimal comissao;
	
	
	
	public User(String cpfCnpj) {
		super();
		this.cpfCnpj = cpfCnpj;
	}


	public User(Long id, String nome, String sobrenome, String cpfCnpj, String email, String celular, String uuid,     
			Double latitude, Double longitude, String endereco) {
		super();
		this.id = id;
		this.uuid = uuid;
		this.cpfCnpj = cpfCnpj;
		this.celular = celular;
		this.email = email;
		this.nome = nome;
		this.sobrenome = sobrenome;
		this.endereco = endereco;
		this.latitude = latitude;
		this.longitude = longitude;
	}
	
	
	public User(Long id, String nome) {
		super();
		this.id = id;
		this.nome = nome;
	}
	
	

	public User(Long id) {
		super();
		this.id = id;
	}


	public byte[] getData() {
		return data;
	}



	public void setData(byte[] data) {
		this.data = data;
	}



	public String getCpfCnpj() {
		return cpfCnpj;
	}



	public void setCpfCnpj(String cpfCnpj) {
		this.cpfCnpj = cpfCnpj;
	}



	public String getUuid() {
		return uuid;
	}



	public void setUuid(String uuid) {
		this.uuid = uuid;
	}


	public String getFileType() {
		return fileType;
	}



	public void setFileType(String fileType) {
		this.fileType = fileType;
	}



	public User() {
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getCelular() {
		return this.celular;
	}

	public void setCelular(String celular) {
		this.celular = celular;
	}

	public Timestamp getDataAdicionado() {
		return this.dataAdicionado;
	}

	public void setDataAdicionado(Timestamp dataAdicionado) {
		this.dataAdicionado = dataAdicionado;
	}

	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getNome() {
		return this.nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getSenha() {
		return this.senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

	public String getSobrenome() {
		return this.sobrenome;
	}

	public void setSobrenome(String sobrenome) {
		this.sobrenome = sobrenome;
	}
	
	

	public String getObservacao() {
		return observacao;
	}


	public void setObservacao(String observacao) {
		this.observacao = observacao;
	}
	
	


	public String getInscricaoEstadual() {
		return inscricaoEstadual;
	}


	public void setInscricaoEstadual(String inscricaoEstadual) {
		this.inscricaoEstadual = inscricaoEstadual;
	}


	public List<Carrinho> getCarrinhos1() {
		return this.carrinhos1;
	}

	public void setCarrinhos1(List<Carrinho> carrinhos1) {
		this.carrinhos1 = carrinhos1;
	}

	public Carrinho addCarrinhos1(Carrinho carrinhos1) {
		getCarrinhos1().add(carrinhos1);
		carrinhos1.setUser1(this);

		return carrinhos1;
	}

	public Carrinho removeCarrinhos1(Carrinho carrinhos1) {
		getCarrinhos1().remove(carrinhos1);
		carrinhos1.setUser1(null);

		return carrinhos1;
	}

	public List<Carrinho> getCarrinhos2() {
		return this.carrinhos2;
	}

	public void setCarrinhos2(List<Carrinho> carrinhos2) {
		this.carrinhos2 = carrinhos2;
	}

	public Carrinho addCarrinhos2(Carrinho carrinhos2) {
		getCarrinhos2().add(carrinhos2);
		carrinhos2.setUser2(this);

		return carrinhos2;
	}

	public Carrinho removeCarrinhos2(Carrinho carrinhos2) {
		getCarrinhos2().remove(carrinhos2);
		carrinhos2.setUser2(null);

		return carrinhos2;
	}

	public List<ProdutoHasTipoProdutosSubstituto> getProdutoHasTipoProdutosSubstitutos() {
		return this.produtoHasTipoProdutosSubstitutos;
	}

	public void setProdutoHasTipoProdutosSubstitutos(
			List<ProdutoHasTipoProdutosSubstituto> produtoHasTipoProdutosSubstitutos) {
		this.produtoHasTipoProdutosSubstitutos = produtoHasTipoProdutosSubstitutos;
	}

	public ProdutoHasTipoProdutosSubstituto addProdutoHasTipoProdutosSubstituto(
			ProdutoHasTipoProdutosSubstituto produtoHasTipoProdutosSubstituto) {
		getProdutoHasTipoProdutosSubstitutos().add(produtoHasTipoProdutosSubstituto);
		produtoHasTipoProdutosSubstituto.setUser(this);

		return produtoHasTipoProdutosSubstituto;
	}

	public ProdutoHasTipoProdutosSubstituto removeProdutoHasTipoProdutosSubstituto(
			ProdutoHasTipoProdutosSubstituto produtoHasTipoProdutosSubstituto) {
		getProdutoHasTipoProdutosSubstitutos().remove(produtoHasTipoProdutosSubstituto);
		produtoHasTipoProdutosSubstituto.setUser(null);

		return produtoHasTipoProdutosSubstituto;
	}

	public List<Produto> getProdutos() {
		return this.produtos;
	}

	public void setProdutos(List<Produto> produtos) {
		this.produtos = produtos;
	}

	public Produto addProduto(Produto produto) {
		getProdutos().add(produto);
		produto.setUser(this);

		return produto;
	}
	
	

	public String getCep() {
		return cep;
	}


	public void setCep(String cep) {
		this.cep = cep;
	}


	public String getEndereco() {
		return endereco;
	}



	public void setEndereco(String endereco) {
		this.endereco = endereco;
	}



	public Double getLatitude() {
		return latitude;
	}



	public void setLatitude(Double latitude) {
		this.latitude = latitude;
	}



	public Double getLongitude() {
		return longitude;
	}



	public void setLongitude(Double longitude) {
		this.longitude = longitude;
	}



	public Produto removeProduto(Produto produto) {
		getProdutos().remove(produto);
		produto.setUser(null);

		return produto;
	}

	public List<ProdutosHasTipoProduto> getProdutosHasTipoProdutos() {
		return this.produtosHasTipoProdutos;
	}

	public void setProdutosHasTipoProdutos(List<ProdutosHasTipoProduto> produtosHasTipoProdutos) {
		this.produtosHasTipoProdutos = produtosHasTipoProdutos;
	}

	public ProdutosHasTipoProduto addProdutosHasTipoProduto(ProdutosHasTipoProduto produtosHasTipoProduto) {
		getProdutosHasTipoProdutos().add(produtosHasTipoProduto);
		produtosHasTipoProduto.setUser(this);

		return produtosHasTipoProduto;
	}

	public ProdutosHasTipoProduto removeProdutosHasTipoProduto(ProdutosHasTipoProduto produtosHasTipoProduto) {
		getProdutosHasTipoProdutos().remove(produtosHasTipoProduto);
		produtosHasTipoProduto.setUser(null);

		return produtosHasTipoProduto;
	}

	public List<ProdutosHasTipoProdutosComplementar> getProdutosHasTipoProdutosComplementars() {
		return this.produtosHasTipoProdutosComplementars;
	}

	public void setProdutosHasTipoProdutosComplementars(
			List<ProdutosHasTipoProdutosComplementar> produtosHasTipoProdutosComplementars) {
		this.produtosHasTipoProdutosComplementars = produtosHasTipoProdutosComplementars;
	}

	public ProdutosHasTipoProdutosComplementar addProdutosHasTipoProdutosComplementar(
			ProdutosHasTipoProdutosComplementar produtosHasTipoProdutosComplementar) {
		getProdutosHasTipoProdutosComplementars().add(produtosHasTipoProdutosComplementar);
		produtosHasTipoProdutosComplementar.setUser(this);

		return produtosHasTipoProdutosComplementar;
	}

	public ProdutosHasTipoProdutosComplementar removeProdutosHasTipoProdutosComplementar(
			ProdutosHasTipoProdutosComplementar produtosHasTipoProdutosComplementar) {
		getProdutosHasTipoProdutosComplementars().remove(produtosHasTipoProdutosComplementar);
		produtosHasTipoProdutosComplementar.setUser(null);

		return produtosHasTipoProdutosComplementar;
	}

	public List<TipoProduto> getTipoProdutos() {
		return this.tipoProdutos;
	}

	public void setTipoProdutos(List<TipoProduto> tipoProdutos) {
		this.tipoProdutos = tipoProdutos;
	}

	public TipoProduto addTipoProduto(TipoProduto tipoProduto) {
		getTipoProdutos().add(tipoProduto);
		tipoProduto.setUser(this);

		return tipoProduto;
	}

	public TipoProduto removeTipoProduto(TipoProduto tipoProduto) {
		getTipoProdutos().remove(tipoProduto);
		tipoProduto.setUser(null);

		return tipoProduto;
	}

	public List<UserHasRole> getUserHasRoles() {
		return this.userHasRoles;
	}

	public void setUserHasRoles(List<UserHasRole> userHasRoles) {
		this.userHasRoles = userHasRoles;
	}

	public UserHasRole addUserHasRole(UserHasRole userHasRole) {
		getUserHasRoles().add(userHasRole);
		userHasRole.setUser(this);

		return userHasRole;
	}

	public UserHasRole removeUserHasRole(UserHasRole userHasRole) {
		getUserHasRoles().remove(userHasRole);
		userHasRole.setUser(null);

		return userHasRole;
	}

	public List<Venda> getVendas1() {
		return this.vendas1;
	}

	public void setVendas1(List<Venda> vendas1) {
		this.vendas1 = vendas1;
	}

	public Venda addVendas1(Venda vendas1) {
		getVendas1().add(vendas1);
		vendas1.setUser1(this);

		return vendas1;
	}

	public Venda removeVendas1(Venda vendas1) {
		getVendas1().remove(vendas1);
		vendas1.setUser1(null);

		return vendas1;
	}

	public List<Venda> getVendas2() {
		return this.vendas2;
	}

	public void setVendas2(List<Venda> vendas2) {
		this.vendas2 = vendas2;
	}

	public Venda addVendas2(Venda vendas2) {
		getVendas2().add(vendas2);
		vendas2.setUser2(this);

		return vendas2;
	}

	public Venda removeVendas2(Venda vendas2) {
		getVendas2().remove(vendas2);
		vendas2.setUser2(null);

		return vendas2;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public List<User> getUsers() {
		return users;
	}

	public void setUsers(List<User> users) {
		this.users = users;
	}

	public List<EstadoPedido> getEstadoPedidos() {
		return estadoPedidos;
	}

	public void setEstadoPedidos(List<EstadoPedido> estadoPedidos) {
		this.estadoPedidos = estadoPedidos;
	}

	public List<Pedido> getPedidos1() {
		return pedidos1;
	}

	public void setPedidos1(List<Pedido> pedidos1) {
		this.pedidos1 = pedidos1;
	}

	public List<Pedido> getPedidos2() {
		return pedidos2;
	}

	public void setPedidos2(List<Pedido> pedidos2) {
		this.pedidos2 = pedidos2;
	}


	public User getUser2() {
		return user2;
	}


	public void setUser2(User user2) {
		this.user2 = user2;
	}

	public List<Pesquisapreco> getPesquisaprecos() {
		return this.pesquisaprecos;
	}

	public void setPesquisaprecos(List<Pesquisapreco> pesquisaprecos) {
		this.pesquisaprecos = pesquisaprecos;
	}

	public Pesquisapreco addPesquisapreco(Pesquisapreco pesquisapreco) {
		getPesquisaprecos().add(pesquisapreco);
		pesquisapreco.setUser(this);

		return pesquisapreco;
	}

	public Pesquisapreco removePesquisapreco(Pesquisapreco pesquisapreco) {
		getPesquisaprecos().remove(pesquisapreco);
		pesquisapreco.setUser(null);

		return pesquisapreco;
	}


	public BigDecimal getComissao() {
		return comissao;
	}


	public void setComissao(BigDecimal comissao) {
		this.comissao = comissao;
	}
	
	
	
}