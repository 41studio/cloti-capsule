package com.fourtyonestudio.cloticapsuleloopback;

import android.app.Application;
import android.content.Context;
import android.util.Log;

import com.strongloop.android.loopback.RestAdapter;
import com.strongloop.android.remoting.adapters.RestContractItem;

public class Apps extends Application {
    RestAdapter adapter;
    public static Context context=null;
    @Override
	public void onCreate() {
		// TODO Auto-generated method stub
		super.onCreate();
		Log.d("LoopAndroidApplication", getApplicationContext()+"");
        context = getApplicationContext();
	}
	public RestAdapter getLoopBackAdapter() {
        if (adapter == null) {
            // Instantiate the shared RestAdapter. In most circumstances,
            // you'll do this only once; putting that reference in a singleton
            // is recommended for the sake of simplicity.
            // However, some applications will need to talk to more than one
            // server - create as many Adapters as you need.
            adapter = new RestAdapter(
            		context, "http://cloticapsule.herokuapp.com/api/");
            Log.d("LoopAndroidApplication"," connected"+adapter.isConnected());

            adapter.getContract().addItem(
                    new RestContractItem("Users/login", "POST"),
                    "Users/login");

            adapter.getContract().addItem(
                    new RestContractItem("Users", "POST"),
                    "Users");
        }
        return adapter;
    }
}
