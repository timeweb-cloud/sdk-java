/*
 * Timeweb Cloud API
 * # Введение API Timeweb Cloud позволяет вам управлять ресурсами в облаке программным способом с использованием обычных HTTP-запросов.  Множество функций, которые доступны в панели управления Timeweb Cloud, также доступны через API, что позволяет вам автоматизировать ваши собственные сценарии.  В этой документации сперва будет описан общий дизайн и принципы работы API, а после этого конкретные конечные точки. Также будут приведены примеры запросов к ним.   ## Запросы Запросы должны выполняться по протоколу `HTTPS`, чтобы гарантировать шифрование транзакций. Поддерживаются следующие методы запроса: |Метод|Применение| |--- |--- | |GET|Извлекает данные о коллекциях и отдельных ресурсах.| |POST|Для коллекций создает новый ресурс этого типа. Также используется для выполнения действий с конкретным ресурсом.| |PUT|Обновляет существующий ресурс.| |PATCH|Некоторые ресурсы поддерживают частичное обновление, то есть обновление только части атрибутов ресурса, в этом случае вместо метода PUT будет использован PATCH.| |DELETE|Удаляет ресурс.|  Методы `POST`, `PUT` и `PATCH` могут включать объект в тело запроса с типом содержимого `application/json`.  ### Параметры в запросах Некоторые коллекции поддерживают пагинацию, поиск или сортировку в запросах. В параметрах запроса требуется передать: - `limit` — обозначает количество записей, которое необходимо вернуть  - `offset` — указывает на смещение, относительно начала списка  - `search` — позволяет указать набор символов для поиска  - `sort` — можно задать правило сортировки коллекции  ## Ответы Запросы вернут один из следующих кодов состояния ответа HTTP:  |Статус|Описание| |--- |--- | |200 OK|Действие с ресурсом было выполнено успешно.| |201 Created|Ресурс был успешно создан. При этом ресурс может быть как уже готовым к использованию, так и находиться в процессе запуска.| |204 No Content|Действие с ресурсом было выполнено успешно, и ответ не содержит дополнительной информации в теле.| |400 Bad Request|Был отправлен неверный запрос, например, в нем отсутствуют обязательные параметры и т. д. Тело ответа будет содержать дополнительную информацию об ошибке.| |401 Unauthorized|Ошибка аутентификации.| |403 Forbidden|Аутентификация прошла успешно, но недостаточно прав для выполнения действия.| |404 Not Found|Запрашиваемый ресурс не найден.| |409 Conflict|Запрос конфликтует с текущим состоянием.| |423 Locked|Ресурс из запроса заблокирован от применения к нему указанного метода.| |429 Too Many Requests|Был достигнут лимит по количеству запросов в единицу времени.| |500 Internal Server Error|При выполнении запроса произошла какая-то внутренняя ошибка. Чтобы решить эту проблему, лучше всего создать тикет в панели управления.|  ### Структура успешного ответа Все конечные точки будут возвращать данные в формате `JSON`. Ответы на `GET`-запросы будут иметь на верхнем уровне следующую структуру атрибутов:  |Название поля|Тип|Описание| |--- |--- |--- | |[entity_name]|object, object[], string[], number[], boolean|Динамическое поле, которое будет меняться в зависимости от запрашиваемого ресурса и будет содержать все атрибуты, необходимые для описания этого ресурса. Например, при запросе списка баз данных будет возвращаться поле `dbs`, а при запросе конкретного облачного сервера `server`. Для некоторых конечных точек в ответе может возвращаться сразу несколько ресурсов.| |meta|object|Опционально. Объект, который содержит вспомогательную информацию о ресурсе. Чаще всего будет встречаться при запросе коллекций и содержать поле `total`, которое будет указывать на количество элементов в коллекции.| |response_id|string|Опционально. В большинстве случаев в ответе будет содержаться уникальный идентификатор ответа в формате UUIDv4, который однозначно указывает на ваш запрос внутри нашей системы. Если вам потребуется задать вопрос нашей поддержке, приложите к вопросу этот идентификатор — так мы сможем найти ответ на него намного быстрее. Также вы можете использовать этот идентификатор, чтобы убедиться, что это новый ответ на запрос и результат не был получен из кэша.|  Пример запроса на получение списка SSH-ключей: ```     HTTP/2.0 200 OK     {       \"ssh_keys\":[           {             \"body\":\"ssh-rsa AAAAB3NzaC1sdfghjkOAsBwWhs= example@device.local\",             \"created_at\":\"2021-09-15T19:52:27Z\",             \"expired_at\":null,             \"id\":5297,             \"is_default\":false,             \"name\":\"example@device.local\",             \"used_at\":null,             \"used_by\":[]           }       ],       \"meta\":{           \"total\":1       },       \"response_id\":\"94608d15-8672-4eed-8ab6-28bd6fa3cdf7\"     } ```  ### Структура ответа с ошибкой |Название поля|Тип|Описание| |--- |--- |--- | |status_code|number|Короткий числовой идентификатор ошибки.| |error_code|string|Короткий текстовый идентификатор ошибки, который уточняет числовой идентификатор и удобен для программной обработки. Самый простой пример — это код `not_found` для ошибки 404.| |message|string, string[]|Опционально. В большинстве случаев в ответе будет содержаться человекочитаемое подробное описание ошибки или ошибок, которые помогут понять, что нужно исправить.| |response_id|string|Опционально. В большинстве случае в ответе будет содержаться уникальный идентификатор ответа в формате UUIDv4, который однозначно указывает на ваш запрос внутри нашей системы. Если вам потребуется задать вопрос нашей поддержке, приложите к вопросу этот идентификатор — так мы сможем найти ответ на него намного быстрее.|  Пример: ```     HTTP/2.0 403 Forbidden     {       \"status_code\": 403,       \"error_code\":  \"forbidden\",       \"message\":     \"You do not have access for the attempted action\",       \"response_id\": \"94608d15-8672-4eed-8ab6-28bd6fa3cdf7\"     } ```  ## Статусы ресурсов Важно учесть, что при создании большинства ресурсов внутри платформы вам будет сразу возвращен ответ от сервера со статусом `200 OK` или `201 Created` и идентификатором созданного ресурса в теле ответа, но при этом этот ресурс может быть ещё в *состоянии запуска*.  Для того чтобы понять, в каком состоянии сейчас находится ваш ресурс, мы добавили поле `status` в ответ на получение информации о ресурсе.  Список статусов будет отличаться в зависимости от типа ресурса. Увидеть поддерживаемый список статусов вы сможете в описании каждого конкретного ресурса.     ## Ограничение скорости запросов (Rate Limiting) Чтобы обеспечить стабильность для всех пользователей, Timeweb Cloud защищает API от всплесков входящего трафика, анализируя количество запросов c каждого аккаунта к каждой конечной точке.  Если ваше приложение отправляет более 20 запросов в секунду на одну конечную точку, то для этого запроса API может вернуть код состояния HTTP `429 Too Many Requests`.   ## Аутентификация Доступ к API осуществляется с помощью JWT-токена. Токенами можно управлять внутри панели управления Timeweb Cloud в разделе *API и Terraform*.  Токен необходимо передавать в заголовке каждого запроса в формате: ```   Authorization: Bearer $TIMEWEB_CLOUD_TOKEN ```  ## Формат примеров API Примеры в этой документации описаны с помощью `curl`, HTTP-клиента командной строки. На компьютерах `Linux` и `macOS` обычно по умолчанию установлен `curl`, и он доступен для загрузки на всех популярных платформах, включая `Windows`.  Каждый пример разделен на несколько строк символом `\\`, который совместим с `bash`. Типичный пример выглядит так: ```   curl -X PATCH      -H \"Content-Type: application/json\"      -H \"Authorization: Bearer $TIMEWEB_CLOUD_TOKEN\"      -d '{\"name\":\"Cute Corvus\",\"comment\":\"Development Server\"}'      \"https://api.timeweb.cloud/api/v1/dedicated/1051\" ``` - Параметр `-X` задает метод запроса. Для согласованности метод будет указан во всех примерах, даже если он явно не требуется для методов `GET`. - Строки `-H` задают требуемые HTTP-заголовки. - Примеры, для которых требуется объект JSON в теле запроса, передают требуемые данные через параметр `-d`.  Чтобы использовать приведенные примеры, не подставляя каждый раз в них свой токен, вы можете добавить токен один раз в переменные окружения в вашей консоли. Например, на `Linux` это можно сделать с помощью команды:  ``` TIMEWEB_CLOUD_TOKEN=\"token\" ```  После этого токен будет автоматически подставляться в ваши запросы.  Обратите внимание, что все значения в этой документации являются примерами. Не полагайтесь на идентификаторы операционных систем, тарифов и т.д., используемые в примерах. Используйте соответствующую конечную точку для получения значений перед созданием ресурсов.   ## Версионирование API построено согласно принципам [семантического версионирования](https://semver.org/lang/ru). Это значит, что мы гарантируем обратную совместимость всех изменений в пределах одной мажорной версии.  Мажорная версия каждой конечной точки обозначается в пути запроса, например, запрос `/api/v1/servers` указывает, что этот метод имеет версию 1.
 *
 * The version of the OpenAPI document: 
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
import org.openapitools.client.model.DomainPaymentPeriod;
import org.openapitools.client.model.DomainPrimeType;

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
 * Заявка на продление/регистрацию/трансфер домена.
 */
