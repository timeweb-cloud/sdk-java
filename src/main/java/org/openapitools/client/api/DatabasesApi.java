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


import org.openapitools.client.model.BackupDownloadUrlRequest;
import org.openapitools.client.model.ClusterAction;
import org.openapitools.client.model.CreateAdmin;
import org.openapitools.client.model.CreateCluster;
import org.openapitools.client.model.CreateDatabaseBackup201Response;
import org.openapitools.client.model.CreateDatabaseBackupDownloadUrl201Response;
import org.openapitools.client.model.CreateDatabaseCluster201Response;
import org.openapitools.client.model.CreateDatabaseInstance201Response;
import org.openapitools.client.model.CreateDatabaseS3Backup201Response;
import org.openapitools.client.model.CreateDatabaseUser201Response;
import org.openapitools.client.model.CreateInstance;
import org.openapitools.client.model.CreateS3Backup;
import org.openapitools.client.model.DbParametersByType;
import org.openapitools.client.model.DbsCreateBackup;
import org.openapitools.client.model.DbsUpdateBackup;
import org.openapitools.client.model.DeleteDatabaseCluster200Response;
import org.openapitools.client.model.GetAccountStatus403Response;
import org.openapitools.client.model.GetDatabaseAutoBackupsSettings200Response;
import org.openapitools.client.model.GetDatabaseBackup200Response;
import org.openapitools.client.model.GetDatabaseBackups200Response;
import org.openapitools.client.model.GetDatabaseClusterReplicas200Response;
import org.openapitools.client.model.GetDatabaseClusterTypes200Response;
import org.openapitools.client.model.GetDatabaseClusters200Response;
import org.openapitools.client.model.GetDatabaseConfigurators200Response;
import org.openapitools.client.model.GetDatabaseDefaultParameters200Response;
import org.openapitools.client.model.GetDatabaseInstances200Response;
import org.openapitools.client.model.GetDatabasePreset200Response;
import org.openapitools.client.model.GetDatabasePrivileges200Response;
import org.openapitools.client.model.GetDatabaseS3Backups200Response;
import org.openapitools.client.model.GetDatabaseUsers200Response;
import org.openapitools.client.model.GetDatabasesPresets200Response;
import org.openapitools.client.model.GetFinances400Response;
import org.openapitools.client.model.GetFinances401Response;
import org.openapitools.client.model.GetFinances429Response;
import org.openapitools.client.model.GetFinances500Response;
import org.openapitools.client.model.GetImage404Response;
import java.util.UUID;
import org.openapitools.client.model.UpdateAdmin;
import org.openapitools.client.model.UpdateAutoBackup;
import org.openapitools.client.model.UpdateCluster;
import org.openapitools.client.model.UpdateClusterV2;
import org.openapitools.client.model.UpdateDatabaseCluster200Response;
import org.openapitools.client.model.UpdateDatabaseInstance409Response;
import org.openapitools.client.model.UpdateInstance;
import org.openapitools.client.model.UpdateS3Backup;

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
     * Build call for createDatabaseBackup
     * @param dbId ID базы данных (required)
     * @param dbsCreateBackup  (optional)
     * @param _callback Callback for upload/download progress
     * @return Call to execute
     * @throws ApiException If fail to serialize the request body object
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 201 </td><td> Ответ будет представлять собой объект JSON с ключом &#x60;backup&#x60;. Для кластеров PostgreSQL из нескольких нод значение ключа &#x60;backup&#x60; будет равно &#x60;null&#x60;. </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Некорректный запрос </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Не авторизован </td><td>  -  </td></tr>
        <tr><td> 403 </td><td> Запрещено </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Не найдено </td><td>  -  </td></tr>
        <tr><td> 409 </td><td> Конфликт </td><td>  -  </td></tr>
        <tr><td> 429 </td><td> Слишком много запросов </td><td>  -  </td></tr>
        <tr><td> 500 </td><td> Внутренняя ошибка сервера </td><td>  -  </td></tr>
     </table>
     */
    public okhttp3.Call createDatabaseBackupCall(Integer dbId, DbsCreateBackup dbsCreateBackup, final ApiCallback _callback) throws ApiException {
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

        Object localVarPostBody = dbsCreateBackup;

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
    private okhttp3.Call createDatabaseBackupValidateBeforeCall(Integer dbId, DbsCreateBackup dbsCreateBackup, final ApiCallback _callback) throws ApiException {
        // verify the required parameter 'dbId' is set
        if (dbId == null) {
            throw new ApiException("Missing the required parameter 'dbId' when calling createDatabaseBackup(Async)");
        }

        return createDatabaseBackupCall(dbId, dbsCreateBackup, _callback);

    }

    /**
     * Создание бэкапа базы данных
     * Чтобы создать бэкап базы данных, отправьте запрос POST в &#x60;api/v1/dbs/{db_id}/backups&#x60;. 
     * @param dbId ID базы данных (required)
     * @param dbsCreateBackup  (optional)
     * @return CreateDatabaseBackup201Response
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 201 </td><td> Ответ будет представлять собой объект JSON с ключом &#x60;backup&#x60;. Для кластеров PostgreSQL из нескольких нод значение ключа &#x60;backup&#x60; будет равно &#x60;null&#x60;. </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Некорректный запрос </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Не авторизован </td><td>  -  </td></tr>
        <tr><td> 403 </td><td> Запрещено </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Не найдено </td><td>  -  </td></tr>
        <tr><td> 409 </td><td> Конфликт </td><td>  -  </td></tr>
        <tr><td> 429 </td><td> Слишком много запросов </td><td>  -  </td></tr>
        <tr><td> 500 </td><td> Внутренняя ошибка сервера </td><td>  -  </td></tr>
     </table>
     */
    public CreateDatabaseBackup201Response createDatabaseBackup(Integer dbId, DbsCreateBackup dbsCreateBackup) throws ApiException {
        ApiResponse<CreateDatabaseBackup201Response> localVarResp = createDatabaseBackupWithHttpInfo(dbId, dbsCreateBackup);
        return localVarResp.getData();
    }

    /**
     * Создание бэкапа базы данных
     * Чтобы создать бэкап базы данных, отправьте запрос POST в &#x60;api/v1/dbs/{db_id}/backups&#x60;. 
     * @param dbId ID базы данных (required)
     * @param dbsCreateBackup  (optional)
     * @return ApiResponse&lt;CreateDatabaseBackup201Response&gt;
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 201 </td><td> Ответ будет представлять собой объект JSON с ключом &#x60;backup&#x60;. Для кластеров PostgreSQL из нескольких нод значение ключа &#x60;backup&#x60; будет равно &#x60;null&#x60;. </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Некорректный запрос </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Не авторизован </td><td>  -  </td></tr>
        <tr><td> 403 </td><td> Запрещено </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Не найдено </td><td>  -  </td></tr>
        <tr><td> 409 </td><td> Конфликт </td><td>  -  </td></tr>
        <tr><td> 429 </td><td> Слишком много запросов </td><td>  -  </td></tr>
        <tr><td> 500 </td><td> Внутренняя ошибка сервера </td><td>  -  </td></tr>
     </table>
     */
    public ApiResponse<CreateDatabaseBackup201Response> createDatabaseBackupWithHttpInfo(Integer dbId, DbsCreateBackup dbsCreateBackup) throws ApiException {
        okhttp3.Call localVarCall = createDatabaseBackupValidateBeforeCall(dbId, dbsCreateBackup, null);
        Type localVarReturnType = new TypeToken<CreateDatabaseBackup201Response>(){}.getType();
        return localVarApiClient.execute(localVarCall, localVarReturnType);
    }

    /**
     * Создание бэкапа базы данных (asynchronously)
     * Чтобы создать бэкап базы данных, отправьте запрос POST в &#x60;api/v1/dbs/{db_id}/backups&#x60;. 
     * @param dbId ID базы данных (required)
     * @param dbsCreateBackup  (optional)
     * @param _callback The callback to be executed when the API call finishes
     * @return The request call
     * @throws ApiException If fail to process the API call, e.g. serializing the request body object
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 201 </td><td> Ответ будет представлять собой объект JSON с ключом &#x60;backup&#x60;. Для кластеров PostgreSQL из нескольких нод значение ключа &#x60;backup&#x60; будет равно &#x60;null&#x60;. </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Некорректный запрос </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Не авторизован </td><td>  -  </td></tr>
        <tr><td> 403 </td><td> Запрещено </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Не найдено </td><td>  -  </td></tr>
        <tr><td> 409 </td><td> Конфликт </td><td>  -  </td></tr>
        <tr><td> 429 </td><td> Слишком много запросов </td><td>  -  </td></tr>
        <tr><td> 500 </td><td> Внутренняя ошибка сервера </td><td>  -  </td></tr>
     </table>
     */
    public okhttp3.Call createDatabaseBackupAsync(Integer dbId, DbsCreateBackup dbsCreateBackup, final ApiCallback<CreateDatabaseBackup201Response> _callback) throws ApiException {

        okhttp3.Call localVarCall = createDatabaseBackupValidateBeforeCall(dbId, dbsCreateBackup, _callback);
        Type localVarReturnType = new TypeToken<CreateDatabaseBackup201Response>(){}.getType();
        localVarApiClient.executeAsync(localVarCall, localVarReturnType, _callback);
        return localVarCall;
    }
    /**
     * Build call for createDatabaseBackupDownloadUrl
     * @param dbId ID базы данных (required)
     * @param backupId ID резервной копии (required)
     * @param backupDownloadUrlRequest  (required)
     * @param _callback Callback for upload/download progress
     * @return Call to execute
     * @throws ApiException If fail to serialize the request body object
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 201 </td><td> Ответ будет представлять собой объект JSON с ключом &#x60;backup_url&#x60;. </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Некорректный запрос </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Не авторизован </td><td>  -  </td></tr>
        <tr><td> 403 </td><td> Запрещено </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Не найдено </td><td>  -  </td></tr>
        <tr><td> 409 </td><td> Конфликт </td><td>  -  </td></tr>
        <tr><td> 429 </td><td> Слишком много запросов </td><td>  -  </td></tr>
        <tr><td> 500 </td><td> Внутренняя ошибка сервера </td><td>  -  </td></tr>
     </table>
     */
    public okhttp3.Call createDatabaseBackupDownloadUrlCall(Integer dbId, Integer backupId, BackupDownloadUrlRequest backupDownloadUrlRequest, final ApiCallback _callback) throws ApiException {
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

        Object localVarPostBody = backupDownloadUrlRequest;

        // create path and map variables
        String localVarPath = "/api/v1/dbs/{db_id}/backups/{backup_id}/download-url"
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
    private okhttp3.Call createDatabaseBackupDownloadUrlValidateBeforeCall(Integer dbId, Integer backupId, BackupDownloadUrlRequest backupDownloadUrlRequest, final ApiCallback _callback) throws ApiException {
        // verify the required parameter 'dbId' is set
        if (dbId == null) {
            throw new ApiException("Missing the required parameter 'dbId' when calling createDatabaseBackupDownloadUrl(Async)");
        }

        // verify the required parameter 'backupId' is set
        if (backupId == null) {
            throw new ApiException("Missing the required parameter 'backupId' when calling createDatabaseBackupDownloadUrl(Async)");
        }

        // verify the required parameter 'backupDownloadUrlRequest' is set
        if (backupDownloadUrlRequest == null) {
            throw new ApiException("Missing the required parameter 'backupDownloadUrlRequest' when calling createDatabaseBackupDownloadUrl(Async)");
        }

        return createDatabaseBackupDownloadUrlCall(dbId, backupId, backupDownloadUrlRequest, _callback);

    }

    /**
     * Получение ссылки для скачивания бэкапа базы данных
     * Чтобы получить ссылку для скачивания резервной копии базы данных, отправьте POST-запрос на &#x60;/api/v1/dbs/{db_id}/backups/{backup_id}/download-url&#x60;.   Скачивание резервных копий доступно не для всех кластеров. Если для вашего кластера оно недоступно, метод вернет ошибку со статусом &#x60;400&#x60;.   Тело ответа будет представлять собой объект JSON с ключом &#x60;backup_url&#x60;.
     * @param dbId ID базы данных (required)
     * @param backupId ID резервной копии (required)
     * @param backupDownloadUrlRequest  (required)
     * @return CreateDatabaseBackupDownloadUrl201Response
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 201 </td><td> Ответ будет представлять собой объект JSON с ключом &#x60;backup_url&#x60;. </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Некорректный запрос </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Не авторизован </td><td>  -  </td></tr>
        <tr><td> 403 </td><td> Запрещено </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Не найдено </td><td>  -  </td></tr>
        <tr><td> 409 </td><td> Конфликт </td><td>  -  </td></tr>
        <tr><td> 429 </td><td> Слишком много запросов </td><td>  -  </td></tr>
        <tr><td> 500 </td><td> Внутренняя ошибка сервера </td><td>  -  </td></tr>
     </table>
     */
    public CreateDatabaseBackupDownloadUrl201Response createDatabaseBackupDownloadUrl(Integer dbId, Integer backupId, BackupDownloadUrlRequest backupDownloadUrlRequest) throws ApiException {
        ApiResponse<CreateDatabaseBackupDownloadUrl201Response> localVarResp = createDatabaseBackupDownloadUrlWithHttpInfo(dbId, backupId, backupDownloadUrlRequest);
        return localVarResp.getData();
    }

    /**
     * Получение ссылки для скачивания бэкапа базы данных
     * Чтобы получить ссылку для скачивания резервной копии базы данных, отправьте POST-запрос на &#x60;/api/v1/dbs/{db_id}/backups/{backup_id}/download-url&#x60;.   Скачивание резервных копий доступно не для всех кластеров. Если для вашего кластера оно недоступно, метод вернет ошибку со статусом &#x60;400&#x60;.   Тело ответа будет представлять собой объект JSON с ключом &#x60;backup_url&#x60;.
     * @param dbId ID базы данных (required)
     * @param backupId ID резервной копии (required)
     * @param backupDownloadUrlRequest  (required)
     * @return ApiResponse&lt;CreateDatabaseBackupDownloadUrl201Response&gt;
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 201 </td><td> Ответ будет представлять собой объект JSON с ключом &#x60;backup_url&#x60;. </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Некорректный запрос </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Не авторизован </td><td>  -  </td></tr>
        <tr><td> 403 </td><td> Запрещено </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Не найдено </td><td>  -  </td></tr>
        <tr><td> 409 </td><td> Конфликт </td><td>  -  </td></tr>
        <tr><td> 429 </td><td> Слишком много запросов </td><td>  -  </td></tr>
        <tr><td> 500 </td><td> Внутренняя ошибка сервера </td><td>  -  </td></tr>
     </table>
     */
    public ApiResponse<CreateDatabaseBackupDownloadUrl201Response> createDatabaseBackupDownloadUrlWithHttpInfo(Integer dbId, Integer backupId, BackupDownloadUrlRequest backupDownloadUrlRequest) throws ApiException {
        okhttp3.Call localVarCall = createDatabaseBackupDownloadUrlValidateBeforeCall(dbId, backupId, backupDownloadUrlRequest, null);
        Type localVarReturnType = new TypeToken<CreateDatabaseBackupDownloadUrl201Response>(){}.getType();
        return localVarApiClient.execute(localVarCall, localVarReturnType);
    }

    /**
     * Получение ссылки для скачивания бэкапа базы данных (asynchronously)
     * Чтобы получить ссылку для скачивания резервной копии базы данных, отправьте POST-запрос на &#x60;/api/v1/dbs/{db_id}/backups/{backup_id}/download-url&#x60;.   Скачивание резервных копий доступно не для всех кластеров. Если для вашего кластера оно недоступно, метод вернет ошибку со статусом &#x60;400&#x60;.   Тело ответа будет представлять собой объект JSON с ключом &#x60;backup_url&#x60;.
     * @param dbId ID базы данных (required)
     * @param backupId ID резервной копии (required)
     * @param backupDownloadUrlRequest  (required)
     * @param _callback The callback to be executed when the API call finishes
     * @return The request call
     * @throws ApiException If fail to process the API call, e.g. serializing the request body object
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 201 </td><td> Ответ будет представлять собой объект JSON с ключом &#x60;backup_url&#x60;. </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Некорректный запрос </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Не авторизован </td><td>  -  </td></tr>
        <tr><td> 403 </td><td> Запрещено </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Не найдено </td><td>  -  </td></tr>
        <tr><td> 409 </td><td> Конфликт </td><td>  -  </td></tr>
        <tr><td> 429 </td><td> Слишком много запросов </td><td>  -  </td></tr>
        <tr><td> 500 </td><td> Внутренняя ошибка сервера </td><td>  -  </td></tr>
     </table>
     */
    public okhttp3.Call createDatabaseBackupDownloadUrlAsync(Integer dbId, Integer backupId, BackupDownloadUrlRequest backupDownloadUrlRequest, final ApiCallback<CreateDatabaseBackupDownloadUrl201Response> _callback) throws ApiException {

        okhttp3.Call localVarCall = createDatabaseBackupDownloadUrlValidateBeforeCall(dbId, backupId, backupDownloadUrlRequest, _callback);
        Type localVarReturnType = new TypeToken<CreateDatabaseBackupDownloadUrl201Response>(){}.getType();
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
     * Чтобы создать кластер базы данных на вашем аккаунте, отправьте POST-запрос на &#x60;/api/v1/databases&#x60;.   Вместе с кластером будет создан один инстанс базы данных и один пользователь.   Размер кластера задается либо тарифом (&#x60;preset_id&#x60;), либо конфигуратором (&#x60;configuration&#x60;). Эти поля взаимоисключающие, но одно из них передать обязательно — запрос без обоих вернется с ошибкой.
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
     * Чтобы создать кластер базы данных на вашем аккаунте, отправьте POST-запрос на &#x60;/api/v1/databases&#x60;.   Вместе с кластером будет создан один инстанс базы данных и один пользователь.   Размер кластера задается либо тарифом (&#x60;preset_id&#x60;), либо конфигуратором (&#x60;configuration&#x60;). Эти поля взаимоисключающие, но одно из них передать обязательно — запрос без обоих вернется с ошибкой.
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
     * Чтобы создать кластер базы данных на вашем аккаунте, отправьте POST-запрос на &#x60;/api/v1/databases&#x60;.   Вместе с кластером будет создан один инстанс базы данных и один пользователь.   Размер кластера задается либо тарифом (&#x60;preset_id&#x60;), либо конфигуратором (&#x60;configuration&#x60;). Эти поля взаимоисключающие, но одно из них передать обязательно — запрос без обоих вернется с ошибкой.
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
     * Build call for createDatabaseS3Backup
     * @param dbId ID базы данных (required)
     * @param createS3Backup  (optional)
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
    public okhttp3.Call createDatabaseS3BackupCall(Integer dbId, CreateS3Backup createS3Backup, final ApiCallback _callback) throws ApiException {
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

        Object localVarPostBody = createS3Backup;

        // create path and map variables
        String localVarPath = "/api/v2/databases/{db_id}/backups"
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
        return localVarApiClient.buildCall(basePath, localVarPath, "POST", localVarQueryParams, localVarCollectionQueryParams, localVarPostBody, localVarHeaderParams, localVarCookieParams, localVarFormParams, localVarAuthNames, _callback);
    }

    @SuppressWarnings("rawtypes")
    private okhttp3.Call createDatabaseS3BackupValidateBeforeCall(Integer dbId, CreateS3Backup createS3Backup, final ApiCallback _callback) throws ApiException {
        // verify the required parameter 'dbId' is set
        if (dbId == null) {
            throw new ApiException("Missing the required parameter 'dbId' when calling createDatabaseS3Backup(Async)");
        }

        return createDatabaseS3BackupCall(dbId, createS3Backup, _callback);

    }

    /**
     * Создание S3-бэкапа базы данных
     * Чтобы создать резервную копию кластера базы данных в объектном хранилище, отправьте POST-запрос на &#x60;/api/v2/databases/{db_id}/backups&#x60;.   Тело запроса необязательно: единственное поле &#x60;comment&#x60; можно не передавать. Тело ответа будет представлять собой объект JSON с ключом &#x60;backup&#x60;.   Копия создается асинхронно. Пока она создается, ее статус — &#x60;running&#x60;, и восстановиться из нее нельзя. Дождитесь статуса &#x60;success&#x60;, опрашивая &#x60;/api/v2/databases/{db_id}/backups/{backup_id}&#x60;.
     * @param dbId ID базы данных (required)
     * @param createS3Backup  (optional)
     * @return CreateDatabaseS3Backup201Response
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
    public CreateDatabaseS3Backup201Response createDatabaseS3Backup(Integer dbId, CreateS3Backup createS3Backup) throws ApiException {
        ApiResponse<CreateDatabaseS3Backup201Response> localVarResp = createDatabaseS3BackupWithHttpInfo(dbId, createS3Backup);
        return localVarResp.getData();
    }

    /**
     * Создание S3-бэкапа базы данных
     * Чтобы создать резервную копию кластера базы данных в объектном хранилище, отправьте POST-запрос на &#x60;/api/v2/databases/{db_id}/backups&#x60;.   Тело запроса необязательно: единственное поле &#x60;comment&#x60; можно не передавать. Тело ответа будет представлять собой объект JSON с ключом &#x60;backup&#x60;.   Копия создается асинхронно. Пока она создается, ее статус — &#x60;running&#x60;, и восстановиться из нее нельзя. Дождитесь статуса &#x60;success&#x60;, опрашивая &#x60;/api/v2/databases/{db_id}/backups/{backup_id}&#x60;.
     * @param dbId ID базы данных (required)
     * @param createS3Backup  (optional)
     * @return ApiResponse&lt;CreateDatabaseS3Backup201Response&gt;
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
    public ApiResponse<CreateDatabaseS3Backup201Response> createDatabaseS3BackupWithHttpInfo(Integer dbId, CreateS3Backup createS3Backup) throws ApiException {
        okhttp3.Call localVarCall = createDatabaseS3BackupValidateBeforeCall(dbId, createS3Backup, null);
        Type localVarReturnType = new TypeToken<CreateDatabaseS3Backup201Response>(){}.getType();
        return localVarApiClient.execute(localVarCall, localVarReturnType);
    }

    /**
     * Создание S3-бэкапа базы данных (asynchronously)
     * Чтобы создать резервную копию кластера базы данных в объектном хранилище, отправьте POST-запрос на &#x60;/api/v2/databases/{db_id}/backups&#x60;.   Тело запроса необязательно: единственное поле &#x60;comment&#x60; можно не передавать. Тело ответа будет представлять собой объект JSON с ключом &#x60;backup&#x60;.   Копия создается асинхронно. Пока она создается, ее статус — &#x60;running&#x60;, и восстановиться из нее нельзя. Дождитесь статуса &#x60;success&#x60;, опрашивая &#x60;/api/v2/databases/{db_id}/backups/{backup_id}&#x60;.
     * @param dbId ID базы данных (required)
     * @param createS3Backup  (optional)
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
    public okhttp3.Call createDatabaseS3BackupAsync(Integer dbId, CreateS3Backup createS3Backup, final ApiCallback<CreateDatabaseS3Backup201Response> _callback) throws ApiException {

        okhttp3.Call localVarCall = createDatabaseS3BackupValidateBeforeCall(dbId, createS3Backup, _callback);
        Type localVarReturnType = new TypeToken<CreateDatabaseS3Backup201Response>(){}.getType();
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
     * Build call for deleteDatabaseBackup
     * @param dbId ID базы данных (required)
     * @param backupId ID резервной копии (required)
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
        <tr><td> 409 </td><td> Конфликт </td><td>  -  </td></tr>
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
     * @param backupId ID резервной копии (required)
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 204 </td><td> Бэкап успешно удален. </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Некорректный запрос </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Не авторизован </td><td>  -  </td></tr>
        <tr><td> 403 </td><td> Запрещено </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Не найдено </td><td>  -  </td></tr>
        <tr><td> 409 </td><td> Конфликт </td><td>  -  </td></tr>
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
     * @param backupId ID резервной копии (required)
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
        <tr><td> 409 </td><td> Конфликт </td><td>  -  </td></tr>
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
     * @param backupId ID резервной копии (required)
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
        <tr><td> 409 </td><td> Конфликт </td><td>  -  </td></tr>
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
     * @param _callback Callback for upload/download progress
     * @return Call to execute
     * @throws ApiException If fail to serialize the request body object
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> Если для удаления кластера требуется подтверждение, кластер не удаляется сразу: ответ будет представлять собой объект JSON c ключом &#x60;database_delete&#x60;. </td><td>  -  </td></tr>
        <tr><td> 204 </td><td> Кластер базы данных удален. </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Некорректный запрос </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Не авторизован </td><td>  -  </td></tr>
        <tr><td> 403 </td><td> Запрещено </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Не найдено </td><td>  -  </td></tr>
        <tr><td> 429 </td><td> Слишком много запросов </td><td>  -  </td></tr>
        <tr><td> 500 </td><td> Внутренняя ошибка сервера </td><td>  -  </td></tr>
     </table>
     */
    public okhttp3.Call deleteDatabaseClusterCall(Integer dbClusterId, final ApiCallback _callback) throws ApiException {
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
        return localVarApiClient.buildCall(basePath, localVarPath, "DELETE", localVarQueryParams, localVarCollectionQueryParams, localVarPostBody, localVarHeaderParams, localVarCookieParams, localVarFormParams, localVarAuthNames, _callback);
    }

    @SuppressWarnings("rawtypes")
    private okhttp3.Call deleteDatabaseClusterValidateBeforeCall(Integer dbClusterId, final ApiCallback _callback) throws ApiException {
        // verify the required parameter 'dbClusterId' is set
        if (dbClusterId == null) {
            throw new ApiException("Missing the required parameter 'dbClusterId' when calling deleteDatabaseCluster(Async)");
        }

        return deleteDatabaseClusterCall(dbClusterId, _callback);

    }

    /**
     * Удаление кластера базы данных
     * Чтобы удалить кластер базы данных, отправьте DELETE-запрос на &#x60;/api/v1/databases/{db_cluster_id}&#x60;.
     * @param dbClusterId ID кластера базы данных (required)
     * @return DeleteDatabaseCluster200Response
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> Если для удаления кластера требуется подтверждение, кластер не удаляется сразу: ответ будет представлять собой объект JSON c ключом &#x60;database_delete&#x60;. </td><td>  -  </td></tr>
        <tr><td> 204 </td><td> Кластер базы данных удален. </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Некорректный запрос </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Не авторизован </td><td>  -  </td></tr>
        <tr><td> 403 </td><td> Запрещено </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Не найдено </td><td>  -  </td></tr>
        <tr><td> 429 </td><td> Слишком много запросов </td><td>  -  </td></tr>
        <tr><td> 500 </td><td> Внутренняя ошибка сервера </td><td>  -  </td></tr>
     </table>
     */
    public DeleteDatabaseCluster200Response deleteDatabaseCluster(Integer dbClusterId) throws ApiException {
        ApiResponse<DeleteDatabaseCluster200Response> localVarResp = deleteDatabaseClusterWithHttpInfo(dbClusterId);
        return localVarResp.getData();
    }

    /**
     * Удаление кластера базы данных
     * Чтобы удалить кластер базы данных, отправьте DELETE-запрос на &#x60;/api/v1/databases/{db_cluster_id}&#x60;.
     * @param dbClusterId ID кластера базы данных (required)
     * @return ApiResponse&lt;DeleteDatabaseCluster200Response&gt;
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> Если для удаления кластера требуется подтверждение, кластер не удаляется сразу: ответ будет представлять собой объект JSON c ключом &#x60;database_delete&#x60;. </td><td>  -  </td></tr>
        <tr><td> 204 </td><td> Кластер базы данных удален. </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Некорректный запрос </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Не авторизован </td><td>  -  </td></tr>
        <tr><td> 403 </td><td> Запрещено </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Не найдено </td><td>  -  </td></tr>
        <tr><td> 429 </td><td> Слишком много запросов </td><td>  -  </td></tr>
        <tr><td> 500 </td><td> Внутренняя ошибка сервера </td><td>  -  </td></tr>
     </table>
     */
    public ApiResponse<DeleteDatabaseCluster200Response> deleteDatabaseClusterWithHttpInfo(Integer dbClusterId) throws ApiException {
        okhttp3.Call localVarCall = deleteDatabaseClusterValidateBeforeCall(dbClusterId, null);
        Type localVarReturnType = new TypeToken<DeleteDatabaseCluster200Response>(){}.getType();
        return localVarApiClient.execute(localVarCall, localVarReturnType);
    }

    /**
     * Удаление кластера базы данных (asynchronously)
     * Чтобы удалить кластер базы данных, отправьте DELETE-запрос на &#x60;/api/v1/databases/{db_cluster_id}&#x60;.
     * @param dbClusterId ID кластера базы данных (required)
     * @param _callback The callback to be executed when the API call finishes
     * @return The request call
     * @throws ApiException If fail to process the API call, e.g. serializing the request body object
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> Если для удаления кластера требуется подтверждение, кластер не удаляется сразу: ответ будет представлять собой объект JSON c ключом &#x60;database_delete&#x60;. </td><td>  -  </td></tr>
        <tr><td> 204 </td><td> Кластер базы данных удален. </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Некорректный запрос </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Не авторизован </td><td>  -  </td></tr>
        <tr><td> 403 </td><td> Запрещено </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Не найдено </td><td>  -  </td></tr>
        <tr><td> 429 </td><td> Слишком много запросов </td><td>  -  </td></tr>
        <tr><td> 500 </td><td> Внутренняя ошибка сервера </td><td>  -  </td></tr>
     </table>
     */
    public okhttp3.Call deleteDatabaseClusterAsync(Integer dbClusterId, final ApiCallback<DeleteDatabaseCluster200Response> _callback) throws ApiException {

        okhttp3.Call localVarCall = deleteDatabaseClusterValidateBeforeCall(dbClusterId, _callback);
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
     * Build call for deleteDatabaseS3Backup
     * @param dbId ID базы данных (required)
     * @param backupId ID резервной копии в формате UUID (required)
     * @param _callback Callback for upload/download progress
     * @return Call to execute
     * @throws ApiException If fail to serialize the request body object
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 204 </td><td> Резервная копия успешно удалена. Тело ответа пустое. </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Некорректный запрос </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Не авторизован </td><td>  -  </td></tr>
        <tr><td> 403 </td><td> Запрещено </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Не найдено </td><td>  -  </td></tr>
        <tr><td> 409 </td><td> Конфликт </td><td>  -  </td></tr>
        <tr><td> 429 </td><td> Слишком много запросов </td><td>  -  </td></tr>
        <tr><td> 500 </td><td> Внутренняя ошибка сервера </td><td>  -  </td></tr>
     </table>
     */
    public okhttp3.Call deleteDatabaseS3BackupCall(Integer dbId, UUID backupId, final ApiCallback _callback) throws ApiException {
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
        String localVarPath = "/api/v2/databases/{db_id}/backups/{backup_id}"
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
    private okhttp3.Call deleteDatabaseS3BackupValidateBeforeCall(Integer dbId, UUID backupId, final ApiCallback _callback) throws ApiException {
        // verify the required parameter 'dbId' is set
        if (dbId == null) {
            throw new ApiException("Missing the required parameter 'dbId' when calling deleteDatabaseS3Backup(Async)");
        }

        // verify the required parameter 'backupId' is set
        if (backupId == null) {
            throw new ApiException("Missing the required parameter 'backupId' when calling deleteDatabaseS3Backup(Async)");
        }

        return deleteDatabaseS3BackupCall(dbId, backupId, _callback);

    }

    /**
     * Удаление S3-бэкапа базы данных
     * Чтобы удалить резервную копию кластера базы данных из объектного хранилища, отправьте DELETE-запрос на &#x60;/api/v2/databases/{db_id}/backups/{backup_id}&#x60;.   Копия удаляется безвозвратно, тело ответа пустое. На резервные копии из &#x60;/api/v1/dbs/{db_id}/backups/{backup_id}&#x60; этот метод не действует — они удаляются отдельным запросом.
     * @param dbId ID базы данных (required)
     * @param backupId ID резервной копии в формате UUID (required)
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 204 </td><td> Резервная копия успешно удалена. Тело ответа пустое. </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Некорректный запрос </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Не авторизован </td><td>  -  </td></tr>
        <tr><td> 403 </td><td> Запрещено </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Не найдено </td><td>  -  </td></tr>
        <tr><td> 409 </td><td> Конфликт </td><td>  -  </td></tr>
        <tr><td> 429 </td><td> Слишком много запросов </td><td>  -  </td></tr>
        <tr><td> 500 </td><td> Внутренняя ошибка сервера </td><td>  -  </td></tr>
     </table>
     */
    public void deleteDatabaseS3Backup(Integer dbId, UUID backupId) throws ApiException {
        deleteDatabaseS3BackupWithHttpInfo(dbId, backupId);
    }

    /**
     * Удаление S3-бэкапа базы данных
     * Чтобы удалить резервную копию кластера базы данных из объектного хранилища, отправьте DELETE-запрос на &#x60;/api/v2/databases/{db_id}/backups/{backup_id}&#x60;.   Копия удаляется безвозвратно, тело ответа пустое. На резервные копии из &#x60;/api/v1/dbs/{db_id}/backups/{backup_id}&#x60; этот метод не действует — они удаляются отдельным запросом.
     * @param dbId ID базы данных (required)
     * @param backupId ID резервной копии в формате UUID (required)
     * @return ApiResponse&lt;Void&gt;
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 204 </td><td> Резервная копия успешно удалена. Тело ответа пустое. </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Некорректный запрос </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Не авторизован </td><td>  -  </td></tr>
        <tr><td> 403 </td><td> Запрещено </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Не найдено </td><td>  -  </td></tr>
        <tr><td> 409 </td><td> Конфликт </td><td>  -  </td></tr>
        <tr><td> 429 </td><td> Слишком много запросов </td><td>  -  </td></tr>
        <tr><td> 500 </td><td> Внутренняя ошибка сервера </td><td>  -  </td></tr>
     </table>
     */
    public ApiResponse<Void> deleteDatabaseS3BackupWithHttpInfo(Integer dbId, UUID backupId) throws ApiException {
        okhttp3.Call localVarCall = deleteDatabaseS3BackupValidateBeforeCall(dbId, backupId, null);
        return localVarApiClient.execute(localVarCall);
    }

    /**
     * Удаление S3-бэкапа базы данных (asynchronously)
     * Чтобы удалить резервную копию кластера базы данных из объектного хранилища, отправьте DELETE-запрос на &#x60;/api/v2/databases/{db_id}/backups/{backup_id}&#x60;.   Копия удаляется безвозвратно, тело ответа пустое. На резервные копии из &#x60;/api/v1/dbs/{db_id}/backups/{backup_id}&#x60; этот метод не действует — они удаляются отдельным запросом.
     * @param dbId ID базы данных (required)
     * @param backupId ID резервной копии в формате UUID (required)
     * @param _callback The callback to be executed when the API call finishes
     * @return The request call
     * @throws ApiException If fail to process the API call, e.g. serializing the request body object
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 204 </td><td> Резервная копия успешно удалена. Тело ответа пустое. </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Некорректный запрос </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Не авторизован </td><td>  -  </td></tr>
        <tr><td> 403 </td><td> Запрещено </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Не найдено </td><td>  -  </td></tr>
        <tr><td> 409 </td><td> Конфликт </td><td>  -  </td></tr>
        <tr><td> 429 </td><td> Слишком много запросов </td><td>  -  </td></tr>
        <tr><td> 500 </td><td> Внутренняя ошибка сервера </td><td>  -  </td></tr>
     </table>
     */
    public okhttp3.Call deleteDatabaseS3BackupAsync(Integer dbId, UUID backupId, final ApiCallback<Void> _callback) throws ApiException {

        okhttp3.Call localVarCall = deleteDatabaseS3BackupValidateBeforeCall(dbId, backupId, _callback);
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
        <tr><td> 409 </td><td> Конфликт </td><td>  -  </td></tr>
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
        <tr><td> 409 </td><td> Конфликт </td><td>  -  </td></tr>
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
        <tr><td> 409 </td><td> Конфликт </td><td>  -  </td></tr>
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
        <tr><td> 409 </td><td> Конфликт </td><td>  -  </td></tr>
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
     * @param backupId ID резервной копии (required)
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
        <tr><td> 409 </td><td> Конфликт </td><td>  -  </td></tr>
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
     * @param backupId ID резервной копии (required)
     * @return GetDatabaseBackup200Response
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> Ответ будет представлять собой объект JSON с ключом &#x60;backup&#x60;. </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Некорректный запрос </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Не авторизован </td><td>  -  </td></tr>
        <tr><td> 403 </td><td> Запрещено </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Не найдено </td><td>  -  </td></tr>
        <tr><td> 409 </td><td> Конфликт </td><td>  -  </td></tr>
        <tr><td> 429 </td><td> Слишком много запросов </td><td>  -  </td></tr>
        <tr><td> 500 </td><td> Внутренняя ошибка сервера </td><td>  -  </td></tr>
     </table>
     */
    public GetDatabaseBackup200Response getDatabaseBackup(Integer dbId, Integer backupId) throws ApiException {
        ApiResponse<GetDatabaseBackup200Response> localVarResp = getDatabaseBackupWithHttpInfo(dbId, backupId);
        return localVarResp.getData();
    }

    /**
     * Получение бэкапа базы данных
     * Чтобы получить бэкап базы данных, отправьте запрос GET в &#x60;api/v1/dbs/{db_id}/backups/{backup_id}&#x60;. 
     * @param dbId ID базы данных (required)
     * @param backupId ID резервной копии (required)
     * @return ApiResponse&lt;GetDatabaseBackup200Response&gt;
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> Ответ будет представлять собой объект JSON с ключом &#x60;backup&#x60;. </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Некорректный запрос </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Не авторизован </td><td>  -  </td></tr>
        <tr><td> 403 </td><td> Запрещено </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Не найдено </td><td>  -  </td></tr>
        <tr><td> 409 </td><td> Конфликт </td><td>  -  </td></tr>
        <tr><td> 429 </td><td> Слишком много запросов </td><td>  -  </td></tr>
        <tr><td> 500 </td><td> Внутренняя ошибка сервера </td><td>  -  </td></tr>
     </table>
     */
    public ApiResponse<GetDatabaseBackup200Response> getDatabaseBackupWithHttpInfo(Integer dbId, Integer backupId) throws ApiException {
        okhttp3.Call localVarCall = getDatabaseBackupValidateBeforeCall(dbId, backupId, null);
        Type localVarReturnType = new TypeToken<GetDatabaseBackup200Response>(){}.getType();
        return localVarApiClient.execute(localVarCall, localVarReturnType);
    }

    /**
     * Получение бэкапа базы данных (asynchronously)
     * Чтобы получить бэкап базы данных, отправьте запрос GET в &#x60;api/v1/dbs/{db_id}/backups/{backup_id}&#x60;. 
     * @param dbId ID базы данных (required)
     * @param backupId ID резервной копии (required)
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
        <tr><td> 409 </td><td> Конфликт </td><td>  -  </td></tr>
        <tr><td> 429 </td><td> Слишком много запросов </td><td>  -  </td></tr>
        <tr><td> 500 </td><td> Внутренняя ошибка сервера </td><td>  -  </td></tr>
     </table>
     */
    public okhttp3.Call getDatabaseBackupAsync(Integer dbId, Integer backupId, final ApiCallback<GetDatabaseBackup200Response> _callback) throws ApiException {

        okhttp3.Call localVarCall = getDatabaseBackupValidateBeforeCall(dbId, backupId, _callback);
        Type localVarReturnType = new TypeToken<GetDatabaseBackup200Response>(){}.getType();
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
        <tr><td> 200 </td><td> Ответ будет представлять собой объект JSON c ключом &#x60;db&#x60;. </td><td>  -  </td></tr>
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
        <tr><td> 200 </td><td> Ответ будет представлять собой объект JSON c ключом &#x60;db&#x60;. </td><td>  -  </td></tr>
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
        <tr><td> 200 </td><td> Ответ будет представлять собой объект JSON c ключом &#x60;db&#x60;. </td><td>  -  </td></tr>
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
        <tr><td> 200 </td><td> Ответ будет представлять собой объект JSON c ключом &#x60;db&#x60;. </td><td>  -  </td></tr>
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
     * Build call for getDatabaseClusterReplicas
     * @param dbClusterId ID кластера базы данных (required)
     * @param _callback Callback for upload/download progress
     * @return Call to execute
     * @throws ApiException If fail to serialize the request body object
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> Ответ будет представлять собой объект JSON c ключом &#x60;replicas&#x60;. </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Некорректный запрос </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Не авторизован </td><td>  -  </td></tr>
        <tr><td> 403 </td><td> Запрещено </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Не найдено </td><td>  -  </td></tr>
        <tr><td> 429 </td><td> Слишком много запросов </td><td>  -  </td></tr>
        <tr><td> 500 </td><td> Внутренняя ошибка сервера </td><td>  -  </td></tr>
     </table>
     */
    public okhttp3.Call getDatabaseClusterReplicasCall(Integer dbClusterId, final ApiCallback _callback) throws ApiException {
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
        String localVarPath = "/api/v1/databases/{db_cluster_id}/replicas"
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
    private okhttp3.Call getDatabaseClusterReplicasValidateBeforeCall(Integer dbClusterId, final ApiCallback _callback) throws ApiException {
        // verify the required parameter 'dbClusterId' is set
        if (dbClusterId == null) {
            throw new ApiException("Missing the required parameter 'dbClusterId' when calling getDatabaseClusterReplicas(Async)");
        }

        return getDatabaseClusterReplicasCall(dbClusterId, _callback);

    }

    /**
     * Получение списка реплик кластера базы данных
     * Чтобы получить список реплик кластера базы данных, отправьте GET-запрос на &#x60;/api/v1/databases/{db_cluster_id}/replicas&#x60;.   Тело ответа будет представлять собой объект JSON с ключом &#x60;replicas&#x60;.
     * @param dbClusterId ID кластера базы данных (required)
     * @return GetDatabaseClusterReplicas200Response
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> Ответ будет представлять собой объект JSON c ключом &#x60;replicas&#x60;. </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Некорректный запрос </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Не авторизован </td><td>  -  </td></tr>
        <tr><td> 403 </td><td> Запрещено </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Не найдено </td><td>  -  </td></tr>
        <tr><td> 429 </td><td> Слишком много запросов </td><td>  -  </td></tr>
        <tr><td> 500 </td><td> Внутренняя ошибка сервера </td><td>  -  </td></tr>
     </table>
     */
    public GetDatabaseClusterReplicas200Response getDatabaseClusterReplicas(Integer dbClusterId) throws ApiException {
        ApiResponse<GetDatabaseClusterReplicas200Response> localVarResp = getDatabaseClusterReplicasWithHttpInfo(dbClusterId);
        return localVarResp.getData();
    }

    /**
     * Получение списка реплик кластера базы данных
     * Чтобы получить список реплик кластера базы данных, отправьте GET-запрос на &#x60;/api/v1/databases/{db_cluster_id}/replicas&#x60;.   Тело ответа будет представлять собой объект JSON с ключом &#x60;replicas&#x60;.
     * @param dbClusterId ID кластера базы данных (required)
     * @return ApiResponse&lt;GetDatabaseClusterReplicas200Response&gt;
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> Ответ будет представлять собой объект JSON c ключом &#x60;replicas&#x60;. </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Некорректный запрос </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Не авторизован </td><td>  -  </td></tr>
        <tr><td> 403 </td><td> Запрещено </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Не найдено </td><td>  -  </td></tr>
        <tr><td> 429 </td><td> Слишком много запросов </td><td>  -  </td></tr>
        <tr><td> 500 </td><td> Внутренняя ошибка сервера </td><td>  -  </td></tr>
     </table>
     */
    public ApiResponse<GetDatabaseClusterReplicas200Response> getDatabaseClusterReplicasWithHttpInfo(Integer dbClusterId) throws ApiException {
        okhttp3.Call localVarCall = getDatabaseClusterReplicasValidateBeforeCall(dbClusterId, null);
        Type localVarReturnType = new TypeToken<GetDatabaseClusterReplicas200Response>(){}.getType();
        return localVarApiClient.execute(localVarCall, localVarReturnType);
    }

    /**
     * Получение списка реплик кластера базы данных (asynchronously)
     * Чтобы получить список реплик кластера базы данных, отправьте GET-запрос на &#x60;/api/v1/databases/{db_cluster_id}/replicas&#x60;.   Тело ответа будет представлять собой объект JSON с ключом &#x60;replicas&#x60;.
     * @param dbClusterId ID кластера базы данных (required)
     * @param _callback The callback to be executed when the API call finishes
     * @return The request call
     * @throws ApiException If fail to process the API call, e.g. serializing the request body object
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> Ответ будет представлять собой объект JSON c ключом &#x60;replicas&#x60;. </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Некорректный запрос </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Не авторизован </td><td>  -  </td></tr>
        <tr><td> 403 </td><td> Запрещено </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Не найдено </td><td>  -  </td></tr>
        <tr><td> 429 </td><td> Слишком много запросов </td><td>  -  </td></tr>
        <tr><td> 500 </td><td> Внутренняя ошибка сервера </td><td>  -  </td></tr>
     </table>
     */
    public okhttp3.Call getDatabaseClusterReplicasAsync(Integer dbClusterId, final ApiCallback<GetDatabaseClusterReplicas200Response> _callback) throws ApiException {

        okhttp3.Call localVarCall = getDatabaseClusterReplicasValidateBeforeCall(dbClusterId, _callback);
        Type localVarReturnType = new TypeToken<GetDatabaseClusterReplicas200Response>(){}.getType();
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
     * Build call for getDatabaseConfigurators
     * @param clusterId ID кластера базы данных. Возвращает конфигураторы группы, в пределах которой доступна смена конфигурации этого кластера (сценарий изменения кластера). (optional)
     * @param withUnavailable Включить в ответ конфигураторы, недоступные к заказу из-за нехватки свободных ресурсов. Учитывается только при запросе без &#x60;cluster_id&#x60;. (optional)
     * @param _callback Callback for upload/download progress
     * @return Call to execute
     * @throws ApiException If fail to serialize the request body object
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> Конфигураторы успешно получены. </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Некорректный запрос </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Не авторизован </td><td>  -  </td></tr>
        <tr><td> 403 </td><td> Запрещено </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Не найдено </td><td>  -  </td></tr>
        <tr><td> 429 </td><td> Слишком много запросов </td><td>  -  </td></tr>
        <tr><td> 500 </td><td> Внутренняя ошибка сервера </td><td>  -  </td></tr>
     </table>
     */
    public okhttp3.Call getDatabaseConfiguratorsCall(Integer clusterId, Boolean withUnavailable, final ApiCallback _callback) throws ApiException {
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
        String localVarPath = "/api/v1/configurator/databases";

        List<Pair> localVarQueryParams = new ArrayList<Pair>();
        List<Pair> localVarCollectionQueryParams = new ArrayList<Pair>();
        Map<String, String> localVarHeaderParams = new HashMap<String, String>();
        Map<String, String> localVarCookieParams = new HashMap<String, String>();
        Map<String, Object> localVarFormParams = new HashMap<String, Object>();

        if (clusterId != null) {
            localVarQueryParams.addAll(localVarApiClient.parameterToPair("cluster_id", clusterId));
        }

        if (withUnavailable != null) {
            localVarQueryParams.addAll(localVarApiClient.parameterToPair("with_unavailable", withUnavailable));
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
    private okhttp3.Call getDatabaseConfiguratorsValidateBeforeCall(Integer clusterId, Boolean withUnavailable, final ApiCallback _callback) throws ApiException {
        return getDatabaseConfiguratorsCall(clusterId, withUnavailable, _callback);

    }

    /**
     * Получение списка конфигураторов баз данных
     * Чтобы получить список конфигураторов баз данных, отправьте GET-запрос на &#x60;/api/v1/configurator/databases&#x60;.   Конфигуратор позволяет создать кластер с произвольным количеством ресурсов вместо готового тарифа: его ID передается при создании кластера в поле &#x60;configuration.configurator_id&#x60;, а допустимые значения ресурсов ограничены объектом &#x60;requirements&#x60;.   Тело ответа будет представлять собой объект JSON с ключом &#x60;database_configurators&#x60;.
     * @param clusterId ID кластера базы данных. Возвращает конфигураторы группы, в пределах которой доступна смена конфигурации этого кластера (сценарий изменения кластера). (optional)
     * @param withUnavailable Включить в ответ конфигураторы, недоступные к заказу из-за нехватки свободных ресурсов. Учитывается только при запросе без &#x60;cluster_id&#x60;. (optional)
     * @return GetDatabaseConfigurators200Response
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> Конфигураторы успешно получены. </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Некорректный запрос </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Не авторизован </td><td>  -  </td></tr>
        <tr><td> 403 </td><td> Запрещено </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Не найдено </td><td>  -  </td></tr>
        <tr><td> 429 </td><td> Слишком много запросов </td><td>  -  </td></tr>
        <tr><td> 500 </td><td> Внутренняя ошибка сервера </td><td>  -  </td></tr>
     </table>
     */
    public GetDatabaseConfigurators200Response getDatabaseConfigurators(Integer clusterId, Boolean withUnavailable) throws ApiException {
        ApiResponse<GetDatabaseConfigurators200Response> localVarResp = getDatabaseConfiguratorsWithHttpInfo(clusterId, withUnavailable);
        return localVarResp.getData();
    }

    /**
     * Получение списка конфигураторов баз данных
     * Чтобы получить список конфигураторов баз данных, отправьте GET-запрос на &#x60;/api/v1/configurator/databases&#x60;.   Конфигуратор позволяет создать кластер с произвольным количеством ресурсов вместо готового тарифа: его ID передается при создании кластера в поле &#x60;configuration.configurator_id&#x60;, а допустимые значения ресурсов ограничены объектом &#x60;requirements&#x60;.   Тело ответа будет представлять собой объект JSON с ключом &#x60;database_configurators&#x60;.
     * @param clusterId ID кластера базы данных. Возвращает конфигураторы группы, в пределах которой доступна смена конфигурации этого кластера (сценарий изменения кластера). (optional)
     * @param withUnavailable Включить в ответ конфигураторы, недоступные к заказу из-за нехватки свободных ресурсов. Учитывается только при запросе без &#x60;cluster_id&#x60;. (optional)
     * @return ApiResponse&lt;GetDatabaseConfigurators200Response&gt;
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> Конфигураторы успешно получены. </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Некорректный запрос </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Не авторизован </td><td>  -  </td></tr>
        <tr><td> 403 </td><td> Запрещено </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Не найдено </td><td>  -  </td></tr>
        <tr><td> 429 </td><td> Слишком много запросов </td><td>  -  </td></tr>
        <tr><td> 500 </td><td> Внутренняя ошибка сервера </td><td>  -  </td></tr>
     </table>
     */
    public ApiResponse<GetDatabaseConfigurators200Response> getDatabaseConfiguratorsWithHttpInfo(Integer clusterId, Boolean withUnavailable) throws ApiException {
        okhttp3.Call localVarCall = getDatabaseConfiguratorsValidateBeforeCall(clusterId, withUnavailable, null);
        Type localVarReturnType = new TypeToken<GetDatabaseConfigurators200Response>(){}.getType();
        return localVarApiClient.execute(localVarCall, localVarReturnType);
    }

    /**
     * Получение списка конфигураторов баз данных (asynchronously)
     * Чтобы получить список конфигураторов баз данных, отправьте GET-запрос на &#x60;/api/v1/configurator/databases&#x60;.   Конфигуратор позволяет создать кластер с произвольным количеством ресурсов вместо готового тарифа: его ID передается при создании кластера в поле &#x60;configuration.configurator_id&#x60;, а допустимые значения ресурсов ограничены объектом &#x60;requirements&#x60;.   Тело ответа будет представлять собой объект JSON с ключом &#x60;database_configurators&#x60;.
     * @param clusterId ID кластера базы данных. Возвращает конфигураторы группы, в пределах которой доступна смена конфигурации этого кластера (сценарий изменения кластера). (optional)
     * @param withUnavailable Включить в ответ конфигураторы, недоступные к заказу из-за нехватки свободных ресурсов. Учитывается только при запросе без &#x60;cluster_id&#x60;. (optional)
     * @param _callback The callback to be executed when the API call finishes
     * @return The request call
     * @throws ApiException If fail to process the API call, e.g. serializing the request body object
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> Конфигураторы успешно получены. </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Некорректный запрос </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Не авторизован </td><td>  -  </td></tr>
        <tr><td> 403 </td><td> Запрещено </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Не найдено </td><td>  -  </td></tr>
        <tr><td> 429 </td><td> Слишком много запросов </td><td>  -  </td></tr>
        <tr><td> 500 </td><td> Внутренняя ошибка сервера </td><td>  -  </td></tr>
     </table>
     */
    public okhttp3.Call getDatabaseConfiguratorsAsync(Integer clusterId, Boolean withUnavailable, final ApiCallback<GetDatabaseConfigurators200Response> _callback) throws ApiException {

        okhttp3.Call localVarCall = getDatabaseConfiguratorsValidateBeforeCall(clusterId, withUnavailable, _callback);
        Type localVarReturnType = new TypeToken<GetDatabaseConfigurators200Response>(){}.getType();
        localVarApiClient.executeAsync(localVarCall, localVarReturnType, _callback);
        return localVarCall;
    }
    /**
     * Build call for getDatabaseDefaultParameters
     * @param type Тип кластера базы данных. (required)
     * @param ram Объём оперативной памяти кластера (в Мб). (required)
     * @param replicaCount Количество нод (реплик) кластера. (optional, default to 1)
     * @param _callback Callback for upload/download progress
     * @return Call to execute
     * @throws ApiException If fail to serialize the request body object
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> Рекомендуемые значения параметров успешно получены. </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Некорректный запрос </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Не авторизован </td><td>  -  </td></tr>
        <tr><td> 403 </td><td> Запрещено </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Не найдено </td><td>  -  </td></tr>
        <tr><td> 429 </td><td> Слишком много запросов </td><td>  -  </td></tr>
        <tr><td> 500 </td><td> Внутренняя ошибка сервера </td><td>  -  </td></tr>
     </table>
     */
    public okhttp3.Call getDatabaseDefaultParametersCall(String type, Integer ram, Integer replicaCount, final ApiCallback _callback) throws ApiException {
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
        String localVarPath = "/api/v1/dbs/default-parameters";

        List<Pair> localVarQueryParams = new ArrayList<Pair>();
        List<Pair> localVarCollectionQueryParams = new ArrayList<Pair>();
        Map<String, String> localVarHeaderParams = new HashMap<String, String>();
        Map<String, String> localVarCookieParams = new HashMap<String, String>();
        Map<String, Object> localVarFormParams = new HashMap<String, Object>();

        if (type != null) {
            localVarQueryParams.addAll(localVarApiClient.parameterToPair("type", type));
        }

        if (ram != null) {
            localVarQueryParams.addAll(localVarApiClient.parameterToPair("ram", ram));
        }

        if (replicaCount != null) {
            localVarQueryParams.addAll(localVarApiClient.parameterToPair("replica_count", replicaCount));
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
    private okhttp3.Call getDatabaseDefaultParametersValidateBeforeCall(String type, Integer ram, Integer replicaCount, final ApiCallback _callback) throws ApiException {
        // verify the required parameter 'type' is set
        if (type == null) {
            throw new ApiException("Missing the required parameter 'type' when calling getDatabaseDefaultParameters(Async)");
        }

        // verify the required parameter 'ram' is set
        if (ram == null) {
            throw new ApiException("Missing the required parameter 'ram' when calling getDatabaseDefaultParameters(Async)");
        }

        return getDatabaseDefaultParametersCall(type, ram, replicaCount, _callback);

    }

    /**
     * Получение рекомендуемых значений параметров баз данных
     * Чтобы получить рекомендуемые значения параметров базы данных, отправьте GET-запрос на &#x60;/api/v1/dbs/default-parameters&#x60;.   Значения рассчитываются для указанного типа кластера, объема оперативной памяти и количества реплик — их можно передать при создании кластера в поле &#x60;config_parameters&#x60;. Список имен параметров, доступных для каждого типа кластера, возвращает &#x60;GET /api/v1/dbs/parameters&#x60;.   Тело ответа будет представлять собой объект JSON с ключом &#x60;config_params&#x60;. Рекомендуемые значения рассчитываются только для кластеров MySQL, PostgreSQL и Valkey — для остальных типов возвращается пустой объект.
     * @param type Тип кластера базы данных. (required)
     * @param ram Объём оперативной памяти кластера (в Мб). (required)
     * @param replicaCount Количество нод (реплик) кластера. (optional, default to 1)
     * @return GetDatabaseDefaultParameters200Response
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> Рекомендуемые значения параметров успешно получены. </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Некорректный запрос </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Не авторизован </td><td>  -  </td></tr>
        <tr><td> 403 </td><td> Запрещено </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Не найдено </td><td>  -  </td></tr>
        <tr><td> 429 </td><td> Слишком много запросов </td><td>  -  </td></tr>
        <tr><td> 500 </td><td> Внутренняя ошибка сервера </td><td>  -  </td></tr>
     </table>
     */
    public GetDatabaseDefaultParameters200Response getDatabaseDefaultParameters(String type, Integer ram, Integer replicaCount) throws ApiException {
        ApiResponse<GetDatabaseDefaultParameters200Response> localVarResp = getDatabaseDefaultParametersWithHttpInfo(type, ram, replicaCount);
        return localVarResp.getData();
    }

    /**
     * Получение рекомендуемых значений параметров баз данных
     * Чтобы получить рекомендуемые значения параметров базы данных, отправьте GET-запрос на &#x60;/api/v1/dbs/default-parameters&#x60;.   Значения рассчитываются для указанного типа кластера, объема оперативной памяти и количества реплик — их можно передать при создании кластера в поле &#x60;config_parameters&#x60;. Список имен параметров, доступных для каждого типа кластера, возвращает &#x60;GET /api/v1/dbs/parameters&#x60;.   Тело ответа будет представлять собой объект JSON с ключом &#x60;config_params&#x60;. Рекомендуемые значения рассчитываются только для кластеров MySQL, PostgreSQL и Valkey — для остальных типов возвращается пустой объект.
     * @param type Тип кластера базы данных. (required)
     * @param ram Объём оперативной памяти кластера (в Мб). (required)
     * @param replicaCount Количество нод (реплик) кластера. (optional, default to 1)
     * @return ApiResponse&lt;GetDatabaseDefaultParameters200Response&gt;
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> Рекомендуемые значения параметров успешно получены. </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Некорректный запрос </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Не авторизован </td><td>  -  </td></tr>
        <tr><td> 403 </td><td> Запрещено </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Не найдено </td><td>  -  </td></tr>
        <tr><td> 429 </td><td> Слишком много запросов </td><td>  -  </td></tr>
        <tr><td> 500 </td><td> Внутренняя ошибка сервера </td><td>  -  </td></tr>
     </table>
     */
    public ApiResponse<GetDatabaseDefaultParameters200Response> getDatabaseDefaultParametersWithHttpInfo(String type, Integer ram, Integer replicaCount) throws ApiException {
        okhttp3.Call localVarCall = getDatabaseDefaultParametersValidateBeforeCall(type, ram, replicaCount, null);
        Type localVarReturnType = new TypeToken<GetDatabaseDefaultParameters200Response>(){}.getType();
        return localVarApiClient.execute(localVarCall, localVarReturnType);
    }

    /**
     * Получение рекомендуемых значений параметров баз данных (asynchronously)
     * Чтобы получить рекомендуемые значения параметров базы данных, отправьте GET-запрос на &#x60;/api/v1/dbs/default-parameters&#x60;.   Значения рассчитываются для указанного типа кластера, объема оперативной памяти и количества реплик — их можно передать при создании кластера в поле &#x60;config_parameters&#x60;. Список имен параметров, доступных для каждого типа кластера, возвращает &#x60;GET /api/v1/dbs/parameters&#x60;.   Тело ответа будет представлять собой объект JSON с ключом &#x60;config_params&#x60;. Рекомендуемые значения рассчитываются только для кластеров MySQL, PostgreSQL и Valkey — для остальных типов возвращается пустой объект.
     * @param type Тип кластера базы данных. (required)
     * @param ram Объём оперативной памяти кластера (в Мб). (required)
     * @param replicaCount Количество нод (реплик) кластера. (optional, default to 1)
     * @param _callback The callback to be executed when the API call finishes
     * @return The request call
     * @throws ApiException If fail to process the API call, e.g. serializing the request body object
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> Рекомендуемые значения параметров успешно получены. </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Некорректный запрос </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Не авторизован </td><td>  -  </td></tr>
        <tr><td> 403 </td><td> Запрещено </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Не найдено </td><td>  -  </td></tr>
        <tr><td> 429 </td><td> Слишком много запросов </td><td>  -  </td></tr>
        <tr><td> 500 </td><td> Внутренняя ошибка сервера </td><td>  -  </td></tr>
     </table>
     */
    public okhttp3.Call getDatabaseDefaultParametersAsync(String type, Integer ram, Integer replicaCount, final ApiCallback<GetDatabaseDefaultParameters200Response> _callback) throws ApiException {

        okhttp3.Call localVarCall = getDatabaseDefaultParametersValidateBeforeCall(type, ram, replicaCount, _callback);
        Type localVarReturnType = new TypeToken<GetDatabaseDefaultParameters200Response>(){}.getType();
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
        <tr><td> 200 </td><td> Ответ будет представлять собой объект JSON, ключи которого — типы кластеров баз данных, а значения — массивы имён доступных параметров. </td><td>  -  </td></tr>
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
     * Чтобы получить список параметров баз данных, отправьте GET-запрос на &#x60;/api/v1/dbs/parameters&#x60;.   Ответ содержит только имена параметров, доступных для каждого типа кластера. Рекомендуемые значения этих параметров для конкретной конфигурации возвращает &#x60;GET /api/v1/dbs/default-parameters&#x60;.
     * @return DbParametersByType
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> Ответ будет представлять собой объект JSON, ключи которого — типы кластеров баз данных, а значения — массивы имён доступных параметров. </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Некорректный запрос </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Не авторизован </td><td>  -  </td></tr>
        <tr><td> 403 </td><td> Запрещено </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Не найдено </td><td>  -  </td></tr>
        <tr><td> 429 </td><td> Слишком много запросов </td><td>  -  </td></tr>
        <tr><td> 500 </td><td> Внутренняя ошибка сервера </td><td>  -  </td></tr>
     </table>
     */
    public DbParametersByType getDatabaseParameters() throws ApiException {
        ApiResponse<DbParametersByType> localVarResp = getDatabaseParametersWithHttpInfo();
        return localVarResp.getData();
    }

    /**
     * Получение списка параметров баз данных
     * Чтобы получить список параметров баз данных, отправьте GET-запрос на &#x60;/api/v1/dbs/parameters&#x60;.   Ответ содержит только имена параметров, доступных для каждого типа кластера. Рекомендуемые значения этих параметров для конкретной конфигурации возвращает &#x60;GET /api/v1/dbs/default-parameters&#x60;.
     * @return ApiResponse&lt;DbParametersByType&gt;
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> Ответ будет представлять собой объект JSON, ключи которого — типы кластеров баз данных, а значения — массивы имён доступных параметров. </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Некорректный запрос </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Не авторизован </td><td>  -  </td></tr>
        <tr><td> 403 </td><td> Запрещено </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Не найдено </td><td>  -  </td></tr>
        <tr><td> 429 </td><td> Слишком много запросов </td><td>  -  </td></tr>
        <tr><td> 500 </td><td> Внутренняя ошибка сервера </td><td>  -  </td></tr>
     </table>
     */
    public ApiResponse<DbParametersByType> getDatabaseParametersWithHttpInfo() throws ApiException {
        okhttp3.Call localVarCall = getDatabaseParametersValidateBeforeCall(null);
        Type localVarReturnType = new TypeToken<DbParametersByType>(){}.getType();
        return localVarApiClient.execute(localVarCall, localVarReturnType);
    }

    /**
     * Получение списка параметров баз данных (asynchronously)
     * Чтобы получить список параметров баз данных, отправьте GET-запрос на &#x60;/api/v1/dbs/parameters&#x60;.   Ответ содержит только имена параметров, доступных для каждого типа кластера. Рекомендуемые значения этих параметров для конкретной конфигурации возвращает &#x60;GET /api/v1/dbs/default-parameters&#x60;.
     * @param _callback The callback to be executed when the API call finishes
     * @return The request call
     * @throws ApiException If fail to process the API call, e.g. serializing the request body object
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> Ответ будет представлять собой объект JSON, ключи которого — типы кластеров баз данных, а значения — массивы имён доступных параметров. </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Некорректный запрос </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Не авторизован </td><td>  -  </td></tr>
        <tr><td> 403 </td><td> Запрещено </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Не найдено </td><td>  -  </td></tr>
        <tr><td> 429 </td><td> Слишком много запросов </td><td>  -  </td></tr>
        <tr><td> 500 </td><td> Внутренняя ошибка сервера </td><td>  -  </td></tr>
     </table>
     */
    public okhttp3.Call getDatabaseParametersAsync(final ApiCallback<DbParametersByType> _callback) throws ApiException {

        okhttp3.Call localVarCall = getDatabaseParametersValidateBeforeCall(_callback);
        Type localVarReturnType = new TypeToken<DbParametersByType>(){}.getType();
        localVarApiClient.executeAsync(localVarCall, localVarReturnType, _callback);
        return localVarCall;
    }
    /**
     * Build call for getDatabasePreset
     * @param presetId ID тарифа (required)
     * @param _callback Callback for upload/download progress
     * @return Call to execute
     * @throws ApiException If fail to serialize the request body object
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> Тариф успешно получен. </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Некорректный запрос </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Не авторизован </td><td>  -  </td></tr>
        <tr><td> 403 </td><td> Запрещено </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Не найдено </td><td>  -  </td></tr>
        <tr><td> 429 </td><td> Слишком много запросов </td><td>  -  </td></tr>
        <tr><td> 500 </td><td> Внутренняя ошибка сервера </td><td>  -  </td></tr>
     </table>
     */
    public okhttp3.Call getDatabasePresetCall(Integer presetId, final ApiCallback _callback) throws ApiException {
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
        String localVarPath = "/api/v2/dbs/presets/{preset_id}"
            .replace("{" + "preset_id" + "}", localVarApiClient.escapeString(presetId.toString()));

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
    private okhttp3.Call getDatabasePresetValidateBeforeCall(Integer presetId, final ApiCallback _callback) throws ApiException {
        // verify the required parameter 'presetId' is set
        if (presetId == null) {
            throw new ApiException("Missing the required parameter 'presetId' when calling getDatabasePreset(Async)");
        }

        return getDatabasePresetCall(presetId, _callback);

    }

    /**
     * Получение тарифа для базы данных
     * Чтобы получить тариф для базы данных, отправьте GET-запрос на &#x60;/api/v2/dbs/presets/{preset_id}&#x60;.   Тело ответа будет представлять собой объект JSON с ключом &#x60;databases_preset&#x60;.
     * @param presetId ID тарифа (required)
     * @return GetDatabasePreset200Response
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> Тариф успешно получен. </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Некорректный запрос </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Не авторизован </td><td>  -  </td></tr>
        <tr><td> 403 </td><td> Запрещено </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Не найдено </td><td>  -  </td></tr>
        <tr><td> 429 </td><td> Слишком много запросов </td><td>  -  </td></tr>
        <tr><td> 500 </td><td> Внутренняя ошибка сервера </td><td>  -  </td></tr>
     </table>
     */
    public GetDatabasePreset200Response getDatabasePreset(Integer presetId) throws ApiException {
        ApiResponse<GetDatabasePreset200Response> localVarResp = getDatabasePresetWithHttpInfo(presetId);
        return localVarResp.getData();
    }

    /**
     * Получение тарифа для базы данных
     * Чтобы получить тариф для базы данных, отправьте GET-запрос на &#x60;/api/v2/dbs/presets/{preset_id}&#x60;.   Тело ответа будет представлять собой объект JSON с ключом &#x60;databases_preset&#x60;.
     * @param presetId ID тарифа (required)
     * @return ApiResponse&lt;GetDatabasePreset200Response&gt;
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> Тариф успешно получен. </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Некорректный запрос </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Не авторизован </td><td>  -  </td></tr>
        <tr><td> 403 </td><td> Запрещено </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Не найдено </td><td>  -  </td></tr>
        <tr><td> 429 </td><td> Слишком много запросов </td><td>  -  </td></tr>
        <tr><td> 500 </td><td> Внутренняя ошибка сервера </td><td>  -  </td></tr>
     </table>
     */
    public ApiResponse<GetDatabasePreset200Response> getDatabasePresetWithHttpInfo(Integer presetId) throws ApiException {
        okhttp3.Call localVarCall = getDatabasePresetValidateBeforeCall(presetId, null);
        Type localVarReturnType = new TypeToken<GetDatabasePreset200Response>(){}.getType();
        return localVarApiClient.execute(localVarCall, localVarReturnType);
    }

    /**
     * Получение тарифа для базы данных (asynchronously)
     * Чтобы получить тариф для базы данных, отправьте GET-запрос на &#x60;/api/v2/dbs/presets/{preset_id}&#x60;.   Тело ответа будет представлять собой объект JSON с ключом &#x60;databases_preset&#x60;.
     * @param presetId ID тарифа (required)
     * @param _callback The callback to be executed when the API call finishes
     * @return The request call
     * @throws ApiException If fail to process the API call, e.g. serializing the request body object
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> Тариф успешно получен. </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Некорректный запрос </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Не авторизован </td><td>  -  </td></tr>
        <tr><td> 403 </td><td> Запрещено </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Не найдено </td><td>  -  </td></tr>
        <tr><td> 429 </td><td> Слишком много запросов </td><td>  -  </td></tr>
        <tr><td> 500 </td><td> Внутренняя ошибка сервера </td><td>  -  </td></tr>
     </table>
     */
    public okhttp3.Call getDatabasePresetAsync(Integer presetId, final ApiCallback<GetDatabasePreset200Response> _callback) throws ApiException {

        okhttp3.Call localVarCall = getDatabasePresetValidateBeforeCall(presetId, _callback);
        Type localVarReturnType = new TypeToken<GetDatabasePreset200Response>(){}.getType();
        localVarApiClient.executeAsync(localVarCall, localVarReturnType, _callback);
        return localVarCall;
    }
    /**
     * Build call for getDatabasePrivileges
     * @param dbClusterId ID кластера базы данных (required)
     * @param _callback Callback for upload/download progress
     * @return Call to execute
     * @throws ApiException If fail to serialize the request body object
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> Ответ будет представлять собой объект JSON c ключом &#x60;privileges&#x60;. </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Некорректный запрос </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Не авторизован </td><td>  -  </td></tr>
        <tr><td> 403 </td><td> Запрещено </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Не найдено </td><td>  -  </td></tr>
        <tr><td> 429 </td><td> Слишком много запросов </td><td>  -  </td></tr>
        <tr><td> 500 </td><td> Внутренняя ошибка сервера </td><td>  -  </td></tr>
     </table>
     */
    public okhttp3.Call getDatabasePrivilegesCall(Integer dbClusterId, final ApiCallback _callback) throws ApiException {
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
        String localVarPath = "/api/v1/databases/{db_cluster_id}/privileges"
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
    private okhttp3.Call getDatabasePrivilegesValidateBeforeCall(Integer dbClusterId, final ApiCallback _callback) throws ApiException {
        // verify the required parameter 'dbClusterId' is set
        if (dbClusterId == null) {
            throw new ApiException("Missing the required parameter 'dbClusterId' when calling getDatabasePrivileges(Async)");
        }

        return getDatabasePrivilegesCall(dbClusterId, _callback);

    }

    /**
     * Получение привилегий кластера базы данных
     * Чтобы получить список привилегий, которые можно выдать пользователям кластера базы данных, отправьте GET-запрос на &#x60;/api/v1/databases/{db_cluster_id}/privileges&#x60;.\\    Список зависит от типа СУБД кластера и определяется сервером автоматически: возвращаются только те привилегии, которые допустимы для этого кластера. Используйте его, чтобы заполнить поле &#x60;privileges&#x60; при &lt;a href&#x3D;&#39;#tag/Bazy-dannyh/operation/createDatabaseUser&#39;&gt;создании&lt;/a&gt; или &lt;a href&#x3D;&#39;#tag/Bazy-dannyh/operation/updateDatabaseUser&#39;&gt;изменении&lt;/a&gt; пользователя базы данных.
     * @param dbClusterId ID кластера базы данных (required)
     * @return GetDatabasePrivileges200Response
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> Ответ будет представлять собой объект JSON c ключом &#x60;privileges&#x60;. </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Некорректный запрос </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Не авторизован </td><td>  -  </td></tr>
        <tr><td> 403 </td><td> Запрещено </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Не найдено </td><td>  -  </td></tr>
        <tr><td> 429 </td><td> Слишком много запросов </td><td>  -  </td></tr>
        <tr><td> 500 </td><td> Внутренняя ошибка сервера </td><td>  -  </td></tr>
     </table>
     */
    public GetDatabasePrivileges200Response getDatabasePrivileges(Integer dbClusterId) throws ApiException {
        ApiResponse<GetDatabasePrivileges200Response> localVarResp = getDatabasePrivilegesWithHttpInfo(dbClusterId);
        return localVarResp.getData();
    }

    /**
     * Получение привилегий кластера базы данных
     * Чтобы получить список привилегий, которые можно выдать пользователям кластера базы данных, отправьте GET-запрос на &#x60;/api/v1/databases/{db_cluster_id}/privileges&#x60;.\\    Список зависит от типа СУБД кластера и определяется сервером автоматически: возвращаются только те привилегии, которые допустимы для этого кластера. Используйте его, чтобы заполнить поле &#x60;privileges&#x60; при &lt;a href&#x3D;&#39;#tag/Bazy-dannyh/operation/createDatabaseUser&#39;&gt;создании&lt;/a&gt; или &lt;a href&#x3D;&#39;#tag/Bazy-dannyh/operation/updateDatabaseUser&#39;&gt;изменении&lt;/a&gt; пользователя базы данных.
     * @param dbClusterId ID кластера базы данных (required)
     * @return ApiResponse&lt;GetDatabasePrivileges200Response&gt;
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> Ответ будет представлять собой объект JSON c ключом &#x60;privileges&#x60;. </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Некорректный запрос </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Не авторизован </td><td>  -  </td></tr>
        <tr><td> 403 </td><td> Запрещено </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Не найдено </td><td>  -  </td></tr>
        <tr><td> 429 </td><td> Слишком много запросов </td><td>  -  </td></tr>
        <tr><td> 500 </td><td> Внутренняя ошибка сервера </td><td>  -  </td></tr>
     </table>
     */
    public ApiResponse<GetDatabasePrivileges200Response> getDatabasePrivilegesWithHttpInfo(Integer dbClusterId) throws ApiException {
        okhttp3.Call localVarCall = getDatabasePrivilegesValidateBeforeCall(dbClusterId, null);
        Type localVarReturnType = new TypeToken<GetDatabasePrivileges200Response>(){}.getType();
        return localVarApiClient.execute(localVarCall, localVarReturnType);
    }

    /**
     * Получение привилегий кластера базы данных (asynchronously)
     * Чтобы получить список привилегий, которые можно выдать пользователям кластера базы данных, отправьте GET-запрос на &#x60;/api/v1/databases/{db_cluster_id}/privileges&#x60;.\\    Список зависит от типа СУБД кластера и определяется сервером автоматически: возвращаются только те привилегии, которые допустимы для этого кластера. Используйте его, чтобы заполнить поле &#x60;privileges&#x60; при &lt;a href&#x3D;&#39;#tag/Bazy-dannyh/operation/createDatabaseUser&#39;&gt;создании&lt;/a&gt; или &lt;a href&#x3D;&#39;#tag/Bazy-dannyh/operation/updateDatabaseUser&#39;&gt;изменении&lt;/a&gt; пользователя базы данных.
     * @param dbClusterId ID кластера базы данных (required)
     * @param _callback The callback to be executed when the API call finishes
     * @return The request call
     * @throws ApiException If fail to process the API call, e.g. serializing the request body object
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> Ответ будет представлять собой объект JSON c ключом &#x60;privileges&#x60;. </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Некорректный запрос </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Не авторизован </td><td>  -  </td></tr>
        <tr><td> 403 </td><td> Запрещено </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Не найдено </td><td>  -  </td></tr>
        <tr><td> 429 </td><td> Слишком много запросов </td><td>  -  </td></tr>
        <tr><td> 500 </td><td> Внутренняя ошибка сервера </td><td>  -  </td></tr>
     </table>
     */
    public okhttp3.Call getDatabasePrivilegesAsync(Integer dbClusterId, final ApiCallback<GetDatabasePrivileges200Response> _callback) throws ApiException {

        okhttp3.Call localVarCall = getDatabasePrivilegesValidateBeforeCall(dbClusterId, _callback);
        Type localVarReturnType = new TypeToken<GetDatabasePrivileges200Response>(){}.getType();
        localVarApiClient.executeAsync(localVarCall, localVarReturnType, _callback);
        return localVarCall;
    }
    /**
     * Build call for getDatabaseS3Backup
     * @param dbId ID базы данных (required)
     * @param backupId ID резервной копии в формате UUID (required)
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
    public okhttp3.Call getDatabaseS3BackupCall(Integer dbId, UUID backupId, final ApiCallback _callback) throws ApiException {
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
        String localVarPath = "/api/v2/databases/{db_id}/backups/{backup_id}"
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
    private okhttp3.Call getDatabaseS3BackupValidateBeforeCall(Integer dbId, UUID backupId, final ApiCallback _callback) throws ApiException {
        // verify the required parameter 'dbId' is set
        if (dbId == null) {
            throw new ApiException("Missing the required parameter 'dbId' when calling getDatabaseS3Backup(Async)");
        }

        // verify the required parameter 'backupId' is set
        if (backupId == null) {
            throw new ApiException("Missing the required parameter 'backupId' when calling getDatabaseS3Backup(Async)");
        }

        return getDatabaseS3BackupCall(dbId, backupId, _callback);

    }

    /**
     * Получение S3-бэкапа базы данных
     * Чтобы получить информацию о резервной копии кластера базы данных в объектном хранилище, отправьте GET-запрос на &#x60;/api/v2/databases/{db_id}/backups/{backup_id}&#x60;.   Тело ответа будет представлять собой объект JSON с ключом &#x60;backup&#x60;. Обратите внимание, что &#x60;backup_id&#x60; здесь — строка в формате UUID, а не число, как в &#x60;/api/v1/dbs/{db_id}/backups/{backup_id}&#x60;.
     * @param dbId ID базы данных (required)
     * @param backupId ID резервной копии в формате UUID (required)
     * @return CreateDatabaseS3Backup201Response
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
    public CreateDatabaseS3Backup201Response getDatabaseS3Backup(Integer dbId, UUID backupId) throws ApiException {
        ApiResponse<CreateDatabaseS3Backup201Response> localVarResp = getDatabaseS3BackupWithHttpInfo(dbId, backupId);
        return localVarResp.getData();
    }

    /**
     * Получение S3-бэкапа базы данных
     * Чтобы получить информацию о резервной копии кластера базы данных в объектном хранилище, отправьте GET-запрос на &#x60;/api/v2/databases/{db_id}/backups/{backup_id}&#x60;.   Тело ответа будет представлять собой объект JSON с ключом &#x60;backup&#x60;. Обратите внимание, что &#x60;backup_id&#x60; здесь — строка в формате UUID, а не число, как в &#x60;/api/v1/dbs/{db_id}/backups/{backup_id}&#x60;.
     * @param dbId ID базы данных (required)
     * @param backupId ID резервной копии в формате UUID (required)
     * @return ApiResponse&lt;CreateDatabaseS3Backup201Response&gt;
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
    public ApiResponse<CreateDatabaseS3Backup201Response> getDatabaseS3BackupWithHttpInfo(Integer dbId, UUID backupId) throws ApiException {
        okhttp3.Call localVarCall = getDatabaseS3BackupValidateBeforeCall(dbId, backupId, null);
        Type localVarReturnType = new TypeToken<CreateDatabaseS3Backup201Response>(){}.getType();
        return localVarApiClient.execute(localVarCall, localVarReturnType);
    }

    /**
     * Получение S3-бэкапа базы данных (asynchronously)
     * Чтобы получить информацию о резервной копии кластера базы данных в объектном хранилище, отправьте GET-запрос на &#x60;/api/v2/databases/{db_id}/backups/{backup_id}&#x60;.   Тело ответа будет представлять собой объект JSON с ключом &#x60;backup&#x60;. Обратите внимание, что &#x60;backup_id&#x60; здесь — строка в формате UUID, а не число, как в &#x60;/api/v1/dbs/{db_id}/backups/{backup_id}&#x60;.
     * @param dbId ID базы данных (required)
     * @param backupId ID резервной копии в формате UUID (required)
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
    public okhttp3.Call getDatabaseS3BackupAsync(Integer dbId, UUID backupId, final ApiCallback<CreateDatabaseS3Backup201Response> _callback) throws ApiException {

        okhttp3.Call localVarCall = getDatabaseS3BackupValidateBeforeCall(dbId, backupId, _callback);
        Type localVarReturnType = new TypeToken<CreateDatabaseS3Backup201Response>(){}.getType();
        localVarApiClient.executeAsync(localVarCall, localVarReturnType, _callback);
        return localVarCall;
    }
    /**
     * Build call for getDatabaseS3Backups
     * @param dbId ID базы данных (required)
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
    public okhttp3.Call getDatabaseS3BackupsCall(Integer dbId, final ApiCallback _callback) throws ApiException {
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
        String localVarPath = "/api/v2/databases/{db_id}/backups"
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
    private okhttp3.Call getDatabaseS3BackupsValidateBeforeCall(Integer dbId, final ApiCallback _callback) throws ApiException {
        // verify the required parameter 'dbId' is set
        if (dbId == null) {
            throw new ApiException("Missing the required parameter 'dbId' when calling getDatabaseS3Backups(Async)");
        }

        return getDatabaseS3BackupsCall(dbId, _callback);

    }

    /**
     * Список S3-бэкапов базы данных
     * Чтобы получить список резервных копий кластера базы данных в объектном хранилище, отправьте GET-запрос на &#x60;/api/v2/databases/{db_id}/backups&#x60;.   Тело ответа будет представлять собой объект JSON с ключом &#x60;backups&#x60;. Копии отсортированы по дате создания по убыванию — сначала самые свежие.   Резервное копирование в объектное хранилище доступно для кластеров MySQL и PostgreSQL. Идентификатор такой копии — строка в формате UUID; это отдельный от &#x60;/api/v1/dbs/{db_id}/backups&#x60; механизм, и идентификаторы копий между ними не взаимозаменяемы.
     * @param dbId ID базы данных (required)
     * @return GetDatabaseS3Backups200Response
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
    public GetDatabaseS3Backups200Response getDatabaseS3Backups(Integer dbId) throws ApiException {
        ApiResponse<GetDatabaseS3Backups200Response> localVarResp = getDatabaseS3BackupsWithHttpInfo(dbId);
        return localVarResp.getData();
    }

    /**
     * Список S3-бэкапов базы данных
     * Чтобы получить список резервных копий кластера базы данных в объектном хранилище, отправьте GET-запрос на &#x60;/api/v2/databases/{db_id}/backups&#x60;.   Тело ответа будет представлять собой объект JSON с ключом &#x60;backups&#x60;. Копии отсортированы по дате создания по убыванию — сначала самые свежие.   Резервное копирование в объектное хранилище доступно для кластеров MySQL и PostgreSQL. Идентификатор такой копии — строка в формате UUID; это отдельный от &#x60;/api/v1/dbs/{db_id}/backups&#x60; механизм, и идентификаторы копий между ними не взаимозаменяемы.
     * @param dbId ID базы данных (required)
     * @return ApiResponse&lt;GetDatabaseS3Backups200Response&gt;
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
    public ApiResponse<GetDatabaseS3Backups200Response> getDatabaseS3BackupsWithHttpInfo(Integer dbId) throws ApiException {
        okhttp3.Call localVarCall = getDatabaseS3BackupsValidateBeforeCall(dbId, null);
        Type localVarReturnType = new TypeToken<GetDatabaseS3Backups200Response>(){}.getType();
        return localVarApiClient.execute(localVarCall, localVarReturnType);
    }

    /**
     * Список S3-бэкапов базы данных (asynchronously)
     * Чтобы получить список резервных копий кластера базы данных в объектном хранилище, отправьте GET-запрос на &#x60;/api/v2/databases/{db_id}/backups&#x60;.   Тело ответа будет представлять собой объект JSON с ключом &#x60;backups&#x60;. Копии отсортированы по дате создания по убыванию — сначала самые свежие.   Резервное копирование в объектное хранилище доступно для кластеров MySQL и PostgreSQL. Идентификатор такой копии — строка в формате UUID; это отдельный от &#x60;/api/v1/dbs/{db_id}/backups&#x60; механизм, и идентификаторы копий между ними не взаимозаменяемы.
     * @param dbId ID базы данных (required)
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
    public okhttp3.Call getDatabaseS3BackupsAsync(Integer dbId, final ApiCallback<GetDatabaseS3Backups200Response> _callback) throws ApiException {

        okhttp3.Call localVarCall = getDatabaseS3BackupsValidateBeforeCall(dbId, _callback);
        Type localVarReturnType = new TypeToken<GetDatabaseS3Backups200Response>(){}.getType();
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
     * Build call for getDatabasesPresets
     * @param clusterId ID кластера базы данных. Возвращает тарифы группы, в пределах которой доступна смена тарифа этого кластера (сценарий изменения кластера). (optional)
     * @param withUnavailable Включить в ответ тарифы, недоступные к заказу из-за нехватки свободных ресурсов. Учитывается только при запросе без &#x60;cluster_id&#x60;: вместе с &#x60;cluster_id&#x60; фильтр по свободным ресурсам и так не применяется. (optional)
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
    public okhttp3.Call getDatabasesPresetsCall(Integer clusterId, Boolean withUnavailable, final ApiCallback _callback) throws ApiException {
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
        String localVarPath = "/api/v2/presets/dbs";

        List<Pair> localVarQueryParams = new ArrayList<Pair>();
        List<Pair> localVarCollectionQueryParams = new ArrayList<Pair>();
        Map<String, String> localVarHeaderParams = new HashMap<String, String>();
        Map<String, String> localVarCookieParams = new HashMap<String, String>();
        Map<String, Object> localVarFormParams = new HashMap<String, Object>();

        if (clusterId != null) {
            localVarQueryParams.addAll(localVarApiClient.parameterToPair("cluster_id", clusterId));
        }

        if (withUnavailable != null) {
            localVarQueryParams.addAll(localVarApiClient.parameterToPair("with_unavailable", withUnavailable));
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
    private okhttp3.Call getDatabasesPresetsValidateBeforeCall(Integer clusterId, Boolean withUnavailable, final ApiCallback _callback) throws ApiException {
        return getDatabasesPresetsCall(clusterId, withUnavailable, _callback);

    }

    /**
     * Получение списка тарифов для баз данных
     * Чтобы получить список тарифов для баз данных, отправьте GET-запрос на &#x60;/api/v2/presets/dbs&#x60;.   Без параметров возвращаются тарифы, доступные к заказу — этот список используется при создании кластера. Если передать &#x60;cluster_id&#x60;, вернутся тарифы группы, в пределах которой можно сменить тариф указанного кластера.   Тело ответа будет представлять собой объект JSON с ключом &#x60;databases_presets&#x60;.
     * @param clusterId ID кластера базы данных. Возвращает тарифы группы, в пределах которой доступна смена тарифа этого кластера (сценарий изменения кластера). (optional)
     * @param withUnavailable Включить в ответ тарифы, недоступные к заказу из-за нехватки свободных ресурсов. Учитывается только при запросе без &#x60;cluster_id&#x60;: вместе с &#x60;cluster_id&#x60; фильтр по свободным ресурсам и так не применяется. (optional)
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
    public GetDatabasesPresets200Response getDatabasesPresets(Integer clusterId, Boolean withUnavailable) throws ApiException {
        ApiResponse<GetDatabasesPresets200Response> localVarResp = getDatabasesPresetsWithHttpInfo(clusterId, withUnavailable);
        return localVarResp.getData();
    }

    /**
     * Получение списка тарифов для баз данных
     * Чтобы получить список тарифов для баз данных, отправьте GET-запрос на &#x60;/api/v2/presets/dbs&#x60;.   Без параметров возвращаются тарифы, доступные к заказу — этот список используется при создании кластера. Если передать &#x60;cluster_id&#x60;, вернутся тарифы группы, в пределах которой можно сменить тариф указанного кластера.   Тело ответа будет представлять собой объект JSON с ключом &#x60;databases_presets&#x60;.
     * @param clusterId ID кластера базы данных. Возвращает тарифы группы, в пределах которой доступна смена тарифа этого кластера (сценарий изменения кластера). (optional)
     * @param withUnavailable Включить в ответ тарифы, недоступные к заказу из-за нехватки свободных ресурсов. Учитывается только при запросе без &#x60;cluster_id&#x60;: вместе с &#x60;cluster_id&#x60; фильтр по свободным ресурсам и так не применяется. (optional)
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
    public ApiResponse<GetDatabasesPresets200Response> getDatabasesPresetsWithHttpInfo(Integer clusterId, Boolean withUnavailable) throws ApiException {
        okhttp3.Call localVarCall = getDatabasesPresetsValidateBeforeCall(clusterId, withUnavailable, null);
        Type localVarReturnType = new TypeToken<GetDatabasesPresets200Response>(){}.getType();
        return localVarApiClient.execute(localVarCall, localVarReturnType);
    }

    /**
     * Получение списка тарифов для баз данных (asynchronously)
     * Чтобы получить список тарифов для баз данных, отправьте GET-запрос на &#x60;/api/v2/presets/dbs&#x60;.   Без параметров возвращаются тарифы, доступные к заказу — этот список используется при создании кластера. Если передать &#x60;cluster_id&#x60;, вернутся тарифы группы, в пределах которой можно сменить тариф указанного кластера.   Тело ответа будет представлять собой объект JSON с ключом &#x60;databases_presets&#x60;.
     * @param clusterId ID кластера базы данных. Возвращает тарифы группы, в пределах которой доступна смена тарифа этого кластера (сценарий изменения кластера). (optional)
     * @param withUnavailable Включить в ответ тарифы, недоступные к заказу из-за нехватки свободных ресурсов. Учитывается только при запросе без &#x60;cluster_id&#x60;: вместе с &#x60;cluster_id&#x60; фильтр по свободным ресурсам и так не применяется. (optional)
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
    public okhttp3.Call getDatabasesPresetsAsync(Integer clusterId, Boolean withUnavailable, final ApiCallback<GetDatabasesPresets200Response> _callback) throws ApiException {

        okhttp3.Call localVarCall = getDatabasesPresetsValidateBeforeCall(clusterId, withUnavailable, _callback);
        Type localVarReturnType = new TypeToken<GetDatabasesPresets200Response>(){}.getType();
        localVarApiClient.executeAsync(localVarCall, localVarReturnType, _callback);
        return localVarCall;
    }
    /**
     * Build call for performDatabaseClusterAction
     * @param dbClusterId ID кластера базы данных (required)
     * @param clusterAction  (required)
     * @param _callback Callback for upload/download progress
     * @return Call to execute
     * @throws ApiException If fail to serialize the request body object
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 201 </td><td> Действие принято к выполнению. Тело ответа пустое. </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Некорректный запрос </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Не авторизован </td><td>  -  </td></tr>
        <tr><td> 403 </td><td> Запрещено </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Не найдено </td><td>  -  </td></tr>
        <tr><td> 429 </td><td> Слишком много запросов </td><td>  -  </td></tr>
        <tr><td> 500 </td><td> Внутренняя ошибка сервера </td><td>  -  </td></tr>
     </table>
     */
    public okhttp3.Call performDatabaseClusterActionCall(Integer dbClusterId, ClusterAction clusterAction, final ApiCallback _callback) throws ApiException {
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

        Object localVarPostBody = clusterAction;

        // create path and map variables
        String localVarPath = "/api/v1/databases/{db_cluster_id}/action"
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
    private okhttp3.Call performDatabaseClusterActionValidateBeforeCall(Integer dbClusterId, ClusterAction clusterAction, final ApiCallback _callback) throws ApiException {
        // verify the required parameter 'dbClusterId' is set
        if (dbClusterId == null) {
            throw new ApiException("Missing the required parameter 'dbClusterId' when calling performDatabaseClusterAction(Async)");
        }

        // verify the required parameter 'clusterAction' is set
        if (clusterAction == null) {
            throw new ApiException("Missing the required parameter 'clusterAction' when calling performDatabaseClusterAction(Async)");
        }

        return performDatabaseClusterActionCall(dbClusterId, clusterAction, _callback);

    }

    /**
     * Выполнение действия над кластером базы данных
     * Чтобы выполнить действие над кластером базы данных, отправьте POST-запрос на &#x60;/api/v1/databases/{db_cluster_id}/action&#x60;.   Доступные действия: &#x60;reboot&#x60; — перезагрузка кластера, &#x60;shutdown&#x60; — выключение кластера, &#x60;start&#x60; — включение кластера.
     * @param dbClusterId ID кластера базы данных (required)
     * @param clusterAction  (required)
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 201 </td><td> Действие принято к выполнению. Тело ответа пустое. </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Некорректный запрос </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Не авторизован </td><td>  -  </td></tr>
        <tr><td> 403 </td><td> Запрещено </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Не найдено </td><td>  -  </td></tr>
        <tr><td> 429 </td><td> Слишком много запросов </td><td>  -  </td></tr>
        <tr><td> 500 </td><td> Внутренняя ошибка сервера </td><td>  -  </td></tr>
     </table>
     */
    public void performDatabaseClusterAction(Integer dbClusterId, ClusterAction clusterAction) throws ApiException {
        performDatabaseClusterActionWithHttpInfo(dbClusterId, clusterAction);
    }

    /**
     * Выполнение действия над кластером базы данных
     * Чтобы выполнить действие над кластером базы данных, отправьте POST-запрос на &#x60;/api/v1/databases/{db_cluster_id}/action&#x60;.   Доступные действия: &#x60;reboot&#x60; — перезагрузка кластера, &#x60;shutdown&#x60; — выключение кластера, &#x60;start&#x60; — включение кластера.
     * @param dbClusterId ID кластера базы данных (required)
     * @param clusterAction  (required)
     * @return ApiResponse&lt;Void&gt;
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 201 </td><td> Действие принято к выполнению. Тело ответа пустое. </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Некорректный запрос </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Не авторизован </td><td>  -  </td></tr>
        <tr><td> 403 </td><td> Запрещено </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Не найдено </td><td>  -  </td></tr>
        <tr><td> 429 </td><td> Слишком много запросов </td><td>  -  </td></tr>
        <tr><td> 500 </td><td> Внутренняя ошибка сервера </td><td>  -  </td></tr>
     </table>
     */
    public ApiResponse<Void> performDatabaseClusterActionWithHttpInfo(Integer dbClusterId, ClusterAction clusterAction) throws ApiException {
        okhttp3.Call localVarCall = performDatabaseClusterActionValidateBeforeCall(dbClusterId, clusterAction, null);
        return localVarApiClient.execute(localVarCall);
    }

    /**
     * Выполнение действия над кластером базы данных (asynchronously)
     * Чтобы выполнить действие над кластером базы данных, отправьте POST-запрос на &#x60;/api/v1/databases/{db_cluster_id}/action&#x60;.   Доступные действия: &#x60;reboot&#x60; — перезагрузка кластера, &#x60;shutdown&#x60; — выключение кластера, &#x60;start&#x60; — включение кластера.
     * @param dbClusterId ID кластера базы данных (required)
     * @param clusterAction  (required)
     * @param _callback The callback to be executed when the API call finishes
     * @return The request call
     * @throws ApiException If fail to process the API call, e.g. serializing the request body object
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 201 </td><td> Действие принято к выполнению. Тело ответа пустое. </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Некорректный запрос </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Не авторизован </td><td>  -  </td></tr>
        <tr><td> 403 </td><td> Запрещено </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Не найдено </td><td>  -  </td></tr>
        <tr><td> 429 </td><td> Слишком много запросов </td><td>  -  </td></tr>
        <tr><td> 500 </td><td> Внутренняя ошибка сервера </td><td>  -  </td></tr>
     </table>
     */
    public okhttp3.Call performDatabaseClusterActionAsync(Integer dbClusterId, ClusterAction clusterAction, final ApiCallback<Void> _callback) throws ApiException {

        okhttp3.Call localVarCall = performDatabaseClusterActionValidateBeforeCall(dbClusterId, clusterAction, _callback);
        localVarApiClient.executeAsync(localVarCall, _callback);
        return localVarCall;
    }
    /**
     * Build call for restoreDatabaseFromBackup
     * @param dbId ID базы данных (required)
     * @param backupId ID резервной копии (required)
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
     * @param backupId ID резервной копии (required)
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
     * @param backupId ID резервной копии (required)
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
     * @param backupId ID резервной копии (required)
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
     * Build call for restoreDatabaseFromS3Backup
     * @param dbId ID базы данных (required)
     * @param backupId ID резервной копии в формате UUID (required)
     * @param _callback Callback for upload/download progress
     * @return Call to execute
     * @throws ApiException If fail to serialize the request body object
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 204 </td><td> Восстановление успешно запущено. Тело ответа пустое. </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Некорректный запрос </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Не авторизован </td><td>  -  </td></tr>
        <tr><td> 403 </td><td> Запрещено </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Не найдено </td><td>  -  </td></tr>
        <tr><td> 409 </td><td> Конфликт </td><td>  -  </td></tr>
        <tr><td> 429 </td><td> Слишком много запросов </td><td>  -  </td></tr>
        <tr><td> 500 </td><td> Внутренняя ошибка сервера </td><td>  -  </td></tr>
     </table>
     */
    public okhttp3.Call restoreDatabaseFromS3BackupCall(Integer dbId, UUID backupId, final ApiCallback _callback) throws ApiException {
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
        String localVarPath = "/api/v2/databases/{db_id}/backups/{backup_id}/restore"
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
        return localVarApiClient.buildCall(basePath, localVarPath, "POST", localVarQueryParams, localVarCollectionQueryParams, localVarPostBody, localVarHeaderParams, localVarCookieParams, localVarFormParams, localVarAuthNames, _callback);
    }

    @SuppressWarnings("rawtypes")
    private okhttp3.Call restoreDatabaseFromS3BackupValidateBeforeCall(Integer dbId, UUID backupId, final ApiCallback _callback) throws ApiException {
        // verify the required parameter 'dbId' is set
        if (dbId == null) {
            throw new ApiException("Missing the required parameter 'dbId' when calling restoreDatabaseFromS3Backup(Async)");
        }

        // verify the required parameter 'backupId' is set
        if (backupId == null) {
            throw new ApiException("Missing the required parameter 'backupId' when calling restoreDatabaseFromS3Backup(Async)");
        }

        return restoreDatabaseFromS3BackupCall(dbId, backupId, _callback);

    }

    /**
     * Восстановление базы данных из S3-бэкапа
     * Чтобы восстановить кластер базы данных из резервной копии в объектном хранилище, отправьте POST-запрос на &#x60;/api/v2/databases/{db_id}/backups/{backup_id}/restore&#x60;.   Тела запроса нет, тело ответа пустое. Восстановиться можно только из копии со статусом &#x60;success&#x60;.   Сразу после запуска кластер переходит в статус &#x60;backup_recovery&#x60;. Пока восстановление не завершится, создание, изменение и удаление резервных копий, а также повторный запуск восстановления недоступны.
     * @param dbId ID базы данных (required)
     * @param backupId ID резервной копии в формате UUID (required)
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 204 </td><td> Восстановление успешно запущено. Тело ответа пустое. </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Некорректный запрос </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Не авторизован </td><td>  -  </td></tr>
        <tr><td> 403 </td><td> Запрещено </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Не найдено </td><td>  -  </td></tr>
        <tr><td> 409 </td><td> Конфликт </td><td>  -  </td></tr>
        <tr><td> 429 </td><td> Слишком много запросов </td><td>  -  </td></tr>
        <tr><td> 500 </td><td> Внутренняя ошибка сервера </td><td>  -  </td></tr>
     </table>
     */
    public void restoreDatabaseFromS3Backup(Integer dbId, UUID backupId) throws ApiException {
        restoreDatabaseFromS3BackupWithHttpInfo(dbId, backupId);
    }

    /**
     * Восстановление базы данных из S3-бэкапа
     * Чтобы восстановить кластер базы данных из резервной копии в объектном хранилище, отправьте POST-запрос на &#x60;/api/v2/databases/{db_id}/backups/{backup_id}/restore&#x60;.   Тела запроса нет, тело ответа пустое. Восстановиться можно только из копии со статусом &#x60;success&#x60;.   Сразу после запуска кластер переходит в статус &#x60;backup_recovery&#x60;. Пока восстановление не завершится, создание, изменение и удаление резервных копий, а также повторный запуск восстановления недоступны.
     * @param dbId ID базы данных (required)
     * @param backupId ID резервной копии в формате UUID (required)
     * @return ApiResponse&lt;Void&gt;
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 204 </td><td> Восстановление успешно запущено. Тело ответа пустое. </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Некорректный запрос </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Не авторизован </td><td>  -  </td></tr>
        <tr><td> 403 </td><td> Запрещено </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Не найдено </td><td>  -  </td></tr>
        <tr><td> 409 </td><td> Конфликт </td><td>  -  </td></tr>
        <tr><td> 429 </td><td> Слишком много запросов </td><td>  -  </td></tr>
        <tr><td> 500 </td><td> Внутренняя ошибка сервера </td><td>  -  </td></tr>
     </table>
     */
    public ApiResponse<Void> restoreDatabaseFromS3BackupWithHttpInfo(Integer dbId, UUID backupId) throws ApiException {
        okhttp3.Call localVarCall = restoreDatabaseFromS3BackupValidateBeforeCall(dbId, backupId, null);
        return localVarApiClient.execute(localVarCall);
    }

    /**
     * Восстановление базы данных из S3-бэкапа (asynchronously)
     * Чтобы восстановить кластер базы данных из резервной копии в объектном хранилище, отправьте POST-запрос на &#x60;/api/v2/databases/{db_id}/backups/{backup_id}/restore&#x60;.   Тела запроса нет, тело ответа пустое. Восстановиться можно только из копии со статусом &#x60;success&#x60;.   Сразу после запуска кластер переходит в статус &#x60;backup_recovery&#x60;. Пока восстановление не завершится, создание, изменение и удаление резервных копий, а также повторный запуск восстановления недоступны.
     * @param dbId ID базы данных (required)
     * @param backupId ID резервной копии в формате UUID (required)
     * @param _callback The callback to be executed when the API call finishes
     * @return The request call
     * @throws ApiException If fail to process the API call, e.g. serializing the request body object
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 204 </td><td> Восстановление успешно запущено. Тело ответа пустое. </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Некорректный запрос </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Не авторизован </td><td>  -  </td></tr>
        <tr><td> 403 </td><td> Запрещено </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Не найдено </td><td>  -  </td></tr>
        <tr><td> 409 </td><td> Конфликт </td><td>  -  </td></tr>
        <tr><td> 429 </td><td> Слишком много запросов </td><td>  -  </td></tr>
        <tr><td> 500 </td><td> Внутренняя ошибка сервера </td><td>  -  </td></tr>
     </table>
     */
    public okhttp3.Call restoreDatabaseFromS3BackupAsync(Integer dbId, UUID backupId, final ApiCallback<Void> _callback) throws ApiException {

        okhttp3.Call localVarCall = restoreDatabaseFromS3BackupValidateBeforeCall(dbId, backupId, _callback);
        localVarApiClient.executeAsync(localVarCall, _callback);
        return localVarCall;
    }
    /**
     * Build call for updateDatabaseAutoBackupsSettings
     * @param dbId ID базы данных (required)
     * @param updateAutoBackup При значении &#x60;is_enabled&#x60;: &#x60;true&#x60;, поля &#x60;copy_count&#x60;, &#x60;creation_start_at&#x60;, &#x60;interval&#x60; являются обязательными (required)
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
        <tr><td> 409 </td><td> Конфликт </td><td>  -  </td></tr>
        <tr><td> 429 </td><td> Слишком много запросов </td><td>  -  </td></tr>
        <tr><td> 500 </td><td> Внутренняя ошибка сервера </td><td>  -  </td></tr>
     </table>
     */
    public okhttp3.Call updateDatabaseAutoBackupsSettingsCall(Integer dbId, UpdateAutoBackup updateAutoBackup, final ApiCallback _callback) throws ApiException {
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

        Object localVarPostBody = updateAutoBackup;

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
    private okhttp3.Call updateDatabaseAutoBackupsSettingsValidateBeforeCall(Integer dbId, UpdateAutoBackup updateAutoBackup, final ApiCallback _callback) throws ApiException {
        // verify the required parameter 'dbId' is set
        if (dbId == null) {
            throw new ApiException("Missing the required parameter 'dbId' when calling updateDatabaseAutoBackupsSettings(Async)");
        }

        // verify the required parameter 'updateAutoBackup' is set
        if (updateAutoBackup == null) {
            throw new ApiException("Missing the required parameter 'updateAutoBackup' when calling updateDatabaseAutoBackupsSettings(Async)");
        }

        return updateDatabaseAutoBackupsSettingsCall(dbId, updateAutoBackup, _callback);

    }

    /**
     * Изменение настроек автобэкапов базы данных
     * Чтобы изменить список настроек автобэкапов базы данных, отправьте запрос PATCH в &#x60;api/v1/dbs/{db_id}/auto-backups&#x60;
     * @param dbId ID базы данных (required)
     * @param updateAutoBackup При значении &#x60;is_enabled&#x60;: &#x60;true&#x60;, поля &#x60;copy_count&#x60;, &#x60;creation_start_at&#x60;, &#x60;interval&#x60; являются обязательными (required)
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
        <tr><td> 409 </td><td> Конфликт </td><td>  -  </td></tr>
        <tr><td> 429 </td><td> Слишком много запросов </td><td>  -  </td></tr>
        <tr><td> 500 </td><td> Внутренняя ошибка сервера </td><td>  -  </td></tr>
     </table>
     */
    public GetDatabaseAutoBackupsSettings200Response updateDatabaseAutoBackupsSettings(Integer dbId, UpdateAutoBackup updateAutoBackup) throws ApiException {
        ApiResponse<GetDatabaseAutoBackupsSettings200Response> localVarResp = updateDatabaseAutoBackupsSettingsWithHttpInfo(dbId, updateAutoBackup);
        return localVarResp.getData();
    }

    /**
     * Изменение настроек автобэкапов базы данных
     * Чтобы изменить список настроек автобэкапов базы данных, отправьте запрос PATCH в &#x60;api/v1/dbs/{db_id}/auto-backups&#x60;
     * @param dbId ID базы данных (required)
     * @param updateAutoBackup При значении &#x60;is_enabled&#x60;: &#x60;true&#x60;, поля &#x60;copy_count&#x60;, &#x60;creation_start_at&#x60;, &#x60;interval&#x60; являются обязательными (required)
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
        <tr><td> 409 </td><td> Конфликт </td><td>  -  </td></tr>
        <tr><td> 429 </td><td> Слишком много запросов </td><td>  -  </td></tr>
        <tr><td> 500 </td><td> Внутренняя ошибка сервера </td><td>  -  </td></tr>
     </table>
     */
    public ApiResponse<GetDatabaseAutoBackupsSettings200Response> updateDatabaseAutoBackupsSettingsWithHttpInfo(Integer dbId, UpdateAutoBackup updateAutoBackup) throws ApiException {
        okhttp3.Call localVarCall = updateDatabaseAutoBackupsSettingsValidateBeforeCall(dbId, updateAutoBackup, null);
        Type localVarReturnType = new TypeToken<GetDatabaseAutoBackupsSettings200Response>(){}.getType();
        return localVarApiClient.execute(localVarCall, localVarReturnType);
    }

    /**
     * Изменение настроек автобэкапов базы данных (asynchronously)
     * Чтобы изменить список настроек автобэкапов базы данных, отправьте запрос PATCH в &#x60;api/v1/dbs/{db_id}/auto-backups&#x60;
     * @param dbId ID базы данных (required)
     * @param updateAutoBackup При значении &#x60;is_enabled&#x60;: &#x60;true&#x60;, поля &#x60;copy_count&#x60;, &#x60;creation_start_at&#x60;, &#x60;interval&#x60; являются обязательными (required)
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
        <tr><td> 409 </td><td> Конфликт </td><td>  -  </td></tr>
        <tr><td> 429 </td><td> Слишком много запросов </td><td>  -  </td></tr>
        <tr><td> 500 </td><td> Внутренняя ошибка сервера </td><td>  -  </td></tr>
     </table>
     */
    public okhttp3.Call updateDatabaseAutoBackupsSettingsAsync(Integer dbId, UpdateAutoBackup updateAutoBackup, final ApiCallback<GetDatabaseAutoBackupsSettings200Response> _callback) throws ApiException {

        okhttp3.Call localVarCall = updateDatabaseAutoBackupsSettingsValidateBeforeCall(dbId, updateAutoBackup, _callback);
        Type localVarReturnType = new TypeToken<GetDatabaseAutoBackupsSettings200Response>(){}.getType();
        localVarApiClient.executeAsync(localVarCall, localVarReturnType, _callback);
        return localVarCall;
    }
    /**
     * Build call for updateDatabaseBackup
     * @param dbId ID базы данных (required)
     * @param backupId ID резервной копии (required)
     * @param dbsUpdateBackup  (required)
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
        <tr><td> 409 </td><td> Конфликт </td><td>  -  </td></tr>
        <tr><td> 429 </td><td> Слишком много запросов </td><td>  -  </td></tr>
        <tr><td> 500 </td><td> Внутренняя ошибка сервера </td><td>  -  </td></tr>
     </table>
     */
    public okhttp3.Call updateDatabaseBackupCall(Integer dbId, Integer backupId, DbsUpdateBackup dbsUpdateBackup, final ApiCallback _callback) throws ApiException {
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

        Object localVarPostBody = dbsUpdateBackup;

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
    private okhttp3.Call updateDatabaseBackupValidateBeforeCall(Integer dbId, Integer backupId, DbsUpdateBackup dbsUpdateBackup, final ApiCallback _callback) throws ApiException {
        // verify the required parameter 'dbId' is set
        if (dbId == null) {
            throw new ApiException("Missing the required parameter 'dbId' when calling updateDatabaseBackup(Async)");
        }

        // verify the required parameter 'backupId' is set
        if (backupId == null) {
            throw new ApiException("Missing the required parameter 'backupId' when calling updateDatabaseBackup(Async)");
        }

        // verify the required parameter 'dbsUpdateBackup' is set
        if (dbsUpdateBackup == null) {
            throw new ApiException("Missing the required parameter 'dbsUpdateBackup' when calling updateDatabaseBackup(Async)");
        }

        return updateDatabaseBackupCall(dbId, backupId, dbsUpdateBackup, _callback);

    }

    /**
     * Изменение комментария к бэкапу базы данных
     * Чтобы изменить комментарий к бэкапу базы данных, отправьте PATCH-запрос на &#x60;/api/v1/dbs/{db_id}/backups/{backup_id}&#x60;.  Тело ответа будет представлять собой объект JSON с ключом &#x60;backup&#x60;. 
     * @param dbId ID базы данных (required)
     * @param backupId ID резервной копии (required)
     * @param dbsUpdateBackup  (required)
     * @return GetDatabaseBackup200Response
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> Ответ будет представлять собой объект JSON с ключом &#x60;backup&#x60;. </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Некорректный запрос </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Не авторизован </td><td>  -  </td></tr>
        <tr><td> 403 </td><td> Запрещено </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Не найдено </td><td>  -  </td></tr>
        <tr><td> 409 </td><td> Конфликт </td><td>  -  </td></tr>
        <tr><td> 429 </td><td> Слишком много запросов </td><td>  -  </td></tr>
        <tr><td> 500 </td><td> Внутренняя ошибка сервера </td><td>  -  </td></tr>
     </table>
     */
    public GetDatabaseBackup200Response updateDatabaseBackup(Integer dbId, Integer backupId, DbsUpdateBackup dbsUpdateBackup) throws ApiException {
        ApiResponse<GetDatabaseBackup200Response> localVarResp = updateDatabaseBackupWithHttpInfo(dbId, backupId, dbsUpdateBackup);
        return localVarResp.getData();
    }

    /**
     * Изменение комментария к бэкапу базы данных
     * Чтобы изменить комментарий к бэкапу базы данных, отправьте PATCH-запрос на &#x60;/api/v1/dbs/{db_id}/backups/{backup_id}&#x60;.  Тело ответа будет представлять собой объект JSON с ключом &#x60;backup&#x60;. 
     * @param dbId ID базы данных (required)
     * @param backupId ID резервной копии (required)
     * @param dbsUpdateBackup  (required)
     * @return ApiResponse&lt;GetDatabaseBackup200Response&gt;
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> Ответ будет представлять собой объект JSON с ключом &#x60;backup&#x60;. </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Некорректный запрос </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Не авторизован </td><td>  -  </td></tr>
        <tr><td> 403 </td><td> Запрещено </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Не найдено </td><td>  -  </td></tr>
        <tr><td> 409 </td><td> Конфликт </td><td>  -  </td></tr>
        <tr><td> 429 </td><td> Слишком много запросов </td><td>  -  </td></tr>
        <tr><td> 500 </td><td> Внутренняя ошибка сервера </td><td>  -  </td></tr>
     </table>
     */
    public ApiResponse<GetDatabaseBackup200Response> updateDatabaseBackupWithHttpInfo(Integer dbId, Integer backupId, DbsUpdateBackup dbsUpdateBackup) throws ApiException {
        okhttp3.Call localVarCall = updateDatabaseBackupValidateBeforeCall(dbId, backupId, dbsUpdateBackup, null);
        Type localVarReturnType = new TypeToken<GetDatabaseBackup200Response>(){}.getType();
        return localVarApiClient.execute(localVarCall, localVarReturnType);
    }

    /**
     * Изменение комментария к бэкапу базы данных (asynchronously)
     * Чтобы изменить комментарий к бэкапу базы данных, отправьте PATCH-запрос на &#x60;/api/v1/dbs/{db_id}/backups/{backup_id}&#x60;.  Тело ответа будет представлять собой объект JSON с ключом &#x60;backup&#x60;. 
     * @param dbId ID базы данных (required)
     * @param backupId ID резервной копии (required)
     * @param dbsUpdateBackup  (required)
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
        <tr><td> 409 </td><td> Конфликт </td><td>  -  </td></tr>
        <tr><td> 429 </td><td> Слишком много запросов </td><td>  -  </td></tr>
        <tr><td> 500 </td><td> Внутренняя ошибка сервера </td><td>  -  </td></tr>
     </table>
     */
    public okhttp3.Call updateDatabaseBackupAsync(Integer dbId, Integer backupId, DbsUpdateBackup dbsUpdateBackup, final ApiCallback<GetDatabaseBackup200Response> _callback) throws ApiException {

        okhttp3.Call localVarCall = updateDatabaseBackupValidateBeforeCall(dbId, backupId, dbsUpdateBackup, _callback);
        Type localVarReturnType = new TypeToken<GetDatabaseBackup200Response>(){}.getType();
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
     * Чтобы изменить кластер базы данных на вашем аккаунте, отправьте PATCH-запрос на &#x60;/api/v1/databases/{db_cluster_id}&#x60;.   Размер кластера задается либо тарифом (&#x60;preset_id&#x60;), либо конфигуратором (&#x60;configuration&#x60;) — эти поля взаимоисключающие.
     * @param dbClusterId ID кластера базы данных (required)
     * @param updateCluster  (required)
     * @return UpdateDatabaseCluster200Response
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
    public UpdateDatabaseCluster200Response updateDatabaseCluster(Integer dbClusterId, UpdateCluster updateCluster) throws ApiException {
        ApiResponse<UpdateDatabaseCluster200Response> localVarResp = updateDatabaseClusterWithHttpInfo(dbClusterId, updateCluster);
        return localVarResp.getData();
    }

    /**
     * Изменение кластера базы данных
     * Чтобы изменить кластер базы данных на вашем аккаунте, отправьте PATCH-запрос на &#x60;/api/v1/databases/{db_cluster_id}&#x60;.   Размер кластера задается либо тарифом (&#x60;preset_id&#x60;), либо конфигуратором (&#x60;configuration&#x60;) — эти поля взаимоисключающие.
     * @param dbClusterId ID кластера базы данных (required)
     * @param updateCluster  (required)
     * @return ApiResponse&lt;UpdateDatabaseCluster200Response&gt;
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
    public ApiResponse<UpdateDatabaseCluster200Response> updateDatabaseClusterWithHttpInfo(Integer dbClusterId, UpdateCluster updateCluster) throws ApiException {
        okhttp3.Call localVarCall = updateDatabaseClusterValidateBeforeCall(dbClusterId, updateCluster, null);
        Type localVarReturnType = new TypeToken<UpdateDatabaseCluster200Response>(){}.getType();
        return localVarApiClient.execute(localVarCall, localVarReturnType);
    }

    /**
     * Изменение кластера базы данных (asynchronously)
     * Чтобы изменить кластер базы данных на вашем аккаунте, отправьте PATCH-запрос на &#x60;/api/v1/databases/{db_cluster_id}&#x60;.   Размер кластера задается либо тарифом (&#x60;preset_id&#x60;), либо конфигуратором (&#x60;configuration&#x60;) — эти поля взаимоисключающие.
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
    public okhttp3.Call updateDatabaseClusterAsync(Integer dbClusterId, UpdateCluster updateCluster, final ApiCallback<UpdateDatabaseCluster200Response> _callback) throws ApiException {

        okhttp3.Call localVarCall = updateDatabaseClusterValidateBeforeCall(dbClusterId, updateCluster, _callback);
        Type localVarReturnType = new TypeToken<UpdateDatabaseCluster200Response>(){}.getType();
        localVarApiClient.executeAsync(localVarCall, localVarReturnType, _callback);
        return localVarCall;
    }
    /**
     * Build call for updateDatabaseClusterV2
     * @param dbClusterId ID кластера базы данных (required)
     * @param updateClusterV2  (required)
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
    public okhttp3.Call updateDatabaseClusterV2Call(Integer dbClusterId, UpdateClusterV2 updateClusterV2, final ApiCallback _callback) throws ApiException {
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

        Object localVarPostBody = updateClusterV2;

        // create path and map variables
        String localVarPath = "/api/v2/databases/{db_cluster_id}"
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
    private okhttp3.Call updateDatabaseClusterV2ValidateBeforeCall(Integer dbClusterId, UpdateClusterV2 updateClusterV2, final ApiCallback _callback) throws ApiException {
        // verify the required parameter 'dbClusterId' is set
        if (dbClusterId == null) {
            throw new ApiException("Missing the required parameter 'dbClusterId' when calling updateDatabaseClusterV2(Async)");
        }

        // verify the required parameter 'updateClusterV2' is set
        if (updateClusterV2 == null) {
            throw new ApiException("Missing the required parameter 'updateClusterV2' when calling updateDatabaseClusterV2(Async)");
        }

        return updateDatabaseClusterV2Call(dbClusterId, updateClusterV2, _callback);

    }

    /**
     * Изменение кластера базы данных (v2)
     * Чтобы изменить кластер базы данных на вашем аккаунте, отправьте PATCH-запрос на &#x60;/api/v2/databases/{db_cluster_id}&#x60;.   В отличие от &#x60;/api/v1/databases/{db_cluster_id}&#x60;, эта версия дополнительно позволяет привязать плавающий IP-адрес (&#x60;floating_ip&#x60;).   Размер кластера задается либо тарифом (&#x60;preset_id&#x60;), либо конфигуратором (&#x60;configuration&#x60;) — эти поля взаимоисключающие.
     * @param dbClusterId ID кластера базы данных (required)
     * @param updateClusterV2  (required)
     * @return UpdateDatabaseCluster200Response
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
    public UpdateDatabaseCluster200Response updateDatabaseClusterV2(Integer dbClusterId, UpdateClusterV2 updateClusterV2) throws ApiException {
        ApiResponse<UpdateDatabaseCluster200Response> localVarResp = updateDatabaseClusterV2WithHttpInfo(dbClusterId, updateClusterV2);
        return localVarResp.getData();
    }

    /**
     * Изменение кластера базы данных (v2)
     * Чтобы изменить кластер базы данных на вашем аккаунте, отправьте PATCH-запрос на &#x60;/api/v2/databases/{db_cluster_id}&#x60;.   В отличие от &#x60;/api/v1/databases/{db_cluster_id}&#x60;, эта версия дополнительно позволяет привязать плавающий IP-адрес (&#x60;floating_ip&#x60;).   Размер кластера задается либо тарифом (&#x60;preset_id&#x60;), либо конфигуратором (&#x60;configuration&#x60;) — эти поля взаимоисключающие.
     * @param dbClusterId ID кластера базы данных (required)
     * @param updateClusterV2  (required)
     * @return ApiResponse&lt;UpdateDatabaseCluster200Response&gt;
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
    public ApiResponse<UpdateDatabaseCluster200Response> updateDatabaseClusterV2WithHttpInfo(Integer dbClusterId, UpdateClusterV2 updateClusterV2) throws ApiException {
        okhttp3.Call localVarCall = updateDatabaseClusterV2ValidateBeforeCall(dbClusterId, updateClusterV2, null);
        Type localVarReturnType = new TypeToken<UpdateDatabaseCluster200Response>(){}.getType();
        return localVarApiClient.execute(localVarCall, localVarReturnType);
    }

    /**
     * Изменение кластера базы данных (v2) (asynchronously)
     * Чтобы изменить кластер базы данных на вашем аккаунте, отправьте PATCH-запрос на &#x60;/api/v2/databases/{db_cluster_id}&#x60;.   В отличие от &#x60;/api/v1/databases/{db_cluster_id}&#x60;, эта версия дополнительно позволяет привязать плавающий IP-адрес (&#x60;floating_ip&#x60;).   Размер кластера задается либо тарифом (&#x60;preset_id&#x60;), либо конфигуратором (&#x60;configuration&#x60;) — эти поля взаимоисключающие.
     * @param dbClusterId ID кластера базы данных (required)
     * @param updateClusterV2  (required)
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
    public okhttp3.Call updateDatabaseClusterV2Async(Integer dbClusterId, UpdateClusterV2 updateClusterV2, final ApiCallback<UpdateDatabaseCluster200Response> _callback) throws ApiException {

        okhttp3.Call localVarCall = updateDatabaseClusterV2ValidateBeforeCall(dbClusterId, updateClusterV2, _callback);
        Type localVarReturnType = new TypeToken<UpdateDatabaseCluster200Response>(){}.getType();
        localVarApiClient.executeAsync(localVarCall, localVarReturnType, _callback);
        return localVarCall;
    }
    /**
     * Build call for updateDatabaseInstance
     * @param dbClusterId ID кластера базы данных (required)
     * @param instanceId ID инстанса базы данных (required)
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
        <tr><td> 409 </td><td> Конфликт </td><td>  -  </td></tr>
        <tr><td> 429 </td><td> Слишком много запросов </td><td>  -  </td></tr>
        <tr><td> 500 </td><td> Внутренняя ошибка сервера </td><td>  -  </td></tr>
     </table>
     */
    public okhttp3.Call updateDatabaseInstanceCall(Integer dbClusterId, Integer instanceId, UpdateInstance updateInstance, final ApiCallback _callback) throws ApiException {
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
    private okhttp3.Call updateDatabaseInstanceValidateBeforeCall(Integer dbClusterId, Integer instanceId, UpdateInstance updateInstance, final ApiCallback _callback) throws ApiException {
        // verify the required parameter 'dbClusterId' is set
        if (dbClusterId == null) {
            throw new ApiException("Missing the required parameter 'dbClusterId' when calling updateDatabaseInstance(Async)");
        }

        // verify the required parameter 'instanceId' is set
        if (instanceId == null) {
            throw new ApiException("Missing the required parameter 'instanceId' when calling updateDatabaseInstance(Async)");
        }

        // verify the required parameter 'updateInstance' is set
        if (updateInstance == null) {
            throw new ApiException("Missing the required parameter 'updateInstance' when calling updateDatabaseInstance(Async)");
        }

        return updateDatabaseInstanceCall(dbClusterId, instanceId, updateInstance, _callback);

    }

    /**
     * Изменение инстанса базы данных
     * Чтобы изменить инстанс базы данных, отправьте PATCH-запрос на &#x60;/api/v1/databases/{db_cluster_id}/instances/{instance_id}&#x60;.   Изменить название базы данных (&#x60;name&#x60;) и ее владельца (&#x60;owner_id&#x60;) можно только в кластере PostgreSQL, а настройки топика (&#x60;config_parameters&#x60;) — только в кластере Kafka. Если один из этих трех параметров передан для неподходящего типа кластера, запрос вернется с ошибкой 409.   Расширения (&#x60;extensions&#x60;) применимы к кластерам PostgreSQL и RabbitMQ.
     * @param dbClusterId ID кластера базы данных (required)
     * @param instanceId ID инстанса базы данных (required)
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
        <tr><td> 409 </td><td> Конфликт </td><td>  -  </td></tr>
        <tr><td> 429 </td><td> Слишком много запросов </td><td>  -  </td></tr>
        <tr><td> 500 </td><td> Внутренняя ошибка сервера </td><td>  -  </td></tr>
     </table>
     */
    public CreateDatabaseInstance201Response updateDatabaseInstance(Integer dbClusterId, Integer instanceId, UpdateInstance updateInstance) throws ApiException {
        ApiResponse<CreateDatabaseInstance201Response> localVarResp = updateDatabaseInstanceWithHttpInfo(dbClusterId, instanceId, updateInstance);
        return localVarResp.getData();
    }

    /**
     * Изменение инстанса базы данных
     * Чтобы изменить инстанс базы данных, отправьте PATCH-запрос на &#x60;/api/v1/databases/{db_cluster_id}/instances/{instance_id}&#x60;.   Изменить название базы данных (&#x60;name&#x60;) и ее владельца (&#x60;owner_id&#x60;) можно только в кластере PostgreSQL, а настройки топика (&#x60;config_parameters&#x60;) — только в кластере Kafka. Если один из этих трех параметров передан для неподходящего типа кластера, запрос вернется с ошибкой 409.   Расширения (&#x60;extensions&#x60;) применимы к кластерам PostgreSQL и RabbitMQ.
     * @param dbClusterId ID кластера базы данных (required)
     * @param instanceId ID инстанса базы данных (required)
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
        <tr><td> 409 </td><td> Конфликт </td><td>  -  </td></tr>
        <tr><td> 429 </td><td> Слишком много запросов </td><td>  -  </td></tr>
        <tr><td> 500 </td><td> Внутренняя ошибка сервера </td><td>  -  </td></tr>
     </table>
     */
    public ApiResponse<CreateDatabaseInstance201Response> updateDatabaseInstanceWithHttpInfo(Integer dbClusterId, Integer instanceId, UpdateInstance updateInstance) throws ApiException {
        okhttp3.Call localVarCall = updateDatabaseInstanceValidateBeforeCall(dbClusterId, instanceId, updateInstance, null);
        Type localVarReturnType = new TypeToken<CreateDatabaseInstance201Response>(){}.getType();
        return localVarApiClient.execute(localVarCall, localVarReturnType);
    }

    /**
     * Изменение инстанса базы данных (asynchronously)
     * Чтобы изменить инстанс базы данных, отправьте PATCH-запрос на &#x60;/api/v1/databases/{db_cluster_id}/instances/{instance_id}&#x60;.   Изменить название базы данных (&#x60;name&#x60;) и ее владельца (&#x60;owner_id&#x60;) можно только в кластере PostgreSQL, а настройки топика (&#x60;config_parameters&#x60;) — только в кластере Kafka. Если один из этих трех параметров передан для неподходящего типа кластера, запрос вернется с ошибкой 409.   Расширения (&#x60;extensions&#x60;) применимы к кластерам PostgreSQL и RabbitMQ.
     * @param dbClusterId ID кластера базы данных (required)
     * @param instanceId ID инстанса базы данных (required)
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
        <tr><td> 409 </td><td> Конфликт </td><td>  -  </td></tr>
        <tr><td> 429 </td><td> Слишком много запросов </td><td>  -  </td></tr>
        <tr><td> 500 </td><td> Внутренняя ошибка сервера </td><td>  -  </td></tr>
     </table>
     */
    public okhttp3.Call updateDatabaseInstanceAsync(Integer dbClusterId, Integer instanceId, UpdateInstance updateInstance, final ApiCallback<CreateDatabaseInstance201Response> _callback) throws ApiException {

        okhttp3.Call localVarCall = updateDatabaseInstanceValidateBeforeCall(dbClusterId, instanceId, updateInstance, _callback);
        Type localVarReturnType = new TypeToken<CreateDatabaseInstance201Response>(){}.getType();
        localVarApiClient.executeAsync(localVarCall, localVarReturnType, _callback);
        return localVarCall;
    }
    /**
     * Build call for updateDatabaseS3Backup
     * @param dbId ID базы данных (required)
     * @param backupId ID резервной копии в формате UUID (required)
     * @param updateS3Backup  (optional)
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
        <tr><td> 409 </td><td> Конфликт </td><td>  -  </td></tr>
        <tr><td> 429 </td><td> Слишком много запросов </td><td>  -  </td></tr>
        <tr><td> 500 </td><td> Внутренняя ошибка сервера </td><td>  -  </td></tr>
     </table>
     */
    public okhttp3.Call updateDatabaseS3BackupCall(Integer dbId, UUID backupId, UpdateS3Backup updateS3Backup, final ApiCallback _callback) throws ApiException {
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

        Object localVarPostBody = updateS3Backup;

        // create path and map variables
        String localVarPath = "/api/v2/databases/{db_id}/backups/{backup_id}"
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
    private okhttp3.Call updateDatabaseS3BackupValidateBeforeCall(Integer dbId, UUID backupId, UpdateS3Backup updateS3Backup, final ApiCallback _callback) throws ApiException {
        // verify the required parameter 'dbId' is set
        if (dbId == null) {
            throw new ApiException("Missing the required parameter 'dbId' when calling updateDatabaseS3Backup(Async)");
        }

        // verify the required parameter 'backupId' is set
        if (backupId == null) {
            throw new ApiException("Missing the required parameter 'backupId' when calling updateDatabaseS3Backup(Async)");
        }

        return updateDatabaseS3BackupCall(dbId, backupId, updateS3Backup, _callback);

    }

    /**
     * Изменение комментария S3-бэкапа базы данных
     * Чтобы изменить комментарий к резервной копии кластера базы данных, отправьте PATCH-запрос на &#x60;/api/v2/databases/{db_id}/backups/{backup_id}&#x60;.   Изменить можно только комментарий: других полей метод не принимает, сама резервная копия при этом не пересоздается. Тело ответа будет представлять собой объект JSON с ключом &#x60;backup&#x60;.
     * @param dbId ID базы данных (required)
     * @param backupId ID резервной копии в формате UUID (required)
     * @param updateS3Backup  (optional)
     * @return CreateDatabaseS3Backup201Response
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> Ответ будет представлять собой объект JSON с ключом &#x60;backup&#x60;. </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Некорректный запрос </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Не авторизован </td><td>  -  </td></tr>
        <tr><td> 403 </td><td> Запрещено </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Не найдено </td><td>  -  </td></tr>
        <tr><td> 409 </td><td> Конфликт </td><td>  -  </td></tr>
        <tr><td> 429 </td><td> Слишком много запросов </td><td>  -  </td></tr>
        <tr><td> 500 </td><td> Внутренняя ошибка сервера </td><td>  -  </td></tr>
     </table>
     */
    public CreateDatabaseS3Backup201Response updateDatabaseS3Backup(Integer dbId, UUID backupId, UpdateS3Backup updateS3Backup) throws ApiException {
        ApiResponse<CreateDatabaseS3Backup201Response> localVarResp = updateDatabaseS3BackupWithHttpInfo(dbId, backupId, updateS3Backup);
        return localVarResp.getData();
    }

    /**
     * Изменение комментария S3-бэкапа базы данных
     * Чтобы изменить комментарий к резервной копии кластера базы данных, отправьте PATCH-запрос на &#x60;/api/v2/databases/{db_id}/backups/{backup_id}&#x60;.   Изменить можно только комментарий: других полей метод не принимает, сама резервная копия при этом не пересоздается. Тело ответа будет представлять собой объект JSON с ключом &#x60;backup&#x60;.
     * @param dbId ID базы данных (required)
     * @param backupId ID резервной копии в формате UUID (required)
     * @param updateS3Backup  (optional)
     * @return ApiResponse&lt;CreateDatabaseS3Backup201Response&gt;
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> Ответ будет представлять собой объект JSON с ключом &#x60;backup&#x60;. </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Некорректный запрос </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Не авторизован </td><td>  -  </td></tr>
        <tr><td> 403 </td><td> Запрещено </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Не найдено </td><td>  -  </td></tr>
        <tr><td> 409 </td><td> Конфликт </td><td>  -  </td></tr>
        <tr><td> 429 </td><td> Слишком много запросов </td><td>  -  </td></tr>
        <tr><td> 500 </td><td> Внутренняя ошибка сервера </td><td>  -  </td></tr>
     </table>
     */
    public ApiResponse<CreateDatabaseS3Backup201Response> updateDatabaseS3BackupWithHttpInfo(Integer dbId, UUID backupId, UpdateS3Backup updateS3Backup) throws ApiException {
        okhttp3.Call localVarCall = updateDatabaseS3BackupValidateBeforeCall(dbId, backupId, updateS3Backup, null);
        Type localVarReturnType = new TypeToken<CreateDatabaseS3Backup201Response>(){}.getType();
        return localVarApiClient.execute(localVarCall, localVarReturnType);
    }

    /**
     * Изменение комментария S3-бэкапа базы данных (asynchronously)
     * Чтобы изменить комментарий к резервной копии кластера базы данных, отправьте PATCH-запрос на &#x60;/api/v2/databases/{db_id}/backups/{backup_id}&#x60;.   Изменить можно только комментарий: других полей метод не принимает, сама резервная копия при этом не пересоздается. Тело ответа будет представлять собой объект JSON с ключом &#x60;backup&#x60;.
     * @param dbId ID базы данных (required)
     * @param backupId ID резервной копии в формате UUID (required)
     * @param updateS3Backup  (optional)
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
        <tr><td> 409 </td><td> Конфликт </td><td>  -  </td></tr>
        <tr><td> 429 </td><td> Слишком много запросов </td><td>  -  </td></tr>
        <tr><td> 500 </td><td> Внутренняя ошибка сервера </td><td>  -  </td></tr>
     </table>
     */
    public okhttp3.Call updateDatabaseS3BackupAsync(Integer dbId, UUID backupId, UpdateS3Backup updateS3Backup, final ApiCallback<CreateDatabaseS3Backup201Response> _callback) throws ApiException {

        okhttp3.Call localVarCall = updateDatabaseS3BackupValidateBeforeCall(dbId, backupId, updateS3Backup, _callback);
        Type localVarReturnType = new TypeToken<CreateDatabaseS3Backup201Response>(){}.getType();
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
