package com.syneygy.fillnow.Jpfmsdutymanager.fillnowdutymanager1.interfaces;

import com.syneygy.fillnow.Jpfmsdutymanager.fillnowdutymanager1.model.Asset;

/**
 * Created by Ved Yadav on 4/24/2019.
 */
public interface OnAssetOperation {
    void OnAssetSelected(String assetId, String remainingBalance,String assetName, Asset asset,int postion);
}
