/*
 * Timeweb Cloud API
 * # Введение API Timeweb Cloud позволяет вам управлять ресурсами в облаке программным способом с использованием обычных HTTP-запросов.  Множество функций, которые доступны в панели управления Timeweb Cloud, также доступны через API, что позволяет вам автоматизировать ваши собственные сценарии.  В этой документации сперва будет описан общий дизайн и принципы работы API, а после этого конкретные конечные точки. Также будут приведены примеры запросов к ним.   ## Запросы Запросы должны выполняться по протоколу `HTTPS`, чтобы гарантировать шифрование транзакций. Поддерживаются следующие методы запроса: |Метод|Применение| |--- |--- | |GET|Извлекает данные о коллекциях и отдельных ресурсах.| |POST|Для коллекций создает новый ресурс этого типа. Также используется для выполнения действий с конкретным ресурсом.| |PUT|Обновляет существующий ресурс.| |PATCH|Некоторые ресурсы поддерживают частичное обновление, то есть обновление только части атрибутов ресурса, в этом случае вместо метода PUT будет использован PATCH.| |DELETE|Удаляет ресурс.|  Методы `POST`, `PUT` и `PATCH` могут включать объект в тело запроса с типом содержимого `application/json`.  ### Параметры в запросах Некоторые коллекции поддерживают пагинацию, поиск или сортировку в запросах. В параметрах запроса требуется передать: - `limit` — обозначает количество записей, которое необходимо вернуть  - `offset` — указывает на смещение, относительно начала списка  - `search` — позволяет указать набор символов для поиска  - `sort` — можно задать правило сортировки коллекции  ## Ответы Запросы вернут один из следующих кодов состояния ответа HTTP:  |Статус|Описание| |--- |--- | |200 OK|Действие с ресурсом было выполнено успешно.| |201 Created|Ресурс был успешно создан. При этом ресурс может быть как уже готовым к использованию, так и находиться в процессе запуска.| |204 No Content|Действие с ресурсом было выполнено успешно, и ответ не содержит дополнительной информации в теле.| |400 Bad Request|Был отправлен неверный запрос, например, в нем отсутствуют обязательные параметры и т. д. Тело ответа будет содержать дополнительную информацию об ошибке.| |401 Unauthorized|Ошибка аутентификации.| |403 Forbidden|Аутентификация прошла успешно, но недостаточно прав для выполнения действия.| |404 Not Found|Запрашиваемый ресурс не найден.| |409 Conflict|Запрос конфликтует с текущим состоянием.| |423 Locked|Ресурс из запроса заблокирован от применения к нему указанного метода.| |429 Too Many Requests|Был достигнут лимит по количеству запросов в единицу времени.| |500 Internal Server Error|При выполнении запроса произошла какая-то внутренняя ошибка. Чтобы решить эту проблему, лучше всего создать тикет в панели управления.|  ### Структура успешного ответа Все конечные точки будут возвращать данные в формате `JSON`. Ответы на `GET`-запросы будут иметь на верхнем уровне следующую структуру атрибутов:  |Название поля|Тип|Описание| |--- |--- |--- | |[entity_name]|object, object[], string[], number[], boolean|Динамическое поле, которое будет меняться в зависимости от запрашиваемого ресурса и будет содержать все атрибуты, необходимые для описания этого ресурса. Например, при запросе списка баз данных будет возвращаться поле `dbs`, а при запросе конкретного облачного сервера `server`. Для некоторых конечных точек в ответе может возвращаться сразу несколько ресурсов.| |meta|object|Опционально. Объект, который содержит вспомогательную информацию о ресурсе. Чаще всего будет встречаться при запросе коллекций и содержать поле `total`, которое будет указывать на количество элементов в коллекции.| |response_id|string|Опционально. В большинстве случаев в ответе будет содержаться уникальный идентификатор ответа в формате UUIDv4, который однозначно указывает на ваш запрос внутри нашей системы. Если вам потребуется задать вопрос нашей поддержке, приложите к вопросу этот идентификатор — так мы сможем найти ответ на него намного быстрее. Также вы можете использовать этот идентификатор, чтобы убедиться, что это новый ответ на запрос и результат не был получен из кэша.|  Пример запроса на получение списка SSH-ключей: ```     HTTP/2.0 200 OK     {       \"ssh_keys\":[           {             \"body\":\"ssh-rsa AAAAB3NzaC1sdfghjkOAsBwWhs= example@device.local\",             \"created_at\":\"2021-09-15T19:52:27Z\",             \"expired_at\":null,             \"id\":5297,             \"is_default\":false,             \"name\":\"example@device.local\",             \"used_at\":null,             \"used_by\":[]           }       ],       \"meta\":{           \"total\":1       },       \"response_id\":\"94608d15-8672-4eed-8ab6-28bd6fa3cdf7\"     } ```  ### Структура ответа с ошибкой |Название поля|Тип|Описание| |--- |--- |--- | |status_code|number|Короткий числовой идентификатор ошибки.| |error_code|string|Короткий текстовый идентификатор ошибки, который уточняет числовой идентификатор и удобен для программной обработки. Самый простой пример — это код `not_found` для ошибки 404.| |message|string, string[]|Опционально. В большинстве случаев в ответе будет содержаться человекочитаемое подробное описание ошибки или ошибок, которые помогут понять, что нужно исправить.| |response_id|string|Опционально. В большинстве случае в ответе будет содержаться уникальный идентификатор ответа в формате UUIDv4, который однозначно указывает на ваш запрос внутри нашей системы. Если вам потребуется задать вопрос нашей поддержке, приложите к вопросу этот идентификатор — так мы сможем найти ответ на него намного быстрее.|  Пример: ```     HTTP/2.0 403 Forbidden     {       \"status_code\": 403,       \"error_code\":  \"forbidden\",       \"message\":     \"You do not have access for the attempted action\",       \"response_id\": \"94608d15-8672-4eed-8ab6-28bd6fa3cdf7\"     } ```  ## Статусы ресурсов Важно учесть, что при создании большинства ресурсов внутри платформы вам будет сразу возвращен ответ от сервера со статусом `200 OK` или `201 Created` и идентификатором созданного ресурса в теле ответа, но при этом этот ресурс может быть ещё в *состоянии запуска*.  Для того чтобы понять, в каком состоянии сейчас находится ваш ресурс, мы добавили поле `status` в ответ на получение информации о ресурсе.  Список статусов будет отличаться в зависимости от типа ресурса. Увидеть поддерживаемый список статусов вы сможете в описании каждого конкретного ресурса.     ## Ограничение скорости запросов (Rate Limiting) Чтобы обеспечить стабильность для всех пользователей, Timeweb Cloud защищает API от всплесков входящего трафика, анализируя количество запросов c каждого аккаунта к каждой конечной точке.  Если ваше приложение отправляет более 20 запросов в секунду на одну конечную точку, то для этого запроса API может вернуть код состояния HTTP `429 Too Many Requests`.   ## Аутентификация Доступ к API осуществляется с помощью JWT-токена. Токенами можно управлять внутри панели управления Timeweb Cloud в разделе *API и Terraform*.  Токен необходимо передавать в заголовке каждого запроса в формате: ```   Authorization: Bearer $TIMEWEB_CLOUD_TOKEN ```  ## Формат примеров API Примеры в этой документации описаны с помощью `curl`, HTTP-клиента командной строки. На компьютерах `Linux` и `macOS` обычно по умолчанию установлен `curl`, и он доступен для загрузки на всех популярных платформах, включая `Windows`.  Каждый пример разделен на несколько строк символом `\\`, который совместим с `bash`. Типичный пример выглядит так: ```   curl -X PATCH      -H \"Content-Type: application/json\"      -H \"Authorization: Bearer $TIMEWEB_CLOUD_TOKEN\"      -d '{\"name\":\"Cute Corvus\",\"comment\":\"Development Server\"}'      \"https://api.timeweb.cloud/api/v1/dedicated/1051\" ``` - Параметр `-X` задает метод запроса. Для согласованности метод будет указан во всех примерах, даже если он явно не требуется для методов `GET`. - Строки `-H` задают требуемые HTTP-заголовки. - Примеры, для которых требуется объект JSON в теле запроса, передают требуемые данные через параметр `-d`.  Чтобы использовать приведенные примеры, не подставляя каждый раз в них свой токен, вы можете добавить токен один раз в переменные окружения в вашей консоли. Например, на `Linux` это можно сделать с помощью команды:  ``` TIMEWEB_CLOUD_TOKEN=\"token\" ```  После этого токен будет автоматически подставляться в ваши запросы.  Обратите внимание, что все значения в этой документации являются примерами. Не полагайтесь на идентификаторы операционных систем, тарифов и т.д., используемые в примерах. Используйте соответствующую конечную точку для получения значений перед созданием ресурсов.   ## Версионирование API построено согласно принципам [семантического версионирования](https://semver.org/lang/ru). Это значит, что мы гарантируем обратную совместимость всех изменений в пределах одной мажорной версии.  Мажорная версия каждой конечной точки обозначается в пути запроса, например, запрос `/api/v1/servers` указывает, что этот метод имеет версию 1.
 *
 * The version of the OpenAPI document: 
 * Contact: info@timeweb.cloud
 *
 * NOTE: This class is auto generated by OpenAPI Generator (https://openapi-generator.tech).
 * https://openapi-generator.tech
 * Do not edit the class manually.
 */


