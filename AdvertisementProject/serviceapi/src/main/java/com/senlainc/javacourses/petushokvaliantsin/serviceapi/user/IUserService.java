package com.senlainc.javacourses.petushokvaliantsin.serviceapi.user;

import com.senlainc.javacourses.petushokvaliantsin.model.user.User;

public interface IUserService {

    boolean update(User object);

    boolean delete(Long index);

    boolean create(User object);

    User read(Long index);
}
