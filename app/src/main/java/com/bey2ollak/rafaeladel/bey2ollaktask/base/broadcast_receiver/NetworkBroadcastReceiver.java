package com.bey2ollak.rafaeladel.bey2ollaktask.base.broadcast_receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

/**
 * Created by Rafael Adel on 9/30/2017.
 */

public class NetworkBroadcastReceiver extends BroadcastReceiver {
    private Runnable actionToTake;



    @Override
    public void onReceive(Context context, Intent intent) {
        if(actionToTake != null) {
            actionToTake.run();
        }
    }

    public Runnable getActionToTake() {
        return actionToTake;
    }

    public void setActionToTake(Runnable actionToTake) {
        this.actionToTake = actionToTake;
    }
}