package org.openapitools.client.api;

import org.openapitools.client.ApiException;
import org.openapitools.client.model.AddServerIP201Response;
import org.openapitools.client.model.AddServerIPRequest;
import org.openapitools.client.model.AutoBackup;
import org.openapitools.client.model.CreateDatabaseBackup409Response;
import org.openapitools.client.model.CreateServer;
import org.openapitools.client.model.CreateServer201Response;
import org.openapitools.client.model.CreateServerDisk201Response;
import org.openapitools.client.model.CreateServerDiskBackup201Response;
import org.openapitools.client.model.CreateServerDiskBackupRequest;
import org.openapitools.client.model.CreateServerDiskRequest;
import org.openapitools.client.model.DeleteServer200Response;
import org.openapitools.client.model.DeleteServerIPRequest;
import org.openapitools.client.model.GetConfigurators200Response;
import org.openapitools.client.model.GetFinances400Response;
import org.openapitools.client.model.GetFinances401Response;
import org.openapitools.client.model.GetFinances403Response;
import org.openapitools.client.model.GetFinances404Response;
import org.openapitools.client.model.GetFinances429Response;
import org.openapitools.client.model.GetFinances500Response;
import org.openapitools.client.model.GetOsList200Response;
import org.openapitools.client.model.GetServerDiskAutoBackupSettings200Response;
import org.openapitools.client.model.GetServerDiskBackup200Response;
import org.openapitools.client.model.GetServerDiskBackups200Response;
import org.openapitools.client.model.GetServerDisks200Response;
import org.openapitools.client.model.GetServerIPs200Response;
import org.openapitools.client.model.GetServerLogs200Response;
import org.openapitools.client.model.GetServerStatistics200Response;
import org.openapitools.client.model.GetServers200Response;
import org.openapitools.client.model.GetServersPresets200Response;
import org.openapitools.client.model.GetSoftware200Response;
import org.openapitools.client.model.PerformActionOnBackupRequest;
import org.openapitools.client.model.PerformActionOnServerRequest;
import org.openapitools.client.model.UpdateServer;
import org.openapitools.client.model.UpdateServerDiskBackupRequest;
import org.openapitools.client.model.UpdateServerDiskRequest;
import org.openapitools.client.model.UpdateServerIPRequest;
import org.openapitools.client.model.UpdateServerNATRequest;
import org.openapitools.client.model.UpdateServerOSBootModeRequest;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * API tests for ServersApi
 */
