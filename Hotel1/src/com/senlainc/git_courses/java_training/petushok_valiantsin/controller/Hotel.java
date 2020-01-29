package com.senlainc.git_courses.java_training.petushok_valiantsin.controller;

import com.senlainc.git_courses.java_training.petushok_valiantsin.api.service.IAttendanceService;
import com.senlainc.git_courses.java_training.petushok_valiantsin.api.service.IGuestService;
import com.senlainc.git_courses.java_training.petushok_valiantsin.api.service.IOrderService;
import com.senlainc.git_courses.java_training.petushok_valiantsin.api.service.IRoomService;
import com.senlainc.git_courses.java_training.petushok_valiantsin.model.Order;
import com.senlainc.git_courses.java_training.petushok_valiantsin.model.Room;
import com.senlainc.git_courses.java_training.petushok_valiantsin.model.status.RoomStatus;
import com.senlainc.git_courses.java_training.petushok_valiantsin.utility.logger.CustomLogger;

import java.time.LocalDate;
import java.util.List;
import java.util.logging.Level;

public class Hotel {
    private static Hotel instance;
    private final IGuestService guestService;
    private final IRoomService roomService;
    private final IAttendanceService attendanceService;
    private final IOrderService orderService;

    private Hotel(IGuestService guestService, IRoomService roomService, IAttendanceService attendanceService, IOrderService orderService) {
        this.guestService = guestService;
        this.roomService = roomService;
        this.attendanceService = attendanceService;
        this.orderService = orderService;
    }

    public static void setInstance(IGuestService guestService, IRoomService roomService, IAttendanceService attendanceService, IOrderService orderService) {
        instance = new Hotel(guestService, roomService, attendanceService, orderService);
    }

    public static Hotel getInstance() {
        return instance;
    }

    /** Attendance */
    public void createAttendance() {
        attendanceService.add("Lunch", "Food", 12.3);
        attendanceService.add("Dinner", "Food", 9);
        attendanceService.add("Breakfast", "Food", 9);
        CustomLogger.getLogger().log(Level.INFO, "Create attendance (For example)");
    }

    public void addAttendance(String name, String section, double price) {
        attendanceService.add(name, section, price);
        CustomLogger.getLogger().log(Level.INFO, "Add attendance in list");
    }

    public void showAttendance() {
        attendanceService.showAttendance();
    }

    public void changePriceAttendance(int index, double price) {
        attendanceService.changePrice(index, price);
        CustomLogger.getLogger().log(Level.INFO, "Change attendance price");
    }

    /** Room */
    public void createRoom() {
        roomService.add(new Room(203, "President", (short) 4, (short) 6, 330.0));
        roomService.add(new Room(312, "Business", (short) 1, (short) 2, 170.0));
        roomService.add(new Room(401, "Lux", (short) 2, (short) 4, 230.0));
        roomService.add(new Room(105, "Lux", (short) 2, (short) 4, 30.0));
        roomService.add(new Room(506, "President", (short) 5, (short) 10, 230.0));
        CustomLogger.getLogger().log(Level.INFO, "Create room (For example)");
    }

    public void addRoom(Room room) {
        roomService.add(room);
        CustomLogger.getLogger().log(Level.INFO, "Add room in list");
    }

    public void changePriceRoom(int index, double price) {
        roomService.changePrice(index, price);
        CustomLogger.getLogger().log(Level.INFO, "Change room price");
    }

    public void changeStatusRoom(int index, RoomStatus status) {
        roomService.changeStatus(index, status);
        CustomLogger.getLogger().log(Level.INFO, "Change room status");
    }

    public void sortRoom(String type, String parameter) {
        List<Room> myList = roomService.sort(parameter);
        roomService.show(type, myList);
        CustomLogger.getLogger().log(Level.INFO, "Show room list sorted by: " + parameter);
    }

    public void showRoom(String parameter) {
        roomService.show(parameter, roomService.getRoomList());
    }

    public void numFreeRoom() {
        roomService.numFreeRoom();
        CustomLogger.getLogger().log(Level.INFO, "Show umber of free room");
    }

    /** Guest */
    public void createGuest() {
        guestService.add("Victoria", "July", LocalDate.of(1986, 5, 12), "+1532521678");
        guestService.add("Robert", "Johnson", LocalDate.of(1967, 12, 1), "+278392386");
        guestService.add("Daniel", "Blake", LocalDate.of(1971, 1, 24), "+1532521678");
        CustomLogger.getLogger().log(Level.INFO, "Create guest (For Example)");
    }

    public void addGuest(String firstName, String lastName, LocalDate birthday, String infoContact) {
        guestService.add(firstName, lastName, birthday, infoContact);
        CustomLogger.getLogger().log(Level.INFO, "Add guest in list");
    }

    public void numGuest() {
        guestService.num();
        CustomLogger.getLogger().log(Level.INFO, "Show number of guest");
    }

    public void showGuest() {
        guestService.show();
    }

    /** Order */
    public void createOrder() {
        orderService.add(2, 4, LocalDate.of(2019, 3, 12));
        orderService.add(1, 1, LocalDate.of(2019, 1, 3));
        orderService.add(3, 2, LocalDate.of(2019, 2, 23));
        CustomLogger.getLogger().log(Level.INFO, "Create order (For example)");
    }

    public void addOrder(int guestIndex, int roomIndex, LocalDate endDate) {
        orderService.add(guestIndex, roomIndex, endDate);
        CustomLogger.getLogger().log(Level.INFO, "Add order in list");
    }

    public void deleteOrder(int orderIndex) {
        orderService.delete(orderIndex);
        CustomLogger.getLogger().log(Level.INFO, "Delete order from list");
    }

    public void sortOrder(String parameter) {
        List<Order> myList = orderService.sort(parameter);
        orderService.show(myList);
        CustomLogger.getLogger().log(Level.INFO, "Show order list sorted by: " + parameter);
    }

    public void showOrder() {
        orderService.show();
    }

    public void showAfterDate(LocalDate freeDate) {
        orderService.showAfterDate(freeDate);
        CustomLogger.getLogger().log(Level.INFO, "Show room will free after chosen date");
    }

    public void showGuestRoom(int index) {
        orderService.showGuestRoom(index);
        CustomLogger.getLogger().log(Level.INFO, "Show last 3 room of guest");
    }

    public void showOrderAttendance(int index) {
        orderService.showAttendance(index);
        CustomLogger.getLogger().log(Level.INFO, "Show guest attendance");
    }

    public void addOrderAttendance(int orderIndex, int attendanceIndex) {
        orderService.addAttendance(orderIndex, attendanceIndex);
        CustomLogger.getLogger().log(Level.INFO, "Add attendance to order");
    }
}
