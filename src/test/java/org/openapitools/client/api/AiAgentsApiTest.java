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
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * API tests for AiAgentsApi
 */
@Disabled
public class AiAgentsApiTest {

    private final AiAgentsApi api = new AiAgentsApi();

    /**
     * Добавление дополнительного пакета токенов
     *
     * Чтобы докупить дополнительные пакеты токенов для AI агента, отправьте POST-запрос на &#x60;/api/v1/cloud-ai/agents/{id}/add-additional-token-package&#x60;.  Метод относится к устаревшей схеме оплаты пакетами токенов и работает только для агентов с пакетом (&#x60;token_package_id&#x60; в ответе агента). Для агентов с поресурсной оплатой вернётся 400 &#x60;per-resource_agents_do_not_support_additional_token_packages&#x60;. Стоимость (цена дополнительного пакета модели агента × &#x60;count&#x60;) сразу списывается с баланса; при нехватке средств — 400 &#x60;insufficient_balance&#x60;, если подписка агента истекла — 400 &#x60;subscription_expired&#x60;. После покупки заблокированный агент разблокируется.  Для токенов дополнительных пользователей аккаунта нужна роль &#x60;ai:write&#x60;.
     *
     * @throws ApiException if the Api call fails
     */
    @Test
    public void addAdditionalTokenPackageTest() throws ApiException {
        Integer id = null;
        AddTokenPackage addTokenPackage = null;
        api.addAdditionalTokenPackage(id, addTokenPackage);
        // TODO: test validations
    }

    /**
     * Смена модели AI агента
     *
     * Чтобы сменить основную модель AI агента, отправьте PATCH-запрос на &#x60;/api/v1/cloud-ai/agents/{id}/model&#x60;.  Параметры модели из &#x60;settings.model&#x60;, которые новая модель не поддерживает, отбрасываются. Смена модели доступна только агентам с поресурсной оплатой (для агента с пакетом токенов — 400 &#x60;model_change_is_allowed_only_for_per-resource_account_and_agent&#x60;). Если модель не найдена или недоступна — 404 &#x60;model_not_found&#x60;.  Для токенов дополнительных пользователей аккаунта нужна роль &#x60;ai:write&#x60;.
     *
     * @throws ApiException if the Api call fails
     */
    @Test
    public void changeAgentModelTest() throws ApiException {
        Integer id = null;
        ChangeAgentModel changeAgentModel = null;
        CreateAgent201Response response = api.changeAgentModel(id, changeAgentModel);
        // TODO: test validations
    }

    /**
     * Создание AI агента
     *
     * Чтобы создать AI агента, отправьте POST-запрос на &#x60;/api/v1/cloud-ai/agents&#x60;, задав необходимые атрибуты.  Метод устарел — используйте &#x60;POST /api/v2/cloud-ai/agents&#x60;. Поле &#x60;token_package_id&#x60; обязательно, но не учитывается: агент всегда создаётся с поресурсной оплатой (оплачиваются фактические запросы к модели).  Для токенов дополнительных пользователей аккаунта нужна роль &#x60;ai:write&#x60;.
     *
     * @throws ApiException if the Api call fails
     */
    @Test
    public void createAgentTest() throws ApiException {
        CreateAgent createAgent = null;
        CreateAgent201Response response = api.createAgent(createAgent);
        // TODO: test validations
    }

    /**
     * Создание лимита агента
     *
     * Чтобы создать лимит AI агента, отправьте POST-запрос на &#x60;/api/v1/cloud-ai/agents/{id}/entity-limits&#x60;.  Лимиты агента ограничивают расход на его запросы за период (&#x60;hour&#x60;, &#x60;day&#x60;, &#x60;week&#x60;, &#x60;month&#x60;) — в токенах (&#x60;limit&#x60;) или в деньгах (&#x60;money_limit&#x60;, приоритетнее). Лимит действует на все модели агента (&#x60;model_id&#x60; &#x3D; &#x60;null&#x60;) или на одну модель. При достижении лимита запросы к агенту отклоняются до конца периода.  Нужно передать &#x60;limit&#x60; или &#x60;money_limit&#x60; (иначе — 400 &#x60;entity_keys_limit_value_required&#x60;). Если лимит с тем же периодом и моделью уже есть — 409 &#x60;entity_keys_limit_already_exists&#x60; (измените его или используйте синхронизацию).  Для токенов дополнительных пользователей аккаунта нужна роль &#x60;ai:write&#x60;.
     *
     * @throws ApiException if the Api call fails
     */
    @Test
    public void createAgentEntityLimitTest() throws ApiException {
        Integer id = null;
        CreateAgentEntityLimit createAgentEntityLimit = null;
        CreateAgentEntityLimit201Response response = api.createAgentEntityLimit(id, createAgentEntityLimit);
        // TODO: test validations
    }

