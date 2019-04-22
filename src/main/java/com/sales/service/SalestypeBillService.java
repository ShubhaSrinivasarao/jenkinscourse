package com.sales.service;

import com.sales.domain.SalestypeBill;
import com.sales.repository.SalestypeBillRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.List;

/**
 * Service Implementation for managing SalestypeBill.
 */
@Service
@Transactional
public class SalestypeBillService {

    private final Logger log = LoggerFactory.getLogger(SalestypeBillService.class);
    
    @Inject
    private SalestypeBillRepository salestypeBillRepository;

    /**
     * Save a salestypeBill.
     *
     * @param salestypeBill the entity to save
     * @return the persisted entity
     */
    public SalestypeBill save(SalestypeBill salestypeBill) {
        log.debug("Request to save SalestypeBill : {}", salestypeBill);
        SalestypeBill result = salestypeBillRepository.save(salestypeBill);
        return result;
    }

    /**
     *  Get all the salestypeBills.
     *  
     *  @return the list of entities
     */
    @Transactional(readOnly = true) 
    public List<SalestypeBill> findAll() {
        log.debug("Request to get all SalestypeBills");
        List<SalestypeBill> result = salestypeBillRepository.findAll();

        return result;
    }

    /**
     *  Get one salestypeBill by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true) 
    public SalestypeBill findOne(Long id) {
        log.debug("Request to get SalestypeBill : {}", id);
        SalestypeBill salestypeBill = salestypeBillRepository.findOne(id);
        return salestypeBill;
    }

    /**
     *  Delete the  salestypeBill by id.
     *
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete SalestypeBill : {}", id);
        salestypeBillRepository.delete(id);
    }
}
