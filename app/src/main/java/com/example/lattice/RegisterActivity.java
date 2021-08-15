package com.example.lattice;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.lattice.Calender.DatePickerFragment;
import com.example.lattice.RetrofitPincode.models.PincodeData;
import com.example.lattice.RetrofitPincode.service.PincodeApiClient;
import com.example.lattice.RetrofitPincode.service.PincodeApiInterface;
import com.example.lattice.SingletonPattern.Singleton;

import java.text.DateFormat;
import java.util.Calendar;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener, DatePickerDialog.OnDateSetListener {


    private EditText mPhoneNumber, mName, mDOB;
    private EditText mAddressLine1, mAddressLine2, mPincode;

    private EditText mDistrict, mState;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        setTitle("Register");

        mPhoneNumber = findViewById(R.id.phone_number);
        mName = findViewById(R.id.name);
        mDOB = findViewById(R.id.dob);

        mAddressLine1 = findViewById(R.id.address1);
        mAddressLine2 = findViewById(R.id.address2);
        mPincode = findViewById(R.id.pincode);
        mDistrict = findViewById(R.id.district);
        mState = findViewById(R.id.state);

        Button mCheck = findViewById(R.id.check);
        Button mRegister = findViewById(R.id.register);

        //DatePicker
        ImageView mCalenderImage = findViewById(R.id.calender);
        mCalenderImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment datePicker = new DatePickerFragment();
                datePicker.show(getSupportFragmentManager(), "date picker");
            }
        });

        //DropDown
        Spinner mGender = findViewById(R.id.gender);
        ArrayAdapter<CharSequence> genderAdapter = ArrayAdapter.createFromResource(this, R.array.gender, android.R.layout.simple_spinner_item);
        genderAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mGender.setAdapter(genderAdapter);
        mGender.setOnItemSelectedListener(this);

        mCheck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String pincode = mPincode.getText().toString();

                getPincodeData(pincode);

            }
        });


        mRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String phoneNumber = mPhoneNumber.getText().toString().trim();
                String name = mName.getText().toString().trim();
                String dob = mDOB.getText().toString().trim();
                String addressLine1 = mAddressLine1.getText().toString().trim();
                String addressLine2 = mAddressLine2.getText().toString().trim();
                String pincode = mPincode.getText().toString().trim();
                String district = mDistrict.getText().toString().trim();
                String state = mState.getText().toString().trim();

                if (TextUtils.isEmpty(phoneNumber)) {
                    mPhoneNumber.setError("Phone number is empty");
                    return;
                }

                if (phoneNumber.length() != 10) {
                    mPhoneNumber.setError("Please recheck your phone number");
                    return;
                }

                if (TextUtils.isEmpty(name)) {
                    mName.setError("Please enter name");
                    return;
                }

                if (name.length() >= 50) {
                    mName.setError("Your name is too long");
                    return;
                }

                if (TextUtils.isEmpty(dob)) {
                    mDOB.setError("Please enter DOB");
                    return;
                }

                if (TextUtils.isEmpty(addressLine1)) {
                    mAddressLine1.setError("Please enter your address");
                    return;
                }

                if (addressLine1.length() <= 3) {
                    mAddressLine1.setError("Address is too short");
                    return;
                }

                if (addressLine1.length() >= 50) {
                    mAddressLine1.setError("Address is too long");
                    return;
                }

                if (addressLine2.length() >= 50) {
                    mAddressLine2.setError("Address is too long");
                    return;
                }

                if (district.isEmpty()) {
                    mDistrict.setError("Please enter District");
                    return;
                }

                if (state.isEmpty()) {
                    mDistrict.setError("Please enter State");
                    return;
                }

                Singleton singleton = Singleton.getInstance();

                singleton.setPhoneNumber(phoneNumber);
                singleton.setName(name);
                singleton.setDob(dob);
                singleton.setAddressLine1(addressLine1);
                singleton.setAddressLine2(addressLine2);
                singleton.setPincode(pincode);
                singleton.setDistrict(district);
                singleton.setState(state);

                //Register to MainActivity
                startActivity(new Intent(getApplicationContext(), WeatherActivity.class));

            }

        });

    }

    public void getPincodeData(String pincode) {

        PincodeApiInterface pincodeApiInterface = PincodeApiClient.getClient(RegisterActivity.this).create(PincodeApiInterface.class);
        Call<List<PincodeData>> call = pincodeApiInterface.getPincodeData(pincode);

        call.enqueue(new Callback<List<PincodeData>>() {
            @Override
            public void onResponse(Call<List<PincodeData>> call, Response<List<PincodeData>> response) {

                if (response.body().get(0).getStatus().equals("Success")) {
                    mDistrict.setText(response.body().get(0).getPostOfficeList().get(0).getDistrict());
                    mState.setText(response.body().get(0).getPostOfficeList().get(0).getState());
                }
                else {
                    Toast.makeText(RegisterActivity.this, "Please enter valid pincode", Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<List<PincodeData>> call, Throwable t) {

            }


        });

    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        String gender = parent.getItemAtPosition(position).toString();

        Singleton singleton = Singleton.getInstance();
        singleton.setGender(gender);
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {

        Calendar calendar = Calendar.getInstance();

        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.MONTH, month);
        calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);

        String DOB = DateFormat.getDateInstance(DateFormat.DATE_FIELD).format(calendar.getTime());

        mDOB.setText(DOB);

    }

}