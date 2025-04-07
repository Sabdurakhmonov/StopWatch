package uz.abdurakhmonov.domain.use_case.stop_watch

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import uz.abdurakhmonov.data.repository.stop_watch.StopWatchRepository
import uz.abdurakhmonov.domain.mapper.toData
import uz.abdurakhmonov.domain.mapper.toDomain
import uz.abdurakhmonov.domain.remote.History
import javax.inject.Inject

internal class LocalDateStoreUCImpl @Inject constructor(
    private val stopWatchRepository: StopWatchRepository
) : LocalDateStoreUC {

    override suspend fun setDate(date: Long) { stopWatchRepository.setDate(date) }

    override suspend fun getDate(): Long = stopWatchRepository.getDate()

    override suspend fun setFlags(flags: List<History>) {
        stopWatchRepository.setFlags(flags.map { it.toData() })
    }

    override fun getFlags(): Flow<List<History>> = flow{
        stopWatchRepository.getFlags().collect {
            emit(it.map { it.toDomain() })
        }
    }

    override suspend fun getState(): String =stopWatchRepository.getState()

    override suspend fun setState(state: String) { stopWatchRepository.setState(state) }

    override suspend fun setTimer(time: Long) { stopWatchRepository.setTimer(time) }

    override suspend fun getTimer(): Long = stopWatchRepository.getTimer()

    override suspend fun setCounter(counter: Int) { stopWatchRepository.setCounter(counter) }

    override suspend fun getCounter(): Int  = stopWatchRepository.getCounter()
}