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
import java.util.HashMap;
import java.util.Map;
import org.openapitools.client.model.AiGatewayBatchRequestCounts;
import org.openapitools.jackson.nullable.JsonNullable;

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
 * Батч
 */
@javax.annotation.Generated(value = "org.openapitools.codegen.languages.JavaClientCodegen", date = "2026-10-23T14:06:46.753006Z[Etc/UTC]")
public class AiGatewayBatch {
  public static final String SERIALIZED_NAME_ID = "id";
  @SerializedName(SERIALIZED_NAME_ID)
  private String id;

  /**
   * Тип объекта
   */
  @JsonAdapter(ObjectEnum.Adapter.class)
  public enum ObjectEnum {
    BATCH("batch");

    private String value;

    ObjectEnum(String value) {
      this.value = value;
    }

    public String getValue() {
      return value;
    }

    @Override
    public String toString() {
      return String.valueOf(value);
    }

    public static ObjectEnum fromValue(String value) {
      for (ObjectEnum b : ObjectEnum.values()) {
        if (b.value.equals(value)) {
          return b;
        }
      }
      throw new IllegalArgumentException("Unexpected value '" + value + "'");
    }

    public static class Adapter extends TypeAdapter<ObjectEnum> {
      @Override
      public void write(final JsonWriter jsonWriter, final ObjectEnum enumeration) throws IOException {
        jsonWriter.value(enumeration.getValue());
      }

      @Override
      public ObjectEnum read(final JsonReader jsonReader) throws IOException {
        String value =  jsonReader.nextString();
        return ObjectEnum.fromValue(value);
      }
    }
  }

  public static final String SERIALIZED_NAME_OBJECT = "object";
  @SerializedName(SERIALIZED_NAME_OBJECT)
  private ObjectEnum _object;

  public static final String SERIALIZED_NAME_ENDPOINT = "endpoint";
  @SerializedName(SERIALIZED_NAME_ENDPOINT)
  private String endpoint;

  public static final String SERIALIZED_NAME_MODEL = "model";
  @SerializedName(SERIALIZED_NAME_MODEL)
  private String model;

  public static final String SERIALIZED_NAME_INPUT_FILE_ID = "input_file_id";
  @SerializedName(SERIALIZED_NAME_INPUT_FILE_ID)
  private String inputFileId;

  public static final String SERIALIZED_NAME_OUTPUT_FILE_ID = "output_file_id";
  @SerializedName(SERIALIZED_NAME_OUTPUT_FILE_ID)
  private String outputFileId;

  public static final String SERIALIZED_NAME_ERROR_FILE_ID = "error_file_id";
  @SerializedName(SERIALIZED_NAME_ERROR_FILE_ID)
  private String errorFileId;

  public static final String SERIALIZED_NAME_COMPLETION_WINDOW = "completion_window";
  @SerializedName(SERIALIZED_NAME_COMPLETION_WINDOW)
  private String completionWindow;

  /**
   * Статус батча. &#x60;cancelling&#x60; — отмена выполняется, может занять несколько минут
   */
  @JsonAdapter(StatusEnum.Adapter.class)
  public enum StatusEnum {
    VALIDATING("validating"),
    
    FAILED("failed"),
    
    IN_PROGRESS("in_progress"),
    
    FINALIZING("finalizing"),
    
    COMPLETED("completed"),
    
    EXPIRED("expired"),
    
    CANCELLING("cancelling"),
    
    CANCELLED("cancelled");

    private String value;

    StatusEnum(String value) {
      this.value = value;
    }

    public String getValue() {
      return value;
    }

    @Override
    public String toString() {
      return String.valueOf(value);
    }

    public static StatusEnum fromValue(String value) {
      for (StatusEnum b : StatusEnum.values()) {
        if (b.value.equals(value)) {
          return b;
        }
      }
      throw new IllegalArgumentException("Unexpected value '" + value + "'");
    }

    public static class Adapter extends TypeAdapter<StatusEnum> {
      @Override
      public void write(final JsonWriter jsonWriter, final StatusEnum enumeration) throws IOException {
        jsonWriter.value(enumeration.getValue());
      }

      @Override
      public StatusEnum read(final JsonReader jsonReader) throws IOException {
        String value =  jsonReader.nextString();
        return StatusEnum.fromValue(value);
      }
    }
  }

