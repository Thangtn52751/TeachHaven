package dev.nes24.android.techhaven_ph52634_ph52751.Fragment;

import android.content.DialogInterface;
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

import dev.nes24.android.techhaven_ph52634_ph52751.Adapter.EmpolyeeAdapter;
import dev.nes24.android.techhaven_ph52634_ph52751.DAO.Department_DAO;
import dev.nes24.android.techhaven_ph52634_ph52751.DAO.Empolyee_DAO;
import dev.nes24.android.techhaven_ph52634_ph52751.Model.Department;
import dev.nes24.android.techhaven_ph52634_ph52751.Model.Empolyee;
import dev.nes24.android.techhaven_ph52634_ph52751.NavigationActivity;
import dev.nes24.android.techhaven_ph52634_ph52751.NotificationHelper.NotificationHelperAdd;
import dev.nes24.android.techhaven_ph52634_ph52751.R;

public class Frag_Empolyee extends Fragment {
    RecyclerView rc_Category;
    Button btn_add, btn_sort;
    EmpolyeeAdapter adapter;
    Empolyee_DAO dao;
    ArrayList<Empolyee> list= new ArrayList<>();
    Department_DAO departmentDao;
    NavigationActivity mainActivity;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.frag_nhanvien, container, false);

        rc_Category = view.findViewById(R.id.rc_Category);
        btn_add = view.findViewById(R.id.btn_addEmployee);
        btn_sort = view.findViewById(R.id.btn_sortEmployee);
        dao = new Empolyee_DAO(getContext());

        mainActivity = (NavigationActivity) getActivity();
        String keyword = mainActivity.getMemail();

        list = dao.getListEmpolyee(keyword);
        adapter = new EmpolyeeAdapter(getContext(), list);
        departmentDao = new Department_DAO(getContext());

        btn_add.setOnClickListener(v -> dialogAddEmployee());
        btn_sort.setOnClickListener(v -> sortEmployees());
        rc_Category.setAdapter(adapter);

        return view;
    }


    private void dialogAddEmployee() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        View view = getLayoutInflater().inflate(R.layout.dialog_empolyee_add, null);

        EditText edt_employeeID_add = view.findViewById(R.id.edt_employeeID_add);
        EditText edt_employeeName_add = view.findViewById(R.id.edt_employeeName_add);
        EditText edt_Employee_MiddleName_add = view.findViewById(R.id.edt_Employee_MiddleName_add);
        EditText edt_emplyeepn_add = view.findViewById(R.id.edt_emplyeepn_add);
        EditText edt_employeeEmail_add = view.findViewById(R.id.edt_employeeEmail_add);
        EditText edt_employeeDepartmentId_add = view.findViewById(R.id.edt_employeeDepartmentId_add);
        EditText edt_password = view.findViewById(R.id.edt_password);
        EditText edt_Empolyee_Salary = view.findViewById(R.id.edt_Empolyee_Salary);

        edt_employeeDepartmentId_add.setOnClickListener(v -> {
            AlertDialog.Builder builder1 = new AlertDialog.Builder(getContext());
            ArrayList<Department> listDepartment = departmentDao.getAllDepartment("");

            ArrayList<String> list1 = new ArrayList<>();
            for (Department list : listDepartment){
                list1.add(String.valueOf(list.getId_Department()));
            }
            String[] departmentIds = list1.toArray(new String[0]);

            builder1.setItems(departmentIds, (dialog, which) -> {
                edt_employeeDepartmentId_add.setText(departmentIds[which]);

            });

            builder1.show();
        });

        Button add_Employee = view.findViewById(R.id.add_Employee);
        Button btn_back_add = view.findViewById(R.id.btn_back_add);
        builder.setView(view);

        AlertDialog dialog = builder.create();
        dialog.setCanceledOnTouchOutside(false);

        add_Employee.setOnClickListener(v -> {
           String id = edt_employeeID_add.getText().toString().trim();
           String name = edt_employeeName_add.getText().toString().trim();
           String middlename = edt_Employee_MiddleName_add.getText().toString().trim();
           String phone = edt_emplyeepn_add.getText().toString().trim();
           String email = edt_employeeEmail_add.getText().toString().trim();
           String StrDepartmentId = edt_employeeDepartmentId_add.getText().toString().trim();
           String password = edt_password.getText().toString().trim();
           String Stsalary = edt_Empolyee_Salary.getText().toString().trim();


           if(id.isEmpty() || name.isEmpty() || middlename.isEmpty() || phone.isEmpty() || email.isEmpty() || StrDepartmentId.isEmpty() || password.isEmpty() || Stsalary.isEmpty()){
               if (id.isEmpty()){
                   edt_employeeID_add.setError("Nhập đầy đủ thông tin");
               }
               if (middlename.isEmpty()){
                   edt_Employee_MiddleName_add.setError("Nhập đầy đủ thông tin");
               }
               if (email.isEmpty()){
                   edt_employeeEmail_add.setError("Nhập đầy đủ thông tin");
               }
               if (name.isEmpty()){
                   edt_employeeName_add.setError("Nhập đầy đủ thông tin");
               }
               if (phone.isEmpty()){
                   edt_emplyeepn_add.setError("Nhập đầy đủ thông tin");
               }
               if (StrDepartmentId.isEmpty()){
                   edt_employeeDepartmentId_add.setError("Nhập đầy đủ thông tin");
               }
               if (password.isEmpty()){
                   edt_password.setError("Nhập đầy đủ thông tin");
               }
               if (Stsalary.isEmpty()){
                   edt_Empolyee_Salary.setError("Nhập đầy đủ thông tin");
               }
           } else if (dao.getOneEmployee(id) != null) {
               Toast.makeText(getContext(), "Mã nhân viên đã tồn tại", Toast.LENGTH_SHORT).show();
           } else if (!id.matches("^NV[0-9]{3,6}$")) {
               Toast.makeText(getContext(), "Mã nhân viên bắt đầu bằng NV", Toast.LENGTH_SHORT).show();
           }
           else if (!phone.matches("^0[0-9]{9,10}$")) {
               Toast.makeText(getContext(), "Số điện thoại bắt đầu bằng 0", Toast.LENGTH_SHORT).show();
           } else if (!email.matches("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$")) {
               Toast.makeText(getContext(), "Email không hợp lệ", Toast.LENGTH_SHORT).show();
           } else if (dao.isEmailExists(email)) {
               Toast.makeText(getContext(), "Email đã tồn tại", Toast.LENGTH_SHORT).show();
           } else if (dao.isPhoneExists(phone)) {
               Toast.makeText(getContext(), "Số điện thoại đã tồn tại", Toast.LENGTH_SHORT).show();
           } else {
               Integer DepartmentId = Integer.parseInt(StrDepartmentId);
               Integer salary = Integer.parseInt(Stsalary);
              boolean check = dao.addEmployee(new Empolyee(DepartmentId,id,name,password,middlename,phone,email, "NhanVien",salary ));
              if (check){
                  NotificationHelperAdd add = new NotificationHelperAdd(getContext());
                  add.showNotification();
                  Toast.makeText(getContext(), "Thêm nhân viên thành công", Toast.LENGTH_SHORT).show();
                  list.clear();
                  list.addAll(dao.getListEmpolyee(""));
                  adapter.notifyDataSetChanged();
                  dialog.dismiss();
              } else {
                  Toast.makeText(getContext(), "Thêm nhân viên thất bại", Toast.LENGTH_SHORT).show();
              }
           }
        });

        btn_back_add.setOnClickListener(v -> {
            dialog.dismiss();
        });



        dialog.show();
    }

    private void sortEmployees() {
        if (list.isEmpty()) {
            Toast.makeText(getContext(), "Danh sách nhân viên trống", Toast.LENGTH_SHORT).show();
            return;
        }

        Collections.sort(list, new Comparator<Empolyee>() {
            @Override
            public int compare(Empolyee e1, Empolyee e2) {
                return e1.getEmployee_Name().compareToIgnoreCase(e2.getEmployee_Name());
            }
        });


        adapter.notifyDataSetChanged();
        Toast.makeText(getContext(), "Danh sách nhân viên đã được sắp xếp từ A-Z", Toast.LENGTH_SHORT).show();
    }



}
