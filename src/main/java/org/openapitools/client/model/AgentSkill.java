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
 * Навык аккаунта и его состояние у агента
 */
@javax.annotation.Generated(value = "org.openapitools.codegen.languages.JavaClientCodegen", date = "2026-10-28T15:56:17.696691Z[Etc/UTC]")
public class AgentSkill {
  public static final String SERIALIZED_NAME_SKILL_ID = "skill_id";
  @SerializedName(SERIALIZED_NAME_SKILL_ID)
  private Integer skillId;

  public static final String SERIALIZED_NAME_NAME = "name";
  @SerializedName(SERIALIZED_NAME_NAME)
  private String name;

  public static final String SERIALIZED_NAME_WHEN_TO_USE = "when_to_use";
  @SerializedName(SERIALIZED_NAME_WHEN_TO_USE)
  private String whenToUse;

  public static final String SERIALIZED_NAME_CURRENT_VERSION = "current_version";
  @SerializedName(SERIALIZED_NAME_CURRENT_VERSION)
  private Integer currentVersion;

  public static final String SERIALIZED_NAME_IS_CONNECTED = "is_connected";
  @SerializedName(SERIALIZED_NAME_IS_CONNECTED)
  private Boolean isConnected;

  public static final String SERIALIZED_NAME_IS_ENABLED = "is_enabled";
  @SerializedName(SERIALIZED_NAME_IS_ENABLED)
  private Boolean isEnabled;

  public static final String SERIALIZED_NAME_PINNED_VERSION = "pinned_version";
  @SerializedName(SERIALIZED_NAME_PINNED_VERSION)
  private Integer pinnedVersion;

  public static final String SERIALIZED_NAME_HAS_NEWER_VERSION = "has_newer_version";
  @SerializedName(SERIALIZED_NAME_HAS_NEWER_VERSION)
  private Boolean hasNewerVersion;

  public static final String SERIALIZED_NAME_IS_FROM_CATALOG = "is_from_catalog";
  @SerializedName(SERIALIZED_NAME_IS_FROM_CATALOG)
  private Boolean isFromCatalog;

  public static final String SERIALIZED_NAME_IS_MODIFIED = "is_modified";
  @SerializedName(SERIALIZED_NAME_IS_MODIFIED)
  private Boolean isModified;

  public AgentSkill() {
  }

  public AgentSkill skillId(Integer skillId) {
    
    this.skillId = skillId;
    return this;
  }

   /**
   * ID навыка
   * @return skillId
  **/
  @javax.annotation.Nonnull
  public Integer getSkillId() {
    return skillId;
  }


  public void setSkillId(Integer skillId) {
    this.skillId = skillId;
  }


  public AgentSkill name(String name) {
    
    this.name = name;
    return this;
  }

   /**
   * Название навыка
   * @return name
  **/
  @javax.annotation.Nonnull
  public String getName() {
    return name;
  }


  public void setName(String name) {
    this.name = name;
  }


  public AgentSkill whenToUse(String whenToUse) {
    
    this.whenToUse = whenToUse;
    return this;
  }

   /**
   * Когда применять навык
   * @return whenToUse
  **/
  @javax.annotation.Nonnull
  public String getWhenToUse() {
    return whenToUse;
  }


  public void setWhenToUse(String whenToUse) {
    this.whenToUse = whenToUse;
  }


  public AgentSkill currentVersion(Integer currentVersion) {
    
    this.currentVersion = currentVersion;
    return this;
  }

   /**
   * Номер текущей версии навыка
   * @return currentVersion
  **/
  @javax.annotation.Nonnull
  public Integer getCurrentVersion() {
    return currentVersion;
  }


  public void setCurrentVersion(Integer currentVersion) {
    this.currentVersion = currentVersion;
  }


  public AgentSkill isConnected(Boolean isConnected) {
    
    this.isConnected = isConnected;
    return this;
  }

   /**
   * Навык подключён к агенту
   * @return isConnected
  **/
  @javax.annotation.Nonnull
  public Boolean getIsConnected() {
    return isConnected;
  }


  public void setIsConnected(Boolean isConnected) {
    this.isConnected = isConnected;
  }


  public AgentSkill isEnabled(Boolean isEnabled) {
    
    this.isEnabled = isEnabled;
    return this;
  }

   /**
   * Навык включён у агента
   * @return isEnabled
  **/
  @javax.annotation.Nonnull
  public Boolean getIsEnabled() {
    return isEnabled;
  }


