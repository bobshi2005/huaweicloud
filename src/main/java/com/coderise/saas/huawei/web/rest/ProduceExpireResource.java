package com.coderise.saas.huawei.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.coderise.saas.huawei.service.ProduceExpireService;
import com.coderise.saas.huawei.web.rest.errors.BadRequestAlertException;
import com.coderise.saas.huawei.web.rest.util.HeaderUtil;
import com.coderise.saas.huawei.service.dto.ProduceExpireDTO;
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
 * REST controller for managing ProduceExpire.
 */
@RestController
@RequestMapping("/api")
public class ProduceExpireResource {

    private final Logger log = LoggerFactory.getLogger(ProduceExpireResource.class);

    private static final String ENTITY_NAME = "produceExpire";

    private final ProduceExpireService produceExpireService;

    public ProduceExpireResource(ProduceExpireService produceExpireService) {
        this.produceExpireService = produceExpireService;
    }

    /**
     * POST  /produce-expires : Create a new produceExpire.
     *
     * @param produceExpireDTO the produceExpireDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new produceExpireDTO, or with status 400 (Bad Request) if the produceExpire has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/produce-expires")
    @Timed
    public ResponseEntity<ProduceExpireDTO> createProduceExpire(@RequestBody ProduceExpireDTO produceExpireDTO) throws URISyntaxException {
        log.debug("REST request to save ProduceExpire : {}", produceExpireDTO);
        if (produceExpireDTO.getId() != null) {
            throw new BadRequestAlertException("A new produceExpire cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ProduceExpireDTO result = produceExpireService.save(produceExpireDTO);
        return ResponseEntity.created(new URI("/api/produce-expires/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /produce-expires : Updates an existing produceExpire.
     *
     * @param produceExpireDTO the produceExpireDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated produceExpireDTO,
     * or with status 400 (Bad Request) if the produceExpireDTO is not valid,
     * or with status 500 (Internal Server Error) if the produceExpireDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/produce-expires")
    @Timed
    public ResponseEntity<ProduceExpireDTO> updateProduceExpire(@RequestBody ProduceExpireDTO produceExpireDTO) throws URISyntaxException {
        log.debug("REST request to update ProduceExpire : {}", produceExpireDTO);
        if (produceExpireDTO.getId() == null) {
            return createProduceExpire(produceExpireDTO);
        }
        ProduceExpireDTO result = produceExpireService.save(produceExpireDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, produceExpireDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /produce-expires : get all the produceExpires.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of produceExpires in body
     */
    @GetMapping("/produce-expires")
    @Timed
    public List<ProduceExpireDTO> getAllProduceExpires() {
        log.debug("REST request to get all ProduceExpires");
        return produceExpireService.findAll();
        }

    /**
     * GET  /produce-expires/:id : get the "id" produceExpire.
     *
     * @param id the id of the produceExpireDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the produceExpireDTO, or with status 404 (Not Found)
     */
    @GetMapping("/produce-expires/{id}")
    @Timed
    public ResponseEntity<ProduceExpireDTO> getProduceExpire(@PathVariable Long id) {
        log.debug("REST request to get ProduceExpire : {}", id);
        ProduceExpireDTO produceExpireDTO = produceExpireService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(produceExpireDTO));
    }

    /**
     * DELETE  /produce-expires/:id : delete the "id" produceExpire.
     *
     * @param id the id of the produceExpireDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/produce-expires/{id}")
    @Timed
    public ResponseEntity<Void> deleteProduceExpire(@PathVariable Long id) {
        log.debug("REST request to delete ProduceExpire : {}", id);
        produceExpireService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
