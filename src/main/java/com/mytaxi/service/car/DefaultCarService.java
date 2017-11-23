package com.mytaxi.service.car;

import com.mytaxi.dataaccessobject.CarRepository;
import com.mytaxi.dataaccessobject.ManufacturerRepository;
import com.mytaxi.domainobject.CarDO;
import com.mytaxi.domainobject.ManufacturerDO;
import com.mytaxi.domainvalue.EngineType;
import com.mytaxi.exception.ConstraintsViolationException;
import com.mytaxi.exception.EntityNotFoundException;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Default Service to encapsulate the link between car DAO and  car controller and to have business logic for cars.
 * <p/>
 */

@Service
public class DefaultCarService implements CarService {

    private static org.slf4j.Logger LOG = LoggerFactory.getLogger(DefaultCarService.class);

    private CarRepository carRepository;
    private ManufacturerRepository manufacturerRepository;

    @Autowired
    public DefaultCarService(CarRepository carRepository, ManufacturerRepository manufacturerRepository) {
        this.carRepository = carRepository;
        this.manufacturerRepository = manufacturerRepository;
    }

    /**
     * Creates a new car.
     *
     * @param licensePlate
     * @param manufacturerId
     * @param rating
     * @param seatCount
     * @param engineType
     * @param convertible
     * @return CarDO
     * @throws ConstraintsViolationException if a car already exists with the given licence or if some constraints are thrown due to car creation ... .
     * @throws EntityNotFoundException if a manufacturer is not found
     */
    @Override
    public CarDO create(String licensePlate, Long manufacturerId, int rating, int seatCount, EngineType engineType, boolean convertible) throws EntityNotFoundException, ConstraintsViolationException {
        Optional<ManufacturerDO> manufacturer = Optional.ofNullable(manufacturerRepository.findOne(manufacturerId));

        manufacturer.orElseThrow( () -> {
            LOG.warn("Could not find manufacturer with id: " + manufacturerId);
            return new EntityNotFoundException("Could not find manufacturer with id: " + manufacturerId);
        });

        Optional<CarDO> existingCar = carRepository.findByLicensePlate(licensePlate);
        if(existingCar.isPresent()){
            LOG.warn("A car with informed license plate already exists");
            throw new ConstraintsViolationException("A car with informed license plate already exists");
        }

        CarDO car = new CarDO(licensePlate, manufacturer.get(), engineType, rating, convertible, seatCount);
        try {
            return carRepository.save(car);
        } catch (DataIntegrityViolationException e) {
            LOG.warn("Some constraints are thrown due to car creation", e);
            throw new ConstraintsViolationException(e.getMessage());
        }
    }

    /**
     * Selects a car by id.
     *
     * @param  id
     * @return found car
     * @throws EntityNotFoundException if no car with the given id was found.
     */
    @Override
    public CarDO find(long id) throws EntityNotFoundException {
        Optional<CarDO> car = Optional.ofNullable(carRepository.findOne(id));
        return car.orElseThrow(() -> {
            LOG.warn("Could not find car with id: " + id);
            return new EntityNotFoundException("Could not find car with id: " + id);
        });
    }

    /**
     * Save a car to database.
     *
     * @param  car
     * @return saved car object
     */
    @Override
    public CarDO save(CarDO car) {
        return carRepository.save(car);
    }

    /**
     * Find all cars.
     *
     * @return List of cars
     */
    @Override
    public List<CarDO> find() {
        List<CarDO> result = new ArrayList<>();
        carRepository.findAll().forEach(result::add);
        return result;
    }

    /**
     * Delete a car by car id.
     * @param carId
     */
    @Override
    public void delete(long carId) {
        carRepository.delete(carId);
    }

    /**
     * Update a car by id and rating field
     *
     * @param  id
     * @param  rating
     * @return updated car
     * @throws EntityNotFoundException if no car with the given id was found.
     */
    @Override
    public CarDO update(long id, int rating) throws EntityNotFoundException {
        Optional<CarDO> car = Optional.ofNullable(carRepository.findOne(id));

        CarDO c = car.orElseThrow(() -> {
            LOG.warn("Could not find car with id: " + id);
            return new EntityNotFoundException("Could not find car with id: " + id);
        });

        c.setRating(rating);

        return carRepository.save(c);
    }

}
