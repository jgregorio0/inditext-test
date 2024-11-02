package com.sngular.test.inditex.integration;


import com.sngular.test.inditex.dto.PriceDto;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.test.context.jdbc.Sql.ExecutionPhase.AFTER_TEST_CLASS;
import static org.springframework.test.context.jdbc.Sql.ExecutionPhase.BEFORE_TEST_CLASS;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Sql(scripts = {"/insert_prices_data.sql"}, executionPhase = BEFORE_TEST_CLASS)
@Sql(scripts = {"/delete_prices_data.sql"}, executionPhase = AFTER_TEST_CLASS)
@ActiveProfiles("test")
public class PriceIntegrationTest {

    @LocalServerPort
    private int port;
    private final String urlBase = "http://localhost";
    private final TestRestTemplate restTemplate = new TestRestTemplate();


    private final int product35355 = 35455;
    private final int brandZara = 1;

    /**
     * Test 1: petición a las 10:00 del día 14 del producto 35455   para la brand 1 (ZARA)
     * Assert that returns price 1: (1, 1, '2020-06-14 00:00:00', '2020-12-31 23:59:59', 1, 35455, 0, 35.50, 'EUR')
     */
    @Test
    public void test1_givenDay14At10_whenGetPrices_thenOKAndReturnPrice1() {
        // GIVEN
        final String day14At10 = "2020-06-14 10:00:00";
        // WHEN
        String url = String.format("%s:%d/api/v1/prices?productId=%d&brandId=%d&date=%s",
                urlBase, port, product35355, brandZara, day14At10);
        ResponseEntity<PriceDto> response = restTemplate.getForEntity(url, PriceDto.class);
        // THEN returns price 1: (1, 1, '2020-06-14 00:00:00', '2020-12-31 23:59:59', 1, 35455, 0, 35.50, 'EUR'),
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(brandZara, response.getBody().getBrandId());
        assertEquals("2020-06-14 00:00:00", response.getBody().getStartDate());
        assertEquals("2020-12-31 23:59:59", response.getBody().getEndDate());
        assertEquals(1, response.getBody().getPriceList());
        assertEquals(product35355, response.getBody().getProductId());
        assertEquals(0, BigDecimal.valueOf(35.50).compareTo(response.getBody().getPrice()));
        assertEquals("EUR", response.getBody().getCurrency());
    }

    /**
     * Test 2: petición a las 16:00 del día 14 del producto 35455   para la brand 1 (ZARA)
     * Assert that returns price 2: (2, 1, '2020-06-14 15:00:00', '2020-06-14 18:30:00', 2, 35455, 1, 25.45, 'EUR')
     */
    @Test
    public void test2_givenDay14At16_whenGetPrices_thenOKAndReturnPrice2() {
        // GIVEN
        final String day14At16 = "2020-06-14 16:00:00";
        // WHEN
        String url = String.format("%s:%d/api/v1/prices?productId=%d&brandId=%d&date=%s",
                urlBase, port, product35355, brandZara, day14At16);
        ResponseEntity<PriceDto> response = restTemplate.getForEntity(url, PriceDto.class);
        // THEN returns price 2: (2, 1, '2020-06-14 15:00:00', '2020-06-14 18:30:00', 2, 35455, 1, 25.45, 'EUR'),
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(brandZara, response.getBody().getBrandId());
        assertEquals("2020-06-14 15:00:00", response.getBody().getStartDate());
        assertEquals("2020-06-14 18:30:00", response.getBody().getEndDate());
        assertEquals(2, response.getBody().getPriceList());
        assertEquals(product35355, response.getBody().getProductId());
        assertEquals(0, BigDecimal.valueOf(25.45).compareTo(response.getBody().getPrice()));
        assertEquals("EUR", response.getBody().getCurrency());
    }

