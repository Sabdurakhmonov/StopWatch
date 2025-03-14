package uz.abdurakhmonov.domain.use_case

import kotlinx.coroutines.flow.Flow
import uz.abdurakhmonov.data.remote.HistoryDate
import uz.abdurakhmonov.domain.remote.History

interface LocalDateStoreUC {
    suspend fun setDate(date:Long)
    suspend fun getDate():Long
    suspend fun setFlags(flags:List<History>)
    fun getFlags(): Flow<List<History>>
    suspend fun getState():String
    suspend fun setState(state:String)
    suspend fun setTimer(time:Long)
    suspend fun getTimer():Long
    suspend fun setCounter(counter:Int)
    suspend fun getCounter():Int

}