package com.mytaxi.controller.mapper;

import com.mytaxi.datatransferobject.CarDTO;
import com.mytaxi.domainobject.CarDO;
import com.mytaxi.domainobject.ManufacturerDO;

import java.util.Optional;

public class CarMapper {

    public static CarDTO makeCarDTO(CarDO car) {
        return Optional.ofNullable(car).map( c -> {
            Long manufacturerId =  Optional.ofNullable(car.getManufacturer())
                    .map(ManufacturerDO::getId).orElse(null);

            return new CarDTO(c.getId(),
                    c.getLicensePlate(),
                    c.getSeatCount(),
                    c.isConvertible(),
                    c.getRating(),
                    c.getEngineType(),
                    manufacturerId);
        }).orElse(null);
    }
}
