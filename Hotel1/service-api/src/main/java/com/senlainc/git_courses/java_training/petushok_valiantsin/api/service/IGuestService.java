package com.senlainc.git_courses.java_training.petushok_valiantsin.api.service;

import com.senlainc.git_courses.java_training.petushok_valiantsin.model.Guest;

import java.time.LocalDate;
import java.util.List;

public interface IGuestService {

    void create(String firstName, String lastName, LocalDate birthday);

    void delete(long index);

    Long num();

    List<Guest> getGuestList();
}
