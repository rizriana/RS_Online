package com.rizrira.rsantrian;

import android.annotation.SuppressLint;
import android.app.SearchManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.navigation.NavigationView;
import com.rizrira.rsantrian.slider.BannerSlider;
import com.rizrira.rsantrian.slider.FragmentSlider;
import com.rizrira.rsantrian.slider.SliderIndicator;
import com.rizrira.rsantrian.slider.SliderPagerAdapter;
import com.rizrira.rsantrian.pdModel.pdModel;
import com.rizrira.rsantrian.ui.login.LoginActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


public class MainMenuActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    TextView tviduser, tvnamauser, tvemailuser, tvnomortelp;
    LinearLayout lv_anggotakeluarga, lv_pendaftaran, lv_klinik, lv_infoantrian;

    private static final String PREFS_NAME = "preferences";
    private static final String PREF_UNAME = "Username";
    private static final String PREF_NO = "Password";

    private final String DefaultUnameValue = "";
    private String UnameValue;
    private String no_telp;
    private String email_user;

    private final String DefaultPasswordValue = "";
    private String PasswordValue;
    private static final String TAG = "LoginActivity";
    private static final int REQUEST_SIGNUP = 0;

    private SliderPagerAdapter mAdapter;
    private SliderIndicator mIndicator;

    private BannerSlider bannerSlider;
    private LinearLayout mLinearLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        bannerSlider = findViewById(R.id.sliderView);
        mLinearLayout = findViewById(R.id.pagesContainer);
        setupSlider();

        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle(null);
            getSupportActionBar().setElevation(0);
        }

        lv_anggotakeluarga = (LinearLayout) findViewById(R.id.view_anggotakeluarga);
        lv_pendaftaran = (LinearLayout) findViewById(R.id.view_pendaftaran);
        lv_klinik = (LinearLayout) findViewById(R.id.view_klinik);
        lv_infoantrian = (LinearLayout) findViewById(R.id.view_antrian);
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        View hView = navigationView.getHeaderView(0);
        ConstraintLayout container = hView.findViewById(R.id.lnav);
        AnimationDrawable animationDrawable2 = (AnimationDrawable) container.getBackground();
        animationDrawable2.setEnterFadeDuration(2000);
        animationDrawable2.setExitFadeDuration(4000);
        animationDrawable2.start();
        tviduser = (TextView) hView.findViewById(R.id.tvIduser);
        tvnomortelp = (TextView) hView.findViewById(R.id.tvnomortelp);
        tvnamauser = (TextView) hView.findViewById(R.id.tvNamauser);
        tvemailuser = (TextView) hView.findViewById(R.id.tvemailuser);
        Intent intent = getIntent();
        LinearLayout mAdViewLayout = findViewById(R.id.adView);
        // tvemailuser.setText(intent.getStringExtra("email_user"));
        lv_anggotakeluarga.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent1 = new Intent(MainMenuActivity.this, ListAnggotaKeluarga.class);
                intent1.putExtra("id_user2", tviduser.getText().toString());
                startActivity(intent1);
                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
            }
        });
        lv_pendaftaran.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new AlertDialog.Builder(MainMenuActivity.this)
                        .setMessage("PENDAFTARAN PASIEN")
                        .setCancelable(false)
                        .setPositiveButton("PASIEN LAMA", new DialogInterface.OnClickListener() {
                            @SuppressLint("NewApi")
                            public void onClick(DialogInterface dialog, int id) {
                                Intent intent1 = new Intent(MainMenuActivity.this, PilihPasienActivity.class);
                                intent1.putExtra("id_user2", tviduser.getText().toString());
                                startActivity(intent1);
                            }
                        })

                        .setNegativeButton("PASIEN BARU", new DialogInterface.OnClickListener() {
                            @SuppressLint("NewApi")
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Intent intent1 = new Intent(MainMenuActivity.this, InputPasienActivity.class);
                                startActivity(intent1);
                            }
                        })

                        .show();
            }
        });
        lv_klinik.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent1 = new Intent(MainMenuActivity.this, ListKlinik.class);
                startActivity(intent1);

            }
        });
        lv_infoantrian.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent1 = new Intent(MainMenuActivity.this, InfoAntrian.class);
                startActivity(intent1);

            }
        });
    }

    private void setupSlider() {
        bannerSlider.setDurationScroll(800);
        List<Fragment> fragments = new ArrayList<>();

        //link image
        fragments.add(FragmentSlider.newInstance("https://raw.githubusercontent.com/AnanditaRian19/RSMS_Online/master/ss1.png?token=APSU34RCMDFKNNXXRZ2PTC27ULP44"));
        fragments.add(FragmentSlider.newInstance("https://raw.githubusercontent.com/AnanditaRian19/RSMS_Online/master/ss22.png?token=APSU34VJ2W5P4WALR6MMDF27ULRCA"));
        fragments.add(FragmentSlider.newInstance("https://raw.githubusercontent.com/AnanditaRian19/RSMS_Online/master/ss3.png?token=APSU34VO572GWHROL52U7MC7ULQLG"));
        fragments.add(FragmentSlider.newInstance("https://raw.githubusercontent.com/AnanditaRian19/RSMS_Online/master/ss4.png?token=APSU34RXBD2PISVH4XWO2C27ULSMU"));

        mAdapter = new SliderPagerAdapter(getSupportFragmentManager(), fragments);
        bannerSlider.setAdapter(mAdapter);
        mIndicator = new SliderIndicator(this, mLinearLayout, bannerSlider, R.drawable.indicator_circle);
        mIndicator.setPageCount(fragments.size());
        mIndicator.show();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.main_menu, menu);
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);

        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        if (searchManager != null) {
            SearchView searchView = (SearchView) (menu.findItem(R.id.action_search)).getActionView();
            searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
            searchView.setQueryHint(getString(R.string.search_hint));
            searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                @Override
                public boolean onQueryTextSubmit(String query) {
                    Toast.makeText(MainMenuActivity.this, query, Toast.LENGTH_SHORT).show();
                    return true;
                }

                @Override
                public boolean onQueryTextChange(String newText) {
                    return false;
                }
            });
        }
        return true;
    }

    @Override
    protected void onPause() {
        super.onPause();
        savePreferences();
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadPreferences();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_profile) {
            Intent intent = new Intent(MainMenuActivity.this, Akun.class);
            intent.putExtra("id_akun", tviduser.getText().toString());
            startActivity(intent);
            overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        if (id == R.id.nav_bantuan) {
            Intent intent = new Intent(MainMenuActivity.this, Bantuan.class);
            startActivity(intent);
            // Handle the camera action
        } else if (id == R.id.nav_akun) {
            Intent intent = new Intent(MainMenuActivity.this, Akun.class);
            intent.putExtra("id_akun", tviduser.getText().toString());
            startActivity(intent);
        } else if (id == R.id.nav_logout) {
            new AlertDialog.Builder(MainMenuActivity.this)
                    .setMessage("Keluar ?")
                    .setTitle("Anda akan keluar dari Aplikasi antrian")
                    .setCancelable(false)
                    .setPositiveButton("Keluar", new DialogInterface.OnClickListener() {
                        @SuppressLint("NewApi")
                        public void onClick(DialogInterface dialog, int id) {
//                            finish();
                            startActivity(new Intent(getApplicationContext(), LoginActivity.class));
                        }
                    })
                    .setNegativeButton("Batal", new DialogInterface.OnClickListener() {
                        @SuppressLint("NewApi")
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                        }
                    })
                    .show();
        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void getProfile() {
        pdModel.pdLogin(MainMenuActivity.this);
        String url = ConfigApp.SERVERAPP + "getprofile.php";
        StringRequest stringRequest2 = new StringRequest(url + "?email_user=" + tvemailuser.getText().toString(), new Response.Listener<String>() {
            @Override
            public void onResponse(String response2) {
                String nama = "";
                String id_user = "";
                String no_telp = "";
                try {
                    JSONObject jsonObject = new JSONObject(response2);
                    JSONArray result = jsonObject.getJSONArray("profile");
                    JSONObject collegeData = result.getJSONObject(0);
                    nama = collegeData.getString("nama_user");
                    id_user = collegeData.getString("id_user");
                    no_telp = collegeData.getString("no_telp");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                tvnamauser.setText(nama);
                tviduser.setText(id_user);
                tvnomortelp.setText(no_telp);
                pdModel.hideProgressDialog();
            }
        },
                error -> {
                    Toast.makeText(MainMenuActivity.this, getString(R.string.error_toast_login), Toast.LENGTH_LONG).show();
                    pdModel.hideProgressDialog();
                });
        RequestQueue requestQueue2 = Volley.newRequestQueue(MainMenuActivity.this);
        requestQueue2.add(stringRequest2);
    }

    private void savePreferences() {
        SharedPreferences settings = getSharedPreferences(PREFS_NAME,
                Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = settings.edit();

        // Edit and commit
        UnameValue = tviduser.getText().toString();
        no_telp = tvnomortelp.getText().toString();

        System.out.println("onPause save name: " + UnameValue);

        editor.putString(PREF_UNAME, UnameValue);
        editor.putString(PREF_NO, no_telp);
        editor.apply();
    }

    private void loadPreferences() {

        SharedPreferences settings = getSharedPreferences(PREFS_NAME,
                Context.MODE_PRIVATE);

        // Get value
        UnameValue = settings.getString(PREF_UNAME, DefaultUnameValue);
        email_user = settings.getString("email_user", DefaultUnameValue);
        no_telp = settings.getString(PREF_NO, DefaultUnameValue);

        tviduser.setText(UnameValue);
        tvnomortelp.setText(no_telp);
        tvemailuser.setText(email_user);
        Toast.makeText(MainMenuActivity.this, tvemailuser.getText().toString(), Toast.LENGTH_LONG).show();
        getProfile();

        System.out.println("onResume load name: " + UnameValue);
        System.out.println("onResume load password: " + PasswordValue);
    }
}
