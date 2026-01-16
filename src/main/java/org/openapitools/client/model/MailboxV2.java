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
import java.util.ArrayList;
import java.util.List;

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
 * Почтовый ящик (API v2)
 */
@javax.annotation.Generated(value = "org.openapitools.codegen.languages.JavaClientCodegen", date = "2026-01-16T15:24:58.872867Z[Etc/UTC]")
public class MailboxV2 {
  public static final String SERIALIZED_NAME_IDN_NAME = "idn_name";
  @SerializedName(SERIALIZED_NAME_IDN_NAME)
  private String idnName;

  public static final String SERIALIZED_NAME_AUTOREPLY_MESSAGE = "autoreply_message";
  @SerializedName(SERIALIZED_NAME_AUTOREPLY_MESSAGE)
  private String autoreplyMessage;

  public static final String SERIALIZED_NAME_AUTOREPLY_STATUS = "autoreply_status";
  @SerializedName(SERIALIZED_NAME_AUTOREPLY_STATUS)
  private Boolean autoreplyStatus;

  public static final String SERIALIZED_NAME_AUTOREPLY_SUBJECT = "autoreply_subject";
  @SerializedName(SERIALIZED_NAME_AUTOREPLY_SUBJECT)
  private String autoreplySubject;

  public static final String SERIALIZED_NAME_COMMENT = "comment";
  @SerializedName(SERIALIZED_NAME_COMMENT)
  private String comment;

  /**
   * Что делать с письмами, которые попадают в спам
   */
  @JsonAdapter(FilterActionEnum.Adapter.class)
  public enum FilterActionEnum {
    DIRECTORY("directory"),
    
    FORWARD("forward"),
    
    DELETE("delete"),
    
    TAG("tag");

    private String value;

    FilterActionEnum(String value) {
      this.value = value;
    }

    public String getValue() {
      return value;
    }

    @Override
    public String toString() {
      return String.valueOf(value);
    }

    public static FilterActionEnum fromValue(String value) {
      for (FilterActionEnum b : FilterActionEnum.values()) {
        if (b.value.equals(value)) {
          return b;
        }
      }
      throw new IllegalArgumentException("Unexpected value '" + value + "'");
    }

    public static class Adapter extends TypeAdapter<FilterActionEnum> {
      @Override
      public void write(final JsonWriter jsonWriter, final FilterActionEnum enumeration) throws IOException {
        jsonWriter.value(enumeration.getValue());
      }

      @Override
      public FilterActionEnum read(final JsonReader jsonReader) throws IOException {
        String value =  jsonReader.nextString();
        return FilterActionEnum.fromValue(value);
      }
    }
  }

  public static final String SERIALIZED_NAME_FILTER_ACTION = "filter_action";
  @SerializedName(SERIALIZED_NAME_FILTER_ACTION)
  private FilterActionEnum filterAction;

  public static final String SERIALIZED_NAME_FILTER_STATUS = "filter_status";
  @SerializedName(SERIALIZED_NAME_FILTER_STATUS)
  private Boolean filterStatus;

  public static final String SERIALIZED_NAME_FORWARD_LIST = "forward_list";
  @SerializedName(SERIALIZED_NAME_FORWARD_LIST)
  private List<String> forwardList = new ArrayList<>();

  public static final String SERIALIZED_NAME_FORWARD_STATUS = "forward_status";
  @SerializedName(SERIALIZED_NAME_FORWARD_STATUS)
  private Boolean forwardStatus;

  public static final String SERIALIZED_NAME_OUTGOING_CONTROL = "outgoing_control";
  @SerializedName(SERIALIZED_NAME_OUTGOING_CONTROL)
  private Boolean outgoingControl;

  public static final String SERIALIZED_NAME_OUTGOING_EMAIL = "outgoing_email";
  @SerializedName(SERIALIZED_NAME_OUTGOING_EMAIL)
  private String outgoingEmail;

