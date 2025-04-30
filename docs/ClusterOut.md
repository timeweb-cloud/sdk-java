

# ClusterOut


## Properties

| Name | Type | Description | Notes |
|------------ | ------------- | ------------- | -------------|
|**id** | **Integer** | ID кластера |  |
|**name** | **String** | Название |  |
|**createdAt** | **OffsetDateTime** | Дата и время создания кластера в формате ISO8601 |  |
|**status** | **String** | Статус |  |
|**description** | **String** | Описание |  |
|**k8sVersion** | **String** | Версия Kubernetes |  |
|**networkDriver** | [**NetworkDriverEnum**](#NetworkDriverEnum) | Используемый сетевой драйвер |  |
|**avatarLink** | **String** | Ссылка на аватар кластера. |  |
|**ingress** | **Boolean** | Логическое значение, показывающее, включен ли Ingress |  |
|**presetId** | **Integer** | ID тарифа мастер-ноды |  |
|**cpu** | **Integer** | Общее количество ядер |  [optional] |
|**ram** | **Integer** | Общее количество памяти |  [optional] |
|**disk** | **Integer** | Общее дисковое пространство |  [optional] |
|**availabilityZone** | [**AvailabilityZoneEnum**](#AvailabilityZoneEnum) | Зона доступности |  [optional] |
|**projectId** | **Integer** | ID проекта |  [optional] |



## Enum: NetworkDriverEnum

| Name | Value |
|---- | -----|
| KUBEROUTER | &quot;kuberouter&quot; |
| CALICO | &quot;calico&quot; |
| FLANNEL | &quot;flannel&quot; |
| CILIUM | &quot;cilium&quot; |



## Enum: AvailabilityZoneEnum

| Name | Value |
|---- | -----|
| SPB_3 | &quot;spb-3&quot; |
| MSK_1 | &quot;msk-1&quot; |
| AMS_1 | &quot;ams-1&quot; |



