// 接口地址
export const apiUrl = localStorage.getItem("pay.api.url") || 'http://localhost:30083';
// 应用配置
const appConfig = {
    // 接口
    api: {
        url: apiUrl
    },
    // 应用名称
    app: {
        name: '魔法支付',
        version: '1.0.0',
        dashboardTitle: ''
    },
    apiKey: 'test'
}
export default appConfig