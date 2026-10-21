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
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * API tests for AiAgentSkillsApi
 */
@Disabled
public class AiAgentSkillsApiTest {

    private final AiAgentSkillsApi api = new AiAgentSkillsApi();

    /**
     * Применение обновления навыка из каталога
     *
     * Чтобы применить к установленному навыку новую версию из каталога, отправьте POST-запрос на &#x60;/api/v1/cloud-ai/skills/{skill_id}/apply-catalog-update&#x60;.  Текст «когда применять» и инструкция заменяются каталожными, при этом создаётся новая версия: прежний текст, в том числе ваши правки, остаётся в истории. Название навыка не меняется. Если обновления нет, ничего не меняется (&#x60;meta.version_created&#x60; — &#x60;false&#x60;).  Для навыка, установленного не из каталога, вернётся 409 с &#x60;error_code&#x60; &#x60;skill_is_not_installed_from_catalog&#x60;.
     *
     * @throws ApiException if the Api call fails
     */
    @Test
    public void applyAgentSkillCatalogUpdateTest() throws ApiException {
        Integer skillId = null;
        CreateAgentSkill201Response response = api.applyAgentSkillCatalogUpdate(skillId);
        // TODO: test validations
    }

    /**
     * Подключение нескольких навыков к агенту
     *
     * Чтобы подключить и сразу включить у агента несколько навыков, отправьте POST-запрос на &#x60;/api/v1/cloud-ai/agents/{agent_id}/skills/connect&#x60;.  Навыки, которых нет в аккаунте, пропускаются без ошибки. Уже подключённые навыки включаются; если для них передана версия, агент закрепляется на ней. В ответе — только навыки, которые удалось подключить.
     *
     * @throws ApiException if the Api call fails
     */
    @Test
    public void connectAgentSkillsToAgentTest() throws ApiException {
        Integer agentId = null;
        ConnectSkills connectSkills = null;
        GetAgentSkillsForAgent200Response response = api.connectAgentSkillsToAgent(agentId, connectSkills);
        // TODO: test validations
    }

    /**
     * Создание навыка
     *
     * Чтобы создать навык, отправьте POST-запрос на &#x60;/api/v1/cloud-ai/skills&#x60;.  Навык создаётся с версией 1 и ни к одному агенту не подключается. Предупреждения о качестве текста приходят в &#x60;meta.warnings&#x60; и не мешают сохранению. Скрипты передаются в теле запроса. Их общий размер — до 10 МБ, а всё тело запроса — до 16 МБ; запрос с телом больше отклоняется до проверки.  Если навык с таким названием уже есть, вернётся 409 с &#x60;error_code&#x60; &#x60;skill_name_already_exists&#x60;. Если скрипты не прошли проверку (больше 20 файлов, повторяющиеся имена, общий размер больше 10 МБ, файл без содержимого), вернётся 400 с &#x60;error_code&#x60; &#x60;skill_scripts_invalid&#x60;.
     *
     * @throws ApiException if the Api call fails
     */
    @Test
    public void createAgentSkillTest() throws ApiException {
        CreateSkill createSkill = null;
        CreateAgentSkill201Response response = api.createAgentSkill(createSkill);
        // TODO: test validations
    }

    /**
     * Удаление навыка
     *
     * Чтобы удалить навык, отправьте DELETE-запрос на &#x60;/api/v1/cloud-ai/skills/{skill_id}&#x60;.  Навык отключается от всех агентов, история версий и скрипты удаляются. Срабатывания навыка под старыми ответами агентов сохраняются.
     *
     * @throws ApiException if the Api call fails
     */
    @Test
    public void deleteAgentSkillTest() throws ApiException {
        Integer skillId = null;
        api.deleteAgentSkill(skillId);
        // TODO: test validations
    }

