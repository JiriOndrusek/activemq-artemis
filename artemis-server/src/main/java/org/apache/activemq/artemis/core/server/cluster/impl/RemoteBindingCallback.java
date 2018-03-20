package org.apache.activemq.artemis.core.server.cluster.impl;

import org.apache.activemq.artemis.api.core.SimpleString;
import org.apache.activemq.artemis.api.core.client.AvailablePermitsCallback;
import org.apache.activemq.artemis.core.server.cluster.RemoteQueueBinding;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

public class RemoteBindingCallback implements AvailablePermitsCallback {

//    private static Map<RemoteQueueBinding, Integer> counts = new LinkedHashMap<>();


    final private RemoteQueueBinding remoteBinding;

    private int ownedCredits = 0;

    private int i = 0;

    private boolean locked = false;


    public RemoteBindingCallback(RemoteQueueBinding remoteBinding) {
        this.remoteBinding = remoteBinding;
    }

    @Override
    public void callback(SimpleString address, int availablePermits) {
        synchronized (this) {
//            System.out.println(address+"########################################## " + this.ownedCredits + " and add " + availablePermits);

            if(this.ownedCredits + availablePermits >= 0) {
                this.ownedCredits += availablePermits;
            }
//            this.ownedCredits += availablePermits;
//            System.out.println(address+"########################################## result: " + this.ownedCredits);
//            int count = counts.get(remoteBinding);
//            counts.put(remoteBinding, count+1);
//
//            printStatistics();

            if(availablePermits > 0) {
                i++;
            }
        }


    }

//    public void printStatistics() {
//        System.out.println("---------------------- statisctic -------------------------");
//        for(Map.Entry<RemoteQueueBinding, Integer> e : counts.entrySet()) {
//            System.out.println(e.getKey().getAddress()+":"+e.getValue());
//        }
//    }

    public int getOwnedCredits() {
        return ownedCredits;
    }

    @Override
    public boolean isLocked() {
        return locked;
    }

    @Override
    public void setLocked(boolean locked) {
        this.locked = locked;
    }
}
