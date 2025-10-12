import java.util.Arrays;
import java.util.List;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

public class FlatMapDemo {
    public static void main(String[] args) {
        // Create employees
        Employee alice = new Employee(1, "Alice", "Engineer");
        Employee bob = new Employee(2, "Bob", "Engineer");
        Employee charlie = new Employee(3, "Charlie", "Manager");
        Employee diana = new Employee(4, "Diana", "Designer");

        // Create departments with employee lists
        Department engineering = new Department("Engineering", Arrays.asList(alice, bob));
        Department design = new Department("Design", Arrays.asList(diana));
        Department management = new Department("Management", Arrays.asList(charlie));

        List<Department> departments = Arrays.asList(engineering, design, management);

        // Use flatMap to flatten department employee lists into a single stream
        List<Employee> allEmployees = departments.stream()
                .filter(dept -> dept != null)
                .flatMap(dept -> dept.getEmployees().stream())
                .distinct()
                .collect(Collectors.toList());

        // Print flattened list
        System.out.println("All employees (flattened):");
        allEmployees.forEach(System.out::println);

        // Group employees department-wise
        Map<String, List<Employee>> employeesByDept = departments.stream()
                .filter(Objects::nonNull)
                .collect(Collectors.toMap(
                        Department::getName,
                        d -> d.getEmployees().stream()
                                .filter(Objects::nonNull)
                                .distinct()
                                .collect(Collectors.toList()),
                        (left, right) -> {
                            List<Employee> merged = new java.util.ArrayList<>(left);
                            merged.addAll(right);
                            return merged.stream().distinct().collect(Collectors.toList());
                        },
                        LinkedHashMap::new
                ));

        System.out.println("\nEmployees department-wise:");
        employeesByDept.forEach((deptName, emps) -> {
            System.out.println(deptName + ":");
            emps.forEach(e -> System.out.println("  " + e));
        });
    }
}