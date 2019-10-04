package br.transversa.backend.model;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonManagedReference;

/**
 * Created by rajeevkumarsingh on 01/08/17.
 */
@Entity
@Table(name = "roles")
public class Role {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="id_role")
    private Long id;

    private String name;

	//bi-directional many-to-one association to UserHasRole
	@OneToMany(mappedBy="role", fetch=FetchType.LAZY)
	@JsonManagedReference
	private List<UserHasRole> userHasRoles;

	public Role() {
	}



	public Role(Long id, String name) {
		super();
		this.id = id;
		this.name = name;
	}



	public Long getId() {
		return id;
	}



	public void setId(Long id) {
		this.id = id;
	}



	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<UserHasRole> getUserHasRoles() {
		return this.userHasRoles;
	}

	public void setUserHasRoles(List<UserHasRole> userHasRoles) {
		this.userHasRoles = userHasRoles;
	}

	public UserHasRole addUserHasRole(UserHasRole userHasRole) {
		getUserHasRoles().add(userHasRole);
		userHasRole.setRole(this);

		return userHasRole;
	}

	public UserHasRole removeUserHasRole(UserHasRole userHasRole) {
		getUserHasRoles().remove(userHasRole);
		userHasRole.setRole(null);

		return userHasRole;
	}

}
