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
import org.openapitools.client.model.ToolTemplateConfigSchema;
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
 * Шаблон MCP-инструмента из маркетплейса
 */
@javax.annotation.Generated(value = "org.openapitools.codegen.languages.JavaClientCodegen", date = "2026-10-21T20:10:29.240516Z[Etc/UTC]")
public class ToolTemplate {
  public static final String SERIALIZED_NAME_ID = "id";
  @SerializedName(SERIALIZED_NAME_ID)
  private Integer id;

  public static final String SERIALIZED_NAME_NAME = "name";
  @SerializedName(SERIALIZED_NAME_NAME)
  private String name;

  public static final String SERIALIZED_NAME_KEY = "key";
  @SerializedName(SERIALIZED_NAME_KEY)
  private String key;

  public static final String SERIALIZED_NAME_DESCRIPTION = "description";
  @SerializedName(SERIALIZED_NAME_DESCRIPTION)
  private String description;

  /**
   * Тип инструмента
   */
  @JsonAdapter(TypeEnum.Adapter.class)
  public enum TypeEnum {
    MCP("MCP");

    private String value;

    TypeEnum(String value) {
      this.value = value;
    }

    public String getValue() {
      return value;
    }

    @Override
    public String toString() {
      return String.valueOf(value);
    }

    public static TypeEnum fromValue(String value) {
      for (TypeEnum b : TypeEnum.values()) {
        if (b.value.equals(value)) {
          return b;
        }
      }
      throw new IllegalArgumentException("Unexpected value '" + value + "'");
    }

    public static class Adapter extends TypeAdapter<TypeEnum> {
      @Override
      public void write(final JsonWriter jsonWriter, final TypeEnum enumeration) throws IOException {
        jsonWriter.value(enumeration.getValue());
      }

      @Override
      public TypeEnum read(final JsonReader jsonReader) throws IOException {
        String value =  jsonReader.nextString();
        return TypeEnum.fromValue(value);
      }
    }
  }

  public static final String SERIALIZED_NAME_TYPE = "type";
  @SerializedName(SERIALIZED_NAME_TYPE)
  private TypeEnum type;

  public static final String SERIALIZED_NAME_VERSION = "version";
  @SerializedName(SERIALIZED_NAME_VERSION)
  private String version;

  /**
   * Шаблон одобрен для маркетплейса: &#x60;1&#x60; — да, &#x60;0&#x60; — нет
   */
  @JsonAdapter(IsApprovedEnum.Adapter.class)
  public enum IsApprovedEnum {
    NUMBER_0(0),
    
    NUMBER_1(1);

    private Integer value;

    IsApprovedEnum(Integer value) {
      this.value = value;
    }

    public Integer getValue() {
      return value;
    }

    @Override
    public String toString() {
      return String.valueOf(value);
    }

    public static IsApprovedEnum fromValue(Integer value) {
      for (IsApprovedEnum b : IsApprovedEnum.values()) {
        if (b.value.equals(value)) {
          return b;
        }
      }
      throw new IllegalArgumentException("Unexpected value '" + value + "'");
    }

    public static class Adapter extends TypeAdapter<IsApprovedEnum> {
      @Override
      public void write(final JsonWriter jsonWriter, final IsApprovedEnum enumeration) throws IOException {
        jsonWriter.value(enumeration.getValue());
      }

      @Override
      public IsApprovedEnum read(final JsonReader jsonReader) throws IOException {
        Integer value =  jsonReader.nextInt();
        return IsApprovedEnum.fromValue(value);
      }
    }
  }

  public static final String SERIALIZED_NAME_IS_APPROVED = "is_approved";
  @SerializedName(SERIALIZED_NAME_IS_APPROVED)
  private IsApprovedEnum isApproved;

  public static final String SERIALIZED_NAME_AUTHOR_ACCOUNT_ID = "author_account_id";
  @SerializedName(SERIALIZED_NAME_AUTHOR_ACCOUNT_ID)
  private String authorAccountId;

