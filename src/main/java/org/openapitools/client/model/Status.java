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
import org.openapitools.client.model.StatusCompanyInfo;

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
 * Статус аккаунта
 */
@javax.annotation.Generated(value = "org.openapitools.codegen.languages.JavaClientCodegen", date = "2024-05-13T11:29:00.286642Z[Etc/UTC]")
public class Status {
  public static final String SERIALIZED_NAME_IS_BLOCKED = "is_blocked";
  @SerializedName(SERIALIZED_NAME_IS_BLOCKED)
  private Boolean isBlocked;

  public static final String SERIALIZED_NAME_IS_PERMANENT_BLOCKED = "is_permanent_blocked";
  @SerializedName(SERIALIZED_NAME_IS_PERMANENT_BLOCKED)
  private Boolean isPermanentBlocked;

  public static final String SERIALIZED_NAME_IS_SEND_BILL_LETTERS = "is_send_bill_letters";
  @SerializedName(SERIALIZED_NAME_IS_SEND_BILL_LETTERS)
  private Boolean isSendBillLetters;

  public static final String SERIALIZED_NAME_COMPANY_INFO = "company_info";
  @SerializedName(SERIALIZED_NAME_COMPANY_INFO)
  private StatusCompanyInfo companyInfo;

  public static final String SERIALIZED_NAME_LAST_PASSWORD_CHANGED_AT = "last_password_changed_at";
  @SerializedName(SERIALIZED_NAME_LAST_PASSWORD_CHANGED_AT)
  private String lastPasswordChangedAt;

  public static final String SERIALIZED_NAME_YM_CLIENT_ID = "ym_client_id";
  @SerializedName(SERIALIZED_NAME_YM_CLIENT_ID)
  private String ymClientId;

  public Status() {
  }

  public Status isBlocked(Boolean isBlocked) {
    
    this.isBlocked = isBlocked;
    return this;
  }

   /**
   * Это логическое значение, которое показывает, заблокирован ли аккаунт.
   * @return isBlocked
  **/
  @javax.annotation.Nonnull
  public Boolean getIsBlocked() {
    return isBlocked;
  }


  public void setIsBlocked(Boolean isBlocked) {
    this.isBlocked = isBlocked;
  }


  public Status isPermanentBlocked(Boolean isPermanentBlocked) {
    
    this.isPermanentBlocked = isPermanentBlocked;
    return this;
  }

   /**
   * Это логическое значение, которое показывает, заблокирован ли аккаунт навсегда.
   * @return isPermanentBlocked
  **/
  @javax.annotation.Nonnull
  public Boolean getIsPermanentBlocked() {
    return isPermanentBlocked;
  }


  public void setIsPermanentBlocked(Boolean isPermanentBlocked) {
    this.isPermanentBlocked = isPermanentBlocked;
  }


  public Status isSendBillLetters(Boolean isSendBillLetters) {
    
    this.isSendBillLetters = isSendBillLetters;
    return this;
  }

   /**
   * Это логическое значение, которое показывает, требуется ли отправлять счета на почту.
   * @return isSendBillLetters
  **/
  @javax.annotation.Nonnull
  public Boolean getIsSendBillLetters() {
    return isSendBillLetters;
  }


  public void setIsSendBillLetters(Boolean isSendBillLetters) {
    this.isSendBillLetters = isSendBillLetters;
  }


  public Status companyInfo(StatusCompanyInfo companyInfo) {
    
    this.companyInfo = companyInfo;
    return this;
  }

   /**
   * Get companyInfo
   * @return companyInfo
  **/
  @javax.annotation.Nonnull
  public StatusCompanyInfo getCompanyInfo() {
    return companyInfo;
  }


  public void setCompanyInfo(StatusCompanyInfo companyInfo) {
    this.companyInfo = companyInfo;
  }


  public Status lastPasswordChangedAt(String lastPasswordChangedAt) {
    
    this.lastPasswordChangedAt = lastPasswordChangedAt;
    return this;
  }

   /**
   * Значение времени, указанное в комбинированном формате даты и времени ISO8601, которое представляет, когда последний раз изменялся пароль.
   * @return lastPasswordChangedAt
  **/
  @javax.annotation.Nonnull
  public String getLastPasswordChangedAt() {
    return lastPasswordChangedAt;
  }


  public void setLastPasswordChangedAt(String lastPasswordChangedAt) {
    this.lastPasswordChangedAt = lastPasswordChangedAt;
  }


  public Status ymClientId(String ymClientId) {
    
    this.ymClientId = ymClientId;
    return this;
  }

   /**
   * Идентификатор аккаунта для яндекс метрики.
   * @return ymClientId
  **/
  @javax.annotation.Nullable
  public String getYmClientId() {
    return ymClientId;
  }


