package com.craftylyteam.craftylyapp1.main.prompt.settings;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.core.content.res.ResourcesCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.craftylyteam.craftylyapp1.R;
import com.craftylyteam.craftylyapp1.utils.Constants;
import com.craftylyteam.craftylyapp1.utils.themeUtils;

import java.util.ArrayList;
import java.util.List;

public class ThemesAdapter extends RecyclerView.Adapter<ThemesAdapter.ThemesHolder> {
    private String[] themesList;
    private List<CardView>cardViewList = new ArrayList<>();


    public ThemesAdapter(String[] themesList) {
        this.themesList = themesList;

    }




    public String[] getThemesList() {
        return themesList;
    }


    @NonNull
    @Override
    public ThemesHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.themes_item,
                parent, false);


        return new ThemesHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ThemesHolder holder, int position) {

        String theme = themesList[position];

        TextView title = holder.textViewThemeTitle;
        TextView description = holder.textViewThemeDescription;
        ImageView bulb = holder.imageViewThemeLightbulb;

        Context context = title.getContext();

        switch (theme) {
            case Constants.CRAFTYLY_BULB:
                title.setText("Craftyly Default");
                description.setText("Purple | Tangerine | Green");
                bulb.setImageResource(R.drawable.ic_craftyly_bulb_icon);
                bulb.setTag(Constants.CRAFTYLY_BULB);
                break;
            case Constants.CALICO_BULB:
                title.setText("Calico");
                description.setText("Brown | Pink | Orange");
                bulb.setImageResource(R.drawable.ic_calico_bulb_icon);
                bulb.setTag(Constants.CALICO_BULB);
                break;
            case Constants.CRAFTYLY_CALICO_BULB:
                title.setText("Craftyly Calico");
                description.setText("Purple | Tangerine | Green");
                bulb.setImageResource(R.drawable.ic_craftyly_calico_icon);
                bulb.setTag(Constants.CRAFTYLY_CALICO_BULB);
                break;
            case Constants.LEMON_BULB:
                title.setText("Mint Lemonade");
                description.setText("Dark green | Yellow | Green");
                bulb.setImageResource(R.drawable.ic_lemon_bulb_icon);
                bulb.setTag(Constants.LEMON_BULB);
                break;
            case Constants.SUNSET_BULB:
                title.setText("Deep Sunset");
                description.setText("Deep purple | Yellow | Red");
                bulb.setImageResource(R.drawable.ic_sunset_bulb_icon);
                bulb.setTag(Constants.SUNSET_BULB);
                break;
            case Constants.CAMO_BULB:
                title.setText("Winter Camouflage");
                description.setText("Olive | Green | Light blue");
                bulb.setImageResource(R.drawable.ic_camo_bulb_icon);
                bulb.setTag(Constants.CAMO_BULB);
                break;
            default:
                break;
        }

        if (!cardViewList.contains(holder.themeItemCard)) {
            cardViewList.add(holder.themeItemCard);
        }
        holder.themeItemCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for (CardView cardView : cardViewList){
                    cardView.setCardBackgroundColor(ResourcesCompat
//                            get int color from color resource
                            .getColor(context.getResources(), R.color.white,null));
                    holder.themeItemCard.setCardBackgroundColor(ResourcesCompat
                    .getColor(context.getResources(), R.color.cultured, null));

                }
                Log.d("themesadapter", String.valueOf(v));
                if(cardViewList.indexOf(v)==0){
                    themeUtils.changeToThemeByContext(context, Constants.CRAFTYLY_BULB);
                    Log.d("themesadapter", "onbindview 0");
                }else if(cardViewList.indexOf(v)==1){
                    themeUtils.changeToThemeByContext(context, Constants.CALICO_BULB);

                }else if(cardViewList.indexOf(v)==2){
                    themeUtils.changeToThemeByContext(context, Constants.CRAFTYLY_CALICO_BULB);

                }else if(cardViewList.indexOf(v)==3){
                    themeUtils.changeToThemeByContext(context, Constants.LEMON_BULB);
                    Log.d("themesadapter","onclick onbindview, theme set to lemon");

                }else if(cardViewList.indexOf(v)==4){
                    themeUtils.changeToThemeByContext(context, Constants.SUNSET_BULB);

                }else if(cardViewList.indexOf(v)==5){
                    themeUtils.changeToThemeByContext(context, Constants.CAMO_BULB);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return themesList.length;
    }

    class ThemesHolder extends RecyclerView.ViewHolder {
        TextView textViewThemeTitle;
        TextView textViewThemeDescription;
        ImageView imageViewThemeLightbulb;
        CardView themeItemCard;

        public ThemesHolder(@NonNull View itemView) {
            super(itemView);
            textViewThemeTitle = itemView.findViewById(R.id.text_view_theme_title);
            textViewThemeDescription = itemView.findViewById(R.id.text_view_theme_description);
            imageViewThemeLightbulb = itemView.findViewById(R.id.image_view_theme_lightbulb);
            themeItemCard = itemView.findViewById(R.id.theme_item_card);
        }




    }


}
