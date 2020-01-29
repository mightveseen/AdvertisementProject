package com.senlainc.git_courses.java_training.petushok_valiantsin.service;

import com.senlainc.git_courses.java_training.petushok_valiantsin.api.repository.IRoomDao;
import com.senlainc.git_courses.java_training.petushok_valiantsin.api.service.IRoomService;
import com.senlainc.git_courses.java_training.petushok_valiantsin.model.Room;
import com.senlainc.git_courses.java_training.petushok_valiantsin.model.status.RoomStatus;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class RoomService implements IRoomService {
    private final IRoomDao roomDao;
    private final Comparator<Room> SORT_BY_PRICE = Comparator.comparing(room -> String.valueOf(room.getPrice()));
    private final Comparator<Room> SORT_BY_CLASSIFICATION = Comparator.comparing(Room::getClassification);
    private final Comparator<Room> SORT_BY_ROOM_NUMBER = Comparator.comparing(room -> String.valueOf(room.getRoomNumber()));

    public RoomService(IRoomDao roomDao) {
        this.roomDao = roomDao;
    }

    @Override
    public void add(Room room) {
        if (roomDao.readAll().stream().anyMatch(i -> i.getNumber() == room.getNumber())) {
            throw new NullPointerException("Room with number: " + room.getNumber() + " already exists.");
        }
        roomDao.create(room);
    }

    @Override
    public void delete(int index) {
        try {
            roomDao.delete(index);
        } catch (ArrayIndexOutOfBoundsException e) {
            throw new NullPointerException("Room with index: " + index + " dont exists.");
        }
    }

    @Override
    public List<Room> getRoomList() {
        return roomDao.readAll();
    }

    @Override
    public Room getRoom(int index) {
        try {
            return roomDao.read(index);
        } catch (ArrayIndexOutOfBoundsException e) {
            throw new RuntimeException("Room with index: " + index + " dont exists.", e);
        }
    }

    @Override
    public void changePrice(int index, double price) {
        try {
            Room room = roomDao.read(index);
            room.setPrice(price);
            roomDao.update(room);
        } catch (ArrayIndexOutOfBoundsException e) {
            throw new RuntimeException("Room with index: " + index + " dont exists.", e);
        }
    }

    @Override
    public void changeStatus(int index, RoomStatus status) {
        try {
            Room room = roomDao.read(index);
            room.setStatus(status);
            roomDao.update(room);
        } catch (ArrayIndexOutOfBoundsException e) {
            throw new RuntimeException("Room with index: " + index + " dont exists.", e);
        }
    }

    @Override
    public List<String> show(String parameter, List<Room> myList) {
        if(parameter.equals("free")) {
            return showFreeRoom(myList);
        }
        return createStringList(myList);
    }

    private List<String> showFreeRoom(List<Room> myList) {
        return createStringList(myList.stream().filter(i -> i.getStatus().equals(RoomStatus.FREE)).collect(Collectors.toList()));
    }

    @Override
    public String numFreeRoom() {
        final long counter = roomDao.readAll().stream().filter(i -> i.getStatus().equals(RoomStatus.FREE)).count();
        return "Number of free room: " + counter;
    }

    @Override
    public List<Room> sort(String parameter) {
        List<Room> myList = roomDao.readAll();
        switch (parameter) {
            case "price":
                sortByPrice(myList);
                break;
            case "classification":
                sortByClassification(myList);
                break;
            case "room number":
                sortByRoomNumber(myList);
                break;
        }
        return myList;
    }

    private void sortByPrice(List<Room> myList) {
        myList.sort(SORT_BY_PRICE);
    }

    private void sortByClassification(List<Room> myList) {
        myList.sort(SORT_BY_CLASSIFICATION);
    }

    private void sortByRoomNumber(List<Room> myList) {
        myList.sort(SORT_BY_ROOM_NUMBER);
    }

    private List<String> createStringList(List<Room> roomList) {
        List<String> stringList = new ArrayList<>();
        for(Room room : roomList) {
            stringList.add(room.toString());
        }
        return stringList;
    }
}
