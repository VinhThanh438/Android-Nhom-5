package ninhduynhat.com.android_n5_manager_account.Adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import java.text.NumberFormat;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

import ninhduynhat.com.android_n5_manager_account.Model.SubjectObject;
import ninhduynhat.com.android_n5_manager_account.R;

public class SubjectAdapter extends RecyclerView.Adapter<SubjectAdapter.ViewHolder> {

    private final List<SubjectObject> subjects;
    private final Map<Integer, Set<Integer>> selectedPositionsBySemester; // Map để lưu các vị trí đã chọn theo kỳ
    private final Context context;

    public SubjectAdapter(List<SubjectObject> subjects, Context context) {
        this.subjects = subjects;
        this.context = context;
        this.selectedPositionsBySemester = new HashMap<>(); // Khởi tạo selectedPositionsBySemester
    }

    // Phương thức để thiết lập các vị trí đã chọn cho một kỳ nhất định
    public void setSelectedSubjectsForSemester(int semester, Set<Integer> selectedPositions) {
        selectedPositionsBySemester.put(semester, selectedPositions);
        notifyDataSetChanged(); // Thông báo cho adapter rằng dữ liệu đã thay đổi
    }

    // Phương thức để lấy các vị trí đã chọn cho một kỳ nhất định
    public Set<Integer> getSelectedPositionsForSemester(int semester) {
        return selectedPositionsBySemester.getOrDefault(semester, new HashSet<>());
    }

    // Phương thức để xóa lựa chọn cho một kỳ nhất định
    public void clearSelectionForSemester(int semester) {
        if (selectedPositionsBySemester.containsKey(semester)) {
            selectedPositionsBySemester.get(semester).clear();
            notifyDataSetChanged();
        }
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public CardView cardView;
        public TextView description;
        public TextView credit;
        public TextView amount;

        public ViewHolder(View view) {
            super(view);
            cardView = view.findViewById(R.id.cardSubject);
            description = view.findViewById(R.id.nameSubject);
            credit = view.findViewById(R.id.tvcredit);
            amount = view.findViewById(R.id.subject_amount);
        }
    }

    @Override
    public SubjectAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_subject, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        SubjectObject subject = subjects.get(position);

        holder.description.setText(subject.getSubjectName());
        holder.credit.setText(String.format("Số tín chỉ: %d", subject.getStudyCredits()));

        // Định dạng số tiền
        NumberFormat numberFormat = NumberFormat.getInstance(new Locale("vi", "VN"));
        String formattedAmount = numberFormat.format(subject.getAmount());
        holder.amount.setText(String.format("Số tiền: %s", formattedAmount));

        int semester = subject.getSemester();
        Set<Integer> selectedPositions = selectedPositionsBySemester.getOrDefault(semester, new HashSet<>());

        // Thiết lập màu nền dựa trên trạng thái lựa chọn
        holder.itemView.setBackgroundColor(selectedPositions.contains(position) ?
                ContextCompat.getColor(context, R.color.xanhla) :
                ContextCompat.getColor(context, android.R.color.white));

        // Xử lý sự kiện click vào item
        holder.itemView.setOnClickListener(v -> {
            if (selectedPositions.contains(position)) {
                selectedPositions.remove(position);
                holder.itemView.setBackgroundColor(ContextCompat.getColor(context, android.R.color.white));
            } else {
                selectedPositions.add(position);
                holder.itemView.setBackgroundColor(ContextCompat.getColor(context, R.color.xanhla));
            }
            selectedPositionsBySemester.put(semester, selectedPositions);
        });
    }

    @Override
    public int getItemCount() {
        return subjects.size();
    }

    public void clearAllSelections() {
        selectedPositionsBySemester.clear();
        notifyDataSetChanged();
    }
}
