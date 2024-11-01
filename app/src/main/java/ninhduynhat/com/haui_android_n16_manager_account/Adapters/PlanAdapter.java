package ninhduynhat.com.haui_android_n16_manager_account.Adapters;

import android.content.Context;
import android.content.Intent;
import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import ninhduynhat.com.haui_android_n16_manager_account.R;
import ninhduynhat.com.haui_android_n16_manager_account.Model.PlanObject;
import ninhduynhat.com.haui_android_n16_manager_account.View.PlanDetailActivity;

import java.io.Serializable;
import java.util.ArrayList;

public class PlanAdapter extends RecyclerView.Adapter<PlanAdapter.ViewHolder> {
    private static ArrayList<PlanObject> items;
    private static Context context;
    private OnItemClickListener listener;

    public PlanAdapter(Context context, ArrayList<PlanObject> items, OnItemClickListener listener) {
        this.context = context;
        this.items = items;
        this.listener = listener;
    }

    public void setTypePlan(ArrayList<PlanObject> plans) {
        items = plans;
        notifyDataSetChanged();
    }

    public interface OnItemClickListener {
        void onItemClick(PlanObject item);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        View view = LayoutInflater.from(context).inflate(R.layout.item_list_target, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        PlanObject plan = items.get(position);
        holder.title.setText(plan.getPlanName());
        // Ấn ra
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

//                 listener.onItemClick(plan);

                Intent intent = new Intent(context, PlanDetailActivity.class);
                intent.putExtra("Target", (Parcelable) plan);  // Make sure PlanObject implements Parcelable
                context.startActivity(intent);

            }
        });

        double amountReached = plan.getAmoutReached();
        double amountNeeded = plan.getAmoutNeeded();
        int progress = amountNeeded != 0 ? (int) ((amountReached / amountNeeded) * 100) : 0;

        holder.processPercent.setText(progress + "%");
        holder.progressBar.setProgress(progress);

         //Uncomment and update this part if you have image handling
//         int drawableResourceId = holder.itemView.getResources().getIdentifier(plan.getImgSrc(), "drawable", holder.itemView.getContext().getPackageName());
//         Glide.with(context).load(drawableResourceId).into(holder.ic);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        ImageView ic;
        TextView title, processPercent;
        ProgressBar progressBar;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ic = itemView.findViewById(R.id.icon_target);
            title = itemView.findViewById(R.id.title);
            processPercent = itemView.findViewById(R.id.progress);
            progressBar = itemView.findViewById(R.id.progressBar);
        }

        @Override
        public void onClick(View v) {
            int position=getAdapterPosition();
            PlanObject obj=items.get(position);
            Intent intent= new Intent(context, PlanDetailActivity.class);
            intent.putExtra("PlanObject", (Serializable) obj);
            context.startActivity(intent);
        }
    }
}
