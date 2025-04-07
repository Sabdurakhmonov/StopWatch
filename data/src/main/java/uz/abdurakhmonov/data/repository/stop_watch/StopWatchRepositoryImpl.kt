package uz.abdurakhmonov.data.repository.stop_watch

import kotlinx.coroutines.flow.Flow
import uz.abdurakhmonov.data.local.LocalDataStore
import uz.abdurakhmonov.data.remote.local.HistoryDate
import javax.inject.Inject

internal class StopWatchRepositoryImpl @Inject constructor(
    private val localDataSource: LocalDataStore,
) : StopWatchRepository {

    override suspend fun setDate(date: Long) { localDataSource.setDate(date) }

    override suspend fun getDate(): Long = localDataSource.getDate()

    override suspend fun setFlags(flags: List<HistoryDate>) { localDataSource.setFlags(flags) }

    override fun getFlags(): Flow<List<HistoryDate>> = localDataSource.getFlags()

    override suspend fun getState(): String = localDataSource.getState()

    override suspend fun setState(state: String) { localDataSource.setState(state) }

    override suspend fun setTimer(time: Long) { localDataSource.setTimer(time) }

    override suspend fun getTimer(): Long = localDataSource.getTimer()

    override suspend fun setCounter(counter: Int) { localDataSource.setCounter(counter) }

    override suspend fun getCounter(): Int = localDataSource.getCounter()

}