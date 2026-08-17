package com.jobtrackai.core.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

/**
 * The single Room database for the app — the offline-first source of truth
 * described in Section 24 (Room → UI observes Flow, network only fills Room,
 * never the UI directly).
 *
 * Deliberately empty of `entities`/`daos` for now. Section 23 lists eleven
 * required entities (`UserEntity`, `JobEntity`, `ApplicationEntity`, etc.);
 * adding them all here in Phase 2 — before any feature that owns them
 * exists — would violate Rule 2 (no giant files) by front-loading a huge
 * annotation list nobody can review in context. Instead, each feature
 * module contributes its own entity/DAO in the phase that implements it
 * (Jobs → Phase 8, Applications → Phase 9, Interviews → Phase 10, ...),
 * and this class's `entities` array grows by exactly the lines that phase
 * needs.
 *
 * `version` starts at 1 and bumps (with an accompanying `Migration`) every
 * time a later phase adds or changes a table — never a destructive
 * fallback, since Rule 5 requires user data to survive schema changes.
 */
@Database(
    entities = [],
    version = 1,
    exportSchema = true,
)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase()
