package com.senlainc.git_courses.java_training.petushok_valiantsin.order;

import com.senlainc.git_courses.java_training.petushok_valiantsin.Hotel;
import com.senlainc.git_courses.java_training.petushok_valiantsin.IAction;
import com.senlainc.git_courses.java_training.petushok_valiantsin.exception.WrongEnteredDataException;
import com.senlainc.git_courses.java_training.petushok_valiantsin.injection.DependencyController;

import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ShowAttendanceOrder implements IAction {
    private static final Logger LOGGER = Logger.getLogger(ShowAttendanceOrder.class.getSimpleName());

    @Override
    public void execute() {
        try {
            final Hotel hotel = DependencyController.getInstance().getClazz(Hotel.class);
            final Scanner scanner = new Scanner(System.in);
            hotel.showOrder().forEach(System.out::println);
            System.out.print("Enter order index: ");
            final int index = Integer.parseInt(scanner.nextLine());
            hotel.showOrderAttendance(index).forEach(System.out::println);
            LOGGER.log(Level.INFO, "Show guest attendance");
        } catch (NumberFormatException e) {
            throw new WrongEnteredDataException("Wrong entered data in: " + e.getMessage(), e);
        }
    }
}
