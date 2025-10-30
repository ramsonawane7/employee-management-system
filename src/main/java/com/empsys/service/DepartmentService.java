package com.empsys.service;

import com.empsys.dto.DepartmentDTO;
import com.empsys.entity.Department;
import com.empsys.repository.DepartmentRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class DepartmentService {

    @Autowired
    private DepartmentRepository departmentRepository;

    @Autowired
    private ModelMapper modelMapper;

    public List<DepartmentDTO> getAllDepartments() {
        return departmentRepository.findAll(Sort.by(Sort.Direction.ASC, "deptId"))
                .stream()
                .map(dept -> modelMapper.map(dept, DepartmentDTO.class))
                .collect(Collectors.toList());
    }

    public DepartmentDTO getDepartmentById(Long id) {
        return departmentRepository.findById(id)
                .map(dept -> modelMapper.map(dept, DepartmentDTO.class))
                .orElse(null);
    }

    public DepartmentDTO addDepartment(DepartmentDTO dto) {
        Department department = modelMapper.map(dto, Department.class);
        departmentRepository.save(department);
        return modelMapper.map(department, DepartmentDTO.class);
    }

    public void deleteDepartment(Long id) {
        departmentRepository.deleteById(id);
    }

    public DepartmentDTO updateDepartment(Long id, DepartmentDTO dto) {
        return departmentRepository.findById(id)
                .map(existingDept -> {
                    existingDept.setDeptName(dto.getDeptName());
                    departmentRepository.save(existingDept);
                    return modelMapper.map(existingDept, DepartmentDTO.class);
                })
                .orElse(null);
    }
}
