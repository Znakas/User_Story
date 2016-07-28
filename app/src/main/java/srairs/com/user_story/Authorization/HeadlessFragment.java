package srairs.com.user_story.Authorization;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import srairs.com.user_story.Constants;

/**
 * Created by Andrii on 30.04.2016.
 */
public class HeadlessFragment extends Fragment {

    public String savedFirstName = "not saved";
    public String savedLastName = "not saved";
    public String savedDate = "not saved";
    public String savedMail = "not saved";
    public String savedPass = "not saved ";

    Bundle bundle = getArguments();
    public User loggedUser;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        setRetainInstance(true);
        Bundle bundle = getArguments();
        loggedUser = (User) bundle.getSerializable(Constants.BUNDLE_USER);
        Log.d(Constants.MyLog, "Pr1vet" + loggedUser.getFname());

        return null;
    }
}
