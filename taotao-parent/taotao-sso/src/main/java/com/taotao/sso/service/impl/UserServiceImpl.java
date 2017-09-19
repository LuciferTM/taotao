package com.taotao.sso.service.impl;

import com.taotao.common.pojo.TaotaoResult;
import com.taotao.mapper.TbUserMapper;
import com.taotao.pojo.TbUser;
import com.taotao.pojo.TbUserExample;
import com.taotao.sso.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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

    @Autowired
    private TbUserMapper tbUserMapper;

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
}
