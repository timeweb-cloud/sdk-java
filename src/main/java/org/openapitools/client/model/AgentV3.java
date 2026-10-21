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
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import org.openapitools.client.model.AgentAdditionalModel;
import org.openapitools.client.model.AgentSettings;
import org.openapitools.client.model.AgentToolsInner;
import org.openapitools.client.model.AgentV3KnowledgeBasesInner;
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
 * AI агент (API v3)
 */
@javax.annotation.Generated(value = "org.openapitools.codegen.languages.JavaClientCodegen", date = "2026-10-21T20:10:29.240516Z[Etc/UTC]")
public class AgentV3 {
  public static final String SERIALIZED_NAME_ID = "id";
  @SerializedName(SERIALIZED_NAME_ID)
  private Integer id;

  public static final String SERIALIZED_NAME_NAME = "name";
  @SerializedName(SERIALIZED_NAME_NAME)
  private String name;

  public static final String SERIALIZED_NAME_DESCRIPTION = "description";
  @SerializedName(SERIALIZED_NAME_DESCRIPTION)
  private String description;

  public static final String SERIALIZED_NAME_PROJECT_ID = "project_id";
  @SerializedName(SERIALIZED_NAME_PROJECT_ID)
  private Integer projectId;

  public static final String SERIALIZED_NAME_MODEL_ID = "model_id";
  @SerializedName(SERIALIZED_NAME_MODEL_ID)
  private Integer modelId;

  public static final String SERIALIZED_NAME_PROVIDER_ID = "provider_id";
  @SerializedName(SERIALIZED_NAME_PROVIDER_ID)
  private Integer providerId;

  public static final String SERIALIZED_NAME_SETTINGS = "settings";
  @SerializedName(SERIALIZED_NAME_SETTINGS)
  private AgentSettings settings;

  /**
   * Статус агента: - &#x60;active&#x60; — работает; - &#x60;suspended&#x60; — приостановлен владельцем; - &#x60;blocked&#x60; — заблокирован (для агентов с пакетом токенов — закончились токены или подписка); - &#x60;deleted&#x60; — удалён.
   */
  @JsonAdapter(StatusEnum.Adapter.class)
  public enum StatusEnum {
    ACTIVE("active"),
    
    BLOCKED("blocked"),
    
    DELETED("deleted"),
    
    SUSPENDED("suspended");

    private String value;

    StatusEnum(String value) {
      this.value = value;
    }

    public String getValue() {
      return value;
    }

    @Override
    public String toString() {
      return String.valueOf(value);
    }

    public static StatusEnum fromValue(String value) {
      for (StatusEnum b : StatusEnum.values()) {
        if (b.value.equals(value)) {
          return b;
        }
      }
      throw new IllegalArgumentException("Unexpected value '" + value + "'");
    }

    public static class Adapter extends TypeAdapter<StatusEnum> {
      @Override
      public void write(final JsonWriter jsonWriter, final StatusEnum enumeration) throws IOException {
        jsonWriter.value(enumeration.getValue());
      }

      @Override
      public StatusEnum read(final JsonReader jsonReader) throws IOException {
        String value =  jsonReader.nextString();
        return StatusEnum.fromValue(value);
      }
    }
  }

  public static final String SERIALIZED_NAME_STATUS = "status";
  @SerializedName(SERIALIZED_NAME_STATUS)
  private StatusEnum status;

  /**
   * Тип доступа: &#x60;public&#x60; — агент отвечает анонимным посетителям сайта через виджет; &#x60;private&#x60; — только по токену аккаунта.
   */
  @JsonAdapter(AccessTypeEnum.Adapter.class)
  public enum AccessTypeEnum {
    PUBLIC("public"),
    
    PRIVATE("private");

    private String value;

    AccessTypeEnum(String value) {
      this.value = value;
    }

    public String getValue() {
      return value;
    }

    @Override
    public String toString() {
      return String.valueOf(value);
    }

    public static AccessTypeEnum fromValue(String value) {
      for (AccessTypeEnum b : AccessTypeEnum.values()) {
        if (b.value.equals(value)) {
          return b;
        }
      }
      throw new IllegalArgumentException("Unexpected value '" + value + "'");
    }

    public static class Adapter extends TypeAdapter<AccessTypeEnum> {
      @Override
      public void write(final JsonWriter jsonWriter, final AccessTypeEnum enumeration) throws IOException {
        jsonWriter.value(enumeration.getValue());
      }

