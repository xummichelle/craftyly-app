package com.craftylyteam.craftylyapp1.main.notes;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.craftylyteam.craftylyapp1.R;
import com.craftylyteam.craftylyapp1.main.prompt.Prompt;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;

//firestore recycler adapter for the rejected list recycler view
public class RejectedListAdapter extends FirestoreRecyclerAdapter<Prompt, RejectedListAdapter.RejectedListHolder> {

    public RejectedListAdapter(@NonNull FirestoreRecyclerOptions<Prompt> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull RejectedListHolder holder, int position, @NonNull Prompt model) {
        holder.acceptRejectPromptDescription.setText(model.getDescription());
    }

    @NonNull
    @Override
    public RejectedListHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.accept_reject_note_item,
                parent, false);
        return new RejectedListHolder(v);
    }

    public void deleteItem(int position) {
        getSnapshots().getSnapshot(position).getReference().delete();
    }

    class RejectedListHolder extends RecyclerView.ViewHolder{
        TextView acceptRejectPromptDescription;

        public RejectedListHolder(@NonNull View itemView) {
            super(itemView);
            acceptRejectPromptDescription = (TextView) itemView.findViewById(R.id.accept_reject_prompt_description);
        }
    }
}
