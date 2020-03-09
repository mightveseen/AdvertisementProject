package com.senlainc.git_courses.java_training.petushok_valiantsin.order;

import com.senlainc.git_courses.java_training.petushok_valiantsin.Hotel;
import com.senlainc.git_courses.java_training.petushok_valiantsin.IAction;
import com.senlainc.git_courses.java_training.petushok_valiantsin.exception.WrongEnteredDataException;
import com.senlainc.git_courses.java_training.petushok_valiantsin.injection.DependencyController;

import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

public class AddAttendanceOrder implements IAction {
    private static final Logger LOGGER = Logger.getLogger(AddAttendanceOrder.class.getSimpleName());

    @Override
    public void execute() {
        try {
            final Hotel hotel = DependencyController.getInstance().getClazz(Hotel.class);
            final Scanner scanner = new Scanner(System.in);
            hotel.showOrder().forEach(System.out::println);
            System.out.print("Enter order index: ");
            final int orderIndex = Integer.parseInt(scanner.nextLine());
            hotel.showAttendance().forEach(System.out::println);
            System.out.print("Enter attendance index: ");
            final int attendanceIndex = Integer.parseInt(scanner.nextLine());
            hotel.addOrderAttendance(orderIndex, attendanceIndex);
            LOGGER.log(Level.INFO, "Add attendance to order");
        } catch (NumberFormatException e) {
            throw new WrongEnteredDataException("Wrong entered data in: " + e.getMessage(), e);
        }
    }
}
