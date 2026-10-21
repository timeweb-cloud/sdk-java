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

import org.openapitools.client.ApiException;
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
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * API tests for AiAgentToolsApi
 */
@Disabled
public class AiAgentToolsApiTest {

    private final AiAgentToolsApi api = new AiAgentToolsApi();

    /**
     * Подключение MCP-инструмента к агенту
     *
     * Чтобы подключить MCP-инструмент к агенту, отправьте POST-запрос на &#x60;/api/v1/cloud-ai/agents/{agent_id}/tools&#x60;.  В &#x60;allowed_methods&#x60; перечислите методы, которые агенту разрешено вызывать. Подходят только имена из &#x60;methods[].name&#x60; инструмента, иначе вернётся 400 с &#x60;error_code&#x60; &#x60;invalid_methods_provided&#x60;. Повторно подключить уже подключённый инструмент нельзя. Чтобы изменить список методов, используйте PUT-запрос.
     *
     * @throws ApiException if the Api call fails
     */
    @Test
    public void connectAgentToolToAgentTest() throws ApiException {
        Integer agentId = null;
        ConnectAgentTool connectAgentTool = null;
        api.connectAgentToolToAgent(agentId, connectAgentTool);
        // TODO: test validations
    }

    /**
     * Подключение собственного MCP-сервера
     *
     * Чтобы подключить собственный MCP-сервер, отправьте POST-запрос на &#x60;/api/v1/cloud-ai/tools&#x60;.  Платформа сразу подключается к серверу и сохраняет список его методов. Если сервер недоступен или отвечает ошибкой, инструмент не создаётся: вернётся 400 с &#x60;error_code&#x60; &#x60;invalid_methods_provided&#x60;. Адреса частных сетей и localhost отклоняются с ошибкой 400.  Для &#x60;auth_data.type &#x3D; oauth2&#x60; подключение к серверу при создании не проверяется. Инструмент создаётся в статусе &#x60;pending_auth&#x60; с пустым списком методов, а методы загружаются после авторизации пользователя: см. «Получение ссылки на OAuth2-авторизацию инструмента». Если не удалось определить адреса авторизации или зарегистрировать клиента, вернётся 401 с &#x60;error_code&#x60; &#x60;tool_authentication_failed&#x60;.  Чтобы агент мог пользоваться инструментом, подключите его к агенту.
     *
     * @throws ApiException if the Api call fails
     */
    @Test
    public void createAgentToolTest() throws ApiException {
        CreateTool createTool = null;
        CreateAgentTool201Response response = api.createAgentTool(createTool);
        // TODO: test validations
    }

    /**
     * Удаление MCP-инструмента
     *
     * Чтобы удалить MCP-инструмент, отправьте DELETE-запрос на &#x60;/api/v1/cloud-ai/tools/{id}&#x60;.  Инструмент автоматически отключается от всех агентов.
     *
     * @throws ApiException if the Api call fails
     */
    @Test
    public void deleteAgentToolTest() throws ApiException {
        Integer id = null;
        api.deleteAgentTool(id);
        // TODO: test validations
    }

    /**
     * Отключение MCP-инструмента от агента
     *
     * Чтобы отключить MCP-инструмент от агента, отправьте DELETE-запрос на &#x60;/api/v1/cloud-ai/agents/{agent_id}/tools/{tool_id}&#x60;.  Сам инструмент остаётся в аккаунте и у других агентов.
     *
     * @throws ApiException if the Api call fails
     */
    @Test
    public void disconnectAgentToolFromAgentTest() throws ApiException {
        Integer agentId = null;
        Integer toolId = null;
        api.disconnectAgentToolFromAgent(agentId, toolId);
        // TODO: test validations
    }

    /**
     * Получение MCP-инструмента
     *
     * Чтобы получить MCP-инструмент, отправьте GET-запрос на &#x60;/api/v1/cloud-ai/tools/{id}&#x60;.  По полю &#x60;status&#x60; можно отследить завершение OAuth2-авторизации: после неё статус меняется с &#x60;pending_auth&#x60; на &#x60;active&#x60;.
     *
     * @throws ApiException if the Api call fails
     */
    @Test
    public void getAgentToolTest() throws ApiException {
        Integer id = null;
        CreateAgentTool201Response response = api.getAgentTool(id);
        // TODO: test validations
    }

