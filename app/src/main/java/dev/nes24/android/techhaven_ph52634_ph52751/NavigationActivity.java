package dev.nes24.android.techhaven_ph52634_ph52751;

import android.Manifest;
import android.app.Application;
import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentContainerView;
import androidx.fragment.app.FragmentManager;

import com.google.android.material.navigation.NavigationView;

import dev.nes24.android.techhaven_ph52634_ph52751.Adapter.DepartmentAdapter;
import dev.nes24.android.techhaven_ph52634_ph52751.Adapter.EmpolyeeAdapter;
import dev.nes24.android.techhaven_ph52634_ph52751.DAO.Empolyee_DAO;
import dev.nes24.android.techhaven_ph52634_ph52751.Fragment.Frag_Empolyee;
import dev.nes24.android.techhaven_ph52634_ph52751.Fragment.Frag_home;
import dev.nes24.android.techhaven_ph52634_ph52751.Model.Empolyee;

public class NavigationActivity extends AppCompatActivity {
    Toolbar toolbar;
    DrawerLayout drawerLayout;
    NavigationView nav;
    FragmentContainerView fragmentContainer;
    FragmentManager fm;
    ImageView search;
    EditText edt_search;
    Empolyee_DAO dao;


    public String getMemail() {
        return memail;
    }

    String memail;

    private static final int FRAG_HOME = 0;
    private static final int FRAG_EMPOLYEE = 1;
    private static final int Frag_Tax = 2;

    private int mCurrenFragMent = FRAG_HOME;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_navigation);

        getUi();

        setSupportActionBar(toolbar);

        ActionBarDrawerToggle drawerToggle = new ActionBarDrawerToggle(this
                , drawerLayout
                ,toolbar
                ,R.string.Open_Nav
                ,R.string.Close_Nav);

        drawerToggle.setDrawerIndicatorEnabled(true);
        drawerToggle.syncState();
        drawerLayout.addDrawerListener(drawerToggle);
        setFragment(new Frag_home());

        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.POST_NOTIFICATIONS}, 9999);

        listenerEdt_Search();
        search.setOnClickListener(v -> clickSearch());
        nav_Listener();
    }

    private void clickSearch() {
        String email = edt_search.getText().toString().trim();
        memail = email;
        if (mCurrenFragMent == FRAG_EMPOLYEE){
            setFragment(new Frag_Empolyee());

        }
        else if(mCurrenFragMent == FRAG_HOME){
            setFragment(new Frag_home());

        }
    }

    public void listenerEdt_Search (){
        edt_search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() > 0){
                    getResources().getDrawable(R.drawable.cancel);
                }else {
                    edt_search.setCompoundDrawablesRelativeWithIntrinsicBounds(null , null, null, null);
                    memail = "";
                    if (mCurrenFragMent == FRAG_EMPOLYEE){
                        setFragment(new Frag_Empolyee());
                    }
                    else if(mCurrenFragMent == FRAG_HOME){
                        setFragment(new Frag_home());
                    }
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }


    public void getUi(){
        toolbar = findViewById(R.id.Toolbar);
        drawerLayout = findViewById(R.id.DrawerLayout);
        nav = findViewById(R.id.Nav_View);
        fragmentContainer = findViewById(R.id.FragmentContainerView);
        fm = getSupportFragmentManager();
        dao = new Empolyee_DAO(NavigationActivity.this);
        search = toolbar.findViewById(R.id.search);
        edt_search = toolbar.findViewById(R.id.edt_Search);
    }

    public void nav_Listener(){
        nav.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                if (item.getItemId() == R.id.QlPhongBan_nav){
                    if (mCurrenFragMent != FRAG_HOME){
                        setFragment(new Frag_home());
                        mCurrenFragMent = FRAG_HOME;
                    }
                }
                else if (item.getItemId() == R.id.QlNhanVien_nav){
                    if (mCurrenFragMent != FRAG_EMPOLYEE){
                        setFragment(new Frag_Empolyee());
                        mCurrenFragMent = FRAG_EMPOLYEE;
                    }
                }

                else if (item.getItemId() == R.id.Logout_nav){
                    startActivities(new Intent[]{new Intent(NavigationActivity.this, LoginActivity.class)});
                    finishAffinity();
                }
                else if (item.getItemId() == R.id.UpdatePassWord){
                    showDialogUpdatePassWord();
                }
                drawerLayout.close();
                return true;
            }
        });
    }

    public void setFragment(Fragment fragment){
        fm.beginTransaction().replace(R.id.FragmentContainerView, fragment).commit();
    }

    public void showDialogUpdatePassWord(){
        SharedPreferences sharedPreferences = getSharedPreferences("ThongTin", MODE_PRIVATE);
        String maNv = sharedPreferences.getString("MaNv", "");
        Empolyee empolyee = dao.getOneEmployee(maNv);

        if (empolyee != null){
            View view = LayoutInflater.from(this).inflate(R.layout.dialog_update_password, null, false);
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setView(view);

            EditText edt_oldPass = view.findViewById(R.id.oldPassword);

            EditText edt_newPass = view.findViewById(R.id.newPasswword);
            EditText edt_newPass2 = view.findViewById(R.id.newPasswword2);

            Button btnUpdate = view.findViewById(R.id.btn_update);
            Button btn_Canel = view.findViewById(R.id.btn_Canel);

            builder.setView(view);

            Dialog dialog = builder.create();
            dialog.setCanceledOnTouchOutside(false);

            btnUpdate.setOnClickListener(v -> {
                String oldPassword = edt_oldPass.getText().toString().trim();
                String newPassword = edt_newPass.getText().toString().trim();
                String newPassword2 = edt_newPass2.getText().toString().trim();
                if (oldPassword.isEmpty() || newPassword.isEmpty() || newPassword2.isEmpty()){
                    if (oldPassword.isEmpty()){
                        edt_oldPass.setError("Chưa nhập password cũ");
                    }
                    if (newPassword.isEmpty()){
                        edt_newPass.setError("Chưa nhập password mới");
                    }
                    if (newPassword2.isEmpty()){
                        edt_newPass2.setError("Chưa nhập lại password");
                    }
                }else {
                    if (!newPassword.equals(newPassword2)){
                        Toast.makeText(NavigationActivity.this, "Pass không trùng nhau", Toast.LENGTH_SHORT).show();
                    }
                    else {
                        boolean check = dao.updateEmployee(new Empolyee(empolyee.getId_Department(), empolyee.getId_Employee(), empolyee.getEmployee_Name(),newPassword, empolyee.getEmployee_MiddleName(), empolyee.getEmployee_Phone(), empolyee.getEmpolyee_Email(), empolyee.getEmployee_Duty(), empolyee.getEmpolyee_Salary()), empolyee.getId_Employee());
                        if (check){
                            Toast.makeText(NavigationActivity.this, "Cập nhập mật khẩu thành công", Toast.LENGTH_SHORT).show();
                            dialog.dismiss();
                        }else {
                            Toast.makeText(NavigationActivity.this, "Mật khẩu cũ không đúng", Toast.LENGTH_SHORT).show();
                        }
                    }
                }

            });
            btn_Canel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();
                }
            });
            dialog.show();
        }

    }
}