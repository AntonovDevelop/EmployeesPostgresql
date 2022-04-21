package com.company.repository;

import com.company.dao.Dao;
import com.company.dao.PositionDao;
import com.company.exceptions.NonExistentCustomerException;
import com.company.exceptions.NonExistentEntityException;
import com.company.model.Position;

import java.util.Collection;
import java.util.Optional;

public class PositionRepository {
    private static final Dao<Position, Integer> positionDao = new PositionDao();

    public static Position getPosition(int id) throws NonExistentEntityException {
        Optional<Position> position = positionDao.get(id);
        return position.orElseThrow(NonExistentCustomerException::new);
    }

    public static Collection<Position> getAllPositions() {
        return positionDao.getAll();
    }

    public static void updatePosition(Position position) {
        positionDao.update(position);
    }

    public static Optional<Integer> addPosition(Position position) {
        return positionDao.save(position);
    }

    public static void deletePosition(Position position) {
        positionDao.delete(position);
    }
}