  public static final String SERIALIZED_NAME_PASSWORD = "password";
  @SerializedName(SERIALIZED_NAME_PASSWORD)
  private String password;

  public static final String SERIALIZED_NAME_SPAMBOX = "spambox";
  @SerializedName(SERIALIZED_NAME_SPAMBOX)
  private String spambox;

  public static final String SERIALIZED_NAME_WHITE_LIST = "white_list";
  @SerializedName(SERIALIZED_NAME_WHITE_LIST)
  private List<String> whiteList = new ArrayList<>();

  public static final String SERIALIZED_NAME_WEBMAIL = "webmail";
  @SerializedName(SERIALIZED_NAME_WEBMAIL)
  private Boolean webmail;

  public static final String SERIALIZED_NAME_DOVECOT = "dovecot";
  @SerializedName(SERIALIZED_NAME_DOVECOT)
  private Boolean dovecot;

  public static final String SERIALIZED_NAME_FQDN = "fqdn";
  @SerializedName(SERIALIZED_NAME_FQDN)
  private String fqdn;

  public static final String SERIALIZED_NAME_LEAVE_MESSAGES = "leave_messages";
  @SerializedName(SERIALIZED_NAME_LEAVE_MESSAGES)
  private Boolean leaveMessages;

  public static final String SERIALIZED_NAME_MAILBOX = "mailbox";
  @SerializedName(SERIALIZED_NAME_MAILBOX)
  private String mailbox;

  public static final String SERIALIZED_NAME_OWNER_FULL_NAME = "owner_full_name";
  @SerializedName(SERIALIZED_NAME_OWNER_FULL_NAME)
  private String ownerFullName;

  public MailboxV2() {
  }

  public MailboxV2 idnName(String idnName) {
    
    this.idnName = idnName;
    return this;
  }

   /**
   * IDN домен почтового ящика
   * @return idnName
  **/
  @javax.annotation.Nonnull
  public String getIdnName() {
    return idnName;
  }


  public void setIdnName(String idnName) {
    this.idnName = idnName;
  }


  public MailboxV2 autoreplyMessage(String autoreplyMessage) {
    
    this.autoreplyMessage = autoreplyMessage;
    return this;
  }

   /**
   * Сообщение автоответчика на входящие письма
   * @return autoreplyMessage
  **/
  @javax.annotation.Nonnull
  public String getAutoreplyMessage() {
    return autoreplyMessage;
  }


  public void setAutoreplyMessage(String autoreplyMessage) {
    this.autoreplyMessage = autoreplyMessage;
  }


  public MailboxV2 autoreplyStatus(Boolean autoreplyStatus) {
    
    this.autoreplyStatus = autoreplyStatus;
    return this;
  }

   /**
   * Включен ли автоответчик на входящие письма
   * @return autoreplyStatus
  **/
  @javax.annotation.Nonnull
  public Boolean getAutoreplyStatus() {
    return autoreplyStatus;
  }


  public void setAutoreplyStatus(Boolean autoreplyStatus) {
    this.autoreplyStatus = autoreplyStatus;
  }


  public MailboxV2 autoreplySubject(String autoreplySubject) {
    
    this.autoreplySubject = autoreplySubject;
    return this;
  }

   /**
   * Тема сообщения автоответчика на входящие письма
   * @return autoreplySubject
  **/
  @javax.annotation.Nonnull
  public String getAutoreplySubject() {
    return autoreplySubject;
  }


  public void setAutoreplySubject(String autoreplySubject) {
    this.autoreplySubject = autoreplySubject;
  }


  public MailboxV2 comment(String comment) {
    
    this.comment = comment;
    return this;
  }

   /**
   * Комментарий к почтовому ящику
   * @return comment
  **/
  @javax.annotation.Nonnull
  public String getComment() {
    return comment;
  }


  public void setComment(String comment) {
    this.comment = comment;
  }


