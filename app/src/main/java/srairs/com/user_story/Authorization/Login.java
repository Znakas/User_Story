package srairs.com.user_story.Authorization;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import srairs.com.user_story.Constants;
import srairs.com.user_story.R;
import srairs.com.user_story.SecondActivity;

/**
 * Created by Andrii on 13.04.2016.
 */
public class Login extends Fragment implements View.OnClickListener {

    private User user;
    private EditText edit_login_mail, edit_login_password;
    private Button btn_login;
    private TextView txt_register, txt_view_welcome;
    private Fragment regFrament;
    private SharedPreferences prefs;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View loginView = inflater.inflate(R.layout.fragment_auth_login, container, false);

        edit_login_mail = (EditText) loginView.findViewById(R.id.edit_login_mail);
        edit_login_password = (EditText) loginView.findViewById(R.id.edit_login_password);
        btn_login = (Button) loginView.findViewById(R.id.btn_login);
        txt_register = (TextView) loginView.findViewById(R.id.txt_register);
        txt_view_welcome = (TextView) loginView.findViewById(R.id.txt_view_welcome);
        txt_register.setClickable(true);
        txt_register.setOnClickListener(this);
        btn_login.setOnClickListener(this);

        prefs = getActivity().getSharedPreferences(Constants.MY_PREFS_NAME, Context.MODE_PRIVATE);

        //getLoggedUser();
        /* ***sets autologin**/


        return loginView;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_login:
                checkData();
                break;
            case R.id.txt_register:
                invokeRegFragment();
                break;
        }
    }

    public void getLoggedUser() {
        // get User, if logged, else - sey Hi
        if (prefs.contains(Constants.SAVED_LAST_EMAIl)) {
            prefs.getString(Constants.SAVED_LAST_EMAIl, "no email");
            prefs.getString(Constants.SAVED_LAST_PASSWORD, "no pass");
            String loggedMail = prefs.getString(Constants.SAVED_LAST_EMAIl, "Hi! Input data, or register");
            String loggedPass = prefs.getString(Constants.SAVED_LAST_PASSWORD, "Hi! Input data, or register");

            SharedPreferences.Editor ed = prefs.edit();
            ed.putString(Constants.USER_ID, loggedMail);
            ed.apply();
            login(loggedMail, loggedPass);
        } else txt_view_welcome.setText("Hi! Input data, or register");
    }

    private void setLoggedUser(String loggedEmail, String loggedPass) {
        SharedPreferences.Editor ed = prefs.edit();
        ed.putString(Constants.SAVED_LAST_EMAIl, loggedEmail);
        ed.putString(Constants.SAVED_LAST_PASSWORD, loggedPass);
        ed.apply();
    }

    void checkData() {
        String inputMail = edit_login_mail.getText().toString();
        String inputPass = edit_login_password.getText().toString();

        String savedPass = prefs.getString(Constants.SAVED_PASS + inputMail, "no pass saved");
        String savedMail = prefs.getString(Constants.SAVED_MAIL + inputMail, "wrong_mail");

        if (inputMail.equals(savedMail) & inputPass.equals(savedPass)) {
            Toast.makeText(getContext(), "equasls", Toast.LENGTH_SHORT).show();
            login(inputMail, inputPass);
            setLoggedUser(inputMail, inputPass); // write to Preferences last logged user
        } else {
            txt_view_welcome.setText("Wrong data");
            edit_login_mail.setText("");
            edit_login_password.setText("");
        }
    }

    public void login(String inputLogin, String inputPassword) {
        Log.d(Constants.MyLog, "login " + inputLogin + " " + inputPassword);
        String savedMail = prefs.getString(Constants.SAVED_MAIL + inputLogin, "wrong_mail");
        String savedPass = prefs.getString(Constants.SAVED_PASS + inputLogin, "no such pass saved");
        String savedFirstName = prefs.getString(Constants.SAVED_FIRST_NAME + inputLogin, "no fname saved");
        String savedLastName = prefs.getString(Constants.SAVED_LAST_NAME + inputLogin, "no lname saved");
        String savedDate = prefs.getString(Constants.SAVED_DATE + inputLogin, "no data saved");
        createUser(savedFirstName, savedLastName, savedDate, savedMail, savedPass);
    }

    private void createUser(String savedFirstName, String savedLastName, String savedDate, String savedMail, String savedPass) {
        user = new User(savedFirstName, savedLastName, savedDate, savedMail, savedPass);
        Log.d(Constants.MyLog, "Equals! Login fragment invoked user: " + savedFirstName + " " + savedLastName);
        invokeSecondActivity(user);
        getActivity().finish();
        Log.d(Constants.MyLog, "Main Activity finished ");

    }

    private void invokeSecondActivity(User user) {
        Log.d(Constants.MyLog, "invokeSecondActivity with intent user: " + user.getFname());

        Intent intent = new Intent(getContext(), SecondActivity.class);
        intent.putExtra(Constants.INTENT_USER, user);
        startActivity(intent);

    }

    public void invokeRegFragment() {
        Log.d(Constants.MyLog, "Fragment Reg invoked");
        regFrament = new Reg();
        getFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_place, regFrament)
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                .addToBackStack("myStack")
                .commit();
    }
}