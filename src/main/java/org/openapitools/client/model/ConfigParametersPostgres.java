/*
 * Timeweb Cloud API
 * # Введение API Timeweb Cloud позволяет вам управлять ресурсами в облаке программным способом с использованием обычных HTTP-запросов.  Множество функций, которые доступны в панели управления Timeweb Cloud, также доступны через API, что позволяет вам автоматизировать ваши собственные сценарии.  В этой документации сперва будет описан общий дизайн и принципы работы API, а после этого конкретные конечные точки. Также будут приведены примеры запросов к ним.   ## Запросы Запросы должны выполняться по протоколу `HTTPS`, чтобы гарантировать шифрование транзакций. Поддерживаются следующие методы запроса: |Метод|Применение| |--- |--- | |GET|Извлекает данные о коллекциях и отдельных ресурсах.| |POST|Для коллекций создает новый ресурс этого типа. Также используется для выполнения действий с конкретным ресурсом.| |PUT|Обновляет существующий ресурс.| |PATCH|Некоторые ресурсы поддерживают частичное обновление, то есть обновление только части атрибутов ресурса, в этом случае вместо метода PUT будет использован PATCH.| |DELETE|Удаляет ресурс.|  Методы `POST`, `PUT` и `PATCH` могут включать объект в тело запроса с типом содержимого `application/json`.  ### Параметры в запросах Некоторые коллекции поддерживают пагинацию, поиск или сортировку в запросах. В параметрах запроса требуется передать: - `limit` — обозначает количество записей, которое необходимо вернуть  - `offset` — указывает на смещение, относительно начала списка  - `search` — позволяет указать набор символов для поиска  - `sort` — можно задать правило сортировки коллекции  ## Ответы Запросы вернут один из следующих кодов состояния ответа HTTP:  |Статус|Описание| |--- |--- | |200 OK|Действие с ресурсом было выполнено успешно.| |201 Created|Ресурс был успешно создан. При этом ресурс может быть как уже готовым к использованию, так и находиться в процессе запуска.| |204 No Content|Действие с ресурсом было выполнено успешно, и ответ не содержит дополнительной информации в теле.| |400 Bad Request|Был отправлен неверный запрос, например, в нем отсутствуют обязательные параметры и т. д. Тело ответа будет содержать дополнительную информацию об ошибке.| |401 Unauthorized|Ошибка аутентификации.| |403 Forbidden|Аутентификация прошла успешно, но недостаточно прав для выполнения действия.| |404 Not Found|Запрашиваемый ресурс не найден.| |409 Conflict|Запрос конфликтует с текущим состоянием.| |423 Locked|Ресурс из запроса заблокирован от применения к нему указанного метода.| |429 Too Many Requests|Был достигнут лимит по количеству запросов в единицу времени.| |500 Internal Server Error|При выполнении запроса произошла какая-то внутренняя ошибка. Чтобы решить эту проблему, лучше всего создать тикет в панели управления.|  ### Структура успешного ответа Все конечные точки будут возвращать данные в формате `JSON`. Ответы на `GET`-запросы будут иметь на верхнем уровне следующую структуру атрибутов:  |Название поля|Тип|Описание| |--- |--- |--- | |[entity_name]|object, object[], string[], number[], boolean|Динамическое поле, которое будет меняться в зависимости от запрашиваемого ресурса и будет содержать все атрибуты, необходимые для описания этого ресурса. Например, при запросе списка баз данных будет возвращаться поле `dbs`, а при запросе конкретного облачного сервера `server`. Для некоторых конечных точек в ответе может возвращаться сразу несколько ресурсов.| |meta|object|Опционально. Объект, который содержит вспомогательную информацию о ресурсе. Чаще всего будет встречаться при запросе коллекций и содержать поле `total`, которое будет указывать на количество элементов в коллекции.| |response_id|string|Опционально. В большинстве случаев в ответе будет содержаться ID ответа в формате UUIDv4, который однозначно указывает на ваш запрос внутри нашей системы. Если вам потребуется задать вопрос нашей поддержке, приложите к вопросу этот ID— так мы сможем найти ответ на него намного быстрее. Также вы можете использовать этот ID, чтобы убедиться, что это новый ответ на запрос и результат не был получен из кэша.|  Пример запроса на получение списка SSH-ключей: ```     HTTP/2.0 200 OK     {       \"ssh_keys\":[           {             \"body\":\"ssh-rsa AAAAB3NzaC1sdfghjkOAsBwWhs= example@device.local\",             \"created_at\":\"2021-09-15T19:52:27Z\",             \"expired_at\":null,             \"id\":5297,             \"is_default\":false,             \"name\":\"example@device.local\",             \"used_at\":null,             \"used_by\":[]           }       ],       \"meta\":{           \"total\":1       },       \"response_id\":\"94608d15-8672-4eed-8ab6-28bd6fa3cdf7\"     } ```  ### Структура ответа с ошибкой |Название поля|Тип|Описание| |--- |--- |--- | |status_code|number|Короткий числовой идентификатор ошибки.| |error_code|string|Короткий текстовый идентификатор ошибки, который уточняет числовой идентификатор и удобен для программной обработки. Самый простой пример — это код `not_found` для ошибки 404.| |message|string, string[]|Опционально. В большинстве случаев в ответе будет содержаться человекочитаемое подробное описание ошибки или ошибок, которые помогут понять, что нужно исправить.| |response_id|string|Опционально. В большинстве случае в ответе будет содержаться ID ответа в формате UUIDv4, который однозначно указывает на ваш запрос внутри нашей системы. Если вам потребуется задать вопрос нашей поддержке, приложите к вопросу этот ID — так мы сможем найти ответ на него намного быстрее.|  Пример: ```     HTTP/2.0 403 Forbidden     {       \"status_code\": 403,       \"error_code\":  \"forbidden\",       \"message\":     \"You do not have access for the attempted action\",       \"response_id\": \"94608d15-8672-4eed-8ab6-28bd6fa3cdf7\"     } ```  ## Статусы ресурсов Важно учесть, что при создании большинства ресурсов внутри платформы вам будет сразу возвращен ответ от сервера со статусом `200 OK` или `201 Created` и ID созданного ресурса в теле ответа, но при этом этот ресурс может быть ещё в *состоянии запуска*.  Для того чтобы понять, в каком состоянии сейчас находится ваш ресурс, мы добавили поле `status` в ответ на получение информации о ресурсе.  Список статусов будет отличаться в зависимости от типа ресурса. Увидеть поддерживаемый список статусов вы сможете в описании каждого конкретного ресурса.     ## Ограничение скорости запросов (Rate Limiting) Чтобы обеспечить стабильность для всех пользователей, Timeweb Cloud защищает API от всплесков входящего трафика, анализируя количество запросов c каждого аккаунта к каждой конечной точке.  Если ваше приложение отправляет более 20 запросов в секунду на одну конечную точку, то для этого запроса API может вернуть код состояния HTTP `429 Too Many Requests`.   ## Аутентификация Доступ к API осуществляется с помощью JWT-токена. Токенами можно управлять внутри панели управления Timeweb Cloud в разделе *API и Terraform*.  Токен необходимо передавать в заголовке каждого запроса в формате: ```   Authorization: Bearer $TIMEWEB_CLOUD_TOKEN ```  ## Формат примеров API Примеры в этой документации описаны с помощью `curl`, HTTP-клиента командной строки. На компьютерах `Linux` и `macOS` обычно по умолчанию установлен `curl`, и он доступен для загрузки на всех популярных платформах, включая `Windows`.  Каждый пример разделен на несколько строк символом `\\`, который совместим с `bash`. Типичный пример выглядит так: ```   curl -X PATCH      -H \"Content-Type: application/json\"      -H \"Authorization: Bearer $TIMEWEB_CLOUD_TOKEN\"      -d '{\"name\":\"Cute Corvus\",\"comment\":\"Development Server\"}'      \"https://api.timeweb.cloud/api/v1/dedicated/1051\" ``` - Параметр `-X` задает метод запроса. Для согласованности метод будет указан во всех примерах, даже если он явно не требуется для методов `GET`. - Строки `-H` задают требуемые HTTP-заголовки. - Примеры, для которых требуется объект JSON в теле запроса, передают требуемые данные через параметр `-d`.  Чтобы использовать приведенные примеры, не подставляя каждый раз в них свой токен, вы можете добавить токен один раз в переменные окружения в вашей консоли. Например, на `Linux` это можно сделать с помощью команды:  ``` TIMEWEB_CLOUD_TOKEN=\"token\" ```  После этого токен будет автоматически подставляться в ваши запросы.  Обратите внимание, что все значения в этой документации являются примерами. Не полагайтесь на IDы операционных систем, тарифов и т.д., используемые в примерах. Используйте соответствующую конечную точку для получения значений перед созданием ресурсов.   ## Версионирование API построено согласно принципам [семантического версионирования](https://semver.org/lang/ru). Это значит, что мы гарантируем обратную совместимость всех изменений в пределах одной мажорной версии.  Мажорная версия каждой конечной точки обозначается в пути запроса, например, запрос `/api/v1/servers` указывает, что этот метод имеет версию 1.
 *
 * The version of the OpenAPI document: 1.0.0
 * Contact: info@timeweb.cloud
 *
 * NOTE: This class is auto generated by OpenAPI Generator (https://openapi-generator.tech).
 * https://openapi-generator.tech
 * Do not edit the class manually.
 */


package org.openapitools.client.model;

import java.util.Objects;
import java.util.Arrays;
import com.google.gson.TypeAdapter;
import com.google.gson.annotations.JsonAdapter;
import com.google.gson.annotations.SerializedName;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import java.io.IOException;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.TypeAdapterFactory;
import com.google.gson.reflect.TypeToken;
import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import java.io.IOException;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.openapitools.client.JSON;

/**
 * Параметры PostgreSQL (&#x60;postgres&#x60; | &#x60;postgres14&#x60; | &#x60;postgres15&#x60; | &#x60;postgres16&#x60; | &#x60;postgres17&#x60; | &#x60;postgres18&#x60;)
 */
@javax.annotation.Generated(value = "org.openapitools.codegen.languages.JavaClientCodegen", date = "2026-07-13T13:33:30.951959Z[Etc/UTC]")
public class ConfigParametersPostgres {
  public static final String SERIALIZED_NAME_MAX_CONNECTIONS = "max_connections";
  @SerializedName(SERIALIZED_NAME_MAX_CONNECTIONS)
  private String maxConnections;

  public static final String SERIALIZED_NAME_AUTOVACUUM_ANALYZE_SCALE_FACTOR = "autovacuum_analyze_scale_factor";
  @SerializedName(SERIALIZED_NAME_AUTOVACUUM_ANALYZE_SCALE_FACTOR)
  private String autovacuumAnalyzeScaleFactor;

  public static final String SERIALIZED_NAME_AUTOVACUUM_MAX_WORKERS = "autovacuum_max_workers";
  @SerializedName(SERIALIZED_NAME_AUTOVACUUM_MAX_WORKERS)
  private String autovacuumMaxWorkers;

  public static final String SERIALIZED_NAME_AUTOVACUUM_NAPTIME = "autovacuum_naptime";
  @SerializedName(SERIALIZED_NAME_AUTOVACUUM_NAPTIME)
  private String autovacuumNaptime;

  public static final String SERIALIZED_NAME_AUTOVACUUM_VACUUM_INSERT_SCALE_FACTOR = "autovacuum_vacuum_insert_scale_factor";
  @SerializedName(SERIALIZED_NAME_AUTOVACUUM_VACUUM_INSERT_SCALE_FACTOR)
  private String autovacuumVacuumInsertScaleFactor;

  public static final String SERIALIZED_NAME_AUTOVACUUM_VACUUM_SCALE_FACTOR = "autovacuum_vacuum_scale_factor";
  @SerializedName(SERIALIZED_NAME_AUTOVACUUM_VACUUM_SCALE_FACTOR)
  private String autovacuumVacuumScaleFactor;

  public static final String SERIALIZED_NAME_AUTOVACUUM_WORK_MEM = "autovacuum_work_mem";
  @SerializedName(SERIALIZED_NAME_AUTOVACUUM_WORK_MEM)
  private String autovacuumWorkMem;

  public static final String SERIALIZED_NAME_BGWRITER_DELAY = "bgwriter_delay";
  @SerializedName(SERIALIZED_NAME_BGWRITER_DELAY)
  private String bgwriterDelay;

  public static final String SERIALIZED_NAME_BGWRITER_LRU_MAXPAGES = "bgwriter_lru_maxpages";
  @SerializedName(SERIALIZED_NAME_BGWRITER_LRU_MAXPAGES)
  private String bgwriterLruMaxpages;

  public static final String SERIALIZED_NAME_DEADLOCK_TIMEOUT = "deadlock_timeout";
  @SerializedName(SERIALIZED_NAME_DEADLOCK_TIMEOUT)
  private String deadlockTimeout;

  public static final String SERIALIZED_NAME_GIN_PENDING_LIST_LIMIT = "gin_pending_list_limit";
  @SerializedName(SERIALIZED_NAME_GIN_PENDING_LIST_LIMIT)
  private String ginPendingListLimit;

  public static final String SERIALIZED_NAME_IDLE_IN_TRANSACTION_SESSION_TIMEOUT = "idle_in_transaction_session_timeout";
  @SerializedName(SERIALIZED_NAME_IDLE_IN_TRANSACTION_SESSION_TIMEOUT)
  private String idleInTransactionSessionTimeout;

  public static final String SERIALIZED_NAME_JOIN_COLLAPSE_LIMIT = "join_collapse_limit";
  @SerializedName(SERIALIZED_NAME_JOIN_COLLAPSE_LIMIT)
  private String joinCollapseLimit;

  public static final String SERIALIZED_NAME_LOCK_TIMEOUT = "lock_timeout";
  @SerializedName(SERIALIZED_NAME_LOCK_TIMEOUT)
  private String lockTimeout;

  public static final String SERIALIZED_NAME_MAX_PREPARED_TRANSACTIONS = "max_prepared_transactions";
  @SerializedName(SERIALIZED_NAME_MAX_PREPARED_TRANSACTIONS)
  private String maxPreparedTransactions;

  public static final String SERIALIZED_NAME_SHARED_BUFFERS = "shared_buffers";
  @SerializedName(SERIALIZED_NAME_SHARED_BUFFERS)
  private String sharedBuffers;

  public static final String SERIALIZED_NAME_LOG_MIN_DURATION_STATEMENT = "log_min_duration_statement";
  @SerializedName(SERIALIZED_NAME_LOG_MIN_DURATION_STATEMENT)
  private String logMinDurationStatement;

  public static final String SERIALIZED_NAME_WAL_BUFFERS = "wal_buffers";
  @SerializedName(SERIALIZED_NAME_WAL_BUFFERS)
  private String walBuffers;

  public static final String SERIALIZED_NAME_TEMP_BUFFERS = "temp_buffers";
  @SerializedName(SERIALIZED_NAME_TEMP_BUFFERS)
  private String tempBuffers;

  public static final String SERIALIZED_NAME_WORK_MEM = "work_mem";
  @SerializedName(SERIALIZED_NAME_WORK_MEM)
  private String workMem;

  public static final String SERIALIZED_NAME_DEFAULT_TRANSACTION_ISOLATION = "default_transaction_isolation";
  @SerializedName(SERIALIZED_NAME_DEFAULT_TRANSACTION_ISOLATION)
  private String defaultTransactionIsolation;

  public static final String SERIALIZED_NAME_EFFECTIVE_CACHE_SIZE = "effective_cache_size";
  @SerializedName(SERIALIZED_NAME_EFFECTIVE_CACHE_SIZE)
  private String effectiveCacheSize;

  public static final String SERIALIZED_NAME_MAX_WAL_SIZE = "max_wal_size";
  @SerializedName(SERIALIZED_NAME_MAX_WAL_SIZE)
  private String maxWalSize;

  public static final String SERIALIZED_NAME_MIN_WAL_SIZE = "min_wal_size";
  @SerializedName(SERIALIZED_NAME_MIN_WAL_SIZE)
  private String minWalSize;

  public static final String SERIALIZED_NAME_WAL_LEVEL = "wal_level";
  @SerializedName(SERIALIZED_NAME_WAL_LEVEL)
  private String walLevel;

  public static final String SERIALIZED_NAME_MAX_REPLICATION_SLOTS = "max_replication_slots";
  @SerializedName(SERIALIZED_NAME_MAX_REPLICATION_SLOTS)
  private String maxReplicationSlots;

  public static final String SERIALIZED_NAME_MAX_WAL_SENDERS = "max_wal_senders";
  @SerializedName(SERIALIZED_NAME_MAX_WAL_SENDERS)
  private String maxWalSenders;

  public static final String SERIALIZED_NAME_MAX_WORKER_PROCESSES = "max_worker_processes";
  @SerializedName(SERIALIZED_NAME_MAX_WORKER_PROCESSES)
  private String maxWorkerProcesses;

  public static final String SERIALIZED_NAME_MAX_LOGICAL_REPLICATION_WORKERS = "max_logical_replication_workers";
  @SerializedName(SERIALIZED_NAME_MAX_LOGICAL_REPLICATION_WORKERS)
  private String maxLogicalReplicationWorkers;

  public static final String SERIALIZED_NAME_MAX_PARALLEL_MAINTENANCE_WORKERS = "max_parallel_maintenance_workers";
  @SerializedName(SERIALIZED_NAME_MAX_PARALLEL_MAINTENANCE_WORKERS)
  private String maxParallelMaintenanceWorkers;

