package com.senlainc.git_courses.java_training.petushok_valiantsin.api.service;

import com.senlainc.git_courses.java_training.petushok_valiantsin.impl.model.Order;

import java.time.LocalDate;

public interface IOrderService {
    void add(Order order);

    void delete(int index);

    void changeEndDate(int index, LocalDate date);

    void showOrder();

    void sort(String parameter);
}
