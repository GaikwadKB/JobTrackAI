package com.jobtrackai.core.database

import androidx.room.TypeConverter
import com.jobtrackai.core.common.model.ApplicationStage
import com.jobtrackai.core.common.model.RemotePreference
import com.jobtrackai.core.common.sync.SyncStatus
import java.time.Instant

/**
 * Shared Room type converters, registered once on [AppDatabase] rather than
 * per-entity so every feature's DAOs get them for free.
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

    @TypeConverter
    @JvmStatic
    fun fromRemotePreferenceName(value: String?): RemotePreference? = value?.let(RemotePreference::valueOf)

    @TypeConverter
    @JvmStatic
    fun remotePreferenceToName(value: RemotePreference?): String? = value?.name

    @TypeConverter
    @JvmStatic
    fun fromApplicationStageName(value: String?): ApplicationStage? = value?.let(ApplicationStage::valueOf)

    @TypeConverter
    @JvmStatic
    fun applicationStageToName(value: ApplicationStage?): String? = value?.name

    @TypeConverter
    @JvmStatic
    fun fromStringList(value: String?): List<String>? = value?.split(",")

    @TypeConverter
    @JvmStatic
    fun stringListToString(list: List<String>?): String? = list?.joinToString(",")
}
