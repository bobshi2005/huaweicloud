package com.coderise.saas.huawei.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.coderise.saas.huawei.service.InstanceService;
import com.coderise.saas.huawei.web.rest.errors.BadRequestAlertException;
import com.coderise.saas.huawei.web.rest.util.HeaderUtil;
import com.coderise.saas.huawei.service.dto.InstanceDTO;
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
 * REST controller for managing Instance.
 */
@RestController
@RequestMapping("/api")
public class InstanceResource {

    private final Logger log = LoggerFactory.getLogger(InstanceResource.class);

    private static final String ENTITY_NAME = "instance";

    private final InstanceService instanceService;

    public InstanceResource(InstanceService instanceService) {
        this.instanceService = instanceService;
    }

    /**
     * POST  /instances : Create a new instance.
     *
     * @param instanceDTO the instanceDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new instanceDTO, or with status 400 (Bad Request) if the instance has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/instances")
    @Timed
    public ResponseEntity<InstanceDTO> createInstance(@Valid @RequestBody InstanceDTO instanceDTO) throws URISyntaxException {
        log.debug("REST request to save Instance : {}", instanceDTO);
        if (instanceDTO.getId() != null) {
            throw new BadRequestAlertException("A new instance cannot already have an ID", ENTITY_NAME, "idexists");
        }
        InstanceDTO result = instanceService.save(instanceDTO);
        return ResponseEntity.created(new URI("/api/instances/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /instances : Updates an existing instance.
     *
     * @param instanceDTO the instanceDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated instanceDTO,
     * or with status 400 (Bad Request) if the instanceDTO is not valid,
     * or with status 500 (Internal Server Error) if the instanceDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/instances")
    @Timed
    public ResponseEntity<InstanceDTO> updateInstance(@Valid @RequestBody InstanceDTO instanceDTO) throws URISyntaxException {
        log.debug("REST request to update Instance : {}", instanceDTO);
        if (instanceDTO.getId() == null) {
            return createInstance(instanceDTO);
        }
        InstanceDTO result = instanceService.save(instanceDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, instanceDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /instances : get all the instances.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of instances in body
     */
    @GetMapping("/instances")
    @Timed
    public List<InstanceDTO> getAllInstances() {
        log.debug("REST request to get all Instances");
        return instanceService.findAll();
        }

    /**
     * GET  /instances/:id : get the "id" instance.
     *
     * @param id the id of the instanceDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the instanceDTO, or with status 404 (Not Found)
     */
    @GetMapping("/instances/{id}")
    @Timed
    public ResponseEntity<InstanceDTO> getInstance(@PathVariable Long id) {
        log.debug("REST request to get Instance : {}", id);
        InstanceDTO instanceDTO = instanceService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(instanceDTO));
    }

    /**
     * DELETE  /instances/:id : delete the "id" instance.
     *
     * @param id the id of the instanceDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/instances/{id}")
    @Timed
    public ResponseEntity<Void> deleteInstance(@PathVariable Long id) {
        log.debug("REST request to delete Instance : {}", id);
        instanceService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
