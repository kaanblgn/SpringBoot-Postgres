package com.sekom.banking.business;

import com.sekom.banking.dto.request.BankRequestDto;
import com.sekom.banking.dto.response.BankResponseDto;
import com.sekom.banking.entity.Bank;
import com.sekom.banking.enums.EntityType;
import com.sekom.banking.mapper.CustomMapper;
import com.sekom.banking.service.BankService;
import com.sekom.banking.service.impl.CrudException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class BankBusinessService {

    private final BankService bankService;
    private final CustomMapper mapper;

    public BankResponseDto getBankById(UUID bankId) {
        return bankService.getBankById(bankId)
                .map(mapper::mapToResponseDto)
                .orElseThrow(() ->  CrudException.entityNotFound(EntityType.BANK, String.valueOf(bankId)));
    }

    public List<BankResponseDto> getAllBanks() {
        List<Bank> bankList = bankService.getAllBanks();

        if (bankList.isEmpty()){
            throw CrudException.entitiesNotFound(EntityType.BANK);
        }

        return bankList.stream().map(mapper::mapToResponseDto).toList();
    }

    public BankResponseDto createBank(BankRequestDto requestDto) {

        try {
            Bank createdBank = bankService.createBank(mapper.mapToEntity(requestDto));
            return mapper.mapToResponseDto(createdBank);
        } catch (Exception e) {
            throw CrudException.createEntityFailed(EntityType.BANK, e);
        }
    }

    public BankResponseDto updateBank(UUID bankId, BankRequestDto requestDto) {
        Bank bankToUpdate = bankService.getBankById(bankId)
                .orElseThrow(() -> CrudException.entityNotFound(EntityType.BANK, bankId.toString()));

        bankToUpdate.setName(requestDto.getName());

        try {
            Bank updatedBank = bankService.updateBank(bankToUpdate);
            return mapper.mapToResponseDto(updatedBank);
        } catch (Exception e){
            throw CrudException.updateEntityFailed(EntityType.BANK, bankId.toString(), e);
        }
    }

    public void deleteBank(UUID bankId) {
        Bank bankToDelete = bankService.getBankById(bankId)
                .orElseThrow(() -> CrudException.entityNotFound(EntityType.BANK, bankId.toString()));

        try {
            bankService.deleteBank(bankToDelete);
        } catch (Exception e) {
            throw CrudException.deleteEntityFailed(EntityType.BANK, bankId.toString(), e);
        }
    }


}