/*
 * Timeweb Cloud API
 * # Введение API Timeweb Cloud позволяет вам управлять ресурсами в облаке программным способом с использованием обычных HTTP-запросов.  Множество функций, которые доступны в панели управления Timeweb Cloud, также доступны через API, что позволяет вам автоматизировать ваши собственные сценарии.  В этой документации сперва будет описан общий дизайн и принципы работы API, а после этого конкретные конечные точки. Также будут приведены примеры запросов к ним.   ## Запросы Запросы должны выполняться по протоколу `HTTPS`, чтобы гарантировать шифрование транзакций. Поддерживаются следующие методы запроса: |Метод|Применение| |--- |--- | |GET|Извлекает данные о коллекциях и отдельных ресурсах.| |POST|Для коллекций создает новый ресурс этого типа. Также используется для выполнения действий с конкретным ресурсом.| |PUT|Обновляет существующий ресурс.| |PATCH|Некоторые ресурсы поддерживают частичное обновление, то есть обновление только части атрибутов ресурса, в этом случае вместо метода PUT будет использован PATCH.| |DELETE|Удаляет ресурс.|  Методы `POST`, `PUT` и `PATCH` могут включать объект в тело запроса с типом содержимого `application/json`.  ### Параметры в запросах Некоторые коллекции поддерживают пагинацию, поиск или сортировку в запросах. В параметрах запроса требуется передать: - `limit` — обозначает количество записей, которое необходимо вернуть  - `offset` — указывает на смещение, относительно начала списка  - `search` — позволяет указать набор символов для поиска  - `sort` — можно задать правило сортировки коллекции  ## Ответы Запросы вернут один из следующих кодов состояния ответа HTTP:  |Статус|Описание| |--- |--- | |200 OK|Действие с ресурсом было выполнено успешно.| |201 Created|Ресурс был успешно создан. При этом ресурс может быть как уже готовым к использованию, так и находиться в процессе запуска.| |204 No Content|Действие с ресурсом было выполнено успешно, и ответ не содержит дополнительной информации в теле.| |400 Bad Request|Был отправлен неверный запрос, например, в нем отсутствуют обязательные параметры и т. д. Тело ответа будет содержать дополнительную информацию об ошибке.| |401 Unauthorized|Ошибка аутентификации.| |403 Forbidden|Аутентификация прошла успешно, но недостаточно прав для выполнения действия.| |404 Not Found|Запрашиваемый ресурс не найден.| |409 Conflict|Запрос конфликтует с текущим состоянием.| |423 Locked|Ресурс из запроса заблокирован от применения к нему указанного метода.| |429 Too Many Requests|Был достигнут лимит по количеству запросов в единицу времени.| |500 Internal Server Error|При выполнении запроса произошла какая-то внутренняя ошибка. Чтобы решить эту проблему, лучше всего создать тикет в панели управления.|  ### Структура успешного ответа Все конечные точки будут возвращать данные в формате `JSON`. Ответы на `GET`-запросы будут иметь на верхнем уровне следующую структуру атрибутов:  |Название поля|Тип|Описание| |--- |--- |--- | |[entity_name]|object, object[], string[], number[], boolean|Динамическое поле, которое будет меняться в зависимости от запрашиваемого ресурса и будет содержать все атрибуты, необходимые для описания этого ресурса. Например, при запросе списка баз данных будет возвращаться поле `dbs`, а при запросе конкретного облачного сервера `server`. Для некоторых конечных точек в ответе может возвращаться сразу несколько ресурсов.| |meta|object|Опционально. Объект, который содержит вспомогательную информацию о ресурсе. Чаще всего будет встречаться при запросе коллекций и содержать поле `total`, которое будет указывать на количество элементов в коллекции.| |response_id|string|Опционально. В большинстве случаев в ответе будет содержаться уникальный идентификатор ответа в формате UUIDv4, который однозначно указывает на ваш запрос внутри нашей системы. Если вам потребуется задать вопрос нашей поддержке, приложите к вопросу этот идентификатор — так мы сможем найти ответ на него намного быстрее. Также вы можете использовать этот идентификатор, чтобы убедиться, что это новый ответ на запрос и результат не был получен из кэша.|  Пример запроса на получение списка SSH-ключей: ```     HTTP/2.0 200 OK     {       \"ssh_keys\":[           {             \"body\":\"ssh-rsa AAAAB3NzaC1sdfghjkOAsBwWhs= example@device.local\",             \"created_at\":\"2021-09-15T19:52:27Z\",             \"expired_at\":null,             \"id\":5297,             \"is_default\":false,             \"name\":\"example@device.local\",             \"used_at\":null,             \"used_by\":[]           }       ],       \"meta\":{           \"total\":1       },       \"response_id\":\"94608d15-8672-4eed-8ab6-28bd6fa3cdf7\"     } ```  ### Структура ответа с ошибкой |Название поля|Тип|Описание| |--- |--- |--- | |status_code|number|Короткий числовой идентификатор ошибки.| |error_code|string|Короткий текстовый идентификатор ошибки, который уточняет числовой идентификатор и удобен для программной обработки. Самый простой пример — это код `not_found` для ошибки 404.| |message|string, string[]|Опционально. В большинстве случаев в ответе будет содержаться человекочитаемое подробное описание ошибки или ошибок, которые помогут понять, что нужно исправить.| |response_id|string|Опционально. В большинстве случае в ответе будет содержаться уникальный идентификатор ответа в формате UUIDv4, который однозначно указывает на ваш запрос внутри нашей системы. Если вам потребуется задать вопрос нашей поддержке, приложите к вопросу этот идентификатор — так мы сможем найти ответ на него намного быстрее.|  Пример: ```     HTTP/2.0 403 Forbidden     {       \"status_code\": 403,       \"error_code\":  \"forbidden\",       \"message\":     \"You do not have access for the attempted action\",       \"response_id\": \"94608d15-8672-4eed-8ab6-28bd6fa3cdf7\"     } ```  ## Статусы ресурсов Важно учесть, что при создании большинства ресурсов внутри платформы вам будет сразу возвращен ответ от сервера со статусом `200 OK` или `201 Created` и идентификатором созданного ресурса в теле ответа, но при этом этот ресурс может быть ещё в *состоянии запуска*.  Для того чтобы понять, в каком состоянии сейчас находится ваш ресурс, мы добавили поле `status` в ответ на получение информации о ресурсе.  Список статусов будет отличаться в зависимости от типа ресурса. Увидеть поддерживаемый список статусов вы сможете в описании каждого конкретного ресурса.     ## Ограничение скорости запросов (Rate Limiting) Чтобы обеспечить стабильность для всех пользователей, Timeweb Cloud защищает API от всплесков входящего трафика, анализируя количество запросов c каждого аккаунта к каждой конечной точке.  Если ваше приложение отправляет более 20 запросов в секунду на одну конечную точку, то для этого запроса API может вернуть код состояния HTTP `429 Too Many Requests`.   ## Аутентификация Доступ к API осуществляется с помощью JWT-токена. Токенами можно управлять внутри панели управления Timeweb Cloud в разделе *API и Terraform*.  Токен необходимо передавать в заголовке каждого запроса в формате: ```   Authorization: Bearer $TIMEWEB_CLOUD_TOKEN ```  ## Формат примеров API Примеры в этой документации описаны с помощью `curl`, HTTP-клиента командной строки. На компьютерах `Linux` и `macOS` обычно по умолчанию установлен `curl`, и он доступен для загрузки на всех популярных платформах, включая `Windows`.  Каждый пример разделен на несколько строк символом `\\`, который совместим с `bash`. Типичный пример выглядит так: ```   curl -X PATCH      -H \"Content-Type: application/json\"      -H \"Authorization: Bearer $TIMEWEB_CLOUD_TOKEN\"      -d '{\"name\":\"Cute Corvus\",\"comment\":\"Development Server\"}'      \"https://api.timeweb.cloud/api/v1/dedicated/1051\" ``` - Параметр `-X` задает метод запроса. Для согласованности метод будет указан во всех примерах, даже если он явно не требуется для методов `GET`. - Строки `-H` задают требуемые HTTP-заголовки. - Примеры, для которых требуется объект JSON в теле запроса, передают требуемые данные через параметр `-d`.  Чтобы использовать приведенные примеры, не подставляя каждый раз в них свой токен, вы можете добавить токен один раз в переменные окружения в вашей консоли. Например, на `Linux` это можно сделать с помощью команды:  ``` TIMEWEB_CLOUD_TOKEN=\"token\" ```  После этого токен будет автоматически подставляться в ваши запросы.  Обратите внимание, что все значения в этой документации являются примерами. Не полагайтесь на идентификаторы операционных систем, тарифов и т.д., используемые в примерах. Используйте соответствующую конечную точку для получения значений перед созданием ресурсов.   ## Версионирование API построено согласно принципам [семантического версионирования](https://semver.org/lang/ru). Это значит, что мы гарантируем обратную совместимость всех изменений в пределах одной мажорной версии.  Мажорная версия каждой конечной точки обозначается в пути запроса, например, запрос `/api/v1/servers` указывает, что этот метод имеет версию 1.
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
import java.util.UUID;
import org.openapitools.client.model.AvailabilityZone;
import org.openapitools.client.model.CreateServerConfiguration;
import org.openapitools.client.model.Network;

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
 * CreateServer
 */
