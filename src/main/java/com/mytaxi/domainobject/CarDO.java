package com.mytaxi.domainobject;

import com.mytaxi.domainvalue.EngineType;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.ZonedDateTime;
import java.util.Objects;

@Entity
@Table(
    name = "car",
    uniqueConstraints = @UniqueConstraint(name = "uc_license_plate", columnNames = {"licensePlate"})
)
public class CarDO {

    @Id
    @GeneratedValue
    private Long id;

    @Column(nullable = false)
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private ZonedDateTime dateCreated;

    @Column(nullable = false)
    @NotNull(message = "License plate can not be null!")
    private String licensePlate;

    private int seatCount;

    private boolean convertible;

    private int rating;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    @NotNull(message = "Engine type can not be null!")
    private EngineType engineType;

    @ManyToOne(optional=false)
    @JoinColumn(name="manufacturer_id", nullable=false, updatable=false)
    private ManufacturerDO manufacturer;

    private boolean selected;

    public CarDO(String licensePlate, ManufacturerDO manufacturer, EngineType engineType, int rating, boolean convertible, int seatCount) {
        this.id=null;
        this.manufacturer = manufacturer;
        this.engineType = engineType;
        this.rating = rating;
        this.convertible = convertible;
        this.seatCount = seatCount;
        this.licensePlate = licensePlate;
        this.dateCreated = ZonedDateTime.now();
        this.selected = false;
    }

    private CarDO(){}

    public Long getId() {
        return id;
    }

    public ZonedDateTime getDateCreated() {
        return ZonedDateTime.from(dateCreated);
    }

    public String getLicensePlate() {
        return licensePlate;
    }

    public int getSeatCount() {
        return seatCount;
    }

    public boolean isConvertible() {
        return convertible;
    }

    public int getRating() {
        return rating;
    }

    public EngineType getEngineType() {
        return engineType;
    }

    public ManufacturerDO getManufacturer() {
        return manufacturer;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CarDO carDO = (CarDO) o;
        return getSeatCount() == carDO.getSeatCount() &&
                isConvertible() == carDO.isConvertible() &&
                getRating() == carDO.getRating() &&
                selected == carDO.selected &&
                Objects.equals(getId(), carDO.getId()) &&
                Objects.equals(getDateCreated(), carDO.getDateCreated()) &&
                Objects.equals(getLicensePlate(), carDO.getLicensePlate()) &&
                getEngineType() == carDO.getEngineType() &&
                Objects.equals(getManufacturer(), carDO.getManufacturer());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getDateCreated(), getLicensePlate(), getSeatCount(), isConvertible(), getRating(), getEngineType(), getManufacturer(), selected);
    }
}
