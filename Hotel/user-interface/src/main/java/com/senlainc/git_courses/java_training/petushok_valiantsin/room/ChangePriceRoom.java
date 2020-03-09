package com.senlainc.git_courses.java_training.petushok_valiantsin.room;

import com.senlainc.git_courses.java_training.petushok_valiantsin.Hotel;
import com.senlainc.git_courses.java_training.petushok_valiantsin.IAction;
import com.senlainc.git_courses.java_training.petushok_valiantsin.exception.WrongEnteredDataException;
import com.senlainc.git_courses.java_training.petushok_valiantsin.injection.DependencyController;

import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ChangePriceRoom implements IAction {
    private static final Logger LOGGER = Logger.getLogger(ChangePriceRoom.class.getSimpleName());

    @Override
    public void execute() {
        try {
            final Hotel hotel = DependencyController.getInstance().getClazz(Hotel.class);
            final Scanner scanner = new Scanner(System.in);
            hotel.showRoom("all").forEach(System.out::println);
            System.out.print("Enter room index: ");
            final int index = Integer.parseInt(scanner.nextLine());
            System.out.print("Enter price(split cost - '.'): ");
            final double price = Double.parseDouble(scanner.nextLine());
            hotel.changePriceRoom(index, price);
            LOGGER.log(Level.INFO, "Change room price");
        } catch (NumberFormatException e) {
            throw new WrongEnteredDataException("Wrong entered data in: " + e.getMessage(), e);
        }
    }
}
