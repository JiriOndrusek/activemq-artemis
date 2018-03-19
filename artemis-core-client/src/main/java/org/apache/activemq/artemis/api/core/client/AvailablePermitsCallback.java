package org.apache.activemq.artemis.api.core.client;

import org.apache.activemq.artemis.api.core.SimpleString;

public interface AvailablePermitsCallback {

    void callback(SimpleString address, boolean locked);

    boolean isLocked();
}
