package srairs.com.user_story.Articles;

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

import srairs.com.user_story.Constants;
import srairs.com.user_story.R;

/**
 * Created by Andry on 14.07.2016.
 */

public class UpdateArticle extends Fragment implements View.OnClickListener {

    private EditText update_title_edit_text, update_article_edit_text;
    private Button update_accept_btn;
    private long id;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View changeView = inflater.inflate(R.layout.fragment_update_article, container, false);

        update_title_edit_text = (EditText) changeView.findViewById(R.id.update_title_edit_text);
        update_article_edit_text = (EditText) changeView.findViewById(R.id.update_article_edit_text);
        update_accept_btn = (Button) changeView.findViewById(R.id.update_accept_btn);
        update_accept_btn.setOnClickListener(this);
        if (savedInstanceState == null) {
            Bundle bundle = getArguments();
            if (bundle != null) {
                id = bundle.getLong(Constants.ITEM_ID);
                update_title_edit_text.setText(bundle.getString(Constants.TITLE));
                update_article_edit_text.setText(bundle.getString(Constants.ARTICLE));
                bundle.clear();
                Log.d(Constants.MyLog, "WE have " + bundle.getString(Constants.TITLE) + " " + bundle.getString(Constants.ARTICLE));
            }
        }
        return changeView;
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.update_accept_btn:
                invokeArticlesListFragment();
                break;
        }
    }

    private void invokeArticlesListFragment() {
        String title = update_title_edit_text.getText().toString();
        String article = update_article_edit_text.getText().toString();
        getArticlesListFragmentData().updateArticle(article, title, id);
        getFragmentManager()
                .popBackStack();
    }


    public ArticlesList getArticlesListFragmentData() {
        return (ArticlesList) getFragmentManager().findFragmentByTag("fragment_articles_list");
    }
}
