package com.azizbek.todolist.screens.main;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SortedList;

import com.azizbek.todolist.App;
import com.azizbek.todolist.R;
import com.azizbek.todolist.model.Note;
import com.azizbek.todolist.screens.details.NoteDetailsActivity;

import java.util.List;

public class Adapter extends RecyclerView.Adapter<Adapter.NoteViewHolder> {

    private SortedList<Note> sortedList;

    public Adapter() {

        sortedList = new SortedList<>(Note.class, new SortedList.Callback<Note>() {
            @Override
            public int compare(Note o1, Note o2) {
                if (!o2.done && o1.done) {
                    return 1;
                }
                if (o2.done && !o1.done) {
                    return -1;
                }
                return (int) (o2.timestamp - o1.timestamp);
            }

            @Override
            public void onChanged(int position, int count) {
                notifyItemRangeChanged(position, count);
            }

            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public boolean areContentsTheSame(Note oldItem, Note newItem) {
                return oldItem.equals(newItem);
            }

            @Override
            public boolean areItemsTheSame(Note item1, Note item2) {
                return item1.uid == item2.uid;
            }

            @Override
            public void onInserted(int position, int count) {
                notifyItemRangeInserted(position, count);
            }

            @Override
            public void onRemoved(int position, int count) {
                notifyItemRangeRemoved(position, count);
            }

            @Override
            public void onMoved(int fromPosition, int toPosition) {
                notifyItemMoved(fromPosition, toPosition);
            }
        });
    }

    @NonNull
    @Override
    public NoteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new NoteViewHolder(
                LayoutInflater.from(parent.getContext()).inflate(R.layout.item_note_list, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull NoteViewHolder holder, int position) {
        holder.bind(sortedList.get(position));
    }

    @Override
    public int getItemCount() {
        return sortedList.size();
    }

    public void setItems(List<Note> notes) {
        sortedList.replaceAll(notes);
    }


    static class NoteViewHolder extends RecyclerView.ViewHolder {

        TextView title;
        TextView time;
        CheckBox completed;
        View delete;

        Note note;

        boolean silentUpdate;

        public NoteViewHolder(@NonNull final View itemView) {
            super(itemView);

            title = itemView.findViewById(R.id.title_text);
            time = itemView.findViewById(R.id.time);
            completed = itemView.findViewById(R.id.completed);
            delete = itemView.findViewById(R.id.delete);

            itemView.setOnClickListener(
                    view -> NoteDetailsActivity.start((Activity) itemView.getContext(), note));

            delete.setOnClickListener(view -> App.getInstance().getNoteDao().delete(note));

            completed.setOnCheckedChangeListener((compoundButton, checked) -> {
                if (!silentUpdate) {
                    note.done = checked;
                    App.getInstance().getNoteDao().update(note);
                }
                updateStrokeOut();
            });

        }

        public void bind(Note note) {
            this.note = note;

            title.setText(note.getTitle());
            time.setText(note.date);
            updateStrokeOut();

            silentUpdate = true;
            completed.setChecked(note.done);
            silentUpdate = false;
        }

        private void updateStrokeOut() {
            if (note.done) {
                title.setPaintFlags(title.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
                title.setTextColor(Color.GRAY);
            } else {
                title.setPaintFlags(title.getPaintFlags() & ~Paint.STRIKE_THRU_TEXT_FLAG);
            }
        }
    }
}
