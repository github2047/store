<template>
    <div id="page-main" class="main-bg">
        <div class="header ">
            <div class="row content-wrap">
                <div class="logo">
                    <img src="./../assets/pay-logo.svg" id="app-logo" alt="">
                </div>
                <div class="site-text">魔法支付</div>
            </div>
        </div>
        <div class="wrap content-wrap">
            <div class="slogan"></div>
            <div class="mid">
                <div class="main-entry">
                    <n-button class="login-btn" type="info" size="large" @click="$router.push('/login')">
                        <template #icon>
                            <n-icon>
                                <svg viewBox="0 0 1024 1024" version="1.1"
                                     xmlns="http://www.w3.org/2000/svg" width="128" height="128">
                                    <path
                                        d="M512 74.666667C270.933333 74.666667 74.666667 270.933333 74.666667 512S270.933333 949.333333 512 949.333333 949.333333 753.066667 949.333333 512 753.066667 74.666667 512 74.666667zM288 810.666667c0-123.733333 100.266667-224 224-224S736 686.933333 736 810.666667c-61.866667 46.933333-140.8 74.666667-224 74.666666s-162.133333-27.733333-224-74.666666z m128-384c0-53.333333 42.666667-96 96-96s96 42.666667 96 96-42.666667 96-96 96-96-42.666667-96-96z m377.6 328.533333c-19.2-96-85.333333-174.933333-174.933333-211.2 32-29.866667 51.2-70.4 51.2-117.333333 0-87.466667-72.533333-160-160-160s-160 72.533333-160 160c0 46.933333 19.2 87.466667 51.2 117.333333-89.6 36.266667-155.733333 115.2-174.933334 211.2-55.466667-66.133333-91.733333-149.333333-91.733333-243.2 0-204.8 168.533333-373.333333 373.333333-373.333333S885.333333 307.2 885.333333 512c0 93.866667-34.133333 177.066667-91.733333 243.2z"
                                    ></path>
                                </svg>
                            </n-icon>
                        </template>
                        用户登录
                    </n-button>
                    <n-button class="login-btn" type="info" size="large" @click="$router.push('/e-login')">
                        <template #icon>
                            <n-icon>
                                <svg viewBox="0 0 1137 1024" version="1.1"
                                     xmlns="http://www.w3.org/2000/svg" width="128" height="128">
                                    <path
                                        d="M1126.4 324.266667v-5.688889l-96.711111-238.933334C1012.622222 28.444444 967.111111 0 910.222222 0H238.933333c-56.888889 0-102.4 28.444444-119.466666 73.955556L17.066667 324.266667v5.688889C5.688889 347.022222 0 375.466667 0 398.222222c0 85.333333 45.511111 159.288889 119.466667 199.111111 28.444444 17.066667 68.266667 22.755556 108.088889 22.755556 68.266667 0 130.844444-28.444444 170.666666-79.644445 39.822222 51.2 102.4 79.644444 170.666667 79.644445s130.844444-28.444444 170.666667-79.644445c39.822222 51.2 102.4 79.644444 170.666666 79.644445 39.822222 0 73.955556-11.377778 108.088889-22.755556 73.955556-39.822222 119.466667-113.777778 119.466667-199.111111 0-22.755556-5.688889-51.2-11.377778-73.955555z m-142.222222 199.111111c-17.066667 11.377778-39.822222 17.066667-68.266667 17.066666-51.2 0-96.711111-22.755556-119.466667-68.266666 0-5.688889-5.688889-11.377778-11.377777-17.066667-5.688889-5.688889-17.066667-17.066667-39.822223-17.066667-17.066667 0-34.133333 5.688889-39.822222 17.066667-5.688889 5.688889-5.688889 11.377778-11.377778 17.066667-28.444444 39.822222-73.955556 68.266667-119.466666 68.266666-51.2 0-91.022222-22.755556-119.466667-68.266666 0-5.688889-5.688889-11.377778-11.377778-17.066667-17.066667-22.755556-62.577778-22.755556-79.644444 0-5.688889 5.688889-11.377778 11.377778-11.377778 17.066667C324.266667 512 278.755556 534.755556 227.555556 534.755556c-28.444444 0-51.2-5.688889-68.266667-17.066667C113.777778 494.933333 85.333333 449.422222 85.333333 398.222222c0-17.066667 0-34.133333 5.688889-45.511111L193.422222 113.777778c0-5.688889 5.688889-22.755556 39.822222-22.755556H910.222222c11.377778 0 34.133333 0 45.511111 28.444445l96.711111 238.933333v5.688889c5.688889 17.066667 5.688889 34.133333 5.688889 45.511111 0 39.822222-28.444444 85.333333-73.955555 113.777778z m28.444444 130.844444c-22.755556 0-39.822222 17.066667-39.822222 39.822222v221.866667c0 11.377778-11.377778 22.755556-22.755556 22.755556H182.044444c-11.377778 0-22.755556-11.377778-22.755555-22.755556v-221.866667c0-22.755556-17.066667-39.822222-39.822222-39.822222-22.755556 0-39.822222 17.066667-39.822223 39.822222v221.866667c0 56.888889 45.511111 102.4 102.4 102.4h768c56.888889 0 102.4-45.511111 102.4-108.088889v-221.866666c0-17.066667-17.066667-34.133333-39.822222-34.133334z m-119.466666-290.133333H238.933333c-22.755556 0-39.822222-17.066667-39.822222-39.822222-5.688889-22.755556 11.377778-39.822222 39.822222-39.822223h654.222223c22.755556 0 39.822222 17.066667 39.822222 39.822223 0 22.755556-22.755556 39.822222-39.822222 39.822222z"
                                    ></path>
                                </svg>
                            </n-icon>
                        </template>
                        商户登录
                    </n-button>
                </div>
                <div style="margin-top: 30px;">
                    <router-link to="/test-pay" style="color:#ccc" v-if="false">支付测试</router-link>
                    <n-button style="color:#ccc" @click="showModal=true">如何使用?</n-button>
                    <n-modal v-model:show="showModal">
                        <n-card style="width: 800px;" title="如何集合到项目中..." :bordered="false" size="huge">
                            <div style="background-color: #fff;">
                                <div class="section">
                                    <p>1.POM.xml的依赖</p>
                                    <div class="code-content">
