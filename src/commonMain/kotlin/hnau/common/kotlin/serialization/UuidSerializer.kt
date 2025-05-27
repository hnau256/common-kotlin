package hnau.common.kotlin.serialization

import hnau.common.kotlin.mapper.Mapper
import hnau.common.kotlin.mapper.stringToUuid
import kotlinx.serialization.builtins.serializer
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

@OptIn(ExperimentalUuidApi::class)
object UuidSerializer: MappingKSerializer<String, Uuid>(
    base = String.serializer(),
    mapper = Mapper.stringToUuid,
)