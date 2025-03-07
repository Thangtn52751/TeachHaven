package dev.nes24.android.techhaven_ph52634_ph52751;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import dev.nes24.android.techhaven_ph52634_ph52751.DAO.Empolyee_DAO;
import dev.nes24.android.techhaven_ph52634_ph52751.Model.Empolyee;

public class SignupActivity extends AppCompatActivity {
    TextView tv_DangNhap;
    EditText edt_user, edt_pass, edt_Confimpass, edt_Employee_MiddleName, edt_Name;
    Empolyee_DAO empolyeeDao;
    Button btn_SignUp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_signup);

        getUi();
        clickTv_DangNhap();
        click_Listener();
    }

    public void getUi(){
        tv_DangNhap = findViewById(R.id.tv_DangNhap);
        edt_user = findViewById(R.id.edt_username);
        edt_pass = findViewById(R.id.edt_password);
        btn_SignUp = findViewById(R.id.btn_Sign_Up);
        edt_Confimpass = findViewById(R.id.edt_ConfirmPassword);
        edt_Employee_MiddleName = findViewById(R.id.edt_Employee_MiddleName);
        edt_Name = findViewById(R.id.edt_Name);
        empolyeeDao = new Empolyee_DAO(this);
    }

    public void clickTv_DangNhap(){
        tv_DangNhap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SignupActivity.this, LoginActivity.class));
            }
        });
    }

    public void click_Listener(){
        btn_SignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addNhanVien();
            }
        });
    }

    public void addNhanVien(){
        String maNv = edt_user.getText().toString().trim();
        String passWord = edt_pass.getText().toString().trim();
        String confimPass = edt_Confimpass.getText().toString().trim();
        String Employee_MiddleName = edt_Employee_MiddleName.getText().toString().trim();
        String Name = edt_Name.getText().toString().trim();
        if (Employee_MiddleName.isEmpty() || Name.isEmpty() || maNv.isEmpty() || passWord.isEmpty() || confimPass.isEmpty()){
            if (Employee_MiddleName.isEmpty()){
                edt_Employee_MiddleName.setError("Bạn chưa nhập họ đệm");
                edt_Employee_MiddleName.requestFocus();
            }
            if (Name.isEmpty()){
                edt_Name.setError("Bạn chưa nhập tên");
            }
            if (maNv.isEmpty()){
                edt_user.setError("Bạn chưa nhập mã nhân viên");
                edt_user.requestFocus();
            }
            if (passWord.isEmpty()){
                edt_pass.setError("Bạn chưa nhập mật khẩu");
                edt_pass.requestFocus();
            }
            if (confimPass.isEmpty()){
                edt_Confimpass.setError("Bạn chưa nhập mật khẩu");
                edt_Confimpass.requestFocus();
            }
        }else{
            if (!maNv.matches("^NV[0-9]{3,6}$")){
                edt_user.setError("Mã nhân bắt đầu bằng NV");
            }
            if (!passWord.matches("^.*[A-Z]+.*$")){
                edt_pass.setError("Mật khẩu phải có ít nhất 1 chữ cái in hoa");
            } else if (!passWord.matches("^.*[0-9]+.*$")) {
                edt_pass.setError("Mật khẩu phải có ít nhất 1 số");
            }else if (!passWord.matches("^.*[!@#$&*]+.*$")){
                edt_pass.setError("Mật khẩu phải có ít nhất 1 ký tự đặc biệt");
            }else if (passWord.length()<6){
                edt_pass.setError("Mật khẩu phải có ít nhất 6 ký tự");
            }else {
                if(!passWord.equals(confimPass)){
                    edt_Confimpass.setError("Mật khẩu không trùng khớp");
                }else {
                    boolean check = empolyeeDao.addEmployee(new Empolyee(1,maNv, Name, passWord, Employee_MiddleName,null, null, "NhanVien", 0));
                    if (check) {
                        Toast.makeText(this, "Đăng ký thành công", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(SignupActivity.this, LoginActivity.class));
                    }else {
                        Toast.makeText(this, "Đăng ký thất bại", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        }
    }
}