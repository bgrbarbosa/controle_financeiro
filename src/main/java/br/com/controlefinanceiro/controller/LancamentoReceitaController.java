package br.com.controlefinanceiro.controller;

import br.com.controlefinanceiro.model.LancamentoReceita;
import br.com.controlefinanceiro.model.dto.DespesaDTO;
import br.com.controlefinanceiro.model.dto.LancamentoDespesaDTO;
import br.com.controlefinanceiro.model.dto.LancamentoReceitaDTO;
import br.com.controlefinanceiro.services.DespesaService;
import br.com.controlefinanceiro.services.LancamentoReceitaService;
import jakarta.websocket.server.PathParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping(value = "/lancamento/receita")
public class LancamentoReceitaController {

    @Autowired
    private LancamentoReceitaService service;

    @GetMapping
    public ResponseEntity<Object> findAll() {
        List<LancamentoReceitaDTO> list = service.findAll();
        return ResponseEntity.ok().body(list);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<LancamentoReceitaDTO> findById(@PathVariable Long id) {
        LancamentoReceitaDTO dto = service.findById(id);
        return ResponseEntity.ok().body(dto);
    }

    @GetMapping(value = "/date")
    public ResponseEntity<Object> findByDate(@PathParam("date") LocalDate date) {
        List<LancamentoReceitaDTO> list = service.findByDate(date);
        return ResponseEntity.ok().body(list);
    }

    @GetMapping(value = "/period")
    public ResponseEntity<Object> findByPeriod(
            @PathParam("date") LocalDate dt_init,
            @PathParam("date") LocalDate dt_final) {
        List<LancamentoReceitaDTO> list = service.findByPeriod(dt_init, dt_final);
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
    public ResponseEntity<Object>insert(@RequestBody LancamentoReceitaDTO dto){
        LancamentoReceitaDTO result = service.insert(dto);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(dto.getId_lancamento()).toUri();
        return ResponseEntity.created(uri).body(result);
    }

    @PutMapping
    public ResponseEntity<LancamentoReceitaDTO> update(@RequestBody LancamentoReceitaDTO dto) {
        dto = service.update(dto.getId_lancamento(), dto);
        return ResponseEntity.ok().body(dto);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
