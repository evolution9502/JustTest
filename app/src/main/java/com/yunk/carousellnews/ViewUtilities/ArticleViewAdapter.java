package com.yunk.carousellnews.ViewUtilities;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.yunk.carousellnews.R;

import java.io.InputStream;
import java.util.List;

import static com.yunk.carousellnews.DataUtilities.RelativeTimeParser.getTimeAgo;

/**
 * Created by bradley on 2019/3/8.
 */

public class ArticleViewAdapter extends RecyclerView.Adapter<ArticleViewAdapter.ViewHolder> {
    private Context mContext;
    private List<Article> mArticleList;

    public ArticleViewAdapter(Context context, List<Article> articleList) {
        mContext = context;
        mArticleList = articleList;
    }

    @Override
    public ArticleViewAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.article_item_card, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ArticleViewAdapter.ViewHolder holder, int position) {
        final Article mArticle = mArticleList.get(position);
        new DownloadImageTask(holder.articleBanner).execute(mArticle.getBanner_url());

        holder.article_title.setText(mArticle.getTitle());
        holder.article_content.setText(mArticle.getDescription());
        holder.article_date.setText(getTimeAgo(mArticle.getTime_created(), mContext));
    }

    @Override
    public int getItemCount() {
        return mArticleList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        ImageView articleBanner;
        TextView article_title, article_content;
        TextView article_date;
        ViewHolder(View itemView) {
            super(itemView);
            articleBanner = itemView.findViewById(R.id.iv_banner);
            article_title = itemView.findViewById(R.id.tv_title);
            article_content = itemView.findViewById(R.id.tv_descr);
            article_date = itemView.findViewById(R.id.tv_date);
        }
    }

    private class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {
        ImageView bmImage;

        public DownloadImageTask(ImageView bmImage) {
            this.bmImage = bmImage;
        }

        protected Bitmap doInBackground(String... urls) {
            String urldisplay = urls[0];
            Bitmap mIcon11 = null;
            try {
                InputStream in = new java.net.URL(urldisplay).openStream();
                mIcon11 = BitmapFactory.decodeStream(in);
            } catch (Exception e) {
                Log.e("Error", e.getMessage());
                e.printStackTrace();
            }
            return mIcon11;
        }

        protected void onPostExecute(Bitmap result) {
            bmImage.setImageBitmap(result);
        }
    }
}
