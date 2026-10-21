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


package org.openapitools.client.api;

import org.openapitools.client.ApiCallback;
import org.openapitools.client.ApiClient;
import org.openapitools.client.ApiException;
import org.openapitools.client.ApiResponse;
import org.openapitools.client.Configuration;
import org.openapitools.client.Pair;
import org.openapitools.client.ProgressRequestBody;
import org.openapitools.client.ProgressResponseBody;

import com.google.gson.reflect.TypeToken;

import java.io.IOException;


import org.openapitools.client.model.GetFinances401Response;
import org.openapitools.client.model.JsonRpcErrorResponse;
import org.openapitools.client.model.JsonRpcRequest;
import org.openapitools.client.model.JsonRpcResultResponse;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class McpApi {
    private ApiClient localVarApiClient;
    private int localHostIndex;
    private String localCustomBaseUrl;

    public McpApi() {
        this(Configuration.getDefaultApiClient());
    }

    public McpApi(ApiClient apiClient) {
        this.localVarApiClient = apiClient;
    }

    public ApiClient getApiClient() {
        return localVarApiClient;
    }

    public void setApiClient(ApiClient apiClient) {
        this.localVarApiClient = apiClient;
    }

    public int getHostIndex() {
        return localHostIndex;
    }

    public void setHostIndex(int hostIndex) {
        this.localHostIndex = hostIndex;
    }

    public String getCustomBaseUrl() {
        return localCustomBaseUrl;
    }

    public void setCustomBaseUrl(String customBaseUrl) {
        this.localCustomBaseUrl = customBaseUrl;
    }

    /**
     * Build call for deleteMcpSearchSession
     * @param _callback Callback for upload/download progress
     * @return Call to execute
     * @throws ApiException If fail to serialize the request body object
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> Готово, тела нет </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Не авторизован </td><td>  -  </td></tr>
        <tr><td> 429 </td><td> Превышен лимит запросов для токена </td><td>  -  </td></tr>
     </table>
     */
    public okhttp3.Call deleteMcpSearchSessionCall(final ApiCallback _callback) throws ApiException {
        String basePath = null;
        // Operation Servers
        String[] localBasePaths = new String[] { "https://api.timeweb.cloud" };

        // Determine Base Path to Use
        if (localCustomBaseUrl != null){
            basePath = localCustomBaseUrl;
        } else if ( localBasePaths.length > 0 ) {
            basePath = localBasePaths[localHostIndex];
        } else {
            basePath = null;
        }

        Object localVarPostBody = null;

        // create path and map variables
        String localVarPath = "/api/v1/mcp/search";

        List<Pair> localVarQueryParams = new ArrayList<Pair>();
        List<Pair> localVarCollectionQueryParams = new ArrayList<Pair>();
        Map<String, String> localVarHeaderParams = new HashMap<String, String>();
        Map<String, String> localVarCookieParams = new HashMap<String, String>();
        Map<String, Object> localVarFormParams = new HashMap<String, Object>();

        final String[] localVarAccepts = {
            "application/json"
        };
        final String localVarAccept = localVarApiClient.selectHeaderAccept(localVarAccepts);
        if (localVarAccept != null) {
            localVarHeaderParams.put("Accept", localVarAccept);
        }

        final String[] localVarContentTypes = {
        };
        final String localVarContentType = localVarApiClient.selectHeaderContentType(localVarContentTypes);
        if (localVarContentType != null) {
            localVarHeaderParams.put("Content-Type", localVarContentType);
        }

        String[] localVarAuthNames = new String[] { "Bearer" };
        return localVarApiClient.buildCall(basePath, localVarPath, "DELETE", localVarQueryParams, localVarCollectionQueryParams, localVarPostBody, localVarHeaderParams, localVarCookieParams, localVarFormParams, localVarAuthNames, _callback);
    }

    @SuppressWarnings("rawtypes")
    private okhttp3.Call deleteMcpSearchSessionValidateBeforeCall(final ApiCallback _callback) throws ApiException {
        return deleteMcpSearchSessionCall(_callback);

    }

    /**
     * Завершение сессии сервера MCP с поиском инструментов
     * Чтобы завершить сессию сервера MCP &#x60;timeweb-cloud-mcp-search&#x60;, отправьте DELETE-запрос на &#x60;/api/v1/mcp/search&#x60;.  Сервер не хранит сессий (не выдаёт &#x60;Mcp-Session-Id&#x60;), поэтому завершать нечего: ответ всегда 200 без тела.
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> Готово, тела нет </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Не авторизован </td><td>  -  </td></tr>
        <tr><td> 429 </td><td> Превышен лимит запросов для токена </td><td>  -  </td></tr>
     </table>
     */
    public void deleteMcpSearchSession() throws ApiException {
        deleteMcpSearchSessionWithHttpInfo();
    }

    /**
     * Завершение сессии сервера MCP с поиском инструментов
     * Чтобы завершить сессию сервера MCP &#x60;timeweb-cloud-mcp-search&#x60;, отправьте DELETE-запрос на &#x60;/api/v1/mcp/search&#x60;.  Сервер не хранит сессий (не выдаёт &#x60;Mcp-Session-Id&#x60;), поэтому завершать нечего: ответ всегда 200 без тела.
     * @return ApiResponse&lt;Void&gt;
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> Готово, тела нет </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Не авторизован </td><td>  -  </td></tr>
        <tr><td> 429 </td><td> Превышен лимит запросов для токена </td><td>  -  </td></tr>
     </table>
     */
    public ApiResponse<Void> deleteMcpSearchSessionWithHttpInfo() throws ApiException {
        okhttp3.Call localVarCall = deleteMcpSearchSessionValidateBeforeCall(null);
        return localVarApiClient.execute(localVarCall);
    }

    /**
     * Завершение сессии сервера MCP с поиском инструментов (asynchronously)
     * Чтобы завершить сессию сервера MCP &#x60;timeweb-cloud-mcp-search&#x60;, отправьте DELETE-запрос на &#x60;/api/v1/mcp/search&#x60;.  Сервер не хранит сессий (не выдаёт &#x60;Mcp-Session-Id&#x60;), поэтому завершать нечего: ответ всегда 200 без тела.
     * @param _callback The callback to be executed when the API call finishes
     * @return The request call
     * @throws ApiException If fail to process the API call, e.g. serializing the request body object
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> Готово, тела нет </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Не авторизован </td><td>  -  </td></tr>
        <tr><td> 429 </td><td> Превышен лимит запросов для токена </td><td>  -  </td></tr>
     </table>
     */
    public okhttp3.Call deleteMcpSearchSessionAsync(final ApiCallback<Void> _callback) throws ApiException {

        okhttp3.Call localVarCall = deleteMcpSearchSessionValidateBeforeCall(_callback);
        localVarApiClient.executeAsync(localVarCall, _callback);
        return localVarCall;
    }
    /**
     * Build call for deleteMcpSession
     * @param _callback Callback for upload/download progress
     * @return Call to execute
     * @throws ApiException If fail to serialize the request body object
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> Готово, тела нет </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Не авторизован </td><td>  -  </td></tr>
        <tr><td> 429 </td><td> Превышен лимит запросов для токена </td><td>  -  </td></tr>
     </table>
     */
    public okhttp3.Call deleteMcpSessionCall(final ApiCallback _callback) throws ApiException {
        String basePath = null;
        // Operation Servers
        String[] localBasePaths = new String[] { "https://api.timeweb.cloud" };

        // Determine Base Path to Use
        if (localCustomBaseUrl != null){
            basePath = localCustomBaseUrl;
        } else if ( localBasePaths.length > 0 ) {
            basePath = localBasePaths[localHostIndex];
        } else {
            basePath = null;
        }

        Object localVarPostBody = null;

        // create path and map variables
        String localVarPath = "/api/v1/mcp";

        List<Pair> localVarQueryParams = new ArrayList<Pair>();
        List<Pair> localVarCollectionQueryParams = new ArrayList<Pair>();
        Map<String, String> localVarHeaderParams = new HashMap<String, String>();
        Map<String, String> localVarCookieParams = new HashMap<String, String>();
        Map<String, Object> localVarFormParams = new HashMap<String, Object>();

        final String[] localVarAccepts = {
            "application/json"
        };
        final String localVarAccept = localVarApiClient.selectHeaderAccept(localVarAccepts);
        if (localVarAccept != null) {
            localVarHeaderParams.put("Accept", localVarAccept);
        }

        final String[] localVarContentTypes = {
        };
        final String localVarContentType = localVarApiClient.selectHeaderContentType(localVarContentTypes);
        if (localVarContentType != null) {
            localVarHeaderParams.put("Content-Type", localVarContentType);
        }

        String[] localVarAuthNames = new String[] { "Bearer" };
        return localVarApiClient.buildCall(basePath, localVarPath, "DELETE", localVarQueryParams, localVarCollectionQueryParams, localVarPostBody, localVarHeaderParams, localVarCookieParams, localVarFormParams, localVarAuthNames, _callback);
    }

    @SuppressWarnings("rawtypes")
    private okhttp3.Call deleteMcpSessionValidateBeforeCall(final ApiCallback _callback) throws ApiException {
        return deleteMcpSessionCall(_callback);

    }

    /**
     * Завершение сессии сервера MCP
     * Чтобы завершить сессию сервера MCP &#x60;timeweb-cloud-mcp&#x60;, отправьте DELETE-запрос на &#x60;/api/v1/mcp&#x60;.  Сервер не хранит сессий (не выдаёт &#x60;Mcp-Session-Id&#x60;), поэтому завершать нечего: ответ всегда 200 без тела.
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> Готово, тела нет </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Не авторизован </td><td>  -  </td></tr>
        <tr><td> 429 </td><td> Превышен лимит запросов для токена </td><td>  -  </td></tr>
     </table>
     */
    public void deleteMcpSession() throws ApiException {
        deleteMcpSessionWithHttpInfo();
    }

    /**
     * Завершение сессии сервера MCP
     * Чтобы завершить сессию сервера MCP &#x60;timeweb-cloud-mcp&#x60;, отправьте DELETE-запрос на &#x60;/api/v1/mcp&#x60;.  Сервер не хранит сессий (не выдаёт &#x60;Mcp-Session-Id&#x60;), поэтому завершать нечего: ответ всегда 200 без тела.
     * @return ApiResponse&lt;Void&gt;
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> Готово, тела нет </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Не авторизован </td><td>  -  </td></tr>
        <tr><td> 429 </td><td> Превышен лимит запросов для токена </td><td>  -  </td></tr>
     </table>
     */
    public ApiResponse<Void> deleteMcpSessionWithHttpInfo() throws ApiException {
        okhttp3.Call localVarCall = deleteMcpSessionValidateBeforeCall(null);
        return localVarApiClient.execute(localVarCall);
    }

    /**
     * Завершение сессии сервера MCP (asynchronously)
     * Чтобы завершить сессию сервера MCP &#x60;timeweb-cloud-mcp&#x60;, отправьте DELETE-запрос на &#x60;/api/v1/mcp&#x60;.  Сервер не хранит сессий (не выдаёт &#x60;Mcp-Session-Id&#x60;), поэтому завершать нечего: ответ всегда 200 без тела.
     * @param _callback The callback to be executed when the API call finishes
     * @return The request call
     * @throws ApiException If fail to process the API call, e.g. serializing the request body object
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> Готово, тела нет </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Не авторизован </td><td>  -  </td></tr>
        <tr><td> 429 </td><td> Превышен лимит запросов для токена </td><td>  -  </td></tr>
     </table>
     */
    public okhttp3.Call deleteMcpSessionAsync(final ApiCallback<Void> _callback) throws ApiException {

        okhttp3.Call localVarCall = deleteMcpSessionValidateBeforeCall(_callback);
        localVarApiClient.executeAsync(localVarCall, _callback);
        return localVarCall;
    }
    /**
     * Build call for openMcpSearchStream
     * @param accept Должен содержать &#x60;text/event-stream&#x60;, иначе сервер ответит 406 (required)
     * @param mcPProtocolVersion Версия протокола, согласованная в &#x60;initialize&#x60;. Неподдерживаемое значение — ответ 400 (optional)
     * @param _callback Callback for upload/download progress
     * @return Call to execute
     * @throws ApiException If fail to serialize the request body object
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> Поток событий без событий: сервер не отправляет сообщений по своей инициативе </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Не авторизован </td><td>  -  </td></tr>
        <tr><td> 406 </td><td> Заголовок &#x60;Accept&#x60; не содержит &#x60;text/event-stream&#x60; </td><td>  -  </td></tr>
        <tr><td> 429 </td><td> Превышен лимит запросов для токена </td><td>  -  </td></tr>
     </table>
     */
    public okhttp3.Call openMcpSearchStreamCall(String accept, String mcPProtocolVersion, final ApiCallback _callback) throws ApiException {
        String basePath = null;
        // Operation Servers
        String[] localBasePaths = new String[] { "https://api.timeweb.cloud" };

        // Determine Base Path to Use
        if (localCustomBaseUrl != null){
            basePath = localCustomBaseUrl;
        } else if ( localBasePaths.length > 0 ) {
            basePath = localBasePaths[localHostIndex];
        } else {
            basePath = null;
        }

        Object localVarPostBody = null;

        // create path and map variables
        String localVarPath = "/api/v1/mcp/search";

        List<Pair> localVarQueryParams = new ArrayList<Pair>();
        List<Pair> localVarCollectionQueryParams = new ArrayList<Pair>();
        Map<String, String> localVarHeaderParams = new HashMap<String, String>();
        Map<String, String> localVarCookieParams = new HashMap<String, String>();
        Map<String, Object> localVarFormParams = new HashMap<String, Object>();

        if (accept != null) {
            localVarHeaderParams.put("Accept", localVarApiClient.parameterToString(accept));
        }

        if (mcPProtocolVersion != null) {
            localVarHeaderParams.put("MCP-Protocol-Version", localVarApiClient.parameterToString(mcPProtocolVersion));
        }

        final String[] localVarAccepts = {
            "text/event-stream",
            "application/json"
        };
        final String localVarAccept = localVarApiClient.selectHeaderAccept(localVarAccepts);
        if (localVarAccept != null) {
            localVarHeaderParams.put("Accept", localVarAccept);
        }

        final String[] localVarContentTypes = {
        };
        final String localVarContentType = localVarApiClient.selectHeaderContentType(localVarContentTypes);
        if (localVarContentType != null) {
            localVarHeaderParams.put("Content-Type", localVarContentType);
        }

        String[] localVarAuthNames = new String[] { "Bearer" };
        return localVarApiClient.buildCall(basePath, localVarPath, "GET", localVarQueryParams, localVarCollectionQueryParams, localVarPostBody, localVarHeaderParams, localVarCookieParams, localVarFormParams, localVarAuthNames, _callback);
    }

    @SuppressWarnings("rawtypes")
    private okhttp3.Call openMcpSearchStreamValidateBeforeCall(String accept, String mcPProtocolVersion, final ApiCallback _callback) throws ApiException {
        // verify the required parameter 'accept' is set
        if (accept == null) {
            throw new ApiException("Missing the required parameter 'accept' when calling openMcpSearchStream(Async)");
        }

        return openMcpSearchStreamCall(accept, mcPProtocolVersion, _callback);

    }

    /**
     * Открытие потока событий сервера MCP с поиском инструментов
     * Чтобы открыть поток сообщений, которые сервер MCP &#x60;timeweb-cloud-mcp-search&#x60; отправляет по своей инициативе, отправьте GET-запрос на &#x60;/api/v1/mcp/search&#x60;.  Сервер не отправляет сообщений по своей инициативе, поэтому поток не содержит событий и остаётся открытым, пока его не закроет шлюз. Через шлюз заголовки ответа на этот запрос могут долго не приходить. Открывать поток не нужно: работайте с сервером только POST-запросами — если клиент позволяет выбрать режим, отключите в нём GET-поток.
     * @param accept Должен содержать &#x60;text/event-stream&#x60;, иначе сервер ответит 406 (required)
     * @param mcPProtocolVersion Версия протокола, согласованная в &#x60;initialize&#x60;. Неподдерживаемое значение — ответ 400 (optional)
     * @return String
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> Поток событий без событий: сервер не отправляет сообщений по своей инициативе </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Не авторизован </td><td>  -  </td></tr>
        <tr><td> 406 </td><td> Заголовок &#x60;Accept&#x60; не содержит &#x60;text/event-stream&#x60; </td><td>  -  </td></tr>
        <tr><td> 429 </td><td> Превышен лимит запросов для токена </td><td>  -  </td></tr>
     </table>
     */
    public String openMcpSearchStream(String accept, String mcPProtocolVersion) throws ApiException {
        ApiResponse<String> localVarResp = openMcpSearchStreamWithHttpInfo(accept, mcPProtocolVersion);
        return localVarResp.getData();
    }

    /**
     * Открытие потока событий сервера MCP с поиском инструментов
     * Чтобы открыть поток сообщений, которые сервер MCP &#x60;timeweb-cloud-mcp-search&#x60; отправляет по своей инициативе, отправьте GET-запрос на &#x60;/api/v1/mcp/search&#x60;.  Сервер не отправляет сообщений по своей инициативе, поэтому поток не содержит событий и остаётся открытым, пока его не закроет шлюз. Через шлюз заголовки ответа на этот запрос могут долго не приходить. Открывать поток не нужно: работайте с сервером только POST-запросами — если клиент позволяет выбрать режим, отключите в нём GET-поток.
     * @param accept Должен содержать &#x60;text/event-stream&#x60;, иначе сервер ответит 406 (required)
     * @param mcPProtocolVersion Версия протокола, согласованная в &#x60;initialize&#x60;. Неподдерживаемое значение — ответ 400 (optional)
     * @return ApiResponse&lt;String&gt;
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> Поток событий без событий: сервер не отправляет сообщений по своей инициативе </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Не авторизован </td><td>  -  </td></tr>
        <tr><td> 406 </td><td> Заголовок &#x60;Accept&#x60; не содержит &#x60;text/event-stream&#x60; </td><td>  -  </td></tr>
        <tr><td> 429 </td><td> Превышен лимит запросов для токена </td><td>  -  </td></tr>
     </table>
     */
    public ApiResponse<String> openMcpSearchStreamWithHttpInfo(String accept, String mcPProtocolVersion) throws ApiException {
        okhttp3.Call localVarCall = openMcpSearchStreamValidateBeforeCall(accept, mcPProtocolVersion, null);
        Type localVarReturnType = new TypeToken<String>(){}.getType();
        return localVarApiClient.execute(localVarCall, localVarReturnType);
    }

    /**
     * Открытие потока событий сервера MCP с поиском инструментов (asynchronously)
     * Чтобы открыть поток сообщений, которые сервер MCP &#x60;timeweb-cloud-mcp-search&#x60; отправляет по своей инициативе, отправьте GET-запрос на &#x60;/api/v1/mcp/search&#x60;.  Сервер не отправляет сообщений по своей инициативе, поэтому поток не содержит событий и остаётся открытым, пока его не закроет шлюз. Через шлюз заголовки ответа на этот запрос могут долго не приходить. Открывать поток не нужно: работайте с сервером только POST-запросами — если клиент позволяет выбрать режим, отключите в нём GET-поток.
     * @param accept Должен содержать &#x60;text/event-stream&#x60;, иначе сервер ответит 406 (required)
     * @param mcPProtocolVersion Версия протокола, согласованная в &#x60;initialize&#x60;. Неподдерживаемое значение — ответ 400 (optional)
     * @param _callback The callback to be executed when the API call finishes
     * @return The request call
     * @throws ApiException If fail to process the API call, e.g. serializing the request body object
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> Поток событий без событий: сервер не отправляет сообщений по своей инициативе </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Не авторизован </td><td>  -  </td></tr>
        <tr><td> 406 </td><td> Заголовок &#x60;Accept&#x60; не содержит &#x60;text/event-stream&#x60; </td><td>  -  </td></tr>
        <tr><td> 429 </td><td> Превышен лимит запросов для токена </td><td>  -  </td></tr>
     </table>
     */
    public okhttp3.Call openMcpSearchStreamAsync(String accept, String mcPProtocolVersion, final ApiCallback<String> _callback) throws ApiException {

        okhttp3.Call localVarCall = openMcpSearchStreamValidateBeforeCall(accept, mcPProtocolVersion, _callback);
        Type localVarReturnType = new TypeToken<String>(){}.getType();
        localVarApiClient.executeAsync(localVarCall, localVarReturnType, _callback);
        return localVarCall;
    }
    /**
     * Build call for openMcpStream
     * @param accept Должен содержать &#x60;text/event-stream&#x60;, иначе сервер ответит 406 (required)
     * @param mcPProtocolVersion Версия протокола, согласованная в &#x60;initialize&#x60;. Неподдерживаемое значение — ответ 400 (optional)
     * @param _callback Callback for upload/download progress
     * @return Call to execute
     * @throws ApiException If fail to serialize the request body object
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> Поток событий без событий: сервер не отправляет сообщений по своей инициативе </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Не авторизован </td><td>  -  </td></tr>
        <tr><td> 406 </td><td> Заголовок &#x60;Accept&#x60; не содержит &#x60;text/event-stream&#x60; </td><td>  -  </td></tr>
        <tr><td> 429 </td><td> Превышен лимит запросов для токена </td><td>  -  </td></tr>
     </table>
     */
    public okhttp3.Call openMcpStreamCall(String accept, String mcPProtocolVersion, final ApiCallback _callback) throws ApiException {
        String basePath = null;
        // Operation Servers
        String[] localBasePaths = new String[] { "https://api.timeweb.cloud" };

        // Determine Base Path to Use
        if (localCustomBaseUrl != null){
            basePath = localCustomBaseUrl;
        } else if ( localBasePaths.length > 0 ) {
            basePath = localBasePaths[localHostIndex];
        } else {
            basePath = null;
        }

        Object localVarPostBody = null;

        // create path and map variables
        String localVarPath = "/api/v1/mcp";

        List<Pair> localVarQueryParams = new ArrayList<Pair>();
        List<Pair> localVarCollectionQueryParams = new ArrayList<Pair>();
        Map<String, String> localVarHeaderParams = new HashMap<String, String>();
        Map<String, String> localVarCookieParams = new HashMap<String, String>();
        Map<String, Object> localVarFormParams = new HashMap<String, Object>();

        if (accept != null) {
            localVarHeaderParams.put("Accept", localVarApiClient.parameterToString(accept));
        }

        if (mcPProtocolVersion != null) {
            localVarHeaderParams.put("MCP-Protocol-Version", localVarApiClient.parameterToString(mcPProtocolVersion));
        }

        final String[] localVarAccepts = {
            "text/event-stream",
            "application/json"
        };
        final String localVarAccept = localVarApiClient.selectHeaderAccept(localVarAccepts);
        if (localVarAccept != null) {
            localVarHeaderParams.put("Accept", localVarAccept);
        }

        final String[] localVarContentTypes = {
        };
        final String localVarContentType = localVarApiClient.selectHeaderContentType(localVarContentTypes);
        if (localVarContentType != null) {
            localVarHeaderParams.put("Content-Type", localVarContentType);
        }

        String[] localVarAuthNames = new String[] { "Bearer" };
        return localVarApiClient.buildCall(basePath, localVarPath, "GET", localVarQueryParams, localVarCollectionQueryParams, localVarPostBody, localVarHeaderParams, localVarCookieParams, localVarFormParams, localVarAuthNames, _callback);
    }

    @SuppressWarnings("rawtypes")
    private okhttp3.Call openMcpStreamValidateBeforeCall(String accept, String mcPProtocolVersion, final ApiCallback _callback) throws ApiException {
        // verify the required parameter 'accept' is set
        if (accept == null) {
            throw new ApiException("Missing the required parameter 'accept' when calling openMcpStream(Async)");
        }

        return openMcpStreamCall(accept, mcPProtocolVersion, _callback);

    }

    /**
     * Открытие потока событий сервера MCP
     * Чтобы открыть поток сообщений, которые сервер MCP &#x60;timeweb-cloud-mcp&#x60; отправляет по своей инициативе, отправьте GET-запрос на &#x60;/api/v1/mcp&#x60;.  Сервер не отправляет сообщений по своей инициативе, поэтому поток не содержит событий и остаётся открытым, пока его не закроет шлюз. Через шлюз заголовки ответа на этот запрос могут долго не приходить. Открывать поток не нужно: работайте с сервером только POST-запросами — если клиент позволяет выбрать режим, отключите в нём GET-поток.
     * @param accept Должен содержать &#x60;text/event-stream&#x60;, иначе сервер ответит 406 (required)
     * @param mcPProtocolVersion Версия протокола, согласованная в &#x60;initialize&#x60;. Неподдерживаемое значение — ответ 400 (optional)
     * @return String
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> Поток событий без событий: сервер не отправляет сообщений по своей инициативе </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Не авторизован </td><td>  -  </td></tr>
        <tr><td> 406 </td><td> Заголовок &#x60;Accept&#x60; не содержит &#x60;text/event-stream&#x60; </td><td>  -  </td></tr>
        <tr><td> 429 </td><td> Превышен лимит запросов для токена </td><td>  -  </td></tr>
     </table>
     */
    public String openMcpStream(String accept, String mcPProtocolVersion) throws ApiException {
        ApiResponse<String> localVarResp = openMcpStreamWithHttpInfo(accept, mcPProtocolVersion);
        return localVarResp.getData();
    }

    /**
     * Открытие потока событий сервера MCP
     * Чтобы открыть поток сообщений, которые сервер MCP &#x60;timeweb-cloud-mcp&#x60; отправляет по своей инициативе, отправьте GET-запрос на &#x60;/api/v1/mcp&#x60;.  Сервер не отправляет сообщений по своей инициативе, поэтому поток не содержит событий и остаётся открытым, пока его не закроет шлюз. Через шлюз заголовки ответа на этот запрос могут долго не приходить. Открывать поток не нужно: работайте с сервером только POST-запросами — если клиент позволяет выбрать режим, отключите в нём GET-поток.
     * @param accept Должен содержать &#x60;text/event-stream&#x60;, иначе сервер ответит 406 (required)
     * @param mcPProtocolVersion Версия протокола, согласованная в &#x60;initialize&#x60;. Неподдерживаемое значение — ответ 400 (optional)
     * @return ApiResponse&lt;String&gt;
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> Поток событий без событий: сервер не отправляет сообщений по своей инициативе </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Не авторизован </td><td>  -  </td></tr>
        <tr><td> 406 </td><td> Заголовок &#x60;Accept&#x60; не содержит &#x60;text/event-stream&#x60; </td><td>  -  </td></tr>
        <tr><td> 429 </td><td> Превышен лимит запросов для токена </td><td>  -  </td></tr>
     </table>
     */
    public ApiResponse<String> openMcpStreamWithHttpInfo(String accept, String mcPProtocolVersion) throws ApiException {
        okhttp3.Call localVarCall = openMcpStreamValidateBeforeCall(accept, mcPProtocolVersion, null);
        Type localVarReturnType = new TypeToken<String>(){}.getType();
        return localVarApiClient.execute(localVarCall, localVarReturnType);
    }

    /**
     * Открытие потока событий сервера MCP (asynchronously)
     * Чтобы открыть поток сообщений, которые сервер MCP &#x60;timeweb-cloud-mcp&#x60; отправляет по своей инициативе, отправьте GET-запрос на &#x60;/api/v1/mcp&#x60;.  Сервер не отправляет сообщений по своей инициативе, поэтому поток не содержит событий и остаётся открытым, пока его не закроет шлюз. Через шлюз заголовки ответа на этот запрос могут долго не приходить. Открывать поток не нужно: работайте с сервером только POST-запросами — если клиент позволяет выбрать режим, отключите в нём GET-поток.
     * @param accept Должен содержать &#x60;text/event-stream&#x60;, иначе сервер ответит 406 (required)
     * @param mcPProtocolVersion Версия протокола, согласованная в &#x60;initialize&#x60;. Неподдерживаемое значение — ответ 400 (optional)
     * @param _callback The callback to be executed when the API call finishes
     * @return The request call
     * @throws ApiException If fail to process the API call, e.g. serializing the request body object
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> Поток событий без событий: сервер не отправляет сообщений по своей инициативе </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Не авторизован </td><td>  -  </td></tr>
        <tr><td> 406 </td><td> Заголовок &#x60;Accept&#x60; не содержит &#x60;text/event-stream&#x60; </td><td>  -  </td></tr>
        <tr><td> 429 </td><td> Превышен лимит запросов для токена </td><td>  -  </td></tr>
     </table>
     */
    public okhttp3.Call openMcpStreamAsync(String accept, String mcPProtocolVersion, final ApiCallback<String> _callback) throws ApiException {

        okhttp3.Call localVarCall = openMcpStreamValidateBeforeCall(accept, mcPProtocolVersion, _callback);
        Type localVarReturnType = new TypeToken<String>(){}.getType();
        localVarApiClient.executeAsync(localVarCall, localVarReturnType, _callback);
        return localVarCall;
    }
    /**
     * Build call for sendMcpMessage
     * @param accept Должен содержать и &#x60;application/json&#x60;, и &#x60;text/event-stream&#x60;, иначе сервер ответит 406 (required)
     * @param jsonRpcRequest  (required)
     * @param mcPProtocolVersion Версия протокола, согласованная в &#x60;initialize&#x60;. Неподдерживаемое значение — ответ 400 (optional)
     * @param xMcpConfirmationUi Как &#x60;request_user_confirmation&#x60; спрашивает пользователя. &#x60;text&#x60; (по умолчанию) — пользователь отвечает в чате, на &#x60;/api/v1/mcp&#x60; инструмент в этом режиме не выводится. &#x60;buttons&#x60; — инструмент возвращает &#x60;ui_action&#x60; с кнопками для клиентов, которые умеют их показывать. Регистр не важен, другие значения игнорируются (optional)
     * @param _callback Callback for upload/download progress
     * @return Call to execute
     * @throws ApiException If fail to serialize the request body object
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> Ответ на запрос: поток &#x60;text/event-stream&#x60; с одним событием &#x60;message&#x60;. В &#x60;data&#x60; события — ответ JSON-RPC (схема и примеры ниже): успешный или с ошибкой JSON-RPC, например &#x60;-32601&#x60; для неизвестного метода. Пример потока:  &#x60;&#x60;&#x60; event: message data: {\&quot;jsonrpc\&quot;:\&quot;2.0\&quot;,\&quot;id\&quot;:1,\&quot;result\&quot;:{\&quot;tools\&quot;:[...]}} &#x60;&#x60;&#x60; </td><td>  -  </td></tr>
        <tr><td> 202 </td><td> Уведомление принято, тела нет </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Тело не является корректным JSON или сообщением JSON-RPC (&#x60;-32700&#x60;), заголовок &#x60;MCP-Protocol-Version&#x60; содержит неподдерживаемую версию (&#x60;-32000&#x60;) </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Не авторизован </td><td>  -  </td></tr>
        <tr><td> 406 </td><td> Заголовок &#x60;Accept&#x60; не содержит &#x60;application/json&#x60; или &#x60;text/event-stream&#x60; </td><td>  -  </td></tr>
        <tr><td> 415 </td><td> &#x60;Content-Type&#x60; не &#x60;application/json&#x60; </td><td>  -  </td></tr>
        <tr><td> 429 </td><td> Превышен лимит запросов для токена </td><td>  -  </td></tr>
     </table>
     */
    public okhttp3.Call sendMcpMessageCall(String accept, JsonRpcRequest jsonRpcRequest, String mcPProtocolVersion, String xMcpConfirmationUi, final ApiCallback _callback) throws ApiException {
        String basePath = null;
        // Operation Servers
        String[] localBasePaths = new String[] { "https://api.timeweb.cloud" };

        // Determine Base Path to Use
        if (localCustomBaseUrl != null){
            basePath = localCustomBaseUrl;
        } else if ( localBasePaths.length > 0 ) {
            basePath = localBasePaths[localHostIndex];
        } else {
            basePath = null;
        }

        Object localVarPostBody = jsonRpcRequest;

        // create path and map variables
        String localVarPath = "/api/v1/mcp";

        List<Pair> localVarQueryParams = new ArrayList<Pair>();
        List<Pair> localVarCollectionQueryParams = new ArrayList<Pair>();
        Map<String, String> localVarHeaderParams = new HashMap<String, String>();
        Map<String, String> localVarCookieParams = new HashMap<String, String>();
        Map<String, Object> localVarFormParams = new HashMap<String, Object>();

        if (accept != null) {
            localVarHeaderParams.put("Accept", localVarApiClient.parameterToString(accept));
        }

        if (mcPProtocolVersion != null) {
            localVarHeaderParams.put("MCP-Protocol-Version", localVarApiClient.parameterToString(mcPProtocolVersion));
        }

        if (xMcpConfirmationUi != null) {
            localVarHeaderParams.put("x-mcp-confirmation-ui", localVarApiClient.parameterToString(xMcpConfirmationUi));
        }

        final String[] localVarAccepts = {
            "text/event-stream",
            "application/json"
        };
        final String localVarAccept = localVarApiClient.selectHeaderAccept(localVarAccepts);
        if (localVarAccept != null) {
            localVarHeaderParams.put("Accept", localVarAccept);
        }

        final String[] localVarContentTypes = {
            "application/json"
        };
        final String localVarContentType = localVarApiClient.selectHeaderContentType(localVarContentTypes);
        if (localVarContentType != null) {
            localVarHeaderParams.put("Content-Type", localVarContentType);
        }

        String[] localVarAuthNames = new String[] { "Bearer" };
        return localVarApiClient.buildCall(basePath, localVarPath, "POST", localVarQueryParams, localVarCollectionQueryParams, localVarPostBody, localVarHeaderParams, localVarCookieParams, localVarFormParams, localVarAuthNames, _callback);
    }

    @SuppressWarnings("rawtypes")
    private okhttp3.Call sendMcpMessageValidateBeforeCall(String accept, JsonRpcRequest jsonRpcRequest, String mcPProtocolVersion, String xMcpConfirmationUi, final ApiCallback _callback) throws ApiException {
        // verify the required parameter 'accept' is set
        if (accept == null) {
            throw new ApiException("Missing the required parameter 'accept' when calling sendMcpMessage(Async)");
        }

        // verify the required parameter 'jsonRpcRequest' is set
        if (jsonRpcRequest == null) {
            throw new ApiException("Missing the required parameter 'jsonRpcRequest' when calling sendMcpMessage(Async)");
        }

        return sendMcpMessageCall(accept, jsonRpcRequest, mcPProtocolVersion, xMcpConfirmationUi, _callback);

    }

    /**
     * Отправка JSON-RPC-сообщения серверу MCP
     * Чтобы отправить запрос или уведомление серверу MCP &#x60;timeweb-cloud-mcp&#x60;, отправьте POST-запрос на &#x60;/api/v1/mcp&#x60; с сообщением JSON-RPC 2.0 в теле.  Через этот метод идут все запросы MCP: &#x60;initialize&#x60;, &#x60;tools/list&#x60; (все инструменты сразу), &#x60;tools/call&#x60;, &#x60;resources/list&#x60;, &#x60;resources/read&#x60;, &#x60;ping&#x60;. Ответ на запрос приходит потоком &#x60;text/event-stream&#x60; с одним событием &#x60;message&#x60;, уведомление принимается с кодом 202 без тела. Изменяющие инструменты выполняются в два вызова с &#x60;confirm_token&#x60; — см. описание раздела.  Заголовки &#x60;x-tool-access&#x60; и &#x60;x-allowed-tools&#x60; здесь не действуют — используйте &#x60;/api/v1/mcp/search&#x60;.
     * @param accept Должен содержать и &#x60;application/json&#x60;, и &#x60;text/event-stream&#x60;, иначе сервер ответит 406 (required)
     * @param jsonRpcRequest  (required)
     * @param mcPProtocolVersion Версия протокола, согласованная в &#x60;initialize&#x60;. Неподдерживаемое значение — ответ 400 (optional)
     * @param xMcpConfirmationUi Как &#x60;request_user_confirmation&#x60; спрашивает пользователя. &#x60;text&#x60; (по умолчанию) — пользователь отвечает в чате, на &#x60;/api/v1/mcp&#x60; инструмент в этом режиме не выводится. &#x60;buttons&#x60; — инструмент возвращает &#x60;ui_action&#x60; с кнопками для клиентов, которые умеют их показывать. Регистр не важен, другие значения игнорируются (optional)
     * @return JsonRpcResultResponse
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> Ответ на запрос: поток &#x60;text/event-stream&#x60; с одним событием &#x60;message&#x60;. В &#x60;data&#x60; события — ответ JSON-RPC (схема и примеры ниже): успешный или с ошибкой JSON-RPC, например &#x60;-32601&#x60; для неизвестного метода. Пример потока:  &#x60;&#x60;&#x60; event: message data: {\&quot;jsonrpc\&quot;:\&quot;2.0\&quot;,\&quot;id\&quot;:1,\&quot;result\&quot;:{\&quot;tools\&quot;:[...]}} &#x60;&#x60;&#x60; </td><td>  -  </td></tr>
        <tr><td> 202 </td><td> Уведомление принято, тела нет </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Тело не является корректным JSON или сообщением JSON-RPC (&#x60;-32700&#x60;), заголовок &#x60;MCP-Protocol-Version&#x60; содержит неподдерживаемую версию (&#x60;-32000&#x60;) </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Не авторизован </td><td>  -  </td></tr>
        <tr><td> 406 </td><td> Заголовок &#x60;Accept&#x60; не содержит &#x60;application/json&#x60; или &#x60;text/event-stream&#x60; </td><td>  -  </td></tr>
        <tr><td> 415 </td><td> &#x60;Content-Type&#x60; не &#x60;application/json&#x60; </td><td>  -  </td></tr>
        <tr><td> 429 </td><td> Превышен лимит запросов для токена </td><td>  -  </td></tr>
     </table>
     */
    public JsonRpcResultResponse sendMcpMessage(String accept, JsonRpcRequest jsonRpcRequest, String mcPProtocolVersion, String xMcpConfirmationUi) throws ApiException {
        ApiResponse<JsonRpcResultResponse> localVarResp = sendMcpMessageWithHttpInfo(accept, jsonRpcRequest, mcPProtocolVersion, xMcpConfirmationUi);
        return localVarResp.getData();
    }

    /**
     * Отправка JSON-RPC-сообщения серверу MCP
     * Чтобы отправить запрос или уведомление серверу MCP &#x60;timeweb-cloud-mcp&#x60;, отправьте POST-запрос на &#x60;/api/v1/mcp&#x60; с сообщением JSON-RPC 2.0 в теле.  Через этот метод идут все запросы MCP: &#x60;initialize&#x60;, &#x60;tools/list&#x60; (все инструменты сразу), &#x60;tools/call&#x60;, &#x60;resources/list&#x60;, &#x60;resources/read&#x60;, &#x60;ping&#x60;. Ответ на запрос приходит потоком &#x60;text/event-stream&#x60; с одним событием &#x60;message&#x60;, уведомление принимается с кодом 202 без тела. Изменяющие инструменты выполняются в два вызова с &#x60;confirm_token&#x60; — см. описание раздела.  Заголовки &#x60;x-tool-access&#x60; и &#x60;x-allowed-tools&#x60; здесь не действуют — используйте &#x60;/api/v1/mcp/search&#x60;.
     * @param accept Должен содержать и &#x60;application/json&#x60;, и &#x60;text/event-stream&#x60;, иначе сервер ответит 406 (required)
     * @param jsonRpcRequest  (required)
     * @param mcPProtocolVersion Версия протокола, согласованная в &#x60;initialize&#x60;. Неподдерживаемое значение — ответ 400 (optional)
     * @param xMcpConfirmationUi Как &#x60;request_user_confirmation&#x60; спрашивает пользователя. &#x60;text&#x60; (по умолчанию) — пользователь отвечает в чате, на &#x60;/api/v1/mcp&#x60; инструмент в этом режиме не выводится. &#x60;buttons&#x60; — инструмент возвращает &#x60;ui_action&#x60; с кнопками для клиентов, которые умеют их показывать. Регистр не важен, другие значения игнорируются (optional)
     * @return ApiResponse&lt;JsonRpcResultResponse&gt;
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> Ответ на запрос: поток &#x60;text/event-stream&#x60; с одним событием &#x60;message&#x60;. В &#x60;data&#x60; события — ответ JSON-RPC (схема и примеры ниже): успешный или с ошибкой JSON-RPC, например &#x60;-32601&#x60; для неизвестного метода. Пример потока:  &#x60;&#x60;&#x60; event: message data: {\&quot;jsonrpc\&quot;:\&quot;2.0\&quot;,\&quot;id\&quot;:1,\&quot;result\&quot;:{\&quot;tools\&quot;:[...]}} &#x60;&#x60;&#x60; </td><td>  -  </td></tr>
        <tr><td> 202 </td><td> Уведомление принято, тела нет </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Тело не является корректным JSON или сообщением JSON-RPC (&#x60;-32700&#x60;), заголовок &#x60;MCP-Protocol-Version&#x60; содержит неподдерживаемую версию (&#x60;-32000&#x60;) </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Не авторизован </td><td>  -  </td></tr>
        <tr><td> 406 </td><td> Заголовок &#x60;Accept&#x60; не содержит &#x60;application/json&#x60; или &#x60;text/event-stream&#x60; </td><td>  -  </td></tr>
        <tr><td> 415 </td><td> &#x60;Content-Type&#x60; не &#x60;application/json&#x60; </td><td>  -  </td></tr>
        <tr><td> 429 </td><td> Превышен лимит запросов для токена </td><td>  -  </td></tr>
     </table>
     */
    public ApiResponse<JsonRpcResultResponse> sendMcpMessageWithHttpInfo(String accept, JsonRpcRequest jsonRpcRequest, String mcPProtocolVersion, String xMcpConfirmationUi) throws ApiException {
        okhttp3.Call localVarCall = sendMcpMessageValidateBeforeCall(accept, jsonRpcRequest, mcPProtocolVersion, xMcpConfirmationUi, null);
        Type localVarReturnType = new TypeToken<JsonRpcResultResponse>(){}.getType();
        return localVarApiClient.execute(localVarCall, localVarReturnType);
    }

    /**
     * Отправка JSON-RPC-сообщения серверу MCP (asynchronously)
     * Чтобы отправить запрос или уведомление серверу MCP &#x60;timeweb-cloud-mcp&#x60;, отправьте POST-запрос на &#x60;/api/v1/mcp&#x60; с сообщением JSON-RPC 2.0 в теле.  Через этот метод идут все запросы MCP: &#x60;initialize&#x60;, &#x60;tools/list&#x60; (все инструменты сразу), &#x60;tools/call&#x60;, &#x60;resources/list&#x60;, &#x60;resources/read&#x60;, &#x60;ping&#x60;. Ответ на запрос приходит потоком &#x60;text/event-stream&#x60; с одним событием &#x60;message&#x60;, уведомление принимается с кодом 202 без тела. Изменяющие инструменты выполняются в два вызова с &#x60;confirm_token&#x60; — см. описание раздела.  Заголовки &#x60;x-tool-access&#x60; и &#x60;x-allowed-tools&#x60; здесь не действуют — используйте &#x60;/api/v1/mcp/search&#x60;.
     * @param accept Должен содержать и &#x60;application/json&#x60;, и &#x60;text/event-stream&#x60;, иначе сервер ответит 406 (required)
     * @param jsonRpcRequest  (required)
     * @param mcPProtocolVersion Версия протокола, согласованная в &#x60;initialize&#x60;. Неподдерживаемое значение — ответ 400 (optional)
     * @param xMcpConfirmationUi Как &#x60;request_user_confirmation&#x60; спрашивает пользователя. &#x60;text&#x60; (по умолчанию) — пользователь отвечает в чате, на &#x60;/api/v1/mcp&#x60; инструмент в этом режиме не выводится. &#x60;buttons&#x60; — инструмент возвращает &#x60;ui_action&#x60; с кнопками для клиентов, которые умеют их показывать. Регистр не важен, другие значения игнорируются (optional)
     * @param _callback The callback to be executed when the API call finishes
     * @return The request call
     * @throws ApiException If fail to process the API call, e.g. serializing the request body object
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> Ответ на запрос: поток &#x60;text/event-stream&#x60; с одним событием &#x60;message&#x60;. В &#x60;data&#x60; события — ответ JSON-RPC (схема и примеры ниже): успешный или с ошибкой JSON-RPC, например &#x60;-32601&#x60; для неизвестного метода. Пример потока:  &#x60;&#x60;&#x60; event: message data: {\&quot;jsonrpc\&quot;:\&quot;2.0\&quot;,\&quot;id\&quot;:1,\&quot;result\&quot;:{\&quot;tools\&quot;:[...]}} &#x60;&#x60;&#x60; </td><td>  -  </td></tr>
        <tr><td> 202 </td><td> Уведомление принято, тела нет </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Тело не является корректным JSON или сообщением JSON-RPC (&#x60;-32700&#x60;), заголовок &#x60;MCP-Protocol-Version&#x60; содержит неподдерживаемую версию (&#x60;-32000&#x60;) </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Не авторизован </td><td>  -  </td></tr>
        <tr><td> 406 </td><td> Заголовок &#x60;Accept&#x60; не содержит &#x60;application/json&#x60; или &#x60;text/event-stream&#x60; </td><td>  -  </td></tr>
        <tr><td> 415 </td><td> &#x60;Content-Type&#x60; не &#x60;application/json&#x60; </td><td>  -  </td></tr>
        <tr><td> 429 </td><td> Превышен лимит запросов для токена </td><td>  -  </td></tr>
     </table>
     */
    public okhttp3.Call sendMcpMessageAsync(String accept, JsonRpcRequest jsonRpcRequest, String mcPProtocolVersion, String xMcpConfirmationUi, final ApiCallback<JsonRpcResultResponse> _callback) throws ApiException {

        okhttp3.Call localVarCall = sendMcpMessageValidateBeforeCall(accept, jsonRpcRequest, mcPProtocolVersion, xMcpConfirmationUi, _callback);
        Type localVarReturnType = new TypeToken<JsonRpcResultResponse>(){}.getType();
        localVarApiClient.executeAsync(localVarCall, localVarReturnType, _callback);
        return localVarCall;
    }
    /**
     * Build call for sendMcpSearchMessage
     * @param accept Должен содержать и &#x60;application/json&#x60;, и &#x60;text/event-stream&#x60;, иначе сервер ответит 406 (required)
     * @param jsonRpcRequest  (required)
     * @param mcPProtocolVersion Версия протокола, согласованная в &#x60;initialize&#x60;. Неподдерживаемое значение — ответ 400 (optional)
     * @param xToolAccess Только операции чтения: поиск и определения пропускают изменяющие инструменты, а &#x60;execute_tool&#x60; отказывает с &#x60;tool_not_allowed&#x60;. Единственное допустимое значение — &#x60;read-only&#x60; (регистр не важен); любое другое, в том числе пустое, — ответ 400 с ошибкой &#x60;-32600&#x60; (optional)
     * @param xAllowedTools Имена разрешённых операций через запятую — все остальные недоступны. Неизвестные имена игнорируются, пустое значение не разрешает ничего. Вместе с &#x60;x-tool-access&#x60; операция должна пройти оба ограничения (optional)
     * @param xMcpConfirmationUi Как &#x60;request_user_confirmation&#x60; спрашивает пользователя. &#x60;text&#x60; (по умолчанию) — пользователь отвечает в чате, на &#x60;/api/v1/mcp&#x60; инструмент в этом режиме не выводится. &#x60;buttons&#x60; — инструмент возвращает &#x60;ui_action&#x60; с кнопками для клиентов, которые умеют их показывать. Регистр не важен, другие значения игнорируются (optional)
     * @param _callback Callback for upload/download progress
     * @return Call to execute
     * @throws ApiException If fail to serialize the request body object
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> Ответ на запрос: поток &#x60;text/event-stream&#x60; с одним событием &#x60;message&#x60;. В &#x60;data&#x60; события — ответ JSON-RPC (схема и примеры ниже): успешный или с ошибкой JSON-RPC, например &#x60;-32601&#x60; для неизвестного метода. Пример потока:  &#x60;&#x60;&#x60; event: message data: {\&quot;jsonrpc\&quot;:\&quot;2.0\&quot;,\&quot;id\&quot;:1,\&quot;result\&quot;:{\&quot;tools\&quot;:[...]}} &#x60;&#x60;&#x60; </td><td>  -  </td></tr>
        <tr><td> 202 </td><td> Уведомление принято, тела нет </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Тело не является корректным JSON или сообщением JSON-RPC (&#x60;-32700&#x60;), заголовок &#x60;MCP-Protocol-Version&#x60; содержит неподдерживаемую версию (&#x60;-32000&#x60;) или недопустимое значение &#x60;x-tool-access&#x60; (&#x60;-32600&#x60;) </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Не авторизован </td><td>  -  </td></tr>
        <tr><td> 406 </td><td> Заголовок &#x60;Accept&#x60; не содержит &#x60;application/json&#x60; или &#x60;text/event-stream&#x60; </td><td>  -  </td></tr>
        <tr><td> 415 </td><td> &#x60;Content-Type&#x60; не &#x60;application/json&#x60; </td><td>  -  </td></tr>
        <tr><td> 429 </td><td> Превышен лимит запросов для токена </td><td>  -  </td></tr>
     </table>
     */
    public okhttp3.Call sendMcpSearchMessageCall(String accept, JsonRpcRequest jsonRpcRequest, String mcPProtocolVersion, String xToolAccess, String xAllowedTools, String xMcpConfirmationUi, final ApiCallback _callback) throws ApiException {
        String basePath = null;
        // Operation Servers
        String[] localBasePaths = new String[] { "https://api.timeweb.cloud" };

        // Determine Base Path to Use
        if (localCustomBaseUrl != null){
            basePath = localCustomBaseUrl;
        } else if ( localBasePaths.length > 0 ) {
            basePath = localBasePaths[localHostIndex];
        } else {
            basePath = null;
        }

        Object localVarPostBody = jsonRpcRequest;

        // create path and map variables
        String localVarPath = "/api/v1/mcp/search";

        List<Pair> localVarQueryParams = new ArrayList<Pair>();
        List<Pair> localVarCollectionQueryParams = new ArrayList<Pair>();
        Map<String, String> localVarHeaderParams = new HashMap<String, String>();
        Map<String, String> localVarCookieParams = new HashMap<String, String>();
        Map<String, Object> localVarFormParams = new HashMap<String, Object>();

        if (accept != null) {
            localVarHeaderParams.put("Accept", localVarApiClient.parameterToString(accept));
        }

        if (mcPProtocolVersion != null) {
            localVarHeaderParams.put("MCP-Protocol-Version", localVarApiClient.parameterToString(mcPProtocolVersion));
        }

        if (xToolAccess != null) {
            localVarHeaderParams.put("x-tool-access", localVarApiClient.parameterToString(xToolAccess));
        }

        if (xAllowedTools != null) {
            localVarHeaderParams.put("x-allowed-tools", localVarApiClient.parameterToString(xAllowedTools));
        }

        if (xMcpConfirmationUi != null) {
            localVarHeaderParams.put("x-mcp-confirmation-ui", localVarApiClient.parameterToString(xMcpConfirmationUi));
        }

        final String[] localVarAccepts = {
            "text/event-stream",
            "application/json"
        };
        final String localVarAccept = localVarApiClient.selectHeaderAccept(localVarAccepts);
        if (localVarAccept != null) {
            localVarHeaderParams.put("Accept", localVarAccept);
        }

        final String[] localVarContentTypes = {
            "application/json"
        };
        final String localVarContentType = localVarApiClient.selectHeaderContentType(localVarContentTypes);
        if (localVarContentType != null) {
            localVarHeaderParams.put("Content-Type", localVarContentType);
        }

        String[] localVarAuthNames = new String[] { "Bearer" };
        return localVarApiClient.buildCall(basePath, localVarPath, "POST", localVarQueryParams, localVarCollectionQueryParams, localVarPostBody, localVarHeaderParams, localVarCookieParams, localVarFormParams, localVarAuthNames, _callback);
    }

    @SuppressWarnings("rawtypes")
    private okhttp3.Call sendMcpSearchMessageValidateBeforeCall(String accept, JsonRpcRequest jsonRpcRequest, String mcPProtocolVersion, String xToolAccess, String xAllowedTools, String xMcpConfirmationUi, final ApiCallback _callback) throws ApiException {
        // verify the required parameter 'accept' is set
        if (accept == null) {
            throw new ApiException("Missing the required parameter 'accept' when calling sendMcpSearchMessage(Async)");
        }

        // verify the required parameter 'jsonRpcRequest' is set
        if (jsonRpcRequest == null) {
            throw new ApiException("Missing the required parameter 'jsonRpcRequest' when calling sendMcpSearchMessage(Async)");
        }

        return sendMcpSearchMessageCall(accept, jsonRpcRequest, mcPProtocolVersion, xToolAccess, xAllowedTools, xMcpConfirmationUi, _callback);

    }

    /**
     * Отправка JSON-RPC-сообщения серверу MCP с поиском инструментов
     * Чтобы отправить запрос или уведомление серверу MCP &#x60;timeweb-cloud-mcp-search&#x60;, отправьте POST-запрос на &#x60;/api/v1/mcp/search&#x60; с сообщением JSON-RPC 2.0 в теле.  &#x60;tools/list&#x60; возвращает четыре инструмента: &#x60;search_tools&#x60;, &#x60;get_tool_definition&#x60;, &#x60;execute_tool&#x60; и &#x60;request_user_confirmation&#x60;. Все операции облака находятся через &#x60;search_tools&#x60; и выполняются через &#x60;execute_tool&#x60;; изменяющие операции — в два вызова &#x60;execute_tool&#x60; с &#x60;confirm_token&#x60; внутри &#x60;arguments&#x60;.  Доступ модели можно сузить заголовками &#x60;x-tool-access&#x60; и &#x60;x-allowed-tools&#x60;. Недопустимое значение &#x60;x-tool-access&#x60; — ответ 400 с ошибкой &#x60;-32600&#x60;.
     * @param accept Должен содержать и &#x60;application/json&#x60;, и &#x60;text/event-stream&#x60;, иначе сервер ответит 406 (required)
     * @param jsonRpcRequest  (required)
     * @param mcPProtocolVersion Версия протокола, согласованная в &#x60;initialize&#x60;. Неподдерживаемое значение — ответ 400 (optional)
     * @param xToolAccess Только операции чтения: поиск и определения пропускают изменяющие инструменты, а &#x60;execute_tool&#x60; отказывает с &#x60;tool_not_allowed&#x60;. Единственное допустимое значение — &#x60;read-only&#x60; (регистр не важен); любое другое, в том числе пустое, — ответ 400 с ошибкой &#x60;-32600&#x60; (optional)
     * @param xAllowedTools Имена разрешённых операций через запятую — все остальные недоступны. Неизвестные имена игнорируются, пустое значение не разрешает ничего. Вместе с &#x60;x-tool-access&#x60; операция должна пройти оба ограничения (optional)
     * @param xMcpConfirmationUi Как &#x60;request_user_confirmation&#x60; спрашивает пользователя. &#x60;text&#x60; (по умолчанию) — пользователь отвечает в чате, на &#x60;/api/v1/mcp&#x60; инструмент в этом режиме не выводится. &#x60;buttons&#x60; — инструмент возвращает &#x60;ui_action&#x60; с кнопками для клиентов, которые умеют их показывать. Регистр не важен, другие значения игнорируются (optional)
     * @return JsonRpcResultResponse
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> Ответ на запрос: поток &#x60;text/event-stream&#x60; с одним событием &#x60;message&#x60;. В &#x60;data&#x60; события — ответ JSON-RPC (схема и примеры ниже): успешный или с ошибкой JSON-RPC, например &#x60;-32601&#x60; для неизвестного метода. Пример потока:  &#x60;&#x60;&#x60; event: message data: {\&quot;jsonrpc\&quot;:\&quot;2.0\&quot;,\&quot;id\&quot;:1,\&quot;result\&quot;:{\&quot;tools\&quot;:[...]}} &#x60;&#x60;&#x60; </td><td>  -  </td></tr>
        <tr><td> 202 </td><td> Уведомление принято, тела нет </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Тело не является корректным JSON или сообщением JSON-RPC (&#x60;-32700&#x60;), заголовок &#x60;MCP-Protocol-Version&#x60; содержит неподдерживаемую версию (&#x60;-32000&#x60;) или недопустимое значение &#x60;x-tool-access&#x60; (&#x60;-32600&#x60;) </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Не авторизован </td><td>  -  </td></tr>
        <tr><td> 406 </td><td> Заголовок &#x60;Accept&#x60; не содержит &#x60;application/json&#x60; или &#x60;text/event-stream&#x60; </td><td>  -  </td></tr>
        <tr><td> 415 </td><td> &#x60;Content-Type&#x60; не &#x60;application/json&#x60; </td><td>  -  </td></tr>
        <tr><td> 429 </td><td> Превышен лимит запросов для токена </td><td>  -  </td></tr>
     </table>
     */
    public JsonRpcResultResponse sendMcpSearchMessage(String accept, JsonRpcRequest jsonRpcRequest, String mcPProtocolVersion, String xToolAccess, String xAllowedTools, String xMcpConfirmationUi) throws ApiException {
        ApiResponse<JsonRpcResultResponse> localVarResp = sendMcpSearchMessageWithHttpInfo(accept, jsonRpcRequest, mcPProtocolVersion, xToolAccess, xAllowedTools, xMcpConfirmationUi);
        return localVarResp.getData();
    }

    /**
     * Отправка JSON-RPC-сообщения серверу MCP с поиском инструментов
     * Чтобы отправить запрос или уведомление серверу MCP &#x60;timeweb-cloud-mcp-search&#x60;, отправьте POST-запрос на &#x60;/api/v1/mcp/search&#x60; с сообщением JSON-RPC 2.0 в теле.  &#x60;tools/list&#x60; возвращает четыре инструмента: &#x60;search_tools&#x60;, &#x60;get_tool_definition&#x60;, &#x60;execute_tool&#x60; и &#x60;request_user_confirmation&#x60;. Все операции облака находятся через &#x60;search_tools&#x60; и выполняются через &#x60;execute_tool&#x60;; изменяющие операции — в два вызова &#x60;execute_tool&#x60; с &#x60;confirm_token&#x60; внутри &#x60;arguments&#x60;.  Доступ модели можно сузить заголовками &#x60;x-tool-access&#x60; и &#x60;x-allowed-tools&#x60;. Недопустимое значение &#x60;x-tool-access&#x60; — ответ 400 с ошибкой &#x60;-32600&#x60;.
     * @param accept Должен содержать и &#x60;application/json&#x60;, и &#x60;text/event-stream&#x60;, иначе сервер ответит 406 (required)
     * @param jsonRpcRequest  (required)
     * @param mcPProtocolVersion Версия протокола, согласованная в &#x60;initialize&#x60;. Неподдерживаемое значение — ответ 400 (optional)
     * @param xToolAccess Только операции чтения: поиск и определения пропускают изменяющие инструменты, а &#x60;execute_tool&#x60; отказывает с &#x60;tool_not_allowed&#x60;. Единственное допустимое значение — &#x60;read-only&#x60; (регистр не важен); любое другое, в том числе пустое, — ответ 400 с ошибкой &#x60;-32600&#x60; (optional)
     * @param xAllowedTools Имена разрешённых операций через запятую — все остальные недоступны. Неизвестные имена игнорируются, пустое значение не разрешает ничего. Вместе с &#x60;x-tool-access&#x60; операция должна пройти оба ограничения (optional)
     * @param xMcpConfirmationUi Как &#x60;request_user_confirmation&#x60; спрашивает пользователя. &#x60;text&#x60; (по умолчанию) — пользователь отвечает в чате, на &#x60;/api/v1/mcp&#x60; инструмент в этом режиме не выводится. &#x60;buttons&#x60; — инструмент возвращает &#x60;ui_action&#x60; с кнопками для клиентов, которые умеют их показывать. Регистр не важен, другие значения игнорируются (optional)
     * @return ApiResponse&lt;JsonRpcResultResponse&gt;
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> Ответ на запрос: поток &#x60;text/event-stream&#x60; с одним событием &#x60;message&#x60;. В &#x60;data&#x60; события — ответ JSON-RPC (схема и примеры ниже): успешный или с ошибкой JSON-RPC, например &#x60;-32601&#x60; для неизвестного метода. Пример потока:  &#x60;&#x60;&#x60; event: message data: {\&quot;jsonrpc\&quot;:\&quot;2.0\&quot;,\&quot;id\&quot;:1,\&quot;result\&quot;:{\&quot;tools\&quot;:[...]}} &#x60;&#x60;&#x60; </td><td>  -  </td></tr>
        <tr><td> 202 </td><td> Уведомление принято, тела нет </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Тело не является корректным JSON или сообщением JSON-RPC (&#x60;-32700&#x60;), заголовок &#x60;MCP-Protocol-Version&#x60; содержит неподдерживаемую версию (&#x60;-32000&#x60;) или недопустимое значение &#x60;x-tool-access&#x60; (&#x60;-32600&#x60;) </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Не авторизован </td><td>  -  </td></tr>
        <tr><td> 406 </td><td> Заголовок &#x60;Accept&#x60; не содержит &#x60;application/json&#x60; или &#x60;text/event-stream&#x60; </td><td>  -  </td></tr>
        <tr><td> 415 </td><td> &#x60;Content-Type&#x60; не &#x60;application/json&#x60; </td><td>  -  </td></tr>
        <tr><td> 429 </td><td> Превышен лимит запросов для токена </td><td>  -  </td></tr>
     </table>
     */
    public ApiResponse<JsonRpcResultResponse> sendMcpSearchMessageWithHttpInfo(String accept, JsonRpcRequest jsonRpcRequest, String mcPProtocolVersion, String xToolAccess, String xAllowedTools, String xMcpConfirmationUi) throws ApiException {
        okhttp3.Call localVarCall = sendMcpSearchMessageValidateBeforeCall(accept, jsonRpcRequest, mcPProtocolVersion, xToolAccess, xAllowedTools, xMcpConfirmationUi, null);
        Type localVarReturnType = new TypeToken<JsonRpcResultResponse>(){}.getType();
        return localVarApiClient.execute(localVarCall, localVarReturnType);
    }

    /**
     * Отправка JSON-RPC-сообщения серверу MCP с поиском инструментов (asynchronously)
     * Чтобы отправить запрос или уведомление серверу MCP &#x60;timeweb-cloud-mcp-search&#x60;, отправьте POST-запрос на &#x60;/api/v1/mcp/search&#x60; с сообщением JSON-RPC 2.0 в теле.  &#x60;tools/list&#x60; возвращает четыре инструмента: &#x60;search_tools&#x60;, &#x60;get_tool_definition&#x60;, &#x60;execute_tool&#x60; и &#x60;request_user_confirmation&#x60;. Все операции облака находятся через &#x60;search_tools&#x60; и выполняются через &#x60;execute_tool&#x60;; изменяющие операции — в два вызова &#x60;execute_tool&#x60; с &#x60;confirm_token&#x60; внутри &#x60;arguments&#x60;.  Доступ модели можно сузить заголовками &#x60;x-tool-access&#x60; и &#x60;x-allowed-tools&#x60;. Недопустимое значение &#x60;x-tool-access&#x60; — ответ 400 с ошибкой &#x60;-32600&#x60;.
     * @param accept Должен содержать и &#x60;application/json&#x60;, и &#x60;text/event-stream&#x60;, иначе сервер ответит 406 (required)
     * @param jsonRpcRequest  (required)
     * @param mcPProtocolVersion Версия протокола, согласованная в &#x60;initialize&#x60;. Неподдерживаемое значение — ответ 400 (optional)
     * @param xToolAccess Только операции чтения: поиск и определения пропускают изменяющие инструменты, а &#x60;execute_tool&#x60; отказывает с &#x60;tool_not_allowed&#x60;. Единственное допустимое значение — &#x60;read-only&#x60; (регистр не важен); любое другое, в том числе пустое, — ответ 400 с ошибкой &#x60;-32600&#x60; (optional)
     * @param xAllowedTools Имена разрешённых операций через запятую — все остальные недоступны. Неизвестные имена игнорируются, пустое значение не разрешает ничего. Вместе с &#x60;x-tool-access&#x60; операция должна пройти оба ограничения (optional)
     * @param xMcpConfirmationUi Как &#x60;request_user_confirmation&#x60; спрашивает пользователя. &#x60;text&#x60; (по умолчанию) — пользователь отвечает в чате, на &#x60;/api/v1/mcp&#x60; инструмент в этом режиме не выводится. &#x60;buttons&#x60; — инструмент возвращает &#x60;ui_action&#x60; с кнопками для клиентов, которые умеют их показывать. Регистр не важен, другие значения игнорируются (optional)
     * @param _callback The callback to be executed when the API call finishes
     * @return The request call
     * @throws ApiException If fail to process the API call, e.g. serializing the request body object
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> Ответ на запрос: поток &#x60;text/event-stream&#x60; с одним событием &#x60;message&#x60;. В &#x60;data&#x60; события — ответ JSON-RPC (схема и примеры ниже): успешный или с ошибкой JSON-RPC, например &#x60;-32601&#x60; для неизвестного метода. Пример потока:  &#x60;&#x60;&#x60; event: message data: {\&quot;jsonrpc\&quot;:\&quot;2.0\&quot;,\&quot;id\&quot;:1,\&quot;result\&quot;:{\&quot;tools\&quot;:[...]}} &#x60;&#x60;&#x60; </td><td>  -  </td></tr>
        <tr><td> 202 </td><td> Уведомление принято, тела нет </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Тело не является корректным JSON или сообщением JSON-RPC (&#x60;-32700&#x60;), заголовок &#x60;MCP-Protocol-Version&#x60; содержит неподдерживаемую версию (&#x60;-32000&#x60;) или недопустимое значение &#x60;x-tool-access&#x60; (&#x60;-32600&#x60;) </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Не авторизован </td><td>  -  </td></tr>
        <tr><td> 406 </td><td> Заголовок &#x60;Accept&#x60; не содержит &#x60;application/json&#x60; или &#x60;text/event-stream&#x60; </td><td>  -  </td></tr>
        <tr><td> 415 </td><td> &#x60;Content-Type&#x60; не &#x60;application/json&#x60; </td><td>  -  </td></tr>
        <tr><td> 429 </td><td> Превышен лимит запросов для токена </td><td>  -  </td></tr>
     </table>
     */
    public okhttp3.Call sendMcpSearchMessageAsync(String accept, JsonRpcRequest jsonRpcRequest, String mcPProtocolVersion, String xToolAccess, String xAllowedTools, String xMcpConfirmationUi, final ApiCallback<JsonRpcResultResponse> _callback) throws ApiException {

        okhttp3.Call localVarCall = sendMcpSearchMessageValidateBeforeCall(accept, jsonRpcRequest, mcPProtocolVersion, xToolAccess, xAllowedTools, xMcpConfirmationUi, _callback);
        Type localVarReturnType = new TypeToken<JsonRpcResultResponse>(){}.getType();
        localVarApiClient.executeAsync(localVarCall, localVarReturnType, _callback);
        return localVarCall;
    }
}
