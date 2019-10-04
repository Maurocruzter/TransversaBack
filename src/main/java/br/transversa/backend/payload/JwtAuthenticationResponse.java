package br.transversa.backend.payload;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

public class JwtAuthenticationResponse {
    private String idToken;
    private String tokenType = "Bearer";
    private List<String> roles;
    private int expiresIn;
    private String email;

    public JwtAuthenticationResponse(String idToken, 
    		Collection<? extends GrantedAuthority> collection, 
    		int expiresIn,
    		String email) {
        this.idToken = idToken;
        this.expiresIn = expiresIn/1000;
        roles = new ArrayList<>();
        
        collection.forEach(grantedAuthority -> {
        	roles.add(grantedAuthority.getAuthority());  
		});
        this.email = email;
    }

    public String getIdToken() {
		return idToken;
	}

	public void setIdToken(String idToken) {
		this.idToken = idToken;
	}




	public String getTokenType() {
        return tokenType;
    }

    public void setTokenType(String tokenType) {
        this.tokenType = tokenType;
    }

	public List<String> getRoles() {
		return roles;
	}
	
	

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public void setRoles(List<String> roles) {
		this.roles = roles;
	}

	public int getExpiresIn() {
		return expiresIn;
	}

	public void setExpiresIn(int expiresIn) {
		this.expiresIn = expiresIn;
	}


	
}
