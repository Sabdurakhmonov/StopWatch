package uz.abdurakhmonov.domain.mapper

import uz.abdurakhmonov.data.remote.local.HistoryDate
import uz.abdurakhmonov.domain.remote.History

fun HistoryDate.toDomain() = History(
    id = id,
    oldTime = oldTime,
    time = time,
)

fun History.toData() = HistoryDate(
    id = id,
    oldTime = oldTime,
    time = time,
)