  /**
   * Системный шаблон платформы: &#x60;1&#x60; — да, &#x60;0&#x60; — нет
   */
  @JsonAdapter(IsSystemEnum.Adapter.class)
  public enum IsSystemEnum {
    NUMBER_0(0),
    
    NUMBER_1(1);

    private Integer value;

    IsSystemEnum(Integer value) {
      this.value = value;
    }

    public Integer getValue() {
      return value;
    }

    @Override
    public String toString() {
      return String.valueOf(value);
    }

    public static IsSystemEnum fromValue(Integer value) {
      for (IsSystemEnum b : IsSystemEnum.values()) {
        if (b.value.equals(value)) {
          return b;
        }
      }
      throw new IllegalArgumentException("Unexpected value '" + value + "'");
    }

    public static class Adapter extends TypeAdapter<IsSystemEnum> {
      @Override
      public void write(final JsonWriter jsonWriter, final IsSystemEnum enumeration) throws IOException {
        jsonWriter.value(enumeration.getValue());
      }

      @Override
      public IsSystemEnum read(final JsonReader jsonReader) throws IOException {
        Integer value =  jsonReader.nextInt();
        return IsSystemEnum.fromValue(value);
      }
    }
  }

  public static final String SERIALIZED_NAME_IS_SYSTEM = "is_system";
  @SerializedName(SERIALIZED_NAME_IS_SYSTEM)
  private IsSystemEnum isSystem;

  public static final String SERIALIZED_NAME_INSTALL_COUNT = "install_count";
  @SerializedName(SERIALIZED_NAME_INSTALL_COUNT)
  private Integer installCount;

  /**
   * Категория шаблона
   */
  @JsonAdapter(CategoryEnum.Adapter.class)
  public enum CategoryEnum {
    SEARCH("search"),
    
    NULL("null");

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
      return null;
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

  public static final String SERIALIZED_NAME_CONFIG_SCHEMA = "config_schema";
  @SerializedName(SERIALIZED_NAME_CONFIG_SCHEMA)
  private ToolTemplateConfigSchema configSchema;

  public static final String SERIALIZED_NAME_AUTH_TYPE = "auth_type";
  @SerializedName(SERIALIZED_NAME_AUTH_TYPE)
  private String authType;

  /**
   * Где работает MCP-сервер: &#x60;managed&#x60; — на стороне Timeweb Cloud, &#x60;external&#x60; — у стороннего сервиса
   */
  @JsonAdapter(DeploymentTypeEnum.Adapter.class)
  public enum DeploymentTypeEnum {
    MANAGED("managed"),
    
    EXTERNAL("external");

    private String value;

    DeploymentTypeEnum(String value) {
      this.value = value;
    }

    public String getValue() {
      return value;
    }

    @Override
    public String toString() {
      return String.valueOf(value);
    }

    public static DeploymentTypeEnum fromValue(String value) {
      for (DeploymentTypeEnum b : DeploymentTypeEnum.values()) {
        if (b.value.equals(value)) {
          return b;
        }
      }
      throw new IllegalArgumentException("Unexpected value '" + value + "'");
    }

    public static class Adapter extends TypeAdapter<DeploymentTypeEnum> {
      @Override
      public void write(final JsonWriter jsonWriter, final DeploymentTypeEnum enumeration) throws IOException {
        jsonWriter.value(enumeration.getValue());
      }

      @Override
      public DeploymentTypeEnum read(final JsonReader jsonReader) throws IOException {
        String value =  jsonReader.nextString();
        return DeploymentTypeEnum.fromValue(value);
      }
    }
  }

  public static final String SERIALIZED_NAME_DEPLOYMENT_TYPE = "deployment_type";
  @SerializedName(SERIALIZED_NAME_DEPLOYMENT_TYPE)
  private DeploymentTypeEnum deploymentType;

  public static final String SERIALIZED_NAME_CREATED_AT = "created_at";
  @SerializedName(SERIALIZED_NAME_CREATED_AT)
  private OffsetDateTime createdAt;

