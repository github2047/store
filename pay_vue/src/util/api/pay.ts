import {IPage, PayRecord} from "./types";
import {get} from "./common";

export function queryRecord(uid: number, page: number = 1, size: number = 15): Promise<IPage<PayRecord>> {
    return get('/pay/record', {uid, page, size})
}


export default {
    queryRecord
}