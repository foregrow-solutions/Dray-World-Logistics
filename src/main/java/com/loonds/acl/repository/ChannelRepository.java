package com.loonds.acl.repository;

import com.loonds.acl.model.entity.Channel;
import com.loonds.acl.model.entity.User;
import com.loonds.acl.model.enums.Status;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface ChannelRepository extends JpaRepository<Channel, String> {
    List<Channel> findAllByUser(User user);
    Page<Channel> findAllByStatus(Status status, Pageable pageable);

    Optional<Channel> findFirstByPublicId(String publicId);

}