    /**
     * Создание AI агента
     *
     * Чтобы создать AI агента, отправьте POST-запрос на &#x60;/api/v2/cloud-ai/agents&#x60;, задав необходимые атрибуты.  Агент создаётся с поресурсной оплатой: оплачиваются фактические запросы к модели. Если не переданы настройки виджета, подставляются значения по умолчанию. Дневной лимит расходов можно задать сразу полем &#x60;money_limit&#x60;, остальные лимиты — через лимиты агента (&#x60;/api/v1/cloud-ai/agents/{id}/entity-limits&#x60;).  Если модель не найдена или недоступна, вернётся 404 &#x60;model_not_found&#x60;; если модель не поддерживается — 400 &#x60;model_not_supported&#x60;; недопустимые параметры модели в &#x60;settings.model&#x60; — 400.  Вызывать созданного агента из своих приложений можно через OpenAI-совместимое API по его &#x60;access_id&#x60; — см. [документацию](https://agent.timeweb.cloud/docs).  Для токенов дополнительных пользователей аккаунта нужна роль &#x60;ai:write&#x60;.
     *
     * @throws ApiException if the Api call fails
     */
    @Test
    public void createAgentV2Test() throws ApiException {
        CreateAgentV2 createAgentV2 = null;
        CreateAgent201Response response = api.createAgentV2(createAgentV2);
        // TODO: test validations
    }

    /**
     * Удаление AI агента
     *
     * Чтобы удалить AI агента, отправьте DELETE-запрос на &#x60;/api/v1/cloud-ai/agents/{id}&#x60;.  Вместе с агентом отключаются его базы знаний, навыки и интеграции (Telegram, MAX, Jivo, Crisp). В теле запроса можно необязательно указать причину удаления.  Для токенов дополнительных пользователей аккаунта нужна роль &#x60;ai:write&#x60;.
     *
     * @throws ApiException if the Api call fails
     */
    @Test
    public void deleteAgentTest() throws ApiException {
        Integer id = null;
        DeleteAgent deleteAgent = null;
        api.deleteAgent(id, deleteAgent);
        // TODO: test validations
    }

    /**
     * Удаление лимита агента
     *
     * Чтобы удалить лимит AI агента, отправьте DELETE-запрос на &#x60;/api/v1/cloud-ai/agents/{id}/entity-limits/{limit_id}&#x60;.  Для токенов дополнительных пользователей аккаунта нужна роль &#x60;ai:write&#x60;.
     *
     * @throws ApiException if the Api call fails
     */
    @Test
    public void deleteAgentEntityLimitTest() throws ApiException {
        Integer id = null;
        UUID limitId = null;
        api.deleteAgentEntityLimit(id, limitId);
        // TODO: test validations
    }

    /**
     * Выгрузка чатов агента в CSV
     *
     * Чтобы выгрузить чаты AI агента в формате CSV, отправьте GET-запрос на &#x60;/api/v1/cloud-ai/agents/{id}/chats/docs&#x60;.  CSV возвращается строкой в поле &#x60;chats_csv.csv&#x60; тела JSON. &#x60;limit&#x60; и &#x60;offset&#x60; применяются к сообщениям, а не к чатам. Если передан &#x60;from&#x60;, выгружаются сообщения от &#x60;from&#x60; до &#x60;to&#x60; (по умолчанию — до текущего момента). Чаты внешнего чата не выгружаются.  Для токенов дополнительных пользователей аккаунта нужна роль &#x60;ai:read&#x60;.
     *
     * @throws ApiException if the Api call fails
     */
    @Test
    public void exportAgentChatsCsvTest() throws ApiException {
        Integer id = null;
        Integer offset = null;
        Integer limit = null;
        String order = null;
        OffsetDateTime from = null;
        OffsetDateTime to = null;
        String locale = null;
        ExportAgentChatsCsv200Response response = api.exportAgentChatsCsv(id, offset, limit, order, from, to, locale);
        // TODO: test validations
    }

