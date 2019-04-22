package com.sales.web.rest;

import com.sales.SalesMaintenanceApp;

import com.sales.domain.SalestypeBill;
import com.sales.repository.SalestypeBillRepository;
import com.sales.service.SalestypeBillService;

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
import java.math.BigDecimal;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the SalestypeBillResource REST controller.
 *
 * @see SalestypeBillResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = SalesMaintenanceApp.class)
public class SalestypeBillResourceIntTest {

    private static final String DEFAULT_BILL_NO = "AAAAAAAAAA";
    private static final String UPDATED_BILL_NO = "BBBBBBBBBB";

    private static final BigDecimal DEFAULT_ALLOTED_AMOUNT = new BigDecimal(1);
    private static final BigDecimal UPDATED_ALLOTED_AMOUNT = new BigDecimal(2);

    @Inject
    private SalestypeBillRepository salestypeBillRepository;

    @Inject
    private SalestypeBillService salestypeBillService;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Inject
    private EntityManager em;

    private MockMvc restSalestypeBillMockMvc;

    private SalestypeBill salestypeBill;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        SalestypeBillResource salestypeBillResource = new SalestypeBillResource();
        ReflectionTestUtils.setField(salestypeBillResource, "salestypeBillService", salestypeBillService);
        this.restSalestypeBillMockMvc = MockMvcBuilders.standaloneSetup(salestypeBillResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static SalestypeBill createEntity(EntityManager em) {
        SalestypeBill salestypeBill = new SalestypeBill()
                .billNo(DEFAULT_BILL_NO)
                .allotedAmount(DEFAULT_ALLOTED_AMOUNT);
        return salestypeBill;
    }

    @Before
    public void initTest() {
        salestypeBill = createEntity(em);
    }

    @Test
    @Transactional
    public void createSalestypeBill() throws Exception {
        int databaseSizeBeforeCreate = salestypeBillRepository.findAll().size();

        // Create the SalestypeBill

        restSalestypeBillMockMvc.perform(post("/api/salestype-bills")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(salestypeBill)))
            .andExpect(status().isCreated());

        // Validate the SalestypeBill in the database
        List<SalestypeBill> salestypeBillList = salestypeBillRepository.findAll();
        assertThat(salestypeBillList).hasSize(databaseSizeBeforeCreate + 1);
        SalestypeBill testSalestypeBill = salestypeBillList.get(salestypeBillList.size() - 1);
        assertThat(testSalestypeBill.getBillNo()).isEqualTo(DEFAULT_BILL_NO);
        assertThat(testSalestypeBill.getAllotedAmount()).isEqualTo(DEFAULT_ALLOTED_AMOUNT);
    }

    @Test
    @Transactional
    public void createSalestypeBillWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = salestypeBillRepository.findAll().size();

        // Create the SalestypeBill with an existing ID
        SalestypeBill existingSalestypeBill = new SalestypeBill();
        existingSalestypeBill.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restSalestypeBillMockMvc.perform(post("/api/salestype-bills")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(existingSalestypeBill)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<SalestypeBill> salestypeBillList = salestypeBillRepository.findAll();
        assertThat(salestypeBillList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllSalestypeBills() throws Exception {
        // Initialize the database
        salestypeBillRepository.saveAndFlush(salestypeBill);

        // Get all the salestypeBillList
        restSalestypeBillMockMvc.perform(get("/api/salestype-bills?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(salestypeBill.getId().intValue())))
            .andExpect(jsonPath("$.[*].billNo").value(hasItem(DEFAULT_BILL_NO.toString())))
            .andExpect(jsonPath("$.[*].allotedAmount").value(hasItem(DEFAULT_ALLOTED_AMOUNT.intValue())));
    }

    @Test
    @Transactional
    public void getSalestypeBill() throws Exception {
        // Initialize the database
        salestypeBillRepository.saveAndFlush(salestypeBill);

        // Get the salestypeBill
        restSalestypeBillMockMvc.perform(get("/api/salestype-bills/{id}", salestypeBill.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(salestypeBill.getId().intValue()))
            .andExpect(jsonPath("$.billNo").value(DEFAULT_BILL_NO.toString()))
            .andExpect(jsonPath("$.allotedAmount").value(DEFAULT_ALLOTED_AMOUNT.intValue()));
    }

    @Test
    @Transactional
    public void getNonExistingSalestypeBill() throws Exception {
        // Get the salestypeBill
        restSalestypeBillMockMvc.perform(get("/api/salestype-bills/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateSalestypeBill() throws Exception {
        // Initialize the database
        salestypeBillService.save(salestypeBill);

        int databaseSizeBeforeUpdate = salestypeBillRepository.findAll().size();

        // Update the salestypeBill
        SalestypeBill updatedSalestypeBill = salestypeBillRepository.findOne(salestypeBill.getId());
        updatedSalestypeBill
                .billNo(UPDATED_BILL_NO)
                .allotedAmount(UPDATED_ALLOTED_AMOUNT);

        restSalestypeBillMockMvc.perform(put("/api/salestype-bills")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedSalestypeBill)))
            .andExpect(status().isOk());

        // Validate the SalestypeBill in the database
        List<SalestypeBill> salestypeBillList = salestypeBillRepository.findAll();
        assertThat(salestypeBillList).hasSize(databaseSizeBeforeUpdate);
        SalestypeBill testSalestypeBill = salestypeBillList.get(salestypeBillList.size() - 1);
        assertThat(testSalestypeBill.getBillNo()).isEqualTo(UPDATED_BILL_NO);
        assertThat(testSalestypeBill.getAllotedAmount()).isEqualTo(UPDATED_ALLOTED_AMOUNT);
    }

    @Test
    @Transactional
    public void updateNonExistingSalestypeBill() throws Exception {
        int databaseSizeBeforeUpdate = salestypeBillRepository.findAll().size();

        // Create the SalestypeBill

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restSalestypeBillMockMvc.perform(put("/api/salestype-bills")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(salestypeBill)))
            .andExpect(status().isCreated());

        // Validate the SalestypeBill in the database
        List<SalestypeBill> salestypeBillList = salestypeBillRepository.findAll();
        assertThat(salestypeBillList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteSalestypeBill() throws Exception {
        // Initialize the database
        salestypeBillService.save(salestypeBill);

        int databaseSizeBeforeDelete = salestypeBillRepository.findAll().size();

        // Get the salestypeBill
        restSalestypeBillMockMvc.perform(delete("/api/salestype-bills/{id}", salestypeBill.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<SalestypeBill> salestypeBillList = salestypeBillRepository.findAll();
        assertThat(salestypeBillList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
