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
import org.openapitools.client.model.Bonus;
import org.openapitools.client.model.Free;
import org.openapitools.client.model.Invoice;
import org.openapitools.client.model.Use;



import java.io.IOException;
import java.lang.reflect.Type;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonParseException;
import com.google.gson.TypeAdapter;
import com.google.gson.TypeAdapterFactory;
import com.google.gson.reflect.TypeToken;
import com.google.gson.JsonPrimitive;
import com.google.gson.annotations.JsonAdapter;
import com.google.gson.annotations.SerializedName;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonArray;
import com.google.gson.JsonParseException;

import org.openapitools.client.JSON;

@javax.annotation.Generated(value = "org.openapitools.codegen.languages.JavaClientCodegen", date = "2023-08-21T14:21:17.845880Z[Etc/UTC]")
public class UpdateDomainRequestRequest extends AbstractOpenApiSchema {
    private static final Logger log = Logger.getLogger(UpdateDomainRequestRequest.class.getName());

    public static class CustomTypeAdapterFactory implements TypeAdapterFactory {
        @SuppressWarnings("unchecked")
        @Override
        public <T> TypeAdapter<T> create(Gson gson, TypeToken<T> type) {
            if (!UpdateDomainRequestRequest.class.isAssignableFrom(type.getRawType())) {
                return null; // this class only serializes 'UpdateDomainRequestRequest' and its subtypes
            }
            final TypeAdapter<JsonElement> elementAdapter = gson.getAdapter(JsonElement.class);
            final TypeAdapter<Use> adapterUse = gson.getDelegateAdapter(this, TypeToken.get(Use.class));
            final TypeAdapter<Invoice> adapterInvoice = gson.getDelegateAdapter(this, TypeToken.get(Invoice.class));
            final TypeAdapter<Free> adapterFree = gson.getDelegateAdapter(this, TypeToken.get(Free.class));
            final TypeAdapter<Bonus> adapterBonus = gson.getDelegateAdapter(this, TypeToken.get(Bonus.class));

            return (TypeAdapter<T>) new TypeAdapter<UpdateDomainRequestRequest>() {
                @Override
                public void write(JsonWriter out, UpdateDomainRequestRequest value) throws IOException {
                    if (value == null || value.getActualInstance() == null) {
                        elementAdapter.write(out, null);
                        return;
                    }

                    // check if the actual instance is of the type `Use`
                    if (value.getActualInstance() instanceof Use) {
                      JsonElement element = adapterUse.toJsonTree((Use)value.getActualInstance());
                      elementAdapter.write(out, element);
                      return;
                    }
                    // check if the actual instance is of the type `Invoice`
                    if (value.getActualInstance() instanceof Invoice) {
                      JsonElement element = adapterInvoice.toJsonTree((Invoice)value.getActualInstance());
                      elementAdapter.write(out, element);
                      return;
                    }
                    // check if the actual instance is of the type `Free`
                    if (value.getActualInstance() instanceof Free) {
                      JsonElement element = adapterFree.toJsonTree((Free)value.getActualInstance());
                      elementAdapter.write(out, element);
                      return;
                    }
                    // check if the actual instance is of the type `Bonus`
                    if (value.getActualInstance() instanceof Bonus) {
                      JsonElement element = adapterBonus.toJsonTree((Bonus)value.getActualInstance());
                      elementAdapter.write(out, element);
                      return;
                    }
                    throw new IOException("Failed to serialize as the type doesn't match oneOf schemas: Bonus, Free, Invoice, Use");
                }

                @Override
                public UpdateDomainRequestRequest read(JsonReader in) throws IOException {
                    Object deserialized = null;
                    JsonElement jsonElement = elementAdapter.read(in);

                    int match = 0;
                    ArrayList<String> errorMessages = new ArrayList<>();
                    TypeAdapter actualAdapter = elementAdapter;

                    // deserialize Use
                    try {
                      // validate the JSON object to see if any exception is thrown
                      Use.validateJsonElement(jsonElement);
                      actualAdapter = adapterUse;
                      match++;
                      log.log(Level.FINER, "Input data matches schema 'Use'");
                    } catch (Exception e) {
                      // deserialization failed, continue
                      errorMessages.add(String.format("Deserialization for Use failed with `%s`.", e.getMessage()));
                      log.log(Level.FINER, "Input data does not match schema 'Use'", e);
                    }
                    // deserialize Invoice
                    try {
                      // validate the JSON object to see if any exception is thrown
                      Invoice.validateJsonElement(jsonElement);
                      actualAdapter = adapterInvoice;
                      match++;
                      log.log(Level.FINER, "Input data matches schema 'Invoice'");
                    } catch (Exception e) {
                      // deserialization failed, continue
                      errorMessages.add(String.format("Deserialization for Invoice failed with `%s`.", e.getMessage()));
                      log.log(Level.FINER, "Input data does not match schema 'Invoice'", e);
                    }
                    // deserialize Free
                    try {
                      // validate the JSON object to see if any exception is thrown
                      Free.validateJsonElement(jsonElement);
                      actualAdapter = adapterFree;
                      match++;
                      log.log(Level.FINER, "Input data matches schema 'Free'");
                    } catch (Exception e) {
                      // deserialization failed, continue
                      errorMessages.add(String.format("Deserialization for Free failed with `%s`.", e.getMessage()));
                      log.log(Level.FINER, "Input data does not match schema 'Free'", e);
                    }
                    // deserialize Bonus
                    try {
                      // validate the JSON object to see if any exception is thrown
                      Bonus.validateJsonElement(jsonElement);
                      actualAdapter = adapterBonus;
                      match++;
                      log.log(Level.FINER, "Input data matches schema 'Bonus'");
                    } catch (Exception e) {
                      // deserialization failed, continue
                      errorMessages.add(String.format("Deserialization for Bonus failed with `%s`.", e.getMessage()));
                      log.log(Level.FINER, "Input data does not match schema 'Bonus'", e);
                    }

                    if (match == 1) {
                        UpdateDomainRequestRequest ret = new UpdateDomainRequestRequest();
                        ret.setActualInstance(actualAdapter.fromJsonTree(jsonElement));
                        return ret;
                    }

                    throw new IOException(String.format("Failed deserialization for UpdateDomainRequestRequest: %d classes match result, expected 1. Detailed failure message for oneOf schemas: %s. JSON: %s", match, errorMessages, jsonElement.toString()));
                }
            }.nullSafe();
        }
    }

