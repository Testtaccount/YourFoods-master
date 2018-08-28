package com.trannguyentanthuan2903.yourfoods.my_cart.view;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;
import com.trannguyentanthuan2903.yourfoods.R;
import com.trannguyentanthuan2903.yourfoods.my_cart.model.MyCart;
import com.trannguyentanthuan2903.yourfoods.my_cart.presenter.MyCartPresenter;
import com.trannguyentanthuan2903.yourfoods.oop.BaseActivity;
import com.trannguyentanthuan2903.yourfoods.product.model.Product;
import com.trannguyentanthuan2903.yourfoods.utility.Constain;

import java.util.Calendar;
import java.util.Random;

/**
 * Created by Administrator on 10/17/2017.
 */

public class DisplayProduct extends BaseActivity {

    private ImageView imgPhotoProduct;
    private TextView txtProductName, txtDescribe, txtPrice;
    private Button btnBuyProduct;
    private Product product;
    private EditText edtCountProduct;
    private MyCartPresenter prsenter;
    private DatabaseReference mData;
    private String idUser, idStore, categoryName = "", idMyCart, linkPhotoProduct = "";
    private StringBuilder idMyCart_mBuilder;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_product);
        addControls ();
        initInfo ();
        addEvents ();
    }

    private void addEvents() {
        btnBuyProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addToMyCart ();
            }
        });
    }

    private void addToMyCart() {
        if (TextUtils.isEmpty(edtCountProduct.getText().toString())){
            edtCountProduct.setError("Bắt buộc");
        }
        else {
            final int countProduct = Integer.parseInt(edtCountProduct.getText().toString());
            final float price = product.getPrice() * countProduct;
            linkPhotoProduct = product.getLinkPhotoProduct();
            Calendar now = Calendar.getInstance();
            int day = now.get(Calendar.DAY_OF_MONTH);
            int month = now.get(Calendar.MONTH);
            int year = now.get(Calendar.YEAR);
            int hour = now.get(Calendar.HOUR);
            int minute = now.get(Calendar.MINUTE);
            final String timeOrder = hour + "h:" + minute + "p - " + day + "\\" + month + "\\" + year;
            edtCountProduct.setError(null);
            try {
                mData.child(Constain.USERS).child(idUser).child(Constain.MY_CART).child(idStore).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        if (dataSnapshot.getValue() != null){
                            boolean flag_id = true;
                            while (flag_id == true){
                                Random ra = new Random();
                                for (int i = 0;i<=10;i++){
                                    String number = String.valueOf(ra.nextInt(10));
                                    idMyCart_mBuilder.append(number);
                                }
                                idMyCart = idMyCart_mBuilder.toString();
                                for (DataSnapshot dt : dataSnapshot.getChildren()){
                                    if (idMyCart.equals(dt.getKey())){
                                        flag_id = true;
                                        break;
                                    }
                                    else {
                                        flag_id = false;
                                    }
                                }
                            }
                            try {
                                boolean flag_name = true;
                                int sumCount = 0;
                                float sumPrice = 0;
                                for (DataSnapshot dt : dataSnapshot.getChildren()) {
                                    MyCart myCart = dt.getValue(MyCart.class);
                                    if (product.getProductName().equals(myCart.getProductName())){
                                        flag_name = false;
                                        idMyCart = myCart.getIdMyOrder();
                                        sumCount = countProduct + myCart.getCount();
                                        sumPrice = sumCount * product.getPrice();
                                        break;
                                    }
                                }
                                if (flag_name == true){
                                    MyCart myCart = new MyCart(idMyCart, categoryName, product.getIdProduct(), product.getProductName(), linkPhotoProduct, countProduct, timeOrder, price);
                                    prsenter.addProductToCart(idUser, idMyCart, idStore, myCart);
                                    showToast("Thêm vào giỏ hàng thành công!");
                                    finish();
                                }
                                else {
                                    prsenter.updateCountProduct(idUser, idMyCart, idStore, sumCount);
                                    prsenter.updatePrice(idUser, idMyCart, idStore, sumPrice);
                                    showToast("Thêm vào giỏ hàng thành công!");
                                    finish();
                                }
                            }
                            catch (Exception ex){
                                ex.printStackTrace();
                            }
                        }
                        else {
                            Random ra = new Random();
                            for (int i = 0;i<=10;i++){
                                String number = String.valueOf(ra.nextInt(10));
                                idMyCart_mBuilder.append(number);
                            }
                            idMyCart = idMyCart_mBuilder.toString();
                            MyCart myCart = new MyCart(idMyCart, categoryName, product.getIdProduct(), product.getProductName(), linkPhotoProduct, countProduct, timeOrder, price);
                            prsenter.addProductToCart(idUser, idMyCart, idStore, myCart);
                            showToast("Thêm vào giỏ thành công!");
                            finish();
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
            }
            catch (Exception ex){
                ex.printStackTrace();
            }
        }
    }
    private void initInfo() {
        if (product != null) {
            txtProductName.setText(product.getProductName());
            txtDescribe.setText(product.getInfoProduct());
            txtPrice.setText(Math.round(product.getPrice()) + " VNĐ");
            linkPhotoProduct = product.getLinkPhotoProduct();
            if (!linkPhotoProduct.equals("")) {
                Picasso.with(this)
                        .load(linkPhotoProduct)
                        .resize(400, 400)
                        .centerCrop()
                        .placeholder(R.drawable.product_defaultimg)
                        .error(R.drawable.product_defaultimg)
                        .into(imgPhotoProduct);
            }
        }
    }

    private void addControls() {
        imgPhotoProduct = (ImageView) findViewById(R.id.imgPhotoProduct);
        txtProductName = (TextView) findViewById(R.id.txtProductName_displayproduct);
        txtDescribe = (TextView) findViewById(R.id.txtDescriber);
        txtPrice = (TextView) findViewById(R.id.txtPrice_displayproduct);
        btnBuyProduct = (Button) findViewById(R.id.btnBuyProduct);
        edtCountProduct = (EditText) findViewById(R.id.edtCountProduct);
        prsenter = new MyCartPresenter();
        mData = FirebaseDatabase.getInstance().getReference();
        idMyCart_mBuilder = new StringBuilder();
        //get Data
        Intent intent = getIntent();
        product = (Product) intent.getSerializableExtra(Constain.PRODUCTS);
        categoryName = intent.getStringExtra(Constain.CATEGORY_NAME);
        idStore = intent.getStringExtra(Constain.ID_STORE);
        idUser = getIdUser();
    }
}
