package com.sales.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.sales.domain.SalestypeAmount;
import com.sales.service.SalestypeAmountService;
import com.sales.web.rest.util.HeaderUtil;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing SalestypeAmount.
 */
@RestController
@RequestMapping("/api")
public class SalestypeAmountResource {

    private final Logger log = LoggerFactory.getLogger(SalestypeAmountResource.class);
        
    @Inject
    private SalestypeAmountService salestypeAmountService;

    /**
     * POST  /salestype-amounts : Create a new salestypeAmount.
     *
     * @param salestypeAmount the salestypeAmount to create
     * @return the ResponseEntity with status 201 (Created) and with body the new salestypeAmount, or with status 400 (Bad Request) if the salestypeAmount has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/salestype-amounts")
    @Timed
    public ResponseEntity<SalestypeAmount> createSalestypeAmount(@RequestBody SalestypeAmount salestypeAmount) throws URISyntaxException {
        log.debug("REST request to save SalestypeAmount : {}", salestypeAmount);
        if (salestypeAmount.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("salestypeAmount", "idexists", "A new salestypeAmount cannot already have an ID")).body(null);
        }
        SalestypeAmount result = salestypeAmountService.save(salestypeAmount);
        return ResponseEntity.created(new URI("/api/salestype-amounts/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("salestypeAmount", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /salestype-amounts : Updates an existing salestypeAmount.
     *
     * @param salestypeAmount the salestypeAmount to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated salestypeAmount,
     * or with status 400 (Bad Request) if the salestypeAmount is not valid,
     * or with status 500 (Internal Server Error) if the salestypeAmount couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/salestype-amounts")
    @Timed
    public ResponseEntity<SalestypeAmount> updateSalestypeAmount(@RequestBody SalestypeAmount salestypeAmount) throws URISyntaxException {
        log.debug("REST request to update SalestypeAmount : {}", salestypeAmount);
        if (salestypeAmount.getId() == null) {
            return createSalestypeAmount(salestypeAmount);
        }
        SalestypeAmount result = salestypeAmountService.save(salestypeAmount);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("salestypeAmount", salestypeAmount.getId().toString()))
            .body(result);
    }

    /**
     * GET  /salestype-amounts : get all the salestypeAmounts.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of salestypeAmounts in body
     */
    @GetMapping("/salestype-amounts")
    @Timed
    public List<SalestypeAmount> getAllSalestypeAmounts() {
        log.debug("REST request to get all SalestypeAmounts");
        return salestypeAmountService.findAll();
    }

    /**
     * GET  /salestype-amounts/:id : get the "id" salestypeAmount.
     *
     * @param id the id of the salestypeAmount to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the salestypeAmount, or with status 404 (Not Found)
     */
    @GetMapping("/salestype-amounts/{id}")
    @Timed
    public ResponseEntity<SalestypeAmount> getSalestypeAmount(@PathVariable Long id) {
        log.debug("REST request to get SalestypeAmount : {}", id);
        SalestypeAmount salestypeAmount = salestypeAmountService.findOne(id);
        return Optional.ofNullable(salestypeAmount)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /salestype-amounts/:id : delete the "id" salestypeAmount.
     *
     * @param id the id of the salestypeAmount to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/salestype-amounts/{id}")
    @Timed
    public ResponseEntity<Void> deleteSalestypeAmount(@PathVariable Long id) {
        log.debug("REST request to delete SalestypeAmount : {}", id);
        salestypeAmountService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("salestypeAmount", id.toString())).build();
    }

}
