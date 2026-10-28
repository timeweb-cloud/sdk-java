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
import java.net.URI;
import java.util.LinkedHashSet;
import java.util.Set;

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
 * Настройки виджета чата агента для встраивания на сайт
 */
@javax.annotation.Generated(value = "org.openapitools.codegen.languages.JavaClientCodegen", date = "2026-10-28T15:56:17.696691Z[Etc/UTC]")
public class AgentWidgetSettings {
  public static final String SERIALIZED_NAME_WHITELIST_DOMAINS = "whitelist_domains";
  @SerializedName(SERIALIZED_NAME_WHITELIST_DOMAINS)
  private Set<String> whitelistDomains = new LinkedHashSet<>();

  public static final String SERIALIZED_NAME_NAME = "name";
  @SerializedName(SERIALIZED_NAME_NAME)
  private String name;

  public static final String SERIALIZED_NAME_SIGNATURE = "signature";
  @SerializedName(SERIALIZED_NAME_SIGNATURE)
  private String signature;

  public static final String SERIALIZED_NAME_WELCOME_MESSAGE = "welcome_message";
  @SerializedName(SERIALIZED_NAME_WELCOME_MESSAGE)
  private String welcomeMessage;

  public static final String SERIALIZED_NAME_PRIMARY_COLOR = "primary_color";
  @SerializedName(SERIALIZED_NAME_PRIMARY_COLOR)
  private String primaryColor;

  public static final String SERIALIZED_NAME_TEXT_COLOR = "text_color";
  @SerializedName(SERIALIZED_NAME_TEXT_COLOR)
  private String textColor;

  public static final String SERIALIZED_NAME_BACKGROUND_COLOR = "background_color";
  @SerializedName(SERIALIZED_NAME_BACKGROUND_COLOR)
  private String backgroundColor;

  public static final String SERIALIZED_NAME_HEADER_FOOTER_COLOR = "header_footer_color";
  @SerializedName(SERIALIZED_NAME_HEADER_FOOTER_COLOR)
  private String headerFooterColor;

  public static final String SERIALIZED_NAME_FONT = "font";
  @SerializedName(SERIALIZED_NAME_FONT)
  private String font;

  public static final String SERIALIZED_NAME_ICON_URL = "icon_url";
  @SerializedName(SERIALIZED_NAME_ICON_URL)
  private URI iconUrl;

  public static final String SERIALIZED_NAME_USE_AVATAR = "use_avatar";
  @SerializedName(SERIALIZED_NAME_USE_AVATAR)
  private Boolean useAvatar = false;

  public static final String SERIALIZED_NAME_SHOW_BUTTON = "show_button";
  @SerializedName(SERIALIZED_NAME_SHOW_BUTTON)
  private Boolean showButton = true;

  public static final String SERIALIZED_NAME_SHOW_TOOL_PROCESSING = "show_tool_processing";
  @SerializedName(SERIALIZED_NAME_SHOW_TOOL_PROCESSING)
  private Boolean showToolProcessing = false;

  public static final String SERIALIZED_NAME_SHOW_CONFIRMATION_BUTTONS = "show_confirmation_buttons";
  @SerializedName(SERIALIZED_NAME_SHOW_CONFIRMATION_BUTTONS)
  private Boolean showConfirmationButtons = true;

  /**
   * Положение виджета на странице
   */
  @JsonAdapter(ChatPositionEnum.Adapter.class)
  public enum ChatPositionEnum {
    BOTTOM_RIGHT("bottom_right"),
    
    BOTTOM_LEFT("bottom_left"),
    
    TOP_RIGHT("top_right"),
    
    TOP_LEFT("top_left");

    private String value;

    ChatPositionEnum(String value) {
      this.value = value;
    }

    public String getValue() {
      return value;
    }

    @Override
    public String toString() {
      return String.valueOf(value);
    }

    public static ChatPositionEnum fromValue(String value) {
      for (ChatPositionEnum b : ChatPositionEnum.values()) {
        if (b.value.equals(value)) {
          return b;
        }
      }
      throw new IllegalArgumentException("Unexpected value '" + value + "'");
    }

