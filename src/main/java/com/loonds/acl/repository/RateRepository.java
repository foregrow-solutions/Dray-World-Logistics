package com.loonds.acl.repository;

import com.loonds.acl.model.entity.Channel;
import com.loonds.acl.model.entity.Rate;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RateRepository extends JpaRepository<Rate, Long> {
    List<Rate> findAllByChannel(Channel channel);


//    @Query("DELETE FORM RATE r where r.channel= :channel")
//    void deleteByChannel(@Param("channel") Channel channel);
}
