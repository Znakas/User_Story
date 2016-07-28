package srairs.com.user_story.Profile;

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
import android.widget.Toast;

import srairs.com.user_story.Authorization.HeadlessFragment;
import srairs.com.user_story.Authorization.User;
import srairs.com.user_story.Constants;
import srairs.com.user_story.R;

/**
 * Created by Andrii on 24.04.2016.
 */
public class Profile extends Fragment implements View.OnClickListener {

    EditText profile_first_name, profile_last_name, profile_date, profile_mail, profile_pass;
    Button btn_update;
    User user;
    SharedPreferences sPref;
    String oldMail;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View profileView = inflater.inflate(R.layout.fragment_profile, container, false);

        profile_first_name = (EditText) profileView.findViewById(R.id.profile_first_name);
        profile_last_name = (EditText) profileView.findViewById(R.id.profile_last_name);
        profile_date = (EditText) profileView.findViewById(R.id.profile_date);
        profile_mail = (EditText) profileView.findViewById(R.id.profile_mail);
        profile_pass = (EditText) profileView.findViewById(R.id.profile_pass);
        btn_update = (Button) profileView.findViewById(R.id.btn_update_profile);
        btn_update.setOnClickListener(this);

        Bundle bundle = getArguments();
        if (bundle != null) {
            getHeadlessFragmentData().loggedUser = (User) bundle.getSerializable(Constants.BUNDLE_USER);
            bundle.clear();
        }

        user = getHeadlessFragmentData().loggedUser;
        try {
            profile_first_name.setText(user.getFname());
            profile_last_name.setText(user.getLname());
            profile_date.setText(user.getDate());
            profile_mail.setText(user.getMail());
            profile_pass.setText(user.getPass());
            //mail key for old user
            oldMail = profile_mail.getText().toString();

            Log.d(Constants.MyLog, "oldMail :" + oldMail);
            Toast.makeText(getContext(), getHeadlessFragmentData().loggedUser.getFname() + "HEADLESS DATA", Toast.LENGTH_SHORT).show();
        } catch (NullPointerException ex) {
            ex.printStackTrace();
            this.getActivity().finish();
        }
        return profileView;
    }

    @Override
    public void onClick(View v) {
        // update user data
        String newFirstName = profile_first_name.getText().toString();
        String newLastName = profile_last_name.getText().toString();
        String newDate = profile_date.getText().toString();
        String newMail = profile_mail.getText().toString();
        String newPass = profile_pass.getText().toString();

        sPref = getActivity().getSharedPreferences(Constants.MY_PREFS_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor ed = sPref.edit();

        //delete old user
        ed.remove(Constants.SAVED_FIRST_NAME + oldMail);
        ed.remove(Constants.SAVED_LAST_NAME + oldMail);
        ed.remove(Constants.SAVED_DATE + oldMail);
        ed.remove(Constants.SAVED_MAIL + oldMail);
        ed.remove(Constants.SAVED_PASS + oldMail);

        //write new  user
        ed.putString(Constants.SAVED_FIRST_NAME + newMail, newFirstName);
        ed.putString(Constants.SAVED_LAST_NAME + newMail, newLastName);
        ed.putString(Constants.SAVED_DATE + newMail, newDate);
        ed.putString(Constants.SAVED_MAIL + newMail, newMail);

        Log.d(Constants.MyLog, "putString newMail: " + newMail);

        ed.putString(Constants.SAVED_PASS + newMail, newPass);
        ed.apply();

        getHeadlessFragmentData().loggedUser = new User(newFirstName, newLastName, newDate, newMail, newPass);

        Toast.makeText(getContext(), "User " + getHeadlessFragmentData().loggedUser.getFname() + " updated", Toast.LENGTH_SHORT).show();
    }

    public HeadlessFragment getHeadlessFragmentData() {
        return (HeadlessFragment) getFragmentManager().findFragmentByTag("headless");
    }
}
