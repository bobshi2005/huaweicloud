package com.coderise.saas.huawei.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.coderise.saas.huawei.service.ProduceReleaseService;
import com.coderise.saas.huawei.web.rest.errors.BadRequestAlertException;
import com.coderise.saas.huawei.web.rest.util.HeaderUtil;
import com.coderise.saas.huawei.service.dto.ProduceReleaseDTO;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing ProduceRelease.
 */
@RestController
@RequestMapping("/api")
public class ProduceReleaseResource {

    private final Logger log = LoggerFactory.getLogger(ProduceReleaseResource.class);

    private static final String ENTITY_NAME = "produceRelease";

    private final ProduceReleaseService produceReleaseService;

    public ProduceReleaseResource(ProduceReleaseService produceReleaseService) {
        this.produceReleaseService = produceReleaseService;
    }

    /**
     * POST  /produce-releases : Create a new produceRelease.
     *
     * @param produceReleaseDTO the produceReleaseDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new produceReleaseDTO, or with status 400 (Bad Request) if the produceRelease has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/produce-releases")
    @Timed
    public ResponseEntity<ProduceReleaseDTO> createProduceRelease(@RequestBody ProduceReleaseDTO produceReleaseDTO) throws URISyntaxException {
        log.debug("REST request to save ProduceRelease : {}", produceReleaseDTO);
        if (produceReleaseDTO.getId() != null) {
            throw new BadRequestAlertException("A new produceRelease cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ProduceReleaseDTO result = produceReleaseService.save(produceReleaseDTO);
        return ResponseEntity.created(new URI("/api/produce-releases/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /produce-releases : Updates an existing produceRelease.
     *
     * @param produceReleaseDTO the produceReleaseDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated produceReleaseDTO,
     * or with status 400 (Bad Request) if the produceReleaseDTO is not valid,
     * or with status 500 (Internal Server Error) if the produceReleaseDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/produce-releases")
    @Timed
    public ResponseEntity<ProduceReleaseDTO> updateProduceRelease(@RequestBody ProduceReleaseDTO produceReleaseDTO) throws URISyntaxException {
        log.debug("REST request to update ProduceRelease : {}", produceReleaseDTO);
        if (produceReleaseDTO.getId() == null) {
            return createProduceRelease(produceReleaseDTO);
        }
        ProduceReleaseDTO result = produceReleaseService.save(produceReleaseDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, produceReleaseDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /produce-releases : get all the produceReleases.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of produceReleases in body
     */
    @GetMapping("/produce-releases")
    @Timed
    public List<ProduceReleaseDTO> getAllProduceReleases() {
        log.debug("REST request to get all ProduceReleases");
        return produceReleaseService.findAll();
        }

    /**
     * GET  /produce-releases/:id : get the "id" produceRelease.
     *
     * @param id the id of the produceReleaseDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the produceReleaseDTO, or with status 404 (Not Found)
     */
    @GetMapping("/produce-releases/{id}")
    @Timed
    public ResponseEntity<ProduceReleaseDTO> getProduceRelease(@PathVariable Long id) {
        log.debug("REST request to get ProduceRelease : {}", id);
        ProduceReleaseDTO produceReleaseDTO = produceReleaseService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(produceReleaseDTO));
    }

    /**
     * DELETE  /produce-releases/:id : delete the "id" produceRelease.
     *
     * @param id the id of the produceReleaseDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/produce-releases/{id}")
    @Timed
    public ResponseEntity<Void> deleteProduceRelease(@PathVariable Long id) {
        log.debug("REST request to delete ProduceRelease : {}", id);
        produceReleaseService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
