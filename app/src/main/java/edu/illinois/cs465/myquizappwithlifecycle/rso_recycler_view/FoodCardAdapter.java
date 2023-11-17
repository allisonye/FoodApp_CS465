package edu.illinois.cs465.myquizappwithlifecycle.rso_recycler_view;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import edu.illinois.cs465.myquizappwithlifecycle.R;
import edu.illinois.cs465.myquizappwithlifecycle.data.FoodListing;

public class FoodCardAdapter extends RecyclerView.Adapter<FoodCardAdapter.FoodCardHolder> {
    private List<FoodListing> foodListings = new ArrayList<>();
    private OnItemClickListener listener;

    @NonNull
    @Override
    public FoodCardHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.rso_food_card, parent, false);
        return new FoodCardHolder(itemView);
    }
    @Override
    public void onBindViewHolder(@NonNull FoodCardHolder holder, int position) {
        FoodListing currentFoodListing = foodListings.get(position);
        holder.textViewTitle.setText(currentFoodListing.food_name);
        //TODO: Repeat for time and date use String.valueOf()
    }

    @Override
    public int getItemCount() {
        return foodListings.size();
    }

    public void setFoodListings(List<FoodListing> foodListings){
        this.foodListings = foodListings;
        notifyDataSetChanged();
    }

    public FoodListing getFoodListingAt(int position) {
        return foodListings.get(position);
    }
    class FoodCardHolder extends RecyclerView.ViewHolder{
        private TextView textViewTitle;
        private TextView textViewDate;
        private TextView textViewExpiryTime;

        public FoodCardHolder(@NonNull View itemView) {
            super(itemView);
            textViewTitle = itemView.findViewById(R.id.text_view_title);
//            textViewDate = itemView.findViewById(R.id.text_view_card_date);
//            textViewExpiryTime = itemView.findViewById(R.id.text_view_expiry_time);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if (listener != null && position != RecyclerView.NO_POSITION) {
                        listener.onItemClick(foodListings.get(position));
                    }
                }
            });
        }
    }
    public interface OnItemClickListener {
        void onItemClick(FoodListing foodListing);
    }
    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }
}
