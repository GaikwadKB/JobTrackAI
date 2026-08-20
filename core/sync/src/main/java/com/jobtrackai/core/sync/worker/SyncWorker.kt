package com.jobtrackai.core.sync.worker

import android.content.Context
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.jobtrackai.core.common.result.DomainResult
import com.jobtrackai.core.sync.domain.SyncRepository
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject

@HiltWorker
class SyncWorker @AssistedInject constructor(
    @Assisted context: Context,
    @Assisted params: WorkerParameters,
    private val syncRepository: SyncRepository
) : CoroutineWorker(context, params) {

    override suspend fun doWork(): Result {
        return when (syncRepository.sync()) {
            is DomainResult.Success -> Result.success()
            is DomainResult.Error -> Result.retry()
        }
    }
}
