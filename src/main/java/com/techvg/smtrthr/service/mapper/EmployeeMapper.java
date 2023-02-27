package com.techvg.smtrthr.service.mapper;

import com.techvg.smtrthr.domain.Branch;
import com.techvg.smtrthr.domain.Department;
import com.techvg.smtrthr.domain.Designation;
import com.techvg.smtrthr.domain.Employee;
import com.techvg.smtrthr.domain.Region;
import com.techvg.smtrthr.service.dto.BranchDTO;
import com.techvg.smtrthr.service.dto.DepartmentDTO;
import com.techvg.smtrthr.service.dto.DesignationDTO;
import com.techvg.smtrthr.service.dto.EmployeeDTO;
import com.techvg.smtrthr.service.dto.RegionDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Employee} and its DTO {@link EmployeeDTO}.
 */
@Mapper(componentModel = "spring")
public interface EmployeeMapper extends EntityMapper<EmployeeDTO, Employee> {
    @Mapping(target = "designation", source = "designation", qualifiedByName = "designationName")
    @Mapping(target = "department", source = "department", qualifiedByName = "departmentName")
    @Mapping(target = "branch", source = "branch", qualifiedByName = "branchBranchName")
    @Mapping(target = "region", source = "region", qualifiedByName = "regionRegionName")
    EmployeeDTO toDto(Employee s);

    @Named("designationName")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "name", source = "name")
    DesignationDTO toDtoDesignationName(Designation designation);

    @Named("departmentName")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "name", source = "name")
    DepartmentDTO toDtoDepartmentName(Department department);

    @Named("branchBranchName")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "branchName", source = "branchName")
    BranchDTO toDtoBranchBranchName(Branch branch);

    @Named("regionRegionName")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "regionName", source = "regionName")
    RegionDTO toDtoRegionRegionName(Region region);
}