    /**
     * Получение ссылки на OAuth2-авторизацию инструмента
     *
     * Чтобы получить ссылку на OAuth2-авторизацию MCP-инструмента, отправьте GET-запрос на &#x60;/api/v1/cloud-ai/tools/oauth2/authorize-url/{tool_id}&#x60;.  Откройте &#x60;authorize_url&#x60; в браузере пользователя. Ссылка действует 30 минут. После входа и согласия провайдер вернёт пользователя на адрес возврата платформы &#x60;https://api.timeweb.cloud/api/v1/cloud-ai/oauth2/callback&#x60;. Платформа обменяет код на токены, переведёт инструмент в статус &#x60;active&#x60; и загрузит список методов.  Чтобы узнать результат, запрашивайте инструмент, пока его статус не сменится с &#x60;pending_auth&#x60; на &#x60;active&#x60;. Этим же запросом можно повторно авторизовать уже активный инструмент.
     *
     * @throws ApiException if the Api call fails
     */
    @Test
    public void getAgentToolOAuth2AuthorizeUrlTest() throws ApiException {
        Integer toolId = null;
        String redirectUrl = null;
        AuthorizeUrl response = api.getAgentToolOAuth2AuthorizeUrl(toolId, redirectUrl);
        // TODO: test validations
    }

    /**
     * Получение цен системных инструментов
     *
     * Чтобы получить цены системных инструментов платформы, например веб-поиска, отправьте GET-запрос на &#x60;/api/v1/cloud-ai/tools/templates/prices&#x60;.
     *
     * @throws ApiException if the Api call fails
     */
    @Test
    public void getAgentToolTemplatePricesTest() throws ApiException {
        String locale = null;
        GetAgentToolTemplatePrices200Response response = api.getAgentToolTemplatePrices(locale);
        // TODO: test validations
    }

    /**
     * Получение шаблонов MCP-инструментов из маркетплейса
     *
     * Чтобы получить готовые интеграции маркетплейса, отправьте GET-запрос на &#x60;/api/v1/cloud-ai/tools/templates/marketplace&#x60;.  Сначала идут самые популярные шаблоны. Поле &#x60;config_schema&#x60; описывает, что нужно передать в &#x60;config_values&#x60; при установке.
     *
     * @throws ApiException if the Api call fails
     */
    @Test
    public void getAgentToolTemplatesTest() throws ApiException {
        GetAgentToolTemplates200Response response = api.getAgentToolTemplates();
        // TODO: test validations
    }

    /**
     * Получение списка MCP-инструментов
     *
     * Чтобы получить список MCP-инструментов аккаунта, отправьте GET-запрос на &#x60;/api/v1/cloud-ai/tools&#x60;.  В списке есть и собственные MCP-серверы, и инструменты, установленные из маркетплейса. Сначала идут новые. Удалённые инструменты не возвращаются. Секреты в &#x60;auth_data&#x60; заменены на &#x60;********&#x60;.
     *
     * @throws ApiException if the Api call fails
     */
    @Test
    public void getAgentToolsTest() throws ApiException {
        GetAgentTools200Response response = api.getAgentTools();
        // TODO: test validations
    }

    /**
     * Получение MCP-инструментов агента
     *
     * Чтобы получить MCP-инструменты, подключённые к агенту, отправьте GET-запрос на &#x60;/api/v1/cloud-ai/agents/{agent_id}/tools&#x60;.  В ответе — инструменты целиком, в том же виде, что и в списке инструментов аккаунта. Списка разрешённых агенту методов в ответе нет.
     *
     * @throws ApiException if the Api call fails
     */
    @Test
    public void getAgentToolsByAgentTest() throws ApiException {
        Integer agentId = null;
        GetAgentToolsByAgent200Response response = api.getAgentToolsByAgent(agentId);
        // TODO: test validations
    }

