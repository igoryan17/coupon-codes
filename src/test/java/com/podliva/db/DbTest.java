package com.podliva.db;

import com.podliva.db.model.CouponInformation;
import com.podliva.db.repositories.CouponRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Collections;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@Import(DbTestConfiguration.class)
@ActiveProfiles("test")
public class DbTest {
    @Autowired
    private CouponRepository couponRepository;

    private CouponInformation couponInformation;

    @Before
    public void createEntity() {
        couponInformation = CouponInformation.builder()
                .code("Купоны")
                .description("Скидка 30%")
                .id(365L)
                .locations(Collections.singletonList("Москва"))
                .build();
    }

    @Test
    public void testSave() {
        couponRepository.save(couponInformation);
    }

    @Test
    public void testFindById() {
        assertEquals(couponInformation, couponRepository.findById(couponInformation.getId()));
    }
}
