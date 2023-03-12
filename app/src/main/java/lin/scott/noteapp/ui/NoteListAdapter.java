package lin.scott.noteapp.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import lin.scott.noteapp.R;
import lin.scott.noteapp.data.models.Note;
import lin.scott.noteapp.persistence.entity.NoteDB;

public class NoteListAdapter extends ListAdapter<Note, NoteListAdapter.NoteViewHolder> {

    public static class NoteViewHolder extends RecyclerView.ViewHolder {

        private final CardView cardView;
        private final TextView title;
        private final TextView content;
//        private final Note note;

        public NoteViewHolder(@NonNull View itemView) {
            super(itemView);
            cardView = itemView.findViewById(R.id.note_item_card_view);

            cardView.setOnClickListener(view -> {
                System.out.println("clicked!!!!!!!!!!!!!!!");
                Navigation.findNavController(itemView).navigate(
                        R.id.action_listNotesFragment_to_createNoteFragment
//                        ,
//                        new Bundle().putParcelable("note", new Note())
                        );
            });

            title   = itemView.findViewById(R.id.title_text);
            content = itemView.findViewById(R.id.content_summary_text);
        }

        public void bind(final Note note) {
            title.setText(note.getTitle());
            content.setText(note.getContent());
            cardView.setOnClickListener(view -> {
                System.out.println("clicked!!!!!!!!!!!!!!!");
                final Bundle bundle = new Bundle();
                System.out.println(note);
                bundle.putParcelable("note", note);
                Navigation.findNavController(itemView).navigate(
                        R.id.action_listNotesFragment_to_createNoteFragment,
                        bundle
                );
            });
        }

        static NoteViewHolder create(ViewGroup parent) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.note_item, parent, false);
            return new NoteViewHolder(view);
        }


        public TextView getTitle() {
            return title;
        }
        public TextView getContent() {
            return content;
        }
    }

    public NoteListAdapter(@NonNull DiffUtil.ItemCallback<Note> diffCallback) {
        super(diffCallback);
    }

    @Override
    public NoteViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return NoteViewHolder.create(parent);
    }

    @Override
    public void onBindViewHolder(NoteViewHolder holder, int position) {
        Note current = getItem(position);
        holder.bind(current);
    }

    static class noteDiff extends DiffUtil.ItemCallback<Note> {

        @Override
        public boolean areItemsTheSame(@NonNull Note oldItem, @NonNull Note newItem) {
            return oldItem == newItem;
        }

        @Override
        public boolean areContentsTheSame(@NonNull Note oldItem, @NonNull Note newItem) {
            return oldItem.getTitle().equals(newItem.getTitle()) && oldItem.getContent().equals(newItem.getContent());
        }
    }
}
