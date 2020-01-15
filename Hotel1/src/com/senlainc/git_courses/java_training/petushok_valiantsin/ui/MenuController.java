package com.senlainc.git_courses.java_training.petushok_valiantsin.ui;

import java.util.Scanner;

public class MenuController {
    private final MenuBuilder menuBuilder;
    private MenuNavigator menuNavigator;
    private Menu menu;

    public MenuController(Hotel hotel) {
        this.menuBuilder = new MenuBuilder(hotel);
        this.menu = menuBuilder.getRootMenu();
        this.menuNavigator = new MenuNavigator(menu);
    }

    public void showMenu() {
        menuNavigator.printMenu();
        System.out.print("Choose operation: ");
        try {
            menuNavigator.navigate(new Scanner(System.in).nextInt());
        } catch (NullPointerException e) {
            System.err.println("Wrong number/format of operation");
        }
    }

    public void run() throws InterruptedException {
        do {
            showMenu();
            Thread.sleep(3000);
            menu = menuNavigator.getCurrentMenu();
        } while (true);
    }
}
