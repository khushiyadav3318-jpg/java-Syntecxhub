import java.util.*;
import java.util.stream.Collectors;

// Represents an individual employee record
class Employee {
    private final int id;
    private String name;
    private String department;
    private double salary;

    public Employee(int id, String name, String department, double salary) {
        this.id = id;
        this.name = name;
        this.department = department;
        this.salary = salary;
    }

    public int getId() { return id; }
    public String getName() { return name; }
    public String getDepartment() { return department; }
    public double getSalary() { return salary; }

    public void setName(String name) { this.name = name; }
    public void setDepartment(String department) { this.department = department; }
    public void setSalary(double salary) { this.salary = salary; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Employee employee = (Employee) o;
        return id == employee.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return String.format("ID: %d | Name: %s | Dept: %s | Salary: $%.2f", id, name, department, salary);
    }
}

// Manages the database using optimal Java Collections and Streams
class EmployeeManagementSystem {
    private final Map<Integer, Employee> employeeMap = new HashMap<>();

    // Adds a new employee record safely
    public boolean addEmployee(Employee emp) {
        if (emp == null || employeeMap.containsKey(emp.getId())) {
            return false;
        }
        employeeMap.put(emp.getId(), emp);
        return true;
    }

    // Updates an existing record
    public boolean updateEmployee(int id, String newName, String newDept, double newSalary) {
        Employee emp = employeeMap.get(id);
        if (emp == null) {
            return false;
        }
        emp.setName(newName);
        emp.setDepartment(newDept);
        emp.setSalary(newSalary);
        return true;
    }

    // Deletes a record by ID
    public boolean deleteEmployee(int id) {
        if (!employeeMap.containsKey(id)) {
            return false;
        }
        employeeMap.remove(id);
        return true;
    }

    // O(1) Search lookup
    public Employee getEmployeeById(int id) {
        return employeeMap.get(id);
    }

    // Returns an unmodifiable list view to preserve encapsulation
    public List<Employee> getAllEmployees() {
        return Collections.unmodifiableList(new ArrayList<>(employeeMap.values()));
    }

    // Computes active unique departments dynamically via Streams
    public Set<String> getUniqueDepartments() {
        return employeeMap.values().stream()
                .map(Employee::getDepartment)
                .collect(Collectors.toUnmodifiableSet());
    }
}

// Main execution class presenting terminal interface
public class ers1 {
    public static void main(String[] args) {
        EmployeeManagementSystem ems = new EmployeeManagementSystem();
        
        // Seed default records for immediate testing
        ems.addEmployee(new Employee(101, "Alice Smith", "Engineering", 85000));
        ems.addEmployee(new Employee(102, "Bob Jones", "HR", 60000));

        try (Scanner scanner = new Scanner(System.in)) {
            while (true) {
                System.out.println("\n=== EMPLOYEE MANAGEMENT SYSTEM ===");
                System.out.println("1. Add Employee");
                System.out.println("2. Update Employee");
                System.out.println("3. Delete Employee");
                System.out.println("4. Search Employee by ID");
                System.out.println("5. Display All Employees & Departments");
                System.out.println("6. Exit");
                System.out.print("Enter your choice (1-6): ");

                String choice = scanner.nextLine().trim();

                switch (choice) {
                    case "1":
                        System.out.println("\n--- Add New Employee ---");
                        int addId = readValidInt(scanner, "Enter ID: ");
                        
                        if (ems.getEmployeeById(addId) != null) {
                            System.out.println("Error: An employee with ID " + addId + " already exists.");
                            break;
                        }
                        
                        String addName = readValidString(scanner, "Enter Name: ");
                        String addDept = readValidString(scanner, "Enter Department: ");
                        double addSalary = readValidDouble(scanner, "Enter Salary: ");

                        ems.addEmployee(new Employee(addId, addName, addDept, addSalary));
                        System.out.println("Employee added successfully!");
                        break;

                    case "2":
                        System.out.println("\n--- Update Existing Employee ---");
                        int updateId = readValidInt(scanner, "Enter ID to Update: ");
                        
                        if (ems.getEmployeeById(updateId) == null) {
                            System.out.println("Error: Employee ID " + updateId + " not found.");
                            break;
                        }
                        
                        String newName = readValidString(scanner, "Enter New Name: ");
                        String newDept = readValidString(scanner, "Enter New Department: ");
                        double newSalary = readValidDouble(scanner, "Enter New Salary: ");

                        ems.updateEmployee(updateId, newName, newDept, newSalary);
                        System.out.println("Employee updated successfully!");
                        break;

                    case "3":
                        System.out.println("\n--- Delete Employee ---");
                        int deleteId = readValidInt(scanner, "Enter ID to Delete: ");
                        
                        boolean deleted = ems.deleteEmployee(deleteId);
                        System.out.println(deleted ? "Employee deleted successfully!" : "Error: Employee ID not found.");
                        break;

                    case "4":
                        System.out.println("\n--- Search Employee ---");
                        int searchId = readValidInt(scanner, "Enter ID to Search: ");
                        
                        Employee emp = ems.getEmployeeById(searchId);
                        System.out.println(emp != null ? "\n" + emp : "Employee not found.");
                        break;

                    case "5":
                        System.out.println("\n--- Registered Employees ---");
                        List<Employee> allEmployees = ems.getAllEmployees();
                        if (allEmployees.isEmpty()) {
                            System.out.println("No records found.");
                        } else {
                            allEmployees.forEach(System.out::println);
                        }

                        System.out.println("\n--- Active Departments ---");
                        Set<String> departments = ems.getUniqueDepartments();
                        if (departments.isEmpty()) {
                            System.out.println("No active departments found.");
                        } else {
                            System.out.println(departments);
                        }
                        break;

                    case "6":
                        System.out.println("Closing system safely. Goodbye!");
                        return;

                    default:
                        System.out.println("Invalid selection! Please enter a number between 1 and 6.");
                }
            }
        }
    }

    // Helper method to ensure valid integer input
    private static int readValidInt(Scanner scanner, String prompt) {
        while (true) {
            System.out.print(prompt);
            try {
                return Integer.parseInt(scanner.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a valid whole number.");
            }
        }
    }

    // Helper method to ensure valid decimal input
    private static double readValidDouble(Scanner scanner, String prompt) {
        while (true) {
            System.out.print(prompt);
            try {
                double val = Double.parseDouble(scanner.nextLine().trim());
                if (val < 0) {
                    System.out.println("Value cannot be negative.");
                    continue;
                }
                return val;
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a valid decimal number.");
            }
        }
    }

    // Helper method to ensure text values are not blank
    private static String readValidString(Scanner scanner, String prompt) {
        while (true) {
            System.out.print(prompt);
            String input = scanner.nextLine().trim();
            if (!input.isEmpty()) {
                return input;
            }
            System.out.println("Field cannot be left blank.");
        }
    }
}
