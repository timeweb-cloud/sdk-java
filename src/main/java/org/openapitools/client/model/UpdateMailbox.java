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
import org.openapitools.client.model.AutoReplyIsDisabled;
import org.openapitools.client.model.ForwardingIncomingIsDisabled;
import org.openapitools.client.model.ForwardingOutgoingIsDisabled;
import org.openapitools.client.model.SpamFilterIsDisabled;

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
 * Изменение почтового ящика
 */
@javax.annotation.Generated(value = "org.openapitools.codegen.languages.JavaClientCodegen", date = "2025-05-13T14:01:57.127467Z[Etc/UTC]")
public class UpdateMailbox {
  public static final String SERIALIZED_NAME_AUTO_REPLY = "auto_reply";
  @SerializedName(SERIALIZED_NAME_AUTO_REPLY)
  private AutoReplyIsDisabled autoReply;

  public static final String SERIALIZED_NAME_SPAM_FILTER = "spam_filter";
  @SerializedName(SERIALIZED_NAME_SPAM_FILTER)
  private SpamFilterIsDisabled spamFilter;

  public static final String SERIALIZED_NAME_FORWARDING_INCOMING = "forwarding_incoming";
  @SerializedName(SERIALIZED_NAME_FORWARDING_INCOMING)
  private ForwardingIncomingIsDisabled forwardingIncoming;

  public static final String SERIALIZED_NAME_FORWARDING_OUTGOING = "forwarding_outgoing";
  @SerializedName(SERIALIZED_NAME_FORWARDING_OUTGOING)
  private ForwardingOutgoingIsDisabled forwardingOutgoing;

  public static final String SERIALIZED_NAME_COMMENT = "comment";
  @SerializedName(SERIALIZED_NAME_COMMENT)
  private String comment;

  public static final String SERIALIZED_NAME_PASSWORD = "password";
  @SerializedName(SERIALIZED_NAME_PASSWORD)
  private String password;

  public UpdateMailbox() {
  }

  public UpdateMailbox autoReply(AutoReplyIsDisabled autoReply) {
    
    this.autoReply = autoReply;
    return this;
  }

   /**
   * Get autoReply
   * @return autoReply
  **/
  @javax.annotation.Nullable
  public AutoReplyIsDisabled getAutoReply() {
    return autoReply;
  }


  public void setAutoReply(AutoReplyIsDisabled autoReply) {
    this.autoReply = autoReply;
  }


  public UpdateMailbox spamFilter(SpamFilterIsDisabled spamFilter) {
    
    this.spamFilter = spamFilter;
    return this;
  }

   /**
   * Get spamFilter
   * @return spamFilter
  **/
  @javax.annotation.Nullable
  public SpamFilterIsDisabled getSpamFilter() {
    return spamFilter;
  }


  public void setSpamFilter(SpamFilterIsDisabled spamFilter) {
    this.spamFilter = spamFilter;
  }


  public UpdateMailbox forwardingIncoming(ForwardingIncomingIsDisabled forwardingIncoming) {
    
    this.forwardingIncoming = forwardingIncoming;
    return this;
  }

   /**
   * Get forwardingIncoming
   * @return forwardingIncoming
  **/
  @javax.annotation.Nullable
  public ForwardingIncomingIsDisabled getForwardingIncoming() {
    return forwardingIncoming;
  }


  public void setForwardingIncoming(ForwardingIncomingIsDisabled forwardingIncoming) {
    this.forwardingIncoming = forwardingIncoming;
  }


  public UpdateMailbox forwardingOutgoing(ForwardingOutgoingIsDisabled forwardingOutgoing) {
    
    this.forwardingOutgoing = forwardingOutgoing;
    return this;
  }

   /**
   * Get forwardingOutgoing
   * @return forwardingOutgoing
  **/
  @javax.annotation.Nullable
  public ForwardingOutgoingIsDisabled getForwardingOutgoing() {
    return forwardingOutgoing;
  }


  public void setForwardingOutgoing(ForwardingOutgoingIsDisabled forwardingOutgoing) {
    this.forwardingOutgoing = forwardingOutgoing;
  }


  public UpdateMailbox comment(String comment) {
    
    this.comment = comment;
    return this;
  }

   /**
   * Комментарий к почтовому ящику
   * @return comment
  **/
  @javax.annotation.Nullable
  public String getComment() {
    return comment;
  }


  public void setComment(String comment) {
    this.comment = comment;
  }


  public UpdateMailbox password(String password) {
    
    this.password = password;
    return this;
  }

   /**
   * Пароль почтового ящика
   * @return password
  **/
  @javax.annotation.Nullable
  public String getPassword() {
    return password;
  }


