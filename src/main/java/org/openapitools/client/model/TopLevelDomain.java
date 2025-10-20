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
import org.openapitools.client.model.TopLevelDomainAllowedBuyPeriodsInner;

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
 * Доменная зона.
 */
@javax.annotation.Generated(value = "org.openapitools.codegen.languages.JavaClientCodegen", date = "2025-10-20T12:50:09.036217Z[Etc/UTC]")
public class TopLevelDomain {
  public static final String SERIALIZED_NAME_ALLOWED_BUY_PERIODS = "allowed_buy_periods";
  @SerializedName(SERIALIZED_NAME_ALLOWED_BUY_PERIODS)
  private List<TopLevelDomainAllowedBuyPeriodsInner> allowedBuyPeriods = new ArrayList<>();

  public static final String SERIALIZED_NAME_EARLY_RENEW_PERIOD = "early_renew_period";
  @SerializedName(SERIALIZED_NAME_EARLY_RENEW_PERIOD)
  private BigDecimal earlyRenewPeriod;

  public static final String SERIALIZED_NAME_GRACE_PERIOD = "grace_period";
  @SerializedName(SERIALIZED_NAME_GRACE_PERIOD)
  private BigDecimal gracePeriod;

  public static final String SERIALIZED_NAME_ID = "id";
  @SerializedName(SERIALIZED_NAME_ID)
  private BigDecimal id;

  public static final String SERIALIZED_NAME_IS_PUBLISHED = "is_published";
  @SerializedName(SERIALIZED_NAME_IS_PUBLISHED)
  private Boolean isPublished;

  public static final String SERIALIZED_NAME_IS_REGISTERED = "is_registered";
  @SerializedName(SERIALIZED_NAME_IS_REGISTERED)
  private Boolean isRegistered;

  public static final String SERIALIZED_NAME_IS_WHOIS_PRIVACY_DEFAULT_ENABLED = "is_whois_privacy_default_enabled";
  @SerializedName(SERIALIZED_NAME_IS_WHOIS_PRIVACY_DEFAULT_ENABLED)
  private Boolean isWhoisPrivacyDefaultEnabled;

  public static final String SERIALIZED_NAME_IS_WHOIS_PRIVACY_ENABLED = "is_whois_privacy_enabled";
  @SerializedName(SERIALIZED_NAME_IS_WHOIS_PRIVACY_ENABLED)
  private Boolean isWhoisPrivacyEnabled;

  public static final String SERIALIZED_NAME_NAME = "name";
  @SerializedName(SERIALIZED_NAME_NAME)
  private String name;

  public static final String SERIALIZED_NAME_PRICE = "price";
  @SerializedName(SERIALIZED_NAME_PRICE)
  private BigDecimal price;

  public static final String SERIALIZED_NAME_PROLONG_PRICE = "prolong_price";
  @SerializedName(SERIALIZED_NAME_PROLONG_PRICE)
  private BigDecimal prolongPrice;

  /**
   * Регистратор доменной зоны.
   */
  @JsonAdapter(RegistrarEnum.Adapter.class)
  public enum RegistrarEnum {
    NIC("NIC"),
    
    PDR("PDR"),
    
    R01("R01"),
    
    TIMEWEB("timeweb"),
    
    TIMEWEBVIRTREG("TimewebVirtreg"),
    
    WEBNAMES("Webnames"),
    
    UNKNOWN("unknown");

    private String value;

    RegistrarEnum(String value) {
      this.value = value;
    }

    public String getValue() {
      return value;
    }

    @Override
    public String toString() {
      return String.valueOf(value);
    }

    public static RegistrarEnum fromValue(String value) {
      for (RegistrarEnum b : RegistrarEnum.values()) {
        if (b.value.equals(value)) {
          return b;
        }
      }
      throw new IllegalArgumentException("Unexpected value '" + value + "'");
    }

    public static class Adapter extends TypeAdapter<RegistrarEnum> {
      @Override
      public void write(final JsonWriter jsonWriter, final RegistrarEnum enumeration) throws IOException {
        jsonWriter.value(enumeration.getValue());
      }

