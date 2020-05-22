/*
 * Copyright Amazon.com, Inc. or its affiliates. All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License").
 * You may not use this file except in compliance with the License.
 * A copy of the License is located at
 *
 *  http://aws.amazon.com/apache2.0
 *
 * or in the "license" file accompanying this file. This file is distributed
 * on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either
 * express or implied. See the License for the specific language governing
 * permissions and limitations under the License.
 */

plugins {
    java
    id("com.github.johnrengelman.shadow") version "5.2.0"
}

java {
    sourceCompatibility = JavaVersion.VERSION_1_7
    targetCompatibility = JavaVersion.VERSION_1_7
}

dependencies {
    implementation("io.opentelemetry:opentelemetry-sdk:0.4.1")
}

tasks {
    shadowJar {
        archiveClassifier.set("")

        mergeServiceFiles()

        exclude("**/module-info.class")

        // Prevents conflict with other SLF4J instances. Important for premain.
        relocate("org.slf4j", "io.opentelemetry.auto.slf4j")
        // rewrite dependencies calling Logger.getLogger
        relocate("java.util.logging.Logger", "io.opentelemetry.auto.bootstrap.PatchLogger")

        // relocate OpenTelemetry API
        relocate("io.opentelemetry.OpenTelemetry", "io.opentelemetry.auto.shaded.io.opentelemetry.OpenTelemetry")
        relocate("io.opentelemetry.common", "io.opentelemetry.auto.shaded.io.opentelemetry.common")
        relocate("io.opentelemetry.context", "io.opentelemetry.auto.shaded.io.opentelemetry.context")
        relocate("io.opentelemetry.correlationcontext", "io.opentelemetry.auto.shaded.io.opentelemetry.correlationcontext")
        relocate("io.opentelemetry.internal", "io.opentelemetry.auto.shaded.io.opentelemetry.internal")
        relocate("io.opentelemetry.metrics", "io.opentelemetry.auto.shaded.io.opentelemetry.metrics")
        relocate("io.opentelemetry.trace", "io.opentelemetry.auto.shaded.io.opentelemetry.trace")
        relocate("io.opentelemetry.contrib.auto.annotations", "io.opentelemetry.auto.shaded.io.opentelemetry.contrib.auto.annotations")

        // relocate OpenTelemetry API dependency
        relocate("io.grpc", "io.opentelemetry.auto.shaded.io.grpc")
    }
}