    /**
     * Получение AI агента
     *
     * Чтобы получить информацию об AI агенте, отправьте GET-запрос на &#x60;/api/v1/cloud-ai/agents/{id}&#x60;.  Для токенов дополнительных пользователей аккаунта нужна роль &#x60;ai:read&#x60;.
     *
     * @throws ApiException if the Api call fails
     */
    @Test
    public void getAgentTest() throws ApiException {
        Integer id = null;
        CreateAgent201Response response = api.getAgent(id);
        // TODO: test validations
    }

    /**
     * Получение списка чатов агента
     *
     * Чтобы получить список чатов (диалогов) AI агента, отправьте GET-запрос на &#x60;/api/v1/cloud-ai/agents/{id}/chat-list&#x60;.  Для каждого чата возвращаются первое сообщение пользователя, количество оценок и среднее время ответа. Чаты внешнего чата сюда не входят. Фильтр по периоду применяется, только если переданы оба параметра &#x60;from&#x60; и &#x60;to&#x60;. В &#x60;meta.metrics&#x60; — сводка по чатам для фильтров.  Для токенов дополнительных пользователей аккаунта нужна роль &#x60;ai:read&#x60;.
     *
     * @throws ApiException if the Api call fails
     */
    @Test
    public void getAgentChatListTest() throws ApiException {
        Integer id = null;
        Integer offset = null;
        Integer limit = null;
        String order = null;
        OffsetDateTime from = null;
        OffsetDateTime to = null;
        String reactionType = null;
        List<String> responseTimeRanges = null;
        GetAgentChatList200Response response = api.getAgentChatList(id, offset, limit, order, from, to, reactionType, responseTimeRanges);
        // TODO: test validations
    }

    /**
     * Получение сообщений чата агента
     *
     * Чтобы получить сообщения чата AI агента, отправьте GET-запрос на &#x60;/api/v1/cloud-ai/agents/{id}/chat-list/{chat_id}/messages&#x60;.  Возвращаются сообщения пользователя и ответы агента. Фильтр по периоду применяется, только если переданы оба параметра &#x60;from&#x60; и &#x60;to&#x60;.  Для токенов дополнительных пользователей аккаунта нужна роль &#x60;ai:read&#x60;.
     *
     * @throws ApiException if the Api call fails
     */
    @Test
    public void getAgentChatMessagesTest() throws ApiException {
        Integer id = null;
        String chatId = null;
        Integer offset = null;
        Integer limit = null;
        String order = null;
        OffsetDateTime from = null;
        OffsetDateTime to = null;
        GetAgentChatMessages200Response response = api.getAgentChatMessages(id, chatId, offset, limit, order, from, to);
        // TODO: test validations
    }

    /**
     * Получение лимита агента
     *
     * Чтобы получить лимит AI агента, отправьте GET-запрос на &#x60;/api/v1/cloud-ai/agents/{id}/entity-limits/{limit_id}&#x60;.  Для токенов дополнительных пользователей аккаунта нужна роль &#x60;ai:read&#x60;.
     *
     * @throws ApiException if the Api call fails
     */
    @Test
    public void getAgentEntityLimitTest() throws ApiException {
        Integer id = null;
        UUID limitId = null;
        CreateAgentEntityLimit201Response response = api.getAgentEntityLimit(id, limitId);
        // TODO: test validations
    }

