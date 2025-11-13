package hnau.common.kotlin.serialization

import kotlinx.datetime.format.DateTimeComponents
import kotlinx.datetime.serializers.FormattedInstantSerializer

data object InstantSerializer: FormattedInstantSerializer(
    name = "hnau.common.kotlin.serialization.InstantSerializer",
    format = DateTimeComponents.Formats.ISO_DATE_TIME_OFFSET,
)