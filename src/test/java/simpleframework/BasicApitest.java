package simpleframework;

import org.testng.annotations.Test;
import simpleframework.utilities.RestAssuredUtil;

public class BasicApitest extends BaseTest{

    @Test
    public void T01_Statuscodeandgetclienttest(){
        res= RestAssuredUtil.getResponse("/gen/clients");
        tu.checkStatusIs200(res);
        jp=RestAssuredUtil.getJSONPath(res);
        System.out.println(tu.getClients(jp));
    }
    @Test
    public void T02_GetAndroidmodelPackageoptions(){
        res=RestAssuredUtil.getResponse("/gen/clients/android");
        tu.checkStatusIs200(res);
        jp=RestAssuredUtil.getJSONPath(res);
        System.out.println("Opt: "+jp.get("modelPackage.opt"));

    }

    @Test
    public void T03_validateHeader(){

        tu.checkheaders();
    }
}
