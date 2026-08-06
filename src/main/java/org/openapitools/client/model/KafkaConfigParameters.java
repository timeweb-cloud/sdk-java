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
 * Настройки топика Kafka. Все значения возвращаются в виде строк. Не заданные явно параметры возвращаются со значениями по умолчанию.
 */
@javax.annotation.Generated(value = "org.openapitools.codegen.languages.JavaClientCodegen", date = "2026-08-06T15:06:51.562821Z[Etc/UTC]")
public class KafkaConfigParameters {
  public static final String SERIALIZED_NAME_PARTITIONS = "partitions";
  @SerializedName(SERIALIZED_NAME_PARTITIONS)
  private String partitions;

  /**
   * Политика очистки старых сегментов лога: &#x60;delete&#x60; — удалять, &#x60;compact&#x60; — уплотнять.
   */
  @JsonAdapter(CleanupPolicyEnum.Adapter.class)
  public enum CleanupPolicyEnum {
    DELETE("delete"),
    
    COMPACT("compact");

    private String value;

    CleanupPolicyEnum(String value) {
      this.value = value;
    }

    public String getValue() {
      return value;
    }

    @Override
    public String toString() {
      return String.valueOf(value);
    }

    public static CleanupPolicyEnum fromValue(String value) {
      for (CleanupPolicyEnum b : CleanupPolicyEnum.values()) {
        if (b.value.equals(value)) {
          return b;
        }
      }
      throw new IllegalArgumentException("Unexpected value '" + value + "'");
    }

    public static class Adapter extends TypeAdapter<CleanupPolicyEnum> {
      @Override
      public void write(final JsonWriter jsonWriter, final CleanupPolicyEnum enumeration) throws IOException {
        jsonWriter.value(enumeration.getValue());
      }

      @Override
      public CleanupPolicyEnum read(final JsonReader jsonReader) throws IOException {
        String value =  jsonReader.nextString();
        return CleanupPolicyEnum.fromValue(value);
      }
    }
  }

  public static final String SERIALIZED_NAME_CLEANUP_POLICY = "cleanup_policy";
  @SerializedName(SERIALIZED_NAME_CLEANUP_POLICY)
  private CleanupPolicyEnum cleanupPolicy;

  /**
   * Тип сжатия сообщений в топике.
   */
  @JsonAdapter(CompressionTypeEnum.Adapter.class)
  public enum CompressionTypeEnum {
    UNCOMPRESSED("uncompressed"),
    
    ZSTD("zstd"),
    
    LZ4("lz4"),
    
    SNAPPY("snappy"),
    
    GZIP("gzip"),
    
    PRODUCER("producer");

    private String value;

    CompressionTypeEnum(String value) {
      this.value = value;
    }

    public String getValue() {
      return value;
    }

    @Override
    public String toString() {
      return String.valueOf(value);
    }

    public static CompressionTypeEnum fromValue(String value) {
      for (CompressionTypeEnum b : CompressionTypeEnum.values()) {
        if (b.value.equals(value)) {
          return b;
        }
      }
      throw new IllegalArgumentException("Unexpected value '" + value + "'");
    }

    public static class Adapter extends TypeAdapter<CompressionTypeEnum> {
      @Override
      public void write(final JsonWriter jsonWriter, final CompressionTypeEnum enumeration) throws IOException {
        jsonWriter.value(enumeration.getValue());
      }

      @Override
      public CompressionTypeEnum read(final JsonReader jsonReader) throws IOException {
        String value =  jsonReader.nextString();
        return CompressionTypeEnum.fromValue(value);
      }
    }
  }

  public static final String SERIALIZED_NAME_COMPRESSION_TYPE = "compression_type";
  @SerializedName(SERIALIZED_NAME_COMPRESSION_TYPE)
  private CompressionTypeEnum compressionType;

  public static final String SERIALIZED_NAME_DELETE_RETENTION_MS = "delete_retention_ms";
  @SerializedName(SERIALIZED_NAME_DELETE_RETENTION_MS)
  private String deleteRetentionMs;

  public static final String SERIALIZED_NAME_FILE_DELETE_DELAY_MS = "file_delete_delay_ms";
  @SerializedName(SERIALIZED_NAME_FILE_DELETE_DELAY_MS)
  private String fileDeleteDelayMs;

  public static final String SERIALIZED_NAME_FLUSH_MESSAGES = "flush_messages";
  @SerializedName(SERIALIZED_NAME_FLUSH_MESSAGES)
  private String flushMessages;

  public static final String SERIALIZED_NAME_FLUSH_MS = "flush_ms";
  @SerializedName(SERIALIZED_NAME_FLUSH_MS)
  private String flushMs;

  public static final String SERIALIZED_NAME_INDEX_INTERVAL_BYTES = "index_interval_bytes";
  @SerializedName(SERIALIZED_NAME_INDEX_INTERVAL_BYTES)
  private String indexIntervalBytes;

  public static final String SERIALIZED_NAME_MIN_COMPACTION_LAG_MS = "min_compaction_lag_ms";
  @SerializedName(SERIALIZED_NAME_MIN_COMPACTION_LAG_MS)
  private String minCompactionLagMs;

  public static final String SERIALIZED_NAME_MAX_COMPACTION_LAG_MS = "max_compaction_lag_ms";
  @SerializedName(SERIALIZED_NAME_MAX_COMPACTION_LAG_MS)
  private String maxCompactionLagMs;

  public static final String SERIALIZED_NAME_MAX_MESSAGE_BYTES = "max_message_bytes";
  @SerializedName(SERIALIZED_NAME_MAX_MESSAGE_BYTES)
  private String maxMessageBytes;

  public static final String SERIALIZED_NAME_MESSAGE_FORMAT_VERSION = "message_format_version";
  @SerializedName(SERIALIZED_NAME_MESSAGE_FORMAT_VERSION)
  private String messageFormatVersion;

  public static final String SERIALIZED_NAME_MESSAGE_TIMESTAMP_DIFFERENCE_MAX_MS = "message_timestamp_difference_max_ms";
  @SerializedName(SERIALIZED_NAME_MESSAGE_TIMESTAMP_DIFFERENCE_MAX_MS)
  private String messageTimestampDifferenceMaxMs;

