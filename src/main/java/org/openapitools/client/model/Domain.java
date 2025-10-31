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
import java.util.ArrayList;
import java.util.List;
import org.openapitools.client.model.DomainAllowedBuyPeriodsInner;
import org.openapitools.client.model.Subdomain;

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
 * Домен
 */
@javax.annotation.Generated(value = "org.openapitools.codegen.languages.JavaClientCodegen", date = "2025-10-31T14:40:51.795734Z[Etc/UTC]")
public class Domain {
  public static final String SERIALIZED_NAME_ALLOWED_BUY_PERIODS = "allowed_buy_periods";
  @SerializedName(SERIALIZED_NAME_ALLOWED_BUY_PERIODS)
  private List<DomainAllowedBuyPeriodsInner> allowedBuyPeriods = new ArrayList<>();

  public static final String SERIALIZED_NAME_DAYS_LEFT = "days_left";
  @SerializedName(SERIALIZED_NAME_DAYS_LEFT)
  private BigDecimal daysLeft;

  /**
   * Статус домена.
   */
  @JsonAdapter(DomainStatusEnum.Adapter.class)
  public enum DomainStatusEnum {
    AWAITING_PAYMENT("awaiting_payment"),
    
    EXPIRED("expired"),
    
    FINAL_EXPIRED("final_expired"),
    
    FREE("free"),
    
    NO_PAID("no_paid"),
    
    NS_BASED("ns_based"),
    
    PAID("paid"),
    
    SOON_EXPIRE("soon_expire"),
    
    TODAY_EXPIRED("today_expired");

    private String value;

    DomainStatusEnum(String value) {
      this.value = value;
    }

    public String getValue() {
      return value;
    }

    @Override
    public String toString() {
      return String.valueOf(value);
    }

    public static DomainStatusEnum fromValue(String value) {
      for (DomainStatusEnum b : DomainStatusEnum.values()) {
        if (b.value.equals(value)) {
          return b;
        }
      }
      throw new IllegalArgumentException("Unexpected value '" + value + "'");
    }

    public static class Adapter extends TypeAdapter<DomainStatusEnum> {
      @Override
      public void write(final JsonWriter jsonWriter, final DomainStatusEnum enumeration) throws IOException {
        jsonWriter.value(enumeration.getValue());
      }

      @Override
      public DomainStatusEnum read(final JsonReader jsonReader) throws IOException {
        String value =  jsonReader.nextString();
        return DomainStatusEnum.fromValue(value);
      }
    }
  }

  public static final String SERIALIZED_NAME_DOMAIN_STATUS = "domain_status";
  @SerializedName(SERIALIZED_NAME_DOMAIN_STATUS)
  private DomainStatusEnum domainStatus;

  public static final String SERIALIZED_NAME_EXPIRATION = "expiration";
  @SerializedName(SERIALIZED_NAME_EXPIRATION)
  private String expiration;

  public static final String SERIALIZED_NAME_FQDN = "fqdn";
  @SerializedName(SERIALIZED_NAME_FQDN)
  private String fqdn;

  public static final String SERIALIZED_NAME_ID = "id";
  @SerializedName(SERIALIZED_NAME_ID)
  private BigDecimal id;

  public static final String SERIALIZED_NAME_AVATAR_LINK = "avatar_link";
  @SerializedName(SERIALIZED_NAME_AVATAR_LINK)
  private String avatarLink;

  public static final String SERIALIZED_NAME_IS_AUTOPROLONG_ENABLED = "is_autoprolong_enabled";
  @SerializedName(SERIALIZED_NAME_IS_AUTOPROLONG_ENABLED)
  private Boolean isAutoprolongEnabled;

  public static final String SERIALIZED_NAME_IS_PREMIUM = "is_premium";
  @SerializedName(SERIALIZED_NAME_IS_PREMIUM)
  private Boolean isPremium;

  public static final String SERIALIZED_NAME_IS_PROLONG_ALLOWED = "is_prolong_allowed";
  @SerializedName(SERIALIZED_NAME_IS_PROLONG_ALLOWED)
  private Boolean isProlongAllowed;

  public static final String SERIALIZED_NAME_IS_TECHNICAL = "is_technical";
  @SerializedName(SERIALIZED_NAME_IS_TECHNICAL)
  private Boolean isTechnical;