      @Override
      public RegistrarEnum read(final JsonReader jsonReader) throws IOException {
        String value =  jsonReader.nextString();
        return RegistrarEnum.fromValue(value);
      }
    }
  }

  public static final String SERIALIZED_NAME_REGISTRAR = "registrar";
  @SerializedName(SERIALIZED_NAME_REGISTRAR)
  private RegistrarEnum registrar;

  public static final String SERIALIZED_NAME_TRANSFER = "transfer";
  @SerializedName(SERIALIZED_NAME_TRANSFER)
  private BigDecimal transfer;

  public static final String SERIALIZED_NAME_WHOIS_PRIVACY_PRICE = "whois_privacy_price";
  @SerializedName(SERIALIZED_NAME_WHOIS_PRIVACY_PRICE)
  private BigDecimal whoisPrivacyPrice;

  public TopLevelDomain() {
  }

  public TopLevelDomain allowedBuyPeriods(List<TopLevelDomainAllowedBuyPeriodsInner> allowedBuyPeriods) {
    
    this.allowedBuyPeriods = allowedBuyPeriods;
    return this;
  }

  public TopLevelDomain addAllowedBuyPeriodsItem(TopLevelDomainAllowedBuyPeriodsInner allowedBuyPeriodsItem) {
    if (this.allowedBuyPeriods == null) {
      this.allowedBuyPeriods = new ArrayList<>();
    }
    this.allowedBuyPeriods.add(allowedBuyPeriodsItem);
    return this;
  }

   /**
   * Список доступных периодов для регистрации/продления доменов со стоимостью.
   * @return allowedBuyPeriods
  **/
  @javax.annotation.Nonnull
  public List<TopLevelDomainAllowedBuyPeriodsInner> getAllowedBuyPeriods() {
    return allowedBuyPeriods;
  }


  public void setAllowedBuyPeriods(List<TopLevelDomainAllowedBuyPeriodsInner> allowedBuyPeriods) {
    this.allowedBuyPeriods = allowedBuyPeriods;
  }


  public TopLevelDomain earlyRenewPeriod(BigDecimal earlyRenewPeriod) {
    
    this.earlyRenewPeriod = earlyRenewPeriod;
    return this;
  }

   /**
   * Количество дней до истечение срока регистрации, когда можно продлять домен.
   * @return earlyRenewPeriod
  **/
  @javax.annotation.Nullable
  public BigDecimal getEarlyRenewPeriod() {
    return earlyRenewPeriod;
  }


  public void setEarlyRenewPeriod(BigDecimal earlyRenewPeriod) {
    this.earlyRenewPeriod = earlyRenewPeriod;
  }


  public TopLevelDomain gracePeriod(BigDecimal gracePeriod) {
    
    this.gracePeriod = gracePeriod;
    return this;
  }

   /**
   * Количество дней, которые действует льготный период когда вы ещё можете продлить домен, после окончания его регистрации
   * @return gracePeriod
  **/
  @javax.annotation.Nonnull
  public BigDecimal getGracePeriod() {
    return gracePeriod;
  }


  public void setGracePeriod(BigDecimal gracePeriod) {
    this.gracePeriod = gracePeriod;
  }


  public TopLevelDomain id(BigDecimal id) {
    
    this.id = id;
    return this;
  }

   /**
   * ID доменной зоны.
   * @return id
  **/
  @javax.annotation.Nonnull
  public BigDecimal getId() {
    return id;
  }


  public void setId(BigDecimal id) {
    this.id = id;
  }


  public TopLevelDomain isPublished(Boolean isPublished) {
    
    this.isPublished = isPublished;
    return this;
  }

   /**
   * Это логическое значение, которое показывает, опубликована ли доменная зона.
   * @return isPublished
  **/
  @javax.annotation.Nonnull
  public Boolean getIsPublished() {
    return isPublished;
  }


  public void setIsPublished(Boolean isPublished) {
    this.isPublished = isPublished;
  }


  public TopLevelDomain isRegistered(Boolean isRegistered) {
    
    this.isRegistered = isRegistered;
    return this;
  }

   /**
   * Это логическое значение, которое показывает, зарегистрирована ли доменная зона.
   * @return isRegistered
  **/
  @javax.annotation.Nonnull
  public Boolean getIsRegistered() {
    return isRegistered;
  }