  public MailboxV2 filterAction(FilterActionEnum filterAction) {
    
    this.filterAction = filterAction;
    return this;
  }

   /**
   * Что делать с письмами, которые попадают в спам
   * @return filterAction
  **/
  @javax.annotation.Nonnull
  public FilterActionEnum getFilterAction() {
    return filterAction;
  }


  public void setFilterAction(FilterActionEnum filterAction) {
    this.filterAction = filterAction;
  }


  public MailboxV2 filterStatus(Boolean filterStatus) {
    
    this.filterStatus = filterStatus;
    return this;
  }

   /**
   * Включен ли спам-фильтр
   * @return filterStatus
  **/
  @javax.annotation.Nonnull
  public Boolean getFilterStatus() {
    return filterStatus;
  }


  public void setFilterStatus(Boolean filterStatus) {
    this.filterStatus = filterStatus;
  }


  public MailboxV2 forwardList(List<String> forwardList) {
    
    this.forwardList = forwardList;
    return this;
  }

  public MailboxV2 addForwardListItem(String forwardListItem) {
    if (this.forwardList == null) {
      this.forwardList = new ArrayList<>();
    }
    this.forwardList.add(forwardListItem);
    return this;
  }

   /**
   * Список адресов для пересылки входящих писем
   * @return forwardList
  **/
  @javax.annotation.Nonnull
  public List<String> getForwardList() {
    return forwardList;
  }


  public void setForwardList(List<String> forwardList) {
    this.forwardList = forwardList;
  }


  public MailboxV2 forwardStatus(Boolean forwardStatus) {
    
    this.forwardStatus = forwardStatus;
    return this;
  }

   /**
   * Включена ли пересылка входящих писем
   * @return forwardStatus
  **/
  @javax.annotation.Nonnull
  public Boolean getForwardStatus() {
    return forwardStatus;
  }


  public void setForwardStatus(Boolean forwardStatus) {
    this.forwardStatus = forwardStatus;
  }


  public MailboxV2 outgoingControl(Boolean outgoingControl) {
    
    this.outgoingControl = outgoingControl;
    return this;
  }

   /**
   * Включена ли пересылка исходящих писем
   * @return outgoingControl
  **/
  @javax.annotation.Nonnull
  public Boolean getOutgoingControl() {
    return outgoingControl;
  }


  public void setOutgoingControl(Boolean outgoingControl) {
    this.outgoingControl = outgoingControl;
  }


  public MailboxV2 outgoingEmail(String outgoingEmail) {
    
    this.outgoingEmail = outgoingEmail;
    return this;
  }

   /**
   * Адрес для пересылки исходящих писем
   * @return outgoingEmail
  **/
  @javax.annotation.Nonnull
  public String getOutgoingEmail() {
    return outgoingEmail;
  }


  public void setOutgoingEmail(String outgoingEmail) {
    this.outgoingEmail = outgoingEmail;
  }


  public MailboxV2 password(String password) {
    
    this.password = password;
    return this;
  }

   /**
   * Пароль почтового ящика (всегда возвращается пустой строкой)
   * @return password
  **/
  @javax.annotation.Nonnull
  public String getPassword() {
    return password;
  }


  public void setPassword(String password) {
    this.password = password;
  }


  public MailboxV2 spambox(String spambox) {
    
    this.spambox = spambox;
    return this;
  }

   /**
   * Адрес для пересылки спама при выбранном действии forward
   * @return spambox
  **/
  @javax.annotation.Nonnull
  public String getSpambox() {
    return spambox;
  }


  public void setSpambox(String spambox) {
    this.spambox = spambox;
  }


  public MailboxV2 whiteList(List<String> whiteList) {
    
    this.whiteList = whiteList;
    return this;
  }

  public MailboxV2 addWhiteListItem(String whiteListItem) {
    if (this.whiteList == null) {
      this.whiteList = new ArrayList<>();
    }
    this.whiteList.add(whiteListItem);
    return this;
  }

