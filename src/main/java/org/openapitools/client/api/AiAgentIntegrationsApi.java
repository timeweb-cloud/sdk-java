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


import org.openapitools.client.model.CreateAgentBotTelegramBot201Response;
import org.openapitools.client.model.CreateBot;
import org.openapitools.client.model.CreateCrispIntegration;
import org.openapitools.client.model.CreateJivoIntegration;
import org.openapitools.client.model.CreateMaxBot;
import org.openapitools.client.model.GetAccountStatus403Response;
import org.openapitools.client.model.GetAgentBotMaxBots200Response;
import org.openapitools.client.model.GetAgentBotMaxBotsByAgent200Response;
import org.openapitools.client.model.GetAgentBotTelegramBots200Response;
import org.openapitools.client.model.GetAgentBotTelegramBotsByAgent200Response;
import org.openapitools.client.model.GetAgentIntegrationCrisp200Response;
import org.openapitools.client.model.GetAgentIntegrationJivo200Response;
import org.openapitools.client.model.GetFinances400Response;
import org.openapitools.client.model.GetFinances401Response;
import org.openapitools.client.model.GetFinances429Response;
import org.openapitools.client.model.GetFinances500Response;
import org.openapitools.client.model.GetImage404Response;
import java.util.UUID;
import org.openapitools.client.model.UpdateBot;
import org.openapitools.client.model.UpdateDatabaseInstance409Response;
import org.openapitools.client.model.UpdateMaxBot;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AiAgentIntegrationsApi {
    private ApiClient localVarApiClient;
    private int localHostIndex;
    private String localCustomBaseUrl;

    public AiAgentIntegrationsApi() {
        this(Configuration.getDefaultApiClient());
    }

    public AiAgentIntegrationsApi(ApiClient apiClient) {
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
     * Build call for createAgentBotMaxBot
     * @param createMaxBot  (required)
     * @param _callback Callback for upload/download progress
     * @return Call to execute
     * @throws ApiException If fail to serialize the request body object
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 201 </td><td> Объект JSON c ключом &#x60;bot&#x60; </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Некорректный запрос </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Не авторизован </td><td>  -  </td></tr>
        <tr><td> 403 </td><td> Запрещено </td><td>  -  </td></tr>
        <tr><td> 409 </td><td> Конфликт </td><td>  -  </td></tr>
        <tr><td> 429 </td><td> Слишком много запросов </td><td>  -  </td></tr>
        <tr><td> 500 </td><td> Внутренняя ошибка сервера </td><td>  -  </td></tr>
     </table>
     */
    public okhttp3.Call createAgentBotMaxBotCall(CreateMaxBot createMaxBot, final ApiCallback _callback) throws ApiException {
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

        Object localVarPostBody = createMaxBot;

        // create path and map variables
        String localVarPath = "/api/v1/cloud-ai/max-bots";

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
    private okhttp3.Call createAgentBotMaxBotValidateBeforeCall(CreateMaxBot createMaxBot, final ApiCallback _callback) throws ApiException {
        // verify the required parameter 'createMaxBot' is set
        if (createMaxBot == null) {
            throw new ApiException("Missing the required parameter 'createMaxBot' when calling createAgentBotMaxBot(Async)");
        }

        return createAgentBotMaxBotCall(createMaxBot, _callback);

    }

    /**
     * Подключение бота MAX к агенту
     * Чтобы подключить бота MAX к AI агенту, отправьте POST-запрос на &#x60;/api/v1/cloud-ai/max-bots&#x60;, передав ID агента и токен бота от @MasterBot.  Платформа проверит токен в MAX, сохранит бота и сама подпишет его на события (вебхук) — настраивать подписку в MAX не нужно. После этого агент начинает отвечать пользователям бота.  Один бот может быть подключен только к одному агенту. Если бот уже подключен к агенту, который был удален, бот будет переподключен к новому агенту. Ограничение доступа по списку пользователей для ботов MAX не поддерживается.  Ошибки: - &#x60;400 invalid_max_token&#x60; — MAX не принял токен; - &#x60;400 max_webhook_error&#x60; — не удалось подписать бота на события; - &#x60;409 bot_already_exists&#x60; — бот уже подключен к существующему агенту или к другому аккаунту.  Тело ответа будет представлять собой объект JSON с ключом &#x60;bot&#x60;.
     * @param createMaxBot  (required)
     * @return CreateAgentBotTelegramBot201Response
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 201 </td><td> Объект JSON c ключом &#x60;bot&#x60; </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Некорректный запрос </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Не авторизован </td><td>  -  </td></tr>
        <tr><td> 403 </td><td> Запрещено </td><td>  -  </td></tr>
        <tr><td> 409 </td><td> Конфликт </td><td>  -  </td></tr>
        <tr><td> 429 </td><td> Слишком много запросов </td><td>  -  </td></tr>
        <tr><td> 500 </td><td> Внутренняя ошибка сервера </td><td>  -  </td></tr>
     </table>
     */
    public CreateAgentBotTelegramBot201Response createAgentBotMaxBot(CreateMaxBot createMaxBot) throws ApiException {
        ApiResponse<CreateAgentBotTelegramBot201Response> localVarResp = createAgentBotMaxBotWithHttpInfo(createMaxBot);
        return localVarResp.getData();
    }

    /**
     * Подключение бота MAX к агенту
     * Чтобы подключить бота MAX к AI агенту, отправьте POST-запрос на &#x60;/api/v1/cloud-ai/max-bots&#x60;, передав ID агента и токен бота от @MasterBot.  Платформа проверит токен в MAX, сохранит бота и сама подпишет его на события (вебхук) — настраивать подписку в MAX не нужно. После этого агент начинает отвечать пользователям бота.  Один бот может быть подключен только к одному агенту. Если бот уже подключен к агенту, который был удален, бот будет переподключен к новому агенту. Ограничение доступа по списку пользователей для ботов MAX не поддерживается.  Ошибки: - &#x60;400 invalid_max_token&#x60; — MAX не принял токен; - &#x60;400 max_webhook_error&#x60; — не удалось подписать бота на события; - &#x60;409 bot_already_exists&#x60; — бот уже подключен к существующему агенту или к другому аккаунту.  Тело ответа будет представлять собой объект JSON с ключом &#x60;bot&#x60;.
     * @param createMaxBot  (required)
     * @return ApiResponse&lt;CreateAgentBotTelegramBot201Response&gt;
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 201 </td><td> Объект JSON c ключом &#x60;bot&#x60; </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Некорректный запрос </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Не авторизован </td><td>  -  </td></tr>
        <tr><td> 403 </td><td> Запрещено </td><td>  -  </td></tr>
        <tr><td> 409 </td><td> Конфликт </td><td>  -  </td></tr>
        <tr><td> 429 </td><td> Слишком много запросов </td><td>  -  </td></tr>
        <tr><td> 500 </td><td> Внутренняя ошибка сервера </td><td>  -  </td></tr>
     </table>
     */
    public ApiResponse<CreateAgentBotTelegramBot201Response> createAgentBotMaxBotWithHttpInfo(CreateMaxBot createMaxBot) throws ApiException {
        okhttp3.Call localVarCall = createAgentBotMaxBotValidateBeforeCall(createMaxBot, null);
        Type localVarReturnType = new TypeToken<CreateAgentBotTelegramBot201Response>(){}.getType();
        return localVarApiClient.execute(localVarCall, localVarReturnType);
    }

    /**
     * Подключение бота MAX к агенту (asynchronously)
     * Чтобы подключить бота MAX к AI агенту, отправьте POST-запрос на &#x60;/api/v1/cloud-ai/max-bots&#x60;, передав ID агента и токен бота от @MasterBot.  Платформа проверит токен в MAX, сохранит бота и сама подпишет его на события (вебхук) — настраивать подписку в MAX не нужно. После этого агент начинает отвечать пользователям бота.  Один бот может быть подключен только к одному агенту. Если бот уже подключен к агенту, который был удален, бот будет переподключен к новому агенту. Ограничение доступа по списку пользователей для ботов MAX не поддерживается.  Ошибки: - &#x60;400 invalid_max_token&#x60; — MAX не принял токен; - &#x60;400 max_webhook_error&#x60; — не удалось подписать бота на события; - &#x60;409 bot_already_exists&#x60; — бот уже подключен к существующему агенту или к другому аккаунту.  Тело ответа будет представлять собой объект JSON с ключом &#x60;bot&#x60;.
     * @param createMaxBot  (required)
     * @param _callback The callback to be executed when the API call finishes
     * @return The request call
     * @throws ApiException If fail to process the API call, e.g. serializing the request body object
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 201 </td><td> Объект JSON c ключом &#x60;bot&#x60; </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Некорректный запрос </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Не авторизован </td><td>  -  </td></tr>
        <tr><td> 403 </td><td> Запрещено </td><td>  -  </td></tr>
        <tr><td> 409 </td><td> Конфликт </td><td>  -  </td></tr>
        <tr><td> 429 </td><td> Слишком много запросов </td><td>  -  </td></tr>
        <tr><td> 500 </td><td> Внутренняя ошибка сервера </td><td>  -  </td></tr>
     </table>
     */
    public okhttp3.Call createAgentBotMaxBotAsync(CreateMaxBot createMaxBot, final ApiCallback<CreateAgentBotTelegramBot201Response> _callback) throws ApiException {

        okhttp3.Call localVarCall = createAgentBotMaxBotValidateBeforeCall(createMaxBot, _callback);
        Type localVarReturnType = new TypeToken<CreateAgentBotTelegramBot201Response>(){}.getType();
        localVarApiClient.executeAsync(localVarCall, localVarReturnType, _callback);
        return localVarCall;
    }
    /**
     * Build call for createAgentBotTelegramBot
     * @param createBot  (required)
     * @param _callback Callback for upload/download progress
     * @return Call to execute
     * @throws ApiException If fail to serialize the request body object
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 201 </td><td> Объект JSON c ключом &#x60;bot&#x60; </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Некорректный запрос </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Не авторизован </td><td>  -  </td></tr>
        <tr><td> 403 </td><td> Запрещено </td><td>  -  </td></tr>
        <tr><td> 409 </td><td> Конфликт </td><td>  -  </td></tr>
        <tr><td> 429 </td><td> Слишком много запросов </td><td>  -  </td></tr>
        <tr><td> 500 </td><td> Внутренняя ошибка сервера </td><td>  -  </td></tr>
     </table>
     */
    public okhttp3.Call createAgentBotTelegramBotCall(CreateBot createBot, final ApiCallback _callback) throws ApiException {
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

        Object localVarPostBody = createBot;

        // create path and map variables
        String localVarPath = "/api/v1/cloud-ai/bots";

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
    private okhttp3.Call createAgentBotTelegramBotValidateBeforeCall(CreateBot createBot, final ApiCallback _callback) throws ApiException {
        // verify the required parameter 'createBot' is set
        if (createBot == null) {
            throw new ApiException("Missing the required parameter 'createBot' when calling createAgentBotTelegramBot(Async)");
        }

        return createAgentBotTelegramBotCall(createBot, _callback);

    }

    /**
     * Подключение Telegram-бота к агенту
     * Чтобы подключить Telegram-бота к AI агенту, отправьте POST-запрос на &#x60;/api/v1/cloud-ai/bots&#x60;, передав ID агента и токен бота от @BotFather.  Платформа проверит токен в Telegram, сохранит бота и сама установит ему вебхук — настраивать вебхук в Telegram не нужно. После этого агент начинает отвечать пользователям бота. Ранее накопленные у бота обновления сбрасываются.  Один бот может быть подключен только к одному агенту. Если бот уже подключен к агенту, который был удален, бот будет переподключен к новому агенту.  Ошибки: - &#x60;400 invalid_telegram_token&#x60; — Telegram не принял токен; - &#x60;400 telegram_webhook_error&#x60; — не удалось установить вебхук; - &#x60;409 bot_already_exists&#x60; — бот уже подключен к существующему агенту или к другому аккаунту.  Тело ответа будет представлять собой объект JSON с ключом &#x60;bot&#x60;.
     * @param createBot  (required)
     * @return CreateAgentBotTelegramBot201Response
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 201 </td><td> Объект JSON c ключом &#x60;bot&#x60; </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Некорректный запрос </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Не авторизован </td><td>  -  </td></tr>
        <tr><td> 403 </td><td> Запрещено </td><td>  -  </td></tr>
        <tr><td> 409 </td><td> Конфликт </td><td>  -  </td></tr>
        <tr><td> 429 </td><td> Слишком много запросов </td><td>  -  </td></tr>
        <tr><td> 500 </td><td> Внутренняя ошибка сервера </td><td>  -  </td></tr>
     </table>
     */
    public CreateAgentBotTelegramBot201Response createAgentBotTelegramBot(CreateBot createBot) throws ApiException {
        ApiResponse<CreateAgentBotTelegramBot201Response> localVarResp = createAgentBotTelegramBotWithHttpInfo(createBot);
        return localVarResp.getData();
    }

    /**
     * Подключение Telegram-бота к агенту
     * Чтобы подключить Telegram-бота к AI агенту, отправьте POST-запрос на &#x60;/api/v1/cloud-ai/bots&#x60;, передав ID агента и токен бота от @BotFather.  Платформа проверит токен в Telegram, сохранит бота и сама установит ему вебхук — настраивать вебхук в Telegram не нужно. После этого агент начинает отвечать пользователям бота. Ранее накопленные у бота обновления сбрасываются.  Один бот может быть подключен только к одному агенту. Если бот уже подключен к агенту, который был удален, бот будет переподключен к новому агенту.  Ошибки: - &#x60;400 invalid_telegram_token&#x60; — Telegram не принял токен; - &#x60;400 telegram_webhook_error&#x60; — не удалось установить вебхук; - &#x60;409 bot_already_exists&#x60; — бот уже подключен к существующему агенту или к другому аккаунту.  Тело ответа будет представлять собой объект JSON с ключом &#x60;bot&#x60;.
     * @param createBot  (required)
     * @return ApiResponse&lt;CreateAgentBotTelegramBot201Response&gt;
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 201 </td><td> Объект JSON c ключом &#x60;bot&#x60; </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Некорректный запрос </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Не авторизован </td><td>  -  </td></tr>
        <tr><td> 403 </td><td> Запрещено </td><td>  -  </td></tr>
        <tr><td> 409 </td><td> Конфликт </td><td>  -  </td></tr>
        <tr><td> 429 </td><td> Слишком много запросов </td><td>  -  </td></tr>
        <tr><td> 500 </td><td> Внутренняя ошибка сервера </td><td>  -  </td></tr>
     </table>
     */
    public ApiResponse<CreateAgentBotTelegramBot201Response> createAgentBotTelegramBotWithHttpInfo(CreateBot createBot) throws ApiException {
        okhttp3.Call localVarCall = createAgentBotTelegramBotValidateBeforeCall(createBot, null);
        Type localVarReturnType = new TypeToken<CreateAgentBotTelegramBot201Response>(){}.getType();
        return localVarApiClient.execute(localVarCall, localVarReturnType);
    }

    /**
     * Подключение Telegram-бота к агенту (asynchronously)
     * Чтобы подключить Telegram-бота к AI агенту, отправьте POST-запрос на &#x60;/api/v1/cloud-ai/bots&#x60;, передав ID агента и токен бота от @BotFather.  Платформа проверит токен в Telegram, сохранит бота и сама установит ему вебхук — настраивать вебхук в Telegram не нужно. После этого агент начинает отвечать пользователям бота. Ранее накопленные у бота обновления сбрасываются.  Один бот может быть подключен только к одному агенту. Если бот уже подключен к агенту, который был удален, бот будет переподключен к новому агенту.  Ошибки: - &#x60;400 invalid_telegram_token&#x60; — Telegram не принял токен; - &#x60;400 telegram_webhook_error&#x60; — не удалось установить вебхук; - &#x60;409 bot_already_exists&#x60; — бот уже подключен к существующему агенту или к другому аккаунту.  Тело ответа будет представлять собой объект JSON с ключом &#x60;bot&#x60;.
     * @param createBot  (required)
     * @param _callback The callback to be executed when the API call finishes
     * @return The request call
     * @throws ApiException If fail to process the API call, e.g. serializing the request body object
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 201 </td><td> Объект JSON c ключом &#x60;bot&#x60; </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Некорректный запрос </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Не авторизован </td><td>  -  </td></tr>
        <tr><td> 403 </td><td> Запрещено </td><td>  -  </td></tr>
        <tr><td> 409 </td><td> Конфликт </td><td>  -  </td></tr>
        <tr><td> 429 </td><td> Слишком много запросов </td><td>  -  </td></tr>
        <tr><td> 500 </td><td> Внутренняя ошибка сервера </td><td>  -  </td></tr>
     </table>
     */
    public okhttp3.Call createAgentBotTelegramBotAsync(CreateBot createBot, final ApiCallback<CreateAgentBotTelegramBot201Response> _callback) throws ApiException {

        okhttp3.Call localVarCall = createAgentBotTelegramBotValidateBeforeCall(createBot, _callback);
        Type localVarReturnType = new TypeToken<CreateAgentBotTelegramBot201Response>(){}.getType();
        localVarApiClient.executeAsync(localVarCall, localVarReturnType, _callback);
        return localVarCall;
    }
    /**
     * Build call for createAgentIntegrationCrisp
     * @param agentId ID агента (required)
     * @param createCrispIntegration  (required)
     * @param _callback Callback for upload/download progress
     * @return Call to execute
     * @throws ApiException If fail to serialize the request body object
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 201 </td><td> Объект JSON c ключом &#x60;crisp_integration&#x60; </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Некорректный запрос </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Не авторизован </td><td>  -  </td></tr>
        <tr><td> 403 </td><td> Запрещено </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Не найдено </td><td>  -  </td></tr>
        <tr><td> 409 </td><td> Конфликт </td><td>  -  </td></tr>
        <tr><td> 429 </td><td> Слишком много запросов </td><td>  -  </td></tr>
        <tr><td> 500 </td><td> Внутренняя ошибка сервера </td><td>  -  </td></tr>
        <tr><td> 503 </td><td> Интеграция с Crisp временно недоступна </td><td>  -  </td></tr>
     </table>
     */
    public okhttp3.Call createAgentIntegrationCrispCall(Integer agentId, CreateCrispIntegration createCrispIntegration, final ApiCallback _callback) throws ApiException {
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

        Object localVarPostBody = createCrispIntegration;

        // create path and map variables
        String localVarPath = "/api/v1/cloud-ai/agent/{agent_id}/crisp"
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
    private okhttp3.Call createAgentIntegrationCrispValidateBeforeCall(Integer agentId, CreateCrispIntegration createCrispIntegration, final ApiCallback _callback) throws ApiException {
        // verify the required parameter 'agentId' is set
        if (agentId == null) {
            throw new ApiException("Missing the required parameter 'agentId' when calling createAgentIntegrationCrisp(Async)");
        }

        // verify the required parameter 'createCrispIntegration' is set
        if (createCrispIntegration == null) {
            throw new ApiException("Missing the required parameter 'createCrispIntegration' when calling createAgentIntegrationCrisp(Async)");
        }

        return createAgentIntegrationCrispCall(agentId, createCrispIntegration, _callback);

    }

    /**
     * Подключение агента к Crisp
     * Чтобы подключить AI агента к Crisp, отправьте POST-запрос на &#x60;/api/v1/cloud-ai/agent/{agent_id}/crisp&#x60;, передав Website ID сайта и API-токен Crisp (Identifier и Key).  Платформа проверит токен в Crisp и вернет &#x60;webhook_secret&#x60;. Затем добавьте в Crisp вебхук &#x60;https://api.timeweb.cloud/api/v1/crisp/{website_id}/webhook/{webhook_secret}&#x60; — подробнее в разделе «Интеграции AI агентов».  К одному агенту можно подключить один сайт Crisp, и один сайт — только к одному агенту.  Ошибки: - &#x60;400 invalid_crisp_token&#x60; — Crisp не принял токен для этого сайта; - &#x60;409 bot_already_exists&#x60; — у агента уже есть интеграция с Crisp или сайт подключен к другому агенту; - &#x60;503&#x60; — интеграция с Crisp временно недоступна.  Тело ответа будет представлять собой объект JSON с ключом &#x60;crisp_integration&#x60;.
     * @param agentId ID агента (required)
     * @param createCrispIntegration  (required)
     * @return GetAgentIntegrationCrisp200Response
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 201 </td><td> Объект JSON c ключом &#x60;crisp_integration&#x60; </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Некорректный запрос </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Не авторизован </td><td>  -  </td></tr>
        <tr><td> 403 </td><td> Запрещено </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Не найдено </td><td>  -  </td></tr>
        <tr><td> 409 </td><td> Конфликт </td><td>  -  </td></tr>
        <tr><td> 429 </td><td> Слишком много запросов </td><td>  -  </td></tr>
        <tr><td> 500 </td><td> Внутренняя ошибка сервера </td><td>  -  </td></tr>
        <tr><td> 503 </td><td> Интеграция с Crisp временно недоступна </td><td>  -  </td></tr>
     </table>
     */
    public GetAgentIntegrationCrisp200Response createAgentIntegrationCrisp(Integer agentId, CreateCrispIntegration createCrispIntegration) throws ApiException {
        ApiResponse<GetAgentIntegrationCrisp200Response> localVarResp = createAgentIntegrationCrispWithHttpInfo(agentId, createCrispIntegration);
        return localVarResp.getData();
    }

    /**
     * Подключение агента к Crisp
     * Чтобы подключить AI агента к Crisp, отправьте POST-запрос на &#x60;/api/v1/cloud-ai/agent/{agent_id}/crisp&#x60;, передав Website ID сайта и API-токен Crisp (Identifier и Key).  Платформа проверит токен в Crisp и вернет &#x60;webhook_secret&#x60;. Затем добавьте в Crisp вебхук &#x60;https://api.timeweb.cloud/api/v1/crisp/{website_id}/webhook/{webhook_secret}&#x60; — подробнее в разделе «Интеграции AI агентов».  К одному агенту можно подключить один сайт Crisp, и один сайт — только к одному агенту.  Ошибки: - &#x60;400 invalid_crisp_token&#x60; — Crisp не принял токен для этого сайта; - &#x60;409 bot_already_exists&#x60; — у агента уже есть интеграция с Crisp или сайт подключен к другому агенту; - &#x60;503&#x60; — интеграция с Crisp временно недоступна.  Тело ответа будет представлять собой объект JSON с ключом &#x60;crisp_integration&#x60;.
     * @param agentId ID агента (required)
     * @param createCrispIntegration  (required)
     * @return ApiResponse&lt;GetAgentIntegrationCrisp200Response&gt;
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 201 </td><td> Объект JSON c ключом &#x60;crisp_integration&#x60; </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Некорректный запрос </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Не авторизован </td><td>  -  </td></tr>
        <tr><td> 403 </td><td> Запрещено </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Не найдено </td><td>  -  </td></tr>
        <tr><td> 409 </td><td> Конфликт </td><td>  -  </td></tr>
        <tr><td> 429 </td><td> Слишком много запросов </td><td>  -  </td></tr>
        <tr><td> 500 </td><td> Внутренняя ошибка сервера </td><td>  -  </td></tr>
        <tr><td> 503 </td><td> Интеграция с Crisp временно недоступна </td><td>  -  </td></tr>
     </table>
     */
    public ApiResponse<GetAgentIntegrationCrisp200Response> createAgentIntegrationCrispWithHttpInfo(Integer agentId, CreateCrispIntegration createCrispIntegration) throws ApiException {
        okhttp3.Call localVarCall = createAgentIntegrationCrispValidateBeforeCall(agentId, createCrispIntegration, null);
        Type localVarReturnType = new TypeToken<GetAgentIntegrationCrisp200Response>(){}.getType();
        return localVarApiClient.execute(localVarCall, localVarReturnType);
    }

    /**
     * Подключение агента к Crisp (asynchronously)
     * Чтобы подключить AI агента к Crisp, отправьте POST-запрос на &#x60;/api/v1/cloud-ai/agent/{agent_id}/crisp&#x60;, передав Website ID сайта и API-токен Crisp (Identifier и Key).  Платформа проверит токен в Crisp и вернет &#x60;webhook_secret&#x60;. Затем добавьте в Crisp вебхук &#x60;https://api.timeweb.cloud/api/v1/crisp/{website_id}/webhook/{webhook_secret}&#x60; — подробнее в разделе «Интеграции AI агентов».  К одному агенту можно подключить один сайт Crisp, и один сайт — только к одному агенту.  Ошибки: - &#x60;400 invalid_crisp_token&#x60; — Crisp не принял токен для этого сайта; - &#x60;409 bot_already_exists&#x60; — у агента уже есть интеграция с Crisp или сайт подключен к другому агенту; - &#x60;503&#x60; — интеграция с Crisp временно недоступна.  Тело ответа будет представлять собой объект JSON с ключом &#x60;crisp_integration&#x60;.
     * @param agentId ID агента (required)
     * @param createCrispIntegration  (required)
     * @param _callback The callback to be executed when the API call finishes
     * @return The request call
     * @throws ApiException If fail to process the API call, e.g. serializing the request body object
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 201 </td><td> Объект JSON c ключом &#x60;crisp_integration&#x60; </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Некорректный запрос </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Не авторизован </td><td>  -  </td></tr>
        <tr><td> 403 </td><td> Запрещено </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Не найдено </td><td>  -  </td></tr>
        <tr><td> 409 </td><td> Конфликт </td><td>  -  </td></tr>
        <tr><td> 429 </td><td> Слишком много запросов </td><td>  -  </td></tr>
        <tr><td> 500 </td><td> Внутренняя ошибка сервера </td><td>  -  </td></tr>
        <tr><td> 503 </td><td> Интеграция с Crisp временно недоступна </td><td>  -  </td></tr>
     </table>
     */
    public okhttp3.Call createAgentIntegrationCrispAsync(Integer agentId, CreateCrispIntegration createCrispIntegration, final ApiCallback<GetAgentIntegrationCrisp200Response> _callback) throws ApiException {

        okhttp3.Call localVarCall = createAgentIntegrationCrispValidateBeforeCall(agentId, createCrispIntegration, _callback);
        Type localVarReturnType = new TypeToken<GetAgentIntegrationCrisp200Response>(){}.getType();
        localVarApiClient.executeAsync(localVarCall, localVarReturnType, _callback);
        return localVarCall;
    }
    /**
     * Build call for createAgentIntegrationJivo
     * @param agentId ID агента (required)
     * @param createJivoIntegration  (required)
     * @param _callback Callback for upload/download progress
     * @return Call to execute
     * @throws ApiException If fail to serialize the request body object
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 201 </td><td> Объект JSON c ключом &#x60;jivo_integration&#x60; </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Некорректный запрос </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Не авторизован </td><td>  -  </td></tr>
        <tr><td> 403 </td><td> Запрещено </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Не найдено </td><td>  -  </td></tr>
        <tr><td> 409 </td><td> Конфликт </td><td>  -  </td></tr>
        <tr><td> 429 </td><td> Слишком много запросов </td><td>  -  </td></tr>
        <tr><td> 500 </td><td> Внутренняя ошибка сервера </td><td>  -  </td></tr>
     </table>
     */
    public okhttp3.Call createAgentIntegrationJivoCall(Integer agentId, CreateJivoIntegration createJivoIntegration, final ApiCallback _callback) throws ApiException {
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

        Object localVarPostBody = createJivoIntegration;

        // create path and map variables
        String localVarPath = "/api/v1/cloud-ai/agent/{agent_id}/jivo"
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
    private okhttp3.Call createAgentIntegrationJivoValidateBeforeCall(Integer agentId, CreateJivoIntegration createJivoIntegration, final ApiCallback _callback) throws ApiException {
        // verify the required parameter 'agentId' is set
        if (agentId == null) {
            throw new ApiException("Missing the required parameter 'agentId' when calling createAgentIntegrationJivo(Async)");
        }

        // verify the required parameter 'createJivoIntegration' is set
        if (createJivoIntegration == null) {
            throw new ApiException("Missing the required parameter 'createJivoIntegration' when calling createAgentIntegrationJivo(Async)");
        }

        return createAgentIntegrationJivoCall(agentId, createJivoIntegration, _callback);

    }

    /**
     * Подключение агента к Jivo
     * Чтобы подключить AI агента к Jivo, отправьте POST-запрос на &#x60;/api/v1/cloud-ai/agent/{agent_id}/jivo&#x60;, передав Provider ID, выданный поддержкой Jivo.  Подготовка описана в разделе «Интеграции AI агентов»: адрес для событий и токен бота нужно заранее передать в поддержку Jivo.  Если агент не найден, вернется ошибка &#x60;404 agent_not_found&#x60;.  К одному агенту можно подключить одну интеграцию Jivo. Если она уже есть, вернется ошибка &#x60;409 bot_already_exists&#x60;.  Тело ответа будет представлять собой объект JSON с ключом &#x60;jivo_integration&#x60;.
     * @param agentId ID агента (required)
     * @param createJivoIntegration  (required)
     * @return GetAgentIntegrationJivo200Response
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 201 </td><td> Объект JSON c ключом &#x60;jivo_integration&#x60; </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Некорректный запрос </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Не авторизован </td><td>  -  </td></tr>
        <tr><td> 403 </td><td> Запрещено </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Не найдено </td><td>  -  </td></tr>
        <tr><td> 409 </td><td> Конфликт </td><td>  -  </td></tr>
        <tr><td> 429 </td><td> Слишком много запросов </td><td>  -  </td></tr>
        <tr><td> 500 </td><td> Внутренняя ошибка сервера </td><td>  -  </td></tr>
     </table>
     */
    public GetAgentIntegrationJivo200Response createAgentIntegrationJivo(Integer agentId, CreateJivoIntegration createJivoIntegration) throws ApiException {
        ApiResponse<GetAgentIntegrationJivo200Response> localVarResp = createAgentIntegrationJivoWithHttpInfo(agentId, createJivoIntegration);
        return localVarResp.getData();
    }

    /**
     * Подключение агента к Jivo
     * Чтобы подключить AI агента к Jivo, отправьте POST-запрос на &#x60;/api/v1/cloud-ai/agent/{agent_id}/jivo&#x60;, передав Provider ID, выданный поддержкой Jivo.  Подготовка описана в разделе «Интеграции AI агентов»: адрес для событий и токен бота нужно заранее передать в поддержку Jivo.  Если агент не найден, вернется ошибка &#x60;404 agent_not_found&#x60;.  К одному агенту можно подключить одну интеграцию Jivo. Если она уже есть, вернется ошибка &#x60;409 bot_already_exists&#x60;.  Тело ответа будет представлять собой объект JSON с ключом &#x60;jivo_integration&#x60;.
     * @param agentId ID агента (required)
     * @param createJivoIntegration  (required)
     * @return ApiResponse&lt;GetAgentIntegrationJivo200Response&gt;
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 201 </td><td> Объект JSON c ключом &#x60;jivo_integration&#x60; </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Некорректный запрос </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Не авторизован </td><td>  -  </td></tr>
        <tr><td> 403 </td><td> Запрещено </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Не найдено </td><td>  -  </td></tr>
        <tr><td> 409 </td><td> Конфликт </td><td>  -  </td></tr>
        <tr><td> 429 </td><td> Слишком много запросов </td><td>  -  </td></tr>
        <tr><td> 500 </td><td> Внутренняя ошибка сервера </td><td>  -  </td></tr>
     </table>
     */
    public ApiResponse<GetAgentIntegrationJivo200Response> createAgentIntegrationJivoWithHttpInfo(Integer agentId, CreateJivoIntegration createJivoIntegration) throws ApiException {
        okhttp3.Call localVarCall = createAgentIntegrationJivoValidateBeforeCall(agentId, createJivoIntegration, null);
        Type localVarReturnType = new TypeToken<GetAgentIntegrationJivo200Response>(){}.getType();
        return localVarApiClient.execute(localVarCall, localVarReturnType);
    }

    /**
     * Подключение агента к Jivo (asynchronously)
     * Чтобы подключить AI агента к Jivo, отправьте POST-запрос на &#x60;/api/v1/cloud-ai/agent/{agent_id}/jivo&#x60;, передав Provider ID, выданный поддержкой Jivo.  Подготовка описана в разделе «Интеграции AI агентов»: адрес для событий и токен бота нужно заранее передать в поддержку Jivo.  Если агент не найден, вернется ошибка &#x60;404 agent_not_found&#x60;.  К одному агенту можно подключить одну интеграцию Jivo. Если она уже есть, вернется ошибка &#x60;409 bot_already_exists&#x60;.  Тело ответа будет представлять собой объект JSON с ключом &#x60;jivo_integration&#x60;.
     * @param agentId ID агента (required)
     * @param createJivoIntegration  (required)
     * @param _callback The callback to be executed when the API call finishes
     * @return The request call
     * @throws ApiException If fail to process the API call, e.g. serializing the request body object
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 201 </td><td> Объект JSON c ключом &#x60;jivo_integration&#x60; </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Некорректный запрос </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Не авторизован </td><td>  -  </td></tr>
        <tr><td> 403 </td><td> Запрещено </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Не найдено </td><td>  -  </td></tr>
        <tr><td> 409 </td><td> Конфликт </td><td>  -  </td></tr>
        <tr><td> 429 </td><td> Слишком много запросов </td><td>  -  </td></tr>
        <tr><td> 500 </td><td> Внутренняя ошибка сервера </td><td>  -  </td></tr>
     </table>
     */
    public okhttp3.Call createAgentIntegrationJivoAsync(Integer agentId, CreateJivoIntegration createJivoIntegration, final ApiCallback<GetAgentIntegrationJivo200Response> _callback) throws ApiException {

        okhttp3.Call localVarCall = createAgentIntegrationJivoValidateBeforeCall(agentId, createJivoIntegration, _callback);
        Type localVarReturnType = new TypeToken<GetAgentIntegrationJivo200Response>(){}.getType();
        localVarApiClient.executeAsync(localVarCall, localVarReturnType, _callback);
        return localVarCall;
    }
    /**
     * Build call for deleteAgentBotMaxBot
     * @param botId ID бота (поле &#x60;id&#x60; из ответа) (required)
     * @param _callback Callback for upload/download progress
     * @return Call to execute
     * @throws ApiException If fail to serialize the request body object
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 204 </td><td> Бот MAX отключен </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Некорректный запрос </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Не авторизован </td><td>  -  </td></tr>
        <tr><td> 403 </td><td> Запрещено </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Не найдено </td><td>  -  </td></tr>
        <tr><td> 429 </td><td> Слишком много запросов </td><td>  -  </td></tr>
        <tr><td> 500 </td><td> Внутренняя ошибка сервера </td><td>  -  </td></tr>
     </table>
     */
    public okhttp3.Call deleteAgentBotMaxBotCall(UUID botId, final ApiCallback _callback) throws ApiException {
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
        String localVarPath = "/api/v1/cloud-ai/max-bots/{bot_id}"
            .replace("{" + "bot_id" + "}", localVarApiClient.escapeString(botId.toString()));

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
    private okhttp3.Call deleteAgentBotMaxBotValidateBeforeCall(UUID botId, final ApiCallback _callback) throws ApiException {
        // verify the required parameter 'botId' is set
        if (botId == null) {
            throw new ApiException("Missing the required parameter 'botId' when calling deleteAgentBotMaxBot(Async)");
        }

        return deleteAgentBotMaxBotCall(botId, _callback);

    }

    /**
     * Отключение бота MAX
     * Чтобы отключить бота MAX от агента, отправьте DELETE-запрос на &#x60;/api/v1/cloud-ai/max-bots/{bot_id}&#x60;.  Платформа отпишет бота от событий в MAX и удалит подключение. Сам бот в MAX не удаляется — его можно подключить снова.
     * @param botId ID бота (поле &#x60;id&#x60; из ответа) (required)
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 204 </td><td> Бот MAX отключен </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Некорректный запрос </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Не авторизован </td><td>  -  </td></tr>
        <tr><td> 403 </td><td> Запрещено </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Не найдено </td><td>  -  </td></tr>
        <tr><td> 429 </td><td> Слишком много запросов </td><td>  -  </td></tr>
        <tr><td> 500 </td><td> Внутренняя ошибка сервера </td><td>  -  </td></tr>
     </table>
     */
    public void deleteAgentBotMaxBot(UUID botId) throws ApiException {
        deleteAgentBotMaxBotWithHttpInfo(botId);
    }

    /**
     * Отключение бота MAX
     * Чтобы отключить бота MAX от агента, отправьте DELETE-запрос на &#x60;/api/v1/cloud-ai/max-bots/{bot_id}&#x60;.  Платформа отпишет бота от событий в MAX и удалит подключение. Сам бот в MAX не удаляется — его можно подключить снова.
     * @param botId ID бота (поле &#x60;id&#x60; из ответа) (required)
     * @return ApiResponse&lt;Void&gt;
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 204 </td><td> Бот MAX отключен </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Некорректный запрос </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Не авторизован </td><td>  -  </td></tr>
        <tr><td> 403 </td><td> Запрещено </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Не найдено </td><td>  -  </td></tr>
        <tr><td> 429 </td><td> Слишком много запросов </td><td>  -  </td></tr>
        <tr><td> 500 </td><td> Внутренняя ошибка сервера </td><td>  -  </td></tr>
     </table>
     */
    public ApiResponse<Void> deleteAgentBotMaxBotWithHttpInfo(UUID botId) throws ApiException {
        okhttp3.Call localVarCall = deleteAgentBotMaxBotValidateBeforeCall(botId, null);
        return localVarApiClient.execute(localVarCall);
    }

    /**
     * Отключение бота MAX (asynchronously)
     * Чтобы отключить бота MAX от агента, отправьте DELETE-запрос на &#x60;/api/v1/cloud-ai/max-bots/{bot_id}&#x60;.  Платформа отпишет бота от событий в MAX и удалит подключение. Сам бот в MAX не удаляется — его можно подключить снова.
     * @param botId ID бота (поле &#x60;id&#x60; из ответа) (required)
     * @param _callback The callback to be executed when the API call finishes
     * @return The request call
     * @throws ApiException If fail to process the API call, e.g. serializing the request body object
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 204 </td><td> Бот MAX отключен </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Некорректный запрос </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Не авторизован </td><td>  -  </td></tr>
        <tr><td> 403 </td><td> Запрещено </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Не найдено </td><td>  -  </td></tr>
        <tr><td> 429 </td><td> Слишком много запросов </td><td>  -  </td></tr>
        <tr><td> 500 </td><td> Внутренняя ошибка сервера </td><td>  -  </td></tr>
     </table>
     */
    public okhttp3.Call deleteAgentBotMaxBotAsync(UUID botId, final ApiCallback<Void> _callback) throws ApiException {

        okhttp3.Call localVarCall = deleteAgentBotMaxBotValidateBeforeCall(botId, _callback);
        localVarApiClient.executeAsync(localVarCall, _callback);
        return localVarCall;
    }
    /**
     * Build call for deleteAgentBotTelegramBot
     * @param botId ID бота (поле &#x60;id&#x60; из ответа) (required)
     * @param _callback Callback for upload/download progress
     * @return Call to execute
     * @throws ApiException If fail to serialize the request body object
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 204 </td><td> Telegram-бот отключен </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Некорректный запрос </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Не авторизован </td><td>  -  </td></tr>
        <tr><td> 403 </td><td> Запрещено </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Не найдено </td><td>  -  </td></tr>
        <tr><td> 429 </td><td> Слишком много запросов </td><td>  -  </td></tr>
        <tr><td> 500 </td><td> Внутренняя ошибка сервера </td><td>  -  </td></tr>
     </table>
     */
    public okhttp3.Call deleteAgentBotTelegramBotCall(UUID botId, final ApiCallback _callback) throws ApiException {
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
        String localVarPath = "/api/v1/cloud-ai/bots/{bot_id}"
            .replace("{" + "bot_id" + "}", localVarApiClient.escapeString(botId.toString()));

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
    private okhttp3.Call deleteAgentBotTelegramBotValidateBeforeCall(UUID botId, final ApiCallback _callback) throws ApiException {
        // verify the required parameter 'botId' is set
        if (botId == null) {
            throw new ApiException("Missing the required parameter 'botId' when calling deleteAgentBotTelegramBot(Async)");
        }

        return deleteAgentBotTelegramBotCall(botId, _callback);

    }

    /**
     * Отключение Telegram-бота
     * Чтобы отключить Telegram-бота от агента, отправьте DELETE-запрос на &#x60;/api/v1/cloud-ai/bots/{bot_id}&#x60;.  Платформа снимет вебхук бота в Telegram и удалит подключение. Сам бот в Telegram не удаляется — его можно подключить снова.
     * @param botId ID бота (поле &#x60;id&#x60; из ответа) (required)
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 204 </td><td> Telegram-бот отключен </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Некорректный запрос </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Не авторизован </td><td>  -  </td></tr>
        <tr><td> 403 </td><td> Запрещено </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Не найдено </td><td>  -  </td></tr>
        <tr><td> 429 </td><td> Слишком много запросов </td><td>  -  </td></tr>
        <tr><td> 500 </td><td> Внутренняя ошибка сервера </td><td>  -  </td></tr>
     </table>
     */
    public void deleteAgentBotTelegramBot(UUID botId) throws ApiException {
        deleteAgentBotTelegramBotWithHttpInfo(botId);
    }

    /**
     * Отключение Telegram-бота
     * Чтобы отключить Telegram-бота от агента, отправьте DELETE-запрос на &#x60;/api/v1/cloud-ai/bots/{bot_id}&#x60;.  Платформа снимет вебхук бота в Telegram и удалит подключение. Сам бот в Telegram не удаляется — его можно подключить снова.
     * @param botId ID бота (поле &#x60;id&#x60; из ответа) (required)
     * @return ApiResponse&lt;Void&gt;
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 204 </td><td> Telegram-бот отключен </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Некорректный запрос </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Не авторизован </td><td>  -  </td></tr>
        <tr><td> 403 </td><td> Запрещено </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Не найдено </td><td>  -  </td></tr>
        <tr><td> 429 </td><td> Слишком много запросов </td><td>  -  </td></tr>
        <tr><td> 500 </td><td> Внутренняя ошибка сервера </td><td>  -  </td></tr>
     </table>
     */
    public ApiResponse<Void> deleteAgentBotTelegramBotWithHttpInfo(UUID botId) throws ApiException {
        okhttp3.Call localVarCall = deleteAgentBotTelegramBotValidateBeforeCall(botId, null);
        return localVarApiClient.execute(localVarCall);
    }

    /**
     * Отключение Telegram-бота (asynchronously)
     * Чтобы отключить Telegram-бота от агента, отправьте DELETE-запрос на &#x60;/api/v1/cloud-ai/bots/{bot_id}&#x60;.  Платформа снимет вебхук бота в Telegram и удалит подключение. Сам бот в Telegram не удаляется — его можно подключить снова.
     * @param botId ID бота (поле &#x60;id&#x60; из ответа) (required)
     * @param _callback The callback to be executed when the API call finishes
     * @return The request call
     * @throws ApiException If fail to process the API call, e.g. serializing the request body object
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 204 </td><td> Telegram-бот отключен </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Некорректный запрос </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Не авторизован </td><td>  -  </td></tr>
        <tr><td> 403 </td><td> Запрещено </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Не найдено </td><td>  -  </td></tr>
        <tr><td> 429 </td><td> Слишком много запросов </td><td>  -  </td></tr>
        <tr><td> 500 </td><td> Внутренняя ошибка сервера </td><td>  -  </td></tr>
     </table>
     */
    public okhttp3.Call deleteAgentBotTelegramBotAsync(UUID botId, final ApiCallback<Void> _callback) throws ApiException {

        okhttp3.Call localVarCall = deleteAgentBotTelegramBotValidateBeforeCall(botId, _callback);
        localVarApiClient.executeAsync(localVarCall, _callback);
        return localVarCall;
    }
    /**
     * Build call for deleteAgentIntegrationCrisp
     * @param agentId ID агента (required)
     * @param _callback Callback for upload/download progress
     * @return Call to execute
     * @throws ApiException If fail to serialize the request body object
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 204 </td><td> Интеграция с Crisp удалена </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Некорректный запрос </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Не авторизован </td><td>  -  </td></tr>
        <tr><td> 403 </td><td> Запрещено </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Не найдено </td><td>  -  </td></tr>
        <tr><td> 429 </td><td> Слишком много запросов </td><td>  -  </td></tr>
        <tr><td> 500 </td><td> Внутренняя ошибка сервера </td><td>  -  </td></tr>
     </table>
     */
    public okhttp3.Call deleteAgentIntegrationCrispCall(Integer agentId, final ApiCallback _callback) throws ApiException {
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
        String localVarPath = "/api/v1/cloud-ai/agent/{agent_id}/crisp"
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
        return localVarApiClient.buildCall(basePath, localVarPath, "DELETE", localVarQueryParams, localVarCollectionQueryParams, localVarPostBody, localVarHeaderParams, localVarCookieParams, localVarFormParams, localVarAuthNames, _callback);
    }

    @SuppressWarnings("rawtypes")
    private okhttp3.Call deleteAgentIntegrationCrispValidateBeforeCall(Integer agentId, final ApiCallback _callback) throws ApiException {
        // verify the required parameter 'agentId' is set
        if (agentId == null) {
            throw new ApiException("Missing the required parameter 'agentId' when calling deleteAgentIntegrationCrisp(Async)");
        }

        return deleteAgentIntegrationCrispCall(agentId, _callback);

    }

    /**
     * Отключение агента от Crisp
     * Чтобы отключить AI агента от Crisp, отправьте DELETE-запрос на &#x60;/api/v1/cloud-ai/agent/{agent_id}/crisp&#x60;.  После удаления интеграции агент перестает отвечать в чатах Crisp. Вебхук в настройках Crisp можно удалить вручную.
     * @param agentId ID агента (required)
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 204 </td><td> Интеграция с Crisp удалена </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Некорректный запрос </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Не авторизован </td><td>  -  </td></tr>
        <tr><td> 403 </td><td> Запрещено </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Не найдено </td><td>  -  </td></tr>
        <tr><td> 429 </td><td> Слишком много запросов </td><td>  -  </td></tr>
        <tr><td> 500 </td><td> Внутренняя ошибка сервера </td><td>  -  </td></tr>
     </table>
     */
    public void deleteAgentIntegrationCrisp(Integer agentId) throws ApiException {
        deleteAgentIntegrationCrispWithHttpInfo(agentId);
    }

    /**
     * Отключение агента от Crisp
     * Чтобы отключить AI агента от Crisp, отправьте DELETE-запрос на &#x60;/api/v1/cloud-ai/agent/{agent_id}/crisp&#x60;.  После удаления интеграции агент перестает отвечать в чатах Crisp. Вебхук в настройках Crisp можно удалить вручную.
     * @param agentId ID агента (required)
     * @return ApiResponse&lt;Void&gt;
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 204 </td><td> Интеграция с Crisp удалена </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Некорректный запрос </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Не авторизован </td><td>  -  </td></tr>
        <tr><td> 403 </td><td> Запрещено </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Не найдено </td><td>  -  </td></tr>
        <tr><td> 429 </td><td> Слишком много запросов </td><td>  -  </td></tr>
        <tr><td> 500 </td><td> Внутренняя ошибка сервера </td><td>  -  </td></tr>
     </table>
     */
    public ApiResponse<Void> deleteAgentIntegrationCrispWithHttpInfo(Integer agentId) throws ApiException {
        okhttp3.Call localVarCall = deleteAgentIntegrationCrispValidateBeforeCall(agentId, null);
        return localVarApiClient.execute(localVarCall);
    }

    /**
     * Отключение агента от Crisp (asynchronously)
     * Чтобы отключить AI агента от Crisp, отправьте DELETE-запрос на &#x60;/api/v1/cloud-ai/agent/{agent_id}/crisp&#x60;.  После удаления интеграции агент перестает отвечать в чатах Crisp. Вебхук в настройках Crisp можно удалить вручную.
     * @param agentId ID агента (required)
     * @param _callback The callback to be executed when the API call finishes
     * @return The request call
     * @throws ApiException If fail to process the API call, e.g. serializing the request body object
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 204 </td><td> Интеграция с Crisp удалена </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Некорректный запрос </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Не авторизован </td><td>  -  </td></tr>
        <tr><td> 403 </td><td> Запрещено </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Не найдено </td><td>  -  </td></tr>
        <tr><td> 429 </td><td> Слишком много запросов </td><td>  -  </td></tr>
        <tr><td> 500 </td><td> Внутренняя ошибка сервера </td><td>  -  </td></tr>
     </table>
     */
    public okhttp3.Call deleteAgentIntegrationCrispAsync(Integer agentId, final ApiCallback<Void> _callback) throws ApiException {

        okhttp3.Call localVarCall = deleteAgentIntegrationCrispValidateBeforeCall(agentId, _callback);
        localVarApiClient.executeAsync(localVarCall, _callback);
        return localVarCall;
    }
    /**
     * Build call for deleteAgentIntegrationJivo
     * @param agentId ID агента (required)
     * @param _callback Callback for upload/download progress
     * @return Call to execute
     * @throws ApiException If fail to serialize the request body object
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 204 </td><td> Интеграция с Jivo удалена </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Некорректный запрос </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Не авторизован </td><td>  -  </td></tr>
        <tr><td> 403 </td><td> Запрещено </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Не найдено </td><td>  -  </td></tr>
        <tr><td> 429 </td><td> Слишком много запросов </td><td>  -  </td></tr>
        <tr><td> 500 </td><td> Внутренняя ошибка сервера </td><td>  -  </td></tr>
     </table>
     */
    public okhttp3.Call deleteAgentIntegrationJivoCall(Integer agentId, final ApiCallback _callback) throws ApiException {
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
        String localVarPath = "/api/v1/cloud-ai/agent/{agent_id}/jivo"
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
        return localVarApiClient.buildCall(basePath, localVarPath, "DELETE", localVarQueryParams, localVarCollectionQueryParams, localVarPostBody, localVarHeaderParams, localVarCookieParams, localVarFormParams, localVarAuthNames, _callback);
    }

    @SuppressWarnings("rawtypes")
    private okhttp3.Call deleteAgentIntegrationJivoValidateBeforeCall(Integer agentId, final ApiCallback _callback) throws ApiException {
        // verify the required parameter 'agentId' is set
        if (agentId == null) {
            throw new ApiException("Missing the required parameter 'agentId' when calling deleteAgentIntegrationJivo(Async)");
        }

        return deleteAgentIntegrationJivoCall(agentId, _callback);

    }

    /**
     * Отключение агента от Jivo
     * Чтобы отключить AI агента от Jivo, отправьте DELETE-запрос на &#x60;/api/v1/cloud-ai/agent/{agent_id}/jivo&#x60;.  После удаления интеграции агент перестает отвечать на события Jivo. Настройки бота в самом Jivo не меняются.
     * @param agentId ID агента (required)
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 204 </td><td> Интеграция с Jivo удалена </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Некорректный запрос </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Не авторизован </td><td>  -  </td></tr>
        <tr><td> 403 </td><td> Запрещено </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Не найдено </td><td>  -  </td></tr>
        <tr><td> 429 </td><td> Слишком много запросов </td><td>  -  </td></tr>
        <tr><td> 500 </td><td> Внутренняя ошибка сервера </td><td>  -  </td></tr>
     </table>
     */
    public void deleteAgentIntegrationJivo(Integer agentId) throws ApiException {
        deleteAgentIntegrationJivoWithHttpInfo(agentId);
    }

    /**
     * Отключение агента от Jivo
     * Чтобы отключить AI агента от Jivo, отправьте DELETE-запрос на &#x60;/api/v1/cloud-ai/agent/{agent_id}/jivo&#x60;.  После удаления интеграции агент перестает отвечать на события Jivo. Настройки бота в самом Jivo не меняются.
     * @param agentId ID агента (required)
     * @return ApiResponse&lt;Void&gt;
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 204 </td><td> Интеграция с Jivo удалена </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Некорректный запрос </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Не авторизован </td><td>  -  </td></tr>
        <tr><td> 403 </td><td> Запрещено </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Не найдено </td><td>  -  </td></tr>
        <tr><td> 429 </td><td> Слишком много запросов </td><td>  -  </td></tr>
        <tr><td> 500 </td><td> Внутренняя ошибка сервера </td><td>  -  </td></tr>
     </table>
     */
    public ApiResponse<Void> deleteAgentIntegrationJivoWithHttpInfo(Integer agentId) throws ApiException {
        okhttp3.Call localVarCall = deleteAgentIntegrationJivoValidateBeforeCall(agentId, null);
        return localVarApiClient.execute(localVarCall);
    }

    /**
     * Отключение агента от Jivo (asynchronously)
     * Чтобы отключить AI агента от Jivo, отправьте DELETE-запрос на &#x60;/api/v1/cloud-ai/agent/{agent_id}/jivo&#x60;.  После удаления интеграции агент перестает отвечать на события Jivo. Настройки бота в самом Jivo не меняются.
     * @param agentId ID агента (required)
     * @param _callback The callback to be executed when the API call finishes
     * @return The request call
     * @throws ApiException If fail to process the API call, e.g. serializing the request body object
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 204 </td><td> Интеграция с Jivo удалена </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Некорректный запрос </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Не авторизован </td><td>  -  </td></tr>
        <tr><td> 403 </td><td> Запрещено </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Не найдено </td><td>  -  </td></tr>
        <tr><td> 429 </td><td> Слишком много запросов </td><td>  -  </td></tr>
        <tr><td> 500 </td><td> Внутренняя ошибка сервера </td><td>  -  </td></tr>
     </table>
     */
    public okhttp3.Call deleteAgentIntegrationJivoAsync(Integer agentId, final ApiCallback<Void> _callback) throws ApiException {

        okhttp3.Call localVarCall = deleteAgentIntegrationJivoValidateBeforeCall(agentId, _callback);
        localVarApiClient.executeAsync(localVarCall, _callback);
        return localVarCall;
    }
    /**
     * Build call for getAgentBotMaxBot
     * @param botId ID бота (поле &#x60;id&#x60; из ответа) (required)
     * @param _callback Callback for upload/download progress
     * @return Call to execute
     * @throws ApiException If fail to serialize the request body object
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> Объект JSON c ключом &#x60;bot&#x60; </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Некорректный запрос </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Не авторизован </td><td>  -  </td></tr>
        <tr><td> 403 </td><td> Запрещено </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Не найдено </td><td>  -  </td></tr>
        <tr><td> 429 </td><td> Слишком много запросов </td><td>  -  </td></tr>
        <tr><td> 500 </td><td> Внутренняя ошибка сервера </td><td>  -  </td></tr>
     </table>
     */
    public okhttp3.Call getAgentBotMaxBotCall(UUID botId, final ApiCallback _callback) throws ApiException {
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
        String localVarPath = "/api/v1/cloud-ai/max-bots/{bot_id}"
            .replace("{" + "bot_id" + "}", localVarApiClient.escapeString(botId.toString()));

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
    private okhttp3.Call getAgentBotMaxBotValidateBeforeCall(UUID botId, final ApiCallback _callback) throws ApiException {
        // verify the required parameter 'botId' is set
        if (botId == null) {
            throw new ApiException("Missing the required parameter 'botId' when calling getAgentBotMaxBot(Async)");
        }

        return getAgentBotMaxBotCall(botId, _callback);

    }

    /**
     * Получение информации о боте MAX
     * Чтобы получить информацию о боте MAX, отправьте GET-запрос на &#x60;/api/v1/cloud-ai/max-bots/{bot_id}&#x60;.  Тело ответа будет представлять собой объект JSON с ключом &#x60;bot&#x60;.
     * @param botId ID бота (поле &#x60;id&#x60; из ответа) (required)
     * @return CreateAgentBotTelegramBot201Response
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> Объект JSON c ключом &#x60;bot&#x60; </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Некорректный запрос </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Не авторизован </td><td>  -  </td></tr>
        <tr><td> 403 </td><td> Запрещено </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Не найдено </td><td>  -  </td></tr>
        <tr><td> 429 </td><td> Слишком много запросов </td><td>  -  </td></tr>
        <tr><td> 500 </td><td> Внутренняя ошибка сервера </td><td>  -  </td></tr>
     </table>
     */
    public CreateAgentBotTelegramBot201Response getAgentBotMaxBot(UUID botId) throws ApiException {
        ApiResponse<CreateAgentBotTelegramBot201Response> localVarResp = getAgentBotMaxBotWithHttpInfo(botId);
        return localVarResp.getData();
    }

    /**
     * Получение информации о боте MAX
     * Чтобы получить информацию о боте MAX, отправьте GET-запрос на &#x60;/api/v1/cloud-ai/max-bots/{bot_id}&#x60;.  Тело ответа будет представлять собой объект JSON с ключом &#x60;bot&#x60;.
     * @param botId ID бота (поле &#x60;id&#x60; из ответа) (required)
     * @return ApiResponse&lt;CreateAgentBotTelegramBot201Response&gt;
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> Объект JSON c ключом &#x60;bot&#x60; </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Некорректный запрос </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Не авторизован </td><td>  -  </td></tr>
        <tr><td> 403 </td><td> Запрещено </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Не найдено </td><td>  -  </td></tr>
        <tr><td> 429 </td><td> Слишком много запросов </td><td>  -  </td></tr>
        <tr><td> 500 </td><td> Внутренняя ошибка сервера </td><td>  -  </td></tr>
     </table>
     */
    public ApiResponse<CreateAgentBotTelegramBot201Response> getAgentBotMaxBotWithHttpInfo(UUID botId) throws ApiException {
        okhttp3.Call localVarCall = getAgentBotMaxBotValidateBeforeCall(botId, null);
        Type localVarReturnType = new TypeToken<CreateAgentBotTelegramBot201Response>(){}.getType();
        return localVarApiClient.execute(localVarCall, localVarReturnType);
    }

    /**
     * Получение информации о боте MAX (asynchronously)
     * Чтобы получить информацию о боте MAX, отправьте GET-запрос на &#x60;/api/v1/cloud-ai/max-bots/{bot_id}&#x60;.  Тело ответа будет представлять собой объект JSON с ключом &#x60;bot&#x60;.
     * @param botId ID бота (поле &#x60;id&#x60; из ответа) (required)
     * @param _callback The callback to be executed when the API call finishes
     * @return The request call
     * @throws ApiException If fail to process the API call, e.g. serializing the request body object
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> Объект JSON c ключом &#x60;bot&#x60; </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Некорректный запрос </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Не авторизован </td><td>  -  </td></tr>
        <tr><td> 403 </td><td> Запрещено </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Не найдено </td><td>  -  </td></tr>
        <tr><td> 429 </td><td> Слишком много запросов </td><td>  -  </td></tr>
        <tr><td> 500 </td><td> Внутренняя ошибка сервера </td><td>  -  </td></tr>
     </table>
     */
    public okhttp3.Call getAgentBotMaxBotAsync(UUID botId, final ApiCallback<CreateAgentBotTelegramBot201Response> _callback) throws ApiException {

        okhttp3.Call localVarCall = getAgentBotMaxBotValidateBeforeCall(botId, _callback);
        Type localVarReturnType = new TypeToken<CreateAgentBotTelegramBot201Response>(){}.getType();
        localVarApiClient.executeAsync(localVarCall, localVarReturnType, _callback);
        return localVarCall;
    }
    /**
     * Build call for getAgentBotMaxBots
     * @param _callback Callback for upload/download progress
     * @return Call to execute
     * @throws ApiException If fail to serialize the request body object
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> Объект JSON c ключами &#x60;bots&#x60; и &#x60;meta&#x60; </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Некорректный запрос </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Не авторизован </td><td>  -  </td></tr>
        <tr><td> 403 </td><td> Запрещено </td><td>  -  </td></tr>
        <tr><td> 429 </td><td> Слишком много запросов </td><td>  -  </td></tr>
        <tr><td> 500 </td><td> Внутренняя ошибка сервера </td><td>  -  </td></tr>
     </table>
     */
    public okhttp3.Call getAgentBotMaxBotsCall(final ApiCallback _callback) throws ApiException {
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
        String localVarPath = "/api/v1/cloud-ai/max-bots";

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
    private okhttp3.Call getAgentBotMaxBotsValidateBeforeCall(final ApiCallback _callback) throws ApiException {
        return getAgentBotMaxBotsCall(_callback);

    }

    /**
     * Получение списка ботов MAX
     * Чтобы получить список ботов MAX, подключенных к агентам аккаунта, отправьте GET-запрос на &#x60;/api/v1/cloud-ai/max-bots&#x60;.  Тело ответа будет представлять собой объект JSON с ключами &#x60;bots&#x60; и &#x60;meta&#x60;.
     * @return GetAgentBotMaxBots200Response
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> Объект JSON c ключами &#x60;bots&#x60; и &#x60;meta&#x60; </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Некорректный запрос </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Не авторизован </td><td>  -  </td></tr>
        <tr><td> 403 </td><td> Запрещено </td><td>  -  </td></tr>
        <tr><td> 429 </td><td> Слишком много запросов </td><td>  -  </td></tr>
        <tr><td> 500 </td><td> Внутренняя ошибка сервера </td><td>  -  </td></tr>
     </table>
     */
    public GetAgentBotMaxBots200Response getAgentBotMaxBots() throws ApiException {
        ApiResponse<GetAgentBotMaxBots200Response> localVarResp = getAgentBotMaxBotsWithHttpInfo();
        return localVarResp.getData();
    }

    /**
     * Получение списка ботов MAX
     * Чтобы получить список ботов MAX, подключенных к агентам аккаунта, отправьте GET-запрос на &#x60;/api/v1/cloud-ai/max-bots&#x60;.  Тело ответа будет представлять собой объект JSON с ключами &#x60;bots&#x60; и &#x60;meta&#x60;.
     * @return ApiResponse&lt;GetAgentBotMaxBots200Response&gt;
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> Объект JSON c ключами &#x60;bots&#x60; и &#x60;meta&#x60; </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Некорректный запрос </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Не авторизован </td><td>  -  </td></tr>
        <tr><td> 403 </td><td> Запрещено </td><td>  -  </td></tr>
        <tr><td> 429 </td><td> Слишком много запросов </td><td>  -  </td></tr>
        <tr><td> 500 </td><td> Внутренняя ошибка сервера </td><td>  -  </td></tr>
     </table>
     */
    public ApiResponse<GetAgentBotMaxBots200Response> getAgentBotMaxBotsWithHttpInfo() throws ApiException {
        okhttp3.Call localVarCall = getAgentBotMaxBotsValidateBeforeCall(null);
        Type localVarReturnType = new TypeToken<GetAgentBotMaxBots200Response>(){}.getType();
        return localVarApiClient.execute(localVarCall, localVarReturnType);
    }

    /**
     * Получение списка ботов MAX (asynchronously)
     * Чтобы получить список ботов MAX, подключенных к агентам аккаунта, отправьте GET-запрос на &#x60;/api/v1/cloud-ai/max-bots&#x60;.  Тело ответа будет представлять собой объект JSON с ключами &#x60;bots&#x60; и &#x60;meta&#x60;.
     * @param _callback The callback to be executed when the API call finishes
     * @return The request call
     * @throws ApiException If fail to process the API call, e.g. serializing the request body object
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> Объект JSON c ключами &#x60;bots&#x60; и &#x60;meta&#x60; </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Некорректный запрос </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Не авторизован </td><td>  -  </td></tr>
        <tr><td> 403 </td><td> Запрещено </td><td>  -  </td></tr>
        <tr><td> 429 </td><td> Слишком много запросов </td><td>  -  </td></tr>
        <tr><td> 500 </td><td> Внутренняя ошибка сервера </td><td>  -  </td></tr>
     </table>
     */
    public okhttp3.Call getAgentBotMaxBotsAsync(final ApiCallback<GetAgentBotMaxBots200Response> _callback) throws ApiException {

        okhttp3.Call localVarCall = getAgentBotMaxBotsValidateBeforeCall(_callback);
        Type localVarReturnType = new TypeToken<GetAgentBotMaxBots200Response>(){}.getType();
        localVarApiClient.executeAsync(localVarCall, localVarReturnType, _callback);
        return localVarCall;
    }
    /**
     * Build call for getAgentBotMaxBotsByAgent
     * @param agentId ID агента (required)
     * @param _callback Callback for upload/download progress
     * @return Call to execute
     * @throws ApiException If fail to serialize the request body object
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> Объект JSON c ключами &#x60;bots&#x60; и &#x60;meta&#x60; </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Некорректный запрос </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Не авторизован </td><td>  -  </td></tr>
        <tr><td> 403 </td><td> Запрещено </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Не найдено </td><td>  -  </td></tr>
        <tr><td> 429 </td><td> Слишком много запросов </td><td>  -  </td></tr>
        <tr><td> 500 </td><td> Внутренняя ошибка сервера </td><td>  -  </td></tr>
     </table>
     */
    public okhttp3.Call getAgentBotMaxBotsByAgentCall(Integer agentId, final ApiCallback _callback) throws ApiException {
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
        String localVarPath = "/api/v1/cloud-ai/agents/{agent_id}/max-bots"
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
    private okhttp3.Call getAgentBotMaxBotsByAgentValidateBeforeCall(Integer agentId, final ApiCallback _callback) throws ApiException {
        // verify the required parameter 'agentId' is set
        if (agentId == null) {
            throw new ApiException("Missing the required parameter 'agentId' when calling getAgentBotMaxBotsByAgent(Async)");
        }

        return getAgentBotMaxBotsByAgentCall(agentId, _callback);

    }

    /**
     * Получение списка ботов MAX агента
     * Чтобы получить список ботов MAX, подключенных к AI агенту, отправьте GET-запрос на &#x60;/api/v1/cloud-ai/agents/{agent_id}/max-bots&#x60;.  Тело ответа будет представлять собой объект JSON с ключами &#x60;bots&#x60; и &#x60;meta&#x60;.
     * @param agentId ID агента (required)
     * @return GetAgentBotMaxBotsByAgent200Response
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> Объект JSON c ключами &#x60;bots&#x60; и &#x60;meta&#x60; </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Некорректный запрос </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Не авторизован </td><td>  -  </td></tr>
        <tr><td> 403 </td><td> Запрещено </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Не найдено </td><td>  -  </td></tr>
        <tr><td> 429 </td><td> Слишком много запросов </td><td>  -  </td></tr>
        <tr><td> 500 </td><td> Внутренняя ошибка сервера </td><td>  -  </td></tr>
     </table>
     */
    public GetAgentBotMaxBotsByAgent200Response getAgentBotMaxBotsByAgent(Integer agentId) throws ApiException {
        ApiResponse<GetAgentBotMaxBotsByAgent200Response> localVarResp = getAgentBotMaxBotsByAgentWithHttpInfo(agentId);
        return localVarResp.getData();
    }

    /**
     * Получение списка ботов MAX агента
     * Чтобы получить список ботов MAX, подключенных к AI агенту, отправьте GET-запрос на &#x60;/api/v1/cloud-ai/agents/{agent_id}/max-bots&#x60;.  Тело ответа будет представлять собой объект JSON с ключами &#x60;bots&#x60; и &#x60;meta&#x60;.
     * @param agentId ID агента (required)
     * @return ApiResponse&lt;GetAgentBotMaxBotsByAgent200Response&gt;
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> Объект JSON c ключами &#x60;bots&#x60; и &#x60;meta&#x60; </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Некорректный запрос </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Не авторизован </td><td>  -  </td></tr>
        <tr><td> 403 </td><td> Запрещено </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Не найдено </td><td>  -  </td></tr>
        <tr><td> 429 </td><td> Слишком много запросов </td><td>  -  </td></tr>
        <tr><td> 500 </td><td> Внутренняя ошибка сервера </td><td>  -  </td></tr>
     </table>
     */
    public ApiResponse<GetAgentBotMaxBotsByAgent200Response> getAgentBotMaxBotsByAgentWithHttpInfo(Integer agentId) throws ApiException {
        okhttp3.Call localVarCall = getAgentBotMaxBotsByAgentValidateBeforeCall(agentId, null);
        Type localVarReturnType = new TypeToken<GetAgentBotMaxBotsByAgent200Response>(){}.getType();
        return localVarApiClient.execute(localVarCall, localVarReturnType);
    }

    /**
     * Получение списка ботов MAX агента (asynchronously)
     * Чтобы получить список ботов MAX, подключенных к AI агенту, отправьте GET-запрос на &#x60;/api/v1/cloud-ai/agents/{agent_id}/max-bots&#x60;.  Тело ответа будет представлять собой объект JSON с ключами &#x60;bots&#x60; и &#x60;meta&#x60;.
     * @param agentId ID агента (required)
     * @param _callback The callback to be executed when the API call finishes
     * @return The request call
     * @throws ApiException If fail to process the API call, e.g. serializing the request body object
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> Объект JSON c ключами &#x60;bots&#x60; и &#x60;meta&#x60; </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Некорректный запрос </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Не авторизован </td><td>  -  </td></tr>
        <tr><td> 403 </td><td> Запрещено </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Не найдено </td><td>  -  </td></tr>
        <tr><td> 429 </td><td> Слишком много запросов </td><td>  -  </td></tr>
        <tr><td> 500 </td><td> Внутренняя ошибка сервера </td><td>  -  </td></tr>
     </table>
     */
    public okhttp3.Call getAgentBotMaxBotsByAgentAsync(Integer agentId, final ApiCallback<GetAgentBotMaxBotsByAgent200Response> _callback) throws ApiException {

        okhttp3.Call localVarCall = getAgentBotMaxBotsByAgentValidateBeforeCall(agentId, _callback);
        Type localVarReturnType = new TypeToken<GetAgentBotMaxBotsByAgent200Response>(){}.getType();
        localVarApiClient.executeAsync(localVarCall, localVarReturnType, _callback);
        return localVarCall;
    }
    /**
     * Build call for getAgentBotTelegramBot
     * @param botId ID бота (поле &#x60;id&#x60; из ответа) (required)
     * @param _callback Callback for upload/download progress
     * @return Call to execute
     * @throws ApiException If fail to serialize the request body object
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> Объект JSON c ключом &#x60;bot&#x60; </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Некорректный запрос </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Не авторизован </td><td>  -  </td></tr>
        <tr><td> 403 </td><td> Запрещено </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Не найдено </td><td>  -  </td></tr>
        <tr><td> 429 </td><td> Слишком много запросов </td><td>  -  </td></tr>
        <tr><td> 500 </td><td> Внутренняя ошибка сервера </td><td>  -  </td></tr>
     </table>
     */
    public okhttp3.Call getAgentBotTelegramBotCall(UUID botId, final ApiCallback _callback) throws ApiException {
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
        String localVarPath = "/api/v1/cloud-ai/bots/{bot_id}"
            .replace("{" + "bot_id" + "}", localVarApiClient.escapeString(botId.toString()));

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
    private okhttp3.Call getAgentBotTelegramBotValidateBeforeCall(UUID botId, final ApiCallback _callback) throws ApiException {
        // verify the required parameter 'botId' is set
        if (botId == null) {
            throw new ApiException("Missing the required parameter 'botId' when calling getAgentBotTelegramBot(Async)");
        }

        return getAgentBotTelegramBotCall(botId, _callback);

    }

    /**
     * Получение информации о Telegram-боте
     * Чтобы получить информацию о Telegram-боте, отправьте GET-запрос на &#x60;/api/v1/cloud-ai/bots/{bot_id}&#x60;.  Тело ответа будет представлять собой объект JSON с ключом &#x60;bot&#x60;.
     * @param botId ID бота (поле &#x60;id&#x60; из ответа) (required)
     * @return CreateAgentBotTelegramBot201Response
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> Объект JSON c ключом &#x60;bot&#x60; </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Некорректный запрос </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Не авторизован </td><td>  -  </td></tr>
        <tr><td> 403 </td><td> Запрещено </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Не найдено </td><td>  -  </td></tr>
        <tr><td> 429 </td><td> Слишком много запросов </td><td>  -  </td></tr>
        <tr><td> 500 </td><td> Внутренняя ошибка сервера </td><td>  -  </td></tr>
     </table>
     */
    public CreateAgentBotTelegramBot201Response getAgentBotTelegramBot(UUID botId) throws ApiException {
        ApiResponse<CreateAgentBotTelegramBot201Response> localVarResp = getAgentBotTelegramBotWithHttpInfo(botId);
        return localVarResp.getData();
    }

    /**
     * Получение информации о Telegram-боте
     * Чтобы получить информацию о Telegram-боте, отправьте GET-запрос на &#x60;/api/v1/cloud-ai/bots/{bot_id}&#x60;.  Тело ответа будет представлять собой объект JSON с ключом &#x60;bot&#x60;.
     * @param botId ID бота (поле &#x60;id&#x60; из ответа) (required)
     * @return ApiResponse&lt;CreateAgentBotTelegramBot201Response&gt;
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> Объект JSON c ключом &#x60;bot&#x60; </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Некорректный запрос </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Не авторизован </td><td>  -  </td></tr>
        <tr><td> 403 </td><td> Запрещено </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Не найдено </td><td>  -  </td></tr>
        <tr><td> 429 </td><td> Слишком много запросов </td><td>  -  </td></tr>
        <tr><td> 500 </td><td> Внутренняя ошибка сервера </td><td>  -  </td></tr>
     </table>
     */
    public ApiResponse<CreateAgentBotTelegramBot201Response> getAgentBotTelegramBotWithHttpInfo(UUID botId) throws ApiException {
        okhttp3.Call localVarCall = getAgentBotTelegramBotValidateBeforeCall(botId, null);
        Type localVarReturnType = new TypeToken<CreateAgentBotTelegramBot201Response>(){}.getType();
        return localVarApiClient.execute(localVarCall, localVarReturnType);
    }

    /**
     * Получение информации о Telegram-боте (asynchronously)
     * Чтобы получить информацию о Telegram-боте, отправьте GET-запрос на &#x60;/api/v1/cloud-ai/bots/{bot_id}&#x60;.  Тело ответа будет представлять собой объект JSON с ключом &#x60;bot&#x60;.
     * @param botId ID бота (поле &#x60;id&#x60; из ответа) (required)
     * @param _callback The callback to be executed when the API call finishes
     * @return The request call
     * @throws ApiException If fail to process the API call, e.g. serializing the request body object
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> Объект JSON c ключом &#x60;bot&#x60; </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Некорректный запрос </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Не авторизован </td><td>  -  </td></tr>
        <tr><td> 403 </td><td> Запрещено </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Не найдено </td><td>  -  </td></tr>
        <tr><td> 429 </td><td> Слишком много запросов </td><td>  -  </td></tr>
        <tr><td> 500 </td><td> Внутренняя ошибка сервера </td><td>  -  </td></tr>
     </table>
     */
    public okhttp3.Call getAgentBotTelegramBotAsync(UUID botId, final ApiCallback<CreateAgentBotTelegramBot201Response> _callback) throws ApiException {

        okhttp3.Call localVarCall = getAgentBotTelegramBotValidateBeforeCall(botId, _callback);
        Type localVarReturnType = new TypeToken<CreateAgentBotTelegramBot201Response>(){}.getType();
        localVarApiClient.executeAsync(localVarCall, localVarReturnType, _callback);
        return localVarCall;
    }
    /**
     * Build call for getAgentBotTelegramBots
     * @param _callback Callback for upload/download progress
     * @return Call to execute
     * @throws ApiException If fail to serialize the request body object
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> Объект JSON c ключами &#x60;bots&#x60; и &#x60;meta&#x60; </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Некорректный запрос </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Не авторизован </td><td>  -  </td></tr>
        <tr><td> 403 </td><td> Запрещено </td><td>  -  </td></tr>
        <tr><td> 429 </td><td> Слишком много запросов </td><td>  -  </td></tr>
        <tr><td> 500 </td><td> Внутренняя ошибка сервера </td><td>  -  </td></tr>
     </table>
     */
    public okhttp3.Call getAgentBotTelegramBotsCall(final ApiCallback _callback) throws ApiException {
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
        String localVarPath = "/api/v1/cloud-ai/bots";

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
    private okhttp3.Call getAgentBotTelegramBotsValidateBeforeCall(final ApiCallback _callback) throws ApiException {
        return getAgentBotTelegramBotsCall(_callback);

    }

    /**
     * Получение списка Telegram-ботов
     * Чтобы получить список Telegram-ботов, подключенных к агентам аккаунта, отправьте GET-запрос на &#x60;/api/v1/cloud-ai/bots&#x60;.  Тело ответа будет представлять собой объект JSON с ключами &#x60;bots&#x60; и &#x60;meta&#x60;.
     * @return GetAgentBotTelegramBots200Response
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> Объект JSON c ключами &#x60;bots&#x60; и &#x60;meta&#x60; </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Некорректный запрос </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Не авторизован </td><td>  -  </td></tr>
        <tr><td> 403 </td><td> Запрещено </td><td>  -  </td></tr>
        <tr><td> 429 </td><td> Слишком много запросов </td><td>  -  </td></tr>
        <tr><td> 500 </td><td> Внутренняя ошибка сервера </td><td>  -  </td></tr>
     </table>
     */
    public GetAgentBotTelegramBots200Response getAgentBotTelegramBots() throws ApiException {
        ApiResponse<GetAgentBotTelegramBots200Response> localVarResp = getAgentBotTelegramBotsWithHttpInfo();
        return localVarResp.getData();
    }

    /**
     * Получение списка Telegram-ботов
     * Чтобы получить список Telegram-ботов, подключенных к агентам аккаунта, отправьте GET-запрос на &#x60;/api/v1/cloud-ai/bots&#x60;.  Тело ответа будет представлять собой объект JSON с ключами &#x60;bots&#x60; и &#x60;meta&#x60;.
     * @return ApiResponse&lt;GetAgentBotTelegramBots200Response&gt;
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> Объект JSON c ключами &#x60;bots&#x60; и &#x60;meta&#x60; </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Некорректный запрос </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Не авторизован </td><td>  -  </td></tr>
        <tr><td> 403 </td><td> Запрещено </td><td>  -  </td></tr>
        <tr><td> 429 </td><td> Слишком много запросов </td><td>  -  </td></tr>
        <tr><td> 500 </td><td> Внутренняя ошибка сервера </td><td>  -  </td></tr>
     </table>
     */
    public ApiResponse<GetAgentBotTelegramBots200Response> getAgentBotTelegramBotsWithHttpInfo() throws ApiException {
        okhttp3.Call localVarCall = getAgentBotTelegramBotsValidateBeforeCall(null);
        Type localVarReturnType = new TypeToken<GetAgentBotTelegramBots200Response>(){}.getType();
        return localVarApiClient.execute(localVarCall, localVarReturnType);
    }

    /**
     * Получение списка Telegram-ботов (asynchronously)
     * Чтобы получить список Telegram-ботов, подключенных к агентам аккаунта, отправьте GET-запрос на &#x60;/api/v1/cloud-ai/bots&#x60;.  Тело ответа будет представлять собой объект JSON с ключами &#x60;bots&#x60; и &#x60;meta&#x60;.
     * @param _callback The callback to be executed when the API call finishes
     * @return The request call
     * @throws ApiException If fail to process the API call, e.g. serializing the request body object
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> Объект JSON c ключами &#x60;bots&#x60; и &#x60;meta&#x60; </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Некорректный запрос </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Не авторизован </td><td>  -  </td></tr>
        <tr><td> 403 </td><td> Запрещено </td><td>  -  </td></tr>
        <tr><td> 429 </td><td> Слишком много запросов </td><td>  -  </td></tr>
        <tr><td> 500 </td><td> Внутренняя ошибка сервера </td><td>  -  </td></tr>
     </table>
     */
    public okhttp3.Call getAgentBotTelegramBotsAsync(final ApiCallback<GetAgentBotTelegramBots200Response> _callback) throws ApiException {

        okhttp3.Call localVarCall = getAgentBotTelegramBotsValidateBeforeCall(_callback);
        Type localVarReturnType = new TypeToken<GetAgentBotTelegramBots200Response>(){}.getType();
        localVarApiClient.executeAsync(localVarCall, localVarReturnType, _callback);
        return localVarCall;
    }
    /**
     * Build call for getAgentBotTelegramBotsByAgent
     * @param agentId ID агента (required)
     * @param _callback Callback for upload/download progress
     * @return Call to execute
     * @throws ApiException If fail to serialize the request body object
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> Объект JSON c ключами &#x60;bots&#x60; и &#x60;meta&#x60; </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Некорректный запрос </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Не авторизован </td><td>  -  </td></tr>
        <tr><td> 403 </td><td> Запрещено </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Не найдено </td><td>  -  </td></tr>
        <tr><td> 429 </td><td> Слишком много запросов </td><td>  -  </td></tr>
        <tr><td> 500 </td><td> Внутренняя ошибка сервера </td><td>  -  </td></tr>
     </table>
     */
    public okhttp3.Call getAgentBotTelegramBotsByAgentCall(Integer agentId, final ApiCallback _callback) throws ApiException {
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
        String localVarPath = "/api/v1/cloud-ai/agents/{agent_id}/bots"
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
    private okhttp3.Call getAgentBotTelegramBotsByAgentValidateBeforeCall(Integer agentId, final ApiCallback _callback) throws ApiException {
        // verify the required parameter 'agentId' is set
        if (agentId == null) {
            throw new ApiException("Missing the required parameter 'agentId' when calling getAgentBotTelegramBotsByAgent(Async)");
        }

        return getAgentBotTelegramBotsByAgentCall(agentId, _callback);

    }

    /**
     * Получение списка Telegram-ботов агента
     * Чтобы получить список Telegram-ботов, подключенных к AI агенту, отправьте GET-запрос на &#x60;/api/v1/cloud-ai/agents/{agent_id}/bots&#x60;.  Тело ответа будет представлять собой объект JSON с ключами &#x60;bots&#x60; и &#x60;meta&#x60;.
     * @param agentId ID агента (required)
     * @return GetAgentBotTelegramBotsByAgent200Response
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> Объект JSON c ключами &#x60;bots&#x60; и &#x60;meta&#x60; </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Некорректный запрос </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Не авторизован </td><td>  -  </td></tr>
        <tr><td> 403 </td><td> Запрещено </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Не найдено </td><td>  -  </td></tr>
        <tr><td> 429 </td><td> Слишком много запросов </td><td>  -  </td></tr>
        <tr><td> 500 </td><td> Внутренняя ошибка сервера </td><td>  -  </td></tr>
     </table>
     */
    public GetAgentBotTelegramBotsByAgent200Response getAgentBotTelegramBotsByAgent(Integer agentId) throws ApiException {
        ApiResponse<GetAgentBotTelegramBotsByAgent200Response> localVarResp = getAgentBotTelegramBotsByAgentWithHttpInfo(agentId);
        return localVarResp.getData();
    }

    /**
     * Получение списка Telegram-ботов агента
     * Чтобы получить список Telegram-ботов, подключенных к AI агенту, отправьте GET-запрос на &#x60;/api/v1/cloud-ai/agents/{agent_id}/bots&#x60;.  Тело ответа будет представлять собой объект JSON с ключами &#x60;bots&#x60; и &#x60;meta&#x60;.
     * @param agentId ID агента (required)
     * @return ApiResponse&lt;GetAgentBotTelegramBotsByAgent200Response&gt;
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> Объект JSON c ключами &#x60;bots&#x60; и &#x60;meta&#x60; </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Некорректный запрос </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Не авторизован </td><td>  -  </td></tr>
        <tr><td> 403 </td><td> Запрещено </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Не найдено </td><td>  -  </td></tr>
        <tr><td> 429 </td><td> Слишком много запросов </td><td>  -  </td></tr>
        <tr><td> 500 </td><td> Внутренняя ошибка сервера </td><td>  -  </td></tr>
     </table>
     */
    public ApiResponse<GetAgentBotTelegramBotsByAgent200Response> getAgentBotTelegramBotsByAgentWithHttpInfo(Integer agentId) throws ApiException {
        okhttp3.Call localVarCall = getAgentBotTelegramBotsByAgentValidateBeforeCall(agentId, null);
        Type localVarReturnType = new TypeToken<GetAgentBotTelegramBotsByAgent200Response>(){}.getType();
        return localVarApiClient.execute(localVarCall, localVarReturnType);
    }

    /**
     * Получение списка Telegram-ботов агента (asynchronously)
     * Чтобы получить список Telegram-ботов, подключенных к AI агенту, отправьте GET-запрос на &#x60;/api/v1/cloud-ai/agents/{agent_id}/bots&#x60;.  Тело ответа будет представлять собой объект JSON с ключами &#x60;bots&#x60; и &#x60;meta&#x60;.
     * @param agentId ID агента (required)
     * @param _callback The callback to be executed when the API call finishes
     * @return The request call
     * @throws ApiException If fail to process the API call, e.g. serializing the request body object
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> Объект JSON c ключами &#x60;bots&#x60; и &#x60;meta&#x60; </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Некорректный запрос </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Не авторизован </td><td>  -  </td></tr>
        <tr><td> 403 </td><td> Запрещено </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Не найдено </td><td>  -  </td></tr>
        <tr><td> 429 </td><td> Слишком много запросов </td><td>  -  </td></tr>
        <tr><td> 500 </td><td> Внутренняя ошибка сервера </td><td>  -  </td></tr>
     </table>
     */
    public okhttp3.Call getAgentBotTelegramBotsByAgentAsync(Integer agentId, final ApiCallback<GetAgentBotTelegramBotsByAgent200Response> _callback) throws ApiException {

        okhttp3.Call localVarCall = getAgentBotTelegramBotsByAgentValidateBeforeCall(agentId, _callback);
        Type localVarReturnType = new TypeToken<GetAgentBotTelegramBotsByAgent200Response>(){}.getType();
        localVarApiClient.executeAsync(localVarCall, localVarReturnType, _callback);
        return localVarCall;
    }
    /**
     * Build call for getAgentIntegrationCrisp
     * @param agentId ID агента (required)
     * @param _callback Callback for upload/download progress
     * @return Call to execute
     * @throws ApiException If fail to serialize the request body object
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> Объект JSON c ключом &#x60;crisp_integration&#x60; </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Некорректный запрос </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Не авторизован </td><td>  -  </td></tr>
        <tr><td> 403 </td><td> Запрещено </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Не найдено </td><td>  -  </td></tr>
        <tr><td> 429 </td><td> Слишком много запросов </td><td>  -  </td></tr>
        <tr><td> 500 </td><td> Внутренняя ошибка сервера </td><td>  -  </td></tr>
        <tr><td> 503 </td><td> Интеграция с Crisp временно недоступна </td><td>  -  </td></tr>
     </table>
     */
    public okhttp3.Call getAgentIntegrationCrispCall(Integer agentId, final ApiCallback _callback) throws ApiException {
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
        String localVarPath = "/api/v1/cloud-ai/agent/{agent_id}/crisp"
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
    private okhttp3.Call getAgentIntegrationCrispValidateBeforeCall(Integer agentId, final ApiCallback _callback) throws ApiException {
        // verify the required parameter 'agentId' is set
        if (agentId == null) {
            throw new ApiException("Missing the required parameter 'agentId' when calling getAgentIntegrationCrisp(Async)");
        }

        return getAgentIntegrationCrispCall(agentId, _callback);

    }

    /**
     * Получение интеграции агента с Crisp
     * Чтобы получить интеграцию AI агента с Crisp, отправьте GET-запрос на &#x60;/api/v1/cloud-ai/agent/{agent_id}/crisp&#x60;.  В ответе есть &#x60;webhook_secret&#x60; — по нему можно восстановить адрес вебхука для Crisp. Если агент не подключен к Crisp, вернется ошибка 404.  Тело ответа будет представлять собой объект JSON с ключом &#x60;crisp_integration&#x60;.
     * @param agentId ID агента (required)
     * @return GetAgentIntegrationCrisp200Response
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> Объект JSON c ключом &#x60;crisp_integration&#x60; </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Некорректный запрос </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Не авторизован </td><td>  -  </td></tr>
        <tr><td> 403 </td><td> Запрещено </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Не найдено </td><td>  -  </td></tr>
        <tr><td> 429 </td><td> Слишком много запросов </td><td>  -  </td></tr>
        <tr><td> 500 </td><td> Внутренняя ошибка сервера </td><td>  -  </td></tr>
        <tr><td> 503 </td><td> Интеграция с Crisp временно недоступна </td><td>  -  </td></tr>
     </table>
     */
    public GetAgentIntegrationCrisp200Response getAgentIntegrationCrisp(Integer agentId) throws ApiException {
        ApiResponse<GetAgentIntegrationCrisp200Response> localVarResp = getAgentIntegrationCrispWithHttpInfo(agentId);
        return localVarResp.getData();
    }

    /**
     * Получение интеграции агента с Crisp
     * Чтобы получить интеграцию AI агента с Crisp, отправьте GET-запрос на &#x60;/api/v1/cloud-ai/agent/{agent_id}/crisp&#x60;.  В ответе есть &#x60;webhook_secret&#x60; — по нему можно восстановить адрес вебхука для Crisp. Если агент не подключен к Crisp, вернется ошибка 404.  Тело ответа будет представлять собой объект JSON с ключом &#x60;crisp_integration&#x60;.
     * @param agentId ID агента (required)
     * @return ApiResponse&lt;GetAgentIntegrationCrisp200Response&gt;
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> Объект JSON c ключом &#x60;crisp_integration&#x60; </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Некорректный запрос </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Не авторизован </td><td>  -  </td></tr>
        <tr><td> 403 </td><td> Запрещено </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Не найдено </td><td>  -  </td></tr>
        <tr><td> 429 </td><td> Слишком много запросов </td><td>  -  </td></tr>
        <tr><td> 500 </td><td> Внутренняя ошибка сервера </td><td>  -  </td></tr>
        <tr><td> 503 </td><td> Интеграция с Crisp временно недоступна </td><td>  -  </td></tr>
     </table>
     */
    public ApiResponse<GetAgentIntegrationCrisp200Response> getAgentIntegrationCrispWithHttpInfo(Integer agentId) throws ApiException {
        okhttp3.Call localVarCall = getAgentIntegrationCrispValidateBeforeCall(agentId, null);
        Type localVarReturnType = new TypeToken<GetAgentIntegrationCrisp200Response>(){}.getType();
        return localVarApiClient.execute(localVarCall, localVarReturnType);
    }

    /**
     * Получение интеграции агента с Crisp (asynchronously)
     * Чтобы получить интеграцию AI агента с Crisp, отправьте GET-запрос на &#x60;/api/v1/cloud-ai/agent/{agent_id}/crisp&#x60;.  В ответе есть &#x60;webhook_secret&#x60; — по нему можно восстановить адрес вебхука для Crisp. Если агент не подключен к Crisp, вернется ошибка 404.  Тело ответа будет представлять собой объект JSON с ключом &#x60;crisp_integration&#x60;.
     * @param agentId ID агента (required)
     * @param _callback The callback to be executed when the API call finishes
     * @return The request call
     * @throws ApiException If fail to process the API call, e.g. serializing the request body object
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> Объект JSON c ключом &#x60;crisp_integration&#x60; </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Некорректный запрос </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Не авторизован </td><td>  -  </td></tr>
        <tr><td> 403 </td><td> Запрещено </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Не найдено </td><td>  -  </td></tr>
        <tr><td> 429 </td><td> Слишком много запросов </td><td>  -  </td></tr>
        <tr><td> 500 </td><td> Внутренняя ошибка сервера </td><td>  -  </td></tr>
        <tr><td> 503 </td><td> Интеграция с Crisp временно недоступна </td><td>  -  </td></tr>
     </table>
     */
    public okhttp3.Call getAgentIntegrationCrispAsync(Integer agentId, final ApiCallback<GetAgentIntegrationCrisp200Response> _callback) throws ApiException {

        okhttp3.Call localVarCall = getAgentIntegrationCrispValidateBeforeCall(agentId, _callback);
        Type localVarReturnType = new TypeToken<GetAgentIntegrationCrisp200Response>(){}.getType();
        localVarApiClient.executeAsync(localVarCall, localVarReturnType, _callback);
        return localVarCall;
    }
    /**
     * Build call for getAgentIntegrationJivo
     * @param agentId ID агента (required)
     * @param _callback Callback for upload/download progress
     * @return Call to execute
     * @throws ApiException If fail to serialize the request body object
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> Объект JSON c ключом &#x60;jivo_integration&#x60; </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Некорректный запрос </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Не авторизован </td><td>  -  </td></tr>
        <tr><td> 403 </td><td> Запрещено </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Не найдено </td><td>  -  </td></tr>
        <tr><td> 429 </td><td> Слишком много запросов </td><td>  -  </td></tr>
        <tr><td> 500 </td><td> Внутренняя ошибка сервера </td><td>  -  </td></tr>
     </table>
     */
    public okhttp3.Call getAgentIntegrationJivoCall(Integer agentId, final ApiCallback _callback) throws ApiException {
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
        String localVarPath = "/api/v1/cloud-ai/agent/{agent_id}/jivo"
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
    private okhttp3.Call getAgentIntegrationJivoValidateBeforeCall(Integer agentId, final ApiCallback _callback) throws ApiException {
        // verify the required parameter 'agentId' is set
        if (agentId == null) {
            throw new ApiException("Missing the required parameter 'agentId' when calling getAgentIntegrationJivo(Async)");
        }

        return getAgentIntegrationJivoCall(agentId, _callback);

    }

    /**
     * Получение интеграции агента с Jivo
     * Чтобы получить интеграцию AI агента с Jivo, отправьте GET-запрос на &#x60;/api/v1/cloud-ai/agent/{agent_id}/jivo&#x60;.  Если агент не подключен к Jivo, вернется ошибка 404.  Тело ответа будет представлять собой объект JSON с ключом &#x60;jivo_integration&#x60;.
     * @param agentId ID агента (required)
     * @return GetAgentIntegrationJivo200Response
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> Объект JSON c ключом &#x60;jivo_integration&#x60; </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Некорректный запрос </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Не авторизован </td><td>  -  </td></tr>
        <tr><td> 403 </td><td> Запрещено </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Не найдено </td><td>  -  </td></tr>
        <tr><td> 429 </td><td> Слишком много запросов </td><td>  -  </td></tr>
        <tr><td> 500 </td><td> Внутренняя ошибка сервера </td><td>  -  </td></tr>
     </table>
     */
    public GetAgentIntegrationJivo200Response getAgentIntegrationJivo(Integer agentId) throws ApiException {
        ApiResponse<GetAgentIntegrationJivo200Response> localVarResp = getAgentIntegrationJivoWithHttpInfo(agentId);
        return localVarResp.getData();
    }

    /**
     * Получение интеграции агента с Jivo
     * Чтобы получить интеграцию AI агента с Jivo, отправьте GET-запрос на &#x60;/api/v1/cloud-ai/agent/{agent_id}/jivo&#x60;.  Если агент не подключен к Jivo, вернется ошибка 404.  Тело ответа будет представлять собой объект JSON с ключом &#x60;jivo_integration&#x60;.
     * @param agentId ID агента (required)
     * @return ApiResponse&lt;GetAgentIntegrationJivo200Response&gt;
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> Объект JSON c ключом &#x60;jivo_integration&#x60; </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Некорректный запрос </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Не авторизован </td><td>  -  </td></tr>
        <tr><td> 403 </td><td> Запрещено </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Не найдено </td><td>  -  </td></tr>
        <tr><td> 429 </td><td> Слишком много запросов </td><td>  -  </td></tr>
        <tr><td> 500 </td><td> Внутренняя ошибка сервера </td><td>  -  </td></tr>
     </table>
     */
    public ApiResponse<GetAgentIntegrationJivo200Response> getAgentIntegrationJivoWithHttpInfo(Integer agentId) throws ApiException {
        okhttp3.Call localVarCall = getAgentIntegrationJivoValidateBeforeCall(agentId, null);
        Type localVarReturnType = new TypeToken<GetAgentIntegrationJivo200Response>(){}.getType();
        return localVarApiClient.execute(localVarCall, localVarReturnType);
    }

    /**
     * Получение интеграции агента с Jivo (asynchronously)
     * Чтобы получить интеграцию AI агента с Jivo, отправьте GET-запрос на &#x60;/api/v1/cloud-ai/agent/{agent_id}/jivo&#x60;.  Если агент не подключен к Jivo, вернется ошибка 404.  Тело ответа будет представлять собой объект JSON с ключом &#x60;jivo_integration&#x60;.
     * @param agentId ID агента (required)
     * @param _callback The callback to be executed when the API call finishes
     * @return The request call
     * @throws ApiException If fail to process the API call, e.g. serializing the request body object
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> Объект JSON c ключом &#x60;jivo_integration&#x60; </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Некорректный запрос </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Не авторизован </td><td>  -  </td></tr>
        <tr><td> 403 </td><td> Запрещено </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Не найдено </td><td>  -  </td></tr>
        <tr><td> 429 </td><td> Слишком много запросов </td><td>  -  </td></tr>
        <tr><td> 500 </td><td> Внутренняя ошибка сервера </td><td>  -  </td></tr>
     </table>
     */
    public okhttp3.Call getAgentIntegrationJivoAsync(Integer agentId, final ApiCallback<GetAgentIntegrationJivo200Response> _callback) throws ApiException {

        okhttp3.Call localVarCall = getAgentIntegrationJivoValidateBeforeCall(agentId, _callback);
        Type localVarReturnType = new TypeToken<GetAgentIntegrationJivo200Response>(){}.getType();
        localVarApiClient.executeAsync(localVarCall, localVarReturnType, _callback);
        return localVarCall;
    }
    /**
     * Build call for updateAgentBotMaxBot
     * @param botId ID бота (поле &#x60;id&#x60; из ответа) (required)
     * @param updateMaxBot  (required)
     * @param _callback Callback for upload/download progress
     * @return Call to execute
     * @throws ApiException If fail to serialize the request body object
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> Объект JSON c ключом &#x60;bot&#x60; </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Некорректный запрос </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Не авторизован </td><td>  -  </td></tr>
        <tr><td> 403 </td><td> Запрещено </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Не найдено </td><td>  -  </td></tr>
        <tr><td> 429 </td><td> Слишком много запросов </td><td>  -  </td></tr>
        <tr><td> 500 </td><td> Внутренняя ошибка сервера </td><td>  -  </td></tr>
     </table>
     */
    public okhttp3.Call updateAgentBotMaxBotCall(UUID botId, UpdateMaxBot updateMaxBot, final ApiCallback _callback) throws ApiException {
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

        Object localVarPostBody = updateMaxBot;

        // create path and map variables
        String localVarPath = "/api/v1/cloud-ai/max-bots/{bot_id}"
            .replace("{" + "bot_id" + "}", localVarApiClient.escapeString(botId.toString()));

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
    private okhttp3.Call updateAgentBotMaxBotValidateBeforeCall(UUID botId, UpdateMaxBot updateMaxBot, final ApiCallback _callback) throws ApiException {
        // verify the required parameter 'botId' is set
        if (botId == null) {
            throw new ApiException("Missing the required parameter 'botId' when calling updateAgentBotMaxBot(Async)");
        }

        // verify the required parameter 'updateMaxBot' is set
        if (updateMaxBot == null) {
            throw new ApiException("Missing the required parameter 'updateMaxBot' when calling updateAgentBotMaxBot(Async)");
        }

        return updateAgentBotMaxBotCall(botId, updateMaxBot, _callback);

    }

    /**
     * Изменение бота MAX
     * Чтобы изменить бота MAX, отправьте PATCH-запрос на &#x60;/api/v1/cloud-ai/max-bots/{bot_id}&#x60;, передав только изменяемые поля.  Через &#x60;token&#x60; можно заменить токен того же бота: платформа проверит новый токен и заново подпишет бота на события. Перенести подключение на другого бота нельзя — для этого удалите бота и подключите новый.  Ошибки: - &#x60;400 invalid_max_token&#x60; — MAX не принял токен или токен принадлежит другому боту; - &#x60;400 max_webhook_error&#x60; — не удалось подписать бота на события.  Тело ответа будет представлять собой объект JSON с ключом &#x60;bot&#x60;.
     * @param botId ID бота (поле &#x60;id&#x60; из ответа) (required)
     * @param updateMaxBot  (required)
     * @return CreateAgentBotTelegramBot201Response
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> Объект JSON c ключом &#x60;bot&#x60; </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Некорректный запрос </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Не авторизован </td><td>  -  </td></tr>
        <tr><td> 403 </td><td> Запрещено </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Не найдено </td><td>  -  </td></tr>
        <tr><td> 429 </td><td> Слишком много запросов </td><td>  -  </td></tr>
        <tr><td> 500 </td><td> Внутренняя ошибка сервера </td><td>  -  </td></tr>
     </table>
     */
    public CreateAgentBotTelegramBot201Response updateAgentBotMaxBot(UUID botId, UpdateMaxBot updateMaxBot) throws ApiException {
        ApiResponse<CreateAgentBotTelegramBot201Response> localVarResp = updateAgentBotMaxBotWithHttpInfo(botId, updateMaxBot);
        return localVarResp.getData();
    }

    /**
     * Изменение бота MAX
     * Чтобы изменить бота MAX, отправьте PATCH-запрос на &#x60;/api/v1/cloud-ai/max-bots/{bot_id}&#x60;, передав только изменяемые поля.  Через &#x60;token&#x60; можно заменить токен того же бота: платформа проверит новый токен и заново подпишет бота на события. Перенести подключение на другого бота нельзя — для этого удалите бота и подключите новый.  Ошибки: - &#x60;400 invalid_max_token&#x60; — MAX не принял токен или токен принадлежит другому боту; - &#x60;400 max_webhook_error&#x60; — не удалось подписать бота на события.  Тело ответа будет представлять собой объект JSON с ключом &#x60;bot&#x60;.
     * @param botId ID бота (поле &#x60;id&#x60; из ответа) (required)
     * @param updateMaxBot  (required)
     * @return ApiResponse&lt;CreateAgentBotTelegramBot201Response&gt;
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> Объект JSON c ключом &#x60;bot&#x60; </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Некорректный запрос </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Не авторизован </td><td>  -  </td></tr>
        <tr><td> 403 </td><td> Запрещено </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Не найдено </td><td>  -  </td></tr>
        <tr><td> 429 </td><td> Слишком много запросов </td><td>  -  </td></tr>
        <tr><td> 500 </td><td> Внутренняя ошибка сервера </td><td>  -  </td></tr>
     </table>
     */
    public ApiResponse<CreateAgentBotTelegramBot201Response> updateAgentBotMaxBotWithHttpInfo(UUID botId, UpdateMaxBot updateMaxBot) throws ApiException {
        okhttp3.Call localVarCall = updateAgentBotMaxBotValidateBeforeCall(botId, updateMaxBot, null);
        Type localVarReturnType = new TypeToken<CreateAgentBotTelegramBot201Response>(){}.getType();
        return localVarApiClient.execute(localVarCall, localVarReturnType);
    }

    /**
     * Изменение бота MAX (asynchronously)
     * Чтобы изменить бота MAX, отправьте PATCH-запрос на &#x60;/api/v1/cloud-ai/max-bots/{bot_id}&#x60;, передав только изменяемые поля.  Через &#x60;token&#x60; можно заменить токен того же бота: платформа проверит новый токен и заново подпишет бота на события. Перенести подключение на другого бота нельзя — для этого удалите бота и подключите новый.  Ошибки: - &#x60;400 invalid_max_token&#x60; — MAX не принял токен или токен принадлежит другому боту; - &#x60;400 max_webhook_error&#x60; — не удалось подписать бота на события.  Тело ответа будет представлять собой объект JSON с ключом &#x60;bot&#x60;.
     * @param botId ID бота (поле &#x60;id&#x60; из ответа) (required)
     * @param updateMaxBot  (required)
     * @param _callback The callback to be executed when the API call finishes
     * @return The request call
     * @throws ApiException If fail to process the API call, e.g. serializing the request body object
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> Объект JSON c ключом &#x60;bot&#x60; </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Некорректный запрос </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Не авторизован </td><td>  -  </td></tr>
        <tr><td> 403 </td><td> Запрещено </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Не найдено </td><td>  -  </td></tr>
        <tr><td> 429 </td><td> Слишком много запросов </td><td>  -  </td></tr>
        <tr><td> 500 </td><td> Внутренняя ошибка сервера </td><td>  -  </td></tr>
     </table>
     */
    public okhttp3.Call updateAgentBotMaxBotAsync(UUID botId, UpdateMaxBot updateMaxBot, final ApiCallback<CreateAgentBotTelegramBot201Response> _callback) throws ApiException {

        okhttp3.Call localVarCall = updateAgentBotMaxBotValidateBeforeCall(botId, updateMaxBot, _callback);
        Type localVarReturnType = new TypeToken<CreateAgentBotTelegramBot201Response>(){}.getType();
        localVarApiClient.executeAsync(localVarCall, localVarReturnType, _callback);
        return localVarCall;
    }
    /**
     * Build call for updateAgentBotTelegramBot
     * @param botId ID бота (поле &#x60;id&#x60; из ответа) (required)
     * @param updateBot  (required)
     * @param _callback Callback for upload/download progress
     * @return Call to execute
     * @throws ApiException If fail to serialize the request body object
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> Объект JSON c ключом &#x60;bot&#x60; </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Некорректный запрос </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Не авторизован </td><td>  -  </td></tr>
        <tr><td> 403 </td><td> Запрещено </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Не найдено </td><td>  -  </td></tr>
        <tr><td> 429 </td><td> Слишком много запросов </td><td>  -  </td></tr>
        <tr><td> 500 </td><td> Внутренняя ошибка сервера </td><td>  -  </td></tr>
     </table>
     */
    public okhttp3.Call updateAgentBotTelegramBotCall(UUID botId, UpdateBot updateBot, final ApiCallback _callback) throws ApiException {
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

        Object localVarPostBody = updateBot;

        // create path and map variables
        String localVarPath = "/api/v1/cloud-ai/bots/{bot_id}"
            .replace("{" + "bot_id" + "}", localVarApiClient.escapeString(botId.toString()));

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
    private okhttp3.Call updateAgentBotTelegramBotValidateBeforeCall(UUID botId, UpdateBot updateBot, final ApiCallback _callback) throws ApiException {
        // verify the required parameter 'botId' is set
        if (botId == null) {
            throw new ApiException("Missing the required parameter 'botId' when calling updateAgentBotTelegramBot(Async)");
        }

        // verify the required parameter 'updateBot' is set
        if (updateBot == null) {
            throw new ApiException("Missing the required parameter 'updateBot' when calling updateAgentBotTelegramBot(Async)");
        }

        return updateAgentBotTelegramBotCall(botId, updateBot, _callback);

    }

    /**
     * Изменение Telegram-бота
     * Чтобы изменить Telegram-бота, отправьте PATCH-запрос на &#x60;/api/v1/cloud-ai/bots/{bot_id}&#x60;, передав только изменяемые поля.  Через &#x60;token&#x60; можно заменить токен того же бота, например после его перевыпуска в @BotFather: платформа проверит новый токен и переустановит вебхук. Перенести подключение на другого бота нельзя — для этого удалите бота и подключите новый. Агента, к которому подключен бот, этим запросом сменить нельзя.  Ошибки: - &#x60;400 invalid_telegram_token&#x60; — Telegram не принял токен или токен принадлежит другому боту; - &#x60;400 telegram_webhook_error&#x60; — не удалось переустановить вебхук.  Тело ответа будет представлять собой объект JSON с ключом &#x60;bot&#x60;.
     * @param botId ID бота (поле &#x60;id&#x60; из ответа) (required)
     * @param updateBot  (required)
     * @return CreateAgentBotTelegramBot201Response
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> Объект JSON c ключом &#x60;bot&#x60; </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Некорректный запрос </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Не авторизован </td><td>  -  </td></tr>
        <tr><td> 403 </td><td> Запрещено </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Не найдено </td><td>  -  </td></tr>
        <tr><td> 429 </td><td> Слишком много запросов </td><td>  -  </td></tr>
        <tr><td> 500 </td><td> Внутренняя ошибка сервера </td><td>  -  </td></tr>
     </table>
     */
    public CreateAgentBotTelegramBot201Response updateAgentBotTelegramBot(UUID botId, UpdateBot updateBot) throws ApiException {
        ApiResponse<CreateAgentBotTelegramBot201Response> localVarResp = updateAgentBotTelegramBotWithHttpInfo(botId, updateBot);
        return localVarResp.getData();
    }

    /**
     * Изменение Telegram-бота
     * Чтобы изменить Telegram-бота, отправьте PATCH-запрос на &#x60;/api/v1/cloud-ai/bots/{bot_id}&#x60;, передав только изменяемые поля.  Через &#x60;token&#x60; можно заменить токен того же бота, например после его перевыпуска в @BotFather: платформа проверит новый токен и переустановит вебхук. Перенести подключение на другого бота нельзя — для этого удалите бота и подключите новый. Агента, к которому подключен бот, этим запросом сменить нельзя.  Ошибки: - &#x60;400 invalid_telegram_token&#x60; — Telegram не принял токен или токен принадлежит другому боту; - &#x60;400 telegram_webhook_error&#x60; — не удалось переустановить вебхук.  Тело ответа будет представлять собой объект JSON с ключом &#x60;bot&#x60;.
     * @param botId ID бота (поле &#x60;id&#x60; из ответа) (required)
     * @param updateBot  (required)
     * @return ApiResponse&lt;CreateAgentBotTelegramBot201Response&gt;
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> Объект JSON c ключом &#x60;bot&#x60; </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Некорректный запрос </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Не авторизован </td><td>  -  </td></tr>
        <tr><td> 403 </td><td> Запрещено </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Не найдено </td><td>  -  </td></tr>
        <tr><td> 429 </td><td> Слишком много запросов </td><td>  -  </td></tr>
        <tr><td> 500 </td><td> Внутренняя ошибка сервера </td><td>  -  </td></tr>
     </table>
     */
    public ApiResponse<CreateAgentBotTelegramBot201Response> updateAgentBotTelegramBotWithHttpInfo(UUID botId, UpdateBot updateBot) throws ApiException {
        okhttp3.Call localVarCall = updateAgentBotTelegramBotValidateBeforeCall(botId, updateBot, null);
        Type localVarReturnType = new TypeToken<CreateAgentBotTelegramBot201Response>(){}.getType();
        return localVarApiClient.execute(localVarCall, localVarReturnType);
    }

    /**
     * Изменение Telegram-бота (asynchronously)
     * Чтобы изменить Telegram-бота, отправьте PATCH-запрос на &#x60;/api/v1/cloud-ai/bots/{bot_id}&#x60;, передав только изменяемые поля.  Через &#x60;token&#x60; можно заменить токен того же бота, например после его перевыпуска в @BotFather: платформа проверит новый токен и переустановит вебхук. Перенести подключение на другого бота нельзя — для этого удалите бота и подключите новый. Агента, к которому подключен бот, этим запросом сменить нельзя.  Ошибки: - &#x60;400 invalid_telegram_token&#x60; — Telegram не принял токен или токен принадлежит другому боту; - &#x60;400 telegram_webhook_error&#x60; — не удалось переустановить вебхук.  Тело ответа будет представлять собой объект JSON с ключом &#x60;bot&#x60;.
     * @param botId ID бота (поле &#x60;id&#x60; из ответа) (required)
     * @param updateBot  (required)
     * @param _callback The callback to be executed when the API call finishes
     * @return The request call
     * @throws ApiException If fail to process the API call, e.g. serializing the request body object
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> Объект JSON c ключом &#x60;bot&#x60; </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Некорректный запрос </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Не авторизован </td><td>  -  </td></tr>
        <tr><td> 403 </td><td> Запрещено </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Не найдено </td><td>  -  </td></tr>
        <tr><td> 429 </td><td> Слишком много запросов </td><td>  -  </td></tr>
        <tr><td> 500 </td><td> Внутренняя ошибка сервера </td><td>  -  </td></tr>
     </table>
     */
    public okhttp3.Call updateAgentBotTelegramBotAsync(UUID botId, UpdateBot updateBot, final ApiCallback<CreateAgentBotTelegramBot201Response> _callback) throws ApiException {

        okhttp3.Call localVarCall = updateAgentBotTelegramBotValidateBeforeCall(botId, updateBot, _callback);
        Type localVarReturnType = new TypeToken<CreateAgentBotTelegramBot201Response>(){}.getType();
        localVarApiClient.executeAsync(localVarCall, localVarReturnType, _callback);
        return localVarCall;
    }
}
