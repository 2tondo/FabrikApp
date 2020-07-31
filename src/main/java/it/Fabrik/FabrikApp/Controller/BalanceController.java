package it.Fabrik.FabrikApp.Controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

@org.springframework.stereotype.Controller
@RequestMapping("/balance/")
public class BalanceController {

    private final RestTemplate restTemplate;

    public BalanceController(RestTemplateBuilder restTemplateBuilder) {
        this.restTemplate = restTemplateBuilder.build();
    }

    @RequestMapping("showBalance")
    public String showBalance(Model model, HttpServletRequest request, HttpServletResponse response){

        //TODO get balance https://docs.fabrick.com/platform/apis/gbs-banking-account-cash-v4.0

        // https://sandbox.platfr.io/api/gbs/banking/v4.0/accounts/{accountId}/balance

        String uID = request.getParameter("UID");

        if(uID == null){
            return "genericError";
        }

        String url = "https://sandbox.platfr.io/api/gbs/banking/v4.0/accounts/" + uID +"/balance";


        ObjectMapper mapper = new ObjectMapper();
        String json;
        try {
            json = this.restTemplate.getForObject(url, String.class);
        }catch (Exception e){
            return "genericError";
        }

        Map map;
        try {
            map = mapper.readValue(json, Map.class);
        } catch (IOException e) {
            return "genericError";
        }

        model.addAttribute("balance", map.get("balance"));

        return "showBalance";
    }
}