   /**
   * Белый список адресов от которых письма не будут попадать в спам
   * @return whiteList
  **/
  @javax.annotation.Nonnull
  public List<String> getWhiteList() {
    return whiteList;
  }


  public void setWhiteList(List<String> whiteList) {
    this.whiteList = whiteList;
  }


  public MailboxV2 webmail(Boolean webmail) {
    
    this.webmail = webmail;
    return this;
  }

   /**
   * Доступен ли Webmail
   * @return webmail
  **/
  @javax.annotation.Nonnull
  public Boolean getWebmail() {
    return webmail;
  }


  public void setWebmail(Boolean webmail) {
    this.webmail = webmail;
  }


  public MailboxV2 dovecot(Boolean dovecot) {
    
    this.dovecot = dovecot;
    return this;
  }

   /**
   * Есть ли доступ через dovecot
   * @return dovecot
  **/
  @javax.annotation.Nonnull
  public Boolean getDovecot() {
    return dovecot;
  }


  public void setDovecot(Boolean dovecot) {
    this.dovecot = dovecot;
  }


  public MailboxV2 fqdn(String fqdn) {
    
    this.fqdn = fqdn;
    return this;
  }

   /**
   * Домен почты
   * @return fqdn
  **/
  @javax.annotation.Nonnull
  public String getFqdn() {
    return fqdn;
  }


  public void setFqdn(String fqdn) {
    this.fqdn = fqdn;
  }


  public MailboxV2 leaveMessages(Boolean leaveMessages) {
    
    this.leaveMessages = leaveMessages;
    return this;
  }

   /**
   * Оставлять ли сообщения на сервере при пересылке
   * @return leaveMessages
  **/
  @javax.annotation.Nonnull
  public Boolean getLeaveMessages() {
    return leaveMessages;
  }


  public void setLeaveMessages(Boolean leaveMessages) {
    this.leaveMessages = leaveMessages;
  }


  public MailboxV2 mailbox(String mailbox) {
    
    this.mailbox = mailbox;
    return this;
  }

   /**
   * Название почтового ящика
   * @return mailbox
  **/
  @javax.annotation.Nonnull
  public String getMailbox() {
    return mailbox;
  }


  public void setMailbox(String mailbox) {
    this.mailbox = mailbox;
  }


  public MailboxV2 ownerFullName(String ownerFullName) {
    
    this.ownerFullName = ownerFullName;
    return this;
  }

   /**
   * ФИО владельца почтового ящика
   * @return ownerFullName
  **/
  @javax.annotation.Nonnull
  public String getOwnerFullName() {
    return ownerFullName;
  }


