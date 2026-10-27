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
 * Параметры создания лимита ключа
 */
@javax.annotation.Generated(value = "org.openapitools.codegen.languages.JavaClientCodegen", date = "2026-10-27T09:28:31.074093Z[Etc/UTC]")
public class CreateAiKeyLimit {
  public static final String SERIALIZED_NAME_ENTITY_TYPE = "entity_type";
  @SerializedName(SERIALIZED_NAME_ENTITY_TYPE)
  private String entityType;

  public static final String SERIALIZED_NAME_ENTITY_ID = "entity_id";
  @SerializedName(SERIALIZED_NAME_ENTITY_ID)
  private String entityId;

  /**
   * Период лимита: &#x60;hour&#x60; — час, &#x60;day&#x60; — сутки, &#x60;week&#x60; — 7 дней, &#x60;month&#x60; — 30 дней
   */
  @JsonAdapter(LimitTypeEnum.Adapter.class)
  public enum LimitTypeEnum {
    HOUR("hour"),
    
    DAY("day"),
    
    WEEK("week"),
    
    MONTH("month");

    private String value;

    LimitTypeEnum(String value) {
      this.value = value;
    }

    public String getValue() {
      return value;
    }

    @Override
    public String toString() {
      return String.valueOf(value);
    }

    public static LimitTypeEnum fromValue(String value) {
      for (LimitTypeEnum b : LimitTypeEnum.values()) {
        if (b.value.equals(value)) {
          return b;
        }
      }
      throw new IllegalArgumentException("Unexpected value '" + value + "'");
    }

    public static class Adapter extends TypeAdapter<LimitTypeEnum> {
      @Override
      public void write(final JsonWriter jsonWriter, final LimitTypeEnum enumeration) throws IOException {
        jsonWriter.value(enumeration.getValue());
      }

      @Override
      public LimitTypeEnum read(final JsonReader jsonReader) throws IOException {
        String value =  jsonReader.nextString();
        return LimitTypeEnum.fromValue(value);
      }
    }
  }

  public static final String SERIALIZED_NAME_LIMIT_TYPE = "limit_type";
  @SerializedName(SERIALIZED_NAME_LIMIT_TYPE)
  private LimitTypeEnum limitType;

  public static final String SERIALIZED_NAME_MODEL_ID = "model_id";
  @SerializedName(SERIALIZED_NAME_MODEL_ID)
  private Integer modelId;

  public static final String SERIALIZED_NAME_LIMIT = "limit";
  @SerializedName(SERIALIZED_NAME_LIMIT)
  private Integer limit;

  public static final String SERIALIZED_NAME_MONEY_LIMIT = "money_limit";
  @SerializedName(SERIALIZED_NAME_MONEY_LIMIT)
  private BigDecimal moneyLimit;

  public CreateAiKeyLimit() {
  }

  public CreateAiKeyLimit entityType(String entityType) {
    
    this.entityType = entityType;
    return this;
  }

   /**
   * Тип сущности, на которую действует лимит. Для лимита на сам ключ передайте его тип — поле &#x60;entity_type&#x60; ключа (&#x60;aikey&#x60; или &#x60;k8s&#x60;)
   * @return entityType
  **/
  @javax.annotation.Nonnull
  public String getEntityType() {
    return entityType;
  }


  public void setEntityType(String entityType) {
    this.entityType = entityType;
  }


  public CreateAiKeyLimit entityId(String entityId) {
    
    this.entityId = entityId;
    return this;
  }

   /**
   * ID сущности. Для лимита на сам ключ не нужен: подставляется ID ключа
   * @return entityId
  **/
  @javax.annotation.Nullable
  public String getEntityId() {
    return entityId;
  }


  public void setEntityId(String entityId) {
    this.entityId = entityId;
  }


  public CreateAiKeyLimit limitType(LimitTypeEnum limitType) {
    
    this.limitType = limitType;
    return this;
  }

   /**
   * Период лимита: &#x60;hour&#x60; — час, &#x60;day&#x60; — сутки, &#x60;week&#x60; — 7 дней, &#x60;month&#x60; — 30 дней
   * @return limitType
  **/
  @javax.annotation.Nonnull
  public LimitTypeEnum getLimitType() {
    return limitType;
  }


