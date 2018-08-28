package com.trannguyentanthuan2903.yourfoods.main.view;

import android.Manifest;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
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

import com.aurelhubert.ahbottomnavigation.AHBottomNavigation;
import com.aurelhubert.ahbottomnavigation.AHBottomNavigationItem;
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
import com.trannguyentanthuan2903.yourfoods.my_cart.view.DisplayProduct;
import com.trannguyentanthuan2903.yourfoods.my_cart.view.MyCartFragment;
import com.trannguyentanthuan2903.yourfoods.oop.BaseActivity;
import com.trannguyentanthuan2903.yourfoods.product.model.Product;
import com.trannguyentanthuan2903.yourfoods.product_list.view.ProductListFragment;
import com.trannguyentanthuan2903.yourfoods.profile_user.model.User;
import com.trannguyentanthuan2903.yourfoods.profile_user.view.ProfileUser_Fragment;
import com.trannguyentanthuan2903.yourfoods.search_user.model.SearchProduct;
import com.trannguyentanthuan2903.yourfoods.search_user.model.SearchProductAdapter;
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

/**
 * Created by Administrator on 10/16/2017.
 */

public class MainUser2Activity extends BaseActivity implements View.OnClickListener, Serializable, AHBottomNavigation.OnTabSelectedListener, TextWatcher {

