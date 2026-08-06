

# UpdateKafkaConfigParameters

Настройки топика Kafka. Передаются только для кластеров Kafka: для кластеров других типов запрос вернется с ошибкой `forbidden_change_configuration`. Не переданные параметры получают значения по умолчанию. Числовые значения можно передавать как числом, так и строкой.

## Properties

| Name | Type | Description | Notes |
|------------ | ------------- | ------------- | -------------|
|**partitions** | **Integer** | Количество партиций топика. Количество партиций нельзя уменьшить: если передать значение меньше текущего, останется текущее. |  [optional] |
|**cleanupPolicy** | [**CleanupPolicyEnum**](#CleanupPolicyEnum) | Политика очистки старых сегментов лога: &#x60;delete&#x60; — удалять, &#x60;compact&#x60; — уплотнять. |  [optional] |
|**compressionType** | [**CompressionTypeEnum**](#CompressionTypeEnum) | Тип сжатия сообщений в топике. |  [optional] |
|**deleteRetentionMs** | **Long** | Время (в мс) хранения меток удаления для уплотняемых топиков. Максимальное значение — 9223372036854775807. |  [optional] |
|**fileDeleteDelayMs** | **Long** | Задержка (в мс) перед удалением файла из файловой системы. Максимальное значение — 9223372036854775807. |  [optional] |
|**flushMessages** | **Long** | Количество сообщений, после которого данные принудительно сбрасываются на диск. Максимальное значение — 9223372036854775807. |  [optional] |
|**flushMs** | **Long** | Интервал (в мс), после которого данные принудительно сбрасываются на диск. Максимальное значение — 9223372036854775807. |  [optional] |
|**indexIntervalBytes** | **Integer** | Интервал (в байтах), с которым Kafka добавляет запись в индекс смещений. |  [optional] |
|**minCompactionLagMs** | **Long** | Минимальное время (в мс), в течение которого сообщение остается неуплотненным. Максимальное значение — 9223372036854775807. |  [optional] |
|**maxCompactionLagMs** | **Long** | Максимальное время (в мс), в течение которого сообщение может оставаться неуплотненным. Максимальное значение — 9223372036854775807. |  [optional] |
|**maxMessageBytes** | **Integer** | Максимальный размер (в байтах) пакета сообщений. |  [optional] |
|**messageFormatVersion** | [**MessageFormatVersionEnum**](#MessageFormatVersionEnum) | Версия формата сообщений, в котором Kafka добавляет сообщения в лог. |  [optional] |
|**messageTimestampDifferenceMaxMs** | **Long** | Максимально допустимая разница (в мс) между временной меткой сообщения и временем его получения брокером. Максимальное значение — 9223372036854775807. |  [optional] |
|**messageDownconversionEnable** | [**MessageDownconversionEnableEnum**](#MessageDownconversionEnableEnum) | Понижение версии формата сообщений для старых клиентов. |  [optional] |
|**messageTimestampType** | [**MessageTimestampTypeEnum**](#MessageTimestampTypeEnum) | Источник временной метки сообщения: &#x60;CreateTime&#x60; — время создания сообщения клиентом, &#x60;LogAppendTime&#x60; — время добавления сообщения в лог брокером. |  [optional] |
|**minCleanableDirtyRatio** | **BigDecimal** | Доля неуплотненных данных в логе, при которой запускается уплотнение. |  [optional] |
|**minInsyncReplicas** | **Integer** | Минимальное количество синхронизированных реплик, необходимое для подтверждения записи. |  [optional] |
|**preallocate** | [**PreallocateEnum**](#PreallocateEnum) | Предварительное выделение места на диске при создании нового сегмента лога. |  [optional] |
|**retentionBytes** | **Long** | Максимальный размер (в байтах) партиции топика, после которого старые сегменты удаляются. &#x60;-1&#x60; — без ограничения. Максимальное значение — 9223372036854775807. |  [optional] |
|**retentionMs** | **Long** | Время (в мс) хранения сообщений в топике. &#x60;-1&#x60; — хранить бессрочно. Максимальное значение — 9223372036854775807. |  [optional] |
|**segmentBytes** | **Integer** | Максимальный размер (в байтах) одного сегмента лога. |  [optional] |
|**segmentIndexBytes** | **Integer** | Максимальный размер (в байтах) индексного файла сегмента лога. |  [optional] |
|**segmentJitterMs** | **Long** | Максимальное случайное отклонение (в мс) от времени ротации сегмента. Максимальное значение — 9223372036854775807. |  [optional] |
|**segmentMs** | **Long** | Период (в мс), после которого Kafka создает новый сегмент лога. Максимальное значение — 9223372036854775807. |  [optional] |
|**uncleanLeaderElectionEnable** | [**UncleanLeaderElectionEnableEnum**](#UncleanLeaderElectionEnableEnum) | Возможность выбрать лидером партиции реплику, которая не входит в число синхронизированных. |  [optional] |



## Enum: CleanupPolicyEnum

| Name | Value |
|---- | -----|
| DELETE | &quot;delete&quot; |
| COMPACT | &quot;compact&quot; |



## Enum: CompressionTypeEnum

| Name | Value |
|---- | -----|
| UNCOMPRESSED | &quot;uncompressed&quot; |
| ZSTD | &quot;zstd&quot; |
| LZ4 | &quot;lz4&quot; |
| SNAPPY | &quot;snappy&quot; |
| GZIP | &quot;gzip&quot; |
| PRODUCER | &quot;producer&quot; |



## Enum: MessageFormatVersionEnum

| Name | Value |
|---- | -----|
| _0_8_0 | &quot;0.8.0&quot; |
| _0_8_1 | &quot;0.8.1&quot; |
| _0_8_2 | &quot;0.8.2&quot; |
| _0_9_0 | &quot;0.9.0&quot; |
| _0_10_0_IV0 | &quot;0.10.0-IV0&quot; |
| _0_10_0_IV1 | &quot;0.10.0-IV1&quot; |
| _0_10_1_IV0 | &quot;0.10.1-IV0&quot; |
| _0_10_1_IV1 | &quot;0.10.1-IV1&quot; |
| _0_10_1_IV2 | &quot;0.10.1-IV2&quot; |
| _0_10_2_IV0 | &quot;0.10.2-IV0&quot; |
| _0_11_0_IV0 | &quot;0.11.0-IV0&quot; |
| _0_11_0_IV1 | &quot;0.11.0-IV1&quot; |
| _0_11_0_IV2 | &quot;0.11.0-IV2&quot; |
| _1_0_IV0 | &quot;1.0-IV0&quot; |
| _1_1_IV0 | &quot;1.1-IV0&quot; |
| _2_0_IV0 | &quot;2.0-IV0&quot; |
| _2_0_IV1 | &quot;2.0-IV1&quot; |
| _2_1_IV0 | &quot;2.1-IV0&quot; |
| _2_1_IV1 | &quot;2.1-IV1&quot; |
| _2_1_IV2 | &quot;2.1-IV2&quot; |
| _2_2_IV0 | &quot;2.2-IV0&quot; |
| _2_2_IV1 | &quot;2.2-IV1&quot; |
| _2_3_IV0 | &quot;2.3-IV0&quot; |
| _2_3_IV1 | &quot;2.3-IV1&quot; |
| _2_4_IV0 | &quot;2.4-IV0&quot; |
| _2_4_IV1 | &quot;2.4-IV1&quot; |
| _2_5_IV0 | &quot;2.5-IV0&quot; |
| _2_6_IV0 | &quot;2.6-IV0&quot; |
| _2_7_IV0 | &quot;2.7-IV0&quot; |
| _2_7_IV1 | &quot;2.7-IV1&quot; |
| _2_7_IV2 | &quot;2.7-IV2&quot; |
| _2_8_IV0 | &quot;2.8-IV0&quot; |
| _2_8_IV1 | &quot;2.8-IV1&quot; |
| _3_0_IV0 | &quot;3.0-IV0&quot; |
| _3_0_IV1 | &quot;3.0-IV1&quot; |
| _3_1_IV0 | &quot;3.1-IV0&quot; |
| _3_2_IV0 | &quot;3.2-IV0&quot; |
| _3_3_IV0 | &quot;3.3-IV0&quot; |
| _3_3_IV1 | &quot;3.3-IV1&quot; |
| _3_3_IV2 | &quot;3.3-IV2&quot; |
| _3_3_IV3 | &quot;3.3-IV3&quot; |
| _3_4_IV0 | &quot;3.4-IV0&quot; |
| _3_5_IV0 | &quot;3.5-IV0&quot; |
| _3_5_IV1 | &quot;3.5-IV1&quot; |
| _3_5_IV2 | &quot;3.5-IV2&quot; |



## Enum: MessageDownconversionEnableEnum

| Name | Value |
|---- | -----|
| ON | &quot;ON&quot; |
| OFF | &quot;OFF&quot; |



## Enum: MessageTimestampTypeEnum

| Name | Value |
|---- | -----|
| CREATETIME | &quot;CreateTime&quot; |
| LOGAPPENDTIME | &quot;LogAppendTime&quot; |



## Enum: PreallocateEnum

| Name | Value |
|---- | -----|
| ON | &quot;ON&quot; |
| OFF | &quot;OFF&quot; |



## Enum: UncleanLeaderElectionEnableEnum

| Name | Value |
|---- | -----|
| ON | &quot;ON&quot; |
| OFF | &quot;OFF&quot; |



