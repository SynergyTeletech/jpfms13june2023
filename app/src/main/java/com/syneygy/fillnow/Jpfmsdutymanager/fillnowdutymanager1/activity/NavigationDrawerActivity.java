package com.syneygy.fillnow.Jpfmsdutymanager.fillnowdutymanager1.activity;

import static com.syneygy.fillnow.Jpfmsdutymanager.fillnowdutymanager1.activity.SynergyDispenser.hexStringToByteArray;

import android.annotation.SuppressLint;
import android.bluetooth.BluetoothSocket;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.internal.LinkedTreeMap;
import com.koushikdutta.async.Util;
import com.koushikdutta.async.callback.CompletedCallback;
import com.syneygy.fillnow.Jpfmsdutymanager.fillnowdutymanager1.BrancoApp;
import com.syneygy.fillnow.Jpfmsdutymanager.fillnowdutymanager1.R;
import com.syneygy.fillnow.Jpfmsdutymanager.fillnowdutymanager1.adapter.DrawerItemNavigationAdapter;
import com.syneygy.fillnow.Jpfmsdutymanager.fillnowdutymanager1.constant.AppConstants;
import com.syneygy.fillnow.Jpfmsdutymanager.fillnowdutymanager1.fragments.FragmentAcceptedOrders;
import com.syneygy.fillnow.Jpfmsdutymanager.fillnowdutymanager1.fragments.FragmentInvoice;
import com.syneygy.fillnow.Jpfmsdutymanager.fillnowdutymanager1.fragments.FragmentNotify;
import com.syneygy.fillnow.Jpfmsdutymanager.fillnowdutymanager1.fragments.FragmentOperationMenu;
import com.syneygy.fillnow.Jpfmsdutymanager.fillnowdutymanager1.fragments.FragmentOrderList;
import com.syneygy.fillnow.Jpfmsdutymanager.fillnowdutymanager1.fragments.FragmentReceivedPayment;
import com.syneygy.fillnow.Jpfmsdutymanager.fillnowdutymanager1.fragments.FragmentRoutePlan;
import com.syneygy.fillnow.Jpfmsdutymanager.fillnowdutymanager1.fragments.FragmentViewProfile;
import com.syneygy.fillnow.Jpfmsdutymanager.fillnowdutymanager1.fragments.PassordUpdateFragment;
import com.syneygy.fillnow.Jpfmsdutymanager.fillnowdutymanager1.hardware.interfaces.RouterResponseRelay;
import com.syneygy.fillnow.Jpfmsdutymanager.fillnowdutymanager1.hardware.serverpack.ServerRelay;
import com.syneygy.fillnow.Jpfmsdutymanager.fillnowdutymanager1.hardware.synergy.Commands;
import com.syneygy.fillnow.Jpfmsdutymanager.fillnowdutymanager1.model.LoginResponse;
import com.syneygy.fillnow.Jpfmsdutymanager.fillnowdutymanager1.model.NavigationModel;
import com.syneygy.fillnow.Jpfmsdutymanager.fillnowdutymanager1.model.orderSpinnermodel;
import com.syneygy.fillnow.Jpfmsdutymanager.fillnowdutymanager1.utils.FineteckCalculation;
import com.syneygy.fillnow.Jpfmsdutymanager.fillnowdutymanager1.utils.FragmentUtils;
import com.syneygy.fillnow.Jpfmsdutymanager.fillnowdutymanager1.utils.NetworkChangeReceiver;
import com.syneygy.fillnow.Jpfmsdutymanager.fillnowdutymanager1.utils.SharedPref;
import com.syneygy.fillnow.Jpfmsdutymanager.fillnowdutymanager1.utils.TokheiumCalculation;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.ref.WeakReference;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.LinkedHashMap;
import java.util.Locale;
import java.util.TimeZone;


public class NavigationDrawerActivity extends BaseActivity implements View.OnClickListener, RouterResponseRelay {
    private String[] mNavigationDrawerItemTitle;
    InetAddress  inetAddress1;

//    private ClientAtg  client2;
    Handler presetHandler;
    Runnable presetrunnable;
    int test = 0;

    int atgcount = 0;
    private String intialATG;
    private double tank1MaxVolume = 0f, tank2MaxVolume = 0f;
    private double tankMaxHeight = 0f;
    private LinkedHashMap<String, JSONObject> atgLinkedHashMapTank1;
    Runnable runnableatg;
    private String atgSerialNoTank1 = "";
    private String atgDipChart="";
    
