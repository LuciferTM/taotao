package com.taotao.service;

import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

/**
 * Created by LuciferTM on 2017/8/12.
 */
public interface PictuerService {
    Map uploadPicture(MultipartFile uploadFile);
}
