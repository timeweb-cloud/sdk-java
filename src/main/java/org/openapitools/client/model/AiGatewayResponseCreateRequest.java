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
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.openapitools.client.model.AiGatewayResponseCreateRequestReasoning;

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
 * Запрос Responses API в формате OpenAI. Остальные параметры OpenAI передаются модели как есть
 */
@javax.annotation.Generated(value = "org.openapitools.codegen.languages.JavaClientCodegen", date = "2026-10-23T14:06:46.753006Z[Etc/UTC]")
public class AiGatewayResponseCreateRequest {
  public static final String SERIALIZED_NAME_MODEL = "model";
  @SerializedName(SERIALIZED_NAME_MODEL)
  private String model;

  public static final String SERIALIZED_NAME_INPUT = "input";
  @SerializedName(SERIALIZED_NAME_INPUT)
  private String input;

  public static final String SERIALIZED_NAME_INSTRUCTIONS = "instructions";
  @SerializedName(SERIALIZED_NAME_INSTRUCTIONS)
  private String instructions;

  public static final String SERIALIZED_NAME_PREVIOUS_RESPONSE_ID = "previous_response_id";
  @SerializedName(SERIALIZED_NAME_PREVIOUS_RESPONSE_ID)
  private String previousResponseId;

  public static final String SERIALIZED_NAME_STREAM = "stream";
  @SerializedName(SERIALIZED_NAME_STREAM)
  private Boolean stream = false;

  public static final String SERIALIZED_NAME_BACKGROUND = "background";
  @SerializedName(SERIALIZED_NAME_BACKGROUND)
  private Boolean background;

  public static final String SERIALIZED_NAME_STORE = "store";
  @SerializedName(SERIALIZED_NAME_STORE)
  private Boolean store;

  public static final String SERIALIZED_NAME_MAX_OUTPUT_TOKENS = "max_output_tokens";
  @SerializedName(SERIALIZED_NAME_MAX_OUTPUT_TOKENS)
  private Integer maxOutputTokens;

  public static final String SERIALIZED_NAME_TOOLS = "tools";
  @SerializedName(SERIALIZED_NAME_TOOLS)
  private List<Map<String, Object>> tools;

  public static final String SERIALIZED_NAME_REASONING = "reasoning";
  @SerializedName(SERIALIZED_NAME_REASONING)
  private AiGatewayResponseCreateRequestReasoning reasoning;

  public static final String SERIALIZED_NAME_TEXT = "text";
  @SerializedName(SERIALIZED_NAME_TEXT)
  private Map<String, Object> text = new HashMap<>();

  public static final String SERIALIZED_NAME_TEMPERATURE = "temperature";
  @SerializedName(SERIALIZED_NAME_TEMPERATURE)
  private BigDecimal temperature;

  public AiGatewayResponseCreateRequest() {
  }

  public AiGatewayResponseCreateRequest model(String model) {
    
    this.model = model;
    return this;
  }

   /**
   * ID модели из &#x60;GET /v1/models&#x60;
   * @return model
  **/
  @javax.annotation.Nonnull
  public String getModel() {
    return model;
  }


  public void setModel(String model) {
    this.model = model;
  }


  public AiGatewayResponseCreateRequest input(String input) {
    
    this.input = input;
    return this;
  }

   /**
   * Текст или массив входных элементов (сообщения, результаты вызова функций и т. п.)
   * @return input
  **/
  @javax.annotation.Nonnull
  public String getInput() {
    return input;
  }


  public void setInput(String input) {
    this.input = input;
  }


  public AiGatewayResponseCreateRequest instructions(String instructions) {
    
    this.instructions = instructions;
    return this;
  }

   /**
   * Системные инструкции
   * @return instructions
  **/
  @javax.annotation.Nullable
  public String getInstructions() {
    return instructions;
  }


  public void setInstructions(String instructions) {
    this.instructions = instructions;
  }


