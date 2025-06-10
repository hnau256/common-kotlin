package hnau.common.kotlin.serialization

import hnau.common.kotlin.mapper.Mapper
import kotlinx.serialization.KSerializer
import kotlinx.serialization.Serializable
import kotlinx.serialization.builtins.serializer

sealed class ClosedFloatingPointRangeSerializer<T : Comparable<T>>(
    itemSerializer: KSerializer<T>,
    createRange: (start: T, endInclusive: T) -> ClosedFloatingPointRange<T>,
) : MappingKSerializer<ClosedFloatingPointRangeSerializer.Surrogate<T>, ClosedFloatingPointRange<T>>(
    base = Surrogate.serializer(itemSerializer),
    mapper = Mapper(
        direct = { surrogate ->
            createRange(surrogate.start, surrogate.endInclusive)
        },
        reverse = { range ->
            Surrogate(
                start = range.start,
                endInclusive = range.endInclusive,
            )
        }
    )
) {

    @Serializable
    data class Surrogate<T>(
        val start: T,
        val endInclusive: T,
    )

    object Float: ClosedFloatingPointRangeSerializer<kotlin.Float>(
        itemSerializer = kotlin.Float.serializer(),
        createRange = { start, endInclusive ->
            start..endInclusive
        },
    )

    object Double: ClosedFloatingPointRangeSerializer<kotlin.Double>(
        itemSerializer = kotlin.Double.serializer(),
        createRange = { start, endInclusive ->
            start..endInclusive
        },
    )
}