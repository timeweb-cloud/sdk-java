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
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * API tests for AiAgentIntegrationsApi
 */
@Disabled
public class AiAgentIntegrationsApiTest {

    private final AiAgentIntegrationsApi api = new AiAgentIntegrationsApi();

    /**
     * Подключение бота MAX к агенту
     *
     * Чтобы подключить бота MAX к AI агенту, отправьте POST-запрос на &#x60;/api/v1/cloud-ai/max-bots&#x60;, передав ID агента и токен бота от @MasterBot.  Платформа проверит токен в MAX, сохранит бота и сама подпишет его на события (вебхук) — настраивать подписку в MAX не нужно. После этого агент начинает отвечать пользователям бота.  Один бот может быть подключен только к одному агенту. Если бот уже подключен к агенту, который был удален, бот будет переподключен к новому агенту. Ограничение доступа по списку пользователей для ботов MAX не поддерживается.  Ошибки: - &#x60;400 invalid_max_token&#x60; — MAX не принял токен; - &#x60;400 max_webhook_error&#x60; — не удалось подписать бота на события; - &#x60;409 bot_already_exists&#x60; — бот уже подключен к существующему агенту или к другому аккаунту.  Тело ответа будет представлять собой объект JSON с ключом &#x60;bot&#x60;.
     *
     * @throws ApiException if the Api call fails
     */
    @Test
    public void createAgentBotMaxBotTest() throws ApiException {
        CreateMaxBot createMaxBot = null;
        CreateAgentBotTelegramBot201Response response = api.createAgentBotMaxBot(createMaxBot);
        // TODO: test validations
    }

    /**
     * Подключение Telegram-бота к агенту
     *
     * Чтобы подключить Telegram-бота к AI агенту, отправьте POST-запрос на &#x60;/api/v1/cloud-ai/bots&#x60;, передав ID агента и токен бота от @BotFather.  Платформа проверит токен в Telegram, сохранит бота и сама установит ему вебхук — настраивать вебхук в Telegram не нужно. После этого агент начинает отвечать пользователям бота. Ранее накопленные у бота обновления сбрасываются.  Один бот может быть подключен только к одному агенту. Если бот уже подключен к агенту, который был удален, бот будет переподключен к новому агенту.  Ошибки: - &#x60;400 invalid_telegram_token&#x60; — Telegram не принял токен; - &#x60;400 telegram_webhook_error&#x60; — не удалось установить вебхук; - &#x60;409 bot_already_exists&#x60; — бот уже подключен к существующему агенту или к другому аккаунту.  Тело ответа будет представлять собой объект JSON с ключом &#x60;bot&#x60;.
     *
     * @throws ApiException if the Api call fails
     */
    @Test
    public void createAgentBotTelegramBotTest() throws ApiException {
        CreateBot createBot = null;
        CreateAgentBotTelegramBot201Response response = api.createAgentBotTelegramBot(createBot);
        // TODO: test validations
    }

    /**
     * Подключение агента к Crisp
     *
     * Чтобы подключить AI агента к Crisp, отправьте POST-запрос на &#x60;/api/v1/cloud-ai/agent/{agent_id}/crisp&#x60;, передав Website ID сайта и API-токен Crisp (Identifier и Key).  Платформа проверит токен в Crisp и вернет &#x60;webhook_secret&#x60;. Затем добавьте в Crisp вебхук &#x60;https://api.timeweb.cloud/api/v1/crisp/{website_id}/webhook/{webhook_secret}&#x60; — подробнее в разделе «Интеграции AI агентов».  К одному агенту можно подключить один сайт Crisp, и один сайт — только к одному агенту.  Ошибки: - &#x60;400 invalid_crisp_token&#x60; — Crisp не принял токен для этого сайта; - &#x60;409 bot_already_exists&#x60; — у агента уже есть интеграция с Crisp или сайт подключен к другому агенту; - &#x60;503&#x60; — интеграция с Crisp временно недоступна.  Тело ответа будет представлять собой объект JSON с ключом &#x60;crisp_integration&#x60;.
     *
     * @throws ApiException if the Api call fails
     */
    @Test
    public void createAgentIntegrationCrispTest() throws ApiException {
        Integer agentId = null;
        CreateCrispIntegration createCrispIntegration = null;
        GetAgentIntegrationCrisp200Response response = api.createAgentIntegrationCrisp(agentId, createCrispIntegration);
        // TODO: test validations
    }

