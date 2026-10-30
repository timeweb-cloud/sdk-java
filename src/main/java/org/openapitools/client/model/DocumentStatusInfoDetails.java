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
import java.time.OffsetDateTime;

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
 * Подробности, набор полей зависит от состояния. Могут присутствовать и другие поля
 */
@javax.annotation.Generated(value = "org.openapitools.codegen.languages.JavaClientCodegen", date = "2026-10-30T15:03:29.616934Z[Etc/UTC]")
public class DocumentStatusInfoDetails {
  public static final String SERIALIZED_NAME_STARTED_AT = "started_at";
  @SerializedName(SERIALIZED_NAME_STARTED_AT)
  private OffsetDateTime startedAt;

  public static final String SERIALIZED_NAME_CURRENT_STAGE = "current_stage";
  @SerializedName(SERIALIZED_NAME_CURRENT_STAGE)
  private String currentStage;

  public static final String SERIALIZED_NAME_PROGRESS = "progress";
  @SerializedName(SERIALIZED_NAME_PROGRESS)
  private BigDecimal progress;

  public static final String SERIALIZED_NAME_INDEXING_DURATION_MS = "indexing_duration_ms";
  @SerializedName(SERIALIZED_NAME_INDEXING_DURATION_MS)
  private BigDecimal indexingDurationMs;

  public static final String SERIALIZED_NAME_TOKENS_USED = "tokens_used";
  @SerializedName(SERIALIZED_NAME_TOKENS_USED)
  private BigDecimal tokensUsed;

  public static final String SERIALIZED_NAME_TOKENS_ESTIMATED = "tokens_estimated";
  @SerializedName(SERIALIZED_NAME_TOKENS_ESTIMATED)
  private BigDecimal tokensEstimated;

  public static final String SERIALIZED_NAME_CHUNKS_COUNT = "chunks_count";
  @SerializedName(SERIALIZED_NAME_CHUNKS_COUNT)
  private BigDecimal chunksCount;

  public static final String SERIALIZED_NAME_NEEDED = "needed";
  @SerializedName(SERIALIZED_NAME_NEEDED)
  private BigDecimal needed;

  public static final String SERIALIZED_NAME_AVAILABLE = "available";
  @SerializedName(SERIALIZED_NAME_AVAILABLE)
  private BigDecimal available;

  public static final String SERIALIZED_NAME_ESTIMATED = "estimated";
  @SerializedName(SERIALIZED_NAME_ESTIMATED)
  private BigDecimal estimated;

  public static final String SERIALIZED_NAME_BALANCE = "balance";
  @SerializedName(SERIALIZED_NAME_BALANCE)
  private BigDecimal balance;

  public static final String SERIALIZED_NAME_MIN_REQUIRED = "min_required";
  @SerializedName(SERIALIZED_NAME_MIN_REQUIRED)
  private BigDecimal minRequired;

  public DocumentStatusInfoDetails() {
  }

  public DocumentStatusInfoDetails startedAt(OffsetDateTime startedAt) {
    
    this.startedAt = startedAt;
    return this;
  }

   /**
   * &#x60;indexing&#x60;: время начала индексации
   * @return startedAt
  **/
  @javax.annotation.Nullable
  public OffsetDateTime getStartedAt() {
    return startedAt;
  }


  public void setStartedAt(OffsetDateTime startedAt) {
    this.startedAt = startedAt;
  }


  public DocumentStatusInfoDetails currentStage(String currentStage) {
    
    this.currentStage = currentStage;
    return this;
  }

   /**
   * &#x60;indexing&#x60;: текущий этап
   * @return currentStage
  **/
  @javax.annotation.Nullable
  public String getCurrentStage() {
    return currentStage;
  }


  public void setCurrentStage(String currentStage) {
    this.currentStage = currentStage;
  }


  public DocumentStatusInfoDetails progress(BigDecimal progress) {
    
    this.progress = progress;
    return this;
  }

   /**
   * &#x60;indexing&#x60;: прогресс в процентах
   * minimum: 0
   * maximum: 100
   * @return progress
  **/
  @javax.annotation.Nullable
  public BigDecimal getProgress() {
    return progress;
  }


  public void setProgress(BigDecimal progress) {
    this.progress = progress;
  }


  public DocumentStatusInfoDetails indexingDurationMs(BigDecimal indexingDurationMs) {
    
    this.indexingDurationMs = indexingDurationMs;
    return this;
  }

   /**
   * &#x60;indexed&#x60;: длительность индексации в миллисекундах
   * @return indexingDurationMs
  **/
  @javax.annotation.Nullable
  public BigDecimal getIndexingDurationMs() {
    return indexingDurationMs;
  }


  public void setIndexingDurationMs(BigDecimal indexingDurationMs) {
    this.indexingDurationMs = indexingDurationMs;
  }


  public DocumentStatusInfoDetails tokensUsed(BigDecimal tokensUsed) {
    
    this.tokensUsed = tokensUsed;
    return this;
  }

