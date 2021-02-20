package pers.store.market.member.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.apache.http.HttpResponse;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import pers.store.market.common.domain.vo.SocialUserVo;
import pers.store.market.common.utils.HttpUtils;
import pers.store.market.common.utils.PageUtils;
import pers.store.market.common.utils.Query;
import pers.store.market.member.dao.MemberDao;
import pers.store.market.member.entity.MemberEntity;
import pers.store.market.member.entity.MemberLevelEntity;
import pers.store.market.member.exception.PhoneNumExistException;
import pers.store.market.member.exception.UserExistException;
import pers.store.market.member.service.MemberLevelService;
import pers.store.market.member.service.MemberService;
import pers.store.market.member.vo.UserLoginVo;
import pers.store.market.member.vo.UserRegisterVo;


@Service("memberService")
public class MemberServiceImpl extends ServiceImpl<MemberDao, MemberEntity> implements MemberService {

    @Autowired
    private MemberLevelService memberLevelService;

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<MemberEntity> page = this.page(
                new Query<MemberEntity>().getPage(params),
                new QueryWrapper<MemberEntity>()
        );

        return new PageUtils(page);
    }

    /**
     * 注册接口
     *
     * @param registerVo 用户参数vo
     */
    @Override
    public void register(UserRegisterVo registerVo) {
        //校验用户数据唯一性
        checkPhoneUnique(registerVo.getPhone());
        checkUserNameUnique(registerVo.getUserName());
        //封装数据
        MemberEntity memberEntity = new MemberEntity();
        memberEntity.setCreateTime(new Date());
        memberEntity.setMobile(registerVo.getPhone());
        memberEntity.setUsername(registerVo.getUserName());

        //密码加密md5
        //spring提供BCryptPasswordEncoder加密工具类,自动加盐值
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String encodePassword = passwordEncoder.encode(registerVo.getPassword());
        memberEntity.setPassword(encodePassword);
        MemberLevelEntity defaultLevelEntity = memberLevelService.getOne(new QueryWrapper<MemberLevelEntity>().eq("default_status", 1));
        if (defaultLevelEntity != null) {
            memberEntity.setLevelId(defaultLevelEntity.getId());
        }
        this.baseMapper.insert(memberEntity);
    }

    /**
     * 登录接口
     *
     * @param userLoginVo 用户登录表单参数
     * @return MemberEntity
     */
    @Override
    public MemberEntity login(UserLoginVo userLoginVo) {
        String loginAccount = userLoginVo.getAccount();
        //以用户名或电话号登录的进行查询
        MemberEntity entity = this.getOne(new QueryWrapper<MemberEntity>().eq("username", loginAccount).or().eq("mobile", loginAccount));
        if (entity != null) {
            BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
            boolean matches = bCryptPasswordEncoder.matches(userLoginVo.getPassword(), entity.getPassword());
            if (matches) {
                entity.setPassword("");
                return entity;
            }
        }
        return null;
    }

    /**
     * 新浪微博社交登录
     *
     * @param socialUser 参数类
     * @return MemberEntity
     */
    @Override
    public MemberEntity login(SocialUserVo socialUser) {
        MemberEntity memberEntity = this.getOne(new QueryWrapper<MemberEntity>().eq("uid", socialUser.getUid()));
        //1 如果之前未登陆过，则查询其社交信息进行注册
        if (memberEntity == null) {
            Map<String, String> query = new HashMap<>();
            query.put("access_token",socialUser.getAccess_token());
            query.put("uid", socialUser.getUid());
            //调用微博api接口获取用户信息
            String json = null;
            try {
                HttpResponse response = HttpUtils.doGet("https://api.weibo.com", "/2/users/show.json", "get", new HashMap<>(), query);
                json = EntityUtils.toString(response.getEntity());
            } catch (Exception e) {
                e.printStackTrace();
            }
            JSONObject jsonObject = JSON.parseObject(json);
            //获得昵称，性别，头像
            String name = jsonObject.getString("name");
            String gender = jsonObject.getString("gender");
            String profile_image_url = jsonObject.getString("profile_image_url");
            //封装用户信息并保存
            memberEntity = new MemberEntity();
            MemberLevelEntity defaultLevel = memberLevelService.getOne(new QueryWrapper<MemberLevelEntity>().eq("default_status", 1));
            memberEntity.setLevelId(defaultLevel.getId());
            memberEntity.setNickname(name);
            memberEntity.setGender("m".equals(gender)?0:1);
            memberEntity.setHeader(profile_image_url);
            memberEntity.setAccessToken(socialUser.getAccess_token());
            memberEntity.setUid(socialUser.getUid());
            memberEntity.setExpiresIn(socialUser.getExpires_in());
            this.save(memberEntity);
        }else {
            //2 否则更新令牌等信息并返回
            memberEntity.setAccessToken(socialUser.getAccess_token());
            memberEntity.setUid(socialUser.getUid());
            memberEntity.setExpiresIn(socialUser.getExpires_in());
            this.updateById(memberEntity);
        }
        return memberEntity;
    }


    private void checkUserNameUnique(String userName) {
        Integer count = baseMapper.selectCount(new QueryWrapper<MemberEntity>().eq("username", userName));
        if (count > 0) {
            throw new UserExistException();
        }
    }

    private void checkPhoneUnique(String phone) {
        Integer count = baseMapper.selectCount(new QueryWrapper<MemberEntity>().eq("mobile", phone));
        if (count > 0) {
            throw new PhoneNumExistException();
        }
    }

}