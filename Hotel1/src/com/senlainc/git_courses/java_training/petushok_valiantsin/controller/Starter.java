package com.senlainc.git_courses.java_training.petushok_valiantsin.controller;

import com.senlainc.git_courses.java_training.petushok_valiantsin.api.repository.IAttendanceDao;
import com.senlainc.git_courses.java_training.petushok_valiantsin.api.repository.IGuestDao;
import com.senlainc.git_courses.java_training.petushok_valiantsin.api.repository.IOrderDao;
import com.senlainc.git_courses.java_training.petushok_valiantsin.api.repository.IRoomDao;
import com.senlainc.git_courses.java_training.petushok_valiantsin.api.service.IAttendanceService;
import com.senlainc.git_courses.java_training.petushok_valiantsin.api.service.IGuestService;
import com.senlainc.git_courses.java_training.petushok_valiantsin.api.service.IOrderService;
import com.senlainc.git_courses.java_training.petushok_valiantsin.api.service.IRoomService;
import com.senlainc.git_courses.java_training.petushok_valiantsin.model.Status;
import com.senlainc.git_courses.java_training.petushok_valiantsin.repository.AttendanceDao;
import com.senlainc.git_courses.java_training.petushok_valiantsin.repository.GuestDao;
import com.senlainc.git_courses.java_training.petushok_valiantsin.repository.OrderDao;
import com.senlainc.git_courses.java_training.petushok_valiantsin.repository.RoomDao;
import com.senlainc.git_courses.java_training.petushok_valiantsin.service.AttendanceService;
import com.senlainc.git_courses.java_training.petushok_valiantsin.service.GuestService;
import com.senlainc.git_courses.java_training.petushok_valiantsin.service.OrderService;
import com.senlainc.git_courses.java_training.petushok_valiantsin.service.RoomService;
import com.senlainc.git_courses.java_training.petushok_valiantsin.ui.Hotel;
import com.senlainc.git_courses.java_training.petushok_valiantsin.ui.MenuController;

public class Starter {
    private static final Status.StatusType[] statusType = Status.StatusType.values();
    private static final IGuestDao guestDao = new GuestDao();
    private static final IRoomDao roomDao = new RoomDao();
    private static final IAttendanceDao attendanceDao = new AttendanceDao();
    private static final IOrderDao orderDao = new OrderDao();
    private static final IGuestService guestService = new GuestService(guestDao);
    private static final IRoomService roomService = new RoomService(roomDao, statusType);
    private static final IAttendanceService attendanceService = new AttendanceService(attendanceDao);
    private static final IOrderService orderService = new OrderService(orderDao, roomService, guestService, attendanceService, statusType);

    public static void main(String[] args) throws InterruptedException {
        Hotel hotel = new Hotel(guestService, roomService, attendanceService, orderService, statusType);
        MenuController menuController = new MenuController(hotel);
        menuController.run();
    }
}
