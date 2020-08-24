# Currency converter backend
This currency converter backend uses api from Lietuvos Bankas for real time FX rates.

### Launch service
```mvn spring-boot:run```

## How to use api


### HTTP GET:

Convert EUR to USD
```
http://localhost:8080/api/v1/CurrencyExchange?currency1=AUD&amount=20&currency2=USD
```
Result
```
{
    "id": 1,
    "currency1": "AUD",
    "currency2": "USD",
    "amount": 20,
    "exchangeRate": 0.72,
    "result": 14.40
}
```
All currency conversion rates according to EUR
```
http://localhost:8080/api/v1/CurrencyExchange/all
```
Backend log
```
http://localhost:8080/api/v1/CurrencyExchange/log
```

