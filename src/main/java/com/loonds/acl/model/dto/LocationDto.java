package com.loonds.acl.model.dto;

import com.loonds.acl.model.entity.Location;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class LocationDto {
    long id;
    String shipFrom;
    String shipTo;
    LocalDate shipFromDate;
    LocalDate shipToDate;

    public static LocationDto of(Location location) {
        LocationDto output = new LocationDto();
        output.setId(location.getId());
        output.setShipFrom(location.getShipFrom());
        output.setShipTo(location.getShipTo());
        output.setShipFromDate(location.getShipFromDate());
        output.setShipToDate(location.getShipToDate());
        return output;
    }
}