  public AiGatewayResponseCreateRequest previousResponseId(String previousResponseId) {
    
    this.previousResponseId = previousResponseId;
    return this;
  }

   /**
   * ID сохранённого ответа, чтобы продолжить диалог
   * @return previousResponseId
  **/
  @javax.annotation.Nullable
  public String getPreviousResponseId() {
    return previousResponseId;
  }


  public void setPreviousResponseId(String previousResponseId) {
    this.previousResponseId = previousResponseId;
  }


  public AiGatewayResponseCreateRequest stream(Boolean stream) {
    
    this.stream = stream;
    return this;
  }

   /**
   * Отправлять ответ потоком типизированных событий Server-Sent Events
   * @return stream
  **/
  @javax.annotation.Nullable
  public Boolean getStream() {
    return stream;
  }


  public void setStream(Boolean stream) {
    this.stream = stream;
  }


  public AiGatewayResponseCreateRequest background(Boolean background) {
    
    this.background = background;
    return this;
  }

   /**
   * Выполнить запрос в фоне: результат получайте через &#x60;GET /v1/responses/{response_id}&#x60;
   * @return background
  **/
  @javax.annotation.Nullable
  public Boolean getBackground() {
    return background;
  }


  public void setBackground(Boolean background) {
    this.background = background;
  }


  public AiGatewayResponseCreateRequest store(Boolean store) {
    
    this.store = store;
    return this;
  }

   /**
   * Сохранить ответ, чтобы получить его позже или продолжить диалог
   * @return store
  **/
  @javax.annotation.Nullable
  public Boolean getStore() {
    return store;
  }


  public void setStore(Boolean store) {
    this.store = store;
  }


  public AiGatewayResponseCreateRequest maxOutputTokens(Integer maxOutputTokens) {
    
    this.maxOutputTokens = maxOutputTokens;
    return this;
  }

   /**
   * Максимум выходных токенов
   * @return maxOutputTokens
  **/
  @javax.annotation.Nullable
  public Integer getMaxOutputTokens() {
    return maxOutputTokens;
  }


  public void setMaxOutputTokens(Integer maxOutputTokens) {
    this.maxOutputTokens = maxOutputTokens;
  }


  public AiGatewayResponseCreateRequest tools(List<Map<String, Object>> tools) {
    
    this.tools = tools;
    return this;
  }

  public AiGatewayResponseCreateRequest addToolsItem(Map<String, Object> toolsItem) {
    if (this.tools == null) {
      this.tools = new ArrayList<>();
    }
    this.tools.add(toolsItem);
    return this;
  }

   /**
   * Инструменты, которые может вызвать модель
   * @return tools
  **/
  @javax.annotation.Nullable
  public List<Map<String, Object>> getTools() {
    return tools;
  }


  public void setTools(List<Map<String, Object>> tools) {
    this.tools = tools;
  }


  public AiGatewayResponseCreateRequest reasoning(AiGatewayResponseCreateRequestReasoning reasoning) {
    
    this.reasoning = reasoning;
    return this;
  }

   /**
   * Get reasoning
   * @return reasoning
  **/
  @javax.annotation.Nullable
  public AiGatewayResponseCreateRequestReasoning getReasoning() {
    return reasoning;
  }


  public void setReasoning(AiGatewayResponseCreateRequestReasoning reasoning) {
    this.reasoning = reasoning;
  }


  public AiGatewayResponseCreateRequest text(Map<String, Object> text) {
    
    this.text = text;
    return this;
  }

  public AiGatewayResponseCreateRequest putTextItem(String key, Object textItem) {
    if (this.text == null) {
      this.text = new HashMap<>();
    }
    this.text.put(key, textItem);
    return this;
  }

   /**
   * Настройки формата текста ответа
   * @return text
  **/
  @javax.annotation.Nullable
  public Map<String, Object> getText() {
    return text;
  }


  public void setText(Map<String, Object> text) {
    this.text = text;
  }


