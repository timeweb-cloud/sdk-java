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
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * API tests for AiSolutionsApi
 */
@Disabled
public class AiSolutionsApiTest {

    private final AiSolutionsApi api = new AiSolutionsApi();

    /**
     * Подключение Telegram-бота к AI-решению
     *
     * Чтобы подключить Telegram-бота к решению, отправьте POST-запрос на &#x60;/api/v1/cloud-ai/solutions/{solution_id}/telegram/bots&#x60;.  Принимает токен бота от @BotFather, проверяет его в Telegram и настраивает бота на сервере решения. Владелец подтверждается кодом: пока он не подтверждён, бот никому не отвечает. Напишите боту в личные сообщения, получите в ответ код и передайте его в метод подтверждения владельца.  К решению можно подключить только одного бота: повторный запрос для того же бота обновляет его токен, для другого бота — возвращает 409 &#x60;ai_solution_already_has_a_connected_telegram_bot&#x60;. Если Telegram отклонил токен, ответ — 400 &#x60;ai_solution_telegram_token_is_invalid&#x60;.  Перед вызовом убедитесь, что чат готов (&#x60;ready: true&#x60; в методе получения статуса чата): пока сервер решения разворачивается или выключен, запрос завершается ошибкой.
     *
     * @throws ApiException if the Api call fails
     */
    @Test
    public void addAiSolutionTelegramBotTest() throws ApiException {
        Integer solutionId = null;
        ConnectTelegram connectTelegram = null;
        AddAiSolutionTelegramBot201Response response = api.addAiSolutionTelegramBot(solutionId, connectTelegram);
        // TODO: test validations
    }

    /**
     * Подтверждение владельца Telegram-бота AI-решения
     *
     * Чтобы подтвердить владельца Telegram-бота, отправьте POST-запрос на &#x60;/api/v1/cloud-ai/solutions/{solution_id}/telegram/bots/{account_key}/pairing/approve&#x60;.  Передайте код, который бот прислал в личные сообщения в ответ на первое сообщение. После подтверждения агент отвечает этому пользователю Telegram в боте, а бот переходит в состояние &#x60;connected&#x60;. Владелец привязывается к конкретному боту. Повторный запрос с кодом, уже подтверждённым в последние 15 минут, считается успешным. Некорректный &#x60;account_key&#x60; — ответ 400 &#x60;ai_solution_telegram_token_is_invalid&#x60;.
     *
     * @throws ApiException if the Api call fails
     */
    @Test
    public void approveAiSolutionTelegramPairingTest() throws ApiException {
        Integer solutionId = null;
        String accountKey = null;
        ApproveTelegramPairing approveTelegramPairing = null;
        api.approveAiSolutionTelegramPairing(solutionId, accountKey, approveTelegramPairing);
        // TODO: test validations
    }

    /**
     * Создание AI-решения
     *
     * Чтобы создать AI-решение, отправьте POST-запрос на &#x60;/api/v1/cloud-ai/solutions&#x60;.  Выпускает для решения ключ AI Gateway и создаёт облачный сервер из образа решения; агент на сервере настраивается на выбранную модель через этот ключ. Сервер тарифицируется по выбранному тарифу, обращения к модели — через ключ AI Gateway.  Решение возвращается в статусе &#x60;creating&#x60;: развёртывание сервера и настройка агента занимают несколько минут. Дождитесь статуса &#x60;active&#x60;, а затем &#x60;ready: true&#x60; в методе получения статуса чата.  Тариф (&#x60;preset_id&#x60;) должен быть из той же локации, что и зона доступности (&#x60;availability_zone&#x60; или зона по умолчанию для региона аккаунта), иначе — 400 &#x60;invalid_location_zone&#x60;. Соответствие тарифа &#x60;requirements&#x60; типа решения при создании не проверяется — выбирайте тариф не ниже требований сами. Ответ 409 &#x60;no_free_node&#x60; означает, что в выбранной зоне сейчас нет свободных ресурсов под этот тариф — выберите другой тариф или зону. Неподходящая или устаревшая модель — 404 &#x60;model_not_found&#x60;.
     *
     * @throws ApiException if the Api call fails
     */
    @Test
    public void createAiSolutionTest() throws ApiException {
        CreateAiSolution createAiSolution = null;
        CreateAiSolution201Response response = api.createAiSolution(createAiSolution);
        // TODO: test validations
    }

