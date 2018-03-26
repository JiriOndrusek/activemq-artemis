package org.apache.activemq.artemis.core.server.cluster.impl;

import org.apache.activemq.artemis.api.core.SimpleString;
import org.apache.activemq.artemis.api.core.client.AvailablePermitsCallback;
import org.apache.activemq.artemis.core.client.impl.ClientProducerCredits;
import org.apache.activemq.artemis.core.server.cluster.RemoteQueueBinding;

public class RemoteBindingCallback implements AvailablePermitsCallback {

    final private RemoteQueueBinding remoteBinding;

    private boolean locked = false;
    private ClientProducerCredits clientProducerCredits;


    public RemoteBindingCallback(RemoteQueueBinding remoteBinding) {
        this.remoteBinding = remoteBinding;
        remoteBinding.setAvailablePermitsCallback(this);
    }

    @Override
    public synchronized void callback(SimpleString address, boolean locked) {
        this.locked = locked;
    }

    @Override
    public synchronized boolean isLocked() {
        return locked;
    }

    @Override
    public ClientProducerCredits getClientProducerCredits() {
        return clientProducerCredits;
    }

    @Override
    public void setClientProducerCredits(ClientProducerCredits clientProducerCredits) {
        this.clientProducerCredits = clientProducerCredits;
    }
}
