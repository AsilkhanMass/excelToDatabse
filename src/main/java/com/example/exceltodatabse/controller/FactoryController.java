package com.example.exceltodatabse.controller;

import com.example.exceltodatabse.dto.factory.FactoryDto;
import com.example.exceltodatabse.entity.Factory;
import com.example.exceltodatabse.exception.ItemNotFoundException;
import com.example.exceltodatabse.repository.FactoryRepository;
import com.example.exceltodatabse.service.imp.FactoryService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/factory")
public class FactoryController {
    private final FactoryService factoryService;
    private final FactoryRepository factoryRepository;

    public FactoryController(FactoryService factoryService, FactoryRepository factoryRepository) {
        this.factoryService = factoryService;
        this.factoryRepository = factoryRepository;
    }

    @PostMapping("/add")
    @PreAuthorize("hasAnyRole('ADMIN', 'SUPER_ADMIN')")
    public ResponseEntity<String> add(@RequestBody FactoryDto dto) {
        if (factoryRepository.existsByInvNumber(dto.invNumber())) {
            return new ResponseEntity<>("This Inv exists", HttpStatus.BAD_REQUEST);
        }
        Factory factory = Factory.builder()
                .invNumber(dto.invNumber())
                .serialNumber(dto.serialNumber())
                .name(dto.name())
                .build();
        factoryRepository.save(factory);
        return new ResponseEntity<>("Created successfully!", HttpStatus.CREATED);
    }


    @DeleteMapping("/delete")
    @PreAuthorize("hasAnyRole('ADMIN', 'SUPER_ADMIN')")
    public ResponseEntity<String> delete(@RequestBody Long id){
        Factory factory = factoryRepository.findById(id).orElseThrow(
                () -> new ItemNotFoundException("Item not found with id:" + id)
        );
        factoryRepository.delete(factory);
        return new ResponseEntity<>("Deleted successfully", HttpStatus.OK);
    }

    @PutMapping("/update")
    @PreAuthorize("hasAnyRole('ADMIN', 'SUPER_ADMIN')")
    public ResponseEntity<String> update(@RequestBody FactoryDto dto){
        if(factoryRepository.existsByInvNumber(dto.invNumber())){
            Factory factory = factoryRepository.findByInvNumber(dto.invNumber()).orElseThrow(
                    () -> new ItemNotFoundException("Item not found with Inv Number: " + dto.invNumber())
            );
            if(dto.invNumber()!=null){
                factory.setInvNumber(dto.invNumber());
            }
            if(dto.name()!=null){
                factory.setName(dto.name());
            }
            if(dto.serialNumber()!=null){
                factory.setSerialNumber(dto.serialNumber());
            }
            factoryRepository.save(factory);
            return new ResponseEntity<>("Updated!", HttpStatus.OK);
        }
        return new ResponseEntity<>("Something went wrong!", HttpStatus.BAD_REQUEST);

    }


}
