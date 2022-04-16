package org.example.store.controller.web.pay.order;


import org.example.store.controller.web.ActionParam;
import org.example.store.controller.web.ActionProcess;
import org.example.store.pojo.ApiResponse;
import org.springframework.stereotype.Component;

@ActionRouter(
        name = "pay.order.test",
        paramType = ActionParam.class
)
@Component
public class Test implements ActionProcess<ActionParam> {
    @Override
    public ApiResponse process(ActionParam actionParam) {
        return ApiResponse.Success("test success -> " + actionParam.getMerchant().getAppName());
    }
}
