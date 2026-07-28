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
 * Person
 */
@javax.annotation.Generated(value = "org.openapitools.codegen.languages.JavaClientCodegen", date = "2026-07-28T16:28:31.821697Z[Etc/UTC]")
public class Person {
  public static final String SERIALIZED_NAME_ID = "id";
  @SerializedName(SERIALIZED_NAME_ID)
  private Integer id;

  /**
   * Тип администратора: &#x60;person&#x60; — физическое лицо, &#x60;org&#x60; — организация, &#x60;ip&#x60; — индивидуальный предприниматель.
   */
  @JsonAdapter(TypeEnum.Adapter.class)
  public enum TypeEnum {
    PERSON("person"),
    
    ORG("org"),
    
    IP("ip");

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

  public static final String SERIALIZED_NAME_NAME = "name";
  @SerializedName(SERIALIZED_NAME_NAME)
  private String name;

  public static final String SERIALIZED_NAME_NAME_ENG = "name_eng";
  @SerializedName(SERIALIZED_NAME_NAME_ENG)
  private String nameEng;

  public static final String SERIALIZED_NAME_EMAIL = "email";
  @SerializedName(SERIALIZED_NAME_EMAIL)
  private String email;

  public static final String SERIALIZED_NAME_PHONE = "phone";
  @SerializedName(SERIALIZED_NAME_PHONE)
  private String phone;

  public static final String SERIALIZED_NAME_POSTCODE = "postcode";
  @SerializedName(SERIALIZED_NAME_POSTCODE)
  private String postcode;

  public static final String SERIALIZED_NAME_MAILING_ADDRESS = "mailing_address";
  @SerializedName(SERIALIZED_NAME_MAILING_ADDRESS)
  private String mailingAddress;

  public static final String SERIALIZED_NAME_COUNTRY_CODE = "country_code";
  @SerializedName(SERIALIZED_NAME_COUNTRY_CODE)
  private String countryCode;

  public static final String SERIALIZED_NAME_IS_RESIDENT = "is_resident";
  @SerializedName(SERIALIZED_NAME_IS_RESIDENT)
  private Boolean isResident;

  public static final String SERIALIZED_NAME_IS_BLANK = "is_blank";
  @SerializedName(SERIALIZED_NAME_IS_BLANK)
  private Boolean isBlank;

  public static final String SERIALIZED_NAME_IS_CLOSED = "is_closed";
  @SerializedName(SERIALIZED_NAME_IS_CLOSED)
  private Boolean isClosed;

  public static final String SERIALIZED_NAME_BIRTHDATE = "birthdate";
  @SerializedName(SERIALIZED_NAME_BIRTHDATE)
  private String birthdate;

  public static final String SERIALIZED_NAME_PASSPORT_DATE = "passport_date";
  @SerializedName(SERIALIZED_NAME_PASSPORT_DATE)
  private String passportDate;

  public static final String SERIALIZED_NAME_PASSPORT_NUMBER = "passport_number";
  @SerializedName(SERIALIZED_NAME_PASSPORT_NUMBER)
  private String passportNumber;

  public static final String SERIALIZED_NAME_PASSPORT_PLACE = "passport_place";
  @SerializedName(SERIALIZED_NAME_PASSPORT_PLACE)
  private String passportPlace;

  public static final String SERIALIZED_NAME_PASSPORT_SERIES = "passport_series";
  @SerializedName(SERIALIZED_NAME_PASSPORT_SERIES)
  private String passportSeries;

  public static final String SERIALIZED_NAME_INN = "inn";
  @SerializedName(SERIALIZED_NAME_INN)
  private String inn;

  public static final String SERIALIZED_NAME_KPP = "kpp";
  @SerializedName(SERIALIZED_NAME_KPP)
  private String kpp;

  public static final String SERIALIZED_NAME_LEGAL_ADDRESS = "legal_address";
  @SerializedName(SERIALIZED_NAME_LEGAL_ADDRESS)
  private String legalAddress;

  public static final String SERIALIZED_NAME_CONTACT_NAME = "contact_name";
  @SerializedName(SERIALIZED_NAME_CONTACT_NAME)
  private String contactName;

  public Person() {
  }

