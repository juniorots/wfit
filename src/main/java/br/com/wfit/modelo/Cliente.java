/**
 * Vendas de Kits chiques.
 * 
 * @author Jose Alves
 */
package br.com.wfit.modelo;

import static javax.persistence.TemporalType.DATE;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Temporal;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;

import br.com.wfit.framework.persistence.DomainObject;

@Entity
public class Cliente extends DomainObject {

	private static final long serialVersionUID = 1L;
	
	@NotNull
	private String nome;
	
	@NotNull
	private String email;
	
	private String senha;
	    
	@Transient
	private String confirmaSenha;
	
	@Temporal(DATE)
    private Date dtNascimento;
	
	private String mensagem;

	public String getMensagem() {
		return mensagem;
	}

	public void setMensagem(String mensagem) {
		this.mensagem = mensagem;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

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

	public String getConfirmaSenha() {
		return confirmaSenha;
	}

	public void setConfirmaSenha(String confirmaSenha) {
		this.confirmaSenha = confirmaSenha;
	}

	public Date getDtNascimento() {
		return dtNascimento;
	}

	public void setDtNascimento(Date dtNascimento) {
		this.dtNascimento = dtNascimento;
	}

}