  public static final String SERIALIZED_NAME_IS_WHOIS_PRIVACY_ENABLED = "is_whois_privacy_enabled";
  @SerializedName(SERIALIZED_NAME_IS_WHOIS_PRIVACY_ENABLED)
  private Boolean isWhoisPrivacyEnabled;

  public static final String SERIALIZED_NAME_LINKED_IP = "linked_ip";
  @SerializedName(SERIALIZED_NAME_LINKED_IP)
  private String linkedIp;

  public static final String SERIALIZED_NAME_PAID_TILL = "paid_till";
  @SerializedName(SERIALIZED_NAME_PAID_TILL)
  private String paidTill;

  public static final String SERIALIZED_NAME_PERSON_ID = "person_id";
  @SerializedName(SERIALIZED_NAME_PERSON_ID)
  private BigDecimal personId;

  public static final String SERIALIZED_NAME_PREMIUM_PROLONG_COST = "premium_prolong_cost";
  @SerializedName(SERIALIZED_NAME_PREMIUM_PROLONG_COST)
  private BigDecimal premiumProlongCost;

  public static final String SERIALIZED_NAME_PROVIDER = "provider";
  @SerializedName(SERIALIZED_NAME_PROVIDER)
  private String provider;

  /**
   * Статус заявки на продление/регистрацию/трансфер домена.
   */
  @JsonAdapter(RequestStatusEnum.Adapter.class)
  public enum RequestStatusEnum {
    PROLONGATION_FAIL("prolongation_fail"),
    
    PROLONGATION_REQUEST("prolongation_request"),
    
    REGISTRATION_FAIL("registration_fail"),
    
    REGISTRATION_REQUEST("registration_request"),
    
    TRANSFER_FAIL("transfer_fail"),
    
    TRANSFER_REQUEST("transfer_request"),
    
    AWAITING_PERSON("awaiting_person");

    private String value;

    RequestStatusEnum(String value) {
      this.value = value;
    }

    public String getValue() {
      return value;
    }

    @Override
    public String toString() {
      return String.valueOf(value);
    }

    public static RequestStatusEnum fromValue(String value) {
      for (RequestStatusEnum b : RequestStatusEnum.values()) {
        if (b.value.equals(value)) {
          return b;
        }
      }
      return null;
    }

    public static class Adapter extends TypeAdapter<RequestStatusEnum> {
      @Override
      public void write(final JsonWriter jsonWriter, final RequestStatusEnum enumeration) throws IOException {
        jsonWriter.value(enumeration.getValue());
      }

      @Override
      public RequestStatusEnum read(final JsonReader jsonReader) throws IOException {
        String value =  jsonReader.nextString();
        return RequestStatusEnum.fromValue(value);
      }
    }
  }

  public static final String SERIALIZED_NAME_REQUEST_STATUS = "request_status";
  @SerializedName(SERIALIZED_NAME_REQUEST_STATUS)
  private RequestStatusEnum requestStatus;

  public static final String SERIALIZED_NAME_SUBDOMAINS = "subdomains";
  @SerializedName(SERIALIZED_NAME_SUBDOMAINS)
  private List<Subdomain> subdomains = new ArrayList<>();

  public static final String SERIALIZED_NAME_TLD_ID = "tld_id";
  @SerializedName(SERIALIZED_NAME_TLD_ID)
  private BigDecimal tldId;

  public Domain() {
  }

  public Domain allowedBuyPeriods(List<DomainAllowedBuyPeriodsInner> allowedBuyPeriods) {
    
    this.allowedBuyPeriods = allowedBuyPeriods;
    return this;
  }

  public Domain addAllowedBuyPeriodsItem(DomainAllowedBuyPeriodsInner allowedBuyPeriodsItem) {
    if (this.allowedBuyPeriods == null) {
      this.allowedBuyPeriods = new ArrayList<>();
    }
    this.allowedBuyPeriods.add(allowedBuyPeriodsItem);
    return this;
  }

   /**
   * Допустимые периоды продления домена.
   * @return allowedBuyPeriods
  **/
  @javax.annotation.Nonnull
  public List<DomainAllowedBuyPeriodsInner> getAllowedBuyPeriods() {
    return allowedBuyPeriods;
  }


