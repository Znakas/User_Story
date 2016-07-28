package srairs.com.user_story.Articles;

import android.database.Cursor;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.AdapterContextMenuInfo;
import srairs.com.user_story.Constants;
import srairs.com.user_story.DB;
import srairs.com.user_story.R;

/**
 * Created by Andrii on 12.04.2016.
 */
public class ArticlesList extends Fragment implements View.OnClickListener {
    private static final int CM_DELETE_ID = 1;
    private static final int CM_UPDATE_ID = 2;

    private SimpleCursorAdapter adapter;
    private Cursor c;
    private DB myDb;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View articlesView = inflater.inflate(R.layout.fragment_articles_list, container, false);
        ListView list_view_articles = (ListView) articlesView.findViewById(R.id.list_view_articles);

        FloatingActionButton btn_add_article = (FloatingActionButton) articlesView.findViewById(R.id.btn_add_article);
        //Button btn_clear = (Button) articlesView.findViewById(R.id.btn_clear);

        btn_add_article.setOnClickListener(this);
        //btn_clear.setOnClickListener(this);

        myDb = new DB(getContext());
        myDb.openDB();

        c = myDb.getAllItems();
        getActivity().startManagingCursor(c);

        String[] from = new String[]{Constants.COLUMN_ARTICLE, Constants.COLUMN_TITLE};
        int[] to = new int[]{R.id.itmTitle, R.id.itmArticle};

        adapter = new SimpleCursorAdapter(getContext(), R.layout.fragment_article_list_item, c, from, to, 0);
        list_view_articles.setAdapter(adapter);
        list_view_articles.setOnCreateContextMenuListener(this);

        Bundle bundle = this.getArguments();
        if (bundle != null) {
            if (bundle.containsKey(Constants.TITLE) & bundle.containsKey(Constants.ARTICLE)) {
                String title = bundle.getString(Constants.TITLE);
                String article = bundle.getString(Constants.ARTICLE);
                addArticle(article, title);
                bundle.clear();
            }
        }

        list_view_articles.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View itemClicked, int position, long id) {
                Log.d(Constants.MyLog, "onItemClick");
                // send articles_list_item data throught bundle to view fragment
                getClickedTitle(itemClicked);
                getClickedArticle(itemClicked);


                Log.d(Constants.MyLog, "title " + getClickedTitle(itemClicked) + " article " + getClickedArticle(itemClicked));
                Bundle bundle = new Bundle();
                bundle.putString(Constants.TITLE_VIEW, getClickedTitle(itemClicked));
                bundle.putString(Constants.ARTICLE_VIEW, getClickedArticle(itemClicked));
                invokeViewArticleFragment(bundle);

                c.requery();
                adapter.notifyDataSetChanged();
            }
        });

        list_view_articles.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {

                return false;
            }
        });

        readArticles();
        return articlesView;
    }


    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        menu.add(0, CM_DELETE_ID, 0, R.string.delete_item);
        menu.add(0, CM_UPDATE_ID, 0, R.string.update_item);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {

        AdapterContextMenuInfo info = (AdapterContextMenuInfo) item.getMenuInfo();
        switch (item.getItemId()) {
            case CM_DELETE_ID:
                Log.d(Constants.MyLog, "++");

                Log.d(Constants.MyLog, "Try to delete articles_list_item id: " + info.id + "Position " + info.position);
                myDb.deleteItem(info.id);
                c.requery();
                adapter.notifyDataSetChanged();
                Log.d(Constants.MyLog, "DELETED info.pos =" + info.position);
                break;
            case CM_UPDATE_ID:
                View mView = adapter.getView(info.position, null, null);
                invokeChangeArticleFragment(getClickedTitle(mView), getClickedArticle(mView), info.id);
                break;
        }
        return super.onContextItemSelected(item);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_add_article:
                invokeAddArticleFragment();
                break;
//            case R.id.btn_clear:
//                clearDB();
//                break;
        }
    }

//    private void clearDB() {
//        myDb.deleteAllItems();
//        c.requery();
//        adapter.notifyDataSetChanged();
//    }

    public void addArticle(String title, String article) {
        myDb.addItem(title, article);
        c.requery();
    }

    public void updateArticle(String title, String article, long id) {
        myDb.updateItem(title, article, id);
        c.requery();
        adapter.notifyDataSetChanged();
    }

    private void readArticles() {

    }

    private String getClickedTitle(View itemClicked) {
        TextView itmTitle = (TextView) itemClicked.findViewById(R.id.itmTitle);
        return itmTitle.getText().toString();
    }

    private String getClickedArticle(View itemClicked) {
        TextView itmArticle = (TextView) itemClicked.findViewById(R.id.itmArticle);
        return itmArticle.getText().toString();
    }

    private void invokeViewArticleFragment(Bundle bundle) {
        Fragment viewArticle;
        viewArticle = new ArticleView();
        viewArticle.setArguments(bundle);
        getFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_place, viewArticle, "viewArticle")
                .addToBackStack("myStack")
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                .commit();
    }

    private void invokeChangeArticleFragment(String title, String article, long id) {

        Bundle bundle = new Bundle();
        bundle.putString(Constants.TITLE, title);
        bundle.putString(Constants.ARTICLE, article);
        bundle.putLong(Constants.ITEM_ID, id);

        Fragment changeArticle;

        changeArticle = new UpdateArticle();
        changeArticle.setArguments(bundle);

        getFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_place, changeArticle, "changeArticle")
                .addToBackStack("myStack")
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                .commit();
    }

    private void invokeAddArticleFragment() {
        Fragment addArticle;
        addArticle = new AddArticle();
        getFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_place, addArticle, "addArticle")
                .addToBackStack("myStack")
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                .commit();
        Toast.makeText(getContext(), "updated", Toast.LENGTH_SHORT).show();
    }
}




