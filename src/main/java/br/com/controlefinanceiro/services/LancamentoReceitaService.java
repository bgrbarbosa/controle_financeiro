package br.com.controlefinanceiro.services;

import br.com.controlefinanceiro.model.Despesa;
import br.com.controlefinanceiro.model.LancamentoReceita;
import br.com.controlefinanceiro.model.dto.DespesaDTO;
import br.com.controlefinanceiro.model.dto.LancamentoDespesaDTO;
import br.com.controlefinanceiro.model.dto.LancamentoReceitaDTO;
import br.com.controlefinanceiro.repository.DespesaRepository;
import br.com.controlefinanceiro.repository.LancamentoReceitaRepository;
import br.com.controlefinanceiro.services.exception.DatabaseException;
import br.com.controlefinanceiro.services.exception.ResourceNotFoundException;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class LancamentoReceitaService {

    @Autowired
    private LancamentoReceitaRepository repository;

    @Transactional(readOnly = true)
    public List<LancamentoReceitaDTO> findAll() {
        List<LancamentoReceita> listLancReceita = repository.findAll();
        List<LancamentoReceitaDTO> listDTO = new ArrayList<>();
        for(LancamentoReceita lancamentoReceita : listLancReceita) {
            listDTO.add(toDTO(lancamentoReceita));
        }
        return listDTO;
    }

    @Transactional(readOnly = true)
    public LancamentoReceitaDTO findById(Long id) {
        Optional<LancamentoReceita> obj = repository.findById(id);
        LancamentoReceita entity = obj.orElseThrow(() -> new ResourceNotFoundException("Entity not found"));
        return toDTO(entity);
    }

    public LancamentoReceitaDTO insert(LancamentoReceitaDTO dto) {
        LancamentoReceita entity = new LancamentoReceita();
        entity = toEntity(dto);
        return toDTO(repository.save(entity));
    }

    @Transactional
    public LancamentoReceitaDTO update(Long id, LancamentoReceitaDTO dto) {
        try {
            LancamentoReceita entity = repository.getReferenceById(id);
            entity.setDesc_lancamento(dto.getDesc_lancamento());
            entity.setReceita(dto.getReceita());
            entity.setStatus(dto.getStatus());
            entity.setDt_venc(dto.getDt_venc());
            entity.setVl_lanc(dto.getVl_lanc());
            return toDTO(repository.save(entity));
        }
        catch (EntityNotFoundException e) {
            throw new ResourceNotFoundException("Id not found " + id);
        }
    }

    @Transactional()
    public void delete(Long id) {
        if (!repository.existsById(id)) {
            throw new ResourceNotFoundException("Resource not found!");
        }
        try {
            repository.deleteById(id);
        }
        catch (DataIntegrityViolationException e) {
            throw new DatabaseException("Integrity Violation");
        }
    }

    private LancamentoReceita toEntity(LancamentoReceitaDTO dto){
        var entity = new LancamentoReceita();
        BeanUtils.copyProperties(dto, entity);
        return entity;
    }

    private LancamentoReceitaDTO toDTO(LancamentoReceita entity){
        var dto = new LancamentoReceitaDTO();
        BeanUtils.copyProperties(entity, dto);
        return dto;
    }

}
