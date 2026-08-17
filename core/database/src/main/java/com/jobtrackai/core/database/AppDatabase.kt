package com.jobtrackai.core.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

import com.jobtrackai.core.database.dummy.DummyEntity

/**
 * The single Room database for the app — the offline-first source of truth
 * described in Section 24 (Room → UI observes Flow, network only fills Room,
 * never the UI directly).
 *
 * Each feature module contributes its own entity/DAO in the phase that
 * implements it (Jobs → Phase 8, Applications → Phase 9, Interviews → Phase 10, ...).
 *
 * For now, it contains a [DummyEntity] just to satisfy Room's requirement
 * for at least one entity during the Phase 4 Navigation Skeleton build.
 */
@Database(
    entities = [DummyEntity::class],
    version = 1,
    exportSchema = true,
)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase()