    private boolean isAtgGet = false, isAtgLastReading = false;
    public DrawerLayout mDrawerLayout;
    private ListView mDrawerList;
    public Toolbar mToolbar;
    public DrawerLayout drawerLayout;
    private CharSequence mDrawerTitle;
    private CharSequence mTitle;
    public static TextView availableBalance;
    public ActionBarDrawerToggle mActionBarDrawerToggle;
    private String TAG = NavigationDrawerActivity.class.getSimpleName();
    public ImageView ivLogo;
    public static SearchView searchView;
    public TextView tvToolbar, fragment_title;
    private LinearLayout llLeftDrawer;
    private ImageView backPressed;
    private TextView eWayBill, inVoice, receivedPayment, notify, viewProfile, routePlan;
    private ArrayList<orderSpinnermodel> spinneritem;
    private String vehicleId;
    private Spinner order_spinner;
    boolean doubleBackToExitPressedOnce = false;
    private TextView totalTicketAndFuel;
    private NetworkChangeReceiver networkChangeReceiver = new NetworkChangeReceiver();
    private LinearLayout llTicketAndFuel;
    LoginResponse response;
    RouterResponseRelay routerResponseRelay;

    public static BluetoothSocket btsocket;
    private WeakReference<ClientAtg.ReciverListnerAtg> listener;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        Thread.setDefaultUncaughtExceptionHandler(new ExceptionHandler(this));
        super.onCreate(savedInstanceState);
        setContentView(R.layout.navigation_drawer);



//        ServerRelay serverRelay=new ServerRelay("192.168.1.104",54301,this);


        response=SharedPref.getLoginResponse();

        Log.d("responseHex",""+response);
        try {
            Bundle bundle = getIntent().getExtras();
            vehicleId = bundle.getString(AppConstants.Vehicle_id);
            setUpToolbar();
            setViews();
            setUpOrderList();
            mTitle = mDrawerTitle = getTitle();
            mNavigationDrawerItemTitle = getResources().getStringArray(R.array.navigation_drawer_items_array);
            mDrawerLayout = findViewById(R.id.drawer_layout);
            mDrawerList = findViewById(R.id.left_drawer);
            llLeftDrawer = findViewById(R.id.ll_left_drawer);
            NavigationModel[] navigationDrawer = new NavigationModel[9];
            navigationDrawer[0] = new NavigationModel(R.drawable.ic_profile, mNavigationDrawerItemTitle[0]);
            navigationDrawer[1] = new NavigationModel(R.drawable.ic_profile, mNavigationDrawerItemTitle[1]);
            navigationDrawer[2] = new NavigationModel(R.drawable.ic_profile, mNavigationDrawerItemTitle[2]);
            navigationDrawer[3] = new NavigationModel(R.drawable.ic_profile, mNavigationDrawerItemTitle[3]);
            navigationDrawer[4] = new NavigationModel(R.drawable.ic_profile, mNavigationDrawerItemTitle[4]);
            navigationDrawer[5] = new NavigationModel(R.drawable.ic_profile, mNavigationDrawerItemTitle[5]);
            navigationDrawer[6] = new NavigationModel(R.drawable.ic_profile, mNavigationDrawerItemTitle[6]);
            navigationDrawer[7] = new NavigationModel(R.drawable.ic_profile, "Change Password");
            navigationDrawer[8] = new NavigationModel(R.drawable.ic_profile, mNavigationDrawerItemTitle[7]);

            getSupportActionBar().setDisplayHomeAsUpEnabled(false);
            DrawerItemNavigationAdapter navigationAdapter = new DrawerItemNavigationAdapter(this, R.layout.nav_list_row, navigationDrawer);
            mDrawerList.setAdapter(navigationAdapter);
            mDrawerList.setOnItemClickListener(new DrawerItemClickListener());
            mDrawerLayout = findViewById(R.id.drawer_layout);
            mDrawerLayout.setDrawerListener(mActionBarDrawerToggle);
            setUpDrawerToggle();

        } catch (NullPointerException e) {
            e.printStackTrace();
        }

