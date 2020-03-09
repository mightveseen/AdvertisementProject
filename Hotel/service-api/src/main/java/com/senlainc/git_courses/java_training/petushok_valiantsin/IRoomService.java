package com.senlainc.git_courses.java_training.petushok_valiantsin;

import com.senlainc.git_courses.java_training.petushok_valiantsin.status.RoomStatus;

import java.util.List;

public interface IRoomService {
    void add(int number, String classification, short roomNumber, short capacity, double price);

    void delete(int index);

    List<Room> getRoomList();

    void changePrice(int index, double price);

    void changeStatus(int index, RoomStatus status);

    List<Room> getRoomList(String parameter);

    long numFreeRoom();

    List<Room> sort(String type, String parameter);
}
