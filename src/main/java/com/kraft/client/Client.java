package com.kraft.client;

import tr.gov.nvi.tckimlik.ws.KPSPublic;
import tr.gov.nvi.tckimlik.ws.KPSPublicSoap;

public class Client {

    public static void main(String[] args) {

        KPSPublic kps = new KPSPublic();
        KPSPublicSoap kpsSoap = kps.getKPSPublicSoap();
        boolean result = kpsSoap.tcKimlikNoDogrula(12229650586L,"Larhonda","Ernser",2003);
        System.out.println("Sonuç: " + result);
    }
}
