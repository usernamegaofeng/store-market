package pers.store.market.member.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Map;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
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