  public static final String SERIALIZED_NAME_STATUS = "status";
  @SerializedName(SERIALIZED_NAME_STATUS)
  private StatusEnum status;

  public static final String SERIALIZED_NAME_CREATED_AT = "created_at";
  @SerializedName(SERIALIZED_NAME_CREATED_AT)
  private Integer createdAt;

  public static final String SERIALIZED_NAME_COMPLETED_AT = "completed_at";
  @SerializedName(SERIALIZED_NAME_COMPLETED_AT)
  private Integer completedAt;

  public static final String SERIALIZED_NAME_EXPIRES_AT = "expires_at";
  @SerializedName(SERIALIZED_NAME_EXPIRES_AT)
  private Integer expiresAt;

  public static final String SERIALIZED_NAME_REQUEST_COUNTS = "request_counts";
  @SerializedName(SERIALIZED_NAME_REQUEST_COUNTS)
  private AiGatewayBatchRequestCounts requestCounts;

  public static final String SERIALIZED_NAME_METADATA = "metadata";
  @SerializedName(SERIALIZED_NAME_METADATA)
  private Map<String, Object> metadata;

  public static final String SERIALIZED_NAME_USAGE = "usage";
  @SerializedName(SERIALIZED_NAME_USAGE)
  private Map<String, Object> usage;

  public AiGatewayBatch() {
  }

  public AiGatewayBatch id(String id) {
    
    this.id = id;
    return this;
  }

   /**
   * ID батча
   * @return id
  **/
  @javax.annotation.Nullable
  public String getId() {
    return id;
  }


  public void setId(String id) {
    this.id = id;
  }


  public AiGatewayBatch _object(ObjectEnum _object) {
    
    this._object = _object;
    return this;
  }

   /**
   * Тип объекта
   * @return _object
  **/
  @javax.annotation.Nullable
  public ObjectEnum getObject() {
    return _object;
  }


  public void setObject(ObjectEnum _object) {
    this._object = _object;
  }


  public AiGatewayBatch endpoint(String endpoint) {
    
    this.endpoint = endpoint;
    return this;
  }

   /**
   * Эндпоинт запросов батча
   * @return endpoint
  **/
  @javax.annotation.Nullable
  public String getEndpoint() {
    return endpoint;
  }


  public void setEndpoint(String endpoint) {
    this.endpoint = endpoint;
  }


  public AiGatewayBatch model(String model) {
    
    this.model = model;
    return this;
  }

   /**
   * Модель батча. На этапе &#x60;validating&#x60; может быть &#x60;null&#x60;
   * @return model
  **/
  @javax.annotation.Nullable
  public String getModel() {
    return model;
  }


  public void setModel(String model) {
    this.model = model;
  }


  public AiGatewayBatch inputFileId(String inputFileId) {
    
    this.inputFileId = inputFileId;
    return this;
  }

   /**
   * ID входного файла
   * @return inputFileId
  **/
  @javax.annotation.Nullable
  public String getInputFileId() {
    return inputFileId;
  }


  public void setInputFileId(String inputFileId) {
    this.inputFileId = inputFileId;
  }


  public AiGatewayBatch outputFileId(String outputFileId) {
    
    this.outputFileId = outputFileId;
    return this;
  }

   /**
   * ID файла результатов — когда батч выполнен
   * @return outputFileId
  **/
  @javax.annotation.Nullable
  public String getOutputFileId() {
    return outputFileId;
  }


  public void setOutputFileId(String outputFileId) {
    this.outputFileId = outputFileId;
  }


  public AiGatewayBatch errorFileId(String errorFileId) {
    
    this.errorFileId = errorFileId;
    return this;
  }

   /**
   * ID файла с ошибками отдельных запросов, если они были
   * @return errorFileId
  **/
  @javax.annotation.Nullable
  public String getErrorFileId() {
    return errorFileId;
  }


  public void setErrorFileId(String errorFileId) {
    this.errorFileId = errorFileId;
  }


  public AiGatewayBatch completionWindow(String completionWindow) {
    
    this.completionWindow = completionWindow;
    return this;
  }

