package com.loonds.acl.model.payload;

import com.loonds.acl.model.dto.LocationDto;
import com.loonds.acl.model.enums.ChannelType;
import lombok.Data;

import java.util.List;

@Data
public class CreateLoadPayload {
    String name;
    String carrierNumber;
    long customerId;
    long driverId;
    List<LocationDto> locations;

    ChannelType type;

}
