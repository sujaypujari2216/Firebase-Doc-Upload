package com.example.firebasedocupload;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class View_PDF extends AppCompatActivity {

    ListView PDFListView;
    DatabaseReference db_ref;
    List<UploadPDF> uploadPDFS;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view__p_d_f);

        PDFListView=(ListView)findViewById(R.id.myListView);
        uploadPDFS=new ArrayList<>();

        viewAllPDFS();

        PDFListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    UploadPDF uploadPDF=uploadPDFS.get(i);

                    Intent intent=new Intent();
                    //intent.setType(intent.ACTION_VIEW);
                    //intent.setData(Uri.parse(uploadPDF.getUrl()));

                    intent.setDataAndType(Uri.parse(uploadPDF.getUrl()),intent.ACTION_VIEW);
                    startActivity(intent);
            }
        });
    }

    private void viewAllPDFS() {
        db_ref= FirebaseDatabase.getInstance().getReference("uploads");
        db_ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for(DataSnapshot postSnapshot: dataSnapshot.getChildren()){

                    UploadPDF uploadPDF =postSnapshot.getValue(com.example.firebasedocupload.UploadPDF.class);
                    uploadPDFS.add(uploadPDF);
                }

                String[] uploads=new String[uploadPDFS.size()];
                for(int i=0;i<uploads.length;i++){
                    uploads[i]=uploadPDFS.get(i).getName();
                }
                ArrayAdapter<String> adapter=new ArrayAdapter<String>(getApplicationContext(),android.R.layout.simple_list_item_1,uploads){

                    @Override
                    public View getView(int position, View convertView, ViewGroup parent) {

                        View view=super.getView(position,convertView,parent);

                        TextView textView=(TextView) view.findViewById(android.R.id.text1);
                        textView.setTextColor(Color.BLACK);

                        return view;
                    }
                };
                PDFListView.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}