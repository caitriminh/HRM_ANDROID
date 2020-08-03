package com.example.HRM;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.SubMenu;
import android.view.Menu;

import com.example.HRM.ui.BaoHiem.BaoHiemFragment;
import com.example.HRM.ui.CongTac.CongTacFragment;
import com.example.HRM.ui.DiemDanh.DiemDanhFragment;
import com.example.HRM.ui.HopDongLaoDong.HopDongLaoDongFragment;
import com.example.HRM.ui.LenhTangCa.LenhTangCaFragment;
import com.example.HRM.ui.NhanVienTangCa.NhanVienTangCaFragment;
import com.example.HRM.ui.NhatKyQuetThe.NhatKyQuetTheFragment;
import com.example.HRM.ui.PhepNam.PhepNamFragment;
import com.example.HRM.ui.VeSom.VeSomFragment;
import com.example.HRM.ui.nghiphep.NghiPhepFragment;
import com.example.HRM.ui.nghiviec.NhanVienNghiViecFragment;
import com.example.HRM.ui.nhanvien.NhanVienFragment;
import com.example.HRM.ui.thaisan.NhanVienThaiSanFragment;
import com.google.android.material.navigation.NavigationView;

import androidx.annotation.NonNull;
import androidx.core.view.GravityCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class MainActivity extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;
    public static FragmentTransaction fragmentTransaction;
    public static Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(                R.id.nav_nhanvien, R.id.nav_nghiviec,  R.id.nav_nghiphep, R.id.nav_vesom, R.id.nav_congtac)
                .setDrawerLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
//                    case R.id.nav_setting:
//                        Intent intent = new Intent(MainActivity.this, SettingsActivity.class);
//                        startActivity(intent);
//                        break;
                    case R.id.nav_nhanvien:
                        NhanVienFragment fmNhanVien = new NhanVienFragment();
                        open_fragment(fmNhanVien);
                        break;
                    case R.id.nav_nghiviec:
                        NhanVienNghiViecFragment fmNhanVienNghiViec = new NhanVienNghiViecFragment();
                        open_fragment(fmNhanVienNghiViec);
                        break;
                    case R.id.nav_thaisan:
                        NhanVienThaiSanFragment fmNhanVienThaiSan = new NhanVienThaiSanFragment();
                        open_fragment(fmNhanVienThaiSan);
                        break;
                    case R.id.nav_nghiphep:
                        NghiPhepFragment frmNghiPhep = new NghiPhepFragment();
                        open_fragment(frmNghiPhep);
                        break;
                    case R.id.nav_vesom:
                        VeSomFragment frmVeSom = new VeSomFragment();
                        open_fragment(frmVeSom);
                        break;
                    case R.id.nav_congtac:
                        CongTacFragment frmCongTac = new CongTacFragment();
                        open_fragment(frmCongTac);
                        break;
                    case R.id.nav_nhatky_quetthe:
                        NhatKyQuetTheFragment frmNhatKyQuetThe = new NhatKyQuetTheFragment();
                        open_fragment(frmNhatKyQuetThe);
                        break;
                    case R.id.nav_tangca:
                        NhanVienTangCaFragment frmTangCa = new NhanVienTangCaFragment();
                        open_fragment(frmTangCa);
                        break;
                    case R.id.nav_hopdong_laodong:
                        HopDongLaoDongFragment frmHopDongLaoDong = new HopDongLaoDongFragment();
                        open_fragment(frmHopDongLaoDong);
                        break;
                    case R.id.nav_phepnam:
                        PhepNamFragment frmPhepNam = new PhepNamFragment();
                        open_fragment(frmPhepNam);
                        break;
                    case R.id.nav_lenhtangca:
                        LenhTangCaFragment frmLenhTangCa = new LenhTangCaFragment();
                        open_fragment(frmLenhTangCa);
                        break;
                    case R.id.nav_baohiem:
                        BaoHiemFragment frmBaoHiem = new BaoHiemFragment();
                        open_fragment(frmBaoHiem);
                        break;
                    case R.id.nav_diemdanh:
                        DiemDanhFragment frmDiemDanh= new DiemDanhFragment();
                        open_fragment(frmDiemDanh);
                        break;
//                    case R.id.nav_logout:
//                        SharedPreferences pref = getSharedPreferences("SESSION", MODE_PRIVATE);
//                        pref.edit().clear().commit();
//                        Intent intent_loyout = new Intent(MainActivity.this, LoginActivity.class);
//                        startActivity(intent_loyout);
//                        finish();
//                        break;
                }
                //Đanh dấu lại menu đã chon
                for (int i = 0; i < navigationView.getMenu().size(); i++) {
                    MenuItem big_item = navigationView.getMenu().getItem(i);
                    SubMenu lst_submenu = big_item.getSubMenu();
                    for (int j = 0; j < lst_submenu.size(); j++) {
                        MenuItem subMenu = lst_submenu.getItem(j);
                        subMenu.setChecked(false);
                        subMenu.setCheckable(false);
                    }
                }
                item.setCheckable(true);
                item.setChecked(true);
                drawer.closeDrawer(GravityCompat.START);
                return false;
            }
        });
    }

    public static void SetTileActionBar(String title) {
        toolbar.setTitle(title);
    }

    public void open_fragment(Fragment fragment) {
        fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.nav_host_fragment, fragment);
        fragmentTransaction.setCustomAnimations(R.anim.fade_in, R.anim.fade_out);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }
}