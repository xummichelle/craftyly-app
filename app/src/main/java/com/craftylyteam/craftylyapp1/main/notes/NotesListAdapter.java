package com.craftylyteam.craftylyapp1.main.notes;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.craftylyteam.craftylyapp1.R;
import com.craftylyteam.craftylyapp1.utils.Constants;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.DocumentSnapshot;

public class NotesListAdapter extends FirestoreRecyclerAdapter<Note, NotesListAdapter.NoteHolder> {

    private OnItemClickListener listener;

    public NotesListAdapter(@NonNull FirestoreRecyclerOptions<Note> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull NoteHolder holder, int position, @NonNull Note model) {
        holder.textViewNoteListTitle.setText(model.getTitle());
        holder.imageViewNoteListLightBulb.setTag(Constants.CRAFTYLY_BULB);
        String mBulbTag = model.getBulbTag();
        switch (mBulbTag) {
            case Constants.CRAFTYLY_BULB:
                holder.imageViewNoteListLightBulb.setImageResource(R.drawable.ic_craftyly_bulb_icon);
                holder.imageViewNoteListLightBulb.setTag(Constants.CRAFTYLY_BULB);
                break;
            case Constants.CALICO_BULB:
                holder.imageViewNoteListLightBulb.setImageResource(R.drawable.ic_calico_bulb_icon);
                holder.imageViewNoteListLightBulb.setTag(Constants.CALICO_BULB);
                break;
            case Constants.CRAFTYLY_CALICO_BULB:
                holder.imageViewNoteListLightBulb.setImageResource(R.drawable.ic_craftyly_calico_icon);
                holder.imageViewNoteListLightBulb.setTag(Constants.CRAFTYLY_CALICO_BULB);
                break;
            case Constants.LEMON_BULB:
                holder.imageViewNoteListLightBulb.setImageResource(R.drawable.ic_lemon_bulb_icon);
                holder.imageViewNoteListLightBulb.setTag(Constants.LEMON_BULB);
                break;
            case Constants.SUNSET_BULB:
                holder.imageViewNoteListLightBulb.setImageResource(R.drawable.ic_sunset_bulb_icon);
                holder.imageViewNoteListLightBulb.setTag(Constants.SUNSET_BULB);
                break;
            case Constants.CAMO_BULB:
                holder.imageViewNoteListLightBulb.setImageResource(R.drawable.ic_camo_bulb_icon);
                holder.imageViewNoteListLightBulb.setTag(Constants.CAMO_BULB);
                break;
            default:
                break;
        }
        Log.d("noteslistadaptertag", String.valueOf(holder.imageViewNoteListLightBulb.getTag()));
    }

    @NonNull
    @Override
    public NoteHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.note_item,
                parent, false);
        return new NoteHolder(v);
    }

    public void deleteItem(int position) {
        getSnapshots().getSnapshot(position).getReference().delete();
    }

    class NoteHolder extends RecyclerView.ViewHolder {
        TextView textViewNoteListTitle;
        ImageView imageViewNoteListLightBulb;
        CardView noteItemCard;

        public NoteHolder(@NonNull View itemView) {
            super(itemView);
            textViewNoteListTitle = itemView.findViewById(R.id.text_view_notelist_title);
            imageViewNoteListLightBulb = itemView.findViewById(R.id.image_view_notelist_lightbulb);
            noteItemCard = itemView.findViewById(R.id.note_item_card);


            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION && listener != null) {
                        listener.onItemClick(getSnapshots().getSnapshot(position), position);
                    }
                }
            });
        }
    }

    public interface OnItemClickListener {
        void onItemClick(DocumentSnapshot documentSnapshot, int position);

    }
    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;

    }

}
