import {AppProfileEnum} from "./app-profile.enum";

export class AppInfoModel {
    projectName: string;
    projectDescription: string;
    version: string;
    profile: AppProfileEnum;
}