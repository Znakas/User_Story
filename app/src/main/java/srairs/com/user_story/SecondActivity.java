package srairs.com.user_story;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import srairs.com.user_story.Articles.ArticlesList;
import srairs.com.user_story.Authorization.HeadlessFragment;
import srairs.com.user_story.Authorization.User;
import srairs.com.user_story.Profile.Profile;

/**
 * Created by Andrii on 15.05.2016.
 */
public class SecondActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {

    private Fragment headlessFragment;
    private User user;

    private DrawerLayout mDrawerLayout;
    private ListView mDrawerList;
    private String mTitle = "Navigation Drawer";
    private String[] items;
    private int selectedPosition;
    private boolean firstInvoked = false;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);


        items = getResources().getStringArray(R.array.menus);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerList = (ListView) findViewById(R.id.drawer_list);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.drawer_list_item, items);
        mDrawerList.setAdapter(adapter);
        mDrawerList.setOnItemClickListener(this);


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        if (toolbar != null) {
            toolbar.setTitleTextColor(getResources().getColor(android.R.color.white));
            setSupportActionBar(toolbar);
        }
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
        if (savedInstanceState == null) {
            firstInvoked = true;
            getUser();
            invokeHeadlessFragment(user);
            invokeArticlesListFragment();

        }
        Log.d(Constants.MyLog, "Second activity invoked");
    }

    private void getUser() {
        Intent intent = getIntent();
        user = (User) intent.getSerializableExtra(Constants.INTENT_USER);
        Log.d(Constants.MyLog, "getUser(): " + user.getFname());

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // handle arrow click here
        if (item.getItemId() == android.R.id.home) {
            Log.d(Constants.MyLog, "back pressed in toolbar");
            onBackPressed(); // close this activity and return to preview activity (if there is any)
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        switch (position) {
            case 0:
                /*
                if (firstInvoked) {
                    invokeProfileFragment(user);
                    firstInvoked = false;
                } else {
                invokeProfileFragment();
                }*/
                invokeProfileFragment();
                break;
            case 1:
                invokeArticlesListFragment();
                break;
        }
    }

    private void invokeHeadlessFragment(User user) {

        Bundle bundle = new Bundle();
        bundle.putSerializable(Constants.BUNDLE_USER, user);

        headlessFragment = new HeadlessFragment();
        headlessFragment.setRetainInstance(true);

        headlessFragment.setArguments(bundle);
        getSupportFragmentManager()
                .beginTransaction()
                .add(headlessFragment, "headless")
                .commit();
    }

    public void invokeArticlesListFragment() {
        Log.d(Constants.MyLog, "Fragment ArticlesList invoked");
        Fragment articlesListFragment = new ArticlesList();
        articlesListFragment.setRetainInstance(true);
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_place, articlesListFragment, "fragment_articles_list")
                //.addToBackStack("myStack")
                .commit();
    }


    public void invokeProfileFragment() {
        Log.d(Constants.MyLog, "Fragment Profile invoked");
        Fragment profileFragment = new Profile();
        profileFragment.setRetainInstance(true);
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_place, profileFragment, "fragment_fragment_profile")
                //.addToBackStack("myStack")
                .commit();
    }

/*
    public void invokeProfileFragment(User user) {
        Log.d(Constants.MyLog, "Fragment Profile invoked with user: "+user.getFname());

        Bundle userBundle = new Bundle();
        userBundle.putSerializable(Constants.BUNDLE_USER, user);

        Fragment profileFragment = new Profile();
        profileFragment.setArguments(userBundle);

        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_place, profileFragment, "fragment_profile")
                //.addToBackStack("myStack")
                .commit();
    }*/


    @Override
    public void onBackPressed() {
        int count = getSupportFragmentManager().getBackStackEntryCount();
        String sCount = Integer.toString(count);
        if (count == 0) {
            super.onBackPressed();
            //this.finish();
            Toast.makeText(getApplicationContext(), "count = 0 " + sCount, Toast.LENGTH_SHORT).show();
        }

        if (count >= 1) {
            // Toast.makeText(getApplicationContext(), "count >1 " + sCount, Toast.LENGTH_SHORT).show();
            Log.d(Constants.MyLog, "count >1 " + sCount);

            for (int i = 0; count >= 0; count--) {
                getSupportFragmentManager().popBackStack();
                //Toast.makeText(getApplicationContext(), "count >1 " + sCount, Toast.LENGTH_SHORT).show();
                //  Log.d(Constants.MyLog, "count >1 " + sCount);

            }
            Toast.makeText(getApplicationContext(), "count >1 " + sCount, Toast.LENGTH_SHORT).show();
            Log.d(Constants.MyLog, "count >1 " + sCount);

            invokeArticlesListFragment();

        }

        /*if (count >= 2) {
                //getSupportFragmentManager().popBackStack();
                //count--;
                Toast.makeText(getApplicationContext(), "count 2", Toast.LENGTH_SHORT).show();
            }
            getFragmentManager().popBackStack();*/

    }


//    public HeadlessFragment getHeadlessFragmentData(){
//        return (HeadlessFragment) getSupportFragmentManager().findFragmentByTag("headless");
//    }
}
