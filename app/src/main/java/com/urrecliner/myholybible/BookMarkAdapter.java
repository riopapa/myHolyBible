package com.urrecliner.myholybible;

import android.graphics.Typeface;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Locale;

import static com.urrecliner.myholybible.Vars.TAB_MODE_NEW;
import static com.urrecliner.myholybible.Vars.TAB_MODE_OLD;
import static com.urrecliner.myholybible.Vars.bookMarkAdapter;
import static com.urrecliner.myholybible.Vars.bookMarks;
import static com.urrecliner.myholybible.Vars.fullBibleNames;
import static com.urrecliner.myholybible.Vars.history;
import static com.urrecliner.myholybible.Vars.mContext;
import static com.urrecliner.myholybible.Vars.makeBible;
import static com.urrecliner.myholybible.Vars.nowBible;
import static com.urrecliner.myholybible.Vars.nowChapter;
import static com.urrecliner.myholybible.Vars.nowHymn;
import static com.urrecliner.myholybible.Vars.nowVerse;
import static com.urrecliner.myholybible.Vars.setActivity;
import static com.urrecliner.myholybible.Vars.topTab;
import static com.urrecliner.myholybible.Vars.utils;

public class BookMarkAdapter extends RecyclerView.Adapter<BookMarkAdapter.ViewHolder>  {

    @Override
    public int getItemCount() {
        return bookMarks.size();
    }

    @NonNull
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.book_mark, parent, false);
        return new ViewHolder(view);
    }

    private static int pos;

    static class ViewHolder extends RecyclerView.ViewHolder {

        TextView tvBibleChapter, tvDateTime;
        View lo = itemView.findViewById(R.id.bookMark);

        ViewHolder(final View itemView) {
            super(itemView);

            tvBibleChapter = (TextView) itemView.findViewById(R.id.bibleChapter);
            tvDateTime = (TextView) itemView.findViewById(R.id.dateTime);

            tvBibleChapter.setOnClickListener(view -> {
                pos = getAdapterPosition();
                jump2BookMark();
            });
            tvBibleChapter.setOnLongClickListener(view -> {
                pos = getAdapterPosition();
                saveOrNot();
                return true;
            });

            tvDateTime.setOnClickListener(view -> {
                pos = getAdapterPosition();
                jump2BookMark();
            });
            tvDateTime.setOnLongClickListener(view -> {
                pos = getAdapterPosition();
                saveOrNot();
                return true;
            });
        }
    }

    private static void saveOrNot() {
        BookMark bookMark = bookMarks.get(pos);
        bookMark.setSave(!bookMark.isSave());
        bookMarks.set(pos, bookMark);
        utils.savePrefers("bookMark", bookMarks);
        bookMarkAdapter.notifyItemChanged(pos);
    }

    private static void jump2BookMark() {
        final BookMark bookMark = bookMarks.get(pos);
        AlertDialog.Builder builder = new AlertDialog.Builder(setActivity);
        builder.setTitle("Book Mark");
        String s = fullBibleNames[bookMark.getBible()] + " " + bookMark.getChapter()+" 장";
        if (bookMark.getVerse() > 0)
            s += " "+bookMark.getVerse()+" 절";
        builder.setMessage(s);
        builder.setPositiveButton(s+" 로 이동",
                (dialog, which) -> {
                    history.push();
                    nowBible = bookMark.getBible();
                    nowChapter = bookMark.getChapter();
                    nowVerse = bookMark.getVerse();
                    nowHymn = 0;
                    topTab = (nowBible < 40) ? TAB_MODE_OLD : TAB_MODE_NEW;
                    if (makeBible == null)
                        makeBible = new MakeBible();
                    makeBible.makeBibleBody();
                    setActivity.finish();
                    setActivity.overridePendingTransition(R.anim.anim_slide_in_right, R.anim.anim_slide_out_left);
                });
        builder.setNegativeButton("삭제",
                (dialog, which) -> {
                    bookMarks.remove(pos);
                    bookMarkAdapter.notifyItemRemoved(pos);
                });
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int pos) {

        final SimpleDateFormat sdfDate = new SimpleDateFormat("yy/MM/dd HH:mm", Locale.US);
        String s;
        BookMark bookMark = bookMarks.get(pos);
        s = fullBibleNames[bookMark.getBible()] + " " + bookMark.getChapter();
        if (bookMark.getVerse()> 0)
            s += ":"+bookMark.getVerse();
        holder.tvBibleChapter.setText(s);
        s = sdfDate.format(bookMark.getWhen());
        holder.tvDateTime.setText(s);
        if (bookMark.isSave()) {
            holder.tvBibleChapter.setTypeface(null, Typeface.BOLD_ITALIC);
            holder.tvDateTime.setTypeface(null, Typeface.BOLD_ITALIC);
        }
        else {
            holder.tvBibleChapter.setTypeface(null, Typeface.NORMAL);
            holder.tvDateTime.setTypeface(null, Typeface.NORMAL);
        }
        int grayed = 200 * pos / (bookMarks.size()+1);
        holder.lo.setBackgroundColor(ContextCompat.getColor(mContext,R.color.screenBodyColor) - grayed - grayed * 256 - grayed * 256 * 256);
    }
}