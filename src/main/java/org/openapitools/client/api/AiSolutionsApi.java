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


import org.openapitools.client.model.AddAiSolutionTelegramBot201Response;
import org.openapitools.client.model.ApproveTelegramPairing;
import org.openapitools.client.model.ConnectTelegram;
import org.openapitools.client.model.CreateAiSolution;
import org.openapitools.client.model.CreateAiSolution201Response;
import org.openapitools.client.model.ErrorResponse;
import org.openapitools.client.model.GetAccountStatus403Response;
import org.openapitools.client.model.GetAiSolutionChatStatus200Response;
import org.openapitools.client.model.GetAiSolutionMainChatHistory200Response;
import org.openapitools.client.model.GetFinances400Response;
import org.openapitools.client.model.GetFinances401Response;
import org.openapitools.client.model.GetFinances429Response;
import org.openapitools.client.model.GetFinances500Response;
import org.openapitools.client.model.GetImage404Response;
import org.openapitools.client.model.ListAiSolutionConversations200Response;
import org.openapitools.client.model.ListAiSolutionTelegramBots200Response;
import org.openapitools.client.model.ListAiSolutionTypes200Response;
import org.openapitools.client.model.ListAiSolutions200Response;
import org.openapitools.client.model.RegenerateAiSolutionAiKey200Response;
import org.openapitools.client.model.ResetAiSolutionChat;
import org.openapitools.client.model.ResetAiSolutionChat200Response;
import org.openapitools.client.model.SendAiSolutionMessage;
import org.openapitools.client.model.SendAiSolutionMessage200Response;
import org.openapitools.client.model.StopAiSolutionChat;
import org.openapitools.client.model.StreamError;
import org.openapitools.client.model.UpdateAiSolution;
import org.openapitools.client.model.UpdateDatabaseInstance409Response;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AiSolutionsApi {
    private ApiClient localVarApiClient;
    private int localHostIndex;
    private String localCustomBaseUrl;

    public AiSolutionsApi() {
        this(Configuration.getDefaultApiClient());
    }

    public AiSolutionsApi(ApiClient apiClient) {
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
     * Build call for addAiSolutionTelegramBot
     * @param solutionId ID AI-решения (required)
     * @param connectTelegram  (required)
     * @param _callback Callback for upload/download progress
     * @return Call to execute
     * @throws ApiException If fail to serialize the request body object
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 201 </td><td> Объект JSON c ключом &#x60;ai_solution_telegram_bot&#x60; </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Некорректный запрос </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Не авторизован </td><td>  -  </td></tr>
        <tr><td> 403 </td><td> Запрещено </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Не найдено </td><td>  -  </td></tr>
        <tr><td> 409 </td><td> Конфликт </td><td>  -  </td></tr>
        <tr><td> 429 </td><td> Слишком много запросов </td><td>  -  </td></tr>
        <tr><td> 500 </td><td> Внутренняя ошибка сервера </td><td>  -  </td></tr>
        <tr><td> 502 </td><td> Сервер решения не смог настроить бота (&#x60;ai_solution_chat_request_failed&#x60;) </td><td>  -  </td></tr>
        <tr><td> 503 </td><td> Сервер решения недоступен или не поддерживает Telegram — во втором случае пересоздайте решение (&#x60;ai_solution_chat_is_unavailable&#x60;) </td><td>  -  </td></tr>
     </table>
     */
    public okhttp3.Call addAiSolutionTelegramBotCall(Integer solutionId, ConnectTelegram connectTelegram, final ApiCallback _callback) throws ApiException {
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

        Object localVarPostBody = connectTelegram;

        // create path and map variables
        String localVarPath = "/api/v1/cloud-ai/solutions/{solution_id}/telegram/bots"
            .replace("{" + "solution_id" + "}", localVarApiClient.escapeString(solutionId.toString()));

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
    private okhttp3.Call addAiSolutionTelegramBotValidateBeforeCall(Integer solutionId, ConnectTelegram connectTelegram, final ApiCallback _callback) throws ApiException {
        // verify the required parameter 'solutionId' is set
        if (solutionId == null) {
            throw new ApiException("Missing the required parameter 'solutionId' when calling addAiSolutionTelegramBot(Async)");
        }

        // verify the required parameter 'connectTelegram' is set
        if (connectTelegram == null) {
            throw new ApiException("Missing the required parameter 'connectTelegram' when calling addAiSolutionTelegramBot(Async)");
        }

        return addAiSolutionTelegramBotCall(solutionId, connectTelegram, _callback);

    }

    /**
     * Подключение Telegram-бота к AI-решению
     * Чтобы подключить Telegram-бота к решению, отправьте POST-запрос на &#x60;/api/v1/cloud-ai/solutions/{solution_id}/telegram/bots&#x60;.  Принимает токен бота от @BotFather, проверяет его в Telegram и настраивает бота на сервере решения. Владелец подтверждается кодом: пока он не подтверждён, бот никому не отвечает. Напишите боту в личные сообщения, получите в ответ код и передайте его в метод подтверждения владельца.  К решению можно подключить только одного бота: повторный запрос для того же бота обновляет его токен, для другого бота — возвращает 409 &#x60;ai_solution_already_has_a_connected_telegram_bot&#x60;. Если Telegram отклонил токен, ответ — 400 &#x60;ai_solution_telegram_token_is_invalid&#x60;.  Перед вызовом убедитесь, что чат готов (&#x60;ready: true&#x60; в методе получения статуса чата): пока сервер решения разворачивается или выключен, запрос завершается ошибкой.
     * @param solutionId ID AI-решения (required)
     * @param connectTelegram  (required)
     * @return AddAiSolutionTelegramBot201Response
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 201 </td><td> Объект JSON c ключом &#x60;ai_solution_telegram_bot&#x60; </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Некорректный запрос </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Не авторизован </td><td>  -  </td></tr>
        <tr><td> 403 </td><td> Запрещено </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Не найдено </td><td>  -  </td></tr>
        <tr><td> 409 </td><td> Конфликт </td><td>  -  </td></tr>
        <tr><td> 429 </td><td> Слишком много запросов </td><td>  -  </td></tr>
        <tr><td> 500 </td><td> Внутренняя ошибка сервера </td><td>  -  </td></tr>
        <tr><td> 502 </td><td> Сервер решения не смог настроить бота (&#x60;ai_solution_chat_request_failed&#x60;) </td><td>  -  </td></tr>
        <tr><td> 503 </td><td> Сервер решения недоступен или не поддерживает Telegram — во втором случае пересоздайте решение (&#x60;ai_solution_chat_is_unavailable&#x60;) </td><td>  -  </td></tr>
     </table>
     */
    public AddAiSolutionTelegramBot201Response addAiSolutionTelegramBot(Integer solutionId, ConnectTelegram connectTelegram) throws ApiException {
        ApiResponse<AddAiSolutionTelegramBot201Response> localVarResp = addAiSolutionTelegramBotWithHttpInfo(solutionId, connectTelegram);
        return localVarResp.getData();
    }

    /**
     * Подключение Telegram-бота к AI-решению
     * Чтобы подключить Telegram-бота к решению, отправьте POST-запрос на &#x60;/api/v1/cloud-ai/solutions/{solution_id}/telegram/bots&#x60;.  Принимает токен бота от @BotFather, проверяет его в Telegram и настраивает бота на сервере решения. Владелец подтверждается кодом: пока он не подтверждён, бот никому не отвечает. Напишите боту в личные сообщения, получите в ответ код и передайте его в метод подтверждения владельца.  К решению можно подключить только одного бота: повторный запрос для того же бота обновляет его токен, для другого бота — возвращает 409 &#x60;ai_solution_already_has_a_connected_telegram_bot&#x60;. Если Telegram отклонил токен, ответ — 400 &#x60;ai_solution_telegram_token_is_invalid&#x60;.  Перед вызовом убедитесь, что чат готов (&#x60;ready: true&#x60; в методе получения статуса чата): пока сервер решения разворачивается или выключен, запрос завершается ошибкой.
     * @param solutionId ID AI-решения (required)
     * @param connectTelegram  (required)
     * @return ApiResponse&lt;AddAiSolutionTelegramBot201Response&gt;
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 201 </td><td> Объект JSON c ключом &#x60;ai_solution_telegram_bot&#x60; </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Некорректный запрос </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Не авторизован </td><td>  -  </td></tr>
        <tr><td> 403 </td><td> Запрещено </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Не найдено </td><td>  -  </td></tr>
        <tr><td> 409 </td><td> Конфликт </td><td>  -  </td></tr>
        <tr><td> 429 </td><td> Слишком много запросов </td><td>  -  </td></tr>
        <tr><td> 500 </td><td> Внутренняя ошибка сервера </td><td>  -  </td></tr>
        <tr><td> 502 </td><td> Сервер решения не смог настроить бота (&#x60;ai_solution_chat_request_failed&#x60;) </td><td>  -  </td></tr>
        <tr><td> 503 </td><td> Сервер решения недоступен или не поддерживает Telegram — во втором случае пересоздайте решение (&#x60;ai_solution_chat_is_unavailable&#x60;) </td><td>  -  </td></tr>
     </table>
     */
    public ApiResponse<AddAiSolutionTelegramBot201Response> addAiSolutionTelegramBotWithHttpInfo(Integer solutionId, ConnectTelegram connectTelegram) throws ApiException {
        okhttp3.Call localVarCall = addAiSolutionTelegramBotValidateBeforeCall(solutionId, connectTelegram, null);
        Type localVarReturnType = new TypeToken<AddAiSolutionTelegramBot201Response>(){}.getType();
        return localVarApiClient.execute(localVarCall, localVarReturnType);
    }

    /**
     * Подключение Telegram-бота к AI-решению (asynchronously)
     * Чтобы подключить Telegram-бота к решению, отправьте POST-запрос на &#x60;/api/v1/cloud-ai/solutions/{solution_id}/telegram/bots&#x60;.  Принимает токен бота от @BotFather, проверяет его в Telegram и настраивает бота на сервере решения. Владелец подтверждается кодом: пока он не подтверждён, бот никому не отвечает. Напишите боту в личные сообщения, получите в ответ код и передайте его в метод подтверждения владельца.  К решению можно подключить только одного бота: повторный запрос для того же бота обновляет его токен, для другого бота — возвращает 409 &#x60;ai_solution_already_has_a_connected_telegram_bot&#x60;. Если Telegram отклонил токен, ответ — 400 &#x60;ai_solution_telegram_token_is_invalid&#x60;.  Перед вызовом убедитесь, что чат готов (&#x60;ready: true&#x60; в методе получения статуса чата): пока сервер решения разворачивается или выключен, запрос завершается ошибкой.
     * @param solutionId ID AI-решения (required)
     * @param connectTelegram  (required)
     * @param _callback The callback to be executed when the API call finishes
     * @return The request call
     * @throws ApiException If fail to process the API call, e.g. serializing the request body object
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 201 </td><td> Объект JSON c ключом &#x60;ai_solution_telegram_bot&#x60; </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Некорректный запрос </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Не авторизован </td><td>  -  </td></tr>
        <tr><td> 403 </td><td> Запрещено </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Не найдено </td><td>  -  </td></tr>
        <tr><td> 409 </td><td> Конфликт </td><td>  -  </td></tr>
        <tr><td> 429 </td><td> Слишком много запросов </td><td>  -  </td></tr>
        <tr><td> 500 </td><td> Внутренняя ошибка сервера </td><td>  -  </td></tr>
        <tr><td> 502 </td><td> Сервер решения не смог настроить бота (&#x60;ai_solution_chat_request_failed&#x60;) </td><td>  -  </td></tr>
        <tr><td> 503 </td><td> Сервер решения недоступен или не поддерживает Telegram — во втором случае пересоздайте решение (&#x60;ai_solution_chat_is_unavailable&#x60;) </td><td>  -  </td></tr>
     </table>
     */
    public okhttp3.Call addAiSolutionTelegramBotAsync(Integer solutionId, ConnectTelegram connectTelegram, final ApiCallback<AddAiSolutionTelegramBot201Response> _callback) throws ApiException {

        okhttp3.Call localVarCall = addAiSolutionTelegramBotValidateBeforeCall(solutionId, connectTelegram, _callback);
        Type localVarReturnType = new TypeToken<AddAiSolutionTelegramBot201Response>(){}.getType();
        localVarApiClient.executeAsync(localVarCall, localVarReturnType, _callback);
        return localVarCall;
    }
    /**
     * Build call for approveAiSolutionTelegramPairing
     * @param solutionId ID AI-решения (required)
     * @param accountKey ID Telegram-бота в решении (&#x60;account_key&#x60; из списка ботов) в формате &#x60;b&lt;число&gt;&#x60;, например &#x60;b123456789&#x60; (required)
     * @param approveTelegramPairing  (required)
     * @param _callback Callback for upload/download progress
     * @return Call to execute
     * @throws ApiException If fail to serialize the request body object
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 204 </td><td> Владелец подтверждён </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Некорректный запрос </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Не авторизован </td><td>  -  </td></tr>
        <tr><td> 403 </td><td> Запрещено </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Не найдено </td><td>  -  </td></tr>
        <tr><td> 429 </td><td> Слишком много запросов </td><td>  -  </td></tr>
        <tr><td> 500 </td><td> Внутренняя ошибка сервера </td><td>  -  </td></tr>
        <tr><td> 502 </td><td> Сервер решения не подтвердил владельца — например, код неверный или устарел (&#x60;ai_solution_chat_request_failed&#x60;) </td><td>  -  </td></tr>
        <tr><td> 503 </td><td> Сервер решения недоступен или не поддерживает Telegram — во втором случае пересоздайте решение (&#x60;ai_solution_chat_is_unavailable&#x60;) </td><td>  -  </td></tr>
     </table>
     */
    public okhttp3.Call approveAiSolutionTelegramPairingCall(Integer solutionId, String accountKey, ApproveTelegramPairing approveTelegramPairing, final ApiCallback _callback) throws ApiException {
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

        Object localVarPostBody = approveTelegramPairing;

        // create path and map variables
        String localVarPath = "/api/v1/cloud-ai/solutions/{solution_id}/telegram/bots/{account_key}/pairing/approve"
            .replace("{" + "solution_id" + "}", localVarApiClient.escapeString(solutionId.toString()))
            .replace("{" + "account_key" + "}", localVarApiClient.escapeString(accountKey.toString()));

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
    private okhttp3.Call approveAiSolutionTelegramPairingValidateBeforeCall(Integer solutionId, String accountKey, ApproveTelegramPairing approveTelegramPairing, final ApiCallback _callback) throws ApiException {
        // verify the required parameter 'solutionId' is set
        if (solutionId == null) {
            throw new ApiException("Missing the required parameter 'solutionId' when calling approveAiSolutionTelegramPairing(Async)");
        }

        // verify the required parameter 'accountKey' is set
        if (accountKey == null) {
            throw new ApiException("Missing the required parameter 'accountKey' when calling approveAiSolutionTelegramPairing(Async)");
        }

        // verify the required parameter 'approveTelegramPairing' is set
        if (approveTelegramPairing == null) {
            throw new ApiException("Missing the required parameter 'approveTelegramPairing' when calling approveAiSolutionTelegramPairing(Async)");
        }

        return approveAiSolutionTelegramPairingCall(solutionId, accountKey, approveTelegramPairing, _callback);

    }

    /**
     * Подтверждение владельца Telegram-бота AI-решения
     * Чтобы подтвердить владельца Telegram-бота, отправьте POST-запрос на &#x60;/api/v1/cloud-ai/solutions/{solution_id}/telegram/bots/{account_key}/pairing/approve&#x60;.  Передайте код, который бот прислал в личные сообщения в ответ на первое сообщение. После подтверждения агент отвечает этому пользователю Telegram в боте, а бот переходит в состояние &#x60;connected&#x60;. Владелец привязывается к конкретному боту. Повторный запрос с кодом, уже подтверждённым в последние 15 минут, считается успешным. Некорректный &#x60;account_key&#x60; — ответ 400 &#x60;ai_solution_telegram_token_is_invalid&#x60;.
     * @param solutionId ID AI-решения (required)
     * @param accountKey ID Telegram-бота в решении (&#x60;account_key&#x60; из списка ботов) в формате &#x60;b&lt;число&gt;&#x60;, например &#x60;b123456789&#x60; (required)
     * @param approveTelegramPairing  (required)
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 204 </td><td> Владелец подтверждён </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Некорректный запрос </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Не авторизован </td><td>  -  </td></tr>
        <tr><td> 403 </td><td> Запрещено </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Не найдено </td><td>  -  </td></tr>
        <tr><td> 429 </td><td> Слишком много запросов </td><td>  -  </td></tr>
        <tr><td> 500 </td><td> Внутренняя ошибка сервера </td><td>  -  </td></tr>
        <tr><td> 502 </td><td> Сервер решения не подтвердил владельца — например, код неверный или устарел (&#x60;ai_solution_chat_request_failed&#x60;) </td><td>  -  </td></tr>
        <tr><td> 503 </td><td> Сервер решения недоступен или не поддерживает Telegram — во втором случае пересоздайте решение (&#x60;ai_solution_chat_is_unavailable&#x60;) </td><td>  -  </td></tr>
     </table>
     */
    public void approveAiSolutionTelegramPairing(Integer solutionId, String accountKey, ApproveTelegramPairing approveTelegramPairing) throws ApiException {
        approveAiSolutionTelegramPairingWithHttpInfo(solutionId, accountKey, approveTelegramPairing);
    }

    /**
     * Подтверждение владельца Telegram-бота AI-решения
     * Чтобы подтвердить владельца Telegram-бота, отправьте POST-запрос на &#x60;/api/v1/cloud-ai/solutions/{solution_id}/telegram/bots/{account_key}/pairing/approve&#x60;.  Передайте код, который бот прислал в личные сообщения в ответ на первое сообщение. После подтверждения агент отвечает этому пользователю Telegram в боте, а бот переходит в состояние &#x60;connected&#x60;. Владелец привязывается к конкретному боту. Повторный запрос с кодом, уже подтверждённым в последние 15 минут, считается успешным. Некорректный &#x60;account_key&#x60; — ответ 400 &#x60;ai_solution_telegram_token_is_invalid&#x60;.
     * @param solutionId ID AI-решения (required)
     * @param accountKey ID Telegram-бота в решении (&#x60;account_key&#x60; из списка ботов) в формате &#x60;b&lt;число&gt;&#x60;, например &#x60;b123456789&#x60; (required)
     * @param approveTelegramPairing  (required)
     * @return ApiResponse&lt;Void&gt;
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 204 </td><td> Владелец подтверждён </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Некорректный запрос </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Не авторизован </td><td>  -  </td></tr>
        <tr><td> 403 </td><td> Запрещено </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Не найдено </td><td>  -  </td></tr>
        <tr><td> 429 </td><td> Слишком много запросов </td><td>  -  </td></tr>
        <tr><td> 500 </td><td> Внутренняя ошибка сервера </td><td>  -  </td></tr>
        <tr><td> 502 </td><td> Сервер решения не подтвердил владельца — например, код неверный или устарел (&#x60;ai_solution_chat_request_failed&#x60;) </td><td>  -  </td></tr>
        <tr><td> 503 </td><td> Сервер решения недоступен или не поддерживает Telegram — во втором случае пересоздайте решение (&#x60;ai_solution_chat_is_unavailable&#x60;) </td><td>  -  </td></tr>
     </table>
     */
    public ApiResponse<Void> approveAiSolutionTelegramPairingWithHttpInfo(Integer solutionId, String accountKey, ApproveTelegramPairing approveTelegramPairing) throws ApiException {
        okhttp3.Call localVarCall = approveAiSolutionTelegramPairingValidateBeforeCall(solutionId, accountKey, approveTelegramPairing, null);
        return localVarApiClient.execute(localVarCall);
    }

    /**
     * Подтверждение владельца Telegram-бота AI-решения (asynchronously)
     * Чтобы подтвердить владельца Telegram-бота, отправьте POST-запрос на &#x60;/api/v1/cloud-ai/solutions/{solution_id}/telegram/bots/{account_key}/pairing/approve&#x60;.  Передайте код, который бот прислал в личные сообщения в ответ на первое сообщение. После подтверждения агент отвечает этому пользователю Telegram в боте, а бот переходит в состояние &#x60;connected&#x60;. Владелец привязывается к конкретному боту. Повторный запрос с кодом, уже подтверждённым в последние 15 минут, считается успешным. Некорректный &#x60;account_key&#x60; — ответ 400 &#x60;ai_solution_telegram_token_is_invalid&#x60;.
     * @param solutionId ID AI-решения (required)
     * @param accountKey ID Telegram-бота в решении (&#x60;account_key&#x60; из списка ботов) в формате &#x60;b&lt;число&gt;&#x60;, например &#x60;b123456789&#x60; (required)
     * @param approveTelegramPairing  (required)
     * @param _callback The callback to be executed when the API call finishes
     * @return The request call
     * @throws ApiException If fail to process the API call, e.g. serializing the request body object
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 204 </td><td> Владелец подтверждён </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Некорректный запрос </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Не авторизован </td><td>  -  </td></tr>
        <tr><td> 403 </td><td> Запрещено </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Не найдено </td><td>  -  </td></tr>
        <tr><td> 429 </td><td> Слишком много запросов </td><td>  -  </td></tr>
        <tr><td> 500 </td><td> Внутренняя ошибка сервера </td><td>  -  </td></tr>
        <tr><td> 502 </td><td> Сервер решения не подтвердил владельца — например, код неверный или устарел (&#x60;ai_solution_chat_request_failed&#x60;) </td><td>  -  </td></tr>
        <tr><td> 503 </td><td> Сервер решения недоступен или не поддерживает Telegram — во втором случае пересоздайте решение (&#x60;ai_solution_chat_is_unavailable&#x60;) </td><td>  -  </td></tr>
     </table>
     */
    public okhttp3.Call approveAiSolutionTelegramPairingAsync(Integer solutionId, String accountKey, ApproveTelegramPairing approveTelegramPairing, final ApiCallback<Void> _callback) throws ApiException {

        okhttp3.Call localVarCall = approveAiSolutionTelegramPairingValidateBeforeCall(solutionId, accountKey, approveTelegramPairing, _callback);
        localVarApiClient.executeAsync(localVarCall, _callback);
        return localVarCall;
    }
    /**
     * Build call for createAiSolution
     * @param createAiSolution  (required)
     * @param _callback Callback for upload/download progress
     * @return Call to execute
     * @throws ApiException If fail to serialize the request body object
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 201 </td><td> Объект JSON c ключом &#x60;ai_solution&#x60; </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Некорректный запрос </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Не авторизован </td><td>  -  </td></tr>
        <tr><td> 402 </td><td> Баланса аккаунта не хватает на час работы выбранного тарифа (&#x60;insufficient_account_balance_to_create_ai_solution&#x60;) </td><td>  -  </td></tr>
        <tr><td> 403 </td><td> Запрещено </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Не найдено </td><td>  -  </td></tr>
        <tr><td> 409 </td><td> Конфликт </td><td>  -  </td></tr>
        <tr><td> 429 </td><td> Слишком много запросов </td><td>  -  </td></tr>
        <tr><td> 500 </td><td> Внутренняя ошибка сервера </td><td>  -  </td></tr>
        <tr><td> 503 </td><td> Образ решения недоступен в выбранной зоне или в зоне по умолчанию для региона аккаунта (&#x60;ai_solution_image_is_not_available_in_this_zone&#x60;) </td><td>  -  </td></tr>
     </table>
     */
    public okhttp3.Call createAiSolutionCall(CreateAiSolution createAiSolution, final ApiCallback _callback) throws ApiException {
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

        Object localVarPostBody = createAiSolution;

        // create path and map variables
        String localVarPath = "/api/v1/cloud-ai/solutions";

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
    private okhttp3.Call createAiSolutionValidateBeforeCall(CreateAiSolution createAiSolution, final ApiCallback _callback) throws ApiException {
        // verify the required parameter 'createAiSolution' is set
        if (createAiSolution == null) {
            throw new ApiException("Missing the required parameter 'createAiSolution' when calling createAiSolution(Async)");
        }

        return createAiSolutionCall(createAiSolution, _callback);

    }

    /**
     * Создание AI-решения
     * Чтобы создать AI-решение, отправьте POST-запрос на &#x60;/api/v1/cloud-ai/solutions&#x60;.  Выпускает для решения ключ AI Gateway и создаёт облачный сервер из образа решения; агент на сервере настраивается на выбранную модель через этот ключ. Сервер тарифицируется по выбранному тарифу, обращения к модели — через ключ AI Gateway.  Решение возвращается в статусе &#x60;creating&#x60;: развёртывание сервера и настройка агента занимают несколько минут. Дождитесь статуса &#x60;active&#x60;, а затем &#x60;ready: true&#x60; в методе получения статуса чата.  Тариф (&#x60;preset_id&#x60;) должен быть из той же локации, что и зона доступности (&#x60;availability_zone&#x60; или зона по умолчанию для региона аккаунта), иначе — 400 &#x60;invalid_location_zone&#x60;. Соответствие тарифа &#x60;requirements&#x60; типа решения при создании не проверяется — выбирайте тариф не ниже требований сами. Ответ 409 &#x60;no_free_node&#x60; означает, что в выбранной зоне сейчас нет свободных ресурсов под этот тариф — выберите другой тариф или зону. Неподходящая или устаревшая модель — 404 &#x60;model_not_found&#x60;.
     * @param createAiSolution  (required)
     * @return CreateAiSolution201Response
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 201 </td><td> Объект JSON c ключом &#x60;ai_solution&#x60; </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Некорректный запрос </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Не авторизован </td><td>  -  </td></tr>
        <tr><td> 402 </td><td> Баланса аккаунта не хватает на час работы выбранного тарифа (&#x60;insufficient_account_balance_to_create_ai_solution&#x60;) </td><td>  -  </td></tr>
        <tr><td> 403 </td><td> Запрещено </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Не найдено </td><td>  -  </td></tr>
        <tr><td> 409 </td><td> Конфликт </td><td>  -  </td></tr>
        <tr><td> 429 </td><td> Слишком много запросов </td><td>  -  </td></tr>
        <tr><td> 500 </td><td> Внутренняя ошибка сервера </td><td>  -  </td></tr>
        <tr><td> 503 </td><td> Образ решения недоступен в выбранной зоне или в зоне по умолчанию для региона аккаунта (&#x60;ai_solution_image_is_not_available_in_this_zone&#x60;) </td><td>  -  </td></tr>
     </table>
     */
    public CreateAiSolution201Response createAiSolution(CreateAiSolution createAiSolution) throws ApiException {
        ApiResponse<CreateAiSolution201Response> localVarResp = createAiSolutionWithHttpInfo(createAiSolution);
        return localVarResp.getData();
    }

    /**
     * Создание AI-решения
     * Чтобы создать AI-решение, отправьте POST-запрос на &#x60;/api/v1/cloud-ai/solutions&#x60;.  Выпускает для решения ключ AI Gateway и создаёт облачный сервер из образа решения; агент на сервере настраивается на выбранную модель через этот ключ. Сервер тарифицируется по выбранному тарифу, обращения к модели — через ключ AI Gateway.  Решение возвращается в статусе &#x60;creating&#x60;: развёртывание сервера и настройка агента занимают несколько минут. Дождитесь статуса &#x60;active&#x60;, а затем &#x60;ready: true&#x60; в методе получения статуса чата.  Тариф (&#x60;preset_id&#x60;) должен быть из той же локации, что и зона доступности (&#x60;availability_zone&#x60; или зона по умолчанию для региона аккаунта), иначе — 400 &#x60;invalid_location_zone&#x60;. Соответствие тарифа &#x60;requirements&#x60; типа решения при создании не проверяется — выбирайте тариф не ниже требований сами. Ответ 409 &#x60;no_free_node&#x60; означает, что в выбранной зоне сейчас нет свободных ресурсов под этот тариф — выберите другой тариф или зону. Неподходящая или устаревшая модель — 404 &#x60;model_not_found&#x60;.
     * @param createAiSolution  (required)
     * @return ApiResponse&lt;CreateAiSolution201Response&gt;
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 201 </td><td> Объект JSON c ключом &#x60;ai_solution&#x60; </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Некорректный запрос </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Не авторизован </td><td>  -  </td></tr>
        <tr><td> 402 </td><td> Баланса аккаунта не хватает на час работы выбранного тарифа (&#x60;insufficient_account_balance_to_create_ai_solution&#x60;) </td><td>  -  </td></tr>
        <tr><td> 403 </td><td> Запрещено </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Не найдено </td><td>  -  </td></tr>
        <tr><td> 409 </td><td> Конфликт </td><td>  -  </td></tr>
        <tr><td> 429 </td><td> Слишком много запросов </td><td>  -  </td></tr>
        <tr><td> 500 </td><td> Внутренняя ошибка сервера </td><td>  -  </td></tr>
        <tr><td> 503 </td><td> Образ решения недоступен в выбранной зоне или в зоне по умолчанию для региона аккаунта (&#x60;ai_solution_image_is_not_available_in_this_zone&#x60;) </td><td>  -  </td></tr>
     </table>
     */
    public ApiResponse<CreateAiSolution201Response> createAiSolutionWithHttpInfo(CreateAiSolution createAiSolution) throws ApiException {
        okhttp3.Call localVarCall = createAiSolutionValidateBeforeCall(createAiSolution, null);
        Type localVarReturnType = new TypeToken<CreateAiSolution201Response>(){}.getType();
        return localVarApiClient.execute(localVarCall, localVarReturnType);
    }

    /**
     * Создание AI-решения (asynchronously)
     * Чтобы создать AI-решение, отправьте POST-запрос на &#x60;/api/v1/cloud-ai/solutions&#x60;.  Выпускает для решения ключ AI Gateway и создаёт облачный сервер из образа решения; агент на сервере настраивается на выбранную модель через этот ключ. Сервер тарифицируется по выбранному тарифу, обращения к модели — через ключ AI Gateway.  Решение возвращается в статусе &#x60;creating&#x60;: развёртывание сервера и настройка агента занимают несколько минут. Дождитесь статуса &#x60;active&#x60;, а затем &#x60;ready: true&#x60; в методе получения статуса чата.  Тариф (&#x60;preset_id&#x60;) должен быть из той же локации, что и зона доступности (&#x60;availability_zone&#x60; или зона по умолчанию для региона аккаунта), иначе — 400 &#x60;invalid_location_zone&#x60;. Соответствие тарифа &#x60;requirements&#x60; типа решения при создании не проверяется — выбирайте тариф не ниже требований сами. Ответ 409 &#x60;no_free_node&#x60; означает, что в выбранной зоне сейчас нет свободных ресурсов под этот тариф — выберите другой тариф или зону. Неподходящая или устаревшая модель — 404 &#x60;model_not_found&#x60;.
     * @param createAiSolution  (required)
     * @param _callback The callback to be executed when the API call finishes
     * @return The request call
     * @throws ApiException If fail to process the API call, e.g. serializing the request body object
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 201 </td><td> Объект JSON c ключом &#x60;ai_solution&#x60; </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Некорректный запрос </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Не авторизован </td><td>  -  </td></tr>
        <tr><td> 402 </td><td> Баланса аккаунта не хватает на час работы выбранного тарифа (&#x60;insufficient_account_balance_to_create_ai_solution&#x60;) </td><td>  -  </td></tr>
        <tr><td> 403 </td><td> Запрещено </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Не найдено </td><td>  -  </td></tr>
        <tr><td> 409 </td><td> Конфликт </td><td>  -  </td></tr>
        <tr><td> 429 </td><td> Слишком много запросов </td><td>  -  </td></tr>
        <tr><td> 500 </td><td> Внутренняя ошибка сервера </td><td>  -  </td></tr>
        <tr><td> 503 </td><td> Образ решения недоступен в выбранной зоне или в зоне по умолчанию для региона аккаунта (&#x60;ai_solution_image_is_not_available_in_this_zone&#x60;) </td><td>  -  </td></tr>
     </table>
     */
    public okhttp3.Call createAiSolutionAsync(CreateAiSolution createAiSolution, final ApiCallback<CreateAiSolution201Response> _callback) throws ApiException {

        okhttp3.Call localVarCall = createAiSolutionValidateBeforeCall(createAiSolution, _callback);
        Type localVarReturnType = new TypeToken<CreateAiSolution201Response>(){}.getType();
        localVarApiClient.executeAsync(localVarCall, localVarReturnType, _callback);
        return localVarCall;
    }
    /**
     * Build call for deleteAiSolution
     * @param solutionId ID AI-решения (required)
     * @param _callback Callback for upload/download progress
     * @return Call to execute
     * @throws ApiException If fail to serialize the request body object
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 204 </td><td> Решение удалено </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Некорректный запрос </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Не авторизован </td><td>  -  </td></tr>
        <tr><td> 403 </td><td> Запрещено </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Не найдено </td><td>  -  </td></tr>
        <tr><td> 429 </td><td> Слишком много запросов </td><td>  -  </td></tr>
        <tr><td> 500 </td><td> Внутренняя ошибка сервера </td><td>  -  </td></tr>
     </table>
     */
    public okhttp3.Call deleteAiSolutionCall(Integer solutionId, final ApiCallback _callback) throws ApiException {
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
        String localVarPath = "/api/v1/cloud-ai/solutions/{solution_id}"
            .replace("{" + "solution_id" + "}", localVarApiClient.escapeString(solutionId.toString()));

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
    private okhttp3.Call deleteAiSolutionValidateBeforeCall(Integer solutionId, final ApiCallback _callback) throws ApiException {
        // verify the required parameter 'solutionId' is set
        if (solutionId == null) {
            throw new ApiException("Missing the required parameter 'solutionId' when calling deleteAiSolution(Async)");
        }

        return deleteAiSolutionCall(solutionId, _callback);

    }

    /**
     * Удаление AI-решения
     * Чтобы удалить AI-решение, отправьте DELETE-запрос на &#x60;/api/v1/cloud-ai/solutions/{solution_id}&#x60;.  Решение удаляется вместе с сервером и ключом AI Gateway. Действие необратимо: решение и история его чата становятся недоступны.
     * @param solutionId ID AI-решения (required)
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 204 </td><td> Решение удалено </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Некорректный запрос </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Не авторизован </td><td>  -  </td></tr>
        <tr><td> 403 </td><td> Запрещено </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Не найдено </td><td>  -  </td></tr>
        <tr><td> 429 </td><td> Слишком много запросов </td><td>  -  </td></tr>
        <tr><td> 500 </td><td> Внутренняя ошибка сервера </td><td>  -  </td></tr>
     </table>
     */
    public void deleteAiSolution(Integer solutionId) throws ApiException {
        deleteAiSolutionWithHttpInfo(solutionId);
    }

    /**
     * Удаление AI-решения
     * Чтобы удалить AI-решение, отправьте DELETE-запрос на &#x60;/api/v1/cloud-ai/solutions/{solution_id}&#x60;.  Решение удаляется вместе с сервером и ключом AI Gateway. Действие необратимо: решение и история его чата становятся недоступны.
     * @param solutionId ID AI-решения (required)
     * @return ApiResponse&lt;Void&gt;
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 204 </td><td> Решение удалено </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Некорректный запрос </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Не авторизован </td><td>  -  </td></tr>
        <tr><td> 403 </td><td> Запрещено </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Не найдено </td><td>  -  </td></tr>
        <tr><td> 429 </td><td> Слишком много запросов </td><td>  -  </td></tr>
        <tr><td> 500 </td><td> Внутренняя ошибка сервера </td><td>  -  </td></tr>
     </table>
     */
    public ApiResponse<Void> deleteAiSolutionWithHttpInfo(Integer solutionId) throws ApiException {
        okhttp3.Call localVarCall = deleteAiSolutionValidateBeforeCall(solutionId, null);
        return localVarApiClient.execute(localVarCall);
    }

    /**
     * Удаление AI-решения (asynchronously)
     * Чтобы удалить AI-решение, отправьте DELETE-запрос на &#x60;/api/v1/cloud-ai/solutions/{solution_id}&#x60;.  Решение удаляется вместе с сервером и ключом AI Gateway. Действие необратимо: решение и история его чата становятся недоступны.
     * @param solutionId ID AI-решения (required)
     * @param _callback The callback to be executed when the API call finishes
     * @return The request call
     * @throws ApiException If fail to process the API call, e.g. serializing the request body object
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 204 </td><td> Решение удалено </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Некорректный запрос </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Не авторизован </td><td>  -  </td></tr>
        <tr><td> 403 </td><td> Запрещено </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Не найдено </td><td>  -  </td></tr>
        <tr><td> 429 </td><td> Слишком много запросов </td><td>  -  </td></tr>
        <tr><td> 500 </td><td> Внутренняя ошибка сервера </td><td>  -  </td></tr>
     </table>
     */
    public okhttp3.Call deleteAiSolutionAsync(Integer solutionId, final ApiCallback<Void> _callback) throws ApiException {

        okhttp3.Call localVarCall = deleteAiSolutionValidateBeforeCall(solutionId, _callback);
        localVarApiClient.executeAsync(localVarCall, _callback);
        return localVarCall;
    }
    /**
     * Build call for getAiSolution
     * @param solutionId ID AI-решения (required)
     * @param _callback Callback for upload/download progress
     * @return Call to execute
     * @throws ApiException If fail to serialize the request body object
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> Объект JSON c ключом &#x60;ai_solution&#x60; </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Некорректный запрос </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Не авторизован </td><td>  -  </td></tr>
        <tr><td> 403 </td><td> Запрещено </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Не найдено </td><td>  -  </td></tr>
        <tr><td> 429 </td><td> Слишком много запросов </td><td>  -  </td></tr>
        <tr><td> 500 </td><td> Внутренняя ошибка сервера </td><td>  -  </td></tr>
     </table>
     */
    public okhttp3.Call getAiSolutionCall(Integer solutionId, final ApiCallback _callback) throws ApiException {
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
        String localVarPath = "/api/v1/cloud-ai/solutions/{solution_id}"
            .replace("{" + "solution_id" + "}", localVarApiClient.escapeString(solutionId.toString()));

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
    private okhttp3.Call getAiSolutionValidateBeforeCall(Integer solutionId, final ApiCallback _callback) throws ApiException {
        // verify the required parameter 'solutionId' is set
        if (solutionId == null) {
            throw new ApiException("Missing the required parameter 'solutionId' when calling getAiSolution(Async)");
        }

        return getAiSolutionCall(solutionId, _callback);

    }

    /**
     * Получение AI-решения
     * Чтобы получить AI-решение, отправьте GET-запрос на &#x60;/api/v1/cloud-ai/solutions/{solution_id}&#x60;.  Статус решения выводится из текущего состояния его сервера. Тело ответа будет представлять собой объект JSON с ключом &#x60;ai_solution&#x60;.
     * @param solutionId ID AI-решения (required)
     * @return CreateAiSolution201Response
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> Объект JSON c ключом &#x60;ai_solution&#x60; </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Некорректный запрос </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Не авторизован </td><td>  -  </td></tr>
        <tr><td> 403 </td><td> Запрещено </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Не найдено </td><td>  -  </td></tr>
        <tr><td> 429 </td><td> Слишком много запросов </td><td>  -  </td></tr>
        <tr><td> 500 </td><td> Внутренняя ошибка сервера </td><td>  -  </td></tr>
     </table>
     */
    public CreateAiSolution201Response getAiSolution(Integer solutionId) throws ApiException {
        ApiResponse<CreateAiSolution201Response> localVarResp = getAiSolutionWithHttpInfo(solutionId);
        return localVarResp.getData();
    }

    /**
     * Получение AI-решения
     * Чтобы получить AI-решение, отправьте GET-запрос на &#x60;/api/v1/cloud-ai/solutions/{solution_id}&#x60;.  Статус решения выводится из текущего состояния его сервера. Тело ответа будет представлять собой объект JSON с ключом &#x60;ai_solution&#x60;.
     * @param solutionId ID AI-решения (required)
     * @return ApiResponse&lt;CreateAiSolution201Response&gt;
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> Объект JSON c ключом &#x60;ai_solution&#x60; </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Некорректный запрос </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Не авторизован </td><td>  -  </td></tr>
        <tr><td> 403 </td><td> Запрещено </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Не найдено </td><td>  -  </td></tr>
        <tr><td> 429 </td><td> Слишком много запросов </td><td>  -  </td></tr>
        <tr><td> 500 </td><td> Внутренняя ошибка сервера </td><td>  -  </td></tr>
     </table>
     */
    public ApiResponse<CreateAiSolution201Response> getAiSolutionWithHttpInfo(Integer solutionId) throws ApiException {
        okhttp3.Call localVarCall = getAiSolutionValidateBeforeCall(solutionId, null);
        Type localVarReturnType = new TypeToken<CreateAiSolution201Response>(){}.getType();
        return localVarApiClient.execute(localVarCall, localVarReturnType);
    }

    /**
     * Получение AI-решения (asynchronously)
     * Чтобы получить AI-решение, отправьте GET-запрос на &#x60;/api/v1/cloud-ai/solutions/{solution_id}&#x60;.  Статус решения выводится из текущего состояния его сервера. Тело ответа будет представлять собой объект JSON с ключом &#x60;ai_solution&#x60;.
     * @param solutionId ID AI-решения (required)
     * @param _callback The callback to be executed when the API call finishes
     * @return The request call
     * @throws ApiException If fail to process the API call, e.g. serializing the request body object
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> Объект JSON c ключом &#x60;ai_solution&#x60; </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Некорректный запрос </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Не авторизован </td><td>  -  </td></tr>
        <tr><td> 403 </td><td> Запрещено </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Не найдено </td><td>  -  </td></tr>
        <tr><td> 429 </td><td> Слишком много запросов </td><td>  -  </td></tr>
        <tr><td> 500 </td><td> Внутренняя ошибка сервера </td><td>  -  </td></tr>
     </table>
     */
    public okhttp3.Call getAiSolutionAsync(Integer solutionId, final ApiCallback<CreateAiSolution201Response> _callback) throws ApiException {

        okhttp3.Call localVarCall = getAiSolutionValidateBeforeCall(solutionId, _callback);
        Type localVarReturnType = new TypeToken<CreateAiSolution201Response>(){}.getType();
        localVarApiClient.executeAsync(localVarCall, localVarReturnType, _callback);
        return localVarCall;
    }
    /**
     * Build call for getAiSolutionChatFullHistory
     * @param solutionId ID AI-решения (required)
     * @param limit Количество сообщений на странице, от 1 до 10 000 (optional, default to 100)
     * @param offset Количество сообщений, которые нужно пропустить (optional, default to 0)
     * @param _callback Callback for upload/download progress
     * @return Call to execute
     * @throws ApiException If fail to serialize the request body object
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> Объект JSON c ключами &#x60;ai_solution_chat_messages&#x60; и &#x60;meta&#x60; </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Некорректный запрос </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Не авторизован </td><td>  -  </td></tr>
        <tr><td> 403 </td><td> Запрещено </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Не найдено </td><td>  -  </td></tr>
        <tr><td> 429 </td><td> Слишком много запросов </td><td>  -  </td></tr>
        <tr><td> 500 </td><td> Внутренняя ошибка сервера </td><td>  -  </td></tr>
     </table>
     */
    public okhttp3.Call getAiSolutionChatFullHistoryCall(Integer solutionId, Integer limit, Integer offset, final ApiCallback _callback) throws ApiException {
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
        String localVarPath = "/api/v1/cloud-ai/solutions/{solution_id}/chat/history"
            .replace("{" + "solution_id" + "}", localVarApiClient.escapeString(solutionId.toString()));

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
    private okhttp3.Call getAiSolutionChatFullHistoryValidateBeforeCall(Integer solutionId, Integer limit, Integer offset, final ApiCallback _callback) throws ApiException {
        // verify the required parameter 'solutionId' is set
        if (solutionId == null) {
            throw new ApiException("Missing the required parameter 'solutionId' when calling getAiSolutionChatFullHistory(Async)");
        }

        return getAiSolutionChatFullHistoryCall(solutionId, limit, offset, _callback);

    }

    /**
     * Получение всей истории чата AI-решения
     * Чтобы получить сообщения всех разговоров решения, отправьте GET-запрос на &#x60;/api/v1/cloud-ai/solutions/{solution_id}/chat/history&#x60;.  В отличие от &#x60;chat/messages&#x60; (только основной разговор) и &#x60;chat/conversations/{conversation_id}/messages&#x60; (конкретный разговор), возвращает сообщения из всех разговоров сразу. Сообщения отсортированы по дате создания от новых к старым. Тело ответа будет представлять собой объект JSON с ключами &#x60;ai_solution_chat_messages&#x60; и &#x60;meta&#x60;.
     * @param solutionId ID AI-решения (required)
     * @param limit Количество сообщений на странице, от 1 до 10 000 (optional, default to 100)
     * @param offset Количество сообщений, которые нужно пропустить (optional, default to 0)
     * @return GetAiSolutionMainChatHistory200Response
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> Объект JSON c ключами &#x60;ai_solution_chat_messages&#x60; и &#x60;meta&#x60; </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Некорректный запрос </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Не авторизован </td><td>  -  </td></tr>
        <tr><td> 403 </td><td> Запрещено </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Не найдено </td><td>  -  </td></tr>
        <tr><td> 429 </td><td> Слишком много запросов </td><td>  -  </td></tr>
        <tr><td> 500 </td><td> Внутренняя ошибка сервера </td><td>  -  </td></tr>
     </table>
     */
    public GetAiSolutionMainChatHistory200Response getAiSolutionChatFullHistory(Integer solutionId, Integer limit, Integer offset) throws ApiException {
        ApiResponse<GetAiSolutionMainChatHistory200Response> localVarResp = getAiSolutionChatFullHistoryWithHttpInfo(solutionId, limit, offset);
        return localVarResp.getData();
    }

    /**
     * Получение всей истории чата AI-решения
     * Чтобы получить сообщения всех разговоров решения, отправьте GET-запрос на &#x60;/api/v1/cloud-ai/solutions/{solution_id}/chat/history&#x60;.  В отличие от &#x60;chat/messages&#x60; (только основной разговор) и &#x60;chat/conversations/{conversation_id}/messages&#x60; (конкретный разговор), возвращает сообщения из всех разговоров сразу. Сообщения отсортированы по дате создания от новых к старым. Тело ответа будет представлять собой объект JSON с ключами &#x60;ai_solution_chat_messages&#x60; и &#x60;meta&#x60;.
     * @param solutionId ID AI-решения (required)
     * @param limit Количество сообщений на странице, от 1 до 10 000 (optional, default to 100)
     * @param offset Количество сообщений, которые нужно пропустить (optional, default to 0)
     * @return ApiResponse&lt;GetAiSolutionMainChatHistory200Response&gt;
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> Объект JSON c ключами &#x60;ai_solution_chat_messages&#x60; и &#x60;meta&#x60; </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Некорректный запрос </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Не авторизован </td><td>  -  </td></tr>
        <tr><td> 403 </td><td> Запрещено </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Не найдено </td><td>  -  </td></tr>
        <tr><td> 429 </td><td> Слишком много запросов </td><td>  -  </td></tr>
        <tr><td> 500 </td><td> Внутренняя ошибка сервера </td><td>  -  </td></tr>
     </table>
     */
    public ApiResponse<GetAiSolutionMainChatHistory200Response> getAiSolutionChatFullHistoryWithHttpInfo(Integer solutionId, Integer limit, Integer offset) throws ApiException {
        okhttp3.Call localVarCall = getAiSolutionChatFullHistoryValidateBeforeCall(solutionId, limit, offset, null);
        Type localVarReturnType = new TypeToken<GetAiSolutionMainChatHistory200Response>(){}.getType();
        return localVarApiClient.execute(localVarCall, localVarReturnType);
    }

    /**
     * Получение всей истории чата AI-решения (asynchronously)
     * Чтобы получить сообщения всех разговоров решения, отправьте GET-запрос на &#x60;/api/v1/cloud-ai/solutions/{solution_id}/chat/history&#x60;.  В отличие от &#x60;chat/messages&#x60; (только основной разговор) и &#x60;chat/conversations/{conversation_id}/messages&#x60; (конкретный разговор), возвращает сообщения из всех разговоров сразу. Сообщения отсортированы по дате создания от новых к старым. Тело ответа будет представлять собой объект JSON с ключами &#x60;ai_solution_chat_messages&#x60; и &#x60;meta&#x60;.
     * @param solutionId ID AI-решения (required)
     * @param limit Количество сообщений на странице, от 1 до 10 000 (optional, default to 100)
     * @param offset Количество сообщений, которые нужно пропустить (optional, default to 0)
     * @param _callback The callback to be executed when the API call finishes
     * @return The request call
     * @throws ApiException If fail to process the API call, e.g. serializing the request body object
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> Объект JSON c ключами &#x60;ai_solution_chat_messages&#x60; и &#x60;meta&#x60; </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Некорректный запрос </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Не авторизован </td><td>  -  </td></tr>
        <tr><td> 403 </td><td> Запрещено </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Не найдено </td><td>  -  </td></tr>
        <tr><td> 429 </td><td> Слишком много запросов </td><td>  -  </td></tr>
        <tr><td> 500 </td><td> Внутренняя ошибка сервера </td><td>  -  </td></tr>
     </table>
     */
    public okhttp3.Call getAiSolutionChatFullHistoryAsync(Integer solutionId, Integer limit, Integer offset, final ApiCallback<GetAiSolutionMainChatHistory200Response> _callback) throws ApiException {

        okhttp3.Call localVarCall = getAiSolutionChatFullHistoryValidateBeforeCall(solutionId, limit, offset, _callback);
        Type localVarReturnType = new TypeToken<GetAiSolutionMainChatHistory200Response>(){}.getType();
        localVarApiClient.executeAsync(localVarCall, localVarReturnType, _callback);
        return localVarCall;
    }
    /**
     * Build call for getAiSolutionChatHistory
     * @param solutionId ID AI-решения (required)
     * @param conversationId ID разговора. У основного разговора решения — &#x60;solution-&lt;solution_id&gt;&#x60; (required)
     * @param limit Количество сообщений на странице, от 1 до 10 000 (optional, default to 100)
     * @param offset Количество сообщений, которые нужно пропустить (optional, default to 0)
     * @param _callback Callback for upload/download progress
     * @return Call to execute
     * @throws ApiException If fail to serialize the request body object
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> Объект JSON c ключами &#x60;ai_solution_chat_messages&#x60; и &#x60;meta&#x60; </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Некорректный запрос </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Не авторизован </td><td>  -  </td></tr>
        <tr><td> 403 </td><td> Запрещено </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Не найдено </td><td>  -  </td></tr>
        <tr><td> 429 </td><td> Слишком много запросов </td><td>  -  </td></tr>
        <tr><td> 500 </td><td> Внутренняя ошибка сервера </td><td>  -  </td></tr>
     </table>
     */
    public okhttp3.Call getAiSolutionChatHistoryCall(Integer solutionId, String conversationId, Integer limit, Integer offset, final ApiCallback _callback) throws ApiException {
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
        String localVarPath = "/api/v1/cloud-ai/solutions/{solution_id}/chat/conversations/{conversation_id}/messages"
            .replace("{" + "solution_id" + "}", localVarApiClient.escapeString(solutionId.toString()))
            .replace("{" + "conversation_id" + "}", localVarApiClient.escapeString(conversationId.toString()));

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
    private okhttp3.Call getAiSolutionChatHistoryValidateBeforeCall(Integer solutionId, String conversationId, Integer limit, Integer offset, final ApiCallback _callback) throws ApiException {
        // verify the required parameter 'solutionId' is set
        if (solutionId == null) {
            throw new ApiException("Missing the required parameter 'solutionId' when calling getAiSolutionChatHistory(Async)");
        }

        // verify the required parameter 'conversationId' is set
        if (conversationId == null) {
            throw new ApiException("Missing the required parameter 'conversationId' when calling getAiSolutionChatHistory(Async)");
        }

        return getAiSolutionChatHistoryCall(solutionId, conversationId, limit, offset, _callback);

    }

    /**
     * Получение истории разговора AI-решения
     * Чтобы получить историю разговора, отправьте GET-запрос на &#x60;/api/v1/cloud-ai/solutions/{solution_id}/chat/conversations/{conversation_id}/messages&#x60;.  Сообщения отсортированы по дате создания от новых к старым: первая страница — последние сообщения разговора. Для несуществующего разговора возвращается пустой список. Тело ответа будет представлять собой объект JSON с ключами &#x60;ai_solution_chat_messages&#x60; и &#x60;meta&#x60;.
     * @param solutionId ID AI-решения (required)
     * @param conversationId ID разговора. У основного разговора решения — &#x60;solution-&lt;solution_id&gt;&#x60; (required)
     * @param limit Количество сообщений на странице, от 1 до 10 000 (optional, default to 100)
     * @param offset Количество сообщений, которые нужно пропустить (optional, default to 0)
     * @return GetAiSolutionMainChatHistory200Response
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> Объект JSON c ключами &#x60;ai_solution_chat_messages&#x60; и &#x60;meta&#x60; </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Некорректный запрос </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Не авторизован </td><td>  -  </td></tr>
        <tr><td> 403 </td><td> Запрещено </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Не найдено </td><td>  -  </td></tr>
        <tr><td> 429 </td><td> Слишком много запросов </td><td>  -  </td></tr>
        <tr><td> 500 </td><td> Внутренняя ошибка сервера </td><td>  -  </td></tr>
     </table>
     */
    public GetAiSolutionMainChatHistory200Response getAiSolutionChatHistory(Integer solutionId, String conversationId, Integer limit, Integer offset) throws ApiException {
        ApiResponse<GetAiSolutionMainChatHistory200Response> localVarResp = getAiSolutionChatHistoryWithHttpInfo(solutionId, conversationId, limit, offset);
        return localVarResp.getData();
    }

    /**
     * Получение истории разговора AI-решения
     * Чтобы получить историю разговора, отправьте GET-запрос на &#x60;/api/v1/cloud-ai/solutions/{solution_id}/chat/conversations/{conversation_id}/messages&#x60;.  Сообщения отсортированы по дате создания от новых к старым: первая страница — последние сообщения разговора. Для несуществующего разговора возвращается пустой список. Тело ответа будет представлять собой объект JSON с ключами &#x60;ai_solution_chat_messages&#x60; и &#x60;meta&#x60;.
     * @param solutionId ID AI-решения (required)
     * @param conversationId ID разговора. У основного разговора решения — &#x60;solution-&lt;solution_id&gt;&#x60; (required)
     * @param limit Количество сообщений на странице, от 1 до 10 000 (optional, default to 100)
     * @param offset Количество сообщений, которые нужно пропустить (optional, default to 0)
     * @return ApiResponse&lt;GetAiSolutionMainChatHistory200Response&gt;
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> Объект JSON c ключами &#x60;ai_solution_chat_messages&#x60; и &#x60;meta&#x60; </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Некорректный запрос </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Не авторизован </td><td>  -  </td></tr>
        <tr><td> 403 </td><td> Запрещено </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Не найдено </td><td>  -  </td></tr>
        <tr><td> 429 </td><td> Слишком много запросов </td><td>  -  </td></tr>
        <tr><td> 500 </td><td> Внутренняя ошибка сервера </td><td>  -  </td></tr>
     </table>
     */
    public ApiResponse<GetAiSolutionMainChatHistory200Response> getAiSolutionChatHistoryWithHttpInfo(Integer solutionId, String conversationId, Integer limit, Integer offset) throws ApiException {
        okhttp3.Call localVarCall = getAiSolutionChatHistoryValidateBeforeCall(solutionId, conversationId, limit, offset, null);
        Type localVarReturnType = new TypeToken<GetAiSolutionMainChatHistory200Response>(){}.getType();
        return localVarApiClient.execute(localVarCall, localVarReturnType);
    }

    /**
     * Получение истории разговора AI-решения (asynchronously)
     * Чтобы получить историю разговора, отправьте GET-запрос на &#x60;/api/v1/cloud-ai/solutions/{solution_id}/chat/conversations/{conversation_id}/messages&#x60;.  Сообщения отсортированы по дате создания от новых к старым: первая страница — последние сообщения разговора. Для несуществующего разговора возвращается пустой список. Тело ответа будет представлять собой объект JSON с ключами &#x60;ai_solution_chat_messages&#x60; и &#x60;meta&#x60;.
     * @param solutionId ID AI-решения (required)
     * @param conversationId ID разговора. У основного разговора решения — &#x60;solution-&lt;solution_id&gt;&#x60; (required)
     * @param limit Количество сообщений на странице, от 1 до 10 000 (optional, default to 100)
     * @param offset Количество сообщений, которые нужно пропустить (optional, default to 0)
     * @param _callback The callback to be executed when the API call finishes
     * @return The request call
     * @throws ApiException If fail to process the API call, e.g. serializing the request body object
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> Объект JSON c ключами &#x60;ai_solution_chat_messages&#x60; и &#x60;meta&#x60; </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Некорректный запрос </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Не авторизован </td><td>  -  </td></tr>
        <tr><td> 403 </td><td> Запрещено </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Не найдено </td><td>  -  </td></tr>
        <tr><td> 429 </td><td> Слишком много запросов </td><td>  -  </td></tr>
        <tr><td> 500 </td><td> Внутренняя ошибка сервера </td><td>  -  </td></tr>
     </table>
     */
    public okhttp3.Call getAiSolutionChatHistoryAsync(Integer solutionId, String conversationId, Integer limit, Integer offset, final ApiCallback<GetAiSolutionMainChatHistory200Response> _callback) throws ApiException {

        okhttp3.Call localVarCall = getAiSolutionChatHistoryValidateBeforeCall(solutionId, conversationId, limit, offset, _callback);
        Type localVarReturnType = new TypeToken<GetAiSolutionMainChatHistory200Response>(){}.getType();
        localVarApiClient.executeAsync(localVarCall, localVarReturnType, _callback);
        return localVarCall;
    }
    /**
     * Build call for getAiSolutionChatStatus
     * @param solutionId ID AI-решения (required)
     * @param _callback Callback for upload/download progress
     * @return Call to execute
     * @throws ApiException If fail to serialize the request body object
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> Объект JSON c ключом &#x60;ai_solution_chat_status&#x60; </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Некорректный запрос </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Не авторизован </td><td>  -  </td></tr>
        <tr><td> 403 </td><td> Запрещено </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Не найдено </td><td>  -  </td></tr>
        <tr><td> 429 </td><td> Слишком много запросов </td><td>  -  </td></tr>
        <tr><td> 500 </td><td> Внутренняя ошибка сервера </td><td>  -  </td></tr>
     </table>
     */
    public okhttp3.Call getAiSolutionChatStatusCall(Integer solutionId, final ApiCallback _callback) throws ApiException {
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
        String localVarPath = "/api/v1/cloud-ai/solutions/{solution_id}/chat/status"
            .replace("{" + "solution_id" + "}", localVarApiClient.escapeString(solutionId.toString()));

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
    private okhttp3.Call getAiSolutionChatStatusValidateBeforeCall(Integer solutionId, final ApiCallback _callback) throws ApiException {
        // verify the required parameter 'solutionId' is set
        if (solutionId == null) {
            throw new ApiException("Missing the required parameter 'solutionId' when calling getAiSolutionChatStatus(Async)");
        }

        return getAiSolutionChatStatusCall(solutionId, _callback);

    }

    /**
     * Получение статуса чата AI-решения
     * Чтобы узнать, готов ли агент решения принимать сообщения, отправьте GET-запрос на &#x60;/api/v1/cloud-ai/solutions/{solution_id}/chat/status&#x60;.  Готовность чата отличается от статуса решения: тот выводится из состояния сервера, а сервер включается задолго до того, как будет установлено и настроено ПО агента. После создания или запуска решения опрашивайте этот метод, пока &#x60;ready&#x60; не станет &#x60;true&#x60;. Тело ответа будет представлять собой объект JSON с ключом &#x60;ai_solution_chat_status&#x60;.
     * @param solutionId ID AI-решения (required)
     * @return GetAiSolutionChatStatus200Response
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> Объект JSON c ключом &#x60;ai_solution_chat_status&#x60; </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Некорректный запрос </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Не авторизован </td><td>  -  </td></tr>
        <tr><td> 403 </td><td> Запрещено </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Не найдено </td><td>  -  </td></tr>
        <tr><td> 429 </td><td> Слишком много запросов </td><td>  -  </td></tr>
        <tr><td> 500 </td><td> Внутренняя ошибка сервера </td><td>  -  </td></tr>
     </table>
     */
    public GetAiSolutionChatStatus200Response getAiSolutionChatStatus(Integer solutionId) throws ApiException {
        ApiResponse<GetAiSolutionChatStatus200Response> localVarResp = getAiSolutionChatStatusWithHttpInfo(solutionId);
        return localVarResp.getData();
    }

    /**
     * Получение статуса чата AI-решения
     * Чтобы узнать, готов ли агент решения принимать сообщения, отправьте GET-запрос на &#x60;/api/v1/cloud-ai/solutions/{solution_id}/chat/status&#x60;.  Готовность чата отличается от статуса решения: тот выводится из состояния сервера, а сервер включается задолго до того, как будет установлено и настроено ПО агента. После создания или запуска решения опрашивайте этот метод, пока &#x60;ready&#x60; не станет &#x60;true&#x60;. Тело ответа будет представлять собой объект JSON с ключом &#x60;ai_solution_chat_status&#x60;.
     * @param solutionId ID AI-решения (required)
     * @return ApiResponse&lt;GetAiSolutionChatStatus200Response&gt;
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> Объект JSON c ключом &#x60;ai_solution_chat_status&#x60; </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Некорректный запрос </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Не авторизован </td><td>  -  </td></tr>
        <tr><td> 403 </td><td> Запрещено </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Не найдено </td><td>  -  </td></tr>
        <tr><td> 429 </td><td> Слишком много запросов </td><td>  -  </td></tr>
        <tr><td> 500 </td><td> Внутренняя ошибка сервера </td><td>  -  </td></tr>
     </table>
     */
    public ApiResponse<GetAiSolutionChatStatus200Response> getAiSolutionChatStatusWithHttpInfo(Integer solutionId) throws ApiException {
        okhttp3.Call localVarCall = getAiSolutionChatStatusValidateBeforeCall(solutionId, null);
        Type localVarReturnType = new TypeToken<GetAiSolutionChatStatus200Response>(){}.getType();
        return localVarApiClient.execute(localVarCall, localVarReturnType);
    }

    /**
     * Получение статуса чата AI-решения (asynchronously)
     * Чтобы узнать, готов ли агент решения принимать сообщения, отправьте GET-запрос на &#x60;/api/v1/cloud-ai/solutions/{solution_id}/chat/status&#x60;.  Готовность чата отличается от статуса решения: тот выводится из состояния сервера, а сервер включается задолго до того, как будет установлено и настроено ПО агента. После создания или запуска решения опрашивайте этот метод, пока &#x60;ready&#x60; не станет &#x60;true&#x60;. Тело ответа будет представлять собой объект JSON с ключом &#x60;ai_solution_chat_status&#x60;.
     * @param solutionId ID AI-решения (required)
     * @param _callback The callback to be executed when the API call finishes
     * @return The request call
     * @throws ApiException If fail to process the API call, e.g. serializing the request body object
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> Объект JSON c ключом &#x60;ai_solution_chat_status&#x60; </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Некорректный запрос </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Не авторизован </td><td>  -  </td></tr>
        <tr><td> 403 </td><td> Запрещено </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Не найдено </td><td>  -  </td></tr>
        <tr><td> 429 </td><td> Слишком много запросов </td><td>  -  </td></tr>
        <tr><td> 500 </td><td> Внутренняя ошибка сервера </td><td>  -  </td></tr>
     </table>
     */
    public okhttp3.Call getAiSolutionChatStatusAsync(Integer solutionId, final ApiCallback<GetAiSolutionChatStatus200Response> _callback) throws ApiException {

        okhttp3.Call localVarCall = getAiSolutionChatStatusValidateBeforeCall(solutionId, _callback);
        Type localVarReturnType = new TypeToken<GetAiSolutionChatStatus200Response>(){}.getType();
        localVarApiClient.executeAsync(localVarCall, localVarReturnType, _callback);
        return localVarCall;
    }
    /**
     * Build call for getAiSolutionMainChatHistory
     * @param solutionId ID AI-решения (required)
     * @param limit Количество сообщений на странице, от 1 до 10 000 (optional, default to 100)
     * @param offset Количество сообщений, которые нужно пропустить (optional, default to 0)
     * @param _callback Callback for upload/download progress
     * @return Call to execute
     * @throws ApiException If fail to serialize the request body object
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> Объект JSON c ключами &#x60;ai_solution_chat_messages&#x60; и &#x60;meta&#x60; </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Некорректный запрос </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Не авторизован </td><td>  -  </td></tr>
        <tr><td> 403 </td><td> Запрещено </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Не найдено </td><td>  -  </td></tr>
        <tr><td> 429 </td><td> Слишком много запросов </td><td>  -  </td></tr>
        <tr><td> 500 </td><td> Внутренняя ошибка сервера </td><td>  -  </td></tr>
     </table>
     */
    public okhttp3.Call getAiSolutionMainChatHistoryCall(Integer solutionId, Integer limit, Integer offset, final ApiCallback _callback) throws ApiException {
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
        String localVarPath = "/api/v1/cloud-ai/solutions/{solution_id}/chat/messages"
            .replace("{" + "solution_id" + "}", localVarApiClient.escapeString(solutionId.toString()));

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
    private okhttp3.Call getAiSolutionMainChatHistoryValidateBeforeCall(Integer solutionId, Integer limit, Integer offset, final ApiCallback _callback) throws ApiException {
        // verify the required parameter 'solutionId' is set
        if (solutionId == null) {
            throw new ApiException("Missing the required parameter 'solutionId' when calling getAiSolutionMainChatHistory(Async)");
        }

        return getAiSolutionMainChatHistoryCall(solutionId, limit, offset, _callback);

    }

    /**
     * Получение истории основного разговора AI-решения
     * Чтобы получить историю основного разговора решения, отправьте GET-запрос на &#x60;/api/v1/cloud-ai/solutions/{solution_id}/chat/messages&#x60;.  ID основного разговора знать не нужно; для других разговоров используйте метод с явным &#x60;conversation_id&#x60;. Сообщения отсортированы по дате создания от новых к старым: первая страница — последние сообщения. Тело ответа будет представлять собой объект JSON с ключами &#x60;ai_solution_chat_messages&#x60; и &#x60;meta&#x60; (&#x60;meta.total&#x60; — общее количество сообщений).
     * @param solutionId ID AI-решения (required)
     * @param limit Количество сообщений на странице, от 1 до 10 000 (optional, default to 100)
     * @param offset Количество сообщений, которые нужно пропустить (optional, default to 0)
     * @return GetAiSolutionMainChatHistory200Response
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> Объект JSON c ключами &#x60;ai_solution_chat_messages&#x60; и &#x60;meta&#x60; </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Некорректный запрос </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Не авторизован </td><td>  -  </td></tr>
        <tr><td> 403 </td><td> Запрещено </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Не найдено </td><td>  -  </td></tr>
        <tr><td> 429 </td><td> Слишком много запросов </td><td>  -  </td></tr>
        <tr><td> 500 </td><td> Внутренняя ошибка сервера </td><td>  -  </td></tr>
     </table>
     */
    public GetAiSolutionMainChatHistory200Response getAiSolutionMainChatHistory(Integer solutionId, Integer limit, Integer offset) throws ApiException {
        ApiResponse<GetAiSolutionMainChatHistory200Response> localVarResp = getAiSolutionMainChatHistoryWithHttpInfo(solutionId, limit, offset);
        return localVarResp.getData();
    }

    /**
     * Получение истории основного разговора AI-решения
     * Чтобы получить историю основного разговора решения, отправьте GET-запрос на &#x60;/api/v1/cloud-ai/solutions/{solution_id}/chat/messages&#x60;.  ID основного разговора знать не нужно; для других разговоров используйте метод с явным &#x60;conversation_id&#x60;. Сообщения отсортированы по дате создания от новых к старым: первая страница — последние сообщения. Тело ответа будет представлять собой объект JSON с ключами &#x60;ai_solution_chat_messages&#x60; и &#x60;meta&#x60; (&#x60;meta.total&#x60; — общее количество сообщений).
     * @param solutionId ID AI-решения (required)
     * @param limit Количество сообщений на странице, от 1 до 10 000 (optional, default to 100)
     * @param offset Количество сообщений, которые нужно пропустить (optional, default to 0)
     * @return ApiResponse&lt;GetAiSolutionMainChatHistory200Response&gt;
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> Объект JSON c ключами &#x60;ai_solution_chat_messages&#x60; и &#x60;meta&#x60; </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Некорректный запрос </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Не авторизован </td><td>  -  </td></tr>
        <tr><td> 403 </td><td> Запрещено </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Не найдено </td><td>  -  </td></tr>
        <tr><td> 429 </td><td> Слишком много запросов </td><td>  -  </td></tr>
        <tr><td> 500 </td><td> Внутренняя ошибка сервера </td><td>  -  </td></tr>
     </table>
     */
    public ApiResponse<GetAiSolutionMainChatHistory200Response> getAiSolutionMainChatHistoryWithHttpInfo(Integer solutionId, Integer limit, Integer offset) throws ApiException {
        okhttp3.Call localVarCall = getAiSolutionMainChatHistoryValidateBeforeCall(solutionId, limit, offset, null);
        Type localVarReturnType = new TypeToken<GetAiSolutionMainChatHistory200Response>(){}.getType();
        return localVarApiClient.execute(localVarCall, localVarReturnType);
    }

    /**
     * Получение истории основного разговора AI-решения (asynchronously)
     * Чтобы получить историю основного разговора решения, отправьте GET-запрос на &#x60;/api/v1/cloud-ai/solutions/{solution_id}/chat/messages&#x60;.  ID основного разговора знать не нужно; для других разговоров используйте метод с явным &#x60;conversation_id&#x60;. Сообщения отсортированы по дате создания от новых к старым: первая страница — последние сообщения. Тело ответа будет представлять собой объект JSON с ключами &#x60;ai_solution_chat_messages&#x60; и &#x60;meta&#x60; (&#x60;meta.total&#x60; — общее количество сообщений).
     * @param solutionId ID AI-решения (required)
     * @param limit Количество сообщений на странице, от 1 до 10 000 (optional, default to 100)
     * @param offset Количество сообщений, которые нужно пропустить (optional, default to 0)
     * @param _callback The callback to be executed when the API call finishes
     * @return The request call
     * @throws ApiException If fail to process the API call, e.g. serializing the request body object
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> Объект JSON c ключами &#x60;ai_solution_chat_messages&#x60; и &#x60;meta&#x60; </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Некорректный запрос </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Не авторизован </td><td>  -  </td></tr>
        <tr><td> 403 </td><td> Запрещено </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Не найдено </td><td>  -  </td></tr>
        <tr><td> 429 </td><td> Слишком много запросов </td><td>  -  </td></tr>
        <tr><td> 500 </td><td> Внутренняя ошибка сервера </td><td>  -  </td></tr>
     </table>
     */
    public okhttp3.Call getAiSolutionMainChatHistoryAsync(Integer solutionId, Integer limit, Integer offset, final ApiCallback<GetAiSolutionMainChatHistory200Response> _callback) throws ApiException {

        okhttp3.Call localVarCall = getAiSolutionMainChatHistoryValidateBeforeCall(solutionId, limit, offset, _callback);
        Type localVarReturnType = new TypeToken<GetAiSolutionMainChatHistory200Response>(){}.getType();
        localVarApiClient.executeAsync(localVarCall, localVarReturnType, _callback);
        return localVarCall;
    }
    /**
     * Build call for listAiSolutionConversations
     * @param solutionId ID AI-решения (required)
     * @param _callback Callback for upload/download progress
     * @return Call to execute
     * @throws ApiException If fail to serialize the request body object
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> Объект JSON c ключами &#x60;ai_solution_conversations&#x60; и &#x60;meta&#x60; </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Некорректный запрос </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Не авторизован </td><td>  -  </td></tr>
        <tr><td> 403 </td><td> Запрещено </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Не найдено </td><td>  -  </td></tr>
        <tr><td> 429 </td><td> Слишком много запросов </td><td>  -  </td></tr>
        <tr><td> 500 </td><td> Внутренняя ошибка сервера </td><td>  -  </td></tr>
     </table>
     */
    public okhttp3.Call listAiSolutionConversationsCall(Integer solutionId, final ApiCallback _callback) throws ApiException {
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
        String localVarPath = "/api/v1/cloud-ai/solutions/{solution_id}/chat/conversations"
            .replace("{" + "solution_id" + "}", localVarApiClient.escapeString(solutionId.toString()));

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
    private okhttp3.Call listAiSolutionConversationsValidateBeforeCall(Integer solutionId, final ApiCallback _callback) throws ApiException {
        // verify the required parameter 'solutionId' is set
        if (solutionId == null) {
            throw new ApiException("Missing the required parameter 'solutionId' when calling listAiSolutionConversations(Async)");
        }

        return listAiSolutionConversationsCall(solutionId, _callback);

    }

    /**
     * Получение списка разговоров AI-решения
     * Чтобы получить список разговоров с агентом решения, отправьте GET-запрос на &#x60;/api/v1/cloud-ai/solutions/{solution_id}/chat/conversations&#x60;.  Возвращает все разговоры решения, в которых есть сообщения, включая основной, отсортированные по дате последнего сообщения от новых к старым. Тело ответа будет представлять собой объект JSON с ключами &#x60;ai_solution_conversations&#x60; и &#x60;meta&#x60;.
     * @param solutionId ID AI-решения (required)
     * @return ListAiSolutionConversations200Response
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> Объект JSON c ключами &#x60;ai_solution_conversations&#x60; и &#x60;meta&#x60; </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Некорректный запрос </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Не авторизован </td><td>  -  </td></tr>
        <tr><td> 403 </td><td> Запрещено </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Не найдено </td><td>  -  </td></tr>
        <tr><td> 429 </td><td> Слишком много запросов </td><td>  -  </td></tr>
        <tr><td> 500 </td><td> Внутренняя ошибка сервера </td><td>  -  </td></tr>
     </table>
     */
    public ListAiSolutionConversations200Response listAiSolutionConversations(Integer solutionId) throws ApiException {
        ApiResponse<ListAiSolutionConversations200Response> localVarResp = listAiSolutionConversationsWithHttpInfo(solutionId);
        return localVarResp.getData();
    }

    /**
     * Получение списка разговоров AI-решения
     * Чтобы получить список разговоров с агентом решения, отправьте GET-запрос на &#x60;/api/v1/cloud-ai/solutions/{solution_id}/chat/conversations&#x60;.  Возвращает все разговоры решения, в которых есть сообщения, включая основной, отсортированные по дате последнего сообщения от новых к старым. Тело ответа будет представлять собой объект JSON с ключами &#x60;ai_solution_conversations&#x60; и &#x60;meta&#x60;.
     * @param solutionId ID AI-решения (required)
     * @return ApiResponse&lt;ListAiSolutionConversations200Response&gt;
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> Объект JSON c ключами &#x60;ai_solution_conversations&#x60; и &#x60;meta&#x60; </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Некорректный запрос </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Не авторизован </td><td>  -  </td></tr>
        <tr><td> 403 </td><td> Запрещено </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Не найдено </td><td>  -  </td></tr>
        <tr><td> 429 </td><td> Слишком много запросов </td><td>  -  </td></tr>
        <tr><td> 500 </td><td> Внутренняя ошибка сервера </td><td>  -  </td></tr>
     </table>
     */
    public ApiResponse<ListAiSolutionConversations200Response> listAiSolutionConversationsWithHttpInfo(Integer solutionId) throws ApiException {
        okhttp3.Call localVarCall = listAiSolutionConversationsValidateBeforeCall(solutionId, null);
        Type localVarReturnType = new TypeToken<ListAiSolutionConversations200Response>(){}.getType();
        return localVarApiClient.execute(localVarCall, localVarReturnType);
    }

    /**
     * Получение списка разговоров AI-решения (asynchronously)
     * Чтобы получить список разговоров с агентом решения, отправьте GET-запрос на &#x60;/api/v1/cloud-ai/solutions/{solution_id}/chat/conversations&#x60;.  Возвращает все разговоры решения, в которых есть сообщения, включая основной, отсортированные по дате последнего сообщения от новых к старым. Тело ответа будет представлять собой объект JSON с ключами &#x60;ai_solution_conversations&#x60; и &#x60;meta&#x60;.
     * @param solutionId ID AI-решения (required)
     * @param _callback The callback to be executed when the API call finishes
     * @return The request call
     * @throws ApiException If fail to process the API call, e.g. serializing the request body object
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> Объект JSON c ключами &#x60;ai_solution_conversations&#x60; и &#x60;meta&#x60; </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Некорректный запрос </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Не авторизован </td><td>  -  </td></tr>
        <tr><td> 403 </td><td> Запрещено </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Не найдено </td><td>  -  </td></tr>
        <tr><td> 429 </td><td> Слишком много запросов </td><td>  -  </td></tr>
        <tr><td> 500 </td><td> Внутренняя ошибка сервера </td><td>  -  </td></tr>
     </table>
     */
    public okhttp3.Call listAiSolutionConversationsAsync(Integer solutionId, final ApiCallback<ListAiSolutionConversations200Response> _callback) throws ApiException {

        okhttp3.Call localVarCall = listAiSolutionConversationsValidateBeforeCall(solutionId, _callback);
        Type localVarReturnType = new TypeToken<ListAiSolutionConversations200Response>(){}.getType();
        localVarApiClient.executeAsync(localVarCall, localVarReturnType, _callback);
        return localVarCall;
    }
    /**
     * Build call for listAiSolutionTelegramBots
     * @param solutionId ID AI-решения (required)
     * @param _callback Callback for upload/download progress
     * @return Call to execute
     * @throws ApiException If fail to serialize the request body object
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> Объект JSON c ключами &#x60;ai_solution_telegram_bots&#x60; и &#x60;meta&#x60; </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Некорректный запрос </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Не авторизован </td><td>  -  </td></tr>
        <tr><td> 403 </td><td> Запрещено </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Не найдено </td><td>  -  </td></tr>
        <tr><td> 429 </td><td> Слишком много запросов </td><td>  -  </td></tr>
        <tr><td> 500 </td><td> Внутренняя ошибка сервера </td><td>  -  </td></tr>
        <tr><td> 502 </td><td> Сервер решения ответил ошибкой (&#x60;ai_solution_chat_request_failed&#x60;) </td><td>  -  </td></tr>
        <tr><td> 503 </td><td> Сервер решения недоступен или не поддерживает Telegram — во втором случае пересоздайте решение (&#x60;ai_solution_chat_is_unavailable&#x60;) </td><td>  -  </td></tr>
     </table>
     */
    public okhttp3.Call listAiSolutionTelegramBotsCall(Integer solutionId, final ApiCallback _callback) throws ApiException {
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
        String localVarPath = "/api/v1/cloud-ai/solutions/{solution_id}/telegram/bots"
            .replace("{" + "solution_id" + "}", localVarApiClient.escapeString(solutionId.toString()));

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
    private okhttp3.Call listAiSolutionTelegramBotsValidateBeforeCall(Integer solutionId, final ApiCallback _callback) throws ApiException {
        // verify the required parameter 'solutionId' is set
        if (solutionId == null) {
            throw new ApiException("Missing the required parameter 'solutionId' when calling listAiSolutionTelegramBots(Async)");
        }

        return listAiSolutionTelegramBotsCall(solutionId, _callback);

    }

    /**
     * Получение списка Telegram-ботов AI-решения
     * Чтобы получить список Telegram-ботов решения, отправьте GET-запрос на &#x60;/api/v1/cloud-ai/solutions/{solution_id}/telegram/bots&#x60;.  Возвращает подключённых к решению ботов и состояние их подтверждения. Список читается с сервера решения. Тело ответа будет представлять собой объект JSON с ключами &#x60;ai_solution_telegram_bots&#x60; и &#x60;meta&#x60;.  Перед вызовом убедитесь, что чат готов (&#x60;ready: true&#x60; в методе получения статуса чата): пока сервер решения разворачивается или выключен, запрос завершается ошибкой.
     * @param solutionId ID AI-решения (required)
     * @return ListAiSolutionTelegramBots200Response
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> Объект JSON c ключами &#x60;ai_solution_telegram_bots&#x60; и &#x60;meta&#x60; </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Некорректный запрос </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Не авторизован </td><td>  -  </td></tr>
        <tr><td> 403 </td><td> Запрещено </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Не найдено </td><td>  -  </td></tr>
        <tr><td> 429 </td><td> Слишком много запросов </td><td>  -  </td></tr>
        <tr><td> 500 </td><td> Внутренняя ошибка сервера </td><td>  -  </td></tr>
        <tr><td> 502 </td><td> Сервер решения ответил ошибкой (&#x60;ai_solution_chat_request_failed&#x60;) </td><td>  -  </td></tr>
        <tr><td> 503 </td><td> Сервер решения недоступен или не поддерживает Telegram — во втором случае пересоздайте решение (&#x60;ai_solution_chat_is_unavailable&#x60;) </td><td>  -  </td></tr>
     </table>
     */
    public ListAiSolutionTelegramBots200Response listAiSolutionTelegramBots(Integer solutionId) throws ApiException {
        ApiResponse<ListAiSolutionTelegramBots200Response> localVarResp = listAiSolutionTelegramBotsWithHttpInfo(solutionId);
        return localVarResp.getData();
    }

    /**
     * Получение списка Telegram-ботов AI-решения
     * Чтобы получить список Telegram-ботов решения, отправьте GET-запрос на &#x60;/api/v1/cloud-ai/solutions/{solution_id}/telegram/bots&#x60;.  Возвращает подключённых к решению ботов и состояние их подтверждения. Список читается с сервера решения. Тело ответа будет представлять собой объект JSON с ключами &#x60;ai_solution_telegram_bots&#x60; и &#x60;meta&#x60;.  Перед вызовом убедитесь, что чат готов (&#x60;ready: true&#x60; в методе получения статуса чата): пока сервер решения разворачивается или выключен, запрос завершается ошибкой.
     * @param solutionId ID AI-решения (required)
     * @return ApiResponse&lt;ListAiSolutionTelegramBots200Response&gt;
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> Объект JSON c ключами &#x60;ai_solution_telegram_bots&#x60; и &#x60;meta&#x60; </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Некорректный запрос </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Не авторизован </td><td>  -  </td></tr>
        <tr><td> 403 </td><td> Запрещено </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Не найдено </td><td>  -  </td></tr>
        <tr><td> 429 </td><td> Слишком много запросов </td><td>  -  </td></tr>
        <tr><td> 500 </td><td> Внутренняя ошибка сервера </td><td>  -  </td></tr>
        <tr><td> 502 </td><td> Сервер решения ответил ошибкой (&#x60;ai_solution_chat_request_failed&#x60;) </td><td>  -  </td></tr>
        <tr><td> 503 </td><td> Сервер решения недоступен или не поддерживает Telegram — во втором случае пересоздайте решение (&#x60;ai_solution_chat_is_unavailable&#x60;) </td><td>  -  </td></tr>
     </table>
     */
    public ApiResponse<ListAiSolutionTelegramBots200Response> listAiSolutionTelegramBotsWithHttpInfo(Integer solutionId) throws ApiException {
        okhttp3.Call localVarCall = listAiSolutionTelegramBotsValidateBeforeCall(solutionId, null);
        Type localVarReturnType = new TypeToken<ListAiSolutionTelegramBots200Response>(){}.getType();
        return localVarApiClient.execute(localVarCall, localVarReturnType);
    }

    /**
     * Получение списка Telegram-ботов AI-решения (asynchronously)
     * Чтобы получить список Telegram-ботов решения, отправьте GET-запрос на &#x60;/api/v1/cloud-ai/solutions/{solution_id}/telegram/bots&#x60;.  Возвращает подключённых к решению ботов и состояние их подтверждения. Список читается с сервера решения. Тело ответа будет представлять собой объект JSON с ключами &#x60;ai_solution_telegram_bots&#x60; и &#x60;meta&#x60;.  Перед вызовом убедитесь, что чат готов (&#x60;ready: true&#x60; в методе получения статуса чата): пока сервер решения разворачивается или выключен, запрос завершается ошибкой.
     * @param solutionId ID AI-решения (required)
     * @param _callback The callback to be executed when the API call finishes
     * @return The request call
     * @throws ApiException If fail to process the API call, e.g. serializing the request body object
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> Объект JSON c ключами &#x60;ai_solution_telegram_bots&#x60; и &#x60;meta&#x60; </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Некорректный запрос </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Не авторизован </td><td>  -  </td></tr>
        <tr><td> 403 </td><td> Запрещено </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Не найдено </td><td>  -  </td></tr>
        <tr><td> 429 </td><td> Слишком много запросов </td><td>  -  </td></tr>
        <tr><td> 500 </td><td> Внутренняя ошибка сервера </td><td>  -  </td></tr>
        <tr><td> 502 </td><td> Сервер решения ответил ошибкой (&#x60;ai_solution_chat_request_failed&#x60;) </td><td>  -  </td></tr>
        <tr><td> 503 </td><td> Сервер решения недоступен или не поддерживает Telegram — во втором случае пересоздайте решение (&#x60;ai_solution_chat_is_unavailable&#x60;) </td><td>  -  </td></tr>
     </table>
     */
    public okhttp3.Call listAiSolutionTelegramBotsAsync(Integer solutionId, final ApiCallback<ListAiSolutionTelegramBots200Response> _callback) throws ApiException {

        okhttp3.Call localVarCall = listAiSolutionTelegramBotsValidateBeforeCall(solutionId, _callback);
        Type localVarReturnType = new TypeToken<ListAiSolutionTelegramBots200Response>(){}.getType();
        localVarApiClient.executeAsync(localVarCall, localVarReturnType, _callback);
        return localVarCall;
    }
    /**
     * Build call for listAiSolutionTypes
     * @param _callback Callback for upload/download progress
     * @return Call to execute
     * @throws ApiException If fail to serialize the request body object
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> Объект JSON c ключами &#x60;ai_solution_types&#x60; и &#x60;meta&#x60; </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Некорректный запрос </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Не авторизован </td><td>  -  </td></tr>
        <tr><td> 403 </td><td> Запрещено </td><td>  -  </td></tr>
        <tr><td> 429 </td><td> Слишком много запросов </td><td>  -  </td></tr>
        <tr><td> 500 </td><td> Внутренняя ошибка сервера </td><td>  -  </td></tr>
     </table>
     */
    public okhttp3.Call listAiSolutionTypesCall(final ApiCallback _callback) throws ApiException {
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
        String localVarPath = "/api/v1/cloud-ai/solutions/types";

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
    private okhttp3.Call listAiSolutionTypesValidateBeforeCall(final ApiCallback _callback) throws ApiException {
        return listAiSolutionTypesCall(_callback);

    }

    /**
     * Получение списка типов AI-решений
     * Чтобы получить список типов AI-решений, отправьте GET-запрос на &#x60;/api/v1/cloud-ai/solutions/types&#x60;.  Возвращает каталог готовых решений, которые можно развернуть на облачном сервере, с минимальными требованиями к конфигурации сервера (&#x60;requirements&#x60;). Тело ответа будет представлять собой объект JSON с ключами &#x60;ai_solution_types&#x60; и &#x60;meta&#x60;.
     * @return ListAiSolutionTypes200Response
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> Объект JSON c ключами &#x60;ai_solution_types&#x60; и &#x60;meta&#x60; </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Некорректный запрос </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Не авторизован </td><td>  -  </td></tr>
        <tr><td> 403 </td><td> Запрещено </td><td>  -  </td></tr>
        <tr><td> 429 </td><td> Слишком много запросов </td><td>  -  </td></tr>
        <tr><td> 500 </td><td> Внутренняя ошибка сервера </td><td>  -  </td></tr>
     </table>
     */
    public ListAiSolutionTypes200Response listAiSolutionTypes() throws ApiException {
        ApiResponse<ListAiSolutionTypes200Response> localVarResp = listAiSolutionTypesWithHttpInfo();
        return localVarResp.getData();
    }

    /**
     * Получение списка типов AI-решений
     * Чтобы получить список типов AI-решений, отправьте GET-запрос на &#x60;/api/v1/cloud-ai/solutions/types&#x60;.  Возвращает каталог готовых решений, которые можно развернуть на облачном сервере, с минимальными требованиями к конфигурации сервера (&#x60;requirements&#x60;). Тело ответа будет представлять собой объект JSON с ключами &#x60;ai_solution_types&#x60; и &#x60;meta&#x60;.
     * @return ApiResponse&lt;ListAiSolutionTypes200Response&gt;
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> Объект JSON c ключами &#x60;ai_solution_types&#x60; и &#x60;meta&#x60; </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Некорректный запрос </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Не авторизован </td><td>  -  </td></tr>
        <tr><td> 403 </td><td> Запрещено </td><td>  -  </td></tr>
        <tr><td> 429 </td><td> Слишком много запросов </td><td>  -  </td></tr>
        <tr><td> 500 </td><td> Внутренняя ошибка сервера </td><td>  -  </td></tr>
     </table>
     */
    public ApiResponse<ListAiSolutionTypes200Response> listAiSolutionTypesWithHttpInfo() throws ApiException {
        okhttp3.Call localVarCall = listAiSolutionTypesValidateBeforeCall(null);
        Type localVarReturnType = new TypeToken<ListAiSolutionTypes200Response>(){}.getType();
        return localVarApiClient.execute(localVarCall, localVarReturnType);
    }

    /**
     * Получение списка типов AI-решений (asynchronously)
     * Чтобы получить список типов AI-решений, отправьте GET-запрос на &#x60;/api/v1/cloud-ai/solutions/types&#x60;.  Возвращает каталог готовых решений, которые можно развернуть на облачном сервере, с минимальными требованиями к конфигурации сервера (&#x60;requirements&#x60;). Тело ответа будет представлять собой объект JSON с ключами &#x60;ai_solution_types&#x60; и &#x60;meta&#x60;.
     * @param _callback The callback to be executed when the API call finishes
     * @return The request call
     * @throws ApiException If fail to process the API call, e.g. serializing the request body object
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> Объект JSON c ключами &#x60;ai_solution_types&#x60; и &#x60;meta&#x60; </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Некорректный запрос </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Не авторизован </td><td>  -  </td></tr>
        <tr><td> 403 </td><td> Запрещено </td><td>  -  </td></tr>
        <tr><td> 429 </td><td> Слишком много запросов </td><td>  -  </td></tr>
        <tr><td> 500 </td><td> Внутренняя ошибка сервера </td><td>  -  </td></tr>
     </table>
     */
    public okhttp3.Call listAiSolutionTypesAsync(final ApiCallback<ListAiSolutionTypes200Response> _callback) throws ApiException {

        okhttp3.Call localVarCall = listAiSolutionTypesValidateBeforeCall(_callback);
        Type localVarReturnType = new TypeToken<ListAiSolutionTypes200Response>(){}.getType();
        localVarApiClient.executeAsync(localVarCall, localVarReturnType, _callback);
        return localVarCall;
    }
    /**
     * Build call for listAiSolutions
     * @param _callback Callback for upload/download progress
     * @return Call to execute
     * @throws ApiException If fail to serialize the request body object
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> Объект JSON c ключами &#x60;ai_solutions&#x60; и &#x60;meta&#x60; </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Некорректный запрос </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Не авторизован </td><td>  -  </td></tr>
        <tr><td> 403 </td><td> Запрещено </td><td>  -  </td></tr>
        <tr><td> 429 </td><td> Слишком много запросов </td><td>  -  </td></tr>
        <tr><td> 500 </td><td> Внутренняя ошибка сервера </td><td>  -  </td></tr>
     </table>
     */
    public okhttp3.Call listAiSolutionsCall(final ApiCallback _callback) throws ApiException {
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
        String localVarPath = "/api/v1/cloud-ai/solutions";

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
    private okhttp3.Call listAiSolutionsValidateBeforeCall(final ApiCallback _callback) throws ApiException {
        return listAiSolutionsCall(_callback);

    }

    /**
     * Получение списка AI-решений
     * Чтобы получить список AI-решений аккаунта, отправьте GET-запрос на &#x60;/api/v1/cloud-ai/solutions&#x60;.  Возвращает все решения аккаунта, кроме удалённых. Статус каждого решения выводится из текущего состояния его сервера. Тело ответа будет представлять собой объект JSON с ключами &#x60;ai_solutions&#x60; и &#x60;meta&#x60;.
     * @return ListAiSolutions200Response
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> Объект JSON c ключами &#x60;ai_solutions&#x60; и &#x60;meta&#x60; </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Некорректный запрос </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Не авторизован </td><td>  -  </td></tr>
        <tr><td> 403 </td><td> Запрещено </td><td>  -  </td></tr>
        <tr><td> 429 </td><td> Слишком много запросов </td><td>  -  </td></tr>
        <tr><td> 500 </td><td> Внутренняя ошибка сервера </td><td>  -  </td></tr>
     </table>
     */
    public ListAiSolutions200Response listAiSolutions() throws ApiException {
        ApiResponse<ListAiSolutions200Response> localVarResp = listAiSolutionsWithHttpInfo();
        return localVarResp.getData();
    }

    /**
     * Получение списка AI-решений
     * Чтобы получить список AI-решений аккаунта, отправьте GET-запрос на &#x60;/api/v1/cloud-ai/solutions&#x60;.  Возвращает все решения аккаунта, кроме удалённых. Статус каждого решения выводится из текущего состояния его сервера. Тело ответа будет представлять собой объект JSON с ключами &#x60;ai_solutions&#x60; и &#x60;meta&#x60;.
     * @return ApiResponse&lt;ListAiSolutions200Response&gt;
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> Объект JSON c ключами &#x60;ai_solutions&#x60; и &#x60;meta&#x60; </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Некорректный запрос </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Не авторизован </td><td>  -  </td></tr>
        <tr><td> 403 </td><td> Запрещено </td><td>  -  </td></tr>
        <tr><td> 429 </td><td> Слишком много запросов </td><td>  -  </td></tr>
        <tr><td> 500 </td><td> Внутренняя ошибка сервера </td><td>  -  </td></tr>
     </table>
     */
    public ApiResponse<ListAiSolutions200Response> listAiSolutionsWithHttpInfo() throws ApiException {
        okhttp3.Call localVarCall = listAiSolutionsValidateBeforeCall(null);
        Type localVarReturnType = new TypeToken<ListAiSolutions200Response>(){}.getType();
        return localVarApiClient.execute(localVarCall, localVarReturnType);
    }

    /**
     * Получение списка AI-решений (asynchronously)
     * Чтобы получить список AI-решений аккаунта, отправьте GET-запрос на &#x60;/api/v1/cloud-ai/solutions&#x60;.  Возвращает все решения аккаунта, кроме удалённых. Статус каждого решения выводится из текущего состояния его сервера. Тело ответа будет представлять собой объект JSON с ключами &#x60;ai_solutions&#x60; и &#x60;meta&#x60;.
     * @param _callback The callback to be executed when the API call finishes
     * @return The request call
     * @throws ApiException If fail to process the API call, e.g. serializing the request body object
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> Объект JSON c ключами &#x60;ai_solutions&#x60; и &#x60;meta&#x60; </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Некорректный запрос </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Не авторизован </td><td>  -  </td></tr>
        <tr><td> 403 </td><td> Запрещено </td><td>  -  </td></tr>
        <tr><td> 429 </td><td> Слишком много запросов </td><td>  -  </td></tr>
        <tr><td> 500 </td><td> Внутренняя ошибка сервера </td><td>  -  </td></tr>
     </table>
     */
    public okhttp3.Call listAiSolutionsAsync(final ApiCallback<ListAiSolutions200Response> _callback) throws ApiException {

        okhttp3.Call localVarCall = listAiSolutionsValidateBeforeCall(_callback);
        Type localVarReturnType = new TypeToken<ListAiSolutions200Response>(){}.getType();
        localVarApiClient.executeAsync(localVarCall, localVarReturnType, _callback);
        return localVarCall;
    }
    /**
     * Build call for regenerateAiSolutionAiKey
     * @param solutionId ID AI-решения (required)
     * @param _callback Callback for upload/download progress
     * @return Call to execute
     * @throws ApiException If fail to serialize the request body object
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> Объект JSON c ключом &#x60;ai_solution_ai_key&#x60; </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Некорректный запрос </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Не авторизован </td><td>  -  </td></tr>
        <tr><td> 403 </td><td> Запрещено </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Не найдено </td><td>  -  </td></tr>
        <tr><td> 429 </td><td> Слишком много запросов </td><td>  -  </td></tr>
        <tr><td> 500 </td><td> Внутренняя ошибка сервера </td><td>  -  </td></tr>
        <tr><td> 502 </td><td> Сервер решения не принял новый ключ (&#x60;ai_solution_configuration_failed&#x60;) </td><td>  -  </td></tr>
        <tr><td> 503 </td><td> Сервер решения нельзя настроить: он выключен, недоступен, ещё разворачивается или создан старой версией решения (&#x60;ai_solution_machine_is_unavailable&#x60;), либо у него нет публичного IP (&#x60;ai_solution_chat_is_unavailable&#x60;) </td><td>  -  </td></tr>
     </table>
     */
    public okhttp3.Call regenerateAiSolutionAiKeyCall(Integer solutionId, final ApiCallback _callback) throws ApiException {
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
        String localVarPath = "/api/v1/cloud-ai/solutions/{solution_id}/ai-key/regenerate"
            .replace("{" + "solution_id" + "}", localVarApiClient.escapeString(solutionId.toString()));

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
    private okhttp3.Call regenerateAiSolutionAiKeyValidateBeforeCall(Integer solutionId, final ApiCallback _callback) throws ApiException {
        // verify the required parameter 'solutionId' is set
        if (solutionId == null) {
            throw new ApiException("Missing the required parameter 'solutionId' when calling regenerateAiSolutionAiKey(Async)");
        }

        return regenerateAiSolutionAiKeyCall(solutionId, _callback);

    }

    /**
     * Перевыпуск ключа AI Gateway решения
     * Чтобы перевыпустить ключ AI Gateway решения, отправьте POST-запрос на &#x60;/api/v1/cloud-ai/solutions/{solution_id}/ai-key/regenerate&#x60;.  Единственный способ сменить этот ключ: он выпущен платформой для решения и в разделе AI Gateway доступен только для чтения. Новый ключ сразу записывается в конфигурацию агента на сервере решения, старый перестаёт работать немедленно. Сервер должен быть включён и полностью развёрнут — это проверяется до перевыпуска, потому что отменить его нельзя. Если записать новый ключ на сервер не удалось, повторите запрос.
     * @param solutionId ID AI-решения (required)
     * @return RegenerateAiSolutionAiKey200Response
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> Объект JSON c ключом &#x60;ai_solution_ai_key&#x60; </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Некорректный запрос </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Не авторизован </td><td>  -  </td></tr>
        <tr><td> 403 </td><td> Запрещено </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Не найдено </td><td>  -  </td></tr>
        <tr><td> 429 </td><td> Слишком много запросов </td><td>  -  </td></tr>
        <tr><td> 500 </td><td> Внутренняя ошибка сервера </td><td>  -  </td></tr>
        <tr><td> 502 </td><td> Сервер решения не принял новый ключ (&#x60;ai_solution_configuration_failed&#x60;) </td><td>  -  </td></tr>
        <tr><td> 503 </td><td> Сервер решения нельзя настроить: он выключен, недоступен, ещё разворачивается или создан старой версией решения (&#x60;ai_solution_machine_is_unavailable&#x60;), либо у него нет публичного IP (&#x60;ai_solution_chat_is_unavailable&#x60;) </td><td>  -  </td></tr>
     </table>
     */
    public RegenerateAiSolutionAiKey200Response regenerateAiSolutionAiKey(Integer solutionId) throws ApiException {
        ApiResponse<RegenerateAiSolutionAiKey200Response> localVarResp = regenerateAiSolutionAiKeyWithHttpInfo(solutionId);
        return localVarResp.getData();
    }

    /**
     * Перевыпуск ключа AI Gateway решения
     * Чтобы перевыпустить ключ AI Gateway решения, отправьте POST-запрос на &#x60;/api/v1/cloud-ai/solutions/{solution_id}/ai-key/regenerate&#x60;.  Единственный способ сменить этот ключ: он выпущен платформой для решения и в разделе AI Gateway доступен только для чтения. Новый ключ сразу записывается в конфигурацию агента на сервере решения, старый перестаёт работать немедленно. Сервер должен быть включён и полностью развёрнут — это проверяется до перевыпуска, потому что отменить его нельзя. Если записать новый ключ на сервер не удалось, повторите запрос.
     * @param solutionId ID AI-решения (required)
     * @return ApiResponse&lt;RegenerateAiSolutionAiKey200Response&gt;
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> Объект JSON c ключом &#x60;ai_solution_ai_key&#x60; </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Некорректный запрос </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Не авторизован </td><td>  -  </td></tr>
        <tr><td> 403 </td><td> Запрещено </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Не найдено </td><td>  -  </td></tr>
        <tr><td> 429 </td><td> Слишком много запросов </td><td>  -  </td></tr>
        <tr><td> 500 </td><td> Внутренняя ошибка сервера </td><td>  -  </td></tr>
        <tr><td> 502 </td><td> Сервер решения не принял новый ключ (&#x60;ai_solution_configuration_failed&#x60;) </td><td>  -  </td></tr>
        <tr><td> 503 </td><td> Сервер решения нельзя настроить: он выключен, недоступен, ещё разворачивается или создан старой версией решения (&#x60;ai_solution_machine_is_unavailable&#x60;), либо у него нет публичного IP (&#x60;ai_solution_chat_is_unavailable&#x60;) </td><td>  -  </td></tr>
     </table>
     */
    public ApiResponse<RegenerateAiSolutionAiKey200Response> regenerateAiSolutionAiKeyWithHttpInfo(Integer solutionId) throws ApiException {
        okhttp3.Call localVarCall = regenerateAiSolutionAiKeyValidateBeforeCall(solutionId, null);
        Type localVarReturnType = new TypeToken<RegenerateAiSolutionAiKey200Response>(){}.getType();
        return localVarApiClient.execute(localVarCall, localVarReturnType);
    }

    /**
     * Перевыпуск ключа AI Gateway решения (asynchronously)
     * Чтобы перевыпустить ключ AI Gateway решения, отправьте POST-запрос на &#x60;/api/v1/cloud-ai/solutions/{solution_id}/ai-key/regenerate&#x60;.  Единственный способ сменить этот ключ: он выпущен платформой для решения и в разделе AI Gateway доступен только для чтения. Новый ключ сразу записывается в конфигурацию агента на сервере решения, старый перестаёт работать немедленно. Сервер должен быть включён и полностью развёрнут — это проверяется до перевыпуска, потому что отменить его нельзя. Если записать новый ключ на сервер не удалось, повторите запрос.
     * @param solutionId ID AI-решения (required)
     * @param _callback The callback to be executed when the API call finishes
     * @return The request call
     * @throws ApiException If fail to process the API call, e.g. serializing the request body object
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> Объект JSON c ключом &#x60;ai_solution_ai_key&#x60; </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Некорректный запрос </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Не авторизован </td><td>  -  </td></tr>
        <tr><td> 403 </td><td> Запрещено </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Не найдено </td><td>  -  </td></tr>
        <tr><td> 429 </td><td> Слишком много запросов </td><td>  -  </td></tr>
        <tr><td> 500 </td><td> Внутренняя ошибка сервера </td><td>  -  </td></tr>
        <tr><td> 502 </td><td> Сервер решения не принял новый ключ (&#x60;ai_solution_configuration_failed&#x60;) </td><td>  -  </td></tr>
        <tr><td> 503 </td><td> Сервер решения нельзя настроить: он выключен, недоступен, ещё разворачивается или создан старой версией решения (&#x60;ai_solution_machine_is_unavailable&#x60;), либо у него нет публичного IP (&#x60;ai_solution_chat_is_unavailable&#x60;) </td><td>  -  </td></tr>
     </table>
     */
    public okhttp3.Call regenerateAiSolutionAiKeyAsync(Integer solutionId, final ApiCallback<RegenerateAiSolutionAiKey200Response> _callback) throws ApiException {

        okhttp3.Call localVarCall = regenerateAiSolutionAiKeyValidateBeforeCall(solutionId, _callback);
        Type localVarReturnType = new TypeToken<RegenerateAiSolutionAiKey200Response>(){}.getType();
        localVarApiClient.executeAsync(localVarCall, localVarReturnType, _callback);
        return localVarCall;
    }
    /**
     * Build call for removeAiSolutionTelegramBot
     * @param solutionId ID AI-решения (required)
     * @param accountKey ID Telegram-бота в решении (&#x60;account_key&#x60; из списка ботов) в формате &#x60;b&lt;число&gt;&#x60;, например &#x60;b123456789&#x60; (required)
     * @param _callback Callback for upload/download progress
     * @return Call to execute
     * @throws ApiException If fail to serialize the request body object
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 204 </td><td> Бот отключён </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Некорректный запрос </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Не авторизован </td><td>  -  </td></tr>
        <tr><td> 403 </td><td> Запрещено </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Не найдено </td><td>  -  </td></tr>
        <tr><td> 429 </td><td> Слишком много запросов </td><td>  -  </td></tr>
        <tr><td> 500 </td><td> Внутренняя ошибка сервера </td><td>  -  </td></tr>
        <tr><td> 502 </td><td> Сервер решения не смог отключить бота (&#x60;ai_solution_chat_request_failed&#x60;) </td><td>  -  </td></tr>
        <tr><td> 503 </td><td> Сервер решения недоступен или не поддерживает Telegram — во втором случае пересоздайте решение (&#x60;ai_solution_chat_is_unavailable&#x60;) </td><td>  -  </td></tr>
     </table>
     */
    public okhttp3.Call removeAiSolutionTelegramBotCall(Integer solutionId, String accountKey, final ApiCallback _callback) throws ApiException {
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
        String localVarPath = "/api/v1/cloud-ai/solutions/{solution_id}/telegram/bots/{account_key}"
            .replace("{" + "solution_id" + "}", localVarApiClient.escapeString(solutionId.toString()))
            .replace("{" + "account_key" + "}", localVarApiClient.escapeString(accountKey.toString()));

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
    private okhttp3.Call removeAiSolutionTelegramBotValidateBeforeCall(Integer solutionId, String accountKey, final ApiCallback _callback) throws ApiException {
        // verify the required parameter 'solutionId' is set
        if (solutionId == null) {
            throw new ApiException("Missing the required parameter 'solutionId' when calling removeAiSolutionTelegramBot(Async)");
        }

        // verify the required parameter 'accountKey' is set
        if (accountKey == null) {
            throw new ApiException("Missing the required parameter 'accountKey' when calling removeAiSolutionTelegramBot(Async)");
        }

        return removeAiSolutionTelegramBotCall(solutionId, accountKey, _callback);

    }

    /**
     * Отключение Telegram-бота от AI-решения
     * Чтобы отключить Telegram-бота от решения, отправьте DELETE-запрос на &#x60;/api/v1/cloud-ai/solutions/{solution_id}/telegram/bots/{account_key}&#x60;.  Удаляет бота из конфигурации сервера решения: агент перестаёт отвечать в этом боте. Если бот не подключён, запрос всё равно возвращает 204. Некорректный &#x60;account_key&#x60; — ответ 400 &#x60;ai_solution_telegram_token_is_invalid&#x60;.  Перед вызовом убедитесь, что чат готов (&#x60;ready: true&#x60; в методе получения статуса чата): пока сервер решения разворачивается или выключен, запрос завершается ошибкой.
     * @param solutionId ID AI-решения (required)
     * @param accountKey ID Telegram-бота в решении (&#x60;account_key&#x60; из списка ботов) в формате &#x60;b&lt;число&gt;&#x60;, например &#x60;b123456789&#x60; (required)
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 204 </td><td> Бот отключён </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Некорректный запрос </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Не авторизован </td><td>  -  </td></tr>
        <tr><td> 403 </td><td> Запрещено </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Не найдено </td><td>  -  </td></tr>
        <tr><td> 429 </td><td> Слишком много запросов </td><td>  -  </td></tr>
        <tr><td> 500 </td><td> Внутренняя ошибка сервера </td><td>  -  </td></tr>
        <tr><td> 502 </td><td> Сервер решения не смог отключить бота (&#x60;ai_solution_chat_request_failed&#x60;) </td><td>  -  </td></tr>
        <tr><td> 503 </td><td> Сервер решения недоступен или не поддерживает Telegram — во втором случае пересоздайте решение (&#x60;ai_solution_chat_is_unavailable&#x60;) </td><td>  -  </td></tr>
     </table>
     */
    public void removeAiSolutionTelegramBot(Integer solutionId, String accountKey) throws ApiException {
        removeAiSolutionTelegramBotWithHttpInfo(solutionId, accountKey);
    }

    /**
     * Отключение Telegram-бота от AI-решения
     * Чтобы отключить Telegram-бота от решения, отправьте DELETE-запрос на &#x60;/api/v1/cloud-ai/solutions/{solution_id}/telegram/bots/{account_key}&#x60;.  Удаляет бота из конфигурации сервера решения: агент перестаёт отвечать в этом боте. Если бот не подключён, запрос всё равно возвращает 204. Некорректный &#x60;account_key&#x60; — ответ 400 &#x60;ai_solution_telegram_token_is_invalid&#x60;.  Перед вызовом убедитесь, что чат готов (&#x60;ready: true&#x60; в методе получения статуса чата): пока сервер решения разворачивается или выключен, запрос завершается ошибкой.
     * @param solutionId ID AI-решения (required)
     * @param accountKey ID Telegram-бота в решении (&#x60;account_key&#x60; из списка ботов) в формате &#x60;b&lt;число&gt;&#x60;, например &#x60;b123456789&#x60; (required)
     * @return ApiResponse&lt;Void&gt;
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 204 </td><td> Бот отключён </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Некорректный запрос </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Не авторизован </td><td>  -  </td></tr>
        <tr><td> 403 </td><td> Запрещено </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Не найдено </td><td>  -  </td></tr>
        <tr><td> 429 </td><td> Слишком много запросов </td><td>  -  </td></tr>
        <tr><td> 500 </td><td> Внутренняя ошибка сервера </td><td>  -  </td></tr>
        <tr><td> 502 </td><td> Сервер решения не смог отключить бота (&#x60;ai_solution_chat_request_failed&#x60;) </td><td>  -  </td></tr>
        <tr><td> 503 </td><td> Сервер решения недоступен или не поддерживает Telegram — во втором случае пересоздайте решение (&#x60;ai_solution_chat_is_unavailable&#x60;) </td><td>  -  </td></tr>
     </table>
     */
    public ApiResponse<Void> removeAiSolutionTelegramBotWithHttpInfo(Integer solutionId, String accountKey) throws ApiException {
        okhttp3.Call localVarCall = removeAiSolutionTelegramBotValidateBeforeCall(solutionId, accountKey, null);
        return localVarApiClient.execute(localVarCall);
    }

    /**
     * Отключение Telegram-бота от AI-решения (asynchronously)
     * Чтобы отключить Telegram-бота от решения, отправьте DELETE-запрос на &#x60;/api/v1/cloud-ai/solutions/{solution_id}/telegram/bots/{account_key}&#x60;.  Удаляет бота из конфигурации сервера решения: агент перестаёт отвечать в этом боте. Если бот не подключён, запрос всё равно возвращает 204. Некорректный &#x60;account_key&#x60; — ответ 400 &#x60;ai_solution_telegram_token_is_invalid&#x60;.  Перед вызовом убедитесь, что чат готов (&#x60;ready: true&#x60; в методе получения статуса чата): пока сервер решения разворачивается или выключен, запрос завершается ошибкой.
     * @param solutionId ID AI-решения (required)
     * @param accountKey ID Telegram-бота в решении (&#x60;account_key&#x60; из списка ботов) в формате &#x60;b&lt;число&gt;&#x60;, например &#x60;b123456789&#x60; (required)
     * @param _callback The callback to be executed when the API call finishes
     * @return The request call
     * @throws ApiException If fail to process the API call, e.g. serializing the request body object
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 204 </td><td> Бот отключён </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Некорректный запрос </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Не авторизован </td><td>  -  </td></tr>
        <tr><td> 403 </td><td> Запрещено </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Не найдено </td><td>  -  </td></tr>
        <tr><td> 429 </td><td> Слишком много запросов </td><td>  -  </td></tr>
        <tr><td> 500 </td><td> Внутренняя ошибка сервера </td><td>  -  </td></tr>
        <tr><td> 502 </td><td> Сервер решения не смог отключить бота (&#x60;ai_solution_chat_request_failed&#x60;) </td><td>  -  </td></tr>
        <tr><td> 503 </td><td> Сервер решения недоступен или не поддерживает Telegram — во втором случае пересоздайте решение (&#x60;ai_solution_chat_is_unavailable&#x60;) </td><td>  -  </td></tr>
     </table>
     */
    public okhttp3.Call removeAiSolutionTelegramBotAsync(Integer solutionId, String accountKey, final ApiCallback<Void> _callback) throws ApiException {

        okhttp3.Call localVarCall = removeAiSolutionTelegramBotValidateBeforeCall(solutionId, accountKey, _callback);
        localVarApiClient.executeAsync(localVarCall, _callback);
        return localVarCall;
    }
    /**
     * Build call for resetAiSolutionChat
     * @param solutionId ID AI-решения (required)
     * @param resetAiSolutionChat  (optional)
     * @param _callback Callback for upload/download progress
     * @return Call to execute
     * @throws ApiException If fail to serialize the request body object
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> Объект JSON c ключом &#x60;ai_solution_chat_message&#x60; </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Некорректный запрос </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Не авторизован </td><td>  -  </td></tr>
        <tr><td> 403 </td><td> Запрещено </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Не найдено </td><td>  -  </td></tr>
        <tr><td> 429 </td><td> Слишком много запросов </td><td>  -  </td></tr>
        <tr><td> 500 </td><td> Внутренняя ошибка сервера </td><td>  -  </td></tr>
        <tr><td> 502 </td><td> Агент на сервере решения ответил ошибкой (&#x60;ai_solution_chat_request_failed&#x60;) </td><td>  -  </td></tr>
        <tr><td> 503 </td><td> Чат решения недоступен: сервер разворачивается, выключен или у него нет публичного IP (&#x60;ai_solution_chat_is_unavailable&#x60;) </td><td>  -  </td></tr>
     </table>
     */
    public okhttp3.Call resetAiSolutionChatCall(Integer solutionId, ResetAiSolutionChat resetAiSolutionChat, final ApiCallback _callback) throws ApiException {
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

        Object localVarPostBody = resetAiSolutionChat;

        // create path and map variables
        String localVarPath = "/api/v1/cloud-ai/solutions/{solution_id}/chat/reset"
            .replace("{" + "solution_id" + "}", localVarApiClient.escapeString(solutionId.toString()));

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
    private okhttp3.Call resetAiSolutionChatValidateBeforeCall(Integer solutionId, ResetAiSolutionChat resetAiSolutionChat, final ApiCallback _callback) throws ApiException {
        // verify the required parameter 'solutionId' is set
        if (solutionId == null) {
            throw new ApiException("Missing the required parameter 'solutionId' when calling resetAiSolutionChat(Async)");
        }

        return resetAiSolutionChatCall(solutionId, resetAiSolutionChat, _callback);

    }

    /**
     * Сброс контекста разговора AI-решения
     * Чтобы сбросить контекст разговора, отправьте POST-запрос на &#x60;/api/v1/cloud-ai/solutions/{solution_id}/chat/reset&#x60;.  Контекст сбрасывается на месте: разговор сохраняет &#x60;conversation_id&#x60;, но агент больше не видит предыдущих сообщений. Без &#x60;conversation_id&#x60; сбрасывается основной разговор решения. В историю добавляется служебное сообщение с ролью &#x60;context-reset&#x60;, отмечающее границу текущего контекста, — оно и возвращается в ответе (с ключом &#x60;ai_solution_chat_message&#x60;). Отметка добавляется и для разговора, в котором ещё не было сообщений.  Перед вызовом убедитесь, что чат готов (&#x60;ready: true&#x60; в методе получения статуса чата): пока сервер решения разворачивается или выключен, запрос завершается ошибкой.
     * @param solutionId ID AI-решения (required)
     * @param resetAiSolutionChat  (optional)
     * @return ResetAiSolutionChat200Response
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> Объект JSON c ключом &#x60;ai_solution_chat_message&#x60; </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Некорректный запрос </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Не авторизован </td><td>  -  </td></tr>
        <tr><td> 403 </td><td> Запрещено </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Не найдено </td><td>  -  </td></tr>
        <tr><td> 429 </td><td> Слишком много запросов </td><td>  -  </td></tr>
        <tr><td> 500 </td><td> Внутренняя ошибка сервера </td><td>  -  </td></tr>
        <tr><td> 502 </td><td> Агент на сервере решения ответил ошибкой (&#x60;ai_solution_chat_request_failed&#x60;) </td><td>  -  </td></tr>
        <tr><td> 503 </td><td> Чат решения недоступен: сервер разворачивается, выключен или у него нет публичного IP (&#x60;ai_solution_chat_is_unavailable&#x60;) </td><td>  -  </td></tr>
     </table>
     */
    public ResetAiSolutionChat200Response resetAiSolutionChat(Integer solutionId, ResetAiSolutionChat resetAiSolutionChat) throws ApiException {
        ApiResponse<ResetAiSolutionChat200Response> localVarResp = resetAiSolutionChatWithHttpInfo(solutionId, resetAiSolutionChat);
        return localVarResp.getData();
    }

    /**
     * Сброс контекста разговора AI-решения
     * Чтобы сбросить контекст разговора, отправьте POST-запрос на &#x60;/api/v1/cloud-ai/solutions/{solution_id}/chat/reset&#x60;.  Контекст сбрасывается на месте: разговор сохраняет &#x60;conversation_id&#x60;, но агент больше не видит предыдущих сообщений. Без &#x60;conversation_id&#x60; сбрасывается основной разговор решения. В историю добавляется служебное сообщение с ролью &#x60;context-reset&#x60;, отмечающее границу текущего контекста, — оно и возвращается в ответе (с ключом &#x60;ai_solution_chat_message&#x60;). Отметка добавляется и для разговора, в котором ещё не было сообщений.  Перед вызовом убедитесь, что чат готов (&#x60;ready: true&#x60; в методе получения статуса чата): пока сервер решения разворачивается или выключен, запрос завершается ошибкой.
     * @param solutionId ID AI-решения (required)
     * @param resetAiSolutionChat  (optional)
     * @return ApiResponse&lt;ResetAiSolutionChat200Response&gt;
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> Объект JSON c ключом &#x60;ai_solution_chat_message&#x60; </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Некорректный запрос </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Не авторизован </td><td>  -  </td></tr>
        <tr><td> 403 </td><td> Запрещено </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Не найдено </td><td>  -  </td></tr>
        <tr><td> 429 </td><td> Слишком много запросов </td><td>  -  </td></tr>
        <tr><td> 500 </td><td> Внутренняя ошибка сервера </td><td>  -  </td></tr>
        <tr><td> 502 </td><td> Агент на сервере решения ответил ошибкой (&#x60;ai_solution_chat_request_failed&#x60;) </td><td>  -  </td></tr>
        <tr><td> 503 </td><td> Чат решения недоступен: сервер разворачивается, выключен или у него нет публичного IP (&#x60;ai_solution_chat_is_unavailable&#x60;) </td><td>  -  </td></tr>
     </table>
     */
    public ApiResponse<ResetAiSolutionChat200Response> resetAiSolutionChatWithHttpInfo(Integer solutionId, ResetAiSolutionChat resetAiSolutionChat) throws ApiException {
        okhttp3.Call localVarCall = resetAiSolutionChatValidateBeforeCall(solutionId, resetAiSolutionChat, null);
        Type localVarReturnType = new TypeToken<ResetAiSolutionChat200Response>(){}.getType();
        return localVarApiClient.execute(localVarCall, localVarReturnType);
    }

    /**
     * Сброс контекста разговора AI-решения (asynchronously)
     * Чтобы сбросить контекст разговора, отправьте POST-запрос на &#x60;/api/v1/cloud-ai/solutions/{solution_id}/chat/reset&#x60;.  Контекст сбрасывается на месте: разговор сохраняет &#x60;conversation_id&#x60;, но агент больше не видит предыдущих сообщений. Без &#x60;conversation_id&#x60; сбрасывается основной разговор решения. В историю добавляется служебное сообщение с ролью &#x60;context-reset&#x60;, отмечающее границу текущего контекста, — оно и возвращается в ответе (с ключом &#x60;ai_solution_chat_message&#x60;). Отметка добавляется и для разговора, в котором ещё не было сообщений.  Перед вызовом убедитесь, что чат готов (&#x60;ready: true&#x60; в методе получения статуса чата): пока сервер решения разворачивается или выключен, запрос завершается ошибкой.
     * @param solutionId ID AI-решения (required)
     * @param resetAiSolutionChat  (optional)
     * @param _callback The callback to be executed when the API call finishes
     * @return The request call
     * @throws ApiException If fail to process the API call, e.g. serializing the request body object
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> Объект JSON c ключом &#x60;ai_solution_chat_message&#x60; </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Некорректный запрос </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Не авторизован </td><td>  -  </td></tr>
        <tr><td> 403 </td><td> Запрещено </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Не найдено </td><td>  -  </td></tr>
        <tr><td> 429 </td><td> Слишком много запросов </td><td>  -  </td></tr>
        <tr><td> 500 </td><td> Внутренняя ошибка сервера </td><td>  -  </td></tr>
        <tr><td> 502 </td><td> Агент на сервере решения ответил ошибкой (&#x60;ai_solution_chat_request_failed&#x60;) </td><td>  -  </td></tr>
        <tr><td> 503 </td><td> Чат решения недоступен: сервер разворачивается, выключен или у него нет публичного IP (&#x60;ai_solution_chat_is_unavailable&#x60;) </td><td>  -  </td></tr>
     </table>
     */
    public okhttp3.Call resetAiSolutionChatAsync(Integer solutionId, ResetAiSolutionChat resetAiSolutionChat, final ApiCallback<ResetAiSolutionChat200Response> _callback) throws ApiException {

        okhttp3.Call localVarCall = resetAiSolutionChatValidateBeforeCall(solutionId, resetAiSolutionChat, _callback);
        Type localVarReturnType = new TypeToken<ResetAiSolutionChat200Response>(){}.getType();
        localVarApiClient.executeAsync(localVarCall, localVarReturnType, _callback);
        return localVarCall;
    }
    /**
     * Build call for sendAiSolutionMessage
     * @param solutionId ID AI-решения (required)
     * @param sendAiSolutionMessage  (required)
     * @param _callback Callback for upload/download progress
     * @return Call to execute
     * @throws ApiException If fail to serialize the request body object
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> Объект JSON c ключом &#x60;ai_solution_chat_reply&#x60; </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Некорректный запрос </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Не авторизован </td><td>  -  </td></tr>
        <tr><td> 402 </td><td> Исчерпан лимит расходов ключа AI Gateway решения (&#x60;ai_solution_chat_budget_exceeded&#x60;): поднимите лимит или дождитесь его сброса. В &#x60;details&#x60; — &#x60;max_budget&#x60;, &#x60;spend&#x60; и &#x60;budget_reset_at&#x60;, если они известны </td><td>  -  </td></tr>
        <tr><td> 403 </td><td> Запрещено </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Не найдено </td><td>  -  </td></tr>
        <tr><td> 429 </td><td> Слишком много запросов </td><td>  -  </td></tr>
        <tr><td> 500 </td><td> Внутренняя ошибка сервера </td><td>  -  </td></tr>
        <tr><td> 502 </td><td> Агент на сервере решения ответил ошибкой (&#x60;ai_solution_chat_request_failed&#x60;) </td><td>  -  </td></tr>
        <tr><td> 503 </td><td> Чат решения недоступен: сервер разворачивается, выключен или у него нет публичного IP (&#x60;ai_solution_chat_is_unavailable&#x60;) </td><td>  -  </td></tr>
     </table>
     */
    public okhttp3.Call sendAiSolutionMessageCall(Integer solutionId, SendAiSolutionMessage sendAiSolutionMessage, final ApiCallback _callback) throws ApiException {
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

        Object localVarPostBody = sendAiSolutionMessage;

        // create path and map variables
        String localVarPath = "/api/v1/cloud-ai/solutions/{solution_id}/chat/messages"
            .replace("{" + "solution_id" + "}", localVarApiClient.escapeString(solutionId.toString()));

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
    private okhttp3.Call sendAiSolutionMessageValidateBeforeCall(Integer solutionId, SendAiSolutionMessage sendAiSolutionMessage, final ApiCallback _callback) throws ApiException {
        // verify the required parameter 'solutionId' is set
        if (solutionId == null) {
            throw new ApiException("Missing the required parameter 'solutionId' when calling sendAiSolutionMessage(Async)");
        }

        // verify the required parameter 'sendAiSolutionMessage' is set
        if (sendAiSolutionMessage == null) {
            throw new ApiException("Missing the required parameter 'sendAiSolutionMessage' when calling sendAiSolutionMessage(Async)");
        }

        return sendAiSolutionMessageCall(solutionId, sendAiSolutionMessage, _callback);

    }

    /**
     * Отправка сообщения агенту AI-решения
     * Чтобы отправить сообщение агенту решения, отправьте POST-запрос на &#x60;/api/v1/cloud-ai/solutions/{solution_id}/chat/messages&#x60;.  Ответ возвращается целиком после завершения хода агента (ожидание — до 3 минут). Без &#x60;conversation_id&#x60; сообщение уходит в основной разговор решения; новый разговор начинается только при явной передаче нового ID. Без &#x60;message&#x60; агент сам здоровается — так можно показать первую реплику чата. Чтобы получать ответ по мере генерации, используйте потоковый метод.  Перед вызовом убедитесь, что чат готов (&#x60;ready: true&#x60; в методе получения статуса чата): пока сервер решения разворачивается или выключен, запрос завершается ошибкой.
     * @param solutionId ID AI-решения (required)
     * @param sendAiSolutionMessage  (required)
     * @return SendAiSolutionMessage200Response
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> Объект JSON c ключом &#x60;ai_solution_chat_reply&#x60; </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Некорректный запрос </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Не авторизован </td><td>  -  </td></tr>
        <tr><td> 402 </td><td> Исчерпан лимит расходов ключа AI Gateway решения (&#x60;ai_solution_chat_budget_exceeded&#x60;): поднимите лимит или дождитесь его сброса. В &#x60;details&#x60; — &#x60;max_budget&#x60;, &#x60;spend&#x60; и &#x60;budget_reset_at&#x60;, если они известны </td><td>  -  </td></tr>
        <tr><td> 403 </td><td> Запрещено </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Не найдено </td><td>  -  </td></tr>
        <tr><td> 429 </td><td> Слишком много запросов </td><td>  -  </td></tr>
        <tr><td> 500 </td><td> Внутренняя ошибка сервера </td><td>  -  </td></tr>
        <tr><td> 502 </td><td> Агент на сервере решения ответил ошибкой (&#x60;ai_solution_chat_request_failed&#x60;) </td><td>  -  </td></tr>
        <tr><td> 503 </td><td> Чат решения недоступен: сервер разворачивается, выключен или у него нет публичного IP (&#x60;ai_solution_chat_is_unavailable&#x60;) </td><td>  -  </td></tr>
     </table>
     */
    public SendAiSolutionMessage200Response sendAiSolutionMessage(Integer solutionId, SendAiSolutionMessage sendAiSolutionMessage) throws ApiException {
        ApiResponse<SendAiSolutionMessage200Response> localVarResp = sendAiSolutionMessageWithHttpInfo(solutionId, sendAiSolutionMessage);
        return localVarResp.getData();
    }

    /**
     * Отправка сообщения агенту AI-решения
     * Чтобы отправить сообщение агенту решения, отправьте POST-запрос на &#x60;/api/v1/cloud-ai/solutions/{solution_id}/chat/messages&#x60;.  Ответ возвращается целиком после завершения хода агента (ожидание — до 3 минут). Без &#x60;conversation_id&#x60; сообщение уходит в основной разговор решения; новый разговор начинается только при явной передаче нового ID. Без &#x60;message&#x60; агент сам здоровается — так можно показать первую реплику чата. Чтобы получать ответ по мере генерации, используйте потоковый метод.  Перед вызовом убедитесь, что чат готов (&#x60;ready: true&#x60; в методе получения статуса чата): пока сервер решения разворачивается или выключен, запрос завершается ошибкой.
     * @param solutionId ID AI-решения (required)
     * @param sendAiSolutionMessage  (required)
     * @return ApiResponse&lt;SendAiSolutionMessage200Response&gt;
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> Объект JSON c ключом &#x60;ai_solution_chat_reply&#x60; </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Некорректный запрос </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Не авторизован </td><td>  -  </td></tr>
        <tr><td> 402 </td><td> Исчерпан лимит расходов ключа AI Gateway решения (&#x60;ai_solution_chat_budget_exceeded&#x60;): поднимите лимит или дождитесь его сброса. В &#x60;details&#x60; — &#x60;max_budget&#x60;, &#x60;spend&#x60; и &#x60;budget_reset_at&#x60;, если они известны </td><td>  -  </td></tr>
        <tr><td> 403 </td><td> Запрещено </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Не найдено </td><td>  -  </td></tr>
        <tr><td> 429 </td><td> Слишком много запросов </td><td>  -  </td></tr>
        <tr><td> 500 </td><td> Внутренняя ошибка сервера </td><td>  -  </td></tr>
        <tr><td> 502 </td><td> Агент на сервере решения ответил ошибкой (&#x60;ai_solution_chat_request_failed&#x60;) </td><td>  -  </td></tr>
        <tr><td> 503 </td><td> Чат решения недоступен: сервер разворачивается, выключен или у него нет публичного IP (&#x60;ai_solution_chat_is_unavailable&#x60;) </td><td>  -  </td></tr>
     </table>
     */
    public ApiResponse<SendAiSolutionMessage200Response> sendAiSolutionMessageWithHttpInfo(Integer solutionId, SendAiSolutionMessage sendAiSolutionMessage) throws ApiException {
        okhttp3.Call localVarCall = sendAiSolutionMessageValidateBeforeCall(solutionId, sendAiSolutionMessage, null);
        Type localVarReturnType = new TypeToken<SendAiSolutionMessage200Response>(){}.getType();
        return localVarApiClient.execute(localVarCall, localVarReturnType);
    }

    /**
     * Отправка сообщения агенту AI-решения (asynchronously)
     * Чтобы отправить сообщение агенту решения, отправьте POST-запрос на &#x60;/api/v1/cloud-ai/solutions/{solution_id}/chat/messages&#x60;.  Ответ возвращается целиком после завершения хода агента (ожидание — до 3 минут). Без &#x60;conversation_id&#x60; сообщение уходит в основной разговор решения; новый разговор начинается только при явной передаче нового ID. Без &#x60;message&#x60; агент сам здоровается — так можно показать первую реплику чата. Чтобы получать ответ по мере генерации, используйте потоковый метод.  Перед вызовом убедитесь, что чат готов (&#x60;ready: true&#x60; в методе получения статуса чата): пока сервер решения разворачивается или выключен, запрос завершается ошибкой.
     * @param solutionId ID AI-решения (required)
     * @param sendAiSolutionMessage  (required)
     * @param _callback The callback to be executed when the API call finishes
     * @return The request call
     * @throws ApiException If fail to process the API call, e.g. serializing the request body object
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> Объект JSON c ключом &#x60;ai_solution_chat_reply&#x60; </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Некорректный запрос </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Не авторизован </td><td>  -  </td></tr>
        <tr><td> 402 </td><td> Исчерпан лимит расходов ключа AI Gateway решения (&#x60;ai_solution_chat_budget_exceeded&#x60;): поднимите лимит или дождитесь его сброса. В &#x60;details&#x60; — &#x60;max_budget&#x60;, &#x60;spend&#x60; и &#x60;budget_reset_at&#x60;, если они известны </td><td>  -  </td></tr>
        <tr><td> 403 </td><td> Запрещено </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Не найдено </td><td>  -  </td></tr>
        <tr><td> 429 </td><td> Слишком много запросов </td><td>  -  </td></tr>
        <tr><td> 500 </td><td> Внутренняя ошибка сервера </td><td>  -  </td></tr>
        <tr><td> 502 </td><td> Агент на сервере решения ответил ошибкой (&#x60;ai_solution_chat_request_failed&#x60;) </td><td>  -  </td></tr>
        <tr><td> 503 </td><td> Чат решения недоступен: сервер разворачивается, выключен или у него нет публичного IP (&#x60;ai_solution_chat_is_unavailable&#x60;) </td><td>  -  </td></tr>
     </table>
     */
    public okhttp3.Call sendAiSolutionMessageAsync(Integer solutionId, SendAiSolutionMessage sendAiSolutionMessage, final ApiCallback<SendAiSolutionMessage200Response> _callback) throws ApiException {

        okhttp3.Call localVarCall = sendAiSolutionMessageValidateBeforeCall(solutionId, sendAiSolutionMessage, _callback);
        Type localVarReturnType = new TypeToken<SendAiSolutionMessage200Response>(){}.getType();
        localVarApiClient.executeAsync(localVarCall, localVarReturnType, _callback);
        return localVarCall;
    }
    /**
     * Build call for shutdownAiSolution
     * @param solutionId ID AI-решения (required)
     * @param _callback Callback for upload/download progress
     * @return Call to execute
     * @throws ApiException If fail to serialize the request body object
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 204 </td><td> Выключение начато </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Некорректный запрос </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Не авторизован </td><td>  -  </td></tr>
        <tr><td> 403 </td><td> Запрещено </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Не найдено </td><td>  -  </td></tr>
        <tr><td> 409 </td><td> Конфликт </td><td>  -  </td></tr>
        <tr><td> 429 </td><td> Слишком много запросов </td><td>  -  </td></tr>
        <tr><td> 500 </td><td> Внутренняя ошибка сервера </td><td>  -  </td></tr>
        <tr><td> 502 </td><td> Действие над сервером решения не выполнено (&#x60;ai_solution_action_failed&#x60;) </td><td>  -  </td></tr>
     </table>
     */
    public okhttp3.Call shutdownAiSolutionCall(Integer solutionId, final ApiCallback _callback) throws ApiException {
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
        String localVarPath = "/api/v1/cloud-ai/solutions/{solution_id}/shutdown"
            .replace("{" + "solution_id" + "}", localVarApiClient.escapeString(solutionId.toString()));

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
    private okhttp3.Call shutdownAiSolutionValidateBeforeCall(Integer solutionId, final ApiCallback _callback) throws ApiException {
        // verify the required parameter 'solutionId' is set
        if (solutionId == null) {
            throw new ApiException("Missing the required parameter 'solutionId' when calling shutdownAiSolution(Async)");
        }

        return shutdownAiSolutionCall(solutionId, _callback);

    }

    /**
     * Выключение AI-решения
     * Чтобы выключить AI-решение, отправьте POST-запрос на &#x60;/api/v1/cloud-ai/solutions/{solution_id}/shutdown&#x60;.  Выключает сервер решения. Действие асинхронное: следите за результатом по статусу решения (обычно 1–2 минуты до &#x60;stopped&#x60;). Пока сервер выключен, чат недоступен, а сервер продолжает тарифицироваться. Выключение уже выключенного решения возвращает 204. Пока над сервером выполняется другое действие, ответ — 409 &#x60;ai_solution_is_busy&#x60;.
     * @param solutionId ID AI-решения (required)
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 204 </td><td> Выключение начато </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Некорректный запрос </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Не авторизован </td><td>  -  </td></tr>
        <tr><td> 403 </td><td> Запрещено </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Не найдено </td><td>  -  </td></tr>
        <tr><td> 409 </td><td> Конфликт </td><td>  -  </td></tr>
        <tr><td> 429 </td><td> Слишком много запросов </td><td>  -  </td></tr>
        <tr><td> 500 </td><td> Внутренняя ошибка сервера </td><td>  -  </td></tr>
        <tr><td> 502 </td><td> Действие над сервером решения не выполнено (&#x60;ai_solution_action_failed&#x60;) </td><td>  -  </td></tr>
     </table>
     */
    public void shutdownAiSolution(Integer solutionId) throws ApiException {
        shutdownAiSolutionWithHttpInfo(solutionId);
    }

    /**
     * Выключение AI-решения
     * Чтобы выключить AI-решение, отправьте POST-запрос на &#x60;/api/v1/cloud-ai/solutions/{solution_id}/shutdown&#x60;.  Выключает сервер решения. Действие асинхронное: следите за результатом по статусу решения (обычно 1–2 минуты до &#x60;stopped&#x60;). Пока сервер выключен, чат недоступен, а сервер продолжает тарифицироваться. Выключение уже выключенного решения возвращает 204. Пока над сервером выполняется другое действие, ответ — 409 &#x60;ai_solution_is_busy&#x60;.
     * @param solutionId ID AI-решения (required)
     * @return ApiResponse&lt;Void&gt;
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 204 </td><td> Выключение начато </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Некорректный запрос </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Не авторизован </td><td>  -  </td></tr>
        <tr><td> 403 </td><td> Запрещено </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Не найдено </td><td>  -  </td></tr>
        <tr><td> 409 </td><td> Конфликт </td><td>  -  </td></tr>
        <tr><td> 429 </td><td> Слишком много запросов </td><td>  -  </td></tr>
        <tr><td> 500 </td><td> Внутренняя ошибка сервера </td><td>  -  </td></tr>
        <tr><td> 502 </td><td> Действие над сервером решения не выполнено (&#x60;ai_solution_action_failed&#x60;) </td><td>  -  </td></tr>
     </table>
     */
    public ApiResponse<Void> shutdownAiSolutionWithHttpInfo(Integer solutionId) throws ApiException {
        okhttp3.Call localVarCall = shutdownAiSolutionValidateBeforeCall(solutionId, null);
        return localVarApiClient.execute(localVarCall);
    }

    /**
     * Выключение AI-решения (asynchronously)
     * Чтобы выключить AI-решение, отправьте POST-запрос на &#x60;/api/v1/cloud-ai/solutions/{solution_id}/shutdown&#x60;.  Выключает сервер решения. Действие асинхронное: следите за результатом по статусу решения (обычно 1–2 минуты до &#x60;stopped&#x60;). Пока сервер выключен, чат недоступен, а сервер продолжает тарифицироваться. Выключение уже выключенного решения возвращает 204. Пока над сервером выполняется другое действие, ответ — 409 &#x60;ai_solution_is_busy&#x60;.
     * @param solutionId ID AI-решения (required)
     * @param _callback The callback to be executed when the API call finishes
     * @return The request call
     * @throws ApiException If fail to process the API call, e.g. serializing the request body object
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 204 </td><td> Выключение начато </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Некорректный запрос </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Не авторизован </td><td>  -  </td></tr>
        <tr><td> 403 </td><td> Запрещено </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Не найдено </td><td>  -  </td></tr>
        <tr><td> 409 </td><td> Конфликт </td><td>  -  </td></tr>
        <tr><td> 429 </td><td> Слишком много запросов </td><td>  -  </td></tr>
        <tr><td> 500 </td><td> Внутренняя ошибка сервера </td><td>  -  </td></tr>
        <tr><td> 502 </td><td> Действие над сервером решения не выполнено (&#x60;ai_solution_action_failed&#x60;) </td><td>  -  </td></tr>
     </table>
     */
    public okhttp3.Call shutdownAiSolutionAsync(Integer solutionId, final ApiCallback<Void> _callback) throws ApiException {

        okhttp3.Call localVarCall = shutdownAiSolutionValidateBeforeCall(solutionId, _callback);
        localVarApiClient.executeAsync(localVarCall, _callback);
        return localVarCall;
    }
    /**
     * Build call for startAiSolution
     * @param solutionId ID AI-решения (required)
     * @param _callback Callback for upload/download progress
     * @return Call to execute
     * @throws ApiException If fail to serialize the request body object
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 204 </td><td> Запуск начат </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Некорректный запрос </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Не авторизован </td><td>  -  </td></tr>
        <tr><td> 403 </td><td> Запрещено </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Не найдено </td><td>  -  </td></tr>
        <tr><td> 409 </td><td> Конфликт </td><td>  -  </td></tr>
        <tr><td> 429 </td><td> Слишком много запросов </td><td>  -  </td></tr>
        <tr><td> 500 </td><td> Внутренняя ошибка сервера </td><td>  -  </td></tr>
        <tr><td> 502 </td><td> Действие над сервером решения не выполнено (&#x60;ai_solution_action_failed&#x60;) </td><td>  -  </td></tr>
     </table>
     */
    public okhttp3.Call startAiSolutionCall(Integer solutionId, final ApiCallback _callback) throws ApiException {
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
        String localVarPath = "/api/v1/cloud-ai/solutions/{solution_id}/start"
            .replace("{" + "solution_id" + "}", localVarApiClient.escapeString(solutionId.toString()));

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
    private okhttp3.Call startAiSolutionValidateBeforeCall(Integer solutionId, final ApiCallback _callback) throws ApiException {
        // verify the required parameter 'solutionId' is set
        if (solutionId == null) {
            throw new ApiException("Missing the required parameter 'solutionId' when calling startAiSolution(Async)");
        }

        return startAiSolutionCall(solutionId, _callback);

    }

    /**
     * Запуск AI-решения
     * Чтобы запустить AI-решение, отправьте POST-запрос на &#x60;/api/v1/cloud-ai/solutions/{solution_id}/start&#x60;.  Включает сервер решения. Действие асинхронное: следите за результатом по статусу решения, а готовность агента проверяйте методом получения статуса чата. Пока над сервером выполняется действие, запуск и выключение возвращают 409 &#x60;ai_solution_is_busy&#x60;.  Не вызывайте метод для работающего решения: запрос будет принят (204) и запустит действие над сервером, а решение на несколько минут станет занятым.
     * @param solutionId ID AI-решения (required)
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 204 </td><td> Запуск начат </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Некорректный запрос </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Не авторизован </td><td>  -  </td></tr>
        <tr><td> 403 </td><td> Запрещено </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Не найдено </td><td>  -  </td></tr>
        <tr><td> 409 </td><td> Конфликт </td><td>  -  </td></tr>
        <tr><td> 429 </td><td> Слишком много запросов </td><td>  -  </td></tr>
        <tr><td> 500 </td><td> Внутренняя ошибка сервера </td><td>  -  </td></tr>
        <tr><td> 502 </td><td> Действие над сервером решения не выполнено (&#x60;ai_solution_action_failed&#x60;) </td><td>  -  </td></tr>
     </table>
     */
    public void startAiSolution(Integer solutionId) throws ApiException {
        startAiSolutionWithHttpInfo(solutionId);
    }

    /**
     * Запуск AI-решения
     * Чтобы запустить AI-решение, отправьте POST-запрос на &#x60;/api/v1/cloud-ai/solutions/{solution_id}/start&#x60;.  Включает сервер решения. Действие асинхронное: следите за результатом по статусу решения, а готовность агента проверяйте методом получения статуса чата. Пока над сервером выполняется действие, запуск и выключение возвращают 409 &#x60;ai_solution_is_busy&#x60;.  Не вызывайте метод для работающего решения: запрос будет принят (204) и запустит действие над сервером, а решение на несколько минут станет занятым.
     * @param solutionId ID AI-решения (required)
     * @return ApiResponse&lt;Void&gt;
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 204 </td><td> Запуск начат </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Некорректный запрос </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Не авторизован </td><td>  -  </td></tr>
        <tr><td> 403 </td><td> Запрещено </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Не найдено </td><td>  -  </td></tr>
        <tr><td> 409 </td><td> Конфликт </td><td>  -  </td></tr>
        <tr><td> 429 </td><td> Слишком много запросов </td><td>  -  </td></tr>
        <tr><td> 500 </td><td> Внутренняя ошибка сервера </td><td>  -  </td></tr>
        <tr><td> 502 </td><td> Действие над сервером решения не выполнено (&#x60;ai_solution_action_failed&#x60;) </td><td>  -  </td></tr>
     </table>
     */
    public ApiResponse<Void> startAiSolutionWithHttpInfo(Integer solutionId) throws ApiException {
        okhttp3.Call localVarCall = startAiSolutionValidateBeforeCall(solutionId, null);
        return localVarApiClient.execute(localVarCall);
    }

    /**
     * Запуск AI-решения (asynchronously)
     * Чтобы запустить AI-решение, отправьте POST-запрос на &#x60;/api/v1/cloud-ai/solutions/{solution_id}/start&#x60;.  Включает сервер решения. Действие асинхронное: следите за результатом по статусу решения, а готовность агента проверяйте методом получения статуса чата. Пока над сервером выполняется действие, запуск и выключение возвращают 409 &#x60;ai_solution_is_busy&#x60;.  Не вызывайте метод для работающего решения: запрос будет принят (204) и запустит действие над сервером, а решение на несколько минут станет занятым.
     * @param solutionId ID AI-решения (required)
     * @param _callback The callback to be executed when the API call finishes
     * @return The request call
     * @throws ApiException If fail to process the API call, e.g. serializing the request body object
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 204 </td><td> Запуск начат </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Некорректный запрос </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Не авторизован </td><td>  -  </td></tr>
        <tr><td> 403 </td><td> Запрещено </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Не найдено </td><td>  -  </td></tr>
        <tr><td> 409 </td><td> Конфликт </td><td>  -  </td></tr>
        <tr><td> 429 </td><td> Слишком много запросов </td><td>  -  </td></tr>
        <tr><td> 500 </td><td> Внутренняя ошибка сервера </td><td>  -  </td></tr>
        <tr><td> 502 </td><td> Действие над сервером решения не выполнено (&#x60;ai_solution_action_failed&#x60;) </td><td>  -  </td></tr>
     </table>
     */
    public okhttp3.Call startAiSolutionAsync(Integer solutionId, final ApiCallback<Void> _callback) throws ApiException {

        okhttp3.Call localVarCall = startAiSolutionValidateBeforeCall(solutionId, _callback);
        localVarApiClient.executeAsync(localVarCall, _callback);
        return localVarCall;
    }
    /**
     * Build call for stopAiSolutionChat
     * @param solutionId ID AI-решения (required)
     * @param stopAiSolutionChat  (optional)
     * @param _callback Callback for upload/download progress
     * @return Call to execute
     * @throws ApiException If fail to serialize the request body object
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 204 </td><td> Ход остановлен </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Некорректный запрос </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Не авторизован </td><td>  -  </td></tr>
        <tr><td> 403 </td><td> Запрещено </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Не найдено </td><td>  -  </td></tr>
        <tr><td> 429 </td><td> Слишком много запросов </td><td>  -  </td></tr>
        <tr><td> 500 </td><td> Внутренняя ошибка сервера </td><td>  -  </td></tr>
        <tr><td> 502 </td><td> Агент на сервере решения ответил ошибкой (&#x60;ai_solution_chat_request_failed&#x60;) </td><td>  -  </td></tr>
        <tr><td> 503 </td><td> Чат решения недоступен: сервер разворачивается, выключен или у него нет публичного IP (&#x60;ai_solution_chat_is_unavailable&#x60;) </td><td>  -  </td></tr>
     </table>
     */
    public okhttp3.Call stopAiSolutionChatCall(Integer solutionId, StopAiSolutionChat stopAiSolutionChat, final ApiCallback _callback) throws ApiException {
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

        Object localVarPostBody = stopAiSolutionChat;

        // create path and map variables
        String localVarPath = "/api/v1/cloud-ai/solutions/{solution_id}/chat/stop"
            .replace("{" + "solution_id" + "}", localVarApiClient.escapeString(solutionId.toString()));

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
    private okhttp3.Call stopAiSolutionChatValidateBeforeCall(Integer solutionId, StopAiSolutionChat stopAiSolutionChat, final ApiCallback _callback) throws ApiException {
        // verify the required parameter 'solutionId' is set
        if (solutionId == null) {
            throw new ApiException("Missing the required parameter 'solutionId' when calling stopAiSolutionChat(Async)");
        }

        return stopAiSolutionChatCall(solutionId, stopAiSolutionChat, _callback);

    }

    /**
     * Остановка хода агента AI-решения
     * Чтобы остановить текущий ход агента, отправьте POST-запрос на &#x60;/api/v1/cloud-ai/solutions/{solution_id}/chat/stop&#x60;.  Прерывает генерацию ответа — например, если ответ зациклился или тратит слишком много токенов. Без &#x60;conversation_id&#x60; останавливается текущий ход основного разговора решения. Ход прерывается на стороне агента: закрыть соединение недостаточно, потому что принятый агентом ход продолжает выполняться независимо от клиента. Если хода нет, запрос всё равно возвращает 204.  Перед вызовом убедитесь, что чат готов (&#x60;ready: true&#x60; в методе получения статуса чата): пока сервер решения разворачивается или выключен, запрос завершается ошибкой.
     * @param solutionId ID AI-решения (required)
     * @param stopAiSolutionChat  (optional)
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 204 </td><td> Ход остановлен </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Некорректный запрос </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Не авторизован </td><td>  -  </td></tr>
        <tr><td> 403 </td><td> Запрещено </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Не найдено </td><td>  -  </td></tr>
        <tr><td> 429 </td><td> Слишком много запросов </td><td>  -  </td></tr>
        <tr><td> 500 </td><td> Внутренняя ошибка сервера </td><td>  -  </td></tr>
        <tr><td> 502 </td><td> Агент на сервере решения ответил ошибкой (&#x60;ai_solution_chat_request_failed&#x60;) </td><td>  -  </td></tr>
        <tr><td> 503 </td><td> Чат решения недоступен: сервер разворачивается, выключен или у него нет публичного IP (&#x60;ai_solution_chat_is_unavailable&#x60;) </td><td>  -  </td></tr>
     </table>
     */
    public void stopAiSolutionChat(Integer solutionId, StopAiSolutionChat stopAiSolutionChat) throws ApiException {
        stopAiSolutionChatWithHttpInfo(solutionId, stopAiSolutionChat);
    }

    /**
     * Остановка хода агента AI-решения
     * Чтобы остановить текущий ход агента, отправьте POST-запрос на &#x60;/api/v1/cloud-ai/solutions/{solution_id}/chat/stop&#x60;.  Прерывает генерацию ответа — например, если ответ зациклился или тратит слишком много токенов. Без &#x60;conversation_id&#x60; останавливается текущий ход основного разговора решения. Ход прерывается на стороне агента: закрыть соединение недостаточно, потому что принятый агентом ход продолжает выполняться независимо от клиента. Если хода нет, запрос всё равно возвращает 204.  Перед вызовом убедитесь, что чат готов (&#x60;ready: true&#x60; в методе получения статуса чата): пока сервер решения разворачивается или выключен, запрос завершается ошибкой.
     * @param solutionId ID AI-решения (required)
     * @param stopAiSolutionChat  (optional)
     * @return ApiResponse&lt;Void&gt;
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 204 </td><td> Ход остановлен </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Некорректный запрос </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Не авторизован </td><td>  -  </td></tr>
        <tr><td> 403 </td><td> Запрещено </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Не найдено </td><td>  -  </td></tr>
        <tr><td> 429 </td><td> Слишком много запросов </td><td>  -  </td></tr>
        <tr><td> 500 </td><td> Внутренняя ошибка сервера </td><td>  -  </td></tr>
        <tr><td> 502 </td><td> Агент на сервере решения ответил ошибкой (&#x60;ai_solution_chat_request_failed&#x60;) </td><td>  -  </td></tr>
        <tr><td> 503 </td><td> Чат решения недоступен: сервер разворачивается, выключен или у него нет публичного IP (&#x60;ai_solution_chat_is_unavailable&#x60;) </td><td>  -  </td></tr>
     </table>
     */
    public ApiResponse<Void> stopAiSolutionChatWithHttpInfo(Integer solutionId, StopAiSolutionChat stopAiSolutionChat) throws ApiException {
        okhttp3.Call localVarCall = stopAiSolutionChatValidateBeforeCall(solutionId, stopAiSolutionChat, null);
        return localVarApiClient.execute(localVarCall);
    }

    /**
     * Остановка хода агента AI-решения (asynchronously)
     * Чтобы остановить текущий ход агента, отправьте POST-запрос на &#x60;/api/v1/cloud-ai/solutions/{solution_id}/chat/stop&#x60;.  Прерывает генерацию ответа — например, если ответ зациклился или тратит слишком много токенов. Без &#x60;conversation_id&#x60; останавливается текущий ход основного разговора решения. Ход прерывается на стороне агента: закрыть соединение недостаточно, потому что принятый агентом ход продолжает выполняться независимо от клиента. Если хода нет, запрос всё равно возвращает 204.  Перед вызовом убедитесь, что чат готов (&#x60;ready: true&#x60; в методе получения статуса чата): пока сервер решения разворачивается или выключен, запрос завершается ошибкой.
     * @param solutionId ID AI-решения (required)
     * @param stopAiSolutionChat  (optional)
     * @param _callback The callback to be executed when the API call finishes
     * @return The request call
     * @throws ApiException If fail to process the API call, e.g. serializing the request body object
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 204 </td><td> Ход остановлен </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Некорректный запрос </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Не авторизован </td><td>  -  </td></tr>
        <tr><td> 403 </td><td> Запрещено </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Не найдено </td><td>  -  </td></tr>
        <tr><td> 429 </td><td> Слишком много запросов </td><td>  -  </td></tr>
        <tr><td> 500 </td><td> Внутренняя ошибка сервера </td><td>  -  </td></tr>
        <tr><td> 502 </td><td> Агент на сервере решения ответил ошибкой (&#x60;ai_solution_chat_request_failed&#x60;) </td><td>  -  </td></tr>
        <tr><td> 503 </td><td> Чат решения недоступен: сервер разворачивается, выключен или у него нет публичного IP (&#x60;ai_solution_chat_is_unavailable&#x60;) </td><td>  -  </td></tr>
     </table>
     */
    public okhttp3.Call stopAiSolutionChatAsync(Integer solutionId, StopAiSolutionChat stopAiSolutionChat, final ApiCallback<Void> _callback) throws ApiException {

        okhttp3.Call localVarCall = stopAiSolutionChatValidateBeforeCall(solutionId, stopAiSolutionChat, _callback);
        localVarApiClient.executeAsync(localVarCall, _callback);
        return localVarCall;
    }
    /**
     * Build call for streamAiSolutionChat
     * @param solutionId ID AI-решения (required)
     * @param sendAiSolutionMessage  (required)
     * @param _callback Callback for upload/download progress
     * @return Call to execute
     * @throws ApiException If fail to serialize the request body object
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> Поток событий ответа </td><td>  * X-Conversation-Id - ID разговора, в который ушёл ход <br>  </td></tr>
        <tr><td> 400 </td><td> Некорректные параметры запроса </td><td>  -  </td></tr>
        <tr><td> 403 </td><td> Токен не передан или недействителен, либо у него нет доступа на запись к AI-сервисам </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Решение не найдено (&#x60;ai_solution_not_found&#x60;) </td><td>  -  </td></tr>
        <tr><td> 500 </td><td> Внутренняя ошибка </td><td>  -  </td></tr>
        <tr><td> 503 </td><td> Чат решения недоступен (&#x60;ai_solution_chat_is_unavailable&#x60;) </td><td>  -  </td></tr>
     </table>
     */
    public okhttp3.Call streamAiSolutionChatCall(Integer solutionId, SendAiSolutionMessage sendAiSolutionMessage, final ApiCallback _callback) throws ApiException {
        String basePath = null;
        // Operation Servers
        String[] localBasePaths = new String[] { "https://agent.timeweb.cloud" };

        // Determine Base Path to Use
        if (localCustomBaseUrl != null){
            basePath = localCustomBaseUrl;
        } else if ( localBasePaths.length > 0 ) {
            basePath = localBasePaths[localHostIndex];
        } else {
            basePath = null;
        }

        Object localVarPostBody = sendAiSolutionMessage;

        // create path and map variables
        String localVarPath = "/api/v1/cloud-ai/solutions/{solution_id}/chat/stream"
            .replace("{" + "solution_id" + "}", localVarApiClient.escapeString(solutionId.toString()));

        List<Pair> localVarQueryParams = new ArrayList<Pair>();
        List<Pair> localVarCollectionQueryParams = new ArrayList<Pair>();
        Map<String, String> localVarHeaderParams = new HashMap<String, String>();
        Map<String, String> localVarCookieParams = new HashMap<String, String>();
        Map<String, Object> localVarFormParams = new HashMap<String, Object>();

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
    private okhttp3.Call streamAiSolutionChatValidateBeforeCall(Integer solutionId, SendAiSolutionMessage sendAiSolutionMessage, final ApiCallback _callback) throws ApiException {
        // verify the required parameter 'solutionId' is set
        if (solutionId == null) {
            throw new ApiException("Missing the required parameter 'solutionId' when calling streamAiSolutionChat(Async)");
        }

        // verify the required parameter 'sendAiSolutionMessage' is set
        if (sendAiSolutionMessage == null) {
            throw new ApiException("Missing the required parameter 'sendAiSolutionMessage' when calling streamAiSolutionChat(Async)");
        }

        return streamAiSolutionChatCall(solutionId, sendAiSolutionMessage, _callback);

    }

    /**
     * Отправка сообщения агенту AI-решения с потоковым ответом
     * Чтобы отправить сообщение агенту решения и получать ответ потоком, отправьте POST-запрос на &#x60;https://agent.timeweb.cloud/api/v1/cloud-ai/solutions/{solution_id}/chat/stream&#x60;.  Метод работает на &#x60;https://agent.timeweb.cloud&#x60;, а не на &#x60;https://api.timeweb.cloud&#x60;: шлюз публичного API накапливает ответ целиком и не может передавать его по частям. Авторизация та же — API-токен в заголовке &#x60;Authorization&#x60;; токену с ограниченными правами нужен доступ на запись к AI-сервисам. Тело запроса такое же, как у метода отправки сообщения.  Ответ — &#x60;text/event-stream&#x60;: каждое событие приходит как &#x60;event: &lt;тип&gt;&#x60; и &#x60;data: &lt;JSON&gt;&#x60;. Отправка сообщения и подписка на ответ — один запрос, поэтому начало ответа не теряется. Строки, начинающиеся с &#x60;:&#x60;, — комментарии для поддержания соединения (каждые 15 секунд), их нужно игнорировать. ID разговора возвращается в заголовке ответа &#x60;X-Conversation-Id&#x60;.  Типы событий: - &#x60;subscribed&#x60; &#x60;{}&#x60; — ход принят; - &#x60;delta&#x60; &#x60;{text, seq, runId}&#x60; — очередной фрагмент ответа; - &#x60;tool&#x60; &#x60;{name, target, phase, status, seq, runId}&#x60; — агент использует инструмент; - &#x60;message&#x60; &#x60;{text, seq, id, stopReason}&#x60; — финальный ответ целиком, всегда после последнего &#x60;delta&#x60;; - &#x60;done&#x60; &#x60;{phase, seq, runId}&#x60; — ход завершён; - &#x60;error&#x60; &#x60;{reason, message, budget?}&#x60; — ход не удался. &#x60;reason&#x60;: &#x60;empty_reply&#x60; — агент не выдал текста; &#x60;stream_failed&#x60; — поток прервался; &#x60;agent_failed&#x60; — ход упал на стороне агента при исправном соединении, повтор имеет смысл; &#x60;budget_exceeded&#x60; — исчерпан лимит расходов ключа AI Gateway решения, в &#x60;budget&#x60; &#x60;{max, spent, resetAt}&#x60; — цифры лимита, если они известны. &#x60;error&#x60; всегда последнее событие потока и может прийти после &#x60;done&#x60; (например, с &#x60;empty_reply&#x60;).  Ошибки, возникшие до начала потока (решение не найдено, некорректный запрос, нет доступа), возвращаются обычным JSON-ответом с соответствующим HTTP-статусом. Если сервер решения выключен или недоступен, поток может открыться и сразу завершиться событием &#x60;error&#x60; с &#x60;reason: stream_failed&#x60; — проверяйте готовность чата заранее методом получения статуса чата.
     * @param solutionId ID AI-решения (required)
     * @param sendAiSolutionMessage  (required)
     * @return String
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> Поток событий ответа </td><td>  * X-Conversation-Id - ID разговора, в который ушёл ход <br>  </td></tr>
        <tr><td> 400 </td><td> Некорректные параметры запроса </td><td>  -  </td></tr>
        <tr><td> 403 </td><td> Токен не передан или недействителен, либо у него нет доступа на запись к AI-сервисам </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Решение не найдено (&#x60;ai_solution_not_found&#x60;) </td><td>  -  </td></tr>
        <tr><td> 500 </td><td> Внутренняя ошибка </td><td>  -  </td></tr>
        <tr><td> 503 </td><td> Чат решения недоступен (&#x60;ai_solution_chat_is_unavailable&#x60;) </td><td>  -  </td></tr>
     </table>
     */
    public String streamAiSolutionChat(Integer solutionId, SendAiSolutionMessage sendAiSolutionMessage) throws ApiException {
        ApiResponse<String> localVarResp = streamAiSolutionChatWithHttpInfo(solutionId, sendAiSolutionMessage);
        return localVarResp.getData();
    }

    /**
     * Отправка сообщения агенту AI-решения с потоковым ответом
     * Чтобы отправить сообщение агенту решения и получать ответ потоком, отправьте POST-запрос на &#x60;https://agent.timeweb.cloud/api/v1/cloud-ai/solutions/{solution_id}/chat/stream&#x60;.  Метод работает на &#x60;https://agent.timeweb.cloud&#x60;, а не на &#x60;https://api.timeweb.cloud&#x60;: шлюз публичного API накапливает ответ целиком и не может передавать его по частям. Авторизация та же — API-токен в заголовке &#x60;Authorization&#x60;; токену с ограниченными правами нужен доступ на запись к AI-сервисам. Тело запроса такое же, как у метода отправки сообщения.  Ответ — &#x60;text/event-stream&#x60;: каждое событие приходит как &#x60;event: &lt;тип&gt;&#x60; и &#x60;data: &lt;JSON&gt;&#x60;. Отправка сообщения и подписка на ответ — один запрос, поэтому начало ответа не теряется. Строки, начинающиеся с &#x60;:&#x60;, — комментарии для поддержания соединения (каждые 15 секунд), их нужно игнорировать. ID разговора возвращается в заголовке ответа &#x60;X-Conversation-Id&#x60;.  Типы событий: - &#x60;subscribed&#x60; &#x60;{}&#x60; — ход принят; - &#x60;delta&#x60; &#x60;{text, seq, runId}&#x60; — очередной фрагмент ответа; - &#x60;tool&#x60; &#x60;{name, target, phase, status, seq, runId}&#x60; — агент использует инструмент; - &#x60;message&#x60; &#x60;{text, seq, id, stopReason}&#x60; — финальный ответ целиком, всегда после последнего &#x60;delta&#x60;; - &#x60;done&#x60; &#x60;{phase, seq, runId}&#x60; — ход завершён; - &#x60;error&#x60; &#x60;{reason, message, budget?}&#x60; — ход не удался. &#x60;reason&#x60;: &#x60;empty_reply&#x60; — агент не выдал текста; &#x60;stream_failed&#x60; — поток прервался; &#x60;agent_failed&#x60; — ход упал на стороне агента при исправном соединении, повтор имеет смысл; &#x60;budget_exceeded&#x60; — исчерпан лимит расходов ключа AI Gateway решения, в &#x60;budget&#x60; &#x60;{max, spent, resetAt}&#x60; — цифры лимита, если они известны. &#x60;error&#x60; всегда последнее событие потока и может прийти после &#x60;done&#x60; (например, с &#x60;empty_reply&#x60;).  Ошибки, возникшие до начала потока (решение не найдено, некорректный запрос, нет доступа), возвращаются обычным JSON-ответом с соответствующим HTTP-статусом. Если сервер решения выключен или недоступен, поток может открыться и сразу завершиться событием &#x60;error&#x60; с &#x60;reason: stream_failed&#x60; — проверяйте готовность чата заранее методом получения статуса чата.
     * @param solutionId ID AI-решения (required)
     * @param sendAiSolutionMessage  (required)
     * @return ApiResponse&lt;String&gt;
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> Поток событий ответа </td><td>  * X-Conversation-Id - ID разговора, в который ушёл ход <br>  </td></tr>
        <tr><td> 400 </td><td> Некорректные параметры запроса </td><td>  -  </td></tr>
        <tr><td> 403 </td><td> Токен не передан или недействителен, либо у него нет доступа на запись к AI-сервисам </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Решение не найдено (&#x60;ai_solution_not_found&#x60;) </td><td>  -  </td></tr>
        <tr><td> 500 </td><td> Внутренняя ошибка </td><td>  -  </td></tr>
        <tr><td> 503 </td><td> Чат решения недоступен (&#x60;ai_solution_chat_is_unavailable&#x60;) </td><td>  -  </td></tr>
     </table>
     */
    public ApiResponse<String> streamAiSolutionChatWithHttpInfo(Integer solutionId, SendAiSolutionMessage sendAiSolutionMessage) throws ApiException {
        okhttp3.Call localVarCall = streamAiSolutionChatValidateBeforeCall(solutionId, sendAiSolutionMessage, null);
        Type localVarReturnType = new TypeToken<String>(){}.getType();
        return localVarApiClient.execute(localVarCall, localVarReturnType);
    }

    /**
     * Отправка сообщения агенту AI-решения с потоковым ответом (asynchronously)
     * Чтобы отправить сообщение агенту решения и получать ответ потоком, отправьте POST-запрос на &#x60;https://agent.timeweb.cloud/api/v1/cloud-ai/solutions/{solution_id}/chat/stream&#x60;.  Метод работает на &#x60;https://agent.timeweb.cloud&#x60;, а не на &#x60;https://api.timeweb.cloud&#x60;: шлюз публичного API накапливает ответ целиком и не может передавать его по частям. Авторизация та же — API-токен в заголовке &#x60;Authorization&#x60;; токену с ограниченными правами нужен доступ на запись к AI-сервисам. Тело запроса такое же, как у метода отправки сообщения.  Ответ — &#x60;text/event-stream&#x60;: каждое событие приходит как &#x60;event: &lt;тип&gt;&#x60; и &#x60;data: &lt;JSON&gt;&#x60;. Отправка сообщения и подписка на ответ — один запрос, поэтому начало ответа не теряется. Строки, начинающиеся с &#x60;:&#x60;, — комментарии для поддержания соединения (каждые 15 секунд), их нужно игнорировать. ID разговора возвращается в заголовке ответа &#x60;X-Conversation-Id&#x60;.  Типы событий: - &#x60;subscribed&#x60; &#x60;{}&#x60; — ход принят; - &#x60;delta&#x60; &#x60;{text, seq, runId}&#x60; — очередной фрагмент ответа; - &#x60;tool&#x60; &#x60;{name, target, phase, status, seq, runId}&#x60; — агент использует инструмент; - &#x60;message&#x60; &#x60;{text, seq, id, stopReason}&#x60; — финальный ответ целиком, всегда после последнего &#x60;delta&#x60;; - &#x60;done&#x60; &#x60;{phase, seq, runId}&#x60; — ход завершён; - &#x60;error&#x60; &#x60;{reason, message, budget?}&#x60; — ход не удался. &#x60;reason&#x60;: &#x60;empty_reply&#x60; — агент не выдал текста; &#x60;stream_failed&#x60; — поток прервался; &#x60;agent_failed&#x60; — ход упал на стороне агента при исправном соединении, повтор имеет смысл; &#x60;budget_exceeded&#x60; — исчерпан лимит расходов ключа AI Gateway решения, в &#x60;budget&#x60; &#x60;{max, spent, resetAt}&#x60; — цифры лимита, если они известны. &#x60;error&#x60; всегда последнее событие потока и может прийти после &#x60;done&#x60; (например, с &#x60;empty_reply&#x60;).  Ошибки, возникшие до начала потока (решение не найдено, некорректный запрос, нет доступа), возвращаются обычным JSON-ответом с соответствующим HTTP-статусом. Если сервер решения выключен или недоступен, поток может открыться и сразу завершиться событием &#x60;error&#x60; с &#x60;reason: stream_failed&#x60; — проверяйте готовность чата заранее методом получения статуса чата.
     * @param solutionId ID AI-решения (required)
     * @param sendAiSolutionMessage  (required)
     * @param _callback The callback to be executed when the API call finishes
     * @return The request call
     * @throws ApiException If fail to process the API call, e.g. serializing the request body object
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> Поток событий ответа </td><td>  * X-Conversation-Id - ID разговора, в который ушёл ход <br>  </td></tr>
        <tr><td> 400 </td><td> Некорректные параметры запроса </td><td>  -  </td></tr>
        <tr><td> 403 </td><td> Токен не передан или недействителен, либо у него нет доступа на запись к AI-сервисам </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Решение не найдено (&#x60;ai_solution_not_found&#x60;) </td><td>  -  </td></tr>
        <tr><td> 500 </td><td> Внутренняя ошибка </td><td>  -  </td></tr>
        <tr><td> 503 </td><td> Чат решения недоступен (&#x60;ai_solution_chat_is_unavailable&#x60;) </td><td>  -  </td></tr>
     </table>
     */
    public okhttp3.Call streamAiSolutionChatAsync(Integer solutionId, SendAiSolutionMessage sendAiSolutionMessage, final ApiCallback<String> _callback) throws ApiException {

        okhttp3.Call localVarCall = streamAiSolutionChatValidateBeforeCall(solutionId, sendAiSolutionMessage, _callback);
        Type localVarReturnType = new TypeToken<String>(){}.getType();
        localVarApiClient.executeAsync(localVarCall, localVarReturnType, _callback);
        return localVarCall;
    }
    /**
     * Build call for updateAiSolution
     * @param solutionId ID AI-решения (required)
     * @param updateAiSolution  (required)
     * @param _callback Callback for upload/download progress
     * @return Call to execute
     * @throws ApiException If fail to serialize the request body object
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> Объект JSON c ключом &#x60;ai_solution&#x60; </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Некорректный запрос </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Не авторизован </td><td>  -  </td></tr>
        <tr><td> 403 </td><td> Запрещено </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Не найдено </td><td>  -  </td></tr>
        <tr><td> 429 </td><td> Слишком много запросов </td><td>  -  </td></tr>
        <tr><td> 500 </td><td> Внутренняя ошибка сервера </td><td>  -  </td></tr>
        <tr><td> 502 </td><td> Сервер решения не применил изменение (&#x60;ai_solution_configuration_failed&#x60;) </td><td>  -  </td></tr>
        <tr><td> 503 </td><td> Сервер решения нельзя настроить: он выключен, недоступен, ещё разворачивается или создан старой версией решения (&#x60;ai_solution_machine_is_unavailable&#x60;), либо у него нет публичного IP (&#x60;ai_solution_chat_is_unavailable&#x60;) </td><td>  -  </td></tr>
     </table>
     */
    public okhttp3.Call updateAiSolutionCall(Integer solutionId, UpdateAiSolution updateAiSolution, final ApiCallback _callback) throws ApiException {
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

        Object localVarPostBody = updateAiSolution;

        // create path and map variables
        String localVarPath = "/api/v1/cloud-ai/solutions/{solution_id}"
            .replace("{" + "solution_id" + "}", localVarApiClient.escapeString(solutionId.toString()));

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
    private okhttp3.Call updateAiSolutionValidateBeforeCall(Integer solutionId, UpdateAiSolution updateAiSolution, final ApiCallback _callback) throws ApiException {
        // verify the required parameter 'solutionId' is set
        if (solutionId == null) {
            throw new ApiException("Missing the required parameter 'solutionId' when calling updateAiSolution(Async)");
        }

        // verify the required parameter 'updateAiSolution' is set
        if (updateAiSolution == null) {
            throw new ApiException("Missing the required parameter 'updateAiSolution' when calling updateAiSolution(Async)");
        }

        return updateAiSolutionCall(solutionId, updateAiSolution, _callback);

    }

    /**
     * Изменение AI-решения
     * Чтобы изменить AI-решение, отправьте PATCH-запрос на &#x60;/api/v1/cloud-ai/solutions/{solution_id}&#x60;.  Меняются только переданные поля; &#x60;description: null&#x60; очищает описание. Смена модели переписывает конфигурацию агента на сервере решения и перезапускает агента, поэтому сервер должен быть включён и полностью развёрнут — иначе запрос завершится ошибкой и ничего не изменится. Смена только названия или описания сервер не затрагивает. Неподходящая модель — 404 &#x60;model_not_found&#x60;.
     * @param solutionId ID AI-решения (required)
     * @param updateAiSolution  (required)
     * @return CreateAiSolution201Response
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> Объект JSON c ключом &#x60;ai_solution&#x60; </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Некорректный запрос </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Не авторизован </td><td>  -  </td></tr>
        <tr><td> 403 </td><td> Запрещено </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Не найдено </td><td>  -  </td></tr>
        <tr><td> 429 </td><td> Слишком много запросов </td><td>  -  </td></tr>
        <tr><td> 500 </td><td> Внутренняя ошибка сервера </td><td>  -  </td></tr>
        <tr><td> 502 </td><td> Сервер решения не применил изменение (&#x60;ai_solution_configuration_failed&#x60;) </td><td>  -  </td></tr>
        <tr><td> 503 </td><td> Сервер решения нельзя настроить: он выключен, недоступен, ещё разворачивается или создан старой версией решения (&#x60;ai_solution_machine_is_unavailable&#x60;), либо у него нет публичного IP (&#x60;ai_solution_chat_is_unavailable&#x60;) </td><td>  -  </td></tr>
     </table>
     */
    public CreateAiSolution201Response updateAiSolution(Integer solutionId, UpdateAiSolution updateAiSolution) throws ApiException {
        ApiResponse<CreateAiSolution201Response> localVarResp = updateAiSolutionWithHttpInfo(solutionId, updateAiSolution);
        return localVarResp.getData();
    }

    /**
     * Изменение AI-решения
     * Чтобы изменить AI-решение, отправьте PATCH-запрос на &#x60;/api/v1/cloud-ai/solutions/{solution_id}&#x60;.  Меняются только переданные поля; &#x60;description: null&#x60; очищает описание. Смена модели переписывает конфигурацию агента на сервере решения и перезапускает агента, поэтому сервер должен быть включён и полностью развёрнут — иначе запрос завершится ошибкой и ничего не изменится. Смена только названия или описания сервер не затрагивает. Неподходящая модель — 404 &#x60;model_not_found&#x60;.
     * @param solutionId ID AI-решения (required)
     * @param updateAiSolution  (required)
     * @return ApiResponse&lt;CreateAiSolution201Response&gt;
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> Объект JSON c ключом &#x60;ai_solution&#x60; </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Некорректный запрос </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Не авторизован </td><td>  -  </td></tr>
        <tr><td> 403 </td><td> Запрещено </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Не найдено </td><td>  -  </td></tr>
        <tr><td> 429 </td><td> Слишком много запросов </td><td>  -  </td></tr>
        <tr><td> 500 </td><td> Внутренняя ошибка сервера </td><td>  -  </td></tr>
        <tr><td> 502 </td><td> Сервер решения не применил изменение (&#x60;ai_solution_configuration_failed&#x60;) </td><td>  -  </td></tr>
        <tr><td> 503 </td><td> Сервер решения нельзя настроить: он выключен, недоступен, ещё разворачивается или создан старой версией решения (&#x60;ai_solution_machine_is_unavailable&#x60;), либо у него нет публичного IP (&#x60;ai_solution_chat_is_unavailable&#x60;) </td><td>  -  </td></tr>
     </table>
     */
    public ApiResponse<CreateAiSolution201Response> updateAiSolutionWithHttpInfo(Integer solutionId, UpdateAiSolution updateAiSolution) throws ApiException {
        okhttp3.Call localVarCall = updateAiSolutionValidateBeforeCall(solutionId, updateAiSolution, null);
        Type localVarReturnType = new TypeToken<CreateAiSolution201Response>(){}.getType();
        return localVarApiClient.execute(localVarCall, localVarReturnType);
    }

    /**
     * Изменение AI-решения (asynchronously)
     * Чтобы изменить AI-решение, отправьте PATCH-запрос на &#x60;/api/v1/cloud-ai/solutions/{solution_id}&#x60;.  Меняются только переданные поля; &#x60;description: null&#x60; очищает описание. Смена модели переписывает конфигурацию агента на сервере решения и перезапускает агента, поэтому сервер должен быть включён и полностью развёрнут — иначе запрос завершится ошибкой и ничего не изменится. Смена только названия или описания сервер не затрагивает. Неподходящая модель — 404 &#x60;model_not_found&#x60;.
     * @param solutionId ID AI-решения (required)
     * @param updateAiSolution  (required)
     * @param _callback The callback to be executed when the API call finishes
     * @return The request call
     * @throws ApiException If fail to process the API call, e.g. serializing the request body object
     * @http.response.details
     <table summary="Response Details" border="1">
        <tr><td> Status Code </td><td> Description </td><td> Response Headers </td></tr>
        <tr><td> 200 </td><td> Объект JSON c ключом &#x60;ai_solution&#x60; </td><td>  -  </td></tr>
        <tr><td> 400 </td><td> Некорректный запрос </td><td>  -  </td></tr>
        <tr><td> 401 </td><td> Не авторизован </td><td>  -  </td></tr>
        <tr><td> 403 </td><td> Запрещено </td><td>  -  </td></tr>
        <tr><td> 404 </td><td> Не найдено </td><td>  -  </td></tr>
        <tr><td> 429 </td><td> Слишком много запросов </td><td>  -  </td></tr>
        <tr><td> 500 </td><td> Внутренняя ошибка сервера </td><td>  -  </td></tr>
        <tr><td> 502 </td><td> Сервер решения не применил изменение (&#x60;ai_solution_configuration_failed&#x60;) </td><td>  -  </td></tr>
        <tr><td> 503 </td><td> Сервер решения нельзя настроить: он выключен, недоступен, ещё разворачивается или создан старой версией решения (&#x60;ai_solution_machine_is_unavailable&#x60;), либо у него нет публичного IP (&#x60;ai_solution_chat_is_unavailable&#x60;) </td><td>  -  </td></tr>
     </table>
     */
    public okhttp3.Call updateAiSolutionAsync(Integer solutionId, UpdateAiSolution updateAiSolution, final ApiCallback<CreateAiSolution201Response> _callback) throws ApiException {

        okhttp3.Call localVarCall = updateAiSolutionValidateBeforeCall(solutionId, updateAiSolution, _callback);
        Type localVarReturnType = new TypeToken<CreateAiSolution201Response>(){}.getType();
        localVarApiClient.executeAsync(localVarCall, localVarReturnType, _callback);
        return localVarCall;
    }
}
