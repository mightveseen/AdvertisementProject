package com.senlainc.git_courses.java_training.petushok_valiantsin.service;

import com.senlainc.git_courses.java_training.petushok_valiantsin.api.repository.IAttendanceDao;
import com.senlainc.git_courses.java_training.petushok_valiantsin.api.service.IAttendanceService;
import com.senlainc.git_courses.java_training.petushok_valiantsin.model.Attendance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class AttendanceService implements IAttendanceService {

    private final IAttendanceDao attendanceDao;

    @Autowired
    public AttendanceService(IAttendanceDao attendanceDao) {
        this.attendanceDao = attendanceDao;
    }

    @Override
    @Transactional
    public void create(Attendance object) {
        attendanceDao.create(object);
    }

    @Override
    @Transactional
    public void delete(Long index) {
        attendanceDao.delete(attendanceDao.read(index));
    }

    @Override
    @Transactional
    public void update(Attendance object) {
        attendanceDao.update(object);
    }

    @Override
    @Transactional
    public void changePrice(long index, double price) {
        final Attendance attendance = attendanceDao.read(index);
        attendance.setPrice(price);
        attendanceDao.update(attendance);
    }

    @Override
    @Transactional(readOnly = true)
    public Attendance read(Long index) {
        return attendanceDao.read(index);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Attendance> readAll(int firstElement, int maxResult) {
        return attendanceDao.readAll(firstElement, maxResult);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Attendance> readAllSorted(String parameter, int firstElement, int maxResult) {
        if (parameter.equals("default")) {
            return readAll(firstElement, maxResult);
        }
        return attendanceDao.readAll(firstElement, maxResult, parameter);
    }
}
