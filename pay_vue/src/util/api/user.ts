import {Userinfo} from "./types";
import {get, post} from "./common";

export function login(username: string, password: string): Promise<Userinfo> {
    return post('/user/login', {username, password})
}
export function reg(data:any): Promise<Userinfo> {
    return post('/user/reg', data)
}

export function query(name: string): Promise<Userinfo> {
    return get('/user/query', {name})
}

export function charge(id:number,money: number): Promise<Userinfo> {
    return get('/user/charge', {id,money})
}
export function emailExists(email:string): Promise<Userinfo> {
    return get('/user/exists', {email})
}

export default {
    login,reg,
    query,
    charge,
    emailExists
}