  public void setIsEnabled(Boolean isEnabled) {
    this.isEnabled = isEnabled;
  }


  public AgentSkill pinnedVersion(Integer pinnedVersion) {
    
    this.pinnedVersion = pinnedVersion;
    return this;
  }

   /**
   * Версия, на которой закреплён агент. &#x60;null&#x60; — навык не подключён
   * @return pinnedVersion
  **/
  @javax.annotation.Nullable
  public Integer getPinnedVersion() {
    return pinnedVersion;
  }


  public void setPinnedVersion(Integer pinnedVersion) {
    this.pinnedVersion = pinnedVersion;
  }


  public AgentSkill hasNewerVersion(Boolean hasNewerVersion) {
    
    this.hasNewerVersion = hasNewerVersion;
    return this;
  }

   /**
   * Текущая версия навыка отличается от закреплённой
   * @return hasNewerVersion
  **/
  @javax.annotation.Nonnull
  public Boolean getHasNewerVersion() {
    return hasNewerVersion;
  }


  public void setHasNewerVersion(Boolean hasNewerVersion) {
    this.hasNewerVersion = hasNewerVersion;
  }


  public AgentSkill isFromCatalog(Boolean isFromCatalog) {
    
    this.isFromCatalog = isFromCatalog;
    return this;
  }

   /**
   * Навык установлен из каталога
   * @return isFromCatalog
  **/
  @javax.annotation.Nonnull
  public Boolean getIsFromCatalog() {
    return isFromCatalog;
  }


  public void setIsFromCatalog(Boolean isFromCatalog) {
    this.isFromCatalog = isFromCatalog;
  }


  public AgentSkill isModified(Boolean isModified) {
    
    this.isModified = isModified;
    return this;
  }

   /**
   * Установленный из каталога навык изменялся владельцем
   * @return isModified
  **/
  @javax.annotation.Nonnull
  public Boolean getIsModified() {
    return isModified;
  }


