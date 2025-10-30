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
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;
import org.openapitools.client.model.AgentSettings;

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
 * AI Agent
 */
@javax.annotation.Generated(value = "org.openapitools.codegen.languages.JavaClientCodegen", date = "2025-10-30T12:35:37.057389Z[Etc/UTC]")
public class Agent {
  public static final String SERIALIZED_NAME_ID = "id";
  @SerializedName(SERIALIZED_NAME_ID)
  private BigDecimal id;

  public static final String SERIALIZED_NAME_NAME = "name";
  @SerializedName(SERIALIZED_NAME_NAME)
  private String name;

  public static final String SERIALIZED_NAME_DESCRIPTION = "description";
  @SerializedName(SERIALIZED_NAME_DESCRIPTION)
  private String description;

  public static final String SERIALIZED_NAME_MODEL_ID = "model_id";
  @SerializedName(SERIALIZED_NAME_MODEL_ID)
  private BigDecimal modelId;

  public static final String SERIALIZED_NAME_PROVIDER_ID = "provider_id";
  @SerializedName(SERIALIZED_NAME_PROVIDER_ID)
  private BigDecimal providerId;

  public static final String SERIALIZED_NAME_SETTINGS = "settings";
  @SerializedName(SERIALIZED_NAME_SETTINGS)
  private AgentSettings settings;

  /**
   * Статус агента
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
   * Тип доступа к агенту
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

  public static final String SERIALIZED_NAME_TOTAL_TOKENS = "total_tokens";
  @SerializedName(SERIALIZED_NAME_TOTAL_TOKENS)
  private BigDecimal totalTokens;

  public static final String SERIALIZED_NAME_USED_TOKENS = "used_tokens";
  @SerializedName(SERIALIZED_NAME_USED_TOKENS)
  private BigDecimal usedTokens;

  public static final String SERIALIZED_NAME_REMAINING_TOKENS = "remaining_tokens";
  @SerializedName(SERIALIZED_NAME_REMAINING_TOKENS)
  private BigDecimal remainingTokens;

  public static final String SERIALIZED_NAME_TOKEN_PACKAGE_ID = "token_package_id";
  @SerializedName(SERIALIZED_NAME_TOKEN_PACKAGE_ID)
  private BigDecimal tokenPackageId;

  public static final String SERIALIZED_NAME_SUBSCRIPTION_RENEWAL_DATE = "subscription_renewal_date";
  @SerializedName(SERIALIZED_NAME_SUBSCRIPTION_RENEWAL_DATE)
  private OffsetDateTime subscriptionRenewalDate;

  public static final String SERIALIZED_NAME_KNOWLEDGE_BASES_IDS = "knowledge_bases_ids";
  @SerializedName(SERIALIZED_NAME_KNOWLEDGE_BASES_IDS)
  private List<BigDecimal> knowledgeBasesIds = new ArrayList<>();

  public static final String SERIALIZED_NAME_ACCESS_ID = "access_id";
  @SerializedName(SERIALIZED_NAME_ACCESS_ID)
  private BigDecimal accessId;

  public static final String SERIALIZED_NAME_CREATED_AT = "created_at";
  @SerializedName(SERIALIZED_NAME_CREATED_AT)
  private OffsetDateTime createdAt;

  public Agent() {
  }

  public Agent id(BigDecimal id) {
    
    this.id = id;
    return this;
  }

   /**
   * Уникальный идентификатор агента
   * @return id
  **/
  @javax.annotation.Nonnull
  public BigDecimal getId() {
    return id;
  }


  public void setId(BigDecimal id) {
    this.id = id;
  }


