package br.com.controlefinanceiro.services;

import br.com.controlefinanceiro.model.LancamentoDespesa;
import br.com.controlefinanceiro.model.dto.LancamentoDespesaDTO;
import br.com.controlefinanceiro.repository.LancamentoDespesaRepository;
import br.com.controlefinanceiro.services.exception.DatabaseException;
import br.com.controlefinanceiro.services.exception.ResourceNotFoundException;
import jakarta.persistence.EntityNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class LancamentoDespesaService {

    @Autowired
    private LancamentoDespesaRepository repository;

    private ModelMapper mapper;

    @Transactional(readOnly = true)
    public List<LancamentoDespesaDTO> findAll() {
        List<LancamentoDespesa> listLancDespesa = repository.findAll();
        List<LancamentoDespesaDTO> listLancDTO = new ArrayList<>();
        for(LancamentoDespesa lancDespesa : listLancDespesa) {
            listLancDTO.add(toDTO(lancDespesa));
        }
        return listLancDTO;
    }

    @Transactional(readOnly = true)
    public LancamentoDespesaDTO findById(Long id) {
        Optional<LancamentoDespesa> obj = repository.findById(id);
        LancamentoDespesa entity = obj.orElseThrow(() -> new ResourceNotFoundException("Entity not found"));
        return toDTO(entity);
    }

    public LancamentoDespesaDTO insert(LancamentoDespesaDTO dto) {
        LancamentoDespesa entity = new LancamentoDespesa();
        entity = toEntity(dto);
        return toDTO(repository.save(entity));
    }

    @Transactional
    public LancamentoDespesaDTO update(Long id, LancamentoDespesaDTO dto) {
        try {
            LancamentoDespesa entity = repository.getReferenceById(id);
            entity.setDesc_lancamento(dto.getDesc_lancamento());
            entity.setDespesa(dto.getDespesa());
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

    private LancamentoDespesa toEntity(LancamentoDespesaDTO dto){
        var entity = new LancamentoDespesa();
        BeanUtils.copyProperties(dto, entity);
       // entity = mapper.map(dto, LancamentoDespesa.class);
        return entity;
    }

    private LancamentoDespesaDTO toDTO(LancamentoDespesa entity){
        var dto = new LancamentoDespesaDTO();
        BeanUtils.copyProperties(entity, dto);
       // dto = mapper.map(entity, LancamentoDespesaDTO.class);
        return dto;
    }

}