  /**
   * Понижение версии формата сообщений для старых клиентов.
   */
  @JsonAdapter(MessageDownconversionEnableEnum.Adapter.class)
  public enum MessageDownconversionEnableEnum {
    ON("ON"),
    
    OFF("OFF");

    private String value;

    MessageDownconversionEnableEnum(String value) {
      this.value = value;
    }

    public String getValue() {
      return value;
    }

    @Override
    public String toString() {
      return String.valueOf(value);
    }

    public static MessageDownconversionEnableEnum fromValue(String value) {
      for (MessageDownconversionEnableEnum b : MessageDownconversionEnableEnum.values()) {
        if (b.value.equals(value)) {
          return b;
        }
      }
      throw new IllegalArgumentException("Unexpected value '" + value + "'");
    }

    public static class Adapter extends TypeAdapter<MessageDownconversionEnableEnum> {
      @Override
      public void write(final JsonWriter jsonWriter, final MessageDownconversionEnableEnum enumeration) throws IOException {
        jsonWriter.value(enumeration.getValue());
      }

      @Override
      public MessageDownconversionEnableEnum read(final JsonReader jsonReader) throws IOException {
        String value =  jsonReader.nextString();
        return MessageDownconversionEnableEnum.fromValue(value);
      }
    }
  }

  public static final String SERIALIZED_NAME_MESSAGE_DOWNCONVERSION_ENABLE = "message_downconversion_enable";
  @SerializedName(SERIALIZED_NAME_MESSAGE_DOWNCONVERSION_ENABLE)
  private MessageDownconversionEnableEnum messageDownconversionEnable;

  /**
   * Источник временной метки сообщения: &#x60;CreateTime&#x60; — время создания сообщения клиентом, &#x60;LogAppendTime&#x60; — время добавления сообщения в лог брокером.
   */
  @JsonAdapter(MessageTimestampTypeEnum.Adapter.class)
  public enum MessageTimestampTypeEnum {
    CREATETIME("CreateTime"),
    
    LOGAPPENDTIME("LogAppendTime");

    private String value;

    MessageTimestampTypeEnum(String value) {
      this.value = value;
    }

    public String getValue() {
      return value;
    }

    @Override
    public String toString() {
      return String.valueOf(value);
    }

    public static MessageTimestampTypeEnum fromValue(String value) {
      for (MessageTimestampTypeEnum b : MessageTimestampTypeEnum.values()) {
        if (b.value.equals(value)) {
          return b;
        }
      }
      throw new IllegalArgumentException("Unexpected value '" + value + "'");
    }

    public static class Adapter extends TypeAdapter<MessageTimestampTypeEnum> {
      @Override
      public void write(final JsonWriter jsonWriter, final MessageTimestampTypeEnum enumeration) throws IOException {
        jsonWriter.value(enumeration.getValue());
      }

      @Override
      public MessageTimestampTypeEnum read(final JsonReader jsonReader) throws IOException {
        String value =  jsonReader.nextString();
        return MessageTimestampTypeEnum.fromValue(value);
      }
    }
  }

  public static final String SERIALIZED_NAME_MESSAGE_TIMESTAMP_TYPE = "message_timestamp_type";
  @SerializedName(SERIALIZED_NAME_MESSAGE_TIMESTAMP_TYPE)
  private MessageTimestampTypeEnum messageTimestampType;

  public static final String SERIALIZED_NAME_MIN_CLEANABLE_DIRTY_RATIO = "min_cleanable_dirty_ratio";
  @SerializedName(SERIALIZED_NAME_MIN_CLEANABLE_DIRTY_RATIO)
  private String minCleanableDirtyRatio;

  public static final String SERIALIZED_NAME_MIN_INSYNC_REPLICAS = "min_insync_replicas";
  @SerializedName(SERIALIZED_NAME_MIN_INSYNC_REPLICAS)
  private String minInsyncReplicas;

  /**
   * Предварительное выделение места на диске при создании нового сегмента лога.
   */
  @JsonAdapter(PreallocateEnum.Adapter.class)
  public enum PreallocateEnum {
    ON("ON"),
    
    OFF("OFF");

    private String value;

    PreallocateEnum(String value) {
      this.value = value;
    }

    public String getValue() {
      return value;
    }

    @Override
    public String toString() {
      return String.valueOf(value);
    }

    public static PreallocateEnum fromValue(String value) {
      for (PreallocateEnum b : PreallocateEnum.values()) {
        if (b.value.equals(value)) {
          return b;
        }
      }
      throw new IllegalArgumentException("Unexpected value '" + value + "'");
    }

    public static class Adapter extends TypeAdapter<PreallocateEnum> {
      @Override
      public void write(final JsonWriter jsonWriter, final PreallocateEnum enumeration) throws IOException {
        jsonWriter.value(enumeration.getValue());
      }

      @Override
      public PreallocateEnum read(final JsonReader jsonReader) throws IOException {
        String value =  jsonReader.nextString();
        return PreallocateEnum.fromValue(value);
      }
    }
  }

  public static final String SERIALIZED_NAME_PREALLOCATE = "preallocate";
  @SerializedName(SERIALIZED_NAME_PREALLOCATE)
  private PreallocateEnum preallocate;

  public static final String SERIALIZED_NAME_RETENTION_BYTES = "retention_bytes";
  @SerializedName(SERIALIZED_NAME_RETENTION_BYTES)
  private String retentionBytes;

  public static final String SERIALIZED_NAME_RETENTION_MS = "retention_ms";
  @SerializedName(SERIALIZED_NAME_RETENTION_MS)
  private String retentionMs;

  public static final String SERIALIZED_NAME_SEGMENT_BYTES = "segment_bytes";
  @SerializedName(SERIALIZED_NAME_SEGMENT_BYTES)
  private String segmentBytes;

  public static final String SERIALIZED_NAME_SEGMENT_INDEX_BYTES = "segment_index_bytes";
  @SerializedName(SERIALIZED_NAME_SEGMENT_INDEX_BYTES)
  private String segmentIndexBytes;