  public void setPassword(String password) {
    this.password = password;
  }



  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    UpdateMailbox updateMailbox = (UpdateMailbox) o;
    return Objects.equals(this.autoReply, updateMailbox.autoReply) &&
        Objects.equals(this.spamFilter, updateMailbox.spamFilter) &&
        Objects.equals(this.forwardingIncoming, updateMailbox.forwardingIncoming) &&
        Objects.equals(this.forwardingOutgoing, updateMailbox.forwardingOutgoing) &&
        Objects.equals(this.comment, updateMailbox.comment) &&
        Objects.equals(this.password, updateMailbox.password);
  }

  @Override
  public int hashCode() {
    return Objects.hash(autoReply, spamFilter, forwardingIncoming, forwardingOutgoing, comment, password);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class UpdateMailbox {\n");
    sb.append("    autoReply: ").append(toIndentedString(autoReply)).append("\n");
    sb.append("    spamFilter: ").append(toIndentedString(spamFilter)).append("\n");
    sb.append("    forwardingIncoming: ").append(toIndentedString(forwardingIncoming)).append("\n");
    sb.append("    forwardingOutgoing: ").append(toIndentedString(forwardingOutgoing)).append("\n");
    sb.append("    comment: ").append(toIndentedString(comment)).append("\n");
    sb.append("    password: ").append(toIndentedString(password)).append("\n");
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
    openapiFields.add("auto_reply");
    openapiFields.add("spam_filter");
    openapiFields.add("forwarding_incoming");
    openapiFields.add("forwarding_outgoing");
    openapiFields.add("comment");
    openapiFields.add("password");

    // a set of required properties/fields (JSON key names)
    openapiRequiredFields = new HashSet<String>();
  }

 /**
  * Validates the JSON Element and throws an exception if issues found
  *
  * @param jsonElement JSON Element
  * @throws IOException if the JSON Element is invalid with respect to UpdateMailbox
  */
  public static void validateJsonElement(JsonElement jsonElement) throws IOException {
      if (jsonElement == null) {
        if (!UpdateMailbox.openapiRequiredFields.isEmpty()) { // has required fields but JSON element is null
          throw new IllegalArgumentException(String.format("The required field(s) %s in UpdateMailbox is not found in the empty JSON string", UpdateMailbox.openapiRequiredFields.toString()));
        }
      }

      Set<Entry<String, JsonElement>> entries = jsonElement.getAsJsonObject().entrySet();
      // check to see if the JSON string contains additional fields
      for (Entry<String, JsonElement> entry : entries) {
        if (!UpdateMailbox.openapiFields.contains(entry.getKey())) {
          throw new IllegalArgumentException(String.format("The field `%s` in the JSON string is not defined in the `UpdateMailbox` properties. JSON: %s", entry.getKey(), jsonElement.toString()));
        }
      }
        JsonObject jsonObj = jsonElement.getAsJsonObject();
      // validate the optional field `auto_reply`
      if (jsonObj.get("auto_reply") != null && !jsonObj.get("auto_reply").isJsonNull()) {
        AutoReplyIsDisabled.validateJsonElement(jsonObj.get("auto_reply"));
      }
      // validate the optional field `spam_filter`
      if (jsonObj.get("spam_filter") != null && !jsonObj.get("spam_filter").isJsonNull()) {
        SpamFilterIsDisabled.validateJsonElement(jsonObj.get("spam_filter"));
      }
      // validate the optional field `forwarding_incoming`
      if (jsonObj.get("forwarding_incoming") != null && !jsonObj.get("forwarding_incoming").isJsonNull()) {
        ForwardingIncomingIsDisabled.validateJsonElement(jsonObj.get("forwarding_incoming"));
      }
      // validate the optional field `forwarding_outgoing`
      if (jsonObj.get("forwarding_outgoing") != null && !jsonObj.get("forwarding_outgoing").isJsonNull()) {
        ForwardingOutgoingIsDisabled.validateJsonElement(jsonObj.get("forwarding_outgoing"));
      }
      if ((jsonObj.get("comment") != null && !jsonObj.get("comment").isJsonNull()) && !jsonObj.get("comment").isJsonPrimitive()) {
        throw new IllegalArgumentException(String.format("Expected the field `comment` to be a primitive type in the JSON string but got `%s`", jsonObj.get("comment").toString()));
      }
      if ((jsonObj.get("password") != null && !jsonObj.get("password").isJsonNull()) && !jsonObj.get("password").isJsonPrimitive()) {
        throw new IllegalArgumentException(String.format("Expected the field `password` to be a primitive type in the JSON string but got `%s`", jsonObj.get("password").toString()));
      }
  }

  public static class CustomTypeAdapterFactory implements TypeAdapterFactory {
    @SuppressWarnings("unchecked")
    @Override
    public <T> TypeAdapter<T> create(Gson gson, TypeToken<T> type) {
       if (!UpdateMailbox.class.isAssignableFrom(type.getRawType())) {
         return null; // this class only serializes 'UpdateMailbox' and its subtypes
       }
       final TypeAdapter<JsonElement> elementAdapter = gson.getAdapter(JsonElement.class);
       final TypeAdapter<UpdateMailbox> thisAdapter
                        = gson.getDelegateAdapter(this, TypeToken.get(UpdateMailbox.class));

       return (TypeAdapter<T>) new TypeAdapter<UpdateMailbox>() {
           @Override
           public void write(JsonWriter out, UpdateMailbox value) throws IOException {
             JsonObject obj = thisAdapter.toJsonTree(value).getAsJsonObject();
             elementAdapter.write(out, obj);
           }

           @Override
           public UpdateMailbox read(JsonReader in) throws IOException {
             JsonElement jsonElement = elementAdapter.read(in);
             validateJsonElement(jsonElement);
             return thisAdapter.fromJsonTree(jsonElement);
           }

       }.nullSafe();
    }
  }

 /**
  * Create an instance of UpdateMailbox given an JSON string
  *
  * @param jsonString JSON string
  * @return An instance of UpdateMailbox
  * @throws IOException if the JSON string is invalid with respect to UpdateMailbox
  */
  public static UpdateMailbox fromJson(String jsonString) throws IOException {
    return JSON.getGson().fromJson(jsonString, UpdateMailbox.class);
  }

 /**
  * Convert an instance of UpdateMailbox to an JSON string
  *
  * @return JSON string
  */
  public String toJson() {
    return JSON.getGson().toJson(this);
  }
}