  public static final String SERIALIZED_NAME_MAX_PARALLEL_WORKERS = "max_parallel_workers";
  @SerializedName(SERIALIZED_NAME_MAX_PARALLEL_WORKERS)
  private String maxParallelWorkers;

  public static final String SERIALIZED_NAME_MAX_PARALLEL_WORKERS_PER_GATHER = "max_parallel_workers_per_gather";
  @SerializedName(SERIALIZED_NAME_MAX_PARALLEL_WORKERS_PER_GATHER)
  private String maxParallelWorkersPerGather;

  public static final String SERIALIZED_NAME_ARRAY_NULLS = "array_nulls";
  @SerializedName(SERIALIZED_NAME_ARRAY_NULLS)
  private String arrayNulls;

  public static final String SERIALIZED_NAME_BACKEND_FLUSH_AFTER = "backend_flush_after";
  @SerializedName(SERIALIZED_NAME_BACKEND_FLUSH_AFTER)
  private String backendFlushAfter;

  public static final String SERIALIZED_NAME_BACKSLASH_QUOTE = "backslash_quote";
  @SerializedName(SERIALIZED_NAME_BACKSLASH_QUOTE)
  private String backslashQuote;

  public static final String SERIALIZED_NAME_BGWRITER_FLUSH_AFTER = "bgwriter_flush_after";
  @SerializedName(SERIALIZED_NAME_BGWRITER_FLUSH_AFTER)
  private String bgwriterFlushAfter;

  public static final String SERIALIZED_NAME_BGWRITER_LRU_MULTIPLIER = "bgwriter_lru_multiplier";
  @SerializedName(SERIALIZED_NAME_BGWRITER_LRU_MULTIPLIER)
  private String bgwriterLruMultiplier;

  public static final String SERIALIZED_NAME_DEFAULT_TRANSACTION_READ_ONLY = "default_transaction_read_only";
  @SerializedName(SERIALIZED_NAME_DEFAULT_TRANSACTION_READ_ONLY)
  private String defaultTransactionReadOnly;

  public static final String SERIALIZED_NAME_ENABLE_HASHAGG = "enable_hashagg";
  @SerializedName(SERIALIZED_NAME_ENABLE_HASHAGG)
  private String enableHashagg;

  public static final String SERIALIZED_NAME_ENABLE_HASHJOIN = "enable_hashjoin";
  @SerializedName(SERIALIZED_NAME_ENABLE_HASHJOIN)
  private String enableHashjoin;

  public static final String SERIALIZED_NAME_ENABLE_INCREMENTAL_SORT = "enable_incremental_sort";
  @SerializedName(SERIALIZED_NAME_ENABLE_INCREMENTAL_SORT)
  private String enableIncrementalSort;

  public static final String SERIALIZED_NAME_ENABLE_INDEXSCAN = "enable_indexscan";
  @SerializedName(SERIALIZED_NAME_ENABLE_INDEXSCAN)
  private String enableIndexscan;

  public static final String SERIALIZED_NAME_ENABLE_INDEXONLYSCAN = "enable_indexonlyscan";
  @SerializedName(SERIALIZED_NAME_ENABLE_INDEXONLYSCAN)
  private String enableIndexonlyscan;

  public static final String SERIALIZED_NAME_ENABLE_MATERIAL = "enable_material";
  @SerializedName(SERIALIZED_NAME_ENABLE_MATERIAL)
  private String enableMaterial;

  public static final String SERIALIZED_NAME_ENABLE_MEMOIZE = "enable_memoize";
  @SerializedName(SERIALIZED_NAME_ENABLE_MEMOIZE)
  private String enableMemoize;

  public static final String SERIALIZED_NAME_ENABLE_MERGEJOIN = "enable_mergejoin";
  @SerializedName(SERIALIZED_NAME_ENABLE_MERGEJOIN)
  private String enableMergejoin;

  public static final String SERIALIZED_NAME_ENABLE_PARALLEL_APPEND = "enable_parallel_append";
  @SerializedName(SERIALIZED_NAME_ENABLE_PARALLEL_APPEND)
  private String enableParallelAppend;

  public static final String SERIALIZED_NAME_ENABLE_PARALLEL_HASH = "enable_parallel_hash";
  @SerializedName(SERIALIZED_NAME_ENABLE_PARALLEL_HASH)
  private String enableParallelHash;

  public static final String SERIALIZED_NAME_ENABLE_PARTITION_PRUNING = "enable_partition_pruning";
  @SerializedName(SERIALIZED_NAME_ENABLE_PARTITION_PRUNING)
  private String enablePartitionPruning;

  public static final String SERIALIZED_NAME_ENABLE_PARTITIONWISE_JOIN = "enable_partitionwise_join";
  @SerializedName(SERIALIZED_NAME_ENABLE_PARTITIONWISE_JOIN)
  private String enablePartitionwiseJoin;

  public static final String SERIALIZED_NAME_ENABLE_PARTITIONWISE_AGGREGATE = "enable_partitionwise_aggregate";
  @SerializedName(SERIALIZED_NAME_ENABLE_PARTITIONWISE_AGGREGATE)
  private String enablePartitionwiseAggregate;

  public static final String SERIALIZED_NAME_ENABLE_SEQSCAN = "enable_seqscan";
  @SerializedName(SERIALIZED_NAME_ENABLE_SEQSCAN)
  private String enableSeqscan;

  public static final String SERIALIZED_NAME_ENABLE_SORT = "enable_sort";
  @SerializedName(SERIALIZED_NAME_ENABLE_SORT)
  private String enableSort;

  public static final String SERIALIZED_NAME_ENABLE_TIDSCAN = "enable_tidscan";
  @SerializedName(SERIALIZED_NAME_ENABLE_TIDSCAN)
  private String enableTidscan;

  public static final String SERIALIZED_NAME_EXIT_ON_ERROR = "exit_on_error";
  @SerializedName(SERIALIZED_NAME_EXIT_ON_ERROR)
  private String exitOnError;

  public static final String SERIALIZED_NAME_FROM_COLLAPSE_LIMIT = "from_collapse_limit";
  @SerializedName(SERIALIZED_NAME_FROM_COLLAPSE_LIMIT)
  private String fromCollapseLimit;

  public static final String SERIALIZED_NAME_JIT = "jit";
  @SerializedName(SERIALIZED_NAME_JIT)
  private String jit;

  public static final String SERIALIZED_NAME_PLAN_CACHE_MODE = "plan_cache_mode";
  @SerializedName(SERIALIZED_NAME_PLAN_CACHE_MODE)
  private String planCacheMode;

  public static final String SERIALIZED_NAME_QUOTE_ALL_IDENTIFIERS = "quote_all_identifiers";
  @SerializedName(SERIALIZED_NAME_QUOTE_ALL_IDENTIFIERS)
  private String quoteAllIdentifiers;

  public static final String SERIALIZED_NAME_STANDARD_CONFORMING_STRINGS = "standard_conforming_strings";
  @SerializedName(SERIALIZED_NAME_STANDARD_CONFORMING_STRINGS)
  private String standardConformingStrings;

  public static final String SERIALIZED_NAME_STATEMENT_TIMEOUT = "statement_timeout";
  @SerializedName(SERIALIZED_NAME_STATEMENT_TIMEOUT)
  private String statementTimeout;

  public static final String SERIALIZED_NAME_TIMEZONE = "timezone";
  @SerializedName(SERIALIZED_NAME_TIMEZONE)
  private String timezone;

  public static final String SERIALIZED_NAME_TRANSFORM_NULL_EQUALS = "transform_null_equals";
  @SerializedName(SERIALIZED_NAME_TRANSFORM_NULL_EQUALS)
  private String transformNullEquals;

  public static final String SERIALIZED_NAME_MAX_LOCKS_PER_TRANSACTION = "max_locks_per_transaction";
  @SerializedName(SERIALIZED_NAME_MAX_LOCKS_PER_TRANSACTION)
  private String maxLocksPerTransaction;

  public static final String SERIALIZED_NAME_AUTOVACUUM_VACUUM_COST_LIMIT = "autovacuum_vacuum_cost_limit";
  @SerializedName(SERIALIZED_NAME_AUTOVACUUM_VACUUM_COST_LIMIT)
  private String autovacuumVacuumCostLimit;

  public static final String SERIALIZED_NAME_CHECKPOINT_TIMEOUT = "checkpoint_timeout";
  @SerializedName(SERIALIZED_NAME_CHECKPOINT_TIMEOUT)
  private String checkpointTimeout;

  public static final String SERIALIZED_NAME_CHECKPOINT_COMPLETION_TARGET = "checkpoint_completion_target";
  @SerializedName(SERIALIZED_NAME_CHECKPOINT_COMPLETION_TARGET)
  private String checkpointCompletionTarget;

  public static final String SERIALIZED_NAME_WAL_COMPRESSION = "wal_compression";
  @SerializedName(SERIALIZED_NAME_WAL_COMPRESSION)
  private String walCompression;

  public static final String SERIALIZED_NAME_RANDOM_PAGE_COST = "random_page_cost";
  @SerializedName(SERIALIZED_NAME_RANDOM_PAGE_COST)
  private String randomPageCost;

  public static final String SERIALIZED_NAME_EFFECTIVE_IO_CONCURRENCY = "effective_io_concurrency";
  @SerializedName(SERIALIZED_NAME_EFFECTIVE_IO_CONCURRENCY)
  private String effectiveIoConcurrency;

  public static final String SERIALIZED_NAME_LOG_LOCK_WAITS = "log_lock_waits";
  @SerializedName(SERIALIZED_NAME_LOG_LOCK_WAITS)
  private String logLockWaits;

  public static final String SERIALIZED_NAME_LOG_TEMP_FILES = "log_temp_files";
  @SerializedName(SERIALIZED_NAME_LOG_TEMP_FILES)
  private String logTempFiles;

  public static final String SERIALIZED_NAME_TRACK_IO_TIMING = "track_io_timing";
  @SerializedName(SERIALIZED_NAME_TRACK_IO_TIMING)
  private String trackIoTiming;

  public static final String SERIALIZED_NAME_MAINTENANCE_WORK_MEM = "maintenance_work_mem";
  @SerializedName(SERIALIZED_NAME_MAINTENANCE_WORK_MEM)
  private String maintenanceWorkMem;

  public static final String SERIALIZED_NAME_IDLE_SESSION_TIMEOUT = "idle_session_timeout";
  @SerializedName(SERIALIZED_NAME_IDLE_SESSION_TIMEOUT)
  private String idleSessionTimeout;

  public static final String SERIALIZED_NAME_IO_METHOD = "io_method";
  @SerializedName(SERIALIZED_NAME_IO_METHOD)
  private String ioMethod;

  public static final String SERIALIZED_NAME_IO_WORKERS = "io_workers";
  @SerializedName(SERIALIZED_NAME_IO_WORKERS)
  private String ioWorkers;

  public ConfigParametersPostgres() {
  }

  public ConfigParametersPostgres maxConnections(String maxConnections) {
    
    this.maxConnections = maxConnections;
    return this;
  }

   /**
   * Максимальное количество одновременных подключений к серверу (&#x60;mysql5&#x60; | &#x60;mysql&#x60; | &#x60;mysql8_4&#x60; | &#x60;postgres&#x60; | &#x60;postgres14&#x60; | &#x60;postgres15&#x60; | &#x60;postgres16&#x60; | &#x60;postgres17&#x60; | &#x60;postgres18&#x60;).
   * @return maxConnections
  **/
  @javax.annotation.Nullable
  public String getMaxConnections() {
    return maxConnections;
  }


  public void setMaxConnections(String maxConnections) {
    this.maxConnections = maxConnections;
  }


  public ConfigParametersPostgres autovacuumAnalyzeScaleFactor(String autovacuumAnalyzeScaleFactor) {
    
    this.autovacuumAnalyzeScaleFactor = autovacuumAnalyzeScaleFactor;
    return this;
  }

   /**
   * Доля изменения строк таблицы перед запуском автоматического анализа (&#x60;postgres&#x60; | &#x60;postgres14&#x60; | &#x60;postgres15&#x60; | &#x60;postgres16&#x60; | &#x60;postgres17&#x60; | &#x60;postgres18&#x60;).
   * @return autovacuumAnalyzeScaleFactor
  **/
  @javax.annotation.Nullable
  public String getAutovacuumAnalyzeScaleFactor() {
    return autovacuumAnalyzeScaleFactor;
  }


  public void setAutovacuumAnalyzeScaleFactor(String autovacuumAnalyzeScaleFactor) {
    this.autovacuumAnalyzeScaleFactor = autovacuumAnalyzeScaleFactor;
  }


  public ConfigParametersPostgres autovacuumMaxWorkers(String autovacuumMaxWorkers) {
    
    this.autovacuumMaxWorkers = autovacuumMaxWorkers;
    return this;
  }

   /**
   * Максимальное количество процессов autovacuum, которые могут работать одновременно (&#x60;postgres&#x60; | &#x60;postgres14&#x60; | &#x60;postgres15&#x60; | &#x60;postgres16&#x60; | &#x60;postgres17&#x60; | &#x60;postgres18&#x60;).
   * @return autovacuumMaxWorkers
  **/
  @javax.annotation.Nullable
  public String getAutovacuumMaxWorkers() {
    return autovacuumMaxWorkers;
  }


  public void setAutovacuumMaxWorkers(String autovacuumMaxWorkers) {
    this.autovacuumMaxWorkers = autovacuumMaxWorkers;
  }


  public ConfigParametersPostgres autovacuumNaptime(String autovacuumNaptime) {
    
    this.autovacuumNaptime = autovacuumNaptime;
    return this;
  }

   /**
   * Интервал между запусками процессов autovacuum (&#x60;postgres&#x60; | &#x60;postgres14&#x60; | &#x60;postgres15&#x60; | &#x60;postgres16&#x60; | &#x60;postgres17&#x60; | &#x60;postgres18&#x60;).
   * @return autovacuumNaptime
  **/
  @javax.annotation.Nullable
  public String getAutovacuumNaptime() {
    return autovacuumNaptime;
  }


  public void setAutovacuumNaptime(String autovacuumNaptime) {
    this.autovacuumNaptime = autovacuumNaptime;
  }


  public ConfigParametersPostgres autovacuumVacuumInsertScaleFactor(String autovacuumVacuumInsertScaleFactor) {
    
    this.autovacuumVacuumInsertScaleFactor = autovacuumVacuumInsertScaleFactor;
    return this;
  }

   /**
   * Доля вставленных строк перед запуском vacuum для таблиц с большим количеством вставок (&#x60;postgres&#x60; | &#x60;postgres14&#x60; | &#x60;postgres15&#x60; | &#x60;postgres16&#x60; | &#x60;postgres17&#x60; | &#x60;postgres18&#x60;).
   * @return autovacuumVacuumInsertScaleFactor
  **/
  @javax.annotation.Nullable
  public String getAutovacuumVacuumInsertScaleFactor() {
    return autovacuumVacuumInsertScaleFactor;
  }


  public void setAutovacuumVacuumInsertScaleFactor(String autovacuumVacuumInsertScaleFactor) {
    this.autovacuumVacuumInsertScaleFactor = autovacuumVacuumInsertScaleFactor;
  }


  public ConfigParametersPostgres autovacuumVacuumScaleFactor(String autovacuumVacuumScaleFactor) {
    
    this.autovacuumVacuumScaleFactor = autovacuumVacuumScaleFactor;
    return this;
  }

   /**
   * Доля измененных или удаленных строк перед запуском autovacuum (&#x60;postgres&#x60; | &#x60;postgres14&#x60; | &#x60;postgres15&#x60; | &#x60;postgres16&#x60; | &#x60;postgres17&#x60; | &#x60;postgres18&#x60;).
   * @return autovacuumVacuumScaleFactor
  **/
  @javax.annotation.Nullable
  public String getAutovacuumVacuumScaleFactor() {
    return autovacuumVacuumScaleFactor;
  }


  public void setAutovacuumVacuumScaleFactor(String autovacuumVacuumScaleFactor) {
    this.autovacuumVacuumScaleFactor = autovacuumVacuumScaleFactor;
  }


  public ConfigParametersPostgres autovacuumWorkMem(String autovacuumWorkMem) {
    
    this.autovacuumWorkMem = autovacuumWorkMem;
    return this;
  }

   /**
   * Объем памяти, используемый одним процессом autovacuum (&#x60;postgres&#x60; | &#x60;postgres14&#x60; | &#x60;postgres15&#x60; | &#x60;postgres16&#x60; | &#x60;postgres17&#x60; | &#x60;postgres18&#x60;).
   * @return autovacuumWorkMem
  **/
  @javax.annotation.Nullable
  public String getAutovacuumWorkMem() {
    return autovacuumWorkMem;
  }


  public void setAutovacuumWorkMem(String autovacuumWorkMem) {
    this.autovacuumWorkMem = autovacuumWorkMem;
  }


  public ConfigParametersPostgres bgwriterDelay(String bgwriterDelay) {
    
    this.bgwriterDelay = bgwriterDelay;
    return this;
  }

   /**
   * Интервал между циклами фонового процесса записи страниц (&#x60;postgres&#x60; | &#x60;postgres14&#x60; | &#x60;postgres15&#x60; | &#x60;postgres16&#x60; | &#x60;postgres17&#x60; | &#x60;postgres18&#x60;).
   * @return bgwriterDelay
  **/
  @javax.annotation.Nullable
  public String getBgwriterDelay() {
    return bgwriterDelay;
  }


  public void setBgwriterDelay(String bgwriterDelay) {
    this.bgwriterDelay = bgwriterDelay;
  }


  public ConfigParametersPostgres bgwriterLruMaxpages(String bgwriterLruMaxpages) {
    
    this.bgwriterLruMaxpages = bgwriterLruMaxpages;
    return this;
  }