    /**
     * Получение лимитов агента
     *
     * Чтобы получить лимиты AI агента, отправьте GET-запрос на &#x60;/api/v1/cloud-ai/agents/{id}/entity-limits&#x60;.  Лимиты агента ограничивают расход на его запросы за период (&#x60;hour&#x60;, &#x60;day&#x60;, &#x60;week&#x60;, &#x60;month&#x60;) — в токенах (&#x60;limit&#x60;) или в деньгах (&#x60;money_limit&#x60;, приоритетнее). Лимит действует на все модели агента (&#x60;model_id&#x60; &#x3D; &#x60;null&#x60;) или на одну модель. При достижении лимита запросы к агенту отклоняются до конца периода.  Для токенов дополнительных пользователей аккаунта нужна роль &#x60;ai:read&#x60;.
     *
     * @throws ApiException if the Api call fails
     */
    @Test
    public void getAgentEntityLimitsTest() throws ApiException {
        Integer id = null;
        String limitType = null;
        GetAgentEntityLimits200Response response = api.getAgentEntityLimits(id, limitType);
        // TODO: test validations
    }

    /**
     * Получение статистики использования токенов агента
     *
     * Чтобы получить статистику использования токенов AI агента, отправьте GET-запрос на &#x60;/api/v1/cloud-ai/agents/{id}/statistic&#x60;.  Статистика разбита на интервалы длиной &#x60;interval&#x60; минут: для каждого интервала возвращается по точке на каждую модель, к которой были запросы за период, а для интервалов без запросов — одна нулевая точка без &#x60;model_name&#x60;. По умолчанию возвращаются последние 24 часа.  Для токенов дополнительных пользователей аккаунта нужна роль &#x60;ai:read&#x60;.
     *
     * @throws ApiException if the Api call fails
     */
    @Test
    public void getAgentStatisticsTest() throws ApiException {
        Integer id = null;
        OffsetDateTime startTime = null;
        OffsetDateTime endTime = null;
        Integer interval = null;
        GetAgentStatistics200Response response = api.getAgentStatistics(id, startTime, endTime, interval);
        // TODO: test validations
    }

    /**
     * Получение AI агента (v3)
     *
     * Чтобы получить информацию об AI агенте, отправьте GET-запрос на &#x60;/api/v3/cloud-ai/agents/{id}&#x60;.  Отличается от &#x60;/api/v1/cloud-ai/agents/{id}&#x60; тем, что вместо &#x60;knowledge_bases_ids&#x60; возвращает &#x60;knowledge_bases&#x60; — подключённые базы знаний с режимом поиска.  Для токенов дополнительных пользователей аккаунта нужна роль &#x60;ai:read&#x60;.
     *
     * @throws ApiException if the Api call fails
     */
    @Test
    public void getAgentV3Test() throws ApiException {
        Integer id = null;
        GetAgentV3200Response response = api.getAgentV3(id);
        // TODO: test validations
    }

    /**
     * Получение списка AI агентов
     *
     * Чтобы получить список AI агентов, отправьте GET-запрос на &#x60;/api/v1/cloud-ai/agents&#x60;.  Возвращаются все агенты аккаунта (без пагинации), включая приостановленных и заблокированных. Тело ответа будет представлять собой объект JSON с ключами &#x60;agents&#x60; и &#x60;meta&#x60;. Подключённые базы знаний вместе с режимом поиска возвращает метод &#x60;/api/v3/cloud-ai/agents&#x60;.  Для токенов дополнительных пользователей аккаунта нужна роль &#x60;ai:read&#x60;.
     *
     * @throws ApiException if the Api call fails
     */
    @Test
    public void getAgentsTest() throws ApiException {
        GetAgents200Response response = api.getAgents();
        // TODO: test validations
    }

    /**
     * Получение списка AI агентов (v3)
     *
     * Чтобы получить список AI агентов, отправьте GET-запрос на &#x60;/api/v3/cloud-ai/agents&#x60;.  Отличается от &#x60;/api/v1/cloud-ai/agents&#x60; тем, что вместо &#x60;knowledge_bases_ids&#x60; возвращает &#x60;knowledge_bases&#x60; — подключённые базы знаний с режимом поиска.  Для токенов дополнительных пользователей аккаунта нужна роль &#x60;ai:read&#x60;.
     *
     * @throws ApiException if the Api call fails
     */
    @Test
    public void getAgentsV3Test() throws ApiException {
        GetAgentsV3200Response response = api.getAgentsV3();
        // TODO: test validations
    }