@Disabled
public class ServersApiTest {

    private final ServersApi api = new ServersApi();

    /**
     * Добавление IP-адреса сервера
     *
     * Чтобы добавить IP-адрес сервера, отправьте POST-запрос на &#x60;/api/v1/servers/{server_id}/ips&#x60;. \\  На данный момент IPv6 доступны только для серверов с локацией &#x60;ru-1&#x60;.
     *
     * @throws ApiException if the Api call fails
     */
    @Test
    public void addServerIPTest() throws ApiException {
        Integer serverId = null;
        AddServerIPRequest addServerIPRequest = null;
        AddServerIP201Response response = api.addServerIP(serverId, addServerIPRequest);
        // TODO: test validations
    }

    /**
     * Клонирование сервера
     *
     * Чтобы клонировать сервер, отправьте POST-запрос на &#x60;/api/v1/servers/{server_id}/clone&#x60;.
     *
     * @throws ApiException if the Api call fails
     */
    @Test
    public void cloneServerTest() throws ApiException {
        Integer serverId = null;
        CreateServer201Response response = api.cloneServer(serverId);
        // TODO: test validations
    }

    /**
     * Создание сервера
     *
     * Чтобы создать сервер, отправьте POST-запрос в &#x60;api/v1/servers&#x60;, задав необходимые атрибуты. Обязательно должен присутствовать один из параметров &#x60;configuration&#x60; или &#x60;preset_id&#x60;, а также &#x60;image_id&#x60; или &#x60;os_id&#x60;.  Cервер будет создан с использованием предоставленной информации. Тело ответа будет содержать объект JSON с информацией о созданном сервере.
     *
     * @throws ApiException if the Api call fails
     */
    @Test
    public void createServerTest() throws ApiException {
        CreateServer createServer = null;
        CreateServer201Response response = api.createServer(createServer);
        // TODO: test validations
    }

