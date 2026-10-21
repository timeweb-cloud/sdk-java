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


import org.openapitools.client.model.AddTokenPackage;
import org.openapitools.client.model.ChangeAgentModel;
import org.openapitools.client.model.CreateAgent;
import org.openapitools.client.model.CreateAgent201Response;
import org.openapitools.client.model.CreateAgentEntityLimit;
import org.openapitools.client.model.CreateAgentEntityLimit201Response;
import org.openapitools.client.model.CreateAgentV2;
import org.openapitools.client.model.DeleteAgent;
import org.openapitools.client.model.ExportAgentChatsCsv200Response;
import org.openapitools.client.model.GetAccountStatus403Response;
import org.openapitools.client.model.GetAgentChatList200Response;
import org.openapitools.client.model.GetAgentChatMessages200Response;
import org.openapitools.client.model.GetAgentEntityLimits200Response;
import org.openapitools.client.model.GetAgentStatistics200Response;
import org.openapitools.client.model.GetAgentV3200Response;
import org.openapitools.client.model.GetAgents200Response;
import org.openapitools.client.model.GetAgentsV3200Response;
import org.openapitools.client.model.GetFinances400Response;
import org.openapitools.client.model.GetFinances401Response;
import org.openapitools.client.model.GetFinances429Response;
import org.openapitools.client.model.GetFinances500Response;
import org.openapitools.client.model.GetImage404Response;
import java.time.OffsetDateTime;
import org.openapitools.client.model.SyncAgentEntityLimits;
import java.util.UUID;
import org.openapitools.client.model.UpdateAgent;
import org.openapitools.client.model.UpdateAgentEntityLimit;
import org.openapitools.client.model.UpdateDatabaseInstance409Response;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AiAgentsApi {
    private ApiClient localVarApiClient;
    private int localHostIndex;
    private String localCustomBaseUrl;

    public AiAgentsApi() {
        this(Configuration.getDefaultApiClient());
    }

    public AiAgentsApi(ApiClient apiClient) {
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
     * Build call for addAdditionalTokenPackage
     * @param id ID агента (required)
     * @param addTokenPackage  (optional)
     * @param _callback Callback for upload/download progress
     * @return Call to execute
     * @throws ApiException If fail to serialize the request body object
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 204 </td><td> Дополнительный пакет токенов успешно добавлен </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Некорректный запрос </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Не авторизован </td><td>  -  </td></tr>
        <tr><td> 403 </td><td> Запрещено </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Не найдено </td><td>  -  </td></tr>
        <tr><td> 429 </td><td> Слишком много запросов </td><td>  -  </td></tr>
        <tr><td> 500 </td><td> Внутренняя ошибка сервера </td><td>  -  </td></tr>
     </table>
     * @deprecated
     */
    @Deprecated
    public okhttp3.Call addAdditionalTokenPackageCall(Integer id, AddTokenPackage addTokenPackage, final ApiCallback _callback) throws ApiException {
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

        Object localVarPostBody = addTokenPackage;

        // create path and map variables
        String localVarPath = "/api/v1/cloud-ai/agents/{id}/add-additional-token-package"
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
        return localVarApiClient.buildCall(basePath, localVarPath, "POST", localVarQueryParams, localVarCollectionQueryParams, localVarPostBody, localVarHeaderParams, localVarCookieParams, localVarFormParams, localVarAuthNames, _callback);
    }

    @Deprecated
    @SuppressWarnings("rawtypes")
    private okhttp3.Call addAdditionalTokenPackageValidateBeforeCall(Integer id, AddTokenPackage addTokenPackage, final ApiCallback _callback) throws ApiException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new ApiException("Missing the required parameter 'id' when calling addAdditionalTokenPackage(Async)");
        }

        return addAdditionalTokenPackageCall(id, addTokenPackage, _callback);

    }

    /**
     * Добавление дополнительного пакета токенов
     * Чтобы докупить дополнительные пакеты токенов для AI агента, отправьте POST-запрос на &#x60;/api/v1/cloud-ai/agents/{id}/add-additional-token-package&#x60;.  Метод относится к устаревшей схеме оплаты пакетами токенов и работает только для агентов с пакетом (&#x60;token_package_id&#x60; в ответе агента). Для агентов с поресурсной оплатой вернётся 400 &#x60;per-resource_agents_do_not_support_additional_token_packages&#x60;. Стоимость (цена дополнительного пакета модели агента × &#x60;count&#x60;) сразу списывается с баланса; при нехватке средств — 400 &#x60;insufficient_balance&#x60;, если подписка агента истекла — 400 &#x60;subscription_expired&#x60;. После покупки заблокированный агент разблокируется.  Для токенов дополнительных пользователей аккаунта нужна роль &#x60;ai:write&#x60;.
     * @param id ID агента (required)
     * @param addTokenPackage  (optional)
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 204 </td><td> Дополнительный пакет токенов успешно добавлен </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Некорректный запрос </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Не авторизован </td><td>  -  </td></tr>
        <tr><td> 403 </td><td> Запрещено </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Не найдено </td><td>  -  </td></tr>
        <tr><td> 429 </td><td> Слишком много запросов </td><td>  -  </td></tr>
        <tr><td> 500 </td><td> Внутренняя ошибка сервера </td><td>  -  </td></tr>
     </table>
     * @deprecated
     */
    @Deprecated
    public void addAdditionalTokenPackage(Integer id, AddTokenPackage addTokenPackage) throws ApiException {
        addAdditionalTokenPackageWithHttpInfo(id, addTokenPackage);
    }

    /**
     * Добавление дополнительного пакета токенов
     * Чтобы докупить дополнительные пакеты токенов для AI агента, отправьте POST-запрос на &#x60;/api/v1/cloud-ai/agents/{id}/add-additional-token-package&#x60;.  Метод относится к устаревшей схеме оплаты пакетами токенов и работает только для агентов с пакетом (&#x60;token_package_id&#x60; в ответе агента). Для агентов с поресурсной оплатой вернётся 400 &#x60;per-resource_agents_do_not_support_additional_token_packages&#x60;. Стоимость (цена дополнительного пакета модели агента × &#x60;count&#x60;) сразу списывается с баланса; при нехватке средств — 400 &#x60;insufficient_balance&#x60;, если подписка агента истекла — 400 &#x60;subscription_expired&#x60;. После покупки заблокированный агент разблокируется.  Для токенов дополнительных пользователей аккаунта нужна роль &#x60;ai:write&#x60;.
     * @param id ID агента (required)
     * @param addTokenPackage  (optional)
     * @return ApiResponse&lt;Void&gt;
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 204 </td><td> Дополнительный пакет токенов успешно добавлен </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Некорректный запрос </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Не авторизован </td><td>  -  </td></tr>
        <tr><td> 403 </td><td> Запрещено </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Не найдено </td><td>  -  </td></tr>
        <tr><td> 429 </td><td> Слишком много запросов </td><td>  -  </td></tr>
        <tr><td> 500 </td><td> Внутренняя ошибка сервера </td><td>  -  </td></tr>
     </table>
     * @deprecated
     */
    @Deprecated
    public ApiResponse<Void> addAdditionalTokenPackageWithHttpInfo(Integer id, AddTokenPackage addTokenPackage) throws ApiException {
        okhttp3.Call localVarCall = addAdditionalTokenPackageValidateBeforeCall(id, addTokenPackage, null);
        return localVarApiClient.execute(localVarCall);
    }

    /**
     * Добавление дополнительного пакета токенов (asynchronously)
     * Чтобы докупить дополнительные пакеты токенов для AI агента, отправьте POST-запрос на &#x60;/api/v1/cloud-ai/agents/{id}/add-additional-token-package&#x60;.  Метод относится к устаревшей схеме оплаты пакетами токенов и работает только для агентов с пакетом (&#x60;token_package_id&#x60; в ответе агента). Для агентов с поресурсной оплатой вернётся 400 &#x60;per-resource_agents_do_not_support_additional_token_packages&#x60;. Стоимость (цена дополнительного пакета модели агента × &#x60;count&#x60;) сразу списывается с баланса; при нехватке средств — 400 &#x60;insufficient_balance&#x60;, если подписка агента истекла — 400 &#x60;subscription_expired&#x60;. После покупки заблокированный агент разблокируется.  Для токенов дополнительных пользователей аккаунта нужна роль &#x60;ai:write&#x60;.
     * @param id ID агента (required)
     * @param addTokenPackage  (optional)
     * @param _callback The callback to be executed when the API call finishes
     * @return The request call
     * @throws ApiException If fail to process the API call, e.g. serializing the request body object
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 204 </td><td> Дополнительный пакет токенов успешно добавлен </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Некорректный запрос </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Не авторизован </td><td>  -  </td></tr>
        <tr><td> 403 </td><td> Запрещено </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Не найдено </td><td>  -  </td></tr>
        <tr><td> 429 </td><td> Слишком много запросов </td><td>  -  </td></tr>
        <tr><td> 500 </td><td> Внутренняя ошибка сервера </td><td>  -  </td></tr>
     </table>
     * @deprecated
     */
    @Deprecated
    public okhttp3.Call addAdditionalTokenPackageAsync(Integer id, AddTokenPackage addTokenPackage, final ApiCallback<Void> _callback) throws ApiException {

        okhttp3.Call localVarCall = addAdditionalTokenPackageValidateBeforeCall(id, addTokenPackage, _callback);
        localVarApiClient.executeAsync(localVarCall, _callback);
        return localVarCall;
    }
    /**
     * Build call for changeAgentModel
     * @param id ID агента (required)
     * @param changeAgentModel  (required)
     * @param _callback Callback for upload/download progress
     * @return Call to execute
     * @throws ApiException If fail to serialize the request body object
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> Объект JSON c ключом &#x60;agent&#x60; </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Некорректный запрос </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Не авторизован </td><td>  -  </td></tr>
        <tr><td> 403 </td><td> Запрещено </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Не найдено </td><td>  -  </td></tr>
        <tr><td> 429 </td><td> Слишком много запросов </td><td>  -  </td></tr>
        <tr><td> 500 </td><td> Внутренняя ошибка сервера </td><td>  -  </td></tr>
     </table>
     */
    public okhttp3.Call changeAgentModelCall(Integer id, ChangeAgentModel changeAgentModel, final ApiCallback _callback) throws ApiException {
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

        Object localVarPostBody = changeAgentModel;

        // create path and map variables
        String localVarPath = "/api/v1/cloud-ai/agents/{id}/model"
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
        return localVarApiClient.buildCall(basePath, localVarPath, "PATCH", localVarQueryParams, localVarCollectionQueryParams, localVarPostBody, localVarHeaderParams, localVarCookieParams, localVarFormParams, localVarAuthNames, _callback);
    }

    @SuppressWarnings("rawtypes")
    private okhttp3.Call changeAgentModelValidateBeforeCall(Integer id, ChangeAgentModel changeAgentModel, final ApiCallback _callback) throws ApiException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new ApiException("Missing the required parameter 'id' when calling changeAgentModel(Async)");
        }

        // verify the required parameter 'changeAgentModel' is set
        if (changeAgentModel == null) {
            throw new ApiException("Missing the required parameter 'changeAgentModel' when calling changeAgentModel(Async)");
        }

        return changeAgentModelCall(id, changeAgentModel, _callback);

    }

    /**
     * Смена модели AI агента
     * Чтобы сменить основную модель AI агента, отправьте PATCH-запрос на &#x60;/api/v1/cloud-ai/agents/{id}/model&#x60;.  Параметры модели из &#x60;settings.model&#x60;, которые новая модель не поддерживает, отбрасываются. Смена модели доступна только агентам с поресурсной оплатой (для агента с пакетом токенов — 400 &#x60;model_change_is_allowed_only_for_per-resource_account_and_agent&#x60;). Если модель не найдена или недоступна — 404 &#x60;model_not_found&#x60;.  Для токенов дополнительных пользователей аккаунта нужна роль &#x60;ai:write&#x60;.
     * @param id ID агента (required)
     * @param changeAgentModel  (required)
     * @return CreateAgent201Response
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> Объект JSON c ключом &#x60;agent&#x60; </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Некорректный запрос </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Не авторизован </td><td>  -  </td></tr>
        <tr><td> 403 </td><td> Запрещено </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Не найдено </td><td>  -  </td></tr>
        <tr><td> 429 </td><td> Слишком много запросов </td><td>  -  </td></tr>
        <tr><td> 500 </td><td> Внутренняя ошибка сервера </td><td>  -  </td></tr>
     </table>
     */
    public CreateAgent201Response changeAgentModel(Integer id, ChangeAgentModel changeAgentModel) throws ApiException {
        ApiResponse<CreateAgent201Response> localVarResp = changeAgentModelWithHttpInfo(id, changeAgentModel);
        return localVarResp.getData();
    }

    /**
     * Смена модели AI агента
     * Чтобы сменить основную модель AI агента, отправьте PATCH-запрос на &#x60;/api/v1/cloud-ai/agents/{id}/model&#x60;.  Параметры модели из &#x60;settings.model&#x60;, которые новая модель не поддерживает, отбрасываются. Смена модели доступна только агентам с поресурсной оплатой (для агента с пакетом токенов — 400 &#x60;model_change_is_allowed_only_for_per-resource_account_and_agent&#x60;). Если модель не найдена или недоступна — 404 &#x60;model_not_found&#x60;.  Для токенов дополнительных пользователей аккаунта нужна роль &#x60;ai:write&#x60;.
     * @param id ID агента (required)
     * @param changeAgentModel  (required)
     * @return ApiResponse&lt;CreateAgent201Response&gt;
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> Объект JSON c ключом &#x60;agent&#x60; </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Некорректный запрос </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Не авторизован </td><td>  -  </td></tr>
        <tr><td> 403 </td><td> Запрещено </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Не найдено </td><td>  -  </td></tr>
        <tr><td> 429 </td><td> Слишком много запросов </td><td>  -  </td></tr>
        <tr><td> 500 </td><td> Внутренняя ошибка сервера </td><td>  -  </td></tr>
     </table>
     */
    public ApiResponse<CreateAgent201Response> changeAgentModelWithHttpInfo(Integer id, ChangeAgentModel changeAgentModel) throws ApiException {
        okhttp3.Call localVarCall = changeAgentModelValidateBeforeCall(id, changeAgentModel, null);
        Type localVarReturnType = new TypeToken<CreateAgent201Response>(){}.getType();
        return localVarApiClient.execute(localVarCall, localVarReturnType);
    }

    /**
     * Смена модели AI агента (asynchronously)
     * Чтобы сменить основную модель AI агента, отправьте PATCH-запрос на &#x60;/api/v1/cloud-ai/agents/{id}/model&#x60;.  Параметры модели из &#x60;settings.model&#x60;, которые новая модель не поддерживает, отбрасываются. Смена модели доступна только агентам с поресурсной оплатой (для агента с пакетом токенов — 400 &#x60;model_change_is_allowed_only_for_per-resource_account_and_agent&#x60;). Если модель не найдена или недоступна — 404 &#x60;model_not_found&#x60;.  Для токенов дополнительных пользователей аккаунта нужна роль &#x60;ai:write&#x60;.
     * @param id ID агента (required)
     * @param changeAgentModel  (required)
     * @param _callback The callback to be executed when the API call finishes
     * @return The request call
     * @throws ApiException If fail to process the API call, e.g. serializing the request body object
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> Объект JSON c ключом &#x60;agent&#x60; </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Некорректный запрос </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Не авторизован </td><td>  -  </td></tr>
        <tr><td> 403 </td><td> Запрещено </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Не найдено </td><td>  -  </td></tr>
        <tr><td> 429 </td><td> Слишком много запросов </td><td>  -  </td></tr>
        <tr><td> 500 </td><td> Внутренняя ошибка сервера </td><td>  -  </td></tr>
     </table>
     */
    public okhttp3.Call changeAgentModelAsync(Integer id, ChangeAgentModel changeAgentModel, final ApiCallback<CreateAgent201Response> _callback) throws ApiException {

        okhttp3.Call localVarCall = changeAgentModelValidateBeforeCall(id, changeAgentModel, _callback);
        Type localVarReturnType = new TypeToken<CreateAgent201Response>(){}.getType();
        localVarApiClient.executeAsync(localVarCall, localVarReturnType, _callback);
        return localVarCall;
    }
    /**
     * Build call for createAgent
     * @param createAgent  (required)
     * @param _callback Callback for upload/download progress
     * @return Call to execute
     * @throws ApiException If fail to serialize the request body object
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 201 </td><td> Объект JSON c ключом &#x60;agent&#x60; </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Некорректный запрос </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Не авторизован </td><td>  -  </td></tr>
        <tr><td> 403 </td><td> Запрещено </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Не найдено </td><td>  -  </td></tr>
        <tr><td> 429 </td><td> Слишком много запросов </td><td>  -  </td></tr>
        <tr><td> 500 </td><td> Внутренняя ошибка сервера </td><td>  -  </td></tr>
     </table>
     * @deprecated
     */
    @Deprecated
    public okhttp3.Call createAgentCall(CreateAgent createAgent, final ApiCallback _callback) throws ApiException {
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

        Object localVarPostBody = createAgent;

        // create path and map variables
        String localVarPath = "/api/v1/cloud-ai/agents";

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

    @Deprecated
    @SuppressWarnings("rawtypes")
    private okhttp3.Call createAgentValidateBeforeCall(CreateAgent createAgent, final ApiCallback _callback) throws ApiException {
        // verify the required parameter 'createAgent' is set
        if (createAgent == null) {
            throw new ApiException("Missing the required parameter 'createAgent' when calling createAgent(Async)");
        }

        return createAgentCall(createAgent, _callback);

    }

    /**
     * Создание AI агента
     * Чтобы создать AI агента, отправьте POST-запрос на &#x60;/api/v1/cloud-ai/agents&#x60;, задав необходимые атрибуты.  Метод устарел — используйте &#x60;POST /api/v2/cloud-ai/agents&#x60;. Поле &#x60;token_package_id&#x60; обязательно, но не учитывается: агент всегда создаётся с поресурсной оплатой (оплачиваются фактические запросы к модели).  Для токенов дополнительных пользователей аккаунта нужна роль &#x60;ai:write&#x60;.
     * @param createAgent  (required)
     * @return CreateAgent201Response
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 201 </td><td> Объект JSON c ключом &#x60;agent&#x60; </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Некорректный запрос </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Не авторизован </td><td>  -  </td></tr>
        <tr><td> 403 </td><td> Запрещено </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Не найдено </td><td>  -  </td></tr>
        <tr><td> 429 </td><td> Слишком много запросов </td><td>  -  </td></tr>
        <tr><td> 500 </td><td> Внутренняя ошибка сервера </td><td>  -  </td></tr>
     </table>
     * @deprecated
     */
    @Deprecated
    public CreateAgent201Response createAgent(CreateAgent createAgent) throws ApiException {
        ApiResponse<CreateAgent201Response> localVarResp = createAgentWithHttpInfo(createAgent);
        return localVarResp.getData();
    }

    /**
     * Создание AI агента
     * Чтобы создать AI агента, отправьте POST-запрос на &#x60;/api/v1/cloud-ai/agents&#x60;, задав необходимые атрибуты.  Метод устарел — используйте &#x60;POST /api/v2/cloud-ai/agents&#x60;. Поле &#x60;token_package_id&#x60; обязательно, но не учитывается: агент всегда создаётся с поресурсной оплатой (оплачиваются фактические запросы к модели).  Для токенов дополнительных пользователей аккаунта нужна роль &#x60;ai:write&#x60;.
     * @param createAgent  (required)
     * @return ApiResponse&lt;CreateAgent201Response&gt;
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 201 </td><td> Объект JSON c ключом &#x60;agent&#x60; </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Некорректный запрос </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Не авторизован </td><td>  -  </td></tr>
        <tr><td> 403 </td><td> Запрещено </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Не найдено </td><td>  -  </td></tr>
        <tr><td> 429 </td><td> Слишком много запросов </td><td>  -  </td></tr>
        <tr><td> 500 </td><td> Внутренняя ошибка сервера </td><td>  -  </td></tr>
     </table>
     * @deprecated
     */
    @Deprecated
    public ApiResponse<CreateAgent201Response> createAgentWithHttpInfo(CreateAgent createAgent) throws ApiException {
        okhttp3.Call localVarCall = createAgentValidateBeforeCall(createAgent, null);
        Type localVarReturnType = new TypeToken<CreateAgent201Response>(){}.getType();
        return localVarApiClient.execute(localVarCall, localVarReturnType);
    }

    /**
     * Создание AI агента (asynchronously)
     * Чтобы создать AI агента, отправьте POST-запрос на &#x60;/api/v1/cloud-ai/agents&#x60;, задав необходимые атрибуты.  Метод устарел — используйте &#x60;POST /api/v2/cloud-ai/agents&#x60;. Поле &#x60;token_package_id&#x60; обязательно, но не учитывается: агент всегда создаётся с поресурсной оплатой (оплачиваются фактические запросы к модели).  Для токенов дополнительных пользователей аккаунта нужна роль &#x60;ai:write&#x60;.
     * @param createAgent  (required)
     * @param _callback The callback to be executed when the API call finishes
     * @return The request call
     * @throws ApiException If fail to process the API call, e.g. serializing the request body object
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 201 </td><td> Объект JSON c ключом &#x60;agent&#x60; </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Некорректный запрос </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Не авторизован </td><td>  -  </td></tr>
        <tr><td> 403 </td><td> Запрещено </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Не найдено </td><td>  -  </td></tr>
        <tr><td> 429 </td><td> Слишком много запросов </td><td>  -  </td></tr>
        <tr><td> 500 </td><td> Внутренняя ошибка сервера </td><td>  -  </td></tr>
     </table>
     * @deprecated
     */
    @Deprecated
    public okhttp3.Call createAgentAsync(CreateAgent createAgent, final ApiCallback<CreateAgent201Response> _callback) throws ApiException {

        okhttp3.Call localVarCall = createAgentValidateBeforeCall(createAgent, _callback);
        Type localVarReturnType = new TypeToken<CreateAgent201Response>(){}.getType();
        localVarApiClient.executeAsync(localVarCall, localVarReturnType, _callback);
        return localVarCall;
    }
    /**
     * Build call for createAgentEntityLimit
     * @param id ID агента (required)
     * @param createAgentEntityLimit  (required)
     * @param _callback Callback for upload/download progress
     * @return Call to execute
     * @throws ApiException If fail to serialize the request body object
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 201 </td><td> Объект JSON c ключом &#x60;agent_entity_limit&#x60; </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Некорректный запрос </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Не авторизован </td><td>  -  </td></tr>
        <tr><td> 403 </td><td> Запрещено </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Не найдено </td><td>  -  </td></tr>
        <tr><td> 409 </td><td> Конфликт </td><td>  -  </td></tr>
        <tr><td> 429 </td><td> Слишком много запросов </td><td>  -  </td></tr>
        <tr><td> 500 </td><td> Внутренняя ошибка сервера </td><td>  -  </td></tr>
     </table>
     */
    public okhttp3.Call createAgentEntityLimitCall(Integer id, CreateAgentEntityLimit createAgentEntityLimit, final ApiCallback _callback) throws ApiException {
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

        Object localVarPostBody = createAgentEntityLimit;

        // create path and map variables
        String localVarPath = "/api/v1/cloud-ai/agents/{id}/entity-limits"
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
        return localVarApiClient.buildCall(basePath, localVarPath, "POST", localVarQueryParams, localVarCollectionQueryParams, localVarPostBody, localVarHeaderParams, localVarCookieParams, localVarFormParams, localVarAuthNames, _callback);
    }

    @SuppressWarnings("rawtypes")
    private okhttp3.Call createAgentEntityLimitValidateBeforeCall(Integer id, CreateAgentEntityLimit createAgentEntityLimit, final ApiCallback _callback) throws ApiException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new ApiException("Missing the required parameter 'id' when calling createAgentEntityLimit(Async)");
        }

        // verify the required parameter 'createAgentEntityLimit' is set
        if (createAgentEntityLimit == null) {
            throw new ApiException("Missing the required parameter 'createAgentEntityLimit' when calling createAgentEntityLimit(Async)");
        }

        return createAgentEntityLimitCall(id, createAgentEntityLimit, _callback);

    }

    /**
     * Создание лимита агента
     * Чтобы создать лимит AI агента, отправьте POST-запрос на &#x60;/api/v1/cloud-ai/agents/{id}/entity-limits&#x60;.  Лимиты агента ограничивают расход на его запросы за период (&#x60;hour&#x60;, &#x60;day&#x60;, &#x60;week&#x60;, &#x60;month&#x60;) — в токенах (&#x60;limit&#x60;) или в деньгах (&#x60;money_limit&#x60;, приоритетнее). Лимит действует на все модели агента (&#x60;model_id&#x60; &#x3D; &#x60;null&#x60;) или на одну модель. При достижении лимита запросы к агенту отклоняются до конца периода.  Нужно передать &#x60;limit&#x60; или &#x60;money_limit&#x60; (иначе — 400 &#x60;entity_keys_limit_value_required&#x60;). Если лимит с тем же периодом и моделью уже есть — 409 &#x60;entity_keys_limit_already_exists&#x60; (измените его или используйте синхронизацию).  Для токенов дополнительных пользователей аккаунта нужна роль &#x60;ai:write&#x60;.
     * @param id ID агента (required)
     * @param createAgentEntityLimit  (required)
     * @return CreateAgentEntityLimit201Response
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 201 </td><td> Объект JSON c ключом &#x60;agent_entity_limit&#x60; </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Некорректный запрос </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Не авторизован </td><td>  -  </td></tr>
        <tr><td> 403 </td><td> Запрещено </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Не найдено </td><td>  -  </td></tr>
        <tr><td> 409 </td><td> Конфликт </td><td>  -  </td></tr>
        <tr><td> 429 </td><td> Слишком много запросов </td><td>  -  </td></tr>
        <tr><td> 500 </td><td> Внутренняя ошибка сервера </td><td>  -  </td></tr>
     </table>
     */
    public CreateAgentEntityLimit201Response createAgentEntityLimit(Integer id, CreateAgentEntityLimit createAgentEntityLimit) throws ApiException {
        ApiResponse<CreateAgentEntityLimit201Response> localVarResp = createAgentEntityLimitWithHttpInfo(id, createAgentEntityLimit);
        return localVarResp.getData();
    }

    /**
     * Создание лимита агента
     * Чтобы создать лимит AI агента, отправьте POST-запрос на &#x60;/api/v1/cloud-ai/agents/{id}/entity-limits&#x60;.  Лимиты агента ограничивают расход на его запросы за период (&#x60;hour&#x60;, &#x60;day&#x60;, &#x60;week&#x60;, &#x60;month&#x60;) — в токенах (&#x60;limit&#x60;) или в деньгах (&#x60;money_limit&#x60;, приоритетнее). Лимит действует на все модели агента (&#x60;model_id&#x60; &#x3D; &#x60;null&#x60;) или на одну модель. При достижении лимита запросы к агенту отклоняются до конца периода.  Нужно передать &#x60;limit&#x60; или &#x60;money_limit&#x60; (иначе — 400 &#x60;entity_keys_limit_value_required&#x60;). Если лимит с тем же периодом и моделью уже есть — 409 &#x60;entity_keys_limit_already_exists&#x60; (измените его или используйте синхронизацию).  Для токенов дополнительных пользователей аккаунта нужна роль &#x60;ai:write&#x60;.
     * @param id ID агента (required)
     * @param createAgentEntityLimit  (required)
     * @return ApiResponse&lt;CreateAgentEntityLimit201Response&gt;
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 201 </td><td> Объект JSON c ключом &#x60;agent_entity_limit&#x60; </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Некорректный запрос </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Не авторизован </td><td>  -  </td></tr>
        <tr><td> 403 </td><td> Запрещено </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Не найдено </td><td>  -  </td></tr>
        <tr><td> 409 </td><td> Конфликт </td><td>  -  </td></tr>
        <tr><td> 429 </td><td> Слишком много запросов </td><td>  -  </td></tr>
        <tr><td> 500 </td><td> Внутренняя ошибка сервера </td><td>  -  </td></tr>
     </table>
     */
    public ApiResponse<CreateAgentEntityLimit201Response> createAgentEntityLimitWithHttpInfo(Integer id, CreateAgentEntityLimit createAgentEntityLimit) throws ApiException {
        okhttp3.Call localVarCall = createAgentEntityLimitValidateBeforeCall(id, createAgentEntityLimit, null);
        Type localVarReturnType = new TypeToken<CreateAgentEntityLimit201Response>(){}.getType();
        return localVarApiClient.execute(localVarCall, localVarReturnType);
    }

    /**
     * Создание лимита агента (asynchronously)
     * Чтобы создать лимит AI агента, отправьте POST-запрос на &#x60;/api/v1/cloud-ai/agents/{id}/entity-limits&#x60;.  Лимиты агента ограничивают расход на его запросы за период (&#x60;hour&#x60;, &#x60;day&#x60;, &#x60;week&#x60;, &#x60;month&#x60;) — в токенах (&#x60;limit&#x60;) или в деньгах (&#x60;money_limit&#x60;, приоритетнее). Лимит действует на все модели агента (&#x60;model_id&#x60; &#x3D; &#x60;null&#x60;) или на одну модель. При достижении лимита запросы к агенту отклоняются до конца периода.  Нужно передать &#x60;limit&#x60; или &#x60;money_limit&#x60; (иначе — 400 &#x60;entity_keys_limit_value_required&#x60;). Если лимит с тем же периодом и моделью уже есть — 409 &#x60;entity_keys_limit_already_exists&#x60; (измените его или используйте синхронизацию).  Для токенов дополнительных пользователей аккаунта нужна роль &#x60;ai:write&#x60;.
     * @param id ID агента (required)
     * @param createAgentEntityLimit  (required)
     * @param _callback The callback to be executed when the API call finishes
     * @return The request call
     * @throws ApiException If fail to process the API call, e.g. serializing the request body object
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 201 </td><td> Объект JSON c ключом &#x60;agent_entity_limit&#x60; </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Некорректный запрос </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Не авторизован </td><td>  -  </td></tr>
        <tr><td> 403 </td><td> Запрещено </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Не найдено </td><td>  -  </td></tr>
        <tr><td> 409 </td><td> Конфликт </td><td>  -  </td></tr>
        <tr><td> 429 </td><td> Слишком много запросов </td><td>  -  </td></tr>
        <tr><td> 500 </td><td> Внутренняя ошибка сервера </td><td>  -  </td></tr>
     </table>
     */
    public okhttp3.Call createAgentEntityLimitAsync(Integer id, CreateAgentEntityLimit createAgentEntityLimit, final ApiCallback<CreateAgentEntityLimit201Response> _callback) throws ApiException {

        okhttp3.Call localVarCall = createAgentEntityLimitValidateBeforeCall(id, createAgentEntityLimit, _callback);
        Type localVarReturnType = new TypeToken<CreateAgentEntityLimit201Response>(){}.getType();
        localVarApiClient.executeAsync(localVarCall, localVarReturnType, _callback);
        return localVarCall;
    }
    /**
     * Build call for createAgentV2
     * @param createAgentV2  (required)
     * @param _callback Callback for upload/download progress
     * @return Call to execute
     * @throws ApiException If fail to serialize the request body object
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 201 </td><td> Объект JSON c ключом &#x60;agent&#x60; </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Некорректный запрос </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Не авторизован </td><td>  -  </td></tr>
        <tr><td> 403 </td><td> Запрещено </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Не найдено </td><td>  -  </td></tr>
        <tr><td> 429 </td><td> Слишком много запросов </td><td>  -  </td></tr>
        <tr><td> 500 </td><td> Внутренняя ошибка сервера </td><td>  -  </td></tr>
     </table>
     */
    public okhttp3.Call createAgentV2Call(CreateAgentV2 createAgentV2, final ApiCallback _callback) throws ApiException {
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

        Object localVarPostBody = createAgentV2;

        // create path and map variables
        String localVarPath = "/api/v2/cloud-ai/agents";

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
    private okhttp3.Call createAgentV2ValidateBeforeCall(CreateAgentV2 createAgentV2, final ApiCallback _callback) throws ApiException {
        // verify the required parameter 'createAgentV2' is set
        if (createAgentV2 == null) {
            throw new ApiException("Missing the required parameter 'createAgentV2' when calling createAgentV2(Async)");
        }

        return createAgentV2Call(createAgentV2, _callback);

    }

    /**
     * Создание AI агента
     * Чтобы создать AI агента, отправьте POST-запрос на &#x60;/api/v2/cloud-ai/agents&#x60;, задав необходимые атрибуты.  Агент создаётся с поресурсной оплатой: оплачиваются фактические запросы к модели. Если не переданы настройки виджета, подставляются значения по умолчанию. Дневной лимит расходов можно задать сразу полем &#x60;money_limit&#x60;, остальные лимиты — через лимиты агента (&#x60;/api/v1/cloud-ai/agents/{id}/entity-limits&#x60;).  Если модель не найдена или недоступна, вернётся 404 &#x60;model_not_found&#x60;; если модель не поддерживается — 400 &#x60;model_not_supported&#x60;; недопустимые параметры модели в &#x60;settings.model&#x60; — 400.  Вызывать созданного агента из своих приложений можно через OpenAI-совместимое API по его &#x60;access_id&#x60; — см. [документацию](https://agent.timeweb.cloud/docs).  Для токенов дополнительных пользователей аккаунта нужна роль &#x60;ai:write&#x60;.
     * @param createAgentV2  (required)
     * @return CreateAgent201Response
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 201 </td><td> Объект JSON c ключом &#x60;agent&#x60; </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Некорректный запрос </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Не авторизован </td><td>  -  </td></tr>
        <tr><td> 403 </td><td> Запрещено </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Не найдено </td><td>  -  </td></tr>
        <tr><td> 429 </td><td> Слишком много запросов </td><td>  -  </td></tr>
        <tr><td> 500 </td><td> Внутренняя ошибка сервера </td><td>  -  </td></tr>
     </table>
     */
    public CreateAgent201Response createAgentV2(CreateAgentV2 createAgentV2) throws ApiException {
        ApiResponse<CreateAgent201Response> localVarResp = createAgentV2WithHttpInfo(createAgentV2);
        return localVarResp.getData();
    }

    /**
     * Создание AI агента
     * Чтобы создать AI агента, отправьте POST-запрос на &#x60;/api/v2/cloud-ai/agents&#x60;, задав необходимые атрибуты.  Агент создаётся с поресурсной оплатой: оплачиваются фактические запросы к модели. Если не переданы настройки виджета, подставляются значения по умолчанию. Дневной лимит расходов можно задать сразу полем &#x60;money_limit&#x60;, остальные лимиты — через лимиты агента (&#x60;/api/v1/cloud-ai/agents/{id}/entity-limits&#x60;).  Если модель не найдена или недоступна, вернётся 404 &#x60;model_not_found&#x60;; если модель не поддерживается — 400 &#x60;model_not_supported&#x60;; недопустимые параметры модели в &#x60;settings.model&#x60; — 400.  Вызывать созданного агента из своих приложений можно через OpenAI-совместимое API по его &#x60;access_id&#x60; — см. [документацию](https://agent.timeweb.cloud/docs).  Для токенов дополнительных пользователей аккаунта нужна роль &#x60;ai:write&#x60;.
     * @param createAgentV2  (required)
     * @return ApiResponse&lt;CreateAgent201Response&gt;
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 201 </td><td> Объект JSON c ключом &#x60;agent&#x60; </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Некорректный запрос </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Не авторизован </td><td>  -  </td></tr>
        <tr><td> 403 </td><td> Запрещено </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Не найдено </td><td>  -  </td></tr>
        <tr><td> 429 </td><td> Слишком много запросов </td><td>  -  </td></tr>
        <tr><td> 500 </td><td> Внутренняя ошибка сервера </td><td>  -  </td></tr>
     </table>
     */
    public ApiResponse<CreateAgent201Response> createAgentV2WithHttpInfo(CreateAgentV2 createAgentV2) throws ApiException {
        okhttp3.Call localVarCall = createAgentV2ValidateBeforeCall(createAgentV2, null);
        Type localVarReturnType = new TypeToken<CreateAgent201Response>(){}.getType();
        return localVarApiClient.execute(localVarCall, localVarReturnType);
    }

    /**
     * Создание AI агента (asynchronously)
     * Чтобы создать AI агента, отправьте POST-запрос на &#x60;/api/v2/cloud-ai/agents&#x60;, задав необходимые атрибуты.  Агент создаётся с поресурсной оплатой: оплачиваются фактические запросы к модели. Если не переданы настройки виджета, подставляются значения по умолчанию. Дневной лимит расходов можно задать сразу полем &#x60;money_limit&#x60;, остальные лимиты — через лимиты агента (&#x60;/api/v1/cloud-ai/agents/{id}/entity-limits&#x60;).  Если модель не найдена или недоступна, вернётся 404 &#x60;model_not_found&#x60;; если модель не поддерживается — 400 &#x60;model_not_supported&#x60;; недопустимые параметры модели в &#x60;settings.model&#x60; — 400.  Вызывать созданного агента из своих приложений можно через OpenAI-совместимое API по его &#x60;access_id&#x60; — см. [документацию](https://agent.timeweb.cloud/docs).  Для токенов дополнительных пользователей аккаунта нужна роль &#x60;ai:write&#x60;.
     * @param createAgentV2  (required)
     * @param _callback The callback to be executed when the API call finishes
     * @return The request call
     * @throws ApiException If fail to process the API call, e.g. serializing the request body object
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 201 </td><td> Объект JSON c ключом &#x60;agent&#x60; </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Некорректный запрос </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Не авторизован </td><td>  -  </td></tr>
        <tr><td> 403 </td><td> Запрещено </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Не найдено </td><td>  -  </td></tr>
        <tr><td> 429 </td><td> Слишком много запросов </td><td>  -  </td></tr>
        <tr><td> 500 </td><td> Внутренняя ошибка сервера </td><td>  -  </td></tr>
     </table>
     */
    public okhttp3.Call createAgentV2Async(CreateAgentV2 createAgentV2, final ApiCallback<CreateAgent201Response> _callback) throws ApiException {

        okhttp3.Call localVarCall = createAgentV2ValidateBeforeCall(createAgentV2, _callback);
        Type localVarReturnType = new TypeToken<CreateAgent201Response>(){}.getType();
        localVarApiClient.executeAsync(localVarCall, localVarReturnType, _callback);
        return localVarCall;
    }
    /**
     * Build call for deleteAgent
     * @param id ID агента (required)
     * @param deleteAgent  (optional)
     * @param _callback Callback for upload/download progress
     * @return Call to execute
     * @throws ApiException If fail to serialize the request body object
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 204 </td><td> AI агент успешно удален </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Некорректный запрос </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Не авторизован </td><td>  -  </td></tr>
        <tr><td> 403 </td><td> Запрещено </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Не найдено </td><td>  -  </td></tr>
        <tr><td> 429 </td><td> Слишком много запросов </td><td>  -  </td></tr>
        <tr><td> 500 </td><td> Внутренняя ошибка сервера </td><td>  -  </td></tr>
     </table>
     */
    public okhttp3.Call deleteAgentCall(Integer id, DeleteAgent deleteAgent, final ApiCallback _callback) throws ApiException {
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

        Object localVarPostBody = deleteAgent;

        // create path and map variables
        String localVarPath = "/api/v1/cloud-ai/agents/{id}"
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
        return localVarApiClient.buildCall(basePath, localVarPath, "DELETE", localVarQueryParams, localVarCollectionQueryParams, localVarPostBody, localVarHeaderParams, localVarCookieParams, localVarFormParams, localVarAuthNames, _callback);
    }

    @SuppressWarnings("rawtypes")
    private okhttp3.Call deleteAgentValidateBeforeCall(Integer id, DeleteAgent deleteAgent, final ApiCallback _callback) throws ApiException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new ApiException("Missing the required parameter 'id' when calling deleteAgent(Async)");
        }

        return deleteAgentCall(id, deleteAgent, _callback);

    }

    /**
     * Удаление AI агента
     * Чтобы удалить AI агента, отправьте DELETE-запрос на &#x60;/api/v1/cloud-ai/agents/{id}&#x60;.  Вместе с агентом отключаются его базы знаний, навыки и интеграции (Telegram, MAX, Jivo, Crisp). В теле запроса можно необязательно указать причину удаления.  Для токенов дополнительных пользователей аккаунта нужна роль &#x60;ai:write&#x60;.
     * @param id ID агента (required)
     * @param deleteAgent  (optional)
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 204 </td><td> AI агент успешно удален </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Некорректный запрос </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Не авторизован </td><td>  -  </td></tr>
        <tr><td> 403 </td><td> Запрещено </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Не найдено </td><td>  -  </td></tr>
        <tr><td> 429 </td><td> Слишком много запросов </td><td>  -  </td></tr>
        <tr><td> 500 </td><td> Внутренняя ошибка сервера </td><td>  -  </td></tr>
     </table>
     */
    public void deleteAgent(Integer id, DeleteAgent deleteAgent) throws ApiException {
        deleteAgentWithHttpInfo(id, deleteAgent);
    }

    /**
     * Удаление AI агента
     * Чтобы удалить AI агента, отправьте DELETE-запрос на &#x60;/api/v1/cloud-ai/agents/{id}&#x60;.  Вместе с агентом отключаются его базы знаний, навыки и интеграции (Telegram, MAX, Jivo, Crisp). В теле запроса можно необязательно указать причину удаления.  Для токенов дополнительных пользователей аккаунта нужна роль &#x60;ai:write&#x60;.
     * @param id ID агента (required)
     * @param deleteAgent  (optional)
     * @return ApiResponse&lt;Void&gt;
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 204 </td><td> AI агент успешно удален </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Некорректный запрос </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Не авторизован </td><td>  -  </td></tr>
        <tr><td> 403 </td><td> Запрещено </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Не найдено </td><td>  -  </td></tr>
        <tr><td> 429 </td><td> Слишком много запросов </td><td>  -  </td></tr>
        <tr><td> 500 </td><td> Внутренняя ошибка сервера </td><td>  -  </td></tr>
     </table>
     */
    public ApiResponse<Void> deleteAgentWithHttpInfo(Integer id, DeleteAgent deleteAgent) throws ApiException {
        okhttp3.Call localVarCall = deleteAgentValidateBeforeCall(id, deleteAgent, null);
        return localVarApiClient.execute(localVarCall);
    }

    /**
     * Удаление AI агента (asynchronously)
     * Чтобы удалить AI агента, отправьте DELETE-запрос на &#x60;/api/v1/cloud-ai/agents/{id}&#x60;.  Вместе с агентом отключаются его базы знаний, навыки и интеграции (Telegram, MAX, Jivo, Crisp). В теле запроса можно необязательно указать причину удаления.  Для токенов дополнительных пользователей аккаунта нужна роль &#x60;ai:write&#x60;.
     * @param id ID агента (required)
     * @param deleteAgent  (optional)
     * @param _callback The callback to be executed when the API call finishes
     * @return The request call
     * @throws ApiException If fail to process the API call, e.g. serializing the request body object
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 204 </td><td> AI агент успешно удален </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Некорректный запрос </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Не авторизован </td><td>  -  </td></tr>
        <tr><td> 403 </td><td> Запрещено </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Не найдено </td><td>  -  </td></tr>
        <tr><td> 429 </td><td> Слишком много запросов </td><td>  -  </td></tr>
        <tr><td> 500 </td><td> Внутренняя ошибка сервера </td><td>  -  </td></tr>
     </table>
     */
    public okhttp3.Call deleteAgentAsync(Integer id, DeleteAgent deleteAgent, final ApiCallback<Void> _callback) throws ApiException {

        okhttp3.Call localVarCall = deleteAgentValidateBeforeCall(id, deleteAgent, _callback);
        localVarApiClient.executeAsync(localVarCall, _callback);
        return localVarCall;
    }
    /**
     * Build call for deleteAgentEntityLimit
     * @param id ID агента (required)
     * @param limitId ID лимита агента (required)
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
    public okhttp3.Call deleteAgentEntityLimitCall(Integer id, UUID limitId, final ApiCallback _callback) throws ApiException {
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
        String localVarPath = "/api/v1/cloud-ai/agents/{id}/entity-limits/{limit_id}"
            .replace("{" + "id" + "}", localVarApiClient.escapeString(id.toString()))
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
    private okhttp3.Call deleteAgentEntityLimitValidateBeforeCall(Integer id, UUID limitId, final ApiCallback _callback) throws ApiException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new ApiException("Missing the required parameter 'id' when calling deleteAgentEntityLimit(Async)");
        }

        // verify the required parameter 'limitId' is set
        if (limitId == null) {
            throw new ApiException("Missing the required parameter 'limitId' when calling deleteAgentEntityLimit(Async)");
        }

        return deleteAgentEntityLimitCall(id, limitId, _callback);

    }

    /**
     * Удаление лимита агента
     * Чтобы удалить лимит AI агента, отправьте DELETE-запрос на &#x60;/api/v1/cloud-ai/agents/{id}/entity-limits/{limit_id}&#x60;.  Для токенов дополнительных пользователей аккаунта нужна роль &#x60;ai:write&#x60;.
     * @param id ID агента (required)
     * @param limitId ID лимита агента (required)
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
    public void deleteAgentEntityLimit(Integer id, UUID limitId) throws ApiException {
        deleteAgentEntityLimitWithHttpInfo(id, limitId);
    }

    /**
     * Удаление лимита агента
     * Чтобы удалить лимит AI агента, отправьте DELETE-запрос на &#x60;/api/v1/cloud-ai/agents/{id}/entity-limits/{limit_id}&#x60;.  Для токенов дополнительных пользователей аккаунта нужна роль &#x60;ai:write&#x60;.
     * @param id ID агента (required)
     * @param limitId ID лимита агента (required)
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
    public ApiResponse<Void> deleteAgentEntityLimitWithHttpInfo(Integer id, UUID limitId) throws ApiException {
        okhttp3.Call localVarCall = deleteAgentEntityLimitValidateBeforeCall(id, limitId, null);
        return localVarApiClient.execute(localVarCall);
    }

    /**
     * Удаление лимита агента (asynchronously)
     * Чтобы удалить лимит AI агента, отправьте DELETE-запрос на &#x60;/api/v1/cloud-ai/agents/{id}/entity-limits/{limit_id}&#x60;.  Для токенов дополнительных пользователей аккаунта нужна роль &#x60;ai:write&#x60;.
     * @param id ID агента (required)
     * @param limitId ID лимита агента (required)
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
    public okhttp3.Call deleteAgentEntityLimitAsync(Integer id, UUID limitId, final ApiCallback<Void> _callback) throws ApiException {

        okhttp3.Call localVarCall = deleteAgentEntityLimitValidateBeforeCall(id, limitId, _callback);
        localVarApiClient.executeAsync(localVarCall, _callback);
        return localVarCall;
    }
    /**
     * Build call for exportAgentChatsCsv
     * @param id ID агента (required)
     * @param offset Смещение относительно начала списка (optional, default to 0)
     * @param limit Количество сообщений в ответе (optional, default to 100)
     * @param order Сортировка по дате сообщения (optional, default to desc)
     * @param from Начало периода (дата и время в формате ISO 8601) (optional)
     * @param to Конец периода (дата и время в формате ISO 8601) (optional)
     * @param locale Язык заголовков и значений CSV (optional, default to ru)
     * @param _callback Callback for upload/download progress
     * @return Call to execute
     * @throws ApiException If fail to serialize the request body object
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> Объект JSON c ключом &#x60;chats_csv&#x60; </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Некорректный запрос </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Не авторизован </td><td>  -  </td></tr>
        <tr><td> 403 </td><td> Запрещено </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Не найдено </td><td>  -  </td></tr>
        <tr><td> 429 </td><td> Слишком много запросов </td><td>  -  </td></tr>
        <tr><td> 500 </td><td> Внутренняя ошибка сервера </td><td>  -  </td></tr>
     </table>
     */
    public okhttp3.Call exportAgentChatsCsvCall(Integer id, Integer offset, Integer limit, String order, OffsetDateTime from, OffsetDateTime to, String locale, final ApiCallback _callback) throws ApiException {
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
        String localVarPath = "/api/v1/cloud-ai/agents/{id}/chats/docs"
            .replace("{" + "id" + "}", localVarApiClient.escapeString(id.toString()));

        List<Pair> localVarQueryParams = new ArrayList<Pair>();
        List<Pair> localVarCollectionQueryParams = new ArrayList<Pair>();
        Map<String, String> localVarHeaderParams = new HashMap<String, String>();
        Map<String, String> localVarCookieParams = new HashMap<String, String>();
        Map<String, Object> localVarFormParams = new HashMap<String, Object>();

        if (offset != null) {
            localVarQueryParams.addAll(localVarApiClient.parameterToPair("offset", offset));
        }

        if (limit != null) {
            localVarQueryParams.addAll(localVarApiClient.parameterToPair("limit", limit));
        }

        if (order != null) {
            localVarQueryParams.addAll(localVarApiClient.parameterToPair("order", order));
        }

        if (from != null) {
            localVarQueryParams.addAll(localVarApiClient.parameterToPair("from", from));
        }

        if (to != null) {
            localVarQueryParams.addAll(localVarApiClient.parameterToPair("to", to));
        }

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
    private okhttp3.Call exportAgentChatsCsvValidateBeforeCall(Integer id, Integer offset, Integer limit, String order, OffsetDateTime from, OffsetDateTime to, String locale, final ApiCallback _callback) throws ApiException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new ApiException("Missing the required parameter 'id' when calling exportAgentChatsCsv(Async)");
        }

        return exportAgentChatsCsvCall(id, offset, limit, order, from, to, locale, _callback);

    }

    /**
     * Выгрузка чатов агента в CSV
     * Чтобы выгрузить чаты AI агента в формате CSV, отправьте GET-запрос на &#x60;/api/v1/cloud-ai/agents/{id}/chats/docs&#x60;.  CSV возвращается строкой в поле &#x60;chats_csv.csv&#x60; тела JSON. &#x60;limit&#x60; и &#x60;offset&#x60; применяются к сообщениям, а не к чатам. Если передан &#x60;from&#x60;, выгружаются сообщения от &#x60;from&#x60; до &#x60;to&#x60; (по умолчанию — до текущего момента). Чаты внешнего чата не выгружаются.  Для токенов дополнительных пользователей аккаунта нужна роль &#x60;ai:read&#x60;.
     * @param id ID агента (required)
     * @param offset Смещение относительно начала списка (optional, default to 0)
     * @param limit Количество сообщений в ответе (optional, default to 100)
     * @param order Сортировка по дате сообщения (optional, default to desc)
     * @param from Начало периода (дата и время в формате ISO 8601) (optional)
     * @param to Конец периода (дата и время в формате ISO 8601) (optional)
     * @param locale Язык заголовков и значений CSV (optional, default to ru)
     * @return ExportAgentChatsCsv200Response
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> Объект JSON c ключом &#x60;chats_csv&#x60; </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Некорректный запрос </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Не авторизован </td><td>  -  </td></tr>
        <tr><td> 403 </td><td> Запрещено </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Не найдено </td><td>  -  </td></tr>
        <tr><td> 429 </td><td> Слишком много запросов </td><td>  -  </td></tr>
        <tr><td> 500 </td><td> Внутренняя ошибка сервера </td><td>  -  </td></tr>
     </table>
     */
    public ExportAgentChatsCsv200Response exportAgentChatsCsv(Integer id, Integer offset, Integer limit, String order, OffsetDateTime from, OffsetDateTime to, String locale) throws ApiException {
        ApiResponse<ExportAgentChatsCsv200Response> localVarResp = exportAgentChatsCsvWithHttpInfo(id, offset, limit, order, from, to, locale);
        return localVarResp.getData();
    }

    /**
     * Выгрузка чатов агента в CSV
     * Чтобы выгрузить чаты AI агента в формате CSV, отправьте GET-запрос на &#x60;/api/v1/cloud-ai/agents/{id}/chats/docs&#x60;.  CSV возвращается строкой в поле &#x60;chats_csv.csv&#x60; тела JSON. &#x60;limit&#x60; и &#x60;offset&#x60; применяются к сообщениям, а не к чатам. Если передан &#x60;from&#x60;, выгружаются сообщения от &#x60;from&#x60; до &#x60;to&#x60; (по умолчанию — до текущего момента). Чаты внешнего чата не выгружаются.  Для токенов дополнительных пользователей аккаунта нужна роль &#x60;ai:read&#x60;.
     * @param id ID агента (required)
     * @param offset Смещение относительно начала списка (optional, default to 0)
     * @param limit Количество сообщений в ответе (optional, default to 100)
     * @param order Сортировка по дате сообщения (optional, default to desc)
     * @param from Начало периода (дата и время в формате ISO 8601) (optional)
     * @param to Конец периода (дата и время в формате ISO 8601) (optional)
     * @param locale Язык заголовков и значений CSV (optional, default to ru)
     * @return ApiResponse&lt;ExportAgentChatsCsv200Response&gt;
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> Объект JSON c ключом &#x60;chats_csv&#x60; </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Некорректный запрос </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Не авторизован </td><td>  -  </td></tr>
        <tr><td> 403 </td><td> Запрещено </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Не найдено </td><td>  -  </td></tr>
        <tr><td> 429 </td><td> Слишком много запросов </td><td>  -  </td></tr>
        <tr><td> 500 </td><td> Внутренняя ошибка сервера </td><td>  -  </td></tr>
     </table>
     */
    public ApiResponse<ExportAgentChatsCsv200Response> exportAgentChatsCsvWithHttpInfo(Integer id, Integer offset, Integer limit, String order, OffsetDateTime from, OffsetDateTime to, String locale) throws ApiException {
        okhttp3.Call localVarCall = exportAgentChatsCsvValidateBeforeCall(id, offset, limit, order, from, to, locale, null);
        Type localVarReturnType = new TypeToken<ExportAgentChatsCsv200Response>(){}.getType();
        return localVarApiClient.execute(localVarCall, localVarReturnType);
    }

    /**
     * Выгрузка чатов агента в CSV (asynchronously)
     * Чтобы выгрузить чаты AI агента в формате CSV, отправьте GET-запрос на &#x60;/api/v1/cloud-ai/agents/{id}/chats/docs&#x60;.  CSV возвращается строкой в поле &#x60;chats_csv.csv&#x60; тела JSON. &#x60;limit&#x60; и &#x60;offset&#x60; применяются к сообщениям, а не к чатам. Если передан &#x60;from&#x60;, выгружаются сообщения от &#x60;from&#x60; до &#x60;to&#x60; (по умолчанию — до текущего момента). Чаты внешнего чата не выгружаются.  Для токенов дополнительных пользователей аккаунта нужна роль &#x60;ai:read&#x60;.
     * @param id ID агента (required)
     * @param offset Смещение относительно начала списка (optional, default to 0)
     * @param limit Количество сообщений в ответе (optional, default to 100)
     * @param order Сортировка по дате сообщения (optional, default to desc)
     * @param from Начало периода (дата и время в формате ISO 8601) (optional)
     * @param to Конец периода (дата и время в формате ISO 8601) (optional)
     * @param locale Язык заголовков и значений CSV (optional, default to ru)
     * @param _callback The callback to be executed when the API call finishes
     * @return The request call
     * @throws ApiException If fail to process the API call, e.g. serializing the request body object
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> Объект JSON c ключом &#x60;chats_csv&#x60; </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Некорректный запрос </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Не авторизован </td><td>  -  </td></tr>
        <tr><td> 403 </td><td> Запрещено </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Не найдено </td><td>  -  </td></tr>
        <tr><td> 429 </td><td> Слишком много запросов </td><td>  -  </td></tr>
        <tr><td> 500 </td><td> Внутренняя ошибка сервера </td><td>  -  </td></tr>
     </table>
     */
    public okhttp3.Call exportAgentChatsCsvAsync(Integer id, Integer offset, Integer limit, String order, OffsetDateTime from, OffsetDateTime to, String locale, final ApiCallback<ExportAgentChatsCsv200Response> _callback) throws ApiException {

        okhttp3.Call localVarCall = exportAgentChatsCsvValidateBeforeCall(id, offset, limit, order, from, to, locale, _callback);
        Type localVarReturnType = new TypeToken<ExportAgentChatsCsv200Response>(){}.getType();
        localVarApiClient.executeAsync(localVarCall, localVarReturnType, _callback);
        return localVarCall;
    }
    /**
     * Build call for getAgent
     * @param id ID агента (required)
     * @param _callback Callback for upload/download progress
     * @return Call to execute
     * @throws ApiException If fail to serialize the request body object
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> Объект JSON c ключом &#x60;agent&#x60; </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Некорректный запрос </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Не авторизован </td><td>  -  </td></tr>
        <tr><td> 403 </td><td> Запрещено </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Не найдено </td><td>  -  </td></tr>
        <tr><td> 429 </td><td> Слишком много запросов </td><td>  -  </td></tr>
        <tr><td> 500 </td><td> Внутренняя ошибка сервера </td><td>  -  </td></tr>
     </table>
     */
    public okhttp3.Call getAgentCall(Integer id, final ApiCallback _callback) throws ApiException {
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
        String localVarPath = "/api/v1/cloud-ai/agents/{id}"
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
    private okhttp3.Call getAgentValidateBeforeCall(Integer id, final ApiCallback _callback) throws ApiException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new ApiException("Missing the required parameter 'id' when calling getAgent(Async)");
        }

        return getAgentCall(id, _callback);

    }

    /**
     * Получение AI агента
     * Чтобы получить информацию об AI агенте, отправьте GET-запрос на &#x60;/api/v1/cloud-ai/agents/{id}&#x60;.  Для токенов дополнительных пользователей аккаунта нужна роль &#x60;ai:read&#x60;.
     * @param id ID агента (required)
     * @return CreateAgent201Response
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> Объект JSON c ключом &#x60;agent&#x60; </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Некорректный запрос </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Не авторизован </td><td>  -  </td></tr>
        <tr><td> 403 </td><td> Запрещено </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Не найдено </td><td>  -  </td></tr>
        <tr><td> 429 </td><td> Слишком много запросов </td><td>  -  </td></tr>
        <tr><td> 500 </td><td> Внутренняя ошибка сервера </td><td>  -  </td></tr>
     </table>
     */
    public CreateAgent201Response getAgent(Integer id) throws ApiException {
        ApiResponse<CreateAgent201Response> localVarResp = getAgentWithHttpInfo(id);
        return localVarResp.getData();
    }

    /**
     * Получение AI агента
     * Чтобы получить информацию об AI агенте, отправьте GET-запрос на &#x60;/api/v1/cloud-ai/agents/{id}&#x60;.  Для токенов дополнительных пользователей аккаунта нужна роль &#x60;ai:read&#x60;.
     * @param id ID агента (required)
     * @return ApiResponse&lt;CreateAgent201Response&gt;
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> Объект JSON c ключом &#x60;agent&#x60; </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Некорректный запрос </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Не авторизован </td><td>  -  </td></tr>
        <tr><td> 403 </td><td> Запрещено </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Не найдено </td><td>  -  </td></tr>
        <tr><td> 429 </td><td> Слишком много запросов </td><td>  -  </td></tr>
        <tr><td> 500 </td><td> Внутренняя ошибка сервера </td><td>  -  </td></tr>
     </table>
     */
    public ApiResponse<CreateAgent201Response> getAgentWithHttpInfo(Integer id) throws ApiException {
        okhttp3.Call localVarCall = getAgentValidateBeforeCall(id, null);
        Type localVarReturnType = new TypeToken<CreateAgent201Response>(){}.getType();
        return localVarApiClient.execute(localVarCall, localVarReturnType);
    }

    /**
     * Получение AI агента (asynchronously)
     * Чтобы получить информацию об AI агенте, отправьте GET-запрос на &#x60;/api/v1/cloud-ai/agents/{id}&#x60;.  Для токенов дополнительных пользователей аккаунта нужна роль &#x60;ai:read&#x60;.
     * @param id ID агента (required)
     * @param _callback The callback to be executed when the API call finishes
     * @return The request call
     * @throws ApiException If fail to process the API call, e.g. serializing the request body object
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> Объект JSON c ключом &#x60;agent&#x60; </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Некорректный запрос </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Не авторизован </td><td>  -  </td></tr>
        <tr><td> 403 </td><td> Запрещено </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Не найдено </td><td>  -  </td></tr>
        <tr><td> 429 </td><td> Слишком много запросов </td><td>  -  </td></tr>
        <tr><td> 500 </td><td> Внутренняя ошибка сервера </td><td>  -  </td></tr>
     </table>
     */
    public okhttp3.Call getAgentAsync(Integer id, final ApiCallback<CreateAgent201Response> _callback) throws ApiException {

        okhttp3.Call localVarCall = getAgentValidateBeforeCall(id, _callback);
        Type localVarReturnType = new TypeToken<CreateAgent201Response>(){}.getType();
        localVarApiClient.executeAsync(localVarCall, localVarReturnType, _callback);
        return localVarCall;
    }
    /**
     * Build call for getAgentChatList
     * @param id ID агента (required)
     * @param offset Смещение относительно начала списка (optional, default to 0)
     * @param limit Количество чатов в ответе (optional, default to 100)
     * @param order Сортировка по дате первого сообщения (optional, default to asc)
     * @param from Начало периода (дата и время в формате ISO 8601) (optional)
     * @param to Конец периода (дата и время в формате ISO 8601) (optional)
     * @param reactionType Фильтр по оценкам: &#x60;likes&#x60; — есть положительная, &#x60;dislikes&#x60; — есть отрицательная, &#x60;with&#x60; — есть любая, &#x60;none&#x60; — без оценок (optional)
     * @param responseTimeRanges Фильтр по среднему времени ответа в секундах: диапазоны &#x60;мин-макс&#x60; или &#x60;мин-&#x60; («от мин и больше»). Можно передать несколько диапазонов — они объединяются по ИЛИ. (optional)
     * @param _callback Callback for upload/download progress
     * @return Call to execute
     * @throws ApiException If fail to serialize the request body object
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> Объект JSON c ключами &#x60;chat_list&#x60; и &#x60;meta&#x60; </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Некорректный запрос </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Не авторизован </td><td>  -  </td></tr>
        <tr><td> 403 </td><td> Запрещено </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Не найдено </td><td>  -  </td></tr>
        <tr><td> 429 </td><td> Слишком много запросов </td><td>  -  </td></tr>
        <tr><td> 500 </td><td> Внутренняя ошибка сервера </td><td>  -  </td></tr>
     </table>
     */
    public okhttp3.Call getAgentChatListCall(Integer id, Integer offset, Integer limit, String order, OffsetDateTime from, OffsetDateTime to, String reactionType, List<String> responseTimeRanges, final ApiCallback _callback) throws ApiException {
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
        String localVarPath = "/api/v1/cloud-ai/agents/{id}/chat-list"
            .replace("{" + "id" + "}", localVarApiClient.escapeString(id.toString()));

        List<Pair> localVarQueryParams = new ArrayList<Pair>();
        List<Pair> localVarCollectionQueryParams = new ArrayList<Pair>();
        Map<String, String> localVarHeaderParams = new HashMap<String, String>();
        Map<String, String> localVarCookieParams = new HashMap<String, String>();
        Map<String, Object> localVarFormParams = new HashMap<String, Object>();

        if (offset != null) {
            localVarQueryParams.addAll(localVarApiClient.parameterToPair("offset", offset));
        }

        if (limit != null) {
            localVarQueryParams.addAll(localVarApiClient.parameterToPair("limit", limit));
        }

        if (order != null) {
            localVarQueryParams.addAll(localVarApiClient.parameterToPair("order", order));
        }

        if (from != null) {
            localVarQueryParams.addAll(localVarApiClient.parameterToPair("from", from));
        }

        if (to != null) {
            localVarQueryParams.addAll(localVarApiClient.parameterToPair("to", to));
        }

        if (reactionType != null) {
            localVarQueryParams.addAll(localVarApiClient.parameterToPair("reaction_type", reactionType));
        }

        if (responseTimeRanges != null) {
            localVarCollectionQueryParams.addAll(localVarApiClient.parameterToPairs("multi", "response_time_ranges", responseTimeRanges));
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
    private okhttp3.Call getAgentChatListValidateBeforeCall(Integer id, Integer offset, Integer limit, String order, OffsetDateTime from, OffsetDateTime to, String reactionType, List<String> responseTimeRanges, final ApiCallback _callback) throws ApiException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new ApiException("Missing the required parameter 'id' when calling getAgentChatList(Async)");
        }

        return getAgentChatListCall(id, offset, limit, order, from, to, reactionType, responseTimeRanges, _callback);

    }

    /**
     * Получение списка чатов агента
     * Чтобы получить список чатов (диалогов) AI агента, отправьте GET-запрос на &#x60;/api/v1/cloud-ai/agents/{id}/chat-list&#x60;.  Для каждого чата возвращаются первое сообщение пользователя, количество оценок и среднее время ответа. Чаты внешнего чата сюда не входят. Фильтр по периоду применяется, только если переданы оба параметра &#x60;from&#x60; и &#x60;to&#x60;. В &#x60;meta.metrics&#x60; — сводка по чатам для фильтров.  Для токенов дополнительных пользователей аккаунта нужна роль &#x60;ai:read&#x60;.
     * @param id ID агента (required)
     * @param offset Смещение относительно начала списка (optional, default to 0)
     * @param limit Количество чатов в ответе (optional, default to 100)
     * @param order Сортировка по дате первого сообщения (optional, default to asc)
     * @param from Начало периода (дата и время в формате ISO 8601) (optional)
     * @param to Конец периода (дата и время в формате ISO 8601) (optional)
     * @param reactionType Фильтр по оценкам: &#x60;likes&#x60; — есть положительная, &#x60;dislikes&#x60; — есть отрицательная, &#x60;with&#x60; — есть любая, &#x60;none&#x60; — без оценок (optional)
     * @param responseTimeRanges Фильтр по среднему времени ответа в секундах: диапазоны &#x60;мин-макс&#x60; или &#x60;мин-&#x60; («от мин и больше»). Можно передать несколько диапазонов — они объединяются по ИЛИ. (optional)
     * @return GetAgentChatList200Response
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> Объект JSON c ключами &#x60;chat_list&#x60; и &#x60;meta&#x60; </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Некорректный запрос </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Не авторизован </td><td>  -  </td></tr>
        <tr><td> 403 </td><td> Запрещено </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Не найдено </td><td>  -  </td></tr>
        <tr><td> 429 </td><td> Слишком много запросов </td><td>  -  </td></tr>
        <tr><td> 500 </td><td> Внутренняя ошибка сервера </td><td>  -  </td></tr>
     </table>
     */
    public GetAgentChatList200Response getAgentChatList(Integer id, Integer offset, Integer limit, String order, OffsetDateTime from, OffsetDateTime to, String reactionType, List<String> responseTimeRanges) throws ApiException {
        ApiResponse<GetAgentChatList200Response> localVarResp = getAgentChatListWithHttpInfo(id, offset, limit, order, from, to, reactionType, responseTimeRanges);
        return localVarResp.getData();
    }

    /**
     * Получение списка чатов агента
     * Чтобы получить список чатов (диалогов) AI агента, отправьте GET-запрос на &#x60;/api/v1/cloud-ai/agents/{id}/chat-list&#x60;.  Для каждого чата возвращаются первое сообщение пользователя, количество оценок и среднее время ответа. Чаты внешнего чата сюда не входят. Фильтр по периоду применяется, только если переданы оба параметра &#x60;from&#x60; и &#x60;to&#x60;. В &#x60;meta.metrics&#x60; — сводка по чатам для фильтров.  Для токенов дополнительных пользователей аккаунта нужна роль &#x60;ai:read&#x60;.
     * @param id ID агента (required)
     * @param offset Смещение относительно начала списка (optional, default to 0)
     * @param limit Количество чатов в ответе (optional, default to 100)
     * @param order Сортировка по дате первого сообщения (optional, default to asc)
     * @param from Начало периода (дата и время в формате ISO 8601) (optional)
     * @param to Конец периода (дата и время в формате ISO 8601) (optional)
     * @param reactionType Фильтр по оценкам: &#x60;likes&#x60; — есть положительная, &#x60;dislikes&#x60; — есть отрицательная, &#x60;with&#x60; — есть любая, &#x60;none&#x60; — без оценок (optional)
     * @param responseTimeRanges Фильтр по среднему времени ответа в секундах: диапазоны &#x60;мин-макс&#x60; или &#x60;мин-&#x60; («от мин и больше»). Можно передать несколько диапазонов — они объединяются по ИЛИ. (optional)
     * @return ApiResponse&lt;GetAgentChatList200Response&gt;
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> Объект JSON c ключами &#x60;chat_list&#x60; и &#x60;meta&#x60; </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Некорректный запрос </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Не авторизован </td><td>  -  </td></tr>
        <tr><td> 403 </td><td> Запрещено </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Не найдено </td><td>  -  </td></tr>
        <tr><td> 429 </td><td> Слишком много запросов </td><td>  -  </td></tr>
        <tr><td> 500 </td><td> Внутренняя ошибка сервера </td><td>  -  </td></tr>
     </table>
     */
    public ApiResponse<GetAgentChatList200Response> getAgentChatListWithHttpInfo(Integer id, Integer offset, Integer limit, String order, OffsetDateTime from, OffsetDateTime to, String reactionType, List<String> responseTimeRanges) throws ApiException {
        okhttp3.Call localVarCall = getAgentChatListValidateBeforeCall(id, offset, limit, order, from, to, reactionType, responseTimeRanges, null);
        Type localVarReturnType = new TypeToken<GetAgentChatList200Response>(){}.getType();
        return localVarApiClient.execute(localVarCall, localVarReturnType);
    }

    /**
     * Получение списка чатов агента (asynchronously)
     * Чтобы получить список чатов (диалогов) AI агента, отправьте GET-запрос на &#x60;/api/v1/cloud-ai/agents/{id}/chat-list&#x60;.  Для каждого чата возвращаются первое сообщение пользователя, количество оценок и среднее время ответа. Чаты внешнего чата сюда не входят. Фильтр по периоду применяется, только если переданы оба параметра &#x60;from&#x60; и &#x60;to&#x60;. В &#x60;meta.metrics&#x60; — сводка по чатам для фильтров.  Для токенов дополнительных пользователей аккаунта нужна роль &#x60;ai:read&#x60;.
     * @param id ID агента (required)
     * @param offset Смещение относительно начала списка (optional, default to 0)
     * @param limit Количество чатов в ответе (optional, default to 100)
     * @param order Сортировка по дате первого сообщения (optional, default to asc)
     * @param from Начало периода (дата и время в формате ISO 8601) (optional)
     * @param to Конец периода (дата и время в формате ISO 8601) (optional)
     * @param reactionType Фильтр по оценкам: &#x60;likes&#x60; — есть положительная, &#x60;dislikes&#x60; — есть отрицательная, &#x60;with&#x60; — есть любая, &#x60;none&#x60; — без оценок (optional)
     * @param responseTimeRanges Фильтр по среднему времени ответа в секундах: диапазоны &#x60;мин-макс&#x60; или &#x60;мин-&#x60; («от мин и больше»). Можно передать несколько диапазонов — они объединяются по ИЛИ. (optional)
     * @param _callback The callback to be executed when the API call finishes
     * @return The request call
     * @throws ApiException If fail to process the API call, e.g. serializing the request body object
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> Объект JSON c ключами &#x60;chat_list&#x60; и &#x60;meta&#x60; </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Некорректный запрос </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Не авторизован </td><td>  -  </td></tr>
        <tr><td> 403 </td><td> Запрещено </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Не найдено </td><td>  -  </td></tr>
        <tr><td> 429 </td><td> Слишком много запросов </td><td>  -  </td></tr>
        <tr><td> 500 </td><td> Внутренняя ошибка сервера </td><td>  -  </td></tr>
     </table>
     */
    public okhttp3.Call getAgentChatListAsync(Integer id, Integer offset, Integer limit, String order, OffsetDateTime from, OffsetDateTime to, String reactionType, List<String> responseTimeRanges, final ApiCallback<GetAgentChatList200Response> _callback) throws ApiException {

        okhttp3.Call localVarCall = getAgentChatListValidateBeforeCall(id, offset, limit, order, from, to, reactionType, responseTimeRanges, _callback);
        Type localVarReturnType = new TypeToken<GetAgentChatList200Response>(){}.getType();
        localVarApiClient.executeAsync(localVarCall, localVarReturnType, _callback);
        return localVarCall;
    }
    /**
     * Build call for getAgentChatMessages
     * @param id ID агента (required)
     * @param chatId ID чата (диалога) из поля &#x60;id&#x60; списка чатов агента (required)
     * @param offset Смещение относительно начала списка (optional, default to 0)
     * @param limit Количество сообщений в ответе (optional, default to 100)
     * @param order Сортировка по дате сообщения (optional, default to desc)
     * @param from Начало периода (дата и время в формате ISO 8601) (optional)
     * @param to Конец периода (дата и время в формате ISO 8601) (optional)
     * @param _callback Callback for upload/download progress
     * @return Call to execute
     * @throws ApiException If fail to serialize the request body object
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> Объект JSON c ключами &#x60;chat_list_messages&#x60; и &#x60;meta&#x60; </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Некорректный запрос </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Не авторизован </td><td>  -  </td></tr>
        <tr><td> 403 </td><td> Запрещено </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Не найдено </td><td>  -  </td></tr>
        <tr><td> 429 </td><td> Слишком много запросов </td><td>  -  </td></tr>
        <tr><td> 500 </td><td> Внутренняя ошибка сервера </td><td>  -  </td></tr>
     </table>
     */
    public okhttp3.Call getAgentChatMessagesCall(Integer id, String chatId, Integer offset, Integer limit, String order, OffsetDateTime from, OffsetDateTime to, final ApiCallback _callback) throws ApiException {
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
        String localVarPath = "/api/v1/cloud-ai/agents/{id}/chat-list/{chat_id}/messages"
            .replace("{" + "id" + "}", localVarApiClient.escapeString(id.toString()))
            .replace("{" + "chat_id" + "}", localVarApiClient.escapeString(chatId.toString()));

        List<Pair> localVarQueryParams = new ArrayList<Pair>();
        List<Pair> localVarCollectionQueryParams = new ArrayList<Pair>();
        Map<String, String> localVarHeaderParams = new HashMap<String, String>();
        Map<String, String> localVarCookieParams = new HashMap<String, String>();
        Map<String, Object> localVarFormParams = new HashMap<String, Object>();

        if (offset != null) {
            localVarQueryParams.addAll(localVarApiClient.parameterToPair("offset", offset));
        }

        if (limit != null) {
            localVarQueryParams.addAll(localVarApiClient.parameterToPair("limit", limit));
        }

        if (order != null) {
            localVarQueryParams.addAll(localVarApiClient.parameterToPair("order", order));
        }

        if (from != null) {
            localVarQueryParams.addAll(localVarApiClient.parameterToPair("from", from));
        }

        if (to != null) {
            localVarQueryParams.addAll(localVarApiClient.parameterToPair("to", to));
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
    private okhttp3.Call getAgentChatMessagesValidateBeforeCall(Integer id, String chatId, Integer offset, Integer limit, String order, OffsetDateTime from, OffsetDateTime to, final ApiCallback _callback) throws ApiException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new ApiException("Missing the required parameter 'id' when calling getAgentChatMessages(Async)");
        }

        // verify the required parameter 'chatId' is set
        if (chatId == null) {
            throw new ApiException("Missing the required parameter 'chatId' when calling getAgentChatMessages(Async)");
        }

        return getAgentChatMessagesCall(id, chatId, offset, limit, order, from, to, _callback);

    }

    /**
     * Получение сообщений чата агента
     * Чтобы получить сообщения чата AI агента, отправьте GET-запрос на &#x60;/api/v1/cloud-ai/agents/{id}/chat-list/{chat_id}/messages&#x60;.  Возвращаются сообщения пользователя и ответы агента. Фильтр по периоду применяется, только если переданы оба параметра &#x60;from&#x60; и &#x60;to&#x60;.  Для токенов дополнительных пользователей аккаунта нужна роль &#x60;ai:read&#x60;.
     * @param id ID агента (required)
     * @param chatId ID чата (диалога) из поля &#x60;id&#x60; списка чатов агента (required)
     * @param offset Смещение относительно начала списка (optional, default to 0)
     * @param limit Количество сообщений в ответе (optional, default to 100)
     * @param order Сортировка по дате сообщения (optional, default to desc)
     * @param from Начало периода (дата и время в формате ISO 8601) (optional)
     * @param to Конец периода (дата и время в формате ISO 8601) (optional)
     * @return GetAgentChatMessages200Response
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> Объект JSON c ключами &#x60;chat_list_messages&#x60; и &#x60;meta&#x60; </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Некорректный запрос </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Не авторизован </td><td>  -  </td></tr>
        <tr><td> 403 </td><td> Запрещено </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Не найдено </td><td>  -  </td></tr>
        <tr><td> 429 </td><td> Слишком много запросов </td><td>  -  </td></tr>
        <tr><td> 500 </td><td> Внутренняя ошибка сервера </td><td>  -  </td></tr>
     </table>
     */
    public GetAgentChatMessages200Response getAgentChatMessages(Integer id, String chatId, Integer offset, Integer limit, String order, OffsetDateTime from, OffsetDateTime to) throws ApiException {
        ApiResponse<GetAgentChatMessages200Response> localVarResp = getAgentChatMessagesWithHttpInfo(id, chatId, offset, limit, order, from, to);
        return localVarResp.getData();
    }

    /**
     * Получение сообщений чата агента
     * Чтобы получить сообщения чата AI агента, отправьте GET-запрос на &#x60;/api/v1/cloud-ai/agents/{id}/chat-list/{chat_id}/messages&#x60;.  Возвращаются сообщения пользователя и ответы агента. Фильтр по периоду применяется, только если переданы оба параметра &#x60;from&#x60; и &#x60;to&#x60;.  Для токенов дополнительных пользователей аккаунта нужна роль &#x60;ai:read&#x60;.
     * @param id ID агента (required)
     * @param chatId ID чата (диалога) из поля &#x60;id&#x60; списка чатов агента (required)
     * @param offset Смещение относительно начала списка (optional, default to 0)
     * @param limit Количество сообщений в ответе (optional, default to 100)
     * @param order Сортировка по дате сообщения (optional, default to desc)
     * @param from Начало периода (дата и время в формате ISO 8601) (optional)
     * @param to Конец периода (дата и время в формате ISO 8601) (optional)
     * @return ApiResponse&lt;GetAgentChatMessages200Response&gt;
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> Объект JSON c ключами &#x60;chat_list_messages&#x60; и &#x60;meta&#x60; </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Некорректный запрос </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Не авторизован </td><td>  -  </td></tr>
        <tr><td> 403 </td><td> Запрещено </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Не найдено </td><td>  -  </td></tr>
        <tr><td> 429 </td><td> Слишком много запросов </td><td>  -  </td></tr>
        <tr><td> 500 </td><td> Внутренняя ошибка сервера </td><td>  -  </td></tr>
     </table>
     */
    public ApiResponse<GetAgentChatMessages200Response> getAgentChatMessagesWithHttpInfo(Integer id, String chatId, Integer offset, Integer limit, String order, OffsetDateTime from, OffsetDateTime to) throws ApiException {
        okhttp3.Call localVarCall = getAgentChatMessagesValidateBeforeCall(id, chatId, offset, limit, order, from, to, null);
        Type localVarReturnType = new TypeToken<GetAgentChatMessages200Response>(){}.getType();
        return localVarApiClient.execute(localVarCall, localVarReturnType);
    }

    /**
     * Получение сообщений чата агента (asynchronously)
     * Чтобы получить сообщения чата AI агента, отправьте GET-запрос на &#x60;/api/v1/cloud-ai/agents/{id}/chat-list/{chat_id}/messages&#x60;.  Возвращаются сообщения пользователя и ответы агента. Фильтр по периоду применяется, только если переданы оба параметра &#x60;from&#x60; и &#x60;to&#x60;.  Для токенов дополнительных пользователей аккаунта нужна роль &#x60;ai:read&#x60;.
     * @param id ID агента (required)
     * @param chatId ID чата (диалога) из поля &#x60;id&#x60; списка чатов агента (required)
     * @param offset Смещение относительно начала списка (optional, default to 0)
     * @param limit Количество сообщений в ответе (optional, default to 100)
     * @param order Сортировка по дате сообщения (optional, default to desc)
     * @param from Начало периода (дата и время в формате ISO 8601) (optional)
     * @param to Конец периода (дата и время в формате ISO 8601) (optional)
     * @param _callback The callback to be executed when the API call finishes
     * @return The request call
     * @throws ApiException If fail to process the API call, e.g. serializing the request body object
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> Объект JSON c ключами &#x60;chat_list_messages&#x60; и &#x60;meta&#x60; </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Некорректный запрос </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Не авторизован </td><td>  -  </td></tr>
        <tr><td> 403 </td><td> Запрещено </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Не найдено </td><td>  -  </td></tr>
        <tr><td> 429 </td><td> Слишком много запросов </td><td>  -  </td></tr>
        <tr><td> 500 </td><td> Внутренняя ошибка сервера </td><td>  -  </td></tr>
     </table>
     */
    public okhttp3.Call getAgentChatMessagesAsync(Integer id, String chatId, Integer offset, Integer limit, String order, OffsetDateTime from, OffsetDateTime to, final ApiCallback<GetAgentChatMessages200Response> _callback) throws ApiException {

        okhttp3.Call localVarCall = getAgentChatMessagesValidateBeforeCall(id, chatId, offset, limit, order, from, to, _callback);
        Type localVarReturnType = new TypeToken<GetAgentChatMessages200Response>(){}.getType();
        localVarApiClient.executeAsync(localVarCall, localVarReturnType, _callback);
        return localVarCall;
    }
    /**
     * Build call for getAgentEntityLimit
     * @param id ID агента (required)
     * @param limitId ID лимита агента (required)
     * @param _callback Callback for upload/download progress
     * @return Call to execute
     * @throws ApiException If fail to serialize the request body object
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> Объект JSON c ключом &#x60;agent_entity_limit&#x60; </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Некорректный запрос </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Не авторизован </td><td>  -  </td></tr>
        <tr><td> 403 </td><td> Запрещено </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Не найдено </td><td>  -  </td></tr>
        <tr><td> 429 </td><td> Слишком много запросов </td><td>  -  </td></tr>
        <tr><td> 500 </td><td> Внутренняя ошибка сервера </td><td>  -  </td></tr>
     </table>
     */
    public okhttp3.Call getAgentEntityLimitCall(Integer id, UUID limitId, final ApiCallback _callback) throws ApiException {
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
        String localVarPath = "/api/v1/cloud-ai/agents/{id}/entity-limits/{limit_id}"
            .replace("{" + "id" + "}", localVarApiClient.escapeString(id.toString()))
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
    private okhttp3.Call getAgentEntityLimitValidateBeforeCall(Integer id, UUID limitId, final ApiCallback _callback) throws ApiException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new ApiException("Missing the required parameter 'id' when calling getAgentEntityLimit(Async)");
        }

        // verify the required parameter 'limitId' is set
        if (limitId == null) {
            throw new ApiException("Missing the required parameter 'limitId' when calling getAgentEntityLimit(Async)");
        }

        return getAgentEntityLimitCall(id, limitId, _callback);

    }

    /**
     * Получение лимита агента
     * Чтобы получить лимит AI агента, отправьте GET-запрос на &#x60;/api/v1/cloud-ai/agents/{id}/entity-limits/{limit_id}&#x60;.  Для токенов дополнительных пользователей аккаунта нужна роль &#x60;ai:read&#x60;.
     * @param id ID агента (required)
     * @param limitId ID лимита агента (required)
     * @return CreateAgentEntityLimit201Response
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> Объект JSON c ключом &#x60;agent_entity_limit&#x60; </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Некорректный запрос </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Не авторизован </td><td>  -  </td></tr>
        <tr><td> 403 </td><td> Запрещено </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Не найдено </td><td>  -  </td></tr>
        <tr><td> 429 </td><td> Слишком много запросов </td><td>  -  </td></tr>
        <tr><td> 500 </td><td> Внутренняя ошибка сервера </td><td>  -  </td></tr>
     </table>
     */
    public CreateAgentEntityLimit201Response getAgentEntityLimit(Integer id, UUID limitId) throws ApiException {
        ApiResponse<CreateAgentEntityLimit201Response> localVarResp = getAgentEntityLimitWithHttpInfo(id, limitId);
        return localVarResp.getData();
    }

    /**
     * Получение лимита агента
     * Чтобы получить лимит AI агента, отправьте GET-запрос на &#x60;/api/v1/cloud-ai/agents/{id}/entity-limits/{limit_id}&#x60;.  Для токенов дополнительных пользователей аккаунта нужна роль &#x60;ai:read&#x60;.
     * @param id ID агента (required)
     * @param limitId ID лимита агента (required)
     * @return ApiResponse&lt;CreateAgentEntityLimit201Response&gt;
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> Объект JSON c ключом &#x60;agent_entity_limit&#x60; </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Некорректный запрос </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Не авторизован </td><td>  -  </td></tr>
        <tr><td> 403 </td><td> Запрещено </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Не найдено </td><td>  -  </td></tr>
        <tr><td> 429 </td><td> Слишком много запросов </td><td>  -  </td></tr>
        <tr><td> 500 </td><td> Внутренняя ошибка сервера </td><td>  -  </td></tr>
     </table>
     */
    public ApiResponse<CreateAgentEntityLimit201Response> getAgentEntityLimitWithHttpInfo(Integer id, UUID limitId) throws ApiException {
        okhttp3.Call localVarCall = getAgentEntityLimitValidateBeforeCall(id, limitId, null);
        Type localVarReturnType = new TypeToken<CreateAgentEntityLimit201Response>(){}.getType();
        return localVarApiClient.execute(localVarCall, localVarReturnType);
    }

    /**
     * Получение лимита агента (asynchronously)
     * Чтобы получить лимит AI агента, отправьте GET-запрос на &#x60;/api/v1/cloud-ai/agents/{id}/entity-limits/{limit_id}&#x60;.  Для токенов дополнительных пользователей аккаунта нужна роль &#x60;ai:read&#x60;.
     * @param id ID агента (required)
     * @param limitId ID лимита агента (required)
     * @param _callback The callback to be executed when the API call finishes
     * @return The request call
     * @throws ApiException If fail to process the API call, e.g. serializing the request body object
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> Объект JSON c ключом &#x60;agent_entity_limit&#x60; </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Некорректный запрос </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Не авторизован </td><td>  -  </td></tr>
        <tr><td> 403 </td><td> Запрещено </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Не найдено </td><td>  -  </td></tr>
        <tr><td> 429 </td><td> Слишком много запросов </td><td>  -  </td></tr>
        <tr><td> 500 </td><td> Внутренняя ошибка сервера </td><td>  -  </td></tr>
     </table>
     */
    public okhttp3.Call getAgentEntityLimitAsync(Integer id, UUID limitId, final ApiCallback<CreateAgentEntityLimit201Response> _callback) throws ApiException {

        okhttp3.Call localVarCall = getAgentEntityLimitValidateBeforeCall(id, limitId, _callback);
        Type localVarReturnType = new TypeToken<CreateAgentEntityLimit201Response>(){}.getType();
        localVarApiClient.executeAsync(localVarCall, localVarReturnType, _callback);
        return localVarCall;
    }
    /**
     * Build call for getAgentEntityLimits
     * @param id ID агента (required)
     * @param limitType Фильтр по периоду лимита (optional)
     * @param _callback Callback for upload/download progress
     * @return Call to execute
     * @throws ApiException If fail to serialize the request body object
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> Объект JSON c ключами &#x60;agent_entity_limits&#x60; и &#x60;meta&#x60; </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Некорректный запрос </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Не авторизован </td><td>  -  </td></tr>
        <tr><td> 403 </td><td> Запрещено </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Не найдено </td><td>  -  </td></tr>
        <tr><td> 429 </td><td> Слишком много запросов </td><td>  -  </td></tr>
        <tr><td> 500 </td><td> Внутренняя ошибка сервера </td><td>  -  </td></tr>
     </table>
     */
    public okhttp3.Call getAgentEntityLimitsCall(Integer id, String limitType, final ApiCallback _callback) throws ApiException {
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
        String localVarPath = "/api/v1/cloud-ai/agents/{id}/entity-limits"
            .replace("{" + "id" + "}", localVarApiClient.escapeString(id.toString()));

        List<Pair> localVarQueryParams = new ArrayList<Pair>();
        List<Pair> localVarCollectionQueryParams = new ArrayList<Pair>();
        Map<String, String> localVarHeaderParams = new HashMap<String, String>();
        Map<String, String> localVarCookieParams = new HashMap<String, String>();
        Map<String, Object> localVarFormParams = new HashMap<String, Object>();

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
    private okhttp3.Call getAgentEntityLimitsValidateBeforeCall(Integer id, String limitType, final ApiCallback _callback) throws ApiException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new ApiException("Missing the required parameter 'id' when calling getAgentEntityLimits(Async)");
        }

        return getAgentEntityLimitsCall(id, limitType, _callback);

    }

    /**
     * Получение лимитов агента
     * Чтобы получить лимиты AI агента, отправьте GET-запрос на &#x60;/api/v1/cloud-ai/agents/{id}/entity-limits&#x60;.  Лимиты агента ограничивают расход на его запросы за период (&#x60;hour&#x60;, &#x60;day&#x60;, &#x60;week&#x60;, &#x60;month&#x60;) — в токенах (&#x60;limit&#x60;) или в деньгах (&#x60;money_limit&#x60;, приоритетнее). Лимит действует на все модели агента (&#x60;model_id&#x60; &#x3D; &#x60;null&#x60;) или на одну модель. При достижении лимита запросы к агенту отклоняются до конца периода.  Для токенов дополнительных пользователей аккаунта нужна роль &#x60;ai:read&#x60;.
     * @param id ID агента (required)
     * @param limitType Фильтр по периоду лимита (optional)
     * @return GetAgentEntityLimits200Response
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> Объект JSON c ключами &#x60;agent_entity_limits&#x60; и &#x60;meta&#x60; </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Некорректный запрос </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Не авторизован </td><td>  -  </td></tr>
        <tr><td> 403 </td><td> Запрещено </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Не найдено </td><td>  -  </td></tr>
        <tr><td> 429 </td><td> Слишком много запросов </td><td>  -  </td></tr>
        <tr><td> 500 </td><td> Внутренняя ошибка сервера </td><td>  -  </td></tr>
     </table>
     */
    public GetAgentEntityLimits200Response getAgentEntityLimits(Integer id, String limitType) throws ApiException {
        ApiResponse<GetAgentEntityLimits200Response> localVarResp = getAgentEntityLimitsWithHttpInfo(id, limitType);
        return localVarResp.getData();
    }

    /**
     * Получение лимитов агента
     * Чтобы получить лимиты AI агента, отправьте GET-запрос на &#x60;/api/v1/cloud-ai/agents/{id}/entity-limits&#x60;.  Лимиты агента ограничивают расход на его запросы за период (&#x60;hour&#x60;, &#x60;day&#x60;, &#x60;week&#x60;, &#x60;month&#x60;) — в токенах (&#x60;limit&#x60;) или в деньгах (&#x60;money_limit&#x60;, приоритетнее). Лимит действует на все модели агента (&#x60;model_id&#x60; &#x3D; &#x60;null&#x60;) или на одну модель. При достижении лимита запросы к агенту отклоняются до конца периода.  Для токенов дополнительных пользователей аккаунта нужна роль &#x60;ai:read&#x60;.
     * @param id ID агента (required)
     * @param limitType Фильтр по периоду лимита (optional)
     * @return ApiResponse&lt;GetAgentEntityLimits200Response&gt;
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> Объект JSON c ключами &#x60;agent_entity_limits&#x60; и &#x60;meta&#x60; </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Некорректный запрос </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Не авторизован </td><td>  -  </td></tr>
        <tr><td> 403 </td><td> Запрещено </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Не найдено </td><td>  -  </td></tr>
        <tr><td> 429 </td><td> Слишком много запросов </td><td>  -  </td></tr>
        <tr><td> 500 </td><td> Внутренняя ошибка сервера </td><td>  -  </td></tr>
     </table>
     */
    public ApiResponse<GetAgentEntityLimits200Response> getAgentEntityLimitsWithHttpInfo(Integer id, String limitType) throws ApiException {
        okhttp3.Call localVarCall = getAgentEntityLimitsValidateBeforeCall(id, limitType, null);
        Type localVarReturnType = new TypeToken<GetAgentEntityLimits200Response>(){}.getType();
        return localVarApiClient.execute(localVarCall, localVarReturnType);
    }

    /**
     * Получение лимитов агента (asynchronously)
     * Чтобы получить лимиты AI агента, отправьте GET-запрос на &#x60;/api/v1/cloud-ai/agents/{id}/entity-limits&#x60;.  Лимиты агента ограничивают расход на его запросы за период (&#x60;hour&#x60;, &#x60;day&#x60;, &#x60;week&#x60;, &#x60;month&#x60;) — в токенах (&#x60;limit&#x60;) или в деньгах (&#x60;money_limit&#x60;, приоритетнее). Лимит действует на все модели агента (&#x60;model_id&#x60; &#x3D; &#x60;null&#x60;) или на одну модель. При достижении лимита запросы к агенту отклоняются до конца периода.  Для токенов дополнительных пользователей аккаунта нужна роль &#x60;ai:read&#x60;.
     * @param id ID агента (required)
     * @param limitType Фильтр по периоду лимита (optional)
     * @param _callback The callback to be executed when the API call finishes
     * @return The request call
     * @throws ApiException If fail to process the API call, e.g. serializing the request body object
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> Объект JSON c ключами &#x60;agent_entity_limits&#x60; и &#x60;meta&#x60; </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Некорректный запрос </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Не авторизован </td><td>  -  </td></tr>
        <tr><td> 403 </td><td> Запрещено </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Не найдено </td><td>  -  </td></tr>
        <tr><td> 429 </td><td> Слишком много запросов </td><td>  -  </td></tr>
        <tr><td> 500 </td><td> Внутренняя ошибка сервера </td><td>  -  </td></tr>
     </table>
     */
    public okhttp3.Call getAgentEntityLimitsAsync(Integer id, String limitType, final ApiCallback<GetAgentEntityLimits200Response> _callback) throws ApiException {

        okhttp3.Call localVarCall = getAgentEntityLimitsValidateBeforeCall(id, limitType, _callback);
        Type localVarReturnType = new TypeToken<GetAgentEntityLimits200Response>(){}.getType();
        localVarApiClient.executeAsync(localVarCall, localVarReturnType, _callback);
        return localVarCall;
    }
    /**
     * Build call for getAgentStatistics
     * @param id ID агента (required)
     * @param startTime Начало периода (ISO 8601). По умолчанию — за 24 часа до &#x60;endTime&#x60;. (optional)
     * @param endTime Конец периода (ISO 8601). По умолчанию — текущий момент. (optional)
     * @param interval Длина интервала агрегации в минутах (optional, default to 60)
     * @param _callback Callback for upload/download progress
     * @return Call to execute
     * @throws ApiException If fail to serialize the request body object
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> Объект JSON c ключами &#x60;agent_statistics&#x60; и &#x60;meta&#x60; </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Некорректный запрос </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Не авторизован </td><td>  -  </td></tr>
        <tr><td> 403 </td><td> Запрещено </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Не найдено </td><td>  -  </td></tr>
        <tr><td> 429 </td><td> Слишком много запросов </td><td>  -  </td></tr>
        <tr><td> 500 </td><td> Внутренняя ошибка сервера </td><td>  -  </td></tr>
     </table>
     */
    public okhttp3.Call getAgentStatisticsCall(Integer id, OffsetDateTime startTime, OffsetDateTime endTime, Integer interval, final ApiCallback _callback) throws ApiException {
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
        String localVarPath = "/api/v1/cloud-ai/agents/{id}/statistic"
            .replace("{" + "id" + "}", localVarApiClient.escapeString(id.toString()));

        List<Pair> localVarQueryParams = new ArrayList<Pair>();
        List<Pair> localVarCollectionQueryParams = new ArrayList<Pair>();
        Map<String, String> localVarHeaderParams = new HashMap<String, String>();
        Map<String, String> localVarCookieParams = new HashMap<String, String>();
        Map<String, Object> localVarFormParams = new HashMap<String, Object>();

        if (startTime != null) {
            localVarQueryParams.addAll(localVarApiClient.parameterToPair("startTime", startTime));
        }

        if (endTime != null) {
            localVarQueryParams.addAll(localVarApiClient.parameterToPair("endTime", endTime));
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
    private okhttp3.Call getAgentStatisticsValidateBeforeCall(Integer id, OffsetDateTime startTime, OffsetDateTime endTime, Integer interval, final ApiCallback _callback) throws ApiException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new ApiException("Missing the required parameter 'id' when calling getAgentStatistics(Async)");
        }

        return getAgentStatisticsCall(id, startTime, endTime, interval, _callback);

    }

    /**
     * Получение статистики использования токенов агента
     * Чтобы получить статистику использования токенов AI агента, отправьте GET-запрос на &#x60;/api/v1/cloud-ai/agents/{id}/statistic&#x60;.  Статистика разбита на интервалы длиной &#x60;interval&#x60; минут: для каждого интервала возвращается по точке на каждую модель, к которой были запросы за период, а для интервалов без запросов — одна нулевая точка без &#x60;model_name&#x60;. По умолчанию возвращаются последние 24 часа.  Для токенов дополнительных пользователей аккаунта нужна роль &#x60;ai:read&#x60;.
     * @param id ID агента (required)
     * @param startTime Начало периода (ISO 8601). По умолчанию — за 24 часа до &#x60;endTime&#x60;. (optional)
     * @param endTime Конец периода (ISO 8601). По умолчанию — текущий момент. (optional)
     * @param interval Длина интервала агрегации в минутах (optional, default to 60)
     * @return GetAgentStatistics200Response
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> Объект JSON c ключами &#x60;agent_statistics&#x60; и &#x60;meta&#x60; </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Некорректный запрос </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Не авторизован </td><td>  -  </td></tr>
        <tr><td> 403 </td><td> Запрещено </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Не найдено </td><td>  -  </td></tr>
        <tr><td> 429 </td><td> Слишком много запросов </td><td>  -  </td></tr>
        <tr><td> 500 </td><td> Внутренняя ошибка сервера </td><td>  -  </td></tr>
     </table>
     */
    public GetAgentStatistics200Response getAgentStatistics(Integer id, OffsetDateTime startTime, OffsetDateTime endTime, Integer interval) throws ApiException {
        ApiResponse<GetAgentStatistics200Response> localVarResp = getAgentStatisticsWithHttpInfo(id, startTime, endTime, interval);
        return localVarResp.getData();
    }

    /**
     * Получение статистики использования токенов агента
     * Чтобы получить статистику использования токенов AI агента, отправьте GET-запрос на &#x60;/api/v1/cloud-ai/agents/{id}/statistic&#x60;.  Статистика разбита на интервалы длиной &#x60;interval&#x60; минут: для каждого интервала возвращается по точке на каждую модель, к которой были запросы за период, а для интервалов без запросов — одна нулевая точка без &#x60;model_name&#x60;. По умолчанию возвращаются последние 24 часа.  Для токенов дополнительных пользователей аккаунта нужна роль &#x60;ai:read&#x60;.
     * @param id ID агента (required)
     * @param startTime Начало периода (ISO 8601). По умолчанию — за 24 часа до &#x60;endTime&#x60;. (optional)
     * @param endTime Конец периода (ISO 8601). По умолчанию — текущий момент. (optional)
     * @param interval Длина интервала агрегации в минутах (optional, default to 60)
     * @return ApiResponse&lt;GetAgentStatistics200Response&gt;
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> Объект JSON c ключами &#x60;agent_statistics&#x60; и &#x60;meta&#x60; </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Некорректный запрос </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Не авторизован </td><td>  -  </td></tr>
        <tr><td> 403 </td><td> Запрещено </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Не найдено </td><td>  -  </td></tr>
        <tr><td> 429 </td><td> Слишком много запросов </td><td>  -  </td></tr>
        <tr><td> 500 </td><td> Внутренняя ошибка сервера </td><td>  -  </td></tr>
     </table>
     */
    public ApiResponse<GetAgentStatistics200Response> getAgentStatisticsWithHttpInfo(Integer id, OffsetDateTime startTime, OffsetDateTime endTime, Integer interval) throws ApiException {
        okhttp3.Call localVarCall = getAgentStatisticsValidateBeforeCall(id, startTime, endTime, interval, null);
        Type localVarReturnType = new TypeToken<GetAgentStatistics200Response>(){}.getType();
        return localVarApiClient.execute(localVarCall, localVarReturnType);
    }

    /**
     * Получение статистики использования токенов агента (asynchronously)
     * Чтобы получить статистику использования токенов AI агента, отправьте GET-запрос на &#x60;/api/v1/cloud-ai/agents/{id}/statistic&#x60;.  Статистика разбита на интервалы длиной &#x60;interval&#x60; минут: для каждого интервала возвращается по точке на каждую модель, к которой были запросы за период, а для интервалов без запросов — одна нулевая точка без &#x60;model_name&#x60;. По умолчанию возвращаются последние 24 часа.  Для токенов дополнительных пользователей аккаунта нужна роль &#x60;ai:read&#x60;.
     * @param id ID агента (required)
     * @param startTime Начало периода (ISO 8601). По умолчанию — за 24 часа до &#x60;endTime&#x60;. (optional)
     * @param endTime Конец периода (ISO 8601). По умолчанию — текущий момент. (optional)
     * @param interval Длина интервала агрегации в минутах (optional, default to 60)
     * @param _callback The callback to be executed when the API call finishes
     * @return The request call
     * @throws ApiException If fail to process the API call, e.g. serializing the request body object
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> Объект JSON c ключами &#x60;agent_statistics&#x60; и &#x60;meta&#x60; </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Некорректный запрос </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Не авторизован </td><td>  -  </td></tr>
        <tr><td> 403 </td><td> Запрещено </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Не найдено </td><td>  -  </td></tr>
        <tr><td> 429 </td><td> Слишком много запросов </td><td>  -  </td></tr>
        <tr><td> 500 </td><td> Внутренняя ошибка сервера </td><td>  -  </td></tr>
     </table>
     */
    public okhttp3.Call getAgentStatisticsAsync(Integer id, OffsetDateTime startTime, OffsetDateTime endTime, Integer interval, final ApiCallback<GetAgentStatistics200Response> _callback) throws ApiException {

        okhttp3.Call localVarCall = getAgentStatisticsValidateBeforeCall(id, startTime, endTime, interval, _callback);
        Type localVarReturnType = new TypeToken<GetAgentStatistics200Response>(){}.getType();
        localVarApiClient.executeAsync(localVarCall, localVarReturnType, _callback);
        return localVarCall;
    }
    /**
     * Build call for getAgentV3
     * @param id ID агента (required)
     * @param _callback Callback for upload/download progress
     * @return Call to execute
     * @throws ApiException If fail to serialize the request body object
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> Объект JSON c ключом &#x60;agent&#x60; </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Некорректный запрос </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Не авторизован </td><td>  -  </td></tr>
        <tr><td> 403 </td><td> Запрещено </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Не найдено </td><td>  -  </td></tr>
        <tr><td> 429 </td><td> Слишком много запросов </td><td>  -  </td></tr>
        <tr><td> 500 </td><td> Внутренняя ошибка сервера </td><td>  -  </td></tr>
     </table>
     */
    public okhttp3.Call getAgentV3Call(Integer id, final ApiCallback _callback) throws ApiException {
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
        String localVarPath = "/api/v3/cloud-ai/agents/{id}"
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
    private okhttp3.Call getAgentV3ValidateBeforeCall(Integer id, final ApiCallback _callback) throws ApiException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new ApiException("Missing the required parameter 'id' when calling getAgentV3(Async)");
        }

        return getAgentV3Call(id, _callback);

    }

    /**
     * Получение AI агента (v3)
     * Чтобы получить информацию об AI агенте, отправьте GET-запрос на &#x60;/api/v3/cloud-ai/agents/{id}&#x60;.  Отличается от &#x60;/api/v1/cloud-ai/agents/{id}&#x60; тем, что вместо &#x60;knowledge_bases_ids&#x60; возвращает &#x60;knowledge_bases&#x60; — подключённые базы знаний с режимом поиска.  Для токенов дополнительных пользователей аккаунта нужна роль &#x60;ai:read&#x60;.
     * @param id ID агента (required)
     * @return GetAgentV3200Response
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> Объект JSON c ключом &#x60;agent&#x60; </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Некорректный запрос </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Не авторизован </td><td>  -  </td></tr>
        <tr><td> 403 </td><td> Запрещено </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Не найдено </td><td>  -  </td></tr>
        <tr><td> 429 </td><td> Слишком много запросов </td><td>  -  </td></tr>
        <tr><td> 500 </td><td> Внутренняя ошибка сервера </td><td>  -  </td></tr>
     </table>
     */
    public GetAgentV3200Response getAgentV3(Integer id) throws ApiException {
        ApiResponse<GetAgentV3200Response> localVarResp = getAgentV3WithHttpInfo(id);
        return localVarResp.getData();
    }

    /**
     * Получение AI агента (v3)
     * Чтобы получить информацию об AI агенте, отправьте GET-запрос на &#x60;/api/v3/cloud-ai/agents/{id}&#x60;.  Отличается от &#x60;/api/v1/cloud-ai/agents/{id}&#x60; тем, что вместо &#x60;knowledge_bases_ids&#x60; возвращает &#x60;knowledge_bases&#x60; — подключённые базы знаний с режимом поиска.  Для токенов дополнительных пользователей аккаунта нужна роль &#x60;ai:read&#x60;.
     * @param id ID агента (required)
     * @return ApiResponse&lt;GetAgentV3200Response&gt;
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> Объект JSON c ключом &#x60;agent&#x60; </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Некорректный запрос </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Не авторизован </td><td>  -  </td></tr>
        <tr><td> 403 </td><td> Запрещено </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Не найдено </td><td>  -  </td></tr>
        <tr><td> 429 </td><td> Слишком много запросов </td><td>  -  </td></tr>
        <tr><td> 500 </td><td> Внутренняя ошибка сервера </td><td>  -  </td></tr>
     </table>
     */
    public ApiResponse<GetAgentV3200Response> getAgentV3WithHttpInfo(Integer id) throws ApiException {
        okhttp3.Call localVarCall = getAgentV3ValidateBeforeCall(id, null);
        Type localVarReturnType = new TypeToken<GetAgentV3200Response>(){}.getType();
        return localVarApiClient.execute(localVarCall, localVarReturnType);
    }

    /**
     * Получение AI агента (v3) (asynchronously)
     * Чтобы получить информацию об AI агенте, отправьте GET-запрос на &#x60;/api/v3/cloud-ai/agents/{id}&#x60;.  Отличается от &#x60;/api/v1/cloud-ai/agents/{id}&#x60; тем, что вместо &#x60;knowledge_bases_ids&#x60; возвращает &#x60;knowledge_bases&#x60; — подключённые базы знаний с режимом поиска.  Для токенов дополнительных пользователей аккаунта нужна роль &#x60;ai:read&#x60;.
     * @param id ID агента (required)
     * @param _callback The callback to be executed when the API call finishes
     * @return The request call
     * @throws ApiException If fail to process the API call, e.g. serializing the request body object
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> Объект JSON c ключом &#x60;agent&#x60; </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Некорректный запрос </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Не авторизован </td><td>  -  </td></tr>
        <tr><td> 403 </td><td> Запрещено </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Не найдено </td><td>  -  </td></tr>
        <tr><td> 429 </td><td> Слишком много запросов </td><td>  -  </td></tr>
        <tr><td> 500 </td><td> Внутренняя ошибка сервера </td><td>  -  </td></tr>
     </table>
     */
    public okhttp3.Call getAgentV3Async(Integer id, final ApiCallback<GetAgentV3200Response> _callback) throws ApiException {

        okhttp3.Call localVarCall = getAgentV3ValidateBeforeCall(id, _callback);
        Type localVarReturnType = new TypeToken<GetAgentV3200Response>(){}.getType();
        localVarApiClient.executeAsync(localVarCall, localVarReturnType, _callback);
        return localVarCall;
    }
    /**
     * Build call for getAgents
     * @param _callback Callback for upload/download progress
     * @return Call to execute
     * @throws ApiException If fail to serialize the request body object
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> Объект JSON c ключами &#x60;agents&#x60; и &#x60;meta&#x60; </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Некорректный запрос </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Не авторизован </td><td>  -  </td></tr>
        <tr><td> 403 </td><td> Запрещено </td><td>  -  </td></tr>
        <tr><td> 429 </td><td> Слишком много запросов </td><td>  -  </td></tr>
        <tr><td> 500 </td><td> Внутренняя ошибка сервера </td><td>  -  </td></tr>
     </table>
     */
    public okhttp3.Call getAgentsCall(final ApiCallback _callback) throws ApiException {
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
        String localVarPath = "/api/v1/cloud-ai/agents";

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
    private okhttp3.Call getAgentsValidateBeforeCall(final ApiCallback _callback) throws ApiException {
        return getAgentsCall(_callback);

    }

    /**
     * Получение списка AI агентов
     * Чтобы получить список AI агентов, отправьте GET-запрос на &#x60;/api/v1/cloud-ai/agents&#x60;.  Возвращаются все агенты аккаунта (без пагинации), включая приостановленных и заблокированных. Тело ответа будет представлять собой объект JSON с ключами &#x60;agents&#x60; и &#x60;meta&#x60;. Подключённые базы знаний вместе с режимом поиска возвращает метод &#x60;/api/v3/cloud-ai/agents&#x60;.  Для токенов дополнительных пользователей аккаунта нужна роль &#x60;ai:read&#x60;.
     * @return GetAgents200Response
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> Объект JSON c ключами &#x60;agents&#x60; и &#x60;meta&#x60; </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Некорректный запрос </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Не авторизован </td><td>  -  </td></tr>
        <tr><td> 403 </td><td> Запрещено </td><td>  -  </td></tr>
        <tr><td> 429 </td><td> Слишком много запросов </td><td>  -  </td></tr>
        <tr><td> 500 </td><td> Внутренняя ошибка сервера </td><td>  -  </td></tr>
     </table>
     */
    public GetAgents200Response getAgents() throws ApiException {
        ApiResponse<GetAgents200Response> localVarResp = getAgentsWithHttpInfo();
        return localVarResp.getData();
    }

    /**
     * Получение списка AI агентов
     * Чтобы получить список AI агентов, отправьте GET-запрос на &#x60;/api/v1/cloud-ai/agents&#x60;.  Возвращаются все агенты аккаунта (без пагинации), включая приостановленных и заблокированных. Тело ответа будет представлять собой объект JSON с ключами &#x60;agents&#x60; и &#x60;meta&#x60;. Подключённые базы знаний вместе с режимом поиска возвращает метод &#x60;/api/v3/cloud-ai/agents&#x60;.  Для токенов дополнительных пользователей аккаунта нужна роль &#x60;ai:read&#x60;.
     * @return ApiResponse&lt;GetAgents200Response&gt;
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> Объект JSON c ключами &#x60;agents&#x60; и &#x60;meta&#x60; </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Некорректный запрос </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Не авторизован </td><td>  -  </td></tr>
        <tr><td> 403 </td><td> Запрещено </td><td>  -  </td></tr>
        <tr><td> 429 </td><td> Слишком много запросов </td><td>  -  </td></tr>
        <tr><td> 500 </td><td> Внутренняя ошибка сервера </td><td>  -  </td></tr>
     </table>
     */
    public ApiResponse<GetAgents200Response> getAgentsWithHttpInfo() throws ApiException {
        okhttp3.Call localVarCall = getAgentsValidateBeforeCall(null);
        Type localVarReturnType = new TypeToken<GetAgents200Response>(){}.getType();
        return localVarApiClient.execute(localVarCall, localVarReturnType);
    }

    /**
     * Получение списка AI агентов (asynchronously)
     * Чтобы получить список AI агентов, отправьте GET-запрос на &#x60;/api/v1/cloud-ai/agents&#x60;.  Возвращаются все агенты аккаунта (без пагинации), включая приостановленных и заблокированных. Тело ответа будет представлять собой объект JSON с ключами &#x60;agents&#x60; и &#x60;meta&#x60;. Подключённые базы знаний вместе с режимом поиска возвращает метод &#x60;/api/v3/cloud-ai/agents&#x60;.  Для токенов дополнительных пользователей аккаунта нужна роль &#x60;ai:read&#x60;.
     * @param _callback The callback to be executed when the API call finishes
     * @return The request call
     * @throws ApiException If fail to process the API call, e.g. serializing the request body object
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> Объект JSON c ключами &#x60;agents&#x60; и &#x60;meta&#x60; </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Некорректный запрос </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Не авторизован </td><td>  -  </td></tr>
        <tr><td> 403 </td><td> Запрещено </td><td>  -  </td></tr>
        <tr><td> 429 </td><td> Слишком много запросов </td><td>  -  </td></tr>
        <tr><td> 500 </td><td> Внутренняя ошибка сервера </td><td>  -  </td></tr>
     </table>
     */
    public okhttp3.Call getAgentsAsync(final ApiCallback<GetAgents200Response> _callback) throws ApiException {

        okhttp3.Call localVarCall = getAgentsValidateBeforeCall(_callback);
        Type localVarReturnType = new TypeToken<GetAgents200Response>(){}.getType();
        localVarApiClient.executeAsync(localVarCall, localVarReturnType, _callback);
        return localVarCall;
    }
    /**
     * Build call for getAgentsV3
     * @param _callback Callback for upload/download progress
     * @return Call to execute
     * @throws ApiException If fail to serialize the request body object
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> Объект JSON c ключами &#x60;agents&#x60; и &#x60;meta&#x60; </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Некорректный запрос </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Не авторизован </td><td>  -  </td></tr>
        <tr><td> 403 </td><td> Запрещено </td><td>  -  </td></tr>
        <tr><td> 429 </td><td> Слишком много запросов </td><td>  -  </td></tr>
        <tr><td> 500 </td><td> Внутренняя ошибка сервера </td><td>  -  </td></tr>
     </table>
     */
    public okhttp3.Call getAgentsV3Call(final ApiCallback _callback) throws ApiException {
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
        String localVarPath = "/api/v3/cloud-ai/agents";

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
    private okhttp3.Call getAgentsV3ValidateBeforeCall(final ApiCallback _callback) throws ApiException {
        return getAgentsV3Call(_callback);

    }

    /**
     * Получение списка AI агентов (v3)
     * Чтобы получить список AI агентов, отправьте GET-запрос на &#x60;/api/v3/cloud-ai/agents&#x60;.  Отличается от &#x60;/api/v1/cloud-ai/agents&#x60; тем, что вместо &#x60;knowledge_bases_ids&#x60; возвращает &#x60;knowledge_bases&#x60; — подключённые базы знаний с режимом поиска.  Для токенов дополнительных пользователей аккаунта нужна роль &#x60;ai:read&#x60;.
     * @return GetAgentsV3200Response
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> Объект JSON c ключами &#x60;agents&#x60; и &#x60;meta&#x60; </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Некорректный запрос </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Не авторизован </td><td>  -  </td></tr>
        <tr><td> 403 </td><td> Запрещено </td><td>  -  </td></tr>
        <tr><td> 429 </td><td> Слишком много запросов </td><td>  -  </td></tr>
        <tr><td> 500 </td><td> Внутренняя ошибка сервера </td><td>  -  </td></tr>
     </table>
     */
    public GetAgentsV3200Response getAgentsV3() throws ApiException {
        ApiResponse<GetAgentsV3200Response> localVarResp = getAgentsV3WithHttpInfo();
        return localVarResp.getData();
    }

    /**
     * Получение списка AI агентов (v3)
     * Чтобы получить список AI агентов, отправьте GET-запрос на &#x60;/api/v3/cloud-ai/agents&#x60;.  Отличается от &#x60;/api/v1/cloud-ai/agents&#x60; тем, что вместо &#x60;knowledge_bases_ids&#x60; возвращает &#x60;knowledge_bases&#x60; — подключённые базы знаний с режимом поиска.  Для токенов дополнительных пользователей аккаунта нужна роль &#x60;ai:read&#x60;.
     * @return ApiResponse&lt;GetAgentsV3200Response&gt;
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> Объект JSON c ключами &#x60;agents&#x60; и &#x60;meta&#x60; </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Некорректный запрос </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Не авторизован </td><td>  -  </td></tr>
        <tr><td> 403 </td><td> Запрещено </td><td>  -  </td></tr>
        <tr><td> 429 </td><td> Слишком много запросов </td><td>  -  </td></tr>
        <tr><td> 500 </td><td> Внутренняя ошибка сервера </td><td>  -  </td></tr>
     </table>
     */
    public ApiResponse<GetAgentsV3200Response> getAgentsV3WithHttpInfo() throws ApiException {
        okhttp3.Call localVarCall = getAgentsV3ValidateBeforeCall(null);
        Type localVarReturnType = new TypeToken<GetAgentsV3200Response>(){}.getType();
        return localVarApiClient.execute(localVarCall, localVarReturnType);
    }

    /**
     * Получение списка AI агентов (v3) (asynchronously)
     * Чтобы получить список AI агентов, отправьте GET-запрос на &#x60;/api/v3/cloud-ai/agents&#x60;.  Отличается от &#x60;/api/v1/cloud-ai/agents&#x60; тем, что вместо &#x60;knowledge_bases_ids&#x60; возвращает &#x60;knowledge_bases&#x60; — подключённые базы знаний с режимом поиска.  Для токенов дополнительных пользователей аккаунта нужна роль &#x60;ai:read&#x60;.
     * @param _callback The callback to be executed when the API call finishes
     * @return The request call
     * @throws ApiException If fail to process the API call, e.g. serializing the request body object
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> Объект JSON c ключами &#x60;agents&#x60; и &#x60;meta&#x60; </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Некорректный запрос </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Не авторизован </td><td>  -  </td></tr>
        <tr><td> 403 </td><td> Запрещено </td><td>  -  </td></tr>
        <tr><td> 429 </td><td> Слишком много запросов </td><td>  -  </td></tr>
        <tr><td> 500 </td><td> Внутренняя ошибка сервера </td><td>  -  </td></tr>
     </table>
     */
    public okhttp3.Call getAgentsV3Async(final ApiCallback<GetAgentsV3200Response> _callback) throws ApiException {

        okhttp3.Call localVarCall = getAgentsV3ValidateBeforeCall(_callback);
        Type localVarReturnType = new TypeToken<GetAgentsV3200Response>(){}.getType();
        localVarApiClient.executeAsync(localVarCall, localVarReturnType, _callback);
        return localVarCall;
    }
    /**
     * Build call for syncAgentEntityLimits
     * @param id ID агента (required)
     * @param syncAgentEntityLimits  (required)
     * @param _callback Callback for upload/download progress
     * @return Call to execute
     * @throws ApiException If fail to serialize the request body object
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> Объект JSON c ключами &#x60;agent_entity_limits&#x60; и &#x60;meta&#x60; </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Некорректный запрос </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Не авторизован </td><td>  -  </td></tr>
        <tr><td> 403 </td><td> Запрещено </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Не найдено </td><td>  -  </td></tr>
        <tr><td> 409 </td><td> Конфликт </td><td>  -  </td></tr>
        <tr><td> 429 </td><td> Слишком много запросов </td><td>  -  </td></tr>
        <tr><td> 500 </td><td> Внутренняя ошибка сервера </td><td>  -  </td></tr>
     </table>
     */
    public okhttp3.Call syncAgentEntityLimitsCall(Integer id, SyncAgentEntityLimits syncAgentEntityLimits, final ApiCallback _callback) throws ApiException {
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

        Object localVarPostBody = syncAgentEntityLimits;

        // create path and map variables
        String localVarPath = "/api/v1/cloud-ai/agents/{id}/entity-limits/sync"
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
    private okhttp3.Call syncAgentEntityLimitsValidateBeforeCall(Integer id, SyncAgentEntityLimits syncAgentEntityLimits, final ApiCallback _callback) throws ApiException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new ApiException("Missing the required parameter 'id' when calling syncAgentEntityLimits(Async)");
        }

        // verify the required parameter 'syncAgentEntityLimits' is set
        if (syncAgentEntityLimits == null) {
            throw new ApiException("Missing the required parameter 'syncAgentEntityLimits' when calling syncAgentEntityLimits(Async)");
        }

        return syncAgentEntityLimitsCall(id, syncAgentEntityLimits, _callback);

    }

    /**
     * Синхронизация лимитов агента за период
     * Чтобы задать полный набор лимитов AI агента за период, отправьте PUT-запрос на &#x60;/api/v1/cloud-ai/agents/{id}/entity-limits/sync&#x60;.  Лимиты из запроса создаются или обновляются (сопоставление по &#x60;model_id&#x60;), а лимиты этого периода, которых нет в запросе, удаляются. Лимиты других периодов не затрагиваются. В ответе — актуальные лимиты периода. Если лимиты одновременно изменил другой запрос, вернётся 409 &#x60;entity_keys_limits_sync_conflict&#x60; — повторите запрос.  Для токенов дополнительных пользователей аккаунта нужна роль &#x60;ai:write&#x60;.
     * @param id ID агента (required)
     * @param syncAgentEntityLimits  (required)
     * @return GetAgentEntityLimits200Response
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> Объект JSON c ключами &#x60;agent_entity_limits&#x60; и &#x60;meta&#x60; </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Некорректный запрос </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Не авторизован </td><td>  -  </td></tr>
        <tr><td> 403 </td><td> Запрещено </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Не найдено </td><td>  -  </td></tr>
        <tr><td> 409 </td><td> Конфликт </td><td>  -  </td></tr>
        <tr><td> 429 </td><td> Слишком много запросов </td><td>  -  </td></tr>
        <tr><td> 500 </td><td> Внутренняя ошибка сервера </td><td>  -  </td></tr>
     </table>
     */
    public GetAgentEntityLimits200Response syncAgentEntityLimits(Integer id, SyncAgentEntityLimits syncAgentEntityLimits) throws ApiException {
        ApiResponse<GetAgentEntityLimits200Response> localVarResp = syncAgentEntityLimitsWithHttpInfo(id, syncAgentEntityLimits);
        return localVarResp.getData();
    }

    /**
     * Синхронизация лимитов агента за период
     * Чтобы задать полный набор лимитов AI агента за период, отправьте PUT-запрос на &#x60;/api/v1/cloud-ai/agents/{id}/entity-limits/sync&#x60;.  Лимиты из запроса создаются или обновляются (сопоставление по &#x60;model_id&#x60;), а лимиты этого периода, которых нет в запросе, удаляются. Лимиты других периодов не затрагиваются. В ответе — актуальные лимиты периода. Если лимиты одновременно изменил другой запрос, вернётся 409 &#x60;entity_keys_limits_sync_conflict&#x60; — повторите запрос.  Для токенов дополнительных пользователей аккаунта нужна роль &#x60;ai:write&#x60;.
     * @param id ID агента (required)
     * @param syncAgentEntityLimits  (required)
     * @return ApiResponse&lt;GetAgentEntityLimits200Response&gt;
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> Объект JSON c ключами &#x60;agent_entity_limits&#x60; и &#x60;meta&#x60; </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Некорректный запрос </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Не авторизован </td><td>  -  </td></tr>
        <tr><td> 403 </td><td> Запрещено </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Не найдено </td><td>  -  </td></tr>
        <tr><td> 409 </td><td> Конфликт </td><td>  -  </td></tr>
        <tr><td> 429 </td><td> Слишком много запросов </td><td>  -  </td></tr>
        <tr><td> 500 </td><td> Внутренняя ошибка сервера </td><td>  -  </td></tr>
     </table>
     */
    public ApiResponse<GetAgentEntityLimits200Response> syncAgentEntityLimitsWithHttpInfo(Integer id, SyncAgentEntityLimits syncAgentEntityLimits) throws ApiException {
        okhttp3.Call localVarCall = syncAgentEntityLimitsValidateBeforeCall(id, syncAgentEntityLimits, null);
        Type localVarReturnType = new TypeToken<GetAgentEntityLimits200Response>(){}.getType();
        return localVarApiClient.execute(localVarCall, localVarReturnType);
    }

    /**
     * Синхронизация лимитов агента за период (asynchronously)
     * Чтобы задать полный набор лимитов AI агента за период, отправьте PUT-запрос на &#x60;/api/v1/cloud-ai/agents/{id}/entity-limits/sync&#x60;.  Лимиты из запроса создаются или обновляются (сопоставление по &#x60;model_id&#x60;), а лимиты этого периода, которых нет в запросе, удаляются. Лимиты других периодов не затрагиваются. В ответе — актуальные лимиты периода. Если лимиты одновременно изменил другой запрос, вернётся 409 &#x60;entity_keys_limits_sync_conflict&#x60; — повторите запрос.  Для токенов дополнительных пользователей аккаунта нужна роль &#x60;ai:write&#x60;.
     * @param id ID агента (required)
     * @param syncAgentEntityLimits  (required)
     * @param _callback The callback to be executed when the API call finishes
     * @return The request call
     * @throws ApiException If fail to process the API call, e.g. serializing the request body object
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> Объект JSON c ключами &#x60;agent_entity_limits&#x60; и &#x60;meta&#x60; </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Некорректный запрос </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Не авторизован </td><td>  -  </td></tr>
        <tr><td> 403 </td><td> Запрещено </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Не найдено </td><td>  -  </td></tr>
        <tr><td> 409 </td><td> Конфликт </td><td>  -  </td></tr>
        <tr><td> 429 </td><td> Слишком много запросов </td><td>  -  </td></tr>
        <tr><td> 500 </td><td> Внутренняя ошибка сервера </td><td>  -  </td></tr>
     </table>
     */
    public okhttp3.Call syncAgentEntityLimitsAsync(Integer id, SyncAgentEntityLimits syncAgentEntityLimits, final ApiCallback<GetAgentEntityLimits200Response> _callback) throws ApiException {

        okhttp3.Call localVarCall = syncAgentEntityLimitsValidateBeforeCall(id, syncAgentEntityLimits, _callback);
        Type localVarReturnType = new TypeToken<GetAgentEntityLimits200Response>(){}.getType();
        localVarApiClient.executeAsync(localVarCall, localVarReturnType, _callback);
        return localVarCall;
    }
    /**
     * Build call for updateAgent
     * @param id ID агента (required)
     * @param updateAgent  (required)
     * @param _callback Callback for upload/download progress
     * @return Call to execute
     * @throws ApiException If fail to serialize the request body object
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> Объект JSON c ключом &#x60;agent&#x60; </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Некорректный запрос </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Не авторизован </td><td>  -  </td></tr>
        <tr><td> 403 </td><td> Запрещено </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Не найдено </td><td>  -  </td></tr>
        <tr><td> 410 </td><td> Передано удалённое поле &#x60;token_limit&#x60; </td><td>  -  </td></tr>
        <tr><td> 429 </td><td> Слишком много запросов </td><td>  -  </td></tr>
        <tr><td> 500 </td><td> Внутренняя ошибка сервера </td><td>  -  </td></tr>
        <tr><td> 503 </td><td> Не удалось проверить возможность привязки &#x60;ai_gateway_key_id&#x60;, повторите запрос позже </td><td>  -  </td></tr>
     </table>
     */
    public okhttp3.Call updateAgentCall(Integer id, UpdateAgent updateAgent, final ApiCallback _callback) throws ApiException {
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

        Object localVarPostBody = updateAgent;

        // create path and map variables
        String localVarPath = "/api/v1/cloud-ai/agents/{id}"
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
        return localVarApiClient.buildCall(basePath, localVarPath, "PATCH", localVarQueryParams, localVarCollectionQueryParams, localVarPostBody, localVarHeaderParams, localVarCookieParams, localVarFormParams, localVarAuthNames, _callback);
    }

    @SuppressWarnings("rawtypes")
    private okhttp3.Call updateAgentValidateBeforeCall(Integer id, UpdateAgent updateAgent, final ApiCallback _callback) throws ApiException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new ApiException("Missing the required parameter 'id' when calling updateAgent(Async)");
        }

        // verify the required parameter 'updateAgent' is set
        if (updateAgent == null) {
            throw new ApiException("Missing the required parameter 'updateAgent' when calling updateAgent(Async)");
        }

        return updateAgentCall(id, updateAgent, _callback);

    }

    /**
     * Обновление AI агента
     * Чтобы обновить AI агента, отправьте PATCH-запрос на &#x60;/api/v1/cloud-ai/agents/{id}&#x60;, передав изменяемые поля.  Особенности: - &#x60;settings&#x60; заменяются целиком (кроме &#x60;web_search_allowed_sites&#x60;, &#x60;additional_models&#x60; и &#x60;memory_enabled&#x60;, которые сохраняются, если не переданы) — передавайте полный объект настроек, включая &#x60;widget&#x60;; - основную модель меняет отдельный метод &#x60;PATCH /api/v1/cloud-ai/agents/{id}/model&#x60;; - поле &#x60;token_limit&#x60; больше не поддерживается: запрос с ним отклоняется с кодом 410 — лимиты задаются через &#x60;/api/v1/cloud-ai/agents/{id}/entity-limits&#x60;; - ошибки привязки &#x60;ai_gateway_key_id&#x60;: 400 &#x60;ai_gateway_key_not_found&#x60;, &#x60;agent_is_already_migrated_to_another_ai_gateway_key&#x60;, &#x60;agent_is_not_eligible_for_ai_gateway_migration&#x60;; 503 — проверку временно не удалось выполнить; - изменить статус заблокированного или удалённого агента нельзя (400 &#x60;agent_can&#39;t_change_status&#x60;).  Для токенов дополнительных пользователей аккаунта нужна роль &#x60;ai:write&#x60;.
     * @param id ID агента (required)
     * @param updateAgent  (required)
     * @return CreateAgent201Response
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> Объект JSON c ключом &#x60;agent&#x60; </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Некорректный запрос </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Не авторизован </td><td>  -  </td></tr>
        <tr><td> 403 </td><td> Запрещено </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Не найдено </td><td>  -  </td></tr>
        <tr><td> 410 </td><td> Передано удалённое поле &#x60;token_limit&#x60; </td><td>  -  </td></tr>
        <tr><td> 429 </td><td> Слишком много запросов </td><td>  -  </td></tr>
        <tr><td> 500 </td><td> Внутренняя ошибка сервера </td><td>  -  </td></tr>
        <tr><td> 503 </td><td> Не удалось проверить возможность привязки &#x60;ai_gateway_key_id&#x60;, повторите запрос позже </td><td>  -  </td></tr>
     </table>
     */
    public CreateAgent201Response updateAgent(Integer id, UpdateAgent updateAgent) throws ApiException {
        ApiResponse<CreateAgent201Response> localVarResp = updateAgentWithHttpInfo(id, updateAgent);
        return localVarResp.getData();
    }

    /**
     * Обновление AI агента
     * Чтобы обновить AI агента, отправьте PATCH-запрос на &#x60;/api/v1/cloud-ai/agents/{id}&#x60;, передав изменяемые поля.  Особенности: - &#x60;settings&#x60; заменяются целиком (кроме &#x60;web_search_allowed_sites&#x60;, &#x60;additional_models&#x60; и &#x60;memory_enabled&#x60;, которые сохраняются, если не переданы) — передавайте полный объект настроек, включая &#x60;widget&#x60;; - основную модель меняет отдельный метод &#x60;PATCH /api/v1/cloud-ai/agents/{id}/model&#x60;; - поле &#x60;token_limit&#x60; больше не поддерживается: запрос с ним отклоняется с кодом 410 — лимиты задаются через &#x60;/api/v1/cloud-ai/agents/{id}/entity-limits&#x60;; - ошибки привязки &#x60;ai_gateway_key_id&#x60;: 400 &#x60;ai_gateway_key_not_found&#x60;, &#x60;agent_is_already_migrated_to_another_ai_gateway_key&#x60;, &#x60;agent_is_not_eligible_for_ai_gateway_migration&#x60;; 503 — проверку временно не удалось выполнить; - изменить статус заблокированного или удалённого агента нельзя (400 &#x60;agent_can&#39;t_change_status&#x60;).  Для токенов дополнительных пользователей аккаунта нужна роль &#x60;ai:write&#x60;.
     * @param id ID агента (required)
     * @param updateAgent  (required)
     * @return ApiResponse&lt;CreateAgent201Response&gt;
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> Объект JSON c ключом &#x60;agent&#x60; </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Некорректный запрос </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Не авторизован </td><td>  -  </td></tr>
        <tr><td> 403 </td><td> Запрещено </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Не найдено </td><td>  -  </td></tr>
        <tr><td> 410 </td><td> Передано удалённое поле &#x60;token_limit&#x60; </td><td>  -  </td></tr>
        <tr><td> 429 </td><td> Слишком много запросов </td><td>  -  </td></tr>
        <tr><td> 500 </td><td> Внутренняя ошибка сервера </td><td>  -  </td></tr>
        <tr><td> 503 </td><td> Не удалось проверить возможность привязки &#x60;ai_gateway_key_id&#x60;, повторите запрос позже </td><td>  -  </td></tr>
     </table>
     */
    public ApiResponse<CreateAgent201Response> updateAgentWithHttpInfo(Integer id, UpdateAgent updateAgent) throws ApiException {
        okhttp3.Call localVarCall = updateAgentValidateBeforeCall(id, updateAgent, null);
        Type localVarReturnType = new TypeToken<CreateAgent201Response>(){}.getType();
        return localVarApiClient.execute(localVarCall, localVarReturnType);
    }

    /**
     * Обновление AI агента (asynchronously)
     * Чтобы обновить AI агента, отправьте PATCH-запрос на &#x60;/api/v1/cloud-ai/agents/{id}&#x60;, передав изменяемые поля.  Особенности: - &#x60;settings&#x60; заменяются целиком (кроме &#x60;web_search_allowed_sites&#x60;, &#x60;additional_models&#x60; и &#x60;memory_enabled&#x60;, которые сохраняются, если не переданы) — передавайте полный объект настроек, включая &#x60;widget&#x60;; - основную модель меняет отдельный метод &#x60;PATCH /api/v1/cloud-ai/agents/{id}/model&#x60;; - поле &#x60;token_limit&#x60; больше не поддерживается: запрос с ним отклоняется с кодом 410 — лимиты задаются через &#x60;/api/v1/cloud-ai/agents/{id}/entity-limits&#x60;; - ошибки привязки &#x60;ai_gateway_key_id&#x60;: 400 &#x60;ai_gateway_key_not_found&#x60;, &#x60;agent_is_already_migrated_to_another_ai_gateway_key&#x60;, &#x60;agent_is_not_eligible_for_ai_gateway_migration&#x60;; 503 — проверку временно не удалось выполнить; - изменить статус заблокированного или удалённого агента нельзя (400 &#x60;agent_can&#39;t_change_status&#x60;).  Для токенов дополнительных пользователей аккаунта нужна роль &#x60;ai:write&#x60;.
     * @param id ID агента (required)
     * @param updateAgent  (required)
     * @param _callback The callback to be executed when the API call finishes
     * @return The request call
     * @throws ApiException If fail to process the API call, e.g. serializing the request body object
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> Объект JSON c ключом &#x60;agent&#x60; </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Некорректный запрос </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Не авторизован </td><td>  -  </td></tr>
        <tr><td> 403 </td><td> Запрещено </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Не найдено </td><td>  -  </td></tr>
        <tr><td> 410 </td><td> Передано удалённое поле &#x60;token_limit&#x60; </td><td>  -  </td></tr>
        <tr><td> 429 </td><td> Слишком много запросов </td><td>  -  </td></tr>
        <tr><td> 500 </td><td> Внутренняя ошибка сервера </td><td>  -  </td></tr>
        <tr><td> 503 </td><td> Не удалось проверить возможность привязки &#x60;ai_gateway_key_id&#x60;, повторите запрос позже </td><td>  -  </td></tr>
     </table>
     */
    public okhttp3.Call updateAgentAsync(Integer id, UpdateAgent updateAgent, final ApiCallback<CreateAgent201Response> _callback) throws ApiException {

        okhttp3.Call localVarCall = updateAgentValidateBeforeCall(id, updateAgent, _callback);
        Type localVarReturnType = new TypeToken<CreateAgent201Response>(){}.getType();
        localVarApiClient.executeAsync(localVarCall, localVarReturnType, _callback);
        return localVarCall;
    }
    /**
     * Build call for updateAgentEntityLimit
     * @param id ID агента (required)
     * @param limitId ID лимита агента (required)
     * @param updateAgentEntityLimit  (required)
     * @param _callback Callback for upload/download progress
     * @return Call to execute
     * @throws ApiException If fail to serialize the request body object
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> Объект JSON c ключом &#x60;agent_entity_limit&#x60; </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Некорректный запрос </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Не авторизован </td><td>  -  </td></tr>
        <tr><td> 403 </td><td> Запрещено </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Не найдено </td><td>  -  </td></tr>
        <tr><td> 429 </td><td> Слишком много запросов </td><td>  -  </td></tr>
        <tr><td> 500 </td><td> Внутренняя ошибка сервера </td><td>  -  </td></tr>
     </table>
     */
    public okhttp3.Call updateAgentEntityLimitCall(Integer id, UUID limitId, UpdateAgentEntityLimit updateAgentEntityLimit, final ApiCallback _callback) throws ApiException {
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

        Object localVarPostBody = updateAgentEntityLimit;

        // create path and map variables
        String localVarPath = "/api/v1/cloud-ai/agents/{id}/entity-limits/{limit_id}"
            .replace("{" + "id" + "}", localVarApiClient.escapeString(id.toString()))
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
    private okhttp3.Call updateAgentEntityLimitValidateBeforeCall(Integer id, UUID limitId, UpdateAgentEntityLimit updateAgentEntityLimit, final ApiCallback _callback) throws ApiException {
        // verify the required parameter 'id' is set
        if (id == null) {
            throw new ApiException("Missing the required parameter 'id' when calling updateAgentEntityLimit(Async)");
        }

        // verify the required parameter 'limitId' is set
        if (limitId == null) {
            throw new ApiException("Missing the required parameter 'limitId' when calling updateAgentEntityLimit(Async)");
        }

        // verify the required parameter 'updateAgentEntityLimit' is set
        if (updateAgentEntityLimit == null) {
            throw new ApiException("Missing the required parameter 'updateAgentEntityLimit' when calling updateAgentEntityLimit(Async)");
        }

        return updateAgentEntityLimitCall(id, limitId, updateAgentEntityLimit, _callback);

    }

    /**
     * Изменение лимита агента
     * Чтобы изменить значение лимита AI агента, отправьте PATCH-запрос на &#x60;/api/v1/cloud-ai/agents/{id}/entity-limits/{limit_id}&#x60;.  Непереданные поля не меняются. Снять денежный лимит (&#x60;money_limit&#x60;: &#x60;null&#x60;) можно только вместе с передачей &#x60;limit&#x60;.  Для токенов дополнительных пользователей аккаунта нужна роль &#x60;ai:write&#x60;.
     * @param id ID агента (required)
     * @param limitId ID лимита агента (required)
     * @param updateAgentEntityLimit  (required)
     * @return CreateAgentEntityLimit201Response
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> Объект JSON c ключом &#x60;agent_entity_limit&#x60; </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Некорректный запрос </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Не авторизован </td><td>  -  </td></tr>
        <tr><td> 403 </td><td> Запрещено </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Не найдено </td><td>  -  </td></tr>
        <tr><td> 429 </td><td> Слишком много запросов </td><td>  -  </td></tr>
        <tr><td> 500 </td><td> Внутренняя ошибка сервера </td><td>  -  </td></tr>
     </table>
     */
    public CreateAgentEntityLimit201Response updateAgentEntityLimit(Integer id, UUID limitId, UpdateAgentEntityLimit updateAgentEntityLimit) throws ApiException {
        ApiResponse<CreateAgentEntityLimit201Response> localVarResp = updateAgentEntityLimitWithHttpInfo(id, limitId, updateAgentEntityLimit);
        return localVarResp.getData();
    }

    /**
     * Изменение лимита агента
     * Чтобы изменить значение лимита AI агента, отправьте PATCH-запрос на &#x60;/api/v1/cloud-ai/agents/{id}/entity-limits/{limit_id}&#x60;.  Непереданные поля не меняются. Снять денежный лимит (&#x60;money_limit&#x60;: &#x60;null&#x60;) можно только вместе с передачей &#x60;limit&#x60;.  Для токенов дополнительных пользователей аккаунта нужна роль &#x60;ai:write&#x60;.
     * @param id ID агента (required)
     * @param limitId ID лимита агента (required)
     * @param updateAgentEntityLimit  (required)
     * @return ApiResponse&lt;CreateAgentEntityLimit201Response&gt;
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> Объект JSON c ключом &#x60;agent_entity_limit&#x60; </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Некорректный запрос </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Не авторизован </td><td>  -  </td></tr>
        <tr><td> 403 </td><td> Запрещено </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Не найдено </td><td>  -  </td></tr>
        <tr><td> 429 </td><td> Слишком много запросов </td><td>  -  </td></tr>
        <tr><td> 500 </td><td> Внутренняя ошибка сервера </td><td>  -  </td></tr>
     </table>
     */
    public ApiResponse<CreateAgentEntityLimit201Response> updateAgentEntityLimitWithHttpInfo(Integer id, UUID limitId, UpdateAgentEntityLimit updateAgentEntityLimit) throws ApiException {
        okhttp3.Call localVarCall = updateAgentEntityLimitValidateBeforeCall(id, limitId, updateAgentEntityLimit, null);
        Type localVarReturnType = new TypeToken<CreateAgentEntityLimit201Response>(){}.getType();
        return localVarApiClient.execute(localVarCall, localVarReturnType);
    }

    /**
     * Изменение лимита агента (asynchronously)
     * Чтобы изменить значение лимита AI агента, отправьте PATCH-запрос на &#x60;/api/v1/cloud-ai/agents/{id}/entity-limits/{limit_id}&#x60;.  Непереданные поля не меняются. Снять денежный лимит (&#x60;money_limit&#x60;: &#x60;null&#x60;) можно только вместе с передачей &#x60;limit&#x60;.  Для токенов дополнительных пользователей аккаунта нужна роль &#x60;ai:write&#x60;.
     * @param id ID агента (required)
     * @param limitId ID лимита агента (required)
     * @param updateAgentEntityLimit  (required)
     * @param _callback The callback to be executed when the API call finishes
     * @return The request call
     * @throws ApiException If fail to process the API call, e.g. serializing the request body object
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> Объект JSON c ключом &#x60;agent_entity_limit&#x60; </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Некорректный запрос </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Не авторизован </td><td>  -  </td></tr>
        <tr><td> 403 </td><td> Запрещено </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Не найдено </td><td>  -  </td></tr>
        <tr><td> 429 </td><td> Слишком много запросов </td><td>  -  </td></tr>
        <tr><td> 500 </td><td> Внутренняя ошибка сервера </td><td>  -  </td></tr>
     </table>
     */
    public okhttp3.Call updateAgentEntityLimitAsync(Integer id, UUID limitId, UpdateAgentEntityLimit updateAgentEntityLimit, final ApiCallback<CreateAgentEntityLimit201Response> _callback) throws ApiException {

        okhttp3.Call localVarCall = updateAgentEntityLimitValidateBeforeCall(id, limitId, updateAgentEntityLimit, _callback);
        Type localVarReturnType = new TypeToken<CreateAgentEntityLimit201Response>(){}.getType();
        localVarApiClient.executeAsync(localVarCall, localVarReturnType, _callback);
        return localVarCall;
    }
}
