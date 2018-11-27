import {GenderModel} from "./gender.enum";

export class  AccountModel {
     id: number;
     username: string;
     nick: string
     email: string;
     firstName: string;
     lastName: string;
     age: number;
     gender: GenderModel;
     avatar?: any[];
     isFirstLogin: boolean;
     login: string;
}   