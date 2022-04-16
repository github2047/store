import {FormInst} from "naive-ui";

export function defaultFormInstValue(): FormInst {
    return {} as FormInst
}

export interface BaseType {
    create_time?: Date;
    update_time?: Date;
    status?: number;
}

export interface ApiResponse {
    code: number;
    data: any;
    message?: string | null;
    token?: string;
}

export interface HttpException {
    response: any;
    message: any;
    status: number

}

export interface Userinfo {
    balance: number
    createTime: string
    email: string
    id: number
    password: string
    payPwd: string
    phone: string
    status: number
    updateTime: string
    username: string
}

export interface PayRecord {
    no: string
    uid: number
    title: string
    detail: string
    mchId: string
    notifyUrl: string
    outTradeNo: string
    payTime: string
    attach: string
    totalFee: number
    timeExpire: Date
    timeStart: Date
    createTime: Date
    updateTime: Date
    status: number
}

export interface IPage<T> {
    current: number
    pages: number
    records: T[]
    size: number
    total: number
}

export interface Merchant {
    id: number
    account: string
    password: string
    appName: string
    appIcon: string
    secret: string
    pubKey: string
    balance: number
}

export interface SystemNotice extends BaseType {
    id: number
    title: string
    content: string
}