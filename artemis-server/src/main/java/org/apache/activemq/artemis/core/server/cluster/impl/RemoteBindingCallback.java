package org.apache.activemq.artemis.core.server.cluster.impl;

import org.apache.activemq.artemis.api.core.SimpleString;
import org.apache.activemq.artemis.api.core.client.AvailablePermitsCallback;
import org.apache.activemq.artemis.core.server.cluster.RemoteQueueBinding;

public class RemoteBindingCallback implements AvailablePermitsCallback {

    final private RemoteQueueBinding remoteBinding;

    private boolean locked = false;


    public RemoteBindingCallback(RemoteQueueBinding remoteBinding) {
        this.remoteBinding = remoteBinding;
    }

    @Override
    public synchronized void callback(SimpleString address, boolean locked) {
        this.locked = locked;
    }

    @Override
    public synchronized boolean isLocked() {
        return locked;
    }
}
