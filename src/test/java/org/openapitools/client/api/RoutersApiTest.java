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
import org.openapitools.client.model.AvailableNetworksResponse;
import org.openapitools.client.model.AvailableStaticRoutesResponse;
import org.openapitools.client.model.DnatIn;
import org.openapitools.client.model.DnatRuleResponse;
import org.openapitools.client.model.DnatRulesResponse;
import org.openapitools.client.model.GetAccountStatus403Response;
import org.openapitools.client.model.GetFinances400Response;
import org.openapitools.client.model.GetFinances401Response;
import org.openapitools.client.model.GetFinances429Response;
import org.openapitools.client.model.GetFinances500Response;
import org.openapitools.client.model.GetImage404Response;
import org.openapitools.client.model.NatIn;
import org.openapitools.client.model.NetworkEdit;
import org.openapitools.client.model.NetworkIn;
import org.openapitools.client.model.NetworkResponse;
import org.openapitools.client.model.NetworksResponse;
import org.openapitools.client.model.RouterEdit;
import org.openapitools.client.model.RouterIn;
import org.openapitools.client.model.RouterPresetsResponse;
import org.openapitools.client.model.RouterResponse;
import org.openapitools.client.model.RouterStatisticsResponse;
import org.openapitools.client.model.RoutersResponse;
import org.openapitools.client.model.StaticRouteIn;
import org.openapitools.client.model.StaticRouteResponse;
import org.openapitools.client.model.StaticRoutesResponse;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * API tests for RoutersApi
 */
@Disabled
public class RoutersApiTest {

    private final RoutersApi api = new RoutersApi();

    /**
     * Подключение сетей к роутеру
     *
     * Чтобы подключить сети к роутеру, отправьте POST-запрос на &#x60;/api/v1/routers/{router_id}/networks&#x60;.
     *
     * @throws ApiException if the Api call fails
     */
    @Test
    public void addNetworksTest() throws ApiException {
        String routerId = null;
        NetworkIn networkIn = null;
        NetworksResponse response = api.addNetworks(routerId, networkIn);
        // TODO: test validations
    }

    /**
     * Создание роутера
     *
     * Чтобы создать роутер, отправьте POST-запрос на &#x60;/api/v1/routers&#x60;.
     *
     * @throws ApiException if the Api call fails
     */
    @Test
    public void createRouterTest() throws ApiException {
        RouterIn routerIn = null;
        RouterResponse response = api.createRouter(routerIn);
        // TODO: test validations
    }

    /**
     * Удаление правила проброса портов
     *
     * Чтобы удалить правило проброса портов (DNAT), отправьте DELETE-запрос на &#x60;/api/v1/routers/{router_id}/dnat-rules/{dnat_id}&#x60;.
     *
     * @throws ApiException if the Api call fails
     */
    @Test
    public void deleteDnatTest() throws ApiException {
        String routerId = null;
        String dnatId = null;
        api.deleteDnat(routerId, dnatId);
        // TODO: test validations
    }

    /**
     * Удаление роутера
     *
     * Чтобы удалить роутер, отправьте DELETE-запрос на &#x60;/api/v1/routers/{router_id}&#x60;.
     *
     * @throws ApiException if the Api call fails
     */
    @Test
    public void deleteRouterTest() throws ApiException {
        String routerId = null;
        api.deleteRouter(routerId);
        // TODO: test validations
    }

    /**
     * Выключение NAT для сети
     *
     * Чтобы выключить NAT для сети роутера, отправьте DELETE-запрос на &#x60;/api/v1/routers/{router_id}/networks/{network_name}/nat&#x60;.
     *
     * @throws ApiException if the Api call fails
     */
    @Test
    public void deleteRouterNatTest() throws ApiException {
        String routerId = null;
        String networkName = null;
        RouterResponse response = api.deleteRouterNat(routerId, networkName);
        // TODO: test validations
    }

    /**
     * Удаление сети из роутера
     *
     * Чтобы отключить сеть от роутера, отправьте DELETE-запрос на &#x60;/api/v1/routers/{router_id}/networks/{network_name}&#x60;.
     *
     * @throws ApiException if the Api call fails
     */
    @Test
    public void deleteRouterNetworkTest() throws ApiException {
        String routerId = null;
        String networkName = null;
        api.deleteRouterNetwork(routerId, networkName);
        // TODO: test validations
    }

