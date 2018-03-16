package org.apache.activemq.artemis.core.server.cluster.impl;

import org.apache.activemq.artemis.api.core.SimpleString;
import org.apache.activemq.artemis.api.core.client.AvailablePermitsCallback;
import org.apache.activemq.artemis.core.server.cluster.RemoteQueueBinding;

public class RemoteBindingCallback implements AvailablePermitsCallback {

    final private RemoteQueueBinding remoteBinding;


    public RemoteBindingCallback(RemoteQueueBinding remoteBinding) {
        this.remoteBinding = remoteBinding;
    }

    @Override
    public void callback(SimpleString address, int availablePermits) {
        remoteBinding.setAvailablePermits(availablePermits);
    }
}
