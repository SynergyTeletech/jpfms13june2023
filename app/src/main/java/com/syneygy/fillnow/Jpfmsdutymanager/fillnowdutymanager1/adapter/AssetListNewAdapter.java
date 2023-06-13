package com.syneygy.fillnow.Jpfmsdutymanager.fillnowdutymanager1.adapter;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.syneygy.fillnow.Jpfmsdutymanager.fillnowdutymanager1.R;
import com.syneygy.fillnow.Jpfmsdutymanager.fillnowdutymanager1.model.Asset;

import java.util.List;

public class AssetListNewAdapter extends RecyclerView.Adapter<AssetListNewAdapter.MyViewHolder> {
    private Context context;
    private List<Asset> list;


    public AssetListNewAdapter(Context context, List<Asset> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view= LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.asset_look,viewGroup,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, final int position) {
        myViewHolder.assetName.setText(list.get(position).getAssetName());
        //    assetHolder.assetNumber.setText(String.format("Equipment :%s", String.valueOf(position + 1)));
        myViewHolder.assetNumber.setText(String.format("Equipment Id:"+""+ list.get(position).getId()));
        String assetLockId;
        assetLockId= list.get(position).getLinked_asset();
 /*       myViewHolder.card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ass != null) {
                    assetListener.OnAssetSelected(asset.get(position).getId(), data.getString("qnty"), asset.get(position).getAssetName(), asset.get(position),position);
                    assetHolder.card.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            dismiss();
                        }
                    }, 200);
                }
            }


        });*/

                         /*   iv_search.setOnClickListener(new View.OnClickListener() {

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
        return list.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        String assetLockId;
        private final View card;
        private TextView assetName, assetNumber,asset;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            assetName = itemView.findViewById(R.id.assetName);
            assetNumber = itemView.findViewById(R.id.assetNumber);
            //    iv_Search=itemView.findViewById(R.id.iv_search);
            //    et_lock_id=itemView.findViewById(R.id.et_lock_id);
            card = itemView;
        }
    }
}