    /**
     * Удаление статического маршрута
     *
     * Чтобы удалить статический маршрут, отправьте DELETE-запрос на &#x60;/api/v1/routers/{router_id}/static-routes/{static_route_id}&#x60;.
     *
     * @throws ApiException if the Api call fails
     */
    @Test
    public void deleteStaticRouteTest() throws ApiException {
        String routerId = null;
        String staticRouteId = null;
        api.deleteStaticRoute(routerId, staticRouteId);
        // TODO: test validations
    }

    /**
     * Получение доступных подсетей для статических маршрутов
     *
     * Чтобы получить список подсетей, доступных для добавления статических маршрутов, отправьте GET-запрос на &#x60;/api/v1/routers/{router_id}/static-routes/available&#x60;.
     *
     * @throws ApiException if the Api call fails
     */
    @Test
    public void getAvailableStaticRoutesTest() throws ApiException {
        String routerId = null;
        AvailableStaticRoutesResponse response = api.getAvailableStaticRoutes(routerId);
        // TODO: test validations
    }

    /**
     * Получение списка правил проброса портов
     *
     * Чтобы получить список правил проброса портов (DNAT), отправьте GET-запрос на &#x60;/api/v1/routers/{router_id}/dnat-rules&#x60;.
     *
     * @throws ApiException if the Api call fails
     */
    @Test
    public void getDnatTest() throws ApiException {
        String routerId = null;
        DnatRulesResponse response = api.getDnat(routerId);
        // TODO: test validations
    }

    /**
     * Получение правила проброса портов
     *
     * Чтобы получить информацию о правиле проброса портов (DNAT), отправьте GET-запрос на &#x60;/api/v1/routers/{router_id}/dnat-rules/{dnat_id}&#x60;.
     *
     * @throws ApiException if the Api call fails
     */
    @Test
    public void getDnatRuleTest() throws ApiException {
        String routerId = null;
        String dnatId = null;
        DnatRuleResponse response = api.getDnatRule(routerId, dnatId);
        // TODO: test validations
    }

    /**
     * Получение списка сетей роутера
     *
     * Чтобы получить список сетей роутера, отправьте GET-запрос на &#x60;/api/v1/routers/{router_id}/networks&#x60;.
     *
     * @throws ApiException if the Api call fails
     */
    @Test
    public void getNetworksTest() throws ApiException {
        String routerId = null;
        NetworksResponse response = api.getNetworks(routerId);
        // TODO: test validations
    }

    /**
     * Получение информации о роутере
     *
     * Чтобы получить информацию о роутере, отправьте GET-запрос на &#x60;/api/v1/routers/{router_id}&#x60;.
     *
     * @throws ApiException if the Api call fails
     */
    @Test
    public void getRouterTest() throws ApiException {
        String routerId = null;
        RouterResponse response = api.getRouter(routerId);
        // TODO: test validations
    }

    /**
     * Получение списка доступных сетей
     *
     * Чтобы получить список локальных сетей, доступных для подключения к роутеру, отправьте GET-запрос на &#x60;/api/v1/routers/networks/available&#x60;.
     *
     * @throws ApiException if the Api call fails
     */
    @Test
    public void getRouterAvailableNetworksTest() throws ApiException {
        AvailableNetworksResponse response = api.getRouterAvailableNetworks();
        // TODO: test validations
    }

    /**
     * Получение списка тарифов роутеров
     *
     * Чтобы получить список доступных тарифов роутеров, отправьте GET-запрос на &#x60;/api/v1/presets/routers&#x60;.
     *
     * @throws ApiException if the Api call fails
     */
    @Test
    public void getRouterPresetsTest() throws ApiException {
        RouterPresetsResponse response = api.getRouterPresets();
        // TODO: test validations
    }

    /**
     * Получение статистики роутера
     *
     * Чтобы получить статистику роутера, отправьте GET-запрос на &#x60;/api/v1/routers/{router_id}/statistics/{time_from}/{period}/{keys}&#x60;.
     *
     * @throws ApiException if the Api call fails
     */
    @Test
    public void getRouterStatisticsTest() throws ApiException {
        String routerId = null;
        String timeFrom = null;
        String period = null;
        String keys = null;
        String nodeId = null;
        RouterStatisticsResponse response = api.getRouterStatistics(routerId, timeFrom, period, keys, nodeId);
        // TODO: test validations
    }

