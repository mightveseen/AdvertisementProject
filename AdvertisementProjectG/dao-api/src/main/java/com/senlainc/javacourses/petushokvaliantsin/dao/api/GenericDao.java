package com.senlainc.javacourses.petushokvaliantsin.dao.api;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.NoRepositoryBean;

import java.io.Serializable;

@NoRepositoryBean
public interface GenericDao<E, K extends Serializable> extends CrudRepository<E, K> {

}
