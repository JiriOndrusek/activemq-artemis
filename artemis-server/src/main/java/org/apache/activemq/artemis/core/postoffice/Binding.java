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
package org.apache.activemq.artemis.core.postoffice;

import org.apache.activemq.artemis.api.core.SimpleString;
import org.apache.activemq.artemis.core.filter.Filter;
import org.apache.activemq.artemis.core.server.Bindable;
import org.apache.activemq.artemis.core.server.RoutingContext;
import org.apache.activemq.artemis.core.server.ServerMessage;
import org.apache.activemq.artemis.core.server.group.UnproposalListener;

import java.util.List;

public interface Binding extends UnproposalListener {

   SimpleString getAddress();

   Bindable getBindable();

   BindingType getType();

   SimpleString getUniqueName();

   SimpleString getRoutingName();

   SimpleString getClusterName();

   Filter getFilter();

   boolean isHighAcceptPriority(ServerMessage message);

   boolean isExclusive();

   long getID();

   int getDistance();

   void route(ServerMessage message, RoutingContext context) throws Exception;

   void routeWithAck(ServerMessage message, RoutingContext context) throws Exception;

   void close() throws Exception;

   default List<Binding> reorderBindingsByCreditsOwned(List<Binding> bindings)
   {
      return bindings;
   }


   /**
    * This method will create a string representation meant for management operations.
    * <p>
    * This is different from the toString() method that is meant for debugging and will
    * contain information that regular users won't understand well.
    *
    * @return
    */
   String toManagementString();

   boolean isConnected();

   default boolean isLocked(ServerMessage serverMessage) {
      return false;
   }
}
