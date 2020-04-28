package com.senlainc.git_courses.java_training.petushok_valiantsin.service;

import com.senlainc.git_courses.java_training.petushok_valiantsin.api.repository.IRoomDao;
import com.senlainc.git_courses.java_training.petushok_valiantsin.api.service.IRoomService;
import com.senlainc.git_courses.java_training.petushok_valiantsin.model.Room;
import com.senlainc.git_courses.java_training.petushok_valiantsin.model.status.RoomStatus;
import com.senlainc.git_courses.java_training.petushok_valiantsin.utility.exception.ElementAlreadyExistsException;
import com.senlainc.git_courses.java_training.petushok_valiantsin.utility.exception.ElementNotAvailableException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@PropertySource(value = {"classpath:/properties/room.properties"}, ignoreResourceNotFound = true)
public class RoomService implements IRoomService {

    private final IRoomDao roomDao;
    @Value("${ROOM_CONFIG.CHANGE_STATUS_VALUE:true}")
    private boolean changeStatusProperty;

    @Autowired
    public RoomService(IRoomDao roomDao) {
        this.roomDao = roomDao;
    }

    @Override
    @Transactional
    public void create(Room object) {
        if (roomDao.readByNumber(object.getNumber())) {
            throw new ElementAlreadyExistsException("Room with number: " + object.getNumber() + " already exists.");
        }
        roomDao.create(object);
    }

    @Override
    @Transactional
    public void delete(Long index) {
        roomDao.delete(roomDao.read(index));
    }

    @Override
    @Transactional
    public void update(Room object) {
        roomDao.update(object);
    }

    @Override
    @Transactional
    public void changeStatus(long index, String status) {
        if (changeStatusProperty) {
            final Room room = roomDao.read(index);
            room.setStatus(RoomStatus.valueOf(status));
            roomDao.update(room);
        } else {
            throw new ElementNotAvailableException("Property for change status is false");
        }
    }

    @Override
    public Long getNumFree() {
        return roomDao.readFreeSize();
    }

    @Override
    public RoomStatus getRoomStatus(long index) {
        return roomDao.readStatus(index);
    }

    @Override
    public Room read(Long index) {
        return roomDao.read(index);
    }

    @Override
    public List<Room> readAll(int firstElement, int maxResult) {
        return roomDao.readAll(firstElement, maxResult);
    }

    @Override
    public List<Room> readAll(String parameter, int firstElement, int maxResult) {
        if (parameter.equals("free")) {
            return roomDao.readAllFree(firstElement, maxResult);
        }
        return roomDao.readAll(firstElement, maxResult);
    }

    @Override
    public List<Room> readAllSorted(String type, int firstElement, int maxResult, String parameter) {
        if (parameter.equals("default")) {
            return readAll(type, firstElement, maxResult);
        }
        if (type.equals("free")) {
            return roomDao.readAllFree(firstElement, maxResult, parameter);
        }
        return roomDao.readAll(firstElement, maxResult, parameter);
    }
}
