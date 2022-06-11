package com.urrecliner.myholybible;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.BackgroundColorSpan;
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
import static com.urrecliner.myholybible.Vars.searchNext;
import static com.urrecliner.myholybible.Vars.searchResults;
import static com.urrecliner.myholybible.Vars.searchText;
import static com.urrecliner.myholybible.Vars.shortBibleNames;
import static com.urrecliner.myholybible.Vars.textSizeBibleBody;
import static com.urrecliner.myholybible.Vars.topTab;

class SearchAdapter extends RecyclerView.Adapter<SearchAdapter.ViewHolder>  {

    @Override
    public int getItemCount() {
        return  (searchResults == null)? 0:searchResults.size();
    }

    @NonNull
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.search_item, parent, false);
        return new ViewHolder(view);
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        TextView tvVerse, tvText;
        ViewHolder(final View itemView) {
            super(itemView);

            tvVerse = (TextView) itemView.findViewById(R.id.result_Verse);
            tvText = (TextView) itemView.findViewById(R.id.result_Text);
            tvText.setTextSize(textSizeBibleBody*2/7);

            View view = itemView.findViewById(R.id.search_item);
            view.setOnClickListener(view1 -> jump2Searched(getAdapterPosition()));
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
//        holder.tvBible.setText(shortBibleNames[searchResult.getBible()]);
        String s = shortBibleNames[searchResult.getBible()]+"\n"+searchResult.getChapter()+":"+searchResult.getVerse();
        holder.tvVerse.setText(s);
        s = searchResult.getText();
        String[] verses = s.split("\n");
        SpannableString ss = new SpannableString(s);
        int sLen = verses[0].length();
        int tLen = 0;
        tLen += sLen + 1;
        sLen = verses[1].length();
        ss.setSpan(new BackgroundColorSpan(0x3845B2D9), tLen, tLen + sLen, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        int kPos = s.indexOf(searchText);
        while (kPos > 0) {
            ss.setSpan(new BackgroundColorSpan(0xFF00FF00), kPos, kPos + searchText.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            kPos = s.indexOf(searchText, kPos+2);
        }

        holder.tvText.setText(ss);

    }

}