    /**
     * Создание диска сервера
     *
     * Чтобы создать диск сервера, отправьте POST-запрос на &#x60;/api/v1/servers/{server_id}/disks&#x60;. Системный диск создать нельзя.
     *
     * @throws ApiException if the Api call fails
     */
    @Test
    public void createServerDiskTest() throws ApiException {
        Integer serverId = null;
        CreateServerDiskRequest createServerDiskRequest = null;
        CreateServerDisk201Response response = api.createServerDisk(serverId, createServerDiskRequest);
        // TODO: test validations
    }

    /**
     * Создание бэкапа диска сервера
     *
     * Чтобы создать бэкап диска сервера, отправьте POST-запрос на &#x60;/api/v1/servers/{server_id}/disks/{disk_id}/backups&#x60;.   Тело ответа будет представлять собой объект JSON с ключом &#x60;backup&#x60;.
     *
     * @throws ApiException if the Api call fails
     */
    @Test
    public void createServerDiskBackupTest() throws ApiException {
        Integer serverId = null;
        Integer diskId = null;
        CreateServerDiskBackupRequest createServerDiskBackupRequest = null;
        CreateServerDiskBackup201Response response = api.createServerDiskBackup(serverId, diskId, createServerDiskBackupRequest);
        // TODO: test validations
    }

    /**
     * Удаление сервера
     *
     * Чтобы удалить сервер, отправьте запрос DELETE в &#x60;/api/v1/servers/{server_id}&#x60;.\\  Обратите внимание, если на аккаунте включено удаление серверов по смс, то вернется ошибка 423.
     *
     * @throws ApiException if the Api call fails
     */
    @Test
    public void deleteServerTest() throws ApiException {
        Integer serverId = null;
        String hash = null;
        String code = null;
        DeleteServer200Response response = api.deleteServer(serverId, hash, code);
        // TODO: test validations
    }

    /**
     * Удаление диска сервера
     *
     * Чтобы удалить диск сервера, отправьте DELETE-запрос на &#x60;/api/v1/servers/{server_id}/disks/{disk_id}&#x60;. Нельзя удалять системный диск.
     *
     * @throws ApiException if the Api call fails
     */
    @Test
    public void deleteServerDiskTest() throws ApiException {
        Integer serverId = null;
        Integer diskId = null;
        api.deleteServerDisk(serverId, diskId);
        // TODO: test validations
    }

    /**
     * Удаление бэкапа диска сервера
     *
     * Чтобы удалить бэкап диска сервера, отправьте DELETE-запрос на &#x60;/api/v1/servers/{server_id}/disks/{disk_id}/backups/{backup_id}&#x60;.
     *
     * @throws ApiException if the Api call fails
     */
    @Test
    public void deleteServerDiskBackupTest() throws ApiException {
        Integer serverId = null;
        Integer diskId = null;
        Integer backupId = null;
        api.deleteServerDiskBackup(serverId, diskId, backupId);
        // TODO: test validations
    }

    /**
     * Удаление IP-адреса сервера
     *
     * Чтобы удалить IP-адрес сервера, отправьте DELETE-запрос на &#x60;/api/v1/servers/{server_id}/ips&#x60;. Нельзя удалить основной IP-адрес
     *
     * @throws ApiException if the Api call fails
     */
    @Test
    public void deleteServerIPTest() throws ApiException {
        Integer serverId = null;
        DeleteServerIPRequest deleteServerIPRequest = null;
        api.deleteServerIP(serverId, deleteServerIPRequest);
        // TODO: test validations
    }

