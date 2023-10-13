/*
 * Timeweb Cloud API
 * # Введение API Timeweb Cloud позволяет вам управлять ресурсами в облаке программным способом с использованием обычных HTTP-запросов.  Множество функций, которые доступны в панели управления Timeweb Cloud, также доступны через API, что позволяет вам автоматизировать ваши собственные сценарии.  В этой документации сперва будет описан общий дизайн и принципы работы API, а после этого конкретные конечные точки. Также будут приведены примеры запросов к ним.   ## Запросы Запросы должны выполняться по протоколу `HTTPS`, чтобы гарантировать шифрование транзакций. Поддерживаются следующие методы запроса: |Метод|Применение| |--- |--- | |GET|Извлекает данные о коллекциях и отдельных ресурсах.| |POST|Для коллекций создает новый ресурс этого типа. Также используется для выполнения действий с конкретным ресурсом.| |PUT|Обновляет существующий ресурс.| |PATCH|Некоторые ресурсы поддерживают частичное обновление, то есть обновление только части атрибутов ресурса, в этом случае вместо метода PUT будет использован PATCH.| |DELETE|Удаляет ресурс.|  Методы `POST`, `PUT` и `PATCH` могут включать объект в тело запроса с типом содержимого `application/json`.  ### Параметры в запросах Некоторые коллекции поддерживают пагинацию, поиск или сортировку в запросах. В параметрах запроса требуется передать: - `limit` — обозначает количество записей, которое необходимо вернуть  - `offset` — указывает на смещение, относительно начала списка  - `search` — позволяет указать набор символов для поиска  - `sort` — можно задать правило сортировки коллекции  ## Ответы Запросы вернут один из следующих кодов состояния ответа HTTP:  |Статус|Описание| |--- |--- | |200 OK|Действие с ресурсом было выполнено успешно.| |201 Created|Ресурс был успешно создан. При этом ресурс может быть как уже готовым к использованию, так и находиться в процессе запуска.| |204 No Content|Действие с ресурсом было выполнено успешно, и ответ не содержит дополнительной информации в теле.| |400 Bad Request|Был отправлен неверный запрос, например, в нем отсутствуют обязательные параметры и т. д. Тело ответа будет содержать дополнительную информацию об ошибке.| |401 Unauthorized|Ошибка аутентификации.| |403 Forbidden|Аутентификация прошла успешно, но недостаточно прав для выполнения действия.| |404 Not Found|Запрашиваемый ресурс не найден.| |409 Conflict|Запрос конфликтует с текущим состоянием.| |423 Locked|Ресурс из запроса заблокирован от применения к нему указанного метода.| |429 Too Many Requests|Был достигнут лимит по количеству запросов в единицу времени.| |500 Internal Server Error|При выполнении запроса произошла какая-то внутренняя ошибка. Чтобы решить эту проблему, лучше всего создать тикет в панели управления.|  ### Структура успешного ответа Все конечные точки будут возвращать данные в формате `JSON`. Ответы на `GET`-запросы будут иметь на верхнем уровне следующую структуру атрибутов:  |Название поля|Тип|Описание| |--- |--- |--- | |[entity_name]|object, object[], string[], number[], boolean|Динамическое поле, которое будет меняться в зависимости от запрашиваемого ресурса и будет содержать все атрибуты, необходимые для описания этого ресурса. Например, при запросе списка баз данных будет возвращаться поле `dbs`, а при запросе конкретного облачного сервера `server`. Для некоторых конечных точек в ответе может возвращаться сразу несколько ресурсов.| |meta|object|Опционально. Объект, который содержит вспомогательную информацию о ресурсе. Чаще всего будет встречаться при запросе коллекций и содержать поле `total`, которое будет указывать на количество элементов в коллекции.| |response_id|string|Опционально. В большинстве случаев в ответе будет содержаться уникальный идентификатор ответа в формате UUIDv4, который однозначно указывает на ваш запрос внутри нашей системы. Если вам потребуется задать вопрос нашей поддержке, приложите к вопросу этот идентификатор — так мы сможем найти ответ на него намного быстрее. Также вы можете использовать этот идентификатор, чтобы убедиться, что это новый ответ на запрос и результат не был получен из кэша.|  Пример запроса на получение списка SSH-ключей: ```     HTTP/2.0 200 OK     {       \"ssh_keys\":[           {             \"body\":\"ssh-rsa AAAAB3NzaC1sdfghjkOAsBwWhs= example@device.local\",             \"created_at\":\"2021-09-15T19:52:27Z\",             \"expired_at\":null,             \"id\":5297,             \"is_default\":false,             \"name\":\"example@device.local\",             \"used_at\":null,             \"used_by\":[]           }       ],       \"meta\":{           \"total\":1       },       \"response_id\":\"94608d15-8672-4eed-8ab6-28bd6fa3cdf7\"     } ```  ### Структура ответа с ошибкой |Название поля|Тип|Описание| |--- |--- |--- | |status_code|number|Короткий числовой идентификатор ошибки.| |error_code|string|Короткий текстовый идентификатор ошибки, который уточняет числовой идентификатор и удобен для программной обработки. Самый простой пример — это код `not_found` для ошибки 404.| |message|string, string[]|Опционально. В большинстве случаев в ответе будет содержаться человекочитаемое подробное описание ошибки или ошибок, которые помогут понять, что нужно исправить.| |response_id|string|Опционально. В большинстве случае в ответе будет содержаться уникальный идентификатор ответа в формате UUIDv4, который однозначно указывает на ваш запрос внутри нашей системы. Если вам потребуется задать вопрос нашей поддержке, приложите к вопросу этот идентификатор — так мы сможем найти ответ на него намного быстрее.|  Пример: ```     HTTP/2.0 403 Forbidden     {       \"status_code\": 403,       \"error_code\":  \"forbidden\",       \"message\":     \"You do not have access for the attempted action\",       \"response_id\": \"94608d15-8672-4eed-8ab6-28bd6fa3cdf7\"     } ```  ## Статусы ресурсов Важно учесть, что при создании большинства ресурсов внутри платформы вам будет сразу возвращен ответ от сервера со статусом `200 OK` или `201 Created` и идентификатором созданного ресурса в теле ответа, но при этом этот ресурс может быть ещё в *состоянии запуска*.  Для того чтобы понять, в каком состоянии сейчас находится ваш ресурс, мы добавили поле `status` в ответ на получение информации о ресурсе.  Список статусов будет отличаться в зависимости от типа ресурса. Увидеть поддерживаемый список статусов вы сможете в описании каждого конкретного ресурса.     ## Ограничение скорости запросов (Rate Limiting) Чтобы обеспечить стабильность для всех пользователей, Timeweb Cloud защищает API от всплесков входящего трафика, анализируя количество запросов c каждого аккаунта к каждой конечной точке.  Если ваше приложение отправляет более 20 запросов в секунду на одну конечную точку, то для этого запроса API может вернуть код состояния HTTP `429 Too Many Requests`.   ## Аутентификация Доступ к API осуществляется с помощью JWT-токена. Токенами можно управлять внутри панели управления Timeweb Cloud в разделе *API и Terraform*.  Токен необходимо передавать в заголовке каждого запроса в формате: ```   Authorization: Bearer $TIMEWEB_CLOUD_TOKEN ```  ## Формат примеров API Примеры в этой документации описаны с помощью `curl`, HTTP-клиента командной строки. На компьютерах `Linux` и `macOS` обычно по умолчанию установлен `curl`, и он доступен для загрузки на всех популярных платформах, включая `Windows`.  Каждый пример разделен на несколько строк символом `\\`, который совместим с `bash`. Типичный пример выглядит так: ```   curl -X PATCH      -H \"Content-Type: application/json\"      -H \"Authorization: Bearer $TIMEWEB_CLOUD_TOKEN\"      -d '{\"name\":\"Cute Corvus\",\"comment\":\"Development Server\"}'      \"https://api.timeweb.cloud/api/v1/dedicated/1051\" ``` - Параметр `-X` задает метод запроса. Для согласованности метод будет указан во всех примерах, даже если он явно не требуется для методов `GET`. - Строки `-H` задают требуемые HTTP-заголовки. - Примеры, для которых требуется объект JSON в теле запроса, передают требуемые данные через параметр `-d`.  Чтобы использовать приведенные примеры, не подставляя каждый раз в них свой токен, вы можете добавить токен один раз в переменные окружения в вашей консоли. Например, на `Linux` это можно сделать с помощью команды:  ``` TIMEWEB_CLOUD_TOKEN=\"token\" ```  После этого токен будет автоматически подставляться в ваши запросы.  Обратите внимание, что все значения в этой документации являются примерами. Не полагайтесь на идентификаторы операционных систем, тарифов и т.д., используемые в примерах. Используйте соответствующую конечную точку для получения значений перед созданием ресурсов.   ## Версионирование API построено согласно принципам [семантического версионирования](https://semver.org/lang/ru). Это значит, что мы гарантируем обратную совместимость всех изменений в пределах одной мажорной версии.  Мажорная версия каждой конечной точки обозначается в пути запроса, например, запрос `/api/v1/servers` указывает, что этот метод имеет версию 1.
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
 * ConfigParameters
 */
