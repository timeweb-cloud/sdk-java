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


import org.openapitools.client.model.CreateAiKey;
import org.openapitools.client.model.CreateAiKey201Response;
import org.openapitools.client.model.CreateAiKeyLimit;
import org.openapitools.client.model.CreateAiKeyLimit201Response;
import org.openapitools.client.model.GetAccountStatus403Response;
import org.openapitools.client.model.GetAiKeyCallsStatistic200Response;
import org.openapitools.client.model.GetAiKeyLimits200Response;
import org.openapitools.client.model.GetAiKeyModelsStatistic200Response;
import org.openapitools.client.model.GetAiKeyTokenUsageStatistic200Response;
import org.openapitools.client.model.GetAiKeys200Response;
import org.openapitools.client.model.GetFinances400Response;
import org.openapitools.client.model.GetFinances401Response;
import org.openapitools.client.model.GetFinances429Response;
import org.openapitools.client.model.GetFinances500Response;
import org.openapitools.client.model.GetImage404Response;
import java.time.OffsetDateTime;
import org.openapitools.client.model.SyncAiKeyLimits;
import java.util.UUID;
import org.openapitools.client.model.UpdateAiKey;
import org.openapitools.client.model.UpdateAiKey200Response;
import org.openapitools.client.model.UpdateAiKeyLimit;
import org.openapitools.client.model.UpdateDatabaseInstance409Response;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AiGatewayApi {
    private ApiClient localVarApiClient;
    private int localHostIndex;
    private String localCustomBaseUrl;

    public AiGatewayApi() {
        this(Configuration.getDefaultApiClient());
    }

    public AiGatewayApi(ApiClient apiClient) {
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
     * Build call for createAiKey
     * @param createAiKey  (required)
     * @param _callback Callback for upload/download progress
     * @return Call to execute
     * @throws ApiException If fail to serialize the request body object
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 201 </td><td> Объект JSON c ключами &#x60;ai_key&#x60; и &#x60;key&#x60; </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Некорректный запрос </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Не авторизован </td><td>  -  </td></tr>
        <tr><td> 403 </td><td> Запрещено </td><td>  -  </td></tr>
        <tr><td> 429 </td><td> Слишком много запросов </td><td>  -  </td></tr>
        <tr><td> 500 </td><td> Внутренняя ошибка сервера </td><td>  -  </td></tr>
     </table>
     */
    public okhttp3.Call createAiKeyCall(CreateAiKey createAiKey, final ApiCallback _callback) throws ApiException {
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

        Object localVarPostBody = createAiKey;

        // create path and map variables
        String localVarPath = "/api/v1/cloud-ai/ai-keys";

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
    private okhttp3.Call createAiKeyValidateBeforeCall(CreateAiKey createAiKey, final ApiCallback _callback) throws ApiException {
        // verify the required parameter 'createAiKey' is set
        if (createAiKey == null) {
            throw new ApiException("Missing the required parameter 'createAiKey' when calling createAiKey(Async)");
        }

        return createAiKeyCall(createAiKey, _callback);

    }

    /**
     * Создание ключа AI Gateway
     * Чтобы создать ключ AI Gateway, отправьте POST-запрос на &#x60;/api/v1/cloud-ai/ai-keys&#x60;.  Значение ключа (&#x60;sk-...&#x60;) возвращается в поле &#x60;key&#x60; только в ответе на этот запрос — сохраните его. Если значение утеряно, перевыпустите ключ.  Если список &#x60;fallback_models&#x60; некорректен, вернётся 400 с &#x60;error_code&#x60; &#x60;fallback_chain_invalid&#x60; (больше двух моделей или повторы, правило — в &#x60;details.reason&#x60;) или &#x60;fallback_model_not_found&#x60; (модели нет среди действующих текстовых моделей, список — в &#x60;details.model_names&#x60;).
     * @param createAiKey  (required)
     * @return CreateAiKey201Response
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 201 </td><td> Объект JSON c ключами &#x60;ai_key&#x60; и &#x60;key&#x60; </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Некорректный запрос </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Не авторизован </td><td>  -  </td></tr>
        <tr><td> 403 </td><td> Запрещено </td><td>  -  </td></tr>
        <tr><td> 429 </td><td> Слишком много запросов </td><td>  -  </td></tr>
        <tr><td> 500 </td><td> Внутренняя ошибка сервера </td><td>  -  </td></tr>
     </table>
     */
    public CreateAiKey201Response createAiKey(CreateAiKey createAiKey) throws ApiException {
        ApiResponse<CreateAiKey201Response> localVarResp = createAiKeyWithHttpInfo(createAiKey);
        return localVarResp.getData();
    }

    /**
     * Создание ключа AI Gateway
     * Чтобы создать ключ AI Gateway, отправьте POST-запрос на &#x60;/api/v1/cloud-ai/ai-keys&#x60;.  Значение ключа (&#x60;sk-...&#x60;) возвращается в поле &#x60;key&#x60; только в ответе на этот запрос — сохраните его. Если значение утеряно, перевыпустите ключ.  Если список &#x60;fallback_models&#x60; некорректен, вернётся 400 с &#x60;error_code&#x60; &#x60;fallback_chain_invalid&#x60; (больше двух моделей или повторы, правило — в &#x60;details.reason&#x60;) или &#x60;fallback_model_not_found&#x60; (модели нет среди действующих текстовых моделей, список — в &#x60;details.model_names&#x60;).
     * @param createAiKey  (required)
     * @return ApiResponse&lt;CreateAiKey201Response&gt;
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 201 </td><td> Объект JSON c ключами &#x60;ai_key&#x60; и &#x60;key&#x60; </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Некорректный запрос </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Не авторизован </td><td>  -  </td></tr>
        <tr><td> 403 </td><td> Запрещено </td><td>  -  </td></tr>
        <tr><td> 429 </td><td> Слишком много запросов </td><td>  -  </td></tr>
        <tr><td> 500 </td><td> Внутренняя ошибка сервера </td><td>  -  </td></tr>
     </table>
     */
    public ApiResponse<CreateAiKey201Response> createAiKeyWithHttpInfo(CreateAiKey createAiKey) throws ApiException {
        okhttp3.Call localVarCall = createAiKeyValidateBeforeCall(createAiKey, null);
        Type localVarReturnType = new TypeToken<CreateAiKey201Response>(){}.getType();
        return localVarApiClient.execute(localVarCall, localVarReturnType);
    }

    /**
     * Создание ключа AI Gateway (asynchronously)
     * Чтобы создать ключ AI Gateway, отправьте POST-запрос на &#x60;/api/v1/cloud-ai/ai-keys&#x60;.  Значение ключа (&#x60;sk-...&#x60;) возвращается в поле &#x60;key&#x60; только в ответе на этот запрос — сохраните его. Если значение утеряно, перевыпустите ключ.  Если список &#x60;fallback_models&#x60; некорректен, вернётся 400 с &#x60;error_code&#x60; &#x60;fallback_chain_invalid&#x60; (больше двух моделей или повторы, правило — в &#x60;details.reason&#x60;) или &#x60;fallback_model_not_found&#x60; (модели нет среди действующих текстовых моделей, список — в &#x60;details.model_names&#x60;).
     * @param createAiKey  (required)
     * @param _callback The callback to be executed when the API call finishes
     * @return The request call
     * @throws ApiException If fail to process the API call, e.g. serializing the request body object
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 201 </td><td> Объект JSON c ключами &#x60;ai_key&#x60; и &#x60;key&#x60; </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Некорректный запрос </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Не авторизован </td><td>  -  </td></tr>
        <tr><td> 403 </td><td> Запрещено </td><td>  -  </td></tr>
        <tr><td> 429 </td><td> Слишком много запросов </td><td>  -  </td></tr>
        <tr><td> 500 </td><td> Внутренняя ошибка сервера </td><td>  -  </td></tr>
     </table>
     */
    public okhttp3.Call createAiKeyAsync(CreateAiKey createAiKey, final ApiCallback<CreateAiKey201Response> _callback) throws ApiException {

        okhttp3.Call localVarCall = createAiKeyValidateBeforeCall(createAiKey, _callback);
        Type localVarReturnType = new TypeToken<CreateAiKey201Response>(){}.getType();
        localVarApiClient.executeAsync(localVarCall, localVarReturnType, _callback);
        return localVarCall;
    }
    /**
     * Build call for createAiKeyLimit
     * @param keyId ID ключа AI Gateway (UUID) (required)
     * @param createAiKeyLimit  (required)
     * @param _callback Callback for upload/download progress
     * @return Call to execute
     * @throws ApiException If fail to serialize the request body object
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 201 </td><td> Объект JSON c ключом &#x60;ai_key_entity_limit&#x60; </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Некорректный запрос </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Не авторизован </td><td>  -  </td></tr>
        <tr><td> 403 </td><td> Запрещено </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Не найдено </td><td>  -  </td></tr>
        <tr><td> 409 </td><td> Конфликт </td><td>  -  </td></tr>
        <tr><td> 429 </td><td> Слишком много запросов </td><td>  -  </td></tr>
        <tr><td> 500 </td><td> Внутренняя ошибка сервера </td><td>  -  </td></tr>
     </table>
     */
    public okhttp3.Call createAiKeyLimitCall(UUID keyId, CreateAiKeyLimit createAiKeyLimit, final ApiCallback _callback) throws ApiException {
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

        Object localVarPostBody = createAiKeyLimit;

        // create path and map variables
        String localVarPath = "/api/v1/cloud-ai/ai-keys/{key_id}/entity-limits"
            .replace("{" + "key_id" + "}", localVarApiClient.escapeString(keyId.toString()));

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
    private okhttp3.Call createAiKeyLimitValidateBeforeCall(UUID keyId, CreateAiKeyLimit createAiKeyLimit, final ApiCallback _callback) throws ApiException {
        // verify the required parameter 'keyId' is set
        if (keyId == null) {
            throw new ApiException("Missing the required parameter 'keyId' when calling createAiKeyLimit(Async)");
        }

        // verify the required parameter 'createAiKeyLimit' is set
        if (createAiKeyLimit == null) {
            throw new ApiException("Missing the required parameter 'createAiKeyLimit' when calling createAiKeyLimit(Async)");
        }

        return createAiKeyLimitCall(keyId, createAiKeyLimit, _callback);

    }

    /**
     * Создание лимита ключа AI Gateway
     * Чтобы ограничить расход ключа AI Gateway, отправьте POST-запрос на &#x60;/api/v1/cloud-ai/ai-keys/{key_id}/entity-limits&#x60;.  Лимит задаётся в токенах (&#x60;limit&#x60;) или в деньгах (&#x60;money_limit&#x60;, приоритетнее) на период &#x60;limit_type&#x60; — для всех моделей или для одной (&#x60;model_id&#x60;). Когда лимит исчерпан, запросы ключа к AI Gateway API завершаются ошибкой 422 до начала следующего периода. Если от лимита осталась часть, максимальный размер ответа текстового запроса уменьшается так, чтобы уложиться в остаток.  На один период и одну модель (или на все модели) можно завести только один лимит — повторный вернёт 409 с &#x60;error_code&#x60; &#x60;entity_keys_limit_already_exists&#x60;. Если не передан ни &#x60;limit&#x60;, ни &#x60;money_limit&#x60;, вернётся 400 с &#x60;error_code&#x60; &#x60;entity_keys_limit_value_required&#x60;.
     * @param keyId ID ключа AI Gateway (UUID) (required)
     * @param createAiKeyLimit  (required)
     * @return CreateAiKeyLimit201Response
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 201 </td><td> Объект JSON c ключом &#x60;ai_key_entity_limit&#x60; </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Некорректный запрос </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Не авторизован </td><td>  -  </td></tr>
        <tr><td> 403 </td><td> Запрещено </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Не найдено </td><td>  -  </td></tr>
        <tr><td> 409 </td><td> Конфликт </td><td>  -  </td></tr>
        <tr><td> 429 </td><td> Слишком много запросов </td><td>  -  </td></tr>
        <tr><td> 500 </td><td> Внутренняя ошибка сервера </td><td>  -  </td></tr>
     </table>
     */
    public CreateAiKeyLimit201Response createAiKeyLimit(UUID keyId, CreateAiKeyLimit createAiKeyLimit) throws ApiException {
        ApiResponse<CreateAiKeyLimit201Response> localVarResp = createAiKeyLimitWithHttpInfo(keyId, createAiKeyLimit);
        return localVarResp.getData();
    }

    /**
     * Создание лимита ключа AI Gateway
     * Чтобы ограничить расход ключа AI Gateway, отправьте POST-запрос на &#x60;/api/v1/cloud-ai/ai-keys/{key_id}/entity-limits&#x60;.  Лимит задаётся в токенах (&#x60;limit&#x60;) или в деньгах (&#x60;money_limit&#x60;, приоритетнее) на период &#x60;limit_type&#x60; — для всех моделей или для одной (&#x60;model_id&#x60;). Когда лимит исчерпан, запросы ключа к AI Gateway API завершаются ошибкой 422 до начала следующего периода. Если от лимита осталась часть, максимальный размер ответа текстового запроса уменьшается так, чтобы уложиться в остаток.  На один период и одну модель (или на все модели) можно завести только один лимит — повторный вернёт 409 с &#x60;error_code&#x60; &#x60;entity_keys_limit_already_exists&#x60;. Если не передан ни &#x60;limit&#x60;, ни &#x60;money_limit&#x60;, вернётся 400 с &#x60;error_code&#x60; &#x60;entity_keys_limit_value_required&#x60;.
     * @param keyId ID ключа AI Gateway (UUID) (required)
     * @param createAiKeyLimit  (required)
     * @return ApiResponse&lt;CreateAiKeyLimit201Response&gt;
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 201 </td><td> Объект JSON c ключом &#x60;ai_key_entity_limit&#x60; </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Некорректный запрос </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Не авторизован </td><td>  -  </td></tr>
        <tr><td> 403 </td><td> Запрещено </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Не найдено </td><td>  -  </td></tr>
        <tr><td> 409 </td><td> Конфликт </td><td>  -  </td></tr>
        <tr><td> 429 </td><td> Слишком много запросов </td><td>  -  </td></tr>
        <tr><td> 500 </td><td> Внутренняя ошибка сервера </td><td>  -  </td></tr>
     </table>
     */
    public ApiResponse<CreateAiKeyLimit201Response> createAiKeyLimitWithHttpInfo(UUID keyId, CreateAiKeyLimit createAiKeyLimit) throws ApiException {
        okhttp3.Call localVarCall = createAiKeyLimitValidateBeforeCall(keyId, createAiKeyLimit, null);
        Type localVarReturnType = new TypeToken<CreateAiKeyLimit201Response>(){}.getType();
        return localVarApiClient.execute(localVarCall, localVarReturnType);
    }

    /**
     * Создание лимита ключа AI Gateway (asynchronously)
     * Чтобы ограничить расход ключа AI Gateway, отправьте POST-запрос на &#x60;/api/v1/cloud-ai/ai-keys/{key_id}/entity-limits&#x60;.  Лимит задаётся в токенах (&#x60;limit&#x60;) или в деньгах (&#x60;money_limit&#x60;, приоритетнее) на период &#x60;limit_type&#x60; — для всех моделей или для одной (&#x60;model_id&#x60;). Когда лимит исчерпан, запросы ключа к AI Gateway API завершаются ошибкой 422 до начала следующего периода. Если от лимита осталась часть, максимальный размер ответа текстового запроса уменьшается так, чтобы уложиться в остаток.  На один период и одну модель (или на все модели) можно завести только один лимит — повторный вернёт 409 с &#x60;error_code&#x60; &#x60;entity_keys_limit_already_exists&#x60;. Если не передан ни &#x60;limit&#x60;, ни &#x60;money_limit&#x60;, вернётся 400 с &#x60;error_code&#x60; &#x60;entity_keys_limit_value_required&#x60;.
     * @param keyId ID ключа AI Gateway (UUID) (required)
     * @param createAiKeyLimit  (required)
     * @param _callback The callback to be executed when the API call finishes
     * @return The request call
     * @throws ApiException If fail to process the API call, e.g. serializing the request body object
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 201 </td><td> Объект JSON c ключом &#x60;ai_key_entity_limit&#x60; </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Некорректный запрос </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Не авторизован </td><td>  -  </td></tr>
        <tr><td> 403 </td><td> Запрещено </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Не найдено </td><td>  -  </td></tr>
        <tr><td> 409 </td><td> Конфликт </td><td>  -  </td></tr>
        <tr><td> 429 </td><td> Слишком много запросов </td><td>  -  </td></tr>
        <tr><td> 500 </td><td> Внутренняя ошибка сервера </td><td>  -  </td></tr>
     </table>
     */
    public okhttp3.Call createAiKeyLimitAsync(UUID keyId, CreateAiKeyLimit createAiKeyLimit, final ApiCallback<CreateAiKeyLimit201Response> _callback) throws ApiException {

        okhttp3.Call localVarCall = createAiKeyLimitValidateBeforeCall(keyId, createAiKeyLimit, _callback);
        Type localVarReturnType = new TypeToken<CreateAiKeyLimit201Response>(){}.getType();
        localVarApiClient.executeAsync(localVarCall, localVarReturnType, _callback);
        return localVarCall;
    }
    /**
     * Build call for deleteAiKey
     * @param keyId ID ключа AI Gateway (UUID) (required)
     * @param _callback Callback for upload/download progress
     * @return Call to execute
     * @throws ApiException If fail to serialize the request body object
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 204 </td><td> Ключ удалён </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Некорректный запрос </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Не авторизован </td><td>  -  </td></tr>
        <tr><td> 403 </td><td> Запрещено </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Не найдено </td><td>  -  </td></tr>
        <tr><td> 429 </td><td> Слишком много запросов </td><td>  -  </td></tr>
        <tr><td> 500 </td><td> Внутренняя ошибка сервера </td><td>  -  </td></tr>
     </table>
     */
    public okhttp3.Call deleteAiKeyCall(UUID keyId, final ApiCallback _callback) throws ApiException {
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
        String localVarPath = "/api/v1/cloud-ai/ai-keys/{key_id}"
            .replace("{" + "key_id" + "}", localVarApiClient.escapeString(keyId.toString()));

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
    private okhttp3.Call deleteAiKeyValidateBeforeCall(UUID keyId, final ApiCallback _callback) throws ApiException {
        // verify the required parameter 'keyId' is set
        if (keyId == null) {
            throw new ApiException("Missing the required parameter 'keyId' when calling deleteAiKey(Async)");
        }

        return deleteAiKeyCall(keyId, _callback);

    }

    /**
     * Удаление ключа AI Gateway
     * Чтобы удалить ключ AI Gateway, отправьте DELETE-запрос на &#x60;/api/v1/cloud-ai/ai-keys/{key_id}&#x60;.  После удаления запросы с этим ключом перестают приниматься. Ключи, которыми управляет платформа (&#x60;is_readonly: true&#x60;), удалить нельзя: вернётся 403 с &#x60;error_code&#x60; &#x60;ai_key_is_readonly&#x60;.
     * @param keyId ID ключа AI Gateway (UUID) (required)
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 204 </td><td> Ключ удалён </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Некорректный запрос </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Не авторизован </td><td>  -  </td></tr>
        <tr><td> 403 </td><td> Запрещено </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Не найдено </td><td>  -  </td></tr>
        <tr><td> 429 </td><td> Слишком много запросов </td><td>  -  </td></tr>
        <tr><td> 500 </td><td> Внутренняя ошибка сервера </td><td>  -  </td></tr>
     </table>
     */
    public void deleteAiKey(UUID keyId) throws ApiException {
        deleteAiKeyWithHttpInfo(keyId);
    }

    /**
     * Удаление ключа AI Gateway
     * Чтобы удалить ключ AI Gateway, отправьте DELETE-запрос на &#x60;/api/v1/cloud-ai/ai-keys/{key_id}&#x60;.  После удаления запросы с этим ключом перестают приниматься. Ключи, которыми управляет платформа (&#x60;is_readonly: true&#x60;), удалить нельзя: вернётся 403 с &#x60;error_code&#x60; &#x60;ai_key_is_readonly&#x60;.
     * @param keyId ID ключа AI Gateway (UUID) (required)
     * @return ApiResponse&lt;Void&gt;
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 204 </td><td> Ключ удалён </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Некорректный запрос </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Не авторизован </td><td>  -  </td></tr>
        <tr><td> 403 </td><td> Запрещено </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Не найдено </td><td>  -  </td></tr>
        <tr><td> 429 </td><td> Слишком много запросов </td><td>  -  </td></tr>
        <tr><td> 500 </td><td> Внутренняя ошибка сервера </td><td>  -  </td></tr>
     </table>
     */
    public ApiResponse<Void> deleteAiKeyWithHttpInfo(UUID keyId) throws ApiException {
        okhttp3.Call localVarCall = deleteAiKeyValidateBeforeCall(keyId, null);
        return localVarApiClient.execute(localVarCall);
    }

    /**
     * Удаление ключа AI Gateway (asynchronously)
     * Чтобы удалить ключ AI Gateway, отправьте DELETE-запрос на &#x60;/api/v1/cloud-ai/ai-keys/{key_id}&#x60;.  После удаления запросы с этим ключом перестают приниматься. Ключи, которыми управляет платформа (&#x60;is_readonly: true&#x60;), удалить нельзя: вернётся 403 с &#x60;error_code&#x60; &#x60;ai_key_is_readonly&#x60;.
     * @param keyId ID ключа AI Gateway (UUID) (required)
     * @param _callback The callback to be executed when the API call finishes
     * @return The request call
     * @throws ApiException If fail to process the API call, e.g. serializing the request body object
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 204 </td><td> Ключ удалён </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Некорректный запрос </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Не авторизован </td><td>  -  </td></tr>
        <tr><td> 403 </td><td> Запрещено </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Не найдено </td><td>  -  </td></tr>
        <tr><td> 429 </td><td> Слишком много запросов </td><td>  -  </td></tr>
        <tr><td> 500 </td><td> Внутренняя ошибка сервера </td><td>  -  </td></tr>
     </table>
     */
    public okhttp3.Call deleteAiKeyAsync(UUID keyId, final ApiCallback<Void> _callback) throws ApiException {

        okhttp3.Call localVarCall = deleteAiKeyValidateBeforeCall(keyId, _callback);
        localVarApiClient.executeAsync(localVarCall, _callback);
        return localVarCall;
    }
    /**
     * Build call for deleteAiKeyLimit
     * @param keyId ID ключа AI Gateway (UUID) (required)
     * @param limitId ID лимита (UUID) (required)
     * @param _callback Callback for upload/download progress
     * @return Call to execute
     * @throws ApiException If fail to serialize the request body object
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 204 </td><td> Лимит удалён </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Некорректный запрос </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Не авторизован </td><td>  -  </td></tr>
        <tr><td> 403 </td><td> Запрещено </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Не найдено </td><td>  -  </td></tr>
        <tr><td> 429 </td><td> Слишком много запросов </td><td>  -  </td></tr>
        <tr><td> 500 </td><td> Внутренняя ошибка сервера </td><td>  -  </td></tr>
     </table>
     */
    public okhttp3.Call deleteAiKeyLimitCall(UUID keyId, UUID limitId, final ApiCallback _callback) throws ApiException {
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
        String localVarPath = "/api/v1/cloud-ai/ai-keys/{key_id}/entity-limits/{limit_id}"
            .replace("{" + "key_id" + "}", localVarApiClient.escapeString(keyId.toString()))
            .replace("{" + "limit_id" + "}", localVarApiClient.escapeString(limitId.toString()));

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
    private okhttp3.Call deleteAiKeyLimitValidateBeforeCall(UUID keyId, UUID limitId, final ApiCallback _callback) throws ApiException {
        // verify the required parameter 'keyId' is set
        if (keyId == null) {
            throw new ApiException("Missing the required parameter 'keyId' when calling deleteAiKeyLimit(Async)");
        }

        // verify the required parameter 'limitId' is set
        if (limitId == null) {
            throw new ApiException("Missing the required parameter 'limitId' when calling deleteAiKeyLimit(Async)");
        }

        return deleteAiKeyLimitCall(keyId, limitId, _callback);

    }

    /**
     * Удаление лимита ключа AI Gateway
     * Чтобы удалить лимит ключа AI Gateway, отправьте DELETE-запрос на &#x60;/api/v1/cloud-ai/ai-keys/{key_id}/entity-limits/{limit_id}&#x60;.  Если лимит относится к другому ключу, вернётся 403.
     * @param keyId ID ключа AI Gateway (UUID) (required)
     * @param limitId ID лимита (UUID) (required)
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 204 </td><td> Лимит удалён </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Некорректный запрос </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Не авторизован </td><td>  -  </td></tr>
        <tr><td> 403 </td><td> Запрещено </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Не найдено </td><td>  -  </td></tr>
        <tr><td> 429 </td><td> Слишком много запросов </td><td>  -  </td></tr>
        <tr><td> 500 </td><td> Внутренняя ошибка сервера </td><td>  -  </td></tr>
     </table>
     */
    public void deleteAiKeyLimit(UUID keyId, UUID limitId) throws ApiException {
        deleteAiKeyLimitWithHttpInfo(keyId, limitId);
    }

    /**
     * Удаление лимита ключа AI Gateway
     * Чтобы удалить лимит ключа AI Gateway, отправьте DELETE-запрос на &#x60;/api/v1/cloud-ai/ai-keys/{key_id}/entity-limits/{limit_id}&#x60;.  Если лимит относится к другому ключу, вернётся 403.
     * @param keyId ID ключа AI Gateway (UUID) (required)
     * @param limitId ID лимита (UUID) (required)
     * @return ApiResponse&lt;Void&gt;
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 204 </td><td> Лимит удалён </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Некорректный запрос </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Не авторизован </td><td>  -  </td></tr>
        <tr><td> 403 </td><td> Запрещено </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Не найдено </td><td>  -  </td></tr>
        <tr><td> 429 </td><td> Слишком много запросов </td><td>  -  </td></tr>
        <tr><td> 500 </td><td> Внутренняя ошибка сервера </td><td>  -  </td></tr>
     </table>
     */
    public ApiResponse<Void> deleteAiKeyLimitWithHttpInfo(UUID keyId, UUID limitId) throws ApiException {
        okhttp3.Call localVarCall = deleteAiKeyLimitValidateBeforeCall(keyId, limitId, null);
        return localVarApiClient.execute(localVarCall);
    }

    /**
     * Удаление лимита ключа AI Gateway (asynchronously)
     * Чтобы удалить лимит ключа AI Gateway, отправьте DELETE-запрос на &#x60;/api/v1/cloud-ai/ai-keys/{key_id}/entity-limits/{limit_id}&#x60;.  Если лимит относится к другому ключу, вернётся 403.
     * @param keyId ID ключа AI Gateway (UUID) (required)
     * @param limitId ID лимита (UUID) (required)
     * @param _callback The callback to be executed when the API call finishes
     * @return The request call
     * @throws ApiException If fail to process the API call, e.g. serializing the request body object
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 204 </td><td> Лимит удалён </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Некорректный запрос </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Не авторизован </td><td>  -  </td></tr>
        <tr><td> 403 </td><td> Запрещено </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Не найдено </td><td>  -  </td></tr>
        <tr><td> 429 </td><td> Слишком много запросов </td><td>  -  </td></tr>
        <tr><td> 500 </td><td> Внутренняя ошибка сервера </td><td>  -  </td></tr>
     </table>
     */
    public okhttp3.Call deleteAiKeyLimitAsync(UUID keyId, UUID limitId, final ApiCallback<Void> _callback) throws ApiException {

        okhttp3.Call localVarCall = deleteAiKeyLimitValidateBeforeCall(keyId, limitId, _callback);
        localVarApiClient.executeAsync(localVarCall, _callback);
        return localVarCall;
    }
    /**
     * Build call for getAiKeyCallsStatistic
     * @param entityId ID ключа — поле &#x60;id&#x60; из списка ключей. (required)
     * @param entityType Тип ключа — поле &#x60;entity_type&#x60; из списка ключей: &#x60;aikey&#x60; — ключ, созданный пользователем, &#x60;k8s&#x60; — ключ кластера Kubernetes. Если тип не совпадает с типом ключа, вернётся 404. (required)
     * @param modelName Фильтр по моделям: ID модели или несколько ID через запятую. Значение другого формата (например, с заглавными буквами) игнорируется — статистика возвращается по всем моделям. (optional)
     * @param startTime Начало периода в формате ISO 8601. По умолчанию — за 24 часа до &#x60;end_time&#x60;. (optional)
     * @param endTime Конец периода в формате ISO 8601. По умолчанию — текущий момент. (optional)
     * @param interval Шаг агрегации в минутах. (optional, default to 60)
     * @param _callback Callback for upload/download progress
     * @return Call to execute
     * @throws ApiException If fail to serialize the request body object
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> Объект JSON c ключами &#x60;ai_key_calls_statistics&#x60; и &#x60;meta&#x60; </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Некорректный запрос </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Не авторизован </td><td>  -  </td></tr>
        <tr><td> 403 </td><td> Запрещено </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Не найдено </td><td>  -  </td></tr>
        <tr><td> 429 </td><td> Слишком много запросов </td><td>  -  </td></tr>
        <tr><td> 500 </td><td> Внутренняя ошибка сервера </td><td>  -  </td></tr>
     </table>
     */
    public okhttp3.Call getAiKeyCallsStatisticCall(String entityId, String entityType, String modelName, OffsetDateTime startTime, OffsetDateTime endTime, Integer interval, final ApiCallback _callback) throws ApiException {
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
        String localVarPath = "/api/v1/cloud-ai/ai-keys/statistic/calls";

        List<Pair> localVarQueryParams = new ArrayList<Pair>();
        List<Pair> localVarCollectionQueryParams = new ArrayList<Pair>();
        Map<String, String> localVarHeaderParams = new HashMap<String, String>();
        Map<String, String> localVarCookieParams = new HashMap<String, String>();
        Map<String, Object> localVarFormParams = new HashMap<String, Object>();

        if (entityId != null) {
            localVarQueryParams.addAll(localVarApiClient.parameterToPair("entity_id", entityId));
        }

        if (entityType != null) {
            localVarQueryParams.addAll(localVarApiClient.parameterToPair("entity_type", entityType));
        }

        if (modelName != null) {
            localVarQueryParams.addAll(localVarApiClient.parameterToPair("model_name", modelName));
        }

        if (startTime != null) {
            localVarQueryParams.addAll(localVarApiClient.parameterToPair("start_time", startTime));
        }

        if (endTime != null) {
            localVarQueryParams.addAll(localVarApiClient.parameterToPair("end_time", endTime));
        }

        if (interval != null) {
            localVarQueryParams.addAll(localVarApiClient.parameterToPair("interval", interval));
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
    private okhttp3.Call getAiKeyCallsStatisticValidateBeforeCall(String entityId, String entityType, String modelName, OffsetDateTime startTime, OffsetDateTime endTime, Integer interval, final ApiCallback _callback) throws ApiException {
        // verify the required parameter 'entityId' is set
        if (entityId == null) {
            throw new ApiException("Missing the required parameter 'entityId' when calling getAiKeyCallsStatistic(Async)");
        }

        // verify the required parameter 'entityType' is set
        if (entityType == null) {
            throw new ApiException("Missing the required parameter 'entityType' when calling getAiKeyCallsStatistic(Async)");
        }

        return getAiKeyCallsStatisticCall(entityId, entityType, modelName, startTime, endTime, interval, _callback);

    }

    /**
     * Получение статистики запросов ключа AI Gateway
     * Чтобы получить количество запросов ключа по интервалам времени, отправьте GET-запрос на &#x60;/api/v1/cloud-ai/ai-keys/statistic/calls&#x60;.  Ключ задаётся параметрами &#x60;entity_id&#x60; (ID ключа) и &#x60;entity_type&#x60; (тип ключа). Если ключ не найден, вернётся 404. Период по умолчанию — последние 24 часа. Точки возвращаются для каждого интервала периода, включая интервалы без запросов.  Тело ответа будет представлять собой объект JSON с ключами &#x60;ai_key_calls_statistics&#x60; и &#x60;meta&#x60;.
     * @param entityId ID ключа — поле &#x60;id&#x60; из списка ключей. (required)
     * @param entityType Тип ключа — поле &#x60;entity_type&#x60; из списка ключей: &#x60;aikey&#x60; — ключ, созданный пользователем, &#x60;k8s&#x60; — ключ кластера Kubernetes. Если тип не совпадает с типом ключа, вернётся 404. (required)
     * @param modelName Фильтр по моделям: ID модели или несколько ID через запятую. Значение другого формата (например, с заглавными буквами) игнорируется — статистика возвращается по всем моделям. (optional)
     * @param startTime Начало периода в формате ISO 8601. По умолчанию — за 24 часа до &#x60;end_time&#x60;. (optional)
     * @param endTime Конец периода в формате ISO 8601. По умолчанию — текущий момент. (optional)
     * @param interval Шаг агрегации в минутах. (optional, default to 60)
     * @return GetAiKeyCallsStatistic200Response
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> Объект JSON c ключами &#x60;ai_key_calls_statistics&#x60; и &#x60;meta&#x60; </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Некорректный запрос </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Не авторизован </td><td>  -  </td></tr>
        <tr><td> 403 </td><td> Запрещено </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Не найдено </td><td>  -  </td></tr>
        <tr><td> 429 </td><td> Слишком много запросов </td><td>  -  </td></tr>
        <tr><td> 500 </td><td> Внутренняя ошибка сервера </td><td>  -  </td></tr>
     </table>
     */
    public GetAiKeyCallsStatistic200Response getAiKeyCallsStatistic(String entityId, String entityType, String modelName, OffsetDateTime startTime, OffsetDateTime endTime, Integer interval) throws ApiException {
        ApiResponse<GetAiKeyCallsStatistic200Response> localVarResp = getAiKeyCallsStatisticWithHttpInfo(entityId, entityType, modelName, startTime, endTime, interval);
        return localVarResp.getData();
    }

    /**
     * Получение статистики запросов ключа AI Gateway
     * Чтобы получить количество запросов ключа по интервалам времени, отправьте GET-запрос на &#x60;/api/v1/cloud-ai/ai-keys/statistic/calls&#x60;.  Ключ задаётся параметрами &#x60;entity_id&#x60; (ID ключа) и &#x60;entity_type&#x60; (тип ключа). Если ключ не найден, вернётся 404. Период по умолчанию — последние 24 часа. Точки возвращаются для каждого интервала периода, включая интервалы без запросов.  Тело ответа будет представлять собой объект JSON с ключами &#x60;ai_key_calls_statistics&#x60; и &#x60;meta&#x60;.
     * @param entityId ID ключа — поле &#x60;id&#x60; из списка ключей. (required)
     * @param entityType Тип ключа — поле &#x60;entity_type&#x60; из списка ключей: &#x60;aikey&#x60; — ключ, созданный пользователем, &#x60;k8s&#x60; — ключ кластера Kubernetes. Если тип не совпадает с типом ключа, вернётся 404. (required)
     * @param modelName Фильтр по моделям: ID модели или несколько ID через запятую. Значение другого формата (например, с заглавными буквами) игнорируется — статистика возвращается по всем моделям. (optional)
     * @param startTime Начало периода в формате ISO 8601. По умолчанию — за 24 часа до &#x60;end_time&#x60;. (optional)
     * @param endTime Конец периода в формате ISO 8601. По умолчанию — текущий момент. (optional)
     * @param interval Шаг агрегации в минутах. (optional, default to 60)
     * @return ApiResponse&lt;GetAiKeyCallsStatistic200Response&gt;
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> Объект JSON c ключами &#x60;ai_key_calls_statistics&#x60; и &#x60;meta&#x60; </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Некорректный запрос </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Не авторизован </td><td>  -  </td></tr>
        <tr><td> 403 </td><td> Запрещено </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Не найдено </td><td>  -  </td></tr>
        <tr><td> 429 </td><td> Слишком много запросов </td><td>  -  </td></tr>
        <tr><td> 500 </td><td> Внутренняя ошибка сервера </td><td>  -  </td></tr>
     </table>
     */
    public ApiResponse<GetAiKeyCallsStatistic200Response> getAiKeyCallsStatisticWithHttpInfo(String entityId, String entityType, String modelName, OffsetDateTime startTime, OffsetDateTime endTime, Integer interval) throws ApiException {
        okhttp3.Call localVarCall = getAiKeyCallsStatisticValidateBeforeCall(entityId, entityType, modelName, startTime, endTime, interval, null);
        Type localVarReturnType = new TypeToken<GetAiKeyCallsStatistic200Response>(){}.getType();
        return localVarApiClient.execute(localVarCall, localVarReturnType);
    }

    /**
     * Получение статистики запросов ключа AI Gateway (asynchronously)
     * Чтобы получить количество запросов ключа по интервалам времени, отправьте GET-запрос на &#x60;/api/v1/cloud-ai/ai-keys/statistic/calls&#x60;.  Ключ задаётся параметрами &#x60;entity_id&#x60; (ID ключа) и &#x60;entity_type&#x60; (тип ключа). Если ключ не найден, вернётся 404. Период по умолчанию — последние 24 часа. Точки возвращаются для каждого интервала периода, включая интервалы без запросов.  Тело ответа будет представлять собой объект JSON с ключами &#x60;ai_key_calls_statistics&#x60; и &#x60;meta&#x60;.
     * @param entityId ID ключа — поле &#x60;id&#x60; из списка ключей. (required)
     * @param entityType Тип ключа — поле &#x60;entity_type&#x60; из списка ключей: &#x60;aikey&#x60; — ключ, созданный пользователем, &#x60;k8s&#x60; — ключ кластера Kubernetes. Если тип не совпадает с типом ключа, вернётся 404. (required)
     * @param modelName Фильтр по моделям: ID модели или несколько ID через запятую. Значение другого формата (например, с заглавными буквами) игнорируется — статистика возвращается по всем моделям. (optional)
     * @param startTime Начало периода в формате ISO 8601. По умолчанию — за 24 часа до &#x60;end_time&#x60;. (optional)
     * @param endTime Конец периода в формате ISO 8601. По умолчанию — текущий момент. (optional)
     * @param interval Шаг агрегации в минутах. (optional, default to 60)
     * @param _callback The callback to be executed when the API call finishes
     * @return The request call
     * @throws ApiException If fail to process the API call, e.g. serializing the request body object
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> Объект JSON c ключами &#x60;ai_key_calls_statistics&#x60; и &#x60;meta&#x60; </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Некорректный запрос </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Не авторизован </td><td>  -  </td></tr>
        <tr><td> 403 </td><td> Запрещено </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Не найдено </td><td>  -  </td></tr>
        <tr><td> 429 </td><td> Слишком много запросов </td><td>  -  </td></tr>
        <tr><td> 500 </td><td> Внутренняя ошибка сервера </td><td>  -  </td></tr>
     </table>
     */
    public okhttp3.Call getAiKeyCallsStatisticAsync(String entityId, String entityType, String modelName, OffsetDateTime startTime, OffsetDateTime endTime, Integer interval, final ApiCallback<GetAiKeyCallsStatistic200Response> _callback) throws ApiException {

        okhttp3.Call localVarCall = getAiKeyCallsStatisticValidateBeforeCall(entityId, entityType, modelName, startTime, endTime, interval, _callback);
        Type localVarReturnType = new TypeToken<GetAiKeyCallsStatistic200Response>(){}.getType();
        localVarApiClient.executeAsync(localVarCall, localVarReturnType, _callback);
        return localVarCall;
    }
    /**
     * Build call for getAiKeyLimit
     * @param keyId ID ключа AI Gateway (UUID) (required)
     * @param limitId ID лимита (UUID) (required)
     * @param _callback Callback for upload/download progress
     * @return Call to execute
     * @throws ApiException If fail to serialize the request body object
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> Объект JSON c ключом &#x60;ai_key_entity_limit&#x60; </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Некорректный запрос </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Не авторизован </td><td>  -  </td></tr>
        <tr><td> 403 </td><td> Запрещено </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Не найдено </td><td>  -  </td></tr>
        <tr><td> 429 </td><td> Слишком много запросов </td><td>  -  </td></tr>
        <tr><td> 500 </td><td> Внутренняя ошибка сервера </td><td>  -  </td></tr>
     </table>
     */
    public okhttp3.Call getAiKeyLimitCall(UUID keyId, UUID limitId, final ApiCallback _callback) throws ApiException {
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
        String localVarPath = "/api/v1/cloud-ai/ai-keys/{key_id}/entity-limits/{limit_id}"
            .replace("{" + "key_id" + "}", localVarApiClient.escapeString(keyId.toString()))
            .replace("{" + "limit_id" + "}", localVarApiClient.escapeString(limitId.toString()));

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
    private okhttp3.Call getAiKeyLimitValidateBeforeCall(UUID keyId, UUID limitId, final ApiCallback _callback) throws ApiException {
        // verify the required parameter 'keyId' is set
        if (keyId == null) {
            throw new ApiException("Missing the required parameter 'keyId' when calling getAiKeyLimit(Async)");
        }

        // verify the required parameter 'limitId' is set
        if (limitId == null) {
            throw new ApiException("Missing the required parameter 'limitId' when calling getAiKeyLimit(Async)");
        }

        return getAiKeyLimitCall(keyId, limitId, _callback);

    }

    /**
     * Получение лимита ключа AI Gateway
     * Чтобы получить лимит ключа AI Gateway, отправьте GET-запрос на &#x60;/api/v1/cloud-ai/ai-keys/{key_id}/entity-limits/{limit_id}&#x60;.  Если лимит относится к другому ключу, вернётся 403 с &#x60;error_code&#x60; &#x60;entity_keys_limit_forbidden&#x60;.
     * @param keyId ID ключа AI Gateway (UUID) (required)
     * @param limitId ID лимита (UUID) (required)
     * @return CreateAiKeyLimit201Response
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> Объект JSON c ключом &#x60;ai_key_entity_limit&#x60; </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Некорректный запрос </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Не авторизован </td><td>  -  </td></tr>
        <tr><td> 403 </td><td> Запрещено </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Не найдено </td><td>  -  </td></tr>
        <tr><td> 429 </td><td> Слишком много запросов </td><td>  -  </td></tr>
        <tr><td> 500 </td><td> Внутренняя ошибка сервера </td><td>  -  </td></tr>
     </table>
     */
    public CreateAiKeyLimit201Response getAiKeyLimit(UUID keyId, UUID limitId) throws ApiException {
        ApiResponse<CreateAiKeyLimit201Response> localVarResp = getAiKeyLimitWithHttpInfo(keyId, limitId);
        return localVarResp.getData();
    }

    /**
     * Получение лимита ключа AI Gateway
     * Чтобы получить лимит ключа AI Gateway, отправьте GET-запрос на &#x60;/api/v1/cloud-ai/ai-keys/{key_id}/entity-limits/{limit_id}&#x60;.  Если лимит относится к другому ключу, вернётся 403 с &#x60;error_code&#x60; &#x60;entity_keys_limit_forbidden&#x60;.
     * @param keyId ID ключа AI Gateway (UUID) (required)
     * @param limitId ID лимита (UUID) (required)
     * @return ApiResponse&lt;CreateAiKeyLimit201Response&gt;
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> Объект JSON c ключом &#x60;ai_key_entity_limit&#x60; </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Некорректный запрос </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Не авторизован </td><td>  -  </td></tr>
        <tr><td> 403 </td><td> Запрещено </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Не найдено </td><td>  -  </td></tr>
        <tr><td> 429 </td><td> Слишком много запросов </td><td>  -  </td></tr>
        <tr><td> 500 </td><td> Внутренняя ошибка сервера </td><td>  -  </td></tr>
     </table>
     */
    public ApiResponse<CreateAiKeyLimit201Response> getAiKeyLimitWithHttpInfo(UUID keyId, UUID limitId) throws ApiException {
        okhttp3.Call localVarCall = getAiKeyLimitValidateBeforeCall(keyId, limitId, null);
        Type localVarReturnType = new TypeToken<CreateAiKeyLimit201Response>(){}.getType();
        return localVarApiClient.execute(localVarCall, localVarReturnType);
    }

    /**
     * Получение лимита ключа AI Gateway (asynchronously)
     * Чтобы получить лимит ключа AI Gateway, отправьте GET-запрос на &#x60;/api/v1/cloud-ai/ai-keys/{key_id}/entity-limits/{limit_id}&#x60;.  Если лимит относится к другому ключу, вернётся 403 с &#x60;error_code&#x60; &#x60;entity_keys_limit_forbidden&#x60;.
     * @param keyId ID ключа AI Gateway (UUID) (required)
     * @param limitId ID лимита (UUID) (required)
     * @param _callback The callback to be executed when the API call finishes
     * @return The request call
     * @throws ApiException If fail to process the API call, e.g. serializing the request body object
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> Объект JSON c ключом &#x60;ai_key_entity_limit&#x60; </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Некорректный запрос </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Не авторизован </td><td>  -  </td></tr>
        <tr><td> 403 </td><td> Запрещено </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Не найдено </td><td>  -  </td></tr>
        <tr><td> 429 </td><td> Слишком много запросов </td><td>  -  </td></tr>
        <tr><td> 500 </td><td> Внутренняя ошибка сервера </td><td>  -  </td></tr>
     </table>
     */
    public okhttp3.Call getAiKeyLimitAsync(UUID keyId, UUID limitId, final ApiCallback<CreateAiKeyLimit201Response> _callback) throws ApiException {

        okhttp3.Call localVarCall = getAiKeyLimitValidateBeforeCall(keyId, limitId, _callback);
        Type localVarReturnType = new TypeToken<CreateAiKeyLimit201Response>(){}.getType();
        localVarApiClient.executeAsync(localVarCall, localVarReturnType, _callback);
        return localVarCall;
    }
    /**
     * Build call for getAiKeyLimits
     * @param keyId ID ключа AI Gateway (UUID) (required)
     * @param entityType Фильтр по типу сущности лимита (&#x60;entity_type&#x60;). (optional)
     * @param limitType Фильтр по периоду лимита. (optional)
     * @param _callback Callback for upload/download progress
     * @return Call to execute
     * @throws ApiException If fail to serialize the request body object
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> Объект JSON c ключами &#x60;ai_key_entity_limits&#x60; и &#x60;meta&#x60; </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Некорректный запрос </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Не авторизован </td><td>  -  </td></tr>
        <tr><td> 403 </td><td> Запрещено </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Не найдено </td><td>  -  </td></tr>
        <tr><td> 429 </td><td> Слишком много запросов </td><td>  -  </td></tr>
        <tr><td> 500 </td><td> Внутренняя ошибка сервера </td><td>  -  </td></tr>
     </table>
     */
    public okhttp3.Call getAiKeyLimitsCall(UUID keyId, String entityType, String limitType, final ApiCallback _callback) throws ApiException {
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
        String localVarPath = "/api/v1/cloud-ai/ai-keys/{key_id}/entity-limits"
            .replace("{" + "key_id" + "}", localVarApiClient.escapeString(keyId.toString()));

        List<Pair> localVarQueryParams = new ArrayList<Pair>();
        List<Pair> localVarCollectionQueryParams = new ArrayList<Pair>();
        Map<String, String> localVarHeaderParams = new HashMap<String, String>();
        Map<String, String> localVarCookieParams = new HashMap<String, String>();
        Map<String, Object> localVarFormParams = new HashMap<String, Object>();

        if (entityType != null) {
            localVarQueryParams.addAll(localVarApiClient.parameterToPair("entity_type", entityType));
        }

        if (limitType != null) {
            localVarQueryParams.addAll(localVarApiClient.parameterToPair("limit_type", limitType));
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
    private okhttp3.Call getAiKeyLimitsValidateBeforeCall(UUID keyId, String entityType, String limitType, final ApiCallback _callback) throws ApiException {
        // verify the required parameter 'keyId' is set
        if (keyId == null) {
            throw new ApiException("Missing the required parameter 'keyId' when calling getAiKeyLimits(Async)");
        }

        return getAiKeyLimitsCall(keyId, entityType, limitType, _callback);

    }

    /**
     * Получение списка лимитов ключа AI Gateway
     * Чтобы получить лимиты расхода ключа AI Gateway, отправьте GET-запрос на &#x60;/api/v1/cloud-ai/ai-keys/{key_id}/entity-limits&#x60;.  Тело ответа будет представлять собой объект JSON с ключами &#x60;ai_key_entity_limits&#x60; и &#x60;meta&#x60;.
     * @param keyId ID ключа AI Gateway (UUID) (required)
     * @param entityType Фильтр по типу сущности лимита (&#x60;entity_type&#x60;). (optional)
     * @param limitType Фильтр по периоду лимита. (optional)
     * @return GetAiKeyLimits200Response
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> Объект JSON c ключами &#x60;ai_key_entity_limits&#x60; и &#x60;meta&#x60; </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Некорректный запрос </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Не авторизован </td><td>  -  </td></tr>
        <tr><td> 403 </td><td> Запрещено </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Не найдено </td><td>  -  </td></tr>
        <tr><td> 429 </td><td> Слишком много запросов </td><td>  -  </td></tr>
        <tr><td> 500 </td><td> Внутренняя ошибка сервера </td><td>  -  </td></tr>
     </table>
     */
    public GetAiKeyLimits200Response getAiKeyLimits(UUID keyId, String entityType, String limitType) throws ApiException {
        ApiResponse<GetAiKeyLimits200Response> localVarResp = getAiKeyLimitsWithHttpInfo(keyId, entityType, limitType);
        return localVarResp.getData();
    }

    /**
     * Получение списка лимитов ключа AI Gateway
     * Чтобы получить лимиты расхода ключа AI Gateway, отправьте GET-запрос на &#x60;/api/v1/cloud-ai/ai-keys/{key_id}/entity-limits&#x60;.  Тело ответа будет представлять собой объект JSON с ключами &#x60;ai_key_entity_limits&#x60; и &#x60;meta&#x60;.
     * @param keyId ID ключа AI Gateway (UUID) (required)
     * @param entityType Фильтр по типу сущности лимита (&#x60;entity_type&#x60;). (optional)
     * @param limitType Фильтр по периоду лимита. (optional)
     * @return ApiResponse&lt;GetAiKeyLimits200Response&gt;
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> Объект JSON c ключами &#x60;ai_key_entity_limits&#x60; и &#x60;meta&#x60; </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Некорректный запрос </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Не авторизован </td><td>  -  </td></tr>
        <tr><td> 403 </td><td> Запрещено </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Не найдено </td><td>  -  </td></tr>
        <tr><td> 429 </td><td> Слишком много запросов </td><td>  -  </td></tr>
        <tr><td> 500 </td><td> Внутренняя ошибка сервера </td><td>  -  </td></tr>
     </table>
     */
    public ApiResponse<GetAiKeyLimits200Response> getAiKeyLimitsWithHttpInfo(UUID keyId, String entityType, String limitType) throws ApiException {
        okhttp3.Call localVarCall = getAiKeyLimitsValidateBeforeCall(keyId, entityType, limitType, null);
        Type localVarReturnType = new TypeToken<GetAiKeyLimits200Response>(){}.getType();
        return localVarApiClient.execute(localVarCall, localVarReturnType);
    }

    /**
     * Получение списка лимитов ключа AI Gateway (asynchronously)
     * Чтобы получить лимиты расхода ключа AI Gateway, отправьте GET-запрос на &#x60;/api/v1/cloud-ai/ai-keys/{key_id}/entity-limits&#x60;.  Тело ответа будет представлять собой объект JSON с ключами &#x60;ai_key_entity_limits&#x60; и &#x60;meta&#x60;.
     * @param keyId ID ключа AI Gateway (UUID) (required)
     * @param entityType Фильтр по типу сущности лимита (&#x60;entity_type&#x60;). (optional)
     * @param limitType Фильтр по периоду лимита. (optional)
     * @param _callback The callback to be executed when the API call finishes
     * @return The request call
     * @throws ApiException If fail to process the API call, e.g. serializing the request body object
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> Объект JSON c ключами &#x60;ai_key_entity_limits&#x60; и &#x60;meta&#x60; </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Некорректный запрос </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Не авторизован </td><td>  -  </td></tr>
        <tr><td> 403 </td><td> Запрещено </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Не найдено </td><td>  -  </td></tr>
        <tr><td> 429 </td><td> Слишком много запросов </td><td>  -  </td></tr>
        <tr><td> 500 </td><td> Внутренняя ошибка сервера </td><td>  -  </td></tr>
     </table>
     */
    public okhttp3.Call getAiKeyLimitsAsync(UUID keyId, String entityType, String limitType, final ApiCallback<GetAiKeyLimits200Response> _callback) throws ApiException {

        okhttp3.Call localVarCall = getAiKeyLimitsValidateBeforeCall(keyId, entityType, limitType, _callback);
        Type localVarReturnType = new TypeToken<GetAiKeyLimits200Response>(){}.getType();
        localVarApiClient.executeAsync(localVarCall, localVarReturnType, _callback);
        return localVarCall;
    }
    /**
     * Build call for getAiKeyModelsStatistic
     * @param entityId ID ключа — поле &#x60;id&#x60; из списка ключей. (required)
     * @param entityType Тип ключа — поле &#x60;entity_type&#x60; из списка ключей: &#x60;aikey&#x60; — ключ, созданный пользователем, &#x60;k8s&#x60; — ключ кластера Kubernetes. Если тип не совпадает с типом ключа, вернётся 404. (required)
     * @param modelName Фильтр по моделям: ID модели или несколько ID через запятую. Значение другого формата (например, с заглавными буквами) игнорируется — статистика возвращается по всем моделям. (optional)
     * @param startTime Начало периода в формате ISO 8601. По умолчанию — за 24 часа до &#x60;end_time&#x60;. (optional)
     * @param endTime Конец периода в формате ISO 8601. По умолчанию — текущий момент. (optional)
     * @param _callback Callback for upload/download progress
     * @return Call to execute
     * @throws ApiException If fail to serialize the request body object
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> Объект JSON c ключами &#x60;ai_key_models_statistics&#x60; и &#x60;meta&#x60; </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Некорректный запрос </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Не авторизован </td><td>  -  </td></tr>
        <tr><td> 403 </td><td> Запрещено </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Не найдено </td><td>  -  </td></tr>
        <tr><td> 429 </td><td> Слишком много запросов </td><td>  -  </td></tr>
        <tr><td> 500 </td><td> Внутренняя ошибка сервера </td><td>  -  </td></tr>
     </table>
     */
    public okhttp3.Call getAiKeyModelsStatisticCall(String entityId, String entityType, String modelName, OffsetDateTime startTime, OffsetDateTime endTime, final ApiCallback _callback) throws ApiException {
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
        String localVarPath = "/api/v1/cloud-ai/ai-keys/statistic/models";

        List<Pair> localVarQueryParams = new ArrayList<Pair>();
        List<Pair> localVarCollectionQueryParams = new ArrayList<Pair>();
        Map<String, String> localVarHeaderParams = new HashMap<String, String>();
        Map<String, String> localVarCookieParams = new HashMap<String, String>();
        Map<String, Object> localVarFormParams = new HashMap<String, Object>();

        if (entityId != null) {
            localVarQueryParams.addAll(localVarApiClient.parameterToPair("entity_id", entityId));
        }

        if (entityType != null) {
            localVarQueryParams.addAll(localVarApiClient.parameterToPair("entity_type", entityType));
        }

        if (modelName != null) {
            localVarQueryParams.addAll(localVarApiClient.parameterToPair("model_name", modelName));
        }

        if (startTime != null) {
            localVarQueryParams.addAll(localVarApiClient.parameterToPair("start_time", startTime));
        }

        if (endTime != null) {
            localVarQueryParams.addAll(localVarApiClient.parameterToPair("end_time", endTime));
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
    private okhttp3.Call getAiKeyModelsStatisticValidateBeforeCall(String entityId, String entityType, String modelName, OffsetDateTime startTime, OffsetDateTime endTime, final ApiCallback _callback) throws ApiException {
        // verify the required parameter 'entityId' is set
        if (entityId == null) {
            throw new ApiException("Missing the required parameter 'entityId' when calling getAiKeyModelsStatistic(Async)");
        }

        // verify the required parameter 'entityType' is set
        if (entityType == null) {
            throw new ApiException("Missing the required parameter 'entityType' when calling getAiKeyModelsStatistic(Async)");
        }

        return getAiKeyModelsStatisticCall(entityId, entityType, modelName, startTime, endTime, _callback);

    }

    /**
     * Получение списка моделей, использованных ключом AI Gateway
     * Чтобы получить список моделей, к которым обращался ключ за период, отправьте GET-запрос на &#x60;/api/v1/cloud-ai/ai-keys/statistic/models&#x60;.  Ключ задаётся параметрами &#x60;entity_id&#x60; (ID ключа) и &#x60;entity_type&#x60; (тип ключа). Если ключ не найден, вернётся 404. Период по умолчанию — последние 24 часа. Модели отсортированы по алфавиту.  Тело ответа будет представлять собой объект JSON с ключами &#x60;ai_key_models_statistics&#x60; и &#x60;meta&#x60;.
     * @param entityId ID ключа — поле &#x60;id&#x60; из списка ключей. (required)
     * @param entityType Тип ключа — поле &#x60;entity_type&#x60; из списка ключей: &#x60;aikey&#x60; — ключ, созданный пользователем, &#x60;k8s&#x60; — ключ кластера Kubernetes. Если тип не совпадает с типом ключа, вернётся 404. (required)
     * @param modelName Фильтр по моделям: ID модели или несколько ID через запятую. Значение другого формата (например, с заглавными буквами) игнорируется — статистика возвращается по всем моделям. (optional)
     * @param startTime Начало периода в формате ISO 8601. По умолчанию — за 24 часа до &#x60;end_time&#x60;. (optional)
     * @param endTime Конец периода в формате ISO 8601. По умолчанию — текущий момент. (optional)
     * @return GetAiKeyModelsStatistic200Response
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> Объект JSON c ключами &#x60;ai_key_models_statistics&#x60; и &#x60;meta&#x60; </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Некорректный запрос </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Не авторизован </td><td>  -  </td></tr>
        <tr><td> 403 </td><td> Запрещено </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Не найдено </td><td>  -  </td></tr>
        <tr><td> 429 </td><td> Слишком много запросов </td><td>  -  </td></tr>
        <tr><td> 500 </td><td> Внутренняя ошибка сервера </td><td>  -  </td></tr>
     </table>
     */
    public GetAiKeyModelsStatistic200Response getAiKeyModelsStatistic(String entityId, String entityType, String modelName, OffsetDateTime startTime, OffsetDateTime endTime) throws ApiException {
        ApiResponse<GetAiKeyModelsStatistic200Response> localVarResp = getAiKeyModelsStatisticWithHttpInfo(entityId, entityType, modelName, startTime, endTime);
        return localVarResp.getData();
    }

    /**
     * Получение списка моделей, использованных ключом AI Gateway
     * Чтобы получить список моделей, к которым обращался ключ за период, отправьте GET-запрос на &#x60;/api/v1/cloud-ai/ai-keys/statistic/models&#x60;.  Ключ задаётся параметрами &#x60;entity_id&#x60; (ID ключа) и &#x60;entity_type&#x60; (тип ключа). Если ключ не найден, вернётся 404. Период по умолчанию — последние 24 часа. Модели отсортированы по алфавиту.  Тело ответа будет представлять собой объект JSON с ключами &#x60;ai_key_models_statistics&#x60; и &#x60;meta&#x60;.
     * @param entityId ID ключа — поле &#x60;id&#x60; из списка ключей. (required)
     * @param entityType Тип ключа — поле &#x60;entity_type&#x60; из списка ключей: &#x60;aikey&#x60; — ключ, созданный пользователем, &#x60;k8s&#x60; — ключ кластера Kubernetes. Если тип не совпадает с типом ключа, вернётся 404. (required)
     * @param modelName Фильтр по моделям: ID модели или несколько ID через запятую. Значение другого формата (например, с заглавными буквами) игнорируется — статистика возвращается по всем моделям. (optional)
     * @param startTime Начало периода в формате ISO 8601. По умолчанию — за 24 часа до &#x60;end_time&#x60;. (optional)
     * @param endTime Конец периода в формате ISO 8601. По умолчанию — текущий момент. (optional)
     * @return ApiResponse&lt;GetAiKeyModelsStatistic200Response&gt;
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> Объект JSON c ключами &#x60;ai_key_models_statistics&#x60; и &#x60;meta&#x60; </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Некорректный запрос </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Не авторизован </td><td>  -  </td></tr>
        <tr><td> 403 </td><td> Запрещено </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Не найдено </td><td>  -  </td></tr>
        <tr><td> 429 </td><td> Слишком много запросов </td><td>  -  </td></tr>
        <tr><td> 500 </td><td> Внутренняя ошибка сервера </td><td>  -  </td></tr>
     </table>
     */
    public ApiResponse<GetAiKeyModelsStatistic200Response> getAiKeyModelsStatisticWithHttpInfo(String entityId, String entityType, String modelName, OffsetDateTime startTime, OffsetDateTime endTime) throws ApiException {
        okhttp3.Call localVarCall = getAiKeyModelsStatisticValidateBeforeCall(entityId, entityType, modelName, startTime, endTime, null);
        Type localVarReturnType = new TypeToken<GetAiKeyModelsStatistic200Response>(){}.getType();
        return localVarApiClient.execute(localVarCall, localVarReturnType);
    }

    /**
     * Получение списка моделей, использованных ключом AI Gateway (asynchronously)
     * Чтобы получить список моделей, к которым обращался ключ за период, отправьте GET-запрос на &#x60;/api/v1/cloud-ai/ai-keys/statistic/models&#x60;.  Ключ задаётся параметрами &#x60;entity_id&#x60; (ID ключа) и &#x60;entity_type&#x60; (тип ключа). Если ключ не найден, вернётся 404. Период по умолчанию — последние 24 часа. Модели отсортированы по алфавиту.  Тело ответа будет представлять собой объект JSON с ключами &#x60;ai_key_models_statistics&#x60; и &#x60;meta&#x60;.
     * @param entityId ID ключа — поле &#x60;id&#x60; из списка ключей. (required)
     * @param entityType Тип ключа — поле &#x60;entity_type&#x60; из списка ключей: &#x60;aikey&#x60; — ключ, созданный пользователем, &#x60;k8s&#x60; — ключ кластера Kubernetes. Если тип не совпадает с типом ключа, вернётся 404. (required)
     * @param modelName Фильтр по моделям: ID модели или несколько ID через запятую. Значение другого формата (например, с заглавными буквами) игнорируется — статистика возвращается по всем моделям. (optional)
     * @param startTime Начало периода в формате ISO 8601. По умолчанию — за 24 часа до &#x60;end_time&#x60;. (optional)
     * @param endTime Конец периода в формате ISO 8601. По умолчанию — текущий момент. (optional)
     * @param _callback The callback to be executed when the API call finishes
     * @return The request call
     * @throws ApiException If fail to process the API call, e.g. serializing the request body object
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> Объект JSON c ключами &#x60;ai_key_models_statistics&#x60; и &#x60;meta&#x60; </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Некорректный запрос </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Не авторизован </td><td>  -  </td></tr>
        <tr><td> 403 </td><td> Запрещено </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Не найдено </td><td>  -  </td></tr>
        <tr><td> 429 </td><td> Слишком много запросов </td><td>  -  </td></tr>
        <tr><td> 500 </td><td> Внутренняя ошибка сервера </td><td>  -  </td></tr>
     </table>
     */
    public okhttp3.Call getAiKeyModelsStatisticAsync(String entityId, String entityType, String modelName, OffsetDateTime startTime, OffsetDateTime endTime, final ApiCallback<GetAiKeyModelsStatistic200Response> _callback) throws ApiException {

        okhttp3.Call localVarCall = getAiKeyModelsStatisticValidateBeforeCall(entityId, entityType, modelName, startTime, endTime, _callback);
        Type localVarReturnType = new TypeToken<GetAiKeyModelsStatistic200Response>(){}.getType();
        localVarApiClient.executeAsync(localVarCall, localVarReturnType, _callback);
        return localVarCall;
    }
    /**
     * Build call for getAiKeyTokenUsageStatistic
     * @param entityId ID ключа — поле &#x60;id&#x60; из списка ключей. (required)
     * @param entityType Тип ключа — поле &#x60;entity_type&#x60; из списка ключей: &#x60;aikey&#x60; — ключ, созданный пользователем, &#x60;k8s&#x60; — ключ кластера Kubernetes. Если тип не совпадает с типом ключа, вернётся 404. (required)
     * @param modelName Фильтр по моделям: ID модели или несколько ID через запятую. Значение другого формата (например, с заглавными буквами) игнорируется — статистика возвращается по всем моделям. (optional)
     * @param startTime Начало периода в формате ISO 8601. По умолчанию — за 24 часа до &#x60;end_time&#x60;. (optional)
     * @param endTime Конец периода в формате ISO 8601. По умолчанию — текущий момент. (optional)
     * @param interval Шаг агрегации в минутах. (optional, default to 60)
     * @param _callback Callback for upload/download progress
     * @return Call to execute
     * @throws ApiException If fail to serialize the request body object
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> Объект JSON c ключами &#x60;ai_key_token_statistics&#x60; и &#x60;meta&#x60; </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Некорректный запрос </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Не авторизован </td><td>  -  </td></tr>
        <tr><td> 403 </td><td> Запрещено </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Не найдено </td><td>  -  </td></tr>
        <tr><td> 429 </td><td> Слишком много запросов </td><td>  -  </td></tr>
        <tr><td> 500 </td><td> Внутренняя ошибка сервера </td><td>  -  </td></tr>
     </table>
     */
    public okhttp3.Call getAiKeyTokenUsageStatisticCall(String entityId, String entityType, String modelName, OffsetDateTime startTime, OffsetDateTime endTime, Integer interval, final ApiCallback _callback) throws ApiException {
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
        String localVarPath = "/api/v1/cloud-ai/ai-keys/statistic/token-usage";

        List<Pair> localVarQueryParams = new ArrayList<Pair>();
        List<Pair> localVarCollectionQueryParams = new ArrayList<Pair>();
        Map<String, String> localVarHeaderParams = new HashMap<String, String>();
        Map<String, String> localVarCookieParams = new HashMap<String, String>();
        Map<String, Object> localVarFormParams = new HashMap<String, Object>();

        if (entityId != null) {
            localVarQueryParams.addAll(localVarApiClient.parameterToPair("entity_id", entityId));
        }

        if (entityType != null) {
            localVarQueryParams.addAll(localVarApiClient.parameterToPair("entity_type", entityType));
        }

        if (modelName != null) {
            localVarQueryParams.addAll(localVarApiClient.parameterToPair("model_name", modelName));
        }

        if (startTime != null) {
            localVarQueryParams.addAll(localVarApiClient.parameterToPair("start_time", startTime));
        }

        if (endTime != null) {
            localVarQueryParams.addAll(localVarApiClient.parameterToPair("end_time", endTime));
        }

        if (interval != null) {
            localVarQueryParams.addAll(localVarApiClient.parameterToPair("interval", interval));
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
    private okhttp3.Call getAiKeyTokenUsageStatisticValidateBeforeCall(String entityId, String entityType, String modelName, OffsetDateTime startTime, OffsetDateTime endTime, Integer interval, final ApiCallback _callback) throws ApiException {
        // verify the required parameter 'entityId' is set
        if (entityId == null) {
            throw new ApiException("Missing the required parameter 'entityId' when calling getAiKeyTokenUsageStatistic(Async)");
        }

        // verify the required parameter 'entityType' is set
        if (entityType == null) {
            throw new ApiException("Missing the required parameter 'entityType' when calling getAiKeyTokenUsageStatistic(Async)");
        }

        return getAiKeyTokenUsageStatisticCall(entityId, entityType, modelName, startTime, endTime, interval, _callback);

    }

    /**
     * Получение статистики расхода токенов ключа AI Gateway
     * Чтобы получить расход токенов и стоимость запросов ключа по интервалам времени, отправьте GET-запрос на &#x60;/api/v1/cloud-ai/ai-keys/statistic/token-usage&#x60;.  Ключ задаётся параметрами &#x60;entity_id&#x60; (ID ключа) и &#x60;entity_type&#x60; (тип ключа). Если ключ не найден, вернётся 404. Период по умолчанию — последние 24 часа. Точки возвращаются для каждого интервала периода, включая интервалы без запросов.  Тело ответа будет представлять собой объект JSON с ключами &#x60;ai_key_token_statistics&#x60; и &#x60;meta&#x60;.
     * @param entityId ID ключа — поле &#x60;id&#x60; из списка ключей. (required)
     * @param entityType Тип ключа — поле &#x60;entity_type&#x60; из списка ключей: &#x60;aikey&#x60; — ключ, созданный пользователем, &#x60;k8s&#x60; — ключ кластера Kubernetes. Если тип не совпадает с типом ключа, вернётся 404. (required)
     * @param modelName Фильтр по моделям: ID модели или несколько ID через запятую. Значение другого формата (например, с заглавными буквами) игнорируется — статистика возвращается по всем моделям. (optional)
     * @param startTime Начало периода в формате ISO 8601. По умолчанию — за 24 часа до &#x60;end_time&#x60;. (optional)
     * @param endTime Конец периода в формате ISO 8601. По умолчанию — текущий момент. (optional)
     * @param interval Шаг агрегации в минутах. (optional, default to 60)
     * @return GetAiKeyTokenUsageStatistic200Response
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> Объект JSON c ключами &#x60;ai_key_token_statistics&#x60; и &#x60;meta&#x60; </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Некорректный запрос </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Не авторизован </td><td>  -  </td></tr>
        <tr><td> 403 </td><td> Запрещено </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Не найдено </td><td>  -  </td></tr>
        <tr><td> 429 </td><td> Слишком много запросов </td><td>  -  </td></tr>
        <tr><td> 500 </td><td> Внутренняя ошибка сервера </td><td>  -  </td></tr>
     </table>
     */
    public GetAiKeyTokenUsageStatistic200Response getAiKeyTokenUsageStatistic(String entityId, String entityType, String modelName, OffsetDateTime startTime, OffsetDateTime endTime, Integer interval) throws ApiException {
        ApiResponse<GetAiKeyTokenUsageStatistic200Response> localVarResp = getAiKeyTokenUsageStatisticWithHttpInfo(entityId, entityType, modelName, startTime, endTime, interval);
        return localVarResp.getData();
    }

    /**
     * Получение статистики расхода токенов ключа AI Gateway
     * Чтобы получить расход токенов и стоимость запросов ключа по интервалам времени, отправьте GET-запрос на &#x60;/api/v1/cloud-ai/ai-keys/statistic/token-usage&#x60;.  Ключ задаётся параметрами &#x60;entity_id&#x60; (ID ключа) и &#x60;entity_type&#x60; (тип ключа). Если ключ не найден, вернётся 404. Период по умолчанию — последние 24 часа. Точки возвращаются для каждого интервала периода, включая интервалы без запросов.  Тело ответа будет представлять собой объект JSON с ключами &#x60;ai_key_token_statistics&#x60; и &#x60;meta&#x60;.
     * @param entityId ID ключа — поле &#x60;id&#x60; из списка ключей. (required)
     * @param entityType Тип ключа — поле &#x60;entity_type&#x60; из списка ключей: &#x60;aikey&#x60; — ключ, созданный пользователем, &#x60;k8s&#x60; — ключ кластера Kubernetes. Если тип не совпадает с типом ключа, вернётся 404. (required)
     * @param modelName Фильтр по моделям: ID модели или несколько ID через запятую. Значение другого формата (например, с заглавными буквами) игнорируется — статистика возвращается по всем моделям. (optional)
     * @param startTime Начало периода в формате ISO 8601. По умолчанию — за 24 часа до &#x60;end_time&#x60;. (optional)
     * @param endTime Конец периода в формате ISO 8601. По умолчанию — текущий момент. (optional)
     * @param interval Шаг агрегации в минутах. (optional, default to 60)
     * @return ApiResponse&lt;GetAiKeyTokenUsageStatistic200Response&gt;
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> Объект JSON c ключами &#x60;ai_key_token_statistics&#x60; и &#x60;meta&#x60; </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Некорректный запрос </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Не авторизован </td><td>  -  </td></tr>
        <tr><td> 403 </td><td> Запрещено </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Не найдено </td><td>  -  </td></tr>
        <tr><td> 429 </td><td> Слишком много запросов </td><td>  -  </td></tr>
        <tr><td> 500 </td><td> Внутренняя ошибка сервера </td><td>  -  </td></tr>
     </table>
     */
    public ApiResponse<GetAiKeyTokenUsageStatistic200Response> getAiKeyTokenUsageStatisticWithHttpInfo(String entityId, String entityType, String modelName, OffsetDateTime startTime, OffsetDateTime endTime, Integer interval) throws ApiException {
        okhttp3.Call localVarCall = getAiKeyTokenUsageStatisticValidateBeforeCall(entityId, entityType, modelName, startTime, endTime, interval, null);
        Type localVarReturnType = new TypeToken<GetAiKeyTokenUsageStatistic200Response>(){}.getType();
        return localVarApiClient.execute(localVarCall, localVarReturnType);
    }

    /**
     * Получение статистики расхода токенов ключа AI Gateway (asynchronously)
     * Чтобы получить расход токенов и стоимость запросов ключа по интервалам времени, отправьте GET-запрос на &#x60;/api/v1/cloud-ai/ai-keys/statistic/token-usage&#x60;.  Ключ задаётся параметрами &#x60;entity_id&#x60; (ID ключа) и &#x60;entity_type&#x60; (тип ключа). Если ключ не найден, вернётся 404. Период по умолчанию — последние 24 часа. Точки возвращаются для каждого интервала периода, включая интервалы без запросов.  Тело ответа будет представлять собой объект JSON с ключами &#x60;ai_key_token_statistics&#x60; и &#x60;meta&#x60;.
     * @param entityId ID ключа — поле &#x60;id&#x60; из списка ключей. (required)
     * @param entityType Тип ключа — поле &#x60;entity_type&#x60; из списка ключей: &#x60;aikey&#x60; — ключ, созданный пользователем, &#x60;k8s&#x60; — ключ кластера Kubernetes. Если тип не совпадает с типом ключа, вернётся 404. (required)
     * @param modelName Фильтр по моделям: ID модели или несколько ID через запятую. Значение другого формата (например, с заглавными буквами) игнорируется — статистика возвращается по всем моделям. (optional)
     * @param startTime Начало периода в формате ISO 8601. По умолчанию — за 24 часа до &#x60;end_time&#x60;. (optional)
     * @param endTime Конец периода в формате ISO 8601. По умолчанию — текущий момент. (optional)
     * @param interval Шаг агрегации в минутах. (optional, default to 60)
     * @param _callback The callback to be executed when the API call finishes
     * @return The request call
     * @throws ApiException If fail to process the API call, e.g. serializing the request body object
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> Объект JSON c ключами &#x60;ai_key_token_statistics&#x60; и &#x60;meta&#x60; </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Некорректный запрос </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Не авторизован </td><td>  -  </td></tr>
        <tr><td> 403 </td><td> Запрещено </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Не найдено </td><td>  -  </td></tr>
        <tr><td> 429 </td><td> Слишком много запросов </td><td>  -  </td></tr>
        <tr><td> 500 </td><td> Внутренняя ошибка сервера </td><td>  -  </td></tr>
     </table>
     */
    public okhttp3.Call getAiKeyTokenUsageStatisticAsync(String entityId, String entityType, String modelName, OffsetDateTime startTime, OffsetDateTime endTime, Integer interval, final ApiCallback<GetAiKeyTokenUsageStatistic200Response> _callback) throws ApiException {

        okhttp3.Call localVarCall = getAiKeyTokenUsageStatisticValidateBeforeCall(entityId, entityType, modelName, startTime, endTime, interval, _callback);
        Type localVarReturnType = new TypeToken<GetAiKeyTokenUsageStatistic200Response>(){}.getType();
        localVarApiClient.executeAsync(localVarCall, localVarReturnType, _callback);
        return localVarCall;
    }
    /**
     * Build call for getAiKeys
     * @param _callback Callback for upload/download progress
     * @return Call to execute
     * @throws ApiException If fail to serialize the request body object
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> Объект JSON c ключами &#x60;ai_keys&#x60; и &#x60;meta&#x60; </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Некорректный запрос </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Не авторизован </td><td>  -  </td></tr>
        <tr><td> 403 </td><td> Запрещено </td><td>  -  </td></tr>
        <tr><td> 429 </td><td> Слишком много запросов </td><td>  -  </td></tr>
        <tr><td> 500 </td><td> Внутренняя ошибка сервера </td><td>  -  </td></tr>
     </table>
     */
    public okhttp3.Call getAiKeysCall(final ApiCallback _callback) throws ApiException {
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
        String localVarPath = "/api/v1/cloud-ai/ai-keys";

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
    private okhttp3.Call getAiKeysValidateBeforeCall(final ApiCallback _callback) throws ApiException {
        return getAiKeysCall(_callback);

    }

    /**
     * Получение списка ключей AI Gateway
     * Чтобы получить список ключей AI Gateway, отправьте GET-запрос на &#x60;/api/v1/cloud-ai/ai-keys&#x60;.  В списке — ключи, созданные пользователем, и ключи кластеров Kubernetes, выпущенные платформой. Значения ключей (&#x60;sk-...&#x60;) не возвращаются.  Тело ответа будет представлять собой объект JSON с ключами &#x60;ai_keys&#x60; и &#x60;meta&#x60;.
     * @return GetAiKeys200Response
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> Объект JSON c ключами &#x60;ai_keys&#x60; и &#x60;meta&#x60; </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Некорректный запрос </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Не авторизован </td><td>  -  </td></tr>
        <tr><td> 403 </td><td> Запрещено </td><td>  -  </td></tr>
        <tr><td> 429 </td><td> Слишком много запросов </td><td>  -  </td></tr>
        <tr><td> 500 </td><td> Внутренняя ошибка сервера </td><td>  -  </td></tr>
     </table>
     */
    public GetAiKeys200Response getAiKeys() throws ApiException {
        ApiResponse<GetAiKeys200Response> localVarResp = getAiKeysWithHttpInfo();
        return localVarResp.getData();
    }

    /**
     * Получение списка ключей AI Gateway
     * Чтобы получить список ключей AI Gateway, отправьте GET-запрос на &#x60;/api/v1/cloud-ai/ai-keys&#x60;.  В списке — ключи, созданные пользователем, и ключи кластеров Kubernetes, выпущенные платформой. Значения ключей (&#x60;sk-...&#x60;) не возвращаются.  Тело ответа будет представлять собой объект JSON с ключами &#x60;ai_keys&#x60; и &#x60;meta&#x60;.
     * @return ApiResponse&lt;GetAiKeys200Response&gt;
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> Объект JSON c ключами &#x60;ai_keys&#x60; и &#x60;meta&#x60; </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Некорректный запрос </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Не авторизован </td><td>  -  </td></tr>
        <tr><td> 403 </td><td> Запрещено </td><td>  -  </td></tr>
        <tr><td> 429 </td><td> Слишком много запросов </td><td>  -  </td></tr>
        <tr><td> 500 </td><td> Внутренняя ошибка сервера </td><td>  -  </td></tr>
     </table>
     */
    public ApiResponse<GetAiKeys200Response> getAiKeysWithHttpInfo() throws ApiException {
        okhttp3.Call localVarCall = getAiKeysValidateBeforeCall(null);
        Type localVarReturnType = new TypeToken<GetAiKeys200Response>(){}.getType();
        return localVarApiClient.execute(localVarCall, localVarReturnType);
    }

    /**
     * Получение списка ключей AI Gateway (asynchronously)
     * Чтобы получить список ключей AI Gateway, отправьте GET-запрос на &#x60;/api/v1/cloud-ai/ai-keys&#x60;.  В списке — ключи, созданные пользователем, и ключи кластеров Kubernetes, выпущенные платформой. Значения ключей (&#x60;sk-...&#x60;) не возвращаются.  Тело ответа будет представлять собой объект JSON с ключами &#x60;ai_keys&#x60; и &#x60;meta&#x60;.
     * @param _callback The callback to be executed when the API call finishes
     * @return The request call
     * @throws ApiException If fail to process the API call, e.g. serializing the request body object
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> Объект JSON c ключами &#x60;ai_keys&#x60; и &#x60;meta&#x60; </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Некорректный запрос </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Не авторизован </td><td>  -  </td></tr>
        <tr><td> 403 </td><td> Запрещено </td><td>  -  </td></tr>
        <tr><td> 429 </td><td> Слишком много запросов </td><td>  -  </td></tr>
        <tr><td> 500 </td><td> Внутренняя ошибка сервера </td><td>  -  </td></tr>
     </table>
     */
    public okhttp3.Call getAiKeysAsync(final ApiCallback<GetAiKeys200Response> _callback) throws ApiException {

        okhttp3.Call localVarCall = getAiKeysValidateBeforeCall(_callback);
        Type localVarReturnType = new TypeToken<GetAiKeys200Response>(){}.getType();
        localVarApiClient.executeAsync(localVarCall, localVarReturnType, _callback);
        return localVarCall;
    }
    /**
     * Build call for regenerateAiKey
     * @param keyId ID ключа AI Gateway (UUID) (required)
     * @param _callback Callback for upload/download progress
     * @return Call to execute
     * @throws ApiException If fail to serialize the request body object
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> Объект JSON c ключами &#x60;ai_key&#x60; и &#x60;key&#x60; </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Некорректный запрос </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Не авторизован </td><td>  -  </td></tr>
        <tr><td> 403 </td><td> Запрещено </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Не найдено </td><td>  -  </td></tr>
        <tr><td> 429 </td><td> Слишком много запросов </td><td>  -  </td></tr>
        <tr><td> 500 </td><td> Внутренняя ошибка сервера </td><td>  -  </td></tr>
     </table>
     */
    public okhttp3.Call regenerateAiKeyCall(UUID keyId, final ApiCallback _callback) throws ApiException {
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
        String localVarPath = "/api/v1/cloud-ai/ai-keys/{key_id}/regenerate"
            .replace("{" + "key_id" + "}", localVarApiClient.escapeString(keyId.toString()));

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
        return localVarApiClient.buildCall(basePath, localVarPath, "PUT", localVarQueryParams, localVarCollectionQueryParams, localVarPostBody, localVarHeaderParams, localVarCookieParams, localVarFormParams, localVarAuthNames, _callback);
    }

    @SuppressWarnings("rawtypes")
    private okhttp3.Call regenerateAiKeyValidateBeforeCall(UUID keyId, final ApiCallback _callback) throws ApiException {
        // verify the required parameter 'keyId' is set
        if (keyId == null) {
            throw new ApiException("Missing the required parameter 'keyId' when calling regenerateAiKey(Async)");
        }

        return regenerateAiKeyCall(keyId, _callback);

    }

    /**
     * Перевыпуск ключа AI Gateway
     * Чтобы перевыпустить ключ AI Gateway, отправьте PUT-запрос на &#x60;/api/v1/cloud-ai/ai-keys/{key_id}/regenerate&#x60;.  Ключ получает новое значение, старое перестаёт действовать сразу. Название, лимиты, запасные модели и статистика сохраняются. Новое значение (&#x60;sk-...&#x60;) возвращается в поле &#x60;key&#x60; только в ответе на этот запрос — сохраните его. Поле &#x60;expires_at&#x60; в ответе не заполняется.  Ключи, которыми управляет платформа (&#x60;is_readonly: true&#x60;), перевыпустить нельзя: вернётся 403 с &#x60;error_code&#x60; &#x60;ai_key_is_readonly&#x60;.
     * @param keyId ID ключа AI Gateway (UUID) (required)
     * @return CreateAiKey201Response
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> Объект JSON c ключами &#x60;ai_key&#x60; и &#x60;key&#x60; </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Некорректный запрос </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Не авторизован </td><td>  -  </td></tr>
        <tr><td> 403 </td><td> Запрещено </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Не найдено </td><td>  -  </td></tr>
        <tr><td> 429 </td><td> Слишком много запросов </td><td>  -  </td></tr>
        <tr><td> 500 </td><td> Внутренняя ошибка сервера </td><td>  -  </td></tr>
     </table>
     */
    public CreateAiKey201Response regenerateAiKey(UUID keyId) throws ApiException {
        ApiResponse<CreateAiKey201Response> localVarResp = regenerateAiKeyWithHttpInfo(keyId);
        return localVarResp.getData();
    }

    /**
     * Перевыпуск ключа AI Gateway
     * Чтобы перевыпустить ключ AI Gateway, отправьте PUT-запрос на &#x60;/api/v1/cloud-ai/ai-keys/{key_id}/regenerate&#x60;.  Ключ получает новое значение, старое перестаёт действовать сразу. Название, лимиты, запасные модели и статистика сохраняются. Новое значение (&#x60;sk-...&#x60;) возвращается в поле &#x60;key&#x60; только в ответе на этот запрос — сохраните его. Поле &#x60;expires_at&#x60; в ответе не заполняется.  Ключи, которыми управляет платформа (&#x60;is_readonly: true&#x60;), перевыпустить нельзя: вернётся 403 с &#x60;error_code&#x60; &#x60;ai_key_is_readonly&#x60;.
     * @param keyId ID ключа AI Gateway (UUID) (required)
     * @return ApiResponse&lt;CreateAiKey201Response&gt;
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> Объект JSON c ключами &#x60;ai_key&#x60; и &#x60;key&#x60; </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Некорректный запрос </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Не авторизован </td><td>  -  </td></tr>
        <tr><td> 403 </td><td> Запрещено </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Не найдено </td><td>  -  </td></tr>
        <tr><td> 429 </td><td> Слишком много запросов </td><td>  -  </td></tr>
        <tr><td> 500 </td><td> Внутренняя ошибка сервера </td><td>  -  </td></tr>
     </table>
     */
    public ApiResponse<CreateAiKey201Response> regenerateAiKeyWithHttpInfo(UUID keyId) throws ApiException {
        okhttp3.Call localVarCall = regenerateAiKeyValidateBeforeCall(keyId, null);
        Type localVarReturnType = new TypeToken<CreateAiKey201Response>(){}.getType();
        return localVarApiClient.execute(localVarCall, localVarReturnType);
    }

    /**
     * Перевыпуск ключа AI Gateway (asynchronously)
     * Чтобы перевыпустить ключ AI Gateway, отправьте PUT-запрос на &#x60;/api/v1/cloud-ai/ai-keys/{key_id}/regenerate&#x60;.  Ключ получает новое значение, старое перестаёт действовать сразу. Название, лимиты, запасные модели и статистика сохраняются. Новое значение (&#x60;sk-...&#x60;) возвращается в поле &#x60;key&#x60; только в ответе на этот запрос — сохраните его. Поле &#x60;expires_at&#x60; в ответе не заполняется.  Ключи, которыми управляет платформа (&#x60;is_readonly: true&#x60;), перевыпустить нельзя: вернётся 403 с &#x60;error_code&#x60; &#x60;ai_key_is_readonly&#x60;.
     * @param keyId ID ключа AI Gateway (UUID) (required)
     * @param _callback The callback to be executed when the API call finishes
     * @return The request call
     * @throws ApiException If fail to process the API call, e.g. serializing the request body object
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> Объект JSON c ключами &#x60;ai_key&#x60; и &#x60;key&#x60; </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Некорректный запрос </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Не авторизован </td><td>  -  </td></tr>
        <tr><td> 403 </td><td> Запрещено </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Не найдено </td><td>  -  </td></tr>
        <tr><td> 429 </td><td> Слишком много запросов </td><td>  -  </td></tr>
        <tr><td> 500 </td><td> Внутренняя ошибка сервера </td><td>  -  </td></tr>
     </table>
     */
    public okhttp3.Call regenerateAiKeyAsync(UUID keyId, final ApiCallback<CreateAiKey201Response> _callback) throws ApiException {

        okhttp3.Call localVarCall = regenerateAiKeyValidateBeforeCall(keyId, _callback);
        Type localVarReturnType = new TypeToken<CreateAiKey201Response>(){}.getType();
        localVarApiClient.executeAsync(localVarCall, localVarReturnType, _callback);
        return localVarCall;
    }
    /**
     * Build call for syncAiKeyLimits
     * @param keyId ID ключа AI Gateway (UUID) (required)
     * @param syncAiKeyLimits  (required)
     * @param _callback Callback for upload/download progress
     * @return Call to execute
     * @throws ApiException If fail to serialize the request body object
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> Объект JSON c ключами &#x60;ai_key_entity_limits&#x60; и &#x60;meta&#x60; </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Некорректный запрос </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Не авторизован </td><td>  -  </td></tr>
        <tr><td> 403 </td><td> Запрещено </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Не найдено </td><td>  -  </td></tr>
        <tr><td> 409 </td><td> Конфликт </td><td>  -  </td></tr>
        <tr><td> 429 </td><td> Слишком много запросов </td><td>  -  </td></tr>
        <tr><td> 500 </td><td> Внутренняя ошибка сервера </td><td>  -  </td></tr>
     </table>
     */
    public okhttp3.Call syncAiKeyLimitsCall(UUID keyId, SyncAiKeyLimits syncAiKeyLimits, final ApiCallback _callback) throws ApiException {
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

        Object localVarPostBody = syncAiKeyLimits;

        // create path and map variables
        String localVarPath = "/api/v1/cloud-ai/ai-keys/{key_id}/entity-limits/sync"
            .replace("{" + "key_id" + "}", localVarApiClient.escapeString(keyId.toString()));

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
    private okhttp3.Call syncAiKeyLimitsValidateBeforeCall(UUID keyId, SyncAiKeyLimits syncAiKeyLimits, final ApiCallback _callback) throws ApiException {
        // verify the required parameter 'keyId' is set
        if (keyId == null) {
            throw new ApiException("Missing the required parameter 'keyId' when calling syncAiKeyLimits(Async)");
        }

        // verify the required parameter 'syncAiKeyLimits' is set
        if (syncAiKeyLimits == null) {
            throw new ApiException("Missing the required parameter 'syncAiKeyLimits' when calling syncAiKeyLimits(Async)");
        }

        return syncAiKeyLimitsCall(keyId, syncAiKeyLimits, _callback);

    }

    /**
     * Синхронизация лимитов ключа AI Gateway
     * Чтобы задать все лимиты ключа AI Gateway на один период одним запросом, отправьте PUT-запрос на &#x60;/api/v1/cloud-ai/ai-keys/{key_id}/entity-limits/sync&#x60;.  Лимиты из &#x60;limits&#x60; создаются или обновляются (по &#x60;model_id&#x60;), а лимиты того же периода, которых нет в запросе, удаляются. Изменения применяются целиком или не применяются совсем.  Если в записи не задан ни &#x60;limit&#x60;, ни &#x60;money_limit&#x60; или &#x60;money_limit&#x60; снимается без &#x60;limit&#x60;, вернётся 400 с &#x60;error_code&#x60; &#x60;entity_keys_limit_value_required&#x60;. Если лимиты одновременно изменил другой запрос, вернётся 409 с &#x60;error_code&#x60; &#x60;entity_keys_limits_sync_conflict&#x60; — повторите запрос.  Тело ответа будет представлять собой объект JSON с ключами &#x60;ai_key_entity_limits&#x60; и &#x60;meta&#x60; — лимиты периода после синхронизации.
     * @param keyId ID ключа AI Gateway (UUID) (required)
     * @param syncAiKeyLimits  (required)
     * @return GetAiKeyLimits200Response
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> Объект JSON c ключами &#x60;ai_key_entity_limits&#x60; и &#x60;meta&#x60; </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Некорректный запрос </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Не авторизован </td><td>  -  </td></tr>
        <tr><td> 403 </td><td> Запрещено </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Не найдено </td><td>  -  </td></tr>
        <tr><td> 409 </td><td> Конфликт </td><td>  -  </td></tr>
        <tr><td> 429 </td><td> Слишком много запросов </td><td>  -  </td></tr>
        <tr><td> 500 </td><td> Внутренняя ошибка сервера </td><td>  -  </td></tr>
     </table>
     */
    public GetAiKeyLimits200Response syncAiKeyLimits(UUID keyId, SyncAiKeyLimits syncAiKeyLimits) throws ApiException {
        ApiResponse<GetAiKeyLimits200Response> localVarResp = syncAiKeyLimitsWithHttpInfo(keyId, syncAiKeyLimits);
        return localVarResp.getData();
    }

    /**
     * Синхронизация лимитов ключа AI Gateway
     * Чтобы задать все лимиты ключа AI Gateway на один период одним запросом, отправьте PUT-запрос на &#x60;/api/v1/cloud-ai/ai-keys/{key_id}/entity-limits/sync&#x60;.  Лимиты из &#x60;limits&#x60; создаются или обновляются (по &#x60;model_id&#x60;), а лимиты того же периода, которых нет в запросе, удаляются. Изменения применяются целиком или не применяются совсем.  Если в записи не задан ни &#x60;limit&#x60;, ни &#x60;money_limit&#x60; или &#x60;money_limit&#x60; снимается без &#x60;limit&#x60;, вернётся 400 с &#x60;error_code&#x60; &#x60;entity_keys_limit_value_required&#x60;. Если лимиты одновременно изменил другой запрос, вернётся 409 с &#x60;error_code&#x60; &#x60;entity_keys_limits_sync_conflict&#x60; — повторите запрос.  Тело ответа будет представлять собой объект JSON с ключами &#x60;ai_key_entity_limits&#x60; и &#x60;meta&#x60; — лимиты периода после синхронизации.
     * @param keyId ID ключа AI Gateway (UUID) (required)
     * @param syncAiKeyLimits  (required)
     * @return ApiResponse&lt;GetAiKeyLimits200Response&gt;
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> Объект JSON c ключами &#x60;ai_key_entity_limits&#x60; и &#x60;meta&#x60; </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Некорректный запрос </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Не авторизован </td><td>  -  </td></tr>
        <tr><td> 403 </td><td> Запрещено </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Не найдено </td><td>  -  </td></tr>
        <tr><td> 409 </td><td> Конфликт </td><td>  -  </td></tr>
        <tr><td> 429 </td><td> Слишком много запросов </td><td>  -  </td></tr>
        <tr><td> 500 </td><td> Внутренняя ошибка сервера </td><td>  -  </td></tr>
     </table>
     */
    public ApiResponse<GetAiKeyLimits200Response> syncAiKeyLimitsWithHttpInfo(UUID keyId, SyncAiKeyLimits syncAiKeyLimits) throws ApiException {
        okhttp3.Call localVarCall = syncAiKeyLimitsValidateBeforeCall(keyId, syncAiKeyLimits, null);
        Type localVarReturnType = new TypeToken<GetAiKeyLimits200Response>(){}.getType();
        return localVarApiClient.execute(localVarCall, localVarReturnType);
    }

    /**
     * Синхронизация лимитов ключа AI Gateway (asynchronously)
     * Чтобы задать все лимиты ключа AI Gateway на один период одним запросом, отправьте PUT-запрос на &#x60;/api/v1/cloud-ai/ai-keys/{key_id}/entity-limits/sync&#x60;.  Лимиты из &#x60;limits&#x60; создаются или обновляются (по &#x60;model_id&#x60;), а лимиты того же периода, которых нет в запросе, удаляются. Изменения применяются целиком или не применяются совсем.  Если в записи не задан ни &#x60;limit&#x60;, ни &#x60;money_limit&#x60; или &#x60;money_limit&#x60; снимается без &#x60;limit&#x60;, вернётся 400 с &#x60;error_code&#x60; &#x60;entity_keys_limit_value_required&#x60;. Если лимиты одновременно изменил другой запрос, вернётся 409 с &#x60;error_code&#x60; &#x60;entity_keys_limits_sync_conflict&#x60; — повторите запрос.  Тело ответа будет представлять собой объект JSON с ключами &#x60;ai_key_entity_limits&#x60; и &#x60;meta&#x60; — лимиты периода после синхронизации.
     * @param keyId ID ключа AI Gateway (UUID) (required)
     * @param syncAiKeyLimits  (required)
     * @param _callback The callback to be executed when the API call finishes
     * @return The request call
     * @throws ApiException If fail to process the API call, e.g. serializing the request body object
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> Объект JSON c ключами &#x60;ai_key_entity_limits&#x60; и &#x60;meta&#x60; </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Некорректный запрос </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Не авторизован </td><td>  -  </td></tr>
        <tr><td> 403 </td><td> Запрещено </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Не найдено </td><td>  -  </td></tr>
        <tr><td> 409 </td><td> Конфликт </td><td>  -  </td></tr>
        <tr><td> 429 </td><td> Слишком много запросов </td><td>  -  </td></tr>
        <tr><td> 500 </td><td> Внутренняя ошибка сервера </td><td>  -  </td></tr>
     </table>
     */
    public okhttp3.Call syncAiKeyLimitsAsync(UUID keyId, SyncAiKeyLimits syncAiKeyLimits, final ApiCallback<GetAiKeyLimits200Response> _callback) throws ApiException {

        okhttp3.Call localVarCall = syncAiKeyLimitsValidateBeforeCall(keyId, syncAiKeyLimits, _callback);
        Type localVarReturnType = new TypeToken<GetAiKeyLimits200Response>(){}.getType();
        localVarApiClient.executeAsync(localVarCall, localVarReturnType, _callback);
        return localVarCall;
    }
    /**
     * Build call for updateAiKey
     * @param keyId ID ключа AI Gateway (UUID) (required)
     * @param updateAiKey  (required)
     * @param _callback Callback for upload/download progress
     * @return Call to execute
     * @throws ApiException If fail to serialize the request body object
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> Объект JSON c ключом &#x60;ai_key&#x60; </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Некорректный запрос </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Не авторизован </td><td>  -  </td></tr>
        <tr><td> 403 </td><td> Запрещено </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Не найдено </td><td>  -  </td></tr>
        <tr><td> 429 </td><td> Слишком много запросов </td><td>  -  </td></tr>
        <tr><td> 500 </td><td> Внутренняя ошибка сервера </td><td>  -  </td></tr>
     </table>
     */
    public okhttp3.Call updateAiKeyCall(UUID keyId, UpdateAiKey updateAiKey, final ApiCallback _callback) throws ApiException {
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

        Object localVarPostBody = updateAiKey;

        // create path and map variables
        String localVarPath = "/api/v1/cloud-ai/ai-keys/{key_id}"
            .replace("{" + "key_id" + "}", localVarApiClient.escapeString(keyId.toString()));

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
        return localVarApiClient.buildCall(basePath, localVarPath, "PATCH", localVarQueryParams, localVarCollectionQueryParams, localVarPostBody, localVarHeaderParams, localVarCookieParams, localVarFormParams, localVarAuthNames, _callback);
    }

    @SuppressWarnings("rawtypes")
    private okhttp3.Call updateAiKeyValidateBeforeCall(UUID keyId, UpdateAiKey updateAiKey, final ApiCallback _callback) throws ApiException {
        // verify the required parameter 'keyId' is set
        if (keyId == null) {
            throw new ApiException("Missing the required parameter 'keyId' when calling updateAiKey(Async)");
        }

        // verify the required parameter 'updateAiKey' is set
        if (updateAiKey == null) {
            throw new ApiException("Missing the required parameter 'updateAiKey' when calling updateAiKey(Async)");
        }

        return updateAiKeyCall(keyId, updateAiKey, _callback);

    }

    /**
     * Изменение ключа AI Gateway
     * Чтобы изменить ключ AI Gateway, отправьте PATCH-запрос на &#x60;/api/v1/cloud-ai/ai-keys/{key_id}&#x60;, передав изменяемые поля.  Ключи, которыми управляет платформа (&#x60;is_readonly: true&#x60; — ключи AI-решений и кластеров Kubernetes), изменить нельзя: вернётся 403 с &#x60;error_code&#x60; &#x60;ai_key_is_readonly&#x60;.  Ошибки в &#x60;fallback_models&#x60; — как при создании ключа (400). Поле &#x60;expires_at&#x60; в ответе этого запроса не заполняется — срок действия ключа смотрите в списке ключей.
     * @param keyId ID ключа AI Gateway (UUID) (required)
     * @param updateAiKey  (required)
     * @return UpdateAiKey200Response
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> Объект JSON c ключом &#x60;ai_key&#x60; </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Некорректный запрос </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Не авторизован </td><td>  -  </td></tr>
        <tr><td> 403 </td><td> Запрещено </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Не найдено </td><td>  -  </td></tr>
        <tr><td> 429 </td><td> Слишком много запросов </td><td>  -  </td></tr>
        <tr><td> 500 </td><td> Внутренняя ошибка сервера </td><td>  -  </td></tr>
     </table>
     */
    public UpdateAiKey200Response updateAiKey(UUID keyId, UpdateAiKey updateAiKey) throws ApiException {
        ApiResponse<UpdateAiKey200Response> localVarResp = updateAiKeyWithHttpInfo(keyId, updateAiKey);
        return localVarResp.getData();
    }

    /**
     * Изменение ключа AI Gateway
     * Чтобы изменить ключ AI Gateway, отправьте PATCH-запрос на &#x60;/api/v1/cloud-ai/ai-keys/{key_id}&#x60;, передав изменяемые поля.  Ключи, которыми управляет платформа (&#x60;is_readonly: true&#x60; — ключи AI-решений и кластеров Kubernetes), изменить нельзя: вернётся 403 с &#x60;error_code&#x60; &#x60;ai_key_is_readonly&#x60;.  Ошибки в &#x60;fallback_models&#x60; — как при создании ключа (400). Поле &#x60;expires_at&#x60; в ответе этого запроса не заполняется — срок действия ключа смотрите в списке ключей.
     * @param keyId ID ключа AI Gateway (UUID) (required)
     * @param updateAiKey  (required)
     * @return ApiResponse&lt;UpdateAiKey200Response&gt;
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> Объект JSON c ключом &#x60;ai_key&#x60; </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Некорректный запрос </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Не авторизован </td><td>  -  </td></tr>
        <tr><td> 403 </td><td> Запрещено </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Не найдено </td><td>  -  </td></tr>
        <tr><td> 429 </td><td> Слишком много запросов </td><td>  -  </td></tr>
        <tr><td> 500 </td><td> Внутренняя ошибка сервера </td><td>  -  </td></tr>
     </table>
     */
    public ApiResponse<UpdateAiKey200Response> updateAiKeyWithHttpInfo(UUID keyId, UpdateAiKey updateAiKey) throws ApiException {
        okhttp3.Call localVarCall = updateAiKeyValidateBeforeCall(keyId, updateAiKey, null);
        Type localVarReturnType = new TypeToken<UpdateAiKey200Response>(){}.getType();
        return localVarApiClient.execute(localVarCall, localVarReturnType);
    }

    /**
     * Изменение ключа AI Gateway (asynchronously)
     * Чтобы изменить ключ AI Gateway, отправьте PATCH-запрос на &#x60;/api/v1/cloud-ai/ai-keys/{key_id}&#x60;, передав изменяемые поля.  Ключи, которыми управляет платформа (&#x60;is_readonly: true&#x60; — ключи AI-решений и кластеров Kubernetes), изменить нельзя: вернётся 403 с &#x60;error_code&#x60; &#x60;ai_key_is_readonly&#x60;.  Ошибки в &#x60;fallback_models&#x60; — как при создании ключа (400). Поле &#x60;expires_at&#x60; в ответе этого запроса не заполняется — срок действия ключа смотрите в списке ключей.
     * @param keyId ID ключа AI Gateway (UUID) (required)
     * @param updateAiKey  (required)
     * @param _callback The callback to be executed when the API call finishes
     * @return The request call
     * @throws ApiException If fail to process the API call, e.g. serializing the request body object
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> Объект JSON c ключом &#x60;ai_key&#x60; </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Некорректный запрос </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Не авторизован </td><td>  -  </td></tr>
        <tr><td> 403 </td><td> Запрещено </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Не найдено </td><td>  -  </td></tr>
        <tr><td> 429 </td><td> Слишком много запросов </td><td>  -  </td></tr>
        <tr><td> 500 </td><td> Внутренняя ошибка сервера </td><td>  -  </td></tr>
     </table>
     */
    public okhttp3.Call updateAiKeyAsync(UUID keyId, UpdateAiKey updateAiKey, final ApiCallback<UpdateAiKey200Response> _callback) throws ApiException {

        okhttp3.Call localVarCall = updateAiKeyValidateBeforeCall(keyId, updateAiKey, _callback);
        Type localVarReturnType = new TypeToken<UpdateAiKey200Response>(){}.getType();
        localVarApiClient.executeAsync(localVarCall, localVarReturnType, _callback);
        return localVarCall;
    }
    /**
     * Build call for updateAiKeyLimit
     * @param keyId ID ключа AI Gateway (UUID) (required)
     * @param limitId ID лимита (UUID) (required)
     * @param updateAiKeyLimit  (required)
     * @param _callback Callback for upload/download progress
     * @return Call to execute
     * @throws ApiException If fail to serialize the request body object
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> Объект JSON c ключом &#x60;ai_key_entity_limit&#x60; </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Некорректный запрос </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Не авторизован </td><td>  -  </td></tr>
        <tr><td> 403 </td><td> Запрещено </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Не найдено </td><td>  -  </td></tr>
        <tr><td> 429 </td><td> Слишком много запросов </td><td>  -  </td></tr>
        <tr><td> 500 </td><td> Внутренняя ошибка сервера </td><td>  -  </td></tr>
     </table>
     */
    public okhttp3.Call updateAiKeyLimitCall(UUID keyId, UUID limitId, UpdateAiKeyLimit updateAiKeyLimit, final ApiCallback _callback) throws ApiException {
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

        Object localVarPostBody = updateAiKeyLimit;

        // create path and map variables
        String localVarPath = "/api/v1/cloud-ai/ai-keys/{key_id}/entity-limits/{limit_id}"
            .replace("{" + "key_id" + "}", localVarApiClient.escapeString(keyId.toString()))
            .replace("{" + "limit_id" + "}", localVarApiClient.escapeString(limitId.toString()));

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
        return localVarApiClient.buildCall(basePath, localVarPath, "PATCH", localVarQueryParams, localVarCollectionQueryParams, localVarPostBody, localVarHeaderParams, localVarCookieParams, localVarFormParams, localVarAuthNames, _callback);
    }

    @SuppressWarnings("rawtypes")
    private okhttp3.Call updateAiKeyLimitValidateBeforeCall(UUID keyId, UUID limitId, UpdateAiKeyLimit updateAiKeyLimit, final ApiCallback _callback) throws ApiException {
        // verify the required parameter 'keyId' is set
        if (keyId == null) {
            throw new ApiException("Missing the required parameter 'keyId' when calling updateAiKeyLimit(Async)");
        }

        // verify the required parameter 'limitId' is set
        if (limitId == null) {
            throw new ApiException("Missing the required parameter 'limitId' when calling updateAiKeyLimit(Async)");
        }

        // verify the required parameter 'updateAiKeyLimit' is set
        if (updateAiKeyLimit == null) {
            throw new ApiException("Missing the required parameter 'updateAiKeyLimit' when calling updateAiKeyLimit(Async)");
        }

        return updateAiKeyLimitCall(keyId, limitId, updateAiKeyLimit, _callback);

    }

    /**
     * Изменение лимита ключа AI Gateway
     * Чтобы изменить лимит ключа AI Gateway, отправьте PATCH-запрос на &#x60;/api/v1/cloud-ai/ai-keys/{key_id}/entity-limits/{limit_id}&#x60;.  Не переданные поля не меняются. Если не передан ни &#x60;limit&#x60;, ни &#x60;money_limit&#x60; или &#x60;money_limit&#x60; снимается без &#x60;limit&#x60;, вернётся 400 с &#x60;error_code&#x60; &#x60;entity_keys_limit_value_required&#x60;. Если лимит относится к другому ключу, вернётся 403.
     * @param keyId ID ключа AI Gateway (UUID) (required)
     * @param limitId ID лимита (UUID) (required)
     * @param updateAiKeyLimit  (required)
     * @return CreateAiKeyLimit201Response
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> Объект JSON c ключом &#x60;ai_key_entity_limit&#x60; </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Некорректный запрос </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Не авторизован </td><td>  -  </td></tr>
        <tr><td> 403 </td><td> Запрещено </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Не найдено </td><td>  -  </td></tr>
        <tr><td> 429 </td><td> Слишком много запросов </td><td>  -  </td></tr>
        <tr><td> 500 </td><td> Внутренняя ошибка сервера </td><td>  -  </td></tr>
     </table>
     */
    public CreateAiKeyLimit201Response updateAiKeyLimit(UUID keyId, UUID limitId, UpdateAiKeyLimit updateAiKeyLimit) throws ApiException {
        ApiResponse<CreateAiKeyLimit201Response> localVarResp = updateAiKeyLimitWithHttpInfo(keyId, limitId, updateAiKeyLimit);
        return localVarResp.getData();
    }

    /**
     * Изменение лимита ключа AI Gateway
     * Чтобы изменить лимит ключа AI Gateway, отправьте PATCH-запрос на &#x60;/api/v1/cloud-ai/ai-keys/{key_id}/entity-limits/{limit_id}&#x60;.  Не переданные поля не меняются. Если не передан ни &#x60;limit&#x60;, ни &#x60;money_limit&#x60; или &#x60;money_limit&#x60; снимается без &#x60;limit&#x60;, вернётся 400 с &#x60;error_code&#x60; &#x60;entity_keys_limit_value_required&#x60;. Если лимит относится к другому ключу, вернётся 403.
     * @param keyId ID ключа AI Gateway (UUID) (required)
     * @param limitId ID лимита (UUID) (required)
     * @param updateAiKeyLimit  (required)
     * @return ApiResponse&lt;CreateAiKeyLimit201Response&gt;
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> Объект JSON c ключом &#x60;ai_key_entity_limit&#x60; </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Некорректный запрос </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Не авторизован </td><td>  -  </td></tr>
        <tr><td> 403 </td><td> Запрещено </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Не найдено </td><td>  -  </td></tr>
        <tr><td> 429 </td><td> Слишком много запросов </td><td>  -  </td></tr>
        <tr><td> 500 </td><td> Внутренняя ошибка сервера </td><td>  -  </td></tr>
     </table>
     */
    public ApiResponse<CreateAiKeyLimit201Response> updateAiKeyLimitWithHttpInfo(UUID keyId, UUID limitId, UpdateAiKeyLimit updateAiKeyLimit) throws ApiException {
        okhttp3.Call localVarCall = updateAiKeyLimitValidateBeforeCall(keyId, limitId, updateAiKeyLimit, null);
        Type localVarReturnType = new TypeToken<CreateAiKeyLimit201Response>(){}.getType();
        return localVarApiClient.execute(localVarCall, localVarReturnType);
    }

    /**
     * Изменение лимита ключа AI Gateway (asynchronously)
     * Чтобы изменить лимит ключа AI Gateway, отправьте PATCH-запрос на &#x60;/api/v1/cloud-ai/ai-keys/{key_id}/entity-limits/{limit_id}&#x60;.  Не переданные поля не меняются. Если не передан ни &#x60;limit&#x60;, ни &#x60;money_limit&#x60; или &#x60;money_limit&#x60; снимается без &#x60;limit&#x60;, вернётся 400 с &#x60;error_code&#x60; &#x60;entity_keys_limit_value_required&#x60;. Если лимит относится к другому ключу, вернётся 403.
     * @param keyId ID ключа AI Gateway (UUID) (required)
     * @param limitId ID лимита (UUID) (required)
     * @param updateAiKeyLimit  (required)
     * @param _callback The callback to be executed when the API call finishes
     * @return The request call
     * @throws ApiException If fail to process the API call, e.g. serializing the request body object
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> Объект JSON c ключом &#x60;ai_key_entity_limit&#x60; </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Некорректный запрос </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Не авторизован </td><td>  -  </td></tr>
        <tr><td> 403 </td><td> Запрещено </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Не найдено </td><td>  -  </td></tr>
        <tr><td> 429 </td><td> Слишком много запросов </td><td>  -  </td></tr>
        <tr><td> 500 </td><td> Внутренняя ошибка сервера </td><td>  -  </td></tr>
     </table>
     */
    public okhttp3.Call updateAiKeyLimitAsync(UUID keyId, UUID limitId, UpdateAiKeyLimit updateAiKeyLimit, final ApiCallback<CreateAiKeyLimit201Response> _callback) throws ApiException {

        okhttp3.Call localVarCall = updateAiKeyLimitValidateBeforeCall(keyId, limitId, updateAiKeyLimit, _callback);
        Type localVarReturnType = new TypeToken<CreateAiKeyLimit201Response>(){}.getType();
        localVarApiClient.executeAsync(localVarCall, localVarReturnType, _callback);
        return localVarCall;
    }
}