        if(response.getData().get(0).getAtgDataList().isEmpty()){
            atgSerialNoTank1="1234";
        }else {
            atgSerialNoTank1 = SharedPref.getLoginResponse().getData().get(0).getAtgDataList().get(0).getData_atg_serial_no();


        }
        atgDipChart=response.getData().get(0).getAtgDataList().get(0).getData_volume();
//        if(!response.getData().get(0).getAtgDataList().isEmpty()) {
//            presetHandler = new Handler();
//            presetrunnable = new Runnable() {
//                @Override
//                public void run() {
//                    getATG1Data();
//
//                }
//            };
//            presetHandler.postDelayed(presetrunnable, 500);
//
//        }


    }

    private void getATG1Data() {
         if(ServerRelay.getSocket()!=null){

           sendCommandToRelay("#*SP31");
         }
//        client2.writeData("#*SP31".getBytes());
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if(response.getVehicle_data().get(0).getMakeOfAtg().contains("Finetech")) {
//                client2.writeData(hexStringToByteArray(Commands.FINETEK_ATG_LEVEL.toString()));
                }
                else if(response.getVehicle_data().get(0).getMakeOfAtg().contains("Tokheium")){
//                    client2.writeData(("M"+atgSerialNoTank1+"\r").getBytes());
                    if(ServerRelay.getSocket()!=null){
                        sendCommandToRelay("M"+atgSerialNoTank1+"\r");
                    }


                }
                else {
                    if(ServerRelay.getSocket()!=null){
                        sendCommandToRelay("M"+atgSerialNoTank1+"\r");
                    }
//                   client2.writeData(("M"+atgSerialNoTank1+"\r").getBytes());
                }
            }
        },2000);


    }


    private void sendCommandToRelay(String s) {
        Util.writeAll(ServerRelay.getSocket(), s.getBytes(), new CompletedCallback() {
            @Override
            public void onCompleted(Exception ex) {

            }
        });

    }


    private void connect232Port() {




//        try {
//            Log.v("Ip", "232Port" + SharedPref.getServerIp());
//            inetAddress1 = InetAddress.getByName(SharedPref.getServerIp());
//
//        } catch (UnknownHostException e) {
//            e.printStackTrace();
//        }
//
//        client2 = new ClientAtg(inetAddress1, 232, get232ReciverListner());
//        client2.execute();
    }



    @SuppressLint("SetTextI18n")
    private void setViews() {
        backPressed = findViewById(R.id.iv_backpressed);
        eWayBill = findViewById(R.id.operationMenu);
        receivedPayment = findViewById(R.id.received_Payment);
        notify = findViewById(R.id.notify);
        inVoice = findViewById(R.id.inVoice);
        fragment_title = findViewById(R.id.tv_fragment_title);
        viewProfile = findViewById(R.id.view_Profile);
        routePlan = findViewById(R.id.route_plan);
        order_spinner = findViewById(R.id.spinner_items);
        totalTicketAndFuel = findViewById(R.id.totalTicketAndFuel);
        llTicketAndFuel = findViewById(R.id.ll_text_container2);
        searchView = findViewById(R.id.searchViewToolbar);
        availableBalance = findViewById(R.id.availableBalance);

//        backPressed.setOnClickListener(this);
        eWayBill.setOnClickListener(this);
        inVoice.setOnClickListener(this);
        receivedPayment.setOnClickListener(this);
        notify.setOnClickListener(this);
        viewProfile.setOnClickListener(this);
        routePlan.setOnClickListener(this);
        availableBalance.setOnClickListener(this);
        /*if (SharedPref.getAvailablevolume() != null) {
            availableBalance.setText("Fuel Balance Quantity: " + SharedPref.getAvailablevolume());
            Log.v("FUEL Q","SK" + SharedPref.getAvailablevolume());
        }*/
    }

    /*
    method to set fragment dashboard
     */