    /**
     * Получение списка конфигураторов серверов
     *
     * Чтобы получить список всех конфигураторов серверов, отправьте GET-запрос на &#x60;/api/v1/configurator/servers&#x60;.   Тело ответа будет представлять собой объект JSON с ключом &#x60;server_configurators&#x60;.
     *
     * @throws ApiException if the Api call fails
     */
    @Test
    public void getConfiguratorsTest() throws ApiException {
        GetConfigurators200Response response = api.getConfigurators();
        // TODO: test validations
    }

    /**
     * Получение списка операционных систем
     *
     * Чтобы получить список всех операционных систем, отправьте GET-запрос на &#x60;/api/v1/os/servers&#x60;.   Тело ответа будет представлять собой объект JSON с ключом &#x60;servers_os&#x60;.
     *
     * @throws ApiException if the Api call fails
     */
    @Test
    public void getOsListTest() throws ApiException {
        GetOsList200Response response = api.getOsList();
        // TODO: test validations
    }

    /**
     * Получение сервера
     *
     * Чтобы получить сервер, отправьте запрос GET в &#x60;/api/v1/servers/{server_id}&#x60;.
     *
     * @throws ApiException if the Api call fails
     */
    @Test
    public void getServerTest() throws ApiException {
        Integer serverId = null;
        CreateServer201Response response = api.getServer(serverId);
        // TODO: test validations
    }

    /**
     * Получение диска сервера
     *
     * Чтобы получить диск сервера, отправьте GET-запрос на &#x60;/api/v1/servers/{server_id}/disks/{disk_id}&#x60;.
     *
     * @throws ApiException if the Api call fails
     */
    @Test
    public void getServerDiskTest() throws ApiException {
        Integer serverId = null;
        Integer diskId = null;
        CreateServerDisk201Response response = api.getServerDisk(serverId, diskId);
        // TODO: test validations
    }

    /**
     * Получить настройки автобэкапов диска сервера
     *
     * Чтобы полученить настройки автобэкапов диска сервера, отправьте GET-запрос на &#x60;/api/v1/servers/{server_id}/disks/{disk_id}/auto-backups&#x60;.
     *
     * @throws ApiException if the Api call fails
     */
    @Test
    public void getServerDiskAutoBackupSettingsTest() throws ApiException {
        Integer serverId = null;
        Integer diskId = null;
        GetServerDiskAutoBackupSettings200Response response = api.getServerDiskAutoBackupSettings(serverId, diskId);
        // TODO: test validations
    }

    /**
     * Получение бэкапа диска сервера
     *
     * Чтобы получить бэкап диска сервера, отправьте GET-запрос на &#x60;/api/v1/servers/{server_id}/disks/{disk_id}/backups/{backup_id}&#x60;.   Тело ответа будет представлять собой объект JSON с ключом &#x60;backup&#x60;.
     *
     * @throws ApiException if the Api call fails
     */
    @Test
    public void getServerDiskBackupTest() throws ApiException {
        Integer serverId = null;
        Integer diskId = null;
        Integer backupId = null;
        GetServerDiskBackup200Response response = api.getServerDiskBackup(serverId, diskId, backupId);
        // TODO: test validations
    }

    /**
     * Получение списка бэкапов диска сервера
     *
     * Чтобы получить список бэкапов диска сервера, отправьте GET-запрос на &#x60;/api/v1/servers/{server_id}/disks/{disk_id}/backups&#x60;.   Тело ответа будет представлять собой объект JSON с ключом &#x60;backups&#x60;.
     *
     * @throws ApiException if the Api call fails
     */
    @Test
    public void getServerDiskBackupsTest() throws ApiException {
        Integer serverId = null;
        Integer diskId = null;
        GetServerDiskBackups200Response response = api.getServerDiskBackups(serverId, diskId);
        // TODO: test validations
    }

    /**
     * Получение списка дисков сервера
     *
     * Чтобы получить список дисков сервера, отправьте GET-запрос на &#x60;/api/v1/servers/{server_id}/disks&#x60;.
     *
     * @throws ApiException if the Api call fails
     */
    @Test
    public void getServerDisksTest() throws ApiException {
        Integer serverId = null;
        GetServerDisks200Response response = api.getServerDisks(serverId);
        // TODO: test validations
    }

    /**
     * Получение списка IP-адресов сервера
     *
     * Чтобы получить список IP-адресов сервера, отправьте GET-запрос на &#x60;/api/v1/servers/{server_id}/ips&#x60;. \\  На данный момент IPv6 доступны только для локации &#x60;ru-1&#x60;.
     *
     * @throws ApiException if the Api call fails
     */
    @Test
    public void getServerIPsTest() throws ApiException {
        Integer serverId = null;
        GetServerIPs200Response response = api.getServerIPs(serverId);
        // TODO: test validations
    }