@javax.annotation.Generated(value = "org.openapitools.codegen.languages.JavaClientCodegen", date = "2024-02-21T10:17:19.178316Z[Etc/UTC]")
public class CreateServer {
  public static final String SERIALIZED_NAME_CONFIGURATION = "configuration";
  @SerializedName(SERIALIZED_NAME_CONFIGURATION)
  private CreateServerConfiguration _configuration;

  public static final String SERIALIZED_NAME_IS_DDOS_GUARD = "is_ddos_guard";
  @SerializedName(SERIALIZED_NAME_IS_DDOS_GUARD)
  private Boolean isDdosGuard;

  public static final String SERIALIZED_NAME_OS_ID = "os_id";
  @SerializedName(SERIALIZED_NAME_OS_ID)
  private BigDecimal osId;

  public static final String SERIALIZED_NAME_IMAGE_ID = "image_id";
  @SerializedName(SERIALIZED_NAME_IMAGE_ID)
  private UUID imageId;

  public static final String SERIALIZED_NAME_SOFTWARE_ID = "software_id";
  @SerializedName(SERIALIZED_NAME_SOFTWARE_ID)
  private BigDecimal softwareId;

  public static final String SERIALIZED_NAME_PRESET_ID = "preset_id";
  @SerializedName(SERIALIZED_NAME_PRESET_ID)
  private BigDecimal presetId;

