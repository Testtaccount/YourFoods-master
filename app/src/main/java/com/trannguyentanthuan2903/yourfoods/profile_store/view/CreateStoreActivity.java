package com.trannguyentanthuan2903.yourfoods.profile_store.view;

import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.trannguyentanthuan2903.yourfoods.R;
import com.trannguyentanthuan2903.yourfoods.oop.BaseActivity;
import com.trannguyentanthuan2903.yourfoods.profile_store.presenter.CreateStorePresenter;
import com.trannguyentanthuan2903.yourfoods.utility.Constain;
import com.trannguyentanthuan2903.yourfoods.utility.GPSTracker;
import com.wdullaer.materialdatetimepicker.time.TimePickerDialog;

import java.io.IOException;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Administrator on 10/16/2017.
 */

public class CreateStoreActivity extends BaseActivity implements View.OnClickListener {

    private EditText edtStoreName, edtEmail, edtPhoneNumber, edtPassword, edtAddress;
    private TextView txtFrom, txtTo;
    private Button btnCreateStore;
    private CreateStorePresenter presenter;
    private GPSTracker gps;
    private FirebaseAuth mAuth;
    private double lo = 0.0, la = 0.0;
    private String startTime, endTime;
    private int hourStart = 0, hourEnd = 23;
    private String addr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_store);
        addControls();
//        checkGPS();
        addEvents();
        addr = edtAddress.getText().toString().trim();
    }

    private void addEvents() {
        btnCreateStore.setOnClickListener(this);
        txtFrom.setOnClickListener(this);
        txtTo.setOnClickListener(this);
    }

//    private void checkGPS() {
//        if (gps.canGetLocation()) {
//            gps.getLocation();
//            lo = gps.getLongitude();
//            la = gps.getLatitude();
//        } else {
//            gps.showSettingsAlert();
//        }
//    }

    private void getLocationFromAddress(Context context, String strAddress) {

        Geocoder coder = new Geocoder(context);
        List<Address> address;

        try {
            address = coder.getFromLocationName(strAddress, 5);
            if (address == null) {
                return;
            }
            Address location = address.get(0);
            la = location.getLatitude();
            lo = location.getLongitude();

        } catch (IOException ex) {

            ex.printStackTrace();
        }
    }

    private void createStore() {
        boolean isVaild = true;
        String storeName = (edtStoreName.getText().toString()).trim();
        String email = (edtEmail.getText().toString()).trim();
        String phoneNumber = (edtPhoneNumber.getText().toString()).trim();
        String password = (edtPassword.getText().toString()).trim();
        final String adddress = (edtAddress.getText().toString()).trim();
        String from = (txtFrom.getText().toString()).trim();
        String to = (txtTo.getText().toString()).trim();
        if (from.length() == 0 || to.length() == 0) {
            isVaild = false;
            showToast("Bạn chưa chọn thời gian là việc!");
        }
        if (TextUtils.isEmpty(storeName)) {
            isVaild = false;
            edtStoreName.setError("Bắt Buộc");
        } else {
            edtStoreName.setError(null);
        }
        if (TextUtils.isEmpty(email)) {
            isVaild = false;
            edtEmail.setError("Bắt Buộc");
        } else {
            edtEmail.setError(null);
        }
        if (!isEmailVail(email)) {
            isVaild = false;
            edtEmail.setError("Email không hợp lệ");
            edtEmail.requestFocus();
        }
        if (TextUtils.isEmpty(phoneNumber)) {
            isVaild = false;
            edtPhoneNumber.setError("Bắt Buộc");
        } else {
            edtPhoneNumber.setError(null);
        }
        if (TextUtils.isEmpty(adddress)) {
            isVaild = false;
            edtAddress.setError("Bắt Buộc");
        } else {
            edtAddress.setError(null);
        }
        if (TextUtils.isEmpty(password)) {
            isVaild = false;
            edtPassword.setError("Bắt Buộc");
        } else {
            edtPassword.setError(null);
        }
        if (password.length() < 6 && password.length() > 0) {
            isVaild = false;
            showToast("Mật khẩu phải lớn hơn 6 ký tự!");
            edtPassword.setText("");
            edtPassword.requestFocus();
        }
        if (isVaild) {
            //Create new store
            showProgressDialog();
            HashMap<String, Object> location = new HashMap<>();
            getLocationFromAddress(this,adddress);
            location.put(Constain.LO, lo);
            location.put(Constain.LA, la);
            location.put(Constain.ADDRESS, adddress);
            presenter.createNewStore(email, password, storeName, phoneNumber, location, from, to);

        }
    }

    private void addControls() {
        //EditText
        edtStoreName = (EditText) findViewById(R.id.edtstorename);
        edtEmail = (EditText) findViewById(R.id.edtemail_createstore);
        edtPhoneNumber = (EditText) findViewById(R.id.edtphonenumber_createstore);
        edtPassword = (EditText) findViewById(R.id.edtpassword_createstore);
        //TextView
        edtAddress = (EditText) findViewById(R.id.edtAddress);
        txtFrom = (TextView) findViewById(R.id.txtFrom);
        txtTo = (TextView) findViewById(R.id.txtTo);
        //Button
        btnCreateStore = (Button) findViewById(R.id.btncreatestor);
        //presenter
        mAuth = FirebaseAuth.getInstance();
        presenter = new CreateStorePresenter(this, mAuth);
        gps = new GPSTracker(this);
    }

    @Override
    public void onClick(View v) {
        int view = v.getId();
        if (view == R.id.btncreatestor) {
            createStore();
        }
        if (view == R.id.txtFrom) {
            showTimeStartPicker();
        }
        if (view == R.id.txtTo) {
            showTimeEndPicker();
        }
    }

    private void showTimeEndPicker() {
        Calendar now = Calendar.getInstance();
        int hour = now.get(Calendar.HOUR);
        int minute = now.get(Calendar.MINUTE);
        int second = now.get(Calendar.SECOND);

        TimePickerDialog.OnTimeSetListener callBack = new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePickerDialog view, int hourOfDay, int minute, int second) {
                endTime = hourOfDay + "h";
                hourEnd = hourOfDay;
                txtTo.setText(endTime);
            }
        };
        TimePickerDialog timePicker = TimePickerDialog.newInstance(callBack, hour, minute, second, true);
        timePicker.setMinTime(hourStart, 0, 0);
        timePicker.show(getFragmentManager(), "TimeEndPickerDialog");
    }

    private void showTimeStartPicker() {
        Calendar now = Calendar.getInstance();
        int hour = now.get(Calendar.HOUR);
        int minute = now.get(Calendar.MINUTE);
        int second = now.get(Calendar.SECOND);

        TimePickerDialog.OnTimeSetListener callBack = new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePickerDialog view, int hourOfDay, int minute, int second) {
                startTime = hourOfDay + "h";
                hourStart = hourOfDay;
                txtFrom.setText(startTime);
            }
        };
        TimePickerDialog timePicker = TimePickerDialog.newInstance(callBack, hour, minute, second, true);
        timePicker.setMaxTime(hourEnd, 59, 59);
        timePicker.show(getFragmentManager(), "TimeStartPickerDialog");
    }

//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        if (requestCode == Constain.REQUEST_CODE_GPS) {
//            gps.getLocation();
//            lo = gps.getLongitude();
//            la = gps.getLatitude();
//        }
//    }
}
