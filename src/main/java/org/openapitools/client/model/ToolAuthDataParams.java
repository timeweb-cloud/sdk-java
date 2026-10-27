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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
 * Параметры авторизации. Набор полей зависит от &#x60;type&#x60;: для &#x60;none&#x60; — пустой объект, для &#x60;bearer&#x60; — &#x60;token&#x60;, для &#x60;custom_headers&#x60; — &#x60;headers&#x60;, для &#x60;oauth2&#x60; — поля OAuth2-клиента.
 */
@javax.annotation.Generated(value = "org.openapitools.codegen.languages.JavaClientCodegen", date = "2026-10-27T09:28:31.074093Z[Etc/UTC]")
public class ToolAuthDataParams {
  public static final String SERIALIZED_NAME_TOKEN = "token";
  @SerializedName(SERIALIZED_NAME_TOKEN)
  private String token;

  public static final String SERIALIZED_NAME_HEADERS = "headers";
  @SerializedName(SERIALIZED_NAME_HEADERS)
  private Map<String, String> headers = new HashMap<>();

  public static final String SERIALIZED_NAME_CLIENT_ID = "client_id";
  @SerializedName(SERIALIZED_NAME_CLIENT_ID)
  private String clientId;

  public static final String SERIALIZED_NAME_CLIENT_SECRET = "client_secret";
  @SerializedName(SERIALIZED_NAME_CLIENT_SECRET)
  private String clientSecret;

  public static final String SERIALIZED_NAME_AUTHORIZATION_URL = "authorization_url";
  @SerializedName(SERIALIZED_NAME_AUTHORIZATION_URL)
  private String authorizationUrl;

  public static final String SERIALIZED_NAME_TOKEN_URL = "token_url";
  @SerializedName(SERIALIZED_NAME_TOKEN_URL)
  private String tokenUrl;

  public static final String SERIALIZED_NAME_REGISTRATION_URL = "registration_url";
  @SerializedName(SERIALIZED_NAME_REGISTRATION_URL)
  private String registrationUrl;

  public static final String SERIALIZED_NAME_SCOPES = "scopes";
  @SerializedName(SERIALIZED_NAME_SCOPES)
  private List<String> scopes;

  public ToolAuthDataParams() {
  }

  public ToolAuthDataParams token(String token) {
    
    this.token = token;
    return this;
  }

   /**
   * Токен для заголовка &#x60;Authorization: Bearer&#x60; (для &#x60;bearer&#x60;). В ответах заменён на &#x60;********&#x60;
   * @return token
  **/
  @javax.annotation.Nullable
  public String getToken() {
    return token;
  }


  public void setToken(String token) {
    this.token = token;
  }


  public ToolAuthDataParams headers(Map<String, String> headers) {
    
    this.headers = headers;
    return this;
  }

  public ToolAuthDataParams putHeadersItem(String key, String headersItem) {
    if (this.headers == null) {
      this.headers = new HashMap<>();
    }
    this.headers.put(key, headersItem);
    return this;
  }

   /**
   * HTTP-заголовки, которые передаются MCP-серверу (для &#x60;custom_headers&#x60;). В ответах значения заменены на &#x60;********&#x60;
   * @return headers
  **/
  @javax.annotation.Nullable
  public Map<String, String> getHeaders() {
    return headers;
  }


  public void setHeaders(Map<String, String> headers) {
    this.headers = headers;
  }


  public ToolAuthDataParams clientId(String clientId) {
    
    this.clientId = clientId;
    return this;
  }

   /**
   * ID OAuth2-клиента (для &#x60;oauth2&#x60;). Не нужен, если сервер поддерживает динамическую регистрацию клиентов
   * @return clientId
  **/
  @javax.annotation.Nullable
  public String getClientId() {
    return clientId;
  }


  public void setClientId(String clientId) {
    this.clientId = clientId;
  }


  public ToolAuthDataParams clientSecret(String clientSecret) {
    
    this.clientSecret = clientSecret;
    return this;
  }

   /**
   * Секрет OAuth2-клиента (для &#x60;oauth2&#x60;). В ответах заменён на &#x60;********&#x60;
   * @return clientSecret
  **/
  @javax.annotation.Nullable
  public String getClientSecret() {
    return clientSecret;
  }


  public void setClientSecret(String clientSecret) {
    this.clientSecret = clientSecret;
  }


  public ToolAuthDataParams authorizationUrl(String authorizationUrl) {
    
    this.authorizationUrl = authorizationUrl;
    return this;
  }

   /**
   * Адрес страницы авторизации провайдера (для &#x60;oauth2&#x60;). Если не задан, определяется по метаданным сервера
   * @return authorizationUrl
  **/
  @javax.annotation.Nullable
  public String getAuthorizationUrl() {
    return authorizationUrl;
  }


