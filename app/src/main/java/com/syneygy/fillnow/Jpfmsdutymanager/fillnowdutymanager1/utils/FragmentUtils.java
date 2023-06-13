package com.syneygy.fillnow.Jpfmsdutymanager.fillnowdutymanager1.utils;


import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import java.util.List;

/**
 * Created by Ved Yadav on 4/13/2019.
 */
public class FragmentUtils {
    public static boolean dispatchOnBackPressedToFragments(FragmentManager fm) {

        List<Fragment> fragments = fm.getFragments();
        boolean result;
        if (fragments != null && !fragments.isEmpty()) {
            for (Fragment frag : fragments) {
                if (frag != null && frag.isAdded() && frag.getChildFragmentManager() != null) {
                    // go to the next level of child fragments.
                    result = dispatchOnBackPressedToFragments(frag.getChildFragmentManager());
                    if (result) return true;
                }
            }
        }

        // if the back stack is not empty then we pop the last transaction.
        if (fm.getBackStackEntryCount() > 0) {

            fm.popBackStack();
            fm.executePendingTransactions();
            return true;
        }

        return false;
    }
}
