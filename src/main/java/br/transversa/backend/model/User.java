package br.transversa.backend.model;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.Timestamp;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.Lob;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.GenericGenerator;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import br.transversa.backend.util.AppConstants;

@Entity
@Table(name = "users")
@NamedQuery(name = "User.findAll", query = "SELECT u FROM User u")
@JsonInclude(value = Include.NON_EMPTY)
public class User {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id_user", unique=true, nullable=false)
	private Long id;

	@Column(nullable=false)
	private byte ativo;

	@Column(length=45)
	private String bairro;

	@Column(name="casa_numero", length=20)
	private String casaNumero;

	@Column(length=13)
	private String celular;

	@Column(length=15)
	private String cep;

	@Column(length=35)
	private String cidade;

	@Column(length=25)
	private String cnpj;

	@Column(precision=10, scale=2)
	private BigDecimal comissao;

	@Column(nullable=false, length=25)
	private String cpf;

	@Column(name="data_adicionado_user", nullable=false)
	private Timestamp dataAdicionadoUser;

	@Column(nullable=false, length=45)
	private String email;

	@Column(name="file_type", length=15)
	private String fileType;

	@Column(length=13)
	private String fixo;

	@Lob
	@Column(name="foto_documento")
	private byte[] fotoDocumento;

	@Lob
	@Column(name="foto_estabelecimento")
	private byte[] fotoEstabelecimento;

	@Column(name="inscricao_estadual", length=15)
	private String inscricaoEstadual;

	private double latitude;

	@Column(length=45)
	private String logradouro;

	private double longitude;

	@Column(name="nome_fantasia", nullable=false, length=45)
	private String sobrenome;

	@Column(length=45)
	private String observacao;

	@Column(name="ponto_referencia1", length=45)
	private String pontoReferencia1;

	@Column(name="ponto_referencia2", length=45)
	private String pontoReferencia2;

	@Column(name="razao_social", nullable=false, length=45)
	private String nome;

	@Column(nullable=false, length=80)
	private String senha;

	@Column(name="tipo_estabelecimento")
	private Integer tipoEstabelecimento;

	@Column(length=45)
	private String uuid;

	@Column(nullable=false, length=13)
	private String whatsapp;
	
	@Transient
	private String tipoEstabelecimento2;

	//bi-directional many-to-one association to Automovel
	@OneToMany(mappedBy="user")
	private List<Automovel> automovels;

	//bi-directional many-to-one association to Carrinho
	@OneToMany(mappedBy="user1")
	private List<Carrinho> carrinhos1;

	//bi-directional many-to-one association to Carrinho
	@OneToMany(mappedBy="user2")
	private List<Carrinho> carrinhos2;

	//bi-directional many-to-one association to EntradaProdutoStock
	@OneToMany(mappedBy="user")
	private List<EntradaProdutoStock> entradaProdutoStocks;

	//bi-directional many-to-one association to EntregadorPedido
	@OneToMany(mappedBy="user")
	private List<EntregadorPedido> entregadorPedidos;

	//bi-directional many-to-one association to EstadoPedido
	@OneToMany(mappedBy="user")
	private List<EstadoPedido> estadoPedidos;

	//bi-directional many-to-one association to Pedido
	@OneToMany(mappedBy="user1")
	private List<Pedido> pedidos1;

	//bi-directional many-to-one association to Pedido
	@OneToMany(mappedBy="user2")
	private List<Pedido> pedidos2;

	//bi-directional many-to-one association to Pesquisapreco
	@OneToMany(mappedBy="user")
	private List<Pesquisapreco> pesquisaprecos;

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

	//bi-directional many-to-one association to Promocoes
	@OneToMany(mappedBy="user")
	private List<Promocoes> promocoes;

	//bi-directional many-to-one association to Quilometragem
	@OneToMany(mappedBy="user")
	private List<Quilometragem> quilometragems;

	//bi-directional many-to-one association to SaidaProdutoStock
	@OneToMany(mappedBy="user")
	private List<SaidaProdutoStock> saidaProdutoStocks;

	//bi-directional many-to-one association to TipoProduto
	@OneToMany(mappedBy="user")
	private List<TipoProduto> tipoProdutos;

	//bi-directional many-to-one association to UserHasRole
	@OneToMany(mappedBy="user")
	private List<UserHasRole> userHasRoles;

	//bi-directional many-to-many association to Pedido
	@ManyToMany
	@JoinTable(
		name="pedidos_has_users"
		, joinColumns={
			@JoinColumn(name="users_id_user", nullable=false)
			}
		, inverseJoinColumns={
			@JoinColumn(name="pedidos_id_pedidos", nullable=false)
			}
		)
	private List<Pedido> pedidos3;

