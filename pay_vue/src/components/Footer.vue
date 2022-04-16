<template>
    <div class="footer text-center">
        <div class="ownership">
            <a href="###">经营许可证编号：合字B2-1234567890</a>
            <a style="margin-left:10px;" href="##">网站备案：蜀ICP备1234567890号-1</a>
        </div>
        <div class="nav-icons">
            <a href="https://gw.alipayobjects.com/mdn/rms/afts/img/A*oiJsR7ymj2EAAAAAAAAAAAAAARQnAQ" class="pic1"
               target="_blank"></a>
            <a href="https://sealinfo.verisign.com/splash?form_file=fdf/splash.fdf&amp;dn=WWW.ALIPAY.COM&amp;zh_cn"
               class="pic2" target="_blank"></a>
            <a href="javascript:;" class="pic3" target="_blank"></a>
            <a href="https://gw.alipayobjects.com/mdn/rms_fb4804/afts/img/A*AXv7QaOCsLQAAAAAAAAAAABkARQnAQ"
               class="pic4" target="_blank"></a>
            <a href="https://fun.alipay.com/certificate/index.htm" class="pic7" target="_blank"></a>
        </div>
    </div>
</template>

<script>
export default {
    name: "Footer"
}
</script>

<style scoped lang="less">
a {
    text-decoration: none;
    color: #666;
}
.footer {
    position: absolute;
    bottom: 0px;
    left: 0px;
    width: 100%;
    padding: 10px 0;
    font-size: 12px;
    background-color: white;
    z-index: 99;
}
.nav-icons {
    width: 250px;
    height: 30px;
    margin: 0 auto;
    text-align: center;

    a {
        width: 20px;
        display: block;
        float: left;
        margin-right: 5px;
        height: 28px;
        background: url(https://img.alicdn.com/tps/TB1.cMTIpXXXXbLXVXXXXXXXXXX.png) no-repeat 0 0;
    }

    a.pic1 {
        background-position: 0px -5px;
        width: 18px;
    }

    a.pic1:hover {
        background-position: 1px -28px;
        width: 18px;
    }

    a.pic2 {
        background-position: -33px -5px;
        width: 40px;
    }

    a.pic2:hover {
        background-position: -32px -28px;
        width: 40px;
    }

    a.pic3 {
        background-position: -74px -5px;
        width: 33px;
    }

    a.pic3:hover {
        background-position: -73px -28px;
        width: 33px;
    }

    a.pic4 {
        background-position: -115px -5px;
        width: 18px;
    }

    a.pic4:hover {
        background-position: -114px -28px;
        width: 18px;
    }

    a.pic5 {
        background-position: -135px -5px;
        width: 31px;
    }

    a.pic5:hover {
        background-position: -134px -28px;
        width: 31px;
    }


    a.pic7 {
        background-position: -200px -5px;
        width: 20px;
    }

    a.pic7:hover {
        background-position: -200px -26px;
        width: 20px;
    }
}

</style>