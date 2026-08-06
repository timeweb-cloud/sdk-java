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
import java.math.BigDecimal;

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
 * Настройки топика Kafka. Передаются только для кластеров Kafka: для кластеров других типов запрос вернется с ошибкой &#x60;forbidden_change_configuration&#x60;. Не переданные параметры получают значения по умолчанию. Числовые значения можно передавать как числом, так и строкой.
 */
@javax.annotation.Generated(value = "org.openapitools.codegen.languages.JavaClientCodegen", date = "2026-08-06T15:06:51.562821Z[Etc/UTC]")
public class UpdateKafkaConfigParameters {
  public static final String SERIALIZED_NAME_PARTITIONS = "partitions";
  @SerializedName(SERIALIZED_NAME_PARTITIONS)
  private Integer partitions;

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
  private Long deleteRetentionMs;

  public static final String SERIALIZED_NAME_FILE_DELETE_DELAY_MS = "file_delete_delay_ms";
  @SerializedName(SERIALIZED_NAME_FILE_DELETE_DELAY_MS)
  private Long fileDeleteDelayMs;

  public static final String SERIALIZED_NAME_FLUSH_MESSAGES = "flush_messages";
  @SerializedName(SERIALIZED_NAME_FLUSH_MESSAGES)
  private Long flushMessages;

  public static final String SERIALIZED_NAME_FLUSH_MS = "flush_ms";
  @SerializedName(SERIALIZED_NAME_FLUSH_MS)
  private Long flushMs;

  public static final String SERIALIZED_NAME_INDEX_INTERVAL_BYTES = "index_interval_bytes";
  @SerializedName(SERIALIZED_NAME_INDEX_INTERVAL_BYTES)
  private Integer indexIntervalBytes;

  public static final String SERIALIZED_NAME_MIN_COMPACTION_LAG_MS = "min_compaction_lag_ms";
  @SerializedName(SERIALIZED_NAME_MIN_COMPACTION_LAG_MS)
  private Long minCompactionLagMs;

  public static final String SERIALIZED_NAME_MAX_COMPACTION_LAG_MS = "max_compaction_lag_ms";
  @SerializedName(SERIALIZED_NAME_MAX_COMPACTION_LAG_MS)
  private Long maxCompactionLagMs;

  public static final String SERIALIZED_NAME_MAX_MESSAGE_BYTES = "max_message_bytes";
  @SerializedName(SERIALIZED_NAME_MAX_MESSAGE_BYTES)
  private Integer maxMessageBytes;

  /**
   * Версия формата сообщений, в котором Kafka добавляет сообщения в лог.
   */
  @JsonAdapter(MessageFormatVersionEnum.Adapter.class)
  public enum MessageFormatVersionEnum {
    _0_8_0("0.8.0"),
    
    _0_8_1("0.8.1"),
    
    _0_8_2("0.8.2"),
    
    _0_9_0("0.9.0"),
    
    _0_10_0_IV0("0.10.0-IV0"),
    
    _0_10_0_IV1("0.10.0-IV1"),
    
    _0_10_1_IV0("0.10.1-IV0"),
    
    _0_10_1_IV1("0.10.1-IV1"),
    
    _0_10_1_IV2("0.10.1-IV2"),
    
    _0_10_2_IV0("0.10.2-IV0"),
    
    _0_11_0_IV0("0.11.0-IV0"),
    
    _0_11_0_IV1("0.11.0-IV1"),
    
    _0_11_0_IV2("0.11.0-IV2"),
    
    _1_0_IV0("1.0-IV0"),
    
    _1_1_IV0("1.1-IV0"),
    
    _2_0_IV0("2.0-IV0"),
    
    _2_0_IV1("2.0-IV1"),
    
    _2_1_IV0("2.1-IV0"),
    
    _2_1_IV1("2.1-IV1"),
    
    _2_1_IV2("2.1-IV2"),
    
    _2_2_IV0("2.2-IV0"),
    
    _2_2_IV1("2.2-IV1"),
    
    _2_3_IV0("2.3-IV0"),
    
    _2_3_IV1("2.3-IV1"),
    
    _2_4_IV0("2.4-IV0"),
    
    _2_4_IV1("2.4-IV1"),
    
    _2_5_IV0("2.5-IV0"),
    
    _2_6_IV0("2.6-IV0"),
    
    _2_7_IV0("2.7-IV0"),
    
