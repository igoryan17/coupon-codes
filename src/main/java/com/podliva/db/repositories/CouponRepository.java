package com.podliva.db.repositories;

import com.podliva.db.model.CouponInformation;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;

public interface CouponRepository extends MongoRepository<CouponInformation, Long> {
    CouponInformation findById(Long id);
    CouponInformation findByCode(String code);
    List<CouponInformation> findByPriceLessThan(Integer msxPrice);
    @Query(value = "{ 'locations': { $in: [?0, { $regex: '^Все города.*', $options: 'i' }] } }")
    List<CouponInformation> findByLocation(String location);
}
