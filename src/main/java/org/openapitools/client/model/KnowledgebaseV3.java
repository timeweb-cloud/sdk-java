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
import org.openapitools.client.model.RetrievalModeRef;

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
 * База знаний (v3): количество документов и привязанные агенты с режимом поиска
 */
@javax.annotation.Generated(value = "org.openapitools.codegen.languages.JavaClientCodegen", date = "2026-10-23T14:06:46.753006Z[Etc/UTC]")
public class KnowledgebaseV3 {
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

  public static final String SERIALIZED_NAME_DBAAS_ID = "dbaas_id";
  @SerializedName(SERIALIZED_NAME_DBAAS_ID)
  private Integer dbaasId;

  /**
   * Статус базы знаний: - &#x60;creating&#x60; — создаётся кластер базы данных, документы будут проиндексированы после перехода в &#x60;active&#x60; - &#x60;active&#x60; — база знаний работает - &#x60;blocked&#x60; — заблокирована: закончились токены или не удалось продлить подписку - &#x60;deleted&#x60; — удалена
   */
  @JsonAdapter(StatusEnum.Adapter.class)
  public enum StatusEnum {
    ACTIVE("active"),
    
    BLOCKED("blocked"),
    
    CREATING("creating"),
    
    DELETED("deleted");

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

  public static final String SERIALIZED_NAME_LAST_SYNC = "last_sync";
  @SerializedName(SERIALIZED_NAME_LAST_SYNC)
  private OffsetDateTime lastSync;

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

  public static final String SERIALIZED_NAME_DOCUMENTS_COUNT = "documents_count";
  @SerializedName(SERIALIZED_NAME_DOCUMENTS_COUNT)
  private Integer documentsCount;

  public static final String SERIALIZED_NAME_AGENTS = "agents";
  @SerializedName(SERIALIZED_NAME_AGENTS)
  private List<RetrievalModeRef> agents = new ArrayList<>();

  public static final String SERIALIZED_NAME_CREATED_AT = "created_at";
  @SerializedName(SERIALIZED_NAME_CREATED_AT)
  private OffsetDateTime createdAt;

  public KnowledgebaseV3() {
  }

  public KnowledgebaseV3 id(Integer id) {
    
    this.id = id;
    return this;
  }

   /**
   * ID базы знаний
   * @return id
  **/
  @javax.annotation.Nonnull
  public Integer getId() {
    return id;
  }


  public void setId(Integer id) {
    this.id = id;
  }


  public KnowledgebaseV3 name(String name) {
    
    this.name = name;
    return this;
  }

   /**
   * Название базы знаний
   * @return name
  **/
  @javax.annotation.Nonnull
  public String getName() {
    return name;
  }


  public void setName(String name) {
    this.name = name;
  }


  public KnowledgebaseV3 description(String description) {
    
    this.description = description;
    return this;
  }

   /**
   * Описание базы знаний
   * @return description
  **/
  @javax.annotation.Nullable
  public String getDescription() {
    return description;
  }


  public void setDescription(String description) {
    this.description = description;
  }


  public KnowledgebaseV3 projectId(Integer projectId) {
    
    this.projectId = projectId;
    return this;
  }

   /**
   * ID проекта, в котором находится база знаний. Поля нет, если проект не определён
   * @return projectId
  **/
  @javax.annotation.Nullable
  public Integer getProjectId() {
    return projectId;
  }


  public void setProjectId(Integer projectId) {
    this.projectId = projectId;
  }


  public KnowledgebaseV3 dbaasId(Integer dbaasId) {
    
    this.dbaasId = dbaasId;
    return this;
  }

   /**
   * ID кластера базы данных OpenSearch, который создан для базы знаний в вашем аккаунте
   * @return dbaasId
  **/
  @javax.annotation.Nonnull
  public Integer getDbaasId() {
    return dbaasId;
  }


  public void setDbaasId(Integer dbaasId) {
    this.dbaasId = dbaasId;
  }


  public KnowledgebaseV3 status(StatusEnum status) {
    
    this.status = status;
    return this;
  }