    /**
     * Получение списка логов сервера
     *
     * Чтобы получить список логов сервера, отправьте GET-запрос на &#x60;/api/v1/servers/{server_id}/logs&#x60;.
     *
     * @throws ApiException if the Api call fails
     */
    @Test
    public void getServerLogsTest() throws ApiException {
        Integer serverId = null;
        Integer limit = null;
        Integer offset = null;
        String order = null;
        GetServerLogs200Response response = api.getServerLogs(serverId, limit, offset, order);
        // TODO: test validations
    }

    /**
     * Получение статистики сервера
     *
     * Чтобы получить статистику сервера, отправьте GET-запрос на &#x60;/api/v1/servers/{server_id}/statistics&#x60;.
     *
     * @throws ApiException if the Api call fails
     */
    @Test
    public void getServerStatisticsTest() throws ApiException {
        Integer serverId = null;
        String dateFrom = null;
        String dateTo = null;
        GetServerStatistics200Response response = api.getServerStatistics(serverId, dateFrom, dateTo);
        // TODO: test validations
    }

    /**
     * Получение списка серверов
     *
     * Чтобы получить список серверов, отправьте GET-запрос на &#x60;/api/v1/servers&#x60;.   Тело ответа будет представлять собой объект JSON с ключом &#x60;servers&#x60;.
     *
     * @throws ApiException if the Api call fails
     */
    @Test
    public void getServersTest() throws ApiException {
        Integer limit = null;
        Integer offset = null;
        GetServers200Response response = api.getServers(limit, offset);
        // TODO: test validations
    }

    /**
     * Получение списка тарифов серверов
     *
     * Чтобы получить список всех тарифов серверов, отправьте GET-запрос на &#x60;/api/v1/presets/servers&#x60;.   Тело ответа будет представлять собой объект JSON с ключом &#x60;server_presets&#x60;.
     *
     * @throws ApiException if the Api call fails
     */
    @Test
    public void getServersPresetsTest() throws ApiException {
        GetServersPresets200Response response = api.getServersPresets();
        // TODO: test validations
    }

    /**
     * Получение списка ПО из маркетплейса
     *
     * Чтобы получить список ПО из маркетплейса, отправьте GET-запрос на &#x60;/api/v1/software/servers&#x60;.   Тело ответа будет представлять собой объект JSON с ключом &#x60;servers_software&#x60;.
     *
     * @throws ApiException if the Api call fails
     */
    @Test
    public void getSoftwareTest() throws ApiException {
        GetSoftware200Response response = api.getSoftware();
        // TODO: test validations
    }

    /**
     * Отмонтирование ISO образа и перезагрузка сервера
     *
     * Чтобы отмонтировать ISO образ и перезагрузить сервер, отправьте POST-запрос на &#x60;/api/v1/servers/{server_id}/image-unmount&#x60;.
     *
     * @throws ApiException if the Api call fails
     */
    @Test
    public void imageUnmountAndServerReloadTest() throws ApiException {
        Integer serverId = null;
        api.imageUnmountAndServerReload(serverId);
        // TODO: test validations
    }

    /**
     * Выполнение действия над бэкапом диска сервера
     *
     * Чтобы выполнить действие над бэкапом диска сервера, отправьте POST-запрос на &#x60;/api/v1/servers/{server_id}/disks/{disk_id}/backups/{backup_id}/action&#x60;.
     *
     * @throws ApiException if the Api call fails
     */
    @Test
    public void performActionOnBackupTest() throws ApiException {
        Integer serverId = null;
        Integer diskId = null;
        Integer backupId = null;
        PerformActionOnBackupRequest performActionOnBackupRequest = null;
        api.performActionOnBackup(serverId, diskId, backupId, performActionOnBackupRequest);
        // TODO: test validations
    }

    /**
     * Выполнение действия над сервером
     *
     * Чтобы выполнить действие над сервером, отправьте POST-запрос на &#x60;/api/v1/servers/{server_id}/action&#x60;.
     *
     * @throws ApiException if the Api call fails
     */
    @Test
    public void performActionOnServerTest() throws ApiException {
        Integer serverId = null;
        PerformActionOnServerRequest performActionOnServerRequest = null;
        api.performActionOnServer(serverId, performActionOnServerRequest);
        // TODO: test validations
    }

