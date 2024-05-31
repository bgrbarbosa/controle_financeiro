package br.com.controlefinanceiro.services;

import br.com.controlefinanceiro.model.Receita;
import br.com.controlefinanceiro.model.dto.DespesaDTO;
import br.com.controlefinanceiro.model.dto.ReceitaDTO;
import br.com.controlefinanceiro.repository.ReceitaRepository;
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
public class ReceitaService {

    @Autowired
    private ReceitaRepository repository;

    @Transactional(readOnly = true)
    public List<ReceitaDTO> findAll() {
        List<Receita> listReceita = repository.findAll();
        List<ReceitaDTO> listDTO = new ArrayList<>();
        for(Receita receita : listReceita) {
            listDTO.add(toDTO(receita));
        }
        return listDTO;
    }

    @Transactional(readOnly = true)
    public ReceitaDTO findById(Long id) {
        Optional<Receita> obj = repository.findById(id);
        Receita entity = obj.orElseThrow(() -> new ResourceNotFoundException("Entity not found"));
        return toDTO(entity);
    }

    public ReceitaDTO insert(ReceitaDTO dto) {
        Receita entity = new Receita();
        entity = toEntity(dto);
        return toDTO(repository.save(entity));
    }

    @Transactional
    public ReceitaDTO update(Long id, ReceitaDTO dto) {
        try {
            Receita entity = repository.getReferenceById(id);
            entity.setDesc_receita(dto.getDesc_receita());
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

    private Receita toEntity(ReceitaDTO dto){
        var entity = new Receita();
        BeanUtils.copyProperties(dto, entity);
        return entity;
    }

    private ReceitaDTO toDTO(Receita entity){
        var dto = new ReceitaDTO();
        BeanUtils.copyProperties(entity, dto);
        return dto;
    }
}
