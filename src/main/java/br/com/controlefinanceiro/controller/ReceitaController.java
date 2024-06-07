package br.com.controlefinanceiro.controller;

import br.com.controlefinanceiro.model.dto.ReceitaDTO;
import br.com.controlefinanceiro.services.ReceitaService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping(value = "/receita")
public class ReceitaController {

    @Autowired
    private ReceitaService service;

    @GetMapping
    public ResponseEntity<Object> findAll() {
        List<ReceitaDTO> list = service.findAll();
        return ResponseEntity.ok().body(list);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<ReceitaDTO> findById(@PathVariable Long id) {
        ReceitaDTO dto = service.findById(id);
        return ResponseEntity.ok().body(dto);
    }

    @PostMapping
    public ResponseEntity<Object>insert(@Valid @RequestBody ReceitaDTO dto){
        ReceitaDTO result = service.insert(dto);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(dto.getId_receita()).toUri();
        return ResponseEntity.created(uri).body(result);
    }

    @PutMapping
    public ResponseEntity<ReceitaDTO> update(@Valid @RequestBody ReceitaDTO dto) {
        dto = service.update(dto.getId_receita(), dto);
        return ResponseEntity.ok().body(dto);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