   /**
   * Срок выполнения
   * @return completionWindow
  **/
  @javax.annotation.Nullable
  public String getCompletionWindow() {
    return completionWindow;
  }


  public void setCompletionWindow(String completionWindow) {
    this.completionWindow = completionWindow;
  }


  public AiGatewayBatch status(StatusEnum status) {
    
    this.status = status;
    return this;
  }

   /**
   * Статус батча. &#x60;cancelling&#x60; — отмена выполняется, может занять несколько минут
   * @return status
  **/
  @javax.annotation.Nullable
  public StatusEnum getStatus() {
    return status;
  }


  public void setStatus(StatusEnum status) {
    this.status = status;
  }


  public AiGatewayBatch createdAt(Integer createdAt) {
    
    this.createdAt = createdAt;
    return this;
  }

   /**
   * Время создания, Unix-время в секундах
   * @return createdAt
  **/
  @javax.annotation.Nullable
  public Integer getCreatedAt() {
    return createdAt;
  }


  public void setCreatedAt(Integer createdAt) {
    this.createdAt = createdAt;
  }


  public AiGatewayBatch completedAt(Integer completedAt) {
    
    this.completedAt = completedAt;
    return this;
  }

   /**
   * Время завершения, Unix-время в секундах
   * @return completedAt
  **/
  @javax.annotation.Nullable
  public Integer getCompletedAt() {
    return completedAt;
  }


  public void setCompletedAt(Integer completedAt) {
    this.completedAt = completedAt;
  }


  public AiGatewayBatch expiresAt(Integer expiresAt) {
    
    this.expiresAt = expiresAt;
    return this;
  }

   /**
   * Крайний срок выполнения, Unix-время в секундах
   * @return expiresAt
  **/
  @javax.annotation.Nullable
  public Integer getExpiresAt() {
    return expiresAt;
  }


  public void setExpiresAt(Integer expiresAt) {
    this.expiresAt = expiresAt;
  }


  public AiGatewayBatch requestCounts(AiGatewayBatchRequestCounts requestCounts) {
    
    this.requestCounts = requestCounts;
    return this;
  }

   /**
   * Get requestCounts
   * @return requestCounts
  **/
  @javax.annotation.Nullable
  public AiGatewayBatchRequestCounts getRequestCounts() {
    return requestCounts;
  }


  public void setRequestCounts(AiGatewayBatchRequestCounts requestCounts) {
    this.requestCounts = requestCounts;
  }


  public AiGatewayBatch metadata(Map<String, Object> metadata) {
    
    this.metadata = metadata;
    return this;
  }

  public AiGatewayBatch putMetadataItem(String key, Object metadataItem) {
    if (this.metadata == null) {
      this.metadata = new HashMap<>();
    }
    this.metadata.put(key, metadataItem);
    return this;
  }

   /**
   * Метки батча из запроса
   * @return metadata
  **/
  @javax.annotation.Nullable
  public Map<String, Object> getMetadata() {
    return metadata;
  }


  public void setMetadata(Map<String, Object> metadata) {
    this.metadata = metadata;
  }


  public AiGatewayBatch usage(Map<String, Object> usage) {
    
    this.usage = usage;
    return this;
  }

  public AiGatewayBatch putUsageItem(String key, Object usageItem) {
    if (this.usage == null) {
      this.usage = new HashMap<>();
    }
    this.usage.put(key, usageItem);
    return this;
  }

   /**
   * Расход токенов батча
   * @return usage
  **/
  @javax.annotation.Nullable
  public Map<String, Object> getUsage() {
    return usage;
  }


  public void setUsage(Map<String, Object> usage) {
    this.usage = usage;
  }

  /**
   * A container for additional, undeclared properties.
   * This is a holder for any undeclared properties as specified with
   * the 'additionalProperties' keyword in the OAS document.
   */
  private Map<String, Object> additionalProperties;

  /**
   * Set the additional (undeclared) property with the specified name and value.
   * If the property does not already exist, create it otherwise replace it.
   *
   * @param key name of the property
   * @param value value of the property
   * @return the AiGatewayBatch instance itself
   */
  public AiGatewayBatch putAdditionalProperty(String key, Object value) {
    if (this.additionalProperties == null) {
        this.additionalProperties = new HashMap<String, Object>();
    }
    this.additionalProperties.put(key, value);
    return this;
  }

