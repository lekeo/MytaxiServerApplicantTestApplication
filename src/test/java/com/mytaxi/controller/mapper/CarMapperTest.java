package com.mytaxi.controller.mapper;

import com.mytaxi.datatransferobject.CarDTO;
import com.mytaxi.domainobject.CarDO;
import com.mytaxi.domainobject.ManufacturerDO;
import com.mytaxi.domainvalue.EngineType;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.Assert.assertEquals;

@RunWith(SpringJUnit4ClassRunner.class)
public class CarMapperTest {


    @Test
    public void whenCreatingDtoFromDoCopyValues() {
        CarDO car = new CarDO("123", new ManufacturerDO("name"), EngineType.DIESEL, 1, false, 1);
        CarDTO carDto = CarMapper.makeCarDTO(car);

        assertEquals(null, carDto.getId());
        assertEquals(EngineType.DIESEL, carDto.getEngineType());
        assertEquals("123", carDto.getLicensePlate());
        assertEquals(null, carDto.getManufacturer());
        assertEquals(1, carDto.getRating());
        assertEquals(1, carDto.getSeatCount());
        assertEquals(false, carDto.isConvertible());
    }

    @Test
    public void whenCreatingDtoFromDoReturnNullIfDoIsNull() {
        CarDTO carDto = CarMapper.makeCarDTO(null);
        assertEquals(null, carDto);
    }

}