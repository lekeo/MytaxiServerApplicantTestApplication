package com.mytaxi.service.car;

import com.mytaxi.domainobject.CarDO;
import com.mytaxi.domainvalue.EngineType;
import com.mytaxi.exception.ConstraintsViolationException;
import com.mytaxi.exception.EntityNotFoundException;

import java.util.List;

public interface CarService {

    CarDO create(String licensePlate, Long manufacturer, int rating, int seatCount, EngineType engineType, boolean convertible) throws EntityNotFoundException, ConstraintsViolationException;

    CarDO find(long id) throws EntityNotFoundException;

    CarDO save(CarDO car);

    List<CarDO> find();

    void delete(long carId);

    CarDO update(long carId, int rating) throws EntityNotFoundException;
}