  public static final String SERIALIZED_NAME_UPDATED_AT = "updated_at";
  @SerializedName(SERIALIZED_NAME_UPDATED_AT)
  private OffsetDateTime updatedAt;

  public ToolTemplate() {
  }

  public ToolTemplate id(Integer id) {
    
    this.id = id;
    return this;
  }

   /**
   * ID шаблона. Передаётся в &#x60;template_id&#x60; при установке
   * @return id
  **/
  @javax.annotation.Nonnull
  public Integer getId() {
    return id;
  }


  public void setId(Integer id) {
    this.id = id;
  }


  public ToolTemplate name(String name) {
    
    this.name = name;
    return this;
  }

   /**
   * Название
   * @return name
  **/
  @javax.annotation.Nonnull
  public String getName() {
    return name;
  }


  public void setName(String name) {
    this.name = name;
  }


  public ToolTemplate key(String key) {
    
    this.key = key;
    return this;
  }

   /**
   * Уникальный ключ шаблона
   * @return key
  **/
  @javax.annotation.Nullable
  public String getKey() {
    return key;
  }


  public void setKey(String key) {
    this.key = key;
  }


  public ToolTemplate description(String description) {
    
    this.description = description;
    return this;
  }

   /**
   * Описание
   * @return description
  **/
  @javax.annotation.Nonnull
  public String getDescription() {
    return description;
  }


  public void setDescription(String description) {
    this.description = description;
  }


  public ToolTemplate type(TypeEnum type) {
    
    this.type = type;
    return this;
  }

   /**
   * Тип инструмента
   * @return type
  **/
  @javax.annotation.Nonnull
  public TypeEnum getType() {
    return type;
  }


  public void setType(TypeEnum type) {
    this.type = type;
  }


  public ToolTemplate version(String version) {
    
    this.version = version;
    return this;
  }

   /**
   * Версия шаблона
   * @return version
  **/
  @javax.annotation.Nonnull
  public String getVersion() {
    return version;
  }


  public void setVersion(String version) {
    this.version = version;
  }


  public ToolTemplate isApproved(IsApprovedEnum isApproved) {
    
    this.isApproved = isApproved;
    return this;
  }

   /**
   * Шаблон одобрен для маркетплейса: &#x60;1&#x60; — да, &#x60;0&#x60; — нет
   * @return isApproved
  **/
  @javax.annotation.Nonnull
  public IsApprovedEnum getIsApproved() {
    return isApproved;
  }


  public void setIsApproved(IsApprovedEnum isApproved) {
    this.isApproved = isApproved;
  }


  public ToolTemplate authorAccountId(String authorAccountId) {
    
    this.authorAccountId = authorAccountId;
    return this;
  }

   /**
   * Автор шаблона. &#x60;null&#x60; — шаблон Timeweb Cloud
   * @return authorAccountId
  **/
  @javax.annotation.Nullable
  public String getAuthorAccountId() {
    return authorAccountId;
  }


  public void setAuthorAccountId(String authorAccountId) {
    this.authorAccountId = authorAccountId;
  }


  public ToolTemplate isSystem(IsSystemEnum isSystem) {
    
    this.isSystem = isSystem;
    return this;
  }

   /**
   * Системный шаблон платформы: &#x60;1&#x60; — да, &#x60;0&#x60; — нет
   * @return isSystem
  **/
  @javax.annotation.Nonnull
  public IsSystemEnum getIsSystem() {
    return isSystem;
  }


  public void setIsSystem(IsSystemEnum isSystem) {
    this.isSystem = isSystem;
  }


  public ToolTemplate installCount(Integer installCount) {
    
    this.installCount = installCount;
    return this;
  }

   /**
   * Сколько раз шаблон установлен
   * @return installCount
  **/
  @javax.annotation.Nonnull
  public Integer getInstallCount() {
    return installCount;
  }