    public static class Adapter extends TypeAdapter<ChatPositionEnum> {
      @Override
      public void write(final JsonWriter jsonWriter, final ChatPositionEnum enumeration) throws IOException {
        jsonWriter.value(enumeration.getValue());
      }

      @Override
      public ChatPositionEnum read(final JsonReader jsonReader) throws IOException {
        String value =  jsonReader.nextString();
        return ChatPositionEnum.fromValue(value);
      }
    }
  }

  public static final String SERIALIZED_NAME_CHAT_POSITION = "chat_position";
  @SerializedName(SERIALIZED_NAME_CHAT_POSITION)
  private ChatPositionEnum chatPosition;

  public static final String SERIALIZED_NAME_CHAT_POSITION_ADJUSTMENT_X = "chat_position_adjustment_x";
  @SerializedName(SERIALIZED_NAME_CHAT_POSITION_ADJUSTMENT_X)
  private BigDecimal chatPositionAdjustmentX;

  public static final String SERIALIZED_NAME_CHAT_POSITION_ADJUSTMENT_Y = "chat_position_adjustment_y";
  @SerializedName(SERIALIZED_NAME_CHAT_POSITION_ADJUSTMENT_Y)
  private BigDecimal chatPositionAdjustmentY;

  public AgentWidgetSettings() {
  }

  public AgentWidgetSettings whitelistDomains(Set<String> whitelistDomains) {
    
    this.whitelistDomains = whitelistDomains;
    return this;
  }

  public AgentWidgetSettings addWhitelistDomainsItem(String whitelistDomainsItem) {
    if (this.whitelistDomains == null) {
      this.whitelistDomains = new LinkedHashSet<>();
    }
    this.whitelistDomains.add(whitelistDomainsItem);
    return this;
  }

   /**
   * Домены, на которых разрешено показывать виджет. Пустой массив — без ограничений.
   * @return whitelistDomains
  **/
  @javax.annotation.Nonnull
  public Set<String> getWhitelistDomains() {
    return whitelistDomains;
  }


  public void setWhitelistDomains(Set<String> whitelistDomains) {
    this.whitelistDomains = whitelistDomains;
  }


  public AgentWidgetSettings name(String name) {
    
    this.name = name;
    return this;
  }

   /**
   * Отображаемое имя агента в виджете
   * @return name
  **/
  @javax.annotation.Nonnull
  public String getName() {
    return name;
  }


  public void setName(String name) {
    this.name = name;
  }


  public AgentWidgetSettings signature(String signature) {
    
    this.signature = signature;
    return this;
  }

   /**
   * Подпись под именем агента в виджете
   * @return signature
  **/
  @javax.annotation.Nullable
  public String getSignature() {
    return signature;
  }


  public void setSignature(String signature) {
    this.signature = signature;
  }


  public AgentWidgetSettings welcomeMessage(String welcomeMessage) {
    
    this.welcomeMessage = welcomeMessage;
    return this;
  }

   /**
   * Приветственное сообщение при открытии виджета
   * @return welcomeMessage
  **/
  @javax.annotation.Nonnull
  public String getWelcomeMessage() {
    return welcomeMessage;
  }


  public void setWelcomeMessage(String welcomeMessage) {
    this.welcomeMessage = welcomeMessage;
  }


  public AgentWidgetSettings primaryColor(String primaryColor) {
    
    this.primaryColor = primaryColor;
    return this;
  }

   /**
   * Основной цвет виджета в формате &#x60;#RRGGBB&#x60;
   * @return primaryColor
  **/
  @javax.annotation.Nonnull
  public String getPrimaryColor() {
    return primaryColor;
  }


  public void setPrimaryColor(String primaryColor) {
    this.primaryColor = primaryColor;
  }


  public AgentWidgetSettings textColor(String textColor) {
    
    this.textColor = textColor;
    return this;
  }

   /**
   * Цвет текста в формате &#x60;#RRGGBB&#x60;
   * @return textColor
  **/
  @javax.annotation.Nullable
  public String getTextColor() {
    return textColor;
  }


  public void setTextColor(String textColor) {
    this.textColor = textColor;
  }


  public AgentWidgetSettings backgroundColor(String backgroundColor) {
    
    this.backgroundColor = backgroundColor;
    return this;
  }