    _2_7_IV1("2.7-IV1"),
    
    _2_7_IV2("2.7-IV2"),
    
    _2_8_IV0("2.8-IV0"),
    
    _2_8_IV1("2.8-IV1"),
    
    _3_0_IV0("3.0-IV0"),
    
    _3_0_IV1("3.0-IV1"),
    
    _3_1_IV0("3.1-IV0"),
    
    _3_2_IV0("3.2-IV0"),
    
    _3_3_IV0("3.3-IV0"),
    
    _3_3_IV1("3.3-IV1"),
    
    _3_3_IV2("3.3-IV2"),
    
    _3_3_IV3("3.3-IV3"),
    
    _3_4_IV0("3.4-IV0"),
    
    _3_5_IV0("3.5-IV0"),
    
    _3_5_IV1("3.5-IV1"),
    
    _3_5_IV2("3.5-IV2");

    private String value;

    MessageFormatVersionEnum(String value) {
      this.value = value;
    }

    public String getValue() {
      return value;
    }

    @Override
    public String toString() {
      return String.valueOf(value);
    }

    public static MessageFormatVersionEnum fromValue(String value) {
      for (MessageFormatVersionEnum b : MessageFormatVersionEnum.values()) {
        if (b.value.equals(value)) {
          return b;
        }
      }
      throw new IllegalArgumentException("Unexpected value '" + value + "'");
    }

    public static class Adapter extends TypeAdapter<MessageFormatVersionEnum> {
      @Override
      public void write(final JsonWriter jsonWriter, final MessageFormatVersionEnum enumeration) throws IOException {
        jsonWriter.value(enumeration.getValue());
      }

      @Override
      public MessageFormatVersionEnum read(final JsonReader jsonReader) throws IOException {
        String value =  jsonReader.nextString();
        return MessageFormatVersionEnum.fromValue(value);
      }
    }
  }

  public static final String SERIALIZED_NAME_MESSAGE_FORMAT_VERSION = "message_format_version";
  @SerializedName(SERIALIZED_NAME_MESSAGE_FORMAT_VERSION)
  private MessageFormatVersionEnum messageFormatVersion;

  public static final String SERIALIZED_NAME_MESSAGE_TIMESTAMP_DIFFERENCE_MAX_MS = "message_timestamp_difference_max_ms";
  @SerializedName(SERIALIZED_NAME_MESSAGE_TIMESTAMP_DIFFERENCE_MAX_MS)
  private Long messageTimestampDifferenceMaxMs;

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
  private BigDecimal minCleanableDirtyRatio;

  public static final String SERIALIZED_NAME_MIN_INSYNC_REPLICAS = "min_insync_replicas";
  @SerializedName(SERIALIZED_NAME_MIN_INSYNC_REPLICAS)
  private Integer minInsyncReplicas;

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
  private Long retentionBytes;

  public static final String SERIALIZED_NAME_RETENTION_MS = "retention_ms";
  @SerializedName(SERIALIZED_NAME_RETENTION_MS)
  private Long retentionMs;

  public static final String SERIALIZED_NAME_SEGMENT_BYTES = "segment_bytes";
  @SerializedName(SERIALIZED_NAME_SEGMENT_BYTES)
  private Integer segmentBytes;

  public static final String SERIALIZED_NAME_SEGMENT_INDEX_BYTES = "segment_index_bytes";
  @SerializedName(SERIALIZED_NAME_SEGMENT_INDEX_BYTES)
  private Integer segmentIndexBytes;

  public static final String SERIALIZED_NAME_SEGMENT_JITTER_MS = "segment_jitter_ms";
  @SerializedName(SERIALIZED_NAME_SEGMENT_JITTER_MS)
  private Long segmentJitterMs;

  public static final String SERIALIZED_NAME_SEGMENT_MS = "segment_ms";
  @SerializedName(SERIALIZED_NAME_SEGMENT_MS)
  private Long segmentMs;

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

  public UpdateKafkaConfigParameters() {
  }

  public UpdateKafkaConfigParameters partitions(Integer partitions) {
    
    this.partitions = partitions;
    return this;
  }

   /**
   * Количество партиций топика. Количество партиций нельзя уменьшить: если передать значение меньше текущего, останется текущее.
   * minimum: 1
   * maximum: 4294967295
   * @return partitions
  **/
  @javax.annotation.Nullable
  public Integer getPartitions() {
    return partitions;
  }


