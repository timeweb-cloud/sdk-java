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
 * MailboxResponse
 */
@javax.annotation.Generated(value = "org.openapitools.codegen.languages.JavaClientCodegen", date = "2026-01-16T13:16:16.584479Z[Etc/UTC]")
public class MailboxResponse {
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
   * Действие фильтра спама
   */
  @JsonAdapter(FilterActionEnum.Adapter.class)
  public enum FilterActionEnum {
    DIRECTORY("directory"),
    
    LABEL("label");

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
  private List<String> forwardList;

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

  public static final String SERIALIZED_NAME_WHITE_LIST = "white_list";
  @SerializedName(SERIALIZED_NAME_WHITE_LIST)
  private List<String> whiteList;

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

  public MailboxResponse() {
  }

  public MailboxResponse idnName(String idnName) {
    
    this.idnName = idnName;
    return this;
  }

   /**
   * IDN имя домена
   * @return idnName
  **/
  @javax.annotation.Nullable
  public String getIdnName() {
    return idnName;
  }


  public void setIdnName(String idnName) {
    this.idnName = idnName;
  }


  public MailboxResponse autoreplyMessage(String autoreplyMessage) {
    
    this.autoreplyMessage = autoreplyMessage;
    return this;
  }

   /**
   * Сообщение автоответчика
   * @return autoreplyMessage
  **/
  @javax.annotation.Nullable
  public String getAutoreplyMessage() {
    return autoreplyMessage;
  }


  public void setAutoreplyMessage(String autoreplyMessage) {
    this.autoreplyMessage = autoreplyMessage;
  }


  public MailboxResponse autoreplyStatus(Boolean autoreplyStatus) {
    
    this.autoreplyStatus = autoreplyStatus;
    return this;
  }

   /**
   * Статус автоответчика
   * @return autoreplyStatus
  **/
  @javax.annotation.Nullable
  public Boolean getAutoreplyStatus() {
    return autoreplyStatus;
  }


  public void setAutoreplyStatus(Boolean autoreplyStatus) {
    this.autoreplyStatus = autoreplyStatus;
  }


  public MailboxResponse autoreplySubject(String autoreplySubject) {
    
    this.autoreplySubject = autoreplySubject;
    return this;
  }

   /**
   * Тема автоответчика
   * @return autoreplySubject
  **/
  @javax.annotation.Nullable
  public String getAutoreplySubject() {
    return autoreplySubject;
  }


  public void setAutoreplySubject(String autoreplySubject) {
    this.autoreplySubject = autoreplySubject;
  }


  public MailboxResponse comment(String comment) {
    
    this.comment = comment;
    return this;
  }

   /**
   * Комментарий
   * @return comment
  **/
  @javax.annotation.Nullable
  public String getComment() {
    return comment;
  }


  public void setComment(String comment) {
    this.comment = comment;
  }


  public MailboxResponse filterAction(FilterActionEnum filterAction) {
    
    this.filterAction = filterAction;
    return this;
  }

   /**
   * Действие фильтра спама
   * @return filterAction
  **/
  @javax.annotation.Nullable
  public FilterActionEnum getFilterAction() {
    return filterAction;
  }


  public void setFilterAction(FilterActionEnum filterAction) {
    this.filterAction = filterAction;
  }


  public MailboxResponse filterStatus(Boolean filterStatus) {
    
    this.filterStatus = filterStatus;
    return this;
  }

   /**
   * Статус фильтра спама
   * @return filterStatus
  **/
  @javax.annotation.Nullable
  public Boolean getFilterStatus() {
    return filterStatus;
  }


  public void setFilterStatus(Boolean filterStatus) {
    this.filterStatus = filterStatus;
  }


  public MailboxResponse forwardList(List<String> forwardList) {
    
    this.forwardList = forwardList;
    return this;
  }

  public MailboxResponse addForwardListItem(String forwardListItem) {
    if (this.forwardList == null) {
      this.forwardList = new ArrayList<>();
    }
    this.forwardList.add(forwardListItem);
    return this;
  }

   /**
   * Список адресов для пересылки
   * @return forwardList
  **/
  @javax.annotation.Nullable
  public List<String> getForwardList() {
    return forwardList;
  }


  public void setForwardList(List<String> forwardList) {
    this.forwardList = forwardList;
  }


  public MailboxResponse forwardStatus(Boolean forwardStatus) {
    
    this.forwardStatus = forwardStatus;
    return this;
  }

   /**
   * Статус пересылки
   * @return forwardStatus
  **/
  @javax.annotation.Nullable
  public Boolean getForwardStatus() {
    return forwardStatus;
  }


