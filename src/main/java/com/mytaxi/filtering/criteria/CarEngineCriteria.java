package com.mytaxi.filtering.criteria;

import com.mytaxi.domainobject.CarDO;
import com.mytaxi.domainobject.DriverDO;
import com.mytaxi.domainvalue.EngineType;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class CarEngineCriteria implements Criteria {

    private final EngineType engineType;

    public CarEngineCriteria(EngineType engineType){
        this.engineType = engineType;
    }

    @Override
    public List<DriverDO> meetCriteria(final List<DriverDO> drivers) {
        return drivers.stream()
                .filter(d -> {
                    Optional<CarDO> car = Optional.ofNullable(d.getCar());
                    Optional<EngineType> carEngineType = car.map(CarDO::getEngineType);
                    return engineType==carEngineType.orElse(null);
                })
                .collect(Collectors.toList());
    }
}
