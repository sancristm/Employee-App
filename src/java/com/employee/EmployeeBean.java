///*
// * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
// * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
// */

/**
 *
 * @author sancrist
 */


package com.employee;

import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Named;
import org.primefaces.event.SelectEvent;
import org.primefaces.event.UnselectEvent;

import java.io.Serializable;
import java.util.List;

@Named
@ViewScoped
public class EmployeeBean implements Serializable {
    private List<Employee> employees;
    private Employee selectedEmployee = new Employee();
    private Employee newEmployee = new Employee();
    private final EmployeeDAO employeeDAO = new EmployeeDAO();

    public void loadEmployees() {
        try {
            employees = employeeDAO.getAllEmployees();
        } catch (RuntimeException e) {
            FacesContext.getCurrentInstance().addMessage(null, 
                new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error loading employees", e.getMessage()));
        }
    }

    public void onRowSelect() {
        
        
        if (selectedEmployee == null) {
            selectedEmployee = new Employee();
            
        }
    }
     public void onRowSelect(SelectEvent<Employee> event) {
        selectedEmployee = event.getObject();
        System.out.println("Selected employee ID: " + selectedEmployee.getId());
    }
     public void onRowUnselect(UnselectEvent<Employee> event) {
        selectedEmployee = new Employee();
    }
     
     public void updateEmployee() {
        try {
            if (selectedEmployee == null || selectedEmployee.getId() == 0) {
                throw new RuntimeException("No employee selected for update");
            }
            
            employeeDAO.updateEmployee(selectedEmployee);
            loadEmployees();
            
            FacesContext.getCurrentInstance().addMessage(null,
                new FacesMessage(FacesMessage.SEVERITY_INFO,
                "Success", "Employee updated successfully"));
            
            // Reset selection
            selectedEmployee = new Employee();
        } catch (RuntimeException e) {
            FacesContext.getCurrentInstance().addMessage(null,
                new FacesMessage(FacesMessage.SEVERITY_ERROR,
                "Error", "Failed to update employee: " + e.getMessage()));
        }
    }
    
    public void deleteEmployee() {
        try {
            if (selectedEmployee == null || selectedEmployee.getId() == 0) {
                throw new RuntimeException("No employee selected for deletion");
            }
            
            int idToDelete = selectedEmployee.getId();
            employeeDAO.deleteEmployee(idToDelete);
            loadEmployees();
            
            FacesContext.getCurrentInstance().addMessage(null,
                new FacesMessage(FacesMessage.SEVERITY_INFO,
                "Success", "Employee deleted successfully"));
            
            // Reset selection
            selectedEmployee = new Employee();
        } catch (RuntimeException e) {
            FacesContext.getCurrentInstance().addMessage(null,
                new FacesMessage(FacesMessage.SEVERITY_ERROR,
                "Error", "Failed to delete employee: " + e.getMessage()));
        }
    }
    
    
    
    public void addEmployee() {
        try {
            employeeDAO.addEmployee(newEmployee);
            loadEmployees();
            newEmployee = new Employee();
            FacesContext.getCurrentInstance().addMessage(null, 
                new FacesMessage("Employee added successfully"));
        } catch (RuntimeException e) {
            FacesContext.getCurrentInstance().addMessage(null, 
                new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error adding employee", e.getMessage()));
        }
    }

//    public void updateEmployee() {
//        try {
//            employeeDAO.updateEmployee(selectedEmployee);
//            loadEmployees();
//            FacesContext.getCurrentInstance().addMessage(null, 
//                new FacesMessage("Employee updated successfully"));
//        } catch (RuntimeException e) {
//            FacesContext.getCurrentInstance().addMessage(null, 
//                new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error updating employee", e.getMessage()));
//        }
//    }

//    public void deleteEmployee() {
//        try {
//            employeeDAO.deleteEmployee(selectedEmployee.getId());
//            loadEmployees();
//            selectedEmployee = new Employee();
//            FacesContext.getCurrentInstance().addMessage(null, 
//                new FacesMessage("Employee deleted successfully"));
//        } catch (RuntimeException e) {
//            FacesContext.getCurrentInstance().addMessage(null, 
//                new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error deleting employee", e.getMessage()));
//        }
//    }

    public void cancelEdit() {
        selectedEmployee = new Employee();
    }

    // Getters and Setters
    public List<Employee> getEmployees() {
        if (employees == null) {
            loadEmployees();
        }
        return employees;
    }

    public Employee getSelectedEmployee() { return selectedEmployee; }
    public void setSelectedEmployee(Employee selectedEmployee) { this.selectedEmployee = selectedEmployee; }
    public Employee getNewEmployee() { return newEmployee; }
    public void setNewEmployee(Employee newEmployee) { this.newEmployee = newEmployee; }
}