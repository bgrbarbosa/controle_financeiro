package br.com.controlefinanceiro.controller;

import br.com.controlefinanceiro.model.dto.DespesaDTO;
import br.com.controlefinanceiro.model.dto.LancamentoDespesaDTO;
import br.com.controlefinanceiro.services.DespesaService;
import br.com.controlefinanceiro.services.LancamentoDespesaService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping(value = "/lancamento/despesa")
public class LancamentoDespesaController {

    @Autowired
    private LancamentoDespesaService service;

    @GetMapping
    @Transactional
    public ResponseEntity<Object> findAll() {
        List<LancamentoDespesaDTO> list = service.findAll();
        return ResponseEntity.ok().body(list);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<LancamentoDespesaDTO> findById(@PathVariable Long id) {
        LancamentoDespesaDTO dto = service.findById(id);
        return ResponseEntity.ok().body(dto);
    }

    @PostMapping
    public ResponseEntity<Object>insert(@RequestBody LancamentoDespesaDTO dto){
        LancamentoDespesaDTO result = service.insert(dto);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(dto.getId_lancamento()).toUri();
        return ResponseEntity.created(uri).body(result);
    }

    @PutMapping
    public ResponseEntity<LancamentoDespesaDTO> update(@RequestBody LancamentoDespesaDTO dto) {
        dto = service.update(dto.getId_lancamento(), dto);
        return ResponseEntity.ok().body(dto);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
