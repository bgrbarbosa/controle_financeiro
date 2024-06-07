package br.com.controlefinanceiro.controller;

import br.com.controlefinanceiro.model.dto.DespesaDTO;
import br.com.controlefinanceiro.services.DespesaService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping(value = "/despesa")
public class DespesaController {

    @Autowired
    private DespesaService service;

    @GetMapping
    public ResponseEntity<Object> findAll() {
        List<DespesaDTO> list = service.findAll();
        return ResponseEntity.ok().body(list);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<DespesaDTO> findById(@PathVariable Long id) {
        DespesaDTO dto = service.findById(id);
        return ResponseEntity.ok().body(dto);
    }

    @PostMapping
    public ResponseEntity<Object>insert(@Valid  @RequestBody DespesaDTO dto){
        DespesaDTO result = service.insert(dto);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(dto.getId_despesa()).toUri();
        return ResponseEntity.created(uri).body(result);
    }

    @PutMapping
    public ResponseEntity<DespesaDTO> update(@Valid @RequestBody DespesaDTO dto) {
        dto = service.update(dto.getId_despesa(), dto);
        return ResponseEntity.ok().body(dto);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