@javax.annotation.Generated(value = "org.openapitools.codegen.languages.JavaClientCodegen", date = "2023-08-22T13:30:15.518956Z[Etc/UTC]")
public class DomainRequest {
  public static final String SERIALIZED_NAME_ACCOUNT_ID = "account_id";
  @SerializedName(SERIALIZED_NAME_ACCOUNT_ID)
  private String accountId;

  public static final String SERIALIZED_NAME_AUTH_CODE = "auth_code";
  @SerializedName(SERIALIZED_NAME_AUTH_CODE)
  private String authCode;

  public static final String SERIALIZED_NAME_DATE = "date";
  @SerializedName(SERIALIZED_NAME_DATE)
  private OffsetDateTime date;

  public static final String SERIALIZED_NAME_DOMAIN_BUNDLE_ID = "domain_bundle_id";
  @SerializedName(SERIALIZED_NAME_DOMAIN_BUNDLE_ID)
  private String domainBundleId;

  public static final String SERIALIZED_NAME_ERROR_CODE_TRANSFER = "error_code_transfer";
  @SerializedName(SERIALIZED_NAME_ERROR_CODE_TRANSFER)
  private String errorCodeTransfer;

  public static final String SERIALIZED_NAME_FQDN = "fqdn";
  @SerializedName(SERIALIZED_NAME_FQDN)
  private String fqdn;

