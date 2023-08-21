

# Rule

Firewall правило

## Properties

| Name | Type | Description | Notes |
|------------ | ------------- | ------------- | -------------|
|**id** | **String** | Идентификатор правила |  |
|**description** | **String** | Описание правила |  |
|**direction** | **FirewallRuleDirection** |  |  |
|**protocol** | **FirewallRuleProtocol** |  |  |
|**port** | **String** | Порт или диапазон портов, в случае tcp или udp |  [optional] |
|**cidr** | [**Cidr**](Cidr.md) |  |  [optional] |
|**groupId** | **String** | Идентификатор группы правил |  |