	//bi-directional many-to-one association to User
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="created_by_id")
	private User user1;

	//bi-directional many-to-one association to User
	@OneToMany(mappedBy="user1")
	private List<User> users1;

	//bi-directional many-to-one association to User
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="assigned_to_id")
	private User user2;

	//bi-directional many-to-one association to User
	@OneToMany(mappedBy="user2")
	private List<User> users2;

	//bi-directional many-to-one association to Venda
	@OneToMany(mappedBy="user1")
	private List<Venda> vendas1;

	//bi-directional many-to-one association to Venda
	@OneToMany(mappedBy="user2")
	private List<Venda> vendas2;

	public User() {
	}

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public byte getAtivo() {
		return this.ativo;
	}

	public void setAtivo(byte ativo) {
		this.ativo = ativo;
	}

	public String getBairro() {
		return this.bairro;
	}

	public void setBairro(String bairro) {
		this.bairro = bairro;
	}

	public String getCasaNumero() {
		return this.casaNumero;
	}

	public void setCasaNumero(String casaNumero) {
		this.casaNumero = casaNumero;
	}

	public String getCelular() {
		return this.celular;
	}

	public void setCelular(String celular) {
		this.celular = celular;
	}

	public String getCep() {
		return this.cep;
	}

	public void setCep(String cep) {
		this.cep = cep;
	}

	public String getCidade() {
		return this.cidade;
	}

	public void setCidade(String cidade) {
		this.cidade = cidade;
	}

	public String getCnpj() {
		return this.cnpj;
	}

	public void setCnpj(String cnpj) {
		this.cnpj = cnpj;
	}

	public BigDecimal getComissao() {
		return this.comissao;
	}

	public void setComissao(BigDecimal comissao) {
		this.comissao = comissao;
	}

	public String getCpf() {
		return this.cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

	public Timestamp getDataAdicionadoUser() {
		return this.dataAdicionadoUser;
	}

	public void setDataAdicionadoUser(Timestamp dataAdicionadoUser) {
		this.dataAdicionadoUser = dataAdicionadoUser;
	}

	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getFileType() {
		return this.fileType;
	}

	public void setFileType(String fileType) {
		this.fileType = fileType;
	}

	public String getFixo() {
		return this.fixo;
	}

	public void setFixo(String fixo) {
		this.fixo = fixo;
	}

	public byte[] getFotoDocumento() {
		return this.fotoDocumento;
	}

	public void setFotoDocumento(byte[] fotoDocumento) {
		this.fotoDocumento = fotoDocumento;
	}

	public byte[] getFotoEstabelecimento() {
		return this.fotoEstabelecimento;
	}

	public void setFotoEstabelecimento(byte[] fotoEstabelecimento) {
		this.fotoEstabelecimento = fotoEstabelecimento;
	}

	public String getInscricaoEstadual() {
		return this.inscricaoEstadual;
	}

	public void setInscricaoEstadual(String inscricaoEstadual) {
		this.inscricaoEstadual = inscricaoEstadual;
	}

	public double getLatitude() {
		return this.latitude;
	}

	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}

	public String getLogradouro() {
		return this.logradouro;
	}

	public void setLogradouro(String logradouro) {
		this.logradouro = logradouro;
	}

	public double getLongitude() {
		return this.longitude;
	}

	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}



	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getObservacao() {
		return this.observacao;
	}

	public void setObservacao(String observacao) {
		this.observacao = observacao;
	}

	public String getPontoReferencia1() {
		return this.pontoReferencia1;
	}

	public void setPontoReferencia1(String pontoReferencia1) {
		this.pontoReferencia1 = pontoReferencia1;
	}

	public String getPontoReferencia2() {
		return this.pontoReferencia2;
	}

	public void setPontoReferencia2(String pontoReferencia2) {
		this.pontoReferencia2 = pontoReferencia2;
	}



	public String getSobrenome() {
		return sobrenome;
	}

	public void setSobrenome(String sobrenome) {
		this.sobrenome = sobrenome;
	}

	public String getSenha() {
		return this.senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

	public Integer getTipoEstabelecimento() {
		return this.tipoEstabelecimento;
	}

	public void setTipoEstabelecimento(Integer tipoEstabelecimento) {
		this.tipoEstabelecimento = tipoEstabelecimento;
	}

	public String getUuid() {
		return this.uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public String getWhatsapp() {
		return this.whatsapp;
	}

	public void setWhatsapp(String whatsapp) {
		this.whatsapp = whatsapp;
	}

	public List<Automovel> getAutomovels() {
		return this.automovels;
	}

	public void setAutomovels(List<Automovel> automovels) {
		this.automovels = automovels;
	}

	public Automovel addAutomovel(Automovel automovel) {
		getAutomovels().add(automovel);
		automovel.setUser(this);

		return automovel;
	}

	public Automovel removeAutomovel(Automovel automovel) {
		getAutomovels().remove(automovel);
		automovel.setUser(null);

		return automovel;
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

	public List<EntradaProdutoStock> getEntradaProdutoStocks() {
		return this.entradaProdutoStocks;
	}

	public void setEntradaProdutoStocks(List<EntradaProdutoStock> entradaProdutoStocks) {
		this.entradaProdutoStocks = entradaProdutoStocks;
	}

	public EntradaProdutoStock addEntradaProdutoStock(EntradaProdutoStock entradaProdutoStock) {
		getEntradaProdutoStocks().add(entradaProdutoStock);
		entradaProdutoStock.setUser(this);

		return entradaProdutoStock;
	}

	public EntradaProdutoStock removeEntradaProdutoStock(EntradaProdutoStock entradaProdutoStock) {
		getEntradaProdutoStocks().remove(entradaProdutoStock);
		entradaProdutoStock.setUser(null);

		return entradaProdutoStock;
	}

	public List<EntregadorPedido> getEntregadorPedidos() {
		return this.entregadorPedidos;
	}

	public void setEntregadorPedidos(List<EntregadorPedido> entregadorPedidos) {
		this.entregadorPedidos = entregadorPedidos;
	}

	public EntregadorPedido addEntregadorPedido(EntregadorPedido entregadorPedido) {
		getEntregadorPedidos().add(entregadorPedido);
		entregadorPedido.setUser(this);

		return entregadorPedido;
	}

	public EntregadorPedido removeEntregadorPedido(EntregadorPedido entregadorPedido) {
		getEntregadorPedidos().remove(entregadorPedido);
		entregadorPedido.setUser(null);

		return entregadorPedido;
	}

	public List<EstadoPedido> getEstadoPedidos() {
		return this.estadoPedidos;
	}

	public void setEstadoPedidos(List<EstadoPedido> estadoPedidos) {
		this.estadoPedidos = estadoPedidos;
	}

	public EstadoPedido addEstadoPedido(EstadoPedido estadoPedido) {
		getEstadoPedidos().add(estadoPedido);
		estadoPedido.setUser(this);

		return estadoPedido;
	}

	public EstadoPedido removeEstadoPedido(EstadoPedido estadoPedido) {
		getEstadoPedidos().remove(estadoPedido);
		estadoPedido.setUser(null);

		return estadoPedido;
	}

	public List<Pedido> getPedidos1() {
		return this.pedidos1;
	}

	public void setPedidos1(List<Pedido> pedidos1) {
		this.pedidos1 = pedidos1;
	}

	public Pedido addPedidos1(Pedido pedidos1) {
		getPedidos1().add(pedidos1);
		pedidos1.setUser1(this);

		return pedidos1;
	}

	public Pedido removePedidos1(Pedido pedidos1) {
		getPedidos1().remove(pedidos1);
		pedidos1.setUser1(null);

		return pedidos1;
	}

	public List<Pedido> getPedidos2() {
		return this.pedidos2;
	}

	public void setPedidos2(List<Pedido> pedidos2) {
		this.pedidos2 = pedidos2;
	}

	public Pedido addPedidos2(Pedido pedidos2) {
		getPedidos2().add(pedidos2);
		pedidos2.setUser2(this);

		return pedidos2;
	}

	public Pedido removePedidos2(Pedido pedidos2) {
		getPedidos2().remove(pedidos2);
		pedidos2.setUser2(null);

		return pedidos2;
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

	public List<ProdutoHasTipoProdutosSubstituto> getProdutoHasTipoProdutosSubstitutos() {
		return this.produtoHasTipoProdutosSubstitutos;
	}

	public void setProdutoHasTipoProdutosSubstitutos(List<ProdutoHasTipoProdutosSubstituto> produtoHasTipoProdutosSubstitutos) {
		this.produtoHasTipoProdutosSubstitutos = produtoHasTipoProdutosSubstitutos;
	}

	public ProdutoHasTipoProdutosSubstituto addProdutoHasTipoProdutosSubstituto(ProdutoHasTipoProdutosSubstituto produtoHasTipoProdutosSubstituto) {
		getProdutoHasTipoProdutosSubstitutos().add(produtoHasTipoProdutosSubstituto);
		produtoHasTipoProdutosSubstituto.setUser(this);

		return produtoHasTipoProdutosSubstituto;
	}

	public ProdutoHasTipoProdutosSubstituto removeProdutoHasTipoProdutosSubstituto(ProdutoHasTipoProdutosSubstituto produtoHasTipoProdutosSubstituto) {
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

	public void setProdutosHasTipoProdutosComplementars(List<ProdutosHasTipoProdutosComplementar> produtosHasTipoProdutosComplementars) {
		this.produtosHasTipoProdutosComplementars = produtosHasTipoProdutosComplementars;
	}

	public ProdutosHasTipoProdutosComplementar addProdutosHasTipoProdutosComplementar(ProdutosHasTipoProdutosComplementar produtosHasTipoProdutosComplementar) {
		getProdutosHasTipoProdutosComplementars().add(produtosHasTipoProdutosComplementar);
		produtosHasTipoProdutosComplementar.setUser(this);

		return produtosHasTipoProdutosComplementar;
	}

	public ProdutosHasTipoProdutosComplementar removeProdutosHasTipoProdutosComplementar(ProdutosHasTipoProdutosComplementar produtosHasTipoProdutosComplementar) {
		getProdutosHasTipoProdutosComplementars().remove(produtosHasTipoProdutosComplementar);
		produtosHasTipoProdutosComplementar.setUser(null);

		return produtosHasTipoProdutosComplementar;
	}

	public List<Promocoes> getPromocoes() {
		return this.promocoes;
	}

	public void setPromocoes(List<Promocoes> promocoes) {
		this.promocoes = promocoes;
	}

	public Promocoes addPromocoe(Promocoes promocoe) {
		getPromocoes().add(promocoe);
		promocoe.setUser(this);

		return promocoe;
	}

	public Promocoes removePromocoe(Promocoes promocoe) {
		getPromocoes().remove(promocoe);
		promocoe.setUser(null);

		return promocoe;
	}

	public List<Quilometragem> getQuilometragems() {
		return this.quilometragems;
	}

	public void setQuilometragems(List<Quilometragem> quilometragems) {
		this.quilometragems = quilometragems;
	}

	public Quilometragem addQuilometragem(Quilometragem quilometragem) {
		getQuilometragems().add(quilometragem);
		quilometragem.setUser(this);

		return quilometragem;
	}

	public Quilometragem removeQuilometragem(Quilometragem quilometragem) {
		getQuilometragems().remove(quilometragem);
		quilometragem.setUser(null);

		return quilometragem;
	}

	public List<SaidaProdutoStock> getSaidaProdutoStocks() {
		return this.saidaProdutoStocks;
	}

	public void setSaidaProdutoStocks(List<SaidaProdutoStock> saidaProdutoStocks) {
		this.saidaProdutoStocks = saidaProdutoStocks;
	}

	public SaidaProdutoStock addSaidaProdutoStock(SaidaProdutoStock saidaProdutoStock) {
		getSaidaProdutoStocks().add(saidaProdutoStock);
		saidaProdutoStock.setUser(this);

		return saidaProdutoStock;
	}

	public SaidaProdutoStock removeSaidaProdutoStock(SaidaProdutoStock saidaProdutoStock) {
		getSaidaProdutoStocks().remove(saidaProdutoStock);
		saidaProdutoStock.setUser(null);

		return saidaProdutoStock;
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

	public List<Pedido> getPedidos3() {
		return this.pedidos3;
	}

	public void setPedidos3(List<Pedido> pedidos3) {
		this.pedidos3 = pedidos3;
	}

	public User getUser1() {
		return this.user1;
	}

	public void setUser1(User user1) {
		this.user1 = user1;
	}

	public List<User> getUsers1() {
		return this.users1;
	}

	public void setUsers1(List<User> users1) {
		this.users1 = users1;
	}

	public User addUsers1(User users1) {
		getUsers1().add(users1);
		users1.setUser1(this);

		return users1;
	}

	public User removeUsers1(User users1) {
		getUsers1().remove(users1);
		users1.setUser1(null);

		return users1;
	}

	public User getUser2() {
		return this.user2;
	}

	public void setUser2(User user2) {
		this.user2 = user2;
	}

	public List<User> getUsers2() {
		return this.users2;
	}

	public void setUsers2(List<User> users2) {
		this.users2 = users2;
	}

	public User addUsers2(User users2) {
		getUsers2().add(users2);
		users2.setUser2(this);

		return users2;
	}

	public User removeUsers2(User users2) {
		getUsers2().remove(users2);
		users2.setUser2(null);

		return users2;
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

	public User(String cpf) {
		super();
		this.cpf = cpf;
	}

	public User(Long id, String nome, String sobrenome, String cpf, String email, String celular, String uuid,
			Double latitude, Double longitude, String logradouro) {
		super();
		this.id = id;
		this.uuid = uuid;
		this.cpf = cpf;
		this.celular = celular;
		this.email = email;
		this.nome = nome;
		this.sobrenome = sobrenome;
		this.logradouro = logradouro;
		this.latitude = latitude;
		this.longitude = longitude;
	}

	public User(Long id, String nome, String sobrenome, String cpf, String email, String celular, String uuid,
			Double latitude, Double longitude, String logradouro, String cnpj) {
		super();
		this.id = id;
		this.uuid = uuid;
		this.cpf = cpf;
		this.celular = celular;
		this.email = email;
		this.nome = nome;
		this.sobrenome = sobrenome;
		this.logradouro = logradouro;
		this.latitude = latitude;
		this.longitude = longitude;
		this.cnpj = cnpj;
	}
	
	public User(Long id, String nome, String sobrenome, String cpf, String email, String celular, String uuid,
			Double latitude, Double longitude, String logradouro, String cnpj, String whatsapp,
			String fixo, String pontoReferencia1, String pontoReferencia2, String observacao,
			String cep, String casaNumero, String cidade, String inscricaoEstadual, 
			Integer tipoEstabelecimento, String bairro
			) {
		super();
		this.id = id;
		this.nome = nome;
		this.sobrenome = sobrenome;
		this.cpf = cpf;
		this.email = email;
		this.celular = celular;
		this.uuid = uuid;
		this.latitude = latitude;
		this.longitude = longitude;
		this.logradouro = logradouro;
		this.cnpj = cnpj;
		this.whatsapp = whatsapp;
		this.fixo = fixo;
		this.pontoReferencia1 = pontoReferencia1;
		this.pontoReferencia2 = pontoReferencia2;
		this.observacao = observacao;
		this.cep = cep;
		this.casaNumero = casaNumero;
		this.cidade = cidade;
		this.inscricaoEstadual = inscricaoEstadual;
		this.tipoEstabelecimento2 = AppConstants.TIPO_ESTABELECIMENTOS.get(tipoEstabelecimento);
		this.bairro = bairro;
		
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

	public String getTipoEstabelecimento2() {
		return tipoEstabelecimento2;
	}

	public void setTipoEstabelecimento2(String tipoEstabelecimento2) {
		this.tipoEstabelecimento2 = tipoEstabelecimento2;
	}

	
	
	
	/*
	 * @Id
	 * 
	 * @GeneratedValue(strategy = GenerationType.IDENTITY)
	 * 
	 * @Column(name="id_user") private Long id;
	 * 
	 * @Lob private byte[] data;
	 * 
	 * @GeneratedValue(generator = "uuid")
	 * 
	 * @GenericGenerator(name = "uuid", strategy = "uuid2") private String uuid;
	 * 
	 * @Column(name="file_type") private String fileType;
	 * 
	 * @Column(name="cpf_cnpj") private String cpfCnpj;
	 * 
	 * private String celular;
	 * 
	 * @Column(name="data_adicionado_user") private Timestamp dataAdicionado;
	 * 
	 * private String email;
	 * 
	 * @Column(name="razao_social") private String nome;
	 * 
	 * @JsonIgnore private String senha;
	 * 
	 * @Column(name="nome_fantasia") private String sobrenome;
	 * 
	 * private String endereco;
	 * 
	 * private Double latitude;
	 * 
	 * private Double longitude;
	 * 
	 * private String observacao;
	 * 
	 * private String cep;
	 * 
	 * @Column(name="inscricao_estadual") private String inscricaoEstadual;
	 * 
	 * //bi-directional many-to-one association to Carrinho
	 * 
	 * @OneToMany(mappedBy="user1") private List<Carrinho> carrinhos1;
	 * 
	 * //bi-directional many-to-one association to Carrinho
	 * 
	 * @OneToMany(mappedBy="user2") private List<Carrinho> carrinhos2;
	 * 
	 * //bi-directional many-to-one association to EstadoPedido
	 * 
	 * @OneToMany(mappedBy="user") private List<EstadoPedido> estadoPedidos;
	 * 
	 * //bi-directional many-to-one association to Pedido
	 * 
	 * @OneToMany(mappedBy="user1") private List<Pedido> pedidos1;
	 * 
	 * //bi-directional many-to-one association to Pedido
	 * 
	 * @OneToMany(mappedBy="user2") private List<Pedido> pedidos2;
	 * 
	 * //bi-directional many-to-one association to ProdutoHasTipoProdutosSubstituto
	 * 
	 * @OneToMany(mappedBy="user") private List<ProdutoHasTipoProdutosSubstituto>
	 * produtoHasTipoProdutosSubstitutos;
	 * 
	 * //bi-directional many-to-one association to Produto
	 * 
	 * @OneToMany(mappedBy="user") private List<Produto> produtos;
	 * 
	 * //bi-directional many-to-one association to ProdutosHasTipoProduto
	 * 
	 * @OneToMany(mappedBy="user") private List<ProdutosHasTipoProduto>
	 * produtosHasTipoProdutos;
	 * 
	 * //bi-directional many-to-one association to
	 * ProdutosHasTipoProdutosComplementar
	 * 
	 * @OneToMany(mappedBy="user") private List<ProdutosHasTipoProdutosComplementar>
	 * produtosHasTipoProdutosComplementars;
	 * 
	 * //bi-directional many-to-one association to TipoProduto
	 * 
	 * @OneToMany(mappedBy="user") private List<TipoProduto> tipoProdutos;
	 * 
	 * //bi-directional many-to-one association to UserHasRole
	 * 
	 * @OneToMany(mappedBy="user") private List<UserHasRole> userHasRoles;
	 * 
	 * //bi-directional many-to-one association to User
	 * 
	 * @ManyToOne(fetch=FetchType.LAZY)
	 * 
	 * @JoinColumn(name="created_by_id") private User user;
	 * 
	 * //bi-directional many-to-one association to User
	 * 
	 * @ManyToOne(fetch=FetchType.LAZY)
	 * 
	 * @JoinColumn(name="assigned_to_id") private User user2;
	 * 
	 * //bi-directional many-to-one association to User
	 * 
	 * @OneToMany(mappedBy="user") private List<User> users;
	 * 
	 * //bi-directional many-to-one association to Venda
	 * 
	 * @OneToMany(mappedBy="user1") private List<Venda> vendas1;
	 * 
	 * //bi-directional many-to-one association to Venda
	 * 
	 * @OneToMany(mappedBy="user2") private List<Venda> vendas2;
	 * 
	 * //bi-directional many-to-one association to Pesquisapreco
	 * 
	 * @OneToMany(mappedBy="user") private List<Pesquisapreco> pesquisaprecos;
	 * 
	 * //bi-directional many-to-one association to Pesquisapreco
	 * 
	 * @OneToMany(mappedBy="user") private List<Automovel> automovels;
	 * 
	 * private BigDecimal comissao;
	 * 
	 * 
	 * 
	 * public User(String cpfCnpj) { super(); this.cpfCnpj = cpfCnpj; }
	 * 
	 * 
	 * public User(Long id, String nome, String sobrenome, String cpfCnpj, String
	 * email, String celular, String uuid, Double latitude, Double longitude, String
	 * endereco) { super(); this.id = id; this.uuid = uuid; this.cpfCnpj = cpfCnpj;
	 * this.celular = celular; this.email = email; this.nome = nome; this.sobrenome
	 * = sobrenome; this.endereco = endereco; this.latitude = latitude;
	 * this.longitude = longitude; }
	 * 
	 * 
	 * public User(Long id, String nome) { super(); this.id = id; this.nome = nome;
	 * }
	 * 
	 * 
	 * 
	 * public User(Long id) { super(); this.id = id; }
	 * 
	 * 
	 * public byte[] getData() { return data; }
	 * 
	 * 
	 * 
	 * public void setData(byte[] data) { this.data = data; }
	 * 
	 * 
	 * 
	 * public String getCpfCnpj() { return cpfCnpj; }
	 * 
	 * 
	 * 
	 * public void setCpfCnpj(String cpfCnpj) { this.cpfCnpj = cpfCnpj; }
	 * 
	 * 
	 * 
	 * public String getUuid() { return uuid; }
	 * 
	 * 
	 * 
	 * public void setUuid(String uuid) { this.uuid = uuid; }
	 * 
	 * 
	 * public String getFileType() { return fileType; }
	 * 
	 * 
	 * 
	 * public void setFileType(String fileType) { this.fileType = fileType; }
	 * 
	 * 
	 * 
	 * public User() { }
	 * 
	 * public Long getId() { return id; }
	 * 
	 * public void setId(Long id) { this.id = id; }
	 * 
	 * public String getCelular() { return this.celular; }
	 * 
	 * public void setCelular(String celular) { this.celular = celular; }
	 * 
	 * public Timestamp getDataAdicionado() { return this.dataAdicionado; }
	 * 
	 * public void setDataAdicionado(Timestamp dataAdicionado) { this.dataAdicionado
	 * = dataAdicionado; }
	 * 
	 * public String getEmail() { return this.email; }
	 * 
	 * public void setEmail(String email) { this.email = email; }
	 * 
	 * public String getNome() { return this.nome; }
	 * 
	 * public void setNome(String nome) { this.nome = nome; }
	 * 
	 * public String getSenha() { return this.senha; }
	 * 
	 * public void setSenha(String senha) { this.senha = senha; }
	 * 
	 * public String getSobrenome() { return this.sobrenome; }
	 * 
	 * public void setSobrenome(String sobrenome) { this.sobrenome = sobrenome; }
	 * 
	 * 
	 * 
	 * public String getObservacao() { return observacao; }
	 * 
	 * 
	 * public void setObservacao(String observacao) { this.observacao = observacao;
	 * }
	 * 
	 * 
	 * 
	 * 
	 * public String getInscricaoEstadual() { return inscricaoEstadual; }
	 * 
	 * 
	 * public void setInscricaoEstadual(String inscricaoEstadual) {
	 * this.inscricaoEstadual = inscricaoEstadual; }
	 * 
	 * 
	 * public List<Carrinho> getCarrinhos1() { return this.carrinhos1; }
	 * 
	 * public void setCarrinhos1(List<Carrinho> carrinhos1) { this.carrinhos1 =
	 * carrinhos1; }
	 * 
	 * public Carrinho addCarrinhos1(Carrinho carrinhos1) {
	 * getCarrinhos1().add(carrinhos1); carrinhos1.setUser1(this);
	 * 
	 * return carrinhos1; }
	 * 
	 * public Carrinho removeCarrinhos1(Carrinho carrinhos1) {
	 * getCarrinhos1().remove(carrinhos1); carrinhos1.setUser1(null);
	 * 
	 * return carrinhos1; }
	 * 
	 * public List<Carrinho> getCarrinhos2() { return this.carrinhos2; }
	 * 
	 * public void setCarrinhos2(List<Carrinho> carrinhos2) { this.carrinhos2 =
	 * carrinhos2; }
	 * 
	 * public Carrinho addCarrinhos2(Carrinho carrinhos2) {
	 * getCarrinhos2().add(carrinhos2); carrinhos2.setUser2(this);
	 * 
	 * return carrinhos2; }
	 * 
	 * public Carrinho removeCarrinhos2(Carrinho carrinhos2) {
	 * getCarrinhos2().remove(carrinhos2); carrinhos2.setUser2(null);
	 * 
	 * return carrinhos2; }
	 * 
	 * public List<ProdutoHasTipoProdutosSubstituto>
	 * getProdutoHasTipoProdutosSubstitutos() { return
	 * this.produtoHasTipoProdutosSubstitutos; }
	 * 
	 * public void setProdutoHasTipoProdutosSubstitutos(
	 * List<ProdutoHasTipoProdutosSubstituto> produtoHasTipoProdutosSubstitutos) {
	 * this.produtoHasTipoProdutosSubstitutos = produtoHasTipoProdutosSubstitutos; }
	 * 
	 * public ProdutoHasTipoProdutosSubstituto addProdutoHasTipoProdutosSubstituto(
	 * ProdutoHasTipoProdutosSubstituto produtoHasTipoProdutosSubstituto) {
	 * getProdutoHasTipoProdutosSubstitutos().add(produtoHasTipoProdutosSubstituto);
	 * produtoHasTipoProdutosSubstituto.setUser(this);
	 * 
	 * return produtoHasTipoProdutosSubstituto; }
	 * 
	 * public ProdutoHasTipoProdutosSubstituto
	 * removeProdutoHasTipoProdutosSubstituto( ProdutoHasTipoProdutosSubstituto
	 * produtoHasTipoProdutosSubstituto) {
	 * getProdutoHasTipoProdutosSubstitutos().remove(
	 * produtoHasTipoProdutosSubstituto);
	 * produtoHasTipoProdutosSubstituto.setUser(null);
	 * 
	 * return produtoHasTipoProdutosSubstituto; }
	 * 
	 * public List<Produto> getProdutos() { return this.produtos; }
	 * 
	 * public void setProdutos(List<Produto> produtos) { this.produtos = produtos; }
	 * 
	 * public Produto addProduto(Produto produto) { getProdutos().add(produto);
	 * produto.setUser(this);
	 * 
	 * return produto; }
	 * 
	 * 
	 * 
	 * public String getCep() { return cep; }
	 * 
	 * 
	 * public void setCep(String cep) { this.cep = cep; }
	 * 
	 * 
	 * public String getEndereco() { return endereco; }
	 * 
	 * 
	 * 
	 * public void setEndereco(String endereco) { this.endereco = endereco; }
	 * 
	 * 
	 * 
	 * public Double getLatitude() { return latitude; }
	 * 
	 * 
	 * 
	 * public void setLatitude(Double latitude) { this.latitude = latitude; }
	 * 
	 * 
	 * 
	 * public Double getLongitude() { return longitude; }
	 * 
	 * 
	 * 
	 * public void setLongitude(Double longitude) { this.longitude = longitude; }
	 * 
	 * 
	 * 
	 * public Produto removeProduto(Produto produto) {
	 * getProdutos().remove(produto); produto.setUser(null);
	 * 
	 * return produto; }
	 * 
	 * public List<ProdutosHasTipoProduto> getProdutosHasTipoProdutos() { return
	 * this.produtosHasTipoProdutos; }
	 * 
	 * public void setProdutosHasTipoProdutos(List<ProdutosHasTipoProduto>
	 * produtosHasTipoProdutos) { this.produtosHasTipoProdutos =
	 * produtosHasTipoProdutos; }
	 * 
	 * public ProdutosHasTipoProduto
	 * addProdutosHasTipoProduto(ProdutosHasTipoProduto produtosHasTipoProduto) {
	 * getProdutosHasTipoProdutos().add(produtosHasTipoProduto);
	 * produtosHasTipoProduto.setUser(this);
	 * 
	 * return produtosHasTipoProduto; }
	 * 
	 * public ProdutosHasTipoProduto
	 * removeProdutosHasTipoProduto(ProdutosHasTipoProduto produtosHasTipoProduto) {
	 * getProdutosHasTipoProdutos().remove(produtosHasTipoProduto);
	 * produtosHasTipoProduto.setUser(null);
	 * 
	 * return produtosHasTipoProduto; }
	 * 
	 * public List<ProdutosHasTipoProdutosComplementar>
	 * getProdutosHasTipoProdutosComplementars() { return
	 * this.produtosHasTipoProdutosComplementars; }
	 * 
	 * public void setProdutosHasTipoProdutosComplementars(
	 * List<ProdutosHasTipoProdutosComplementar>
	 * produtosHasTipoProdutosComplementars) {
	 * this.produtosHasTipoProdutosComplementars =
	 * produtosHasTipoProdutosComplementars; }
	 * 
	 * public ProdutosHasTipoProdutosComplementar
	 * addProdutosHasTipoProdutosComplementar( ProdutosHasTipoProdutosComplementar
	 * produtosHasTipoProdutosComplementar) {
	 * getProdutosHasTipoProdutosComplementars().add(
	 * produtosHasTipoProdutosComplementar);
	 * produtosHasTipoProdutosComplementar.setUser(this);
	 * 
	 * return produtosHasTipoProdutosComplementar; }
	 * 
	 * public ProdutosHasTipoProdutosComplementar
	 * removeProdutosHasTipoProdutosComplementar(
	 * ProdutosHasTipoProdutosComplementar produtosHasTipoProdutosComplementar) {
	 * getProdutosHasTipoProdutosComplementars().remove(
	 * produtosHasTipoProdutosComplementar);
	 * produtosHasTipoProdutosComplementar.setUser(null);
	 * 
	 * return produtosHasTipoProdutosComplementar; }
	 * 
	 * public List<TipoProduto> getTipoProdutos() { return this.tipoProdutos; }
	 * 
	 * public void setTipoProdutos(List<TipoProduto> tipoProdutos) {
	 * this.tipoProdutos = tipoProdutos; }
	 * 
	 * public TipoProduto addTipoProduto(TipoProduto tipoProduto) {
	 * getTipoProdutos().add(tipoProduto); tipoProduto.setUser(this);
	 * 
	 * return tipoProduto; }
	 * 
	 * public TipoProduto removeTipoProduto(TipoProduto tipoProduto) {
	 * getTipoProdutos().remove(tipoProduto); tipoProduto.setUser(null);
	 * 
	 * return tipoProduto; }
	 * 
	 * public List<UserHasRole> getUserHasRoles() { return this.userHasRoles; }
	 * 
	 * public void setUserHasRoles(List<UserHasRole> userHasRoles) {
	 * this.userHasRoles = userHasRoles; }
	 * 
	 * public UserHasRole addUserHasRole(UserHasRole userHasRole) {
	 * getUserHasRoles().add(userHasRole); userHasRole.setUser(this);
	 * 
	 * return userHasRole; }
	 * 
	 * public UserHasRole removeUserHasRole(UserHasRole userHasRole) {
	 * getUserHasRoles().remove(userHasRole); userHasRole.setUser(null);
	 * 
	 * return userHasRole; }
	 * 
	 * public List<Venda> getVendas1() { return this.vendas1; }
	 * 
	 * public void setVendas1(List<Venda> vendas1) { this.vendas1 = vendas1; }
	 * 
	 * public Venda addVendas1(Venda vendas1) { getVendas1().add(vendas1);
	 * vendas1.setUser1(this);
	 * 
	 * return vendas1; }
	 * 
	 * public Venda removeVendas1(Venda vendas1) { getVendas1().remove(vendas1);
	 * vendas1.setUser1(null);
	 * 
	 * return vendas1; }
	 * 
	 * public List<Venda> getVendas2() { return this.vendas2; }
	 * 
	 * public void setVendas2(List<Venda> vendas2) { this.vendas2 = vendas2; }
	 * 
	 * public Venda addVendas2(Venda vendas2) { getVendas2().add(vendas2);
	 * vendas2.setUser2(this);
	 * 
	 * return vendas2; }
	 * 
	 * public Venda removeVendas2(Venda vendas2) { getVendas2().remove(vendas2);
	 * vendas2.setUser2(null);
	 * 
	 * return vendas2; }
	 * 
	 * public User getUser() { return user; }
	 * 
	 * public void setUser(User user) { this.user = user; }
	 * 
	 * public List<User> getUsers() { return users; }
	 * 
	 * public void setUsers(List<User> users) { this.users = users; }
	 * 
	 * public List<EstadoPedido> getEstadoPedidos() { return estadoPedidos; }
	 * 
	 * public void setEstadoPedidos(List<EstadoPedido> estadoPedidos) {
	 * this.estadoPedidos = estadoPedidos; }
	 * 
	 * public List<Pedido> getPedidos1() { return pedidos1; }
	 * 
	 * public void setPedidos1(List<Pedido> pedidos1) { this.pedidos1 = pedidos1; }
	 * 
	 * public List<Pedido> getPedidos2() { return pedidos2; }
	 * 
	 * public void setPedidos2(List<Pedido> pedidos2) { this.pedidos2 = pedidos2; }
	 * 
	 * 
	 * public User getUser2() { return user2; }
	 * 
	 * 
	 * public void setUser2(User user2) { this.user2 = user2; }
	 * 
	 * public List<Pesquisapreco> getPesquisaprecos() { return this.pesquisaprecos;
	 * }
	 * 
	 * public void setPesquisaprecos(List<Pesquisapreco> pesquisaprecos) {
	 * this.pesquisaprecos = pesquisaprecos; }
	 * 
	 * public Pesquisapreco addPesquisapreco(Pesquisapreco pesquisapreco) {
	 * getPesquisaprecos().add(pesquisapreco); pesquisapreco.setUser(this);
	 * 
	 * return pesquisapreco; }
	 * 
	 * public Pesquisapreco removePesquisapreco(Pesquisapreco pesquisapreco) {
	 * getPesquisaprecos().remove(pesquisapreco); pesquisapreco.setUser(null);
	 * 
	 * return pesquisapreco; }
	 * 
	 * 
	 * public BigDecimal getComissao() { return comissao; }
	 * 
	 * 
	 * public void setComissao(BigDecimal comissao) { this.comissao = comissao; }
	 * 
	 */

}