package com.empsys.service;

import com.empsys.dto.EmployeeDTO;
import com.empsys.entity.Employee;
import com.empsys.repository.EmployeeRepository;
import com.empsys.repository.DepartmentRepository;
import com.empsys.repository.DesignationRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class EmployeeService {

    @Autowired
    private EmployeeRepository employeeRepository;
    @Autowired
    private DepartmentRepository departmentRepository;
    @Autowired
    private DesignationRepository designationRepository;
    @Autowired
    private ModelMapper modelMapper;

    public List<EmployeeDTO> getAllEmployees() {
        return employeeRepository.findAll()
                .stream()
                .map(emp -> modelMapper.map(emp, EmployeeDTO.class))
                .collect(Collectors.toList());
    }

    public EmployeeDTO getEmployeeById(Long id) {
        return employeeRepository.findById(id)
                .map(emp -> modelMapper.map(emp, EmployeeDTO.class))
                .orElse(null);
    }

    public EmployeeDTO addEmployee(EmployeeDTO dto) {
        Employee emp = modelMapper.map(dto, Employee.class);
        emp.setDepartment(departmentRepository.findById(dto.getDeptId()).orElse(null));
        emp.setDesignation(designationRepository.findById(dto.getDesigId()).orElse(null));
        employeeRepository.save(emp);
        return modelMapper.map(emp, EmployeeDTO.class);
    }

    public void deleteEmployee(Long id) {
        employeeRepository.deleteById(id);
    }
}
