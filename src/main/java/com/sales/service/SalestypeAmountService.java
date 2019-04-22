package com.sales.service;

import com.sales.domain.SalestypeAmount;
import com.sales.repository.SalestypeAmountRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.List;

/**
 * Service Implementation for managing SalestypeAmount.
 */
@Service
@Transactional
public class SalestypeAmountService {

    private final Logger log = LoggerFactory.getLogger(SalestypeAmountService.class);
    
    @Inject
    private SalestypeAmountRepository salestypeAmountRepository;

    /**
     * Save a salestypeAmount.
     *
     * @param salestypeAmount the entity to save
     * @return the persisted entity
     */
    public SalestypeAmount save(SalestypeAmount salestypeAmount) {
        log.debug("Request to save SalestypeAmount : {}", salestypeAmount);
        SalestypeAmount result = salestypeAmountRepository.save(salestypeAmount);
        return result;
    }

    /**
     *  Get all the salestypeAmounts.
     *  
     *  @return the list of entities
     */
    @Transactional(readOnly = true) 
    public List<SalestypeAmount> findAll() {
        log.debug("Request to get all SalestypeAmounts");
        List<SalestypeAmount> result = salestypeAmountRepository.findAll();

        return result;
    }

    /**
     *  Get one salestypeAmount by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true) 
    public SalestypeAmount findOne(Long id) {
        log.debug("Request to get SalestypeAmount : {}", id);
        SalestypeAmount salestypeAmount = salestypeAmountRepository.findOne(id);
        return salestypeAmount;
    }

    /**
     *  Delete the  salestypeAmount by id.
     *
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete SalestypeAmount : {}", id);
        salestypeAmountRepository.delete(id);
    }
}
