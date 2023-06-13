package com.syneygy.fillnow.Jpfmsdutymanager.fillnowdutymanager1.fragments;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.syneygy.fillnow.Jpfmsdutymanager.fillnowdutymanager1.R;
import com.syneygy.fillnow.Jpfmsdutymanager.fillnowdutymanager1.activity.ReceiveFuelActivity;
import com.syneygy.fillnow.Jpfmsdutymanager.fillnowdutymanager1.activity.FreshDispenseActivity;
import com.syneygy.fillnow.Jpfmsdutymanager.fillnowdutymanager1.activity.LoginActivity;
import com.syneygy.fillnow.Jpfmsdutymanager.fillnowdutymanager1.activity.NavigationDrawerActivity;
import com.syneygy.fillnow.Jpfmsdutymanager.fillnowdutymanager1.activity.SettingsActivity;
import com.syneygy.fillnow.Jpfmsdutymanager.printer.DeviceList;

public class FragmentOperationMenu extends Fragment {

    private Context context;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = context;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_operation_menu_lay, container, false);
        view.findViewById(R.id.cardDispense).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (NavigationDrawerActivity.btsocket == null) {
                    printerConnectionDialog(1);
                } else {
                    FragmentManager fragmentManager = getChildFragmentManager();
                    fragmentManager.beginTransaction().add(R.id.frameOperationMenu, new FragmentAcceptedOrders(), FragmentAcceptedOrders.class.getSimpleName())
                            .addToBackStack(FragmentAcceptedOrders.class.getSimpleName()).commit();
                }

            }
        });

        view.findViewById(R.id.cardReceive).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (NavigationDrawerActivity.btsocket == null) {
                    printerConnectionDialog(2);
                } else {
                    startActivity(new Intent(context, ReceiveFuelActivity.class));
                }

            }
        });

        view.findViewById(R.id.cv_fresh_dispense).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (NavigationDrawerActivity.btsocket == null) {
                    printerConnectionDialog(3);
                } else {
                    startActivity(new Intent(context, FreshDispenseActivity.class));
                }

            }
        });

        view.findViewById(R.id.cardRePrint).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (NavigationDrawerActivity.btsocket == null) {
                    printerConnectionDialog(4);
                } else {
                    FragmentManager fragmentManager = getChildFragmentManager();
                    fragmentManager.beginTransaction()
                            .add(R.id.frameOperationMenu, new FragmentCompletedOrders(), FragmentCompletedOrders.class.getSimpleName())
                            .addToBackStack(FragmentCompletedOrders.class.getSimpleName())
                            .commit();
                }
            }
        });

        view.findViewById(R.id.cardViewProfile).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fragmentManager = getChildFragmentManager();
                fragmentManager.beginTransaction()
                        .add(R.id.frameOperationMenu, new FragmentViewProfile(), FragmentViewProfile.class.getSimpleName())
                        .addToBackStack(FragmentViewProfile.class.getSimpleName())
                        .commit();
            }
        });
  view.findViewById(R.id.cardLock).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fragmentManager = getChildFragmentManager();
                fragmentManager.beginTransaction()
                        .add(R.id.frameOperationMenu, new LockFragment(), LockFragment.class.getSimpleName())
                        .addToBackStack(LockFragment.class.getSimpleName())
                        .commit();
            }
        });

        view.findViewById(R.id.cardReceivePayment).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fragmentManager = getChildFragmentManager();
                fragmentManager.beginTransaction()
                        .add(R.id.frameOperationMenu, new FragmentReceivedPayment(), FragmentReceivedPayment.class.getSimpleName())
                        .addToBackStack(FragmentReceivedPayment.class.getSimpleName())
                        .commit();
            }
        });

        view.findViewById(R.id.cardSetting).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(context, SettingsActivity.class));

            }
        });

        view.findViewById(R.id.card_logout).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(context, LoginActivity.class));
                ((NavigationDrawerActivity) context).finish();

            }
        });

        view.findViewById(R.id.card_connect_printer).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                connectBluetooth();
            }
        });


        return view;
    }

    public void printerConnectionDialog(int condition){
        new AlertDialog.Builder(context)
                .setTitle("Confirmation?")
                .setMessage("Printer is not connected. Do you want to continue without printing?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        if (condition==1){
                            FragmentManager fragmentManager = getChildFragmentManager();
                            fragmentManager.beginTransaction().add(R.id.frameOperationMenu, new FragmentAcceptedOrders(), FragmentAcceptedOrders.class.getSimpleName())
                                    .addToBackStack(FragmentAcceptedOrders.class.getSimpleName()).commit();
                        }else if (condition==2){
                            startActivity(new Intent(context, ReceiveFuelActivity.class));
                        }else if (condition==3){
                            startActivity(new Intent(context, FreshDispenseActivity.class));
                        }else if (condition==4){
                            FragmentManager fragmentManager = getChildFragmentManager();
                            fragmentManager.beginTransaction()
                                    .add(R.id.frameOperationMenu, new FragmentCompletedOrders(), FragmentCompletedOrders.class.getSimpleName())
                                    .addToBackStack(FragmentCompletedOrders.class.getSimpleName())
                                    .commit();
                        }
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })
                .show();
    }

    @Override
    public void onResume() {
        super.onResume();
        if (context instanceof NavigationDrawerActivity) {
            ((NavigationDrawerActivity) context).setNavigationDrawerItemTitle("Operation Menu");
        }
    }

    private void connectBluetooth() {
        if (NavigationDrawerActivity.btsocket == null) {
            Intent BTIntent = new Intent(context, DeviceList.class);
//            Intent BTIntent = new Intent(getApplicationContext(), DeviceList2.class);
            this.startActivityForResult(BTIntent, DeviceList.REQUEST_CONNECT_BT);
        } else {
            Toast.makeText(context, "Printer Already Connected", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        try {
            NavigationDrawerActivity.btsocket = DeviceList.getSocket();
            if (NavigationDrawerActivity.btsocket != null) {
                Toast.makeText(context, "Printer Already Connected", Toast.LENGTH_SHORT).show();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
