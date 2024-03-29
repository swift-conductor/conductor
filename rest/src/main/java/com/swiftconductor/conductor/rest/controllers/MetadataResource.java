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
package com.swiftconductor.conductor.rest.controllers;

import java.util.List;
import java.util.Map;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.swiftconductor.conductor.common.metadata.tasks.TaskDef;
import com.swiftconductor.conductor.common.metadata.workflow.WorkflowDef;
import com.swiftconductor.conductor.common.metadata.workflow.WorkflowDefSummary;
import com.swiftconductor.conductor.common.model.BulkResponse;
import com.swiftconductor.conductor.service.MetadataService;

import io.swagger.v3.oas.annotations.Operation;

import static com.swiftconductor.conductor.rest.config.RequestMappingConstants.METADATA;

@RestController
@RequestMapping(value = METADATA)
public class MetadataResource {

    private final MetadataService metadataService;

    public MetadataResource(MetadataService metadataService) {
        this.metadataService = metadataService;
    }

    @PostMapping("/workflowdef")
    @Operation(summary = "Create a new workflow definition")
    public void create(@RequestBody WorkflowDef workflowDef) {
        metadataService.registerWorkflowDef(workflowDef);
    }

    @PostMapping("/workflowdef/validate")
    @Operation(summary = "Validates a new workflow definition")
    public void validate(@RequestBody WorkflowDef workflowDef) {
        metadataService.validateWorkflowDef(workflowDef);
    }

    @PutMapping("/workflowdef")
    @Operation(summary = "Create or update workflow definition")
    public BulkResponse update(@RequestBody List<WorkflowDef> workflowDefs) {
        return metadataService.updateWorkflowDef(workflowDefs);
    }

    @Operation(summary = "Retrieves workflow definition along with blueprint")
    @GetMapping("/workflowdef/{name}")
    public WorkflowDef get(
            @PathVariable("name") String name,
            @RequestParam(value = "version", required = false) Integer version) {
        return metadataService.getWorkflowDef(name, version);
    }

    @Operation(summary = "Retrieves all workflow definition along with blueprint")
    @GetMapping("/workflowdef")
    public List<WorkflowDef> getAll() {
        return metadataService.getWorkflowDefs();
    }

    @Operation(summary = "Returns workflow names and versions only (no definition bodies)")
    @GetMapping("/workflowdef/names-and-versions")
    public Map<String, ? extends Iterable<WorkflowDefSummary>> getWorkflowNamesAndVersions() {
        return metadataService.getWorkflowNamesAndVersions();
    }

    @Operation(summary = "Returns only the latest version of all workflow definitions")
    @GetMapping("/workflowdef/latest-versions")
    public List<WorkflowDef> getAllWorkflowsWithLatestVersions() {
        return metadataService.getWorkflowDefsLatestVersions();
    }

    @DeleteMapping("/workflowdef/{name}/{version}")
    @Operation(
            summary =
                    "Removes workflow definition. It does not remove workflows associated with the definition.")
    public void unregisterWorkflowDef(
            @PathVariable("name") String name, @PathVariable("version") Integer version) {
        metadataService.unregisterWorkflowDef(name, version);
    }

    @PostMapping("/taskdef")
    @Operation(summary = "Create new task definition(s)")
    public void registerTaskDef(@RequestBody List<TaskDef> taskDefs) {
        metadataService.registerTaskDef(taskDefs);
    }

    @PutMapping("/taskdef")
    @Operation(summary = "Update an existing task")
    public void updateTaskDef(@RequestBody TaskDef taskDef) {
        metadataService.updateTaskDef(taskDef);
    }

    @GetMapping(value = "/taskdef")
    @Operation(summary = "Gets all task definition")
    public List<TaskDef> getTaskDefs() {
        return metadataService.getTaskDefs();
    }

    @GetMapping("/taskdef/{tasktype}")
    @Operation(summary = "Gets the task definition")
    public TaskDef getTaskDef(@PathVariable("tasktype") String taskType) {
        return metadataService.getTaskDef(taskType);
    }

    @DeleteMapping("/taskdef/{tasktype}")
    @Operation(summary = "Remove a task definition")
    public void unregisterTaskDef(@PathVariable("tasktype") String taskType) {
        metadataService.unregisterTaskDef(taskType);
    }
}