  public static final String SERIALIZED_NAME_SEGMENT_JITTER_MS = "segment_jitter_ms";
  @SerializedName(SERIALIZED_NAME_SEGMENT_JITTER_MS)
  private String segmentJitterMs;

  public static final String SERIALIZED_NAME_SEGMENT_MS = "segment_ms";
  @SerializedName(SERIALIZED_NAME_SEGMENT_MS)
  private String segmentMs;

  /**
   * Возможность выбрать лидером партиции реплику, которая не входит в число синхронизированных.
   */
  @JsonAdapter(UncleanLeaderElectionEnableEnum.Adapter.class)
  public enum UncleanLeaderElectionEnableEnum {
    ON("ON"),
    
    OFF("OFF");

    private String value;

    UncleanLeaderElectionEnableEnum(String value) {
      this.value = value;
    }

    public String getValue() {
      return value;
    }

    @Override
    public String toString() {
      return String.valueOf(value);
    }

    public static UncleanLeaderElectionEnableEnum fromValue(String value) {
      for (UncleanLeaderElectionEnableEnum b : UncleanLeaderElectionEnableEnum.values()) {
        if (b.value.equals(value)) {
          return b;
        }
      }
      throw new IllegalArgumentException("Unexpected value '" + value + "'");
    }

    public static class Adapter extends TypeAdapter<UncleanLeaderElectionEnableEnum> {
      @Override
      public void write(final JsonWriter jsonWriter, final UncleanLeaderElectionEnableEnum enumeration) throws IOException {
        jsonWriter.value(enumeration.getValue());
      }

      @Override
      public UncleanLeaderElectionEnableEnum read(final JsonReader jsonReader) throws IOException {
        String value =  jsonReader.nextString();
        return UncleanLeaderElectionEnableEnum.fromValue(value);
      }
    }
  }

  public static final String SERIALIZED_NAME_UNCLEAN_LEADER_ELECTION_ENABLE = "unclean_leader_election_enable";
  @SerializedName(SERIALIZED_NAME_UNCLEAN_LEADER_ELECTION_ENABLE)
  private UncleanLeaderElectionEnableEnum uncleanLeaderElectionEnable;

  public KafkaConfigParameters() {
  }

  public KafkaConfigParameters partitions(String partitions) {
    
    this.partitions = partitions;
    return this;
  }

   /**
   * Количество партиций топика.
   * @return partitions
  **/
  @javax.annotation.Nullable
  public String getPartitions() {
    return partitions;
  }


  public void setPartitions(String partitions) {
    this.partitions = partitions;
  }


  public KafkaConfigParameters cleanupPolicy(CleanupPolicyEnum cleanupPolicy) {
    
    this.cleanupPolicy = cleanupPolicy;
    return this;
  }

   /**
   * Политика очистки старых сегментов лога: &#x60;delete&#x60; — удалять, &#x60;compact&#x60; — уплотнять.
   * @return cleanupPolicy
  **/
  @javax.annotation.Nullable
  public CleanupPolicyEnum getCleanupPolicy() {
    return cleanupPolicy;
  }


  public void setCleanupPolicy(CleanupPolicyEnum cleanupPolicy) {
    this.cleanupPolicy = cleanupPolicy;
  }


  public KafkaConfigParameters compressionType(CompressionTypeEnum compressionType) {
    
    this.compressionType = compressionType;
    return this;
  }

   /**
   * Тип сжатия сообщений в топике.
   * @return compressionType
  **/
  @javax.annotation.Nullable
  public CompressionTypeEnum getCompressionType() {
    return compressionType;
  }


  public void setCompressionType(CompressionTypeEnum compressionType) {
    this.compressionType = compressionType;
  }


  public KafkaConfigParameters deleteRetentionMs(String deleteRetentionMs) {
    
    this.deleteRetentionMs = deleteRetentionMs;
    return this;
  }

   /**
   * Время (в мс) хранения меток удаления для уплотняемых топиков.
   * @return deleteRetentionMs
  **/
  @javax.annotation.Nullable
  public String getDeleteRetentionMs() {
    return deleteRetentionMs;
  }


  public void setDeleteRetentionMs(String deleteRetentionMs) {
    this.deleteRetentionMs = deleteRetentionMs;
  }


  public KafkaConfigParameters fileDeleteDelayMs(String fileDeleteDelayMs) {
    
    this.fileDeleteDelayMs = fileDeleteDelayMs;
    return this;
  }

   /**
   * Задержка (в мс) перед удалением файла из файловой системы.
   * @return fileDeleteDelayMs
  **/
  @javax.annotation.Nullable
  public String getFileDeleteDelayMs() {
    return fileDeleteDelayMs;
  }


  public void setFileDeleteDelayMs(String fileDeleteDelayMs) {
    this.fileDeleteDelayMs = fileDeleteDelayMs;
  }


  public KafkaConfigParameters flushMessages(String flushMessages) {
    
    this.flushMessages = flushMessages;
    return this;
  }

   /**
   * Количество сообщений, после которого данные принудительно сбрасываются на диск.
   * @return flushMessages
  **/
  @javax.annotation.Nullable
  public String getFlushMessages() {
    return flushMessages;
  }


  public void setFlushMessages(String flushMessages) {
    this.flushMessages = flushMessages;
  }


  public KafkaConfigParameters flushMs(String flushMs) {
    
    this.flushMs = flushMs;
    return this;
  }

   /**
   * Интервал (в мс), после которого данные принудительно сбрасываются на диск.
   * @return flushMs
  **/
  @javax.annotation.Nullable
  public String getFlushMs() {
    return flushMs;
  }


  public void setFlushMs(String flushMs) {
    this.flushMs = flushMs;
  }


  public KafkaConfigParameters indexIntervalBytes(String indexIntervalBytes) {
    
    this.indexIntervalBytes = indexIntervalBytes;
    return this;
  }

   /**
   * Интервал (в байтах), с которым Kafka добавляет запись в индекс смещений.
   * @return indexIntervalBytes
  **/
  @javax.annotation.Nullable
  public String getIndexIntervalBytes() {
    return indexIntervalBytes;
  }