  public static final String SERIALIZED_NAME_BANDWIDTH = "bandwidth";
  @SerializedName(SERIALIZED_NAME_BANDWIDTH)
  private BigDecimal bandwidth;

  public static final String SERIALIZED_NAME_NAME = "name";
  @SerializedName(SERIALIZED_NAME_NAME)
  private String name;

  public static final String SERIALIZED_NAME_AVATAR_ID = "avatar_id";
  @SerializedName(SERIALIZED_NAME_AVATAR_ID)
  private String avatarId;

  public static final String SERIALIZED_NAME_COMMENT = "comment";
  @SerializedName(SERIALIZED_NAME_COMMENT)
  private String comment;

  public static final String SERIALIZED_NAME_SSH_KEYS_IDS = "ssh_keys_ids";
  @SerializedName(SERIALIZED_NAME_SSH_KEYS_IDS)
  private List<BigDecimal> sshKeysIds;

  public static final String SERIALIZED_NAME_IS_LOCAL_NETWORK = "is_local_network";
  @Deprecated
  @SerializedName(SERIALIZED_NAME_IS_LOCAL_NETWORK)
  private Boolean isLocalNetwork;

  public static final String SERIALIZED_NAME_NETWORK = "network";
  @SerializedName(SERIALIZED_NAME_NETWORK)
  private Network network;

  public static final String SERIALIZED_NAME_CLOUD_INIT = "cloud_init";
  @SerializedName(SERIALIZED_NAME_CLOUD_INIT)
  private String cloudInit;

