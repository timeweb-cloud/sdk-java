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
import org.openapitools.client.model.Frameworks;

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
 * UpdeteSettings
 */
@javax.annotation.Generated(value = "org.openapitools.codegen.languages.JavaClientCodegen", date = "2025-04-07T14:19:23.327795Z[Etc/UTC]")
public class UpdeteSettings {
  public static final String SERIALIZED_NAME_IS_AUTO_DEPLOY = "is_auto_deploy";
  @SerializedName(SERIALIZED_NAME_IS_AUTO_DEPLOY)
  private Boolean isAutoDeploy;

  public static final String SERIALIZED_NAME_BUILD_CMD = "build_cmd";
  @SerializedName(SERIALIZED_NAME_BUILD_CMD)
  private String buildCmd;

  public static final String SERIALIZED_NAME_ENVS = "envs";
  @SerializedName(SERIALIZED_NAME_ENVS)
  private Object envs;

  public static final String SERIALIZED_NAME_BRANCH_NAME = "branch_name";
  @SerializedName(SERIALIZED_NAME_BRANCH_NAME)
  private String branchName;

  public static final String SERIALIZED_NAME_COMMIT_SHA = "commit_sha";
  @SerializedName(SERIALIZED_NAME_COMMIT_SHA)
  private String commitSha;

  public static final String SERIALIZED_NAME_ENV_VERSION = "env_version";
  @SerializedName(SERIALIZED_NAME_ENV_VERSION)
  private String envVersion;

  public static final String SERIALIZED_NAME_INDEX_DIR = "index_dir";
  @SerializedName(SERIALIZED_NAME_INDEX_DIR)
  private String indexDir;

  public static final String SERIALIZED_NAME_RUN_CMD = "run_cmd";
  @SerializedName(SERIALIZED_NAME_RUN_CMD)
  private String runCmd;

  public static final String SERIALIZED_NAME_FRAMEWORK = "framework";
  @SerializedName(SERIALIZED_NAME_FRAMEWORK)
  private Frameworks framework;

  public static final String SERIALIZED_NAME_NAME = "name";
  @SerializedName(SERIALIZED_NAME_NAME)
  private String name;

  public static final String SERIALIZED_NAME_COMMENT = "comment";
  @SerializedName(SERIALIZED_NAME_COMMENT)
  private String comment;

  public static final String SERIALIZED_NAME_PRESET_ID = "preset_id";
  @SerializedName(SERIALIZED_NAME_PRESET_ID)
  private BigDecimal presetId;

  public UpdeteSettings() {
  }

  public UpdeteSettings isAutoDeploy(Boolean isAutoDeploy) {
    
    this.isAutoDeploy = isAutoDeploy;
    return this;
  }

   /**
   * Автоматический деплой.
   * @return isAutoDeploy
  **/
  @javax.annotation.Nullable
  public Boolean getIsAutoDeploy() {
    return isAutoDeploy;
  }


  public void setIsAutoDeploy(Boolean isAutoDeploy) {
    this.isAutoDeploy = isAutoDeploy;
  }


  public UpdeteSettings buildCmd(String buildCmd) {
    
    this.buildCmd = buildCmd;
    return this;
  }

   /**
   * Команда сборки приложения.
   * @return buildCmd
  **/
  @javax.annotation.Nullable
  public String getBuildCmd() {
    return buildCmd;
  }


  public void setBuildCmd(String buildCmd) {
    this.buildCmd = buildCmd;
  }


  public UpdeteSettings envs(Object envs) {
    
    this.envs = envs;
    return this;
  }

   /**
   * Переменные окружения приложения. Объект с ключами и значениями типа string.
   * @return envs
  **/
  @javax.annotation.Nullable
  public Object getEnvs() {
    return envs;
  }


  public void setEnvs(Object envs) {
    this.envs = envs;
  }


  public UpdeteSettings branchName(String branchName) {
    
    this.branchName = branchName;
    return this;
  }