  public void setIndexIntervalBytes(String indexIntervalBytes) {
    this.indexIntervalBytes = indexIntervalBytes;
  }


  public KafkaConfigParameters minCompactionLagMs(String minCompactionLagMs) {
    
    this.minCompactionLagMs = minCompactionLagMs;
    return this;
  }

   /**
   * Минимальное время (в мс), в течение которого сообщение остается неуплотненным.
   * @return minCompactionLagMs
  **/
  @javax.annotation.Nullable
  public String getMinCompactionLagMs() {
    return minCompactionLagMs;
  }


  public void setMinCompactionLagMs(String minCompactionLagMs) {
    this.minCompactionLagMs = minCompactionLagMs;
  }


  public KafkaConfigParameters maxCompactionLagMs(String maxCompactionLagMs) {
    
    this.maxCompactionLagMs = maxCompactionLagMs;
    return this;
  }

   /**
   * Максимальное время (в мс), в течение которого сообщение может оставаться неуплотненным.
   * @return maxCompactionLagMs
  **/
  @javax.annotation.Nullable
  public String getMaxCompactionLagMs() {
    return maxCompactionLagMs;
  }


  public void setMaxCompactionLagMs(String maxCompactionLagMs) {
    this.maxCompactionLagMs = maxCompactionLagMs;
  }


  public KafkaConfigParameters maxMessageBytes(String maxMessageBytes) {
    
    this.maxMessageBytes = maxMessageBytes;
    return this;
  }

   /**
   * Максимальный размер (в байтах) пакета сообщений.
   * @return maxMessageBytes
  **/
  @javax.annotation.Nullable
  public String getMaxMessageBytes() {
    return maxMessageBytes;
  }


  public void setMaxMessageBytes(String maxMessageBytes) {
    this.maxMessageBytes = maxMessageBytes;
  }


  public KafkaConfigParameters messageFormatVersion(String messageFormatVersion) {
    
    this.messageFormatVersion = messageFormatVersion;
    return this;
  }

   /**
   * Версия формата сообщений, в котором Kafka добавляет сообщения в лог.
   * @return messageFormatVersion
  **/
  @javax.annotation.Nullable
  public String getMessageFormatVersion() {
    return messageFormatVersion;
  }


  public void setMessageFormatVersion(String messageFormatVersion) {
    this.messageFormatVersion = messageFormatVersion;
  }


  public KafkaConfigParameters messageTimestampDifferenceMaxMs(String messageTimestampDifferenceMaxMs) {
    
    this.messageTimestampDifferenceMaxMs = messageTimestampDifferenceMaxMs;
    return this;
  }

   /**
   * Максимально допустимая разница (в мс) между временной меткой сообщения и временем его получения брокером.
   * @return messageTimestampDifferenceMaxMs
  **/
  @javax.annotation.Nullable
  public String getMessageTimestampDifferenceMaxMs() {
    return messageTimestampDifferenceMaxMs;
  }


  public void setMessageTimestampDifferenceMaxMs(String messageTimestampDifferenceMaxMs) {
    this.messageTimestampDifferenceMaxMs = messageTimestampDifferenceMaxMs;
  }


  public KafkaConfigParameters messageDownconversionEnable(MessageDownconversionEnableEnum messageDownconversionEnable) {
    
    this.messageDownconversionEnable = messageDownconversionEnable;
    return this;
  }

   /**
   * Понижение версии формата сообщений для старых клиентов.
   * @return messageDownconversionEnable
  **/
  @javax.annotation.Nullable
  public MessageDownconversionEnableEnum getMessageDownconversionEnable() {
    return messageDownconversionEnable;
  }


  public void setMessageDownconversionEnable(MessageDownconversionEnableEnum messageDownconversionEnable) {
    this.messageDownconversionEnable = messageDownconversionEnable;
  }


  public KafkaConfigParameters messageTimestampType(MessageTimestampTypeEnum messageTimestampType) {
    
    this.messageTimestampType = messageTimestampType;
    return this;
  }

   /**
   * Источник временной метки сообщения: &#x60;CreateTime&#x60; — время создания сообщения клиентом, &#x60;LogAppendTime&#x60; — время добавления сообщения в лог брокером.
   * @return messageTimestampType
  **/
  @javax.annotation.Nullable
  public MessageTimestampTypeEnum getMessageTimestampType() {
    return messageTimestampType;
  }


  public void setMessageTimestampType(MessageTimestampTypeEnum messageTimestampType) {
    this.messageTimestampType = messageTimestampType;
  }


  public KafkaConfigParameters minCleanableDirtyRatio(String minCleanableDirtyRatio) {
    
    this.minCleanableDirtyRatio = minCleanableDirtyRatio;
    return this;
  }

   /**
   * Доля неуплотненных данных в логе, при которой запускается уплотнение.
   * @return minCleanableDirtyRatio
  **/
  @javax.annotation.Nullable
  public String getMinCleanableDirtyRatio() {
    return minCleanableDirtyRatio;
  }


  public void setMinCleanableDirtyRatio(String minCleanableDirtyRatio) {
    this.minCleanableDirtyRatio = minCleanableDirtyRatio;
  }


  public KafkaConfigParameters minInsyncReplicas(String minInsyncReplicas) {
    
    this.minInsyncReplicas = minInsyncReplicas;
    return this;
  }

   /**
   * Минимальное количество синхронизированных реплик, необходимое для подтверждения записи.
   * @return minInsyncReplicas
  **/
  @javax.annotation.Nullable
  public String getMinInsyncReplicas() {
    return minInsyncReplicas;
  }


  public void setMinInsyncReplicas(String minInsyncReplicas) {
    this.minInsyncReplicas = minInsyncReplicas;
  }


  public KafkaConfigParameters preallocate(PreallocateEnum preallocate) {
    
    this.preallocate = preallocate;
    return this;
  }

   /**
   * Предварительное выделение места на диске при создании нового сегмента лога.
   * @return preallocate
  **/
  @javax.annotation.Nullable
  public PreallocateEnum getPreallocate() {
    return preallocate;
  }