  public static final String SERIALIZED_NAME_GROUP_ID = "group_id";
  @SerializedName(SERIALIZED_NAME_GROUP_ID)
  private BigDecimal groupId;

  public static final String SERIALIZED_NAME_ID = "id";
  @SerializedName(SERIALIZED_NAME_ID)
  private BigDecimal id;

  public static final String SERIALIZED_NAME_IS_ANTISPAM_ENABLED = "is_antispam_enabled";
  @SerializedName(SERIALIZED_NAME_IS_ANTISPAM_ENABLED)
  private Boolean isAntispamEnabled;

  public static final String SERIALIZED_NAME_IS_AUTOPROLONG_ENABLED = "is_autoprolong_enabled";
  @SerializedName(SERIALIZED_NAME_IS_AUTOPROLONG_ENABLED)
  private Boolean isAutoprolongEnabled;

  public static final String SERIALIZED_NAME_IS_WHOIS_PRIVACY_ENABLED = "is_whois_privacy_enabled";
  @SerializedName(SERIALIZED_NAME_IS_WHOIS_PRIVACY_ENABLED)
  private Boolean isWhoisPrivacyEnabled;

  public static final String SERIALIZED_NAME_MESSAGE = "message";
  @SerializedName(SERIALIZED_NAME_MESSAGE)
  private String message;

  /**
   * Источник (способ) оплаты заявки.
   */
  @JsonAdapter(MoneySourceEnum.Adapter.class)
  public enum MoneySourceEnum {
    USE("use"),
    
    BONUS("bonus"),
    
    INVOICE("invoice");

    private String value;

    MoneySourceEnum(String value) {
      this.value = value;
    }

    public String getValue() {
      return value;
    }

    @Override
    public String toString() {
      return String.valueOf(value);
    }

    public static MoneySourceEnum fromValue(String value) {
      for (MoneySourceEnum b : MoneySourceEnum.values()) {
        if (b.value.equals(value)) {
          return b;
        }
      }
      return null;
    }