  /**
   * Return the additional (undeclared) property.
   *
   * @return a map of objects
   */
  public Map<String, Object> getAdditionalProperties() {
    return additionalProperties;
  }

  /**
   * Return the additional (undeclared) property with the specified name.
   *
   * @param key name of the property
   * @return an object
   */
  public Object getAdditionalProperty(String key) {
    if (this.additionalProperties == null) {
        return null;
    }
    return this.additionalProperties.get(key);
  }


  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    AiGatewayBatch aiGatewayBatch = (AiGatewayBatch) o;
    return Objects.equals(this.id, aiGatewayBatch.id) &&
        Objects.equals(this._object, aiGatewayBatch._object) &&
        Objects.equals(this.endpoint, aiGatewayBatch.endpoint) &&
        Objects.equals(this.model, aiGatewayBatch.model) &&
        Objects.equals(this.inputFileId, aiGatewayBatch.inputFileId) &&
        Objects.equals(this.outputFileId, aiGatewayBatch.outputFileId) &&
        Objects.equals(this.errorFileId, aiGatewayBatch.errorFileId) &&
        Objects.equals(this.completionWindow, aiGatewayBatch.completionWindow) &&
        Objects.equals(this.status, aiGatewayBatch.status) &&
        Objects.equals(this.createdAt, aiGatewayBatch.createdAt) &&
        Objects.equals(this.completedAt, aiGatewayBatch.completedAt) &&
        Objects.equals(this.expiresAt, aiGatewayBatch.expiresAt) &&
        Objects.equals(this.requestCounts, aiGatewayBatch.requestCounts) &&
        Objects.equals(this.metadata, aiGatewayBatch.metadata) &&
        Objects.equals(this.usage, aiGatewayBatch.usage)&&
        Objects.equals(this.additionalProperties, aiGatewayBatch.additionalProperties);
  }

  private static <T> boolean equalsNullable(JsonNullable<T> a, JsonNullable<T> b) {
    return a == b || (a != null && b != null && a.isPresent() && b.isPresent() && Objects.deepEquals(a.get(), b.get()));
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, _object, endpoint, model, inputFileId, outputFileId, errorFileId, completionWindow, status, createdAt, completedAt, expiresAt, requestCounts, metadata, usage, additionalProperties);
  }

  private static <T> int hashCodeNullable(JsonNullable<T> a) {
    if (a == null) {
      return 1;
    }
    return a.isPresent() ? Arrays.deepHashCode(new Object[]{a.get()}) : 31;
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class AiGatewayBatch {\n");
    sb.append("    id: ").append(toIndentedString(id)).append("\n");
    sb.append("    _object: ").append(toIndentedString(_object)).append("\n");
    sb.append("    endpoint: ").append(toIndentedString(endpoint)).append("\n");
    sb.append("    model: ").append(toIndentedString(model)).append("\n");
    sb.append("    inputFileId: ").append(toIndentedString(inputFileId)).append("\n");
    sb.append("    outputFileId: ").append(toIndentedString(outputFileId)).append("\n");
    sb.append("    errorFileId: ").append(toIndentedString(errorFileId)).append("\n");
    sb.append("    completionWindow: ").append(toIndentedString(completionWindow)).append("\n");
    sb.append("    status: ").append(toIndentedString(status)).append("\n");
    sb.append("    createdAt: ").append(toIndentedString(createdAt)).append("\n");
    sb.append("    completedAt: ").append(toIndentedString(completedAt)).append("\n");
    sb.append("    expiresAt: ").append(toIndentedString(expiresAt)).append("\n");
    sb.append("    requestCounts: ").append(toIndentedString(requestCounts)).append("\n");
    sb.append("    metadata: ").append(toIndentedString(metadata)).append("\n");
    sb.append("    usage: ").append(toIndentedString(usage)).append("\n");
    sb.append("    additionalProperties: ").append(toIndentedString(additionalProperties)).append("\n");
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
    openapiFields.add("id");
    openapiFields.add("object");
    openapiFields.add("endpoint");
    openapiFields.add("model");
    openapiFields.add("input_file_id");
    openapiFields.add("output_file_id");
    openapiFields.add("error_file_id");
    openapiFields.add("completion_window");
    openapiFields.add("status");
    openapiFields.add("created_at");
    openapiFields.add("completed_at");
    openapiFields.add("expires_at");
    openapiFields.add("request_counts");
    openapiFields.add("metadata");
    openapiFields.add("usage");

    // a set of required properties/fields (JSON key names)
    openapiRequiredFields = new HashSet<String>();
  }

 /**
  * Validates the JSON Element and throws an exception if issues found
  *
  * @param jsonElement JSON Element
  * @throws IOException if the JSON Element is invalid with respect to AiGatewayBatch
  */
  public static void validateJsonElement(JsonElement jsonElement) throws IOException {
      if (jsonElement == null) {
        if (!AiGatewayBatch.openapiRequiredFields.isEmpty()) { // has required fields but JSON element is null
          throw new IllegalArgumentException(String.format("The required field(s) %s in AiGatewayBatch is not found in the empty JSON string", AiGatewayBatch.openapiRequiredFields.toString()));
        }
      }
        JsonObject jsonObj = jsonElement.getAsJsonObject();
      if ((jsonObj.get("id") != null && !jsonObj.get("id").isJsonNull()) && !jsonObj.get("id").isJsonPrimitive()) {
        throw new IllegalArgumentException(String.format("Expected the field `id` to be a primitive type in the JSON string but got `%s`", jsonObj.get("id").toString()));
      }
      if ((jsonObj.get("object") != null && !jsonObj.get("object").isJsonNull()) && !jsonObj.get("object").isJsonPrimitive()) {
        throw new IllegalArgumentException(String.format("Expected the field `object` to be a primitive type in the JSON string but got `%s`", jsonObj.get("object").toString()));
      }
      if ((jsonObj.get("endpoint") != null && !jsonObj.get("endpoint").isJsonNull()) && !jsonObj.get("endpoint").isJsonPrimitive()) {
        throw new IllegalArgumentException(String.format("Expected the field `endpoint` to be a primitive type in the JSON string but got `%s`", jsonObj.get("endpoint").toString()));
      }
      if ((jsonObj.get("model") != null && !jsonObj.get("model").isJsonNull()) && !jsonObj.get("model").isJsonPrimitive()) {
        throw new IllegalArgumentException(String.format("Expected the field `model` to be a primitive type in the JSON string but got `%s`", jsonObj.get("model").toString()));
      }
      if ((jsonObj.get("input_file_id") != null && !jsonObj.get("input_file_id").isJsonNull()) && !jsonObj.get("input_file_id").isJsonPrimitive()) {
        throw new IllegalArgumentException(String.format("Expected the field `input_file_id` to be a primitive type in the JSON string but got `%s`", jsonObj.get("input_file_id").toString()));
      }
      if ((jsonObj.get("output_file_id") != null && !jsonObj.get("output_file_id").isJsonNull()) && !jsonObj.get("output_file_id").isJsonPrimitive()) {
        throw new IllegalArgumentException(String.format("Expected the field `output_file_id` to be a primitive type in the JSON string but got `%s`", jsonObj.get("output_file_id").toString()));
      }
      if ((jsonObj.get("error_file_id") != null && !jsonObj.get("error_file_id").isJsonNull()) && !jsonObj.get("error_file_id").isJsonPrimitive()) {
        throw new IllegalArgumentException(String.format("Expected the field `error_file_id` to be a primitive type in the JSON string but got `%s`", jsonObj.get("error_file_id").toString()));
      }
      if ((jsonObj.get("completion_window") != null && !jsonObj.get("completion_window").isJsonNull()) && !jsonObj.get("completion_window").isJsonPrimitive()) {
        throw new IllegalArgumentException(String.format("Expected the field `completion_window` to be a primitive type in the JSON string but got `%s`", jsonObj.get("completion_window").toString()));
      }
      if ((jsonObj.get("status") != null && !jsonObj.get("status").isJsonNull()) && !jsonObj.get("status").isJsonPrimitive()) {
        throw new IllegalArgumentException(String.format("Expected the field `status` to be a primitive type in the JSON string but got `%s`", jsonObj.get("status").toString()));
      }
      // validate the optional field `request_counts`
      if (jsonObj.get("request_counts") != null && !jsonObj.get("request_counts").isJsonNull()) {
        AiGatewayBatchRequestCounts.validateJsonElement(jsonObj.get("request_counts"));
      }
  }

  public static class CustomTypeAdapterFactory implements TypeAdapterFactory {
    @SuppressWarnings("unchecked")
    @Override
    public <T> TypeAdapter<T> create(Gson gson, TypeToken<T> type) {
       if (!AiGatewayBatch.class.isAssignableFrom(type.getRawType())) {
         return null; // this class only serializes 'AiGatewayBatch' and its subtypes
       }
       final TypeAdapter<JsonElement> elementAdapter = gson.getAdapter(JsonElement.class);
       final TypeAdapter<AiGatewayBatch> thisAdapter
                        = gson.getDelegateAdapter(this, TypeToken.get(AiGatewayBatch.class));

       return (TypeAdapter<T>) new TypeAdapter<AiGatewayBatch>() {
           @Override
           public void write(JsonWriter out, AiGatewayBatch value) throws IOException {
             JsonObject obj = thisAdapter.toJsonTree(value).getAsJsonObject();
             obj.remove("additionalProperties");
             // serialize additional properties
             if (value.getAdditionalProperties() != null) {
               for (Map.Entry<String, Object> entry : value.getAdditionalProperties().entrySet()) {
                 if (entry.getValue() instanceof String)
                   obj.addProperty(entry.getKey(), (String) entry.getValue());
                 else if (entry.getValue() instanceof Number)
                   obj.addProperty(entry.getKey(), (Number) entry.getValue());
                 else if (entry.getValue() instanceof Boolean)
                   obj.addProperty(entry.getKey(), (Boolean) entry.getValue());
                 else if (entry.getValue() instanceof Character)
                   obj.addProperty(entry.getKey(), (Character) entry.getValue());
                 else {
                   obj.add(entry.getKey(), gson.toJsonTree(entry.getValue()).getAsJsonObject());
                 }
               }
             }
             elementAdapter.write(out, obj);
           }

           @Override
           public AiGatewayBatch read(JsonReader in) throws IOException {
             JsonElement jsonElement = elementAdapter.read(in);
             validateJsonElement(jsonElement);
             JsonObject jsonObj = jsonElement.getAsJsonObject();
             // store additional fields in the deserialized instance
             AiGatewayBatch instance = thisAdapter.fromJsonTree(jsonObj);
             for (Map.Entry<String, JsonElement> entry : jsonObj.entrySet()) {
               if (!openapiFields.contains(entry.getKey())) {
                 if (entry.getValue().isJsonPrimitive()) { // primitive type
                   if (entry.getValue().getAsJsonPrimitive().isString())
                     instance.putAdditionalProperty(entry.getKey(), entry.getValue().getAsString());
                   else if (entry.getValue().getAsJsonPrimitive().isNumber())
                     instance.putAdditionalProperty(entry.getKey(), entry.getValue().getAsNumber());
                   else if (entry.getValue().getAsJsonPrimitive().isBoolean())
                     instance.putAdditionalProperty(entry.getKey(), entry.getValue().getAsBoolean());
                   else
                     throw new IllegalArgumentException(String.format("The field `%s` has unknown primitive type. Value: %s", entry.getKey(), entry.getValue().toString()));
                 } else if (entry.getValue().isJsonArray()) {
                     instance.putAdditionalProperty(entry.getKey(), gson.fromJson(entry.getValue(), List.class));
                 } else { // JSON object
                     instance.putAdditionalProperty(entry.getKey(), gson.fromJson(entry.getValue(), HashMap.class));
                 }
               }
             }
             return instance;
           }

       }.nullSafe();
    }
  }

 /**
  * Create an instance of AiGatewayBatch given an JSON string
  *
  * @param jsonString JSON string
  * @return An instance of AiGatewayBatch
  * @throws IOException if the JSON string is invalid with respect to AiGatewayBatch
  */
  public static AiGatewayBatch fromJson(String jsonString) throws IOException {
    return JSON.getGson().fromJson(jsonString, AiGatewayBatch.class);
  }

 /**
  * Convert an instance of AiGatewayBatch to an JSON string
  *
  * @return JSON string
  */
  public String toJson() {
    return JSON.getGson().toJson(this);
  }
}

