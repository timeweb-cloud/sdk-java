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
 * Навык
 */
@javax.annotation.Generated(value = "org.openapitools.codegen.languages.JavaClientCodegen", date = "2026-10-28T15:56:17.696691Z[Etc/UTC]")
public class Skill {
  public static final String SERIALIZED_NAME_ID = "id";
  @SerializedName(SERIALIZED_NAME_ID)
  private Integer id;

  public static final String SERIALIZED_NAME_NAME = "name";
  @SerializedName(SERIALIZED_NAME_NAME)
  private String name;

  public static final String SERIALIZED_NAME_WHEN_TO_USE = "when_to_use";
  @SerializedName(SERIALIZED_NAME_WHEN_TO_USE)
  private String whenToUse;

  public static final String SERIALIZED_NAME_INSTRUCTION = "instruction";
  @SerializedName(SERIALIZED_NAME_INSTRUCTION)
  private String instruction;

  public static final String SERIALIZED_NAME_CURRENT_VERSION = "current_version";
  @SerializedName(SERIALIZED_NAME_CURRENT_VERSION)
  private Integer currentVersion;

  public static final String SERIALIZED_NAME_AGENTS_COUNT = "agents_count";
  @SerializedName(SERIALIZED_NAME_AGENTS_COUNT)
  private Integer agentsCount;

  public static final String SERIALIZED_NAME_FIRST_AGENT_NAME = "first_agent_name";
  @SerializedName(SERIALIZED_NAME_FIRST_AGENT_NAME)
  private String firstAgentName;

  public static final String SERIALIZED_NAME_CATALOG_ITEM_ID = "catalog_item_id";
  @SerializedName(SERIALIZED_NAME_CATALOG_ITEM_ID)
  private Integer catalogItemId;

  public static final String SERIALIZED_NAME_CATALOG_VERSION = "catalog_version";
  @SerializedName(SERIALIZED_NAME_CATALOG_VERSION)
  private Integer catalogVersion;

  public static final String SERIALIZED_NAME_IS_FROM_CATALOG = "is_from_catalog";
  @SerializedName(SERIALIZED_NAME_IS_FROM_CATALOG)
  private Boolean isFromCatalog;

  public static final String SERIALIZED_NAME_IS_MODIFIED = "is_modified";
  @SerializedName(SERIALIZED_NAME_IS_MODIFIED)
  private Boolean isModified;

  public static final String SERIALIZED_NAME_CREATED_AT = "created_at";
  @SerializedName(SERIALIZED_NAME_CREATED_AT)
  private OffsetDateTime createdAt;

  public static final String SERIALIZED_NAME_UPDATED_AT = "updated_at";
  @SerializedName(SERIALIZED_NAME_UPDATED_AT)
  private OffsetDateTime updatedAt;

  public Skill() {
  }

  public Skill id(Integer id) {
    
    this.id = id;
    return this;
  }

   /**
   * ID навыка
   * @return id
  **/
  @javax.annotation.Nonnull
  public Integer getId() {
    return id;
  }


  public void setId(Integer id) {
    this.id = id;
  }


  public Skill name(String name) {
    
    this.name = name;
    return this;
  }

   /**
   * Название навыка, уникальное в аккаунте
   * @return name
  **/
  @javax.annotation.Nonnull
  public String getName() {
    return name;
  }


  public void setName(String name) {
    this.name = name;
  }


  public Skill whenToUse(String whenToUse) {
    
    this.whenToUse = whenToUse;
    return this;
  }

   /**
   * Когда применять навык. Это поле читает модель, когда выбирает навык
   * @return whenToUse
  **/
  @javax.annotation.Nonnull
  public String getWhenToUse() {
    return whenToUse;
  }


  public void setWhenToUse(String whenToUse) {
    this.whenToUse = whenToUse;
  }


  public Skill instruction(String instruction) {
    
    this.instruction = instruction;
    return this;
  }

   /**
   * Инструкция навыка. Попадает в контекст модели только при срабатывании
   * @return instruction
  **/
  @javax.annotation.Nonnull
  public String getInstruction() {
    return instruction;
  }


  public void setInstruction(String instruction) {
    this.instruction = instruction;
  }


  public Skill currentVersion(Integer currentVersion) {
    
    this.currentVersion = currentVersion;
    return this;
  }

   /**
   * Номер текущей версии
   * @return currentVersion
  **/
  @javax.annotation.Nonnull
  public Integer getCurrentVersion() {
    return currentVersion;
  }


  public void setCurrentVersion(Integer currentVersion) {
    this.currentVersion = currentVersion;
  }


  public Skill agentsCount(Integer agentsCount) {
    
    this.agentsCount = agentsCount;
    return this;
  }