      @Override
      public AccessTypeEnum read(final JsonReader jsonReader) throws IOException {
        String value =  jsonReader.nextString();
        return AccessTypeEnum.fromValue(value);
      }
    }
  }

  public static final String SERIALIZED_NAME_ACCESS_TYPE = "access_type";
  @SerializedName(SERIALIZED_NAME_ACCESS_TYPE)
  private AccessTypeEnum accessType;

  public static final String SERIALIZED_NAME_ACCESS_ID = "access_id";
  @SerializedName(SERIALIZED_NAME_ACCESS_ID)
  private UUID accessId;

  public static final String SERIALIZED_NAME_TOKEN_LIMIT = "token_limit";
  @Deprecated
  @SerializedName(SERIALIZED_NAME_TOKEN_LIMIT)
  private Integer tokenLimit;

  public static final String SERIALIZED_NAME_TOTAL_TOKENS = "total_tokens";
  @SerializedName(SERIALIZED_NAME_TOTAL_TOKENS)
  private Integer totalTokens;

  public static final String SERIALIZED_NAME_USED_TOKENS = "used_tokens";
  @SerializedName(SERIALIZED_NAME_USED_TOKENS)
  private Integer usedTokens;

  public static final String SERIALIZED_NAME_REMAINING_TOKENS = "remaining_tokens";
  @SerializedName(SERIALIZED_NAME_REMAINING_TOKENS)
  private Integer remainingTokens;

  public static final String SERIALIZED_NAME_TOKEN_PACKAGE_ID = "token_package_id";
  @SerializedName(SERIALIZED_NAME_TOKEN_PACKAGE_ID)
  private Integer tokenPackageId;

  public static final String SERIALIZED_NAME_SUBSCRIPTION_RENEWAL_DATE = "subscription_renewal_date";
  @SerializedName(SERIALIZED_NAME_SUBSCRIPTION_RENEWAL_DATE)
  private OffsetDateTime subscriptionRenewalDate;

  public static final String SERIALIZED_NAME_AUTO_BUY_ADDITIONAL_TOKENS = "auto_buy_additional_tokens";
  @SerializedName(SERIALIZED_NAME_AUTO_BUY_ADDITIONAL_TOKENS)
  private Boolean autoBuyAdditionalTokens;

  public static final String SERIALIZED_NAME_TOOLS = "tools";
  @SerializedName(SERIALIZED_NAME_TOOLS)
  private List<AgentToolsInner> tools = new ArrayList<>();

  public static final String SERIALIZED_NAME_ADDITIONAL_MODEL_IDS = "additional_model_ids";
  @SerializedName(SERIALIZED_NAME_ADDITIONAL_MODEL_IDS)
  private List<Integer> additionalModelIds = new ArrayList<>();

  public static final String SERIALIZED_NAME_ADDITIONAL_MODELS = "additional_models";
  @SerializedName(SERIALIZED_NAME_ADDITIONAL_MODELS)
  private List<AgentAdditionalModel> additionalModels = new ArrayList<>();

  public static final String SERIALIZED_NAME_IS_WEB_SEARCH_ENABLED = "is_web_search_enabled";
  @SerializedName(SERIALIZED_NAME_IS_WEB_SEARCH_ENABLED)
  private Boolean isWebSearchEnabled;

  public static final String SERIALIZED_NAME_AI_GATEWAY_KEY_ID = "ai_gateway_key_id";
  @SerializedName(SERIALIZED_NAME_AI_GATEWAY_KEY_ID)
  private UUID aiGatewayKeyId;

  public static final String SERIALIZED_NAME_CREATED_AT = "created_at";
  @SerializedName(SERIALIZED_NAME_CREATED_AT)
  private OffsetDateTime createdAt;

  public static final String SERIALIZED_NAME_KNOWLEDGE_BASES = "knowledge_bases";
  @SerializedName(SERIALIZED_NAME_KNOWLEDGE_BASES)
  private List<AgentV3KnowledgeBasesInner> knowledgeBases = new ArrayList<>();

  public AgentV3() {
  }

  public AgentV3 id(Integer id) {
    
    this.id = id;
    return this;
  }

   /**
   * ID агента
   * @return id
  **/
  @javax.annotation.Nonnull
  public Integer getId() {
    return id;
  }


  public void setId(Integer id) {
    this.id = id;
  }