  public void setLimitType(LimitTypeEnum limitType) {
    this.limitType = limitType;
  }


  public CreateAiKeyLimit modelId(Integer modelId) {
    
    this.modelId = modelId;
    return this;
  }

   /**
   * ID модели из каталога (&#x60;GET /api/v3/cloud-ai/models&#x60;), на которую действует лимит. Не передан — лимит на все модели
   * @return modelId
  **/
  @javax.annotation.Nullable
  public Integer getModelId() {
    return modelId;
  }


  public void setModelId(Integer modelId) {
    this.modelId = modelId;
  }


  public CreateAiKeyLimit limit(Integer limit) {
    
    this.limit = limit;
    return this;
  }

   /**
   * Лимит в токенах за период. Нужен &#x60;limit&#x60; или &#x60;money_limit&#x60;; если передан только &#x60;money_limit&#x60;, &#x60;limit&#x60; будет &#x60;0&#x60;. &#x60;0&#x60; без &#x60;money_limit&#x60; — запросы запрещены
   * minimum: 0
   * maximum: 4294967295
   * @return limit
  **/
  @javax.annotation.Nullable
  public Integer getLimit() {
    return limit;
  }


  public void setLimit(Integer limit) {
    this.limit = limit;
  }


  public CreateAiKeyLimit moneyLimit(BigDecimal moneyLimit) {
    
    this.moneyLimit = moneyLimit;
    return this;
  }

   /**
   * Лимит в деньгах за период, в валюте аккаунта, не больше 8 знаков после точки. Приоритетнее &#x60;limit&#x60;. &#x60;0&#x60; — запросы запрещены
   * minimum: 0
   * maximum: 9999999999
   * @return moneyLimit
  **/
  @javax.annotation.Nullable
  public BigDecimal getMoneyLimit() {
    return moneyLimit;
  }


