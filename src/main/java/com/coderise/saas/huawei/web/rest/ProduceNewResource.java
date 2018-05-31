package com.coderise.saas.huawei.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.coderise.saas.huawei.service.ProduceNewService;
import com.coderise.saas.huawei.web.rest.errors.BadRequestAlertException;
import com.coderise.saas.huawei.web.rest.util.HeaderUtil;
import com.coderise.saas.huawei.service.dto.ProduceNewDTO;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing ProduceNew.
 */
@RestController
@RequestMapping("/api")
public class ProduceNewResource {

    private final Logger log = LoggerFactory.getLogger(ProduceNewResource.class);

    private static final String ENTITY_NAME = "produceNew";

    private final ProduceNewService produceNewService;

    public ProduceNewResource(ProduceNewService produceNewService) {
        this.produceNewService = produceNewService;
    }

    /**
     * POST  /produce-news : Create a new produceNew.
     *
     * @param produceNewDTO the produceNewDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new produceNewDTO, or with status 400 (Bad Request) if the produceNew has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/produce-news")
    @Timed
    public ResponseEntity<ProduceNewDTO> createProduceNew(@Valid @RequestBody ProduceNewDTO produceNewDTO) throws URISyntaxException {
        log.debug("REST request to save ProduceNew : {}", produceNewDTO);
        if (produceNewDTO.getId() != null) {
            throw new BadRequestAlertException("A new produceNew cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ProduceNewDTO result = produceNewService.save(produceNewDTO);
        return ResponseEntity.created(new URI("/api/produce-news/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /produce-news : Updates an existing produceNew.
     *
     * @param produceNewDTO the produceNewDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated produceNewDTO,
     * or with status 400 (Bad Request) if the produceNewDTO is not valid,
     * or with status 500 (Internal Server Error) if the produceNewDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/produce-news")
    @Timed
    public ResponseEntity<ProduceNewDTO> updateProduceNew(@Valid @RequestBody ProduceNewDTO produceNewDTO) throws URISyntaxException {
        log.debug("REST request to update ProduceNew : {}", produceNewDTO);
        if (produceNewDTO.getId() == null) {
            return createProduceNew(produceNewDTO);
        }
        ProduceNewDTO result = produceNewService.save(produceNewDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, produceNewDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /produce-news : get all the produceNews.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of produceNews in body
     */
    @GetMapping("/produce-news")
    @Timed
    public List<ProduceNewDTO> getAllProduceNews() {
        log.debug("REST request to get all ProduceNews");
        return produceNewService.findAll();
        }

    /**
     * GET  /produce-news/:id : get the "id" produceNew.
     *
     * @param id the id of the produceNewDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the produceNewDTO, or with status 404 (Not Found)
     */
    @GetMapping("/produce-news/{id}")
    @Timed
    public ResponseEntity<ProduceNewDTO> getProduceNew(@PathVariable Long id) {
        log.debug("REST request to get ProduceNew : {}", id);
        ProduceNewDTO produceNewDTO = produceNewService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(produceNewDTO));
    }

    /**
     * DELETE  /produce-news/:id : delete the "id" produceNew.
     *
     * @param id the id of the produceNewDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/produce-news/{id}")
    @Timed
    public ResponseEntity<Void> deleteProduceNew(@PathVariable Long id) {
        log.debug("REST request to delete ProduceNew : {}", id);
        produceNewService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
