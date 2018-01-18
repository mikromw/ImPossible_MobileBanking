package com.androidtutorialshub.loginregister;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * A simple {@link Fragment} subclass.
 */
public class TransferFragment extends Fragment implements View.OnClickListener{
    private User user;
    private TextView paketpulsa, transferdana;
    public TransferFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View _view = inflater.inflate(R.layout.fragment_transfer, container, false);
        initViews(_view);
        initListeners();
        return _view;
    }
    /**
     * This method is to initialize views
     */
    private void initViews(View _view) {
        paketpulsa=(TextView)_view.findViewById(R.id.paketpulsa);
        transferdana=(TextView)_view.findViewById(R.id.transferdana);
    }
    /**
     * This method is to initialize listeners
     */
    private void initListeners() {
        paketpulsa.setOnClickListener(this);
        transferdana.setOnClickListener(this);
    }
    /**
     * This implemented method is to listen the click on view
     *
     */
    public void onClick(View v) {
        if(v.getId()==R.id.paketpulsa){
            Intent pulsa=new Intent(v.getContext(),PulsaActivity.class);
            v.getContext().startActivity(pulsa);
        }
        else if(v.getId()==R.id.transferdana){
            Intent transfer=new Intent(v.getContext(),TransferActivity.class);
            v.getContext().startActivity(transfer);
        }
    }

}
