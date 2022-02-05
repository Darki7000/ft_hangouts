package com.example.ft_hangouts;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filterable;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

//public class ContactAdapter extends RecyclerView.Adapter<ContactViewHolder> implements Filterable {
//
//    private Context context;
//    private ArrayList<Contact> listContacts;
//    private ArrayList<Contact> mArrayList;
//
//    private ContactDatabase mDatabase;
//
//    public ContactAdapter(Context context, ArrayList<Contact> listContacts) {
//        this.context = context;
//        this.listContacts = listContacts;
//        this.mArrayList=listContacts;
//        mDatabase = new ContactDatabase(context);
//    }
//
//    @Override
//    public ContactViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
//        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.contact, parent, false);
//        return new ContactViewHolder(view);
//    }
//
//    @Override
//    public void onBindViewHolder(ContactViewHolder holder, int position) {
//        final Contact contacts = listContacts.get(position);
//
//        holder.name.setText(contacts.getName());
//        holder.ph_no.setText(contacts.getPhno());
//
//        holder.editContact.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                editTaskDialog(contacts);
//            }
//        });
//
//        holder.deleteContact.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                //delete row from database
//
//                mDatabase.deleteContact(contacts.getId());
//
//                //refresh the activity page.
//                ((Activity)context).finish();
//                context.startActivity(((Activity) context).getIntent());
//            }
//        });
//    }
//
//    @Override
//    public Filter getFilter() {
//
//        return new Filter() {
//            @Override
//            protected FilterResults performFiltering(CharSequence charSequence) {
//
//                String charString = charSequence.toString();
//
//                if (charString.isEmpty()) {
//
//                    listContacts = mArrayList;
//                } else {
//
//                    ArrayList<Contacts> filteredList = new ArrayList<>();
//
//                    for (Contacts contacts : mArrayList) {
//
//                        if (contacts.getName().toLowerCase().contains(charString)) {
//
//                            filteredList.add(contacts);
//                        }
//                    }
//
//                    listContacts = filteredList;
//                }
//
//                FilterResults filterResults = new FilterResults();
//                filterResults.values = listContacts;
//                return filterResults;
//            }
//
//            @Override
//            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
//                listContacts = (ArrayList<Contacts>) filterResults.values;
//                notifyDataSetChanged();
//            }
//        };
//    }
//
//
//    @Override
//    public int getItemCount() {
//        return listContacts.size();
//    }
//
//
//    private void editTaskDialog(final Contacts contacts){
//        LayoutInflater inflater = LayoutInflater.from(context);
//        View subView = inflater.inflate(R.layout.add_contact_layout, null);
//
//        final EditText nameField = (EditText)subView.findViewById(R.id.enter_name);
//        final EditText contactField = (EditText)subView.findViewById(R.id.enter_phno);
//
//        if(contacts != null){
//            nameField.setText(contacts.getName());
//            contactField.setText(String.valueOf(contacts.getPhno()));
//        }
//
//        AlertDialog.Builder builder = new AlertDialog.Builder(context);
//        builder.setTitle("Edit contact");
//        builder.setView(subView);
//        builder.create();
//
//        builder.setPositiveButton("EDIT CONTACT", new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialog, int which) {
//                final String name = nameField.getText().toString();
//                final String ph_no = contactField.getText().toString();
//
//                if(TextUtils.isEmpty(name)){
//                    Toast.makeText(context, "Something went wrong. Check your input values", Toast.LENGTH_LONG).show();
//                }
//                else{
//                    mDatabase.updateContacts(new Contacts(contacts.getId(), name, ph_no));
//                    //refresh the activity
//                    ((Activity)context).finish();
//                    context.startActivity(((Activity)context).getIntent());
//                }
//            }
//        });
//
//        builder.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialog, int which) {
//                Toast.makeText(context, "Task cancelled", Toast.LENGTH_LONG).show();
//            }
//        });
//        builder.show();
//    }
//}