  public void setForwardStatus(Boolean forwardStatus) {
    this.forwardStatus = forwardStatus;
  }


  public MailboxResponse outgoingControl(Boolean outgoingControl) {
    
    this.outgoingControl = outgoingControl;
    return this;
  }

   /**
   * Контроль исходящей почты
   * @return outgoingControl
  **/
  @javax.annotation.Nullable
  public Boolean getOutgoingControl() {
    return outgoingControl;
  }


  public void setOutgoingControl(Boolean outgoingControl) {
    this.outgoingControl = outgoingControl;
  }


  public MailboxResponse outgoingEmail(String outgoingEmail) {
    
    this.outgoingEmail = outgoingEmail;
    return this;
  }

   /**
   * Email для исходящих писем
   * @return outgoingEmail
  **/
  @javax.annotation.Nullable
  public String getOutgoingEmail() {
    return outgoingEmail;
  }


  public void setOutgoingEmail(String outgoingEmail) {
    this.outgoingEmail = outgoingEmail;
  }


  public MailboxResponse password(String password) {
    
    this.password = password;
    return this;
  }

   /**
   * Пароль (обычно пустая строка в ответе)
   * @return password
  **/
  @javax.annotation.Nullable
  public String getPassword() {
    return password;
  }


  public void setPassword(String password) {
    this.password = password;
  }


  public MailboxResponse whiteList(List<String> whiteList) {
    
    this.whiteList = whiteList;
    return this;
  }

  public MailboxResponse addWhiteListItem(String whiteListItem) {
    if (this.whiteList == null) {
      this.whiteList = new ArrayList<>();
    }
    this.whiteList.add(whiteListItem);
    return this;
  }

   /**
   * Белый список адресов
   * @return whiteList
  **/
  @javax.annotation.Nullable
  public List<String> getWhiteList() {
    return whiteList;
  }


  public void setWhiteList(List<String> whiteList) {
    this.whiteList = whiteList;
  }


  public MailboxResponse webmail(Boolean webmail) {
    
    this.webmail = webmail;
    return this;
  }

   /**
   * Доступ к веб-почте
   * @return webmail
  **/
  @javax.annotation.Nullable
  public Boolean getWebmail() {
    return webmail;
  }


  public void setWebmail(Boolean webmail) {
    this.webmail = webmail;
  }


  public MailboxResponse dovecot(Boolean dovecot) {
    
    this.dovecot = dovecot;
    return this;
  }

   /**
   * Использование Dovecot
   * @return dovecot
  **/
  @javax.annotation.Nullable
  public Boolean getDovecot() {
    return dovecot;
  }


  public void setDovecot(Boolean dovecot) {
    this.dovecot = dovecot;
  }


  public MailboxResponse fqdn(String fqdn) {
    
    this.fqdn = fqdn;
    return this;
  }

   /**
   * Полное доменное имя
   * @return fqdn
  **/
  @javax.annotation.Nullable
  public String getFqdn() {
    return fqdn;
  }


  public void setFqdn(String fqdn) {
    this.fqdn = fqdn;
  }


  public MailboxResponse leaveMessages(Boolean leaveMessages) {
    
    this.leaveMessages = leaveMessages;
    return this;
  }

   /**
   * Оставлять копии писем при пересылке
   * @return leaveMessages
  **/
  @javax.annotation.Nullable
  public Boolean getLeaveMessages() {
    return leaveMessages;
  }


  public void setLeaveMessages(Boolean leaveMessages) {
    this.leaveMessages = leaveMessages;
  }


  public MailboxResponse mailbox(String mailbox) {
    
    this.mailbox = mailbox;
    return this;
  }

   /**
   * Имя почтового ящика
   * @return mailbox
  **/
  @javax.annotation.Nullable
  public String getMailbox() {
    return mailbox;
  }


  public void setMailbox(String mailbox) {
    this.mailbox = mailbox;
  }


  public MailboxResponse ownerFullName(String ownerFullName) {
    
    this.ownerFullName = ownerFullName;
    return this;
  }

