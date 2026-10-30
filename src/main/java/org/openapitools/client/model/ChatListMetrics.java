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
 * Сводка по чатам агента с учётом фильтра по периоду (&#x60;from&#x60;/&#x60;to&#x60;)
 */
@javax.annotation.Generated(value = "org.openapitools.codegen.languages.JavaClientCodegen", date = "2026-10-30T15:03:29.616934Z[Etc/UTC]")
public class ChatListMetrics {
  public static final String SERIALIZED_NAME_TOTAL_REACTIONS = "total_reactions";
  @SerializedName(SERIALIZED_NAME_TOTAL_REACTIONS)
  private Integer totalReactions;

  public static final String SERIALIZED_NAME_TOTAL_TIME = "total_time";
  @SerializedName(SERIALIZED_NAME_TOTAL_TIME)
  private Integer totalTime;

  public static final String SERIALIZED_NAME_WITH_LIKES = "with_likes";
  @SerializedName(SERIALIZED_NAME_WITH_LIKES)
  private Integer withLikes;

  public static final String SERIALIZED_NAME_WITH_DISLIKES = "with_dislikes";
  @SerializedName(SERIALIZED_NAME_WITH_DISLIKES)
  private Integer withDislikes;

  public static final String SERIALIZED_NAME_WITH_ANY = "with_any";
  @SerializedName(SERIALIZED_NAME_WITH_ANY)
  private Integer withAny;

  public static final String SERIALIZED_NAME_RESPONSE_TIME_GROUPS_REACTIONS = "response_time_groups_reactions";
  @SerializedName(SERIALIZED_NAME_RESPONSE_TIME_GROUPS_REACTIONS)
  private Map<String, Integer> responseTimeGroupsReactions = new HashMap<>();

  public static final String SERIALIZED_NAME_RESPONSE_TIME_GROUPS_TIME = "response_time_groups_time";
  @SerializedName(SERIALIZED_NAME_RESPONSE_TIME_GROUPS_TIME)
  private Map<String, Integer> responseTimeGroupsTime = new HashMap<>();

  public ChatListMetrics() {
  }

  public ChatListMetrics totalReactions(Integer totalReactions) {
    
    this.totalReactions = totalReactions;
    return this;
  }

   /**
   * Количество чатов с учётом фильтра &#x60;response_time_ranges&#x60; (без учёта &#x60;reaction_type&#x60;)
   * @return totalReactions
  **/
  @javax.annotation.Nonnull
  public Integer getTotalReactions() {
    return totalReactions;
  }


  public void setTotalReactions(Integer totalReactions) {
    this.totalReactions = totalReactions;
  }


  public ChatListMetrics totalTime(Integer totalTime) {
    
    this.totalTime = totalTime;
    return this;
  }

   /**
   * Количество чатов с учётом фильтра &#x60;reaction_type&#x60; (без учёта &#x60;response_time_ranges&#x60;)
   * @return totalTime
  **/
  @javax.annotation.Nonnull
  public Integer getTotalTime() {
    return totalTime;
  }


  public void setTotalTime(Integer totalTime) {
    this.totalTime = totalTime;
  }


  public ChatListMetrics withLikes(Integer withLikes) {
    
    this.withLikes = withLikes;
    return this;
  }

   /**
   * Чатов хотя бы с одной положительной оценкой
   * @return withLikes
  **/
  @javax.annotation.Nonnull
  public Integer getWithLikes() {
    return withLikes;
  }


  public void setWithLikes(Integer withLikes) {
    this.withLikes = withLikes;
  }


  public ChatListMetrics withDislikes(Integer withDislikes) {
    
    this.withDislikes = withDislikes;
    return this;
  }

   /**
   * Чатов хотя бы с одной отрицательной оценкой
   * @return withDislikes
  **/
  @javax.annotation.Nonnull
  public Integer getWithDislikes() {
    return withDislikes;
  }


  public void setWithDislikes(Integer withDislikes) {
    this.withDislikes = withDislikes;
  }


  public ChatListMetrics withAny(Integer withAny) {
    
    this.withAny = withAny;
    return this;
  }

   /**
   * Чатов хотя бы с одной оценкой
   * @return withAny
  **/
  @javax.annotation.Nonnull
  public Integer getWithAny() {
    return withAny;
  }


  public void setWithAny(Integer withAny) {
    this.withAny = withAny;
  }


  public ChatListMetrics responseTimeGroupsReactions(Map<String, Integer> responseTimeGroupsReactions) {
    
    this.responseTimeGroupsReactions = responseTimeGroupsReactions;
    return this;
  }

  public ChatListMetrics putResponseTimeGroupsReactionsItem(String key, Integer responseTimeGroupsReactionsItem) {
    if (this.responseTimeGroupsReactions == null) {
      this.responseTimeGroupsReactions = new HashMap<>();
    }
    this.responseTimeGroupsReactions.put(key, responseTimeGroupsReactionsItem);
    return this;
  }

   /**
   * Количество чатов по среднему времени ответа (в секундах) с учётом фильтра &#x60;response_time_ranges&#x60;
   * @return responseTimeGroupsReactions
  **/
  @javax.annotation.Nonnull
  public Map<String, Integer> getResponseTimeGroupsReactions() {
    return responseTimeGroupsReactions;
  }


  public void setResponseTimeGroupsReactions(Map<String, Integer> responseTimeGroupsReactions) {
    this.responseTimeGroupsReactions = responseTimeGroupsReactions;
  }


  public ChatListMetrics responseTimeGroupsTime(Map<String, Integer> responseTimeGroupsTime) {
    
    this.responseTimeGroupsTime = responseTimeGroupsTime;
    return this;
  }

  public ChatListMetrics putResponseTimeGroupsTimeItem(String key, Integer responseTimeGroupsTimeItem) {
    if (this.responseTimeGroupsTime == null) {
      this.responseTimeGroupsTime = new HashMap<>();
    }
    this.responseTimeGroupsTime.put(key, responseTimeGroupsTimeItem);
    return this;
  }

   /**
   * Количество чатов по среднему времени ответа (в секундах) с учётом фильтра &#x60;reaction_type&#x60;
   * @return responseTimeGroupsTime
  **/
  @javax.annotation.Nonnull
  public Map<String, Integer> getResponseTimeGroupsTime() {
    return responseTimeGroupsTime;
  }


  public void setResponseTimeGroupsTime(Map<String, Integer> responseTimeGroupsTime) {
    this.responseTimeGroupsTime = responseTimeGroupsTime;
  }



  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    ChatListMetrics chatListMetrics = (ChatListMetrics) o;
    return Objects.equals(this.totalReactions, chatListMetrics.totalReactions) &&
        Objects.equals(this.totalTime, chatListMetrics.totalTime) &&
        Objects.equals(this.withLikes, chatListMetrics.withLikes) &&
        Objects.equals(this.withDislikes, chatListMetrics.withDislikes) &&
        Objects.equals(this.withAny, chatListMetrics.withAny) &&
        Objects.equals(this.responseTimeGroupsReactions, chatListMetrics.responseTimeGroupsReactions) &&
        Objects.equals(this.responseTimeGroupsTime, chatListMetrics.responseTimeGroupsTime);
  }

  @Override
  public int hashCode() {
    return Objects.hash(totalReactions, totalTime, withLikes, withDislikes, withAny, responseTimeGroupsReactions, responseTimeGroupsTime);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class ChatListMetrics {\n");
    sb.append("    totalReactions: ").append(toIndentedString(totalReactions)).append("\n");
    sb.append("    totalTime: ").append(toIndentedString(totalTime)).append("\n");
    sb.append("    withLikes: ").append(toIndentedString(withLikes)).append("\n");
    sb.append("    withDislikes: ").append(toIndentedString(withDislikes)).append("\n");
    sb.append("    withAny: ").append(toIndentedString(withAny)).append("\n");
    sb.append("    responseTimeGroupsReactions: ").append(toIndentedString(responseTimeGroupsReactions)).append("\n");
    sb.append("    responseTimeGroupsTime: ").append(toIndentedString(responseTimeGroupsTime)).append("\n");
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
    openapiFields.add("total_reactions");
    openapiFields.add("total_time");
    openapiFields.add("with_likes");
    openapiFields.add("with_dislikes");
    openapiFields.add("with_any");
    openapiFields.add("response_time_groups_reactions");
    openapiFields.add("response_time_groups_time");

    // a set of required properties/fields (JSON key names)
    openapiRequiredFields = new HashSet<String>();
    openapiRequiredFields.add("total_reactions");
    openapiRequiredFields.add("total_time");
    openapiRequiredFields.add("with_likes");
    openapiRequiredFields.add("with_dislikes");
    openapiRequiredFields.add("with_any");
    openapiRequiredFields.add("response_time_groups_reactions");
    openapiRequiredFields.add("response_time_groups_time");
  }

 /**
  * Validates the JSON Element and throws an exception if issues found
  *
  * @param jsonElement JSON Element
  * @throws IOException if the JSON Element is invalid with respect to ChatListMetrics
  */
  public static void validateJsonElement(JsonElement jsonElement) throws IOException {
      if (jsonElement == null) {
        if (!ChatListMetrics.openapiRequiredFields.isEmpty()) { // has required fields but JSON element is null
          throw new IllegalArgumentException(String.format("The required field(s) %s in ChatListMetrics is not found in the empty JSON string", ChatListMetrics.openapiRequiredFields.toString()));
        }
      }

      Set<Entry<String, JsonElement>> entries = jsonElement.getAsJsonObject().entrySet();
      // check to see if the JSON string contains additional fields
      for (Entry<String, JsonElement> entry : entries) {
        if (!ChatListMetrics.openapiFields.contains(entry.getKey())) {
          throw new IllegalArgumentException(String.format("The field `%s` in the JSON string is not defined in the `ChatListMetrics` properties. JSON: %s", entry.getKey(), jsonElement.toString()));
        }
      }

      // check to make sure all required properties/fields are present in the JSON string
      for (String requiredField : ChatListMetrics.openapiRequiredFields) {
        if (jsonElement.getAsJsonObject().get(requiredField) == null) {
          throw new IllegalArgumentException(String.format("The required field `%s` is not found in the JSON string: %s", requiredField, jsonElement.toString()));
        }
      }
        JsonObject jsonObj = jsonElement.getAsJsonObject();
  }

  public static class CustomTypeAdapterFactory implements TypeAdapterFactory {
    @SuppressWarnings("unchecked")
    @Override
    public <T> TypeAdapter<T> create(Gson gson, TypeToken<T> type) {
       if (!ChatListMetrics.class.isAssignableFrom(type.getRawType())) {
         return null; // this class only serializes 'ChatListMetrics' and its subtypes
       }
       final TypeAdapter<JsonElement> elementAdapter = gson.getAdapter(JsonElement.class);
       final TypeAdapter<ChatListMetrics> thisAdapter
                        = gson.getDelegateAdapter(this, TypeToken.get(ChatListMetrics.class));

       return (TypeAdapter<T>) new TypeAdapter<ChatListMetrics>() {
           @Override
           public void write(JsonWriter out, ChatListMetrics value) throws IOException {
             JsonObject obj = thisAdapter.toJsonTree(value).getAsJsonObject();
             elementAdapter.write(out, obj);
           }

           @Override
           public ChatListMetrics read(JsonReader in) throws IOException {
             JsonElement jsonElement = elementAdapter.read(in);
             validateJsonElement(jsonElement);
             return thisAdapter.fromJsonTree(jsonElement);
           }

       }.nullSafe();
    }
  }

 /**
  * Create an instance of ChatListMetrics given an JSON string
  *
  * @param jsonString JSON string
  * @return An instance of ChatListMetrics
  * @throws IOException if the JSON string is invalid with respect to ChatListMetrics
  */
  public static ChatListMetrics fromJson(String jsonString) throws IOException {
    return JSON.getGson().fromJson(jsonString, ChatListMetrics.class);
  }

 /**
  * Convert an instance of ChatListMetrics to an JSON string
  *
  * @return JSON string
  */
  public String toJson() {
    return JSON.getGson().toJson(this);
  }
}