   /**
   * &#x60;indexed&#x60;: израсходовано токенов
   * @return tokensUsed
  **/
  @javax.annotation.Nullable
  public BigDecimal getTokensUsed() {
    return tokensUsed;
  }


  public void setTokensUsed(BigDecimal tokensUsed) {
    this.tokensUsed = tokensUsed;
  }


  public DocumentStatusInfoDetails tokensEstimated(BigDecimal tokensEstimated) {
    
    this.tokensEstimated = tokensEstimated;
    return this;
  }

   /**
   * &#x60;indexed&#x60;: оценка количества токенов перед индексацией
   * @return tokensEstimated
  **/
  @javax.annotation.Nullable
  public BigDecimal getTokensEstimated() {
    return tokensEstimated;
  }


  public void setTokensEstimated(BigDecimal tokensEstimated) {
    this.tokensEstimated = tokensEstimated;
  }


  public DocumentStatusInfoDetails chunksCount(BigDecimal chunksCount) {
    
    this.chunksCount = chunksCount;
    return this;
  }

   /**
   * &#x60;indexed&#x60;: количество фрагментов, на которые разбит документ
   * @return chunksCount
  **/
  @javax.annotation.Nullable
  public BigDecimal getChunksCount() {
    return chunksCount;
  }


  public void setChunksCount(BigDecimal chunksCount) {
    this.chunksCount = chunksCount;
  }


  public DocumentStatusInfoDetails needed(BigDecimal needed) {
    
    this.needed = needed;
    return this;
  }

   /**
   * &#x60;INSUFFICIENT_TOKENS&#x60;: сколько токенов нужно
   * @return needed
  **/
  @javax.annotation.Nullable
  public BigDecimal getNeeded() {
    return needed;
  }


  public void setNeeded(BigDecimal needed) {
    this.needed = needed;
  }


  public DocumentStatusInfoDetails available(BigDecimal available) {
    
    this.available = available;
    return this;
  }

   /**
   * &#x60;INSUFFICIENT_TOKENS&#x60;: сколько токенов доступно
   * @return available
  **/
  @javax.annotation.Nullable
  public BigDecimal getAvailable() {
    return available;
  }


  public void setAvailable(BigDecimal available) {
    this.available = available;
  }


  public DocumentStatusInfoDetails estimated(BigDecimal estimated) {
    
    this.estimated = estimated;
    return this;
  }

   /**
   * &#x60;INSUFFICIENT_TOKENS&#x60;: оценка без запаса
   * @return estimated
  **/
  @javax.annotation.Nullable
  public BigDecimal getEstimated() {
    return estimated;
  }


  public void setEstimated(BigDecimal estimated) {
    this.estimated = estimated;
  }


  public DocumentStatusInfoDetails balance(BigDecimal balance) {
    
    this.balance = balance;
    return this;
  }

   /**
   * &#x60;CRITICAL_BALANCE&#x60;, &#x60;NEGATIVE_BALANCE&#x60;: текущий остаток токенов
   * @return balance
  **/
  @javax.annotation.Nullable
  public BigDecimal getBalance() {
    return balance;
  }


  public void setBalance(BigDecimal balance) {
    this.balance = balance;
  }


  public DocumentStatusInfoDetails minRequired(BigDecimal minRequired) {
    
    this.minRequired = minRequired;
    return this;
  }

   /**
   * &#x60;CRITICAL_BALANCE&#x60;: минимально необходимый остаток
   * @return minRequired
  **/
  @javax.annotation.Nullable
  public BigDecimal getMinRequired() {
    return minRequired;
  }


