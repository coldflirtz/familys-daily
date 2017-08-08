package edu.bluejack16_2.familysdaily;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;

import edu.bluejack16_2.familysdaily.models.Product;

/**
 * Created by fidel on 31-Jul-17.
 */

public class ExpiredListViewAdapter extends BaseAdapter {
    ArrayList<Product> products;
    ArrayList<String> productKeys;
    Context context;


    public class ViewHolder{
        TextView tvProductName, tvExpiredDate;
    }

    public ExpiredListViewAdapter(Context context){
        products = new ArrayList<>();
        productKeys = new ArrayList<>();
        this.context = context;
    }

    public void refresh(){
        products.clear();
    }

    public void add(Product product, String productKey){
        products.add(product);
        productKeys.add(productKey);
    }

    public ArrayList<Product> getProducts() {
        return products;
    }

    public void setProducts(ArrayList<Product> products) {
        this.products = products;
    }

    @Override
    public int getCount() {
        return products.size();
    }

    @Override
    public Product getItem(int position) {
        return products.get(position);
    }

    public String getKey(int position){
        return productKeys.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder mainViewHolder = null;
        if(convertView == null){
            LayoutInflater inflater = LayoutInflater.from(context);
            convertView = inflater.inflate(R.layout.listview_row_expired, parent, false);

            ViewHolder viewHolder = new ViewHolder();

            viewHolder.tvProductName = (TextView) convertView.findViewById(R.id.tvProductName);
            viewHolder.tvExpiredDate = (TextView) convertView.findViewById(R.id.tvExpiredDate);

            viewHolder.tvExpiredDate.setText(products.get(position).getProductExpiredDate());
            viewHolder.tvProductName.setText(products.get(position).getProductName());
            convertView.setTag(viewHolder);
        }
        else{
            mainViewHolder = (ViewHolder) convertView.getTag();
            mainViewHolder.tvExpiredDate.setText(products.get(position).getProductExpiredDate());
            mainViewHolder.tvProductName.setText(products.get(position).getProductName());
        }

        return convertView;
    }
}
