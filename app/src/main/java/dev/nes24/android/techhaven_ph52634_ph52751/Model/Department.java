package dev.nes24.android.techhaven_ph52634_ph52751.Model;

public class Department {
    private Integer Id_Department;
    private String Department_name;
    private String Departmant_Address;

    public Department(Integer id_Department, String department_name, String departmant_Address) {
        Id_Department = id_Department;
        Department_name = department_name;
        Departmant_Address = departmant_Address;
    }

    public Integer getId_Department() {
        return Id_Department;
    }

    public void setId_Department(Integer id_Department) {
        Id_Department = id_Department;
    }

    public String getDepartment_name() {
        return Department_name;
    }

    public void setDepartment_name(String department_name) {
        Department_name = department_name;
    }

    public String getDepartmant_Address() {
        return Departmant_Address;
    }

    public void setDepartmant_Address(String departmant_Address) {
        Departmant_Address = departmant_Address;
    }

    @Override
    public String toString() {
        return "Department{" +
                "Id_Department=" + Id_Department +
                ", Department_name='" + Department_name + '\'' +
                ", Departmant_Address='" + Departmant_Address + '\'' +
                '}';
    }
}