  public void setIsRegistered(Boolean isRegistered) {
    this.isRegistered = isRegistered;
  }


  public TopLevelDomain isWhoisPrivacyDefaultEnabled(Boolean isWhoisPrivacyDefaultEnabled) {
    
    this.isWhoisPrivacyDefaultEnabled = isWhoisPrivacyDefaultEnabled;
    return this;
  }

   /**
   * Это логическое значение, которое показывает, включено ли по умолчанию скрытие данных администратора для доменной зоны.
   * @return isWhoisPrivacyDefaultEnabled
  **/
  @javax.annotation.Nonnull
  public Boolean getIsWhoisPrivacyDefaultEnabled() {
    return isWhoisPrivacyDefaultEnabled;
  }


  public void setIsWhoisPrivacyDefaultEnabled(Boolean isWhoisPrivacyDefaultEnabled) {
    this.isWhoisPrivacyDefaultEnabled = isWhoisPrivacyDefaultEnabled;
  }


  public TopLevelDomain isWhoisPrivacyEnabled(Boolean isWhoisPrivacyEnabled) {
    
    this.isWhoisPrivacyEnabled = isWhoisPrivacyEnabled;
    return this;
  }

   /**
   * Это логическое значение, которое показывает, доступно ли управление скрытием данных администратора для доменной зоны.
   * @return isWhoisPrivacyEnabled
  **/
  @javax.annotation.Nonnull
  public Boolean getIsWhoisPrivacyEnabled() {
    return isWhoisPrivacyEnabled;
  }


  public void setIsWhoisPrivacyEnabled(Boolean isWhoisPrivacyEnabled) {
    this.isWhoisPrivacyEnabled = isWhoisPrivacyEnabled;
  }


  public TopLevelDomain name(String name) {
    
    this.name = name;
    return this;
  }

   /**
   * Имя доменной зоны.
   * @return name
  **/
  @javax.annotation.Nonnull
  public String getName() {
    return name;
  }


  public void setName(String name) {
    this.name = name;
  }


  public TopLevelDomain price(BigDecimal price) {
    
    this.price = price;
    return this;
  }

   /**
   * Цена регистрации домена
   * @return price
  **/
  @javax.annotation.Nonnull
  public BigDecimal getPrice() {
    return price;
  }


  public void setPrice(BigDecimal price) {
    this.price = price;
  }


  public TopLevelDomain prolongPrice(BigDecimal prolongPrice) {
    
    this.prolongPrice = prolongPrice;
    return this;
  }

   /**
   * Цена продления домена.
   * @return prolongPrice
  **/
  @javax.annotation.Nonnull
  public BigDecimal getProlongPrice() {
    return prolongPrice;
  }


  public void setProlongPrice(BigDecimal prolongPrice) {
    this.prolongPrice = prolongPrice;
  }


  public TopLevelDomain registrar(RegistrarEnum registrar) {
    
    this.registrar = registrar;
    return this;
  }

   /**
   * Регистратор доменной зоны.
   * @return registrar
  **/
  @javax.annotation.Nonnull
  public RegistrarEnum getRegistrar() {
    return registrar;
  }


  public void setRegistrar(RegistrarEnum registrar) {
    this.registrar = registrar;
  }


  public TopLevelDomain transfer(BigDecimal transfer) {
    
    this.transfer = transfer;
    return this;
  }

   /**
   * Цена услуги трансфера домена.
   * @return transfer
  **/
  @javax.annotation.Nonnull
  public BigDecimal getTransfer() {
    return transfer;
  }


  public void setTransfer(BigDecimal transfer) {
    this.transfer = transfer;
  }


  public TopLevelDomain whoisPrivacyPrice(BigDecimal whoisPrivacyPrice) {
    
    this.whoisPrivacyPrice = whoisPrivacyPrice;
    return this;
  }

   /**
   * Цена услуги скрытия данных администратора для доменной зоны.
   * @return whoisPrivacyPrice
  **/
  @javax.annotation.Nonnull
  public BigDecimal getWhoisPrivacyPrice() {
    return whoisPrivacyPrice;
  }


