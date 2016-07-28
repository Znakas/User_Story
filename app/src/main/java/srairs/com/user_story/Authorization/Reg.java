package srairs.com.user_story.Authorization;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.HashMap;

import srairs.com.user_story.Constants;
import srairs.com.user_story.R;

/**
 * Created by Andrii on 13.04.2016.
 */
public class Reg extends Fragment implements View.OnClickListener {

    private EditText edit_first_name, edit_last_name, edit_mail, edit_date, edit_password;
    private Button btn_register;
    private User user;
    private SharedPreferences sPref;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View RegView = inflater.inflate(R.layout.fragment_registration, container, false);
        edit_first_name = (EditText) RegView.findViewById(R.id.edit_first_name);
        edit_last_name = (EditText) RegView.findViewById(R.id.edit_last_name);
        edit_mail = (EditText) RegView.findViewById(R.id.edit_mail);
        edit_date = (EditText) RegView.findViewById(R.id.edit_date);
        edit_password = (EditText) RegView.findViewById(R.id.edit_password);
        btn_register = (Button) RegView.findViewById(R.id.btn_register);
        btn_register.setOnClickListener(this);
        return RegView;
    }

    @Override
    public void onClick(View v) {
        String firstName = edit_first_name.getText().toString();
        String lastName = edit_last_name.getText().toString();
        String date = edit_date.getText().toString();
        String mail = edit_mail.getText().toString();
        String pass = edit_password.getText().toString();

        switch(v.getId()){
            case R.id.btn_register:
                register(firstName, lastName, date, mail, pass);
                break;
        }
    }

    private void register(String firstName, String lastName, String date, String mail, String pass){
        if (mail.equals("")||pass.equals("")){
            Toast.makeText(getContext(), R.string.empty, Toast.LENGTH_SHORT).show();
            Log.d(Constants.MyLog, "Get empty field...");
        }
         else {
            // write into Preferences
            sPref = getActivity().getSharedPreferences(Constants.MY_PREFS_NAME, Context.MODE_PRIVATE);
            SharedPreferences.Editor ed = sPref.edit();

            ed.putString(Constants.SAVED_FIRST_NAME+mail, firstName);
            ed.putString(Constants.SAVED_LAST_NAME+mail, lastName);
            ed.putString(Constants.SAVED_DATE+mail, date);
            ed.putString(Constants.SAVED_MAIL+mail, mail);
            ed.putString(Constants.SAVED_PASS+mail, pass);
            ed.apply();
            user = new User(firstName, lastName, date, mail, pass);
            Log.d(Constants.MyLog, "New user registered:" + user.getFname() + " " + user.getLname() + " " + user.getDate() + " " + user.getMail() + " " + user.getPass());

            getFragmentManager().popBackStack();
            Toast.makeText(getContext(), R.string.success_reg, Toast.LENGTH_SHORT).show();

            }
        }
}
