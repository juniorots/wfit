/**
 * Vendas de Kits chiques.
 * 
 * @author Jose Alves
 */
package br.com.wfit.modelo;

import javax.persistence.Entity;
import javax.validation.constraints.NotNull;

import br.com.wfit.framework.persistence.DomainObject;

@Entity 
public class Produto extends DomainObject {

	private static final long serialVersionUID = 1L;
	
	@NotNull
	private String srcImagem;
	
	private String srcImagemModal;
	
	@NotNull
	private String titulo;
	
	private String descritivo;
	
	@NotNull
	private Integer tipo;
	
	private Double preco;
	
	public String getSrcImagemModal() {
		return srcImagemModal;
	}

	public void setSrcImagemModal(String srcImagemModal) {
		this.srcImagemModal = srcImagemModal;
	}

	public Double getPreco() {
		return preco;
	}

	public void setPreco(Double preco) {
		this.preco = preco;
	}

	public String getSrcImagem() {
		return srcImagem;
	}

	public void setSrcImagem(String srcImagem) {
		this.srcImagem = srcImagem;
	}

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	public String getDescritivo() {
		return descritivo;
	}

	public void setDescritivo(String descritivo) {
		this.descritivo = descritivo;
	}

	public Integer getTipo() {
		return tipo;
	}

	public void setTipo(Integer tipo) {
		this.tipo = tipo;
	}	
	
}
