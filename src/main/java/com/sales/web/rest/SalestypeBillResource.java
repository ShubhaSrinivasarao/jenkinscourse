package com.sales.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.sales.domain.SalestypeBill;
import com.sales.service.SalestypeBillService;
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
 * REST controller for managing SalestypeBill.
 */
@RestController
@RequestMapping("/api")
public class SalestypeBillResource {

    private final Logger log = LoggerFactory.getLogger(SalestypeBillResource.class);
        
    @Inject
    private SalestypeBillService salestypeBillService;

    /**
     * POST  /salestype-bills : Create a new salestypeBill.
     *
     * @param salestypeBill the salestypeBill to create
     * @return the ResponseEntity with status 201 (Created) and with body the new salestypeBill, or with status 400 (Bad Request) if the salestypeBill has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/salestype-bills")
    @Timed
    public ResponseEntity<SalestypeBill> createSalestypeBill(@RequestBody SalestypeBill salestypeBill) throws URISyntaxException {
        log.debug("REST request to save SalestypeBill : {}", salestypeBill);
        if (salestypeBill.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("salestypeBill", "idexists", "A new salestypeBill cannot already have an ID")).body(null);
        }
        SalestypeBill result = salestypeBillService.save(salestypeBill);
        return ResponseEntity.created(new URI("/api/salestype-bills/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("salestypeBill", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /salestype-bills : Updates an existing salestypeBill.
     *
     * @param salestypeBill the salestypeBill to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated salestypeBill,
     * or with status 400 (Bad Request) if the salestypeBill is not valid,
     * or with status 500 (Internal Server Error) if the salestypeBill couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/salestype-bills")
    @Timed
    public ResponseEntity<SalestypeBill> updateSalestypeBill(@RequestBody SalestypeBill salestypeBill) throws URISyntaxException {
        log.debug("REST request to update SalestypeBill : {}", salestypeBill);
        if (salestypeBill.getId() == null) {
            return createSalestypeBill(salestypeBill);
        }
        SalestypeBill result = salestypeBillService.save(salestypeBill);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("salestypeBill", salestypeBill.getId().toString()))
            .body(result);
    }

    /**
     * GET  /salestype-bills : get all the salestypeBills.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of salestypeBills in body
     */
    @GetMapping("/salestype-bills")
    @Timed
    public List<SalestypeBill> getAllSalestypeBills() {
        log.debug("REST request to get all SalestypeBills");
        return salestypeBillService.findAll();
    }

    /**
     * GET  /salestype-bills/:id : get the "id" salestypeBill.
     *
     * @param id the id of the salestypeBill to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the salestypeBill, or with status 404 (Not Found)
     */
    @GetMapping("/salestype-bills/{id}")
    @Timed
    public ResponseEntity<SalestypeBill> getSalestypeBill(@PathVariable Long id) {
        log.debug("REST request to get SalestypeBill : {}", id);
        SalestypeBill salestypeBill = salestypeBillService.findOne(id);
        return Optional.ofNullable(salestypeBill)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /salestype-bills/:id : delete the "id" salestypeBill.
     *
     * @param id the id of the salestypeBill to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/salestype-bills/{id}")
    @Timed
    public ResponseEntity<Void> deleteSalestypeBill(@PathVariable Long id) {
        log.debug("REST request to delete SalestypeBill : {}", id);
        salestypeBillService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("salestypeBill", id.toString())).build();
    }

}