    /**
     * Удаление AI-решения
     *
     * Чтобы удалить AI-решение, отправьте DELETE-запрос на &#x60;/api/v1/cloud-ai/solutions/{solution_id}&#x60;.  Решение удаляется вместе с сервером и ключом AI Gateway. Действие необратимо: решение и история его чата становятся недоступны.
     *
     * @throws ApiException if the Api call fails
     */
    @Test
    public void deleteAiSolutionTest() throws ApiException {
        Integer solutionId = null;
        api.deleteAiSolution(solutionId);
        // TODO: test validations
    }

    /**
     * Получение AI-решения
     *
     * Чтобы получить AI-решение, отправьте GET-запрос на &#x60;/api/v1/cloud-ai/solutions/{solution_id}&#x60;.  Статус решения выводится из текущего состояния его сервера. Тело ответа будет представлять собой объект JSON с ключом &#x60;ai_solution&#x60;.
     *
     * @throws ApiException if the Api call fails
     */
    @Test
    public void getAiSolutionTest() throws ApiException {
        Integer solutionId = null;
        CreateAiSolution201Response response = api.getAiSolution(solutionId);
        // TODO: test validations
    }

    /**
     * Получение всей истории чата AI-решения
     *
     * Чтобы получить сообщения всех разговоров решения, отправьте GET-запрос на &#x60;/api/v1/cloud-ai/solutions/{solution_id}/chat/history&#x60;.  В отличие от &#x60;chat/messages&#x60; (только основной разговор) и &#x60;chat/conversations/{conversation_id}/messages&#x60; (конкретный разговор), возвращает сообщения из всех разговоров сразу. Сообщения отсортированы по дате создания от новых к старым. Тело ответа будет представлять собой объект JSON с ключами &#x60;ai_solution_chat_messages&#x60; и &#x60;meta&#x60;.
     *
     * @throws ApiException if the Api call fails
     */
    @Test
    public void getAiSolutionChatFullHistoryTest() throws ApiException {
        Integer solutionId = null;
        Integer limit = null;
        Integer offset = null;
        GetAiSolutionMainChatHistory200Response response = api.getAiSolutionChatFullHistory(solutionId, limit, offset);
        // TODO: test validations
    }

    /**
     * Получение истории разговора AI-решения
     *
     * Чтобы получить историю разговора, отправьте GET-запрос на &#x60;/api/v1/cloud-ai/solutions/{solution_id}/chat/conversations/{conversation_id}/messages&#x60;.  Сообщения отсортированы по дате создания от новых к старым: первая страница — последние сообщения разговора. Для несуществующего разговора возвращается пустой список. Тело ответа будет представлять собой объект JSON с ключами &#x60;ai_solution_chat_messages&#x60; и &#x60;meta&#x60;.
     *
     * @throws ApiException if the Api call fails
     */
    @Test
    public void getAiSolutionChatHistoryTest() throws ApiException {
        Integer solutionId = null;
        String conversationId = null;
        Integer limit = null;
        Integer offset = null;
        GetAiSolutionMainChatHistory200Response response = api.getAiSolutionChatHistory(solutionId, conversationId, limit, offset);
        // TODO: test validations
    }

    /**
     * Получение статуса чата AI-решения
     *
     * Чтобы узнать, готов ли агент решения принимать сообщения, отправьте GET-запрос на &#x60;/api/v1/cloud-ai/solutions/{solution_id}/chat/status&#x60;.  Готовность чата отличается от статуса решения: тот выводится из состояния сервера, а сервер включается задолго до того, как будет установлено и настроено ПО агента. После создания или запуска решения опрашивайте этот метод, пока &#x60;ready&#x60; не станет &#x60;true&#x60;. Тело ответа будет представлять собой объект JSON с ключом &#x60;ai_solution_chat_status&#x60;.
     *
     * @throws ApiException if the Api call fails
     */
    @Test
    public void getAiSolutionChatStatusTest() throws ApiException {
        Integer solutionId = null;
        GetAiSolutionChatStatus200Response response = api.getAiSolutionChatStatus(solutionId);
        // TODO: test validations
    }

