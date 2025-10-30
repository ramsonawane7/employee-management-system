package com.empsys.service;

import com.empsys.dto.DesignationDTO;
import com.empsys.entity.Designation;
import com.empsys.repository.DesignationRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class DesignationService {

    @Autowired
    private DesignationRepository designationRepository;

    @Autowired
    private ModelMapper modelMapper;

    public List<DesignationDTO> getAllDesignations() {
        return designationRepository.findAll(Sort.by(Sort.Direction.ASC, "desigId"))
                .stream()
                .map(desig -> modelMapper.map(desig, DesignationDTO.class))
                .collect(Collectors.toList());
    }

    public DesignationDTO getDesignationById(Long id) {
        return designationRepository.findById(id)
                .map(desig -> modelMapper.map(desig, DesignationDTO.class))
                .orElse(null);
    }

    public DesignationDTO addDesignation(DesignationDTO dto) {
        Designation designation = modelMapper.map(dto, Designation.class);
        designationRepository.save(designation);
        return modelMapper.map(designation, DesignationDTO.class);
    }

    public void deleteDesignation(Long id) {
        designationRepository.deleteById(id);
    }
}
