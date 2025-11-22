**Project Overview**
- **Type:** Spring Boot (reactive WebFlux) demo using R2DBC.
- **Main:** `src/main/java/com/example/javaspring4/Javaspring4Application.java`.

**Quick Start (local)**
- **Start Postgres:**
  - `docker-compose -f docker-compose.yml up -d db`
- **Run app (dev JVM):**
  - `./gradlew bootRun`
- **Build & test:**
  - `./gradlew build`
  - `./gradlew test`

- Continuous Build & test:
  - `./gradlew test --continue`


**Devcontainer**
- **Files:** `/.devcontainer/Dockerfile`, `/.devcontainer/devcontainer.json`.
- **Rebuild container with an optional Mandrel release:** pass build args to the devcontainer/Docker build. Example (manual docker build):

```
docker build --build-arg INSTALL_MANDREL_FROM_RELEASE=true --build-arg MANDREL_VERSION=mandrel-25.0.0 -f .devcontainer/Dockerfile .
```

- The devcontainer sets `GRAALVM_HOME=/opt/mandrel`; the Dockerfile will try a release download then fall back to SDKMAN.
- A named Docker volume `maven-m2` is created and mounted at `/home/vscode/.m2` to persist Maven cache across container restarts.

**Continuous development with Spring DevTools**
- DevTools is included as a `developmentOnly` dependency. To use continuous build + automatic restart:
  - Option A (single terminal): `./gradlew bootRun --continuous`
  - Option B (recommended):
    - Terminal 1: `./gradlew -t classes` (continuous compile)
    - Terminal 2: `./gradlew bootRun` (application restarts when classes update)
- To force DevTools restart behavior, set `SPRING_DEVTOOLS_RESTART_ENABLED=true` or pass `-Dspring.devtools.restart.enabled=true`.

Developer convenience (VS Code)
- There are VS Code tasks to run the two recommended steps:
  - `Gradle: Continuous Compile (classes)` — runs `./gradlew -t classes` in a background terminal
  - `Gradle: Boot Run` — runs `./gradlew bootRun` in a background terminal
- Open the Command Palette (Ctrl+Shift+P) -> `Tasks: Run Task` and start both tasks. DevTools will restart the app when compiled classes change.

**Native image (Mandrel / Graal)**
- `build.gradle` includes `org.graalvm.buildtools.native`. Building native images requires a compatible Mandrel/Graal runtime.
- Note: `build.gradle` config uses a Java toolchain set to Java 25. The devcontainer base image is Java 21; ensure your container provides a Java 25-compatible Mandrel (or install Temurin 25) before building native images.

**Maven cache volume**
- A named Docker volume `maven-m2` is used for `/home/vscode/.m2`. To inspect the volume on the host:

```
docker volume ls | grep maven-m2
docker volume inspect maven-m2
```

**Where to look next**
- **Build config:** `build.gradle`
- **Devcontainer config:** `.devcontainer/devcontainer.json`, `.devcontainer/Dockerfile`
- **Agent guidance:** `.github/copilot-instructions.md`

**Troubleshooting**
- If native-image builds fail due to toolchain mismatch, either:
  - Install a Mandrel release matching Java 25 into `/opt/mandrel`, or
  - Install a system JDK 25 (Temurin/Adoptium) in the devcontainer.
- If DevTools doesn't restart, ensure classes are recompiled (use `-t classes`), and check `spring.devtools.restart.enabled`.

**Feedback / Changes**
- If you want the devcontainer to always install a specific Mandrel version, tell me which exact `MANDREL_VERSION` string to pin and I will update the Dockerfile and `devcontainer.json` build args accordingly.