  public AgentV3 name(String name) {
    
    this.name = name;
    return this;
  }

   /**
   * Название агента
   * @return name
  **/
  @javax.annotation.Nonnull
  public String getName() {
    return name;
  }


  public void setName(String name) {
    this.name = name;
  }


  public AgentV3 description(String description) {
    
    this.description = description;
    return this;
  }

   /**
   * Описание агента
   * @return description
  **/
  @javax.annotation.Nullable
  public String getDescription() {
    return description;
  }


  public void setDescription(String description) {
    this.description = description;
  }


  public AgentV3 projectId(Integer projectId) {
    
    this.projectId = projectId;
    return this;
  }

   /**
   * ID проекта, в котором находится агент
   * @return projectId
  **/
  @javax.annotation.Nullable
  public Integer getProjectId() {
    return projectId;
  }


  public void setProjectId(Integer projectId) {
    this.projectId = projectId;
  }


  public AgentV3 modelId(Integer modelId) {
    
    this.modelId = modelId;
    return this;
  }

   /**
   * ID основной модели агента
   * @return modelId
  **/
  @javax.annotation.Nonnull
  public Integer getModelId() {
    return modelId;
  }


  public void setModelId(Integer modelId) {
    this.modelId = modelId;
  }


  public AgentV3 providerId(Integer providerId) {
    
    this.providerId = providerId;
    return this;
  }

   /**
   * ID провайдера модели
   * @return providerId
  **/
  @javax.annotation.Nonnull
  public Integer getProviderId() {
    return providerId;
  }


  public void setProviderId(Integer providerId) {
    this.providerId = providerId;
  }


  public AgentV3 settings(AgentSettings settings) {
    
    this.settings = settings;
    return this;
  }

   /**
   * Get settings
   * @return settings
  **/
  @javax.annotation.Nonnull
  public AgentSettings getSettings() {
    return settings;
  }


  public void setSettings(AgentSettings settings) {
    this.settings = settings;
  }


  public AgentV3 status(StatusEnum status) {
    
    this.status = status;
    return this;
  }

   /**
   * Статус агента: - &#x60;active&#x60; — работает; - &#x60;suspended&#x60; — приостановлен владельцем; - &#x60;blocked&#x60; — заблокирован (для агентов с пакетом токенов — закончились токены или подписка); - &#x60;deleted&#x60; — удалён.
   * @return status
  **/
  @javax.annotation.Nonnull
  public StatusEnum getStatus() {
    return status;
  }


  public void setStatus(StatusEnum status) {
    this.status = status;
  }


  public AgentV3 accessType(AccessTypeEnum accessType) {
    
    this.accessType = accessType;
    return this;
  }

   /**
   * Тип доступа: &#x60;public&#x60; — агент отвечает анонимным посетителям сайта через виджет; &#x60;private&#x60; — только по токену аккаунта.
   * @return accessType
  **/
  @javax.annotation.Nonnull
  public AccessTypeEnum getAccessType() {
    return accessType;
  }


  public void setAccessType(AccessTypeEnum accessType) {
    this.accessType = accessType;
  }


  public AgentV3 accessId(UUID accessId) {
    
    this.accessId = accessId;
    return this;
  }

   /**
   * Идентификатор доступа агента. Используется для вызова агента из своих приложений через OpenAI-совместимое API (см. https://agent.timeweb.cloud/docs) и для встраивания виджета.
   * @return accessId
  **/
  @javax.annotation.Nonnull
  public UUID getAccessId() {
    return accessId;
  }


  public void setAccessId(UUID accessId) {
    this.accessId = accessId;
  }


  @Deprecated
  public AgentV3 tokenLimit(Integer tokenLimit) {
    
    this.tokenLimit = tokenLimit;
    return this;
  }

   /**
   * Устарело. Дневной лимит агента в токенах без привязки к модели, если он задан; &#x60;0&#x60; — такого лимита нет. Лимиты в деньгах, по моделям и за другие периоды здесь не отражаются — используйте лимиты агента (&#x60;/api/v1/cloud-ai/agents/{id}/entity-limits&#x60;).
   * @return tokenLimit
   * @deprecated
  **/
  @Deprecated
  @javax.annotation.Nonnull
  public Integer getTokenLimit() {
    return tokenLimit;
  }


  @Deprecated
  public void setTokenLimit(Integer tokenLimit) {
    this.tokenLimit = tokenLimit;
  }


