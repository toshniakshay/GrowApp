package com.android.growappdemo.Adapters;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.growappdemo.Models.Http.Product;
import com.android.growappdemo.R;
import com.bumptech.glide.Glide;

import java.util.List;

/**
 * Created by bm302 on 5/13/18.
 */

public class ProductsAdapter extends  RecyclerView.Adapter<ProductsAdapter.ProductsViewHolder> {

    public interface ProductEventHandler {
        void onProductClick(Product product);
    }

    private ProductEventHandler productListener;
    private List<Product> productList;
    private Context context;

    public void setProductListener(ProductEventHandler productListener) {
        this.productListener = productListener;
    }

    public ProductsAdapter(List<Product> productList, Context context) {
        this.context = context;
        this.productList =productList;
    }

    private void notifyProductClick(Product product) {
        if (productListener != null)
            productListener.onProductClick(product);
    }

    @Override
    public ProductsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.product_card_view, parent, false);
        return new ProductsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ProductsViewHolder holder, int position) {
        final Product product = productList.get(position);

        if (product != null) {
            Glide.with(context).load(product.getImage()).into( holder.productImage);
            holder.productTitle. setText(product.getName());
            holder.productPrice.setText(product.getRegualrPrice());

            if (TextUtils.isEmpty(product.getDiscountPercent())) {
                holder.discount.setVisibility(View.GONE);
            } else {
                holder.discount.setVisibility(View.VISIBLE);
                holder.discount.setText(product.getDiscountPercent());
            }

            if (product.getOnSale()) {
                holder.productsOnSale.setVisibility(View.VISIBLE);
            } else {
                holder.productsOnSale.setVisibility(View.GONE);
            }
            holder.productContainer.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    notifyProductClick(product);
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return productList.size();
    }

    public class ProductsViewHolder extends RecyclerView.ViewHolder {

        private ImageView productImage;
        private TextView productTitle;
        private TextView productPrice;
        private CardView productContainer;
        private TextView productsOnSale;
        private TextView discount;


        public ProductsViewHolder(View itemView) {
            super(itemView);

            productContainer = (CardView) itemView.findViewById(R.id.product_container_view);
            productImage = (ImageView) itemView.findViewById(R.id.product_list_image);
            productPrice = (TextView) itemView.findViewById(R.id.product_list_price);
            productTitle = (TextView) itemView.findViewById(R.id.product_list_title);
            productsOnSale = (TextView) itemView.findViewById(R.id.product_on_sale);
            discount = (TextView) itemView.findViewById(R.id.dis_on_prod);
        }

    }
}
