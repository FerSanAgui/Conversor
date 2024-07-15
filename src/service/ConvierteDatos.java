package service;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import modelo.TasaDeConversion;
import modelo.TasaDeConversionApi;

public class ConvierteDatos {
    public TasaDeConversionApi convierteDatos(String json){
        Gson gson = new GsonBuilder()
                .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
                .setPrettyPrinting()
                .create();
        TasaDeConversionApi tasaDeConversionApi = gson.fromJson(json, TasaDeConversionApi.class);
        TasaDeConversion taseDeConversion =  new TasaDeConversion(tasaDeConversionApi);
        return tasaDeConversionApi;

    }
}