   /**
   * Статус базы знаний: - &#x60;creating&#x60; — создаётся кластер базы данных, документы будут проиндексированы после перехода в &#x60;active&#x60; - &#x60;active&#x60; — база знаний работает - &#x60;blocked&#x60; — заблокирована: закончились токены или не удалось продлить подписку - &#x60;deleted&#x60; — удалена
   * @return status
  **/
  @javax.annotation.Nonnull
  public StatusEnum getStatus() {
    return status;
  }


  public void setStatus(StatusEnum status) {
    this.status = status;
  }


  public KnowledgebaseV3 lastSync(OffsetDateTime lastSync) {
    
    this.lastSync = lastSync;
    return this;
  }

   /**
   * Дата и время последней синхронизации. &#x60;null&#x60;, если синхронизации не было
   * @return lastSync
  **/
  @javax.annotation.Nullable
  public OffsetDateTime getLastSync() {
    return lastSync;
  }


  public void setLastSync(OffsetDateTime lastSync) {
    this.lastSync = lastSync;
  }


  public KnowledgebaseV3 totalTokens(Integer totalTokens) {
    
    this.totalTokens = totalTokens;
    return this;
  }

   /**
   * Количество токенов, доступных в текущем периоде подписки (пакет подписки и докупленные пакеты)
   * @return totalTokens
  **/
  @javax.annotation.Nonnull
  public Integer getTotalTokens() {
    return totalTokens;
  }


  public void setTotalTokens(Integer totalTokens) {
    this.totalTokens = totalTokens;
  }


  public KnowledgebaseV3 usedTokens(Integer usedTokens) {
    
    this.usedTokens = usedTokens;
    return this;
  }

   /**
   * Количество израсходованных токенов
   * @return usedTokens
  **/
  @javax.annotation.Nonnull
  public Integer getUsedTokens() {
    return usedTokens;
  }


  public void setUsedTokens(Integer usedTokens) {
    this.usedTokens = usedTokens;
  }


  public KnowledgebaseV3 remainingTokens(Integer remainingTokens) {
    
    this.remainingTokens = remainingTokens;
    return this;
  }

   /**
   * Количество оставшихся токенов
   * @return remainingTokens
  **/
  @javax.annotation.Nonnull
  public Integer getRemainingTokens() {
    return remainingTokens;
  }


  public void setRemainingTokens(Integer remainingTokens) {
    this.remainingTokens = remainingTokens;
  }


  public KnowledgebaseV3 tokenPackageId(Integer tokenPackageId) {
    
    this.tokenPackageId = tokenPackageId;
    return this;
  }

   /**
   * ID пакета токенов подписки базы знаний
   * @return tokenPackageId
  **/
  @javax.annotation.Nonnull
  public Integer getTokenPackageId() {
    return tokenPackageId;
  }


  public void setTokenPackageId(Integer tokenPackageId) {
    this.tokenPackageId = tokenPackageId;
  }


  public KnowledgebaseV3 subscriptionRenewalDate(OffsetDateTime subscriptionRenewalDate) {
    
    this.subscriptionRenewalDate = subscriptionRenewalDate;
    return this;
  }

   /**
   * Дата следующего продления подписки (списания стоимости пакета токенов)
   * @return subscriptionRenewalDate
  **/
  @javax.annotation.Nullable
  public OffsetDateTime getSubscriptionRenewalDate() {
    return subscriptionRenewalDate;
  }


  public void setSubscriptionRenewalDate(OffsetDateTime subscriptionRenewalDate) {
    this.subscriptionRenewalDate = subscriptionRenewalDate;
  }


  public KnowledgebaseV3 documentsCount(Integer documentsCount) {
    
    this.documentsCount = documentsCount;
    return this;
  }

   /**
   * Количество документов в базе знаний
   * @return documentsCount
  **/
  @javax.annotation.Nonnull
  public Integer getDocumentsCount() {
    return documentsCount;
  }


  public void setDocumentsCount(Integer documentsCount) {
    this.documentsCount = documentsCount;
  }


  public KnowledgebaseV3 agents(List<RetrievalModeRef> agents) {
    
    this.agents = agents;
    return this;
  }

  public KnowledgebaseV3 addAgentsItem(RetrievalModeRef agentsItem) {
    if (this.agents == null) {
      this.agents = new ArrayList<>();
    }
    this.agents.add(agentsItem);
    return this;
  }

