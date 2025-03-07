package dev.nes24.android.techhaven_ph52634_ph52751.Adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import dev.nes24.android.techhaven_ph52634_ph52751.DAO.Department_DAO;
import dev.nes24.android.techhaven_ph52634_ph52751.Model.Department;
import dev.nes24.android.techhaven_ph52634_ph52751.NotificationHelper.NotificationHelperDelete;
import dev.nes24.android.techhaven_ph52634_ph52751.NotificationHelper.NotificationHelperUpdate;
import dev.nes24.android.techhaven_ph52634_ph52751.R;

public class DepartmentAdapter extends RecyclerView.Adapter<DepartmentAdapter.ViewHolder> {
    private ArrayList<Department> listDP;
    private Context context;
    private Department_DAO dao;

    public DepartmentAdapter(ArrayList<Department> listDP, Context context) {
        this.listDP = listDP;
        this.context = context;
        this.dao = new Department_DAO(context);
    }

    @NonNull
    @Override
    public DepartmentAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.viewholder_departmentitem, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DepartmentAdapter.ViewHolder holder, int position) {
        Department dp = listDP.get(position);

        holder.img_depart.setImageResource(R.drawable.mall);
        holder.tv_name.setText(dp.getDepartment_name());
        holder.tv_address.setText(dp.getDepartmant_Address());

        SharedPreferences sharedPreferences = context.getSharedPreferences("ThongTin", Context.MODE_PRIVATE);
        String Duty = sharedPreferences.getString("Duty", "");

        if (Duty.equals("NhanVien")){
            holder.iv_delete.setVisibility(View.GONE);
            holder.iv_edit.setVisibility(View.GONE);
        }else{
            holder.iv_delete.setOnClickListener(v -> dialogDelete(dp));
            holder.iv_edit.setOnClickListener(v -> dialogEdit(dp));
        }

    }

    @Override
    public int getItemCount() {
        return listDP.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView img_depart, iv_delete, iv_edit;
        TextView tv_name, tv_address;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            img_depart = itemView.findViewById(R.id.pic);
            tv_name = itemView.findViewById(R.id.titleTxt);
            tv_address = itemView.findViewById(R.id.tv_address);
            iv_delete = itemView.findViewById(R.id.ic_delete);
            iv_edit = itemView.findViewById(R.id.ic_edit);
        }
    }

    private void dialogDelete(Department dp) {
        new AlertDialog.Builder(context)
                .setTitle("Xóa phòng ban")
                .setMessage("Bạn có muốn xóa phòng ban này?")
                .setIcon(R.drawable.ic_delete)
                .setPositiveButton("Có", (dialog, which) -> {
                    boolean check = dao.deleteDepartment(dp.getId_Department());
                    if (check) {
                        NotificationHelperDelete delete = new NotificationHelperDelete(context);
                        delete.showNotification();
                        Toast.makeText(context, "Xóa thành công", Toast.LENGTH_SHORT).show();
                        int position = listDP.indexOf(dp);
                        listDP.remove(dp);
                        notifyItemRemoved(position);
                    } else {
                        Toast.makeText(context, "Xóa thất bại", Toast.LENGTH_SHORT).show();
                    }
                })
                .setNegativeButton("Không", (dialog, which) -> dialog.dismiss())
                .show();
    }

    private void dialogEdit(Department dp) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        View view = LayoutInflater.from(context).inflate(R.layout.viewholder_department_update_dialog, null);
        EditText edt_departmentName = view.findViewById(R.id.edt_departmentName);
        EditText edt_departmentAddress = view.findViewById(R.id.edt_departmentAddress);
        Button btn_Update_Department = view.findViewById(R.id.btn_Update_Department);
        Button btn_Canel = view.findViewById(R.id.btn_Canel);
        builder.setView(view);

        btn_Update_Department.setOnClickListener(v -> {
            String name = edt_departmentName.getText().toString().trim();
            String address = edt_departmentAddress.getText().toString().trim();

            if(name.isEmpty() || address.isEmpty()){
                Toast.makeText(context, "Không được để trống", Toast.LENGTH_SHORT).show();
            } else if (name.equals(dp.getDepartment_name()) && address.equals(dp.getDepartmant_Address())) {
                Toast.makeText(context, "Không có thay đổi", Toast.LENGTH_SHORT).show();
            }else if (name.equals(dp.getDepartment_name())) {
                Toast.makeText(context, "Không có thay đổi", Toast.LENGTH_SHORT).show();
            } else {
                long result = dao.updateDepartment(new Department(dp.getId_Department(), name, address));
                if (result > 0) {
                    NotificationHelperUpdate update = new NotificationHelperUpdate(context);
                    update.showNotification();
                    Toast.makeText(context, "Cập nhật thành công", Toast.LENGTH_SHORT).show();
                    dp.setDepartment_name(name);
                    dp.setDepartmant_Address(address);
                    notifyDataSetChanged();
                }else {
                    Toast.makeText(context, "Cập nhật thất bại", Toast.LENGTH_SHORT).show();
                    Log.e("Error", "Cập nhật thất bại");
                }
            }


        });

        btn_Canel.setOnClickListener(v -> {});

        AlertDialog dialog = builder.create();
        dialog.show();
    }
}
