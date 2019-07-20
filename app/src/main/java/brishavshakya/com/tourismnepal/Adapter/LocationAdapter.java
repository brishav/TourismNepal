package brishavshakya.com.tourismnepal.Adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import brishavshakya.com.tourismnepal.DemoActivity;
import brishavshakya.com.tourismnepal.DescriptionActivity;
import brishavshakya.com.tourismnepal.LocationActivity;
import brishavshakya.com.tourismnepal.Model.LocationModel;
import brishavshakya.com.tourismnepal.R;

public class LocationAdapter extends ArrayAdapter<LocationModel> {
    ArrayList<LocationModel> listdata;
    Context context;
    int resource;
    public LocationAdapter(@NonNull Context context, int resource, @NonNull ArrayList<LocationModel> listdata) {
        super(context, resource, listdata);
        this.listdata=listdata;
        this.context=context;
        this.resource=resource;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (convertView==null){
            LayoutInflater layoutInflater=(LayoutInflater)getContext().getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
            convertView=layoutInflater.inflate(R.layout.grid_list,null,true);
        }
        final LocationModel listdata=getItem(position);
        final TextView text = convertView.findViewById(R.id.district_name);
        text.setText(listdata.getName());
        ImageView img= convertView.findViewById(R.id.district_picture);
        Picasso.with(context)
                .load(listdata.getImageurl())
                .into(img);

        img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(), listdata.getId()+ " : "+ listdata.getPid() , Toast.LENGTH_LONG).show();
                Intent intent = new Intent(getContext(), DescriptionActivity.class).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra("Description", String.valueOf(listdata.getDescription()));
                intent.putExtra("Name", String.valueOf(listdata.getName()));
                intent.putExtra("Image", String.valueOf(listdata.getImageurl()));
                context.startActivity(intent);
            }
        });
        return convertView;
    }
}
