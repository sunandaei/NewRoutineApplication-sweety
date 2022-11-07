package com.sunanda.newroutine.application.adapter;

import android.content.Context;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.sunanda.newroutine.application.R;
import com.sunanda.newroutine.application.modal.ContactModel;

import java.util.List;

public class MyAddressAdapter extends RecyclerView.Adapter<MyAddressAdapter.MyViewHolder> {

    private static final String TAG ="MyAddressAdapter" ;
    private Context mContext;
    private List<ContactModel> addresslist;
    private int lastSelectedPosition = 0;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView user_name, user_address,user_phone;
        public RadioButton radioButton1;
        public ImageView deleteimageview;
        public String username,address,phone_number;

        public MyViewHolder(View view) {
            super(view);
            user_name = (TextView) view.findViewById(R.id.user_name);
            user_address = (TextView) view.findViewById(R.id.user_address);
            user_phone = (TextView) view.findViewById(R.id.user_phone);
            radioButton1 = (RadioButton) view.findViewById(R.id.radioButton1);
            deleteimageview = (ImageView) view.findViewById(R.id.deleteimageview);

            deleteimageview.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    ContactModel contact = new ContactModel();
                    contact.setID(addresslist.get(getAdapterPosition()).getID());
                    removeAt(getAdapterPosition());
                }
            });

            radioButton1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    lastSelectedPosition = getAdapterPosition();
                    notifyDataSetChanged();

                    username = user_name.getText().toString();
                    address = user_address.getText().toString();
                    phone_number = user_phone.getText().toString();

                    String total_address = username+ "," + address+ "," +phone_number;
                    Log.d(TAG, "onClick: "+total_address);
                    Toast.makeText(mContext, "selected offer is " + total_address,
                            Toast.LENGTH_LONG).show();
                }
            });
        }
    }

    public MyAddressAdapter(Context mContext, List<ContactModel> addresslist) {
        this.mContext = mContext;
        this.addresslist = addresslist;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.select_address_layout, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {

        ContactModel address = addresslist.get(position);
        holder.user_name.setText(address.getFirstName());
        holder.user_address.setText(address.getCity()+"," + address.getArea()+"," + address.getFlatno()+"," +address.getPincode()+"," +address.getState());
        holder.user_phone.setText(address.getPhonenumber());

        Log.d(TAG, "onBindViewHolder: "+addresslist.size());

        holder.radioButton1.setChecked(lastSelectedPosition == position);
    }

    @Override
    public int getItemCount() {
        return addresslist.size();

    }

    public void removeAt(int position) {
        addresslist.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, addresslist.size());
    }

}