  public void setInstallCount(Integer installCount) {
    this.installCount = installCount;
  }


  public ToolTemplate category(CategoryEnum category) {
    
    this.category = category;
    return this;
  }

   /**
   * Категория шаблона
   * @return category
  **/
  @javax.annotation.Nullable
  public CategoryEnum getCategory() {
    return category;
  }


  public void setCategory(CategoryEnum category) {
    this.category = category;
  }


  public ToolTemplate configSchema(ToolTemplateConfigSchema configSchema) {
    
    this.configSchema = configSchema;
    return this;
  }

   /**
   * Get configSchema
   * @return configSchema
  **/
  @javax.annotation.Nullable
  public ToolTemplateConfigSchema getConfigSchema() {
    return configSchema;
  }


  public void setConfigSchema(ToolTemplateConfigSchema configSchema) {
    this.configSchema = configSchema;
  }


  public ToolTemplate authType(String authType) {
    
    this.authType = authType;
    return this;
  }

   /**
   * Способ авторизации на MCP-сервере
   * @return authType
  **/
  @javax.annotation.Nonnull
  public String getAuthType() {
    return authType;
  }


  public void setAuthType(String authType) {
    this.authType = authType;
  }


  public ToolTemplate deploymentType(DeploymentTypeEnum deploymentType) {
    
    this.deploymentType = deploymentType;
    return this;
  }

   /**
   * Где работает MCP-сервер: &#x60;managed&#x60; — на стороне Timeweb Cloud, &#x60;external&#x60; — у стороннего сервиса
   * @return deploymentType
  **/
  @javax.annotation.Nonnull
  public DeploymentTypeEnum getDeploymentType() {
    return deploymentType;
  }


  public void setDeploymentType(DeploymentTypeEnum deploymentType) {
    this.deploymentType = deploymentType;
  }


  public ToolTemplate createdAt(OffsetDateTime createdAt) {
    
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


  public ToolTemplate updatedAt(OffsetDateTime updatedAt) {
    
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
    ToolTemplate toolTemplate = (ToolTemplate) o;
    return Objects.equals(this.id, toolTemplate.id) &&
        Objects.equals(this.name, toolTemplate.name) &&
        Objects.equals(this.key, toolTemplate.key) &&
        Objects.equals(this.description, toolTemplate.description) &&
        Objects.equals(this.type, toolTemplate.type) &&
        Objects.equals(this.version, toolTemplate.version) &&
        Objects.equals(this.isApproved, toolTemplate.isApproved) &&
        Objects.equals(this.authorAccountId, toolTemplate.authorAccountId) &&
        Objects.equals(this.isSystem, toolTemplate.isSystem) &&
        Objects.equals(this.installCount, toolTemplate.installCount) &&
        Objects.equals(this.category, toolTemplate.category) &&
        Objects.equals(this.configSchema, toolTemplate.configSchema) &&
        Objects.equals(this.authType, toolTemplate.authType) &&
        Objects.equals(this.deploymentType, toolTemplate.deploymentType) &&
        Objects.equals(this.createdAt, toolTemplate.createdAt) &&
        Objects.equals(this.updatedAt, toolTemplate.updatedAt);
  }

  private static <T> boolean equalsNullable(JsonNullable<T> a, JsonNullable<T> b) {
    return a == b || (a != null && b != null && a.isPresent() && b.isPresent() && Objects.deepEquals(a.get(), b.get()));
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, name, key, description, type, version, isApproved, authorAccountId, isSystem, installCount, category, configSchema, authType, deploymentType, createdAt, updatedAt);
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
    sb.append("class ToolTemplate {\n");
    sb.append("    id: ").append(toIndentedString(id)).append("\n");
    sb.append("    name: ").append(toIndentedString(name)).append("\n");
    sb.append("    key: ").append(toIndentedString(key)).append("\n");
    sb.append("    description: ").append(toIndentedString(description)).append("\n");
    sb.append("    type: ").append(toIndentedString(type)).append("\n");
    sb.append("    version: ").append(toIndentedString(version)).append("\n");
    sb.append("    isApproved: ").append(toIndentedString(isApproved)).append("\n");
    sb.append("    authorAccountId: ").append(toIndentedString(authorAccountId)).append("\n");
    sb.append("    isSystem: ").append(toIndentedString(isSystem)).append("\n");
    sb.append("    installCount: ").append(toIndentedString(installCount)).append("\n");
    sb.append("    category: ").append(toIndentedString(category)).append("\n");
    sb.append("    configSchema: ").append(toIndentedString(configSchema)).append("\n");
    sb.append("    authType: ").append(toIndentedString(authType)).append("\n");
    sb.append("    deploymentType: ").append(toIndentedString(deploymentType)).append("\n");
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
    openapiFields.add("key");
    openapiFields.add("description");
    openapiFields.add("type");
    openapiFields.add("version");
    openapiFields.add("is_approved");
    openapiFields.add("author_account_id");
    openapiFields.add("is_system");
    openapiFields.add("install_count");
    openapiFields.add("category");
    openapiFields.add("config_schema");
    openapiFields.add("auth_type");
    openapiFields.add("deployment_type");
    openapiFields.add("created_at");
    openapiFields.add("updated_at");

    // a set of required properties/fields (JSON key names)
    openapiRequiredFields = new HashSet<String>();
    openapiRequiredFields.add("id");
    openapiRequiredFields.add("name");
    openapiRequiredFields.add("description");
    openapiRequiredFields.add("type");
    openapiRequiredFields.add("version");
    openapiRequiredFields.add("is_approved");
    openapiRequiredFields.add("is_system");
    openapiRequiredFields.add("install_count");
    openapiRequiredFields.add("auth_type");
    openapiRequiredFields.add("deployment_type");
    openapiRequiredFields.add("created_at");
    openapiRequiredFields.add("updated_at");
  }

