/**
 * Vendas de Kits chiques.
 * 
 * @author Jose Alves
 */
package br.com.wfit.base;

import javax.persistence.EntityManager;

import br.com.wfit.framework.persistence.DaoJpa2;
import br.com.wfit.modelo.Produto;

public class ProdutoDAO extends DaoJpa2<Produto>{

	public ProdutoDAO(EntityManager entityManager) {
        super(Produto.class, entityManager);
    }
}
