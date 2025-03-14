package uz.abdurakhmonov.domain.use_case

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import uz.abdurakhmonov.data.repository.Repository
import uz.abdurakhmonov.domain.mapper.toData
import uz.abdurakhmonov.domain.mapper.toDomain
import uz.abdurakhmonov.domain.remote.History
import javax.inject.Inject

internal class LocalDateStoreUCImpl @Inject constructor(
    private val repository: Repository
) : LocalDateStoreUC {

    override suspend fun setDate(date: Long) { repository.setDate(date) }

    override suspend fun getDate(): Long = repository.getDate()

    override suspend fun setFlags(flags: List<History>) { repository.setFlags(flags.map { it.toData() }) }

    override fun getFlags(): Flow<List<History>> = flow {
        repository.getFlags().collect{
            emit(it.map { it.toDomain() })
        }
    }

    override suspend fun getState(): String =repository.getState()

    override suspend fun setState(state: String) { repository.setState(state) }

    override suspend fun setTimer(time: Long) { repository.setTimer(time) }

    override suspend fun getTimer(): Long = repository.getTimer()

    override suspend fun setCounter(counter: Int) { repository.setCounter(counter) }

    override suspend fun getCounter(): Int  = repository.getCounter()
}