package com.coderise.saas.huawei.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.coderise.saas.huawei.service.ProduceExtendService;
import com.coderise.saas.huawei.web.rest.errors.BadRequestAlertException;
import com.coderise.saas.huawei.web.rest.util.HeaderUtil;
import com.coderise.saas.huawei.service.dto.ProduceExtendDTO;
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
 * REST controller for managing ProduceExtend.
 */
@RestController
@RequestMapping("/api")
public class ProduceExtendResource {

    private final Logger log = LoggerFactory.getLogger(ProduceExtendResource.class);

    private static final String ENTITY_NAME = "produceExtend";

    private final ProduceExtendService produceExtendService;

    public ProduceExtendResource(ProduceExtendService produceExtendService) {
        this.produceExtendService = produceExtendService;
    }

    /**
     * POST  /produce-extends : Create a new produceExtend.
     *
     * @param produceExtendDTO the produceExtendDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new produceExtendDTO, or with status 400 (Bad Request) if the produceExtend has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/produce-extends")
    @Timed
    public ResponseEntity<ProduceExtendDTO> createProduceExtend(@Valid @RequestBody ProduceExtendDTO produceExtendDTO) throws URISyntaxException {
        log.debug("REST request to save ProduceExtend : {}", produceExtendDTO);
        if (produceExtendDTO.getId() != null) {
            throw new BadRequestAlertException("A new produceExtend cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ProduceExtendDTO result = produceExtendService.save(produceExtendDTO);
        return ResponseEntity.created(new URI("/api/produce-extends/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /produce-extends : Updates an existing produceExtend.
     *
     * @param produceExtendDTO the produceExtendDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated produceExtendDTO,
     * or with status 400 (Bad Request) if the produceExtendDTO is not valid,
     * or with status 500 (Internal Server Error) if the produceExtendDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/produce-extends")
    @Timed
    public ResponseEntity<ProduceExtendDTO> updateProduceExtend(@Valid @RequestBody ProduceExtendDTO produceExtendDTO) throws URISyntaxException {
        log.debug("REST request to update ProduceExtend : {}", produceExtendDTO);
        if (produceExtendDTO.getId() == null) {
            return createProduceExtend(produceExtendDTO);
        }
        ProduceExtendDTO result = produceExtendService.save(produceExtendDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, produceExtendDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /produce-extends : get all the produceExtends.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of produceExtends in body
     */
    @GetMapping("/produce-extends")
    @Timed
    public List<ProduceExtendDTO> getAllProduceExtends() {
        log.debug("REST request to get all ProduceExtends");
        return produceExtendService.findAll();
        }

    /**
     * GET  /produce-extends/:id : get the "id" produceExtend.
     *
     * @param id the id of the produceExtendDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the produceExtendDTO, or with status 404 (Not Found)
     */
    @GetMapping("/produce-extends/{id}")
    @Timed
    public ResponseEntity<ProduceExtendDTO> getProduceExtend(@PathVariable Long id) {
        log.debug("REST request to get ProduceExtend : {}", id);
        ProduceExtendDTO produceExtendDTO = produceExtendService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(produceExtendDTO));
    }

    /**
     * DELETE  /produce-extends/:id : delete the "id" produceExtend.
     *
     * @param id the id of the produceExtendDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/produce-extends/{id}")
    @Timed
    public ResponseEntity<Void> deleteProduceExtend(@PathVariable Long id) {
        log.debug("REST request to delete ProduceExtend : {}", id);
        produceExtendService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
