    package com.example.kirmizisepetapp.fragments;

    import android.content.Intent;
    import android.os.Bundle;
    import android.view.LayoutInflater;
    import android.view.View;
    import android.view.ViewGroup;
    import android.widget.GridLayout;
    import android.widget.TextView;

    import androidx.annotation.NonNull;
    import androidx.annotation.Nullable;
    import androidx.fragment.app.Fragment;

    import com.example.kirmizisepetapp.CategoryProductsActivity;
    import com.example.kirmizisepetapp.R;

    public class DiscoverFragment extends Fragment {

        private GridLayout categoryGrid;

        @Nullable
        @Override
        public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
            View view = inflater.inflate(R.layout.fragment_discover, container, false);

            categoryGrid = view.findViewById(R.id.grid_category);

            setupCategoryClicks(view);

            return view;
        }

        private void setupCategoryClicks(View view) {
            int[] categoryIds = {
                    R.id.card_gida, R.id.card_teknoloji, R.id.card_beyazesya,
                    R.id.card_bakim, R.id.card_ev, R.id.card_bebek, R.id.card_evcil
            };

            String[] categoryNames = {
                    "Gıda", "Teknolojik Aletler", "Beyaz Eşya",
                    "Kişisel Bakım", "Ev & Yaşam", "Bebek Ürünleri", "Evcil Hayvan Ürünleri"
            };

            for (int i = 0; i < categoryIds.length; i++) {
                View card = view.findViewById(categoryIds[i]);
                String categoryName = categoryNames[i];

                card.setOnClickListener(v -> {
                    Intent intent = new Intent(getActivity(), CategoryProductsActivity.class);
                    intent.putExtra("categoryName", categoryName);
                    startActivity(intent);
                });
            }
        }
    }
