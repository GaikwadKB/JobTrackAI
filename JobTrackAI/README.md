# JobTrack AI

> Status: **Phase 1 — Project Setup** complete. This is a scaffold: module structure,
> Gradle wiring, and a placeholder launch screen. Features are added phase by phase
> (see `docs/PHASES.md` — added once later phases begin).

## What's in this commit

- Multi-module Gradle project (Kotlin DSL), 1 app module + 8 `core` modules + 11 `feature` modules
- Centralized version catalog (`gradle/libs.versions.toml`) — every module resolves dependency versions from here, nothing hardcoded per-module
- Hilt wired at the app level (`JobTrackApplication`, `MainActivity`, `HiltTestRunner`)
- Firebase plugins applied (`google-services`, `crashlytics`) but **not yet configured** — see setup below
- A placeholder Compose screen confirming the toolchain builds end-to-end

## Prerequisites

- Android Studio (2025.3 "Otter" or newer recommended)
- JDK 17
- An Android device/emulator running API 26+

## First-time setup (do this before opening in Android Studio)

1. **Generate the Gradle wrapper jar.** This repo ships `gradle/wrapper/gradle-wrapper.properties`
   (pinned to Gradle 8.11.1) but not the wrapper `.jar`/`gradlew` scripts, since those are binary/
   generated artifacts best produced by your local Gradle install rather than hand-authored. From
   the project root, with any Gradle ≥8 installed locally (or via Android Studio's bundled Gradle):
   ```bash
   gradle wrapper --gradle-version 8.11.1
   ```
   Alternatively, just open the project in Android Studio — it will detect the missing wrapper and
   offer to generate it automatically on first sync.

2. **Firebase config.** `google-services.json` is intentionally **not** included (it's git-ignored —
   see Rule 3, no secrets committed). To run anything past Phase 1:
   - Create a Firebase project at https://console.firebase.google.com
   - Register an Android app with package name `com.jobtrackai.app` (and `com.jobtrackai.app.debug`
     for the debug build type, since `applicationIdSuffix = ".debug"` is set)
   - Download `google-services.json` and place it in `app/`
   - Until you do this, the app **will still build and run** in Phase 1 — Firebase isn't touched
     until later phases wire actual Auth/Firestore calls. The plugin is applied now so later phases
     don't require a build-file migration.

3. **AI provider key (not needed yet).** Per section 57 of the spec, no AI key is ever compiled into
   the APK. When Phase 14 (AI abstraction) lands, `MockAIService` will be the default in debug builds
   (see `AI_MOCK_MODE_DEFAULT` build config field in `app/build.gradle.kts`), so the app is fully
   demoable without any key. A real key, if you add one later, goes in `local.properties` /
   `secrets.properties` — never in source.

4. **Sync and run.** Open the project root in Android Studio, let Gradle sync, then run the `app`
   configuration on a device/emulator (API 26+). You should see:
   > "JobTrack AI — project scaffold ready (Phase 1)"

## Architecture (high level)

```
UI (Composable) → ViewModel → UseCase → Repository → Room (local) / OkHttp (remote)
                                                ↓
                                          Sync Manager (WorkManager)
                                                ↓
                                        Firebase / REST API
```

Room is the offline-first source of truth; the UI observes `Flow`s from Room and never talks to
Room or the network directly (Rule 6). Full architecture diagram and per-layer rationale will be
documented in `docs/ARCHITECTURE.md` once the data layer (Phase 7) exists to describe accurately.

## Module map

| Module | Purpose |
|---|---|
| `app` | Thin composition root: `Application`, `MainActivity`, nav host wiring |
| `core:common` | `Result` wrapper, `DispatcherProvider`, shared extensions — no Android UI, no Hilt |
| `core:designsystem` | Material3 theme, typography, shared composables |
| `core:database` | Room: `AppDatabase`, DAOs, entities |
| `core:datastore` | DataStore Preferences: settings, session flags |
| `core:network` | Centralized OkHttp client, error mapping |
| `core:sync` | Sync queue, WorkManager sync workers |
| `core:notifications` | Notification channels, scheduling |
| `core:di` | Hilt modules that wire the above together |
| `feature:*` | One module per user-facing feature (`auth`, `jobs`, `applications`, `interviews`, `resume`, `profile`, `ai`, `speech`, `analytics`, `settings`, `onboarding`), each internally split into `data/domain/presentation` |

## What's intentionally *not* here yet

- Material3 theme/typography (Phase 3)
- Navigation graph (Phase 4)
- Room schema (Phase 7)
- Any real screens beyond the placeholder (Phases 5–10)
- GitHub Actions CI (Phase 21) — `.github/workflows/` exists but is empty
- Tests beyond one toolchain smoke test (Phase 19 formalizes coverage; each phase adds its own tests as it lands)

## License / Portfolio note

This is a portfolio project built to demonstrate production-grade Android architecture decisions
(offline-first sync, clean architecture layering, AI service abstraction, etc.) for Android
Developer interviews — see the top-level project brief for the full rationale behind each
technology choice.
