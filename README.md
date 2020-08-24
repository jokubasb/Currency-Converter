# Currency converter backend
This currency converter backend uses api from Lietuvos Bankas for real time FX rates.

### Launch service
```mvn spring-boot:run```

## How to use api


### HTTP GET:

Convert EUR to USD
```
http://localhost:8080/api/v1/CurrencyExchange?currency1=EUR&amount=20&currency2=USD
```
Result
```
{
    "id": 1,
    "currency1": "EUR",
    "currency2": "USD",
    "exchangeRate": 1.18,
    "result": 23.60
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

