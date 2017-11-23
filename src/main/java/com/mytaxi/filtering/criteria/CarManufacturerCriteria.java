package com.mytaxi.filtering.criteria;

import com.mytaxi.domainobject.CarDO;
import com.mytaxi.domainobject.DriverDO;
import com.mytaxi.domainobject.ManufacturerDO;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class CarManufacturerCriteria implements Criteria {

    private final long manufacturerId;

    public CarManufacturerCriteria(long manufacturerId) {
        this.manufacturerId = manufacturerId;
    }

    @Override
    public List<DriverDO> meetCriteria(List<DriverDO> drivers) {
        return drivers.stream()
                .filter(d -> {
                    Optional<CarDO> car = Optional.ofNullable(d.getCar());
                    Optional<Long> manufacturer = car.map(CarDO::getManufacturer).map(ManufacturerDO::getId);

                    return new Long(manufacturerId).equals(manufacturer.orElse(null));
                })
                .collect(Collectors.toList());
    }
}