  public Person id(Integer id) {
    
    this.id = id;
    return this;
  }

   /**
   * Уникальный идентификатор администратора домена.
   * @return id
  **/
  @javax.annotation.Nonnull
  public Integer getId() {
    return id;
  }


  public void setId(Integer id) {
    this.id = id;
  }


  public Person type(TypeEnum type) {
    
    this.type = type;
    return this;
  }

   /**
   * Тип администратора: &#x60;person&#x60; — физическое лицо, &#x60;org&#x60; — организация, &#x60;ip&#x60; — индивидуальный предприниматель.
   * @return type
  **/
  @javax.annotation.Nonnull
  public TypeEnum getType() {
    return type;
  }


  public void setType(TypeEnum type) {
    this.type = type;
  }


  public Person name(String name) {
    
    this.name = name;
    return this;
  }

   /**
   * Имя администратора (ФИО или название организации).
   * @return name
  **/
  @javax.annotation.Nonnull
  public String getName() {
    return name;
  }


  public void setName(String name) {
    this.name = name;
  }


  public Person nameEng(String nameEng) {
    
    this.nameEng = nameEng;
    return this;
  }

   /**
   * Имя администратора в латинской транслитерации.
   * @return nameEng
  **/
  @javax.annotation.Nonnull
  public String getNameEng() {
    return nameEng;
  }


  public void setNameEng(String nameEng) {
    this.nameEng = nameEng;
  }


  public Person email(String email) {
    
    this.email = email;
    return this;
  }

   /**
   * Адрес электронной почты администратора.
   * @return email
  **/
  @javax.annotation.Nonnull
  public String getEmail() {
    return email;
  }


  public void setEmail(String email) {
    this.email = email;
  }


  public Person phone(String phone) {
    
    this.phone = phone;
    return this;
  }

   /**
   * Контактный телефон администратора.
   * @return phone
  **/
  @javax.annotation.Nonnull
  public String getPhone() {
    return phone;
  }


  public void setPhone(String phone) {
    this.phone = phone;
  }


  public Person postcode(String postcode) {
    
    this.postcode = postcode;
    return this;
  }

   /**
   * Почтовый индекс.
   * @return postcode
  **/
  @javax.annotation.Nonnull
  public String getPostcode() {
    return postcode;
  }


  public void setPostcode(String postcode) {
    this.postcode = postcode;
  }


  public Person mailingAddress(String mailingAddress) {
    
    this.mailingAddress = mailingAddress;
    return this;
  }

   /**
   * Почтовый адрес.
   * @return mailingAddress
  **/
  @javax.annotation.Nonnull
  public String getMailingAddress() {
    return mailingAddress;
  }


  public void setMailingAddress(String mailingAddress) {
    this.mailingAddress = mailingAddress;
  }


  public Person countryCode(String countryCode) {
    
    this.countryCode = countryCode;
    return this;
  }

   /**
   * Код страны администратора.
   * @return countryCode
  **/
  @javax.annotation.Nullable
  public String getCountryCode() {
    return countryCode;
  }


  public void setCountryCode(String countryCode) {
    this.countryCode = countryCode;
  }


  public Person isResident(Boolean isResident) {
    
    this.isResident = isResident;
    return this;
  }

   /**
   * Это логическое значение, которое показывает, является ли администратор резидентом РФ.
   * @return isResident
  **/
  @javax.annotation.Nonnull
  public Boolean getIsResident() {
    return isResident;
  }


  public void setIsResident(Boolean isResident) {
    this.isResident = isResident;
  }


  public Person isBlank(Boolean isBlank) {
    
    this.isBlank = isBlank;
    return this;
  }

   /**
   * Это логическое значение, которое показывает, заполнены ли данные администратора не полностью.
   * @return isBlank
  **/
  @javax.annotation.Nonnull
  public Boolean getIsBlank() {
    return isBlank;
  }


  public void setIsBlank(Boolean isBlank) {
    this.isBlank = isBlank;
  }


  public Person isClosed(Boolean isClosed) {
    
    this.isClosed = isClosed;
    return this;
  }

   /**
   * Это логическое значение, которое показывает, закрыт ли администратор.
   * @return isClosed
  **/
  @javax.annotation.Nonnull
  public Boolean getIsClosed() {
    return isClosed;
  }