   /**
   * У скольких агентов навык включён
   * @return agentsCount
  **/
  @javax.annotation.Nonnull
  public Integer getAgentsCount() {
    return agentsCount;
  }


  public void setAgentsCount(Integer agentsCount) {
    this.agentsCount = agentsCount;
  }


  public Skill firstAgentName(String firstAgentName) {
    
    this.firstAgentName = firstAgentName;
    return this;
  }

   /**
   * Имя агента, если навык включён ровно у одного агента
   * @return firstAgentName
  **/
  @javax.annotation.Nullable
  public String getFirstAgentName() {
    return firstAgentName;
  }


  public void setFirstAgentName(String firstAgentName) {
    this.firstAgentName = firstAgentName;
  }


  public Skill catalogItemId(Integer catalogItemId) {
    
    this.catalogItemId = catalogItemId;
    return this;
  }

   /**
   * ID навыка в каталоге, из которого установлен этот навык
   * @return catalogItemId
  **/
  @javax.annotation.Nullable
  public Integer getCatalogItemId() {
    return catalogItemId;
  }


  public void setCatalogItemId(Integer catalogItemId) {
    this.catalogItemId = catalogItemId;
  }


  public Skill catalogVersion(Integer catalogVersion) {
    
    this.catalogVersion = catalogVersion;
    return this;
  }

   /**
   * Версия каталожного навыка, из которой установлен или обновлён этот навык
   * @return catalogVersion
  **/
  @javax.annotation.Nullable
  public Integer getCatalogVersion() {
    return catalogVersion;
  }


  public void setCatalogVersion(Integer catalogVersion) {
    this.catalogVersion = catalogVersion;
  }