    /**
     * Изменение сервера
     *
     * Чтобы обновить только определенные атрибуты сервера, отправьте запрос PATCH в &#x60;/api/v1/servers/{server_id}&#x60;.
     *
     * @throws ApiException if the Api call fails
     */
    @Test
    public void updateServerTest() throws ApiException {
        Integer serverId = null;
        UpdateServer updateServer = null;
        CreateServer201Response response = api.updateServer(serverId, updateServer);
        // TODO: test validations
    }

    /**
     * Изменение параметров диска сервера
     *
     * Чтобы изменить параметры диска сервера, отправьте PATCH-запрос на &#x60;/api/v1/servers/{server_id}/disks/{disk_id}&#x60;.
     *
     * @throws ApiException if the Api call fails
     */
    @Test
    public void updateServerDiskTest() throws ApiException {
        Integer serverId = null;
        Integer diskId = null;
        UpdateServerDiskRequest updateServerDiskRequest = null;
        CreateServerDisk201Response response = api.updateServerDisk(serverId, diskId, updateServerDiskRequest);
        // TODO: test validations
    }

    /**
     * Изменение настроек автобэкапов диска сервера
     *
     * Чтобы изменить настройки автобэкапов диска сервера, отправьте PATCH-запрос на &#x60;/api/v1/servers/{server_id}/disks/{disk_id}/auto-backups&#x60;.
     *
     * @throws ApiException if the Api call fails
     */
    @Test
    public void updateServerDiskAutoBackupSettingsTest() throws ApiException {
        Integer serverId = null;
        Integer diskId = null;
        AutoBackup autoBackup = null;
        GetServerDiskAutoBackupSettings200Response response = api.updateServerDiskAutoBackupSettings(serverId, diskId, autoBackup);
        // TODO: test validations
    }

    /**
     * Изменение бэкапа диска сервера
     *
     * Чтобы изменить бэкап диска сервера, отправьте PATCH-запрос на &#x60;/api/v1/servers/{server_id}/disks/{disk_id}/backups/{backup_id}&#x60;.
     *
     * @throws ApiException if the Api call fails
     */
    @Test
    public void updateServerDiskBackupTest() throws ApiException {
        Integer serverId = null;
        Integer diskId = null;
        Integer backupId = null;
        UpdateServerDiskBackupRequest updateServerDiskBackupRequest = null;
        GetServerDiskBackup200Response response = api.updateServerDiskBackup(serverId, diskId, backupId, updateServerDiskBackupRequest);
        // TODO: test validations
    }

    /**
     * Изменение IP-адреса сервера
     *
     * Чтобы изменить IP-адрес сервера, отправьте POST-запрос на &#x60;/api/v1/servers/{server_id}/ips&#x60;.
     *
     * @throws ApiException if the Api call fails
     */
    @Test
    public void updateServerIPTest() throws ApiException {
        Integer serverId = null;
        UpdateServerIPRequest updateServerIPRequest = null;
        AddServerIP201Response response = api.updateServerIP(serverId, updateServerIPRequest);
        // TODO: test validations
    }

    /**
     * Изменение правил маршрутизации трафика сервера (NAT)
     *
     * Чтобы измененить правила маршрутизации трафика сервера (NAT), отправьте PATCH-запрос на &#x60;/api/v1/servers/{server_id}/local-networks/nat-mode&#x60;.
     *
     * @throws ApiException if the Api call fails
     */
    @Test
    public void updateServerNATTest() throws ApiException {
        Integer serverId = null;
        UpdateServerNATRequest updateServerNATRequest = null;
        api.updateServerNAT(serverId, updateServerNATRequest);
        // TODO: test validations
    }

    /**
     * Выбор типа загрузки операционной системы сервера
     *
     * Чтобы изменить тип загрузки операционной системы сервера, отправьте POST-запрос на &#x60;/api/v1/servers/{server_id}/boot-mode&#x60;. \\  После смены типа загрузки сервер будет перезапущен.
     *
     * @throws ApiException if the Api call fails
     */
    @Test
    public void updateServerOSBootModeTest() throws ApiException {
        Integer serverId = null;
        UpdateServerOSBootModeRequest updateServerOSBootModeRequest = null;
        api.updateServerOSBootMode(serverId, updateServerOSBootModeRequest);
        // TODO: test validations
    }

}