   /**
   * ФИО владельца
   * @return ownerFullName
  **/
  @javax.annotation.Nullable
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
    MailboxResponse mailboxResponse = (MailboxResponse) o;
    return Objects.equals(this.idnName, mailboxResponse.idnName) &&
        Objects.equals(this.autoreplyMessage, mailboxResponse.autoreplyMessage) &&
        Objects.equals(this.autoreplyStatus, mailboxResponse.autoreplyStatus) &&
        Objects.equals(this.autoreplySubject, mailboxResponse.autoreplySubject) &&
        Objects.equals(this.comment, mailboxResponse.comment) &&
        Objects.equals(this.filterAction, mailboxResponse.filterAction) &&
        Objects.equals(this.filterStatus, mailboxResponse.filterStatus) &&
        Objects.equals(this.forwardList, mailboxResponse.forwardList) &&
        Objects.equals(this.forwardStatus, mailboxResponse.forwardStatus) &&
        Objects.equals(this.outgoingControl, mailboxResponse.outgoingControl) &&
        Objects.equals(this.outgoingEmail, mailboxResponse.outgoingEmail) &&
        Objects.equals(this.password, mailboxResponse.password) &&
        Objects.equals(this.whiteList, mailboxResponse.whiteList) &&
        Objects.equals(this.webmail, mailboxResponse.webmail) &&
        Objects.equals(this.dovecot, mailboxResponse.dovecot) &&
        Objects.equals(this.fqdn, mailboxResponse.fqdn) &&
        Objects.equals(this.leaveMessages, mailboxResponse.leaveMessages) &&
        Objects.equals(this.mailbox, mailboxResponse.mailbox) &&
        Objects.equals(this.ownerFullName, mailboxResponse.ownerFullName);
  }