@javax.annotation.Generated(value = "org.openapitools.codegen.languages.JavaClientCodegen", date = "2023-10-13T16:26:14.779732Z[Etc/UTC]")
public class ConfigParameters {
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

  public static final String SERIALIZED_NAME_JOIN_BUFFER_SIZE = "join_buffer_size";
  @SerializedName(SERIALIZED_NAME_JOIN_BUFFER_SIZE)
  private String joinBufferSize;

  public static final String SERIALIZED_NAME_MAX_ALLOWED_PACKET = "max_allowed_packet";
  @SerializedName(SERIALIZED_NAME_MAX_ALLOWED_PACKET)
  private String maxAllowedPacket;

  public static final String SERIALIZED_NAME_MAX_HEAP_TABLE_SIZE = "max_heap_table_size";
  @SerializedName(SERIALIZED_NAME_MAX_HEAP_TABLE_SIZE)
  private String maxHeapTableSize;

  public static final String SERIALIZED_NAME_AUTOVACUUM_ANALYZE_SCALE_FACTOR = "autovacuum_analyze_scale_factor";
  @SerializedName(SERIALIZED_NAME_AUTOVACUUM_ANALYZE_SCALE_FACTOR)
  private String autovacuumAnalyzeScaleFactor;

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

  public static final String SERIALIZED_NAME_IDLE_SESSION_TIMEOUT = "idle_session_timeout";
  @SerializedName(SERIALIZED_NAME_IDLE_SESSION_TIMEOUT)
  private String idleSessionTimeout;

  public static final String SERIALIZED_NAME_JOIN_COLLAPSE_LIMIT = "join_collapse_limit";
  @SerializedName(SERIALIZED_NAME_JOIN_COLLAPSE_LIMIT)
  private String joinCollapseLimit;

  public static final String SERIALIZED_NAME_LOCK_TIMEOUT = "lock_timeout";
  @SerializedName(SERIALIZED_NAME_LOCK_TIMEOUT)
  private String lockTimeout;

