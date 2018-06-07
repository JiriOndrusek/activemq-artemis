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
package org.apache.activemq.artemis.tests.integration.persistence;

import org.apache.activemq.artemis.api.core.RoutingType;
import org.apache.activemq.artemis.api.core.SimpleString;
import org.apache.activemq.artemis.core.config.StoreConfiguration;
import org.apache.activemq.artemis.core.persistence.StorageManager;
import org.apache.activemq.artemis.core.persistence.config.PersistedAddressSetting;
import org.apache.activemq.artemis.core.postoffice.QueueBinding;
import org.apache.activemq.artemis.core.server.ActiveMQServer;
import org.apache.activemq.artemis.core.settings.impl.AddressFullMessagePolicy;
import org.apache.activemq.artemis.core.settings.impl.AddressSettings;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runners.Parameterized;

import java.util.Arrays;
import java.util.Collection;

public class LVQWithJournalTest extends StorageManagerTestBase {

   // Constants -----------------------------------------------------

   private static final String ADDRESS = "ADDRESS";

   // Attributes ----------------------------------------------------

   // Static --------------------------------------------------------

   @Parameterized.Parameters(name = "storeType={0}")
   public static Collection<Object[]> data() {
      Object[][] params = new Object[][]{{StoreConfiguration.StoreType.FILE}};
      return Arrays.asList(params);
   }

   // Constructors --------------------------------------------------

   public LVQWithJournalTest(StoreConfiguration.StoreType storeType) {
      super(storeType);
   }

   // Public --------------------------------------------------------



   @Test
   public void testLastValuePropagation() throws Exception {
      //set lastValue = true into address settings accessible by journal
      createStorage();
      AddressSettings setting;
      setting = new AddressSettings().setAddressFullMessagePolicy(AddressFullMessagePolicy.BLOCK).setDeadLetterAddress(new SimpleString("some-test")).setDefaultLastValueQueue(true);
      addAddress(journal, "#", setting);
      journal.stop();


      ActiveMQServer server = createServer(true);

      server.start();

      SimpleString address = new SimpleString("test.address");
      SimpleString queue = new SimpleString("test.queue");

      // todo create queu with the same way as from EAP
//      server.createQueue(address, RoutingType.MULTICAST, queue, null, true, false);
//      server.stop();
//      server.start();

      QueueBinding queueBinding1 = (QueueBinding)server.getPostOffice().getBinding(queue);
      Assert.assertTrue(queueBinding1.getQueue().isLastValue());
   }

   // Package protected ---------------------------------------------

   // Protected -----------------------------------------------------

   // Private -------------------------------------------------------

   private void addAddress(StorageManager journal1, String address, AddressSettings setting) throws Exception {
      SimpleString str = new SimpleString(address);
      PersistedAddressSetting persistedSetting = new PersistedAddressSetting(str, setting);
      journal1.storeAddressSetting(persistedSetting);
   }

   // Inner classes -------------------------------------------------

}