    // store a list of schema names defined in oneOf
    public static final Map<String, Class<?>> schemas = new HashMap<String, Class<?>>();

    public UpdateDomainRequestRequest() {
        super("oneOf", Boolean.FALSE);
    }

    public UpdateDomainRequestRequest(Bonus o) {
        super("oneOf", Boolean.FALSE);
        setActualInstance(o);
    }

    public UpdateDomainRequestRequest(Free o) {
        super("oneOf", Boolean.FALSE);
        setActualInstance(o);
    }

    public UpdateDomainRequestRequest(Invoice o) {
        super("oneOf", Boolean.FALSE);
        setActualInstance(o);
    }

    public UpdateDomainRequestRequest(Use o) {
        super("oneOf", Boolean.FALSE);
        setActualInstance(o);
    }

    static {
        schemas.put("Use", Use.class);
        schemas.put("Invoice", Invoice.class);
        schemas.put("Free", Free.class);
        schemas.put("Bonus", Bonus.class);
    }

    @Override
    public Map<String, Class<?>> getSchemas() {
        return UpdateDomainRequestRequest.schemas;
    }

    /**
     * Set the instance that matches the oneOf child schema, check
     * the instance parameter is valid against the oneOf child schemas:
     * Bonus, Free, Invoice, Use
     *
     * It could be an instance of the 'oneOf' schemas.
     * The oneOf child schemas may themselves be a composed schema (allOf, anyOf, oneOf).
     */
    @Override
    public void setActualInstance(Object instance) {
        if (instance instanceof Use) {
            super.setActualInstance(instance);
            return;
        }

        if (instance instanceof Invoice) {
            super.setActualInstance(instance);
            return;
        }

        if (instance instanceof Free) {
            super.setActualInstance(instance);
            return;
        }

        if (instance instanceof Bonus) {
            super.setActualInstance(instance);
            return;
        }

        throw new RuntimeException("Invalid instance type. Must be Bonus, Free, Invoice, Use");
    }