   /**
   * Максимальное количество страниц, записываемых background writer за один цикл (&#x60;postgres&#x60; | &#x60;postgres14&#x60; | &#x60;postgres15&#x60; | &#x60;postgres16&#x60; | &#x60;postgres17&#x60; | &#x60;postgres18&#x60;).
   * @return bgwriterLruMaxpages
  **/
  @javax.annotation.Nullable
  public String getBgwriterLruMaxpages() {
    return bgwriterLruMaxpages;
  }


  public void setBgwriterLruMaxpages(String bgwriterLruMaxpages) {
    this.bgwriterLruMaxpages = bgwriterLruMaxpages;
  }


  public ConfigParametersPostgres deadlockTimeout(String deadlockTimeout) {
    
    this.deadlockTimeout = deadlockTimeout;
    return this;
  }

   /**
   * Время ожидания блокировки перед проверкой взаимной блокировки (&#x60;postgres&#x60; | &#x60;postgres14&#x60; | &#x60;postgres15&#x60; | &#x60;postgres16&#x60; | &#x60;postgres17&#x60; | &#x60;postgres18&#x60;).
   * @return deadlockTimeout
  **/
  @javax.annotation.Nullable
  public String getDeadlockTimeout() {
    return deadlockTimeout;
  }


  public void setDeadlockTimeout(String deadlockTimeout) {
    this.deadlockTimeout = deadlockTimeout;
  }


  public ConfigParametersPostgres ginPendingListLimit(String ginPendingListLimit) {
    
    this.ginPendingListLimit = ginPendingListLimit;
    return this;
  }

   /**
   * Максимальный размер списка ожидающих вставок индекса GIN (&#x60;postgres&#x60; | &#x60;postgres14&#x60; | &#x60;postgres15&#x60; | &#x60;postgres16&#x60; | &#x60;postgres17&#x60; | &#x60;postgres18&#x60;).
   * @return ginPendingListLimit
  **/
  @javax.annotation.Nullable
  public String getGinPendingListLimit() {
    return ginPendingListLimit;
  }


  public void setGinPendingListLimit(String ginPendingListLimit) {
    this.ginPendingListLimit = ginPendingListLimit;
  }


  public ConfigParametersPostgres idleInTransactionSessionTimeout(String idleInTransactionSessionTimeout) {
    
    this.idleInTransactionSessionTimeout = idleInTransactionSessionTimeout;
    return this;
  }

   /**
   * Время ожидания неактивной транзакционной сессии перед завершением соединения (&#x60;postgres&#x60; | &#x60;postgres14&#x60; | &#x60;postgres15&#x60; | &#x60;postgres16&#x60; | &#x60;postgres17&#x60; | &#x60;postgres18&#x60;).
   * @return idleInTransactionSessionTimeout
  **/
  @javax.annotation.Nullable
  public String getIdleInTransactionSessionTimeout() {
    return idleInTransactionSessionTimeout;
  }


  public void setIdleInTransactionSessionTimeout(String idleInTransactionSessionTimeout) {
    this.idleInTransactionSessionTimeout = idleInTransactionSessionTimeout;
  }


  public ConfigParametersPostgres joinCollapseLimit(String joinCollapseLimit) {
    
    this.joinCollapseLimit = joinCollapseLimit;
    return this;
  }

   /**
   * Максимальное количество таблиц в JOIN, которые планировщик может переупорядочить (&#x60;postgres&#x60; | &#x60;postgres14&#x60; | &#x60;postgres15&#x60; | &#x60;postgres16&#x60; | &#x60;postgres17&#x60; | &#x60;postgres18&#x60;).
   * @return joinCollapseLimit
  **/
  @javax.annotation.Nullable
  public String getJoinCollapseLimit() {
    return joinCollapseLimit;
  }


  public void setJoinCollapseLimit(String joinCollapseLimit) {
    this.joinCollapseLimit = joinCollapseLimit;
  }


  public ConfigParametersPostgres lockTimeout(String lockTimeout) {
    
    this.lockTimeout = lockTimeout;
    return this;
  }

   /**
   * Максимальное время ожидания блокировки перед отменой запроса (&#x60;postgres&#x60; | &#x60;postgres14&#x60; | &#x60;postgres15&#x60; | &#x60;postgres16&#x60; | &#x60;postgres17&#x60; | &#x60;postgres18&#x60;).
   * @return lockTimeout
  **/
  @javax.annotation.Nullable
  public String getLockTimeout() {
    return lockTimeout;
  }


  public void setLockTimeout(String lockTimeout) {
    this.lockTimeout = lockTimeout;
  }


  public ConfigParametersPostgres maxPreparedTransactions(String maxPreparedTransactions) {
    
    this.maxPreparedTransactions = maxPreparedTransactions;
    return this;
  }

   /**
   * Максимальное количество подготовленных транзакций, которые могут существовать одновременно (&#x60;postgres&#x60; | &#x60;postgres14&#x60; | &#x60;postgres15&#x60; | &#x60;postgres16&#x60; | &#x60;postgres17&#x60; | &#x60;postgres18&#x60;).
   * @return maxPreparedTransactions
  **/
  @javax.annotation.Nullable
  public String getMaxPreparedTransactions() {
    return maxPreparedTransactions;
  }


  public void setMaxPreparedTransactions(String maxPreparedTransactions) {
    this.maxPreparedTransactions = maxPreparedTransactions;
  }


  public ConfigParametersPostgres sharedBuffers(String sharedBuffers) {
    
    this.sharedBuffers = sharedBuffers;
    return this;
  }

   /**
   * Размер общей памяти, используемой PostgreSQL для буферного кэша (&#x60;postgres&#x60; | &#x60;postgres14&#x60; | &#x60;postgres15&#x60; | &#x60;postgres16&#x60; | &#x60;postgres17&#x60; | &#x60;postgres18&#x60;).
   * @return sharedBuffers
  **/
  @javax.annotation.Nullable
  public String getSharedBuffers() {
    return sharedBuffers;
  }


  public void setSharedBuffers(String sharedBuffers) {
    this.sharedBuffers = sharedBuffers;
  }


  public ConfigParametersPostgres logMinDurationStatement(String logMinDurationStatement) {
    
    this.logMinDurationStatement = logMinDurationStatement;
    return this;
  }

   /**
   * Минимальное время выполнения запроса, после которого он записывается в журнал (&#x60;postgres&#x60; | &#x60;postgres14&#x60; | &#x60;postgres15&#x60; | &#x60;postgres16&#x60; | &#x60;postgres17&#x60; | &#x60;postgres18&#x60;).
   * @return logMinDurationStatement
  **/
  @javax.annotation.Nullable
  public String getLogMinDurationStatement() {
    return logMinDurationStatement;
  }


  public void setLogMinDurationStatement(String logMinDurationStatement) {
    this.logMinDurationStatement = logMinDurationStatement;
  }


  public ConfigParametersPostgres walBuffers(String walBuffers) {
    
    this.walBuffers = walBuffers;
    return this;
  }

   /**
   * Размер памяти, используемой для буферизации WAL-записей (&#x60;postgres&#x60; | &#x60;postgres14&#x60; | &#x60;postgres15&#x60; | &#x60;postgres16&#x60; | &#x60;postgres17&#x60; | &#x60;postgres18&#x60;).
   * @return walBuffers
  **/
  @javax.annotation.Nullable
  public String getWalBuffers() {
    return walBuffers;
  }


  public void setWalBuffers(String walBuffers) {
    this.walBuffers = walBuffers;
  }


  public ConfigParametersPostgres tempBuffers(String tempBuffers) {
    
    this.tempBuffers = tempBuffers;
    return this;
  }

   /**
   * Максимальный объем памяти для временных таблиц каждой сессии (&#x60;postgres&#x60; | &#x60;postgres14&#x60; | &#x60;postgres15&#x60; | &#x60;postgres16&#x60; | &#x60;postgres17&#x60; | &#x60;postgres18&#x60;).
   * @return tempBuffers
  **/
  @javax.annotation.Nullable
  public String getTempBuffers() {
    return tempBuffers;
  }


  public void setTempBuffers(String tempBuffers) {
    this.tempBuffers = tempBuffers;
  }


  public ConfigParametersPostgres workMem(String workMem) {
    
    this.workMem = workMem;
    return this;
  }

   /**
   * Объем памяти, используемый одной операцией сортировки или хеширования (&#x60;postgres&#x60; | &#x60;postgres14&#x60; | &#x60;postgres15&#x60; | &#x60;postgres16&#x60; | &#x60;postgres17&#x60; | &#x60;postgres18&#x60;).
   * @return workMem
  **/
  @javax.annotation.Nullable
  public String getWorkMem() {
    return workMem;
  }


  public void setWorkMem(String workMem) {
    this.workMem = workMem;
  }


  public ConfigParametersPostgres defaultTransactionIsolation(String defaultTransactionIsolation) {
    
    this.defaultTransactionIsolation = defaultTransactionIsolation;
    return this;
  }

   /**
   * Уровень изоляции транзакций по умолчанию (&#x60;postgres&#x60; | &#x60;postgres14&#x60; | &#x60;postgres15&#x60; | &#x60;postgres16&#x60; | &#x60;postgres17&#x60; | &#x60;postgres18&#x60;).
   * @return defaultTransactionIsolation
  **/
  @javax.annotation.Nullable
  public String getDefaultTransactionIsolation() {
    return defaultTransactionIsolation;
  }


  public void setDefaultTransactionIsolation(String defaultTransactionIsolation) {
    this.defaultTransactionIsolation = defaultTransactionIsolation;
  }


  public ConfigParametersPostgres effectiveCacheSize(String effectiveCacheSize) {
    
    this.effectiveCacheSize = effectiveCacheSize;
    return this;
  }

   /**
   * Оценка объема дискового кэша, доступного планировщику запросов (&#x60;postgres&#x60; | &#x60;postgres14&#x60; | &#x60;postgres15&#x60; | &#x60;postgres16&#x60; | &#x60;postgres17&#x60; | &#x60;postgres18&#x60;).
   * @return effectiveCacheSize
  **/
  @javax.annotation.Nullable
  public String getEffectiveCacheSize() {
    return effectiveCacheSize;
  }


  public void setEffectiveCacheSize(String effectiveCacheSize) {
    this.effectiveCacheSize = effectiveCacheSize;
  }


  public ConfigParametersPostgres maxWalSize(String maxWalSize) {
    
    this.maxWalSize = maxWalSize;
    return this;
  }

   /**
   * Максимальный размер WAL перед запуском контрольной точки (&#x60;postgres&#x60; | &#x60;postgres14&#x60; | &#x60;postgres15&#x60; | &#x60;postgres16&#x60; | &#x60;postgres17&#x60; | &#x60;postgres18&#x60;).
   * @return maxWalSize
  **/
  @javax.annotation.Nullable
  public String getMaxWalSize() {
    return maxWalSize;
  }


  public void setMaxWalSize(String maxWalSize) {
    this.maxWalSize = maxWalSize;
  }


  public ConfigParametersPostgres minWalSize(String minWalSize) {
    
    this.minWalSize = minWalSize;
    return this;
  }

   /**
   * Минимальный размер WAL, который сохраняется между контрольными точками (&#x60;postgres&#x60; | &#x60;postgres14&#x60; | &#x60;postgres15&#x60; | &#x60;postgres16&#x60; | &#x60;postgres17&#x60; | &#x60;postgres18&#x60;).
   * @return minWalSize
  **/
  @javax.annotation.Nullable
  public String getMinWalSize() {
    return minWalSize;
  }


  public void setMinWalSize(String minWalSize) {
    this.minWalSize = minWalSize;
  }


  public ConfigParametersPostgres walLevel(String walLevel) {
    
    this.walLevel = walLevel;
    return this;
  }

   /**
   * Уровень детализации записи WAL для восстановления и репликации (&#x60;postgres&#x60; | &#x60;postgres14&#x60; | &#x60;postgres15&#x60; | &#x60;postgres16&#x60; | &#x60;postgres17&#x60; | &#x60;postgres18&#x60;).
   * @return walLevel
  **/
  @javax.annotation.Nullable
  public String getWalLevel() {
    return walLevel;
  }


  public void setWalLevel(String walLevel) {
    this.walLevel = walLevel;
  }


  public ConfigParametersPostgres maxReplicationSlots(String maxReplicationSlots) {
    
    this.maxReplicationSlots = maxReplicationSlots;
    return this;
  }

   /**
   * Максимальное количество слотов репликации, которые могут быть созданы (&#x60;postgres&#x60; | &#x60;postgres14&#x60; | &#x60;postgres15&#x60; | &#x60;postgres16&#x60; | &#x60;postgres17&#x60; | &#x60;postgres18&#x60;).
   * @return maxReplicationSlots
  **/
  @javax.annotation.Nullable
  public String getMaxReplicationSlots() {
    return maxReplicationSlots;
  }


  public void setMaxReplicationSlots(String maxReplicationSlots) {
    this.maxReplicationSlots = maxReplicationSlots;
  }


  public ConfigParametersPostgres maxWalSenders(String maxWalSenders) {
    
    this.maxWalSenders = maxWalSenders;
    return this;
  }

   /**
   * Максимальное количество процессов отправки WAL для репликации (&#x60;postgres&#x60; | &#x60;postgres14&#x60; | &#x60;postgres15&#x60; | &#x60;postgres16&#x60; | &#x60;postgres17&#x60; | &#x60;postgres18&#x60;).
   * @return maxWalSenders
  **/
  @javax.annotation.Nullable
  public String getMaxWalSenders() {
    return maxWalSenders;
  }


  public void setMaxWalSenders(String maxWalSenders) {
    this.maxWalSenders = maxWalSenders;
  }


  public ConfigParametersPostgres maxWorkerProcesses(String maxWorkerProcesses) {
    
    this.maxWorkerProcesses = maxWorkerProcesses;
    return this;
  }

   /**
   * Максимальное количество фоновых процессов PostgreSQL (&#x60;postgres&#x60; | &#x60;postgres14&#x60; | &#x60;postgres15&#x60; | &#x60;postgres16&#x60; | &#x60;postgres17&#x60; | &#x60;postgres18&#x60;).
   * @return maxWorkerProcesses
  **/
  @javax.annotation.Nullable
  public String getMaxWorkerProcesses() {
    return maxWorkerProcesses;
  }


  public void setMaxWorkerProcesses(String maxWorkerProcesses) {
    this.maxWorkerProcesses = maxWorkerProcesses;
  }


  public ConfigParametersPostgres maxLogicalReplicationWorkers(String maxLogicalReplicationWorkers) {
    
    this.maxLogicalReplicationWorkers = maxLogicalReplicationWorkers;
    return this;
  }

   /**
   * Максимальное количество процессов логической репликации (&#x60;postgres&#x60; | &#x60;postgres14&#x60; | &#x60;postgres15&#x60; | &#x60;postgres16&#x60; | &#x60;postgres17&#x60; | &#x60;postgres18&#x60;).
   * @return maxLogicalReplicationWorkers
  **/
  @javax.annotation.Nullable
  public String getMaxLogicalReplicationWorkers() {
    return maxLogicalReplicationWorkers;
  }


  public void setMaxLogicalReplicationWorkers(String maxLogicalReplicationWorkers) {
    this.maxLogicalReplicationWorkers = maxLogicalReplicationWorkers;
  }


  public ConfigParametersPostgres maxParallelMaintenanceWorkers(String maxParallelMaintenanceWorkers) {
    
    this.maxParallelMaintenanceWorkers = maxParallelMaintenanceWorkers;
    return this;
  }

   /**
   * Максимальное количество параллельных процессов для операций обслуживания (&#x60;postgres&#x60; | &#x60;postgres14&#x60; | &#x60;postgres15&#x60; | &#x60;postgres16&#x60; | &#x60;postgres17&#x60; | &#x60;postgres18&#x60;).
   * @return maxParallelMaintenanceWorkers
  **/
  @javax.annotation.Nullable
  public String getMaxParallelMaintenanceWorkers() {
    return maxParallelMaintenanceWorkers;
  }


  public void setMaxParallelMaintenanceWorkers(String maxParallelMaintenanceWorkers) {
    this.maxParallelMaintenanceWorkers = maxParallelMaintenanceWorkers;
  }


  public ConfigParametersPostgres maxParallelWorkers(String maxParallelWorkers) {
    
    this.maxParallelWorkers = maxParallelWorkers;
    return this;
  }

   /**
   * Максимальное количество параллельных рабочих процессов для запросов (&#x60;postgres&#x60; | &#x60;postgres14&#x60; | &#x60;postgres15&#x60; | &#x60;postgres16&#x60; | &#x60;postgres17&#x60; | &#x60;postgres18&#x60;).
   * @return maxParallelWorkers
  **/
  @javax.annotation.Nullable
  public String getMaxParallelWorkers() {
    return maxParallelWorkers;
  }


  public void setMaxParallelWorkers(String maxParallelWorkers) {
    this.maxParallelWorkers = maxParallelWorkers;
  }


  public ConfigParametersPostgres maxParallelWorkersPerGather(String maxParallelWorkersPerGather) {
    
    this.maxParallelWorkersPerGather = maxParallelWorkersPerGather;
    return this;
  }

   /**
   * Максимальное количество параллельных рабочих процессов на один Gather-узел (&#x60;postgres&#x60; | &#x60;postgres14&#x60; | &#x60;postgres15&#x60; | &#x60;postgres16&#x60; | &#x60;postgres17&#x60; | &#x60;postgres18&#x60;).
   * @return maxParallelWorkersPerGather
  **/
  @javax.annotation.Nullable
  public String getMaxParallelWorkersPerGather() {
    return maxParallelWorkersPerGather;
  }