  public void setMinRequired(BigDecimal minRequired) {
    this.minRequired = minRequired;
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
   * @return the DocumentStatusInfoDetails instance itself
   */
  public DocumentStatusInfoDetails putAdditionalProperty(String key, Object value) {
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
    DocumentStatusInfoDetails documentStatusInfoDetails = (DocumentStatusInfoDetails) o;
    return Objects.equals(this.startedAt, documentStatusInfoDetails.startedAt) &&
        Objects.equals(this.currentStage, documentStatusInfoDetails.currentStage) &&
        Objects.equals(this.progress, documentStatusInfoDetails.progress) &&
        Objects.equals(this.indexingDurationMs, documentStatusInfoDetails.indexingDurationMs) &&
        Objects.equals(this.tokensUsed, documentStatusInfoDetails.tokensUsed) &&
        Objects.equals(this.tokensEstimated, documentStatusInfoDetails.tokensEstimated) &&
        Objects.equals(this.chunksCount, documentStatusInfoDetails.chunksCount) &&
        Objects.equals(this.needed, documentStatusInfoDetails.needed) &&
        Objects.equals(this.available, documentStatusInfoDetails.available) &&
        Objects.equals(this.estimated, documentStatusInfoDetails.estimated) &&
        Objects.equals(this.balance, documentStatusInfoDetails.balance) &&
        Objects.equals(this.minRequired, documentStatusInfoDetails.minRequired)&&
        Objects.equals(this.additionalProperties, documentStatusInfoDetails.additionalProperties);
  }

  @Override
  public int hashCode() {
    return Objects.hash(startedAt, currentStage, progress, indexingDurationMs, tokensUsed, tokensEstimated, chunksCount, needed, available, estimated, balance, minRequired, additionalProperties);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class DocumentStatusInfoDetails {\n");
    sb.append("    startedAt: ").append(toIndentedString(startedAt)).append("\n");
    sb.append("    currentStage: ").append(toIndentedString(currentStage)).append("\n");
    sb.append("    progress: ").append(toIndentedString(progress)).append("\n");
    sb.append("    indexingDurationMs: ").append(toIndentedString(indexingDurationMs)).append("\n");
    sb.append("    tokensUsed: ").append(toIndentedString(tokensUsed)).append("\n");
    sb.append("    tokensEstimated: ").append(toIndentedString(tokensEstimated)).append("\n");
    sb.append("    chunksCount: ").append(toIndentedString(chunksCount)).append("\n");
    sb.append("    needed: ").append(toIndentedString(needed)).append("\n");
    sb.append("    available: ").append(toIndentedString(available)).append("\n");
    sb.append("    estimated: ").append(toIndentedString(estimated)).append("\n");
    sb.append("    balance: ").append(toIndentedString(balance)).append("\n");
    sb.append("    minRequired: ").append(toIndentedString(minRequired)).append("\n");
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
    openapiFields.add("started_at");
    openapiFields.add("current_stage");
    openapiFields.add("progress");
    openapiFields.add("indexing_duration_ms");
    openapiFields.add("tokens_used");
    openapiFields.add("tokens_estimated");
    openapiFields.add("chunks_count");
    openapiFields.add("needed");
    openapiFields.add("available");
    openapiFields.add("estimated");
    openapiFields.add("balance");
    openapiFields.add("min_required");

    // a set of required properties/fields (JSON key names)
    openapiRequiredFields = new HashSet<String>();
  }

 /**
  * Validates the JSON Element and throws an exception if issues found
  *
  * @param jsonElement JSON Element
  * @throws IOException if the JSON Element is invalid with respect to DocumentStatusInfoDetails
  */
  public static void validateJsonElement(JsonElement jsonElement) throws IOException {
      if (jsonElement == null) {
        if (!DocumentStatusInfoDetails.openapiRequiredFields.isEmpty()) { // has required fields but JSON element is null
          throw new IllegalArgumentException(String.format("The required field(s) %s in DocumentStatusInfoDetails is not found in the empty JSON string", DocumentStatusInfoDetails.openapiRequiredFields.toString()));
        }
      }
        JsonObject jsonObj = jsonElement.getAsJsonObject();
      if ((jsonObj.get("current_stage") != null && !jsonObj.get("current_stage").isJsonNull()) && !jsonObj.get("current_stage").isJsonPrimitive()) {
        throw new IllegalArgumentException(String.format("Expected the field `current_stage` to be a primitive type in the JSON string but got `%s`", jsonObj.get("current_stage").toString()));
      }
  }

  public static class CustomTypeAdapterFactory implements TypeAdapterFactory {
    @SuppressWarnings("unchecked")
    @Override
    public <T> TypeAdapter<T> create(Gson gson, TypeToken<T> type) {
       if (!DocumentStatusInfoDetails.class.isAssignableFrom(type.getRawType())) {
         return null; // this class only serializes 'DocumentStatusInfoDetails' and its subtypes
       }
       final TypeAdapter<JsonElement> elementAdapter = gson.getAdapter(JsonElement.class);
       final TypeAdapter<DocumentStatusInfoDetails> thisAdapter
                        = gson.getDelegateAdapter(this, TypeToken.get(DocumentStatusInfoDetails.class));

       return (TypeAdapter<T>) new TypeAdapter<DocumentStatusInfoDetails>() {
           @Override
           public void write(JsonWriter out, DocumentStatusInfoDetails value) throws IOException {
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
           public DocumentStatusInfoDetails read(JsonReader in) throws IOException {
             JsonElement jsonElement = elementAdapter.read(in);
             validateJsonElement(jsonElement);
             JsonObject jsonObj = jsonElement.getAsJsonObject();
             // store additional fields in the deserialized instance
             DocumentStatusInfoDetails instance = thisAdapter.fromJsonTree(jsonObj);
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
  * Create an instance of DocumentStatusInfoDetails given an JSON string
  *
  * @param jsonString JSON string
  * @return An instance of DocumentStatusInfoDetails
  * @throws IOException if the JSON string is invalid with respect to DocumentStatusInfoDetails
  */
  public static DocumentStatusInfoDetails fromJson(String jsonString) throws IOException {
    return JSON.getGson().fromJson(jsonString, DocumentStatusInfoDetails.class);
  }

 /**
  * Convert an instance of DocumentStatusInfoDetails to an JSON string
  *
  * @return JSON string
  */
  public String toJson() {
    return JSON.getGson().toJson(this);
  }
}

