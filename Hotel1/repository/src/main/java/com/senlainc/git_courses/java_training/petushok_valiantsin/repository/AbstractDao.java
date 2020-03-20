package com.senlainc.git_courses.java_training.petushok_valiantsin.repository;

import com.senlainc.git_courses.java_training.petushok_valiantsin.api.repository.ICommonDao;
import com.senlainc.git_courses.java_training.petushok_valiantsin.utility.base_conection.CustomEntityManager;
import com.senlainc.git_courses.java_training.petushok_valiantsin.utility.exception.DaoException;

import javax.persistence.EntityManager;
import javax.persistence.Table;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaQuery;
import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.util.List;

public abstract class AbstractDao<T, K extends Serializable> implements ICommonDao<T, K> {

    private static final String ERROR = "Error during connection to Database. Check query.";
    protected final EntityManager entityManager;
    private final Class<T> clazz;

    public AbstractDao() {
        this.clazz = ((Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0]);
        this.entityManager = CustomEntityManager.getEntityManager();
    }

    protected String getTableName() {
        final String tableName = clazz.getAnnotation(Table.class).name();
        if (tableName.equals("")) {
            return clazz.getSimpleName();
        }
        return tableName;
    }

    @Override
    public void create(T object) {
        try {
            entityManager.persist(object);
        } catch (Exception e) {
            throw new DaoException(ERROR, e);
        }
    }

    @Override
    public void delete(K index) {
        try {
            entityManager.remove(index);
        } catch (Exception e) {
            throw new DaoException(ERROR, e);
        }
    }

    @Override
    public void update(T object) {
        try {
            entityManager.merge(object);
        } catch (Exception e) {
            throw new DaoException(ERROR, e);
        }
    }

    @Override
    public T read(K index) {
        try {
            return entityManager.find(this.clazz, index);
        } catch (Exception e) {
            throw new DaoException(ERROR, e);
        }
    }

    @Override
    public List<T> readAll() {
        try {
            final CriteriaQuery<T> criteriaQuery = entityManager.getCriteriaBuilder().createQuery(this.clazz);
            criteriaQuery.select(criteriaQuery.from(this.clazz));
            final TypedQuery<T> query = entityManager.createQuery(criteriaQuery);
            return query.getResultList();
        } catch (Exception e) {
            throw new DaoException(ERROR, e);
        }
    }
}