  public static final String SERIALIZED_NAME_MAX_PREPARED_TRANSACTIONS = "max_prepared_transactions";
  @SerializedName(SERIALIZED_NAME_MAX_PREPARED_TRANSACTIONS)
  private String maxPreparedTransactions;

  public static final String SERIALIZED_NAME_MAX_CONNECTIONS = "max_connections";
  @SerializedName(SERIALIZED_NAME_MAX_CONNECTIONS)
  private String maxConnections;

  public static final String SERIALIZED_NAME_SHARED_BUFFERS = "shared_buffers";
  @SerializedName(SERIALIZED_NAME_SHARED_BUFFERS)
  private String sharedBuffers;

  public static final String SERIALIZED_NAME_WAL_BUFFERS = "wal_buffers";
  @SerializedName(SERIALIZED_NAME_WAL_BUFFERS)
  private String walBuffers;

  public static final String SERIALIZED_NAME_TEMP_BUFFERS = "temp_buffers";
  @SerializedName(SERIALIZED_NAME_TEMP_BUFFERS)
  private String tempBuffers;

  public static final String SERIALIZED_NAME_WORK_MEM = "work_mem";
  @SerializedName(SERIALIZED_NAME_WORK_MEM)
  private String workMem;

  public static final String SERIALIZED_NAME_SQL_MODE = "sql_mode";
  @SerializedName(SERIALIZED_NAME_SQL_MODE)
  private String sqlMode;

  public static final String SERIALIZED_NAME_QUERY_CACHE_TYPE = "query_cache_type";
  @SerializedName(SERIALIZED_NAME_QUERY_CACHE_TYPE)
  private String queryCacheType;

  public static final String SERIALIZED_NAME_QUERY_CACHE_SIZE = "query_cache_size";
  @SerializedName(SERIALIZED_NAME_QUERY_CACHE_SIZE)
  private String queryCacheSize;

  public ConfigParameters() {
  }

  public ConfigParameters autoIncrementIncrement(String autoIncrementIncrement) {
    
    this.autoIncrementIncrement = autoIncrementIncrement;
    return this;
  }

   /**
   * Интервал между значениями столбцов с атрибутом &#x60;AUTO_INCREMENT&#x60; (&#x60;mysql5&#x60; | &#x60;mysql&#x60;).
   * @return autoIncrementIncrement
  **/
  @javax.annotation.Nullable
  public String getAutoIncrementIncrement() {
    return autoIncrementIncrement;
  }


  public void setAutoIncrementIncrement(String autoIncrementIncrement) {
    this.autoIncrementIncrement = autoIncrementIncrement;
  }


  public ConfigParameters autoIncrementOffset(String autoIncrementOffset) {
    
    this.autoIncrementOffset = autoIncrementOffset;
    return this;
  }

   /**
   * Начальное значение для столбцов с атрибутом &#x60;AUTO_INCREMENT&#x60; (&#x60;mysql5&#x60; | &#x60;mysql&#x60;).
   * @return autoIncrementOffset
  **/
  @javax.annotation.Nullable
  public String getAutoIncrementOffset() {
    return autoIncrementOffset;
  }


  public void setAutoIncrementOffset(String autoIncrementOffset) {
    this.autoIncrementOffset = autoIncrementOffset;
  }


  public ConfigParameters innodbIoCapacity(String innodbIoCapacity) {
    
    this.innodbIoCapacity = innodbIoCapacity;
    return this;
  }

   /**
   * Количество операций ввода-вывода в секунду &#x60;IOPS&#x60; (&#x60;mysql5&#x60; | &#x60;mysql&#x60;).
   * @return innodbIoCapacity
  **/
  @javax.annotation.Nullable
  public String getInnodbIoCapacity() {
    return innodbIoCapacity;
  }


  public void setInnodbIoCapacity(String innodbIoCapacity) {
    this.innodbIoCapacity = innodbIoCapacity;
  }


  public ConfigParameters innodbPurgeThreads(String innodbPurgeThreads) {
    
    this.innodbPurgeThreads = innodbPurgeThreads;
    return this;
  }

   /**
   * Количество потоков ввода-вывода, используемых для операций очистки (&#x60;mysql5&#x60; | &#x60;mysql&#x60;).
   * @return innodbPurgeThreads
  **/
  @javax.annotation.Nullable
  public String getInnodbPurgeThreads() {
    return innodbPurgeThreads;
  }


  public void setInnodbPurgeThreads(String innodbPurgeThreads) {
    this.innodbPurgeThreads = innodbPurgeThreads;
  }


  public ConfigParameters innodbReadIoThreads(String innodbReadIoThreads) {
    
    this.innodbReadIoThreads = innodbReadIoThreads;
    return this;
  }

   /**
   * Количество потоков ввода-вывода, используемых для операций чтения (&#x60;mysql5&#x60; | &#x60;mysql&#x60;).
   * @return innodbReadIoThreads
  **/
  @javax.annotation.Nullable
  public String getInnodbReadIoThreads() {
    return innodbReadIoThreads;
  }


  public void setInnodbReadIoThreads(String innodbReadIoThreads) {
    this.innodbReadIoThreads = innodbReadIoThreads;
  }


  public ConfigParameters innodbThreadConcurrency(String innodbThreadConcurrency) {
    
    this.innodbThreadConcurrency = innodbThreadConcurrency;
    return this;
  }

