

# ClusterIn


## Properties

| Name | Type | Description | Notes |
|------------ | ------------- | ------------- | -------------|
|**name** | **String** | Название кластера |  |
|**description** | **String** | Описание кластера |  [optional] |
|**k8sVersion** | **String** | Версия Kubernetes |  |
|**availabilityZone** | [**AvailabilityZoneEnum**](#AvailabilityZoneEnum) | Зона доступности |  [optional] |
|**networkDriver** | [**NetworkDriverEnum**](#NetworkDriverEnum) | Тип используемого сетевого драйвера в кластере |  |
|**isIngress** | **Boolean** | Логическое значение, которое показывает, использовать ли Ingress в кластере |  [optional] |
|**isK8sDashboard** | **Boolean** | Логическое значение, которое показывает, использовать ли Kubernetes Dashboard в кластере |  [optional] |
|**presetId** | **Integer** | ID тарифа мастер-ноды |  |
|**workerGroups** | [**List&lt;NodeGroupIn&gt;**](NodeGroupIn.md) | Группы воркеров в кластере |  [optional] |
|**networkId** | **String** | ID приватной сети |  [optional] |
|**projectId** | **Integer** | ID проекта |  [optional] |



## Enum: AvailabilityZoneEnum

| Name | Value |
|---- | -----|
| SPB_3 | &quot;spb-3&quot; |
| MSK_1 | &quot;msk-1&quot; |
| AMS_1 | &quot;ams-1&quot; |



## Enum: NetworkDriverEnum

| Name | Value |
|---- | -----|
| KUBEROUTER | &quot;kuberouter&quot; |
| CALICO | &quot;calico&quot; |
| FLANNEL | &quot;flannel&quot; |
| CILIUM | &quot;cilium&quot; |