    /**
     * Получение истории основного разговора AI-решения
     *
     * Чтобы получить историю основного разговора решения, отправьте GET-запрос на &#x60;/api/v1/cloud-ai/solutions/{solution_id}/chat/messages&#x60;.  ID основного разговора знать не нужно; для других разговоров используйте метод с явным &#x60;conversation_id&#x60;. Сообщения отсортированы по дате создания от новых к старым: первая страница — последние сообщения. Тело ответа будет представлять собой объект JSON с ключами &#x60;ai_solution_chat_messages&#x60; и &#x60;meta&#x60; (&#x60;meta.total&#x60; — общее количество сообщений).
     *
     * @throws ApiException if the Api call fails
     */
    @Test
    public void getAiSolutionMainChatHistoryTest() throws ApiException {
        Integer solutionId = null;
        Integer limit = null;
        Integer offset = null;
        GetAiSolutionMainChatHistory200Response response = api.getAiSolutionMainChatHistory(solutionId, limit, offset);
        // TODO: test validations
    }

    /**
     * Получение списка разговоров AI-решения
     *
     * Чтобы получить список разговоров с агентом решения, отправьте GET-запрос на &#x60;/api/v1/cloud-ai/solutions/{solution_id}/chat/conversations&#x60;.  Возвращает все разговоры решения, в которых есть сообщения, включая основной, отсортированные по дате последнего сообщения от новых к старым. Тело ответа будет представлять собой объект JSON с ключами &#x60;ai_solution_conversations&#x60; и &#x60;meta&#x60;.
     *
     * @throws ApiException if the Api call fails
     */
    @Test
    public void listAiSolutionConversationsTest() throws ApiException {
        Integer solutionId = null;
        ListAiSolutionConversations200Response response = api.listAiSolutionConversations(solutionId);
        // TODO: test validations
    }

    /**
     * Получение списка Telegram-ботов AI-решения
     *
     * Чтобы получить список Telegram-ботов решения, отправьте GET-запрос на &#x60;/api/v1/cloud-ai/solutions/{solution_id}/telegram/bots&#x60;.  Возвращает подключённых к решению ботов и состояние их подтверждения. Список читается с сервера решения. Тело ответа будет представлять собой объект JSON с ключами &#x60;ai_solution_telegram_bots&#x60; и &#x60;meta&#x60;.  Перед вызовом убедитесь, что чат готов (&#x60;ready: true&#x60; в методе получения статуса чата): пока сервер решения разворачивается или выключен, запрос завершается ошибкой.
     *
     * @throws ApiException if the Api call fails
     */
    @Test
    public void listAiSolutionTelegramBotsTest() throws ApiException {
        Integer solutionId = null;
        ListAiSolutionTelegramBots200Response response = api.listAiSolutionTelegramBots(solutionId);
        // TODO: test validations
    }

    /**
     * Получение списка типов AI-решений
     *
     * Чтобы получить список типов AI-решений, отправьте GET-запрос на &#x60;/api/v1/cloud-ai/solutions/types&#x60;.  Возвращает каталог готовых решений, которые можно развернуть на облачном сервере, с минимальными требованиями к конфигурации сервера (&#x60;requirements&#x60;). Тело ответа будет представлять собой объект JSON с ключами &#x60;ai_solution_types&#x60; и &#x60;meta&#x60;.
     *
     * @throws ApiException if the Api call fails
     */
    @Test
    public void listAiSolutionTypesTest() throws ApiException {
        ListAiSolutionTypes200Response response = api.listAiSolutionTypes();
        // TODO: test validations
    }

