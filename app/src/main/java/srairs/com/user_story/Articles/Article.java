package srairs.com.user_story.Articles;

/**
 * Created by Andrii on 23.04.2016.
 */
public class Article {

    private String title;
    private String article;

    public Article(String title, String article) {
        this.title = title;
        this.article = article;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String sTitle) {
        title = sTitle;
    }

    public String getArticle() {
        return article;
    }

    public void setArticle(String sArticle) {
        article = sArticle;
    }
}