    /**
     * Получение списка роутеров
     *
     * Чтобы получить список роутеров, отправьте GET-запрос на &#x60;/api/v1/routers&#x60;.
     *
     * @throws ApiException if the Api call fails
     */
    @Test
    public void getRoutersTest() throws ApiException {
        RoutersResponse response = api.getRouters();
        // TODO: test validations
    }

    /**
     * Получение списка статических маршрутов
     *
     * Чтобы получить список статических маршрутов роутера, отправьте GET-запрос на &#x60;/api/v1/routers/{router_id}/static-routes&#x60;.
     *
     * @throws ApiException if the Api call fails
     */
    @Test
    public void getStaticRoutesTest() throws ApiException {
        String routerId = null;
        StaticRoutesResponse response = api.getStaticRoutes(routerId);
        // TODO: test validations
    }

    /**
     * Обновление информации о сети
     *
     * Чтобы включить или выключить DHCP в сети роутера, отправьте PATCH-запрос на &#x60;/api/v1/routers/{router_id}/networks/{network_name}&#x60;.
     *
     * @throws ApiException if the Api call fails
     */
    @Test
    public void patchNetworkTest() throws ApiException {
        String routerId = null;
        String networkName = null;
        NetworkEdit networkEdit = null;
        NetworkResponse response = api.patchNetwork(routerId, networkName, networkEdit);
        // TODO: test validations
    }

    /**
     * Обновление сетей роутера
     *
     * Чтобы обновить набор сетей роутера, отправьте PATCH-запрос на &#x60;/api/v1/routers/{router_id}/networks&#x60;.
     *
     * @throws ApiException if the Api call fails
     */
    @Test
    public void patchNetworksTest() throws ApiException {
        String routerId = null;
        NetworkIn networkIn = null;
        NetworksResponse response = api.patchNetworks(routerId, networkIn);
        // TODO: test validations
    }

    /**
     * Добавление правила проброса портов
     *
     * Чтобы добавить правило проброса портов (DNAT), отправьте POST-запрос на &#x60;/api/v1/routers/{router_id}/dnat-rules&#x60;.
     *
     * @throws ApiException if the Api call fails
     */
    @Test
    public void postDnatTest() throws ApiException {
        String routerId = null;
        DnatIn dnatIn = null;
        DnatRuleResponse response = api.postDnat(routerId, dnatIn);
        // TODO: test validations
    }

    /**
     * Добавление статического маршрута
     *
     * Чтобы добавить статический маршрут, отправьте POST-запрос на &#x60;/api/v1/routers/{router_id}/static-routes&#x60;.
     *
     * @throws ApiException if the Api call fails
     */
    @Test
    public void postStaticRouteTest() throws ApiException {
        String routerId = null;
        StaticRouteIn staticRouteIn = null;
        StaticRouteResponse response = api.postStaticRoute(routerId, staticRouteIn);
        // TODO: test validations
    }

    /**
     * Обновление информации о роутере
     *
     * Чтобы обновить информацию о роутере, отправьте PATCH-запрос на &#x60;/api/v1/routers/{router_id}&#x60;.
     *
     * @throws ApiException if the Api call fails
     */
    @Test
    public void updateRouterTest() throws ApiException {
        String routerId = null;
        RouterEdit routerEdit = null;
        RouterResponse response = api.updateRouter(routerId, routerEdit);
        // TODO: test validations
    }

    /**
     * Включение NAT для сети
     *
     * Чтобы включить NAT для сети роутера, отправьте PATCH-запрос на &#x60;/api/v1/routers/{router_id}/networks/{network_name}/nat&#x60;.
     *
     * @throws ApiException if the Api call fails
     */
    @Test
    public void updateRouterNatTest() throws ApiException {
        String routerId = null;
        String networkName = null;
        NatIn natIn = null;
        RouterResponse response = api.updateRouterNat(routerId, networkName, natIn);
        // TODO: test validations
    }

}