    private ImageView imgAvata, imgSearch;
    private AHBottomNavigation ahBottomNavigation;
    private TextView txtUserName, txtSumOrdered;
    private DatabaseReference mData;
    private String idUser, idStore, userName, linkPhotoUser, sumOrdered, addressUser = "";
    private Button btnLogout;
    private GPSTracker gps;
    private UserPresenter presenter;
    private HashMap<String, Object> location;
    private ArrayList<Product> arrAllProduct;
    private double lo = 0, la = 0;
    private LinearLayout layoutSearch, layoutHome, layoutMyfavorite, layoutOrderHistory, layoutRate, layoutShare, layoutMyProfile;
    private MyCartFragment myCartFragment;
    private ProductListFragment fragment;
    private AutoCompleteTextView edtSearch;
    private ArrayList<SearchProduct> arrSearchProduct;
    private int viewResourceId;
    private SearchProductAdapter adapter;
    private boolean flag_exit = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(this);
        setContentView(R.layout.activity_main_user2);
        addControls();
//        checkGPS();
        initInfo();
        addEvvents();
        sendAddress();
    }

    private void checkGPS() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(MainUser2Activity.this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(MainUser2Activity.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, MY_CHANGE_REQUEST_LOCATION);
        } else {
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
        layoutRate.setOnClickListener(this);
        layoutShare.setOnClickListener(this);
        edtSearch.addTextChangedListener(this);
        edtSearch.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                SearchProduct searchProduct = arrSearchProduct.get(position);
                Product product = searchProduct.getProduct();
                Intent intent = new Intent(MainUser2Activity.this, DisplayProduct.class);
                intent.putExtra(Constain.PRODUCTS, product);
                intent.putExtra(Constain.ID_STORE, idStore);
                startActivity(intent);
            }
        });
    }

    private void initInfo() {
        try {
            //get Info User
            mData.child(Constain.USERS).child(idUser).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    if (dataSnapshot.getValue() != null) {
                        try {
                            User user = dataSnapshot.getValue(User.class);
                            userName = user.getUserName();
                            linkPhotoUser = user.getLinkPhotoUser();
                            sumOrdered = user.getSumOrdered() + " Ordered";
                            if (!linkPhotoUser.equals("")) {
                                Glide.with(MainUser2Activity.this)
                                        .load(linkPhotoUser)
                                        .fitCenter()
                                        .into(imgAvata);
                            }
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
            //get Adress
            mData.child(Constain.USERS).child(idUser).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    if (dataSnapshot.getValue() != null) {
                        try {
                            User user = dataSnapshot.getValue(User.class);
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
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar2);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout2);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();
        Intent intent = getIntent();
        idUser = intent.getStringExtra(Constain.ID_USER);
        idStore = intent.getStringExtra(Constain.ID_STORE);
        myCartFragment = new MyCartFragment();
        Bundle bundle = new Bundle();
        bundle.putString(Constain.ID_STORE, idStore);
        bundle.putBoolean(Constain.IS_STORE, false);
        fragment = new ProductListFragment();
        fragment.setArguments(bundle);
        //Navigation Bottom
        ahBottomNavigation = (AHBottomNavigation) findViewById(R.id.navigation_User);
        initItemNavigation();

        //View
        imgAvata = (ImageView) findViewById(R.id.imgPhotoUser2);
        imgSearch = (ImageView) findViewById(R.id.iconSearchStore2);
        txtUserName = (TextView) findViewById(R.id.txtusername_mainuser2);
        txtSumOrdered = (TextView) findViewById(R.id.txtSumOreders_mainuser2);
        btnLogout = (Button) findViewById(R.id.btn_logout_user2);
        layoutSearch = (LinearLayout) findViewById(R.id.layoutSearch2);
        layoutHome = (LinearLayout) findViewById(R.id.navigation_homeUser2);
        layoutMyProfile = (LinearLayout) findViewById(R.id.navigation_myprofile2);
        layoutMyfavorite = (LinearLayout) findViewById(R.id.navigation_myfavorite2);
        layoutOrderHistory = (LinearLayout) findViewById(R.id.navigation_historyorder2);
        layoutRate = (LinearLayout) findViewById(R.id.navigation_rateus_user2);
        layoutShare = (LinearLayout) findViewById(R.id.navigation_share_user2);
        location = new HashMap<>();
        mData = FirebaseDatabase.getInstance().getReference();
        presenter = new UserPresenter();
        gps = new GPSTracker(this);
        arrAllProduct = new ArrayList<>();
        //search Product
        edtSearch = (AutoCompleteTextView) findViewById(R.id.edtSearch2);
        arrSearchProduct = new ArrayList<>();
        viewResourceId = R.layout.item_search_product;
        getArrSearchProduct();
    }

    private void getArrSearchProduct() {
        try {
            mData.child(Constain.STORES).child(idStore).child(Constain.CATEGORY).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    if (dataSnapshot.getValue() != null) {
                        for (DataSnapshot dt : dataSnapshot.getChildren()) {
                            mData.child(Constain.STORES).child(idStore).child(Constain.CATEGORY).child(dt.getKey()).child(Constain.PRODUCTS).addValueEventListener(new ValueEventListener() {
                                @Override
                                public void onDataChange(DataSnapshot dataSnapshot) {
                                    if (dataSnapshot.getValue() != null) {
                                        for (DataSnapshot dt : dataSnapshot.getChildren()) {
                                            try {
                                                Product product = dt.getValue(Product.class);
                                                SearchProduct searchProduct = new SearchProduct(product);
                                                arrSearchProduct.add(searchProduct);
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
                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout2);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            if (flag_exit == true) {
                moveToStoreList();
            } else {
                flag_exit = true;
                createProductListFragment();
                ahBottomNavigation.setCurrentItem(0);
            }
        }
    }

    @Override
    public void onClick(View v) {
        int view = v.getId();
        if (view == R.id.btn_logout_user2) {
            logOut();
        }
        if (view == R.id.layoutSearch2 || view == R.id.iconSearchStore2) {
            if (adapter == null) {
                adapter = new SearchProductAdapter(MainUser2Activity.this, viewResourceId, arrSearchProduct);
                edtSearch.setAdapter(adapter);
            }
        }
        if (view == R.id.navigation_homeUser2) {
            onBackPressed();
            finish();
            moveToStoreList();
        }
        if (view == R.id.navigation_myprofile2) {
            onBackPressed();
            ahBottomNavigation.setCurrentItem(2);
            moveToProfileFragment();
        }
        if (view == R.id.navigation_myfavorite2) {
            onBackPressed();
            moveToFavoriteFragment();
        }
        if (view == R.id.navigation_historyorder2) {
            onBackPressed();
            moveToHistoryFragment();
        }
        if (view == R.id.navigation_rateus_user2){
            onBackPressed();
            moveToAboutUsFragment();
        }
        if (view == R.id.navigation_share_user2){
            moveToMaps();
        }
    }

    private void  moveToMaps() {
        startActivity(new Intent(MainUser2Activity.this, MapActivity.class));
    }

    private void moveToHistoryFragment() {
        flag_exit = false;
        HistoryOrderUserFragment profileUserFragment = new HistoryOrderUserFragment();
        setTitle("Lịch sử order");
        getSupportFragmentManager().beginTransaction().replace(R.id.content_id_user2, profileUserFragment).commit();
    }

    private void moveToStoreList() {
        Intent intent = new Intent(MainUser2Activity.this, MainUserActivity.class);
        intent.putExtra(Constain.ID_USER, idUser);
        intent.putExtra(Constain.IS_STORE, false);
        startActivity(intent);
    }

    private void moveToProfileFragment() {
        flag_exit = false;
        ProfileUser_Fragment profileUserFragment = new ProfileUser_Fragment();
        setTitle("Trang cá nhân");
        getSupportFragmentManager().beginTransaction().replace(R.id.content_id_user2, profileUserFragment).commit();
    }

    private void moveToAboutUsFragment() {
        flag_exit = false;
        AboutUs_Fragment aboutUs_Fragment = new AboutUs_Fragment();
        setTitle("About us");
        getSupportFragmentManager().beginTransaction().replace(R.id.content_id_user2, aboutUs_Fragment).commit();
    }

    private void moveToFavoriteFragment() {
        flag_exit = false;
        FavoriteFragment favoriteFragment = new FavoriteFragment();
        setTitle("Favorite");
        getSupportFragmentManager().beginTransaction().replace(R.id.content_id_user2, favoriteFragment).commit();
    }

    private void initItemNavigation() {
        AHBottomNavigationItem item1 = new AHBottomNavigationItem("Sản phẩm", R.drawable.icon_coffe);
        AHBottomNavigationItem item2 = new AHBottomNavigationItem("Giỏ hàng", R.drawable.icon_shopping);
        AHBottomNavigationItem item3 = new AHBottomNavigationItem("Trang cá nhân", R.drawable.icon_info);
        ahBottomNavigation.addItem(item1);
        ahBottomNavigation.addItem(item2);
        ahBottomNavigation.addItem(item3);

//        ahBottomNavigation.setDefaultBackgroundColor(Color.parseColor("#35a2e1"));
        ahBottomNavigation.setAccentColor(Color.parseColor("#3248ea"));
//        ahBottomNavigation.setInactiveColor(Color.parseColor("#dad9d9"));
        ahBottomNavigation.setTitleState(AHBottomNavigation.TitleState.SHOW_WHEN_ACTIVE);
        ahBottomNavigation.setTitleState(AHBottomNavigation.TitleState.ALWAYS_SHOW);

        ahBottomNavigation.setOnTabSelectedListener(this);
        ahBottomNavigation.setCurrentItem(0);
    }

    private void logOut() {
        AlertDialog.Builder alert = new AlertDialog.Builder(MainUser2Activity.this);
        alert.setMessage("Do you want to logout?");
        alert.setCancelable(false);
        alert.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                LoginManager.getInstance().logOut();
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(MainUser2Activity.this, MainActivity.class));
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

    @Override
    public boolean onTabSelected(int position, boolean wasSelected) {
        switch (position){
            case 0:
                createProductListFragment();
                break;
            case 1:
                flag_exit = false;
                getSupportFragmentManager().beginTransaction().replace(R.id.content_id_user2, myCartFragment).commit();
                break;
            case 2:
                setTitle("Trang cá nhân");
                moveToProfileFragment();
                break;
        }
        return true;
    }

    public void createProductListFragment() {
        flag_exit = true;
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.content_id_user2, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        if (adapter == null) {
            adapter = new SearchProductAdapter(MainUser2Activity.this, viewResourceId, arrSearchProduct);
            edtSearch.setAdapter(adapter);
        }
    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

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
        if (ContextCompat.checkSelfPermission(MainUser2Activity.this,
                Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(MainUser2Activity.this,
                    Manifest.permission.ACCESS_COARSE_LOCATION)) {
                ActivityCompat.requestPermissions(MainUser2Activity.this,
                        new String[]{Manifest.permission.ACCESS_COARSE_LOCATION}, MY_IMPRESSION_REQUEST_LOCATION);
            } else {
                ActivityCompat.requestPermissions(MainUser2Activity.this,
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
                Toast.makeText(MainUser2Activity.this, "Can not found your location", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case MY_IMPRESSION_REQUEST_LOCATION: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    if (ContextCompat.checkSelfPermission(MainUser2Activity.this,
                            Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                        LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
                        Location location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
                        try {
                            addressUser = (hereLocation(location.getLatitude(), location.getLongitude()));
                            la = latitude(location.getLatitude(), location.getLongitude());
                            lo = longtitude(location.getLatitude(), location.getLongitude());
                        } catch (Exception e) {
                            e.printStackTrace();
                            Toast.makeText(MainUser2Activity.this, "Can not found your location", Toast.LENGTH_SHORT).show();
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

                    gps = new GPSTracker(MainUser2Activity.this);

                    // Check if GPS enabled
                    if (gps.canGetLocation()) {

                        double latitude = gps.getLatitude();
                        double longitude = gps.getLongitude();

                    } else {
                        gps.showSettingsAlert();
                    }

                } else {

                    Toast.makeText(MainUser2Activity.this, "You need to grant permission", Toast.LENGTH_SHORT).show();
                }
                return;
            }
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    public String hereLocation(double lat, double lon) {

        String address = "";
        double latStart, logStart;
        Geocoder geocoder = new Geocoder(MainUser2Activity.this, Locale.getDefault());
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

    public double latitude(double lat, double lon) {

        double latStart = 0, logStart;
        Geocoder geocoder = new Geocoder(MainUser2Activity.this, Locale.getDefault());
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

    public double longtitude(double lat, double lon) {

        double logStart = 0;
        Geocoder geocoder = new Geocoder(MainUser2Activity.this, Locale.getDefault());
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
