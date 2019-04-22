package com.sales.service;

import com.sales.domain.SalestypeBillDetl;
import com.sales.repository.SalestypeBillDetlRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.List;

/**
 * Service Implementation for managing SalestypeBillDetl.
 */
@Service
@Transactional
public class SalestypeBillDetlService {

    private final Logger log = LoggerFactory.getLogger(SalestypeBillDetlService.class);
    
    @Inject
    private SalestypeBillDetlRepository salestypeBillDetlRepository;

    /**
     * Save a salestypeBillDetl.
     *
     * @param salestypeBillDetl the entity to save
     * @return the persisted entity
     */
    public SalestypeBillDetl save(SalestypeBillDetl salestypeBillDetl) {
        log.debug("Request to save SalestypeBillDetl : {}", salestypeBillDetl);
        SalestypeBillDetl result = salestypeBillDetlRepository.save(salestypeBillDetl);
        return result;
    }

    /**
     *  Get all the salestypeBillDetls.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Transactional(readOnly = true) 
    public Page<SalestypeBillDetl> findAll(Pageable pageable) {
        log.debug("Request to get all SalestypeBillDetls");
        Page<SalestypeBillDetl> result = salestypeBillDetlRepository.findAll(pageable);
        return result;
    }

    /**
     *  Get one salestypeBillDetl by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true) 
    public SalestypeBillDetl findOne(Long id) {
        log.debug("Request to get SalestypeBillDetl : {}", id);
        SalestypeBillDetl salestypeBillDetl = salestypeBillDetlRepository.findOne(id);
        return salestypeBillDetl;
    }

    /**
     *  Delete the  salestypeBillDetl by id.
     *
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete SalestypeBillDetl : {}", id);
        salestypeBillDetlRepository.delete(id);
    }
}
