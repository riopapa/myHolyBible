package com.urrecliner.myholybible;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import static com.urrecliner.myholybible.Vars.TAB_MODE_NEW;
import static com.urrecliner.myholybible.Vars.TAB_MODE_OLD;
import static com.urrecliner.myholybible.Vars.history;
import static com.urrecliner.myholybible.Vars.mainActivity;
import static com.urrecliner.myholybible.Vars.nowBible;
import static com.urrecliner.myholybible.Vars.nowChapter;
import static com.urrecliner.myholybible.Vars.nowVerse;
import static com.urrecliner.myholybible.Vars.searchActivity;
import static com.urrecliner.myholybible.Vars.searchResults;
import static com.urrecliner.myholybible.Vars.shortBibleNames;
import static com.urrecliner.myholybible.Vars.topTab;

class SearchAdapter extends RecyclerView.Adapter<SearchAdapter.ViewHolder>  {

    final String logID = "seachAdapter";

    @Override
    public int getItemCount() {
        return  (searchResults == null)? 0:searchResults.size();
    }

    @NonNull
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.search_item, parent, false);
        return new ViewHolder(view);
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        TextView tvBible, tvChapter, tvText;
        ViewHolder(final View itemView) {
            super(itemView);

            tvBible = (TextView) itemView.findViewById(R.id.result_Bible);
            tvChapter = (TextView) itemView.findViewById(R.id.result_Chapter);
            tvText = (TextView) itemView.findViewById(R.id.result_Text);

            View view = itemView.findViewById(R.id.search_item);
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    jump2Searched(getAdapterPosition());
                }
            });
        }

        private static void jump2Searched(int pos) {

            SearchResult searchResult = searchResults.get(pos);
            nowBible = searchResult.getBible();
            nowChapter = searchResult.getChapter();
            nowVerse = searchResult.getVerse();
            topTab = (nowBible < 40) ? TAB_MODE_OLD : TAB_MODE_NEW;
            searchActivity.finish();
            searchActivity.overridePendingTransition(R.anim.anim_slide_in_right, R.anim.anim_slide_out_left);
            history.push();
            history.push();
            mainActivity.goBackward();
//            mActivity.runOnUiThread(new Runnable() {
//                @Override
//                public void run() {
//                    makeBible.makeBibleBody();
//                }
//            });
        }
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int pos) {

        SearchResult searchResult = searchResults.get(pos);
        holder.tvBible.setText(shortBibleNames[searchResult.getBible()]);
        String s = searchResult.getChapter()+":"+searchResult.getVerse();
        holder.tvChapter.setText(s);
        holder.tvText.setText(searchResult.getText());

    }

}