  public void setMaxParallelWorkersPerGather(String maxParallelWorkersPerGather) {
    this.maxParallelWorkersPerGather = maxParallelWorkersPerGather;
  }


  public ConfigParametersPostgres arrayNulls(String arrayNulls) {
    
    this.arrayNulls = arrayNulls;
    return this;
  }

   /**
   * Разрешение использования NULL в массивах PostgreSQL (&#x60;postgres&#x60; | &#x60;postgres14&#x60; | &#x60;postgres15&#x60; | &#x60;postgres16&#x60; | &#x60;postgres17&#x60; | &#x60;postgres18&#x60;).
   * @return arrayNulls
  **/
  @javax.annotation.Nullable
  public String getArrayNulls() {
    return arrayNulls;
  }


  public void setArrayNulls(String arrayNulls) {
    this.arrayNulls = arrayNulls;
  }


  public ConfigParametersPostgres backendFlushAfter(String backendFlushAfter) {
    
    this.backendFlushAfter = backendFlushAfter;
    return this;
  }

   /**
   * Количество страниц, после записи которых выполняется принудительная очистка данных на диск серверным процессом (&#x60;postgres&#x60; | &#x60;postgres14&#x60; | &#x60;postgres15&#x60; | &#x60;postgres16&#x60; | &#x60;postgres17&#x60; | &#x60;postgres18&#x60;).
   * @return backendFlushAfter
  **/
  @javax.annotation.Nullable
  public String getBackendFlushAfter() {
    return backendFlushAfter;
  }


  public void setBackendFlushAfter(String backendFlushAfter) {
    this.backendFlushAfter = backendFlushAfter;
  }


  public ConfigParametersPostgres backslashQuote(String backslashQuote) {
    
    this.backslashQuote = backslashQuote;
    return this;
  }

   /**
   * Управление использованием обратного слеша в строковых литералах (&#x60;postgres&#x60; | &#x60;postgres14&#x60; | &#x60;postgres15&#x60; | &#x60;postgres16&#x60; | &#x60;postgres17&#x60; | &#x60;postgres18&#x60;).
   * @return backslashQuote
  **/
  @javax.annotation.Nullable
  public String getBackslashQuote() {
    return backslashQuote;
  }


  public void setBackslashQuote(String backslashQuote) {
    this.backslashQuote = backslashQuote;
  }


  public ConfigParametersPostgres bgwriterFlushAfter(String bgwriterFlushAfter) {
    
    this.bgwriterFlushAfter = bgwriterFlushAfter;
    return this;
  }

   /**
   * Количество страниц, после которого background writer выполняет очистку данных на диск (&#x60;postgres&#x60; | &#x60;postgres14&#x60; | &#x60;postgres15&#x60; | &#x60;postgres16&#x60; | &#x60;postgres17&#x60; | &#x60;postgres18&#x60;).
   * @return bgwriterFlushAfter
  **/
  @javax.annotation.Nullable
  public String getBgwriterFlushAfter() {
    return bgwriterFlushAfter;
  }


  public void setBgwriterFlushAfter(String bgwriterFlushAfter) {
    this.bgwriterFlushAfter = bgwriterFlushAfter;
  }


  public ConfigParametersPostgres bgwriterLruMultiplier(String bgwriterLruMultiplier) {
    
    this.bgwriterLruMultiplier = bgwriterLruMultiplier;
    return this;
  }

   /**
   * Множитель количества страниц, которые background writer пытается очистить (&#x60;postgres&#x60; | &#x60;postgres14&#x60; | &#x60;postgres15&#x60; | &#x60;postgres16&#x60; | &#x60;postgres17&#x60; | &#x60;postgres18&#x60;).
   * @return bgwriterLruMultiplier
  **/
  @javax.annotation.Nullable
  public String getBgwriterLruMultiplier() {
    return bgwriterLruMultiplier;
  }


  public void setBgwriterLruMultiplier(String bgwriterLruMultiplier) {
    this.bgwriterLruMultiplier = bgwriterLruMultiplier;
  }


  public ConfigParametersPostgres defaultTransactionReadOnly(String defaultTransactionReadOnly) {
    
    this.defaultTransactionReadOnly = defaultTransactionReadOnly;
    return this;
  }

   /**
   * Определяет режим транзакций только для чтения по умолчанию (&#x60;postgres&#x60; | &#x60;postgres14&#x60; | &#x60;postgres15&#x60; | &#x60;postgres16&#x60; | &#x60;postgres17&#x60; | &#x60;postgres18&#x60;).
   * @return defaultTransactionReadOnly
  **/
  @javax.annotation.Nullable
  public String getDefaultTransactionReadOnly() {
    return defaultTransactionReadOnly;
  }


  public void setDefaultTransactionReadOnly(String defaultTransactionReadOnly) {
    this.defaultTransactionReadOnly = defaultTransactionReadOnly;
  }


  public ConfigParametersPostgres enableHashagg(String enableHashagg) {
    
    this.enableHashagg = enableHashagg;
    return this;
  }

   /**
   * Разрешение использования Hash Aggregate планировщиком запросов (&#x60;postgres&#x60; | &#x60;postgres14&#x60; | &#x60;postgres15&#x60; | &#x60;postgres16&#x60; | &#x60;postgres17&#x60; | &#x60;postgres18&#x60;).
   * @return enableHashagg
  **/
  @javax.annotation.Nullable
  public String getEnableHashagg() {
    return enableHashagg;
  }


  public void setEnableHashagg(String enableHashagg) {
    this.enableHashagg = enableHashagg;
  }


  public ConfigParametersPostgres enableHashjoin(String enableHashjoin) {
    
    this.enableHashjoin = enableHashjoin;
    return this;
  }

   /**
   * Разрешение использования Hash Join планировщиком запросов (&#x60;postgres&#x60; | &#x60;postgres14&#x60; | &#x60;postgres15&#x60; | &#x60;postgres16&#x60; | &#x60;postgres17&#x60; | &#x60;postgres18&#x60;).
   * @return enableHashjoin
  **/
  @javax.annotation.Nullable
  public String getEnableHashjoin() {
    return enableHashjoin;
  }


  public void setEnableHashjoin(String enableHashjoin) {
    this.enableHashjoin = enableHashjoin;
  }


  public ConfigParametersPostgres enableIncrementalSort(String enableIncrementalSort) {
    
    this.enableIncrementalSort = enableIncrementalSort;
    return this;
  }

   /**
   * Разрешение использования инкрементальной сортировки планировщиком (&#x60;postgres&#x60; | &#x60;postgres14&#x60; | &#x60;postgres15&#x60; | &#x60;postgres16&#x60; | &#x60;postgres17&#x60; | &#x60;postgres18&#x60;).
   * @return enableIncrementalSort
  **/
  @javax.annotation.Nullable
  public String getEnableIncrementalSort() {
    return enableIncrementalSort;
  }


  public void setEnableIncrementalSort(String enableIncrementalSort) {
    this.enableIncrementalSort = enableIncrementalSort;
  }


  public ConfigParametersPostgres enableIndexscan(String enableIndexscan) {
    
    this.enableIndexscan = enableIndexscan;
    return this;
  }

   /**
   * Разрешение использования обычного индексного сканирования (&#x60;postgres&#x60; | &#x60;postgres14&#x60; | &#x60;postgres15&#x60; | &#x60;postgres16&#x60; | &#x60;postgres17&#x60; | &#x60;postgres18&#x60;).
   * @return enableIndexscan
  **/
  @javax.annotation.Nullable
  public String getEnableIndexscan() {
    return enableIndexscan;
  }


  public void setEnableIndexscan(String enableIndexscan) {
    this.enableIndexscan = enableIndexscan;
  }


  public ConfigParametersPostgres enableIndexonlyscan(String enableIndexonlyscan) {
    
    this.enableIndexonlyscan = enableIndexonlyscan;
    return this;
  }

   /**
   * Разрешение использования index-only scan (&#x60;postgres&#x60; | &#x60;postgres14&#x60; | &#x60;postgres15&#x60; | &#x60;postgres16&#x60; | &#x60;postgres17&#x60; | &#x60;postgres18&#x60;).
   * @return enableIndexonlyscan
  **/
  @javax.annotation.Nullable
  public String getEnableIndexonlyscan() {
    return enableIndexonlyscan;
  }


  public void setEnableIndexonlyscan(String enableIndexonlyscan) {
    this.enableIndexonlyscan = enableIndexonlyscan;
  }


  public ConfigParametersPostgres enableMaterial(String enableMaterial) {
    
    this.enableMaterial = enableMaterial;
    return this;
  }

   /**
   * Разрешение использования материализации промежуточных результатов запросов (&#x60;postgres&#x60; | &#x60;postgres14&#x60; | &#x60;postgres15&#x60; | &#x60;postgres16&#x60; | &#x60;postgres17&#x60; | &#x60;postgres18&#x60;).
   * @return enableMaterial
  **/
  @javax.annotation.Nullable
  public String getEnableMaterial() {
    return enableMaterial;
  }


  public void setEnableMaterial(String enableMaterial) {
    this.enableMaterial = enableMaterial;
  }


  public ConfigParametersPostgres enableMemoize(String enableMemoize) {
    
    this.enableMemoize = enableMemoize;
    return this;
  }

   /**
   * Разрешение использования Memoize узлов планировщиком запросов (&#x60;postgres14&#x60; | &#x60;postgres15&#x60; | &#x60;postgres16&#x60; | &#x60;postgres17&#x60; | &#x60;postgres18&#x60;).
   * @return enableMemoize
  **/
  @javax.annotation.Nullable
  public String getEnableMemoize() {
    return enableMemoize;
  }


  public void setEnableMemoize(String enableMemoize) {
    this.enableMemoize = enableMemoize;
  }


  public ConfigParametersPostgres enableMergejoin(String enableMergejoin) {
    
    this.enableMergejoin = enableMergejoin;
    return this;
  }

   /**
   * Разрешение использования Merge Join планировщиком запросов (&#x60;postgres&#x60; | &#x60;postgres14&#x60; | &#x60;postgres15&#x60; | &#x60;postgres16&#x60; | &#x60;postgres17&#x60; | &#x60;postgres18&#x60;).
   * @return enableMergejoin
  **/
  @javax.annotation.Nullable
  public String getEnableMergejoin() {
    return enableMergejoin;
  }


  public void setEnableMergejoin(String enableMergejoin) {
    this.enableMergejoin = enableMergejoin;
  }


  public ConfigParametersPostgres enableParallelAppend(String enableParallelAppend) {
    
    this.enableParallelAppend = enableParallelAppend;
    return this;
  }

   /**
   * Разрешение использования параллельного Append для запросов (&#x60;postgres&#x60; | &#x60;postgres14&#x60; | &#x60;postgres15&#x60; | &#x60;postgres16&#x60; | &#x60;postgres17&#x60; | &#x60;postgres18&#x60;).
   * @return enableParallelAppend
  **/
  @javax.annotation.Nullable
  public String getEnableParallelAppend() {
    return enableParallelAppend;
  }


  public void setEnableParallelAppend(String enableParallelAppend) {
    this.enableParallelAppend = enableParallelAppend;
  }


  public ConfigParametersPostgres enableParallelHash(String enableParallelHash) {
    
    this.enableParallelHash = enableParallelHash;
    return this;
  }

   /**
   * Разрешение использования параллельных Hash операций (&#x60;postgres&#x60; | &#x60;postgres14&#x60; | &#x60;postgres15&#x60; | &#x60;postgres16&#x60; | &#x60;postgres17&#x60; | &#x60;postgres18&#x60;).
   * @return enableParallelHash
  **/
  @javax.annotation.Nullable
  public String getEnableParallelHash() {
    return enableParallelHash;
  }


  public void setEnableParallelHash(String enableParallelHash) {
    this.enableParallelHash = enableParallelHash;
  }


  public ConfigParametersPostgres enablePartitionPruning(String enablePartitionPruning) {
    
    this.enablePartitionPruning = enablePartitionPruning;
    return this;
  }

   /**
   * Разрешение удаления ненужных разделов таблицы при планировании запроса (&#x60;postgres&#x60; | &#x60;postgres14&#x60; | &#x60;postgres15&#x60; | &#x60;postgres16&#x60; | &#x60;postgres17&#x60; | &#x60;postgres18&#x60;).
   * @return enablePartitionPruning
  **/
  @javax.annotation.Nullable
  public String getEnablePartitionPruning() {
    return enablePartitionPruning;
  }


  public void setEnablePartitionPruning(String enablePartitionPruning) {
    this.enablePartitionPruning = enablePartitionPruning;
  }


  public ConfigParametersPostgres enablePartitionwiseJoin(String enablePartitionwiseJoin) {
    
    this.enablePartitionwiseJoin = enablePartitionwiseJoin;
    return this;
  }

   /**
   * Разрешение выполнения соединений между секционированными таблицами с учетом секций (&#x60;postgres&#x60; | &#x60;postgres14&#x60; | &#x60;postgres15&#x60; | &#x60;postgres16&#x60; | &#x60;postgres17&#x60; | &#x60;postgres18&#x60;).
   * @return enablePartitionwiseJoin
  **/
  @javax.annotation.Nullable
  public String getEnablePartitionwiseJoin() {
    return enablePartitionwiseJoin;
  }


  public void setEnablePartitionwiseJoin(String enablePartitionwiseJoin) {
    this.enablePartitionwiseJoin = enablePartitionwiseJoin;
  }


  public ConfigParametersPostgres enablePartitionwiseAggregate(String enablePartitionwiseAggregate) {
    
    this.enablePartitionwiseAggregate = enablePartitionwiseAggregate;
    return this;
  }

   /**
   * Разрешение выполнения агрегатных операций отдельно для секций таблиц (&#x60;postgres&#x60; | &#x60;postgres14&#x60; | &#x60;postgres15&#x60; | &#x60;postgres16&#x60; | &#x60;postgres17&#x60; | &#x60;postgres18&#x60;).
   * @return enablePartitionwiseAggregate
  **/
  @javax.annotation.Nullable
  public String getEnablePartitionwiseAggregate() {
    return enablePartitionwiseAggregate;
  }


  public void setEnablePartitionwiseAggregate(String enablePartitionwiseAggregate) {
    this.enablePartitionwiseAggregate = enablePartitionwiseAggregate;
  }


  public ConfigParametersPostgres enableSeqscan(String enableSeqscan) {
    
    this.enableSeqscan = enableSeqscan;
    return this;
  }

   /**
   * Разрешение использования последовательного сканирования таблиц планировщиком запросов (&#x60;postgres&#x60; | &#x60;postgres14&#x60; | &#x60;postgres15&#x60; | &#x60;postgres16&#x60; | &#x60;postgres17&#x60; | &#x60;postgres18&#x60;).
   * @return enableSeqscan
  **/
  @javax.annotation.Nullable
  public String getEnableSeqscan() {
    return enableSeqscan;
  }


  public void setEnableSeqscan(String enableSeqscan) {
    this.enableSeqscan = enableSeqscan;
  }


  public ConfigParametersPostgres enableSort(String enableSort) {
    
    this.enableSort = enableSort;
    return this;
  }

   /**
   * Разрешение использования операций сортировки планировщиком запросов (&#x60;postgres&#x60; | &#x60;postgres14&#x60; | &#x60;postgres15&#x60; | &#x60;postgres16&#x60; | &#x60;postgres17&#x60; | &#x60;postgres18&#x60;).
   * @return enableSort
  **/
  @javax.annotation.Nullable
  public String getEnableSort() {
    return enableSort;
  }


  public void setEnableSort(String enableSort) {
    this.enableSort = enableSort;
  }


  public ConfigParametersPostgres enableTidscan(String enableTidscan) {
    
    this.enableTidscan = enableTidscan;
    return this;
  }

   /**
   * Разрешение использования TID Scan для поиска строк по физическим идентификаторам (&#x60;postgres&#x60; | &#x60;postgres14&#x60; | &#x60;postgres15&#x60; | &#x60;postgres16&#x60; | &#x60;postgres17&#x60; | &#x60;postgres18&#x60;).
   * @return enableTidscan
  **/
  @javax.annotation.Nullable
  public String getEnableTidscan() {
    return enableTidscan;
  }


  public void setEnableTidscan(String enableTidscan) {
    this.enableTidscan = enableTidscan;
  }


  public ConfigParametersPostgres exitOnError(String exitOnError) {
    
    this.exitOnError = exitOnError;
    return this;
  }

   /**
   * Завершение сессии при возникновении ошибки SQL (&#x60;postgres&#x60; | &#x60;postgres14&#x60; | &#x60;postgres15&#x60; | &#x60;postgres16&#x60; | &#x60;postgres17&#x60; | &#x60;postgres18&#x60;).
   * @return exitOnError
  **/
  @javax.annotation.Nullable
  public String getExitOnError() {
    return exitOnError;
  }


  public void setExitOnError(String exitOnError) {
    this.exitOnError = exitOnError;
  }


  public ConfigParametersPostgres fromCollapseLimit(String fromCollapseLimit) {
    
    this.fromCollapseLimit = fromCollapseLimit;
    return this;
  }

   /**
   * Максимальное количество элементов FROM, которые планировщик может объединять при оптимизации запросов (&#x60;postgres&#x60; | &#x60;postgres14&#x60; | &#x60;postgres15&#x60; | &#x60;postgres16&#x60; | &#x60;postgres17&#x60; | &#x60;postgres18&#x60;).
   * @return fromCollapseLimit
  **/
  @javax.annotation.Nullable
  public String getFromCollapseLimit() {
    return fromCollapseLimit;
  }