  public static final String SERIALIZED_NAME_AVAILABILITY_ZONE = "availability_zone";
  @SerializedName(SERIALIZED_NAME_AVAILABILITY_ZONE)
  private AvailabilityZone availabilityZone;

  public CreateServer() {
  }

  public CreateServer _configuration(CreateServerConfiguration _configuration) {
    
    this._configuration = _configuration;
    return this;
  }

   /**
   * Get _configuration
   * @return _configuration
  **/
  @javax.annotation.Nullable
  public CreateServerConfiguration getConfiguration() {
    return _configuration;
  }


  public void setConfiguration(CreateServerConfiguration _configuration) {
    this._configuration = _configuration;
  }


  public CreateServer isDdosGuard(Boolean isDdosGuard) {
    
    this.isDdosGuard = isDdosGuard;
    return this;
  }

   /**
   * Защита от DDoS. Серверу выдается защищенный IP-адрес с защитой уровня L3 / L4. Для включения защиты уровня L7 необходимо создать тикет в техническую поддержку.
   * @return isDdosGuard
  **/
  @javax.annotation.Nonnull
  public Boolean getIsDdosGuard() {
    return isDdosGuard;
  }


  public void setIsDdosGuard(Boolean isDdosGuard) {
    this.isDdosGuard = isDdosGuard;
  }


  public CreateServer osId(BigDecimal osId) {
    
    this.osId = osId;
    return this;
  }

   /**
   * Уникальный идентификатор операционной системы, которая будет установлена на облачный сервер. Нельзя передавать вместе с &#x60;image_id&#x60;.
   * @return osId
  **/
  @javax.annotation.Nullable
  public BigDecimal getOsId() {
    return osId;
  }


  public void setOsId(BigDecimal osId) {
    this.osId = osId;
  }


  public CreateServer imageId(UUID imageId) {
    
    this.imageId = imageId;
    return this;
  }

   /**
   * Уникальный идентификатор образа, который будет установлен на облачный сервер. Нельзя передавать вместе с &#x60;os_id&#x60;.
   * @return imageId
  **/
  @javax.annotation.Nullable
  public UUID getImageId() {
    return imageId;
  }


  public void setImageId(UUID imageId) {
    this.imageId = imageId;
  }


  public CreateServer softwareId(BigDecimal softwareId) {
    
    this.softwareId = softwareId;
    return this;
  }

   /**
   * Уникальный идентификатор программного обеспечения сервера.
   * @return softwareId
  **/
  @javax.annotation.Nullable
  public BigDecimal getSoftwareId() {
    return softwareId;
  }


  public void setSoftwareId(BigDecimal softwareId) {
    this.softwareId = softwareId;
  }


  public CreateServer presetId(BigDecimal presetId) {
    
    this.presetId = presetId;
    return this;
  }

   /**
   * Уникальный идентификатор тарифа сервера. Нельзя передавать вместе с ключом &#x60;configurator&#x60;.
   * @return presetId
  **/
  @javax.annotation.Nullable
  public BigDecimal getPresetId() {
    return presetId;
  }


  public void setPresetId(BigDecimal presetId) {
    this.presetId = presetId;
  }


  public CreateServer bandwidth(BigDecimal bandwidth) {
    
    this.bandwidth = bandwidth;
    return this;
  }

   /**
   * Пропускная способность тарифа. Доступные значения от 100 до 1000 с шагом 100.
   * @return bandwidth
  **/
  @javax.annotation.Nonnull
  public BigDecimal getBandwidth() {
    return bandwidth;
  }


  public void setBandwidth(BigDecimal bandwidth) {
    this.bandwidth = bandwidth;
  }


  public CreateServer name(String name) {
    
    this.name = name;
    return this;
  }

   /**
   * Имя облачного сервера. Максимальная длина — 255 символов, имя должно быть уникальным.
   * @return name
  **/
  @javax.annotation.Nonnull
  public String getName() {
    return name;
  }


  public void setName(String name) {
    this.name = name;
  }


  public CreateServer avatarId(String avatarId) {
    
    this.avatarId = avatarId;
    return this;
  }