    /**
     * Получение списка AI-решений
     *
     * Чтобы получить список AI-решений аккаунта, отправьте GET-запрос на &#x60;/api/v1/cloud-ai/solutions&#x60;.  Возвращает все решения аккаунта, кроме удалённых. Статус каждого решения выводится из текущего состояния его сервера. Тело ответа будет представлять собой объект JSON с ключами &#x60;ai_solutions&#x60; и &#x60;meta&#x60;.
     *
     * @throws ApiException if the Api call fails
     */
    @Test
    public void listAiSolutionsTest() throws ApiException {
        ListAiSolutions200Response response = api.listAiSolutions();
        // TODO: test validations
    }

    /**
     * Перевыпуск ключа AI Gateway решения
     *
     * Чтобы перевыпустить ключ AI Gateway решения, отправьте POST-запрос на &#x60;/api/v1/cloud-ai/solutions/{solution_id}/ai-key/regenerate&#x60;.  Единственный способ сменить этот ключ: он выпущен платформой для решения и в разделе AI Gateway доступен только для чтения. Новый ключ сразу записывается в конфигурацию агента на сервере решения, старый перестаёт работать немедленно. Сервер должен быть включён и полностью развёрнут — это проверяется до перевыпуска, потому что отменить его нельзя. Если записать новый ключ на сервер не удалось, повторите запрос.
     *
     * @throws ApiException if the Api call fails
     */
    @Test
    public void regenerateAiSolutionAiKeyTest() throws ApiException {
        Integer solutionId = null;
        RegenerateAiSolutionAiKey200Response response = api.regenerateAiSolutionAiKey(solutionId);
        // TODO: test validations
    }

    /**
     * Отключение Telegram-бота от AI-решения
     *
     * Чтобы отключить Telegram-бота от решения, отправьте DELETE-запрос на &#x60;/api/v1/cloud-ai/solutions/{solution_id}/telegram/bots/{account_key}&#x60;.  Удаляет бота из конфигурации сервера решения: агент перестаёт отвечать в этом боте. Если бот не подключён, запрос всё равно возвращает 204. Некорректный &#x60;account_key&#x60; — ответ 400 &#x60;ai_solution_telegram_token_is_invalid&#x60;.  Перед вызовом убедитесь, что чат готов (&#x60;ready: true&#x60; в методе получения статуса чата): пока сервер решения разворачивается или выключен, запрос завершается ошибкой.
     *
     * @throws ApiException if the Api call fails
     */
    @Test
    public void removeAiSolutionTelegramBotTest() throws ApiException {
        Integer solutionId = null;
        String accountKey = null;
        api.removeAiSolutionTelegramBot(solutionId, accountKey);
        // TODO: test validations
    }

    /**
     * Сброс контекста разговора AI-решения
     *
     * Чтобы сбросить контекст разговора, отправьте POST-запрос на &#x60;/api/v1/cloud-ai/solutions/{solution_id}/chat/reset&#x60;.  Контекст сбрасывается на месте: разговор сохраняет &#x60;conversation_id&#x60;, но агент больше не видит предыдущих сообщений. Без &#x60;conversation_id&#x60; сбрасывается основной разговор решения. В историю добавляется служебное сообщение с ролью &#x60;context-reset&#x60;, отмечающее границу текущего контекста, — оно и возвращается в ответе (с ключом &#x60;ai_solution_chat_message&#x60;). Отметка добавляется и для разговора, в котором ещё не было сообщений.  Перед вызовом убедитесь, что чат готов (&#x60;ready: true&#x60; в методе получения статуса чата): пока сервер решения разворачивается или выключен, запрос завершается ошибкой.
     *
     * @throws ApiException if the Api call fails
     */
    @Test
    public void resetAiSolutionChatTest() throws ApiException {
        Integer solutionId = null;
        ResetAiSolutionChat resetAiSolutionChat = null;
        ResetAiSolutionChat200Response response = api.resetAiSolutionChat(solutionId, resetAiSolutionChat);
        // TODO: test validations
    }

