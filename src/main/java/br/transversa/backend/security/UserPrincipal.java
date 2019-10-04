package br.transversa.backend.security;

import com.fasterxml.jackson.annotation.JsonIgnore;

import br.transversa.backend.model.User;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class UserPrincipal implements UserDetails {
    private Long id;

    private String name;

    private String username;
    
    private String roles;
    

//    @JsonIgnore
//    private String email;

    @JsonIgnore
    private String password;

    private Collection<? extends GrantedAuthority> authorities;

    public UserPrincipal(Long id, String name, String email, String password, Collection<? extends GrantedAuthority> authorities) {
        this.id = id;
        this.name = name;
        this.username = email;
        //this.email = email;
        this.password = password;
        this.authorities = authorities;
    }

    
    
//    public UserPrincipal(Long id, String roles) {
//		this.id = id;
//		this.roles = roles;
//	}
    
    



	public UserPrincipal(Long id, Collection<? extends GrantedAuthority> authorities) {
		super();
		this.id = id;
		this.authorities = authorities;
	}



	public static UserPrincipal create(User user) {
        List<GrantedAuthority> authorities = user.getUserHasRoles().stream().map(role ->
                new SimpleGrantedAuthority(role.getRole().getName())
        ).collect(Collectors.toList());

        //System.out.println(user.getUserHasRoles());
        return new UserPrincipal(
                user.getId(),
                user.getNome(),
                user.getEmail(),
                //user.getEmail(),
                user.getSenha(),
                authorities
        );
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

//    public String getEmail() {
//        return email;
//    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }
    
    public String getAuthoritiesToString() {
        return authorities.toString();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserPrincipal that = (UserPrincipal) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id);
    }



	public String getRoles() {
		return roles;
	}
    
    
}
