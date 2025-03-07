package dev.nes24.android.techhaven_ph52634_ph52751.Adapter;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.ramotion.foldingcell.FoldingCell;

import java.util.ArrayList;

import dev.nes24.android.techhaven_ph52634_ph52751.DAO.Department_DAO;
import dev.nes24.android.techhaven_ph52634_ph52751.DAO.Empolyee_DAO;
import dev.nes24.android.techhaven_ph52634_ph52751.Model.Department;
import dev.nes24.android.techhaven_ph52634_ph52751.Model.Empolyee;
import dev.nes24.android.techhaven_ph52634_ph52751.NotificationHelper.NotificationHelperDelete;
import dev.nes24.android.techhaven_ph52634_ph52751.NotificationHelper.NotificationHelperUpdate;
import dev.nes24.android.techhaven_ph52634_ph52751.R;

public class EmpolyeeAdapter extends RecyclerView.Adapter<EmpolyeeAdapter.EmpolyeeViewHoldel> {
    Context context;
    ArrayList<Empolyee> list;
    Empolyee_DAO dao;
    Department_DAO departmentDao;


    public EmpolyeeAdapter(Context context, ArrayList<Empolyee> list) {
        this.context = context;
        this.list = list;
        dao = new Empolyee_DAO(context);
    }

