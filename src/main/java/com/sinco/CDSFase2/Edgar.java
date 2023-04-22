package com.sinco.CDSFase2;

import com.sinco.CDSFase2.controllers.ApiAccess;
import org.json.JSONObject;

public class Edgar {
    public void main(){
        ApiAccess api = new ApiAccess();
        api.listData("wind");
        String respuesta = getWindItem("WIND001", "coste_transporte");
        System.out.println(respuesta);
    }

    public String getWindItem(String item, String dato){
        ApiAccess api = new ApiAccess();
        JSONObject jsonObject = api.itemData("wind",item);
        if(jsonObject!=null){
            JSONObject wind = (JSONObject) jsonObject.get(item);
            return wind.get(dato).toString();
        }
        return "error";
    }
}