  public AiGatewayResponseCreateRequest temperature(BigDecimal temperature) {
    
    this.temperature = temperature;
    return this;
  }

   /**
   * Температура выборки
   * @return temperature
  **/
  @javax.annotation.Nullable
  public BigDecimal getTemperature() {
    return temperature;
  }


  public void setTemperature(BigDecimal temperature) {
    this.temperature = temperature;
  }

  /**
   * A container for additional, undeclared properties.
   * This is a holder for any undeclared properties as specified with
   * the 'additionalProperties' keyword in the OAS document.
   */
  private Map<String, Object> additionalProperties;

  /**
   * Set the additional (undeclared) property with the specified name and value.
   * If the property does not already exist, create it otherwise replace it.
   *
   * @param key name of the property
   * @param value value of the property
   * @return the AiGatewayResponseCreateRequest instance itself
   */
  public AiGatewayResponseCreateRequest putAdditionalProperty(String key, Object value) {
    if (this.additionalProperties == null) {
        this.additionalProperties = new HashMap<String, Object>();
    }
    this.additionalProperties.put(key, value);
    return this;
  }

  /**
   * Return the additional (undeclared) property.
   *
   * @return a map of objects
   */
  public Map<String, Object> getAdditionalProperties() {
    return additionalProperties;
  }

