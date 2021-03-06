package com.baohuquan.resovler;

import com.baohuquan.exception.BaohuquanException;
import com.baohuquan.utils.ResponseCode;
import com.google.common.base.Throwables;
import com.google.common.collect.Maps;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * Created by wangdi5 on 2016/3/20.
 */
public class BaohuquanResolver implements HandlerExceptionResolver {


    Logger logger = LoggerFactory.getLogger(BaohuquanResolver.class);

    @Override
    public ModelAndView resolveException(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
        ModelAndView mv = new ModelAndView();
        MappingJackson2JsonView view = new MappingJackson2JsonView();
        Map<String, String> map = Maps.newHashMap();

        if (ex instanceof BaohuquanException) {
            BaohuquanException sce = (BaohuquanException) ex;
            map.put("code", sce.getResponseCode().getCode());
            map.put("msg", sce.getResponseCode().getMsg());
            view.setAttributesMap(map);
            mv.setView(view);
            return mv;
        }
        logger.error("Exception ex is {} ", Throwables.getRootCause(ex).getMessage(), Throwables.getRootCause(ex));
        map.put("code", ResponseCode.SYSTEM_ERROR.getCode());
        map.put("msg",  ResponseCode.SYSTEM_ERROR.getMsg());
        view.setAttributesMap(map);
        mv.setView(view);
        return mv;
    }

}
