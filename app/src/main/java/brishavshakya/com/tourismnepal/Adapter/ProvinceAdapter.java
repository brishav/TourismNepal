package brishavshakya.com.tourismnepal.Adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import brishavshakya.com.tourismnepal.DistrictActivity;
import brishavshakya.com.tourismnepal.Model.ProvinceModel;
import brishavshakya.com.tourismnepal.R;

public class ProvinceAdapter extends ArrayAdapter<ProvinceModel> implements View.OnClickListener {

    private ArrayList<ProvinceModel> dataset;
    Context mContext;

    private static class ViewHolder {
        TextView provinceName;
        TextView provinceId;
    }

    public ProvinceAdapter(ArrayList<ProvinceModel> data, Context context) {
        super(context, R.layout.province_list_row, data);
        this.dataset = data;
        this.mContext = context;

    }

    @Override
    public void onClick(View v) {

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        final ProvinceModel dataModel = getItem(position);

        final ViewHolder viewHolder;
        final View result;
        if (convertView == null) {
            viewHolder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.province_list_row, parent, false);
            viewHolder.provinceName = (TextView) convertView.findViewById(R.id.province_name);
            result = convertView;
            convertView.setTag(viewHolder);
            convertView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Log.d("Province Number", String.valueOf(dataModel.getProvinceId()));
                    Intent intent = new Intent(getContext(), DistrictActivity.class).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    intent.putExtra("ProvinceNumber", String.valueOf(dataModel.getProvinceId()));
                    mContext.startActivity(intent);
                }
            });
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
            result = convertView;
        }
        viewHolder.provinceName.setText(dataModel.ProvinceName);
        viewHolder.provinceName.setBackgroundColor(Color.parseColor(dataModel.Color));
        return convertView;
    }
}
