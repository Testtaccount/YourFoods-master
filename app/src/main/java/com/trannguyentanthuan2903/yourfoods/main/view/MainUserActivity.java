package com.trannguyentanthuan2903.yourfoods.main.view;

import android.Manifest;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.facebook.FacebookSdk;
import com.facebook.login.LoginManager;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.trannguyentanthuan2903.yourfoods.R;
import com.trannguyentanthuan2903.yourfoods.about.AboutUs_Fragment;
import com.trannguyentanthuan2903.yourfoods.favorites.FavoriteFragment;
import com.trannguyentanthuan2903.yourfoods.history_order_user.view.HistoryOrderUserFragment;
import com.trannguyentanthuan2903.yourfoods.main.presenter.UserPresenter;
import com.trannguyentanthuan2903.yourfoods.maps.MapActivity;
import com.trannguyentanthuan2903.yourfoods.oop.BaseActivity;
import com.trannguyentanthuan2903.yourfoods.product.model.Product;
import com.trannguyentanthuan2903.yourfoods.profile_store.model.Store;
import com.trannguyentanthuan2903.yourfoods.profile_store.view.Profile_Store_Fragment;
import com.trannguyentanthuan2903.yourfoods.profile_user.model.User;
import com.trannguyentanthuan2903.yourfoods.profile_user.view.ProfileUser_Fragment;
import com.trannguyentanthuan2903.yourfoods.search_user.model.SearchStore;
import com.trannguyentanthuan2903.yourfoods.search_user.model.SearchStoreAdapter;
import com.trannguyentanthuan2903.yourfoods.store_list.view.Store_List_Fragment;
import com.trannguyentanthuan2903.yourfoods.utility.Constain;
import com.trannguyentanthuan2903.yourfoods.utility.GPSTracker;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import static com.trannguyentanthuan2903.yourfoods.utility.Constain.MY_CHANGE_REQUEST_LOCATION;
import static com.trannguyentanthuan2903.yourfoods.utility.Constain.MY_IMPRESSION_REQUEST_LOCATION;

public class MainUserActivity extends BaseActivity implements View.OnClickListener, Serializable, TextWatcher {
    private static final String TAG = "MainUserActivity";

