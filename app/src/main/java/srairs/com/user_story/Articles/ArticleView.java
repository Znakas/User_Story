package srairs.com.user_story.Articles;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import srairs.com.user_story.Constants;
import srairs.com.user_story.R;

/**
 * Created by Andrii on 24.04.2016.
 */
public class ArticleView extends Fragment {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View viewArticle = inflater.inflate(R.layout.fragment_article_view, container, false);
        TextView txt_view_title = (TextView) viewArticle.findViewById(R.id.txt_view_title);
        TextView txt_view_article = (TextView) viewArticle.findViewById(R.id.txt_view_article);

        Bundle bundle = this.getArguments();
        if (bundle != null) {
            if (bundle.containsKey(Constants.TITLE_VIEW)) {
                txt_view_title.setText(bundle.getString(Constants.TITLE_VIEW));
            }
            if (bundle.containsKey(Constants.ARTICLE_VIEW)) {
                txt_view_article.setText(bundle.getString(Constants.ARTICLE_VIEW));
            }
        }
        return viewArticle;
    }
}