  public void setPreallocate(PreallocateEnum preallocate) {
    this.preallocate = preallocate;
  }


  public KafkaConfigParameters retentionBytes(String retentionBytes) {
    
    this.retentionBytes = retentionBytes;
    return this;
  }

   /**
   * Максимальный размер (в байтах) партиции топика, после которого старые сегменты удаляются. &#x60;-1&#x60; — без ограничения.
   * @return retentionBytes
  **/
  @javax.annotation.Nullable
  public String getRetentionBytes() {
    return retentionBytes;
  }


  public void setRetentionBytes(String retentionBytes) {
    this.retentionBytes = retentionBytes;
  }


  public KafkaConfigParameters retentionMs(String retentionMs) {
    
    this.retentionMs = retentionMs;
    return this;
  }

   /**
   * Время (в мс) хранения сообщений в топике. &#x60;-1&#x60; — хранить бессрочно.
   * @return retentionMs
  **/
  @javax.annotation.Nullable
  public String getRetentionMs() {
    return retentionMs;
  }


  public void setRetentionMs(String retentionMs) {
    this.retentionMs = retentionMs;
  }


  public KafkaConfigParameters segmentBytes(String segmentBytes) {
    
    this.segmentBytes = segmentBytes;
    return this;
  }

   /**
   * Максимальный размер (в байтах) одного сегмента лога.
   * @return segmentBytes
  **/
  @javax.annotation.Nullable
  public String getSegmentBytes() {
    return segmentBytes;
  }


  public void setSegmentBytes(String segmentBytes) {
    this.segmentBytes = segmentBytes;
  }


  public KafkaConfigParameters segmentIndexBytes(String segmentIndexBytes) {
    
    this.segmentIndexBytes = segmentIndexBytes;
    return this;
  }

   /**
   * Максимальный размер (в байтах) индексного файла сегмента лога.
   * @return segmentIndexBytes
  **/
  @javax.annotation.Nullable
  public String getSegmentIndexBytes() {
    return segmentIndexBytes;
  }


  public void setSegmentIndexBytes(String segmentIndexBytes) {
    this.segmentIndexBytes = segmentIndexBytes;
  }


  public KafkaConfigParameters segmentJitterMs(String segmentJitterMs) {
    
    this.segmentJitterMs = segmentJitterMs;
    return this;
  }

   /**
   * Максимальное случайное отклонение (в мс) от времени ротации сегмента.
   * @return segmentJitterMs
  **/
  @javax.annotation.Nullable
  public String getSegmentJitterMs() {
    return segmentJitterMs;
  }


  public void setSegmentJitterMs(String segmentJitterMs) {
    this.segmentJitterMs = segmentJitterMs;
  }


  public KafkaConfigParameters segmentMs(String segmentMs) {
    
    this.segmentMs = segmentMs;
    return this;
  }

   /**
   * Период (в мс), после которого Kafka создает новый сегмент лога.
   * @return segmentMs
  **/
  @javax.annotation.Nullable
  public String getSegmentMs() {
    return segmentMs;
  }


  public void setSegmentMs(String segmentMs) {
    this.segmentMs = segmentMs;
  }


  public KafkaConfigParameters uncleanLeaderElectionEnable(UncleanLeaderElectionEnableEnum uncleanLeaderElectionEnable) {
    
    this.uncleanLeaderElectionEnable = uncleanLeaderElectionEnable;
    return this;
  }

   /**
   * Возможность выбрать лидером партиции реплику, которая не входит в число синхронизированных.
   * @return uncleanLeaderElectionEnable
  **/
  @javax.annotation.Nullable
  public UncleanLeaderElectionEnableEnum getUncleanLeaderElectionEnable() {
    return uncleanLeaderElectionEnable;
  }


