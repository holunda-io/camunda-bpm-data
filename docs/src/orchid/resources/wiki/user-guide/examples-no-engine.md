---
title: Example no engine
---

## Use case

In seldom cases you may want to use the `camunda-bpm-data` library without Camunda BPM Engine. The most frequent
case for this is if you provide a third-party library that is used with or without the engine. In particular, there is no way to use functionality relying on the Camunda engine without it, but there is some functionality working on simple `Map<String, Object>` or `VariableMap` (from a separate small library `org.camunda.commons:camunda-commons-typed-values`)
which is still usable __WITHOUT__ having the entire Camunda BPM Engine on the class path.

## Limitations

Due to limitations provided by the JVM, usage of `CamundaBpmData` conveneince methods is __NOT POSSIBLE__ if the parts of the Java Camunda API is not on the classpath (`RuntimeService`, `TaskService`, `LockedExternalTask`, ...).

## Default solution

The default solution for this problem would be to put the `org.camunda.bpm:camunda-engine` Camunda BPM Engine JAR on the classpath, but don't initialize the Camunda BPM Engine.

## Alterantve solution

In order to make the JAR footprint lighter, we created a special artifact, which provides the Camunda BPM API only (API classes only but no implementation). This artifact includes __ORIGINAL__ Camunda BPM classes of the API.

In the same time, we started a discussion with Camunda Team to provide a dedicated API JAR of the engine. We will produce the Camunda BPM API for the upcoming releases. Instead of using the original engine JAR, you might want to put the following artifact on your classpath:

``` xml

<dependency>
      <groupId>io.holunda.camunda-api</groupId>
      <artifactId>camunda-bpm-engine-api</artifactId>
      <version>${camunda.version}</version>
</dependency>
```

For more information about the Camunda BPM API, please check its GitHub project page: [https://github.com/holunda-io/camunda-bpm-api/](https://github.com/holunda-io/camunda-bpm-api/)