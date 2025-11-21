<!--
This file is written to help AI coding agents become productive quickly in this repository.
Keep it concise and focused on discoverable, actionable facts (no aspirational guidance).
-->
# Copilot / AI agent instructions for this repository

Overview
- **Project type:** Spring Boot (reactive WebFlux) demo using R2DBC. Main entry: `src/main/java/com/example/javaspring4/Javaspring4Application.java`.
- **Build system:** Gradle wrapper (`./gradlew`). See `build.gradle` for dependencies (WebFlux, R2DBC, Graal native plugin).
- **Runtime DB:** PostgreSQL via `docker-compose.yml` (service `db`) or the simpler `compose.yaml` (service `postgres`).

Key files to read first
- `build.gradle` — dependency set, Java toolchain (languageVersion 25), and Graal native plugin.
- `src/main/java/com/example/javaspring4/Javaspring4Application.java` — application bootstrap.
- `src/main/resources/application.properties` — minimal app config (spring.application.name).
- `docker-compose.yml` and `.devcontainer/devcontainer.json` — how the dev container and DB are wired.
- `.devcontainer/Dockerfile` — devcontainer base image (Java 21) and optional tooling.

Big-picture architecture
- Reactive stack: `spring-boot-starter-webflux` + `spring-boot-starter-data-r2dbc`. Expect non-blocking handlers and R2DBC repositories if added.
- Dataflow: reactive HTTP handlers -> reactive services -> R2DBC repositories -> PostgreSQL (containerized in `docker-compose.yml`).
- Native-image intent: `org.graalvm.buildtools.native` is applied. Building native images requires a compatible Graal/Mandrel runtime and may require additional setup in the devcontainer or CI.

Developer workflows & useful commands
- Start Postgres (recommended when developing locally):
  - `docker-compose -f docker-compose.yml up -d db`
  - Or use `docker compose up -d postgres` when using `compose.yaml`.
- Run the app (non-native):
  - `./gradlew bootRun` (runs with the JVM configured by the toolchain)
- Build and test:
  - `./gradlew build`
  - `./gradlew test` (tests use JUnit Platform — `build.gradle` config uses `useJUnitPlatform()`)
- Discover Gradle tasks (especially native-related tasks):
  - `./gradlew tasks --all | sed -n '1,200p'` then grep for `native` or `graal` to find available native-image tasks.
- Devcontainer and ports:
  - `.devcontainer/devcontainer.json` is configured for a containerized dev environment. It references a Dockerfile and suggests using `forwardPorts` to expose ports. The devcontainer sets `DB_HOST=postgres` (container network).

Project-specific conventions / gotchas
- Java toolchain in `build.gradle` is set to **Java 25**. The devcontainer base image uses Java 21 (`.devcontainer/Dockerfile`). Verify the JDK used inside the container before compiling native images.
- The `docker-compose.yml` uses `network_mode: service:db` for the `app` service and relies on forwarding in the devcontainer; this means local port mapping may not be present unless `forwardPorts` is configured in `devcontainer.json` or you run the container differently.
- Reactive-first: if you add code, prefer reactive types (Mono/Flux) and non-blocking libraries (R2DBC, WebFlux). Mixing blocking JDBC or blocking I/O will cause thread starvation.
- AOT / generated code: the build already produces AOT sources and resources under `build/generated` and `build/generated/aot*` (useful when investigating startup/AOT configurations).

Integration points
- Postgres container: `docker-compose.yml` (service `db`) — environment vars `POSTGRES_USER/POSTGRES_PASSWORD/POSTGRES_DB` are expected by the app when running in the compose/devcontainer setup.
- Runtime environment: `.devcontainer/devcontainer.json` injects `DB_HOST`, `GRAALVM_HOME`, and other env vars used during development.

How to proceed as an AI agent (concise checklist)
- Read `build.gradle` and `.devcontainer/devcontainer.json` first to know toolchain, deps and container behavior.
- If you need to run the app, start Postgres via `docker-compose -f docker-compose.yml up -d db` then `./gradlew bootRun`.
- To prepare a native image, first run `./gradlew tasks --all` and locate the native task (look for `nativeImage`, `nativeCompile`, or similar). Verify Graal/Mandrel availability in the environment and match the `java.toolchain` version.
- When editing source, follow reactive patterns (Mono/Flux). Use `src/main/java/com/example/javaspring4` as the package root.

If something is unclear
- Tell me which command/environment you plan to use (local JVM, devcontainer, or CI) and I will tailor exact steps (e.g., installing Mandrel or adjusting the devcontainer).

Examples referencing files
- Main: `src/main/java/com/example/javaspring4/Javaspring4Application.java`
- Build: `./gradlew build`
- DB: `docker-compose -f docker-compose.yml up -d db`

-- End
