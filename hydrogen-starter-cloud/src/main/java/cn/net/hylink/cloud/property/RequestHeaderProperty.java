package cn.net.hylink.cloud.property;

import cn.net.hylink.cloud.enums.HeaderType;
import lombok.Data;

@Data
public class RequestHeaderProperty {

    private String headerName;

    private HeaderType headerType;

}