  public void setPartitions(Integer partitions) {
    this.partitions = partitions;
  }


  public UpdateKafkaConfigParameters cleanupPolicy(CleanupPolicyEnum cleanupPolicy) {
    
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


  public UpdateKafkaConfigParameters compressionType(CompressionTypeEnum compressionType) {
    
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


  public UpdateKafkaConfigParameters deleteRetentionMs(Long deleteRetentionMs) {
    
    this.deleteRetentionMs = deleteRetentionMs;
    return this;
  }

   /**
   * Время (в мс) хранения меток удаления для уплотняемых топиков. Максимальное значение — 9223372036854775807.
   * minimum: 0
   * @return deleteRetentionMs
  **/
  @javax.annotation.Nullable
  public Long getDeleteRetentionMs() {
    return deleteRetentionMs;
  }


  public void setDeleteRetentionMs(Long deleteRetentionMs) {
    this.deleteRetentionMs = deleteRetentionMs;
  }


  public UpdateKafkaConfigParameters fileDeleteDelayMs(Long fileDeleteDelayMs) {
    
    this.fileDeleteDelayMs = fileDeleteDelayMs;
    return this;
  }

   /**
   * Задержка (в мс) перед удалением файла из файловой системы. Максимальное значение — 9223372036854775807.
   * minimum: 0
   * @return fileDeleteDelayMs
  **/
  @javax.annotation.Nullable
  public Long getFileDeleteDelayMs() {
    return fileDeleteDelayMs;
  }


  public void setFileDeleteDelayMs(Long fileDeleteDelayMs) {
    this.fileDeleteDelayMs = fileDeleteDelayMs;
  }


  public UpdateKafkaConfigParameters flushMessages(Long flushMessages) {
    
    this.flushMessages = flushMessages;
    return this;
  }

   /**
   * Количество сообщений, после которого данные принудительно сбрасываются на диск. Максимальное значение — 9223372036854775807.
   * minimum: 1
   * @return flushMessages
  **/
  @javax.annotation.Nullable
  public Long getFlushMessages() {
    return flushMessages;
  }


  public void setFlushMessages(Long flushMessages) {
    this.flushMessages = flushMessages;
  }


  public UpdateKafkaConfigParameters flushMs(Long flushMs) {
    
    this.flushMs = flushMs;
    return this;
  }

   /**
   * Интервал (в мс), после которого данные принудительно сбрасываются на диск. Максимальное значение — 9223372036854775807.
   * minimum: 0
   * @return flushMs
  **/
  @javax.annotation.Nullable
  public Long getFlushMs() {
    return flushMs;
  }


  public void setFlushMs(Long flushMs) {
    this.flushMs = flushMs;
  }


  public UpdateKafkaConfigParameters indexIntervalBytes(Integer indexIntervalBytes) {
    
    this.indexIntervalBytes = indexIntervalBytes;
    return this;
  }

   /**
   * Интервал (в байтах), с которым Kafka добавляет запись в индекс смещений.
   * minimum: 0
   * maximum: 4294967295
   * @return indexIntervalBytes
  **/
  @javax.annotation.Nullable
  public Integer getIndexIntervalBytes() {
    return indexIntervalBytes;
  }


  public void setIndexIntervalBytes(Integer indexIntervalBytes) {
    this.indexIntervalBytes = indexIntervalBytes;
  }


  public UpdateKafkaConfigParameters minCompactionLagMs(Long minCompactionLagMs) {
    
    this.minCompactionLagMs = minCompactionLagMs;
    return this;
  }

   /**
   * Минимальное время (в мс), в течение которого сообщение остается неуплотненным. Максимальное значение — 9223372036854775807.
   * minimum: 0
   * @return minCompactionLagMs
  **/
  @javax.annotation.Nullable
  public Long getMinCompactionLagMs() {
    return minCompactionLagMs;
  }


  public void setMinCompactionLagMs(Long minCompactionLagMs) {
    this.minCompactionLagMs = minCompactionLagMs;
  }


  public UpdateKafkaConfigParameters maxCompactionLagMs(Long maxCompactionLagMs) {
    
    this.maxCompactionLagMs = maxCompactionLagMs;
    return this;
  }

   /**
   * Максимальное время (в мс), в течение которого сообщение может оставаться неуплотненным. Максимальное значение — 9223372036854775807.
   * minimum: 0
   * @return maxCompactionLagMs
  **/
  @javax.annotation.Nullable
  public Long getMaxCompactionLagMs() {
    return maxCompactionLagMs;
  }


  public void setMaxCompactionLagMs(Long maxCompactionLagMs) {
    this.maxCompactionLagMs = maxCompactionLagMs;
  }


  public UpdateKafkaConfigParameters maxMessageBytes(Integer maxMessageBytes) {
    
    this.maxMessageBytes = maxMessageBytes;
    return this;
  }

   /**
   * Максимальный размер (в байтах) пакета сообщений.
   * minimum: 0
   * maximum: 4294967295
   * @return maxMessageBytes
  **/
  @javax.annotation.Nullable
  public Integer getMaxMessageBytes() {
    return maxMessageBytes;
  }


  public void setMaxMessageBytes(Integer maxMessageBytes) {
    this.maxMessageBytes = maxMessageBytes;
  }


  public UpdateKafkaConfigParameters messageFormatVersion(MessageFormatVersionEnum messageFormatVersion) {
    
    this.messageFormatVersion = messageFormatVersion;
    return this;
  }

   /**
   * Версия формата сообщений, в котором Kafka добавляет сообщения в лог.
   * @return messageFormatVersion
  **/
  @javax.annotation.Nullable
  public MessageFormatVersionEnum getMessageFormatVersion() {
    return messageFormatVersion;
  }


  public void setMessageFormatVersion(MessageFormatVersionEnum messageFormatVersion) {
    this.messageFormatVersion = messageFormatVersion;
  }


  public UpdateKafkaConfigParameters messageTimestampDifferenceMaxMs(Long messageTimestampDifferenceMaxMs) {
    
    this.messageTimestampDifferenceMaxMs = messageTimestampDifferenceMaxMs;
    return this;
  }

   /**
   * Максимально допустимая разница (в мс) между временной меткой сообщения и временем его получения брокером. Максимальное значение — 9223372036854775807.
   * minimum: 0
   * @return messageTimestampDifferenceMaxMs
  **/
  @javax.annotation.Nullable
  public Long getMessageTimestampDifferenceMaxMs() {
    return messageTimestampDifferenceMaxMs;
  }


  public void setMessageTimestampDifferenceMaxMs(Long messageTimestampDifferenceMaxMs) {
    this.messageTimestampDifferenceMaxMs = messageTimestampDifferenceMaxMs;
  }


  public UpdateKafkaConfigParameters messageDownconversionEnable(MessageDownconversionEnableEnum messageDownconversionEnable) {
    
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


  public UpdateKafkaConfigParameters messageTimestampType(MessageTimestampTypeEnum messageTimestampType) {
    
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


  public UpdateKafkaConfigParameters minCleanableDirtyRatio(BigDecimal minCleanableDirtyRatio) {
    
    this.minCleanableDirtyRatio = minCleanableDirtyRatio;
    return this;
  }

   /**
   * Доля неуплотненных данных в логе, при которой запускается уплотнение.
   * minimum: 0
   * maximum: 1
   * @return minCleanableDirtyRatio
  **/
  @javax.annotation.Nullable
  public BigDecimal getMinCleanableDirtyRatio() {
    return minCleanableDirtyRatio;
  }


  public void setMinCleanableDirtyRatio(BigDecimal minCleanableDirtyRatio) {
    this.minCleanableDirtyRatio = minCleanableDirtyRatio;
  }


  public UpdateKafkaConfigParameters minInsyncReplicas(Integer minInsyncReplicas) {
    
    this.minInsyncReplicas = minInsyncReplicas;
    return this;
  }

   /**
   * Минимальное количество синхронизированных реплик, необходимое для подтверждения записи.
   * minimum: 0
   * maximum: 4294967295
   * @return minInsyncReplicas
  **/
  @javax.annotation.Nullable
  public Integer getMinInsyncReplicas() {
    return minInsyncReplicas;
  }


  public void setMinInsyncReplicas(Integer minInsyncReplicas) {
    this.minInsyncReplicas = minInsyncReplicas;
  }


  public UpdateKafkaConfigParameters preallocate(PreallocateEnum preallocate) {
    
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


  public UpdateKafkaConfigParameters retentionBytes(Long retentionBytes) {
    
    this.retentionBytes = retentionBytes;
    return this;
  }

   /**
   * Максимальный размер (в байтах) партиции топика, после которого старые сегменты удаляются. &#x60;-1&#x60; — без ограничения. Максимальное значение — 9223372036854775807.
   * minimum: -1
   * @return retentionBytes
  **/
  @javax.annotation.Nullable
  public Long getRetentionBytes() {
    return retentionBytes;
  }


  public void setRetentionBytes(Long retentionBytes) {
    this.retentionBytes = retentionBytes;
  }


  public UpdateKafkaConfigParameters retentionMs(Long retentionMs) {
    
    this.retentionMs = retentionMs;
    return this;
  }

   /**
   * Время (в мс) хранения сообщений в топике. &#x60;-1&#x60; — хранить бессрочно. Максимальное значение — 9223372036854775807.
   * minimum: -1
   * @return retentionMs
  **/
  @javax.annotation.Nullable
  public Long getRetentionMs() {
    return retentionMs;
  }


  public void setRetentionMs(Long retentionMs) {
    this.retentionMs = retentionMs;
  }


  public UpdateKafkaConfigParameters segmentBytes(Integer segmentBytes) {
    
    this.segmentBytes = segmentBytes;
    return this;
  }

   /**
   * Максимальный размер (в байтах) одного сегмента лога.
   * minimum: 14
   * maximum: 4294967295
   * @return segmentBytes
  **/
  @javax.annotation.Nullable
  public Integer getSegmentBytes() {
    return segmentBytes;
  }


  public void setSegmentBytes(Integer segmentBytes) {
    this.segmentBytes = segmentBytes;
  }


  public UpdateKafkaConfigParameters segmentIndexBytes(Integer segmentIndexBytes) {
    
    this.segmentIndexBytes = segmentIndexBytes;
    return this;
  }

   /**
   * Максимальный размер (в байтах) индексного файла сегмента лога.
   * minimum: 4
   * maximum: 4294967295
   * @return segmentIndexBytes
  **/
  @javax.annotation.Nullable
  public Integer getSegmentIndexBytes() {
    return segmentIndexBytes;
  }


  public void setSegmentIndexBytes(Integer segmentIndexBytes) {
    this.segmentIndexBytes = segmentIndexBytes;
  }


  public UpdateKafkaConfigParameters segmentJitterMs(Long segmentJitterMs) {
    
    this.segmentJitterMs = segmentJitterMs;
    return this;
  }

   /**
   * Максимальное случайное отклонение (в мс) от времени ротации сегмента. Максимальное значение — 9223372036854775807.
   * minimum: 0
   * @return segmentJitterMs
  **/
  @javax.annotation.Nullable
  public Long getSegmentJitterMs() {
    return segmentJitterMs;
  }


  public void setSegmentJitterMs(Long segmentJitterMs) {
    this.segmentJitterMs = segmentJitterMs;
  }


  public UpdateKafkaConfigParameters segmentMs(Long segmentMs) {
    
    this.segmentMs = segmentMs;
    return this;
  }

   /**
   * Период (в мс), после которого Kafka создает новый сегмент лога. Максимальное значение — 9223372036854775807.
   * minimum: 1
   * @return segmentMs
  **/
  @javax.annotation.Nullable
  public Long getSegmentMs() {
    return segmentMs;
  }


  public void setSegmentMs(Long segmentMs) {
    this.segmentMs = segmentMs;
  }


  public UpdateKafkaConfigParameters uncleanLeaderElectionEnable(UncleanLeaderElectionEnableEnum uncleanLeaderElectionEnable) {
    
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
    UpdateKafkaConfigParameters updateKafkaConfigParameters = (UpdateKafkaConfigParameters) o;
    return Objects.equals(this.partitions, updateKafkaConfigParameters.partitions) &&
        Objects.equals(this.cleanupPolicy, updateKafkaConfigParameters.cleanupPolicy) &&
        Objects.equals(this.compressionType, updateKafkaConfigParameters.compressionType) &&
        Objects.equals(this.deleteRetentionMs, updateKafkaConfigParameters.deleteRetentionMs) &&
        Objects.equals(this.fileDeleteDelayMs, updateKafkaConfigParameters.fileDeleteDelayMs) &&
        Objects.equals(this.flushMessages, updateKafkaConfigParameters.flushMessages) &&
        Objects.equals(this.flushMs, updateKafkaConfigParameters.flushMs) &&
        Objects.equals(this.indexIntervalBytes, updateKafkaConfigParameters.indexIntervalBytes) &&
        Objects.equals(this.minCompactionLagMs, updateKafkaConfigParameters.minCompactionLagMs) &&
        Objects.equals(this.maxCompactionLagMs, updateKafkaConfigParameters.maxCompactionLagMs) &&
        Objects.equals(this.maxMessageBytes, updateKafkaConfigParameters.maxMessageBytes) &&
        Objects.equals(this.messageFormatVersion, updateKafkaConfigParameters.messageFormatVersion) &&
        Objects.equals(this.messageTimestampDifferenceMaxMs, updateKafkaConfigParameters.messageTimestampDifferenceMaxMs) &&
        Objects.equals(this.messageDownconversionEnable, updateKafkaConfigParameters.messageDownconversionEnable) &&
        Objects.equals(this.messageTimestampType, updateKafkaConfigParameters.messageTimestampType) &&
        Objects.equals(this.minCleanableDirtyRatio, updateKafkaConfigParameters.minCleanableDirtyRatio) &&
        Objects.equals(this.minInsyncReplicas, updateKafkaConfigParameters.minInsyncReplicas) &&
        Objects.equals(this.preallocate, updateKafkaConfigParameters.preallocate) &&
        Objects.equals(this.retentionBytes, updateKafkaConfigParameters.retentionBytes) &&
        Objects.equals(this.retentionMs, updateKafkaConfigParameters.retentionMs) &&
        Objects.equals(this.segmentBytes, updateKafkaConfigParameters.segmentBytes) &&
        Objects.equals(this.segmentIndexBytes, updateKafkaConfigParameters.segmentIndexBytes) &&
        Objects.equals(this.segmentJitterMs, updateKafkaConfigParameters.segmentJitterMs) &&
        Objects.equals(this.segmentMs, updateKafkaConfigParameters.segmentMs) &&
        Objects.equals(this.uncleanLeaderElectionEnable, updateKafkaConfigParameters.uncleanLeaderElectionEnable);
  }

  @Override
  public int hashCode() {
    return Objects.hash(partitions, cleanupPolicy, compressionType, deleteRetentionMs, fileDeleteDelayMs, flushMessages, flushMs, indexIntervalBytes, minCompactionLagMs, maxCompactionLagMs, maxMessageBytes, messageFormatVersion, messageTimestampDifferenceMaxMs, messageDownconversionEnable, messageTimestampType, minCleanableDirtyRatio, minInsyncReplicas, preallocate, retentionBytes, retentionMs, segmentBytes, segmentIndexBytes, segmentJitterMs, segmentMs, uncleanLeaderElectionEnable);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class UpdateKafkaConfigParameters {\n");
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
  * @throws IOException if the JSON Element is invalid with respect to UpdateKafkaConfigParameters
  */
  public static void validateJsonElement(JsonElement jsonElement) throws IOException {
      if (jsonElement == null) {
        if (!UpdateKafkaConfigParameters.openapiRequiredFields.isEmpty()) { // has required fields but JSON element is null
          throw new IllegalArgumentException(String.format("The required field(s) %s in UpdateKafkaConfigParameters is not found in the empty JSON string", UpdateKafkaConfigParameters.openapiRequiredFields.toString()));
        }
      }

      Set<Entry<String, JsonElement>> entries = jsonElement.getAsJsonObject().entrySet();
      // check to see if the JSON string contains additional fields
      for (Entry<String, JsonElement> entry : entries) {
        if (!UpdateKafkaConfigParameters.openapiFields.contains(entry.getKey())) {
          throw new IllegalArgumentException(String.format("The field `%s` in the JSON string is not defined in the `UpdateKafkaConfigParameters` properties. JSON: %s", entry.getKey(), jsonElement.toString()));
        }
      }
        JsonObject jsonObj = jsonElement.getAsJsonObject();
      if ((jsonObj.get("cleanup_policy") != null && !jsonObj.get("cleanup_policy").isJsonNull()) && !jsonObj.get("cleanup_policy").isJsonPrimitive()) {
        throw new IllegalArgumentException(String.format("Expected the field `cleanup_policy` to be a primitive type in the JSON string but got `%s`", jsonObj.get("cleanup_policy").toString()));
      }
      if ((jsonObj.get("compression_type") != null && !jsonObj.get("compression_type").isJsonNull()) && !jsonObj.get("compression_type").isJsonPrimitive()) {
        throw new IllegalArgumentException(String.format("Expected the field `compression_type` to be a primitive type in the JSON string but got `%s`", jsonObj.get("compression_type").toString()));
      }
      if ((jsonObj.get("message_format_version") != null && !jsonObj.get("message_format_version").isJsonNull()) && !jsonObj.get("message_format_version").isJsonPrimitive()) {
        throw new IllegalArgumentException(String.format("Expected the field `message_format_version` to be a primitive type in the JSON string but got `%s`", jsonObj.get("message_format_version").toString()));
      }
      if ((jsonObj.get("message_downconversion_enable") != null && !jsonObj.get("message_downconversion_enable").isJsonNull()) && !jsonObj.get("message_downconversion_enable").isJsonPrimitive()) {
        throw new IllegalArgumentException(String.format("Expected the field `message_downconversion_enable` to be a primitive type in the JSON string but got `%s`", jsonObj.get("message_downconversion_enable").toString()));
      }
      if ((jsonObj.get("message_timestamp_type") != null && !jsonObj.get("message_timestamp_type").isJsonNull()) && !jsonObj.get("message_timestamp_type").isJsonPrimitive()) {
        throw new IllegalArgumentException(String.format("Expected the field `message_timestamp_type` to be a primitive type in the JSON string but got `%s`", jsonObj.get("message_timestamp_type").toString()));
      }
      if ((jsonObj.get("preallocate") != null && !jsonObj.get("preallocate").isJsonNull()) && !jsonObj.get("preallocate").isJsonPrimitive()) {
        throw new IllegalArgumentException(String.format("Expected the field `preallocate` to be a primitive type in the JSON string but got `%s`", jsonObj.get("preallocate").toString()));
      }
      if ((jsonObj.get("unclean_leader_election_enable") != null && !jsonObj.get("unclean_leader_election_enable").isJsonNull()) && !jsonObj.get("unclean_leader_election_enable").isJsonPrimitive()) {
        throw new IllegalArgumentException(String.format("Expected the field `unclean_leader_election_enable` to be a primitive type in the JSON string but got `%s`", jsonObj.get("unclean_leader_election_enable").toString()));
      }
  }

  public static class CustomTypeAdapterFactory implements TypeAdapterFactory {
    @SuppressWarnings("unchecked")
    @Override
    public <T> TypeAdapter<T> create(Gson gson, TypeToken<T> type) {
       if (!UpdateKafkaConfigParameters.class.isAssignableFrom(type.getRawType())) {
         return null; // this class only serializes 'UpdateKafkaConfigParameters' and its subtypes
       }
       final TypeAdapter<JsonElement> elementAdapter = gson.getAdapter(JsonElement.class);
       final TypeAdapter<UpdateKafkaConfigParameters> thisAdapter
                        = gson.getDelegateAdapter(this, TypeToken.get(UpdateKafkaConfigParameters.class));

       return (TypeAdapter<T>) new TypeAdapter<UpdateKafkaConfigParameters>() {
           @Override
           public void write(JsonWriter out, UpdateKafkaConfigParameters value) throws IOException {
             JsonObject obj = thisAdapter.toJsonTree(value).getAsJsonObject();
             elementAdapter.write(out, obj);
           }

           @Override
           public UpdateKafkaConfigParameters read(JsonReader in) throws IOException {
             JsonElement jsonElement = elementAdapter.read(in);
             validateJsonElement(jsonElement);
             return thisAdapter.fromJsonTree(jsonElement);
           }

       }.nullSafe();
    }
  }

 /**
  * Create an instance of UpdateKafkaConfigParameters given an JSON string
  *
  * @param jsonString JSON string
  * @return An instance of UpdateKafkaConfigParameters
  * @throws IOException if the JSON string is invalid with respect to UpdateKafkaConfigParameters
  */
  public static UpdateKafkaConfigParameters fromJson(String jsonString) throws IOException {
    return JSON.getGson().fromJson(jsonString, UpdateKafkaConfigParameters.class);
  }

 /**
  * Convert an instance of UpdateKafkaConfigParameters to an JSON string
  *
  * @return JSON string
  */
  public String toJson() {
    return JSON.getGson().toJson(this);
  }
}

