/**
 * Vendas de Kits chiques.
 * 
 * @author Jose Alves
 */

package br.com.wfit.base;

import br.com.wfit.framework.persistence.DaoJpa2;
import br.com.wfit.modelo.Cliente;
import javax.persistence.EntityManager;

/**
 *
 * @author Jose Alves
 */
public class ClienteDAO extends DaoJpa2<Cliente>{

    public ClienteDAO(EntityManager entityManager) {
        super(Cliente.class, entityManager);
    }
    
}
