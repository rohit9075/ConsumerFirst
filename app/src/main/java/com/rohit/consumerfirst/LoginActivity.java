package com.rohit.consumerfirst;

import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatTextView;
import android.view.View;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    private final AppCompatActivity activity = LoginActivity.this;

    private NestedScrollView nestedScrollView;

    private TextInputLayout textInputLayoutEmail;
    private TextInputLayout textInputLayoutPassword;

    private TextInputEditText textInputEditTextEmail;
    private TextInputEditText textInputEditTextPassword;

    private AppCompatButton appCompatButtonLogin;

    private AppCompatTextView textViewLinkRegister;

    private InputValidation inputValidation;
    private DatabaseHelper databaseHelper;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(com.rohit.consumerfirst.R.layout.activity_login);
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

        textInputLayoutEmail = (TextInputLayout) findViewById(com.rohit.consumerfirst.R.id.textInputLayoutEmail);
        textInputLayoutPassword = (TextInputLayout) findViewById(com.rohit.consumerfirst.R.id.textInputLayoutPassword);

        textInputEditTextEmail = (TextInputEditText) findViewById(com.rohit.consumerfirst.R.id.textInputEditTextEmail);
        textInputEditTextPassword = (TextInputEditText) findViewById(com.rohit.consumerfirst.R.id.textInputEditTextPassword);

        appCompatButtonLogin = (AppCompatButton) findViewById(com.rohit.consumerfirst.R.id.appCompatButtonLogin);

        textViewLinkRegister = (AppCompatTextView) findViewById(com.rohit.consumerfirst.R.id.textViewLinkRegister);

    }

    /**
     * This method is to initialize listeners
     */
    private void initListeners() {
        appCompatButtonLogin.setOnClickListener(this);
        textViewLinkRegister.setOnClickListener(this);
    }

    /**
     * This method is to initialize objects to be used
     */
    private void initObjects() {
        databaseHelper = new DatabaseHelper(activity);
        inputValidation = new InputValidation(activity);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case com.rohit.consumerfirst.R.id.appCompatButtonLogin:
                verifyFromSQLite();
                break;
            case com.rohit.consumerfirst.R.id.textViewLinkRegister:
                // Navigate to RegisterActivity
                Intent intentRegister = new Intent(getApplicationContext(), RegisterActivity.class);
                startActivity(intentRegister);
                break;
        }
    }

        /**
         * This method is to validate the input text fields and verify login credentials from SQLite
         */
        private void verifyFromSQLite() {
            if (!inputValidation.isInputEditTextFilled(textInputEditTextEmail, textInputLayoutEmail, getString(com.rohit.consumerfirst.R.string.error_message_email))) {
                return;
            }
            if (!inputValidation.isInputEditTextEmail(textInputEditTextEmail, textInputLayoutEmail, getString(com.rohit.consumerfirst.R.string.error_message_email))) {
                return;
            }
            if (!inputValidation.isInputEditTextFilled(textInputEditTextPassword, textInputLayoutPassword, getString(com.rohit.consumerfirst.R.string.error_message_email))) {
                return;
            }

            if (databaseHelper.checkUser(textInputEditTextEmail.getText().toString().trim()
                    , textInputEditTextPassword.getText().toString().trim())) {


                Intent accountsIntent = new Intent(activity, UsersListActivity.class);
                accountsIntent.putExtra("EMAIL", textInputEditTextEmail.getText().toString().trim());
                emptyInputEditText();
                startActivity(accountsIntent);


            } else {
                // Snack Bar to show success message that record is wrong
                Snackbar.make(nestedScrollView, getString(com.rohit.consumerfirst.R.string.error_valid_email_password), Snackbar.LENGTH_LONG).show();
            }
        }

    /**
     * This method is to empty all input edit text
     */
    private void emptyInputEditText() {
        textInputEditTextEmail.setText(null);
        textInputEditTextPassword.setText(null);
    }
    }

