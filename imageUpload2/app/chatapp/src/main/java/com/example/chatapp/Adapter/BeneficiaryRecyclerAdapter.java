package com.example.chatapp.Adapter;

import android.content.Context;

import android.view.LayoutInflater;

import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.RecyclerView;

import android.view.View;

import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;

import com.example.chatapp.BeneficiaryListActivity;
import com.example.chatapp.R;
//import com.example.chatapp.model.Beneficiary;
import com.example.chatapp.Model.Beneficiary;
import com.example.chatapp.R;

import java.util.ArrayList;

/**
 * Created by delaroy on 5/10/17.
 */
public class BeneficiaryRecyclerAdapter extends RecyclerView.Adapter<BeneficiaryRecyclerAdapter.BeneficiaryViewHolder>  {

    private ArrayList<Beneficiary> listBeneficiary;
    public ImageView overflow;
    private Context mContext;



    public BeneficiaryRecyclerAdapter(ArrayList<Beneficiary> listBeneficiary, Context mContext) {
        this.listBeneficiary = listBeneficiary;
        this.mContext = mContext;



    }

    public BeneficiaryRecyclerAdapter(ArrayList<com.example.chatapp.Beneficiary> listBeneficiary, BeneficiaryListActivity mContext) {

    }

    public class BeneficiaryViewHolder extends RecyclerView.ViewHolder {

        public AppCompatTextView textViewName;
        public AppCompatTextView textViewEmail;
        public AppCompatTextView textViewAddress;
        public AppCompatTextView textViewCountry;
        public AppCompatTextView textViewAge;
        public AppCompatTextView textViewGender;
        public AppCompatTextView textViewPulserate;
        public AppCompatTextView textViewWeight;
        public AppCompatTextView textViewHeight;
        public AppCompatTextView textViewBloodGrp;

        public BeneficiaryViewHolder(View view) {
            super(view);
            textViewName = (AppCompatTextView) view.findViewById(R.id.textViewName);
            textViewEmail = (AppCompatTextView) view.findViewById(R.id.textViewEmail);
            textViewAddress = (AppCompatTextView) view.findViewById(R.id.textViewAddress);
            textViewCountry = (AppCompatTextView) view.findViewById(R.id.textViewCountry);
            textViewAge = (AppCompatTextView) view.findViewById(R.id.textViewAge);
            textViewGender = (AppCompatTextView) view.findViewById(R.id.textViewGender);
            textViewPulserate = (AppCompatTextView) view.findViewById(R.id.textViewPulserate);
            textViewWeight = (AppCompatTextView) view.findViewById(R.id.textViewWeight);
            textViewHeight = (AppCompatTextView) view.findViewById(R.id.textViewHeight);
            textViewBloodGrp = (AppCompatTextView) view.findViewById(R.id.textViewBloodGrp);

        }


    }




    @Override
    public BeneficiaryViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // inflating recycler item view
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_beneficiary_recycler, parent, false);

        return new BeneficiaryViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final BeneficiaryViewHolder holder, int position) {
        holder.textViewName.setText(listBeneficiary.get(position).getName());
        holder.textViewEmail.setText(listBeneficiary.get(position).getEmail());
        holder.textViewAddress.setText(listBeneficiary.get(position).getAddress());
        holder.textViewCountry.setText(listBeneficiary.get(position).getCountry());
        holder.textViewAge.setText(listBeneficiary.get(position).getAge());
        holder.textViewGender.setText(listBeneficiary.get(position).getGender());
        holder.textViewPulserate.setText(listBeneficiary.get(position).getPulserate());
        holder.textViewWeight.setText(listBeneficiary.get(position).getWeight());
        holder.textViewHeight.setText(listBeneficiary.get(position).getHeight());
        holder.textViewBloodGrp.setText(listBeneficiary.get(position).getBloodgrp());

    }


    @Override
    public int getItemCount() {

        return listBeneficiary.size();
    }
public void setFilter(ArrayList<Beneficiary> newList){
        listBeneficiary = new ArrayList<>();
        listBeneficiary.addAll(newList);
        notifyDataSetChanged();
}


}