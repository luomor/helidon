/*
 * Copyright (c) 2017, 2018 Oracle and/or its affiliates. All rights reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package io.helidon.config;

import java.time.Instant;
import java.util.concurrent.ForkJoinPool;

import io.helidon.common.reactive.Flow;
import io.helidon.common.reactive.SubmissionPublisher;
import io.helidon.config.spi.PollingStrategy;

/**
 * Testing implementation of {@link PollingStrategy}.
 */
public class TestingPollingStrategy implements PollingStrategy {

    private SubmissionPublisher<PollingEvent> ticksPublisher =
            new SubmissionPublisher<>(ForkJoinPool.commonPool(), Flow.defaultBufferSize());

    @Override
    public SubmissionPublisher<PollingEvent> ticks() {
        return ticksPublisher;
    }

    public Instant submitEvent() {
        PollingEvent pollingEvent = PollingEvent.now();
        ticksPublisher.submit(pollingEvent);
        return pollingEvent.getTimestamp();
    }

}
