import {IPage, Merchant, PayRecord} from "./types";
import {get, post} from "./common";

export interface LoginParam {
    account: string
    password: string
}

export interface RegParam extends LoginParam {
    appName: String
}

export function queryRecord(id: number, page: number = 1, size: number = 15): Promise<IPage<PayRecord>> {
    return get('/merchant/records', {id, page, size})
}

export function appNameExists(appName: string): Promise<boolean> {
    return get('/merchant/exists', {appName})
}

export function accountExists(account: string): Promise<boolean> {
    return get('/merchant/exists', {account})
}

export function login(params: LoginParam): Promise<Merchant> {
    return post('/merchant/login', params)
}

export function create(params: RegParam): Promise<Merchant> {
    return post('/merchant/create', params)
}
export function createKey(id: number): Promise<Merchant> {
    return get('/merchant/create-key', {id})
}
export function updateIcon(id: number,icon:string): Promise<Merchant> {
    return post('/merchant/update-icon', {id,icon})
}
export function query(id: number): Promise<Merchant> {
    return get('/merchant/query', {id})
}

export default {
    queryRecord,
    login,updateIcon,
    create,createKey,
    accountExists,
    appNameExists,
    query
}