<pre>&lt;dependencies&gt;
    ...
    &lt;dependency&gt;
        &lt;groupId&gt;com.github.callmeyan&lt;/groupId&gt;
        &lt;artifactId&gt;pay-client&lt;/artifactId&gt;
        &lt;version&gt;1.0.0&lt;/version&gt;
    &lt;/dependency&gt;
&lt;/dependencies&gt;
&lt;repositories&gt;
    &lt;!--  依赖的第三方库 --&gt;
    &lt;repository&gt;
        &lt;id&gt;jitpack.io&lt;/id&gt;
        &lt;url&gt;https://jitpack.io&lt;/url&gt;
    &lt;/repository&gt;
&lt;/repositories&gt;</pre>
                                    </div>
                                </div>
                                <div class="section">
                                    <p>2.创建支付订单</p>
                                    <div class="code-content">
<pre>PayClient client = PayClient.getClient("商户编号","公钥","私钥","https://pay.wm-app.xyz/api");
Order payOrder = new Order();
payOrder.setOut_trade_no(order.getId().toString());// 设置商城支付编号
payOrder.setTitle(order.getTitle()); // 设置支付标题
payOrder.setTotal_amount(order.getPrice()); // 设置订单金额
payOrder.setNotify_url(successUrl); // 设置通知url
payOrder.setTime_start(dateFormat.format(new Date())); // 开始时间
// 到支付系统下单
OrderResult orderResult = client.create(payOrder);
// 下单成功后进行构造支付链接
String payUrl = client.buildPayUrl(orderResult.getTrade_no(), "支付完成后回调url");
// 可以直接跳转并支付了...
System.out.println(payUrl);</pre>
                                    </div>
                                </div>
                                <div class="section">
                                    <p>3.支付完成后的回调处理</p>
                                    <div class="code-content">
<pre>public String paySuccess(String out_trade_no, String no, String sign, String timestamp) {
    PayClient payClient = PayClient.getClient("商户编号","公钥","私钥","https://pay.wm-app.xyz/api");
    // 由于签名被转码 所以需要解码
    sign = URLDecoder.decode(sign, "utf-8");
    // TODO 订单的支付状态更改
    // 验证请求是否合法
    if (payClient.verify(sign, no, timestamp)) {
        // 查询订单
        QueryOrder queryOrder = new QueryOrder();
        queryOrder.setTradeNo(no);
        PayRecord query = payClient.query(queryOrder);

        // 判断支付系统的支付状态
        if (query.getStatus() == PayRecord.PayStatus.PAID ||
                query.getStatus() == PayRecord.PayStatus.CONFIRM_PAID) {
            orderStatus.put(out_trade_no, true); // 这是为已支付
        }
        return "success";
    }
    return "来源不合法";
}</pre>
                                    </div>
                                </div>
                            </div>
                            <template #action>
                                <div style="text-align: center">
                                    <n-button type="info" @click="showModal=false">关闭</n-button>
                                </div>
                            </template>
                        </n-card>
                    </n-modal>
                </div>
            </div>
        </div>

        <Footer/>
    </div>
</template>

<script>
import Footer from "../components/Footer.vue";
import {ref} from "vue";

export default {
    name: "Index",
    components: {Footer},
    setup() {
        const showModal = ref(false)
        return {
            showModal
        }
    }
}
</script>

<style scoped lang="less">
a {
    text-decoration: none;
    color: #666;
}


.login-btn {
    & + .login-btn {
        margin-left: 20px;
    }
}

#page-main {
}

#main {
}


.wrap {
    position: absolute;
    left: 0;
    top: 20%;
    width: 100%;
    text-align: center;
    z-index: 2;
}

.slogan {
    width: 600px;
    height: 200px;
    background: url(https://img.alicdn.com/tps/TB1POhqIFXXXXXbXFXXXXXXXXXX.png) no-repeat center 0;
    display: inline-block;
    background-size: 600px;
}
</style>