/*******************************************************************************
 * Copyright 2011 Google Inc. All Rights Reserved.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *******************************************************************************/
package edu.upenn.mkse212.hw4.server;

import java.util.Set;

import edu.upenn.mkse212.IKeyValueStorage;
import edu.upenn.mkse212.KeyValueStoreFactory;
import edu.upenn.mkse212.hw4.client.GetFriendsFor;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;

public class GetFriendsForImpl extends RemoteServiceServlet implements GetFriendsFor {

	@Override
	public Set<String> getFriendsList(String userIdAsString) {
		// Open the kvs produced by ParseInputGraph
		IKeyValueStorage storageSystem = KeyValueStoreFactory.getKeyValueStore(KeyValueStoreFactory.STORETYPE.BERKELEY, 
			    "socialGraph", "/home/mkse212/bdb/", "user", "authKey", false);
		Set<String> friendsList = storageSystem.get(userIdAsString);
		storageSystem.close();
		return friendsList;
	}

}