   /**
   * Максимальное число потоков, которые могут исполняться (&#x60;mysql5&#x60; | &#x60;mysql&#x60;).
   * @return innodbThreadConcurrency
  **/
  @javax.annotation.Nullable
  public String getInnodbThreadConcurrency() {
    return innodbThreadConcurrency;
  }


  public void setInnodbThreadConcurrency(String innodbThreadConcurrency) {
    this.innodbThreadConcurrency = innodbThreadConcurrency;
  }


  public ConfigParameters innodbWriteIoThreads(String innodbWriteIoThreads) {
    
    this.innodbWriteIoThreads = innodbWriteIoThreads;
    return this;
  }

   /**
   * Количество потоков ввода-вывода, используемых для операций записи (&#x60;mysql5&#x60; | &#x60;mysql&#x60;).
   * @return innodbWriteIoThreads
  **/
  @javax.annotation.Nullable
  public String getInnodbWriteIoThreads() {
    return innodbWriteIoThreads;
  }


  public void setInnodbWriteIoThreads(String innodbWriteIoThreads) {
    this.innodbWriteIoThreads = innodbWriteIoThreads;
  }


  public ConfigParameters joinBufferSize(String joinBufferSize) {
    
    this.joinBufferSize = joinBufferSize;
    return this;
  }

   /**
   * Минимальный размер буфера (&#x60;mysql5&#x60; | &#x60;mysql&#x60;).
   * @return joinBufferSize
  **/
  @javax.annotation.Nullable
  public String getJoinBufferSize() {
    return joinBufferSize;
  }


  public void setJoinBufferSize(String joinBufferSize) {
    this.joinBufferSize = joinBufferSize;
  }


  public ConfigParameters maxAllowedPacket(String maxAllowedPacket) {
    
    this.maxAllowedPacket = maxAllowedPacket;
    return this;
  }

   /**
   * Максимальный размер одного пакета, строки или параметра, отправляемого функцией &#x60;mysql_stmt_send_long_data()&#x60; (&#x60;mysql5&#x60; | &#x60;mysql&#x60;).
   * @return maxAllowedPacket
  **/
  @javax.annotation.Nullable
  public String getMaxAllowedPacket() {
    return maxAllowedPacket;
  }


  public void setMaxAllowedPacket(String maxAllowedPacket) {
    this.maxAllowedPacket = maxAllowedPacket;
  }


  public ConfigParameters maxHeapTableSize(String maxHeapTableSize) {
    
    this.maxHeapTableSize = maxHeapTableSize;
    return this;
  }

   /**
   * Максимальный размер пользовательских MEMORY-таблиц (&#x60;mysql5&#x60; | &#x60;mysql&#x60;).
   * @return maxHeapTableSize
  **/
  @javax.annotation.Nullable
  public String getMaxHeapTableSize() {
    return maxHeapTableSize;
  }


  public void setMaxHeapTableSize(String maxHeapTableSize) {
    this.maxHeapTableSize = maxHeapTableSize;
  }


  public ConfigParameters autovacuumAnalyzeScaleFactor(String autovacuumAnalyzeScaleFactor) {
    
    this.autovacuumAnalyzeScaleFactor = autovacuumAnalyzeScaleFactor;
    return this;
  }

   /**
   * Доля измененных или удаленных записей в таблице, при которой процесс автоочистки выполнит команду &#x60;ANALYZE&#x60; (&#x60;postgres&#x60;).
   * @return autovacuumAnalyzeScaleFactor
  **/
  @javax.annotation.Nullable
  public String getAutovacuumAnalyzeScaleFactor() {
    return autovacuumAnalyzeScaleFactor;
  }


  public void setAutovacuumAnalyzeScaleFactor(String autovacuumAnalyzeScaleFactor) {
    this.autovacuumAnalyzeScaleFactor = autovacuumAnalyzeScaleFactor;
  }


  public ConfigParameters bgwriterDelay(String bgwriterDelay) {
    
    this.bgwriterDelay = bgwriterDelay;
    return this;
  }

   /**
   * Задержка между запусками процесса фоновой записи (&#x60;postgres&#x60;).
   * @return bgwriterDelay
  **/
  @javax.annotation.Nullable
  public String getBgwriterDelay() {
    return bgwriterDelay;
  }


  public void setBgwriterDelay(String bgwriterDelay) {
    this.bgwriterDelay = bgwriterDelay;
  }


  public ConfigParameters bgwriterLruMaxpages(String bgwriterLruMaxpages) {
    
    this.bgwriterLruMaxpages = bgwriterLruMaxpages;
    return this;
  }

   /**
   * Максимальное число элементов буферного кеша (&#x60;postgres&#x60;).
   * @return bgwriterLruMaxpages
  **/
  @javax.annotation.Nullable
  public String getBgwriterLruMaxpages() {
    return bgwriterLruMaxpages;
  }


  public void setBgwriterLruMaxpages(String bgwriterLruMaxpages) {
    this.bgwriterLruMaxpages = bgwriterLruMaxpages;
  }


  public ConfigParameters deadlockTimeout(String deadlockTimeout) {
    
    this.deadlockTimeout = deadlockTimeout;
    return this;
  }

   /**
   * Время ожидания, по истечении которого будет выполняться проверка состояния перекрестной блокировки (&#x60;postgres&#x60;).
   * @return deadlockTimeout
  **/
  @javax.annotation.Nullable
  public String getDeadlockTimeout() {
    return deadlockTimeout;
  }


  public void setDeadlockTimeout(String deadlockTimeout) {
    this.deadlockTimeout = deadlockTimeout;
  }


  public ConfigParameters ginPendingListLimit(String ginPendingListLimit) {
    
    this.ginPendingListLimit = ginPendingListLimit;
    return this;
  }