 /**
  * Validates the JSON Element and throws an exception if issues found
  *
  * @param jsonElement JSON Element
  * @throws IOException if the JSON Element is invalid with respect to ToolTemplate
  */
  public static void validateJsonElement(JsonElement jsonElement) throws IOException {
      if (jsonElement == null) {
        if (!ToolTemplate.openapiRequiredFields.isEmpty()) { // has required fields but JSON element is null
          throw new IllegalArgumentException(String.format("The required field(s) %s in ToolTemplate is not found in the empty JSON string", ToolTemplate.openapiRequiredFields.toString()));
        }
      }

      Set<Entry<String, JsonElement>> entries = jsonElement.getAsJsonObject().entrySet();
      // check to see if the JSON string contains additional fields
      for (Entry<String, JsonElement> entry : entries) {
        if (!ToolTemplate.openapiFields.contains(entry.getKey())) {
          throw new IllegalArgumentException(String.format("The field `%s` in the JSON string is not defined in the `ToolTemplate` properties. JSON: %s", entry.getKey(), jsonElement.toString()));
        }
      }

      // check to make sure all required properties/fields are present in the JSON string
      for (String requiredField : ToolTemplate.openapiRequiredFields) {
        if (jsonElement.getAsJsonObject().get(requiredField) == null) {
          throw new IllegalArgumentException(String.format("The required field `%s` is not found in the JSON string: %s", requiredField, jsonElement.toString()));
        }
      }
        JsonObject jsonObj = jsonElement.getAsJsonObject();
      if (!jsonObj.get("name").isJsonPrimitive()) {
        throw new IllegalArgumentException(String.format("Expected the field `name` to be a primitive type in the JSON string but got `%s`", jsonObj.get("name").toString()));
      }
      if ((jsonObj.get("key") != null && !jsonObj.get("key").isJsonNull()) && !jsonObj.get("key").isJsonPrimitive()) {
        throw new IllegalArgumentException(String.format("Expected the field `key` to be a primitive type in the JSON string but got `%s`", jsonObj.get("key").toString()));
      }
      if (!jsonObj.get("description").isJsonPrimitive()) {
        throw new IllegalArgumentException(String.format("Expected the field `description` to be a primitive type in the JSON string but got `%s`", jsonObj.get("description").toString()));
      }
      if (!jsonObj.get("type").isJsonPrimitive()) {
        throw new IllegalArgumentException(String.format("Expected the field `type` to be a primitive type in the JSON string but got `%s`", jsonObj.get("type").toString()));
      }
      if (!jsonObj.get("version").isJsonPrimitive()) {
        throw new IllegalArgumentException(String.format("Expected the field `version` to be a primitive type in the JSON string but got `%s`", jsonObj.get("version").toString()));
      }
      if ((jsonObj.get("author_account_id") != null && !jsonObj.get("author_account_id").isJsonNull()) && !jsonObj.get("author_account_id").isJsonPrimitive()) {
        throw new IllegalArgumentException(String.format("Expected the field `author_account_id` to be a primitive type in the JSON string but got `%s`", jsonObj.get("author_account_id").toString()));
      }
      if ((jsonObj.get("category") != null && !jsonObj.get("category").isJsonNull()) && !jsonObj.get("category").isJsonPrimitive()) {
        throw new IllegalArgumentException(String.format("Expected the field `category` to be a primitive type in the JSON string but got `%s`", jsonObj.get("category").toString()));
      }
      // validate the optional field `config_schema`
      if (jsonObj.get("config_schema") != null && !jsonObj.get("config_schema").isJsonNull()) {
        ToolTemplateConfigSchema.validateJsonElement(jsonObj.get("config_schema"));
      }
      if (!jsonObj.get("auth_type").isJsonPrimitive()) {
        throw new IllegalArgumentException(String.format("Expected the field `auth_type` to be a primitive type in the JSON string but got `%s`", jsonObj.get("auth_type").toString()));
      }
      if (!jsonObj.get("deployment_type").isJsonPrimitive()) {
        throw new IllegalArgumentException(String.format("Expected the field `deployment_type` to be a primitive type in the JSON string but got `%s`", jsonObj.get("deployment_type").toString()));
      }
  }

