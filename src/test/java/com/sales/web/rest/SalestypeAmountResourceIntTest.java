package com.sales.web.rest;

import com.sales.SalesMaintenanceApp;

import com.sales.domain.SalestypeAmount;
import com.sales.repository.SalestypeAmountRepository;
import com.sales.service.SalestypeAmountService;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import java.time.LocalDate;
import java.time.ZoneId;
import java.math.BigDecimal;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the SalestypeAmountResource REST controller.
 *
 * @see SalestypeAmountResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = SalesMaintenanceApp.class)
public class SalestypeAmountResourceIntTest {

    private static final Integer DEFAULT_SALES_TYPE_ID = 1;
    private static final Integer UPDATED_SALES_TYPE_ID = 2;

    private static final BigDecimal DEFAULT_TOTAL_AMOUNT = new BigDecimal(1);
    private static final BigDecimal UPDATED_TOTAL_AMOUNT = new BigDecimal(2);

    private static final LocalDate DEFAULT_SALE_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_SALE_DATE = LocalDate.now(ZoneId.systemDefault());

    @Inject
    private SalestypeAmountRepository salestypeAmountRepository;

    @Inject
    private SalestypeAmountService salestypeAmountService;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Inject
    private EntityManager em;

    private MockMvc restSalestypeAmountMockMvc;

    private SalestypeAmount salestypeAmount;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        SalestypeAmountResource salestypeAmountResource = new SalestypeAmountResource();
        ReflectionTestUtils.setField(salestypeAmountResource, "salestypeAmountService", salestypeAmountService);
        this.restSalestypeAmountMockMvc = MockMvcBuilders.standaloneSetup(salestypeAmountResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static SalestypeAmount createEntity(EntityManager em) {
        SalestypeAmount salestypeAmount = new SalestypeAmount()
                .salesTypeId(DEFAULT_SALES_TYPE_ID)
                .totalAmount(DEFAULT_TOTAL_AMOUNT)
                .saleDate(DEFAULT_SALE_DATE);
        return salestypeAmount;
    }

    @Before
    public void initTest() {
        salestypeAmount = createEntity(em);
    }

    @Test
    @Transactional
    public void createSalestypeAmount() throws Exception {
        int databaseSizeBeforeCreate = salestypeAmountRepository.findAll().size();

        // Create the SalestypeAmount

        restSalestypeAmountMockMvc.perform(post("/api/salestype-amounts")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(salestypeAmount)))
            .andExpect(status().isCreated());

        // Validate the SalestypeAmount in the database
        List<SalestypeAmount> salestypeAmountList = salestypeAmountRepository.findAll();
        assertThat(salestypeAmountList).hasSize(databaseSizeBeforeCreate + 1);
        SalestypeAmount testSalestypeAmount = salestypeAmountList.get(salestypeAmountList.size() - 1);
        assertThat(testSalestypeAmount.getSalesTypeId()).isEqualTo(DEFAULT_SALES_TYPE_ID);
        assertThat(testSalestypeAmount.getTotalAmount()).isEqualTo(DEFAULT_TOTAL_AMOUNT);
        assertThat(testSalestypeAmount.getSaleDate()).isEqualTo(DEFAULT_SALE_DATE);
    }

    @Test
    @Transactional
    public void createSalestypeAmountWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = salestypeAmountRepository.findAll().size();

