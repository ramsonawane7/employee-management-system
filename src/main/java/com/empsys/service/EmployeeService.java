package com.empsys.service;

import com.empsys.dto.EmployeeDTO;
import com.empsys.entity.Employee;
import com.empsys.repository.EmployeeRepository;
import com.empsys.repository.DepartmentRepository;
import com.empsys.repository.DesignationRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

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

    //Get All Employees with Pagination
    public Page<EmployeeDTO> getAllEmployees(int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("empId").ascending());
        return employeeRepository.findAll(pageable)
                .map(emp -> {
                    EmployeeDTO dto = modelMapper.map(emp, EmployeeDTO.class);
                    dto.setDeptId(emp.getDepartment() != null ? emp.getDepartment().getDeptId() : null);
                    dto.setDesigId(emp.getDesignation() != null ? emp.getDesignation().getDesigId() : null);
                    return dto;
                });
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

    //Search Employees with Pagination
    public Page<EmployeeDTO> searchEmployees(String keyword, int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("empId").ascending());
        return employeeRepository.searchEmployees(keyword, pageable)
                .map(emp -> {
                    EmployeeDTO dto = modelMapper.map(emp, EmployeeDTO.class);
                    dto.setDeptId(emp.getDepartment() != null ? emp.getDepartment().getDeptId() : null);
                    dto.setDesigId(emp.getDesignation() != null ? emp.getDesignation().getDesigId() : null);
                    return dto;
                });
    }
    
    //count employees
    public long countEmployee() {
    	return employeeRepository.count();
    }
    
    
}
