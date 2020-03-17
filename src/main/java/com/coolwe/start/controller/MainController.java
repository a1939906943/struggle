package com.coolwe.start.controller;

import com.coolwe.start.dao.MainDao;
import net.sf.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.List;
import java.util.Map;

@RestController
public class MainController {
    private Logger logger = LoggerFactory.getLogger(getClass());
    private static JSONObject reqData = null;
    private int width = 200;
    private int height = 69;
    @Resource
    private MainDao mainDao;
    @Autowired
    HttpServletRequest request;

    @GetMapping(value = { "/hello" })
    public String come(HttpServletRequest request) {
        logger.info("/hello");
        InetAddress localHost = null;
        try {
            localHost = Inet4Address.getLocalHost();
        } catch (UnknownHostException e) {
            logger.error(e.getMessage(), e);
        }
        String ip = localHost.getHostAddress();
        String name = localHost.getHostName();
        return "ip:" + ip + ",计算机名:" + name;
    }

    @GetMapping(value = { "/cc" })
    public Boolean cc() {
        return mainDao.cc();
    }

    @PostMapping(value = { "/showtables" })
    public List<Map<String, Object>> showtables(@RequestParam("req") String req) {
        return mainDao.showtables(req);
    }

    @PostMapping(value = { "/showtable" })
    public List<Map<String, Object>> showtable(@RequestParam("req") String req) {
        return mainDao.showtable(req);
    }

    @PostMapping(value = "/showDatabases")
    public List<Map<String, Object>> showDatabases(String name) {
        logger.info("/showDatabases");
        return mainDao.showDatabases();
    }

}