    public static class Adapter extends TypeAdapter<MoneySourceEnum> {
      @Override
      public void write(final JsonWriter jsonWriter, final MoneySourceEnum enumeration) throws IOException {
        jsonWriter.value(enumeration.getValue());
      }

      @Override
      public MoneySourceEnum read(final JsonReader jsonReader) throws IOException {
        String value =  jsonReader.nextString();
        return MoneySourceEnum.fromValue(value);
      }
    }
  }

  public static final String SERIALIZED_NAME_MONEY_SOURCE = "money_source";
  @SerializedName(SERIALIZED_NAME_MONEY_SOURCE)
  private MoneySourceEnum moneySource;

  public static final String SERIALIZED_NAME_PERIOD = "period";
  @SerializedName(SERIALIZED_NAME_PERIOD)
  private DomainPaymentPeriod period;

  public static final String SERIALIZED_NAME_PERSON_ID = "person_id";
  @SerializedName(SERIALIZED_NAME_PERSON_ID)
  private BigDecimal personId;

  public static final String SERIALIZED_NAME_PRIME = "prime";
  @SerializedName(SERIALIZED_NAME_PRIME)
  private DomainPrimeType prime;

  public static final String SERIALIZED_NAME_SOON_EXPIRE = "soon_expire";
  @SerializedName(SERIALIZED_NAME_SOON_EXPIRE)
  private BigDecimal soonExpire;

  public static final String SERIALIZED_NAME_SORT_ORDER = "sort_order";
  @SerializedName(SERIALIZED_NAME_SORT_ORDER)
  private BigDecimal sortOrder;

  /**
   * Тип заявки.
   */
  @JsonAdapter(TypeEnum.Adapter.class)
  public enum TypeEnum {
    REQUEST("request"),
    
    PROLONG("prolong"),
    
    TRANSFER("transfer");

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

  public DomainRequest() {
  }

  public DomainRequest accountId(String accountId) {
    
    this.accountId = accountId;
    return this;
  }

   /**
   * Идентификатор пользователя
   * @return accountId
  **/
  @javax.annotation.Nonnull
  public String getAccountId() {
    return accountId;
  }


  public void setAccountId(String accountId) {
    this.accountId = accountId;
  }


  public DomainRequest authCode(String authCode) {
    
    this.authCode = authCode;
    return this;
  }

   /**
   * Код авторизации для переноса домена.
   * @return authCode
  **/
  @javax.annotation.Nullable
  public String getAuthCode() {
    return authCode;
  }


  public void setAuthCode(String authCode) {
    this.authCode = authCode;
  }


  public DomainRequest date(OffsetDateTime date) {
    
    this.date = date;
    return this;
  }

   /**
   * Дата создания заявки.
   * @return date
  **/
  @javax.annotation.Nonnull
  public OffsetDateTime getDate() {
    return date;
  }


  public void setDate(OffsetDateTime date) {
    this.date = date;
  }


  public DomainRequest domainBundleId(String domainBundleId) {
    
    this.domainBundleId = domainBundleId;
    return this;
  }

   /**
   * Идентификационный номер бандла, в который входит данная заявка (null - если заявка не входит ни в один бандл).
   * @return domainBundleId
  **/
  @javax.annotation.Nullable
  public String getDomainBundleId() {
    return domainBundleId;
  }


  public void setDomainBundleId(String domainBundleId) {
    this.domainBundleId = domainBundleId;
  }


  public DomainRequest errorCodeTransfer(String errorCodeTransfer) {
    
    this.errorCodeTransfer = errorCodeTransfer;
    return this;
  }

   /**
   * Код ошибки трансфера домена.
   * @return errorCodeTransfer
  **/
  @javax.annotation.Nullable
  public String getErrorCodeTransfer() {
    return errorCodeTransfer;
  }


  public void setErrorCodeTransfer(String errorCodeTransfer) {
    this.errorCodeTransfer = errorCodeTransfer;
  }


  public DomainRequest fqdn(String fqdn) {
    
    this.fqdn = fqdn;
    return this;
  }

   /**
   * Полное имя домена.
   * @return fqdn
  **/
  @javax.annotation.Nonnull
  public String getFqdn() {
    return fqdn;
  }


