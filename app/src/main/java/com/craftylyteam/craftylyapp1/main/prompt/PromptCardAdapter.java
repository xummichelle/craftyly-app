package com.craftylyteam.craftylyapp1.main.prompt;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.core.content.res.ResourcesCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.craftylyteam.craftylyapp1.R;
import com.craftylyteam.craftylyapp1.utils.Constants;
import com.craftylyteam.craftylyapp1.utils.themeUtils;

import java.util.List;

//adapter for card stack view
public class PromptCardAdapter extends RecyclerView.Adapter<PromptCardAdapter.PromptCardHolder> {

    private List<String> mPromptsList;

    public PromptCardAdapter(List<String> promptsList) { mPromptsList = promptsList; }
    

    @NonNull
    @Override
    public PromptCardHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        // Inflate the custom layout
        View cardView = inflater.inflate(R.layout.promptcard_item, parent, false);

        // Return a new holder instance
        PromptCardHolder promptCardHolder = new PromptCardHolder(cardView);
        return promptCardHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull PromptCardHolder holder, int position) {
        // Get the data model based on position
        String prompt = mPromptsList.get(position);

        // Set item views based on your views and data model
        TextView textView = holder.textViewPromptDescription;
        textView.setText(prompt);

        CardView cardView = holder.promptcarditem;
        Context context = cardView.getContext();
        String theme = themeUtils.getCurrentThemeByContext(context);
        Log.d("themePromptAdapter", theme);
        switch (theme) {
            default:
                cardView.setCardBackgroundColor(ResourcesCompat
                        .getColor(context.getResources(), R.color.med_purple,null));
                Log.d("themePromptAdapter", "default");
                break;
            case Constants.CALICO_BULB:
                cardView.setCardBackgroundColor(ResourcesCompat
                        .getColor(context.getResources(), R.color.calico_light_brown,null));
                break;
            case Constants.LEMON_BULB:
                cardView.setCardBackgroundColor(ResourcesCompat
                        .getColor(context.getResources(), R.color.lemon_bright_green,null));
                Log.d("themePromptAdapter", "lemon");
                break;
            case Constants.SUNSET_BULB:
                cardView.setCardBackgroundColor(ResourcesCompat
                        .getColor(context.getResources(), R.color.sunset_deep_purple,null));
                break;
            case Constants.CAMO_BULB:
                cardView.setCardBackgroundColor(ResourcesCompat
                        .getColor(context.getResources(), R.color.camo_light_blue,null));
                break;
        }


    }

    @Override
    public int getItemCount() {
        return mPromptsList.size();

    }

    public List<String> getmPromptsList() {
        return mPromptsList;
    }

    class PromptCardHolder extends RecyclerView.ViewHolder {
        TextView textViewPromptDescription;
        CardView promptcarditem;

        public PromptCardHolder(@NonNull View itemView) {
            super(itemView);
            promptcarditem = itemView.findViewById(R.id.prompt_card_item);
            textViewPromptDescription = itemView.findViewById(R.id.text_view_prompt_description);
        }
    }
}