   /**
   * Максимальный размер очереди записей индекса &#x60;GIN&#x60; (&#x60;postgres&#x60;).
   * @return ginPendingListLimit
  **/
  @javax.annotation.Nullable
  public String getGinPendingListLimit() {
    return ginPendingListLimit;
  }


  public void setGinPendingListLimit(String ginPendingListLimit) {
    this.ginPendingListLimit = ginPendingListLimit;
  }


  public ConfigParameters idleInTransactionSessionTimeout(String idleInTransactionSessionTimeout) {
    
    this.idleInTransactionSessionTimeout = idleInTransactionSessionTimeout;
    return this;
  }

   /**
   * Время простоя открытой транзакции, при превышении которого будет завершена сессия с этой транзакцией (&#x60;postgres&#x60;).
   * @return idleInTransactionSessionTimeout
  **/
  @javax.annotation.Nullable
  public String getIdleInTransactionSessionTimeout() {
    return idleInTransactionSessionTimeout;
  }


  public void setIdleInTransactionSessionTimeout(String idleInTransactionSessionTimeout) {
    this.idleInTransactionSessionTimeout = idleInTransactionSessionTimeout;
  }


  public ConfigParameters idleSessionTimeout(String idleSessionTimeout) {
    
    this.idleSessionTimeout = idleSessionTimeout;
    return this;
  }

   /**
   * Время простоя не открытой транзакции, при превышении которого будет завершена сессия с этой транзакцией (&#x60;postgres&#x60;).
   * @return idleSessionTimeout
  **/
  @javax.annotation.Nullable
  public String getIdleSessionTimeout() {
    return idleSessionTimeout;
  }


  public void setIdleSessionTimeout(String idleSessionTimeout) {
    this.idleSessionTimeout = idleSessionTimeout;
  }


  public ConfigParameters joinCollapseLimit(String joinCollapseLimit) {
    
    this.joinCollapseLimit = joinCollapseLimit;
    return this;
  }

   /**
   * Значение количества элементов в списке &#x60;FROM&#x60; при превышении которого, планировщик будет переносить в список явные инструкции &#x60;JOIN&#x60; (&#x60;postgres&#x60;).
   * @return joinCollapseLimit
  **/
  @javax.annotation.Nullable
  public String getJoinCollapseLimit() {
    return joinCollapseLimit;
  }


  public void setJoinCollapseLimit(String joinCollapseLimit) {
    this.joinCollapseLimit = joinCollapseLimit;
  }


  public ConfigParameters lockTimeout(String lockTimeout) {
    
    this.lockTimeout = lockTimeout;
    return this;
  }

   /**
   * Время ожидания освобождения блокировки (&#x60;postgres&#x60;).
   * @return lockTimeout
  **/
  @javax.annotation.Nullable
  public String getLockTimeout() {
    return lockTimeout;
  }


  public void setLockTimeout(String lockTimeout) {
    this.lockTimeout = lockTimeout;
  }


  public ConfigParameters maxPreparedTransactions(String maxPreparedTransactions) {
    
    this.maxPreparedTransactions = maxPreparedTransactions;
    return this;
  }

   /**
   * Максимальное число транзакций, которые могут одновременно находиться в подготовленном состоянии (&#x60;postgres&#x60;).
   * @return maxPreparedTransactions
  **/
  @javax.annotation.Nullable
  public String getMaxPreparedTransactions() {
    return maxPreparedTransactions;
  }


  public void setMaxPreparedTransactions(String maxPreparedTransactions) {
    this.maxPreparedTransactions = maxPreparedTransactions;
  }


  public ConfigParameters maxConnections(String maxConnections) {
    
    this.maxConnections = maxConnections;
    return this;
  }

   /**
   * Допустимое количество соединений (&#x60;postgres&#x60; | &#x60;mysql&#x60;).
   * @return maxConnections
  **/
  @javax.annotation.Nullable
  public String getMaxConnections() {
    return maxConnections;
  }


  public void setMaxConnections(String maxConnections) {
    this.maxConnections = maxConnections;
  }


  public ConfigParameters sharedBuffers(String sharedBuffers) {
    
    this.sharedBuffers = sharedBuffers;
    return this;
  }

   /**
   * Устанавливает количество буферов общей памяти, используемых сервером (&#x60;postgres&#x60;).
   * @return sharedBuffers
  **/
  @javax.annotation.Nullable
  public String getSharedBuffers() {
    return sharedBuffers;
  }


  public void setSharedBuffers(String sharedBuffers) {
    this.sharedBuffers = sharedBuffers;
  }


  public ConfigParameters walBuffers(String walBuffers) {
    
    this.walBuffers = walBuffers;
    return this;
  }

   /**
   * Устанавливает количество буферов дисковых страниц в общей памяти для WAL (&#x60;postgres&#x60;).
   * @return walBuffers
  **/
  @javax.annotation.Nullable
  public String getWalBuffers() {
    return walBuffers;
  }


  public void setWalBuffers(String walBuffers) {
    this.walBuffers = walBuffers;
  }


  public ConfigParameters tempBuffers(String tempBuffers) {
    
    this.tempBuffers = tempBuffers;
    return this;
  }

   /**
   * Устанавливает максимальное количество временных буферов, используемых каждой сессией (&#x60;postgres&#x60;).
   * @return tempBuffers
  **/
  @javax.annotation.Nullable
  public String getTempBuffers() {
    return tempBuffers;
  }


  public void setTempBuffers(String tempBuffers) {
    this.tempBuffers = tempBuffers;
  }


