package com.syneygy.fillnow.Jpfmsdutymanager.fillnowdutymanager1.hardware.synergy;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.Filter;
import android.widget.ImageView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.syneygy.fillnow.Jpfmsdutymanager.fillnowdutymanager1.R;
import com.syneygy.fillnow.Jpfmsdutymanager.fillnowdutymanager1.interfaces.OnAssetOperation;
import com.syneygy.fillnow.Jpfmsdutymanager.fillnowdutymanager1.model.Asset;
import com.syneygy.fillnow.Jpfmsdutymanager.fillnowdutymanager1.utils.SharedPref;
import java.util.ArrayList;
import java.util.List;


public class AssetListDialog extends DialogFragment implements View.OnClickListener {
    private TextView quantity;
    private Context context;
    private RecyclerView assetListRecycler;
    private ArrayList<Asset> asset;
    private String status="";
    private ArrayList<Asset> customFilter;
    private OnAssetOperation assetListener;
     private SearchView searchView;
     private ImageView iv_search;
    public String assetLockId;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = context;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.dialog_asset_list, container, false);
        quantity = v.findViewById(R.id.alertHeaderTxt);
        assetListRecycler = v.findViewById(R.id.alertListRecycler);
       searchView =v.findViewById(R.id.serarch_view_lock_id);
        iv_search=v.findViewById(R.id.iv_search);
        Bundle data = getArguments();
        if (data != null) {
            try {
                if (data.containsKey("assetList")) {
                     new TypeToken<ArrayList<Asset>>() {}.getType();
                    asset = data.getParcelableArrayList("assetList");
                    Gson gson = new Gson();
                    String tmp = gson.toJson(asset, new TypeToken<ArrayList<Asset>>() {}.getType());

                    Log.d("AssetForDialog",""+tmp);
                    if(asset==null){
                        Toast.makeText(getActivity(),"Asset is null ",Toast.LENGTH_SHORT).show();
                    }
                    if(data.getString("balance_qnty") != null){
                        quantity.setText(String.format("Ordered Fuel Quantity Left: %s Ltr.", data.getString("balance_qnty")));
                    }else{
                        quantity.setText(String.format("Ordered Fuel Quantity Left: %s Ltr.", SharedPref.getBalanceQty()));
                    }
                    if(data.containsKey("status")){
                      status=data.getString("status","");
                      if(status==null){
                          status="0";
                      }
                    }
                   // assetListRecycler.setLayoutManager(new GridLayoutManager(context, GridLayoutManager));
                    assetListRecycler.setLayoutManager(new GridLayoutManager(context, 4));
                    // adapterN = new AssetHolder();
                    //assetListRecycler.setAdapter(adapterN);
                    assetListRecycler.setAdapter(new RecyclerView.Adapter<AssetHolder>() {
                  //  assetListRecycler.setAdapter(new RecyclerView.Adapter<AssetHolder>() {

                        @NonNull
                        @Override
                        public AssetHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int position) {
                            return new AssetHolder(LayoutInflater.from(context).inflate(R.layout.asset_look,
                                    viewGroup, false));
                        }

                        @Override
                        public void onBindViewHolder(AssetHolder assetHolder, @SuppressLint("RecyclerView") int position) {
                            Log.d("AssetForDialog1",""+asset.get(position).toString());
                            assetHolder.assetName.setText(asset.get(position).getAssetName());
                            assetHolder.assetNumber.setText(String.format("Equipment Id:"+""+ asset.get(position).getId()));
                            assetLockId= asset.get(position).getLinked_asset();
                            assetHolder.tvAssetLockId.setText(String.format("Lock Id:"+""+ asset.get(position).getElock_serial()));
                            assetHolder.card.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    if (assetListener != null) {
                                        Log.d("status",""+status);
                                        if(status.contains("0")) {
                                            assetListener.OnAssetSelected(asset.get(position).getId(), data.getString("balance_qnty"), asset.get(position).getAssetName(), asset.get(position), position);
                                            assetHolder.card.postDelayed(new Runnable() {
                                                @Override
                                                public void run() {
                                                    dismiss();
                                                }
                                            }, 200);
                                        }
                                        else {
                                            String balanceqty=String.format("%.2f",Double.parseDouble(asset.get(position).getAssetOrderQty())-Double.parseDouble(asset.get(position).getAssetDispenseQty()));
                                            if(Double.parseDouble(balanceqty)>1.0) {
                                                assetListener.OnAssetSelected(asset.get(position).getId(), balanceqty, asset.get(position).getAssetName(), asset.get(position), position);
                                                assetHolder.card.postDelayed(new Runnable() {
                                                    @Override
                                                    public void run() {
                                                        dismiss();
                                                    }
                                                }, 200);
                                            }
                                            else {
                                                Toast.makeText(getActivity(),"Can not dispence on this order because asset qty is low",Toast.LENGTH_LONG).show();
                                            }


                                        }
                                    }
                                }


                            });

                      /*      iv_search.setOnClickListener(new View.OnClickListener() {

                                @Override
                                public void onClick(View v) {
                                    Log.e("click","clicked");
                                    if(lockId.matches(assetLockId)){
                                        // asset.get(0).getLinked_asset();
                                        asset.size();
                                        Log.e("ASSET", String.valueOf(asset.size()));
                                        notifyDataSetChanged();
                                    }
                                }
                            }

                            );*/
                        }
                        @Override
                        public int getItemCount() {
                            return asset == null ? 0 : asset.size();
                        }
                    });
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }




        searchView.setOnQueryTextListener( new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {

                if (asset.contains(assetLockId)) {
                    getFilter().filter(query);
                    return false;
                }
                return  false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                String charText = newText;
                Log.d("text",newText);
               getFilter().filter(newText);
                return false;
            }
        } );



        return v;



    }

    public Filter getFilter() {
        return new Filter() {
            ArrayList<Asset> filfteredlist = new ArrayList<>();
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                String charString = charSequence.toString();
                if (charString.isEmpty()) {
                    asset = customFilter;
                } else {

                    for (Asset item : asset) {

                        if (item.getLinked_asset().toLowerCase().contains(charString.toLowerCase())) {
                            // if the item is matched we are
                            // adding it to our filtered list.
                            Log.e("FILTERED_ITEM", String.valueOf(item));
                            filfteredlist.add(item);
                        }
                    } if (filfteredlist.isEmpty()) {
                        // if no item is added in filtered list we are
                        // displaying a toast message as no data found.
                        Toast.makeText(getActivity(), "No Data Found..", Toast.LENGTH_SHORT).show();
                    }

                    asset = filfteredlist;
                }
                FilterResults filterResults = new FilterResults();
                filterResults.values = asset;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
               // mPostItems = (ArrayList<ApiAssetsListResponse.Datum>) results.values;
             //   asset = (ArrayList<Asset>) results.values;
              //  getFilter().filter((CharSequence) filfteredlist);
                Log.v("Publish","Call");


            }
        };
    }
    /*private void filter(String newText) {
        // creating a new array list to filter our data.
        ArrayList<Asset> filteredlist = new ArrayList<>();

        // running a for loop to compare elements.
        for (Asset item : asset) {

            if (item.getLinked_asset().toLowerCase().contains(newText.toLowerCase())) {
                // if the item is matched we are
                // adding it to our filtered list.
                Log.e("FILTERED_ITEM", String.valueOf(item));
                filteredlist.add(item);
            }
        }
        if (filteredlist.isEmpty()) {
            // if no item is added in filtered list we are
            // displaying a toast message as no data found.
            Toast.makeText(getActivity(), "No Data Found..", Toast.LENGTH_SHORT).show();
        } else {

            Log.e("FILTERED_LIST",String.valueOf(filteredlist));
             //.todo set adapter
     *//*asset.get(0).getLinked_asset();
            filterList(filteredlist);
             adapter.filterList(filteredlist);*//*

        }

    }
*/


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        return new Dialog(getActivity(), getTheme()){
            @Override
            public void onBackPressed() {
              getDialog().dismiss();
            }
        };
    }

    @Override
    public void onResume() {
        super.onResume();
        DisplayMetrics displayMetrics = new DisplayMetrics();
        ((AppCompatActivity) context).getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        // The absolute width of the available display size in pixels.
        int displayWidth = displayMetrics.widthPixels;
        // The absolute height of the available display size in pixels.
        int displayHeight = displayMetrics.heightPixels;

        // Initialize a new window manager layout parameters
        WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams();

        // Copy the alert dialog window attributes to new layout parameter instance
        layoutParams.copyFrom(getDialog().getWindow().getAttributes());

        // Set the alert dialog window width and height
        // Set alert dialog width equal to screen width 90%
        int dialogWindowWidth = (int) (displayWidth * 0.50f);
//         Set alert dialog height equal to screen height 90%
        int dialogWindowHeight = (int) (displayHeight * 0.85f);

        // Set the width and height for the layout parameters
        // This will bet the width and height of alert dialog
        layoutParams.width = dialogWindowWidth;
        layoutParams.height = dialogWindowHeight;

        // Apply the newly created layout parameters to the alert dialog window
        getDialog().getWindow().setAttributes(layoutParams);

        ViewGroup.LayoutParams params = getDialog().getWindow().getAttributes();
//        params.width = ViewGroup.LayoutParams.MATCH_PARENT;
//        params.height = ViewGroup.LayoutParams.MATCH_PARENT;
        getDialog().getWindow().setAttributes((WindowManager.LayoutParams) params);
        getDialog().setCancelable(false);
    }

    @Override
    public void onClick(View v) {
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    public static class AssetHolder extends RecyclerView.ViewHolder {
        private final View card;
        private TextView assetName, assetNumber,asset,tvAssetLockId;
       private final  ImageView iv_Search;
        //private final EditText et_lock_id;


        public AssetHolder(@NonNull View itemView) {
            super(itemView);
            assetName = itemView.findViewById(R.id.assetName);
            assetNumber = itemView.findViewById(R.id.assetNumber);
            tvAssetLockId = itemView.findViewById(R.id.assetLockId);
            iv_Search=itemView.findViewById(R.id.iv_search);
        //    et_lock_id=itemView.findViewById(R.id.et_lock_id);
            card = itemView;
        }



    }

    public void setAssetListener(OnAssetOperation assetListener) {
        this.assetListener = assetListener;

    }

}