  public void setWhoisPrivacyPrice(BigDecimal whoisPrivacyPrice) {
    this.whoisPrivacyPrice = whoisPrivacyPrice;
  }



  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    TopLevelDomain topLevelDomain = (TopLevelDomain) o;
    return Objects.equals(this.allowedBuyPeriods, topLevelDomain.allowedBuyPeriods) &&
        Objects.equals(this.earlyRenewPeriod, topLevelDomain.earlyRenewPeriod) &&
        Objects.equals(this.gracePeriod, topLevelDomain.gracePeriod) &&
        Objects.equals(this.id, topLevelDomain.id) &&
        Objects.equals(this.isPublished, topLevelDomain.isPublished) &&
        Objects.equals(this.isRegistered, topLevelDomain.isRegistered) &&
        Objects.equals(this.isWhoisPrivacyDefaultEnabled, topLevelDomain.isWhoisPrivacyDefaultEnabled) &&
        Objects.equals(this.isWhoisPrivacyEnabled, topLevelDomain.isWhoisPrivacyEnabled) &&
        Objects.equals(this.name, topLevelDomain.name) &&
        Objects.equals(this.price, topLevelDomain.price) &&
        Objects.equals(this.prolongPrice, topLevelDomain.prolongPrice) &&
        Objects.equals(this.registrar, topLevelDomain.registrar) &&
        Objects.equals(this.transfer, topLevelDomain.transfer) &&
        Objects.equals(this.whoisPrivacyPrice, topLevelDomain.whoisPrivacyPrice);
  }

  @Override
  public int hashCode() {
    return Objects.hash(allowedBuyPeriods, earlyRenewPeriod, gracePeriod, id, isPublished, isRegistered, isWhoisPrivacyDefaultEnabled, isWhoisPrivacyEnabled, name, price, prolongPrice, registrar, transfer, whoisPrivacyPrice);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class TopLevelDomain {\n");
    sb.append("    allowedBuyPeriods: ").append(toIndentedString(allowedBuyPeriods)).append("\n");
    sb.append("    earlyRenewPeriod: ").append(toIndentedString(earlyRenewPeriod)).append("\n");
    sb.append("    gracePeriod: ").append(toIndentedString(gracePeriod)).append("\n");
    sb.append("    id: ").append(toIndentedString(id)).append("\n");
    sb.append("    isPublished: ").append(toIndentedString(isPublished)).append("\n");
    sb.append("    isRegistered: ").append(toIndentedString(isRegistered)).append("\n");
    sb.append("    isWhoisPrivacyDefaultEnabled: ").append(toIndentedString(isWhoisPrivacyDefaultEnabled)).append("\n");
    sb.append("    isWhoisPrivacyEnabled: ").append(toIndentedString(isWhoisPrivacyEnabled)).append("\n");
    sb.append("    name: ").append(toIndentedString(name)).append("\n");
    sb.append("    price: ").append(toIndentedString(price)).append("\n");
    sb.append("    prolongPrice: ").append(toIndentedString(prolongPrice)).append("\n");
    sb.append("    registrar: ").append(toIndentedString(registrar)).append("\n");
    sb.append("    transfer: ").append(toIndentedString(transfer)).append("\n");
    sb.append("    whoisPrivacyPrice: ").append(toIndentedString(whoisPrivacyPrice)).append("\n");
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
    openapiFields.add("early_renew_period");
    openapiFields.add("grace_period");
    openapiFields.add("id");
    openapiFields.add("is_published");
    openapiFields.add("is_registered");
    openapiFields.add("is_whois_privacy_default_enabled");
    openapiFields.add("is_whois_privacy_enabled");
    openapiFields.add("name");
    openapiFields.add("price");
    openapiFields.add("prolong_price");
    openapiFields.add("registrar");
    openapiFields.add("transfer");
    openapiFields.add("whois_privacy_price");

    // a set of required properties/fields (JSON key names)
    openapiRequiredFields = new HashSet<String>();
    openapiRequiredFields.add("allowed_buy_periods");
    openapiRequiredFields.add("early_renew_period");
    openapiRequiredFields.add("grace_period");
    openapiRequiredFields.add("id");
    openapiRequiredFields.add("is_published");
    openapiRequiredFields.add("is_registered");
    openapiRequiredFields.add("is_whois_privacy_default_enabled");
    openapiRequiredFields.add("is_whois_privacy_enabled");
    openapiRequiredFields.add("name");
    openapiRequiredFields.add("price");
    openapiRequiredFields.add("prolong_price");
    openapiRequiredFields.add("registrar");
    openapiRequiredFields.add("transfer");
    openapiRequiredFields.add("whois_privacy_price");
  }

 /**
  * Validates the JSON Element and throws an exception if issues found
  *
  * @param jsonElement JSON Element
  * @throws IOException if the JSON Element is invalid with respect to TopLevelDomain
  */
  public static void validateJsonElement(JsonElement jsonElement) throws IOException {
      if (jsonElement == null) {
        if (!TopLevelDomain.openapiRequiredFields.isEmpty()) { // has required fields but JSON element is null
          throw new IllegalArgumentException(String.format("The required field(s) %s in TopLevelDomain is not found in the empty JSON string", TopLevelDomain.openapiRequiredFields.toString()));
        }
      }

      Set<Entry<String, JsonElement>> entries = jsonElement.getAsJsonObject().entrySet();
      // check to see if the JSON string contains additional fields
      for (Entry<String, JsonElement> entry : entries) {
        if (!TopLevelDomain.openapiFields.contains(entry.getKey())) {
          throw new IllegalArgumentException(String.format("The field `%s` in the JSON string is not defined in the `TopLevelDomain` properties. JSON: %s", entry.getKey(), jsonElement.toString()));
        }
      }

      // check to make sure all required properties/fields are present in the JSON string
      for (String requiredField : TopLevelDomain.openapiRequiredFields) {
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
        TopLevelDomainAllowedBuyPeriodsInner.validateJsonElement(jsonArrayallowedBuyPeriods.get(i));
      };
      if (!jsonObj.get("name").isJsonPrimitive()) {
        throw new IllegalArgumentException(String.format("Expected the field `name` to be a primitive type in the JSON string but got `%s`", jsonObj.get("name").toString()));
      }
      if (!jsonObj.get("registrar").isJsonPrimitive()) {
        throw new IllegalArgumentException(String.format("Expected the field `registrar` to be a primitive type in the JSON string but got `%s`", jsonObj.get("registrar").toString()));
      }
  }

  public static class CustomTypeAdapterFactory implements TypeAdapterFactory {
    @SuppressWarnings("unchecked")
    @Override
    public <T> TypeAdapter<T> create(Gson gson, TypeToken<T> type) {
       if (!TopLevelDomain.class.isAssignableFrom(type.getRawType())) {
         return null; // this class only serializes 'TopLevelDomain' and its subtypes
       }
       final TypeAdapter<JsonElement> elementAdapter = gson.getAdapter(JsonElement.class);
       final TypeAdapter<TopLevelDomain> thisAdapter
                        = gson.getDelegateAdapter(this, TypeToken.get(TopLevelDomain.class));

       return (TypeAdapter<T>) new TypeAdapter<TopLevelDomain>() {
           @Override
           public void write(JsonWriter out, TopLevelDomain value) throws IOException {
             JsonObject obj = thisAdapter.toJsonTree(value).getAsJsonObject();
             elementAdapter.write(out, obj);
           }

           @Override
           public TopLevelDomain read(JsonReader in) throws IOException {
             JsonElement jsonElement = elementAdapter.read(in);
             validateJsonElement(jsonElement);
             return thisAdapter.fromJsonTree(jsonElement);
           }

       }.nullSafe();
    }
  }

 /**
  * Create an instance of TopLevelDomain given an JSON string
  *
  * @param jsonString JSON string
  * @return An instance of TopLevelDomain
  * @throws IOException if the JSON string is invalid with respect to TopLevelDomain
  */
  public static TopLevelDomain fromJson(String jsonString) throws IOException {
    return JSON.getGson().fromJson(jsonString, TopLevelDomain.class);
  }

 /**
  * Convert an instance of TopLevelDomain to an JSON string
  *
  * @return JSON string
  */
  public String toJson() {
    return JSON.getGson().toJson(this);
  }
}

