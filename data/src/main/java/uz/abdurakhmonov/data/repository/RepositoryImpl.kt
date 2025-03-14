package uz.abdurakhmonov.data.repository

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import uz.abdurakhmonov.data.local.LocalDataStore
import uz.abdurakhmonov.data.remote.HistoryDate
import javax.inject.Inject

internal class RepositoryImpl @Inject constructor(
    private val localDataSource: LocalDataStore,
) : Repository {

    override suspend fun setDate(date: Long) { localDataSource.setDate(date) }

    override suspend fun getDate(): Long = localDataSource.getDate()

    override suspend fun setFlags(flags: List<HistoryDate>) { localDataSource.setFlags(flags) }

    override fun getFlags(): Flow<List<HistoryDate>> = localDataSource.getFlags().flowOn(Dispatchers.IO)

    override suspend fun getState(): String = localDataSource.getState()

    override suspend fun setState(state: String) { localDataSource.setState(state) }

    override suspend fun setTimer(time: Long) { localDataSource.setTimer(time) }

    override suspend fun getTimer(): Long = localDataSource.getTimer()

    override suspend fun setCounter(counter: Int) { localDataSource.setCounter(counter) }

    override suspend fun getCounter(): Int = localDataSource.getCounter()

}