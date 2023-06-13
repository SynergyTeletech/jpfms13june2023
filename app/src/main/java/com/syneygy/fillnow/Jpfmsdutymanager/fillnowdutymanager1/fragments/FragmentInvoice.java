package com.syneygy.fillnow.Jpfmsdutymanager.fillnowdutymanager1.fragments;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.syneygy.fillnow.Jpfmsdutymanager.fillnowdutymanager1.R;
import com.syneygy.fillnow.Jpfmsdutymanager.fillnowdutymanager1.activity.NavigationDrawerActivity;
import com.syneygy.fillnow.Jpfmsdutymanager.fillnowdutymanager1.network.ApiClient;
import com.syneygy.fillnow.Jpfmsdutymanager.fillnowdutymanager1.utils.PreferenceManager;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;


public class FragmentInvoice extends Fragment {
    private WebView wvInvoice;
    private String perfoma_ids;
    private byte[] encodeValue;
    private Context context;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = context;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_invoice, container, false);
        wvInvoice = view.findViewById(R.id.wv_invoice);
        perfoma_ids = PreferenceManager.read("performa_id", null);

        if (getArguments() != null && getArguments().containsKey("performa")) {
            perfoma_ids = getArguments().getString("performa");
            Toast.makeText(context, "Invoice Found", Toast.LENGTH_SHORT).show();
        }
        if (perfoma_ids != null) try {
            encodeValue = perfoma_ids.getBytes("UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        /*  wvInvoice.loadUrl("http://synergy.xsinfoways.net/smart_oms/bms/performa_invoice_format1.php?per_invoice=MzM3&category=NTE=");*/
//        StringBuffer buffer = new StringBuffer("http://synergy.xsinfoways.net/smart_oms/bms/performa_invoice_format1.php");
        StringBuffer buffer = new StringBuffer(ApiClient.INVOICE_PERFORMA_URL);
        buffer.append("?per_invoice=" + URLEncoder.encode("MzQ0"));
        buffer.append("&category=" + URLEncoder.encode("NTE="));

        wvInvoice.loadUrl(buffer.toString());

        wvInvoice.setWebViewClient(new MyClient());
        wvInvoice.setWebChromeClient(new GoogleClient());
        WebSettings webSettings = wvInvoice.getSettings();
        webSettings.setJavaScriptEnabled(true);
        wvInvoice.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
        wvInvoice.clearCache(true);
        wvInvoice.clearHistory();
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        if (context instanceof NavigationDrawerActivity) {
            ((NavigationDrawerActivity) context).setNavigationDrawerItemTitle("Invoice");
        }
    }
}
