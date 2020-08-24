package com.jokubas.lab1.controller;

import java.io.StringReader;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.net.URI;
import java.util.ArrayList;
import java.util.HashMap;

import java.util.List;
import java.util.Map;
import java.util.logging.LogRecord;

import javax.validation.Valid;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import com.jokubas.lab1.model.Currency;
import com.jokubas.lab1.model.CurrencyResponse;
import com.jokubas.lab1.repository.CurrencyRepository;
import com.jokubas.lab1.repository.LogRepository;
import com.jokubas.lab1.exception.ResourceNotFoundException;

@RestController
@RequestMapping("/api/v1")
public class CurrencyController {
    @Autowired
    private CurrencyRepository CurrencyRepository;
    @Autowired
    private LogRepository LogRepository;


    @EventListener(ApplicationReadyEvent.class)
    public void startup() throws Exception {
        initialData();
    }

    
    @GetMapping("/CurrencyExchange/all")
    public List <Currency> getAllCurrencies(){
        return CurrencyRepository.findAll();
    }

    @GetMapping("/CurrencyExchange/log")
    public List <CurrencyResponse> getLog(){
        return LogRepository.findAll();
    }
    

    //get
    @GetMapping("/CurrencyExchange/{currency}")
    public ResponseEntity <Currency> getCurrencyByName(@PathVariable(value = "currency") String CurrencyName) throws ResourceNotFoundException{
        Currency Currency = CurrencyRepository.findCurrencyByName(CurrencyName);
        return ResponseEntity.ok().body(Currency);
    }

    @RequestMapping(path = "/CurrencyExchange", method = RequestMethod.GET)
    public ResponseEntity <CurrencyResponse> exchangeCurrency(@RequestParam String currency1, @RequestParam BigDecimal amount, @RequestParam String currency2) {
        Currency Currency1 = CurrencyRepository.findCurrencyByName(currency1);
        Currency Currency2 = CurrencyRepository.findCurrencyByName(currency2);
        BigDecimal rate = Currency2.getRate().divide(Currency1.getRate(), RoundingMode.UP);
        BigDecimal result = amount.multiply(rate);
        CurrencyResponse ccyResponse = new CurrencyResponse(currency1, currency2, amount, rate, result);
        LogRepository.save(ccyResponse);

        return ResponseEntity.ok().body(ccyResponse);
    }

    //on web service startup
    void initialData() throws Exception {
        CurrencyRepository.deleteAll();
        List<Currency> currencies = fetchData();
        //add all currencies to database
        for(Currency ccy : currencies){
            CurrencyRepository.save(ccy);
        }
    }

    //fetch data from lietuvos bankas
    final String uri = "https://www.lb.lt/webservices/FxRates/FxRates.asmx/getCurrentFxRates?tp=EU";
    RestTemplate restTemplate = new RestTemplate();

    public List<Currency> fetchData() throws Exception {
        List<Currency> currencies = new ArrayList<>();
        String response = restTemplate.getForObject(uri, String.class);
        Document doc = loadXMLFromString(response);

        doc.getDocumentElement().normalize();

        //add eur as main currency
        BigDecimal one = new BigDecimal(1);
        Currency eur = new Currency("EUR", one);
        currencies.add(eur);
            
        NodeList nListCcy = doc.getElementsByTagName("CcyAmt");
        for (int j = 0; j < nListCcy.getLength(); j++) {
            Node nNodeCcy = nListCcy.item(j);
            if (nNodeCcy.getNodeType() == Node.ELEMENT_NODE) {
                Element elem = (Element) nNodeCcy;
                Node node1 = elem.getElementsByTagName("Ccy").item(0);
                String currency = node1.getTextContent();

                Node node2 = elem.getElementsByTagName("Amt").item(0);
                String rate = node2.getTextContent();
                
                //add all currencies to list
                if(!currency.contains("EUR")){
                    BigDecimal rateBG = new BigDecimal(rate);
                    Currency ccy = new Currency(currency, rateBG);
                    currencies.add(ccy);
                }
            }
        }
        return currencies;
    }

    public static Document loadXMLFromString(String xml) throws Exception
    {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        InputSource is = new InputSource(new StringReader(xml));
        return builder.parse(is);
    }
}