    /**
     * Отключение навыка от агента
     *
     * Чтобы полностью отключить навык от агента, отправьте DELETE-запрос на &#x60;/api/v1/cloud-ai/agents/{agent_id}/skills/{skill_id}&#x60;.  Закреплённая версия при этом не сохраняется. Чтобы выключить навык на время, используйте PUT-запрос с &#x60;is_enabled: false&#x60;. Если навык не подключён к агенту, вернётся 404 с &#x60;error_code&#x60; &#x60;agent_skill_link_not_found&#x60;.
     *
     * @throws ApiException if the Api call fails
     */
    @Test
    public void disconnectAgentSkillFromAgentTest() throws ApiException {
        Integer agentId = null;
        Integer skillId = null;
        api.disconnectAgentSkillFromAgent(agentId, skillId);
        // TODO: test validations
    }

    /**
     * Копирование навыка
     *
     * Чтобы создать копию навыка, отправьте POST-запрос на &#x60;/api/v1/cloud-ai/skills/{skill_id}/duplicate&#x60;.  Копия получает свободное название вида «Название (копия)» или «Название (копия 2)», текст и скрипты текущей версии оригинала и собственную историю с версии 1. К агентам копия не подключается. Копия навыка из каталога становится обычным навыком аккаунта.
     *
     * @throws ApiException if the Api call fails
     */
    @Test
    public void duplicateAgentSkillTest() throws ApiException {
        Integer skillId = null;
        CreateAgentSkill201Response response = api.duplicateAgentSkill(skillId);
        // TODO: test validations
    }

    /**
     * Получение навыка
     *
     * Чтобы получить карточку навыка, отправьте GET-запрос на &#x60;/api/v1/cloud-ai/skills/{skill_id}&#x60;.  В карточке есть скрипты текущей версии (без содержимого), счётчик версий и агенты, к которым подключён навык.
     *
     * @throws ApiException if the Api call fails
     */
    @Test
    public void getAgentSkillTest() throws ApiException {
        Integer skillId = null;
        GetAgentSkill200Response response = api.getAgentSkill(skillId);
        // TODO: test validations
    }

    /**
     * Получение каталога навыков
     *
     * Чтобы получить каталог готовых навыков Timeweb Cloud, отправьте GET-запрос на &#x60;/api/v1/cloud-ai/skill-catalog&#x60;.  Навыки приходят в порядке показа, сгруппированные по &#x60;category&#x60;. Инструкция возвращается полностью, чтобы её можно было прочитать до установки. Для установленных навыков заполнены &#x60;is_installed&#x60;, &#x60;installed_skill_id&#x60;, &#x60;is_modified&#x60; и &#x60;has_catalog_update&#x60;.
     *
     * @throws ApiException if the Api call fails
     */
    @Test
    public void getAgentSkillCatalogTest() throws ApiException {
        GetAgentSkillCatalog200Response response = api.getAgentSkillCatalog();
        // TODO: test validations
    }

    /**
     * Получение списка навыков
     *
     * Чтобы получить навыки аккаунта, отправьте GET-запрос на &#x60;/api/v1/cloud-ai/skills&#x60;.  Сначала идут недавно изменённые. Параметр &#x60;search&#x60; ищет по названию и по тексту инструкции.
     *
     * @throws ApiException if the Api call fails
     */
    @Test
    public void getAgentSkillLibraryTest() throws ApiException {
        String search = null;
        GetAgentSkillLibrary200Response response = api.getAgentSkillLibrary(search);
        // TODO: test validations
    }

    /**
     * Получение версии навыка
     *
     * Чтобы получить текст конкретной версии навыка, отправьте GET-запрос на &#x60;/api/v1/cloud-ai/skills/{skill_id}/versions/{version}&#x60;.  Номер версии сработавшего навыка есть в срабатываниях под ответами агента.
     *
     * @throws ApiException if the Api call fails
     */
    @Test
    public void getAgentSkillVersionTest() throws ApiException {
        Integer skillId = null;
        Integer version = null;
        GetAgentSkillVersion200Response response = api.getAgentSkillVersion(skillId, version);
        // TODO: test validations
    }