  public void setOwnerFullName(String ownerFullName) {
    this.ownerFullName = ownerFullName;
  }



  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    MailboxV2 mailboxV2 = (MailboxV2) o;
    return Objects.equals(this.idnName, mailboxV2.idnName) &&
        Objects.equals(this.autoreplyMessage, mailboxV2.autoreplyMessage) &&
        Objects.equals(this.autoreplyStatus, mailboxV2.autoreplyStatus) &&
        Objects.equals(this.autoreplySubject, mailboxV2.autoreplySubject) &&
        Objects.equals(this.comment, mailboxV2.comment) &&
        Objects.equals(this.filterAction, mailboxV2.filterAction) &&
        Objects.equals(this.filterStatus, mailboxV2.filterStatus) &&
        Objects.equals(this.forwardList, mailboxV2.forwardList) &&
        Objects.equals(this.forwardStatus, mailboxV2.forwardStatus) &&
        Objects.equals(this.outgoingControl, mailboxV2.outgoingControl) &&
        Objects.equals(this.outgoingEmail, mailboxV2.outgoingEmail) &&
        Objects.equals(this.password, mailboxV2.password) &&
        Objects.equals(this.spambox, mailboxV2.spambox) &&
        Objects.equals(this.whiteList, mailboxV2.whiteList) &&
        Objects.equals(this.webmail, mailboxV2.webmail) &&
        Objects.equals(this.dovecot, mailboxV2.dovecot) &&
        Objects.equals(this.fqdn, mailboxV2.fqdn) &&
        Objects.equals(this.leaveMessages, mailboxV2.leaveMessages) &&
        Objects.equals(this.mailbox, mailboxV2.mailbox) &&
        Objects.equals(this.ownerFullName, mailboxV2.ownerFullName);
  }

  @Override
  public int hashCode() {
    return Objects.hash(idnName, autoreplyMessage, autoreplyStatus, autoreplySubject, comment, filterAction, filterStatus, forwardList, forwardStatus, outgoingControl, outgoingEmail, password, spambox, whiteList, webmail, dovecot, fqdn, leaveMessages, mailbox, ownerFullName);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class MailboxV2 {\n");
    sb.append("    idnName: ").append(toIndentedString(idnName)).append("\n");
    sb.append("    autoreplyMessage: ").append(toIndentedString(autoreplyMessage)).append("\n");
    sb.append("    autoreplyStatus: ").append(toIndentedString(autoreplyStatus)).append("\n");
    sb.append("    autoreplySubject: ").append(toIndentedString(autoreplySubject)).append("\n");
    sb.append("    comment: ").append(toIndentedString(comment)).append("\n");
    sb.append("    filterAction: ").append(toIndentedString(filterAction)).append("\n");
    sb.append("    filterStatus: ").append(toIndentedString(filterStatus)).append("\n");
    sb.append("    forwardList: ").append(toIndentedString(forwardList)).append("\n");
    sb.append("    forwardStatus: ").append(toIndentedString(forwardStatus)).append("\n");
    sb.append("    outgoingControl: ").append(toIndentedString(outgoingControl)).append("\n");
    sb.append("    outgoingEmail: ").append(toIndentedString(outgoingEmail)).append("\n");
    sb.append("    password: ").append(toIndentedString(password)).append("\n");
    sb.append("    spambox: ").append(toIndentedString(spambox)).append("\n");
    sb.append("    whiteList: ").append(toIndentedString(whiteList)).append("\n");
    sb.append("    webmail: ").append(toIndentedString(webmail)).append("\n");
    sb.append("    dovecot: ").append(toIndentedString(dovecot)).append("\n");
    sb.append("    fqdn: ").append(toIndentedString(fqdn)).append("\n");
    sb.append("    leaveMessages: ").append(toIndentedString(leaveMessages)).append("\n");
    sb.append("    mailbox: ").append(toIndentedString(mailbox)).append("\n");
    sb.append("    ownerFullName: ").append(toIndentedString(ownerFullName)).append("\n");
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
    openapiFields.add("idn_name");
    openapiFields.add("autoreply_message");
    openapiFields.add("autoreply_status");
    openapiFields.add("autoreply_subject");
    openapiFields.add("comment");
    openapiFields.add("filter_action");
    openapiFields.add("filter_status");
    openapiFields.add("forward_list");
    openapiFields.add("forward_status");
    openapiFields.add("outgoing_control");
    openapiFields.add("outgoing_email");
    openapiFields.add("password");
    openapiFields.add("spambox");
    openapiFields.add("white_list");
    openapiFields.add("webmail");
    openapiFields.add("dovecot");
    openapiFields.add("fqdn");
    openapiFields.add("leave_messages");
    openapiFields.add("mailbox");
    openapiFields.add("owner_full_name");

    // a set of required properties/fields (JSON key names)
    openapiRequiredFields = new HashSet<String>();
    openapiRequiredFields.add("idn_name");
    openapiRequiredFields.add("autoreply_message");
    openapiRequiredFields.add("autoreply_status");
    openapiRequiredFields.add("autoreply_subject");
    openapiRequiredFields.add("comment");
    openapiRequiredFields.add("filter_action");
    openapiRequiredFields.add("filter_status");
    openapiRequiredFields.add("forward_list");
    openapiRequiredFields.add("forward_status");
    openapiRequiredFields.add("outgoing_control");
    openapiRequiredFields.add("outgoing_email");
    openapiRequiredFields.add("password");
    openapiRequiredFields.add("spambox");
    openapiRequiredFields.add("white_list");
    openapiRequiredFields.add("webmail");
    openapiRequiredFields.add("dovecot");
    openapiRequiredFields.add("fqdn");
    openapiRequiredFields.add("leave_messages");
    openapiRequiredFields.add("mailbox");
    openapiRequiredFields.add("owner_full_name");
  }

 /**
  * Validates the JSON Element and throws an exception if issues found
  *
  * @param jsonElement JSON Element
  * @throws IOException if the JSON Element is invalid with respect to MailboxV2
  */
  public static void validateJsonElement(JsonElement jsonElement) throws IOException {
      if (jsonElement == null) {
        if (!MailboxV2.openapiRequiredFields.isEmpty()) { // has required fields but JSON element is null
          throw new IllegalArgumentException(String.format("The required field(s) %s in MailboxV2 is not found in the empty JSON string", MailboxV2.openapiRequiredFields.toString()));
        }
      }

      Set<Entry<String, JsonElement>> entries = jsonElement.getAsJsonObject().entrySet();
      // check to see if the JSON string contains additional fields
      for (Entry<String, JsonElement> entry : entries) {
        if (!MailboxV2.openapiFields.contains(entry.getKey())) {
          throw new IllegalArgumentException(String.format("The field `%s` in the JSON string is not defined in the `MailboxV2` properties. JSON: %s", entry.getKey(), jsonElement.toString()));
        }
      }

      // check to make sure all required properties/fields are present in the JSON string
      for (String requiredField : MailboxV2.openapiRequiredFields) {
        if (jsonElement.getAsJsonObject().get(requiredField) == null) {
          throw new IllegalArgumentException(String.format("The required field `%s` is not found in the JSON string: %s", requiredField, jsonElement.toString()));
        }
      }
        JsonObject jsonObj = jsonElement.getAsJsonObject();
      if (!jsonObj.get("idn_name").isJsonPrimitive()) {
        throw new IllegalArgumentException(String.format("Expected the field `idn_name` to be a primitive type in the JSON string but got `%s`", jsonObj.get("idn_name").toString()));
      }
      if (!jsonObj.get("autoreply_message").isJsonPrimitive()) {
        throw new IllegalArgumentException(String.format("Expected the field `autoreply_message` to be a primitive type in the JSON string but got `%s`", jsonObj.get("autoreply_message").toString()));
      }
      if (!jsonObj.get("autoreply_subject").isJsonPrimitive()) {
        throw new IllegalArgumentException(String.format("Expected the field `autoreply_subject` to be a primitive type in the JSON string but got `%s`", jsonObj.get("autoreply_subject").toString()));
      }
      if (!jsonObj.get("comment").isJsonPrimitive()) {
        throw new IllegalArgumentException(String.format("Expected the field `comment` to be a primitive type in the JSON string but got `%s`", jsonObj.get("comment").toString()));
      }
      if (!jsonObj.get("filter_action").isJsonPrimitive()) {
        throw new IllegalArgumentException(String.format("Expected the field `filter_action` to be a primitive type in the JSON string but got `%s`", jsonObj.get("filter_action").toString()));
      }
      // ensure the required json array is present
      if (jsonObj.get("forward_list") == null) {
        throw new IllegalArgumentException("Expected the field `linkedContent` to be an array in the JSON string but got `null`");
      } else if (!jsonObj.get("forward_list").isJsonArray()) {
        throw new IllegalArgumentException(String.format("Expected the field `forward_list` to be an array in the JSON string but got `%s`", jsonObj.get("forward_list").toString()));
      }
      if (!jsonObj.get("outgoing_email").isJsonPrimitive()) {
        throw new IllegalArgumentException(String.format("Expected the field `outgoing_email` to be a primitive type in the JSON string but got `%s`", jsonObj.get("outgoing_email").toString()));
      }
      if (!jsonObj.get("password").isJsonPrimitive()) {
        throw new IllegalArgumentException(String.format("Expected the field `password` to be a primitive type in the JSON string but got `%s`", jsonObj.get("password").toString()));
      }
      if (!jsonObj.get("spambox").isJsonPrimitive()) {
        throw new IllegalArgumentException(String.format("Expected the field `spambox` to be a primitive type in the JSON string but got `%s`", jsonObj.get("spambox").toString()));
      }
      // ensure the required json array is present
      if (jsonObj.get("white_list") == null) {
        throw new IllegalArgumentException("Expected the field `linkedContent` to be an array in the JSON string but got `null`");
      } else if (!jsonObj.get("white_list").isJsonArray()) {
        throw new IllegalArgumentException(String.format("Expected the field `white_list` to be an array in the JSON string but got `%s`", jsonObj.get("white_list").toString()));
      }
      if (!jsonObj.get("fqdn").isJsonPrimitive()) {
        throw new IllegalArgumentException(String.format("Expected the field `fqdn` to be a primitive type in the JSON string but got `%s`", jsonObj.get("fqdn").toString()));
      }
      if (!jsonObj.get("mailbox").isJsonPrimitive()) {
        throw new IllegalArgumentException(String.format("Expected the field `mailbox` to be a primitive type in the JSON string but got `%s`", jsonObj.get("mailbox").toString()));
      }
      if (!jsonObj.get("owner_full_name").isJsonPrimitive()) {
        throw new IllegalArgumentException(String.format("Expected the field `owner_full_name` to be a primitive type in the JSON string but got `%s`", jsonObj.get("owner_full_name").toString()));
      }
  }

  public static class CustomTypeAdapterFactory implements TypeAdapterFactory {
    @SuppressWarnings("unchecked")
    @Override
    public <T> TypeAdapter<T> create(Gson gson, TypeToken<T> type) {
       if (!MailboxV2.class.isAssignableFrom(type.getRawType())) {
         return null; // this class only serializes 'MailboxV2' and its subtypes
       }
       final TypeAdapter<JsonElement> elementAdapter = gson.getAdapter(JsonElement.class);
       final TypeAdapter<MailboxV2> thisAdapter
                        = gson.getDelegateAdapter(this, TypeToken.get(MailboxV2.class));

       return (TypeAdapter<T>) new TypeAdapter<MailboxV2>() {
           @Override
           public void write(JsonWriter out, MailboxV2 value) throws IOException {
             JsonObject obj = thisAdapter.toJsonTree(value).getAsJsonObject();
             elementAdapter.write(out, obj);
           }

           @Override
           public MailboxV2 read(JsonReader in) throws IOException {
             JsonElement jsonElement = elementAdapter.read(in);
             validateJsonElement(jsonElement);
             return thisAdapter.fromJsonTree(jsonElement);
           }

       }.nullSafe();
    }
  }

 /**
  * Create an instance of MailboxV2 given an JSON string
  *
  * @param jsonString JSON string
  * @return An instance of MailboxV2
  * @throws IOException if the JSON string is invalid with respect to MailboxV2
  */
  public static MailboxV2 fromJson(String jsonString) throws IOException {
    return JSON.getGson().fromJson(jsonString, MailboxV2.class);
  }

 /**
  * Convert an instance of MailboxV2 to an JSON string
  *
  * @return JSON string
  */
  public String toJson() {
    return JSON.getGson().toJson(this);
  }
}