    private ImageView imgAvata, iconSearch;
    private TextView txtUserName, txtSumOrdered;
    private DatabaseReference mData;
    private String idUser, userName, linkPhotoUser, sumOrdered, addressUser = "";
    private Button btnLogout;
    private GPSTracker gps;
    private UserPresenter presenter;
    private HashMap<String, Object> location;
    private ArrayList<Product> arrAllProduct;
    private double lo = 0, la = 0;
    private LinearLayout layoutSearch, layoutHome, layoutMyfavorite, layoutOrderHistory, layoutAboutUs, layoutMap, layoutMyProfile;
    private Store_List_Fragment storeListFragment;
    private AutoCompleteTextView edtSearch;
    private ArrayList<SearchStore> arrSearchStore;
    private int viewResourceId;
    private SearchStoreAdapter adapter;
    private boolean flag_exit = true;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(this);
        setContentView(R.layout.activity_main_user);
        addControls();
//        checkGPS();
        initInfo();
        addEvvents();
        sendAddress();
    }

    private void checkGPS() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(MainUserActivity.this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(MainUserActivity.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, MY_CHANGE_REQUEST_LOCATION);

        } else {
            // Check if GPS enabled
            if (gps.canGetLocation()) {
                la = gps.getLatitude();
                lo = gps.getLongitude();
            } else {
                gps.showSettingsAlert();
            }
        }
    }

    private void addEvvents() {
        btnLogout.setOnClickListener(this);
        layoutSearch.setOnClickListener(this);
        layoutMyProfile.setOnClickListener(this);
        layoutMyfavorite.setOnClickListener(this);
        layoutHome.setOnClickListener(this);
        layoutOrderHistory.setOnClickListener(this);
        layoutAboutUs.setOnClickListener(this);
        layoutMap.setOnClickListener(this);
        edtSearch.addTextChangedListener(this);
        edtSearch.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                SearchStore searchStore = arrSearchStore.get(position);
                Intent intent = new Intent(MainUserActivity.this, MainUser2Activity.class);
                intent.putExtra(Constain.ID_STORE, searchStore.getIdStore());
                intent.putExtra(Constain.ID_USER, idUser);
                intent.putExtra(Constain.IS_STORE, false);
                startActivity(intent);
            }
        });
    }

    private void initInfo() {
        Intent intent = getIntent();
        idUser = intent.getStringExtra(Constain.ID_USER);
        try {
            mData.child(Constain.USERS).child(idUser).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    if (dataSnapshot.getValue() != null) {
                        try {
                            User user = dataSnapshot.getValue(User.class);
                            userName = user.getUserName();
                            addressUser = "";
                            if (user.getLocation() != null) {
                                HashMap<String, Object> flag = new HashMap<>();
                                flag = user.getLocation();
                                addressUser = String.valueOf(flag.get(Constain.ADDRESS));
                            }
                                location.put(Constain.LO, lo);
                            location.put(Constain.LA, la);
                            location.put(Constain.ADDRESS, addressUser);
                            presenter.updateLocation(idUser, location);
                            linkPhotoUser = user.getLinkPhotoUser();
                            sumOrdered = user.getSumOrdered() + " Ordered";
                            if (!linkPhotoUser.equals("")) {
                                Glide.with(MainUserActivity.this)
                                        .load(linkPhotoUser)
                                        .fitCenter()
                                        .into(imgAvata);
                            }
                            txtUserName.setText(userName);
                            txtSumOrdered.setText(sumOrdered);
                        } catch (Exception ex) {
                            ex.printStackTrace();
                        }
                    } else {
                        showToast("Lỗi không load được User!");
                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
        } catch (Exception ex) {

        }

    }

    private void addControls() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        //View
        imgAvata = (ImageView) findViewById(R.id.imgPhotoUser);
        txtUserName = (TextView) findViewById(R.id.txtusername_mainuser);
        txtSumOrdered = (TextView) findViewById(R.id.txtSumOreders_mainuser);
        btnLogout = (Button) findViewById(R.id.btn_logout_user);
        layoutSearch = (LinearLayout) findViewById(R.id.layoutSearch);
        iconSearch = (ImageView) findViewById(R.id.iconSearchStore);
        layoutHome = (LinearLayout) findViewById(R.id.navigation_homeUser);
        layoutMyProfile = (LinearLayout) findViewById(R.id.navigation_myprofile);
        layoutMyfavorite = (LinearLayout) findViewById(R.id.navigation_myfavorite);
        layoutOrderHistory = (LinearLayout) findViewById(R.id.navigation_historyorder);
        layoutAboutUs = (LinearLayout) findViewById(R.id.navigation_aboutus_user);
        layoutMap = (LinearLayout) findViewById(R.id.navigation_maps);
        location = new HashMap<>();
        mData = FirebaseDatabase.getInstance().getReference();
        presenter = new UserPresenter();
        gps = new GPSTracker(this);
        arrAllProduct = new ArrayList<>();
        storeListFragment = new Store_List_Fragment();
        getSupportFragmentManager().beginTransaction().replace(R.id.content_id_user, storeListFragment).commit();
        //search store
        edtSearch = (AutoCompleteTextView) findViewById(R.id.edtSearch);
        arrSearchStore = new ArrayList<>();
        initInfo();
        getArrSearchStore();
    }

    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            if (flag_exit == true) {
                AlertDialog.Builder aler = new AlertDialog.Builder(this);
                aler.setMessage("Bạn có chắc chắn muốn thoát?");
                aler.setCancelable(false);
                aler.setPositiveButton("Có", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        moveTaskToBack(true);
                        android.os.Process.killProcess(android.os.Process.myPid());
                        System.exit(1);
                    }
                });
                aler.setNegativeButton("Không", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                aler.create().show();
            } else {
                flag_exit = true;
                getSupportFragmentManager().beginTransaction().replace(R.id.content_id_user, storeListFragment).commit();
            }
        }
    }

    @Override
    public void onClick(View v) {
        int view = v.getId();
        if (view == R.id.btn_logout_user) {
            logOut();
        }
        if (view == R.id.layoutSearch ) {
            // moveToSearchAcitvity();
            if (adapter == null) {
                viewResourceId = R.layout.item_search_store;
//                Collections.sort(arrSearchStore,SearchStore.BY_NAME_ALPHABET);
                adapter = new SearchStoreAdapter(this, viewResourceId, arrSearchStore, lo, la);
                edtSearch.setAdapter(adapter);
            }
        }
        if (view == R.id.navigation_homeUser) {
            onBackPressed();
            flag_exit = true;
            getSupportFragmentManager().beginTransaction().replace(R.id.content_id_user, storeListFragment).commit();
        }
        if (view == R.id.navigation_myprofile) {
            onBackPressed();
            moveToProfileFragment();
        }
        if (view == R.id.navigation_myfavorite) {
            onBackPressed();
            moveToFavoriteFragment();
        }
        if (view == R.id.navigation_historyorder) {
            onBackPressed();
            moveToHistoryFragment();
        }
        if (view == R.id.navigation_aboutus_user){
            onBackPressed();
            moveToAboutUsFragment();
        }
        if (view == R.id.navigation_maps){
            moveToMaps();
        }
    }

    private void moveToMaps() {
        startActivity(new Intent(MainUserActivity.this, MapActivity.class));
    }
    private void moveToHistoryFragment() {
        flag_exit = false;
        HistoryOrderUserFragment historyOrderUserFragment = new HistoryOrderUserFragment();
        setTitle("Lịch sử order");
        getSupportFragmentManager().beginTransaction().replace(R.id.content_id_user, historyOrderUserFragment).commit();
    }

    private void moveToProfileFragment() {
        flag_exit = false;
        ProfileUser_Fragment profileUserFragment = new ProfileUser_Fragment();
        setTitle("Trang cá nhân");
        getSupportFragmentManager().beginTransaction().replace(R.id.content_id_user, profileUserFragment).commit();
    }

    private void moveToAboutUsFragment() {
        flag_exit = false;
        AboutUs_Fragment aboutUs_Fragment = new AboutUs_Fragment();
        setTitle("About us");
        getSupportFragmentManager().beginTransaction().replace(R.id.content_id_user, aboutUs_Fragment).commit();
    }

    private void moveToFavoriteFragment() {
        flag_exit = false;
        FavoriteFragment favoriteFragment = new FavoriteFragment();
        setTitle("Favorite");
        getSupportFragmentManager().beginTransaction().replace(R.id.content_id_user, favoriteFragment).commit();
    }

    private void getArrSearchStore() {
        mData.child(Constain.STORES).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                arrSearchStore.clear();
                if (dataSnapshot.getValue() != null) {
                    for (DataSnapshot dt : dataSnapshot.getChildren()) {
                        try {
                            Store store = dt.getValue(Store.class);
                            HashMap<String, Object> location = new HashMap<String, Object>();
                            double lo = 0, la = 0;
                            try {
                                if (store.getLocation() != null) {
                                    location = store.getLocation();
                                    lo = (double) location.get(Constain.LO);
                                    la = (double) location.get(Constain.LA);
                                }
                            } catch (Exception ex) {
                                ex.printStackTrace();
                            }
                            SearchStore searchStore = new SearchStore(store.getLinkPhotoStore(), store.getIdStore(), store.getStoreName(), lo, la);
                            arrSearchStore.add(searchStore);
                        } catch (Exception ex) {
                            ex.printStackTrace();
                        }
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private void logOut() {
        AlertDialog.Builder alert = new AlertDialog.Builder(MainUserActivity.this);
        alert.setMessage("Do you want to logout?");
        alert.setCancelable(false);
        alert.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                LoginManager.getInstance().logOut();
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(MainUserActivity.this, MainActivity.class));
            }
        });
        alert.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        alert.show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == Constain.REQUEST_CODE_GPS) {
//            gps.getLocation();
//            lo = gps.getLongitude();
//            la = gps.getLatitude();
            location.put(Constain.LO, lo);
            location.put(Constain.LA, la);
            location.put(Constain.ADDRESS, addressUser);
            presenter.updateLocation(idUser, location);
        }
    }

    public void moveToProfileStoreFragment(String idStore) {
        flag_exit = false;
        Profile_Store_Fragment profileStoreFragment = new Profile_Store_Fragment();
        Bundle bundle = new Bundle();
        bundle.putBoolean(Constain.IS_STORE, false);
        bundle.putString(Constain.ID_STORE, idStore);
        profileStoreFragment.setArguments(bundle);
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.content_id_user, profileStoreFragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        if (adapter == null) {
            viewResourceId = R.layout.item_search_store;
            adapter = new SearchStoreAdapter(this, viewResourceId, arrSearchStore, lo, la);
            edtSearch.setAdapter(adapter);
        }
    }

    @Override
    public void afterTextChanged(Editable s) {

    }

    private boolean MyStartActivity(Intent aIntent) {
        try {
            startActivity(aIntent);
            return true;
        } catch (ActivityNotFoundException e) {
            return false;
        }
    }

    private void sendAddress() {
        if (ContextCompat.checkSelfPermission(MainUserActivity.this,
                Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(MainUserActivity.this,
                    Manifest.permission.ACCESS_COARSE_LOCATION)) {
                ActivityCompat.requestPermissions(MainUserActivity.this,
                        new String[]{Manifest.permission.ACCESS_COARSE_LOCATION}, MY_IMPRESSION_REQUEST_LOCATION);
            } else {
                ActivityCompat.requestPermissions(MainUserActivity.this,
                        new String[]{Manifest.permission.ACCESS_COARSE_LOCATION}, MY_IMPRESSION_REQUEST_LOCATION);
            }

        } else {
            LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
            Location location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
            try {
                addressUser = (hereLocation(location.getLatitude(), location.getLongitude()));
                la = latitude(location.getLatitude(), location.getLongitude());
                lo = longtitude(location.getLatitude(), location.getLongitude());
            } catch (Exception e) {
                e.printStackTrace();
                gps.showSettingsAlert();
                Toast.makeText(MainUserActivity.this, "Can not found your location", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case MY_IMPRESSION_REQUEST_LOCATION: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    if (ContextCompat.checkSelfPermission(MainUserActivity.this,
                            Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                        LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
                        Location location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
                        try {
                            addressUser = (hereLocation(location.getLatitude(), location.getLongitude()));
                            la = latitude(location.getLatitude(), location.getLongitude());
                            lo = longtitude(location.getLatitude(), location.getLongitude());
                        } catch (Exception e) {
                            e.printStackTrace();
                            Toast.makeText(MainUserActivity.this, "Can not found your location", Toast.LENGTH_SHORT).show();
                        }
                    }
                } else {
                    Toast.makeText(this, "Need permission", Toast.LENGTH_SHORT).show();
                }
                return;
            }
            case MY_CHANGE_REQUEST_LOCATION: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    gps = new GPSTracker(MainUserActivity.this);
                    if (gps.canGetLocation()) {

                        double latitude = gps.getLatitude();
                        double longitude = gps.getLongitude();

                        Toast.makeText(getApplicationContext(), "Your Location is - \nLat: " + latitude + "\nLong: " + longitude, Toast.LENGTH_LONG).show();
                    } else {
                        gps.showSettingsAlert();
                    }

                } else {
                    Toast.makeText(MainUserActivity.this, "You need to grant permission", Toast.LENGTH_SHORT).show();
                }
                return;
            }
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    private String hereLocation(double lat, double lon) {

        String address = "";
        double latStart, logStart;
        Geocoder geocoder = new Geocoder(MainUserActivity.this, Locale.getDefault());
        List<Address> addressList;
        try {
            addressList = geocoder.getFromLocation(lat, lon, 1);
            if (addressList.size() > 0) {
                address = addressList.get(0).getAddressLine(0) + " " + addressList.get(0).getAddressLine(1) + " " +
                        addressList.get(0).getAddressLine(2) + " " + addressList.get(0).getAddressLine(3);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return address;
    }

    private double latitude(double lat, double lon) {

        double latStart = 0, logStart;
        Geocoder geocoder = new Geocoder(MainUserActivity.this, Locale.getDefault());
        List<Address> addressList;
        try {
            addressList = geocoder.getFromLocation(lat, lon, 1);
            if (addressList.size() > 0) {

                latStart = addressList.get(0).getLatitude();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return latStart;
    }

    private double longtitude(double lat, double lon) {

        double logStart = 0;
        Geocoder geocoder = new Geocoder(MainUserActivity.this, Locale.getDefault());
        List<Address> addressList;
        try {
            addressList = geocoder.getFromLocation(lat, lon, 1);
            if (addressList.size() > 0) {
                logStart = addressList.get(0).getLongitude();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return logStart;
    }
}

