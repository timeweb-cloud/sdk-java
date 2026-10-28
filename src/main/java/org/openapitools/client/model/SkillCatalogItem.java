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
 * Навык из каталога Timeweb Cloud
 */
@javax.annotation.Generated(value = "org.openapitools.codegen.languages.JavaClientCodegen", date = "2026-10-28T15:56:17.696691Z[Etc/UTC]")
public class SkillCatalogItem {
  public static final String SERIALIZED_NAME_ID = "id";
  @SerializedName(SERIALIZED_NAME_ID)
  private Integer id;

  public static final String SERIALIZED_NAME_KEY = "key";
  @SerializedName(SERIALIZED_NAME_KEY)
  private String key;

  /**
   * Назначение: &#x60;communication&#x60; — манера общения, &#x60;support&#x60; — регламенты обслуживания, &#x60;sales&#x60; — продажи, &#x60;content&#x60; — документы и база знаний, &#x60;safety&#x60; — ограничения и безопасность
   */
  @JsonAdapter(CategoryEnum.Adapter.class)
  public enum CategoryEnum {
    COMMUNICATION("communication"),
    
    SUPPORT("support"),
    
    SALES("sales"),
    
    CONTENT("content"),
    
    SAFETY("safety");

    private String value;

    CategoryEnum(String value) {
      this.value = value;
    }

    public String getValue() {
      return value;
    }

    @Override
    public String toString() {
      return String.valueOf(value);
    }

    public static CategoryEnum fromValue(String value) {
      for (CategoryEnum b : CategoryEnum.values()) {
        if (b.value.equals(value)) {
          return b;
        }
      }
      throw new IllegalArgumentException("Unexpected value '" + value + "'");
    }

    public static class Adapter extends TypeAdapter<CategoryEnum> {
      @Override
      public void write(final JsonWriter jsonWriter, final CategoryEnum enumeration) throws IOException {
        jsonWriter.value(enumeration.getValue());
      }

      @Override
      public CategoryEnum read(final JsonReader jsonReader) throws IOException {
        String value =  jsonReader.nextString();
        return CategoryEnum.fromValue(value);
      }
    }
  }

  public static final String SERIALIZED_NAME_CATEGORY = "category";
  @SerializedName(SERIALIZED_NAME_CATEGORY)
  private CategoryEnum category;

  public static final String SERIALIZED_NAME_NAME = "name";
  @SerializedName(SERIALIZED_NAME_NAME)
  private String name;

  public static final String SERIALIZED_NAME_DESCRIPTION = "description";
  @SerializedName(SERIALIZED_NAME_DESCRIPTION)
  private String description;

  public static final String SERIALIZED_NAME_WHEN_TO_USE = "when_to_use";
  @SerializedName(SERIALIZED_NAME_WHEN_TO_USE)
  private String whenToUse;

  public static final String SERIALIZED_NAME_INSTRUCTION = "instruction";
  @SerializedName(SERIALIZED_NAME_INSTRUCTION)
  private String instruction;

  public static final String SERIALIZED_NAME_VERSION = "version";
  @SerializedName(SERIALIZED_NAME_VERSION)
  private Integer version;

  public static final String SERIALIZED_NAME_IS_INSTALLED = "is_installed";
  @SerializedName(SERIALIZED_NAME_IS_INSTALLED)
  private Boolean isInstalled;

  public static final String SERIALIZED_NAME_INSTALLED_SKILL_ID = "installed_skill_id";
  @SerializedName(SERIALIZED_NAME_INSTALLED_SKILL_ID)
  private Integer installedSkillId;

  public static final String SERIALIZED_NAME_IS_MODIFIED = "is_modified";
  @SerializedName(SERIALIZED_NAME_IS_MODIFIED)
  private Boolean isModified;

  public static final String SERIALIZED_NAME_HAS_CATALOG_UPDATE = "has_catalog_update";
  @SerializedName(SERIALIZED_NAME_HAS_CATALOG_UPDATE)
  private Boolean hasCatalogUpdate;

  public SkillCatalogItem() {
  }

