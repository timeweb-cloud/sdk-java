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


import org.openapitools.client.model.AutoBackup;
import org.openapitools.client.model.CreateAdmin;
import org.openapitools.client.model.CreateCluster;
import org.openapitools.client.model.CreateDatabase201Response;
import org.openapitools.client.model.CreateDatabaseBackup201Response;
import org.openapitools.client.model.CreateDatabaseBackup409Response;
import org.openapitools.client.model.CreateDatabaseCluster201Response;
import org.openapitools.client.model.CreateDatabaseInstance201Response;
import org.openapitools.client.model.CreateDatabaseUser201Response;
import org.openapitools.client.model.CreateDb;
import org.openapitools.client.model.CreateInstance;
import org.openapitools.client.model.DeleteDatabase200Response;
import org.openapitools.client.model.DeleteDatabaseCluster200Response;
import org.openapitools.client.model.GetDatabaseAutoBackupsSettings200Response;
import org.openapitools.client.model.GetDatabaseBackups200Response;
import org.openapitools.client.model.GetDatabaseClusterTypes200Response;
import org.openapitools.client.model.GetDatabaseClusters200Response;
import org.openapitools.client.model.GetDatabaseInstances200Response;
import org.openapitools.client.model.GetDatabaseUsers200Response;
import org.openapitools.client.model.GetDatabases200Response;
import org.openapitools.client.model.GetDatabasesPresets200Response;
import org.openapitools.client.model.GetFinances400Response;
import org.openapitools.client.model.GetFinances401Response;
import org.openapitools.client.model.GetFinances403Response;
import org.openapitools.client.model.GetFinances429Response;
import org.openapitools.client.model.GetFinances500Response;
import org.openapitools.client.model.GetImage404Response;
import org.openapitools.client.model.UpdateAdmin;
import org.openapitools.client.model.UpdateCluster;
import org.openapitools.client.model.UpdateDb;
import org.openapitools.client.model.UpdateInstance;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DatabasesApi {
    private ApiClient localVarApiClient;
    private int localHostIndex;
    private String localCustomBaseUrl;

    public DatabasesApi() {
        this(Configuration.getDefaultApiClient());
    }

    public DatabasesApi(ApiClient apiClient) {
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
     * Build call for createDatabase
     * @param createDb  (required)
     * @param _callback Callback for upload/download progress
     * @return Call to execute
     * @throws ApiException If fail to serialize the request body object
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 201 </td><td> Ответ будет представлять собой объект JSON c ключом &#x60;db&#x60;. </td><td>  -  </td></tr>
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
    public okhttp3.Call createDatabaseCall(CreateDb createDb, final ApiCallback _callback) throws ApiException {
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

        Object localVarPostBody = createDb;

        // create path and map variables
        String localVarPath = "/api/v1/dbs";

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
    private okhttp3.Call createDatabaseValidateBeforeCall(CreateDb createDb, final ApiCallback _callback) throws ApiException {
        // verify the required parameter 'createDb' is set
        if (createDb == null) {
            throw new ApiException("Missing the required parameter 'createDb' when calling createDatabase(Async)");
        }

        return createDatabaseCall(createDb, _callback);

    }

    /**
     * Создание базы данных
     * Чтобы создать базу данных на вашем аккаунте, отправьте POST-запрос на &#x60;/api/v1/dbs&#x60;, задав необходимые атрибуты.  База данных будет создана с использованием предоставленной информации. Тело ответа будет содержать объект JSON с информацией о созданной базе данных.
     * @param createDb  (required)
     * @return CreateDatabase201Response
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 201 </td><td> Ответ будет представлять собой объект JSON c ключом &#x60;db&#x60;. </td><td>  -  </td></tr>
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
    public CreateDatabase201Response createDatabase(CreateDb createDb) throws ApiException {
        ApiResponse<CreateDatabase201Response> localVarResp = createDatabaseWithHttpInfo(createDb);
        return localVarResp.getData();
    }

    /**
     * Создание базы данных
     * Чтобы создать базу данных на вашем аккаунте, отправьте POST-запрос на &#x60;/api/v1/dbs&#x60;, задав необходимые атрибуты.  База данных будет создана с использованием предоставленной информации. Тело ответа будет содержать объект JSON с информацией о созданной базе данных.
     * @param createDb  (required)
     * @return ApiResponse&lt;CreateDatabase201Response&gt;
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 201 </td><td> Ответ будет представлять собой объект JSON c ключом &#x60;db&#x60;. </td><td>  -  </td></tr>
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
    public ApiResponse<CreateDatabase201Response> createDatabaseWithHttpInfo(CreateDb createDb) throws ApiException {
        okhttp3.Call localVarCall = createDatabaseValidateBeforeCall(createDb, null);
        Type localVarReturnType = new TypeToken<CreateDatabase201Response>(){}.getType();
        return localVarApiClient.execute(localVarCall, localVarReturnType);
    }

    /**
     * Создание базы данных (asynchronously)
     * Чтобы создать базу данных на вашем аккаунте, отправьте POST-запрос на &#x60;/api/v1/dbs&#x60;, задав необходимые атрибуты.  База данных будет создана с использованием предоставленной информации. Тело ответа будет содержать объект JSON с информацией о созданной базе данных.
     * @param createDb  (required)
     * @param _callback The callback to be executed when the API call finishes
     * @return The request call
     * @throws ApiException If fail to process the API call, e.g. serializing the request body object
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 201 </td><td> Ответ будет представлять собой объект JSON c ключом &#x60;db&#x60;. </td><td>  -  </td></tr>
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
    public okhttp3.Call createDatabaseAsync(CreateDb createDb, final ApiCallback<CreateDatabase201Response> _callback) throws ApiException {

        okhttp3.Call localVarCall = createDatabaseValidateBeforeCall(createDb, _callback);
        Type localVarReturnType = new TypeToken<CreateDatabase201Response>(){}.getType();
        localVarApiClient.executeAsync(localVarCall, localVarReturnType, _callback);
        return localVarCall;
    }
    /**
     * Build call for createDatabaseBackup
     * @param dbId ID базы данных (required)
     * @param _callback Callback for upload/download progress
     * @return Call to execute
     * @throws ApiException If fail to serialize the request body object
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 201 </td><td> Ответ будет представлять собой объект JSON с ключом &#x60;backup&#x60;. </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Некорректный запрос </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Не авторизован </td><td>  -  </td></tr>
        <tr><td> 403 </td><td> Запрещено </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Не найдено </td><td>  -  </td></tr>
        <tr><td> 409 </td><td> Конфликт </td><td>  -  </td></tr>
        <tr><td> 429 </td><td> Слишком много запросов </td><td>  -  </td></tr>
        <tr><td> 500 </td><td> Внутренняя ошибка сервера </td><td>  -  </td></tr>
     </table>
     */
    public okhttp3.Call createDatabaseBackupCall(Integer dbId, final ApiCallback _callback) throws ApiException {
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
        String localVarPath = "/api/v1/dbs/{db_id}/backups"
            .replace("{" + "db_id" + "}", localVarApiClient.escapeString(dbId.toString()));

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
    private okhttp3.Call createDatabaseBackupValidateBeforeCall(Integer dbId, final ApiCallback _callback) throws ApiException {
        // verify the required parameter 'dbId' is set
        if (dbId == null) {
            throw new ApiException("Missing the required parameter 'dbId' when calling createDatabaseBackup(Async)");
        }

        return createDatabaseBackupCall(dbId, _callback);

    }

    /**
     * Создание бэкапа базы данных
     * Чтобы создать бэкап базы данных, отправьте запрос POST в &#x60;api/v1/dbs/{db_id}/backups&#x60;. 
     * @param dbId ID базы данных (required)
     * @return CreateDatabaseBackup201Response
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 201 </td><td> Ответ будет представлять собой объект JSON с ключом &#x60;backup&#x60;. </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Некорректный запрос </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Не авторизован </td><td>  -  </td></tr>
        <tr><td> 403 </td><td> Запрещено </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Не найдено </td><td>  -  </td></tr>
        <tr><td> 409 </td><td> Конфликт </td><td>  -  </td></tr>
        <tr><td> 429 </td><td> Слишком много запросов </td><td>  -  </td></tr>
        <tr><td> 500 </td><td> Внутренняя ошибка сервера </td><td>  -  </td></tr>
     </table>
     */
    public CreateDatabaseBackup201Response createDatabaseBackup(Integer dbId) throws ApiException {
        ApiResponse<CreateDatabaseBackup201Response> localVarResp = createDatabaseBackupWithHttpInfo(dbId);
        return localVarResp.getData();
    }

    /**
     * Создание бэкапа базы данных
     * Чтобы создать бэкап базы данных, отправьте запрос POST в &#x60;api/v1/dbs/{db_id}/backups&#x60;. 
     * @param dbId ID базы данных (required)
     * @return ApiResponse&lt;CreateDatabaseBackup201Response&gt;
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 201 </td><td> Ответ будет представлять собой объект JSON с ключом &#x60;backup&#x60;. </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Некорректный запрос </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Не авторизован </td><td>  -  </td></tr>
        <tr><td> 403 </td><td> Запрещено </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Не найдено </td><td>  -  </td></tr>
        <tr><td> 409 </td><td> Конфликт </td><td>  -  </td></tr>
        <tr><td> 429 </td><td> Слишком много запросов </td><td>  -  </td></tr>
        <tr><td> 500 </td><td> Внутренняя ошибка сервера </td><td>  -  </td></tr>
     </table>
     */
    public ApiResponse<CreateDatabaseBackup201Response> createDatabaseBackupWithHttpInfo(Integer dbId) throws ApiException {
        okhttp3.Call localVarCall = createDatabaseBackupValidateBeforeCall(dbId, null);
        Type localVarReturnType = new TypeToken<CreateDatabaseBackup201Response>(){}.getType();
        return localVarApiClient.execute(localVarCall, localVarReturnType);
    }

    /**
     * Создание бэкапа базы данных (asynchronously)
     * Чтобы создать бэкап базы данных, отправьте запрос POST в &#x60;api/v1/dbs/{db_id}/backups&#x60;. 
     * @param dbId ID базы данных (required)
     * @param _callback The callback to be executed when the API call finishes
     * @return The request call
     * @throws ApiException If fail to process the API call, e.g. serializing the request body object
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 201 </td><td> Ответ будет представлять собой объект JSON с ключом &#x60;backup&#x60;. </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Некорректный запрос </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Не авторизован </td><td>  -  </td></tr>
        <tr><td> 403 </td><td> Запрещено </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Не найдено </td><td>  -  </td></tr>
        <tr><td> 409 </td><td> Конфликт </td><td>  -  </td></tr>
        <tr><td> 429 </td><td> Слишком много запросов </td><td>  -  </td></tr>
        <tr><td> 500 </td><td> Внутренняя ошибка сервера </td><td>  -  </td></tr>
     </table>
     */
    public okhttp3.Call createDatabaseBackupAsync(Integer dbId, final ApiCallback<CreateDatabaseBackup201Response> _callback) throws ApiException {

        okhttp3.Call localVarCall = createDatabaseBackupValidateBeforeCall(dbId, _callback);
        Type localVarReturnType = new TypeToken<CreateDatabaseBackup201Response>(){}.getType();
        localVarApiClient.executeAsync(localVarCall, localVarReturnType, _callback);
        return localVarCall;
    }
    /**
     * Build call for createDatabaseCluster
     * @param createCluster  (required)
     * @param _callback Callback for upload/download progress
     * @return Call to execute
     * @throws ApiException If fail to serialize the request body object
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 201 </td><td> Ответ будет представлять собой объект JSON c ключом &#x60;db&#x60;. </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Некорректный запрос </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Не авторизован </td><td>  -  </td></tr>
        <tr><td> 403 </td><td> Запрещено </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Не найдено </td><td>  -  </td></tr>
        <tr><td> 429 </td><td> Слишком много запросов </td><td>  -  </td></tr>
        <tr><td> 500 </td><td> Внутренняя ошибка сервера </td><td>  -  </td></tr>
     </table>
     */
    public okhttp3.Call createDatabaseClusterCall(CreateCluster createCluster, final ApiCallback _callback) throws ApiException {
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

        Object localVarPostBody = createCluster;

        // create path and map variables
        String localVarPath = "/api/v1/databases";

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
    private okhttp3.Call createDatabaseClusterValidateBeforeCall(CreateCluster createCluster, final ApiCallback _callback) throws ApiException {
        // verify the required parameter 'createCluster' is set
        if (createCluster == null) {
            throw new ApiException("Missing the required parameter 'createCluster' when calling createDatabaseCluster(Async)");
        }

        return createDatabaseClusterCall(createCluster, _callback);

    }

    /**
     * Создание кластера базы данных
     * Чтобы создать кластер базы данных на вашем аккаунте, отправьте POST-запрос на &#x60;/api/v1/databases&#x60;.   Вместе с кластером будет создан один инстанс базы данных и один пользователь.
     * @param createCluster  (required)
     * @return CreateDatabaseCluster201Response
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 201 </td><td> Ответ будет представлять собой объект JSON c ключом &#x60;db&#x60;. </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Некорректный запрос </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Не авторизован </td><td>  -  </td></tr>
        <tr><td> 403 </td><td> Запрещено </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Не найдено </td><td>  -  </td></tr>
        <tr><td> 429 </td><td> Слишком много запросов </td><td>  -  </td></tr>
        <tr><td> 500 </td><td> Внутренняя ошибка сервера </td><td>  -  </td></tr>
     </table>
     */
    public CreateDatabaseCluster201Response createDatabaseCluster(CreateCluster createCluster) throws ApiException {
        ApiResponse<CreateDatabaseCluster201Response> localVarResp = createDatabaseClusterWithHttpInfo(createCluster);
        return localVarResp.getData();
    }

    /**
     * Создание кластера базы данных
     * Чтобы создать кластер базы данных на вашем аккаунте, отправьте POST-запрос на &#x60;/api/v1/databases&#x60;.   Вместе с кластером будет создан один инстанс базы данных и один пользователь.
     * @param createCluster  (required)
     * @return ApiResponse&lt;CreateDatabaseCluster201Response&gt;
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 201 </td><td> Ответ будет представлять собой объект JSON c ключом &#x60;db&#x60;. </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Некорректный запрос </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Не авторизован </td><td>  -  </td></tr>
        <tr><td> 403 </td><td> Запрещено </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Не найдено </td><td>  -  </td></tr>
        <tr><td> 429 </td><td> Слишком много запросов </td><td>  -  </td></tr>
        <tr><td> 500 </td><td> Внутренняя ошибка сервера </td><td>  -  </td></tr>
     </table>
     */
    public ApiResponse<CreateDatabaseCluster201Response> createDatabaseClusterWithHttpInfo(CreateCluster createCluster) throws ApiException {
        okhttp3.Call localVarCall = createDatabaseClusterValidateBeforeCall(createCluster, null);
        Type localVarReturnType = new TypeToken<CreateDatabaseCluster201Response>(){}.getType();
        return localVarApiClient.execute(localVarCall, localVarReturnType);
    }

    /**
     * Создание кластера базы данных (asynchronously)
     * Чтобы создать кластер базы данных на вашем аккаунте, отправьте POST-запрос на &#x60;/api/v1/databases&#x60;.   Вместе с кластером будет создан один инстанс базы данных и один пользователь.
     * @param createCluster  (required)
     * @param _callback The callback to be executed when the API call finishes
     * @return The request call
     * @throws ApiException If fail to process the API call, e.g. serializing the request body object
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 201 </td><td> Ответ будет представлять собой объект JSON c ключом &#x60;db&#x60;. </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Некорректный запрос </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Не авторизован </td><td>  -  </td></tr>
        <tr><td> 403 </td><td> Запрещено </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Не найдено </td><td>  -  </td></tr>
        <tr><td> 429 </td><td> Слишком много запросов </td><td>  -  </td></tr>
        <tr><td> 500 </td><td> Внутренняя ошибка сервера </td><td>  -  </td></tr>
     </table>
     */
    public okhttp3.Call createDatabaseClusterAsync(CreateCluster createCluster, final ApiCallback<CreateDatabaseCluster201Response> _callback) throws ApiException {

        okhttp3.Call localVarCall = createDatabaseClusterValidateBeforeCall(createCluster, _callback);
        Type localVarReturnType = new TypeToken<CreateDatabaseCluster201Response>(){}.getType();
        localVarApiClient.executeAsync(localVarCall, localVarReturnType, _callback);
        return localVarCall;
    }
    /**
     * Build call for createDatabaseInstance
     * @param dbClusterId ID кластера базы данных (required)
     * @param createInstance  (required)
     * @param _callback Callback for upload/download progress
     * @return Call to execute
     * @throws ApiException If fail to serialize the request body object
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 201 </td><td> Ответ будет представлять собой объект JSON c ключом &#x60;instance&#x60;. </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Некорректный запрос </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Не авторизован </td><td>  -  </td></tr>
        <tr><td> 403 </td><td> Запрещено </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Не найдено </td><td>  -  </td></tr>
        <tr><td> 429 </td><td> Слишком много запросов </td><td>  -  </td></tr>
        <tr><td> 500 </td><td> Внутренняя ошибка сервера </td><td>  -  </td></tr>
     </table>
     */
    public okhttp3.Call createDatabaseInstanceCall(Integer dbClusterId, CreateInstance createInstance, final ApiCallback _callback) throws ApiException {
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

        Object localVarPostBody = createInstance;

        // create path and map variables
        String localVarPath = "/api/v1/databases/{db_cluster_id}/instances"
            .replace("{" + "db_cluster_id" + "}", localVarApiClient.escapeString(dbClusterId.toString()));

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
    private okhttp3.Call createDatabaseInstanceValidateBeforeCall(Integer dbClusterId, CreateInstance createInstance, final ApiCallback _callback) throws ApiException {
        // verify the required parameter 'dbClusterId' is set
        if (dbClusterId == null) {
            throw new ApiException("Missing the required parameter 'dbClusterId' when calling createDatabaseInstance(Async)");
        }

        // verify the required parameter 'createInstance' is set
        if (createInstance == null) {
            throw new ApiException("Missing the required parameter 'createInstance' when calling createDatabaseInstance(Async)");
        }

        return createDatabaseInstanceCall(dbClusterId, createInstance, _callback);

    }

    /**
     * Создание инстанса базы данных
     * Чтобы создать инстанс базы данных, отправьте POST-запрос на &#x60;/api/v1/databases/{db_cluster_id}/instances&#x60;.\\    Существующие пользователи не будут иметь доступа к новой базе данных после создания. Вы можете изменить привилегии для пользователя через &lt;a href&#x3D;&#39;#tag/Bazy-dannyh/operation/updateDatabaseUser&#39;&gt;метод изменения пользователя&lt;/a&gt; 
     * @param dbClusterId ID кластера базы данных (required)
     * @param createInstance  (required)
     * @return CreateDatabaseInstance201Response
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 201 </td><td> Ответ будет представлять собой объект JSON c ключом &#x60;instance&#x60;. </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Некорректный запрос </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Не авторизован </td><td>  -  </td></tr>
        <tr><td> 403 </td><td> Запрещено </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Не найдено </td><td>  -  </td></tr>
        <tr><td> 429 </td><td> Слишком много запросов </td><td>  -  </td></tr>
        <tr><td> 500 </td><td> Внутренняя ошибка сервера </td><td>  -  </td></tr>
     </table>
     */
    public CreateDatabaseInstance201Response createDatabaseInstance(Integer dbClusterId, CreateInstance createInstance) throws ApiException {
        ApiResponse<CreateDatabaseInstance201Response> localVarResp = createDatabaseInstanceWithHttpInfo(dbClusterId, createInstance);
        return localVarResp.getData();
    }

    /**
     * Создание инстанса базы данных
     * Чтобы создать инстанс базы данных, отправьте POST-запрос на &#x60;/api/v1/databases/{db_cluster_id}/instances&#x60;.\\    Существующие пользователи не будут иметь доступа к новой базе данных после создания. Вы можете изменить привилегии для пользователя через &lt;a href&#x3D;&#39;#tag/Bazy-dannyh/operation/updateDatabaseUser&#39;&gt;метод изменения пользователя&lt;/a&gt; 
     * @param dbClusterId ID кластера базы данных (required)
     * @param createInstance  (required)
     * @return ApiResponse&lt;CreateDatabaseInstance201Response&gt;
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 201 </td><td> Ответ будет представлять собой объект JSON c ключом &#x60;instance&#x60;. </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Некорректный запрос </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Не авторизован </td><td>  -  </td></tr>
        <tr><td> 403 </td><td> Запрещено </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Не найдено </td><td>  -  </td></tr>
        <tr><td> 429 </td><td> Слишком много запросов </td><td>  -  </td></tr>
        <tr><td> 500 </td><td> Внутренняя ошибка сервера </td><td>  -  </td></tr>
     </table>
     */
    public ApiResponse<CreateDatabaseInstance201Response> createDatabaseInstanceWithHttpInfo(Integer dbClusterId, CreateInstance createInstance) throws ApiException {
        okhttp3.Call localVarCall = createDatabaseInstanceValidateBeforeCall(dbClusterId, createInstance, null);
        Type localVarReturnType = new TypeToken<CreateDatabaseInstance201Response>(){}.getType();
        return localVarApiClient.execute(localVarCall, localVarReturnType);
    }

    /**
     * Создание инстанса базы данных (asynchronously)
     * Чтобы создать инстанс базы данных, отправьте POST-запрос на &#x60;/api/v1/databases/{db_cluster_id}/instances&#x60;.\\    Существующие пользователи не будут иметь доступа к новой базе данных после создания. Вы можете изменить привилегии для пользователя через &lt;a href&#x3D;&#39;#tag/Bazy-dannyh/operation/updateDatabaseUser&#39;&gt;метод изменения пользователя&lt;/a&gt; 
     * @param dbClusterId ID кластера базы данных (required)
     * @param createInstance  (required)
     * @param _callback The callback to be executed when the API call finishes
     * @return The request call
     * @throws ApiException If fail to process the API call, e.g. serializing the request body object
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 201 </td><td> Ответ будет представлять собой объект JSON c ключом &#x60;instance&#x60;. </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Некорректный запрос </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Не авторизован </td><td>  -  </td></tr>
        <tr><td> 403 </td><td> Запрещено </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Не найдено </td><td>  -  </td></tr>
        <tr><td> 429 </td><td> Слишком много запросов </td><td>  -  </td></tr>
        <tr><td> 500 </td><td> Внутренняя ошибка сервера </td><td>  -  </td></tr>
     </table>
     */
    public okhttp3.Call createDatabaseInstanceAsync(Integer dbClusterId, CreateInstance createInstance, final ApiCallback<CreateDatabaseInstance201Response> _callback) throws ApiException {

        okhttp3.Call localVarCall = createDatabaseInstanceValidateBeforeCall(dbClusterId, createInstance, _callback);
        Type localVarReturnType = new TypeToken<CreateDatabaseInstance201Response>(){}.getType();
        localVarApiClient.executeAsync(localVarCall, localVarReturnType, _callback);
        return localVarCall;
    }
    /**
     * Build call for createDatabaseUser
     * @param dbClusterId ID кластера базы данных (required)
     * @param createAdmin  (required)
     * @param _callback Callback for upload/download progress
     * @return Call to execute
     * @throws ApiException If fail to serialize the request body object
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 201 </td><td> Ответ будет представлять собой объект JSON c ключом &#x60;admin&#x60;. </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Некорректный запрос </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Не авторизован </td><td>  -  </td></tr>
        <tr><td> 403 </td><td> Запрещено </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Не найдено </td><td>  -  </td></tr>
        <tr><td> 429 </td><td> Слишком много запросов </td><td>  -  </td></tr>
        <tr><td> 500 </td><td> Внутренняя ошибка сервера </td><td>  -  </td></tr>
     </table>
     */
    public okhttp3.Call createDatabaseUserCall(Integer dbClusterId, CreateAdmin createAdmin, final ApiCallback _callback) throws ApiException {
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

        Object localVarPostBody = createAdmin;

        // create path and map variables
        String localVarPath = "/api/v1/databases/{db_cluster_id}/admins"
            .replace("{" + "db_cluster_id" + "}", localVarApiClient.escapeString(dbClusterId.toString()));

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
    private okhttp3.Call createDatabaseUserValidateBeforeCall(Integer dbClusterId, CreateAdmin createAdmin, final ApiCallback _callback) throws ApiException {
        // verify the required parameter 'dbClusterId' is set
        if (dbClusterId == null) {
            throw new ApiException("Missing the required parameter 'dbClusterId' when calling createDatabaseUser(Async)");
        }

        // verify the required parameter 'createAdmin' is set
        if (createAdmin == null) {
            throw new ApiException("Missing the required parameter 'createAdmin' when calling createDatabaseUser(Async)");
        }

        return createDatabaseUserCall(dbClusterId, createAdmin, _callback);

    }

    /**
     * Создание пользователя базы данных
     * Чтобы создать пользователя базы данных, отправьте POST-запрос на &#x60;/api/v1/databases/{db_cluster_id}/admins&#x60;.
     * @param dbClusterId ID кластера базы данных (required)
     * @param createAdmin  (required)
     * @return CreateDatabaseUser201Response
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 201 </td><td> Ответ будет представлять собой объект JSON c ключом &#x60;admin&#x60;. </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Некорректный запрос </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Не авторизован </td><td>  -  </td></tr>
        <tr><td> 403 </td><td> Запрещено </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Не найдено </td><td>  -  </td></tr>
        <tr><td> 429 </td><td> Слишком много запросов </td><td>  -  </td></tr>
        <tr><td> 500 </td><td> Внутренняя ошибка сервера </td><td>  -  </td></tr>
     </table>
     */
    public CreateDatabaseUser201Response createDatabaseUser(Integer dbClusterId, CreateAdmin createAdmin) throws ApiException {
        ApiResponse<CreateDatabaseUser201Response> localVarResp = createDatabaseUserWithHttpInfo(dbClusterId, createAdmin);
        return localVarResp.getData();
    }

    /**
     * Создание пользователя базы данных
     * Чтобы создать пользователя базы данных, отправьте POST-запрос на &#x60;/api/v1/databases/{db_cluster_id}/admins&#x60;.
     * @param dbClusterId ID кластера базы данных (required)
     * @param createAdmin  (required)
     * @return ApiResponse&lt;CreateDatabaseUser201Response&gt;
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 201 </td><td> Ответ будет представлять собой объект JSON c ключом &#x60;admin&#x60;. </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Некорректный запрос </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Не авторизован </td><td>  -  </td></tr>
        <tr><td> 403 </td><td> Запрещено </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Не найдено </td><td>  -  </td></tr>
        <tr><td> 429 </td><td> Слишком много запросов </td><td>  -  </td></tr>
        <tr><td> 500 </td><td> Внутренняя ошибка сервера </td><td>  -  </td></tr>
     </table>
     */
    public ApiResponse<CreateDatabaseUser201Response> createDatabaseUserWithHttpInfo(Integer dbClusterId, CreateAdmin createAdmin) throws ApiException {
        okhttp3.Call localVarCall = createDatabaseUserValidateBeforeCall(dbClusterId, createAdmin, null);
        Type localVarReturnType = new TypeToken<CreateDatabaseUser201Response>(){}.getType();
        return localVarApiClient.execute(localVarCall, localVarReturnType);
    }

    /**
     * Создание пользователя базы данных (asynchronously)
     * Чтобы создать пользователя базы данных, отправьте POST-запрос на &#x60;/api/v1/databases/{db_cluster_id}/admins&#x60;.
     * @param dbClusterId ID кластера базы данных (required)
     * @param createAdmin  (required)
     * @param _callback The callback to be executed when the API call finishes
     * @return The request call
     * @throws ApiException If fail to process the API call, e.g. serializing the request body object
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 201 </td><td> Ответ будет представлять собой объект JSON c ключом &#x60;admin&#x60;. </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Некорректный запрос </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Не авторизован </td><td>  -  </td></tr>
        <tr><td> 403 </td><td> Запрещено </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Не найдено </td><td>  -  </td></tr>
        <tr><td> 429 </td><td> Слишком много запросов </td><td>  -  </td></tr>
        <tr><td> 500 </td><td> Внутренняя ошибка сервера </td><td>  -  </td></tr>
     </table>
     */
    public okhttp3.Call createDatabaseUserAsync(Integer dbClusterId, CreateAdmin createAdmin, final ApiCallback<CreateDatabaseUser201Response> _callback) throws ApiException {

        okhttp3.Call localVarCall = createDatabaseUserValidateBeforeCall(dbClusterId, createAdmin, _callback);
        Type localVarReturnType = new TypeToken<CreateDatabaseUser201Response>(){}.getType();
        localVarApiClient.executeAsync(localVarCall, localVarReturnType, _callback);
        return localVarCall;
    }
    /**
     * Build call for deleteDatabase
     * @param dbId ID базы данных (required)
     * @param hash Хеш, который совместно с кодом авторизации надо отправить для удаления, если включено подтверждение удаления сервисов через Телеграм. (optional)
     * @param code Код подтверждения, который придет к вам в Телеграм, после запроса удаления, если включено подтверждение удаления сервисов.  При помощи API токена сервисы можно удалять без подтверждения, если параметр токена &#x60;is_able_to_delete&#x60; установлен в значение &#x60;true&#x60; (optional)
     * @param _callback Callback for upload/download progress
     * @return Call to execute
     * @throws ApiException If fail to serialize the request body object
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> Объект JSON c ключом &#x60;database_delete&#x60; </td><td>  -  </td></tr>
        <tr><td> 204 </td><td> База данных успешно удалена. </td><td>  -  </td></tr>
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
    public okhttp3.Call deleteDatabaseCall(Integer dbId, String hash, String code, final ApiCallback _callback) throws ApiException {
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
        String localVarPath = "/api/v1/dbs/{db_id}"
            .replace("{" + "db_id" + "}", localVarApiClient.escapeString(dbId.toString()));

        List<Pair> localVarQueryParams = new ArrayList<Pair>();
        List<Pair> localVarCollectionQueryParams = new ArrayList<Pair>();
        Map<String, String> localVarHeaderParams = new HashMap<String, String>();
        Map<String, String> localVarCookieParams = new HashMap<String, String>();
        Map<String, Object> localVarFormParams = new HashMap<String, Object>();

        if (hash != null) {
            localVarQueryParams.addAll(localVarApiClient.parameterToPair("hash", hash));
        }

        if (code != null) {
            localVarQueryParams.addAll(localVarApiClient.parameterToPair("code", code));
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
        return localVarApiClient.buildCall(basePath, localVarPath, "DELETE", localVarQueryParams, localVarCollectionQueryParams, localVarPostBody, localVarHeaderParams, localVarCookieParams, localVarFormParams, localVarAuthNames, _callback);
    }

    @Deprecated
    @SuppressWarnings("rawtypes")
    private okhttp3.Call deleteDatabaseValidateBeforeCall(Integer dbId, String hash, String code, final ApiCallback _callback) throws ApiException {
        // verify the required parameter 'dbId' is set
        if (dbId == null) {
            throw new ApiException("Missing the required parameter 'dbId' when calling deleteDatabase(Async)");
        }

        return deleteDatabaseCall(dbId, hash, code, _callback);

    }

    /**
     * Удаление базы данных
     * Чтобы удалить базу данных, отправьте запрос DELETE в &#x60;api/v1/dbs/{db_id}&#x60;. 
     * @param dbId ID базы данных (required)
     * @param hash Хеш, который совместно с кодом авторизации надо отправить для удаления, если включено подтверждение удаления сервисов через Телеграм. (optional)
     * @param code Код подтверждения, который придет к вам в Телеграм, после запроса удаления, если включено подтверждение удаления сервисов.  При помощи API токена сервисы можно удалять без подтверждения, если параметр токена &#x60;is_able_to_delete&#x60; установлен в значение &#x60;true&#x60; (optional)
     * @return DeleteDatabase200Response
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> Объект JSON c ключом &#x60;database_delete&#x60; </td><td>  -  </td></tr>
        <tr><td> 204 </td><td> База данных успешно удалена. </td><td>  -  </td></tr>
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
    public DeleteDatabase200Response deleteDatabase(Integer dbId, String hash, String code) throws ApiException {
        ApiResponse<DeleteDatabase200Response> localVarResp = deleteDatabaseWithHttpInfo(dbId, hash, code);
        return localVarResp.getData();
    }

    /**
     * Удаление базы данных
     * Чтобы удалить базу данных, отправьте запрос DELETE в &#x60;api/v1/dbs/{db_id}&#x60;. 
     * @param dbId ID базы данных (required)
     * @param hash Хеш, который совместно с кодом авторизации надо отправить для удаления, если включено подтверждение удаления сервисов через Телеграм. (optional)
     * @param code Код подтверждения, который придет к вам в Телеграм, после запроса удаления, если включено подтверждение удаления сервисов.  При помощи API токена сервисы можно удалять без подтверждения, если параметр токена &#x60;is_able_to_delete&#x60; установлен в значение &#x60;true&#x60; (optional)
     * @return ApiResponse&lt;DeleteDatabase200Response&gt;
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> Объект JSON c ключом &#x60;database_delete&#x60; </td><td>  -  </td></tr>
        <tr><td> 204 </td><td> База данных успешно удалена. </td><td>  -  </td></tr>
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
    public ApiResponse<DeleteDatabase200Response> deleteDatabaseWithHttpInfo(Integer dbId, String hash, String code) throws ApiException {
        okhttp3.Call localVarCall = deleteDatabaseValidateBeforeCall(dbId, hash, code, null);
        Type localVarReturnType = new TypeToken<DeleteDatabase200Response>(){}.getType();
        return localVarApiClient.execute(localVarCall, localVarReturnType);
    }

    /**
     * Удаление базы данных (asynchronously)
     * Чтобы удалить базу данных, отправьте запрос DELETE в &#x60;api/v1/dbs/{db_id}&#x60;. 
     * @param dbId ID базы данных (required)
     * @param hash Хеш, который совместно с кодом авторизации надо отправить для удаления, если включено подтверждение удаления сервисов через Телеграм. (optional)
     * @param code Код подтверждения, который придет к вам в Телеграм, после запроса удаления, если включено подтверждение удаления сервисов.  При помощи API токена сервисы можно удалять без подтверждения, если параметр токена &#x60;is_able_to_delete&#x60; установлен в значение &#x60;true&#x60; (optional)
     * @param _callback The callback to be executed when the API call finishes
     * @return The request call
     * @throws ApiException If fail to process the API call, e.g. serializing the request body object
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> Объект JSON c ключом &#x60;database_delete&#x60; </td><td>  -  </td></tr>
        <tr><td> 204 </td><td> База данных успешно удалена. </td><td>  -  </td></tr>
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
    public okhttp3.Call deleteDatabaseAsync(Integer dbId, String hash, String code, final ApiCallback<DeleteDatabase200Response> _callback) throws ApiException {

        okhttp3.Call localVarCall = deleteDatabaseValidateBeforeCall(dbId, hash, code, _callback);
        Type localVarReturnType = new TypeToken<DeleteDatabase200Response>(){}.getType();
        localVarApiClient.executeAsync(localVarCall, localVarReturnType, _callback);
        return localVarCall;
    }
    /**
     * Build call for deleteDatabaseBackup
     * @param dbId ID базы данных (required)
     * @param backupId ID резевной копии (required)
     * @param _callback Callback for upload/download progress
     * @return Call to execute
     * @throws ApiException If fail to serialize the request body object
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 204 </td><td> Бэкап успешно удален. </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Некорректный запрос </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Не авторизован </td><td>  -  </td></tr>
        <tr><td> 403 </td><td> Запрещено </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Не найдено </td><td>  -  </td></tr>
        <tr><td> 429 </td><td> Слишком много запросов </td><td>  -  </td></tr>
        <tr><td> 500 </td><td> Внутренняя ошибка сервера </td><td>  -  </td></tr>
     </table>
     */
    public okhttp3.Call deleteDatabaseBackupCall(Integer dbId, Integer backupId, final ApiCallback _callback) throws ApiException {
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
        String localVarPath = "/api/v1/dbs/{db_id}/backups/{backup_id}"
            .replace("{" + "db_id" + "}", localVarApiClient.escapeString(dbId.toString()))
            .replace("{" + "backup_id" + "}", localVarApiClient.escapeString(backupId.toString()));

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
    private okhttp3.Call deleteDatabaseBackupValidateBeforeCall(Integer dbId, Integer backupId, final ApiCallback _callback) throws ApiException {
        // verify the required parameter 'dbId' is set
        if (dbId == null) {
            throw new ApiException("Missing the required parameter 'dbId' when calling deleteDatabaseBackup(Async)");
        }

        // verify the required parameter 'backupId' is set
        if (backupId == null) {
            throw new ApiException("Missing the required parameter 'backupId' when calling deleteDatabaseBackup(Async)");
        }

        return deleteDatabaseBackupCall(dbId, backupId, _callback);

    }

    /**
     * Удаление бэкапа базы данных
     * Чтобы удалить бэкап базы данных, отправьте запрос DELETE в &#x60;api/v1/dbs/{db_id}/backups/{backup_id}&#x60;. 
     * @param dbId ID базы данных (required)
     * @param backupId ID резевной копии (required)
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 204 </td><td> Бэкап успешно удален. </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Некорректный запрос </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Не авторизован </td><td>  -  </td></tr>
        <tr><td> 403 </td><td> Запрещено </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Не найдено </td><td>  -  </td></tr>
        <tr><td> 429 </td><td> Слишком много запросов </td><td>  -  </td></tr>
        <tr><td> 500 </td><td> Внутренняя ошибка сервера </td><td>  -  </td></tr>
     </table>
     */
    public void deleteDatabaseBackup(Integer dbId, Integer backupId) throws ApiException {
        deleteDatabaseBackupWithHttpInfo(dbId, backupId);
    }

    /**
     * Удаление бэкапа базы данных
     * Чтобы удалить бэкап базы данных, отправьте запрос DELETE в &#x60;api/v1/dbs/{db_id}/backups/{backup_id}&#x60;. 
     * @param dbId ID базы данных (required)
     * @param backupId ID резевной копии (required)
     * @return ApiResponse&lt;Void&gt;
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 204 </td><td> Бэкап успешно удален. </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Некорректный запрос </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Не авторизован </td><td>  -  </td></tr>
        <tr><td> 403 </td><td> Запрещено </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Не найдено </td><td>  -  </td></tr>
        <tr><td> 429 </td><td> Слишком много запросов </td><td>  -  </td></tr>
        <tr><td> 500 </td><td> Внутренняя ошибка сервера </td><td>  -  </td></tr>
     </table>
     */
    public ApiResponse<Void> deleteDatabaseBackupWithHttpInfo(Integer dbId, Integer backupId) throws ApiException {
        okhttp3.Call localVarCall = deleteDatabaseBackupValidateBeforeCall(dbId, backupId, null);
        return localVarApiClient.execute(localVarCall);
    }

    /**
     * Удаление бэкапа базы данных (asynchronously)
     * Чтобы удалить бэкап базы данных, отправьте запрос DELETE в &#x60;api/v1/dbs/{db_id}/backups/{backup_id}&#x60;. 
     * @param dbId ID базы данных (required)
     * @param backupId ID резевной копии (required)
     * @param _callback The callback to be executed when the API call finishes
     * @return The request call
     * @throws ApiException If fail to process the API call, e.g. serializing the request body object
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 204 </td><td> Бэкап успешно удален. </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Некорректный запрос </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Не авторизован </td><td>  -  </td></tr>
        <tr><td> 403 </td><td> Запрещено </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Не найдено </td><td>  -  </td></tr>
        <tr><td> 429 </td><td> Слишком много запросов </td><td>  -  </td></tr>
        <tr><td> 500 </td><td> Внутренняя ошибка сервера </td><td>  -  </td></tr>
     </table>
     */
    public okhttp3.Call deleteDatabaseBackupAsync(Integer dbId, Integer backupId, final ApiCallback<Void> _callback) throws ApiException {

        okhttp3.Call localVarCall = deleteDatabaseBackupValidateBeforeCall(dbId, backupId, _callback);
        localVarApiClient.executeAsync(localVarCall, _callback);
        return localVarCall;
    }
    /**
     * Build call for deleteDatabaseCluster
     * @param dbClusterId ID кластера базы данных (required)
     * @param hash Хеш, который совместно с кодом авторизации надо отправить для удаления, если включено подтверждение удаления сервисов через Телеграм. (optional)
     * @param code Код подтверждения, который придет к вам в Телеграм, после запроса удаления, если включено подтверждение удаления сервисов.  При помощи API токена сервисы можно удалять без подтверждения, если параметр токена &#x60;is_able_to_delete&#x60; установлен в значение &#x60;true&#x60; (optional)
     * @param _callback Callback for upload/download progress
     * @return Call to execute
     * @throws ApiException If fail to serialize the request body object
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> Объект JSON c ключом &#x60;hash&#x60; </td><td>  -  </td></tr>
        <tr><td> 204 </td><td> Кластер базы данных удален. </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Некорректный запрос </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Не авторизован </td><td>  -  </td></tr>
        <tr><td> 403 </td><td> Запрещено </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Не найдено </td><td>  -  </td></tr>
        <tr><td> 429 </td><td> Слишком много запросов </td><td>  -  </td></tr>
        <tr><td> 500 </td><td> Внутренняя ошибка сервера </td><td>  -  </td></tr>
     </table>
     */
    public okhttp3.Call deleteDatabaseClusterCall(Integer dbClusterId, String hash, String code, final ApiCallback _callback) throws ApiException {
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
        String localVarPath = "/api/v1/databases/{db_cluster_id}"
            .replace("{" + "db_cluster_id" + "}", localVarApiClient.escapeString(dbClusterId.toString()));

        List<Pair> localVarQueryParams = new ArrayList<Pair>();
        List<Pair> localVarCollectionQueryParams = new ArrayList<Pair>();
        Map<String, String> localVarHeaderParams = new HashMap<String, String>();
        Map<String, String> localVarCookieParams = new HashMap<String, String>();
        Map<String, Object> localVarFormParams = new HashMap<String, Object>();

        if (hash != null) {
            localVarQueryParams.addAll(localVarApiClient.parameterToPair("hash", hash));
        }

        if (code != null) {
            localVarQueryParams.addAll(localVarApiClient.parameterToPair("code", code));
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
        return localVarApiClient.buildCall(basePath, localVarPath, "DELETE", localVarQueryParams, localVarCollectionQueryParams, localVarPostBody, localVarHeaderParams, localVarCookieParams, localVarFormParams, localVarAuthNames, _callback);
    }

    @SuppressWarnings("rawtypes")
    private okhttp3.Call deleteDatabaseClusterValidateBeforeCall(Integer dbClusterId, String hash, String code, final ApiCallback _callback) throws ApiException {
        // verify the required parameter 'dbClusterId' is set
        if (dbClusterId == null) {
            throw new ApiException("Missing the required parameter 'dbClusterId' when calling deleteDatabaseCluster(Async)");
        }

        return deleteDatabaseClusterCall(dbClusterId, hash, code, _callback);

    }

    /**
     * Удаление кластера базы данных
     * Чтобы удалить кластер базы данных, отправьте DELETE-запрос на &#x60;/api/v1/databases/{db_cluster_id}&#x60;.
     * @param dbClusterId ID кластера базы данных (required)
     * @param hash Хеш, который совместно с кодом авторизации надо отправить для удаления, если включено подтверждение удаления сервисов через Телеграм. (optional)
     * @param code Код подтверждения, который придет к вам в Телеграм, после запроса удаления, если включено подтверждение удаления сервисов.  При помощи API токена сервисы можно удалять без подтверждения, если параметр токена &#x60;is_able_to_delete&#x60; установлен в значение &#x60;true&#x60; (optional)
     * @return DeleteDatabaseCluster200Response
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> Объект JSON c ключом &#x60;hash&#x60; </td><td>  -  </td></tr>
        <tr><td> 204 </td><td> Кластер базы данных удален. </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Некорректный запрос </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Не авторизован </td><td>  -  </td></tr>
        <tr><td> 403 </td><td> Запрещено </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Не найдено </td><td>  -  </td></tr>
        <tr><td> 429 </td><td> Слишком много запросов </td><td>  -  </td></tr>
        <tr><td> 500 </td><td> Внутренняя ошибка сервера </td><td>  -  </td></tr>
     </table>
     */
    public DeleteDatabaseCluster200Response deleteDatabaseCluster(Integer dbClusterId, String hash, String code) throws ApiException {
        ApiResponse<DeleteDatabaseCluster200Response> localVarResp = deleteDatabaseClusterWithHttpInfo(dbClusterId, hash, code);
        return localVarResp.getData();
    }

    /**
     * Удаление кластера базы данных
     * Чтобы удалить кластер базы данных, отправьте DELETE-запрос на &#x60;/api/v1/databases/{db_cluster_id}&#x60;.
     * @param dbClusterId ID кластера базы данных (required)
     * @param hash Хеш, который совместно с кодом авторизации надо отправить для удаления, если включено подтверждение удаления сервисов через Телеграм. (optional)
     * @param code Код подтверждения, который придет к вам в Телеграм, после запроса удаления, если включено подтверждение удаления сервисов.  При помощи API токена сервисы можно удалять без подтверждения, если параметр токена &#x60;is_able_to_delete&#x60; установлен в значение &#x60;true&#x60; (optional)
     * @return ApiResponse&lt;DeleteDatabaseCluster200Response&gt;
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> Объект JSON c ключом &#x60;hash&#x60; </td><td>  -  </td></tr>
        <tr><td> 204 </td><td> Кластер базы данных удален. </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Некорректный запрос </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Не авторизован </td><td>  -  </td></tr>
        <tr><td> 403 </td><td> Запрещено </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Не найдено </td><td>  -  </td></tr>
        <tr><td> 429 </td><td> Слишком много запросов </td><td>  -  </td></tr>
        <tr><td> 500 </td><td> Внутренняя ошибка сервера </td><td>  -  </td></tr>
     </table>
     */
    public ApiResponse<DeleteDatabaseCluster200Response> deleteDatabaseClusterWithHttpInfo(Integer dbClusterId, String hash, String code) throws ApiException {
        okhttp3.Call localVarCall = deleteDatabaseClusterValidateBeforeCall(dbClusterId, hash, code, null);
        Type localVarReturnType = new TypeToken<DeleteDatabaseCluster200Response>(){}.getType();
        return localVarApiClient.execute(localVarCall, localVarReturnType);
    }

    /**
     * Удаление кластера базы данных (asynchronously)
     * Чтобы удалить кластер базы данных, отправьте DELETE-запрос на &#x60;/api/v1/databases/{db_cluster_id}&#x60;.
     * @param dbClusterId ID кластера базы данных (required)
     * @param hash Хеш, который совместно с кодом авторизации надо отправить для удаления, если включено подтверждение удаления сервисов через Телеграм. (optional)
     * @param code Код подтверждения, который придет к вам в Телеграм, после запроса удаления, если включено подтверждение удаления сервисов.  При помощи API токена сервисы можно удалять без подтверждения, если параметр токена &#x60;is_able_to_delete&#x60; установлен в значение &#x60;true&#x60; (optional)
     * @param _callback The callback to be executed when the API call finishes
     * @return The request call
     * @throws ApiException If fail to process the API call, e.g. serializing the request body object
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> Объект JSON c ключом &#x60;hash&#x60; </td><td>  -  </td></tr>
        <tr><td> 204 </td><td> Кластер базы данных удален. </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Некорректный запрос </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Не авторизован </td><td>  -  </td></tr>
        <tr><td> 403 </td><td> Запрещено </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Не найдено </td><td>  -  </td></tr>
        <tr><td> 429 </td><td> Слишком много запросов </td><td>  -  </td></tr>
        <tr><td> 500 </td><td> Внутренняя ошибка сервера </td><td>  -  </td></tr>
     </table>
     */
    public okhttp3.Call deleteDatabaseClusterAsync(Integer dbClusterId, String hash, String code, final ApiCallback<DeleteDatabaseCluster200Response> _callback) throws ApiException {

        okhttp3.Call localVarCall = deleteDatabaseClusterValidateBeforeCall(dbClusterId, hash, code, _callback);
        Type localVarReturnType = new TypeToken<DeleteDatabaseCluster200Response>(){}.getType();
        localVarApiClient.executeAsync(localVarCall, localVarReturnType, _callback);
        return localVarCall;
    }
    /**
     * Build call for deleteDatabaseInstance
     * @param dbClusterId ID кластера базы данных (required)
     * @param instanceId ID инстанса базы данных (required)
     * @param _callback Callback for upload/download progress
     * @return Call to execute
     * @throws ApiException If fail to serialize the request body object
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 204 </td><td> Инстанс базы данных удален. </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Некорректный запрос </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Не авторизован </td><td>  -  </td></tr>
        <tr><td> 403 </td><td> Запрещено </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Не найдено </td><td>  -  </td></tr>
        <tr><td> 429 </td><td> Слишком много запросов </td><td>  -  </td></tr>
        <tr><td> 500 </td><td> Внутренняя ошибка сервера </td><td>  -  </td></tr>
     </table>
     */
    public okhttp3.Call deleteDatabaseInstanceCall(Integer dbClusterId, Integer instanceId, final ApiCallback _callback) throws ApiException {
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
        String localVarPath = "/api/v1/databases/{db_cluster_id}/instances/{instance_id}"
            .replace("{" + "db_cluster_id" + "}", localVarApiClient.escapeString(dbClusterId.toString()))
            .replace("{" + "instance_id" + "}", localVarApiClient.escapeString(instanceId.toString()));

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
    private okhttp3.Call deleteDatabaseInstanceValidateBeforeCall(Integer dbClusterId, Integer instanceId, final ApiCallback _callback) throws ApiException {
        // verify the required parameter 'dbClusterId' is set
        if (dbClusterId == null) {
            throw new ApiException("Missing the required parameter 'dbClusterId' when calling deleteDatabaseInstance(Async)");
        }

        // verify the required parameter 'instanceId' is set
        if (instanceId == null) {
            throw new ApiException("Missing the required parameter 'instanceId' when calling deleteDatabaseInstance(Async)");
        }

        return deleteDatabaseInstanceCall(dbClusterId, instanceId, _callback);

    }

    /**
     * Удаление инстанса базы данных
     * Чтобы удалить инстанс базы данных, отправьте DELETE-запрос на &#x60;/api/v1/databases/{db_cluster_id}/instances/{instance_id}&#x60;.
     * @param dbClusterId ID кластера базы данных (required)
     * @param instanceId ID инстанса базы данных (required)
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 204 </td><td> Инстанс базы данных удален. </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Некорректный запрос </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Не авторизован </td><td>  -  </td></tr>
        <tr><td> 403 </td><td> Запрещено </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Не найдено </td><td>  -  </td></tr>
        <tr><td> 429 </td><td> Слишком много запросов </td><td>  -  </td></tr>
        <tr><td> 500 </td><td> Внутренняя ошибка сервера </td><td>  -  </td></tr>
     </table>
     */
    public void deleteDatabaseInstance(Integer dbClusterId, Integer instanceId) throws ApiException {
        deleteDatabaseInstanceWithHttpInfo(dbClusterId, instanceId);
    }

    /**
     * Удаление инстанса базы данных
     * Чтобы удалить инстанс базы данных, отправьте DELETE-запрос на &#x60;/api/v1/databases/{db_cluster_id}/instances/{instance_id}&#x60;.
     * @param dbClusterId ID кластера базы данных (required)
     * @param instanceId ID инстанса базы данных (required)
     * @return ApiResponse&lt;Void&gt;
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 204 </td><td> Инстанс базы данных удален. </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Некорректный запрос </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Не авторизован </td><td>  -  </td></tr>
        <tr><td> 403 </td><td> Запрещено </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Не найдено </td><td>  -  </td></tr>
        <tr><td> 429 </td><td> Слишком много запросов </td><td>  -  </td></tr>
        <tr><td> 500 </td><td> Внутренняя ошибка сервера </td><td>  -  </td></tr>
     </table>
     */
    public ApiResponse<Void> deleteDatabaseInstanceWithHttpInfo(Integer dbClusterId, Integer instanceId) throws ApiException {
        okhttp3.Call localVarCall = deleteDatabaseInstanceValidateBeforeCall(dbClusterId, instanceId, null);
        return localVarApiClient.execute(localVarCall);
    }

    /**
     * Удаление инстанса базы данных (asynchronously)
     * Чтобы удалить инстанс базы данных, отправьте DELETE-запрос на &#x60;/api/v1/databases/{db_cluster_id}/instances/{instance_id}&#x60;.
     * @param dbClusterId ID кластера базы данных (required)
     * @param instanceId ID инстанса базы данных (required)
     * @param _callback The callback to be executed when the API call finishes
     * @return The request call
     * @throws ApiException If fail to process the API call, e.g. serializing the request body object
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 204 </td><td> Инстанс базы данных удален. </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Некорректный запрос </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Не авторизован </td><td>  -  </td></tr>
        <tr><td> 403 </td><td> Запрещено </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Не найдено </td><td>  -  </td></tr>
        <tr><td> 429 </td><td> Слишком много запросов </td><td>  -  </td></tr>
        <tr><td> 500 </td><td> Внутренняя ошибка сервера </td><td>  -  </td></tr>
     </table>
     */
    public okhttp3.Call deleteDatabaseInstanceAsync(Integer dbClusterId, Integer instanceId, final ApiCallback<Void> _callback) throws ApiException {

        okhttp3.Call localVarCall = deleteDatabaseInstanceValidateBeforeCall(dbClusterId, instanceId, _callback);
        localVarApiClient.executeAsync(localVarCall, _callback);
        return localVarCall;
    }
    /**
     * Build call for deleteDatabaseUser
     * @param dbClusterId ID кластера базы данных (required)
     * @param adminId ID пользователя базы данных (required)
     * @param _callback Callback for upload/download progress
     * @return Call to execute
     * @throws ApiException If fail to serialize the request body object
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 204 </td><td> Пользователь базы данных удален. </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Некорректный запрос </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Не авторизован </td><td>  -  </td></tr>
        <tr><td> 403 </td><td> Запрещено </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Не найдено </td><td>  -  </td></tr>
        <tr><td> 429 </td><td> Слишком много запросов </td><td>  -  </td></tr>
        <tr><td> 500 </td><td> Внутренняя ошибка сервера </td><td>  -  </td></tr>
     </table>
     */
    public okhttp3.Call deleteDatabaseUserCall(Integer dbClusterId, Integer adminId, final ApiCallback _callback) throws ApiException {
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
        String localVarPath = "/api/v1/databases/{db_cluster_id}/admins/{admin_id}"
            .replace("{" + "db_cluster_id" + "}", localVarApiClient.escapeString(dbClusterId.toString()))
            .replace("{" + "admin_id" + "}", localVarApiClient.escapeString(adminId.toString()));

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
    private okhttp3.Call deleteDatabaseUserValidateBeforeCall(Integer dbClusterId, Integer adminId, final ApiCallback _callback) throws ApiException {
        // verify the required parameter 'dbClusterId' is set
        if (dbClusterId == null) {
            throw new ApiException("Missing the required parameter 'dbClusterId' when calling deleteDatabaseUser(Async)");
        }

        // verify the required parameter 'adminId' is set
        if (adminId == null) {
            throw new ApiException("Missing the required parameter 'adminId' when calling deleteDatabaseUser(Async)");
        }

        return deleteDatabaseUserCall(dbClusterId, adminId, _callback);

    }

    /**
     * Удаление пользователя базы данных
     * Чтобы удалить пользователя базы данных на вашем аккаунте, отправьте DELETE-запрос на &#x60;/api/v1/databases/{db_cluster_id}/admins/{admin_id}&#x60;.
     * @param dbClusterId ID кластера базы данных (required)
     * @param adminId ID пользователя базы данных (required)
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 204 </td><td> Пользователь базы данных удален. </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Некорректный запрос </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Не авторизован </td><td>  -  </td></tr>
        <tr><td> 403 </td><td> Запрещено </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Не найдено </td><td>  -  </td></tr>
        <tr><td> 429 </td><td> Слишком много запросов </td><td>  -  </td></tr>
        <tr><td> 500 </td><td> Внутренняя ошибка сервера </td><td>  -  </td></tr>
     </table>
     */
    public void deleteDatabaseUser(Integer dbClusterId, Integer adminId) throws ApiException {
        deleteDatabaseUserWithHttpInfo(dbClusterId, adminId);
    }

    /**
     * Удаление пользователя базы данных
     * Чтобы удалить пользователя базы данных на вашем аккаунте, отправьте DELETE-запрос на &#x60;/api/v1/databases/{db_cluster_id}/admins/{admin_id}&#x60;.
     * @param dbClusterId ID кластера базы данных (required)
     * @param adminId ID пользователя базы данных (required)
     * @return ApiResponse&lt;Void&gt;
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 204 </td><td> Пользователь базы данных удален. </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Некорректный запрос </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Не авторизован </td><td>  -  </td></tr>
        <tr><td> 403 </td><td> Запрещено </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Не найдено </td><td>  -  </td></tr>
        <tr><td> 429 </td><td> Слишком много запросов </td><td>  -  </td></tr>
        <tr><td> 500 </td><td> Внутренняя ошибка сервера </td><td>  -  </td></tr>
     </table>
     */
    public ApiResponse<Void> deleteDatabaseUserWithHttpInfo(Integer dbClusterId, Integer adminId) throws ApiException {
        okhttp3.Call localVarCall = deleteDatabaseUserValidateBeforeCall(dbClusterId, adminId, null);
        return localVarApiClient.execute(localVarCall);
    }

    /**
     * Удаление пользователя базы данных (asynchronously)
     * Чтобы удалить пользователя базы данных на вашем аккаунте, отправьте DELETE-запрос на &#x60;/api/v1/databases/{db_cluster_id}/admins/{admin_id}&#x60;.
     * @param dbClusterId ID кластера базы данных (required)
     * @param adminId ID пользователя базы данных (required)
     * @param _callback The callback to be executed when the API call finishes
     * @return The request call
     * @throws ApiException If fail to process the API call, e.g. serializing the request body object
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 204 </td><td> Пользователь базы данных удален. </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Некорректный запрос </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Не авторизован </td><td>  -  </td></tr>
        <tr><td> 403 </td><td> Запрещено </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Не найдено </td><td>  -  </td></tr>
        <tr><td> 429 </td><td> Слишком много запросов </td><td>  -  </td></tr>
        <tr><td> 500 </td><td> Внутренняя ошибка сервера </td><td>  -  </td></tr>
     </table>
     */
    public okhttp3.Call deleteDatabaseUserAsync(Integer dbClusterId, Integer adminId, final ApiCallback<Void> _callback) throws ApiException {

        okhttp3.Call localVarCall = deleteDatabaseUserValidateBeforeCall(dbClusterId, adminId, _callback);
        localVarApiClient.executeAsync(localVarCall, _callback);
        return localVarCall;
    }
    /**
     * Build call for getDatabase
     * @param dbId ID базы данных (required)
     * @param _callback Callback for upload/download progress
     * @return Call to execute
     * @throws ApiException If fail to serialize the request body object
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> Ответ будет представлять собой объект JSON с ключом &#x60;db&#x60;. </td><td>  -  </td></tr>
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
    public okhttp3.Call getDatabaseCall(Integer dbId, final ApiCallback _callback) throws ApiException {
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
        String localVarPath = "/api/v1/dbs/{db_id}"
            .replace("{" + "db_id" + "}", localVarApiClient.escapeString(dbId.toString()));

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

    @Deprecated
    @SuppressWarnings("rawtypes")
    private okhttp3.Call getDatabaseValidateBeforeCall(Integer dbId, final ApiCallback _callback) throws ApiException {
        // verify the required parameter 'dbId' is set
        if (dbId == null) {
            throw new ApiException("Missing the required parameter 'dbId' when calling getDatabase(Async)");
        }

        return getDatabaseCall(dbId, _callback);

    }

    /**
     * Получение базы данных
     * Чтобы отобразить информацию об отдельной базе данных, отправьте запрос GET на &#x60;api/v1/dbs/{db_id}&#x60;. 
     * @param dbId ID базы данных (required)
     * @return CreateDatabase201Response
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> Ответ будет представлять собой объект JSON с ключом &#x60;db&#x60;. </td><td>  -  </td></tr>
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
    public CreateDatabase201Response getDatabase(Integer dbId) throws ApiException {
        ApiResponse<CreateDatabase201Response> localVarResp = getDatabaseWithHttpInfo(dbId);
        return localVarResp.getData();
    }

    /**
     * Получение базы данных
     * Чтобы отобразить информацию об отдельной базе данных, отправьте запрос GET на &#x60;api/v1/dbs/{db_id}&#x60;. 
     * @param dbId ID базы данных (required)
     * @return ApiResponse&lt;CreateDatabase201Response&gt;
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> Ответ будет представлять собой объект JSON с ключом &#x60;db&#x60;. </td><td>  -  </td></tr>
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
    public ApiResponse<CreateDatabase201Response> getDatabaseWithHttpInfo(Integer dbId) throws ApiException {
        okhttp3.Call localVarCall = getDatabaseValidateBeforeCall(dbId, null);
        Type localVarReturnType = new TypeToken<CreateDatabase201Response>(){}.getType();
        return localVarApiClient.execute(localVarCall, localVarReturnType);
    }

    /**
     * Получение базы данных (asynchronously)
     * Чтобы отобразить информацию об отдельной базе данных, отправьте запрос GET на &#x60;api/v1/dbs/{db_id}&#x60;. 
     * @param dbId ID базы данных (required)
     * @param _callback The callback to be executed when the API call finishes
     * @return The request call
     * @throws ApiException If fail to process the API call, e.g. serializing the request body object
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> Ответ будет представлять собой объект JSON с ключом &#x60;db&#x60;. </td><td>  -  </td></tr>
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
    public okhttp3.Call getDatabaseAsync(Integer dbId, final ApiCallback<CreateDatabase201Response> _callback) throws ApiException {

        okhttp3.Call localVarCall = getDatabaseValidateBeforeCall(dbId, _callback);
        Type localVarReturnType = new TypeToken<CreateDatabase201Response>(){}.getType();
        localVarApiClient.executeAsync(localVarCall, localVarReturnType, _callback);
        return localVarCall;
    }
    /**
     * Build call for getDatabaseAutoBackupsSettings
     * @param dbId ID базы данных (required)
     * @param _callback Callback for upload/download progress
     * @return Call to execute
     * @throws ApiException If fail to serialize the request body object
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> Ответ будет представлять собой объект JSON с ключом &#x60;auto_backups_settings&#x60;. </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Некорректный запрос </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Не авторизован </td><td>  -  </td></tr>
        <tr><td> 403 </td><td> Запрещено </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Не найдено </td><td>  -  </td></tr>
        <tr><td> 429 </td><td> Слишком много запросов </td><td>  -  </td></tr>
        <tr><td> 500 </td><td> Внутренняя ошибка сервера </td><td>  -  </td></tr>
     </table>
     */
    public okhttp3.Call getDatabaseAutoBackupsSettingsCall(Integer dbId, final ApiCallback _callback) throws ApiException {
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
        String localVarPath = "/api/v1/dbs/{db_id}/auto-backups"
            .replace("{" + "db_id" + "}", localVarApiClient.escapeString(dbId.toString()));

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
    private okhttp3.Call getDatabaseAutoBackupsSettingsValidateBeforeCall(Integer dbId, final ApiCallback _callback) throws ApiException {
        // verify the required parameter 'dbId' is set
        if (dbId == null) {
            throw new ApiException("Missing the required parameter 'dbId' when calling getDatabaseAutoBackupsSettings(Async)");
        }

        return getDatabaseAutoBackupsSettingsCall(dbId, _callback);

    }

    /**
     * Получение настроек автобэкапов базы данных
     * Чтобы получить список настроек автобэкапов базы данных, отправьте запрос GET в &#x60;api/v1/dbs/{db_id}/auto-backups&#x60;
     * @param dbId ID базы данных (required)
     * @return GetDatabaseAutoBackupsSettings200Response
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> Ответ будет представлять собой объект JSON с ключом &#x60;auto_backups_settings&#x60;. </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Некорректный запрос </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Не авторизован </td><td>  -  </td></tr>
        <tr><td> 403 </td><td> Запрещено </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Не найдено </td><td>  -  </td></tr>
        <tr><td> 429 </td><td> Слишком много запросов </td><td>  -  </td></tr>
        <tr><td> 500 </td><td> Внутренняя ошибка сервера </td><td>  -  </td></tr>
     </table>
     */
    public GetDatabaseAutoBackupsSettings200Response getDatabaseAutoBackupsSettings(Integer dbId) throws ApiException {
        ApiResponse<GetDatabaseAutoBackupsSettings200Response> localVarResp = getDatabaseAutoBackupsSettingsWithHttpInfo(dbId);
        return localVarResp.getData();
    }

    /**
     * Получение настроек автобэкапов базы данных
     * Чтобы получить список настроек автобэкапов базы данных, отправьте запрос GET в &#x60;api/v1/dbs/{db_id}/auto-backups&#x60;
     * @param dbId ID базы данных (required)
     * @return ApiResponse&lt;GetDatabaseAutoBackupsSettings200Response&gt;
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> Ответ будет представлять собой объект JSON с ключом &#x60;auto_backups_settings&#x60;. </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Некорректный запрос </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Не авторизован </td><td>  -  </td></tr>
        <tr><td> 403 </td><td> Запрещено </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Не найдено </td><td>  -  </td></tr>
        <tr><td> 429 </td><td> Слишком много запросов </td><td>  -  </td></tr>
        <tr><td> 500 </td><td> Внутренняя ошибка сервера </td><td>  -  </td></tr>
     </table>
     */
    public ApiResponse<GetDatabaseAutoBackupsSettings200Response> getDatabaseAutoBackupsSettingsWithHttpInfo(Integer dbId) throws ApiException {
        okhttp3.Call localVarCall = getDatabaseAutoBackupsSettingsValidateBeforeCall(dbId, null);
        Type localVarReturnType = new TypeToken<GetDatabaseAutoBackupsSettings200Response>(){}.getType();
        return localVarApiClient.execute(localVarCall, localVarReturnType);
    }

    /**
     * Получение настроек автобэкапов базы данных (asynchronously)
     * Чтобы получить список настроек автобэкапов базы данных, отправьте запрос GET в &#x60;api/v1/dbs/{db_id}/auto-backups&#x60;
     * @param dbId ID базы данных (required)
     * @param _callback The callback to be executed when the API call finishes
     * @return The request call
     * @throws ApiException If fail to process the API call, e.g. serializing the request body object
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> Ответ будет представлять собой объект JSON с ключом &#x60;auto_backups_settings&#x60;. </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Некорректный запрос </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Не авторизован </td><td>  -  </td></tr>
        <tr><td> 403 </td><td> Запрещено </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Не найдено </td><td>  -  </td></tr>
        <tr><td> 429 </td><td> Слишком много запросов </td><td>  -  </td></tr>
        <tr><td> 500 </td><td> Внутренняя ошибка сервера </td><td>  -  </td></tr>
     </table>
     */
    public okhttp3.Call getDatabaseAutoBackupsSettingsAsync(Integer dbId, final ApiCallback<GetDatabaseAutoBackupsSettings200Response> _callback) throws ApiException {

        okhttp3.Call localVarCall = getDatabaseAutoBackupsSettingsValidateBeforeCall(dbId, _callback);
        Type localVarReturnType = new TypeToken<GetDatabaseAutoBackupsSettings200Response>(){}.getType();
        localVarApiClient.executeAsync(localVarCall, localVarReturnType, _callback);
        return localVarCall;
    }
    /**
     * Build call for getDatabaseBackup
     * @param dbId ID базы данных (required)
     * @param backupId ID резевной копии (required)
     * @param _callback Callback for upload/download progress
     * @return Call to execute
     * @throws ApiException If fail to serialize the request body object
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> Ответ будет представлять собой объект JSON с ключом &#x60;backup&#x60;. </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Некорректный запрос </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Не авторизован </td><td>  -  </td></tr>
        <tr><td> 403 </td><td> Запрещено </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Не найдено </td><td>  -  </td></tr>
        <tr><td> 429 </td><td> Слишком много запросов </td><td>  -  </td></tr>
        <tr><td> 500 </td><td> Внутренняя ошибка сервера </td><td>  -  </td></tr>
     </table>
     */
    public okhttp3.Call getDatabaseBackupCall(Integer dbId, Integer backupId, final ApiCallback _callback) throws ApiException {
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
        String localVarPath = "/api/v1/dbs/{db_id}/backups/{backup_id}"
            .replace("{" + "db_id" + "}", localVarApiClient.escapeString(dbId.toString()))
            .replace("{" + "backup_id" + "}", localVarApiClient.escapeString(backupId.toString()));

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
    private okhttp3.Call getDatabaseBackupValidateBeforeCall(Integer dbId, Integer backupId, final ApiCallback _callback) throws ApiException {
        // verify the required parameter 'dbId' is set
        if (dbId == null) {
            throw new ApiException("Missing the required parameter 'dbId' when calling getDatabaseBackup(Async)");
        }

        // verify the required parameter 'backupId' is set
        if (backupId == null) {
            throw new ApiException("Missing the required parameter 'backupId' when calling getDatabaseBackup(Async)");
        }

        return getDatabaseBackupCall(dbId, backupId, _callback);

    }

    /**
     * Получение бэкапа базы данных
     * Чтобы получить бэкап базы данных, отправьте запрос GET в &#x60;api/v1/dbs/{db_id}/backups/{backup_id}&#x60;. 
     * @param dbId ID базы данных (required)
     * @param backupId ID резевной копии (required)
     * @return CreateDatabaseBackup201Response
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> Ответ будет представлять собой объект JSON с ключом &#x60;backup&#x60;. </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Некорректный запрос </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Не авторизован </td><td>  -  </td></tr>
        <tr><td> 403 </td><td> Запрещено </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Не найдено </td><td>  -  </td></tr>
        <tr><td> 429 </td><td> Слишком много запросов </td><td>  -  </td></tr>
        <tr><td> 500 </td><td> Внутренняя ошибка сервера </td><td>  -  </td></tr>
     </table>
     */
    public CreateDatabaseBackup201Response getDatabaseBackup(Integer dbId, Integer backupId) throws ApiException {
        ApiResponse<CreateDatabaseBackup201Response> localVarResp = getDatabaseBackupWithHttpInfo(dbId, backupId);
        return localVarResp.getData();
    }

    /**
     * Получение бэкапа базы данных
     * Чтобы получить бэкап базы данных, отправьте запрос GET в &#x60;api/v1/dbs/{db_id}/backups/{backup_id}&#x60;. 
     * @param dbId ID базы данных (required)
     * @param backupId ID резевной копии (required)
     * @return ApiResponse&lt;CreateDatabaseBackup201Response&gt;
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> Ответ будет представлять собой объект JSON с ключом &#x60;backup&#x60;. </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Некорректный запрос </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Не авторизован </td><td>  -  </td></tr>
        <tr><td> 403 </td><td> Запрещено </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Не найдено </td><td>  -  </td></tr>
        <tr><td> 429 </td><td> Слишком много запросов </td><td>  -  </td></tr>
        <tr><td> 500 </td><td> Внутренняя ошибка сервера </td><td>  -  </td></tr>
     </table>
     */
    public ApiResponse<CreateDatabaseBackup201Response> getDatabaseBackupWithHttpInfo(Integer dbId, Integer backupId) throws ApiException {
        okhttp3.Call localVarCall = getDatabaseBackupValidateBeforeCall(dbId, backupId, null);
        Type localVarReturnType = new TypeToken<CreateDatabaseBackup201Response>(){}.getType();
        return localVarApiClient.execute(localVarCall, localVarReturnType);
    }

    /**
     * Получение бэкапа базы данных (asynchronously)
     * Чтобы получить бэкап базы данных, отправьте запрос GET в &#x60;api/v1/dbs/{db_id}/backups/{backup_id}&#x60;. 
     * @param dbId ID базы данных (required)
     * @param backupId ID резевной копии (required)
     * @param _callback The callback to be executed when the API call finishes
     * @return The request call
     * @throws ApiException If fail to process the API call, e.g. serializing the request body object
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> Ответ будет представлять собой объект JSON с ключом &#x60;backup&#x60;. </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Некорректный запрос </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Не авторизован </td><td>  -  </td></tr>
        <tr><td> 403 </td><td> Запрещено </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Не найдено </td><td>  -  </td></tr>
        <tr><td> 429 </td><td> Слишком много запросов </td><td>  -  </td></tr>
        <tr><td> 500 </td><td> Внутренняя ошибка сервера </td><td>  -  </td></tr>
     </table>
     */
    public okhttp3.Call getDatabaseBackupAsync(Integer dbId, Integer backupId, final ApiCallback<CreateDatabaseBackup201Response> _callback) throws ApiException {

        okhttp3.Call localVarCall = getDatabaseBackupValidateBeforeCall(dbId, backupId, _callback);
        Type localVarReturnType = new TypeToken<CreateDatabaseBackup201Response>(){}.getType();
        localVarApiClient.executeAsync(localVarCall, localVarReturnType, _callback);
        return localVarCall;
    }
    /**
     * Build call for getDatabaseBackups
     * @param dbId ID базы данных (required)
     * @param limit Обозначает количество записей, которое необходимо вернуть. (optional, default to 100)
     * @param offset Указывает на смещение относительно начала списка. (optional, default to 0)
     * @param _callback Callback for upload/download progress
     * @return Call to execute
     * @throws ApiException If fail to serialize the request body object
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> Ответ будет представлять собой объект JSON с ключом &#x60;backups&#x60;. </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Некорректный запрос </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Не авторизован </td><td>  -  </td></tr>
        <tr><td> 403 </td><td> Запрещено </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Не найдено </td><td>  -  </td></tr>
        <tr><td> 429 </td><td> Слишком много запросов </td><td>  -  </td></tr>
        <tr><td> 500 </td><td> Внутренняя ошибка сервера </td><td>  -  </td></tr>
     </table>
     */
    public okhttp3.Call getDatabaseBackupsCall(Integer dbId, Integer limit, Integer offset, final ApiCallback _callback) throws ApiException {
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
        String localVarPath = "/api/v1/dbs/{db_id}/backups"
            .replace("{" + "db_id" + "}", localVarApiClient.escapeString(dbId.toString()));

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
    private okhttp3.Call getDatabaseBackupsValidateBeforeCall(Integer dbId, Integer limit, Integer offset, final ApiCallback _callback) throws ApiException {
        // verify the required parameter 'dbId' is set
        if (dbId == null) {
            throw new ApiException("Missing the required parameter 'dbId' when calling getDatabaseBackups(Async)");
        }

        return getDatabaseBackupsCall(dbId, limit, offset, _callback);

    }

    /**
     * Список бэкапов базы данных
     * Чтобы получить список бэкапов базы данных, отправьте запрос GET в &#x60;api/v1/dbs/{db_id}/backups&#x60;. 
     * @param dbId ID базы данных (required)
     * @param limit Обозначает количество записей, которое необходимо вернуть. (optional, default to 100)
     * @param offset Указывает на смещение относительно начала списка. (optional, default to 0)
     * @return GetDatabaseBackups200Response
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> Ответ будет представлять собой объект JSON с ключом &#x60;backups&#x60;. </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Некорректный запрос </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Не авторизован </td><td>  -  </td></tr>
        <tr><td> 403 </td><td> Запрещено </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Не найдено </td><td>  -  </td></tr>
        <tr><td> 429 </td><td> Слишком много запросов </td><td>  -  </td></tr>
        <tr><td> 500 </td><td> Внутренняя ошибка сервера </td><td>  -  </td></tr>
     </table>
     */
    public GetDatabaseBackups200Response getDatabaseBackups(Integer dbId, Integer limit, Integer offset) throws ApiException {
        ApiResponse<GetDatabaseBackups200Response> localVarResp = getDatabaseBackupsWithHttpInfo(dbId, limit, offset);
        return localVarResp.getData();
    }

    /**
     * Список бэкапов базы данных
     * Чтобы получить список бэкапов базы данных, отправьте запрос GET в &#x60;api/v1/dbs/{db_id}/backups&#x60;. 
     * @param dbId ID базы данных (required)
     * @param limit Обозначает количество записей, которое необходимо вернуть. (optional, default to 100)
     * @param offset Указывает на смещение относительно начала списка. (optional, default to 0)
     * @return ApiResponse&lt;GetDatabaseBackups200Response&gt;
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> Ответ будет представлять собой объект JSON с ключом &#x60;backups&#x60;. </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Некорректный запрос </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Не авторизован </td><td>  -  </td></tr>
        <tr><td> 403 </td><td> Запрещено </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Не найдено </td><td>  -  </td></tr>
        <tr><td> 429 </td><td> Слишком много запросов </td><td>  -  </td></tr>
        <tr><td> 500 </td><td> Внутренняя ошибка сервера </td><td>  -  </td></tr>
     </table>
     */
    public ApiResponse<GetDatabaseBackups200Response> getDatabaseBackupsWithHttpInfo(Integer dbId, Integer limit, Integer offset) throws ApiException {
        okhttp3.Call localVarCall = getDatabaseBackupsValidateBeforeCall(dbId, limit, offset, null);
        Type localVarReturnType = new TypeToken<GetDatabaseBackups200Response>(){}.getType();
        return localVarApiClient.execute(localVarCall, localVarReturnType);
    }

    /**
     * Список бэкапов базы данных (asynchronously)
     * Чтобы получить список бэкапов базы данных, отправьте запрос GET в &#x60;api/v1/dbs/{db_id}/backups&#x60;. 
     * @param dbId ID базы данных (required)
     * @param limit Обозначает количество записей, которое необходимо вернуть. (optional, default to 100)
     * @param offset Указывает на смещение относительно начала списка. (optional, default to 0)
     * @param _callback The callback to be executed when the API call finishes
     * @return The request call
     * @throws ApiException If fail to process the API call, e.g. serializing the request body object
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> Ответ будет представлять собой объект JSON с ключом &#x60;backups&#x60;. </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Некорректный запрос </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Не авторизован </td><td>  -  </td></tr>
        <tr><td> 403 </td><td> Запрещено </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Не найдено </td><td>  -  </td></tr>
        <tr><td> 429 </td><td> Слишком много запросов </td><td>  -  </td></tr>
        <tr><td> 500 </td><td> Внутренняя ошибка сервера </td><td>  -  </td></tr>
     </table>
     */
    public okhttp3.Call getDatabaseBackupsAsync(Integer dbId, Integer limit, Integer offset, final ApiCallback<GetDatabaseBackups200Response> _callback) throws ApiException {

        okhttp3.Call localVarCall = getDatabaseBackupsValidateBeforeCall(dbId, limit, offset, _callback);
        Type localVarReturnType = new TypeToken<GetDatabaseBackups200Response>(){}.getType();
        localVarApiClient.executeAsync(localVarCall, localVarReturnType, _callback);
        return localVarCall;
    }
    /**
     * Build call for getDatabaseCluster
     * @param dbClusterId ID кластера базы данных (required)
     * @param _callback Callback for upload/download progress
     * @return Call to execute
     * @throws ApiException If fail to serialize the request body object
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 201 </td><td> Ответ будет представлять собой объект JSON c ключом &#x60;db&#x60;. </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Некорректный запрос </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Не авторизован </td><td>  -  </td></tr>
        <tr><td> 403 </td><td> Запрещено </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Не найдено </td><td>  -  </td></tr>
        <tr><td> 429 </td><td> Слишком много запросов </td><td>  -  </td></tr>
        <tr><td> 500 </td><td> Внутренняя ошибка сервера </td><td>  -  </td></tr>
     </table>
     */
    public okhttp3.Call getDatabaseClusterCall(Integer dbClusterId, final ApiCallback _callback) throws ApiException {
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
        String localVarPath = "/api/v1/databases/{db_cluster_id}"
            .replace("{" + "db_cluster_id" + "}", localVarApiClient.escapeString(dbClusterId.toString()));

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
    private okhttp3.Call getDatabaseClusterValidateBeforeCall(Integer dbClusterId, final ApiCallback _callback) throws ApiException {
        // verify the required parameter 'dbClusterId' is set
        if (dbClusterId == null) {
            throw new ApiException("Missing the required parameter 'dbClusterId' when calling getDatabaseCluster(Async)");
        }

        return getDatabaseClusterCall(dbClusterId, _callback);

    }

    /**
     * Получение кластера базы данных
     * Чтобы получить кластер базы данных на вашем аккаунте, отправьте GET-запрос на &#x60;/api/v1/databases/{db_cluster_id}&#x60;.
     * @param dbClusterId ID кластера базы данных (required)
     * @return CreateDatabaseCluster201Response
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 201 </td><td> Ответ будет представлять собой объект JSON c ключом &#x60;db&#x60;. </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Некорректный запрос </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Не авторизован </td><td>  -  </td></tr>
        <tr><td> 403 </td><td> Запрещено </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Не найдено </td><td>  -  </td></tr>
        <tr><td> 429 </td><td> Слишком много запросов </td><td>  -  </td></tr>
        <tr><td> 500 </td><td> Внутренняя ошибка сервера </td><td>  -  </td></tr>
     </table>
     */
    public CreateDatabaseCluster201Response getDatabaseCluster(Integer dbClusterId) throws ApiException {
        ApiResponse<CreateDatabaseCluster201Response> localVarResp = getDatabaseClusterWithHttpInfo(dbClusterId);
        return localVarResp.getData();
    }

    /**
     * Получение кластера базы данных
     * Чтобы получить кластер базы данных на вашем аккаунте, отправьте GET-запрос на &#x60;/api/v1/databases/{db_cluster_id}&#x60;.
     * @param dbClusterId ID кластера базы данных (required)
     * @return ApiResponse&lt;CreateDatabaseCluster201Response&gt;
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 201 </td><td> Ответ будет представлять собой объект JSON c ключом &#x60;db&#x60;. </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Некорректный запрос </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Не авторизован </td><td>  -  </td></tr>
        <tr><td> 403 </td><td> Запрещено </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Не найдено </td><td>  -  </td></tr>
        <tr><td> 429 </td><td> Слишком много запросов </td><td>  -  </td></tr>
        <tr><td> 500 </td><td> Внутренняя ошибка сервера </td><td>  -  </td></tr>
     </table>
     */
    public ApiResponse<CreateDatabaseCluster201Response> getDatabaseClusterWithHttpInfo(Integer dbClusterId) throws ApiException {
        okhttp3.Call localVarCall = getDatabaseClusterValidateBeforeCall(dbClusterId, null);
        Type localVarReturnType = new TypeToken<CreateDatabaseCluster201Response>(){}.getType();
        return localVarApiClient.execute(localVarCall, localVarReturnType);
    }

    /**
     * Получение кластера базы данных (asynchronously)
     * Чтобы получить кластер базы данных на вашем аккаунте, отправьте GET-запрос на &#x60;/api/v1/databases/{db_cluster_id}&#x60;.
     * @param dbClusterId ID кластера базы данных (required)
     * @param _callback The callback to be executed when the API call finishes
     * @return The request call
     * @throws ApiException If fail to process the API call, e.g. serializing the request body object
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 201 </td><td> Ответ будет представлять собой объект JSON c ключом &#x60;db&#x60;. </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Некорректный запрос </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Не авторизован </td><td>  -  </td></tr>
        <tr><td> 403 </td><td> Запрещено </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Не найдено </td><td>  -  </td></tr>
        <tr><td> 429 </td><td> Слишком много запросов </td><td>  -  </td></tr>
        <tr><td> 500 </td><td> Внутренняя ошибка сервера </td><td>  -  </td></tr>
     </table>
     */
    public okhttp3.Call getDatabaseClusterAsync(Integer dbClusterId, final ApiCallback<CreateDatabaseCluster201Response> _callback) throws ApiException {

        okhttp3.Call localVarCall = getDatabaseClusterValidateBeforeCall(dbClusterId, _callback);
        Type localVarReturnType = new TypeToken<CreateDatabaseCluster201Response>(){}.getType();
        localVarApiClient.executeAsync(localVarCall, localVarReturnType, _callback);
        return localVarCall;
    }
    /**
     * Build call for getDatabaseClusterTypes
     * @param _callback Callback for upload/download progress
     * @return Call to execute
     * @throws ApiException If fail to serialize the request body object
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> Ответ будет представлять собой объект JSON c ключом &#x60;types&#x60;. </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Некорректный запрос </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Не авторизован </td><td>  -  </td></tr>
        <tr><td> 403 </td><td> Запрещено </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Не найдено </td><td>  -  </td></tr>
        <tr><td> 429 </td><td> Слишком много запросов </td><td>  -  </td></tr>
        <tr><td> 500 </td><td> Внутренняя ошибка сервера </td><td>  -  </td></tr>
     </table>
     */
    public okhttp3.Call getDatabaseClusterTypesCall(final ApiCallback _callback) throws ApiException {
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
        String localVarPath = "/api/v1/database-types";

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
    private okhttp3.Call getDatabaseClusterTypesValidateBeforeCall(final ApiCallback _callback) throws ApiException {
        return getDatabaseClusterTypesCall(_callback);

    }

    /**
     * Получение списка типов кластеров баз данных
     * Чтобы получить список типов баз данных на вашем аккаунте, отправьте GET-запрос на &#x60;/api/v1/database-types&#x60;.
     * @return GetDatabaseClusterTypes200Response
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> Ответ будет представлять собой объект JSON c ключом &#x60;types&#x60;. </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Некорректный запрос </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Не авторизован </td><td>  -  </td></tr>
        <tr><td> 403 </td><td> Запрещено </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Не найдено </td><td>  -  </td></tr>
        <tr><td> 429 </td><td> Слишком много запросов </td><td>  -  </td></tr>
        <tr><td> 500 </td><td> Внутренняя ошибка сервера </td><td>  -  </td></tr>
     </table>
     */
    public GetDatabaseClusterTypes200Response getDatabaseClusterTypes() throws ApiException {
        ApiResponse<GetDatabaseClusterTypes200Response> localVarResp = getDatabaseClusterTypesWithHttpInfo();
        return localVarResp.getData();
    }

    /**
     * Получение списка типов кластеров баз данных
     * Чтобы получить список типов баз данных на вашем аккаунте, отправьте GET-запрос на &#x60;/api/v1/database-types&#x60;.
     * @return ApiResponse&lt;GetDatabaseClusterTypes200Response&gt;
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> Ответ будет представлять собой объект JSON c ключом &#x60;types&#x60;. </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Некорректный запрос </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Не авторизован </td><td>  -  </td></tr>
        <tr><td> 403 </td><td> Запрещено </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Не найдено </td><td>  -  </td></tr>
        <tr><td> 429 </td><td> Слишком много запросов </td><td>  -  </td></tr>
        <tr><td> 500 </td><td> Внутренняя ошибка сервера </td><td>  -  </td></tr>
     </table>
     */
    public ApiResponse<GetDatabaseClusterTypes200Response> getDatabaseClusterTypesWithHttpInfo() throws ApiException {
        okhttp3.Call localVarCall = getDatabaseClusterTypesValidateBeforeCall(null);
        Type localVarReturnType = new TypeToken<GetDatabaseClusterTypes200Response>(){}.getType();
        return localVarApiClient.execute(localVarCall, localVarReturnType);
    }

    /**
     * Получение списка типов кластеров баз данных (asynchronously)
     * Чтобы получить список типов баз данных на вашем аккаунте, отправьте GET-запрос на &#x60;/api/v1/database-types&#x60;.
     * @param _callback The callback to be executed when the API call finishes
     * @return The request call
     * @throws ApiException If fail to process the API call, e.g. serializing the request body object
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> Ответ будет представлять собой объект JSON c ключом &#x60;types&#x60;. </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Некорректный запрос </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Не авторизован </td><td>  -  </td></tr>
        <tr><td> 403 </td><td> Запрещено </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Не найдено </td><td>  -  </td></tr>
        <tr><td> 429 </td><td> Слишком много запросов </td><td>  -  </td></tr>
        <tr><td> 500 </td><td> Внутренняя ошибка сервера </td><td>  -  </td></tr>
     </table>
     */
    public okhttp3.Call getDatabaseClusterTypesAsync(final ApiCallback<GetDatabaseClusterTypes200Response> _callback) throws ApiException {

        okhttp3.Call localVarCall = getDatabaseClusterTypesValidateBeforeCall(_callback);
        Type localVarReturnType = new TypeToken<GetDatabaseClusterTypes200Response>(){}.getType();
        localVarApiClient.executeAsync(localVarCall, localVarReturnType, _callback);
        return localVarCall;
    }
    /**
     * Build call for getDatabaseClusters
     * @param limit Обозначает количество записей, которое необходимо вернуть. (optional, default to 100)
     * @param offset Указывает на смещение относительно начала списка. (optional, default to 0)
     * @param _callback Callback for upload/download progress
     * @return Call to execute
     * @throws ApiException If fail to serialize the request body object
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> Ответ будет представлять собой объект JSON c ключом &#x60;dbs&#x60;. </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Некорректный запрос </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Не авторизован </td><td>  -  </td></tr>
        <tr><td> 403 </td><td> Запрещено </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Не найдено </td><td>  -  </td></tr>
        <tr><td> 429 </td><td> Слишком много запросов </td><td>  -  </td></tr>
        <tr><td> 500 </td><td> Внутренняя ошибка сервера </td><td>  -  </td></tr>
     </table>
     */
    public okhttp3.Call getDatabaseClustersCall(Integer limit, Integer offset, final ApiCallback _callback) throws ApiException {
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
        String localVarPath = "/api/v1/databases";

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
    private okhttp3.Call getDatabaseClustersValidateBeforeCall(Integer limit, Integer offset, final ApiCallback _callback) throws ApiException {
        return getDatabaseClustersCall(limit, offset, _callback);

    }

    /**
     * Получение списка кластеров баз данных
     * Чтобы получить список кластеров баз данных, отправьте GET-запрос на &#x60;/api/v1/databases&#x60;.   Тело ответа будет представлять собой объект JSON с ключом &#x60;dbs&#x60;.
     * @param limit Обозначает количество записей, которое необходимо вернуть. (optional, default to 100)
     * @param offset Указывает на смещение относительно начала списка. (optional, default to 0)
     * @return GetDatabaseClusters200Response
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> Ответ будет представлять собой объект JSON c ключом &#x60;dbs&#x60;. </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Некорректный запрос </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Не авторизован </td><td>  -  </td></tr>
        <tr><td> 403 </td><td> Запрещено </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Не найдено </td><td>  -  </td></tr>
        <tr><td> 429 </td><td> Слишком много запросов </td><td>  -  </td></tr>
        <tr><td> 500 </td><td> Внутренняя ошибка сервера </td><td>  -  </td></tr>
     </table>
     */
    public GetDatabaseClusters200Response getDatabaseClusters(Integer limit, Integer offset) throws ApiException {
        ApiResponse<GetDatabaseClusters200Response> localVarResp = getDatabaseClustersWithHttpInfo(limit, offset);
        return localVarResp.getData();
    }

    /**
     * Получение списка кластеров баз данных
     * Чтобы получить список кластеров баз данных, отправьте GET-запрос на &#x60;/api/v1/databases&#x60;.   Тело ответа будет представлять собой объект JSON с ключом &#x60;dbs&#x60;.
     * @param limit Обозначает количество записей, которое необходимо вернуть. (optional, default to 100)
     * @param offset Указывает на смещение относительно начала списка. (optional, default to 0)
     * @return ApiResponse&lt;GetDatabaseClusters200Response&gt;
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> Ответ будет представлять собой объект JSON c ключом &#x60;dbs&#x60;. </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Некорректный запрос </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Не авторизован </td><td>  -  </td></tr>
        <tr><td> 403 </td><td> Запрещено </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Не найдено </td><td>  -  </td></tr>
        <tr><td> 429 </td><td> Слишком много запросов </td><td>  -  </td></tr>
        <tr><td> 500 </td><td> Внутренняя ошибка сервера </td><td>  -  </td></tr>
     </table>
     */
    public ApiResponse<GetDatabaseClusters200Response> getDatabaseClustersWithHttpInfo(Integer limit, Integer offset) throws ApiException {
        okhttp3.Call localVarCall = getDatabaseClustersValidateBeforeCall(limit, offset, null);
        Type localVarReturnType = new TypeToken<GetDatabaseClusters200Response>(){}.getType();
        return localVarApiClient.execute(localVarCall, localVarReturnType);
    }

    /**
     * Получение списка кластеров баз данных (asynchronously)
     * Чтобы получить список кластеров баз данных, отправьте GET-запрос на &#x60;/api/v1/databases&#x60;.   Тело ответа будет представлять собой объект JSON с ключом &#x60;dbs&#x60;.
     * @param limit Обозначает количество записей, которое необходимо вернуть. (optional, default to 100)
     * @param offset Указывает на смещение относительно начала списка. (optional, default to 0)
     * @param _callback The callback to be executed when the API call finishes
     * @return The request call
     * @throws ApiException If fail to process the API call, e.g. serializing the request body object
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> Ответ будет представлять собой объект JSON c ключом &#x60;dbs&#x60;. </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Некорректный запрос </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Не авторизован </td><td>  -  </td></tr>
        <tr><td> 403 </td><td> Запрещено </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Не найдено </td><td>  -  </td></tr>
        <tr><td> 429 </td><td> Слишком много запросов </td><td>  -  </td></tr>
        <tr><td> 500 </td><td> Внутренняя ошибка сервера </td><td>  -  </td></tr>
     </table>
     */
    public okhttp3.Call getDatabaseClustersAsync(Integer limit, Integer offset, final ApiCallback<GetDatabaseClusters200Response> _callback) throws ApiException {

        okhttp3.Call localVarCall = getDatabaseClustersValidateBeforeCall(limit, offset, _callback);
        Type localVarReturnType = new TypeToken<GetDatabaseClusters200Response>(){}.getType();
        localVarApiClient.executeAsync(localVarCall, localVarReturnType, _callback);
        return localVarCall;
    }
    /**
     * Build call for getDatabaseInstance
     * @param dbClusterId ID кластера базы данных (required)
     * @param instanceId ID инстанса базы данных (required)
     * @param _callback Callback for upload/download progress
     * @return Call to execute
     * @throws ApiException If fail to serialize the request body object
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> Ответ будет представлять собой объект JSON c ключом &#x60;instance&#x60;. </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Некорректный запрос </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Не авторизован </td><td>  -  </td></tr>
        <tr><td> 403 </td><td> Запрещено </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Не найдено </td><td>  -  </td></tr>
        <tr><td> 429 </td><td> Слишком много запросов </td><td>  -  </td></tr>
        <tr><td> 500 </td><td> Внутренняя ошибка сервера </td><td>  -  </td></tr>
     </table>
     */
    public okhttp3.Call getDatabaseInstanceCall(Integer dbClusterId, Integer instanceId, final ApiCallback _callback) throws ApiException {
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
        String localVarPath = "/api/v1/databases/{db_cluster_id}/instances/{instance_id}"
            .replace("{" + "db_cluster_id" + "}", localVarApiClient.escapeString(dbClusterId.toString()))
            .replace("{" + "instance_id" + "}", localVarApiClient.escapeString(instanceId.toString()));

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
    private okhttp3.Call getDatabaseInstanceValidateBeforeCall(Integer dbClusterId, Integer instanceId, final ApiCallback _callback) throws ApiException {
        // verify the required parameter 'dbClusterId' is set
        if (dbClusterId == null) {
            throw new ApiException("Missing the required parameter 'dbClusterId' when calling getDatabaseInstance(Async)");
        }

        // verify the required parameter 'instanceId' is set
        if (instanceId == null) {
            throw new ApiException("Missing the required parameter 'instanceId' when calling getDatabaseInstance(Async)");
        }

        return getDatabaseInstanceCall(dbClusterId, instanceId, _callback);

    }

    /**
     * Получение инстанса базы данных
     * Чтобы получить инстанс базы данных, отправьте GET-запрос на &#x60;/api/v1/databases/{db_cluster_id}/instances/{instance_id}&#x60;.
     * @param dbClusterId ID кластера базы данных (required)
     * @param instanceId ID инстанса базы данных (required)
     * @return CreateDatabaseInstance201Response
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> Ответ будет представлять собой объект JSON c ключом &#x60;instance&#x60;. </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Некорректный запрос </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Не авторизован </td><td>  -  </td></tr>
        <tr><td> 403 </td><td> Запрещено </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Не найдено </td><td>  -  </td></tr>
        <tr><td> 429 </td><td> Слишком много запросов </td><td>  -  </td></tr>
        <tr><td> 500 </td><td> Внутренняя ошибка сервера </td><td>  -  </td></tr>
     </table>
     */
    public CreateDatabaseInstance201Response getDatabaseInstance(Integer dbClusterId, Integer instanceId) throws ApiException {
        ApiResponse<CreateDatabaseInstance201Response> localVarResp = getDatabaseInstanceWithHttpInfo(dbClusterId, instanceId);
        return localVarResp.getData();
    }

    /**
     * Получение инстанса базы данных
     * Чтобы получить инстанс базы данных, отправьте GET-запрос на &#x60;/api/v1/databases/{db_cluster_id}/instances/{instance_id}&#x60;.
     * @param dbClusterId ID кластера базы данных (required)
     * @param instanceId ID инстанса базы данных (required)
     * @return ApiResponse&lt;CreateDatabaseInstance201Response&gt;
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> Ответ будет представлять собой объект JSON c ключом &#x60;instance&#x60;. </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Некорректный запрос </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Не авторизован </td><td>  -  </td></tr>
        <tr><td> 403 </td><td> Запрещено </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Не найдено </td><td>  -  </td></tr>
        <tr><td> 429 </td><td> Слишком много запросов </td><td>  -  </td></tr>
        <tr><td> 500 </td><td> Внутренняя ошибка сервера </td><td>  -  </td></tr>
     </table>
     */
    public ApiResponse<CreateDatabaseInstance201Response> getDatabaseInstanceWithHttpInfo(Integer dbClusterId, Integer instanceId) throws ApiException {
        okhttp3.Call localVarCall = getDatabaseInstanceValidateBeforeCall(dbClusterId, instanceId, null);
        Type localVarReturnType = new TypeToken<CreateDatabaseInstance201Response>(){}.getType();
        return localVarApiClient.execute(localVarCall, localVarReturnType);
    }

    /**
     * Получение инстанса базы данных (asynchronously)
     * Чтобы получить инстанс базы данных, отправьте GET-запрос на &#x60;/api/v1/databases/{db_cluster_id}/instances/{instance_id}&#x60;.
     * @param dbClusterId ID кластера базы данных (required)
     * @param instanceId ID инстанса базы данных (required)
     * @param _callback The callback to be executed when the API call finishes
     * @return The request call
     * @throws ApiException If fail to process the API call, e.g. serializing the request body object
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> Ответ будет представлять собой объект JSON c ключом &#x60;instance&#x60;. </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Некорректный запрос </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Не авторизован </td><td>  -  </td></tr>
        <tr><td> 403 </td><td> Запрещено </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Не найдено </td><td>  -  </td></tr>
        <tr><td> 429 </td><td> Слишком много запросов </td><td>  -  </td></tr>
        <tr><td> 500 </td><td> Внутренняя ошибка сервера </td><td>  -  </td></tr>
     </table>
     */
    public okhttp3.Call getDatabaseInstanceAsync(Integer dbClusterId, Integer instanceId, final ApiCallback<CreateDatabaseInstance201Response> _callback) throws ApiException {

        okhttp3.Call localVarCall = getDatabaseInstanceValidateBeforeCall(dbClusterId, instanceId, _callback);
        Type localVarReturnType = new TypeToken<CreateDatabaseInstance201Response>(){}.getType();
        localVarApiClient.executeAsync(localVarCall, localVarReturnType, _callback);
        return localVarCall;
    }
    /**
     * Build call for getDatabaseInstances
     * @param dbClusterId ID кластера базы данных (required)
     * @param _callback Callback for upload/download progress
     * @return Call to execute
     * @throws ApiException If fail to serialize the request body object
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> Ответ будет представлять собой объект JSON c ключом &#x60;instances&#x60;. </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Некорректный запрос </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Не авторизован </td><td>  -  </td></tr>
        <tr><td> 403 </td><td> Запрещено </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Не найдено </td><td>  -  </td></tr>
        <tr><td> 429 </td><td> Слишком много запросов </td><td>  -  </td></tr>
        <tr><td> 500 </td><td> Внутренняя ошибка сервера </td><td>  -  </td></tr>
     </table>
     */
    public okhttp3.Call getDatabaseInstancesCall(Integer dbClusterId, final ApiCallback _callback) throws ApiException {
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
        String localVarPath = "/api/v1/databases/{db_cluster_id}/instances"
            .replace("{" + "db_cluster_id" + "}", localVarApiClient.escapeString(dbClusterId.toString()));

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
    private okhttp3.Call getDatabaseInstancesValidateBeforeCall(Integer dbClusterId, final ApiCallback _callback) throws ApiException {
        // verify the required parameter 'dbClusterId' is set
        if (dbClusterId == null) {
            throw new ApiException("Missing the required parameter 'dbClusterId' when calling getDatabaseInstances(Async)");
        }

        return getDatabaseInstancesCall(dbClusterId, _callback);

    }

    /**
     * Получение списка инстансов баз данных
     * Чтобы получить список баз данных на вашем аккаунте, отправьте GET-запрос на &#x60;/api/v1/databases/{db_cluster_id}/instances&#x60;.
     * @param dbClusterId ID кластера базы данных (required)
     * @return GetDatabaseInstances200Response
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> Ответ будет представлять собой объект JSON c ключом &#x60;instances&#x60;. </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Некорректный запрос </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Не авторизован </td><td>  -  </td></tr>
        <tr><td> 403 </td><td> Запрещено </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Не найдено </td><td>  -  </td></tr>
        <tr><td> 429 </td><td> Слишком много запросов </td><td>  -  </td></tr>
        <tr><td> 500 </td><td> Внутренняя ошибка сервера </td><td>  -  </td></tr>
     </table>
     */
    public GetDatabaseInstances200Response getDatabaseInstances(Integer dbClusterId) throws ApiException {
        ApiResponse<GetDatabaseInstances200Response> localVarResp = getDatabaseInstancesWithHttpInfo(dbClusterId);
        return localVarResp.getData();
    }

    /**
     * Получение списка инстансов баз данных
     * Чтобы получить список баз данных на вашем аккаунте, отправьте GET-запрос на &#x60;/api/v1/databases/{db_cluster_id}/instances&#x60;.
     * @param dbClusterId ID кластера базы данных (required)
     * @return ApiResponse&lt;GetDatabaseInstances200Response&gt;
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> Ответ будет представлять собой объект JSON c ключом &#x60;instances&#x60;. </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Некорректный запрос </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Не авторизован </td><td>  -  </td></tr>
        <tr><td> 403 </td><td> Запрещено </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Не найдено </td><td>  -  </td></tr>
        <tr><td> 429 </td><td> Слишком много запросов </td><td>  -  </td></tr>
        <tr><td> 500 </td><td> Внутренняя ошибка сервера </td><td>  -  </td></tr>
     </table>
     */
    public ApiResponse<GetDatabaseInstances200Response> getDatabaseInstancesWithHttpInfo(Integer dbClusterId) throws ApiException {
        okhttp3.Call localVarCall = getDatabaseInstancesValidateBeforeCall(dbClusterId, null);
        Type localVarReturnType = new TypeToken<GetDatabaseInstances200Response>(){}.getType();
        return localVarApiClient.execute(localVarCall, localVarReturnType);
    }

    /**
     * Получение списка инстансов баз данных (asynchronously)
     * Чтобы получить список баз данных на вашем аккаунте, отправьте GET-запрос на &#x60;/api/v1/databases/{db_cluster_id}/instances&#x60;.
     * @param dbClusterId ID кластера базы данных (required)
     * @param _callback The callback to be executed when the API call finishes
     * @return The request call
     * @throws ApiException If fail to process the API call, e.g. serializing the request body object
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> Ответ будет представлять собой объект JSON c ключом &#x60;instances&#x60;. </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Некорректный запрос </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Не авторизован </td><td>  -  </td></tr>
        <tr><td> 403 </td><td> Запрещено </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Не найдено </td><td>  -  </td></tr>
        <tr><td> 429 </td><td> Слишком много запросов </td><td>  -  </td></tr>
        <tr><td> 500 </td><td> Внутренняя ошибка сервера </td><td>  -  </td></tr>
     </table>
     */
    public okhttp3.Call getDatabaseInstancesAsync(Integer dbClusterId, final ApiCallback<GetDatabaseInstances200Response> _callback) throws ApiException {

        okhttp3.Call localVarCall = getDatabaseInstancesValidateBeforeCall(dbClusterId, _callback);
        Type localVarReturnType = new TypeToken<GetDatabaseInstances200Response>(){}.getType();
        localVarApiClient.executeAsync(localVarCall, localVarReturnType, _callback);
        return localVarCall;
    }
    /**
     * Build call for getDatabaseParameters
     * @param _callback Callback for upload/download progress
     * @return Call to execute
     * @throws ApiException If fail to serialize the request body object
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> Ответ будет представлять собой объект JSON c типами баз данных и их параметров. </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Некорректный запрос </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Не авторизован </td><td>  -  </td></tr>
        <tr><td> 403 </td><td> Запрещено </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Не найдено </td><td>  -  </td></tr>
        <tr><td> 429 </td><td> Слишком много запросов </td><td>  -  </td></tr>
        <tr><td> 500 </td><td> Внутренняя ошибка сервера </td><td>  -  </td></tr>
     </table>
     */
    public okhttp3.Call getDatabaseParametersCall(final ApiCallback _callback) throws ApiException {
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
        String localVarPath = "/api/v1/dbs/parameters";

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
    private okhttp3.Call getDatabaseParametersValidateBeforeCall(final ApiCallback _callback) throws ApiException {
        return getDatabaseParametersCall(_callback);

    }

    /**
     * Получение списка параметров баз данных
     * Чтобы получить список параметров баз данных, отправьте GET-запрос на &#x60;/api/v1/dbs/parameters&#x60;.
     * @return Map&lt;String, List&lt;String&gt;&gt;
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> Ответ будет представлять собой объект JSON c типами баз данных и их параметров. </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Некорректный запрос </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Не авторизован </td><td>  -  </td></tr>
        <tr><td> 403 </td><td> Запрещено </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Не найдено </td><td>  -  </td></tr>
        <tr><td> 429 </td><td> Слишком много запросов </td><td>  -  </td></tr>
        <tr><td> 500 </td><td> Внутренняя ошибка сервера </td><td>  -  </td></tr>
     </table>
     */
    public Map<String, List<String>> getDatabaseParameters() throws ApiException {
        ApiResponse<Map<String, List<String>>> localVarResp = getDatabaseParametersWithHttpInfo();
        return localVarResp.getData();
    }

    /**
     * Получение списка параметров баз данных
     * Чтобы получить список параметров баз данных, отправьте GET-запрос на &#x60;/api/v1/dbs/parameters&#x60;.
     * @return ApiResponse&lt;Map&lt;String, List&lt;String&gt;&gt;&gt;
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> Ответ будет представлять собой объект JSON c типами баз данных и их параметров. </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Некорректный запрос </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Не авторизован </td><td>  -  </td></tr>
        <tr><td> 403 </td><td> Запрещено </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Не найдено </td><td>  -  </td></tr>
        <tr><td> 429 </td><td> Слишком много запросов </td><td>  -  </td></tr>
        <tr><td> 500 </td><td> Внутренняя ошибка сервера </td><td>  -  </td></tr>
     </table>
     */
    public ApiResponse<Map<String, List<String>>> getDatabaseParametersWithHttpInfo() throws ApiException {
        okhttp3.Call localVarCall = getDatabaseParametersValidateBeforeCall(null);
        Type localVarReturnType = new TypeToken<Map<String, List<String>>>(){}.getType();
        return localVarApiClient.execute(localVarCall, localVarReturnType);
    }

    /**
     * Получение списка параметров баз данных (asynchronously)
     * Чтобы получить список параметров баз данных, отправьте GET-запрос на &#x60;/api/v1/dbs/parameters&#x60;.
     * @param _callback The callback to be executed when the API call finishes
     * @return The request call
     * @throws ApiException If fail to process the API call, e.g. serializing the request body object
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> Ответ будет представлять собой объект JSON c типами баз данных и их параметров. </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Некорректный запрос </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Не авторизован </td><td>  -  </td></tr>
        <tr><td> 403 </td><td> Запрещено </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Не найдено </td><td>  -  </td></tr>
        <tr><td> 429 </td><td> Слишком много запросов </td><td>  -  </td></tr>
        <tr><td> 500 </td><td> Внутренняя ошибка сервера </td><td>  -  </td></tr>
     </table>
     */
    public okhttp3.Call getDatabaseParametersAsync(final ApiCallback<Map<String, List<String>>> _callback) throws ApiException {

        okhttp3.Call localVarCall = getDatabaseParametersValidateBeforeCall(_callback);
        Type localVarReturnType = new TypeToken<Map<String, List<String>>>(){}.getType();
        localVarApiClient.executeAsync(localVarCall, localVarReturnType, _callback);
        return localVarCall;
    }
    /**
     * Build call for getDatabaseUser
     * @param dbClusterId ID кластера базы данных (required)
     * @param adminId ID пользователя базы данных (required)
     * @param _callback Callback for upload/download progress
     * @return Call to execute
     * @throws ApiException If fail to serialize the request body object
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> Ответ будет представлять собой объект JSON c ключом &#x60;admin&#x60;. </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Некорректный запрос </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Не авторизован </td><td>  -  </td></tr>
        <tr><td> 403 </td><td> Запрещено </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Не найдено </td><td>  -  </td></tr>
        <tr><td> 429 </td><td> Слишком много запросов </td><td>  -  </td></tr>
        <tr><td> 500 </td><td> Внутренняя ошибка сервера </td><td>  -  </td></tr>
     </table>
     */
    public okhttp3.Call getDatabaseUserCall(Integer dbClusterId, Integer adminId, final ApiCallback _callback) throws ApiException {
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
        String localVarPath = "/api/v1/databases/{db_cluster_id}/admins/{admin_id}"
            .replace("{" + "db_cluster_id" + "}", localVarApiClient.escapeString(dbClusterId.toString()))
            .replace("{" + "admin_id" + "}", localVarApiClient.escapeString(adminId.toString()));

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
    private okhttp3.Call getDatabaseUserValidateBeforeCall(Integer dbClusterId, Integer adminId, final ApiCallback _callback) throws ApiException {
        // verify the required parameter 'dbClusterId' is set
        if (dbClusterId == null) {
            throw new ApiException("Missing the required parameter 'dbClusterId' when calling getDatabaseUser(Async)");
        }

        // verify the required parameter 'adminId' is set
        if (adminId == null) {
            throw new ApiException("Missing the required parameter 'adminId' when calling getDatabaseUser(Async)");
        }

        return getDatabaseUserCall(dbClusterId, adminId, _callback);

    }

    /**
     * Получение пользователя базы данных
     * Чтобы получить пользователя базы данных на вашем аккаунте, отправьте GET-запрос на &#x60;/api/v1/databases/{db_cluster_id}/admins/{admin_id}&#x60;.
     * @param dbClusterId ID кластера базы данных (required)
     * @param adminId ID пользователя базы данных (required)
     * @return CreateDatabaseUser201Response
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> Ответ будет представлять собой объект JSON c ключом &#x60;admin&#x60;. </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Некорректный запрос </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Не авторизован </td><td>  -  </td></tr>
        <tr><td> 403 </td><td> Запрещено </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Не найдено </td><td>  -  </td></tr>
        <tr><td> 429 </td><td> Слишком много запросов </td><td>  -  </td></tr>
        <tr><td> 500 </td><td> Внутренняя ошибка сервера </td><td>  -  </td></tr>
     </table>
     */
    public CreateDatabaseUser201Response getDatabaseUser(Integer dbClusterId, Integer adminId) throws ApiException {
        ApiResponse<CreateDatabaseUser201Response> localVarResp = getDatabaseUserWithHttpInfo(dbClusterId, adminId);
        return localVarResp.getData();
    }

    /**
     * Получение пользователя базы данных
     * Чтобы получить пользователя базы данных на вашем аккаунте, отправьте GET-запрос на &#x60;/api/v1/databases/{db_cluster_id}/admins/{admin_id}&#x60;.
     * @param dbClusterId ID кластера базы данных (required)
     * @param adminId ID пользователя базы данных (required)
     * @return ApiResponse&lt;CreateDatabaseUser201Response&gt;
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> Ответ будет представлять собой объект JSON c ключом &#x60;admin&#x60;. </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Некорректный запрос </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Не авторизован </td><td>  -  </td></tr>
        <tr><td> 403 </td><td> Запрещено </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Не найдено </td><td>  -  </td></tr>
        <tr><td> 429 </td><td> Слишком много запросов </td><td>  -  </td></tr>
        <tr><td> 500 </td><td> Внутренняя ошибка сервера </td><td>  -  </td></tr>
     </table>
     */
    public ApiResponse<CreateDatabaseUser201Response> getDatabaseUserWithHttpInfo(Integer dbClusterId, Integer adminId) throws ApiException {
        okhttp3.Call localVarCall = getDatabaseUserValidateBeforeCall(dbClusterId, adminId, null);
        Type localVarReturnType = new TypeToken<CreateDatabaseUser201Response>(){}.getType();
        return localVarApiClient.execute(localVarCall, localVarReturnType);
    }

    /**
     * Получение пользователя базы данных (asynchronously)
     * Чтобы получить пользователя базы данных на вашем аккаунте, отправьте GET-запрос на &#x60;/api/v1/databases/{db_cluster_id}/admins/{admin_id}&#x60;.
     * @param dbClusterId ID кластера базы данных (required)
     * @param adminId ID пользователя базы данных (required)
     * @param _callback The callback to be executed when the API call finishes
     * @return The request call
     * @throws ApiException If fail to process the API call, e.g. serializing the request body object
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> Ответ будет представлять собой объект JSON c ключом &#x60;admin&#x60;. </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Некорректный запрос </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Не авторизован </td><td>  -  </td></tr>
        <tr><td> 403 </td><td> Запрещено </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Не найдено </td><td>  -  </td></tr>
        <tr><td> 429 </td><td> Слишком много запросов </td><td>  -  </td></tr>
        <tr><td> 500 </td><td> Внутренняя ошибка сервера </td><td>  -  </td></tr>
     </table>
     */
    public okhttp3.Call getDatabaseUserAsync(Integer dbClusterId, Integer adminId, final ApiCallback<CreateDatabaseUser201Response> _callback) throws ApiException {

        okhttp3.Call localVarCall = getDatabaseUserValidateBeforeCall(dbClusterId, adminId, _callback);
        Type localVarReturnType = new TypeToken<CreateDatabaseUser201Response>(){}.getType();
        localVarApiClient.executeAsync(localVarCall, localVarReturnType, _callback);
        return localVarCall;
    }
    /**
     * Build call for getDatabaseUsers
     * @param dbClusterId ID кластера базы данных (required)
     * @param _callback Callback for upload/download progress
     * @return Call to execute
     * @throws ApiException If fail to serialize the request body object
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> Ответ будет представлять собой объект JSON c ключом &#x60;admins&#x60;. </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Некорректный запрос </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Не авторизован </td><td>  -  </td></tr>
        <tr><td> 403 </td><td> Запрещено </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Не найдено </td><td>  -  </td></tr>
        <tr><td> 429 </td><td> Слишком много запросов </td><td>  -  </td></tr>
        <tr><td> 500 </td><td> Внутренняя ошибка сервера </td><td>  -  </td></tr>
     </table>
     */
    public okhttp3.Call getDatabaseUsersCall(Integer dbClusterId, final ApiCallback _callback) throws ApiException {
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
        String localVarPath = "/api/v1/databases/{db_cluster_id}/admins"
            .replace("{" + "db_cluster_id" + "}", localVarApiClient.escapeString(dbClusterId.toString()));

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
    private okhttp3.Call getDatabaseUsersValidateBeforeCall(Integer dbClusterId, final ApiCallback _callback) throws ApiException {
        // verify the required parameter 'dbClusterId' is set
        if (dbClusterId == null) {
            throw new ApiException("Missing the required parameter 'dbClusterId' when calling getDatabaseUsers(Async)");
        }

        return getDatabaseUsersCall(dbClusterId, _callback);

    }

    /**
     * Получение списка пользователей базы данных
     * Чтобы получить список пользователей базы данных на вашем аккаунте, отправьте GET-запрос на &#x60;/api/v1/databases/{db_cluster_id}/admins&#x60;.
     * @param dbClusterId ID кластера базы данных (required)
     * @return GetDatabaseUsers200Response
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> Ответ будет представлять собой объект JSON c ключом &#x60;admins&#x60;. </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Некорректный запрос </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Не авторизован </td><td>  -  </td></tr>
        <tr><td> 403 </td><td> Запрещено </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Не найдено </td><td>  -  </td></tr>
        <tr><td> 429 </td><td> Слишком много запросов </td><td>  -  </td></tr>
        <tr><td> 500 </td><td> Внутренняя ошибка сервера </td><td>  -  </td></tr>
     </table>
     */
    public GetDatabaseUsers200Response getDatabaseUsers(Integer dbClusterId) throws ApiException {
        ApiResponse<GetDatabaseUsers200Response> localVarResp = getDatabaseUsersWithHttpInfo(dbClusterId);
        return localVarResp.getData();
    }

    /**
     * Получение списка пользователей базы данных
     * Чтобы получить список пользователей базы данных на вашем аккаунте, отправьте GET-запрос на &#x60;/api/v1/databases/{db_cluster_id}/admins&#x60;.
     * @param dbClusterId ID кластера базы данных (required)
     * @return ApiResponse&lt;GetDatabaseUsers200Response&gt;
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> Ответ будет представлять собой объект JSON c ключом &#x60;admins&#x60;. </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Некорректный запрос </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Не авторизован </td><td>  -  </td></tr>
        <tr><td> 403 </td><td> Запрещено </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Не найдено </td><td>  -  </td></tr>
        <tr><td> 429 </td><td> Слишком много запросов </td><td>  -  </td></tr>
        <tr><td> 500 </td><td> Внутренняя ошибка сервера </td><td>  -  </td></tr>
     </table>
     */
    public ApiResponse<GetDatabaseUsers200Response> getDatabaseUsersWithHttpInfo(Integer dbClusterId) throws ApiException {
        okhttp3.Call localVarCall = getDatabaseUsersValidateBeforeCall(dbClusterId, null);
        Type localVarReturnType = new TypeToken<GetDatabaseUsers200Response>(){}.getType();
        return localVarApiClient.execute(localVarCall, localVarReturnType);
    }

    /**
     * Получение списка пользователей базы данных (asynchronously)
     * Чтобы получить список пользователей базы данных на вашем аккаунте, отправьте GET-запрос на &#x60;/api/v1/databases/{db_cluster_id}/admins&#x60;.
     * @param dbClusterId ID кластера базы данных (required)
     * @param _callback The callback to be executed when the API call finishes
     * @return The request call
     * @throws ApiException If fail to process the API call, e.g. serializing the request body object
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> Ответ будет представлять собой объект JSON c ключом &#x60;admins&#x60;. </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Некорректный запрос </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Не авторизован </td><td>  -  </td></tr>
        <tr><td> 403 </td><td> Запрещено </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Не найдено </td><td>  -  </td></tr>
        <tr><td> 429 </td><td> Слишком много запросов </td><td>  -  </td></tr>
        <tr><td> 500 </td><td> Внутренняя ошибка сервера </td><td>  -  </td></tr>
     </table>
     */
    public okhttp3.Call getDatabaseUsersAsync(Integer dbClusterId, final ApiCallback<GetDatabaseUsers200Response> _callback) throws ApiException {

        okhttp3.Call localVarCall = getDatabaseUsersValidateBeforeCall(dbClusterId, _callback);
        Type localVarReturnType = new TypeToken<GetDatabaseUsers200Response>(){}.getType();
        localVarApiClient.executeAsync(localVarCall, localVarReturnType, _callback);
        return localVarCall;
    }
    /**
     * Build call for getDatabases
     * @param limit Обозначает количество записей, которое необходимо вернуть. (optional, default to 100)
     * @param offset Указывает на смещение относительно начала списка. (optional, default to 0)
     * @param _callback Callback for upload/download progress
     * @return Call to execute
     * @throws ApiException If fail to serialize the request body object
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> Ответ будет представлять собой объект JSON c ключом &#x60;dbs&#x60;. </td><td>  -  </td></tr>
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
    public okhttp3.Call getDatabasesCall(Integer limit, Integer offset, final ApiCallback _callback) throws ApiException {
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
        String localVarPath = "/api/v1/dbs";

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

    @Deprecated
    @SuppressWarnings("rawtypes")
    private okhttp3.Call getDatabasesValidateBeforeCall(Integer limit, Integer offset, final ApiCallback _callback) throws ApiException {
        return getDatabasesCall(limit, offset, _callback);

    }

    /**
     * Получение списка всех баз данных
     * Чтобы получить список всех баз данных на вашем аккаунте, отправьте GET-запрос на &#x60;/api/v1/dbs&#x60;.   Тело ответа будет представлять собой объект JSON с ключом &#x60;dbs&#x60;.
     * @param limit Обозначает количество записей, которое необходимо вернуть. (optional, default to 100)
     * @param offset Указывает на смещение относительно начала списка. (optional, default to 0)
     * @return GetDatabases200Response
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> Ответ будет представлять собой объект JSON c ключом &#x60;dbs&#x60;. </td><td>  -  </td></tr>
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
    public GetDatabases200Response getDatabases(Integer limit, Integer offset) throws ApiException {
        ApiResponse<GetDatabases200Response> localVarResp = getDatabasesWithHttpInfo(limit, offset);
        return localVarResp.getData();
    }

    /**
     * Получение списка всех баз данных
     * Чтобы получить список всех баз данных на вашем аккаунте, отправьте GET-запрос на &#x60;/api/v1/dbs&#x60;.   Тело ответа будет представлять собой объект JSON с ключом &#x60;dbs&#x60;.
     * @param limit Обозначает количество записей, которое необходимо вернуть. (optional, default to 100)
     * @param offset Указывает на смещение относительно начала списка. (optional, default to 0)
     * @return ApiResponse&lt;GetDatabases200Response&gt;
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> Ответ будет представлять собой объект JSON c ключом &#x60;dbs&#x60;. </td><td>  -  </td></tr>
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
    public ApiResponse<GetDatabases200Response> getDatabasesWithHttpInfo(Integer limit, Integer offset) throws ApiException {
        okhttp3.Call localVarCall = getDatabasesValidateBeforeCall(limit, offset, null);
        Type localVarReturnType = new TypeToken<GetDatabases200Response>(){}.getType();
        return localVarApiClient.execute(localVarCall, localVarReturnType);
    }

    /**
     * Получение списка всех баз данных (asynchronously)
     * Чтобы получить список всех баз данных на вашем аккаунте, отправьте GET-запрос на &#x60;/api/v1/dbs&#x60;.   Тело ответа будет представлять собой объект JSON с ключом &#x60;dbs&#x60;.
     * @param limit Обозначает количество записей, которое необходимо вернуть. (optional, default to 100)
     * @param offset Указывает на смещение относительно начала списка. (optional, default to 0)
     * @param _callback The callback to be executed when the API call finishes
     * @return The request call
     * @throws ApiException If fail to process the API call, e.g. serializing the request body object
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> Ответ будет представлять собой объект JSON c ключом &#x60;dbs&#x60;. </td><td>  -  </td></tr>
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
    public okhttp3.Call getDatabasesAsync(Integer limit, Integer offset, final ApiCallback<GetDatabases200Response> _callback) throws ApiException {

        okhttp3.Call localVarCall = getDatabasesValidateBeforeCall(limit, offset, _callback);
        Type localVarReturnType = new TypeToken<GetDatabases200Response>(){}.getType();
        localVarApiClient.executeAsync(localVarCall, localVarReturnType, _callback);
        return localVarCall;
    }
    /**
     * Build call for getDatabasesPresets
     * @param _callback Callback for upload/download progress
     * @return Call to execute
     * @throws ApiException If fail to serialize the request body object
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> Тарифы успешно получены. </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Некорректный запрос </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Не авторизован </td><td>  -  </td></tr>
        <tr><td> 403 </td><td> Запрещено </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Не найдено </td><td>  -  </td></tr>
        <tr><td> 429 </td><td> Слишком много запросов </td><td>  -  </td></tr>
        <tr><td> 500 </td><td> Внутренняя ошибка сервера </td><td>  -  </td></tr>
     </table>
     */
    public okhttp3.Call getDatabasesPresetsCall(final ApiCallback _callback) throws ApiException {
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
        String localVarPath = "/api/v1/presets/dbs";

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
    private okhttp3.Call getDatabasesPresetsValidateBeforeCall(final ApiCallback _callback) throws ApiException {
        return getDatabasesPresetsCall(_callback);

    }

    /**
     * Получение списка тарифов для баз данных
     * Чтобы получить список тарифов для баз данных, отправьте GET-запрос на &#x60;/api/v1/presets/dbs&#x60;.   Тело ответа будет представлять собой объект JSON с ключом &#x60;databases_presets&#x60;.
     * @return GetDatabasesPresets200Response
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> Тарифы успешно получены. </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Некорректный запрос </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Не авторизован </td><td>  -  </td></tr>
        <tr><td> 403 </td><td> Запрещено </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Не найдено </td><td>  -  </td></tr>
        <tr><td> 429 </td><td> Слишком много запросов </td><td>  -  </td></tr>
        <tr><td> 500 </td><td> Внутренняя ошибка сервера </td><td>  -  </td></tr>
     </table>
     */
    public GetDatabasesPresets200Response getDatabasesPresets() throws ApiException {
        ApiResponse<GetDatabasesPresets200Response> localVarResp = getDatabasesPresetsWithHttpInfo();
        return localVarResp.getData();
    }

    /**
     * Получение списка тарифов для баз данных
     * Чтобы получить список тарифов для баз данных, отправьте GET-запрос на &#x60;/api/v1/presets/dbs&#x60;.   Тело ответа будет представлять собой объект JSON с ключом &#x60;databases_presets&#x60;.
     * @return ApiResponse&lt;GetDatabasesPresets200Response&gt;
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> Тарифы успешно получены. </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Некорректный запрос </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Не авторизован </td><td>  -  </td></tr>
        <tr><td> 403 </td><td> Запрещено </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Не найдено </td><td>  -  </td></tr>
        <tr><td> 429 </td><td> Слишком много запросов </td><td>  -  </td></tr>
        <tr><td> 500 </td><td> Внутренняя ошибка сервера </td><td>  -  </td></tr>
     </table>
     */
    public ApiResponse<GetDatabasesPresets200Response> getDatabasesPresetsWithHttpInfo() throws ApiException {
        okhttp3.Call localVarCall = getDatabasesPresetsValidateBeforeCall(null);
        Type localVarReturnType = new TypeToken<GetDatabasesPresets200Response>(){}.getType();
        return localVarApiClient.execute(localVarCall, localVarReturnType);
    }

    /**
     * Получение списка тарифов для баз данных (asynchronously)
     * Чтобы получить список тарифов для баз данных, отправьте GET-запрос на &#x60;/api/v1/presets/dbs&#x60;.   Тело ответа будет представлять собой объект JSON с ключом &#x60;databases_presets&#x60;.
     * @param _callback The callback to be executed when the API call finishes
     * @return The request call
     * @throws ApiException If fail to process the API call, e.g. serializing the request body object
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> Тарифы успешно получены. </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Некорректный запрос </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Не авторизован </td><td>  -  </td></tr>
        <tr><td> 403 </td><td> Запрещено </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Не найдено </td><td>  -  </td></tr>
        <tr><td> 429 </td><td> Слишком много запросов </td><td>  -  </td></tr>
        <tr><td> 500 </td><td> Внутренняя ошибка сервера </td><td>  -  </td></tr>
     </table>
     */
    public okhttp3.Call getDatabasesPresetsAsync(final ApiCallback<GetDatabasesPresets200Response> _callback) throws ApiException {

        okhttp3.Call localVarCall = getDatabasesPresetsValidateBeforeCall(_callback);
        Type localVarReturnType = new TypeToken<GetDatabasesPresets200Response>(){}.getType();
        localVarApiClient.executeAsync(localVarCall, localVarReturnType, _callback);
        return localVarCall;
    }
    /**
     * Build call for restoreDatabaseFromBackup
     * @param dbId ID базы данных (required)
     * @param backupId ID резевной копии (required)
     * @param _callback Callback for upload/download progress
     * @return Call to execute
     * @throws ApiException If fail to serialize the request body object
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 204 </td><td> База данных из бэкапа успешно восстановлена. </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Некорректный запрос </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Не авторизован </td><td>  -  </td></tr>
        <tr><td> 403 </td><td> Запрещено </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Не найдено </td><td>  -  </td></tr>
        <tr><td> 409 </td><td> Конфликт </td><td>  -  </td></tr>
        <tr><td> 429 </td><td> Слишком много запросов </td><td>  -  </td></tr>
        <tr><td> 500 </td><td> Внутренняя ошибка сервера </td><td>  -  </td></tr>
     </table>
     */
    public okhttp3.Call restoreDatabaseFromBackupCall(Integer dbId, Integer backupId, final ApiCallback _callback) throws ApiException {
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
        String localVarPath = "/api/v1/dbs/{db_id}/backups/{backup_id}"
            .replace("{" + "db_id" + "}", localVarApiClient.escapeString(dbId.toString()))
            .replace("{" + "backup_id" + "}", localVarApiClient.escapeString(backupId.toString()));

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
    private okhttp3.Call restoreDatabaseFromBackupValidateBeforeCall(Integer dbId, Integer backupId, final ApiCallback _callback) throws ApiException {
        // verify the required parameter 'dbId' is set
        if (dbId == null) {
            throw new ApiException("Missing the required parameter 'dbId' when calling restoreDatabaseFromBackup(Async)");
        }

        // verify the required parameter 'backupId' is set
        if (backupId == null) {
            throw new ApiException("Missing the required parameter 'backupId' when calling restoreDatabaseFromBackup(Async)");
        }

        return restoreDatabaseFromBackupCall(dbId, backupId, _callback);

    }

    /**
     * Восстановление базы данных из бэкапа
     * Чтобы восстановить базу данных из бэкапа, отправьте запрос PUT в &#x60;api/v1/dbs/{db_id}/backups/{backup_id}&#x60;. 
     * @param dbId ID базы данных (required)
     * @param backupId ID резевной копии (required)
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 204 </td><td> База данных из бэкапа успешно восстановлена. </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Некорректный запрос </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Не авторизован </td><td>  -  </td></tr>
        <tr><td> 403 </td><td> Запрещено </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Не найдено </td><td>  -  </td></tr>
        <tr><td> 409 </td><td> Конфликт </td><td>  -  </td></tr>
        <tr><td> 429 </td><td> Слишком много запросов </td><td>  -  </td></tr>
        <tr><td> 500 </td><td> Внутренняя ошибка сервера </td><td>  -  </td></tr>
     </table>
     */
    public void restoreDatabaseFromBackup(Integer dbId, Integer backupId) throws ApiException {
        restoreDatabaseFromBackupWithHttpInfo(dbId, backupId);
    }

    /**
     * Восстановление базы данных из бэкапа
     * Чтобы восстановить базу данных из бэкапа, отправьте запрос PUT в &#x60;api/v1/dbs/{db_id}/backups/{backup_id}&#x60;. 
     * @param dbId ID базы данных (required)
     * @param backupId ID резевной копии (required)
     * @return ApiResponse&lt;Void&gt;
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 204 </td><td> База данных из бэкапа успешно восстановлена. </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Некорректный запрос </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Не авторизован </td><td>  -  </td></tr>
        <tr><td> 403 </td><td> Запрещено </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Не найдено </td><td>  -  </td></tr>
        <tr><td> 409 </td><td> Конфликт </td><td>  -  </td></tr>
        <tr><td> 429 </td><td> Слишком много запросов </td><td>  -  </td></tr>
        <tr><td> 500 </td><td> Внутренняя ошибка сервера </td><td>  -  </td></tr>
     </table>
     */
    public ApiResponse<Void> restoreDatabaseFromBackupWithHttpInfo(Integer dbId, Integer backupId) throws ApiException {
        okhttp3.Call localVarCall = restoreDatabaseFromBackupValidateBeforeCall(dbId, backupId, null);
        return localVarApiClient.execute(localVarCall);
    }

    /**
     * Восстановление базы данных из бэкапа (asynchronously)
     * Чтобы восстановить базу данных из бэкапа, отправьте запрос PUT в &#x60;api/v1/dbs/{db_id}/backups/{backup_id}&#x60;. 
     * @param dbId ID базы данных (required)
     * @param backupId ID резевной копии (required)
     * @param _callback The callback to be executed when the API call finishes
     * @return The request call
     * @throws ApiException If fail to process the API call, e.g. serializing the request body object
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 204 </td><td> База данных из бэкапа успешно восстановлена. </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Некорректный запрос </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Не авторизован </td><td>  -  </td></tr>
        <tr><td> 403 </td><td> Запрещено </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Не найдено </td><td>  -  </td></tr>
        <tr><td> 409 </td><td> Конфликт </td><td>  -  </td></tr>
        <tr><td> 429 </td><td> Слишком много запросов </td><td>  -  </td></tr>
        <tr><td> 500 </td><td> Внутренняя ошибка сервера </td><td>  -  </td></tr>
     </table>
     */
    public okhttp3.Call restoreDatabaseFromBackupAsync(Integer dbId, Integer backupId, final ApiCallback<Void> _callback) throws ApiException {

        okhttp3.Call localVarCall = restoreDatabaseFromBackupValidateBeforeCall(dbId, backupId, _callback);
        localVarApiClient.executeAsync(localVarCall, _callback);
        return localVarCall;
    }
    /**
     * Build call for updateDatabase
     * @param dbId ID базы данных (required)
     * @param updateDb  (required)
     * @param _callback Callback for upload/download progress
     * @return Call to execute
     * @throws ApiException If fail to serialize the request body object
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> Ответ будет представлять собой объект JSON с ключом &#x60;db&#x60;. </td><td>  -  </td></tr>
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
    public okhttp3.Call updateDatabaseCall(Integer dbId, UpdateDb updateDb, final ApiCallback _callback) throws ApiException {
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

        Object localVarPostBody = updateDb;

        // create path and map variables
        String localVarPath = "/api/v1/dbs/{db_id}"
            .replace("{" + "db_id" + "}", localVarApiClient.escapeString(dbId.toString()));

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
    private okhttp3.Call updateDatabaseValidateBeforeCall(Integer dbId, UpdateDb updateDb, final ApiCallback _callback) throws ApiException {
        // verify the required parameter 'dbId' is set
        if (dbId == null) {
            throw new ApiException("Missing the required parameter 'dbId' when calling updateDatabase(Async)");
        }

        // verify the required parameter 'updateDb' is set
        if (updateDb == null) {
            throw new ApiException("Missing the required parameter 'updateDb' when calling updateDatabase(Async)");
        }

        return updateDatabaseCall(dbId, updateDb, _callback);

    }

    /**
     * Обновление базы данных
     * Чтобы обновить только определенные атрибуты базы данных, отправьте запрос PATCH в &#x60;api/v1/dbs/{db_id}&#x60;. 
     * @param dbId ID базы данных (required)
     * @param updateDb  (required)
     * @return CreateDatabase201Response
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> Ответ будет представлять собой объект JSON с ключом &#x60;db&#x60;. </td><td>  -  </td></tr>
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
    public CreateDatabase201Response updateDatabase(Integer dbId, UpdateDb updateDb) throws ApiException {
        ApiResponse<CreateDatabase201Response> localVarResp = updateDatabaseWithHttpInfo(dbId, updateDb);
        return localVarResp.getData();
    }

    /**
     * Обновление базы данных
     * Чтобы обновить только определенные атрибуты базы данных, отправьте запрос PATCH в &#x60;api/v1/dbs/{db_id}&#x60;. 
     * @param dbId ID базы данных (required)
     * @param updateDb  (required)
     * @return ApiResponse&lt;CreateDatabase201Response&gt;
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> Ответ будет представлять собой объект JSON с ключом &#x60;db&#x60;. </td><td>  -  </td></tr>
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
    public ApiResponse<CreateDatabase201Response> updateDatabaseWithHttpInfo(Integer dbId, UpdateDb updateDb) throws ApiException {
        okhttp3.Call localVarCall = updateDatabaseValidateBeforeCall(dbId, updateDb, null);
        Type localVarReturnType = new TypeToken<CreateDatabase201Response>(){}.getType();
        return localVarApiClient.execute(localVarCall, localVarReturnType);
    }

    /**
     * Обновление базы данных (asynchronously)
     * Чтобы обновить только определенные атрибуты базы данных, отправьте запрос PATCH в &#x60;api/v1/dbs/{db_id}&#x60;. 
     * @param dbId ID базы данных (required)
     * @param updateDb  (required)
     * @param _callback The callback to be executed when the API call finishes
     * @return The request call
     * @throws ApiException If fail to process the API call, e.g. serializing the request body object
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> Ответ будет представлять собой объект JSON с ключом &#x60;db&#x60;. </td><td>  -  </td></tr>
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
    public okhttp3.Call updateDatabaseAsync(Integer dbId, UpdateDb updateDb, final ApiCallback<CreateDatabase201Response> _callback) throws ApiException {

        okhttp3.Call localVarCall = updateDatabaseValidateBeforeCall(dbId, updateDb, _callback);
        Type localVarReturnType = new TypeToken<CreateDatabase201Response>(){}.getType();
        localVarApiClient.executeAsync(localVarCall, localVarReturnType, _callback);
        return localVarCall;
    }
    /**
     * Build call for updateDatabaseAutoBackupsSettings
     * @param dbId ID базы данных (required)
     * @param autoBackup При значении &#x60;is_enabled&#x60;: &#x60;true&#x60;, поля &#x60;copy_count&#x60;, &#x60;creation_start_at&#x60;, &#x60;interval&#x60; являются обязательными (optional)
     * @param _callback Callback for upload/download progress
     * @return Call to execute
     * @throws ApiException If fail to serialize the request body object
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> Ответ будет представлять собой объект JSON с ключом &#x60;auto_backups_settings&#x60;. </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Некорректный запрос </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Не авторизован </td><td>  -  </td></tr>
        <tr><td> 403 </td><td> Запрещено </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Не найдено </td><td>  -  </td></tr>
        <tr><td> 429 </td><td> Слишком много запросов </td><td>  -  </td></tr>
        <tr><td> 500 </td><td> Внутренняя ошибка сервера </td><td>  -  </td></tr>
     </table>
     */
    public okhttp3.Call updateDatabaseAutoBackupsSettingsCall(Integer dbId, AutoBackup autoBackup, final ApiCallback _callback) throws ApiException {
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

        Object localVarPostBody = autoBackup;

        // create path and map variables
        String localVarPath = "/api/v1/dbs/{db_id}/auto-backups"
            .replace("{" + "db_id" + "}", localVarApiClient.escapeString(dbId.toString()));

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
    private okhttp3.Call updateDatabaseAutoBackupsSettingsValidateBeforeCall(Integer dbId, AutoBackup autoBackup, final ApiCallback _callback) throws ApiException {
        // verify the required parameter 'dbId' is set
        if (dbId == null) {
            throw new ApiException("Missing the required parameter 'dbId' when calling updateDatabaseAutoBackupsSettings(Async)");
        }

        return updateDatabaseAutoBackupsSettingsCall(dbId, autoBackup, _callback);

    }

    /**
     * Изменение настроек автобэкапов базы данных
     * Чтобы изменить список настроек автобэкапов базы данных, отправьте запрос PATCH в &#x60;api/v1/dbs/{db_id}/auto-backups&#x60;
     * @param dbId ID базы данных (required)
     * @param autoBackup При значении &#x60;is_enabled&#x60;: &#x60;true&#x60;, поля &#x60;copy_count&#x60;, &#x60;creation_start_at&#x60;, &#x60;interval&#x60; являются обязательными (optional)
     * @return GetDatabaseAutoBackupsSettings200Response
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> Ответ будет представлять собой объект JSON с ключом &#x60;auto_backups_settings&#x60;. </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Некорректный запрос </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Не авторизован </td><td>  -  </td></tr>
        <tr><td> 403 </td><td> Запрещено </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Не найдено </td><td>  -  </td></tr>
        <tr><td> 429 </td><td> Слишком много запросов </td><td>  -  </td></tr>
        <tr><td> 500 </td><td> Внутренняя ошибка сервера </td><td>  -  </td></tr>
     </table>
     */
    public GetDatabaseAutoBackupsSettings200Response updateDatabaseAutoBackupsSettings(Integer dbId, AutoBackup autoBackup) throws ApiException {
        ApiResponse<GetDatabaseAutoBackupsSettings200Response> localVarResp = updateDatabaseAutoBackupsSettingsWithHttpInfo(dbId, autoBackup);
        return localVarResp.getData();
    }

    /**
     * Изменение настроек автобэкапов базы данных
     * Чтобы изменить список настроек автобэкапов базы данных, отправьте запрос PATCH в &#x60;api/v1/dbs/{db_id}/auto-backups&#x60;
     * @param dbId ID базы данных (required)
     * @param autoBackup При значении &#x60;is_enabled&#x60;: &#x60;true&#x60;, поля &#x60;copy_count&#x60;, &#x60;creation_start_at&#x60;, &#x60;interval&#x60; являются обязательными (optional)
     * @return ApiResponse&lt;GetDatabaseAutoBackupsSettings200Response&gt;
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> Ответ будет представлять собой объект JSON с ключом &#x60;auto_backups_settings&#x60;. </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Некорректный запрос </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Не авторизован </td><td>  -  </td></tr>
        <tr><td> 403 </td><td> Запрещено </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Не найдено </td><td>  -  </td></tr>
        <tr><td> 429 </td><td> Слишком много запросов </td><td>  -  </td></tr>
        <tr><td> 500 </td><td> Внутренняя ошибка сервера </td><td>  -  </td></tr>
     </table>
     */
    public ApiResponse<GetDatabaseAutoBackupsSettings200Response> updateDatabaseAutoBackupsSettingsWithHttpInfo(Integer dbId, AutoBackup autoBackup) throws ApiException {
        okhttp3.Call localVarCall = updateDatabaseAutoBackupsSettingsValidateBeforeCall(dbId, autoBackup, null);
        Type localVarReturnType = new TypeToken<GetDatabaseAutoBackupsSettings200Response>(){}.getType();
        return localVarApiClient.execute(localVarCall, localVarReturnType);
    }

    /**
     * Изменение настроек автобэкапов базы данных (asynchronously)
     * Чтобы изменить список настроек автобэкапов базы данных, отправьте запрос PATCH в &#x60;api/v1/dbs/{db_id}/auto-backups&#x60;
     * @param dbId ID базы данных (required)
     * @param autoBackup При значении &#x60;is_enabled&#x60;: &#x60;true&#x60;, поля &#x60;copy_count&#x60;, &#x60;creation_start_at&#x60;, &#x60;interval&#x60; являются обязательными (optional)
     * @param _callback The callback to be executed when the API call finishes
     * @return The request call
     * @throws ApiException If fail to process the API call, e.g. serializing the request body object
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> Ответ будет представлять собой объект JSON с ключом &#x60;auto_backups_settings&#x60;. </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Некорректный запрос </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Не авторизован </td><td>  -  </td></tr>
        <tr><td> 403 </td><td> Запрещено </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Не найдено </td><td>  -  </td></tr>
        <tr><td> 429 </td><td> Слишком много запросов </td><td>  -  </td></tr>
        <tr><td> 500 </td><td> Внутренняя ошибка сервера </td><td>  -  </td></tr>
     </table>
     */
    public okhttp3.Call updateDatabaseAutoBackupsSettingsAsync(Integer dbId, AutoBackup autoBackup, final ApiCallback<GetDatabaseAutoBackupsSettings200Response> _callback) throws ApiException {

        okhttp3.Call localVarCall = updateDatabaseAutoBackupsSettingsValidateBeforeCall(dbId, autoBackup, _callback);
        Type localVarReturnType = new TypeToken<GetDatabaseAutoBackupsSettings200Response>(){}.getType();
        localVarApiClient.executeAsync(localVarCall, localVarReturnType, _callback);
        return localVarCall;
    }
    /**
     * Build call for updateDatabaseCluster
     * @param dbClusterId ID кластера базы данных (required)
     * @param updateCluster  (required)
     * @param _callback Callback for upload/download progress
     * @return Call to execute
     * @throws ApiException If fail to serialize the request body object
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> Ответ будет представлять собой объект JSON c ключом &#x60;db&#x60;. </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Некорректный запрос </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Не авторизован </td><td>  -  </td></tr>
        <tr><td> 403 </td><td> Запрещено </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Не найдено </td><td>  -  </td></tr>
        <tr><td> 429 </td><td> Слишком много запросов </td><td>  -  </td></tr>
        <tr><td> 500 </td><td> Внутренняя ошибка сервера </td><td>  -  </td></tr>
     </table>
     */
    public okhttp3.Call updateDatabaseClusterCall(Integer dbClusterId, UpdateCluster updateCluster, final ApiCallback _callback) throws ApiException {
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

        Object localVarPostBody = updateCluster;

        // create path and map variables
        String localVarPath = "/api/v1/databases/{db_cluster_id}"
            .replace("{" + "db_cluster_id" + "}", localVarApiClient.escapeString(dbClusterId.toString()));

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
    private okhttp3.Call updateDatabaseClusterValidateBeforeCall(Integer dbClusterId, UpdateCluster updateCluster, final ApiCallback _callback) throws ApiException {
        // verify the required parameter 'dbClusterId' is set
        if (dbClusterId == null) {
            throw new ApiException("Missing the required parameter 'dbClusterId' when calling updateDatabaseCluster(Async)");
        }

        // verify the required parameter 'updateCluster' is set
        if (updateCluster == null) {
            throw new ApiException("Missing the required parameter 'updateCluster' when calling updateDatabaseCluster(Async)");
        }

        return updateDatabaseClusterCall(dbClusterId, updateCluster, _callback);

    }

    /**
     * Изменение кластера базы данных
     * Чтобы изменить кластер базы данных на вашем аккаунте, отправьте PATCH-запрос на &#x60;/api/v1/databases/{db_cluster_id}&#x60;.
     * @param dbClusterId ID кластера базы данных (required)
     * @param updateCluster  (required)
     * @return CreateDatabaseCluster201Response
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> Ответ будет представлять собой объект JSON c ключом &#x60;db&#x60;. </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Некорректный запрос </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Не авторизован </td><td>  -  </td></tr>
        <tr><td> 403 </td><td> Запрещено </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Не найдено </td><td>  -  </td></tr>
        <tr><td> 429 </td><td> Слишком много запросов </td><td>  -  </td></tr>
        <tr><td> 500 </td><td> Внутренняя ошибка сервера </td><td>  -  </td></tr>
     </table>
     */
    public CreateDatabaseCluster201Response updateDatabaseCluster(Integer dbClusterId, UpdateCluster updateCluster) throws ApiException {
        ApiResponse<CreateDatabaseCluster201Response> localVarResp = updateDatabaseClusterWithHttpInfo(dbClusterId, updateCluster);
        return localVarResp.getData();
    }

    /**
     * Изменение кластера базы данных
     * Чтобы изменить кластер базы данных на вашем аккаунте, отправьте PATCH-запрос на &#x60;/api/v1/databases/{db_cluster_id}&#x60;.
     * @param dbClusterId ID кластера базы данных (required)
     * @param updateCluster  (required)
     * @return ApiResponse&lt;CreateDatabaseCluster201Response&gt;
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> Ответ будет представлять собой объект JSON c ключом &#x60;db&#x60;. </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Некорректный запрос </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Не авторизован </td><td>  -  </td></tr>
        <tr><td> 403 </td><td> Запрещено </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Не найдено </td><td>  -  </td></tr>
        <tr><td> 429 </td><td> Слишком много запросов </td><td>  -  </td></tr>
        <tr><td> 500 </td><td> Внутренняя ошибка сервера </td><td>  -  </td></tr>
     </table>
     */
    public ApiResponse<CreateDatabaseCluster201Response> updateDatabaseClusterWithHttpInfo(Integer dbClusterId, UpdateCluster updateCluster) throws ApiException {
        okhttp3.Call localVarCall = updateDatabaseClusterValidateBeforeCall(dbClusterId, updateCluster, null);
        Type localVarReturnType = new TypeToken<CreateDatabaseCluster201Response>(){}.getType();
        return localVarApiClient.execute(localVarCall, localVarReturnType);
    }

    /**
     * Изменение кластера базы данных (asynchronously)
     * Чтобы изменить кластер базы данных на вашем аккаунте, отправьте PATCH-запрос на &#x60;/api/v1/databases/{db_cluster_id}&#x60;.
     * @param dbClusterId ID кластера базы данных (required)
     * @param updateCluster  (required)
     * @param _callback The callback to be executed when the API call finishes
     * @return The request call
     * @throws ApiException If fail to process the API call, e.g. serializing the request body object
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> Ответ будет представлять собой объект JSON c ключом &#x60;db&#x60;. </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Некорректный запрос </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Не авторизован </td><td>  -  </td></tr>
        <tr><td> 403 </td><td> Запрещено </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Не найдено </td><td>  -  </td></tr>
        <tr><td> 429 </td><td> Слишком много запросов </td><td>  -  </td></tr>
        <tr><td> 500 </td><td> Внутренняя ошибка сервера </td><td>  -  </td></tr>
     </table>
     */
    public okhttp3.Call updateDatabaseClusterAsync(Integer dbClusterId, UpdateCluster updateCluster, final ApiCallback<CreateDatabaseCluster201Response> _callback) throws ApiException {

        okhttp3.Call localVarCall = updateDatabaseClusterValidateBeforeCall(dbClusterId, updateCluster, _callback);
        Type localVarReturnType = new TypeToken<CreateDatabaseCluster201Response>(){}.getType();
        localVarApiClient.executeAsync(localVarCall, localVarReturnType, _callback);
        return localVarCall;
    }
    /**
     * Build call for updateDatabaseInstance
     * @param dbClusterId ID кластера базы данных (required)
     * @param updateInstance  (required)
     * @param _callback Callback for upload/download progress
     * @return Call to execute
     * @throws ApiException If fail to serialize the request body object
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> Ответ будет представлять собой объект JSON c ключом &#x60;instance&#x60;. </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Некорректный запрос </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Не авторизован </td><td>  -  </td></tr>
        <tr><td> 403 </td><td> Запрещено </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Не найдено </td><td>  -  </td></tr>
        <tr><td> 429 </td><td> Слишком много запросов </td><td>  -  </td></tr>
        <tr><td> 500 </td><td> Внутренняя ошибка сервера </td><td>  -  </td></tr>
     </table>
     */
    public okhttp3.Call updateDatabaseInstanceCall(Integer dbClusterId, UpdateInstance updateInstance, final ApiCallback _callback) throws ApiException {
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

        Object localVarPostBody = updateInstance;

        // create path and map variables
        String localVarPath = "/api/v1/databases/{db_cluster_id}/instances/{instance_id}"
            .replace("{" + "db_cluster_id" + "}", localVarApiClient.escapeString(dbClusterId.toString()));

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
    private okhttp3.Call updateDatabaseInstanceValidateBeforeCall(Integer dbClusterId, UpdateInstance updateInstance, final ApiCallback _callback) throws ApiException {
        // verify the required parameter 'dbClusterId' is set
        if (dbClusterId == null) {
            throw new ApiException("Missing the required parameter 'dbClusterId' when calling updateDatabaseInstance(Async)");
        }

        // verify the required parameter 'updateInstance' is set
        if (updateInstance == null) {
            throw new ApiException("Missing the required parameter 'updateInstance' when calling updateDatabaseInstance(Async)");
        }

        return updateDatabaseInstanceCall(dbClusterId, updateInstance, _callback);

    }

    /**
     * Изменение инстанса базы данных
     * Чтобы изменить инстанс базы данных, отправьте PATCH-запрос на &#x60;/api/v1/databases/{db_cluster_id}/instances/{instance_id}&#x60;.
     * @param dbClusterId ID кластера базы данных (required)
     * @param updateInstance  (required)
     * @return CreateDatabaseInstance201Response
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> Ответ будет представлять собой объект JSON c ключом &#x60;instance&#x60;. </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Некорректный запрос </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Не авторизован </td><td>  -  </td></tr>
        <tr><td> 403 </td><td> Запрещено </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Не найдено </td><td>  -  </td></tr>
        <tr><td> 429 </td><td> Слишком много запросов </td><td>  -  </td></tr>
        <tr><td> 500 </td><td> Внутренняя ошибка сервера </td><td>  -  </td></tr>
     </table>
     */
    public CreateDatabaseInstance201Response updateDatabaseInstance(Integer dbClusterId, UpdateInstance updateInstance) throws ApiException {
        ApiResponse<CreateDatabaseInstance201Response> localVarResp = updateDatabaseInstanceWithHttpInfo(dbClusterId, updateInstance);
        return localVarResp.getData();
    }

    /**
     * Изменение инстанса базы данных
     * Чтобы изменить инстанс базы данных, отправьте PATCH-запрос на &#x60;/api/v1/databases/{db_cluster_id}/instances/{instance_id}&#x60;.
     * @param dbClusterId ID кластера базы данных (required)
     * @param updateInstance  (required)
     * @return ApiResponse&lt;CreateDatabaseInstance201Response&gt;
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> Ответ будет представлять собой объект JSON c ключом &#x60;instance&#x60;. </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Некорректный запрос </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Не авторизован </td><td>  -  </td></tr>
        <tr><td> 403 </td><td> Запрещено </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Не найдено </td><td>  -  </td></tr>
        <tr><td> 429 </td><td> Слишком много запросов </td><td>  -  </td></tr>
        <tr><td> 500 </td><td> Внутренняя ошибка сервера </td><td>  -  </td></tr>
     </table>
     */
    public ApiResponse<CreateDatabaseInstance201Response> updateDatabaseInstanceWithHttpInfo(Integer dbClusterId, UpdateInstance updateInstance) throws ApiException {
        okhttp3.Call localVarCall = updateDatabaseInstanceValidateBeforeCall(dbClusterId, updateInstance, null);
        Type localVarReturnType = new TypeToken<CreateDatabaseInstance201Response>(){}.getType();
        return localVarApiClient.execute(localVarCall, localVarReturnType);
    }

    /**
     * Изменение инстанса базы данных (asynchronously)
     * Чтобы изменить инстанс базы данных, отправьте PATCH-запрос на &#x60;/api/v1/databases/{db_cluster_id}/instances/{instance_id}&#x60;.
     * @param dbClusterId ID кластера базы данных (required)
     * @param updateInstance  (required)
     * @param _callback The callback to be executed when the API call finishes
     * @return The request call
     * @throws ApiException If fail to process the API call, e.g. serializing the request body object
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> Ответ будет представлять собой объект JSON c ключом &#x60;instance&#x60;. </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Некорректный запрос </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Не авторизован </td><td>  -  </td></tr>
        <tr><td> 403 </td><td> Запрещено </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Не найдено </td><td>  -  </td></tr>
        <tr><td> 429 </td><td> Слишком много запросов </td><td>  -  </td></tr>
        <tr><td> 500 </td><td> Внутренняя ошибка сервера </td><td>  -  </td></tr>
     </table>
     */
    public okhttp3.Call updateDatabaseInstanceAsync(Integer dbClusterId, UpdateInstance updateInstance, final ApiCallback<CreateDatabaseInstance201Response> _callback) throws ApiException {

        okhttp3.Call localVarCall = updateDatabaseInstanceValidateBeforeCall(dbClusterId, updateInstance, _callback);
        Type localVarReturnType = new TypeToken<CreateDatabaseInstance201Response>(){}.getType();
        localVarApiClient.executeAsync(localVarCall, localVarReturnType, _callback);
        return localVarCall;
    }
    /**
     * Build call for updateDatabaseUser
     * @param dbClusterId ID кластера базы данных (required)
     * @param adminId ID пользователя базы данных (required)
     * @param updateAdmin  (required)
     * @param _callback Callback for upload/download progress
     * @return Call to execute
     * @throws ApiException If fail to serialize the request body object
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> Ответ будет представлять собой объект JSON c ключом &#x60;admin&#x60;. </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Некорректный запрос </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Не авторизован </td><td>  -  </td></tr>
        <tr><td> 403 </td><td> Запрещено </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Не найдено </td><td>  -  </td></tr>
        <tr><td> 429 </td><td> Слишком много запросов </td><td>  -  </td></tr>
        <tr><td> 500 </td><td> Внутренняя ошибка сервера </td><td>  -  </td></tr>
     </table>
     */
    public okhttp3.Call updateDatabaseUserCall(Integer dbClusterId, Integer adminId, UpdateAdmin updateAdmin, final ApiCallback _callback) throws ApiException {
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

        Object localVarPostBody = updateAdmin;

        // create path and map variables
        String localVarPath = "/api/v1/databases/{db_cluster_id}/admins/{admin_id}"
            .replace("{" + "db_cluster_id" + "}", localVarApiClient.escapeString(dbClusterId.toString()))
            .replace("{" + "admin_id" + "}", localVarApiClient.escapeString(adminId.toString()));

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
    private okhttp3.Call updateDatabaseUserValidateBeforeCall(Integer dbClusterId, Integer adminId, UpdateAdmin updateAdmin, final ApiCallback _callback) throws ApiException {
        // verify the required parameter 'dbClusterId' is set
        if (dbClusterId == null) {
            throw new ApiException("Missing the required parameter 'dbClusterId' when calling updateDatabaseUser(Async)");
        }

        // verify the required parameter 'adminId' is set
        if (adminId == null) {
            throw new ApiException("Missing the required parameter 'adminId' when calling updateDatabaseUser(Async)");
        }

        // verify the required parameter 'updateAdmin' is set
        if (updateAdmin == null) {
            throw new ApiException("Missing the required parameter 'updateAdmin' when calling updateDatabaseUser(Async)");
        }

        return updateDatabaseUserCall(dbClusterId, adminId, updateAdmin, _callback);

    }

    /**
     * Изменение пользователя базы данных
     * Чтобы изменить пользователя базы данных на вашем аккаунте, отправьте PATCH-запрос на &#x60;/api/v1/databases/{db_cluster_id}/admins/{admin_id}&#x60;.
     * @param dbClusterId ID кластера базы данных (required)
     * @param adminId ID пользователя базы данных (required)
     * @param updateAdmin  (required)
     * @return CreateDatabaseUser201Response
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> Ответ будет представлять собой объект JSON c ключом &#x60;admin&#x60;. </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Некорректный запрос </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Не авторизован </td><td>  -  </td></tr>
        <tr><td> 403 </td><td> Запрещено </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Не найдено </td><td>  -  </td></tr>
        <tr><td> 429 </td><td> Слишком много запросов </td><td>  -  </td></tr>
        <tr><td> 500 </td><td> Внутренняя ошибка сервера </td><td>  -  </td></tr>
     </table>
     */
    public CreateDatabaseUser201Response updateDatabaseUser(Integer dbClusterId, Integer adminId, UpdateAdmin updateAdmin) throws ApiException {
        ApiResponse<CreateDatabaseUser201Response> localVarResp = updateDatabaseUserWithHttpInfo(dbClusterId, adminId, updateAdmin);
        return localVarResp.getData();
    }

    /**
     * Изменение пользователя базы данных
     * Чтобы изменить пользователя базы данных на вашем аккаунте, отправьте PATCH-запрос на &#x60;/api/v1/databases/{db_cluster_id}/admins/{admin_id}&#x60;.
     * @param dbClusterId ID кластера базы данных (required)
     * @param adminId ID пользователя базы данных (required)
     * @param updateAdmin  (required)
     * @return ApiResponse&lt;CreateDatabaseUser201Response&gt;
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> Ответ будет представлять собой объект JSON c ключом &#x60;admin&#x60;. </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Некорректный запрос </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Не авторизован </td><td>  -  </td></tr>
        <tr><td> 403 </td><td> Запрещено </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Не найдено </td><td>  -  </td></tr>
        <tr><td> 429 </td><td> Слишком много запросов </td><td>  -  </td></tr>
        <tr><td> 500 </td><td> Внутренняя ошибка сервера </td><td>  -  </td></tr>
     </table>
     */
    public ApiResponse<CreateDatabaseUser201Response> updateDatabaseUserWithHttpInfo(Integer dbClusterId, Integer adminId, UpdateAdmin updateAdmin) throws ApiException {
        okhttp3.Call localVarCall = updateDatabaseUserValidateBeforeCall(dbClusterId, adminId, updateAdmin, null);
        Type localVarReturnType = new TypeToken<CreateDatabaseUser201Response>(){}.getType();
        return localVarApiClient.execute(localVarCall, localVarReturnType);
    }

    /**
     * Изменение пользователя базы данных (asynchronously)
     * Чтобы изменить пользователя базы данных на вашем аккаунте, отправьте PATCH-запрос на &#x60;/api/v1/databases/{db_cluster_id}/admins/{admin_id}&#x60;.
     * @param dbClusterId ID кластера базы данных (required)
     * @param adminId ID пользователя базы данных (required)
     * @param updateAdmin  (required)
     * @param _callback The callback to be executed when the API call finishes
     * @return The request call
     * @throws ApiException If fail to process the API call, e.g. serializing the request body object
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> Ответ будет представлять собой объект JSON c ключом &#x60;admin&#x60;. </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Некорректный запрос </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Не авторизован </td><td>  -  </td></tr>
        <tr><td> 403 </td><td> Запрещено </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Не найдено </td><td>  -  </td></tr>
        <tr><td> 429 </td><td> Слишком много запросов </td><td>  -  </td></tr>
        <tr><td> 500 </td><td> Внутренняя ошибка сервера </td><td>  -  </td></tr>
     </table>
     */
    public okhttp3.Call updateDatabaseUserAsync(Integer dbClusterId, Integer adminId, UpdateAdmin updateAdmin, final ApiCallback<CreateDatabaseUser201Response> _callback) throws ApiException {

        okhttp3.Call localVarCall = updateDatabaseUserValidateBeforeCall(dbClusterId, adminId, updateAdmin, _callback);
        Type localVarReturnType = new TypeToken<CreateDatabaseUser201Response>(){}.getType();
        localVarApiClient.executeAsync(localVarCall, localVarReturnType, _callback);
        return localVarCall;
    }
}