    /**
     * Синхронизация лимитов агента за период
     *
     * Чтобы задать полный набор лимитов AI агента за период, отправьте PUT-запрос на &#x60;/api/v1/cloud-ai/agents/{id}/entity-limits/sync&#x60;.  Лимиты из запроса создаются или обновляются (сопоставление по &#x60;model_id&#x60;), а лимиты этого периода, которых нет в запросе, удаляются. Лимиты других периодов не затрагиваются. В ответе — актуальные лимиты периода. Если лимиты одновременно изменил другой запрос, вернётся 409 &#x60;entity_keys_limits_sync_conflict&#x60; — повторите запрос.  Для токенов дополнительных пользователей аккаунта нужна роль &#x60;ai:write&#x60;.
     *
     * @throws ApiException if the Api call fails
     */
    @Test
    public void syncAgentEntityLimitsTest() throws ApiException {
        Integer id = null;
        SyncAgentEntityLimits syncAgentEntityLimits = null;
        GetAgentEntityLimits200Response response = api.syncAgentEntityLimits(id, syncAgentEntityLimits);
        // TODO: test validations
    }

    /**
     * Обновление AI агента
     *
     * Чтобы обновить AI агента, отправьте PATCH-запрос на &#x60;/api/v1/cloud-ai/agents/{id}&#x60;, передав изменяемые поля.  Особенности: - &#x60;settings&#x60; заменяются целиком (кроме &#x60;web_search_allowed_sites&#x60;, &#x60;additional_models&#x60; и &#x60;memory_enabled&#x60;, которые сохраняются, если не переданы) — передавайте полный объект настроек, включая &#x60;widget&#x60;; - основную модель меняет отдельный метод &#x60;PATCH /api/v1/cloud-ai/agents/{id}/model&#x60;; - поле &#x60;token_limit&#x60; больше не поддерживается: запрос с ним отклоняется с кодом 410 — лимиты задаются через &#x60;/api/v1/cloud-ai/agents/{id}/entity-limits&#x60;; - ошибки привязки &#x60;ai_gateway_key_id&#x60;: 400 &#x60;ai_gateway_key_not_found&#x60;, &#x60;agent_is_already_migrated_to_another_ai_gateway_key&#x60;, &#x60;agent_is_not_eligible_for_ai_gateway_migration&#x60;; 503 — проверку временно не удалось выполнить; - изменить статус заблокированного или удалённого агента нельзя (400 &#x60;agent_can&#39;t_change_status&#x60;).  Для токенов дополнительных пользователей аккаунта нужна роль &#x60;ai:write&#x60;.
     *
     * @throws ApiException if the Api call fails
     */
    @Test
    public void updateAgentTest() throws ApiException {
        Integer id = null;
        UpdateAgent updateAgent = null;
        CreateAgent201Response response = api.updateAgent(id, updateAgent);
        // TODO: test validations
    }

    /**
     * Изменение лимита агента
     *
     * Чтобы изменить значение лимита AI агента, отправьте PATCH-запрос на &#x60;/api/v1/cloud-ai/agents/{id}/entity-limits/{limit_id}&#x60;.  Непереданные поля не меняются. Снять денежный лимит (&#x60;money_limit&#x60;: &#x60;null&#x60;) можно только вместе с передачей &#x60;limit&#x60;.  Для токенов дополнительных пользователей аккаунта нужна роль &#x60;ai:write&#x60;.
     *
     * @throws ApiException if the Api call fails
     */
    @Test
    public void updateAgentEntityLimitTest() throws ApiException {
        Integer id = null;
        UUID limitId = null;
        UpdateAgentEntityLimit updateAgentEntityLimit = null;
        CreateAgentEntityLimit201Response response = api.updateAgentEntityLimit(id, limitId, updateAgentEntityLimit);
        // TODO: test validations
    }

}
