package hnau.common.kotlin

import arrow.core.NonEmptyList
import arrow.core.None
import arrow.core.Option
import arrow.core.Some
import arrow.core.toNonEmptyListOrThrow

inline fun <T> Iterable<T>.sumOf(selector: (T) -> Float): Float {
    var sum = 0f
    for (element in this) {
        sum += selector(element)
    }
    return sum
}

inline fun <T> Iterable<T>.exclude(
    predicate: (T) -> Boolean,
): Pair<List<T>, T>? = this
    .fold<_, Pair<List<T>, Option<T>>>(
        initial = emptyList<T>() to None
    ) { (acc, excludedOrNone), item ->
        excludedOrNone.fold(
            ifSome = { (acc + item) to excludedOrNone },
            ifEmpty = {
                predicate(item).foldBoolean(
                    ifTrue = { acc to Some(item) },
                    ifFalse = { (acc + item) to None },
                )
            },
        )
    }
    .let { (remaining, excludedOrNone) ->
        excludedOrNone.fold(
            ifEmpty = { null },
            ifSome = { excluded -> remaining to excluded },
        )
    }

inline fun <I, K, O> Iterable<I>.groupByToNonEmpty(
    split: (I) -> KeyValue<K, O>,
): Map<K, NonEmptyList<O>> = map(split)
    .groupBy(KeyValue<K, *>::key)
    .mapValues { (_, items) ->
        items
            .map(KeyValue<*, O>::value)
            .toNonEmptyListOrThrow()
    }