    /**
     * Подключение агента к Jivo
     *
     * Чтобы подключить AI агента к Jivo, отправьте POST-запрос на &#x60;/api/v1/cloud-ai/agent/{agent_id}/jivo&#x60;, передав Provider ID, выданный поддержкой Jivo.  Подготовка описана в разделе «Интеграции AI агентов»: адрес для событий и токен бота нужно заранее передать в поддержку Jivo.  Если агент не найден, вернется ошибка &#x60;404 agent_not_found&#x60;.  К одному агенту можно подключить одну интеграцию Jivo. Если она уже есть, вернется ошибка &#x60;409 bot_already_exists&#x60;.  Тело ответа будет представлять собой объект JSON с ключом &#x60;jivo_integration&#x60;.
     *
     * @throws ApiException if the Api call fails
     */
    @Test
    public void createAgentIntegrationJivoTest() throws ApiException {
        Integer agentId = null;
        CreateJivoIntegration createJivoIntegration = null;
        GetAgentIntegrationJivo200Response response = api.createAgentIntegrationJivo(agentId, createJivoIntegration);
        // TODO: test validations
    }

    /**
     * Отключение бота MAX
     *
     * Чтобы отключить бота MAX от агента, отправьте DELETE-запрос на &#x60;/api/v1/cloud-ai/max-bots/{bot_id}&#x60;.  Платформа отпишет бота от событий в MAX и удалит подключение. Сам бот в MAX не удаляется — его можно подключить снова.
     *
     * @throws ApiException if the Api call fails
     */
    @Test
    public void deleteAgentBotMaxBotTest() throws ApiException {
        UUID botId = null;
        api.deleteAgentBotMaxBot(botId);
        // TODO: test validations
    }

    /**
     * Отключение Telegram-бота
     *
     * Чтобы отключить Telegram-бота от агента, отправьте DELETE-запрос на &#x60;/api/v1/cloud-ai/bots/{bot_id}&#x60;.  Платформа снимет вебхук бота в Telegram и удалит подключение. Сам бот в Telegram не удаляется — его можно подключить снова.
     *
     * @throws ApiException if the Api call fails
     */
    @Test
    public void deleteAgentBotTelegramBotTest() throws ApiException {
        UUID botId = null;
        api.deleteAgentBotTelegramBot(botId);
        // TODO: test validations
    }

    /**
     * Отключение агента от Crisp
     *
     * Чтобы отключить AI агента от Crisp, отправьте DELETE-запрос на &#x60;/api/v1/cloud-ai/agent/{agent_id}/crisp&#x60;.  После удаления интеграции агент перестает отвечать в чатах Crisp. Вебхук в настройках Crisp можно удалить вручную.
     *
     * @throws ApiException if the Api call fails
     */
    @Test
    public void deleteAgentIntegrationCrispTest() throws ApiException {
        Integer agentId = null;
        api.deleteAgentIntegrationCrisp(agentId);
        // TODO: test validations
    }

    /**
     * Отключение агента от Jivo
     *
     * Чтобы отключить AI агента от Jivo, отправьте DELETE-запрос на &#x60;/api/v1/cloud-ai/agent/{agent_id}/jivo&#x60;.  После удаления интеграции агент перестает отвечать на события Jivo. Настройки бота в самом Jivo не меняются.
     *
     * @throws ApiException if the Api call fails
     */
    @Test
    public void deleteAgentIntegrationJivoTest() throws ApiException {
        Integer agentId = null;
        api.deleteAgentIntegrationJivo(agentId);
        // TODO: test validations
    }

