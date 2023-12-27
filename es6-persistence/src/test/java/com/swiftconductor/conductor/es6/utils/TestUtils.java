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
package com.swiftconductor.es6.utils;

import java.nio.charset.StandardCharsets;

import org.apache.commons.io.FileUtils;
import org.springframework.util.ResourceUtils;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.swiftconductor.common.run.TaskSummary;
import com.swiftconductor.common.run.WorkflowSummary;
import com.swiftconductor.core.utils.IDGenerator;

public class TestUtils {

    private static final String WORKFLOW_INSTANCE_ID_PLACEHOLDER = "WORKFLOW_INSTANCE_ID";

    public static WorkflowSummary loadWorkflowSnapshot(
            ObjectMapper objectMapper, String resourceFileName) {
        try {
            String content = loadJsonResource(resourceFileName);
            String workflowId = new IDGenerator().generate();
            content = content.replace(WORKFLOW_INSTANCE_ID_PLACEHOLDER, workflowId);

            return objectMapper.readValue(content, WorkflowSummary.class);
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage(), e);
        }
    }

    public static TaskSummary loadTaskSnapshot(ObjectMapper objectMapper, String resourceFileName) {
        try {
            String content = loadJsonResource(resourceFileName);
            String workflowId = new IDGenerator().generate();
            content = content.replace(WORKFLOW_INSTANCE_ID_PLACEHOLDER, workflowId);

            return objectMapper.readValue(content, TaskSummary.class);
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage(), e);
        }
    }

    public static TaskSummary loadTaskSnapshot(
            ObjectMapper objectMapper, String resourceFileName, String workflowId) {
        try {
            String content = loadJsonResource(resourceFileName);
            content = content.replace(WORKFLOW_INSTANCE_ID_PLACEHOLDER, workflowId);

            return objectMapper.readValue(content, TaskSummary.class);
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage(), e);
        }
    }

    public static String loadJsonResource(String resourceFileName) {
        try {
            return FileUtils.readFileToString(
                    ResourceUtils.getFile("classpath:" + resourceFileName + ".json"),
                    StandardCharsets.UTF_8);
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage(), e);
        }
    }
}
