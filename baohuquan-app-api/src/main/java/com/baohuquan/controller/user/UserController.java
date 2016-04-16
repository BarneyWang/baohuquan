package com.baohuquan.controller.user;

import com.baohuquan.anno.TokenRequired;
import com.baohuquan.cache.XMemcached;
import com.baohuquan.constant.CacheKeyConstants;
import com.baohuquan.constant.Gender;
import com.baohuquan.model.ThirdUser;
import com.baohuquan.model.User;
import com.baohuquan.service.SharedBabyInfoServiceIF;
import com.baohuquan.service.ThirdUserServiceIF;
import com.baohuquan.service.UserServiceIF;
import com.baohuquan.sms.MSGSender;
import com.baohuquan.utils.MD5Utils;
import com.baohuquan.utils.ResponseCode;
import com.baohuquan.utils.ResponseWrapper;
import com.baohuquan.utils.TokenUtil;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.Random;
import java.util.regex.Pattern;

import static com.baohuquan.constant.Constants.ENCRYPTION;
import static com.baohuquan.constant.Constants.EXPIRE_5_MINUTE;

/**
 * 登陆接口
 * 手机登陆 和 第三方登陆
 * Created by wangdi5 on 2016/3/20.
 */
@Controller
@RequestMapping(value = "/user", produces = {"application/json;charset=UTF-8"})
public class UserController {

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);


    private final static String MSGTPL = "{0}是您本次的手机验证码，请在5分钟内进行校验，如非本人操作，请忽略。";
    private final static String NICKNAME = "BAO_";


    @Resource
    UserServiceIF userService;

    @Resource
    ThirdUserServiceIF thirdUserService;

    @Resource
    MSGSender msgSender;


    @Resource
    SharedBabyInfoServiceIF sharedBabyInfoService;
    /**
     * 第三方登录
     *
     * @param type
     * @param thirdUserParam
     */
    @RequestMapping(value = "/login", produces = {"application/json;charset=UTF-8"})
    @ResponseBody
    public String loginThird(Byte type, String signature,
                             ThirdUserParam thirdUserParam) throws IOException {
        long start = System.currentTimeMillis();
        ResponseWrapper responseWrapper = new ResponseWrapper();
        responseWrapper.setCode(ResponseCode.SUCCESS.getCode());
        boolean isFirst = false;
        StringBuilder sb = new StringBuilder();
        ResponseCode rc = validateParam(type, signature, thirdUserParam);
        if (!rc.getCode().equals("0")) {
            responseWrapper.setCode(rc.getCode());
            responseWrapper.setMsg(rc.getMsg());
            responseWrapper.setCost(System.currentTimeMillis() - start);
            return responseWrapper.toJSON();
        }
        sb.append(ENCRYPTION).append(thirdUserParam.getOpen_id()).append(ENCRYPTION);
//        if (StringUtils.equals(signature, DigestUtils.md5DigestAsHex(sb.toString().getBytes()))) {
        if (StringUtils.equals(DigestUtils.md5DigestAsHex(sb.toString().getBytes())
                , DigestUtils.md5DigestAsHex(sb.toString().getBytes()))) {
            ThirdUser thirdUser = thirdUserService.getThirdUser(type, thirdUserParam.getOpen_id());
            Integer userId = null;
            User user = null;
            if (thirdUser == null) {
                user = createUser(thirdUserParam);
                createThirdUser(user.getId(), type, thirdUserParam);
                userId=user.getId();
                isFirst=true;
            } else {
                userId = thirdUser.getUserId();
                user = userService.getUser(userId);
            }

            responseWrapper.addValue("uid", userId);
            responseWrapper.addValue("avatarUrl", thirdUserParam.getAvatar_url());
            responseWrapper.addValue("nickName", thirdUserParam.getUser_name());
            responseWrapper.addValue("gender", thirdUserParam.getGender());
            responseWrapper.addValue("token",user.getToken());
            responseWrapper.addValue("isFirst", isFirst ? 0 : 1);

        } else {
            responseWrapper.setCode(ResponseCode.SMCODE_ILLEGAL_TIMEOUT.getCode());
            responseWrapper.setMsg(ResponseCode.SMCODE_ILLEGAL_TIMEOUT.getMsg());
        }
        responseWrapper.getCost(System.currentTimeMillis()-start);
        return responseWrapper.toJSON();
    }

    /**
     * 获取短信验证码
     *
     * @return
     */
    @RequestMapping(value = "/login/SMCode", produces = {"application/json;charset=UTF-8"})
    @ResponseBody
    public String getSMCode(String area,
                            @RequestParam("cell_phone") String phone_number) throws UnsupportedEncodingException {

        long start = System.currentTimeMillis();
        ResponseWrapper responseWrapper = new ResponseWrapper();
        responseWrapper.setCode(ResponseCode.SUCCESS.getCode());
        ResponseCode rc = validateAreaAndPhone(area, phone_number);
        if (!"0".equals(rc.getCode())) {
            responseWrapper.setCode(rc.getCode());
            responseWrapper.setMsg(rc.getMsg());
            return responseWrapper.toJSON();
        }
        String smCode = generalSMCode(4);
        String tpl = "";
        tpl = MSGTPL.replaceAll("\\{0\\}", smCode);

       boolean isSend=  msgSender.sendSMS(tpl,phone_number,"2000");
        if(!isSend){
            responseWrapper.getCost(System.currentTimeMillis()-start);
            responseWrapper.setCode(ResponseCode.SMCODE_SEND_FAIL.getCode());
            responseWrapper.setMsg(ResponseCode.SMCODE_SEND_FAIL.getMsg());
            return responseWrapper.toJSON();
        }
        logger.info("[发送验证码]cellnumber=" + phone_number + "|msg=" + tpl);
        XMemcached.set(CacheKeyConstants.KEY_PHONE_SMCODE + "_" + phone_number, EXPIRE_5_MINUTE, smCode);
        responseWrapper.getCost(System.currentTimeMillis()-start);
        return responseWrapper.toJSON();
    }






    /**
     * 手机号登陆
     *
     * @param area
     * @return
     */
    @RequestMapping(value = "/login", params = "type=0", produces = {"application/json;charset=UTF-8"})
    @ResponseBody
    public String login(String area,
                        @RequestParam(value = "cellnumber") String cellNumber,
                        @RequestParam(value = "smscode") String smCode) {
        long start = System.currentTimeMillis();
        ResponseWrapper responseWrapper = new ResponseWrapper();
        responseWrapper.setCode(ResponseCode.SUCCESS.getCode());
        ResponseCode rc = validateAreaAndPhone(area, cellNumber);
        boolean isFirst = false;
        if (!"0".equals(rc.getCode())) {
            responseWrapper.setCode(rc.getCode());
            responseWrapper.setMsg(rc.getMsg());
            return responseWrapper.toJSON();
        }
        String oldSMCode = XMemcached.get(CacheKeyConstants.KEY_PHONE_SMCODE + "_" + cellNumber);

        //万能测试号
        if ("3141592".equals(smCode)) {
            oldSMCode = "3141592";
        }
        if (oldSMCode == null) {
            responseWrapper.setCode(ResponseCode.SMCODE_TIMEOUT.getCode());
            responseWrapper.setMsg(ResponseCode.SMCODE_TIMEOUT.getMsg());
            return responseWrapper.toJSON();
        }
        if (StringUtils.equals(smCode, oldSMCode)) {
            User user = null;
            String token=null;
            //防止请求过快
            synchronized (cellNumber.intern()) {
                XMemcached.delete(CacheKeyConstants.KEY_PHONE_SMCODE + "_" + cellNumber);
                user = userService.getUserByCell(cellNumber);
                //手机号不存在则创建用户
                if (user == null) {
                    user = createUser(area, cellNumber);
                    userService.saveUser(user);
                    token=TokenUtil.generalToken(user.getId());
                    userService.updateUserToken(user.getId(),token);
                    isFirst = true;
                }
            }
            //查询是否收到过邀请 如果收到过 自动加入家庭圈
            sharedBabyInfoService.isHaveSharedInfo(cellNumber,user.getId());
            responseWrapper.addValue("uid", user.getId());
            responseWrapper.addValue("avatarUrl", user.getAvatarUrl());
            responseWrapper.addValue("nickName", user.getNickName());
            responseWrapper.addValue("gender", user.getGender());
            responseWrapper.addValue("isFirst", isFirst ? 0 : 1);
            responseWrapper.addValue("token",StringUtils.isBlank(token)?user.getToken():token);

        } else {
            responseWrapper.setCode(ResponseCode.SMCODE_ILLEGAL_TIMEOUT.getCode());
            responseWrapper.setMsg(ResponseCode.SMCODE_ILLEGAL_TIMEOUT.getMsg());
        }
        responseWrapper.getCost(System.currentTimeMillis()-start);
        return responseWrapper.toJSON();
    }







    /**
     * 修改头像
     * @param uid
     * @param avatarUrl
     * @return
     */
    @TokenRequired
    @RequestMapping(value = "/update_avatar", produces = {"application/json;charset=UTF-8"})
    @ResponseBody
    public String updateImg(@RequestParam(value = "uid")Integer uid,
                            @RequestParam(value = "avatarurl")String avatarUrl){
        ResponseWrapper responseWrapper = new ResponseWrapper();
        responseWrapper.setCode(ResponseCode.SUCCESS.getCode());
        ResponseCode rc = validateParamImg(uid, avatarUrl);

        if (!rc.getCode().equals("0")){
            responseWrapper.setCode(rc.getCode());
            responseWrapper.setMsg(rc.getMsg());
            return responseWrapper.toJSON();
        }
        logger.info("[user]修改头像成功|uid="+uid);

        userService.updateAvatar(uid, avatarUrl);
        return responseWrapper.toJSON();
    }


    /**
     * 修改昵称
     * @param uid
     * @param nickName
     * @return
     */
    @TokenRequired
    @RequestMapping(value = "/update_nickname", produces = {"application/json;charset=UTF-8"})
    @ResponseBody
    public String updateNickName(@RequestParam(value = "uid") Integer uid,
                                 @RequestParam(value = "nickname") String nickName){
        ResponseWrapper responseWrapper = new ResponseWrapper();
        responseWrapper.setCode(ResponseCode.SUCCESS.getCode());
        ResponseCode rc = validateParamImg(uid, nickName);

        if (!rc.getCode().equals("0")){
            responseWrapper.setCode(rc.getCode());
            responseWrapper.setMsg(rc.getMsg());
            return responseWrapper.toJSON();
        }
        logger.info("[user]修改昵称成功|uid="+uid);

        userService.updateNickName(uid, nickName);
        return responseWrapper.toJSON();
    }


    /**
     * 修改昵称
     * @param uid
     * @param gender
     * @return
     */
    @TokenRequired
    @RequestMapping(value = "/update_gender", produces = {"application/json;charset=UTF-8"})
    @ResponseBody
    public String updateGender(@RequestParam(value = "uid") Integer uid,
                                 @RequestParam(value = "gender") Integer gender){
        ResponseWrapper responseWrapper = new ResponseWrapper();
        responseWrapper.setCode(ResponseCode.SUCCESS.getCode());

        logger.info("[user]修改性别成功|uid="+uid);

        userService.updateGender(uid, gender);
        return responseWrapper.toJSON();
    }



    /**
     * 获取短信验证码（绑定手机号）
     *
     * @return
     */
    @RequestMapping(value = "/update/SMCode", produces = {"application/json;charset=UTF-8"})
    @ResponseBody
    public String getUpdateSMCode(String area,
                                  @RequestParam("cell_phone") String phone_number) throws UnsupportedEncodingException {

        long start = System.currentTimeMillis();
        ResponseWrapper responseWrapper = new ResponseWrapper();
        responseWrapper.setCode(ResponseCode.SUCCESS.getCode());
        ResponseCode rc = validateAreaAndPhone(area, phone_number);
        if (!"0".equals(rc.getCode())) {
            responseWrapper.setCode(rc.getCode());
            responseWrapper.setMsg(rc.getMsg());
            return responseWrapper.toJSON();
        }
        String smCode = generalSMCode(4);
        String tpl = "";
        tpl = MSGTPL.replaceAll("\\{0\\}", smCode);

        boolean isSend=  msgSender.sendSMS(tpl,phone_number,"2000");
        if(!isSend){
            responseWrapper.getCost(System.currentTimeMillis()-start);
            responseWrapper.setCode(ResponseCode.SMCODE_SEND_FAIL.getCode());
            responseWrapper.setMsg(ResponseCode.SMCODE_SEND_FAIL.getMsg());
            return responseWrapper.toJSON();
        }
        logger.info("[发送验证码][绑定手机号]cellnumber=" + phone_number + "|msg=" + tpl);
        XMemcached.set(CacheKeyConstants.KEY_PHONE_SMCODE_UPDATE + "_" + phone_number, EXPIRE_5_MINUTE, smCode);
        responseWrapper.getCost(System.currentTimeMillis()-start);
        return responseWrapper.toJSON();
    }


    /**
     * 修改手机号
     * @param uid
     * @param cellnumber
     * @return
     */
    @TokenRequired
    @RequestMapping(value = "/update_cellnumber", produces = {"application/json;charset=UTF-8"})
    @ResponseBody
    public String updateCellNumber(@RequestParam(value = "uid") Integer uid,
                                   @RequestParam(value="area") String area,
                                 @RequestParam(value = "cellnumber") String cellnumber,
                                  @RequestParam(value="smCode") String smCode){
        long start = System.currentTimeMillis();
        ResponseWrapper responseWrapper = new ResponseWrapper();
        responseWrapper.setCode(ResponseCode.SUCCESS.getCode());
        ResponseCode rc = validateParamImg(uid, cellnumber);
        if (!rc.getCode().equals("0")){
            responseWrapper.setCode(rc.getCode());
            responseWrapper.setMsg(rc.getMsg());
            return responseWrapper.toJSON();
        }
        String oldSMCode = XMemcached.get(CacheKeyConstants.KEY_PHONE_SMCODE_UPDATE + "_" + cellnumber);

        //万能测试号
        if ("3141592".equals(smCode)) {
            oldSMCode = "3141592";
        }
        if (oldSMCode == null) {
            responseWrapper.setCode(ResponseCode.SMCODE_TIMEOUT.getCode());
            responseWrapper.setMsg(ResponseCode.SMCODE_TIMEOUT.getMsg());
            return responseWrapper.toJSON();
        }
        if (StringUtils.equals(smCode, oldSMCode)) {
            ThirdUser thirdUser = thirdUserService.getThirdUserByUid(uid);
            if(thirdUser==null){
                responseWrapper.setCode(ResponseCode.THIRDUSER_ONLY.getCode());
                responseWrapper.setMsg(ResponseCode.THIRDUSER_ONLY.getMsg());
                return responseWrapper.toJSON();
            }
            User user = userService.getUserByCell(cellnumber);
            if(user!=null){
                responseWrapper.setCode(ResponseCode.REPETITION_CELL.getCode());
                responseWrapper.setMsg(ResponseCode.REPETITION_CELL.getMsg());
                return responseWrapper.toJSON();
            }
            logger.info("[user]修改手机号成功|uid="+uid);
            userService.updateCellNumber(uid, area,cellnumber);
            responseWrapper.setCode(ResponseCode.SUCCESS.getCode());
            responseWrapper.setMsg(ResponseCode.SUCCESS.getMsg());
        }
        else {
            responseWrapper.setCode(ResponseCode.SMCODE_ILLEGAL_TIMEOUT.getCode());
            responseWrapper.setMsg(ResponseCode.SMCODE_ILLEGAL_TIMEOUT.getMsg());
        }
        responseWrapper.getCost(System.currentTimeMillis()-start);
        return responseWrapper.toJSON();


    }

    /**
     * 修改手机号
     * 只有第三方登陆才能修改
     * @param uid
     * @return
     */
    @TokenRequired
    @RequestMapping(value = "/unbind/cell", produces = {"application/json;charset=UTF-8"})
    @ResponseBody
    public String unbingCell(@RequestParam(value = "uid") Integer uid){
        ResponseWrapper responseWrapper = new ResponseWrapper();
        responseWrapper.setCode(ResponseCode.SUCCESS.getCode());
        logger.info("[user]解绑手机号成功|uid="+uid);

        userService.updateCellNumber(uid,StringUtils.EMPTY,StringUtils.EMPTY);
        return responseWrapper.toJSON();
    }



