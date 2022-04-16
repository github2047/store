package org.example.store.controller.web.pay.order.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.store.controller.web.ActionProcess;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ActionProcessData {
    private Class type;
    private ActionProcess process;
}
