import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Department {
    private final String name;
    private final List<Employee> employees;

    public Department(String name, List<Employee> employees) {
        this.name = name;
        this.employees = employees != null ? new ArrayList<>(employees) : new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public List<Employee> getEmployees() {
        return Collections.unmodifiableList(employees);
    }

    public void addEmployee(Employee employee) {
        if (employee != null) {
            employees.add(employee);
        }
    }
}