  public AgentV3 totalTokens(Integer totalTokens) {
    
    this.totalTokens = totalTokens;
    return this;
  }

   /**
   * Всего токенов в пакете. Только для агентов с пакетом токенов (устаревшая схема оплаты).
   * @return totalTokens
  **/
  @javax.annotation.Nullable
  public Integer getTotalTokens() {
    return totalTokens;
  }


  public void setTotalTokens(Integer totalTokens) {
    this.totalTokens = totalTokens;
  }


  public AgentV3 usedTokens(Integer usedTokens) {
    
    this.usedTokens = usedTokens;
    return this;
  }

   /**
   * Использовано токенов пакета. Для агентов с поресурсной оплатой — &#x60;null&#x60;.
   * @return usedTokens
  **/
  @javax.annotation.Nullable
  public Integer getUsedTokens() {
    return usedTokens;
  }


  public void setUsedTokens(Integer usedTokens) {
    this.usedTokens = usedTokens;
  }


  public AgentV3 remainingTokens(Integer remainingTokens) {
    
    this.remainingTokens = remainingTokens;
    return this;
  }

   /**
   * Осталось токенов в пакете. Только для агентов с пакетом токенов.
   * @return remainingTokens
  **/
  @javax.annotation.Nullable
  public Integer getRemainingTokens() {
    return remainingTokens;
  }


  public void setRemainingTokens(Integer remainingTokens) {
    this.remainingTokens = remainingTokens;
  }


  public AgentV3 tokenPackageId(Integer tokenPackageId) {
    
    this.tokenPackageId = tokenPackageId;
    return this;
  }

   /**
   * ID пакета токенов. Только для агентов с пакетом токенов; у агентов с поресурсной оплатой поле отсутствует.
   * @return tokenPackageId
  **/
  @javax.annotation.Nullable
  public Integer getTokenPackageId() {
    return tokenPackageId;
  }


  public void setTokenPackageId(Integer tokenPackageId) {
    this.tokenPackageId = tokenPackageId;
  }


  public AgentV3 subscriptionRenewalDate(OffsetDateTime subscriptionRenewalDate) {
    
    this.subscriptionRenewalDate = subscriptionRenewalDate;
    return this;
  }

   /**
   * Дата продления подписки на пакет токенов. Только для агентов с пакетом токенов.
   * @return subscriptionRenewalDate
  **/
  @javax.annotation.Nullable
  public OffsetDateTime getSubscriptionRenewalDate() {
    return subscriptionRenewalDate;
  }


  public void setSubscriptionRenewalDate(OffsetDateTime subscriptionRenewalDate) {
    this.subscriptionRenewalDate = subscriptionRenewalDate;
  }


  public AgentV3 autoBuyAdditionalTokens(Boolean autoBuyAdditionalTokens) {
    
    this.autoBuyAdditionalTokens = autoBuyAdditionalTokens;
    return this;
  }

   /**
   * Включена ли автоматическая докупка пакетов токенов
   * @return autoBuyAdditionalTokens
  **/
  @javax.annotation.Nonnull
  public Boolean getAutoBuyAdditionalTokens() {
    return autoBuyAdditionalTokens;
  }


  public void setAutoBuyAdditionalTokens(Boolean autoBuyAdditionalTokens) {
    this.autoBuyAdditionalTokens = autoBuyAdditionalTokens;
  }


  public AgentV3 tools(List<AgentToolsInner> tools) {
    
    this.tools = tools;
    return this;
  }

  public AgentV3 addToolsItem(AgentToolsInner toolsItem) {
    if (this.tools == null) {
      this.tools = new ArrayList<>();
    }
    this.tools.add(toolsItem);
    return this;
  }

   /**
   * Инструменты, подключённые к агенту
   * @return tools
  **/
  @javax.annotation.Nonnull
  public List<AgentToolsInner> getTools() {
    return tools;
  }


  public void setTools(List<AgentToolsInner> tools) {
    this.tools = tools;
  }


  public AgentV3 additionalModelIds(List<Integer> additionalModelIds) {
    
    this.additionalModelIds = additionalModelIds;
    return this;
  }

  public AgentV3 addAdditionalModelIdsItem(Integer additionalModelIdsItem) {
    if (this.additionalModelIds == null) {
      this.additionalModelIds = new ArrayList<>();
    }
    this.additionalModelIds.add(additionalModelIdsItem);
    return this;
  }

