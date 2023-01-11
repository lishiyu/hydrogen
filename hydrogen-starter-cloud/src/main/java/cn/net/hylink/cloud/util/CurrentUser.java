package cn.net.hylink.cloud.util;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONException;
import cn.hutool.json.JSONUtil;
import cn.net.hylink.cloud.common.Constant;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.Enumeration;
import java.util.List;

@Slf4j
@Getter
@ToString
@Setter
public class CurrentUser {

    // 用户ID
    private Object id;
    // 用户姓名
    private String name;
    // 登录账号
    private String username;
    // 用户组织ID
    private String departmentId;
    // 用户组织编码
    private String departmentCode;
    // 用户组织名称
    private String departmentName;
    // 证件号码
    private String policeNum;
    // 身份证号
    private String idCard;
    // 用户请求源IP
    private String uip;
    private String token;
    private String policeId;
    /**
     * 是否是运维账号
     */
    private Integer isAdmin;
    /**
     * 管辖机构编码
     */
    private List<String> managementDepartmentCodes;

    /**
     * 管辖机构编码
     */
    private List<String> roleCodes;

    /**
     * 用户手机号
     */
    private String telephoneNumber;

    /**
     * 获取当前在线用户
     */
    public static CurrentUser get() {
        CurrentUser user = new CurrentUser();
        ServletRequestAttributes ra = null;
        try {
            ra = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
        } catch (Exception e) {
            ra = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        }
        if (ra != null) {
            HttpServletRequest req = ra.getRequest();

            if (req.getHeader(Constant.ID) == null) {
                return user;
            }
            try {
                user.id = Long.valueOf(req.getHeader(Constant.ID));
            } catch (NumberFormatException e) {
                user.id = req.getHeader(Constant.ID);
                log.error("用户Id转换类型失败，错误信息:{}", e.getMessage(), e);

            }
            try {
                user.name = URLDecoder.decode(req.getHeader(Constant.NAME), "UTF-8");

                if (StrUtil.isNotBlank(req.getHeader(Constant.DEPARTMENT_NAME))) {
                    user.departmentName = URLDecoder.decode(req.getHeader(Constant.DEPARTMENT_NAME), "UTF-8");
                }

                if (StrUtil.isNotBlank(req.getHeader(Constant.POLICE_ID))) {
                    user.policeId = URLDecoder.decode(req.getHeader(Constant.POLICE_ID), "UTF-8");
                }

            } catch (Exception e) {
                log.error("编码设置错误：{}", e.getMessage(), e);
            }
            user.uip = req.getHeader(Constant.UIP);
            user.username = req.getHeader(Constant.USERNAME);
            user.token = req.getHeader(Constant.TOKEN);
            user.isAdmin = Integer.valueOf(req.getHeader(Constant.IS_ADMIN));
            user.departmentId = req.getHeader(Constant.DEPARTMENT_ID);
            user.departmentCode = req.getHeader(Constant.DEPARTMENT_CODE);
            user.policeNum = req.getHeader(Constant.POLICE_NUM);
            user.idCard = req.getHeader(Constant.ID_CARD);
            user.telephoneNumber = req.getHeader(Constant.TELEPHONE_NUMBER);

            if (StrUtil.isNotBlank(req.getHeader(Constant.MANAGEMENT_DEPARTMENT_CODES))) {
                try {
                    user.managementDepartmentCodes = JSONUtil.toList(req.getHeader(Constant.MANAGEMENT_DEPARTMENT_CODES), String.class);
                } catch (JSONException e) {
                    user.managementDepartmentCodes = Collections.singletonList(req.getHeader(Constant.MANAGEMENT_DEPARTMENT_CODES));
                }
            }

            if (StrUtil.isNotBlank(req.getHeader(Constant.ROLE_CODES))) {
                try {
                    user.roleCodes = JSONUtil.toList(req.getHeader(Constant.ROLE_CODES), String.class);
                } catch (JSONException e) {
                    user.roleCodes = Collections.singletonList(req.getHeader(Constant.ROLE_CODES));
                }
            }

        }
        return user;
    }
}
