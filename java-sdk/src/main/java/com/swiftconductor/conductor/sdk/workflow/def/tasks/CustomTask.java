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
package com.swiftconductor.conductor.sdk.workflow.def.tasks;

import com.swiftconductor.conductor.common.metadata.tasks.TaskDef;
import com.swiftconductor.conductor.common.metadata.tasks.TaskType;
import com.swiftconductor.conductor.common.metadata.workflow.WorkflowTask;

/** Workflow task executed by a worker */
public class CustomTask extends Task<CustomTask> {

    private TaskDef taskDef;

    public CustomTask(String taskDefName, String taskReferenceName) {
        super(taskReferenceName, TaskType.CUSTOM);
        super.name(taskDefName);
    }

    CustomTask(WorkflowTask workflowTask) {
        super(workflowTask);
        this.taskDef = workflowTask.getTaskDefinition();
    }

    public TaskDef getTaskDef() {
        return taskDef;
    }

    public CustomTask setTaskDef(TaskDef taskDef) {
        this.taskDef = taskDef;
        return this;
    }

    @Override
    protected void updateWorkflowTask(WorkflowTask workflowTask) {
        workflowTask.setTaskDefinition(taskDef);
    }
}
