package com.example.zaid_alam_individual_project2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class CheckOutActivity extends AppCompatActivity {

    EditText customerName,customerEmail,customerPhone,customerAddress,cardNumber,cardExpiry,cardCVV;
    Button orderPlaceBtn;
    LinearLayout cardDetails;
    RadioGroup radioPayment;
    FirebaseDatabase database;
    DatabaseReference myRef;
    String paymentOption;
    ArrayList<AddToCartItems> cartItemsadded;
    Boolean isValidEmail =true;
    Boolean isValidPhone=true;
    Boolean isValidCard=true;
    Boolean isValidExpiryDate=true;
    Boolean isValideCVVvalue=true;
    String phoneNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_out);

        customerName=findViewById(R.id.customerName);
        customerEmail=findViewById(R.id.customerEmail);
        customerPhone=findViewById(R.id.customerPhone);
        customerAddress=findViewById(R.id.customerAddress);

        cardNumber=findViewById(R.id.cardNumber);
        cardExpiry=findViewById(R.id.cardExpiry);
        cardCVV=findViewById(R.id.cardCVV);

        orderPlaceBtn=findViewById(R.id.orderPlaceBtn);

        cardDetails=findViewById(R.id.cardDetails);
        radioPayment=findViewById(R.id.radioPayment);

        database=FirebaseDatabase.getInstance();
        myRef=database.getReference("Orders");


        Users user;

        // To see if card is selected or COD
        radioPayment.setOnCheckedChangeListener((group, checkedId) -> {
            if (checkedId == R.id.radioButtonCard) {
                cardDetails.setVisibility(View.VISIBLE);
                paymentOption="Card";

            } else if (checkedId == R.id.radioButtonCOD) {
                cardDetails.setVisibility(View.GONE);
                paymentOption="COD";
            }
        });

        // Email Validations
        customerEmail.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                String input = charSequence.toString();

                if (!input.isEmpty()) {
                    if (android.util.Patterns.EMAIL_ADDRESS.matcher(input).matches()) {
                        customerEmail.setError(null);
                        isValidEmail=true;
                    } else {
                        customerEmail.setError("Enter a valid email address");
                        isValidEmail=false;
                    }
                } else {
                    customerEmail.setError(null);
                    isValidEmail=true;
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        // Phone Number
        customerPhone.addTextChangedListener(new TextWatcher() { //
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                String input = charSequence.toString().replaceAll("-", "");
                if (!input.isEmpty()) {
                    if (isValidPhoneNumber(input)) {
                        customerPhone.setError(null);
                        isValidPhone=true;
                    } else {
                        customerPhone.setError("Enter a valid phone number");
                        isValidPhone=false;
                    }
                } else {
                    customerPhone.setError(null);
                    isValidPhone=true;
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });



          //Card Number
          cardNumber.addTextChangedListener(new TextWatcher() {
              @Override
              public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
              }

              @Override
              public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                  String input = charSequence.toString();
                  Log.i("info","input card "+input);
                  if (!input.isEmpty()) {
                      if (isValidCardNumber(input)) {
                          cardNumber.setError(null);
                          isValidCard=true;
                      } else {
                          cardNumber.setError("Enter a valid Card");
                          isValidCard=false;
                      }
                  } else {
                      cardNumber.setError(null);
                      isValidCard=true;
                  }
              }

              @Override
              public void afterTextChanged(Editable editable) {
              }
          });

          // Card Expiry
          cardExpiry.addTextChangedListener(new TextWatcher() {
              @Override
              public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
              }

              @Override
              public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                  String input = charSequence.toString();
                  if (!input.isEmpty()) {
                      if (isValidexpiry(input)) {
                          cardExpiry.setError(null);
                          isValidExpiryDate=true;
                      } else {
                          cardExpiry.setError("Enter a valid expiry date");
                          isValidExpiryDate=false;
                      }
                  } else {
                      cardExpiry.setError(null);
                      isValidExpiryDate=true;
                  }
              }

              @Override
              public void afterTextChanged(Editable editable) {
              }
          });

          // Card CVV
          cardCVV.addTextChangedListener(new TextWatcher() {
              @Override
              public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
              }

              @Override
              public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                  String input = charSequence.toString();
                  if (!input.isEmpty()) {
                      if (isValidCVV(input)) {
                          cardCVV.setError(null);
                          isValideCVVvalue=true;
                      } else {
                          cardCVV.setError("Enter a valid CVV");
                          isValideCVVvalue=false;
                      }
                  } else {
                      cardCVV.setError(null);
                      isValideCVVvalue=true;
                  }
              }

              @Override
              public void afterTextChanged(Editable editable) {
              }
          });






        orderPlaceBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



                String name,email,address,phoneNumber,userUID,inputCardNumber,inputCardExpiry,inputCardCVV;
                Double totalCost;

                name=customerName.getText().toString();
                email=customerEmail.getText().toString();
                address=customerAddress.getText().toString();
                phoneNumber=customerPhone.getText().toString();
                cartItemsadded=CommonSingleton.getInstance().getCartItems();
                userUID=CommonSingleton.getInstance().getUserUID();
                totalCost=CommonSingleton.getInstance().getTotalPrice();
                inputCardNumber=cardNumber.getText().toString();
                inputCardExpiry=cardExpiry.getText().toString();
                inputCardCVV=cardCVV.getText().toString();


                Orders order=new Orders(name,address,email,phoneNumber,userUID,paymentOption,inputCardNumber,inputCardExpiry,inputCardCVV,totalCost,cartItemsadded);

                if(TextUtils.isEmpty(name)){ // to check if Name is empty or not
                    Toast.makeText(CheckOutActivity.this,"Please enter a Name",Toast.LENGTH_SHORT).show();
                }

                else if(TextUtils.isEmpty(email)){ // to check if Name is empty or not
                    Toast.makeText(CheckOutActivity.this,"Please enter an Email",Toast.LENGTH_SHORT).show();
                }
                else if(isValidEmail == false){
                    Toast.makeText(CheckOutActivity.this,"Please enter a valid email address",
                            Toast.LENGTH_SHORT).show();

                }
                else if(TextUtils.isEmpty(phoneNumber)){ // to check if Name is empty or not
                    Toast.makeText(CheckOutActivity.this,"Please enter a phone number",Toast.LENGTH_SHORT).show();
                }
                else if (!isValidPhoneNumber(phoneNumber)) {
                    Toast.makeText(CheckOutActivity.this,"Please enter a valid phone number",
                            Toast.LENGTH_SHORT).show();

                }

               else if(TextUtils.isEmpty(address)){ // to check if Name is empty or not
                    Toast.makeText(CheckOutActivity.this,"Please enter an Address",Toast.LENGTH_SHORT).show();
                }


               if(paymentOption == "Card"){
                    if(TextUtils.isEmpty(inputCardNumber)){ // to check if Name is empty or not
                       Toast.makeText(CheckOutActivity.this,"Please enter a  Card number",Toast.LENGTH_SHORT).show();
                   }
                   else if (!isValidCard) {
                       Toast.makeText(CheckOutActivity.this,"Please enter a valid Card Number",
                               Toast.LENGTH_SHORT).show();;

                   }

                   else if(TextUtils.isEmpty(inputCardExpiry)){ // to check if Name is empty or not
                       Toast.makeText(CheckOutActivity.this,"Please enter a card expiry",Toast.LENGTH_SHORT).show();
                   }
                   else if (!isValidExpiryDate) {
                       Toast.makeText(CheckOutActivity.this,"Please enter a valid Card Expiry Date",
                               Toast.LENGTH_SHORT).show();;

                   }

                   else if(TextUtils.isEmpty(inputCardCVV)){ // to check if Name is empty or not
                       Toast.makeText(CheckOutActivity.this,"Please enter a CVV",Toast.LENGTH_SHORT).show();
                   }
                   else if (!isValideCVVvalue) {
                       Toast.makeText(CheckOutActivity.this,"Please enter a valid CVV Number",
                               Toast.LENGTH_SHORT).show();;

                   }

               }

               else {
                    myRef.child(name).setValue(order).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            Intent myIntent=new Intent(CheckOutActivity.this,AfterCheckoutActivity.class);
                            startActivity(myIntent);
                            CommonSingleton.getInstance().getCartItems().clear();
                            finish();
                        }
                    });
               }
                 isValidEmail =true;
                 isValidPhone=true;
                isValidCard=true;
                 isValidExpiryDate=true;
                 isValideCVVvalue=true;

            }
        });
    }

    // Phone Validations
    public boolean isValidPhoneNumber(String number) {
        String regex = "\\d{10}|\\d{3}-\\d{3}-\\d{4}";
        return number.matches(regex) && number.length() <= 12;
    }

    // Card Number Validations
    private boolean isValidCardNumber(String cardNumber) {
        String regex = "\\d{4}-\\d{4}-\\d{4}-\\d{4}";
        return cardNumber.matches(regex) && cardNumber.length() <= 19;
    }
    private boolean isValidexpiry(String cardNumber) {
        String regex = "^(0[1-9]|1[0-2])/(\\d{2})$";
        return cardNumber.matches(regex) && cardNumber.length() <= 5;
    }
    private boolean isValidCVV(String cvv) {
        String regex = "\\d{3}";
        return cvv.matches(regex)&& cvv.length() <= 3;
    }
}