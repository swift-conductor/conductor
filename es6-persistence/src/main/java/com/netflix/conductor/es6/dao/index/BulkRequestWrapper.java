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
package com.netflix.conductor.es6.dao.index;

import java.util.Objects;

import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.update.UpdateRequest;
import org.springframework.lang.NonNull;

/** Thread-safe wrapper for {@link BulkRequest}. */
class BulkRequestWrapper {

    private final BulkRequest bulkRequest;

    BulkRequestWrapper(@NonNull BulkRequest bulkRequest) {
        this.bulkRequest = Objects.requireNonNull(bulkRequest);
    }

    public void add(@NonNull UpdateRequest req) {
        synchronized (bulkRequest) {
            bulkRequest.add(Objects.requireNonNull(req));
        }
    }

    public void add(@NonNull IndexRequest req) {
        synchronized (bulkRequest) {
            bulkRequest.add(Objects.requireNonNull(req));
        }
    }

    BulkRequest get() {
        return bulkRequest;
    }

    int numberOfActions() {
        synchronized (bulkRequest) {
            return bulkRequest.numberOfActions();
        }
    }
}
