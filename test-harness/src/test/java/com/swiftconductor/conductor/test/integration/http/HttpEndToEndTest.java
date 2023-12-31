/*
 * Copyright 2023 Swift Software Group, Inc.
 * (Code and content before December 13, 2023, Copyright Netflix, Inc.)
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with
 * the License. You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on
 * an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations under the License.
 */
package com.swiftconductor.conductor.test.integration.http;

import org.junit.Before;

import com.swiftconductor.conductor.client.http.EventClient;
import com.swiftconductor.conductor.client.http.MetadataClient;
import com.swiftconductor.conductor.client.http.TaskClient;
import com.swiftconductor.conductor.client.http.WorkflowClient;

public class HttpEndToEndTest extends AbstractHttpEndToEndTest {

    @Before
    public void init() {
        apiRoot = String.format("http://localhost:%d/api/", port);

        taskClient = new TaskClient();
        taskClient.setRootURI(apiRoot);

        workflowClient = new WorkflowClient();
        workflowClient.setRootURI(apiRoot);

        metadataClient = new MetadataClient();
        metadataClient.setRootURI(apiRoot);

        eventClient = new EventClient();
        eventClient.setRootURI(apiRoot);
    }
}
