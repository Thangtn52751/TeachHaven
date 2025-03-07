package dev.nes24.android.techhaven_ph52634_ph52751.Fragment;

import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import dev.nes24.android.techhaven_ph52634_ph52751.Adapter.DepartmentAdapter;
import dev.nes24.android.techhaven_ph52634_ph52751.DAO.Department_DAO;
import dev.nes24.android.techhaven_ph52634_ph52751.Model.Department;
import dev.nes24.android.techhaven_ph52634_ph52751.NavigationActivity;
import dev.nes24.android.techhaven_ph52634_ph52751.NotificationHelper.NotificationHelperAdd;
import dev.nes24.android.techhaven_ph52634_ph52751.R;

public class Frag_home extends Fragment {
    RecyclerView rc_Category;
    Button btn_add, btn_sort;
    ArrayList <Department> listDP = new ArrayList<>();
    DepartmentAdapter adapter;
    Department_DAO dao;
    NavigationActivity navigationActivity;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.activity_home, container, false);

        rc_Category = view.findViewById(R.id.rc_Category);
        btn_add = view.findViewById(R.id.btn_add);
        btn_sort = view.findViewById(R.id.btn_sortDepartment);
         navigationActivity = (NavigationActivity) getActivity();

         String keyword = navigationActivity.getMemail();

        dao = new Department_DAO(getContext());

        listDP = dao.getAllDepartment(keyword);

        adapter = new DepartmentAdapter(listDP,getContext());

        rc_Category.setAdapter(adapter);

        SharedPreferences sharedPreferences = getContext().getSharedPreferences("ThongTin", Context.MODE_PRIVATE);
        String Duty = sharedPreferences.getString("Duty", "");

        if (Duty.equals("NhanVien")){
            btn_add.setVisibility(View.GONE);
        }
        btn_add.setOnClickListener(v -> dialogAdd());
        btn_sort.setOnClickListener(v -> sortDepartment());
        return view;
    }




    private void dialogAdd() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        View view = getLayoutInflater().inflate(R.layout.dialog_department_add, null);
        Button add_Department = view.findViewById(R.id.add_Department);
        Button back_Department = view.findViewById(R.id.btn_back_add);
        EditText edt_departmentName_add = view.findViewById(R.id.edt_departmentName_add);
        EditText edt_departmentAddress_add = view.findViewById(R.id.edt_departmentAddress_add);
        builder.setView(view);
        Dialog dialog = builder.create();
        dialog.setCanceledOnTouchOutside(false);

        add_Department.setOnClickListener(v -> {
            String name = edt_departmentName_add.getText().toString().trim();
            String address = edt_departmentAddress_add.getText().toString().trim();

            if (name.isEmpty() || address.isEmpty()) {
                Toast.makeText(getContext(), "Không được để trống", Toast.LENGTH_SHORT).show();
            } else {
                long result = dao.addDepartment(new Department(null, name, address));
                if (result > 0) {
                    NotificationHelperAdd add = new NotificationHelperAdd(getContext());
                    add.showNotification();
                    Toast.makeText(getContext(), "Thêm thành công", Toast.LENGTH_SHORT).show();
                    listDP.add(new Department((int) result, name, address));
                    adapter.notifyDataSetChanged();
                    dialog.dismiss();

                } else {
                    Toast.makeText(getContext(), "Thêm thất bại", Toast.LENGTH_SHORT).show();
                }
            }
        });

        back_Department.setOnClickListener(v -> {
            dialog.dismiss();
        });


        dialog.show();
    }


    private void sortDepartment() {
        if (listDP.isEmpty()) {
            Toast.makeText(getContext(), "Danh sách phòng ban trống", Toast.LENGTH_SHORT).show();
            return;
        }
        Collections.sort(listDP, new Comparator<Department>() {
            @Override
            public int compare(Department o1, Department o2) {
                return o1.getDepartment_name().compareToIgnoreCase(o2.getDepartment_name());
            }
        });

        adapter.notifyDataSetChanged();
        Toast.makeText(getContext(), "Danh sách nhân viên đã được sắp xếp từ A-Z", Toast.LENGTH_SHORT).show();
    }


}