  public void setIsClosed(Boolean isClosed) {
    this.isClosed = isClosed;
  }


  public Person birthdate(String birthdate) {
    
    this.birthdate = birthdate;
    return this;
  }

   /**
   * Дата рождения. Только для типов &#x60;person&#x60; и &#x60;ip&#x60;.
   * @return birthdate
  **/
  @javax.annotation.Nullable
  public String getBirthdate() {
    return birthdate;
  }


  public void setBirthdate(String birthdate) {
    this.birthdate = birthdate;
  }


  public Person passportDate(String passportDate) {
    
    this.passportDate = passportDate;
    return this;
  }

   /**
   * Дата выдачи паспорта. Только для типов &#x60;person&#x60; и &#x60;ip&#x60;.
   * @return passportDate
  **/
  @javax.annotation.Nullable
  public String getPassportDate() {
    return passportDate;
  }


  public void setPassportDate(String passportDate) {
    this.passportDate = passportDate;
  }


  public Person passportNumber(String passportNumber) {
    
    this.passportNumber = passportNumber;
    return this;
  }

   /**
   * Номер паспорта. Только для типов &#x60;person&#x60; и &#x60;ip&#x60;.
   * @return passportNumber
  **/
  @javax.annotation.Nullable
  public String getPassportNumber() {
    return passportNumber;
  }


  public void setPassportNumber(String passportNumber) {
    this.passportNumber = passportNumber;
  }


  public Person passportPlace(String passportPlace) {
    
    this.passportPlace = passportPlace;
    return this;
  }

   /**
   * Кем выдан паспорт. Только для типов &#x60;person&#x60; и &#x60;ip&#x60;.
   * @return passportPlace
  **/
  @javax.annotation.Nullable
  public String getPassportPlace() {
    return passportPlace;
  }


  public void setPassportPlace(String passportPlace) {
    this.passportPlace = passportPlace;
  }


  public Person passportSeries(String passportSeries) {
    
    this.passportSeries = passportSeries;
    return this;
  }

   /**
   * Серия паспорта. Только для типов &#x60;person&#x60; и &#x60;ip&#x60;.
   * @return passportSeries
  **/
  @javax.annotation.Nullable
  public String getPassportSeries() {
    return passportSeries;
  }


  public void setPassportSeries(String passportSeries) {
    this.passportSeries = passportSeries;
  }


  public Person inn(String inn) {
    
    this.inn = inn;
    return this;
  }

   /**
   * ИНН. Только для типов &#x60;org&#x60; и &#x60;ip&#x60;.
   * @return inn
  **/
  @javax.annotation.Nullable
  public String getInn() {
    return inn;
  }


  public void setInn(String inn) {
    this.inn = inn;
  }


  public Person kpp(String kpp) {
    
    this.kpp = kpp;
    return this;
  }

   /**
   * КПП организации. Только для типа &#x60;org&#x60;.
   * @return kpp
  **/
  @javax.annotation.Nullable
  public String getKpp() {
    return kpp;
  }


  public void setKpp(String kpp) {
    this.kpp = kpp;
  }


  public Person legalAddress(String legalAddress) {
    
    this.legalAddress = legalAddress;
    return this;
  }

   /**
   * Юридический адрес организации. Только для типа &#x60;org&#x60;.
   * @return legalAddress
  **/
  @javax.annotation.Nullable
  public String getLegalAddress() {
    return legalAddress;
  }


  public void setLegalAddress(String legalAddress) {
    this.legalAddress = legalAddress;
  }


  public Person contactName(String contactName) {
    
    this.contactName = contactName;
    return this;
  }

   /**
   * Контактное лицо организации. Только для типа &#x60;org&#x60;.
   * @return contactName
  **/
  @javax.annotation.Nullable
  public String getContactName() {
    return contactName;
  }


