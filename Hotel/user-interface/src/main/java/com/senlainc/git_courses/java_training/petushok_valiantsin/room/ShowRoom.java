package com.senlainc.git_courses.java_training.petushok_valiantsin.room;

import com.senlainc.git_courses.java_training.petushok_valiantsin.Hotel;
import com.senlainc.git_courses.java_training.petushok_valiantsin.IAction;
import com.senlainc.git_courses.java_training.petushok_valiantsin.injection.DependencyController;

import java.util.logging.Level;
import java.util.logging.Logger;

public class ShowRoom implements IAction {
    private static final Logger LOGGER = Logger.getLogger(ShowRoom.class.getSimpleName());
    private final String type;
    private final String parameter;

    public ShowRoom(String type, String parameter) {
        this.parameter = parameter;
        this.type = type;
    }

    @Override
    public void execute() {
        final Hotel hotel = DependencyController.getInstance().getClazz(Hotel.class);
        hotel.sortRoom(type, parameter).forEach(System.out::println);
        LOGGER.log(Level.INFO, "Show room list sorted by: " + parameter);
    }
}
