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


import org.openapitools.client.model.ConnectSkills;
import org.openapitools.client.model.CreateAgentSkill201Response;
import org.openapitools.client.model.CreateSkill;
import org.openapitools.client.model.GetAccountStatus403Response;
import org.openapitools.client.model.GetAgentSkill200Response;
import org.openapitools.client.model.GetAgentSkillCatalog200Response;
import org.openapitools.client.model.GetAgentSkillLibrary200Response;
import org.openapitools.client.model.GetAgentSkillVersion200Response;
import org.openapitools.client.model.GetAgentSkillVersions200Response;
import org.openapitools.client.model.GetAgentSkillsForAgent200Response;
import org.openapitools.client.model.GetFinances400Response;
import org.openapitools.client.model.GetFinances401Response;
import org.openapitools.client.model.GetFinances429Response;
import org.openapitools.client.model.GetFinances500Response;
import org.openapitools.client.model.GetImage404Response;
import org.openapitools.client.model.InstallAgentSkillFromCatalog200Response;
import org.openapitools.client.model.InstallAndConnectAgentSkill200Response;
import org.openapitools.client.model.InstallAndConnectSkill;
import org.openapitools.client.model.SetAgentSkill;
import org.openapitools.client.model.SetAgentSkillForAgent200Response;
import org.openapitools.client.model.SyncAgentSkillAgentsVersions200Response;
import org.openapitools.client.model.SyncAgentSkillVersionsForAgent200Response;
import org.openapitools.client.model.UpdateDatabaseInstance409Response;
import org.openapitools.client.model.UpdateSkill;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AiAgentSkillsApi {
    private ApiClient localVarApiClient;
    private int localHostIndex;
    private String localCustomBaseUrl;

    public AiAgentSkillsApi() {
        this(Configuration.getDefaultApiClient());
    }

    public AiAgentSkillsApi(ApiClient apiClient) {
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
     * Build call for applyAgentSkillCatalogUpdate
     * @param skillId ID навыка (required)
     * @param _callback Callback for upload/download progress
     * @return Call to execute
     * @throws ApiException If fail to serialize the request body object
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> Объект JSON c ключами &#x60;skill&#x60; и &#x60;meta&#x60; </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Некорректный запрос </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Не авторизован </td><td>  -  </td></tr>
        <tr><td> 403 </td><td> Запрещено </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Не найдено </td><td>  -  </td></tr>
        <tr><td> 409 </td><td> Конфликт </td><td>  -  </td></tr>
        <tr><td> 429 </td><td> Слишком много запросов </td><td>  -  </td></tr>
        <tr><td> 500 </td><td> Внутренняя ошибка сервера </td><td>  -  </td></tr>
     </table>
     */
    public okhttp3.Call applyAgentSkillCatalogUpdateCall(Integer skillId, final ApiCallback _callback) throws ApiException {
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
        String localVarPath = "/api/v1/cloud-ai/skills/{skill_id}/apply-catalog-update"
            .replace("{" + "skill_id" + "}", localVarApiClient.escapeString(skillId.toString()));

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
    private okhttp3.Call applyAgentSkillCatalogUpdateValidateBeforeCall(Integer skillId, final ApiCallback _callback) throws ApiException {
        // verify the required parameter 'skillId' is set
        if (skillId == null) {
            throw new ApiException("Missing the required parameter 'skillId' when calling applyAgentSkillCatalogUpdate(Async)");
        }

        return applyAgentSkillCatalogUpdateCall(skillId, _callback);

    }

    /**
     * Применение обновления навыка из каталога
     * Чтобы применить к установленному навыку новую версию из каталога, отправьте POST-запрос на &#x60;/api/v1/cloud-ai/skills/{skill_id}/apply-catalog-update&#x60;.  Текст «когда применять» и инструкция заменяются каталожными, при этом создаётся новая версия: прежний текст, в том числе ваши правки, остаётся в истории. Название навыка не меняется. Если обновления нет, ничего не меняется (&#x60;meta.version_created&#x60; — &#x60;false&#x60;).  Для навыка, установленного не из каталога, вернётся 409 с &#x60;error_code&#x60; &#x60;skill_is_not_installed_from_catalog&#x60;.
     * @param skillId ID навыка (required)
     * @return CreateAgentSkill201Response
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> Объект JSON c ключами &#x60;skill&#x60; и &#x60;meta&#x60; </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Некорректный запрос </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Не авторизован </td><td>  -  </td></tr>
        <tr><td> 403 </td><td> Запрещено </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Не найдено </td><td>  -  </td></tr>
        <tr><td> 409 </td><td> Конфликт </td><td>  -  </td></tr>
        <tr><td> 429 </td><td> Слишком много запросов </td><td>  -  </td></tr>
        <tr><td> 500 </td><td> Внутренняя ошибка сервера </td><td>  -  </td></tr>
     </table>
     */
    public CreateAgentSkill201Response applyAgentSkillCatalogUpdate(Integer skillId) throws ApiException {
        ApiResponse<CreateAgentSkill201Response> localVarResp = applyAgentSkillCatalogUpdateWithHttpInfo(skillId);
        return localVarResp.getData();
    }

    /**
     * Применение обновления навыка из каталога
     * Чтобы применить к установленному навыку новую версию из каталога, отправьте POST-запрос на &#x60;/api/v1/cloud-ai/skills/{skill_id}/apply-catalog-update&#x60;.  Текст «когда применять» и инструкция заменяются каталожными, при этом создаётся новая версия: прежний текст, в том числе ваши правки, остаётся в истории. Название навыка не меняется. Если обновления нет, ничего не меняется (&#x60;meta.version_created&#x60; — &#x60;false&#x60;).  Для навыка, установленного не из каталога, вернётся 409 с &#x60;error_code&#x60; &#x60;skill_is_not_installed_from_catalog&#x60;.
     * @param skillId ID навыка (required)
     * @return ApiResponse&lt;CreateAgentSkill201Response&gt;
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> Объект JSON c ключами &#x60;skill&#x60; и &#x60;meta&#x60; </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Некорректный запрос </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Не авторизован </td><td>  -  </td></tr>
        <tr><td> 403 </td><td> Запрещено </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Не найдено </td><td>  -  </td></tr>
        <tr><td> 409 </td><td> Конфликт </td><td>  -  </td></tr>
        <tr><td> 429 </td><td> Слишком много запросов </td><td>  -  </td></tr>
        <tr><td> 500 </td><td> Внутренняя ошибка сервера </td><td>  -  </td></tr>
     </table>
     */
    public ApiResponse<CreateAgentSkill201Response> applyAgentSkillCatalogUpdateWithHttpInfo(Integer skillId) throws ApiException {
        okhttp3.Call localVarCall = applyAgentSkillCatalogUpdateValidateBeforeCall(skillId, null);
        Type localVarReturnType = new TypeToken<CreateAgentSkill201Response>(){}.getType();
        return localVarApiClient.execute(localVarCall, localVarReturnType);
    }

    /**
     * Применение обновления навыка из каталога (asynchronously)
     * Чтобы применить к установленному навыку новую версию из каталога, отправьте POST-запрос на &#x60;/api/v1/cloud-ai/skills/{skill_id}/apply-catalog-update&#x60;.  Текст «когда применять» и инструкция заменяются каталожными, при этом создаётся новая версия: прежний текст, в том числе ваши правки, остаётся в истории. Название навыка не меняется. Если обновления нет, ничего не меняется (&#x60;meta.version_created&#x60; — &#x60;false&#x60;).  Для навыка, установленного не из каталога, вернётся 409 с &#x60;error_code&#x60; &#x60;skill_is_not_installed_from_catalog&#x60;.
     * @param skillId ID навыка (required)
     * @param _callback The callback to be executed when the API call finishes
     * @return The request call
     * @throws ApiException If fail to process the API call, e.g. serializing the request body object
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> Объект JSON c ключами &#x60;skill&#x60; и &#x60;meta&#x60; </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Некорректный запрос </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Не авторизован </td><td>  -  </td></tr>
        <tr><td> 403 </td><td> Запрещено </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Не найдено </td><td>  -  </td></tr>
        <tr><td> 409 </td><td> Конфликт </td><td>  -  </td></tr>
        <tr><td> 429 </td><td> Слишком много запросов </td><td>  -  </td></tr>
        <tr><td> 500 </td><td> Внутренняя ошибка сервера </td><td>  -  </td></tr>
     </table>
     */
    public okhttp3.Call applyAgentSkillCatalogUpdateAsync(Integer skillId, final ApiCallback<CreateAgentSkill201Response> _callback) throws ApiException {

        okhttp3.Call localVarCall = applyAgentSkillCatalogUpdateValidateBeforeCall(skillId, _callback);
        Type localVarReturnType = new TypeToken<CreateAgentSkill201Response>(){}.getType();
        localVarApiClient.executeAsync(localVarCall, localVarReturnType, _callback);
        return localVarCall;
    }
    /**
     * Build call for connectAgentSkillsToAgent
     * @param agentId ID агента (required)
     * @param connectSkills  (required)
     * @param _callback Callback for upload/download progress
     * @return Call to execute
     * @throws ApiException If fail to serialize the request body object
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> Объект JSON c ключами &#x60;agent_skills&#x60; и &#x60;meta&#x60; </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Некорректный запрос </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Не авторизован </td><td>  -  </td></tr>
        <tr><td> 403 </td><td> Запрещено </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Не найдено </td><td>  -  </td></tr>
        <tr><td> 429 </td><td> Слишком много запросов </td><td>  -  </td></tr>
        <tr><td> 500 </td><td> Внутренняя ошибка сервера </td><td>  -  </td></tr>
     </table>
     */
    public okhttp3.Call connectAgentSkillsToAgentCall(Integer agentId, ConnectSkills connectSkills, final ApiCallback _callback) throws ApiException {
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

        Object localVarPostBody = connectSkills;

        // create path and map variables
        String localVarPath = "/api/v1/cloud-ai/agents/{agent_id}/skills/connect"
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
    private okhttp3.Call connectAgentSkillsToAgentValidateBeforeCall(Integer agentId, ConnectSkills connectSkills, final ApiCallback _callback) throws ApiException {
        // verify the required parameter 'agentId' is set
        if (agentId == null) {
            throw new ApiException("Missing the required parameter 'agentId' when calling connectAgentSkillsToAgent(Async)");
        }

        // verify the required parameter 'connectSkills' is set
        if (connectSkills == null) {
            throw new ApiException("Missing the required parameter 'connectSkills' when calling connectAgentSkillsToAgent(Async)");
        }

        return connectAgentSkillsToAgentCall(agentId, connectSkills, _callback);

    }

    /**
     * Подключение нескольких навыков к агенту
     * Чтобы подключить и сразу включить у агента несколько навыков, отправьте POST-запрос на &#x60;/api/v1/cloud-ai/agents/{agent_id}/skills/connect&#x60;.  Навыки, которых нет в аккаунте, пропускаются без ошибки. Уже подключённые навыки включаются; если для них передана версия, агент закрепляется на ней. В ответе — только навыки, которые удалось подключить.
     * @param agentId ID агента (required)
     * @param connectSkills  (required)
     * @return GetAgentSkillsForAgent200Response
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> Объект JSON c ключами &#x60;agent_skills&#x60; и &#x60;meta&#x60; </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Некорректный запрос </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Не авторизован </td><td>  -  </td></tr>
        <tr><td> 403 </td><td> Запрещено </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Не найдено </td><td>  -  </td></tr>
        <tr><td> 429 </td><td> Слишком много запросов </td><td>  -  </td></tr>
        <tr><td> 500 </td><td> Внутренняя ошибка сервера </td><td>  -  </td></tr>
     </table>
     */
    public GetAgentSkillsForAgent200Response connectAgentSkillsToAgent(Integer agentId, ConnectSkills connectSkills) throws ApiException {
        ApiResponse<GetAgentSkillsForAgent200Response> localVarResp = connectAgentSkillsToAgentWithHttpInfo(agentId, connectSkills);
        return localVarResp.getData();
    }

    /**
     * Подключение нескольких навыков к агенту
     * Чтобы подключить и сразу включить у агента несколько навыков, отправьте POST-запрос на &#x60;/api/v1/cloud-ai/agents/{agent_id}/skills/connect&#x60;.  Навыки, которых нет в аккаунте, пропускаются без ошибки. Уже подключённые навыки включаются; если для них передана версия, агент закрепляется на ней. В ответе — только навыки, которые удалось подключить.
     * @param agentId ID агента (required)
     * @param connectSkills  (required)
     * @return ApiResponse&lt;GetAgentSkillsForAgent200Response&gt;
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> Объект JSON c ключами &#x60;agent_skills&#x60; и &#x60;meta&#x60; </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Некорректный запрос </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Не авторизован </td><td>  -  </td></tr>
        <tr><td> 403 </td><td> Запрещено </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Не найдено </td><td>  -  </td></tr>
        <tr><td> 429 </td><td> Слишком много запросов </td><td>  -  </td></tr>
        <tr><td> 500 </td><td> Внутренняя ошибка сервера </td><td>  -  </td></tr>
     </table>
     */
    public ApiResponse<GetAgentSkillsForAgent200Response> connectAgentSkillsToAgentWithHttpInfo(Integer agentId, ConnectSkills connectSkills) throws ApiException {
        okhttp3.Call localVarCall = connectAgentSkillsToAgentValidateBeforeCall(agentId, connectSkills, null);
        Type localVarReturnType = new TypeToken<GetAgentSkillsForAgent200Response>(){}.getType();
        return localVarApiClient.execute(localVarCall, localVarReturnType);
    }

    /**
     * Подключение нескольких навыков к агенту (asynchronously)
     * Чтобы подключить и сразу включить у агента несколько навыков, отправьте POST-запрос на &#x60;/api/v1/cloud-ai/agents/{agent_id}/skills/connect&#x60;.  Навыки, которых нет в аккаунте, пропускаются без ошибки. Уже подключённые навыки включаются; если для них передана версия, агент закрепляется на ней. В ответе — только навыки, которые удалось подключить.
     * @param agentId ID агента (required)
     * @param connectSkills  (required)
     * @param _callback The callback to be executed when the API call finishes
     * @return The request call
     * @throws ApiException If fail to process the API call, e.g. serializing the request body object
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> Объект JSON c ключами &#x60;agent_skills&#x60; и &#x60;meta&#x60; </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Некорректный запрос </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Не авторизован </td><td>  -  </td></tr>
        <tr><td> 403 </td><td> Запрещено </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Не найдено </td><td>  -  </td></tr>
        <tr><td> 429 </td><td> Слишком много запросов </td><td>  -  </td></tr>
        <tr><td> 500 </td><td> Внутренняя ошибка сервера </td><td>  -  </td></tr>
     </table>
     */
    public okhttp3.Call connectAgentSkillsToAgentAsync(Integer agentId, ConnectSkills connectSkills, final ApiCallback<GetAgentSkillsForAgent200Response> _callback) throws ApiException {

        okhttp3.Call localVarCall = connectAgentSkillsToAgentValidateBeforeCall(agentId, connectSkills, _callback);
        Type localVarReturnType = new TypeToken<GetAgentSkillsForAgent200Response>(){}.getType();
        localVarApiClient.executeAsync(localVarCall, localVarReturnType, _callback);
        return localVarCall;
    }
    /**
     * Build call for createAgentSkill
     * @param createSkill  (required)
     * @param _callback Callback for upload/download progress
     * @return Call to execute
     * @throws ApiException If fail to serialize the request body object
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 201 </td><td> Объект JSON c ключами &#x60;skill&#x60; и &#x60;meta&#x60; </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Некорректный запрос </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Не авторизован </td><td>  -  </td></tr>
        <tr><td> 403 </td><td> Запрещено </td><td>  -  </td></tr>
        <tr><td> 409 </td><td> Конфликт </td><td>  -  </td></tr>
        <tr><td> 429 </td><td> Слишком много запросов </td><td>  -  </td></tr>
        <tr><td> 500 </td><td> Внутренняя ошибка сервера </td><td>  -  </td></tr>
     </table>
     */
    public okhttp3.Call createAgentSkillCall(CreateSkill createSkill, final ApiCallback _callback) throws ApiException {
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

        Object localVarPostBody = createSkill;

        // create path and map variables
        String localVarPath = "/api/v1/cloud-ai/skills";

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
    private okhttp3.Call createAgentSkillValidateBeforeCall(CreateSkill createSkill, final ApiCallback _callback) throws ApiException {
        // verify the required parameter 'createSkill' is set
        if (createSkill == null) {
            throw new ApiException("Missing the required parameter 'createSkill' when calling createAgentSkill(Async)");
        }

        return createAgentSkillCall(createSkill, _callback);

    }

    /**
     * Создание навыка
     * Чтобы создать навык, отправьте POST-запрос на &#x60;/api/v1/cloud-ai/skills&#x60;.  Навык создаётся с версией 1 и ни к одному агенту не подключается. Предупреждения о качестве текста приходят в &#x60;meta.warnings&#x60; и не мешают сохранению. Скрипты передаются в теле запроса. Их общий размер — до 10 МБ, а всё тело запроса — до 16 МБ; запрос с телом больше отклоняется до проверки.  Если навык с таким названием уже есть, вернётся 409 с &#x60;error_code&#x60; &#x60;skill_name_already_exists&#x60;. Если скрипты не прошли проверку (больше 20 файлов, повторяющиеся имена, общий размер больше 10 МБ, файл без содержимого), вернётся 400 с &#x60;error_code&#x60; &#x60;skill_scripts_invalid&#x60;.
     * @param createSkill  (required)
     * @return CreateAgentSkill201Response
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 201 </td><td> Объект JSON c ключами &#x60;skill&#x60; и &#x60;meta&#x60; </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Некорректный запрос </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Не авторизован </td><td>  -  </td></tr>
        <tr><td> 403 </td><td> Запрещено </td><td>  -  </td></tr>
        <tr><td> 409 </td><td> Конфликт </td><td>  -  </td></tr>
        <tr><td> 429 </td><td> Слишком много запросов </td><td>  -  </td></tr>
        <tr><td> 500 </td><td> Внутренняя ошибка сервера </td><td>  -  </td></tr>
     </table>
     */
    public CreateAgentSkill201Response createAgentSkill(CreateSkill createSkill) throws ApiException {
        ApiResponse<CreateAgentSkill201Response> localVarResp = createAgentSkillWithHttpInfo(createSkill);
        return localVarResp.getData();
    }

    /**
     * Создание навыка
     * Чтобы создать навык, отправьте POST-запрос на &#x60;/api/v1/cloud-ai/skills&#x60;.  Навык создаётся с версией 1 и ни к одному агенту не подключается. Предупреждения о качестве текста приходят в &#x60;meta.warnings&#x60; и не мешают сохранению. Скрипты передаются в теле запроса. Их общий размер — до 10 МБ, а всё тело запроса — до 16 МБ; запрос с телом больше отклоняется до проверки.  Если навык с таким названием уже есть, вернётся 409 с &#x60;error_code&#x60; &#x60;skill_name_already_exists&#x60;. Если скрипты не прошли проверку (больше 20 файлов, повторяющиеся имена, общий размер больше 10 МБ, файл без содержимого), вернётся 400 с &#x60;error_code&#x60; &#x60;skill_scripts_invalid&#x60;.
     * @param createSkill  (required)
     * @return ApiResponse&lt;CreateAgentSkill201Response&gt;
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 201 </td><td> Объект JSON c ключами &#x60;skill&#x60; и &#x60;meta&#x60; </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Некорректный запрос </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Не авторизован </td><td>  -  </td></tr>
        <tr><td> 403 </td><td> Запрещено </td><td>  -  </td></tr>
        <tr><td> 409 </td><td> Конфликт </td><td>  -  </td></tr>
        <tr><td> 429 </td><td> Слишком много запросов </td><td>  -  </td></tr>
        <tr><td> 500 </td><td> Внутренняя ошибка сервера </td><td>  -  </td></tr>
     </table>
     */
    public ApiResponse<CreateAgentSkill201Response> createAgentSkillWithHttpInfo(CreateSkill createSkill) throws ApiException {
        okhttp3.Call localVarCall = createAgentSkillValidateBeforeCall(createSkill, null);
        Type localVarReturnType = new TypeToken<CreateAgentSkill201Response>(){}.getType();
        return localVarApiClient.execute(localVarCall, localVarReturnType);
    }

    /**
     * Создание навыка (asynchronously)
     * Чтобы создать навык, отправьте POST-запрос на &#x60;/api/v1/cloud-ai/skills&#x60;.  Навык создаётся с версией 1 и ни к одному агенту не подключается. Предупреждения о качестве текста приходят в &#x60;meta.warnings&#x60; и не мешают сохранению. Скрипты передаются в теле запроса. Их общий размер — до 10 МБ, а всё тело запроса — до 16 МБ; запрос с телом больше отклоняется до проверки.  Если навык с таким названием уже есть, вернётся 409 с &#x60;error_code&#x60; &#x60;skill_name_already_exists&#x60;. Если скрипты не прошли проверку (больше 20 файлов, повторяющиеся имена, общий размер больше 10 МБ, файл без содержимого), вернётся 400 с &#x60;error_code&#x60; &#x60;skill_scripts_invalid&#x60;.
     * @param createSkill  (required)
     * @param _callback The callback to be executed when the API call finishes
     * @return The request call
     * @throws ApiException If fail to process the API call, e.g. serializing the request body object
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 201 </td><td> Объект JSON c ключами &#x60;skill&#x60; и &#x60;meta&#x60; </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Некорректный запрос </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Не авторизован </td><td>  -  </td></tr>
        <tr><td> 403 </td><td> Запрещено </td><td>  -  </td></tr>
        <tr><td> 409 </td><td> Конфликт </td><td>  -  </td></tr>
        <tr><td> 429 </td><td> Слишком много запросов </td><td>  -  </td></tr>
        <tr><td> 500 </td><td> Внутренняя ошибка сервера </td><td>  -  </td></tr>
     </table>
     */
    public okhttp3.Call createAgentSkillAsync(CreateSkill createSkill, final ApiCallback<CreateAgentSkill201Response> _callback) throws ApiException {

        okhttp3.Call localVarCall = createAgentSkillValidateBeforeCall(createSkill, _callback);
        Type localVarReturnType = new TypeToken<CreateAgentSkill201Response>(){}.getType();
        localVarApiClient.executeAsync(localVarCall, localVarReturnType, _callback);
        return localVarCall;
    }
    /**
     * Build call for deleteAgentSkill
     * @param skillId ID навыка (required)
     * @param _callback Callback for upload/download progress
     * @return Call to execute
     * @throws ApiException If fail to serialize the request body object
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 204 </td><td> Навык удалён </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Некорректный запрос </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Не авторизован </td><td>  -  </td></tr>
        <tr><td> 403 </td><td> Запрещено </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Не найдено </td><td>  -  </td></tr>
        <tr><td> 429 </td><td> Слишком много запросов </td><td>  -  </td></tr>
        <tr><td> 500 </td><td> Внутренняя ошибка сервера </td><td>  -  </td></tr>
     </table>
     */
    public okhttp3.Call deleteAgentSkillCall(Integer skillId, final ApiCallback _callback) throws ApiException {
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
        String localVarPath = "/api/v1/cloud-ai/skills/{skill_id}"
            .replace("{" + "skill_id" + "}", localVarApiClient.escapeString(skillId.toString()));

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
    private okhttp3.Call deleteAgentSkillValidateBeforeCall(Integer skillId, final ApiCallback _callback) throws ApiException {
        // verify the required parameter 'skillId' is set
        if (skillId == null) {
            throw new ApiException("Missing the required parameter 'skillId' when calling deleteAgentSkill(Async)");
        }

        return deleteAgentSkillCall(skillId, _callback);

    }

    /**
     * Удаление навыка
     * Чтобы удалить навык, отправьте DELETE-запрос на &#x60;/api/v1/cloud-ai/skills/{skill_id}&#x60;.  Навык отключается от всех агентов, история версий и скрипты удаляются. Срабатывания навыка под старыми ответами агентов сохраняются.
     * @param skillId ID навыка (required)
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 204 </td><td> Навык удалён </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Некорректный запрос </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Не авторизован </td><td>  -  </td></tr>
        <tr><td> 403 </td><td> Запрещено </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Не найдено </td><td>  -  </td></tr>
        <tr><td> 429 </td><td> Слишком много запросов </td><td>  -  </td></tr>
        <tr><td> 500 </td><td> Внутренняя ошибка сервера </td><td>  -  </td></tr>
     </table>
     */
    public void deleteAgentSkill(Integer skillId) throws ApiException {
        deleteAgentSkillWithHttpInfo(skillId);
    }

    /**
     * Удаление навыка
     * Чтобы удалить навык, отправьте DELETE-запрос на &#x60;/api/v1/cloud-ai/skills/{skill_id}&#x60;.  Навык отключается от всех агентов, история версий и скрипты удаляются. Срабатывания навыка под старыми ответами агентов сохраняются.
     * @param skillId ID навыка (required)
     * @return ApiResponse&lt;Void&gt;
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 204 </td><td> Навык удалён </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Некорректный запрос </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Не авторизован </td><td>  -  </td></tr>
        <tr><td> 403 </td><td> Запрещено </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Не найдено </td><td>  -  </td></tr>
        <tr><td> 429 </td><td> Слишком много запросов </td><td>  -  </td></tr>
        <tr><td> 500 </td><td> Внутренняя ошибка сервера </td><td>  -  </td></tr>
     </table>
     */
    public ApiResponse<Void> deleteAgentSkillWithHttpInfo(Integer skillId) throws ApiException {
        okhttp3.Call localVarCall = deleteAgentSkillValidateBeforeCall(skillId, null);
        return localVarApiClient.execute(localVarCall);
    }

    /**
     * Удаление навыка (asynchronously)
     * Чтобы удалить навык, отправьте DELETE-запрос на &#x60;/api/v1/cloud-ai/skills/{skill_id}&#x60;.  Навык отключается от всех агентов, история версий и скрипты удаляются. Срабатывания навыка под старыми ответами агентов сохраняются.
     * @param skillId ID навыка (required)
     * @param _callback The callback to be executed when the API call finishes
     * @return The request call
     * @throws ApiException If fail to process the API call, e.g. serializing the request body object
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 204 </td><td> Навык удалён </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Некорректный запрос </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Не авторизован </td><td>  -  </td></tr>
        <tr><td> 403 </td><td> Запрещено </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Не найдено </td><td>  -  </td></tr>
        <tr><td> 429 </td><td> Слишком много запросов </td><td>  -  </td></tr>
        <tr><td> 500 </td><td> Внутренняя ошибка сервера </td><td>  -  </td></tr>
     </table>
     */
    public okhttp3.Call deleteAgentSkillAsync(Integer skillId, final ApiCallback<Void> _callback) throws ApiException {

        okhttp3.Call localVarCall = deleteAgentSkillValidateBeforeCall(skillId, _callback);
        localVarApiClient.executeAsync(localVarCall, _callback);
        return localVarCall;
    }
    /**
     * Build call for disconnectAgentSkillFromAgent
     * @param agentId ID агента (required)
     * @param skillId ID навыка (required)
     * @param _callback Callback for upload/download progress
     * @return Call to execute
     * @throws ApiException If fail to serialize the request body object
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 204 </td><td> Навык отключён от агента </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Некорректный запрос </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Не авторизован </td><td>  -  </td></tr>
        <tr><td> 403 </td><td> Запрещено </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Не найдено </td><td>  -  </td></tr>
        <tr><td> 429 </td><td> Слишком много запросов </td><td>  -  </td></tr>
        <tr><td> 500 </td><td> Внутренняя ошибка сервера </td><td>  -  </td></tr>
     </table>
     */
    public okhttp3.Call disconnectAgentSkillFromAgentCall(Integer agentId, Integer skillId, final ApiCallback _callback) throws ApiException {
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
        String localVarPath = "/api/v1/cloud-ai/agents/{agent_id}/skills/{skill_id}"
            .replace("{" + "agent_id" + "}", localVarApiClient.escapeString(agentId.toString()))
            .replace("{" + "skill_id" + "}", localVarApiClient.escapeString(skillId.toString()));

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
    private okhttp3.Call disconnectAgentSkillFromAgentValidateBeforeCall(Integer agentId, Integer skillId, final ApiCallback _callback) throws ApiException {
        // verify the required parameter 'agentId' is set
        if (agentId == null) {
            throw new ApiException("Missing the required parameter 'agentId' when calling disconnectAgentSkillFromAgent(Async)");
        }

        // verify the required parameter 'skillId' is set
        if (skillId == null) {
            throw new ApiException("Missing the required parameter 'skillId' when calling disconnectAgentSkillFromAgent(Async)");
        }

        return disconnectAgentSkillFromAgentCall(agentId, skillId, _callback);

    }

    /**
     * Отключение навыка от агента
     * Чтобы полностью отключить навык от агента, отправьте DELETE-запрос на &#x60;/api/v1/cloud-ai/agents/{agent_id}/skills/{skill_id}&#x60;.  Закреплённая версия при этом не сохраняется. Чтобы выключить навык на время, используйте PUT-запрос с &#x60;is_enabled: false&#x60;. Если навык не подключён к агенту, вернётся 404 с &#x60;error_code&#x60; &#x60;agent_skill_link_not_found&#x60;.
     * @param agentId ID агента (required)
     * @param skillId ID навыка (required)
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 204 </td><td> Навык отключён от агента </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Некорректный запрос </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Не авторизован </td><td>  -  </td></tr>
        <tr><td> 403 </td><td> Запрещено </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Не найдено </td><td>  -  </td></tr>
        <tr><td> 429 </td><td> Слишком много запросов </td><td>  -  </td></tr>
        <tr><td> 500 </td><td> Внутренняя ошибка сервера </td><td>  -  </td></tr>
     </table>
     */
    public void disconnectAgentSkillFromAgent(Integer agentId, Integer skillId) throws ApiException {
        disconnectAgentSkillFromAgentWithHttpInfo(agentId, skillId);
    }

    /**
     * Отключение навыка от агента
     * Чтобы полностью отключить навык от агента, отправьте DELETE-запрос на &#x60;/api/v1/cloud-ai/agents/{agent_id}/skills/{skill_id}&#x60;.  Закреплённая версия при этом не сохраняется. Чтобы выключить навык на время, используйте PUT-запрос с &#x60;is_enabled: false&#x60;. Если навык не подключён к агенту, вернётся 404 с &#x60;error_code&#x60; &#x60;agent_skill_link_not_found&#x60;.
     * @param agentId ID агента (required)
     * @param skillId ID навыка (required)
     * @return ApiResponse&lt;Void&gt;
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 204 </td><td> Навык отключён от агента </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Некорректный запрос </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Не авторизован </td><td>  -  </td></tr>
        <tr><td> 403 </td><td> Запрещено </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Не найдено </td><td>  -  </td></tr>
        <tr><td> 429 </td><td> Слишком много запросов </td><td>  -  </td></tr>
        <tr><td> 500 </td><td> Внутренняя ошибка сервера </td><td>  -  </td></tr>
     </table>
     */
    public ApiResponse<Void> disconnectAgentSkillFromAgentWithHttpInfo(Integer agentId, Integer skillId) throws ApiException {
        okhttp3.Call localVarCall = disconnectAgentSkillFromAgentValidateBeforeCall(agentId, skillId, null);
        return localVarApiClient.execute(localVarCall);
    }

    /**
     * Отключение навыка от агента (asynchronously)
     * Чтобы полностью отключить навык от агента, отправьте DELETE-запрос на &#x60;/api/v1/cloud-ai/agents/{agent_id}/skills/{skill_id}&#x60;.  Закреплённая версия при этом не сохраняется. Чтобы выключить навык на время, используйте PUT-запрос с &#x60;is_enabled: false&#x60;. Если навык не подключён к агенту, вернётся 404 с &#x60;error_code&#x60; &#x60;agent_skill_link_not_found&#x60;.
     * @param agentId ID агента (required)
     * @param skillId ID навыка (required)
     * @param _callback The callback to be executed when the API call finishes
     * @return The request call
     * @throws ApiException If fail to process the API call, e.g. serializing the request body object
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 204 </td><td> Навык отключён от агента </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Некорректный запрос </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Не авторизован </td><td>  -  </td></tr>
        <tr><td> 403 </td><td> Запрещено </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Не найдено </td><td>  -  </td></tr>
        <tr><td> 429 </td><td> Слишком много запросов </td><td>  -  </td></tr>
        <tr><td> 500 </td><td> Внутренняя ошибка сервера </td><td>  -  </td></tr>
     </table>
     */
    public okhttp3.Call disconnectAgentSkillFromAgentAsync(Integer agentId, Integer skillId, final ApiCallback<Void> _callback) throws ApiException {

        okhttp3.Call localVarCall = disconnectAgentSkillFromAgentValidateBeforeCall(agentId, skillId, _callback);
        localVarApiClient.executeAsync(localVarCall, _callback);
        return localVarCall;
    }
    /**
     * Build call for duplicateAgentSkill
     * @param skillId ID навыка (required)
     * @param _callback Callback for upload/download progress
     * @return Call to execute
     * @throws ApiException If fail to serialize the request body object
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 201 </td><td> Объект JSON c ключами &#x60;skill&#x60; и &#x60;meta&#x60; </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Некорректный запрос </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Не авторизован </td><td>  -  </td></tr>
        <tr><td> 403 </td><td> Запрещено </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Не найдено </td><td>  -  </td></tr>
        <tr><td> 409 </td><td> Конфликт </td><td>  -  </td></tr>
        <tr><td> 429 </td><td> Слишком много запросов </td><td>  -  </td></tr>
        <tr><td> 500 </td><td> Внутренняя ошибка сервера </td><td>  -  </td></tr>
     </table>
     */
    public okhttp3.Call duplicateAgentSkillCall(Integer skillId, final ApiCallback _callback) throws ApiException {
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
        String localVarPath = "/api/v1/cloud-ai/skills/{skill_id}/duplicate"
            .replace("{" + "skill_id" + "}", localVarApiClient.escapeString(skillId.toString()));

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
    private okhttp3.Call duplicateAgentSkillValidateBeforeCall(Integer skillId, final ApiCallback _callback) throws ApiException {
        // verify the required parameter 'skillId' is set
        if (skillId == null) {
            throw new ApiException("Missing the required parameter 'skillId' when calling duplicateAgentSkill(Async)");
        }

        return duplicateAgentSkillCall(skillId, _callback);

    }

    /**
     * Копирование навыка
     * Чтобы создать копию навыка, отправьте POST-запрос на &#x60;/api/v1/cloud-ai/skills/{skill_id}/duplicate&#x60;.  Копия получает свободное название вида «Название (копия)» или «Название (копия 2)», текст и скрипты текущей версии оригинала и собственную историю с версии 1. К агентам копия не подключается. Копия навыка из каталога становится обычным навыком аккаунта.
     * @param skillId ID навыка (required)
     * @return CreateAgentSkill201Response
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 201 </td><td> Объект JSON c ключами &#x60;skill&#x60; и &#x60;meta&#x60; </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Некорректный запрос </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Не авторизован </td><td>  -  </td></tr>
        <tr><td> 403 </td><td> Запрещено </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Не найдено </td><td>  -  </td></tr>
        <tr><td> 409 </td><td> Конфликт </td><td>  -  </td></tr>
        <tr><td> 429 </td><td> Слишком много запросов </td><td>  -  </td></tr>
        <tr><td> 500 </td><td> Внутренняя ошибка сервера </td><td>  -  </td></tr>
     </table>
     */
    public CreateAgentSkill201Response duplicateAgentSkill(Integer skillId) throws ApiException {
        ApiResponse<CreateAgentSkill201Response> localVarResp = duplicateAgentSkillWithHttpInfo(skillId);
        return localVarResp.getData();
    }

    /**
     * Копирование навыка
     * Чтобы создать копию навыка, отправьте POST-запрос на &#x60;/api/v1/cloud-ai/skills/{skill_id}/duplicate&#x60;.  Копия получает свободное название вида «Название (копия)» или «Название (копия 2)», текст и скрипты текущей версии оригинала и собственную историю с версии 1. К агентам копия не подключается. Копия навыка из каталога становится обычным навыком аккаунта.
     * @param skillId ID навыка (required)
     * @return ApiResponse&lt;CreateAgentSkill201Response&gt;
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 201 </td><td> Объект JSON c ключами &#x60;skill&#x60; и &#x60;meta&#x60; </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Некорректный запрос </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Не авторизован </td><td>  -  </td></tr>
        <tr><td> 403 </td><td> Запрещено </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Не найдено </td><td>  -  </td></tr>
        <tr><td> 409 </td><td> Конфликт </td><td>  -  </td></tr>
        <tr><td> 429 </td><td> Слишком много запросов </td><td>  -  </td></tr>
        <tr><td> 500 </td><td> Внутренняя ошибка сервера </td><td>  -  </td></tr>
     </table>
     */
    public ApiResponse<CreateAgentSkill201Response> duplicateAgentSkillWithHttpInfo(Integer skillId) throws ApiException {
        okhttp3.Call localVarCall = duplicateAgentSkillValidateBeforeCall(skillId, null);
        Type localVarReturnType = new TypeToken<CreateAgentSkill201Response>(){}.getType();
        return localVarApiClient.execute(localVarCall, localVarReturnType);
    }

    /**
     * Копирование навыка (asynchronously)
     * Чтобы создать копию навыка, отправьте POST-запрос на &#x60;/api/v1/cloud-ai/skills/{skill_id}/duplicate&#x60;.  Копия получает свободное название вида «Название (копия)» или «Название (копия 2)», текст и скрипты текущей версии оригинала и собственную историю с версии 1. К агентам копия не подключается. Копия навыка из каталога становится обычным навыком аккаунта.
     * @param skillId ID навыка (required)
     * @param _callback The callback to be executed when the API call finishes
     * @return The request call
     * @throws ApiException If fail to process the API call, e.g. serializing the request body object
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 201 </td><td> Объект JSON c ключами &#x60;skill&#x60; и &#x60;meta&#x60; </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Некорректный запрос </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Не авторизован </td><td>  -  </td></tr>
        <tr><td> 403 </td><td> Запрещено </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Не найдено </td><td>  -  </td></tr>
        <tr><td> 409 </td><td> Конфликт </td><td>  -  </td></tr>
        <tr><td> 429 </td><td> Слишком много запросов </td><td>  -  </td></tr>
        <tr><td> 500 </td><td> Внутренняя ошибка сервера </td><td>  -  </td></tr>
     </table>
     */
    public okhttp3.Call duplicateAgentSkillAsync(Integer skillId, final ApiCallback<CreateAgentSkill201Response> _callback) throws ApiException {

        okhttp3.Call localVarCall = duplicateAgentSkillValidateBeforeCall(skillId, _callback);
        Type localVarReturnType = new TypeToken<CreateAgentSkill201Response>(){}.getType();
        localVarApiClient.executeAsync(localVarCall, localVarReturnType, _callback);
        return localVarCall;
    }
    /**
     * Build call for getAgentSkill
     * @param skillId ID навыка (required)
     * @param _callback Callback for upload/download progress
     * @return Call to execute
     * @throws ApiException If fail to serialize the request body object
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> Объект JSON c ключом &#x60;skill&#x60; </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Некорректный запрос </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Не авторизован </td><td>  -  </td></tr>
        <tr><td> 403 </td><td> Запрещено </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Не найдено </td><td>  -  </td></tr>
        <tr><td> 429 </td><td> Слишком много запросов </td><td>  -  </td></tr>
        <tr><td> 500 </td><td> Внутренняя ошибка сервера </td><td>  -  </td></tr>
     </table>
     */
    public okhttp3.Call getAgentSkillCall(Integer skillId, final ApiCallback _callback) throws ApiException {
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
        String localVarPath = "/api/v1/cloud-ai/skills/{skill_id}"
            .replace("{" + "skill_id" + "}", localVarApiClient.escapeString(skillId.toString()));

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
    private okhttp3.Call getAgentSkillValidateBeforeCall(Integer skillId, final ApiCallback _callback) throws ApiException {
        // verify the required parameter 'skillId' is set
        if (skillId == null) {
            throw new ApiException("Missing the required parameter 'skillId' when calling getAgentSkill(Async)");
        }

        return getAgentSkillCall(skillId, _callback);

    }

    /**
     * Получение навыка
     * Чтобы получить карточку навыка, отправьте GET-запрос на &#x60;/api/v1/cloud-ai/skills/{skill_id}&#x60;.  В карточке есть скрипты текущей версии (без содержимого), счётчик версий и агенты, к которым подключён навык.
     * @param skillId ID навыка (required)
     * @return GetAgentSkill200Response
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> Объект JSON c ключом &#x60;skill&#x60; </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Некорректный запрос </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Не авторизован </td><td>  -  </td></tr>
        <tr><td> 403 </td><td> Запрещено </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Не найдено </td><td>  -  </td></tr>
        <tr><td> 429 </td><td> Слишком много запросов </td><td>  -  </td></tr>
        <tr><td> 500 </td><td> Внутренняя ошибка сервера </td><td>  -  </td></tr>
     </table>
     */
    public GetAgentSkill200Response getAgentSkill(Integer skillId) throws ApiException {
        ApiResponse<GetAgentSkill200Response> localVarResp = getAgentSkillWithHttpInfo(skillId);
        return localVarResp.getData();
    }

    /**
     * Получение навыка
     * Чтобы получить карточку навыка, отправьте GET-запрос на &#x60;/api/v1/cloud-ai/skills/{skill_id}&#x60;.  В карточке есть скрипты текущей версии (без содержимого), счётчик версий и агенты, к которым подключён навык.
     * @param skillId ID навыка (required)
     * @return ApiResponse&lt;GetAgentSkill200Response&gt;
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> Объект JSON c ключом &#x60;skill&#x60; </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Некорректный запрос </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Не авторизован </td><td>  -  </td></tr>
        <tr><td> 403 </td><td> Запрещено </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Не найдено </td><td>  -  </td></tr>
        <tr><td> 429 </td><td> Слишком много запросов </td><td>  -  </td></tr>
        <tr><td> 500 </td><td> Внутренняя ошибка сервера </td><td>  -  </td></tr>
     </table>
     */
    public ApiResponse<GetAgentSkill200Response> getAgentSkillWithHttpInfo(Integer skillId) throws ApiException {
        okhttp3.Call localVarCall = getAgentSkillValidateBeforeCall(skillId, null);
        Type localVarReturnType = new TypeToken<GetAgentSkill200Response>(){}.getType();
        return localVarApiClient.execute(localVarCall, localVarReturnType);
    }

    /**
     * Получение навыка (asynchronously)
     * Чтобы получить карточку навыка, отправьте GET-запрос на &#x60;/api/v1/cloud-ai/skills/{skill_id}&#x60;.  В карточке есть скрипты текущей версии (без содержимого), счётчик версий и агенты, к которым подключён навык.
     * @param skillId ID навыка (required)
     * @param _callback The callback to be executed when the API call finishes
     * @return The request call
     * @throws ApiException If fail to process the API call, e.g. serializing the request body object
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> Объект JSON c ключом &#x60;skill&#x60; </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Некорректный запрос </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Не авторизован </td><td>  -  </td></tr>
        <tr><td> 403 </td><td> Запрещено </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Не найдено </td><td>  -  </td></tr>
        <tr><td> 429 </td><td> Слишком много запросов </td><td>  -  </td></tr>
        <tr><td> 500 </td><td> Внутренняя ошибка сервера </td><td>  -  </td></tr>
     </table>
     */
    public okhttp3.Call getAgentSkillAsync(Integer skillId, final ApiCallback<GetAgentSkill200Response> _callback) throws ApiException {

        okhttp3.Call localVarCall = getAgentSkillValidateBeforeCall(skillId, _callback);
        Type localVarReturnType = new TypeToken<GetAgentSkill200Response>(){}.getType();
        localVarApiClient.executeAsync(localVarCall, localVarReturnType, _callback);
        return localVarCall;
    }
    /**
     * Build call for getAgentSkillCatalog
     * @param _callback Callback for upload/download progress
     * @return Call to execute
     * @throws ApiException If fail to serialize the request body object
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> Объект JSON c ключами &#x60;skill_catalog_items&#x60; и &#x60;meta&#x60; </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Некорректный запрос </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Не авторизован </td><td>  -  </td></tr>
        <tr><td> 403 </td><td> Запрещено </td><td>  -  </td></tr>
        <tr><td> 429 </td><td> Слишком много запросов </td><td>  -  </td></tr>
        <tr><td> 500 </td><td> Внутренняя ошибка сервера </td><td>  -  </td></tr>
     </table>
     */
    public okhttp3.Call getAgentSkillCatalogCall(final ApiCallback _callback) throws ApiException {
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
        String localVarPath = "/api/v1/cloud-ai/skill-catalog";

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
    private okhttp3.Call getAgentSkillCatalogValidateBeforeCall(final ApiCallback _callback) throws ApiException {
        return getAgentSkillCatalogCall(_callback);

    }

    /**
     * Получение каталога навыков
     * Чтобы получить каталог готовых навыков Timeweb Cloud, отправьте GET-запрос на &#x60;/api/v1/cloud-ai/skill-catalog&#x60;.  Навыки приходят в порядке показа, сгруппированные по &#x60;category&#x60;. Инструкция возвращается полностью, чтобы её можно было прочитать до установки. Для установленных навыков заполнены &#x60;is_installed&#x60;, &#x60;installed_skill_id&#x60;, &#x60;is_modified&#x60; и &#x60;has_catalog_update&#x60;.
     * @return GetAgentSkillCatalog200Response
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> Объект JSON c ключами &#x60;skill_catalog_items&#x60; и &#x60;meta&#x60; </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Некорректный запрос </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Не авторизован </td><td>  -  </td></tr>
        <tr><td> 403 </td><td> Запрещено </td><td>  -  </td></tr>
        <tr><td> 429 </td><td> Слишком много запросов </td><td>  -  </td></tr>
        <tr><td> 500 </td><td> Внутренняя ошибка сервера </td><td>  -  </td></tr>
     </table>
     */
    public GetAgentSkillCatalog200Response getAgentSkillCatalog() throws ApiException {
        ApiResponse<GetAgentSkillCatalog200Response> localVarResp = getAgentSkillCatalogWithHttpInfo();
        return localVarResp.getData();
    }

    /**
     * Получение каталога навыков
     * Чтобы получить каталог готовых навыков Timeweb Cloud, отправьте GET-запрос на &#x60;/api/v1/cloud-ai/skill-catalog&#x60;.  Навыки приходят в порядке показа, сгруппированные по &#x60;category&#x60;. Инструкция возвращается полностью, чтобы её можно было прочитать до установки. Для установленных навыков заполнены &#x60;is_installed&#x60;, &#x60;installed_skill_id&#x60;, &#x60;is_modified&#x60; и &#x60;has_catalog_update&#x60;.
     * @return ApiResponse&lt;GetAgentSkillCatalog200Response&gt;
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> Объект JSON c ключами &#x60;skill_catalog_items&#x60; и &#x60;meta&#x60; </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Некорректный запрос </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Не авторизован </td><td>  -  </td></tr>
        <tr><td> 403 </td><td> Запрещено </td><td>  -  </td></tr>
        <tr><td> 429 </td><td> Слишком много запросов </td><td>  -  </td></tr>
        <tr><td> 500 </td><td> Внутренняя ошибка сервера </td><td>  -  </td></tr>
     </table>
     */
    public ApiResponse<GetAgentSkillCatalog200Response> getAgentSkillCatalogWithHttpInfo() throws ApiException {
        okhttp3.Call localVarCall = getAgentSkillCatalogValidateBeforeCall(null);
        Type localVarReturnType = new TypeToken<GetAgentSkillCatalog200Response>(){}.getType();
        return localVarApiClient.execute(localVarCall, localVarReturnType);
    }

    /**
     * Получение каталога навыков (asynchronously)
     * Чтобы получить каталог готовых навыков Timeweb Cloud, отправьте GET-запрос на &#x60;/api/v1/cloud-ai/skill-catalog&#x60;.  Навыки приходят в порядке показа, сгруппированные по &#x60;category&#x60;. Инструкция возвращается полностью, чтобы её можно было прочитать до установки. Для установленных навыков заполнены &#x60;is_installed&#x60;, &#x60;installed_skill_id&#x60;, &#x60;is_modified&#x60; и &#x60;has_catalog_update&#x60;.
     * @param _callback The callback to be executed when the API call finishes
     * @return The request call
     * @throws ApiException If fail to process the API call, e.g. serializing the request body object
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> Объект JSON c ключами &#x60;skill_catalog_items&#x60; и &#x60;meta&#x60; </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Некорректный запрос </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Не авторизован </td><td>  -  </td></tr>
        <tr><td> 403 </td><td> Запрещено </td><td>  -  </td></tr>
        <tr><td> 429 </td><td> Слишком много запросов </td><td>  -  </td></tr>
        <tr><td> 500 </td><td> Внутренняя ошибка сервера </td><td>  -  </td></tr>
     </table>
     */
    public okhttp3.Call getAgentSkillCatalogAsync(final ApiCallback<GetAgentSkillCatalog200Response> _callback) throws ApiException {

        okhttp3.Call localVarCall = getAgentSkillCatalogValidateBeforeCall(_callback);
        Type localVarReturnType = new TypeToken<GetAgentSkillCatalog200Response>(){}.getType();
        localVarApiClient.executeAsync(localVarCall, localVarReturnType, _callback);
        return localVarCall;
    }
    /**
     * Build call for getAgentSkillLibrary
     * @param search Поиск по названию навыка и по тексту инструкции (optional)
     * @param _callback Callback for upload/download progress
     * @return Call to execute
     * @throws ApiException If fail to serialize the request body object
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> Объект JSON c ключами &#x60;skills&#x60; и &#x60;meta&#x60; </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Некорректный запрос </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Не авторизован </td><td>  -  </td></tr>
        <tr><td> 403 </td><td> Запрещено </td><td>  -  </td></tr>
        <tr><td> 429 </td><td> Слишком много запросов </td><td>  -  </td></tr>
        <tr><td> 500 </td><td> Внутренняя ошибка сервера </td><td>  -  </td></tr>
     </table>
     */
    public okhttp3.Call getAgentSkillLibraryCall(String search, final ApiCallback _callback) throws ApiException {
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
        String localVarPath = "/api/v1/cloud-ai/skills";

        List<Pair> localVarQueryParams = new ArrayList<Pair>();
        List<Pair> localVarCollectionQueryParams = new ArrayList<Pair>();
        Map<String, String> localVarHeaderParams = new HashMap<String, String>();
        Map<String, String> localVarCookieParams = new HashMap<String, String>();
        Map<String, Object> localVarFormParams = new HashMap<String, Object>();

        if (search != null) {
            localVarQueryParams.addAll(localVarApiClient.parameterToPair("search", search));
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
    private okhttp3.Call getAgentSkillLibraryValidateBeforeCall(String search, final ApiCallback _callback) throws ApiException {
        return getAgentSkillLibraryCall(search, _callback);

    }

    /**
     * Получение списка навыков
     * Чтобы получить навыки аккаунта, отправьте GET-запрос на &#x60;/api/v1/cloud-ai/skills&#x60;.  Сначала идут недавно изменённые. Параметр &#x60;search&#x60; ищет по названию и по тексту инструкции.
     * @param search Поиск по названию навыка и по тексту инструкции (optional)
     * @return GetAgentSkillLibrary200Response
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> Объект JSON c ключами &#x60;skills&#x60; и &#x60;meta&#x60; </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Некорректный запрос </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Не авторизован </td><td>  -  </td></tr>
        <tr><td> 403 </td><td> Запрещено </td><td>  -  </td></tr>
        <tr><td> 429 </td><td> Слишком много запросов </td><td>  -  </td></tr>
        <tr><td> 500 </td><td> Внутренняя ошибка сервера </td><td>  -  </td></tr>
     </table>
     */
    public GetAgentSkillLibrary200Response getAgentSkillLibrary(String search) throws ApiException {
        ApiResponse<GetAgentSkillLibrary200Response> localVarResp = getAgentSkillLibraryWithHttpInfo(search);
        return localVarResp.getData();
    }

    /**
     * Получение списка навыков
     * Чтобы получить навыки аккаунта, отправьте GET-запрос на &#x60;/api/v1/cloud-ai/skills&#x60;.  Сначала идут недавно изменённые. Параметр &#x60;search&#x60; ищет по названию и по тексту инструкции.
     * @param search Поиск по названию навыка и по тексту инструкции (optional)
     * @return ApiResponse&lt;GetAgentSkillLibrary200Response&gt;
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> Объект JSON c ключами &#x60;skills&#x60; и &#x60;meta&#x60; </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Некорректный запрос </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Не авторизован </td><td>  -  </td></tr>
        <tr><td> 403 </td><td> Запрещено </td><td>  -  </td></tr>
        <tr><td> 429 </td><td> Слишком много запросов </td><td>  -  </td></tr>
        <tr><td> 500 </td><td> Внутренняя ошибка сервера </td><td>  -  </td></tr>
     </table>
     */
    public ApiResponse<GetAgentSkillLibrary200Response> getAgentSkillLibraryWithHttpInfo(String search) throws ApiException {
        okhttp3.Call localVarCall = getAgentSkillLibraryValidateBeforeCall(search, null);
        Type localVarReturnType = new TypeToken<GetAgentSkillLibrary200Response>(){}.getType();
        return localVarApiClient.execute(localVarCall, localVarReturnType);
    }

    /**
     * Получение списка навыков (asynchronously)
     * Чтобы получить навыки аккаунта, отправьте GET-запрос на &#x60;/api/v1/cloud-ai/skills&#x60;.  Сначала идут недавно изменённые. Параметр &#x60;search&#x60; ищет по названию и по тексту инструкции.
     * @param search Поиск по названию навыка и по тексту инструкции (optional)
     * @param _callback The callback to be executed when the API call finishes
     * @return The request call
     * @throws ApiException If fail to process the API call, e.g. serializing the request body object
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> Объект JSON c ключами &#x60;skills&#x60; и &#x60;meta&#x60; </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Некорректный запрос </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Не авторизован </td><td>  -  </td></tr>
        <tr><td> 403 </td><td> Запрещено </td><td>  -  </td></tr>
        <tr><td> 429 </td><td> Слишком много запросов </td><td>  -  </td></tr>
        <tr><td> 500 </td><td> Внутренняя ошибка сервера </td><td>  -  </td></tr>
     </table>
     */
    public okhttp3.Call getAgentSkillLibraryAsync(String search, final ApiCallback<GetAgentSkillLibrary200Response> _callback) throws ApiException {

        okhttp3.Call localVarCall = getAgentSkillLibraryValidateBeforeCall(search, _callback);
        Type localVarReturnType = new TypeToken<GetAgentSkillLibrary200Response>(){}.getType();
        localVarApiClient.executeAsync(localVarCall, localVarReturnType, _callback);
        return localVarCall;
    }
    /**
     * Build call for getAgentSkillVersion
     * @param skillId ID навыка (required)
     * @param version Номер версии навыка (required)
     * @param _callback Callback for upload/download progress
     * @return Call to execute
     * @throws ApiException If fail to serialize the request body object
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> Объект JSON c ключом &#x60;skill_version&#x60; </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Некорректный запрос </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Не авторизован </td><td>  -  </td></tr>
        <tr><td> 403 </td><td> Запрещено </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Не найдено </td><td>  -  </td></tr>
        <tr><td> 429 </td><td> Слишком много запросов </td><td>  -  </td></tr>
        <tr><td> 500 </td><td> Внутренняя ошибка сервера </td><td>  -  </td></tr>
     </table>
     */
    public okhttp3.Call getAgentSkillVersionCall(Integer skillId, Integer version, final ApiCallback _callback) throws ApiException {
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
        String localVarPath = "/api/v1/cloud-ai/skills/{skill_id}/versions/{version}"
            .replace("{" + "skill_id" + "}", localVarApiClient.escapeString(skillId.toString()))
            .replace("{" + "version" + "}", localVarApiClient.escapeString(version.toString()));

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
    private okhttp3.Call getAgentSkillVersionValidateBeforeCall(Integer skillId, Integer version, final ApiCallback _callback) throws ApiException {
        // verify the required parameter 'skillId' is set
        if (skillId == null) {
            throw new ApiException("Missing the required parameter 'skillId' when calling getAgentSkillVersion(Async)");
        }

        // verify the required parameter 'version' is set
        if (version == null) {
            throw new ApiException("Missing the required parameter 'version' when calling getAgentSkillVersion(Async)");
        }

        return getAgentSkillVersionCall(skillId, version, _callback);

    }

    /**
     * Получение версии навыка
     * Чтобы получить текст конкретной версии навыка, отправьте GET-запрос на &#x60;/api/v1/cloud-ai/skills/{skill_id}/versions/{version}&#x60;.  Номер версии сработавшего навыка есть в срабатываниях под ответами агента.
     * @param skillId ID навыка (required)
     * @param version Номер версии навыка (required)
     * @return GetAgentSkillVersion200Response
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> Объект JSON c ключом &#x60;skill_version&#x60; </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Некорректный запрос </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Не авторизован </td><td>  -  </td></tr>
        <tr><td> 403 </td><td> Запрещено </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Не найдено </td><td>  -  </td></tr>
        <tr><td> 429 </td><td> Слишком много запросов </td><td>  -  </td></tr>
        <tr><td> 500 </td><td> Внутренняя ошибка сервера </td><td>  -  </td></tr>
     </table>
     */
    public GetAgentSkillVersion200Response getAgentSkillVersion(Integer skillId, Integer version) throws ApiException {
        ApiResponse<GetAgentSkillVersion200Response> localVarResp = getAgentSkillVersionWithHttpInfo(skillId, version);
        return localVarResp.getData();
    }

    /**
     * Получение версии навыка
     * Чтобы получить текст конкретной версии навыка, отправьте GET-запрос на &#x60;/api/v1/cloud-ai/skills/{skill_id}/versions/{version}&#x60;.  Номер версии сработавшего навыка есть в срабатываниях под ответами агента.
     * @param skillId ID навыка (required)
     * @param version Номер версии навыка (required)
     * @return ApiResponse&lt;GetAgentSkillVersion200Response&gt;
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> Объект JSON c ключом &#x60;skill_version&#x60; </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Некорректный запрос </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Не авторизован </td><td>  -  </td></tr>
        <tr><td> 403 </td><td> Запрещено </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Не найдено </td><td>  -  </td></tr>
        <tr><td> 429 </td><td> Слишком много запросов </td><td>  -  </td></tr>
        <tr><td> 500 </td><td> Внутренняя ошибка сервера </td><td>  -  </td></tr>
     </table>
     */
    public ApiResponse<GetAgentSkillVersion200Response> getAgentSkillVersionWithHttpInfo(Integer skillId, Integer version) throws ApiException {
        okhttp3.Call localVarCall = getAgentSkillVersionValidateBeforeCall(skillId, version, null);
        Type localVarReturnType = new TypeToken<GetAgentSkillVersion200Response>(){}.getType();
        return localVarApiClient.execute(localVarCall, localVarReturnType);
    }

    /**
     * Получение версии навыка (asynchronously)
     * Чтобы получить текст конкретной версии навыка, отправьте GET-запрос на &#x60;/api/v1/cloud-ai/skills/{skill_id}/versions/{version}&#x60;.  Номер версии сработавшего навыка есть в срабатываниях под ответами агента.
     * @param skillId ID навыка (required)
     * @param version Номер версии навыка (required)
     * @param _callback The callback to be executed when the API call finishes
     * @return The request call
     * @throws ApiException If fail to process the API call, e.g. serializing the request body object
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> Объект JSON c ключом &#x60;skill_version&#x60; </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Некорректный запрос </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Не авторизован </td><td>  -  </td></tr>
        <tr><td> 403 </td><td> Запрещено </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Не найдено </td><td>  -  </td></tr>
        <tr><td> 429 </td><td> Слишком много запросов </td><td>  -  </td></tr>
        <tr><td> 500 </td><td> Внутренняя ошибка сервера </td><td>  -  </td></tr>
     </table>
     */
    public okhttp3.Call getAgentSkillVersionAsync(Integer skillId, Integer version, final ApiCallback<GetAgentSkillVersion200Response> _callback) throws ApiException {

        okhttp3.Call localVarCall = getAgentSkillVersionValidateBeforeCall(skillId, version, _callback);
        Type localVarReturnType = new TypeToken<GetAgentSkillVersion200Response>(){}.getType();
        localVarApiClient.executeAsync(localVarCall, localVarReturnType, _callback);
        return localVarCall;
    }
    /**
     * Build call for getAgentSkillVersions
     * @param skillId ID навыка (required)
     * @param _callback Callback for upload/download progress
     * @return Call to execute
     * @throws ApiException If fail to serialize the request body object
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> Объект JSON c ключами &#x60;skill_versions&#x60; и &#x60;meta&#x60; </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Некорректный запрос </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Не авторизован </td><td>  -  </td></tr>
        <tr><td> 403 </td><td> Запрещено </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Не найдено </td><td>  -  </td></tr>
        <tr><td> 429 </td><td> Слишком много запросов </td><td>  -  </td></tr>
        <tr><td> 500 </td><td> Внутренняя ошибка сервера </td><td>  -  </td></tr>
     </table>
     */
    public okhttp3.Call getAgentSkillVersionsCall(Integer skillId, final ApiCallback _callback) throws ApiException {
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
        String localVarPath = "/api/v1/cloud-ai/skills/{skill_id}/versions"
            .replace("{" + "skill_id" + "}", localVarApiClient.escapeString(skillId.toString()));

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
    private okhttp3.Call getAgentSkillVersionsValidateBeforeCall(Integer skillId, final ApiCallback _callback) throws ApiException {
        // verify the required parameter 'skillId' is set
        if (skillId == null) {
            throw new ApiException("Missing the required parameter 'skillId' when calling getAgentSkillVersions(Async)");
        }

        return getAgentSkillVersionsCall(skillId, _callback);

    }

    /**
     * Получение истории версий навыка
     * Чтобы получить историю версий навыка, отправьте GET-запрос на &#x60;/api/v1/cloud-ai/skills/{skill_id}/versions&#x60;.
     * @param skillId ID навыка (required)
     * @return GetAgentSkillVersions200Response
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> Объект JSON c ключами &#x60;skill_versions&#x60; и &#x60;meta&#x60; </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Некорректный запрос </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Не авторизован </td><td>  -  </td></tr>
        <tr><td> 403 </td><td> Запрещено </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Не найдено </td><td>  -  </td></tr>
        <tr><td> 429 </td><td> Слишком много запросов </td><td>  -  </td></tr>
        <tr><td> 500 </td><td> Внутренняя ошибка сервера </td><td>  -  </td></tr>
     </table>
     */
    public GetAgentSkillVersions200Response getAgentSkillVersions(Integer skillId) throws ApiException {
        ApiResponse<GetAgentSkillVersions200Response> localVarResp = getAgentSkillVersionsWithHttpInfo(skillId);
        return localVarResp.getData();
    }

    /**
     * Получение истории версий навыка
     * Чтобы получить историю версий навыка, отправьте GET-запрос на &#x60;/api/v1/cloud-ai/skills/{skill_id}/versions&#x60;.
     * @param skillId ID навыка (required)
     * @return ApiResponse&lt;GetAgentSkillVersions200Response&gt;
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> Объект JSON c ключами &#x60;skill_versions&#x60; и &#x60;meta&#x60; </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Некорректный запрос </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Не авторизован </td><td>  -  </td></tr>
        <tr><td> 403 </td><td> Запрещено </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Не найдено </td><td>  -  </td></tr>
        <tr><td> 429 </td><td> Слишком много запросов </td><td>  -  </td></tr>
        <tr><td> 500 </td><td> Внутренняя ошибка сервера </td><td>  -  </td></tr>
     </table>
     */
    public ApiResponse<GetAgentSkillVersions200Response> getAgentSkillVersionsWithHttpInfo(Integer skillId) throws ApiException {
        okhttp3.Call localVarCall = getAgentSkillVersionsValidateBeforeCall(skillId, null);
        Type localVarReturnType = new TypeToken<GetAgentSkillVersions200Response>(){}.getType();
        return localVarApiClient.execute(localVarCall, localVarReturnType);
    }

    /**
     * Получение истории версий навыка (asynchronously)
     * Чтобы получить историю версий навыка, отправьте GET-запрос на &#x60;/api/v1/cloud-ai/skills/{skill_id}/versions&#x60;.
     * @param skillId ID навыка (required)
     * @param _callback The callback to be executed when the API call finishes
     * @return The request call
     * @throws ApiException If fail to process the API call, e.g. serializing the request body object
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> Объект JSON c ключами &#x60;skill_versions&#x60; и &#x60;meta&#x60; </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Некорректный запрос </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Не авторизован </td><td>  -  </td></tr>
        <tr><td> 403 </td><td> Запрещено </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Не найдено </td><td>  -  </td></tr>
        <tr><td> 429 </td><td> Слишком много запросов </td><td>  -  </td></tr>
        <tr><td> 500 </td><td> Внутренняя ошибка сервера </td><td>  -  </td></tr>
     </table>
     */
    public okhttp3.Call getAgentSkillVersionsAsync(Integer skillId, final ApiCallback<GetAgentSkillVersions200Response> _callback) throws ApiException {

        okhttp3.Call localVarCall = getAgentSkillVersionsValidateBeforeCall(skillId, _callback);
        Type localVarReturnType = new TypeToken<GetAgentSkillVersions200Response>(){}.getType();
        localVarApiClient.executeAsync(localVarCall, localVarReturnType, _callback);
        return localVarCall;
    }
    /**
     * Build call for getAgentSkillsForAgent
     * @param agentId ID агента (required)
     * @param _callback Callback for upload/download progress
     * @return Call to execute
     * @throws ApiException If fail to serialize the request body object
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> Объект JSON c ключами &#x60;agent_skills&#x60; и &#x60;meta&#x60; </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Некорректный запрос </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Не авторизован </td><td>  -  </td></tr>
        <tr><td> 403 </td><td> Запрещено </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Не найдено </td><td>  -  </td></tr>
        <tr><td> 429 </td><td> Слишком много запросов </td><td>  -  </td></tr>
        <tr><td> 500 </td><td> Внутренняя ошибка сервера </td><td>  -  </td></tr>
     </table>
     */
    public okhttp3.Call getAgentSkillsForAgentCall(Integer agentId, final ApiCallback _callback) throws ApiException {
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
        String localVarPath = "/api/v1/cloud-ai/agents/{agent_id}/skills"
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
    private okhttp3.Call getAgentSkillsForAgentValidateBeforeCall(Integer agentId, final ApiCallback _callback) throws ApiException {
        // verify the required parameter 'agentId' is set
        if (agentId == null) {
            throw new ApiException("Missing the required parameter 'agentId' when calling getAgentSkillsForAgent(Async)");
        }

        return getAgentSkillsForAgentCall(agentId, _callback);

    }

    /**
     * Получение навыков агента
     * Чтобы получить навыки агента, отправьте GET-запрос на &#x60;/api/v1/cloud-ai/agents/{agent_id}/skills&#x60;.  В ответе все навыки аккаунта: неподключённые приходят с &#x60;is_connected: false&#x60;. &#x60;has_newer_version: true&#x60; означает, что агент закреплён на устаревшей версии.
     * @param agentId ID агента (required)
     * @return GetAgentSkillsForAgent200Response
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> Объект JSON c ключами &#x60;agent_skills&#x60; и &#x60;meta&#x60; </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Некорректный запрос </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Не авторизован </td><td>  -  </td></tr>
        <tr><td> 403 </td><td> Запрещено </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Не найдено </td><td>  -  </td></tr>
        <tr><td> 429 </td><td> Слишком много запросов </td><td>  -  </td></tr>
        <tr><td> 500 </td><td> Внутренняя ошибка сервера </td><td>  -  </td></tr>
     </table>
     */
    public GetAgentSkillsForAgent200Response getAgentSkillsForAgent(Integer agentId) throws ApiException {
        ApiResponse<GetAgentSkillsForAgent200Response> localVarResp = getAgentSkillsForAgentWithHttpInfo(agentId);
        return localVarResp.getData();
    }

    /**
     * Получение навыков агента
     * Чтобы получить навыки агента, отправьте GET-запрос на &#x60;/api/v1/cloud-ai/agents/{agent_id}/skills&#x60;.  В ответе все навыки аккаунта: неподключённые приходят с &#x60;is_connected: false&#x60;. &#x60;has_newer_version: true&#x60; означает, что агент закреплён на устаревшей версии.
     * @param agentId ID агента (required)
     * @return ApiResponse&lt;GetAgentSkillsForAgent200Response&gt;
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> Объект JSON c ключами &#x60;agent_skills&#x60; и &#x60;meta&#x60; </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Некорректный запрос </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Не авторизован </td><td>  -  </td></tr>
        <tr><td> 403 </td><td> Запрещено </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Не найдено </td><td>  -  </td></tr>
        <tr><td> 429 </td><td> Слишком много запросов </td><td>  -  </td></tr>
        <tr><td> 500 </td><td> Внутренняя ошибка сервера </td><td>  -  </td></tr>
     </table>
     */
    public ApiResponse<GetAgentSkillsForAgent200Response> getAgentSkillsForAgentWithHttpInfo(Integer agentId) throws ApiException {
        okhttp3.Call localVarCall = getAgentSkillsForAgentValidateBeforeCall(agentId, null);
        Type localVarReturnType = new TypeToken<GetAgentSkillsForAgent200Response>(){}.getType();
        return localVarApiClient.execute(localVarCall, localVarReturnType);
    }

    /**
     * Получение навыков агента (asynchronously)
     * Чтобы получить навыки агента, отправьте GET-запрос на &#x60;/api/v1/cloud-ai/agents/{agent_id}/skills&#x60;.  В ответе все навыки аккаунта: неподключённые приходят с &#x60;is_connected: false&#x60;. &#x60;has_newer_version: true&#x60; означает, что агент закреплён на устаревшей версии.
     * @param agentId ID агента (required)
     * @param _callback The callback to be executed when the API call finishes
     * @return The request call
     * @throws ApiException If fail to process the API call, e.g. serializing the request body object
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> Объект JSON c ключами &#x60;agent_skills&#x60; и &#x60;meta&#x60; </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Некорректный запрос </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Не авторизован </td><td>  -  </td></tr>
        <tr><td> 403 </td><td> Запрещено </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Не найдено </td><td>  -  </td></tr>
        <tr><td> 429 </td><td> Слишком много запросов </td><td>  -  </td></tr>
        <tr><td> 500 </td><td> Внутренняя ошибка сервера </td><td>  -  </td></tr>
     </table>
     */
    public okhttp3.Call getAgentSkillsForAgentAsync(Integer agentId, final ApiCallback<GetAgentSkillsForAgent200Response> _callback) throws ApiException {

        okhttp3.Call localVarCall = getAgentSkillsForAgentValidateBeforeCall(agentId, _callback);
        Type localVarReturnType = new TypeToken<GetAgentSkillsForAgent200Response>(){}.getType();
        localVarApiClient.executeAsync(localVarCall, localVarReturnType, _callback);
        return localVarCall;
    }
    /**
     * Build call for installAgentSkillFromCatalog
     * @param catalogItemId ID навыка в каталоге (required)
     * @param _callback Callback for upload/download progress
     * @return Call to execute
     * @throws ApiException If fail to serialize the request body object
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> Объект JSON c ключами &#x60;skill&#x60; и &#x60;meta&#x60; </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Некорректный запрос </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Не авторизован </td><td>  -  </td></tr>
        <tr><td> 403 </td><td> Запрещено </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Не найдено </td><td>  -  </td></tr>
        <tr><td> 409 </td><td> Конфликт </td><td>  -  </td></tr>
        <tr><td> 429 </td><td> Слишком много запросов </td><td>  -  </td></tr>
        <tr><td> 500 </td><td> Внутренняя ошибка сервера </td><td>  -  </td></tr>
     </table>
     */
    public okhttp3.Call installAgentSkillFromCatalogCall(Integer catalogItemId, final ApiCallback _callback) throws ApiException {
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
        String localVarPath = "/api/v1/cloud-ai/skill-catalog/{catalog_item_id}/install"
            .replace("{" + "catalog_item_id" + "}", localVarApiClient.escapeString(catalogItemId.toString()));

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
    private okhttp3.Call installAgentSkillFromCatalogValidateBeforeCall(Integer catalogItemId, final ApiCallback _callback) throws ApiException {
        // verify the required parameter 'catalogItemId' is set
        if (catalogItemId == null) {
            throw new ApiException("Missing the required parameter 'catalogItemId' when calling installAgentSkillFromCatalog(Async)");
        }

        return installAgentSkillFromCatalogCall(catalogItemId, _callback);

    }

    /**
     * Установка навыка из каталога
     * Чтобы установить навык из каталога в библиотеку аккаунта, отправьте POST-запрос на &#x60;/api/v1/cloud-ai/skill-catalog/{catalog_item_id}/install&#x60;.  Повторная установка не создаёт копию, а возвращает уже установленный навык с &#x60;meta.already_installed: true&#x60;. Если название занято вашим навыком, к нему добавляется «(копия)». Чтобы сразу включить навык у агента, используйте &#x60;POST /api/v1/cloud-ai/agents/{agent_id}/skills/install-and-connect&#x60;.
     * @param catalogItemId ID навыка в каталоге (required)
     * @return InstallAgentSkillFromCatalog200Response
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> Объект JSON c ключами &#x60;skill&#x60; и &#x60;meta&#x60; </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Некорректный запрос </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Не авторизован </td><td>  -  </td></tr>
        <tr><td> 403 </td><td> Запрещено </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Не найдено </td><td>  -  </td></tr>
        <tr><td> 409 </td><td> Конфликт </td><td>  -  </td></tr>
        <tr><td> 429 </td><td> Слишком много запросов </td><td>  -  </td></tr>
        <tr><td> 500 </td><td> Внутренняя ошибка сервера </td><td>  -  </td></tr>
     </table>
     */
    public InstallAgentSkillFromCatalog200Response installAgentSkillFromCatalog(Integer catalogItemId) throws ApiException {
        ApiResponse<InstallAgentSkillFromCatalog200Response> localVarResp = installAgentSkillFromCatalogWithHttpInfo(catalogItemId);
        return localVarResp.getData();
    }

    /**
     * Установка навыка из каталога
     * Чтобы установить навык из каталога в библиотеку аккаунта, отправьте POST-запрос на &#x60;/api/v1/cloud-ai/skill-catalog/{catalog_item_id}/install&#x60;.  Повторная установка не создаёт копию, а возвращает уже установленный навык с &#x60;meta.already_installed: true&#x60;. Если название занято вашим навыком, к нему добавляется «(копия)». Чтобы сразу включить навык у агента, используйте &#x60;POST /api/v1/cloud-ai/agents/{agent_id}/skills/install-and-connect&#x60;.
     * @param catalogItemId ID навыка в каталоге (required)
     * @return ApiResponse&lt;InstallAgentSkillFromCatalog200Response&gt;
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> Объект JSON c ключами &#x60;skill&#x60; и &#x60;meta&#x60; </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Некорректный запрос </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Не авторизован </td><td>  -  </td></tr>
        <tr><td> 403 </td><td> Запрещено </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Не найдено </td><td>  -  </td></tr>
        <tr><td> 409 </td><td> Конфликт </td><td>  -  </td></tr>
        <tr><td> 429 </td><td> Слишком много запросов </td><td>  -  </td></tr>
        <tr><td> 500 </td><td> Внутренняя ошибка сервера </td><td>  -  </td></tr>
     </table>
     */
    public ApiResponse<InstallAgentSkillFromCatalog200Response> installAgentSkillFromCatalogWithHttpInfo(Integer catalogItemId) throws ApiException {
        okhttp3.Call localVarCall = installAgentSkillFromCatalogValidateBeforeCall(catalogItemId, null);
        Type localVarReturnType = new TypeToken<InstallAgentSkillFromCatalog200Response>(){}.getType();
        return localVarApiClient.execute(localVarCall, localVarReturnType);
    }

    /**
     * Установка навыка из каталога (asynchronously)
     * Чтобы установить навык из каталога в библиотеку аккаунта, отправьте POST-запрос на &#x60;/api/v1/cloud-ai/skill-catalog/{catalog_item_id}/install&#x60;.  Повторная установка не создаёт копию, а возвращает уже установленный навык с &#x60;meta.already_installed: true&#x60;. Если название занято вашим навыком, к нему добавляется «(копия)». Чтобы сразу включить навык у агента, используйте &#x60;POST /api/v1/cloud-ai/agents/{agent_id}/skills/install-and-connect&#x60;.
     * @param catalogItemId ID навыка в каталоге (required)
     * @param _callback The callback to be executed when the API call finishes
     * @return The request call
     * @throws ApiException If fail to process the API call, e.g. serializing the request body object
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> Объект JSON c ключами &#x60;skill&#x60; и &#x60;meta&#x60; </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Некорректный запрос </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Не авторизован </td><td>  -  </td></tr>
        <tr><td> 403 </td><td> Запрещено </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Не найдено </td><td>  -  </td></tr>
        <tr><td> 409 </td><td> Конфликт </td><td>  -  </td></tr>
        <tr><td> 429 </td><td> Слишком много запросов </td><td>  -  </td></tr>
        <tr><td> 500 </td><td> Внутренняя ошибка сервера </td><td>  -  </td></tr>
     </table>
     */
    public okhttp3.Call installAgentSkillFromCatalogAsync(Integer catalogItemId, final ApiCallback<InstallAgentSkillFromCatalog200Response> _callback) throws ApiException {

        okhttp3.Call localVarCall = installAgentSkillFromCatalogValidateBeforeCall(catalogItemId, _callback);
        Type localVarReturnType = new TypeToken<InstallAgentSkillFromCatalog200Response>(){}.getType();
        localVarApiClient.executeAsync(localVarCall, localVarReturnType, _callback);
        return localVarCall;
    }
    /**
     * Build call for installAndConnectAgentSkill
     * @param agentId ID агента (required)
     * @param installAndConnectSkill  (required)
     * @param _callback Callback for upload/download progress
     * @return Call to execute
     * @throws ApiException If fail to serialize the request body object
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> Объект JSON c ключами &#x60;agent_skill&#x60; и &#x60;meta&#x60; </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Некорректный запрос </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Не авторизован </td><td>  -  </td></tr>
        <tr><td> 403 </td><td> Запрещено </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Не найдено </td><td>  -  </td></tr>
        <tr><td> 409 </td><td> Конфликт </td><td>  -  </td></tr>
        <tr><td> 429 </td><td> Слишком много запросов </td><td>  -  </td></tr>
        <tr><td> 500 </td><td> Внутренняя ошибка сервера </td><td>  -  </td></tr>
     </table>
     */
    public okhttp3.Call installAndConnectAgentSkillCall(Integer agentId, InstallAndConnectSkill installAndConnectSkill, final ApiCallback _callback) throws ApiException {
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

        Object localVarPostBody = installAndConnectSkill;

        // create path and map variables
        String localVarPath = "/api/v1/cloud-ai/agents/{agent_id}/skills/install-and-connect"
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
    private okhttp3.Call installAndConnectAgentSkillValidateBeforeCall(Integer agentId, InstallAndConnectSkill installAndConnectSkill, final ApiCallback _callback) throws ApiException {
        // verify the required parameter 'agentId' is set
        if (agentId == null) {
            throw new ApiException("Missing the required parameter 'agentId' when calling installAndConnectAgentSkill(Async)");
        }

        // verify the required parameter 'installAndConnectSkill' is set
        if (installAndConnectSkill == null) {
            throw new ApiException("Missing the required parameter 'installAndConnectSkill' when calling installAndConnectAgentSkill(Async)");
        }

        return installAndConnectAgentSkillCall(agentId, installAndConnectSkill, _callback);

    }

    /**
     * Установка навыка из каталога и включение у агента
     * Чтобы установить навык из каталога и сразу включить его у агента, отправьте POST-запрос на &#x60;/api/v1/cloud-ai/agents/{agent_id}/skills/install-and-connect&#x60;.  Если навык уже установлен, копия не создаётся (&#x60;meta.already_installed: true&#x60;), навык просто включается у агента.
     * @param agentId ID агента (required)
     * @param installAndConnectSkill  (required)
     * @return InstallAndConnectAgentSkill200Response
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> Объект JSON c ключами &#x60;agent_skill&#x60; и &#x60;meta&#x60; </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Некорректный запрос </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Не авторизован </td><td>  -  </td></tr>
        <tr><td> 403 </td><td> Запрещено </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Не найдено </td><td>  -  </td></tr>
        <tr><td> 409 </td><td> Конфликт </td><td>  -  </td></tr>
        <tr><td> 429 </td><td> Слишком много запросов </td><td>  -  </td></tr>
        <tr><td> 500 </td><td> Внутренняя ошибка сервера </td><td>  -  </td></tr>
     </table>
     */
    public InstallAndConnectAgentSkill200Response installAndConnectAgentSkill(Integer agentId, InstallAndConnectSkill installAndConnectSkill) throws ApiException {
        ApiResponse<InstallAndConnectAgentSkill200Response> localVarResp = installAndConnectAgentSkillWithHttpInfo(agentId, installAndConnectSkill);
        return localVarResp.getData();
    }

    /**
     * Установка навыка из каталога и включение у агента
     * Чтобы установить навык из каталога и сразу включить его у агента, отправьте POST-запрос на &#x60;/api/v1/cloud-ai/agents/{agent_id}/skills/install-and-connect&#x60;.  Если навык уже установлен, копия не создаётся (&#x60;meta.already_installed: true&#x60;), навык просто включается у агента.
     * @param agentId ID агента (required)
     * @param installAndConnectSkill  (required)
     * @return ApiResponse&lt;InstallAndConnectAgentSkill200Response&gt;
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> Объект JSON c ключами &#x60;agent_skill&#x60; и &#x60;meta&#x60; </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Некорректный запрос </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Не авторизован </td><td>  -  </td></tr>
        <tr><td> 403 </td><td> Запрещено </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Не найдено </td><td>  -  </td></tr>
        <tr><td> 409 </td><td> Конфликт </td><td>  -  </td></tr>
        <tr><td> 429 </td><td> Слишком много запросов </td><td>  -  </td></tr>
        <tr><td> 500 </td><td> Внутренняя ошибка сервера </td><td>  -  </td></tr>
     </table>
     */
    public ApiResponse<InstallAndConnectAgentSkill200Response> installAndConnectAgentSkillWithHttpInfo(Integer agentId, InstallAndConnectSkill installAndConnectSkill) throws ApiException {
        okhttp3.Call localVarCall = installAndConnectAgentSkillValidateBeforeCall(agentId, installAndConnectSkill, null);
        Type localVarReturnType = new TypeToken<InstallAndConnectAgentSkill200Response>(){}.getType();
        return localVarApiClient.execute(localVarCall, localVarReturnType);
    }

    /**
     * Установка навыка из каталога и включение у агента (asynchronously)
     * Чтобы установить навык из каталога и сразу включить его у агента, отправьте POST-запрос на &#x60;/api/v1/cloud-ai/agents/{agent_id}/skills/install-and-connect&#x60;.  Если навык уже установлен, копия не создаётся (&#x60;meta.already_installed: true&#x60;), навык просто включается у агента.
     * @param agentId ID агента (required)
     * @param installAndConnectSkill  (required)
     * @param _callback The callback to be executed when the API call finishes
     * @return The request call
     * @throws ApiException If fail to process the API call, e.g. serializing the request body object
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> Объект JSON c ключами &#x60;agent_skill&#x60; и &#x60;meta&#x60; </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Некорректный запрос </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Не авторизован </td><td>  -  </td></tr>
        <tr><td> 403 </td><td> Запрещено </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Не найдено </td><td>  -  </td></tr>
        <tr><td> 409 </td><td> Конфликт </td><td>  -  </td></tr>
        <tr><td> 429 </td><td> Слишком много запросов </td><td>  -  </td></tr>
        <tr><td> 500 </td><td> Внутренняя ошибка сервера </td><td>  -  </td></tr>
     </table>
     */
    public okhttp3.Call installAndConnectAgentSkillAsync(Integer agentId, InstallAndConnectSkill installAndConnectSkill, final ApiCallback<InstallAndConnectAgentSkill200Response> _callback) throws ApiException {

        okhttp3.Call localVarCall = installAndConnectAgentSkillValidateBeforeCall(agentId, installAndConnectSkill, _callback);
        Type localVarReturnType = new TypeToken<InstallAndConnectAgentSkill200Response>(){}.getType();
        localVarApiClient.executeAsync(localVarCall, localVarReturnType, _callback);
        return localVarCall;
    }
    /**
     * Build call for rollbackAgentSkillVersion
     * @param skillId ID навыка (required)
     * @param version Номер версии навыка (required)
     * @param _callback Callback for upload/download progress
     * @return Call to execute
     * @throws ApiException If fail to serialize the request body object
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> Объект JSON c ключами &#x60;skill&#x60; и &#x60;meta&#x60; </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Некорректный запрос </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Не авторизован </td><td>  -  </td></tr>
        <tr><td> 403 </td><td> Запрещено </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Не найдено </td><td>  -  </td></tr>
        <tr><td> 429 </td><td> Слишком много запросов </td><td>  -  </td></tr>
        <tr><td> 500 </td><td> Внутренняя ошибка сервера </td><td>  -  </td></tr>
     </table>
     */
    public okhttp3.Call rollbackAgentSkillVersionCall(Integer skillId, Integer version, final ApiCallback _callback) throws ApiException {
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
        String localVarPath = "/api/v1/cloud-ai/skills/{skill_id}/versions/{version}/rollback"
            .replace("{" + "skill_id" + "}", localVarApiClient.escapeString(skillId.toString()))
            .replace("{" + "version" + "}", localVarApiClient.escapeString(version.toString()));

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
    private okhttp3.Call rollbackAgentSkillVersionValidateBeforeCall(Integer skillId, Integer version, final ApiCallback _callback) throws ApiException {
        // verify the required parameter 'skillId' is set
        if (skillId == null) {
            throw new ApiException("Missing the required parameter 'skillId' when calling rollbackAgentSkillVersion(Async)");
        }

        // verify the required parameter 'version' is set
        if (version == null) {
            throw new ApiException("Missing the required parameter 'version' when calling rollbackAgentSkillVersion(Async)");
        }

        return rollbackAgentSkillVersionCall(skillId, version, _callback);

    }

    /**
     * Переключение навыка на версию из истории
     * Чтобы сделать версию из истории текущей, отправьте POST-запрос на &#x60;/api/v1/cloud-ai/skills/{skill_id}/versions/{version}/rollback&#x60;.  Новая версия не создаётся (&#x60;meta.version_created&#x60; — &#x60;false&#x60;), название навыка не меняется. Агенты остаются на своих закреплённых версиях. Если выбранная версия уже текущая, ничего не меняется.
     * @param skillId ID навыка (required)
     * @param version Номер версии навыка (required)
     * @return CreateAgentSkill201Response
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> Объект JSON c ключами &#x60;skill&#x60; и &#x60;meta&#x60; </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Некорректный запрос </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Не авторизован </td><td>  -  </td></tr>
        <tr><td> 403 </td><td> Запрещено </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Не найдено </td><td>  -  </td></tr>
        <tr><td> 429 </td><td> Слишком много запросов </td><td>  -  </td></tr>
        <tr><td> 500 </td><td> Внутренняя ошибка сервера </td><td>  -  </td></tr>
     </table>
     */
    public CreateAgentSkill201Response rollbackAgentSkillVersion(Integer skillId, Integer version) throws ApiException {
        ApiResponse<CreateAgentSkill201Response> localVarResp = rollbackAgentSkillVersionWithHttpInfo(skillId, version);
        return localVarResp.getData();
    }

    /**
     * Переключение навыка на версию из истории
     * Чтобы сделать версию из истории текущей, отправьте POST-запрос на &#x60;/api/v1/cloud-ai/skills/{skill_id}/versions/{version}/rollback&#x60;.  Новая версия не создаётся (&#x60;meta.version_created&#x60; — &#x60;false&#x60;), название навыка не меняется. Агенты остаются на своих закреплённых версиях. Если выбранная версия уже текущая, ничего не меняется.
     * @param skillId ID навыка (required)
     * @param version Номер версии навыка (required)
     * @return ApiResponse&lt;CreateAgentSkill201Response&gt;
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> Объект JSON c ключами &#x60;skill&#x60; и &#x60;meta&#x60; </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Некорректный запрос </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Не авторизован </td><td>  -  </td></tr>
        <tr><td> 403 </td><td> Запрещено </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Не найдено </td><td>  -  </td></tr>
        <tr><td> 429 </td><td> Слишком много запросов </td><td>  -  </td></tr>
        <tr><td> 500 </td><td> Внутренняя ошибка сервера </td><td>  -  </td></tr>
     </table>
     */
    public ApiResponse<CreateAgentSkill201Response> rollbackAgentSkillVersionWithHttpInfo(Integer skillId, Integer version) throws ApiException {
        okhttp3.Call localVarCall = rollbackAgentSkillVersionValidateBeforeCall(skillId, version, null);
        Type localVarReturnType = new TypeToken<CreateAgentSkill201Response>(){}.getType();
        return localVarApiClient.execute(localVarCall, localVarReturnType);
    }

    /**
     * Переключение навыка на версию из истории (asynchronously)
     * Чтобы сделать версию из истории текущей, отправьте POST-запрос на &#x60;/api/v1/cloud-ai/skills/{skill_id}/versions/{version}/rollback&#x60;.  Новая версия не создаётся (&#x60;meta.version_created&#x60; — &#x60;false&#x60;), название навыка не меняется. Агенты остаются на своих закреплённых версиях. Если выбранная версия уже текущая, ничего не меняется.
     * @param skillId ID навыка (required)
     * @param version Номер версии навыка (required)
     * @param _callback The callback to be executed when the API call finishes
     * @return The request call
     * @throws ApiException If fail to process the API call, e.g. serializing the request body object
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> Объект JSON c ключами &#x60;skill&#x60; и &#x60;meta&#x60; </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Некорректный запрос </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Не авторизован </td><td>  -  </td></tr>
        <tr><td> 403 </td><td> Запрещено </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Не найдено </td><td>  -  </td></tr>
        <tr><td> 429 </td><td> Слишком много запросов </td><td>  -  </td></tr>
        <tr><td> 500 </td><td> Внутренняя ошибка сервера </td><td>  -  </td></tr>
     </table>
     */
    public okhttp3.Call rollbackAgentSkillVersionAsync(Integer skillId, Integer version, final ApiCallback<CreateAgentSkill201Response> _callback) throws ApiException {

        okhttp3.Call localVarCall = rollbackAgentSkillVersionValidateBeforeCall(skillId, version, _callback);
        Type localVarReturnType = new TypeToken<CreateAgentSkill201Response>(){}.getType();
        localVarApiClient.executeAsync(localVarCall, localVarReturnType, _callback);
        return localVarCall;
    }
    /**
     * Build call for setAgentSkillForAgent
     * @param agentId ID агента (required)
     * @param skillId ID навыка (required)
     * @param setAgentSkill  (required)
     * @param _callback Callback for upload/download progress
     * @return Call to execute
     * @throws ApiException If fail to serialize the request body object
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> Объект JSON c ключом &#x60;agent_skill&#x60; </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Некорректный запрос </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Не авторизован </td><td>  -  </td></tr>
        <tr><td> 403 </td><td> Запрещено </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Не найдено </td><td>  -  </td></tr>
        <tr><td> 429 </td><td> Слишком много запросов </td><td>  -  </td></tr>
        <tr><td> 500 </td><td> Внутренняя ошибка сервера </td><td>  -  </td></tr>
     </table>
     */
    public okhttp3.Call setAgentSkillForAgentCall(Integer agentId, Integer skillId, SetAgentSkill setAgentSkill, final ApiCallback _callback) throws ApiException {
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

        Object localVarPostBody = setAgentSkill;

        // create path and map variables
        String localVarPath = "/api/v1/cloud-ai/agents/{agent_id}/skills/{skill_id}"
            .replace("{" + "agent_id" + "}", localVarApiClient.escapeString(agentId.toString()))
            .replace("{" + "skill_id" + "}", localVarApiClient.escapeString(skillId.toString()));

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
    private okhttp3.Call setAgentSkillForAgentValidateBeforeCall(Integer agentId, Integer skillId, SetAgentSkill setAgentSkill, final ApiCallback _callback) throws ApiException {
        // verify the required parameter 'agentId' is set
        if (agentId == null) {
            throw new ApiException("Missing the required parameter 'agentId' when calling setAgentSkillForAgent(Async)");
        }

        // verify the required parameter 'skillId' is set
        if (skillId == null) {
            throw new ApiException("Missing the required parameter 'skillId' when calling setAgentSkillForAgent(Async)");
        }

        // verify the required parameter 'setAgentSkill' is set
        if (setAgentSkill == null) {
            throw new ApiException("Missing the required parameter 'setAgentSkill' when calling setAgentSkillForAgent(Async)");
        }

        return setAgentSkillForAgentCall(agentId, skillId, setAgentSkill, _callback);

    }

    /**
     * Включение и выключение навыка у агента
     * Чтобы включить или выключить навык у агента, отправьте PUT-запрос на &#x60;/api/v1/cloud-ai/agents/{agent_id}/skills/{skill_id}&#x60;.  Первое включение подключает навык и закрепляет агента на текущей версии навыка или на версии из &#x60;version&#x60;. Выключение сохраняет закреплённую версию, поэтому повторное включение вернёт то же поведение. Изменения действуют сразу. Выключение неподключённого навыка ничего не меняет.
     * @param agentId ID агента (required)
     * @param skillId ID навыка (required)
     * @param setAgentSkill  (required)
     * @return SetAgentSkillForAgent200Response
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> Объект JSON c ключом &#x60;agent_skill&#x60; </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Некорректный запрос </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Не авторизован </td><td>  -  </td></tr>
        <tr><td> 403 </td><td> Запрещено </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Не найдено </td><td>  -  </td></tr>
        <tr><td> 429 </td><td> Слишком много запросов </td><td>  -  </td></tr>
        <tr><td> 500 </td><td> Внутренняя ошибка сервера </td><td>  -  </td></tr>
     </table>
     */
    public SetAgentSkillForAgent200Response setAgentSkillForAgent(Integer agentId, Integer skillId, SetAgentSkill setAgentSkill) throws ApiException {
        ApiResponse<SetAgentSkillForAgent200Response> localVarResp = setAgentSkillForAgentWithHttpInfo(agentId, skillId, setAgentSkill);
        return localVarResp.getData();
    }

    /**
     * Включение и выключение навыка у агента
     * Чтобы включить или выключить навык у агента, отправьте PUT-запрос на &#x60;/api/v1/cloud-ai/agents/{agent_id}/skills/{skill_id}&#x60;.  Первое включение подключает навык и закрепляет агента на текущей версии навыка или на версии из &#x60;version&#x60;. Выключение сохраняет закреплённую версию, поэтому повторное включение вернёт то же поведение. Изменения действуют сразу. Выключение неподключённого навыка ничего не меняет.
     * @param agentId ID агента (required)
     * @param skillId ID навыка (required)
     * @param setAgentSkill  (required)
     * @return ApiResponse&lt;SetAgentSkillForAgent200Response&gt;
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> Объект JSON c ключом &#x60;agent_skill&#x60; </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Некорректный запрос </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Не авторизован </td><td>  -  </td></tr>
        <tr><td> 403 </td><td> Запрещено </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Не найдено </td><td>  -  </td></tr>
        <tr><td> 429 </td><td> Слишком много запросов </td><td>  -  </td></tr>
        <tr><td> 500 </td><td> Внутренняя ошибка сервера </td><td>  -  </td></tr>
     </table>
     */
    public ApiResponse<SetAgentSkillForAgent200Response> setAgentSkillForAgentWithHttpInfo(Integer agentId, Integer skillId, SetAgentSkill setAgentSkill) throws ApiException {
        okhttp3.Call localVarCall = setAgentSkillForAgentValidateBeforeCall(agentId, skillId, setAgentSkill, null);
        Type localVarReturnType = new TypeToken<SetAgentSkillForAgent200Response>(){}.getType();
        return localVarApiClient.execute(localVarCall, localVarReturnType);
    }

    /**
     * Включение и выключение навыка у агента (asynchronously)
     * Чтобы включить или выключить навык у агента, отправьте PUT-запрос на &#x60;/api/v1/cloud-ai/agents/{agent_id}/skills/{skill_id}&#x60;.  Первое включение подключает навык и закрепляет агента на текущей версии навыка или на версии из &#x60;version&#x60;. Выключение сохраняет закреплённую версию, поэтому повторное включение вернёт то же поведение. Изменения действуют сразу. Выключение неподключённого навыка ничего не меняет.
     * @param agentId ID агента (required)
     * @param skillId ID навыка (required)
     * @param setAgentSkill  (required)
     * @param _callback The callback to be executed when the API call finishes
     * @return The request call
     * @throws ApiException If fail to process the API call, e.g. serializing the request body object
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> Объект JSON c ключом &#x60;agent_skill&#x60; </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Некорректный запрос </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Не авторизован </td><td>  -  </td></tr>
        <tr><td> 403 </td><td> Запрещено </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Не найдено </td><td>  -  </td></tr>
        <tr><td> 429 </td><td> Слишком много запросов </td><td>  -  </td></tr>
        <tr><td> 500 </td><td> Внутренняя ошибка сервера </td><td>  -  </td></tr>
     </table>
     */
    public okhttp3.Call setAgentSkillForAgentAsync(Integer agentId, Integer skillId, SetAgentSkill setAgentSkill, final ApiCallback<SetAgentSkillForAgent200Response> _callback) throws ApiException {

        okhttp3.Call localVarCall = setAgentSkillForAgentValidateBeforeCall(agentId, skillId, setAgentSkill, _callback);
        Type localVarReturnType = new TypeToken<SetAgentSkillForAgent200Response>(){}.getType();
        localVarApiClient.executeAsync(localVarCall, localVarReturnType, _callback);
        return localVarCall;
    }
    /**
     * Build call for syncAgentSkillAgentsVersions
     * @param skillId ID навыка (required)
     * @param _callback Callback for upload/download progress
     * @return Call to execute
     * @throws ApiException If fail to serialize the request body object
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> Объект JSON c ключами &#x60;skill&#x60; и &#x60;meta&#x60; </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Некорректный запрос </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Не авторизован </td><td>  -  </td></tr>
        <tr><td> 403 </td><td> Запрещено </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Не найдено </td><td>  -  </td></tr>
        <tr><td> 429 </td><td> Слишком много запросов </td><td>  -  </td></tr>
        <tr><td> 500 </td><td> Внутренняя ошибка сервера </td><td>  -  </td></tr>
     </table>
     */
    public okhttp3.Call syncAgentSkillAgentsVersionsCall(Integer skillId, final ApiCallback _callback) throws ApiException {
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
        String localVarPath = "/api/v1/cloud-ai/skills/{skill_id}/agents/sync-versions"
            .replace("{" + "skill_id" + "}", localVarApiClient.escapeString(skillId.toString()));

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
    private okhttp3.Call syncAgentSkillAgentsVersionsValidateBeforeCall(Integer skillId, final ApiCallback _callback) throws ApiException {
        // verify the required parameter 'skillId' is set
        if (skillId == null) {
            throw new ApiException("Missing the required parameter 'skillId' when calling syncAgentSkillAgentsVersions(Async)");
        }

        return syncAgentSkillAgentsVersionsCall(skillId, _callback);

    }

    /**
     * Перевод всех агентов навыка на текущую версию
     * Чтобы перевести всех агентов, у которых включён навык, на его текущую версию, отправьте POST-запрос на &#x60;/api/v1/cloud-ai/skills/{skill_id}/agents/sync-versions&#x60;.  Агенты, у которых навык выключен, остаются на своих версиях. Сколько агентов переведено, видно в &#x60;meta.updated_count&#x60;.
     * @param skillId ID навыка (required)
     * @return SyncAgentSkillAgentsVersions200Response
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> Объект JSON c ключами &#x60;skill&#x60; и &#x60;meta&#x60; </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Некорректный запрос </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Не авторизован </td><td>  -  </td></tr>
        <tr><td> 403 </td><td> Запрещено </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Не найдено </td><td>  -  </td></tr>
        <tr><td> 429 </td><td> Слишком много запросов </td><td>  -  </td></tr>
        <tr><td> 500 </td><td> Внутренняя ошибка сервера </td><td>  -  </td></tr>
     </table>
     */
    public SyncAgentSkillAgentsVersions200Response syncAgentSkillAgentsVersions(Integer skillId) throws ApiException {
        ApiResponse<SyncAgentSkillAgentsVersions200Response> localVarResp = syncAgentSkillAgentsVersionsWithHttpInfo(skillId);
        return localVarResp.getData();
    }

    /**
     * Перевод всех агентов навыка на текущую версию
     * Чтобы перевести всех агентов, у которых включён навык, на его текущую версию, отправьте POST-запрос на &#x60;/api/v1/cloud-ai/skills/{skill_id}/agents/sync-versions&#x60;.  Агенты, у которых навык выключен, остаются на своих версиях. Сколько агентов переведено, видно в &#x60;meta.updated_count&#x60;.
     * @param skillId ID навыка (required)
     * @return ApiResponse&lt;SyncAgentSkillAgentsVersions200Response&gt;
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> Объект JSON c ключами &#x60;skill&#x60; и &#x60;meta&#x60; </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Некорректный запрос </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Не авторизован </td><td>  -  </td></tr>
        <tr><td> 403 </td><td> Запрещено </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Не найдено </td><td>  -  </td></tr>
        <tr><td> 429 </td><td> Слишком много запросов </td><td>  -  </td></tr>
        <tr><td> 500 </td><td> Внутренняя ошибка сервера </td><td>  -  </td></tr>
     </table>
     */
    public ApiResponse<SyncAgentSkillAgentsVersions200Response> syncAgentSkillAgentsVersionsWithHttpInfo(Integer skillId) throws ApiException {
        okhttp3.Call localVarCall = syncAgentSkillAgentsVersionsValidateBeforeCall(skillId, null);
        Type localVarReturnType = new TypeToken<SyncAgentSkillAgentsVersions200Response>(){}.getType();
        return localVarApiClient.execute(localVarCall, localVarReturnType);
    }

    /**
     * Перевод всех агентов навыка на текущую версию (asynchronously)
     * Чтобы перевести всех агентов, у которых включён навык, на его текущую версию, отправьте POST-запрос на &#x60;/api/v1/cloud-ai/skills/{skill_id}/agents/sync-versions&#x60;.  Агенты, у которых навык выключен, остаются на своих версиях. Сколько агентов переведено, видно в &#x60;meta.updated_count&#x60;.
     * @param skillId ID навыка (required)
     * @param _callback The callback to be executed when the API call finishes
     * @return The request call
     * @throws ApiException If fail to process the API call, e.g. serializing the request body object
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> Объект JSON c ключами &#x60;skill&#x60; и &#x60;meta&#x60; </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Некорректный запрос </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Не авторизован </td><td>  -  </td></tr>
        <tr><td> 403 </td><td> Запрещено </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Не найдено </td><td>  -  </td></tr>
        <tr><td> 429 </td><td> Слишком много запросов </td><td>  -  </td></tr>
        <tr><td> 500 </td><td> Внутренняя ошибка сервера </td><td>  -  </td></tr>
     </table>
     */
    public okhttp3.Call syncAgentSkillAgentsVersionsAsync(Integer skillId, final ApiCallback<SyncAgentSkillAgentsVersions200Response> _callback) throws ApiException {

        okhttp3.Call localVarCall = syncAgentSkillAgentsVersionsValidateBeforeCall(skillId, _callback);
        Type localVarReturnType = new TypeToken<SyncAgentSkillAgentsVersions200Response>(){}.getType();
        localVarApiClient.executeAsync(localVarCall, localVarReturnType, _callback);
        return localVarCall;
    }
    /**
     * Build call for syncAgentSkillVersionsForAgent
     * @param agentId ID агента (required)
     * @param _callback Callback for upload/download progress
     * @return Call to execute
     * @throws ApiException If fail to serialize the request body object
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> Объект JSON c ключами &#x60;agent_skills&#x60;, &#x60;meta&#x60; и &#x60;updated_count&#x60; </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Некорректный запрос </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Не авторизован </td><td>  -  </td></tr>
        <tr><td> 403 </td><td> Запрещено </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Не найдено </td><td>  -  </td></tr>
        <tr><td> 429 </td><td> Слишком много запросов </td><td>  -  </td></tr>
        <tr><td> 500 </td><td> Внутренняя ошибка сервера </td><td>  -  </td></tr>
     </table>
     */
    public okhttp3.Call syncAgentSkillVersionsForAgentCall(Integer agentId, final ApiCallback _callback) throws ApiException {
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
        String localVarPath = "/api/v1/cloud-ai/agents/{agent_id}/skills/sync-versions"
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
        return localVarApiClient.buildCall(basePath, localVarPath, "POST", localVarQueryParams, localVarCollectionQueryParams, localVarPostBody, localVarHeaderParams, localVarCookieParams, localVarFormParams, localVarAuthNames, _callback);
    }

    @SuppressWarnings("rawtypes")
    private okhttp3.Call syncAgentSkillVersionsForAgentValidateBeforeCall(Integer agentId, final ApiCallback _callback) throws ApiException {
        // verify the required parameter 'agentId' is set
        if (agentId == null) {
            throw new ApiException("Missing the required parameter 'agentId' when calling syncAgentSkillVersionsForAgent(Async)");
        }

        return syncAgentSkillVersionsForAgentCall(agentId, _callback);

    }

    /**
     * Перевод всех навыков агента на текущие версии
     * Чтобы перевести все включённые у агента навыки на их текущие версии, отправьте POST-запрос на &#x60;/api/v1/cloud-ai/agents/{agent_id}/skills/sync-versions&#x60;.  В ответе все навыки аккаунта с состоянием у агента, как в списке навыков агента. Сколько навыков переведено, видно в &#x60;updated_count&#x60; в корне ответа.
     * @param agentId ID агента (required)
     * @return SyncAgentSkillVersionsForAgent200Response
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> Объект JSON c ключами &#x60;agent_skills&#x60;, &#x60;meta&#x60; и &#x60;updated_count&#x60; </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Некорректный запрос </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Не авторизован </td><td>  -  </td></tr>
        <tr><td> 403 </td><td> Запрещено </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Не найдено </td><td>  -  </td></tr>
        <tr><td> 429 </td><td> Слишком много запросов </td><td>  -  </td></tr>
        <tr><td> 500 </td><td> Внутренняя ошибка сервера </td><td>  -  </td></tr>
     </table>
     */
    public SyncAgentSkillVersionsForAgent200Response syncAgentSkillVersionsForAgent(Integer agentId) throws ApiException {
        ApiResponse<SyncAgentSkillVersionsForAgent200Response> localVarResp = syncAgentSkillVersionsForAgentWithHttpInfo(agentId);
        return localVarResp.getData();
    }

    /**
     * Перевод всех навыков агента на текущие версии
     * Чтобы перевести все включённые у агента навыки на их текущие версии, отправьте POST-запрос на &#x60;/api/v1/cloud-ai/agents/{agent_id}/skills/sync-versions&#x60;.  В ответе все навыки аккаунта с состоянием у агента, как в списке навыков агента. Сколько навыков переведено, видно в &#x60;updated_count&#x60; в корне ответа.
     * @param agentId ID агента (required)
     * @return ApiResponse&lt;SyncAgentSkillVersionsForAgent200Response&gt;
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> Объект JSON c ключами &#x60;agent_skills&#x60;, &#x60;meta&#x60; и &#x60;updated_count&#x60; </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Некорректный запрос </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Не авторизован </td><td>  -  </td></tr>
        <tr><td> 403 </td><td> Запрещено </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Не найдено </td><td>  -  </td></tr>
        <tr><td> 429 </td><td> Слишком много запросов </td><td>  -  </td></tr>
        <tr><td> 500 </td><td> Внутренняя ошибка сервера </td><td>  -  </td></tr>
     </table>
     */
    public ApiResponse<SyncAgentSkillVersionsForAgent200Response> syncAgentSkillVersionsForAgentWithHttpInfo(Integer agentId) throws ApiException {
        okhttp3.Call localVarCall = syncAgentSkillVersionsForAgentValidateBeforeCall(agentId, null);
        Type localVarReturnType = new TypeToken<SyncAgentSkillVersionsForAgent200Response>(){}.getType();
        return localVarApiClient.execute(localVarCall, localVarReturnType);
    }

    /**
     * Перевод всех навыков агента на текущие версии (asynchronously)
     * Чтобы перевести все включённые у агента навыки на их текущие версии, отправьте POST-запрос на &#x60;/api/v1/cloud-ai/agents/{agent_id}/skills/sync-versions&#x60;.  В ответе все навыки аккаунта с состоянием у агента, как в списке навыков агента. Сколько навыков переведено, видно в &#x60;updated_count&#x60; в корне ответа.
     * @param agentId ID агента (required)
     * @param _callback The callback to be executed when the API call finishes
     * @return The request call
     * @throws ApiException If fail to process the API call, e.g. serializing the request body object
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> Объект JSON c ключами &#x60;agent_skills&#x60;, &#x60;meta&#x60; и &#x60;updated_count&#x60; </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Некорректный запрос </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Не авторизован </td><td>  -  </td></tr>
        <tr><td> 403 </td><td> Запрещено </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Не найдено </td><td>  -  </td></tr>
        <tr><td> 429 </td><td> Слишком много запросов </td><td>  -  </td></tr>
        <tr><td> 500 </td><td> Внутренняя ошибка сервера </td><td>  -  </td></tr>
     </table>
     */
    public okhttp3.Call syncAgentSkillVersionsForAgentAsync(Integer agentId, final ApiCallback<SyncAgentSkillVersionsForAgent200Response> _callback) throws ApiException {

        okhttp3.Call localVarCall = syncAgentSkillVersionsForAgentValidateBeforeCall(agentId, _callback);
        Type localVarReturnType = new TypeToken<SyncAgentSkillVersionsForAgent200Response>(){}.getType();
        localVarApiClient.executeAsync(localVarCall, localVarReturnType, _callback);
        return localVarCall;
    }
    /**
     * Build call for updateAgentSkill
     * @param skillId ID навыка (required)
     * @param updateSkill  (required)
     * @param _callback Callback for upload/download progress
     * @return Call to execute
     * @throws ApiException If fail to serialize the request body object
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> Объект JSON c ключами &#x60;skill&#x60; и &#x60;meta&#x60; </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Некорректный запрос </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Не авторизован </td><td>  -  </td></tr>
        <tr><td> 403 </td><td> Запрещено </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Не найдено </td><td>  -  </td></tr>
        <tr><td> 409 </td><td> Конфликт </td><td>  -  </td></tr>
        <tr><td> 429 </td><td> Слишком много запросов </td><td>  -  </td></tr>
        <tr><td> 500 </td><td> Внутренняя ошибка сервера </td><td>  -  </td></tr>
     </table>
     */
    public okhttp3.Call updateAgentSkillCall(Integer skillId, UpdateSkill updateSkill, final ApiCallback _callback) throws ApiException {
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

        Object localVarPostBody = updateSkill;

        // create path and map variables
        String localVarPath = "/api/v1/cloud-ai/skills/{skill_id}"
            .replace("{" + "skill_id" + "}", localVarApiClient.escapeString(skillId.toString()));

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
    private okhttp3.Call updateAgentSkillValidateBeforeCall(Integer skillId, UpdateSkill updateSkill, final ApiCallback _callback) throws ApiException {
        // verify the required parameter 'skillId' is set
        if (skillId == null) {
            throw new ApiException("Missing the required parameter 'skillId' when calling updateAgentSkill(Async)");
        }

        // verify the required parameter 'updateSkill' is set
        if (updateSkill == null) {
            throw new ApiException("Missing the required parameter 'updateSkill' when calling updateAgentSkill(Async)");
        }

        return updateAgentSkillCall(skillId, updateSkill, _callback);

    }

    /**
     * Изменение навыка
     * Чтобы изменить навык, отправьте PATCH-запрос на &#x60;/api/v1/cloud-ai/skills/{skill_id}&#x60;, передав только изменяемые поля.  Изменение «когда применять», инструкции или скриптов создаёт новую версию. Переименование и сохранение без изменений версию не создают (&#x60;meta.version_created&#x60;). Подключённые агенты остаются на своих версиях. Сколько агентов затронуто, видно в &#x60;meta.affected_agents_count&#x60;. Если в истории уже 50 версий, самая старая свободная версия удаляется (&#x60;meta.pruned_versions&#x60;). Скрипты передаются в теле запроса. Их общий размер — до 10 МБ, а всё тело запроса — до 16 МБ; запрос с телом больше отклоняется до проверки.  Возможные ошибки 409: &#x60;skill_name_already_exists&#x60; — название занято; &#x60;skill_versions_limit_exceeded&#x60; — все 50 версий заняты текущей и закреплёнными за агентами; &#x60;skill_was_modified_concurrently&#x60; — навык одновременно изменён другим запросом, повторите сохранение.
     * @param skillId ID навыка (required)
     * @param updateSkill  (required)
     * @return CreateAgentSkill201Response
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> Объект JSON c ключами &#x60;skill&#x60; и &#x60;meta&#x60; </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Некорректный запрос </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Не авторизован </td><td>  -  </td></tr>
        <tr><td> 403 </td><td> Запрещено </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Не найдено </td><td>  -  </td></tr>
        <tr><td> 409 </td><td> Конфликт </td><td>  -  </td></tr>
        <tr><td> 429 </td><td> Слишком много запросов </td><td>  -  </td></tr>
        <tr><td> 500 </td><td> Внутренняя ошибка сервера </td><td>  -  </td></tr>
     </table>
     */
    public CreateAgentSkill201Response updateAgentSkill(Integer skillId, UpdateSkill updateSkill) throws ApiException {
        ApiResponse<CreateAgentSkill201Response> localVarResp = updateAgentSkillWithHttpInfo(skillId, updateSkill);
        return localVarResp.getData();
    }

    /**
     * Изменение навыка
     * Чтобы изменить навык, отправьте PATCH-запрос на &#x60;/api/v1/cloud-ai/skills/{skill_id}&#x60;, передав только изменяемые поля.  Изменение «когда применять», инструкции или скриптов создаёт новую версию. Переименование и сохранение без изменений версию не создают (&#x60;meta.version_created&#x60;). Подключённые агенты остаются на своих версиях. Сколько агентов затронуто, видно в &#x60;meta.affected_agents_count&#x60;. Если в истории уже 50 версий, самая старая свободная версия удаляется (&#x60;meta.pruned_versions&#x60;). Скрипты передаются в теле запроса. Их общий размер — до 10 МБ, а всё тело запроса — до 16 МБ; запрос с телом больше отклоняется до проверки.  Возможные ошибки 409: &#x60;skill_name_already_exists&#x60; — название занято; &#x60;skill_versions_limit_exceeded&#x60; — все 50 версий заняты текущей и закреплёнными за агентами; &#x60;skill_was_modified_concurrently&#x60; — навык одновременно изменён другим запросом, повторите сохранение.
     * @param skillId ID навыка (required)
     * @param updateSkill  (required)
     * @return ApiResponse&lt;CreateAgentSkill201Response&gt;
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> Объект JSON c ключами &#x60;skill&#x60; и &#x60;meta&#x60; </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Некорректный запрос </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Не авторизован </td><td>  -  </td></tr>
        <tr><td> 403 </td><td> Запрещено </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Не найдено </td><td>  -  </td></tr>
        <tr><td> 409 </td><td> Конфликт </td><td>  -  </td></tr>
        <tr><td> 429 </td><td> Слишком много запросов </td><td>  -  </td></tr>
        <tr><td> 500 </td><td> Внутренняя ошибка сервера </td><td>  -  </td></tr>
     </table>
     */
    public ApiResponse<CreateAgentSkill201Response> updateAgentSkillWithHttpInfo(Integer skillId, UpdateSkill updateSkill) throws ApiException {
        okhttp3.Call localVarCall = updateAgentSkillValidateBeforeCall(skillId, updateSkill, null);
        Type localVarReturnType = new TypeToken<CreateAgentSkill201Response>(){}.getType();
        return localVarApiClient.execute(localVarCall, localVarReturnType);
    }

    /**
     * Изменение навыка (asynchronously)
     * Чтобы изменить навык, отправьте PATCH-запрос на &#x60;/api/v1/cloud-ai/skills/{skill_id}&#x60;, передав только изменяемые поля.  Изменение «когда применять», инструкции или скриптов создаёт новую версию. Переименование и сохранение без изменений версию не создают (&#x60;meta.version_created&#x60;). Подключённые агенты остаются на своих версиях. Сколько агентов затронуто, видно в &#x60;meta.affected_agents_count&#x60;. Если в истории уже 50 версий, самая старая свободная версия удаляется (&#x60;meta.pruned_versions&#x60;). Скрипты передаются в теле запроса. Их общий размер — до 10 МБ, а всё тело запроса — до 16 МБ; запрос с телом больше отклоняется до проверки.  Возможные ошибки 409: &#x60;skill_name_already_exists&#x60; — название занято; &#x60;skill_versions_limit_exceeded&#x60; — все 50 версий заняты текущей и закреплёнными за агентами; &#x60;skill_was_modified_concurrently&#x60; — навык одновременно изменён другим запросом, повторите сохранение.
     * @param skillId ID навыка (required)
     * @param updateSkill  (required)
     * @param _callback The callback to be executed when the API call finishes
     * @return The request call
     * @throws ApiException If fail to process the API call, e.g. serializing the request body object
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> Объект JSON c ключами &#x60;skill&#x60; и &#x60;meta&#x60; </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Некорректный запрос </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Не авторизован </td><td>  -  </td></tr>
        <tr><td> 403 </td><td> Запрещено </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Не найдено </td><td>  -  </td></tr>
        <tr><td> 409 </td><td> Конфликт </td><td>  -  </td></tr>
        <tr><td> 429 </td><td> Слишком много запросов </td><td>  -  </td></tr>
        <tr><td> 500 </td><td> Внутренняя ошибка сервера </td><td>  -  </td></tr>
     </table>
     */
    public okhttp3.Call updateAgentSkillAsync(Integer skillId, UpdateSkill updateSkill, final ApiCallback<CreateAgentSkill201Response> _callback) throws ApiException {

        okhttp3.Call localVarCall = updateAgentSkillValidateBeforeCall(skillId, updateSkill, _callback);
        Type localVarReturnType = new TypeToken<CreateAgentSkill201Response>(){}.getType();
        localVarApiClient.executeAsync(localVarCall, localVarReturnType, _callback);
        return localVarCall;
    }
    /**
     * Build call for updateAgentSkillVersionForAgent
     * @param agentId ID агента (required)
     * @param skillId ID навыка (required)
     * @param _callback Callback for upload/download progress
     * @return Call to execute
     * @throws ApiException If fail to serialize the request body object
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> Объект JSON c ключом &#x60;agent_skill&#x60; </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Некорректный запрос </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Не авторизован </td><td>  -  </td></tr>
        <tr><td> 403 </td><td> Запрещено </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Не найдено </td><td>  -  </td></tr>
        <tr><td> 429 </td><td> Слишком много запросов </td><td>  -  </td></tr>
        <tr><td> 500 </td><td> Внутренняя ошибка сервера </td><td>  -  </td></tr>
     </table>
     */
    public okhttp3.Call updateAgentSkillVersionForAgentCall(Integer agentId, Integer skillId, final ApiCallback _callback) throws ApiException {
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
        String localVarPath = "/api/v1/cloud-ai/agents/{agent_id}/skills/{skill_id}/update-version"
            .replace("{" + "agent_id" + "}", localVarApiClient.escapeString(agentId.toString()))
            .replace("{" + "skill_id" + "}", localVarApiClient.escapeString(skillId.toString()));

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
    private okhttp3.Call updateAgentSkillVersionForAgentValidateBeforeCall(Integer agentId, Integer skillId, final ApiCallback _callback) throws ApiException {
        // verify the required parameter 'agentId' is set
        if (agentId == null) {
            throw new ApiException("Missing the required parameter 'agentId' when calling updateAgentSkillVersionForAgent(Async)");
        }

        // verify the required parameter 'skillId' is set
        if (skillId == null) {
            throw new ApiException("Missing the required parameter 'skillId' when calling updateAgentSkillVersionForAgent(Async)");
        }

        return updateAgentSkillVersionForAgentCall(agentId, skillId, _callback);

    }

    /**
     * Перевод агента на текущую версию навыка
     * Чтобы перевести агента на текущую версию навыка, отправьте POST-запрос на &#x60;/api/v1/cloud-ai/agents/{agent_id}/skills/{skill_id}/update-version&#x60;.  Если навык не подключён к агенту, вернётся 404 с &#x60;error_code&#x60; &#x60;agent_skill_link_not_found&#x60;.
     * @param agentId ID агента (required)
     * @param skillId ID навыка (required)
     * @return SetAgentSkillForAgent200Response
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> Объект JSON c ключом &#x60;agent_skill&#x60; </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Некорректный запрос </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Не авторизован </td><td>  -  </td></tr>
        <tr><td> 403 </td><td> Запрещено </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Не найдено </td><td>  -  </td></tr>
        <tr><td> 429 </td><td> Слишком много запросов </td><td>  -  </td></tr>
        <tr><td> 500 </td><td> Внутренняя ошибка сервера </td><td>  -  </td></tr>
     </table>
     */
    public SetAgentSkillForAgent200Response updateAgentSkillVersionForAgent(Integer agentId, Integer skillId) throws ApiException {
        ApiResponse<SetAgentSkillForAgent200Response> localVarResp = updateAgentSkillVersionForAgentWithHttpInfo(agentId, skillId);
        return localVarResp.getData();
    }

    /**
     * Перевод агента на текущую версию навыка
     * Чтобы перевести агента на текущую версию навыка, отправьте POST-запрос на &#x60;/api/v1/cloud-ai/agents/{agent_id}/skills/{skill_id}/update-version&#x60;.  Если навык не подключён к агенту, вернётся 404 с &#x60;error_code&#x60; &#x60;agent_skill_link_not_found&#x60;.
     * @param agentId ID агента (required)
     * @param skillId ID навыка (required)
     * @return ApiResponse&lt;SetAgentSkillForAgent200Response&gt;
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> Объект JSON c ключом &#x60;agent_skill&#x60; </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Некорректный запрос </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Не авторизован </td><td>  -  </td></tr>
        <tr><td> 403 </td><td> Запрещено </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Не найдено </td><td>  -  </td></tr>
        <tr><td> 429 </td><td> Слишком много запросов </td><td>  -  </td></tr>
        <tr><td> 500 </td><td> Внутренняя ошибка сервера </td><td>  -  </td></tr>
     </table>
     */
    public ApiResponse<SetAgentSkillForAgent200Response> updateAgentSkillVersionForAgentWithHttpInfo(Integer agentId, Integer skillId) throws ApiException {
        okhttp3.Call localVarCall = updateAgentSkillVersionForAgentValidateBeforeCall(agentId, skillId, null);
        Type localVarReturnType = new TypeToken<SetAgentSkillForAgent200Response>(){}.getType();
        return localVarApiClient.execute(localVarCall, localVarReturnType);
    }

    /**
     * Перевод агента на текущую версию навыка (asynchronously)
     * Чтобы перевести агента на текущую версию навыка, отправьте POST-запрос на &#x60;/api/v1/cloud-ai/agents/{agent_id}/skills/{skill_id}/update-version&#x60;.  Если навык не подключён к агенту, вернётся 404 с &#x60;error_code&#x60; &#x60;agent_skill_link_not_found&#x60;.
     * @param agentId ID агента (required)
     * @param skillId ID навыка (required)
     * @param _callback The callback to be executed when the API call finishes
     * @return The request call
     * @throws ApiException If fail to process the API call, e.g. serializing the request body object
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> Объект JSON c ключом &#x60;agent_skill&#x60; </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Некорректный запрос </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Не авторизован </td><td>  -  </td></tr>
        <tr><td> 403 </td><td> Запрещено </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Не найдено </td><td>  -  </td></tr>
        <tr><td> 429 </td><td> Слишком много запросов </td><td>  -  </td></tr>
        <tr><td> 500 </td><td> Внутренняя ошибка сервера </td><td>  -  </td></tr>
     </table>
     */
    public okhttp3.Call updateAgentSkillVersionForAgentAsync(Integer agentId, Integer skillId, final ApiCallback<SetAgentSkillForAgent200Response> _callback) throws ApiException {

        okhttp3.Call localVarCall = updateAgentSkillVersionForAgentValidateBeforeCall(agentId, skillId, _callback);
        Type localVarReturnType = new TypeToken<SetAgentSkillForAgent200Response>(){}.getType();
        localVarApiClient.executeAsync(localVarCall, localVarReturnType, _callback);
        return localVarCall;
    }
}
