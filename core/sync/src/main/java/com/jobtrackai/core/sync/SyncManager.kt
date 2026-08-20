package com.jobtrackai.core.sync

import android.content.Context
import androidx.work.Constraints
import androidx.work.ExistingWorkPolicy
import androidx.work.NetworkType
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import com.jobtrackai.core.common.util.NetworkMonitor
import com.jobtrackai.core.database.dao.SyncDao
import com.jobtrackai.core.sync.worker.SyncWorker
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SyncManager @Inject constructor(
    @ApplicationContext private val context: Context,
    private val syncDao: SyncDao,
    private val networkMonitor: NetworkMonitor
) {
    private val workManager = WorkManager.getInstance(context)

    /**
     * Starts observing the queue and network to trigger sync automatically.
     */
    fun setupAutoSync(scope: CoroutineScope) {
        scope.launch {
            combine(
                syncDao.getSyncQueue(),
                networkMonitor.isOnline
            ) { queue, isOnline ->
                queue.isNotEmpty() && isOnline
            }.collectLatest { shouldSync ->
                if (shouldSync) {
                    requestSync()
                }
            }
        }
    }

    /**
     * Manually requests a synchronization cycle.
     */
    fun requestSync() {
        val constraints = Constraints.Builder()
            .setRequiredNetworkType(NetworkType.CONNECTED)
            .build()

        val syncRequest = OneTimeWorkRequestBuilder<SyncWorker>()
            .setConstraints(constraints)
            .build()

        workManager.enqueueUniqueWork(
            SYNC_WORK_NAME,
            ExistingWorkPolicy.KEEP,
            syncRequest
        )
    }

    companion object {
        private const val SYNC_WORK_NAME = "JobTrackSyncWork"
    }
}
