package srairs.com.user_story;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ListView;
import android.widget.Toast;

import srairs.com.user_story.Authorization.HeadlessFragment;
import srairs.com.user_story.Authorization.Login;

public class MainActivity extends AppCompatActivity {
    Fragment loginFragment;

    private DrawerLayout mDrawerLayout;
    private ListView mDrawerList;
    private String mTitle = "Navigation Drawer";
    private String[] items;
    private int selectedPosition;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.d(Constants.MyLog, "Main activity invoked");
        if (savedInstanceState == null) {
            invokeLoginFragment();
        }
    }

    private void logged() {
        SharedPreferences prefs = getSharedPreferences(Constants.MY_PREFS_NAME, Context.MODE_PRIVATE);
        if (prefs.contains(Constants.SAVED_LAST_EMAIl)) {
            prefs.getString(Constants.SAVED_LAST_EMAIl, getString(R.string.no_email));
            prefs.getString(Constants.SAVED_LAST_PASSWORD, getString(R.string.no_password));
            String loggedMail = prefs.getString(Constants.SAVED_LAST_EMAIl, getString(R.string.no_email));
            String loggedPass = prefs.getString(Constants.SAVED_LAST_PASSWORD, getString(R.string.no_password));
            Toast.makeText(getApplicationContext(), loggedMail + " " + loggedPass, Toast.LENGTH_SHORT).show();
        }
    }

    public void invokeLoginFragment() {
        Log.d(Constants.MyLog, "Fragment Login1 invoked");
        loginFragment = new Login();
        loginFragment.setRetainInstance(true);
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_place, loginFragment, "fragment_auth_login")
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                .commit();
    }
}