package com.loonds.acl.model.dto;

import com.loonds.acl.model.entity.Channel;
import com.loonds.acl.model.enums.ChannelType;
import com.loonds.acl.model.enums.RateType;
import com.loonds.acl.model.enums.Status;
import lombok.Getter;
import lombok.Setter;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Getter
@Setter
public class ChannelDto {
    String id;
    String publicId;
    String agentName;
    String customerName;
    String carrierNumber;
    String weight;
    String size;
    List<Long> documentIds;
    List<LocationDto> locations;

    List<RateDto> customerRate;
    List<RateDto> driverRate;

    List<DocumentDto> documentList;

    Status status;


    ChannelType type;

    public static ChannelDto of(Channel channel) {
        ChannelDto output = new ChannelDto();
        output.setId(channel.getId());
        output.setPublicId(channel.getPublicId());
        output.setCarrierNumber(channel.getCarrierNumber());
        output.setWeight(channel.getWeight());
        output.setType(channel.getType());
        output.setStatus(channel.getStatus());
        output.setAgentName(channel.getUser().getFirstName());
        output.setCustomerName(channel.getCustomer().getName());
        output.setWeight(channel.getWeight());
        output.setLocations(Optional.ofNullable(channel.getLocations()).map(locations1 -> locations1.stream()
                        .map(LocationDto::of).collect(Collectors.toList()))
                .orElse(Collections.emptyList()));
        output.setCustomerRate(Optional.ofNullable(channel.getRates())
                .map(rates -> rates.stream().filter(rate -> rate.getType() == RateType.CUSTOMER).map(RateDto::of).collect(Collectors.toList()))
                .orElse(Collections.emptyList()));
        output.setDriverRate(Optional.ofNullable(channel.getRates())
                .map(rates -> rates.stream().filter(rate -> rate.getType() == RateType.DRIVER).map(RateDto::of).collect(Collectors.toList()))
                .orElse(Collections.emptyList()));
        output.setDocumentList(Optional.ofNullable(channel.getDocuments())
                .map(documents -> documents.stream().map(DocumentDto::of).collect(Collectors.toList()))
                .orElse(Collections.emptyList()));

        output.setStatus(channel.getStatus());
        return output;
    }
}