//    private void setUpDashboard() {
//
//        FragmentDashboard fragmentDashboard = new FragmentDashboard();
//        replaceFragment(NavigationDrawerActivity.this, R.id.ll_dashboard_container, fragmentDashboard, null, true);
//    }

    private void setUpDrawerToggle() {
        mActionBarDrawerToggle = new ActionBarDrawerToggle(NavigationDrawerActivity.this, mDrawerLayout, mToolbar, R.string.app_name, R.string.app_name);
        mActionBarDrawerToggle.syncState();
    }

    @Override
    public void onClick(View view) {
        findViewById(R.id.operationMenuLay).setVisibility(View.GONE);
        switch (view.getId()) {
            case R.id.operationMenu:
                setupOperationMenu();
                break;
            case R.id.inVoice:
                setupInvoice();
                break;
            case R.id.received_Payment:
                setupReceivedPayment();
                break;
            case R.id.notify:
                setupNotify();
                break;
            case R.id.view_Profile:
                setupViewProfile();
                break;
            case R.id.route_plan:
                setupRoutePlan();
                break;
            case R.id.availableBalance:
                  getRefreshAtg();
                break;
        }
    }

    private void getRefreshAtg() {

        if(!SharedPref.getLoginResponse().getData().get(0).getAtgDataList().isEmpty()) {
            presetHandler = new Handler();
            presetrunnable = new Runnable() {
                @Override
                public void run() {
                    getATG1Data();

                }
            };
            presetHandler.postDelayed(presetrunnable, 500);
        }
    }

    public void setUpOrderList() {
        fragment_title.setText(R.string.text_order_list);
        getSupportActionBar().setHomeButtonEnabled(true);
        FragmentOrderList orderList = new FragmentOrderList();
        Bundle bundle = new Bundle();
        bundle.putString(AppConstants.Vehicle_id, vehicleId);
        orderList.setArguments(bundle);
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.ll_fragment_container, orderList, vehicleId)
                .addToBackStack(FragmentOrderList.class.getSimpleName())
                .commit();
    }

    private void setupRoutePlan() {
        fragment_title.setText(R.string.text_route_plan);
        FragmentRoutePlan fragmentRoutePlan = new FragmentRoutePlan();
        replaceFragment(NavigationDrawerActivity.this, R.id.ll_fragment_container, fragmentRoutePlan, null, false);
    }


    private void setupViewProfile() {
        fragment_title.setText(R.string.text_view_profile);
        FragmentViewProfile fragmentViewProfile = new FragmentViewProfile();
        replaceFragment(NavigationDrawerActivity.this, R.id.ll_fragment_container, fragmentViewProfile, null, false);
    }

    private void setupNotify() {
        fragment_title.setText(R.string.text_notify);
        FragmentNotify fragmentNotify = new FragmentNotify();
        replaceFragment(NavigationDrawerActivity.this, R.id.ll_fragment_container, fragmentNotify, null, false);
    }

    private void setupReceivedPayment() {
        fragment_title.setText(R.string.text_received_payment);
        FragmentReceivedPayment fragmentReceivedPayment = new FragmentReceivedPayment();
        replaceFragment(NavigationDrawerActivity.this, R.id.ll_fragment_container, fragmentReceivedPayment, null, false);
    }

    private void setupInvoice() {
        fragment_title.setText(R.string.text_invoice);
        FragmentInvoice fragmentInvoice = new FragmentInvoice();
        replaceFragment(NavigationDrawerActivity.this, R.id.ll_fragment_container, fragmentInvoice, null, false);
    }

    public void setupOperationMenu() {
        fragment_title.setText(R.string.text_eway_bill);
        FragmentOperationMenu fragmentOperationMenu = new FragmentOperationMenu();
        replaceFragment(NavigationDrawerActivity.this, R.id.ll_fragment_container, fragmentOperationMenu, null, true);
    }

    @Override
    public void RecivedData1(String responseAscci, String responseHex, boolean isConnected) {
       if(!responseAscci.contains("SERIAL PORT")) {
           Log.d("hexRespnse", responseAscci + "," + responseHex);
           String volumes = "0";
           if (responseHex.contains("0103")) {
               FineteckCalculation fineteckCalculation = new FineteckCalculation();
               volumes = String.format("%.2f", fineteckCalculation.getAtgState(fineteckCalculation.response(responseHex), atgDipChart));
           } else if (responseAscci.contains(atgSerialNoTank1)) {
               TokheiumCalculation tokheiumCalculation = new TokheiumCalculation();
               Float volume = tokheiumCalculation.getAtgState(tokheiumCalculation.response(responseAscci), atgDipChart);
               volumes = String.format("%.2f", volume);
               Log.d("hexRespnse", responseAscci + "," + volumes);
           } else {

           }

           String finalVolumes = volumes;
           runOnUiThread(new Runnable() {
               @Override
               public void run() {
                   Log.d("hexRespnse", responseAscci + "," + finalVolumes);
                   NavigationDrawerActivity.availableBalance.setText("Fuel Balance Quantity: " + finalVolumes);
               }
           });
       }
    }


    private class DrawerItemClickListener implements AdapterView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            selectItem(position);
        }
    }

    private void selectItem(int position) {
        //  Fragment fragment = null;
        findViewById(R.id.operationMenuLay).setVisibility(View.GONE);
        switch (position) {
            case 0:
                setUpOrderList();
                mDrawerLayout.closeDrawers();
                findViewById(R.id.operationMenuLay).setVisibility(View.VISIBLE);
                break;

            case 1:
                setupViewProfile();
                mDrawerLayout.closeDrawers();
                break;

            case 2:
                setupOperationMenu();
                mDrawerLayout.closeDrawers();
                break;

            case 3:
                setupInvoice();
                mDrawerLayout.closeDrawers();
                break;

            case 4:
                setupReceivedPayment();
                mDrawerLayout.closeDrawers();
                break;

            case 5:
                setupRoutePlan();
                mDrawerLayout.closeDrawers();
                break;

            case 6:
                startActivity(new Intent(NavigationDrawerActivity.this, SettingsActivity.class));
                break;
            case 7:
                fragment_title.setText(R.string.text_route_plan);
                PassordUpdateFragment fragmentRoutePlan = new PassordUpdateFragment();
                replaceFragment(NavigationDrawerActivity.this, R.id.ll_fragment_container, fragmentRoutePlan, null, false);

                break;
            case 8:
                startActivity(new Intent(this, LoginActivity.class));
                finish();


                break;
//            case 17:
//                if (sharedPerferences.isLogin()){
//                    getLogOutApi();
//                }else {drawerLayout.isDrawerOpen(GravityCompat.START);
//                    drawerLayout.closeDrawers();
//                    showToast("You are not Logged In");
//                }
//
//                break;
        }

//        if (fragment != null) {
//            replaceFragment(this, R.id.ll_dashboard_container, fragment, null, false);
//
//            mDrawerList.setItemChecked(position, true);
//            mDrawerList.setSelection(position);
//            setTitle(mNavigationDrawerItemTitle[position]);
//            mDrawerLayout.closeDrawer(Gravity.LEFT);
//        } else {
//            Log.e(TAG, "Error in creating fragment");
//        }
    }