        // Create the SalestypeAmount with an existing ID
        SalestypeAmount existingSalestypeAmount = new SalestypeAmount();
        existingSalestypeAmount.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restSalestypeAmountMockMvc.perform(post("/api/salestype-amounts")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(existingSalestypeAmount)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<SalestypeAmount> salestypeAmountList = salestypeAmountRepository.findAll();
        assertThat(salestypeAmountList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllSalestypeAmounts() throws Exception {
        // Initialize the database
        salestypeAmountRepository.saveAndFlush(salestypeAmount);

        // Get all the salestypeAmountList
        restSalestypeAmountMockMvc.perform(get("/api/salestype-amounts?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(salestypeAmount.getId().intValue())))
            .andExpect(jsonPath("$.[*].salesTypeId").value(hasItem(DEFAULT_SALES_TYPE_ID)))
            .andExpect(jsonPath("$.[*].totalAmount").value(hasItem(DEFAULT_TOTAL_AMOUNT.intValue())))
            .andExpect(jsonPath("$.[*].saleDate").value(hasItem(DEFAULT_SALE_DATE.toString())));
    }

    @Test
    @Transactional
    public void getSalestypeAmount() throws Exception {
        // Initialize the database
        salestypeAmountRepository.saveAndFlush(salestypeAmount);

        // Get the salestypeAmount
        restSalestypeAmountMockMvc.perform(get("/api/salestype-amounts/{id}", salestypeAmount.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(salestypeAmount.getId().intValue()))
            .andExpect(jsonPath("$.salesTypeId").value(DEFAULT_SALES_TYPE_ID))
            .andExpect(jsonPath("$.totalAmount").value(DEFAULT_TOTAL_AMOUNT.intValue()))
            .andExpect(jsonPath("$.saleDate").value(DEFAULT_SALE_DATE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingSalestypeAmount() throws Exception {
        // Get the salestypeAmount
        restSalestypeAmountMockMvc.perform(get("/api/salestype-amounts/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateSalestypeAmount() throws Exception {
        // Initialize the database
        salestypeAmountService.save(salestypeAmount);

        int databaseSizeBeforeUpdate = salestypeAmountRepository.findAll().size();

        // Update the salestypeAmount
        SalestypeAmount updatedSalestypeAmount = salestypeAmountRepository.findOne(salestypeAmount.getId());
        updatedSalestypeAmount
                .salesTypeId(UPDATED_SALES_TYPE_ID)
                .totalAmount(UPDATED_TOTAL_AMOUNT)
                .saleDate(UPDATED_SALE_DATE);

        restSalestypeAmountMockMvc.perform(put("/api/salestype-amounts")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedSalestypeAmount)))
            .andExpect(status().isOk());

        // Validate the SalestypeAmount in the database
        List<SalestypeAmount> salestypeAmountList = salestypeAmountRepository.findAll();
        assertThat(salestypeAmountList).hasSize(databaseSizeBeforeUpdate);
        SalestypeAmount testSalestypeAmount = salestypeAmountList.get(salestypeAmountList.size() - 1);
        assertThat(testSalestypeAmount.getSalesTypeId()).isEqualTo(UPDATED_SALES_TYPE_ID);
        assertThat(testSalestypeAmount.getTotalAmount()).isEqualTo(UPDATED_TOTAL_AMOUNT);
        assertThat(testSalestypeAmount.getSaleDate()).isEqualTo(UPDATED_SALE_DATE);
    }

    @Test
    @Transactional
    public void updateNonExistingSalestypeAmount() throws Exception {
        int databaseSizeBeforeUpdate = salestypeAmountRepository.findAll().size();

        // Create the SalestypeAmount

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restSalestypeAmountMockMvc.perform(put("/api/salestype-amounts")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(salestypeAmount)))
            .andExpect(status().isCreated());

        // Validate the SalestypeAmount in the database
        List<SalestypeAmount> salestypeAmountList = salestypeAmountRepository.findAll();
        assertThat(salestypeAmountList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteSalestypeAmount() throws Exception {
        // Initialize the database
        salestypeAmountService.save(salestypeAmount);

        int databaseSizeBeforeDelete = salestypeAmountRepository.findAll().size();

        // Get the salestypeAmount
        restSalestypeAmountMockMvc.perform(delete("/api/salestype-amounts/{id}", salestypeAmount.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<SalestypeAmount> salestypeAmountList = salestypeAmountRepository.findAll();
        assertThat(salestypeAmountList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