  public void setFromCollapseLimit(String fromCollapseLimit) {
    this.fromCollapseLimit = fromCollapseLimit;
  }


  public ConfigParametersPostgres jit(String jit) {
    
    this.jit = jit;
    return this;
  }

   /**
   * Включение JIT-компиляции для ускорения выполнения запросов (&#x60;postgres&#x60; | &#x60;postgres14&#x60; | &#x60;postgres15&#x60; | &#x60;postgres16&#x60; | &#x60;postgres17&#x60; | &#x60;postgres18&#x60;).
   * @return jit
  **/
  @javax.annotation.Nullable
  public String getJit() {
    return jit;
  }


  public void setJit(String jit) {
    this.jit = jit;
  }


  public ConfigParametersPostgres planCacheMode(String planCacheMode) {
    
    this.planCacheMode = planCacheMode;
    return this;
  }

   /**
   * Режим использования кэша планов подготовленных запросов (&#x60;postgres&#x60; | &#x60;postgres14&#x60; | &#x60;postgres15&#x60; | &#x60;postgres16&#x60; | &#x60;postgres17&#x60; | &#x60;postgres18&#x60;).
   * @return planCacheMode
  **/
  @javax.annotation.Nullable
  public String getPlanCacheMode() {
    return planCacheMode;
  }


  public void setPlanCacheMode(String planCacheMode) {
    this.planCacheMode = planCacheMode;
  }


  public ConfigParametersPostgres quoteAllIdentifiers(String quoteAllIdentifiers) {
    
    this.quoteAllIdentifiers = quoteAllIdentifiers;
    return this;
  }

   /**
   * Всегда заключать идентификаторы в кавычки при генерации SQL (&#x60;postgres&#x60; | &#x60;postgres14&#x60; | &#x60;postgres15&#x60; | &#x60;postgres16&#x60; | &#x60;postgres17&#x60; | &#x60;postgres18&#x60;).
   * @return quoteAllIdentifiers
  **/
  @javax.annotation.Nullable
  public String getQuoteAllIdentifiers() {
    return quoteAllIdentifiers;
  }


  public void setQuoteAllIdentifiers(String quoteAllIdentifiers) {
    this.quoteAllIdentifiers = quoteAllIdentifiers;
  }


  public ConfigParametersPostgres standardConformingStrings(String standardConformingStrings) {
    
    this.standardConformingStrings = standardConformingStrings;
    return this;
  }

   /**
   * Использование стандартного поведения строковых литералов SQL (&#x60;postgres&#x60; | &#x60;postgres14&#x60; | &#x60;postgres15&#x60; | &#x60;postgres16&#x60; | &#x60;postgres17&#x60; | &#x60;postgres18&#x60;).
   * @return standardConformingStrings
  **/
  @javax.annotation.Nullable
  public String getStandardConformingStrings() {
    return standardConformingStrings;
  }


  public void setStandardConformingStrings(String standardConformingStrings) {
    this.standardConformingStrings = standardConformingStrings;
  }


  public ConfigParametersPostgres statementTimeout(String statementTimeout) {
    
    this.statementTimeout = statementTimeout;
    return this;
  }

   /**
   * Максимальное время выполнения SQL-запроса перед автоматической отменой (&#x60;postgres&#x60; | &#x60;postgres14&#x60; | &#x60;postgres15&#x60; | &#x60;postgres16&#x60; | &#x60;postgres17&#x60; | &#x60;postgres18&#x60;).
   * @return statementTimeout
  **/
  @javax.annotation.Nullable
  public String getStatementTimeout() {
    return statementTimeout;
  }


  public void setStatementTimeout(String statementTimeout) {
    this.statementTimeout = statementTimeout;
  }


  public ConfigParametersPostgres timezone(String timezone) {
    
    this.timezone = timezone;
    return this;
  }

   /**
   * Часовой пояс сервера PostgreSQL по умолчанию (&#x60;postgres&#x60; | &#x60;postgres14&#x60; | &#x60;postgres15&#x60; | &#x60;postgres16&#x60; | &#x60;postgres17&#x60; | &#x60;postgres18&#x60;).
   * @return timezone
  **/
  @javax.annotation.Nullable
  public String getTimezone() {
    return timezone;
  }


  public void setTimezone(String timezone) {
    this.timezone = timezone;
  }


  public ConfigParametersPostgres transformNullEquals(String transformNullEquals) {
    
    this.transformNullEquals = transformNullEquals;
    return this;
  }

   /**
   * Преобразование выражений вида &#x60;NULL &#x3D; NULL&#x60; в проверку IS NULL (&#x60;postgres&#x60; | &#x60;postgres14&#x60; | &#x60;postgres15&#x60; | &#x60;postgres16&#x60; | &#x60;postgres17&#x60; | &#x60;postgres18&#x60;).
   * @return transformNullEquals
  **/
  @javax.annotation.Nullable
  public String getTransformNullEquals() {
    return transformNullEquals;
  }


  public void setTransformNullEquals(String transformNullEquals) {
    this.transformNullEquals = transformNullEquals;
  }


  public ConfigParametersPostgres maxLocksPerTransaction(String maxLocksPerTransaction) {
    
    this.maxLocksPerTransaction = maxLocksPerTransaction;
    return this;
  }

   /**
   * Количество объектов, которые может блокировать одна транзакция (&#x60;postgres&#x60; | &#x60;postgres14&#x60; | &#x60;postgres15&#x60; | &#x60;postgres16&#x60; | &#x60;postgres17&#x60; | &#x60;postgres18&#x60;).
   * @return maxLocksPerTransaction
  **/
  @javax.annotation.Nullable
  public String getMaxLocksPerTransaction() {
    return maxLocksPerTransaction;
  }


  public void setMaxLocksPerTransaction(String maxLocksPerTransaction) {
    this.maxLocksPerTransaction = maxLocksPerTransaction;
  }


  public ConfigParametersPostgres autovacuumVacuumCostLimit(String autovacuumVacuumCostLimit) {
    
    this.autovacuumVacuumCostLimit = autovacuumVacuumCostLimit;
    return this;
  }

   /**
   * Лимит стоимости операций autovacuum перед приостановкой работы (&#x60;postgres&#x60; | &#x60;postgres14&#x60; | &#x60;postgres15&#x60; | &#x60;postgres16&#x60; | &#x60;postgres17&#x60; | &#x60;postgres18&#x60;).
   * @return autovacuumVacuumCostLimit
  **/
  @javax.annotation.Nullable
  public String getAutovacuumVacuumCostLimit() {
    return autovacuumVacuumCostLimit;
  }


  public void setAutovacuumVacuumCostLimit(String autovacuumVacuumCostLimit) {
    this.autovacuumVacuumCostLimit = autovacuumVacuumCostLimit;
  }


  public ConfigParametersPostgres checkpointTimeout(String checkpointTimeout) {
    
    this.checkpointTimeout = checkpointTimeout;
    return this;
  }

   /**
   * Максимальный интервал времени между автоматическими контрольными точками (&#x60;postgres&#x60; | &#x60;postgres14&#x60; | &#x60;postgres15&#x60; | &#x60;postgres16&#x60; | &#x60;postgres17&#x60; | &#x60;postgres18&#x60;).
   * @return checkpointTimeout
  **/
  @javax.annotation.Nullable
  public String getCheckpointTimeout() {
    return checkpointTimeout;
  }


  public void setCheckpointTimeout(String checkpointTimeout) {
    this.checkpointTimeout = checkpointTimeout;
  }


  public ConfigParametersPostgres checkpointCompletionTarget(String checkpointCompletionTarget) {
    
    this.checkpointCompletionTarget = checkpointCompletionTarget;
    return this;
  }

   /**
   * Доля интервала checkpoint, за которую PostgreSQL распределяет запись данных (&#x60;postgres&#x60; | &#x60;postgres14&#x60; | &#x60;postgres15&#x60; | &#x60;postgres16&#x60; | &#x60;postgres17&#x60; | &#x60;postgres18&#x60;).
   * @return checkpointCompletionTarget
  **/
  @javax.annotation.Nullable
  public String getCheckpointCompletionTarget() {
    return checkpointCompletionTarget;
  }


  public void setCheckpointCompletionTarget(String checkpointCompletionTarget) {
    this.checkpointCompletionTarget = checkpointCompletionTarget;
  }


  public ConfigParametersPostgres walCompression(String walCompression) {
    
    this.walCompression = walCompression;
    return this;
  }

   /**
   * Включение сжатия WAL-записей для уменьшения объема журнала (&#x60;postgres&#x60; | &#x60;postgres14&#x60; | &#x60;postgres15&#x60; | &#x60;postgres16&#x60; | &#x60;postgres17&#x60; | &#x60;postgres18&#x60;).
   * @return walCompression
  **/
  @javax.annotation.Nullable
  public String getWalCompression() {
    return walCompression;
  }


  public void setWalCompression(String walCompression) {
    this.walCompression = walCompression;
  }


  public ConfigParametersPostgres randomPageCost(String randomPageCost) {
    
    this.randomPageCost = randomPageCost;
    return this;
  }

   /**
   * Оценочная стоимость случайного чтения страницы для планировщика запросов (&#x60;postgres&#x60; | &#x60;postgres14&#x60; | &#x60;postgres15&#x60; | &#x60;postgres16&#x60; | &#x60;postgres17&#x60; | &#x60;postgres18&#x60;).
   * @return randomPageCost
  **/
  @javax.annotation.Nullable
  public String getRandomPageCost() {
    return randomPageCost;
  }


  public void setRandomPageCost(String randomPageCost) {
    this.randomPageCost = randomPageCost;
  }


  public ConfigParametersPostgres effectiveIoConcurrency(String effectiveIoConcurrency) {
    
    this.effectiveIoConcurrency = effectiveIoConcurrency;
    return this;
  }

   /**
   * Количество параллельных операций ввода-вывода, которые планировщик может учитывать (&#x60;postgres&#x60; | &#x60;postgres14&#x60; | &#x60;postgres15&#x60; | &#x60;postgres16&#x60; | &#x60;postgres17&#x60; | &#x60;postgres18&#x60;).
   * @return effectiveIoConcurrency
  **/
  @javax.annotation.Nullable
  public String getEffectiveIoConcurrency() {
    return effectiveIoConcurrency;
  }


  public void setEffectiveIoConcurrency(String effectiveIoConcurrency) {
    this.effectiveIoConcurrency = effectiveIoConcurrency;
  }


  public ConfigParametersPostgres logLockWaits(String logLockWaits) {
    
    this.logLockWaits = logLockWaits;
    return this;
  }

   /**
   * Включение записи в журнал информации об ожидании блокировок дольше deadlock_timeout (&#x60;postgres&#x60; | &#x60;postgres14&#x60; | &#x60;postgres15&#x60; | &#x60;postgres16&#x60; | &#x60;postgres17&#x60; | &#x60;postgres18&#x60;).
   * @return logLockWaits
  **/
  @javax.annotation.Nullable
  public String getLogLockWaits() {
    return logLockWaits;
  }


  public void setLogLockWaits(String logLockWaits) {
    this.logLockWaits = logLockWaits;
  }


  public ConfigParametersPostgres logTempFiles(String logTempFiles) {
    
    this.logTempFiles = logTempFiles;
    return this;
  }

   /**
   * Минимальный размер временных файлов, при котором они записываются в журнал (&#x60;postgres&#x60; | &#x60;postgres14&#x60; | &#x60;postgres15&#x60; | &#x60;postgres16&#x60; | &#x60;postgres17&#x60; | &#x60;postgres18&#x60;).
   * @return logTempFiles
  **/
  @javax.annotation.Nullable
  public String getLogTempFiles() {
    return logTempFiles;
  }


  public void setLogTempFiles(String logTempFiles) {
    this.logTempFiles = logTempFiles;
  }


  public ConfigParametersPostgres trackIoTiming(String trackIoTiming) {
    
    this.trackIoTiming = trackIoTiming;
    return this;
  }

   /**
   * Включение сбора статистики времени операций ввода-вывода (&#x60;postgres&#x60; | &#x60;postgres14&#x60; | &#x60;postgres15&#x60; | &#x60;postgres16&#x60; | &#x60;postgres17&#x60; | &#x60;postgres18&#x60;).
   * @return trackIoTiming
  **/
  @javax.annotation.Nullable
  public String getTrackIoTiming() {
    return trackIoTiming;
  }


  public void setTrackIoTiming(String trackIoTiming) {
    this.trackIoTiming = trackIoTiming;
  }


  public ConfigParametersPostgres maintenanceWorkMem(String maintenanceWorkMem) {
    
    this.maintenanceWorkMem = maintenanceWorkMem;
    return this;
  }

   /**
   * Максимальный объем памяти для операций обслуживания, таких как VACUUM и CREATE INDEX (&#x60;postgres&#x60; | &#x60;postgres14&#x60; | &#x60;postgres15&#x60; | &#x60;postgres16&#x60; | &#x60;postgres17&#x60; | &#x60;postgres18&#x60;).
   * @return maintenanceWorkMem
  **/
  @javax.annotation.Nullable
  public String getMaintenanceWorkMem() {
    return maintenanceWorkMem;
  }


  public void setMaintenanceWorkMem(String maintenanceWorkMem) {
    this.maintenanceWorkMem = maintenanceWorkMem;
  }


  public ConfigParametersPostgres idleSessionTimeout(String idleSessionTimeout) {
    
    this.idleSessionTimeout = idleSessionTimeout;
    return this;
  }

   /**
   * Время ожидания неактивной сессии перед автоматическим завершением соединения (&#x60;postgres14&#x60; | &#x60;postgres15&#x60; | &#x60;postgres16&#x60; | &#x60;postgres17&#x60; | &#x60;postgres18&#x60;).
   * @return idleSessionTimeout
  **/
  @javax.annotation.Nullable
  public String getIdleSessionTimeout() {
    return idleSessionTimeout;
  }


  public void setIdleSessionTimeout(String idleSessionTimeout) {
    this.idleSessionTimeout = idleSessionTimeout;
  }


  public ConfigParametersPostgres ioMethod(String ioMethod) {
    
    this.ioMethod = ioMethod;
    return this;
  }

   /**
   * Метод выполнения операций ввода-вывода PostgreSQL (&#x60;postgres18&#x60;).
   * @return ioMethod
  **/
  @javax.annotation.Nullable
  public String getIoMethod() {
    return ioMethod;
  }


  public void setIoMethod(String ioMethod) {
    this.ioMethod = ioMethod;
  }


  public ConfigParametersPostgres ioWorkers(String ioWorkers) {
    
    this.ioWorkers = ioWorkers;
    return this;
  }

   /**
   * Количество фоновых процессов для выполнения операций ввода-вывода (&#x60;postgres18&#x60;).
   * @return ioWorkers
  **/
  @javax.annotation.Nullable
  public String getIoWorkers() {
    return ioWorkers;
  }