  public SkillCatalogItem id(Integer id) {
    
    this.id = id;
    return this;
  }

   /**
   * ID навыка в каталоге
   * @return id
  **/
  @javax.annotation.Nonnull
  public Integer getId() {
    return id;
  }


  public void setId(Integer id) {
    this.id = id;
  }


  public SkillCatalogItem key(String key) {
    
    this.key = key;
    return this;
  }

   /**
   * Уникальный ключ навыка в каталоге
   * @return key
  **/
  @javax.annotation.Nonnull
  public String getKey() {
    return key;
  }


  public void setKey(String key) {
    this.key = key;
  }


  public SkillCatalogItem category(CategoryEnum category) {
    
    this.category = category;
    return this;
  }

   /**
   * Назначение: &#x60;communication&#x60; — манера общения, &#x60;support&#x60; — регламенты обслуживания, &#x60;sales&#x60; — продажи, &#x60;content&#x60; — документы и база знаний, &#x60;safety&#x60; — ограничения и безопасность
   * @return category
  **/
  @javax.annotation.Nonnull
  public CategoryEnum getCategory() {
    return category;
  }


  public void setCategory(CategoryEnum category) {
    this.category = category;
  }


  public SkillCatalogItem name(String name) {
    
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


  public SkillCatalogItem description(String description) {
    
    this.description = description;
    return this;
  }

   /**
   * Краткое описание навыка
   * @return description
  **/
  @javax.annotation.Nullable
  public String getDescription() {
    return description;
  }


  public void setDescription(String description) {
    this.description = description;
  }


  public SkillCatalogItem whenToUse(String whenToUse) {
    
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


  public SkillCatalogItem instruction(String instruction) {
    
    this.instruction = instruction;
    return this;
  }

   /**
   * Полная инструкция навыка
   * @return instruction
  **/
  @javax.annotation.Nonnull
  public String getInstruction() {
    return instruction;
  }


  public void setInstruction(String instruction) {
    this.instruction = instruction;
  }


  public SkillCatalogItem version(Integer version) {
    
    this.version = version;
    return this;
  }

   /**
   * Версия навыка в каталоге
   * @return version
  **/
  @javax.annotation.Nonnull
  public Integer getVersion() {
    return version;
  }


  public void setVersion(Integer version) {
    this.version = version;
  }


  public SkillCatalogItem isInstalled(Boolean isInstalled) {
    
    this.isInstalled = isInstalled;
    return this;
  }

   /**
   * Навык уже установлен в аккаунте
   * @return isInstalled
  **/
  @javax.annotation.Nonnull
  public Boolean getIsInstalled() {
    return isInstalled;
  }


  public void setIsInstalled(Boolean isInstalled) {
    this.isInstalled = isInstalled;
  }


  public SkillCatalogItem installedSkillId(Integer installedSkillId) {
    
    this.installedSkillId = installedSkillId;
    return this;
  }

   /**
   * ID установленного навыка в аккаунте
   * @return installedSkillId
  **/
  @javax.annotation.Nullable
  public Integer getInstalledSkillId() {
    return installedSkillId;
  }


  public void setInstalledSkillId(Integer installedSkillId) {
    this.installedSkillId = installedSkillId;
  }


  public SkillCatalogItem isModified(Boolean isModified) {
    
    this.isModified = isModified;
    return this;
  }

   /**
   * Установленный навык изменялся владельцем
   * @return isModified
  **/
  @javax.annotation.Nonnull
  public Boolean getIsModified() {
    return isModified;
  }


  public void setIsModified(Boolean isModified) {
    this.isModified = isModified;
  }


  public SkillCatalogItem hasCatalogUpdate(Boolean hasCatalogUpdate) {
    
    this.hasCatalogUpdate = hasCatalogUpdate;
    return this;
  }

   /**
   * В каталоге есть версия новее установленной
   * @return hasCatalogUpdate
  **/
  @javax.annotation.Nonnull
  public Boolean getHasCatalogUpdate() {
    return hasCatalogUpdate;
  }


  public void setHasCatalogUpdate(Boolean hasCatalogUpdate) {
    this.hasCatalogUpdate = hasCatalogUpdate;
  }



  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    SkillCatalogItem skillCatalogItem = (SkillCatalogItem) o;
    return Objects.equals(this.id, skillCatalogItem.id) &&
        Objects.equals(this.key, skillCatalogItem.key) &&
        Objects.equals(this.category, skillCatalogItem.category) &&
        Objects.equals(this.name, skillCatalogItem.name) &&
        Objects.equals(this.description, skillCatalogItem.description) &&
        Objects.equals(this.whenToUse, skillCatalogItem.whenToUse) &&
        Objects.equals(this.instruction, skillCatalogItem.instruction) &&
        Objects.equals(this.version, skillCatalogItem.version) &&
        Objects.equals(this.isInstalled, skillCatalogItem.isInstalled) &&
        Objects.equals(this.installedSkillId, skillCatalogItem.installedSkillId) &&
        Objects.equals(this.isModified, skillCatalogItem.isModified) &&
        Objects.equals(this.hasCatalogUpdate, skillCatalogItem.hasCatalogUpdate);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, key, category, name, description, whenToUse, instruction, version, isInstalled, installedSkillId, isModified, hasCatalogUpdate);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class SkillCatalogItem {\n");
    sb.append("    id: ").append(toIndentedString(id)).append("\n");
    sb.append("    key: ").append(toIndentedString(key)).append("\n");
    sb.append("    category: ").append(toIndentedString(category)).append("\n");
    sb.append("    name: ").append(toIndentedString(name)).append("\n");
    sb.append("    description: ").append(toIndentedString(description)).append("\n");
    sb.append("    whenToUse: ").append(toIndentedString(whenToUse)).append("\n");
    sb.append("    instruction: ").append(toIndentedString(instruction)).append("\n");
    sb.append("    version: ").append(toIndentedString(version)).append("\n");
    sb.append("    isInstalled: ").append(toIndentedString(isInstalled)).append("\n");
    sb.append("    installedSkillId: ").append(toIndentedString(installedSkillId)).append("\n");
    sb.append("    isModified: ").append(toIndentedString(isModified)).append("\n");
    sb.append("    hasCatalogUpdate: ").append(toIndentedString(hasCatalogUpdate)).append("\n");
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
    openapiFields.add("key");
    openapiFields.add("category");
    openapiFields.add("name");
    openapiFields.add("description");
    openapiFields.add("when_to_use");
    openapiFields.add("instruction");
    openapiFields.add("version");
    openapiFields.add("is_installed");
    openapiFields.add("installed_skill_id");
    openapiFields.add("is_modified");
    openapiFields.add("has_catalog_update");

    // a set of required properties/fields (JSON key names)
    openapiRequiredFields = new HashSet<String>();
    openapiRequiredFields.add("id");
    openapiRequiredFields.add("key");
    openapiRequiredFields.add("category");
    openapiRequiredFields.add("name");
    openapiRequiredFields.add("description");
    openapiRequiredFields.add("when_to_use");
    openapiRequiredFields.add("instruction");
    openapiRequiredFields.add("version");
    openapiRequiredFields.add("is_installed");
    openapiRequiredFields.add("installed_skill_id");
    openapiRequiredFields.add("is_modified");
    openapiRequiredFields.add("has_catalog_update");
  }

 /**
  * Validates the JSON Element and throws an exception if issues found
  *
  * @param jsonElement JSON Element
  * @throws IOException if the JSON Element is invalid with respect to SkillCatalogItem
  */
  public static void validateJsonElement(JsonElement jsonElement) throws IOException {
      if (jsonElement == null) {
        if (!SkillCatalogItem.openapiRequiredFields.isEmpty()) { // has required fields but JSON element is null
          throw new IllegalArgumentException(String.format("The required field(s) %s in SkillCatalogItem is not found in the empty JSON string", SkillCatalogItem.openapiRequiredFields.toString()));
        }
      }

      Set<Entry<String, JsonElement>> entries = jsonElement.getAsJsonObject().entrySet();
      // check to see if the JSON string contains additional fields
      for (Entry<String, JsonElement> entry : entries) {
        if (!SkillCatalogItem.openapiFields.contains(entry.getKey())) {
          throw new IllegalArgumentException(String.format("The field `%s` in the JSON string is not defined in the `SkillCatalogItem` properties. JSON: %s", entry.getKey(), jsonElement.toString()));
        }
      }

      // check to make sure all required properties/fields are present in the JSON string
      for (String requiredField : SkillCatalogItem.openapiRequiredFields) {
        if (jsonElement.getAsJsonObject().get(requiredField) == null) {
          throw new IllegalArgumentException(String.format("The required field `%s` is not found in the JSON string: %s", requiredField, jsonElement.toString()));
        }
      }
        JsonObject jsonObj = jsonElement.getAsJsonObject();
      if (!jsonObj.get("key").isJsonPrimitive()) {
        throw new IllegalArgumentException(String.format("Expected the field `key` to be a primitive type in the JSON string but got `%s`", jsonObj.get("key").toString()));
      }
      if (!jsonObj.get("category").isJsonPrimitive()) {
        throw new IllegalArgumentException(String.format("Expected the field `category` to be a primitive type in the JSON string but got `%s`", jsonObj.get("category").toString()));
      }
      if (!jsonObj.get("name").isJsonPrimitive()) {
        throw new IllegalArgumentException(String.format("Expected the field `name` to be a primitive type in the JSON string but got `%s`", jsonObj.get("name").toString()));
      }
      if ((jsonObj.get("description") != null && !jsonObj.get("description").isJsonNull()) && !jsonObj.get("description").isJsonPrimitive()) {
        throw new IllegalArgumentException(String.format("Expected the field `description` to be a primitive type in the JSON string but got `%s`", jsonObj.get("description").toString()));
      }
      if (!jsonObj.get("when_to_use").isJsonPrimitive()) {
        throw new IllegalArgumentException(String.format("Expected the field `when_to_use` to be a primitive type in the JSON string but got `%s`", jsonObj.get("when_to_use").toString()));
      }
      if (!jsonObj.get("instruction").isJsonPrimitive()) {
        throw new IllegalArgumentException(String.format("Expected the field `instruction` to be a primitive type in the JSON string but got `%s`", jsonObj.get("instruction").toString()));
      }
  }

  public static class CustomTypeAdapterFactory implements TypeAdapterFactory {
    @SuppressWarnings("unchecked")
    @Override
    public <T> TypeAdapter<T> create(Gson gson, TypeToken<T> type) {
       if (!SkillCatalogItem.class.isAssignableFrom(type.getRawType())) {
         return null; // this class only serializes 'SkillCatalogItem' and its subtypes
       }
       final TypeAdapter<JsonElement> elementAdapter = gson.getAdapter(JsonElement.class);
       final TypeAdapter<SkillCatalogItem> thisAdapter
                        = gson.getDelegateAdapter(this, TypeToken.get(SkillCatalogItem.class));

       return (TypeAdapter<T>) new TypeAdapter<SkillCatalogItem>() {
           @Override
           public void write(JsonWriter out, SkillCatalogItem value) throws IOException {
             JsonObject obj = thisAdapter.toJsonTree(value).getAsJsonObject();
             elementAdapter.write(out, obj);
           }

           @Override
           public SkillCatalogItem read(JsonReader in) throws IOException {
             JsonElement jsonElement = elementAdapter.read(in);
             validateJsonElement(jsonElement);
             return thisAdapter.fromJsonTree(jsonElement);
           }

       }.nullSafe();
    }
  }

 /**
  * Create an instance of SkillCatalogItem given an JSON string
  *
  * @param jsonString JSON string
  * @return An instance of SkillCatalogItem
  * @throws IOException if the JSON string is invalid with respect to SkillCatalogItem
  */
  public static SkillCatalogItem fromJson(String jsonString) throws IOException {
    return JSON.getGson().fromJson(jsonString, SkillCatalogItem.class);
  }

 /**
  * Convert an instance of SkillCatalogItem to an JSON string
  *
  * @return JSON string
  */
  public String toJson() {
    return JSON.getGson().toJson(this);
  }
}

