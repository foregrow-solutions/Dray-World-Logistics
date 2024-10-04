package com.loonds.acl.service.impl;

import com.loonds.acl.event.NotificationEvent;
import com.loonds.acl.model.dto.ChannelDto;
import com.loonds.acl.model.entity.*;
import com.loonds.acl.model.enums.RateType;
import com.loonds.acl.model.enums.Status;
import com.loonds.acl.model.payload.CreateLoadPayload;
import com.loonds.acl.repository.*;
import com.loonds.acl.service.ChannelService;
import com.loonds.acl.util.RandomIdGenerator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class ChannelServiceImpl implements ChannelService {
    private final UserRepository userRepository;
    private final CustomerRepository customerRepository;
    private final DriverRepository driverRepository;
    private final ChannelRepository channelRepository;
    private final RateRepository rateRepository;
    private final LocationRepository locationRepository;
    private final DocumentRepository documentRepository;
    private final ApplicationEventPublisher eventPublisher;

    @Override
    @Transactional
    public ChannelDto create(String userId, CreateLoadPayload payload) {
        User user = userRepository.getReferenceById(userId);
        Customer customer = customerRepository.getReferenceById(payload.getCustomerId());
        Driver driver = driverRepository.getReferenceById(payload.getDriverId());
        Channel channel = new Channel();
        channel.setCarrierNumber(payload.getCarrierNumber());
        channel.setPublicId("DWL-" + RandomIdGenerator.generateLoadNumber());
        channel.setCustomer(customer);
        channel.setDriver(driver);
        channel.setUser(user);
        Set<Location> locations = new HashSet<>();
        payload.getLocations().forEach(locationDto -> {
            Location location = new Location();
            location.setShipFrom(locationDto.getShipFrom());
            location.setShipTo(locationDto.getShipTo());
            location.setShipToDate(locationDto.getShipToDate());
            location.setShipFromDate(locationDto.getShipFromDate());
            Location save = locationRepository.save(location);
            locations.add(save);
        });
        channel.getLocations().addAll(locations);

        channel.setStatus(Status.PENDING);
        channel.setCreatedDate(Instant.now());
        channel.setModifiedDate(Instant.now());
        Channel save = channelRepository.save(channel);
        eventPublisher.publishEvent(new NotificationEvent(userId, "Create New", "Load Created Successfully"));

        return ChannelDto.of(save);
    }

    @Override
    @Transactional
    public Optional<ChannelDto> update(String userId, String id, ChannelDto channelDto) {
        return channelRepository.findById(id).map(channel -> {
            channel.setCarrierNumber(channelDto.getCarrierNumber());
            channel.setSize(channelDto.getSize());
            channel.setWeight(channelDto.getWeight());


            // Add new customer rates
            if (channelDto.getCustomerRate() != null) {
                channel.getRates().removeIf(rate -> rate.getType() == RateType.CUSTOMER);

                channelDto.getCustomerRate().forEach(rateDto -> {
                    Rate rate = createRate(channel, rateDto.getLabel(), rateDto.getAmount(), RateType.CUSTOMER);
                    channel.getRates().add(rate);
                });
            }


            // Add new driver rates
            if (channelDto.getDriverRate() != null) {
                channel.getRates().removeIf(rate -> rate.getType() == RateType.DRIVER);
                channelDto.getDriverRate().forEach(rateDto -> {
                    Rate rate = createRate(channel, rateDto.getLabel(), rateDto.getAmount(), RateType.DRIVER);
                    channel.getRates().add(rate);
                });
            }

            // Save the updated rates
            List<Rate> savedRates = rateRepository.saveAll(channel.getRates());

            channel.setStatus(channelDto.getStatus());
            channel.setModifiedDate(Instant.now());
            return ChannelDto.of(channel);
        });
    }



    private Rate createRate(Channel channel, String label, double amount, RateType type) {
        Rate rate = new Rate();
        rate.setChannel(channel);
        rate.setLabel(label);
        rate.setAmount(amount);
        rate.setType(type);
        return rate;
    }


    @Override
    public Optional<ChannelDto> get(String id) {
        return channelRepository.findById(id).map(ChannelDto::of);
    }

    @Override
    public Page<ChannelDto> getLoadByStatus(Status status, Pageable pageable) {
        return channelRepository.findAllByStatus(status, pageable).map(ChannelDto::of);
    }

    @Override
    public List<ChannelDto> getAllUserLoad(String userId) {
        User user = userRepository.getReferenceById(userId);
        return channelRepository.findAllByUser(user).stream().map(ChannelDto::of).collect(Collectors.toList());
    }

    @Override
    public List<ChannelDto> getAllLoad() {
        return channelRepository.findAll().stream().map(ChannelDto::of).collect(Collectors.toList());
    }


    @Override
    public Optional<Channel> findByPid(String publicId) {
        return channelRepository.findFirstByPublicId(publicId);
    }

    @Override
    @Transactional
    public Optional<ChannelDto> updateStatus(String id, Status status) {
        return channelRepository.findById(id).map(channel -> {
            driverRepository.findById(channel.getDriver().getId()).map(driver -> {
//                driver.setApproved(true);
                return driver;
            });
            customerRepository.findById(channel.getCustomer().getId()).map(customer -> {
//                customer.setApproved(true);
                return channel;
            });
            channel.setStatus(status);
            return ChannelDto.of(channel);
        });
    }

    @Override
    @Transactional
    public void delete(String id) {
        if (channelRepository.existsById(id)){
            channelRepository.deleteById(id);
        }
    }

    @Override
    @Transactional
    public void deleteAll() {
        channelRepository.deleteAll();
    }


}
