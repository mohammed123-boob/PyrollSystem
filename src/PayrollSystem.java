import java.util.*;
import java.time.LocalDate;

interface Taxable {
    double TAX_RATE = 0.15;

    double calculateTax();
}

interface BonusCalculable {
    double calculateBonus(double score);
}

abstract class Employee implements Taxable, BonusCalculable, Comparable<Employee> {

    private String id;
    private String name;
    protected double baseSalary;
    private LocalDate joinDate;

    public Employee(String id, String name, double baseSalary, LocalDate joinDate) {
        this.id = id;
        this.name = name;
        this.baseSalary = baseSalary;
        this.joinDate = joinDate;
    }

    public abstract double calculateNetSalary();

    public double calculateTax() {
        return calculateNetSalary() * TAX_RATE;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public double getBaseSalary() {
        return baseSalary;
    }

    public LocalDate getJoinDate() {
        return joinDate;
    }

    public int compareTo(Employee other) {
        return Double.compare(other.calculateNetSalary(), calculateNetSalary());
    }

    public String toString() {
        return "ID: " + id +
               " Name: " + name +
               " Salary: " + calculateNetSalary();
    }
}

class FullTimeEmployee extends Employee {

    private double allowance;

    public FullTimeEmployee(String id, String name, double salary,
                            LocalDate date, double allowance) {
        super(id, name, salary, date);
        this.allowance = allowance;
    }

    public double calculateNetSalary() {
        return baseSalary + allowance;
    }

    public double calculateBonus(double score) {
        if (score >= 4.0) {
            return baseSalary * 0.10;
        }
        return 0;
    }
}

class ContractorEmployee extends Employee {

    private int hourlyRate;
    private int hoursWorked;

    public ContractorEmployee(String id, String name, LocalDate date,
                              int hourlyRate, int hoursWorked) {
        super(id, name, 0, date);
        this.hourlyRate = hourlyRate;
        this.hoursWorked = hoursWorked;
    }

    public double calculateNetSalary() {
        return hourlyRate * hoursWorked;
    }

    public double calculateBonus(double score) {
        if (score > 4.0) {
            return 500;
        }
        return 0;
    }
}

public class PayrollSystem {

    public static void main(String[] args) {

        ArrayList<Employee> employees = new ArrayList<>();

        employees.add(new FullTimeEmployee(
                "101", "Ahmed", 5000,
                LocalDate.of(2022, 1, 10), 1000));

        employees.add(new FullTimeEmployee(
                "102", "Ali", 4000,
                LocalDate.of(2023, 5, 15), 800));

        employees.add(new ContractorEmployee(
                "103", "Omar",
                LocalDate.of(2021, 3, 20), 50, 100));

        employees.add(new ContractorEmployee(
                "104", "Mohamed",
                LocalDate.of(2024, 2, 10), 40, 80));

        Map<String, Double> scores = new HashMap<>();

        scores.put("101", 4.5);
        scores.put("102", 3.5);
        scores.put("103", 4.8);
        scores.put("104", 4.0);

        System.out.println("Employees Sorted By Salary:");

        Collections.sort(employees);

        for (Employee e : employees) {
            System.out.println(e);
        }

        System.out.println("\nEmployees Sorted By Join Date:");

        employees.sort(Comparator.comparing(Employee::getJoinDate));

        for (Employee e : employees) {
            System.out.println(e);
        }

        double totalSalary = 0;
        double totalTax = 0;
        double totalBonus = 0;

        System.out.println("\nPayroll Report:");

        for (Employee e : employees) {

            double salary = e.calculateNetSalary();
            double tax = e.calculateTax();
            double bonus = e.calculateBonus(scores.get(e.getId()));

            totalSalary += salary;
            totalTax += tax;
            totalBonus += bonus;

System.out.println("\nName: " + e.getName());
            System.out.println("Salary: " + salary);
            System.out.println("Tax: " + tax);
            System.out.println("Bonus: " + bonus);
        }

        System.out.println("\nTotal Salary: " + totalSalary);
        System.out.println("Total Tax: " + totalTax);
        System.out.println("Total Bonus: " + totalBonus);
    }
}