  /**
   * Return the additional (undeclared) property with the specified name.
   *
   * @param key name of the property
   * @return an object
   */
  public Object getAdditionalProperty(String key) {
    if (this.additionalProperties == null) {
        return null;
    }
    return this.additionalProperties.get(key);
  }


  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    AiGatewayResponseCreateRequest aiGatewayResponseCreateRequest = (AiGatewayResponseCreateRequest) o;
    return Objects.equals(this.model, aiGatewayResponseCreateRequest.model) &&
        Objects.equals(this.input, aiGatewayResponseCreateRequest.input) &&
        Objects.equals(this.instructions, aiGatewayResponseCreateRequest.instructions) &&
        Objects.equals(this.previousResponseId, aiGatewayResponseCreateRequest.previousResponseId) &&
        Objects.equals(this.stream, aiGatewayResponseCreateRequest.stream) &&
        Objects.equals(this.background, aiGatewayResponseCreateRequest.background) &&
        Objects.equals(this.store, aiGatewayResponseCreateRequest.store) &&
        Objects.equals(this.maxOutputTokens, aiGatewayResponseCreateRequest.maxOutputTokens) &&
        Objects.equals(this.tools, aiGatewayResponseCreateRequest.tools) &&
        Objects.equals(this.reasoning, aiGatewayResponseCreateRequest.reasoning) &&
        Objects.equals(this.text, aiGatewayResponseCreateRequest.text) &&
        Objects.equals(this.temperature, aiGatewayResponseCreateRequest.temperature)&&
        Objects.equals(this.additionalProperties, aiGatewayResponseCreateRequest.additionalProperties);
  }

  @Override
  public int hashCode() {
    return Objects.hash(model, input, instructions, previousResponseId, stream, background, store, maxOutputTokens, tools, reasoning, text, temperature, additionalProperties);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class AiGatewayResponseCreateRequest {\n");
    sb.append("    model: ").append(toIndentedString(model)).append("\n");
    sb.append("    input: ").append(toIndentedString(input)).append("\n");
    sb.append("    instructions: ").append(toIndentedString(instructions)).append("\n");
    sb.append("    previousResponseId: ").append(toIndentedString(previousResponseId)).append("\n");
    sb.append("    stream: ").append(toIndentedString(stream)).append("\n");
    sb.append("    background: ").append(toIndentedString(background)).append("\n");
    sb.append("    store: ").append(toIndentedString(store)).append("\n");
    sb.append("    maxOutputTokens: ").append(toIndentedString(maxOutputTokens)).append("\n");
    sb.append("    tools: ").append(toIndentedString(tools)).append("\n");
    sb.append("    reasoning: ").append(toIndentedString(reasoning)).append("\n");
    sb.append("    text: ").append(toIndentedString(text)).append("\n");
    sb.append("    temperature: ").append(toIndentedString(temperature)).append("\n");
    sb.append("    additionalProperties: ").append(toIndentedString(additionalProperties)).append("\n");
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
    openapiFields.add("model");
    openapiFields.add("input");
    openapiFields.add("instructions");
    openapiFields.add("previous_response_id");
    openapiFields.add("stream");
    openapiFields.add("background");
    openapiFields.add("store");
    openapiFields.add("max_output_tokens");
    openapiFields.add("tools");
    openapiFields.add("reasoning");
    openapiFields.add("text");
    openapiFields.add("temperature");

    // a set of required properties/fields (JSON key names)
    openapiRequiredFields = new HashSet<String>();
    openapiRequiredFields.add("model");
    openapiRequiredFields.add("input");
  }

 /**
  * Validates the JSON Element and throws an exception if issues found
  *
  * @param jsonElement JSON Element
  * @throws IOException if the JSON Element is invalid with respect to AiGatewayResponseCreateRequest
  */
  public static void validateJsonElement(JsonElement jsonElement) throws IOException {
      if (jsonElement == null) {
        if (!AiGatewayResponseCreateRequest.openapiRequiredFields.isEmpty()) { // has required fields but JSON element is null
          throw new IllegalArgumentException(String.format("The required field(s) %s in AiGatewayResponseCreateRequest is not found in the empty JSON string", AiGatewayResponseCreateRequest.openapiRequiredFields.toString()));
        }
      }

      // check to make sure all required properties/fields are present in the JSON string
      for (String requiredField : AiGatewayResponseCreateRequest.openapiRequiredFields) {
        if (jsonElement.getAsJsonObject().get(requiredField) == null) {
          throw new IllegalArgumentException(String.format("The required field `%s` is not found in the JSON string: %s", requiredField, jsonElement.toString()));
        }
      }
        JsonObject jsonObj = jsonElement.getAsJsonObject();
      if (!jsonObj.get("model").isJsonPrimitive()) {
        throw new IllegalArgumentException(String.format("Expected the field `model` to be a primitive type in the JSON string but got `%s`", jsonObj.get("model").toString()));
      }
      if (!jsonObj.get("input").isJsonPrimitive()) {
        throw new IllegalArgumentException(String.format("Expected the field `input` to be a primitive type in the JSON string but got `%s`", jsonObj.get("input").toString()));
      }
      if ((jsonObj.get("instructions") != null && !jsonObj.get("instructions").isJsonNull()) && !jsonObj.get("instructions").isJsonPrimitive()) {
        throw new IllegalArgumentException(String.format("Expected the field `instructions` to be a primitive type in the JSON string but got `%s`", jsonObj.get("instructions").toString()));
      }
      if ((jsonObj.get("previous_response_id") != null && !jsonObj.get("previous_response_id").isJsonNull()) && !jsonObj.get("previous_response_id").isJsonPrimitive()) {
        throw new IllegalArgumentException(String.format("Expected the field `previous_response_id` to be a primitive type in the JSON string but got `%s`", jsonObj.get("previous_response_id").toString()));
      }
      // ensure the optional json data is an array if present
      if (jsonObj.get("tools") != null && !jsonObj.get("tools").isJsonArray()) {
        throw new IllegalArgumentException(String.format("Expected the field `tools` to be an array in the JSON string but got `%s`", jsonObj.get("tools").toString()));
      }
  }

  public static class CustomTypeAdapterFactory implements TypeAdapterFactory {
    @SuppressWarnings("unchecked")
    @Override
    public <T> TypeAdapter<T> create(Gson gson, TypeToken<T> type) {
       if (!AiGatewayResponseCreateRequest.class.isAssignableFrom(type.getRawType())) {
         return null; // this class only serializes 'AiGatewayResponseCreateRequest' and its subtypes
       }
       final TypeAdapter<JsonElement> elementAdapter = gson.getAdapter(JsonElement.class);
       final TypeAdapter<AiGatewayResponseCreateRequest> thisAdapter
                        = gson.getDelegateAdapter(this, TypeToken.get(AiGatewayResponseCreateRequest.class));

       return (TypeAdapter<T>) new TypeAdapter<AiGatewayResponseCreateRequest>() {
           @Override
           public void write(JsonWriter out, AiGatewayResponseCreateRequest value) throws IOException {
             JsonObject obj = thisAdapter.toJsonTree(value).getAsJsonObject();
             obj.remove("additionalProperties");
             // serialize additional properties
             if (value.getAdditionalProperties() != null) {
               for (Map.Entry<String, Object> entry : value.getAdditionalProperties().entrySet()) {
                 if (entry.getValue() instanceof String)
                   obj.addProperty(entry.getKey(), (String) entry.getValue());
                 else if (entry.getValue() instanceof Number)
                   obj.addProperty(entry.getKey(), (Number) entry.getValue());
                 else if (entry.getValue() instanceof Boolean)
                   obj.addProperty(entry.getKey(), (Boolean) entry.getValue());
                 else if (entry.getValue() instanceof Character)
                   obj.addProperty(entry.getKey(), (Character) entry.getValue());
                 else {
                   obj.add(entry.getKey(), gson.toJsonTree(entry.getValue()).getAsJsonObject());
                 }
               }
             }
             elementAdapter.write(out, obj);
           }

           @Override
           public AiGatewayResponseCreateRequest read(JsonReader in) throws IOException {
             JsonElement jsonElement = elementAdapter.read(in);
             validateJsonElement(jsonElement);
             JsonObject jsonObj = jsonElement.getAsJsonObject();
             // store additional fields in the deserialized instance
             AiGatewayResponseCreateRequest instance = thisAdapter.fromJsonTree(jsonObj);
             for (Map.Entry<String, JsonElement> entry : jsonObj.entrySet()) {
               if (!openapiFields.contains(entry.getKey())) {
                 if (entry.getValue().isJsonPrimitive()) { // primitive type
                   if (entry.getValue().getAsJsonPrimitive().isString())
                     instance.putAdditionalProperty(entry.getKey(), entry.getValue().getAsString());
                   else if (entry.getValue().getAsJsonPrimitive().isNumber())
                     instance.putAdditionalProperty(entry.getKey(), entry.getValue().getAsNumber());
                   else if (entry.getValue().getAsJsonPrimitive().isBoolean())
                     instance.putAdditionalProperty(entry.getKey(), entry.getValue().getAsBoolean());
                   else
                     throw new IllegalArgumentException(String.format("The field `%s` has unknown primitive type. Value: %s", entry.getKey(), entry.getValue().toString()));
                 } else if (entry.getValue().isJsonArray()) {
                     instance.putAdditionalProperty(entry.getKey(), gson.fromJson(entry.getValue(), List.class));
                 } else { // JSON object
                     instance.putAdditionalProperty(entry.getKey(), gson.fromJson(entry.getValue(), HashMap.class));
                 }
               }
             }
             return instance;
           }

       }.nullSafe();
    }
  }

 /**
  * Create an instance of AiGatewayResponseCreateRequest given an JSON string
  *
  * @param jsonString JSON string
  * @return An instance of AiGatewayResponseCreateRequest
  * @throws IOException if the JSON string is invalid with respect to AiGatewayResponseCreateRequest
  */
  public static AiGatewayResponseCreateRequest fromJson(String jsonString) throws IOException {
    return JSON.getGson().fromJson(jsonString, AiGatewayResponseCreateRequest.class);
  }

 /**
  * Convert an instance of AiGatewayResponseCreateRequest to an JSON string
  *
  * @return JSON string
  */
  public String toJson() {
    return JSON.getGson().toJson(this);
  }
}