   /**
   * ID дополнительных моделей агента
   * @return additionalModelIds
  **/
  @javax.annotation.Nonnull
  public List<Integer> getAdditionalModelIds() {
    return additionalModelIds;
  }


  public void setAdditionalModelIds(List<Integer> additionalModelIds) {
    this.additionalModelIds = additionalModelIds;
  }


  public AgentV3 additionalModels(List<AgentAdditionalModel> additionalModels) {
    
    this.additionalModels = additionalModels;
    return this;
  }

  public AgentV3 addAdditionalModelsItem(AgentAdditionalModel additionalModelsItem) {
    if (this.additionalModels == null) {
      this.additionalModels = new ArrayList<>();
    }
    this.additionalModels.add(additionalModelsItem);
    return this;
  }

   /**
   * Дополнительные модели агента с настройками
   * @return additionalModels
  **/
  @javax.annotation.Nonnull
  public List<AgentAdditionalModel> getAdditionalModels() {
    return additionalModels;
  }


  public void setAdditionalModels(List<AgentAdditionalModel> additionalModels) {
    this.additionalModels = additionalModels;
  }


  public AgentV3 isWebSearchEnabled(Boolean isWebSearchEnabled) {
    
    this.isWebSearchEnabled = isWebSearchEnabled;
    return this;
  }

   /**
   * Включён ли веб-поиск
   * @return isWebSearchEnabled
  **/
  @javax.annotation.Nonnull
  public Boolean getIsWebSearchEnabled() {
    return isWebSearchEnabled;
  }


  public void setIsWebSearchEnabled(Boolean isWebSearchEnabled) {
    this.isWebSearchEnabled = isWebSearchEnabled;
  }


  public AgentV3 aiGatewayKeyId(UUID aiGatewayKeyId) {
    
    this.aiGatewayKeyId = aiGatewayKeyId;
    return this;
  }

   /**
   * ID ключа AI Gateway, к которому привязан агент. &#x60;null&#x60; — агента можно привязать к ключу (см. обновление агента); поле отсутствует — привязка недоступна.
   * @return aiGatewayKeyId
  **/
  @javax.annotation.Nullable
  public UUID getAiGatewayKeyId() {
    return aiGatewayKeyId;
  }


  public void setAiGatewayKeyId(UUID aiGatewayKeyId) {
    this.aiGatewayKeyId = aiGatewayKeyId;
  }


  public AgentV3 createdAt(OffsetDateTime createdAt) {
    
    this.createdAt = createdAt;
    return this;
  }

   /**
   * Дата создания агента
   * @return createdAt
  **/
  @javax.annotation.Nonnull
  public OffsetDateTime getCreatedAt() {
    return createdAt;
  }


  public void setCreatedAt(OffsetDateTime createdAt) {
    this.createdAt = createdAt;
  }


  public AgentV3 knowledgeBases(List<AgentV3KnowledgeBasesInner> knowledgeBases) {
    
    this.knowledgeBases = knowledgeBases;
    return this;
  }

  public AgentV3 addKnowledgeBasesItem(AgentV3KnowledgeBasesInner knowledgeBasesItem) {
    if (this.knowledgeBases == null) {
      this.knowledgeBases = new ArrayList<>();
    }
    this.knowledgeBases.add(knowledgeBasesItem);
    return this;
  }

   /**
   * Базы знаний, подключённые к агенту, с режимом поиска
   * @return knowledgeBases
  **/
  @javax.annotation.Nonnull
  public List<AgentV3KnowledgeBasesInner> getKnowledgeBases() {
    return knowledgeBases;
  }


