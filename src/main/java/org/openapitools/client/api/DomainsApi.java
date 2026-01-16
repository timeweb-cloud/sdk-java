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


import org.openapitools.client.model.AddSubdomain201Response;
import org.openapitools.client.model.CheckDomain200Response;
import org.openapitools.client.model.CreateDatabaseBackup409Response;
import org.openapitools.client.model.CreateDns;
import org.openapitools.client.model.CreateDnsV2;
import org.openapitools.client.model.CreateDomainDNSRecord201Response;
import org.openapitools.client.model.CreateDomainDNSRecordV2201Response;
import org.openapitools.client.model.CreateDomainRequest201Response;
import org.openapitools.client.model.DomainRegister;
import org.openapitools.client.model.GetDomain200Response;
import org.openapitools.client.model.GetDomainDNSRecords200Response;
import org.openapitools.client.model.GetDomainNameServers200Response;
import org.openapitools.client.model.GetDomainRequests200Response;
import org.openapitools.client.model.GetDomains200Response;
import org.openapitools.client.model.GetFinances400Response;
import org.openapitools.client.model.GetFinances401Response;
import org.openapitools.client.model.GetFinances429Response;
import org.openapitools.client.model.GetFinances500Response;
import org.openapitools.client.model.GetImage404Response;
import org.openapitools.client.model.GetTLD200Response;
import org.openapitools.client.model.GetTLDs200Response;
import org.openapitools.client.model.UpdateDomain;
import org.openapitools.client.model.UpdateDomainAutoProlongation200Response;
import org.openapitools.client.model.UpdateDomainNameServers;
import org.openapitools.client.model.Use;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DomainsApi {
    private ApiClient localVarApiClient;
    private int localHostIndex;
    private String localCustomBaseUrl;

    public DomainsApi() {
        this(Configuration.getDefaultApiClient());
    }

    public DomainsApi(ApiClient apiClient) {
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
     * Build call for addDomain
     * @param fqdn Полное имя домена. (required)
     * @param _callback Callback for upload/download progress
     * @return Call to execute
     * @throws ApiException If fail to serialize the request body object
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 204 </td><td> Домен успешно добавлен на ваш аккаунт. </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Некорректный запрос </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Не авторизован </td><td>  -  </td></tr>
        <tr><td> 409 </td><td> Конфликт </td><td>  -  </td></tr>
        <tr><td> 429 </td><td> Слишком много запросов </td><td>  -  </td></tr>
        <tr><td> 500 </td><td> Внутренняя ошибка сервера </td><td>  -  </td></tr>
     </table>
     */
    public okhttp3.Call addDomainCall(String fqdn, final ApiCallback _callback) throws ApiException {
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
        String localVarPath = "/api/v1/add-domain/{fqdn}"
            .replace("{" + "fqdn" + "}", localVarApiClient.escapeString(fqdn.toString()));

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
    private okhttp3.Call addDomainValidateBeforeCall(String fqdn, final ApiCallback _callback) throws ApiException {
        // verify the required parameter 'fqdn' is set
        if (fqdn == null) {
            throw new ApiException("Missing the required parameter 'fqdn' when calling addDomain(Async)");
        }

        return addDomainCall(fqdn, _callback);

    }

    /**
     * Добавление домена на аккаунт
     * Чтобы добавить домен на свой аккаунт, отправьте запрос POST на &#x60;/api/v1/add-domain/{fqdn}&#x60;.
     * @param fqdn Полное имя домена. (required)
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 204 </td><td> Домен успешно добавлен на ваш аккаунт. </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Некорректный запрос </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Не авторизован </td><td>  -  </td></tr>
        <tr><td> 409 </td><td> Конфликт </td><td>  -  </td></tr>
        <tr><td> 429 </td><td> Слишком много запросов </td><td>  -  </td></tr>
        <tr><td> 500 </td><td> Внутренняя ошибка сервера </td><td>  -  </td></tr>
     </table>
     */
    public void addDomain(String fqdn) throws ApiException {
        addDomainWithHttpInfo(fqdn);
    }

    /**
     * Добавление домена на аккаунт
     * Чтобы добавить домен на свой аккаунт, отправьте запрос POST на &#x60;/api/v1/add-domain/{fqdn}&#x60;.
     * @param fqdn Полное имя домена. (required)
     * @return ApiResponse&lt;Void&gt;
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 204 </td><td> Домен успешно добавлен на ваш аккаунт. </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Некорректный запрос </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Не авторизован </td><td>  -  </td></tr>
        <tr><td> 409 </td><td> Конфликт </td><td>  -  </td></tr>
        <tr><td> 429 </td><td> Слишком много запросов </td><td>  -  </td></tr>
        <tr><td> 500 </td><td> Внутренняя ошибка сервера </td><td>  -  </td></tr>
     </table>
     */
    public ApiResponse<Void> addDomainWithHttpInfo(String fqdn) throws ApiException {
        okhttp3.Call localVarCall = addDomainValidateBeforeCall(fqdn, null);
        return localVarApiClient.execute(localVarCall);
    }

    /**
     * Добавление домена на аккаунт (asynchronously)
     * Чтобы добавить домен на свой аккаунт, отправьте запрос POST на &#x60;/api/v1/add-domain/{fqdn}&#x60;.
     * @param fqdn Полное имя домена. (required)
     * @param _callback The callback to be executed when the API call finishes
     * @return The request call
     * @throws ApiException If fail to process the API call, e.g. serializing the request body object
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 204 </td><td> Домен успешно добавлен на ваш аккаунт. </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Некорректный запрос </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Не авторизован </td><td>  -  </td></tr>
        <tr><td> 409 </td><td> Конфликт </td><td>  -  </td></tr>
        <tr><td> 429 </td><td> Слишком много запросов </td><td>  -  </td></tr>
        <tr><td> 500 </td><td> Внутренняя ошибка сервера </td><td>  -  </td></tr>
     </table>
     */
    public okhttp3.Call addDomainAsync(String fqdn, final ApiCallback<Void> _callback) throws ApiException {

        okhttp3.Call localVarCall = addDomainValidateBeforeCall(fqdn, _callback);
        localVarApiClient.executeAsync(localVarCall, _callback);
        return localVarCall;
    }
    /**
     * Build call for addSubdomain
     * @param fqdn Полное имя домена. (required)
     * @param subdomainFqdn Полное имя поддомена. (required)
     * @param _callback Callback for upload/download progress
     * @return Call to execute
     * @throws ApiException If fail to serialize the request body object
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 201 </td><td> Ответ будет представлять собой объект JSON c ключом &#x60;subdomain&#x60;. </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Некорректный запрос </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Не авторизован </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Не найдено </td><td>  -  </td></tr>
        <tr><td> 429 </td><td> Слишком много запросов </td><td>  -  </td></tr>
        <tr><td> 500 </td><td> Внутренняя ошибка сервера </td><td>  -  </td></tr>
     </table>
     */
    public okhttp3.Call addSubdomainCall(String fqdn, String subdomainFqdn, final ApiCallback _callback) throws ApiException {
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
        String localVarPath = "/api/v1/domains/{fqdn}/subdomains/{subdomain_fqdn}"
            .replace("{" + "fqdn" + "}", localVarApiClient.escapeString(fqdn.toString()))
            .replace("{" + "subdomain_fqdn" + "}", localVarApiClient.escapeString(subdomainFqdn.toString()));

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
    private okhttp3.Call addSubdomainValidateBeforeCall(String fqdn, String subdomainFqdn, final ApiCallback _callback) throws ApiException {
        // verify the required parameter 'fqdn' is set
        if (fqdn == null) {
            throw new ApiException("Missing the required parameter 'fqdn' when calling addSubdomain(Async)");
        }

        // verify the required parameter 'subdomainFqdn' is set
        if (subdomainFqdn == null) {
            throw new ApiException("Missing the required parameter 'subdomainFqdn' when calling addSubdomain(Async)");
        }

        return addSubdomainCall(fqdn, subdomainFqdn, _callback);

    }

    /**
     * Добавление поддомена
     * Чтобы добавить поддомен, отправьте запрос POST на &#x60;/api/v1/domains/{fqdn}/subdomains/{subdomain_fqdn}&#x60;, задав необходимые атрибуты.  Поддомен будет добавлен с использованием предоставленной информации. Тело ответа будет содержать объект JSON с информацией о добавленном поддомене.
     * @param fqdn Полное имя домена. (required)
     * @param subdomainFqdn Полное имя поддомена. (required)
     * @return AddSubdomain201Response
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 201 </td><td> Ответ будет представлять собой объект JSON c ключом &#x60;subdomain&#x60;. </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Некорректный запрос </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Не авторизован </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Не найдено </td><td>  -  </td></tr>
        <tr><td> 429 </td><td> Слишком много запросов </td><td>  -  </td></tr>
        <tr><td> 500 </td><td> Внутренняя ошибка сервера </td><td>  -  </td></tr>
     </table>
     */
    public AddSubdomain201Response addSubdomain(String fqdn, String subdomainFqdn) throws ApiException {
        ApiResponse<AddSubdomain201Response> localVarResp = addSubdomainWithHttpInfo(fqdn, subdomainFqdn);
        return localVarResp.getData();
    }

    /**
     * Добавление поддомена
     * Чтобы добавить поддомен, отправьте запрос POST на &#x60;/api/v1/domains/{fqdn}/subdomains/{subdomain_fqdn}&#x60;, задав необходимые атрибуты.  Поддомен будет добавлен с использованием предоставленной информации. Тело ответа будет содержать объект JSON с информацией о добавленном поддомене.
     * @param fqdn Полное имя домена. (required)
     * @param subdomainFqdn Полное имя поддомена. (required)
     * @return ApiResponse&lt;AddSubdomain201Response&gt;
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 201 </td><td> Ответ будет представлять собой объект JSON c ключом &#x60;subdomain&#x60;. </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Некорректный запрос </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Не авторизован </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Не найдено </td><td>  -  </td></tr>
        <tr><td> 429 </td><td> Слишком много запросов </td><td>  -  </td></tr>
        <tr><td> 500 </td><td> Внутренняя ошибка сервера </td><td>  -  </td></tr>
     </table>
     */
    public ApiResponse<AddSubdomain201Response> addSubdomainWithHttpInfo(String fqdn, String subdomainFqdn) throws ApiException {
        okhttp3.Call localVarCall = addSubdomainValidateBeforeCall(fqdn, subdomainFqdn, null);
        Type localVarReturnType = new TypeToken<AddSubdomain201Response>(){}.getType();
        return localVarApiClient.execute(localVarCall, localVarReturnType);
    }

    /**
     * Добавление поддомена (asynchronously)
     * Чтобы добавить поддомен, отправьте запрос POST на &#x60;/api/v1/domains/{fqdn}/subdomains/{subdomain_fqdn}&#x60;, задав необходимые атрибуты.  Поддомен будет добавлен с использованием предоставленной информации. Тело ответа будет содержать объект JSON с информацией о добавленном поддомене.
     * @param fqdn Полное имя домена. (required)
     * @param subdomainFqdn Полное имя поддомена. (required)
     * @param _callback The callback to be executed when the API call finishes
     * @return The request call
     * @throws ApiException If fail to process the API call, e.g. serializing the request body object
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 201 </td><td> Ответ будет представлять собой объект JSON c ключом &#x60;subdomain&#x60;. </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Некорректный запрос </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Не авторизован </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Не найдено </td><td>  -  </td></tr>
        <tr><td> 429 </td><td> Слишком много запросов </td><td>  -  </td></tr>
        <tr><td> 500 </td><td> Внутренняя ошибка сервера </td><td>  -  </td></tr>
     </table>
     */
    public okhttp3.Call addSubdomainAsync(String fqdn, String subdomainFqdn, final ApiCallback<AddSubdomain201Response> _callback) throws ApiException {

        okhttp3.Call localVarCall = addSubdomainValidateBeforeCall(fqdn, subdomainFqdn, _callback);
        Type localVarReturnType = new TypeToken<AddSubdomain201Response>(){}.getType();
        localVarApiClient.executeAsync(localVarCall, localVarReturnType, _callback);
        return localVarCall;
    }
    /**
     * Build call for checkDomain
     * @param fqdn Полное имя домена. (required)
     * @param _callback Callback for upload/download progress
     * @return Call to execute
     * @throws ApiException If fail to serialize the request body object
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> Ответ будет представлять собой объект JSON c ключом &#x60;is_domain_available&#x60;. </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Некорректный запрос </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Не авторизован </td><td>  -  </td></tr>
        <tr><td> 429 </td><td> Слишком много запросов </td><td>  -  </td></tr>
        <tr><td> 500 </td><td> Внутренняя ошибка сервера </td><td>  -  </td></tr>
     </table>
     */
    public okhttp3.Call checkDomainCall(String fqdn, final ApiCallback _callback) throws ApiException {
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
        String localVarPath = "/api/v1/check-domain/{fqdn}"
            .replace("{" + "fqdn" + "}", localVarApiClient.escapeString(fqdn.toString()));

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
    private okhttp3.Call checkDomainValidateBeforeCall(String fqdn, final ApiCallback _callback) throws ApiException {
        // verify the required parameter 'fqdn' is set
        if (fqdn == null) {
            throw new ApiException("Missing the required parameter 'fqdn' when calling checkDomain(Async)");
        }

        return checkDomainCall(fqdn, _callback);

    }

    /**
     * Проверить, доступен ли домен для регистрации
     * Чтобы проверить, доступен ли домен для регистрации, отправьте запрос GET на &#x60;/api/v1/check-domain/{fqdn}&#x60;.
     * @param fqdn Полное имя домена. (required)
     * @return CheckDomain200Response
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> Ответ будет представлять собой объект JSON c ключом &#x60;is_domain_available&#x60;. </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Некорректный запрос </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Не авторизован </td><td>  -  </td></tr>
        <tr><td> 429 </td><td> Слишком много запросов </td><td>  -  </td></tr>
        <tr><td> 500 </td><td> Внутренняя ошибка сервера </td><td>  -  </td></tr>
     </table>
     */
    public CheckDomain200Response checkDomain(String fqdn) throws ApiException {
        ApiResponse<CheckDomain200Response> localVarResp = checkDomainWithHttpInfo(fqdn);
        return localVarResp.getData();
    }

    /**
     * Проверить, доступен ли домен для регистрации
     * Чтобы проверить, доступен ли домен для регистрации, отправьте запрос GET на &#x60;/api/v1/check-domain/{fqdn}&#x60;.
     * @param fqdn Полное имя домена. (required)
     * @return ApiResponse&lt;CheckDomain200Response&gt;
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> Ответ будет представлять собой объект JSON c ключом &#x60;is_domain_available&#x60;. </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Некорректный запрос </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Не авторизован </td><td>  -  </td></tr>
        <tr><td> 429 </td><td> Слишком много запросов </td><td>  -  </td></tr>
        <tr><td> 500 </td><td> Внутренняя ошибка сервера </td><td>  -  </td></tr>
     </table>
     */
    public ApiResponse<CheckDomain200Response> checkDomainWithHttpInfo(String fqdn) throws ApiException {
        okhttp3.Call localVarCall = checkDomainValidateBeforeCall(fqdn, null);
        Type localVarReturnType = new TypeToken<CheckDomain200Response>(){}.getType();
        return localVarApiClient.execute(localVarCall, localVarReturnType);
    }

    /**
     * Проверить, доступен ли домен для регистрации (asynchronously)
     * Чтобы проверить, доступен ли домен для регистрации, отправьте запрос GET на &#x60;/api/v1/check-domain/{fqdn}&#x60;.
     * @param fqdn Полное имя домена. (required)
     * @param _callback The callback to be executed when the API call finishes
     * @return The request call
     * @throws ApiException If fail to process the API call, e.g. serializing the request body object
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> Ответ будет представлять собой объект JSON c ключом &#x60;is_domain_available&#x60;. </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Некорректный запрос </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Не авторизован </td><td>  -  </td></tr>
        <tr><td> 429 </td><td> Слишком много запросов </td><td>  -  </td></tr>
        <tr><td> 500 </td><td> Внутренняя ошибка сервера </td><td>  -  </td></tr>
     </table>
     */
    public okhttp3.Call checkDomainAsync(String fqdn, final ApiCallback<CheckDomain200Response> _callback) throws ApiException {

        okhttp3.Call localVarCall = checkDomainValidateBeforeCall(fqdn, _callback);
        Type localVarReturnType = new TypeToken<CheckDomain200Response>(){}.getType();
        localVarApiClient.executeAsync(localVarCall, localVarReturnType, _callback);
        return localVarCall;
    }
    /**
     * Build call for createDomainDNSRecord
     * @param fqdn Полное имя домена или поддомена. (required)
     * @param createDns  (required)
     * @param _callback Callback for upload/download progress
     * @return Call to execute
     * @throws ApiException If fail to serialize the request body object
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 201 </td><td> Ответ будет представлять собой объект JSON c ключом &#x60;dns_record&#x60;. </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Некорректный запрос </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Не авторизован </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Не найдено </td><td>  -  </td></tr>
        <tr><td> 429 </td><td> Слишком много запросов </td><td>  -  </td></tr>
        <tr><td> 500 </td><td> Внутренняя ошибка сервера </td><td>  -  </td></tr>
     </table>
     * @deprecated
     */
    @Deprecated
    public okhttp3.Call createDomainDNSRecordCall(String fqdn, CreateDns createDns, final ApiCallback _callback) throws ApiException {
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

        Object localVarPostBody = createDns;

        // create path and map variables
        String localVarPath = "/api/v1/domains/{fqdn}/dns-records"
            .replace("{" + "fqdn" + "}", localVarApiClient.escapeString(fqdn.toString()));

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
    private okhttp3.Call createDomainDNSRecordValidateBeforeCall(String fqdn, CreateDns createDns, final ApiCallback _callback) throws ApiException {
        // verify the required parameter 'fqdn' is set
        if (fqdn == null) {
            throw new ApiException("Missing the required parameter 'fqdn' when calling createDomainDNSRecord(Async)");
        }

        // verify the required parameter 'createDns' is set
        if (createDns == null) {
            throw new ApiException("Missing the required parameter 'createDns' when calling createDomainDNSRecord(Async)");
        }

        return createDomainDNSRecordCall(fqdn, createDns, _callback);

    }

    /**
     * Добавить информацию о DNS-записи для домена или поддомена
     * Чтобы добавить информацию о DNS-записи для домена или поддомена, отправьте запрос POST на &#x60;/api/v1/domains/{fqdn}/dns-records&#x60;, задав необходимые атрибуты.  DNS-запись будет добавлена с использованием предоставленной информации. Тело ответа будет содержать объект JSON с информацией о добавленной DNS-записи.
     * @param fqdn Полное имя домена или поддомена. (required)
     * @param createDns  (required)
     * @return CreateDomainDNSRecord201Response
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 201 </td><td> Ответ будет представлять собой объект JSON c ключом &#x60;dns_record&#x60;. </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Некорректный запрос </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Не авторизован </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Не найдено </td><td>  -  </td></tr>
        <tr><td> 429 </td><td> Слишком много запросов </td><td>  -  </td></tr>
        <tr><td> 500 </td><td> Внутренняя ошибка сервера </td><td>  -  </td></tr>
     </table>
     * @deprecated
     */
    @Deprecated
    public CreateDomainDNSRecord201Response createDomainDNSRecord(String fqdn, CreateDns createDns) throws ApiException {
        ApiResponse<CreateDomainDNSRecord201Response> localVarResp = createDomainDNSRecordWithHttpInfo(fqdn, createDns);
        return localVarResp.getData();
    }

    /**
     * Добавить информацию о DNS-записи для домена или поддомена
     * Чтобы добавить информацию о DNS-записи для домена или поддомена, отправьте запрос POST на &#x60;/api/v1/domains/{fqdn}/dns-records&#x60;, задав необходимые атрибуты.  DNS-запись будет добавлена с использованием предоставленной информации. Тело ответа будет содержать объект JSON с информацией о добавленной DNS-записи.
     * @param fqdn Полное имя домена или поддомена. (required)
     * @param createDns  (required)
     * @return ApiResponse&lt;CreateDomainDNSRecord201Response&gt;
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 201 </td><td> Ответ будет представлять собой объект JSON c ключом &#x60;dns_record&#x60;. </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Некорректный запрос </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Не авторизован </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Не найдено </td><td>  -  </td></tr>
        <tr><td> 429 </td><td> Слишком много запросов </td><td>  -  </td></tr>
        <tr><td> 500 </td><td> Внутренняя ошибка сервера </td><td>  -  </td></tr>
     </table>
     * @deprecated
     */
    @Deprecated
    public ApiResponse<CreateDomainDNSRecord201Response> createDomainDNSRecordWithHttpInfo(String fqdn, CreateDns createDns) throws ApiException {
        okhttp3.Call localVarCall = createDomainDNSRecordValidateBeforeCall(fqdn, createDns, null);
        Type localVarReturnType = new TypeToken<CreateDomainDNSRecord201Response>(){}.getType();
        return localVarApiClient.execute(localVarCall, localVarReturnType);
    }

    /**
     * Добавить информацию о DNS-записи для домена или поддомена (asynchronously)
     * Чтобы добавить информацию о DNS-записи для домена или поддомена, отправьте запрос POST на &#x60;/api/v1/domains/{fqdn}/dns-records&#x60;, задав необходимые атрибуты.  DNS-запись будет добавлена с использованием предоставленной информации. Тело ответа будет содержать объект JSON с информацией о добавленной DNS-записи.
     * @param fqdn Полное имя домена или поддомена. (required)
     * @param createDns  (required)
     * @param _callback The callback to be executed when the API call finishes
     * @return The request call
     * @throws ApiException If fail to process the API call, e.g. serializing the request body object
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 201 </td><td> Ответ будет представлять собой объект JSON c ключом &#x60;dns_record&#x60;. </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Некорректный запрос </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Не авторизован </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Не найдено </td><td>  -  </td></tr>
        <tr><td> 429 </td><td> Слишком много запросов </td><td>  -  </td></tr>
        <tr><td> 500 </td><td> Внутренняя ошибка сервера </td><td>  -  </td></tr>
     </table>
     * @deprecated
     */
    @Deprecated
    public okhttp3.Call createDomainDNSRecordAsync(String fqdn, CreateDns createDns, final ApiCallback<CreateDomainDNSRecord201Response> _callback) throws ApiException {

        okhttp3.Call localVarCall = createDomainDNSRecordValidateBeforeCall(fqdn, createDns, _callback);
        Type localVarReturnType = new TypeToken<CreateDomainDNSRecord201Response>(){}.getType();
        localVarApiClient.executeAsync(localVarCall, localVarReturnType, _callback);
        return localVarCall;
    }
    /**
     * Build call for createDomainDNSRecordV2
     * @param fqdn Полное имя домена или поддомена. Для создания записи на основном домене передайте имя домена (например, &#x60;somedomain.ru&#x60;). Для создания записи на поддомене передайте полное доменное имя включая поддомен (например, &#x60;sub.somedomain.ru&#x60;). (required)
     * @param createDnsV2  (required)
     * @param _callback Callback for upload/download progress
     * @return Call to execute
     * @throws ApiException If fail to serialize the request body object
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 201 </td><td> Ответ будет представлять собой объект JSON c ключом &#x60;dns_record&#x60;. </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Некорректный запрос </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Не авторизован </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Не найдено </td><td>  -  </td></tr>
        <tr><td> 409 </td><td> Конфликт </td><td>  -  </td></tr>
        <tr><td> 429 </td><td> Слишком много запросов </td><td>  -  </td></tr>
        <tr><td> 500 </td><td> Внутренняя ошибка сервера </td><td>  -  </td></tr>
     </table>
     */
    public okhttp3.Call createDomainDNSRecordV2Call(String fqdn, CreateDnsV2 createDnsV2, final ApiCallback _callback) throws ApiException {
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

        Object localVarPostBody = createDnsV2;

        // create path and map variables
        String localVarPath = "/api/v2/domains/{fqdn}/dns-records"
            .replace("{" + "fqdn" + "}", localVarApiClient.escapeString(fqdn.toString()));

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
    private okhttp3.Call createDomainDNSRecordV2ValidateBeforeCall(String fqdn, CreateDnsV2 createDnsV2, final ApiCallback _callback) throws ApiException {
        // verify the required parameter 'fqdn' is set
        if (fqdn == null) {
            throw new ApiException("Missing the required parameter 'fqdn' when calling createDomainDNSRecordV2(Async)");
        }

        // verify the required parameter 'createDnsV2' is set
        if (createDnsV2 == null) {
            throw new ApiException("Missing the required parameter 'createDnsV2' when calling createDomainDNSRecordV2(Async)");
        }

        return createDomainDNSRecordV2Call(fqdn, createDnsV2, _callback);

    }

    /**
     * Добавить информацию о DNS-записи для домена или поддомена
     * Чтобы добавить информацию о DNS-записи для домена или поддомена, отправьте запрос POST на &#x60;/api/v2/domains/{fqdn}/dns-records&#x60;, задав необходимые атрибуты.  DNS-запись будет добавлена с использованием предоставленной информации. Тело ответа будет содержать объект JSON с информацией о добавленной DNS-записи.
     * @param fqdn Полное имя домена или поддомена. Для создания записи на основном домене передайте имя домена (например, &#x60;somedomain.ru&#x60;). Для создания записи на поддомене передайте полное доменное имя включая поддомен (например, &#x60;sub.somedomain.ru&#x60;). (required)
     * @param createDnsV2  (required)
     * @return CreateDomainDNSRecordV2201Response
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 201 </td><td> Ответ будет представлять собой объект JSON c ключом &#x60;dns_record&#x60;. </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Некорректный запрос </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Не авторизован </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Не найдено </td><td>  -  </td></tr>
        <tr><td> 409 </td><td> Конфликт </td><td>  -  </td></tr>
        <tr><td> 429 </td><td> Слишком много запросов </td><td>  -  </td></tr>
        <tr><td> 500 </td><td> Внутренняя ошибка сервера </td><td>  -  </td></tr>
     </table>
     */
    public CreateDomainDNSRecordV2201Response createDomainDNSRecordV2(String fqdn, CreateDnsV2 createDnsV2) throws ApiException {
        ApiResponse<CreateDomainDNSRecordV2201Response> localVarResp = createDomainDNSRecordV2WithHttpInfo(fqdn, createDnsV2);
        return localVarResp.getData();
    }

    /**
     * Добавить информацию о DNS-записи для домена или поддомена
     * Чтобы добавить информацию о DNS-записи для домена или поддомена, отправьте запрос POST на &#x60;/api/v2/domains/{fqdn}/dns-records&#x60;, задав необходимые атрибуты.  DNS-запись будет добавлена с использованием предоставленной информации. Тело ответа будет содержать объект JSON с информацией о добавленной DNS-записи.
     * @param fqdn Полное имя домена или поддомена. Для создания записи на основном домене передайте имя домена (например, &#x60;somedomain.ru&#x60;). Для создания записи на поддомене передайте полное доменное имя включая поддомен (например, &#x60;sub.somedomain.ru&#x60;). (required)
     * @param createDnsV2  (required)
     * @return ApiResponse&lt;CreateDomainDNSRecordV2201Response&gt;
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 201 </td><td> Ответ будет представлять собой объект JSON c ключом &#x60;dns_record&#x60;. </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Некорректный запрос </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Не авторизован </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Не найдено </td><td>  -  </td></tr>
        <tr><td> 409 </td><td> Конфликт </td><td>  -  </td></tr>
        <tr><td> 429 </td><td> Слишком много запросов </td><td>  -  </td></tr>
        <tr><td> 500 </td><td> Внутренняя ошибка сервера </td><td>  -  </td></tr>
     </table>
     */
    public ApiResponse<CreateDomainDNSRecordV2201Response> createDomainDNSRecordV2WithHttpInfo(String fqdn, CreateDnsV2 createDnsV2) throws ApiException {
        okhttp3.Call localVarCall = createDomainDNSRecordV2ValidateBeforeCall(fqdn, createDnsV2, null);
        Type localVarReturnType = new TypeToken<CreateDomainDNSRecordV2201Response>(){}.getType();
        return localVarApiClient.execute(localVarCall, localVarReturnType);
    }

    /**
     * Добавить информацию о DNS-записи для домена или поддомена (asynchronously)
     * Чтобы добавить информацию о DNS-записи для домена или поддомена, отправьте запрос POST на &#x60;/api/v2/domains/{fqdn}/dns-records&#x60;, задав необходимые атрибуты.  DNS-запись будет добавлена с использованием предоставленной информации. Тело ответа будет содержать объект JSON с информацией о добавленной DNS-записи.
     * @param fqdn Полное имя домена или поддомена. Для создания записи на основном домене передайте имя домена (например, &#x60;somedomain.ru&#x60;). Для создания записи на поддомене передайте полное доменное имя включая поддомен (например, &#x60;sub.somedomain.ru&#x60;). (required)
     * @param createDnsV2  (required)
     * @param _callback The callback to be executed when the API call finishes
     * @return The request call
     * @throws ApiException If fail to process the API call, e.g. serializing the request body object
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 201 </td><td> Ответ будет представлять собой объект JSON c ключом &#x60;dns_record&#x60;. </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Некорректный запрос </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Не авторизован </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Не найдено </td><td>  -  </td></tr>
        <tr><td> 409 </td><td> Конфликт </td><td>  -  </td></tr>
        <tr><td> 429 </td><td> Слишком много запросов </td><td>  -  </td></tr>
        <tr><td> 500 </td><td> Внутренняя ошибка сервера </td><td>  -  </td></tr>
     </table>
     */
    public okhttp3.Call createDomainDNSRecordV2Async(String fqdn, CreateDnsV2 createDnsV2, final ApiCallback<CreateDomainDNSRecordV2201Response> _callback) throws ApiException {

        okhttp3.Call localVarCall = createDomainDNSRecordV2ValidateBeforeCall(fqdn, createDnsV2, _callback);
        Type localVarReturnType = new TypeToken<CreateDomainDNSRecordV2201Response>(){}.getType();
        localVarApiClient.executeAsync(localVarCall, localVarReturnType, _callback);
        return localVarCall;
    }
    /**
     * Build call for createDomainRequest
     * @param domainRegister  (required)
     * @param _callback Callback for upload/download progress
     * @return Call to execute
     * @throws ApiException If fail to serialize the request body object
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 201 </td><td> Ответ будет представлять собой объект JSON c ключом &#x60;request&#x60;. </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Некорректный запрос </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Не авторизован </td><td>  -  </td></tr>
        <tr><td> 409 </td><td> Конфликт </td><td>  -  </td></tr>
        <tr><td> 429 </td><td> Слишком много запросов </td><td>  -  </td></tr>
        <tr><td> 500 </td><td> Внутренняя ошибка сервера </td><td>  -  </td></tr>
     </table>
     */
    public okhttp3.Call createDomainRequestCall(DomainRegister domainRegister, final ApiCallback _callback) throws ApiException {
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

        Object localVarPostBody = domainRegister;

        // create path and map variables
        String localVarPath = "/api/v1/domains-requests";

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
    private okhttp3.Call createDomainRequestValidateBeforeCall(DomainRegister domainRegister, final ApiCallback _callback) throws ApiException {
        // verify the required parameter 'domainRegister' is set
        if (domainRegister == null) {
            throw new ApiException("Missing the required parameter 'domainRegister' when calling createDomainRequest(Async)");
        }

        return createDomainRequestCall(domainRegister, _callback);

    }

    /**
     * Создание заявки на регистрацию/продление/трансфер домена
     * Чтобы создать заявку на регистрацию/продление/трансфер домена, отправьте POST-запрос в &#x60;api/v1/domains-requests&#x60;, задав необходимые атрибуты.  Заявка будет создана с использованием предоставленной информации. Тело ответа будет содержать объект JSON с информацией о созданной заявке.
     * @param domainRegister  (required)
     * @return CreateDomainRequest201Response
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 201 </td><td> Ответ будет представлять собой объект JSON c ключом &#x60;request&#x60;. </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Некорректный запрос </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Не авторизован </td><td>  -  </td></tr>
        <tr><td> 409 </td><td> Конфликт </td><td>  -  </td></tr>
        <tr><td> 429 </td><td> Слишком много запросов </td><td>  -  </td></tr>
        <tr><td> 500 </td><td> Внутренняя ошибка сервера </td><td>  -  </td></tr>
     </table>
     */
    public CreateDomainRequest201Response createDomainRequest(DomainRegister domainRegister) throws ApiException {
        ApiResponse<CreateDomainRequest201Response> localVarResp = createDomainRequestWithHttpInfo(domainRegister);
        return localVarResp.getData();
    }

    /**
     * Создание заявки на регистрацию/продление/трансфер домена
     * Чтобы создать заявку на регистрацию/продление/трансфер домена, отправьте POST-запрос в &#x60;api/v1/domains-requests&#x60;, задав необходимые атрибуты.  Заявка будет создана с использованием предоставленной информации. Тело ответа будет содержать объект JSON с информацией о созданной заявке.
     * @param domainRegister  (required)
     * @return ApiResponse&lt;CreateDomainRequest201Response&gt;
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 201 </td><td> Ответ будет представлять собой объект JSON c ключом &#x60;request&#x60;. </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Некорректный запрос </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Не авторизован </td><td>  -  </td></tr>
        <tr><td> 409 </td><td> Конфликт </td><td>  -  </td></tr>
        <tr><td> 429 </td><td> Слишком много запросов </td><td>  -  </td></tr>
        <tr><td> 500 </td><td> Внутренняя ошибка сервера </td><td>  -  </td></tr>
     </table>
     */
    public ApiResponse<CreateDomainRequest201Response> createDomainRequestWithHttpInfo(DomainRegister domainRegister) throws ApiException {
        okhttp3.Call localVarCall = createDomainRequestValidateBeforeCall(domainRegister, null);
        Type localVarReturnType = new TypeToken<CreateDomainRequest201Response>(){}.getType();
        return localVarApiClient.execute(localVarCall, localVarReturnType);
    }

    /**
     * Создание заявки на регистрацию/продление/трансфер домена (asynchronously)
     * Чтобы создать заявку на регистрацию/продление/трансфер домена, отправьте POST-запрос в &#x60;api/v1/domains-requests&#x60;, задав необходимые атрибуты.  Заявка будет создана с использованием предоставленной информации. Тело ответа будет содержать объект JSON с информацией о созданной заявке.
     * @param domainRegister  (required)
     * @param _callback The callback to be executed when the API call finishes
     * @return The request call
     * @throws ApiException If fail to process the API call, e.g. serializing the request body object
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 201 </td><td> Ответ будет представлять собой объект JSON c ключом &#x60;request&#x60;. </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Некорректный запрос </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Не авторизован </td><td>  -  </td></tr>
        <tr><td> 409 </td><td> Конфликт </td><td>  -  </td></tr>
        <tr><td> 429 </td><td> Слишком много запросов </td><td>  -  </td></tr>
        <tr><td> 500 </td><td> Внутренняя ошибка сервера </td><td>  -  </td></tr>
     </table>
     */
    public okhttp3.Call createDomainRequestAsync(DomainRegister domainRegister, final ApiCallback<CreateDomainRequest201Response> _callback) throws ApiException {

        okhttp3.Call localVarCall = createDomainRequestValidateBeforeCall(domainRegister, _callback);
        Type localVarReturnType = new TypeToken<CreateDomainRequest201Response>(){}.getType();
        localVarApiClient.executeAsync(localVarCall, localVarReturnType, _callback);
        return localVarCall;
    }
    /**
     * Build call for deleteDomain
     * @param fqdn Полное имя домена. (required)
     * @param _callback Callback for upload/download progress
     * @return Call to execute
     * @throws ApiException If fail to serialize the request body object
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 204 </td><td> Домен успешно удален. </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Некорректный запрос </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Не авторизован </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Не найдено </td><td>  -  </td></tr>
        <tr><td> 409 </td><td> Конфликт </td><td>  -  </td></tr>
        <tr><td> 429 </td><td> Слишком много запросов </td><td>  -  </td></tr>
        <tr><td> 500 </td><td> Внутренняя ошибка сервера </td><td>  -  </td></tr>
     </table>
     */
    public okhttp3.Call deleteDomainCall(String fqdn, final ApiCallback _callback) throws ApiException {
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
        String localVarPath = "/api/v1/domains/{fqdn}"
            .replace("{" + "fqdn" + "}", localVarApiClient.escapeString(fqdn.toString()));

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
    private okhttp3.Call deleteDomainValidateBeforeCall(String fqdn, final ApiCallback _callback) throws ApiException {
        // verify the required parameter 'fqdn' is set
        if (fqdn == null) {
            throw new ApiException("Missing the required parameter 'fqdn' when calling deleteDomain(Async)");
        }

        return deleteDomainCall(fqdn, _callback);

    }

    /**
     * Удаление домена
     * Чтобы удалить домен, отправьте запрос DELETE на &#x60;/api/v1/domains/{fqdn}&#x60;.
     * @param fqdn Полное имя домена. (required)
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 204 </td><td> Домен успешно удален. </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Некорректный запрос </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Не авторизован </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Не найдено </td><td>  -  </td></tr>
        <tr><td> 409 </td><td> Конфликт </td><td>  -  </td></tr>
        <tr><td> 429 </td><td> Слишком много запросов </td><td>  -  </td></tr>
        <tr><td> 500 </td><td> Внутренняя ошибка сервера </td><td>  -  </td></tr>
     </table>
     */
    public void deleteDomain(String fqdn) throws ApiException {
        deleteDomainWithHttpInfo(fqdn);
    }

    /**
     * Удаление домена
     * Чтобы удалить домен, отправьте запрос DELETE на &#x60;/api/v1/domains/{fqdn}&#x60;.
     * @param fqdn Полное имя домена. (required)
     * @return ApiResponse&lt;Void&gt;
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 204 </td><td> Домен успешно удален. </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Некорректный запрос </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Не авторизован </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Не найдено </td><td>  -  </td></tr>
        <tr><td> 409 </td><td> Конфликт </td><td>  -  </td></tr>
        <tr><td> 429 </td><td> Слишком много запросов </td><td>  -  </td></tr>
        <tr><td> 500 </td><td> Внутренняя ошибка сервера </td><td>  -  </td></tr>
     </table>
     */
    public ApiResponse<Void> deleteDomainWithHttpInfo(String fqdn) throws ApiException {
        okhttp3.Call localVarCall = deleteDomainValidateBeforeCall(fqdn, null);
        return localVarApiClient.execute(localVarCall);
    }

    /**
     * Удаление домена (asynchronously)
     * Чтобы удалить домен, отправьте запрос DELETE на &#x60;/api/v1/domains/{fqdn}&#x60;.
     * @param fqdn Полное имя домена. (required)
     * @param _callback The callback to be executed when the API call finishes
     * @return The request call
     * @throws ApiException If fail to process the API call, e.g. serializing the request body object
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 204 </td><td> Домен успешно удален. </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Некорректный запрос </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Не авторизован </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Не найдено </td><td>  -  </td></tr>
        <tr><td> 409 </td><td> Конфликт </td><td>  -  </td></tr>
        <tr><td> 429 </td><td> Слишком много запросов </td><td>  -  </td></tr>
        <tr><td> 500 </td><td> Внутренняя ошибка сервера </td><td>  -  </td></tr>
     </table>
     */
    public okhttp3.Call deleteDomainAsync(String fqdn, final ApiCallback<Void> _callback) throws ApiException {

        okhttp3.Call localVarCall = deleteDomainValidateBeforeCall(fqdn, _callback);
        localVarApiClient.executeAsync(localVarCall, _callback);
        return localVarCall;
    }
    /**
     * Build call for deleteDomainDNSRecord
     * @param fqdn Полное имя домена или поддомена. (required)
     * @param recordId ID DNS-записи домена или поддомена. (required)
     * @param _callback Callback for upload/download progress
     * @return Call to execute
     * @throws ApiException If fail to serialize the request body object
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 204 </td><td> Информация о DNS-записи успешно удалена. </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Некорректный запрос </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Не авторизован </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Не найдено </td><td>  -  </td></tr>
        <tr><td> 429 </td><td> Слишком много запросов </td><td>  -  </td></tr>
        <tr><td> 500 </td><td> Внутренняя ошибка сервера </td><td>  -  </td></tr>
     </table>
     * @deprecated
     */
    @Deprecated
    public okhttp3.Call deleteDomainDNSRecordCall(String fqdn, Integer recordId, final ApiCallback _callback) throws ApiException {
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
        String localVarPath = "/api/v1/domains/{fqdn}/dns-records/{record_id}"
            .replace("{" + "fqdn" + "}", localVarApiClient.escapeString(fqdn.toString()))
            .replace("{" + "record_id" + "}", localVarApiClient.escapeString(recordId.toString()));

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

    @Deprecated
    @SuppressWarnings("rawtypes")
    private okhttp3.Call deleteDomainDNSRecordValidateBeforeCall(String fqdn, Integer recordId, final ApiCallback _callback) throws ApiException {
        // verify the required parameter 'fqdn' is set
        if (fqdn == null) {
            throw new ApiException("Missing the required parameter 'fqdn' when calling deleteDomainDNSRecord(Async)");
        }

        // verify the required parameter 'recordId' is set
        if (recordId == null) {
            throw new ApiException("Missing the required parameter 'recordId' when calling deleteDomainDNSRecord(Async)");
        }

        return deleteDomainDNSRecordCall(fqdn, recordId, _callback);

    }

    /**
     * Удалить информацию о DNS-записи для домена или поддомена
     * Чтобы удалить информацию о DNS-записи для домена или поддомена, отправьте запрос DELETE на &#x60;/api/v1/domains/{fqdn}/dns-records/{record_id}&#x60;.
     * @param fqdn Полное имя домена или поддомена. (required)
     * @param recordId ID DNS-записи домена или поддомена. (required)
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 204 </td><td> Информация о DNS-записи успешно удалена. </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Некорректный запрос </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Не авторизован </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Не найдено </td><td>  -  </td></tr>
        <tr><td> 429 </td><td> Слишком много запросов </td><td>  -  </td></tr>
        <tr><td> 500 </td><td> Внутренняя ошибка сервера </td><td>  -  </td></tr>
     </table>
     * @deprecated
     */
    @Deprecated
    public void deleteDomainDNSRecord(String fqdn, Integer recordId) throws ApiException {
        deleteDomainDNSRecordWithHttpInfo(fqdn, recordId);
    }

    /**
     * Удалить информацию о DNS-записи для домена или поддомена
     * Чтобы удалить информацию о DNS-записи для домена или поддомена, отправьте запрос DELETE на &#x60;/api/v1/domains/{fqdn}/dns-records/{record_id}&#x60;.
     * @param fqdn Полное имя домена или поддомена. (required)
     * @param recordId ID DNS-записи домена или поддомена. (required)
     * @return ApiResponse&lt;Void&gt;
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 204 </td><td> Информация о DNS-записи успешно удалена. </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Некорректный запрос </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Не авторизован </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Не найдено </td><td>  -  </td></tr>
        <tr><td> 429 </td><td> Слишком много запросов </td><td>  -  </td></tr>
        <tr><td> 500 </td><td> Внутренняя ошибка сервера </td><td>  -  </td></tr>
     </table>
     * @deprecated
     */
    @Deprecated
    public ApiResponse<Void> deleteDomainDNSRecordWithHttpInfo(String fqdn, Integer recordId) throws ApiException {
        okhttp3.Call localVarCall = deleteDomainDNSRecordValidateBeforeCall(fqdn, recordId, null);
        return localVarApiClient.execute(localVarCall);
    }

    /**
     * Удалить информацию о DNS-записи для домена или поддомена (asynchronously)
     * Чтобы удалить информацию о DNS-записи для домена или поддомена, отправьте запрос DELETE на &#x60;/api/v1/domains/{fqdn}/dns-records/{record_id}&#x60;.
     * @param fqdn Полное имя домена или поддомена. (required)
     * @param recordId ID DNS-записи домена или поддомена. (required)
     * @param _callback The callback to be executed when the API call finishes
     * @return The request call
     * @throws ApiException If fail to process the API call, e.g. serializing the request body object
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 204 </td><td> Информация о DNS-записи успешно удалена. </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Некорректный запрос </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Не авторизован </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Не найдено </td><td>  -  </td></tr>
        <tr><td> 429 </td><td> Слишком много запросов </td><td>  -  </td></tr>
        <tr><td> 500 </td><td> Внутренняя ошибка сервера </td><td>  -  </td></tr>
     </table>
     * @deprecated
     */
    @Deprecated
    public okhttp3.Call deleteDomainDNSRecordAsync(String fqdn, Integer recordId, final ApiCallback<Void> _callback) throws ApiException {

        okhttp3.Call localVarCall = deleteDomainDNSRecordValidateBeforeCall(fqdn, recordId, _callback);
        localVarApiClient.executeAsync(localVarCall, _callback);
        return localVarCall;
    }
    /**
     * Build call for deleteDomainDNSRecordV2
     * @param fqdn Полное имя домена или поддомена. Для создания записи на основном домене передайте имя домена (например, &#x60;somedomain.ru&#x60;). Для создания записи на поддомене передайте полное доменное имя включая поддомен (например, &#x60;sub.somedomain.ru&#x60;). (required)
     * @param recordId ID DNS-записи домена или поддомена. (required)
     * @param _callback Callback for upload/download progress
     * @return Call to execute
     * @throws ApiException If fail to serialize the request body object
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 204 </td><td> Информация о DNS-записи успешно удалена. </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Некорректный запрос </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Не авторизован </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Не найдено </td><td>  -  </td></tr>
        <tr><td> 429 </td><td> Слишком много запросов </td><td>  -  </td></tr>
        <tr><td> 500 </td><td> Внутренняя ошибка сервера </td><td>  -  </td></tr>
     </table>
     */
    public okhttp3.Call deleteDomainDNSRecordV2Call(String fqdn, Integer recordId, final ApiCallback _callback) throws ApiException {
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
        String localVarPath = "/api/v2/domains/{fqdn}/dns-records/{record_id}"
            .replace("{" + "fqdn" + "}", localVarApiClient.escapeString(fqdn.toString()))
            .replace("{" + "record_id" + "}", localVarApiClient.escapeString(recordId.toString()));

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
    private okhttp3.Call deleteDomainDNSRecordV2ValidateBeforeCall(String fqdn, Integer recordId, final ApiCallback _callback) throws ApiException {
        // verify the required parameter 'fqdn' is set
        if (fqdn == null) {
            throw new ApiException("Missing the required parameter 'fqdn' when calling deleteDomainDNSRecordV2(Async)");
        }

        // verify the required parameter 'recordId' is set
        if (recordId == null) {
            throw new ApiException("Missing the required parameter 'recordId' when calling deleteDomainDNSRecordV2(Async)");
        }

        return deleteDomainDNSRecordV2Call(fqdn, recordId, _callback);

    }

    /**
     * Удалить информацию о DNS-записи для домена или поддомена
     * Чтобы удалить информацию о DNS-записи для домена или поддомена, отправьте запрос DELETE на &#x60;/api/v2/domains/{fqdn}/dns-records/{record_id}&#x60;.
     * @param fqdn Полное имя домена или поддомена. Для создания записи на основном домене передайте имя домена (например, &#x60;somedomain.ru&#x60;). Для создания записи на поддомене передайте полное доменное имя включая поддомен (например, &#x60;sub.somedomain.ru&#x60;). (required)
     * @param recordId ID DNS-записи домена или поддомена. (required)
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 204 </td><td> Информация о DNS-записи успешно удалена. </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Некорректный запрос </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Не авторизован </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Не найдено </td><td>  -  </td></tr>
        <tr><td> 429 </td><td> Слишком много запросов </td><td>  -  </td></tr>
        <tr><td> 500 </td><td> Внутренняя ошибка сервера </td><td>  -  </td></tr>
     </table>
     */
    public void deleteDomainDNSRecordV2(String fqdn, Integer recordId) throws ApiException {
        deleteDomainDNSRecordV2WithHttpInfo(fqdn, recordId);
    }

    /**
     * Удалить информацию о DNS-записи для домена или поддомена
     * Чтобы удалить информацию о DNS-записи для домена или поддомена, отправьте запрос DELETE на &#x60;/api/v2/domains/{fqdn}/dns-records/{record_id}&#x60;.
     * @param fqdn Полное имя домена или поддомена. Для создания записи на основном домене передайте имя домена (например, &#x60;somedomain.ru&#x60;). Для создания записи на поддомене передайте полное доменное имя включая поддомен (например, &#x60;sub.somedomain.ru&#x60;). (required)
     * @param recordId ID DNS-записи домена или поддомена. (required)
     * @return ApiResponse&lt;Void&gt;
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 204 </td><td> Информация о DNS-записи успешно удалена. </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Некорректный запрос </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Не авторизован </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Не найдено </td><td>  -  </td></tr>
        <tr><td> 429 </td><td> Слишком много запросов </td><td>  -  </td></tr>
        <tr><td> 500 </td><td> Внутренняя ошибка сервера </td><td>  -  </td></tr>
     </table>
     */
    public ApiResponse<Void> deleteDomainDNSRecordV2WithHttpInfo(String fqdn, Integer recordId) throws ApiException {
        okhttp3.Call localVarCall = deleteDomainDNSRecordV2ValidateBeforeCall(fqdn, recordId, null);
        return localVarApiClient.execute(localVarCall);
    }

    /**
     * Удалить информацию о DNS-записи для домена или поддомена (asynchronously)
     * Чтобы удалить информацию о DNS-записи для домена или поддомена, отправьте запрос DELETE на &#x60;/api/v2/domains/{fqdn}/dns-records/{record_id}&#x60;.
     * @param fqdn Полное имя домена или поддомена. Для создания записи на основном домене передайте имя домена (например, &#x60;somedomain.ru&#x60;). Для создания записи на поддомене передайте полное доменное имя включая поддомен (например, &#x60;sub.somedomain.ru&#x60;). (required)
     * @param recordId ID DNS-записи домена или поддомена. (required)
     * @param _callback The callback to be executed when the API call finishes
     * @return The request call
     * @throws ApiException If fail to process the API call, e.g. serializing the request body object
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 204 </td><td> Информация о DNS-записи успешно удалена. </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Некорректный запрос </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Не авторизован </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Не найдено </td><td>  -  </td></tr>
        <tr><td> 429 </td><td> Слишком много запросов </td><td>  -  </td></tr>
        <tr><td> 500 </td><td> Внутренняя ошибка сервера </td><td>  -  </td></tr>
     </table>
     */
    public okhttp3.Call deleteDomainDNSRecordV2Async(String fqdn, Integer recordId, final ApiCallback<Void> _callback) throws ApiException {

        okhttp3.Call localVarCall = deleteDomainDNSRecordV2ValidateBeforeCall(fqdn, recordId, _callback);
        localVarApiClient.executeAsync(localVarCall, _callback);
        return localVarCall;
    }
    /**
     * Build call for deleteSubdomain
     * @param fqdn Полное имя домена. (required)
     * @param subdomainFqdn Полное имя поддомена. (required)
     * @param _callback Callback for upload/download progress
     * @return Call to execute
     * @throws ApiException If fail to serialize the request body object
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 204 </td><td> Поддомен успешно удален </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Некорректный запрос </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Не авторизован </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Не найдено </td><td>  -  </td></tr>
        <tr><td> 409 </td><td> Конфликт </td><td>  -  </td></tr>
        <tr><td> 429 </td><td> Слишком много запросов </td><td>  -  </td></tr>
        <tr><td> 500 </td><td> Внутренняя ошибка сервера </td><td>  -  </td></tr>
     </table>
     */
    public okhttp3.Call deleteSubdomainCall(String fqdn, String subdomainFqdn, final ApiCallback _callback) throws ApiException {
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
        String localVarPath = "/api/v1/domains/{fqdn}/subdomains/{subdomain_fqdn}"
            .replace("{" + "fqdn" + "}", localVarApiClient.escapeString(fqdn.toString()))
            .replace("{" + "subdomain_fqdn" + "}", localVarApiClient.escapeString(subdomainFqdn.toString()));

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
    private okhttp3.Call deleteSubdomainValidateBeforeCall(String fqdn, String subdomainFqdn, final ApiCallback _callback) throws ApiException {
        // verify the required parameter 'fqdn' is set
        if (fqdn == null) {
            throw new ApiException("Missing the required parameter 'fqdn' when calling deleteSubdomain(Async)");
        }

        // verify the required parameter 'subdomainFqdn' is set
        if (subdomainFqdn == null) {
            throw new ApiException("Missing the required parameter 'subdomainFqdn' when calling deleteSubdomain(Async)");
        }

        return deleteSubdomainCall(fqdn, subdomainFqdn, _callback);

    }

    /**
     * Удаление поддомена
     * Чтобы удалить поддомен, отправьте запрос DELETE на &#x60;/api/v1/domains/{fqdn}/subdomains/{subdomain_fqdn}&#x60;.
     * @param fqdn Полное имя домена. (required)
     * @param subdomainFqdn Полное имя поддомена. (required)
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 204 </td><td> Поддомен успешно удален </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Некорректный запрос </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Не авторизован </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Не найдено </td><td>  -  </td></tr>
        <tr><td> 409 </td><td> Конфликт </td><td>  -  </td></tr>
        <tr><td> 429 </td><td> Слишком много запросов </td><td>  -  </td></tr>
        <tr><td> 500 </td><td> Внутренняя ошибка сервера </td><td>  -  </td></tr>
     </table>
     */
    public void deleteSubdomain(String fqdn, String subdomainFqdn) throws ApiException {
        deleteSubdomainWithHttpInfo(fqdn, subdomainFqdn);
    }

    /**
     * Удаление поддомена
     * Чтобы удалить поддомен, отправьте запрос DELETE на &#x60;/api/v1/domains/{fqdn}/subdomains/{subdomain_fqdn}&#x60;.
     * @param fqdn Полное имя домена. (required)
     * @param subdomainFqdn Полное имя поддомена. (required)
     * @return ApiResponse&lt;Void&gt;
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 204 </td><td> Поддомен успешно удален </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Некорректный запрос </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Не авторизован </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Не найдено </td><td>  -  </td></tr>
        <tr><td> 409 </td><td> Конфликт </td><td>  -  </td></tr>
        <tr><td> 429 </td><td> Слишком много запросов </td><td>  -  </td></tr>
        <tr><td> 500 </td><td> Внутренняя ошибка сервера </td><td>  -  </td></tr>
     </table>
     */
    public ApiResponse<Void> deleteSubdomainWithHttpInfo(String fqdn, String subdomainFqdn) throws ApiException {
        okhttp3.Call localVarCall = deleteSubdomainValidateBeforeCall(fqdn, subdomainFqdn, null);
        return localVarApiClient.execute(localVarCall);
    }

    /**
     * Удаление поддомена (asynchronously)
     * Чтобы удалить поддомен, отправьте запрос DELETE на &#x60;/api/v1/domains/{fqdn}/subdomains/{subdomain_fqdn}&#x60;.
     * @param fqdn Полное имя домена. (required)
     * @param subdomainFqdn Полное имя поддомена. (required)
     * @param _callback The callback to be executed when the API call finishes
     * @return The request call
     * @throws ApiException If fail to process the API call, e.g. serializing the request body object
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 204 </td><td> Поддомен успешно удален </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Некорректный запрос </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Не авторизован </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Не найдено </td><td>  -  </td></tr>
        <tr><td> 409 </td><td> Конфликт </td><td>  -  </td></tr>
        <tr><td> 429 </td><td> Слишком много запросов </td><td>  -  </td></tr>
        <tr><td> 500 </td><td> Внутренняя ошибка сервера </td><td>  -  </td></tr>
     </table>
     */
    public okhttp3.Call deleteSubdomainAsync(String fqdn, String subdomainFqdn, final ApiCallback<Void> _callback) throws ApiException {

        okhttp3.Call localVarCall = deleteSubdomainValidateBeforeCall(fqdn, subdomainFqdn, _callback);
        localVarApiClient.executeAsync(localVarCall, _callback);
        return localVarCall;
    }
    /**
     * Build call for getDomain
     * @param fqdn Полное имя домена. (required)
     * @param _callback Callback for upload/download progress
     * @return Call to execute
     * @throws ApiException If fail to serialize the request body object
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> Ответ будет представлять собой объект JSON c ключом &#x60;domain&#x60;. </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Некорректный запрос </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Не авторизован </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Не найдено </td><td>  -  </td></tr>
        <tr><td> 429 </td><td> Слишком много запросов </td><td>  -  </td></tr>
        <tr><td> 500 </td><td> Внутренняя ошибка сервера </td><td>  -  </td></tr>
     </table>
     */
    public okhttp3.Call getDomainCall(String fqdn, final ApiCallback _callback) throws ApiException {
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
        String localVarPath = "/api/v1/domains/{fqdn}"
            .replace("{" + "fqdn" + "}", localVarApiClient.escapeString(fqdn.toString()));

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
    private okhttp3.Call getDomainValidateBeforeCall(String fqdn, final ApiCallback _callback) throws ApiException {
        // verify the required parameter 'fqdn' is set
        if (fqdn == null) {
            throw new ApiException("Missing the required parameter 'fqdn' when calling getDomain(Async)");
        }

        return getDomainCall(fqdn, _callback);

    }

    /**
     * Получение информации о домене
     * Чтобы отобразить информацию об отдельном домене, отправьте запрос GET на &#x60;/api/v1/domains/{fqdn}&#x60;.
     * @param fqdn Полное имя домена. (required)
     * @return GetDomain200Response
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> Ответ будет представлять собой объект JSON c ключом &#x60;domain&#x60;. </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Некорректный запрос </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Не авторизован </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Не найдено </td><td>  -  </td></tr>
        <tr><td> 429 </td><td> Слишком много запросов </td><td>  -  </td></tr>
        <tr><td> 500 </td><td> Внутренняя ошибка сервера </td><td>  -  </td></tr>
     </table>
     */
    public GetDomain200Response getDomain(String fqdn) throws ApiException {
        ApiResponse<GetDomain200Response> localVarResp = getDomainWithHttpInfo(fqdn);
        return localVarResp.getData();
    }

    /**
     * Получение информации о домене
     * Чтобы отобразить информацию об отдельном домене, отправьте запрос GET на &#x60;/api/v1/domains/{fqdn}&#x60;.
     * @param fqdn Полное имя домена. (required)
     * @return ApiResponse&lt;GetDomain200Response&gt;
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> Ответ будет представлять собой объект JSON c ключом &#x60;domain&#x60;. </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Некорректный запрос </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Не авторизован </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Не найдено </td><td>  -  </td></tr>
        <tr><td> 429 </td><td> Слишком много запросов </td><td>  -  </td></tr>
        <tr><td> 500 </td><td> Внутренняя ошибка сервера </td><td>  -  </td></tr>
     </table>
     */
    public ApiResponse<GetDomain200Response> getDomainWithHttpInfo(String fqdn) throws ApiException {
        okhttp3.Call localVarCall = getDomainValidateBeforeCall(fqdn, null);
        Type localVarReturnType = new TypeToken<GetDomain200Response>(){}.getType();
        return localVarApiClient.execute(localVarCall, localVarReturnType);
    }

    /**
     * Получение информации о домене (asynchronously)
     * Чтобы отобразить информацию об отдельном домене, отправьте запрос GET на &#x60;/api/v1/domains/{fqdn}&#x60;.
     * @param fqdn Полное имя домена. (required)
     * @param _callback The callback to be executed when the API call finishes
     * @return The request call
     * @throws ApiException If fail to process the API call, e.g. serializing the request body object
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> Ответ будет представлять собой объект JSON c ключом &#x60;domain&#x60;. </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Некорректный запрос </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Не авторизован </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Не найдено </td><td>  -  </td></tr>
        <tr><td> 429 </td><td> Слишком много запросов </td><td>  -  </td></tr>
        <tr><td> 500 </td><td> Внутренняя ошибка сервера </td><td>  -  </td></tr>
     </table>
     */
    public okhttp3.Call getDomainAsync(String fqdn, final ApiCallback<GetDomain200Response> _callback) throws ApiException {

        okhttp3.Call localVarCall = getDomainValidateBeforeCall(fqdn, _callback);
        Type localVarReturnType = new TypeToken<GetDomain200Response>(){}.getType();
        localVarApiClient.executeAsync(localVarCall, localVarReturnType, _callback);
        return localVarCall;
    }
    /**
     * Build call for getDomainDNSRecords
     * @param fqdn Полное имя домена или поддомена. (required)
     * @param limit Обозначает количество записей, которое необходимо вернуть. (optional, default to 100)
     * @param offset Указывает на смещение относительно начала списка. (optional, default to 0)
     * @param _callback Callback for upload/download progress
     * @return Call to execute
     * @throws ApiException If fail to serialize the request body object
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> Ответ будет представлять собой объект JSON c ключом &#x60;dns_records&#x60;. </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Некорректный запрос </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Не авторизован </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Не найдено </td><td>  -  </td></tr>
        <tr><td> 429 </td><td> Слишком много запросов </td><td>  -  </td></tr>
        <tr><td> 500 </td><td> Внутренняя ошибка сервера </td><td>  -  </td></tr>
     </table>
     */
    public okhttp3.Call getDomainDNSRecordsCall(String fqdn, Integer limit, Integer offset, final ApiCallback _callback) throws ApiException {
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
        String localVarPath = "/api/v1/domains/{fqdn}/dns-records"
            .replace("{" + "fqdn" + "}", localVarApiClient.escapeString(fqdn.toString()));

        List<Pair> localVarQueryParams = new ArrayList<Pair>();
        List<Pair> localVarCollectionQueryParams = new ArrayList<Pair>();
        Map<String, String> localVarHeaderParams = new HashMap<String, String>();
        Map<String, String> localVarCookieParams = new HashMap<String, String>();
        Map<String, Object> localVarFormParams = new HashMap<String, Object>();

        if (limit != null) {
            localVarQueryParams.addAll(localVarApiClient.parameterToPair("limit", limit));
        }

        if (offset != null) {
            localVarQueryParams.addAll(localVarApiClient.parameterToPair("offset", offset));
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
    private okhttp3.Call getDomainDNSRecordsValidateBeforeCall(String fqdn, Integer limit, Integer offset, final ApiCallback _callback) throws ApiException {
        // verify the required parameter 'fqdn' is set
        if (fqdn == null) {
            throw new ApiException("Missing the required parameter 'fqdn' when calling getDomainDNSRecords(Async)");
        }

        return getDomainDNSRecordsCall(fqdn, limit, offset, _callback);

    }

    /**
     * Получить информацию обо всех пользовательских DNS-записях домена или поддомена
     * Чтобы получить информацию обо всех пользовательских DNS-записях домена или поддомена, отправьте запрос GET на &#x60;/api/v1/domains/{fqdn}/dns-records&#x60;.
     * @param fqdn Полное имя домена или поддомена. (required)
     * @param limit Обозначает количество записей, которое необходимо вернуть. (optional, default to 100)
     * @param offset Указывает на смещение относительно начала списка. (optional, default to 0)
     * @return GetDomainDNSRecords200Response
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> Ответ будет представлять собой объект JSON c ключом &#x60;dns_records&#x60;. </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Некорректный запрос </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Не авторизован </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Не найдено </td><td>  -  </td></tr>
        <tr><td> 429 </td><td> Слишком много запросов </td><td>  -  </td></tr>
        <tr><td> 500 </td><td> Внутренняя ошибка сервера </td><td>  -  </td></tr>
     </table>
     */
    public GetDomainDNSRecords200Response getDomainDNSRecords(String fqdn, Integer limit, Integer offset) throws ApiException {
        ApiResponse<GetDomainDNSRecords200Response> localVarResp = getDomainDNSRecordsWithHttpInfo(fqdn, limit, offset);
        return localVarResp.getData();
    }

    /**
     * Получить информацию обо всех пользовательских DNS-записях домена или поддомена
     * Чтобы получить информацию обо всех пользовательских DNS-записях домена или поддомена, отправьте запрос GET на &#x60;/api/v1/domains/{fqdn}/dns-records&#x60;.
     * @param fqdn Полное имя домена или поддомена. (required)
     * @param limit Обозначает количество записей, которое необходимо вернуть. (optional, default to 100)
     * @param offset Указывает на смещение относительно начала списка. (optional, default to 0)
     * @return ApiResponse&lt;GetDomainDNSRecords200Response&gt;
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> Ответ будет представлять собой объект JSON c ключом &#x60;dns_records&#x60;. </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Некорректный запрос </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Не авторизован </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Не найдено </td><td>  -  </td></tr>
        <tr><td> 429 </td><td> Слишком много запросов </td><td>  -  </td></tr>
        <tr><td> 500 </td><td> Внутренняя ошибка сервера </td><td>  -  </td></tr>
     </table>
     */
    public ApiResponse<GetDomainDNSRecords200Response> getDomainDNSRecordsWithHttpInfo(String fqdn, Integer limit, Integer offset) throws ApiException {
        okhttp3.Call localVarCall = getDomainDNSRecordsValidateBeforeCall(fqdn, limit, offset, null);
        Type localVarReturnType = new TypeToken<GetDomainDNSRecords200Response>(){}.getType();
        return localVarApiClient.execute(localVarCall, localVarReturnType);
    }

    /**
     * Получить информацию обо всех пользовательских DNS-записях домена или поддомена (asynchronously)
     * Чтобы получить информацию обо всех пользовательских DNS-записях домена или поддомена, отправьте запрос GET на &#x60;/api/v1/domains/{fqdn}/dns-records&#x60;.
     * @param fqdn Полное имя домена или поддомена. (required)
     * @param limit Обозначает количество записей, которое необходимо вернуть. (optional, default to 100)
     * @param offset Указывает на смещение относительно начала списка. (optional, default to 0)
     * @param _callback The callback to be executed when the API call finishes
     * @return The request call
     * @throws ApiException If fail to process the API call, e.g. serializing the request body object
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> Ответ будет представлять собой объект JSON c ключом &#x60;dns_records&#x60;. </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Некорректный запрос </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Не авторизован </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Не найдено </td><td>  -  </td></tr>
        <tr><td> 429 </td><td> Слишком много запросов </td><td>  -  </td></tr>
        <tr><td> 500 </td><td> Внутренняя ошибка сервера </td><td>  -  </td></tr>
     </table>
     */
    public okhttp3.Call getDomainDNSRecordsAsync(String fqdn, Integer limit, Integer offset, final ApiCallback<GetDomainDNSRecords200Response> _callback) throws ApiException {

        okhttp3.Call localVarCall = getDomainDNSRecordsValidateBeforeCall(fqdn, limit, offset, _callback);
        Type localVarReturnType = new TypeToken<GetDomainDNSRecords200Response>(){}.getType();
        localVarApiClient.executeAsync(localVarCall, localVarReturnType, _callback);
        return localVarCall;
    }
    /**
     * Build call for getDomainDefaultDNSRecords
     * @param fqdn Полное имя домена или поддомена. (required)
     * @param limit Обозначает количество записей, которое необходимо вернуть. (optional, default to 100)
     * @param offset Указывает на смещение относительно начала списка. (optional, default to 0)
     * @param _callback Callback for upload/download progress
     * @return Call to execute
     * @throws ApiException If fail to serialize the request body object
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> Ответ будет представлять собой объект JSON c ключом &#x60;dns_records&#x60;. </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Некорректный запрос </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Не авторизован </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Не найдено </td><td>  -  </td></tr>
        <tr><td> 429 </td><td> Слишком много запросов </td><td>  -  </td></tr>
        <tr><td> 500 </td><td> Внутренняя ошибка сервера </td><td>  -  </td></tr>
     </table>
     */
    public okhttp3.Call getDomainDefaultDNSRecordsCall(String fqdn, Integer limit, Integer offset, final ApiCallback _callback) throws ApiException {
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
        String localVarPath = "/api/v1/domains/{fqdn}/default-dns-records"
            .replace("{" + "fqdn" + "}", localVarApiClient.escapeString(fqdn.toString()));

        List<Pair> localVarQueryParams = new ArrayList<Pair>();
        List<Pair> localVarCollectionQueryParams = new ArrayList<Pair>();
        Map<String, String> localVarHeaderParams = new HashMap<String, String>();
        Map<String, String> localVarCookieParams = new HashMap<String, String>();
        Map<String, Object> localVarFormParams = new HashMap<String, Object>();

        if (limit != null) {
            localVarQueryParams.addAll(localVarApiClient.parameterToPair("limit", limit));
        }

        if (offset != null) {
            localVarQueryParams.addAll(localVarApiClient.parameterToPair("offset", offset));
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
    private okhttp3.Call getDomainDefaultDNSRecordsValidateBeforeCall(String fqdn, Integer limit, Integer offset, final ApiCallback _callback) throws ApiException {
        // verify the required parameter 'fqdn' is set
        if (fqdn == null) {
            throw new ApiException("Missing the required parameter 'fqdn' when calling getDomainDefaultDNSRecords(Async)");
        }

        return getDomainDefaultDNSRecordsCall(fqdn, limit, offset, _callback);

    }

    /**
     * Получить информацию обо всех DNS-записях по умолчанию домена или поддомена
     * Чтобы получить информацию обо всех DNS-записях по умолчанию домена или поддомена, отправьте запрос GET на &#x60;/api/v1/domains/{fqdn}/default-dns-records&#x60;.
     * @param fqdn Полное имя домена или поддомена. (required)
     * @param limit Обозначает количество записей, которое необходимо вернуть. (optional, default to 100)
     * @param offset Указывает на смещение относительно начала списка. (optional, default to 0)
     * @return GetDomainDNSRecords200Response
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> Ответ будет представлять собой объект JSON c ключом &#x60;dns_records&#x60;. </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Некорректный запрос </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Не авторизован </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Не найдено </td><td>  -  </td></tr>
        <tr><td> 429 </td><td> Слишком много запросов </td><td>  -  </td></tr>
        <tr><td> 500 </td><td> Внутренняя ошибка сервера </td><td>  -  </td></tr>
     </table>
     */
    public GetDomainDNSRecords200Response getDomainDefaultDNSRecords(String fqdn, Integer limit, Integer offset) throws ApiException {
        ApiResponse<GetDomainDNSRecords200Response> localVarResp = getDomainDefaultDNSRecordsWithHttpInfo(fqdn, limit, offset);
        return localVarResp.getData();
    }

    /**
     * Получить информацию обо всех DNS-записях по умолчанию домена или поддомена
     * Чтобы получить информацию обо всех DNS-записях по умолчанию домена или поддомена, отправьте запрос GET на &#x60;/api/v1/domains/{fqdn}/default-dns-records&#x60;.
     * @param fqdn Полное имя домена или поддомена. (required)
     * @param limit Обозначает количество записей, которое необходимо вернуть. (optional, default to 100)
     * @param offset Указывает на смещение относительно начала списка. (optional, default to 0)
     * @return ApiResponse&lt;GetDomainDNSRecords200Response&gt;
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> Ответ будет представлять собой объект JSON c ключом &#x60;dns_records&#x60;. </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Некорректный запрос </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Не авторизован </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Не найдено </td><td>  -  </td></tr>
        <tr><td> 429 </td><td> Слишком много запросов </td><td>  -  </td></tr>
        <tr><td> 500 </td><td> Внутренняя ошибка сервера </td><td>  -  </td></tr>
     </table>
     */
    public ApiResponse<GetDomainDNSRecords200Response> getDomainDefaultDNSRecordsWithHttpInfo(String fqdn, Integer limit, Integer offset) throws ApiException {
        okhttp3.Call localVarCall = getDomainDefaultDNSRecordsValidateBeforeCall(fqdn, limit, offset, null);
        Type localVarReturnType = new TypeToken<GetDomainDNSRecords200Response>(){}.getType();
        return localVarApiClient.execute(localVarCall, localVarReturnType);
    }

    /**
     * Получить информацию обо всех DNS-записях по умолчанию домена или поддомена (asynchronously)
     * Чтобы получить информацию обо всех DNS-записях по умолчанию домена или поддомена, отправьте запрос GET на &#x60;/api/v1/domains/{fqdn}/default-dns-records&#x60;.
     * @param fqdn Полное имя домена или поддомена. (required)
     * @param limit Обозначает количество записей, которое необходимо вернуть. (optional, default to 100)
     * @param offset Указывает на смещение относительно начала списка. (optional, default to 0)
     * @param _callback The callback to be executed when the API call finishes
     * @return The request call
     * @throws ApiException If fail to process the API call, e.g. serializing the request body object
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> Ответ будет представлять собой объект JSON c ключом &#x60;dns_records&#x60;. </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Некорректный запрос </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Не авторизован </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Не найдено </td><td>  -  </td></tr>
        <tr><td> 429 </td><td> Слишком много запросов </td><td>  -  </td></tr>
        <tr><td> 500 </td><td> Внутренняя ошибка сервера </td><td>  -  </td></tr>
     </table>
     */
    public okhttp3.Call getDomainDefaultDNSRecordsAsync(String fqdn, Integer limit, Integer offset, final ApiCallback<GetDomainDNSRecords200Response> _callback) throws ApiException {

        okhttp3.Call localVarCall = getDomainDefaultDNSRecordsValidateBeforeCall(fqdn, limit, offset, _callback);
        Type localVarReturnType = new TypeToken<GetDomainDNSRecords200Response>(){}.getType();
        localVarApiClient.executeAsync(localVarCall, localVarReturnType, _callback);
        return localVarCall;
    }
    /**
     * Build call for getDomainNameServers
     * @param fqdn Полное имя домена. (required)
     * @param _callback Callback for upload/download progress
     * @return Call to execute
     * @throws ApiException If fail to serialize the request body object
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> Ответ будет представлять собой объект JSON c ключом &#x60;name_servers&#x60;. </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Некорректный запрос </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Не авторизован </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Не найдено </td><td>  -  </td></tr>
        <tr><td> 429 </td><td> Слишком много запросов </td><td>  -  </td></tr>
        <tr><td> 500 </td><td> Внутренняя ошибка сервера </td><td>  -  </td></tr>
     </table>
     */
    public okhttp3.Call getDomainNameServersCall(String fqdn, final ApiCallback _callback) throws ApiException {
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
        String localVarPath = "/api/v1/domains/{fqdn}/name-servers"
            .replace("{" + "fqdn" + "}", localVarApiClient.escapeString(fqdn.toString()));

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
    private okhttp3.Call getDomainNameServersValidateBeforeCall(String fqdn, final ApiCallback _callback) throws ApiException {
        // verify the required parameter 'fqdn' is set
        if (fqdn == null) {
            throw new ApiException("Missing the required parameter 'fqdn' when calling getDomainNameServers(Async)");
        }

        return getDomainNameServersCall(fqdn, _callback);

    }

    /**
     * Получение списка name-серверов домена
     * Чтобы получить список name-серверов домена, отправьте запрос GET на &#x60;/api/v1/domains/{fqdn}/name-servers&#x60;.
     * @param fqdn Полное имя домена. (required)
     * @return GetDomainNameServers200Response
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> Ответ будет представлять собой объект JSON c ключом &#x60;name_servers&#x60;. </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Некорректный запрос </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Не авторизован </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Не найдено </td><td>  -  </td></tr>
        <tr><td> 429 </td><td> Слишком много запросов </td><td>  -  </td></tr>
        <tr><td> 500 </td><td> Внутренняя ошибка сервера </td><td>  -  </td></tr>
     </table>
     */
    public GetDomainNameServers200Response getDomainNameServers(String fqdn) throws ApiException {
        ApiResponse<GetDomainNameServers200Response> localVarResp = getDomainNameServersWithHttpInfo(fqdn);
        return localVarResp.getData();
    }

    /**
     * Получение списка name-серверов домена
     * Чтобы получить список name-серверов домена, отправьте запрос GET на &#x60;/api/v1/domains/{fqdn}/name-servers&#x60;.
     * @param fqdn Полное имя домена. (required)
     * @return ApiResponse&lt;GetDomainNameServers200Response&gt;
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> Ответ будет представлять собой объект JSON c ключом &#x60;name_servers&#x60;. </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Некорректный запрос </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Не авторизован </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Не найдено </td><td>  -  </td></tr>
        <tr><td> 429 </td><td> Слишком много запросов </td><td>  -  </td></tr>
        <tr><td> 500 </td><td> Внутренняя ошибка сервера </td><td>  -  </td></tr>
     </table>
     */
    public ApiResponse<GetDomainNameServers200Response> getDomainNameServersWithHttpInfo(String fqdn) throws ApiException {
        okhttp3.Call localVarCall = getDomainNameServersValidateBeforeCall(fqdn, null);
        Type localVarReturnType = new TypeToken<GetDomainNameServers200Response>(){}.getType();
        return localVarApiClient.execute(localVarCall, localVarReturnType);
    }

    /**
     * Получение списка name-серверов домена (asynchronously)
     * Чтобы получить список name-серверов домена, отправьте запрос GET на &#x60;/api/v1/domains/{fqdn}/name-servers&#x60;.
     * @param fqdn Полное имя домена. (required)
     * @param _callback The callback to be executed when the API call finishes
     * @return The request call
     * @throws ApiException If fail to process the API call, e.g. serializing the request body object
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> Ответ будет представлять собой объект JSON c ключом &#x60;name_servers&#x60;. </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Некорректный запрос </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Не авторизован </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Не найдено </td><td>  -  </td></tr>
        <tr><td> 429 </td><td> Слишком много запросов </td><td>  -  </td></tr>
        <tr><td> 500 </td><td> Внутренняя ошибка сервера </td><td>  -  </td></tr>
     </table>
     */
    public okhttp3.Call getDomainNameServersAsync(String fqdn, final ApiCallback<GetDomainNameServers200Response> _callback) throws ApiException {

        okhttp3.Call localVarCall = getDomainNameServersValidateBeforeCall(fqdn, _callback);
        Type localVarReturnType = new TypeToken<GetDomainNameServers200Response>(){}.getType();
        localVarApiClient.executeAsync(localVarCall, localVarReturnType, _callback);
        return localVarCall;
    }
    /**
     * Build call for getDomainRequest
     * @param requestId ID заявки на регистрацию/продление/трансфер домена. (required)
     * @param _callback Callback for upload/download progress
     * @return Call to execute
     * @throws ApiException If fail to serialize the request body object
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> Ответ будет представлять собой объект JSON c ключом &#x60;request&#x60;. </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Некорректный запрос </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Не авторизован </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Не найдено </td><td>  -  </td></tr>
        <tr><td> 429 </td><td> Слишком много запросов </td><td>  -  </td></tr>
        <tr><td> 500 </td><td> Внутренняя ошибка сервера </td><td>  -  </td></tr>
     </table>
     */
    public okhttp3.Call getDomainRequestCall(Integer requestId, final ApiCallback _callback) throws ApiException {
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
        String localVarPath = "/api/v1/domains-requests/{request_id}"
            .replace("{" + "request_id" + "}", localVarApiClient.escapeString(requestId.toString()));

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
    private okhttp3.Call getDomainRequestValidateBeforeCall(Integer requestId, final ApiCallback _callback) throws ApiException {
        // verify the required parameter 'requestId' is set
        if (requestId == null) {
            throw new ApiException("Missing the required parameter 'requestId' when calling getDomainRequest(Async)");
        }

        return getDomainRequestCall(requestId, _callback);

    }

    /**
     * Получение заявки на регистрацию/продление/трансфер домена
     * Чтобы получить заявку на регистрацию/продление/трансфер домена, отправьте запрос GET на &#x60;/api/v1/domains-requests/{request_id}&#x60;.
     * @param requestId ID заявки на регистрацию/продление/трансфер домена. (required)
     * @return CreateDomainRequest201Response
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> Ответ будет представлять собой объект JSON c ключом &#x60;request&#x60;. </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Некорректный запрос </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Не авторизован </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Не найдено </td><td>  -  </td></tr>
        <tr><td> 429 </td><td> Слишком много запросов </td><td>  -  </td></tr>
        <tr><td> 500 </td><td> Внутренняя ошибка сервера </td><td>  -  </td></tr>
     </table>
     */
    public CreateDomainRequest201Response getDomainRequest(Integer requestId) throws ApiException {
        ApiResponse<CreateDomainRequest201Response> localVarResp = getDomainRequestWithHttpInfo(requestId);
        return localVarResp.getData();
    }

    /**
     * Получение заявки на регистрацию/продление/трансфер домена
     * Чтобы получить заявку на регистрацию/продление/трансфер домена, отправьте запрос GET на &#x60;/api/v1/domains-requests/{request_id}&#x60;.
     * @param requestId ID заявки на регистрацию/продление/трансфер домена. (required)
     * @return ApiResponse&lt;CreateDomainRequest201Response&gt;
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> Ответ будет представлять собой объект JSON c ключом &#x60;request&#x60;. </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Некорректный запрос </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Не авторизован </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Не найдено </td><td>  -  </td></tr>
        <tr><td> 429 </td><td> Слишком много запросов </td><td>  -  </td></tr>
        <tr><td> 500 </td><td> Внутренняя ошибка сервера </td><td>  -  </td></tr>
     </table>
     */
    public ApiResponse<CreateDomainRequest201Response> getDomainRequestWithHttpInfo(Integer requestId) throws ApiException {
        okhttp3.Call localVarCall = getDomainRequestValidateBeforeCall(requestId, null);
        Type localVarReturnType = new TypeToken<CreateDomainRequest201Response>(){}.getType();
        return localVarApiClient.execute(localVarCall, localVarReturnType);
    }

    /**
     * Получение заявки на регистрацию/продление/трансфер домена (asynchronously)
     * Чтобы получить заявку на регистрацию/продление/трансфер домена, отправьте запрос GET на &#x60;/api/v1/domains-requests/{request_id}&#x60;.
     * @param requestId ID заявки на регистрацию/продление/трансфер домена. (required)
     * @param _callback The callback to be executed when the API call finishes
     * @return The request call
     * @throws ApiException If fail to process the API call, e.g. serializing the request body object
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> Ответ будет представлять собой объект JSON c ключом &#x60;request&#x60;. </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Некорректный запрос </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Не авторизован </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Не найдено </td><td>  -  </td></tr>
        <tr><td> 429 </td><td> Слишком много запросов </td><td>  -  </td></tr>
        <tr><td> 500 </td><td> Внутренняя ошибка сервера </td><td>  -  </td></tr>
     </table>
     */
    public okhttp3.Call getDomainRequestAsync(Integer requestId, final ApiCallback<CreateDomainRequest201Response> _callback) throws ApiException {

        okhttp3.Call localVarCall = getDomainRequestValidateBeforeCall(requestId, _callback);
        Type localVarReturnType = new TypeToken<CreateDomainRequest201Response>(){}.getType();
        localVarApiClient.executeAsync(localVarCall, localVarReturnType, _callback);
        return localVarCall;
    }
    /**
     * Build call for getDomainRequests
     * @param personId ID администратора, на которого зарегистрирован домен. (optional)
     * @param _callback Callback for upload/download progress
     * @return Call to execute
     * @throws ApiException If fail to serialize the request body object
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> Ответ будет представлять собой объект JSON c ключом &#x60;requests&#x60;. </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Некорректный запрос </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Не авторизован </td><td>  -  </td></tr>
        <tr><td> 429 </td><td> Слишком много запросов </td><td>  -  </td></tr>
        <tr><td> 500 </td><td> Внутренняя ошибка сервера </td><td>  -  </td></tr>
     </table>
     */
    public okhttp3.Call getDomainRequestsCall(Integer personId, final ApiCallback _callback) throws ApiException {
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
        String localVarPath = "/api/v1/domains-requests";

        List<Pair> localVarQueryParams = new ArrayList<Pair>();
        List<Pair> localVarCollectionQueryParams = new ArrayList<Pair>();
        Map<String, String> localVarHeaderParams = new HashMap<String, String>();
        Map<String, String> localVarCookieParams = new HashMap<String, String>();
        Map<String, Object> localVarFormParams = new HashMap<String, Object>();

        if (personId != null) {
            localVarQueryParams.addAll(localVarApiClient.parameterToPair("person_id", personId));
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
    private okhttp3.Call getDomainRequestsValidateBeforeCall(Integer personId, final ApiCallback _callback) throws ApiException {
        return getDomainRequestsCall(personId, _callback);

    }

    /**
     * Получение списка заявок на регистрацию/продление/трансфер домена
     * Чтобы получить список заявок на регистрацию/продление/трансфер домена, отправьте запрос GET на &#x60;/api/v1/domains-requests&#x60;.
     * @param personId ID администратора, на которого зарегистрирован домен. (optional)
     * @return GetDomainRequests200Response
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> Ответ будет представлять собой объект JSON c ключом &#x60;requests&#x60;. </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Некорректный запрос </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Не авторизован </td><td>  -  </td></tr>
        <tr><td> 429 </td><td> Слишком много запросов </td><td>  -  </td></tr>
        <tr><td> 500 </td><td> Внутренняя ошибка сервера </td><td>  -  </td></tr>
     </table>
     */
    public GetDomainRequests200Response getDomainRequests(Integer personId) throws ApiException {
        ApiResponse<GetDomainRequests200Response> localVarResp = getDomainRequestsWithHttpInfo(personId);
        return localVarResp.getData();
    }

    /**
     * Получение списка заявок на регистрацию/продление/трансфер домена
     * Чтобы получить список заявок на регистрацию/продление/трансфер домена, отправьте запрос GET на &#x60;/api/v1/domains-requests&#x60;.
     * @param personId ID администратора, на которого зарегистрирован домен. (optional)
     * @return ApiResponse&lt;GetDomainRequests200Response&gt;
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> Ответ будет представлять собой объект JSON c ключом &#x60;requests&#x60;. </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Некорректный запрос </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Не авторизован </td><td>  -  </td></tr>
        <tr><td> 429 </td><td> Слишком много запросов </td><td>  -  </td></tr>
        <tr><td> 500 </td><td> Внутренняя ошибка сервера </td><td>  -  </td></tr>
     </table>
     */
    public ApiResponse<GetDomainRequests200Response> getDomainRequestsWithHttpInfo(Integer personId) throws ApiException {
        okhttp3.Call localVarCall = getDomainRequestsValidateBeforeCall(personId, null);
        Type localVarReturnType = new TypeToken<GetDomainRequests200Response>(){}.getType();
        return localVarApiClient.execute(localVarCall, localVarReturnType);
    }

    /**
     * Получение списка заявок на регистрацию/продление/трансфер домена (asynchronously)
     * Чтобы получить список заявок на регистрацию/продление/трансфер домена, отправьте запрос GET на &#x60;/api/v1/domains-requests&#x60;.
     * @param personId ID администратора, на которого зарегистрирован домен. (optional)
     * @param _callback The callback to be executed when the API call finishes
     * @return The request call
     * @throws ApiException If fail to process the API call, e.g. serializing the request body object
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> Ответ будет представлять собой объект JSON c ключом &#x60;requests&#x60;. </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Некорректный запрос </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Не авторизован </td><td>  -  </td></tr>
        <tr><td> 429 </td><td> Слишком много запросов </td><td>  -  </td></tr>
        <tr><td> 500 </td><td> Внутренняя ошибка сервера </td><td>  -  </td></tr>
     </table>
     */
    public okhttp3.Call getDomainRequestsAsync(Integer personId, final ApiCallback<GetDomainRequests200Response> _callback) throws ApiException {

        okhttp3.Call localVarCall = getDomainRequestsValidateBeforeCall(personId, _callback);
        Type localVarReturnType = new TypeToken<GetDomainRequests200Response>(){}.getType();
        localVarApiClient.executeAsync(localVarCall, localVarReturnType, _callback);
        return localVarCall;
    }
    /**
     * Build call for getDomains
     * @param limit Обозначает количество записей, которое необходимо вернуть. (optional, default to 100)
     * @param offset Указывает на смещение относительно начала списка. (optional, default to 0)
     * @param idnName Интернационализированное доменное имя. (optional)
     * @param linkedIp Привязанный к домену IP-адрес. (optional)
     * @param order Порядок доменов. (optional)
     * @param sort Сортировка доменов. (optional)
     * @param _callback Callback for upload/download progress
     * @return Call to execute
     * @throws ApiException If fail to serialize the request body object
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> Ответ будет представлять собой объект JSON c ключом &#x60;domains&#x60;. </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Некорректный запрос </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Не авторизован </td><td>  -  </td></tr>
        <tr><td> 429 </td><td> Слишком много запросов </td><td>  -  </td></tr>
        <tr><td> 500 </td><td> Внутренняя ошибка сервера </td><td>  -  </td></tr>
     </table>
     */
    public okhttp3.Call getDomainsCall(Integer limit, Integer offset, String idnName, String linkedIp, String order, String sort, final ApiCallback _callback) throws ApiException {
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
        String localVarPath = "/api/v1/domains";

        List<Pair> localVarQueryParams = new ArrayList<Pair>();
        List<Pair> localVarCollectionQueryParams = new ArrayList<Pair>();
        Map<String, String> localVarHeaderParams = new HashMap<String, String>();
        Map<String, String> localVarCookieParams = new HashMap<String, String>();
        Map<String, Object> localVarFormParams = new HashMap<String, Object>();

        if (limit != null) {
            localVarQueryParams.addAll(localVarApiClient.parameterToPair("limit", limit));
        }

        if (offset != null) {
            localVarQueryParams.addAll(localVarApiClient.parameterToPair("offset", offset));
        }

        if (idnName != null) {
            localVarQueryParams.addAll(localVarApiClient.parameterToPair("idn_name", idnName));
        }

        if (linkedIp != null) {
            localVarQueryParams.addAll(localVarApiClient.parameterToPair("linked_ip", linkedIp));
        }

        if (order != null) {
            localVarQueryParams.addAll(localVarApiClient.parameterToPair("order", order));
        }

        if (sort != null) {
            localVarQueryParams.addAll(localVarApiClient.parameterToPair("sort", sort));
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
    private okhttp3.Call getDomainsValidateBeforeCall(Integer limit, Integer offset, String idnName, String linkedIp, String order, String sort, final ApiCallback _callback) throws ApiException {
        return getDomainsCall(limit, offset, idnName, linkedIp, order, sort, _callback);

    }

    /**
     * Получение списка всех доменов
     * Чтобы получить список всех доменов на вашем аккаунте, отправьте GET-запрос на &#x60;/api/v1/domains&#x60;.   Тело ответа будет представлять собой объект JSON с ключом &#x60;domains&#x60;.
     * @param limit Обозначает количество записей, которое необходимо вернуть. (optional, default to 100)
     * @param offset Указывает на смещение относительно начала списка. (optional, default to 0)
     * @param idnName Интернационализированное доменное имя. (optional)
     * @param linkedIp Привязанный к домену IP-адрес. (optional)
     * @param order Порядок доменов. (optional)
     * @param sort Сортировка доменов. (optional)
     * @return GetDomains200Response
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> Ответ будет представлять собой объект JSON c ключом &#x60;domains&#x60;. </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Некорректный запрос </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Не авторизован </td><td>  -  </td></tr>
        <tr><td> 429 </td><td> Слишком много запросов </td><td>  -  </td></tr>
        <tr><td> 500 </td><td> Внутренняя ошибка сервера </td><td>  -  </td></tr>
     </table>
     */
    public GetDomains200Response getDomains(Integer limit, Integer offset, String idnName, String linkedIp, String order, String sort) throws ApiException {
        ApiResponse<GetDomains200Response> localVarResp = getDomainsWithHttpInfo(limit, offset, idnName, linkedIp, order, sort);
        return localVarResp.getData();
    }

    /**
     * Получение списка всех доменов
     * Чтобы получить список всех доменов на вашем аккаунте, отправьте GET-запрос на &#x60;/api/v1/domains&#x60;.   Тело ответа будет представлять собой объект JSON с ключом &#x60;domains&#x60;.
     * @param limit Обозначает количество записей, которое необходимо вернуть. (optional, default to 100)
     * @param offset Указывает на смещение относительно начала списка. (optional, default to 0)
     * @param idnName Интернационализированное доменное имя. (optional)
     * @param linkedIp Привязанный к домену IP-адрес. (optional)
     * @param order Порядок доменов. (optional)
     * @param sort Сортировка доменов. (optional)
     * @return ApiResponse&lt;GetDomains200Response&gt;
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> Ответ будет представлять собой объект JSON c ключом &#x60;domains&#x60;. </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Некорректный запрос </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Не авторизован </td><td>  -  </td></tr>
        <tr><td> 429 </td><td> Слишком много запросов </td><td>  -  </td></tr>
        <tr><td> 500 </td><td> Внутренняя ошибка сервера </td><td>  -  </td></tr>
     </table>
     */
    public ApiResponse<GetDomains200Response> getDomainsWithHttpInfo(Integer limit, Integer offset, String idnName, String linkedIp, String order, String sort) throws ApiException {
        okhttp3.Call localVarCall = getDomainsValidateBeforeCall(limit, offset, idnName, linkedIp, order, sort, null);
        Type localVarReturnType = new TypeToken<GetDomains200Response>(){}.getType();
        return localVarApiClient.execute(localVarCall, localVarReturnType);
    }

    /**
     * Получение списка всех доменов (asynchronously)
     * Чтобы получить список всех доменов на вашем аккаунте, отправьте GET-запрос на &#x60;/api/v1/domains&#x60;.   Тело ответа будет представлять собой объект JSON с ключом &#x60;domains&#x60;.
     * @param limit Обозначает количество записей, которое необходимо вернуть. (optional, default to 100)
     * @param offset Указывает на смещение относительно начала списка. (optional, default to 0)
     * @param idnName Интернационализированное доменное имя. (optional)
     * @param linkedIp Привязанный к домену IP-адрес. (optional)
     * @param order Порядок доменов. (optional)
     * @param sort Сортировка доменов. (optional)
     * @param _callback The callback to be executed when the API call finishes
     * @return The request call
     * @throws ApiException If fail to process the API call, e.g. serializing the request body object
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> Ответ будет представлять собой объект JSON c ключом &#x60;domains&#x60;. </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Некорректный запрос </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Не авторизован </td><td>  -  </td></tr>
        <tr><td> 429 </td><td> Слишком много запросов </td><td>  -  </td></tr>
        <tr><td> 500 </td><td> Внутренняя ошибка сервера </td><td>  -  </td></tr>
     </table>
     */
    public okhttp3.Call getDomainsAsync(Integer limit, Integer offset, String idnName, String linkedIp, String order, String sort, final ApiCallback<GetDomains200Response> _callback) throws ApiException {

        okhttp3.Call localVarCall = getDomainsValidateBeforeCall(limit, offset, idnName, linkedIp, order, sort, _callback);
        Type localVarReturnType = new TypeToken<GetDomains200Response>(){}.getType();
        localVarApiClient.executeAsync(localVarCall, localVarReturnType, _callback);
        return localVarCall;
    }
    /**
     * Build call for getTLD
     * @param tldId ID доменной зоны. (required)
     * @param _callback Callback for upload/download progress
     * @return Call to execute
     * @throws ApiException If fail to serialize the request body object
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> Ответ будет представлять собой объект JSON c ключом &#x60;top_level_domain&#x60;. </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Некорректный запрос </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Не авторизован </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Не найдено </td><td>  -  </td></tr>
        <tr><td> 429 </td><td> Слишком много запросов </td><td>  -  </td></tr>
        <tr><td> 500 </td><td> Внутренняя ошибка сервера </td><td>  -  </td></tr>
     </table>
     */
    public okhttp3.Call getTLDCall(Integer tldId, final ApiCallback _callback) throws ApiException {
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
        String localVarPath = "/api/v1/tlds/{tld_id}"
            .replace("{" + "tld_id" + "}", localVarApiClient.escapeString(tldId.toString()));

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
    private okhttp3.Call getTLDValidateBeforeCall(Integer tldId, final ApiCallback _callback) throws ApiException {
        // verify the required parameter 'tldId' is set
        if (tldId == null) {
            throw new ApiException("Missing the required parameter 'tldId' when calling getTLD(Async)");
        }

        return getTLDCall(tldId, _callback);

    }

    /**
     * Получить информацию о доменной зоне по ID
     * Чтобы получить информацию о доменной зоне по ID, отправьте запрос GET на &#x60;/api/v1/tlds/{tld_id}&#x60;.
     * @param tldId ID доменной зоны. (required)
     * @return GetTLD200Response
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> Ответ будет представлять собой объект JSON c ключом &#x60;top_level_domain&#x60;. </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Некорректный запрос </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Не авторизован </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Не найдено </td><td>  -  </td></tr>
        <tr><td> 429 </td><td> Слишком много запросов </td><td>  -  </td></tr>
        <tr><td> 500 </td><td> Внутренняя ошибка сервера </td><td>  -  </td></tr>
     </table>
     */
    public GetTLD200Response getTLD(Integer tldId) throws ApiException {
        ApiResponse<GetTLD200Response> localVarResp = getTLDWithHttpInfo(tldId);
        return localVarResp.getData();
    }

    /**
     * Получить информацию о доменной зоне по ID
     * Чтобы получить информацию о доменной зоне по ID, отправьте запрос GET на &#x60;/api/v1/tlds/{tld_id}&#x60;.
     * @param tldId ID доменной зоны. (required)
     * @return ApiResponse&lt;GetTLD200Response&gt;
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> Ответ будет представлять собой объект JSON c ключом &#x60;top_level_domain&#x60;. </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Некорректный запрос </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Не авторизован </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Не найдено </td><td>  -  </td></tr>
        <tr><td> 429 </td><td> Слишком много запросов </td><td>  -  </td></tr>
        <tr><td> 500 </td><td> Внутренняя ошибка сервера </td><td>  -  </td></tr>
     </table>
     */
    public ApiResponse<GetTLD200Response> getTLDWithHttpInfo(Integer tldId) throws ApiException {
        okhttp3.Call localVarCall = getTLDValidateBeforeCall(tldId, null);
        Type localVarReturnType = new TypeToken<GetTLD200Response>(){}.getType();
        return localVarApiClient.execute(localVarCall, localVarReturnType);
    }

    /**
     * Получить информацию о доменной зоне по ID (asynchronously)
     * Чтобы получить информацию о доменной зоне по ID, отправьте запрос GET на &#x60;/api/v1/tlds/{tld_id}&#x60;.
     * @param tldId ID доменной зоны. (required)
     * @param _callback The callback to be executed when the API call finishes
     * @return The request call
     * @throws ApiException If fail to process the API call, e.g. serializing the request body object
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> Ответ будет представлять собой объект JSON c ключом &#x60;top_level_domain&#x60;. </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Некорректный запрос </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Не авторизован </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Не найдено </td><td>  -  </td></tr>
        <tr><td> 429 </td><td> Слишком много запросов </td><td>  -  </td></tr>
        <tr><td> 500 </td><td> Внутренняя ошибка сервера </td><td>  -  </td></tr>
     </table>
     */
    public okhttp3.Call getTLDAsync(Integer tldId, final ApiCallback<GetTLD200Response> _callback) throws ApiException {

        okhttp3.Call localVarCall = getTLDValidateBeforeCall(tldId, _callback);
        Type localVarReturnType = new TypeToken<GetTLD200Response>(){}.getType();
        localVarApiClient.executeAsync(localVarCall, localVarReturnType, _callback);
        return localVarCall;
    }
    /**
     * Build call for getTLDs
     * @param isPublished Это логическое значение, которое показывает, опубликована ли доменная зона. (optional)
     * @param isRegistered Это логическое значение, которое показывает, зарегистрирована ли доменная зона. (optional)
     * @param _callback Callback for upload/download progress
     * @return Call to execute
     * @throws ApiException If fail to serialize the request body object
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> Ответ будет представлять собой объект JSON c ключом &#x60;top_level_domains&#x60;. </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Некорректный запрос </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Не авторизован </td><td>  -  </td></tr>
        <tr><td> 429 </td><td> Слишком много запросов </td><td>  -  </td></tr>
        <tr><td> 500 </td><td> Внутренняя ошибка сервера </td><td>  -  </td></tr>
     </table>
     */
    public okhttp3.Call getTLDsCall(Boolean isPublished, Boolean isRegistered, final ApiCallback _callback) throws ApiException {
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
        String localVarPath = "/api/v1/tlds";

        List<Pair> localVarQueryParams = new ArrayList<Pair>();
        List<Pair> localVarCollectionQueryParams = new ArrayList<Pair>();
        Map<String, String> localVarHeaderParams = new HashMap<String, String>();
        Map<String, String> localVarCookieParams = new HashMap<String, String>();
        Map<String, Object> localVarFormParams = new HashMap<String, Object>();

        if (isPublished != null) {
            localVarQueryParams.addAll(localVarApiClient.parameterToPair("is_published", isPublished));
        }

        if (isRegistered != null) {
            localVarQueryParams.addAll(localVarApiClient.parameterToPair("is_registered", isRegistered));
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
    private okhttp3.Call getTLDsValidateBeforeCall(Boolean isPublished, Boolean isRegistered, final ApiCallback _callback) throws ApiException {
        return getTLDsCall(isPublished, isRegistered, _callback);

    }

    /**
     * Получить информацию о доменных зонах
     * Чтобы получить информацию о доменных зонах, отправьте запрос GET на &#x60;/api/v1/tlds&#x60;.
     * @param isPublished Это логическое значение, которое показывает, опубликована ли доменная зона. (optional)
     * @param isRegistered Это логическое значение, которое показывает, зарегистрирована ли доменная зона. (optional)
     * @return GetTLDs200Response
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> Ответ будет представлять собой объект JSON c ключом &#x60;top_level_domains&#x60;. </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Некорректный запрос </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Не авторизован </td><td>  -  </td></tr>
        <tr><td> 429 </td><td> Слишком много запросов </td><td>  -  </td></tr>
        <tr><td> 500 </td><td> Внутренняя ошибка сервера </td><td>  -  </td></tr>
     </table>
     */
    public GetTLDs200Response getTLDs(Boolean isPublished, Boolean isRegistered) throws ApiException {
        ApiResponse<GetTLDs200Response> localVarResp = getTLDsWithHttpInfo(isPublished, isRegistered);
        return localVarResp.getData();
    }

    /**
     * Получить информацию о доменных зонах
     * Чтобы получить информацию о доменных зонах, отправьте запрос GET на &#x60;/api/v1/tlds&#x60;.
     * @param isPublished Это логическое значение, которое показывает, опубликована ли доменная зона. (optional)
     * @param isRegistered Это логическое значение, которое показывает, зарегистрирована ли доменная зона. (optional)
     * @return ApiResponse&lt;GetTLDs200Response&gt;
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> Ответ будет представлять собой объект JSON c ключом &#x60;top_level_domains&#x60;. </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Некорректный запрос </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Не авторизован </td><td>  -  </td></tr>
        <tr><td> 429 </td><td> Слишком много запросов </td><td>  -  </td></tr>
        <tr><td> 500 </td><td> Внутренняя ошибка сервера </td><td>  -  </td></tr>
     </table>
     */
    public ApiResponse<GetTLDs200Response> getTLDsWithHttpInfo(Boolean isPublished, Boolean isRegistered) throws ApiException {
        okhttp3.Call localVarCall = getTLDsValidateBeforeCall(isPublished, isRegistered, null);
        Type localVarReturnType = new TypeToken<GetTLDs200Response>(){}.getType();
        return localVarApiClient.execute(localVarCall, localVarReturnType);
    }

    /**
     * Получить информацию о доменных зонах (asynchronously)
     * Чтобы получить информацию о доменных зонах, отправьте запрос GET на &#x60;/api/v1/tlds&#x60;.
     * @param isPublished Это логическое значение, которое показывает, опубликована ли доменная зона. (optional)
     * @param isRegistered Это логическое значение, которое показывает, зарегистрирована ли доменная зона. (optional)
     * @param _callback The callback to be executed when the API call finishes
     * @return The request call
     * @throws ApiException If fail to process the API call, e.g. serializing the request body object
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> Ответ будет представлять собой объект JSON c ключом &#x60;top_level_domains&#x60;. </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Некорректный запрос </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Не авторизован </td><td>  -  </td></tr>
        <tr><td> 429 </td><td> Слишком много запросов </td><td>  -  </td></tr>
        <tr><td> 500 </td><td> Внутренняя ошибка сервера </td><td>  -  </td></tr>
     </table>
     */
    public okhttp3.Call getTLDsAsync(Boolean isPublished, Boolean isRegistered, final ApiCallback<GetTLDs200Response> _callback) throws ApiException {

        okhttp3.Call localVarCall = getTLDsValidateBeforeCall(isPublished, isRegistered, _callback);
        Type localVarReturnType = new TypeToken<GetTLDs200Response>(){}.getType();
        localVarApiClient.executeAsync(localVarCall, localVarReturnType, _callback);
        return localVarCall;
    }
    /**
     * Build call for updateDomainAutoProlongation
     * @param fqdn Полное имя домена. (required)
     * @param updateDomain  (required)
     * @param _callback Callback for upload/download progress
     * @return Call to execute
     * @throws ApiException If fail to serialize the request body object
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> Объект JSON c ключом &#x60;domain&#x60; </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Некорректный запрос </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Не авторизован </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Не найдено </td><td>  -  </td></tr>
        <tr><td> 409 </td><td> Конфликт </td><td>  -  </td></tr>
        <tr><td> 429 </td><td> Слишком много запросов </td><td>  -  </td></tr>
        <tr><td> 500 </td><td> Внутренняя ошибка сервера </td><td>  -  </td></tr>
     </table>
     */
    public okhttp3.Call updateDomainAutoProlongationCall(String fqdn, UpdateDomain updateDomain, final ApiCallback _callback) throws ApiException {
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

        Object localVarPostBody = updateDomain;

        // create path and map variables
        String localVarPath = "/api/v1/domains/{fqdn}"
            .replace("{" + "fqdn" + "}", localVarApiClient.escapeString(fqdn.toString()));

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
    private okhttp3.Call updateDomainAutoProlongationValidateBeforeCall(String fqdn, UpdateDomain updateDomain, final ApiCallback _callback) throws ApiException {
        // verify the required parameter 'fqdn' is set
        if (fqdn == null) {
            throw new ApiException("Missing the required parameter 'fqdn' when calling updateDomainAutoProlongation(Async)");
        }

        // verify the required parameter 'updateDomain' is set
        if (updateDomain == null) {
            throw new ApiException("Missing the required parameter 'updateDomain' when calling updateDomainAutoProlongation(Async)");
        }

        return updateDomainAutoProlongationCall(fqdn, updateDomain, _callback);

    }

    /**
     * Включение/выключение автопродления домена
     * Чтобы включить/выключить автопродление домена, отправьте запрос PATCH на &#x60;/api/v1/domains/{fqdn}&#x60;, передав в теле запроса параметр &#x60;is_autoprolong_enabled&#x60;
     * @param fqdn Полное имя домена. (required)
     * @param updateDomain  (required)
     * @return UpdateDomainAutoProlongation200Response
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> Объект JSON c ключом &#x60;domain&#x60; </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Некорректный запрос </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Не авторизован </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Не найдено </td><td>  -  </td></tr>
        <tr><td> 409 </td><td> Конфликт </td><td>  -  </td></tr>
        <tr><td> 429 </td><td> Слишком много запросов </td><td>  -  </td></tr>
        <tr><td> 500 </td><td> Внутренняя ошибка сервера </td><td>  -  </td></tr>
     </table>
     */
    public UpdateDomainAutoProlongation200Response updateDomainAutoProlongation(String fqdn, UpdateDomain updateDomain) throws ApiException {
        ApiResponse<UpdateDomainAutoProlongation200Response> localVarResp = updateDomainAutoProlongationWithHttpInfo(fqdn, updateDomain);
        return localVarResp.getData();
    }

    /**
     * Включение/выключение автопродления домена
     * Чтобы включить/выключить автопродление домена, отправьте запрос PATCH на &#x60;/api/v1/domains/{fqdn}&#x60;, передав в теле запроса параметр &#x60;is_autoprolong_enabled&#x60;
     * @param fqdn Полное имя домена. (required)
     * @param updateDomain  (required)
     * @return ApiResponse&lt;UpdateDomainAutoProlongation200Response&gt;
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> Объект JSON c ключом &#x60;domain&#x60; </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Некорректный запрос </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Не авторизован </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Не найдено </td><td>  -  </td></tr>
        <tr><td> 409 </td><td> Конфликт </td><td>  -  </td></tr>
        <tr><td> 429 </td><td> Слишком много запросов </td><td>  -  </td></tr>
        <tr><td> 500 </td><td> Внутренняя ошибка сервера </td><td>  -  </td></tr>
     </table>
     */
    public ApiResponse<UpdateDomainAutoProlongation200Response> updateDomainAutoProlongationWithHttpInfo(String fqdn, UpdateDomain updateDomain) throws ApiException {
        okhttp3.Call localVarCall = updateDomainAutoProlongationValidateBeforeCall(fqdn, updateDomain, null);
        Type localVarReturnType = new TypeToken<UpdateDomainAutoProlongation200Response>(){}.getType();
        return localVarApiClient.execute(localVarCall, localVarReturnType);
    }

    /**
     * Включение/выключение автопродления домена (asynchronously)
     * Чтобы включить/выключить автопродление домена, отправьте запрос PATCH на &#x60;/api/v1/domains/{fqdn}&#x60;, передав в теле запроса параметр &#x60;is_autoprolong_enabled&#x60;
     * @param fqdn Полное имя домена. (required)
     * @param updateDomain  (required)
     * @param _callback The callback to be executed when the API call finishes
     * @return The request call
     * @throws ApiException If fail to process the API call, e.g. serializing the request body object
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> Объект JSON c ключом &#x60;domain&#x60; </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Некорректный запрос </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Не авторизован </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Не найдено </td><td>  -  </td></tr>
        <tr><td> 409 </td><td> Конфликт </td><td>  -  </td></tr>
        <tr><td> 429 </td><td> Слишком много запросов </td><td>  -  </td></tr>
        <tr><td> 500 </td><td> Внутренняя ошибка сервера </td><td>  -  </td></tr>
     </table>
     */
    public okhttp3.Call updateDomainAutoProlongationAsync(String fqdn, UpdateDomain updateDomain, final ApiCallback<UpdateDomainAutoProlongation200Response> _callback) throws ApiException {

        okhttp3.Call localVarCall = updateDomainAutoProlongationValidateBeforeCall(fqdn, updateDomain, _callback);
        Type localVarReturnType = new TypeToken<UpdateDomainAutoProlongation200Response>(){}.getType();
        localVarApiClient.executeAsync(localVarCall, localVarReturnType, _callback);
        return localVarCall;
    }
    /**
     * Build call for updateDomainDNSRecord
     * @param fqdn Полное имя домена или поддомена. (required)
     * @param recordId ID DNS-записи домена или поддомена. (required)
     * @param createDns  (required)
     * @param _callback Callback for upload/download progress
     * @return Call to execute
     * @throws ApiException If fail to serialize the request body object
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> Ответ будет представлять собой объект JSON c ключом &#x60;dns_record&#x60;. </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Некорректный запрос </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Не авторизован </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Не найдено </td><td>  -  </td></tr>
        <tr><td> 429 </td><td> Слишком много запросов </td><td>  -  </td></tr>
        <tr><td> 500 </td><td> Внутренняя ошибка сервера </td><td>  -  </td></tr>
     </table>
     * @deprecated
     */
    @Deprecated
    public okhttp3.Call updateDomainDNSRecordCall(String fqdn, Integer recordId, CreateDns createDns, final ApiCallback _callback) throws ApiException {
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

        Object localVarPostBody = createDns;

        // create path and map variables
        String localVarPath = "/api/v1/domains/{fqdn}/dns-records/{record_id}"
            .replace("{" + "fqdn" + "}", localVarApiClient.escapeString(fqdn.toString()))
            .replace("{" + "record_id" + "}", localVarApiClient.escapeString(recordId.toString()));

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

    @Deprecated
    @SuppressWarnings("rawtypes")
    private okhttp3.Call updateDomainDNSRecordValidateBeforeCall(String fqdn, Integer recordId, CreateDns createDns, final ApiCallback _callback) throws ApiException {
        // verify the required parameter 'fqdn' is set
        if (fqdn == null) {
            throw new ApiException("Missing the required parameter 'fqdn' when calling updateDomainDNSRecord(Async)");
        }

        // verify the required parameter 'recordId' is set
        if (recordId == null) {
            throw new ApiException("Missing the required parameter 'recordId' when calling updateDomainDNSRecord(Async)");
        }

        // verify the required parameter 'createDns' is set
        if (createDns == null) {
            throw new ApiException("Missing the required parameter 'createDns' when calling updateDomainDNSRecord(Async)");
        }

        return updateDomainDNSRecordCall(fqdn, recordId, createDns, _callback);

    }

    /**
     * Обновить информацию о DNS-записи домена или поддомена
     * Чтобы обновить информацию о DNS-записи для домена или поддомена, отправьте запрос PATCH на &#x60;/api/v1/domains/{fqdn}/dns-records/{record_id}&#x60;, задав необходимые атрибуты.  DNS-запись будет обновлена с использованием предоставленной информации. Тело ответа будет содержать объект JSON с информацией об добавленной DNS-записи.
     * @param fqdn Полное имя домена или поддомена. (required)
     * @param recordId ID DNS-записи домена или поддомена. (required)
     * @param createDns  (required)
     * @return CreateDomainDNSRecord201Response
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> Ответ будет представлять собой объект JSON c ключом &#x60;dns_record&#x60;. </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Некорректный запрос </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Не авторизован </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Не найдено </td><td>  -  </td></tr>
        <tr><td> 429 </td><td> Слишком много запросов </td><td>  -  </td></tr>
        <tr><td> 500 </td><td> Внутренняя ошибка сервера </td><td>  -  </td></tr>
     </table>
     * @deprecated
     */
    @Deprecated
    public CreateDomainDNSRecord201Response updateDomainDNSRecord(String fqdn, Integer recordId, CreateDns createDns) throws ApiException {
        ApiResponse<CreateDomainDNSRecord201Response> localVarResp = updateDomainDNSRecordWithHttpInfo(fqdn, recordId, createDns);
        return localVarResp.getData();
    }

    /**
     * Обновить информацию о DNS-записи домена или поддомена
     * Чтобы обновить информацию о DNS-записи для домена или поддомена, отправьте запрос PATCH на &#x60;/api/v1/domains/{fqdn}/dns-records/{record_id}&#x60;, задав необходимые атрибуты.  DNS-запись будет обновлена с использованием предоставленной информации. Тело ответа будет содержать объект JSON с информацией об добавленной DNS-записи.
     * @param fqdn Полное имя домена или поддомена. (required)
     * @param recordId ID DNS-записи домена или поддомена. (required)
     * @param createDns  (required)
     * @return ApiResponse&lt;CreateDomainDNSRecord201Response&gt;
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> Ответ будет представлять собой объект JSON c ключом &#x60;dns_record&#x60;. </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Некорректный запрос </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Не авторизован </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Не найдено </td><td>  -  </td></tr>
        <tr><td> 429 </td><td> Слишком много запросов </td><td>  -  </td></tr>
        <tr><td> 500 </td><td> Внутренняя ошибка сервера </td><td>  -  </td></tr>
     </table>
     * @deprecated
     */
    @Deprecated
    public ApiResponse<CreateDomainDNSRecord201Response> updateDomainDNSRecordWithHttpInfo(String fqdn, Integer recordId, CreateDns createDns) throws ApiException {
        okhttp3.Call localVarCall = updateDomainDNSRecordValidateBeforeCall(fqdn, recordId, createDns, null);
        Type localVarReturnType = new TypeToken<CreateDomainDNSRecord201Response>(){}.getType();
        return localVarApiClient.execute(localVarCall, localVarReturnType);
    }

    /**
     * Обновить информацию о DNS-записи домена или поддомена (asynchronously)
     * Чтобы обновить информацию о DNS-записи для домена или поддомена, отправьте запрос PATCH на &#x60;/api/v1/domains/{fqdn}/dns-records/{record_id}&#x60;, задав необходимые атрибуты.  DNS-запись будет обновлена с использованием предоставленной информации. Тело ответа будет содержать объект JSON с информацией об добавленной DNS-записи.
     * @param fqdn Полное имя домена или поддомена. (required)
     * @param recordId ID DNS-записи домена или поддомена. (required)
     * @param createDns  (required)
     * @param _callback The callback to be executed when the API call finishes
     * @return The request call
     * @throws ApiException If fail to process the API call, e.g. serializing the request body object
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> Ответ будет представлять собой объект JSON c ключом &#x60;dns_record&#x60;. </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Некорректный запрос </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Не авторизован </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Не найдено </td><td>  -  </td></tr>
        <tr><td> 429 </td><td> Слишком много запросов </td><td>  -  </td></tr>
        <tr><td> 500 </td><td> Внутренняя ошибка сервера </td><td>  -  </td></tr>
     </table>
     * @deprecated
     */
    @Deprecated
    public okhttp3.Call updateDomainDNSRecordAsync(String fqdn, Integer recordId, CreateDns createDns, final ApiCallback<CreateDomainDNSRecord201Response> _callback) throws ApiException {

        okhttp3.Call localVarCall = updateDomainDNSRecordValidateBeforeCall(fqdn, recordId, createDns, _callback);
        Type localVarReturnType = new TypeToken<CreateDomainDNSRecord201Response>(){}.getType();
        localVarApiClient.executeAsync(localVarCall, localVarReturnType, _callback);
        return localVarCall;
    }
    /**
     * Build call for updateDomainDNSRecordV2
     * @param fqdn Полное имя домена или поддомена. Для создания записи на основном домене передайте имя домена (например, &#x60;somedomain.ru&#x60;). Для создания записи на поддомене передайте полное доменное имя включая поддомен (например, &#x60;sub.somedomain.ru&#x60;). (required)
     * @param recordId ID DNS-записи домена или поддомена. (required)
     * @param createDnsV2  (required)
     * @param _callback Callback for upload/download progress
     * @return Call to execute
     * @throws ApiException If fail to serialize the request body object
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> Ответ будет представлять собой объект JSON c ключом &#x60;dns_record&#x60;. </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Некорректный запрос </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Не авторизован </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Не найдено </td><td>  -  </td></tr>
        <tr><td> 409 </td><td> Конфликт </td><td>  -  </td></tr>
        <tr><td> 429 </td><td> Слишком много запросов </td><td>  -  </td></tr>
        <tr><td> 500 </td><td> Внутренняя ошибка сервера </td><td>  -  </td></tr>
     </table>
     */
    public okhttp3.Call updateDomainDNSRecordV2Call(String fqdn, Integer recordId, CreateDnsV2 createDnsV2, final ApiCallback _callback) throws ApiException {
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

        Object localVarPostBody = createDnsV2;

        // create path and map variables
        String localVarPath = "/api/v2/domains/{fqdn}/dns-records/{record_id}"
            .replace("{" + "fqdn" + "}", localVarApiClient.escapeString(fqdn.toString()))
            .replace("{" + "record_id" + "}", localVarApiClient.escapeString(recordId.toString()));

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
    private okhttp3.Call updateDomainDNSRecordV2ValidateBeforeCall(String fqdn, Integer recordId, CreateDnsV2 createDnsV2, final ApiCallback _callback) throws ApiException {
        // verify the required parameter 'fqdn' is set
        if (fqdn == null) {
            throw new ApiException("Missing the required parameter 'fqdn' when calling updateDomainDNSRecordV2(Async)");
        }

        // verify the required parameter 'recordId' is set
        if (recordId == null) {
            throw new ApiException("Missing the required parameter 'recordId' when calling updateDomainDNSRecordV2(Async)");
        }

        // verify the required parameter 'createDnsV2' is set
        if (createDnsV2 == null) {
            throw new ApiException("Missing the required parameter 'createDnsV2' when calling updateDomainDNSRecordV2(Async)");
        }

        return updateDomainDNSRecordV2Call(fqdn, recordId, createDnsV2, _callback);

    }

    /**
     * Обновить информацию о DNS-записи домена или поддомена
     * Чтобы обновить информацию о DNS-записи для домена или поддомена, отправьте запрос PATCH на &#x60;/api/v2/domains/{fqdn}/dns-records/{record_id}&#x60;, задав необходимые атрибуты.  DNS-запись будет обновлена с использованием предоставленной информации. Тело ответа будет содержать объект JSON с информацией об обновленной DNS-записи.
     * @param fqdn Полное имя домена или поддомена. Для создания записи на основном домене передайте имя домена (например, &#x60;somedomain.ru&#x60;). Для создания записи на поддомене передайте полное доменное имя включая поддомен (например, &#x60;sub.somedomain.ru&#x60;). (required)
     * @param recordId ID DNS-записи домена или поддомена. (required)
     * @param createDnsV2  (required)
     * @return CreateDomainDNSRecordV2201Response
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> Ответ будет представлять собой объект JSON c ключом &#x60;dns_record&#x60;. </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Некорректный запрос </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Не авторизован </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Не найдено </td><td>  -  </td></tr>
        <tr><td> 409 </td><td> Конфликт </td><td>  -  </td></tr>
        <tr><td> 429 </td><td> Слишком много запросов </td><td>  -  </td></tr>
        <tr><td> 500 </td><td> Внутренняя ошибка сервера </td><td>  -  </td></tr>
     </table>
     */
    public CreateDomainDNSRecordV2201Response updateDomainDNSRecordV2(String fqdn, Integer recordId, CreateDnsV2 createDnsV2) throws ApiException {
        ApiResponse<CreateDomainDNSRecordV2201Response> localVarResp = updateDomainDNSRecordV2WithHttpInfo(fqdn, recordId, createDnsV2);
        return localVarResp.getData();
    }

    /**
     * Обновить информацию о DNS-записи домена или поддомена
     * Чтобы обновить информацию о DNS-записи для домена или поддомена, отправьте запрос PATCH на &#x60;/api/v2/domains/{fqdn}/dns-records/{record_id}&#x60;, задав необходимые атрибуты.  DNS-запись будет обновлена с использованием предоставленной информации. Тело ответа будет содержать объект JSON с информацией об обновленной DNS-записи.
     * @param fqdn Полное имя домена или поддомена. Для создания записи на основном домене передайте имя домена (например, &#x60;somedomain.ru&#x60;). Для создания записи на поддомене передайте полное доменное имя включая поддомен (например, &#x60;sub.somedomain.ru&#x60;). (required)
     * @param recordId ID DNS-записи домена или поддомена. (required)
     * @param createDnsV2  (required)
     * @return ApiResponse&lt;CreateDomainDNSRecordV2201Response&gt;
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> Ответ будет представлять собой объект JSON c ключом &#x60;dns_record&#x60;. </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Некорректный запрос </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Не авторизован </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Не найдено </td><td>  -  </td></tr>
        <tr><td> 409 </td><td> Конфликт </td><td>  -  </td></tr>
        <tr><td> 429 </td><td> Слишком много запросов </td><td>  -  </td></tr>
        <tr><td> 500 </td><td> Внутренняя ошибка сервера </td><td>  -  </td></tr>
     </table>
     */
    public ApiResponse<CreateDomainDNSRecordV2201Response> updateDomainDNSRecordV2WithHttpInfo(String fqdn, Integer recordId, CreateDnsV2 createDnsV2) throws ApiException {
        okhttp3.Call localVarCall = updateDomainDNSRecordV2ValidateBeforeCall(fqdn, recordId, createDnsV2, null);
        Type localVarReturnType = new TypeToken<CreateDomainDNSRecordV2201Response>(){}.getType();
        return localVarApiClient.execute(localVarCall, localVarReturnType);
    }

    /**
     * Обновить информацию о DNS-записи домена или поддомена (asynchronously)
     * Чтобы обновить информацию о DNS-записи для домена или поддомена, отправьте запрос PATCH на &#x60;/api/v2/domains/{fqdn}/dns-records/{record_id}&#x60;, задав необходимые атрибуты.  DNS-запись будет обновлена с использованием предоставленной информации. Тело ответа будет содержать объект JSON с информацией об обновленной DNS-записи.
     * @param fqdn Полное имя домена или поддомена. Для создания записи на основном домене передайте имя домена (например, &#x60;somedomain.ru&#x60;). Для создания записи на поддомене передайте полное доменное имя включая поддомен (например, &#x60;sub.somedomain.ru&#x60;). (required)
     * @param recordId ID DNS-записи домена или поддомена. (required)
     * @param createDnsV2  (required)
     * @param _callback The callback to be executed when the API call finishes
     * @return The request call
     * @throws ApiException If fail to process the API call, e.g. serializing the request body object
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> Ответ будет представлять собой объект JSON c ключом &#x60;dns_record&#x60;. </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Некорректный запрос </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Не авторизован </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Не найдено </td><td>  -  </td></tr>
        <tr><td> 409 </td><td> Конфликт </td><td>  -  </td></tr>
        <tr><td> 429 </td><td> Слишком много запросов </td><td>  -  </td></tr>
        <tr><td> 500 </td><td> Внутренняя ошибка сервера </td><td>  -  </td></tr>
     </table>
     */
    public okhttp3.Call updateDomainDNSRecordV2Async(String fqdn, Integer recordId, CreateDnsV2 createDnsV2, final ApiCallback<CreateDomainDNSRecordV2201Response> _callback) throws ApiException {

        okhttp3.Call localVarCall = updateDomainDNSRecordV2ValidateBeforeCall(fqdn, recordId, createDnsV2, _callback);
        Type localVarReturnType = new TypeToken<CreateDomainDNSRecordV2201Response>(){}.getType();
        localVarApiClient.executeAsync(localVarCall, localVarReturnType, _callback);
        return localVarCall;
    }
    /**
     * Build call for updateDomainNameServers
     * @param fqdn Полное имя домена. (required)
     * @param updateDomainNameServers  (required)
     * @param _callback Callback for upload/download progress
     * @return Call to execute
     * @throws ApiException If fail to serialize the request body object
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> Ответ будет представлять собой объект JSON c ключом &#x60;name_servers&#x60;. </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Некорректный запрос </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Не авторизован </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Не найдено </td><td>  -  </td></tr>
        <tr><td> 429 </td><td> Слишком много запросов </td><td>  -  </td></tr>
        <tr><td> 500 </td><td> Внутренняя ошибка сервера </td><td>  -  </td></tr>
     </table>
     */
    public okhttp3.Call updateDomainNameServersCall(String fqdn, UpdateDomainNameServers updateDomainNameServers, final ApiCallback _callback) throws ApiException {
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

        Object localVarPostBody = updateDomainNameServers;

        // create path and map variables
        String localVarPath = "/api/v1/domains/{fqdn}/name-servers"
            .replace("{" + "fqdn" + "}", localVarApiClient.escapeString(fqdn.toString()));

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
    private okhttp3.Call updateDomainNameServersValidateBeforeCall(String fqdn, UpdateDomainNameServers updateDomainNameServers, final ApiCallback _callback) throws ApiException {
        // verify the required parameter 'fqdn' is set
        if (fqdn == null) {
            throw new ApiException("Missing the required parameter 'fqdn' when calling updateDomainNameServers(Async)");
        }

        // verify the required parameter 'updateDomainNameServers' is set
        if (updateDomainNameServers == null) {
            throw new ApiException("Missing the required parameter 'updateDomainNameServers' when calling updateDomainNameServers(Async)");
        }

        return updateDomainNameServersCall(fqdn, updateDomainNameServers, _callback);

    }

    /**
     * Изменение name-серверов домена
     * Чтобы изменить name-серверы домена, отправьте запрос PUT на &#x60;/api/v1/domains/{fqdn}/name-servers&#x60;, задав необходимые атрибуты.  Name-серверы будут изменены с использованием предоставленной информации. Тело ответа будет содержать объект JSON с информацией о name-серверах домена.
     * @param fqdn Полное имя домена. (required)
     * @param updateDomainNameServers  (required)
     * @return GetDomainNameServers200Response
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> Ответ будет представлять собой объект JSON c ключом &#x60;name_servers&#x60;. </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Некорректный запрос </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Не авторизован </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Не найдено </td><td>  -  </td></tr>
        <tr><td> 429 </td><td> Слишком много запросов </td><td>  -  </td></tr>
        <tr><td> 500 </td><td> Внутренняя ошибка сервера </td><td>  -  </td></tr>
     </table>
     */
    public GetDomainNameServers200Response updateDomainNameServers(String fqdn, UpdateDomainNameServers updateDomainNameServers) throws ApiException {
        ApiResponse<GetDomainNameServers200Response> localVarResp = updateDomainNameServersWithHttpInfo(fqdn, updateDomainNameServers);
        return localVarResp.getData();
    }

    /**
     * Изменение name-серверов домена
     * Чтобы изменить name-серверы домена, отправьте запрос PUT на &#x60;/api/v1/domains/{fqdn}/name-servers&#x60;, задав необходимые атрибуты.  Name-серверы будут изменены с использованием предоставленной информации. Тело ответа будет содержать объект JSON с информацией о name-серверах домена.
     * @param fqdn Полное имя домена. (required)
     * @param updateDomainNameServers  (required)
     * @return ApiResponse&lt;GetDomainNameServers200Response&gt;
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> Ответ будет представлять собой объект JSON c ключом &#x60;name_servers&#x60;. </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Некорректный запрос </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Не авторизован </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Не найдено </td><td>  -  </td></tr>
        <tr><td> 429 </td><td> Слишком много запросов </td><td>  -  </td></tr>
        <tr><td> 500 </td><td> Внутренняя ошибка сервера </td><td>  -  </td></tr>
     </table>
     */
    public ApiResponse<GetDomainNameServers200Response> updateDomainNameServersWithHttpInfo(String fqdn, UpdateDomainNameServers updateDomainNameServers) throws ApiException {
        okhttp3.Call localVarCall = updateDomainNameServersValidateBeforeCall(fqdn, updateDomainNameServers, null);
        Type localVarReturnType = new TypeToken<GetDomainNameServers200Response>(){}.getType();
        return localVarApiClient.execute(localVarCall, localVarReturnType);
    }

    /**
     * Изменение name-серверов домена (asynchronously)
     * Чтобы изменить name-серверы домена, отправьте запрос PUT на &#x60;/api/v1/domains/{fqdn}/name-servers&#x60;, задав необходимые атрибуты.  Name-серверы будут изменены с использованием предоставленной информации. Тело ответа будет содержать объект JSON с информацией о name-серверах домена.
     * @param fqdn Полное имя домена. (required)
     * @param updateDomainNameServers  (required)
     * @param _callback The callback to be executed when the API call finishes
     * @return The request call
     * @throws ApiException If fail to process the API call, e.g. serializing the request body object
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> Ответ будет представлять собой объект JSON c ключом &#x60;name_servers&#x60;. </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Некорректный запрос </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Не авторизован </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Не найдено </td><td>  -  </td></tr>
        <tr><td> 429 </td><td> Слишком много запросов </td><td>  -  </td></tr>
        <tr><td> 500 </td><td> Внутренняя ошибка сервера </td><td>  -  </td></tr>
     </table>
     */
    public okhttp3.Call updateDomainNameServersAsync(String fqdn, UpdateDomainNameServers updateDomainNameServers, final ApiCallback<GetDomainNameServers200Response> _callback) throws ApiException {

        okhttp3.Call localVarCall = updateDomainNameServersValidateBeforeCall(fqdn, updateDomainNameServers, _callback);
        Type localVarReturnType = new TypeToken<GetDomainNameServers200Response>(){}.getType();
        localVarApiClient.executeAsync(localVarCall, localVarReturnType, _callback);
        return localVarCall;
    }
    /**
     * Build call for updateDomainRequest
     * @param requestId ID заявки на регистрацию/продление/трансфер домена. (required)
     * @param use  (required)
     * @param _callback Callback for upload/download progress
     * @return Call to execute
     * @throws ApiException If fail to serialize the request body object
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> Ответ будет представлять собой объект JSON c ключом &#x60;request&#x60;. </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Некорректный запрос </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Не авторизован </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Не найдено </td><td>  -  </td></tr>
        <tr><td> 409 </td><td> Конфликт </td><td>  -  </td></tr>
        <tr><td> 429 </td><td> Слишком много запросов </td><td>  -  </td></tr>
        <tr><td> 500 </td><td> Внутренняя ошибка сервера </td><td>  -  </td></tr>
     </table>
     */
    public okhttp3.Call updateDomainRequestCall(Integer requestId, Use use, final ApiCallback _callback) throws ApiException {
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

        Object localVarPostBody = use;

        // create path and map variables
        String localVarPath = "/api/v1/domains-requests/{request_id}"
            .replace("{" + "request_id" + "}", localVarApiClient.escapeString(requestId.toString()));

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
    private okhttp3.Call updateDomainRequestValidateBeforeCall(Integer requestId, Use use, final ApiCallback _callback) throws ApiException {
        // verify the required parameter 'requestId' is set
        if (requestId == null) {
            throw new ApiException("Missing the required parameter 'requestId' when calling updateDomainRequest(Async)");
        }

        // verify the required parameter 'use' is set
        if (use == null) {
            throw new ApiException("Missing the required parameter 'use' when calling updateDomainRequest(Async)");
        }

        return updateDomainRequestCall(requestId, use, _callback);

    }

    /**
     * Оплата/обновление заявки на регистрацию/продление/трансфер домена
     * Чтобы оплатить/обновить заявку на регистрацию/продление/трансфер домена, отправьте запрос PATCH на &#x60;/api/v1/domains-requests/{request_id}&#x60;, задав необходимые атрибуты.  Заявка будет обновлена с использованием предоставленной информации. Тело ответа будет содержать объект JSON с информацией о обновленной заявке.
     * @param requestId ID заявки на регистрацию/продление/трансфер домена. (required)
     * @param use  (required)
     * @return CreateDomainRequest201Response
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> Ответ будет представлять собой объект JSON c ключом &#x60;request&#x60;. </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Некорректный запрос </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Не авторизован </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Не найдено </td><td>  -  </td></tr>
        <tr><td> 409 </td><td> Конфликт </td><td>  -  </td></tr>
        <tr><td> 429 </td><td> Слишком много запросов </td><td>  -  </td></tr>
        <tr><td> 500 </td><td> Внутренняя ошибка сервера </td><td>  -  </td></tr>
     </table>
     */
    public CreateDomainRequest201Response updateDomainRequest(Integer requestId, Use use) throws ApiException {
        ApiResponse<CreateDomainRequest201Response> localVarResp = updateDomainRequestWithHttpInfo(requestId, use);
        return localVarResp.getData();
    }

    /**
     * Оплата/обновление заявки на регистрацию/продление/трансфер домена
     * Чтобы оплатить/обновить заявку на регистрацию/продление/трансфер домена, отправьте запрос PATCH на &#x60;/api/v1/domains-requests/{request_id}&#x60;, задав необходимые атрибуты.  Заявка будет обновлена с использованием предоставленной информации. Тело ответа будет содержать объект JSON с информацией о обновленной заявке.
     * @param requestId ID заявки на регистрацию/продление/трансфер домена. (required)
     * @param use  (required)
     * @return ApiResponse&lt;CreateDomainRequest201Response&gt;
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> Ответ будет представлять собой объект JSON c ключом &#x60;request&#x60;. </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Некорректный запрос </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Не авторизован </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Не найдено </td><td>  -  </td></tr>
        <tr><td> 409 </td><td> Конфликт </td><td>  -  </td></tr>
        <tr><td> 429 </td><td> Слишком много запросов </td><td>  -  </td></tr>
        <tr><td> 500 </td><td> Внутренняя ошибка сервера </td><td>  -  </td></tr>
     </table>
     */
    public ApiResponse<CreateDomainRequest201Response> updateDomainRequestWithHttpInfo(Integer requestId, Use use) throws ApiException {
        okhttp3.Call localVarCall = updateDomainRequestValidateBeforeCall(requestId, use, null);
        Type localVarReturnType = new TypeToken<CreateDomainRequest201Response>(){}.getType();
        return localVarApiClient.execute(localVarCall, localVarReturnType);
    }

    /**
     * Оплата/обновление заявки на регистрацию/продление/трансфер домена (asynchronously)
     * Чтобы оплатить/обновить заявку на регистрацию/продление/трансфер домена, отправьте запрос PATCH на &#x60;/api/v1/domains-requests/{request_id}&#x60;, задав необходимые атрибуты.  Заявка будет обновлена с использованием предоставленной информации. Тело ответа будет содержать объект JSON с информацией о обновленной заявке.
     * @param requestId ID заявки на регистрацию/продление/трансфер домена. (required)
     * @param use  (required)
     * @param _callback The callback to be executed when the API call finishes
     * @return The request call
     * @throws ApiException If fail to process the API call, e.g. serializing the request body object
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> Ответ будет представлять собой объект JSON c ключом &#x60;request&#x60;. </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Некорректный запрос </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Не авторизован </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Не найдено </td><td>  -  </td></tr>
        <tr><td> 409 </td><td> Конфликт </td><td>  -  </td></tr>
        <tr><td> 429 </td><td> Слишком много запросов </td><td>  -  </td></tr>
        <tr><td> 500 </td><td> Внутренняя ошибка сервера </td><td>  -  </td></tr>
     </table>
     */
    public okhttp3.Call updateDomainRequestAsync(Integer requestId, Use use, final ApiCallback<CreateDomainRequest201Response> _callback) throws ApiException {

        okhttp3.Call localVarCall = updateDomainRequestValidateBeforeCall(requestId, use, _callback);
        Type localVarReturnType = new TypeToken<CreateDomainRequest201Response>(){}.getType();
        localVarApiClient.executeAsync(localVarCall, localVarReturnType, _callback);
        return localVarCall;
    }
}
