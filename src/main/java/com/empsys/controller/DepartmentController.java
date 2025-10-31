package com.empsys.controller;

import com.empsys.dto.DepartmentDTO;
import com.empsys.service.DepartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/department")
@CrossOrigin(origins = "*")
public class DepartmentController {

    @Autowired
    private DepartmentService departmentService;

    @GetMapping
    public List<DepartmentDTO> getAllDepartments() {
        return departmentService.getAllDepartments();
    }

    @GetMapping("/{id}")
    public DepartmentDTO getDepartmentById(@PathVariable Long id) {
        return departmentService.getDepartmentById(id);
    }

    @PostMapping
    public DepartmentDTO addDepartment(@RequestBody DepartmentDTO dto) {
        return departmentService.addDepartment(dto);
    }

    @DeleteMapping("/{id}")
    public void deleteDepartment(@PathVariable Long id) {
        departmentService.deleteDepartment(id);
    }

    @PutMapping("/{id}")
    public DepartmentDTO updateDepartment(@PathVariable Long id, @RequestBody DepartmentDTO dto) {
        dto.setDeptId(id);
        return departmentService.updateDepartment(id, dto);
    }
    
    //count employees apis
    @GetMapping("/count")
    public long countEmployees() {
    	return departmentService.countDepartment();
    }
}