   /**
   * Агенты, к которым привязана база знаний, и режим поиска для каждого
   * @return agents
  **/
  @javax.annotation.Nonnull
  public List<RetrievalModeRef> getAgents() {
    return agents;
  }


  public void setAgents(List<RetrievalModeRef> agents) {
    this.agents = agents;
  }


  public KnowledgebaseV3 createdAt(OffsetDateTime createdAt) {
    
    this.createdAt = createdAt;
    return this;
  }

   /**
   * Дата и время создания базы знаний
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
    KnowledgebaseV3 knowledgebaseV3 = (KnowledgebaseV3) o;
    return Objects.equals(this.id, knowledgebaseV3.id) &&
        Objects.equals(this.name, knowledgebaseV3.name) &&
        Objects.equals(this.description, knowledgebaseV3.description) &&
        Objects.equals(this.projectId, knowledgebaseV3.projectId) &&
        Objects.equals(this.dbaasId, knowledgebaseV3.dbaasId) &&
        Objects.equals(this.status, knowledgebaseV3.status) &&
        Objects.equals(this.lastSync, knowledgebaseV3.lastSync) &&
        Objects.equals(this.totalTokens, knowledgebaseV3.totalTokens) &&
        Objects.equals(this.usedTokens, knowledgebaseV3.usedTokens) &&
        Objects.equals(this.remainingTokens, knowledgebaseV3.remainingTokens) &&
        Objects.equals(this.tokenPackageId, knowledgebaseV3.tokenPackageId) &&
        Objects.equals(this.subscriptionRenewalDate, knowledgebaseV3.subscriptionRenewalDate) &&
        Objects.equals(this.documentsCount, knowledgebaseV3.documentsCount) &&
        Objects.equals(this.agents, knowledgebaseV3.agents) &&
        Objects.equals(this.createdAt, knowledgebaseV3.createdAt);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, name, description, projectId, dbaasId, status, lastSync, totalTokens, usedTokens, remainingTokens, tokenPackageId, subscriptionRenewalDate, documentsCount, agents, createdAt);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class KnowledgebaseV3 {\n");
    sb.append("    id: ").append(toIndentedString(id)).append("\n");
    sb.append("    name: ").append(toIndentedString(name)).append("\n");
    sb.append("    description: ").append(toIndentedString(description)).append("\n");
    sb.append("    projectId: ").append(toIndentedString(projectId)).append("\n");
    sb.append("    dbaasId: ").append(toIndentedString(dbaasId)).append("\n");
    sb.append("    status: ").append(toIndentedString(status)).append("\n");
    sb.append("    lastSync: ").append(toIndentedString(lastSync)).append("\n");
    sb.append("    totalTokens: ").append(toIndentedString(totalTokens)).append("\n");
    sb.append("    usedTokens: ").append(toIndentedString(usedTokens)).append("\n");
    sb.append("    remainingTokens: ").append(toIndentedString(remainingTokens)).append("\n");
    sb.append("    tokenPackageId: ").append(toIndentedString(tokenPackageId)).append("\n");
    sb.append("    subscriptionRenewalDate: ").append(toIndentedString(subscriptionRenewalDate)).append("\n");
    sb.append("    documentsCount: ").append(toIndentedString(documentsCount)).append("\n");
    sb.append("    agents: ").append(toIndentedString(agents)).append("\n");
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
    openapiFields.add("project_id");
    openapiFields.add("dbaas_id");
    openapiFields.add("status");
    openapiFields.add("last_sync");
    openapiFields.add("total_tokens");
    openapiFields.add("used_tokens");
    openapiFields.add("remaining_tokens");
    openapiFields.add("token_package_id");
    openapiFields.add("subscription_renewal_date");
    openapiFields.add("documents_count");
    openapiFields.add("agents");
    openapiFields.add("created_at");

    // a set of required properties/fields (JSON key names)
    openapiRequiredFields = new HashSet<String>();
    openapiRequiredFields.add("id");
    openapiRequiredFields.add("name");
    openapiRequiredFields.add("description");
    openapiRequiredFields.add("dbaas_id");
    openapiRequiredFields.add("status");
    openapiRequiredFields.add("last_sync");
    openapiRequiredFields.add("total_tokens");
    openapiRequiredFields.add("used_tokens");
    openapiRequiredFields.add("remaining_tokens");
    openapiRequiredFields.add("token_package_id");
    openapiRequiredFields.add("subscription_renewal_date");
    openapiRequiredFields.add("documents_count");
    openapiRequiredFields.add("agents");
    openapiRequiredFields.add("created_at");
  }

 /**
  * Validates the JSON Element and throws an exception if issues found
  *
  * @param jsonElement JSON Element
  * @throws IOException if the JSON Element is invalid with respect to KnowledgebaseV3
  */
  public static void validateJsonElement(JsonElement jsonElement) throws IOException {
      if (jsonElement == null) {
        if (!KnowledgebaseV3.openapiRequiredFields.isEmpty()) { // has required fields but JSON element is null
          throw new IllegalArgumentException(String.format("The required field(s) %s in KnowledgebaseV3 is not found in the empty JSON string", KnowledgebaseV3.openapiRequiredFields.toString()));
        }
      }

      Set<Entry<String, JsonElement>> entries = jsonElement.getAsJsonObject().entrySet();
      // check to see if the JSON string contains additional fields
      for (Entry<String, JsonElement> entry : entries) {
        if (!KnowledgebaseV3.openapiFields.contains(entry.getKey())) {
          throw new IllegalArgumentException(String.format("The field `%s` in the JSON string is not defined in the `KnowledgebaseV3` properties. JSON: %s", entry.getKey(), jsonElement.toString()));
        }
      }

      // check to make sure all required properties/fields are present in the JSON string
      for (String requiredField : KnowledgebaseV3.openapiRequiredFields) {
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
      if (!jsonObj.get("status").isJsonPrimitive()) {
        throw new IllegalArgumentException(String.format("Expected the field `status` to be a primitive type in the JSON string but got `%s`", jsonObj.get("status").toString()));
      }
      // ensure the json data is an array
      if (!jsonObj.get("agents").isJsonArray()) {
        throw new IllegalArgumentException(String.format("Expected the field `agents` to be an array in the JSON string but got `%s`", jsonObj.get("agents").toString()));
      }

      JsonArray jsonArrayagents = jsonObj.getAsJsonArray("agents");
      // validate the required field `agents` (array)
      for (int i = 0; i < jsonArrayagents.size(); i++) {
        RetrievalModeRef.validateJsonElement(jsonArrayagents.get(i));
      };
  }

  public static class CustomTypeAdapterFactory implements TypeAdapterFactory {
    @SuppressWarnings("unchecked")
    @Override
    public <T> TypeAdapter<T> create(Gson gson, TypeToken<T> type) {
       if (!KnowledgebaseV3.class.isAssignableFrom(type.getRawType())) {
         return null; // this class only serializes 'KnowledgebaseV3' and its subtypes
       }
       final TypeAdapter<JsonElement> elementAdapter = gson.getAdapter(JsonElement.class);
       final TypeAdapter<KnowledgebaseV3> thisAdapter
                        = gson.getDelegateAdapter(this, TypeToken.get(KnowledgebaseV3.class));

       return (TypeAdapter<T>) new TypeAdapter<KnowledgebaseV3>() {
           @Override
           public void write(JsonWriter out, KnowledgebaseV3 value) throws IOException {
             JsonObject obj = thisAdapter.toJsonTree(value).getAsJsonObject();
             elementAdapter.write(out, obj);
           }

           @Override
           public KnowledgebaseV3 read(JsonReader in) throws IOException {
             JsonElement jsonElement = elementAdapter.read(in);
             validateJsonElement(jsonElement);
             return thisAdapter.fromJsonTree(jsonElement);
           }

       }.nullSafe();
    }
  }

 /**
  * Create an instance of KnowledgebaseV3 given an JSON string
  *
  * @param jsonString JSON string
  * @return An instance of KnowledgebaseV3
  * @throws IOException if the JSON string is invalid with respect to KnowledgebaseV3
  */
  public static KnowledgebaseV3 fromJson(String jsonString) throws IOException {
    return JSON.getGson().fromJson(jsonString, KnowledgebaseV3.class);
  }

 /**
  * Convert an instance of KnowledgebaseV3 to an JSON string
  *
  * @return JSON string
  */
  public String toJson() {
    return JSON.getGson().toJson(this);
  }
}

