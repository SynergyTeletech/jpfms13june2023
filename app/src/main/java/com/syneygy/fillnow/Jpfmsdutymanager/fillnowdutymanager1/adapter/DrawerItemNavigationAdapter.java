package com.syneygy.fillnow.Jpfmsdutymanager.fillnowdutymanager1.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.syneygy.fillnow.Jpfmsdutymanager.fillnowdutymanager1.R;
import com.syneygy.fillnow.Jpfmsdutymanager.fillnowdutymanager1.model.NavigationModel;


public class DrawerItemNavigationAdapter extends ArrayAdapter<NavigationModel> {

    private Context mContext;
    private int layoutResourceId;
    private NavigationModel data[] = null;

    public DrawerItemNavigationAdapter(@NonNull Context context, int resource, NavigationModel[] mDrawerList) {
        super(context, resource, mDrawerList);
        this.layoutResourceId = layoutResourceId;
        this.mContext = context;
        this.data = mDrawerList;
    }


    @Override
    public int getViewTypeCount() {
        return data.length;
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
      //  View listItem = convertView;
        if (convertView == null){
            LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.nav_list_row, null);

            if(data[position].getName().equalsIgnoreCase("-")){
                View divider = inflater.inflate(R.layout.divider_view,null);
                return divider;
            }

            TextView tvNav = convertView.findViewById(R.id.tv_nav);
            ImageView ivNav = convertView.findViewById(R.id.iv_nav_icon);
          //  ImageView ivArrow = convertView.findViewById(R.id.iv_arrow);

            NavigationModel navigationModel = data[position];

            tvNav.setText(navigationModel.getName());
            tvNav.setTextColor(mContext.getResources().getColor(R.color.black));
            ivNav.setImageResource(navigationModel.getIcon());

//            if(data[position].getName().equalsIgnoreCase("Reports")){
//                ivArrow.setVisibility(View.VISIBLE);
//            }else if (data[position].getName().equalsIgnoreCase("Support")){
//                ivArrow.setVisibility(View.VISIBLE);
//            }
        }

     /*   LayoutInflater inflater = ((AppCompatActivity)mContext).getLayoutInflater();
        listItem = inflater.inflate(layoutResourceId, parent, false);*/

        return convertView;

    }
}
