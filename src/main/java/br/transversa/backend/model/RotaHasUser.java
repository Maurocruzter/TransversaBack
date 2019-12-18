package br.transversa.backend.model;


import java.io.Serializable;
import javax.persistence.*;
import java.math.BigInteger;


/**
 * The persistent class for the rota_has_users database table.
 * 
 */
@Entity
@Table(name="rota_has_users")
@NamedQuery(name="RotaHasUser.findAll", query="SELECT r FROM RotaHasUser r")
public class RotaHasUser implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id_rota_has_users", unique=true, nullable=false)
	private BigInteger id;

	//bi-directional many-to-one association to Rota
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="rota_id", nullable=false)
	private Rota rota;

	//bi-directional many-to-one association to User
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="users_id", nullable=false)
	private User user;

	

	public BigInteger getId() {
		return this.id;
	}

	public void setId(BigInteger id) {
		this.id = id;
	}

	public Rota getRota() {
		return this.rota;
	}

	public void setRota(Rota rota) {
		this.rota = rota;
	}

	public User getUser() {
		return this.user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public RotaHasUser() {
	}

	public RotaHasUser(BigInteger id) {
		super();
		this.id = id;
	}
	
	
	
}