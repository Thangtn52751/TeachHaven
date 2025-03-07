package dev.nes24.android.techhaven_ph52634_ph52751.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

import dev.nes24.android.techhaven_ph52634_ph52751.DbHelper.DbSQLite;
import dev.nes24.android.techhaven_ph52634_ph52751.Model.Department;

public class Department_DAO {
    private SQLiteDatabase db;
    private final DbSQLite dbhelper;

    public Department_DAO(Context context) {
        dbhelper = new DbSQLite(context);
        db = dbhelper.getWritableDatabase();
    }

    public long addDepartment(Department dp) {
        ContentValues values = new ContentValues();
        values.put("Department_name", dp.getDepartment_name());
        values.put("Departmant_Address", dp.getDepartmant_Address());

        long result = db.insert("Department", null, values);
        return result < 0 ? -1 : result;
    }

    public long updateDepartment(Department dp) {
        ContentValues values = new ContentValues();
        values.put("Department_name", dp.getDepartment_name());
        values.put("Departmant_Address", dp.getDepartmant_Address());

        long result = db.update("Department", values, "Id_Department = ?", new String[]{String.valueOf(dp.getId_Department())});
        return result < 0 ? -1 : result;
    }

    public boolean deleteDepartment(int id) {
        long result = db.delete("Department", "Id_Department = ?", new String[]{String.valueOf(id)});
        return result >= 0;
    }

    public ArrayList<Department> getAllDepartment(String keyword) {
        ArrayList<Department> listDP = new ArrayList<>();
        Cursor cursor = null;
            String SQl = "SELECT Id_Department, Department_name, Departmant_Address FROM Department";
            db = dbhelper.getReadableDatabase();

            if (keyword == null){
                cursor = db.rawQuery(SQl, null);
            }else {
                SQl += " WHERE Department_name LIKE ? OR Departmant_Address Like ?";
                cursor = db.rawQuery(SQl, new String[]{String.valueOf("%"+keyword+"%")});
            }

           if (cursor != null){
               while (cursor.moveToNext()) {
                   Integer id = cursor.getInt(0);
                   String name = cursor.getString(1);
                   String address = cursor.getString(2);
                   listDP.add(new Department(id, name, address));
               }
           }

        return listDP;
    }
}