   /**
   * Цвет фона в формате &#x60;#RRGGBB&#x60;
   * @return backgroundColor
  **/
  @javax.annotation.Nullable
  public String getBackgroundColor() {
    return backgroundColor;
  }


  public void setBackgroundColor(String backgroundColor) {
    this.backgroundColor = backgroundColor;
  }


  public AgentWidgetSettings headerFooterColor(String headerFooterColor) {
    
    this.headerFooterColor = headerFooterColor;
    return this;
  }

   /**
   * Цвет шапки и подвала виджета в формате &#x60;#RRGGBB&#x60;
   * @return headerFooterColor
  **/
  @javax.annotation.Nullable
  public String getHeaderFooterColor() {
    return headerFooterColor;
  }


  public void setHeaderFooterColor(String headerFooterColor) {
    this.headerFooterColor = headerFooterColor;
  }


  public AgentWidgetSettings font(String font) {
    
    this.font = font;
    return this;
  }

   /**
   * Шрифт виджета. Пустая строка — шрифт сайта.
   * @return font
  **/
  @javax.annotation.Nonnull
  public String getFont() {
    return font;
  }


  public void setFont(String font) {
    this.font = font;
  }


  public AgentWidgetSettings iconUrl(URI iconUrl) {
    
    this.iconUrl = iconUrl;
    return this;
  }

   /**
   * URL иконки агента в виджете
   * @return iconUrl
  **/
  @javax.annotation.Nullable
  public URI getIconUrl() {
    return iconUrl;
  }


  public void setIconUrl(URI iconUrl) {
    this.iconUrl = iconUrl;
  }


  public AgentWidgetSettings useAvatar(Boolean useAvatar) {
    
    this.useAvatar = useAvatar;
    return this;
  }

   /**
   * Использовать загруженный в панели аватар как иконку агента
   * @return useAvatar
  **/
  @javax.annotation.Nullable
  public Boolean getUseAvatar() {
    return useAvatar;
  }


  public void setUseAvatar(Boolean useAvatar) {
    this.useAvatar = useAvatar;
  }


  public AgentWidgetSettings showButton(Boolean showButton) {
    
    this.showButton = showButton;
    return this;
  }

   /**
   * Показывать кнопку открытия виджета
   * @return showButton
  **/
  @javax.annotation.Nullable
  public Boolean getShowButton() {
    return showButton;
  }


  public void setShowButton(Boolean showButton) {
    this.showButton = showButton;
  }


  public AgentWidgetSettings showToolProcessing(Boolean showToolProcessing) {
    
    this.showToolProcessing = showToolProcessing;
    return this;
  }

   /**
   * Показывать в виджете статус выполнения инструментов агента
   * @return showToolProcessing
  **/
  @javax.annotation.Nullable
  public Boolean getShowToolProcessing() {
    return showToolProcessing;
  }


  public void setShowToolProcessing(Boolean showToolProcessing) {
    this.showToolProcessing = showToolProcessing;
  }


  public AgentWidgetSettings showConfirmationButtons(Boolean showConfirmationButtons) {
    
    this.showConfirmationButtons = showConfirmationButtons;
    return this;
  }

   /**
   * Показывать кнопки подтверждения и отмены операций, которые агент выполняет через инструменты
   * @return showConfirmationButtons
  **/
  @javax.annotation.Nullable
  public Boolean getShowConfirmationButtons() {
    return showConfirmationButtons;
  }


  public void setShowConfirmationButtons(Boolean showConfirmationButtons) {
    this.showConfirmationButtons = showConfirmationButtons;
  }


  public AgentWidgetSettings chatPosition(ChatPositionEnum chatPosition) {
    
    this.chatPosition = chatPosition;
    return this;
  }

   /**
   * Положение виджета на странице
   * @return chatPosition
  **/
  @javax.annotation.Nonnull
  public ChatPositionEnum getChatPosition() {
    return chatPosition;
  }


  public void setChatPosition(ChatPositionEnum chatPosition) {
    this.chatPosition = chatPosition;
  }


  public AgentWidgetSettings chatPositionAdjustmentX(BigDecimal chatPositionAdjustmentX) {
    
    this.chatPositionAdjustmentX = chatPositionAdjustmentX;
    return this;
  }