  public void setAllowedBuyPeriods(List<DomainAllowedBuyPeriodsInner> allowedBuyPeriods) {
    this.allowedBuyPeriods = allowedBuyPeriods;
  }


  public Domain daysLeft(BigDecimal daysLeft) {
    
    this.daysLeft = daysLeft;
    return this;
  }

   /**
   * Количество дней, оставшихся до конца срока регистрации домена.
   * @return daysLeft
  **/
  @javax.annotation.Nonnull
  public BigDecimal getDaysLeft() {
    return daysLeft;
  }


  public void setDaysLeft(BigDecimal daysLeft) {
    this.daysLeft = daysLeft;
  }


  public Domain domainStatus(DomainStatusEnum domainStatus) {
    
    this.domainStatus = domainStatus;
    return this;
  }

   /**
   * Статус домена.
   * @return domainStatus
  **/
  @javax.annotation.Nonnull
  public DomainStatusEnum getDomainStatus() {
    return domainStatus;
  }


  public void setDomainStatus(DomainStatusEnum domainStatus) {
    this.domainStatus = domainStatus;
  }


  public Domain expiration(String expiration) {
    
    this.expiration = expiration;
    return this;
  }

   /**
   * Дата окончания срока регистрации домена, для доменов без срока окончания регистрации будет приходить 0000-00-00.
   * @return expiration
  **/
  @javax.annotation.Nonnull
  public String getExpiration() {
    return expiration;
  }


  public void setExpiration(String expiration) {
    this.expiration = expiration;
  }


