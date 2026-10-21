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


import org.openapitools.client.model.AuthorizeUrl;
import org.openapitools.client.model.ConnectAgentTool;
import org.openapitools.client.model.CreateAgentTool201Response;
import org.openapitools.client.model.CreateTool;
import org.openapitools.client.model.GetAccountStatus403Response;
import org.openapitools.client.model.GetAgentToolTemplatePrices200Response;
import org.openapitools.client.model.GetAgentToolTemplates200Response;
import org.openapitools.client.model.GetAgentTools200Response;
import org.openapitools.client.model.GetAgentToolsByAgent200Response;
import org.openapitools.client.model.GetFinances400Response;
import org.openapitools.client.model.GetFinances401Response;
import org.openapitools.client.model.GetFinances429Response;
import org.openapitools.client.model.GetFinances500Response;
import org.openapitools.client.model.GetImage404Response;
import org.openapitools.client.model.InstallTool;
import org.openapitools.client.model.UpdateAgentTool;
import org.openapitools.client.model.UpdateTool;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AiAgentToolsApi {
    private ApiClient localVarApiClient;
    private int localHostIndex;
    private String localCustomBaseUrl;

    public AiAgentToolsApi() {
        this(Configuration.getDefaultApiClient());
    }

    public AiAgentToolsApi(ApiClient apiClient) {
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
     * Build call for connectAgentToolToAgent
     * @param agentId ID агента (required)
     * @param connectAgentTool  (required)
     * @param _callback Callback for upload/download progress
     * @return Call to execute
     * @throws ApiException If fail to serialize the request body object
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 201 </td><td> Инструмент подключён к агенту. Тело ответа пустое </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Некорректный запрос </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Не авторизован </td><td>  -  </td></tr>
        <tr><td> 403 </td><td> Запрещено </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Не найдено </td><td>  -  </td></tr>
        <tr><td> 429 </td><td> Слишком много запросов </td><td>  -  </td></tr>
        <tr><td> 500 </td><td> Внутренняя ошибка сервера </td><td>  -  </td></tr>
     </table>
     */
    public okhttp3.Call connectAgentToolToAgentCall(Integer agentId, ConnectAgentTool connectAgentTool, final ApiCallback _callback) throws ApiException {
        String basePath = null;
        // Operation Servers
        String[] localBasePaths = new String[] {  };

        // Determine Base Path to Use
        if (localCustomBaseUrl != null){
            basePath = localCustomBaseUrl;
        } else if ( localBasePaths.length > 0 ) {
            basePath = localBasePaths[localHostIndex];
        } else {
            basePath = null;
        }

        Object localVarPostBody = connectAgentTool;

        // create path and map variables
        String localVarPath = "/api/v1/cloud-ai/agents/{agent_id}/tools"
            .replace("{" + "agent_id" + "}", localVarApiClient.escapeString(agentId.toString()));

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
    private okhttp3.Call connectAgentToolToAgentValidateBeforeCall(Integer agentId, ConnectAgentTool connectAgentTool, final ApiCallback _callback) throws ApiException {
        // verify the required parameter 'agentId' is set
        if (agentId == null) {
            throw new ApiException("Missing the required parameter 'agentId' when calling connectAgentToolToAgent(Async)");
        }

        // verify the required parameter 'connectAgentTool' is set
        if (connectAgentTool == null) {
            throw new ApiException("Missing the required parameter 'connectAgentTool' when calling connectAgentToolToAgent(Async)");
        }

        return connectAgentToolToAgentCall(agentId, connectAgentTool, _callback);

    }

    /**
     * Подключение MCP-инструмента к агенту
     * Чтобы подключить MCP-инструмент к агенту, отправьте POST-запрос на &#x60;/api/v1/cloud-ai/agents/{agent_id}/tools&#x60;.  В &#x60;allowed_methods&#x60; перечислите методы, которые агенту разрешено вызывать. Подходят только имена из &#x60;methods[].name&#x60; инструмента, иначе вернётся 400 с &#x60;error_code&#x60; &#x60;invalid_methods_provided&#x60;. Повторно подключить уже подключённый инструмент нельзя. Чтобы изменить список методов, используйте PUT-запрос.
     * @param agentId ID агента (required)
     * @param connectAgentTool  (required)
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 201 </td><td> Инструмент подключён к агенту. Тело ответа пустое </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Некорректный запрос </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Не авторизован </td><td>  -  </td></tr>
        <tr><td> 403 </td><td> Запрещено </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Не найдено </td><td>  -  </td></tr>
        <tr><td> 429 </td><td> Слишком много запросов </td><td>  -  </td></tr>
        <tr><td> 500 </td><td> Внутренняя ошибка сервера </td><td>  -  </td></tr>
     </table>
     */
    public void connectAgentToolToAgent(Integer agentId, ConnectAgentTool connectAgentTool) throws ApiException {
        connectAgentToolToAgentWithHttpInfo(agentId, connectAgentTool);
    }

    /**
     * Подключение MCP-инструмента к агенту
     * Чтобы подключить MCP-инструмент к агенту, отправьте POST-запрос на &#x60;/api/v1/cloud-ai/agents/{agent_id}/tools&#x60;.  В &#x60;allowed_methods&#x60; перечислите методы, которые агенту разрешено вызывать. Подходят только имена из &#x60;methods[].name&#x60; инструмента, иначе вернётся 400 с &#x60;error_code&#x60; &#x60;invalid_methods_provided&#x60;. Повторно подключить уже подключённый инструмент нельзя. Чтобы изменить список методов, используйте PUT-запрос.
     * @param agentId ID агента (required)
     * @param connectAgentTool  (required)
     * @return ApiResponse&lt;Void&gt;
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 201 </td><td> Инструмент подключён к агенту. Тело ответа пустое </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Некорректный запрос </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Не авторизован </td><td>  -  </td></tr>
        <tr><td> 403 </td><td> Запрещено </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Не найдено </td><td>  -  </td></tr>
        <tr><td> 429 </td><td> Слишком много запросов </td><td>  -  </td></tr>
        <tr><td> 500 </td><td> Внутренняя ошибка сервера </td><td>  -  </td></tr>
     </table>
     */
    public ApiResponse<Void> connectAgentToolToAgentWithHttpInfo(Integer agentId, ConnectAgentTool connectAgentTool) throws ApiException {
        okhttp3.Call localVarCall = connectAgentToolToAgentValidateBeforeCall(agentId, connectAgentTool, null);
        return localVarApiClient.execute(localVarCall);
    }

    /**
     * Подключение MCP-инструмента к агенту (asynchronously)
     * Чтобы подключить MCP-инструмент к агенту, отправьте POST-запрос на &#x60;/api/v1/cloud-ai/agents/{agent_id}/tools&#x60;.  В &#x60;allowed_methods&#x60; перечислите методы, которые агенту разрешено вызывать. Подходят только имена из &#x60;methods[].name&#x60; инструмента, иначе вернётся 400 с &#x60;error_code&#x60; &#x60;invalid_methods_provided&#x60;. Повторно подключить уже подключённый инструмент нельзя. Чтобы изменить список методов, используйте PUT-запрос.
     * @param agentId ID агента (required)
     * @param connectAgentTool  (required)
     * @param _callback The callback to be executed when the API call finishes
     * @return The request call
     * @throws ApiException If fail to process the API call, e.g. serializing the request body object
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 201 </td><td> Инструмент подключён к агенту. Тело ответа пустое </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Некорректный запрос </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Не авторизован </td><td>  -  </td></tr>
        <tr><td> 403 </td><td> Запрещено </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Не найдено </td><td>  -  </td></tr>
        <tr><td> 429 </td><td> Слишком много запросов </td><td>  -  </td></tr>
        <tr><td> 500 </td><td> Внутренняя ошибка сервера </td><td>  -  </td></tr>
     </table>
     */
    public okhttp3.Call connectAgentToolToAgentAsync(Integer agentId, ConnectAgentTool connectAgentTool, final ApiCallback<Void> _callback) throws ApiException {

        okhttp3.Call localVarCall = connectAgentToolToAgentValidateBeforeCall(agentId, connectAgentTool, _callback);
        localVarApiClient.executeAsync(localVarCall, _callback);
        return localVarCall;
    }
    /**
     * Build call for createAgentTool
     * @param createTool  (required)
     * @param _callback Callback for upload/download progress
     * @return Call to execute
     * @throws ApiException If fail to serialize the request body object
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 201 </td><td> Объект JSON c ключом &#x60;tool&#x60; </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Некорректный запрос </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Не авторизован </td><td>  -  </td></tr>
        <tr><td> 403 </td><td> Запрещено </td><td>  -  </td></tr>
        <tr><td> 429 </td><td> Слишком много запросов </td><td>  -  </td></tr>
        <tr><td> 500 </td><td> Внутренняя ошибка сервера </td><td>  -  </td></tr>
     </table>
     */
    public okhttp3.Call createAgentToolCall(CreateTool createTool, final ApiCallback _callback) throws ApiException {
        String basePath = null;
        // Operation Servers
        String[] localBasePaths = new String[] {  };

        // Determine Base Path to Use
        if (localCustomBaseUrl != null){
            basePath = localCustomBaseUrl;
        } else if ( localBasePaths.length > 0 ) {
            basePath = localBasePaths[localHostIndex];
        } else {
            basePath = null;
        }

        Object localVarPostBody = createTool;

        // create path and map variables
        String localVarPath = "/api/v1/cloud-ai/tools";

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
    private okhttp3.Call createAgentToolValidateBeforeCall(CreateTool createTool, final ApiCallback _callback) throws ApiException {
        // verify the required parameter 'createTool' is set
        if (createTool == null) {
            throw new ApiException("Missing the required parameter 'createTool' when calling createAgentTool(Async)");
        }

        return createAgentToolCall(createTool, _callback);

    }

    /**
     * Подключение собственного MCP-сервера
     * Чтобы подключить собственный MCP-сервер, отправьте POST-запрос на &#x60;/api/v1/cloud-ai/tools&#x60;.  Платформа сразу подключается к серверу и сохраняет список его методов. Если сервер недоступен или отвечает ошибкой, инструмент не создаётся: вернётся 400 с &#x60;error_code&#x60; &#x60;invalid_methods_provided&#x60;. Адреса частных сетей и localhost отклоняются с ошибкой 400.  Для &#x60;auth_data.type &#x3D; oauth2&#x60; подключение к серверу при создании не проверяется. Инструмент создаётся в статусе &#x60;pending_auth&#x60; с пустым списком методов, а методы загружаются после авторизации пользователя: см. «Получение ссылки на OAuth2-авторизацию инструмента». Если не удалось определить адреса авторизации или зарегистрировать клиента, вернётся 401 с &#x60;error_code&#x60; &#x60;tool_authentication_failed&#x60;.  Чтобы агент мог пользоваться инструментом, подключите его к агенту.
     * @param createTool  (required)
     * @return CreateAgentTool201Response
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 201 </td><td> Объект JSON c ключом &#x60;tool&#x60; </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Некорректный запрос </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Не авторизован </td><td>  -  </td></tr>
        <tr><td> 403 </td><td> Запрещено </td><td>  -  </td></tr>
        <tr><td> 429 </td><td> Слишком много запросов </td><td>  -  </td></tr>
        <tr><td> 500 </td><td> Внутренняя ошибка сервера </td><td>  -  </td></tr>
     </table>
     */
    public CreateAgentTool201Response createAgentTool(CreateTool createTool) throws ApiException {
        ApiResponse<CreateAgentTool201Response> localVarResp = createAgentToolWithHttpInfo(createTool);
        return localVarResp.getData();
    }

    /**
     * Подключение собственного MCP-сервера
     * Чтобы подключить собственный MCP-сервер, отправьте POST-запрос на &#x60;/api/v1/cloud-ai/tools&#x60;.  Платформа сразу подключается к серверу и сохраняет список его методов. Если сервер недоступен или отвечает ошибкой, инструмент не создаётся: вернётся 400 с &#x60;error_code&#x60; &#x60;invalid_methods_provided&#x60;. Адреса частных сетей и localhost отклоняются с ошибкой 400.  Для &#x60;auth_data.type &#x3D; oauth2&#x60; подключение к серверу при создании не проверяется. Инструмент создаётся в статусе &#x60;pending_auth&#x60; с пустым списком методов, а методы загружаются после авторизации пользователя: см. «Получение ссылки на OAuth2-авторизацию инструмента». Если не удалось определить адреса авторизации или зарегистрировать клиента, вернётся 401 с &#x60;error_code&#x60; &#x60;tool_authentication_failed&#x60;.  Чтобы агент мог пользоваться инструментом, подключите его к агенту.
     * @param createTool  (required)
     * @return ApiResponse&lt;CreateAgentTool201Response&gt;
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 201 </td><td> Объект JSON c ключом &#x60;tool&#x60; </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Некорректный запрос </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Не авторизован </td><td>  -  </td></tr>
        <tr><td> 403 </td><td> Запрещено </td><td>  -  </td></tr>
        <tr><td> 429 </td><td> Слишком много запросов </td><td>  -  </td></tr>
        <tr><td> 500 </td><td> Внутренняя ошибка сервера </td><td>  -  </td></tr>
     </table>
     */
    public ApiResponse<CreateAgentTool201Response> createAgentToolWithHttpInfo(CreateTool createTool) throws ApiException {
        okhttp3.Call localVarCall = createAgentToolValidateBeforeCall(createTool, null);
        Type localVarReturnType = new TypeToken<CreateAgentTool201Response>(){}.getType();
        return localVarApiClient.execute(localVarCall, localVarReturnType);
    }

    /**
     * Подключение собственного MCP-сервера (asynchronously)
     * Чтобы подключить собственный MCP-сервер, отправьте POST-запрос на &#x60;/api/v1/cloud-ai/tools&#x60;.  Платформа сразу подключается к серверу и сохраняет список его методов. Если сервер недоступен или отвечает ошибкой, инструмент не создаётся: вернётся 400 с &#x60;error_code&#x60; &#x60;invalid_methods_provided&#x60;. Адреса частных сетей и localhost отклоняются с ошибкой 400.  Для &#x60;auth_data.type &#x3D; oauth2&#x60; подключение к серверу при создании не проверяется. Инструмент создаётся в статусе &#x60;pending_auth&#x60; с пустым списком методов, а методы загружаются после авторизации пользователя: см. «Получение ссылки на OAuth2-авторизацию инструмента». Если не удалось определить адреса авторизации или зарегистрировать клиента, вернётся 401 с &#x60;error_code&#x60; &#x60;tool_authentication_failed&#x60;.  Чтобы агент мог пользоваться инструментом, подключите его к агенту.
     * @param createTool  (required)
     * @param _callback The callback to be executed when the API call finishes
     * @return The request call
     * @throws ApiException If fail to process the API call, e.g. serializing the request body object
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 201 </td><td> Объект JSON c ключом &#x60;tool&#x60; </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Некорректный запрос </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Не авторизован </td><td>  -  </td></tr>
        <tr><td> 403 </td><td> Запрещено </td><td>  -  </td></tr>
        <tr><td> 429 </td><td> Слишком много запросов </td><td>  -  </td></tr>
        <tr><td> 500 </td><td> Внутренняя ошибка сервера </td><td>  -  </td></tr>
     </table>
     */
    public okhttp3.Call createAgentToolAsync(CreateTool createTool, final ApiCallback<CreateAgentTool201Response> _callback) throws ApiException {

        okhttp3.Call localVarCall = createAgentToolValidateBeforeCall(createTool, _callback);
        Type localVarReturnType = new TypeToken<CreateAgentTool201Response>(){}.getType();
        localVarApiClient.executeAsync(localVarCall, localVarReturnType, _callback);
        return localVarCall;
    }
    /**
     * Build call for deleteAgentTool
     * @param id ID инструмента (required)
     * @param _callback Callback for upload/download progress
     * @return Call to execute
     * @throws ApiException If fail to serialize the request body object
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> Инструмент удалён. Тело ответа пустое </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Некорректный запрос </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Не авторизован </td><td>  -  </td></tr>
        <tr><td> 403 </td><td> Запрещено </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Не найдено </td><td>  -  </td></tr>
        <tr><td> 429 </td><td> Слишком много запросов </td><td>  -  </td></tr>
        <tr><td> 500 </td><td> Внутренняя ошибка сервера </td><td>  -  </td></tr>
     </table>
     */
    public okhttp3.Call deleteAgentToolCall(Integer id, final ApiCallback _callback) throws ApiException {
        String basePath = null;
        // Operation Servers
        String[] localBasePaths = new String[] {  };

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
        String localVarPath = "/api/v1/cloud-ai/tools/{id}"
            .replace("{" + "id" + "}", localVarApiClient.escapeString(id.toString()));

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
    private okhttp3.Call deleteAgentToolValidateBeforeCall(Integer id, final ApiCallback _callback) throws ApiException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new ApiException("Missing the required parameter 'id' when calling deleteAgentTool(Async)");
        }

        return deleteAgentToolCall(id, _callback);

    }

    /**
     * Удаление MCP-инструмента
     * Чтобы удалить MCP-инструмент, отправьте DELETE-запрос на &#x60;/api/v1/cloud-ai/tools/{id}&#x60;.  Инструмент автоматически отключается от всех агентов.
     * @param id ID инструмента (required)
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> Инструмент удалён. Тело ответа пустое </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Некорректный запрос </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Не авторизован </td><td>  -  </td></tr>
        <tr><td> 403 </td><td> Запрещено </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Не найдено </td><td>  -  </td></tr>
        <tr><td> 429 </td><td> Слишком много запросов </td><td>  -  </td></tr>
        <tr><td> 500 </td><td> Внутренняя ошибка сервера </td><td>  -  </td></tr>
     </table>
     */
    public void deleteAgentTool(Integer id) throws ApiException {
        deleteAgentToolWithHttpInfo(id);
    }

    /**
     * Удаление MCP-инструмента
     * Чтобы удалить MCP-инструмент, отправьте DELETE-запрос на &#x60;/api/v1/cloud-ai/tools/{id}&#x60;.  Инструмент автоматически отключается от всех агентов.
     * @param id ID инструмента (required)
     * @return ApiResponse&lt;Void&gt;
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> Инструмент удалён. Тело ответа пустое </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Некорректный запрос </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Не авторизован </td><td>  -  </td></tr>
        <tr><td> 403 </td><td> Запрещено </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Не найдено </td><td>  -  </td></tr>
        <tr><td> 429 </td><td> Слишком много запросов </td><td>  -  </td></tr>
        <tr><td> 500 </td><td> Внутренняя ошибка сервера </td><td>  -  </td></tr>
     </table>
     */
    public ApiResponse<Void> deleteAgentToolWithHttpInfo(Integer id) throws ApiException {
        okhttp3.Call localVarCall = deleteAgentToolValidateBeforeCall(id, null);
        return localVarApiClient.execute(localVarCall);
    }

    /**
     * Удаление MCP-инструмента (asynchronously)
     * Чтобы удалить MCP-инструмент, отправьте DELETE-запрос на &#x60;/api/v1/cloud-ai/tools/{id}&#x60;.  Инструмент автоматически отключается от всех агентов.
     * @param id ID инструмента (required)
     * @param _callback The callback to be executed when the API call finishes
     * @return The request call
     * @throws ApiException If fail to process the API call, e.g. serializing the request body object
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> Инструмент удалён. Тело ответа пустое </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Некорректный запрос </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Не авторизован </td><td>  -  </td></tr>
        <tr><td> 403 </td><td> Запрещено </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Не найдено </td><td>  -  </td></tr>
        <tr><td> 429 </td><td> Слишком много запросов </td><td>  -  </td></tr>
        <tr><td> 500 </td><td> Внутренняя ошибка сервера </td><td>  -  </td></tr>
     </table>
     */
    public okhttp3.Call deleteAgentToolAsync(Integer id, final ApiCallback<Void> _callback) throws ApiException {

        okhttp3.Call localVarCall = deleteAgentToolValidateBeforeCall(id, _callback);
        localVarApiClient.executeAsync(localVarCall, _callback);
        return localVarCall;
    }
    /**
     * Build call for disconnectAgentToolFromAgent
     * @param agentId ID агента (required)
     * @param toolId ID инструмента (required)
     * @param _callback Callback for upload/download progress
     * @return Call to execute
     * @throws ApiException If fail to serialize the request body object
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> Инструмент отключён от агента. Тело ответа пустое </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Некорректный запрос </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Не авторизован </td><td>  -  </td></tr>
        <tr><td> 403 </td><td> Запрещено </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Не найдено </td><td>  -  </td></tr>
        <tr><td> 429 </td><td> Слишком много запросов </td><td>  -  </td></tr>
        <tr><td> 500 </td><td> Внутренняя ошибка сервера </td><td>  -  </td></tr>
     </table>
     */
    public okhttp3.Call disconnectAgentToolFromAgentCall(Integer agentId, Integer toolId, final ApiCallback _callback) throws ApiException {
        String basePath = null;
        // Operation Servers
        String[] localBasePaths = new String[] {  };

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
        String localVarPath = "/api/v1/cloud-ai/agents/{agent_id}/tools/{tool_id}"
            .replace("{" + "agent_id" + "}", localVarApiClient.escapeString(agentId.toString()))
            .replace("{" + "tool_id" + "}", localVarApiClient.escapeString(toolId.toString()));

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
    private okhttp3.Call disconnectAgentToolFromAgentValidateBeforeCall(Integer agentId, Integer toolId, final ApiCallback _callback) throws ApiException {
        // verify the required parameter 'agentId' is set
        if (agentId == null) {
            throw new ApiException("Missing the required parameter 'agentId' when calling disconnectAgentToolFromAgent(Async)");
        }

        // verify the required parameter 'toolId' is set
        if (toolId == null) {
            throw new ApiException("Missing the required parameter 'toolId' when calling disconnectAgentToolFromAgent(Async)");
        }

        return disconnectAgentToolFromAgentCall(agentId, toolId, _callback);

    }

    /**
     * Отключение MCP-инструмента от агента
     * Чтобы отключить MCP-инструмент от агента, отправьте DELETE-запрос на &#x60;/api/v1/cloud-ai/agents/{agent_id}/tools/{tool_id}&#x60;.  Сам инструмент остаётся в аккаунте и у других агентов.
     * @param agentId ID агента (required)
     * @param toolId ID инструмента (required)
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> Инструмент отключён от агента. Тело ответа пустое </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Некорректный запрос </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Не авторизован </td><td>  -  </td></tr>
        <tr><td> 403 </td><td> Запрещено </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Не найдено </td><td>  -  </td></tr>
        <tr><td> 429 </td><td> Слишком много запросов </td><td>  -  </td></tr>
        <tr><td> 500 </td><td> Внутренняя ошибка сервера </td><td>  -  </td></tr>
     </table>
     */
    public void disconnectAgentToolFromAgent(Integer agentId, Integer toolId) throws ApiException {
        disconnectAgentToolFromAgentWithHttpInfo(agentId, toolId);
    }

    /**
     * Отключение MCP-инструмента от агента
     * Чтобы отключить MCP-инструмент от агента, отправьте DELETE-запрос на &#x60;/api/v1/cloud-ai/agents/{agent_id}/tools/{tool_id}&#x60;.  Сам инструмент остаётся в аккаунте и у других агентов.
     * @param agentId ID агента (required)
     * @param toolId ID инструмента (required)
     * @return ApiResponse&lt;Void&gt;
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> Инструмент отключён от агента. Тело ответа пустое </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Некорректный запрос </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Не авторизован </td><td>  -  </td></tr>
        <tr><td> 403 </td><td> Запрещено </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Не найдено </td><td>  -  </td></tr>
        <tr><td> 429 </td><td> Слишком много запросов </td><td>  -  </td></tr>
        <tr><td> 500 </td><td> Внутренняя ошибка сервера </td><td>  -  </td></tr>
     </table>
     */
    public ApiResponse<Void> disconnectAgentToolFromAgentWithHttpInfo(Integer agentId, Integer toolId) throws ApiException {
        okhttp3.Call localVarCall = disconnectAgentToolFromAgentValidateBeforeCall(agentId, toolId, null);
        return localVarApiClient.execute(localVarCall);
    }

    /**
     * Отключение MCP-инструмента от агента (asynchronously)
     * Чтобы отключить MCP-инструмент от агента, отправьте DELETE-запрос на &#x60;/api/v1/cloud-ai/agents/{agent_id}/tools/{tool_id}&#x60;.  Сам инструмент остаётся в аккаунте и у других агентов.
     * @param agentId ID агента (required)
     * @param toolId ID инструмента (required)
     * @param _callback The callback to be executed when the API call finishes
     * @return The request call
     * @throws ApiException If fail to process the API call, e.g. serializing the request body object
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> Инструмент отключён от агента. Тело ответа пустое </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Некорректный запрос </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Не авторизован </td><td>  -  </td></tr>
        <tr><td> 403 </td><td> Запрещено </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Не найдено </td><td>  -  </td></tr>
        <tr><td> 429 </td><td> Слишком много запросов </td><td>  -  </td></tr>
        <tr><td> 500 </td><td> Внутренняя ошибка сервера </td><td>  -  </td></tr>
     </table>
     */
    public okhttp3.Call disconnectAgentToolFromAgentAsync(Integer agentId, Integer toolId, final ApiCallback<Void> _callback) throws ApiException {

        okhttp3.Call localVarCall = disconnectAgentToolFromAgentValidateBeforeCall(agentId, toolId, _callback);
        localVarApiClient.executeAsync(localVarCall, _callback);
        return localVarCall;
    }
    /**
     * Build call for getAgentTool
     * @param id ID инструмента (required)
     * @param _callback Callback for upload/download progress
     * @return Call to execute
     * @throws ApiException If fail to serialize the request body object
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> Объект JSON c ключом &#x60;tool&#x60; </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Некорректный запрос </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Не авторизован </td><td>  -  </td></tr>
        <tr><td> 403 </td><td> Запрещено </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Не найдено </td><td>  -  </td></tr>
        <tr><td> 429 </td><td> Слишком много запросов </td><td>  -  </td></tr>
        <tr><td> 500 </td><td> Внутренняя ошибка сервера </td><td>  -  </td></tr>
     </table>
     */
    public okhttp3.Call getAgentToolCall(Integer id, final ApiCallback _callback) throws ApiException {
        String basePath = null;
        // Operation Servers
        String[] localBasePaths = new String[] {  };

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
        String localVarPath = "/api/v1/cloud-ai/tools/{id}"
            .replace("{" + "id" + "}", localVarApiClient.escapeString(id.toString()));

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
        return localVarApiClient.buildCall(basePath, localVarPath, "GET", localVarQueryParams, localVarCollectionQueryParams, localVarPostBody, localVarHeaderParams, localVarCookieParams, localVarFormParams, localVarAuthNames, _callback);
    }

    @SuppressWarnings("rawtypes")
    private okhttp3.Call getAgentToolValidateBeforeCall(Integer id, final ApiCallback _callback) throws ApiException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new ApiException("Missing the required parameter 'id' when calling getAgentTool(Async)");
        }

        return getAgentToolCall(id, _callback);

    }

    /**
     * Получение MCP-инструмента
     * Чтобы получить MCP-инструмент, отправьте GET-запрос на &#x60;/api/v1/cloud-ai/tools/{id}&#x60;.  По полю &#x60;status&#x60; можно отследить завершение OAuth2-авторизации: после неё статус меняется с &#x60;pending_auth&#x60; на &#x60;active&#x60;.
     * @param id ID инструмента (required)
     * @return CreateAgentTool201Response
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> Объект JSON c ключом &#x60;tool&#x60; </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Некорректный запрос </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Не авторизован </td><td>  -  </td></tr>
        <tr><td> 403 </td><td> Запрещено </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Не найдено </td><td>  -  </td></tr>
        <tr><td> 429 </td><td> Слишком много запросов </td><td>  -  </td></tr>
        <tr><td> 500 </td><td> Внутренняя ошибка сервера </td><td>  -  </td></tr>
     </table>
     */
    public CreateAgentTool201Response getAgentTool(Integer id) throws ApiException {
        ApiResponse<CreateAgentTool201Response> localVarResp = getAgentToolWithHttpInfo(id);
        return localVarResp.getData();
    }

    /**
     * Получение MCP-инструмента
     * Чтобы получить MCP-инструмент, отправьте GET-запрос на &#x60;/api/v1/cloud-ai/tools/{id}&#x60;.  По полю &#x60;status&#x60; можно отследить завершение OAuth2-авторизации: после неё статус меняется с &#x60;pending_auth&#x60; на &#x60;active&#x60;.
     * @param id ID инструмента (required)
     * @return ApiResponse&lt;CreateAgentTool201Response&gt;
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> Объект JSON c ключом &#x60;tool&#x60; </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Некорректный запрос </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Не авторизован </td><td>  -  </td></tr>
        <tr><td> 403 </td><td> Запрещено </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Не найдено </td><td>  -  </td></tr>
        <tr><td> 429 </td><td> Слишком много запросов </td><td>  -  </td></tr>
        <tr><td> 500 </td><td> Внутренняя ошибка сервера </td><td>  -  </td></tr>
     </table>
     */
    public ApiResponse<CreateAgentTool201Response> getAgentToolWithHttpInfo(Integer id) throws ApiException {
        okhttp3.Call localVarCall = getAgentToolValidateBeforeCall(id, null);
        Type localVarReturnType = new TypeToken<CreateAgentTool201Response>(){}.getType();
        return localVarApiClient.execute(localVarCall, localVarReturnType);
    }

    /**
     * Получение MCP-инструмента (asynchronously)
     * Чтобы получить MCP-инструмент, отправьте GET-запрос на &#x60;/api/v1/cloud-ai/tools/{id}&#x60;.  По полю &#x60;status&#x60; можно отследить завершение OAuth2-авторизации: после неё статус меняется с &#x60;pending_auth&#x60; на &#x60;active&#x60;.
     * @param id ID инструмента (required)
     * @param _callback The callback to be executed when the API call finishes
     * @return The request call
     * @throws ApiException If fail to process the API call, e.g. serializing the request body object
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> Объект JSON c ключом &#x60;tool&#x60; </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Некорректный запрос </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Не авторизован </td><td>  -  </td></tr>
        <tr><td> 403 </td><td> Запрещено </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Не найдено </td><td>  -  </td></tr>
        <tr><td> 429 </td><td> Слишком много запросов </td><td>  -  </td></tr>
        <tr><td> 500 </td><td> Внутренняя ошибка сервера </td><td>  -  </td></tr>
     </table>
     */
    public okhttp3.Call getAgentToolAsync(Integer id, final ApiCallback<CreateAgentTool201Response> _callback) throws ApiException {

        okhttp3.Call localVarCall = getAgentToolValidateBeforeCall(id, _callback);
        Type localVarReturnType = new TypeToken<CreateAgentTool201Response>(){}.getType();
        localVarApiClient.executeAsync(localVarCall, localVarReturnType, _callback);
        return localVarCall;
    }
    /**
     * Build call for getAgentToolOAuth2AuthorizeUrl
     * @param toolId ID инструмента (required)
     * @param redirectUrl Куда вернуть пользователя после авторизации. Принимаются только адреса на доменах &#x60;timeweb.cloud&#x60; и &#x60;timeweb.net&#x60; и их поддоменах; другие адреса молча игнорируются. После успешной авторизации к адресу добавляются &#x60;oauth_success&#x3D;true&amp;tool_id&#x3D;&lt;id&gt;&#x60;, после ошибки — &#x60;oauth_error&#x3D;&lt;текст&gt;&amp;tool_id&#x3D;&lt;id&gt;&#x60;. Без параметра страница возврата показывает результат в виде JSON. (optional)
     * @param _callback Callback for upload/download progress
     * @return Call to execute
     * @throws ApiException If fail to serialize the request body object
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> Объект JSON c ключом &#x60;authorize_url&#x60; </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Некорректный запрос </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Не авторизован </td><td>  -  </td></tr>
        <tr><td> 403 </td><td> Запрещено </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Не найдено </td><td>  -  </td></tr>
        <tr><td> 429 </td><td> Слишком много запросов </td><td>  -  </td></tr>
        <tr><td> 500 </td><td> Внутренняя ошибка сервера </td><td>  -  </td></tr>
     </table>
     */
    public okhttp3.Call getAgentToolOAuth2AuthorizeUrlCall(Integer toolId, String redirectUrl, final ApiCallback _callback) throws ApiException {
        String basePath = null;
        // Operation Servers
        String[] localBasePaths = new String[] {  };

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
        String localVarPath = "/api/v1/cloud-ai/tools/oauth2/authorize-url/{tool_id}"
            .replace("{" + "tool_id" + "}", localVarApiClient.escapeString(toolId.toString()));

        List<Pair> localVarQueryParams = new ArrayList<Pair>();
        List<Pair> localVarCollectionQueryParams = new ArrayList<Pair>();
        Map<String, String> localVarHeaderParams = new HashMap<String, String>();
        Map<String, String> localVarCookieParams = new HashMap<String, String>();
        Map<String, Object> localVarFormParams = new HashMap<String, Object>();

        if (redirectUrl != null) {
            localVarQueryParams.addAll(localVarApiClient.parameterToPair("redirect_url", redirectUrl));
        }

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
        return localVarApiClient.buildCall(basePath, localVarPath, "GET", localVarQueryParams, localVarCollectionQueryParams, localVarPostBody, localVarHeaderParams, localVarCookieParams, localVarFormParams, localVarAuthNames, _callback);
    }

    @SuppressWarnings("rawtypes")
    private okhttp3.Call getAgentToolOAuth2AuthorizeUrlValidateBeforeCall(Integer toolId, String redirectUrl, final ApiCallback _callback) throws ApiException {
        // verify the required parameter 'toolId' is set
        if (toolId == null) {
            throw new ApiException("Missing the required parameter 'toolId' when calling getAgentToolOAuth2AuthorizeUrl(Async)");
        }

        return getAgentToolOAuth2AuthorizeUrlCall(toolId, redirectUrl, _callback);

    }

    /**
     * Получение ссылки на OAuth2-авторизацию инструмента
     * Чтобы получить ссылку на OAuth2-авторизацию MCP-инструмента, отправьте GET-запрос на &#x60;/api/v1/cloud-ai/tools/oauth2/authorize-url/{tool_id}&#x60;.  Откройте &#x60;authorize_url&#x60; в браузере пользователя. Ссылка действует 30 минут. После входа и согласия провайдер вернёт пользователя на адрес возврата платформы &#x60;https://api.timeweb.cloud/api/v1/cloud-ai/oauth2/callback&#x60;. Платформа обменяет код на токены, переведёт инструмент в статус &#x60;active&#x60; и загрузит список методов.  Чтобы узнать результат, запрашивайте инструмент, пока его статус не сменится с &#x60;pending_auth&#x60; на &#x60;active&#x60;. Этим же запросом можно повторно авторизовать уже активный инструмент.
     * @param toolId ID инструмента (required)
     * @param redirectUrl Куда вернуть пользователя после авторизации. Принимаются только адреса на доменах &#x60;timeweb.cloud&#x60; и &#x60;timeweb.net&#x60; и их поддоменах; другие адреса молча игнорируются. После успешной авторизации к адресу добавляются &#x60;oauth_success&#x3D;true&amp;tool_id&#x3D;&lt;id&gt;&#x60;, после ошибки — &#x60;oauth_error&#x3D;&lt;текст&gt;&amp;tool_id&#x3D;&lt;id&gt;&#x60;. Без параметра страница возврата показывает результат в виде JSON. (optional)
     * @return AuthorizeUrl
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> Объект JSON c ключом &#x60;authorize_url&#x60; </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Некорректный запрос </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Не авторизован </td><td>  -  </td></tr>
        <tr><td> 403 </td><td> Запрещено </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Не найдено </td><td>  -  </td></tr>
        <tr><td> 429 </td><td> Слишком много запросов </td><td>  -  </td></tr>
        <tr><td> 500 </td><td> Внутренняя ошибка сервера </td><td>  -  </td></tr>
     </table>
     */
    public AuthorizeUrl getAgentToolOAuth2AuthorizeUrl(Integer toolId, String redirectUrl) throws ApiException {
        ApiResponse<AuthorizeUrl> localVarResp = getAgentToolOAuth2AuthorizeUrlWithHttpInfo(toolId, redirectUrl);
        return localVarResp.getData();
    }

    /**
     * Получение ссылки на OAuth2-авторизацию инструмента
     * Чтобы получить ссылку на OAuth2-авторизацию MCP-инструмента, отправьте GET-запрос на &#x60;/api/v1/cloud-ai/tools/oauth2/authorize-url/{tool_id}&#x60;.  Откройте &#x60;authorize_url&#x60; в браузере пользователя. Ссылка действует 30 минут. После входа и согласия провайдер вернёт пользователя на адрес возврата платформы &#x60;https://api.timeweb.cloud/api/v1/cloud-ai/oauth2/callback&#x60;. Платформа обменяет код на токены, переведёт инструмент в статус &#x60;active&#x60; и загрузит список методов.  Чтобы узнать результат, запрашивайте инструмент, пока его статус не сменится с &#x60;pending_auth&#x60; на &#x60;active&#x60;. Этим же запросом можно повторно авторизовать уже активный инструмент.
     * @param toolId ID инструмента (required)
     * @param redirectUrl Куда вернуть пользователя после авторизации. Принимаются только адреса на доменах &#x60;timeweb.cloud&#x60; и &#x60;timeweb.net&#x60; и их поддоменах; другие адреса молча игнорируются. После успешной авторизации к адресу добавляются &#x60;oauth_success&#x3D;true&amp;tool_id&#x3D;&lt;id&gt;&#x60;, после ошибки — &#x60;oauth_error&#x3D;&lt;текст&gt;&amp;tool_id&#x3D;&lt;id&gt;&#x60;. Без параметра страница возврата показывает результат в виде JSON. (optional)
     * @return ApiResponse&lt;AuthorizeUrl&gt;
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> Объект JSON c ключом &#x60;authorize_url&#x60; </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Некорректный запрос </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Не авторизован </td><td>  -  </td></tr>
        <tr><td> 403 </td><td> Запрещено </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Не найдено </td><td>  -  </td></tr>
        <tr><td> 429 </td><td> Слишком много запросов </td><td>  -  </td></tr>
        <tr><td> 500 </td><td> Внутренняя ошибка сервера </td><td>  -  </td></tr>
     </table>
     */
    public ApiResponse<AuthorizeUrl> getAgentToolOAuth2AuthorizeUrlWithHttpInfo(Integer toolId, String redirectUrl) throws ApiException {
        okhttp3.Call localVarCall = getAgentToolOAuth2AuthorizeUrlValidateBeforeCall(toolId, redirectUrl, null);
        Type localVarReturnType = new TypeToken<AuthorizeUrl>(){}.getType();
        return localVarApiClient.execute(localVarCall, localVarReturnType);
    }

    /**
     * Получение ссылки на OAuth2-авторизацию инструмента (asynchronously)
     * Чтобы получить ссылку на OAuth2-авторизацию MCP-инструмента, отправьте GET-запрос на &#x60;/api/v1/cloud-ai/tools/oauth2/authorize-url/{tool_id}&#x60;.  Откройте &#x60;authorize_url&#x60; в браузере пользователя. Ссылка действует 30 минут. После входа и согласия провайдер вернёт пользователя на адрес возврата платформы &#x60;https://api.timeweb.cloud/api/v1/cloud-ai/oauth2/callback&#x60;. Платформа обменяет код на токены, переведёт инструмент в статус &#x60;active&#x60; и загрузит список методов.  Чтобы узнать результат, запрашивайте инструмент, пока его статус не сменится с &#x60;pending_auth&#x60; на &#x60;active&#x60;. Этим же запросом можно повторно авторизовать уже активный инструмент.
     * @param toolId ID инструмента (required)
     * @param redirectUrl Куда вернуть пользователя после авторизации. Принимаются только адреса на доменах &#x60;timeweb.cloud&#x60; и &#x60;timeweb.net&#x60; и их поддоменах; другие адреса молча игнорируются. После успешной авторизации к адресу добавляются &#x60;oauth_success&#x3D;true&amp;tool_id&#x3D;&lt;id&gt;&#x60;, после ошибки — &#x60;oauth_error&#x3D;&lt;текст&gt;&amp;tool_id&#x3D;&lt;id&gt;&#x60;. Без параметра страница возврата показывает результат в виде JSON. (optional)
     * @param _callback The callback to be executed when the API call finishes
     * @return The request call
     * @throws ApiException If fail to process the API call, e.g. serializing the request body object
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> Объект JSON c ключом &#x60;authorize_url&#x60; </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Некорректный запрос </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Не авторизован </td><td>  -  </td></tr>
        <tr><td> 403 </td><td> Запрещено </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Не найдено </td><td>  -  </td></tr>
        <tr><td> 429 </td><td> Слишком много запросов </td><td>  -  </td></tr>
        <tr><td> 500 </td><td> Внутренняя ошибка сервера </td><td>  -  </td></tr>
     </table>
     */
    public okhttp3.Call getAgentToolOAuth2AuthorizeUrlAsync(Integer toolId, String redirectUrl, final ApiCallback<AuthorizeUrl> _callback) throws ApiException {

        okhttp3.Call localVarCall = getAgentToolOAuth2AuthorizeUrlValidateBeforeCall(toolId, redirectUrl, _callback);
        Type localVarReturnType = new TypeToken<AuthorizeUrl>(){}.getType();
        localVarApiClient.executeAsync(localVarCall, localVarReturnType, _callback);
        return localVarCall;
    }
    /**
     * Build call for getAgentToolTemplatePrices
     * @param locale Валюта цены: &#x60;ru&#x60; — в рублях, &#x60;en&#x60; — в долларах США. По умолчанию &#x60;ru&#x60;. (optional, default to ru)
     * @param _callback Callback for upload/download progress
     * @return Call to execute
     * @throws ApiException If fail to serialize the request body object
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> Объект JSON c ключами &#x60;system_tool_prices&#x60; и &#x60;meta&#x60; </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Некорректный запрос </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Не авторизован </td><td>  -  </td></tr>
        <tr><td> 403 </td><td> Запрещено </td><td>  -  </td></tr>
        <tr><td> 429 </td><td> Слишком много запросов </td><td>  -  </td></tr>
        <tr><td> 500 </td><td> Внутренняя ошибка сервера </td><td>  -  </td></tr>
     </table>
     */
    public okhttp3.Call getAgentToolTemplatePricesCall(String locale, final ApiCallback _callback) throws ApiException {
        String basePath = null;
        // Operation Servers
        String[] localBasePaths = new String[] {  };

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
        String localVarPath = "/api/v1/cloud-ai/tools/templates/prices";

        List<Pair> localVarQueryParams = new ArrayList<Pair>();
        List<Pair> localVarCollectionQueryParams = new ArrayList<Pair>();
        Map<String, String> localVarHeaderParams = new HashMap<String, String>();
        Map<String, String> localVarCookieParams = new HashMap<String, String>();
        Map<String, Object> localVarFormParams = new HashMap<String, Object>();

        if (locale != null) {
            localVarQueryParams.addAll(localVarApiClient.parameterToPair("locale", locale));
        }

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
        return localVarApiClient.buildCall(basePath, localVarPath, "GET", localVarQueryParams, localVarCollectionQueryParams, localVarPostBody, localVarHeaderParams, localVarCookieParams, localVarFormParams, localVarAuthNames, _callback);
    }

    @SuppressWarnings("rawtypes")
    private okhttp3.Call getAgentToolTemplatePricesValidateBeforeCall(String locale, final ApiCallback _callback) throws ApiException {
        return getAgentToolTemplatePricesCall(locale, _callback);

    }

    /**
     * Получение цен системных инструментов
     * Чтобы получить цены системных инструментов платформы, например веб-поиска, отправьте GET-запрос на &#x60;/api/v1/cloud-ai/tools/templates/prices&#x60;.
     * @param locale Валюта цены: &#x60;ru&#x60; — в рублях, &#x60;en&#x60; — в долларах США. По умолчанию &#x60;ru&#x60;. (optional, default to ru)
     * @return GetAgentToolTemplatePrices200Response
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> Объект JSON c ключами &#x60;system_tool_prices&#x60; и &#x60;meta&#x60; </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Некорректный запрос </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Не авторизован </td><td>  -  </td></tr>
        <tr><td> 403 </td><td> Запрещено </td><td>  -  </td></tr>
        <tr><td> 429 </td><td> Слишком много запросов </td><td>  -  </td></tr>
        <tr><td> 500 </td><td> Внутренняя ошибка сервера </td><td>  -  </td></tr>
     </table>
     */
    public GetAgentToolTemplatePrices200Response getAgentToolTemplatePrices(String locale) throws ApiException {
        ApiResponse<GetAgentToolTemplatePrices200Response> localVarResp = getAgentToolTemplatePricesWithHttpInfo(locale);
        return localVarResp.getData();
    }

    /**
     * Получение цен системных инструментов
     * Чтобы получить цены системных инструментов платформы, например веб-поиска, отправьте GET-запрос на &#x60;/api/v1/cloud-ai/tools/templates/prices&#x60;.
     * @param locale Валюта цены: &#x60;ru&#x60; — в рублях, &#x60;en&#x60; — в долларах США. По умолчанию &#x60;ru&#x60;. (optional, default to ru)
     * @return ApiResponse&lt;GetAgentToolTemplatePrices200Response&gt;
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> Объект JSON c ключами &#x60;system_tool_prices&#x60; и &#x60;meta&#x60; </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Некорректный запрос </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Не авторизован </td><td>  -  </td></tr>
        <tr><td> 403 </td><td> Запрещено </td><td>  -  </td></tr>
        <tr><td> 429 </td><td> Слишком много запросов </td><td>  -  </td></tr>
        <tr><td> 500 </td><td> Внутренняя ошибка сервера </td><td>  -  </td></tr>
     </table>
     */
    public ApiResponse<GetAgentToolTemplatePrices200Response> getAgentToolTemplatePricesWithHttpInfo(String locale) throws ApiException {
        okhttp3.Call localVarCall = getAgentToolTemplatePricesValidateBeforeCall(locale, null);
        Type localVarReturnType = new TypeToken<GetAgentToolTemplatePrices200Response>(){}.getType();
        return localVarApiClient.execute(localVarCall, localVarReturnType);
    }

    /**
     * Получение цен системных инструментов (asynchronously)
     * Чтобы получить цены системных инструментов платформы, например веб-поиска, отправьте GET-запрос на &#x60;/api/v1/cloud-ai/tools/templates/prices&#x60;.
     * @param locale Валюта цены: &#x60;ru&#x60; — в рублях, &#x60;en&#x60; — в долларах США. По умолчанию &#x60;ru&#x60;. (optional, default to ru)
     * @param _callback The callback to be executed when the API call finishes
     * @return The request call
     * @throws ApiException If fail to process the API call, e.g. serializing the request body object
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> Объект JSON c ключами &#x60;system_tool_prices&#x60; и &#x60;meta&#x60; </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Некорректный запрос </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Не авторизован </td><td>  -  </td></tr>
        <tr><td> 403 </td><td> Запрещено </td><td>  -  </td></tr>
        <tr><td> 429 </td><td> Слишком много запросов </td><td>  -  </td></tr>
        <tr><td> 500 </td><td> Внутренняя ошибка сервера </td><td>  -  </td></tr>
     </table>
     */
    public okhttp3.Call getAgentToolTemplatePricesAsync(String locale, final ApiCallback<GetAgentToolTemplatePrices200Response> _callback) throws ApiException {

        okhttp3.Call localVarCall = getAgentToolTemplatePricesValidateBeforeCall(locale, _callback);
        Type localVarReturnType = new TypeToken<GetAgentToolTemplatePrices200Response>(){}.getType();
        localVarApiClient.executeAsync(localVarCall, localVarReturnType, _callback);
        return localVarCall;
    }
    /**
     * Build call for getAgentToolTemplates
     * @param _callback Callback for upload/download progress
     * @return Call to execute
     * @throws ApiException If fail to serialize the request body object
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> Объект JSON c ключами &#x60;tool_templates&#x60; и &#x60;meta&#x60; </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Некорректный запрос </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Не авторизован </td><td>  -  </td></tr>
        <tr><td> 403 </td><td> Запрещено </td><td>  -  </td></tr>
        <tr><td> 429 </td><td> Слишком много запросов </td><td>  -  </td></tr>
        <tr><td> 500 </td><td> Внутренняя ошибка сервера </td><td>  -  </td></tr>
     </table>
     */
    public okhttp3.Call getAgentToolTemplatesCall(final ApiCallback _callback) throws ApiException {
        String basePath = null;
        // Operation Servers
        String[] localBasePaths = new String[] {  };

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
        String localVarPath = "/api/v1/cloud-ai/tools/templates/marketplace";

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
        return localVarApiClient.buildCall(basePath, localVarPath, "GET", localVarQueryParams, localVarCollectionQueryParams, localVarPostBody, localVarHeaderParams, localVarCookieParams, localVarFormParams, localVarAuthNames, _callback);
    }

    @SuppressWarnings("rawtypes")
    private okhttp3.Call getAgentToolTemplatesValidateBeforeCall(final ApiCallback _callback) throws ApiException {
        return getAgentToolTemplatesCall(_callback);

    }

    /**
     * Получение шаблонов MCP-инструментов из маркетплейса
     * Чтобы получить готовые интеграции маркетплейса, отправьте GET-запрос на &#x60;/api/v1/cloud-ai/tools/templates/marketplace&#x60;.  Сначала идут самые популярные шаблоны. Поле &#x60;config_schema&#x60; описывает, что нужно передать в &#x60;config_values&#x60; при установке.
     * @return GetAgentToolTemplates200Response
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> Объект JSON c ключами &#x60;tool_templates&#x60; и &#x60;meta&#x60; </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Некорректный запрос </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Не авторизован </td><td>  -  </td></tr>
        <tr><td> 403 </td><td> Запрещено </td><td>  -  </td></tr>
        <tr><td> 429 </td><td> Слишком много запросов </td><td>  -  </td></tr>
        <tr><td> 500 </td><td> Внутренняя ошибка сервера </td><td>  -  </td></tr>
     </table>
     */
    public GetAgentToolTemplates200Response getAgentToolTemplates() throws ApiException {
        ApiResponse<GetAgentToolTemplates200Response> localVarResp = getAgentToolTemplatesWithHttpInfo();
        return localVarResp.getData();
    }

    /**
     * Получение шаблонов MCP-инструментов из маркетплейса
     * Чтобы получить готовые интеграции маркетплейса, отправьте GET-запрос на &#x60;/api/v1/cloud-ai/tools/templates/marketplace&#x60;.  Сначала идут самые популярные шаблоны. Поле &#x60;config_schema&#x60; описывает, что нужно передать в &#x60;config_values&#x60; при установке.
     * @return ApiResponse&lt;GetAgentToolTemplates200Response&gt;
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> Объект JSON c ключами &#x60;tool_templates&#x60; и &#x60;meta&#x60; </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Некорректный запрос </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Не авторизован </td><td>  -  </td></tr>
        <tr><td> 403 </td><td> Запрещено </td><td>  -  </td></tr>
        <tr><td> 429 </td><td> Слишком много запросов </td><td>  -  </td></tr>
        <tr><td> 500 </td><td> Внутренняя ошибка сервера </td><td>  -  </td></tr>
     </table>
     */
    public ApiResponse<GetAgentToolTemplates200Response> getAgentToolTemplatesWithHttpInfo() throws ApiException {
        okhttp3.Call localVarCall = getAgentToolTemplatesValidateBeforeCall(null);
        Type localVarReturnType = new TypeToken<GetAgentToolTemplates200Response>(){}.getType();
        return localVarApiClient.execute(localVarCall, localVarReturnType);
    }

    /**
     * Получение шаблонов MCP-инструментов из маркетплейса (asynchronously)
     * Чтобы получить готовые интеграции маркетплейса, отправьте GET-запрос на &#x60;/api/v1/cloud-ai/tools/templates/marketplace&#x60;.  Сначала идут самые популярные шаблоны. Поле &#x60;config_schema&#x60; описывает, что нужно передать в &#x60;config_values&#x60; при установке.
     * @param _callback The callback to be executed when the API call finishes
     * @return The request call
     * @throws ApiException If fail to process the API call, e.g. serializing the request body object
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> Объект JSON c ключами &#x60;tool_templates&#x60; и &#x60;meta&#x60; </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Некорректный запрос </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Не авторизован </td><td>  -  </td></tr>
        <tr><td> 403 </td><td> Запрещено </td><td>  -  </td></tr>
        <tr><td> 429 </td><td> Слишком много запросов </td><td>  -  </td></tr>
        <tr><td> 500 </td><td> Внутренняя ошибка сервера </td><td>  -  </td></tr>
     </table>
     */
    public okhttp3.Call getAgentToolTemplatesAsync(final ApiCallback<GetAgentToolTemplates200Response> _callback) throws ApiException {

        okhttp3.Call localVarCall = getAgentToolTemplatesValidateBeforeCall(_callback);
        Type localVarReturnType = new TypeToken<GetAgentToolTemplates200Response>(){}.getType();
        localVarApiClient.executeAsync(localVarCall, localVarReturnType, _callback);
        return localVarCall;
    }
    /**
     * Build call for getAgentTools
     * @param _callback Callback for upload/download progress
     * @return Call to execute
     * @throws ApiException If fail to serialize the request body object
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> Объект JSON c ключами &#x60;tools&#x60; и &#x60;meta&#x60; </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Некорректный запрос </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Не авторизован </td><td>  -  </td></tr>
        <tr><td> 403 </td><td> Запрещено </td><td>  -  </td></tr>
        <tr><td> 429 </td><td> Слишком много запросов </td><td>  -  </td></tr>
        <tr><td> 500 </td><td> Внутренняя ошибка сервера </td><td>  -  </td></tr>
     </table>
     */
    public okhttp3.Call getAgentToolsCall(final ApiCallback _callback) throws ApiException {
        String basePath = null;
        // Operation Servers
        String[] localBasePaths = new String[] {  };

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
        String localVarPath = "/api/v1/cloud-ai/tools";

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
        return localVarApiClient.buildCall(basePath, localVarPath, "GET", localVarQueryParams, localVarCollectionQueryParams, localVarPostBody, localVarHeaderParams, localVarCookieParams, localVarFormParams, localVarAuthNames, _callback);
    }

    @SuppressWarnings("rawtypes")
    private okhttp3.Call getAgentToolsValidateBeforeCall(final ApiCallback _callback) throws ApiException {
        return getAgentToolsCall(_callback);

    }

    /**
     * Получение списка MCP-инструментов
     * Чтобы получить список MCP-инструментов аккаунта, отправьте GET-запрос на &#x60;/api/v1/cloud-ai/tools&#x60;.  В списке есть и собственные MCP-серверы, и инструменты, установленные из маркетплейса. Сначала идут новые. Удалённые инструменты не возвращаются. Секреты в &#x60;auth_data&#x60; заменены на &#x60;********&#x60;.
     * @return GetAgentTools200Response
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> Объект JSON c ключами &#x60;tools&#x60; и &#x60;meta&#x60; </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Некорректный запрос </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Не авторизован </td><td>  -  </td></tr>
        <tr><td> 403 </td><td> Запрещено </td><td>  -  </td></tr>
        <tr><td> 429 </td><td> Слишком много запросов </td><td>  -  </td></tr>
        <tr><td> 500 </td><td> Внутренняя ошибка сервера </td><td>  -  </td></tr>
     </table>
     */
    public GetAgentTools200Response getAgentTools() throws ApiException {
        ApiResponse<GetAgentTools200Response> localVarResp = getAgentToolsWithHttpInfo();
        return localVarResp.getData();
    }

    /**
     * Получение списка MCP-инструментов
     * Чтобы получить список MCP-инструментов аккаунта, отправьте GET-запрос на &#x60;/api/v1/cloud-ai/tools&#x60;.  В списке есть и собственные MCP-серверы, и инструменты, установленные из маркетплейса. Сначала идут новые. Удалённые инструменты не возвращаются. Секреты в &#x60;auth_data&#x60; заменены на &#x60;********&#x60;.
     * @return ApiResponse&lt;GetAgentTools200Response&gt;
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> Объект JSON c ключами &#x60;tools&#x60; и &#x60;meta&#x60; </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Некорректный запрос </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Не авторизован </td><td>  -  </td></tr>
        <tr><td> 403 </td><td> Запрещено </td><td>  -  </td></tr>
        <tr><td> 429 </td><td> Слишком много запросов </td><td>  -  </td></tr>
        <tr><td> 500 </td><td> Внутренняя ошибка сервера </td><td>  -  </td></tr>
     </table>
     */
    public ApiResponse<GetAgentTools200Response> getAgentToolsWithHttpInfo() throws ApiException {
        okhttp3.Call localVarCall = getAgentToolsValidateBeforeCall(null);
        Type localVarReturnType = new TypeToken<GetAgentTools200Response>(){}.getType();
        return localVarApiClient.execute(localVarCall, localVarReturnType);
    }

    /**
     * Получение списка MCP-инструментов (asynchronously)
     * Чтобы получить список MCP-инструментов аккаунта, отправьте GET-запрос на &#x60;/api/v1/cloud-ai/tools&#x60;.  В списке есть и собственные MCP-серверы, и инструменты, установленные из маркетплейса. Сначала идут новые. Удалённые инструменты не возвращаются. Секреты в &#x60;auth_data&#x60; заменены на &#x60;********&#x60;.
     * @param _callback The callback to be executed when the API call finishes
     * @return The request call
     * @throws ApiException If fail to process the API call, e.g. serializing the request body object
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> Объект JSON c ключами &#x60;tools&#x60; и &#x60;meta&#x60; </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Некорректный запрос </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Не авторизован </td><td>  -  </td></tr>
        <tr><td> 403 </td><td> Запрещено </td><td>  -  </td></tr>
        <tr><td> 429 </td><td> Слишком много запросов </td><td>  -  </td></tr>
        <tr><td> 500 </td><td> Внутренняя ошибка сервера </td><td>  -  </td></tr>
     </table>
     */
    public okhttp3.Call getAgentToolsAsync(final ApiCallback<GetAgentTools200Response> _callback) throws ApiException {

        okhttp3.Call localVarCall = getAgentToolsValidateBeforeCall(_callback);
        Type localVarReturnType = new TypeToken<GetAgentTools200Response>(){}.getType();
        localVarApiClient.executeAsync(localVarCall, localVarReturnType, _callback);
        return localVarCall;
    }
    /**
     * Build call for getAgentToolsByAgent
     * @param agentId ID агента (required)
     * @param _callback Callback for upload/download progress
     * @return Call to execute
     * @throws ApiException If fail to serialize the request body object
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> Объект JSON c ключами &#x60;agent_tools&#x60; и &#x60;meta&#x60; </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Некорректный запрос </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Не авторизован </td><td>  -  </td></tr>
        <tr><td> 403 </td><td> Запрещено </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Не найдено </td><td>  -  </td></tr>
        <tr><td> 429 </td><td> Слишком много запросов </td><td>  -  </td></tr>
        <tr><td> 500 </td><td> Внутренняя ошибка сервера </td><td>  -  </td></tr>
     </table>
     */
    public okhttp3.Call getAgentToolsByAgentCall(Integer agentId, final ApiCallback _callback) throws ApiException {
        String basePath = null;
        // Operation Servers
        String[] localBasePaths = new String[] {  };

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
        String localVarPath = "/api/v1/cloud-ai/agents/{agent_id}/tools"
            .replace("{" + "agent_id" + "}", localVarApiClient.escapeString(agentId.toString()));

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
        return localVarApiClient.buildCall(basePath, localVarPath, "GET", localVarQueryParams, localVarCollectionQueryParams, localVarPostBody, localVarHeaderParams, localVarCookieParams, localVarFormParams, localVarAuthNames, _callback);
    }

    @SuppressWarnings("rawtypes")
    private okhttp3.Call getAgentToolsByAgentValidateBeforeCall(Integer agentId, final ApiCallback _callback) throws ApiException {
        // verify the required parameter 'agentId' is set
        if (agentId == null) {
            throw new ApiException("Missing the required parameter 'agentId' when calling getAgentToolsByAgent(Async)");
        }

        return getAgentToolsByAgentCall(agentId, _callback);

    }

    /**
     * Получение MCP-инструментов агента
     * Чтобы получить MCP-инструменты, подключённые к агенту, отправьте GET-запрос на &#x60;/api/v1/cloud-ai/agents/{agent_id}/tools&#x60;.  В ответе — инструменты целиком, в том же виде, что и в списке инструментов аккаунта. Списка разрешённых агенту методов в ответе нет.
     * @param agentId ID агента (required)
     * @return GetAgentToolsByAgent200Response
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> Объект JSON c ключами &#x60;agent_tools&#x60; и &#x60;meta&#x60; </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Некорректный запрос </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Не авторизован </td><td>  -  </td></tr>
        <tr><td> 403 </td><td> Запрещено </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Не найдено </td><td>  -  </td></tr>
        <tr><td> 429 </td><td> Слишком много запросов </td><td>  -  </td></tr>
        <tr><td> 500 </td><td> Внутренняя ошибка сервера </td><td>  -  </td></tr>
     </table>
     */
    public GetAgentToolsByAgent200Response getAgentToolsByAgent(Integer agentId) throws ApiException {
        ApiResponse<GetAgentToolsByAgent200Response> localVarResp = getAgentToolsByAgentWithHttpInfo(agentId);
        return localVarResp.getData();
    }

    /**
     * Получение MCP-инструментов агента
     * Чтобы получить MCP-инструменты, подключённые к агенту, отправьте GET-запрос на &#x60;/api/v1/cloud-ai/agents/{agent_id}/tools&#x60;.  В ответе — инструменты целиком, в том же виде, что и в списке инструментов аккаунта. Списка разрешённых агенту методов в ответе нет.
     * @param agentId ID агента (required)
     * @return ApiResponse&lt;GetAgentToolsByAgent200Response&gt;
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> Объект JSON c ключами &#x60;agent_tools&#x60; и &#x60;meta&#x60; </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Некорректный запрос </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Не авторизован </td><td>  -  </td></tr>
        <tr><td> 403 </td><td> Запрещено </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Не найдено </td><td>  -  </td></tr>
        <tr><td> 429 </td><td> Слишком много запросов </td><td>  -  </td></tr>
        <tr><td> 500 </td><td> Внутренняя ошибка сервера </td><td>  -  </td></tr>
     </table>
     */
    public ApiResponse<GetAgentToolsByAgent200Response> getAgentToolsByAgentWithHttpInfo(Integer agentId) throws ApiException {
        okhttp3.Call localVarCall = getAgentToolsByAgentValidateBeforeCall(agentId, null);
        Type localVarReturnType = new TypeToken<GetAgentToolsByAgent200Response>(){}.getType();
        return localVarApiClient.execute(localVarCall, localVarReturnType);
    }

    /**
     * Получение MCP-инструментов агента (asynchronously)
     * Чтобы получить MCP-инструменты, подключённые к агенту, отправьте GET-запрос на &#x60;/api/v1/cloud-ai/agents/{agent_id}/tools&#x60;.  В ответе — инструменты целиком, в том же виде, что и в списке инструментов аккаунта. Списка разрешённых агенту методов в ответе нет.
     * @param agentId ID агента (required)
     * @param _callback The callback to be executed when the API call finishes
     * @return The request call
     * @throws ApiException If fail to process the API call, e.g. serializing the request body object
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> Объект JSON c ключами &#x60;agent_tools&#x60; и &#x60;meta&#x60; </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Некорректный запрос </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Не авторизован </td><td>  -  </td></tr>
        <tr><td> 403 </td><td> Запрещено </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Не найдено </td><td>  -  </td></tr>
        <tr><td> 429 </td><td> Слишком много запросов </td><td>  -  </td></tr>
        <tr><td> 500 </td><td> Внутренняя ошибка сервера </td><td>  -  </td></tr>
     </table>
     */
    public okhttp3.Call getAgentToolsByAgentAsync(Integer agentId, final ApiCallback<GetAgentToolsByAgent200Response> _callback) throws ApiException {

        okhttp3.Call localVarCall = getAgentToolsByAgentValidateBeforeCall(agentId, _callback);
        Type localVarReturnType = new TypeToken<GetAgentToolsByAgent200Response>(){}.getType();
        localVarApiClient.executeAsync(localVarCall, localVarReturnType, _callback);
        return localVarCall;
    }
    /**
     * Build call for installAgentToolFromTemplate
     * @param installTool  (required)
     * @param _callback Callback for upload/download progress
     * @return Call to execute
     * @throws ApiException If fail to serialize the request body object
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 201 </td><td> Объект JSON c ключом &#x60;tool&#x60; </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Некорректный запрос </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Не авторизован </td><td>  -  </td></tr>
        <tr><td> 403 </td><td> Запрещено </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Не найдено </td><td>  -  </td></tr>
        <tr><td> 429 </td><td> Слишком много запросов </td><td>  -  </td></tr>
        <tr><td> 500 </td><td> Внутренняя ошибка сервера </td><td>  -  </td></tr>
     </table>
     */
    public okhttp3.Call installAgentToolFromTemplateCall(InstallTool installTool, final ApiCallback _callback) throws ApiException {
        String basePath = null;
        // Operation Servers
        String[] localBasePaths = new String[] {  };

        // Determine Base Path to Use
        if (localCustomBaseUrl != null){
            basePath = localCustomBaseUrl;
        } else if ( localBasePaths.length > 0 ) {
            basePath = localBasePaths[localHostIndex];
        } else {
            basePath = null;
        }

        Object localVarPostBody = installTool;

        // create path and map variables
        String localVarPath = "/api/v1/cloud-ai/tools/install";

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
    private okhttp3.Call installAgentToolFromTemplateValidateBeforeCall(InstallTool installTool, final ApiCallback _callback) throws ApiException {
        // verify the required parameter 'installTool' is set
        if (installTool == null) {
            throw new ApiException("Missing the required parameter 'installTool' when calling installAgentToolFromTemplate(Async)");
        }

        return installAgentToolFromTemplateCall(installTool, _callback);

    }

    /**
     * Установка MCP-инструмента из маркетплейса
     * Чтобы установить MCP-инструмент из шаблона маркетплейса, отправьте POST-запрос на &#x60;/api/v1/cloud-ai/tools/install&#x60;.  Значения полей из &#x60;config_schema&#x60; шаблона передайте в &#x60;config_values&#x60;. Платформа соберёт из них заголовки авторизации, подключится к MCP-серверу и сохранит список методов. Если у шаблона есть &#x60;config_schema&#x60;, поле &#x60;auth_data&#x60; не используется. Если подключиться к серверу не удалось, вернётся 400 с &#x60;error_code&#x60; &#x60;invalid_methods_provided&#x60;.  Один шаблон можно установить несколько раз, например с разными ключами.
     * @param installTool  (required)
     * @return CreateAgentTool201Response
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 201 </td><td> Объект JSON c ключом &#x60;tool&#x60; </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Некорректный запрос </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Не авторизован </td><td>  -  </td></tr>
        <tr><td> 403 </td><td> Запрещено </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Не найдено </td><td>  -  </td></tr>
        <tr><td> 429 </td><td> Слишком много запросов </td><td>  -  </td></tr>
        <tr><td> 500 </td><td> Внутренняя ошибка сервера </td><td>  -  </td></tr>
     </table>
     */
    public CreateAgentTool201Response installAgentToolFromTemplate(InstallTool installTool) throws ApiException {
        ApiResponse<CreateAgentTool201Response> localVarResp = installAgentToolFromTemplateWithHttpInfo(installTool);
        return localVarResp.getData();
    }

    /**
     * Установка MCP-инструмента из маркетплейса
     * Чтобы установить MCP-инструмент из шаблона маркетплейса, отправьте POST-запрос на &#x60;/api/v1/cloud-ai/tools/install&#x60;.  Значения полей из &#x60;config_schema&#x60; шаблона передайте в &#x60;config_values&#x60;. Платформа соберёт из них заголовки авторизации, подключится к MCP-серверу и сохранит список методов. Если у шаблона есть &#x60;config_schema&#x60;, поле &#x60;auth_data&#x60; не используется. Если подключиться к серверу не удалось, вернётся 400 с &#x60;error_code&#x60; &#x60;invalid_methods_provided&#x60;.  Один шаблон можно установить несколько раз, например с разными ключами.
     * @param installTool  (required)
     * @return ApiResponse&lt;CreateAgentTool201Response&gt;
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 201 </td><td> Объект JSON c ключом &#x60;tool&#x60; </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Некорректный запрос </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Не авторизован </td><td>  -  </td></tr>
        <tr><td> 403 </td><td> Запрещено </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Не найдено </td><td>  -  </td></tr>
        <tr><td> 429 </td><td> Слишком много запросов </td><td>  -  </td></tr>
        <tr><td> 500 </td><td> Внутренняя ошибка сервера </td><td>  -  </td></tr>
     </table>
     */
    public ApiResponse<CreateAgentTool201Response> installAgentToolFromTemplateWithHttpInfo(InstallTool installTool) throws ApiException {
        okhttp3.Call localVarCall = installAgentToolFromTemplateValidateBeforeCall(installTool, null);
        Type localVarReturnType = new TypeToken<CreateAgentTool201Response>(){}.getType();
        return localVarApiClient.execute(localVarCall, localVarReturnType);
    }

    /**
     * Установка MCP-инструмента из маркетплейса (asynchronously)
     * Чтобы установить MCP-инструмент из шаблона маркетплейса, отправьте POST-запрос на &#x60;/api/v1/cloud-ai/tools/install&#x60;.  Значения полей из &#x60;config_schema&#x60; шаблона передайте в &#x60;config_values&#x60;. Платформа соберёт из них заголовки авторизации, подключится к MCP-серверу и сохранит список методов. Если у шаблона есть &#x60;config_schema&#x60;, поле &#x60;auth_data&#x60; не используется. Если подключиться к серверу не удалось, вернётся 400 с &#x60;error_code&#x60; &#x60;invalid_methods_provided&#x60;.  Один шаблон можно установить несколько раз, например с разными ключами.
     * @param installTool  (required)
     * @param _callback The callback to be executed when the API call finishes
     * @return The request call
     * @throws ApiException If fail to process the API call, e.g. serializing the request body object
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 201 </td><td> Объект JSON c ключом &#x60;tool&#x60; </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Некорректный запрос </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Не авторизован </td><td>  -  </td></tr>
        <tr><td> 403 </td><td> Запрещено </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Не найдено </td><td>  -  </td></tr>
        <tr><td> 429 </td><td> Слишком много запросов </td><td>  -  </td></tr>
        <tr><td> 500 </td><td> Внутренняя ошибка сервера </td><td>  -  </td></tr>
     </table>
     */
    public okhttp3.Call installAgentToolFromTemplateAsync(InstallTool installTool, final ApiCallback<CreateAgentTool201Response> _callback) throws ApiException {

        okhttp3.Call localVarCall = installAgentToolFromTemplateValidateBeforeCall(installTool, _callback);
        Type localVarReturnType = new TypeToken<CreateAgentTool201Response>(){}.getType();
        localVarApiClient.executeAsync(localVarCall, localVarReturnType, _callback);
        return localVarCall;
    }
    /**
     * Build call for refreshAgentToolMethods
     * @param id ID инструмента (required)
     * @param _callback Callback for upload/download progress
     * @return Call to execute
     * @throws ApiException If fail to serialize the request body object
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 201 </td><td> Объект JSON c ключом &#x60;tool&#x60; </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Некорректный запрос </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Не авторизован </td><td>  -  </td></tr>
        <tr><td> 403 </td><td> Запрещено </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Не найдено </td><td>  -  </td></tr>
        <tr><td> 429 </td><td> Слишком много запросов </td><td>  -  </td></tr>
        <tr><td> 500 </td><td> Внутренняя ошибка сервера </td><td>  -  </td></tr>
     </table>
     */
    public okhttp3.Call refreshAgentToolMethodsCall(Integer id, final ApiCallback _callback) throws ApiException {
        String basePath = null;
        // Operation Servers
        String[] localBasePaths = new String[] {  };

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
        String localVarPath = "/api/v1/cloud-ai/tools/{id}/refresh-methods"
            .replace("{" + "id" + "}", localVarApiClient.escapeString(id.toString()));

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
        return localVarApiClient.buildCall(basePath, localVarPath, "POST", localVarQueryParams, localVarCollectionQueryParams, localVarPostBody, localVarHeaderParams, localVarCookieParams, localVarFormParams, localVarAuthNames, _callback);
    }

    @SuppressWarnings("rawtypes")
    private okhttp3.Call refreshAgentToolMethodsValidateBeforeCall(Integer id, final ApiCallback _callback) throws ApiException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new ApiException("Missing the required parameter 'id' when calling refreshAgentToolMethods(Async)");
        }

        return refreshAgentToolMethodsCall(id, _callback);

    }

    /**
     * Обновление списка методов MCP-инструмента
     * Чтобы заново получить список методов с MCP-сервера, отправьте POST-запрос на &#x60;/api/v1/cloud-ai/tools/{id}/refresh-methods&#x60;.  Список &#x60;methods&#x60; заменяется полученным с сервера, а методы, которых больше нет, убираются из разрешённых у всех агентов. Если обновление прошло успешно, инструмент переходит в статус &#x60;active&#x60;. Если сервер недоступен, инструмент переходит в статус &#x60;error&#x60; с причиной в &#x60;status_info&#x60;, а запрос возвращает 400 с &#x60;error_code&#x60; &#x60;invalid_methods_provided&#x60;.
     * @param id ID инструмента (required)
     * @return CreateAgentTool201Response
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 201 </td><td> Объект JSON c ключом &#x60;tool&#x60; </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Некорректный запрос </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Не авторизован </td><td>  -  </td></tr>
        <tr><td> 403 </td><td> Запрещено </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Не найдено </td><td>  -  </td></tr>
        <tr><td> 429 </td><td> Слишком много запросов </td><td>  -  </td></tr>
        <tr><td> 500 </td><td> Внутренняя ошибка сервера </td><td>  -  </td></tr>
     </table>
     */
    public CreateAgentTool201Response refreshAgentToolMethods(Integer id) throws ApiException {
        ApiResponse<CreateAgentTool201Response> localVarResp = refreshAgentToolMethodsWithHttpInfo(id);
        return localVarResp.getData();
    }

    /**
     * Обновление списка методов MCP-инструмента
     * Чтобы заново получить список методов с MCP-сервера, отправьте POST-запрос на &#x60;/api/v1/cloud-ai/tools/{id}/refresh-methods&#x60;.  Список &#x60;methods&#x60; заменяется полученным с сервера, а методы, которых больше нет, убираются из разрешённых у всех агентов. Если обновление прошло успешно, инструмент переходит в статус &#x60;active&#x60;. Если сервер недоступен, инструмент переходит в статус &#x60;error&#x60; с причиной в &#x60;status_info&#x60;, а запрос возвращает 400 с &#x60;error_code&#x60; &#x60;invalid_methods_provided&#x60;.
     * @param id ID инструмента (required)
     * @return ApiResponse&lt;CreateAgentTool201Response&gt;
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 201 </td><td> Объект JSON c ключом &#x60;tool&#x60; </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Некорректный запрос </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Не авторизован </td><td>  -  </td></tr>
        <tr><td> 403 </td><td> Запрещено </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Не найдено </td><td>  -  </td></tr>
        <tr><td> 429 </td><td> Слишком много запросов </td><td>  -  </td></tr>
        <tr><td> 500 </td><td> Внутренняя ошибка сервера </td><td>  -  </td></tr>
     </table>
     */
    public ApiResponse<CreateAgentTool201Response> refreshAgentToolMethodsWithHttpInfo(Integer id) throws ApiException {
        okhttp3.Call localVarCall = refreshAgentToolMethodsValidateBeforeCall(id, null);
        Type localVarReturnType = new TypeToken<CreateAgentTool201Response>(){}.getType();
        return localVarApiClient.execute(localVarCall, localVarReturnType);
    }

    /**
     * Обновление списка методов MCP-инструмента (asynchronously)
     * Чтобы заново получить список методов с MCP-сервера, отправьте POST-запрос на &#x60;/api/v1/cloud-ai/tools/{id}/refresh-methods&#x60;.  Список &#x60;methods&#x60; заменяется полученным с сервера, а методы, которых больше нет, убираются из разрешённых у всех агентов. Если обновление прошло успешно, инструмент переходит в статус &#x60;active&#x60;. Если сервер недоступен, инструмент переходит в статус &#x60;error&#x60; с причиной в &#x60;status_info&#x60;, а запрос возвращает 400 с &#x60;error_code&#x60; &#x60;invalid_methods_provided&#x60;.
     * @param id ID инструмента (required)
     * @param _callback The callback to be executed when the API call finishes
     * @return The request call
     * @throws ApiException If fail to process the API call, e.g. serializing the request body object
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 201 </td><td> Объект JSON c ключом &#x60;tool&#x60; </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Некорректный запрос </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Не авторизован </td><td>  -  </td></tr>
        <tr><td> 403 </td><td> Запрещено </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Не найдено </td><td>  -  </td></tr>
        <tr><td> 429 </td><td> Слишком много запросов </td><td>  -  </td></tr>
        <tr><td> 500 </td><td> Внутренняя ошибка сервера </td><td>  -  </td></tr>
     </table>
     */
    public okhttp3.Call refreshAgentToolMethodsAsync(Integer id, final ApiCallback<CreateAgentTool201Response> _callback) throws ApiException {

        okhttp3.Call localVarCall = refreshAgentToolMethodsValidateBeforeCall(id, _callback);
        Type localVarReturnType = new TypeToken<CreateAgentTool201Response>(){}.getType();
        localVarApiClient.executeAsync(localVarCall, localVarReturnType, _callback);
        return localVarCall;
    }
    /**
     * Build call for updateAgentTool
     * @param id ID инструмента (required)
     * @param updateTool  (required)
     * @param _callback Callback for upload/download progress
     * @return Call to execute
     * @throws ApiException If fail to serialize the request body object
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> Объект JSON c ключом &#x60;tool&#x60; </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Некорректный запрос </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Не авторизован </td><td>  -  </td></tr>
        <tr><td> 403 </td><td> Запрещено </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Не найдено </td><td>  -  </td></tr>
        <tr><td> 429 </td><td> Слишком много запросов </td><td>  -  </td></tr>
        <tr><td> 500 </td><td> Внутренняя ошибка сервера </td><td>  -  </td></tr>
     </table>
     */
    public okhttp3.Call updateAgentToolCall(Integer id, UpdateTool updateTool, final ApiCallback _callback) throws ApiException {
        String basePath = null;
        // Operation Servers
        String[] localBasePaths = new String[] {  };

        // Determine Base Path to Use
        if (localCustomBaseUrl != null){
            basePath = localCustomBaseUrl;
        } else if ( localBasePaths.length > 0 ) {
            basePath = localBasePaths[localHostIndex];
        } else {
            basePath = null;
        }

        Object localVarPostBody = updateTool;

        // create path and map variables
        String localVarPath = "/api/v1/cloud-ai/tools/{id}"
            .replace("{" + "id" + "}", localVarApiClient.escapeString(id.toString()));

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
            "application/json"
        };
        final String localVarContentType = localVarApiClient.selectHeaderContentType(localVarContentTypes);
        if (localVarContentType != null) {
            localVarHeaderParams.put("Content-Type", localVarContentType);
        }

        String[] localVarAuthNames = new String[] { "Bearer" };
        return localVarApiClient.buildCall(basePath, localVarPath, "PUT", localVarQueryParams, localVarCollectionQueryParams, localVarPostBody, localVarHeaderParams, localVarCookieParams, localVarFormParams, localVarAuthNames, _callback);
    }

    @SuppressWarnings("rawtypes")
    private okhttp3.Call updateAgentToolValidateBeforeCall(Integer id, UpdateTool updateTool, final ApiCallback _callback) throws ApiException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new ApiException("Missing the required parameter 'id' when calling updateAgentTool(Async)");
        }

        // verify the required parameter 'updateTool' is set
        if (updateTool == null) {
            throw new ApiException("Missing the required parameter 'updateTool' when calling updateAgentTool(Async)");
        }

        return updateAgentToolCall(id, updateTool, _callback);

    }

    /**
     * Изменение MCP-инструмента
     * Чтобы изменить MCP-инструмент, отправьте PUT-запрос на &#x60;/api/v1/cloud-ai/tools/{id}&#x60;, передав только изменяемые поля.  Если передать в &#x60;auth_data&#x60; секрет &#x60;********&#x60; из ответа API, сохранённый секрет не изменится. Изменение адреса или авторизации не обновляет список методов: после него вызовите обновление методов. Статус &#x60;inactive&#x60; выключает инструмент у всех агентов, к которым он подключён.
     * @param id ID инструмента (required)
     * @param updateTool  (required)
     * @return CreateAgentTool201Response
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> Объект JSON c ключом &#x60;tool&#x60; </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Некорректный запрос </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Не авторизован </td><td>  -  </td></tr>
        <tr><td> 403 </td><td> Запрещено </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Не найдено </td><td>  -  </td></tr>
        <tr><td> 429 </td><td> Слишком много запросов </td><td>  -  </td></tr>
        <tr><td> 500 </td><td> Внутренняя ошибка сервера </td><td>  -  </td></tr>
     </table>
     */
    public CreateAgentTool201Response updateAgentTool(Integer id, UpdateTool updateTool) throws ApiException {
        ApiResponse<CreateAgentTool201Response> localVarResp = updateAgentToolWithHttpInfo(id, updateTool);
        return localVarResp.getData();
    }

    /**
     * Изменение MCP-инструмента
     * Чтобы изменить MCP-инструмент, отправьте PUT-запрос на &#x60;/api/v1/cloud-ai/tools/{id}&#x60;, передав только изменяемые поля.  Если передать в &#x60;auth_data&#x60; секрет &#x60;********&#x60; из ответа API, сохранённый секрет не изменится. Изменение адреса или авторизации не обновляет список методов: после него вызовите обновление методов. Статус &#x60;inactive&#x60; выключает инструмент у всех агентов, к которым он подключён.
     * @param id ID инструмента (required)
     * @param updateTool  (required)
     * @return ApiResponse&lt;CreateAgentTool201Response&gt;
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> Объект JSON c ключом &#x60;tool&#x60; </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Некорректный запрос </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Не авторизован </td><td>  -  </td></tr>
        <tr><td> 403 </td><td> Запрещено </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Не найдено </td><td>  -  </td></tr>
        <tr><td> 429 </td><td> Слишком много запросов </td><td>  -  </td></tr>
        <tr><td> 500 </td><td> Внутренняя ошибка сервера </td><td>  -  </td></tr>
     </table>
     */
    public ApiResponse<CreateAgentTool201Response> updateAgentToolWithHttpInfo(Integer id, UpdateTool updateTool) throws ApiException {
        okhttp3.Call localVarCall = updateAgentToolValidateBeforeCall(id, updateTool, null);
        Type localVarReturnType = new TypeToken<CreateAgentTool201Response>(){}.getType();
        return localVarApiClient.execute(localVarCall, localVarReturnType);
    }

    /**
     * Изменение MCP-инструмента (asynchronously)
     * Чтобы изменить MCP-инструмент, отправьте PUT-запрос на &#x60;/api/v1/cloud-ai/tools/{id}&#x60;, передав только изменяемые поля.  Если передать в &#x60;auth_data&#x60; секрет &#x60;********&#x60; из ответа API, сохранённый секрет не изменится. Изменение адреса или авторизации не обновляет список методов: после него вызовите обновление методов. Статус &#x60;inactive&#x60; выключает инструмент у всех агентов, к которым он подключён.
     * @param id ID инструмента (required)
     * @param updateTool  (required)
     * @param _callback The callback to be executed when the API call finishes
     * @return The request call
     * @throws ApiException If fail to process the API call, e.g. serializing the request body object
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> Объект JSON c ключом &#x60;tool&#x60; </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Некорректный запрос </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Не авторизован </td><td>  -  </td></tr>
        <tr><td> 403 </td><td> Запрещено </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Не найдено </td><td>  -  </td></tr>
        <tr><td> 429 </td><td> Слишком много запросов </td><td>  -  </td></tr>
        <tr><td> 500 </td><td> Внутренняя ошибка сервера </td><td>  -  </td></tr>
     </table>
     */
    public okhttp3.Call updateAgentToolAsync(Integer id, UpdateTool updateTool, final ApiCallback<CreateAgentTool201Response> _callback) throws ApiException {

        okhttp3.Call localVarCall = updateAgentToolValidateBeforeCall(id, updateTool, _callback);
        Type localVarReturnType = new TypeToken<CreateAgentTool201Response>(){}.getType();
        localVarApiClient.executeAsync(localVarCall, localVarReturnType, _callback);
        return localVarCall;
    }
    /**
     * Build call for updateAgentToolConnection
     * @param agentId ID агента (required)
     * @param toolId ID инструмента (required)
     * @param updateAgentTool  (required)
     * @param _callback Callback for upload/download progress
     * @return Call to execute
     * @throws ApiException If fail to serialize the request body object
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> Запрос выполнен. Тело ответа пустое </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Некорректный запрос </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Не авторизован </td><td>  -  </td></tr>
        <tr><td> 403 </td><td> Запрещено </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Не найдено </td><td>  -  </td></tr>
        <tr><td> 429 </td><td> Слишком много запросов </td><td>  -  </td></tr>
        <tr><td> 500 </td><td> Внутренняя ошибка сервера </td><td>  -  </td></tr>
     </table>
     */
    public okhttp3.Call updateAgentToolConnectionCall(Integer agentId, Integer toolId, UpdateAgentTool updateAgentTool, final ApiCallback _callback) throws ApiException {
        String basePath = null;
        // Operation Servers
        String[] localBasePaths = new String[] {  };

        // Determine Base Path to Use
        if (localCustomBaseUrl != null){
            basePath = localCustomBaseUrl;
        } else if ( localBasePaths.length > 0 ) {
            basePath = localBasePaths[localHostIndex];
        } else {
            basePath = null;
        }

        Object localVarPostBody = updateAgentTool;

        // create path and map variables
        String localVarPath = "/api/v1/cloud-ai/agents/{agent_id}/tools/{tool_id}"
            .replace("{" + "agent_id" + "}", localVarApiClient.escapeString(agentId.toString()))
            .replace("{" + "tool_id" + "}", localVarApiClient.escapeString(toolId.toString()));

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
            "application/json"
        };
        final String localVarContentType = localVarApiClient.selectHeaderContentType(localVarContentTypes);
        if (localVarContentType != null) {
            localVarHeaderParams.put("Content-Type", localVarContentType);
        }

        String[] localVarAuthNames = new String[] { "Bearer" };
        return localVarApiClient.buildCall(basePath, localVarPath, "PUT", localVarQueryParams, localVarCollectionQueryParams, localVarPostBody, localVarHeaderParams, localVarCookieParams, localVarFormParams, localVarAuthNames, _callback);
    }

    @SuppressWarnings("rawtypes")
    private okhttp3.Call updateAgentToolConnectionValidateBeforeCall(Integer agentId, Integer toolId, UpdateAgentTool updateAgentTool, final ApiCallback _callback) throws ApiException {
        // verify the required parameter 'agentId' is set
        if (agentId == null) {
            throw new ApiException("Missing the required parameter 'agentId' when calling updateAgentToolConnection(Async)");
        }

        // verify the required parameter 'toolId' is set
        if (toolId == null) {
            throw new ApiException("Missing the required parameter 'toolId' when calling updateAgentToolConnection(Async)");
        }

        // verify the required parameter 'updateAgentTool' is set
        if (updateAgentTool == null) {
            throw new ApiException("Missing the required parameter 'updateAgentTool' when calling updateAgentToolConnection(Async)");
        }

        return updateAgentToolConnectionCall(agentId, toolId, updateAgentTool, _callback);

    }

    /**
     * Изменение разрешённых методов MCP-инструмента у агента
     * Чтобы изменить список методов, которые агенту разрешено вызывать, отправьте PUT-запрос на &#x60;/api/v1/cloud-ai/agents/{agent_id}/tools/{tool_id}&#x60;.  Список заменяется целиком. Подходят только имена из &#x60;methods[].name&#x60; инструмента, иначе вернётся 400 с &#x60;error_code&#x60; &#x60;invalid_methods_provided&#x60;. Если инструмент не подключён к агенту, вернётся 404 с &#x60;error_code&#x60; &#x60;agent-tool_link_not_found&#x60;.
     * @param agentId ID агента (required)
     * @param toolId ID инструмента (required)
     * @param updateAgentTool  (required)
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> Запрос выполнен. Тело ответа пустое </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Некорректный запрос </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Не авторизован </td><td>  -  </td></tr>
        <tr><td> 403 </td><td> Запрещено </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Не найдено </td><td>  -  </td></tr>
        <tr><td> 429 </td><td> Слишком много запросов </td><td>  -  </td></tr>
        <tr><td> 500 </td><td> Внутренняя ошибка сервера </td><td>  -  </td></tr>
     </table>
     */
    public void updateAgentToolConnection(Integer agentId, Integer toolId, UpdateAgentTool updateAgentTool) throws ApiException {
        updateAgentToolConnectionWithHttpInfo(agentId, toolId, updateAgentTool);
    }

    /**
     * Изменение разрешённых методов MCP-инструмента у агента
     * Чтобы изменить список методов, которые агенту разрешено вызывать, отправьте PUT-запрос на &#x60;/api/v1/cloud-ai/agents/{agent_id}/tools/{tool_id}&#x60;.  Список заменяется целиком. Подходят только имена из &#x60;methods[].name&#x60; инструмента, иначе вернётся 400 с &#x60;error_code&#x60; &#x60;invalid_methods_provided&#x60;. Если инструмент не подключён к агенту, вернётся 404 с &#x60;error_code&#x60; &#x60;agent-tool_link_not_found&#x60;.
     * @param agentId ID агента (required)
     * @param toolId ID инструмента (required)
     * @param updateAgentTool  (required)
     * @return ApiResponse&lt;Void&gt;
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> Запрос выполнен. Тело ответа пустое </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Некорректный запрос </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Не авторизован </td><td>  -  </td></tr>
        <tr><td> 403 </td><td> Запрещено </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Не найдено </td><td>  -  </td></tr>
        <tr><td> 429 </td><td> Слишком много запросов </td><td>  -  </td></tr>
        <tr><td> 500 </td><td> Внутренняя ошибка сервера </td><td>  -  </td></tr>
     </table>
     */
    public ApiResponse<Void> updateAgentToolConnectionWithHttpInfo(Integer agentId, Integer toolId, UpdateAgentTool updateAgentTool) throws ApiException {
        okhttp3.Call localVarCall = updateAgentToolConnectionValidateBeforeCall(agentId, toolId, updateAgentTool, null);
        return localVarApiClient.execute(localVarCall);
    }

    /**
     * Изменение разрешённых методов MCP-инструмента у агента (asynchronously)
     * Чтобы изменить список методов, которые агенту разрешено вызывать, отправьте PUT-запрос на &#x60;/api/v1/cloud-ai/agents/{agent_id}/tools/{tool_id}&#x60;.  Список заменяется целиком. Подходят только имена из &#x60;methods[].name&#x60; инструмента, иначе вернётся 400 с &#x60;error_code&#x60; &#x60;invalid_methods_provided&#x60;. Если инструмент не подключён к агенту, вернётся 404 с &#x60;error_code&#x60; &#x60;agent-tool_link_not_found&#x60;.
     * @param agentId ID агента (required)
     * @param toolId ID инструмента (required)
     * @param updateAgentTool  (required)
     * @param _callback The callback to be executed when the API call finishes
     * @return The request call
     * @throws ApiException If fail to process the API call, e.g. serializing the request body object
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> Запрос выполнен. Тело ответа пустое </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Некорректный запрос </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Не авторизован </td><td>  -  </td></tr>
        <tr><td> 403 </td><td> Запрещено </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Не найдено </td><td>  -  </td></tr>
        <tr><td> 429 </td><td> Слишком много запросов </td><td>  -  </td></tr>
        <tr><td> 500 </td><td> Внутренняя ошибка сервера </td><td>  -  </td></tr>
     </table>
     */
    public okhttp3.Call updateAgentToolConnectionAsync(Integer agentId, Integer toolId, UpdateAgentTool updateAgentTool, final ApiCallback<Void> _callback) throws ApiException {

        okhttp3.Call localVarCall = updateAgentToolConnectionValidateBeforeCall(agentId, toolId, updateAgentTool, _callback);
        localVarApiClient.executeAsync(localVarCall, _callback);
        return localVarCall;
    }
}