  public ConfigParameters workMem(String workMem) {
    
    this.workMem = workMem;
    return this;
  }

   /**
   * Устанавливает максимальное количество памяти, используемое для рабочих пространств запросов (&#x60;postgres&#x60;).
   * @return workMem
  **/
  @javax.annotation.Nullable
  public String getWorkMem() {
    return workMem;
  }


  public void setWorkMem(String workMem) {
    this.workMem = workMem;
  }


  public ConfigParameters sqlMode(String sqlMode) {
    
    this.sqlMode = sqlMode;
    return this;
  }

   /**
   * Устанавливает режим SQL. Можно задать несколько режимов, разделяя их запятой. (&#x60;mysql&#x60;).
   * @return sqlMode
  **/
  @javax.annotation.Nullable
  public String getSqlMode() {
    return sqlMode;
  }


  public void setSqlMode(String sqlMode) {
    this.sqlMode = sqlMode;
  }


  public ConfigParameters queryCacheType(String queryCacheType) {
    
    this.queryCacheType = queryCacheType;
    return this;
  }

   /**
   * Параметр включает или отключает работу MySQL Query Cache (&#x60;mysql&#x60;).
   * @return queryCacheType
  **/
  @javax.annotation.Nullable
  public String getQueryCacheType() {
    return queryCacheType;
  }


  public void setQueryCacheType(String queryCacheType) {
    this.queryCacheType = queryCacheType;
  }


  public ConfigParameters queryCacheSize(String queryCacheSize) {
    
    this.queryCacheSize = queryCacheSize;
    return this;
  }

   /**
   * Размер в байтах, доступный для кэша запросов (&#x60;mysql&#x60;).
   * @return queryCacheSize
  **/
  @javax.annotation.Nullable
  public String getQueryCacheSize() {
    return queryCacheSize;
  }


