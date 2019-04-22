package com.sales.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.sales.domain.SalestypeBillDetl;
import com.sales.service.SalestypeBillDetlService;
import com.sales.web.rest.util.HeaderUtil;
import com.sales.web.rest.util.PaginationUtil;

import io.swagger.annotations.ApiParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
 * REST controller for managing SalestypeBillDetl.
 */
@RestController
@RequestMapping("/api")
public class SalestypeBillDetlResource {

    private final Logger log = LoggerFactory.getLogger(SalestypeBillDetlResource.class);
        
    @Inject
    private SalestypeBillDetlService salestypeBillDetlService;

    /**
     * POST  /salestype-bill-detls : Create a new salestypeBillDetl.
     *
     * @param salestypeBillDetl the salestypeBillDetl to create
     * @return the ResponseEntity with status 201 (Created) and with body the new salestypeBillDetl, or with status 400 (Bad Request) if the salestypeBillDetl has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/salestype-bill-detls")
    @Timed
    public ResponseEntity<SalestypeBillDetl> createSalestypeBillDetl(@RequestBody SalestypeBillDetl salestypeBillDetl) throws URISyntaxException {
        log.debug("REST request to save SalestypeBillDetl : {}", salestypeBillDetl);
        if (salestypeBillDetl.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("salestypeBillDetl", "idexists", "A new salestypeBillDetl cannot already have an ID")).body(null);
        }
        SalestypeBillDetl result = salestypeBillDetlService.save(salestypeBillDetl);
        return ResponseEntity.created(new URI("/api/salestype-bill-detls/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("salestypeBillDetl", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /salestype-bill-detls : Updates an existing salestypeBillDetl.
     *
     * @param salestypeBillDetl the salestypeBillDetl to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated salestypeBillDetl,
     * or with status 400 (Bad Request) if the salestypeBillDetl is not valid,
     * or with status 500 (Internal Server Error) if the salestypeBillDetl couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/salestype-bill-detls")
    @Timed
    public ResponseEntity<SalestypeBillDetl> updateSalestypeBillDetl(@RequestBody SalestypeBillDetl salestypeBillDetl) throws URISyntaxException {
        log.debug("REST request to update SalestypeBillDetl : {}", salestypeBillDetl);
        if (salestypeBillDetl.getId() == null) {
            return createSalestypeBillDetl(salestypeBillDetl);
        }
        SalestypeBillDetl result = salestypeBillDetlService.save(salestypeBillDetl);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("salestypeBillDetl", salestypeBillDetl.getId().toString()))
            .body(result);
    }

    /**
     * GET  /salestype-bill-detls : get all the salestypeBillDetls.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of salestypeBillDetls in body
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @GetMapping("/salestype-bill-detls")
    @Timed
    public ResponseEntity<List<SalestypeBillDetl>> getAllSalestypeBillDetls(@ApiParam Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of SalestypeBillDetls");
        Page<SalestypeBillDetl> page = salestypeBillDetlService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/salestype-bill-detls");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /salestype-bill-detls/:id : get the "id" salestypeBillDetl.
     *
     * @param id the id of the salestypeBillDetl to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the salestypeBillDetl, or with status 404 (Not Found)
     */
    @GetMapping("/salestype-bill-detls/{id}")
    @Timed
    public ResponseEntity<SalestypeBillDetl> getSalestypeBillDetl(@PathVariable Long id) {
        log.debug("REST request to get SalestypeBillDetl : {}", id);
        SalestypeBillDetl salestypeBillDetl = salestypeBillDetlService.findOne(id);
        return Optional.ofNullable(salestypeBillDetl)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /salestype-bill-detls/:id : delete the "id" salestypeBillDetl.
     *
     * @param id the id of the salestypeBillDetl to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/salestype-bill-detls/{id}")
    @Timed
    public ResponseEntity<Void> deleteSalestypeBillDetl(@PathVariable Long id) {
        log.debug("REST request to delete SalestypeBillDetl : {}", id);
        salestypeBillDetlService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("salestypeBillDetl", id.toString())).build();
    }

}
