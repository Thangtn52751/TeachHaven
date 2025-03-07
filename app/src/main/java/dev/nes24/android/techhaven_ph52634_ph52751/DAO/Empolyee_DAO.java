package dev.nes24.android.techhaven_ph52634_ph52751.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

import dev.nes24.android.techhaven_ph52634_ph52751.DbHelper.DbSQLite;
import dev.nes24.android.techhaven_ph52634_ph52751.Model.Empolyee;

public class Empolyee_DAO {
    private DbSQLite dbSQLite;
    private SQLiteDatabase sqLiteDatabase;

    public Empolyee_DAO(Context context) {
        dbSQLite = new DbSQLite(context);
        sqLiteDatabase = dbSQLite.getWritableDatabase();
    }

    // Retrieve a list of employees based on a keyword for filtering
    public ArrayList<Empolyee> getListEmpolyee(String keyWord) {
        ArrayList<Empolyee> list = new ArrayList<>();
        sqLiteDatabase = dbSQLite.getReadableDatabase();
        Cursor cursor = null;
        String SQL = "SELECT em.Id_Department, em.Id_Employee, em.Employee_name, em.Employee_pass, em.Employee_middleName, em.Employee_Phone, em.Empolyee_Email, em.Employee_Duty, em.Empolyee_Salary ,de.Department_name FROM Employee em, Department de WHERE em.Id_Department = de.Id_Department AND Employee_Duty = 'NhanVien'";

        if (keyWord != null && !keyWord.isEmpty()) {
            SQL += " AND (em.Employee_name LIKE ? OR em.Id_Employee LIKE ?)";
            cursor = sqLiteDatabase.rawQuery(SQL, new String[]{"%" + keyWord + "%", "%" + keyWord + "%"});
        } else {
            cursor = sqLiteDatabase.rawQuery(SQL, null);
        }

        if (cursor != null) {
            if (cursor.moveToFirst()) {
                do {
                    Integer idDepartment = cursor.getInt(0);
                    String idEmployee = cursor.getString(1);
                    String employeeName = cursor.getString(2);
                    String employeePass = cursor.getString(3);
                    String employeeMiddleName = cursor.getString(4);
                    String employeePhone = cursor.getString(5);
                    String employeeEmail = cursor.getString(6);
                    String employeeDuty = cursor.getString(7);
                    Integer empolyee_Salary = cursor.getInt(8);
                    String name_Department = cursor.getString(9);
                    list.add(new Empolyee(idDepartment, idEmployee, employeeName, employeePass, employeeMiddleName, employeePhone, employeeEmail, employeeDuty, empolyee_Salary, name_Department));
                } while (cursor.moveToNext());
            }
            cursor.close();
        }
        return list;
    }

    // Add a new employee to the database
    public boolean addEmployee(Empolyee employee) {
        ContentValues values = new ContentValues();
        values.put("Id_Department", employee.getId_Department());
        values.put("Id_Employee", employee.getId_Employee());
        values.put("Employee_name", employee.getEmployee_Name());
        values.put("Employee_pass", employee.getEmployee_password());
        values.put("Employee_middleName", employee.getEmployee_MiddleName());
        values.put("Employee_Phone", employee.getEmployee_Phone());
        values.put("Empolyee_Email", employee.getEmpolyee_Email());
        values.put("Employee_Duty", employee.getEmployee_Duty());
        values.put("Empolyee_Salary", employee.getEmpolyee_Salary());

        long result = sqLiteDatabase.insert("Employee", null, values);
        return result > 0;
    }

    // Update existing employee details
    public boolean updateEmployee(Empolyee employee, String oldId_empolyee) {
        ContentValues values = new ContentValues();
        values.put("Id_Department", employee.getId_Department());
        values.put("Id_Employee", employee.getId_Employee());
        values.put("Employee_name", employee.getEmployee_Name());
        values.put("Employee_pass", employee.getEmployee_password());
        values.put("Employee_middleName", employee.getEmployee_MiddleName());
        values.put("Employee_Phone", employee.getEmployee_Phone());
        values.put("Empolyee_Email", employee.getEmpolyee_Email());
        values.put("Employee_Duty", employee.getEmployee_Duty());
        values.put("Empolyee_Salary", employee.getEmpolyee_Salary());

        long result = sqLiteDatabase.update("Employee", values, "Id_Employee = ?", new String[]{oldId_empolyee});
        return result > 0;
    }

    // Delete an employee from the database
    public boolean deleteEmployee(String idEmployee) {
        long result = sqLiteDatabase.delete("Employee", "Id_Employee = ?", new String[]{idEmployee});
        return result > 0;
    }

    // Retrieve a single employee by ID
    public Empolyee getOneEmployee(String idEmployee) {
        sqLiteDatabase = dbSQLite.getReadableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery(
                "SELECT Id_Department, Id_Employee, Employee_name, Employee_pass, Employee_middleName, Employee_Phone, Empolyee_Email, Employee_Duty, Empolyee_Salary FROM Employee WHERE Id_Employee = ?",
                new String[]{idEmployee}
        );

        if (cursor != null) {
            if (cursor.moveToFirst()) {
                Integer idDepartment = cursor.getInt(0);
                String idEmp = cursor.getString(1);
                String employeeName = cursor.getString(2);
                String employeePass = cursor.getString(3);
                String employeeMiddleName = cursor.getString(4);
                String employeePhone = cursor.getString(5);
                String employeeEmail = cursor.getString(6);
                String employeeDuty = cursor.getString(7);
                Integer empolyee_Salary = cursor.getInt(8);
                cursor.close();
                return new Empolyee(idDepartment, idEmp, employeeName, employeePass, employeeMiddleName, employeePhone, employeeEmail, employeeDuty, empolyee_Salary);
            }
            cursor.close();
        }
        return null;
    }

    // Check if employee ID already exists
    public boolean isEmployeeIdExists(String employeeId) {
        Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM Employee WHERE Id_Employee = ?", new String[]{employeeId});
        boolean exists = cursor.getCount() > 0;
        cursor.close();
        return exists;
    }

    // Check if phone number already exists
    public boolean isPhoneExists(String phone) {
        Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM Employee WHERE Employee_Phone = ?", new String[]{phone});
        boolean exists = cursor.getCount() > 0;
        cursor.close();
        return exists;
    }

    // Check if email already exists
    public boolean isEmailExists(String email) {
        Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM Employee WHERE Empolyee_Email = ?", new String[]{email});
        boolean exists = cursor.getCount() > 0;
        cursor.close();
        return exists;
    }

}
