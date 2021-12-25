package com.craftylyteam.craftylyapp1.main.prompt;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.craftylyteam.craftylyapp1.R;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;

//firestore recycler adapter class for prompt history recycler view
public class HistoryAdapter extends FirestoreRecyclerAdapter<Prompt, HistoryAdapter.HistoryHolder> {
    private RecyclerView recyclerView;

    public HistoryAdapter(@NonNull FirestoreRecyclerOptions<Prompt> options, RecyclerView recyclerView) {
        super(options);
        this.recyclerView = recyclerView;
    }

    @Override
    public void onDataChanged() {
        super.onDataChanged();
        recyclerView.smoothScrollToPosition(0);
    }

    @Override
    protected void onBindViewHolder(@NonNull HistoryHolder holder, int position, @NonNull Prompt model) {
        holder.historyPromptDescriptionTextView.setText(model.getDescription());
        if (model.isAccepted()) {
            holder.historyStateIndicatorIV
                    .setImageResource(R.drawable.ic_history_accept_indicator);
        } else if (!model.isAccepted()) {
            holder.historyStateIndicatorIV
                    .setImageResource(R.drawable.ic_history_reject_indicator);
        }
    }

    @NonNull
    @Override
    public HistoryHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.history_item,
                parent, false);
        return new HistoryHolder(v);
    }

    class HistoryHolder extends RecyclerView.ViewHolder {
        TextView historyPromptDescriptionTextView;
        ImageView historyStateIndicatorIV;

        public HistoryHolder(@NonNull View itemView) {
            super(itemView);
            historyPromptDescriptionTextView = itemView.findViewById(R.id.history_prompt_description_text_view);
            historyStateIndicatorIV = itemView.findViewById(R.id.history_state_indicator_iv);
        }
    }
}
