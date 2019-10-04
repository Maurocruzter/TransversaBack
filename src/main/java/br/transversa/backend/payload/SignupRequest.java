package br.transversa.backend.payload;

import java.util.List;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;


public class SignupRequest {
	
	@NotNull(
    		message = "Insira um ID de role"
    		)
	private List<Long> role;
	
    @NotBlank(
    		message = "Insira um email"
    		)
    private String email;

    @NotBlank(
    		message = "Insira uma senha"
    		)
    @Length(min = 5, max = 50, message = "senha deve ter pelo menos 5 caracteres e menos de 50")
    private String senha;
    
    @NotBlank(
    		message = "Insira um numero de celular"
    		)
    private String celular;

    @NotBlank(
    		message = "Insira um nome"
    		)
	private String nome;

    @NotBlank(
    		message = "Insira um sobrenome"
    		)
	private String sobrenome;

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

	public String getCelular() {
		return celular;
	}

	public void setCelular(String celular) {
		this.celular = celular;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getSobrenome() {
		return sobrenome;
	}

	public void setSobrenome(String sobrenome) {
		this.sobrenome = sobrenome;
	}

	public List<Long> getRole() {
		return role;
	}

	public void setRole(List<Long> role) {
		this.role = role;
	}
	
	

//	public Long getRole() {
//		return role;
//	}
//
//	public void setRole(Long role) {
//		this.role = role;
//	}

	
}
