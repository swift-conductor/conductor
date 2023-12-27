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
package com.swiftconductor.core.execution.tasks;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

import com.swiftconductor.core.execution.WorkflowExecutor;
import com.swiftconductor.core.execution.evaluators.Evaluator;
import com.swiftconductor.core.execution.evaluators.JavascriptEvaluator;
import com.swiftconductor.core.execution.evaluators.ValueParamEvaluator;
import com.swiftconductor.model.TaskModel;
import com.swiftconductor.model.WorkflowModel;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

public class InlineTest {

    private final WorkflowModel workflow = new WorkflowModel();
    private final WorkflowExecutor executor = mock(WorkflowExecutor.class);

    @Test
    public void testInlineTaskValidationFailures() {
        Inline inline = new Inline(getStringEvaluatorMap());

        Map<String, Object> inputObj = new HashMap<>();
        inputObj.put("value", 1);
        inputObj.put("expression", "");
        inputObj.put("evaluatorType", "value-param");

        TaskModel task = new TaskModel();
        task.getInputData().putAll(inputObj);
        inline.execute(workflow, task, executor);
        assertEquals(TaskModel.Status.FAILED_WITH_TERMINAL_ERROR, task.getStatus());
        assertEquals(
                "Empty 'expression' in Inline task's input parameters. A non-empty String value must be provided.",
                task.getReasonForIncompletion());

        inputObj = new HashMap<>();
        inputObj.put("value", 1);
        inputObj.put("expression", "value");
        inputObj.put("evaluatorType", "");

        task = new TaskModel();
        task.getInputData().putAll(inputObj);
        inline.execute(workflow, task, executor);
        assertEquals(TaskModel.Status.FAILED_WITH_TERMINAL_ERROR, task.getStatus());
        assertEquals(
                "Empty 'evaluatorType' in INLINE task's input parameters. A non-empty String value must be provided.",
                task.getReasonForIncompletion());
    }

    @Test
    public void testInlineValueParamExpression() {
        Inline inline = new Inline(getStringEvaluatorMap());

        Map<String, Object> inputObj = new HashMap<>();
        inputObj.put("value", 101);
        inputObj.put("expression", "value");
        inputObj.put("evaluatorType", "value-param");

        TaskModel task = new TaskModel();
        task.getInputData().putAll(inputObj);

        inline.execute(workflow, task, executor);
        assertEquals(TaskModel.Status.COMPLETED, task.getStatus());
        assertNull(task.getReasonForIncompletion());
        assertEquals(101, task.getOutputData().get("result"));

        inputObj = new HashMap<>();
        inputObj.put("value", "StringValue");
        inputObj.put("expression", "value");
        inputObj.put("evaluatorType", "value-param");

        task = new TaskModel();
        task.getInputData().putAll(inputObj);

        inline.execute(workflow, task, executor);
        assertEquals(TaskModel.Status.COMPLETED, task.getStatus());
        assertNull(task.getReasonForIncompletion());
        assertEquals("StringValue", task.getOutputData().get("result"));
    }

    @SuppressWarnings("unchecked")
    @Test
    public void testInlineJavascriptExpression() {
        Inline inline = new Inline(getStringEvaluatorMap());

        Map<String, Object> inputObj = new HashMap<>();
        inputObj.put("value", 101);
        inputObj.put(
                "expression",
                "function e() { if ($.value == 101){return {\"evalResult\": true}} else { return {\"evalResult\": false}}} e();");
        inputObj.put("evaluatorType", "javascript");

        TaskModel task = new TaskModel();
        task.getInputData().putAll(inputObj);

        inline.execute(workflow, task, executor);
        assertEquals(TaskModel.Status.COMPLETED, task.getStatus());
        assertNull(task.getReasonForIncompletion());
        assertEquals(
                true, ((Map<String, Object>) task.getOutputData().get("result")).get("evalResult"));

        inputObj = new HashMap<>();
        inputObj.put("value", "StringValue");
        inputObj.put(
                "expression",
                "function e() { if ($.value == 'StringValue'){return {\"evalResult\": true}} else { return {\"evalResult\": false}}} e();");
        inputObj.put("evaluatorType", "javascript");

        task = new TaskModel();
        task.getInputData().putAll(inputObj);

        inline.execute(workflow, task, executor);
        assertEquals(TaskModel.Status.COMPLETED, task.getStatus());
        assertNull(task.getReasonForIncompletion());
        assertEquals(
                true, ((Map<String, Object>) task.getOutputData().get("result")).get("evalResult"));
    }

    private Map<String, Evaluator> getStringEvaluatorMap() {
        Map<String, Evaluator> evaluators = new HashMap<>();
        evaluators.put(ValueParamEvaluator.NAME, new ValueParamEvaluator());
        evaluators.put(JavascriptEvaluator.NAME, new JavascriptEvaluator());
        return evaluators;
    }
}