    /**
     * Get the actual instance, which can be the following:
     * Bonus, Free, Invoice, Use
     *
     * @return The actual instance (Bonus, Free, Invoice, Use)
     */
    @Override
    public Object getActualInstance() {
        return super.getActualInstance();
    }

    /**
     * Get the actual instance of `Use`. If the actual instance is not `Use`,
     * the ClassCastException will be thrown.
     *
     * @return The actual instance of `Use`
     * @throws ClassCastException if the instance is not `Use`
     */
    public Use getUse() throws ClassCastException {
        return (Use)super.getActualInstance();
    }
    /**
     * Get the actual instance of `Invoice`. If the actual instance is not `Invoice`,
     * the ClassCastException will be thrown.
     *
     * @return The actual instance of `Invoice`
     * @throws ClassCastException if the instance is not `Invoice`
     */
    public Invoice getInvoice() throws ClassCastException {
        return (Invoice)super.getActualInstance();
    }
    /**
     * Get the actual instance of `Free`. If the actual instance is not `Free`,
     * the ClassCastException will be thrown.
     *
     * @return The actual instance of `Free`
     * @throws ClassCastException if the instance is not `Free`
     */
    public Free getFree() throws ClassCastException {
        return (Free)super.getActualInstance();
    }
    /**
     * Get the actual instance of `Bonus`. If the actual instance is not `Bonus`,
     * the ClassCastException will be thrown.
     *
     * @return The actual instance of `Bonus`
     * @throws ClassCastException if the instance is not `Bonus`
     */
    public Bonus getBonus() throws ClassCastException {
        return (Bonus)super.getActualInstance();
    }

 /**
  * Validates the JSON Element and throws an exception if issues found
  *
  * @param jsonElement JSON Element
  * @throws IOException if the JSON Element is invalid with respect to UpdateDomainRequestRequest
  */
  public static void validateJsonElement(JsonElement jsonElement) throws IOException {
    // validate oneOf schemas one by one
    int validCount = 0;
    ArrayList<String> errorMessages = new ArrayList<>();
    // validate the json string with Use
    try {
      Use.validateJsonElement(jsonElement);
      validCount++;
    } catch (Exception e) {
      errorMessages.add(String.format("Deserialization for Use failed with `%s`.", e.getMessage()));
      // continue to the next one
    }
    // validate the json string with Invoice
    try {
      Invoice.validateJsonElement(jsonElement);
      validCount++;
    } catch (Exception e) {
      errorMessages.add(String.format("Deserialization for Invoice failed with `%s`.", e.getMessage()));
      // continue to the next one
    }
    // validate the json string with Free
    try {
      Free.validateJsonElement(jsonElement);
      validCount++;
    } catch (Exception e) {
      errorMessages.add(String.format("Deserialization for Free failed with `%s`.", e.getMessage()));
      // continue to the next one
    }
    // validate the json string with Bonus
    try {
      Bonus.validateJsonElement(jsonElement);
      validCount++;
    } catch (Exception e) {
      errorMessages.add(String.format("Deserialization for Bonus failed with `%s`.", e.getMessage()));
      // continue to the next one
    }
    if (validCount != 1) {
      throw new IOException(String.format("The JSON string is invalid for UpdateDomainRequestRequest with oneOf schemas: Bonus, Free, Invoice, Use. %d class(es) match the result, expected 1. Detailed failure message for oneOf schemas: %s. JSON: %s", validCount, errorMessages, jsonElement.toString()));
    }
  }

 /**
  * Create an instance of UpdateDomainRequestRequest given an JSON string
  *
  * @param jsonString JSON string
  * @return An instance of UpdateDomainRequestRequest
  * @throws IOException if the JSON string is invalid with respect to UpdateDomainRequestRequest
  */
  public static UpdateDomainRequestRequest fromJson(String jsonString) throws IOException {
    return JSON.getGson().fromJson(jsonString, UpdateDomainRequestRequest.class);
  }

 /**
  * Convert an instance of UpdateDomainRequestRequest to an JSON string
  *
  * @return JSON string
  */
  public String toJson() {
    return JSON.getGson().toJson(this);
  }
}