  public void setYmClientId(String ymClientId) {
    this.ymClientId = ymClientId;
  }



  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Status status = (Status) o;
    return Objects.equals(this.isBlocked, status.isBlocked) &&
        Objects.equals(this.isPermanentBlocked, status.isPermanentBlocked) &&
        Objects.equals(this.isSendBillLetters, status.isSendBillLetters) &&
        Objects.equals(this.companyInfo, status.companyInfo) &&
        Objects.equals(this.lastPasswordChangedAt, status.lastPasswordChangedAt) &&
        Objects.equals(this.ymClientId, status.ymClientId);
  }

  @Override
  public int hashCode() {
    return Objects.hash(isBlocked, isPermanentBlocked, isSendBillLetters, companyInfo, lastPasswordChangedAt, ymClientId);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class Status {\n");
    sb.append("    isBlocked: ").append(toIndentedString(isBlocked)).append("\n");
    sb.append("    isPermanentBlocked: ").append(toIndentedString(isPermanentBlocked)).append("\n");
    sb.append("    isSendBillLetters: ").append(toIndentedString(isSendBillLetters)).append("\n");
    sb.append("    companyInfo: ").append(toIndentedString(companyInfo)).append("\n");
    sb.append("    lastPasswordChangedAt: ").append(toIndentedString(lastPasswordChangedAt)).append("\n");
    sb.append("    ymClientId: ").append(toIndentedString(ymClientId)).append("\n");
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
    openapiFields.add("is_blocked");
    openapiFields.add("is_permanent_blocked");
    openapiFields.add("is_send_bill_letters");
    openapiFields.add("company_info");
    openapiFields.add("last_password_changed_at");
    openapiFields.add("ym_client_id");

    // a set of required properties/fields (JSON key names)
    openapiRequiredFields = new HashSet<String>();
    openapiRequiredFields.add("is_blocked");
    openapiRequiredFields.add("is_permanent_blocked");
    openapiRequiredFields.add("is_send_bill_letters");
    openapiRequiredFields.add("company_info");
    openapiRequiredFields.add("last_password_changed_at");
    openapiRequiredFields.add("ym_client_id");
  }

 /**
  * Validates the JSON Element and throws an exception if issues found
  *
  * @param jsonElement JSON Element
  * @throws IOException if the JSON Element is invalid with respect to Status
  */
  public static void validateJsonElement(JsonElement jsonElement) throws IOException {
      if (jsonElement == null) {
        if (!Status.openapiRequiredFields.isEmpty()) { // has required fields but JSON element is null
          throw new IllegalArgumentException(String.format("The required field(s) %s in Status is not found in the empty JSON string", Status.openapiRequiredFields.toString()));
        }
      }

      Set<Entry<String, JsonElement>> entries = jsonElement.getAsJsonObject().entrySet();
      // check to see if the JSON string contains additional fields
      for (Entry<String, JsonElement> entry : entries) {
        if (!Status.openapiFields.contains(entry.getKey())) {
          throw new IllegalArgumentException(String.format("The field `%s` in the JSON string is not defined in the `Status` properties. JSON: %s", entry.getKey(), jsonElement.toString()));
        }
      }

      // check to make sure all required properties/fields are present in the JSON string
      for (String requiredField : Status.openapiRequiredFields) {
        if (jsonElement.getAsJsonObject().get(requiredField) == null) {
          throw new IllegalArgumentException(String.format("The required field `%s` is not found in the JSON string: %s", requiredField, jsonElement.toString()));
        }
      }
        JsonObject jsonObj = jsonElement.getAsJsonObject();
      // validate the required field `company_info`
      StatusCompanyInfo.validateJsonElement(jsonObj.get("company_info"));
      if (!jsonObj.get("last_password_changed_at").isJsonPrimitive()) {
        throw new IllegalArgumentException(String.format("Expected the field `last_password_changed_at` to be a primitive type in the JSON string but got `%s`", jsonObj.get("last_password_changed_at").toString()));
      }
      if ((jsonObj.get("ym_client_id") != null && !jsonObj.get("ym_client_id").isJsonNull()) && !jsonObj.get("ym_client_id").isJsonPrimitive()) {
        throw new IllegalArgumentException(String.format("Expected the field `ym_client_id` to be a primitive type in the JSON string but got `%s`", jsonObj.get("ym_client_id").toString()));
      }
  }

  public static class CustomTypeAdapterFactory implements TypeAdapterFactory {
    @SuppressWarnings("unchecked")
    @Override
    public <T> TypeAdapter<T> create(Gson gson, TypeToken<T> type) {
       if (!Status.class.isAssignableFrom(type.getRawType())) {
         return null; // this class only serializes 'Status' and its subtypes
       }
       final TypeAdapter<JsonElement> elementAdapter = gson.getAdapter(JsonElement.class);
       final TypeAdapter<Status> thisAdapter
                        = gson.getDelegateAdapter(this, TypeToken.get(Status.class));

       return (TypeAdapter<T>) new TypeAdapter<Status>() {
           @Override
           public void write(JsonWriter out, Status value) throws IOException {
             JsonObject obj = thisAdapter.toJsonTree(value).getAsJsonObject();
             elementAdapter.write(out, obj);
           }

           @Override
           public Status read(JsonReader in) throws IOException {
             JsonElement jsonElement = elementAdapter.read(in);
             validateJsonElement(jsonElement);
             return thisAdapter.fromJsonTree(jsonElement);
           }

       }.nullSafe();
    }
  }

 /**
  * Create an instance of Status given an JSON string
  *
  * @param jsonString JSON string
  * @return An instance of Status
  * @throws IOException if the JSON string is invalid with respect to Status
  */
  public static Status fromJson(String jsonString) throws IOException {
    return JSON.getGson().fromJson(jsonString, Status.class);
  }

 /**
  * Convert an instance of Status to an JSON string
  *
  * @return JSON string
  */
  public String toJson() {
    return JSON.getGson().toJson(this);
  }
}

