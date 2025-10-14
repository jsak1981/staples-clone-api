package com.ecommerce.staples_clone.coding;

import java.util.*;
import java.util.stream.Collectors;

public class Question9 {

  public static void main(String[] args) {
    // Given a list of employee objects, group them by their department using the Stream API's
    // groupingBy collector.

    List<Employee> employees =
        Arrays.asList(
            new Employee(10, "Engineering", 91),
            new Employee(20, "Marketing", 93),
            new Employee(30, "Engineering", 94),
            new Employee(40, "HR", 95));
    // Group the employees by their department
    Map<String, List<Employee>> store =
        employees.stream().collect(Collectors.groupingBy(Employee::getDeptName));

    store.forEach(
        (deptName, emps) -> {
          System.out.println(deptName);
          emps.forEach(employee -> System.out.println(employee.getDeptId()));
        });
  }
}

class Employee {

  private int deptId;
  private String deptName;
  private int empId;

  public Employee(int deptId, String deptName, int empId) {
    this.deptId = deptId;
    this.deptName = deptName;
    this.empId = empId;
  }

  public int getDeptId() {
    return deptId;
  }

  public String getDeptName() {
    return deptName;
  }

  public int getEmpId() {
    return empId;
  }
}
