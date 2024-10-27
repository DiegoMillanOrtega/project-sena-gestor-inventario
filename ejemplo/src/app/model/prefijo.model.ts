import { Consecutive } from "./consecutivo.model";

export interface Prefijo {
    prefixId:    number;
    prefix:      string;
    consecutive: Consecutive[];
}