  public Agent name(String name) {
    
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


  public Agent description(String description) {
    
    this.description = description;
    return this;
  }

   /**
   * Описание агента
   * @return description
  **/
  @javax.annotation.Nonnull
  public String getDescription() {
    return description;
  }


  public void setDescription(String description) {
    this.description = description;
  }


  public Agent modelId(BigDecimal modelId) {
    
    this.modelId = modelId;
    return this;
  }

   /**
   * ID модели
   * @return modelId
  **/
  @javax.annotation.Nonnull
  public BigDecimal getModelId() {
    return modelId;
  }


  public void setModelId(BigDecimal modelId) {
    this.modelId = modelId;
  }


  public Agent providerId(BigDecimal providerId) {
    
    this.providerId = providerId;
    return this;
  }

   /**
   * ID провайдера
   * @return providerId
  **/
  @javax.annotation.Nonnull
  public BigDecimal getProviderId() {
    return providerId;
  }


  public void setProviderId(BigDecimal providerId) {
    this.providerId = providerId;
  }


  public Agent settings(AgentSettings settings) {
    
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


  public Agent status(StatusEnum status) {
    
    this.status = status;
    return this;
  }

   /**
   * Статус агента
   * @return status
  **/
  @javax.annotation.Nonnull
  public StatusEnum getStatus() {
    return status;
  }


  public void setStatus(StatusEnum status) {
    this.status = status;
  }


  public Agent accessType(AccessTypeEnum accessType) {
    
    this.accessType = accessType;
    return this;
  }

   /**
   * Тип доступа к агенту
   * @return accessType
  **/
  @javax.annotation.Nonnull
  public AccessTypeEnum getAccessType() {
    return accessType;
  }


  public void setAccessType(AccessTypeEnum accessType) {
    this.accessType = accessType;
  }


  public Agent totalTokens(BigDecimal totalTokens) {
    
    this.totalTokens = totalTokens;
    return this;
  }

   /**
   * Всего токенов выделено агенту
   * @return totalTokens
  **/
  @javax.annotation.Nonnull
  public BigDecimal getTotalTokens() {
    return totalTokens;
  }


  public void setTotalTokens(BigDecimal totalTokens) {
    this.totalTokens = totalTokens;
  }


  public Agent usedTokens(BigDecimal usedTokens) {
    
    this.usedTokens = usedTokens;
    return this;
  }

   /**
   * Использовано токенов
   * @return usedTokens
  **/
  @javax.annotation.Nonnull
  public BigDecimal getUsedTokens() {
    return usedTokens;
  }


  public void setUsedTokens(BigDecimal usedTokens) {
    this.usedTokens = usedTokens;
  }


  public Agent remainingTokens(BigDecimal remainingTokens) {
    
    this.remainingTokens = remainingTokens;
    return this;
  }

   /**
   * Осталось токенов
   * @return remainingTokens
  **/
  @javax.annotation.Nonnull
  public BigDecimal getRemainingTokens() {
    return remainingTokens;
  }


  public void setRemainingTokens(BigDecimal remainingTokens) {
    this.remainingTokens = remainingTokens;
  }


  public Agent tokenPackageId(BigDecimal tokenPackageId) {
    
    this.tokenPackageId = tokenPackageId;
    return this;
  }

   /**
   * ID пакета токенов
   * @return tokenPackageId
  **/
  @javax.annotation.Nonnull
  public BigDecimal getTokenPackageId() {
    return tokenPackageId;
  }


  public void setTokenPackageId(BigDecimal tokenPackageId) {
    this.tokenPackageId = tokenPackageId;
  }


  public Agent subscriptionRenewalDate(OffsetDateTime subscriptionRenewalDate) {
    
    this.subscriptionRenewalDate = subscriptionRenewalDate;
    return this;
  }

   /**
   * Дата обновления подписки
   * @return subscriptionRenewalDate
  **/
  @javax.annotation.Nonnull
  public OffsetDateTime getSubscriptionRenewalDate() {
    return subscriptionRenewalDate;
  }


  public void setSubscriptionRenewalDate(OffsetDateTime subscriptionRenewalDate) {
    this.subscriptionRenewalDate = subscriptionRenewalDate;
  }


  public Agent knowledgeBasesIds(List<BigDecimal> knowledgeBasesIds) {
    
    this.knowledgeBasesIds = knowledgeBasesIds;
    return this;
  }

  public Agent addKnowledgeBasesIdsItem(BigDecimal knowledgeBasesIdsItem) {
    if (this.knowledgeBasesIds == null) {
      this.knowledgeBasesIds = new ArrayList<>();
    }
    this.knowledgeBasesIds.add(knowledgeBasesIdsItem);
    return this;
  }

   /**
   * ID баз знаний, связанных с агентом
   * @return knowledgeBasesIds
  **/
  @javax.annotation.Nonnull
  public List<BigDecimal> getKnowledgeBasesIds() {
    return knowledgeBasesIds;
  }


  public void setKnowledgeBasesIds(List<BigDecimal> knowledgeBasesIds) {
    this.knowledgeBasesIds = knowledgeBasesIds;
  }


  public Agent accessId(BigDecimal accessId) {
    
    this.accessId = accessId;
    return this;
  }

   /**
   * ID доступа
   * @return accessId
  **/
  @javax.annotation.Nonnull
  public BigDecimal getAccessId() {
    return accessId;
  }


  public void setAccessId(BigDecimal accessId) {
    this.accessId = accessId;
  }


  public Agent createdAt(OffsetDateTime createdAt) {
    
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



  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Agent agent = (Agent) o;
    return Objects.equals(this.id, agent.id) &&
        Objects.equals(this.name, agent.name) &&
        Objects.equals(this.description, agent.description) &&
        Objects.equals(this.modelId, agent.modelId) &&
        Objects.equals(this.providerId, agent.providerId) &&
        Objects.equals(this.settings, agent.settings) &&
        Objects.equals(this.status, agent.status) &&
        Objects.equals(this.accessType, agent.accessType) &&
        Objects.equals(this.totalTokens, agent.totalTokens) &&
        Objects.equals(this.usedTokens, agent.usedTokens) &&
        Objects.equals(this.remainingTokens, agent.remainingTokens) &&
        Objects.equals(this.tokenPackageId, agent.tokenPackageId) &&
        Objects.equals(this.subscriptionRenewalDate, agent.subscriptionRenewalDate) &&
        Objects.equals(this.knowledgeBasesIds, agent.knowledgeBasesIds) &&
        Objects.equals(this.accessId, agent.accessId) &&
        Objects.equals(this.createdAt, agent.createdAt);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, name, description, modelId, providerId, settings, status, accessType, totalTokens, usedTokens, remainingTokens, tokenPackageId, subscriptionRenewalDate, knowledgeBasesIds, accessId, createdAt);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class Agent {\n");
    sb.append("    id: ").append(toIndentedString(id)).append("\n");
    sb.append("    name: ").append(toIndentedString(name)).append("\n");
    sb.append("    description: ").append(toIndentedString(description)).append("\n");
    sb.append("    modelId: ").append(toIndentedString(modelId)).append("\n");
    sb.append("    providerId: ").append(toIndentedString(providerId)).append("\n");
    sb.append("    settings: ").append(toIndentedString(settings)).append("\n");
    sb.append("    status: ").append(toIndentedString(status)).append("\n");
    sb.append("    accessType: ").append(toIndentedString(accessType)).append("\n");
    sb.append("    totalTokens: ").append(toIndentedString(totalTokens)).append("\n");
    sb.append("    usedTokens: ").append(toIndentedString(usedTokens)).append("\n");
    sb.append("    remainingTokens: ").append(toIndentedString(remainingTokens)).append("\n");
    sb.append("    tokenPackageId: ").append(toIndentedString(tokenPackageId)).append("\n");
    sb.append("    subscriptionRenewalDate: ").append(toIndentedString(subscriptionRenewalDate)).append("\n");
    sb.append("    knowledgeBasesIds: ").append(toIndentedString(knowledgeBasesIds)).append("\n");
    sb.append("    accessId: ").append(toIndentedString(accessId)).append("\n");
    sb.append("    createdAt: ").append(toIndentedString(createdAt)).append("\n");
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
    openapiFields.add("model_id");
    openapiFields.add("provider_id");
    openapiFields.add("settings");
    openapiFields.add("status");
    openapiFields.add("access_type");
    openapiFields.add("total_tokens");
    openapiFields.add("used_tokens");
    openapiFields.add("remaining_tokens");
    openapiFields.add("token_package_id");
    openapiFields.add("subscription_renewal_date");
    openapiFields.add("knowledge_bases_ids");
    openapiFields.add("access_id");
    openapiFields.add("created_at");

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
    openapiRequiredFields.add("total_tokens");
    openapiRequiredFields.add("used_tokens");
    openapiRequiredFields.add("remaining_tokens");
    openapiRequiredFields.add("token_package_id");
    openapiRequiredFields.add("subscription_renewal_date");
    openapiRequiredFields.add("knowledge_bases_ids");
    openapiRequiredFields.add("access_id");
    openapiRequiredFields.add("created_at");
  }

 /**
  * Validates the JSON Element and throws an exception if issues found
  *
  * @param jsonElement JSON Element
  * @throws IOException if the JSON Element is invalid with respect to Agent
  */
  public static void validateJsonElement(JsonElement jsonElement) throws IOException {
      if (jsonElement == null) {
        if (!Agent.openapiRequiredFields.isEmpty()) { // has required fields but JSON element is null
          throw new IllegalArgumentException(String.format("The required field(s) %s in Agent is not found in the empty JSON string", Agent.openapiRequiredFields.toString()));
        }
      }

      Set<Entry<String, JsonElement>> entries = jsonElement.getAsJsonObject().entrySet();
      // check to see if the JSON string contains additional fields
      for (Entry<String, JsonElement> entry : entries) {
        if (!Agent.openapiFields.contains(entry.getKey())) {
          throw new IllegalArgumentException(String.format("The field `%s` in the JSON string is not defined in the `Agent` properties. JSON: %s", entry.getKey(), jsonElement.toString()));
        }
      }

      // check to make sure all required properties/fields are present in the JSON string
      for (String requiredField : Agent.openapiRequiredFields) {
        if (jsonElement.getAsJsonObject().get(requiredField) == null) {
          throw new IllegalArgumentException(String.format("The required field `%s` is not found in the JSON string: %s", requiredField, jsonElement.toString()));
        }
      }
        JsonObject jsonObj = jsonElement.getAsJsonObject();
      if (!jsonObj.get("name").isJsonPrimitive()) {
        throw new IllegalArgumentException(String.format("Expected the field `name` to be a primitive type in the JSON string but got `%s`", jsonObj.get("name").toString()));
      }
      if (!jsonObj.get("description").isJsonPrimitive()) {
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
      // ensure the required json array is present
      if (jsonObj.get("knowledge_bases_ids") == null) {
        throw new IllegalArgumentException("Expected the field `linkedContent` to be an array in the JSON string but got `null`");
      } else if (!jsonObj.get("knowledge_bases_ids").isJsonArray()) {
        throw new IllegalArgumentException(String.format("Expected the field `knowledge_bases_ids` to be an array in the JSON string but got `%s`", jsonObj.get("knowledge_bases_ids").toString()));
      }
  }

  public static class CustomTypeAdapterFactory implements TypeAdapterFactory {
    @SuppressWarnings("unchecked")
    @Override
    public <T> TypeAdapter<T> create(Gson gson, TypeToken<T> type) {
       if (!Agent.class.isAssignableFrom(type.getRawType())) {
         return null; // this class only serializes 'Agent' and its subtypes
       }
       final TypeAdapter<JsonElement> elementAdapter = gson.getAdapter(JsonElement.class);
       final TypeAdapter<Agent> thisAdapter
                        = gson.getDelegateAdapter(this, TypeToken.get(Agent.class));

       return (TypeAdapter<T>) new TypeAdapter<Agent>() {
           @Override
           public void write(JsonWriter out, Agent value) throws IOException {
             JsonObject obj = thisAdapter.toJsonTree(value).getAsJsonObject();
             elementAdapter.write(out, obj);
           }

           @Override
           public Agent read(JsonReader in) throws IOException {
             JsonElement jsonElement = elementAdapter.read(in);
             validateJsonElement(jsonElement);
             return thisAdapter.fromJsonTree(jsonElement);
           }

       }.nullSafe();
    }
  }

 /**
  * Create an instance of Agent given an JSON string
  *
  * @param jsonString JSON string
  * @return An instance of Agent
  * @throws IOException if the JSON string is invalid with respect to Agent
  */
  public static Agent fromJson(String jsonString) throws IOException {
    return JSON.getGson().fromJson(jsonString, Agent.class);
  }

 /**
  * Convert an instance of Agent to an JSON string
  *
  * @return JSON string
  */
  public String toJson() {
    return JSON.getGson().toJson(this);
  }
}