    /**
     * Получение истории версий навыка
     *
     * Чтобы получить историю версий навыка, отправьте GET-запрос на &#x60;/api/v1/cloud-ai/skills/{skill_id}/versions&#x60;.
     *
     * @throws ApiException if the Api call fails
     */
    @Test
    public void getAgentSkillVersionsTest() throws ApiException {
        Integer skillId = null;
        GetAgentSkillVersions200Response response = api.getAgentSkillVersions(skillId);
        // TODO: test validations
    }

    /**
     * Получение навыков агента
     *
     * Чтобы получить навыки агента, отправьте GET-запрос на &#x60;/api/v1/cloud-ai/agents/{agent_id}/skills&#x60;.  В ответе все навыки аккаунта: неподключённые приходят с &#x60;is_connected: false&#x60;. &#x60;has_newer_version: true&#x60; означает, что агент закреплён на устаревшей версии.
     *
     * @throws ApiException if the Api call fails
     */
    @Test
    public void getAgentSkillsForAgentTest() throws ApiException {
        Integer agentId = null;
        GetAgentSkillsForAgent200Response response = api.getAgentSkillsForAgent(agentId);
        // TODO: test validations
    }

    /**
     * Установка навыка из каталога
     *
     * Чтобы установить навык из каталога в библиотеку аккаунта, отправьте POST-запрос на &#x60;/api/v1/cloud-ai/skill-catalog/{catalog_item_id}/install&#x60;.  Повторная установка не создаёт копию, а возвращает уже установленный навык с &#x60;meta.already_installed: true&#x60;. Если название занято вашим навыком, к нему добавляется «(копия)». Чтобы сразу включить навык у агента, используйте &#x60;POST /api/v1/cloud-ai/agents/{agent_id}/skills/install-and-connect&#x60;.
     *
     * @throws ApiException if the Api call fails
     */
    @Test
    public void installAgentSkillFromCatalogTest() throws ApiException {
        Integer catalogItemId = null;
        InstallAgentSkillFromCatalog200Response response = api.installAgentSkillFromCatalog(catalogItemId);
        // TODO: test validations
    }

    /**
     * Установка навыка из каталога и включение у агента
     *
     * Чтобы установить навык из каталога и сразу включить его у агента, отправьте POST-запрос на &#x60;/api/v1/cloud-ai/agents/{agent_id}/skills/install-and-connect&#x60;.  Если навык уже установлен, копия не создаётся (&#x60;meta.already_installed: true&#x60;), навык просто включается у агента.
     *
     * @throws ApiException if the Api call fails
     */
    @Test
    public void installAndConnectAgentSkillTest() throws ApiException {
        Integer agentId = null;
        InstallAndConnectSkill installAndConnectSkill = null;
        InstallAndConnectAgentSkill200Response response = api.installAndConnectAgentSkill(agentId, installAndConnectSkill);
        // TODO: test validations
    }

    /**
     * Переключение навыка на версию из истории
     *
     * Чтобы сделать версию из истории текущей, отправьте POST-запрос на &#x60;/api/v1/cloud-ai/skills/{skill_id}/versions/{version}/rollback&#x60;.  Новая версия не создаётся (&#x60;meta.version_created&#x60; — &#x60;false&#x60;), название навыка не меняется. Агенты остаются на своих закреплённых версиях. Если выбранная версия уже текущая, ничего не меняется.
     *
     * @throws ApiException if the Api call fails
     */
    @Test
    public void rollbackAgentSkillVersionTest() throws ApiException {
        Integer skillId = null;
        Integer version = null;
        CreateAgentSkill201Response response = api.rollbackAgentSkillVersion(skillId, version);
        // TODO: test validations
    }

    /**
     * Включение и выключение навыка у агента
     *
     * Чтобы включить или выключить навык у агента, отправьте PUT-запрос на &#x60;/api/v1/cloud-ai/agents/{agent_id}/skills/{skill_id}&#x60;.  Первое включение подключает навык и закрепляет агента на текущей версии навыка или на версии из &#x60;version&#x60;. Выключение сохраняет закреплённую версию, поэтому повторное включение вернёт то же поведение. Изменения действуют сразу. Выключение неподключённого навыка ничего не меняет.
     *
     * @throws ApiException if the Api call fails
     */
    @Test
    public void setAgentSkillForAgentTest() throws ApiException {
        Integer agentId = null;
        Integer skillId = null;
        SetAgentSkill setAgentSkill = null;
        SetAgentSkillForAgent200Response response = api.setAgentSkillForAgent(agentId, skillId, setAgentSkill);
        // TODO: test validations
    }