  @Override
  public int hashCode() {
    return Objects.hash(idnName, autoreplyMessage, autoreplyStatus, autoreplySubject, comment, filterAction, filterStatus, forwardList, forwardStatus, outgoingControl, outgoingEmail, password, whiteList, webmail, dovecot, fqdn, leaveMessages, mailbox, ownerFullName);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class MailboxResponse {\n");
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
    openapiFields.add("white_list");
    openapiFields.add("webmail");
    openapiFields.add("dovecot");
    openapiFields.add("fqdn");
    openapiFields.add("leave_messages");
    openapiFields.add("mailbox");
    openapiFields.add("owner_full_name");

    // a set of required properties/fields (JSON key names)
    openapiRequiredFields = new HashSet<String>();
  }

 /**
  * Validates the JSON Element and throws an exception if issues found
  *
  * @param jsonElement JSON Element
  * @throws IOException if the JSON Element is invalid with respect to MailboxResponse
  */
  public static void validateJsonElement(JsonElement jsonElement) throws IOException {
      if (jsonElement == null) {
        if (!MailboxResponse.openapiRequiredFields.isEmpty()) { // has required fields but JSON element is null
          throw new IllegalArgumentException(String.format("The required field(s) %s in MailboxResponse is not found in the empty JSON string", MailboxResponse.openapiRequiredFields.toString()));
        }
      }

      Set<Entry<String, JsonElement>> entries = jsonElement.getAsJsonObject().entrySet();
      // check to see if the JSON string contains additional fields
      for (Entry<String, JsonElement> entry : entries) {
        if (!MailboxResponse.openapiFields.contains(entry.getKey())) {
          throw new IllegalArgumentException(String.format("The field `%s` in the JSON string is not defined in the `MailboxResponse` properties. JSON: %s", entry.getKey(), jsonElement.toString()));
        }
      }
        JsonObject jsonObj = jsonElement.getAsJsonObject();
      if ((jsonObj.get("idn_name") != null && !jsonObj.get("idn_name").isJsonNull()) && !jsonObj.get("idn_name").isJsonPrimitive()) {
        throw new IllegalArgumentException(String.format("Expected the field `idn_name` to be a primitive type in the JSON string but got `%s`", jsonObj.get("idn_name").toString()));
      }
      if ((jsonObj.get("autoreply_message") != null && !jsonObj.get("autoreply_message").isJsonNull()) && !jsonObj.get("autoreply_message").isJsonPrimitive()) {
        throw new IllegalArgumentException(String.format("Expected the field `autoreply_message` to be a primitive type in the JSON string but got `%s`", jsonObj.get("autoreply_message").toString()));
      }
      if ((jsonObj.get("autoreply_subject") != null && !jsonObj.get("autoreply_subject").isJsonNull()) && !jsonObj.get("autoreply_subject").isJsonPrimitive()) {
        throw new IllegalArgumentException(String.format("Expected the field `autoreply_subject` to be a primitive type in the JSON string but got `%s`", jsonObj.get("autoreply_subject").toString()));
      }
      if ((jsonObj.get("comment") != null && !jsonObj.get("comment").isJsonNull()) && !jsonObj.get("comment").isJsonPrimitive()) {
        throw new IllegalArgumentException(String.format("Expected the field `comment` to be a primitive type in the JSON string but got `%s`", jsonObj.get("comment").toString()));
      }
      if ((jsonObj.get("filter_action") != null && !jsonObj.get("filter_action").isJsonNull()) && !jsonObj.get("filter_action").isJsonPrimitive()) {
        throw new IllegalArgumentException(String.format("Expected the field `filter_action` to be a primitive type in the JSON string but got `%s`", jsonObj.get("filter_action").toString()));
      }
      // ensure the optional json data is an array if present
      if (jsonObj.get("forward_list") != null && !jsonObj.get("forward_list").isJsonArray()) {
        throw new IllegalArgumentException(String.format("Expected the field `forward_list` to be an array in the JSON string but got `%s`", jsonObj.get("forward_list").toString()));
      }
      if ((jsonObj.get("outgoing_email") != null && !jsonObj.get("outgoing_email").isJsonNull()) && !jsonObj.get("outgoing_email").isJsonPrimitive()) {
        throw new IllegalArgumentException(String.format("Expected the field `outgoing_email` to be a primitive type in the JSON string but got `%s`", jsonObj.get("outgoing_email").toString()));
      }
      if ((jsonObj.get("password") != null && !jsonObj.get("password").isJsonNull()) && !jsonObj.get("password").isJsonPrimitive()) {
        throw new IllegalArgumentException(String.format("Expected the field `password` to be a primitive type in the JSON string but got `%s`", jsonObj.get("password").toString()));
      }
      // ensure the optional json data is an array if present
      if (jsonObj.get("white_list") != null && !jsonObj.get("white_list").isJsonArray()) {
        throw new IllegalArgumentException(String.format("Expected the field `white_list` to be an array in the JSON string but got `%s`", jsonObj.get("white_list").toString()));
      }
      if ((jsonObj.get("fqdn") != null && !jsonObj.get("fqdn").isJsonNull()) && !jsonObj.get("fqdn").isJsonPrimitive()) {
        throw new IllegalArgumentException(String.format("Expected the field `fqdn` to be a primitive type in the JSON string but got `%s`", jsonObj.get("fqdn").toString()));
      }
      if ((jsonObj.get("mailbox") != null && !jsonObj.get("mailbox").isJsonNull()) && !jsonObj.get("mailbox").isJsonPrimitive()) {
        throw new IllegalArgumentException(String.format("Expected the field `mailbox` to be a primitive type in the JSON string but got `%s`", jsonObj.get("mailbox").toString()));
      }
      if ((jsonObj.get("owner_full_name") != null && !jsonObj.get("owner_full_name").isJsonNull()) && !jsonObj.get("owner_full_name").isJsonPrimitive()) {
        throw new IllegalArgumentException(String.format("Expected the field `owner_full_name` to be a primitive type in the JSON string but got `%s`", jsonObj.get("owner_full_name").toString()));
      }
  }

  public static class CustomTypeAdapterFactory implements TypeAdapterFactory {
    @SuppressWarnings("unchecked")
    @Override
    public <T> TypeAdapter<T> create(Gson gson, TypeToken<T> type) {
       if (!MailboxResponse.class.isAssignableFrom(type.getRawType())) {
         return null; // this class only serializes 'MailboxResponse' and its subtypes
       }
       final TypeAdapter<JsonElement> elementAdapter = gson.getAdapter(JsonElement.class);
       final TypeAdapter<MailboxResponse> thisAdapter
                        = gson.getDelegateAdapter(this, TypeToken.get(MailboxResponse.class));

       return (TypeAdapter<T>) new TypeAdapter<MailboxResponse>() {
           @Override
           public void write(JsonWriter out, MailboxResponse value) throws IOException {
             JsonObject obj = thisAdapter.toJsonTree(value).getAsJsonObject();
             elementAdapter.write(out, obj);
           }

           @Override
           public MailboxResponse read(JsonReader in) throws IOException {
             JsonElement jsonElement = elementAdapter.read(in);
             validateJsonElement(jsonElement);
             return thisAdapter.fromJsonTree(jsonElement);
           }

       }.nullSafe();
    }
  }

 /**
  * Create an instance of MailboxResponse given an JSON string
  *
  * @param jsonString JSON string
  * @return An instance of MailboxResponse
  * @throws IOException if the JSON string is invalid with respect to MailboxResponse
  */
  public static MailboxResponse fromJson(String jsonString) throws IOException {
    return JSON.getGson().fromJson(jsonString, MailboxResponse.class);
  }

 /**
  * Convert an instance of MailboxResponse to an JSON string
  *
  * @return JSON string
  */
  public String toJson() {
    return JSON.getGson().toJson(this);
  }
}

