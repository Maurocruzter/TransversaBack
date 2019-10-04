package br.transversa.backend.payload;

import javax.validation.constraints.NotBlank;

public class LoginRequest {
    @NotBlank
    private String email;

    @NotBlank
    private String senha;



	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
    
    
}