    /**
     * Перевод всех агентов навыка на текущую версию
     *
     * Чтобы перевести всех агентов, у которых включён навык, на его текущую версию, отправьте POST-запрос на &#x60;/api/v1/cloud-ai/skills/{skill_id}/agents/sync-versions&#x60;.  Агенты, у которых навык выключен, остаются на своих версиях. Сколько агентов переведено, видно в &#x60;meta.updated_count&#x60;.
     *
     * @throws ApiException if the Api call fails
     */
    @Test
    public void syncAgentSkillAgentsVersionsTest() throws ApiException {
        Integer skillId = null;
        SyncAgentSkillAgentsVersions200Response response = api.syncAgentSkillAgentsVersions(skillId);
        // TODO: test validations
    }

    /**
     * Перевод всех навыков агента на текущие версии
     *
     * Чтобы перевести все включённые у агента навыки на их текущие версии, отправьте POST-запрос на &#x60;/api/v1/cloud-ai/agents/{agent_id}/skills/sync-versions&#x60;.  В ответе все навыки аккаунта с состоянием у агента, как в списке навыков агента. Сколько навыков переведено, видно в &#x60;updated_count&#x60; в корне ответа.
     *
     * @throws ApiException if the Api call fails
     */
    @Test
    public void syncAgentSkillVersionsForAgentTest() throws ApiException {
        Integer agentId = null;
        SyncAgentSkillVersionsForAgent200Response response = api.syncAgentSkillVersionsForAgent(agentId);
        // TODO: test validations
    }

    /**
     * Изменение навыка
     *
     * Чтобы изменить навык, отправьте PATCH-запрос на &#x60;/api/v1/cloud-ai/skills/{skill_id}&#x60;, передав только изменяемые поля.  Изменение «когда применять», инструкции или скриптов создаёт новую версию. Переименование и сохранение без изменений версию не создают (&#x60;meta.version_created&#x60;). Подключённые агенты остаются на своих версиях. Сколько агентов затронуто, видно в &#x60;meta.affected_agents_count&#x60;. Если в истории уже 50 версий, самая старая свободная версия удаляется (&#x60;meta.pruned_versions&#x60;). Скрипты передаются в теле запроса. Их общий размер — до 10 МБ, а всё тело запроса — до 16 МБ; запрос с телом больше отклоняется до проверки.  Возможные ошибки 409: &#x60;skill_name_already_exists&#x60; — название занято; &#x60;skill_versions_limit_exceeded&#x60; — все 50 версий заняты текущей и закреплёнными за агентами; &#x60;skill_was_modified_concurrently&#x60; — навык одновременно изменён другим запросом, повторите сохранение.
     *
     * @throws ApiException if the Api call fails
     */
    @Test
    public void updateAgentSkillTest() throws ApiException {
        Integer skillId = null;
        UpdateSkill updateSkill = null;
        CreateAgentSkill201Response response = api.updateAgentSkill(skillId, updateSkill);
        // TODO: test validations
    }

    /**
     * Перевод агента на текущую версию навыка
     *
     * Чтобы перевести агента на текущую версию навыка, отправьте POST-запрос на &#x60;/api/v1/cloud-ai/agents/{agent_id}/skills/{skill_id}/update-version&#x60;.  Если навык не подключён к агенту, вернётся 404 с &#x60;error_code&#x60; &#x60;agent_skill_link_not_found&#x60;.
     *
     * @throws ApiException if the Api call fails
     */
    @Test
    public void updateAgentSkillVersionForAgentTest() throws ApiException {
        Integer agentId = null;
        Integer skillId = null;
        SetAgentSkillForAgent200Response response = api.updateAgentSkillVersionForAgent(agentId, skillId);
        // TODO: test validations
    }

}
