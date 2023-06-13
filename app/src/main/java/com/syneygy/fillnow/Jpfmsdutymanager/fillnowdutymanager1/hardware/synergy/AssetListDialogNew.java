package com.syneygy.fillnow.Jpfmsdutymanager.fillnowdutymanager1.hardware.synergy;

import android.content.Context;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.syneygy.fillnow.Jpfmsdutymanager.fillnowdutymanager1.R;
import com.syneygy.fillnow.Jpfmsdutymanager.fillnowdutymanager1.interfaces.OnAssetOperation;
import com.syneygy.fillnow.Jpfmsdutymanager.fillnowdutymanager1.model.Asset;

import java.util.ArrayList;

public class AssetListDialogNew  extends DialogFragment implements View.OnClickListener {

    private TextView quantity;
    private Context context;
    private RecyclerView assetListRecycler;
    private ArrayList<Asset> asset;
    private OnAssetOperation assetListener;
    private SearchView searchView;
    //ArrayAdapter<Asset> adapter;
    // AssetHolder adapterr;
    private AssetListDialog.AssetHolder adapterr;
    ArrayAdapter<AssetListDialog.AssetHolder> adapter;
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
        searchView =v.findViewById(R.id.searchView);

        Bundle data = getArguments();
        if (data != null) {
            try {
                if (data.containsKey("assetList")) {
                    asset = data.getParcelableArrayList("assetList");
                    quantity.setText(String.format("Ordered Fuel Quantity Left: %s Ltr.", data.getString("balance_qnty")));
                    // assetListRecycler.setLayoutManager(new GridLayoutManager(context, GridLayoutManager));
                    assetListRecycler.setLayoutManager(new GridLayoutManager(context, 4));

                    LayoutInflater layoutInflater;
                    ViewGroup viewGroup = null;

                    adapterr = new AssetListDialog.AssetHolder(LayoutInflater.from(context).inflate(R.layout.asset_look,null));




                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }


        searchView.setOnQueryTextListener( new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query)
            {
            /*    if (asset.contains(assetLockId)) {
                    Log.e("ASSET_LOCK_ID", assetLockId);
                    Log.e("text listener", "text_listenr");


                    assetListRecycler.setAdapter((new RecyclerView.Adapter<AssetHolder>());
                }*/
                // adapter.getFilter().filter(assetLockId);

               /* else {
                    Toast.makeText(context, "Not found", Toast.LENGTH_LONG).show();
                }*/
                return false;
            }
            @Override
            public boolean onQueryTextChange(String newText)
            {
                Log.e("text cahnge","text_change");
                //adapter.getFilter().filter(newText);
                filter(newText);

                return false;

            }
        });

        return v;



    }

    private void filter(String newText) {
        // creating a new array list to filter our data.
        ArrayList<Asset> filteredlist = new ArrayList<>();

        // running a for loop to compare elements.
        for (Asset item : asset) {
            // checking if the entered string matched with any item of our recycler view.
            // if (item.getLinked_asset().toLowerCase().contains(newText.toLowerCase())) {
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
            // at last we are passing that filtered
            // list to our adapter class.

            Log.e("FILTERED_LIST",String.valueOf(filteredlist));

            //  adapter.getFilter().filter((CharSequence) filteredlist);

            // adapter.filterList(filteredlist);
        }

    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
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

    }

    @Override
    public void onClick(View v) {
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
    }



    public void setAssetListener(OnAssetOperation assetListener) {
        this.assetListener = assetListener;

    }

}