  public void setContactName(String contactName) {
    this.contactName = contactName;
  }



  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Person person = (Person) o;
    return Objects.equals(this.id, person.id) &&
        Objects.equals(this.type, person.type) &&
        Objects.equals(this.name, person.name) &&
        Objects.equals(this.nameEng, person.nameEng) &&
        Objects.equals(this.email, person.email) &&
        Objects.equals(this.phone, person.phone) &&
        Objects.equals(this.postcode, person.postcode) &&
        Objects.equals(this.mailingAddress, person.mailingAddress) &&
        Objects.equals(this.countryCode, person.countryCode) &&
        Objects.equals(this.isResident, person.isResident) &&
        Objects.equals(this.isBlank, person.isBlank) &&
        Objects.equals(this.isClosed, person.isClosed) &&
        Objects.equals(this.birthdate, person.birthdate) &&
        Objects.equals(this.passportDate, person.passportDate) &&
        Objects.equals(this.passportNumber, person.passportNumber) &&
        Objects.equals(this.passportPlace, person.passportPlace) &&
        Objects.equals(this.passportSeries, person.passportSeries) &&
        Objects.equals(this.inn, person.inn) &&
        Objects.equals(this.kpp, person.kpp) &&
        Objects.equals(this.legalAddress, person.legalAddress) &&
        Objects.equals(this.contactName, person.contactName);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, type, name, nameEng, email, phone, postcode, mailingAddress, countryCode, isResident, isBlank, isClosed, birthdate, passportDate, passportNumber, passportPlace, passportSeries, inn, kpp, legalAddress, contactName);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class Person {\n");
    sb.append("    id: ").append(toIndentedString(id)).append("\n");
    sb.append("    type: ").append(toIndentedString(type)).append("\n");
    sb.append("    name: ").append(toIndentedString(name)).append("\n");
    sb.append("    nameEng: ").append(toIndentedString(nameEng)).append("\n");
    sb.append("    email: ").append(toIndentedString(email)).append("\n");
    sb.append("    phone: ").append(toIndentedString(phone)).append("\n");
    sb.append("    postcode: ").append(toIndentedString(postcode)).append("\n");
    sb.append("    mailingAddress: ").append(toIndentedString(mailingAddress)).append("\n");
    sb.append("    countryCode: ").append(toIndentedString(countryCode)).append("\n");
    sb.append("    isResident: ").append(toIndentedString(isResident)).append("\n");
    sb.append("    isBlank: ").append(toIndentedString(isBlank)).append("\n");
    sb.append("    isClosed: ").append(toIndentedString(isClosed)).append("\n");
    sb.append("    birthdate: ").append(toIndentedString(birthdate)).append("\n");
    sb.append("    passportDate: ").append(toIndentedString(passportDate)).append("\n");
    sb.append("    passportNumber: ").append(toIndentedString(passportNumber)).append("\n");
    sb.append("    passportPlace: ").append(toIndentedString(passportPlace)).append("\n");
    sb.append("    passportSeries: ").append(toIndentedString(passportSeries)).append("\n");
    sb.append("    inn: ").append(toIndentedString(inn)).append("\n");
    sb.append("    kpp: ").append(toIndentedString(kpp)).append("\n");
    sb.append("    legalAddress: ").append(toIndentedString(legalAddress)).append("\n");
    sb.append("    contactName: ").append(toIndentedString(contactName)).append("\n");
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
    openapiFields.add("type");
    openapiFields.add("name");
    openapiFields.add("name_eng");
    openapiFields.add("email");
    openapiFields.add("phone");
    openapiFields.add("postcode");
    openapiFields.add("mailing_address");
    openapiFields.add("country_code");
    openapiFields.add("is_resident");
    openapiFields.add("is_blank");
    openapiFields.add("is_closed");
    openapiFields.add("birthdate");
    openapiFields.add("passport_date");
    openapiFields.add("passport_number");
    openapiFields.add("passport_place");
    openapiFields.add("passport_series");
    openapiFields.add("inn");
    openapiFields.add("kpp");
    openapiFields.add("legal_address");
    openapiFields.add("contact_name");

    // a set of required properties/fields (JSON key names)
    openapiRequiredFields = new HashSet<String>();
    openapiRequiredFields.add("id");
    openapiRequiredFields.add("type");
    openapiRequiredFields.add("name");
    openapiRequiredFields.add("name_eng");
    openapiRequiredFields.add("email");
    openapiRequiredFields.add("phone");
    openapiRequiredFields.add("postcode");
    openapiRequiredFields.add("mailing_address");
    openapiRequiredFields.add("country_code");
    openapiRequiredFields.add("is_resident");
    openapiRequiredFields.add("is_blank");
    openapiRequiredFields.add("is_closed");
  }