  public void setIoWorkers(String ioWorkers) {
    this.ioWorkers = ioWorkers;
  }



  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    ConfigParametersPostgres configParametersPostgres = (ConfigParametersPostgres) o;
    return Objects.equals(this.maxConnections, configParametersPostgres.maxConnections) &&
        Objects.equals(this.autovacuumAnalyzeScaleFactor, configParametersPostgres.autovacuumAnalyzeScaleFactor) &&
        Objects.equals(this.autovacuumMaxWorkers, configParametersPostgres.autovacuumMaxWorkers) &&
        Objects.equals(this.autovacuumNaptime, configParametersPostgres.autovacuumNaptime) &&
        Objects.equals(this.autovacuumVacuumInsertScaleFactor, configParametersPostgres.autovacuumVacuumInsertScaleFactor) &&
        Objects.equals(this.autovacuumVacuumScaleFactor, configParametersPostgres.autovacuumVacuumScaleFactor) &&
        Objects.equals(this.autovacuumWorkMem, configParametersPostgres.autovacuumWorkMem) &&
        Objects.equals(this.bgwriterDelay, configParametersPostgres.bgwriterDelay) &&
        Objects.equals(this.bgwriterLruMaxpages, configParametersPostgres.bgwriterLruMaxpages) &&
        Objects.equals(this.deadlockTimeout, configParametersPostgres.deadlockTimeout) &&
        Objects.equals(this.ginPendingListLimit, configParametersPostgres.ginPendingListLimit) &&
        Objects.equals(this.idleInTransactionSessionTimeout, configParametersPostgres.idleInTransactionSessionTimeout) &&
        Objects.equals(this.joinCollapseLimit, configParametersPostgres.joinCollapseLimit) &&
        Objects.equals(this.lockTimeout, configParametersPostgres.lockTimeout) &&
        Objects.equals(this.maxPreparedTransactions, configParametersPostgres.maxPreparedTransactions) &&
        Objects.equals(this.sharedBuffers, configParametersPostgres.sharedBuffers) &&
        Objects.equals(this.logMinDurationStatement, configParametersPostgres.logMinDurationStatement) &&
        Objects.equals(this.walBuffers, configParametersPostgres.walBuffers) &&
        Objects.equals(this.tempBuffers, configParametersPostgres.tempBuffers) &&
        Objects.equals(this.workMem, configParametersPostgres.workMem) &&
        Objects.equals(this.defaultTransactionIsolation, configParametersPostgres.defaultTransactionIsolation) &&
        Objects.equals(this.effectiveCacheSize, configParametersPostgres.effectiveCacheSize) &&
        Objects.equals(this.maxWalSize, configParametersPostgres.maxWalSize) &&
        Objects.equals(this.minWalSize, configParametersPostgres.minWalSize) &&
        Objects.equals(this.walLevel, configParametersPostgres.walLevel) &&
        Objects.equals(this.maxReplicationSlots, configParametersPostgres.maxReplicationSlots) &&
        Objects.equals(this.maxWalSenders, configParametersPostgres.maxWalSenders) &&
        Objects.equals(this.maxWorkerProcesses, configParametersPostgres.maxWorkerProcesses) &&
        Objects.equals(this.maxLogicalReplicationWorkers, configParametersPostgres.maxLogicalReplicationWorkers) &&
        Objects.equals(this.maxParallelMaintenanceWorkers, configParametersPostgres.maxParallelMaintenanceWorkers) &&
        Objects.equals(this.maxParallelWorkers, configParametersPostgres.maxParallelWorkers) &&
        Objects.equals(this.maxParallelWorkersPerGather, configParametersPostgres.maxParallelWorkersPerGather) &&
        Objects.equals(this.arrayNulls, configParametersPostgres.arrayNulls) &&
        Objects.equals(this.backendFlushAfter, configParametersPostgres.backendFlushAfter) &&
        Objects.equals(this.backslashQuote, configParametersPostgres.backslashQuote) &&
        Objects.equals(this.bgwriterFlushAfter, configParametersPostgres.bgwriterFlushAfter) &&
        Objects.equals(this.bgwriterLruMultiplier, configParametersPostgres.bgwriterLruMultiplier) &&
        Objects.equals(this.defaultTransactionReadOnly, configParametersPostgres.defaultTransactionReadOnly) &&
        Objects.equals(this.enableHashagg, configParametersPostgres.enableHashagg) &&
        Objects.equals(this.enableHashjoin, configParametersPostgres.enableHashjoin) &&
        Objects.equals(this.enableIncrementalSort, configParametersPostgres.enableIncrementalSort) &&
        Objects.equals(this.enableIndexscan, configParametersPostgres.enableIndexscan) &&
        Objects.equals(this.enableIndexonlyscan, configParametersPostgres.enableIndexonlyscan) &&
        Objects.equals(this.enableMaterial, configParametersPostgres.enableMaterial) &&
        Objects.equals(this.enableMemoize, configParametersPostgres.enableMemoize) &&
        Objects.equals(this.enableMergejoin, configParametersPostgres.enableMergejoin) &&
        Objects.equals(this.enableParallelAppend, configParametersPostgres.enableParallelAppend) &&
        Objects.equals(this.enableParallelHash, configParametersPostgres.enableParallelHash) &&
        Objects.equals(this.enablePartitionPruning, configParametersPostgres.enablePartitionPruning) &&
        Objects.equals(this.enablePartitionwiseJoin, configParametersPostgres.enablePartitionwiseJoin) &&
        Objects.equals(this.enablePartitionwiseAggregate, configParametersPostgres.enablePartitionwiseAggregate) &&
        Objects.equals(this.enableSeqscan, configParametersPostgres.enableSeqscan) &&
        Objects.equals(this.enableSort, configParametersPostgres.enableSort) &&
        Objects.equals(this.enableTidscan, configParametersPostgres.enableTidscan) &&
        Objects.equals(this.exitOnError, configParametersPostgres.exitOnError) &&
        Objects.equals(this.fromCollapseLimit, configParametersPostgres.fromCollapseLimit) &&
        Objects.equals(this.jit, configParametersPostgres.jit) &&
        Objects.equals(this.planCacheMode, configParametersPostgres.planCacheMode) &&
        Objects.equals(this.quoteAllIdentifiers, configParametersPostgres.quoteAllIdentifiers) &&
        Objects.equals(this.standardConformingStrings, configParametersPostgres.standardConformingStrings) &&
        Objects.equals(this.statementTimeout, configParametersPostgres.statementTimeout) &&
        Objects.equals(this.timezone, configParametersPostgres.timezone) &&
        Objects.equals(this.transformNullEquals, configParametersPostgres.transformNullEquals) &&
        Objects.equals(this.maxLocksPerTransaction, configParametersPostgres.maxLocksPerTransaction) &&
        Objects.equals(this.autovacuumVacuumCostLimit, configParametersPostgres.autovacuumVacuumCostLimit) &&
        Objects.equals(this.checkpointTimeout, configParametersPostgres.checkpointTimeout) &&
        Objects.equals(this.checkpointCompletionTarget, configParametersPostgres.checkpointCompletionTarget) &&
        Objects.equals(this.walCompression, configParametersPostgres.walCompression) &&
        Objects.equals(this.randomPageCost, configParametersPostgres.randomPageCost) &&
        Objects.equals(this.effectiveIoConcurrency, configParametersPostgres.effectiveIoConcurrency) &&
        Objects.equals(this.logLockWaits, configParametersPostgres.logLockWaits) &&
        Objects.equals(this.logTempFiles, configParametersPostgres.logTempFiles) &&
        Objects.equals(this.trackIoTiming, configParametersPostgres.trackIoTiming) &&
        Objects.equals(this.maintenanceWorkMem, configParametersPostgres.maintenanceWorkMem) &&
        Objects.equals(this.idleSessionTimeout, configParametersPostgres.idleSessionTimeout) &&
        Objects.equals(this.ioMethod, configParametersPostgres.ioMethod) &&
        Objects.equals(this.ioWorkers, configParametersPostgres.ioWorkers);
  }

  @Override
  public int hashCode() {
    return Objects.hash(maxConnections, autovacuumAnalyzeScaleFactor, autovacuumMaxWorkers, autovacuumNaptime, autovacuumVacuumInsertScaleFactor, autovacuumVacuumScaleFactor, autovacuumWorkMem, bgwriterDelay, bgwriterLruMaxpages, deadlockTimeout, ginPendingListLimit, idleInTransactionSessionTimeout, joinCollapseLimit, lockTimeout, maxPreparedTransactions, sharedBuffers, logMinDurationStatement, walBuffers, tempBuffers, workMem, defaultTransactionIsolation, effectiveCacheSize, maxWalSize, minWalSize, walLevel, maxReplicationSlots, maxWalSenders, maxWorkerProcesses, maxLogicalReplicationWorkers, maxParallelMaintenanceWorkers, maxParallelWorkers, maxParallelWorkersPerGather, arrayNulls, backendFlushAfter, backslashQuote, bgwriterFlushAfter, bgwriterLruMultiplier, defaultTransactionReadOnly, enableHashagg, enableHashjoin, enableIncrementalSort, enableIndexscan, enableIndexonlyscan, enableMaterial, enableMemoize, enableMergejoin, enableParallelAppend, enableParallelHash, enablePartitionPruning, enablePartitionwiseJoin, enablePartitionwiseAggregate, enableSeqscan, enableSort, enableTidscan, exitOnError, fromCollapseLimit, jit, planCacheMode, quoteAllIdentifiers, standardConformingStrings, statementTimeout, timezone, transformNullEquals, maxLocksPerTransaction, autovacuumVacuumCostLimit, checkpointTimeout, checkpointCompletionTarget, walCompression, randomPageCost, effectiveIoConcurrency, logLockWaits, logTempFiles, trackIoTiming, maintenanceWorkMem, idleSessionTimeout, ioMethod, ioWorkers);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class ConfigParametersPostgres {\n");
    sb.append("    maxConnections: ").append(toIndentedString(maxConnections)).append("\n");
    sb.append("    autovacuumAnalyzeScaleFactor: ").append(toIndentedString(autovacuumAnalyzeScaleFactor)).append("\n");
    sb.append("    autovacuumMaxWorkers: ").append(toIndentedString(autovacuumMaxWorkers)).append("\n");
    sb.append("    autovacuumNaptime: ").append(toIndentedString(autovacuumNaptime)).append("\n");
    sb.append("    autovacuumVacuumInsertScaleFactor: ").append(toIndentedString(autovacuumVacuumInsertScaleFactor)).append("\n");
    sb.append("    autovacuumVacuumScaleFactor: ").append(toIndentedString(autovacuumVacuumScaleFactor)).append("\n");
    sb.append("    autovacuumWorkMem: ").append(toIndentedString(autovacuumWorkMem)).append("\n");
    sb.append("    bgwriterDelay: ").append(toIndentedString(bgwriterDelay)).append("\n");
    sb.append("    bgwriterLruMaxpages: ").append(toIndentedString(bgwriterLruMaxpages)).append("\n");
    sb.append("    deadlockTimeout: ").append(toIndentedString(deadlockTimeout)).append("\n");
    sb.append("    ginPendingListLimit: ").append(toIndentedString(ginPendingListLimit)).append("\n");
    sb.append("    idleInTransactionSessionTimeout: ").append(toIndentedString(idleInTransactionSessionTimeout)).append("\n");
    sb.append("    joinCollapseLimit: ").append(toIndentedString(joinCollapseLimit)).append("\n");
    sb.append("    lockTimeout: ").append(toIndentedString(lockTimeout)).append("\n");
    sb.append("    maxPreparedTransactions: ").append(toIndentedString(maxPreparedTransactions)).append("\n");
    sb.append("    sharedBuffers: ").append(toIndentedString(sharedBuffers)).append("\n");
    sb.append("    logMinDurationStatement: ").append(toIndentedString(logMinDurationStatement)).append("\n");
    sb.append("    walBuffers: ").append(toIndentedString(walBuffers)).append("\n");
    sb.append("    tempBuffers: ").append(toIndentedString(tempBuffers)).append("\n");
    sb.append("    workMem: ").append(toIndentedString(workMem)).append("\n");
    sb.append("    defaultTransactionIsolation: ").append(toIndentedString(defaultTransactionIsolation)).append("\n");
    sb.append("    effectiveCacheSize: ").append(toIndentedString(effectiveCacheSize)).append("\n");
    sb.append("    maxWalSize: ").append(toIndentedString(maxWalSize)).append("\n");
    sb.append("    minWalSize: ").append(toIndentedString(minWalSize)).append("\n");
    sb.append("    walLevel: ").append(toIndentedString(walLevel)).append("\n");
    sb.append("    maxReplicationSlots: ").append(toIndentedString(maxReplicationSlots)).append("\n");
    sb.append("    maxWalSenders: ").append(toIndentedString(maxWalSenders)).append("\n");
    sb.append("    maxWorkerProcesses: ").append(toIndentedString(maxWorkerProcesses)).append("\n");
    sb.append("    maxLogicalReplicationWorkers: ").append(toIndentedString(maxLogicalReplicationWorkers)).append("\n");
    sb.append("    maxParallelMaintenanceWorkers: ").append(toIndentedString(maxParallelMaintenanceWorkers)).append("\n");
    sb.append("    maxParallelWorkers: ").append(toIndentedString(maxParallelWorkers)).append("\n");
    sb.append("    maxParallelWorkersPerGather: ").append(toIndentedString(maxParallelWorkersPerGather)).append("\n");
    sb.append("    arrayNulls: ").append(toIndentedString(arrayNulls)).append("\n");
    sb.append("    backendFlushAfter: ").append(toIndentedString(backendFlushAfter)).append("\n");
    sb.append("    backslashQuote: ").append(toIndentedString(backslashQuote)).append("\n");
    sb.append("    bgwriterFlushAfter: ").append(toIndentedString(bgwriterFlushAfter)).append("\n");
    sb.append("    bgwriterLruMultiplier: ").append(toIndentedString(bgwriterLruMultiplier)).append("\n");
    sb.append("    defaultTransactionReadOnly: ").append(toIndentedString(defaultTransactionReadOnly)).append("\n");
    sb.append("    enableHashagg: ").append(toIndentedString(enableHashagg)).append("\n");
    sb.append("    enableHashjoin: ").append(toIndentedString(enableHashjoin)).append("\n");
    sb.append("    enableIncrementalSort: ").append(toIndentedString(enableIncrementalSort)).append("\n");
    sb.append("    enableIndexscan: ").append(toIndentedString(enableIndexscan)).append("\n");
    sb.append("    enableIndexonlyscan: ").append(toIndentedString(enableIndexonlyscan)).append("\n");
    sb.append("    enableMaterial: ").append(toIndentedString(enableMaterial)).append("\n");
    sb.append("    enableMemoize: ").append(toIndentedString(enableMemoize)).append("\n");
    sb.append("    enableMergejoin: ").append(toIndentedString(enableMergejoin)).append("\n");
    sb.append("    enableParallelAppend: ").append(toIndentedString(enableParallelAppend)).append("\n");
    sb.append("    enableParallelHash: ").append(toIndentedString(enableParallelHash)).append("\n");
    sb.append("    enablePartitionPruning: ").append(toIndentedString(enablePartitionPruning)).append("\n");
    sb.append("    enablePartitionwiseJoin: ").append(toIndentedString(enablePartitionwiseJoin)).append("\n");
    sb.append("    enablePartitionwiseAggregate: ").append(toIndentedString(enablePartitionwiseAggregate)).append("\n");
    sb.append("    enableSeqscan: ").append(toIndentedString(enableSeqscan)).append("\n");
    sb.append("    enableSort: ").append(toIndentedString(enableSort)).append("\n");
    sb.append("    enableTidscan: ").append(toIndentedString(enableTidscan)).append("\n");
    sb.append("    exitOnError: ").append(toIndentedString(exitOnError)).append("\n");
    sb.append("    fromCollapseLimit: ").append(toIndentedString(fromCollapseLimit)).append("\n");
    sb.append("    jit: ").append(toIndentedString(jit)).append("\n");
    sb.append("    planCacheMode: ").append(toIndentedString(planCacheMode)).append("\n");
    sb.append("    quoteAllIdentifiers: ").append(toIndentedString(quoteAllIdentifiers)).append("\n");
    sb.append("    standardConformingStrings: ").append(toIndentedString(standardConformingStrings)).append("\n");
    sb.append("    statementTimeout: ").append(toIndentedString(statementTimeout)).append("\n");
    sb.append("    timezone: ").append(toIndentedString(timezone)).append("\n");
    sb.append("    transformNullEquals: ").append(toIndentedString(transformNullEquals)).append("\n");
    sb.append("    maxLocksPerTransaction: ").append(toIndentedString(maxLocksPerTransaction)).append("\n");
    sb.append("    autovacuumVacuumCostLimit: ").append(toIndentedString(autovacuumVacuumCostLimit)).append("\n");
    sb.append("    checkpointTimeout: ").append(toIndentedString(checkpointTimeout)).append("\n");
    sb.append("    checkpointCompletionTarget: ").append(toIndentedString(checkpointCompletionTarget)).append("\n");
    sb.append("    walCompression: ").append(toIndentedString(walCompression)).append("\n");
    sb.append("    randomPageCost: ").append(toIndentedString(randomPageCost)).append("\n");
    sb.append("    effectiveIoConcurrency: ").append(toIndentedString(effectiveIoConcurrency)).append("\n");
    sb.append("    logLockWaits: ").append(toIndentedString(logLockWaits)).append("\n");
    sb.append("    logTempFiles: ").append(toIndentedString(logTempFiles)).append("\n");
    sb.append("    trackIoTiming: ").append(toIndentedString(trackIoTiming)).append("\n");
    sb.append("    maintenanceWorkMem: ").append(toIndentedString(maintenanceWorkMem)).append("\n");
    sb.append("    idleSessionTimeout: ").append(toIndentedString(idleSessionTimeout)).append("\n");
    sb.append("    ioMethod: ").append(toIndentedString(ioMethod)).append("\n");
    sb.append("    ioWorkers: ").append(toIndentedString(ioWorkers)).append("\n");
    sb.append("}");
    return sb.toString();
  }

  /**
   * Convert the given object to string with each line indented by 4 spaces
   * (except the first line).
   */
  private String toIndentedString(Object o) {
    if (o == null) {
      return "null";
    }
    return o.toString().replace("\n", "\n    ");
  }


  public static HashSet<String> openapiFields;
  public static HashSet<String> openapiRequiredFields;

  static {
    // a set of all properties/fields (JSON key names)
    openapiFields = new HashSet<String>();
    openapiFields.add("max_connections");
    openapiFields.add("autovacuum_analyze_scale_factor");
    openapiFields.add("autovacuum_max_workers");
    openapiFields.add("autovacuum_naptime");
    openapiFields.add("autovacuum_vacuum_insert_scale_factor");
    openapiFields.add("autovacuum_vacuum_scale_factor");
    openapiFields.add("autovacuum_work_mem");
    openapiFields.add("bgwriter_delay");
    openapiFields.add("bgwriter_lru_maxpages");
    openapiFields.add("deadlock_timeout");
    openapiFields.add("gin_pending_list_limit");
    openapiFields.add("idle_in_transaction_session_timeout");
    openapiFields.add("join_collapse_limit");
    openapiFields.add("lock_timeout");
    openapiFields.add("max_prepared_transactions");
    openapiFields.add("shared_buffers");
    openapiFields.add("log_min_duration_statement");
    openapiFields.add("wal_buffers");
    openapiFields.add("temp_buffers");
    openapiFields.add("work_mem");
    openapiFields.add("default_transaction_isolation");
    openapiFields.add("effective_cache_size");
    openapiFields.add("max_wal_size");
    openapiFields.add("min_wal_size");
    openapiFields.add("wal_level");
    openapiFields.add("max_replication_slots");
    openapiFields.add("max_wal_senders");
    openapiFields.add("max_worker_processes");
    openapiFields.add("max_logical_replication_workers");
    openapiFields.add("max_parallel_maintenance_workers");
    openapiFields.add("max_parallel_workers");
    openapiFields.add("max_parallel_workers_per_gather");
    openapiFields.add("array_nulls");
    openapiFields.add("backend_flush_after");
    openapiFields.add("backslash_quote");
    openapiFields.add("bgwriter_flush_after");
    openapiFields.add("bgwriter_lru_multiplier");
    openapiFields.add("default_transaction_read_only");
    openapiFields.add("enable_hashagg");
    openapiFields.add("enable_hashjoin");
    openapiFields.add("enable_incremental_sort");
    openapiFields.add("enable_indexscan");
    openapiFields.add("enable_indexonlyscan");
    openapiFields.add("enable_material");
    openapiFields.add("enable_memoize");
    openapiFields.add("enable_mergejoin");
    openapiFields.add("enable_parallel_append");
    openapiFields.add("enable_parallel_hash");
    openapiFields.add("enable_partition_pruning");
    openapiFields.add("enable_partitionwise_join");
    openapiFields.add("enable_partitionwise_aggregate");
    openapiFields.add("enable_seqscan");
    openapiFields.add("enable_sort");
    openapiFields.add("enable_tidscan");
    openapiFields.add("exit_on_error");
    openapiFields.add("from_collapse_limit");
    openapiFields.add("jit");
    openapiFields.add("plan_cache_mode");
    openapiFields.add("quote_all_identifiers");
    openapiFields.add("standard_conforming_strings");
    openapiFields.add("statement_timeout");
    openapiFields.add("timezone");
    openapiFields.add("transform_null_equals");
    openapiFields.add("max_locks_per_transaction");
    openapiFields.add("autovacuum_vacuum_cost_limit");
    openapiFields.add("checkpoint_timeout");
    openapiFields.add("checkpoint_completion_target");
    openapiFields.add("wal_compression");
    openapiFields.add("random_page_cost");
    openapiFields.add("effective_io_concurrency");
    openapiFields.add("log_lock_waits");
    openapiFields.add("log_temp_files");
    openapiFields.add("track_io_timing");
    openapiFields.add("maintenance_work_mem");
    openapiFields.add("idle_session_timeout");
    openapiFields.add("io_method");
    openapiFields.add("io_workers");

    // a set of required properties/fields (JSON key names)
    openapiRequiredFields = new HashSet<String>();
  }

 /**
  * Validates the JSON Element and throws an exception if issues found
  *
  * @param jsonElement JSON Element
  * @throws IOException if the JSON Element is invalid with respect to ConfigParametersPostgres
  */
  public static void validateJsonElement(JsonElement jsonElement) throws IOException {
      if (jsonElement == null) {
        if (!ConfigParametersPostgres.openapiRequiredFields.isEmpty()) { // has required fields but JSON element is null
          throw new IllegalArgumentException(String.format("The required field(s) %s in ConfigParametersPostgres is not found in the empty JSON string", ConfigParametersPostgres.openapiRequiredFields.toString()));
        }
      }

      Set<Entry<String, JsonElement>> entries = jsonElement.getAsJsonObject().entrySet();
      // check to see if the JSON string contains additional fields
      for (Entry<String, JsonElement> entry : entries) {
        if (!ConfigParametersPostgres.openapiFields.contains(entry.getKey())) {
          throw new IllegalArgumentException(String.format("The field `%s` in the JSON string is not defined in the `ConfigParametersPostgres` properties. JSON: %s", entry.getKey(), jsonElement.toString()));
        }
      }
        JsonObject jsonObj = jsonElement.getAsJsonObject();
      if ((jsonObj.get("max_connections") != null && !jsonObj.get("max_connections").isJsonNull()) && !jsonObj.get("max_connections").isJsonPrimitive()) {
        throw new IllegalArgumentException(String.format("Expected the field `max_connections` to be a primitive type in the JSON string but got `%s`", jsonObj.get("max_connections").toString()));
      }
      if ((jsonObj.get("autovacuum_analyze_scale_factor") != null && !jsonObj.get("autovacuum_analyze_scale_factor").isJsonNull()) && !jsonObj.get("autovacuum_analyze_scale_factor").isJsonPrimitive()) {
        throw new IllegalArgumentException(String.format("Expected the field `autovacuum_analyze_scale_factor` to be a primitive type in the JSON string but got `%s`", jsonObj.get("autovacuum_analyze_scale_factor").toString()));
      }
      if ((jsonObj.get("autovacuum_max_workers") != null && !jsonObj.get("autovacuum_max_workers").isJsonNull()) && !jsonObj.get("autovacuum_max_workers").isJsonPrimitive()) {
        throw new IllegalArgumentException(String.format("Expected the field `autovacuum_max_workers` to be a primitive type in the JSON string but got `%s`", jsonObj.get("autovacuum_max_workers").toString()));
      }
      if ((jsonObj.get("autovacuum_naptime") != null && !jsonObj.get("autovacuum_naptime").isJsonNull()) && !jsonObj.get("autovacuum_naptime").isJsonPrimitive()) {
        throw new IllegalArgumentException(String.format("Expected the field `autovacuum_naptime` to be a primitive type in the JSON string but got `%s`", jsonObj.get("autovacuum_naptime").toString()));
      }
      if ((jsonObj.get("autovacuum_vacuum_insert_scale_factor") != null && !jsonObj.get("autovacuum_vacuum_insert_scale_factor").isJsonNull()) && !jsonObj.get("autovacuum_vacuum_insert_scale_factor").isJsonPrimitive()) {
        throw new IllegalArgumentException(String.format("Expected the field `autovacuum_vacuum_insert_scale_factor` to be a primitive type in the JSON string but got `%s`", jsonObj.get("autovacuum_vacuum_insert_scale_factor").toString()));
      }
      if ((jsonObj.get("autovacuum_vacuum_scale_factor") != null && !jsonObj.get("autovacuum_vacuum_scale_factor").isJsonNull()) && !jsonObj.get("autovacuum_vacuum_scale_factor").isJsonPrimitive()) {
        throw new IllegalArgumentException(String.format("Expected the field `autovacuum_vacuum_scale_factor` to be a primitive type in the JSON string but got `%s`", jsonObj.get("autovacuum_vacuum_scale_factor").toString()));
      }
      if ((jsonObj.get("autovacuum_work_mem") != null && !jsonObj.get("autovacuum_work_mem").isJsonNull()) && !jsonObj.get("autovacuum_work_mem").isJsonPrimitive()) {
        throw new IllegalArgumentException(String.format("Expected the field `autovacuum_work_mem` to be a primitive type in the JSON string but got `%s`", jsonObj.get("autovacuum_work_mem").toString()));
      }
      if ((jsonObj.get("bgwriter_delay") != null && !jsonObj.get("bgwriter_delay").isJsonNull()) && !jsonObj.get("bgwriter_delay").isJsonPrimitive()) {
        throw new IllegalArgumentException(String.format("Expected the field `bgwriter_delay` to be a primitive type in the JSON string but got `%s`", jsonObj.get("bgwriter_delay").toString()));
      }
      if ((jsonObj.get("bgwriter_lru_maxpages") != null && !jsonObj.get("bgwriter_lru_maxpages").isJsonNull()) && !jsonObj.get("bgwriter_lru_maxpages").isJsonPrimitive()) {
        throw new IllegalArgumentException(String.format("Expected the field `bgwriter_lru_maxpages` to be a primitive type in the JSON string but got `%s`", jsonObj.get("bgwriter_lru_maxpages").toString()));
      }
      if ((jsonObj.get("deadlock_timeout") != null && !jsonObj.get("deadlock_timeout").isJsonNull()) && !jsonObj.get("deadlock_timeout").isJsonPrimitive()) {
        throw new IllegalArgumentException(String.format("Expected the field `deadlock_timeout` to be a primitive type in the JSON string but got `%s`", jsonObj.get("deadlock_timeout").toString()));
      }
      if ((jsonObj.get("gin_pending_list_limit") != null && !jsonObj.get("gin_pending_list_limit").isJsonNull()) && !jsonObj.get("gin_pending_list_limit").isJsonPrimitive()) {
        throw new IllegalArgumentException(String.format("Expected the field `gin_pending_list_limit` to be a primitive type in the JSON string but got `%s`", jsonObj.get("gin_pending_list_limit").toString()));
      }
      if ((jsonObj.get("idle_in_transaction_session_timeout") != null && !jsonObj.get("idle_in_transaction_session_timeout").isJsonNull()) && !jsonObj.get("idle_in_transaction_session_timeout").isJsonPrimitive()) {
        throw new IllegalArgumentException(String.format("Expected the field `idle_in_transaction_session_timeout` to be a primitive type in the JSON string but got `%s`", jsonObj.get("idle_in_transaction_session_timeout").toString()));
      }
      if ((jsonObj.get("join_collapse_limit") != null && !jsonObj.get("join_collapse_limit").isJsonNull()) && !jsonObj.get("join_collapse_limit").isJsonPrimitive()) {
        throw new IllegalArgumentException(String.format("Expected the field `join_collapse_limit` to be a primitive type in the JSON string but got `%s`", jsonObj.get("join_collapse_limit").toString()));
      }
      if ((jsonObj.get("lock_timeout") != null && !jsonObj.get("lock_timeout").isJsonNull()) && !jsonObj.get("lock_timeout").isJsonPrimitive()) {
        throw new IllegalArgumentException(String.format("Expected the field `lock_timeout` to be a primitive type in the JSON string but got `%s`", jsonObj.get("lock_timeout").toString()));
      }
      if ((jsonObj.get("max_prepared_transactions") != null && !jsonObj.get("max_prepared_transactions").isJsonNull()) && !jsonObj.get("max_prepared_transactions").isJsonPrimitive()) {
        throw new IllegalArgumentException(String.format("Expected the field `max_prepared_transactions` to be a primitive type in the JSON string but got `%s`", jsonObj.get("max_prepared_transactions").toString()));
      }
      if ((jsonObj.get("shared_buffers") != null && !jsonObj.get("shared_buffers").isJsonNull()) && !jsonObj.get("shared_buffers").isJsonPrimitive()) {
        throw new IllegalArgumentException(String.format("Expected the field `shared_buffers` to be a primitive type in the JSON string but got `%s`", jsonObj.get("shared_buffers").toString()));
      }
      if ((jsonObj.get("log_min_duration_statement") != null && !jsonObj.get("log_min_duration_statement").isJsonNull()) && !jsonObj.get("log_min_duration_statement").isJsonPrimitive()) {
        throw new IllegalArgumentException(String.format("Expected the field `log_min_duration_statement` to be a primitive type in the JSON string but got `%s`", jsonObj.get("log_min_duration_statement").toString()));
      }
      if ((jsonObj.get("wal_buffers") != null && !jsonObj.get("wal_buffers").isJsonNull()) && !jsonObj.get("wal_buffers").isJsonPrimitive()) {
        throw new IllegalArgumentException(String.format("Expected the field `wal_buffers` to be a primitive type in the JSON string but got `%s`", jsonObj.get("wal_buffers").toString()));
      }
      if ((jsonObj.get("temp_buffers") != null && !jsonObj.get("temp_buffers").isJsonNull()) && !jsonObj.get("temp_buffers").isJsonPrimitive()) {
        throw new IllegalArgumentException(String.format("Expected the field `temp_buffers` to be a primitive type in the JSON string but got `%s`", jsonObj.get("temp_buffers").toString()));
      }
      if ((jsonObj.get("work_mem") != null && !jsonObj.get("work_mem").isJsonNull()) && !jsonObj.get("work_mem").isJsonPrimitive()) {
        throw new IllegalArgumentException(String.format("Expected the field `work_mem` to be a primitive type in the JSON string but got `%s`", jsonObj.get("work_mem").toString()));
      }
      if ((jsonObj.get("default_transaction_isolation") != null && !jsonObj.get("default_transaction_isolation").isJsonNull()) && !jsonObj.get("default_transaction_isolation").isJsonPrimitive()) {
        throw new IllegalArgumentException(String.format("Expected the field `default_transaction_isolation` to be a primitive type in the JSON string but got `%s`", jsonObj.get("default_transaction_isolation").toString()));
      }
      if ((jsonObj.get("effective_cache_size") != null && !jsonObj.get("effective_cache_size").isJsonNull()) && !jsonObj.get("effective_cache_size").isJsonPrimitive()) {
        throw new IllegalArgumentException(String.format("Expected the field `effective_cache_size` to be a primitive type in the JSON string but got `%s`", jsonObj.get("effective_cache_size").toString()));
      }
      if ((jsonObj.get("max_wal_size") != null && !jsonObj.get("max_wal_size").isJsonNull()) && !jsonObj.get("max_wal_size").isJsonPrimitive()) {
        throw new IllegalArgumentException(String.format("Expected the field `max_wal_size` to be a primitive type in the JSON string but got `%s`", jsonObj.get("max_wal_size").toString()));
      }
      if ((jsonObj.get("min_wal_size") != null && !jsonObj.get("min_wal_size").isJsonNull()) && !jsonObj.get("min_wal_size").isJsonPrimitive()) {
        throw new IllegalArgumentException(String.format("Expected the field `min_wal_size` to be a primitive type in the JSON string but got `%s`", jsonObj.get("min_wal_size").toString()));
      }
      if ((jsonObj.get("wal_level") != null && !jsonObj.get("wal_level").isJsonNull()) && !jsonObj.get("wal_level").isJsonPrimitive()) {
        throw new IllegalArgumentException(String.format("Expected the field `wal_level` to be a primitive type in the JSON string but got `%s`", jsonObj.get("wal_level").toString()));
      }
      if ((jsonObj.get("max_replication_slots") != null && !jsonObj.get("max_replication_slots").isJsonNull()) && !jsonObj.get("max_replication_slots").isJsonPrimitive()) {
        throw new IllegalArgumentException(String.format("Expected the field `max_replication_slots` to be a primitive type in the JSON string but got `%s`", jsonObj.get("max_replication_slots").toString()));
      }
      if ((jsonObj.get("max_wal_senders") != null && !jsonObj.get("max_wal_senders").isJsonNull()) && !jsonObj.get("max_wal_senders").isJsonPrimitive()) {
        throw new IllegalArgumentException(String.format("Expected the field `max_wal_senders` to be a primitive type in the JSON string but got `%s`", jsonObj.get("max_wal_senders").toString()));
      }
      if ((jsonObj.get("max_worker_processes") != null && !jsonObj.get("max_worker_processes").isJsonNull()) && !jsonObj.get("max_worker_processes").isJsonPrimitive()) {
        throw new IllegalArgumentException(String.format("Expected the field `max_worker_processes` to be a primitive type in the JSON string but got `%s`", jsonObj.get("max_worker_processes").toString()));
      }
      if ((jsonObj.get("max_logical_replication_workers") != null && !jsonObj.get("max_logical_replication_workers").isJsonNull()) && !jsonObj.get("max_logical_replication_workers").isJsonPrimitive()) {
        throw new IllegalArgumentException(String.format("Expected the field `max_logical_replication_workers` to be a primitive type in the JSON string but got `%s`", jsonObj.get("max_logical_replication_workers").toString()));
      }
      if ((jsonObj.get("max_parallel_maintenance_workers") != null && !jsonObj.get("max_parallel_maintenance_workers").isJsonNull()) && !jsonObj.get("max_parallel_maintenance_workers").isJsonPrimitive()) {
        throw new IllegalArgumentException(String.format("Expected the field `max_parallel_maintenance_workers` to be a primitive type in the JSON string but got `%s`", jsonObj.get("max_parallel_maintenance_workers").toString()));
      }
      if ((jsonObj.get("max_parallel_workers") != null && !jsonObj.get("max_parallel_workers").isJsonNull()) && !jsonObj.get("max_parallel_workers").isJsonPrimitive()) {
        throw new IllegalArgumentException(String.format("Expected the field `max_parallel_workers` to be a primitive type in the JSON string but got `%s`", jsonObj.get("max_parallel_workers").toString()));
      }
      if ((jsonObj.get("max_parallel_workers_per_gather") != null && !jsonObj.get("max_parallel_workers_per_gather").isJsonNull()) && !jsonObj.get("max_parallel_workers_per_gather").isJsonPrimitive()) {
        throw new IllegalArgumentException(String.format("Expected the field `max_parallel_workers_per_gather` to be a primitive type in the JSON string but got `%s`", jsonObj.get("max_parallel_workers_per_gather").toString()));
      }
      if ((jsonObj.get("array_nulls") != null && !jsonObj.get("array_nulls").isJsonNull()) && !jsonObj.get("array_nulls").isJsonPrimitive()) {
        throw new IllegalArgumentException(String.format("Expected the field `array_nulls` to be a primitive type in the JSON string but got `%s`", jsonObj.get("array_nulls").toString()));
      }
      if ((jsonObj.get("backend_flush_after") != null && !jsonObj.get("backend_flush_after").isJsonNull()) && !jsonObj.get("backend_flush_after").isJsonPrimitive()) {
        throw new IllegalArgumentException(String.format("Expected the field `backend_flush_after` to be a primitive type in the JSON string but got `%s`", jsonObj.get("backend_flush_after").toString()));
      }
      if ((jsonObj.get("backslash_quote") != null && !jsonObj.get("backslash_quote").isJsonNull()) && !jsonObj.get("backslash_quote").isJsonPrimitive()) {
        throw new IllegalArgumentException(String.format("Expected the field `backslash_quote` to be a primitive type in the JSON string but got `%s`", jsonObj.get("backslash_quote").toString()));
      }
      if ((jsonObj.get("bgwriter_flush_after") != null && !jsonObj.get("bgwriter_flush_after").isJsonNull()) && !jsonObj.get("bgwriter_flush_after").isJsonPrimitive()) {
        throw new IllegalArgumentException(String.format("Expected the field `bgwriter_flush_after` to be a primitive type in the JSON string but got `%s`", jsonObj.get("bgwriter_flush_after").toString()));
      }
      if ((jsonObj.get("bgwriter_lru_multiplier") != null && !jsonObj.get("bgwriter_lru_multiplier").isJsonNull()) && !jsonObj.get("bgwriter_lru_multiplier").isJsonPrimitive()) {
        throw new IllegalArgumentException(String.format("Expected the field `bgwriter_lru_multiplier` to be a primitive type in the JSON string but got `%s`", jsonObj.get("bgwriter_lru_multiplier").toString()));
      }
      if ((jsonObj.get("default_transaction_read_only") != null && !jsonObj.get("default_transaction_read_only").isJsonNull()) && !jsonObj.get("default_transaction_read_only").isJsonPrimitive()) {
        throw new IllegalArgumentException(String.format("Expected the field `default_transaction_read_only` to be a primitive type in the JSON string but got `%s`", jsonObj.get("default_transaction_read_only").toString()));
      }
      if ((jsonObj.get("enable_hashagg") != null && !jsonObj.get("enable_hashagg").isJsonNull()) && !jsonObj.get("enable_hashagg").isJsonPrimitive()) {
        throw new IllegalArgumentException(String.format("Expected the field `enable_hashagg` to be a primitive type in the JSON string but got `%s`", jsonObj.get("enable_hashagg").toString()));
      }
      if ((jsonObj.get("enable_hashjoin") != null && !jsonObj.get("enable_hashjoin").isJsonNull()) && !jsonObj.get("enable_hashjoin").isJsonPrimitive()) {
        throw new IllegalArgumentException(String.format("Expected the field `enable_hashjoin` to be a primitive type in the JSON string but got `%s`", jsonObj.get("enable_hashjoin").toString()));
      }
      if ((jsonObj.get("enable_incremental_sort") != null && !jsonObj.get("enable_incremental_sort").isJsonNull()) && !jsonObj.get("enable_incremental_sort").isJsonPrimitive()) {
        throw new IllegalArgumentException(String.format("Expected the field `enable_incremental_sort` to be a primitive type in the JSON string but got `%s`", jsonObj.get("enable_incremental_sort").toString()));
      }
      if ((jsonObj.get("enable_indexscan") != null && !jsonObj.get("enable_indexscan").isJsonNull()) && !jsonObj.get("enable_indexscan").isJsonPrimitive()) {
        throw new IllegalArgumentException(String.format("Expected the field `enable_indexscan` to be a primitive type in the JSON string but got `%s`", jsonObj.get("enable_indexscan").toString()));
      }
      if ((jsonObj.get("enable_indexonlyscan") != null && !jsonObj.get("enable_indexonlyscan").isJsonNull()) && !jsonObj.get("enable_indexonlyscan").isJsonPrimitive()) {
        throw new IllegalArgumentException(String.format("Expected the field `enable_indexonlyscan` to be a primitive type in the JSON string but got `%s`", jsonObj.get("enable_indexonlyscan").toString()));
      }
      if ((jsonObj.get("enable_material") != null && !jsonObj.get("enable_material").isJsonNull()) && !jsonObj.get("enable_material").isJsonPrimitive()) {
        throw new IllegalArgumentException(String.format("Expected the field `enable_material` to be a primitive type in the JSON string but got `%s`", jsonObj.get("enable_material").toString()));
      }
      if ((jsonObj.get("enable_memoize") != null && !jsonObj.get("enable_memoize").isJsonNull()) && !jsonObj.get("enable_memoize").isJsonPrimitive()) {
        throw new IllegalArgumentException(String.format("Expected the field `enable_memoize` to be a primitive type in the JSON string but got `%s`", jsonObj.get("enable_memoize").toString()));
      }
      if ((jsonObj.get("enable_mergejoin") != null && !jsonObj.get("enable_mergejoin").isJsonNull()) && !jsonObj.get("enable_mergejoin").isJsonPrimitive()) {
        throw new IllegalArgumentException(String.format("Expected the field `enable_mergejoin` to be a primitive type in the JSON string but got `%s`", jsonObj.get("enable_mergejoin").toString()));
      }
      if ((jsonObj.get("enable_parallel_append") != null && !jsonObj.get("enable_parallel_append").isJsonNull()) && !jsonObj.get("enable_parallel_append").isJsonPrimitive()) {
        throw new IllegalArgumentException(String.format("Expected the field `enable_parallel_append` to be a primitive type in the JSON string but got `%s`", jsonObj.get("enable_parallel_append").toString()));
      }
      if ((jsonObj.get("enable_parallel_hash") != null && !jsonObj.get("enable_parallel_hash").isJsonNull()) && !jsonObj.get("enable_parallel_hash").isJsonPrimitive()) {
        throw new IllegalArgumentException(String.format("Expected the field `enable_parallel_hash` to be a primitive type in the JSON string but got `%s`", jsonObj.get("enable_parallel_hash").toString()));
      }
      if ((jsonObj.get("enable_partition_pruning") != null && !jsonObj.get("enable_partition_pruning").isJsonNull()) && !jsonObj.get("enable_partition_pruning").isJsonPrimitive()) {
        throw new IllegalArgumentException(String.format("Expected the field `enable_partition_pruning` to be a primitive type in the JSON string but got `%s`", jsonObj.get("enable_partition_pruning").toString()));
      }
      if ((jsonObj.get("enable_partitionwise_join") != null && !jsonObj.get("enable_partitionwise_join").isJsonNull()) && !jsonObj.get("enable_partitionwise_join").isJsonPrimitive()) {
        throw new IllegalArgumentException(String.format("Expected the field `enable_partitionwise_join` to be a primitive type in the JSON string but got `%s`", jsonObj.get("enable_partitionwise_join").toString()));
      }
      if ((jsonObj.get("enable_partitionwise_aggregate") != null && !jsonObj.get("enable_partitionwise_aggregate").isJsonNull()) && !jsonObj.get("enable_partitionwise_aggregate").isJsonPrimitive()) {
        throw new IllegalArgumentException(String.format("Expected the field `enable_partitionwise_aggregate` to be a primitive type in the JSON string but got `%s`", jsonObj.get("enable_partitionwise_aggregate").toString()));
      }
      if ((jsonObj.get("enable_seqscan") != null && !jsonObj.get("enable_seqscan").isJsonNull()) && !jsonObj.get("enable_seqscan").isJsonPrimitive()) {
        throw new IllegalArgumentException(String.format("Expected the field `enable_seqscan` to be a primitive type in the JSON string but got `%s`", jsonObj.get("enable_seqscan").toString()));
      }
      if ((jsonObj.get("enable_sort") != null && !jsonObj.get("enable_sort").isJsonNull()) && !jsonObj.get("enable_sort").isJsonPrimitive()) {
        throw new IllegalArgumentException(String.format("Expected the field `enable_sort` to be a primitive type in the JSON string but got `%s`", jsonObj.get("enable_sort").toString()));
      }
      if ((jsonObj.get("enable_tidscan") != null && !jsonObj.get("enable_tidscan").isJsonNull()) && !jsonObj.get("enable_tidscan").isJsonPrimitive()) {
        throw new IllegalArgumentException(String.format("Expected the field `enable_tidscan` to be a primitive type in the JSON string but got `%s`", jsonObj.get("enable_tidscan").toString()));
      }
      if ((jsonObj.get("exit_on_error") != null && !jsonObj.get("exit_on_error").isJsonNull()) && !jsonObj.get("exit_on_error").isJsonPrimitive()) {
        throw new IllegalArgumentException(String.format("Expected the field `exit_on_error` to be a primitive type in the JSON string but got `%s`", jsonObj.get("exit_on_error").toString()));
      }
      if ((jsonObj.get("from_collapse_limit") != null && !jsonObj.get("from_collapse_limit").isJsonNull()) && !jsonObj.get("from_collapse_limit").isJsonPrimitive()) {
        throw new IllegalArgumentException(String.format("Expected the field `from_collapse_limit` to be a primitive type in the JSON string but got `%s`", jsonObj.get("from_collapse_limit").toString()));
      }
      if ((jsonObj.get("jit") != null && !jsonObj.get("jit").isJsonNull()) && !jsonObj.get("jit").isJsonPrimitive()) {
        throw new IllegalArgumentException(String.format("Expected the field `jit` to be a primitive type in the JSON string but got `%s`", jsonObj.get("jit").toString()));
      }
      if ((jsonObj.get("plan_cache_mode") != null && !jsonObj.get("plan_cache_mode").isJsonNull()) && !jsonObj.get("plan_cache_mode").isJsonPrimitive()) {
        throw new IllegalArgumentException(String.format("Expected the field `plan_cache_mode` to be a primitive type in the JSON string but got `%s`", jsonObj.get("plan_cache_mode").toString()));
      }
      if ((jsonObj.get("quote_all_identifiers") != null && !jsonObj.get("quote_all_identifiers").isJsonNull()) && !jsonObj.get("quote_all_identifiers").isJsonPrimitive()) {
        throw new IllegalArgumentException(String.format("Expected the field `quote_all_identifiers` to be a primitive type in the JSON string but got `%s`", jsonObj.get("quote_all_identifiers").toString()));
      }
      if ((jsonObj.get("standard_conforming_strings") != null && !jsonObj.get("standard_conforming_strings").isJsonNull()) && !jsonObj.get("standard_conforming_strings").isJsonPrimitive()) {
        throw new IllegalArgumentException(String.format("Expected the field `standard_conforming_strings` to be a primitive type in the JSON string but got `%s`", jsonObj.get("standard_conforming_strings").toString()));
      }
      if ((jsonObj.get("statement_timeout") != null && !jsonObj.get("statement_timeout").isJsonNull()) && !jsonObj.get("statement_timeout").isJsonPrimitive()) {
        throw new IllegalArgumentException(String.format("Expected the field `statement_timeout` to be a primitive type in the JSON string but got `%s`", jsonObj.get("statement_timeout").toString()));
      }
      if ((jsonObj.get("timezone") != null && !jsonObj.get("timezone").isJsonNull()) && !jsonObj.get("timezone").isJsonPrimitive()) {
        throw new IllegalArgumentException(String.format("Expected the field `timezone` to be a primitive type in the JSON string but got `%s`", jsonObj.get("timezone").toString()));
      }
      if ((jsonObj.get("transform_null_equals") != null && !jsonObj.get("transform_null_equals").isJsonNull()) && !jsonObj.get("transform_null_equals").isJsonPrimitive()) {
        throw new IllegalArgumentException(String.format("Expected the field `transform_null_equals` to be a primitive type in the JSON string but got `%s`", jsonObj.get("transform_null_equals").toString()));
      }
      if ((jsonObj.get("max_locks_per_transaction") != null && !jsonObj.get("max_locks_per_transaction").isJsonNull()) && !jsonObj.get("max_locks_per_transaction").isJsonPrimitive()) {
        throw new IllegalArgumentException(String.format("Expected the field `max_locks_per_transaction` to be a primitive type in the JSON string but got `%s`", jsonObj.get("max_locks_per_transaction").toString()));
      }
      if ((jsonObj.get("autovacuum_vacuum_cost_limit") != null && !jsonObj.get("autovacuum_vacuum_cost_limit").isJsonNull()) && !jsonObj.get("autovacuum_vacuum_cost_limit").isJsonPrimitive()) {
        throw new IllegalArgumentException(String.format("Expected the field `autovacuum_vacuum_cost_limit` to be a primitive type in the JSON string but got `%s`", jsonObj.get("autovacuum_vacuum_cost_limit").toString()));
      }
      if ((jsonObj.get("checkpoint_timeout") != null && !jsonObj.get("checkpoint_timeout").isJsonNull()) && !jsonObj.get("checkpoint_timeout").isJsonPrimitive()) {
        throw new IllegalArgumentException(String.format("Expected the field `checkpoint_timeout` to be a primitive type in the JSON string but got `%s`", jsonObj.get("checkpoint_timeout").toString()));
      }
      if ((jsonObj.get("checkpoint_completion_target") != null && !jsonObj.get("checkpoint_completion_target").isJsonNull()) && !jsonObj.get("checkpoint_completion_target").isJsonPrimitive()) {
        throw new IllegalArgumentException(String.format("Expected the field `checkpoint_completion_target` to be a primitive type in the JSON string but got `%s`", jsonObj.get("checkpoint_completion_target").toString()));
      }
      if ((jsonObj.get("wal_compression") != null && !jsonObj.get("wal_compression").isJsonNull()) && !jsonObj.get("wal_compression").isJsonPrimitive()) {
        throw new IllegalArgumentException(String.format("Expected the field `wal_compression` to be a primitive type in the JSON string but got `%s`", jsonObj.get("wal_compression").toString()));
      }
      if ((jsonObj.get("random_page_cost") != null && !jsonObj.get("random_page_cost").isJsonNull()) && !jsonObj.get("random_page_cost").isJsonPrimitive()) {
        throw new IllegalArgumentException(String.format("Expected the field `random_page_cost` to be a primitive type in the JSON string but got `%s`", jsonObj.get("random_page_cost").toString()));
      }
      if ((jsonObj.get("effective_io_concurrency") != null && !jsonObj.get("effective_io_concurrency").isJsonNull()) && !jsonObj.get("effective_io_concurrency").isJsonPrimitive()) {
        throw new IllegalArgumentException(String.format("Expected the field `effective_io_concurrency` to be a primitive type in the JSON string but got `%s`", jsonObj.get("effective_io_concurrency").toString()));
      }
      if ((jsonObj.get("log_lock_waits") != null && !jsonObj.get("log_lock_waits").isJsonNull()) && !jsonObj.get("log_lock_waits").isJsonPrimitive()) {
        throw new IllegalArgumentException(String.format("Expected the field `log_lock_waits` to be a primitive type in the JSON string but got `%s`", jsonObj.get("log_lock_waits").toString()));
      }
      if ((jsonObj.get("log_temp_files") != null && !jsonObj.get("log_temp_files").isJsonNull()) && !jsonObj.get("log_temp_files").isJsonPrimitive()) {
        throw new IllegalArgumentException(String.format("Expected the field `log_temp_files` to be a primitive type in the JSON string but got `%s`", jsonObj.get("log_temp_files").toString()));
      }
      if ((jsonObj.get("track_io_timing") != null && !jsonObj.get("track_io_timing").isJsonNull()) && !jsonObj.get("track_io_timing").isJsonPrimitive()) {
        throw new IllegalArgumentException(String.format("Expected the field `track_io_timing` to be a primitive type in the JSON string but got `%s`", jsonObj.get("track_io_timing").toString()));
      }
      if ((jsonObj.get("maintenance_work_mem") != null && !jsonObj.get("maintenance_work_mem").isJsonNull()) && !jsonObj.get("maintenance_work_mem").isJsonPrimitive()) {
        throw new IllegalArgumentException(String.format("Expected the field `maintenance_work_mem` to be a primitive type in the JSON string but got `%s`", jsonObj.get("maintenance_work_mem").toString()));
      }
      if ((jsonObj.get("idle_session_timeout") != null && !jsonObj.get("idle_session_timeout").isJsonNull()) && !jsonObj.get("idle_session_timeout").isJsonPrimitive()) {
        throw new IllegalArgumentException(String.format("Expected the field `idle_session_timeout` to be a primitive type in the JSON string but got `%s`", jsonObj.get("idle_session_timeout").toString()));
      }
      if ((jsonObj.get("io_method") != null && !jsonObj.get("io_method").isJsonNull()) && !jsonObj.get("io_method").isJsonPrimitive()) {
        throw new IllegalArgumentException(String.format("Expected the field `io_method` to be a primitive type in the JSON string but got `%s`", jsonObj.get("io_method").toString()));
      }
      if ((jsonObj.get("io_workers") != null && !jsonObj.get("io_workers").isJsonNull()) && !jsonObj.get("io_workers").isJsonPrimitive()) {
        throw new IllegalArgumentException(String.format("Expected the field `io_workers` to be a primitive type in the JSON string but got `%s`", jsonObj.get("io_workers").toString()));
      }
  }

  public static class CustomTypeAdapterFactory implements TypeAdapterFactory {
    @SuppressWarnings("unchecked")
    @Override
    public <T> TypeAdapter<T> create(Gson gson, TypeToken<T> type) {
       if (!ConfigParametersPostgres.class.isAssignableFrom(type.getRawType())) {
         return null; // this class only serializes 'ConfigParametersPostgres' and its subtypes
       }
       final TypeAdapter<JsonElement> elementAdapter = gson.getAdapter(JsonElement.class);
       final TypeAdapter<ConfigParametersPostgres> thisAdapter
                        = gson.getDelegateAdapter(this, TypeToken.get(ConfigParametersPostgres.class));

       return (TypeAdapter<T>) new TypeAdapter<ConfigParametersPostgres>() {
           @Override
           public void write(JsonWriter out, ConfigParametersPostgres value) throws IOException {
             JsonObject obj = thisAdapter.toJsonTree(value).getAsJsonObject();
             elementAdapter.write(out, obj);
           }

           @Override
           public ConfigParametersPostgres read(JsonReader in) throws IOException {
             JsonElement jsonElement = elementAdapter.read(in);
             validateJsonElement(jsonElement);
             return thisAdapter.fromJsonTree(jsonElement);
           }

       }.nullSafe();
    }
  }

 /**
  * Create an instance of ConfigParametersPostgres given an JSON string
  *
  * @param jsonString JSON string
  * @return An instance of ConfigParametersPostgres
  * @throws IOException if the JSON string is invalid with respect to ConfigParametersPostgres
  */
  public static ConfigParametersPostgres fromJson(String jsonString) throws IOException {
    return JSON.getGson().fromJson(jsonString, ConfigParametersPostgres.class);
  }

 /**
  * Convert an instance of ConfigParametersPostgres to an JSON string
  *
  * @return JSON string
  */
  public String toJson() {
    return JSON.getGson().toJson(this);
  }
}

