package com.yupi.yunpicturebackend.api.imagesearch;

import com.yupi.yunpicturebackend.api.imagesearch.model.ImageSearchResult;
import com.yupi.yunpicturebackend.api.imagesearch.sub.GetImageFirstUrlApi;
import com.yupi.yunpicturebackend.api.imagesearch.sub.GetImageListApi;
import com.yupi.yunpicturebackend.api.imagesearch.sub.GetImagePageUrlApi;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Slf4j
public class ImageSearchApiFacade {

    /**
     * 搜索图片
     * @param imageUrl
     * @return
     */
    public static List<ImageSearchResult> searchImage(String imageUrl) {
        String imagePageUrl = GetImagePageUrlApi.getImagePageUrl(imageUrl);
        String imageFirstUrl = GetImageFirstUrlApi.getImageFirstUrl(imagePageUrl);
        List<ImageSearchResult> imageList = GetImageListApi.getImageList(imageFirstUrl);
        return imageList;
    }

    public static void main(String[] args) {
        List<ImageSearchResult> imageList = searchImage("data:image/webp;base64,UklGRmIGAABXRUJQVlA4IFYGAADwMACdASrtAA4BPp1Oo0wlrCOoJfd4aYATiWNu3sEOVFZ38B2wXo/C79ZLwe99Ac6L81+wBzq/Mb5un+W9aX+09QD/VdUN6CnTGZC3r87QZbMRVkuKvh5nvT/w2+vGK8+mbB6d+H0NfLaDMyyZsHp33Ts5YAZzUGfJ5CUBkaw30G3eDLcP9f//IJQb9JWF4N7KX/GOeP7utDg2qazNvw75dkzxOCd0lY3WzXHAqkRZkLenYbLeRpACAJU7X0x1Uih08yp0pHkq57yCknDG9IrFkxOxY38d9TQ8qnFEVDg2nXLfjeB4r8wXIlmuN8JSiOxS+v2pVDywUpa8ZmVgyhHkq8NlRMAdPXnBpvAayjqvqGmsMKOGO2eXbjoxNtiQ5V1mEuo8fNZQ7NvWQTUixMm7Rq5+LC2xxzjz/ziMw2Sf7qjmxUxSnnDSOjuxEQEYk8/Us+UPLmbvP6W3j7KgH4mY3Q9UIMWSpod2kqWjiZvq/0tLgFiEPhd7Mmg/D6H0meEElCVYrNFy5EE+yvN1d24AAP73jgEuD0oAAhms0bPxSW4BZ4WEc0iqccfjKeqht+leSCDiy7NEADD3RCr66PkYfSxufMJJBSKS4bGu8nY7YLub+EJofatFfpawYyWW9w2dMG+blQSTAu0dT9d96dauMK3PyeFH3RzryZqGGaNWqtP+s3R1kTBWGGdVb957j3MBuwi047sxY4WSstHdLK96H+6VeltrTcUJ7szlgJtRm7QUVYGfX8cSm+2/QhyNc0IYFR4ESwQNCah9t87gGLcYy20ur4NfVFyl2Pcd6zf52rBp8WorVcBMOznexCQ/94EUXEJNTaIqSvq3uBf7RfVlCVTd3ta/YpZ+Mw774lthbFeMli2uwVsaSPNnus/p9TTO6SCq6Nb9ICwxK19bRQBIflFwzcaXrvnKxYkpEWUiS6oot5FITauwONqf4+Dy+EjrnF4dp5dxT+QyfuwPtfx4O/NIu6EwntXYgxHkdTu7HbPX1MXP9vDJJQ0tCkcKHNM0SV8I6x4OWEp3LwYD0CfzeMGyko6Li0GW3D2Lf438uethwsumeWdutfpVKvsVSo7HxqUaGiocoh2SyYjbngZZw/X+xUpraq3rJ9EcfeyLyrRpMWj86c65oxr7pwTCglym52WlmHpbEnQLV86f+Uk5kcd8kM9/czTweqlQIQ4If+XeKW6Cb9wN4ZtKneEGqHTVMRlBGHWv+jdCN8fZEOGXGwkC4Gr4vpwhlVPNwYzmoAj5KknxbCytCKYYSGQ45WQtakCMd3JkkTjm1enPO86BRljGHgmVVY02OEAP3bNrfeTsRuqsGWyUp/yczky28w17qAX1IwnQ5ufezguTDewTttaTwpRqURYLhiD4BK2p0Ee6CdAZdWrMmVZW4UAVOpHC05r5TckhldSJAmzmuVSQR85AQNNoiBrtbB+4YUSSsatttyZYKps0kHxyCf2H7VkFn4Np8BXwkcCxu+5014vtVOedN9zDIT4T9PhpLykeDAT/LusPWmEtcVuuzOSdSA/qSHvtn0jUFFeaFeWPWbIvHXjtJr7Nwvpp+QkGoi/vAAH3Wo5xiYwZPwts9iX4XEP3sW/1/NGjaGdt9wbEqcs8rtkIqlAMYMOxFD/fXI9oYgie4g4tl5lk0/BFdKcI+S4HH7yKz4HkRhn29lD0vfLTQ5NekzR04VgCb1ys6HJrvVkYEZRKOUzDJxlGANkDXNBUt7Bnhw0VZ5GATMTzWiG+udCXnqBvb6Qfxtl/SBMQAqg/n70K2UzYxjG2tw+YqA//on5cJ3lQpbkr1siztedhjGdMH6qwuW3Rmvd2ZN3JRjzPmQjbqfVRhv4AieI7lrH9sz/8jz6E5E/ZuCLHYbZQjTYl/uxLbmo0FH0LlV2C3EkJ4ais8JTmpjleuMdKCnyQa7NHwJXBBcKRlaP3aT6wNz0vOGVct/KYiACGQs/57CnIPmFYoLpYr8f7HX34x3HU1XqoXpQExVenhJe+Fmjpnz3d5P0+m6nzCfdoS349eYDnF7mmM2o7lhB6b1LtMEXHJlq2AmL3WnIQczMa6UenrgBAHvv0OfBSgUWhZspiOquRwdKJswuaj2HBXxCdkSoi5cBHhABFEAAxcAKFsM3+qQNK+wNKXB0cGCTJLnybl02YAAAAAA==");
        System.out.println("结果列表" + imageList);
    }
}