  public void setFqdn(String fqdn) {
    this.fqdn = fqdn;
  }


  public DomainRequest groupId(BigDecimal groupId) {
    
    this.groupId = groupId;
    return this;
  }

   /**
   * Идентификатор группы доменных зон.
   * @return groupId
  **/
  @javax.annotation.Nonnull
  public BigDecimal getGroupId() {
    return groupId;
  }


  public void setGroupId(BigDecimal groupId) {
    this.groupId = groupId;
  }


  public DomainRequest id(BigDecimal id) {
    
    this.id = id;
    return this;
  }

   /**
   * Идентификатор заявки.
   * @return id
  **/
  @javax.annotation.Nonnull
  public BigDecimal getId() {
    return id;
  }


  public void setId(BigDecimal id) {
    this.id = id;
  }


  public DomainRequest isAntispamEnabled(Boolean isAntispamEnabled) {
    
    this.isAntispamEnabled = isAntispamEnabled;
    return this;
  }

   /**
   * Это логическое значение, которое показывает включена ли услуга \&quot;Антиспам\&quot; для домена
   * @return isAntispamEnabled
  **/
  @javax.annotation.Nonnull
  public Boolean getIsAntispamEnabled() {
    return isAntispamEnabled;
  }


  public void setIsAntispamEnabled(Boolean isAntispamEnabled) {
    this.isAntispamEnabled = isAntispamEnabled;
  }


  public DomainRequest isAutoprolongEnabled(Boolean isAutoprolongEnabled) {
    
    this.isAutoprolongEnabled = isAutoprolongEnabled;
    return this;
  }

   /**
   * Это логическое значение, которое показывает, включено ли автопродление домена.
   * @return isAutoprolongEnabled
  **/
  @javax.annotation.Nonnull
  public Boolean getIsAutoprolongEnabled() {
    return isAutoprolongEnabled;
  }


  public void setIsAutoprolongEnabled(Boolean isAutoprolongEnabled) {
    this.isAutoprolongEnabled = isAutoprolongEnabled;
  }


  public DomainRequest isWhoisPrivacyEnabled(Boolean isWhoisPrivacyEnabled) {
    
    this.isWhoisPrivacyEnabled = isWhoisPrivacyEnabled;
    return this;
  }

   /**
   * Это логическое значение, которое показывает, включено ли скрытие данных администратора домена для whois. Опция недоступна для доменов в зонах .ru и .рф.
   * @return isWhoisPrivacyEnabled
  **/
  @javax.annotation.Nonnull
  public Boolean getIsWhoisPrivacyEnabled() {
    return isWhoisPrivacyEnabled;
  }


  public void setIsWhoisPrivacyEnabled(Boolean isWhoisPrivacyEnabled) {
    this.isWhoisPrivacyEnabled = isWhoisPrivacyEnabled;
  }


  public DomainRequest message(String message) {
    
    this.message = message;
    return this;
  }

   /**
   * Информационное сообщение о заявке.
   * @return message
  **/
  @javax.annotation.Nullable
  public String getMessage() {
    return message;
  }


  public void setMessage(String message) {
    this.message = message;
  }


  public DomainRequest moneySource(MoneySourceEnum moneySource) {
    
    this.moneySource = moneySource;
    return this;
  }

   /**
   * Источник (способ) оплаты заявки.
   * @return moneySource
  **/
  @javax.annotation.Nullable
  public MoneySourceEnum getMoneySource() {
    return moneySource;
  }


  public void setMoneySource(MoneySourceEnum moneySource) {
    this.moneySource = moneySource;
  }


  public DomainRequest period(DomainPaymentPeriod period) {
    
    this.period = period;
    return this;
  }

   /**
   * Get period
   * @return period
  **/
  @javax.annotation.Nonnull
  public DomainPaymentPeriod getPeriod() {
    return period;
  }


  public void setPeriod(DomainPaymentPeriod period) {
    this.period = period;
  }


  public DomainRequest personId(BigDecimal personId) {
    
    this.personId = personId;
    return this;
  }

   /**
   * Идентификационный номер персоны для заявки на регистрацию.
   * @return personId
  **/
  @javax.annotation.Nonnull
  public BigDecimal getPersonId() {
    return personId;
  }