//    private void getLogOutApi() {
//        mActivity.showProgressDialog(mActivity);
//        Map<String,String> hashMap=new HashMap<>();
//
//        ApiInterface apiInterface= ApiClient.getClientCI().create(ApiInterface.class);
//        apiInterface.getLogOut(hashMap).enqueue(new Callback<Logout>() {
//            @Override
//            public void onResponse(Call<Logout> call, Response<Logout> response) {
//                mActivity.hideProgressDialog(mActivity);
//                if (response.body()!=null){
//                    if (response.body().getSucc()){
//                        Log.d(TAG, "Response : " + response.body().toString());
//                        Intent intent=new Intent(NavigationDrawerActivity.this, FragmentSignIn.class);
//                        startActivity(intent);
//                        finish();
//                    }
//                }
//            }
//
//            @Override
//            public void onFailure(Call<Logout> call, Throwable t) {
//
//            }
//        });
//    }

    /* method to set toolbar */
    private void setUpToolbar() {
        mToolbar = findViewById(R.id.toolbar);
        ivLogo = mToolbar.findViewById(R.id.iv_logo);
        tvToolbar = mToolbar.findViewById(R.id.tv_toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

    }

    @Override
    public void onBackPressed() {
        Fragment fragment = this.getSupportFragmentManager().findFragmentById(R.id.ll_fragment_container);
        if (fragment instanceof FragmentOrderList) {
            if (doubleBackToExitPressedOnce) {
                finish();
                return;
            }

            this.doubleBackToExitPressedOnce = true;
            Toast.makeText(this, "Please click BACK again to exit", Toast.LENGTH_SHORT).show();

            new Handler().postDelayed(new Runnable() {

                @Override
                public void run() {
                    doubleBackToExitPressedOnce = false;
                }
            }, 2000);
        } else if (!FragmentUtils.dispatchOnBackPressedToFragments(getSupportFragmentManager())) {
            // if no child fragment consumed the onBackPressed event,
            // we execute the default behaviour.
            super.onBackPressed();
        }
        try {

            if (fragment != null && fragment.getChildFragmentManager().getBackStackEntryCount() > 0) {
                Fragment frag_Child = fragment.getChildFragmentManager().findFragmentByTag(FragmentAcceptedOrders.class.getSimpleName());
                if (frag_Child != null) {
                    frag_Child.onResume();
                }
            } else {
                if (fragment != null) {
                    fragment.onResume();
                }
            }
//
//            if (fragment instanceof FragmentOrderList) {
//                super.onBackPressed();
//            } else {
//                if (fragment != null && fragment.getChildFragmentManager() != null && fragment.getChildFragmentManager().getBackStackEntryCount() > 0) {
//                    fragment.getChildFragmentManager().popBackStack();

//                } else {
//                    getSupportFragmentManager().popBackStack();
//                    fragment.onResume();
//                }
//            }

        } catch (Exception e) {
            e.printStackTrace();
        }

//        if (fragment instanceof FragmentInvoice) {
//            FragmentOrderList fragmentDashboard = new FragmentOrderList();
//            replaceFragment(this, R.id.ll_dashboard_container, fragmentDashboard, null, false);
//            ivLogo.setVisibility(View.VISIBLE);
//            mActionBarDrawerToggle.setDrawerIndicatorEnabled(true);
//
////            tvToolbar.setVisibility(View.GONE);
//            mDrawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED);
//
//
//        } else if (fragment instanceof FragmentViewProfile) {
//            FragmentOrderList fragmentDashboard = new FragmentOrderList();
//            this.replaceFragment(this, R.id.ll_dashboard_container, fragmentDashboard, null, false);
//            ivLogo.setVisibility(View.VISIBLE);
//            mActionBarDrawerToggle.setDrawerIndicatorEnabled(true);
//
////            tvToolbar.setVisibility(View.GONE);
//            mDrawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED);
//
//        } else if (fragment instanceof FragmentRoutePlan) {
//            FragmentOrderList fragmentDashboard = new FragmentOrderList();
//            this.replaceFragment(this, R.id.ll_dashboard_container, fragmentDashboard, null, false);
//            ivLogo.setVisibility(View.VISIBLE);
//            mActionBarDrawerToggle.setDrawerIndicatorEnabled(true);
//
////            tvToolbar.setVisibility(View.GONE);
//            mDrawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED);
//
//        } else if (fragment instanceof FragmentReceivedPayment) {
//            FragmentOrderList fragmentDashboard = new FragmentOrderList();
//            this.replaceFragment(this, R.id.ll_dashboard_container, fragmentDashboard, null, false);
//            ivLogo.setVisibility(View.VISIBLE);
//            mActionBarDrawerToggle.setDrawerIndicatorEnabled(true);
//
////            tvToolbar.setVisibility(View.GONE);
//            mDrawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED);
//
//        } else if (fragment instanceof FragmentOperationMenu) {
//            FragmentOrderList fragmentDashboard = new FragmentOrderList();
//            this.replaceFragment(this, R.id.ll_dashboard_container, fragmentDashboard, null, false);
//            ivLogo.setVisibility(View.VISIBLE);
//            mActionBarDrawerToggle.setDrawerIndicatorEnabled(true);
//
////            tvToolbar.setVisibility(View.GONE);
//            mDrawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED);
//
//        } else if (fragment instanceof FragmentNotify) {
//            FragmentOrderList fragmentDashboard = new FragmentOrderList();
//            this.replaceFragment(this, R.id.ll_dashboard_container, fragmentDashboard, null, false);
//            ivLogo.setVisibility(View.VISIBLE);
//            mActionBarDrawerToggle.setDrawerIndicatorEnabled(true);
//
////            tvToolbar.setVisibility(View.GONE);
//            mDrawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED);
//
//
//        } else if (fragment instanceof FragmentOrderList) {
//            super.onBackPressed();
//
//        }


        //        fragment_title.setText(R.string.text_order_list);
//        FragmentOrderList fragmentOrderList = new FragmentOrderList();
////        fragmentOrderList.bindListener(new AcceptedOrderList() {
////            @Override
////            public void sendOrderListdata(List<Order> list) {
////                if(list.size()>-1) {
////                    Log.e(TAG, "sizzzzzzzeeeeeeee" + list.size());
////                }else{
////                    Toast.makeText(NavigationDrawerActivity.this,""+list.size(),Toast.LENGTH_SHORT).show();
////                }
////            }
////        });
//        Bundle bundle = getIntent().getExtras();
//        vehicleId = bundle.getString(AppConstants.Vehicle_id);
//        replaceFragment(NavigationDrawerActivity.this, R.id.ll_fragment_container, fragmentOrderList, bundle, true);


        //    Fragment fragment = this.getSupportFragmentManager().findFragmentById(R.id.ll_fragment_container);

//        if (fragment instanceof FragmentDashboard) {
//            finish();
//        } else if (fragment instanceof FragmentPlaceOrder) {
//            //super.onBackPressed();
//            FragmentDashboard fragmentDashboard = new FragmentDashboard();
//            this.replaceFragment(this, R.id.ll_dashboard_container, fragmentDashboard, null, false);
//            ivLogo.setVisibility(View.VISIBLE);
//            mActionBarDrawerToggle.setDrawerIndicatorEnabled(true);
//            mToolbar.setBackgroundColor(getResources().getColor(R.color.c_white));
//            tvToolbar.setVisibility(View.GONE);
//            mDrawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED);
//
//        }
        //             else if (fragment instanceof FragmentOrderHistory) {
//            FragmentDashboard fragmentDashboard = new FragmentDashboard();
//            this.replaceFragment(this, R.id.ll_dashboard_container, fragmentDashboard, null, false);
//            ivLogo.setVisibility(View.VISIBLE);
//            mActionBarDrawerToggle.setDrawerIndicatorEnabled(true);
//            mToolbar.setBackgroundColor(getResources().getColor(R.color.c_white));
//            tvToolbar.setVisibility(View.GONE);
//            mDrawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED);
//        } else if (fragment instanceof FragmentInvoice) {
//            FragmentDashboard fragmentDashboard = new FragmentDashboard();
//            this.replaceFragment(this, R.id.ll_dashboard_container, fragmentDashboard, null, false);
//            ivLogo.setVisibility(View.VISIBLE);
//            mActionBarDrawerToggle.setDrawerIndicatorEnabled(true);
//            mToolbar.setBackgroundColor(getResources().getColor(R.color.c_white));
//            tvToolbar.setVisibility(View.GONE);
//            mDrawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED);
//        } else if (fragment instanceof FragmentGoogleMap) {
//            FragmentDashboard fragmentDashboard = new FragmentDashboard();
//            this.replaceFragment(this, R.id.ll_dashboard_container, fragmentDashboard, null, false);
//            ivLogo.setVisibility(View.VISIBLE);
//            mActionBarDrawerToggle.setDrawerIndicatorEnabled(true);
//            mToolbar.setBackgroundColor(getResources().getColor(R.color.c_white));
//            tvToolbar.setVisibility(View.GONE);
//            mDrawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED);
//        } else if (fragment instanceof FragmentLedger) {
//            FragmentDashboard fragmentDashboard = new FragmentDashboard();
//            this.replaceFragment(this, R.id.ll_dashboard_container, fragmentDashboard, null, false);
//            ivLogo.setVisibility(View.VISIBLE);
//            mActionBarDrawerToggle.setDrawerIndicatorEnabled(true);
//            mToolbar.setBackgroundColor(getResources().getColor(R.color.c_white));
//            tvToolbar.setVisibility(View.GONE);
//            mDrawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED);
//        } else if (fragment instanceof FragmentMakePayment) {
//            FragmentDashboard fragmentDashboard = new FragmentDashboard();
//            this.replaceFragment(this, R.id.ll_dashboard_container, fragmentDashboard, null, false);
//            ivLogo.setVisibility(View.VISIBLE);
//            mActionBarDrawerToggle.setDrawerIndicatorEnabled(true);
//            mToolbar.setBackgroundColor(getResources().getColor(R.color.c_white));
//            tvToolbar.setVisibility(View.GONE);
//            mDrawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED);
//        } else if (fragment instanceof FragmentEditViewProfile) {
//            FragmentDashboard fragmentDashboard = new FragmentDashboard();
//            this.replaceFragment(this, R.id.ll_dashboard_container, fragmentDashboard, null, false);
//            ivLogo.setVisibility(View.VISIBLE);
//            mActionBarDrawerToggle.setDrawerIndicatorEnabled(true);
//            mToolbar.setBackgroundColor(getResources().getColor(R.color.c_white));
//            tvToolbar.setVisibility(View.GONE);
//            mDrawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED);
//        } else if (fragment instanceof FragmentUpdatePassword) {
//            FragmentDashboard fragmentDashboard = new FragmentDashboard();
//            this.replaceFragment(this, R.id.ll_dashboard_container, fragmentDashboard, null, false);
//            ivLogo.setVisibility(View.VISIBLE);
//            mActionBarDrawerToggle.setDrawerIndicatorEnabled(true);
//            mToolbar.setBackgroundColor(getResources().getColor(R.color.c_white));
//            tvToolbar.setVisibility(View.GONE);
//            mDrawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED);
//        } else if (fragment instanceof FragmentOffersRefferrals) {
//            FragmentDashboard fragmentDashboard = new FragmentDashboard();
//            this.replaceFragment(this, R.id.ll_dashboard_container, fragmentDashboard, null, false);
//            ivLogo.setVisibility(View.VISIBLE);
//            mActionBarDrawerToggle.setDrawerIndicatorEnabled(true);
//            mToolbar.setBackgroundColor(getResources().getColor(R.color.c_white));
//            tvToolbar.setVisibility(View.GONE);
//            mDrawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED);
//        } else if (fragment instanceof FragmentNotification) {
//            FragmentDashboard fragmentDashboard = new FragmentDashboard();
//            this.replaceFragment(this, R.id.ll_dashboard_container, fragmentDashboard, null, false);
//            ivLogo.setVisibility(View.VISIBLE);
//            mActionBarDrawerToggle.setDrawerIndicatorEnabled(true);
//            mToolbar.setBackgroundColor(getResources().getColor(R.color.c_white));
//            tvToolbar.setVisibility(View.GONE);
//            mDrawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED);
//        } else if (fragment instanceof FragmentSetting) {
//            FragmentDashboard fragmentDashboard = new FragmentDashboard();
//            this.replaceFragment(this, R.id.ll_dashboard_container, fragmentDashboard, null, false);
//            ivLogo.setVisibility(View.VISIBLE);
//            mActionBarDrawerToggle.setDrawerIndicatorEnabled(true);
//            mToolbar.setBackgroundColor(getResources().getColor(R.color.c_white));
//            tvToolbar.setVisibility(View.GONE);
//            mDrawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED);
//        } else if (fragment instanceof FragmentPerformaInvoice) {
//            FragmentDashboard fragmentDashboard = new FragmentDashboard();
//            this.replaceFragment(this, R.id.ll_dashboard_container, fragmentDashboard, null, false);
//            ivLogo.setVisibility(View.VISIBLE);
//            mActionBarDrawerToggle.setDrawerIndicatorEnabled(true);
//            mToolbar.setBackgroundColor(getResources().getColor(R.color.c_white));
//            tvToolbar.setVisibility(View.GONE);
//            mDrawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED);
//        }else if (fragment instanceof FragmentWallet) {
//            FragmentDashboard fragmentDashboard = new FragmentDashboard();
//            this.replaceFragment(this, R.id.ll_dashboard_container, fragmentDashboard, null, false);
//            ivLogo.setVisibility(View.VISIBLE);
//            mActionBarDrawerToggle.setDrawerIndicatorEnabled(true);
//            mToolbar.setBackgroundColor(getResources().getColor(R.color.c_white));
//            tvToolbar.setVisibility(View.GONE);
//            mDrawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED);
//
//        }else  if (fragment instanceof FragmentMakePayment){
//            FragmentDashboard fragmentDashboard = new FragmentDashboard();
//            this.replaceFragment(this, R.id.ll_dashboard_container, fragmentDashboard, null, false);
//            ivLogo.setVisibility(View.VISIBLE);
//            mActionBarDrawerToggle.setDrawerIndicatorEnabled(true);
//            mToolbar.setBackgroundColor(getResources().getColor(R.color.c_white));
//            tvToolbar.setVisibility(View.GONE);
//            mDrawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED);
//
//        }
//        else {
//            super.onBackPressed();
//        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        routerResponseRelay=this;
        ServerRelay.getInstance("192.168.1.104",54301).setListner(this);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                for(int i=0;i<5;i++) {
                    getATG1Data();
                }
            }
        },3000);


    }

    @Override
    protected void onResume() {
        super.onResume();

        BrancoApp.activityResumed();
    }

    @Override
    protected void onPause() {
        super.onPause();
//        client2.stopClint();
//        client2.cancel(true);
        BrancoApp.activityPaused();
    }

    public void setNavigationDrawerItemTitle(String title) {
        if (fragment_title != null) fragment_title.setText(title);
    }

    public void setTotalTicketAndFuel(String ticket, String fuel) {
        if (totalTicketAndFuel != null)
            totalTicketAndFuel.setText(String.format("Open Orders: %s, Total Fuel Order Quantity : %s Lts", ticket, fuel));
    }


}