   /**
   * Уникальный идентификатор аватара сервера. Описание методов работы с аватарами появится позднее.
   * @return avatarId
  **/
  @javax.annotation.Nullable
  public String getAvatarId() {
    return avatarId;
  }


  public void setAvatarId(String avatarId) {
    this.avatarId = avatarId;
  }


  public CreateServer comment(String comment) {
    
    this.comment = comment;
    return this;
  }

   /**
   * Комментарий к облачному серверу. Максимальная длина — 255 символов.
   * @return comment
  **/
  @javax.annotation.Nullable
  public String getComment() {
    return comment;
  }


  public void setComment(String comment) {
    this.comment = comment;
  }


  public CreateServer sshKeysIds(List<BigDecimal> sshKeysIds) {
    
    this.sshKeysIds = sshKeysIds;
    return this;
  }

  public CreateServer addSshKeysIdsItem(BigDecimal sshKeysIdsItem) {
    if (this.sshKeysIds == null) {
      this.sshKeysIds = new ArrayList<>();
    }
    this.sshKeysIds.add(sshKeysIdsItem);
    return this;
  }

   /**
   * Список SSH-ключей.
   * @return sshKeysIds
  **/
  @javax.annotation.Nullable
  public List<BigDecimal> getSshKeysIds() {
    return sshKeysIds;
  }


  public void setSshKeysIds(List<BigDecimal> sshKeysIds) {
    this.sshKeysIds = sshKeysIds;
  }


  @Deprecated
  public CreateServer isLocalNetwork(Boolean isLocalNetwork) {
    
    this.isLocalNetwork = isLocalNetwork;
    return this;
  }

   /**
   * Локальная сеть.
   * @return isLocalNetwork
   * @deprecated
  **/
  @Deprecated
  @javax.annotation.Nullable
  public Boolean getIsLocalNetwork() {
    return isLocalNetwork;
  }


  @Deprecated
  public void setIsLocalNetwork(Boolean isLocalNetwork) {
    this.isLocalNetwork = isLocalNetwork;
  }


  public CreateServer network(Network network) {
    
    this.network = network;
    return this;
  }

   /**
   * Get network
   * @return network
  **/
  @javax.annotation.Nullable
  public Network getNetwork() {
    return network;
  }


  public void setNetwork(Network network) {
    this.network = network;
  }


  public CreateServer cloudInit(String cloudInit) {
    
    this.cloudInit = cloudInit;
    return this;
  }

   /**
   * Cloud-init скрипт
   * @return cloudInit
  **/
  @javax.annotation.Nullable
  public String getCloudInit() {
    return cloudInit;
  }


  public void setCloudInit(String cloudInit) {
    this.cloudInit = cloudInit;
  }


  public CreateServer availabilityZone(AvailabilityZone availabilityZone) {
    
    this.availabilityZone = availabilityZone;
    return this;
  }

   /**
   * Get availabilityZone
   * @return availabilityZone
  **/
  @javax.annotation.Nullable
  public AvailabilityZone getAvailabilityZone() {
    return availabilityZone;
  }