 /**
  * Validates the JSON Element and throws an exception if issues found
  *
  * @param jsonElement JSON Element
  * @throws IOException if the JSON Element is invalid with respect to Person
  */
  public static void validateJsonElement(JsonElement jsonElement) throws IOException {
      if (jsonElement == null) {
        if (!Person.openapiRequiredFields.isEmpty()) { // has required fields but JSON element is null
          throw new IllegalArgumentException(String.format("The required field(s) %s in Person is not found in the empty JSON string", Person.openapiRequiredFields.toString()));
        }
      }

      Set<Entry<String, JsonElement>> entries = jsonElement.getAsJsonObject().entrySet();
      // check to see if the JSON string contains additional fields
      for (Entry<String, JsonElement> entry : entries) {
        if (!Person.openapiFields.contains(entry.getKey())) {
          throw new IllegalArgumentException(String.format("The field `%s` in the JSON string is not defined in the `Person` properties. JSON: %s", entry.getKey(), jsonElement.toString()));
        }
      }

      // check to make sure all required properties/fields are present in the JSON string
      for (String requiredField : Person.openapiRequiredFields) {
        if (jsonElement.getAsJsonObject().get(requiredField) == null) {
          throw new IllegalArgumentException(String.format("The required field `%s` is not found in the JSON string: %s", requiredField, jsonElement.toString()));
        }
      }
        JsonObject jsonObj = jsonElement.getAsJsonObject();
      if (!jsonObj.get("type").isJsonPrimitive()) {
        throw new IllegalArgumentException(String.format("Expected the field `type` to be a primitive type in the JSON string but got `%s`", jsonObj.get("type").toString()));
      }
      if (!jsonObj.get("name").isJsonPrimitive()) {
        throw new IllegalArgumentException(String.format("Expected the field `name` to be a primitive type in the JSON string but got `%s`", jsonObj.get("name").toString()));
      }
      if (!jsonObj.get("name_eng").isJsonPrimitive()) {
        throw new IllegalArgumentException(String.format("Expected the field `name_eng` to be a primitive type in the JSON string but got `%s`", jsonObj.get("name_eng").toString()));
      }
      if (!jsonObj.get("email").isJsonPrimitive()) {
        throw new IllegalArgumentException(String.format("Expected the field `email` to be a primitive type in the JSON string but got `%s`", jsonObj.get("email").toString()));
      }
      if (!jsonObj.get("phone").isJsonPrimitive()) {
        throw new IllegalArgumentException(String.format("Expected the field `phone` to be a primitive type in the JSON string but got `%s`", jsonObj.get("phone").toString()));
      }
      if (!jsonObj.get("postcode").isJsonPrimitive()) {
        throw new IllegalArgumentException(String.format("Expected the field `postcode` to be a primitive type in the JSON string but got `%s`", jsonObj.get("postcode").toString()));
      }
      if (!jsonObj.get("mailing_address").isJsonPrimitive()) {
        throw new IllegalArgumentException(String.format("Expected the field `mailing_address` to be a primitive type in the JSON string but got `%s`", jsonObj.get("mailing_address").toString()));
      }
      if ((jsonObj.get("country_code") != null && !jsonObj.get("country_code").isJsonNull()) && !jsonObj.get("country_code").isJsonPrimitive()) {
        throw new IllegalArgumentException(String.format("Expected the field `country_code` to be a primitive type in the JSON string but got `%s`", jsonObj.get("country_code").toString()));
      }
      if ((jsonObj.get("birthdate") != null && !jsonObj.get("birthdate").isJsonNull()) && !jsonObj.get("birthdate").isJsonPrimitive()) {
        throw new IllegalArgumentException(String.format("Expected the field `birthdate` to be a primitive type in the JSON string but got `%s`", jsonObj.get("birthdate").toString()));
      }
      if ((jsonObj.get("passport_date") != null && !jsonObj.get("passport_date").isJsonNull()) && !jsonObj.get("passport_date").isJsonPrimitive()) {
        throw new IllegalArgumentException(String.format("Expected the field `passport_date` to be a primitive type in the JSON string but got `%s`", jsonObj.get("passport_date").toString()));
      }
      if ((jsonObj.get("passport_number") != null && !jsonObj.get("passport_number").isJsonNull()) && !jsonObj.get("passport_number").isJsonPrimitive()) {
        throw new IllegalArgumentException(String.format("Expected the field `passport_number` to be a primitive type in the JSON string but got `%s`", jsonObj.get("passport_number").toString()));
      }
      if ((jsonObj.get("passport_place") != null && !jsonObj.get("passport_place").isJsonNull()) && !jsonObj.get("passport_place").isJsonPrimitive()) {
        throw new IllegalArgumentException(String.format("Expected the field `passport_place` to be a primitive type in the JSON string but got `%s`", jsonObj.get("passport_place").toString()));
      }
      if ((jsonObj.get("passport_series") != null && !jsonObj.get("passport_series").isJsonNull()) && !jsonObj.get("passport_series").isJsonPrimitive()) {
        throw new IllegalArgumentException(String.format("Expected the field `passport_series` to be a primitive type in the JSON string but got `%s`", jsonObj.get("passport_series").toString()));
      }
      if ((jsonObj.get("inn") != null && !jsonObj.get("inn").isJsonNull()) && !jsonObj.get("inn").isJsonPrimitive()) {
        throw new IllegalArgumentException(String.format("Expected the field `inn` to be a primitive type in the JSON string but got `%s`", jsonObj.get("inn").toString()));
      }
      if ((jsonObj.get("kpp") != null && !jsonObj.get("kpp").isJsonNull()) && !jsonObj.get("kpp").isJsonPrimitive()) {
        throw new IllegalArgumentException(String.format("Expected the field `kpp` to be a primitive type in the JSON string but got `%s`", jsonObj.get("kpp").toString()));
      }
      if ((jsonObj.get("legal_address") != null && !jsonObj.get("legal_address").isJsonNull()) && !jsonObj.get("legal_address").isJsonPrimitive()) {
        throw new IllegalArgumentException(String.format("Expected the field `legal_address` to be a primitive type in the JSON string but got `%s`", jsonObj.get("legal_address").toString()));
      }
      if ((jsonObj.get("contact_name") != null && !jsonObj.get("contact_name").isJsonNull()) && !jsonObj.get("contact_name").isJsonPrimitive()) {
        throw new IllegalArgumentException(String.format("Expected the field `contact_name` to be a primitive type in the JSON string but got `%s`", jsonObj.get("contact_name").toString()));
      }
  }

