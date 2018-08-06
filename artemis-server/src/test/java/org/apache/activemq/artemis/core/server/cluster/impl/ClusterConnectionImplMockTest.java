/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements. See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License. You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.apache.activemq.artemis.core.server.cluster.impl;

import org.apache.activemq.artemis.api.core.TransportConfiguration;
import org.apache.activemq.artemis.core.remoting.impl.netty.TransportConstants;
import org.apache.activemq.artemis.tests.util.ActiveMQTestBase;
import org.apache.activemq.artemis.utils.ExecutorFactory;
import org.junit.Assert;
import org.junit.Test;

import java.util.concurrent.Executor;

public class ClusterConnectionImplMockTest extends ActiveMQTestBase {

    /**
     * Verification for fix https://issues.apache.org/jira/browse/ARTEMIS-1946
     */
    @Test
    public void testRemvalOfLocalParameters() throws Exception {
        TransportConfiguration tc = new TransportConfiguration();
        tc.setFactoryClassName("mock");
        tc.getParams().put(TransportConstants.LOCAL_ADDRESS_PROP_NAME, "localAddress");
        tc.getParams().put(TransportConstants.LOCAL_PORT_PROP_NAME, "localPort");

        ClusterConnectionImpl cci = new ClusterConnectionImpl(
                null, //final ClusterManager manager,
                new TransportConfiguration[]{tc}, //final TransportConfiguration[] staticTranspConfigs,
                null, //final TransportConfiguration connector,
                null, //final SimpleString name,
                null, //final SimpleString address,
                0, //final int minLargeMessageSize,
                0L, //final long clientFailureCheckPeriod,
                0L, //final long connectionTTL,
                0L, //final long retryInterval,
                0, //final double retryIntervalMultiplier,
                0L, //final long maxRetryInterval,
                0, //final int initialConnectAttempts,
                0, //final int reconnectAttempts,
                0L, //final long callTimeout,
                0L, //final long callFailoverTimeout,
                false, //final boolean useDuplicateDetection,
                null, //final MessageLoadBalancingType messageLoadBalancingType,
                0, //final int confirmationWindowSize,
                0, //final int producerWindowSize,
                new ExecutorFactory() {
                    @Override
                    public Executor getExecutor() {
                        return command -> {};
                    }
                }, //final ExecutorFactory executorFactory,
                null, //final ActiveMQServer server,
                null, //final PostOffice postOffice,
                null, //final ManagementService managementService,
                null, //final ScheduledExecutorService scheduledExecutor,
                0, //final int maxHops,
                null, //final NodeManager nodeManager,
                null, //final String clusterUser,
                null, //final String clusterPassword,
                true, //final boolean allowDirectConnectionsOnly,
                0, //final long clusterNotificationInterval,
                0 //final int clusterNotificationAttempts)
        );

        Assert.assertEquals(1, cci.allowableConnections.size());
        Assert.assertFalse("Local address can not be part of allowable connection.", cci.allowableConnections.iterator().next().getParams().containsKey(TransportConstants.LOCAL_ADDRESS_PROP_NAME));
        Assert.assertFalse("Local port can not be part of allowable connection.", cci.allowableConnections.iterator().next().getParams().containsKey(TransportConstants.LOCAL_PORT_PROP_NAME));

    }
}
