import {GenderEnum} from "./gender.enum";
import {StatisticsModel} from "./statistics.model";

export class  AccountModel {
     id: number;
     username: string;
     nick: string
     email: string;
     firstName: string;
     lastName: string;
     age: number;
     gender: GenderEnum;
     avatar?: string;
     isFirstLogin: boolean;
     statistics: StatisticsModel;
}