  public void setQueryCacheSize(String queryCacheSize) {
    this.queryCacheSize = queryCacheSize;
  }



  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    ConfigParameters configParameters = (ConfigParameters) o;
    return Objects.equals(this.autoIncrementIncrement, configParameters.autoIncrementIncrement) &&
        Objects.equals(this.autoIncrementOffset, configParameters.autoIncrementOffset) &&
        Objects.equals(this.innodbIoCapacity, configParameters.innodbIoCapacity) &&
        Objects.equals(this.innodbPurgeThreads, configParameters.innodbPurgeThreads) &&
        Objects.equals(this.innodbReadIoThreads, configParameters.innodbReadIoThreads) &&
        Objects.equals(this.innodbThreadConcurrency, configParameters.innodbThreadConcurrency) &&
        Objects.equals(this.innodbWriteIoThreads, configParameters.innodbWriteIoThreads) &&
        Objects.equals(this.joinBufferSize, configParameters.joinBufferSize) &&
        Objects.equals(this.maxAllowedPacket, configParameters.maxAllowedPacket) &&
        Objects.equals(this.maxHeapTableSize, configParameters.maxHeapTableSize) &&
        Objects.equals(this.autovacuumAnalyzeScaleFactor, configParameters.autovacuumAnalyzeScaleFactor) &&
        Objects.equals(this.bgwriterDelay, configParameters.bgwriterDelay) &&
        Objects.equals(this.bgwriterLruMaxpages, configParameters.bgwriterLruMaxpages) &&
        Objects.equals(this.deadlockTimeout, configParameters.deadlockTimeout) &&
        Objects.equals(this.ginPendingListLimit, configParameters.ginPendingListLimit) &&
        Objects.equals(this.idleInTransactionSessionTimeout, configParameters.idleInTransactionSessionTimeout) &&
        Objects.equals(this.idleSessionTimeout, configParameters.idleSessionTimeout) &&
        Objects.equals(this.joinCollapseLimit, configParameters.joinCollapseLimit) &&
        Objects.equals(this.lockTimeout, configParameters.lockTimeout) &&
        Objects.equals(this.maxPreparedTransactions, configParameters.maxPreparedTransactions) &&
        Objects.equals(this.maxConnections, configParameters.maxConnections) &&
        Objects.equals(this.sharedBuffers, configParameters.sharedBuffers) &&
        Objects.equals(this.walBuffers, configParameters.walBuffers) &&
        Objects.equals(this.tempBuffers, configParameters.tempBuffers) &&
        Objects.equals(this.workMem, configParameters.workMem) &&
        Objects.equals(this.sqlMode, configParameters.sqlMode) &&
        Objects.equals(this.queryCacheType, configParameters.queryCacheType) &&
        Objects.equals(this.queryCacheSize, configParameters.queryCacheSize);
  }

  @Override
  public int hashCode() {
    return Objects.hash(autoIncrementIncrement, autoIncrementOffset, innodbIoCapacity, innodbPurgeThreads, innodbReadIoThreads, innodbThreadConcurrency, innodbWriteIoThreads, joinBufferSize, maxAllowedPacket, maxHeapTableSize, autovacuumAnalyzeScaleFactor, bgwriterDelay, bgwriterLruMaxpages, deadlockTimeout, ginPendingListLimit, idleInTransactionSessionTimeout, idleSessionTimeout, joinCollapseLimit, lockTimeout, maxPreparedTransactions, maxConnections, sharedBuffers, walBuffers, tempBuffers, workMem, sqlMode, queryCacheType, queryCacheSize);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class ConfigParameters {\n");
    sb.append("    autoIncrementIncrement: ").append(toIndentedString(autoIncrementIncrement)).append("\n");
    sb.append("    autoIncrementOffset: ").append(toIndentedString(autoIncrementOffset)).append("\n");
    sb.append("    innodbIoCapacity: ").append(toIndentedString(innodbIoCapacity)).append("\n");
    sb.append("    innodbPurgeThreads: ").append(toIndentedString(innodbPurgeThreads)).append("\n");
    sb.append("    innodbReadIoThreads: ").append(toIndentedString(innodbReadIoThreads)).append("\n");
    sb.append("    innodbThreadConcurrency: ").append(toIndentedString(innodbThreadConcurrency)).append("\n");
    sb.append("    innodbWriteIoThreads: ").append(toIndentedString(innodbWriteIoThreads)).append("\n");
    sb.append("    joinBufferSize: ").append(toIndentedString(joinBufferSize)).append("\n");
    sb.append("    maxAllowedPacket: ").append(toIndentedString(maxAllowedPacket)).append("\n");
    sb.append("    maxHeapTableSize: ").append(toIndentedString(maxHeapTableSize)).append("\n");
    sb.append("    autovacuumAnalyzeScaleFactor: ").append(toIndentedString(autovacuumAnalyzeScaleFactor)).append("\n");
    sb.append("    bgwriterDelay: ").append(toIndentedString(bgwriterDelay)).append("\n");
    sb.append("    bgwriterLruMaxpages: ").append(toIndentedString(bgwriterLruMaxpages)).append("\n");
    sb.append("    deadlockTimeout: ").append(toIndentedString(deadlockTimeout)).append("\n");
    sb.append("    ginPendingListLimit: ").append(toIndentedString(ginPendingListLimit)).append("\n");
    sb.append("    idleInTransactionSessionTimeout: ").append(toIndentedString(idleInTransactionSessionTimeout)).append("\n");
    sb.append("    idleSessionTimeout: ").append(toIndentedString(idleSessionTimeout)).append("\n");
    sb.append("    joinCollapseLimit: ").append(toIndentedString(joinCollapseLimit)).append("\n");
    sb.append("    lockTimeout: ").append(toIndentedString(lockTimeout)).append("\n");
    sb.append("    maxPreparedTransactions: ").append(toIndentedString(maxPreparedTransactions)).append("\n");
    sb.append("    maxConnections: ").append(toIndentedString(maxConnections)).append("\n");
    sb.append("    sharedBuffers: ").append(toIndentedString(sharedBuffers)).append("\n");
    sb.append("    walBuffers: ").append(toIndentedString(walBuffers)).append("\n");
    sb.append("    tempBuffers: ").append(toIndentedString(tempBuffers)).append("\n");
    sb.append("    workMem: ").append(toIndentedString(workMem)).append("\n");
    sb.append("    sqlMode: ").append(toIndentedString(sqlMode)).append("\n");
    sb.append("    queryCacheType: ").append(toIndentedString(queryCacheType)).append("\n");
    sb.append("    queryCacheSize: ").append(toIndentedString(queryCacheSize)).append("\n");
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
    openapiFields.add("auto_increment_increment");
    openapiFields.add("auto_increment_offset");
    openapiFields.add("innodb_io_capacity");
    openapiFields.add("innodb_purge_threads");
    openapiFields.add("innodb_read_io_threads");
    openapiFields.add("innodb_thread_concurrency");
    openapiFields.add("innodb_write_io_threads");
    openapiFields.add("join_buffer_size");
    openapiFields.add("max_allowed_packet");
    openapiFields.add("max_heap_table_size");
    openapiFields.add("autovacuum_analyze_scale_factor");
    openapiFields.add("bgwriter_delay");
    openapiFields.add("bgwriter_lru_maxpages");
    openapiFields.add("deadlock_timeout");
    openapiFields.add("gin_pending_list_limit");
    openapiFields.add("idle_in_transaction_session_timeout");
    openapiFields.add("idle_session_timeout");
    openapiFields.add("join_collapse_limit");
    openapiFields.add("lock_timeout");
    openapiFields.add("max_prepared_transactions");
    openapiFields.add("max_connections");
    openapiFields.add("shared_buffers");
    openapiFields.add("wal_buffers");
    openapiFields.add("temp_buffers");
    openapiFields.add("work_mem");
    openapiFields.add("sql_mode");
    openapiFields.add("query_cache_type");
    openapiFields.add("query_cache_size");

    // a set of required properties/fields (JSON key names)
    openapiRequiredFields = new HashSet<String>();
  }

 /**
  * Validates the JSON Element and throws an exception if issues found
  *
  * @param jsonElement JSON Element
  * @throws IOException if the JSON Element is invalid with respect to ConfigParameters
  */
  public static void validateJsonElement(JsonElement jsonElement) throws IOException {
      if (jsonElement == null) {
        if (!ConfigParameters.openapiRequiredFields.isEmpty()) { // has required fields but JSON element is null
          throw new IllegalArgumentException(String.format("The required field(s) %s in ConfigParameters is not found in the empty JSON string", ConfigParameters.openapiRequiredFields.toString()));
        }
      }

      Set<Entry<String, JsonElement>> entries = jsonElement.getAsJsonObject().entrySet();
      // check to see if the JSON string contains additional fields
      for (Entry<String, JsonElement> entry : entries) {
        if (!ConfigParameters.openapiFields.contains(entry.getKey())) {
          throw new IllegalArgumentException(String.format("The field `%s` in the JSON string is not defined in the `ConfigParameters` properties. JSON: %s", entry.getKey(), jsonElement.toString()));
        }
      }
        JsonObject jsonObj = jsonElement.getAsJsonObject();
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
      if ((jsonObj.get("join_buffer_size") != null && !jsonObj.get("join_buffer_size").isJsonNull()) && !jsonObj.get("join_buffer_size").isJsonPrimitive()) {
        throw new IllegalArgumentException(String.format("Expected the field `join_buffer_size` to be a primitive type in the JSON string but got `%s`", jsonObj.get("join_buffer_size").toString()));
      }
      if ((jsonObj.get("max_allowed_packet") != null && !jsonObj.get("max_allowed_packet").isJsonNull()) && !jsonObj.get("max_allowed_packet").isJsonPrimitive()) {
        throw new IllegalArgumentException(String.format("Expected the field `max_allowed_packet` to be a primitive type in the JSON string but got `%s`", jsonObj.get("max_allowed_packet").toString()));
      }
      if ((jsonObj.get("max_heap_table_size") != null && !jsonObj.get("max_heap_table_size").isJsonNull()) && !jsonObj.get("max_heap_table_size").isJsonPrimitive()) {
        throw new IllegalArgumentException(String.format("Expected the field `max_heap_table_size` to be a primitive type in the JSON string but got `%s`", jsonObj.get("max_heap_table_size").toString()));
      }
      if ((jsonObj.get("autovacuum_analyze_scale_factor") != null && !jsonObj.get("autovacuum_analyze_scale_factor").isJsonNull()) && !jsonObj.get("autovacuum_analyze_scale_factor").isJsonPrimitive()) {
        throw new IllegalArgumentException(String.format("Expected the field `autovacuum_analyze_scale_factor` to be a primitive type in the JSON string but got `%s`", jsonObj.get("autovacuum_analyze_scale_factor").toString()));
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
      if ((jsonObj.get("idle_session_timeout") != null && !jsonObj.get("idle_session_timeout").isJsonNull()) && !jsonObj.get("idle_session_timeout").isJsonPrimitive()) {
        throw new IllegalArgumentException(String.format("Expected the field `idle_session_timeout` to be a primitive type in the JSON string but got `%s`", jsonObj.get("idle_session_timeout").toString()));
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
      if ((jsonObj.get("max_connections") != null && !jsonObj.get("max_connections").isJsonNull()) && !jsonObj.get("max_connections").isJsonPrimitive()) {
        throw new IllegalArgumentException(String.format("Expected the field `max_connections` to be a primitive type in the JSON string but got `%s`", jsonObj.get("max_connections").toString()));
      }
      if ((jsonObj.get("shared_buffers") != null && !jsonObj.get("shared_buffers").isJsonNull()) && !jsonObj.get("shared_buffers").isJsonPrimitive()) {
        throw new IllegalArgumentException(String.format("Expected the field `shared_buffers` to be a primitive type in the JSON string but got `%s`", jsonObj.get("shared_buffers").toString()));
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
      if ((jsonObj.get("sql_mode") != null && !jsonObj.get("sql_mode").isJsonNull()) && !jsonObj.get("sql_mode").isJsonPrimitive()) {
        throw new IllegalArgumentException(String.format("Expected the field `sql_mode` to be a primitive type in the JSON string but got `%s`", jsonObj.get("sql_mode").toString()));
      }
      if ((jsonObj.get("query_cache_type") != null && !jsonObj.get("query_cache_type").isJsonNull()) && !jsonObj.get("query_cache_type").isJsonPrimitive()) {
        throw new IllegalArgumentException(String.format("Expected the field `query_cache_type` to be a primitive type in the JSON string but got `%s`", jsonObj.get("query_cache_type").toString()));
      }
      if ((jsonObj.get("query_cache_size") != null && !jsonObj.get("query_cache_size").isJsonNull()) && !jsonObj.get("query_cache_size").isJsonPrimitive()) {
        throw new IllegalArgumentException(String.format("Expected the field `query_cache_size` to be a primitive type in the JSON string but got `%s`", jsonObj.get("query_cache_size").toString()));
      }
  }

  public static class CustomTypeAdapterFactory implements TypeAdapterFactory {
    @SuppressWarnings("unchecked")
    @Override
    public <T> TypeAdapter<T> create(Gson gson, TypeToken<T> type) {
       if (!ConfigParameters.class.isAssignableFrom(type.getRawType())) {
         return null; // this class only serializes 'ConfigParameters' and its subtypes
       }
       final TypeAdapter<JsonElement> elementAdapter = gson.getAdapter(JsonElement.class);
       final TypeAdapter<ConfigParameters> thisAdapter
                        = gson.getDelegateAdapter(this, TypeToken.get(ConfigParameters.class));

       return (TypeAdapter<T>) new TypeAdapter<ConfigParameters>() {
           @Override
           public void write(JsonWriter out, ConfigParameters value) throws IOException {
             JsonObject obj = thisAdapter.toJsonTree(value).getAsJsonObject();
             elementAdapter.write(out, obj);
           }

           @Override
           public ConfigParameters read(JsonReader in) throws IOException {
             JsonElement jsonElement = elementAdapter.read(in);
             validateJsonElement(jsonElement);
             return thisAdapter.fromJsonTree(jsonElement);
           }

       }.nullSafe();
    }
  }

 /**
  * Create an instance of ConfigParameters given an JSON string
  *
  * @param jsonString JSON string
  * @return An instance of ConfigParameters
  * @throws IOException if the JSON string is invalid with respect to ConfigParameters
  */
  public static ConfigParameters fromJson(String jsonString) throws IOException {
    return JSON.getGson().fromJson(jsonString, ConfigParameters.class);
  }

 /**
  * Convert an instance of ConfigParameters to an JSON string
  *
  * @return JSON string
  */
  public String toJson() {
    return JSON.getGson().toJson(this);
  }
}