    @NonNull
    @Override
    public EmpolyeeViewHoldel onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_empolyee, parent, false);
        departmentDao = new Department_DAO(context);

        return new EmpolyeeViewHoldel(view);
    }

    @Override
    public void onBindViewHolder(@NonNull EmpolyeeViewHoldel holder, int position) {
        Empolyee empolyee = list.get(position);

        holder.tv_Empolyee_Id.setText("Mã nhân viên: "+empolyee.getId_Employee());
        holder.tv_Empolyee_Id_Content.setText("Mã: "+empolyee.getId_Employee());

        holder.tv_Empolyee_Name.setText("Họ và tên: "+empolyee.getEmployee_MiddleName() + " "+empolyee.getEmployee_Name());
        holder.tv_Empolyee_Name_Content.setText("Tên: "+empolyee.getEmployee_Name());

        holder.tv_Empolyee_ChucVu.setText("Chức vụ: "+empolyee.getEmployee_Duty());
        holder.tv_Empolyee_ChucVu_Content.setText("Chức vụ: "+empolyee.getEmployee_Duty());

        holder.tv_IdDepartment.setText("Mã văn phòng trực thuộc: "+empolyee.getId_Department() + "\n" +"Tên phòng ban: "+empolyee.getName_Department());
        holder.tv_Empolyee_Email.setText("Email: "+empolyee.getEmpolyee_Email());
        holder.tv_Empolyee_Middlename.setText("Họ đệm: "+empolyee.getEmployee_MiddleName());
        holder.tv_Empolyee_PhoneNumber.setText("SDT: "+empolyee.getEmployee_Phone());
        holder.tv_salary.setText("Lương: "+empolyee.getEmpolyee_Salary());

        SharedPreferences sharedPreferences = context.getSharedPreferences("ThongTin", Context.MODE_PRIVATE);
        String Duty = sharedPreferences.getString("Duty", "");

        if (Duty.equals("NhanVien")){
            holder.ic_update.setVisibility(View.GONE);
            holder.ic_delete.setVisibility(View.GONE);
        }else{
            holder.ic_update.setOnClickListener( v -> dialogUpdate(empolyee));
            holder.ic_delete.setOnClickListener(v -> dialogDelete(empolyee));
        }

        holder.foldingCell.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                holder.foldingCell.toggle(false);
            }
        });

        holder.ic_update.setOnClickListener( v -> dialogUpdate(empolyee));
        holder.ic_delete.setOnClickListener(v -> dialogDelete(empolyee));
    }




    @Override
    public int getItemCount() {
        return list.size();
    }

    public class EmpolyeeViewHoldel extends RecyclerView.ViewHolder {
        private FoldingCell foldingCell;
        private TextView tv_Empolyee_Name;
        private TextView tv_Empolyee_Name_Content;

        private TextView tv_Empolyee_ChucVu;
        private TextView tv_Empolyee_ChucVu_Content;

        private TextView tv_Empolyee_Id;
        private TextView tv_Empolyee_Id_Content;

        private TextView tv_Empolyee_PhoneNumber;
        private TextView tv_Empolyee_Email;
        private TextView tv_Empolyee_Middlename;

        private ImageView ic_update, ic_delete,Empolyee_img;;

        private TextView tv_IdDepartment, tv_salary;
        public EmpolyeeViewHoldel(@NonNull View itemView) {
            super(itemView);
            foldingCell = itemView.findViewById(R.id.folding_cell);

            tv_Empolyee_Name = itemView.findViewById(R.id.Empolyee_name);
            tv_Empolyee_Name_Content = itemView.findViewById(R.id.Empolyee_Name_Content);
            tv_Empolyee_Id = itemView.findViewById(R.id.Empolyee_Id);
            tv_Empolyee_Id_Content = itemView.findViewById(R.id.Empolyee_Id_Content);
            tv_Empolyee_ChucVu = itemView.findViewById(R.id.Empolyee_ChucVu);
            tv_Empolyee_ChucVu_Content = itemView.findViewById(R.id.Empolyee_ChucVu_Content);

            tv_Empolyee_Middlename = itemView.findViewById(R.id.Empolyee_Middlename);
            tv_Empolyee_PhoneNumber = itemView.findViewById(R.id.Empolyee_PhoneNumber);
            tv_Empolyee_Email = itemView.findViewById(R.id.Empolyee_Email);
            tv_IdDepartment = itemView.findViewById(R.id.Empolyee_Id_Department);
            tv_salary = itemView.findViewById(R.id.tv_Empolyee_salary);

            Empolyee_img = itemView.findViewById(R.id.Empolyee_img);

            ic_update = itemView.findViewById(R.id.ic_update);
            ic_delete = itemView.findViewById(R.id.ic_delete);
        }

    }

    private void dialogUpdate(Empolyee empolyee) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        View view = LayoutInflater.from(context).inflate(R.layout.update_empolyee, null, false);

        EditText edt_employeeID_add = view.findViewById(R.id.edt_employeeID_add);
        EditText edt_employeeName_add = view.findViewById(R.id.edt_employeeName_add);
        EditText edt_Employee_MiddleName_add = view.findViewById(R.id.edt_Employee_MiddleName_add);
        EditText edt_emplyeepn_add = view.findViewById(R.id.edt_emplyeepn_add);
        EditText edt_employeeEmail_add = view.findViewById(R.id.edt_employeeEmail_add);
        EditText edt_employeeDepartmentId_add = view.findViewById(R.id.edt_employeeDepartmentId_add);
        EditText edt_password = view.findViewById(R.id.edt_password);
        EditText edt_Salary = view.findViewById(R.id.edt_Empolyee_Salary);

        edt_employeeID_add.setText(empolyee.getId_Employee());
        edt_employeeDepartmentId_add.setText(""+empolyee.getId_Department());
        edt_employeeName_add.setText(empolyee.getEmployee_Name());
        edt_password.setText(empolyee.getEmployee_password());
        edt_Employee_MiddleName_add.setText(empolyee.getEmployee_MiddleName());
        edt_emplyeepn_add.setText(empolyee.getEmployee_Phone());
        edt_employeeEmail_add.setText(empolyee.getEmpolyee_Email());
        edt_Salary.setText(""+empolyee.getEmpolyee_Salary());

        edt_employeeDepartmentId_add.setOnClickListener(v -> {
            AlertDialog.Builder builder1 = new AlertDialog.Builder(context);
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

        Button btn_update = view.findViewById(R.id.btn_Update_Empolyee);
        Button btn_Canel = view.findViewById(R.id.btn_back_add);

        builder.setView(view);
        Dialog dialog = builder.create();
        dialog.setCanceledOnTouchOutside(false);

        btn_update.setOnClickListener(v -> {
            String id = edt_employeeID_add.getText().toString().trim();
            String name = edt_employeeName_add.getText().toString().trim();
            String middlename = edt_Employee_MiddleName_add.getText().toString().trim();
            String phone = edt_emplyeepn_add.getText().toString().trim();
            String email = edt_employeeEmail_add.getText().toString().trim();
            String StrDepartmentId = edt_employeeDepartmentId_add.getText().toString().trim();
            String password = edt_password.getText().toString().trim();
            String Strsalary = edt_Salary.getText().toString().trim();



            if(id.isEmpty() || name.isEmpty() || middlename.isEmpty() || phone.isEmpty() || email.isEmpty() || StrDepartmentId.isEmpty() || password.isEmpty() || Strsalary.isEmpty()){
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
                if (Strsalary.isEmpty()){
                    edt_Salary.setError("Nhập đầy đủ thông tin");
                }
            }
            else if (!phone.matches("^0[0-9]{9,10}$")) {
                Toast.makeText(context, "Số điện thoại bắt đầu bằng 0", Toast.LENGTH_SHORT).show();
            } else if (!email.matches("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$")) {
                Toast.makeText(context, "Email không hợp lệ", Toast.LENGTH_SHORT).show();
            } else {
                Integer salary = Integer.valueOf(Strsalary);
                Integer DepartmentId = Integer.parseInt(StrDepartmentId);
                boolean check = dao.updateEmployee(new Empolyee(DepartmentId, id, name, password, middlename, phone, email, "NhanVien", salary),empolyee.getId_Employee() );
                if (check){
                    NotificationHelperUpdate update = new NotificationHelperUpdate(context);
                    update.showNotification();
                    Toast.makeText(context, "Chỉnh sửa nhân viên thành công", Toast.LENGTH_SHORT).show();
                    list.clear();
                    list.addAll(dao.getListEmpolyee(""));
                    notifyDataSetChanged();
                    dialog.dismiss();
                } else {
                    Toast.makeText(context, "Chỉnh sửa viên thất bại", Toast.LENGTH_SHORT).show();
                    dialog.dismiss();
                }
            }
        });

        btn_Canel.setOnClickListener(v -> {
            dialog.dismiss();
        });
        dialog.show();
    }

    private void dialogDelete(Empolyee empolyee) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Bạn chắc không?");
        builder.setMessage("Bạn chắc bạn muốn xóa nhân viên này?");
        builder.setIcon(R.drawable.ic_delete);

        builder.setPositiveButton("Có", (dialog, which) -> {
            if (dao.deleteEmployee(empolyee.getId_Employee())) {
                NotificationHelperDelete delete = new NotificationHelperDelete(context);
                delete.showNotification();
                Toast.makeText(context, "Xóa thành công!", Toast.LENGTH_SHORT).show();
                list.remove(empolyee);
                notifyDataSetChanged();
            } else  {
                Toast.makeText(context, "Xóa thất bại!", Toast.LENGTH_SHORT).show();
            }
            }).setNegativeButton("Thoát", (dialog, which) -> {

            });
        builder.show();

        }
    }



