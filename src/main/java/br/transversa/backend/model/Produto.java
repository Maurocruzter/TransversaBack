package br.transversa.backend.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Date;
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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;




/**
 * The persistent class for the produtos database table.
 * 
 */
@Entity
@Table(name="produtos")
@NamedQuery(name="Produto.findAll", query="SELECT p FROM Produto p")
@JsonInclude(value = Include.NON_NULL)
public class Produto implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id_produto", unique=true, nullable=false)
	private Long id;

	@Lob
	private byte[] data;

	@Column(name="data_adicionado", nullable=false)
	private Timestamp dataAdicionado;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="data_alterado")
	private Date dataAlterado;

	@Column(length=45)
	private String descricao;

	private Float comprimento;
	
	private Float largura;
	
	private Float altura;

	@Column(name="file_type", nullable=false, length=15)
	private String fileType;

	@Column(nullable=false, length=45)
	private String nome;

	private Float peso;

	@Column(nullable=false, precision=10, scale=2)
	private BigDecimal preco;

	@Column(nullable=false, length=45)
	private String uuid;
	
	@Transient
	private int quantidade;
	
	@Transient
	private Long idCarrinho;

	//bi-directional many-to-one association to CarrinhosHasProduto
	@OneToMany(mappedBy="produto")
	private List<CarrinhosHasProduto> carrinhosHasProdutos;

	//bi-directional many-to-one association to EntradaProdutoStock
	@OneToMany(mappedBy="produto")
	private List<EntradaProdutoStock> entradaProdutoStocks;

	//bi-directional many-to-one association to PedidosHasProduto
	@OneToMany(mappedBy="produto")
	private List<PedidosHasProduto> pedidosHasProdutos;

	//bi-directional many-to-one association to ProdutoHasTipoProdutosSubstituto
	@OneToMany(mappedBy="produto")
	private List<ProdutoHasTipoProdutosSubstituto> produtoHasTipoProdutosSubstitutos;

	//bi-directional many-to-one association to User
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="users_id", nullable=false)
	private User user;

	//bi-directional many-to-one association to ProdutosHasTipoProduto
	@OneToMany(mappedBy="produto")
	private List<ProdutosHasTipoProduto> produtosHasTipoProdutos;

	//bi-directional many-to-one association to ProdutosHasTipoProdutosComplementar
	@OneToMany(mappedBy="produto")
	private List<ProdutosHasTipoProdutosComplementar> produtosHasTipoProdutosComplementars;

	//bi-directional many-to-one association to SaidaProdutoStock
	@OneToMany(mappedBy="produto")
	private List<SaidaProdutoStock> saidaProdutoStocks;


	//bi-directional many-to-one association to Stock
	@OneToMany(mappedBy="produto")
	private List<Stock> stocks;

	//bi-directional many-to-one association to VendasHasProduto
	@OneToMany(mappedBy="produto")
	private List<VendasHasProduto> vendasHasProdutos;

	
	

	public int getQuantidade() {
		return quantidade;
	}

	public void setQuantidade(int quantidade) {
		this.quantidade = quantidade;
	}

	public Long getIdCarrinho() {
		return idCarrinho;
	}

	public void setIdCarrinho(Long idCarrinho) {
		this.idCarrinho = idCarrinho;
	}

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public byte[] getData() {
		return this.data;
	}

	public void setData(byte[] data) {
		this.data = data;
	}

	public Timestamp getDataAdicionado() {
		return this.dataAdicionado;
	}

	public void setDataAdicionado(Timestamp dataAdicionado) {
		this.dataAdicionado = dataAdicionado;
	}

	public Date getDataAlterado() {
		return this.dataAlterado;
	}

	public void setDataAlterado(Date dataAlterado) {
		this.dataAlterado = dataAlterado;
	}

	public String getDescricao() {
		return this.descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}


	public String getFileType() {
		return this.fileType;
	}

	public void setFileType(String fileType) {
		this.fileType = fileType;
	}

	public String getNome() {
		return this.nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public Float getComprimento() {
		return comprimento;
	}

	public void setComprimento(Float comprimento) {
		this.comprimento = comprimento;
	}

	public Float getLargura() {
		return largura;
	}

	public void setLargura(Float largura) {
		this.largura = largura;
	}

	public Float getAltura() {
		return altura;
	}

	public void setAltura(Float altura) {
		this.altura = altura;
	}

	public Float getPeso() {
		return peso;
	}

	public void setPeso(Float peso) {
		this.peso = peso;
	}

	public BigDecimal getPreco() {
		return this.preco;
	}

	public void setPreco(BigDecimal preco) {
		this.preco = preco;
	}

	public String getUuid() {
		return this.uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public List<CarrinhosHasProduto> getCarrinhosHasProdutos() {
		return this.carrinhosHasProdutos;
	}

	public void setCarrinhosHasProdutos(List<CarrinhosHasProduto> carrinhosHasProdutos) {
		this.carrinhosHasProdutos = carrinhosHasProdutos;
	}

	public CarrinhosHasProduto addCarrinhosHasProduto(CarrinhosHasProduto carrinhosHasProduto) {
		getCarrinhosHasProdutos().add(carrinhosHasProduto);
		carrinhosHasProduto.setProduto(this);

		return carrinhosHasProduto;
	}

	public CarrinhosHasProduto removeCarrinhosHasProduto(CarrinhosHasProduto carrinhosHasProduto) {
		getCarrinhosHasProdutos().remove(carrinhosHasProduto);
		carrinhosHasProduto.setProduto(null);

		return carrinhosHasProduto;
	}

	public List<EntradaProdutoStock> getEntradaProdutoStocks() {
		return this.entradaProdutoStocks;
	}

	public void setEntradaProdutoStocks(List<EntradaProdutoStock> entradaProdutoStocks) {
		this.entradaProdutoStocks = entradaProdutoStocks;
	}

	public EntradaProdutoStock addEntradaProdutoStock(EntradaProdutoStock entradaProdutoStock) {
		getEntradaProdutoStocks().add(entradaProdutoStock);
		entradaProdutoStock.setProduto(this);

		return entradaProdutoStock;
	}

	public EntradaProdutoStock removeEntradaProdutoStock(EntradaProdutoStock entradaProdutoStock) {
		getEntradaProdutoStocks().remove(entradaProdutoStock);
		entradaProdutoStock.setProduto(null);

		return entradaProdutoStock;
	}


	public List<PedidosHasProduto> getPedidosHasProdutos() {
		return this.pedidosHasProdutos;
	}

	public void setPedidosHasProdutos(List<PedidosHasProduto> pedidosHasProdutos) {
		this.pedidosHasProdutos = pedidosHasProdutos;
	}

	public PedidosHasProduto addPedidosHasProduto(PedidosHasProduto pedidosHasProduto) {
		getPedidosHasProdutos().add(pedidosHasProduto);
		pedidosHasProduto.setProduto(this);

		return pedidosHasProduto;
	}

	public PedidosHasProduto removePedidosHasProduto(PedidosHasProduto pedidosHasProduto) {
		getPedidosHasProdutos().remove(pedidosHasProduto);
		pedidosHasProduto.setProduto(null);

		return pedidosHasProduto;
	}

	public List<ProdutoHasTipoProdutosSubstituto> getProdutoHasTipoProdutosSubstitutos() {
		return this.produtoHasTipoProdutosSubstitutos;
	}

	public void setProdutoHasTipoProdutosSubstitutos(List<ProdutoHasTipoProdutosSubstituto> produtoHasTipoProdutosSubstitutos) {
		this.produtoHasTipoProdutosSubstitutos = produtoHasTipoProdutosSubstitutos;
	}

	public ProdutoHasTipoProdutosSubstituto addProdutoHasTipoProdutosSubstituto(ProdutoHasTipoProdutosSubstituto produtoHasTipoProdutosSubstituto) {
		getProdutoHasTipoProdutosSubstitutos().add(produtoHasTipoProdutosSubstituto);
		produtoHasTipoProdutosSubstituto.setProduto(this);

		return produtoHasTipoProdutosSubstituto;
	}

	public ProdutoHasTipoProdutosSubstituto removeProdutoHasTipoProdutosSubstituto(ProdutoHasTipoProdutosSubstituto produtoHasTipoProdutosSubstituto) {
		getProdutoHasTipoProdutosSubstitutos().remove(produtoHasTipoProdutosSubstituto);
		produtoHasTipoProdutosSubstituto.setProduto(null);

		return produtoHasTipoProdutosSubstituto;
	}

	public User getUser() {
		return this.user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public List<ProdutosHasTipoProduto> getProdutosHasTipoProdutos() {
		return this.produtosHasTipoProdutos;
	}

	public void setProdutosHasTipoProdutos(List<ProdutosHasTipoProduto> produtosHasTipoProdutos) {
		this.produtosHasTipoProdutos = produtosHasTipoProdutos;
	}

	public ProdutosHasTipoProduto addProdutosHasTipoProduto(ProdutosHasTipoProduto produtosHasTipoProduto) {
		getProdutosHasTipoProdutos().add(produtosHasTipoProduto);
		produtosHasTipoProduto.setProduto(this);

		return produtosHasTipoProduto;
	}

	public ProdutosHasTipoProduto removeProdutosHasTipoProduto(ProdutosHasTipoProduto produtosHasTipoProduto) {
		getProdutosHasTipoProdutos().remove(produtosHasTipoProduto);
		produtosHasTipoProduto.setProduto(null);

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
		produtosHasTipoProdutosComplementar.setProduto(this);

		return produtosHasTipoProdutosComplementar;
	}

	public ProdutosHasTipoProdutosComplementar removeProdutosHasTipoProdutosComplementar(ProdutosHasTipoProdutosComplementar produtosHasTipoProdutosComplementar) {
		getProdutosHasTipoProdutosComplementars().remove(produtosHasTipoProdutosComplementar);
		produtosHasTipoProdutosComplementar.setProduto(null);

		return produtosHasTipoProdutosComplementar;
	}

	public List<SaidaProdutoStock> getSaidaProdutoStocks() {
		return this.saidaProdutoStocks;
	}

	public void setSaidaProdutoStocks(List<SaidaProdutoStock> saidaProdutoStocks) {
		this.saidaProdutoStocks = saidaProdutoStocks;
	}

	public SaidaProdutoStock addSaidaProdutoStock(SaidaProdutoStock saidaProdutoStock) {
		getSaidaProdutoStocks().add(saidaProdutoStock);
		saidaProdutoStock.setProduto(this);

		return saidaProdutoStock;
	}

	public SaidaProdutoStock removeSaidaProdutoStock(SaidaProdutoStock saidaProdutoStock) {
		getSaidaProdutoStocks().remove(saidaProdutoStock);
		saidaProdutoStock.setProduto(null);

		return saidaProdutoStock;
	}


	public List<Stock> getStocks() {
		return this.stocks;
	}

	public void setStocks(List<Stock> stocks) {
		this.stocks = stocks;
	}

	public Stock addStock(Stock stock) {
		getStocks().add(stock);
		stock.setProduto(this);

		return stock;
	}

	public Stock removeStock(Stock stock) {
		getStocks().remove(stock);
		stock.setProduto(null);

		return stock;
	}

	public List<VendasHasProduto> getVendasHasProdutos() {
		return this.vendasHasProdutos;
	}

	public void setVendasHasProdutos(List<VendasHasProduto> vendasHasProdutos) {
		this.vendasHasProdutos = vendasHasProdutos;
	}

	public VendasHasProduto addVendasHasProduto(VendasHasProduto vendasHasProduto) {
		getVendasHasProdutos().add(vendasHasProduto);
		vendasHasProduto.setProduto(this);

		return vendasHasProduto;
	}

	public VendasHasProduto removeVendasHasProduto(VendasHasProduto vendasHasProduto) {
		getVendasHasProdutos().remove(vendasHasProduto);
		vendasHasProduto.setProduto(null);

		return vendasHasProduto;
	}
	
	
	

	public Produto() {
	}
	
	

	public Produto(Long id, String nome, BigDecimal preco, String uuid) {
		super();
		this.id = id;
		this.nome = nome;
		this.preco = preco;
		this.uuid = uuid;
	}
	
	
	public Produto(byte[] data) {
		super();
		this.data = data;
	}

	

	

	public Produto(BigDecimal preco) {
		super();
		this.preco = preco;
	}



	public Produto(Long id) {
		super();
		this.id = id;
	}



	public Produto(Long id, String descricao, String nome, BigDecimal preco, String uuid) {
		super();
		this.id = id;
		this.descricao = descricao;
		this.nome = nome;
		this.preco = preco;
		this.uuid = uuid;
	}



	public Produto(String nome, byte[] data, String fileType) {
		super();
		this.nome = nome;
		this.data = data;
		this.fileType = fileType;
	}
	
	



	public Produto(Long id, String nome, BigDecimal preco,
			String uuid,
			 int quantidade) {
		super();
		this.id = id;
		this.nome = nome;
		this.preco = preco;
		this.uuid = uuid;
		this.quantidade = quantidade;
	}
	
	public Produto(Long id, String nome, BigDecimal preco,
			String uuid,
			 int quantidade,
			 Long idCarrinho) {
		super();
		this.id = id;
		this.nome = nome;
		this.preco = preco;
		this.uuid = uuid;
		this.quantidade = quantidade;
		this.idCarrinho = idCarrinho;
	}
	

}