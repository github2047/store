package org.example.store.controller.web;

import lombok.Data;
import lombok.ToString;
import lombok.experimental.Accessors;
import org.example.store.pojo.Merchant;


@Data
@ToString
@Accessors(chain = true)
public class ActionParam {
    /**
     * 商户信息
     */
    Merchant merchant;
}
