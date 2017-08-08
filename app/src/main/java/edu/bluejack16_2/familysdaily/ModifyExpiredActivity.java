package edu.bluejack16_2.familysdaily;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import edu.bluejack16_2.familysdaily.models.Product;

public class ModifyExpiredActivity extends AppCompatActivity {

    private EditText etProductName, etExpiredDate;
    private Button btnUpdate, btnDelete, btnBrowsePhoto, btnTakePhoto;
    private Product currProduct;
    private String currProductKey;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modify_expired);
        init();
    }

    private void init(){
        etProductName = (EditText) findViewById(R.id.etModifyProductName);
        etExpiredDate = (EditText) findViewById(R.id.etModifyExpiredDate);
        btnUpdate = (Button) findViewById(R.id.btnUpdateProduct);
        btnDelete = (Button) findViewById(R.id.btnDeleteProduct);

        currProduct = new Product(getIntent().getExtras());
        currProductKey = getIntent().getStringExtra("productKey");

        onBtnUpdateClicked();
        onBtnDeleteClicked();
    }

    private void fillField(){
        etProductName.setText(currProduct.getProductName());
        etExpiredDate.setText(currProduct.getProductExpiredDate());
    }

    private void onBtnUpdateClicked(){

    }

    private void onBtnDeleteClicked(){

    }

    private void onBtnTakePhotoClicked(){

    }

    private void onBtnBrowsePhotoClicked(){

    }

}
