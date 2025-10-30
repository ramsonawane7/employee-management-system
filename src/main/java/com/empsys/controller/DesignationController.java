package com.empsys.controller;

import com.empsys.dto.DesignationDTO;
import com.empsys.service.DesignationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/designations")
@CrossOrigin(origins = "*")
public class DesignationController {

    @Autowired
    private DesignationService designationService;

    @GetMapping
    public List<DesignationDTO> getAllDesignations() {
        return designationService.getAllDesignations();
    }

    @GetMapping("/{id}")
    public DesignationDTO getDesignationById(@PathVariable Long id) {
        return designationService.getDesignationById(id);
    }

    @PostMapping
    public DesignationDTO addDesignation(@RequestBody DesignationDTO dto) {
        return designationService.addDesignation(dto);
    }

    @DeleteMapping("/{id}")
    public void deleteDesignation(@PathVariable Long id) {
        designationService.deleteDesignation(id);
    }

    @PutMapping("/{id}")
    public DesignationDTO updateDesignation(@PathVariable Long id, @RequestBody DesignationDTO dto) {
        dto.setDesigId(id);
        return designationService.updateDesignation(id, dto);
    }
}
