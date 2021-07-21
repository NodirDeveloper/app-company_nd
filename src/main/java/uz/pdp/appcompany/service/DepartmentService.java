package uz.pdp.appcompany.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uz.pdp.appcompany.entity.Company;
import uz.pdp.appcompany.entity.Department;
import uz.pdp.appcompany.payload.ApiResponse;
import uz.pdp.appcompany.payload.DepartmentDto;
import uz.pdp.appcompany.repository.CompanyRepository;
import uz.pdp.appcompany.repository.DepartmentRepository;

import java.util.List;
import java.util.Optional;

@Service
public class DepartmentService {

    @Autowired
    DepartmentRepository departmentRepository;

    @Autowired
    CompanyRepository companyRepository;


    public List<Department> getDepartmentList() {
        List<Department> departmentList = departmentRepository.findAll();
        return departmentList;
    }

    public Department getDepartmentById(Integer id) {
        Optional<Department> optionalDepartment = departmentRepository.findById(id);
        return optionalDepartment.orElse(null);
    }

    public ApiResponse addDepartment(DepartmentDto departmentDto) {
        boolean existsByName = departmentRepository.existsByName(departmentDto.getName());
        if (existsByName)
            return new ApiResponse("Such a name of department is exists!", false);
        Optional<Company> optionalCompany = companyRepository.findById(departmentDto.getCompanyId());
        if (!optionalCompany.isPresent())
            return new ApiResponse("No company was found!", false);
        Department department = new Department();
        department.setName(departmentDto.getName());
        department.setCompany(optionalCompany.get());
        departmentRepository.save(department);
        return new ApiResponse("Department added", true);
    }

    public ApiResponse editDepartment(DepartmentDto departmentDto, Integer id) {
        Optional<Department> optionalDepartment = departmentRepository.findById(id);
        if (!optionalDepartment.isPresent())
            return new ApiResponse("No company with such id was found!", false);
        boolean existsByNameAndIdNot = departmentRepository.existsByNameAndIdNot(departmentDto.getName(), id);
        if (existsByNameAndIdNot)
            return new ApiResponse("Such a name of department is exists!", false);
        Optional<Company> optionalCompany = companyRepository.findById(departmentDto.getCompanyId());
        if (!optionalCompany.isPresent())
            return new ApiResponse("No company was found!", false);
        Department editingDepartment = optionalDepartment.get();
        editingDepartment.setName(departmentDto.getName());
        editingDepartment.setCompany(optionalCompany.get());
        departmentRepository.save(editingDepartment);
        return new ApiResponse("Department edited", true);
    }

    public ApiResponse deleteDeparment(Integer id) {
        try {
            departmentRepository.deleteById(id);
            return new ApiResponse("Department deleted", true);
        } catch (Exception e) {
            return new ApiResponse("No department with such id was found", false);
        }


    }
}
