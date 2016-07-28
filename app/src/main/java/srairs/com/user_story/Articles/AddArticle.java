package srairs.com.user_story.Articles;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import srairs.com.user_story.R;

/**
 * Created by Andrii on 22.04.2016.
 */
public class AddArticle extends Fragment implements View.OnClickListener {
    Button btn_add;
    EditText edit_title, edit_article;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View addView = inflater.inflate(R.layout.fragment_add_article, container, false);
        edit_title = (EditText) addView.findViewById(R.id.edit_title);
        edit_article = (EditText) addView.findViewById(R.id.edit_article);
        btn_add = (Button) addView.findViewById(R.id.btn_add);
        btn_add.setOnClickListener(this);
        return addView;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_add:
                invokeArticlesListFragment();
                break;
        }
    }

    private void invokeArticlesListFragment() {
        String title = edit_title.getText().toString();
        String article = edit_article.getText().toString();
        if (title.equals("") | article.equals("")){
            Toast.makeText(getContext(), "Fill data", Toast.LENGTH_SHORT).show();
        }
        else {
            getArticlesList().addArticle(article, title);
            getFragmentManager()
                    .popBackStack();
        }
    }

    public ArticlesList getArticlesList() {
        return (ArticlesList) getFragmentManager().findFragmentByTag("fragment_articles_list");
    }
}