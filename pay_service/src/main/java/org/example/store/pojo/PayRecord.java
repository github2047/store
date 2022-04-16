package org.example.store.pojo;


import lombok.Data;
import lombok.experimental.Accessors;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.Date;

@Data
@Accessors(chain = true)
@Entity
public class PayRecord {

  @Id
  private String no;
  private Integer mchId;
  private Integer uid;
  private String title;
  private String detail;
  private String outTradeNo;
  private String attach;
  private Long totalFee;
  private Date timeStart;
  private Date timeExpire;
  private String notifyUrl;
  private Date payTime;
  private Date createTime;
  private Date updateTime;
  private int status;

}