    /**
     * Test 3: petición a las 21:00 del día 14 del producto 35455   para la brand 1 (ZARA)
     * Assert that returns price 1: (1, 1, '2020-06-14 00:00:00', '2020-12-31 23:59:59', 1, 35455, 0, 35.50, 'EUR')
     */
    @Test
    public void test3_givenDay14At21_whenGetPrices_thenOKAndReturnPrice1() {
        // GIVEN
        final String day14At21 = "2020-06-14 21:00:00";
        // WHEN
        String url = String.format("%s:%d/api/v1/prices?productId=%d&brandId=%d&date=%s",
                urlBase, port, product35355, brandZara, day14At21);
        ResponseEntity<PriceDto> response = restTemplate.getForEntity(url, PriceDto.class);
        // THEN returns price 1: (1, 1, '2020-06-14 00:00:00', '2020-12-31 23:59:59', 1, 35455, 0, 35.50, 'EUR'),
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(brandZara, response.getBody().getBrandId());
        assertEquals("2020-06-14 00:00:00", response.getBody().getStartDate());
        assertEquals("2020-12-31 23:59:59", response.getBody().getEndDate());
        assertEquals(1, response.getBody().getPriceList());
        assertEquals(product35355, response.getBody().getProductId());
        assertEquals(0, BigDecimal.valueOf(35.50).compareTo(response.getBody().getPrice()));
        assertEquals("EUR", response.getBody().getCurrency());
    }

    /**
     * Test 4: petición a las 10:00 del día 15 del producto 35455   para la brand 1 (ZARA)
     * Assert that returns price 3: (3, 1, '2020-06-15 00:00:00', '2020-06-15 11:00:00', 3, 35455, 1, 30.50, 'EUR')
     */
    @Test
    public void test4_givenDay15At10_whenGetPrices_thenOKAndReturnPrice3() {
        // GIVEN
        final String day15At10 = "2020-06-15 10:00:00";
        // WHEN
        String url = String.format("%s:%d/api/v1/prices?productId=%d&brandId=%d&date=%s",
                urlBase, port, product35355, brandZara, day15At10);
        ResponseEntity<PriceDto> response = restTemplate.getForEntity(url, PriceDto.class);
        // THEN returns price 3: (3, 1, '2020-06-15 00:00:00', '2020-06-15 11:00:00', 3, 35455, 1, 30.50, 'EUR')
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(brandZara, response.getBody().getBrandId());
        assertEquals("2020-06-15 00:00:00", response.getBody().getStartDate());
        assertEquals("2020-06-15 11:00:00", response.getBody().getEndDate());
        assertEquals(3, response.getBody().getPriceList());
        assertEquals(product35355, response.getBody().getProductId());
        assertEquals(0, BigDecimal.valueOf(30.50).compareTo(response.getBody().getPrice()));
        assertEquals("EUR", response.getBody().getCurrency());
    }

    /**
     * Test 5: petición a las 21:00 del día 16 del producto 35455   para la brand 1 (ZARA)
     * Assert that returns price 4: (4, 1, '2020-06-15 16:00:00', '2020-12-31 23:59:59', 4, 35455, 1, 38.95, 'EUR')
     */
    @Test
    public void test5_givenDay16At21_whenGetPrices_thenOKAndReturnPrice4() {
        // GIVEN
        final String day16At21 = "2020-06-16 21:00:00";
        // WHEN
        String url = String.format("%s:%d/api/v1/prices?productId=%d&brandId=%d&date=%s",
                urlBase, port, product35355, brandZara, day16At21);
        ResponseEntity<PriceDto> response = restTemplate.getForEntity(url, PriceDto.class);
        // THEN returns price 3: (3, 1, '2020-06-15 00:00:00', '2020-06-15 11:00:00', 3, 35455, 1, 30.50, 'EUR')
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(brandZara, response.getBody().getBrandId());
        assertEquals("2020-06-15 16:00:00", response.getBody().getStartDate());
        assertEquals("2020-12-31 23:59:59", response.getBody().getEndDate());
        assertEquals(4, response.getBody().getPriceList());
        assertEquals(product35355, response.getBody().getProductId());
        assertEquals(0, BigDecimal.valueOf(38.95).compareTo(response.getBody().getPrice()));
        assertEquals("EUR", response.getBody().getCurrency());
    }
}
