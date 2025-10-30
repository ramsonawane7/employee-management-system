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
                .map(emp -> {
                    EmployeeDTO dto = modelMapper.map(emp, EmployeeDTO.class);
                    dto.setDeptId(emp.getDepartment() != null ? emp.getDepartment().getDeptId() : null);
                    dto.setDesigId(emp.getDesignation() != null ? emp.getDesignation().getDesigId() : null);
                    return dto;
                })
                .collect(Collectors.toList());
    }

    public EmployeeDTO getEmployeeById(Long id) {
        return employeeRepository.findById(id)
                .map(emp -> {
                    EmployeeDTO dto = modelMapper.map(emp, EmployeeDTO.class);
                    dto.setDeptId(emp.getDepartment() != null ? emp.getDepartment().getDeptId() : null);
                    dto.setDesigId(emp.getDesignation() != null ? emp.getDesignation().getDesigId() : null);
                    return dto;
                })
                .orElse(null);
    }

    public EmployeeDTO addEmployee(EmployeeDTO dto) {
        Employee emp = modelMapper.map(dto, Employee.class);

        // ✅ Set Department & Designation before saving
        emp.setDepartment(departmentRepository.findById(dto.getDeptId()).orElse(null));
        emp.setDesignation(designationRepository.findById(dto.getDesigId()).orElse(null));

        employeeRepository.save(emp);

        EmployeeDTO result = modelMapper.map(emp, EmployeeDTO.class);
        result.setDeptId(emp.getDepartment() != null ? emp.getDepartment().getDeptId() : null);
        result.setDesigId(emp.getDesignation() != null ? emp.getDesignation().getDesigId() : null);
        return result;
    }

    public EmployeeDTO updateEmployee(Long id, EmployeeDTO dto) {
        Employee existingEmp = employeeRepository.findById(id).orElse(null);
        if (existingEmp != null) {
            existingEmp.setFirstName(dto.getFirstName());
            existingEmp.setLastName(dto.getLastName());
            existingEmp.setEmail(dto.getEmail());
            existingEmp.setPhone(dto.getPhone());
            existingEmp.setHireDate(dto.getHireDate());
            existingEmp.setDepartment(departmentRepository.findById(dto.getDeptId()).orElse(null));
            existingEmp.setDesignation(designationRepository.findById(dto.getDesigId()).orElse(null));

            employeeRepository.save(existingEmp);

            EmployeeDTO result = modelMapper.map(existingEmp, EmployeeDTO.class);
            result.setDeptId(existingEmp.getDepartment() != null ? existingEmp.getDepartment().getDeptId() : null);
            result.setDesigId(existingEmp.getDesignation() != null ? existingEmp.getDesignation().getDesigId() : null);
            return result;
        }
        return null;
    }

    public void deleteEmployee(Long id) {
        employeeRepository.deleteById(id);
    }
}
