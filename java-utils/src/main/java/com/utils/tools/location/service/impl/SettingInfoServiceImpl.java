package com.utils.tools.location.service.impl;

import com.alibaba.fastjson.JSON;
import com.utils.tools.location.dto.CountPriceDto;
import com.utils.tools.location.dto.DistanceJson;
import com.utils.tools.location.dto.MapDto;
import com.utils.tools.location.service.SettingInfoService;
import org.apache.http.HttpEntity;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.springframework.stereotype.Service;
import java.io.IOException;
import java.util.List;


@Service
public class SettingInfoServiceImpl implements SettingInfoService {

    /**
     *  返回的还有更加具体的json参数 具体看腾讯地图官网
     * @param countPriceDto
     * @return
     */


    @Override
    public Double getAdvicePrice(CountPriceDto countPriceDto) {
        // 腾讯地图秘钥
        String key = "NT2BZ-Y5Q34-YYCUF-DLQNZ-KW3L5-TCFCC";
        // 起始地
        String from = countPriceDto.getFrom();
        // 目的地
        String to = countPriceDto.getTo();
        // url
        String url = "https://apis.map.qq.com/ws/geocoder/v1/?address=";
        String fromLngLat = getLngLat(url, from, key);
        String toLngLat = getLngLat(url, to, key);
        MapDto fromMapDto = JSON.parseObject(fromLngLat, MapDto.class);
        MapDto toMapDto = JSON.parseObject(toLngLat, MapDto.class);
        MapDto.result fromResult = fromMapDto.getResult().get(0);
        MapDto.result toResult = toMapDto.getResult().get(0);
        String disUrl = "https://apis.map.qq.com/ws/direction/v1/driving/?from=";
        String disFromLngLat = fromResult.getLocation().getLat() + "," + fromResult.getLocation().getLng();
        String disToLngLat = toResult.getLocation().getLat() + "," + toResult.getLocation().getLng();
        String distanceJson = getDistance(disUrl, disFromLngLat, disToLngLat, key);
        DistanceJson disJson = JSON.parseObject(distanceJson, DistanceJson.class);
        Double dis = ComputeDis(disJson);
        if (dis < 1000) {
            dis = 1000.00;
        }
        /**
         *  计算两地之间多少米以及价钱
         */
//        System.out.println("dis =>>>>>>>>>>>>>>> " + dis);
//        List<MoneySettingDomain> domains = moneySettingMapper.selectAll();
//        MoneySettingDomain domain = domains.get(0);
//        if (domain.getMoney() == null) {
//            return (dis / 1000) * 6;
//        } else {
//            return (dis / 1000) * domain.getMoney();
//        }
        return null;
    }

    private String getLngLat(String url, String location, String key) {
        String request = url + location + "&key=" + key;
        CloseableHttpClient httpClient = null;
        CloseableHttpResponse response = null;
        String result = "";
        try {
            // 通过址默认配置创建一个httpClient实例
            httpClient = HttpClients.createDefault();
            // 创建httpGet远程连接实例
            HttpGet httpGet = new HttpGet(request);
            // 设置请求头信息，鉴权
            httpGet.setHeader("Authorization", "Bearer da3efcbf-0845-4fe3-8aba-ee040be542c0");
            // 设置配置请求参数
            RequestConfig requestConfig = RequestConfig.custom().setConnectTimeout(35000)// 连接主机服务超时时间
                    .setConnectionRequestTimeout(35000)// 请求超时时间
                    .setSocketTimeout(60000)// 数据读取超时时间
                    .build();
            // 为httpGet实例设置配置
            httpGet.setConfig(requestConfig);
            // 执行get请求得到返回对象
            response = httpClient.execute(httpGet);
            // 通过返回对象获取返回数据
            HttpEntity entity = response.getEntity();
            // 通过EntityUtils中的toString方法将结果转换为字符串
            result = EntityUtils.toString(entity);
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            // 关闭资源
            if (null != response) {
                try {
                    response.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (null != httpClient) {
                try {
                    httpClient.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return result;
    }

    // "39.915285,116.403857&to=39.915285,116.803857&output=json&callback=cb&key=OB4BZ-D4W3U-B7VVO-4PJWW-6TKDJ-WPB77";
    private String getDistance(String disUrl, String disFromLngLat, String disToLngLat, String key) {
        String request = disUrl + disFromLngLat + "&to=" + disToLngLat + "&output=json&callback=cb&key=" + key;
        CloseableHttpClient httpClient = null;
        CloseableHttpResponse response = null;
        String result = "";
        try {
            // 通过址默认配置创建一个httpClient实例
            httpClient = HttpClients.createDefault();
            // 创建httpGet远程连接实例
            HttpGet httpGet = new HttpGet(request);
            // 设置请求头信息，鉴权
            httpGet.setHeader("Authorization", "Bearer da3efcbf-0845-4fe3-8aba-ee040be542c0");
            // 设置配置请求参数
            RequestConfig requestConfig = RequestConfig.custom().setConnectTimeout(35000)// 连接主机服务超时时间
                    .setConnectionRequestTimeout(35000)// 请求超时时间
                    .setSocketTimeout(60000)// 数据读取超时时间
                    .build();
            // 为httpGet实例设置配置
            httpGet.setConfig(requestConfig);
            // 执行get请求得到返回对象
            response = httpClient.execute(httpGet);
            // 通过返回对象获取返回数据
            HttpEntity entity = response.getEntity();
            // 通过EntityUtils中的toString方法将结果转换为字符串
            result = EntityUtils.toString(entity);
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            // 关闭资源
            if (null != response) {
                try {
                    response.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (null != httpClient) {
                try {
                    httpClient.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return result;
    }

    private Double ComputeDis(DistanceJson json) {
        List<DistanceJson.results> result = json.getResult();
        DistanceJson.results results = result.get(0);
        List<DistanceJson.results.route> routes = results.getRoutes();
        DistanceJson.results.route route = routes.get(0);
        return route.getDistance();
    }
}
