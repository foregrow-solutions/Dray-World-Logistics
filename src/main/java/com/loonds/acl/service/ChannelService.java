package com.loonds.acl.service;

import com.loonds.acl.model.dto.ChannelDto;
import com.loonds.acl.model.entity.Channel;
import com.loonds.acl.model.enums.Status;
import com.loonds.acl.model.payload.CreateLoadPayload;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface ChannelService {
    ChannelDto create(String userId, CreateLoadPayload payload);

    Optional<ChannelDto> update(String userId, String id, ChannelDto channelDto);

    Optional<ChannelDto> get(String id);

    Page<ChannelDto> getLoadByStatus(Status status, Pageable pageable);

    List<ChannelDto> getAllUserLoad(String userId);

    List<ChannelDto> getAllLoad();

    Optional<Channel> findByPid(String publicId);

    Optional<ChannelDto> updateStatus(String id, Status status);

    void delete(String id);

    void deleteAll();

}
