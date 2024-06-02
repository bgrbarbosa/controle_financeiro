package br.com.controlefinanceiro.services;

import br.com.controlefinanceiro.model.Despesa;
import br.com.controlefinanceiro.model.LancamentoDespesa;
import br.com.controlefinanceiro.model.LancamentoReceita;
import br.com.controlefinanceiro.model.Receita;
import br.com.controlefinanceiro.model.dto.LancamentoDespesaDTO;
import br.com.controlefinanceiro.model.dto.LancamentoReceitaDTO;
import br.com.controlefinanceiro.repository.LancamentoReceitaRepository;
import br.com.controlefinanceiro.repository.ReceitaRepository;
import br.com.controlefinanceiro.services.exception.DatabaseException;
import br.com.controlefinanceiro.services.exception.EntityNotFoundException;
import br.com.controlefinanceiro.services.exception.ResourceNotFoundException;
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
public class LancamentoReceitaService {

    @Autowired
    private LancamentoReceitaRepository repository;

    @Autowired
    private ReceitaRepository receitaRepository;

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
    public List<LancamentoReceitaDTO> findByDate(LocalDate dt_venc) {
        List<LancamentoReceita> listLancReceita = repository.findByDate(dt_venc);
        List<LancamentoReceitaDTO> listLancDTO = new ArrayList<>();
        for(LancamentoReceita lancReceita : listLancReceita) {
            listLancDTO.add(toDTO(lancReceita));
        }
        return listLancDTO;
    }

    @Transactional(readOnly = true)
    public List<LancamentoReceitaDTO> findByPeriod(LocalDate dt_init, LocalDate dt_final) {
        List<LancamentoReceita> listLancReceita = repository.findByPeriod(dt_init, dt_final);
        List<LancamentoReceitaDTO> listLancDTO = new ArrayList<>();
        for(LancamentoReceita lancReceita : listLancReceita) {
            listLancDTO.add(toDTO(lancReceita));
        }
        return listLancDTO;
    }

    @Transactional(readOnly = true)
    public Double findByTotal(LocalDate dt_init, LocalDate dt_final) {
        List<LancamentoReceita> listLancReceita = repository.findByPeriod(dt_init, dt_final);
        Double v_total = 0.00;
        for (LancamentoReceita lancamento: listLancReceita){
            v_total = v_total + lancamento.getVl_lanc();
        }
        return v_total;
    }


    @Transactional(readOnly = true)
    public LancamentoReceitaDTO findById(Long id) {
        Optional<LancamentoReceita> obj = repository.findById(id);
        LancamentoReceita entity = obj.orElseThrow(() -> new ResourceNotFoundException("Entity not found"));
        return toDTO(entity);
    }

    @Transactional(readOnly = true)
    public List<LancamentoReceitaDTO> findByStatus(LocalDate dt_init, LocalDate dt_final,String status) {
        List<LancamentoReceita> listLancReceita = repository.findByStatus(dt_init, dt_final, status);
        List<LancamentoReceitaDTO> listLancDTO = new ArrayList<>();
        for(LancamentoReceita lancReceita : listLancReceita) {
            listLancDTO.add(toDTO(lancReceita));
        }
        return listLancDTO;
    }
    @Transactional
    public LancamentoReceitaDTO insert(LancamentoReceitaDTO dto) {
        if (!receitaRepository.existsById(dto.getReceita().getId_receita())) {
            throw new ResourceNotFoundException("Resource not found");
        }
        Optional<Receita> aux = receitaRepository.findById(dto.getReceita().getId_receita());
        dto.setReceita(aux.orElseThrow(() -> new ResourceNotFoundException("Entity not found!")));
        return toDTO(repository.save(toEntity(dto)));
    }

    @Transactional
    public LancamentoReceitaDTO update(Long id, LancamentoReceitaDTO dto) {
        if (!repository.existsById(id) ){
            throw new ResourceNotFoundException("Resource not found");
        } else {
            LancamentoReceita entity = new LancamentoReceita();
            entity.setId_lancamento(id);
            if (!receitaRepository.existsById(dto.getReceita().getId_receita())) {
                throw new ResourceNotFoundException("Resource not found");
            } else {
                entity.setReceita(dto.getReceita());
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