  public void setAvailabilityZone(AvailabilityZone availabilityZone) {
    this.availabilityZone = availabilityZone;
  }



  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    CreateServer createServer = (CreateServer) o;
    return Objects.equals(this._configuration, createServer._configuration) &&
        Objects.equals(this.isDdosGuard, createServer.isDdosGuard) &&
        Objects.equals(this.osId, createServer.osId) &&
        Objects.equals(this.imageId, createServer.imageId) &&
        Objects.equals(this.softwareId, createServer.softwareId) &&
        Objects.equals(this.presetId, createServer.presetId) &&
        Objects.equals(this.bandwidth, createServer.bandwidth) &&
        Objects.equals(this.name, createServer.name) &&
        Objects.equals(this.avatarId, createServer.avatarId) &&
        Objects.equals(this.comment, createServer.comment) &&
        Objects.equals(this.sshKeysIds, createServer.sshKeysIds) &&
        Objects.equals(this.isLocalNetwork, createServer.isLocalNetwork) &&
        Objects.equals(this.network, createServer.network) &&
        Objects.equals(this.cloudInit, createServer.cloudInit) &&
        Objects.equals(this.availabilityZone, createServer.availabilityZone);
  }

  @Override
  public int hashCode() {
    return Objects.hash(_configuration, isDdosGuard, osId, imageId, softwareId, presetId, bandwidth, name, avatarId, comment, sshKeysIds, isLocalNetwork, network, cloudInit, availabilityZone);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class CreateServer {\n");
    sb.append("    _configuration: ").append(toIndentedString(_configuration)).append("\n");
    sb.append("    isDdosGuard: ").append(toIndentedString(isDdosGuard)).append("\n");
    sb.append("    osId: ").append(toIndentedString(osId)).append("\n");
    sb.append("    imageId: ").append(toIndentedString(imageId)).append("\n");
    sb.append("    softwareId: ").append(toIndentedString(softwareId)).append("\n");
    sb.append("    presetId: ").append(toIndentedString(presetId)).append("\n");
    sb.append("    bandwidth: ").append(toIndentedString(bandwidth)).append("\n");
    sb.append("    name: ").append(toIndentedString(name)).append("\n");
    sb.append("    avatarId: ").append(toIndentedString(avatarId)).append("\n");
    sb.append("    comment: ").append(toIndentedString(comment)).append("\n");
    sb.append("    sshKeysIds: ").append(toIndentedString(sshKeysIds)).append("\n");
    sb.append("    isLocalNetwork: ").append(toIndentedString(isLocalNetwork)).append("\n");
    sb.append("    network: ").append(toIndentedString(network)).append("\n");
    sb.append("    cloudInit: ").append(toIndentedString(cloudInit)).append("\n");
    sb.append("    availabilityZone: ").append(toIndentedString(availabilityZone)).append("\n");
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
    openapiFields.add("configuration");
    openapiFields.add("is_ddos_guard");
    openapiFields.add("os_id");
    openapiFields.add("image_id");
    openapiFields.add("software_id");
    openapiFields.add("preset_id");
    openapiFields.add("bandwidth");
    openapiFields.add("name");
    openapiFields.add("avatar_id");
    openapiFields.add("comment");
    openapiFields.add("ssh_keys_ids");
    openapiFields.add("is_local_network");
    openapiFields.add("network");
    openapiFields.add("cloud_init");
    openapiFields.add("availability_zone");

    // a set of required properties/fields (JSON key names)
    openapiRequiredFields = new HashSet<String>();
    openapiRequiredFields.add("is_ddos_guard");
    openapiRequiredFields.add("bandwidth");
    openapiRequiredFields.add("name");
  }

 /**
  * Validates the JSON Element and throws an exception if issues found
  *
  * @param jsonElement JSON Element
  * @throws IOException if the JSON Element is invalid with respect to CreateServer
  */
  public static void validateJsonElement(JsonElement jsonElement) throws IOException {
      if (jsonElement == null) {
        if (!CreateServer.openapiRequiredFields.isEmpty()) { // has required fields but JSON element is null
          throw new IllegalArgumentException(String.format("The required field(s) %s in CreateServer is not found in the empty JSON string", CreateServer.openapiRequiredFields.toString()));
        }
      }

      Set<Entry<String, JsonElement>> entries = jsonElement.getAsJsonObject().entrySet();
      // check to see if the JSON string contains additional fields
      for (Entry<String, JsonElement> entry : entries) {
        if (!CreateServer.openapiFields.contains(entry.getKey())) {
          throw new IllegalArgumentException(String.format("The field `%s` in the JSON string is not defined in the `CreateServer` properties. JSON: %s", entry.getKey(), jsonElement.toString()));
        }
      }

      // check to make sure all required properties/fields are present in the JSON string
      for (String requiredField : CreateServer.openapiRequiredFields) {
        if (jsonElement.getAsJsonObject().get(requiredField) == null) {
          throw new IllegalArgumentException(String.format("The required field `%s` is not found in the JSON string: %s", requiredField, jsonElement.toString()));
        }
      }
        JsonObject jsonObj = jsonElement.getAsJsonObject();
      // validate the optional field `configuration`
      if (jsonObj.get("configuration") != null && !jsonObj.get("configuration").isJsonNull()) {
        CreateServerConfiguration.validateJsonElement(jsonObj.get("configuration"));
      }
      if ((jsonObj.get("image_id") != null && !jsonObj.get("image_id").isJsonNull()) && !jsonObj.get("image_id").isJsonPrimitive()) {
        throw new IllegalArgumentException(String.format("Expected the field `image_id` to be a primitive type in the JSON string but got `%s`", jsonObj.get("image_id").toString()));
      }
      if (!jsonObj.get("name").isJsonPrimitive()) {
        throw new IllegalArgumentException(String.format("Expected the field `name` to be a primitive type in the JSON string but got `%s`", jsonObj.get("name").toString()));
      }
      if ((jsonObj.get("avatar_id") != null && !jsonObj.get("avatar_id").isJsonNull()) && !jsonObj.get("avatar_id").isJsonPrimitive()) {
        throw new IllegalArgumentException(String.format("Expected the field `avatar_id` to be a primitive type in the JSON string but got `%s`", jsonObj.get("avatar_id").toString()));
      }
      if ((jsonObj.get("comment") != null && !jsonObj.get("comment").isJsonNull()) && !jsonObj.get("comment").isJsonPrimitive()) {
        throw new IllegalArgumentException(String.format("Expected the field `comment` to be a primitive type in the JSON string but got `%s`", jsonObj.get("comment").toString()));
      }
      // ensure the optional json data is an array if present
      if (jsonObj.get("ssh_keys_ids") != null && !jsonObj.get("ssh_keys_ids").isJsonArray()) {
        throw new IllegalArgumentException(String.format("Expected the field `ssh_keys_ids` to be an array in the JSON string but got `%s`", jsonObj.get("ssh_keys_ids").toString()));
      }
      // validate the optional field `network`
      if (jsonObj.get("network") != null && !jsonObj.get("network").isJsonNull()) {
        Network.validateJsonElement(jsonObj.get("network"));
      }
      if ((jsonObj.get("cloud_init") != null && !jsonObj.get("cloud_init").isJsonNull()) && !jsonObj.get("cloud_init").isJsonPrimitive()) {
        throw new IllegalArgumentException(String.format("Expected the field `cloud_init` to be a primitive type in the JSON string but got `%s`", jsonObj.get("cloud_init").toString()));
      }
  }

  public static class CustomTypeAdapterFactory implements TypeAdapterFactory {
    @SuppressWarnings("unchecked")
    @Override
    public <T> TypeAdapter<T> create(Gson gson, TypeToken<T> type) {
       if (!CreateServer.class.isAssignableFrom(type.getRawType())) {
         return null; // this class only serializes 'CreateServer' and its subtypes
       }
       final TypeAdapter<JsonElement> elementAdapter = gson.getAdapter(JsonElement.class);
       final TypeAdapter<CreateServer> thisAdapter
                        = gson.getDelegateAdapter(this, TypeToken.get(CreateServer.class));

       return (TypeAdapter<T>) new TypeAdapter<CreateServer>() {
           @Override
           public void write(JsonWriter out, CreateServer value) throws IOException {
             JsonObject obj = thisAdapter.toJsonTree(value).getAsJsonObject();
             elementAdapter.write(out, obj);
           }

           @Override
           public CreateServer read(JsonReader in) throws IOException {
             JsonElement jsonElement = elementAdapter.read(in);
             validateJsonElement(jsonElement);
             return thisAdapter.fromJsonTree(jsonElement);
           }

       }.nullSafe();
    }
  }

 /**
  * Create an instance of CreateServer given an JSON string
  *
  * @param jsonString JSON string
  * @return An instance of CreateServer
  * @throws IOException if the JSON string is invalid with respect to CreateServer
  */
  public static CreateServer fromJson(String jsonString) throws IOException {
    return JSON.getGson().fromJson(jsonString, CreateServer.class);
  }

 /**
  * Convert an instance of CreateServer to an JSON string
  *
  * @return JSON string
  */
  public String toJson() {
    return JSON.getGson().toJson(this);
  }
}

