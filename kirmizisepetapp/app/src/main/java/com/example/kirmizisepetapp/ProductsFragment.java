package com.example.kirmizisepetapp;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class ProductsFragment extends Fragment {

    private RecyclerView rvCategories;

    public ProductsFragment() {
        // Boş constructor
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_category_list, container, false);

        rvCategories = view.findViewById(R.id.rv_categories);
        rvCategories.setLayoutManager(new LinearLayoutManager(getContext()));

        // Kategoriler ve ikonlar
        List<Category> categories = new ArrayList<>();
        categories.add(new Category("Gıda", R.drawable.ic_food));
        categories.add(new Category("Teknolojik", R.drawable.ic_technology));
        categories.add(new Category("Beyaz Eşya", R.drawable.ic_whitegoods));
        categories.add(new Category("Kişisel", R.drawable.ic_personalcare));
        categories.add(new Category("Ev ve Yaşam", R.drawable.ic_home));
        categories.add(new Category("Bebek Ürünleri", R.drawable.ic_baby));
        categories.add(new Category("Evcil Hayvan", R.drawable.ic_pet));

        CategoryAdapter adapter = new CategoryAdapter(getContext(), categories);
        rvCategories.setAdapter(adapter);

        return view;
    }
}
