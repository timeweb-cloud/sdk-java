

# KafkaConfigParameters

Настройки топика Kafka. Все значения возвращаются в виде строк. Не заданные явно параметры возвращаются со значениями по умолчанию.

## Properties

| Name | Type | Description | Notes |
|------------ | ------------- | ------------- | -------------|
|**partitions** | **String** | Количество партиций топика. |  [optional] |
|**cleanupPolicy** | [**CleanupPolicyEnum**](#CleanupPolicyEnum) | Политика очистки старых сегментов лога: &#x60;delete&#x60; — удалять, &#x60;compact&#x60; — уплотнять. |  [optional] |
|**compressionType** | [**CompressionTypeEnum**](#CompressionTypeEnum) | Тип сжатия сообщений в топике. |  [optional] |
|**deleteRetentionMs** | **String** | Время (в мс) хранения меток удаления для уплотняемых топиков. |  [optional] |
|**fileDeleteDelayMs** | **String** | Задержка (в мс) перед удалением файла из файловой системы. |  [optional] |
|**flushMessages** | **String** | Количество сообщений, после которого данные принудительно сбрасываются на диск. |  [optional] |
|**flushMs** | **String** | Интервал (в мс), после которого данные принудительно сбрасываются на диск. |  [optional] |
|**indexIntervalBytes** | **String** | Интервал (в байтах), с которым Kafka добавляет запись в индекс смещений. |  [optional] |
|**minCompactionLagMs** | **String** | Минимальное время (в мс), в течение которого сообщение остается неуплотненным. |  [optional] |
|**maxCompactionLagMs** | **String** | Максимальное время (в мс), в течение которого сообщение может оставаться неуплотненным. |  [optional] |
|**maxMessageBytes** | **String** | Максимальный размер (в байтах) пакета сообщений. |  [optional] |
|**messageFormatVersion** | **String** | Версия формата сообщений, в котором Kafka добавляет сообщения в лог. |  [optional] |
|**messageTimestampDifferenceMaxMs** | **String** | Максимально допустимая разница (в мс) между временной меткой сообщения и временем его получения брокером. |  [optional] |
|**messageDownconversionEnable** | [**MessageDownconversionEnableEnum**](#MessageDownconversionEnableEnum) | Понижение версии формата сообщений для старых клиентов. |  [optional] |
|**messageTimestampType** | [**MessageTimestampTypeEnum**](#MessageTimestampTypeEnum) | Источник временной метки сообщения: &#x60;CreateTime&#x60; — время создания сообщения клиентом, &#x60;LogAppendTime&#x60; — время добавления сообщения в лог брокером. |  [optional] |
|**minCleanableDirtyRatio** | **String** | Доля неуплотненных данных в логе, при которой запускается уплотнение. |  [optional] |
|**minInsyncReplicas** | **String** | Минимальное количество синхронизированных реплик, необходимое для подтверждения записи. |  [optional] |
|**preallocate** | [**PreallocateEnum**](#PreallocateEnum) | Предварительное выделение места на диске при создании нового сегмента лога. |  [optional] |
|**retentionBytes** | **String** | Максимальный размер (в байтах) партиции топика, после которого старые сегменты удаляются. &#x60;-1&#x60; — без ограничения. |  [optional] |
|**retentionMs** | **String** | Время (в мс) хранения сообщений в топике. &#x60;-1&#x60; — хранить бессрочно. |  [optional] |
|**segmentBytes** | **String** | Максимальный размер (в байтах) одного сегмента лога. |  [optional] |
|**segmentIndexBytes** | **String** | Максимальный размер (в байтах) индексного файла сегмента лога. |  [optional] |
|**segmentJitterMs** | **String** | Максимальное случайное отклонение (в мс) от времени ротации сегмента. |  [optional] |
|**segmentMs** | **String** | Период (в мс), после которого Kafka создает новый сегмент лога. |  [optional] |
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



