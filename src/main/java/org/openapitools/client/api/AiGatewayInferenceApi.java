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


import org.openapitools.client.model.AiGatewayBatch;
import org.openapitools.client.model.AiGatewayBatchCreateRequest;
import org.openapitools.client.model.AiGatewayBatchList;
import org.openapitools.client.model.AiGatewayChatCompletion;
import org.openapitools.client.model.AiGatewayChatCompletionChunk;
import org.openapitools.client.model.AiGatewayChatCompletionRequest;
import org.openapitools.client.model.AiGatewayCompletion;
import org.openapitools.client.model.AiGatewayCompletionRequest;
import org.openapitools.client.model.AiGatewayEmbeddingList;
import org.openapitools.client.model.AiGatewayEmbeddingRequest;
import org.openapitools.client.model.AiGatewayError;
import org.openapitools.client.model.AiGatewayFile;
import org.openapitools.client.model.AiGatewayFileDeleted;
import org.openapitools.client.model.AiGatewayImageGenerationRequest;
import org.openapitools.client.model.AiGatewayImagesResponse;
import org.openapitools.client.model.AiGatewayModelList;
import org.openapitools.client.model.AiGatewayResponse;
import org.openapitools.client.model.AiGatewayResponseCreateRequest;
import org.openapitools.client.model.AiGatewayResponseDeleted;
import org.openapitools.client.model.AiGatewaySpeechRequest;
import org.openapitools.client.model.AiGatewayTranscription;
import org.openapitools.client.model.AiGatewayValidationError;
import java.io.File;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AiGatewayInferenceApi {
    private ApiClient localVarApiClient;
    private int localHostIndex;
    private String localCustomBaseUrl;

    public AiGatewayInferenceApi() {
        this(Configuration.getDefaultApiClient());
    }

    public AiGatewayInferenceApi(ApiClient apiClient) {
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
     * Build call for cancelAiGatewayBatch
     * @param batchId ID батча (required)
     * @param _callback Callback for upload/download progress
     * @return Call to execute
     * @throws ApiException If fail to serialize the request body object
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> Отменяемый батч </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Ключ не передан или имеет неверный формат </td><td>  -  </td></tr>
        <tr><td> 403 </td><td> Ключ недействителен или аккаунт заблокирован </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Батч не найден </td><td>  -  </td></tr>
        <tr><td> 500 </td><td> Внутренняя ошибка или некорректный ответ провайдера модели. Повторите запрос позже </td><td>  -  </td></tr>
        <tr><td> 503 </td><td> Сервис или провайдер модели временно недоступен. Повторите запрос позже </td><td>  -  </td></tr>
     </table>
     */
    public okhttp3.Call cancelAiGatewayBatchCall(String batchId, final ApiCallback _callback) throws ApiException {
        String basePath = null;
        // Operation Servers
        String[] localBasePaths = new String[] { "https://api.timeweb.ai" };

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
        String localVarPath = "/v1/batches/{batch_id}/cancel"
            .replace("{" + "batch_id" + "}", localVarApiClient.escapeString(batchId.toString()));

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

        String[] localVarAuthNames = new String[] { "AiGatewayKey" };
        return localVarApiClient.buildCall(basePath, localVarPath, "POST", localVarQueryParams, localVarCollectionQueryParams, localVarPostBody, localVarHeaderParams, localVarCookieParams, localVarFormParams, localVarAuthNames, _callback);
    }

    @SuppressWarnings("rawtypes")
    private okhttp3.Call cancelAiGatewayBatchValidateBeforeCall(String batchId, final ApiCallback _callback) throws ApiException {
        // verify the required parameter 'batchId' is set
        if (batchId == null) {
            throw new ApiException("Missing the required parameter 'batchId' when calling cancelAiGatewayBatch(Async)");
        }

        return cancelAiGatewayBatchCall(batchId, _callback);

    }

    /**
     * Отмена батча
     * Чтобы отменить батч, отправьте POST-запрос на &#x60;/v1/batches/{batch_id}/cancel&#x60;.  Батч переходит в статус &#x60;cancelling&#x60;, отмена выполняется асинхронно и может занять несколько минут; итоговый статус &#x60;cancelled&#x60; проверяйте через &#x60;GET /v1/batches/{batch_id}&#x60;. Пока отмена не завершилась, входной файл батча удалить нельзя.
     * @param batchId ID батча (required)
     * @return AiGatewayBatch
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> Отменяемый батч </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Ключ не передан или имеет неверный формат </td><td>  -  </td></tr>
        <tr><td> 403 </td><td> Ключ недействителен или аккаунт заблокирован </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Батч не найден </td><td>  -  </td></tr>
        <tr><td> 500 </td><td> Внутренняя ошибка или некорректный ответ провайдера модели. Повторите запрос позже </td><td>  -  </td></tr>
        <tr><td> 503 </td><td> Сервис или провайдер модели временно недоступен. Повторите запрос позже </td><td>  -  </td></tr>
     </table>
     */
    public AiGatewayBatch cancelAiGatewayBatch(String batchId) throws ApiException {
        ApiResponse<AiGatewayBatch> localVarResp = cancelAiGatewayBatchWithHttpInfo(batchId);
        return localVarResp.getData();
    }

    /**
     * Отмена батча
     * Чтобы отменить батч, отправьте POST-запрос на &#x60;/v1/batches/{batch_id}/cancel&#x60;.  Батч переходит в статус &#x60;cancelling&#x60;, отмена выполняется асинхронно и может занять несколько минут; итоговый статус &#x60;cancelled&#x60; проверяйте через &#x60;GET /v1/batches/{batch_id}&#x60;. Пока отмена не завершилась, входной файл батча удалить нельзя.
     * @param batchId ID батча (required)
     * @return ApiResponse&lt;AiGatewayBatch&gt;
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> Отменяемый батч </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Ключ не передан или имеет неверный формат </td><td>  -  </td></tr>
        <tr><td> 403 </td><td> Ключ недействителен или аккаунт заблокирован </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Батч не найден </td><td>  -  </td></tr>
        <tr><td> 500 </td><td> Внутренняя ошибка или некорректный ответ провайдера модели. Повторите запрос позже </td><td>  -  </td></tr>
        <tr><td> 503 </td><td> Сервис или провайдер модели временно недоступен. Повторите запрос позже </td><td>  -  </td></tr>
     </table>
     */
    public ApiResponse<AiGatewayBatch> cancelAiGatewayBatchWithHttpInfo(String batchId) throws ApiException {
        okhttp3.Call localVarCall = cancelAiGatewayBatchValidateBeforeCall(batchId, null);
        Type localVarReturnType = new TypeToken<AiGatewayBatch>(){}.getType();
        return localVarApiClient.execute(localVarCall, localVarReturnType);
    }

    /**
     * Отмена батча (asynchronously)
     * Чтобы отменить батч, отправьте POST-запрос на &#x60;/v1/batches/{batch_id}/cancel&#x60;.  Батч переходит в статус &#x60;cancelling&#x60;, отмена выполняется асинхронно и может занять несколько минут; итоговый статус &#x60;cancelled&#x60; проверяйте через &#x60;GET /v1/batches/{batch_id}&#x60;. Пока отмена не завершилась, входной файл батча удалить нельзя.
     * @param batchId ID батча (required)
     * @param _callback The callback to be executed when the API call finishes
     * @return The request call
     * @throws ApiException If fail to process the API call, e.g. serializing the request body object
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> Отменяемый батч </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Ключ не передан или имеет неверный формат </td><td>  -  </td></tr>
        <tr><td> 403 </td><td> Ключ недействителен или аккаунт заблокирован </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Батч не найден </td><td>  -  </td></tr>
        <tr><td> 500 </td><td> Внутренняя ошибка или некорректный ответ провайдера модели. Повторите запрос позже </td><td>  -  </td></tr>
        <tr><td> 503 </td><td> Сервис или провайдер модели временно недоступен. Повторите запрос позже </td><td>  -  </td></tr>
     </table>
     */
    public okhttp3.Call cancelAiGatewayBatchAsync(String batchId, final ApiCallback<AiGatewayBatch> _callback) throws ApiException {

        okhttp3.Call localVarCall = cancelAiGatewayBatchValidateBeforeCall(batchId, _callback);
        Type localVarReturnType = new TypeToken<AiGatewayBatch>(){}.getType();
        localVarApiClient.executeAsync(localVarCall, localVarReturnType, _callback);
        return localVarCall;
    }
    /**
     * Build call for cancelAiGatewayResponse
     * @param responseId ID ответа Responses API (required)
     * @param _callback Callback for upload/download progress
     * @return Call to execute
     * @throws ApiException If fail to serialize the request body object
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> Отменяемый ответ </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Ответ нельзя отменить (создан без &#x60;background&#x60;) или не найден </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Ключ не передан или имеет неверный формат </td><td>  -  </td></tr>
        <tr><td> 403 </td><td> Ключ недействителен или аккаунт заблокирован </td><td>  -  </td></tr>
        <tr><td> 500 </td><td> Внутренняя ошибка или некорректный ответ провайдера модели. Повторите запрос позже </td><td>  -  </td></tr>
     </table>
     */
    public okhttp3.Call cancelAiGatewayResponseCall(String responseId, final ApiCallback _callback) throws ApiException {
        String basePath = null;
        // Operation Servers
        String[] localBasePaths = new String[] { "https://api.timeweb.ai" };

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
        String localVarPath = "/v1/responses/{response_id}/cancel"
            .replace("{" + "response_id" + "}", localVarApiClient.escapeString(responseId.toString()));

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

        String[] localVarAuthNames = new String[] { "AiGatewayKey" };
        return localVarApiClient.buildCall(basePath, localVarPath, "POST", localVarQueryParams, localVarCollectionQueryParams, localVarPostBody, localVarHeaderParams, localVarCookieParams, localVarFormParams, localVarAuthNames, _callback);
    }

    @SuppressWarnings("rawtypes")
    private okhttp3.Call cancelAiGatewayResponseValidateBeforeCall(String responseId, final ApiCallback _callback) throws ApiException {
        // verify the required parameter 'responseId' is set
        if (responseId == null) {
            throw new ApiException("Missing the required parameter 'responseId' when calling cancelAiGatewayResponse(Async)");
        }

        return cancelAiGatewayResponseCall(responseId, _callback);

    }

    /**
     * Отмена фонового ответа (Responses API)
     * Чтобы отменить ответ, запущенный с &#x60;\&quot;background\&quot;: true&#x60;, отправьте POST-запрос на &#x60;/v1/responses/{response_id}/cancel&#x60;.  Отмена может завершиться не сразу: итоговый статус &#x60;cancelled&#x60; проверяйте через &#x60;GET /v1/responses/{response_id}&#x60;. Ответ, созданный без &#x60;background&#x60;, отменить нельзя — вернётся ошибка 400. Неизвестный &#x60;response_id&#x60; также возвращает 400.
     * @param responseId ID ответа Responses API (required)
     * @return AiGatewayResponse
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> Отменяемый ответ </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Ответ нельзя отменить (создан без &#x60;background&#x60;) или не найден </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Ключ не передан или имеет неверный формат </td><td>  -  </td></tr>
        <tr><td> 403 </td><td> Ключ недействителен или аккаунт заблокирован </td><td>  -  </td></tr>
        <tr><td> 500 </td><td> Внутренняя ошибка или некорректный ответ провайдера модели. Повторите запрос позже </td><td>  -  </td></tr>
     </table>
     */
    public AiGatewayResponse cancelAiGatewayResponse(String responseId) throws ApiException {
        ApiResponse<AiGatewayResponse> localVarResp = cancelAiGatewayResponseWithHttpInfo(responseId);
        return localVarResp.getData();
    }

    /**
     * Отмена фонового ответа (Responses API)
     * Чтобы отменить ответ, запущенный с &#x60;\&quot;background\&quot;: true&#x60;, отправьте POST-запрос на &#x60;/v1/responses/{response_id}/cancel&#x60;.  Отмена может завершиться не сразу: итоговый статус &#x60;cancelled&#x60; проверяйте через &#x60;GET /v1/responses/{response_id}&#x60;. Ответ, созданный без &#x60;background&#x60;, отменить нельзя — вернётся ошибка 400. Неизвестный &#x60;response_id&#x60; также возвращает 400.
     * @param responseId ID ответа Responses API (required)
     * @return ApiResponse&lt;AiGatewayResponse&gt;
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> Отменяемый ответ </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Ответ нельзя отменить (создан без &#x60;background&#x60;) или не найден </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Ключ не передан или имеет неверный формат </td><td>  -  </td></tr>
        <tr><td> 403 </td><td> Ключ недействителен или аккаунт заблокирован </td><td>  -  </td></tr>
        <tr><td> 500 </td><td> Внутренняя ошибка или некорректный ответ провайдера модели. Повторите запрос позже </td><td>  -  </td></tr>
     </table>
     */
    public ApiResponse<AiGatewayResponse> cancelAiGatewayResponseWithHttpInfo(String responseId) throws ApiException {
        okhttp3.Call localVarCall = cancelAiGatewayResponseValidateBeforeCall(responseId, null);
        Type localVarReturnType = new TypeToken<AiGatewayResponse>(){}.getType();
        return localVarApiClient.execute(localVarCall, localVarReturnType);
    }

    /**
     * Отмена фонового ответа (Responses API) (asynchronously)
     * Чтобы отменить ответ, запущенный с &#x60;\&quot;background\&quot;: true&#x60;, отправьте POST-запрос на &#x60;/v1/responses/{response_id}/cancel&#x60;.  Отмена может завершиться не сразу: итоговый статус &#x60;cancelled&#x60; проверяйте через &#x60;GET /v1/responses/{response_id}&#x60;. Ответ, созданный без &#x60;background&#x60;, отменить нельзя — вернётся ошибка 400. Неизвестный &#x60;response_id&#x60; также возвращает 400.
     * @param responseId ID ответа Responses API (required)
     * @param _callback The callback to be executed when the API call finishes
     * @return The request call
     * @throws ApiException If fail to process the API call, e.g. serializing the request body object
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> Отменяемый ответ </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Ответ нельзя отменить (создан без &#x60;background&#x60;) или не найден </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Ключ не передан или имеет неверный формат </td><td>  -  </td></tr>
        <tr><td> 403 </td><td> Ключ недействителен или аккаунт заблокирован </td><td>  -  </td></tr>
        <tr><td> 500 </td><td> Внутренняя ошибка или некорректный ответ провайдера модели. Повторите запрос позже </td><td>  -  </td></tr>
     </table>
     */
    public okhttp3.Call cancelAiGatewayResponseAsync(String responseId, final ApiCallback<AiGatewayResponse> _callback) throws ApiException {

        okhttp3.Call localVarCall = cancelAiGatewayResponseValidateBeforeCall(responseId, _callback);
        Type localVarReturnType = new TypeToken<AiGatewayResponse>(){}.getType();
        localVarApiClient.executeAsync(localVarCall, localVarReturnType, _callback);
        return localVarCall;
    }
    /**
     * Build call for createAiGatewayBatch
     * @param aiGatewayBatchCreateRequest  (required)
     * @param _callback Callback for upload/download progress
     * @return Call to execute
     * @throws ApiException If fail to serialize the request body object
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> Созданный батч </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Некорректный запрос: неверный &#x60;input_file_id&#x60;, &#x60;endpoint&#x60; не совпадает с &#x60;url&#x60; строк файла, неверный &#x60;completion_window&#x60; </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Ключ не передан или имеет неверный формат </td><td>  -  </td></tr>
        <tr><td> 402 </td><td> Недостаточно средств на балансе: оценочная стоимость запроса больше доступного баланса (с учётом ещё не списанной стоимости недавних и выполняющихся запросов) </td><td>  -  </td></tr>
        <tr><td> 403 </td><td> Ключ недействителен, аккаунт заблокирован или Batch API недоступен для ключа </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Модель не найдена: неизвестный или не переданный &#x60;model&#x60; </td><td>  -  </td></tr>
        <tr><td> 405 </td><td> Модель не поддерживает Batch API или этот эндпоинт </td><td>  -  </td></tr>
        <tr><td> 410 </td><td> Модель снята с обслуживания </td><td>  -  </td></tr>
        <tr><td> 422 </td><td> Исчерпан лимит расхода ключа </td><td>  -  </td></tr>
        <tr><td> 429 </td><td> Достигнут лимит очереди Batch API для модели — дождитесь завершения ваших батчей и повторите запрос </td><td>  -  </td></tr>
        <tr><td> 500 </td><td> Внутренняя ошибка или некорректный ответ провайдера модели. Повторите запрос позже </td><td>  -  </td></tr>
        <tr><td> 503 </td><td> Сервис или провайдер модели временно недоступен. Повторите запрос позже </td><td>  -  </td></tr>
     </table>
     */
    public okhttp3.Call createAiGatewayBatchCall(AiGatewayBatchCreateRequest aiGatewayBatchCreateRequest, final ApiCallback _callback) throws ApiException {
        String basePath = null;
        // Operation Servers
        String[] localBasePaths = new String[] { "https://api.timeweb.ai" };

        // Determine Base Path to Use
        if (localCustomBaseUrl != null){
            basePath = localCustomBaseUrl;
        } else if ( localBasePaths.length > 0 ) {
            basePath = localBasePaths[localHostIndex];
        } else {
            basePath = null;
        }

        Object localVarPostBody = aiGatewayBatchCreateRequest;

        // create path and map variables
        String localVarPath = "/v1/batches";

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

        String[] localVarAuthNames = new String[] { "AiGatewayKey" };
        return localVarApiClient.buildCall(basePath, localVarPath, "POST", localVarQueryParams, localVarCollectionQueryParams, localVarPostBody, localVarHeaderParams, localVarCookieParams, localVarFormParams, localVarAuthNames, _callback);
    }

    @SuppressWarnings("rawtypes")
    private okhttp3.Call createAiGatewayBatchValidateBeforeCall(AiGatewayBatchCreateRequest aiGatewayBatchCreateRequest, final ApiCallback _callback) throws ApiException {
        // verify the required parameter 'aiGatewayBatchCreateRequest' is set
        if (aiGatewayBatchCreateRequest == null) {
            throw new ApiException("Missing the required parameter 'aiGatewayBatchCreateRequest' when calling createAiGatewayBatch(Async)");
        }

        return createAiGatewayBatchCall(aiGatewayBatchCreateRequest, _callback);

    }

    /**
     * Создание батча
     * Чтобы запустить асинхронную обработку входного файла, загруженного с &#x60;purpose&#x3D;batch&#x60;, отправьте POST-запрос на &#x60;/v1/batches&#x60;.  При создании батча его оценочная стоимость резервируется на балансе, фактическая стоимость списывается по цене Batch API после выполнения. Модели, эндпоинты и коэффициенты цены Batch API — в поле &#x60;batch&#x60; каталога моделей (&#x60;GET https://api.timeweb.cloud/api/v3/cloud-ai/models&#x60;). Batch API доступен только для ключей AI Gateway.
     * @param aiGatewayBatchCreateRequest  (required)
     * @return AiGatewayBatch
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> Созданный батч </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Некорректный запрос: неверный &#x60;input_file_id&#x60;, &#x60;endpoint&#x60; не совпадает с &#x60;url&#x60; строк файла, неверный &#x60;completion_window&#x60; </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Ключ не передан или имеет неверный формат </td><td>  -  </td></tr>
        <tr><td> 402 </td><td> Недостаточно средств на балансе: оценочная стоимость запроса больше доступного баланса (с учётом ещё не списанной стоимости недавних и выполняющихся запросов) </td><td>  -  </td></tr>
        <tr><td> 403 </td><td> Ключ недействителен, аккаунт заблокирован или Batch API недоступен для ключа </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Модель не найдена: неизвестный или не переданный &#x60;model&#x60; </td><td>  -  </td></tr>
        <tr><td> 405 </td><td> Модель не поддерживает Batch API или этот эндпоинт </td><td>  -  </td></tr>
        <tr><td> 410 </td><td> Модель снята с обслуживания </td><td>  -  </td></tr>
        <tr><td> 422 </td><td> Исчерпан лимит расхода ключа </td><td>  -  </td></tr>
        <tr><td> 429 </td><td> Достигнут лимит очереди Batch API для модели — дождитесь завершения ваших батчей и повторите запрос </td><td>  -  </td></tr>
        <tr><td> 500 </td><td> Внутренняя ошибка или некорректный ответ провайдера модели. Повторите запрос позже </td><td>  -  </td></tr>
        <tr><td> 503 </td><td> Сервис или провайдер модели временно недоступен. Повторите запрос позже </td><td>  -  </td></tr>
     </table>
     */
    public AiGatewayBatch createAiGatewayBatch(AiGatewayBatchCreateRequest aiGatewayBatchCreateRequest) throws ApiException {
        ApiResponse<AiGatewayBatch> localVarResp = createAiGatewayBatchWithHttpInfo(aiGatewayBatchCreateRequest);
        return localVarResp.getData();
    }

    /**
     * Создание батча
     * Чтобы запустить асинхронную обработку входного файла, загруженного с &#x60;purpose&#x3D;batch&#x60;, отправьте POST-запрос на &#x60;/v1/batches&#x60;.  При создании батча его оценочная стоимость резервируется на балансе, фактическая стоимость списывается по цене Batch API после выполнения. Модели, эндпоинты и коэффициенты цены Batch API — в поле &#x60;batch&#x60; каталога моделей (&#x60;GET https://api.timeweb.cloud/api/v3/cloud-ai/models&#x60;). Batch API доступен только для ключей AI Gateway.
     * @param aiGatewayBatchCreateRequest  (required)
     * @return ApiResponse&lt;AiGatewayBatch&gt;
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> Созданный батч </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Некорректный запрос: неверный &#x60;input_file_id&#x60;, &#x60;endpoint&#x60; не совпадает с &#x60;url&#x60; строк файла, неверный &#x60;completion_window&#x60; </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Ключ не передан или имеет неверный формат </td><td>  -  </td></tr>
        <tr><td> 402 </td><td> Недостаточно средств на балансе: оценочная стоимость запроса больше доступного баланса (с учётом ещё не списанной стоимости недавних и выполняющихся запросов) </td><td>  -  </td></tr>
        <tr><td> 403 </td><td> Ключ недействителен, аккаунт заблокирован или Batch API недоступен для ключа </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Модель не найдена: неизвестный или не переданный &#x60;model&#x60; </td><td>  -  </td></tr>
        <tr><td> 405 </td><td> Модель не поддерживает Batch API или этот эндпоинт </td><td>  -  </td></tr>
        <tr><td> 410 </td><td> Модель снята с обслуживания </td><td>  -  </td></tr>
        <tr><td> 422 </td><td> Исчерпан лимит расхода ключа </td><td>  -  </td></tr>
        <tr><td> 429 </td><td> Достигнут лимит очереди Batch API для модели — дождитесь завершения ваших батчей и повторите запрос </td><td>  -  </td></tr>
        <tr><td> 500 </td><td> Внутренняя ошибка или некорректный ответ провайдера модели. Повторите запрос позже </td><td>  -  </td></tr>
        <tr><td> 503 </td><td> Сервис или провайдер модели временно недоступен. Повторите запрос позже </td><td>  -  </td></tr>
     </table>
     */
    public ApiResponse<AiGatewayBatch> createAiGatewayBatchWithHttpInfo(AiGatewayBatchCreateRequest aiGatewayBatchCreateRequest) throws ApiException {
        okhttp3.Call localVarCall = createAiGatewayBatchValidateBeforeCall(aiGatewayBatchCreateRequest, null);
        Type localVarReturnType = new TypeToken<AiGatewayBatch>(){}.getType();
        return localVarApiClient.execute(localVarCall, localVarReturnType);
    }

    /**
     * Создание батча (asynchronously)
     * Чтобы запустить асинхронную обработку входного файла, загруженного с &#x60;purpose&#x3D;batch&#x60;, отправьте POST-запрос на &#x60;/v1/batches&#x60;.  При создании батча его оценочная стоимость резервируется на балансе, фактическая стоимость списывается по цене Batch API после выполнения. Модели, эндпоинты и коэффициенты цены Batch API — в поле &#x60;batch&#x60; каталога моделей (&#x60;GET https://api.timeweb.cloud/api/v3/cloud-ai/models&#x60;). Batch API доступен только для ключей AI Gateway.
     * @param aiGatewayBatchCreateRequest  (required)
     * @param _callback The callback to be executed when the API call finishes
     * @return The request call
     * @throws ApiException If fail to process the API call, e.g. serializing the request body object
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> Созданный батч </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Некорректный запрос: неверный &#x60;input_file_id&#x60;, &#x60;endpoint&#x60; не совпадает с &#x60;url&#x60; строк файла, неверный &#x60;completion_window&#x60; </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Ключ не передан или имеет неверный формат </td><td>  -  </td></tr>
        <tr><td> 402 </td><td> Недостаточно средств на балансе: оценочная стоимость запроса больше доступного баланса (с учётом ещё не списанной стоимости недавних и выполняющихся запросов) </td><td>  -  </td></tr>
        <tr><td> 403 </td><td> Ключ недействителен, аккаунт заблокирован или Batch API недоступен для ключа </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Модель не найдена: неизвестный или не переданный &#x60;model&#x60; </td><td>  -  </td></tr>
        <tr><td> 405 </td><td> Модель не поддерживает Batch API или этот эндпоинт </td><td>  -  </td></tr>
        <tr><td> 410 </td><td> Модель снята с обслуживания </td><td>  -  </td></tr>
        <tr><td> 422 </td><td> Исчерпан лимит расхода ключа </td><td>  -  </td></tr>
        <tr><td> 429 </td><td> Достигнут лимит очереди Batch API для модели — дождитесь завершения ваших батчей и повторите запрос </td><td>  -  </td></tr>
        <tr><td> 500 </td><td> Внутренняя ошибка или некорректный ответ провайдера модели. Повторите запрос позже </td><td>  -  </td></tr>
        <tr><td> 503 </td><td> Сервис или провайдер модели временно недоступен. Повторите запрос позже </td><td>  -  </td></tr>
     </table>
     */
    public okhttp3.Call createAiGatewayBatchAsync(AiGatewayBatchCreateRequest aiGatewayBatchCreateRequest, final ApiCallback<AiGatewayBatch> _callback) throws ApiException {

        okhttp3.Call localVarCall = createAiGatewayBatchValidateBeforeCall(aiGatewayBatchCreateRequest, _callback);
        Type localVarReturnType = new TypeToken<AiGatewayBatch>(){}.getType();
        localVarApiClient.executeAsync(localVarCall, localVarReturnType, _callback);
        return localVarCall;
    }
    /**
     * Build call for createAiGatewayChatCompletion
     * @param requestBody  (required)
     * @param _callback Callback for upload/download progress
     * @return Call to execute
     * @throws ApiException If fail to serialize the request body object
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> Ответ модели или, с &#x60;\&quot;stream\&quot;: true&#x60;, поток чанков </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Некорректный запрос: не переданы или неверны параметры (например, пустой &#x60;messages&#x60;), модель не поддерживает операцию (например, модель эмбеддингов) или временно недоступна </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Ключ не передан или имеет неверный формат </td><td>  -  </td></tr>
        <tr><td> 402 </td><td> Недостаточно средств на балансе: оценочная стоимость запроса больше доступного баланса (с учётом ещё не списанной стоимости недавних и выполняющихся запросов) </td><td>  -  </td></tr>
        <tr><td> 403 </td><td> Ключ недействителен или аккаунт заблокирован </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Модель не найдена: неизвестный или не переданный &#x60;model&#x60; </td><td>  -  </td></tr>
        <tr><td> 410 </td><td> Модель снята с обслуживания </td><td>  -  </td></tr>
        <tr><td> 422 </td><td> Исчерпан лимит расхода ключа </td><td>  -  </td></tr>
        <tr><td> 429 </td><td> Превышен лимит запросов провайдера модели (текст ошибки — от провайдера). Повторите запрос позже </td><td>  -  </td></tr>
        <tr><td> 500 </td><td> Внутренняя ошибка или некорректный ответ провайдера модели. Повторите запрос позже </td><td>  -  </td></tr>
        <tr><td> 503 </td><td> Сервис или провайдер модели временно недоступен. Повторите запрос позже </td><td>  -  </td></tr>
     </table>
     */
    public okhttp3.Call createAiGatewayChatCompletionCall(Map<String, Object> requestBody, final ApiCallback _callback) throws ApiException {
        String basePath = null;
        // Operation Servers
        String[] localBasePaths = new String[] { "https://api.timeweb.ai" };

        // Determine Base Path to Use
        if (localCustomBaseUrl != null){
            basePath = localCustomBaseUrl;
        } else if ( localBasePaths.length > 0 ) {
            basePath = localBasePaths[localHostIndex];
        } else {
            basePath = null;
        }

        Object localVarPostBody = requestBody;

        // create path and map variables
        String localVarPath = "/v1/chat/completions";

        List<Pair> localVarQueryParams = new ArrayList<Pair>();
        List<Pair> localVarCollectionQueryParams = new ArrayList<Pair>();
        Map<String, String> localVarHeaderParams = new HashMap<String, String>();
        Map<String, String> localVarCookieParams = new HashMap<String, String>();
        Map<String, Object> localVarFormParams = new HashMap<String, Object>();

        final String[] localVarAccepts = {
            "application/json",
            "text/event-stream"
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

        String[] localVarAuthNames = new String[] { "AiGatewayKey" };
        return localVarApiClient.buildCall(basePath, localVarPath, "POST", localVarQueryParams, localVarCollectionQueryParams, localVarPostBody, localVarHeaderParams, localVarCookieParams, localVarFormParams, localVarAuthNames, _callback);
    }

    @SuppressWarnings("rawtypes")
    private okhttp3.Call createAiGatewayChatCompletionValidateBeforeCall(Map<String, Object> requestBody, final ApiCallback _callback) throws ApiException {
        // verify the required parameter 'requestBody' is set
        if (requestBody == null) {
            throw new ApiException("Missing the required parameter 'requestBody' when calling createAiGatewayChatCompletion(Async)");
        }

        return createAiGatewayChatCompletionCall(requestBody, _callback);

    }

    /**
     * Генерация ответа в диалоге (Chat Completions)
     * Чтобы получить следующий ответ модели в диалоге, отправьте POST-запрос на &#x60;/v1/chat/completions&#x60;.  Запрос не хранит состояние: передавайте всю историю диалога в &#x60;messages&#x60;. Поддерживаются вызов функций (&#x60;tools&#x60;), структурированный ответ (&#x60;response_format&#x60;) и изображения во входных сообщениях (для моделей с поддержкой зрения).  С &#x60;\&quot;stream\&quot;: true&#x60; ответ приходит потоком Server-Sent Events: события &#x60;chat.completion.chunk&#x60; в строках &#x60;data:&#x60;, в конце — &#x60;data: [DONE]&#x60;. С &#x60;\&quot;stream_options\&quot;: {\&quot;include_usage\&quot;: true}&#x60; последний чанк содержит расход токенов.  Если у ключа настроены запасные модели и запрошенная модель не ответила, запрос повторяется на запасных. Заголовок ответа &#x60;x-timeweb-attempted-fallbacks&#x60; показывает, сколько запасных моделей было опробовано.
     * @param requestBody  (required)
     * @return AiGatewayChatCompletion
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> Ответ модели или, с &#x60;\&quot;stream\&quot;: true&#x60;, поток чанков </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Некорректный запрос: не переданы или неверны параметры (например, пустой &#x60;messages&#x60;), модель не поддерживает операцию (например, модель эмбеддингов) или временно недоступна </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Ключ не передан или имеет неверный формат </td><td>  -  </td></tr>
        <tr><td> 402 </td><td> Недостаточно средств на балансе: оценочная стоимость запроса больше доступного баланса (с учётом ещё не списанной стоимости недавних и выполняющихся запросов) </td><td>  -  </td></tr>
        <tr><td> 403 </td><td> Ключ недействителен или аккаунт заблокирован </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Модель не найдена: неизвестный или не переданный &#x60;model&#x60; </td><td>  -  </td></tr>
        <tr><td> 410 </td><td> Модель снята с обслуживания </td><td>  -  </td></tr>
        <tr><td> 422 </td><td> Исчерпан лимит расхода ключа </td><td>  -  </td></tr>
        <tr><td> 429 </td><td> Превышен лимит запросов провайдера модели (текст ошибки — от провайдера). Повторите запрос позже </td><td>  -  </td></tr>
        <tr><td> 500 </td><td> Внутренняя ошибка или некорректный ответ провайдера модели. Повторите запрос позже </td><td>  -  </td></tr>
        <tr><td> 503 </td><td> Сервис или провайдер модели временно недоступен. Повторите запрос позже </td><td>  -  </td></tr>
     </table>
     */
    public AiGatewayChatCompletion createAiGatewayChatCompletion(Map<String, Object> requestBody) throws ApiException {
        ApiResponse<AiGatewayChatCompletion> localVarResp = createAiGatewayChatCompletionWithHttpInfo(requestBody);
        return localVarResp.getData();
    }

    /**
     * Генерация ответа в диалоге (Chat Completions)
     * Чтобы получить следующий ответ модели в диалоге, отправьте POST-запрос на &#x60;/v1/chat/completions&#x60;.  Запрос не хранит состояние: передавайте всю историю диалога в &#x60;messages&#x60;. Поддерживаются вызов функций (&#x60;tools&#x60;), структурированный ответ (&#x60;response_format&#x60;) и изображения во входных сообщениях (для моделей с поддержкой зрения).  С &#x60;\&quot;stream\&quot;: true&#x60; ответ приходит потоком Server-Sent Events: события &#x60;chat.completion.chunk&#x60; в строках &#x60;data:&#x60;, в конце — &#x60;data: [DONE]&#x60;. С &#x60;\&quot;stream_options\&quot;: {\&quot;include_usage\&quot;: true}&#x60; последний чанк содержит расход токенов.  Если у ключа настроены запасные модели и запрошенная модель не ответила, запрос повторяется на запасных. Заголовок ответа &#x60;x-timeweb-attempted-fallbacks&#x60; показывает, сколько запасных моделей было опробовано.
     * @param requestBody  (required)
     * @return ApiResponse&lt;AiGatewayChatCompletion&gt;
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> Ответ модели или, с &#x60;\&quot;stream\&quot;: true&#x60;, поток чанков </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Некорректный запрос: не переданы или неверны параметры (например, пустой &#x60;messages&#x60;), модель не поддерживает операцию (например, модель эмбеддингов) или временно недоступна </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Ключ не передан или имеет неверный формат </td><td>  -  </td></tr>
        <tr><td> 402 </td><td> Недостаточно средств на балансе: оценочная стоимость запроса больше доступного баланса (с учётом ещё не списанной стоимости недавних и выполняющихся запросов) </td><td>  -  </td></tr>
        <tr><td> 403 </td><td> Ключ недействителен или аккаунт заблокирован </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Модель не найдена: неизвестный или не переданный &#x60;model&#x60; </td><td>  -  </td></tr>
        <tr><td> 410 </td><td> Модель снята с обслуживания </td><td>  -  </td></tr>
        <tr><td> 422 </td><td> Исчерпан лимит расхода ключа </td><td>  -  </td></tr>
        <tr><td> 429 </td><td> Превышен лимит запросов провайдера модели (текст ошибки — от провайдера). Повторите запрос позже </td><td>  -  </td></tr>
        <tr><td> 500 </td><td> Внутренняя ошибка или некорректный ответ провайдера модели. Повторите запрос позже </td><td>  -  </td></tr>
        <tr><td> 503 </td><td> Сервис или провайдер модели временно недоступен. Повторите запрос позже </td><td>  -  </td></tr>
     </table>
     */
    public ApiResponse<AiGatewayChatCompletion> createAiGatewayChatCompletionWithHttpInfo(Map<String, Object> requestBody) throws ApiException {
        okhttp3.Call localVarCall = createAiGatewayChatCompletionValidateBeforeCall(requestBody, null);
        Type localVarReturnType = new TypeToken<AiGatewayChatCompletion>(){}.getType();
        return localVarApiClient.execute(localVarCall, localVarReturnType);
    }

    /**
     * Генерация ответа в диалоге (Chat Completions) (asynchronously)
     * Чтобы получить следующий ответ модели в диалоге, отправьте POST-запрос на &#x60;/v1/chat/completions&#x60;.  Запрос не хранит состояние: передавайте всю историю диалога в &#x60;messages&#x60;. Поддерживаются вызов функций (&#x60;tools&#x60;), структурированный ответ (&#x60;response_format&#x60;) и изображения во входных сообщениях (для моделей с поддержкой зрения).  С &#x60;\&quot;stream\&quot;: true&#x60; ответ приходит потоком Server-Sent Events: события &#x60;chat.completion.chunk&#x60; в строках &#x60;data:&#x60;, в конце — &#x60;data: [DONE]&#x60;. С &#x60;\&quot;stream_options\&quot;: {\&quot;include_usage\&quot;: true}&#x60; последний чанк содержит расход токенов.  Если у ключа настроены запасные модели и запрошенная модель не ответила, запрос повторяется на запасных. Заголовок ответа &#x60;x-timeweb-attempted-fallbacks&#x60; показывает, сколько запасных моделей было опробовано.
     * @param requestBody  (required)
     * @param _callback The callback to be executed when the API call finishes
     * @return The request call
     * @throws ApiException If fail to process the API call, e.g. serializing the request body object
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> Ответ модели или, с &#x60;\&quot;stream\&quot;: true&#x60;, поток чанков </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Некорректный запрос: не переданы или неверны параметры (например, пустой &#x60;messages&#x60;), модель не поддерживает операцию (например, модель эмбеддингов) или временно недоступна </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Ключ не передан или имеет неверный формат </td><td>  -  </td></tr>
        <tr><td> 402 </td><td> Недостаточно средств на балансе: оценочная стоимость запроса больше доступного баланса (с учётом ещё не списанной стоимости недавних и выполняющихся запросов) </td><td>  -  </td></tr>
        <tr><td> 403 </td><td> Ключ недействителен или аккаунт заблокирован </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Модель не найдена: неизвестный или не переданный &#x60;model&#x60; </td><td>  -  </td></tr>
        <tr><td> 410 </td><td> Модель снята с обслуживания </td><td>  -  </td></tr>
        <tr><td> 422 </td><td> Исчерпан лимит расхода ключа </td><td>  -  </td></tr>
        <tr><td> 429 </td><td> Превышен лимит запросов провайдера модели (текст ошибки — от провайдера). Повторите запрос позже </td><td>  -  </td></tr>
        <tr><td> 500 </td><td> Внутренняя ошибка или некорректный ответ провайдера модели. Повторите запрос позже </td><td>  -  </td></tr>
        <tr><td> 503 </td><td> Сервис или провайдер модели временно недоступен. Повторите запрос позже </td><td>  -  </td></tr>
     </table>
     */
    public okhttp3.Call createAiGatewayChatCompletionAsync(Map<String, Object> requestBody, final ApiCallback<AiGatewayChatCompletion> _callback) throws ApiException {

        okhttp3.Call localVarCall = createAiGatewayChatCompletionValidateBeforeCall(requestBody, _callback);
        Type localVarReturnType = new TypeToken<AiGatewayChatCompletion>(){}.getType();
        localVarApiClient.executeAsync(localVarCall, localVarReturnType, _callback);
        return localVarCall;
    }
    /**
     * Build call for createAiGatewayCompletion
     * @param requestBody  (required)
     * @param _callback Callback for upload/download progress
     * @return Call to execute
     * @throws ApiException If fail to serialize the request body object
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> Продолжение текста или, с &#x60;\&quot;stream\&quot;: true&#x60;, поток событий </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Некорректный запрос: не переданы или неверны параметры, модель не поддерживает операцию или временно недоступна </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Ключ не передан или имеет неверный формат </td><td>  -  </td></tr>
        <tr><td> 402 </td><td> Недостаточно средств на балансе: оценочная стоимость запроса больше доступного баланса (с учётом ещё не списанной стоимости недавних и выполняющихся запросов) </td><td>  -  </td></tr>
        <tr><td> 403 </td><td> Ключ недействителен или аккаунт заблокирован </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Модель не найдена или снята с обслуживания </td><td>  -  </td></tr>
        <tr><td> 422 </td><td> Исчерпан лимит расхода ключа </td><td>  -  </td></tr>
        <tr><td> 429 </td><td> Превышен лимит запросов провайдера модели (текст ошибки — от провайдера). Повторите запрос позже </td><td>  -  </td></tr>
        <tr><td> 500 </td><td> Внутренняя ошибка или некорректный ответ провайдера модели. Повторите запрос позже </td><td>  -  </td></tr>
        <tr><td> 503 </td><td> Сервис или провайдер модели временно недоступен. Повторите запрос позже </td><td>  -  </td></tr>
     </table>
     */
    public okhttp3.Call createAiGatewayCompletionCall(Map<String, Object> requestBody, final ApiCallback _callback) throws ApiException {
        String basePath = null;
        // Operation Servers
        String[] localBasePaths = new String[] { "https://api.timeweb.ai" };

        // Determine Base Path to Use
        if (localCustomBaseUrl != null){
            basePath = localCustomBaseUrl;
        } else if ( localBasePaths.length > 0 ) {
            basePath = localBasePaths[localHostIndex];
        } else {
            basePath = null;
        }

        Object localVarPostBody = requestBody;

        // create path and map variables
        String localVarPath = "/v1/completions";

        List<Pair> localVarQueryParams = new ArrayList<Pair>();
        List<Pair> localVarCollectionQueryParams = new ArrayList<Pair>();
        Map<String, String> localVarHeaderParams = new HashMap<String, String>();
        Map<String, String> localVarCookieParams = new HashMap<String, String>();
        Map<String, Object> localVarFormParams = new HashMap<String, Object>();

        final String[] localVarAccepts = {
            "application/json",
            "text/event-stream"
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

        String[] localVarAuthNames = new String[] { "AiGatewayKey" };
        return localVarApiClient.buildCall(basePath, localVarPath, "POST", localVarQueryParams, localVarCollectionQueryParams, localVarPostBody, localVarHeaderParams, localVarCookieParams, localVarFormParams, localVarAuthNames, _callback);
    }

    @SuppressWarnings("rawtypes")
    private okhttp3.Call createAiGatewayCompletionValidateBeforeCall(Map<String, Object> requestBody, final ApiCallback _callback) throws ApiException {
        // verify the required parameter 'requestBody' is set
        if (requestBody == null) {
            throw new ApiException("Missing the required parameter 'requestBody' when calling createAiGatewayCompletion(Async)");
        }

        return createAiGatewayCompletionCall(requestBody, _callback);

    }

    /**
     * Продолжение текста (Completions)
     * Чтобы получить продолжение текста &#x60;prompt&#x60;, отправьте POST-запрос на &#x60;/v1/completions&#x60;.  Это устаревший API OpenAI — для новых интеграций используйте &#x60;/v1/chat/completions&#x60;. С &#x60;\&quot;stream\&quot;: true&#x60; ответ приходит потоком Server-Sent Events и заканчивается &#x60;data: [DONE]&#x60;.
     * @param requestBody  (required)
     * @return AiGatewayCompletion
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> Продолжение текста или, с &#x60;\&quot;stream\&quot;: true&#x60;, поток событий </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Некорректный запрос: не переданы или неверны параметры, модель не поддерживает операцию или временно недоступна </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Ключ не передан или имеет неверный формат </td><td>  -  </td></tr>
        <tr><td> 402 </td><td> Недостаточно средств на балансе: оценочная стоимость запроса больше доступного баланса (с учётом ещё не списанной стоимости недавних и выполняющихся запросов) </td><td>  -  </td></tr>
        <tr><td> 403 </td><td> Ключ недействителен или аккаунт заблокирован </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Модель не найдена или снята с обслуживания </td><td>  -  </td></tr>
        <tr><td> 422 </td><td> Исчерпан лимит расхода ключа </td><td>  -  </td></tr>
        <tr><td> 429 </td><td> Превышен лимит запросов провайдера модели (текст ошибки — от провайдера). Повторите запрос позже </td><td>  -  </td></tr>
        <tr><td> 500 </td><td> Внутренняя ошибка или некорректный ответ провайдера модели. Повторите запрос позже </td><td>  -  </td></tr>
        <tr><td> 503 </td><td> Сервис или провайдер модели временно недоступен. Повторите запрос позже </td><td>  -  </td></tr>
     </table>
     */
    public AiGatewayCompletion createAiGatewayCompletion(Map<String, Object> requestBody) throws ApiException {
        ApiResponse<AiGatewayCompletion> localVarResp = createAiGatewayCompletionWithHttpInfo(requestBody);
        return localVarResp.getData();
    }

    /**
     * Продолжение текста (Completions)
     * Чтобы получить продолжение текста &#x60;prompt&#x60;, отправьте POST-запрос на &#x60;/v1/completions&#x60;.  Это устаревший API OpenAI — для новых интеграций используйте &#x60;/v1/chat/completions&#x60;. С &#x60;\&quot;stream\&quot;: true&#x60; ответ приходит потоком Server-Sent Events и заканчивается &#x60;data: [DONE]&#x60;.
     * @param requestBody  (required)
     * @return ApiResponse&lt;AiGatewayCompletion&gt;
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> Продолжение текста или, с &#x60;\&quot;stream\&quot;: true&#x60;, поток событий </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Некорректный запрос: не переданы или неверны параметры, модель не поддерживает операцию или временно недоступна </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Ключ не передан или имеет неверный формат </td><td>  -  </td></tr>
        <tr><td> 402 </td><td> Недостаточно средств на балансе: оценочная стоимость запроса больше доступного баланса (с учётом ещё не списанной стоимости недавних и выполняющихся запросов) </td><td>  -  </td></tr>
        <tr><td> 403 </td><td> Ключ недействителен или аккаунт заблокирован </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Модель не найдена или снята с обслуживания </td><td>  -  </td></tr>
        <tr><td> 422 </td><td> Исчерпан лимит расхода ключа </td><td>  -  </td></tr>
        <tr><td> 429 </td><td> Превышен лимит запросов провайдера модели (текст ошибки — от провайдера). Повторите запрос позже </td><td>  -  </td></tr>
        <tr><td> 500 </td><td> Внутренняя ошибка или некорректный ответ провайдера модели. Повторите запрос позже </td><td>  -  </td></tr>
        <tr><td> 503 </td><td> Сервис или провайдер модели временно недоступен. Повторите запрос позже </td><td>  -  </td></tr>
     </table>
     */
    public ApiResponse<AiGatewayCompletion> createAiGatewayCompletionWithHttpInfo(Map<String, Object> requestBody) throws ApiException {
        okhttp3.Call localVarCall = createAiGatewayCompletionValidateBeforeCall(requestBody, null);
        Type localVarReturnType = new TypeToken<AiGatewayCompletion>(){}.getType();
        return localVarApiClient.execute(localVarCall, localVarReturnType);
    }

    /**
     * Продолжение текста (Completions) (asynchronously)
     * Чтобы получить продолжение текста &#x60;prompt&#x60;, отправьте POST-запрос на &#x60;/v1/completions&#x60;.  Это устаревший API OpenAI — для новых интеграций используйте &#x60;/v1/chat/completions&#x60;. С &#x60;\&quot;stream\&quot;: true&#x60; ответ приходит потоком Server-Sent Events и заканчивается &#x60;data: [DONE]&#x60;.
     * @param requestBody  (required)
     * @param _callback The callback to be executed when the API call finishes
     * @return The request call
     * @throws ApiException If fail to process the API call, e.g. serializing the request body object
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> Продолжение текста или, с &#x60;\&quot;stream\&quot;: true&#x60;, поток событий </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Некорректный запрос: не переданы или неверны параметры, модель не поддерживает операцию или временно недоступна </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Ключ не передан или имеет неверный формат </td><td>  -  </td></tr>
        <tr><td> 402 </td><td> Недостаточно средств на балансе: оценочная стоимость запроса больше доступного баланса (с учётом ещё не списанной стоимости недавних и выполняющихся запросов) </td><td>  -  </td></tr>
        <tr><td> 403 </td><td> Ключ недействителен или аккаунт заблокирован </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Модель не найдена или снята с обслуживания </td><td>  -  </td></tr>
        <tr><td> 422 </td><td> Исчерпан лимит расхода ключа </td><td>  -  </td></tr>
        <tr><td> 429 </td><td> Превышен лимит запросов провайдера модели (текст ошибки — от провайдера). Повторите запрос позже </td><td>  -  </td></tr>
        <tr><td> 500 </td><td> Внутренняя ошибка или некорректный ответ провайдера модели. Повторите запрос позже </td><td>  -  </td></tr>
        <tr><td> 503 </td><td> Сервис или провайдер модели временно недоступен. Повторите запрос позже </td><td>  -  </td></tr>
     </table>
     */
    public okhttp3.Call createAiGatewayCompletionAsync(Map<String, Object> requestBody, final ApiCallback<AiGatewayCompletion> _callback) throws ApiException {

        okhttp3.Call localVarCall = createAiGatewayCompletionValidateBeforeCall(requestBody, _callback);
        Type localVarReturnType = new TypeToken<AiGatewayCompletion>(){}.getType();
        localVarApiClient.executeAsync(localVarCall, localVarReturnType, _callback);
        return localVarCall;
    }
    /**
     * Build call for createAiGatewayEmbedding
     * @param requestBody  (required)
     * @param _callback Callback for upload/download progress
     * @return Call to execute
     * @throws ApiException If fail to serialize the request body object
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> Эмбеддинги </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Некорректный запрос: не передан &#x60;input&#x60;, модель не поддерживает эмбеддинги или временно недоступна </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Ключ не передан или имеет неверный формат </td><td>  -  </td></tr>
        <tr><td> 402 </td><td> Недостаточно средств на балансе: оценочная стоимость запроса больше доступного баланса (с учётом ещё не списанной стоимости недавних и выполняющихся запросов) </td><td>  -  </td></tr>
        <tr><td> 403 </td><td> Ключ недействителен или аккаунт заблокирован </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Модель не найдена: неизвестный или не переданный &#x60;model&#x60; </td><td>  -  </td></tr>
        <tr><td> 410 </td><td> Модель снята с обслуживания </td><td>  -  </td></tr>
        <tr><td> 422 </td><td> Исчерпан лимит расхода ключа </td><td>  -  </td></tr>
        <tr><td> 429 </td><td> Превышен лимит запросов провайдера модели (текст ошибки — от провайдера). Повторите запрос позже </td><td>  -  </td></tr>
        <tr><td> 500 </td><td> Внутренняя ошибка или некорректный ответ провайдера модели. Повторите запрос позже </td><td>  -  </td></tr>
        <tr><td> 503 </td><td> Сервис или провайдер модели временно недоступен. Повторите запрос позже </td><td>  -  </td></tr>
     </table>
     */
    public okhttp3.Call createAiGatewayEmbeddingCall(Map<String, Object> requestBody, final ApiCallback _callback) throws ApiException {
        String basePath = null;
        // Operation Servers
        String[] localBasePaths = new String[] { "https://api.timeweb.ai" };

        // Determine Base Path to Use
        if (localCustomBaseUrl != null){
            basePath = localCustomBaseUrl;
        } else if ( localBasePaths.length > 0 ) {
            basePath = localBasePaths[localHostIndex];
        } else {
            basePath = null;
        }

        Object localVarPostBody = requestBody;

        // create path and map variables
        String localVarPath = "/v1/embeddings";

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

        String[] localVarAuthNames = new String[] { "AiGatewayKey" };
        return localVarApiClient.buildCall(basePath, localVarPath, "POST", localVarQueryParams, localVarCollectionQueryParams, localVarPostBody, localVarHeaderParams, localVarCookieParams, localVarFormParams, localVarAuthNames, _callback);
    }

    @SuppressWarnings("rawtypes")
    private okhttp3.Call createAiGatewayEmbeddingValidateBeforeCall(Map<String, Object> requestBody, final ApiCallback _callback) throws ApiException {
        // verify the required parameter 'requestBody' is set
        if (requestBody == null) {
            throw new ApiException("Missing the required parameter 'requestBody' when calling createAiGatewayEmbedding(Async)");
        }

        return createAiGatewayEmbeddingCall(requestBody, _callback);

    }

    /**
     * Получение эмбеддингов
     * Чтобы получить векторные представления текстов, отправьте POST-запрос на &#x60;/v1/embeddings&#x60;.  Используйте модель с &#x60;mode: embedding&#x60; из &#x60;GET /v1/models&#x60;. Эмбеддинги возвращаются в порядке входных текстов.
     * @param requestBody  (required)
     * @return AiGatewayEmbeddingList
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> Эмбеддинги </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Некорректный запрос: не передан &#x60;input&#x60;, модель не поддерживает эмбеддинги или временно недоступна </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Ключ не передан или имеет неверный формат </td><td>  -  </td></tr>
        <tr><td> 402 </td><td> Недостаточно средств на балансе: оценочная стоимость запроса больше доступного баланса (с учётом ещё не списанной стоимости недавних и выполняющихся запросов) </td><td>  -  </td></tr>
        <tr><td> 403 </td><td> Ключ недействителен или аккаунт заблокирован </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Модель не найдена: неизвестный или не переданный &#x60;model&#x60; </td><td>  -  </td></tr>
        <tr><td> 410 </td><td> Модель снята с обслуживания </td><td>  -  </td></tr>
        <tr><td> 422 </td><td> Исчерпан лимит расхода ключа </td><td>  -  </td></tr>
        <tr><td> 429 </td><td> Превышен лимит запросов провайдера модели (текст ошибки — от провайдера). Повторите запрос позже </td><td>  -  </td></tr>
        <tr><td> 500 </td><td> Внутренняя ошибка или некорректный ответ провайдера модели. Повторите запрос позже </td><td>  -  </td></tr>
        <tr><td> 503 </td><td> Сервис или провайдер модели временно недоступен. Повторите запрос позже </td><td>  -  </td></tr>
     </table>
     */
    public AiGatewayEmbeddingList createAiGatewayEmbedding(Map<String, Object> requestBody) throws ApiException {
        ApiResponse<AiGatewayEmbeddingList> localVarResp = createAiGatewayEmbeddingWithHttpInfo(requestBody);
        return localVarResp.getData();
    }

    /**
     * Получение эмбеддингов
     * Чтобы получить векторные представления текстов, отправьте POST-запрос на &#x60;/v1/embeddings&#x60;.  Используйте модель с &#x60;mode: embedding&#x60; из &#x60;GET /v1/models&#x60;. Эмбеддинги возвращаются в порядке входных текстов.
     * @param requestBody  (required)
     * @return ApiResponse&lt;AiGatewayEmbeddingList&gt;
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> Эмбеддинги </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Некорректный запрос: не передан &#x60;input&#x60;, модель не поддерживает эмбеддинги или временно недоступна </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Ключ не передан или имеет неверный формат </td><td>  -  </td></tr>
        <tr><td> 402 </td><td> Недостаточно средств на балансе: оценочная стоимость запроса больше доступного баланса (с учётом ещё не списанной стоимости недавних и выполняющихся запросов) </td><td>  -  </td></tr>
        <tr><td> 403 </td><td> Ключ недействителен или аккаунт заблокирован </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Модель не найдена: неизвестный или не переданный &#x60;model&#x60; </td><td>  -  </td></tr>
        <tr><td> 410 </td><td> Модель снята с обслуживания </td><td>  -  </td></tr>
        <tr><td> 422 </td><td> Исчерпан лимит расхода ключа </td><td>  -  </td></tr>
        <tr><td> 429 </td><td> Превышен лимит запросов провайдера модели (текст ошибки — от провайдера). Повторите запрос позже </td><td>  -  </td></tr>
        <tr><td> 500 </td><td> Внутренняя ошибка или некорректный ответ провайдера модели. Повторите запрос позже </td><td>  -  </td></tr>
        <tr><td> 503 </td><td> Сервис или провайдер модели временно недоступен. Повторите запрос позже </td><td>  -  </td></tr>
     </table>
     */
    public ApiResponse<AiGatewayEmbeddingList> createAiGatewayEmbeddingWithHttpInfo(Map<String, Object> requestBody) throws ApiException {
        okhttp3.Call localVarCall = createAiGatewayEmbeddingValidateBeforeCall(requestBody, null);
        Type localVarReturnType = new TypeToken<AiGatewayEmbeddingList>(){}.getType();
        return localVarApiClient.execute(localVarCall, localVarReturnType);
    }

    /**
     * Получение эмбеддингов (asynchronously)
     * Чтобы получить векторные представления текстов, отправьте POST-запрос на &#x60;/v1/embeddings&#x60;.  Используйте модель с &#x60;mode: embedding&#x60; из &#x60;GET /v1/models&#x60;. Эмбеддинги возвращаются в порядке входных текстов.
     * @param requestBody  (required)
     * @param _callback The callback to be executed when the API call finishes
     * @return The request call
     * @throws ApiException If fail to process the API call, e.g. serializing the request body object
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> Эмбеддинги </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Некорректный запрос: не передан &#x60;input&#x60;, модель не поддерживает эмбеддинги или временно недоступна </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Ключ не передан или имеет неверный формат </td><td>  -  </td></tr>
        <tr><td> 402 </td><td> Недостаточно средств на балансе: оценочная стоимость запроса больше доступного баланса (с учётом ещё не списанной стоимости недавних и выполняющихся запросов) </td><td>  -  </td></tr>
        <tr><td> 403 </td><td> Ключ недействителен или аккаунт заблокирован </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Модель не найдена: неизвестный или не переданный &#x60;model&#x60; </td><td>  -  </td></tr>
        <tr><td> 410 </td><td> Модель снята с обслуживания </td><td>  -  </td></tr>
        <tr><td> 422 </td><td> Исчерпан лимит расхода ключа </td><td>  -  </td></tr>
        <tr><td> 429 </td><td> Превышен лимит запросов провайдера модели (текст ошибки — от провайдера). Повторите запрос позже </td><td>  -  </td></tr>
        <tr><td> 500 </td><td> Внутренняя ошибка или некорректный ответ провайдера модели. Повторите запрос позже </td><td>  -  </td></tr>
        <tr><td> 503 </td><td> Сервис или провайдер модели временно недоступен. Повторите запрос позже </td><td>  -  </td></tr>
     </table>
     */
    public okhttp3.Call createAiGatewayEmbeddingAsync(Map<String, Object> requestBody, final ApiCallback<AiGatewayEmbeddingList> _callback) throws ApiException {

        okhttp3.Call localVarCall = createAiGatewayEmbeddingValidateBeforeCall(requestBody, _callback);
        Type localVarReturnType = new TypeToken<AiGatewayEmbeddingList>(){}.getType();
        localVarApiClient.executeAsync(localVarCall, localVarReturnType, _callback);
        return localVarCall;
    }
    /**
     * Build call for createAiGatewayImage
     * @param requestBody  (required)
     * @param _callback Callback for upload/download progress
     * @return Call to execute
     * @throws ApiException If fail to serialize the request body object
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> Сгенерированные изображения </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Некорректный запрос: не переданы или неверны параметры, модель не поддерживает операцию или временно недоступна </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Ключ не передан или имеет неверный формат </td><td>  -  </td></tr>
        <tr><td> 402 </td><td> Недостаточно средств на балансе: оценочная стоимость запроса больше доступного баланса (с учётом ещё не списанной стоимости недавних и выполняющихся запросов) </td><td>  -  </td></tr>
        <tr><td> 403 </td><td> Ключ недействителен или аккаунт заблокирован </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Модель не найдена: неизвестный или не переданный &#x60;model&#x60; </td><td>  -  </td></tr>
        <tr><td> 410 </td><td> Модель снята с обслуживания </td><td>  -  </td></tr>
        <tr><td> 422 </td><td> Исчерпан лимит расхода ключа </td><td>  -  </td></tr>
        <tr><td> 429 </td><td> Превышен лимит запросов провайдера модели (текст ошибки — от провайдера). Повторите запрос позже </td><td>  -  </td></tr>
        <tr><td> 500 </td><td> Внутренняя ошибка или некорректный ответ провайдера модели. Повторите запрос позже </td><td>  -  </td></tr>
        <tr><td> 503 </td><td> Сервис или провайдер модели временно недоступен. Повторите запрос позже </td><td>  -  </td></tr>
     </table>
     */
    public okhttp3.Call createAiGatewayImageCall(Map<String, Object> requestBody, final ApiCallback _callback) throws ApiException {
        String basePath = null;
        // Operation Servers
        String[] localBasePaths = new String[] { "https://api.timeweb.ai" };

        // Determine Base Path to Use
        if (localCustomBaseUrl != null){
            basePath = localCustomBaseUrl;
        } else if ( localBasePaths.length > 0 ) {
            basePath = localBasePaths[localHostIndex];
        } else {
            basePath = null;
        }

        Object localVarPostBody = requestBody;

        // create path and map variables
        String localVarPath = "/v1/images/generations";

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

        String[] localVarAuthNames = new String[] { "AiGatewayKey" };
        return localVarApiClient.buildCall(basePath, localVarPath, "POST", localVarQueryParams, localVarCollectionQueryParams, localVarPostBody, localVarHeaderParams, localVarCookieParams, localVarFormParams, localVarAuthNames, _callback);
    }

    @SuppressWarnings("rawtypes")
    private okhttp3.Call createAiGatewayImageValidateBeforeCall(Map<String, Object> requestBody, final ApiCallback _callback) throws ApiException {
        // verify the required parameter 'requestBody' is set
        if (requestBody == null) {
            throw new ApiException("Missing the required parameter 'requestBody' when calling createAiGatewayImage(Async)");
        }

        return createAiGatewayImageCall(requestBody, _callback);

    }

    /**
     * Генерация изображений
     * Чтобы сгенерировать изображения по текстовому описанию, отправьте POST-запрос на &#x60;/v1/images/generations&#x60;.  Используйте модель с &#x60;mode: image_generation&#x60; из &#x60;GET /v1/models&#x60;.
     * @param requestBody  (required)
     * @return AiGatewayImagesResponse
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> Сгенерированные изображения </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Некорректный запрос: не переданы или неверны параметры, модель не поддерживает операцию или временно недоступна </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Ключ не передан или имеет неверный формат </td><td>  -  </td></tr>
        <tr><td> 402 </td><td> Недостаточно средств на балансе: оценочная стоимость запроса больше доступного баланса (с учётом ещё не списанной стоимости недавних и выполняющихся запросов) </td><td>  -  </td></tr>
        <tr><td> 403 </td><td> Ключ недействителен или аккаунт заблокирован </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Модель не найдена: неизвестный или не переданный &#x60;model&#x60; </td><td>  -  </td></tr>
        <tr><td> 410 </td><td> Модель снята с обслуживания </td><td>  -  </td></tr>
        <tr><td> 422 </td><td> Исчерпан лимит расхода ключа </td><td>  -  </td></tr>
        <tr><td> 429 </td><td> Превышен лимит запросов провайдера модели (текст ошибки — от провайдера). Повторите запрос позже </td><td>  -  </td></tr>
        <tr><td> 500 </td><td> Внутренняя ошибка или некорректный ответ провайдера модели. Повторите запрос позже </td><td>  -  </td></tr>
        <tr><td> 503 </td><td> Сервис или провайдер модели временно недоступен. Повторите запрос позже </td><td>  -  </td></tr>
     </table>
     */
    public AiGatewayImagesResponse createAiGatewayImage(Map<String, Object> requestBody) throws ApiException {
        ApiResponse<AiGatewayImagesResponse> localVarResp = createAiGatewayImageWithHttpInfo(requestBody);
        return localVarResp.getData();
    }

    /**
     * Генерация изображений
     * Чтобы сгенерировать изображения по текстовому описанию, отправьте POST-запрос на &#x60;/v1/images/generations&#x60;.  Используйте модель с &#x60;mode: image_generation&#x60; из &#x60;GET /v1/models&#x60;.
     * @param requestBody  (required)
     * @return ApiResponse&lt;AiGatewayImagesResponse&gt;
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> Сгенерированные изображения </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Некорректный запрос: не переданы или неверны параметры, модель не поддерживает операцию или временно недоступна </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Ключ не передан или имеет неверный формат </td><td>  -  </td></tr>
        <tr><td> 402 </td><td> Недостаточно средств на балансе: оценочная стоимость запроса больше доступного баланса (с учётом ещё не списанной стоимости недавних и выполняющихся запросов) </td><td>  -  </td></tr>
        <tr><td> 403 </td><td> Ключ недействителен или аккаунт заблокирован </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Модель не найдена: неизвестный или не переданный &#x60;model&#x60; </td><td>  -  </td></tr>
        <tr><td> 410 </td><td> Модель снята с обслуживания </td><td>  -  </td></tr>
        <tr><td> 422 </td><td> Исчерпан лимит расхода ключа </td><td>  -  </td></tr>
        <tr><td> 429 </td><td> Превышен лимит запросов провайдера модели (текст ошибки — от провайдера). Повторите запрос позже </td><td>  -  </td></tr>
        <tr><td> 500 </td><td> Внутренняя ошибка или некорректный ответ провайдера модели. Повторите запрос позже </td><td>  -  </td></tr>
        <tr><td> 503 </td><td> Сервис или провайдер модели временно недоступен. Повторите запрос позже </td><td>  -  </td></tr>
     </table>
     */
    public ApiResponse<AiGatewayImagesResponse> createAiGatewayImageWithHttpInfo(Map<String, Object> requestBody) throws ApiException {
        okhttp3.Call localVarCall = createAiGatewayImageValidateBeforeCall(requestBody, null);
        Type localVarReturnType = new TypeToken<AiGatewayImagesResponse>(){}.getType();
        return localVarApiClient.execute(localVarCall, localVarReturnType);
    }

    /**
     * Генерация изображений (asynchronously)
     * Чтобы сгенерировать изображения по текстовому описанию, отправьте POST-запрос на &#x60;/v1/images/generations&#x60;.  Используйте модель с &#x60;mode: image_generation&#x60; из &#x60;GET /v1/models&#x60;.
     * @param requestBody  (required)
     * @param _callback The callback to be executed when the API call finishes
     * @return The request call
     * @throws ApiException If fail to process the API call, e.g. serializing the request body object
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> Сгенерированные изображения </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Некорректный запрос: не переданы или неверны параметры, модель не поддерживает операцию или временно недоступна </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Ключ не передан или имеет неверный формат </td><td>  -  </td></tr>
        <tr><td> 402 </td><td> Недостаточно средств на балансе: оценочная стоимость запроса больше доступного баланса (с учётом ещё не списанной стоимости недавних и выполняющихся запросов) </td><td>  -  </td></tr>
        <tr><td> 403 </td><td> Ключ недействителен или аккаунт заблокирован </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Модель не найдена: неизвестный или не переданный &#x60;model&#x60; </td><td>  -  </td></tr>
        <tr><td> 410 </td><td> Модель снята с обслуживания </td><td>  -  </td></tr>
        <tr><td> 422 </td><td> Исчерпан лимит расхода ключа </td><td>  -  </td></tr>
        <tr><td> 429 </td><td> Превышен лимит запросов провайдера модели (текст ошибки — от провайдера). Повторите запрос позже </td><td>  -  </td></tr>
        <tr><td> 500 </td><td> Внутренняя ошибка или некорректный ответ провайдера модели. Повторите запрос позже </td><td>  -  </td></tr>
        <tr><td> 503 </td><td> Сервис или провайдер модели временно недоступен. Повторите запрос позже </td><td>  -  </td></tr>
     </table>
     */
    public okhttp3.Call createAiGatewayImageAsync(Map<String, Object> requestBody, final ApiCallback<AiGatewayImagesResponse> _callback) throws ApiException {

        okhttp3.Call localVarCall = createAiGatewayImageValidateBeforeCall(requestBody, _callback);
        Type localVarReturnType = new TypeToken<AiGatewayImagesResponse>(){}.getType();
        localVarApiClient.executeAsync(localVarCall, localVarReturnType, _callback);
        return localVarCall;
    }
    /**
     * Build call for createAiGatewayImageEdit
     * @param model ID модели: GPT Image (&#x60;openai/gpt-image-*&#x60;) или FLUX.2 (&#x60;black_forest_labs/flux-2-*&#x60;) (required)
     * @param image Исходное изображение или несколько файлов в поле &#x60;image[]&#x60; (required)
     * @param prompt Что изменить (required)
     * @param mask Маска: прозрачные области будут изменены (optional)
     * @param n Количество изображений (optional, default to 1)
     * @param size Размер изображения (optional)
     * @param quality Качество изображения (optional)
     * @param _callback Callback for upload/download progress
     * @return Call to execute
     * @throws ApiException If fail to serialize the request body object
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> Изменённые изображения </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Некорректный запрос: не переданы или неверны параметры, модель не поддерживает операцию или временно недоступна </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Ключ не передан или имеет неверный формат </td><td>  -  </td></tr>
        <tr><td> 402 </td><td> Недостаточно средств на балансе: оценочная стоимость запроса больше доступного баланса (с учётом ещё не списанной стоимости недавних и выполняющихся запросов) </td><td>  -  </td></tr>
        <tr><td> 403 </td><td> Ключ недействителен или аккаунт заблокирован </td><td>  -  </td></tr>
        <tr><td> 405 </td><td> Модель не поддерживает редактирование изображений или не найдена </td><td>  -  </td></tr>
        <tr><td> 410 </td><td> Модель снята с обслуживания </td><td>  -  </td></tr>
        <tr><td> 422 </td><td> Исчерпан лимит расхода ключа </td><td>  -  </td></tr>
        <tr><td> 429 </td><td> Превышен лимит запросов провайдера модели (текст ошибки — от провайдера). Повторите запрос позже </td><td>  -  </td></tr>
        <tr><td> 500 </td><td> Внутренняя ошибка или некорректный ответ провайдера модели. Повторите запрос позже </td><td>  -  </td></tr>
        <tr><td> 503 </td><td> Сервис или провайдер модели временно недоступен. Повторите запрос позже </td><td>  -  </td></tr>
     </table>
     */
    public okhttp3.Call createAiGatewayImageEditCall(String model, File image, String prompt, File mask, Integer n, String size, String quality, final ApiCallback _callback) throws ApiException {
        String basePath = null;
        // Operation Servers
        String[] localBasePaths = new String[] { "https://api.timeweb.ai" };

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
        String localVarPath = "/v1/images/edits";

        List<Pair> localVarQueryParams = new ArrayList<Pair>();
        List<Pair> localVarCollectionQueryParams = new ArrayList<Pair>();
        Map<String, String> localVarHeaderParams = new HashMap<String, String>();
        Map<String, String> localVarCookieParams = new HashMap<String, String>();
        Map<String, Object> localVarFormParams = new HashMap<String, Object>();

        if (model != null) {
            localVarFormParams.put("model", model);
        }

        if (image != null) {
            localVarFormParams.put("image", image);
        }

        if (mask != null) {
            localVarFormParams.put("mask", mask);
        }

        if (prompt != null) {
            localVarFormParams.put("prompt", prompt);
        }

        if (n != null) {
            localVarFormParams.put("n", n);
        }

        if (size != null) {
            localVarFormParams.put("size", size);
        }

        if (quality != null) {
            localVarFormParams.put("quality", quality);
        }

        final String[] localVarAccepts = {
            "application/json"
        };
        final String localVarAccept = localVarApiClient.selectHeaderAccept(localVarAccepts);
        if (localVarAccept != null) {
            localVarHeaderParams.put("Accept", localVarAccept);
        }

        final String[] localVarContentTypes = {
            "multipart/form-data"
        };
        final String localVarContentType = localVarApiClient.selectHeaderContentType(localVarContentTypes);
        if (localVarContentType != null) {
            localVarHeaderParams.put("Content-Type", localVarContentType);
        }

        String[] localVarAuthNames = new String[] { "AiGatewayKey" };
        return localVarApiClient.buildCall(basePath, localVarPath, "POST", localVarQueryParams, localVarCollectionQueryParams, localVarPostBody, localVarHeaderParams, localVarCookieParams, localVarFormParams, localVarAuthNames, _callback);
    }

    @SuppressWarnings("rawtypes")
    private okhttp3.Call createAiGatewayImageEditValidateBeforeCall(String model, File image, String prompt, File mask, Integer n, String size, String quality, final ApiCallback _callback) throws ApiException {
        // verify the required parameter 'model' is set
        if (model == null) {
            throw new ApiException("Missing the required parameter 'model' when calling createAiGatewayImageEdit(Async)");
        }

        // verify the required parameter 'image' is set
        if (image == null) {
            throw new ApiException("Missing the required parameter 'image' when calling createAiGatewayImageEdit(Async)");
        }

        // verify the required parameter 'prompt' is set
        if (prompt == null) {
            throw new ApiException("Missing the required parameter 'prompt' when calling createAiGatewayImageEdit(Async)");
        }

        return createAiGatewayImageEditCall(model, image, prompt, mask, n, size, quality, _callback);

    }

    /**
     * Редактирование изображений
     * Чтобы изменить или дополнить изображения по описанию, отправьте POST-запрос на &#x60;/v1/images/edits&#x60; (multipart/form-data).  Доступно для моделей GPT Image (&#x60;openai/gpt-image-*&#x60;) и FLUX.2 (&#x60;black_forest_labs/flux-2-*&#x60;). Для других моделей, в том числе неизвестных, возвращается ошибка 405.
     * @param model ID модели: GPT Image (&#x60;openai/gpt-image-*&#x60;) или FLUX.2 (&#x60;black_forest_labs/flux-2-*&#x60;) (required)
     * @param image Исходное изображение или несколько файлов в поле &#x60;image[]&#x60; (required)
     * @param prompt Что изменить (required)
     * @param mask Маска: прозрачные области будут изменены (optional)
     * @param n Количество изображений (optional, default to 1)
     * @param size Размер изображения (optional)
     * @param quality Качество изображения (optional)
     * @return AiGatewayImagesResponse
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> Изменённые изображения </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Некорректный запрос: не переданы или неверны параметры, модель не поддерживает операцию или временно недоступна </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Ключ не передан или имеет неверный формат </td><td>  -  </td></tr>
        <tr><td> 402 </td><td> Недостаточно средств на балансе: оценочная стоимость запроса больше доступного баланса (с учётом ещё не списанной стоимости недавних и выполняющихся запросов) </td><td>  -  </td></tr>
        <tr><td> 403 </td><td> Ключ недействителен или аккаунт заблокирован </td><td>  -  </td></tr>
        <tr><td> 405 </td><td> Модель не поддерживает редактирование изображений или не найдена </td><td>  -  </td></tr>
        <tr><td> 410 </td><td> Модель снята с обслуживания </td><td>  -  </td></tr>
        <tr><td> 422 </td><td> Исчерпан лимит расхода ключа </td><td>  -  </td></tr>
        <tr><td> 429 </td><td> Превышен лимит запросов провайдера модели (текст ошибки — от провайдера). Повторите запрос позже </td><td>  -  </td></tr>
        <tr><td> 500 </td><td> Внутренняя ошибка или некорректный ответ провайдера модели. Повторите запрос позже </td><td>  -  </td></tr>
        <tr><td> 503 </td><td> Сервис или провайдер модели временно недоступен. Повторите запрос позже </td><td>  -  </td></tr>
     </table>
     */
    public AiGatewayImagesResponse createAiGatewayImageEdit(String model, File image, String prompt, File mask, Integer n, String size, String quality) throws ApiException {
        ApiResponse<AiGatewayImagesResponse> localVarResp = createAiGatewayImageEditWithHttpInfo(model, image, prompt, mask, n, size, quality);
        return localVarResp.getData();
    }

    /**
     * Редактирование изображений
     * Чтобы изменить или дополнить изображения по описанию, отправьте POST-запрос на &#x60;/v1/images/edits&#x60; (multipart/form-data).  Доступно для моделей GPT Image (&#x60;openai/gpt-image-*&#x60;) и FLUX.2 (&#x60;black_forest_labs/flux-2-*&#x60;). Для других моделей, в том числе неизвестных, возвращается ошибка 405.
     * @param model ID модели: GPT Image (&#x60;openai/gpt-image-*&#x60;) или FLUX.2 (&#x60;black_forest_labs/flux-2-*&#x60;) (required)
     * @param image Исходное изображение или несколько файлов в поле &#x60;image[]&#x60; (required)
     * @param prompt Что изменить (required)
     * @param mask Маска: прозрачные области будут изменены (optional)
     * @param n Количество изображений (optional, default to 1)
     * @param size Размер изображения (optional)
     * @param quality Качество изображения (optional)
     * @return ApiResponse&lt;AiGatewayImagesResponse&gt;
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> Изменённые изображения </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Некорректный запрос: не переданы или неверны параметры, модель не поддерживает операцию или временно недоступна </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Ключ не передан или имеет неверный формат </td><td>  -  </td></tr>
        <tr><td> 402 </td><td> Недостаточно средств на балансе: оценочная стоимость запроса больше доступного баланса (с учётом ещё не списанной стоимости недавних и выполняющихся запросов) </td><td>  -  </td></tr>
        <tr><td> 403 </td><td> Ключ недействителен или аккаунт заблокирован </td><td>  -  </td></tr>
        <tr><td> 405 </td><td> Модель не поддерживает редактирование изображений или не найдена </td><td>  -  </td></tr>
        <tr><td> 410 </td><td> Модель снята с обслуживания </td><td>  -  </td></tr>
        <tr><td> 422 </td><td> Исчерпан лимит расхода ключа </td><td>  -  </td></tr>
        <tr><td> 429 </td><td> Превышен лимит запросов провайдера модели (текст ошибки — от провайдера). Повторите запрос позже </td><td>  -  </td></tr>
        <tr><td> 500 </td><td> Внутренняя ошибка или некорректный ответ провайдера модели. Повторите запрос позже </td><td>  -  </td></tr>
        <tr><td> 503 </td><td> Сервис или провайдер модели временно недоступен. Повторите запрос позже </td><td>  -  </td></tr>
     </table>
     */
    public ApiResponse<AiGatewayImagesResponse> createAiGatewayImageEditWithHttpInfo(String model, File image, String prompt, File mask, Integer n, String size, String quality) throws ApiException {
        okhttp3.Call localVarCall = createAiGatewayImageEditValidateBeforeCall(model, image, prompt, mask, n, size, quality, null);
        Type localVarReturnType = new TypeToken<AiGatewayImagesResponse>(){}.getType();
        return localVarApiClient.execute(localVarCall, localVarReturnType);
    }

    /**
     * Редактирование изображений (asynchronously)
     * Чтобы изменить или дополнить изображения по описанию, отправьте POST-запрос на &#x60;/v1/images/edits&#x60; (multipart/form-data).  Доступно для моделей GPT Image (&#x60;openai/gpt-image-*&#x60;) и FLUX.2 (&#x60;black_forest_labs/flux-2-*&#x60;). Для других моделей, в том числе неизвестных, возвращается ошибка 405.
     * @param model ID модели: GPT Image (&#x60;openai/gpt-image-*&#x60;) или FLUX.2 (&#x60;black_forest_labs/flux-2-*&#x60;) (required)
     * @param image Исходное изображение или несколько файлов в поле &#x60;image[]&#x60; (required)
     * @param prompt Что изменить (required)
     * @param mask Маска: прозрачные области будут изменены (optional)
     * @param n Количество изображений (optional, default to 1)
     * @param size Размер изображения (optional)
     * @param quality Качество изображения (optional)
     * @param _callback The callback to be executed when the API call finishes
     * @return The request call
     * @throws ApiException If fail to process the API call, e.g. serializing the request body object
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> Изменённые изображения </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Некорректный запрос: не переданы или неверны параметры, модель не поддерживает операцию или временно недоступна </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Ключ не передан или имеет неверный формат </td><td>  -  </td></tr>
        <tr><td> 402 </td><td> Недостаточно средств на балансе: оценочная стоимость запроса больше доступного баланса (с учётом ещё не списанной стоимости недавних и выполняющихся запросов) </td><td>  -  </td></tr>
        <tr><td> 403 </td><td> Ключ недействителен или аккаунт заблокирован </td><td>  -  </td></tr>
        <tr><td> 405 </td><td> Модель не поддерживает редактирование изображений или не найдена </td><td>  -  </td></tr>
        <tr><td> 410 </td><td> Модель снята с обслуживания </td><td>  -  </td></tr>
        <tr><td> 422 </td><td> Исчерпан лимит расхода ключа </td><td>  -  </td></tr>
        <tr><td> 429 </td><td> Превышен лимит запросов провайдера модели (текст ошибки — от провайдера). Повторите запрос позже </td><td>  -  </td></tr>
        <tr><td> 500 </td><td> Внутренняя ошибка или некорректный ответ провайдера модели. Повторите запрос позже </td><td>  -  </td></tr>
        <tr><td> 503 </td><td> Сервис или провайдер модели временно недоступен. Повторите запрос позже </td><td>  -  </td></tr>
     </table>
     */
    public okhttp3.Call createAiGatewayImageEditAsync(String model, File image, String prompt, File mask, Integer n, String size, String quality, final ApiCallback<AiGatewayImagesResponse> _callback) throws ApiException {

        okhttp3.Call localVarCall = createAiGatewayImageEditValidateBeforeCall(model, image, prompt, mask, n, size, quality, _callback);
        Type localVarReturnType = new TypeToken<AiGatewayImagesResponse>(){}.getType();
        localVarApiClient.executeAsync(localVarCall, localVarReturnType, _callback);
        return localVarCall;
    }
    /**
     * Build call for createAiGatewayResponse
     * @param requestBody  (required)
     * @param _callback Callback for upload/download progress
     * @return Call to execute
     * @throws ApiException If fail to serialize the request body object
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> Ответ или, с &#x60;\&quot;stream\&quot;: true&#x60;, поток событий </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Некорректный запрос: не переданы или неверны параметры, модель не поддерживает операцию или временно недоступна </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Ключ не передан или имеет неверный формат </td><td>  -  </td></tr>
        <tr><td> 402 </td><td> Недостаточно средств на балансе: оценочная стоимость запроса больше доступного баланса (с учётом ещё не списанной стоимости недавних и выполняющихся запросов) </td><td>  -  </td></tr>
        <tr><td> 403 </td><td> Ключ недействителен или аккаунт заблокирован </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Модель не найдена: неизвестный или не переданный &#x60;model&#x60; </td><td>  -  </td></tr>
        <tr><td> 410 </td><td> Модель снята с обслуживания </td><td>  -  </td></tr>
        <tr><td> 422 </td><td> Исчерпан лимит расхода ключа </td><td>  -  </td></tr>
        <tr><td> 429 </td><td> Превышен лимит запросов провайдера модели (текст ошибки — от провайдера). Повторите запрос позже </td><td>  -  </td></tr>
        <tr><td> 500 </td><td> Внутренняя ошибка или некорректный ответ провайдера модели. Повторите запрос позже </td><td>  -  </td></tr>
        <tr><td> 503 </td><td> Сервис или провайдер модели временно недоступен. Повторите запрос позже </td><td>  -  </td></tr>
     </table>
     */
    public okhttp3.Call createAiGatewayResponseCall(Map<String, Object> requestBody, final ApiCallback _callback) throws ApiException {
        String basePath = null;
        // Operation Servers
        String[] localBasePaths = new String[] { "https://api.timeweb.ai" };

        // Determine Base Path to Use
        if (localCustomBaseUrl != null){
            basePath = localCustomBaseUrl;
        } else if ( localBasePaths.length > 0 ) {
            basePath = localBasePaths[localHostIndex];
        } else {
            basePath = null;
        }

        Object localVarPostBody = requestBody;

        // create path and map variables
        String localVarPath = "/v1/responses";

        List<Pair> localVarQueryParams = new ArrayList<Pair>();
        List<Pair> localVarCollectionQueryParams = new ArrayList<Pair>();
        Map<String, String> localVarHeaderParams = new HashMap<String, String>();
        Map<String, String> localVarCookieParams = new HashMap<String, String>();
        Map<String, Object> localVarFormParams = new HashMap<String, Object>();

        final String[] localVarAccepts = {
            "application/json",
            "text/event-stream"
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

        String[] localVarAuthNames = new String[] { "AiGatewayKey" };
        return localVarApiClient.buildCall(basePath, localVarPath, "POST", localVarQueryParams, localVarCollectionQueryParams, localVarPostBody, localVarHeaderParams, localVarCookieParams, localVarFormParams, localVarAuthNames, _callback);
    }

    @SuppressWarnings("rawtypes")
    private okhttp3.Call createAiGatewayResponseValidateBeforeCall(Map<String, Object> requestBody, final ApiCallback _callback) throws ApiException {
        // verify the required parameter 'requestBody' is set
        if (requestBody == null) {
            throw new ApiException("Missing the required parameter 'requestBody' when calling createAiGatewayResponse(Async)");
        }

        return createAiGatewayResponseCall(requestBody, _callback);

    }

    /**
     * Создание ответа (Responses API)
     * Чтобы получить ответ модели через OpenAI Responses API, отправьте POST-запрос на &#x60;/v1/responses&#x60;.  Диалог можно продолжить по &#x60;previous_response_id&#x60;, долгие запросы — выполнить в фоне с &#x60;\&quot;background\&quot;: true&#x60; и получать результат через &#x60;GET /v1/responses/{response_id}&#x60;.  С &#x60;\&quot;stream\&quot;: true&#x60; ответ приходит потоком типизированных событий Server-Sent Events (&#x60;response.created&#x60;, &#x60;response.output_text.delta&#x60;, &#x60;response.completed&#x60; и др.), в конце потока — &#x60;data: [DONE]&#x60;.  Если у ключа настроены запасные модели и запрошенная модель не ответила, запрос повторяется на запасных (заголовок &#x60;x-timeweb-attempted-fallbacks&#x60;).
     * @param requestBody  (required)
     * @return AiGatewayResponse
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> Ответ или, с &#x60;\&quot;stream\&quot;: true&#x60;, поток событий </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Некорректный запрос: не переданы или неверны параметры, модель не поддерживает операцию или временно недоступна </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Ключ не передан или имеет неверный формат </td><td>  -  </td></tr>
        <tr><td> 402 </td><td> Недостаточно средств на балансе: оценочная стоимость запроса больше доступного баланса (с учётом ещё не списанной стоимости недавних и выполняющихся запросов) </td><td>  -  </td></tr>
        <tr><td> 403 </td><td> Ключ недействителен или аккаунт заблокирован </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Модель не найдена: неизвестный или не переданный &#x60;model&#x60; </td><td>  -  </td></tr>
        <tr><td> 410 </td><td> Модель снята с обслуживания </td><td>  -  </td></tr>
        <tr><td> 422 </td><td> Исчерпан лимит расхода ключа </td><td>  -  </td></tr>
        <tr><td> 429 </td><td> Превышен лимит запросов провайдера модели (текст ошибки — от провайдера). Повторите запрос позже </td><td>  -  </td></tr>
        <tr><td> 500 </td><td> Внутренняя ошибка или некорректный ответ провайдера модели. Повторите запрос позже </td><td>  -  </td></tr>
        <tr><td> 503 </td><td> Сервис или провайдер модели временно недоступен. Повторите запрос позже </td><td>  -  </td></tr>
     </table>
     */
    public AiGatewayResponse createAiGatewayResponse(Map<String, Object> requestBody) throws ApiException {
        ApiResponse<AiGatewayResponse> localVarResp = createAiGatewayResponseWithHttpInfo(requestBody);
        return localVarResp.getData();
    }

    /**
     * Создание ответа (Responses API)
     * Чтобы получить ответ модели через OpenAI Responses API, отправьте POST-запрос на &#x60;/v1/responses&#x60;.  Диалог можно продолжить по &#x60;previous_response_id&#x60;, долгие запросы — выполнить в фоне с &#x60;\&quot;background\&quot;: true&#x60; и получать результат через &#x60;GET /v1/responses/{response_id}&#x60;.  С &#x60;\&quot;stream\&quot;: true&#x60; ответ приходит потоком типизированных событий Server-Sent Events (&#x60;response.created&#x60;, &#x60;response.output_text.delta&#x60;, &#x60;response.completed&#x60; и др.), в конце потока — &#x60;data: [DONE]&#x60;.  Если у ключа настроены запасные модели и запрошенная модель не ответила, запрос повторяется на запасных (заголовок &#x60;x-timeweb-attempted-fallbacks&#x60;).
     * @param requestBody  (required)
     * @return ApiResponse&lt;AiGatewayResponse&gt;
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> Ответ или, с &#x60;\&quot;stream\&quot;: true&#x60;, поток событий </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Некорректный запрос: не переданы или неверны параметры, модель не поддерживает операцию или временно недоступна </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Ключ не передан или имеет неверный формат </td><td>  -  </td></tr>
        <tr><td> 402 </td><td> Недостаточно средств на балансе: оценочная стоимость запроса больше доступного баланса (с учётом ещё не списанной стоимости недавних и выполняющихся запросов) </td><td>  -  </td></tr>
        <tr><td> 403 </td><td> Ключ недействителен или аккаунт заблокирован </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Модель не найдена: неизвестный или не переданный &#x60;model&#x60; </td><td>  -  </td></tr>
        <tr><td> 410 </td><td> Модель снята с обслуживания </td><td>  -  </td></tr>
        <tr><td> 422 </td><td> Исчерпан лимит расхода ключа </td><td>  -  </td></tr>
        <tr><td> 429 </td><td> Превышен лимит запросов провайдера модели (текст ошибки — от провайдера). Повторите запрос позже </td><td>  -  </td></tr>
        <tr><td> 500 </td><td> Внутренняя ошибка или некорректный ответ провайдера модели. Повторите запрос позже </td><td>  -  </td></tr>
        <tr><td> 503 </td><td> Сервис или провайдер модели временно недоступен. Повторите запрос позже </td><td>  -  </td></tr>
     </table>
     */
    public ApiResponse<AiGatewayResponse> createAiGatewayResponseWithHttpInfo(Map<String, Object> requestBody) throws ApiException {
        okhttp3.Call localVarCall = createAiGatewayResponseValidateBeforeCall(requestBody, null);
        Type localVarReturnType = new TypeToken<AiGatewayResponse>(){}.getType();
        return localVarApiClient.execute(localVarCall, localVarReturnType);
    }

    /**
     * Создание ответа (Responses API) (asynchronously)
     * Чтобы получить ответ модели через OpenAI Responses API, отправьте POST-запрос на &#x60;/v1/responses&#x60;.  Диалог можно продолжить по &#x60;previous_response_id&#x60;, долгие запросы — выполнить в фоне с &#x60;\&quot;background\&quot;: true&#x60; и получать результат через &#x60;GET /v1/responses/{response_id}&#x60;.  С &#x60;\&quot;stream\&quot;: true&#x60; ответ приходит потоком типизированных событий Server-Sent Events (&#x60;response.created&#x60;, &#x60;response.output_text.delta&#x60;, &#x60;response.completed&#x60; и др.), в конце потока — &#x60;data: [DONE]&#x60;.  Если у ключа настроены запасные модели и запрошенная модель не ответила, запрос повторяется на запасных (заголовок &#x60;x-timeweb-attempted-fallbacks&#x60;).
     * @param requestBody  (required)
     * @param _callback The callback to be executed when the API call finishes
     * @return The request call
     * @throws ApiException If fail to process the API call, e.g. serializing the request body object
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> Ответ или, с &#x60;\&quot;stream\&quot;: true&#x60;, поток событий </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Некорректный запрос: не переданы или неверны параметры, модель не поддерживает операцию или временно недоступна </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Ключ не передан или имеет неверный формат </td><td>  -  </td></tr>
        <tr><td> 402 </td><td> Недостаточно средств на балансе: оценочная стоимость запроса больше доступного баланса (с учётом ещё не списанной стоимости недавних и выполняющихся запросов) </td><td>  -  </td></tr>
        <tr><td> 403 </td><td> Ключ недействителен или аккаунт заблокирован </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Модель не найдена: неизвестный или не переданный &#x60;model&#x60; </td><td>  -  </td></tr>
        <tr><td> 410 </td><td> Модель снята с обслуживания </td><td>  -  </td></tr>
        <tr><td> 422 </td><td> Исчерпан лимит расхода ключа </td><td>  -  </td></tr>
        <tr><td> 429 </td><td> Превышен лимит запросов провайдера модели (текст ошибки — от провайдера). Повторите запрос позже </td><td>  -  </td></tr>
        <tr><td> 500 </td><td> Внутренняя ошибка или некорректный ответ провайдера модели. Повторите запрос позже </td><td>  -  </td></tr>
        <tr><td> 503 </td><td> Сервис или провайдер модели временно недоступен. Повторите запрос позже </td><td>  -  </td></tr>
     </table>
     */
    public okhttp3.Call createAiGatewayResponseAsync(Map<String, Object> requestBody, final ApiCallback<AiGatewayResponse> _callback) throws ApiException {

        okhttp3.Call localVarCall = createAiGatewayResponseValidateBeforeCall(requestBody, _callback);
        Type localVarReturnType = new TypeToken<AiGatewayResponse>(){}.getType();
        localVarApiClient.executeAsync(localVarCall, localVarReturnType, _callback);
        return localVarCall;
    }
    /**
     * Build call for createAiGatewaySpeech
     * @param requestBody  (required)
     * @param _callback Callback for upload/download progress
     * @return Call to execute
     * @throws ApiException If fail to serialize the request body object
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> Аудиофайл </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Некорректный запрос: не переданы или неверны параметры, модель не поддерживает операцию или временно недоступна </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Ключ не передан или имеет неверный формат </td><td>  -  </td></tr>
        <tr><td> 402 </td><td> Недостаточно средств на балансе: оценочная стоимость запроса больше доступного баланса (с учётом ещё не списанной стоимости недавних и выполняющихся запросов) </td><td>  -  </td></tr>
        <tr><td> 403 </td><td> Ключ недействителен или аккаунт заблокирован </td><td>  -  </td></tr>
        <tr><td> 410 </td><td> Модель снята с обслуживания </td><td>  -  </td></tr>
        <tr><td> 422 </td><td> Исчерпан лимит расхода ключа </td><td>  -  </td></tr>
        <tr><td> 429 </td><td> Превышен лимит запросов провайдера модели (текст ошибки — от провайдера). Повторите запрос позже </td><td>  -  </td></tr>
        <tr><td> 500 </td><td> Внутренняя ошибка или некорректный ответ провайдера модели. Повторите запрос позже </td><td>  -  </td></tr>
        <tr><td> 503 </td><td> Сервис или провайдер модели временно недоступен. Повторите запрос позже </td><td>  -  </td></tr>
     </table>
     */
    public okhttp3.Call createAiGatewaySpeechCall(Map<String, Object> requestBody, final ApiCallback _callback) throws ApiException {
        String basePath = null;
        // Operation Servers
        String[] localBasePaths = new String[] { "https://api.timeweb.ai" };

        // Determine Base Path to Use
        if (localCustomBaseUrl != null){
            basePath = localCustomBaseUrl;
        } else if ( localBasePaths.length > 0 ) {
            basePath = localBasePaths[localHostIndex];
        } else {
            basePath = null;
        }

        Object localVarPostBody = requestBody;

        // create path and map variables
        String localVarPath = "/v1/audio/speech";

        List<Pair> localVarQueryParams = new ArrayList<Pair>();
        List<Pair> localVarCollectionQueryParams = new ArrayList<Pair>();
        Map<String, String> localVarHeaderParams = new HashMap<String, String>();
        Map<String, String> localVarCookieParams = new HashMap<String, String>();
        Map<String, Object> localVarFormParams = new HashMap<String, Object>();

        final String[] localVarAccepts = {
            "audio/mpeg",
            "audio/wav",
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

        String[] localVarAuthNames = new String[] { "AiGatewayKey" };
        return localVarApiClient.buildCall(basePath, localVarPath, "POST", localVarQueryParams, localVarCollectionQueryParams, localVarPostBody, localVarHeaderParams, localVarCookieParams, localVarFormParams, localVarAuthNames, _callback);
    }

    @SuppressWarnings("rawtypes")
    private okhttp3.Call createAiGatewaySpeechValidateBeforeCall(Map<String, Object> requestBody, final ApiCallback _callback) throws ApiException {
        // verify the required parameter 'requestBody' is set
        if (requestBody == null) {
            throw new ApiException("Missing the required parameter 'requestBody' when calling createAiGatewaySpeech(Async)");
        }

        return createAiGatewaySpeechCall(requestBody, _callback);

    }

    /**
     * Синтез речи
     * Чтобы озвучить текст, отправьте POST-запрос на &#x60;/v1/audio/speech&#x60;.  Используйте модель с &#x60;mode: audio_speech&#x60; из &#x60;GET /v1/models&#x60;. Ответ — аудиофайл в формате &#x60;response_format&#x60; (по умолчанию &#x60;mp3&#x60;).
     * @param requestBody  (required)
     * @return File
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> Аудиофайл </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Некорректный запрос: не переданы или неверны параметры, модель не поддерживает операцию или временно недоступна </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Ключ не передан или имеет неверный формат </td><td>  -  </td></tr>
        <tr><td> 402 </td><td> Недостаточно средств на балансе: оценочная стоимость запроса больше доступного баланса (с учётом ещё не списанной стоимости недавних и выполняющихся запросов) </td><td>  -  </td></tr>
        <tr><td> 403 </td><td> Ключ недействителен или аккаунт заблокирован </td><td>  -  </td></tr>
        <tr><td> 410 </td><td> Модель снята с обслуживания </td><td>  -  </td></tr>
        <tr><td> 422 </td><td> Исчерпан лимит расхода ключа </td><td>  -  </td></tr>
        <tr><td> 429 </td><td> Превышен лимит запросов провайдера модели (текст ошибки — от провайдера). Повторите запрос позже </td><td>  -  </td></tr>
        <tr><td> 500 </td><td> Внутренняя ошибка или некорректный ответ провайдера модели. Повторите запрос позже </td><td>  -  </td></tr>
        <tr><td> 503 </td><td> Сервис или провайдер модели временно недоступен. Повторите запрос позже </td><td>  -  </td></tr>
     </table>
     */
    public File createAiGatewaySpeech(Map<String, Object> requestBody) throws ApiException {
        ApiResponse<File> localVarResp = createAiGatewaySpeechWithHttpInfo(requestBody);
        return localVarResp.getData();
    }

    /**
     * Синтез речи
     * Чтобы озвучить текст, отправьте POST-запрос на &#x60;/v1/audio/speech&#x60;.  Используйте модель с &#x60;mode: audio_speech&#x60; из &#x60;GET /v1/models&#x60;. Ответ — аудиофайл в формате &#x60;response_format&#x60; (по умолчанию &#x60;mp3&#x60;).
     * @param requestBody  (required)
     * @return ApiResponse&lt;File&gt;
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> Аудиофайл </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Некорректный запрос: не переданы или неверны параметры, модель не поддерживает операцию или временно недоступна </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Ключ не передан или имеет неверный формат </td><td>  -  </td></tr>
        <tr><td> 402 </td><td> Недостаточно средств на балансе: оценочная стоимость запроса больше доступного баланса (с учётом ещё не списанной стоимости недавних и выполняющихся запросов) </td><td>  -  </td></tr>
        <tr><td> 403 </td><td> Ключ недействителен или аккаунт заблокирован </td><td>  -  </td></tr>
        <tr><td> 410 </td><td> Модель снята с обслуживания </td><td>  -  </td></tr>
        <tr><td> 422 </td><td> Исчерпан лимит расхода ключа </td><td>  -  </td></tr>
        <tr><td> 429 </td><td> Превышен лимит запросов провайдера модели (текст ошибки — от провайдера). Повторите запрос позже </td><td>  -  </td></tr>
        <tr><td> 500 </td><td> Внутренняя ошибка или некорректный ответ провайдера модели. Повторите запрос позже </td><td>  -  </td></tr>
        <tr><td> 503 </td><td> Сервис или провайдер модели временно недоступен. Повторите запрос позже </td><td>  -  </td></tr>
     </table>
     */
    public ApiResponse<File> createAiGatewaySpeechWithHttpInfo(Map<String, Object> requestBody) throws ApiException {
        okhttp3.Call localVarCall = createAiGatewaySpeechValidateBeforeCall(requestBody, null);
        Type localVarReturnType = new TypeToken<File>(){}.getType();
        return localVarApiClient.execute(localVarCall, localVarReturnType);
    }

    /**
     * Синтез речи (asynchronously)
     * Чтобы озвучить текст, отправьте POST-запрос на &#x60;/v1/audio/speech&#x60;.  Используйте модель с &#x60;mode: audio_speech&#x60; из &#x60;GET /v1/models&#x60;. Ответ — аудиофайл в формате &#x60;response_format&#x60; (по умолчанию &#x60;mp3&#x60;).
     * @param requestBody  (required)
     * @param _callback The callback to be executed when the API call finishes
     * @return The request call
     * @throws ApiException If fail to process the API call, e.g. serializing the request body object
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> Аудиофайл </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Некорректный запрос: не переданы или неверны параметры, модель не поддерживает операцию или временно недоступна </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Ключ не передан или имеет неверный формат </td><td>  -  </td></tr>
        <tr><td> 402 </td><td> Недостаточно средств на балансе: оценочная стоимость запроса больше доступного баланса (с учётом ещё не списанной стоимости недавних и выполняющихся запросов) </td><td>  -  </td></tr>
        <tr><td> 403 </td><td> Ключ недействителен или аккаунт заблокирован </td><td>  -  </td></tr>
        <tr><td> 410 </td><td> Модель снята с обслуживания </td><td>  -  </td></tr>
        <tr><td> 422 </td><td> Исчерпан лимит расхода ключа </td><td>  -  </td></tr>
        <tr><td> 429 </td><td> Превышен лимит запросов провайдера модели (текст ошибки — от провайдера). Повторите запрос позже </td><td>  -  </td></tr>
        <tr><td> 500 </td><td> Внутренняя ошибка или некорректный ответ провайдера модели. Повторите запрос позже </td><td>  -  </td></tr>
        <tr><td> 503 </td><td> Сервис или провайдер модели временно недоступен. Повторите запрос позже </td><td>  -  </td></tr>
     </table>
     */
    public okhttp3.Call createAiGatewaySpeechAsync(Map<String, Object> requestBody, final ApiCallback<File> _callback) throws ApiException {

        okhttp3.Call localVarCall = createAiGatewaySpeechValidateBeforeCall(requestBody, _callback);
        Type localVarReturnType = new TypeToken<File>(){}.getType();
        localVarApiClient.executeAsync(localVarCall, localVarReturnType, _callback);
        return localVarCall;
    }
    /**
     * Build call for createAiGatewayTranscription
     * @param _file Аудиофайл (required)
     * @param model ID модели распознавания речи (&#x60;*-transcribe&#x60;) (required)
     * @param language Язык аудио в формате ISO-639-1 (optional)
     * @param prompt Подсказка для распознавания: термины, имена, стиль (optional)
     * @param responseFormat Формат ответа (optional)
     * @param temperature Температура выборки (optional)
     * @param _callback Callback for upload/download progress
     * @return Call to execute
     * @throws ApiException If fail to serialize the request body object
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> Распознанный текст </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Некорректный запрос, например повреждённый аудиофайл </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Ключ не передан или имеет неверный формат </td><td>  -  </td></tr>
        <tr><td> 402 </td><td> Недостаточно средств на балансе: оценочная стоимость запроса больше доступного баланса (с учётом ещё не списанной стоимости недавних и выполняющихся запросов) </td><td>  -  </td></tr>
        <tr><td> 403 </td><td> Ключ недействителен или аккаунт заблокирован </td><td>  -  </td></tr>
        <tr><td> 405 </td><td> Модель не поддерживает распознавание речи или не найдена </td><td>  -  </td></tr>
        <tr><td> 410 </td><td> Модель снята с обслуживания </td><td>  -  </td></tr>
        <tr><td> 422 </td><td> Исчерпан лимит расхода ключа (формат OpenAI) или не прошла проверка параметров multipart-запроса, например не передан файл (формат &#x60;{\&quot;detail\&quot;: ...}&#x60;) </td><td>  -  </td></tr>
        <tr><td> 429 </td><td> Превышен лимит запросов провайдера модели (текст ошибки — от провайдера). Повторите запрос позже </td><td>  -  </td></tr>
        <tr><td> 500 </td><td> Внутренняя ошибка или некорректный ответ провайдера модели. Повторите запрос позже </td><td>  -  </td></tr>
        <tr><td> 503 </td><td> Сервис или провайдер модели временно недоступен. Повторите запрос позже </td><td>  -  </td></tr>
     </table>
     */
    public okhttp3.Call createAiGatewayTranscriptionCall(File _file, String model, String language, String prompt, String responseFormat, BigDecimal temperature, final ApiCallback _callback) throws ApiException {
        String basePath = null;
        // Operation Servers
        String[] localBasePaths = new String[] { "https://api.timeweb.ai" };

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
        String localVarPath = "/v1/audio/transcriptions";

        List<Pair> localVarQueryParams = new ArrayList<Pair>();
        List<Pair> localVarCollectionQueryParams = new ArrayList<Pair>();
        Map<String, String> localVarHeaderParams = new HashMap<String, String>();
        Map<String, String> localVarCookieParams = new HashMap<String, String>();
        Map<String, Object> localVarFormParams = new HashMap<String, Object>();

        if (_file != null) {
            localVarFormParams.put("file", _file);
        }

        if (model != null) {
            localVarFormParams.put("model", model);
        }

        if (language != null) {
            localVarFormParams.put("language", language);
        }

        if (prompt != null) {
            localVarFormParams.put("prompt", prompt);
        }

        if (responseFormat != null) {
            localVarFormParams.put("response_format", responseFormat);
        }

        if (temperature != null) {
            localVarFormParams.put("temperature", temperature);
        }

        final String[] localVarAccepts = {
            "application/json"
        };
        final String localVarAccept = localVarApiClient.selectHeaderAccept(localVarAccepts);
        if (localVarAccept != null) {
            localVarHeaderParams.put("Accept", localVarAccept);
        }

        final String[] localVarContentTypes = {
            "multipart/form-data"
        };
        final String localVarContentType = localVarApiClient.selectHeaderContentType(localVarContentTypes);
        if (localVarContentType != null) {
            localVarHeaderParams.put("Content-Type", localVarContentType);
        }

        String[] localVarAuthNames = new String[] { "AiGatewayKey" };
        return localVarApiClient.buildCall(basePath, localVarPath, "POST", localVarQueryParams, localVarCollectionQueryParams, localVarPostBody, localVarHeaderParams, localVarCookieParams, localVarFormParams, localVarAuthNames, _callback);
    }

    @SuppressWarnings("rawtypes")
    private okhttp3.Call createAiGatewayTranscriptionValidateBeforeCall(File _file, String model, String language, String prompt, String responseFormat, BigDecimal temperature, final ApiCallback _callback) throws ApiException {
        // verify the required parameter '_file' is set
        if (_file == null) {
            throw new ApiException("Missing the required parameter '_file' when calling createAiGatewayTranscription(Async)");
        }

        // verify the required parameter 'model' is set
        if (model == null) {
            throw new ApiException("Missing the required parameter 'model' when calling createAiGatewayTranscription(Async)");
        }

        return createAiGatewayTranscriptionCall(_file, model, language, prompt, responseFormat, temperature, _callback);

    }

    /**
     * Распознавание речи
     * Чтобы распознать речь в аудиофайле, отправьте POST-запрос на &#x60;/v1/audio/transcriptions&#x60; (multipart/form-data).  Доступно для моделей распознавания речи &#x60;*-transcribe&#x60; (например, &#x60;openai/gpt-4o-mini-transcribe&#x60;). Для других моделей, в том числе неизвестных, возвращается ошибка 405. Ответ возвращается в JSON.
     * @param _file Аудиофайл (required)
     * @param model ID модели распознавания речи (&#x60;*-transcribe&#x60;) (required)
     * @param language Язык аудио в формате ISO-639-1 (optional)
     * @param prompt Подсказка для распознавания: термины, имена, стиль (optional)
     * @param responseFormat Формат ответа (optional)
     * @param temperature Температура выборки (optional)
     * @return AiGatewayTranscription
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> Распознанный текст </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Некорректный запрос, например повреждённый аудиофайл </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Ключ не передан или имеет неверный формат </td><td>  -  </td></tr>
        <tr><td> 402 </td><td> Недостаточно средств на балансе: оценочная стоимость запроса больше доступного баланса (с учётом ещё не списанной стоимости недавних и выполняющихся запросов) </td><td>  -  </td></tr>
        <tr><td> 403 </td><td> Ключ недействителен или аккаунт заблокирован </td><td>  -  </td></tr>
        <tr><td> 405 </td><td> Модель не поддерживает распознавание речи или не найдена </td><td>  -  </td></tr>
        <tr><td> 410 </td><td> Модель снята с обслуживания </td><td>  -  </td></tr>
        <tr><td> 422 </td><td> Исчерпан лимит расхода ключа (формат OpenAI) или не прошла проверка параметров multipart-запроса, например не передан файл (формат &#x60;{\&quot;detail\&quot;: ...}&#x60;) </td><td>  -  </td></tr>
        <tr><td> 429 </td><td> Превышен лимит запросов провайдера модели (текст ошибки — от провайдера). Повторите запрос позже </td><td>  -  </td></tr>
        <tr><td> 500 </td><td> Внутренняя ошибка или некорректный ответ провайдера модели. Повторите запрос позже </td><td>  -  </td></tr>
        <tr><td> 503 </td><td> Сервис или провайдер модели временно недоступен. Повторите запрос позже </td><td>  -  </td></tr>
     </table>
     */
    public AiGatewayTranscription createAiGatewayTranscription(File _file, String model, String language, String prompt, String responseFormat, BigDecimal temperature) throws ApiException {
        ApiResponse<AiGatewayTranscription> localVarResp = createAiGatewayTranscriptionWithHttpInfo(_file, model, language, prompt, responseFormat, temperature);
        return localVarResp.getData();
    }

    /**
     * Распознавание речи
     * Чтобы распознать речь в аудиофайле, отправьте POST-запрос на &#x60;/v1/audio/transcriptions&#x60; (multipart/form-data).  Доступно для моделей распознавания речи &#x60;*-transcribe&#x60; (например, &#x60;openai/gpt-4o-mini-transcribe&#x60;). Для других моделей, в том числе неизвестных, возвращается ошибка 405. Ответ возвращается в JSON.
     * @param _file Аудиофайл (required)
     * @param model ID модели распознавания речи (&#x60;*-transcribe&#x60;) (required)
     * @param language Язык аудио в формате ISO-639-1 (optional)
     * @param prompt Подсказка для распознавания: термины, имена, стиль (optional)
     * @param responseFormat Формат ответа (optional)
     * @param temperature Температура выборки (optional)
     * @return ApiResponse&lt;AiGatewayTranscription&gt;
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> Распознанный текст </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Некорректный запрос, например повреждённый аудиофайл </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Ключ не передан или имеет неверный формат </td><td>  -  </td></tr>
        <tr><td> 402 </td><td> Недостаточно средств на балансе: оценочная стоимость запроса больше доступного баланса (с учётом ещё не списанной стоимости недавних и выполняющихся запросов) </td><td>  -  </td></tr>
        <tr><td> 403 </td><td> Ключ недействителен или аккаунт заблокирован </td><td>  -  </td></tr>
        <tr><td> 405 </td><td> Модель не поддерживает распознавание речи или не найдена </td><td>  -  </td></tr>
        <tr><td> 410 </td><td> Модель снята с обслуживания </td><td>  -  </td></tr>
        <tr><td> 422 </td><td> Исчерпан лимит расхода ключа (формат OpenAI) или не прошла проверка параметров multipart-запроса, например не передан файл (формат &#x60;{\&quot;detail\&quot;: ...}&#x60;) </td><td>  -  </td></tr>
        <tr><td> 429 </td><td> Превышен лимит запросов провайдера модели (текст ошибки — от провайдера). Повторите запрос позже </td><td>  -  </td></tr>
        <tr><td> 500 </td><td> Внутренняя ошибка или некорректный ответ провайдера модели. Повторите запрос позже </td><td>  -  </td></tr>
        <tr><td> 503 </td><td> Сервис или провайдер модели временно недоступен. Повторите запрос позже </td><td>  -  </td></tr>
     </table>
     */
    public ApiResponse<AiGatewayTranscription> createAiGatewayTranscriptionWithHttpInfo(File _file, String model, String language, String prompt, String responseFormat, BigDecimal temperature) throws ApiException {
        okhttp3.Call localVarCall = createAiGatewayTranscriptionValidateBeforeCall(_file, model, language, prompt, responseFormat, temperature, null);
        Type localVarReturnType = new TypeToken<AiGatewayTranscription>(){}.getType();
        return localVarApiClient.execute(localVarCall, localVarReturnType);
    }

    /**
     * Распознавание речи (asynchronously)
     * Чтобы распознать речь в аудиофайле, отправьте POST-запрос на &#x60;/v1/audio/transcriptions&#x60; (multipart/form-data).  Доступно для моделей распознавания речи &#x60;*-transcribe&#x60; (например, &#x60;openai/gpt-4o-mini-transcribe&#x60;). Для других моделей, в том числе неизвестных, возвращается ошибка 405. Ответ возвращается в JSON.
     * @param _file Аудиофайл (required)
     * @param model ID модели распознавания речи (&#x60;*-transcribe&#x60;) (required)
     * @param language Язык аудио в формате ISO-639-1 (optional)
     * @param prompt Подсказка для распознавания: термины, имена, стиль (optional)
     * @param responseFormat Формат ответа (optional)
     * @param temperature Температура выборки (optional)
     * @param _callback The callback to be executed when the API call finishes
     * @return The request call
     * @throws ApiException If fail to process the API call, e.g. serializing the request body object
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> Распознанный текст </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Некорректный запрос, например повреждённый аудиофайл </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Ключ не передан или имеет неверный формат </td><td>  -  </td></tr>
        <tr><td> 402 </td><td> Недостаточно средств на балансе: оценочная стоимость запроса больше доступного баланса (с учётом ещё не списанной стоимости недавних и выполняющихся запросов) </td><td>  -  </td></tr>
        <tr><td> 403 </td><td> Ключ недействителен или аккаунт заблокирован </td><td>  -  </td></tr>
        <tr><td> 405 </td><td> Модель не поддерживает распознавание речи или не найдена </td><td>  -  </td></tr>
        <tr><td> 410 </td><td> Модель снята с обслуживания </td><td>  -  </td></tr>
        <tr><td> 422 </td><td> Исчерпан лимит расхода ключа (формат OpenAI) или не прошла проверка параметров multipart-запроса, например не передан файл (формат &#x60;{\&quot;detail\&quot;: ...}&#x60;) </td><td>  -  </td></tr>
        <tr><td> 429 </td><td> Превышен лимит запросов провайдера модели (текст ошибки — от провайдера). Повторите запрос позже </td><td>  -  </td></tr>
        <tr><td> 500 </td><td> Внутренняя ошибка или некорректный ответ провайдера модели. Повторите запрос позже </td><td>  -  </td></tr>
        <tr><td> 503 </td><td> Сервис или провайдер модели временно недоступен. Повторите запрос позже </td><td>  -  </td></tr>
     </table>
     */
    public okhttp3.Call createAiGatewayTranscriptionAsync(File _file, String model, String language, String prompt, String responseFormat, BigDecimal temperature, final ApiCallback<AiGatewayTranscription> _callback) throws ApiException {

        okhttp3.Call localVarCall = createAiGatewayTranscriptionValidateBeforeCall(_file, model, language, prompt, responseFormat, temperature, _callback);
        Type localVarReturnType = new TypeToken<AiGatewayTranscription>(){}.getType();
        localVarApiClient.executeAsync(localVarCall, localVarReturnType, _callback);
        return localVarCall;
    }
    /**
     * Build call for deleteAiGatewayFile
     * @param fileId ID файла (required)
     * @param _callback Callback for upload/download progress
     * @return Call to execute
     * @throws ApiException If fail to serialize the request body object
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> Результат удаления </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Ключ не передан или имеет неверный формат </td><td>  -  </td></tr>
        <tr><td> 403 </td><td> Ключ недействителен или аккаунт заблокирован </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Файл не найден </td><td>  -  </td></tr>
        <tr><td> 409 </td><td> Файл используется батчем, который ещё выполняется </td><td>  -  </td></tr>
        <tr><td> 500 </td><td> Внутренняя ошибка или некорректный ответ провайдера модели. Повторите запрос позже </td><td>  -  </td></tr>
     </table>
     */
    public okhttp3.Call deleteAiGatewayFileCall(String fileId, final ApiCallback _callback) throws ApiException {
        String basePath = null;
        // Operation Servers
        String[] localBasePaths = new String[] { "https://api.timeweb.ai" };

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
        String localVarPath = "/v1/files/{file_id}"
            .replace("{" + "file_id" + "}", localVarApiClient.escapeString(fileId.toString()));

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

        String[] localVarAuthNames = new String[] { "AiGatewayKey" };
        return localVarApiClient.buildCall(basePath, localVarPath, "DELETE", localVarQueryParams, localVarCollectionQueryParams, localVarPostBody, localVarHeaderParams, localVarCookieParams, localVarFormParams, localVarAuthNames, _callback);
    }

    @SuppressWarnings("rawtypes")
    private okhttp3.Call deleteAiGatewayFileValidateBeforeCall(String fileId, final ApiCallback _callback) throws ApiException {
        // verify the required parameter 'fileId' is set
        if (fileId == null) {
            throw new ApiException("Missing the required parameter 'fileId' when calling deleteAiGatewayFile(Async)");
        }

        return deleteAiGatewayFileCall(fileId, _callback);

    }

    /**
     * Удаление файла
     * Чтобы удалить файл, отправьте DELETE-запрос на &#x60;/v1/files/{file_id}&#x60;.  Входной файл батча, который ещё выполняется или отменяется, и результаты батча, расход которого ещё не списан, удалить нельзя — вернётся ошибка 409.
     * @param fileId ID файла (required)
     * @return AiGatewayFileDeleted
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> Результат удаления </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Ключ не передан или имеет неверный формат </td><td>  -  </td></tr>
        <tr><td> 403 </td><td> Ключ недействителен или аккаунт заблокирован </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Файл не найден </td><td>  -  </td></tr>
        <tr><td> 409 </td><td> Файл используется батчем, который ещё выполняется </td><td>  -  </td></tr>
        <tr><td> 500 </td><td> Внутренняя ошибка или некорректный ответ провайдера модели. Повторите запрос позже </td><td>  -  </td></tr>
     </table>
     */
    public AiGatewayFileDeleted deleteAiGatewayFile(String fileId) throws ApiException {
        ApiResponse<AiGatewayFileDeleted> localVarResp = deleteAiGatewayFileWithHttpInfo(fileId);
        return localVarResp.getData();
    }

    /**
     * Удаление файла
     * Чтобы удалить файл, отправьте DELETE-запрос на &#x60;/v1/files/{file_id}&#x60;.  Входной файл батча, который ещё выполняется или отменяется, и результаты батча, расход которого ещё не списан, удалить нельзя — вернётся ошибка 409.
     * @param fileId ID файла (required)
     * @return ApiResponse&lt;AiGatewayFileDeleted&gt;
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> Результат удаления </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Ключ не передан или имеет неверный формат </td><td>  -  </td></tr>
        <tr><td> 403 </td><td> Ключ недействителен или аккаунт заблокирован </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Файл не найден </td><td>  -  </td></tr>
        <tr><td> 409 </td><td> Файл используется батчем, который ещё выполняется </td><td>  -  </td></tr>
        <tr><td> 500 </td><td> Внутренняя ошибка или некорректный ответ провайдера модели. Повторите запрос позже </td><td>  -  </td></tr>
     </table>
     */
    public ApiResponse<AiGatewayFileDeleted> deleteAiGatewayFileWithHttpInfo(String fileId) throws ApiException {
        okhttp3.Call localVarCall = deleteAiGatewayFileValidateBeforeCall(fileId, null);
        Type localVarReturnType = new TypeToken<AiGatewayFileDeleted>(){}.getType();
        return localVarApiClient.execute(localVarCall, localVarReturnType);
    }

    /**
     * Удаление файла (asynchronously)
     * Чтобы удалить файл, отправьте DELETE-запрос на &#x60;/v1/files/{file_id}&#x60;.  Входной файл батча, который ещё выполняется или отменяется, и результаты батча, расход которого ещё не списан, удалить нельзя — вернётся ошибка 409.
     * @param fileId ID файла (required)
     * @param _callback The callback to be executed when the API call finishes
     * @return The request call
     * @throws ApiException If fail to process the API call, e.g. serializing the request body object
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> Результат удаления </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Ключ не передан или имеет неверный формат </td><td>  -  </td></tr>
        <tr><td> 403 </td><td> Ключ недействителен или аккаунт заблокирован </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Файл не найден </td><td>  -  </td></tr>
        <tr><td> 409 </td><td> Файл используется батчем, который ещё выполняется </td><td>  -  </td></tr>
        <tr><td> 500 </td><td> Внутренняя ошибка или некорректный ответ провайдера модели. Повторите запрос позже </td><td>  -  </td></tr>
     </table>
     */
    public okhttp3.Call deleteAiGatewayFileAsync(String fileId, final ApiCallback<AiGatewayFileDeleted> _callback) throws ApiException {

        okhttp3.Call localVarCall = deleteAiGatewayFileValidateBeforeCall(fileId, _callback);
        Type localVarReturnType = new TypeToken<AiGatewayFileDeleted>(){}.getType();
        localVarApiClient.executeAsync(localVarCall, localVarReturnType, _callback);
        return localVarCall;
    }
    /**
     * Build call for deleteAiGatewayResponse
     * @param responseId ID ответа Responses API (required)
     * @param _callback Callback for upload/download progress
     * @return Call to execute
     * @throws ApiException If fail to serialize the request body object
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> Результат удаления </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Ответ с таким &#x60;response_id&#x60; не найден (в том числе уже удалён) или запрос некорректен </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Ключ не передан или имеет неверный формат </td><td>  -  </td></tr>
        <tr><td> 403 </td><td> Ключ недействителен или аккаунт заблокирован </td><td>  -  </td></tr>
        <tr><td> 500 </td><td> Внутренняя ошибка или некорректный ответ провайдера модели. Повторите запрос позже </td><td>  -  </td></tr>
     </table>
     */
    public okhttp3.Call deleteAiGatewayResponseCall(String responseId, final ApiCallback _callback) throws ApiException {
        String basePath = null;
        // Operation Servers
        String[] localBasePaths = new String[] { "https://api.timeweb.ai" };

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
        String localVarPath = "/v1/responses/{response_id}"
            .replace("{" + "response_id" + "}", localVarApiClient.escapeString(responseId.toString()));

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

        String[] localVarAuthNames = new String[] { "AiGatewayKey" };
        return localVarApiClient.buildCall(basePath, localVarPath, "DELETE", localVarQueryParams, localVarCollectionQueryParams, localVarPostBody, localVarHeaderParams, localVarCookieParams, localVarFormParams, localVarAuthNames, _callback);
    }

    @SuppressWarnings("rawtypes")
    private okhttp3.Call deleteAiGatewayResponseValidateBeforeCall(String responseId, final ApiCallback _callback) throws ApiException {
        // verify the required parameter 'responseId' is set
        if (responseId == null) {
            throw new ApiException("Missing the required parameter 'responseId' when calling deleteAiGatewayResponse(Async)");
        }

        return deleteAiGatewayResponseCall(responseId, _callback);

    }

    /**
     * Удаление ответа (Responses API)
     * Чтобы удалить сохранённый ответ, отправьте DELETE-запрос на &#x60;/v1/responses/{response_id}&#x60;.  Неизвестный или уже удалённый &#x60;response_id&#x60; возвращает ошибку 400.
     * @param responseId ID ответа Responses API (required)
     * @return AiGatewayResponseDeleted
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> Результат удаления </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Ответ с таким &#x60;response_id&#x60; не найден (в том числе уже удалён) или запрос некорректен </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Ключ не передан или имеет неверный формат </td><td>  -  </td></tr>
        <tr><td> 403 </td><td> Ключ недействителен или аккаунт заблокирован </td><td>  -  </td></tr>
        <tr><td> 500 </td><td> Внутренняя ошибка или некорректный ответ провайдера модели. Повторите запрос позже </td><td>  -  </td></tr>
     </table>
     */
    public AiGatewayResponseDeleted deleteAiGatewayResponse(String responseId) throws ApiException {
        ApiResponse<AiGatewayResponseDeleted> localVarResp = deleteAiGatewayResponseWithHttpInfo(responseId);
        return localVarResp.getData();
    }

    /**
     * Удаление ответа (Responses API)
     * Чтобы удалить сохранённый ответ, отправьте DELETE-запрос на &#x60;/v1/responses/{response_id}&#x60;.  Неизвестный или уже удалённый &#x60;response_id&#x60; возвращает ошибку 400.
     * @param responseId ID ответа Responses API (required)
     * @return ApiResponse&lt;AiGatewayResponseDeleted&gt;
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> Результат удаления </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Ответ с таким &#x60;response_id&#x60; не найден (в том числе уже удалён) или запрос некорректен </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Ключ не передан или имеет неверный формат </td><td>  -  </td></tr>
        <tr><td> 403 </td><td> Ключ недействителен или аккаунт заблокирован </td><td>  -  </td></tr>
        <tr><td> 500 </td><td> Внутренняя ошибка или некорректный ответ провайдера модели. Повторите запрос позже </td><td>  -  </td></tr>
     </table>
     */
    public ApiResponse<AiGatewayResponseDeleted> deleteAiGatewayResponseWithHttpInfo(String responseId) throws ApiException {
        okhttp3.Call localVarCall = deleteAiGatewayResponseValidateBeforeCall(responseId, null);
        Type localVarReturnType = new TypeToken<AiGatewayResponseDeleted>(){}.getType();
        return localVarApiClient.execute(localVarCall, localVarReturnType);
    }

    /**
     * Удаление ответа (Responses API) (asynchronously)
     * Чтобы удалить сохранённый ответ, отправьте DELETE-запрос на &#x60;/v1/responses/{response_id}&#x60;.  Неизвестный или уже удалённый &#x60;response_id&#x60; возвращает ошибку 400.
     * @param responseId ID ответа Responses API (required)
     * @param _callback The callback to be executed when the API call finishes
     * @return The request call
     * @throws ApiException If fail to process the API call, e.g. serializing the request body object
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> Результат удаления </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Ответ с таким &#x60;response_id&#x60; не найден (в том числе уже удалён) или запрос некорректен </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Ключ не передан или имеет неверный формат </td><td>  -  </td></tr>
        <tr><td> 403 </td><td> Ключ недействителен или аккаунт заблокирован </td><td>  -  </td></tr>
        <tr><td> 500 </td><td> Внутренняя ошибка или некорректный ответ провайдера модели. Повторите запрос позже </td><td>  -  </td></tr>
     </table>
     */
    public okhttp3.Call deleteAiGatewayResponseAsync(String responseId, final ApiCallback<AiGatewayResponseDeleted> _callback) throws ApiException {

        okhttp3.Call localVarCall = deleteAiGatewayResponseValidateBeforeCall(responseId, _callback);
        Type localVarReturnType = new TypeToken<AiGatewayResponseDeleted>(){}.getType();
        localVarApiClient.executeAsync(localVarCall, localVarReturnType, _callback);
        return localVarCall;
    }
    /**
     * Build call for downloadAiGatewayFileContent
     * @param fileId ID файла (required)
     * @param _callback Callback for upload/download progress
     * @return Call to execute
     * @throws ApiException If fail to serialize the request body object
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> Содержимое файла </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Файл этого назначения скачать нельзя </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Ключ не передан или имеет неверный формат </td><td>  -  </td></tr>
        <tr><td> 403 </td><td> Ключ недействителен или аккаунт заблокирован </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Файл не найден </td><td>  -  </td></tr>
        <tr><td> 500 </td><td> Внутренняя ошибка или некорректный ответ провайдера модели. Повторите запрос позже </td><td>  -  </td></tr>
     </table>
     */
    public okhttp3.Call downloadAiGatewayFileContentCall(String fileId, final ApiCallback _callback) throws ApiException {
        String basePath = null;
        // Operation Servers
        String[] localBasePaths = new String[] { "https://api.timeweb.ai" };

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
        String localVarPath = "/v1/files/{file_id}/content"
            .replace("{" + "file_id" + "}", localVarApiClient.escapeString(fileId.toString()));

        List<Pair> localVarQueryParams = new ArrayList<Pair>();
        List<Pair> localVarCollectionQueryParams = new ArrayList<Pair>();
        Map<String, String> localVarHeaderParams = new HashMap<String, String>();
        Map<String, String> localVarCookieParams = new HashMap<String, String>();
        Map<String, Object> localVarFormParams = new HashMap<String, Object>();

        final String[] localVarAccepts = {
            "application/octet-stream",
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

        String[] localVarAuthNames = new String[] { "AiGatewayKey" };
        return localVarApiClient.buildCall(basePath, localVarPath, "GET", localVarQueryParams, localVarCollectionQueryParams, localVarPostBody, localVarHeaderParams, localVarCookieParams, localVarFormParams, localVarAuthNames, _callback);
    }

    @SuppressWarnings("rawtypes")
    private okhttp3.Call downloadAiGatewayFileContentValidateBeforeCall(String fileId, final ApiCallback _callback) throws ApiException {
        // verify the required parameter 'fileId' is set
        if (fileId == null) {
            throw new ApiException("Missing the required parameter 'fileId' when calling downloadAiGatewayFileContent(Async)");
        }

        return downloadAiGatewayFileContentCall(fileId, _callback);

    }

    /**
     * Скачивание файла
     * Чтобы скачать содержимое файла — например, результаты выполненного батча (&#x60;output_file_id&#x60;), — отправьте GET-запрос на &#x60;/v1/files/{file_id}/content&#x60;.  Перед скачиванием результатов получите батч через &#x60;GET /v1/batches/{batch_id}&#x60; и убедитесь, что он в статусе &#x60;completed&#x60;. Файлы с &#x60;purpose&#x3D;user_data&#x60; скачать нельзя — вернётся ошибка 400.
     * @param fileId ID файла (required)
     * @return File
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> Содержимое файла </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Файл этого назначения скачать нельзя </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Ключ не передан или имеет неверный формат </td><td>  -  </td></tr>
        <tr><td> 403 </td><td> Ключ недействителен или аккаунт заблокирован </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Файл не найден </td><td>  -  </td></tr>
        <tr><td> 500 </td><td> Внутренняя ошибка или некорректный ответ провайдера модели. Повторите запрос позже </td><td>  -  </td></tr>
     </table>
     */
    public File downloadAiGatewayFileContent(String fileId) throws ApiException {
        ApiResponse<File> localVarResp = downloadAiGatewayFileContentWithHttpInfo(fileId);
        return localVarResp.getData();
    }

    /**
     * Скачивание файла
     * Чтобы скачать содержимое файла — например, результаты выполненного батча (&#x60;output_file_id&#x60;), — отправьте GET-запрос на &#x60;/v1/files/{file_id}/content&#x60;.  Перед скачиванием результатов получите батч через &#x60;GET /v1/batches/{batch_id}&#x60; и убедитесь, что он в статусе &#x60;completed&#x60;. Файлы с &#x60;purpose&#x3D;user_data&#x60; скачать нельзя — вернётся ошибка 400.
     * @param fileId ID файла (required)
     * @return ApiResponse&lt;File&gt;
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> Содержимое файла </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Файл этого назначения скачать нельзя </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Ключ не передан или имеет неверный формат </td><td>  -  </td></tr>
        <tr><td> 403 </td><td> Ключ недействителен или аккаунт заблокирован </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Файл не найден </td><td>  -  </td></tr>
        <tr><td> 500 </td><td> Внутренняя ошибка или некорректный ответ провайдера модели. Повторите запрос позже </td><td>  -  </td></tr>
     </table>
     */
    public ApiResponse<File> downloadAiGatewayFileContentWithHttpInfo(String fileId) throws ApiException {
        okhttp3.Call localVarCall = downloadAiGatewayFileContentValidateBeforeCall(fileId, null);
        Type localVarReturnType = new TypeToken<File>(){}.getType();
        return localVarApiClient.execute(localVarCall, localVarReturnType);
    }

    /**
     * Скачивание файла (asynchronously)
     * Чтобы скачать содержимое файла — например, результаты выполненного батча (&#x60;output_file_id&#x60;), — отправьте GET-запрос на &#x60;/v1/files/{file_id}/content&#x60;.  Перед скачиванием результатов получите батч через &#x60;GET /v1/batches/{batch_id}&#x60; и убедитесь, что он в статусе &#x60;completed&#x60;. Файлы с &#x60;purpose&#x3D;user_data&#x60; скачать нельзя — вернётся ошибка 400.
     * @param fileId ID файла (required)
     * @param _callback The callback to be executed when the API call finishes
     * @return The request call
     * @throws ApiException If fail to process the API call, e.g. serializing the request body object
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> Содержимое файла </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Файл этого назначения скачать нельзя </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Ключ не передан или имеет неверный формат </td><td>  -  </td></tr>
        <tr><td> 403 </td><td> Ключ недействителен или аккаунт заблокирован </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Файл не найден </td><td>  -  </td></tr>
        <tr><td> 500 </td><td> Внутренняя ошибка или некорректный ответ провайдера модели. Повторите запрос позже </td><td>  -  </td></tr>
     </table>
     */
    public okhttp3.Call downloadAiGatewayFileContentAsync(String fileId, final ApiCallback<File> _callback) throws ApiException {

        okhttp3.Call localVarCall = downloadAiGatewayFileContentValidateBeforeCall(fileId, _callback);
        Type localVarReturnType = new TypeToken<File>(){}.getType();
        localVarApiClient.executeAsync(localVarCall, localVarReturnType, _callback);
        return localVarCall;
    }
    /**
     * Build call for getAiGatewayBatch
     * @param batchId ID батча (required)
     * @param _callback Callback for upload/download progress
     * @return Call to execute
     * @throws ApiException If fail to serialize the request body object
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> Батч </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Ключ не передан или имеет неверный формат </td><td>  -  </td></tr>
        <tr><td> 403 </td><td> Ключ недействителен или аккаунт заблокирован </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Батч не найден </td><td>  -  </td></tr>
        <tr><td> 500 </td><td> Внутренняя ошибка или некорректный ответ провайдера модели. Повторите запрос позже </td><td>  -  </td></tr>
        <tr><td> 503 </td><td> Сервис или провайдер модели временно недоступен. Повторите запрос позже </td><td>  -  </td></tr>
     </table>
     */
    public okhttp3.Call getAiGatewayBatchCall(String batchId, final ApiCallback _callback) throws ApiException {
        String basePath = null;
        // Operation Servers
        String[] localBasePaths = new String[] { "https://api.timeweb.ai" };

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
        String localVarPath = "/v1/batches/{batch_id}"
            .replace("{" + "batch_id" + "}", localVarApiClient.escapeString(batchId.toString()));

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

        String[] localVarAuthNames = new String[] { "AiGatewayKey" };
        return localVarApiClient.buildCall(basePath, localVarPath, "GET", localVarQueryParams, localVarCollectionQueryParams, localVarPostBody, localVarHeaderParams, localVarCookieParams, localVarFormParams, localVarAuthNames, _callback);
    }

    @SuppressWarnings("rawtypes")
    private okhttp3.Call getAiGatewayBatchValidateBeforeCall(String batchId, final ApiCallback _callback) throws ApiException {
        // verify the required parameter 'batchId' is set
        if (batchId == null) {
            throw new ApiException("Missing the required parameter 'batchId' when calling getAiGatewayBatch(Async)");
        }

        return getAiGatewayBatchCall(batchId, _callback);

    }

    /**
     * Получение батча
     * Чтобы получить статус батча, отправьте GET-запрос на &#x60;/v1/batches/{batch_id}&#x60;.  Когда статус станет &#x60;completed&#x60;, скачайте результаты по &#x60;output_file_id&#x60; через &#x60;GET /v1/files/{file_id}/content&#x60;.
     * @param batchId ID батча (required)
     * @return AiGatewayBatch
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> Батч </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Ключ не передан или имеет неверный формат </td><td>  -  </td></tr>
        <tr><td> 403 </td><td> Ключ недействителен или аккаунт заблокирован </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Батч не найден </td><td>  -  </td></tr>
        <tr><td> 500 </td><td> Внутренняя ошибка или некорректный ответ провайдера модели. Повторите запрос позже </td><td>  -  </td></tr>
        <tr><td> 503 </td><td> Сервис или провайдер модели временно недоступен. Повторите запрос позже </td><td>  -  </td></tr>
     </table>
     */
    public AiGatewayBatch getAiGatewayBatch(String batchId) throws ApiException {
        ApiResponse<AiGatewayBatch> localVarResp = getAiGatewayBatchWithHttpInfo(batchId);
        return localVarResp.getData();
    }

    /**
     * Получение батча
     * Чтобы получить статус батча, отправьте GET-запрос на &#x60;/v1/batches/{batch_id}&#x60;.  Когда статус станет &#x60;completed&#x60;, скачайте результаты по &#x60;output_file_id&#x60; через &#x60;GET /v1/files/{file_id}/content&#x60;.
     * @param batchId ID батча (required)
     * @return ApiResponse&lt;AiGatewayBatch&gt;
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> Батч </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Ключ не передан или имеет неверный формат </td><td>  -  </td></tr>
        <tr><td> 403 </td><td> Ключ недействителен или аккаунт заблокирован </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Батч не найден </td><td>  -  </td></tr>
        <tr><td> 500 </td><td> Внутренняя ошибка или некорректный ответ провайдера модели. Повторите запрос позже </td><td>  -  </td></tr>
        <tr><td> 503 </td><td> Сервис или провайдер модели временно недоступен. Повторите запрос позже </td><td>  -  </td></tr>
     </table>
     */
    public ApiResponse<AiGatewayBatch> getAiGatewayBatchWithHttpInfo(String batchId) throws ApiException {
        okhttp3.Call localVarCall = getAiGatewayBatchValidateBeforeCall(batchId, null);
        Type localVarReturnType = new TypeToken<AiGatewayBatch>(){}.getType();
        return localVarApiClient.execute(localVarCall, localVarReturnType);
    }

    /**
     * Получение батча (asynchronously)
     * Чтобы получить статус батча, отправьте GET-запрос на &#x60;/v1/batches/{batch_id}&#x60;.  Когда статус станет &#x60;completed&#x60;, скачайте результаты по &#x60;output_file_id&#x60; через &#x60;GET /v1/files/{file_id}/content&#x60;.
     * @param batchId ID батча (required)
     * @param _callback The callback to be executed when the API call finishes
     * @return The request call
     * @throws ApiException If fail to process the API call, e.g. serializing the request body object
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> Батч </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Ключ не передан или имеет неверный формат </td><td>  -  </td></tr>
        <tr><td> 403 </td><td> Ключ недействителен или аккаунт заблокирован </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Батч не найден </td><td>  -  </td></tr>
        <tr><td> 500 </td><td> Внутренняя ошибка или некорректный ответ провайдера модели. Повторите запрос позже </td><td>  -  </td></tr>
        <tr><td> 503 </td><td> Сервис или провайдер модели временно недоступен. Повторите запрос позже </td><td>  -  </td></tr>
     </table>
     */
    public okhttp3.Call getAiGatewayBatchAsync(String batchId, final ApiCallback<AiGatewayBatch> _callback) throws ApiException {

        okhttp3.Call localVarCall = getAiGatewayBatchValidateBeforeCall(batchId, _callback);
        Type localVarReturnType = new TypeToken<AiGatewayBatch>(){}.getType();
        localVarApiClient.executeAsync(localVarCall, localVarReturnType, _callback);
        return localVarCall;
    }
    /**
     * Build call for getAiGatewayBatches
     * @param limit Количество батчей на странице (optional)
     * @param after Вернуть батчи после батча с этим ID (значение &#x60;last_id&#x60; предыдущей страницы) (optional)
     * @param _callback Callback for upload/download progress
     * @return Call to execute
     * @throws ApiException If fail to serialize the request body object
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> Список батчей </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Ключ не передан или имеет неверный формат </td><td>  -  </td></tr>
        <tr><td> 403 </td><td> Ключ недействителен или аккаунт заблокирован </td><td>  -  </td></tr>
        <tr><td> 422 </td><td> Неверные параметры запроса, например нечисловой &#x60;limit&#x60; </td><td>  -  </td></tr>
        <tr><td> 500 </td><td> Внутренняя ошибка или некорректный ответ провайдера модели. Повторите запрос позже </td><td>  -  </td></tr>
        <tr><td> 503 </td><td> Сервис или провайдер модели временно недоступен. Повторите запрос позже </td><td>  -  </td></tr>
     </table>
     */
    public okhttp3.Call getAiGatewayBatchesCall(Integer limit, String after, final ApiCallback _callback) throws ApiException {
        String basePath = null;
        // Operation Servers
        String[] localBasePaths = new String[] { "https://api.timeweb.ai" };

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
        String localVarPath = "/v1/batches";

        List<Pair> localVarQueryParams = new ArrayList<Pair>();
        List<Pair> localVarCollectionQueryParams = new ArrayList<Pair>();
        Map<String, String> localVarHeaderParams = new HashMap<String, String>();
        Map<String, String> localVarCookieParams = new HashMap<String, String>();
        Map<String, Object> localVarFormParams = new HashMap<String, Object>();

        if (limit != null) {
            localVarQueryParams.addAll(localVarApiClient.parameterToPair("limit", limit));
        }

        if (after != null) {
            localVarQueryParams.addAll(localVarApiClient.parameterToPair("after", after));
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

        String[] localVarAuthNames = new String[] { "AiGatewayKey" };
        return localVarApiClient.buildCall(basePath, localVarPath, "GET", localVarQueryParams, localVarCollectionQueryParams, localVarPostBody, localVarHeaderParams, localVarCookieParams, localVarFormParams, localVarAuthNames, _callback);
    }

    @SuppressWarnings("rawtypes")
    private okhttp3.Call getAiGatewayBatchesValidateBeforeCall(Integer limit, String after, final ApiCallback _callback) throws ApiException {
        return getAiGatewayBatchesCall(limit, after, _callback);

    }

    /**
     * Получение списка батчей
     * Чтобы получить список батчей, отправьте GET-запрос на &#x60;/v1/batches&#x60;.  Для следующей страницы передайте в &#x60;after&#x60; значение &#x60;last_id&#x60;, пока &#x60;has_more&#x60; равно &#x60;true&#x60;.
     * @param limit Количество батчей на странице (optional)
     * @param after Вернуть батчи после батча с этим ID (значение &#x60;last_id&#x60; предыдущей страницы) (optional)
     * @return AiGatewayBatchList
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> Список батчей </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Ключ не передан или имеет неверный формат </td><td>  -  </td></tr>
        <tr><td> 403 </td><td> Ключ недействителен или аккаунт заблокирован </td><td>  -  </td></tr>
        <tr><td> 422 </td><td> Неверные параметры запроса, например нечисловой &#x60;limit&#x60; </td><td>  -  </td></tr>
        <tr><td> 500 </td><td> Внутренняя ошибка или некорректный ответ провайдера модели. Повторите запрос позже </td><td>  -  </td></tr>
        <tr><td> 503 </td><td> Сервис или провайдер модели временно недоступен. Повторите запрос позже </td><td>  -  </td></tr>
     </table>
     */
    public AiGatewayBatchList getAiGatewayBatches(Integer limit, String after) throws ApiException {
        ApiResponse<AiGatewayBatchList> localVarResp = getAiGatewayBatchesWithHttpInfo(limit, after);
        return localVarResp.getData();
    }

    /**
     * Получение списка батчей
     * Чтобы получить список батчей, отправьте GET-запрос на &#x60;/v1/batches&#x60;.  Для следующей страницы передайте в &#x60;after&#x60; значение &#x60;last_id&#x60;, пока &#x60;has_more&#x60; равно &#x60;true&#x60;.
     * @param limit Количество батчей на странице (optional)
     * @param after Вернуть батчи после батча с этим ID (значение &#x60;last_id&#x60; предыдущей страницы) (optional)
     * @return ApiResponse&lt;AiGatewayBatchList&gt;
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> Список батчей </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Ключ не передан или имеет неверный формат </td><td>  -  </td></tr>
        <tr><td> 403 </td><td> Ключ недействителен или аккаунт заблокирован </td><td>  -  </td></tr>
        <tr><td> 422 </td><td> Неверные параметры запроса, например нечисловой &#x60;limit&#x60; </td><td>  -  </td></tr>
        <tr><td> 500 </td><td> Внутренняя ошибка или некорректный ответ провайдера модели. Повторите запрос позже </td><td>  -  </td></tr>
        <tr><td> 503 </td><td> Сервис или провайдер модели временно недоступен. Повторите запрос позже </td><td>  -  </td></tr>
     </table>
     */
    public ApiResponse<AiGatewayBatchList> getAiGatewayBatchesWithHttpInfo(Integer limit, String after) throws ApiException {
        okhttp3.Call localVarCall = getAiGatewayBatchesValidateBeforeCall(limit, after, null);
        Type localVarReturnType = new TypeToken<AiGatewayBatchList>(){}.getType();
        return localVarApiClient.execute(localVarCall, localVarReturnType);
    }

    /**
     * Получение списка батчей (asynchronously)
     * Чтобы получить список батчей, отправьте GET-запрос на &#x60;/v1/batches&#x60;.  Для следующей страницы передайте в &#x60;after&#x60; значение &#x60;last_id&#x60;, пока &#x60;has_more&#x60; равно &#x60;true&#x60;.
     * @param limit Количество батчей на странице (optional)
     * @param after Вернуть батчи после батча с этим ID (значение &#x60;last_id&#x60; предыдущей страницы) (optional)
     * @param _callback The callback to be executed when the API call finishes
     * @return The request call
     * @throws ApiException If fail to process the API call, e.g. serializing the request body object
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> Список батчей </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Ключ не передан или имеет неверный формат </td><td>  -  </td></tr>
        <tr><td> 403 </td><td> Ключ недействителен или аккаунт заблокирован </td><td>  -  </td></tr>
        <tr><td> 422 </td><td> Неверные параметры запроса, например нечисловой &#x60;limit&#x60; </td><td>  -  </td></tr>
        <tr><td> 500 </td><td> Внутренняя ошибка или некорректный ответ провайдера модели. Повторите запрос позже </td><td>  -  </td></tr>
        <tr><td> 503 </td><td> Сервис или провайдер модели временно недоступен. Повторите запрос позже </td><td>  -  </td></tr>
     </table>
     */
    public okhttp3.Call getAiGatewayBatchesAsync(Integer limit, String after, final ApiCallback<AiGatewayBatchList> _callback) throws ApiException {

        okhttp3.Call localVarCall = getAiGatewayBatchesValidateBeforeCall(limit, after, _callback);
        Type localVarReturnType = new TypeToken<AiGatewayBatchList>(){}.getType();
        localVarApiClient.executeAsync(localVarCall, localVarReturnType, _callback);
        return localVarCall;
    }
    /**
     * Build call for getAiGatewayFile
     * @param fileId ID файла (required)
     * @param _callback Callback for upload/download progress
     * @return Call to execute
     * @throws ApiException If fail to serialize the request body object
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> Файл </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Ключ не передан или имеет неверный формат </td><td>  -  </td></tr>
        <tr><td> 403 </td><td> Ключ недействителен или аккаунт заблокирован </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Файл не найден </td><td>  -  </td></tr>
        <tr><td> 500 </td><td> Внутренняя ошибка или некорректный ответ провайдера модели. Повторите запрос позже </td><td>  -  </td></tr>
     </table>
     */
    public okhttp3.Call getAiGatewayFileCall(String fileId, final ApiCallback _callback) throws ApiException {
        String basePath = null;
        // Operation Servers
        String[] localBasePaths = new String[] { "https://api.timeweb.ai" };

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
        String localVarPath = "/v1/files/{file_id}"
            .replace("{" + "file_id" + "}", localVarApiClient.escapeString(fileId.toString()));

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

        String[] localVarAuthNames = new String[] { "AiGatewayKey" };
        return localVarApiClient.buildCall(basePath, localVarPath, "GET", localVarQueryParams, localVarCollectionQueryParams, localVarPostBody, localVarHeaderParams, localVarCookieParams, localVarFormParams, localVarAuthNames, _callback);
    }

    @SuppressWarnings("rawtypes")
    private okhttp3.Call getAiGatewayFileValidateBeforeCall(String fileId, final ApiCallback _callback) throws ApiException {
        // verify the required parameter 'fileId' is set
        if (fileId == null) {
            throw new ApiException("Missing the required parameter 'fileId' when calling getAiGatewayFile(Async)");
        }

        return getAiGatewayFileCall(fileId, _callback);

    }

    /**
     * Получение информации о файле
     * Чтобы получить информацию о загруженном файле, отправьте GET-запрос на &#x60;/v1/files/{file_id}&#x60;.
     * @param fileId ID файла (required)
     * @return AiGatewayFile
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> Файл </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Ключ не передан или имеет неверный формат </td><td>  -  </td></tr>
        <tr><td> 403 </td><td> Ключ недействителен или аккаунт заблокирован </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Файл не найден </td><td>  -  </td></tr>
        <tr><td> 500 </td><td> Внутренняя ошибка или некорректный ответ провайдера модели. Повторите запрос позже </td><td>  -  </td></tr>
     </table>
     */
    public AiGatewayFile getAiGatewayFile(String fileId) throws ApiException {
        ApiResponse<AiGatewayFile> localVarResp = getAiGatewayFileWithHttpInfo(fileId);
        return localVarResp.getData();
    }

    /**
     * Получение информации о файле
     * Чтобы получить информацию о загруженном файле, отправьте GET-запрос на &#x60;/v1/files/{file_id}&#x60;.
     * @param fileId ID файла (required)
     * @return ApiResponse&lt;AiGatewayFile&gt;
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> Файл </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Ключ не передан или имеет неверный формат </td><td>  -  </td></tr>
        <tr><td> 403 </td><td> Ключ недействителен или аккаунт заблокирован </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Файл не найден </td><td>  -  </td></tr>
        <tr><td> 500 </td><td> Внутренняя ошибка или некорректный ответ провайдера модели. Повторите запрос позже </td><td>  -  </td></tr>
     </table>
     */
    public ApiResponse<AiGatewayFile> getAiGatewayFileWithHttpInfo(String fileId) throws ApiException {
        okhttp3.Call localVarCall = getAiGatewayFileValidateBeforeCall(fileId, null);
        Type localVarReturnType = new TypeToken<AiGatewayFile>(){}.getType();
        return localVarApiClient.execute(localVarCall, localVarReturnType);
    }

    /**
     * Получение информации о файле (asynchronously)
     * Чтобы получить информацию о загруженном файле, отправьте GET-запрос на &#x60;/v1/files/{file_id}&#x60;.
     * @param fileId ID файла (required)
     * @param _callback The callback to be executed when the API call finishes
     * @return The request call
     * @throws ApiException If fail to process the API call, e.g. serializing the request body object
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> Файл </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Ключ не передан или имеет неверный формат </td><td>  -  </td></tr>
        <tr><td> 403 </td><td> Ключ недействителен или аккаунт заблокирован </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Файл не найден </td><td>  -  </td></tr>
        <tr><td> 500 </td><td> Внутренняя ошибка или некорректный ответ провайдера модели. Повторите запрос позже </td><td>  -  </td></tr>
     </table>
     */
    public okhttp3.Call getAiGatewayFileAsync(String fileId, final ApiCallback<AiGatewayFile> _callback) throws ApiException {

        okhttp3.Call localVarCall = getAiGatewayFileValidateBeforeCall(fileId, _callback);
        Type localVarReturnType = new TypeToken<AiGatewayFile>(){}.getType();
        localVarApiClient.executeAsync(localVarCall, localVarReturnType, _callback);
        return localVarCall;
    }
    /**
     * Build call for getAiGatewayModels
     * @param _callback Callback for upload/download progress
     * @return Call to execute
     * @throws ApiException If fail to serialize the request body object
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> Список моделей </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Ключ не передан или имеет неверный формат </td><td>  -  </td></tr>
        <tr><td> 403 </td><td> Ключ недействителен или аккаунт заблокирован </td><td>  -  </td></tr>
        <tr><td> 500 </td><td> Внутренняя ошибка или некорректный ответ провайдера модели. Повторите запрос позже </td><td>  -  </td></tr>
     </table>
     */
    public okhttp3.Call getAiGatewayModelsCall(final ApiCallback _callback) throws ApiException {
        String basePath = null;
        // Operation Servers
        String[] localBasePaths = new String[] { "https://api.timeweb.ai" };

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
        String localVarPath = "/v1/models";

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

        String[] localVarAuthNames = new String[] { "AiGatewayKey" };
        return localVarApiClient.buildCall(basePath, localVarPath, "GET", localVarQueryParams, localVarCollectionQueryParams, localVarPostBody, localVarHeaderParams, localVarCookieParams, localVarFormParams, localVarAuthNames, _callback);
    }

    @SuppressWarnings("rawtypes")
    private okhttp3.Call getAiGatewayModelsValidateBeforeCall(final ApiCallback _callback) throws ApiException {
        return getAiGatewayModelsCall(_callback);

    }

    /**
     * Получение списка моделей AI Gateway
     * Чтобы получить список моделей, доступных ключу, отправьте GET-запрос на &#x60;/v1/models&#x60;.  Поле &#x60;mode&#x60; показывает назначение модели. В списке могут оставаться модели, снятые с обслуживания: запросы к ним возвращают ошибку 410. Актуальный статус, цены и возможности моделей — в каталоге &#x60;GET https://api.timeweb.cloud/api/v3/cloud-ai/models&#x60; (снятые модели — &#x60;is_stopped: true&#x60;).
     * @return AiGatewayModelList
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> Список моделей </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Ключ не передан или имеет неверный формат </td><td>  -  </td></tr>
        <tr><td> 403 </td><td> Ключ недействителен или аккаунт заблокирован </td><td>  -  </td></tr>
        <tr><td> 500 </td><td> Внутренняя ошибка или некорректный ответ провайдера модели. Повторите запрос позже </td><td>  -  </td></tr>
     </table>
     */
    public AiGatewayModelList getAiGatewayModels() throws ApiException {
        ApiResponse<AiGatewayModelList> localVarResp = getAiGatewayModelsWithHttpInfo();
        return localVarResp.getData();
    }

    /**
     * Получение списка моделей AI Gateway
     * Чтобы получить список моделей, доступных ключу, отправьте GET-запрос на &#x60;/v1/models&#x60;.  Поле &#x60;mode&#x60; показывает назначение модели. В списке могут оставаться модели, снятые с обслуживания: запросы к ним возвращают ошибку 410. Актуальный статус, цены и возможности моделей — в каталоге &#x60;GET https://api.timeweb.cloud/api/v3/cloud-ai/models&#x60; (снятые модели — &#x60;is_stopped: true&#x60;).
     * @return ApiResponse&lt;AiGatewayModelList&gt;
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> Список моделей </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Ключ не передан или имеет неверный формат </td><td>  -  </td></tr>
        <tr><td> 403 </td><td> Ключ недействителен или аккаунт заблокирован </td><td>  -  </td></tr>
        <tr><td> 500 </td><td> Внутренняя ошибка или некорректный ответ провайдера модели. Повторите запрос позже </td><td>  -  </td></tr>
     </table>
     */
    public ApiResponse<AiGatewayModelList> getAiGatewayModelsWithHttpInfo() throws ApiException {
        okhttp3.Call localVarCall = getAiGatewayModelsValidateBeforeCall(null);
        Type localVarReturnType = new TypeToken<AiGatewayModelList>(){}.getType();
        return localVarApiClient.execute(localVarCall, localVarReturnType);
    }

    /**
     * Получение списка моделей AI Gateway (asynchronously)
     * Чтобы получить список моделей, доступных ключу, отправьте GET-запрос на &#x60;/v1/models&#x60;.  Поле &#x60;mode&#x60; показывает назначение модели. В списке могут оставаться модели, снятые с обслуживания: запросы к ним возвращают ошибку 410. Актуальный статус, цены и возможности моделей — в каталоге &#x60;GET https://api.timeweb.cloud/api/v3/cloud-ai/models&#x60; (снятые модели — &#x60;is_stopped: true&#x60;).
     * @param _callback The callback to be executed when the API call finishes
     * @return The request call
     * @throws ApiException If fail to process the API call, e.g. serializing the request body object
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> Список моделей </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Ключ не передан или имеет неверный формат </td><td>  -  </td></tr>
        <tr><td> 403 </td><td> Ключ недействителен или аккаунт заблокирован </td><td>  -  </td></tr>
        <tr><td> 500 </td><td> Внутренняя ошибка или некорректный ответ провайдера модели. Повторите запрос позже </td><td>  -  </td></tr>
     </table>
     */
    public okhttp3.Call getAiGatewayModelsAsync(final ApiCallback<AiGatewayModelList> _callback) throws ApiException {

        okhttp3.Call localVarCall = getAiGatewayModelsValidateBeforeCall(_callback);
        Type localVarReturnType = new TypeToken<AiGatewayModelList>(){}.getType();
        localVarApiClient.executeAsync(localVarCall, localVarReturnType, _callback);
        return localVarCall;
    }
    /**
     * Build call for getAiGatewayResponse
     * @param responseId ID ответа Responses API (required)
     * @param _callback Callback for upload/download progress
     * @return Call to execute
     * @throws ApiException If fail to serialize the request body object
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> Ответ </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Ответ с таким &#x60;response_id&#x60; не найден (в том числе уже удалён) или запрос некорректен </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Ключ не передан или имеет неверный формат </td><td>  -  </td></tr>
        <tr><td> 403 </td><td> Ключ недействителен или аккаунт заблокирован </td><td>  -  </td></tr>
        <tr><td> 500 </td><td> Внутренняя ошибка или некорректный ответ провайдера модели. Повторите запрос позже </td><td>  -  </td></tr>
     </table>
     */
    public okhttp3.Call getAiGatewayResponseCall(String responseId, final ApiCallback _callback) throws ApiException {
        String basePath = null;
        // Operation Servers
        String[] localBasePaths = new String[] { "https://api.timeweb.ai" };

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
        String localVarPath = "/v1/responses/{response_id}"
            .replace("{" + "response_id" + "}", localVarApiClient.escapeString(responseId.toString()));

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

        String[] localVarAuthNames = new String[] { "AiGatewayKey" };
        return localVarApiClient.buildCall(basePath, localVarPath, "GET", localVarQueryParams, localVarCollectionQueryParams, localVarPostBody, localVarHeaderParams, localVarCookieParams, localVarFormParams, localVarAuthNames, _callback);
    }

    @SuppressWarnings("rawtypes")
    private okhttp3.Call getAiGatewayResponseValidateBeforeCall(String responseId, final ApiCallback _callback) throws ApiException {
        // verify the required parameter 'responseId' is set
        if (responseId == null) {
            throw new ApiException("Missing the required parameter 'responseId' when calling getAiGatewayResponse(Async)");
        }

        return getAiGatewayResponseCall(responseId, _callback);

    }

    /**
     * Получение ответа (Responses API)
     * Чтобы получить сохранённый ответ — например, дождаться завершения фонового ответа, — отправьте GET-запрос на &#x60;/v1/responses/{response_id}&#x60;.  Неизвестный или удалённый &#x60;response_id&#x60; возвращает ошибку 400.
     * @param responseId ID ответа Responses API (required)
     * @return AiGatewayResponse
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> Ответ </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Ответ с таким &#x60;response_id&#x60; не найден (в том числе уже удалён) или запрос некорректен </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Ключ не передан или имеет неверный формат </td><td>  -  </td></tr>
        <tr><td> 403 </td><td> Ключ недействителен или аккаунт заблокирован </td><td>  -  </td></tr>
        <tr><td> 500 </td><td> Внутренняя ошибка или некорректный ответ провайдера модели. Повторите запрос позже </td><td>  -  </td></tr>
     </table>
     */
    public AiGatewayResponse getAiGatewayResponse(String responseId) throws ApiException {
        ApiResponse<AiGatewayResponse> localVarResp = getAiGatewayResponseWithHttpInfo(responseId);
        return localVarResp.getData();
    }

    /**
     * Получение ответа (Responses API)
     * Чтобы получить сохранённый ответ — например, дождаться завершения фонового ответа, — отправьте GET-запрос на &#x60;/v1/responses/{response_id}&#x60;.  Неизвестный или удалённый &#x60;response_id&#x60; возвращает ошибку 400.
     * @param responseId ID ответа Responses API (required)
     * @return ApiResponse&lt;AiGatewayResponse&gt;
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> Ответ </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Ответ с таким &#x60;response_id&#x60; не найден (в том числе уже удалён) или запрос некорректен </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Ключ не передан или имеет неверный формат </td><td>  -  </td></tr>
        <tr><td> 403 </td><td> Ключ недействителен или аккаунт заблокирован </td><td>  -  </td></tr>
        <tr><td> 500 </td><td> Внутренняя ошибка или некорректный ответ провайдера модели. Повторите запрос позже </td><td>  -  </td></tr>
     </table>
     */
    public ApiResponse<AiGatewayResponse> getAiGatewayResponseWithHttpInfo(String responseId) throws ApiException {
        okhttp3.Call localVarCall = getAiGatewayResponseValidateBeforeCall(responseId, null);
        Type localVarReturnType = new TypeToken<AiGatewayResponse>(){}.getType();
        return localVarApiClient.execute(localVarCall, localVarReturnType);
    }

    /**
     * Получение ответа (Responses API) (asynchronously)
     * Чтобы получить сохранённый ответ — например, дождаться завершения фонового ответа, — отправьте GET-запрос на &#x60;/v1/responses/{response_id}&#x60;.  Неизвестный или удалённый &#x60;response_id&#x60; возвращает ошибку 400.
     * @param responseId ID ответа Responses API (required)
     * @param _callback The callback to be executed when the API call finishes
     * @return The request call
     * @throws ApiException If fail to process the API call, e.g. serializing the request body object
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> Ответ </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Ответ с таким &#x60;response_id&#x60; не найден (в том числе уже удалён) или запрос некорректен </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Ключ не передан или имеет неверный формат </td><td>  -  </td></tr>
        <tr><td> 403 </td><td> Ключ недействителен или аккаунт заблокирован </td><td>  -  </td></tr>
        <tr><td> 500 </td><td> Внутренняя ошибка или некорректный ответ провайдера модели. Повторите запрос позже </td><td>  -  </td></tr>
     </table>
     */
    public okhttp3.Call getAiGatewayResponseAsync(String responseId, final ApiCallback<AiGatewayResponse> _callback) throws ApiException {

        okhttp3.Call localVarCall = getAiGatewayResponseValidateBeforeCall(responseId, _callback);
        Type localVarReturnType = new TypeToken<AiGatewayResponse>(){}.getType();
        localVarApiClient.executeAsync(localVarCall, localVarReturnType, _callback);
        return localVarCall;
    }
    /**
     * Build call for uploadAiGatewayFile
     * @param _file Файл. Для Batch API — JSONL, по одному запросу в строке (required)
     * @param purpose Назначение: &#x60;batch&#x60; — входной файл Batch API, &#x60;user_data&#x60; и другие назначения OpenAI — файлы для передачи модели (required)
     * @param model Модель, для которой загружается файл. Обязателен, если не передан &#x60;target_model_names&#x60; (optional)
     * @param targetModelNames Список моделей через запятую — вместо &#x60;model&#x60; (optional)
     * @param _callback Callback for upload/download progress
     * @return Call to execute
     * @throws ApiException If fail to serialize the request body object
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> Загруженный файл </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Некорректный запрос, например модель в строках файла не совпадает с &#x60;model&#x60; </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Ключ не передан или имеет неверный формат </td><td>  -  </td></tr>
        <tr><td> 403 </td><td> Ключ недействителен или аккаунт заблокирован </td><td>  -  </td></tr>
        <tr><td> 422 </td><td> Не прошла проверка параметров, например не передан файл </td><td>  -  </td></tr>
        <tr><td> 500 </td><td> Внутренняя ошибка или некорректный ответ провайдера модели. Повторите запрос позже </td><td>  -  </td></tr>
     </table>
     */
    public okhttp3.Call uploadAiGatewayFileCall(File _file, String purpose, String model, String targetModelNames, final ApiCallback _callback) throws ApiException {
        String basePath = null;
        // Operation Servers
        String[] localBasePaths = new String[] { "https://api.timeweb.ai" };

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
        String localVarPath = "/v1/files";

        List<Pair> localVarQueryParams = new ArrayList<Pair>();
        List<Pair> localVarCollectionQueryParams = new ArrayList<Pair>();
        Map<String, String> localVarHeaderParams = new HashMap<String, String>();
        Map<String, String> localVarCookieParams = new HashMap<String, String>();
        Map<String, Object> localVarFormParams = new HashMap<String, Object>();

        if (_file != null) {
            localVarFormParams.put("file", _file);
        }

        if (purpose != null) {
            localVarFormParams.put("purpose", purpose);
        }

        if (model != null) {
            localVarFormParams.put("model", model);
        }

        if (targetModelNames != null) {
            localVarFormParams.put("target_model_names", targetModelNames);
        }

        final String[] localVarAccepts = {
            "application/json"
        };
        final String localVarAccept = localVarApiClient.selectHeaderAccept(localVarAccepts);
        if (localVarAccept != null) {
            localVarHeaderParams.put("Accept", localVarAccept);
        }

        final String[] localVarContentTypes = {
            "multipart/form-data"
        };
        final String localVarContentType = localVarApiClient.selectHeaderContentType(localVarContentTypes);
        if (localVarContentType != null) {
            localVarHeaderParams.put("Content-Type", localVarContentType);
        }

        String[] localVarAuthNames = new String[] { "AiGatewayKey" };
        return localVarApiClient.buildCall(basePath, localVarPath, "POST", localVarQueryParams, localVarCollectionQueryParams, localVarPostBody, localVarHeaderParams, localVarCookieParams, localVarFormParams, localVarAuthNames, _callback);
    }

    @SuppressWarnings("rawtypes")
    private okhttp3.Call uploadAiGatewayFileValidateBeforeCall(File _file, String purpose, String model, String targetModelNames, final ApiCallback _callback) throws ApiException {
        // verify the required parameter '_file' is set
        if (_file == null) {
            throw new ApiException("Missing the required parameter '_file' when calling uploadAiGatewayFile(Async)");
        }

        // verify the required parameter 'purpose' is set
        if (purpose == null) {
            throw new ApiException("Missing the required parameter 'purpose' when calling uploadAiGatewayFile(Async)");
        }

        return uploadAiGatewayFileCall(_file, purpose, model, targetModelNames, _callback);

    }

    /**
     * Загрузка файла
     * Чтобы загрузить файл, отправьте POST-запрос на &#x60;/v1/files&#x60; (multipart/form-data).  Файл загружается для конкретной модели, поэтому &#x60;model&#x60; (или &#x60;target_model_names&#x60;) обязателен: - &#x60;purpose&#x3D;batch&#x60; — входной JSONL-файл Batch API. Все строки должны обращаться к одной модели — той, что указана в &#x60;model&#x60;, иначе вернётся 400. Размер — до 50 МБ и до 50 000 запросов; - &#x60;user_data&#x60; и другие назначения OpenAI — файлы, на которые можно сослаться по &#x60;file_id&#x60; в сообщениях.  Загрузка бесплатна. Сохраняйте &#x60;id&#x60; загруженных файлов: получить список файлов нельзя (&#x60;GET /v1/files&#x60; возвращает 405).
     * @param _file Файл. Для Batch API — JSONL, по одному запросу в строке (required)
     * @param purpose Назначение: &#x60;batch&#x60; — входной файл Batch API, &#x60;user_data&#x60; и другие назначения OpenAI — файлы для передачи модели (required)
     * @param model Модель, для которой загружается файл. Обязателен, если не передан &#x60;target_model_names&#x60; (optional)
     * @param targetModelNames Список моделей через запятую — вместо &#x60;model&#x60; (optional)
     * @return AiGatewayFile
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> Загруженный файл </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Некорректный запрос, например модель в строках файла не совпадает с &#x60;model&#x60; </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Ключ не передан или имеет неверный формат </td><td>  -  </td></tr>
        <tr><td> 403 </td><td> Ключ недействителен или аккаунт заблокирован </td><td>  -  </td></tr>
        <tr><td> 422 </td><td> Не прошла проверка параметров, например не передан файл </td><td>  -  </td></tr>
        <tr><td> 500 </td><td> Внутренняя ошибка или некорректный ответ провайдера модели. Повторите запрос позже </td><td>  -  </td></tr>
     </table>
     */
    public AiGatewayFile uploadAiGatewayFile(File _file, String purpose, String model, String targetModelNames) throws ApiException {
        ApiResponse<AiGatewayFile> localVarResp = uploadAiGatewayFileWithHttpInfo(_file, purpose, model, targetModelNames);
        return localVarResp.getData();
    }

    /**
     * Загрузка файла
     * Чтобы загрузить файл, отправьте POST-запрос на &#x60;/v1/files&#x60; (multipart/form-data).  Файл загружается для конкретной модели, поэтому &#x60;model&#x60; (или &#x60;target_model_names&#x60;) обязателен: - &#x60;purpose&#x3D;batch&#x60; — входной JSONL-файл Batch API. Все строки должны обращаться к одной модели — той, что указана в &#x60;model&#x60;, иначе вернётся 400. Размер — до 50 МБ и до 50 000 запросов; - &#x60;user_data&#x60; и другие назначения OpenAI — файлы, на которые можно сослаться по &#x60;file_id&#x60; в сообщениях.  Загрузка бесплатна. Сохраняйте &#x60;id&#x60; загруженных файлов: получить список файлов нельзя (&#x60;GET /v1/files&#x60; возвращает 405).
     * @param _file Файл. Для Batch API — JSONL, по одному запросу в строке (required)
     * @param purpose Назначение: &#x60;batch&#x60; — входной файл Batch API, &#x60;user_data&#x60; и другие назначения OpenAI — файлы для передачи модели (required)
     * @param model Модель, для которой загружается файл. Обязателен, если не передан &#x60;target_model_names&#x60; (optional)
     * @param targetModelNames Список моделей через запятую — вместо &#x60;model&#x60; (optional)
     * @return ApiResponse&lt;AiGatewayFile&gt;
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> Загруженный файл </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Некорректный запрос, например модель в строках файла не совпадает с &#x60;model&#x60; </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Ключ не передан или имеет неверный формат </td><td>  -  </td></tr>
        <tr><td> 403 </td><td> Ключ недействителен или аккаунт заблокирован </td><td>  -  </td></tr>
        <tr><td> 422 </td><td> Не прошла проверка параметров, например не передан файл </td><td>  -  </td></tr>
        <tr><td> 500 </td><td> Внутренняя ошибка или некорректный ответ провайдера модели. Повторите запрос позже </td><td>  -  </td></tr>
     </table>
     */
    public ApiResponse<AiGatewayFile> uploadAiGatewayFileWithHttpInfo(File _file, String purpose, String model, String targetModelNames) throws ApiException {
        okhttp3.Call localVarCall = uploadAiGatewayFileValidateBeforeCall(_file, purpose, model, targetModelNames, null);
        Type localVarReturnType = new TypeToken<AiGatewayFile>(){}.getType();
        return localVarApiClient.execute(localVarCall, localVarReturnType);
    }

    /**
     * Загрузка файла (asynchronously)
     * Чтобы загрузить файл, отправьте POST-запрос на &#x60;/v1/files&#x60; (multipart/form-data).  Файл загружается для конкретной модели, поэтому &#x60;model&#x60; (или &#x60;target_model_names&#x60;) обязателен: - &#x60;purpose&#x3D;batch&#x60; — входной JSONL-файл Batch API. Все строки должны обращаться к одной модели — той, что указана в &#x60;model&#x60;, иначе вернётся 400. Размер — до 50 МБ и до 50 000 запросов; - &#x60;user_data&#x60; и другие назначения OpenAI — файлы, на которые можно сослаться по &#x60;file_id&#x60; в сообщениях.  Загрузка бесплатна. Сохраняйте &#x60;id&#x60; загруженных файлов: получить список файлов нельзя (&#x60;GET /v1/files&#x60; возвращает 405).
     * @param _file Файл. Для Batch API — JSONL, по одному запросу в строке (required)
     * @param purpose Назначение: &#x60;batch&#x60; — входной файл Batch API, &#x60;user_data&#x60; и другие назначения OpenAI — файлы для передачи модели (required)
     * @param model Модель, для которой загружается файл. Обязателен, если не передан &#x60;target_model_names&#x60; (optional)
     * @param targetModelNames Список моделей через запятую — вместо &#x60;model&#x60; (optional)
     * @param _callback The callback to be executed when the API call finishes
     * @return The request call
     * @throws ApiException If fail to process the API call, e.g. serializing the request body object
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> Загруженный файл </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Некорректный запрос, например модель в строках файла не совпадает с &#x60;model&#x60; </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Ключ не передан или имеет неверный формат </td><td>  -  </td></tr>
        <tr><td> 403 </td><td> Ключ недействителен или аккаунт заблокирован </td><td>  -  </td></tr>
        <tr><td> 422 </td><td> Не прошла проверка параметров, например не передан файл </td><td>  -  </td></tr>
        <tr><td> 500 </td><td> Внутренняя ошибка или некорректный ответ провайдера модели. Повторите запрос позже </td><td>  -  </td></tr>
     </table>
     */
    public okhttp3.Call uploadAiGatewayFileAsync(File _file, String purpose, String model, String targetModelNames, final ApiCallback<AiGatewayFile> _callback) throws ApiException {

        okhttp3.Call localVarCall = uploadAiGatewayFileValidateBeforeCall(_file, purpose, model, targetModelNames, _callback);
        Type localVarReturnType = new TypeToken<AiGatewayFile>(){}.getType();
        localVarApiClient.executeAsync(localVarCall, localVarReturnType, _callback);
        return localVarCall;
    }
}