    /**
     * Получение информации о боте MAX
     *
     * Чтобы получить информацию о боте MAX, отправьте GET-запрос на &#x60;/api/v1/cloud-ai/max-bots/{bot_id}&#x60;.  Тело ответа будет представлять собой объект JSON с ключом &#x60;bot&#x60;.
     *
     * @throws ApiException if the Api call fails
     */
    @Test
    public void getAgentBotMaxBotTest() throws ApiException {
        UUID botId = null;
        CreateAgentBotTelegramBot201Response response = api.getAgentBotMaxBot(botId);
        // TODO: test validations
    }

    /**
     * Получение списка ботов MAX
     *
     * Чтобы получить список ботов MAX, подключенных к агентам аккаунта, отправьте GET-запрос на &#x60;/api/v1/cloud-ai/max-bots&#x60;.  Тело ответа будет представлять собой объект JSON с ключами &#x60;bots&#x60; и &#x60;meta&#x60;.
     *
     * @throws ApiException if the Api call fails
     */
    @Test
    public void getAgentBotMaxBotsTest() throws ApiException {
        GetAgentBotMaxBots200Response response = api.getAgentBotMaxBots();
        // TODO: test validations
    }

    /**
     * Получение списка ботов MAX агента
     *
     * Чтобы получить список ботов MAX, подключенных к AI агенту, отправьте GET-запрос на &#x60;/api/v1/cloud-ai/agents/{agent_id}/max-bots&#x60;.  Тело ответа будет представлять собой объект JSON с ключами &#x60;bots&#x60; и &#x60;meta&#x60;.
     *
     * @throws ApiException if the Api call fails
     */
    @Test
    public void getAgentBotMaxBotsByAgentTest() throws ApiException {
        Integer agentId = null;
        GetAgentBotMaxBotsByAgent200Response response = api.getAgentBotMaxBotsByAgent(agentId);
        // TODO: test validations
    }

    /**
     * Получение информации о Telegram-боте
     *
     * Чтобы получить информацию о Telegram-боте, отправьте GET-запрос на &#x60;/api/v1/cloud-ai/bots/{bot_id}&#x60;.  Тело ответа будет представлять собой объект JSON с ключом &#x60;bot&#x60;.
     *
     * @throws ApiException if the Api call fails
     */
    @Test
    public void getAgentBotTelegramBotTest() throws ApiException {
        UUID botId = null;
        CreateAgentBotTelegramBot201Response response = api.getAgentBotTelegramBot(botId);
        // TODO: test validations
    }

    /**
     * Получение списка Telegram-ботов
     *
     * Чтобы получить список Telegram-ботов, подключенных к агентам аккаунта, отправьте GET-запрос на &#x60;/api/v1/cloud-ai/bots&#x60;.  Тело ответа будет представлять собой объект JSON с ключами &#x60;bots&#x60; и &#x60;meta&#x60;.
     *
     * @throws ApiException if the Api call fails
     */
    @Test
    public void getAgentBotTelegramBotsTest() throws ApiException {
        GetAgentBotTelegramBots200Response response = api.getAgentBotTelegramBots();
        // TODO: test validations
    }

    /**
     * Получение списка Telegram-ботов агента
     *
     * Чтобы получить список Telegram-ботов, подключенных к AI агенту, отправьте GET-запрос на &#x60;/api/v1/cloud-ai/agents/{agent_id}/bots&#x60;.  Тело ответа будет представлять собой объект JSON с ключами &#x60;bots&#x60; и &#x60;meta&#x60;.
     *
     * @throws ApiException if the Api call fails
     */
    @Test
    public void getAgentBotTelegramBotsByAgentTest() throws ApiException {
        Integer agentId = null;
        GetAgentBotTelegramBotsByAgent200Response response = api.getAgentBotTelegramBotsByAgent(agentId);
        // TODO: test validations
    }

