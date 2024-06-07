package br.com.controlefinanceiro.services;

import br.com.controlefinanceiro.model.Despesa;
import br.com.controlefinanceiro.model.LancamentoDespesa;
import br.com.controlefinanceiro.model.dto.LancamentoDespesaDTO;
import br.com.controlefinanceiro.repository.DespesaRepository;
import br.com.controlefinanceiro.repository.LancamentoDespesaRepository;
import br.com.controlefinanceiro.services.exception.DatabaseException;
import br.com.controlefinanceiro.services.exception.ResourceNotFoundException;
import br.com.controlefinanceiro.services.exception.handleIntegrityConstraintViolationException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class LancamentoDespesaService {

    @Autowired
    private LancamentoDespesaRepository repository;

    @Autowired
    private DespesaService despesaService;

    @Autowired
    private DespesaRepository despesaRepository;

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
    public List<LancamentoDespesaDTO> findByDate(LocalDate dt_venc) {
        List<LancamentoDespesa> listLancDespesa = repository.findByDate(dt_venc);
        List<LancamentoDespesaDTO> listLancDTO = new ArrayList<>();
        for(LancamentoDespesa lancDespesa : listLancDespesa) {
            listLancDTO.add(toDTO(lancDespesa));
        }
        return listLancDTO;
    }

    @Transactional(readOnly = true)
    public List<LancamentoDespesaDTO> findByPeriod(LocalDate dt_init, LocalDate dt_final) {
        List<LancamentoDespesa> listLancDespesa = repository.findByPeriod(dt_init, dt_final);
        List<LancamentoDespesaDTO> listLancDTO = new ArrayList<>();
        for(LancamentoDespesa lancDespesa : listLancDespesa) {
            listLancDTO.add(toDTO(lancDespesa));
        }
        return listLancDTO;
    }

    @Transactional(readOnly = true)
    public Double findByTotal(LocalDate dt_init, LocalDate dt_final) {
        List<LancamentoDespesa> listLancDespesa = repository.findByPeriod(dt_init, dt_final);
        Double v_total = 0.00;
        for (LancamentoDespesa lancamento: listLancDespesa){
            v_total = v_total + lancamento.getVl_lanc();
        }
        return v_total;
    }

    @Transactional(readOnly = true)
    public LancamentoDespesaDTO findById(Long id) {
        Optional<LancamentoDespesa> obj = repository.findById(id);
        LancamentoDespesa entity = obj.orElseThrow(() -> new ResourceNotFoundException("Entity not found"));
        return toDTO(entity);
    }

    @Transactional(readOnly = true)
    public List<LancamentoDespesaDTO> findByStatus(LocalDate dt_init, LocalDate dt_final,String status) {
        List<LancamentoDespesa> listLancDespesa = repository.findByStatus(dt_init, dt_final, status);
        List<LancamentoDespesaDTO> listLancDTO = new ArrayList<>();
        for(LancamentoDespesa lancDespesa : listLancDespesa) {
            listLancDTO.add(toDTO(lancDespesa));
        }
        return listLancDTO;
    }
    @Transactional
    public LancamentoDespesaDTO insert(LancamentoDespesaDTO dto) {
        if (!despesaRepository.existsById(dto.getDespesa().getId_despesa())) {
            throw new ResourceNotFoundException("Resource not found");
        }
        Optional<Despesa> aux = despesaRepository.findById(dto.getDespesa().getId_despesa());
        dto.setDespesa(aux.orElseThrow(() -> new ResourceNotFoundException("Entity not found!")));
        return toDTO(repository.save(toEntity(dto)));
    }

    @Transactional
    public LancamentoDespesaDTO update(Long id, LancamentoDespesaDTO dto) {
        if (!repository.existsById(id) ){
            throw new ResourceNotFoundException("Resource not found");
        } else {
            LancamentoDespesa entity = new LancamentoDespesa();
            entity.setId_lancamento(id);
            if (!despesaRepository.existsById(dto.getDespesa().getId_despesa())) {
                throw new ResourceNotFoundException("Resource not found");
            } else {
                entity.setDespesa(dto.getDespesa());
                entity.setDesc_lancamento(dto.getDesc_lancamento());
                entity.setStatus(dto.getStatus());
                entity.setDt_venc(dto.getDt_venc());
                entity.setVl_lanc(dto.getVl_lanc());
                return toDTO(repository.save(entity));
            }
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
        catch (handleIntegrityConstraintViolationException e) {
            throw new handleIntegrityConstraintViolationException("Integrity Violation");
        }
    }

    private LancamentoDespesa toEntity(LancamentoDespesaDTO dto){
        var entity = new LancamentoDespesa();
        BeanUtils.copyProperties(dto, entity);
        return entity;
    }

    private LancamentoDespesaDTO toDTO(LancamentoDespesa entity){
        var dto = new LancamentoDespesaDTO();
        BeanUtils.copyProperties(entity, dto);
        return dto;
    }

}
