package br.com.controlefinanceiro.controller;

import br.com.controlefinanceiro.model.LancamentoDespesa;
import br.com.controlefinanceiro.model.dto.DespesaDTO;
import br.com.controlefinanceiro.model.dto.LancamentoDespesaDTO;
import br.com.controlefinanceiro.services.DespesaService;
import br.com.controlefinanceiro.services.LancamentoDespesaService;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import jakarta.websocket.server.PathParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping(value = "/lancamento/despesa")
public class LancamentoDespesaController {

    @Autowired
    private LancamentoDespesaService service;

    @Autowired
    private DespesaService despesaService;

    @GetMapping
    public ResponseEntity<Object> findAll() {
        List<LancamentoDespesaDTO> list = service.findAll();
        return ResponseEntity.ok().body(list);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<LancamentoDespesaDTO> findById(@PathVariable Long id) {
        LancamentoDespesaDTO dto = service.findById(id);
        return ResponseEntity.ok().body(dto);
    }

    @GetMapping(value = "/date")
    public ResponseEntity<Object> findByDate(@PathParam("date") LocalDate date) {
        List<LancamentoDespesaDTO> list = service.findByDate(date);
        return ResponseEntity.ok().body(list);
    }

    @GetMapping(value = "/period")
    public ResponseEntity<Object> findByPeriod(
            @PathParam("date") LocalDate dt_init,
            @PathParam("date") LocalDate dt_final) {
        List<LancamentoDespesaDTO> list = service.findByPeriod(dt_init, dt_final);
        return ResponseEntity.ok().body(list);
    }

    @GetMapping(value = "/total")
    public ResponseEntity<Object> findByTotal(
            @PathParam("date") LocalDate dt_init,
            @PathParam("date") LocalDate dt_final) {
        return ResponseEntity.ok().body(service.findByTotal(dt_init, dt_final));
    }

    @GetMapping(value = "/status")
    public ResponseEntity<Object> findByStatus(
            @PathParam("status") String status,
            @PathParam("date") LocalDate dt_init,
            @PathParam("date") LocalDate dt_final) {
        return ResponseEntity.ok().body(service.findByStatus(dt_init, dt_final, status));
    }

    @PostMapping
    public ResponseEntity<Object>insert(@Valid @RequestBody LancamentoDespesaDTO dto){
        LancamentoDespesaDTO result = service.insert(dto);
        LancamentoDespesaDTO aux = service.findById(result.getId_lancamento());
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(aux.getId_lancamento()).toUri();
        return ResponseEntity.created(uri).body(aux);
    }

    @PutMapping
    public ResponseEntity<LancamentoDespesaDTO>update(@Valid @RequestBody LancamentoDespesaDTO dto) {
        dto = service.update(dto.getId_lancamento(), dto);
        return ResponseEntity.ok().body(dto);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