   /**
   * Название ветки репозитория из которой необходимо собрать приложение.
   * @return branchName
  **/
  @javax.annotation.Nullable
  public String getBranchName() {
    return branchName;
  }


  public void setBranchName(String branchName) {
    this.branchName = branchName;
  }


  public UpdeteSettings commitSha(String commitSha) {
    
    this.commitSha = commitSha;
    return this;
  }

   /**
   * Хэш коммита.
   * @return commitSha
  **/
  @javax.annotation.Nullable
  public String getCommitSha() {
    return commitSha;
  }


  public void setCommitSha(String commitSha) {
    this.commitSha = commitSha;
  }


  public UpdeteSettings envVersion(String envVersion) {
    
    this.envVersion = envVersion;
    return this;
  }

   /**
   * Версия окружения.
   * @return envVersion
  **/
  @javax.annotation.Nullable
  public String getEnvVersion() {
    return envVersion;
  }


  public void setEnvVersion(String envVersion) {
    this.envVersion = envVersion;
  }


  public UpdeteSettings indexDir(String indexDir) {
    
    this.indexDir = indexDir;
    return this;
  }

   /**
   * Путь к директории с индексным файлом. Используется для приложений &#x60;type: frontend&#x60;. Не используется для приложений &#x60;type: backend&#x60;. Значение всегда должно начинаться с &#x60;/&#x60;.
   * @return indexDir
  **/
  @javax.annotation.Nullable
  public String getIndexDir() {
    return indexDir;
  }


  public void setIndexDir(String indexDir) {
    this.indexDir = indexDir;
  }


  public UpdeteSettings runCmd(String runCmd) {
    
    this.runCmd = runCmd;
    return this;
  }

   /**
   * Команда для запуска приложения. Используется для приложений &#x60;type: backend&#x60;. Не используется для приложений &#x60;type: frontend&#x60;.
   * @return runCmd
  **/
  @javax.annotation.Nullable
  public String getRunCmd() {
    return runCmd;
  }


  public void setRunCmd(String runCmd) {
    this.runCmd = runCmd;
  }


  public UpdeteSettings framework(Frameworks framework) {
    
    this.framework = framework;
    return this;
  }

   /**
   * Get framework
   * @return framework
  **/
  @javax.annotation.Nullable
  public Frameworks getFramework() {
    return framework;
  }


  public void setFramework(Frameworks framework) {
    this.framework = framework;
  }


  public UpdeteSettings name(String name) {
    
    this.name = name;
    return this;
  }

   /**
   * Имя приложения.
   * @return name
  **/
  @javax.annotation.Nullable
  public String getName() {
    return name;
  }


  public void setName(String name) {
    this.name = name;
  }


  public UpdeteSettings comment(String comment) {
    
    this.comment = comment;
    return this;
  }

   /**
   * Комментарий к приложению.
   * @return comment
  **/
  @javax.annotation.Nullable
  public String getComment() {
    return comment;
  }


  public void setComment(String comment) {
    this.comment = comment;
  }


  public UpdeteSettings presetId(BigDecimal presetId) {
    
    this.presetId = presetId;
    return this;
  }

   /**
   * ID тарифа.
   * @return presetId
  **/
  @javax.annotation.Nullable
  public BigDecimal getPresetId() {
    return presetId;
  }


