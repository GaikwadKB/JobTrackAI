package com.jobtrackai.core.database

import androidx.room.TypeConverter
import com.jobtrackai.core.common.sync.SyncStatus
import java.time.Instant

/**
 * Shared Room type converters, registered once on [AppDatabase] rather than
 * per-entity so every feature's DAOs get them for free.
 *
 * Kept minimal on purpose: only types every syncable entity needs
 * ([Instant] for `createdAt`/`updatedAt`/`deletedAt` timestamps, and
 * [SyncStatus] — see Section 8's `Job` model and Section 25's sync states).
 * Feature-specific converters (e.g. a `List<String>` skills column) are
 * added alongside the entity that needs them, in that feature's own
 * package, and registered with `@TypeConverters` on that entity/DAO rather
 * than dumped in here — keeps this file from becoming the "misc converters"
 * dumping ground Rule 2 warns against.
 */
object Converters {

    @TypeConverter
    @JvmStatic
    fun fromEpochMillis(value: Long?): Instant? = value?.let(Instant::ofEpochMilli)

    @TypeConverter
    @JvmStatic
    fun instantToEpochMillis(instant: Instant?): Long? = instant?.toEpochMilli()

    @TypeConverter
    @JvmStatic
    fun fromSyncStatusName(value: String?): SyncStatus? = value?.let(SyncStatus::valueOf)

    @TypeConverter
    @JvmStatic
    fun syncStatusToName(status: SyncStatus?): String? = status?.name
}