    /**
     * Отправка сообщения агенту AI-решения
     *
     * Чтобы отправить сообщение агенту решения, отправьте POST-запрос на &#x60;/api/v1/cloud-ai/solutions/{solution_id}/chat/messages&#x60;.  Ответ возвращается целиком после завершения хода агента (ожидание — до 3 минут). Без &#x60;conversation_id&#x60; сообщение уходит в основной разговор решения; новый разговор начинается только при явной передаче нового ID. Без &#x60;message&#x60; агент сам здоровается — так можно показать первую реплику чата. Чтобы получать ответ по мере генерации, используйте потоковый метод.  Перед вызовом убедитесь, что чат готов (&#x60;ready: true&#x60; в методе получения статуса чата): пока сервер решения разворачивается или выключен, запрос завершается ошибкой.
     *
     * @throws ApiException if the Api call fails
     */
    @Test
    public void sendAiSolutionMessageTest() throws ApiException {
        Integer solutionId = null;
        SendAiSolutionMessage sendAiSolutionMessage = null;
        SendAiSolutionMessage200Response response = api.sendAiSolutionMessage(solutionId, sendAiSolutionMessage);
        // TODO: test validations
    }

    /**
     * Выключение AI-решения
     *
     * Чтобы выключить AI-решение, отправьте POST-запрос на &#x60;/api/v1/cloud-ai/solutions/{solution_id}/shutdown&#x60;.  Выключает сервер решения. Действие асинхронное: следите за результатом по статусу решения (обычно 1–2 минуты до &#x60;stopped&#x60;). Пока сервер выключен, чат недоступен, а сервер продолжает тарифицироваться. Выключение уже выключенного решения возвращает 204. Пока над сервером выполняется другое действие, ответ — 409 &#x60;ai_solution_is_busy&#x60;.
     *
     * @throws ApiException if the Api call fails
     */
    @Test
    public void shutdownAiSolutionTest() throws ApiException {
        Integer solutionId = null;
        api.shutdownAiSolution(solutionId);
        // TODO: test validations
    }

    /**
     * Запуск AI-решения
     *
     * Чтобы запустить AI-решение, отправьте POST-запрос на &#x60;/api/v1/cloud-ai/solutions/{solution_id}/start&#x60;.  Включает сервер решения. Действие асинхронное: следите за результатом по статусу решения, а готовность агента проверяйте методом получения статуса чата. Пока над сервером выполняется действие, запуск и выключение возвращают 409 &#x60;ai_solution_is_busy&#x60;.  Не вызывайте метод для работающего решения: запрос будет принят (204) и запустит действие над сервером, а решение на несколько минут станет занятым.
     *
     * @throws ApiException if the Api call fails
     */
    @Test
    public void startAiSolutionTest() throws ApiException {
        Integer solutionId = null;
        api.startAiSolution(solutionId);
        // TODO: test validations
    }

    /**
     * Остановка хода агента AI-решения
     *
     * Чтобы остановить текущий ход агента, отправьте POST-запрос на &#x60;/api/v1/cloud-ai/solutions/{solution_id}/chat/stop&#x60;.  Прерывает генерацию ответа — например, если ответ зациклился или тратит слишком много токенов. Без &#x60;conversation_id&#x60; останавливается текущий ход основного разговора решения. Ход прерывается на стороне агента: закрыть соединение недостаточно, потому что принятый агентом ход продолжает выполняться независимо от клиента. Если хода нет, запрос всё равно возвращает 204.  Перед вызовом убедитесь, что чат готов (&#x60;ready: true&#x60; в методе получения статуса чата): пока сервер решения разворачивается или выключен, запрос завершается ошибкой.
     *
     * @throws ApiException if the Api call fails
     */
    @Test
    public void stopAiSolutionChatTest() throws ApiException {
        Integer solutionId = null;
        StopAiSolutionChat stopAiSolutionChat = null;
        api.stopAiSolutionChat(solutionId, stopAiSolutionChat);
        // TODO: test validations
    }

