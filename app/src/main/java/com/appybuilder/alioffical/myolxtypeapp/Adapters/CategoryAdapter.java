//package com.appybuilder.alioffical.myolxtypeapp.Adapters;
//
//import android.content.Context;
//import android.content.Intent;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.ImageView;
//import android.widget.LinearLayout;
//import android.widget.TextView;
//
//import androidx.annotation.NonNull;
//import androidx.recyclerview.widget.RecyclerView;
//
//import com.appybuilder.alioffical.myolxtypeapp.Activitise.ProductDetailActivity;
//import com.appybuilder.alioffical.myolxtypeapp.R;
//import com.bumptech.glide.Glide;
//
//import java.util.List;
//
//public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.MyviewHolder> {
//    List<CategoryModel>categoryModelList;
//    Context context;
//
//    public CategoryAdapter(List<CategoryModel> categoryModelList, Context context) {
//        this.categoryModelList = categoryModelList;
//        this.context = context;
//    }
//
//    @NonNull
//    @Override
//    public CategoryAdapter.MyviewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//        View MyItemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.showcateresourcefile, parent, false);
//        return new MyviewHolder(MyItemView);
//    }
//
//    @Override
//    public void onBindViewHolder(@NonNull CategoryAdapter.MyviewHolder holder, int position) {
//        CategoryModel categoryModel=categoryModelList.get(position);
//        holder.txt_nameItem.setText(categoryModel.getStr_ItemName());
//        Glide.with(context).load(categoryModel.getStr_Itemthubnail()).into(holder.img_item);
//        holder.itemOne.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                context.startActivity(new Intent(context, ProductDetailActivity.class));
//            }
//        });
//
//    }
//
//    @Override
//    public int getItemCount() {
//        return categoryModelList.size();
//    }
//
//    public class MyviewHolder extends RecyclerView.ViewHolder {
//        TextView txt_nameItem;
//        ImageView img_item;
//        LinearLayout itemOne;
//        public MyviewHolder(@NonNull View itemView) {
//            super(itemView);
//            txt_nameItem=itemView.findViewById(R.id.txt_item_name);
//            img_item=itemView.findViewById(R.id.Item_Image);
//            itemOne=itemView.findViewById(R.id.item_1);
//        }
//    }
//}
