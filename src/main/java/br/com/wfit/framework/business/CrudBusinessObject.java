package br.com.wfit.framework.business;

import java.util.List;
import java.util.UUID;

import br.com.wfit.framework.persistence.DataAccessObject;
import br.com.wfit.framework.persistence.DomainObject;

/**
 *
 * @author Rafael Quintino
 * @param <DO>
 * @param <DAO>
 */
public class CrudBusinessObject<DO extends DomainObject, DAO extends DataAccessObject<DO>> extends DatabaseBusinessObject<DO, DAO> implements ICrudBusinessObject<DO> {

    public CrudBusinessObject(DAO dao) {
        super(dao);
    }

    public DO create(DO domainObject) {
        return dao.insert(domainObject);
    }

    public DO retrieve(UUID id) {
        return dao.selectById(id);
    }

    public List<DO> retrieveAll() {
        return dao.selectAll();
    }

    public List<DO> retrieveAll(int first, int max) {
        return dao.selectAll(first, max);
    }

    public List<DO> retrieveByExample(DO exampleDO) {
        return dao.selectUsingFilter(exampleDO);
    }

    public List<DO> retrieveByExample(DO exampleDO, int first, int max) {
        return dao.selectUsingFilter(exampleDO, first, max);
    }

    public DO update(DO domainObject) {
        return dao.update(domainObject);
    }

    public void delete(DO domainObject) {
        dao.delete(domainObject);
    }

}
