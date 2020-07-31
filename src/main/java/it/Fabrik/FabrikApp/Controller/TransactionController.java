package it.Fabrik.FabrikApp.Controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.*;

import org.springframework.web.client.RestTemplate;


@org.springframework.stereotype.Controller
@RequestMapping("/transaction/")
public class TransactionController {

    private final RestTemplate restTemplate;

    public TransactionController(RestTemplateBuilder restTemplateBuilder) {
        this.restTemplate = restTemplateBuilder.build();
    }

    @RequestMapping("doTransaction")
    public String doTransaction(Model model){
        //TODO https://docs.fabrick.com/platform/apis/gbs-banking-payments-moneyTransfers-v4.0
        //the first time the uid is taken and a call to a restful controller made, the second time a list filled by the restful controller

        //https://sandbox.platfr.io/api/gbs/banking/v4.0/accounts/{accountId}/payments/money-transfers


        return "doTransaction";
    }

    @RequestMapping("getTransactions")
    public String getTransactions(Model model, HttpServletRequest request){

        //TODO https://docs.fabrick.com/platform/apis/gbs-banking-account-cash-v4.0
        //https://sandbox.platfr.io/api/gbs/banking/v4.0/accounts/{accountId}/transactions [?<uriQuery>]


        String uID = request.getParameter("UID");
        String fromAccountingDate = request.getParameter("fromAccountingDate");
        String toAccountingDate = request.getParameter("toAccountingDate");

        if(uID == null || fromAccountingDate == null || toAccountingDate == null){
            return "genericError";
        }

        String url = "https://sandbox.platfr.io/api/gbs/banking/v4.0/accounts/" + uID +"/balance" + "?fromAccountingDate=" + fromAccountingDate + "toAccountingDate";


        ObjectMapper mapper = new ObjectMapper();
        String json = this.restTemplate.getForObject(url, String.class);
        List<String> list;
        try {
           list = Arrays.asList(mapper.readValue(json, String[].class));
        } catch (IOException e) {
            e.printStackTrace();
            return "genericError";
        }

        List<Map> outputList = null;
        for (String tmp:list) {
            try {
                outputList.add(mapper.readValue(tmp, Map.class));
            } catch (IOException e) {
                e.printStackTrace();
                return "genericError";
            }
        }

        model.addAttribute("outputList", outputList);

        return "getTransactions";
    }

    @RequestMapping("setFromToTransactions")
    public String setFromToTransactions(Model model, HttpServletRequest request){

        String uID = request.getParameter("uID");
        model.addAttribute("uID" + uID);

        return "setFromToTransactions";
    }
}