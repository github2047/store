import {SystemNotice} from "./types";
import {get} from "./common";

export function query(size = 5): Promise<SystemNotice[]> {
    return get('/notice/query', {size})
}

export default {
    query
}