/**
 * Vendas de Kits chiques.
 * 
 * @author Jose Alves
 */
package br.com.wfit.controller;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import br.com.wfit.base.ProdutoDAO;
import br.com.wfit.modelo.EnumTipoProduto;
import br.com.wfit.modelo.Produto;
import lombok.Cleanup;

/**
*
* @author Jose Alves
*/
@ManagedBean
@RequestScoped
public class ProdutoMB implements Serializable {
	private static final long serialVersionUID = 1L;

	private Produto produto = null;
	private Collection<Produto> listaDestaque = new ArrayList();
	private Collection<Produto> listaLancamento = new ArrayList();

	public ProdutoMB() {
		/*
         * Trabalhando no conteudo...
         */
        @Cleanup
        final EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("databaseDefault");
        
        @Cleanup
        final EntityManager entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();
        
        ProdutoDAO dao = new ProdutoDAO(entityManager);
        
        for (Produto p : dao.selectAll()) {
        	if (p.getTipo().equals(EnumTipoProduto.PRODUTO_DESTAQUE.getTipo())) { 
        		this.listaDestaque.add( p );
        	}
        	if (p.getTipo().equals(EnumTipoProduto.PRODUTO_LANCAMENTO.getTipo())) { 
        		this.listaLancamento.add( p );
        	}
        }
	}

	public Produto getProduto() {
		return produto;
	}

	public void setProduto(Produto produto) {
		this.produto = produto;
	}

	public Collection<Produto> getListaDestaque() {
		return listaDestaque;
	}

	public void setListaDestaque(Collection<Produto> listaDestaque) {
		this.listaDestaque = listaDestaque;
	}

	public Collection<Produto> getListaLancamento() {
		return listaLancamento;
	}

	public void setListaLancamento(Collection<Produto> listaLancamento) {
		this.listaLancamento = listaLancamento;
	}
	
}