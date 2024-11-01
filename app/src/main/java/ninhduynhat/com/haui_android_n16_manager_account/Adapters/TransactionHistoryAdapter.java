package ninhduynhat.com.haui_android_n16_manager_account.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

import ninhduynhat.com.haui_android_n16_manager_account.Model.PayingTuitionObject;
import ninhduynhat.com.haui_android_n16_manager_account.R;

public class TransactionHistoryAdapter extends RecyclerView.Adapter<TransactionHistoryAdapter.TransactionViewHolder> {

    private List<PayingTuitionObject> transactionList;

    public TransactionHistoryAdapter(List<PayingTuitionObject> transactionList) {
        this.transactionList = transactionList;
    }

    @NonNull
//    @Override
    public TransactionViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.trans_his_recycleview, parent, false);
        return new TransactionViewHolder(view);
    }

//    @Override
    public void onBindViewHolder(@NonNull TransactionViewHolder holder, int position) {
        NumberFormat numberFormat = NumberFormat.getNumberInstance(Locale.US);

        PayingTuitionObject transaction = transactionList.get(position);
        holder.description.setText(transaction.getSubjectName());
        holder.amount.setText(numberFormat.format(transaction.getAmount()) + " VND");
        holder.amount.setTextColor(transaction.isPaided() ? holder.itemView.getResources().getColor(android.R.color.holo_green_dark) : holder.itemView.getResources().getColor(android.R.color.holo_red_dark));
    }

//    @Override
    public int getItemCount() {
        return transactionList.size();
    }

    static class TransactionViewHolder extends RecyclerView.ViewHolder {
        ImageView icon;
        TextView description;
        TextView amount;

        TransactionViewHolder(@NonNull View itemView) {
            super(itemView);
            icon = itemView.findViewById(R.id.icontrans_his);
            description = itemView.findViewById(R.id.des_trans_his);
            amount = itemView.findViewById(R.id.transaction_amount);
        }
    }

}
