package dev.nes24.android.techhaven_ph52634_ph52751.Model;

public class Empolyee {
    private Integer Id_Department;
    private String Id_Employee;
    private String Employee_Name;
    private String Employee_password;
    private String Employee_MiddleName;
    private String Employee_Phone;
    private String Empolyee_Email;
    private String Employee_Duty;
    private Integer Empolyee_Salary;

    public Integer getEmpolyee_Salary() {
        return Empolyee_Salary;
    }

    public void setEmpolyee_Salary(Integer empolyee_Salary) {
        Empolyee_Salary = empolyee_Salary;
    }

    public Empolyee(Integer id_Department, String id_Employee, String employee_Name, String employee_password, String employee_MiddleName, String employee_Phone, String empolyee_Email, String employee_Duty, Integer empolyee_Salary) {
        Id_Department = id_Department;
        Id_Employee = id_Employee;
        Employee_Name = employee_Name;
        Employee_password = employee_password;
        Employee_MiddleName = employee_MiddleName;
        Employee_Phone = employee_Phone;
        Empolyee_Email = empolyee_Email;
        Employee_Duty = employee_Duty;
        Empolyee_Salary = empolyee_Salary;
    }
    private String Name_Department;

    public String getName_Department() {
        return Name_Department;
    }

    public void setName_Department(String name_Department) {
        Name_Department = name_Department;
    }

    public Empolyee(Integer id_Department, String id_Employee, String employee_Name, String employee_password, String employee_MiddleName, String employee_Phone, String empolyee_Email, String employee_Duty, Integer empolyee_Salary,String name_Department) {
        Id_Department = id_Department;
        Id_Employee = id_Employee;
        Employee_Name = employee_Name;
        Employee_password = employee_password;
        Employee_MiddleName = employee_MiddleName;
        Employee_Phone = employee_Phone;
        Empolyee_Email = empolyee_Email;
        Employee_Duty = employee_Duty;
        Name_Department = name_Department;
        Empolyee_Salary = empolyee_Salary;
    }


    public String getId_Employee() {
        return Id_Employee;
    }

    public void setId_Employee(String id_Employee) {
        Id_Employee = id_Employee;
    }

    public String getEmployee_Name() {
        return Employee_Name;
    }

    public void setEmployee_Name(String employee_Name) {
        Employee_Name = employee_Name;
    }

    public String getEmployee_password() {
        return Employee_password;
    }

    public void setEmployee_password(String employee_password) {
        Employee_password = employee_password;
    }

    public String getEmployee_MiddleName() {
        return Employee_MiddleName;
    }

    public void setEmployee_MiddleName(String employee_MiddleName) {
        Employee_MiddleName = employee_MiddleName;
    }

    public String getEmployee_Phone() {
        return Employee_Phone;
    }

    public void setEmployee_Phone(String employee_Phone) {
        Employee_Phone = employee_Phone;
    }

    public String getEmpolyee_Email() {
        return Empolyee_Email;
    }

    public void setEmpolyee_Email(String empolyee_Email) {
        Empolyee_Email = empolyee_Email;
    }

    public String getEmployee_Duty() {
        return Employee_Duty;
    }

    public Integer getId_Department() {
        return Id_Department;
    }

    public void setId_Department(Integer id_Department) {
        Id_Department = id_Department;
    }
    public void setEmployee_Duty(String employee_Duty) {
        Employee_Duty = employee_Duty;
    }
}
