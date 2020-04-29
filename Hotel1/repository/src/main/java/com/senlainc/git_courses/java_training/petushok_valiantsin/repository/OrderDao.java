package com.senlainc.git_courses.java_training.petushok_valiantsin.repository;

import com.senlainc.git_courses.java_training.petushok_valiantsin.api.repository.IOrderDao;
import com.senlainc.git_courses.java_training.petushok_valiantsin.model.Guest_;
import com.senlainc.git_courses.java_training.petushok_valiantsin.model.Order;
import com.senlainc.git_courses.java_training.petushok_valiantsin.model.Order_;
import com.senlainc.git_courses.java_training.petushok_valiantsin.model.Room;
import com.senlainc.git_courses.java_training.petushok_valiantsin.model.Room_;
import com.senlainc.git_courses.java_training.petushok_valiantsin.utility.exception.dao.ReadQueryException;
import org.springframework.stereotype.Repository;

import javax.persistence.PersistenceException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.time.LocalDate;
import java.util.List;

@Repository
public class OrderDao extends AbstractDao<Order, Long> implements IOrderDao {

    @Override
    public List<Order> readAll(int fistElement, int maxResult, String sortObject, String sortParameter) {
        try {
            final CriteriaQuery<Order> criteriaQuery = criteriaBuilder.createQuery(clazz);
            final Root<Order> root = criteriaQuery.from(clazz);
            return entityManager.createQuery(criteriaQuery
                    .select(root)
                    .orderBy(criteriaBuilder.asc(root.get(sortObject).get(sortParameter))))
                    .setFirstResult(fistElement)
                    .setMaxResults(maxResult)
                    .getResultList();
        } catch (PersistenceException e) {
            throw new ReadQueryException(e);
        }
    }

    @Override
    //TODO : Make sort
    public List<Room> readLastRoom(Long index, Integer limit) {
        try {
            final CriteriaQuery<Room> criteriaQuery = criteriaBuilder.createQuery(Room.class);
            final Root<Room> root = criteriaQuery.from(Room.class);
            final Join<Room, Order> join = root.join(Room_.orders);
            final Predicate predicate = criteriaBuilder.equal(join.get(Order_.guest).get(Guest_.id), index);
            return entityManager.createQuery(criteriaQuery
                    .select(root)
                    .distinct(true)
                    .where(predicate))
                    .setMaxResults(limit)
                    .getResultList();
        } catch (PersistenceException e) {
            throw new ReadQueryException(e);
        }
    }

    @Override
    public List<Room> readAfterDate(LocalDate date) {
        try {
            final CriteriaQuery<Room> criteriaQuery = criteriaBuilder.createQuery(Room.class);
            final Root<Room> root = criteriaQuery.from(Room.class);
            final Join<Room, Order> join = root.join(Room_.orders);
            final Predicate predicate = criteriaBuilder.lessThan(join.get(Order_.endDate), date);
            return entityManager.createQuery(criteriaQuery
                    .select(root)
                    .distinct(true)
                    .where(predicate))
                    .getResultList();
        } catch (PersistenceException e) {
            throw new ReadQueryException(e);
        }
    }
}
