package uz.abdurakhmonov.data.repository

import uz.abdurakhmonov.data.remote.HistoryDate

interface Repository {
    suspend fun setDate(date:Long)
    suspend fun getDate():Long
    suspend fun setFlags(flags:List<HistoryDate>)
    suspend fun getFlags():List<HistoryDate>
    suspend fun getState():String
    suspend fun setState(state:String)
    suspend fun setTimer(time:Long)
    suspend fun getTimer():Long
    suspend fun setCounter(counter:Int)
    suspend fun getCounter():Int
}