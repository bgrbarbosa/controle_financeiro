package br.com.controlefinanceiro.controller;

import br.com.controlefinanceiro.model.LancamentoReceita;
import br.com.controlefinanceiro.model.dto.DespesaDTO;
import br.com.controlefinanceiro.model.dto.LancamentoDespesaDTO;
import br.com.controlefinanceiro.model.dto.LancamentoReceitaDTO;
import br.com.controlefinanceiro.services.DespesaService;
import br.com.controlefinanceiro.services.JasperServiceLancamentoDespesa;
import br.com.controlefinanceiro.services.JasperServiceLancamentoReceita;
import br.com.controlefinanceiro.services.LancamentoReceitaService;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import jakarta.websocket.server.PathParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.io.IOException;
import java.net.URI;
import java.sql.Date;
import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping(value = "/lancamento/receita")
public class LancamentoReceitaController {

    @Autowired
    private LancamentoReceitaService service;

    @Autowired
    private JasperServiceLancamentoReceita jasperService;

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
    public ResponseEntity<Object>insert(@Valid @RequestBody LancamentoReceitaDTO dto){
        LancamentoReceitaDTO result = service.insert(dto);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(dto.getId_lancamento()).toUri();
        return ResponseEntity.created(uri).body(result);
    }

    @PutMapping
    public ResponseEntity<LancamentoReceitaDTO> update(@Valid @RequestBody LancamentoReceitaDTO dto) {
        dto = service.update(dto.getId_lancamento(), dto);
        return ResponseEntity.ok().body(dto);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/relatorio/status")
    public ResponseEntity<Void> getRelatorioStatus(
            HttpServletResponse response,
            @PathParam("dt_init") Date dt_init,
            @PathParam("dt_final") Date dt_final,
            @PathParam("status") String status) throws IOException {
        jasperService.addParams("dt_init", dt_init);
        jasperService.addParams("dt_final", dt_final);
        jasperService.addParams("status", status);
        byte[] bytes = jasperService.gerarPdfStatus();

        response.setContentType(MediaType.APPLICATION_PDF_VALUE);
        response.setHeader("Content-disposition", "inline; filename=" + System.currentTimeMillis() + ".pdf");
        response.getOutputStream().write(bytes);

        return ResponseEntity.ok().build();
    }

    @GetMapping("/relatorio/periodo")
    public ResponseEntity<Void> gerarPdfPeriodo(
            HttpServletResponse response,
            @PathParam("dt_init") Date dt_init,
            @PathParam("dt_final") Date dt_final) throws IOException {
        jasperService.addParams("dt_init", dt_init);
        jasperService.addParams("dt_final", dt_final);
        byte[] bytes = jasperService.gerarPdfPeriodo();

        response.setContentType(MediaType.APPLICATION_PDF_VALUE);
        response.setHeader("Content-disposition", "inline; filename=" + System.currentTimeMillis() + ".pdf");
        response.getOutputStream().write(bytes);

        return ResponseEntity.ok().build();
    }
}