  public void setMoneyLimit(BigDecimal moneyLimit) {
    this.moneyLimit = moneyLimit;
  }



  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    CreateAiKeyLimit createAiKeyLimit = (CreateAiKeyLimit) o;
    return Objects.equals(this.entityType, createAiKeyLimit.entityType) &&
        Objects.equals(this.entityId, createAiKeyLimit.entityId) &&
        Objects.equals(this.limitType, createAiKeyLimit.limitType) &&
        Objects.equals(this.modelId, createAiKeyLimit.modelId) &&
        Objects.equals(this.limit, createAiKeyLimit.limit) &&
        Objects.equals(this.moneyLimit, createAiKeyLimit.moneyLimit);
  }

  private static <T> boolean equalsNullable(JsonNullable<T> a, JsonNullable<T> b) {
    return a == b || (a != null && b != null && a.isPresent() && b.isPresent() && Objects.deepEquals(a.get(), b.get()));
  }

  @Override
  public int hashCode() {
    return Objects.hash(entityType, entityId, limitType, modelId, limit, moneyLimit);
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
    sb.append("class CreateAiKeyLimit {\n");
    sb.append("    entityType: ").append(toIndentedString(entityType)).append("\n");
    sb.append("    entityId: ").append(toIndentedString(entityId)).append("\n");
    sb.append("    limitType: ").append(toIndentedString(limitType)).append("\n");
    sb.append("    modelId: ").append(toIndentedString(modelId)).append("\n");
    sb.append("    limit: ").append(toIndentedString(limit)).append("\n");
    sb.append("    moneyLimit: ").append(toIndentedString(moneyLimit)).append("\n");
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
    openapiFields.add("entity_type");
    openapiFields.add("entity_id");
    openapiFields.add("limit_type");
    openapiFields.add("model_id");
    openapiFields.add("limit");
    openapiFields.add("money_limit");

    // a set of required properties/fields (JSON key names)
    openapiRequiredFields = new HashSet<String>();
    openapiRequiredFields.add("entity_type");
    openapiRequiredFields.add("limit_type");
  }

 /**
  * Validates the JSON Element and throws an exception if issues found
  *
  * @param jsonElement JSON Element
  * @throws IOException if the JSON Element is invalid with respect to CreateAiKeyLimit
  */
  public static void validateJsonElement(JsonElement jsonElement) throws IOException {
      if (jsonElement == null) {
        if (!CreateAiKeyLimit.openapiRequiredFields.isEmpty()) { // has required fields but JSON element is null
          throw new IllegalArgumentException(String.format("The required field(s) %s in CreateAiKeyLimit is not found in the empty JSON string", CreateAiKeyLimit.openapiRequiredFields.toString()));
        }
      }

      Set<Entry<String, JsonElement>> entries = jsonElement.getAsJsonObject().entrySet();
      // check to see if the JSON string contains additional fields
      for (Entry<String, JsonElement> entry : entries) {
        if (!CreateAiKeyLimit.openapiFields.contains(entry.getKey())) {
          throw new IllegalArgumentException(String.format("The field `%s` in the JSON string is not defined in the `CreateAiKeyLimit` properties. JSON: %s", entry.getKey(), jsonElement.toString()));
        }
      }

      // check to make sure all required properties/fields are present in the JSON string
      for (String requiredField : CreateAiKeyLimit.openapiRequiredFields) {
        if (jsonElement.getAsJsonObject().get(requiredField) == null) {
          throw new IllegalArgumentException(String.format("The required field `%s` is not found in the JSON string: %s", requiredField, jsonElement.toString()));
        }
      }
        JsonObject jsonObj = jsonElement.getAsJsonObject();
      if (!jsonObj.get("entity_type").isJsonPrimitive()) {
        throw new IllegalArgumentException(String.format("Expected the field `entity_type` to be a primitive type in the JSON string but got `%s`", jsonObj.get("entity_type").toString()));
      }
      if ((jsonObj.get("entity_id") != null && !jsonObj.get("entity_id").isJsonNull()) && !jsonObj.get("entity_id").isJsonPrimitive()) {
        throw new IllegalArgumentException(String.format("Expected the field `entity_id` to be a primitive type in the JSON string but got `%s`", jsonObj.get("entity_id").toString()));
      }
      if (!jsonObj.get("limit_type").isJsonPrimitive()) {
        throw new IllegalArgumentException(String.format("Expected the field `limit_type` to be a primitive type in the JSON string but got `%s`", jsonObj.get("limit_type").toString()));
      }
  }

  public static class CustomTypeAdapterFactory implements TypeAdapterFactory {
    @SuppressWarnings("unchecked")
    @Override
    public <T> TypeAdapter<T> create(Gson gson, TypeToken<T> type) {
       if (!CreateAiKeyLimit.class.isAssignableFrom(type.getRawType())) {
         return null; // this class only serializes 'CreateAiKeyLimit' and its subtypes
       }
       final TypeAdapter<JsonElement> elementAdapter = gson.getAdapter(JsonElement.class);
       final TypeAdapter<CreateAiKeyLimit> thisAdapter
                        = gson.getDelegateAdapter(this, TypeToken.get(CreateAiKeyLimit.class));

       return (TypeAdapter<T>) new TypeAdapter<CreateAiKeyLimit>() {
           @Override
           public void write(JsonWriter out, CreateAiKeyLimit value) throws IOException {
             JsonObject obj = thisAdapter.toJsonTree(value).getAsJsonObject();
             elementAdapter.write(out, obj);
           }

           @Override
           public CreateAiKeyLimit read(JsonReader in) throws IOException {
             JsonElement jsonElement = elementAdapter.read(in);
             validateJsonElement(jsonElement);
             return thisAdapter.fromJsonTree(jsonElement);
           }

       }.nullSafe();
    }
  }

 /**
  * Create an instance of CreateAiKeyLimit given an JSON string
  *
  * @param jsonString JSON string
  * @return An instance of CreateAiKeyLimit
  * @throws IOException if the JSON string is invalid with respect to CreateAiKeyLimit
  */
  public static CreateAiKeyLimit fromJson(String jsonString) throws IOException {
    return JSON.getGson().fromJson(jsonString, CreateAiKeyLimit.class);
  }

 /**
  * Convert an instance of CreateAiKeyLimit to an JSON string
  *
  * @return JSON string
  */
  public String toJson() {
    return JSON.getGson().toJson(this);
  }
}