  public void setPersonId(BigDecimal personId) {
    this.personId = personId;
  }


  public DomainRequest prime(DomainPrimeType prime) {
    
    this.prime = prime;
    return this;
  }

   /**
   * Get prime
   * @return prime
  **/
  @javax.annotation.Nonnull
  public DomainPrimeType getPrime() {
    return prime;
  }


  public void setPrime(DomainPrimeType prime) {
    this.prime = prime;
  }


  public DomainRequest soonExpire(BigDecimal soonExpire) {
    
    this.soonExpire = soonExpire;
    return this;
  }

   /**
   * Количество дней до конца регистрации домена, за которые мы уведомим о необходимости продления.
   * @return soonExpire
  **/
  @javax.annotation.Nonnull
  public BigDecimal getSoonExpire() {
    return soonExpire;
  }


  public void setSoonExpire(BigDecimal soonExpire) {
    this.soonExpire = soonExpire;
  }


  public DomainRequest sortOrder(BigDecimal sortOrder) {
    
    this.sortOrder = sortOrder;
    return this;
  }

   /**
   * Это значение используется для сортировки доменных зон в панели управления.
   * @return sortOrder
  **/
  @javax.annotation.Nonnull
  public BigDecimal getSortOrder() {
    return sortOrder;
  }


  public void setSortOrder(BigDecimal sortOrder) {
    this.sortOrder = sortOrder;
  }


  public DomainRequest type(TypeEnum type) {
    
    this.type = type;
    return this;
  }

   /**
   * Тип заявки.
   * @return type
  **/
  @javax.annotation.Nonnull
  public TypeEnum getType() {
    return type;
  }


  public void setType(TypeEnum type) {
    this.type = type;
  }



  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    DomainRequest domainRequest = (DomainRequest) o;
    return Objects.equals(this.accountId, domainRequest.accountId) &&
        Objects.equals(this.authCode, domainRequest.authCode) &&
        Objects.equals(this.date, domainRequest.date) &&
        Objects.equals(this.domainBundleId, domainRequest.domainBundleId) &&
        Objects.equals(this.errorCodeTransfer, domainRequest.errorCodeTransfer) &&
        Objects.equals(this.fqdn, domainRequest.fqdn) &&
        Objects.equals(this.groupId, domainRequest.groupId) &&
        Objects.equals(this.id, domainRequest.id) &&
        Objects.equals(this.isAntispamEnabled, domainRequest.isAntispamEnabled) &&
        Objects.equals(this.isAutoprolongEnabled, domainRequest.isAutoprolongEnabled) &&
        Objects.equals(this.isWhoisPrivacyEnabled, domainRequest.isWhoisPrivacyEnabled) &&
        Objects.equals(this.message, domainRequest.message) &&
        Objects.equals(this.moneySource, domainRequest.moneySource) &&
        Objects.equals(this.period, domainRequest.period) &&
        Objects.equals(this.personId, domainRequest.personId) &&
        Objects.equals(this.prime, domainRequest.prime) &&
        Objects.equals(this.soonExpire, domainRequest.soonExpire) &&
        Objects.equals(this.sortOrder, domainRequest.sortOrder) &&
        Objects.equals(this.type, domainRequest.type);
  }