  public void setAuthorizationUrl(String authorizationUrl) {
    this.authorizationUrl = authorizationUrl;
  }


  public ToolAuthDataParams tokenUrl(String tokenUrl) {
    
    this.tokenUrl = tokenUrl;
    return this;
  }

   /**
   * Адрес получения токенов провайдера (для &#x60;oauth2&#x60;). Если не задан, определяется по метаданным сервера
   * @return tokenUrl
  **/
  @javax.annotation.Nullable
  public String getTokenUrl() {
    return tokenUrl;
  }


  public void setTokenUrl(String tokenUrl) {
    this.tokenUrl = tokenUrl;
  }


  public ToolAuthDataParams registrationUrl(String registrationUrl) {
    
    this.registrationUrl = registrationUrl;
    return this;
  }

   /**
   * Адрес динамической регистрации клиента (для &#x60;oauth2&#x60;)
   * @return registrationUrl
  **/
  @javax.annotation.Nullable
  public String getRegistrationUrl() {
    return registrationUrl;
  }


  public void setRegistrationUrl(String registrationUrl) {
    this.registrationUrl = registrationUrl;
  }


  public ToolAuthDataParams scopes(List<String> scopes) {
    
    this.scopes = scopes;
    return this;
  }

  public ToolAuthDataParams addScopesItem(String scopesItem) {
    if (this.scopes == null) {
      this.scopes = new ArrayList<>();
    }
    this.scopes.add(scopesItem);
    return this;
  }

   /**
   * Запрашиваемые права (для &#x60;oauth2&#x60;). Если не заданы, берутся из метаданных сервера
   * @return scopes
  **/
  @javax.annotation.Nullable
  public List<String> getScopes() {
    return scopes;
  }


