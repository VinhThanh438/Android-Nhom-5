package ninhduynhat.com.haui_android_n16_manager_account.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;
import ninhduynhat.com.haui_android_n16_manager_account.R;
import com.bumptech.glide.Glide;
import java.util.ArrayList;
import ninhduynhat.com.haui_android_n16_manager_account.Model.ListTypePlan;

public class TypePlanAdapter extends RecyclerView.Adapter<TypePlanAdapter.ViewHolder> {
    private ArrayList<ListTypePlan> items;
    private Context context;
    private OnItemClickListener listener;

    public interface OnItemClickListener {
        void onItemClick(ListTypePlan item);
    }

    public TypePlanAdapter(Context context, ArrayList<ListTypePlan> items, OnItemClickListener listener) {
        this.items = items;
        this.context = context;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_listview_type, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final ListTypePlan item = items.get(position);
        holder.titleTxt.setText(item.getTitle());

        int drawableResourceId = holder.itemView.getResources().getIdentifier(item.getImgpath(), "drawable", context.getPackageName());
        Glide.with(context)
                .load(drawableResourceId)
                .into(holder.img);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onItemClick(item);
            }
        });
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ConstraintLayout layoutItem;
        TextView titleTxt;
        ImageView img;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            layoutItem = itemView.findViewById(R.id.type_item);
            titleTxt = itemView.findViewById(R.id.titleTxt);
            img = itemView.findViewById(R.id.img_type);
        }
    }
}
