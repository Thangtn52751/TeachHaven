package dev.nes24.android.techhaven_ph52634_ph52751;

import android.app.NotificationManager;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import dev.nes24.android.techhaven_ph52634_ph52751.Adapter.DepartmentAdapter;
import dev.nes24.android.techhaven_ph52634_ph52751.DAO.Department_DAO;
import dev.nes24.android.techhaven_ph52634_ph52751.Model.Department;
import dev.nes24.android.techhaven_ph52634_ph52751.NotificationHelper.NotificationHelperAdd;

public class HomeActivity extends AppCompatActivity {
    private RecyclerView rc_Category;
    private DepartmentAdapter adapter;
    private ArrayList<Department> listDP;
    private Department_DAO dao;
    private Button btn_add;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        getUI();

        listDP = new ArrayList<>();
        adapter = new DepartmentAdapter(listDP,this);
        rc_Category.setAdapter(adapter);

        dao = new Department_DAO(this);

        btn_add.setOnClickListener(v -> dialogAdd());
    }

    private void getUI() {
        rc_Category = findViewById(R.id.rc_Category);
        btn_add = findViewById(R.id.btn_add);
    }

    private void dialogAdd() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        View view = getLayoutInflater().inflate(R.layout.dialog_department_add, null);
        Button add_Department = view.findViewById(R.id.add_Department);
        Button back_Department = view.findViewById(R.id.btn_back_add);
        EditText edt_departmentName_add = view.findViewById(R.id.edt_departmentName_add);
        EditText edt_departmentAddress_add = view.findViewById(R.id.edt_departmentAddress_add);
        builder.setView(view);
        add_Department.setOnClickListener(v -> {
            String name = edt_departmentName_add.getText().toString().trim();
            String address = edt_departmentAddress_add.getText().toString().trim();

            if (name.isEmpty() || address.isEmpty()) {
                Toast.makeText(this, "Không được để trống", Toast.LENGTH_SHORT).show();
            } else {
                long result = dao.addDepartment(new Department(null, name, address));
                if (result > 0) {
                    NotificationHelperAdd add = new NotificationHelperAdd(this);
                    add.showNotification();
                    Toast.makeText(this, "Thêm thành công", Toast.LENGTH_SHORT).show();
                    listDP.add(new Department((int) result, name, address));
                    adapter.notifyDataSetChanged();
                } else {
                    Toast.makeText(this, "Thêm thất bại", Toast.LENGTH_SHORT).show();
                }
            }
        });

        back_Department.setOnClickListener(v -> {
        });

        AlertDialog dialog = builder.create();
        dialog.show();
    }

}
