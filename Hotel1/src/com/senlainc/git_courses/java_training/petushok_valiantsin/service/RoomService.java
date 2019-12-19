package com.senlainc.git_courses.java_training.petushok_valiantsin.service;

import com.senlainc.git_courses.java_training.petushok_valiantsin.api.repository.IRoomDao;
import com.senlainc.git_courses.java_training.petushok_valiantsin.api.service.IRoomService;
import com.senlainc.git_courses.java_training.petushok_valiantsin.model.Room;
import com.senlainc.git_courses.java_training.petushok_valiantsin.model.Status;
import com.senlainc.git_courses.java_training.petushok_valiantsin.utility.MyList;

import java.util.Comparator;

public class RoomService implements IRoomService {
    private final IRoomDao roomDao;
    private final Status.Type[] types;
    private final Comparator<Room> SORT_BY_PRICE = Comparator.comparing(firstRoom -> String.valueOf(firstRoom.getPrice()));
    private final Comparator<Room> SORT_BY_CLASSIFICATION = Comparator.comparing(Room::getClassification);
    private final Comparator<Room> SORT_BY_ROOM_NUMBER = Comparator.comparing(firstRoom -> String.valueOf(firstRoom.getRoomNumber()));

    public RoomService(IRoomDao roomDao, Status.Type[] types) {
        this.roomDao = roomDao;
        this.types = types;
    }

    @Override
    public void add(Room room) {
        roomDao.create(room);
    }

    @Override
    public void delete(int index) {
        if (roomDao.readAll().size() < index) {
            System.err.println("Room with index: " + index + " dont exists.");
            return;
        }
        roomDao.delete(index);
    }

    @Override
    public double getPrice(int index) {
        return roomDao.read(index).getPrice();
    }

    @Override
    public int getSize() {
        return roomDao.readAll().size();
    }

    @Override
    public Status.Type getStatus(int index) {
        return roomDao.read(index).getStatus();
    }

    @Override
    public Room getRoom(int index) {
        return roomDao.read(index);
    }

    @Override
    public void changePrice(int index, double price) {
        Room room = new Room(roomDao.read(index));
        try {
            room.setPrice(price);
            roomDao.update(room);
        } catch (Exception e) {
            System.err.println("Wrong data.");
        }
    }

    @Override
    public void changeStatus(int index, Status.Type status) {
        Room room = new Room(roomDao.read(index));
        try {
            room.setStatus(status);
            roomDao.update(room);
        } catch (Exception e) {
            System.err.println("Wrong data.");
        }
    }

    @Override
    public void show(String parameter) {
        switch (parameter) {
            case "all":
                showAllRoom();
                break;
            case "free":
                showFreeRoom();
                break;
        }
    }

    @Override
    public void show(MyList<Room> myList) {
        for (int i = 0; i < myList.size(); i++) {
            System.out.print(myList.get(i));
        }
    }

    private void showAllRoom() {
        for (int i = 1; i <= roomDao.readAll().size(); i++) {
            System.out.print(roomDao.read(i));
        }
    }

    private void showFreeRoom() {
        for (int i = 1; i <= roomDao.readAll().size(); i++) {
            if (roomDao.read(i).getStatus().equals(types[0])) {
                System.out.print(roomDao.read(i));
            }
        }
    }

    @Override
    public void numFreeRoom() {
        int counter = 0;
        for (int i = 1; i <= roomDao.readAll().size(); i++) {
            if (roomDao.read(i).getStatus().equals(types[0])) {
                counter++;
            }
        }
        System.out.println("\nNumber of free room: " + counter);
    }

    @Override
    public MyList<Room> sort(String parameter) {
        MyList<Room> myList = new MyList<>();
        createBufList(myList);
        switch (parameter) {
            case "price":
                sortByPrice(myList);
                return myList;
            case "classification":
                sortByClassification(myList);
                return myList;
            case "room number":
                sortByRoomNumber(myList);
                return myList;
        }
        return null;
    }

    private void createBufList(MyList<Room> myList) {
        for (int i = 1; i <= roomDao.readAll().size(); i++) {
            myList.add(roomDao.read(i));
        }
    }

    private void sortByPrice(MyList<Room> myList) {
        myList.sort(SORT_BY_PRICE);
    }

    private void sortByClassification(MyList<Room> myList) {
        myList.sort(SORT_BY_CLASSIFICATION);
    }

    private void sortByRoomNumber(MyList<Room> myList) {
        myList.sort(SORT_BY_ROOM_NUMBER);
    }
}
