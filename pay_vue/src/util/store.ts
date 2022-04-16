import {Merchant, Userinfo} from "./api/types";

interface StoreDataType {
    time: number,
    value: any,
    expire: number
}

const keys = {
    store: {
        user: 'login_userinfo',
        merchant: 'login_merchant',
    }
}

class Store {
    protected _loginUser!: Userinfo | null;
    protected _merchantInfo!: Merchant | null;

    defaultIPage(size = 15, current = 1) {
        return {
            pages: 0,
            total: 0,
            size,
            current,
            records: []
        }
    }

    set loginUser(value: Userinfo | null) {
        this._loginUser = value;

        if (null == value) {
            this.remove(keys.store.user)
        } else {
            this.set(keys.store.user, value)
        }
    }

    get loginUser(): Userinfo {
        return this._loginUser || this.get(keys.store.user);
    }

    removeLoginUser() {
        this.loginUser = null
    }

    set merchantInfo(value: Merchant | null) {
        this._merchantInfo = value;
        if (null == value) {
            this.remove(keys.store.merchant)
        } else {
            this.set(keys.store.merchant, value)
        }
    }

    get merchantInfo(): Merchant {
        return this._merchantInfo || this.get(keys.store.merchant)
    }

    removeMerchantInfo() {
        this.merchantInfo = null
    }


    /**
     *
     * @param key
     * @param value
     * @param expire 过期时间,-1表示不过期
     */
    set(key: string, value: any, expire: number = -1) {
        let obj = {
            value,
            time: Date.now(),
            expire: expire
        };
        localStorage.setItem(key, JSON.stringify(obj));
    }

    remove(key: string) {
        localStorage.removeItem(key);
    }

    get(key: string) {
        let val = localStorage.getItem(key);
        if (!val) {
            return val;
        }
        const data = JSON.parse(val) as StoreDataType;
        if (data.expire != -1 && (Date.now() - data.time) > data.expire) {
            localStorage.removeItem(key);
            return null;
        }
        return data.value;
    }

}

const store = new Store()
export default store