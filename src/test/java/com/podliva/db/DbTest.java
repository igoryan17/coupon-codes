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

import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@RunWith(SpringRunner.class)
@SpringBootTest
@Import(DbTestConfiguration.class)
@ActiveProfiles("test")
public class DbTest {
    @Autowired
    private CouponRepository couponRepository;

    @Before
    public void clearDB() {
        couponRepository.deleteAll();
    }

    @Test
    public void testSaveAndFind() {
        CouponInformation couponInformation = CouponInformation.builder()
                .id(1488L)
                .code("testSaveAndFind")
                .locations(Collections.singletonList("Moscow"))
                .build();
        couponRepository.save(couponInformation);
        assertEquals(couponInformation, couponRepository.findById(1488L));
    }

    @Test
    public void testFindByLocation() {
        CouponInformation moscow = CouponInformation.builder()
                .id(1L)
                .locations(Collections.singletonList("Moscow"))
                .code("testFindByLocation")
                .build();
        moscow = couponRepository.save(moscow);
        List<CouponInformation> foundResult = couponRepository.findByLocation("Moscow");
        assertTrue(foundResult.size() == 1);
        assertEquals(moscow, couponRepository.findByLocation("Moscow").iterator().next());
    }

    @Test
    public void testFindByLocationWithAllCities() {
        CouponInformation moscow = CouponInformation.builder()
                .id(1L)
                .code("Moscow")
                .locations(Collections.singletonList("Moscow"))
                .build();
        moscow = couponRepository.save(moscow);
        CouponInformation allCity = CouponInformation.builder()
                .id(2L)
                .code("Все города")
                .locations(Collections.singletonList("Все города*"))
                .build();
        allCity = couponRepository.save(allCity);
        CouponInformation unexpected = CouponInformation.builder()
                .id(3L)
                .code("Dolgopa")
                .locations(Collections.singletonList("Dolgopa"))
                .build();
        couponRepository.save(unexpected);
        List<CouponInformation> expected = Arrays.asList(moscow, allCity);
        Comparator<CouponInformation> comparator = Comparator.comparing(CouponInformation::getId);
        expected.sort(comparator);
        List<CouponInformation> actual = couponRepository.findByLocation("Moscow");
        actual.sort(comparator);
        assertEquals(expected, actual);
    }
}
