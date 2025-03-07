package dev.nes24.android.techhaven_ph52634_ph52751.DbHelper;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DbSQLite extends SQLiteOpenHelper {

    public DbSQLite(Context context) {

        // Tạo cơ sở dữ liệu
        super(context, "QLNV", null, 7);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        // Tạo bảng Department, Employee
        String CtTbDepartment = "CREATE TABLE Department (Id_Department INTEGER PRIMARY KEY AUTOINCREMENT, Department_name TEXT NOT NULL, Departmant_Address TEXT NOT NULL)";
        db.execSQL(CtTbDepartment);

        String Insert = "INSERT INTO Department(Id_Department, Department_name, Departmant_Address) VALUES ('1', 'Văn phòng 1', 'Nam Từ Liêm - Hà Nội');";
        db.execSQL(Insert);

        String CtTbEmployee ="CREATE TABLE Employee (Id_Department INTEGER CONSTRAINT fk_1 REFERENCES Department (Id_Department), Id_Employee TEXT PRIMARY KEY , Employee_name TEXT NOT NULL, Employee_pass TEXT NOT NULL, Employee_middleName TEXT NOT NULL, Employee_Phone TEXT, Empolyee_Email TEXT, Employee_Duty TEXT NOT NULL DEFAULT 'NhanVien', Empolyee_Salary INTEGER NOT NULL)";
        db.execSQL(CtTbEmployee);

        String admin = "INSERT INTO Employee (Id_Department, Id_Employee, Employee_name, Employee_pass, Employee_middleName, Employee_Duty, Empolyee_Salary) VALUES ('1', '1', 'admin001', '1', 'Văn', 'Admin', 0), ('1', 'NV001', 'Nam', '1', 'Phạm Trọng', 'NhanVien', 2000000)";
        db.execSQL(admin);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        // Chạy laị hàm nếu đôi Version
        if (oldVersion != newVersion){
            db.execSQL("DROP TABLE IF EXISTS Department");
            db.execSQL("DROP TABLE IF EXISTS Employee");
            onCreate(db);
        }
    }
}