  public void setUncleanLeaderElectionEnable(UncleanLeaderElectionEnableEnum uncleanLeaderElectionEnable) {
    this.uncleanLeaderElectionEnable = uncleanLeaderElectionEnable;
  }



  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    KafkaConfigParameters kafkaConfigParameters = (KafkaConfigParameters) o;
    return Objects.equals(this.partitions, kafkaConfigParameters.partitions) &&
        Objects.equals(this.cleanupPolicy, kafkaConfigParameters.cleanupPolicy) &&
        Objects.equals(this.compressionType, kafkaConfigParameters.compressionType) &&
        Objects.equals(this.deleteRetentionMs, kafkaConfigParameters.deleteRetentionMs) &&
        Objects.equals(this.fileDeleteDelayMs, kafkaConfigParameters.fileDeleteDelayMs) &&
        Objects.equals(this.flushMessages, kafkaConfigParameters.flushMessages) &&
        Objects.equals(this.flushMs, kafkaConfigParameters.flushMs) &&
        Objects.equals(this.indexIntervalBytes, kafkaConfigParameters.indexIntervalBytes) &&
        Objects.equals(this.minCompactionLagMs, kafkaConfigParameters.minCompactionLagMs) &&
        Objects.equals(this.maxCompactionLagMs, kafkaConfigParameters.maxCompactionLagMs) &&
        Objects.equals(this.maxMessageBytes, kafkaConfigParameters.maxMessageBytes) &&
        Objects.equals(this.messageFormatVersion, kafkaConfigParameters.messageFormatVersion) &&
        Objects.equals(this.messageTimestampDifferenceMaxMs, kafkaConfigParameters.messageTimestampDifferenceMaxMs) &&
        Objects.equals(this.messageDownconversionEnable, kafkaConfigParameters.messageDownconversionEnable) &&
        Objects.equals(this.messageTimestampType, kafkaConfigParameters.messageTimestampType) &&
        Objects.equals(this.minCleanableDirtyRatio, kafkaConfigParameters.minCleanableDirtyRatio) &&
        Objects.equals(this.minInsyncReplicas, kafkaConfigParameters.minInsyncReplicas) &&
        Objects.equals(this.preallocate, kafkaConfigParameters.preallocate) &&
        Objects.equals(this.retentionBytes, kafkaConfigParameters.retentionBytes) &&
        Objects.equals(this.retentionMs, kafkaConfigParameters.retentionMs) &&
        Objects.equals(this.segmentBytes, kafkaConfigParameters.segmentBytes) &&
        Objects.equals(this.segmentIndexBytes, kafkaConfigParameters.segmentIndexBytes) &&
        Objects.equals(this.segmentJitterMs, kafkaConfigParameters.segmentJitterMs) &&
        Objects.equals(this.segmentMs, kafkaConfigParameters.segmentMs) &&
        Objects.equals(this.uncleanLeaderElectionEnable, kafkaConfigParameters.uncleanLeaderElectionEnable);
  }

  @Override
  public int hashCode() {
    return Objects.hash(partitions, cleanupPolicy, compressionType, deleteRetentionMs, fileDeleteDelayMs, flushMessages, flushMs, indexIntervalBytes, minCompactionLagMs, maxCompactionLagMs, maxMessageBytes, messageFormatVersion, messageTimestampDifferenceMaxMs, messageDownconversionEnable, messageTimestampType, minCleanableDirtyRatio, minInsyncReplicas, preallocate, retentionBytes, retentionMs, segmentBytes, segmentIndexBytes, segmentJitterMs, segmentMs, uncleanLeaderElectionEnable);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class KafkaConfigParameters {\n");
    sb.append("    partitions: ").append(toIndentedString(partitions)).append("\n");
    sb.append("    cleanupPolicy: ").append(toIndentedString(cleanupPolicy)).append("\n");
    sb.append("    compressionType: ").append(toIndentedString(compressionType)).append("\n");
    sb.append("    deleteRetentionMs: ").append(toIndentedString(deleteRetentionMs)).append("\n");
    sb.append("    fileDeleteDelayMs: ").append(toIndentedString(fileDeleteDelayMs)).append("\n");
    sb.append("    flushMessages: ").append(toIndentedString(flushMessages)).append("\n");
    sb.append("    flushMs: ").append(toIndentedString(flushMs)).append("\n");
    sb.append("    indexIntervalBytes: ").append(toIndentedString(indexIntervalBytes)).append("\n");
    sb.append("    minCompactionLagMs: ").append(toIndentedString(minCompactionLagMs)).append("\n");
    sb.append("    maxCompactionLagMs: ").append(toIndentedString(maxCompactionLagMs)).append("\n");
    sb.append("    maxMessageBytes: ").append(toIndentedString(maxMessageBytes)).append("\n");
    sb.append("    messageFormatVersion: ").append(toIndentedString(messageFormatVersion)).append("\n");
    sb.append("    messageTimestampDifferenceMaxMs: ").append(toIndentedString(messageTimestampDifferenceMaxMs)).append("\n");
    sb.append("    messageDownconversionEnable: ").append(toIndentedString(messageDownconversionEnable)).append("\n");
    sb.append("    messageTimestampType: ").append(toIndentedString(messageTimestampType)).append("\n");
    sb.append("    minCleanableDirtyRatio: ").append(toIndentedString(minCleanableDirtyRatio)).append("\n");
    sb.append("    minInsyncReplicas: ").append(toIndentedString(minInsyncReplicas)).append("\n");
    sb.append("    preallocate: ").append(toIndentedString(preallocate)).append("\n");
    sb.append("    retentionBytes: ").append(toIndentedString(retentionBytes)).append("\n");
    sb.append("    retentionMs: ").append(toIndentedString(retentionMs)).append("\n");
    sb.append("    segmentBytes: ").append(toIndentedString(segmentBytes)).append("\n");
    sb.append("    segmentIndexBytes: ").append(toIndentedString(segmentIndexBytes)).append("\n");
    sb.append("    segmentJitterMs: ").append(toIndentedString(segmentJitterMs)).append("\n");
    sb.append("    segmentMs: ").append(toIndentedString(segmentMs)).append("\n");
    sb.append("    uncleanLeaderElectionEnable: ").append(toIndentedString(uncleanLeaderElectionEnable)).append("\n");
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
    openapiFields.add("partitions");
    openapiFields.add("cleanup_policy");
    openapiFields.add("compression_type");
    openapiFields.add("delete_retention_ms");
    openapiFields.add("file_delete_delay_ms");
    openapiFields.add("flush_messages");
    openapiFields.add("flush_ms");
    openapiFields.add("index_interval_bytes");
    openapiFields.add("min_compaction_lag_ms");
    openapiFields.add("max_compaction_lag_ms");
    openapiFields.add("max_message_bytes");
    openapiFields.add("message_format_version");
    openapiFields.add("message_timestamp_difference_max_ms");
    openapiFields.add("message_downconversion_enable");
    openapiFields.add("message_timestamp_type");
    openapiFields.add("min_cleanable_dirty_ratio");
    openapiFields.add("min_insync_replicas");
    openapiFields.add("preallocate");
    openapiFields.add("retention_bytes");
    openapiFields.add("retention_ms");
    openapiFields.add("segment_bytes");
    openapiFields.add("segment_index_bytes");
    openapiFields.add("segment_jitter_ms");
    openapiFields.add("segment_ms");
    openapiFields.add("unclean_leader_election_enable");

    // a set of required properties/fields (JSON key names)
    openapiRequiredFields = new HashSet<String>();
  }

 /**
  * Validates the JSON Element and throws an exception if issues found
  *
  * @param jsonElement JSON Element
  * @throws IOException if the JSON Element is invalid with respect to KafkaConfigParameters
  */
  public static void validateJsonElement(JsonElement jsonElement) throws IOException {
      if (jsonElement == null) {
        if (!KafkaConfigParameters.openapiRequiredFields.isEmpty()) { // has required fields but JSON element is null
          throw new IllegalArgumentException(String.format("The required field(s) %s in KafkaConfigParameters is not found in the empty JSON string", KafkaConfigParameters.openapiRequiredFields.toString()));
        }
      }

      Set<Entry<String, JsonElement>> entries = jsonElement.getAsJsonObject().entrySet();
      // check to see if the JSON string contains additional fields
      for (Entry<String, JsonElement> entry : entries) {
        if (!KafkaConfigParameters.openapiFields.contains(entry.getKey())) {
          throw new IllegalArgumentException(String.format("The field `%s` in the JSON string is not defined in the `KafkaConfigParameters` properties. JSON: %s", entry.getKey(), jsonElement.toString()));
        }
      }
        JsonObject jsonObj = jsonElement.getAsJsonObject();
      if ((jsonObj.get("partitions") != null && !jsonObj.get("partitions").isJsonNull()) && !jsonObj.get("partitions").isJsonPrimitive()) {
        throw new IllegalArgumentException(String.format("Expected the field `partitions` to be a primitive type in the JSON string but got `%s`", jsonObj.get("partitions").toString()));
      }
      if ((jsonObj.get("cleanup_policy") != null && !jsonObj.get("cleanup_policy").isJsonNull()) && !jsonObj.get("cleanup_policy").isJsonPrimitive()) {
        throw new IllegalArgumentException(String.format("Expected the field `cleanup_policy` to be a primitive type in the JSON string but got `%s`", jsonObj.get("cleanup_policy").toString()));
      }
      if ((jsonObj.get("compression_type") != null && !jsonObj.get("compression_type").isJsonNull()) && !jsonObj.get("compression_type").isJsonPrimitive()) {
        throw new IllegalArgumentException(String.format("Expected the field `compression_type` to be a primitive type in the JSON string but got `%s`", jsonObj.get("compression_type").toString()));
      }
      if ((jsonObj.get("delete_retention_ms") != null && !jsonObj.get("delete_retention_ms").isJsonNull()) && !jsonObj.get("delete_retention_ms").isJsonPrimitive()) {
        throw new IllegalArgumentException(String.format("Expected the field `delete_retention_ms` to be a primitive type in the JSON string but got `%s`", jsonObj.get("delete_retention_ms").toString()));
      }
      if ((jsonObj.get("file_delete_delay_ms") != null && !jsonObj.get("file_delete_delay_ms").isJsonNull()) && !jsonObj.get("file_delete_delay_ms").isJsonPrimitive()) {
        throw new IllegalArgumentException(String.format("Expected the field `file_delete_delay_ms` to be a primitive type in the JSON string but got `%s`", jsonObj.get("file_delete_delay_ms").toString()));
      }
      if ((jsonObj.get("flush_messages") != null && !jsonObj.get("flush_messages").isJsonNull()) && !jsonObj.get("flush_messages").isJsonPrimitive()) {
        throw new IllegalArgumentException(String.format("Expected the field `flush_messages` to be a primitive type in the JSON string but got `%s`", jsonObj.get("flush_messages").toString()));
      }
      if ((jsonObj.get("flush_ms") != null && !jsonObj.get("flush_ms").isJsonNull()) && !jsonObj.get("flush_ms").isJsonPrimitive()) {
        throw new IllegalArgumentException(String.format("Expected the field `flush_ms` to be a primitive type in the JSON string but got `%s`", jsonObj.get("flush_ms").toString()));
      }
      if ((jsonObj.get("index_interval_bytes") != null && !jsonObj.get("index_interval_bytes").isJsonNull()) && !jsonObj.get("index_interval_bytes").isJsonPrimitive()) {
        throw new IllegalArgumentException(String.format("Expected the field `index_interval_bytes` to be a primitive type in the JSON string but got `%s`", jsonObj.get("index_interval_bytes").toString()));
      }
      if ((jsonObj.get("min_compaction_lag_ms") != null && !jsonObj.get("min_compaction_lag_ms").isJsonNull()) && !jsonObj.get("min_compaction_lag_ms").isJsonPrimitive()) {
        throw new IllegalArgumentException(String.format("Expected the field `min_compaction_lag_ms` to be a primitive type in the JSON string but got `%s`", jsonObj.get("min_compaction_lag_ms").toString()));
      }
      if ((jsonObj.get("max_compaction_lag_ms") != null && !jsonObj.get("max_compaction_lag_ms").isJsonNull()) && !jsonObj.get("max_compaction_lag_ms").isJsonPrimitive()) {
        throw new IllegalArgumentException(String.format("Expected the field `max_compaction_lag_ms` to be a primitive type in the JSON string but got `%s`", jsonObj.get("max_compaction_lag_ms").toString()));
      }
      if ((jsonObj.get("max_message_bytes") != null && !jsonObj.get("max_message_bytes").isJsonNull()) && !jsonObj.get("max_message_bytes").isJsonPrimitive()) {
        throw new IllegalArgumentException(String.format("Expected the field `max_message_bytes` to be a primitive type in the JSON string but got `%s`", jsonObj.get("max_message_bytes").toString()));
      }
      if ((jsonObj.get("message_format_version") != null && !jsonObj.get("message_format_version").isJsonNull()) && !jsonObj.get("message_format_version").isJsonPrimitive()) {
        throw new IllegalArgumentException(String.format("Expected the field `message_format_version` to be a primitive type in the JSON string but got `%s`", jsonObj.get("message_format_version").toString()));
      }
      if ((jsonObj.get("message_timestamp_difference_max_ms") != null && !jsonObj.get("message_timestamp_difference_max_ms").isJsonNull()) && !jsonObj.get("message_timestamp_difference_max_ms").isJsonPrimitive()) {
        throw new IllegalArgumentException(String.format("Expected the field `message_timestamp_difference_max_ms` to be a primitive type in the JSON string but got `%s`", jsonObj.get("message_timestamp_difference_max_ms").toString()));
      }
      if ((jsonObj.get("message_downconversion_enable") != null && !jsonObj.get("message_downconversion_enable").isJsonNull()) && !jsonObj.get("message_downconversion_enable").isJsonPrimitive()) {
        throw new IllegalArgumentException(String.format("Expected the field `message_downconversion_enable` to be a primitive type in the JSON string but got `%s`", jsonObj.get("message_downconversion_enable").toString()));
      }
      if ((jsonObj.get("message_timestamp_type") != null && !jsonObj.get("message_timestamp_type").isJsonNull()) && !jsonObj.get("message_timestamp_type").isJsonPrimitive()) {
        throw new IllegalArgumentException(String.format("Expected the field `message_timestamp_type` to be a primitive type in the JSON string but got `%s`", jsonObj.get("message_timestamp_type").toString()));
      }
      if ((jsonObj.get("min_cleanable_dirty_ratio") != null && !jsonObj.get("min_cleanable_dirty_ratio").isJsonNull()) && !jsonObj.get("min_cleanable_dirty_ratio").isJsonPrimitive()) {
        throw new IllegalArgumentException(String.format("Expected the field `min_cleanable_dirty_ratio` to be a primitive type in the JSON string but got `%s`", jsonObj.get("min_cleanable_dirty_ratio").toString()));
      }
      if ((jsonObj.get("min_insync_replicas") != null && !jsonObj.get("min_insync_replicas").isJsonNull()) && !jsonObj.get("min_insync_replicas").isJsonPrimitive()) {
        throw new IllegalArgumentException(String.format("Expected the field `min_insync_replicas` to be a primitive type in the JSON string but got `%s`", jsonObj.get("min_insync_replicas").toString()));
      }
      if ((jsonObj.get("preallocate") != null && !jsonObj.get("preallocate").isJsonNull()) && !jsonObj.get("preallocate").isJsonPrimitive()) {
        throw new IllegalArgumentException(String.format("Expected the field `preallocate` to be a primitive type in the JSON string but got `%s`", jsonObj.get("preallocate").toString()));
      }
      if ((jsonObj.get("retention_bytes") != null && !jsonObj.get("retention_bytes").isJsonNull()) && !jsonObj.get("retention_bytes").isJsonPrimitive()) {
        throw new IllegalArgumentException(String.format("Expected the field `retention_bytes` to be a primitive type in the JSON string but got `%s`", jsonObj.get("retention_bytes").toString()));
      }
      if ((jsonObj.get("retention_ms") != null && !jsonObj.get("retention_ms").isJsonNull()) && !jsonObj.get("retention_ms").isJsonPrimitive()) {
        throw new IllegalArgumentException(String.format("Expected the field `retention_ms` to be a primitive type in the JSON string but got `%s`", jsonObj.get("retention_ms").toString()));
      }
      if ((jsonObj.get("segment_bytes") != null && !jsonObj.get("segment_bytes").isJsonNull()) && !jsonObj.get("segment_bytes").isJsonPrimitive()) {
        throw new IllegalArgumentException(String.format("Expected the field `segment_bytes` to be a primitive type in the JSON string but got `%s`", jsonObj.get("segment_bytes").toString()));
      }
      if ((jsonObj.get("segment_index_bytes") != null && !jsonObj.get("segment_index_bytes").isJsonNull()) && !jsonObj.get("segment_index_bytes").isJsonPrimitive()) {
        throw new IllegalArgumentException(String.format("Expected the field `segment_index_bytes` to be a primitive type in the JSON string but got `%s`", jsonObj.get("segment_index_bytes").toString()));
      }
      if ((jsonObj.get("segment_jitter_ms") != null && !jsonObj.get("segment_jitter_ms").isJsonNull()) && !jsonObj.get("segment_jitter_ms").isJsonPrimitive()) {
        throw new IllegalArgumentException(String.format("Expected the field `segment_jitter_ms` to be a primitive type in the JSON string but got `%s`", jsonObj.get("segment_jitter_ms").toString()));
      }
      if ((jsonObj.get("segment_ms") != null && !jsonObj.get("segment_ms").isJsonNull()) && !jsonObj.get("segment_ms").isJsonPrimitive()) {
        throw new IllegalArgumentException(String.format("Expected the field `segment_ms` to be a primitive type in the JSON string but got `%s`", jsonObj.get("segment_ms").toString()));
      }
      if ((jsonObj.get("unclean_leader_election_enable") != null && !jsonObj.get("unclean_leader_election_enable").isJsonNull()) && !jsonObj.get("unclean_leader_election_enable").isJsonPrimitive()) {
        throw new IllegalArgumentException(String.format("Expected the field `unclean_leader_election_enable` to be a primitive type in the JSON string but got `%s`", jsonObj.get("unclean_leader_election_enable").toString()));
      }
  }

  public static class CustomTypeAdapterFactory implements TypeAdapterFactory {
    @SuppressWarnings("unchecked")
    @Override
    public <T> TypeAdapter<T> create(Gson gson, TypeToken<T> type) {
       if (!KafkaConfigParameters.class.isAssignableFrom(type.getRawType())) {
         return null; // this class only serializes 'KafkaConfigParameters' and its subtypes
       }
       final TypeAdapter<JsonElement> elementAdapter = gson.getAdapter(JsonElement.class);
       final TypeAdapter<KafkaConfigParameters> thisAdapter
                        = gson.getDelegateAdapter(this, TypeToken.get(KafkaConfigParameters.class));

       return (TypeAdapter<T>) new TypeAdapter<KafkaConfigParameters>() {
           @Override
           public void write(JsonWriter out, KafkaConfigParameters value) throws IOException {
             JsonObject obj = thisAdapter.toJsonTree(value).getAsJsonObject();
             elementAdapter.write(out, obj);
           }

           @Override
           public KafkaConfigParameters read(JsonReader in) throws IOException {
             JsonElement jsonElement = elementAdapter.read(in);
             validateJsonElement(jsonElement);
             return thisAdapter.fromJsonTree(jsonElement);
           }

       }.nullSafe();
    }
  }

 /**
  * Create an instance of KafkaConfigParameters given an JSON string
  *
  * @param jsonString JSON string
  * @return An instance of KafkaConfigParameters
  * @throws IOException if the JSON string is invalid with respect to KafkaConfigParameters
  */
  public static KafkaConfigParameters fromJson(String jsonString) throws IOException {
    return JSON.getGson().fromJson(jsonString, KafkaConfigParameters.class);
  }

 /**
  * Convert an instance of KafkaConfigParameters to an JSON string
  *
  * @return JSON string
  */
  public String toJson() {
    return JSON.getGson().toJson(this);
  }
}

