package com.senlainc.git_courses.java_training.petushok_valiantsin.ui.guest;

import com.senlainc.git_courses.java_training.petushok_valiantsin.controller.Hotel;
import com.senlainc.git_courses.java_training.petushok_valiantsin.injection.DependencyController;
import com.senlainc.git_courses.java_training.petushok_valiantsin.ui.IAction;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

public class AddGuest implements IAction {
    private static final Logger LOGGER = Logger.getLogger(AddGuest.class.getSimpleName());

    @Override
    public void execute() throws DateTimeParseException {
        try {
            final Hotel hotel = DependencyController.getInstance().getClazz(Hotel.class);
            final Scanner scanner = new Scanner(System.in);
            System.out.print("Enter first name: ");
            final String firstName = scanner.nextLine();
            System.out.print("Enter last name: ");
            final String lastName = scanner.nextLine();
            System.out.print("Enter info contact(Email/Phone number): ");
            final String infoContact = scanner.nextLine();
            System.out.print("Enter birthday(Format: YYYY-MM-DD): ");
            final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            final LocalDate birthday = LocalDate.parse(scanner.nextLine(), formatter);
            hotel.addGuest(firstName, lastName, birthday, infoContact);
            LOGGER.log(Level.INFO, "Add guest in list");
        } catch (DateTimeParseException | NumberFormatException e) {
            throw new RuntimeException("Wrong data: " + e.getMessage(), e);
        }
    }
}