  @Override
  public int hashCode() {
    return Objects.hash(accountId, authCode, date, domainBundleId, errorCodeTransfer, fqdn, groupId, id, isAntispamEnabled, isAutoprolongEnabled, isWhoisPrivacyEnabled, message, moneySource, period, personId, prime, soonExpire, sortOrder, type);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class DomainRequest {\n");
    sb.append("    accountId: ").append(toIndentedString(accountId)).append("\n");
    sb.append("    authCode: ").append(toIndentedString(authCode)).append("\n");
    sb.append("    date: ").append(toIndentedString(date)).append("\n");
    sb.append("    domainBundleId: ").append(toIndentedString(domainBundleId)).append("\n");
    sb.append("    errorCodeTransfer: ").append(toIndentedString(errorCodeTransfer)).append("\n");
    sb.append("    fqdn: ").append(toIndentedString(fqdn)).append("\n");
    sb.append("    groupId: ").append(toIndentedString(groupId)).append("\n");
    sb.append("    id: ").append(toIndentedString(id)).append("\n");
    sb.append("    isAntispamEnabled: ").append(toIndentedString(isAntispamEnabled)).append("\n");
    sb.append("    isAutoprolongEnabled: ").append(toIndentedString(isAutoprolongEnabled)).append("\n");
    sb.append("    isWhoisPrivacyEnabled: ").append(toIndentedString(isWhoisPrivacyEnabled)).append("\n");
    sb.append("    message: ").append(toIndentedString(message)).append("\n");
    sb.append("    moneySource: ").append(toIndentedString(moneySource)).append("\n");
    sb.append("    period: ").append(toIndentedString(period)).append("\n");
    sb.append("    personId: ").append(toIndentedString(personId)).append("\n");
    sb.append("    prime: ").append(toIndentedString(prime)).append("\n");
    sb.append("    soonExpire: ").append(toIndentedString(soonExpire)).append("\n");
    sb.append("    sortOrder: ").append(toIndentedString(sortOrder)).append("\n");
    sb.append("    type: ").append(toIndentedString(type)).append("\n");
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
    openapiFields.add("account_id");
    openapiFields.add("auth_code");
    openapiFields.add("date");
    openapiFields.add("domain_bundle_id");
    openapiFields.add("error_code_transfer");
    openapiFields.add("fqdn");
    openapiFields.add("group_id");
    openapiFields.add("id");
    openapiFields.add("is_antispam_enabled");
    openapiFields.add("is_autoprolong_enabled");
    openapiFields.add("is_whois_privacy_enabled");
    openapiFields.add("message");
    openapiFields.add("money_source");
    openapiFields.add("period");
    openapiFields.add("person_id");
    openapiFields.add("prime");
    openapiFields.add("soon_expire");
    openapiFields.add("sort_order");
    openapiFields.add("type");

    // a set of required properties/fields (JSON key names)
    openapiRequiredFields = new HashSet<String>();
    openapiRequiredFields.add("account_id");
    openapiRequiredFields.add("auth_code");
    openapiRequiredFields.add("date");
    openapiRequiredFields.add("domain_bundle_id");
    openapiRequiredFields.add("error_code_transfer");
    openapiRequiredFields.add("fqdn");
    openapiRequiredFields.add("group_id");
    openapiRequiredFields.add("id");
    openapiRequiredFields.add("is_antispam_enabled");
    openapiRequiredFields.add("is_autoprolong_enabled");
    openapiRequiredFields.add("is_whois_privacy_enabled");
    openapiRequiredFields.add("message");
    openapiRequiredFields.add("money_source");
    openapiRequiredFields.add("period");
    openapiRequiredFields.add("person_id");
    openapiRequiredFields.add("prime");
    openapiRequiredFields.add("soon_expire");
    openapiRequiredFields.add("sort_order");
    openapiRequiredFields.add("type");
  }

 /**
  * Validates the JSON Element and throws an exception if issues found
  *
  * @param jsonElement JSON Element
  * @throws IOException if the JSON Element is invalid with respect to DomainRequest
  */
  public static void validateJsonElement(JsonElement jsonElement) throws IOException {
      if (jsonElement == null) {
        if (!DomainRequest.openapiRequiredFields.isEmpty()) { // has required fields but JSON element is null
          throw new IllegalArgumentException(String.format("The required field(s) %s in DomainRequest is not found in the empty JSON string", DomainRequest.openapiRequiredFields.toString()));
        }
      }

      Set<Entry<String, JsonElement>> entries = jsonElement.getAsJsonObject().entrySet();
      // check to see if the JSON string contains additional fields
      for (Entry<String, JsonElement> entry : entries) {
        if (!DomainRequest.openapiFields.contains(entry.getKey())) {
          throw new IllegalArgumentException(String.format("The field `%s` in the JSON string is not defined in the `DomainRequest` properties. JSON: %s", entry.getKey(), jsonElement.toString()));
        }
      }

      // check to make sure all required properties/fields are present in the JSON string
      for (String requiredField : DomainRequest.openapiRequiredFields) {
        if (jsonElement.getAsJsonObject().get(requiredField) == null) {
          throw new IllegalArgumentException(String.format("The required field `%s` is not found in the JSON string: %s", requiredField, jsonElement.toString()));
        }
      }
        JsonObject jsonObj = jsonElement.getAsJsonObject();
      if (!jsonObj.get("account_id").isJsonPrimitive()) {
        throw new IllegalArgumentException(String.format("Expected the field `account_id` to be a primitive type in the JSON string but got `%s`", jsonObj.get("account_id").toString()));
      }
      if ((jsonObj.get("auth_code") != null && !jsonObj.get("auth_code").isJsonNull()) && !jsonObj.get("auth_code").isJsonPrimitive()) {
        throw new IllegalArgumentException(String.format("Expected the field `auth_code` to be a primitive type in the JSON string but got `%s`", jsonObj.get("auth_code").toString()));
      }
      if ((jsonObj.get("domain_bundle_id") != null && !jsonObj.get("domain_bundle_id").isJsonNull()) && !jsonObj.get("domain_bundle_id").isJsonPrimitive()) {
        throw new IllegalArgumentException(String.format("Expected the field `domain_bundle_id` to be a primitive type in the JSON string but got `%s`", jsonObj.get("domain_bundle_id").toString()));
      }
      if ((jsonObj.get("error_code_transfer") != null && !jsonObj.get("error_code_transfer").isJsonNull()) && !jsonObj.get("error_code_transfer").isJsonPrimitive()) {
        throw new IllegalArgumentException(String.format("Expected the field `error_code_transfer` to be a primitive type in the JSON string but got `%s`", jsonObj.get("error_code_transfer").toString()));
      }
      if (!jsonObj.get("fqdn").isJsonPrimitive()) {
        throw new IllegalArgumentException(String.format("Expected the field `fqdn` to be a primitive type in the JSON string but got `%s`", jsonObj.get("fqdn").toString()));
      }
      if ((jsonObj.get("message") != null && !jsonObj.get("message").isJsonNull()) && !jsonObj.get("message").isJsonPrimitive()) {
        throw new IllegalArgumentException(String.format("Expected the field `message` to be a primitive type in the JSON string but got `%s`", jsonObj.get("message").toString()));
      }
      if ((jsonObj.get("money_source") != null && !jsonObj.get("money_source").isJsonNull()) && !jsonObj.get("money_source").isJsonPrimitive()) {
        throw new IllegalArgumentException(String.format("Expected the field `money_source` to be a primitive type in the JSON string but got `%s`", jsonObj.get("money_source").toString()));
      }
      if (!jsonObj.get("type").isJsonPrimitive()) {
        throw new IllegalArgumentException(String.format("Expected the field `type` to be a primitive type in the JSON string but got `%s`", jsonObj.get("type").toString()));
      }
  }

  public static class CustomTypeAdapterFactory implements TypeAdapterFactory {
    @SuppressWarnings("unchecked")
    @Override
    public <T> TypeAdapter<T> create(Gson gson, TypeToken<T> type) {
       if (!DomainRequest.class.isAssignableFrom(type.getRawType())) {
         return null; // this class only serializes 'DomainRequest' and its subtypes
       }
       final TypeAdapter<JsonElement> elementAdapter = gson.getAdapter(JsonElement.class);
       final TypeAdapter<DomainRequest> thisAdapter
                        = gson.getDelegateAdapter(this, TypeToken.get(DomainRequest.class));

       return (TypeAdapter<T>) new TypeAdapter<DomainRequest>() {
           @Override
           public void write(JsonWriter out, DomainRequest value) throws IOException {
             JsonObject obj = thisAdapter.toJsonTree(value).getAsJsonObject();
             elementAdapter.write(out, obj);
           }

           @Override
           public DomainRequest read(JsonReader in) throws IOException {
             JsonElement jsonElement = elementAdapter.read(in);
             validateJsonElement(jsonElement);
             return thisAdapter.fromJsonTree(jsonElement);
           }

       }.nullSafe();
    }
  }

 /**
  * Create an instance of DomainRequest given an JSON string
  *
  * @param jsonString JSON string
  * @return An instance of DomainRequest
  * @throws IOException if the JSON string is invalid with respect to DomainRequest
  */
  public static DomainRequest fromJson(String jsonString) throws IOException {
    return JSON.getGson().fromJson(jsonString, DomainRequest.class);
  }

 /**
  * Convert an instance of DomainRequest to an JSON string
  *
  * @return JSON string
  */
  public String toJson() {
    return JSON.getGson().toJson(this);
  }
}