  public void setScopes(List<String> scopes) {
    this.scopes = scopes;
  }



  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    ToolAuthDataParams toolAuthDataParams = (ToolAuthDataParams) o;
    return Objects.equals(this.token, toolAuthDataParams.token) &&
        Objects.equals(this.headers, toolAuthDataParams.headers) &&
        Objects.equals(this.clientId, toolAuthDataParams.clientId) &&
        Objects.equals(this.clientSecret, toolAuthDataParams.clientSecret) &&
        Objects.equals(this.authorizationUrl, toolAuthDataParams.authorizationUrl) &&
        Objects.equals(this.tokenUrl, toolAuthDataParams.tokenUrl) &&
        Objects.equals(this.registrationUrl, toolAuthDataParams.registrationUrl) &&
        Objects.equals(this.scopes, toolAuthDataParams.scopes);
  }

  @Override
  public int hashCode() {
    return Objects.hash(token, headers, clientId, clientSecret, authorizationUrl, tokenUrl, registrationUrl, scopes);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class ToolAuthDataParams {\n");
    sb.append("    token: ").append(toIndentedString(token)).append("\n");
    sb.append("    headers: ").append(toIndentedString(headers)).append("\n");
    sb.append("    clientId: ").append(toIndentedString(clientId)).append("\n");
    sb.append("    clientSecret: ").append(toIndentedString(clientSecret)).append("\n");
    sb.append("    authorizationUrl: ").append(toIndentedString(authorizationUrl)).append("\n");
    sb.append("    tokenUrl: ").append(toIndentedString(tokenUrl)).append("\n");
    sb.append("    registrationUrl: ").append(toIndentedString(registrationUrl)).append("\n");
    sb.append("    scopes: ").append(toIndentedString(scopes)).append("\n");
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
    openapiFields.add("token");
    openapiFields.add("headers");
    openapiFields.add("client_id");
    openapiFields.add("client_secret");
    openapiFields.add("authorization_url");
    openapiFields.add("token_url");
    openapiFields.add("registration_url");
    openapiFields.add("scopes");

    // a set of required properties/fields (JSON key names)
    openapiRequiredFields = new HashSet<String>();
  }

 /**
  * Validates the JSON Element and throws an exception if issues found
  *
  * @param jsonElement JSON Element
  * @throws IOException if the JSON Element is invalid with respect to ToolAuthDataParams
  */
  public static void validateJsonElement(JsonElement jsonElement) throws IOException {
      if (jsonElement == null) {
        if (!ToolAuthDataParams.openapiRequiredFields.isEmpty()) { // has required fields but JSON element is null
          throw new IllegalArgumentException(String.format("The required field(s) %s in ToolAuthDataParams is not found in the empty JSON string", ToolAuthDataParams.openapiRequiredFields.toString()));
        }
      }

      Set<Entry<String, JsonElement>> entries = jsonElement.getAsJsonObject().entrySet();
      // check to see if the JSON string contains additional fields
      for (Entry<String, JsonElement> entry : entries) {
        if (!ToolAuthDataParams.openapiFields.contains(entry.getKey())) {
          throw new IllegalArgumentException(String.format("The field `%s` in the JSON string is not defined in the `ToolAuthDataParams` properties. JSON: %s", entry.getKey(), jsonElement.toString()));
        }
      }
        JsonObject jsonObj = jsonElement.getAsJsonObject();
      if ((jsonObj.get("token") != null && !jsonObj.get("token").isJsonNull()) && !jsonObj.get("token").isJsonPrimitive()) {
        throw new IllegalArgumentException(String.format("Expected the field `token` to be a primitive type in the JSON string but got `%s`", jsonObj.get("token").toString()));
      }
      if ((jsonObj.get("client_id") != null && !jsonObj.get("client_id").isJsonNull()) && !jsonObj.get("client_id").isJsonPrimitive()) {
        throw new IllegalArgumentException(String.format("Expected the field `client_id` to be a primitive type in the JSON string but got `%s`", jsonObj.get("client_id").toString()));
      }
      if ((jsonObj.get("client_secret") != null && !jsonObj.get("client_secret").isJsonNull()) && !jsonObj.get("client_secret").isJsonPrimitive()) {
        throw new IllegalArgumentException(String.format("Expected the field `client_secret` to be a primitive type in the JSON string but got `%s`", jsonObj.get("client_secret").toString()));
      }
      if ((jsonObj.get("authorization_url") != null && !jsonObj.get("authorization_url").isJsonNull()) && !jsonObj.get("authorization_url").isJsonPrimitive()) {
        throw new IllegalArgumentException(String.format("Expected the field `authorization_url` to be a primitive type in the JSON string but got `%s`", jsonObj.get("authorization_url").toString()));
      }
      if ((jsonObj.get("token_url") != null && !jsonObj.get("token_url").isJsonNull()) && !jsonObj.get("token_url").isJsonPrimitive()) {
        throw new IllegalArgumentException(String.format("Expected the field `token_url` to be a primitive type in the JSON string but got `%s`", jsonObj.get("token_url").toString()));
      }
      if ((jsonObj.get("registration_url") != null && !jsonObj.get("registration_url").isJsonNull()) && !jsonObj.get("registration_url").isJsonPrimitive()) {
        throw new IllegalArgumentException(String.format("Expected the field `registration_url` to be a primitive type in the JSON string but got `%s`", jsonObj.get("registration_url").toString()));
      }
      // ensure the optional json data is an array if present
      if (jsonObj.get("scopes") != null && !jsonObj.get("scopes").isJsonArray()) {
        throw new IllegalArgumentException(String.format("Expected the field `scopes` to be an array in the JSON string but got `%s`", jsonObj.get("scopes").toString()));
      }
  }

  public static class CustomTypeAdapterFactory implements TypeAdapterFactory {
    @SuppressWarnings("unchecked")
    @Override
    public <T> TypeAdapter<T> create(Gson gson, TypeToken<T> type) {
       if (!ToolAuthDataParams.class.isAssignableFrom(type.getRawType())) {
         return null; // this class only serializes 'ToolAuthDataParams' and its subtypes
       }
       final TypeAdapter<JsonElement> elementAdapter = gson.getAdapter(JsonElement.class);
       final TypeAdapter<ToolAuthDataParams> thisAdapter
                        = gson.getDelegateAdapter(this, TypeToken.get(ToolAuthDataParams.class));

       return (TypeAdapter<T>) new TypeAdapter<ToolAuthDataParams>() {
           @Override
           public void write(JsonWriter out, ToolAuthDataParams value) throws IOException {
             JsonObject obj = thisAdapter.toJsonTree(value).getAsJsonObject();
             elementAdapter.write(out, obj);
           }

           @Override
           public ToolAuthDataParams read(JsonReader in) throws IOException {
             JsonElement jsonElement = elementAdapter.read(in);
             validateJsonElement(jsonElement);
             return thisAdapter.fromJsonTree(jsonElement);
           }

       }.nullSafe();
    }
  }

 /**
  * Create an instance of ToolAuthDataParams given an JSON string
  *
  * @param jsonString JSON string
  * @return An instance of ToolAuthDataParams
  * @throws IOException if the JSON string is invalid with respect to ToolAuthDataParams
  */
  public static ToolAuthDataParams fromJson(String jsonString) throws IOException {
    return JSON.getGson().fromJson(jsonString, ToolAuthDataParams.class);
  }

 /**
  * Convert an instance of ToolAuthDataParams to an JSON string
  *
  * @return JSON string
  */
  public String toJson() {
    return JSON.getGson().toJson(this);
  }
}

