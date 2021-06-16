package com.example.waitless;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.FirebaseDatabase;

public class PateintHelperAdapter extends FirebaseRecyclerAdapter<PatientDetailsHelperClass, PateintHelperAdapter.ViewHolderClass> {

    public PateintHelperAdapter(@NonNull FirebaseRecyclerOptions<PatientDetailsHelperClass> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull ViewHolderClass holder, int positions, @NonNull PatientDetailsHelperClass patientDetailsHelperClass) {
        holder.name.setText(patientDetailsHelperClass.getName());
        holder.gender.setText(patientDetailsHelperClass.getGender());
        holder.age.setText(patientDetailsHelperClass.getAge());
        holder.phone.setText(patientDetailsHelperClass.getPhone());
        holder.cid.setText(patientDetailsHelperClass.getCid());
        holder.symtoms.setText(patientDetailsHelperClass.getSymtoms());
        holder.disease.setText(patientDetailsHelperClass.getDisease());
        holder.date.setText(patientDetailsHelperClass.getDate());
        holder.time.setText(patientDetailsHelperClass.getTime());

    }
    @NonNull
    @Override
    public ViewHolderClass onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_user, parent,false);
        PateintHelperAdapter.ViewHolderClass viewHolderClass = new PateintHelperAdapter.ViewHolderClass(view);
        return viewHolderClass;
    }

    public class ViewHolderClass extends RecyclerView.ViewHolder {
        TextView name, gender, age, phone, cid, symtoms, disease, date, time;
        public ViewHolderClass(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.name_dispalay);
            gender = itemView.findViewById(R.id.gender_dispalay);
            age = itemView.findViewById(R.id.age_dispalay);
            phone = itemView.findViewById(R.id.phone_display);
            cid = itemView.findViewById(R.id.cid_display);
            symtoms = itemView.findViewById(R.id.symtoms_display);
            disease = itemView.findViewById(R.id.disease_display);
            date = itemView.findViewById(R.id.date_display);
            time = itemView.findViewById(R.id.time_display);
        }
    }
}
