package org.apache.activemq.artemis.api.core.client;

import org.apache.activemq.artemis.api.core.SimpleString;
import org.apache.activemq.artemis.core.client.impl.ClientProducerCredits;

public interface AvailablePermitsCallback {

    void callback(SimpleString address, boolean locked);

    boolean isLocked();

    void setClientProducerCredits(ClientProducerCredits clientProducerCredits);

    ClientProducerCredits getClientProducerCredits();
}