  public void setIsModified(Boolean isModified) {
    this.isModified = isModified;
  }



  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    AgentSkill agentSkill = (AgentSkill) o;
    return Objects.equals(this.skillId, agentSkill.skillId) &&
        Objects.equals(this.name, agentSkill.name) &&
        Objects.equals(this.whenToUse, agentSkill.whenToUse) &&
        Objects.equals(this.currentVersion, agentSkill.currentVersion) &&
        Objects.equals(this.isConnected, agentSkill.isConnected) &&
        Objects.equals(this.isEnabled, agentSkill.isEnabled) &&
        Objects.equals(this.pinnedVersion, agentSkill.pinnedVersion) &&
        Objects.equals(this.hasNewerVersion, agentSkill.hasNewerVersion) &&
        Objects.equals(this.isFromCatalog, agentSkill.isFromCatalog) &&
        Objects.equals(this.isModified, agentSkill.isModified);
  }

  @Override
  public int hashCode() {
    return Objects.hash(skillId, name, whenToUse, currentVersion, isConnected, isEnabled, pinnedVersion, hasNewerVersion, isFromCatalog, isModified);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class AgentSkill {\n");
    sb.append("    skillId: ").append(toIndentedString(skillId)).append("\n");
    sb.append("    name: ").append(toIndentedString(name)).append("\n");
    sb.append("    whenToUse: ").append(toIndentedString(whenToUse)).append("\n");
    sb.append("    currentVersion: ").append(toIndentedString(currentVersion)).append("\n");
    sb.append("    isConnected: ").append(toIndentedString(isConnected)).append("\n");
    sb.append("    isEnabled: ").append(toIndentedString(isEnabled)).append("\n");
    sb.append("    pinnedVersion: ").append(toIndentedString(pinnedVersion)).append("\n");
    sb.append("    hasNewerVersion: ").append(toIndentedString(hasNewerVersion)).append("\n");
    sb.append("    isFromCatalog: ").append(toIndentedString(isFromCatalog)).append("\n");
    sb.append("    isModified: ").append(toIndentedString(isModified)).append("\n");
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
    openapiFields.add("skill_id");
    openapiFields.add("name");
    openapiFields.add("when_to_use");
    openapiFields.add("current_version");
    openapiFields.add("is_connected");
    openapiFields.add("is_enabled");
    openapiFields.add("pinned_version");
    openapiFields.add("has_newer_version");
    openapiFields.add("is_from_catalog");
    openapiFields.add("is_modified");

    // a set of required properties/fields (JSON key names)
    openapiRequiredFields = new HashSet<String>();
    openapiRequiredFields.add("skill_id");
    openapiRequiredFields.add("name");
    openapiRequiredFields.add("when_to_use");
    openapiRequiredFields.add("current_version");
    openapiRequiredFields.add("is_connected");
    openapiRequiredFields.add("is_enabled");
    openapiRequiredFields.add("pinned_version");
    openapiRequiredFields.add("has_newer_version");
    openapiRequiredFields.add("is_from_catalog");
    openapiRequiredFields.add("is_modified");
  }

 /**
  * Validates the JSON Element and throws an exception if issues found
  *
  * @param jsonElement JSON Element
  * @throws IOException if the JSON Element is invalid with respect to AgentSkill
  */
  public static void validateJsonElement(JsonElement jsonElement) throws IOException {
      if (jsonElement == null) {
        if (!AgentSkill.openapiRequiredFields.isEmpty()) { // has required fields but JSON element is null
          throw new IllegalArgumentException(String.format("The required field(s) %s in AgentSkill is not found in the empty JSON string", AgentSkill.openapiRequiredFields.toString()));
        }
      }

      Set<Entry<String, JsonElement>> entries = jsonElement.getAsJsonObject().entrySet();
      // check to see if the JSON string contains additional fields
      for (Entry<String, JsonElement> entry : entries) {
        if (!AgentSkill.openapiFields.contains(entry.getKey())) {
          throw new IllegalArgumentException(String.format("The field `%s` in the JSON string is not defined in the `AgentSkill` properties. JSON: %s", entry.getKey(), jsonElement.toString()));
        }
      }

      // check to make sure all required properties/fields are present in the JSON string
      for (String requiredField : AgentSkill.openapiRequiredFields) {
        if (jsonElement.getAsJsonObject().get(requiredField) == null) {
          throw new IllegalArgumentException(String.format("The required field `%s` is not found in the JSON string: %s", requiredField, jsonElement.toString()));
        }
      }
        JsonObject jsonObj = jsonElement.getAsJsonObject();
      if (!jsonObj.get("name").isJsonPrimitive()) {
        throw new IllegalArgumentException(String.format("Expected the field `name` to be a primitive type in the JSON string but got `%s`", jsonObj.get("name").toString()));
      }
      if (!jsonObj.get("when_to_use").isJsonPrimitive()) {
        throw new IllegalArgumentException(String.format("Expected the field `when_to_use` to be a primitive type in the JSON string but got `%s`", jsonObj.get("when_to_use").toString()));
      }
  }

  public static class CustomTypeAdapterFactory implements TypeAdapterFactory {
    @SuppressWarnings("unchecked")
    @Override
    public <T> TypeAdapter<T> create(Gson gson, TypeToken<T> type) {
       if (!AgentSkill.class.isAssignableFrom(type.getRawType())) {
         return null; // this class only serializes 'AgentSkill' and its subtypes
       }
       final TypeAdapter<JsonElement> elementAdapter = gson.getAdapter(JsonElement.class);
       final TypeAdapter<AgentSkill> thisAdapter
                        = gson.getDelegateAdapter(this, TypeToken.get(AgentSkill.class));

       return (TypeAdapter<T>) new TypeAdapter<AgentSkill>() {
           @Override
           public void write(JsonWriter out, AgentSkill value) throws IOException {
             JsonObject obj = thisAdapter.toJsonTree(value).getAsJsonObject();
             elementAdapter.write(out, obj);
           }

           @Override
           public AgentSkill read(JsonReader in) throws IOException {
             JsonElement jsonElement = elementAdapter.read(in);
             validateJsonElement(jsonElement);
             return thisAdapter.fromJsonTree(jsonElement);
           }

       }.nullSafe();
    }
  }

 /**
  * Create an instance of AgentSkill given an JSON string
  *
  * @param jsonString JSON string
  * @return An instance of AgentSkill
  * @throws IOException if the JSON string is invalid with respect to AgentSkill
  */
  public static AgentSkill fromJson(String jsonString) throws IOException {
    return JSON.getGson().fromJson(jsonString, AgentSkill.class);
  }

 /**
  * Convert an instance of AgentSkill to an JSON string
  *
  * @return JSON string
  */
  public String toJson() {
    return JSON.getGson().toJson(this);
  }
}