  public void setPresetId(BigDecimal presetId) {
    this.presetId = presetId;
  }



  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    UpdeteSettings updeteSettings = (UpdeteSettings) o;
    return Objects.equals(this.isAutoDeploy, updeteSettings.isAutoDeploy) &&
        Objects.equals(this.buildCmd, updeteSettings.buildCmd) &&
        Objects.equals(this.envs, updeteSettings.envs) &&
        Objects.equals(this.branchName, updeteSettings.branchName) &&
        Objects.equals(this.commitSha, updeteSettings.commitSha) &&
        Objects.equals(this.envVersion, updeteSettings.envVersion) &&
        Objects.equals(this.indexDir, updeteSettings.indexDir) &&
        Objects.equals(this.runCmd, updeteSettings.runCmd) &&
        Objects.equals(this.framework, updeteSettings.framework) &&
        Objects.equals(this.name, updeteSettings.name) &&
        Objects.equals(this.comment, updeteSettings.comment) &&
        Objects.equals(this.presetId, updeteSettings.presetId);
  }

  @Override
  public int hashCode() {
    return Objects.hash(isAutoDeploy, buildCmd, envs, branchName, commitSha, envVersion, indexDir, runCmd, framework, name, comment, presetId);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class UpdeteSettings {\n");
    sb.append("    isAutoDeploy: ").append(toIndentedString(isAutoDeploy)).append("\n");
    sb.append("    buildCmd: ").append(toIndentedString(buildCmd)).append("\n");
    sb.append("    envs: ").append(toIndentedString(envs)).append("\n");
    sb.append("    branchName: ").append(toIndentedString(branchName)).append("\n");
    sb.append("    commitSha: ").append(toIndentedString(commitSha)).append("\n");
    sb.append("    envVersion: ").append(toIndentedString(envVersion)).append("\n");
    sb.append("    indexDir: ").append(toIndentedString(indexDir)).append("\n");
    sb.append("    runCmd: ").append(toIndentedString(runCmd)).append("\n");
    sb.append("    framework: ").append(toIndentedString(framework)).append("\n");
    sb.append("    name: ").append(toIndentedString(name)).append("\n");
    sb.append("    comment: ").append(toIndentedString(comment)).append("\n");
    sb.append("    presetId: ").append(toIndentedString(presetId)).append("\n");
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
    openapiFields.add("is_auto_deploy");
    openapiFields.add("build_cmd");
    openapiFields.add("envs");
    openapiFields.add("branch_name");
    openapiFields.add("commit_sha");
    openapiFields.add("env_version");
    openapiFields.add("index_dir");
    openapiFields.add("run_cmd");
    openapiFields.add("framework");
    openapiFields.add("name");
    openapiFields.add("comment");
    openapiFields.add("preset_id");

    // a set of required properties/fields (JSON key names)
    openapiRequiredFields = new HashSet<String>();
  }

 /**
  * Validates the JSON Element and throws an exception if issues found
  *
  * @param jsonElement JSON Element
  * @throws IOException if the JSON Element is invalid with respect to UpdeteSettings
  */
  public static void validateJsonElement(JsonElement jsonElement) throws IOException {
      if (jsonElement == null) {
        if (!UpdeteSettings.openapiRequiredFields.isEmpty()) { // has required fields but JSON element is null
          throw new IllegalArgumentException(String.format("The required field(s) %s in UpdeteSettings is not found in the empty JSON string", UpdeteSettings.openapiRequiredFields.toString()));
        }
      }

      Set<Entry<String, JsonElement>> entries = jsonElement.getAsJsonObject().entrySet();
      // check to see if the JSON string contains additional fields
      for (Entry<String, JsonElement> entry : entries) {
        if (!UpdeteSettings.openapiFields.contains(entry.getKey())) {
          throw new IllegalArgumentException(String.format("The field `%s` in the JSON string is not defined in the `UpdeteSettings` properties. JSON: %s", entry.getKey(), jsonElement.toString()));
        }
      }
        JsonObject jsonObj = jsonElement.getAsJsonObject();
      if ((jsonObj.get("build_cmd") != null && !jsonObj.get("build_cmd").isJsonNull()) && !jsonObj.get("build_cmd").isJsonPrimitive()) {
        throw new IllegalArgumentException(String.format("Expected the field `build_cmd` to be a primitive type in the JSON string but got `%s`", jsonObj.get("build_cmd").toString()));
      }
      if ((jsonObj.get("branch_name") != null && !jsonObj.get("branch_name").isJsonNull()) && !jsonObj.get("branch_name").isJsonPrimitive()) {
        throw new IllegalArgumentException(String.format("Expected the field `branch_name` to be a primitive type in the JSON string but got `%s`", jsonObj.get("branch_name").toString()));
      }
      if ((jsonObj.get("commit_sha") != null && !jsonObj.get("commit_sha").isJsonNull()) && !jsonObj.get("commit_sha").isJsonPrimitive()) {
        throw new IllegalArgumentException(String.format("Expected the field `commit_sha` to be a primitive type in the JSON string but got `%s`", jsonObj.get("commit_sha").toString()));
      }
      if ((jsonObj.get("env_version") != null && !jsonObj.get("env_version").isJsonNull()) && !jsonObj.get("env_version").isJsonPrimitive()) {
        throw new IllegalArgumentException(String.format("Expected the field `env_version` to be a primitive type in the JSON string but got `%s`", jsonObj.get("env_version").toString()));
      }
      if ((jsonObj.get("index_dir") != null && !jsonObj.get("index_dir").isJsonNull()) && !jsonObj.get("index_dir").isJsonPrimitive()) {
        throw new IllegalArgumentException(String.format("Expected the field `index_dir` to be a primitive type in the JSON string but got `%s`", jsonObj.get("index_dir").toString()));
      }
      if ((jsonObj.get("run_cmd") != null && !jsonObj.get("run_cmd").isJsonNull()) && !jsonObj.get("run_cmd").isJsonPrimitive()) {
        throw new IllegalArgumentException(String.format("Expected the field `run_cmd` to be a primitive type in the JSON string but got `%s`", jsonObj.get("run_cmd").toString()));
      }
      if ((jsonObj.get("name") != null && !jsonObj.get("name").isJsonNull()) && !jsonObj.get("name").isJsonPrimitive()) {
        throw new IllegalArgumentException(String.format("Expected the field `name` to be a primitive type in the JSON string but got `%s`", jsonObj.get("name").toString()));
      }
      if ((jsonObj.get("comment") != null && !jsonObj.get("comment").isJsonNull()) && !jsonObj.get("comment").isJsonPrimitive()) {
        throw new IllegalArgumentException(String.format("Expected the field `comment` to be a primitive type in the JSON string but got `%s`", jsonObj.get("comment").toString()));
      }
  }

  public static class CustomTypeAdapterFactory implements TypeAdapterFactory {
    @SuppressWarnings("unchecked")
    @Override
    public <T> TypeAdapter<T> create(Gson gson, TypeToken<T> type) {
       if (!UpdeteSettings.class.isAssignableFrom(type.getRawType())) {
         return null; // this class only serializes 'UpdeteSettings' and its subtypes
       }
       final TypeAdapter<JsonElement> elementAdapter = gson.getAdapter(JsonElement.class);
       final TypeAdapter<UpdeteSettings> thisAdapter
                        = gson.getDelegateAdapter(this, TypeToken.get(UpdeteSettings.class));

       return (TypeAdapter<T>) new TypeAdapter<UpdeteSettings>() {
           @Override
           public void write(JsonWriter out, UpdeteSettings value) throws IOException {
             JsonObject obj = thisAdapter.toJsonTree(value).getAsJsonObject();
             elementAdapter.write(out, obj);
           }

           @Override
           public UpdeteSettings read(JsonReader in) throws IOException {
             JsonElement jsonElement = elementAdapter.read(in);
             validateJsonElement(jsonElement);
             return thisAdapter.fromJsonTree(jsonElement);
           }

       }.nullSafe();
    }
  }

 /**
  * Create an instance of UpdeteSettings given an JSON string
  *
  * @param jsonString JSON string
  * @return An instance of UpdeteSettings
  * @throws IOException if the JSON string is invalid with respect to UpdeteSettings
  */
  public static UpdeteSettings fromJson(String jsonString) throws IOException {
    return JSON.getGson().fromJson(jsonString, UpdeteSettings.class);
  }

 /**
  * Convert an instance of UpdeteSettings to an JSON string
  *
  * @return JSON string
  */
  public String toJson() {
    return JSON.getGson().toJson(this);
  }
}