  public static class CustomTypeAdapterFactory implements TypeAdapterFactory {
    @SuppressWarnings("unchecked")
    @Override
    public <T> TypeAdapter<T> create(Gson gson, TypeToken<T> type) {
       if (!Person.class.isAssignableFrom(type.getRawType())) {
         return null; // this class only serializes 'Person' and its subtypes
       }
       final TypeAdapter<JsonElement> elementAdapter = gson.getAdapter(JsonElement.class);
       final TypeAdapter<Person> thisAdapter
                        = gson.getDelegateAdapter(this, TypeToken.get(Person.class));

       return (TypeAdapter<T>) new TypeAdapter<Person>() {
           @Override
           public void write(JsonWriter out, Person value) throws IOException {
             JsonObject obj = thisAdapter.toJsonTree(value).getAsJsonObject();
             elementAdapter.write(out, obj);
           }

           @Override
           public Person read(JsonReader in) throws IOException {
             JsonElement jsonElement = elementAdapter.read(in);
             validateJsonElement(jsonElement);
             return thisAdapter.fromJsonTree(jsonElement);
           }

       }.nullSafe();
    }
  }

 /**
  * Create an instance of Person given an JSON string
  *
  * @param jsonString JSON string
  * @return An instance of Person
  * @throws IOException if the JSON string is invalid with respect to Person
  */
  public static Person fromJson(String jsonString) throws IOException {
    return JSON.getGson().fromJson(jsonString, Person.class);
  }

 /**
  * Convert an instance of Person to an JSON string
  *
  * @return JSON string
  */
  public String toJson() {
    return JSON.getGson().toJson(this);
  }
}