    /**
     * Отправка сообщения агенту AI-решения с потоковым ответом
     *
     * Чтобы отправить сообщение агенту решения и получать ответ потоком, отправьте POST-запрос на &#x60;https://agent.timeweb.cloud/api/v1/cloud-ai/solutions/{solution_id}/chat/stream&#x60;.  Метод работает на &#x60;https://agent.timeweb.cloud&#x60;, а не на &#x60;https://api.timeweb.cloud&#x60;: шлюз публичного API накапливает ответ целиком и не может передавать его по частям. Авторизация та же — API-токен в заголовке &#x60;Authorization&#x60;; токену с ограниченными правами нужен доступ на запись к AI-сервисам. Тело запроса такое же, как у метода отправки сообщения.  Ответ — &#x60;text/event-stream&#x60;: каждое событие приходит как &#x60;event: &lt;тип&gt;&#x60; и &#x60;data: &lt;JSON&gt;&#x60;. Отправка сообщения и подписка на ответ — один запрос, поэтому начало ответа не теряется. Строки, начинающиеся с &#x60;:&#x60;, — комментарии для поддержания соединения (каждые 15 секунд), их нужно игнорировать. ID разговора возвращается в заголовке ответа &#x60;X-Conversation-Id&#x60;.  Типы событий: - &#x60;subscribed&#x60; &#x60;{}&#x60; — ход принят; - &#x60;delta&#x60; &#x60;{text, seq, runId}&#x60; — очередной фрагмент ответа; - &#x60;tool&#x60; &#x60;{name, target, phase, status, seq, runId}&#x60; — агент использует инструмент; - &#x60;message&#x60; &#x60;{text, seq, id, stopReason}&#x60; — финальный ответ целиком, всегда после последнего &#x60;delta&#x60;; - &#x60;done&#x60; &#x60;{phase, seq, runId}&#x60; — ход завершён; - &#x60;error&#x60; &#x60;{reason, message, budget?}&#x60; — ход не удался. &#x60;reason&#x60;: &#x60;empty_reply&#x60; — агент не выдал текста; &#x60;stream_failed&#x60; — поток прервался; &#x60;agent_failed&#x60; — ход упал на стороне агента при исправном соединении, повтор имеет смысл; &#x60;budget_exceeded&#x60; — исчерпан лимит расходов ключа AI Gateway решения, в &#x60;budget&#x60; &#x60;{max, spent, resetAt}&#x60; — цифры лимита, если они известны. &#x60;error&#x60; всегда последнее событие потока и может прийти после &#x60;done&#x60; (например, с &#x60;empty_reply&#x60;).  Ошибки, возникшие до начала потока (решение не найдено, некорректный запрос, нет доступа), возвращаются обычным JSON-ответом с соответствующим HTTP-статусом. Если сервер решения выключен или недоступен, поток может открыться и сразу завершиться событием &#x60;error&#x60; с &#x60;reason: stream_failed&#x60; — проверяйте готовность чата заранее методом получения статуса чата.
     *
     * @throws ApiException if the Api call fails
     */
    @Test
    public void streamAiSolutionChatTest() throws ApiException {
        Integer solutionId = null;
        SendAiSolutionMessage sendAiSolutionMessage = null;
        String response = api.streamAiSolutionChat(solutionId, sendAiSolutionMessage);
        // TODO: test validations
    }

    /**
     * Изменение AI-решения
     *
     * Чтобы изменить AI-решение, отправьте PATCH-запрос на &#x60;/api/v1/cloud-ai/solutions/{solution_id}&#x60;.  Меняются только переданные поля; &#x60;description: null&#x60; очищает описание. Смена модели переписывает конфигурацию агента на сервере решения и перезапускает агента, поэтому сервер должен быть включён и полностью развёрнут — иначе запрос завершится ошибкой и ничего не изменится. Смена только названия или описания сервер не затрагивает. Неподходящая модель — 404 &#x60;model_not_found&#x60;.
     *
     * @throws ApiException if the Api call fails
     */
    @Test
    public void updateAiSolutionTest() throws ApiException {
        Integer solutionId = null;
        UpdateAiSolution updateAiSolution = null;
        CreateAiSolution201Response response = api.updateAiSolution(solutionId, updateAiSolution);
        // TODO: test validations
    }

}
