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
package edu.upenn.mkse212.hw4.client;

import java.util.Set;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.JavaScriptObject;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.TextBox;


/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class FriendViewer implements EntryPoint {
	
	private final GetFriendsForAsync friendService = 
			GetFriendsFor.Util.getInstance();
	private JavaScriptObject graph;
	
	private TextBox queryTbx;
	private Button submitBtn;
	
	public void onModuleLoad() {
		RootPanel rootPanel = RootPanel.get("content");
		
		// Form label
		Label queryLbl = new Label("Query for user by ID: ");
		rootPanel.add(queryLbl);
		
		// TextBox for user id
		queryTbx = new TextBox();
		rootPanel.add(queryTbx);
		
		// Submit button
		submitBtn = new Button();
		rootPanel.add(submitBtn);
		submitBtn.setText("Submit");
		submitBtn.addClickHandler(new ClickHandler(){
			public void onClick(ClickEvent event) {
				// Fetch list of friends and display them
				drawNodeAndNeighbors(queryTbx.getText());
			}
		});
	}
	
	// Method called when either a node or the submit button is clicked
	public void drawNodeAndNeighbors(final String s) {
		final FriendViewer view = this;
		friendService.getFriendsList(s,
				new AsyncCallback<Set<String>>() {
			public void onFailure(Throwable caught) {
				Window.alert("Unable to talk to server");
			}
			public void onSuccess(Set<String> result) {
				// Visualize results
				if (graph == null) {
					String tree = treeFromUserAndFriends(s, result);
					graph = FriendVisualization.createGraph(tree, view);
				}
				else {
					String tree = treeFromUserAndFriends(s, result);
					FriendVisualization.addToGraph(graph, tree);
				}
			}
		});
	}
	
	// Helper function to generate a JSON string
	public static String treeFromUserAndFriends(String user, Set<String> friends) {
		String tree = "{\"id\": "+user+", \"name\": "+user+", \"children\": [";
		boolean first = true;
		for (String friend : friends) {
			if (!first) { tree += ","; }
			else { first = false; }
			tree += "{\"id\": "+friend+", \"name\": "+friend+", \"children\": []}";
		}
		tree += "]}";
		return tree;
	}
}
