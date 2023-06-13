package com.syneygy.fillnow.Jpfmsdutymanager.fillnowdutymanager1.interfaces;

import com.syneygy.fillnow.Jpfmsdutymanager.fillnowdutymanager1.model.CompartmentInfo;

/**
 * Created by Ved Yadav on 5/2/2019.
 */
public interface OnCompartmentOperation {
    void OnCompartmentSelected(String compartmentName, String atgSerialNo, CompartmentInfo compartmentInfo, String command);
}