  public static class CustomTypeAdapterFactory implements TypeAdapterFactory {
    @SuppressWarnings("unchecked")
    @Override
    public <T> TypeAdapter<T> create(Gson gson, TypeToken<T> type) {
       if (!ToolTemplate.class.isAssignableFrom(type.getRawType())) {
         return null; // this class only serializes 'ToolTemplate' and its subtypes
       }
       final TypeAdapter<JsonElement> elementAdapter = gson.getAdapter(JsonElement.class);
       final TypeAdapter<ToolTemplate> thisAdapter
                        = gson.getDelegateAdapter(this, TypeToken.get(ToolTemplate.class));

       return (TypeAdapter<T>) new TypeAdapter<ToolTemplate>() {
           @Override
           public void write(JsonWriter out, ToolTemplate value) throws IOException {
             JsonObject obj = thisAdapter.toJsonTree(value).getAsJsonObject();
             elementAdapter.write(out, obj);
           }

           @Override
           public ToolTemplate read(JsonReader in) throws IOException {
             JsonElement jsonElement = elementAdapter.read(in);
             validateJsonElement(jsonElement);
             return thisAdapter.fromJsonTree(jsonElement);
           }

       }.nullSafe();
    }
  }

 /**
  * Create an instance of ToolTemplate given an JSON string
  *
  * @param jsonString JSON string
  * @return An instance of ToolTemplate
  * @throws IOException if the JSON string is invalid with respect to ToolTemplate
  */
  public static ToolTemplate fromJson(String jsonString) throws IOException {
    return JSON.getGson().fromJson(jsonString, ToolTemplate.class);
  }

 /**
  * Convert an instance of ToolTemplate to an JSON string
  *
  * @return JSON string
  */
  public String toJson() {
    return JSON.getGson().toJson(this);
  }
}

