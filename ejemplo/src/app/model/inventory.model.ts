import { Category } from "./category.model";

export interface Inventory {
    id:       number;
    product:  string;
    category: Category;
    price: number | string;
    stock:    number;
    cantVender?: number;
    selected?: boolean
}