    /**
     * Получение интеграции агента с Crisp
     *
     * Чтобы получить интеграцию AI агента с Crisp, отправьте GET-запрос на &#x60;/api/v1/cloud-ai/agent/{agent_id}/crisp&#x60;.  В ответе есть &#x60;webhook_secret&#x60; — по нему можно восстановить адрес вебхука для Crisp. Если агент не подключен к Crisp, вернется ошибка 404.  Тело ответа будет представлять собой объект JSON с ключом &#x60;crisp_integration&#x60;.
     *
     * @throws ApiException if the Api call fails
     */
    @Test
    public void getAgentIntegrationCrispTest() throws ApiException {
        Integer agentId = null;
        GetAgentIntegrationCrisp200Response response = api.getAgentIntegrationCrisp(agentId);
        // TODO: test validations
    }

    /**
     * Получение интеграции агента с Jivo
     *
     * Чтобы получить интеграцию AI агента с Jivo, отправьте GET-запрос на &#x60;/api/v1/cloud-ai/agent/{agent_id}/jivo&#x60;.  Если агент не подключен к Jivo, вернется ошибка 404.  Тело ответа будет представлять собой объект JSON с ключом &#x60;jivo_integration&#x60;.
     *
     * @throws ApiException if the Api call fails
     */
    @Test
    public void getAgentIntegrationJivoTest() throws ApiException {
        Integer agentId = null;
        GetAgentIntegrationJivo200Response response = api.getAgentIntegrationJivo(agentId);
        // TODO: test validations
    }

    /**
     * Изменение бота MAX
     *
     * Чтобы изменить бота MAX, отправьте PATCH-запрос на &#x60;/api/v1/cloud-ai/max-bots/{bot_id}&#x60;, передав только изменяемые поля.  Через &#x60;token&#x60; можно заменить токен того же бота: платформа проверит новый токен и заново подпишет бота на события. Перенести подключение на другого бота нельзя — для этого удалите бота и подключите новый.  Ошибки: - &#x60;400 invalid_max_token&#x60; — MAX не принял токен или токен принадлежит другому боту; - &#x60;400 max_webhook_error&#x60; — не удалось подписать бота на события.  Тело ответа будет представлять собой объект JSON с ключом &#x60;bot&#x60;.
     *
     * @throws ApiException if the Api call fails
     */
    @Test
    public void updateAgentBotMaxBotTest() throws ApiException {
        UUID botId = null;
        UpdateMaxBot updateMaxBot = null;
        CreateAgentBotTelegramBot201Response response = api.updateAgentBotMaxBot(botId, updateMaxBot);
        // TODO: test validations
    }

    /**
     * Изменение Telegram-бота
     *
     * Чтобы изменить Telegram-бота, отправьте PATCH-запрос на &#x60;/api/v1/cloud-ai/bots/{bot_id}&#x60;, передав только изменяемые поля.  Через &#x60;token&#x60; можно заменить токен того же бота, например после его перевыпуска в @BotFather: платформа проверит новый токен и переустановит вебхук. Перенести подключение на другого бота нельзя — для этого удалите бота и подключите новый. Агента, к которому подключен бот, этим запросом сменить нельзя.  Ошибки: - &#x60;400 invalid_telegram_token&#x60; — Telegram не принял токен или токен принадлежит другому боту; - &#x60;400 telegram_webhook_error&#x60; — не удалось переустановить вебхук.  Тело ответа будет представлять собой объект JSON с ключом &#x60;bot&#x60;.
     *
     * @throws ApiException if the Api call fails
     */
    @Test
    public void updateAgentBotTelegramBotTest() throws ApiException {
        UUID botId = null;
        UpdateBot updateBot = null;
        CreateAgentBotTelegramBot201Response response = api.updateAgentBotTelegramBot(botId, updateBot);
        // TODO: test validations
    }

}