   /**
   * Смещение виджета по горизонтали в пикселях
   * @return chatPositionAdjustmentX
  **/
  @javax.annotation.Nullable
  public BigDecimal getChatPositionAdjustmentX() {
    return chatPositionAdjustmentX;
  }


  public void setChatPositionAdjustmentX(BigDecimal chatPositionAdjustmentX) {
    this.chatPositionAdjustmentX = chatPositionAdjustmentX;
  }


  public AgentWidgetSettings chatPositionAdjustmentY(BigDecimal chatPositionAdjustmentY) {
    
    this.chatPositionAdjustmentY = chatPositionAdjustmentY;
    return this;
  }

   /**
   * Смещение виджета по вертикали в пикселях
   * @return chatPositionAdjustmentY
  **/
  @javax.annotation.Nullable
  public BigDecimal getChatPositionAdjustmentY() {
    return chatPositionAdjustmentY;
  }


  public void setChatPositionAdjustmentY(BigDecimal chatPositionAdjustmentY) {
    this.chatPositionAdjustmentY = chatPositionAdjustmentY;
  }



  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    AgentWidgetSettings agentWidgetSettings = (AgentWidgetSettings) o;
    return Objects.equals(this.whitelistDomains, agentWidgetSettings.whitelistDomains) &&
        Objects.equals(this.name, agentWidgetSettings.name) &&
        Objects.equals(this.signature, agentWidgetSettings.signature) &&
        Objects.equals(this.welcomeMessage, agentWidgetSettings.welcomeMessage) &&
        Objects.equals(this.primaryColor, agentWidgetSettings.primaryColor) &&
        Objects.equals(this.textColor, agentWidgetSettings.textColor) &&
        Objects.equals(this.backgroundColor, agentWidgetSettings.backgroundColor) &&
        Objects.equals(this.headerFooterColor, agentWidgetSettings.headerFooterColor) &&
        Objects.equals(this.font, agentWidgetSettings.font) &&
        Objects.equals(this.iconUrl, agentWidgetSettings.iconUrl) &&
        Objects.equals(this.useAvatar, agentWidgetSettings.useAvatar) &&
        Objects.equals(this.showButton, agentWidgetSettings.showButton) &&
        Objects.equals(this.showToolProcessing, agentWidgetSettings.showToolProcessing) &&
        Objects.equals(this.showConfirmationButtons, agentWidgetSettings.showConfirmationButtons) &&
        Objects.equals(this.chatPosition, agentWidgetSettings.chatPosition) &&
        Objects.equals(this.chatPositionAdjustmentX, agentWidgetSettings.chatPositionAdjustmentX) &&
        Objects.equals(this.chatPositionAdjustmentY, agentWidgetSettings.chatPositionAdjustmentY);
  }

  @Override
  public int hashCode() {
    return Objects.hash(whitelistDomains, name, signature, welcomeMessage, primaryColor, textColor, backgroundColor, headerFooterColor, font, iconUrl, useAvatar, showButton, showToolProcessing, showConfirmationButtons, chatPosition, chatPositionAdjustmentX, chatPositionAdjustmentY);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class AgentWidgetSettings {\n");
    sb.append("    whitelistDomains: ").append(toIndentedString(whitelistDomains)).append("\n");
    sb.append("    name: ").append(toIndentedString(name)).append("\n");
    sb.append("    signature: ").append(toIndentedString(signature)).append("\n");
    sb.append("    welcomeMessage: ").append(toIndentedString(welcomeMessage)).append("\n");
    sb.append("    primaryColor: ").append(toIndentedString(primaryColor)).append("\n");
    sb.append("    textColor: ").append(toIndentedString(textColor)).append("\n");
    sb.append("    backgroundColor: ").append(toIndentedString(backgroundColor)).append("\n");
    sb.append("    headerFooterColor: ").append(toIndentedString(headerFooterColor)).append("\n");
    sb.append("    font: ").append(toIndentedString(font)).append("\n");
    sb.append("    iconUrl: ").append(toIndentedString(iconUrl)).append("\n");
    sb.append("    useAvatar: ").append(toIndentedString(useAvatar)).append("\n");
    sb.append("    showButton: ").append(toIndentedString(showButton)).append("\n");
    sb.append("    showToolProcessing: ").append(toIndentedString(showToolProcessing)).append("\n");
    sb.append("    showConfirmationButtons: ").append(toIndentedString(showConfirmationButtons)).append("\n");
    sb.append("    chatPosition: ").append(toIndentedString(chatPosition)).append("\n");
    sb.append("    chatPositionAdjustmentX: ").append(toIndentedString(chatPositionAdjustmentX)).append("\n");
    sb.append("    chatPositionAdjustmentY: ").append(toIndentedString(chatPositionAdjustmentY)).append("\n");
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
    openapiFields.add("whitelist_domains");
    openapiFields.add("name");
    openapiFields.add("signature");
    openapiFields.add("welcome_message");
    openapiFields.add("primary_color");
    openapiFields.add("text_color");
    openapiFields.add("background_color");
    openapiFields.add("header_footer_color");
    openapiFields.add("font");
    openapiFields.add("icon_url");
    openapiFields.add("use_avatar");
    openapiFields.add("show_button");
    openapiFields.add("show_tool_processing");
    openapiFields.add("show_confirmation_buttons");
    openapiFields.add("chat_position");
    openapiFields.add("chat_position_adjustment_x");
    openapiFields.add("chat_position_adjustment_y");

    // a set of required properties/fields (JSON key names)
    openapiRequiredFields = new HashSet<String>();
    openapiRequiredFields.add("whitelist_domains");
    openapiRequiredFields.add("name");
    openapiRequiredFields.add("welcome_message");
    openapiRequiredFields.add("primary_color");
    openapiRequiredFields.add("font");
    openapiRequiredFields.add("chat_position");
  }

 /**
  * Validates the JSON Element and throws an exception if issues found
  *
  * @param jsonElement JSON Element
  * @throws IOException if the JSON Element is invalid with respect to AgentWidgetSettings
  */
  public static void validateJsonElement(JsonElement jsonElement) throws IOException {
      if (jsonElement == null) {
        if (!AgentWidgetSettings.openapiRequiredFields.isEmpty()) { // has required fields but JSON element is null
          throw new IllegalArgumentException(String.format("The required field(s) %s in AgentWidgetSettings is not found in the empty JSON string", AgentWidgetSettings.openapiRequiredFields.toString()));
        }
      }

      Set<Entry<String, JsonElement>> entries = jsonElement.getAsJsonObject().entrySet();
      // check to see if the JSON string contains additional fields
      for (Entry<String, JsonElement> entry : entries) {
        if (!AgentWidgetSettings.openapiFields.contains(entry.getKey())) {
          throw new IllegalArgumentException(String.format("The field `%s` in the JSON string is not defined in the `AgentWidgetSettings` properties. JSON: %s", entry.getKey(), jsonElement.toString()));
        }
      }

      // check to make sure all required properties/fields are present in the JSON string
      for (String requiredField : AgentWidgetSettings.openapiRequiredFields) {
        if (jsonElement.getAsJsonObject().get(requiredField) == null) {
          throw new IllegalArgumentException(String.format("The required field `%s` is not found in the JSON string: %s", requiredField, jsonElement.toString()));
        }
      }
        JsonObject jsonObj = jsonElement.getAsJsonObject();
      // ensure the required json array is present
      if (jsonObj.get("whitelist_domains") == null) {
        throw new IllegalArgumentException("Expected the field `linkedContent` to be an array in the JSON string but got `null`");
      } else if (!jsonObj.get("whitelist_domains").isJsonArray()) {
        throw new IllegalArgumentException(String.format("Expected the field `whitelist_domains` to be an array in the JSON string but got `%s`", jsonObj.get("whitelist_domains").toString()));
      }
      if (!jsonObj.get("name").isJsonPrimitive()) {
        throw new IllegalArgumentException(String.format("Expected the field `name` to be a primitive type in the JSON string but got `%s`", jsonObj.get("name").toString()));
      }
      if ((jsonObj.get("signature") != null && !jsonObj.get("signature").isJsonNull()) && !jsonObj.get("signature").isJsonPrimitive()) {
        throw new IllegalArgumentException(String.format("Expected the field `signature` to be a primitive type in the JSON string but got `%s`", jsonObj.get("signature").toString()));
      }
      if (!jsonObj.get("welcome_message").isJsonPrimitive()) {
        throw new IllegalArgumentException(String.format("Expected the field `welcome_message` to be a primitive type in the JSON string but got `%s`", jsonObj.get("welcome_message").toString()));
      }
      if (!jsonObj.get("primary_color").isJsonPrimitive()) {
        throw new IllegalArgumentException(String.format("Expected the field `primary_color` to be a primitive type in the JSON string but got `%s`", jsonObj.get("primary_color").toString()));
      }
      if ((jsonObj.get("text_color") != null && !jsonObj.get("text_color").isJsonNull()) && !jsonObj.get("text_color").isJsonPrimitive()) {
        throw new IllegalArgumentException(String.format("Expected the field `text_color` to be a primitive type in the JSON string but got `%s`", jsonObj.get("text_color").toString()));
      }
      if ((jsonObj.get("background_color") != null && !jsonObj.get("background_color").isJsonNull()) && !jsonObj.get("background_color").isJsonPrimitive()) {
        throw new IllegalArgumentException(String.format("Expected the field `background_color` to be a primitive type in the JSON string but got `%s`", jsonObj.get("background_color").toString()));
      }
      if ((jsonObj.get("header_footer_color") != null && !jsonObj.get("header_footer_color").isJsonNull()) && !jsonObj.get("header_footer_color").isJsonPrimitive()) {
        throw new IllegalArgumentException(String.format("Expected the field `header_footer_color` to be a primitive type in the JSON string but got `%s`", jsonObj.get("header_footer_color").toString()));
      }
      if (!jsonObj.get("font").isJsonPrimitive()) {
        throw new IllegalArgumentException(String.format("Expected the field `font` to be a primitive type in the JSON string but got `%s`", jsonObj.get("font").toString()));
      }
      if ((jsonObj.get("icon_url") != null && !jsonObj.get("icon_url").isJsonNull()) && !jsonObj.get("icon_url").isJsonPrimitive()) {
        throw new IllegalArgumentException(String.format("Expected the field `icon_url` to be a primitive type in the JSON string but got `%s`", jsonObj.get("icon_url").toString()));
      }
      if (!jsonObj.get("chat_position").isJsonPrimitive()) {
        throw new IllegalArgumentException(String.format("Expected the field `chat_position` to be a primitive type in the JSON string but got `%s`", jsonObj.get("chat_position").toString()));
      }
  }

  public static class CustomTypeAdapterFactory implements TypeAdapterFactory {
    @SuppressWarnings("unchecked")
    @Override
    public <T> TypeAdapter<T> create(Gson gson, TypeToken<T> type) {
       if (!AgentWidgetSettings.class.isAssignableFrom(type.getRawType())) {
         return null; // this class only serializes 'AgentWidgetSettings' and its subtypes
       }
       final TypeAdapter<JsonElement> elementAdapter = gson.getAdapter(JsonElement.class);
       final TypeAdapter<AgentWidgetSettings> thisAdapter
                        = gson.getDelegateAdapter(this, TypeToken.get(AgentWidgetSettings.class));

       return (TypeAdapter<T>) new TypeAdapter<AgentWidgetSettings>() {
           @Override
           public void write(JsonWriter out, AgentWidgetSettings value) throws IOException {
             JsonObject obj = thisAdapter.toJsonTree(value).getAsJsonObject();
             elementAdapter.write(out, obj);
           }

           @Override
           public AgentWidgetSettings read(JsonReader in) throws IOException {
             JsonElement jsonElement = elementAdapter.read(in);
             validateJsonElement(jsonElement);
             return thisAdapter.fromJsonTree(jsonElement);
           }

       }.nullSafe();
    }
  }

 /**
  * Create an instance of AgentWidgetSettings given an JSON string
  *
  * @param jsonString JSON string
  * @return An instance of AgentWidgetSettings
  * @throws IOException if the JSON string is invalid with respect to AgentWidgetSettings
  */
  public static AgentWidgetSettings fromJson(String jsonString) throws IOException {
    return JSON.getGson().fromJson(jsonString, AgentWidgetSettings.class);
  }

 /**
  * Convert an instance of AgentWidgetSettings to an JSON string
  *
  * @return JSON string
  */
  public String toJson() {
    return JSON.getGson().toJson(this);
  }
}