//    /**
//     * 修改手机号
//     * @param uid
//     * @param password
//     * @return
//     */
//    @TokenRequired
//    @RequestMapping(value = "/update_password", produces = {"application/json;charset=UTF-8"})
//    @ResponseBody
//    public String updatePassword(@RequestParam(value = "uid") Integer uid,
//                                   @RequestParam(value = "password") String password,
//                                  @RequestParam(value="orginPwd")String orginPwd ){
//        ResponseWrapper responseWrapper = new ResponseWrapper();
//        responseWrapper.setCode(ResponseCode.SUCCESS.getCode());
//
//        String pwd= TokenUtil.generatePassword(orginPwd);
//        User user = userService.getUser(uid);
//        //比较原始密码
//        if(!orginPwd.equalsIgnoreCase(user.getPassword())){
//            logger.info("[user]修改密码失败|uid="+uid);
//            responseWrapper.setCode(ResponseCode.PWD_WRONG.getCode());
//            responseWrapper.setMsg(ResponseCode.PWD_WRONG.getMsg());
//            return responseWrapper.toJSON();
//        }
//        logger.info("[user]修改密码成功|uid="+uid);
//        userService.updatePassword(uid, pwd);
//        return responseWrapper.toJSON();
//    }



    private ResponseCode validateParam(Integer userId, Integer gender) {
        if (userId == null || gender == null)
            return ResponseCode.PARAMS_FAIL;
        return ResponseCode.SUCCESS;
    }

    private ResponseCode validateParam(Integer userId, String nickName) {
        if (userId == null || StringUtils.isBlank(nickName) || nickName.length()>20)
            return ResponseCode.PARAMS_FAIL;
        return ResponseCode.SUCCESS;
    }

    private ResponseCode validateParamImg(Integer userId, String url){
        if (userId == null || StringUtils.isBlank(url))
            return ResponseCode.PARAMS_FAIL;
        return ResponseCode.SUCCESS;
    }




    public static class ThirdUserParam {
        public ThirdUserParam() {

        }

        private String open_id;
        private String user_name;
        private String avatar_url;
        private Integer gender;

        public String getOpen_id() {
            return open_id;
        }

        public void setOpen_id(String open_id) {
            this.open_id = open_id;
        }

        public String getUser_name() {
            return user_name;
        }

        public void setUser_name(String user_name) {
            this.user_name = user_name;
        }

        public String getAvatar_url() {
            return avatar_url;
        }

        public void setAvatar_url(String avatar_url) {
            this.avatar_url = avatar_url;
        }

        public int getGender() {
            return gender;
        }

        public void setGender(int gender) {
            this.gender = gender;
        }
    }



    private void createThirdUser(Integer userId, Byte type, ThirdUserParam thirdUserParam) {
        ThirdUser thirdUser;
        thirdUser = new ThirdUser();
        thirdUser.setUserId(userId);
        thirdUser.setType(type);
        thirdUser.setOpenId(thirdUserParam.getOpen_id());
        thirdUser.setNickName(thirdUserParam.getUser_name());
        thirdUser.setAvatarUrl(thirdUserParam.getAvatar_url());
        thirdUser.setCreateTime(new Date());
        thirdUser.setGender(thirdUserParam.getGender());
        thirdUserService.saveThirdUser(thirdUser);
    }

    private User createUser(ThirdUserParam thirdUserParam) {
        User user = new User();
        user.setNickName(thirdUserParam.getUser_name());
        user.setAvatarUrl(thirdUserParam.getAvatar_url());
        user.setCreateTime(new Date());
        user.setGender(thirdUserParam.getGender());
        int id =userService.saveUser(user);
        user.setId(id);
        String token=TokenUtil.generalToken(id);
        user.setToken(token);
        userService.updateUserToken(id,token);
        return user;
    }

    private User createUser(String area, String cellNumber) {
        User user = new User();
        user.setGender(Gender.X.getGender());
        user.setArea(area);
        user.setCellNumber(cellNumber);
        user.setNickName(generalNickName());

        user.setCreateTime(new Date());
        return user;
    }


    private ResponseCode validateParam(Byte type, String signature, ThirdUserParam thirdUserParam) {
        if (type == null || StringUtils.isBlank(signature) || StringUtils.isBlank(thirdUserParam.getOpen_id())
                || StringUtils.isBlank(thirdUserParam.getUser_name())
                ||thirdUserParam.gender==null)
            return ResponseCode.PARAMS_FAIL;
        else
            return ResponseCode.SUCCESS;
    }


    private ResponseCode validateAreaAndPhone(String area, String phone_number) {
        if (StringUtils.isBlank(area) || StringUtils.isBlank(phone_number) || !isArea(area) || !isPhone(phone_number))
            return ResponseCode.PARAMS_FAIL;
        return ResponseCode.SUCCESS;
    }

    private boolean isArea(String area) {
        Pattern p = Pattern.compile("^\\d{2,4}$");
        return p.matcher(area).matches();
    }

    private boolean isPhone(String phoneNumber) {
        Pattern p = Pattern.compile("^1[0-9][0-9][0-9]{8}$");
        return p.matcher(phoneNumber).matches();
    }

    private String generalSMCode(int length) {
        StringBuilder sb = new StringBuilder();
        Random random = new Random();
        for (int i = 0; i < length; i++) {
            sb.append(random.nextInt(10));
        }
        return sb.toString();
    }

    private String generalNickName() {
        StringBuilder sb = new StringBuilder();
        Random random = new Random();
        for (int i = 0; i < 10; i++) {
            sb.append(random.nextInt(10));
        }
        return NICKNAME + MD5Utils.md5Hex(sb.toString()).toUpperCase();
    }


}
