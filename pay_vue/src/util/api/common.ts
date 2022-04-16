// @ts-ignore
import axios, {AxiosRequestConfig, AxiosResponseHeaders, AxiosResponse} from 'axios'
// @ts-ignore
import md5 from 'crypto-js/md5'
// @ts-ignore
import qs from 'qs'
import {nextTick} from 'vue'
// @ts-ignore
import config from '../config';
import {ApiResponse, HttpException} from "./types";
import {useMessage} from 'naive-ui'

axios.defaults.baseURL = config.api.url;
axios.defaults.timeout = 3000000;
// @ts-ignore
axios.interceptors.request.use(function (config) {
    // logger.log('headers ===>', config.headers)
    return config;
})

export function gotoLogin() {
    const redirectLogin = () => {
        // removeToken();
        location.hash = '/login?from=' + location.pathname
    }
    nextTick(() => {
        alert('登录信息已过期,请重新登录');//.then(redirectLogin).catch(redirectLogin)
        // history.replaceState({},'login','/login?from=' + location.pathname)
        // 删除已经保存的凭证 重新登录
        // const router = useRouter();
        // console.log('router=>',router)
        // router?.replace('/login').then(r => console.log('没有权限去登录吧'));
    }).then(() => logger.log('goto login'))
}

const logger = {
    log(message?: any, ...args: any[]): void {
        console.log(message, ...args)
    }
}

const message = {
    error(msg: string) {
        alert(msg)
    }
}
// @ts-ignore
axios.interceptors.response.use((response: AxiosResponse<ApiResponse>) => {
    let res = response.data;
    if (res.code == 0 || res.code == 200) {
        return res;
    } else {
        logger.log('interceptor errors ===>', res)
        if (res.code && res.code == -10000) {
            message.error(res.message || "系统出错了");
        } else if (res.code && res.code == 500) {
            message.error("请求数据失败,请检查网络或者联系管理员");
            return;
        }
        // 报错
        // ElMessage.error(res.message);
        throw new ApiError(res.message || "系统出错了", res.code)
        // useRouter().replace('/login').then(r => console.log('没有权限去登录吧'))
    }
});


export class ApiError extends Error {
    code: number = -1;

    constructor(message = '', code = -1) {
        super(message);
        this.code = code;
    }
}

export interface ListType {
    current: number;
    hitCount: boolean;
    pages: number;
    records: any;
    searchCount: true
    size: number;
    total: number;
}

export function getSignature(params = null, apiKey = null,) {
    const token = apiKey || config.apiKey;
    const paramsKey = md5(params ? params : token).toString()
    const timestamp = Date.now().toString();
    const nonce = Math.random().toString().substr(5, 6);

    const arr = [token, timestamp, nonce, paramsKey]
    const str = arr.join('');
    // console.log('加密前==>', str)
    const signature = md5(str).toString();
    return {
        _timestamp: timestamp,
        _nonce: nonce,
        _signature: signature
    }
}

interface HttpRequestParam {

}

function buildParams(url: string, params: object = {}) {
    if (url.indexOf('?') != -1) {
        let _p = qs.parse(url.substr(url.indexOf('?') + 1));
        url = url.substr(0, url.indexOf('?'));
        for (let pKey in _p) {
            // @ts-ignore
            params[pKey] = _p[pKey]
        }
    }
    let _params = {}
    Object.keys(params).sort().map(k => {
        // @ts-ignore
        let value = params[k];
        if (value.length) {
        }
        // @ts-ignore
        _params[k] = params[k].toString()
    });
    return JSON.stringify(_params)
}

export function request(url: string, method: 'get' | 'post' | 'head' | 'options',
                        params = {}, postType = 'json', signature = true): Promise<any> {
    const headers = {
        // 'Content-Type':'application/json',
        'Content-Type': 'application/x-www-form-urlencoded',
    };

    return new Promise((resolve, reject) => {
        let config: AxiosRequestConfig = {
            url,
            method: method,
            responseType: 'json',
            headers,
            params
        }

        // url += url.indexOf('?') == -1 ?'?':'&'
        let _params = null, _type = 'get';
        params = params || {}
        if (method.toLocaleLowerCase() == 'post') {
            headers["Content-Type"] = 'application/json'
            config['data'] = JSON.stringify(params);
            _params = JSON.stringify(params)
            params = {}
        } else {
            _params = buildParams(url, params)
        }

        // @ts-ignore
        const signature = getSignature(_params);
        config['params'] = {
            ...params,
            ...signature,
            _type
        };
        if (!signature) {
            config['params']['_sign'] = 'no'
        }
        // @ts-ignore
        axios.request(config).then((response) => {
            // logger.log(url, '==>', response)
            resolve(response.data);
        }).catch((e: HttpException) => {
            // logger.error(e);
            if (e.message) {
                // e.message = lang(e.message);
            }
            if (e.response) {
                const {status, data} = e.response;
                if (status == 500 || status == 501 || status == 502) {
                    e.message = "服务器出错了,请稍后~"
                }
                // logger.error(data);
                if (status == 401 && data &&
                    (data.toString() == 'token verify fail' || data.toString() == 'no token')) {
                    // console.log('need refresh token', e)
                    // gotoLogin()
                    reject(e);
                    return;
                }
            }
            reject(e)
        })
    })
}

export function get(url: string, param: { key: string } | {} = {}): Promise<any> {
    return request(url, 'get', param)
}

export function post(url: string, param = {}, postType = "json"): Promise<any> {
    param = param || {}
    return request(url, 'post', param, postType)
}

export interface IPage {
    pageNo: number;
    pageSize: number
}