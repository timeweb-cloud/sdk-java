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
 * Параметры MySQL (&#x60;mysql5&#x60; | &#x60;mysql&#x60; | &#x60;mysql8_4&#x60;)
 */
@javax.annotation.Generated(value = "org.openapitools.codegen.languages.JavaClientCodegen", date = "2026-07-22T11:19:14.654686Z[Etc/UTC]")
public class ConfigParametersMysql {
  public static final String SERIALIZED_NAME_JOIN_BUFFER_SIZE = "join_buffer_size";
  @SerializedName(SERIALIZED_NAME_JOIN_BUFFER_SIZE)
  private String joinBufferSize;

  public static final String SERIALIZED_NAME_MAX_CONNECTIONS = "max_connections";
  @SerializedName(SERIALIZED_NAME_MAX_CONNECTIONS)
  private String maxConnections;

  public static final String SERIALIZED_NAME_SORT_BUFFER_SIZE = "sort_buffer_size";
  @SerializedName(SERIALIZED_NAME_SORT_BUFFER_SIZE)
  private String sortBufferSize;

  public static final String SERIALIZED_NAME_THREAD_CACHE_SIZE = "thread_cache_size";
  @SerializedName(SERIALIZED_NAME_THREAD_CACHE_SIZE)
  private String threadCacheSize;

  public static final String SERIALIZED_NAME_INNODB_BUFFER_POOL_SIZE = "innodb_buffer_pool_size";
  @SerializedName(SERIALIZED_NAME_INNODB_BUFFER_POOL_SIZE)
  private String innodbBufferPoolSize;

  public static final String SERIALIZED_NAME_AUTO_INCREMENT_INCREMENT = "auto_increment_increment";
  @SerializedName(SERIALIZED_NAME_AUTO_INCREMENT_INCREMENT)
  private String autoIncrementIncrement;

  public static final String SERIALIZED_NAME_AUTO_INCREMENT_OFFSET = "auto_increment_offset";
  @SerializedName(SERIALIZED_NAME_AUTO_INCREMENT_OFFSET)
  private String autoIncrementOffset;

  public static final String SERIALIZED_NAME_INNODB_IO_CAPACITY = "innodb_io_capacity";
  @SerializedName(SERIALIZED_NAME_INNODB_IO_CAPACITY)
  private String innodbIoCapacity;

  public static final String SERIALIZED_NAME_INNODB_PURGE_THREADS = "innodb_purge_threads";
  @SerializedName(SERIALIZED_NAME_INNODB_PURGE_THREADS)
  private String innodbPurgeThreads;

  public static final String SERIALIZED_NAME_INNODB_READ_IO_THREADS = "innodb_read_io_threads";
  @SerializedName(SERIALIZED_NAME_INNODB_READ_IO_THREADS)
  private String innodbReadIoThreads;

  public static final String SERIALIZED_NAME_INNODB_THREAD_CONCURRENCY = "innodb_thread_concurrency";
  @SerializedName(SERIALIZED_NAME_INNODB_THREAD_CONCURRENCY)
  private String innodbThreadConcurrency;

  public static final String SERIALIZED_NAME_INNODB_WRITE_IO_THREADS = "innodb_write_io_threads";
  @SerializedName(SERIALIZED_NAME_INNODB_WRITE_IO_THREADS)
  private String innodbWriteIoThreads;

  public static final String SERIALIZED_NAME_INNODB_LOG_FILE_SIZE = "innodb_log_file_size";
  @SerializedName(SERIALIZED_NAME_INNODB_LOG_FILE_SIZE)
  private String innodbLogFileSize;

  public static final String SERIALIZED_NAME_MAX_ALLOWED_PACKET = "max_allowed_packet";
  @SerializedName(SERIALIZED_NAME_MAX_ALLOWED_PACKET)
  private String maxAllowedPacket;

  public static final String SERIALIZED_NAME_MAX_HEAP_TABLE_SIZE = "max_heap_table_size";
  @SerializedName(SERIALIZED_NAME_MAX_HEAP_TABLE_SIZE)
  private String maxHeapTableSize;

  public static final String SERIALIZED_NAME_SQL_MODE = "sql_mode";
  @SerializedName(SERIALIZED_NAME_SQL_MODE)
  private String sqlMode;

  public static final String SERIALIZED_NAME_QUERY_CACHE_TYPE = "query_cache_type";
  @SerializedName(SERIALIZED_NAME_QUERY_CACHE_TYPE)
  private String queryCacheType;

  public static final String SERIALIZED_NAME_QUERY_CACHE_SIZE = "query_cache_size";
  @SerializedName(SERIALIZED_NAME_QUERY_CACHE_SIZE)
  private String queryCacheSize;

  public static final String SERIALIZED_NAME_INNODB_FLUSH_LOG_AT_TRX_COMMIT = "innodb_flush_log_at_trx_commit";
  @SerializedName(SERIALIZED_NAME_INNODB_FLUSH_LOG_AT_TRX_COMMIT)
  private String innodbFlushLogAtTrxCommit;

  public static final String SERIALIZED_NAME_TRANSACTION_ISOLATION = "transaction_isolation";
  @SerializedName(SERIALIZED_NAME_TRANSACTION_ISOLATION)
  private String transactionIsolation;

  public static final String SERIALIZED_NAME_LONG_QUERY_TIME = "long_query_time";
  @SerializedName(SERIALIZED_NAME_LONG_QUERY_TIME)
  private String longQueryTime;

  public static final String SERIALIZED_NAME_TMP_TABLE_SIZE = "tmp_table_size";
  @SerializedName(SERIALIZED_NAME_TMP_TABLE_SIZE)
  private String tmpTableSize;

  public static final String SERIALIZED_NAME_TABLE_OPEN_CACHE = "table_open_cache";
  @SerializedName(SERIALIZED_NAME_TABLE_OPEN_CACHE)
  private String tableOpenCache;

  public static final String SERIALIZED_NAME_TABLE_OPEN_CACHE_INSTANCES = "table_open_cache_instances";
  @SerializedName(SERIALIZED_NAME_TABLE_OPEN_CACHE_INSTANCES)
  private String tableOpenCacheInstances;

  public static final String SERIALIZED_NAME_INNODB_FLUSH_METHOD = "innodb_flush_method";
  @SerializedName(SERIALIZED_NAME_INNODB_FLUSH_METHOD)
  private String innodbFlushMethod;

  public static final String SERIALIZED_NAME_INNODB_STRICT_MODE = "innodb_strict_mode";
  @SerializedName(SERIALIZED_NAME_INNODB_STRICT_MODE)
  private String innodbStrictMode;

  public static final String SERIALIZED_NAME_SLOW_QUERY_LOG = "slow_query_log";
  @SerializedName(SERIALIZED_NAME_SLOW_QUERY_LOG)
  private String slowQueryLog;

  public static final String SERIALIZED_NAME_BINLOG_CACHE_SIZE = "binlog_cache_size";
  @SerializedName(SERIALIZED_NAME_BINLOG_CACHE_SIZE)
  private String binlogCacheSize;

  public static final String SERIALIZED_NAME_BINLOG_GROUP_COMMIT_SYNC_DELAY = "binlog_group_commit_sync_delay";
  @SerializedName(SERIALIZED_NAME_BINLOG_GROUP_COMMIT_SYNC_DELAY)
  private String binlogGroupCommitSyncDelay;

  public static final String SERIALIZED_NAME_BINLOG_ROW_IMAGE = "binlog_row_image";
  @SerializedName(SERIALIZED_NAME_BINLOG_ROW_IMAGE)
  private String binlogRowImage;

  public static final String SERIALIZED_NAME_BINLOG_ROWS_QUERY_LOG_EVENTS = "binlog_rows_query_log_events";
  @SerializedName(SERIALIZED_NAME_BINLOG_ROWS_QUERY_LOG_EVENTS)
  private String binlogRowsQueryLogEvents;

  public static final String SERIALIZED_NAME_CHARACTER_SET_SERVER = "character_set_server";
  @SerializedName(SERIALIZED_NAME_CHARACTER_SET_SERVER)
  private String characterSetServer;

  public static final String SERIALIZED_NAME_EXPLICIT_DEFAULTS_FOR_TIMESTAMP = "explicit_defaults_for_timestamp";
  @SerializedName(SERIALIZED_NAME_EXPLICIT_DEFAULTS_FOR_TIMESTAMP)
  private String explicitDefaultsForTimestamp;

  public static final String SERIALIZED_NAME_GROUP_CONCAT_MAX_LEN = "group_concat_max_len";
  @SerializedName(SERIALIZED_NAME_GROUP_CONCAT_MAX_LEN)
  private String groupConcatMaxLen;

  public static final String SERIALIZED_NAME_INNODB_ADAPTIVE_HASH_INDEX = "innodb_adaptive_hash_index";
  @SerializedName(SERIALIZED_NAME_INNODB_ADAPTIVE_HASH_INDEX)
  private String innodbAdaptiveHashIndex;

  public static final String SERIALIZED_NAME_INNODB_LOCK_WAIT_TIMEOUT = "innodb_lock_wait_timeout";
  @SerializedName(SERIALIZED_NAME_INNODB_LOCK_WAIT_TIMEOUT)
  private String innodbLockWaitTimeout;

  public static final String SERIALIZED_NAME_INNODB_NUMA_INTERLEAVE = "innodb_numa_interleave";
  @SerializedName(SERIALIZED_NAME_INNODB_NUMA_INTERLEAVE)
  private String innodbNumaInterleave;

  public static final String SERIALIZED_NAME_NET_READ_TIMEOUT = "net_read_timeout";
  @SerializedName(SERIALIZED_NAME_NET_READ_TIMEOUT)
  private String netReadTimeout;

  public static final String SERIALIZED_NAME_NET_WRITE_TIMEOUT = "net_write_timeout";
  @SerializedName(SERIALIZED_NAME_NET_WRITE_TIMEOUT)
  private String netWriteTimeout;

  public static final String SERIALIZED_NAME_REGEXP_TIME_LIMIT = "regexp_time_limit";
  @SerializedName(SERIALIZED_NAME_REGEXP_TIME_LIMIT)
  private String regexpTimeLimit;

  public static final String SERIALIZED_NAME_SYNC_BINLOG = "sync_binlog";
  @SerializedName(SERIALIZED_NAME_SYNC_BINLOG)
  private String syncBinlog;

  public static final String SERIALIZED_NAME_TABLE_DEFINITION_CACHE = "table_definition_cache";
  @SerializedName(SERIALIZED_NAME_TABLE_DEFINITION_CACHE)
  private String tableDefinitionCache;

  public static final String SERIALIZED_NAME_LOG_BIN_TRUST_FUNCTION_CREATORS = "log_bin_trust_function_creators";
  @SerializedName(SERIALIZED_NAME_LOG_BIN_TRUST_FUNCTION_CREATORS)
  private String logBinTrustFunctionCreators;

  public static final String SERIALIZED_NAME_SKIP_NAME_RESOLVE = "skip_name_resolve";
  @SerializedName(SERIALIZED_NAME_SKIP_NAME_RESOLVE)
  private String skipNameResolve;

  public static final String SERIALIZED_NAME_INNODB_REDO_LOG_CAPACITY = "innodb_redo_log_capacity";
  @SerializedName(SERIALIZED_NAME_INNODB_REDO_LOG_CAPACITY)
  private String innodbRedoLogCapacity;

  public static final String SERIALIZED_NAME_WAIT_TIMEOUT = "wait_timeout";
  @SerializedName(SERIALIZED_NAME_WAIT_TIMEOUT)
  private String waitTimeout;

  public static final String SERIALIZED_NAME_INTERACTIVE_TIMEOUT = "interactive_timeout";
  @SerializedName(SERIALIZED_NAME_INTERACTIVE_TIMEOUT)
  private String interactiveTimeout;

  public static final String SERIALIZED_NAME_DEFAULT_TIME_ZONE = "default-time-zone";
  @SerializedName(SERIALIZED_NAME_DEFAULT_TIME_ZONE)
  private String defaultTimeZone;

  public static final String SERIALIZED_NAME_PXC_STRICT_MODE = "pxc_strict_mode";
  @SerializedName(SERIALIZED_NAME_PXC_STRICT_MODE)
  private String pxcStrictMode;

  public ConfigParametersMysql() {
  }

  public ConfigParametersMysql joinBufferSize(String joinBufferSize) {
    
    this.joinBufferSize = joinBufferSize;
    return this;
  }

   /**
   * Размер буфера, используемого при соединениях таблиц без индексов (&#x60;mysql5&#x60; | &#x60;mysql&#x60; | &#x60;mysql8_4&#x60;).
   * @return joinBufferSize
  **/
  @javax.annotation.Nullable
  public String getJoinBufferSize() {
    return joinBufferSize;
  }


  public void setJoinBufferSize(String joinBufferSize) {
    this.joinBufferSize = joinBufferSize;
  }


  public ConfigParametersMysql maxConnections(String maxConnections) {
    
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


  public ConfigParametersMysql sortBufferSize(String sortBufferSize) {
    
    this.sortBufferSize = sortBufferSize;
    return this;
  }

   /**
   * Размер буфера сортировки для операций ORDER BY и GROUP BY (&#x60;mysql5&#x60; | &#x60;mysql&#x60; | &#x60;mysql8_4&#x60;).
   * @return sortBufferSize
  **/
  @javax.annotation.Nullable
  public String getSortBufferSize() {
    return sortBufferSize;
  }


  public void setSortBufferSize(String sortBufferSize) {
    this.sortBufferSize = sortBufferSize;
  }


  public ConfigParametersMysql threadCacheSize(String threadCacheSize) {
    
    this.threadCacheSize = threadCacheSize;
    return this;
  }

   /**
   * Количество потоков, которые сервер сохраняет для повторного использования (&#x60;mysql5&#x60; | &#x60;mysql&#x60; | &#x60;mysql8_4&#x60;).
   * @return threadCacheSize
  **/
  @javax.annotation.Nullable
  public String getThreadCacheSize() {
    return threadCacheSize;
  }


  public void setThreadCacheSize(String threadCacheSize) {
    this.threadCacheSize = threadCacheSize;
  }


  public ConfigParametersMysql innodbBufferPoolSize(String innodbBufferPoolSize) {
    
    this.innodbBufferPoolSize = innodbBufferPoolSize;
    return this;
  }

   /**
   * Размер буферного пула InnoDB для хранения данных и индексов в памяти (&#x60;mysql5&#x60; | &#x60;mysql&#x60; | &#x60;mysql8_4&#x60;).
   * @return innodbBufferPoolSize
  **/
  @javax.annotation.Nullable
  public String getInnodbBufferPoolSize() {
    return innodbBufferPoolSize;
  }


  public void setInnodbBufferPoolSize(String innodbBufferPoolSize) {
    this.innodbBufferPoolSize = innodbBufferPoolSize;
  }


  public ConfigParametersMysql autoIncrementIncrement(String autoIncrementIncrement) {
    
    this.autoIncrementIncrement = autoIncrementIncrement;
    return this;
  }

   /**
   * Интервал между значениями столбцов с атрибутом &#x60;AUTO_INCREMENT&#x60; (&#x60;mysql5&#x60; | &#x60;mysql&#x60; | &#x60;mysql8_4&#x60;).
   * @return autoIncrementIncrement
  **/
  @javax.annotation.Nullable
  public String getAutoIncrementIncrement() {
    return autoIncrementIncrement;
  }


  public void setAutoIncrementIncrement(String autoIncrementIncrement) {
    this.autoIncrementIncrement = autoIncrementIncrement;
  }


  public ConfigParametersMysql autoIncrementOffset(String autoIncrementOffset) {
    
    this.autoIncrementOffset = autoIncrementOffset;
    return this;
  }

   /**
   * Начальное значение для столбцов с атрибутом &#x60;AUTO_INCREMENT&#x60; (&#x60;mysql5&#x60; | &#x60;mysql&#x60; | &#x60;mysql8_4&#x60;).
   * @return autoIncrementOffset
  **/
  @javax.annotation.Nullable
  public String getAutoIncrementOffset() {
    return autoIncrementOffset;
  }


  public void setAutoIncrementOffset(String autoIncrementOffset) {
    this.autoIncrementOffset = autoIncrementOffset;
  }


  public ConfigParametersMysql innodbIoCapacity(String innodbIoCapacity) {
    
    this.innodbIoCapacity = innodbIoCapacity;
    return this;
  }

   /**
   * Количество операций ввода-вывода в секунду &#x60;IOPS&#x60;, используемых InnoDB (&#x60;mysql5&#x60; | &#x60;mysql&#x60; | &#x60;mysql8_4&#x60;).
   * @return innodbIoCapacity
  **/
  @javax.annotation.Nullable
  public String getInnodbIoCapacity() {
    return innodbIoCapacity;
  }


  public void setInnodbIoCapacity(String innodbIoCapacity) {
    this.innodbIoCapacity = innodbIoCapacity;
  }


  public ConfigParametersMysql innodbPurgeThreads(String innodbPurgeThreads) {
    
    this.innodbPurgeThreads = innodbPurgeThreads;
    return this;
  }

   /**
   * Количество потоков, используемых для фоновой очистки undo-записей InnoDB (&#x60;mysql5&#x60; | &#x60;mysql&#x60; | &#x60;mysql8_4&#x60;).
   * @return innodbPurgeThreads
  **/
  @javax.annotation.Nullable
  public String getInnodbPurgeThreads() {
    return innodbPurgeThreads;
  }


  public void setInnodbPurgeThreads(String innodbPurgeThreads) {
    this.innodbPurgeThreads = innodbPurgeThreads;
  }


  public ConfigParametersMysql innodbReadIoThreads(String innodbReadIoThreads) {
    
    this.innodbReadIoThreads = innodbReadIoThreads;
    return this;
  }

   /**
   * Количество потоков ввода-вывода для операций чтения InnoDB (&#x60;mysql5&#x60; | &#x60;mysql&#x60; | &#x60;mysql8_4&#x60;).
   * @return innodbReadIoThreads
  **/
  @javax.annotation.Nullable
  public String getInnodbReadIoThreads() {
    return innodbReadIoThreads;
  }


  public void setInnodbReadIoThreads(String innodbReadIoThreads) {
    this.innodbReadIoThreads = innodbReadIoThreads;
  }


  public ConfigParametersMysql innodbThreadConcurrency(String innodbThreadConcurrency) {
    
    this.innodbThreadConcurrency = innodbThreadConcurrency;
    return this;
  }

   /**
   * Ограничение количества одновременно выполняющихся потоков InnoDB (&#x60;mysql5&#x60; | &#x60;mysql&#x60; | &#x60;mysql8_4&#x60;).
   * @return innodbThreadConcurrency
  **/
  @javax.annotation.Nullable
  public String getInnodbThreadConcurrency() {
    return innodbThreadConcurrency;
  }


  public void setInnodbThreadConcurrency(String innodbThreadConcurrency) {
    this.innodbThreadConcurrency = innodbThreadConcurrency;
  }


  public ConfigParametersMysql innodbWriteIoThreads(String innodbWriteIoThreads) {
    
    this.innodbWriteIoThreads = innodbWriteIoThreads;
    return this;
  }

   /**
   * Количество потоков ввода-вывода для операций записи InnoDB (&#x60;mysql5&#x60; | &#x60;mysql&#x60; | &#x60;mysql8_4&#x60;).
   * @return innodbWriteIoThreads
  **/
  @javax.annotation.Nullable
  public String getInnodbWriteIoThreads() {
    return innodbWriteIoThreads;
  }


  public void setInnodbWriteIoThreads(String innodbWriteIoThreads) {
    this.innodbWriteIoThreads = innodbWriteIoThreads;
  }


  public ConfigParametersMysql innodbLogFileSize(String innodbLogFileSize) {
    
    this.innodbLogFileSize = innodbLogFileSize;
    return this;
  }

   /**
   * Размер файла журнала транзакций InnoDB redo log (&#x60;mysql5&#x60; | &#x60;mysql&#x60; | &#x60;mysql8_4&#x60;).
   * @return innodbLogFileSize
  **/
  @javax.annotation.Nullable
  public String getInnodbLogFileSize() {
    return innodbLogFileSize;
  }


  public void setInnodbLogFileSize(String innodbLogFileSize) {
    this.innodbLogFileSize = innodbLogFileSize;
  }


  public ConfigParametersMysql maxAllowedPacket(String maxAllowedPacket) {
    
    this.maxAllowedPacket = maxAllowedPacket;
    return this;
  }

   /**
   * Максимальный размер пакета данных, который может передаваться между клиентом и сервером (&#x60;mysql5&#x60; | &#x60;mysql&#x60; | &#x60;mysql8_4&#x60;).
   * @return maxAllowedPacket
  **/
  @javax.annotation.Nullable
  public String getMaxAllowedPacket() {
    return maxAllowedPacket;
  }


  public void setMaxAllowedPacket(String maxAllowedPacket) {
    this.maxAllowedPacket = maxAllowedPacket;
  }


  public ConfigParametersMysql maxHeapTableSize(String maxHeapTableSize) {
    
    this.maxHeapTableSize = maxHeapTableSize;
    return this;
  }

   /**
   * Максимальный размер таблиц типа MEMORY (&#x60;mysql5&#x60; | &#x60;mysql&#x60; | &#x60;mysql8_4&#x60;).
   * @return maxHeapTableSize
  **/
  @javax.annotation.Nullable
  public String getMaxHeapTableSize() {
    return maxHeapTableSize;
  }


  public void setMaxHeapTableSize(String maxHeapTableSize) {
    this.maxHeapTableSize = maxHeapTableSize;
  }


  public ConfigParametersMysql sqlMode(String sqlMode) {
    
    this.sqlMode = sqlMode;
    return this;
  }

   /**
   * Режим работы SQL сервера, определяющий поведение обработки запросов (&#x60;mysql5&#x60; | &#x60;mysql&#x60; | &#x60;mysql8_4&#x60;).
   * @return sqlMode
  **/
  @javax.annotation.Nullable
  public String getSqlMode() {
    return sqlMode;
  }


  public void setSqlMode(String sqlMode) {
    this.sqlMode = sqlMode;
  }


  public ConfigParametersMysql queryCacheType(String queryCacheType) {
    
    this.queryCacheType = queryCacheType;
    return this;
  }

   /**
   * Тип кэша запросов (&#x60;mysql5&#x60; | &#x60;mysql&#x60; | &#x60;mysql8_4&#x60;).
   * @return queryCacheType
  **/
  @javax.annotation.Nullable
  public String getQueryCacheType() {
    return queryCacheType;
  }


  public void setQueryCacheType(String queryCacheType) {
    this.queryCacheType = queryCacheType;
  }


  public ConfigParametersMysql queryCacheSize(String queryCacheSize) {
    
    this.queryCacheSize = queryCacheSize;
    return this;
  }

   /**
   * Объем памяти, выделяемый для кэширования результатов запросов (&#x60;mysql5&#x60; | &#x60;mysql&#x60; | &#x60;mysql8_4&#x60;).
   * @return queryCacheSize
  **/
  @javax.annotation.Nullable
  public String getQueryCacheSize() {
    return queryCacheSize;
  }


  public void setQueryCacheSize(String queryCacheSize) {
    this.queryCacheSize = queryCacheSize;
  }


  public ConfigParametersMysql innodbFlushLogAtTrxCommit(String innodbFlushLogAtTrxCommit) {
    
    this.innodbFlushLogAtTrxCommit = innodbFlushLogAtTrxCommit;
    return this;
  }

   /**
   * Режим записи журнала InnoDB при фиксации транзакций (&#x60;mysql5&#x60; | &#x60;mysql&#x60; | &#x60;mysql8_4&#x60;).
   * @return innodbFlushLogAtTrxCommit
  **/
  @javax.annotation.Nullable
  public String getInnodbFlushLogAtTrxCommit() {
    return innodbFlushLogAtTrxCommit;
  }


  public void setInnodbFlushLogAtTrxCommit(String innodbFlushLogAtTrxCommit) {
    this.innodbFlushLogAtTrxCommit = innodbFlushLogAtTrxCommit;
  }


  public ConfigParametersMysql transactionIsolation(String transactionIsolation) {
    
    this.transactionIsolation = transactionIsolation;
    return this;
  }

   /**
   * Уровень изоляции транзакций по умолчанию (&#x60;mysql5&#x60; | &#x60;mysql&#x60; | &#x60;mysql8_4&#x60;).
   * @return transactionIsolation
  **/
  @javax.annotation.Nullable
  public String getTransactionIsolation() {
    return transactionIsolation;
  }


  public void setTransactionIsolation(String transactionIsolation) {
    this.transactionIsolation = transactionIsolation;
  }


  public ConfigParametersMysql longQueryTime(String longQueryTime) {
    
    this.longQueryTime = longQueryTime;
    return this;
  }

   /**
   * Время выполнения запроса, после которого он считается долгим и может попасть в slow query log (&#x60;mysql5&#x60; | &#x60;mysql&#x60; | &#x60;mysql8_4&#x60;).
   * @return longQueryTime
  **/
  @javax.annotation.Nullable
  public String getLongQueryTime() {
    return longQueryTime;
  }


  public void setLongQueryTime(String longQueryTime) {
    this.longQueryTime = longQueryTime;
  }


  public ConfigParametersMysql tmpTableSize(String tmpTableSize) {
    
    this.tmpTableSize = tmpTableSize;
    return this;
  }

   /**
   * Максимальный размер временных таблиц в памяти (&#x60;mysql5&#x60; | &#x60;mysql&#x60; | &#x60;mysql8_4&#x60;).
   * @return tmpTableSize
  **/
  @javax.annotation.Nullable
  public String getTmpTableSize() {
    return tmpTableSize;
  }


  public void setTmpTableSize(String tmpTableSize) {
    this.tmpTableSize = tmpTableSize;
  }


  public ConfigParametersMysql tableOpenCache(String tableOpenCache) {
    
    this.tableOpenCache = tableOpenCache;
    return this;
  }

   /**
   * Количество открытых таблиц, которые сервер может хранить в кэше (&#x60;mysql5&#x60; | &#x60;mysql&#x60; | &#x60;mysql8_4&#x60;).
   * @return tableOpenCache
  **/
  @javax.annotation.Nullable
  public String getTableOpenCache() {
    return tableOpenCache;
  }


  public void setTableOpenCache(String tableOpenCache) {
    this.tableOpenCache = tableOpenCache;
  }


  public ConfigParametersMysql tableOpenCacheInstances(String tableOpenCacheInstances) {
    
    this.tableOpenCacheInstances = tableOpenCacheInstances;
    return this;
  }

   /**
   * Количество экземпляров кэша открытых таблиц для снижения конкуренции между потоками (&#x60;mysql5&#x60; | &#x60;mysql&#x60; | &#x60;mysql8_4&#x60;).
   * @return tableOpenCacheInstances
  **/
  @javax.annotation.Nullable
  public String getTableOpenCacheInstances() {
    return tableOpenCacheInstances;
  }


  public void setTableOpenCacheInstances(String tableOpenCacheInstances) {
    this.tableOpenCacheInstances = tableOpenCacheInstances;
  }


  public ConfigParametersMysql innodbFlushMethod(String innodbFlushMethod) {
    
    this.innodbFlushMethod = innodbFlushMethod;
    return this;
  }

   /**
   * Метод выполнения операций записи и синхронизации файлов InnoDB (&#x60;mysql5&#x60; | &#x60;mysql&#x60; | &#x60;mysql8_4&#x60;).
   * @return innodbFlushMethod
  **/
  @javax.annotation.Nullable
  public String getInnodbFlushMethod() {
    return innodbFlushMethod;
  }


  public void setInnodbFlushMethod(String innodbFlushMethod) {
    this.innodbFlushMethod = innodbFlushMethod;
  }


  public ConfigParametersMysql innodbStrictMode(String innodbStrictMode) {
    
    this.innodbStrictMode = innodbStrictMode;
    return this;
  }

   /**
   * Включение строгой проверки операций InnoDB (&#x60;mysql5&#x60; | &#x60;mysql&#x60; | &#x60;mysql8_4&#x60;).
   * @return innodbStrictMode
  **/
  @javax.annotation.Nullable
  public String getInnodbStrictMode() {
    return innodbStrictMode;
  }


  public void setInnodbStrictMode(String innodbStrictMode) {
    this.innodbStrictMode = innodbStrictMode;
  }


  public ConfigParametersMysql slowQueryLog(String slowQueryLog) {
    
    this.slowQueryLog = slowQueryLog;
    return this;
  }

   /**
   * Включение журнала медленных запросов (&#x60;mysql5&#x60; | &#x60;mysql&#x60; | &#x60;mysql8_4&#x60;).
   * @return slowQueryLog
  **/
  @javax.annotation.Nullable
  public String getSlowQueryLog() {
    return slowQueryLog;
  }


  public void setSlowQueryLog(String slowQueryLog) {
    this.slowQueryLog = slowQueryLog;
  }


  public ConfigParametersMysql binlogCacheSize(String binlogCacheSize) {
    
    this.binlogCacheSize = binlogCacheSize;
    return this;
  }

   /**
   * Размер кэша бинарного журнала для транзакций (&#x60;mysql5&#x60; | &#x60;mysql&#x60; | &#x60;mysql8_4&#x60;).
   * @return binlogCacheSize
  **/
  @javax.annotation.Nullable
  public String getBinlogCacheSize() {
    return binlogCacheSize;
  }


  public void setBinlogCacheSize(String binlogCacheSize) {
    this.binlogCacheSize = binlogCacheSize;
  }


  public ConfigParametersMysql binlogGroupCommitSyncDelay(String binlogGroupCommitSyncDelay) {
    
    this.binlogGroupCommitSyncDelay = binlogGroupCommitSyncDelay;
    return this;
  }

   /**
   * Задержка синхронизации групповой фиксации бинарного журнала в микросекундах (&#x60;mysql5&#x60; | &#x60;mysql&#x60; | &#x60;mysql8_4&#x60;).
   * @return binlogGroupCommitSyncDelay
  **/
  @javax.annotation.Nullable
  public String getBinlogGroupCommitSyncDelay() {
    return binlogGroupCommitSyncDelay;
  }


  public void setBinlogGroupCommitSyncDelay(String binlogGroupCommitSyncDelay) {
    this.binlogGroupCommitSyncDelay = binlogGroupCommitSyncDelay;
  }


  public ConfigParametersMysql binlogRowImage(String binlogRowImage) {
    
    this.binlogRowImage = binlogRowImage;
    return this;
  }

   /**
   * Количество информации, записываемой в бинарный журнал при row-based репликации (&#x60;mysql5&#x60; | &#x60;mysql&#x60; | &#x60;mysql8_4&#x60;).
   * @return binlogRowImage
  **/
  @javax.annotation.Nullable
  public String getBinlogRowImage() {
    return binlogRowImage;
  }


  public void setBinlogRowImage(String binlogRowImage) {
    this.binlogRowImage = binlogRowImage;
  }


  public ConfigParametersMysql binlogRowsQueryLogEvents(String binlogRowsQueryLogEvents) {
    
    this.binlogRowsQueryLogEvents = binlogRowsQueryLogEvents;
    return this;
  }

   /**
   * Включение записи SQL-запросов в бинарный журнал при row-based репликации (&#x60;mysql5&#x60; | &#x60;mysql&#x60; | &#x60;mysql8_4&#x60;).
   * @return binlogRowsQueryLogEvents
  **/
  @javax.annotation.Nullable
  public String getBinlogRowsQueryLogEvents() {
    return binlogRowsQueryLogEvents;
  }


  public void setBinlogRowsQueryLogEvents(String binlogRowsQueryLogEvents) {
    this.binlogRowsQueryLogEvents = binlogRowsQueryLogEvents;
  }


  public ConfigParametersMysql characterSetServer(String characterSetServer) {
    
    this.characterSetServer = characterSetServer;
    return this;
  }

   /**
   * Кодировка по умолчанию для сервера MySQL (&#x60;mysql5&#x60; | &#x60;mysql&#x60; | &#x60;mysql8_4&#x60;).
   * @return characterSetServer
  **/
  @javax.annotation.Nullable
  public String getCharacterSetServer() {
    return characterSetServer;
  }


  public void setCharacterSetServer(String characterSetServer) {
    this.characterSetServer = characterSetServer;
  }


  public ConfigParametersMysql explicitDefaultsForTimestamp(String explicitDefaultsForTimestamp) {
    
    this.explicitDefaultsForTimestamp = explicitDefaultsForTimestamp;
    return this;
  }

   /**
   * Определяет автоматическое поведение TIMESTAMP без явных значений по умолчанию (&#x60;mysql5&#x60; | &#x60;mysql&#x60; | &#x60;mysql8_4&#x60;).
   * @return explicitDefaultsForTimestamp
  **/
  @javax.annotation.Nullable
  public String getExplicitDefaultsForTimestamp() {
    return explicitDefaultsForTimestamp;
  }


  public void setExplicitDefaultsForTimestamp(String explicitDefaultsForTimestamp) {
    this.explicitDefaultsForTimestamp = explicitDefaultsForTimestamp;
  }


  public ConfigParametersMysql groupConcatMaxLen(String groupConcatMaxLen) {
    
    this.groupConcatMaxLen = groupConcatMaxLen;
    return this;
  }

   /**
   * Максимальная длина результата функции GROUP_CONCAT (&#x60;mysql5&#x60; | &#x60;mysql&#x60; | &#x60;mysql8_4&#x60;).
   * @return groupConcatMaxLen
  **/
  @javax.annotation.Nullable
  public String getGroupConcatMaxLen() {
    return groupConcatMaxLen;
  }


  public void setGroupConcatMaxLen(String groupConcatMaxLen) {
    this.groupConcatMaxLen = groupConcatMaxLen;
  }


  public ConfigParametersMysql innodbAdaptiveHashIndex(String innodbAdaptiveHashIndex) {
    
    this.innodbAdaptiveHashIndex = innodbAdaptiveHashIndex;
    return this;
  }

   /**
   * Включение или отключение адаптивного хэш-индекса InnoDB для ускорения поиска по индексам (&#x60;mysql5&#x60; | &#x60;mysql&#x60; | &#x60;mysql8_4&#x60;).
   * @return innodbAdaptiveHashIndex
  **/
  @javax.annotation.Nullable
  public String getInnodbAdaptiveHashIndex() {
    return innodbAdaptiveHashIndex;
  }


  public void setInnodbAdaptiveHashIndex(String innodbAdaptiveHashIndex) {
    this.innodbAdaptiveHashIndex = innodbAdaptiveHashIndex;
  }


  public ConfigParametersMysql innodbLockWaitTimeout(String innodbLockWaitTimeout) {
    
    this.innodbLockWaitTimeout = innodbLockWaitTimeout;
    return this;
  }

   /**
   * Время ожидания блокировки InnoDB перед завершением транзакции с ошибкой (&#x60;mysql5&#x60; | &#x60;mysql&#x60; | &#x60;mysql8_4&#x60;).
   * @return innodbLockWaitTimeout
  **/
  @javax.annotation.Nullable
  public String getInnodbLockWaitTimeout() {
    return innodbLockWaitTimeout;
  }


  public void setInnodbLockWaitTimeout(String innodbLockWaitTimeout) {
    this.innodbLockWaitTimeout = innodbLockWaitTimeout;
  }


  public ConfigParametersMysql innodbNumaInterleave(String innodbNumaInterleave) {
    
    this.innodbNumaInterleave = innodbNumaInterleave;
    return this;
  }

   /**
   * Включение распределения памяти InnoDB между NUMA-узлами (&#x60;mysql5&#x60; | &#x60;mysql&#x60; | &#x60;mysql8_4&#x60;).
   * @return innodbNumaInterleave
  **/
  @javax.annotation.Nullable
  public String getInnodbNumaInterleave() {
    return innodbNumaInterleave;
  }


  public void setInnodbNumaInterleave(String innodbNumaInterleave) {
    this.innodbNumaInterleave = innodbNumaInterleave;
  }


  public ConfigParametersMysql netReadTimeout(String netReadTimeout) {
    
    this.netReadTimeout = netReadTimeout;
    return this;
  }

   /**
   * Время ожидания данных от клиента при чтении сетевого соединения (&#x60;mysql5&#x60; | &#x60;mysql&#x60; | &#x60;mysql8_4&#x60;).
   * @return netReadTimeout
  **/
  @javax.annotation.Nullable
  public String getNetReadTimeout() {
    return netReadTimeout;
  }


  public void setNetReadTimeout(String netReadTimeout) {
    this.netReadTimeout = netReadTimeout;
  }


  public ConfigParametersMysql netWriteTimeout(String netWriteTimeout) {
    
    this.netWriteTimeout = netWriteTimeout;
    return this;
  }

   /**
   * Время ожидания записи данных клиенту через сетевое соединение (&#x60;mysql5&#x60; | &#x60;mysql&#x60; | &#x60;mysql8_4&#x60;).
   * @return netWriteTimeout
  **/
  @javax.annotation.Nullable
  public String getNetWriteTimeout() {
    return netWriteTimeout;
  }


  public void setNetWriteTimeout(String netWriteTimeout) {
    this.netWriteTimeout = netWriteTimeout;
  }


  public ConfigParametersMysql regexpTimeLimit(String regexpTimeLimit) {
    
    this.regexpTimeLimit = regexpTimeLimit;
    return this;
  }

   /**
   * Максимальное время выполнения регулярных выражений (&#x60;mysql&#x60; | &#x60;mysql8_4&#x60;).
   * @return regexpTimeLimit
  **/
  @javax.annotation.Nullable
  public String getRegexpTimeLimit() {
    return regexpTimeLimit;
  }


  public void setRegexpTimeLimit(String regexpTimeLimit) {
    this.regexpTimeLimit = regexpTimeLimit;
  }


  public ConfigParametersMysql syncBinlog(String syncBinlog) {
    
    this.syncBinlog = syncBinlog;
    return this;
  }

   /**
   * Количество операций записи бинарного журнала перед принудительной синхронизацией на диск (&#x60;mysql5&#x60; | &#x60;mysql&#x60; | &#x60;mysql8_4&#x60;).
   * @return syncBinlog
  **/
  @javax.annotation.Nullable
  public String getSyncBinlog() {
    return syncBinlog;
  }


  public void setSyncBinlog(String syncBinlog) {
    this.syncBinlog = syncBinlog;
  }


  public ConfigParametersMysql tableDefinitionCache(String tableDefinitionCache) {
    
    this.tableDefinitionCache = tableDefinitionCache;
    return this;
  }

   /**
   * Количество определений таблиц, хранящихся в кэше (&#x60;mysql5&#x60; | &#x60;mysql&#x60; | &#x60;mysql8_4&#x60;).
   * @return tableDefinitionCache
  **/
  @javax.annotation.Nullable
  public String getTableDefinitionCache() {
    return tableDefinitionCache;
  }


  public void setTableDefinitionCache(String tableDefinitionCache) {
    this.tableDefinitionCache = tableDefinitionCache;
  }


  public ConfigParametersMysql logBinTrustFunctionCreators(String logBinTrustFunctionCreators) {
    
    this.logBinTrustFunctionCreators = logBinTrustFunctionCreators;
    return this;
  }

   /**
   * Разрешение создания хранимых функций без проверки бинарной регистрации (&#x60;mysql5&#x60; | &#x60;mysql&#x60; | &#x60;mysql8_4&#x60;).
   * @return logBinTrustFunctionCreators
  **/
  @javax.annotation.Nullable
  public String getLogBinTrustFunctionCreators() {
    return logBinTrustFunctionCreators;
  }


  public void setLogBinTrustFunctionCreators(String logBinTrustFunctionCreators) {
    this.logBinTrustFunctionCreators = logBinTrustFunctionCreators;
  }


  public ConfigParametersMysql skipNameResolve(String skipNameResolve) {
    
    this.skipNameResolve = skipNameResolve;
    return this;
  }

   /**
   * Отключение DNS-разрешения имен клиентов при подключении к серверу (&#x60;mysql5&#x60; | &#x60;mysql&#x60; | &#x60;mysql8_4&#x60;).
   * @return skipNameResolve
  **/
  @javax.annotation.Nullable
  public String getSkipNameResolve() {
    return skipNameResolve;
  }


  public void setSkipNameResolve(String skipNameResolve) {
    this.skipNameResolve = skipNameResolve;
  }


  public ConfigParametersMysql innodbRedoLogCapacity(String innodbRedoLogCapacity) {
    
    this.innodbRedoLogCapacity = innodbRedoLogCapacity;
    return this;
  }

   /**
   * Общий размер redo log InnoDB для хранения журнала восстановления (&#x60;mysql8_4&#x60;).
   * @return innodbRedoLogCapacity
  **/
  @javax.annotation.Nullable
  public String getInnodbRedoLogCapacity() {
    return innodbRedoLogCapacity;
  }


  public void setInnodbRedoLogCapacity(String innodbRedoLogCapacity) {
    this.innodbRedoLogCapacity = innodbRedoLogCapacity;
  }


  public ConfigParametersMysql waitTimeout(String waitTimeout) {
    
    this.waitTimeout = waitTimeout;
    return this;
  }

   /**
   * Время ожидания неактивного клиентского соединения перед закрытием (&#x60;mysql5&#x60; | &#x60;mysql&#x60; | &#x60;mysql8_4&#x60;).
   * @return waitTimeout
  **/
  @javax.annotation.Nullable
  public String getWaitTimeout() {
    return waitTimeout;
  }


  public void setWaitTimeout(String waitTimeout) {
    this.waitTimeout = waitTimeout;
  }


  public ConfigParametersMysql interactiveTimeout(String interactiveTimeout) {
    
    this.interactiveTimeout = interactiveTimeout;
    return this;
  }

   /**
   * Время ожидания неактивного интерактивного соединения перед закрытием (&#x60;mysql5&#x60; | &#x60;mysql&#x60; | &#x60;mysql8_4&#x60;).
   * @return interactiveTimeout
  **/
  @javax.annotation.Nullable
  public String getInteractiveTimeout() {
    return interactiveTimeout;
  }


  public void setInteractiveTimeout(String interactiveTimeout) {
    this.interactiveTimeout = interactiveTimeout;
  }


  public ConfigParametersMysql defaultTimeZone(String defaultTimeZone) {
    
    this.defaultTimeZone = defaultTimeZone;
    return this;
  }

   /**
   * Часовой пояс сервера MySQL по умолчанию (&#x60;mysql5&#x60; | &#x60;mysql&#x60; | &#x60;mysql8_4&#x60;).
   * @return defaultTimeZone
  **/
  @javax.annotation.Nullable
  public String getDefaultTimeZone() {
    return defaultTimeZone;
  }


  public void setDefaultTimeZone(String defaultTimeZone) {
    this.defaultTimeZone = defaultTimeZone;
  }


  public ConfigParametersMysql pxcStrictMode(String pxcStrictMode) {
    
    this.pxcStrictMode = pxcStrictMode;
    return this;
  }

   /**
   * Режим строгой проверки операций в Percona XtraDB Cluster (&#x60;mysql5&#x60; | &#x60;mysql&#x60; | &#x60;mysql8_4&#x60;).
   * @return pxcStrictMode
  **/
  @javax.annotation.Nullable
  public String getPxcStrictMode() {
    return pxcStrictMode;
  }


  public void setPxcStrictMode(String pxcStrictMode) {
    this.pxcStrictMode = pxcStrictMode;
  }



  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    ConfigParametersMysql configParametersMysql = (ConfigParametersMysql) o;
    return Objects.equals(this.joinBufferSize, configParametersMysql.joinBufferSize) &&
        Objects.equals(this.maxConnections, configParametersMysql.maxConnections) &&
        Objects.equals(this.sortBufferSize, configParametersMysql.sortBufferSize) &&
        Objects.equals(this.threadCacheSize, configParametersMysql.threadCacheSize) &&
        Objects.equals(this.innodbBufferPoolSize, configParametersMysql.innodbBufferPoolSize) &&
        Objects.equals(this.autoIncrementIncrement, configParametersMysql.autoIncrementIncrement) &&
        Objects.equals(this.autoIncrementOffset, configParametersMysql.autoIncrementOffset) &&
        Objects.equals(this.innodbIoCapacity, configParametersMysql.innodbIoCapacity) &&
        Objects.equals(this.innodbPurgeThreads, configParametersMysql.innodbPurgeThreads) &&
        Objects.equals(this.innodbReadIoThreads, configParametersMysql.innodbReadIoThreads) &&
        Objects.equals(this.innodbThreadConcurrency, configParametersMysql.innodbThreadConcurrency) &&
        Objects.equals(this.innodbWriteIoThreads, configParametersMysql.innodbWriteIoThreads) &&
        Objects.equals(this.innodbLogFileSize, configParametersMysql.innodbLogFileSize) &&
        Objects.equals(this.maxAllowedPacket, configParametersMysql.maxAllowedPacket) &&
        Objects.equals(this.maxHeapTableSize, configParametersMysql.maxHeapTableSize) &&
        Objects.equals(this.sqlMode, configParametersMysql.sqlMode) &&
        Objects.equals(this.queryCacheType, configParametersMysql.queryCacheType) &&
        Objects.equals(this.queryCacheSize, configParametersMysql.queryCacheSize) &&
        Objects.equals(this.innodbFlushLogAtTrxCommit, configParametersMysql.innodbFlushLogAtTrxCommit) &&
        Objects.equals(this.transactionIsolation, configParametersMysql.transactionIsolation) &&
        Objects.equals(this.longQueryTime, configParametersMysql.longQueryTime) &&
        Objects.equals(this.tmpTableSize, configParametersMysql.tmpTableSize) &&
        Objects.equals(this.tableOpenCache, configParametersMysql.tableOpenCache) &&
        Objects.equals(this.tableOpenCacheInstances, configParametersMysql.tableOpenCacheInstances) &&
        Objects.equals(this.innodbFlushMethod, configParametersMysql.innodbFlushMethod) &&
        Objects.equals(this.innodbStrictMode, configParametersMysql.innodbStrictMode) &&
        Objects.equals(this.slowQueryLog, configParametersMysql.slowQueryLog) &&
        Objects.equals(this.binlogCacheSize, configParametersMysql.binlogCacheSize) &&
        Objects.equals(this.binlogGroupCommitSyncDelay, configParametersMysql.binlogGroupCommitSyncDelay) &&
        Objects.equals(this.binlogRowImage, configParametersMysql.binlogRowImage) &&
        Objects.equals(this.binlogRowsQueryLogEvents, configParametersMysql.binlogRowsQueryLogEvents) &&
        Objects.equals(this.characterSetServer, configParametersMysql.characterSetServer) &&
        Objects.equals(this.explicitDefaultsForTimestamp, configParametersMysql.explicitDefaultsForTimestamp) &&
        Objects.equals(this.groupConcatMaxLen, configParametersMysql.groupConcatMaxLen) &&
        Objects.equals(this.innodbAdaptiveHashIndex, configParametersMysql.innodbAdaptiveHashIndex) &&
        Objects.equals(this.innodbLockWaitTimeout, configParametersMysql.innodbLockWaitTimeout) &&
        Objects.equals(this.innodbNumaInterleave, configParametersMysql.innodbNumaInterleave) &&
        Objects.equals(this.netReadTimeout, configParametersMysql.netReadTimeout) &&
        Objects.equals(this.netWriteTimeout, configParametersMysql.netWriteTimeout) &&
        Objects.equals(this.regexpTimeLimit, configParametersMysql.regexpTimeLimit) &&
        Objects.equals(this.syncBinlog, configParametersMysql.syncBinlog) &&
        Objects.equals(this.tableDefinitionCache, configParametersMysql.tableDefinitionCache) &&
        Objects.equals(this.logBinTrustFunctionCreators, configParametersMysql.logBinTrustFunctionCreators) &&
        Objects.equals(this.skipNameResolve, configParametersMysql.skipNameResolve) &&
        Objects.equals(this.innodbRedoLogCapacity, configParametersMysql.innodbRedoLogCapacity) &&
        Objects.equals(this.waitTimeout, configParametersMysql.waitTimeout) &&
        Objects.equals(this.interactiveTimeout, configParametersMysql.interactiveTimeout) &&
        Objects.equals(this.defaultTimeZone, configParametersMysql.defaultTimeZone) &&
        Objects.equals(this.pxcStrictMode, configParametersMysql.pxcStrictMode);
  }

  @Override
  public int hashCode() {
    return Objects.hash(joinBufferSize, maxConnections, sortBufferSize, threadCacheSize, innodbBufferPoolSize, autoIncrementIncrement, autoIncrementOffset, innodbIoCapacity, innodbPurgeThreads, innodbReadIoThreads, innodbThreadConcurrency, innodbWriteIoThreads, innodbLogFileSize, maxAllowedPacket, maxHeapTableSize, sqlMode, queryCacheType, queryCacheSize, innodbFlushLogAtTrxCommit, transactionIsolation, longQueryTime, tmpTableSize, tableOpenCache, tableOpenCacheInstances, innodbFlushMethod, innodbStrictMode, slowQueryLog, binlogCacheSize, binlogGroupCommitSyncDelay, binlogRowImage, binlogRowsQueryLogEvents, characterSetServer, explicitDefaultsForTimestamp, groupConcatMaxLen, innodbAdaptiveHashIndex, innodbLockWaitTimeout, innodbNumaInterleave, netReadTimeout, netWriteTimeout, regexpTimeLimit, syncBinlog, tableDefinitionCache, logBinTrustFunctionCreators, skipNameResolve, innodbRedoLogCapacity, waitTimeout, interactiveTimeout, defaultTimeZone, pxcStrictMode);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class ConfigParametersMysql {\n");
    sb.append("    joinBufferSize: ").append(toIndentedString(joinBufferSize)).append("\n");
    sb.append("    maxConnections: ").append(toIndentedString(maxConnections)).append("\n");
    sb.append("    sortBufferSize: ").append(toIndentedString(sortBufferSize)).append("\n");
    sb.append("    threadCacheSize: ").append(toIndentedString(threadCacheSize)).append("\n");
    sb.append("    innodbBufferPoolSize: ").append(toIndentedString(innodbBufferPoolSize)).append("\n");
    sb.append("    autoIncrementIncrement: ").append(toIndentedString(autoIncrementIncrement)).append("\n");
    sb.append("    autoIncrementOffset: ").append(toIndentedString(autoIncrementOffset)).append("\n");
    sb.append("    innodbIoCapacity: ").append(toIndentedString(innodbIoCapacity)).append("\n");
    sb.append("    innodbPurgeThreads: ").append(toIndentedString(innodbPurgeThreads)).append("\n");
    sb.append("    innodbReadIoThreads: ").append(toIndentedString(innodbReadIoThreads)).append("\n");
    sb.append("    innodbThreadConcurrency: ").append(toIndentedString(innodbThreadConcurrency)).append("\n");
    sb.append("    innodbWriteIoThreads: ").append(toIndentedString(innodbWriteIoThreads)).append("\n");
    sb.append("    innodbLogFileSize: ").append(toIndentedString(innodbLogFileSize)).append("\n");
    sb.append("    maxAllowedPacket: ").append(toIndentedString(maxAllowedPacket)).append("\n");
    sb.append("    maxHeapTableSize: ").append(toIndentedString(maxHeapTableSize)).append("\n");
    sb.append("    sqlMode: ").append(toIndentedString(sqlMode)).append("\n");
    sb.append("    queryCacheType: ").append(toIndentedString(queryCacheType)).append("\n");
    sb.append("    queryCacheSize: ").append(toIndentedString(queryCacheSize)).append("\n");
    sb.append("    innodbFlushLogAtTrxCommit: ").append(toIndentedString(innodbFlushLogAtTrxCommit)).append("\n");
    sb.append("    transactionIsolation: ").append(toIndentedString(transactionIsolation)).append("\n");
    sb.append("    longQueryTime: ").append(toIndentedString(longQueryTime)).append("\n");
    sb.append("    tmpTableSize: ").append(toIndentedString(tmpTableSize)).append("\n");
    sb.append("    tableOpenCache: ").append(toIndentedString(tableOpenCache)).append("\n");
    sb.append("    tableOpenCacheInstances: ").append(toIndentedString(tableOpenCacheInstances)).append("\n");
    sb.append("    innodbFlushMethod: ").append(toIndentedString(innodbFlushMethod)).append("\n");
    sb.append("    innodbStrictMode: ").append(toIndentedString(innodbStrictMode)).append("\n");
    sb.append("    slowQueryLog: ").append(toIndentedString(slowQueryLog)).append("\n");
    sb.append("    binlogCacheSize: ").append(toIndentedString(binlogCacheSize)).append("\n");
    sb.append("    binlogGroupCommitSyncDelay: ").append(toIndentedString(binlogGroupCommitSyncDelay)).append("\n");
    sb.append("    binlogRowImage: ").append(toIndentedString(binlogRowImage)).append("\n");
    sb.append("    binlogRowsQueryLogEvents: ").append(toIndentedString(binlogRowsQueryLogEvents)).append("\n");
    sb.append("    characterSetServer: ").append(toIndentedString(characterSetServer)).append("\n");
    sb.append("    explicitDefaultsForTimestamp: ").append(toIndentedString(explicitDefaultsForTimestamp)).append("\n");
    sb.append("    groupConcatMaxLen: ").append(toIndentedString(groupConcatMaxLen)).append("\n");
    sb.append("    innodbAdaptiveHashIndex: ").append(toIndentedString(innodbAdaptiveHashIndex)).append("\n");
    sb.append("    innodbLockWaitTimeout: ").append(toIndentedString(innodbLockWaitTimeout)).append("\n");
    sb.append("    innodbNumaInterleave: ").append(toIndentedString(innodbNumaInterleave)).append("\n");
    sb.append("    netReadTimeout: ").append(toIndentedString(netReadTimeout)).append("\n");
    sb.append("    netWriteTimeout: ").append(toIndentedString(netWriteTimeout)).append("\n");
    sb.append("    regexpTimeLimit: ").append(toIndentedString(regexpTimeLimit)).append("\n");
    sb.append("    syncBinlog: ").append(toIndentedString(syncBinlog)).append("\n");
    sb.append("    tableDefinitionCache: ").append(toIndentedString(tableDefinitionCache)).append("\n");
    sb.append("    logBinTrustFunctionCreators: ").append(toIndentedString(logBinTrustFunctionCreators)).append("\n");
    sb.append("    skipNameResolve: ").append(toIndentedString(skipNameResolve)).append("\n");
    sb.append("    innodbRedoLogCapacity: ").append(toIndentedString(innodbRedoLogCapacity)).append("\n");
    sb.append("    waitTimeout: ").append(toIndentedString(waitTimeout)).append("\n");
    sb.append("    interactiveTimeout: ").append(toIndentedString(interactiveTimeout)).append("\n");
    sb.append("    defaultTimeZone: ").append(toIndentedString(defaultTimeZone)).append("\n");
    sb.append("    pxcStrictMode: ").append(toIndentedString(pxcStrictMode)).append("\n");
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
    openapiFields.add("join_buffer_size");
    openapiFields.add("max_connections");
    openapiFields.add("sort_buffer_size");
    openapiFields.add("thread_cache_size");
    openapiFields.add("innodb_buffer_pool_size");
    openapiFields.add("auto_increment_increment");
    openapiFields.add("auto_increment_offset");
    openapiFields.add("innodb_io_capacity");
    openapiFields.add("innodb_purge_threads");
    openapiFields.add("innodb_read_io_threads");
    openapiFields.add("innodb_thread_concurrency");
    openapiFields.add("innodb_write_io_threads");
    openapiFields.add("innodb_log_file_size");
    openapiFields.add("max_allowed_packet");
    openapiFields.add("max_heap_table_size");
    openapiFields.add("sql_mode");
    openapiFields.add("query_cache_type");
    openapiFields.add("query_cache_size");
    openapiFields.add("innodb_flush_log_at_trx_commit");
    openapiFields.add("transaction_isolation");
    openapiFields.add("long_query_time");
    openapiFields.add("tmp_table_size");
    openapiFields.add("table_open_cache");
    openapiFields.add("table_open_cache_instances");
    openapiFields.add("innodb_flush_method");
    openapiFields.add("innodb_strict_mode");
    openapiFields.add("slow_query_log");
    openapiFields.add("binlog_cache_size");
    openapiFields.add("binlog_group_commit_sync_delay");
    openapiFields.add("binlog_row_image");
    openapiFields.add("binlog_rows_query_log_events");
    openapiFields.add("character_set_server");
    openapiFields.add("explicit_defaults_for_timestamp");
    openapiFields.add("group_concat_max_len");
    openapiFields.add("innodb_adaptive_hash_index");
    openapiFields.add("innodb_lock_wait_timeout");
    openapiFields.add("innodb_numa_interleave");
    openapiFields.add("net_read_timeout");
    openapiFields.add("net_write_timeout");
    openapiFields.add("regexp_time_limit");
    openapiFields.add("sync_binlog");
    openapiFields.add("table_definition_cache");
    openapiFields.add("log_bin_trust_function_creators");
    openapiFields.add("skip_name_resolve");
    openapiFields.add("innodb_redo_log_capacity");
    openapiFields.add("wait_timeout");
    openapiFields.add("interactive_timeout");
    openapiFields.add("default-time-zone");
    openapiFields.add("pxc_strict_mode");

    // a set of required properties/fields (JSON key names)
    openapiRequiredFields = new HashSet<String>();
  }

 /**
  * Validates the JSON Element and throws an exception if issues found
  *
  * @param jsonElement JSON Element
  * @throws IOException if the JSON Element is invalid with respect to ConfigParametersMysql
  */
  public static void validateJsonElement(JsonElement jsonElement) throws IOException {
      if (jsonElement == null) {
        if (!ConfigParametersMysql.openapiRequiredFields.isEmpty()) { // has required fields but JSON element is null
          throw new IllegalArgumentException(String.format("The required field(s) %s in ConfigParametersMysql is not found in the empty JSON string", ConfigParametersMysql.openapiRequiredFields.toString()));
        }
      }

      Set<Entry<String, JsonElement>> entries = jsonElement.getAsJsonObject().entrySet();
      // check to see if the JSON string contains additional fields
      for (Entry<String, JsonElement> entry : entries) {
        if (!ConfigParametersMysql.openapiFields.contains(entry.getKey())) {
          throw new IllegalArgumentException(String.format("The field `%s` in the JSON string is not defined in the `ConfigParametersMysql` properties. JSON: %s", entry.getKey(), jsonElement.toString()));
        }
      }
        JsonObject jsonObj = jsonElement.getAsJsonObject();
      if ((jsonObj.get("join_buffer_size") != null && !jsonObj.get("join_buffer_size").isJsonNull()) && !jsonObj.get("join_buffer_size").isJsonPrimitive()) {
        throw new IllegalArgumentException(String.format("Expected the field `join_buffer_size` to be a primitive type in the JSON string but got `%s`", jsonObj.get("join_buffer_size").toString()));
      }
      if ((jsonObj.get("max_connections") != null && !jsonObj.get("max_connections").isJsonNull()) && !jsonObj.get("max_connections").isJsonPrimitive()) {
        throw new IllegalArgumentException(String.format("Expected the field `max_connections` to be a primitive type in the JSON string but got `%s`", jsonObj.get("max_connections").toString()));
      }
      if ((jsonObj.get("sort_buffer_size") != null && !jsonObj.get("sort_buffer_size").isJsonNull()) && !jsonObj.get("sort_buffer_size").isJsonPrimitive()) {
        throw new IllegalArgumentException(String.format("Expected the field `sort_buffer_size` to be a primitive type in the JSON string but got `%s`", jsonObj.get("sort_buffer_size").toString()));
      }
      if ((jsonObj.get("thread_cache_size") != null && !jsonObj.get("thread_cache_size").isJsonNull()) && !jsonObj.get("thread_cache_size").isJsonPrimitive()) {
        throw new IllegalArgumentException(String.format("Expected the field `thread_cache_size` to be a primitive type in the JSON string but got `%s`", jsonObj.get("thread_cache_size").toString()));
      }
      if ((jsonObj.get("innodb_buffer_pool_size") != null && !jsonObj.get("innodb_buffer_pool_size").isJsonNull()) && !jsonObj.get("innodb_buffer_pool_size").isJsonPrimitive()) {
        throw new IllegalArgumentException(String.format("Expected the field `innodb_buffer_pool_size` to be a primitive type in the JSON string but got `%s`", jsonObj.get("innodb_buffer_pool_size").toString()));
      }
      if ((jsonObj.get("auto_increment_increment") != null && !jsonObj.get("auto_increment_increment").isJsonNull()) && !jsonObj.get("auto_increment_increment").isJsonPrimitive()) {
        throw new IllegalArgumentException(String.format("Expected the field `auto_increment_increment` to be a primitive type in the JSON string but got `%s`", jsonObj.get("auto_increment_increment").toString()));
      }
      if ((jsonObj.get("auto_increment_offset") != null && !jsonObj.get("auto_increment_offset").isJsonNull()) && !jsonObj.get("auto_increment_offset").isJsonPrimitive()) {
        throw new IllegalArgumentException(String.format("Expected the field `auto_increment_offset` to be a primitive type in the JSON string but got `%s`", jsonObj.get("auto_increment_offset").toString()));
      }
      if ((jsonObj.get("innodb_io_capacity") != null && !jsonObj.get("innodb_io_capacity").isJsonNull()) && !jsonObj.get("innodb_io_capacity").isJsonPrimitive()) {
        throw new IllegalArgumentException(String.format("Expected the field `innodb_io_capacity` to be a primitive type in the JSON string but got `%s`", jsonObj.get("innodb_io_capacity").toString()));
      }
      if ((jsonObj.get("innodb_purge_threads") != null && !jsonObj.get("innodb_purge_threads").isJsonNull()) && !jsonObj.get("innodb_purge_threads").isJsonPrimitive()) {
        throw new IllegalArgumentException(String.format("Expected the field `innodb_purge_threads` to be a primitive type in the JSON string but got `%s`", jsonObj.get("innodb_purge_threads").toString()));
      }
      if ((jsonObj.get("innodb_read_io_threads") != null && !jsonObj.get("innodb_read_io_threads").isJsonNull()) && !jsonObj.get("innodb_read_io_threads").isJsonPrimitive()) {
        throw new IllegalArgumentException(String.format("Expected the field `innodb_read_io_threads` to be a primitive type in the JSON string but got `%s`", jsonObj.get("innodb_read_io_threads").toString()));
      }
      if ((jsonObj.get("innodb_thread_concurrency") != null && !jsonObj.get("innodb_thread_concurrency").isJsonNull()) && !jsonObj.get("innodb_thread_concurrency").isJsonPrimitive()) {
        throw new IllegalArgumentException(String.format("Expected the field `innodb_thread_concurrency` to be a primitive type in the JSON string but got `%s`", jsonObj.get("innodb_thread_concurrency").toString()));
      }
      if ((jsonObj.get("innodb_write_io_threads") != null && !jsonObj.get("innodb_write_io_threads").isJsonNull()) && !jsonObj.get("innodb_write_io_threads").isJsonPrimitive()) {
        throw new IllegalArgumentException(String.format("Expected the field `innodb_write_io_threads` to be a primitive type in the JSON string but got `%s`", jsonObj.get("innodb_write_io_threads").toString()));
      }
      if ((jsonObj.get("innodb_log_file_size") != null && !jsonObj.get("innodb_log_file_size").isJsonNull()) && !jsonObj.get("innodb_log_file_size").isJsonPrimitive()) {
        throw new IllegalArgumentException(String.format("Expected the field `innodb_log_file_size` to be a primitive type in the JSON string but got `%s`", jsonObj.get("innodb_log_file_size").toString()));
      }
      if ((jsonObj.get("max_allowed_packet") != null && !jsonObj.get("max_allowed_packet").isJsonNull()) && !jsonObj.get("max_allowed_packet").isJsonPrimitive()) {
        throw new IllegalArgumentException(String.format("Expected the field `max_allowed_packet` to be a primitive type in the JSON string but got `%s`", jsonObj.get("max_allowed_packet").toString()));
      }
      if ((jsonObj.get("max_heap_table_size") != null && !jsonObj.get("max_heap_table_size").isJsonNull()) && !jsonObj.get("max_heap_table_size").isJsonPrimitive()) {
        throw new IllegalArgumentException(String.format("Expected the field `max_heap_table_size` to be a primitive type in the JSON string but got `%s`", jsonObj.get("max_heap_table_size").toString()));
      }
      if ((jsonObj.get("sql_mode") != null && !jsonObj.get("sql_mode").isJsonNull()) && !jsonObj.get("sql_mode").isJsonPrimitive()) {
        throw new IllegalArgumentException(String.format("Expected the field `sql_mode` to be a primitive type in the JSON string but got `%s`", jsonObj.get("sql_mode").toString()));
      }
      if ((jsonObj.get("query_cache_type") != null && !jsonObj.get("query_cache_type").isJsonNull()) && !jsonObj.get("query_cache_type").isJsonPrimitive()) {
        throw new IllegalArgumentException(String.format("Expected the field `query_cache_type` to be a primitive type in the JSON string but got `%s`", jsonObj.get("query_cache_type").toString()));
      }
      if ((jsonObj.get("query_cache_size") != null && !jsonObj.get("query_cache_size").isJsonNull()) && !jsonObj.get("query_cache_size").isJsonPrimitive()) {
        throw new IllegalArgumentException(String.format("Expected the field `query_cache_size` to be a primitive type in the JSON string but got `%s`", jsonObj.get("query_cache_size").toString()));
      }
      if ((jsonObj.get("innodb_flush_log_at_trx_commit") != null && !jsonObj.get("innodb_flush_log_at_trx_commit").isJsonNull()) && !jsonObj.get("innodb_flush_log_at_trx_commit").isJsonPrimitive()) {
        throw new IllegalArgumentException(String.format("Expected the field `innodb_flush_log_at_trx_commit` to be a primitive type in the JSON string but got `%s`", jsonObj.get("innodb_flush_log_at_trx_commit").toString()));
      }
      if ((jsonObj.get("transaction_isolation") != null && !jsonObj.get("transaction_isolation").isJsonNull()) && !jsonObj.get("transaction_isolation").isJsonPrimitive()) {
        throw new IllegalArgumentException(String.format("Expected the field `transaction_isolation` to be a primitive type in the JSON string but got `%s`", jsonObj.get("transaction_isolation").toString()));
      }
      if ((jsonObj.get("long_query_time") != null && !jsonObj.get("long_query_time").isJsonNull()) && !jsonObj.get("long_query_time").isJsonPrimitive()) {
        throw new IllegalArgumentException(String.format("Expected the field `long_query_time` to be a primitive type in the JSON string but got `%s`", jsonObj.get("long_query_time").toString()));
      }
      if ((jsonObj.get("tmp_table_size") != null && !jsonObj.get("tmp_table_size").isJsonNull()) && !jsonObj.get("tmp_table_size").isJsonPrimitive()) {
        throw new IllegalArgumentException(String.format("Expected the field `tmp_table_size` to be a primitive type in the JSON string but got `%s`", jsonObj.get("tmp_table_size").toString()));
      }
      if ((jsonObj.get("table_open_cache") != null && !jsonObj.get("table_open_cache").isJsonNull()) && !jsonObj.get("table_open_cache").isJsonPrimitive()) {
        throw new IllegalArgumentException(String.format("Expected the field `table_open_cache` to be a primitive type in the JSON string but got `%s`", jsonObj.get("table_open_cache").toString()));
      }
      if ((jsonObj.get("table_open_cache_instances") != null && !jsonObj.get("table_open_cache_instances").isJsonNull()) && !jsonObj.get("table_open_cache_instances").isJsonPrimitive()) {
        throw new IllegalArgumentException(String.format("Expected the field `table_open_cache_instances` to be a primitive type in the JSON string but got `%s`", jsonObj.get("table_open_cache_instances").toString()));
      }
      if ((jsonObj.get("innodb_flush_method") != null && !jsonObj.get("innodb_flush_method").isJsonNull()) && !jsonObj.get("innodb_flush_method").isJsonPrimitive()) {
        throw new IllegalArgumentException(String.format("Expected the field `innodb_flush_method` to be a primitive type in the JSON string but got `%s`", jsonObj.get("innodb_flush_method").toString()));
      }
      if ((jsonObj.get("innodb_strict_mode") != null && !jsonObj.get("innodb_strict_mode").isJsonNull()) && !jsonObj.get("innodb_strict_mode").isJsonPrimitive()) {
        throw new IllegalArgumentException(String.format("Expected the field `innodb_strict_mode` to be a primitive type in the JSON string but got `%s`", jsonObj.get("innodb_strict_mode").toString()));
      }
      if ((jsonObj.get("slow_query_log") != null && !jsonObj.get("slow_query_log").isJsonNull()) && !jsonObj.get("slow_query_log").isJsonPrimitive()) {
        throw new IllegalArgumentException(String.format("Expected the field `slow_query_log` to be a primitive type in the JSON string but got `%s`", jsonObj.get("slow_query_log").toString()));
      }
      if ((jsonObj.get("binlog_cache_size") != null && !jsonObj.get("binlog_cache_size").isJsonNull()) && !jsonObj.get("binlog_cache_size").isJsonPrimitive()) {
        throw new IllegalArgumentException(String.format("Expected the field `binlog_cache_size` to be a primitive type in the JSON string but got `%s`", jsonObj.get("binlog_cache_size").toString()));
      }
      if ((jsonObj.get("binlog_group_commit_sync_delay") != null && !jsonObj.get("binlog_group_commit_sync_delay").isJsonNull()) && !jsonObj.get("binlog_group_commit_sync_delay").isJsonPrimitive()) {
        throw new IllegalArgumentException(String.format("Expected the field `binlog_group_commit_sync_delay` to be a primitive type in the JSON string but got `%s`", jsonObj.get("binlog_group_commit_sync_delay").toString()));
      }
      if ((jsonObj.get("binlog_row_image") != null && !jsonObj.get("binlog_row_image").isJsonNull()) && !jsonObj.get("binlog_row_image").isJsonPrimitive()) {
        throw new IllegalArgumentException(String.format("Expected the field `binlog_row_image` to be a primitive type in the JSON string but got `%s`", jsonObj.get("binlog_row_image").toString()));
      }
      if ((jsonObj.get("binlog_rows_query_log_events") != null && !jsonObj.get("binlog_rows_query_log_events").isJsonNull()) && !jsonObj.get("binlog_rows_query_log_events").isJsonPrimitive()) {
        throw new IllegalArgumentException(String.format("Expected the field `binlog_rows_query_log_events` to be a primitive type in the JSON string but got `%s`", jsonObj.get("binlog_rows_query_log_events").toString()));
      }
      if ((jsonObj.get("character_set_server") != null && !jsonObj.get("character_set_server").isJsonNull()) && !jsonObj.get("character_set_server").isJsonPrimitive()) {
        throw new IllegalArgumentException(String.format("Expected the field `character_set_server` to be a primitive type in the JSON string but got `%s`", jsonObj.get("character_set_server").toString()));
      }
      if ((jsonObj.get("explicit_defaults_for_timestamp") != null && !jsonObj.get("explicit_defaults_for_timestamp").isJsonNull()) && !jsonObj.get("explicit_defaults_for_timestamp").isJsonPrimitive()) {
        throw new IllegalArgumentException(String.format("Expected the field `explicit_defaults_for_timestamp` to be a primitive type in the JSON string but got `%s`", jsonObj.get("explicit_defaults_for_timestamp").toString()));
      }
      if ((jsonObj.get("group_concat_max_len") != null && !jsonObj.get("group_concat_max_len").isJsonNull()) && !jsonObj.get("group_concat_max_len").isJsonPrimitive()) {
        throw new IllegalArgumentException(String.format("Expected the field `group_concat_max_len` to be a primitive type in the JSON string but got `%s`", jsonObj.get("group_concat_max_len").toString()));
      }
      if ((jsonObj.get("innodb_adaptive_hash_index") != null && !jsonObj.get("innodb_adaptive_hash_index").isJsonNull()) && !jsonObj.get("innodb_adaptive_hash_index").isJsonPrimitive()) {
        throw new IllegalArgumentException(String.format("Expected the field `innodb_adaptive_hash_index` to be a primitive type in the JSON string but got `%s`", jsonObj.get("innodb_adaptive_hash_index").toString()));
      }
      if ((jsonObj.get("innodb_lock_wait_timeout") != null && !jsonObj.get("innodb_lock_wait_timeout").isJsonNull()) && !jsonObj.get("innodb_lock_wait_timeout").isJsonPrimitive()) {
        throw new IllegalArgumentException(String.format("Expected the field `innodb_lock_wait_timeout` to be a primitive type in the JSON string but got `%s`", jsonObj.get("innodb_lock_wait_timeout").toString()));
      }
      if ((jsonObj.get("innodb_numa_interleave") != null && !jsonObj.get("innodb_numa_interleave").isJsonNull()) && !jsonObj.get("innodb_numa_interleave").isJsonPrimitive()) {
        throw new IllegalArgumentException(String.format("Expected the field `innodb_numa_interleave` to be a primitive type in the JSON string but got `%s`", jsonObj.get("innodb_numa_interleave").toString()));
      }
      if ((jsonObj.get("net_read_timeout") != null && !jsonObj.get("net_read_timeout").isJsonNull()) && !jsonObj.get("net_read_timeout").isJsonPrimitive()) {
        throw new IllegalArgumentException(String.format("Expected the field `net_read_timeout` to be a primitive type in the JSON string but got `%s`", jsonObj.get("net_read_timeout").toString()));
      }
      if ((jsonObj.get("net_write_timeout") != null && !jsonObj.get("net_write_timeout").isJsonNull()) && !jsonObj.get("net_write_timeout").isJsonPrimitive()) {
        throw new IllegalArgumentException(String.format("Expected the field `net_write_timeout` to be a primitive type in the JSON string but got `%s`", jsonObj.get("net_write_timeout").toString()));
      }
      if ((jsonObj.get("regexp_time_limit") != null && !jsonObj.get("regexp_time_limit").isJsonNull()) && !jsonObj.get("regexp_time_limit").isJsonPrimitive()) {
        throw new IllegalArgumentException(String.format("Expected the field `regexp_time_limit` to be a primitive type in the JSON string but got `%s`", jsonObj.get("regexp_time_limit").toString()));
      }
      if ((jsonObj.get("sync_binlog") != null && !jsonObj.get("sync_binlog").isJsonNull()) && !jsonObj.get("sync_binlog").isJsonPrimitive()) {
        throw new IllegalArgumentException(String.format("Expected the field `sync_binlog` to be a primitive type in the JSON string but got `%s`", jsonObj.get("sync_binlog").toString()));
      }
      if ((jsonObj.get("table_definition_cache") != null && !jsonObj.get("table_definition_cache").isJsonNull()) && !jsonObj.get("table_definition_cache").isJsonPrimitive()) {
        throw new IllegalArgumentException(String.format("Expected the field `table_definition_cache` to be a primitive type in the JSON string but got `%s`", jsonObj.get("table_definition_cache").toString()));
      }
      if ((jsonObj.get("log_bin_trust_function_creators") != null && !jsonObj.get("log_bin_trust_function_creators").isJsonNull()) && !jsonObj.get("log_bin_trust_function_creators").isJsonPrimitive()) {
        throw new IllegalArgumentException(String.format("Expected the field `log_bin_trust_function_creators` to be a primitive type in the JSON string but got `%s`", jsonObj.get("log_bin_trust_function_creators").toString()));
      }
      if ((jsonObj.get("skip_name_resolve") != null && !jsonObj.get("skip_name_resolve").isJsonNull()) && !jsonObj.get("skip_name_resolve").isJsonPrimitive()) {
        throw new IllegalArgumentException(String.format("Expected the field `skip_name_resolve` to be a primitive type in the JSON string but got `%s`", jsonObj.get("skip_name_resolve").toString()));
      }
      if ((jsonObj.get("innodb_redo_log_capacity") != null && !jsonObj.get("innodb_redo_log_capacity").isJsonNull()) && !jsonObj.get("innodb_redo_log_capacity").isJsonPrimitive()) {
        throw new IllegalArgumentException(String.format("Expected the field `innodb_redo_log_capacity` to be a primitive type in the JSON string but got `%s`", jsonObj.get("innodb_redo_log_capacity").toString()));
      }
      if ((jsonObj.get("wait_timeout") != null && !jsonObj.get("wait_timeout").isJsonNull()) && !jsonObj.get("wait_timeout").isJsonPrimitive()) {
        throw new IllegalArgumentException(String.format("Expected the field `wait_timeout` to be a primitive type in the JSON string but got `%s`", jsonObj.get("wait_timeout").toString()));
      }
      if ((jsonObj.get("interactive_timeout") != null && !jsonObj.get("interactive_timeout").isJsonNull()) && !jsonObj.get("interactive_timeout").isJsonPrimitive()) {
        throw new IllegalArgumentException(String.format("Expected the field `interactive_timeout` to be a primitive type in the JSON string but got `%s`", jsonObj.get("interactive_timeout").toString()));
      }
      if ((jsonObj.get("default-time-zone") != null && !jsonObj.get("default-time-zone").isJsonNull()) && !jsonObj.get("default-time-zone").isJsonPrimitive()) {
        throw new IllegalArgumentException(String.format("Expected the field `default-time-zone` to be a primitive type in the JSON string but got `%s`", jsonObj.get("default-time-zone").toString()));
      }
      if ((jsonObj.get("pxc_strict_mode") != null && !jsonObj.get("pxc_strict_mode").isJsonNull()) && !jsonObj.get("pxc_strict_mode").isJsonPrimitive()) {
        throw new IllegalArgumentException(String.format("Expected the field `pxc_strict_mode` to be a primitive type in the JSON string but got `%s`", jsonObj.get("pxc_strict_mode").toString()));
      }
  }

  public static class CustomTypeAdapterFactory implements TypeAdapterFactory {
    @SuppressWarnings("unchecked")
    @Override
    public <T> TypeAdapter<T> create(Gson gson, TypeToken<T> type) {
       if (!ConfigParametersMysql.class.isAssignableFrom(type.getRawType())) {
         return null; // this class only serializes 'ConfigParametersMysql' and its subtypes
       }
       final TypeAdapter<JsonElement> elementAdapter = gson.getAdapter(JsonElement.class);
       final TypeAdapter<ConfigParametersMysql> thisAdapter
                        = gson.getDelegateAdapter(this, TypeToken.get(ConfigParametersMysql.class));

       return (TypeAdapter<T>) new TypeAdapter<ConfigParametersMysql>() {
           @Override
           public void write(JsonWriter out, ConfigParametersMysql value) throws IOException {
             JsonObject obj = thisAdapter.toJsonTree(value).getAsJsonObject();
             elementAdapter.write(out, obj);
           }

           @Override
           public ConfigParametersMysql read(JsonReader in) throws IOException {
             JsonElement jsonElement = elementAdapter.read(in);
             validateJsonElement(jsonElement);
             return thisAdapter.fromJsonTree(jsonElement);
           }

       }.nullSafe();
    }
  }

 /**
  * Create an instance of ConfigParametersMysql given an JSON string
  *
  * @param jsonString JSON string
  * @return An instance of ConfigParametersMysql
  * @throws IOException if the JSON string is invalid with respect to ConfigParametersMysql
  */
  public static ConfigParametersMysql fromJson(String jsonString) throws IOException {
    return JSON.getGson().fromJson(jsonString, ConfigParametersMysql.class);
  }

 /**
  * Convert an instance of ConfigParametersMysql to an JSON string
  *
  * @return JSON string
  */
  public String toJson() {
    return JSON.getGson().toJson(this);
  }
}

