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
package com.swiftconductor.client.grpc;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.util.ReflectionTestUtils;

import com.swiftconductor.common.run.SearchResult;
import com.swiftconductor.common.run.Workflow;
import com.swiftconductor.common.run.WorkflowSummary;
import com.swiftconductor.grpc.ProtoMapper;
import com.swiftconductor.grpc.SearchPb;
import com.swiftconductor.grpc.WorkflowServiceGrpc;
import com.swiftconductor.grpc.WorkflowServicePb;
import com.swiftconductor.proto.WorkflowPb;
import com.swiftconductor.proto.WorkflowSummaryPb;
import io.grpc.ManagedChannelBuilder;

import static junit.framework.TestCase.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
public class WorkflowClientTest {

    @Mock ProtoMapper mockedProtoMapper;

    @Mock WorkflowServiceGrpc.WorkflowServiceBlockingStub mockedStub;

    WorkflowClient workflowClient;

    @Before
    public void init() {
        workflowClient = new WorkflowClient("test", 0);
        ReflectionTestUtils.setField(workflowClient, "stub", mockedStub);
        ReflectionTestUtils.setField(workflowClient, "protoMapper", mockedProtoMapper);
    }

    @Test
    public void testSearch() {
        WorkflowSummary workflow = mock(WorkflowSummary.class);
        WorkflowSummaryPb.WorkflowSummary workflowPB =
                mock(WorkflowSummaryPb.WorkflowSummary.class);
        when(mockedProtoMapper.fromProto(workflowPB)).thenReturn(workflow);
        WorkflowServicePb.WorkflowSummarySearchResult result =
                WorkflowServicePb.WorkflowSummarySearchResult.newBuilder()
                        .addResults(workflowPB)
                        .setTotalHits(1)
                        .build();
        SearchPb.Request searchRequest =
                SearchPb.Request.newBuilder().setQuery("test query").build();
        when(mockedStub.search(searchRequest)).thenReturn(result);
        SearchResult<WorkflowSummary> searchResult = workflowClient.search("test query");
        assertEquals(1, searchResult.getTotalHits());
        assertEquals(workflow, searchResult.getResults().get(0));
    }

    @Test
    public void testSearchV2() {
        Workflow workflow = mock(Workflow.class);
        WorkflowPb.Workflow workflowPB = mock(WorkflowPb.Workflow.class);
        when(mockedProtoMapper.fromProto(workflowPB)).thenReturn(workflow);
        WorkflowServicePb.WorkflowSearchResult result =
                WorkflowServicePb.WorkflowSearchResult.newBuilder()
                        .addResults(workflowPB)
                        .setTotalHits(1)
                        .build();
        SearchPb.Request searchRequest =
                SearchPb.Request.newBuilder().setQuery("test query").build();
        when(mockedStub.searchV2(searchRequest)).thenReturn(result);
        SearchResult<Workflow> searchResult = workflowClient.searchV2("test query");
        assertEquals(1, searchResult.getTotalHits());
        assertEquals(workflow, searchResult.getResults().get(0));
    }

    @Test
    public void testSearchWithParams() {
        WorkflowSummary workflow = mock(WorkflowSummary.class);
        WorkflowSummaryPb.WorkflowSummary workflowPB =
                mock(WorkflowSummaryPb.WorkflowSummary.class);
        when(mockedProtoMapper.fromProto(workflowPB)).thenReturn(workflow);
        WorkflowServicePb.WorkflowSummarySearchResult result =
                WorkflowServicePb.WorkflowSummarySearchResult.newBuilder()
                        .addResults(workflowPB)
                        .setTotalHits(1)
                        .build();
        SearchPb.Request searchRequest =
                SearchPb.Request.newBuilder()
                        .setStart(1)
                        .setSize(5)
                        .setSort("*")
                        .setFreeText("*")
                        .setQuery("test query")
                        .build();
        when(mockedStub.search(searchRequest)).thenReturn(result);
        SearchResult<WorkflowSummary> searchResult =
                workflowClient.search(1, 5, "*", "*", "test query");
        assertEquals(1, searchResult.getTotalHits());
        assertEquals(workflow, searchResult.getResults().get(0));
    }

    @Test
    public void testSearchV2WithParams() {
        Workflow workflow = mock(Workflow.class);
        WorkflowPb.Workflow workflowPB = mock(WorkflowPb.Workflow.class);
        when(mockedProtoMapper.fromProto(workflowPB)).thenReturn(workflow);
        WorkflowServicePb.WorkflowSearchResult result =
                WorkflowServicePb.WorkflowSearchResult.newBuilder()
                        .addResults(workflowPB)
                        .setTotalHits(1)
                        .build();
        SearchPb.Request searchRequest =
                SearchPb.Request.newBuilder()
                        .setStart(1)
                        .setSize(5)
                        .setSort("*")
                        .setFreeText("*")
                        .setQuery("test query")
                        .build();
        when(mockedStub.searchV2(searchRequest)).thenReturn(result);
        SearchResult<Workflow> searchResult = workflowClient.searchV2(1, 5, "*", "*", "test query");
        assertEquals(1, searchResult.getTotalHits());
        assertEquals(workflow, searchResult.getResults().get(0));
    }

    @Test
    public void testSearchV2WithParamsWithManagedChannel() {
        WorkflowClient workflowClient = createClientWithManagedChannel();
        Workflow workflow = mock(Workflow.class);
        WorkflowPb.Workflow workflowPB = mock(WorkflowPb.Workflow.class);
        when(mockedProtoMapper.fromProto(workflowPB)).thenReturn(workflow);
        WorkflowServicePb.WorkflowSearchResult result =
                WorkflowServicePb.WorkflowSearchResult.newBuilder()
                        .addResults(workflowPB)
                        .setTotalHits(1)
                        .build();
        SearchPb.Request searchRequest =
                SearchPb.Request.newBuilder()
                        .setStart(1)
                        .setSize(5)
                        .setSort("*")
                        .setFreeText("*")
                        .setQuery("test query")
                        .build();
        when(mockedStub.searchV2(searchRequest)).thenReturn(result);
        SearchResult<Workflow> searchResult = workflowClient.searchV2(1, 5, "*", "*", "test query");
        assertEquals(1, searchResult.getTotalHits());
        assertEquals(workflow, searchResult.getResults().get(0));
    }

    public WorkflowClient createClientWithManagedChannel() {
        WorkflowClient workflowClient =
                new WorkflowClient(ManagedChannelBuilder.forAddress("test", 0));
        ReflectionTestUtils.setField(workflowClient, "stub", mockedStub);
        ReflectionTestUtils.setField(workflowClient, "protoMapper", mockedProtoMapper);
        return workflowClient;
    }
}
