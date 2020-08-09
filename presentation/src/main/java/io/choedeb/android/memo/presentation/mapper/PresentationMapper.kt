package io.choedeb.android.memo.presentation.mapper

interface PresentationMapper<E, D> {
    fun toPresentationEntity(type: E): D
    fun toDomainEntity(type: D): E
}
