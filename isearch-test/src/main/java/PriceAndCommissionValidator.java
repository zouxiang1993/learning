import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.io.IOUtils;
import util.HttpClientResult;
import util.HttpClientUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.StringWriter;
import java.net.URI;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

/**
 * Description: No Description
 *
 * @author zouxiang
 * @date 2020/5/8
 */
public class PriceAndCommissionValidator {

    public static final String ISEARCH_PREFIX = "http://localhost:8280";
    public static final Map<String, String> ISEARCH_HEADERS;

    public static final String PRODUCT_PREFIX = "https://sm-napi-dev.weilaijishi.com/api/product-restructure/optionauthc/prodetail/product?id=";

    static {
        ISEARCH_HEADERS = new HashMap<>();
        ISEARCH_HEADERS.put("App-Owner-Code", "2000");
        ISEARCH_HEADERS.put("Content-Type", "application/json");
    }

    public static void main(String[] args) throws Exception {
        new PriceAndCommissionValidator().work();
//        System.out.println(decimal2("2900.0"));
//        System.out.println(decimal2("2900.00"));
//        System.out.println(isSameDecimal("2900.0", "2900.00"));
    }

    public void work() throws Exception {
        URL url = this.getClass().getClassLoader().getResource("PriceAndCommissionValidator");
        URI uri = url.toURI();
        File dir = new File(uri);
        File[] files = dir.listFiles();
        for (File file : files) {
            boolean success = validate(file);
            if (!success) {
                break;
            }
        }
    }

    private boolean validate(File file) throws Exception {
        System.out.println("当前文件名称: " + file.getName());

        StringWriter sw = new StringWriter();
        IOUtils.copy(new FileInputStream(file), sw);
        String jsonString = sw.toString();

        // 遍历每一个cps渠道，分别校验。
        for (int channelId = 1; channelId <= 1; channelId++) {
            System.out.println("当前channelId: " + channelId);
            String requestJson = jsonString.replaceAll("\\{\\{place_holder}}", String.valueOf(channelId));
            JSONObject jsonObject = JSONObject.parseObject(requestJson);
            String url = (String) jsonObject.remove("请求地址");
            url = url.replaceAll("\\{\\{prefix}}", ISEARCH_PREFIX);
            requestJson = jsonObject.toJSONString();

            System.out.println("请求地址： " + url);
            System.out.println("请求参数: " + requestJson);
            HttpClientResult result = HttpClientUtils.doPostWithJsonRequest(url, ISEARCH_HEADERS, requestJson);
            // 再发一次请求，当前有bug
            result = HttpClientUtils.doPostWithJsonRequest(url, ISEARCH_HEADERS, requestJson);
            String content = result.getContent();
            JSONObject isearchResult = JSONObject.parseObject(content);
            JSONObject res = isearchResult.getJSONObject("result");
            JSONArray data = res.getJSONArray("data");
            if (data.size() == 0) {
                throw new RuntimeException("isearch搜索结果为空");
            }
            for (int i = 0; i < data.size(); i++) {
                JSONObject item = data.getJSONObject(i);
                String spuCode = item.getString("spuCode");
                String price = item.getString("price");
                String commission = item.getString("commission");


                HttpClientResult productHttpResult = HttpClientUtils.doGet(PRODUCT_PREFIX + spuCode);
                String productContent = productHttpResult.getContent();
                JSONObject productResult = JSONObject.parseObject(productContent).getJSONObject("result");
                if (productResult == null) {
                    System.out.println(spuCode + "\t" + productContent);
                    continue;
                }
                if (channelId == 0) {
                    // TODO: 探市的规则
                } else {
                    // CPS 的规则
                    JSONObject productCps = productResult.getJSONObject("productCps");
                    String productPrice = productCps.getString("price");
                    String productCommission = productCps.getString("commission");
                    String productId = productCps.getString("productId");
                    if (isSameDecimal(price, productPrice) && isSameDecimal(commission, productCommission)) {

                    } else {
                        System.out.println(spuCode + "\t" + price + "\t" + commission);
                        System.out.println(productId + "\t" + productPrice + "\t" + productCommission);
                        return false;
                    }
                }
                System.out.println(productResult);
            }

        }
        return true;
    }

    private static boolean isSameDecimal(String s1, String s2) {
        if (s1 == null) {
            if (s2 == null) {
                return true;
            }else {
                return false;
            }
        }
        s1 = decimal2(s1);
        s2 = decimal2(s2);
        return s1.equals(s2);
    }

    private static String decimal2(String s) {
        int idx = s.indexOf(".");
        if (idx == -1) {
            return s + ".00";
        }
        s = s + "000000";

        idx = s.indexOf(".");
        return s.substring(0, idx + 3);
    }
}