  public void setKnowledgeBases(List<AgentV3KnowledgeBasesInner> knowledgeBases) {
    this.knowledgeBases = knowledgeBases;
  }



  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    AgentV3 agentV3 = (AgentV3) o;
    return Objects.equals(this.id, agentV3.id) &&
        Objects.equals(this.name, agentV3.name) &&
        Objects.equals(this.description, agentV3.description) &&
        Objects.equals(this.projectId, agentV3.projectId) &&
        Objects.equals(this.modelId, agentV3.modelId) &&
        Objects.equals(this.providerId, agentV3.providerId) &&
        Objects.equals(this.settings, agentV3.settings) &&
        Objects.equals(this.status, agentV3.status) &&
        Objects.equals(this.accessType, agentV3.accessType) &&
        Objects.equals(this.accessId, agentV3.accessId) &&
        Objects.equals(this.tokenLimit, agentV3.tokenLimit) &&
        Objects.equals(this.totalTokens, agentV3.totalTokens) &&
        Objects.equals(this.usedTokens, agentV3.usedTokens) &&
        Objects.equals(this.remainingTokens, agentV3.remainingTokens) &&
        Objects.equals(this.tokenPackageId, agentV3.tokenPackageId) &&
        Objects.equals(this.subscriptionRenewalDate, agentV3.subscriptionRenewalDate) &&
        Objects.equals(this.autoBuyAdditionalTokens, agentV3.autoBuyAdditionalTokens) &&
        Objects.equals(this.tools, agentV3.tools) &&
        Objects.equals(this.additionalModelIds, agentV3.additionalModelIds) &&
        Objects.equals(this.additionalModels, agentV3.additionalModels) &&
        Objects.equals(this.isWebSearchEnabled, agentV3.isWebSearchEnabled) &&
        Objects.equals(this.aiGatewayKeyId, agentV3.aiGatewayKeyId) &&
        Objects.equals(this.createdAt, agentV3.createdAt) &&
        Objects.equals(this.knowledgeBases, agentV3.knowledgeBases);
  }

  private static <T> boolean equalsNullable(JsonNullable<T> a, JsonNullable<T> b) {
    return a == b || (a != null && b != null && a.isPresent() && b.isPresent() && Objects.deepEquals(a.get(), b.get()));
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, name, description, projectId, modelId, providerId, settings, status, accessType, accessId, tokenLimit, totalTokens, usedTokens, remainingTokens, tokenPackageId, subscriptionRenewalDate, autoBuyAdditionalTokens, tools, additionalModelIds, additionalModels, isWebSearchEnabled, aiGatewayKeyId, createdAt, knowledgeBases);
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
    sb.append("class AgentV3 {\n");
    sb.append("    id: ").append(toIndentedString(id)).append("\n");
    sb.append("    name: ").append(toIndentedString(name)).append("\n");
    sb.append("    description: ").append(toIndentedString(description)).append("\n");
    sb.append("    projectId: ").append(toIndentedString(projectId)).append("\n");
    sb.append("    modelId: ").append(toIndentedString(modelId)).append("\n");
    sb.append("    providerId: ").append(toIndentedString(providerId)).append("\n");
    sb.append("    settings: ").append(toIndentedString(settings)).append("\n");
    sb.append("    status: ").append(toIndentedString(status)).append("\n");
    sb.append("    accessType: ").append(toIndentedString(accessType)).append("\n");
    sb.append("    accessId: ").append(toIndentedString(accessId)).append("\n");
    sb.append("    tokenLimit: ").append(toIndentedString(tokenLimit)).append("\n");
    sb.append("    totalTokens: ").append(toIndentedString(totalTokens)).append("\n");
    sb.append("    usedTokens: ").append(toIndentedString(usedTokens)).append("\n");
    sb.append("    remainingTokens: ").append(toIndentedString(remainingTokens)).append("\n");
    sb.append("    tokenPackageId: ").append(toIndentedString(tokenPackageId)).append("\n");
    sb.append("    subscriptionRenewalDate: ").append(toIndentedString(subscriptionRenewalDate)).append("\n");
    sb.append("    autoBuyAdditionalTokens: ").append(toIndentedString(autoBuyAdditionalTokens)).append("\n");
    sb.append("    tools: ").append(toIndentedString(tools)).append("\n");
    sb.append("    additionalModelIds: ").append(toIndentedString(additionalModelIds)).append("\n");
    sb.append("    additionalModels: ").append(toIndentedString(additionalModels)).append("\n");
    sb.append("    isWebSearchEnabled: ").append(toIndentedString(isWebSearchEnabled)).append("\n");
    sb.append("    aiGatewayKeyId: ").append(toIndentedString(aiGatewayKeyId)).append("\n");
    sb.append("    createdAt: ").append(toIndentedString(createdAt)).append("\n");
    sb.append("    knowledgeBases: ").append(toIndentedString(knowledgeBases)).append("\n");
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
    openapiFields.add("description");
    openapiFields.add("project_id");
    openapiFields.add("model_id");
    openapiFields.add("provider_id");
    openapiFields.add("settings");
    openapiFields.add("status");
    openapiFields.add("access_type");
    openapiFields.add("access_id");
    openapiFields.add("token_limit");
    openapiFields.add("total_tokens");
    openapiFields.add("used_tokens");
    openapiFields.add("remaining_tokens");
    openapiFields.add("token_package_id");
    openapiFields.add("subscription_renewal_date");
    openapiFields.add("auto_buy_additional_tokens");
    openapiFields.add("tools");
    openapiFields.add("additional_model_ids");
    openapiFields.add("additional_models");
    openapiFields.add("is_web_search_enabled");
    openapiFields.add("ai_gateway_key_id");
    openapiFields.add("created_at");
    openapiFields.add("knowledge_bases");

    // a set of required properties/fields (JSON key names)
    openapiRequiredFields = new HashSet<String>();
    openapiRequiredFields.add("id");
    openapiRequiredFields.add("name");
    openapiRequiredFields.add("description");
    openapiRequiredFields.add("model_id");
    openapiRequiredFields.add("provider_id");
    openapiRequiredFields.add("settings");
    openapiRequiredFields.add("status");
    openapiRequiredFields.add("access_type");
    openapiRequiredFields.add("access_id");
    openapiRequiredFields.add("token_limit");
    openapiRequiredFields.add("used_tokens");
    openapiRequiredFields.add("auto_buy_additional_tokens");
    openapiRequiredFields.add("tools");
    openapiRequiredFields.add("additional_model_ids");
    openapiRequiredFields.add("additional_models");
    openapiRequiredFields.add("is_web_search_enabled");
    openapiRequiredFields.add("created_at");
    openapiRequiredFields.add("knowledge_bases");
  }

 /**
  * Validates the JSON Element and throws an exception if issues found
  *
  * @param jsonElement JSON Element
  * @throws IOException if the JSON Element is invalid with respect to AgentV3
  */
  public static void validateJsonElement(JsonElement jsonElement) throws IOException {
      if (jsonElement == null) {
        if (!AgentV3.openapiRequiredFields.isEmpty()) { // has required fields but JSON element is null
          throw new IllegalArgumentException(String.format("The required field(s) %s in AgentV3 is not found in the empty JSON string", AgentV3.openapiRequiredFields.toString()));
        }
      }

      Set<Entry<String, JsonElement>> entries = jsonElement.getAsJsonObject().entrySet();
      // check to see if the JSON string contains additional fields
      for (Entry<String, JsonElement> entry : entries) {
        if (!AgentV3.openapiFields.contains(entry.getKey())) {
          throw new IllegalArgumentException(String.format("The field `%s` in the JSON string is not defined in the `AgentV3` properties. JSON: %s", entry.getKey(), jsonElement.toString()));
        }
      }

      // check to make sure all required properties/fields are present in the JSON string
      for (String requiredField : AgentV3.openapiRequiredFields) {
        if (jsonElement.getAsJsonObject().get(requiredField) == null) {
          throw new IllegalArgumentException(String.format("The required field `%s` is not found in the JSON string: %s", requiredField, jsonElement.toString()));
        }
      }
        JsonObject jsonObj = jsonElement.getAsJsonObject();
      if (!jsonObj.get("name").isJsonPrimitive()) {
        throw new IllegalArgumentException(String.format("Expected the field `name` to be a primitive type in the JSON string but got `%s`", jsonObj.get("name").toString()));
      }
      if ((jsonObj.get("description") != null && !jsonObj.get("description").isJsonNull()) && !jsonObj.get("description").isJsonPrimitive()) {
        throw new IllegalArgumentException(String.format("Expected the field `description` to be a primitive type in the JSON string but got `%s`", jsonObj.get("description").toString()));
      }
      // validate the required field `settings`
      AgentSettings.validateJsonElement(jsonObj.get("settings"));
      if (!jsonObj.get("status").isJsonPrimitive()) {
        throw new IllegalArgumentException(String.format("Expected the field `status` to be a primitive type in the JSON string but got `%s`", jsonObj.get("status").toString()));
      }
      if (!jsonObj.get("access_type").isJsonPrimitive()) {
        throw new IllegalArgumentException(String.format("Expected the field `access_type` to be a primitive type in the JSON string but got `%s`", jsonObj.get("access_type").toString()));
      }
      if (!jsonObj.get("access_id").isJsonPrimitive()) {
        throw new IllegalArgumentException(String.format("Expected the field `access_id` to be a primitive type in the JSON string but got `%s`", jsonObj.get("access_id").toString()));
      }
      // ensure the json data is an array
      if (!jsonObj.get("tools").isJsonArray()) {
        throw new IllegalArgumentException(String.format("Expected the field `tools` to be an array in the JSON string but got `%s`", jsonObj.get("tools").toString()));
      }

      JsonArray jsonArraytools = jsonObj.getAsJsonArray("tools");
      // validate the required field `tools` (array)
      for (int i = 0; i < jsonArraytools.size(); i++) {
        AgentToolsInner.validateJsonElement(jsonArraytools.get(i));
      };
      // ensure the required json array is present
      if (jsonObj.get("additional_model_ids") == null) {
        throw new IllegalArgumentException("Expected the field `linkedContent` to be an array in the JSON string but got `null`");
      } else if (!jsonObj.get("additional_model_ids").isJsonArray()) {
        throw new IllegalArgumentException(String.format("Expected the field `additional_model_ids` to be an array in the JSON string but got `%s`", jsonObj.get("additional_model_ids").toString()));
      }
      // ensure the json data is an array
      if (!jsonObj.get("additional_models").isJsonArray()) {
        throw new IllegalArgumentException(String.format("Expected the field `additional_models` to be an array in the JSON string but got `%s`", jsonObj.get("additional_models").toString()));
      }

      JsonArray jsonArrayadditionalModels = jsonObj.getAsJsonArray("additional_models");
      // validate the required field `additional_models` (array)
      for (int i = 0; i < jsonArrayadditionalModels.size(); i++) {
        AgentAdditionalModel.validateJsonElement(jsonArrayadditionalModels.get(i));
      };
      if ((jsonObj.get("ai_gateway_key_id") != null && !jsonObj.get("ai_gateway_key_id").isJsonNull()) && !jsonObj.get("ai_gateway_key_id").isJsonPrimitive()) {
        throw new IllegalArgumentException(String.format("Expected the field `ai_gateway_key_id` to be a primitive type in the JSON string but got `%s`", jsonObj.get("ai_gateway_key_id").toString()));
      }
      // ensure the json data is an array
      if (!jsonObj.get("knowledge_bases").isJsonArray()) {
        throw new IllegalArgumentException(String.format("Expected the field `knowledge_bases` to be an array in the JSON string but got `%s`", jsonObj.get("knowledge_bases").toString()));
      }

      JsonArray jsonArrayknowledgeBases = jsonObj.getAsJsonArray("knowledge_bases");
      // validate the required field `knowledge_bases` (array)
      for (int i = 0; i < jsonArrayknowledgeBases.size(); i++) {
        AgentV3KnowledgeBasesInner.validateJsonElement(jsonArrayknowledgeBases.get(i));
      };
  }

  public static class CustomTypeAdapterFactory implements TypeAdapterFactory {
    @SuppressWarnings("unchecked")
    @Override
    public <T> TypeAdapter<T> create(Gson gson, TypeToken<T> type) {
       if (!AgentV3.class.isAssignableFrom(type.getRawType())) {
         return null; // this class only serializes 'AgentV3' and its subtypes
       }
       final TypeAdapter<JsonElement> elementAdapter = gson.getAdapter(JsonElement.class);
       final TypeAdapter<AgentV3> thisAdapter
                        = gson.getDelegateAdapter(this, TypeToken.get(AgentV3.class));

       return (TypeAdapter<T>) new TypeAdapter<AgentV3>() {
           @Override
           public void write(JsonWriter out, AgentV3 value) throws IOException {
             JsonObject obj = thisAdapter.toJsonTree(value).getAsJsonObject();
             elementAdapter.write(out, obj);
           }

           @Override
           public AgentV3 read(JsonReader in) throws IOException {
             JsonElement jsonElement = elementAdapter.read(in);
             validateJsonElement(jsonElement);
             return thisAdapter.fromJsonTree(jsonElement);
           }

       }.nullSafe();
    }
  }

 /**
  * Create an instance of AgentV3 given an JSON string
  *
  * @param jsonString JSON string
  * @return An instance of AgentV3
  * @throws IOException if the JSON string is invalid with respect to AgentV3
  */
  public static AgentV3 fromJson(String jsonString) throws IOException {
    return JSON.getGson().fromJson(jsonString, AgentV3.class);
  }

 /**
  * Convert an instance of AgentV3 to an JSON string
  *
  * @return JSON string
  */
  public String toJson() {
    return JSON.getGson().toJson(this);
  }
}

