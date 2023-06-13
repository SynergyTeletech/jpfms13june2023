package com.syneygy.fillnow.Jpfmsdutymanager.fillnowdutymanager1.hardware.synergy;

import android.content.Context;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.syneygy.fillnow.Jpfmsdutymanager.fillnowdutymanager1.R;
import com.syneygy.fillnow.Jpfmsdutymanager.fillnowdutymanager1.interfaces.OnCompartmentOperation;
import com.syneygy.fillnow.Jpfmsdutymanager.fillnowdutymanager1.model.CompartmentInfo;

import java.util.ArrayList;
import java.util.Locale;


public class CompartmentListDialog extends DialogFragment {

    private TextView quantity;
    private Context context;
    private RecyclerView compartmentListRecycler;
    private ArrayList<CompartmentInfo> compartment;
    private OnCompartmentOperation compartmentListener;
    private String[] RS232_COMMANDS = {"#*SP31", "#*SP41"};

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
        compartmentListRecycler = v.findViewById(R.id.alertListRecycler);

        Bundle data = getArguments();
        if (data != null) {
            try {
                if (data.containsKey("compartmentList")) {
                    compartment = data.getParcelableArrayList("compartmentList");
                    quantity.setText("Select Compartment For Refueling");
                    compartmentListRecycler.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false));
                    compartmentListRecycler.setAdapter(new RecyclerView.Adapter<CompartmentHolder>() {
                        @NonNull
                        @Override
                        public CompartmentHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int position) {
                            return new CompartmentHolder(LayoutInflater.from(context).inflate(R.layout.compartment_look, viewGroup, false));
                        }

                        @Override
                        public void onBindViewHolder(@NonNull CompartmentHolder CompartmentHolder, int position) {
                            CompartmentHolder.compartmentName.setText(String.format(Locale.US, "Compartment%d", position + 1));
                            CompartmentHolder.compartmentNumber.setText(String.format("compartment :%s", String.valueOf(position + 1)));
                            CompartmentHolder.card.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    if (compartmentListener != null) {
                                        compartmentListener.OnCompartmentSelected(String.valueOf(position + 1), compartment.get(position).getAtgSerialNo(), compartment.get(position), RS232_COMMANDS[position]);

                                        CompartmentHolder.card.postDelayed(new Runnable() {
                                            @Override
                                            public void run() {
                                                dismiss();
                                            }
                                        }, 200);
                                    }
                                }

                            });

                        }

                        @Override
                        public int getItemCount() {
                            return compartment == null ? 0 : compartment.size();
                        }
                    });

                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return v;
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
        int dialogWindowWidth = (int) (displayWidth * 0.70f);
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
    public void onDestroy() {
        super.onDestroy();
    }

    public class CompartmentHolder extends RecyclerView.ViewHolder {
        private final View card;
        private TextView compartmentName, compartmentNumber;

        public CompartmentHolder(@NonNull View itemView) {
            super(itemView);
            compartmentName = itemView.findViewById(R.id.compartmentName);
            compartmentNumber = itemView.findViewById(R.id.compartmentNumber);
            card = itemView;
        }
    }

    public void setCompartmentListener(OnCompartmentOperation compartmentListener) {
        this.compartmentListener = compartmentListener;

    }

}


