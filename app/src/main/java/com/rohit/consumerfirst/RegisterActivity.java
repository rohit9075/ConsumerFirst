package com.rohit.consumerfirst;

import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatTextView;
import android.view.View;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener {

    private final AppCompatActivity activity = RegisterActivity.this;

    private NestedScrollView nestedScrollView;

    private TextInputLayout textInputLayoutFirstName;
    private TextInputLayout textInputLayoutLastName;
    private TextInputLayout textInputLayoutDateOfBirth;
    private TextInputLayout textInputLayoutGender;
    private TextInputLayout textInputLayoutEmail;
    private TextInputLayout textInputLayoutPassword;
    private TextInputLayout textInputLayoutConfirmPassword;

    private TextInputEditText textInputEditTextFirstName;
    private TextInputEditText textInputEditTextLastName;
    private TextInputEditText textInputEditTextDateOfBirth;
    private TextInputEditText textInputEditTextGender;
    private TextInputEditText textInputEditTextEmail;
    private TextInputEditText textInputEditTextPassword;
    private TextInputEditText textInputEditTextConfirmPassword;

    private AppCompatButton appCompatButtonRegister;
    private AppCompatTextView appCompatTextViewLoginLink;

    private InputValidation inputValidation;
    private DatabaseHelper databaseHelper;
    private User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(com.rohit.consumerfirst.R.layout.activity_register);
        getSupportActionBar().hide();

        initViews();
        initListeners();
        initObjects();
    }

    /**
     * This method is to initialize views
     */
    private void initViews() {
        nestedScrollView = (NestedScrollView) findViewById(com.rohit.consumerfirst.R.id.nestedScrollView);

        textInputLayoutFirstName = (TextInputLayout) findViewById(com.rohit.consumerfirst.R.id.textInputLayoutFirstName);
        textInputLayoutLastName = (TextInputLayout) findViewById(com.rohit.consumerfirst.R.id.textInputLayoutLastName);
        textInputLayoutDateOfBirth = (TextInputLayout) findViewById(com.rohit.consumerfirst.R.id.textInputLayoutDateOfBirth);
        textInputLayoutGender = (TextInputLayout) findViewById(com.rohit.consumerfirst.R.id.textInputLayoutGender);
        textInputLayoutEmail = (TextInputLayout) findViewById(com.rohit.consumerfirst.R.id.textInputLayoutEmail);
        textInputLayoutPassword = (TextInputLayout) findViewById(com.rohit.consumerfirst.R.id.textInputLayoutPassword);
        textInputLayoutConfirmPassword = (TextInputLayout) findViewById(com.rohit.consumerfirst.R.id.textInputLayoutConfirmPassword);

        textInputEditTextFirstName = (TextInputEditText) findViewById(com.rohit.consumerfirst.R.id.textInputEditTextFirstName);
        textInputEditTextLastName = (TextInputEditText) findViewById(com.rohit.consumerfirst.R.id.textInputEditTextLastName);
        textInputEditTextDateOfBirth = (TextInputEditText) findViewById(com.rohit.consumerfirst.R.id.textInputEditTextDateOfBirth);
        textInputEditTextGender = (TextInputEditText) findViewById(com.rohit.consumerfirst.R.id.textInputEditTextGender);
        textInputEditTextEmail = (TextInputEditText) findViewById(com.rohit.consumerfirst.R.id.textInputEditTextEmail);
        textInputEditTextPassword = (TextInputEditText) findViewById(com.rohit.consumerfirst.R.id.textInputEditTextPassword);
        textInputEditTextConfirmPassword = (TextInputEditText) findViewById(com.rohit.consumerfirst.R.id.textInputEditTextConfirmPassword);

        appCompatButtonRegister = (AppCompatButton) findViewById(com.rohit.consumerfirst.R.id.appCompatButtonRegister);

        appCompatTextViewLoginLink = (AppCompatTextView) findViewById(com.rohit.consumerfirst.R.id.appCompatTextViewLoginLink);

    }

    /**
     * This method is to initialize listeners
     */
    private void initListeners() {
        appCompatButtonRegister.setOnClickListener(this);
        appCompatTextViewLoginLink.setOnClickListener(this);

    }

    /**
     * This method is to initialize objects to be used
     */
    private void initObjects() {
        inputValidation = new InputValidation(activity);
        databaseHelper = new DatabaseHelper(activity);
        user = new User();

    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case com.rohit.consumerfirst.R.id.appCompatButtonRegister:
                postDataToSQLite();
                break;

            case com.rohit.consumerfirst.R.id.appCompatTextViewLoginLink:
                finish();
                break;
        }

    }

    /**
     * This method is to validate the input text fields and post data to SQLite
     */
    private void postDataToSQLite() {
        if (!inputValidation.isInputEditTextFilled(textInputEditTextFirstName, textInputLayoutFirstName, getString(com.rohit.consumerfirst.R.string.error_message_first_name))) {
            return;
        }
        if (!inputValidation.isInputEditTextFilled(textInputEditTextLastName, textInputLayoutLastName, getString(com.rohit.consumerfirst.R.string.error_message_last_name))) {
            return;
        }
        if (!inputValidation.isInputEditTextFilled(textInputEditTextDateOfBirth, textInputLayoutDateOfBirth, getString(com.rohit.consumerfirst.R.string.error_message_date_of_birth))) {
            return;
        }
        if (!inputValidation.isInputEditTextFilled(textInputEditTextGender, textInputLayoutGender, getString(com.rohit.consumerfirst.R.string.error_message_Gender))) {
            return;
        }
        if (!inputValidation.isInputEditTextFilled(textInputEditTextEmail, textInputLayoutEmail, getString(com.rohit.consumerfirst.R.string.error_message_email))) {
            return;
        }
        if (!inputValidation.isInputEditTextEmail(textInputEditTextEmail, textInputLayoutEmail, getString(com.rohit.consumerfirst.R.string.error_message_email))) {
            return;
        }
        if (!inputValidation.isInputEditTextFilled(textInputEditTextPassword, textInputLayoutPassword, getString(com.rohit.consumerfirst.R.string.error_message_password))) {
            return;
        }
        if (!inputValidation.isInputEditTextMatches(textInputEditTextPassword, textInputEditTextConfirmPassword,
                textInputLayoutConfirmPassword, getString(com.rohit.consumerfirst.R.string.error_password_match))) {
            return;
        }

        if (!databaseHelper.checkUser(textInputEditTextEmail.getText().toString().trim())) {

            user.setFirstname(textInputEditTextFirstName.getText().toString().trim());
            user.setLastname(textInputEditTextLastName.getText().toString().trim());
            user.setDateofbirth(textInputEditTextDateOfBirth.getText().toString().trim());
            user.setGender(textInputEditTextGender.getText().toString().trim());
            user.setEmail(textInputEditTextEmail.getText().toString().trim());
            user.setPassword(textInputEditTextPassword.getText().toString().trim());

            databaseHelper.addUser(user);

            // Snack Bar to show success message that record saved successfully
            Snackbar.make(nestedScrollView, getString(com.rohit.consumerfirst.R.string.success_message), Snackbar.LENGTH_LONG).show();
            emptyInputEditText();


        } else {
            // Snack Bar to show error message that record already exists
            Snackbar.make(nestedScrollView, getString(com.rohit.consumerfirst.R.string.error_email_exists), Snackbar.LENGTH_LONG).show();
        }


    }

    /**
     * This method is to empty all input edit text
     */
    private void emptyInputEditText() {
        textInputEditTextFirstName.setText(null);
        textInputEditTextLastName.setText(null);
        textInputEditTextDateOfBirth.setText(null);
        textInputEditTextGender.setText(null);
        textInputEditTextEmail.setText(null);
        textInputEditTextPassword.setText(null);
        textInputEditTextConfirmPassword.setText(null);
    }
}