  public Domain fqdn(String fqdn) {
    
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


  public Domain id(BigDecimal id) {
    
    this.id = id;
    return this;
  }

   /**
   * ID домена.
   * @return id
  **/
  @javax.annotation.Nonnull
  public BigDecimal getId() {
    return id;
  }


  public void setId(BigDecimal id) {
    this.id = id;
  }


  public Domain avatarLink(String avatarLink) {
    
    this.avatarLink = avatarLink;
    return this;
  }

   /**
   * Ссылка на аватар домена.
   * @return avatarLink
  **/
  @javax.annotation.Nullable
  public String getAvatarLink() {
    return avatarLink;
  }


  public void setAvatarLink(String avatarLink) {
    this.avatarLink = avatarLink;
  }


  public Domain isAutoprolongEnabled(Boolean isAutoprolongEnabled) {
    
    this.isAutoprolongEnabled = isAutoprolongEnabled;
    return this;
  }

   /**
   * Это логическое значение, которое показывает, включено ли автопродление домена.
   * @return isAutoprolongEnabled
  **/
  @javax.annotation.Nullable
  public Boolean getIsAutoprolongEnabled() {
    return isAutoprolongEnabled;
  }


  public void setIsAutoprolongEnabled(Boolean isAutoprolongEnabled) {
    this.isAutoprolongEnabled = isAutoprolongEnabled;
  }


  public Domain isPremium(Boolean isPremium) {
    
    this.isPremium = isPremium;
    return this;
  }

   /**
   * Это логическое значение, которое показывает, является ли домен премиальным.
   * @return isPremium
  **/
  @javax.annotation.Nonnull
  public Boolean getIsPremium() {
    return isPremium;
  }


  public void setIsPremium(Boolean isPremium) {
    this.isPremium = isPremium;
  }


  public Domain isProlongAllowed(Boolean isProlongAllowed) {
    
    this.isProlongAllowed = isProlongAllowed;
    return this;
  }

   /**
   * Это логическое значение, которое показывает, можно ли сейчас продлить домен.
   * @return isProlongAllowed
  **/
  @javax.annotation.Nonnull
  public Boolean getIsProlongAllowed() {
    return isProlongAllowed;
  }


  public void setIsProlongAllowed(Boolean isProlongAllowed) {
    this.isProlongAllowed = isProlongAllowed;
  }


  public Domain isTechnical(Boolean isTechnical) {
    
    this.isTechnical = isTechnical;
    return this;
  }

   /**
   * Это логическое значение, которое показывает, является ли домен техническим.
   * @return isTechnical
  **/
  @javax.annotation.Nonnull
  public Boolean getIsTechnical() {
    return isTechnical;
  }


  public void setIsTechnical(Boolean isTechnical) {
    this.isTechnical = isTechnical;
  }


  public Domain isWhoisPrivacyEnabled(Boolean isWhoisPrivacyEnabled) {
    
    this.isWhoisPrivacyEnabled = isWhoisPrivacyEnabled;
    return this;
  }

   /**
   * Это логическое значение, которое показывает, включено ли скрытие данных администратора домена для whois. Если приходит null, значит для данной зоны эта услуга не доступна.
   * @return isWhoisPrivacyEnabled
  **/
  @javax.annotation.Nullable
  public Boolean getIsWhoisPrivacyEnabled() {
    return isWhoisPrivacyEnabled;
  }


  public void setIsWhoisPrivacyEnabled(Boolean isWhoisPrivacyEnabled) {
    this.isWhoisPrivacyEnabled = isWhoisPrivacyEnabled;
  }


  public Domain linkedIp(String linkedIp) {
    
    this.linkedIp = linkedIp;
    return this;
  }

   /**
   * Привязанный к домену IP-адрес.
   * @return linkedIp
  **/
  @javax.annotation.Nullable
  public String getLinkedIp() {
    return linkedIp;
  }


  public void setLinkedIp(String linkedIp) {
    this.linkedIp = linkedIp;
  }


  public Domain paidTill(String paidTill) {
    
    this.paidTill = paidTill;
    return this;
  }

   /**
   * До какого числа оплачен домен.
   * @return paidTill
  **/
  @javax.annotation.Nullable
  public String getPaidTill() {
    return paidTill;
  }


  public void setPaidTill(String paidTill) {
    this.paidTill = paidTill;
  }


  public Domain personId(BigDecimal personId) {
    
    this.personId = personId;
    return this;
  }

   /**
   * ID администратора, на которого зарегистрирован домен.
   * @return personId
  **/
  @javax.annotation.Nullable
  public BigDecimal getPersonId() {
    return personId;
  }


  public void setPersonId(BigDecimal personId) {
    this.personId = personId;
  }


  public Domain premiumProlongCost(BigDecimal premiumProlongCost) {
    
    this.premiumProlongCost = premiumProlongCost;
    return this;
  }

   /**
   * Стоимость премиального домена.
   * @return premiumProlongCost
  **/
  @javax.annotation.Nullable
  public BigDecimal getPremiumProlongCost() {
    return premiumProlongCost;
  }


  public void setPremiumProlongCost(BigDecimal premiumProlongCost) {
    this.premiumProlongCost = premiumProlongCost;
  }


  public Domain provider(String provider) {
    
    this.provider = provider;
    return this;
  }

   /**
   * ID регистратора домена.
   * @return provider
  **/
  @javax.annotation.Nullable
  public String getProvider() {
    return provider;
  }


  public void setProvider(String provider) {
    this.provider = provider;
  }


  public Domain requestStatus(RequestStatusEnum requestStatus) {
    
    this.requestStatus = requestStatus;
    return this;
  }

   /**
   * Статус заявки на продление/регистрацию/трансфер домена.
   * @return requestStatus
  **/
  @javax.annotation.Nullable
  public RequestStatusEnum getRequestStatus() {
    return requestStatus;
  }


  public void setRequestStatus(RequestStatusEnum requestStatus) {
    this.requestStatus = requestStatus;
  }


  public Domain subdomains(List<Subdomain> subdomains) {
    
    this.subdomains = subdomains;
    return this;
  }

  public Domain addSubdomainsItem(Subdomain subdomainsItem) {
    if (this.subdomains == null) {
      this.subdomains = new ArrayList<>();
    }
    this.subdomains.add(subdomainsItem);
    return this;
  }

   /**
   * Список поддоменов.
   * @return subdomains
  **/
  @javax.annotation.Nonnull
  public List<Subdomain> getSubdomains() {
    return subdomains;
  }


  public void setSubdomains(List<Subdomain> subdomains) {
    this.subdomains = subdomains;
  }


  public Domain tldId(BigDecimal tldId) {
    
    this.tldId = tldId;
    return this;
  }

   /**
   * ID доменной зоны.
   * @return tldId
  **/
  @javax.annotation.Nullable
  public BigDecimal getTldId() {
    return tldId;
  }


  public void setTldId(BigDecimal tldId) {
    this.tldId = tldId;
  }



  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Domain domain = (Domain) o;
    return Objects.equals(this.allowedBuyPeriods, domain.allowedBuyPeriods) &&
        Objects.equals(this.daysLeft, domain.daysLeft) &&
        Objects.equals(this.domainStatus, domain.domainStatus) &&
        Objects.equals(this.expiration, domain.expiration) &&
        Objects.equals(this.fqdn, domain.fqdn) &&
        Objects.equals(this.id, domain.id) &&
        Objects.equals(this.avatarLink, domain.avatarLink) &&
        Objects.equals(this.isAutoprolongEnabled, domain.isAutoprolongEnabled) &&
        Objects.equals(this.isPremium, domain.isPremium) &&
        Objects.equals(this.isProlongAllowed, domain.isProlongAllowed) &&
        Objects.equals(this.isTechnical, domain.isTechnical) &&
        Objects.equals(this.isWhoisPrivacyEnabled, domain.isWhoisPrivacyEnabled) &&
        Objects.equals(this.linkedIp, domain.linkedIp) &&
        Objects.equals(this.paidTill, domain.paidTill) &&
        Objects.equals(this.personId, domain.personId) &&
        Objects.equals(this.premiumProlongCost, domain.premiumProlongCost) &&
        Objects.equals(this.provider, domain.provider) &&
        Objects.equals(this.requestStatus, domain.requestStatus) &&
        Objects.equals(this.subdomains, domain.subdomains) &&
        Objects.equals(this.tldId, domain.tldId);
  }

