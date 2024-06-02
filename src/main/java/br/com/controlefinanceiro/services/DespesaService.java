package br.com.controlefinanceiro.services;

import br.com.controlefinanceiro.model.Despesa;
import br.com.controlefinanceiro.model.dto.DespesaDTO;
import br.com.controlefinanceiro.repository.DespesaRepository;
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
public class DespesaService {

    @Autowired
    private DespesaRepository repository;

    @Transactional(readOnly = true)
    public List<DespesaDTO> findAll() {
        List<Despesa> listDespesa = repository.findAll();
        List<DespesaDTO> listDTO = new ArrayList<>();
        for(Despesa despesa : listDespesa) {
            listDTO.add(toDTO(despesa));
        }
        return listDTO;
    }

    @Transactional(readOnly = true)
    public DespesaDTO findById(Long id) {
        Optional<Despesa> obj = repository.findById(id);
        Despesa entity = obj.orElseThrow(() -> new ResourceNotFoundException("Entity not found"));
        return toDTO(entity);
    }

    public DespesaDTO insert(DespesaDTO dto) {
        Despesa entity = new Despesa();
        entity = toEntity(dto);
        return toDTO(repository.save(entity));
    }

    @Transactional
    public DespesaDTO update(Long id, DespesaDTO dto) {
        try {
            Despesa entity = repository.getReferenceById(id);
            entity.setDesc_despesa(dto.getDesc_despesa());
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

    public Despesa toEntity(DespesaDTO dto){
        var entity = new Despesa();
        BeanUtils.copyProperties(dto, entity);
        return entity;
    }

    public DespesaDTO toDTO(Despesa entity){
        var dto = new DespesaDTO();
        BeanUtils.copyProperties(entity, dto);
        return dto;
    }

}
