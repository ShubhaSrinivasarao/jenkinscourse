package com.sales.web.rest;

import com.sales.SalesMaintenanceApp;

import com.sales.domain.SalestypeBillDetl;
import com.sales.repository.SalestypeBillDetlRepository;
import com.sales.service.SalestypeBillDetlService;

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
 * Test class for the SalestypeBillDetlResource REST controller.
 *
 * @see SalestypeBillDetlResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = SalesMaintenanceApp.class)
public class SalestypeBillDetlResourceIntTest {

    private static final String DEFAULT_RETURN_DESC = "AAAAAAAAAA";
    private static final String UPDATED_RETURN_DESC = "BBBBBBBBBB";

    private static final BigDecimal DEFAULT_RETURN_AMOUNT = new BigDecimal(1);
    private static final BigDecimal UPDATED_RETURN_AMOUNT = new BigDecimal(2);

    @Inject
    private SalestypeBillDetlRepository salestypeBillDetlRepository;

    @Inject
    private SalestypeBillDetlService salestypeBillDetlService;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Inject
    private EntityManager em;

    private MockMvc restSalestypeBillDetlMockMvc;

    private SalestypeBillDetl salestypeBillDetl;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        SalestypeBillDetlResource salestypeBillDetlResource = new SalestypeBillDetlResource();
        ReflectionTestUtils.setField(salestypeBillDetlResource, "salestypeBillDetlService", salestypeBillDetlService);
        this.restSalestypeBillDetlMockMvc = MockMvcBuilders.standaloneSetup(salestypeBillDetlResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static SalestypeBillDetl createEntity(EntityManager em) {
        SalestypeBillDetl salestypeBillDetl = new SalestypeBillDetl()
                .returnDesc(DEFAULT_RETURN_DESC)
                .returnAmount(DEFAULT_RETURN_AMOUNT);
        return salestypeBillDetl;
    }

    @Before
    public void initTest() {
        salestypeBillDetl = createEntity(em);
    }

    @Test
    @Transactional
    public void createSalestypeBillDetl() throws Exception {
        int databaseSizeBeforeCreate = salestypeBillDetlRepository.findAll().size();

        // Create the SalestypeBillDetl

        restSalestypeBillDetlMockMvc.perform(post("/api/salestype-bill-detls")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(salestypeBillDetl)))
            .andExpect(status().isCreated());

        // Validate the SalestypeBillDetl in the database
        List<SalestypeBillDetl> salestypeBillDetlList = salestypeBillDetlRepository.findAll();
        assertThat(salestypeBillDetlList).hasSize(databaseSizeBeforeCreate + 1);
        SalestypeBillDetl testSalestypeBillDetl = salestypeBillDetlList.get(salestypeBillDetlList.size() - 1);
        assertThat(testSalestypeBillDetl.getReturnDesc()).isEqualTo(DEFAULT_RETURN_DESC);
        assertThat(testSalestypeBillDetl.getReturnAmount()).isEqualTo(DEFAULT_RETURN_AMOUNT);
    }

    @Test
    @Transactional
    public void createSalestypeBillDetlWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = salestypeBillDetlRepository.findAll().size();

        // Create the SalestypeBillDetl with an existing ID
        SalestypeBillDetl existingSalestypeBillDetl = new SalestypeBillDetl();
        existingSalestypeBillDetl.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restSalestypeBillDetlMockMvc.perform(post("/api/salestype-bill-detls")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(existingSalestypeBillDetl)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<SalestypeBillDetl> salestypeBillDetlList = salestypeBillDetlRepository.findAll();
        assertThat(salestypeBillDetlList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllSalestypeBillDetls() throws Exception {
        // Initialize the database
        salestypeBillDetlRepository.saveAndFlush(salestypeBillDetl);

        // Get all the salestypeBillDetlList
        restSalestypeBillDetlMockMvc.perform(get("/api/salestype-bill-detls?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(salestypeBillDetl.getId().intValue())))
            .andExpect(jsonPath("$.[*].returnDesc").value(hasItem(DEFAULT_RETURN_DESC.toString())))
            .andExpect(jsonPath("$.[*].returnAmount").value(hasItem(DEFAULT_RETURN_AMOUNT.intValue())));
    }

    @Test
    @Transactional
    public void getSalestypeBillDetl() throws Exception {
        // Initialize the database
        salestypeBillDetlRepository.saveAndFlush(salestypeBillDetl);

        // Get the salestypeBillDetl
        restSalestypeBillDetlMockMvc.perform(get("/api/salestype-bill-detls/{id}", salestypeBillDetl.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(salestypeBillDetl.getId().intValue()))
            .andExpect(jsonPath("$.returnDesc").value(DEFAULT_RETURN_DESC.toString()))
            .andExpect(jsonPath("$.returnAmount").value(DEFAULT_RETURN_AMOUNT.intValue()));
    }

    @Test
    @Transactional
    public void getNonExistingSalestypeBillDetl() throws Exception {
        // Get the salestypeBillDetl
        restSalestypeBillDetlMockMvc.perform(get("/api/salestype-bill-detls/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateSalestypeBillDetl() throws Exception {
        // Initialize the database
        salestypeBillDetlService.save(salestypeBillDetl);

        int databaseSizeBeforeUpdate = salestypeBillDetlRepository.findAll().size();

        // Update the salestypeBillDetl
        SalestypeBillDetl updatedSalestypeBillDetl = salestypeBillDetlRepository.findOne(salestypeBillDetl.getId());
        updatedSalestypeBillDetl
                .returnDesc(UPDATED_RETURN_DESC)
                .returnAmount(UPDATED_RETURN_AMOUNT);

        restSalestypeBillDetlMockMvc.perform(put("/api/salestype-bill-detls")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedSalestypeBillDetl)))
            .andExpect(status().isOk());

        // Validate the SalestypeBillDetl in the database
        List<SalestypeBillDetl> salestypeBillDetlList = salestypeBillDetlRepository.findAll();
        assertThat(salestypeBillDetlList).hasSize(databaseSizeBeforeUpdate);
        SalestypeBillDetl testSalestypeBillDetl = salestypeBillDetlList.get(salestypeBillDetlList.size() - 1);
        assertThat(testSalestypeBillDetl.getReturnDesc()).isEqualTo(UPDATED_RETURN_DESC);
        assertThat(testSalestypeBillDetl.getReturnAmount()).isEqualTo(UPDATED_RETURN_AMOUNT);
    }

    @Test
    @Transactional
    public void updateNonExistingSalestypeBillDetl() throws Exception {
        int databaseSizeBeforeUpdate = salestypeBillDetlRepository.findAll().size();

        // Create the SalestypeBillDetl

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restSalestypeBillDetlMockMvc.perform(put("/api/salestype-bill-detls")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(salestypeBillDetl)))
            .andExpect(status().isCreated());

        // Validate the SalestypeBillDetl in the database
        List<SalestypeBillDetl> salestypeBillDetlList = salestypeBillDetlRepository.findAll();
        assertThat(salestypeBillDetlList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteSalestypeBillDetl() throws Exception {
        // Initialize the database
        salestypeBillDetlService.save(salestypeBillDetl);

        int databaseSizeBeforeDelete = salestypeBillDetlRepository.findAll().size();

        // Get the salestypeBillDetl
        restSalestypeBillDetlMockMvc.perform(delete("/api/salestype-bill-detls/{id}", salestypeBillDetl.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<SalestypeBillDetl> salestypeBillDetlList = salestypeBillDetlRepository.findAll();
        assertThat(salestypeBillDetlList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