  public Skill isFromCatalog(Boolean isFromCatalog) {
    
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


  public Skill isModified(Boolean isModified) {
    
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


  public Skill createdAt(OffsetDateTime createdAt) {
    
    this.createdAt = createdAt;
    return this;
  }

   /**
   * Дата и время создания
   * @return createdAt
  **/
  @javax.annotation.Nonnull
  public OffsetDateTime getCreatedAt() {
    return createdAt;
  }


  public void setCreatedAt(OffsetDateTime createdAt) {
    this.createdAt = createdAt;
  }


  public Skill updatedAt(OffsetDateTime updatedAt) {
    
    this.updatedAt = updatedAt;
    return this;
  }

   /**
   * Дата и время последнего изменения
   * @return updatedAt
  **/
  @javax.annotation.Nonnull
  public OffsetDateTime getUpdatedAt() {
    return updatedAt;
  }


  public void setUpdatedAt(OffsetDateTime updatedAt) {
    this.updatedAt = updatedAt;
  }



  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Skill skill = (Skill) o;
    return Objects.equals(this.id, skill.id) &&
        Objects.equals(this.name, skill.name) &&
        Objects.equals(this.whenToUse, skill.whenToUse) &&
        Objects.equals(this.instruction, skill.instruction) &&
        Objects.equals(this.currentVersion, skill.currentVersion) &&
        Objects.equals(this.agentsCount, skill.agentsCount) &&
        Objects.equals(this.firstAgentName, skill.firstAgentName) &&
        Objects.equals(this.catalogItemId, skill.catalogItemId) &&
        Objects.equals(this.catalogVersion, skill.catalogVersion) &&
        Objects.equals(this.isFromCatalog, skill.isFromCatalog) &&
        Objects.equals(this.isModified, skill.isModified) &&
        Objects.equals(this.createdAt, skill.createdAt) &&
        Objects.equals(this.updatedAt, skill.updatedAt);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, name, whenToUse, instruction, currentVersion, agentsCount, firstAgentName, catalogItemId, catalogVersion, isFromCatalog, isModified, createdAt, updatedAt);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class Skill {\n");
    sb.append("    id: ").append(toIndentedString(id)).append("\n");
    sb.append("    name: ").append(toIndentedString(name)).append("\n");
    sb.append("    whenToUse: ").append(toIndentedString(whenToUse)).append("\n");
    sb.append("    instruction: ").append(toIndentedString(instruction)).append("\n");
    sb.append("    currentVersion: ").append(toIndentedString(currentVersion)).append("\n");
    sb.append("    agentsCount: ").append(toIndentedString(agentsCount)).append("\n");
    sb.append("    firstAgentName: ").append(toIndentedString(firstAgentName)).append("\n");
    sb.append("    catalogItemId: ").append(toIndentedString(catalogItemId)).append("\n");
    sb.append("    catalogVersion: ").append(toIndentedString(catalogVersion)).append("\n");
    sb.append("    isFromCatalog: ").append(toIndentedString(isFromCatalog)).append("\n");
    sb.append("    isModified: ").append(toIndentedString(isModified)).append("\n");
    sb.append("    createdAt: ").append(toIndentedString(createdAt)).append("\n");
    sb.append("    updatedAt: ").append(toIndentedString(updatedAt)).append("\n");
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
    openapiFields.add("name");
    openapiFields.add("when_to_use");
    openapiFields.add("instruction");
    openapiFields.add("current_version");
    openapiFields.add("agents_count");
    openapiFields.add("first_agent_name");
    openapiFields.add("catalog_item_id");
    openapiFields.add("catalog_version");
    openapiFields.add("is_from_catalog");
    openapiFields.add("is_modified");
    openapiFields.add("created_at");
    openapiFields.add("updated_at");

    // a set of required properties/fields (JSON key names)
    openapiRequiredFields = new HashSet<String>();
    openapiRequiredFields.add("id");
    openapiRequiredFields.add("name");
    openapiRequiredFields.add("when_to_use");
    openapiRequiredFields.add("instruction");
    openapiRequiredFields.add("current_version");
    openapiRequiredFields.add("agents_count");
    openapiRequiredFields.add("first_agent_name");
    openapiRequiredFields.add("catalog_item_id");
    openapiRequiredFields.add("catalog_version");
    openapiRequiredFields.add("is_from_catalog");
    openapiRequiredFields.add("is_modified");
    openapiRequiredFields.add("created_at");
    openapiRequiredFields.add("updated_at");
  }

 /**
  * Validates the JSON Element and throws an exception if issues found
  *
  * @param jsonElement JSON Element
  * @throws IOException if the JSON Element is invalid with respect to Skill
  */
  public static void validateJsonElement(JsonElement jsonElement) throws IOException {
      if (jsonElement == null) {
        if (!Skill.openapiRequiredFields.isEmpty()) { // has required fields but JSON element is null
          throw new IllegalArgumentException(String.format("The required field(s) %s in Skill is not found in the empty JSON string", Skill.openapiRequiredFields.toString()));
        }
      }

      Set<Entry<String, JsonElement>> entries = jsonElement.getAsJsonObject().entrySet();
      // check to see if the JSON string contains additional fields
      for (Entry<String, JsonElement> entry : entries) {
        if (!Skill.openapiFields.contains(entry.getKey())) {
          throw new IllegalArgumentException(String.format("The field `%s` in the JSON string is not defined in the `Skill` properties. JSON: %s", entry.getKey(), jsonElement.toString()));
        }
      }

      // check to make sure all required properties/fields are present in the JSON string
      for (String requiredField : Skill.openapiRequiredFields) {
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
      if (!jsonObj.get("instruction").isJsonPrimitive()) {
        throw new IllegalArgumentException(String.format("Expected the field `instruction` to be a primitive type in the JSON string but got `%s`", jsonObj.get("instruction").toString()));
      }
      if ((jsonObj.get("first_agent_name") != null && !jsonObj.get("first_agent_name").isJsonNull()) && !jsonObj.get("first_agent_name").isJsonPrimitive()) {
        throw new IllegalArgumentException(String.format("Expected the field `first_agent_name` to be a primitive type in the JSON string but got `%s`", jsonObj.get("first_agent_name").toString()));
      }
  }

  public static class CustomTypeAdapterFactory implements TypeAdapterFactory {
    @SuppressWarnings("unchecked")
    @Override
    public <T> TypeAdapter<T> create(Gson gson, TypeToken<T> type) {
       if (!Skill.class.isAssignableFrom(type.getRawType())) {
         return null; // this class only serializes 'Skill' and its subtypes
       }
       final TypeAdapter<JsonElement> elementAdapter = gson.getAdapter(JsonElement.class);
       final TypeAdapter<Skill> thisAdapter
                        = gson.getDelegateAdapter(this, TypeToken.get(Skill.class));

       return (TypeAdapter<T>) new TypeAdapter<Skill>() {
           @Override
           public void write(JsonWriter out, Skill value) throws IOException {
             JsonObject obj = thisAdapter.toJsonTree(value).getAsJsonObject();
             elementAdapter.write(out, obj);
           }

           @Override
           public Skill read(JsonReader in) throws IOException {
             JsonElement jsonElement = elementAdapter.read(in);
             validateJsonElement(jsonElement);
             return thisAdapter.fromJsonTree(jsonElement);
           }

       }.nullSafe();
    }
  }

 /**
  * Create an instance of Skill given an JSON string
  *
  * @param jsonString JSON string
  * @return An instance of Skill
  * @throws IOException if the JSON string is invalid with respect to Skill
  */
  public static Skill fromJson(String jsonString) throws IOException {
    return JSON.getGson().fromJson(jsonString, Skill.class);
  }

 /**
  * Convert an instance of Skill to an JSON string
  *
  * @return JSON string
  */
  public String toJson() {
    return JSON.getGson().toJson(this);
  }
}

