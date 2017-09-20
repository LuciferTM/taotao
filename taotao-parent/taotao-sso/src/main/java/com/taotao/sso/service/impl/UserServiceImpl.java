package com.taotao.sso.service.impl;

import com.taotao.common.jedis.JedisClient;
import com.taotao.common.pojo.TaotaoResult;
import com.taotao.common.util.CookieUtils;
import com.taotao.common.util.JsonUtils;
import com.taotao.mapper.TbUserMapper;
import com.taotao.pojo.TbUser;
import com.taotao.pojo.TbUserExample;
import com.taotao.sso.service.UserService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * <p>Title:</p>
 * <p>Description: </p>
 * <p>Company:</p>
 *
 * @author Lucifer
 * @date 2017/9/19
 */

@Service
public class UserServiceImpl implements UserService {



    @Value("${REDIS_USER_SESSION_KEY}")
    private String REDIS_USER_SESSION_KEY;

    @Value("${SSO_SESSION_EXPIRE}")
    private Integer SSO_SESSION_EXPIRE;

    @Autowired
    private TbUserMapper tbUserMapper;

    @Autowired
    private JedisClient jedisClient;

    @Override
    public TaotaoResult checkData(String param, Integer type) {
        /**
         * type=1 username
         * type=2 phone
         * type=3 email
         */
        if (type < 1 || type > 3) {
            return TaotaoResult.build(400, "Invalid Input");
        }

        TbUserExample tbUserExample = new TbUserExample();
        TbUserExample.Criteria critia = tbUserExample.createCriteria();
        if (type == 1) {
            critia.andUsernameEqualTo(param);
        } else if (type == 2) {
            critia.andPhoneEqualTo(param);
        } else if (type == 3) {
            critia.andEmailEqualTo(param);
        } else {
            return TaotaoResult.build(400, "Invalid Input");
        }
        List<TbUser> list = tbUserMapper.selectByExample(tbUserExample);
        if (list == null || list.size() == 0) {
            return TaotaoResult.ok(true);
        }
        return TaotaoResult.ok(false);
    }

    @Override
    public TaotaoResult createUser(TbUser user) {
        user.setCreated(new Date());
        user.setUpdated(new Date());
        user.setPassword(DigestUtils.md5DigestAsHex(user.getPassword().getBytes()));
        tbUserMapper.insert(user);
        return TaotaoResult.ok();
    }

    @Override
    public TaotaoResult login(String username, String password,
                              HttpServletRequest request, HttpServletResponse response) {
        TbUserExample tbUserExample = new TbUserExample();
        TbUserExample.Criteria criteria = tbUserExample.createCriteria();
        criteria.andUsernameEqualTo(username);
        List<TbUser> list = tbUserMapper.selectByExample(tbUserExample);
        if(list == null || list.size() == 0){
            TaotaoResult result = TaotaoResult.build(400, "用户名或密码错误");
            return result;
        }
        TbUser tbUser = list.get(0);
        //对比密码
        if(!DigestUtils.md5DigestAsHex(password.getBytes()).equals(tbUser.getPassword())){
            TaotaoResult result = TaotaoResult.build(400, "密码错误");
            return result;
        }
        //验证通过,设置token并返回token
        //生成token
        String token = UUID.randomUUID().toString();
        //保存用户之前，把用户对象中的密码清空。
        tbUser.setPassword(null);
        //把用户信息写入redis
        jedisClient.set(REDIS_USER_SESSION_KEY + ":" + token, JsonUtils.objectToJson(tbUser));
        //设置session的过期时间
        jedisClient.expire(REDIS_USER_SESSION_KEY + ":" + token, SSO_SESSION_EXPIRE);

        //添加写cookie的逻辑，cookie的有效期是关闭浏览器就失效。
        CookieUtils.setCookie(request, response, "TT_TOKEN", token);

        //返回token
        return TaotaoResult.ok(token);


    }

    @Override
    public TaotaoResult getUserByToken(String token) {
        String userinfo = jedisClient.get(REDIS_USER_SESSION_KEY + ":" + token);
        if(StringUtils.isBlank(userinfo)){
            return TaotaoResult.build(400, "token已过期");
        }
        return TaotaoResult.ok(JsonUtils.jsonToPojo(userinfo, TbUser.class));
    }
}
