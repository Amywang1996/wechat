package com.amy.api.sdk;

import com.alibaba.fastjson.JSONObject;
import com.amy.api.CommonResult;
import com.amy.domain.User;
import com.amy.service.UserService;
import com.amy.utils.HttpRequest;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * SDK编写接口
 */
@Api(tags = "微信授权绑定手机号支付")
@RestController
@RequestMapping("/sdk/api")
public class SdkUserApi {
    @Value("${custom.wx.appId}")
    private String appId;
    @Value("${custom.wx.appIdSecret}")
    private String appIdSecret;
    @Autowired
    private UserService userService;
    protected final Logger logger = LoggerFactory.getLogger(SdkUserApi.class);
    @ApiOperation("微信授权登陆")
    @RequestMapping(value = "/custom/wxUserLogin", method = RequestMethod.POST)
    @ResponseBody
    @ApiImplicitParams({
            @ApiImplicitParam(name = "code", value = "CODE", required = true, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "avatar", value = "avatarUrl", required = true, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name="nickName",value="昵称",required = true,dataType = "String",paramType = "query")
    })
    public CommonResult wxUserLogin(String code, String avatar, String nickName) throws Exception {
        Map<String, Object> map = new HashMap<>();
        StringBuffer buffer= new StringBuffer("https://api.weixin.qq.com/sns/jscode2session?appid=")
                .append(appId).append("&secret=").append(appIdSecret).append("&js_code=").append(code)
                .append("&grant_type=authorization_code");
        String jsonString = HttpRequest.sendGet(buffer.toString());
        JSONObject jsStrs = JSONObject.parseObject(jsonString);
        String openId = jsStrs.getString("openid");
        String sessionKey = jsStrs.getString("session_key");
        map.put("openId", openId);
        map.put("sessionKey", sessionKey);
        map.put("mobileFlag",false);
        if (openId != null) {
            //查询用户信息
            User user =userService.getUserByOpenId(openId);
            if(user==null){
                user=new User();
                user.setNickName(nickName);
                user.setOpenId(openId);
                user.setHeadPortrait(avatar);
                userService.insertUser(user);
            }else{
                if(user.getMobile()!=null){
                    map.put("mobileFlag",true);
                }
            }
            map.put("user",user);
        }
      return new CommonResult(200,"操作成功",map);
    }
    @ApiOperation("绑定手机号码")
    @RequestMapping(value = "/bindMobile", method = RequestMethod.POST)
    @ResponseBody
    @ApiImplicitParams({
            @ApiImplicitParam(name = "openId", value = "OPENID", required = true, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "id", value = "用户id", required = true, dataType = "Long", paramType = "query"),
            @ApiImplicitParam(name = "encrypData", value = "encrypData", required = true, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "iv", value = "iv", required = true, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "sessionKey", value = "sessionKey", required = true, dataType = "String", paramType = "query")
    })
    public CommonResult bindMobile(String openId, Long id, String encrypData, String iv, String sessionKey ) {
        try {
            User user = userService.getUserByIdAndOpenId(id,openId);
            if(user==null){
                return new CommonResult(203,"用户校验失败");
            }
            logger.info("Bind_RequestJson==================>>>> openId="+openId+",encrypData="+encrypData+",iv="+iv+",sessionKey:"+sessionKey);
            return userService.bind(user,encrypData, iv, sessionKey);
        } catch (Exception e) {
            e.printStackTrace();
            logger.info("BindError===>"+e.getMessage());
            return new CommonResult(203,"操作失败",e.getMessage());
        }
    }
}
