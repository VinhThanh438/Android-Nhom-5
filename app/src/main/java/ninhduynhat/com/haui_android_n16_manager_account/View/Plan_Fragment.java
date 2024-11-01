package ninhduynhat.com.haui_android_n16_manager_account.View;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.content.Intent;
import android.widget.ImageView;
import android.widget.TextView;
import java.util.ArrayList;
import ninhduynhat.com.haui_android_n16_manager_account.Adapters.PlanAdapter;
import ninhduynhat.com.haui_android_n16_manager_account.Model.PlanObject;
import ninhduynhat.com.haui_android_n16_manager_account.R;
import ninhduynhat.com.haui_android_n16_manager_account.Model.ListTypePlan;
import ninhduynhat.com.haui_android_n16_manager_account.Database.DatabaseHelper;
import ninhduynhat.com.haui_android_n16_manager_account.Adapters.TypePlanAdapter;
import ninhduynhat.com.haui_android_n16_manager_account.databinding.FragmentKeHoachBinding;

public class Plan_Fragment extends Fragment implements PlanAdapter.OnItemClickListener, TypePlanAdapter.OnItemClickListener {

    // Biến toàn cục lưu trữ danh sách các kế hoạch
    public static ArrayList<PlanObject> items = new ArrayList<>();
    private TypePlanAdapter typeAdapter; // Adapter cho loại kế hoạch
    private PlanAdapter listPlanAdapter; // Adapter cho danh sách kế hoạch
    private RecyclerView recyclerViewType, recyclerViewListPlan; // RecyclerView cho loại kế hoạch và danh sách kế hoạch
    private FragmentKeHoachBinding binding; // Binding cho layout của fragment
    private ImageView ic_plus; // ImageView để thêm kế hoạch mới

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate layout cho fragment này bằng cách sử dụng view binding
        binding = FragmentKeHoachBinding.inflate(inflater, container, false);
        return binding.getRoot(); // Trả về root view từ binding
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        // Khởi tạo ImageView từ binding
        ic_plus = binding.icPlus;
        if (ic_plus == null) {
            throw new NullPointerException("ic_plus là null. Đảm bảo rằng file layout của bạn có chứa một ImageView với id 'icPlus'.");
        }
        icPlusOnclick(); // Thiết lập sự kiện click cho ic_plus
        initRecyclerviewType(); // Khởi tạo RecyclerView cho loại kế hoạch
        initRecyclerviewPlan(); // Khởi tạo RecyclerView cho danh sách kế hoạch
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        // Set binding về null để tránh memory leaks
        binding = null;
    }

    private void icPlusOnclick() {
        ic_plus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Mở AddNewPlanActivity khi người dùng nhấn vào ic_plus
                Intent intent = new Intent(getContext(), AddNewPlanActivity.class);
                startActivity(intent);
            }
        });
    }

    private void initRecyclerviewPlan() {
        // Lấy tất cả kế hoạch từ cơ sở dữ liệu
        items = DatabaseHelper.getInstance(getContext()).fetchAllPlan();
        // Khởi tạo RecyclerView cho danh sách kế hoạch
        recyclerViewListPlan = binding.listviewTarget;
        recyclerViewListPlan.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        // Thiết lập adapter cho RecyclerView
        listPlanAdapter = new PlanAdapter(getContext(), items, this);
        recyclerViewListPlan.setAdapter(listPlanAdapter);
    }

    private void initRecyclerviewType() {
        // Tạo danh sách các loại kế hoạch
        ArrayList<ListTypePlan> typeItems = new ArrayList<>();
        typeItems.add(new ListTypePlan("piggy_bank", "Small"));
        typeItems.add(new ListTypePlan("piggy_bank", "Middle"));
        typeItems.add(new ListTypePlan("piggy_bank", "Big"));
        typeItems.add(new ListTypePlan("piggy_bank", "Short-term"));
        typeItems.add(new ListTypePlan("piggy_bank", "Long-term"));
        // Khởi tạo RecyclerView cho các loại kế hoạch
        recyclerViewType = binding.listviewType;
        recyclerViewType.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        // Thiết lập adapter cho RecyclerView
        typeAdapter = new TypePlanAdapter(getContext(), typeItems, this);
        recyclerViewType.setAdapter(typeAdapter);
    }

    @Override
    public void onItemClick(PlanObject item) {
        // Xử lý sự kiện click cho một item trong danh sách kế hoạch
        Intent intent = new Intent(getContext(), PlanDetailActivity.class);
        intent.putExtra("Target", (Parcelable) item);
        startActivity(intent);
    }

    @Override
    public void onItemClick(ListTypePlan item) {
        // Xử lý sự kiện click cho một item trong danh sách loại kế hoạch
        String type = item.getTitle();
        TextView txtName = binding.planName;
        TextView txtListName = binding.textViewListTarget;
        // Cập nhật tên loại và danh sách kế hoạch theo loại được chọn
        txtName.setText("Loại mục tiêu " + type);
        txtListName.setText("Danh sách mục tiêu " + type);
        // Lấy danh sách kế hoạch theo loại từ cơ sở dữ liệu và cập nhật adapter
        items = DatabaseHelper.getInstance(getContext()).fetchPlanByType(type);
        listPlanAdapter.setTypePlan(items);
    }

    @Override
    public void onResume() {
        super.onResume();
        // Khởi tạo lại RecyclerView cho các loại kế hoạch và danh sách kế hoạch để cập nhật dữ liệu
        initRecyclerviewType();
        initRecyclerviewPlan();
    }
}