    /**
     * Установка MCP-инструмента из маркетплейса
     *
     * Чтобы установить MCP-инструмент из шаблона маркетплейса, отправьте POST-запрос на &#x60;/api/v1/cloud-ai/tools/install&#x60;.  Значения полей из &#x60;config_schema&#x60; шаблона передайте в &#x60;config_values&#x60;. Платформа соберёт из них заголовки авторизации, подключится к MCP-серверу и сохранит список методов. Если у шаблона есть &#x60;config_schema&#x60;, поле &#x60;auth_data&#x60; не используется. Если подключиться к серверу не удалось, вернётся 400 с &#x60;error_code&#x60; &#x60;invalid_methods_provided&#x60;.  Один шаблон можно установить несколько раз, например с разными ключами.
     *
     * @throws ApiException if the Api call fails
     */
    @Test
    public void installAgentToolFromTemplateTest() throws ApiException {
        InstallTool installTool = null;
        CreateAgentTool201Response response = api.installAgentToolFromTemplate(installTool);
        // TODO: test validations
    }

    /**
     * Обновление списка методов MCP-инструмента
     *
     * Чтобы заново получить список методов с MCP-сервера, отправьте POST-запрос на &#x60;/api/v1/cloud-ai/tools/{id}/refresh-methods&#x60;.  Список &#x60;methods&#x60; заменяется полученным с сервера, а методы, которых больше нет, убираются из разрешённых у всех агентов. Если обновление прошло успешно, инструмент переходит в статус &#x60;active&#x60;. Если сервер недоступен, инструмент переходит в статус &#x60;error&#x60; с причиной в &#x60;status_info&#x60;, а запрос возвращает 400 с &#x60;error_code&#x60; &#x60;invalid_methods_provided&#x60;.
     *
     * @throws ApiException if the Api call fails
     */
    @Test
    public void refreshAgentToolMethodsTest() throws ApiException {
        Integer id = null;
        CreateAgentTool201Response response = api.refreshAgentToolMethods(id);
        // TODO: test validations
    }

    /**
     * Изменение MCP-инструмента
     *
     * Чтобы изменить MCP-инструмент, отправьте PUT-запрос на &#x60;/api/v1/cloud-ai/tools/{id}&#x60;, передав только изменяемые поля.  Если передать в &#x60;auth_data&#x60; секрет &#x60;********&#x60; из ответа API, сохранённый секрет не изменится. Изменение адреса или авторизации не обновляет список методов: после него вызовите обновление методов. Статус &#x60;inactive&#x60; выключает инструмент у всех агентов, к которым он подключён.
     *
     * @throws ApiException if the Api call fails
     */
    @Test
    public void updateAgentToolTest() throws ApiException {
        Integer id = null;
        UpdateTool updateTool = null;
        CreateAgentTool201Response response = api.updateAgentTool(id, updateTool);
        // TODO: test validations
    }

    /**
     * Изменение разрешённых методов MCP-инструмента у агента
     *
     * Чтобы изменить список методов, которые агенту разрешено вызывать, отправьте PUT-запрос на &#x60;/api/v1/cloud-ai/agents/{agent_id}/tools/{tool_id}&#x60;.  Список заменяется целиком. Подходят только имена из &#x60;methods[].name&#x60; инструмента, иначе вернётся 400 с &#x60;error_code&#x60; &#x60;invalid_methods_provided&#x60;. Если инструмент не подключён к агенту, вернётся 404 с &#x60;error_code&#x60; &#x60;agent-tool_link_not_found&#x60;.
     *
     * @throws ApiException if the Api call fails
     */
    @Test
    public void updateAgentToolConnectionTest() throws ApiException {
        Integer agentId = null;
        Integer toolId = null;
        UpdateAgentTool updateAgentTool = null;
        api.updateAgentToolConnection(agentId, toolId, updateAgentTool);
        // TODO: test validations
    }

}