  @Override
  public int hashCode() {
    return Objects.hash(allowedBuyPeriods, daysLeft, domainStatus, expiration, fqdn, id, avatarLink, isAutoprolongEnabled, isPremium, isProlongAllowed, isTechnical, isWhoisPrivacyEnabled, linkedIp, paidTill, personId, premiumProlongCost, provider, requestStatus, subdomains, tldId);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class Domain {\n");
    sb.append("    allowedBuyPeriods: ").append(toIndentedString(allowedBuyPeriods)).append("\n");
    sb.append("    daysLeft: ").append(toIndentedString(daysLeft)).append("\n");
    sb.append("    domainStatus: ").append(toIndentedString(domainStatus)).append("\n");
    sb.append("    expiration: ").append(toIndentedString(expiration)).append("\n");
    sb.append("    fqdn: ").append(toIndentedString(fqdn)).append("\n");
    sb.append("    id: ").append(toIndentedString(id)).append("\n");
    sb.append("    avatarLink: ").append(toIndentedString(avatarLink)).append("\n");
    sb.append("    isAutoprolongEnabled: ").append(toIndentedString(isAutoprolongEnabled)).append("\n");
    sb.append("    isPremium: ").append(toIndentedString(isPremium)).append("\n");
    sb.append("    isProlongAllowed: ").append(toIndentedString(isProlongAllowed)).append("\n");
    sb.append("    isTechnical: ").append(toIndentedString(isTechnical)).append("\n");
    sb.append("    isWhoisPrivacyEnabled: ").append(toIndentedString(isWhoisPrivacyEnabled)).append("\n");
    sb.append("    linkedIp: ").append(toIndentedString(linkedIp)).append("\n");
    sb.append("    paidTill: ").append(toIndentedString(paidTill)).append("\n");
    sb.append("    personId: ").append(toIndentedString(personId)).append("\n");
    sb.append("    premiumProlongCost: ").append(toIndentedString(premiumProlongCost)).append("\n");
    sb.append("    provider: ").append(toIndentedString(provider)).append("\n");
    sb.append("    requestStatus: ").append(toIndentedString(requestStatus)).append("\n");
    sb.append("    subdomains: ").append(toIndentedString(subdomains)).append("\n");
    sb.append("    tldId: ").append(toIndentedString(tldId)).append("\n");
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
    openapiFields.add("allowed_buy_periods");
    openapiFields.add("days_left");
    openapiFields.add("domain_status");
    openapiFields.add("expiration");
    openapiFields.add("fqdn");
    openapiFields.add("id");
    openapiFields.add("avatar_link");
    openapiFields.add("is_autoprolong_enabled");
    openapiFields.add("is_premium");
    openapiFields.add("is_prolong_allowed");
    openapiFields.add("is_technical");
    openapiFields.add("is_whois_privacy_enabled");
    openapiFields.add("linked_ip");
    openapiFields.add("paid_till");
    openapiFields.add("person_id");
    openapiFields.add("premium_prolong_cost");
    openapiFields.add("provider");
    openapiFields.add("request_status");
    openapiFields.add("subdomains");
    openapiFields.add("tld_id");

    // a set of required properties/fields (JSON key names)
    openapiRequiredFields = new HashSet<String>();
    openapiRequiredFields.add("allowed_buy_periods");
    openapiRequiredFields.add("days_left");
    openapiRequiredFields.add("domain_status");
    openapiRequiredFields.add("expiration");
    openapiRequiredFields.add("fqdn");
    openapiRequiredFields.add("id");
    openapiRequiredFields.add("avatar_link");
    openapiRequiredFields.add("is_autoprolong_enabled");
    openapiRequiredFields.add("is_premium");
    openapiRequiredFields.add("is_prolong_allowed");
    openapiRequiredFields.add("is_technical");
    openapiRequiredFields.add("is_whois_privacy_enabled");
    openapiRequiredFields.add("linked_ip");
    openapiRequiredFields.add("paid_till");
    openapiRequiredFields.add("person_id");
    openapiRequiredFields.add("premium_prolong_cost");
    openapiRequiredFields.add("provider");
    openapiRequiredFields.add("request_status");
    openapiRequiredFields.add("subdomains");
    openapiRequiredFields.add("tld_id");
  }

 /**
  * Validates the JSON Element and throws an exception if issues found
  *
  * @param jsonElement JSON Element
  * @throws IOException if the JSON Element is invalid with respect to Domain
  */
  public static void validateJsonElement(JsonElement jsonElement) throws IOException {
      if (jsonElement == null) {
        if (!Domain.openapiRequiredFields.isEmpty()) { // has required fields but JSON element is null
          throw new IllegalArgumentException(String.format("The required field(s) %s in Domain is not found in the empty JSON string", Domain.openapiRequiredFields.toString()));
        }
      }

      Set<Entry<String, JsonElement>> entries = jsonElement.getAsJsonObject().entrySet();
      // check to see if the JSON string contains additional fields
      for (Entry<String, JsonElement> entry : entries) {
        if (!Domain.openapiFields.contains(entry.getKey())) {
          throw new IllegalArgumentException(String.format("The field `%s` in the JSON string is not defined in the `Domain` properties. JSON: %s", entry.getKey(), jsonElement.toString()));
        }
      }

      // check to make sure all required properties/fields are present in the JSON string
      for (String requiredField : Domain.openapiRequiredFields) {
        if (jsonElement.getAsJsonObject().get(requiredField) == null) {
          throw new IllegalArgumentException(String.format("The required field `%s` is not found in the JSON string: %s", requiredField, jsonElement.toString()));
        }
      }
        JsonObject jsonObj = jsonElement.getAsJsonObject();
      // ensure the json data is an array
      if (!jsonObj.get("allowed_buy_periods").isJsonArray()) {
        throw new IllegalArgumentException(String.format("Expected the field `allowed_buy_periods` to be an array in the JSON string but got `%s`", jsonObj.get("allowed_buy_periods").toString()));
      }

      JsonArray jsonArrayallowedBuyPeriods = jsonObj.getAsJsonArray("allowed_buy_periods");
      // validate the required field `allowed_buy_periods` (array)
      for (int i = 0; i < jsonArrayallowedBuyPeriods.size(); i++) {
        DomainAllowedBuyPeriodsInner.validateJsonElement(jsonArrayallowedBuyPeriods.get(i));
      };
      if (!jsonObj.get("domain_status").isJsonPrimitive()) {
        throw new IllegalArgumentException(String.format("Expected the field `domain_status` to be a primitive type in the JSON string but got `%s`", jsonObj.get("domain_status").toString()));
      }
      if (!jsonObj.get("expiration").isJsonPrimitive()) {
        throw new IllegalArgumentException(String.format("Expected the field `expiration` to be a primitive type in the JSON string but got `%s`", jsonObj.get("expiration").toString()));
      }
      if (!jsonObj.get("fqdn").isJsonPrimitive()) {
        throw new IllegalArgumentException(String.format("Expected the field `fqdn` to be a primitive type in the JSON string but got `%s`", jsonObj.get("fqdn").toString()));
      }
      if ((jsonObj.get("avatar_link") != null && !jsonObj.get("avatar_link").isJsonNull()) && !jsonObj.get("avatar_link").isJsonPrimitive()) {
        throw new IllegalArgumentException(String.format("Expected the field `avatar_link` to be a primitive type in the JSON string but got `%s`", jsonObj.get("avatar_link").toString()));
      }
      if ((jsonObj.get("linked_ip") != null && !jsonObj.get("linked_ip").isJsonNull()) && !jsonObj.get("linked_ip").isJsonPrimitive()) {
        throw new IllegalArgumentException(String.format("Expected the field `linked_ip` to be a primitive type in the JSON string but got `%s`", jsonObj.get("linked_ip").toString()));
      }
      if ((jsonObj.get("paid_till") != null && !jsonObj.get("paid_till").isJsonNull()) && !jsonObj.get("paid_till").isJsonPrimitive()) {
        throw new IllegalArgumentException(String.format("Expected the field `paid_till` to be a primitive type in the JSON string but got `%s`", jsonObj.get("paid_till").toString()));
      }
      if ((jsonObj.get("provider") != null && !jsonObj.get("provider").isJsonNull()) && !jsonObj.get("provider").isJsonPrimitive()) {
        throw new IllegalArgumentException(String.format("Expected the field `provider` to be a primitive type in the JSON string but got `%s`", jsonObj.get("provider").toString()));
      }
      if ((jsonObj.get("request_status") != null && !jsonObj.get("request_status").isJsonNull()) && !jsonObj.get("request_status").isJsonPrimitive()) {
        throw new IllegalArgumentException(String.format("Expected the field `request_status` to be a primitive type in the JSON string but got `%s`", jsonObj.get("request_status").toString()));
      }
      // ensure the json data is an array
      if (!jsonObj.get("subdomains").isJsonArray()) {
        throw new IllegalArgumentException(String.format("Expected the field `subdomains` to be an array in the JSON string but got `%s`", jsonObj.get("subdomains").toString()));
      }

      JsonArray jsonArraysubdomains = jsonObj.getAsJsonArray("subdomains");
      // validate the required field `subdomains` (array)
      for (int i = 0; i < jsonArraysubdomains.size(); i++) {
        Subdomain.validateJsonElement(jsonArraysubdomains.get(i));
      };
  }

  public static class CustomTypeAdapterFactory implements TypeAdapterFactory {
    @SuppressWarnings("unchecked")
    @Override
    public <T> TypeAdapter<T> create(Gson gson, TypeToken<T> type) {
       if (!Domain.class.isAssignableFrom(type.getRawType())) {
         return null; // this class only serializes 'Domain' and its subtypes
       }
       final TypeAdapter<JsonElement> elementAdapter = gson.getAdapter(JsonElement.class);
       final TypeAdapter<Domain> thisAdapter
                        = gson.getDelegateAdapter(this, TypeToken.get(Domain.class));

       return (TypeAdapter<T>) new TypeAdapter<Domain>() {
           @Override
           public void write(JsonWriter out, Domain value) throws IOException {
             JsonObject obj = thisAdapter.toJsonTree(value).getAsJsonObject();
             elementAdapter.write(out, obj);
           }

           @Override
           public Domain read(JsonReader in) throws IOException {
             JsonElement jsonElement = elementAdapter.read(in);
             validateJsonElement(jsonElement);
             return thisAdapter.fromJsonTree(jsonElement);
           }

       }.nullSafe();
    }
  }

 /**
  * Create an instance of Domain given an JSON string
  *
  * @param jsonString JSON string
  * @return An instance of Domain
  * @throws IOException if the JSON string is invalid with respect to Domain
  */
  public static Domain fromJson(String jsonString) throws IOException {
    return JSON.getGson().fromJson(jsonString, Domain.class);
  }

 /**
  * Convert an instance of Domain to an JSON string
  *
  * @return JSON string
  */
  public String toJson() {
    return JSON.getGson().toJson(this);
  }
}

