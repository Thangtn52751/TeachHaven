package dev.nes24.android.techhaven_ph52634_ph52751;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import dev.nes24.android.techhaven_ph52634_ph52751.DAO.Empolyee_DAO;
import dev.nes24.android.techhaven_ph52634_ph52751.Model.Empolyee;

public class LoginActivity extends AppCompatActivity {
    EditText edt_Username, edt_Password;
    Button btn_Login;
    TextView tv_ToSignUp;
    Empolyee_DAO empolyeeDao;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        //Hide action bar
        //Get UI elements
        getUI();
        //Set click event
        getListener();
    }

    // Get UI elements from layout
    private void getUI() {
        edt_Username = findViewById(R.id.edt_LoginUsername);
        edt_Password = findViewById(R.id.edt_LogPassword);
        btn_Login = findViewById(R.id.btn_Login);
        tv_ToSignUp = findViewById(R.id.tv_ToSignUp);

        empolyeeDao = new Empolyee_DAO(this);
    }

    public void getListener(){
        // thực hiện sự kiện click TextView Sign Up
        tv_ToSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this, SignupActivity.class));
            }
        });

        btn_Login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dangNhap();
            }
        });
    }

    public void dangNhap(){
        String edt_MaNv = edt_Username.getText().toString().trim();
        String edt_MatKhau = edt_Password.getText().toString().trim();

        Empolyee empolyee = empolyeeDao.getOneEmployee(edt_MaNv);
        if (empolyee == null){
            Toast.makeText(this, "Mã nhân viên không tồn tại", Toast.LENGTH_SHORT).show();
        }else {
            if (edt_MaNv.equals(empolyee.getId_Employee()) && edt_MatKhau.equals(empolyee.getEmployee_password())){
                Toast.makeText(this, "Đăng nhập thành công", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(LoginActivity.this, NavigationActivity.class));
                SharedPreferences sharedPreferences = getSharedPreferences("ThongTin", MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();

                editor.putString("Duty", empolyee.getEmployee_Duty()).commit();
                editor.putString("MaNv", empolyee.getId_Employee()).commit();
            }else{
                Toast.makeText(this, "Mã nhân viên hoặc password sai", Toast.LENGTH_SHORT).show();
            }
        }
    }
}